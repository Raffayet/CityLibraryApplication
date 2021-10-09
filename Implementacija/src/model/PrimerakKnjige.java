package model;

import model.enumeracije.FizickoStanjeKnjige;
import model.enumeracije.RaspolozivostPrimerkaKnjige;

import java.util.Objects;

public class PrimerakKnjige extends Entitet {

    private FizickoStanjeKnjige fizickoStanje;
    private RaspolozivostPrimerkaKnjige raspolozivost;
    private Knjiga knjiga;

    public PrimerakKnjige() {
    }

    @Override
    public String formatiranZapisZaFajl() {
        return String.format("%d|%s|%s|%d", this.getId(), this.getFizickoStanje(), this.getRaspolozivost(),
                this.getKnjiga().getId());
    }

    public PrimerakKnjige(int id, FizickoStanjeKnjige fizickoStanje, RaspolozivostPrimerkaKnjige raspolozivost) {
        super(id);
        this.fizickoStanje = fizickoStanje;
        this.raspolozivost = raspolozivost;
    }

    public PrimerakKnjige(int id, FizickoStanjeKnjige fizickoStanje, RaspolozivostPrimerkaKnjige raspolozivost, Knjiga knjiga) {
        super(id);
        this.fizickoStanje = fizickoStanje;
        this.raspolozivost = raspolozivost;
        this.knjiga = knjiga;
    }

    public PrimerakKnjige(int id, Knjiga knjiga){    //konstruktor za novi primerak
        super(id);
        this.fizickoStanje = FizickoStanjeKnjige.NOVA;
        this.raspolozivost = RaspolozivostPrimerkaKnjige.SLOBODAN;
        this.knjiga = knjiga;


    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public FizickoStanjeKnjige getFizickoStanje() {
        return fizickoStanje;
    }

    public RaspolozivostPrimerkaKnjige getRaspolozivost() {
        return raspolozivost;
    }

    public void setRaspolozivost(RaspolozivostPrimerkaKnjige raspolozivost) {
        this.raspolozivost = raspolozivost;
    }

    @Override
    public String toString() {
        return "PrimerakKnjige{" +
                "fizickoStanje=" + fizickoStanje +
                ", raspolozivost=" + raspolozivost +
                ", knjiga=" + knjiga +
                '}';
    }
}
