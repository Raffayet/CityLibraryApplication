package kontroler;

import izuzeci.GodisnjaClanarinaJosNijeOdredjena;
import izuzeci.PolugodisnjaClanarinaJosNijeOdredjena;
import model.Clanarina;
import model.enumeracije.TipClanarine;
import repozitorijum.ClanarinaRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClanarinaController {

    private final ClanarinaRepo clanarinaRepo;

    public ClanarinaController(ClanarinaRepo clanarinaRepo) {
        this.clanarinaRepo = clanarinaRepo;
    }

    public Clanarina kreiranjeClanarine(LocalDate datumOdKogVazi, TipClanarine tipClanarine, int cena){

        Clanarina clanarina = new Clanarina(clanarinaRepo.generisanId(), datumOdKogVazi, tipClanarine, cena);
        clanarinaRepo.dodajEntitet(clanarina);
        return clanarina;
    }

    private void sortirajClanarinePoDatumu() {
        clanarinaRepo.getListaEntiteta().sort(new Comparator<Clanarina>() {
            // u obrnutom poretku se sortira
            @Override
            public int compare(Clanarina c1, Clanarina c2) {
                return c2.getDatumOdKogVazi().compareTo(c1.getDatumOdKogVazi());
            }
        });
    }

    private Clanarina getTrenutnoVazecaGodisnjaClanarina() {
        sortirajClanarinePoDatumu();
        for (Clanarina c : clanarinaRepo.getListaEntiteta()) {
            if (c.getTipClanarine() == TipClanarine.GODISNJA)
                return c;
        }
        throw new GodisnjaClanarinaJosNijeOdredjena();
    }

    private Clanarina getTrenutnoVazecaPolugodisnjaClanarina() {
        sortirajClanarinePoDatumu();
        for (Clanarina c : clanarinaRepo.getListaEntiteta()) {
            if (c.getTipClanarine() == TipClanarine.POLUGODISNJA)
                return c;
        }
        throw new PolugodisnjaClanarinaJosNijeOdredjena();
    }

    public List<TipClanarine> getTipoviClanarina() {
        List<TipClanarine> retVal = new ArrayList<>();
        retVal.add(TipClanarine.POLUGODISNJA);
        retVal.add(TipClanarine.GODISNJA);
        return retVal;
    }

    public Clanarina getTrenutnaClanarinaNaOsnovuTipa(TipClanarine tipClanarine) {
        if (tipClanarine == TipClanarine.GODISNJA)
            return getTrenutnoVazecaGodisnjaClanarina();
        return getTrenutnoVazecaPolugodisnjaClanarina();
    }
}
