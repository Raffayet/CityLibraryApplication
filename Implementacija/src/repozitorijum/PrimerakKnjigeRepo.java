package repozitorijum;

import model.Entitet;
import model.Knjiga;
import model.PrimerakKnjige;
import model.enumeracije.FizickoStanjeKnjige;
import model.enumeracije.RaspolozivostPrimerkaKnjige;

public class PrimerakKnjigeRepo extends GenerickiRepo<PrimerakKnjige> {

    private KnjigeRepo knjigeRepo;

    public PrimerakKnjigeRepo(String putanjaDoFajla, KnjigeRepo knjigeRepo) {
        super(putanjaDoFajla);
        this.knjigeRepo = knjigeRepo;
    }

    @Override
    public void obrisiTrajnoEntitet(PrimerakKnjige entitet) {
        super.obrisiTrajnoEntitet(entitet);
    }

    public void obrisiSvePrimerkePoId(Integer id){
        listaEntiteta.removeIf(p -> p.getKnjiga().getId() == id);

    }

    @Override
    protected Entitet kreirajEntitetIDodajUListu(String[] tokeni) {
        int id = Integer.parseInt(tokeni[0]);
        FizickoStanjeKnjige fizickoStanjeKnjige = FizickoStanjeKnjige.valueOf(tokeni[1]);
        RaspolozivostPrimerkaKnjige raspolozivostPrimerkaKnjige = RaspolozivostPrimerkaKnjige.valueOf(tokeni[2]);
        Knjiga knjiga = knjigeRepo.getById(Integer.parseInt(tokeni[3]));

        PrimerakKnjige primerakKnjige = new PrimerakKnjige(id, fizickoStanjeKnjige, raspolozivostPrimerkaKnjige, knjiga);
        dodajEntitet(primerakKnjige);
        return primerakKnjige;
    }
}
