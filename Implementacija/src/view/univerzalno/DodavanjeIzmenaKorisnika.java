package view.univerzalno;

import kontroler.*;
import model.*;
import model.enumeracije.*;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import repozitorijum.FabrikaRepo;
import utils.DatumLabelaFormater;
import view.modeliTabela.KorisniciTableModel;
import view.modeliTabela.TabelaClanovaIBibliotekara;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

public class DodavanjeIzmenaKorisnika extends JDialog{  //Forma se koristi na sledeci nacin:
    //1) ako je potrebna registracija, prosledjuje se null, i zatim bira vrsta registracije, u suprotnom se prosledjuje korisnik
    //2) na formalni parametar korisnikAkter, namapirati trenutnog korisnika koji koristi aplikaciju
    //3) Formalni parametar korisnik oznacava nad kojim korisnikom se vrse operacije

    private static final long serialVersionUID = -1683983620386553716L;

    private Korisnik korisnik;
    private Korisnik korisnikAkter;
    private VrstaRegistracije registracija;
    private ClanController clanController;
    private BibliotekarController bibliotekarController;
    private FabrikaRepo fabrikaRepo;
    private KorisnikController korisnikController;
    private ClanarinaController clanarinaController;
    private IzdataClanarinaController izdataClanarinaController;

    private JFrame roditelj;
    private JTextField tfId = new JTextField(35);
    private JTextField tfIme = new JTextField(35);
    private JTextField tfPrezime = new JTextField(35);
    private JTextField tfKorisnickoIme = new JTextField(35);
    private JTextField tfLozinka = new JTextField(35);
    private JComboBox<VrstaKorisnika> cbVrstaKorisnika = new JComboBox<VrstaKorisnika>();
    private JComboBox<String> cbStatusAktivnosti = new JComboBox<String>();
    private JComboBox<TipClanarine> cbTipClanarine = new JComboBox<TipClanarine>();
    private JTextField tfJmbg = new JTextField(35);

    private JComboBox<VrstaClana> cbVrstaClana = new JComboBox<VrstaClana>();
    private JComboBox<VrstaBibliotekara> cbVrstaBibliotekara = new JComboBox<VrstaBibliotekara>();
    private JButton btnSacuvaj = new JButton("Sačuvaj");
    private JButton btnOdustani = new JButton("Odustani");

    private JDatePickerImpl datePickerRodjene;
    private UtilDateModel model = new UtilDateModel();

    public DodavanjeIzmenaKorisnika(JFrame roditelj, Korisnik korisnik, Korisnik korisnikAkter, VrstaRegistracije registracija,
                                    KorisnikController korisnikController, FabrikaRepo fabrikaRepo) {
        super(roditelj);
        this.roditelj = roditelj;
        this.korisnik = korisnik;
        this.korisnikAkter = korisnikAkter;
        this.registracija = registracija;
        this.clanController = new ClanController(fabrikaRepo.getClanRepo(), fabrikaRepo.getKorisnikRepo());
        this.bibliotekarController = new BibliotekarController(fabrikaRepo.getBibliotekarRepo(), fabrikaRepo.getKorisnikRepo());
        this.fabrikaRepo = fabrikaRepo;
        this.korisnikController = new KorisnikController(fabrikaRepo.getKorisnikRepo());
        this.clanarinaController = new ClanarinaController(fabrikaRepo.getClanarinaRepo());
        this.izdataClanarinaController = new IzdataClanarinaController(fabrikaRepo.getIzdataClanarinaRepo());

        if(korisnik == null) {                          // ukoliko se kao parametar prosledi null, smatra se da je u pitanju registracija, u suprotnom je modifikacija
            setTitle("Registracija korisnika");
        }
        else {setTitle("Promena podataka");}
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initGUI();
        this.pack();
        setLocationRelativeTo(null);
        akcije();
    }

