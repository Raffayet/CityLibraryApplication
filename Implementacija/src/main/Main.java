package main;

import kontroler.KorisnikController;
import kontroler.RezervisanPrimerakController;
import repozitorijum.FabrikaRepo;
import view.univerzalno.MainFrame;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("-------------Glavna aplikacija-------------");
        System.out.println("Dobrodosli...");

        setUIFont (new javax.swing.plaf.FontUIResource(new Font(Font.SANS_SERIF, Font.PLAIN, 15)));

        FabrikaRepo fabrikaRepo = new FabrikaRepo();
        fabrikaRepo.ucitajPodatke();

        podesavanja(fabrikaRepo);

        KorisnikController korisnikController = new KorisnikController(fabrikaRepo.getKorisnikRepo());
        MainFrame mainFrame = new MainFrame(korisnikController, fabrikaRepo);
        mainFrame.setVisible(true);
    }

    private static void setUIFont (javax.swing.plaf.FontUIResource f){
        @SuppressWarnings("rawtypes")
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }

    private static void podesavanja(FabrikaRepo fabrikaRepo) {
        RezervisanPrimerakController rc = new RezervisanPrimerakController(fabrikaRepo.getRezervacijaRepo(), fabrikaRepo.getIzdatPrimerakRepo());
        rc.ponistiSveIstekleRezervacije();
    }

}
