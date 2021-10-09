package kontroler;

import model.KategorijaKnjige;
import repozitorijum.KategorijeKnjigaRepo;

import java.util.ArrayList;

public class KategorijaKnjigeController {

    KategorijeKnjigaRepo kategorijeKnjigaRepo;

    public KategorijaKnjigeController(KategorijeKnjigaRepo kategorijeKnjigaRepo) {
        this.kategorijeKnjigaRepo = kategorijeKnjigaRepo;
    }

    public ArrayList<String> getNaziviKategorija() {
        ArrayList<String> retVal = new ArrayList();
        for (KategorijaKnjige k : kategorijeKnjigaRepo.getListaEntiteta()) {
            retVal.add(k.getNaziv());
        }
        return retVal;
    }

}