    private void initGUI() {

        podesiDatePickerRodjenja();

        MigLayout mgLayout = new MigLayout();
        this.setLayout(mgLayout);

        add(new JLabel("Korisnik id: "), "split, sg a");
        add(tfId, "pushx, growx, wrap");
        add(new JLabel("Ime: "), "split 2, sg a");
        add(tfIme, "pushx, growx, wrap");
        add(new JLabel("Prezime: "), "split 2, sg a");
        add(tfPrezime, "pushx, growx, wrap");
        add(new JLabel("Korisnicko ime: "), "split 2, sg a");
        add(tfKorisnickoIme, "pushx, growx, wrap");
        add(new JLabel("Lozinka: "), "split 2, sg a");
        add(tfLozinka, "pushx, growx, wrap");
        add(new JLabel("Vrsta korisnika: "), "split 2, sg a");
        add(cbVrstaKorisnika, "pushx, growx, wrap");
        add(new JLabel("Status aktivnosti: "), "split 2, sg a");
        add(cbStatusAktivnosti, "pushx, growx, wrap");
        add(new JLabel("JMBG: "), "split 2, sg a");
        add(tfJmbg, "pushx, growx, wrap");
        add(new JLabel("Datum rodjenja: "), "split 2, sg a");
        add(datePickerRodjene, "pushx, growx, wrap");
        add(new JLabel("Vrsta clana: "), "split 2, sg a");
        add(cbVrstaClana, "pushx, growx, wrap");
        add(new JLabel("Vrsta bibliotekara: "), "split 2, sg a");
        add(cbVrstaBibliotekara, "pushx, growx, wrap");
        add(new JLabel("Tip clanarine: "), "split 2, sg a");
        add(cbTipClanarine, "pushx, growx, wrap");
        add(new JLabel(" "), "span, wrap");
        add(new JLabel(" "), "span, wrap");
        add(new JLabel(" "), "split 3, sg a");
        add(btnSacuvaj);
        add(btnOdustani);

        if (this.korisnik != null) {        //slucaj kada se vrsi modifikacija podataka

            if (this.korisnik instanceof Clan){
                podesiInicijalniDatumRodjenja((Clan) this.korisnik);
                formaModifikacijaClana((Clan) this.korisnik, korisnikAkter);
            }

            else if (this.korisnik instanceof Bibliotekar){
                formaModifikacijaBibliotekara((Bibliotekar) this.korisnik, korisnikAkter);
            }
        }
        else {                                              // slucaj kada se vrsi registracija novog korisnika
            tfId.setVisible(false);

            if (this.registracija == VrstaRegistracije.REGISTRACIJA_CLANA) {

                formaRegistracijaClana();
            }

            else if (this.registracija == VrstaRegistracije.REGISTRACIJA_BIBLIOTEKARA){

                formaRegistracijaBibliotekara();
            }
        }
    }

    private void formaRegistracijaBibliotekara() {
        cbVrstaBibliotekara.addItem(VrstaBibliotekara.POZAJMLJIVAC);
        cbVrstaBibliotekara.addItem(VrstaBibliotekara.OBRADJIVAC);
        cbVrstaBibliotekara.addItem(VrstaBibliotekara.ADMINISTRATOR);
        tfJmbg.setEditable(false);
        datePickerRodjene.setEnabled(false);
        cbVrstaClana.setEnabled(false);
        cbVrstaBibliotekara.setSelectedItem(VrstaBibliotekara.ADMINISTRATOR);
        cbStatusAktivnosti.setSelectedItem("Aktivan");
        cbStatusAktivnosti.addItem("Aktivan");
        cbStatusAktivnosti.addItem("Neaktivan");
        cbStatusAktivnosti.setEnabled(false);
        cbVrstaKorisnika.addItem(VrstaKorisnika.CLAN);
        cbVrstaKorisnika.addItem(VrstaKorisnika.BIBLIOTEKAR);
        cbVrstaKorisnika.setSelectedItem(VrstaKorisnika.BIBLIOTEKAR);
        cbVrstaKorisnika.setEnabled(false);
        datePickerRodjene.setVisible(false);
        cbTipClanarine.setVisible(false);
    }

