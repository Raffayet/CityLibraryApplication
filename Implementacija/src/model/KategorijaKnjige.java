package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

public class KategorijaKnjige extends Entitet {

    private String naziv;
    private ArrayList<KategorijaKnjige> podkategorije;

    private List<Integer> podkategorijeIds; // pomocni atribut prilikom pravljenja kategorija iz fajla

    public KategorijaKnjige() {
    }

    public KategorijaKnjige(int id, String naziv, ArrayList<KategorijaKnjige> podkategorije) {
        super(id);
        this.naziv = naziv;
        this.podkategorije = podkategorije;
        this.podkategorijeIds = new ArrayList<>();
    }

    public KategorijaKnjige(int id, String naziv) {
        super(id);
        this.naziv = naziv;
        this.podkategorijeIds = new ArrayList<>();
        this.podkategorije = new ArrayList<>();
    }

    public KategorijaKnjige(String naziv) {
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public ArrayList<KategorijaKnjige> getPodkategorije() {
        return podkategorije;
    }

    public List<Integer> getPodkategorijeIds() {
        return podkategorijeIds;
    }

    public void setPodkategorije(ArrayList<KategorijaKnjige> podkategorije) {
        this.podkategorije = podkategorije;
    }

    public void setPodkategorijeIds(List<Integer> podkategorijeIds) {
        this.podkategorijeIds = podkategorijeIds;
    }

    public void dodajPodkategoriju(KategorijaKnjige kategorijaKnjige) {
        this.podkategorije.add(kategorijaKnjige);
    }

    @Override
    public String formatiranZapisZaFajl() {
        if (podkategorijeIds.size() != 0)
            return String.format("%d|%s|%s", this.getId(), this.getNaziv(), getListaPodkategorijaIdsKaoString());
        return String.format("%d|%s", this.getId(), this.getNaziv());

    }

    private String getListaPodkategorijaIdsKaoString(){
        StringBuilder sb = new StringBuilder("");
        for (Integer i : this.podkategorijeIds) {
            sb.append(String.valueOf(i)).append(',');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return naziv;
    }
}
