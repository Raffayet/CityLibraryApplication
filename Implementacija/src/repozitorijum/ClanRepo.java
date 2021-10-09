package repozitorijum;

import model.Clan;
import model.Entitet;
import model.IzdataClanarina;
import model.enumeracije.VrstaClana;
import model.enumeracije.VrstaKorisnika;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class ClanRepo extends GenerickiRepo<Clan> {

    public ClanRepo(String putanjaDoFajla) {
        super(putanjaDoFajla);
    }

    public static void main(String[] args) throws IOException {

    }

    @Override
    protected Entitet kreirajEntitetIDodajUListu(String[] tokeni) {
        Clan c = null;
        int id = Integer.parseInt(tokeni[0]);
        String ime = tokeni[2];
        String prezime = tokeni[3];
        String korisnickoIme = tokeni[4];
        String lozinka = tokeni[5];
        String statusAktivnosti = tokeni[6];
        String jmbg = tokeni[7];
        LocalDate datumRodjenja = LocalDate.parse(tokeni[8]);
        VrstaClana vrstaClana = VrstaClana.valueOf(tokeni[9]);
        int trenuntnaClanarinaId = Integer.parseInt(tokeni[10]);

        c = new Clan(id, ime, prezime, korisnickoIme, lozinka, VrstaKorisnika.CLAN, jmbg, datumRodjenja, vrstaClana);
        c.setTrenutnaClanarinaId(trenuntnaClanarinaId);

        if (statusAktivnosti.equals("false")) {
            c.setStatusAktivnosti(false);
        }

        dodajEntitet(c);
        return c;
    }

    public void postaviVezuSaIzdatimClanarinama(IzdataClanarinaRepo izdataClanarinaRepo) {
        for (Clan clan : listaEntiteta) {
            if (clan.getTrenutnaClanarinaId() != -1) {
                IzdataClanarina izdataClanarina = izdataClanarinaRepo.getById(clan.getTrenutnaClanarinaId());
                clan.setTrenutnaClanarina(izdataClanarina);
            }
        }
    }

    public void modifkacijaPodatakaOdStraneClana(int id, String ime, String prezime, String korisnickoIme, String lozinka, LocalDate datumRodjenja){

        for (Clan e : getListaEntiteta()){

            if (e == getById(id)){

                e.setIme(ime);
                e.setPrezime(prezime);
                e.setKorisnickoIme(korisnickoIme);
                e.setLozinka(lozinka);
                e.setDatumRodjenja(datumRodjenja);
            }
        }
    }

    public void modifikacijaPodatakaOdStraneAdmina(int id, String ime, String prezime, String korisnickoIme,
                                                   String lozinka, VrstaKorisnika vrstaKorisnika, Boolean statusAktivnosti,
                                                   String jmbg, LocalDate datumRodjenja, VrstaClana vrstaClana){

        for (Clan e : getListaEntiteta()){

            if (e == getById(id)){

                e.setIme(ime);
                e.setPrezime(prezime);
                e.setKorisnickoIme(korisnickoIme);
                e.setLozinka(lozinka);
                e.setVrstaKorisnika(vrstaKorisnika);
                e.setStatusAktivnosti(statusAktivnosti);
                e.setJmbg(jmbg);
                e.setDatumRodjenja(datumRodjenja);
                e.setVrstaClana(vrstaClana);
            }
        }
    }
}
