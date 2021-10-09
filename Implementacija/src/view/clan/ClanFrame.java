package view.clan;

import kontroler.*;
import model.Clan;
import model.IzdatPrimerak;
import model.enumeracije.VrstaRegistracije;
import net.miginfocom.swing.MigLayout;
import repozitorijum.FabrikaRepo;
import res.ResourceLoader;
import view.univerzalno.DodavanjeIzmenaKorisnika;
import view.univerzalno.MainFrame;
import view.univerzalno.PretragaKnjigaDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClanFrame extends JFrame {

    protected Clan clan;
    private IzdataClanarinaController izdataClanarinaController;
    private IzdatPrimerakController izdatPrimerakController;
    private RezervisanPrimerakController rezervisanPrimerakController;
    private KategorijaKnjigeController kategorijaKnjigeController;

    protected JButton btnOdjava = new JButton("Odjava");
    protected JButton btnRezervacijaKnjiga = new JButton("Rezervacija knjiga");
    protected JButton btnPregledInformacijaONalogu = new JButton("Pregled informacija o nalogu");
    protected JButton btnPretragaKnjiga = new JButton("Pretraga knjiga");
    protected JButton btnMojeClanarine = new JButton("Moje clanarine");
    protected JButton btnMojaIstorijaCitanja = new JButton("Moja istorija citanja");
    protected JButton btnKnjigeKojeTrenutnoCitam = new JButton("Knjige koje trenutno citam");
    protected JButton btnKnjigeKojeSamRezevisao = new JButton("Trenutno rezervisane knjige");

    public ClanFrame(Clan clan, KorisnikController korisnikController, FabrikaRepo fabrikaRepo) throws HeadlessException {
        this.clan = clan;
        this.izdataClanarinaController = new IzdataClanarinaController(fabrikaRepo.getIzdataClanarinaRepo());
        this.rezervisanPrimerakController = new RezervisanPrimerakController(fabrikaRepo.getRezervacijaRepo(), fabrikaRepo.getIzdatPrimerakRepo());
        this.izdatPrimerakController = new IzdatPrimerakController(fabrikaRepo.getIzdatPrimerakRepo(), rezervisanPrimerakController);
        this.kategorijaKnjigeController = new KategorijaKnjigeController(fabrikaRepo.getKategorijeKnjigaRepo());

        setTitle("Clan: " + clan.getIme() + " " + clan.getPrezime());
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
        btnOdjava.setPreferredSize(d);
        btnPregledInformacijaONalogu.setPreferredSize(d);
        btnRezervacijaKnjiga.setPreferredSize(d);
        btnMojeClanarine.setPreferredSize(d);
        btnMojaIstorijaCitanja.setPreferredSize(d);
        btnKnjigeKojeTrenutnoCitam.setPreferredSize(d);
        btnKnjigeKojeSamRezevisao.setPreferredSize(d);
        btnPretragaKnjiga.setPreferredSize(d);
    }

    private void podesiCentar() {
        JPanel panelCentar = new JPanel(new MigLayout());
        panelCentar.setBackground(Color.white);

        JLabel userIconLabel = kreirajLabeluSaSlikom("profile.png", 130, 130);

        panelCentar.add(userIconLabel, "span, center, wrap");
        panelCentar.add(new JLabel(" "), "span, wrap");
        panelCentar.add(new JLabel("                 Ime:"), "split 2, sg a");
        panelCentar.add(new JLabel(this.clan.getIme()), "wrap");
        panelCentar.add(new JLabel("                 Prezime:"), "split 2, sg a");
        panelCentar.add(new JLabel(this.clan.getPrezime()), "wrap");
        panelCentar.add(new JLabel("                 Korisnicko ime:"), "split 2, sg a");
        panelCentar.add(new JLabel(this.clan.getKorisnickoIme()), "wrap");
        panelCentar.add(new JLabel("                                                                      "
                + "                                                    "), "span, wrap");

        this.getContentPane().add(panelCentar, BorderLayout.CENTER);
    }

    private void podesiLeviDeo() {
        JPanel panelZapad = new JPanel(new MigLayout());
        panelZapad.setBackground(new Color(193, 229, 255));

        JLabel pregledInfoONaloguImage = kreirajLabeluSaSlikom("editUser.png",45,45);
        JLabel rezervacijaKnjigaImage = kreirajLabeluSaSlikom("booking.png", 45, 45);
        JLabel odjavaImage = kreirajLabeluSaSlikom("logout.png", 45, 45);
        JLabel pretragaKnjigaImage = kreirajLabeluSaSlikom("book.png", 45, 45);
        JLabel mojeClanarineImage = kreirajLabeluSaSlikom("membership.png", 45, 45);
        JLabel mojaIstorijaCitanjaImage = kreirajLabeluSaSlikom("clock.png", 45, 45);
        JLabel knjigeKojeTrenutnoCitamImage = kreirajLabeluSaSlikom("reading.png", 45, 45);
        JLabel knjigeKojeSamTrenutnoRezervisaoImage = kreirajLabeluSaSlikom("reserved.png", 45, 45);

        panelZapad.add(pregledInfoONaloguImage, "split 2, sg a");
        panelZapad.add(btnPregledInformacijaONalogu, "wrap");

        panelZapad.add(rezervacijaKnjigaImage, "split 2, sg a");
        panelZapad.add(btnRezervacijaKnjiga, "wrap");

        panelZapad.add(pretragaKnjigaImage, "split 2, sg a");
        panelZapad.add(btnPretragaKnjiga, "wrap");

        panelZapad.add(mojeClanarineImage, "split 2, sg a");
        panelZapad.add(btnMojeClanarine, "wrap");

        panelZapad.add(mojaIstorijaCitanjaImage, "split 2, sg a");
        panelZapad.add(btnMojaIstorijaCitanja, "wrap");

        panelZapad.add(knjigeKojeTrenutnoCitamImage, "split 2, sg a");
        panelZapad.add(btnKnjigeKojeTrenutnoCitam, "wrap");

        panelZapad.add(knjigeKojeSamTrenutnoRezervisaoImage, "split 2, sg a");
        panelZapad.add(btnKnjigeKojeSamRezevisao, "wrap");

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

        btnOdjava.addActionListener(e -> {
            setVisible(false);
            dispose();
            MainFrame login = new MainFrame(korisnikController, fabrikaRepo);
            login.setVisible(true);
        });

        btnRezervacijaKnjiga.addActionListener(arg0 -> {
        });

        btnPregledInformacijaONalogu.addActionListener(arg0 -> {
            DodavanjeIzmenaKorisnika pion = new DodavanjeIzmenaKorisnika(ClanFrame.this, clan, clan, VrstaRegistracije.REGISTRACIJA_CLANA, korisnikController, fabrikaRepo);
            pion.setVisible(true);
        });

        btnPretragaKnjiga.addActionListener(e -> {
            PretragaKnjigaDialog pretragaKnjigaDialog = new PretragaKnjigaDialog(ClanFrame.this, true, fabrikaRepo.getKnjigeRepo(), fabrikaRepo.getKategorijeKnjigaRepo());
            pretragaKnjigaDialog.setVisible(true);
        });

        btnMojeClanarine.addActionListener(e -> {
            ClanarineZaClanaDialog clanarineZaClanaDialog  = new ClanarineZaClanaDialog(ClanFrame.this, true,
                    ClanFrame.this.clan, izdataClanarinaController);
            clanarineZaClanaDialog.setVisible(true);
        });

        btnMojaIstorijaCitanja.addActionListener(e -> {
            List<IzdatPrimerak> lista = izdatPrimerakController.getListaProcitanihKnjigaZaClana(clan.getId());
            if (lista.size() == 0) {
                JOptionPane.showMessageDialog(null, "Trenunto nemate procitanih knjiga");
            }else{
                IstorijaCitanjaDialog istorijaCitanjaDialog = new IstorijaCitanjaDialog(ClanFrame.this, true, clan, izdatPrimerakController, rezervisanPrimerakController);
                istorijaCitanjaDialog.setVisible(true);
            }
        });

        btnKnjigeKojeTrenutnoCitam.addActionListener(e -> {
            List<IzdatPrimerak> primerci = izdatPrimerakController.getIzdatiPrimerciKojeClanTrenutnoCita(clan.getId());
            if (primerci.size() == 0) {
                JOptionPane.showMessageDialog(null, "Ne postoje knjige koje trenutno citate");
            } else {
                KnjigeKojeClanTrenutnoCita knjigeKojeClanTrenutnoCita = new KnjigeKojeClanTrenutnoCita(ClanFrame.this, true, clan, izdatPrimerakController, rezervisanPrimerakController);
                knjigeKojeClanTrenutnoCita.setVisible(true);
            }
        });

        btnKnjigeKojeSamRezevisao.addActionListener(e -> {
            List<IzdatPrimerak> primerci = rezervisanPrimerakController.getIzdatiPrimerciKojeJeClanRezervisao(clan.getId());
            if (primerci.size() == 0) {
                JOptionPane.showMessageDialog(null, "Ne postoje knjige koje ste rezervisali");
            } else {
                KnjigeKojeJeClanTrenutnoRezervisao knjigeKojeJeClanTrenutnoRezervisao = new KnjigeKojeJeClanTrenutnoRezervisao(ClanFrame.this,
                        true, clan, izdatPrimerakController, rezervisanPrimerakController);
                knjigeKojeJeClanTrenutnoRezervisao.setVisible(true);
            }
        });

        btnRezervacijaKnjiga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RezervacijaKnjigaDialog rezervacijaKnjigaDialog = new RezervacijaKnjigaDialog(ClanFrame.this, true, clan, kategorijaKnjigeController, fabrikaRepo.getKnjigeRepo(), rezervisanPrimerakController);
                rezervacijaKnjigaDialog.setVisible(true);
            }
        });
    }
}
