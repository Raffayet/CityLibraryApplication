package view.pozajmljivac;

import java.util.List;
import kontroler.IzdatPrimerakController;
import kontroler.RezervisanPrimerakController;
import model.IzdatPrimerak;
import res.ResourceLoader;
import view.modeliTabela.IzdatPrimerakModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UvidUIzdateKnjigeDialog extends JDialog {

    private IzdatPrimerakController izdatPrimerakController;
    private RezervisanPrimerakController rezervisanPrimerakController;

    protected JTable tabelaStavke = new JTable();
    protected JToolBar mainToolBar = new JToolBar();
    protected JTextField tfSearch = new JTextField(20);
    protected JButton btnOznaciKaoProcitanu = new JButton();
    protected JButton btnPrimerciIstekaoRok = new JButton();
    protected JButton btnSviPrimerci = new JButton();
    protected JButton btnRezervisaniPrimerci = new JButton();

    TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

    public UvidUIzdateKnjigeDialog(JFrame roditelj, boolean modal, IzdatPrimerakController izdatPrimerakController,
                                   RezervisanPrimerakController rezervisanPrimerakController) {
        super(roditelj, modal);
        this.izdatPrimerakController = izdatPrimerakController;
        this.rezervisanPrimerakController = rezervisanPrimerakController;
        setTitle("Uvid u izdate knjige");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(1200, 500));
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

    private void podesiToolBar() {
        ImageIcon dodajStavkuIcon = ResourceLoader.getImageIcon("tick.png");
        ImageIcon dodajStavkuIconResized = new ImageIcon(
                dodajStavkuIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));

        ImageIcon istekaoRokIcon = ResourceLoader.getImageIcon("istekaoRok.png");
        ImageIcon istekaoRokIconResized = new ImageIcon(
                istekaoRokIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));

        ImageIcon sviPrimerciIcon = ResourceLoader.getImageIcon("information.png");
        ImageIcon sviPrimerciIconResized = new ImageIcon(
                sviPrimerciIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));

        ImageIcon rezervisaniPrimerciIcon = ResourceLoader.getImageIcon("reserved.png");
        ImageIcon rezervisaniPrimerciIconResized = new ImageIcon(
                rezervisaniPrimerciIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));

        btnOznaciKaoProcitanu.setIcon(dodajStavkuIconResized);
        btnOznaciKaoProcitanu.setToolTipText("Oznaci primerak kao vracen");
        mainToolBar.add(btnOznaciKaoProcitanu);

        btnSviPrimerci.setIcon(sviPrimerciIconResized);
        btnSviPrimerci.setToolTipText("Svi izdati primerci na citanju");
        mainToolBar.add(btnSviPrimerci);

        btnPrimerciIstekaoRok.setIcon(istekaoRokIconResized);
        btnPrimerciIstekaoRok.setToolTipText("Primerci kojima je istekao rok vracanja");
        mainToolBar.add(btnPrimerciIstekaoRok);

        btnRezervisaniPrimerci.setIcon(rezervisaniPrimerciIconResized);
        btnRezervisaniPrimerci.setToolTipText("Pregled rezervisanih primeraka");
        mainToolBar.add(btnRezervisaniPrimerci);

        mainToolBar.setFloatable(false);
        add(mainToolBar, BorderLayout.NORTH);
    }


    private void podesiJug() {
        JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelJug.add(new JLabel("Pretraga: "));
        panelJug.add(tfSearch);

        this.getContentPane().add(panelJug, BorderLayout.SOUTH);
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
        tabelaStavke.setModel(new IzdatPrimerakModel(izdatPrimerakController.getListaIzdatihPrimerakaNaCitanju(), rezervisanPrimerakController));
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

        btnOznaciKaoProcitanu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaStavke.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Morate selektovati stavku iz tabele.", "Greška",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da oznacite primerak kao vracen", "Pitanje",
                            JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        int id = Integer.parseInt(tabelaStavke.getValueAt(selectedRow, 0).toString());
                        izdatPrimerakController.oznaciPrimerakKaoProcitan(id);
                        JOptionPane.showMessageDialog(null, "Primerak je uspesno oznacen kao vracen");
                        osveziTabelu();
                    }
                }
            }
        });

        btnPrimerciIstekaoRok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<IzdatPrimerak> primerci = izdatPrimerakController.getListaIzdatihPrimerakaKojimaJeIstekaoRokVracanja();
                if (primerci.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Trenutno ne postoje rezervisani primerci.");
                }
                else {
                    IzdatPrimerakModel model = new IzdatPrimerakModel(primerci, rezervisanPrimerakController);
                    tabelaStavke.setModel(model);
                    tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
                }
            }
        });

        btnSviPrimerci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                osveziTabelu();
            }
        });

        btnRezervisaniPrimerci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<IzdatPrimerak> primerci = rezervisanPrimerakController.getListaIzdatihPrimerakaKojiSuTrenutnoRezervisani();
                if (primerci.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Trenutno ne postoje izdate knjige koje su trebale biti vracene.");
                }
                else {
                    IzdatPrimerakModel model = new IzdatPrimerakModel(primerci, rezervisanPrimerakController);
                    tabelaStavke.setModel(model);
                    tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
                }
            }
        });


    }

    public void osveziTabelu() {
        IzdatPrimerakModel model = new IzdatPrimerakModel(izdatPrimerakController.getListaIzdatihPrimerakaNaCitanju(), rezervisanPrimerakController);
        tabelaStavke.setModel(model);
        tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
    }
}
