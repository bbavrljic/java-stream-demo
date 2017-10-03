import demo.CollectDemo;
import demo.FilterMapDemo;
import demo.Koncizno;
import demo.ReduceDemo;
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

    /*
     * Cao, dobrodosao u Stream API prakticni tutorial. :)
     * Sve pocinje odavde, tako da obrati paznju.
     *
     * - Najpre se dohvata lista osoba koju cemo korisiti u svim buducim demonstracijama
     * - Demonstracije su podeljene po klasama, preporucuje se da drzite odkomentarisanu
     *   samo jednu od demonstracija - onu koju trenutno prelazite
     * - Demonstracije su predstavljaju zaokruzene celine iza kojih sledi rezime.
     *
     * Have fun!
     */
    public static void main(String[] args) {
        List<Osoba> osobe = stvoriOsobe();

        // Uvod - pocni odavde
        Koncizno.run(osobe);

        /* Da rezimiramo:
         * - Stream je apstrakcija
         * - Stream nije mutabilan
         * -- znaci, izvorna kolekcija se NE menja kao posledica stream operacija
         * -- a, funkcije obrade su ciste funkcije (bez sporednih efekata)
         * --- tj. nemaju stanje i menjaju iskljucivo podatke koje su im prosledjene
         * - Dakle, Stream je vise nego ista jasan pregled transformacija ulaznih podataka
         * - Radimo sa Streamom putem method-chaininga (lancanje naredbi)
         * -- dok Stream ispod haube koristi komponavanje funkcija kako bi sve obrade uradio u samo jednom prolazu
         * - Stream je lenj! Ne radi nista bez terminalne operacije.
        /**/

        /* Koristimo stream tako sto:
         * - se uzdignemo na visi nivo apstrakcije (sa konkretne kolekcije predjemo na stream)
         * - jasemo stream (i to sto duze, kompunujuci, redjajuci operacije)
         * - slecemo nazad na konkretno (tako sto pozovemu jednu od terminalnih operacija)
        /**/


        // Glavna dva alata koji su nam na raspolaganju:
        FilterMapDemo.run(osobe);

        /* Da rezimiramo
         * Glavna dva alata su filter i stream
         * - Filter opciono menja broj elemenata iskljucivo na manje a u zavisnosti od uslova,
         *   ali tip podataka ostaje isti
         * - Map transformise elemente cime opciono i njihov tip a u zavisnosti od prosledjene funkcije,
         *   ali broj elemenata ostaje isti
        /**/


        // Glavna nardeba za racunanje statistike:
        ReduceDemo.run(osobe);

        /* Da rezimiramo:
         * - reduce je fold operacija
         * -- stoga najkorisnija u radu sa primitivnim tipovima i za statistiku
         * - reduce specializacije su sum, min, max, count, average
         * -- mogu se pozvati iskljucivo nad streamovima brojeva
         * -- da bi to naznacili, koristimo `mapToInt` ili `mapToDouble` ili `mapToLong` u zavisnosti sa cime radimo
        /**/


        // Glavna naredba za agregacije, i kako se razlikuje od reduce
//        CollectDemo.run(osobe);

        // Pogresno vs ispravno
//        PogresnoIspravno.run(osobe);


        // Sortiranje
//        SortDemo.run(osobe);

        // Beskonacni streamovi
//        BeskrajnoDemo.run();


        // pogledajte ceo Stream API na:
        // https://docs.oracle.com/javase/9/docs/api/java/util/stream/Stream.html

        // pogledajte sve vrste Streamova na:
        // https://docs.oracle.com/javase/9/docs/api/java/util/stream/package-summary.html

    }
}
