package view.univerzalno;

import kontroler.*;
import model.*;
import model.enumeracije.VrstaBibliotekara;
import model.enumeracije.VrstaClana;
import model.enumeracije.VrstaKorisnika;
import model.enumeracije.VrstaRegistracije;
import net.miginfocom.swing.MigLayout;
import repozitorijum.FabrikaRepo;
import repozitorijum.KategorijeKnjigaRepo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

public class DodavanjeIzmenaKnjiga extends JDialog {

    private Knjiga knjiga;
    private Knjiga knjigaPom;


    private FabrikaRepo fabrikaRepo;
    private KnjigaController knjigaController;

    private JDialog roditelj;
    private JTextField tfisbn = new JTextField(35);
    private JTextField tfNaslov = new JTextField(35);
    private JTextField tfNazivIzdavaca = new JTextField(35);
    private JTextField tfMestoIzdavanja = new JTextField(35);


    private SpinnerNumberModel spinerGod = new SpinnerNumberModel(2020, 1800, 2021, 1);
    private JSpinner spGod = new JSpinner(spinerGod);
    private SpinnerNumberModel spstr = new SpinnerNumberModel(1, 1, 2000, 1);
    private JSpinner spStr = new JSpinner(spstr);
    private SpinnerNumberModel spvis = new SpinnerNumberModel(1, 1, 100, 1);
    private JSpinner spVisina = new JSpinner(spvis);
    private SpinnerNumberModel spUkupanBr = new SpinnerNumberModel(1, 1, 100, 1);
    private JSpinner spUkupanBroj = new JSpinner(spUkupanBr);
    private JComboBox<Boolean> cbIznosivost = new JComboBox<Boolean>();
    private JComboBox<KategorijaKnjige> kategorijaKnjigeJComboBox = new JComboBox<KategorijaKnjige>();


    private JButton btnSacuvaj = new JButton("Sačuvaj");
    private JButton btnOdustani = new JButton("Odustani");

