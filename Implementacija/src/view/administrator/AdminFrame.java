package view.administrator;

import kontroler.*;
import model.Bibliotekar;
import net.miginfocom.swing.MigLayout;
import repozitorijum.FabrikaRepo;
import res.ResourceLoader;
import view.modeliTabela.TabelaClanovaIBibliotekara;
import view.obradjivac.ObradjivacFrame;
import view.univerzalno.IzvestajOCitanostiDialog;
import view.univerzalno.MainFrame;
import view.univerzalno.PretragaKnjigaDialog;
import view.univerzalno.ProduzenjeClanarineDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminFrame extends JFrame {

    private static final long serialVersionUID = 7124917562823111509L;
    protected Bibliotekar ulogovanKorisnik;

    protected JButton btnClanovi = new JButton("Clanovi");
    protected JButton btnBibliotekari = new JButton("Bibliotekari");
    protected JButton btnIzvestajCitanosti = new JButton("Izvestaj o citanosti");
    protected JButton btnProduzenjeClanarine = new JButton("Produzenje clanarine");
    protected JButton btnCenovnik = new JButton("Cenovnik");
    protected JButton btnOdjava = new JButton("Odjava");
    protected JButton btnPretragaKnjiga = new JButton("Pretraga knjiga");
    private ClanController clanController;
    private ClanarinaController clanarinaController;
    private IzdataClanarinaController izdataClanarinaController;
    private DatumiController datumiController;
    private IzdatPrimerakController izdatPrimerakController;

    public AdminFrame(Bibliotekar bibliotekar, KorisnikController korisnikController, FabrikaRepo fabrikaRepo) throws HeadlessException {
        this.ulogovanKorisnik = bibliotekar;
        this.clanarinaController = new ClanarinaController(fabrikaRepo.getClanarinaRepo());
        this.clanController = new ClanController(fabrikaRepo.getClanRepo(), fabrikaRepo.getKorisnikRepo());
        this.izdataClanarinaController = new IzdataClanarinaController(fabrikaRepo.getIzdataClanarinaRepo());
        this.datumiController = new DatumiController();
        this.izdataClanarinaController = new IzdataClanarinaController(fabrikaRepo.getIzdataClanarinaRepo());
        setTitle("Administrator: " + bibliotekar.getIme() + " " + bibliotekar.getPrezime());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(900,650));
        initGUI();
        pack();
        setLocationRelativeTo(null);
        initAkcije(korisnikController, fabrikaRepo);
    }

    private void initGUI() {
        podesiDugmad();
        podesiLeviDeo();
        podesiCentar();
    }

    private void podesiDugmad() {
        Dimension d = new Dimension(260,30);
        btnClanovi.setPreferredSize(d);
        btnBibliotekari.setPreferredSize(d);
        btnIzvestajCitanosti.setPreferredSize(d);
        btnProduzenjeClanarine.setPreferredSize(d);
        btnCenovnik.setPreferredSize(d);
        btnPretragaKnjiga.setPreferredSize(d);
        btnOdjava.setPreferredSize(d);
    }

    private void podesiCentar() {
        JPanel panelCentar = new JPanel(new MigLayout());
        panelCentar.setBackground(Color.white);

        JLabel userIconLabel = kreirajLabeluSaSlikom("profile.png", 130, 130);

        panelCentar.add(userIconLabel, "span, center, wrap");
        panelCentar.add(new JLabel(" "), "span, wrap");
        panelCentar.add(new JLabel("                 Ime:"), "split 2, sg a");
        panelCentar.add(new JLabel(this.ulogovanKorisnik.getIme()), "wrap");
        panelCentar.add(new JLabel("                 Prezime:"), "split 2, sg a");
        panelCentar.add(new JLabel(this.ulogovanKorisnik.getPrezime()), "wrap");
        panelCentar.add(new JLabel("                 Korisnicko ime:"), "split 2, sg a");
        panelCentar.add(new JLabel(this.ulogovanKorisnik.getKorisnickoIme()), "wrap");
        panelCentar.add(new JLabel("                                                                      "
                + "                                                    "), "span, wrap");

        this.getContentPane().add(panelCentar, BorderLayout.CENTER);

    }

    private void podesiLeviDeo() {
        JPanel panelZapad = new JPanel(new MigLayout());
        panelZapad.setBackground(new Color(193, 229, 255));

        JLabel laborantiImage = kreirajLabeluSaSlikom("clanovi.png",45,45);
        JLabel medicinariImage = kreirajLabeluSaSlikom("username.png", 45, 45);
        JLabel analizeImage = kreirajLabeluSaSlikom("archive.png", 45, 45);
        JLabel grupeAnalizaImage = kreirajLabeluSaSlikom("produzenjeClanarine.png", 45, 45);
        JLabel podesavanjeKoeficijenataImage = kreirajLabeluSaSlikom("podesavanja.png", 45, 45);

        JLabel zahteviIzvestajImage = kreirajLabeluSaSlikom("information.png",45,45);
        JLabel pacijentiIzvestajImage = kreirajLabeluSaSlikom("clanovi.png", 45, 45);
        JLabel prihodiRashodiImage = kreirajLabeluSaSlikom("coins.png", 45, 45);
        JLabel zavrseniNalaziImage = kreirajLabeluSaSlikom("obradaRezultata.png", 45, 45);
        JLabel nalaziUObradiImage = kreirajLabeluSaSlikom("uObradi.png", 45, 45);
        JLabel zahteviNaCekanjuImage = kreirajLabeluSaSlikom("question.png", 45, 45);
        JLabel pretragaKnjigaImage = kreirajLabeluSaSlikom("book.png", 45, 45);
        JLabel odjavaImage = kreirajLabeluSaSlikom("logout.png", 45, 45);


        panelZapad.add(laborantiImage, "split 2, sg a");
        panelZapad.add(btnClanovi, "wrap");
        panelZapad.add(medicinariImage, "split 2, sg a");
        panelZapad.add(btnBibliotekari, "wrap");
        panelZapad.add(analizeImage, "split 2, sg a");
        panelZapad.add(btnIzvestajCitanosti, "wrap");
        panelZapad.add(grupeAnalizaImage, "split 2, sg a");
        panelZapad.add(btnProduzenjeClanarine, "wrap");
        panelZapad.add(zahteviIzvestajImage, "split 2, sg a");
        panelZapad.add(btnCenovnik, "wrap");

        panelZapad.add(pretragaKnjigaImage, "sg a, split 2");
        panelZapad.add(btnPretragaKnjiga, "wrap");

        panelZapad.add(odjavaImage, "sg a, split 2");
        panelZapad.add(btnOdjava, "wrap");

        this.getContentPane().add(panelZapad, BorderLayout.WEST);
    }

    private JLabel kreirajLabeluSaSlikom(String imagePath, int width, int height) {
        JLabel labelImage = new JLabel();
        ImageIcon icon = ResourceLoader.getImageIcon(imagePath);
        ImageIcon iconResized = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        labelImage.setIcon(iconResized);
        return labelImage;
    }

    private void initAkcije(KorisnikController korisnikController, FabrikaRepo fabrikaRepo) {
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
                TabelaClanovaIBibliotekara c = new TabelaClanovaIBibliotekara(AdminFrame.this, false, fabrikaRepo, ulogovanKorisnik);
                c.setVisible(true);
            }
        });

        btnBibliotekari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                TabelaClanovaIBibliotekara c = new TabelaClanovaIBibliotekara(AdminFrame.this, true, fabrikaRepo, ulogovanKorisnik);
                c.setVisible(true);
            }
        });

        btnIzvestajCitanosti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IzvestajOCitanostiDialog izvestajOCitanostiDialog = new IzvestajOCitanostiDialog(AdminFrame.this, true, fabrikaRepo,
                        datumiController, izdatPrimerakController );
                izvestajOCitanostiDialog.setVisible(true);
            }
        });

        btnProduzenjeClanarine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProduzenjeClanarineDialog produzenjeClanarineDialog = new ProduzenjeClanarineDialog(AdminFrame.this, true,
                        clanarinaController, clanController, izdataClanarinaController);
                produzenjeClanarineDialog.setVisible(true);
            }
        });

        btnCenovnik.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cenovnik ce = new Cenovnik(AdminFrame.this, fabrikaRepo);
                ce.setVisible(true);
            }
        });

        btnPretragaKnjiga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PretragaKnjigaDialog pretragaKnjigaDialog = new PretragaKnjigaDialog(AdminFrame.this, true, fabrikaRepo.getKnjigeRepo(), fabrikaRepo.getKategorijeKnjigaRepo());
                pretragaKnjigaDialog.setVisible(true);
            }
        });
    }
}
