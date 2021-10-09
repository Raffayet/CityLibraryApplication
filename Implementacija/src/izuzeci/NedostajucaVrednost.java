package izuzeci;

public class NedostajucaVrednost extends AplikacijaIzuzetak {

    private final String vrednost;

    public NedostajucaVrednost(String vrednost) {
        this.vrednost = vrednost;
    }

    public String dobavljanjeImenaVrednosti() {
        return vrednost;
    }
}