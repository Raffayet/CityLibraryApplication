package kontroler;

import model.Clan;
import model.Clanarina;
import model.IzdataClanarina;
import repozitorijum.IzdataClanarinaRepo;

import java.lang.reflect.AnnotatedArrayType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IzdataClanarinaController {

    private final IzdataClanarinaRepo izdataClanarinaRepo;

    public IzdataClanarinaController(IzdataClanarinaRepo izdataClanarinaRepo) {
        this.izdataClanarinaRepo = izdataClanarinaRepo;
    }

    public void izdavanjeClanarine(Clanarina clanarina, Clan clan){

        IzdataClanarina izdataClanarina = new IzdataClanarina(izdataClanarinaRepo.generisanId(), LocalDate.now(), clanarina, clan);
        izdataClanarinaRepo.dodajEntitet(izdataClanarina);
        clan.setTrenutnaClanarina(izdataClanarina);
        clan.setTrenutnaClanarinaId(izdataClanarina.getId());
    }

    public void produzenjeClanarine(int id){

        IzdataClanarina izdataClanarina = izdataClanarinaRepo.getById(id);
        izdataClanarina.setDatumIzdavanja(LocalDate.now());
        izdataClanarina.getClanarina().setDatumOdKogVazi(LocalDate.now());
    }

    public List<IzdataClanarina> getIzdateClanarineZaClana(int id) {
        List<IzdataClanarina> retVal = new ArrayList<>();
        for (IzdataClanarina izdataClanarina : izdataClanarinaRepo.getListaEntiteta()) {
            if (izdataClanarina.getClan().getId() == id)
                retVal.add(izdataClanarina);
        }
        return retVal;
    }
}
