package demo.helpers;

public class Osoba {
    private final String ime;
    private final Pol pol;
    private final int godine;

    public Osoba(String ime, Pol pol, int godine) {
        this.ime = ime;
        this.pol = pol;
        this.godine = godine;
    }

    public String getIme() { return ime; }
    public Pol getPol() { return pol; }
    public int getGodine() {
//        System.out.println("dohvatam godine za " + getIme());
        return godine; }

    @Override
    public String toString() {
        return String.format("(%s ~ %s ~ %d)", ime, pol, godine);
    }
}
