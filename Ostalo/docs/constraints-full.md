# COMPLETE RELATIONAL MODEL CONSTRAINTS — AUTHORITATIVE

These constraints are transcribed from the user's supplied tables. They are
part of the implementation specification and must not be omitted.

All entity identifiers use `Long` in Java / `BIGINT` in SQL. `rb`,
`brojOsoba`, and `mesecnaKvota` are ordinary quantities, not identifiers,
and stay `Integer`/`INT`. Where an `id*` column is a foreign key to another
entity, the Java domain class represents it as an object reference instead
(e.g. `idMesto` on Putnik is `Putnik.mesto : Mesto` in Java) — see
docs/domain-model.md's "Java domain-object representation" section. The
relational model / SQL keep the FK columns as-is.

## 1. Agent

Relation:
Agent(idAgent, ime, prezime, email, telefon, korisnickoIme, sifra)

Simple value constraints:
- idAgent: Long, Not Null
- ime: String, Not Null
- prezime: String, Not Null
- email: String, Not Null and LIKE '%@%.%'
- telefon: String, Not Null and Unique and LIKE '+____________' (+ i 12 cifara)
- korisnickoIme: String, Not Null and Unique (identifies the agent during PrijaviAgent)
- sifra: String, Not Null and Length > 6

Structural constraints:
- INSERT / UPDATE -> Rezervacija: CASCADE
- INSERT / UPDATE -> Zaduzenje: CASCADE
- DELETE -> Rezervacija: RESTRICTED
- DELETE -> Zaduzenje: RESTRICTED

## 2. Aranzman

Relation:
Aranzman(idAranzman, naziv, opis, tipAranzmana, cenaPoOsobi)

Simple value constraints:
- idAranzman: Long, Not Null
- naziv: String, Not Null
- opis: String
- tipAranzmana: String, Not Null
- cenaPoOsobi: Double, Not Null and > 0

Structural constraints:
- INSERT / UPDATE -> StavkaRezervacije: CASCADE
- DELETE -> StavkaRezervacije: RESTRICTED

## 3. Mesto

Relation:
Mesto(idMesto, naziv, pttBroj, pozivniBroj, drzava)

Simple value constraints:
- idMesto: Long, Not Null
- naziv: String, Not Null and Unique
- pttBroj: String, Not Null and Length = 5 and Unique
- pozivniBroj: String, Not Null and Unique
- drzava: String, Not Null

Structural constraints:
- INSERT / UPDATE -> Putnik: CASCADE
- DELETE -> Putnik: RESTRICTED

## 4. Region

Relation:
Region(idRegion, naziv, oznaka, kontinent, opis)

Simple value constraints:
- idRegion: Long, Not Null
- naziv: String, Not Null
- oznaka: String, Not Null and Unique and Length <= 6
- kontinent: String, Not Null
- opis: String

Structural constraints:
- INSERT / UPDATE -> Zaduzenje: CASCADE
- DELETE -> Zaduzenje: RESTRICTED

## 5. Putnik

Relation:
Putnik(idPutnik, ime, prezime, email, telefon, jmbg, brojPasosa, idMesto,
datumRodjenja)

Simple value constraints:
- idPutnik: Long, Not Null
- ime: String, Not Null
- prezime: String, Not Null
- email: String, Not Null and LIKE '%@%'
- telefon: String, Not Null and Unique and LIKE '+____________' (+ i 12 cifara) —
  identical format/uniqueness rule as Agent.telefon
- jmbg: String, Not Null and Length = 13 and Unique (JMBG identifies a unique person)
- brojPasosa: String, Not Null
- idMesto: Long, Not Null
- datumRodjenja: Date, Not Null

Structural constraints:
- INSERT -> Mesto: RESTRICTED
- UPDATE -> Mesto: RESTRICTED
- UPDATE -> Rezervacija: CASCADE
- DELETE -> Rezervacija: RESTRICTED

## 6. Rezervacija

Relation:
Rezervacija(idRezervacija, datumKreiranja, ukupanIznos, statusPlacanja,
napomena, idAgent, idPutnik)

Simple value constraints:
- idRezervacija: Long, Not Null
- datumKreiranja: Date, Not Null
- ukupanIznos: Double, > 0
- statusPlacanja: String, Not Null; allowed examples: 'Placeno', 'Ceka'
- napomena: String
- idAgent: Long, Not Null
- idPutnik: Long, Not Null

Complex value constraint:
- ukupanIznos = SUM(StavkaRezervacije.cena)

Structural constraints:
- INSERT -> Putnik, Agent: RESTRICTED
- UPDATE -> Putnik, Agent: RESTRICTED
- UPDATE -> StavkaRezervacije: CASCADE
- DELETE -> StavkaRezervacije: RESTRICTED

## 7. StavkaRezervacije

Relation:
StavkaRezervacije(idRezervacija, rb, brojOsoba, datumPolaska,
datumDolaska, popust, cena, idAranzman)

Simple value constraints:
- idRezervacija: Long, Not Null (Java: `Rezervacija rezervacija` object reference)
- rb: Integer, Not Null (part of the composite identifying key)
- brojOsoba: Integer, Not Null and > 0
- datumPolaska: Date, Not Null
- datumDolaska: Date, Not Null
- popust: Double, 0.0 - 1.0
- cena: Double, Not Null and > 0
- idAranzman: Long, Not Null

Complex value constraints:
- datumDolaska > datumPolaska
- popust = (brojOsoba >= 3) ? 0.1 : 0
- cena = (brojOsoba * Aranzman.cenaPoOsobi) * (1 - popust)

Structural constraints:
- INSERT -> Rezervacija, Aranzman: RESTRICTED
- UPDATE -> Rezervacija, Aranzman: RESTRICTED
- DELETE: no restriction specified (/)

## 8. Zaduzenje

Relation:
Zaduzenje(idAgent, idRegion, datumOd, datumDo, mesecnaKvota, napomena)

Simple value constraints:
- idAgent: Long, Not Null
- idRegion: Long, Not Null
- datumOd: Date, Not Null
- datumDo: Date
- mesecnaKvota: Integer and > 0
- napomena: String

Complex value constraint:
- if datumDo is defined, datumDo > datumOd

Structural constraints:
- INSERT -> Agent, Region: RESTRICTED
- UPDATE -> Agent, Region: RESTRICTED
- DELETE: no restriction specified (/)

Composite identifying key: (idAgent, idRegion, datumOd). Implements
GenericEntity for consistency with the other seven relations, but no
repository/SO/Controller/GUI in the nine selected use cases exercises it.

## Implementation rule

These constraints must be respected in:
- Client-side validation where appropriate;
- Server/SO validation;
- Repository/database behavior;
- SQL schema where the rule can be represented correctly.

Do not silently omit a constraint because it is inconvenient to express in
SQL. If a rule is not appropriate as a database constraint, enforce it in
the appropriate Server/SO/domain validation layer.

Do not invent additional business rules that are not present here.

The structural INSERT/UPDATE/DELETE rules must be respected when designing
foreign keys and persistence behavior. Where the table says RESTRICTED,
do not substitute CASCADE for convenience. Where it says CASCADE, preserve
the required cascading semantics.

For the selected seminar scope, these constraints are especially relevant
to:
- SK1 Kreiraj rezervaciju
- SK2 Pretrazi rezervaciju
- SK3 Promeni rezervaciju
- SK5 Kreiraj putnika
- SK6 Pretrazi putnika
- SK7 Promeni putnika
- SK8 Obrisi putnika
- SK9 Prijavi agenta
- SK22 Ubaci region
