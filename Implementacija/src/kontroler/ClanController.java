package kontroler;

import izuzeci.KorisnikNijeNadjen;
import izuzeci.NedostajucaVrednost;
import izuzeci.PogresanFormat;
import model.Clan;
import model.Clanarina;
import model.IzdataClanarina;
import model.Korisnik;
import model.enumeracije.TipClanarine;
import model.enumeracije.VrstaClana;
import model.enumeracije.VrstaKorisnika;
import repozitorijum.BibliotekarRepo;
import repozitorijum.ClanRepo;
import repozitorijum.FabrikaRepo;
import repozitorijum.KorisnikRepo;
import utils.Konstante;

import javax.swing.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClanController {

    private final ClanRepo clanRepo;

    private final KorisnikRepo korisnikRepo;

    public ClanController(ClanRepo clanRepo, KorisnikRepo korisnikRepo) {
        this.clanRepo = clanRepo;
        this.korisnikRepo = korisnikRepo;
    }

    public void modifikacijaPodatakaOdStraneClana(int id, String ime, String prezime, String korisnickoIme, String lozinka,
                                                  LocalDate datumRodjenja) {

        clanRepo.modifkacijaPodatakaOdStraneClana(id, ime, prezime, korisnickoIme, lozinka, datumRodjenja);
    }

    public void modifikacijaPodatakaOdStraneAdmina(int id, String ime, String prezime, String korisnickoIme,
                                                   String lozinka, VrstaKorisnika vrstaKorisnika, String statusAktivnosti,
                                                   String jmbg, LocalDate datumRodjenja, VrstaClana vrstaClana) {

        Boolean konvertovanStatusAktivnosti = konvertovanjeStatusaAktivnosti(statusAktivnosti);

        clanRepo.modifikacijaPodatakaOdStraneAdmina(id, ime, prezime, korisnickoIme, lozinka, vrstaKorisnika,
                konvertovanStatusAktivnosti, jmbg, datumRodjenja, vrstaClana);
    }

    public Clan registracijaClana(String ime, String prezime, String korisnickoIme, String lozinka,
                                  VrstaKorisnika vrstaKorisnika, String jmbg, LocalDate datumRodjenja,
                                  VrstaClana vrstaClana) {

        Clan clan = new Clan(korisnikRepo.generisanId(), ime, prezime, korisnickoIme, lozinka, vrstaKorisnika, jmbg
                , datumRodjenja, vrstaClana);
        clanRepo.dodajEntitet(clan);
        korisnikRepo.dodajEntitet(clan);
        return clan;
    }

    private Boolean konvertovanjeStatusaAktivnosti(String statusAktivnosti) {      // konvertovanje iz Stringa u Boolean

        if (statusAktivnosti.equals("Aktivan")){
            return true;
        }

        else {

            return false;
        }
    }

    public boolean unosValidan(String ime, String prezime, String korisnickoIme, String lozinka, String jmbg, LocalDate datumRodjenja) {

        if (ime.equals("") | prezime.equals("") | korisnickoIme.equals("") | lozinka.equals("") | unetoPraznoPolje(jmbg)
                | datumRodjenja == null) {
            JOptionPane.showMessageDialog(null, "Molim vas da unesite sve potrebne podatke."
                    ,"Greska", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        else if (ime.length() > 15 | prezime.length() > 20) {
            JOptionPane.showMessageDialog(null, "Ime ne može imati vise od 15 slova, a prezime više od 20 slova.");
            return false;
        }

        else if (jmbg.length() != 13 || !jmbg.matches(("^\\d+$"))){
            JOptionPane.showMessageDialog(null, "Neispravan format za JMBG!");
            return false;
        }

        return true;
    }

    private Boolean unetoPraznoPolje(String vrednost) {
        return vrednost == null || vrednost.equals("");
    }


    private boolean jmbgPogresanFormat(String jmbg) {
        return (jmbg.length() != 13 || !jmbg.matches("^\\d+$"));
    }

    public List<Korisnik> getClanovi() {

        List<Korisnik>retList = new ArrayList<Korisnik>();
        for (Clan c: clanRepo.getListaEntiteta()) {
            if (c.getStatusAktivnosti()) {
                retList.add(c);
            }
        }
        return retList;
    }

    public Clan getClanSaZadatimJMBGbrojem(String jmbg) {
        if (unetoPraznoPolje(jmbg)) {
            throw new NedostajucaVrednost("Niste uneli JMBG broj");
        }
        if (jmbgPogresanFormat(jmbg)) {
            throw new PogresanFormat("Pogresan format JMBG broja. Uneti 13 cifara");
        }
        for (Clan c : clanRepo.getListaEntiteta()) {
            if (c.getJmbg().equals(jmbg)) {
                return c;
            }
        }
        throw new KorisnikNijeNadjen("Nije moguce pronaci clana sa unetim JMBG brojem");
    }

    public Clan getClanById(int id) { return clanRepo.getById(id); }

    public List<Clan> getListaAktivnihClanova() {
        List<Clan> retVal = new ArrayList<>();
        for (Clan c : clanRepo.getListaEntiteta()) {
            if (c.aktivan())
                retVal.add(c);
        }
        return retVal;
    }

    public void brisanjeClana(int id) {

        Clan clan = clanRepo.getById(id);
        clan.setStatusAktivnosti(false);
    }
}
