package model;

import java.time.LocalDate;

public class Rezervacija extends Entitet{

    private LocalDate datumRezervacije;
    private Boolean aktivna;
    private IzdatPrimerak izdatPrimerak;
    private Clan clan;

    public Rezervacija() {
    }

    public Rezervacija(int id, LocalDate datumRezervacije, Boolean aktivna, IzdatPrimerak izdatPrimerak, Clan clan) {
        super(id);
        this.datumRezervacije = datumRezervacije;
        this.aktivna = aktivna;
        this.izdatPrimerak = izdatPrimerak;
        this.clan = clan;
    }

    public LocalDate getDatumRezervacije() {
        return datumRezervacije;
    }

    public Boolean aktivna() {
        return aktivna;
    }

    public IzdatPrimerak getIzdatPrimerak() {
        return izdatPrimerak;
    }

    public Clan getClan() {
        return clan;
    }

    public void setAktivna(Boolean aktivna) {
        this.aktivna = aktivna;
    }

    @Override
    public String formatiranZapisZaFajl() {
        return String.format("%d|%s|%s|%d|%d", this.getId(), this.getDatumRezervacije().toString(),
                this.aktivna(), this.getIzdatPrimerak().getId(), this.getClan().getId());
    }

    @Override
    public String toString() {
        return "Rezervacija{" +
                "datumRezervacije=" + datumRezervacije +
                ", aktivna=" + aktivna +
                ", izdatPrimerak=" + izdatPrimerak +
                ", clan=" + clan +
                '}';
    }
}
