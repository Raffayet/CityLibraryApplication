package view.administrator;

import kontroler.ClanarinaController;
import kontroler.IzdataClanarinaController;
import model.enumeracije.TipClanarine;
import model.enumeracije.VrstaClana;
import net.miginfocom.swing.MigLayout;
import repozitorijum.FabrikaRepo;
import view.univerzalno.DodavanjeIzmenaKorisnika;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

public class Cenovnik extends JDialog {

    private JTextField tfCenaClanarine = new JTextField(35);
    private JComboBox<TipClanarine> cbTipClanarine = new JComboBox<TipClanarine>();
    private JButton btnSacuvaj = new JButton("Sačuvaj");
    private JButton btnOdustani = new JButton("Odustani");

    private JFrame roditelj;
    private ClanarinaController clanarinaController;

    public Cenovnik(JFrame roditelj, FabrikaRepo fabrikaRepo) {
        super(roditelj);
        this.roditelj = roditelj;
        this.clanarinaController = new ClanarinaController(fabrikaRepo.getClanarinaRepo());
        setTitle("Cenovnik");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400,230));
        initGUI();
        pack();
        setLocationRelativeTo(null);
        initAkcije();
    }

    private void initGUI() {

        MigLayout mgLayout = new MigLayout();
        this.setLayout(mgLayout);

        add(new JLabel("Tip clanarine: "), "split, sg a");
        add(cbTipClanarine, "pushx, growx, wrap");
        add(new JLabel("Cena: "), "split 2, sg a");
        add(tfCenaClanarine, "pushx, growx, wrap");
        add(new JLabel(" "), "span, wrap");
        add(new JLabel(" "), "span, wrap");
        add(new JLabel(" "), "split 3, sg a");
        add(btnSacuvaj);
        add(btnOdustani);

        cbTipClanarine.addItem(TipClanarine.GODISNJA);
        cbTipClanarine.addItem(TipClanarine.POLUGODISNJA);
        cbTipClanarine.setSelectedItem(TipClanarine.GODISNJA);
        tfCenaClanarine.setText(String.valueOf(clanarinaController.getTrenutnaClanarinaNaOsnovuTipa((TipClanarine)cbTipClanarine.getSelectedItem()).getCena()));
    }



    private void initAkcije() {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Cenovnik.this.dispose();
                Cenovnik.this.setVisible(false);
                roditelj.setVisible(true);
                super.windowClosing(e);
            }
        });

        cbTipClanarine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tfCenaClanarine.setText(String.valueOf(clanarinaController.getTrenutnaClanarinaNaOsnovuTipa((TipClanarine)cbTipClanarine.getSelectedItem()).getCena()));
            }
        });

        btnOdustani.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cenovnik.this.dispose();
            }
        });

        btnSacuvaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cenatext =  tfCenaClanarine.getText();
                Integer cena =0;
                if (cenatext == null | cenatext.equals("")) {
                    JOptionPane.showMessageDialog(null, "Molim vas da unesite sve potrebne podatke."
                            ,"Greska", JOptionPane.WARNING_MESSAGE);
                }
                try {
                     cena = Integer.parseInt(cenatext);
                    LocalDate danas = LocalDate.now();
                    clanarinaController.kreiranjeClanarine(danas,(TipClanarine) cbTipClanarine.getSelectedItem(),cena);
                    Cenovnik.this.dispose();




                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Molim vas da cena bude broj: "
                            ,"Greska", JOptionPane.WARNING_MESSAGE);
                }

            }




        });
    }
}
