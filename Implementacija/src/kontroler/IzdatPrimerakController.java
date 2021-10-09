package kontroler;

import com.sun.java.swing.plaf.windows.WindowsTextAreaUI;
import izuzeci.DuploProduzavanjeCitanjaZaPrimerak;
import izuzeci.PredjenLimitZaMaksBrojTrenutnoIzdatihPrimerakaClanu;
import model.Clan;
import model.IzdatPrimerak;
import model.PrimerakKnjige;
import model.enumeracije.RaspolozivostPrimerkaKnjige;
import model.enumeracije.VrstaOcene;
import repozitorijum.IzdatPrimerakRepo;
import repozitorijum.RezervacijaRepo;
import sun.util.resources.cldr.zh.CalendarData_zh_Hans_HK;
import utils.Konstante;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IzdatPrimerakController {

    private IzdatPrimerakRepo izdatPrimerakRepo;
    private RezervisanPrimerakController rezervisanPrimerakController;

    public IzdatPrimerakController(IzdatPrimerakRepo izdatPrimerakRepo, RezervisanPrimerakController rezervisanPrimerakController) {
        this.izdatPrimerakRepo = izdatPrimerakRepo;
        this.rezervisanPrimerakController = rezervisanPrimerakController;
    }



    public IzdatPrimerak getIzdatPrimerakById(int id) {
        return izdatPrimerakRepo.getById(id);
    }

    public void obrisiSvePrimerkePoId(Integer id){
        izdatPrimerakRepo.obrisiSveIzdatePrimerkePoId(id);
    }


    public List<IzdatPrimerak> getListaIzdatihPrimerakaNaCitanju() {
        List<IzdatPrimerak> retVal = new ArrayList<>();
        for (IzdatPrimerak izdatPrimerak : izdatPrimerakRepo.getListaEntiteta()) {
            if (!izdatPrimerak.vracen())
                retVal.add(izdatPrimerak);
        }
        return retVal;
    }

    public List<IzdatPrimerak> getListaProcitanihPrimeraka() {
        List<IzdatPrimerak> retVal = new ArrayList<>();
        for (IzdatPrimerak izdatPrimerak : izdatPrimerakRepo.getListaEntiteta()) {
            if (izdatPrimerak.vracen())
                retVal.add(izdatPrimerak);
        }
        return retVal;
    }

    public void oznaciPrimerakKaoProcitan(int id) {
        IzdatPrimerak izdatPrimerak = izdatPrimerakRepo.getById(id);
        izdatPrimerak.setVracen(true);
        izdatPrimerak.setDatumVracanja(LocalDate.now());

        if (rezervisanPrimerakController.izdatPrimerakJeRezervisan(id)) {
            izdatPrimerak.getPrimerakKnjige().setRaspolozivost(RaspolozivostPrimerkaKnjige.NA_CEKANJU_CLANA);
        } else {
            izdatPrimerak.getPrimerakKnjige().setRaspolozivost(RaspolozivostPrimerkaKnjige.SLOBODAN);
            izdatPrimerak.getPrimerakKnjige().getKnjiga().povecajBrojSlobodnih();
        }

    }

    public List<IzdatPrimerak> getListaIzdatihPrimerakaKojimaJeIstekaoRokVracanja() {
        List<IzdatPrimerak> retVal = new ArrayList<>();
        for (IzdatPrimerak izdatPrimerak : izdatPrimerakRepo.getListaEntiteta()) {
            if (!izdatPrimerak.vracen() && (LocalDate.now()).isAfter(izdatPrimerak.getOcekivaniDatumVracanja())) {
                retVal.add(izdatPrimerak);
            }
        }
        return retVal;
    }

    public Map<Integer, Integer> getKolikoPutaJeKojaKnjigaProcitanaUVremenskomIntervalu(LocalDate pocetak, LocalDate kraj) {
        Map<Integer, Integer> retVal = new HashMap<>();
        for (IzdatPrimerak izdatPrimerak : getListaProcitanihPrimeraka()) {
            if (izdatPrimerak.getDatumVracanja().compareTo(pocetak) >= 0 &&
                izdatPrimerak.getDatumVracanja().compareTo(kraj) <= 0)
            {
                int knjigaId = izdatPrimerak.getPrimerakKnjige().getKnjiga().getId();
                if (retVal.containsKey(knjigaId)) {
                    retVal.put(knjigaId, retVal.get(knjigaId) + 1);
                } else {
                    retVal.put(knjigaId, 1);
                }
            }
        }
        return retVal;
    }

    public void kreirajNoviIzdatiPrimerak(PrimerakKnjige primerakKnjige, Clan clan) {
        if (getIzdatiPrimerciKojeClanTrenutnoCita(clan.getId()).size() == Konstante.MAX_BROJ_KNJIGA_NA_CITANJU)
            throw new PredjenLimitZaMaksBrojTrenutnoIzdatihPrimerakaClanu();
        IzdatPrimerak izdatPrimerak = new IzdatPrimerak(izdatPrimerakRepo.generisanId(), LocalDate.now(), primerakKnjige, clan);
        izdatPrimerakRepo.dodajEntitet(izdatPrimerak);
        primerakKnjige.getKnjiga().smanjiBrojSlobodnih();
        primerakKnjige.setRaspolozivost(RaspolozivostPrimerkaKnjige.NA_CITANJU);
    }



    public List<IzdatPrimerak> getListaProcitanihKnjigaZaClana(int id) {
        List<IzdatPrimerak> retVal = new ArrayList<>();
        for (IzdatPrimerak izdatPrimerak : getListaProcitanihPrimeraka()) {
            if (izdatPrimerak.getClan().getId() == id)
                retVal.add(izdatPrimerak);
        }
        return retVal;
    }

    public List<VrstaOcene> getVrsteOcena() {
        List<VrstaOcene> retVal = new ArrayList<>();
        retVal.add(VrstaOcene.NEOCENJENO);
        retVal.add(VrstaOcene.NEDOVOLJNO);
        retVal.add(VrstaOcene.DOVOLJNO);
        retVal.add(VrstaOcene.DOBRO);
        retVal.add(VrstaOcene.VEOMA_DOBRO);
        retVal.add(VrstaOcene.ODLICNO);
        return retVal;
    }

    public List<IzdatPrimerak> getIzdatiPrimerciKojeClanTrenutnoCita(int idClana) {
        List<IzdatPrimerak> primerci = new ArrayList<>();
        for (IzdatPrimerak izdatPrimerak : getListaIzdatihPrimerakaNaCitanju()) {
            if (izdatPrimerak.getClan().getId() == idClana)
                primerci.add(izdatPrimerak);
        }
        return primerci;
    }

    public void produziCitanjeZaIzdatPrimerak(int idPrimerka) {
        IzdatPrimerak izdatPrimerak = getIzdatPrimerakById(idPrimerka);
        if (izdatPrimerak.produzenoCitanje())
            throw new DuploProduzavanjeCitanjaZaPrimerak();
        izdatPrimerak.setProduzenoCitanje(true);
        LocalDate noviDatumVracanja = izdatPrimerak.getOcekivaniDatumVracanja().plusDays(Konstante.BROJ_DANA_ZA_PRODUZENJE_CITANJA);
        izdatPrimerak.setOcekivaniDatumVracanja(noviDatumVracanja);
    }
}
