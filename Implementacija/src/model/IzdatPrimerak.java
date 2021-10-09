package model;

import model.enumeracije.VrstaOcene;
import utils.Konstante;

import java.time.LocalDate;

public class IzdatPrimerak extends Entitet {

    private LocalDate datumIzdavanja;

    private LocalDate ocekivaniDatumVracanja;

    private LocalDate datumVracanja;

    private Boolean vracena;
    private Boolean produzenoCitanje;
    private VrstaOcene ocena;
    private PrimerakKnjige primerakKnjige;
    private Clan clan;

    public IzdatPrimerak() {
    }

    public IzdatPrimerak(int id, LocalDate datumIzdavanja, PrimerakKnjige primerakKnjige, Clan clan) {
        super(id);
        this.datumIzdavanja = datumIzdavanja;
        this.primerakKnjige = primerakKnjige;
        this.clan = clan;
        this.ocena = VrstaOcene.NEOCENJENO;
        this.vracena = false;
        this.produzenoCitanje = false;
        this.ocekivaniDatumVracanja = datumIzdavanja.plusDays(Konstante.BROJ_DANA_ZA_PRODUZENJE_CITANJA);
    }

    public IzdatPrimerak(int id, LocalDate datumIzdavanja, PrimerakKnjige primerakKnjige, Clan clan, boolean produzenoCitanje) {
        super(id);
        this.datumIzdavanja = datumIzdavanja;
        this.primerakKnjige = primerakKnjige;
        this.clan = clan;
        this.ocena = VrstaOcene.NEOCENJENO;
        this.vracena = false;
        this.produzenoCitanje = produzenoCitanje;
        if (produzenoCitanje) {
            this.ocekivaniDatumVracanja = datumIzdavanja.plusDays(Konstante.BROJ_DANA_ZA_PRODUZENJE_CITANJA * 2);
        } else {
            this.ocekivaniDatumVracanja = datumIzdavanja.plusDays(Konstante.BROJ_DANA_ZA_PRODUZENJE_CITANJA);
        }
    }

    public IzdatPrimerak(int id, LocalDate datumIzdavanja, PrimerakKnjige primerakKnjige, Clan clan, boolean produzenoCitanje, LocalDate datumVracanja) {
        super(id);
        this.datumIzdavanja = datumIzdavanja;
        this.primerakKnjige = primerakKnjige;
        this.clan = clan;
        this.ocena = VrstaOcene.NEOCENJENO;
        this.vracena = true;
        this.produzenoCitanje = produzenoCitanje;
        if (produzenoCitanje) {
            this.ocekivaniDatumVracanja = datumIzdavanja.plusDays(Konstante.BROJ_DANA_ZA_PRODUZENJE_CITANJA * 2);
        } else {
            this.ocekivaniDatumVracanja = datumIzdavanja.plusDays(Konstante.BROJ_DANA_ZA_PRODUZENJE_CITANJA);
        }
        this.datumVracanja = datumVracanja;
    }

    public IzdatPrimerak(int id, LocalDate datumIzdavanja, PrimerakKnjige primerakKnjige, Clan clan, boolean produzenoCitanje, LocalDate datumVracanja, VrstaOcene ocena) {
        super(id);
        this.datumIzdavanja = datumIzdavanja;
        this.primerakKnjige = primerakKnjige;
        this.clan = clan;
        this.ocena = ocena;
        this.vracena = true;
        this.produzenoCitanje = produzenoCitanje;
        if (produzenoCitanje) {
            this.ocekivaniDatumVracanja = datumIzdavanja.plusDays(Konstante.BROJ_DANA_ZA_PRODUZENJE_CITANJA * 2);
        } else {
            this.ocekivaniDatumVracanja = datumIzdavanja.plusDays(Konstante.BROJ_DANA_ZA_PRODUZENJE_CITANJA);
        }
        this.datumVracanja = datumVracanja;
    }

    @Override
    public String formatiranZapisZaFajl() {
        if (!vracena) {
            return String.format("%d|%s|%d|%d|%s", this.getId(), this.getDatumIzdavanja().toString(),
                    this.getPrimerakKnjige().getId(), this.getClan().getId(), this.produzenoCitanje());
        }
        if (ocena == VrstaOcene.NEOCENJENO) {
            return String.format("%d|%s|%d|%d|%s|%s", this.getId(), this.getDatumIzdavanja().toString(),
                    this.getPrimerakKnjige().getId(), this.getClan().getId(),
                    this.produzenoCitanje(), this.getDatumVracanja().toString());
        }
        return String.format("%d|%s|%d|%d|%s|%s|%s", this.getId(), this.getDatumIzdavanja().toString(),
                this.getPrimerakKnjige().getId(), this.getClan().getId(),
                this.produzenoCitanje(), this.getDatumVracanja().toString(), this.getOcena());
    }



    public LocalDate getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public Boolean produzenoCitanje() {
        return produzenoCitanje;
    }

    public LocalDate getOcekivaniDatumVracanja() {
        return ocekivaniDatumVracanja;
    }

    public LocalDate getDatumVracanja() {
        return datumVracanja;
    }

    public Boolean vracen() {
        return vracena;
    }

    public VrstaOcene getOcena() {
        return ocena;
    }

    public PrimerakKnjige getPrimerakKnjige() {
        return primerakKnjige;
    }

    public Clan getClan() {
        return clan;
    }

    public void setProduzenoCitanje(Boolean produzenoCitanje) {
        this.produzenoCitanje = produzenoCitanje;
    }

    public void setVracen(Boolean vracena) {
        this.vracena = vracena;
    }

    public void setDatumVracanja(LocalDate datumVracanja) {
        this.datumVracanja = datumVracanja;
    }

    public void setOcekivaniDatumVracanja(LocalDate ocekivaniDatumVracanja) {
        this.ocekivaniDatumVracanja = ocekivaniDatumVracanja;
    }

    public void setOcena(VrstaOcene vrstaOcene) {
        this.ocena = vrstaOcene;
    }

    @Override
    public String toString() {
        return "IzdatPrimerak{" +
                "datumIzdavanja=" + datumIzdavanja +
                ", ocekivaniDatumVracanja=" + ocekivaniDatumVracanja +
                ", datumVracanja=" + datumVracanja +
                ", vracena=" + vracena +
                ", produzenoCitanje=" + produzenoCitanje +
                ", ocena=" + ocena +
                ", primerakKnjige=" + primerakKnjige +
                ", clan=" + clan +
                '}';
    }
}
