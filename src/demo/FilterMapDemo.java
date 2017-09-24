package demo;

import demo.helpers.Osoba;
import demo.helpers.Pol;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class FilterMapDemo {

    public static void run(List<Osoba> osobe) {

        // Glavna dva alata koja su nam na raspolaganju su filter i map

        // Filter:
        // =======
        // - Od n elemenata na ulazu, daje od 0 do n elemenata na izlazu u zavisnosti od prosledjenog uslova. Dakle:
        // -- menja kolicinu podataka (na manje)
        // -- NE menja tip podatka


        // Zadatak: ispisi sve muskarce
        List<Osoba> muskarci =
            osobe.stream()
                .filter(osoba -> osoba.getPol() == Pol.MUSKO)
                .collect(toList());

        System.out.println("Svih osoba ima: " + osobe.size());
        System.out.println("Dok muskaraca ima: " + muskarci.size());
        System.out.println("Samo muskarci: " + muskarci);
        System.out.println("--");
        // Primetite:
        // - broj osoba je razlicit izmedju dve liste zato sto nisu sve osobe muskarci
        // - tip se nije promenio, lista muskaraca je i dalje lista objekata tipa Osoba


        // Map:
        // ====
        // - Od n elemenata na ulazu tipa <T>, daje n elemenata na izlazu tipa <R>. Dakle:
        // -- NE menja kolicinu podataka
        // -- (opciono) menja tip podatka


        // Zadatak: ispisi sva imena velikim slovima
        List<String> imena =
            osobe.stream()
                .map(Osoba::getIme)
                .map(String::toUpperCase)
                .collect(toList());
        System.out.println("Broj imena je isti kao i broj osoba? " + (imena.size() == osobe.size()));
        System.out.println("Lista imena: " + imena);
        System.out.println("--");
        // Primetite:
        // - broj elemenata u listi je ostao isti
        // - tip elemenata u listi se promenio. Krenuli smo od liste Osoba, zavrsili smo sa listom Stringova
        // - druga map operacija samo transformise podatak, ali ne i tip (String usao String izasao)


        // Zadatak: Promeni imena osoba da budu velikim slovom
        List<Osoba> osobeVelikimSlovima =
            osobe.stream()
                .map(osoba -> new Osoba(osoba.getIme().toUpperCase(), osoba.getPol(), osoba.getGodine()))
                .collect(toList());
        System.out.println("Orginalna lista osoba:\n" + osobe);
        System.out.println("Osobe sa imenima velikim slovima:\n" +osobeVelikimSlovima);
        System.out.println("--");

        // Primetite:
        // - iako smo izvrsili transformaciju, tip podataka je ostao isti. I to je isto u redu :)

        // Napomene:
        // Neki od vas se pitaju, zar nismo mogli da promenimo ime orginala pomocu setter metode za ime?
        // U kodu `.map(osoba -> osoba.setIme(osoba.getIme().toUpperCase()))` ?
        //
        // Ukratko - NE. Ako vas neko ubedjuje u suprotno, bezite.
        //
        // Opsirnije:
        // Da klasa Osobe implementira setter za ime, da, bilo bi moguce i Java se nece buniti.
        // ALI primetite da time menjate izvornu kolekciju i direktno RUSITE osnovnu ideju da Stream nije mutabilan!
        // Dakle, ako vam je cilj izmena osnovne liste, nemojte koristiti Stream.
        //
        // Ako mutacija nije cilj, postavlja se pitanje efikasnosti: Zar nije skupo praviti iznova svaki element liste?
        // Moze biti, i ako je to slucaj, potrebno je razmotriti osnovni tip podatka i po potrebi koristiti strukturu
        // koja efikasnije pravi kopiju.

    }

}
