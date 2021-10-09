package view.clan;

import kontroler.IzdatPrimerakController;
import model.IzdatPrimerak;
import model.Knjiga;
import model.enumeracije.TipClanarine;
import model.enumeracije.VrstaOcene;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OcenjivanjeKnjigeDialog extends JDialog {

    private IzdatPrimerak izdatPrimerak;
    private IzdatPrimerakController izdatPrimerakController;

    protected JLabel lblNaslovKnjige = new JLabel("Naslov knjige:");
    protected JTextField tfNaslovKnjige = new JTextField(30);
    protected JLabel lblAutorKnjige = new JLabel("Autor:");
    protected JTextField tfAutorKnjige = new JTextField(30);
    protected JLabel lblOcena = new JLabel("Ocena:");
    protected JComboBox comboBoxVrstaOcene;
    protected JButton btnPotvrdi = new JButton("Potvrdi");
    protected JButton btnOdustani = new JButton("Odustani");

    protected JDialog roditelj;

    public OcenjivanjeKnjigeDialog(JDialog roditelj, boolean modal, IzdatPrimerakController izdatPrimerakController, IzdatPrimerak izdatPrimerak) {
        super(roditelj, modal);
        this.roditelj = roditelj;
        this.izdatPrimerak = izdatPrimerak;
        this.izdatPrimerakController = izdatPrimerakController;

        this.comboBoxVrstaOcene = new JComboBox(izdatPrimerakController.getVrsteOcena().toArray());
        setTitle("Ocenjivanje knjige");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initGUI();
        this.pack();
        this.setLocationRelativeTo(null);
        initAkcije();
    }

    private void initGUI() {
        JPanel pnl = new JPanel(new MigLayout());
        pnl.add(lblNaslovKnjige, "split 2, sg a");
        pnl.add(tfNaslovKnjige, "pushx, growx, wrap");
        pnl.add(lblAutorKnjige, "split 2, sg a");
        pnl.add(tfAutorKnjige, "pushx, growx, wrap");
        pnl.add(lblOcena, "split 2, sg a");
        pnl.add(comboBoxVrstaOcene, "pushx, growx, wrap");
        pnl.add(btnPotvrdi, "split 2, sg a");
        pnl.add(btnOdustani, "pushx, growx, wrap");

        podesiInicijalneVrednostiPolja();

        this.getContentPane().add(pnl, BorderLayout.NORTH);
    }

    private void podesiInicijalneVrednostiPolja() {
        Knjiga knjiga = izdatPrimerak.getPrimerakKnjige().getKnjiga();
        tfNaslovKnjige.setText(knjiga.getNaslov());
        String imeAutora = knjiga.getImeGlavnogAutora();
        String prezimeAutora = knjiga.getPrezimeGlavnogAutora();
        tfAutorKnjige.setText(imeAutora + " " + prezimeAutora);

        tfNaslovKnjige.setEnabled(false);
        tfAutorKnjige.setEnabled(false);

        comboBoxVrstaOcene.setSelectedItem(izdatPrimerak.getOcena());
    }

    private void initAkcije() {
        btnOdustani.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OcenjivanjeKnjigeDialog.this.dispose();
            }
        });

        btnPotvrdi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBoxVrstaOcene.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "Niste selektovali ocenu", "Greska",JOptionPane.ERROR_MESSAGE);
                } else {
                    VrstaOcene vrstaOcene = (VrstaOcene) comboBoxVrstaOcene.getSelectedItem();
                    izdatPrimerak.setOcena(vrstaOcene);
                    JOptionPane.showMessageDialog(null, "Ocena je uspesno zabelezena", "Informacija",JOptionPane.INFORMATION_MESSAGE);
                    ((IstorijaCitanjaDialog) roditelj).osveziTabelu();
                    OcenjivanjeKnjigeDialog.this.dispose();
                }
            }
        });
    }



}
