package repozitorijum;

import model.Entitet;
import model.UcesnikKnjige;
import model.enumeracije.TipAutorstva;

public class UcesnikKnjigeRepo extends GenerickiRepo<UcesnikKnjige> {

    public UcesnikKnjigeRepo(String putanjaDoFajla) {
        super(putanjaDoFajla);
    }

    @Override
    protected Entitet kreirajEntitetIDodajUListu(String[] tokeni) {
        int id = Integer.parseInt(tokeni[0]);
        String ime = tokeni[1];
        String prezime = tokeni[2];
        TipAutorstva tipAutorstva = TipAutorstva.valueOf(tokeni[3]);
        UcesnikKnjige ucesnikKnjige = new UcesnikKnjige(id, ime, prezime, tipAutorstva);
        dodajEntitet(ucesnikKnjige);
        return ucesnikKnjige;
    }


}
