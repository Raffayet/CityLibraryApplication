package view.univerzalno;

import izuzeci.NedostajucaVrednost;
import izuzeci.OdabirDatumaIzuzetak;
import kontroler.DatumiController;
import kontroler.IzdatPrimerakController;
import net.miginfocom.swing.MigLayout;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import repozitorijum.FabrikaRepo;
import utils.DatumLabelaFormater;
import view.modeliTabela.KnjigaTableModel;
import view.modeliTabela.KnjigeCitanostModel;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;
import java.util.Map;

public class IzvestajOCitanostiDialog extends JDialog {

    private FabrikaRepo fabrikaRepo;
    private DatumiController datumiController;
    private IzdatPrimerakController izdatPrimerakController;

    protected JDatePickerImpl datePickerDatumPocetak;
    protected JDatePickerImpl datePickerDatumKraj;
    protected JButton btnPretraga = new JButton("Pretrazi");
    protected JTable tabela = new JTable();

    public IzvestajOCitanostiDialog(JFrame roditelj, boolean modal, FabrikaRepo fabrikaRepo, DatumiController datumiController,
                                    IzdatPrimerakController izdatPrimerakController) {
        super(roditelj, modal);
        this.fabrikaRepo = fabrikaRepo;
        this.datumiController = datumiController;
        this.izdatPrimerakController = izdatPrimerakController;
        setTitle("Izvestaj o citanosti");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1400,500));
        initGUI();
        pack();
        setLocationRelativeTo(null);
        initAkcije();
    }

    private void initGUI() {
        podesiSever();
        podesiTabelu();
        tabela.setVisible(false);
    }

    private void podesiSever() {
        podesiKalendar();

        JPanel pnl = new JPanel(new MigLayout());
        pnl.add(new JLabel("Izaberite pocetni datum: "), "split 2, sg a");
        pnl.add(datePickerDatumPocetak,"wrap");
        pnl.add(new JLabel("Izaberite krajnji datum: "), "split 2, sg a");
        pnl.add(datePickerDatumKraj,"wrap");

        pnl.add(new JLabel(" "), "split 2, sg a");
        pnl.add(btnPretraga);
        this.getContentPane().add(pnl, BorderLayout.NORTH);

    }

    private void podesiTabelu() {
        JPanel panel = new JPanel(new GridLayout(1,1));
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabela.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoCreateRowSorter(true);
        JTableHeader th = tabela.getTableHeader();
        th.setPreferredSize(new Dimension(50, 45));
        JScrollPane srcPan = new JScrollPane(tabela);
        panel.add(srcPan);
        this.getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void initAkcije() {
        btnPretraga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate pocetak = getSelectedDate(datePickerDatumPocetak);
                LocalDate kraj = getSelectedDate(datePickerDatumKraj);
                try {
                    datumiController.validiraj(pocetak, kraj);
                    Map<Integer, Integer> podaci = izdatPrimerakController.getKolikoPutaJeKojaKnjigaProcitanaUVremenskomIntervalu(pocetak, kraj);

                    if (podaci.size() == 0) {
                        JOptionPane.showMessageDialog(null, "Trenutno ne postoje knjige koje su procitane u zadatom periodu.");
                    }
                    else {
                        tabela.setModel(new KnjigeCitanostModel(podaci, fabrikaRepo.getKnjigeRepo()));
                        tabela.setVisible(true);
                    }
                }
                catch (NedostajucaVrednost | OdabirDatumaIzuzetak ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Greška", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void  podesiKalendar() {
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePickerDatumPocetak = new JDatePickerImpl(datePanel, new DatumLabelaFormater());

        Properties p2 = new Properties();
        p2.put("text.today", "Today");
        p2.put("text.month", "Month");
        p2.put("text.year", "Year");
        UtilDateModel model2 = new UtilDateModel();
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
        datePickerDatumKraj = new JDatePickerImpl(datePanel2, new DatumLabelaFormater());
    }


    private LocalDate getSelectedDate(JDatePickerImpl datePicker) {
        Date datum = (Date) datePicker.getModel().getValue();
        if (datum != null) {
            return datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

}
