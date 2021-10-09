package view.pozajmljivac;

import izuzeci.NijeMoguceIzdatiRezervisanPrimerakJerJosNijeVracen;
import kontroler.IzdatPrimerakController;
import kontroler.RezervisanPrimerakController;
import res.ResourceLoader;
import view.modeliTabela.IzdatPrimerakModel;
import view.modeliTabela.KnjigaTableModel;
import view.modeliTabela.RezervacijeTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RezervacijeDialog extends JDialog {

    private IzdatPrimerakController izdatPrimerakController;
    private RezervisanPrimerakController rezervisanPrimerakController;

    protected JToolBar mainToolBar = new JToolBar();
    protected JTable tabelaStavke = new JTable();
    protected JTextField tfSearch = new JTextField(20);
    protected JButton btnOznaciKaoPreuzetu = new JButton();
    TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

    public RezervacijeDialog(JFrame roditelj, boolean modal, IzdatPrimerakController izdatPrimerakController,
                             RezervisanPrimerakController rezervisanPrimerakController) {
        super(roditelj, modal);

        this.rezervisanPrimerakController = rezervisanPrimerakController;
        this.izdatPrimerakController = izdatPrimerakController;
        setTitle("Rezervisane knjige");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(1100, 500));
        initGUI();
        this.pack();
        this.setLocationRelativeTo(null);
        initAkcije();
    }

    private void initGUI() {
        podesiToolbar();
        podesiTabelu();
        podesiJug();
    }

    private void podesiToolbar() {
        ImageIcon preuzetoIcon = ResourceLoader.getImageIcon("tick.png");
        ImageIcon preuzetoIconResized = new ImageIcon(
                preuzetoIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));

        btnOznaciKaoPreuzetu.setIcon(preuzetoIconResized);
        btnOznaciKaoPreuzetu.setToolTipText("Oznaci da je rezervacija preuzeta");
        mainToolBar.add(btnOznaciKaoPreuzetu);

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
        tabelaStavke.setModel(new RezervacijeTableModel(rezervisanPrimerakController.getListaAktivnihRezervacija()));
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


        btnOznaciKaoPreuzetu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaStavke.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Morate selektovati stavku iz tabele.", "Greška",
                            JOptionPane.WARNING_MESSAGE);
                }
                else {
                    int idRezervacije = Integer.parseInt(tabelaStavke.getValueAt(selectedRow, 0).toString());

                    try {
                        rezervisanPrimerakController.oznaciDaJeRezervacijaPreuzetaIizdajPrimerakClanu(idRezervacije);
                        JOptionPane.showMessageDialog(null, "Rezervacija je oznacena kao zavrsena i primerak je uspesno izdat clanu");
                        osveziTabelu();
                        RezervacijeDialog.this.dispose();
                    }
                    catch (NijeMoguceIzdatiRezervisanPrimerakJerJosNijeVracen ex) {
                        JOptionPane.showMessageDialog(null, "Ovaj primerak je idalje na citanju.", "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public void osveziTabelu() {
        RezervacijeTableModel model = new RezervacijeTableModel(rezervisanPrimerakController.getListaAktivnihRezervacija());
        tabelaStavke.setModel(model);
        tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
    }
}
