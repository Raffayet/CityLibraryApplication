package repozitorijum;

import model.Clan;
import model.Entitet;
import model.IzdatPrimerak;
import model.PrimerakKnjige;
import model.enumeracije.VrstaOcene;

import java.time.LocalDate;

public class IzdatPrimerakRepo extends GenerickiRepo<IzdatPrimerak> {

    PrimerakKnjigeRepo primerakKnjigeRepo;
    ClanRepo clanRepo;

    public IzdatPrimerakRepo(String putanjaDoFajla, PrimerakKnjigeRepo primerakKnjigeRepo, ClanRepo clanRepo) {
        super(putanjaDoFajla);
        this.primerakKnjigeRepo = primerakKnjigeRepo;
        this.clanRepo = clanRepo;
    }

    @Override
    public void obrisiTrajnoEntitet(IzdatPrimerak entitet) {
        super.obrisiTrajnoEntitet(entitet);
    }

    public void obrisiSveIzdatePrimerkePoId(Integer id){
        listaEntiteta.removeIf(p -> p.getPrimerakKnjige().getKnjiga().getId() == id);
    }

    @Override
    protected Entitet kreirajEntitetIDodajUListu(String[] tokeni) {
        IzdatPrimerak izdatPrimerak = null;
        int id = Integer.parseInt(tokeni[0]);
        LocalDate datumIzdavanja = LocalDate.parse(tokeni[1]);
        PrimerakKnjige primerakKnjige = primerakKnjigeRepo.getById(Integer.parseInt(tokeni[2]));
        Clan clan = clanRepo.getById(Integer.parseInt(tokeni[3]));
        boolean produzenjeCitanja = Boolean.parseBoolean(tokeni[4]);

        if (tokeni.length == 5) {
            izdatPrimerak = new IzdatPrimerak(id, datumIzdavanja, primerakKnjige, clan, produzenjeCitanja);
        }
        else if (tokeni.length == 6) {
            LocalDate datumVracanja = LocalDate.parse(tokeni[5]);
            izdatPrimerak = new IzdatPrimerak(id, datumIzdavanja, primerakKnjige, clan, produzenjeCitanja, datumVracanja);
        }
        else if (tokeni.length == 7) {
            LocalDate datumVracanja = LocalDate.parse(tokeni[5]);
            VrstaOcene vrstaOcene = VrstaOcene.valueOf(tokeni[6]);
            izdatPrimerak = new IzdatPrimerak(id, datumIzdavanja, primerakKnjige, clan, produzenjeCitanja, datumVracanja, vrstaOcene);
        }
        dodajEntitet(izdatPrimerak);
        return izdatPrimerak;
    }
}
