package view.pozajmljivac;

import kontroler.*;
import model.Bibliotekar;
import model.Rezervacija;
import net.miginfocom.swing.MigLayout;
import repozitorijum.FabrikaRepo;
import res.ResourceLoader;
import view.modeliTabela.TabelaClanovaIBibliotekara;
import view.univerzalno.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class PozajmljivacFrame extends JFrame {

    protected Bibliotekar bibliotekar;
    private KorisnikController korisnikController;
    private FabrikaRepo fabrikaRepo;
    private IzdatPrimerakController izdatPrimerakController;
    private RezervisanPrimerakController rezervisanPrimerakController;
    private DatumiController datumiController;
    private ClanController clanController;
    private KnjigaController knjigaController;
    private ClanarinaController clanarinaController;
    private IzdataClanarinaController izdataClanarinaController;

    protected JButton btnClanovi = new JButton("Clanovi");
    protected JButton btnIzdavanjeKnjiga = new JButton("Izdaj knjigu clanu");
    protected JButton btnUvidUizdateKnjige = new JButton("Uvid u izdate knjige");
    protected JButton btnIzvestajOCitanosti = new JButton("Izvestaj o citanosti");
    protected JButton btnPretragaKnjiga = new JButton("Pretraga knjiga");
    protected JButton btnProduzenjeClanarine = new JButton("Produzenje clanarine");
    protected JButton btnRezervacije = new JButton("Rezervacije");
    protected JButton btnOdjava = new JButton("Odjava");

    public PozajmljivacFrame(Bibliotekar bibliotekar, KorisnikController korisnikController, FabrikaRepo fabrikaRepo) {
        this.bibliotekar = bibliotekar;
        this.korisnikController = korisnikController;
        this.fabrikaRepo = fabrikaRepo;
        this.clanController = new ClanController(fabrikaRepo.getClanRepo(), fabrikaRepo.getKorisnikRepo());
        this.izdatPrimerakController = new IzdatPrimerakController(fabrikaRepo.getIzdatPrimerakRepo(),
                new RezervisanPrimerakController(fabrikaRepo.getRezervacijaRepo(), fabrikaRepo.getIzdatPrimerakRepo()));
        this.rezervisanPrimerakController = new RezervisanPrimerakController(fabrikaRepo.getRezervacijaRepo(), fabrikaRepo.getIzdatPrimerakRepo());
        this.datumiController = new DatumiController();
        this.knjigaController = new KnjigaController(fabrikaRepo.getKnjigeRepo());
        this.clanarinaController = new ClanarinaController(fabrikaRepo.getClanarinaRepo());
        this.izdataClanarinaController = new IzdataClanarinaController(fabrikaRepo.getIzdataClanarinaRepo());

        setTitle("Pozajmljivac: " + bibliotekar.getIme() + " " + bibliotekar.getPrezime());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(900,650));
        initGUI();
        pack();
        setLocationRelativeTo(null);
        initAkcije();
    }

    private void initGUI() {
        podesiDugmad();
        podesiLeviDeo();
        podesiCentar();
    }

    private void podesiDugmad() {
        Dimension d = new Dimension(260,30);
        btnClanovi.setPreferredSize(d);
        btnIzdavanjeKnjiga.setPreferredSize(d);
        btnPretragaKnjiga.setPreferredSize(d);
        btnIzvestajOCitanosti.setPreferredSize(d);
        btnUvidUizdateKnjige.setPreferredSize(d);
        btnProduzenjeClanarine.setPreferredSize(d);
        btnRezervacije.setPreferredSize(d);
        btnOdjava.setPreferredSize(d);
    }

    private void podesiLeviDeo() {
        JPanel panelZapad = new JPanel(new MigLayout());
        panelZapad.setBackground(new Color(193, 229, 255));

        JLabel clanoviImage = kreirajLabeluSaSlikom("clanovi.png", 45, 45);
        JLabel izdavanjeKnjigaImage = kreirajLabeluSaSlikom("izdavanjeKnjige.png",45,45);
        JLabel uvidUIzdateKnjigeImage = kreirajLabeluSaSlikom("information.png",45,45);
        JLabel pretragraKnjigaImage = kreirajLabeluSaSlikom("book.png", 45, 45);
        JLabel izvestajOCitanostiImage = kreirajLabeluSaSlikom("izvestaji.png", 45, 45);
        JLabel produzenjeClanarineImage = kreirajLabeluSaSlikom("produzenjeClanarine.png", 45, 45);
        JLabel odjavaImage = kreirajLabeluSaSlikom("logout.png", 45, 45);
        JLabel rezervacijaImage = kreirajLabeluSaSlikom("reserved.png", 45, 45);

        panelZapad.add(clanoviImage, "split 2, sg a");
        panelZapad.add(btnClanovi, "wrap");

        panelZapad.add(izdavanjeKnjigaImage, "split 2, sg a");
        panelZapad.add(btnIzdavanjeKnjiga, "wrap");

        panelZapad.add(uvidUIzdateKnjigeImage, "split 2, sg a");
        panelZapad.add(btnUvidUizdateKnjige, "wrap");

        panelZapad.add(pretragraKnjigaImage, "split 2, sg a");
        panelZapad.add(btnPretragaKnjiga, "wrap");

        panelZapad.add(izvestajOCitanostiImage, "split 2, sg a");
        panelZapad.add(btnIzvestajOCitanosti, "wrap");

        panelZapad.add(produzenjeClanarineImage, "split 2, sg a");
        panelZapad.add(btnProduzenjeClanarine, "wrap");

        panelZapad.add(rezervacijaImage, "split 2, sg a");
        panelZapad.add(btnRezervacije, "wrap");

        panelZapad.add(odjavaImage, "sg a, split 2");
        panelZapad.add(btnOdjava, "wrap");

        this.getContentPane().add(panelZapad, BorderLayout.WEST);
    }

    private void podesiCentar() {
        JPanel panelCentar = new JPanel(new MigLayout());
        panelCentar.setBackground(Color.white);

        JLabel userIconLabel = kreirajLabeluSaSlikom("profile.png", 130, 130);

        panelCentar.add(userIconLabel, "span, center, wrap");
        panelCentar.add(new JLabel(" "), "span, wrap");
        panelCentar.add(new JLabel("                 Ime:"), "split 2, sg a");
        panelCentar.add(new JLabel(this.bibliotekar.getIme()), "wrap");
        panelCentar.add(new JLabel("                 Prezime:"), "split 2, sg a");
        panelCentar.add(new JLabel(this.bibliotekar.getPrezime()), "wrap");
        panelCentar.add(new JLabel("                 Korisničko ime:"), "split 2, sg a");
        panelCentar.add(new JLabel(this.bibliotekar.getKorisnickoIme()), "wrap");
        panelCentar.add(new JLabel("                                                                      "
                + "                                                    "), "span, wrap");

        this.getContentPane().add(panelCentar, BorderLayout.CENTER);
    }

    private JLabel kreirajLabeluSaSlikom(String imagePath, int width, int height) {
        JLabel labelImage = new JLabel();
        ImageIcon icon = ResourceLoader.getImageIcon(imagePath);
        ImageIcon iconResized = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        labelImage.setIcon(iconResized);
        return labelImage;
    }


    private void initAkcije() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
                MainFrame login = new MainFrame(korisnikController, fabrikaRepo);
                login.setVisible(true);
            }
        });

        btnPretragaKnjiga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PretragaKnjigaDialog pretragaKnjigaDialog = new PretragaKnjigaDialog(PozajmljivacFrame.this, true, fabrikaRepo.getKnjigeRepo(), fabrikaRepo.getKategorijeKnjigaRepo());
                pretragaKnjigaDialog.setVisible(true);
            }
        });

        btnClanovi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                TabelaClanovaIBibliotekara c = new TabelaClanovaIBibliotekara(PozajmljivacFrame.this, false, fabrikaRepo, bibliotekar);
                c.setVisible(true);
            }
        });

        btnUvidUizdateKnjige.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UvidUIzdateKnjigeDialog uvidUIzdateKnjigeDialog = new UvidUIzdateKnjigeDialog(PozajmljivacFrame.this, true, izdatPrimerakController, rezervisanPrimerakController);
                uvidUIzdateKnjigeDialog.setVisible(true);
            }
        });

        btnOdjava.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                MainFrame login = new MainFrame(korisnikController, fabrikaRepo);
                login.setVisible(true);
            }
        });

        btnIzvestajOCitanosti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IzvestajOCitanostiDialog izvestajOCitanostiDialog = new IzvestajOCitanostiDialog(PozajmljivacFrame.this, true, fabrikaRepo,
                        datumiController, izdatPrimerakController );
                izvestajOCitanostiDialog.setVisible(true);
            }
        });

        btnIzdavanjeKnjiga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IzdavanjaKnjigaDialog izdavanjaKnjigaDialog =
                    new IzdavanjaKnjigaDialog(PozajmljivacFrame.this, true, fabrikaRepo.getKnjigeRepo(),
                            fabrikaRepo.getKategorijeKnjigaRepo(), clanController, knjigaController, izdatPrimerakController);
                izdavanjaKnjigaDialog.setVisible(true);
            }
        });

        btnProduzenjeClanarine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProduzenjeClanarineDialog produzenjeClanarineDialog = new ProduzenjeClanarineDialog(PozajmljivacFrame.this, true,
                        clanarinaController, clanController, izdataClanarinaController);
                produzenjeClanarineDialog.setVisible(true);
            }
        });

        btnRezervacije.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Rezervacija> rezervacije = rezervisanPrimerakController.getListaAktivnihRezervacija();
                if (rezervacije.size() == 0) {
                    System.out.println("Trenutno nema rezervacija");
                }
                else {
                    RezervacijeDialog rezervacijeDialog = new RezervacijeDialog(PozajmljivacFrame.this, true, izdatPrimerakController, rezervisanPrimerakController);
                    rezervacijeDialog.setVisible(true);
                }
            }
        });
    }
}
