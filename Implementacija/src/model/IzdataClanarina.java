package model;

import java.time.LocalDate;

public class IzdataClanarina extends Entitet {

    private LocalDate datumIzdavanja;
    private Clanarina clanarina;
    private Clan clan;

    public IzdataClanarina() {
    }

    @Override
    public String formatiranZapisZaFajl() {
        return String.format("%d|%s|%d|%d", this.getId(), this.getDatumIzdavanja().toString(),
                this.getClanarina().getId(), this.getClan().getId());
    }

    public IzdataClanarina(int id, LocalDate datumIzdavanja, Clanarina clanarina, Clan clan) {
        super(id);
        this.datumIzdavanja = datumIzdavanja;
        this.clanarina = clanarina;
        this.clan = clan;
    }

    public LocalDate getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public Clanarina getClanarina() {
        return clanarina;
    }

    public Clan getClan() {
        return clan;
    }


    public void setDatumIzdavanja(LocalDate datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    @Override
    public String toString() {
        return "IzdataClanarina{" +
                "datumIzdavanja=" + datumIzdavanja +
                ", clanarina=" + clanarina +
                ", clan=" + clan +
                '}';
    }
}
