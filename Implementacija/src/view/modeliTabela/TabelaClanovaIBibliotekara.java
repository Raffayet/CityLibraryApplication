package view.modeliTabela;

import kontroler.BibliotekarController;
import kontroler.ClanController;
import kontroler.KorisnikController;
import model.Bibliotekar;
import model.Clan;
import model.Korisnik;
import model.enumeracije.VrstaRegistracije;
import repozitorijum.BibliotekarRepo;
import repozitorijum.ClanRepo;
import repozitorijum.FabrikaRepo;
import repozitorijum.KorisnikRepo;
import res.ResourceLoader;
import view.administrator.ProduzavanjeClanarineDijalog;
import view.univerzalno.DodavanjeIzmenaKorisnika;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabelaClanovaIBibliotekara extends JFrame {

    private static final long serialVersionUID = -8174551416755291667L;
    private BibliotekarController bibliotekarController;
    private ClanController clanController;
    private KorisnikController korisnikController = new KorisnikController();
    private FabrikaRepo fabrikaRepo;
    private KorisnikRepo korisnikRepo;
    private ClanRepo clanRepo;
    private BibliotekarRepo bibliotekarRepo;
    private Korisnik admin;

    protected JTable tabelaKorisnici = new JTable();
    protected KorisniciTableModel korisniciTableModel;
    protected boolean bibliotekariDijalog;

    protected JToolBar mainToolBar = new JToolBar();
    protected JButton btnRegistracijaKorisnika = new JButton("Dodavanje korisnika");
    protected JButton btnBrisanjeKorisnika = new JButton("Brisanje korisnika");
    protected JButton btnModifikacijaPodataka = new JButton("Modifikacija podataka");

    public TabelaClanovaIBibliotekara(JFrame roditelj, boolean bibliotekariDijalog, FabrikaRepo fabrikaRepo, Korisnik admin) {
        super();
        this.bibliotekariDijalog = bibliotekariDijalog;
        this.fabrikaRepo = fabrikaRepo;
        this.clanController = new ClanController(fabrikaRepo.getClanRepo(), fabrikaRepo.getKorisnikRepo());
        this.bibliotekarController = new BibliotekarController(fabrikaRepo.getBibliotekarRepo(), fabrikaRepo.getKorisnikRepo());
        this.korisnikRepo = fabrikaRepo.getKorisnikRepo();
        this.clanRepo = fabrikaRepo.getClanRepo();
        this.bibliotekarRepo = fabrikaRepo.getBibliotekarRepo();
        this.admin = admin;

        if (bibliotekariDijalog) {
            this.setTitle("Bibliotekari");
        }
        else {
            this.setTitle("Clanovi");
        }
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(1000,500));
        initGUI();
        this.pack();
        this.setLocationRelativeTo(null);
        initAkcije();
    }

    private void initGUI() {

        podesiToolBar();
        podesiTabelu();

    }

    private void podesiToolBar() {
        ImageIcon dodajZaposlenogIcon = ResourceLoader.getImageIcon("dodaj.png");
        ImageIcon dodajZaposlenogIconResized = new ImageIcon(dodajZaposlenogIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        btnRegistracijaKorisnika.setIcon(dodajZaposlenogIconResized);
        btnRegistracijaKorisnika.setToolTipText("Dodaj novog korisnika");
        mainToolBar.add(btnRegistracijaKorisnika);

        ImageIcon obrisiIcon = ResourceLoader.getImageIcon("quit.png");
        ImageIcon obrisiIconResized = new ImageIcon(obrisiIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        btnBrisanjeKorisnika.setIcon(obrisiIconResized);
        btnBrisanjeKorisnika.setToolTipText("Obriši korisnika");
        mainToolBar.add(btnBrisanjeKorisnika);

        ImageIcon editZaposleniIcon = ResourceLoader.getImageIcon("editUser.png");
        ImageIcon editZaposleniIconResized = new ImageIcon(editZaposleniIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        btnModifikacijaPodataka.setIcon(editZaposleniIconResized);
        btnModifikacijaPodataka.setToolTipText("Modifikuj podatke");
        mainToolBar.add(btnModifikacijaPodataka);

        mainToolBar.setFloatable(false);
        add(mainToolBar, BorderLayout.NORTH);

    }

    private void podesiTabelu() {
        if (bibliotekariDijalog) {
            korisniciTableModel = new KorisniciTableModel(bibliotekarController.getZaposleniBibliotekari());
        }
        else {
            korisniciTableModel = new KorisniciTableModel(clanController.getClanovi());
        }
        tabelaKorisnici.setModel(korisniciTableModel);
        JPanel panel = new JPanel(new GridLayout(1,1));
        tabelaKorisnici.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabelaKorisnici.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaKorisnici.getTableHeader().setReorderingAllowed(false);
        JScrollPane srcPan = new JScrollPane(tabelaKorisnici);
        panel.add(srcPan);
        this.getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void initAkcije() {

        btnRegistracijaKorisnika.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bibliotekariDijalog) {
                    DodavanjeIzmenaKorisnika dodavanjeIzmenaKorisnika = new DodavanjeIzmenaKorisnika(TabelaClanovaIBibliotekara.this,
                            null,admin, VrstaRegistracije.REGISTRACIJA_BIBLIOTEKARA, korisnikController, fabrikaRepo);
                    dodavanjeIzmenaKorisnika.setVisible(true);
                }else {
                    DodavanjeIzmenaKorisnika dodavanjeIzmenaKorisnika = new DodavanjeIzmenaKorisnika(TabelaClanovaIBibliotekara.this,
                            null,admin, VrstaRegistracije.REGISTRACIJA_CLANA, korisnikController, fabrikaRepo);
                    dodavanjeIzmenaKorisnika.setVisible(true);
                }

                osveziTabelu(bibliotekariDijalog, bibliotekarController, clanController);
            }
        });

        btnModifikacijaPodataka.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaKorisnici.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Morate selektovati korisnika iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    int id = Integer.parseInt(tabelaKorisnici.getValueAt(selectedRow, 0).toString());
                    if (bibliotekariDijalog) {
                        Bibliotekar b = bibliotekarRepo.getById(id);
                        DodavanjeIzmenaKorisnika dodavanjeIzmenaKorisnika = new DodavanjeIzmenaKorisnika(TabelaClanovaIBibliotekara.this,
                                b,admin, VrstaRegistracije.REGISTRACIJA_BIBLIOTEKARA, korisnikController, fabrikaRepo);
                        dodavanjeIzmenaKorisnika.setVisible(true);
                    }
                    else {
                        Clan c = clanRepo.getById(id);
                        DodavanjeIzmenaKorisnika dodavanjeIzmenaKorisnika = new DodavanjeIzmenaKorisnika(TabelaClanovaIBibliotekara.this,
                                c,admin, VrstaRegistracije.REGISTRACIJA_CLANA, korisnikController, fabrikaRepo);
                        dodavanjeIzmenaKorisnika.setVisible(true);
                    }
                }
                osveziTabelu(bibliotekariDijalog, bibliotekarController, clanController);
            }
        });

        btnBrisanjeKorisnika.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaKorisnici.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Morate selektovati korisnika iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    int answer = JOptionPane.showConfirmDialog(null, "Da li ste 100% posto sigurni da želite da obrišete ovog korisnika :(", "Pitanje",JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        int id = Integer.parseInt(tabelaKorisnici.getValueAt(selectedRow, 0).toString());
                        if (bibliotekariDijalog) {
                            bibliotekarController.brisanjeBibliotekara(id);
                            JOptionPane.showMessageDialog(null, "Bibliotekar je uspešno obrisan");
                        }else {
                            clanController.brisanjeClana(id);
                            JOptionPane.showMessageDialog(null, "Clan je uspešno obrisan");
                        }
                        osveziTabelu(bibliotekariDijalog, bibliotekarController, clanController);
                    }
                }
            }
        });

    }

    public static void osveziTabelu(boolean bibliotekariDijalog, BibliotekarController bibliotekarController, ClanController clanController) {

        JTable tabelaKorisnici = new JTable();
        KorisniciTableModel korisniciTableModel;

        if (bibliotekariDijalog) {
            korisniciTableModel = new KorisniciTableModel(bibliotekarController.getZaposleniBibliotekari());
        }
        else {
            korisniciTableModel = new KorisniciTableModel(clanController.getClanovi());
        }
        tabelaKorisnici.setModel(korisniciTableModel);
    }
}
