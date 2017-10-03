package demo;

import demo.helpers.Osoba;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ReduceDemo {

    public static void run(List<Osoba> osobe) {

        /* Jedna od glavnih terminalanih operacija je reduce.
         *
         * Reduce je `fold` operacija:
         * - ukratko, krene od inicialne vrednosti, onda za nju i prvi element primeni prosledjenu operaciju,
         *   zatim na rezultat i sledeci element primeni prosledjenu operaciju i tako sve dok ima elemenata u
         *   streamu (vise informacija: https://en.wikipedia.org/wiki/Fold_%28higher-order_function%29)
         * - to za sobom povlaci par posledica:
         * -- reduce je pre svega koristan za rad sa primitivnim tipovima - npr. za statistiku
         * -- inicjalna vrednost, kao i rezultat su uvek istog tipa kao elementi ulaznog streama
        /**/


        //Zadatak: ispisi zbir godina svih osoba


        // opsti oblik:
        int zbirGodina =
            osobe.stream()
                .map(Osoba::getGodine)
                .reduce(0, (preneseno, godine) -> preneseno + godine);

        // ali mozemo krace
        int zbirGodina2 =
            osobe.stream()
                .map(Osoba::getGodine)
                .reduce(0, Integer::sum);

        // ali mozemo jos krace!
        // osnovni zbir je toliko cesta reduce operacija da ima svoju specializovanu funkciju
        int zbirGodina3 =
            osobe.stream()
                .mapToInt(Osoba::getGodine)
                .sum();
        // Primetite da smo umesto `map`, morali da koristimo `mapToInt`
        // Mozemo to posmatrati na sledeci nacin: `sum` je jako konkretna operacija i konkretno ima smisla
        // iskljucivo u kontekstu brojeva. `map` sa druge strane, vraca objekte koji dok mogu, ne moraju
        // biti brojevi. Zbog prirode Jave moramo njoj naglasiti da radimo sa brojevima da bi nam `sum`
        // operacija postala dostupna.

        System.out.println("Izracunat zbir godina: " + zbirGodina3);
        System.out.println("Svi izracunati zbirovi godina su jednaki? " +
            (zbirGodina == zbirGodina2 && zbirGodina2 == zbirGodina3));
        System.out.println("--");


        // Zanimljivost: maksimum i minimum su primer reduce operacija.
        // npr. ako zelimo da nadjemo broj godina najstarije osobe, mozemo da uradimo:

        int najviseGodina =
            osobe.stream()
                .map(Osoba::getGodine)
                .reduce(-1, (maxGodina, brojGodina) -> (maxGodina < brojGodina) ? brojGodina : maxGodina);
        System.out.println("Najstarija osoba ima: " + najviseGodina + " godine.");
        // ali primetite da nase resenje ima nedostataka, naime:
        // - sta je potrebno uraditi da bi nam bila vracena konkretna osoba?
        // - i jos, sta se desava u slucaju da je kolekcija bila prazna?


        // Zadatak: nadji najstariju osobu


        Optional<Osoba> najstarijaOsoba =
            osobe.stream()
                .reduce((osoba1, osoba2) -> osoba1.getGodine() > osoba2.getGodine() ? osoba1 : osoba2);
        System.out.println("Najstarija osoba je: " + najstarijaOsoba);
        // Primetite da ovaj put reduce nema definisanu inicjalnu vrednost.
        // Takodje primetite da ovaj put povratna vrednost reduce operacije nije konkretan tip,
        // vec `Optional` sto je nacin Jave 8 da se izbori sa podacima kojih mozda nema,
        // kao u slucaju da pocetni niz bio prazan.

        //i ovde imamo na raspolaganju specializovane operacije, tako da mozemo napisati:
        Optional<Osoba> najstarijaOsoba2 =
            osobe.stream()
                .max((osoba1, osoba2) -> osoba1.getGodine() > osoba2.getGodine() ? 1 : -1);

        // ili krace:
        Optional<Osoba> najstarijaOsoba3 =
            osobe.stream()
                .max((osoba1, osoba2) -> Integer.compare(osoba1.getGodine(), osoba2.getGodine()));

        // ili JOS krace:
        Optional<Osoba> najstarijaOsoba4 =
            osobe.stream()
                .max(Comparator.comparingInt(Osoba::getGodine));

        System.out.println("Sve nadjene osobe su iste:");
        System.out.println("1: " + najstarijaOsoba);
        System.out.println("2: " + najstarijaOsoba2);
        System.out.println("3: " + najstarijaOsoba3);
        System.out.println("4: " + najstarijaOsoba4);
        System.out.println("--");


        // Analogno, mozemo naci najmladju osobu:
        Optional<Osoba> najmladjaOsoba =
            osobe.stream()
                .min(Comparator.comparingInt(Osoba::getGodine));
        System.out.println("Najmladja osoba: " + najmladjaOsoba);
        System.out.println("--");


        // Konacno, ako nas samo zanima broj osoba, npr. koliko ima maloletnih
        // mozemo iskoristi specjalizovanu reduce operaciju `count`
        long brojMaloletnihOsoba =
            osobe.stream()
                .filter(osoba -> osoba.getGodine() < 18)
                .count();
        System.out.println("Broj maloletnih osoba: " + brojMaloletnihOsoba);
        System.out.println("--");


    }
}
