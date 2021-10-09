package repozitorijum;

import model.Clanarina;
import model.Entitet;
import model.enumeracije.TipClanarine;

import java.time.LocalDate;

public class ClanarinaRepo extends GenerickiRepo<Clanarina> {

    public ClanarinaRepo(String putanjaDoFajla) {
        super(putanjaDoFajla);
    }

    @Override
    protected Entitet kreirajEntitetIDodajUListu(String[] tokeni) {
        int id = Integer.parseInt(tokeni[0]);
        LocalDate datumOdKadVazi = LocalDate.parse(tokeni[1]);
        TipClanarine tipClanarine = TipClanarine.valueOf(tokeni[2]);
        int cena = Integer.parseInt(tokeni[3]);
        Clanarina c = new Clanarina(id, datumOdKadVazi, tipClanarine, cena);
        dodajEntitet(c);
        return c;
    }

}
