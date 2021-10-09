package view.univerzalno;

import kontroler.ClanController;
import kontroler.ClanarinaController;
import kontroler.IzdataClanarinaController;
import model.Clan;
import model.Clanarina;
import model.enumeracije.TipClanarine;
import net.miginfocom.swing.MigLayout;
import view.modeliTabela.ClanoviSaClanarinamaTableModel;
import view.modeliTabela.IzdatPrimerakModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProduzenjeClanarineDialog extends JDialog {

    private ClanarinaController clanarinaController;
    private ClanController clanController;
    private IzdataClanarinaController izdataClanarinaController;

    protected JTable tabelaStavke = new JTable();
    protected JTextField tfSearch = new JTextField(20);
    protected JLabel lblSelektujteStavkuIzTbl = new JLabel("SELEKTUJTE CLANA IZ TABELE KOJEM PRODUZUJETE CLANARINU");
    protected JLabel lblCena = new JLabel("Trenutna cena za ovaj tip clanarine:");
    protected JTextField tfCena = new JTextField(20);
    protected JButton btnProduziClanarinu = new JButton("Produzi clanarinu");
    protected JComboBox comboBoxTipClanarine;

    TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();



    public ProduzenjeClanarineDialog(JFrame roditelj, boolean modal, ClanarinaController clanarinaController,
                                     ClanController clanController, IzdataClanarinaController izdataClanarinaController) {
        super(roditelj, modal);

        this.clanController = clanController;
        this.clanarinaController = clanarinaController;
        this.izdataClanarinaController = izdataClanarinaController;
        this.comboBoxTipClanarine = new JComboBox(clanarinaController.getTipoviClanarina().toArray());
        setTitle("Produzenje clanarine");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(900, 500));
        initGUI();
        this.pack();
        this.setLocationRelativeTo(null);
        initAkcije();
    }

    private void initGUI() {
        podesiSever();
        podesiCentar();
        podesiJug();
    }

    private void podesiSever() {
        JPanel pnl = new JPanel(new MigLayout());
        pnl.add(new JLabel("Izaberite tip clanarine:"), "split 2, sg a");
        pnl.add(comboBoxTipClanarine, "pushx, growx, wrap");
        pnl.add(lblCena, "split 2, sg a");
        pnl.add(tfCena, "wrap");
        tfCena.setEnabled(false);
        lblSelektujteStavkuIzTbl.setForeground(Color.BLUE);
        pnl.add(lblSelektujteStavkuIzTbl, "wrap");
        pnl.add(btnProduziClanarinu);

        comboBoxTipClanarine.setSelectedItem(TipClanarine.GODISNJA);
        int trenutnaCena = clanarinaController.getTrenutnaClanarinaNaOsnovuTipa(TipClanarine.GODISNJA).getCena();
        tfCena.setText(String.valueOf(trenutnaCena));

        this.getContentPane().add(pnl, BorderLayout.NORTH);
    }

    private void podesiCentar() {
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
        tabelaStavke.setModel(new ClanoviSaClanarinamaTableModel(clanController.getListaAktivnihClanova()));
    }


    private void podesiJug() {
        JPanel panelJug = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelJug.add(new JLabel("Pretraga:"));
        panelJug.add(tfSearch);

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

        btnProduziClanarinu.addActionListener(e -> {
            int selectedRow = tabelaStavke.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Morate selektovati stavku iz tabele.", "Greška",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if(comboBoxTipClanarine.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Niste selektovali tipClanarine", "Greska",JOptionPane.ERROR_MESSAGE);
            }
            else {
                int id = Integer.parseInt(tabelaStavke.getValueAt(selectedRow, 0).toString());
                Clan clan = clanController.getClanById(id);

                int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da produzite clanarinu za" +
                                "clana: " + clan.getIme() + " " + clan.getPrezime(), "Pitanje", JOptionPane.YES_NO_OPTION);

                if (answer == JOptionPane.YES_OPTION) {
                    Clanarina clanarina = clanarinaController.getTrenutnaClanarinaNaOsnovuTipa((TipClanarine) comboBoxTipClanarine.getSelectedItem());
                    izdataClanarinaController.izdavanjeClanarine(clanarina, clan);
                    JOptionPane.showMessageDialog(null, "Clanarina je uspesno produzena");
                    osveziTabelu();
                }
            }
        });

        comboBoxTipClanarine.addActionListener(e -> {
            Clanarina clanarina = clanarinaController.getTrenutnaClanarinaNaOsnovuTipa((TipClanarine) comboBoxTipClanarine.getSelectedItem());
            tfCena.setText(String.valueOf(clanarina.getCena()));
        });

    }

    private void osveziTabelu() {
        ClanoviSaClanarinamaTableModel model = new ClanoviSaClanarinamaTableModel(clanController.getListaAktivnihClanova());
        tabelaStavke.setModel(model);
        tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
    }

}
