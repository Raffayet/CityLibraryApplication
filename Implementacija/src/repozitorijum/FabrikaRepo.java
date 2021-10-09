package repozitorijum;

import utils.Konstante;

public class FabrikaRepo {
    private BibliotekarRepo bibliotekarRepo;
    private ClanRepo clanRepo;
    private KorisnikRepo korisnikRepo;
    private ClanarinaRepo clanarinaRepo;
    private IzdataClanarinaRepo izdataClanarinaRepo;
    private KategorijeKnjigaRepo kategorijeKnjigaRepo;
    private UcesnikKnjigeRepo ucesnikKnjigeRepo;
    private KnjigeRepo knjigeRepo;
    private PrimerakKnjigeRepo primerakKnjigeRepo;
    private IzdatPrimerakRepo izdatPrimerakRepo;
    private RezervacijaRepo rezervacijaRepo;

    public FabrikaRepo() {
        bibliotekarRepo = new BibliotekarRepo(Konstante.FAJL_KORISNICI);
        clanRepo = new ClanRepo(Konstante.FAJL_KORISNICI);
        korisnikRepo = new KorisnikRepo(Konstante.FAJL_KORISNICI, clanRepo, bibliotekarRepo);
        clanarinaRepo = new ClanarinaRepo(Konstante.FAJL_CLANARINE);
        izdataClanarinaRepo = new IzdataClanarinaRepo(Konstante.FAJL_IZDATE_CLANARINE, clanarinaRepo, clanRepo);
        kategorijeKnjigaRepo = new KategorijeKnjigaRepo(Konstante.FAJL_KATEGORIJE_KNJIGE);
        ucesnikKnjigeRepo = new UcesnikKnjigeRepo(Konstante.FAJL_UCESNICI_KNJIGE);
        knjigeRepo = new KnjigeRepo(Konstante.FAJL_KNJIGE, ucesnikKnjigeRepo, kategorijeKnjigaRepo);
        primerakKnjigeRepo = new PrimerakKnjigeRepo(Konstante.FAJL_PRIMERCI_KNJIGE, knjigeRepo);
        izdatPrimerakRepo = new IzdatPrimerakRepo(Konstante.FAJL_IZDATI_PRIMRCI, primerakKnjigeRepo, clanRepo);
        rezervacijaRepo = new RezervacijaRepo(Konstante.FAJL_REZERVACIJE, izdatPrimerakRepo, clanRepo);
    }

    public void ucitajPodatke() {
        korisnikRepo.ucitajPodatke();
        clanarinaRepo.ucitajPodatke();
        izdataClanarinaRepo.ucitajPodatke();
        clanRepo.postaviVezuSaIzdatimClanarinama(izdataClanarinaRepo); // vazno zbog bidirekcione veze
        ucesnikKnjigeRepo.ucitajPodatke();
        kategorijeKnjigaRepo.ucitajPodatke();
        kategorijeKnjigaRepo.postaviVezuIzmedjuKategorijaIPodKategorija();
        knjigeRepo.ucitajPodatke();
        primerakKnjigeRepo.ucitajPodatke();
        knjigeRepo.postaviVezuIzmedjuKnjigeIPrimeraka(primerakKnjigeRepo); // dodajemo bidirekcionu vezu
        izdatPrimerakRepo.ucitajPodatke();
        rezervacijaRepo.ucitajPodatke();
    }

    public void sacuvajPodatke() {
        korisnikRepo.sacuvajPodatke();
        clanarinaRepo.sacuvajPodatke();
        izdataClanarinaRepo.sacuvajPodatke();
        ucesnikKnjigeRepo.sacuvajPodatke();
        kategorijeKnjigaRepo.sacuvajPodatke();
        knjigeRepo.sacuvajPodatke();
        primerakKnjigeRepo.sacuvajPodatke();
        izdatPrimerakRepo.sacuvajPodatke();
        rezervacijaRepo.sacuvajPodatke();
    }

    public BibliotekarRepo getBibliotekarRepo() {
        return bibliotekarRepo;
    }

    public ClanRepo getClanRepo() {
        return clanRepo;
    }

    public KorisnikRepo getKorisnikRepo() {
        return korisnikRepo;
    }

    public ClanarinaRepo getClanarinaRepo() {
        return clanarinaRepo;
    }

    public IzdataClanarinaRepo getIzdataClanarinaRepo() {
        return izdataClanarinaRepo;
    }

    public KategorijeKnjigaRepo getKategorijeKnjigaRepo() {
        return kategorijeKnjigaRepo;
    }

    public UcesnikKnjigeRepo getUcesnikKnjigeRepo() {
        return ucesnikKnjigeRepo;
    }

    public KnjigeRepo getKnjigeRepo() {
        return knjigeRepo;
    }

    public PrimerakKnjigeRepo getPrimerakKnjigeRepo() {
        return primerakKnjigeRepo;
    }

    public IzdatPrimerakRepo getIzdatPrimerakRepo() {
        return izdatPrimerakRepo;
    }

    public RezervacijaRepo getRezervacijaRepo() {
        return rezervacijaRepo;
    }
}
