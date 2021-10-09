package view.administrator;

import javax.swing.*;

public class Clanovi extends JDialog {

    public Clanovi(JFrame roditelj) {
        super(roditelj);
        setTitle("Clanovi");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //setPreferredSize(new Dimension(1600,750));    zakomentarisane delove posle implementirati
        //initGUI();
        pack();
        setLocationRelativeTo(null);
        //initAkcije();
    }
}
