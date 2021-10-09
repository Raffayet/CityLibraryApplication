package kontroler;

import izuzeci.RezervacijeNijeMoguca;
import izuzeci.NijeMoguceIzdatiRezervisanPrimerakJerJosNijeVracen;
import izuzeci.PredjenLimitZaMaksBrojTrenutnoRezervisanihPrimeraka;
import model.*;
import model.enumeracije.RaspolozivostPrimerkaKnjige;
import repozitorijum.IzdatPrimerakRepo;
import repozitorijum.RezervacijaRepo;
import utils.Konstante;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RezervisanPrimerakController {

    RezervacijaRepo rezervacijaRepo;
    IzdatPrimerakRepo izdatPrimerakRepo;

    public RezervisanPrimerakController(RezervacijaRepo rezervacijaRepo, IzdatPrimerakRepo izdatPrimerakRepo) {
        this.rezervacijaRepo = rezervacijaRepo;
        this.izdatPrimerakRepo = izdatPrimerakRepo;
    }

    public List<Rezervacija> getListaAktivnihRezervacija() {
        List<Rezervacija> retVal = new ArrayList<>();
        for (Rezervacija r : rezervacijaRepo.getListaEntiteta()) {
            if (r.aktivna())
                retVal.add(r);
        }
        return retVal;
    }
    public void izbrisiSveREzervisanePoId(Integer id){
        rezervacijaRepo.obrisiSveRezervacijePoId(id);
    }

    public List<IzdatPrimerak> getListaIzdatihPrimerakaKojiSuTrenutnoRezervisani() {
        List<IzdatPrimerak> retVal = new ArrayList<>();
        for (Rezervacija r : getListaAktivnihRezervacija()) {
            retVal.add(izdatPrimerakRepo.getById(r.getIzdatPrimerak().getId()));
        }
        return retVal;
    }


    public Rezervacija getRezervacijaZaIzdatPrimerak(int idIzdatogPrimerka) {
        for (Rezervacija r : getListaAktivnihRezervacija()) {
            if (r.getIzdatPrimerak().getId() == idIzdatogPrimerka)
                return r;
        }
        return null;
    }

    public boolean izdatPrimerakJeRezervisan(int idIzdatogPrimerka) {
        return getRezervacijaZaIzdatPrimerak(idIzdatogPrimerka) != null;
    }

    public Clan getClanKojiJeRezevisaoIzdatPrimerak(int idIzdatogPrimerka) {
        if (izdatPrimerakJeRezervisan(idIzdatogPrimerka)) {
            return getRezervacijaZaIzdatPrimerak(idIzdatogPrimerka).getClan();
        }
        return null;
    }

    public List<IzdatPrimerak> getIzdatiPrimerciKojeJeClanRezervisao(int idClana) {
        List<IzdatPrimerak> retVal = new ArrayList<>();
        for (Rezervacija rezervacija : getListaAktivnihRezervacija()) {
            if (rezervacija.getClan().getId() == idClana)
                retVal.add(rezervacija.getIzdatPrimerak());
        }
        return retVal;
    }

    public void oznaciDaJeRezervacijaPreuzetaIizdajPrimerakClanu(int idRezervacije) {
        Rezervacija rezervacija = rezervacijaRepo.getById(idRezervacije);
        if (rezervacija.getIzdatPrimerak().getPrimerakKnjige().getRaspolozivost() != RaspolozivostPrimerkaKnjige.NA_CEKANJU_CLANA)
            throw new NijeMoguceIzdatiRezervisanPrimerakJerJosNijeVracen();

        rezervacija.setAktivna(false);
        Knjiga knjiga = rezervacija.getIzdatPrimerak().getPrimerakKnjige().getKnjiga();
        knjiga.smanjiBrojRezervisanih();

        PrimerakKnjige primerakKnjige = rezervacija.getIzdatPrimerak().getPrimerakKnjige();
        primerakKnjige.setRaspolozivost(RaspolozivostPrimerkaKnjige.NA_CITANJU);
        Clan clan = rezervacija.getClan();
        IzdatPrimerak izdatPrimerak = new IzdatPrimerak(izdatPrimerakRepo.generisanId(), LocalDate.now(), primerakKnjige, clan);
        izdatPrimerakRepo.dodajEntitet(izdatPrimerak);
    }

    public void rezervisiPrimerakKnjigeZaClana(Clan clan, Knjiga knjiga) {
        if (getIzdatiPrimerciKojeJeClanRezervisao(clan.getId()).size() == Konstante.MAX_BROJ_REZERVISANIH_KNJIGA)
            throw new PredjenLimitZaMaksBrojTrenutnoRezervisanihPrimeraka();
        if (knjiga.getBrojSlobodnih() == knjiga.getUkupanBroj())
            throw new RezervacijeNijeMoguca("Sve primerci knjige su slobodni");
        if (knjiga.getBrojIdatih() == knjiga.getBrojRezervisanih())
            throw new RezervacijeNijeMoguca("Sve primerci knjige su rezervisani");

        IzdatPrimerak izdatPrimerak = getIzdatPrimerakZaRezervaciju(knjiga);
        izdatPrimerak.getPrimerakKnjige().setRaspolozivost(RaspolozivostPrimerkaKnjige.REZERVISAN);
        izdatPrimerak.getPrimerakKnjige().getKnjiga().povecajBrojRezervisanih();

        Rezervacija rezervacija = new Rezervacija(rezervacijaRepo.generisanId(), LocalDate.now(), true, izdatPrimerak, clan);
        rezervacijaRepo.dodajEntitet(rezervacija);
    }

    private void sortirajIzdatePrimerkePoDatumu() {
        izdatPrimerakRepo.getListaEntiteta().sort(Comparator.comparing(IzdatPrimerak::getOcekivaniDatumVracanja));
    }

    private IzdatPrimerak getIzdatPrimerakZaRezervaciju(Knjiga knjiga) {
        // tezi se da se rezervise primerak koji se ocekuje da najbrze bude vracen u biblioteku
        sortirajIzdatePrimerkePoDatumu();

        for (IzdatPrimerak izdatPrimerak : izdatPrimerakRepo.getListaEntiteta()) {
            int knjigaId = izdatPrimerak.getPrimerakKnjige().getKnjiga().getId();
            if (!izdatPrimerak.vracen() && !izdatPrimerakJeRezervisan(izdatPrimerak.getId()) && knjigaId == knjiga.getId())
                return izdatPrimerak;
        }
        return null;
    }


    public void ponistiSveIstekleRezervacije() {
        for (Rezervacija rezervacija : getListaAktivnihRezervacija()) {
            IzdatPrimerak izdatPrimerak = rezervacija.getIzdatPrimerak();
            if (izdatPrimerak.vracen()) {
                LocalDate prvi = izdatPrimerak.getDatumVracanja().plusDays(Konstante.BROJ_DANA_ZA_DOLAZAK_PO_REZ_KNJIGU+1);
                if (prvi.compareTo(LocalDate.now()) <= 0)
                    rezervacija.setAktivna(false);
            }
        }
    }
}
