package demo;

import demo.helpers.Osoba;

import java.util.*;

import static java.util.stream.Collectors.*;

public class CollectDemo {
    public static void run(List<Osoba> osobe) {

        /* Druga bitna terminalna naredba je collect
         *
         * Za razliku od `reduce` koja je fold operacija, `collect` je aggregaciona operacija:
         * - ukratko, definisete od cega pocinjete, cesto kolekcija, definisete kako dodajete elemente u
         *   pocetni tip, i zbog paralelizma - definisete jos kako kombinujete dva pocetna tipa
         * - odavde sledi kao direktna posledica, da izlazni tip streama moze biti potpuno novi tip podatka
         * - u sustini reduce moze sve sto i collect i obratno, ali zbog nacina rada, reduce je izuzetno NEefikasan
         *   u slucaju aggregacionih operacija
         * -- vise o tome na kraju ovog odeljka
        /**/


        //Zadatak: ispisi imena svih osoba

        List<String> imena =
            osobe.stream()
                .map(Osoba::getIme)
                .collect(
                    () -> new ArrayList<>(),
                    (lista, ime) -> lista.add(ime),
                    (lista1, lista2) -> lista1.addAll(lista2)
                );
        /* Primetite da collect postoji samo u najrazvijenijoj formi, gde je potrebno definisati:
         *  1. kako se pravi akumulator
         *  2. kako se dodje u akumulator
         *  3. kako se kombinuju dva akumulatora
         * Stavka #3 je bitna samo u slucaju da se obrada vrsi u paraleli, i dok se moze postaviti na null,
         * preporuka je da nju uvek definisete.
        /**/

        // Mozemo krace pomocu pokazivaca na funkcije
        List<String> imena2 =
            osobe.stream()
                .map(Osoba::getIme)
                .collect(
                    ArrayList::new,
                    ArrayList::add,
                    ArrayList::addAll
                );

        // i sada nasa obrada deluje jako citljivo.
        // Kako je agregacija u liste tako cesta operacija, u javi su je definisano par pomocnih funkcija.
        // Pa pomocu metoda iz java.util.stream.Collectors paketa mozemo jos konciznije da se izrazimo!
        List<String> imena3 =
            osobe.stream()
                .map(Osoba::getIme)
                .collect(toList());
        // `Collectors.toList` ispod haube radi isto kao naredbe koje smo inicjalno recitije napisali

        System.out.println("Imena svih osoba: " + imena3);
        System.out.println("Sve liste imena su iste? " + (imena.equals(imena2) && imena2.equals(imena3)));
        System.out.println("--");


        // Zadatak: ispisi sva jedinstvena imena

        // Jedan od nacina da to postignemo je koristeci ispravnu strukturu podataka.
        // U nasem slucaju to je `Set` koji dok NE garantuje poredak, garantuje jedinstvenost sadrzanih elemenata.
        // Odgovarajuci `Collector` vec postoji tako da cilj postizemo jednostavnim oslanjanjem na `toSet` metodu.
        Set<String> jedinstvenaImena =
            osobe.stream()
                .map(Osoba::getIme)
                .collect(toSet());

        System.out.println("Jedinstvena imena: " + jedinstvenaImena);
        System.out.println("--");


        // Obzirom da definisemo sta je inicijalni akumulator, a onda i svaki vid rada sa njim
        // `collect` moze da vrsi i daleko kompleksnije operacije, kao npr. transformaciju iz liste u mapu podataka

        // Zadatak: polazeci od liste osoba, napravi mapu gde je:
        //  - kljuc ime osobe i godine sa ':' izmedju,
        //  - a vrednost je trenutna osoba

        Map<String, Osoba> mapa1na1a =
            osobe.stream()
                .collect(
                    HashMap::new,
                    (mapa, osoba) -> mapa.put(String.format("%s:%d", osoba.getIme(), osoba.getGodine()), osoba),
                    HashMap::putAll
                );

        // Koriscenjem `Collectors.toMap` u kome definisemo:
        // - kako zelimo da izgradimo kljuc,
        // - sta ce biti vrednost za taj kljuc
        // mozemo da se izrazimo konciznije sta zelimo, i da prepustimo jeziku da brine o stvaranju i spajanju mapa

        Map<String, Osoba> mapa1na1b =
            osobe.stream()
                .collect(toMap(
                    osoba -> String.format("%s:%d", osoba.getIme(), osoba.getGodine()),
                    osoba -> osoba
                ));

        System.out.println("Mapa svih osoba: " + mapa1na1b);
        System.out.println("Sve mape svih osoba su jednake? " + mapa1na1a.equals(mapa1na1b));
        System.out.println("--");


        // Zadatak: polazeci od liste osoba napravite mapu gde su osobe grupisane po imenu
        // U tu svrhu iskoristicemo specializaciju `Collectors.toMap` zvanu `groupingBy`
        // Kao vezbu, pokusajte da napisete potpunu collect naredbu koja resava ovaj zadatak.

        Map<String, List<Osoba>> grupisaniPoImenu =
            osobe.stream()
                .collect(groupingBy(Osoba::getIme));

        // zarad citljivosti ispisujemo elemente mape svaki u zasebnom redu
        grupisaniPoImenu.forEach((k, v) ->
            System.out.println(k + ": " + v)
        );
        System.out.println("--");


        /* Collect vs Reduce
         *
         * U cilju demonstracije da sve sto moze reduce moze i collect i obratno,
         * iskoristicemo poznate zadatke i prikazati resanje koristeci obe metode.
         *
        /**/

        //Tipican `collect` zadatak: ispisi imena svih osoba

        List<String> imenaPutemCollecta =
            osobe.stream()
                .map(Osoba::getIme)
                .collect(
                    () -> new ArrayList<>(),
                    (lista, ime) -> lista.add(ime),
                    (lista1, lista2) -> lista1.addAll(lista2)
                );

        List<String> imenaPutemReducea =
            osobe.stream()
                .map(Osoba::getIme)
                .reduce(
                    new ArrayList<String>(),
                    (lista, ime) -> {
                        lista.add(ime);
                        return lista;
                    },
                    (lista1, lista2) -> {
                        lista1.addAll(lista2);
                        return lista1;
                    }
                );

        System.out.println("Rezultati collect zadatka su isti? " + imenaPutemCollecta.equals(imenaPutemReducea));

        //Tipican `reduce` zadatak: ispisi zbir svih godina

        int zbirGodinaPutemReducea =
            osobe.stream()
                .map(Osoba::getGodine)
                .reduce(0, (preneseno, godine) -> preneseno + godine);

        int zbirGodinaPutemCollecta =
            osobe.stream()
                .map(Osoba::getGodine)
                .collect(
                    () -> {
                        int[] akumulator = new int[1];
                        akumulator[0] = 0;
                        return akumulator;
                    },
                    (akumulator, godine) -> akumulator[0] += godine,
                    (akumulator1, akumulator2) -> akumulator1[0] += akumulator2[0]
                )[0];

        System.out.println("Rezultati reduce zadatka su isti? " + (zbirGodinaPutemReducea == zbirGodinaPutemCollecta));
        System.out.println("--");

        /* Prikazana resenja namerno ne koriste `Collectors` paket kako bi se demonstriralo sto bolje sta se tacno desava.
         *
         * Kao sto vidite, dok jeste moguce koristi collect i reduce na istim mestima, u zavisnosti od problema,
         * dolazak do resenja je u najmanju ruku nategnut. Zato jako je BITNO da koristite pravi alat na pravom mestu!
        /**/


        // BONUS runda: ako radimo iskljucivo sa brojevima, na raspolaganju su nam `summaryStatistics` metoda,
        // koja objedinjuje sve statisticke podatke poput: max, min, count, sum, cak i average u slucaju realnih brojeva!
        // Primetite da je u pitanju specializacija collect metode a ne reduce.
        // Prepustam vama da dokucite zasto je bas to slucaj! :)

        //Tako da ukoliko nas samo interesuje analiza godina, mozemo jednostavno uraditi sledece:
        DoubleSummaryStatistics statistikaGodina =
            osobe.stream()
                .mapToDouble(Osoba::getGodine)
                .summaryStatistics();
        System.out.println("Sve o godinama grupe: " + statistikaGodina);
        System.out.println("--");
    }
}
