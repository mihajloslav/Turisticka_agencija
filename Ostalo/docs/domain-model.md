# Domain Model

## Classes

All entity identifiers use `Long` in the Java domain model (CommonLib), and
`BIGINT` in the relational model / `database/baza.sql`. `rb`
(StavkaRezervacije) is part of its composite identifying key, but per the
conceptual model `rb : Integer`, so it does not follow the `Long`/`BIGINT`
identifier convention. `brojOsoba` and `mesecnaKvota` are ordinary
quantities, not identifiers, and stay `Integer`/`INT`.

Attribute lists below use the relational/conceptual attribute names (e.g.
`idMesto : Long` on Putnik). The Java domain classes represent the
aggregation/composition endpoints called out in "UML relationship
semantics" below as **object references**, not raw FK fields — see "Java
domain-object representation" at the end of this file.

### Agent

Attributes:

- `idAgent : Long`
- `ime : String`
- `prezime : String`
- `email : String`
- `telefon : String`
- `korisnickoIme : String`
- `sifra : String`

### Aranzman

Attributes:

- `idAranzman : Long`
- `naziv : String`
- `opis : String`
- `tipAranzmana : String`
- `cenaPoOsobi : Double`

### Mesto

Attributes:

- `idMesto : Long`
- `naziv : String`
- `pttBroj : String`
- `pozivniBroj : String`
- `drzava : String`

### Region

Attributes:

- `idRegion : Long`
- `naziv : String`
- `oznaka : String`
- `kontinent : String`
- `opis : String`

Resolved: the user reviewed the conceptual UML image together with this
document and confirmed the conceptual model is authoritative for Region.
`kontinent` and `opis` are part of the Region domain/relational model.

### Putnik

Attributes:

- `idPutnik : Long`
- `ime : String`
- `prezime : String`
- `email : String`
- `telefon : String`
- `jmbg : String`
- `brojPasosa : String`
- `idMesto : Long` (Java: `Mesto mesto` object reference — aggregation)
- `datumRodjenja : Date` (Java: `LocalDate`, consistent with `Rezervacija.datumKreiranja` below)

### Rezervacija

Attributes:

- `idRezervacija : Long`
- `datumKreiranja : Date`
- `ukupanIznos : Double`
- `statusPlacanja : String`
- `napomena : String`
- `idAgent : Long` (Java: `Agent agent` object reference — aggregation)
- `idPutnik : Long` (Java: `Putnik putnik` object reference — aggregation)
- Java also holds `List<StavkaRezervacije> stavke` (composition; not a
  relational column of Rezervacija — StavkaRezervacije is its own table)

### StavkaRezervacije

Attributes:

- `idRezervacija : Long` (part of the composite identifying key; Java: `Rezervacija
  rezervacija` object reference — the association is set to the same object
  present in `Rezervacija.stavke`, so the graph is bidirectional; the
  relational model / SQL still stores it as the `idRezervacija` column)
- `rb : Integer` (part of the composite identifying key, but per the
  conceptual model it is `Integer`, not `Long` like the other identifiers)
- `brojOsoba : Integer`
- `datumPolaska : Date`
- `datumDolaska : Date`
- `popust : Double`
- `cena : Double`
- `idAranzman : Long` (Java: `Aranzman aranzman` object reference — aggregation)

### Zaduzenje

Associative class between Agent and Region.

Attributes:

- `idAgent : Long` (Java: `Agent agent` object reference)
- `idRegion : Long` (Java: `Region region` object reference)
- `datumOd : Date`
- `datumDo : Date`
- `mesecnaKvota : Integer` (a monthly quota — a quantity, not an identifier)
- `napomena : String`

Composite identifying key: `(idAgent, idRegion, datumOd)`.

Implements `GenericEntity`, for consistency with the other seven relational
tables. Not part of any of the nine in-scope use cases, however — no
repository call, Service Operation, Controller, or GUI form ever exercises
it, because none of the nine selected use cases performs CRUD on Zaduzenje.
It conforms to the contract only so the domain model is uniform across all
eight relations. See docs/implementation-rules.md and CLAUDE.md scope
sections.

## UML relationship semantics

The conceptual model supplied by the user is authoritative.

- A BLACK diamond is COMPOSITION.
- A WHITE diamond is AGGREGATION.
- Preserve the cardinalities shown in the model.
- Do not reinterpret the relationships for implementation convenience.
- `Zaduzenje` is an associative class connecting `Agent` and `Region`.
- `StavkaRezervacije` is associated with `Rezervacija` according to the
  composition shown in the conceptual model.

## Important modeling rule

Do not change:
- composition -> aggregation
- aggregation -> composition
- associative class -> ordinary unrelated entity
- cardinalities
- domain object names

without explicit approval.

## Mandatory visual UML rule

The user provided the original conceptual-model image. It was reviewed
together with this file.

- BLACK DIAMOND = COMPOSITION.
- WHITE DIAMOND = AGGREGATION.
- Preserve every multiplicity/cardinality shown in the image.
- `Zaduzenje` is the associative class between `Agent` and `Region`.
- `Rezervacija`–`StavkaRezervacije` is a composition.
- Do not invent or alter relationship types or multiplicities.

## Java domain-object representation

The relational model and `database/baza.sql` represent every association
and aggregation as a foreign-key column (`idAgent`, `idPutnik`, `idMesto`,
`idAranzman`, `idRegion`), per the relational model's own source-of-truth
rule. The **Java domain classes** in CommonLib instead represent each
aggregation/association endpoint from the conceptual model as an **object
reference**, so the Java model reflects the conceptual associations rather
than merely duplicating relational FK ids:

- `Rezervacija.agent : Agent`, `Rezervacija.putnik : Putnik` (replacing
  `idAgent`/`idPutnik` fields)
- `Rezervacija.stavke : List<StavkaRezervacije>` (composition)
- `Putnik.mesto : Mesto` (replacing `idMesto`)
- `StavkaRezervacije.rezervacija : Rezervacija` (replacing `idRezervacija`),
  `StavkaRezervacije.aranzman : Aranzman` (replacing `idAranzman`)
- `Zaduzenje.agent : Agent`, `Zaduzenje.region : Region` (replacing
  `idAgent`/`idRegion`)

All eight domain classes — including `Zaduzenje` — implement
`GenericEntity`. The `repository.db.impl.RepositoryDbGeneric` layer
(Server) is responsible for translating between the two representations:
`GenericEntity.getInsertValues()`/`getUpdateSetParams()` extract the
referenced object's id (e.g. `agent.getIdAgent()`) when building SQL
parameters, and `fromResultSet()` populates a *shallow* reference object
(id only) when reading a row back, since the generic repository's
`SELECT *` has no JOIN capability — a Service Operation that needs the full
referenced row must fetch it with an additional repository call.