    public DodavanjeIzmenaKnjiga(JDialog roditelj, Knjiga knjiga,
                                 KnjigaController knjigaController, FabrikaRepo fabrikaRepo) {
        super(roditelj);
        this.roditelj = roditelj;
        this.knjiga = knjiga;
        this.knjigaPom = knjigaPom;


        this.fabrikaRepo = fabrikaRepo;
        this.knjigaController = new KnjigaController(fabrikaRepo.getKnjigeRepo());

        if (knjiga == null) {                          // ukoliko se kao parametar prosledi null, smatra se da je u pitanju registracija, u suprotnom je modifikacija
            setTitle("Nova Knjifa");
        } else {
            setTitle("Promena podataka");
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initGUI();
        this.pack();
        setLocationRelativeTo(null);
        akcije();
    }

    private void initGUI() {

        MigLayout mgLayout = new MigLayout();
        this.setLayout(mgLayout);

        add(new JLabel("ISBN knjige: "), "split, sg a");
        add(tfisbn, "pushx, growx, wrap");
        add(new JLabel("Naslov: "), "split 2, sg a");
        add(tfNaslov, "pushx, growx, wrap");
        add(new JLabel("Naziv izdavaca: "), "split 2, sg a");
        add(tfNazivIzdavaca, "pushx, growx, wrap");
        add(new JLabel("Mesto izdavanja ime: "), "split 2, sg a");
        add(tfMestoIzdavanja, "pushx, growx, wrap");
        add(new JLabel("Godina izdanja: "), "split 2, sg a");

        add(spGod, "pushx, growx, wrap");
        add(new JLabel("Broj stranica knjige: "), "split 2, sg a");
        add(spStr, "pushx, growx, wrap");
        add(new JLabel("Visina Knjige(cm): "), "split 2, sg a");
        add(spVisina, "pushx, growx, wrap");
        add(new JLabel("Broj primereaka: "), "split 2, sg a");
        add(spUkupanBroj, "pushx, growx, wrap");
        add(new JLabel("Knjiga je iznosiva: "), "split 2, sg a");
        add(cbIznosivost, "pushx, growx, wrap");
        add(new JLabel("Kategorija: "), "split 2, sg a");
        add(kategorijaKnjigeJComboBox, "pushx, growx, wrap");

        add(new JLabel(" "), "span, wrap");
        add(new JLabel(" "), "span, wrap");
        add(new JLabel(" "), "split 3, sg a");
        add(btnSacuvaj);
        add(btnOdustani);

        if (this.knjiga != null) {        //slucaj kada se vrsi modifikacija podataka

            formaZaModifikacijuKnjige(this.knjiga);
        } else {                                              // slucaj kada se vrsi registracija novog korisnika
            formaZaDodavanjeKnjiga();
        }
    }

    private void formaZaModifikacijuKnjige(Knjiga knjiga) {
        tfisbn.setText(knjiga.getIsbn());
        tfisbn.setEnabled(false);
        tfNaslov.setText(knjiga.getNaslov());
        tfNazivIzdavaca.setText(knjiga.getNazivIzdavaca());
        tfMestoIzdavanja.setText(knjiga.getMestoIzdavaca());
        spGod.setValue(knjiga.getGodinaIzdanja());
        spStr.setValue(knjiga.getBrojStranica());
        spVisina.setValue(knjiga.getVisina());
        spUkupanBroj.setValue(knjiga.getUkupanBroj());
        spUkupanBroj.setEnabled(false);
        cbIznosivost.addItem(false);
        cbIznosivost.addItem(true);
        cbIznosivost.setSelectedItem(knjiga.getIznosiva());
        kategorijaKnjigeJComboBox.setSelectedItem(knjiga.getKateogrije());


    }

    private void formaZaDodavanjeKnjiga() {

        spGod.setValue(2000);
        cbIznosivost.addItem(false);
        cbIznosivost.addItem(true);
        cbIznosivost.setSelectedItem(true);
        for (KategorijaKnjige k : fabrikaRepo.getKategorijeKnjigaRepo().getListaEntiteta()) {
            kategorijaKnjigeJComboBox.addItem(k);
        }
    }


    private void modifikacijaKnjigee(String naslov, String nazivIzdavaca, String mestoIzdavanja, Integer godinaIzdavanja, Integer brojStr, Integer visina, Integer ukupanBr, Boolean iznosiva) {
        if (knjigaController.unosValidan(naslov, nazivIzdavaca, mestoIzdavanja, godinaIzdavanja, brojStr, ukupanBr, iznosiva, knjiga.getKateogrije().get(0))) {
            int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate date podatke?", "Pitanje", JOptionPane.YES_NO_CANCEL_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                if (DodavanjeIzmenaKnjiga.this.knjiga != null) {
                    knjigaController.modifikacijaPodataka(knjiga.getId(), naslov, nazivIzdavaca, mestoIzdavanja, godinaIzdavanja, brojStr, visina, ukupanBr, iznosiva);
                }
                DodavanjeIzmenaKnjiga.this.dispose();

            }

        }
    }

    private void dodavanjeKnjige(String isbn, String naslov, String nazivIzdavaca, String mestoIzdavanja, Integer godinaIzdavanja, Integer brojStr, Integer visina, Integer ukupanBr, Boolean iznosiva, KategorijaKnjige kategorijaKnjige)
    {
        if (knjigaController.unosValidan(naslov, nazivIzdavaca, mestoIzdavanja, godinaIzdavanja, brojStr, ukupanBr, iznosiva, kategorijaKnjige)) {
            int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate date podatke?", "Pitanje", JOptionPane.YES_NO_CANCEL_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                if (DodavanjeIzmenaKnjiga.this.knjiga == null) {

                   knjigaController.dodajKnjigu(isbn,naslov,nazivIzdavaca,mestoIzdavanja,godinaIzdavanja,brojStr,visina,ukupanBr,iznosiva,kategorijaKnjige, fabrikaRepo.getPrimerakKnjigeRepo());
                    }
                DodavanjeIzmenaKnjiga.this.dispose();

            }

        }


    }







    private void akcije() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DodavanjeIzmenaKnjiga.this.dispose();
                DodavanjeIzmenaKnjiga.this.setVisible(false);
                roditelj.setVisible(true);
                super.windowClosing(e);
            }
        });

        btnOdustani.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DodavanjeIzmenaKnjiga.this.dispose();
            }
        });

        btnSacuvaj.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = tfisbn.getText().trim();
                String naslov = tfNaslov.getText().trim();
                String nazivIzdavaca = tfNazivIzdavaca.getText().trim();
                String mestoIzdavanja = tfMestoIzdavanja.getText().trim();
                Integer godinaIzdanja = (Integer) spinerGod.getValue();
                Integer brojStranica = (Integer) spstr.getValue();
                Integer visina = (Integer) spvis.getValue();
                Integer ukupanBr = (Integer) spUkupanBr.getValue();
                Boolean iznosiva = (Boolean) cbIznosivost.getSelectedItem();
                KategorijaKnjige kategorijaKnjige =(KategorijaKnjige) kategorijaKnjigeJComboBox.getSelectedItem();
                if (knjiga != null) {
                    modifikacijaKnjigee(naslov,nazivIzdavaca,mestoIzdavanja,godinaIzdanja,brojStranica,visina,ukupanBr,iznosiva);
                }
                else
                {
                   dodavanjeKnjige(isbn,naslov,nazivIzdavaca,mestoIzdavanja,godinaIzdanja,brojStranica,visina,ukupanBr,iznosiva,kategorijaKnjige);
                }



            }});




}}
