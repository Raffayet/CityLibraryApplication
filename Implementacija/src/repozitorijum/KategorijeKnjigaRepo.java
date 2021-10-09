package repozitorijum;

import model.Entitet;
import model.KategorijaKnjige;

import java.util.ArrayList;

public class KategorijeKnjigaRepo extends GenerickiRepo<KategorijaKnjige> {

    public KategorijeKnjigaRepo(String putanjaDoFajla) {
        super(putanjaDoFajla);
    }

    @Override
    protected Entitet kreirajEntitetIDodajUListu(String[] tokeni) {
        int id = Integer.parseInt(tokeni[0]);
        String naziv = tokeni[1];
        KategorijaKnjige kategorijaKnjige = new KategorijaKnjige(id, naziv);

        if (tokeni.length > 2) {
            String podkategorijeIds = tokeni[2];
            kategorijaKnjige.setPodkategorijeIds(getPodkategorijeIdsFromString(podkategorijeIds));
        }

        dodajEntitet(kategorijaKnjige);
        return kategorijaKnjige;
    }

    private ArrayList<Integer> getPodkategorijeIdsFromString(String s) {
        String[] ids = s.split(",");
        ArrayList<Integer> retVal = new ArrayList<Integer>();
        for (String id : ids) {
            retVal.add(Integer.parseInt(id));
        }
        return retVal;
    }

    public void postaviVezuIzmedjuKategorijaIPodKategorija() {
        for (KategorijaKnjige k : listaEntiteta) {
            for (Integer i : k.getPodkategorijeIds()) {
                k.dodajPodkategoriju(getById(i));
            }
        }
    }
}
