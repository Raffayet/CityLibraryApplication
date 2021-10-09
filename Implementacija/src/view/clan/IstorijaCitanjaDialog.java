package view.clan;

import kontroler.IzdatPrimerakController;
import kontroler.IzdataClanarinaController;
import kontroler.RezervisanPrimerakController;
import model.Clan;
import model.IzdatPrimerak;
import res.ResourceLoader;
import view.modeliTabela.IzdatPrimerakModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IstorijaCitanjaDialog extends JDialog {

    private Clan clan;
    private RezervisanPrimerakController rezervisanPrimerakController;
    private IzdatPrimerakController izdatPrimerakController;

    protected JButton btnOceniKnjigu = new JButton();
    protected JToolBar mainToolBar = new JToolBar();
    protected JTable tabelaStavke = new JTable();

    TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();


    public IstorijaCitanjaDialog(JFrame roditelj, boolean modal, Clan clan,
                                 IzdatPrimerakController izdatPrimerakController,
                                 RezervisanPrimerakController rezervisanPrimerakController) {
        super(roditelj, modal);
        this.clan = clan;
        this.izdatPrimerakController = izdatPrimerakController;
        this.rezervisanPrimerakController = rezervisanPrimerakController;
        setTitle("Moja istorija citanja");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(900, 350));
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
        ImageIcon oceniKnjiguIcon = ResourceLoader.getImageIcon("ocena.png");
        ImageIcon oceniKnjiguIconResized = new ImageIcon(
                oceniKnjiguIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));

        btnOceniKnjigu.setIcon(oceniKnjiguIconResized);
        btnOceniKnjigu.setToolTipText("Oceni procitanu knjigu");
        mainToolBar.add(btnOceniKnjigu);

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
        tabelaStavke.setModel(new IzdatPrimerakModel(izdatPrimerakController.getListaProcitanihKnjigaZaClana(clan.getId()), rezervisanPrimerakController));
    }

    public void osveziTabelu() {
        IzdatPrimerakModel model = new IzdatPrimerakModel(izdatPrimerakController.getListaProcitanihKnjigaZaClana(clan.getId()), rezervisanPrimerakController);
        tabelaStavke.setModel(model);
        tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
    }

    private void initAkcije() {
        btnOceniKnjigu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaStavke.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Morate selektovati stavku iz tabele.", "Greška",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    int id = Integer.parseInt(tabelaStavke.getValueAt(selectedRow, 0).toString());
                    IzdatPrimerak izdatPrimerak = izdatPrimerakController.getIzdatPrimerakById(id);
                    OcenjivanjeKnjigeDialog ocenjivanjeKnjigeDialog = new OcenjivanjeKnjigeDialog(IstorijaCitanjaDialog.this, true, izdatPrimerakController, izdatPrimerak);
                    ocenjivanjeKnjigeDialog.setVisible(true);
                }
            }
        });
    }
}
