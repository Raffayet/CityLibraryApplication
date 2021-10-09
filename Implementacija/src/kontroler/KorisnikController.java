package kontroler;

import izuzeci.KorisnikNijeNadjen;
import model.Bibliotekar;
import model.Clan;
import model.Korisnik;
import model.enumeracije.VrstaBibliotekara;
import model.enumeracije.VrstaClana;
import model.enumeracije.VrstaKorisnika;
import repozitorijum.FabrikaRepo;
import repozitorijum.KorisnikRepo;
import view.administrator.AdminFrame;
import view.clan.ClanFrame;
import view.obradjivac.ObradjivacFrame;
import view.pozajmljivac.PozajmljivacFrame;

import javax.swing.*;
import java.time.LocalDate;

public class KorisnikController {

    private KorisnikRepo korisnikRepo;

    public KorisnikController(KorisnikRepo korisnikRepo) {
        this.korisnikRepo = korisnikRepo;
    }

    public KorisnikController() {
    }

    public Korisnik dobavljanjeRegistrovanogKorisnika(String korisnickoIme, String lozinka) throws KorisnikNijeNadjen {
        for (Korisnik k : korisnikRepo.getListaEntiteta()) {
            if (k.getKorisnickoIme().equals(korisnickoIme) & k.getLozinka().equals(lozinka)) {
                return k;
            }
        }
        throw new KorisnikNijeNadjen("Nije pronadjen korisnik");
    }

    public void otvaranjeProzoraZaKorisnika(Korisnik korisnik, JFrame jf, KorisnikController korisnikController, FabrikaRepo fabrikaRepo) {
        jf.setVisible(false);
        jf.dispose();
        if (korisnik instanceof Clan) {
            ClanFrame clanFrame = new ClanFrame((Clan) korisnik, korisnikController, fabrikaRepo);
            clanFrame.setVisible(true);
        }

        else {

            if (korisnik instanceof Bibliotekar){

                if (((Bibliotekar) korisnik).getVrstaBibliotekara() == VrstaBibliotekara.ADMINISTRATOR){

                    AdminFrame adminFrame = new AdminFrame((Bibliotekar) korisnik, korisnikController, fabrikaRepo);
                    adminFrame.setVisible(true);
                }

                else if (((Bibliotekar) korisnik).getVrstaBibliotekara() == VrstaBibliotekara.OBRADJIVAC){

                    ObradjivacFrame obradjivacFrame = new ObradjivacFrame((Bibliotekar) korisnik, korisnikController, fabrikaRepo);
                    obradjivacFrame.setVisible(true);
                }

                else {

                    PozajmljivacFrame pozajmljivacFrame = new PozajmljivacFrame((Bibliotekar) korisnik, korisnikController, fabrikaRepo);
                    pozajmljivacFrame.setVisible(true);
                }
            }
        }
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

    public Boolean korektniPodaciZaLogIn(String korisnickoIme, String lozinka){
        return !unetoPraznoPolje(korisnickoIme) && !unetoPraznoPolje(lozinka);
    }

    private Boolean unetoPraznoPolje(String vrednost) {
        return vrednost == null || vrednost.equals("");
    }

    public boolean korisnikPostoji(String korisnickoIme, String lozinka) {
        for (Korisnik k : korisnikRepo.getListaEntiteta()){
            if (k.getKorisnickoIme().equals(korisnickoIme) && k.getLozinka().equals(lozinka)){
                if (k.getStatusAktivnosti()){
                    return true;
                }
            }
        }
        return false;
    }
}
