package model;

import java.util.ArrayList;
import java.util.Objects;

public class Knjiga extends Entitet{

    private String isbn;
    private String naslov;
    private String nazivIzdavaca;
    private String mestoIzdavaca;
    private int godinaIzdanja;
    private int brojStranica;
    private int visina;
    private int ukupanBroj;
    private int brojSlobodnih;
    private int brojRezervisanih;
    private Boolean iznosiva;
    private ArrayList<UcesnikKnjige> autori;
    private ArrayList<KategorijaKnjige> kateogrije;
    private ArrayList<PrimerakKnjige> primerci;
    private ArrayList<UcesnikKnjige> kreatori;

    private ArrayList<Integer> primerciIds; // pomocni atribut za bidirekcionu vezu sa primerkom

    public Knjiga() {
    }


    public Knjiga(int id, String isbn, String naslov, String nazivIzdavaca, String mestoIzdavaca, int godinaIzdanja,            //konstruktor bez primeraka i kreatora
                  int brojStranica, int visina, int ukupanBroj, int brojSlobodnih, int brojRezervisanih,
                  Boolean iznosiva, ArrayList<UcesnikKnjige> autori, ArrayList<KategorijaKnjige> kateogrije) {
        super(id);
        this.isbn = isbn;
        this.naslov = naslov;
        this.nazivIzdavaca = nazivIzdavaca;
        this.mestoIzdavaca = mestoIzdavaca;
        this.godinaIzdanja = godinaIzdanja;
        this.brojStranica = brojStranica;
        this.visina = visina;
        this.ukupanBroj = ukupanBroj;
        this.brojSlobodnih = brojSlobodnih;
        this.brojRezervisanih = brojRezervisanih;
        this.iznosiva = iznosiva;
        this.autori = autori;
        this.kateogrije = kateogrije;
        this.primerciIds = new ArrayList<>();
        this.primerci = new ArrayList<>();
        this.kreatori = new ArrayList<>();
    }
    public Knjiga(int id, String isbn, String naslov, String nazivIzdavaca, String mestoIzdavaca, int godinaIzdanja,            //konstruktor bez primeraka i kreatora i bez autora
                  int brojStranica, int visina, int ukupanBroj, int brojSlobodnih, int brojRezervisanih,
                  Boolean iznosiva, ArrayList<KategorijaKnjige> kateogrije) {
        super(id);
        this.isbn = isbn;
        this.naslov = naslov;
        this.nazivIzdavaca = nazivIzdavaca;
        this.mestoIzdavaca = mestoIzdavaca;
        this.godinaIzdanja = godinaIzdanja;
        this.brojStranica = brojStranica;
        this.visina = visina;
        this.ukupanBroj = ukupanBroj;
        this.brojSlobodnih = brojSlobodnih;
        this.brojRezervisanih = brojRezervisanih;
        this.iznosiva = iznosiva;
        this.autori = new ArrayList<>();
        this.kateogrije = kateogrije;
        this.primerciIds = new ArrayList<>();
        this.primerci = new ArrayList<>();
        this.kreatori = new ArrayList<>();
    }

    public Knjiga(int id, String isbn, String naslov, String nazivIzdavaca, String mestoIzdavaca, int godinaIzdanja,            //konstruktor bez primeraka (pri osnivanju knjige)
                  int brojStranica, int visina, int ukupanBroj, int brojSlobodnih, int brojRezervisanih,
                  Boolean iznosiva, ArrayList<UcesnikKnjige> autori, ArrayList<KategorijaKnjige> kateogrije,
                  ArrayList<UcesnikKnjige> kreatori) {
        super(id);
        this.isbn = isbn;
        this.naslov = naslov;
        this.nazivIzdavaca = nazivIzdavaca;
        this.mestoIzdavaca = mestoIzdavaca;
        this.godinaIzdanja = godinaIzdanja;
        this.brojStranica = brojStranica;
        this.visina = visina;
        this.ukupanBroj = ukupanBroj;
        this.brojSlobodnih = brojSlobodnih;
        this.brojRezervisanih = brojRezervisanih;
        this.iznosiva = iznosiva;
        this.autori = autori;
        this.kateogrije = kateogrije;
        this.kreatori = kreatori;
        this.primerci = new ArrayList<>();
        this.primerciIds = new ArrayList<>();
    }

    public Knjiga(int id, String isbn, String naslov, String nazivIzdavaca, String mestoIzdavaca, int godinaIzdanja,            // pun  konstruktor
                  int brojStranica, int visina, int ukupanBroj, int brojSlobodnih, int brojRezervisanih,
                  Boolean iznosiva, ArrayList<UcesnikKnjige> autori, ArrayList<KategorijaKnjige> kateogrije,
                  ArrayList<PrimerakKnjige> primerci, ArrayList<UcesnikKnjige> kreatori) {
        super(id);
        this.isbn = isbn;
        this.naslov = naslov;
        this.nazivIzdavaca = nazivIzdavaca;
        this.mestoIzdavaca = mestoIzdavaca;
        this.godinaIzdanja = godinaIzdanja;
        this.brojStranica = brojStranica;
        this.visina = visina;
        this.ukupanBroj = ukupanBroj;
        this.brojSlobodnih = brojSlobodnih;
        this.brojRezervisanih = brojRezervisanih;
        this.iznosiva = iznosiva;
        this.autori = autori;
        this.kateogrije = kateogrije;
        this.primerci = primerci;
        this.kreatori = kreatori;
        this.primerciIds = new ArrayList<>();
    }

