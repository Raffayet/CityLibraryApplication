package model;

import model.enumeracije.VrstaClana;
import model.enumeracije.VrstaKorisnika;

import java.time.LocalDate;

public class Clan extends Korisnik{

    private String jmbg;
    private LocalDate datumRodjenja;
    private VrstaClana vrstaClana;
    private IzdataClanarina trenutnaClanarina;

    private int trenutnaClanarinaId; // pomocni atribut pri pravljenju objekta zbog bidirekcione veze

    public Clan() {
    }

    public Clan(int id, String ime, String prezime, String korisnickoIme, String lozinka, VrstaKorisnika vrstaKorisnika,
                String jmbg, LocalDate datumRodjenja, VrstaClana vrstaClana, IzdataClanarina trenutnaClanarina) {
        super(id, ime, prezime, korisnickoIme, lozinka, vrstaKorisnika);
        this.jmbg = jmbg;
        this.datumRodjenja = datumRodjenja;
        this.vrstaClana = vrstaClana;
        this.trenutnaClanarina = trenutnaClanarina;
    }

    public Clan(int id, String ime, String prezime, String korisnickoIme, String lozinka, VrstaKorisnika vrstaKorisnika,
                String jmbg, LocalDate datumRodjenja, VrstaClana vrstaClana) {
        super(id, ime, prezime, korisnickoIme, lozinka, vrstaKorisnika);
        this.jmbg = jmbg;
        this.datumRodjenja = datumRodjenja;
        this.vrstaClana = vrstaClana;
    }

    @Override
    public String formatiranZapisZaFajl() {
        return String.format("%d|C|%s|%s|%s|%s|%s|%s|%s|%s|%s", this.getId(), this.getIme(), this.getPrezime(),
                this.getKorisnickoIme(), this.getLozinka(), this.getStatusAktivnostiKaoString(), this.getJmbg(),
                this.getDatumRodjenja().toString(), this.getVrstaClana(), this.getTrenutnaClanarina().getId());
    }

    public void setTrenutnaClanarinaId(int id) {
        this.trenutnaClanarinaId = id;
    }

    public int getTrenutnaClanarinaId() { return this.trenutnaClanarinaId; }

    public String getJmbg() {
        return jmbg;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public VrstaClana getVrstaClana() {
        return vrstaClana;
    }

    public IzdataClanarina getTrenutnaClanarina() {
        return trenutnaClanarina;
    }

    public void setTrenutnaClanarina(IzdataClanarina trenutnaClanarina) {
        this.trenutnaClanarina = trenutnaClanarina;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public void setVrstaClana(VrstaClana vrstaClana) {
        this.vrstaClana = vrstaClana;
    }

    public String toString() {
        return "Clan{" +
                super.toString() +
                "jmbg='" + jmbg + '\'' +
                ", datumRodjenja=" + datumRodjenja +
                ", vrstaClana=" + vrstaClana +
                "} ";
    }
}
