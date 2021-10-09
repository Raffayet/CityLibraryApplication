package view.univerzalno;

import kontroler.KategorijaKnjigeController;
import repozitorijum.KategorijeKnjigaRepo;
import repozitorijum.KnjigeRepo;
import view.modeliTabela.KnjigaTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class PretragaKnjigaDialog extends JDialog {


    private KategorijaKnjigeController kategorijaKnjigeController;
    private KnjigeRepo knjigeRepo;

    protected JTable tabelaStavke = new JTable();
    protected JTextField tfSearch = new JTextField(20);

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected JList listGrupe;
    TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

    public PretragaKnjigaDialog(JFrame roditelj, boolean modal, KnjigeRepo knjigeRepo, KategorijeKnjigaRepo kategorijeKnjigaRepo) {
        super(roditelj, modal);
        this.kategorijaKnjigeController = new KategorijaKnjigeController(kategorijeKnjigaRepo);
        this.listGrupe = new JList(kategorijaKnjigeController.getNaziviKategorija().toArray());
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

    }
}