    private void formaRegistracijaClana() {
        cbVrstaKorisnika.addItem(VrstaKorisnika.CLAN);
        cbVrstaKorisnika.addItem(VrstaKorisnika.BIBLIOTEKAR);
        cbVrstaKorisnika.setSelectedItem(VrstaKorisnika.CLAN);
        cbVrstaKorisnika.setEnabled(true);
        cbVrstaClana.addItem(VrstaClana.DJAK);
        cbVrstaClana.addItem(VrstaClana.STUDENT);
        cbVrstaClana.addItem(VrstaClana.ZAPOSLEN);
        cbVrstaClana.addItem(VrstaClana.NEZAPOSLEN);
        cbVrstaClana.addItem(VrstaClana.PENZIONER);
        cbVrstaClana.addItem(VrstaClana.PRIVILEGOVAN);
        cbVrstaClana.setSelectedItem(VrstaClana.ZAPOSLEN);
        cbVrstaClana.setEnabled(true);
        cbVrstaBibliotekara.setEnabled(false);
        cbStatusAktivnosti.setSelectedItem("Aktivan");
        cbStatusAktivnosti.addItem("Aktivan");
        cbStatusAktivnosti.addItem("Neaktivan");
        cbStatusAktivnosti.setEnabled(false);
        cbVrstaKorisnika.setEnabled(false);
        cbTipClanarine.addItem(TipClanarine.GODISNJA);
        cbTipClanarine.addItem(TipClanarine.POLUGODISNJA);
    }

    private void formaModifikacijaClana(Clan korisnik, Korisnik korisnikAkter) {

        tfId.setText(String.valueOf(korisnik.getId()));
        tfId.setEditable(false);
        tfIme.setText(korisnik.getIme());
        tfPrezime.setText(korisnik.getPrezime());
        tfKorisnickoIme.setText(korisnik.getKorisnickoIme());
        tfLozinka.setText(korisnik.getLozinka());
        cbVrstaKorisnika.setSelectedItem(korisnik.getVrstaKorisnika());
        cbVrstaKorisnika.addItem(VrstaKorisnika.CLAN);
        cbVrstaKorisnika.addItem(VrstaKorisnika.BIBLIOTEKAR);
        cbStatusAktivnosti.setSelectedItem("Aktivan");
        cbStatusAktivnosti.addItem("Aktivan");
        cbStatusAktivnosti.addItem("Neaktivan");
        tfJmbg.setText(korisnik.getJmbg());
        datePickerRodjene.setEnabled(true);
        cbVrstaClana.setSelectedItem(korisnik.getVrstaClana());
        cbVrstaClana.addItem(VrstaClana.DJAK);
        cbVrstaClana.addItem(VrstaClana.NEZAPOSLEN);
        cbVrstaClana.addItem(VrstaClana.PENZIONER);
        cbVrstaClana.addItem(VrstaClana.PRIVILEGOVAN);
        cbVrstaClana.addItem(VrstaClana.STUDENT);
        cbVrstaClana.addItem(VrstaClana.ZAPOSLEN);
        cbVrstaBibliotekara.setEnabled(false);
        tfJmbg.setEditable(false);
        cbVrstaKorisnika.setEnabled(false);
        cbStatusAktivnosti.setEnabled(false);
        cbVrstaClana.setEnabled(false);
        cbTipClanarine.setVisible(false);

        if (korisnikAkter instanceof Bibliotekar){

            if (((Bibliotekar) korisnikAkter).getVrstaBibliotekara() == VrstaBibliotekara.ADMINISTRATOR){
                tfJmbg.setEditable(true);
            }

            else {
                cbVrstaKorisnika.setEnabled(true);
                cbStatusAktivnosti.setEnabled(true);
                cbVrstaClana.setEnabled(true);
            }
        }
    }

