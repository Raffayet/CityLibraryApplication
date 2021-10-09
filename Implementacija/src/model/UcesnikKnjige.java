package model;

import model.enumeracije.TipAutorstva;

import java.util.Objects;

public class UcesnikKnjige extends Entitet{

    private String ime, prezime;
    private TipAutorstva tipAutorstva;

    public UcesnikKnjige() {
    }

    public UcesnikKnjige(int id, String ime, String prezime, TipAutorstva tipAutorstva) {
        super(id);
        this.ime = ime;
        this.prezime = prezime;
        this.tipAutorstva = tipAutorstva;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public TipAutorstva getTipAutorstva() {
        return tipAutorstva;
    }


    @Override
    public String formatiranZapisZaFajl() {
        return String.format("%d|%s|%s|%s", this.getId(), this.getIme(), this.getPrezime(), this.getTipAutorstva());
    }

    @Override
    public String toString() {
        return "UcesnikKnjige{" +
                "ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", tipAutorstva=" + tipAutorstva +
                '}';
    }
}
