package view.administrator;

import model.Korisnik;

import javax.swing.*;
import java.awt.*;

public class ProduzavanjeClanarineDijalog extends JDialog{

    private Korisnik korisnik;

    public ProduzavanjeClanarineDijalog(JFrame roditelj, boolean modal, Korisnik korisnik) {
        super(roditelj, modal);
        this.korisnik = korisnik;
        setTitle("Prikaz plata");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(1000,500));
        //initGUI();                            posle implementirati
        this.pack();
        this.setLocationRelativeTo(null);
        //initAkcije();
    }
}
