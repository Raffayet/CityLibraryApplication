package view.univerzalno;

import kontroler.KorisnikController;
import izuzeci.KorisnikNijeNadjen;
import model.Korisnik;
import net.miginfocom.swing.MigLayout;
import repozitorijum.FabrikaRepo;
import res.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = -8026416994513756565L;

    private KorisnikController korisnikController;
    private FabrikaRepo fabrikaRepo;

    public MainFrame() {
    }

    JButton btnPrijava = new JButton("Prijava");
    JButton btnIzlaz = new JButton("Izlaz");

    JTextField tfKorisnickoIme = new JTextField(20);
    JPasswordField pfLozinka = new JPasswordField(20);
    private JPanel panel1;

    public MainFrame(KorisnikController korisnikController, FabrikaRepo fabrikaRepo) {
        this.setIconImage(ResourceLoader.getImageIcon("main.png").getImage());
        this.korisnikController = korisnikController;
        this.fabrikaRepo = fabrikaRepo;
        this.setTitle("Prijava");
        this.setLayout(new MigLayout());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.getContentPane().setBackground(new Color(193, 229, 255));
        initGUI();
        this.pack();
        this.setLocationRelativeTo(null);
        initAkcije(korisnikController, fabrikaRepo);
    }

    private void initGUI() {
        JLabel usernameImage = new JLabel();
        ImageIcon userIcon = ResourceLoader.getImageIcon("username.png");
        usernameImage.setIcon(userIcon);

        JLabel passwordImage = new JLabel();
        ImageIcon passwordIcon = ResourceLoader.getImageIcon("password.png");
        passwordImage.setIcon(passwordIcon);

        this.getRootPane().setDefaultButton(btnPrijava);

        this.add(new JLabel("Dobrodošli. Prijavite se na sistem."), "span, center, wrap");
        this.add(usernameImage, "split 3, sg b");
        this.add(new JLabel("Korisničko ime: "), "sg a");
        this.add(tfKorisnickoIme, "pushx, growx, wrap");
        this.add(passwordImage, "split 3, sg b");
        this.add(new Label("Lozinka: "), "sg a");
        this.add(pfLozinka, "pushx, growx, wrap");
        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(btnPrijava);
        this.add(btnIzlaz);
    }

    private void initAkcije(KorisnikController korisnikController, FabrikaRepo fabrikaRepo) {

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                proveraIzlaksaUpit();
            }
        });

        btnIzlaz.addActionListener(e -> proveraIzlaksaUpit());

        btnPrijava.addActionListener(e -> {
            String korisnickoIme = tfKorisnickoIme.getText();
            String lozinka = new String(pfLozinka.getPassword());
            if (korisnikController.korektniPodaciZaLogIn(korisnickoIme, lozinka) && korisnikController.korisnikPostoji(korisnickoIme, lozinka)) {
                proveraKorisnika(korisnickoIme, lozinka, korisnikController, fabrikaRepo);
            } else {
                JOptionPane.showMessageDialog(null, "Unesite korisnicko ime i lozinku.", "Greska",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        pfLozinka.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    tfKorisnickoIme.requestFocusInWindow();
                }
            }
        });

        tfKorisnickoIme.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    pfLozinka.requestFocusInWindow();
                }
            }
        });

    }

    private void proveraIzlaksaUpit() {
        int option = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da napustite aplikaciju?",
                "Kraj rada", JOptionPane.YES_NO_CANCEL_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            this.fabrikaRepo.sacuvajPodatke();
            System.exit(0);
        }
    }

    private void proveraKorisnika(String korisnickoIme, String lozinka, KorisnikController korisnikController, FabrikaRepo fabrikaRepo) { // prosledjuju se kont i repo zbog pozivanja
                                                                                                                                            //odgovarajuceg mainframe konstruktora
                                                                                                                                            //prilikom odjave korisnika
        try {
            Korisnik korisnik = korisnikController.dobavljanjeRegistrovanogKorisnika(korisnickoIme, lozinka);
            korisnikController.otvaranjeProzoraZaKorisnika(korisnik, this, korisnikController, fabrikaRepo);
        } catch (KorisnikNijeNadjen e) {
            JOptionPane.showMessageDialog(null, "Korisnik nije pronadjen.\nProverite podatke i probajte ponovo.",
                    "Informacija", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

