package demo;

import demo.helpers.Osoba;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Koncizno {

    public static void run(List<Osoba> osobe) {
        // ========
        // Zadatak:
        // Imena svih punoletnih zena, velikim slovima
        // ========


        // Imperativan nacin:


        /* Tok razmisljanja:
         * - u redu, znaci na kraju mi trebaju imena
         * -- dakle treba to sacuvati negde
         * --- radim sa listom, a imena su String
         * ---- treba da Alociram praznu listu stringova
         * - pocinjem sa obradom
         * -- radim sa listom, znaci treba mi PETLJA
         * --- ako koristim FORIN ne moram da brinem o broju elemenata
         * - trebaju mi tek neke osobe, znaci IF
         * -- uslov treba da bude tacan iskljucivo za punoletne I zenskog pola
         * --- ovde treba da sacuvam rezultat
         * ---- ali najpre da dohvatim ime, jer rezultat je tipa liste Stringova
         * ----- kad sam vec tu, treba mi i da bude velikim slovima
        /**/

        ArrayList<String> imena = new ArrayList<>();

        for (Osoba osoba : osobe) {
            if (osoba.getGodine() > 17 && osoba.getPol() == demo.helpers.Pol.ZENSKO) {

                imena.add(osoba.getIme().toUpperCase());

            }
        }

        System.out.println("Imperativan nacin: " + imena);

        /* Napomena:
         * - Primetite kako tok misli stalno skakuce izmedju zadatka i alatki kojim razmatramo da ga resimo.
         * - Primetite kako se se u rezultujucem resenju alati (for, if, rezultujuca lista imena)
         *   preplicu sa logikom zadatka na NEtrivijalan nacin.
         * - Primetite nacin na koji su kandidati za modularizaciju upleteni u kod.
         * - Primetite da logika resenja "curi" van bloka gde se reseva i zavisi od tog neceg spolja.
         * -- Logika unutar if bloka zavisi od `imena` koja su definisana ne samo van if bloka nego i van for bloka.
         *    U praksi, lako se desi da je to van i dosta dalje.
         *
         * - U svrhu vezbe, napravite pauzu par minuta. Nakon povratka ukljucite diktafon i gledajuci iskljucivo kod
         *   iznad svojim recima do detalja objasnite sta radi. Obratite posebnu paznju sta gledate, kuda vam misli
         *   lutaju dok pokusavate da sklopite razumno objasnjenje.
         *
         * - Konacno, zamislite scenario: u pauzama od mesec dana treba da uvedete sledece izmene:
         * -- 1. zameniti osobe od interesa sa punoletnih na maloletne.
         * -- 2. prikazete iskljucivo jedinstvena imena.
         * -- 3. umesto imena, u okviru rezultata vratite cele osobe.
         *
         * Slobodno izmenite kod iznad i isprobajte vasa resenja. Obratite paznju sta ste sve promenili i dok ste
         * menjali na sta ste sve morali da obratite paznju.
        /**/


        // ========
        // Da ponovimo Zadatak:
        // Imena svih punoletnih zena, velikim slovima
        // ========


        // Funkcionalan nacin:

        /* Tok razmisljanja:
         * - u redu, radim sa listom osoba
         * -- dakle mogu da apstrahujem u Stream
         * - trebaju mi samo punoletne osobe
         * -- suzicu pomocu filtera, uslov osoba.godine > 17
         * - trebaju mi samo zenske osobe
         * -- suzicu pomocu filtera, uslov osoba.pol == ZENSKO
         * - trebaju mi imena osoba
         * -- transformisacu pomocu mape, funkcija osoba -> osoba.ime
         * - treba mi da ime bude celo velikim slovima
         * -- transormisacu pomocu mape, finkcija ime -> IME
         * - potrebno mi je da agregiram rezultat
         * -- prikupicu elemente pomocu collecta i to u listu
         * --- rezultat cu sacuvati u listu stringova zarad lakse kasnije upotrebe
        /**/

        List<String> imena2 =
            osobe.stream()
                .filter(osoba -> osoba.getGodine() > 17)
                .filter(osoba -> osoba.getPol() == demo.helpers.Pol.ZENSKO)
                .map(osoba -> osoba.getIme())
                .map(ime -> ime.toUpperCase())
                .collect(toList());


        // mozemo jos malo konciznije (obratite paznju na pokazivace na funkcije u .map operacijama)

        List<String> imena3 =
            osobe.stream()
                .filter(osoba -> osoba.getGodine() > 17)
                .filter(osoba -> osoba.getPol() == demo.helpers.Pol.ZENSKO)
                .map(Osoba::getIme)
                .map(String::toUpperCase)
                .collect(toList());

        System.out.println("Funkcionalan nacin: " + imena3);

        System.out.println(
            "Sve tri liste imena su jednake (imena == imena2 == imena3)? "
                + (imena.equals(imena2) && imena2.equals(imena3))
        );
        System.out.println("--");

        /* Napomena:
         * - Primetite daleko prirodniju tranzicju od zamisli do konkretnog resenja.
         * - (1) Primetite da su sve obrade sadrzane u okviru lambdi u okviru poziva namenskih funkcija
         * -- nema "curenja" logike resenja
         * - (2) Primetite da se namenske funkcije Streama lancaju
         * - 1 i 2 imaju za posledicu stvarnu modularnost!
         * -- obrade se lako mogu izdvojiti u namenske funkcije
         * -- sam stream se moze na trivijalan nacin rasparcati
         *
         * - ponovite obe vezbe iz prethodne napomene. Preporucuje se da pogledate ostale primere pre nego sto
         *   krenete da menjate kod.
         *
        /**/



        /* Malo reci o performansama streamova:
         *
         * - Koriscenje streamova je vremenski efikasnije:
         * -- iz ugla koda:
         * --- zbog lenjog izvrsavanja (npr. da nema terminalne naredbe `collect` na linijama 102 i 113,
         *     stream se ne bi ni izvrsio!)
         * --- kolicine optimizacija koje samo postaju bolje iz verzije u verziju
         * -- iz ugla razvoja:
         * --- kod je pregledniji, samim tim laksi za odrzavanje i nastavak razvoja
         * --- brojne automatizacije koje povecavaju izrazajnost
         * --- trivijalna paralelizacija
         *
         *
         * - Streamovi su dizajnirani da budu thread-safe
         * - Streamovi koriste kompoziciju funkcija
         * -- tj. pri obradi se prolazi kroz kolekciju samo jednom
         *
         * Konacno, Streamovi nisu odgovor za sve
         * - u korist fokusa na paralelizaciju, exception-handling je malo smotan
         * - konverzija u Stream objekata koji to prirodno nisu ume biti neefikasna
         * - jezik ne stiti od mnogih zloupotreba i logickih lapsusa koje za posledicu mogu da imaju jako teske
         *   za otkriti bugove.
         *
        /**/
    }

}
