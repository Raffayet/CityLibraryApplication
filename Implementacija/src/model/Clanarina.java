package model;

import model.enumeracije.TipClanarine;

import java.time.LocalDate;

public class Clanarina extends Entitet{

    private LocalDate datumOdKogVazi;
    private TipClanarine tipClanarine;
    private int cena;

    public Clanarina() {
    }

    public Clanarina(int id, LocalDate datumOdKogVazi, TipClanarine tipClanarine, int cena) {
        super(id);
        this.datumOdKogVazi = datumOdKogVazi;
        this.tipClanarine = tipClanarine;
        this.cena = cena;
    }

    public LocalDate getDatumOdKogVazi() {
        return datumOdKogVazi;
    }

    public TipClanarine getTipClanarine() {
        return tipClanarine;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) { this.cena = cena; }

    public void setDatumOdKogVazi(LocalDate datumOdKogVazi) {
        this.datumOdKogVazi = datumOdKogVazi;
    }

    @Override
    public String toString() {
        return "Clanarina{" +
                "datumOdKogVazi=" + datumOdKogVazi +
                ", tipClanarine=" + tipClanarine +
                ", cena=" + cena +
                "} ";
    }

    @Override
    public String formatiranZapisZaFajl() {
        return String.format("%d|%s|%s|%d", this.getId(), this.getDatumOdKogVazi().toString(),
                this.getTipClanarine(), this.getCena());
    }

}