    private void formaModifikacijaBibliotekara(Bibliotekar korisnik, Korisnik korisnikAkter) {

        tfId.setText(String.valueOf(korisnik.getId()));
        tfId.setEditable(false);
        tfIme.setText(korisnik.getIme());
        tfPrezime.setText(korisnik.getPrezime());
        tfKorisnickoIme.setText(korisnik.getKorisnickoIme());
        tfLozinka.setText(korisnik.getLozinka());
        cbVrstaKorisnika.setSelectedItem(korisnik.getVrstaKorisnika());
        cbVrstaKorisnika.addItem(VrstaKorisnika.CLAN);
        cbVrstaKorisnika.addItem(VrstaKorisnika.BIBLIOTEKAR);
        cbStatusAktivnosti.setSelectedItem("Aktivan");
        cbStatusAktivnosti.addItem("Aktivan");
        cbStatusAktivnosti.addItem("Neaktivan");
        cbVrstaBibliotekara.setSelectedItem(korisnik.getVrstaBibliotekara());
        cbVrstaBibliotekara.addItem(VrstaBibliotekara.ADMINISTRATOR);
        cbVrstaBibliotekara.addItem(VrstaBibliotekara.OBRADJIVAC);
        cbVrstaBibliotekara.addItem(VrstaBibliotekara.POZAJMLJIVAC);
        cbVrstaBibliotekara.setEnabled(false);
        cbVrstaKorisnika.setEnabled(false);
        cbStatusAktivnosti.setEnabled(false);
        tfJmbg.setEditable(false);
        cbVrstaClana.setEnabled(false);
        datePickerRodjene.setVisible(false);
        cbTipClanarine.setVisible(false);

        if (korisnikAkter instanceof Bibliotekar){

            if (((Bibliotekar) korisnikAkter).getVrstaBibliotekara() == VrstaBibliotekara.ADMINISTRATOR){
                cbVrstaKorisnika.setEnabled(true);
                cbVrstaBibliotekara.setEnabled(true);
                cbStatusAktivnosti.setEnabled(true);
            }
        }
    }

