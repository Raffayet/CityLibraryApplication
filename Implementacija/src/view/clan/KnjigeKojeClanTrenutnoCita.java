package view.clan;

import izuzeci.DuploProduzavanjeCitanjaZaPrimerak;
import kontroler.IzdatPrimerakController;
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

public class KnjigeKojeClanTrenutnoCita extends JDialog {

    private Clan clan;
    protected JTable tabelaStavke = new JTable();
    private IzdatPrimerakController izdatPrimerakController;
    private RezervisanPrimerakController rezervisanPrimerakController;
    protected JToolBar mainToolBar = new JToolBar();
    protected JButton btnProduziCitanje = new JButton("Produzi citanje");
    TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

    public KnjigeKojeClanTrenutnoCita(JFrame roditelj, boolean modal, Clan clan, IzdatPrimerakController izdatPrimerakController,
                                      RezervisanPrimerakController rezervisanPrimerakController) {
        super(roditelj, modal);

        this.clan = clan;
        this.izdatPrimerakController = izdatPrimerakController;
        this.rezervisanPrimerakController = rezervisanPrimerakController;
        setTitle("Knjige koje trenutno citam");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(900, 300));
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
        ImageIcon produziCitanjeIcon = ResourceLoader.getImageIcon("produzenjeClanarine.png");
        ImageIcon produziCitanjeIconResized = new ImageIcon(
                produziCitanjeIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));

        btnProduziCitanje.setIcon(produziCitanjeIconResized);
        btnProduziCitanje.setToolTipText("Produzi citanje");
        mainToolBar.add(btnProduziCitanje);

        mainToolBar.setFloatable(false);
        add(mainToolBar, BorderLayout.NORTH);
    }

    private void podesiTabelu() {
        IzdatPrimerakModel izdatPrimerakModel = new IzdatPrimerakModel(izdatPrimerakController.getIzdatiPrimerciKojeClanTrenutnoCita(clan.getId()), rezervisanPrimerakController);
        tabelaStavke.setModel(izdatPrimerakModel);
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

    private void osveziTabelu() {
        IzdatPrimerakModel izdatPrimerakModel = new IzdatPrimerakModel(izdatPrimerakController.getIzdatiPrimerciKojeClanTrenutnoCita(clan.getId()), rezervisanPrimerakController);
        tabelaStavke.setModel(izdatPrimerakModel);
        tableSorter.setModel((AbstractTableModel) tabelaStavke.getModel());
    }

    private void initAkcije() {
        btnProduziCitanje.addActionListener(e -> {
            int selectedRow = tabelaStavke.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Morate selektovati stavku iz tabele.", "Greška",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                int answer = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da produzite citanje knjige", "Pitanje",
                        JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(tabelaStavke.getValueAt(selectedRow, 0).toString());

                    try{
                        izdatPrimerakController.produziCitanjeZaIzdatPrimerak(id);
                        osveziTabelu();
                        JOptionPane.showMessageDialog(null, "Uspesno je produzeno citanje knjige");
                    }
                    catch (DuploProduzavanjeCitanjaZaPrimerak ex) {
                        JOptionPane.showMessageDialog(null, "Vec je produzeno citanja za ovaj primerak", "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
