package view.clan;

import izuzeci.*;
import kontroler.KategorijaKnjigeController;
import kontroler.RezervisanPrimerakController;
import model.Clan;
import model.Knjiga;
import model.PrimerakKnjige;
import net.miginfocom.swing.MigLayout;
import repozitorijum.KnjigeRepo;
import res.ResourceLoader;
import view.modeliTabela.IzdatPrimerakModel;
import view.modeliTabela.KnjigaTableModel;
import view.pozajmljivac.IzdavanjaKnjigaDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RezervacijaKnjigaDialog  extends JDialog {

    private Clan clan;
    private KategorijaKnjigeController kategorijaKnjigeController;
    private KnjigeRepo knjigeRepo;
    private RezervisanPrimerakController rezervisanPrimerakController;

    protected JTable tabelaStavke = new JTable();
    protected JTextField tfSearch = new JTextField(20);
    protected JList listGrupe;
    protected JLabel lblPretraga = new JLabel("Pretraga:");
    protected JLabel lblKategorijeKnjiga = new JLabel("Kategorije knjiga:");
    protected JScrollPane src;
    protected JButton btnRezervisi = new JButton();
    protected JToolBar mainToolBar = new JToolBar();

    TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();


    public RezervacijaKnjigaDialog(JFrame roditelj, boolean modal, Clan clan, KategorijaKnjigeController kategorijaKnjigeController,
                                   KnjigeRepo knjigeRepo, RezervisanPrimerakController rezervisanPrimerakController) {
        super(roditelj, modal);

        this.clan = clan;
        this.knjigeRepo = knjigeRepo;
        this.rezervisanPrimerakController = rezervisanPrimerakController;
        this.listGrupe = new JList(kategorijaKnjigeController.getNaziviKategorija().toArray());
        this.src = new JScrollPane(listGrupe);
        setTitle("Rezervacija knjiga");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(900, 500));
        initGUI();
        this.pack();
        this.setLocationRelativeTo(null);
        initAkcije();
    }

    private void initGUI() {
        podesiToolBar();
        podesiCentar();
        podesiJug();
    }

    private void podesiToolBar() {
        ImageIcon rezervisiIcon = ResourceLoader.getImageIcon("booking.png");
        ImageIcon rezervisiIconResized = new ImageIcon(
                rezervisiIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));

        btnRezervisi.setIcon(rezervisiIconResized);
        btnRezervisi.setToolTipText("Rezervisi primerak knjige");
        mainToolBar.add(btnRezervisi);

        mainToolBar.setFloatable(false);
        add(mainToolBar, BorderLayout.NORTH);
    }

    private void podesiCentar() {
        setTableData();
        JPanel panelCentar = new JPanel(new MigLayout(" "));
        panelCentar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Ukoliko su svi primerci neke knjige slobodni, rezervacija nije moguca (Tada je potrebno da dodjete u biblioteku po primerak)", TitledBorder.CENTER,
                TitledBorder.TOP));
        tabelaStavke.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabelaStavke.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaStavke.getTableHeader().setReorderingAllowed(false);
        tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
        tabelaStavke.setRowSorter(tableSorter);
        tabelaStavke.getTableHeader().setOpaque(false);
        tabelaStavke.getTableHeader().setBackground(new Color(193, 229, 255));
        JScrollPane srcPan = new JScrollPane(tabelaStavke);
        srcPan.setPreferredSize(new Dimension(1000, 500));
        panelCentar.add(srcPan, "wrap");

        add(panelCentar, BorderLayout.CENTER);
    }

    private void setTableData() {
        tabelaStavke.setModel(new KnjigaTableModel(this.knjigeRepo.getListaEntiteta()));
    }


    private void podesiJug() {
        src.setPreferredSize(new Dimension(200, 100));
        JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelJug.add(lblPretraga);
        panelJug.add(tfSearch);
        panelJug.add(lblKategorijeKnjiga);
        panelJug.add(src);

        this.getContentPane().add(panelJug, BorderLayout.SOUTH);
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


        btnRezervisi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaStavke.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Morate selektovati stavku iz tabele.", "Greška",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da rezervisete jedan od primeraka ove knjige", "Pitanje",
                            JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        int idKnjige = Integer.parseInt(tabelaStavke.getValueAt(selectedRow, 0).toString());
                        Knjiga knjiga = knjigeRepo.getById(idKnjige);

                        try {
                            rezervisanPrimerakController.rezervisiPrimerakKnjigeZaClana(clan, knjiga);
                            JOptionPane.showMessageDialog(null, "Primerak knjige je uspesno rezervisan", "Informacija", JOptionPane.INFORMATION_MESSAGE);
                            osveziTabelu();
                        } catch (PredjenLimitZaMaksBrojTrenutnoRezervisanihPrimeraka ex) {
                            JOptionPane.showMessageDialog(null, "Predjen je limit za maksimalan broj trenutno rezervisanih knjiga za ovog clana", "Greska", JOptionPane.ERROR_MESSAGE);
                        } catch (RezervacijeNijeMoguca ex) {
                            String natpis = ex.getPoruka();
                            JOptionPane.showMessageDialog(null, natpis, "Greska", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    public void osveziTabelu() {
        KnjigaTableModel model = new KnjigaTableModel(this.knjigeRepo.getListaEntiteta());
        tabelaStavke.setModel(model);
        tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
    }
}
