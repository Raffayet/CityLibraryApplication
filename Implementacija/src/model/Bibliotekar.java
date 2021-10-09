package model;

import model.enumeracije.VrstaBibliotekara;
import model.enumeracije.VrstaKorisnika;

import java.util.Objects;

public class Bibliotekar extends Korisnik {

    private VrstaBibliotekara vrstaBibliotekara;

    public Bibliotekar() {
    }

    public Bibliotekar(VrstaBibliotekara vrstaBibliotekara) {
        this.vrstaBibliotekara = vrstaBibliotekara;
    }

    public Bibliotekar(int id, String ime, String prezime, String korisnickoIme, String lozinka,
                       VrstaKorisnika vrstaKorisnika, VrstaBibliotekara vrstaBibliotekara) {
        super(id, ime, prezime, korisnickoIme, lozinka, vrstaKorisnika);
        this.vrstaBibliotekara = vrstaBibliotekara;
    }

    public VrstaBibliotekara getVrstaBibliotekara() {
        return vrstaBibliotekara;
    }

    public void setVrstaBibliotekara(VrstaBibliotekara vrstaBibliotekara) {
        this.vrstaBibliotekara = vrstaBibliotekara;
    }

    @Override
    public String formatiranZapisZaFajl() {
        return String.format("%d|B|%s|%s|%s|%s|%s|%s", this.getId(), this.getIme(), this.getPrezime(), this.getKorisnickoIme(),
                this.getLozinka(), this.getStatusAktivnostiKaoString(), this.getVrstaBibliotekara());
    }

    @Override
    public String toString() {
        return "Bibliotekar{" +
                super.toString() +
                "vrstaBibliotekara=" + vrstaBibliotekara +
                '}';
    }
}
