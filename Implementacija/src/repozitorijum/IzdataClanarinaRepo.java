package repozitorijum;

import jdk.internal.org.objectweb.asm.tree.LocalVariableAnnotationNode;
import model.Clan;
import model.Clanarina;
import model.Entitet;
import model.IzdataClanarina;
import utils.Konstante;

import java.time.LocalDate;

public class IzdataClanarinaRepo extends GenerickiRepo<IzdataClanarina> {

    private ClanarinaRepo clanarinaRepo;
    private ClanRepo clanRepo;

    public IzdataClanarinaRepo(String putanjaDoFajla, ClanarinaRepo clanarinaRepo, ClanRepo clanRepo) {
        super(putanjaDoFajla);
        this.clanarinaRepo = clanarinaRepo;
        this.clanRepo =  clanRepo;
    }

    @Override
    protected Entitet kreirajEntitetIDodajUListu(String[] tokeni) {
        int id = Integer.parseInt(tokeni[0]);
        LocalDate datumIzdavanja = LocalDate.parse(tokeni[1]);
        int clanarinaId = Integer.parseInt(tokeni[2]);
        Clanarina clanarina = clanarinaRepo.getById(clanarinaId);
        int clanId = Integer.parseInt(tokeni[3]);
        Clan clan = clanRepo.getById(clanId);

        IzdataClanarina izdataClanarina = new IzdataClanarina(id ,datumIzdavanja, clanarina, clan);
        dodajEntitet(izdataClanarina);
        return izdataClanarina;
    }
}
