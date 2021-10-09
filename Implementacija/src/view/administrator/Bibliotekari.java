package view.administrator;

import javax.swing.*;

public class Bibliotekari extends JDialog{

    public Bibliotekari(JFrame roditelj) {
        super(roditelj);
        setTitle("Bibliotekari");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //setPreferredSize(new Dimension(1600,750));    zakomentarisane delove posle implementirati
        //initGUI();
        pack();
        setLocationRelativeTo(null);
        //initAkcije();
    }
}