    private void akcije() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DodavanjeIzmenaKorisnika.this.dispose();
                DodavanjeIzmenaKorisnika.this.setVisible(false);
                roditelj.setVisible(true);
                super.windowClosing(e);
            }
        });

        btnSacuvaj.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String ime = tfIme.getText().trim();
                String prezime = tfPrezime.getText().trim();
                String korisnickoIme = tfKorisnickoIme.getText().trim();
                String lozinka = tfLozinka.getText().trim();
                VrstaKorisnika vrstaKorisnika = (VrstaKorisnika) cbVrstaKorisnika.getSelectedItem();
                String jmbg = tfJmbg.getText().trim();
                LocalDate datumRodjenja = getSelectedDate(datePickerRodjene);
                VrstaClana vrstaClana = (VrstaClana) cbVrstaClana.getSelectedItem();
                VrstaBibliotekara vrstaBibliotekara  = (VrstaBibliotekara) cbVrstaBibliotekara.getSelectedItem();
                String statusAktivnosti = (String) cbStatusAktivnosti.getSelectedItem();
                TipClanarine trenutnaClanarina = (TipClanarine) cbTipClanarine.getSelectedItem();

                if (korisnik != null && korisnik instanceof Clan && korisnikAkter instanceof Clan){

                    modifikacijaClanaOdStraneClana(ime, prezime, korisnickoIme, lozinka, datumRodjenja, jmbg);
                    TabelaClanovaIBibliotekara.osveziTabelu(false, bibliotekarController, clanController);
                }

                if (korisnik != null && korisnik instanceof Clan && korisnikAkter instanceof Bibliotekar){

                    if (((Bibliotekar) korisnikAkter).getVrstaBibliotekara() == VrstaBibliotekara.ADMINISTRATOR){

                        modifikacijaClanaOdStraneAdmina(ime, prezime, korisnickoIme, lozinka, datumRodjenja,
                                vrstaKorisnika, statusAktivnosti, jmbg, datumRodjenja, vrstaClana);
                        TabelaClanovaIBibliotekara.osveziTabelu(false, bibliotekarController, clanController);
                    }

                    else {

                        modifikacijaClanaOdStraneOstalihBibliotekara(ime, prezime, korisnickoIme, lozinka, datumRodjenja,
                                vrstaKorisnika, statusAktivnosti, jmbg, datumRodjenja, vrstaClana);
                    }
                }

                if (korisnik != null && korisnik instanceof Bibliotekar && korisnikAkter instanceof Bibliotekar){

                    modifikacijaBibliotekaraOdStraneAdmina(ime, prezime, korisnickoIme, lozinka, vrstaKorisnika,
                            statusAktivnosti, vrstaBibliotekara);
                    TabelaClanovaIBibliotekara.osveziTabelu(true, bibliotekarController, clanController);
                }

                if (korisnik == null && registracija == VrstaRegistracije.REGISTRACIJA_CLANA){

                    registracijaClana(ime, prezime, korisnickoIme, lozinka, vrstaKorisnika, jmbg, datumRodjenja, vrstaClana, trenutnaClanarina);
                    TabelaClanovaIBibliotekara.osveziTabelu(false, bibliotekarController, clanController);
                }

                else if (korisnik == null && registracija == VrstaRegistracije.REGISTRACIJA_BIBLIOTEKARA){

                    registracijaBibliotekara(ime, prezime, korisnickoIme, lozinka, vrstaKorisnika, vrstaBibliotekara);
                    TabelaClanovaIBibliotekara.osveziTabelu(true, bibliotekarController, clanController);
                }
            }
        });

        btnOdustani.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DodavanjeIzmenaKorisnika.this.dispose();
            }
        });
    }

    private void registracijaBibliotekara(String ime, String prezime, String korisnickoIme, String lozinka, VrstaKorisnika vrstaKorisnika,
                                          VrstaBibliotekara vrstaBibliotekara) {

        if (this.bibliotekarController.unosValidan(ime, prezime, korisnickoIme, lozinka)) {
            int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate date podatke?", "Pitanje", JOptionPane.YES_NO_CANCEL_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                if (DodavanjeIzmenaKorisnika.this.korisnik == null) {
                    this.bibliotekarController.registracijaBibliotekara(ime, prezime,
                            korisnickoIme, lozinka, vrstaKorisnika, vrstaBibliotekara);
                }

                DodavanjeIzmenaKorisnika.this.dispose();
            }
        }
    }

    private void registracijaClana(String ime, String prezime, String korisnickoIme, String lozinka, VrstaKorisnika vrstaKorisnika,
                                   String jmbg, LocalDate datumRodjenja, VrstaClana vrstaClana, TipClanarine trenutnaClanarina) {

        if (this.clanController.unosValidan(ime, prezime, korisnickoIme, lozinka, jmbg, datumRodjenja)) {
            int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate date podatke?", "Pitanje", JOptionPane.YES_NO_CANCEL_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                if (DodavanjeIzmenaKorisnika.this.korisnik == null) {
                    Clan clan = this.clanController.registracijaClana(ime, prezime,
                            korisnickoIme, lozinka, vrstaKorisnika, jmbg, datumRodjenja, vrstaClana);
                    Clanarina clanarina = this.clanarinaController.getTrenutnaClanarinaNaOsnovuTipa((TipClanarine) cbTipClanarine.getSelectedItem());
                    this.izdataClanarinaController.izdavanjeClanarine(clanarina, clan);
                }
                TabelaClanovaIBibliotekara.osveziTabelu(false, bibliotekarController, clanController);
                DodavanjeIzmenaKorisnika.this.dispose();
            }
        }
    }

    private void modifikacijaClanaOdStraneAdmina(String ime, String prezime, String korisnickoIme, String lozinka,
                                                 LocalDate datumRodjenja, VrstaKorisnika vrstaKorisnika, String statusAktivnosti,
                                                 String jmbg, LocalDate datumRodjenja1, VrstaClana vrstaClana) {

        if (this.clanController.unosValidan(ime, prezime, korisnickoIme, lozinka, jmbg, datumRodjenja)) {
            int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate date podatke?", "Pitanje", JOptionPane.YES_NO_CANCEL_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                if (DodavanjeIzmenaKorisnika.this.korisnik != null) {
                    this.clanController.modifikacijaPodatakaOdStraneAdmina(this.korisnik.getId(), ime, prezime,
                            korisnickoIme, lozinka, vrstaKorisnika, statusAktivnosti, jmbg, datumRodjenja, vrstaClana);
                }

                DodavanjeIzmenaKorisnika.this.dispose();
            }
        }
    }

    private void modifikacijaClanaOdStraneClana(String ime, String prezime, String korisnickoIme, String lozinka, LocalDate datumRodjenja, String jmbg){

        if (this.clanController.unosValidan(ime, prezime, korisnickoIme, lozinka, jmbg, datumRodjenja)) {
            int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate date podatke?", "Pitanje", JOptionPane.YES_NO_CANCEL_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                if (DodavanjeIzmenaKorisnika.this.korisnik != null) {
                    this.clanController.modifikacijaPodatakaOdStraneClana(this.korisnik.getId(), ime, prezime, korisnickoIme, lozinka, datumRodjenja);
                    //this.fabrikaRepo.sacuvajPodatke();
                }

                DodavanjeIzmenaKorisnika.this.dispose();
            }
        }
    }

    private void modifikacijaClanaOdStraneOstalihBibliotekara(String ime, String prezime, String korisnickoIme,
                                                              String lozinka, LocalDate datumRodjenja,
                                                              VrstaKorisnika vrstaKorisnika, String statusAktivnosti,
                                                              String jmbg, LocalDate datumRodjenja1, VrstaClana vrstaClana) {

        if (this.clanController.unosValidan(ime, prezime, korisnickoIme, lozinka, jmbg, datumRodjenja)) {
            int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate date podatke?", "Pitanje", JOptionPane.YES_NO_CANCEL_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                if (DodavanjeIzmenaKorisnika.this.korisnik != null) {
                    this.clanController.modifikacijaPodatakaOdStraneAdmina(this.korisnik.getId(), ime, prezime,
                            korisnickoIme, lozinka, vrstaKorisnika, statusAktivnosti, jmbg, datumRodjenja, vrstaClana);
                }

                DodavanjeIzmenaKorisnika.this.dispose();
                TabelaClanovaIBibliotekara.osveziTabelu(false, bibliotekarController, clanController);
            }
        }
    }

    private void modifikacijaBibliotekaraOdStraneAdmina(String ime, String prezime, String korisnickoIme, String lozinka,
                                                        VrstaKorisnika vrstaKorisnika,
                                                        String statusAktivnosti, VrstaBibliotekara vrstaBibliotekara) {

        if (this.bibliotekarController.unosValidan(ime, prezime, korisnickoIme, lozinka)) {
            int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da sacuvate date podatke?", "Pitanje", JOptionPane.YES_NO_CANCEL_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                if (DodavanjeIzmenaKorisnika.this.korisnik != null) {
                    this.bibliotekarController.modifikacijaBibliotekara(this.korisnik.getId(), ime, prezime,
                            korisnickoIme, lozinka, vrstaKorisnika, statusAktivnosti, vrstaBibliotekara);
                }

                DodavanjeIzmenaKorisnika.this.dispose();
            }
        }
    }

    private void podesiInicijalniDatumRodjenja(Clan korisnik) {
        int godina = korisnik.getDatumRodjenja().getYear();
        int mesec = korisnik.getDatumRodjenja().getMonthValue() - 1;
        int dan = korisnik.getDatumRodjenja().getDayOfMonth();
        model.setDate(godina, mesec, dan);
        model.setSelected(true);
    }

    private void podesiDatePickerRodjenja() {
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePickerRodjene = new JDatePickerImpl(datePanel, new DatumLabelaFormater());
    }

    private LocalDate getSelectedDate(JDatePickerImpl datePicker) {
        Date datum = (Date) datePicker.getModel().getValue();
        if (datum != null) {
            return datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }
}
