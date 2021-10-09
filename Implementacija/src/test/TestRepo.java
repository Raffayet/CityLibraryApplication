package test;

import repozitorijum.*;
import utils.Konstante;

public class TestRepo {
    public static void main(String[] args) {
        ClanRepo clanRepo = new ClanRepo(Konstante.FAJL_KORISNICI);
        BibliotekarRepo bibliotekarRepo = new BibliotekarRepo(Konstante.FAJL_KORISNICI);
        KorisnikRepo korisnikRepo = new KorisnikRepo(Konstante.FAJL_KORISNICI, clanRepo, bibliotekarRepo);
        korisnikRepo.ucitajPodatke();
        korisnikRepo.ispisiPodatke();
        korisnikRepo.sacuvajPodatke();

        ClanarinaRepo clanarinaRepo = new ClanarinaRepo(Konstante.FAJL_CLANARINE);
        clanarinaRepo.ucitajPodatke();
        clanarinaRepo.ispisiPodatke();
        clanarinaRepo.sacuvajPodatke();

        IzdataClanarinaRepo izdataClanarinaRepo = new IzdataClanarinaRepo(Konstante.FAJL_IZDATE_CLANARINE, clanarinaRepo, clanRepo);
        izdataClanarinaRepo.ucitajPodatke();
        izdataClanarinaRepo.ispisiPodatke();
        izdataClanarinaRepo.sacuvajPodatke();

        clanRepo.postaviVezuSaIzdatimClanarinama(izdataClanarinaRepo); // vazno zbog bidirekcione veze


        UcesnikKnjigeRepo ucesnikKnjigeRepo = new UcesnikKnjigeRepo(Konstante.FAJL_UCESNICI_KNJIGE);
        ucesnikKnjigeRepo.ucitajPodatke();
        ucesnikKnjigeRepo.ispisiPodatke();
        ucesnikKnjigeRepo.sacuvajPodatke();

        KategorijeKnjigaRepo kategorijeKnjigaRepo = new KategorijeKnjigaRepo(Konstante.FAJL_KATEGORIJE_KNJIGE);
        kategorijeKnjigaRepo.ucitajPodatke();
        kategorijeKnjigaRepo.postaviVezuIzmedjuKategorijaIPodKategorija();
        kategorijeKnjigaRepo.ispisiPodatke();
        kategorijeKnjigaRepo.sacuvajPodatke();

        KnjigeRepo knjigeRepo = new KnjigeRepo(Konstante.FAJL_KNJIGE, ucesnikKnjigeRepo, kategorijeKnjigaRepo);
        knjigeRepo.ucitajPodatke();
        knjigeRepo.ispisiPodatke();
        knjigeRepo.sacuvajPodatke();

        PrimerakKnjigeRepo primerakKnjigeRepo = new PrimerakKnjigeRepo(Konstante.FAJL_PRIMERCI_KNJIGE, knjigeRepo);
        primerakKnjigeRepo.ucitajPodatke();
        primerakKnjigeRepo.ispisiPodatke();
        primerakKnjigeRepo.sacuvajPodatke();

        knjigeRepo.postaviVezuIzmedjuKnjigeIPrimeraka(primerakKnjigeRepo); // dodajemo bidirekcionu vezu
        knjigeRepo.ispisiPodatke();

        IzdatPrimerakRepo izdatPrimerakRepo = new IzdatPrimerakRepo(Konstante.FAJL_IZDATI_PRIMRCI, primerakKnjigeRepo, clanRepo);
        izdatPrimerakRepo.ucitajPodatke();
        izdatPrimerakRepo.ispisiPodatke();
        izdatPrimerakRepo.sacuvajPodatke();

        RezervacijaRepo rezervacijaRepo = new RezervacijaRepo(Konstante.FAJL_REZERVACIJE, izdatPrimerakRepo, clanRepo);
        rezervacijaRepo.ucitajPodatke();
        rezervacijaRepo.ispisiPodatke();
        rezervacijaRepo.sacuvajPodatke();


    }
}
