package repozitorijum;

import model.*;

import java.util.ArrayList;

public class KnjigeRepo extends GenerickiRepo<Knjiga>{

    UcesnikKnjigeRepo ucesnikKnjigeRepo;
    KategorijeKnjigaRepo kategorijeKnjigaRepo;

    public KnjigeRepo(String putanjaDoFajla, UcesnikKnjigeRepo ucesnikKnjigeRepo, KategorijeKnjigaRepo kategorijeKnjigaRepo) {
        super(putanjaDoFajla);
        this.ucesnikKnjigeRepo = ucesnikKnjigeRepo;
        this.kategorijeKnjigaRepo = kategorijeKnjigaRepo;
    }

    @Override
    protected Entitet kreirajEntitetIDodajUListu(String[] tokeni) {
        Knjiga knjiga = null;
        int id = Integer.parseInt(tokeni[0]);
        String isbn = tokeni[1];
        String naslov = tokeni[2];
        String izdavac = tokeni[3];
        String mesto = tokeni[4];
        int godina = Integer.parseInt(tokeni[5]);
        int brojStranica = Integer.parseInt(tokeni[6]);
        int visina = Integer.parseInt(tokeni[7]);
        int brojUkupnih = Integer.parseInt(tokeni[8]);
        int brojSlobodnih = Integer.parseInt(tokeni[9]);
        int brojRezervisanih = Integer.parseInt(tokeni[10]);
        boolean iznosiva = Boolean.parseBoolean(tokeni[11]);
        ArrayList<UcesnikKnjige> autori = getUcesnikeFromStringOfIds(tokeni[12]);
        ArrayList<KategorijaKnjige> kategorijeKnjige = getKategorijeKnjigeFromStringOfIds(tokeni[13]);

        if (tokeni.length > 15) {
            ArrayList<UcesnikKnjige> kreatori = getUcesnikeFromStringOfIds(tokeni[15]);
            knjiga = new Knjiga(id, isbn, naslov, izdavac, mesto, godina, brojStranica, visina,
                    brojUkupnih, brojSlobodnih, brojRezervisanih, iznosiva, autori, kategorijeKnjige, kreatori);
        } else {
            knjiga = new Knjiga(id, isbn, naslov, izdavac, mesto, godina, brojStranica, visina,
                    brojUkupnih, brojSlobodnih, brojRezervisanih, iznosiva, autori, kategorijeKnjige);
        }

        knjiga.setPrimerciIds(getListaPrimerakaIdsFromString(tokeni[14]));
        dodajEntitet(knjiga);
        return knjiga;
    }

    @Override
    public void obrisiTrajnoEntitet(Knjiga entitet) {
        super.obrisiTrajnoEntitet(entitet);
    }

    public void obrisiTrajnoKnjigu(String isbn){
        listaEntiteta.removeIf(p -> p.getIsbn().equals(isbn));
    }


    private ArrayList<Integer> getListaPrimerakaIdsFromString(String s) {
        ArrayList<Integer> retVal = new ArrayList<>();
        String[] tokeni = s.split(",");
        for (String token : tokeni) {
            int id = Integer.parseInt(token);
            retVal.add(id);
        }
        return retVal;
    }

    private ArrayList<KategorijaKnjige> getKategorijeKnjigeFromStringOfIds(String s) {
        ArrayList<KategorijaKnjige> retVal = new ArrayList<>();
        String[] tokeni = s.split(",");
        for (String token : tokeni) {
            int id = Integer.parseInt(token);
            retVal.add(kategorijeKnjigaRepo.getById(id));
        }
        return retVal;
    }

    private ArrayList<UcesnikKnjige> getUcesnikeFromStringOfIds(String s) {
        ArrayList<UcesnikKnjige> retVal = new ArrayList<>();
        if(!s.equals("")){
        String[] tokeni = s.split(",");
        for (String token : tokeni) {
            int id = Integer.parseInt(token);
            retVal.add(ucesnikKnjigeRepo.getById(id));
        }}
        return retVal;
    }


    public void postaviVezuIzmedjuKnjigeIPrimeraka(PrimerakKnjigeRepo primerakKnjigeRepo) {
        for (Knjiga knjiga : listaEntiteta) {
            for (Integer id : knjiga.getPrimerciIds()) {
                PrimerakKnjige primerakKnjige = primerakKnjigeRepo.getById(id);
                knjiga.dodajPrimerak(primerakKnjige);
            }
        }
    }

    public void modifikacijaPodataka(Integer id,String naslov, String nazivIzdavaca, String mestoIzdavanja, Integer godinaIzdavanja, Integer brojStr,Integer visina, Integer ukupanBr, Boolean iznosiva){

        for (Knjiga e : getListaEntiteta()){

            if (e.getId() == id){

                e.setNaslov(naslov);
                e.setNazivIzdavaca(nazivIzdavaca);
                e.setMestoIzdavaca(mestoIzdavanja);
                e.setGodinaIzdanja(godinaIzdavanja);
                e.setBrojStranica(brojStr);
                e.setUkupanBroj(ukupanBr);
                e.setVisina(visina);
                e.setIznosiva(iznosiva);
            }
        }
    }



    }








