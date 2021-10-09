package repozitorijum;

import com.sun.org.apache.xpath.internal.operations.Bool;
import model.Clan;
import model.Entitet;
import model.IzdatPrimerak;
import model.Rezervacija;

import java.time.LocalDate;

public class RezervacijaRepo extends GenerickiRepo<Rezervacija> {

    IzdatPrimerakRepo izdatPrimerakRepo;
    ClanRepo clanRepo;

    public RezervacijaRepo(String putanjaDoFajla, IzdatPrimerakRepo izdatPrimerakRepo, ClanRepo clanRepo) {
        super(putanjaDoFajla);
        this.izdatPrimerakRepo = izdatPrimerakRepo;
        this.clanRepo = clanRepo;
    }

    public void obrisiSveRezervacijePoId(Integer id){
        listaEntiteta.removeIf(p -> p.getIzdatPrimerak().getPrimerakKnjige().getKnjiga().getId() == id);
    }

    @Override
    protected Entitet kreirajEntitetIDodajUListu(String[] tokeni) {
        int id = Integer.parseInt(tokeni[0]);
        LocalDate datamRezervacije = LocalDate.parse(tokeni[1]);
        boolean aktivna = Boolean.parseBoolean(tokeni[2]);
        IzdatPrimerak izdatPrimerak = izdatPrimerakRepo.getById(Integer.parseInt(tokeni[3]));
        Clan clan = clanRepo.getById(Integer.parseInt(tokeni[4]));

        Rezervacija rezervacija = new Rezervacija(id, datamRezervacije, aktivna, izdatPrimerak, clan);
        dodajEntitet(rezervacija);
        return rezervacija;
    }
}
