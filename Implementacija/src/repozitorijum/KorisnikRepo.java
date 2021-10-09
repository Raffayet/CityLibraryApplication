package repozitorijum;

import model.Bibliotekar;
import model.Clan;
import model.Entitet;
import model.Korisnik;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class KorisnikRepo extends GenerickiRepo<Korisnik> {

    private ClanRepo clanRepo;
    private BibliotekarRepo bibliotekarRepo;

    public KorisnikRepo(String putanjaDoFajla, ClanRepo clanRepo, BibliotekarRepo bibliotekarRepo) {
        super(putanjaDoFajla);
        this.clanRepo = clanRepo;
        this.bibliotekarRepo = bibliotekarRepo;
    }

    public KorisnikRepo(String putanjaDoFajla, ClanRepo clanRepo) {
        super(putanjaDoFajla);
        this.clanRepo = clanRepo;
    }

    public KorisnikRepo(String putanjaDoFajla, BibliotekarRepo bibliotekarRepo) {
        super(putanjaDoFajla);
        this.bibliotekarRepo = bibliotekarRepo;
    }

    @Override
    protected Entitet kreirajEntitetIDodajUListu(String[] tokeni) {
        Entitet e = null;
        switch(tokeni[1]) {
            case "C":
                e = this.clanRepo.kreirajEntitetIDodajUListu(tokeni);
                break;
            case "B":
                e = this.bibliotekarRepo.kreirajEntitetIDodajUListu(tokeni);
                break;
        }
        dodajEntitet((Korisnik) e);
        return e;
    }
}
