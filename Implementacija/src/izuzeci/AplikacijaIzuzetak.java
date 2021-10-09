package izuzeci;

public class AplikacijaIzuzetak extends RuntimeException{

    private String poruka;

    public AplikacijaIzuzetak() {
        super();
    }

    public AplikacijaIzuzetak(String poruka) {

        super(poruka);
        this.poruka = poruka;
    }

    public String getPoruka() { return this.poruka; }
}
