package izuzeci;

public class PogresanFormat extends AplikacijaIzuzetak {

    private String vrednost;

    public PogresanFormat(String vrednost) {
        this.vrednost = vrednost;
    }

    public String dobavljanjeImenaVrednosti() {
        return vrednost;
    }
}
