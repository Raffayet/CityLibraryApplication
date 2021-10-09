package kontroler;

import model.Bibliotekar;
import model.Korisnik;
import model.enumeracije.VrstaBibliotekara;
import model.enumeracije.VrstaKorisnika;
import repozitorijum.BibliotekarRepo;
import repozitorijum.FabrikaRepo;
import repozitorijum.KorisnikRepo;
import utils.Konstante;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BibliotekarController {

    private final BibliotekarRepo bibliotekarRepo;

    private final KorisnikRepo korisnikRepo;

    public BibliotekarController(BibliotekarRepo bibliotekarRepo, KorisnikRepo korisnikRepo) {
        this.bibliotekarRepo = bibliotekarRepo;
        this.korisnikRepo = korisnikRepo;
    }

    public List<Korisnik> getZaposleniBibliotekari() {

        List<Korisnik>retList = new ArrayList<Korisnik>();
        for (Bibliotekar b: bibliotekarRepo.getListaEntiteta()) {
            if (b.aktivan()) {
                retList.add(b);
            }
        }
        return retList;
    }

    public void brisanjeBibliotekara(int id) {

        Bibliotekar bibliotekar = bibliotekarRepo.getById(id);
        bibliotekar.setStatusAktivnosti(false);
    }

    public boolean unosValidan(String ime, String prezime, String korisnickoIme, String lozinka) {

        if (ime.equals("") | prezime.equals("") | korisnickoIme.equals("") | lozinka.equals("")) {
            JOptionPane.showMessageDialog(null, "Molim vas da unesite sve potrebne podatke."
                    ,"Greska", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        else if (ime.length() > 15 | prezime.length() > 20) {
            JOptionPane.showMessageDialog(null, "Ime ne može imati vise od 15 slova, a prezime više od 20 slova.");
            return false;
        }

        return true;
    }

    public void modifikacijaBibliotekara(int id, String ime, String prezime, String korisnickoIme, String lozinka,
                                         VrstaKorisnika vrstaKorisnika, String statusAktivnosti,
                                         VrstaBibliotekara vrstaBibliotekara) {

        Boolean konvertovanStatusAktivnosti = konvertovanjeStatusaAktivnosti(statusAktivnosti);

        bibliotekarRepo.modifikacijaPodatakaOdStraneAdmina(id, ime, prezime, korisnickoIme, lozinka, vrstaKorisnika,
                konvertovanStatusAktivnosti, vrstaBibliotekara);
    }

    private Boolean konvertovanjeStatusaAktivnosti(String statusAktivnosti) {      // konvertovanje iz Strina u Boolean

        if (statusAktivnosti.equals("Aktivan")){
            return true;
        }

        else {

            return false;
        }
    }

    public void registracijaBibliotekara(String ime, String prezime, String korisnickoIme, String lozinka,
                                         VrstaKorisnika vrstaKorisnika, VrstaBibliotekara vrstaBibliotekara) {

        Bibliotekar bibliotekar = new Bibliotekar(korisnikRepo.generisanId(), ime, prezime, korisnickoIme, lozinka, vrstaKorisnika
                ,vrstaBibliotekara);
        bibliotekarRepo.dodajEntitet(bibliotekar);
        korisnikRepo.dodajEntitet(bibliotekar);
    }
}
