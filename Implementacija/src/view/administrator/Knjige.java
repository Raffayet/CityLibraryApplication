package view.administrator;

import javax.swing.*;

public class Knjige extends JDialog {

    public Knjige(JFrame roditelj) {
        super(roditelj);
        setTitle("Knjige");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //setPreferredSize(new Dimension(1600,750));    zakomentarisane delove posle implementirati
        //initGUI();
        pack();
        setLocationRelativeTo(null);
        //initAkcije();
    }
}
