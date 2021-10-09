package view.clan;

import kontroler.IzdataClanarinaController;
import model.Clan;
import view.modeliTabela.IzdateClanarineModel;
import view.modeliTabela.KnjigaTableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ClanarineZaClanaDialog extends JDialog {

    private Clan clan;
    private IzdataClanarinaController izdataClanarinaController;

    protected JTable tabelaStavke = new JTable();

    public ClanarineZaClanaDialog(JFrame roditelj, boolean modal, Clan clan, IzdataClanarinaController izdataClanarinaController) {
        super(roditelj, modal);
        this.clan = clan;
        this.izdataClanarinaController = izdataClanarinaController;
        setTitle("Moje clanarine");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(600, 300));
        initGUI();
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void initGUI() {
        IzdateClanarineModel izdateClanarineModel = new IzdateClanarineModel(this.izdataClanarinaController.getIzdateClanarineZaClana(this.clan.getId()));
        tabelaStavke.setModel(izdateClanarineModel);
        TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
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
}
