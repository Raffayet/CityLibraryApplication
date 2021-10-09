package kontroler;

import model.PrimerakKnjige;
import repozitorijum.PrimerakKnjigeRepo;

public class PrimerakKnjigeController {

    private PrimerakKnjigeRepo primerakKnjigeRepo;

    public PrimerakKnjigeController(PrimerakKnjigeRepo primerakKnjigeRepo) {
        this.primerakKnjigeRepo = primerakKnjigeRepo;
    }

    public void obrisiSvePrierkePoId(Integer id ){
        primerakKnjigeRepo.obrisiSvePrimerkePoId(id);

    }


}

