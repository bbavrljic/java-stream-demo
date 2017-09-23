Branko Bavrljic
bbavrljic@gmail.com
https://github.com/bbavrljic


Teme:
- Prva vidljiva promena u Javi 8: lambde
- Prava snaga u Javi 8: Stream
- Sta radi za nas?
 - odvaja sta od kako
 - prirodno funkcionalno
 - fokus na celinu umesto na delove
- Hajde da probamo
 - Primer: dohvati, velikim slovima, imena svih zena starijih od 18 godina
 - imperativan nacin
 - funkcionalan, pomocu Streamova
- Sta su zapravo?
 - apstrakcija
 - bez mutacija
 - pregled transformacije podataka
 - lanac
- Kako ih koristimo?
 - uzdignemo na visi nivo (sa konkretnog an stream)
 - jasemo stream
 - sastavljamo operacije
 - spustamo nazad na konrkretno
- Streamovi koriste sastavljanje funkcija (function composition)
- ponasanje bez stanja
- ciste funkcije
- Alatke (specializovane funkcije)
 - filter - stampaj sve muskarce
 - map - stampaj sva imena
 - reduce
  - uopsteni reduce - zbir godina svih osoba
  - specializovani
   - sum
   - max - najstariji
   - min - najmladji
   - count - maloletni
Osobine: sized, ordered, distinct, sorted
 - zavisi od izvora
 - zavisi od operacija
- uzdigni, transformisi, spusti
- pazljivo spustaj
- pogresan nacin - lista svih odraslih osoba velikim slovima
- ispravan nacin -  reduce vs collect (fold vs aggregate)
 - list
  - benefiti
  - pojednostavljanje
 - set
 - map
 - groupingBy
- sortiranje pomocu comparatora
 - na ruke
 - comparing
 - thenComparing
- Efikasnost?
- Medju operacije i terminalne operacije
- Stapanje i lenjo izvrsavanje
- Beskonacni streamovi

