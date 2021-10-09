package kontroler;

import izuzeci.KorisnikNijeNadjen;
import izuzeci.SviPrimerciSuZauzeti;
import model.KategorijaKnjige;
import model.Knjiga;
import model.PrimerakKnjige;
import model.enumeracije.RaspolozivostPrimerkaKnjige;
import repozitorijum.KnjigeRepo;
import repozitorijum.PrimerakKnjigeRepo;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class KnjigaController {

    private KnjigeRepo knjigeRepo;

    public KnjigaController(KnjigeRepo knjigeRepo) {
        this.knjigeRepo = knjigeRepo;
    }

    public void obrisisTrajnoKnjigu(String isbn){
        knjigeRepo.obrisiTrajnoKnjigu(isbn);
    }

    public PrimerakKnjige getSlobodanPrimerakKnjige(int knjigaId) {
        Knjiga knjiga = knjigeRepo.getById(knjigaId);
        for (PrimerakKnjige pk : knjiga.getPrimerci()) {
            if (pk.getRaspolozivost() == RaspolozivostPrimerkaKnjige.SLOBODAN)
                return pk;
        }
        throw new SviPrimerciSuZauzeti("Svi primerci za ovu knjigu su trenutno zauzeti");
    }

    public boolean unosValidan(String naslov, String nazivIzdavaca, String mestoIzdavanja, Integer godinaIzdavanja, Integer brojStr, Integer ukupanBr, Boolean iznosiva, KategorijaKnjige kategorijaKnjige) {

        if (naslov.equals("") | nazivIzdavaca.equals("") | mestoIzdavanja.equals("") | iznosiva == null| kategorijaKnjige==null) {
            JOptionPane.showMessageDialog(null, "Molim vas da unesite sve potrebne podatke."
                    ,"Greska", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        else if (naslov.length() > 30 | nazivIzdavaca.length() > 30 | mestoIzdavanja.length()>15) {
            JOptionPane.showMessageDialog(null, "Naslov ne može imati vise od 30 slova, naziv izdavaca više od 30 slova, a mesto vise od 15 slova");
            return false;
        }

        return true;
    }

    public void modifikacijaPodataka(Integer id, String naslov, String nazivIzdavaca, String mestoIzdavanja, Integer godinaIzdavanja, Integer brojStr,Integer visina, Integer ukupanBr, Boolean iznosiva)
    {
        knjigeRepo.modifikacijaPodataka(id,naslov,nazivIzdavaca,mestoIzdavanja,godinaIzdavanja,brojStr,visina,ukupanBr,iznosiva);
    }


    public void dodajKnjigu(String isbn, String naslov, String nazivIzdavaca, String mestoIzdavanja, Integer godinaIzdavanja, Integer brojStr, Integer visina, Integer ukupanBr, Boolean iznosiva, KategorijaKnjige kategorijaKnjige, PrimerakKnjigeRepo pkc)
    {
        ArrayList<KategorijaKnjige> lista = new ArrayList<>();
        lista.add(kategorijaKnjige);
        Knjiga novaKnjiga = new Knjiga(knjigeRepo.generisanId(),isbn,naslov,nazivIzdavaca,mestoIzdavanja,godinaIzdavanja,brojStr,visina,ukupanBr,0,0,iznosiva,lista);
        knjigeRepo.dodajEntitet(novaKnjiga);

        for(int i = 0;i<ukupanBr;i++) {
            int idp = pkc.generisanId();
            PrimerakKnjige primerak = new PrimerakKnjige(idp, novaKnjiga);
            novaKnjiga.dodajPrimerak(primerak);
            pkc.dodajEntitet(primerak);

        }


    }




}
