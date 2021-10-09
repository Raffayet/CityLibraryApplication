package view.univerzalno;

import kontroler.*;
import model.Knjiga;
import model.enumeracije.VrstaRegistracije;
import repozitorijum.*;
import res.ResourceLoader;
import view.modeliTabela.KnjigaTableModel;
import view.modeliTabela.KorisniciTableModel;
import view.modeliTabela.TabelaClanovaIBibliotekara;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IzmenaKnjigaDialog extends JDialog {


    private KategorijaKnjigeController kategorijaKnjigeController;
    private KnjigeRepo knjigeRepo;
    private KnjigaController knjigaController;
    private PrimerakKnjigeController primerakControllor;
    private IzdatPrimerakController izdatPrimerakController;
    private RezervisanPrimerakController rezervisanPrimerakController;
    private FabrikaRepo fabrikaRepo;


    protected JToolBar mainToolBar = new JToolBar();
    protected JButton btnIzmenaKnjiga = new JButton("Izmena");
    protected JButton btnBrisanjeKnjiga = new JButton("Brisanje");
    protected JButton btnDodavanjeKnjiga = new JButton("Dodavanje");

    protected boolean modal;

    protected JTable tabelaStavke = new JTable();
    protected JTextField tfSearch = new JTextField(20);

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected JList listGrupe;
    TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

    public IzmenaKnjigaDialog(JFrame roditelj, boolean modal, KnjigeRepo knjigeRepo, KategorijeKnjigaRepo kategorijeKnjigaRepo, PrimerakKnjigeRepo primerakKnjigeRepo, IzdatPrimerakRepo izdatPrimerakRepo, RezervacijaRepo rezervacijaRepo,FabrikaRepo fabrikaRepo) {
        super(roditelj, modal);
        this.modal = modal;
        this.kategorijaKnjigeController = new KategorijaKnjigeController(kategorijeKnjigaRepo);
        this.listGrupe = new JList(kategorijaKnjigeController.getNaziviKategorija().toArray());
        this.knjigaController = new KnjigaController(knjigeRepo);
        this.primerakControllor = new PrimerakKnjigeController(primerakKnjigeRepo);
        this.rezervisanPrimerakController = new RezervisanPrimerakController(rezervacijaRepo,izdatPrimerakRepo);
        this.izdatPrimerakController = new IzdatPrimerakController(izdatPrimerakRepo,rezervisanPrimerakController);
        this.fabrikaRepo = fabrikaRepo;


        this.knjigeRepo = knjigeRepo;
        setTitle("Pretraga knjiga");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(1100, 500));
        initGUI();
        this.pack();
        this.setLocationRelativeTo(null);
        initAkcije();
    }

    private void initGUI() {
        podesiToolBar();
        podesiTabelu();
        podesiJug();

    }

    private void podesiJug() {
        JScrollPane src = new JScrollPane(listGrupe);
        src.setPreferredSize(new Dimension(200, 100));
        JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelJug.add(new JLabel("Pretraga: "));
        panelJug.add(tfSearch);
        panelJug.add(new JLabel("Kategorije knjiga:"));
        panelJug.add(src);

        this.getContentPane().add(panelJug, BorderLayout.SOUTH);
    }

    private void podesiToolBar() {
        ImageIcon dodajZaposlenogIcon = ResourceLoader.getImageIcon("dodaj.png");
        ImageIcon dodajZaposlenogIconResized = new ImageIcon(dodajZaposlenogIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        btnDodavanjeKnjiga.setIcon(dodajZaposlenogIconResized);
        btnDodavanjeKnjiga.setToolTipText("Dodaj novog korisnika");
        mainToolBar.add(btnDodavanjeKnjiga);

        ImageIcon obrisiIcon = ResourceLoader.getImageIcon("quit.png");
        ImageIcon obrisiIconResized = new ImageIcon(obrisiIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        btnBrisanjeKnjiga.setIcon(obrisiIconResized);
        btnBrisanjeKnjiga.setToolTipText("Obriši korisnika");
        mainToolBar.add(btnBrisanjeKnjiga);

        ImageIcon editZaposleniIcon = ResourceLoader.getImageIcon("editUser.png");
        ImageIcon editZaposleniIconResized = new ImageIcon(editZaposleniIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        btnIzmenaKnjiga.setIcon(editZaposleniIconResized);
        btnIzmenaKnjiga.setToolTipText("Modifikuj podatke");
        mainToolBar.add(btnIzmenaKnjiga);

        mainToolBar.setFloatable(false);
        add(mainToolBar, BorderLayout.NORTH);

    }

    private void podesiTabelu() {
        setTableData();
        JPanel panel = new JPanel(new GridLayout(1, 1));
        tabelaStavke.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabelaStavke.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaStavke.getTableHeader().setReorderingAllowed(false);
        tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
        tabelaStavke.setRowSorter(tableSorter);
        JScrollPane srcPan = new JScrollPane(tabelaStavke);
        panel.add(srcPan);
        this.getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void setTableData() {
        tabelaStavke.setModel(new KnjigaTableModel(this.knjigeRepo.getListaEntiteta()));
    }


    private void initAkcije() {
        this.tfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (tfSearch.getText().trim().length() == 0) {
                    tableSorter.setRowFilter(null);
                } else {
                    tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + tfSearch.getText().trim()));
                }
            }
        });

        this.listGrupe.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                tfSearch.setText(listGrupe.getSelectedValue().toString());
            }
        });




        btnBrisanjeKnjiga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaStavke.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Morate selektovati knjigu iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    int answer = JOptionPane.showConfirmDialog(null, "Da li ste 100% posto sigurni da želite da obrišete ovu knjigu :(", "Pitanje",JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        String isbn = tabelaStavke.getValueAt(selectedRow, 1).toString();
                        Integer id = Integer.parseInt(tabelaStavke.getValueAt(selectedRow, 0).toString());

                        rezervisanPrimerakController.izbrisiSveREzervisanePoId(id);
                        izdatPrimerakController.obrisiSvePrimerkePoId(id);
                        primerakControllor.obrisiSvePrierkePoId(id);
                        knjigaController.obrisisTrajnoKnjigu(isbn);

                        JOptionPane.showMessageDialog(null, "Knjiga je uspešno obrisan");

                       // setTableData();
                    }

                }
            }
        });
        btnIzmenaKnjiga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaStavke.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Morate selektovati knjigu iz tabele.", "Greška", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    int id = Integer.parseInt(tabelaStavke.getValueAt(selectedRow, 0).toString());
                    Knjiga k = knjigeRepo.getById(id);
                    DodavanjeIzmenaKnjiga dodavanjeIzmenaKorisnika = new DodavanjeIzmenaKnjiga(IzmenaKnjigaDialog.this,k,knjigaController,fabrikaRepo);
                    dodavanjeIzmenaKorisnika.setVisible(true);
                }
            }
        });
        btnDodavanjeKnjiga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    DodavanjeIzmenaKnjiga dodavanjeIzmenaKorisnika = new DodavanjeIzmenaKnjiga(IzmenaKnjigaDialog.this,null,knjigaController,fabrikaRepo);
                    dodavanjeIzmenaKorisnika.setVisible(true);

            }
        });

    }








}
