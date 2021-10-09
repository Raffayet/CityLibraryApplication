package view.clan;

import kontroler.IzdatPrimerakController;
import kontroler.RezervisanPrimerakController;
import model.Clan;
import view.modeliTabela.IzdatPrimerakModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class KnjigeKojeJeClanTrenutnoRezervisao extends JDialog {
    private Clan clan;
    protected JTable tabelaStavke = new JTable();
    private RezervisanPrimerakController rezervisanPrimerakController;

    public KnjigeKojeJeClanTrenutnoRezervisao(JFrame roditelj, boolean modal, Clan clan, IzdatPrimerakController izdatPrimerakController,
                                      RezervisanPrimerakController rezervisanPrimerakController) {
        super(roditelj, modal);
        this.clan = clan;
        this.rezervisanPrimerakController = rezervisanPrimerakController;
        setTitle("Knjige koje sam rezervisao");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(900, 300));
        initGUI();
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void initGUI() {
        JLabel natpis = new JLabel("<html>Ukoliko je status knjige NA_CEKANJU_CLANA to znaci da je knjiga vracena i da mozete da odete po nju.<br>" +
                "Rok je 3 dana, inace se rezervacija ponistava");
        natpis.setForeground(Color.BLUE);
        this.getContentPane().add(natpis, BorderLayout.NORTH);
        IzdatPrimerakModel izdatPrimerakModel = new IzdatPrimerakModel(rezervisanPrimerakController.getIzdatiPrimerciKojeJeClanRezervisao(clan.getId()), rezervisanPrimerakController);
        tabelaStavke.setModel(izdatPrimerakModel);
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
