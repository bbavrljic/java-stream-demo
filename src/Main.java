import demo.Koncizno;
import demo.helpers.Osoba;
import demo.helpers.Pol;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static List<Osoba> stvoriOsobe() {
        return Arrays.asList(
            new Osoba("Sara", Pol.ZENSKO, 18),
            new Osoba("Sara", Pol.ZENSKO, 22),
            new Osoba("Ana", Pol.ZENSKO, 11),
            new Osoba("Marko", Pol.MUSKO, 32),
            new Osoba("Maja", Pol.ZENSKO, 32),
            new Osoba("Pera", Pol.MUSKO, 2),
            new Osoba("Pera", Pol.MUSKO, 72),
            new Osoba("Zika", Pol.MUSKO, 22)
        );
    }

    public static void main(String[] args) {
        List<Osoba> osobe = stvoriOsobe();

        //Uvod - pocni odavde
        Koncizno.run(osobe);
    }
}
