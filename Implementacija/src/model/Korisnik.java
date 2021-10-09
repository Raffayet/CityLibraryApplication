package model;

import model.enumeracije.VrstaKorisnika;
import sun.util.locale.StringTokenIterator;

import java.util.Objects;

public abstract class Korisnik extends Entitet {

    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;
    private VrstaKorisnika vrstaKorisnika;
    private Boolean statusAktivnosti;

    public Korisnik() {
    }

    public Korisnik(int id, String ime, String prezime, String korisnickoIme, String lozinka, VrstaKorisnika vrstaKorisnika) {
        super(id);
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.vrstaKorisnika = vrstaKorisnika;
        this.statusAktivnosti = true;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public VrstaKorisnika getVrstaKorisnika() {
        return vrstaKorisnika;
    }

    public Boolean aktivan() {
        return statusAktivnosti;
    }

    public String getStatusAktivnostiKaoString() { return statusAktivnosti ? "true" : "false"; }
    public void setStatusAktivnosti(boolean statusAktivnosti) {
        this.statusAktivnosti = statusAktivnosti;
    }

    public Boolean getStatusAktivnosti() {
        return statusAktivnosti;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public void setVrstaKorisnika(VrstaKorisnika vrstaKorisnika) {
        this.vrstaKorisnika = vrstaKorisnika;
    }

    public void setStatusAktivnosti(Boolean statusAktivnosti) {
        this.statusAktivnosti = statusAktivnosti;
    }

    public abstract String formatiranZapisZaFajl();

    @Override
    public String toString() {
        return "Korisnik{" +
                "ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", korisnickoIme='" + korisnickoIme + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", vrstaKorisnika=" + vrstaKorisnika +
                ", statusAktivnosti=" + statusAktivnosti +
                '}';
    }
}
