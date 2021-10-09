package kontroler;

import izuzeci.NedostajucaVrednost;
import izuzeci.OdabirDatumaIzuzetak;

import javax.swing.*;
import java.time.LocalDate;

public class DatumiController {

    public DatumiController() {

    }

    public void validiraj(LocalDate pocetak, LocalDate kraj) {
        if (pocetak == null || kraj == null) {
            throw new NedostajucaVrednost("Niste selektovali datume");
        }
        else if (!datumJeProslost(pocetak) || !datumJeProslost(kraj)) {
            throw new OdabirDatumaIzuzetak("Za početni i kranji datum morate izabrati neki od prošlih datuma.");
        }
        else if (pocetak.compareTo(kraj) >= 0) {
            throw new OdabirDatumaIzuzetak("Početni datum mora biti starije od krajnjeg.");
        }
    }

    private boolean datumJeProslost(LocalDate localDate) {
        LocalDate today = LocalDate.now();
        return today.isAfter(localDate);
    }
}
