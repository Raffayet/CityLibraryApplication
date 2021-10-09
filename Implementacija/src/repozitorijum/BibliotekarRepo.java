package repozitorijum;

import model.Bibliotekar;
import model.Clan;
import model.Entitet;
import model.enumeracije.VrstaBibliotekara;
import model.enumeracije.VrstaKorisnika;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class BibliotekarRepo extends GenerickiRepo<Bibliotekar> {

    public BibliotekarRepo(String putanjaDoFajla) {
        super(putanjaDoFajla);
    }

    @Override
    protected Entitet kreirajEntitetIDodajUListu(String[] tokeni) {
        Bibliotekar b = null;
        int id = Integer.parseInt(tokeni[0]);
        String ime = tokeni[2];
        String prezime = tokeni[3];
        String korisnickoIme = tokeni[4];
        String lozinka = tokeni[5];
        String statusAktivnosti = tokeni[6];
        VrstaBibliotekara vrstaBibliotekara = VrstaBibliotekara.valueOf(tokeni[7]);

        b = new Bibliotekar(id, ime, prezime, korisnickoIme, lozinka, VrstaKorisnika.BIBLIOTEKAR, vrstaBibliotekara);
        if (statusAktivnosti.equals("false")) {
            b.setStatusAktivnosti(false);
        }
        dodajEntitet(b);
        return b;
    }

    public void modifikacijaPodatakaOdStraneAdmina(int id, String ime, String prezime, String korisnickoIme, String lozinka, VrstaKorisnika vrstaKorisnika,
                                       Boolean statusAktivnosti, VrstaBibliotekara vrstaBibliotekara){

        for (Bibliotekar e : getListaEntiteta()){

            if (e == getById(id)){

                e.setIme(ime);
                e.setPrezime(prezime);
                e.setKorisnickoIme(korisnickoIme);
                e.setLozinka(lozinka);
                e.setVrstaKorisnika(vrstaKorisnika);
                e.setStatusAktivnosti(statusAktivnosti);
                e.setVrstaBibliotekara(vrstaBibliotekara);
            }
        }
    }
}
