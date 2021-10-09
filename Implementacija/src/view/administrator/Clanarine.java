package view.administrator;

import javax.swing.*;

public class Clanarine extends JDialog{

    public Clanarine(JFrame roditelj) {
        super(roditelj);
        setTitle("Clanarine");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //setPreferredSize(new Dimension(1600,750));    zakomentarisane delove posle implementirati
        //initGUI();
        pack();
        setLocationRelativeTo(null);
        //initAkcije();
    }
}
