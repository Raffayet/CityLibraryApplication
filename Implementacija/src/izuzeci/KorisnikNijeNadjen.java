package izuzeci;

public class KorisnikNijeNadjen extends AplikacijaIzuzetak {

    private final String vrednost;

    public KorisnikNijeNadjen(String vrednost) {
        this.vrednost = vrednost;
    }

    public String dobavljanjeImenaVrednosti() {
        return vrednost;
    }
}
