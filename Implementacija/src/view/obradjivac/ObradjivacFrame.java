package view.obradjivac;

import kontroler.*;
import model.Bibliotekar;
import net.miginfocom.swing.MigLayout;
import repozitorijum.FabrikaRepo;
import res.ResourceLoader;
import view.administrator.AdminFrame;
import view.modeliTabela.TabelaClanovaIBibliotekara;
import view.pozajmljivac.PozajmljivacFrame;
import view.univerzalno.IzvestajOCitanostiDialog;
import view.univerzalno.MainFrame;
import view.univerzalno.PretragaKnjigaDialog;
import view.univerzalno.ProduzenjeClanarineDialog;
import view.univerzalno.IzmenaKnjigaDialog;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ObradjivacFrame extends JFrame {

    protected Bibliotekar bibliotekar;
    private KorisnikController korisnikController;
    private FabrikaRepo fabrikaRepo;
    private DatumiController datumiController;
    private IzdatPrimerakController izdatPrimerakController;
    private ClanController clanController;
    private ClanarinaController clanarinaController;
    private IzdataClanarinaController izdataClanarinaController;

    protected JButton btnInfoOKnjigama = new JButton("Knjige");
    protected JButton btnClanovi = new JButton("Clanovi");
    protected JButton btnPretragaKnjiga = new JButton("Pretraga knjiga");
    protected JButton btnIzvestajONabavci = new JButton("Izvestaj o nabavci");
    protected JButton btnIzvestajOCitanosti = new JButton("Izvestaj o citanosti");
    protected JButton btnOdjava = new JButton("Odjava");
    protected JButton btnProduzenjeClanarine = new JButton("Produzenje clanarine");


    public ObradjivacFrame(Bibliotekar bibliotekar, KorisnikController korisnikController, FabrikaRepo fabrikaRepo) {
        this.bibliotekar = bibliotekar;
        this.korisnikController = korisnikController;
        this.fabrikaRepo = fabrikaRepo;
        this.datumiController = new DatumiController();
        this.izdatPrimerakController = new IzdatPrimerakController(fabrikaRepo.getIzdatPrimerakRepo(), new RezervisanPrimerakController(fabrikaRepo.getRezervacijaRepo(), fabrikaRepo.getIzdatPrimerakRepo()));
        this.clanarinaController = new ClanarinaController(fabrikaRepo.getClanarinaRepo());
        this.clanController = new ClanController(fabrikaRepo.getClanRepo(), fabrikaRepo.getKorisnikRepo());
        this.izdataClanarinaController = new IzdataClanarinaController(fabrikaRepo.getIzdataClanarinaRepo());

        setTitle("Obradjivac: " + bibliotekar.getIme() + " " + bibliotekar.getPrezime());
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
        btnInfoOKnjigama.setPreferredSize(d);
        btnIzvestajONabavci.setPreferredSize(d);
        btnPretragaKnjiga.setPreferredSize(d);
        btnIzvestajOCitanosti.setPreferredSize(d);
        btnProduzenjeClanarine.setPreferredSize(d);
        btnOdjava.setPreferredSize(d);
    }

    private void podesiLeviDeo() {
        JPanel panelZapad = new JPanel(new MigLayout());
        panelZapad.setBackground(new Color(193, 229, 255));

        JLabel clanoviImage = kreirajLabeluSaSlikom("clanovi.png", 45, 45);
        JLabel promenaPodatakaOKnjigamaImage = kreirajLabeluSaSlikom("information.png",45,45);
        JLabel pretragraKnjigaImage = kreirajLabeluSaSlikom("book.png",45,45);
        JLabel izvestajONabavciImage = kreirajLabeluSaSlikom("archive.png", 45, 45);
        JLabel izvestajOCitanostiImage = kreirajLabeluSaSlikom("izvestaji.png", 45, 45);
        JLabel produzenjeClanarineImage = kreirajLabeluSaSlikom("produzenjeClanarine.png", 45, 45);
        JLabel odjavaImage = kreirajLabeluSaSlikom("logout.png", 45, 45);

        panelZapad.add(clanoviImage, "split 2, sg a");
        panelZapad.add(btnClanovi, "wrap");

        panelZapad.add(promenaPodatakaOKnjigamaImage, "split 2, sg a");
        panelZapad.add(btnInfoOKnjigama, "wrap");

        panelZapad.add(pretragraKnjigaImage, "split 2, sg a");
        panelZapad.add(btnPretragaKnjiga, "wrap");

        panelZapad.add(izvestajONabavciImage, "split 2, sg a");
        panelZapad.add(btnIzvestajONabavci, "wrap");

        panelZapad.add(izvestajOCitanostiImage, "split 2, sg a");
        panelZapad.add(btnIzvestajOCitanosti, "wrap");

        panelZapad.add(produzenjeClanarineImage, "split 2, sg a");
        panelZapad.add(btnProduzenjeClanarine, "wrap");

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

        btnOdjava.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                MainFrame login = new MainFrame(korisnikController, fabrikaRepo);
                login.setVisible(true);
            }
        });

        btnClanovi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                TabelaClanovaIBibliotekara c = new TabelaClanovaIBibliotekara(ObradjivacFrame.this, false, fabrikaRepo, bibliotekar);
                c.setVisible(true);
            }
        });

        btnInfoOKnjigama.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                IzmenaKnjigaDialog izmenaKnjigadialog = new IzmenaKnjigaDialog(ObradjivacFrame.this, true, fabrikaRepo.getKnjigeRepo(), fabrikaRepo.getKategorijeKnjigaRepo(),fabrikaRepo.getPrimerakKnjigeRepo(),fabrikaRepo.getIzdatPrimerakRepo(),fabrikaRepo.getRezervacijaRepo(),fabrikaRepo);
                izmenaKnjigadialog.setVisible(true);
            }
        });

        btnIzvestajONabavci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(null, "Nije implementirano", "Informacija", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnPretragaKnjiga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PretragaKnjigaDialog pretragaKnjigaDialog = new PretragaKnjigaDialog(ObradjivacFrame.this, true, fabrikaRepo.getKnjigeRepo(), fabrikaRepo.getKategorijeKnjigaRepo());
                pretragaKnjigaDialog.setVisible(true);
            }
        });

        btnIzvestajOCitanosti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IzvestajOCitanostiDialog izvestajOCitanostiDialog = new IzvestajOCitanostiDialog(ObradjivacFrame.this, true, fabrikaRepo,
                        datumiController, izdatPrimerakController );
                izvestajOCitanostiDialog.setVisible(true);
            }
        });

        btnProduzenjeClanarine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProduzenjeClanarineDialog produzenjeClanarineDialog = new ProduzenjeClanarineDialog(ObradjivacFrame.this, true,
                        clanarinaController, clanController, izdataClanarinaController);
                produzenjeClanarineDialog.setVisible(true);
            }
        });
    }


}
