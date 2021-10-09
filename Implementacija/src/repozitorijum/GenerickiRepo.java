package repozitorijum;

import model.Entitet;
import izuzeci.EntitetNePostoji;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class GenerickiRepo<T extends Entitet> {
    protected String putanjaDoFajla;
    protected List<T> listaEntiteta;

    public GenerickiRepo(String putanjaDoFajla) {
        this.putanjaDoFajla = putanjaDoFajla;
        this.listaEntiteta = new ArrayList<>();
    }


    public void ucitajPodatke() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(putanjaDoFajla), StandardCharsets.UTF_8)))
        {
            String line = null;
            while((line = in.readLine())!= null) {
                String[] tokens = line.split("\\|");
                for(int i = 0; i < tokens.length; i++) {
                    tokens[i] = tokens[i].trim();
                }
                kreirajEntitetIDodajUListu(tokens);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract Entitet kreirajEntitetIDodajUListu(String[] tokeni);

    public void sacuvajPodatke() {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(putanjaDoFajla), StandardCharsets.UTF_8)))
        {
            for(T entitet: this.listaEntiteta)
                out.println(entitet.formatiranZapisZaFajl());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void dodajEntitet(T entitet) {
        this.listaEntiteta.add(entitet);
    }

    public void obrisiTrajnoEntitet(T entitet){
        this.listaEntiteta.remove(entitet);
    }

    public List<T> getListaEntiteta() {
        return listaEntiteta;
    }

    public T getById(int id) {
        for (T e : getListaEntiteta())
            if (e.getId() == id)
                return e;
        throw new EntitetNePostoji("Nije moguce pronaci entitet sa trazenim id brojem");
    }

    public int generisanId() {
        int retVal = 0;
        for(T entitet: this.listaEntiteta) {
            if (entitet.getId() > retVal) {
                retVal = entitet.getId();
            }
        }
        return retVal + 1;
    }

    public void ispisiPodatke() {
        for (Entitet e : listaEntiteta) {
            System.out.println(e);
        }
    }

}
