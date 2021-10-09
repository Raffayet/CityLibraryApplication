package view.clan;

import model.Clan;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class PregledInformacijaONalogu extends JDialog{

    protected Clan clan;

    public PregledInformacijaONalogu(JFrame roditelj, Clan clan) {
        super(roditelj);
        this.clan = clan;
        setTitle("Pregled informacija o nalogu");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1600,750));
        initGUI();
        pack();
        setLocationRelativeTo(null);
        //initAkcije();
    }

    private void initGUI() {
        podesiCentar();
    }

    private void podesiCentar() {
        JPanel panelCentar = new JPanel(new MigLayout());
        panelCentar.setBackground(Color.white);


        JTextField ime = new JTextField(35);
        JTextField prezime = new JTextField(35);
        JTextField korisnickoIme = new JTextField(35);
        JTextField lozinka = new JTextField(35);
        JTextField jmbg = new JTextField(35);
        ime.setText(this.clan.getIme());
        prezime.setText(this.clan.getPrezime());
        korisnickoIme.setText(this.clan.getKorisnickoIme());
        lozinka.setText(this.clan.getLozinka());
        jmbg.setText(this.clan.getJmbg());
        panelCentar.add(new JLabel(" "), "span, wrap");
        panelCentar.add(new JLabel("                 Ime:"), "split 2, sg a");
        panelCentar.add(ime);
        panelCentar.add(new JLabel("                 Prezime:"), "split 2, sg a");
        panelCentar.add(prezime);
        panelCentar.add(new JLabel("                 Korisnicko ime:"), "split 2, sg a");
        panelCentar.add(ime);
        panelCentar.add(new JLabel("                 Lozinka:"), "split 2, sg a");
        panelCentar.add(korisnickoIme);
        panelCentar.add(new JLabel("                 Vrsta Korisnika:"), "split 2, sg a");
        panelCentar.add(new JLabel("                 Status Aktivnosti:"), "split 2, sg a");
        panelCentar.add(new JLabel("                 JMBG:"), "split 2, sg a");
        panelCentar.add(lozinka);
        panelCentar.add(new JLabel("                 Datum Rodjenja:"), "split 2, sg a");
        panelCentar.add(new JLabel("                 Vrsta Clana:"), "split 2, sg a");
        panelCentar.add(new JLabel("                                                                      "
                + "                                                    "), "span, wrap");

        this.getContentPane().add(panelCentar, BorderLayout.CENTER);
    }
}