    public String getIsbn() {
        return isbn;
    }

    public String getNaslov() {
        return naslov;
    }

    public String getNazivIzdavaca() {
        return nazivIzdavaca;
    }

    public String getMestoIzdavaca() {
        return mestoIzdavaca;
    }

    public int getGodinaIzdanja() {
        return godinaIzdanja;
    }

    public int getBrojStranica() {
        return brojStranica;
    }

    public int getVisina() {
        return visina;
    }

    public int getUkupanBroj() {
        return ukupanBroj;
    }

    public int getBrojSlobodnih() {
        return brojSlobodnih;
    }

    public int getBrojRezervisanih() {
        return brojRezervisanih;
    }

    public Boolean getIznosiva() {
        return iznosiva;
    }

    public ArrayList<UcesnikKnjige> getAutori() {
        return autori;
    }

    public ArrayList<KategorijaKnjige> getKateogrije() {
        return kateogrije;
    }

    public ArrayList<PrimerakKnjige> getPrimerci() {
        return primerci;
    }

    public ArrayList<UcesnikKnjige> getKreatori() {
        return kreatori;
    }

    public ArrayList<Integer> getPrimerciIds() {
        return primerciIds;
    }

    public String getImeGlavnogAutora() { return autori.get(0).getIme(); }

    public String getPrezimeGlavnogAutora() { return autori.get(0).getPrezime(); }

    public void setPrimerciIds(ArrayList<Integer> primerciIds) {
        this.primerciIds = primerciIds;
    }

    public void dodajPrimerak(PrimerakKnjige primerakKnjige) {
        primerci.add(primerakKnjige);
    }

    public void setNaslov(String naslov){ this.naslov = naslov;}

    public void setNazivIzdavaca(String nazivIzdavaca){ this.nazivIzdavaca = nazivIzdavaca;}

    public void setMestoIzdavaca(String mesto){ this.mestoIzdavaca = mesto;}

    public void setGodinaIzdanja(Integer godinaIzdanja){ this.godinaIzdanja = godinaIzdanja;}

    public void setBrojStranica(Integer brojStranica){ this.brojStranica = brojStranica;}

    public void setUkupanBroj(Integer ukupanBroj){this.ukupanBroj = ukupanBroj;}

    public void setIznosiva(Boolean iznosiva){this.iznosiva = iznosiva;}

    public void setVisina(Integer visina){this.visina = visina;}

    public void povecajBrojSlobodnih() { this.brojSlobodnih++; }
    public void smanjiBrojSlobodnih() { this.brojSlobodnih--;}
    public void povecajBrojRezervisanih() { this.brojRezervisanih++;}
    public void smanjiBrojRezervisanih() { this.brojRezervisanih--;}
    public int getBrojIdatih() { return this.ukupanBroj - this.brojSlobodnih; }

    @Override
    public String formatiranZapisZaFajl() {
        return String.format("%d|%s|%s|%s|%s|%d|%d|%d|%d|%d|%d|%s|%s|%s|%s",
                this.getId(),
                this.getIsbn(),
                this.getNaslov(),
                this.getNazivIzdavaca(),
                this.getMestoIzdavaca(),
                this.getGodinaIzdanja(),
                this.getBrojStranica(),
                this.getVisina(),
                this.getUkupanBroj(),
                this.getBrojSlobodnih(),
                this.getBrojRezervisanih(),
                this.getIznosiva(),
                kreirajListuUcesnikaIdsRazdvojenihZarezom(getAutori()),
                kreirajListuKategorijaIdsRazdvojenihZarezom(),
                kreirajListuPrimerakaIdsRazdvojenihZarezom()) +
                getStringZaIspisAkoImaKreatora();
    }

    private String getStringZaIspisAkoImaKreatora() {
        if (getKreatori().size() == 0)
            return "";
        return "|" + kreirajListuUcesnikaIdsRazdvojenihZarezom(getKreatori());
    }

    private String kreirajListuUcesnikaIdsRazdvojenihZarezom(ArrayList<UcesnikKnjige> ucesnici){
        StringBuilder sb = new StringBuilder("");
        for (UcesnikKnjige u : ucesnici) {
            sb.append(u.getId()).append(',');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private String kreirajListuKategorijaIdsRazdvojenihZarezom() {
        StringBuilder sb = new StringBuilder("");
        for (KategorijaKnjige kategorijaKnjige : getKateogrije()) {
            sb.append(kategorijaKnjige.getId()).append(',');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private String kreirajListuPrimerakaIdsRazdvojenihZarezom() {
        StringBuilder sb = new StringBuilder("");
        for (PrimerakKnjige p : getPrimerci()) {
            sb.append(p.getId()).append(',');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }


    @Override
    public String toString() {
        return "Knjiga{" +
                "isbn='" + isbn + '\'' +
                ", naslov='" + naslov + '\'' +
                ", nazivIzdavaca='" + nazivIzdavaca + '\'' +
                ", mestoIzdavaca='" + mestoIzdavaca + '\'' +
                ", godinaIzdanja=" + godinaIzdanja +
                ", brojStranica=" + brojStranica +
                ", visina=" + visina +
                ", ukupanBroj=" + ukupanBroj +
                ", brojSlobodnih=" + brojSlobodnih +
                ", brojRezervisanih=" + brojRezervisanih +
                ", iznosiva=" + iznosiva +
                ", autori=" + autori +
                ", kateogrije=" + kateogrije +
                ", kreatori=" + kreatori +
                ", primerciIds=" + primerciIds +
                '}';
    }
}
