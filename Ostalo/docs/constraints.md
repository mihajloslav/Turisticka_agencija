# Structural and Value Constraints

These constraints are mandatory.

All entity identifiers (`idAgent`, `idAranzman`, `idMesto`, `idRegion`,
`idPutnik`, `idRezervacija`) use `Long` in Java / `BIGINT` in SQL. `rb` is
part of StavkaRezervacije's composite identifying key, but per the
conceptual model `rb : Integer`, so it stays `Integer`/`INT` like
`brojOsoba` and `mesecnaKvota`, which are ordinary quantities, not
identifiers.

## Agent

- `idAgent`: Long, NOT NULL
- `ime`: String, NOT NULL
- `prezime`: String, NOT NULL
- `email`: String, NOT NULL and LIKE `%@%.%`
- `telefon`: String, NOT NULL and Unique and LIKE `+____________` (+ i 12 cifara)
- `korisnickoIme`: String, NOT NULL, UNIQUE (identifies the agent during PrijaviAgent)
- `sifra`: String, NOT NULL, Length > 6

Structural rules from the specification:
- INSERT/UPDATE restrictions are defined by the related Rezervacija and
  Zaduzenje foreign keys.
- DELETE is RESTRICTED when referenced by Rezervacija/Zaduzenje.

## Aranzman

- `idAranzman`: Long, NOT NULL
- `naziv`: String, NOT NULL
- `opis`: String, optional
- `tipAranzmana`: String, NOT NULL
- `cenaPoOsobi`: Double, NOT NULL, > 0

Structural:
- DELETE is RESTRICTED when referenced by StavkaRezervacije.
- INSERT/UPDATE behavior follows the supplied relation table.

## Mesto

- `idMesto`: Long, NOT NULL
- `naziv`: String, NOT NULL, UNIQUE
- `pttBroj`: String, NOT NULL, Length = 5, UNIQUE
- `pozivniBroj`: String, NOT NULL, UNIQUE
- `drzava`: String, NOT NULL

Structural:
- DELETE is RESTRICTED when referenced by Putnik.

## Region

- `idRegion`: Long, NOT NULL
- `naziv`: String, NOT NULL
- `oznaka`: String, NOT NULL, UNIQUE, Length <= 6
- `kontinent`: String, NOT NULL
- `opis`: String, optional

Structural:
- DELETE is RESTRICTED when referenced by Zaduzenje.

## Putnik

- `idPutnik`: Long, NOT NULL
- `ime`: String, NOT NULL
- `prezime`: String, NOT NULL
- `email`: String, NOT NULL, LIKE `%@%`
- `telefon`: String, NOT NULL, UNIQUE, and LIKE `+____________` (+ i 12 cifara) —
  same format as `Agent.telefon`
- `jmbg`: String, NOT NULL, Length = 13, UNIQUE (JMBG identifies a unique person)
- `brojPasosa`: String, NOT NULL
- `idMesto`: Long, NOT NULL (Java: `Mesto mesto` object reference)
- `datumRodjenja`: Date, NOT NULL

Structural:
- INSERT is RESTRICTED with respect to Mesto in the supplied table.
- UPDATE of Mesto relationship is RESTRICTED.
- UPDATE CASCADES to Rezervacija where specified.
- DELETE is RESTRICTED when referenced by Rezervacija.

## Rezervacija

- `idRezervacija`: Long, NOT NULL
- `datumKreiranja`: Date, NOT NULL
- `ukupanIznos`: Double, > 0
- `statusPlacanja`: String, NOT NULL
- allowed examples include `Placeno` and `Ceka`
- `napomena`: optional
- `idAgent`: Long, NOT NULL (Java: `Agent agent` object reference)
- `idPutnik`: Long, NOT NULL (Java: `Putnik putnik` object reference)

Composite rule:

`ukupanIznos = SUM(StavkaRezervacije.cena)`

Structural:
- references Agent and Putnik
- deletion is restricted when StavkaRezervacije exists
- update behavior follows the supplied table

## StavkaRezervacije

- `idRezervacija`: Long, NOT NULL (Java: `Rezervacija rezervacija` object reference)
- `rb`: Integer, NOT NULL (part of the composite identifying key)
- `brojOsoba`: Integer, NOT NULL, > 0
- `datumPolaska`: Date, NOT NULL
- `datumDolaska`: Date, NOT NULL
- `datumDolaska > datumPolaska`
- `popust`: Double, 0.0–1.0
- if `brojOsoba >= 3`, `popust = 0.1`
- otherwise `popust = 0`
- `cena`: Double, NOT NULL, > 0
- `cena = (brojOsoba * Aranzman.cenaPoOsobi) * (1-popust)`
- `idAranzman`: Long, NOT NULL (Java: `Aranzman aranzman` object reference)

Structural:
- references Rezervacija and Aranzman
- INSERT/UPDATE are restricted with respect to those references as specified
- DELETE behavior follows the supplied table

## Zaduzenje

- `idAgent`: Long, NOT NULL (Java: `Agent agent` object reference)
- `idRegion`: Long, NOT NULL (Java: `Region region` object reference)
- `datumOd`: Date, NOT NULL
- `datumDo`: optional; if present `datumDo > datumOd`
- `mesecnaKvota`: Integer, > 0
- `napomena`: optional

Structural:
- references Agent and Region
- INSERT/UPDATE are restricted with respect to Agent/Region as specified
- DELETE behavior follows the supplied table

Composite identifying key: `(idAgent, idRegion, datumOd)`. Implements
`GenericEntity` for consistency with the other seven relations, but no
repository/SO/Controller/GUI in the nine selected use cases exercises it.

## Rule

Do not weaken, remove, or silently reinterpret a constraint.
If a new implementation detail is not defined by the source documentation,
ask the user rather than inventing a rule.
