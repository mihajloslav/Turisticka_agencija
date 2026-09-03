# SEMINAR PROJECT — CLAUDE CODE PROJECT RULES

## 0. PURPOSE

This repository contains a Java client-server information system for managing
reservations of a travel agency.

These instructions are mandatory project rules for Claude Code.

The project specification, conceptual model, relational model, use cases,
system operations, system-operation contracts, constraints, and the supplied
reference project structure are the source of truth.

If implementation convenience conflicts with the specification, the
specification wins.

DO NOT invent business rules.
DO NOT silently change the domain model.
DO NOT silently change the relational model.
DO NOT replace composition with aggregation or vice versa.
DO NOT replace the associative class Zaduzenje with a different relationship.
DO NOT introduce a different architecture merely because it is more modern.
DO NOT introduce Spring Boot, REST, Hibernate/JPA, ORM, Maven, Gradle, or
other frameworks unless the user explicitly requests them and confirms that
they are allowed by the assignment.
DO NOT move business logic into the GUI.
DO NOT access the database directly from the Client.
DO NOT make the Client communicate directly with Repository classes.
DO NOT bypass the CommonLib communication protocol.

When information is missing, explicitly identify the missing information
instead of inventing it.

---

# 1. SYSTEM OVERVIEW

The system is a Java software system for managing reservations of a travel
agency.

The business concepts are:

- Agent — service provider
- Putnik — service recipient
- Rezervacija — document describing the service process
- Aranzman — codebook
- Mesto — codebook
- Region — codebook
- StavkaRezervacije — reservation item
- Zaduzenje — associative class between Agent and Region

The system authenticates an Agent using korisnickoIme and sifra.

Main menu structure from the specification:

1. Dokumenti
   1.1 Rezervacija
2. Pružalac usluge
   2.1 Agent
3. Primalac usluge
   3.1 Putnik
4. Šifarnici
   4.1 Aranzman
   4.2 Mesto
   4.3 Region
5. Podešavanja softverskog sistema
6. O programu

---

# 2. ARCHITECTURE

The required architecture is a Client / CommonLib / Server / Database
architecture.

Conceptually:

Client
  -> CommonLib communication objects
  -> Server
  -> Controller
  -> Service Operation (SO)
  -> Repository
  -> Database

The Client must not access the database.

The Server owns application logic and database access.

CommonLib contains objects shared by Client and Server, especially:

- communication classes
- domain classes

The reference project supplied for the assignment uses a NetBeans/Ant-style
multi-project structure with Client, CommonLib and Server projects. The
reference structure contains Java Swing `.form` files on the Client and
Server-side GUI files, plus `nbproject` and `build.xml`.

Do not assume an exact Java version from the specification unless it is
explicitly configured in the actual project.

---

# 3. REFERENCE PROJECT STRUCTURE

The supplied reference structure is the architectural template.

Top level:

- database SQL file
- Client
- CommonLib
- Server

Important Client packages:

- `client`
- `client.communication`
- `controller`
- `validation`
- `view`
- `view.components`
- `view.form`

Important CommonLib packages:

- `communication`
- `domain`

Important CommonLib communication classes:

- `Operations`
- `Receiver`
- `Request`
- `Response`
- `ResponseType`
- `Sender`

Important Server packages:

- `constant`
- `controller`
- `repository`
- `repository.db`
- `repository.db.impl`
- `server`
- `so`
- `threads`
- `view`

Important Server classes from the reference:

- `MyServerConstants`
- `Controller`
- `Repository`
- `DbRepository`
- `DbConnectionFactory`
- `RepositoryDbGeneric`
- entity-specific repository implementations
- `Server`
- `AbstractSO`
- concrete SO classes
- `ServerThread`
- `HandleClientThread`

Generated folders such as `build/` contain compiled/generated output and are
not the source of truth. Source code belongs under `src/`.

Do not copy reference domain names such as Product, Invoice, Manufacturer, or
User into this project. They belong to the example project only.

---



# 3A. NETBEANS GUI BUILDER / FORM REQUIREMENT — MANDATORY

The user will provide **three complete/working reference projects**. These
projects are not merely examples of business logic; they are the primary
reference for how the GUI, NetBeans project structure, `.form` files, Swing
components, and GUI Builder integration should be implemented.

## Mandatory rule

ALL GUI forms created or modified in this project MUST be compatible with
**NetBeans GUI Builder**.

This means:

- Every GUI form that is intended to be edited visually in NetBeans must have
  the corresponding `.form` file.
- The `.java` file and `.form` file must remain synchronized.
- Forms must be structured in the same general way as the supplied reference
  projects.
- NetBeans must be able to open the form in **Design** view.
- The implementation must not rely on a hand-written Swing structure that
  prevents NetBeans GUI Builder from opening/editing the form.
- Do not delete `.form` files and replace them with manually constructed
  Swing layouts.
- Do not generate GUI code in a way that makes the form effectively
  uneditable in NetBeans.
- Preserve the NetBeans-generated-code conventions used by the reference
  projects, including the generated `initComponents()` section where
  applicable.
- Event handlers and custom logic should be kept outside generated sections
  whenever the reference structure allows it.
- If a form is created from scratch, create it in a way that NetBeans
  recognizes as a GUI Builder form, not merely as an ordinary Java class.

## Three reference projects

The user will give Claude **three ready-made projects** to inspect.

Claude MUST inspect all three before creating the project's GUI.

Use them to determine:

1. NetBeans project structure
2. package organization
3. Swing component conventions
4. `.form` structure
5. generated `initComponents()` style
6. event-handler organization
7. layout/group definitions used by NetBeans
8. naming conventions for forms/components
9. how forms communicate with Controllers
10. how the reference projects separate generated GUI code from custom code

Do NOT blindly copy business/domain logic from the reference projects if it
does not belong to this reservation system.

The three projects are **GUI/architecture references**, while the reservation
system specification remains the source of truth for this project's domain,
use cases, system operations, constraints, and business rules.

## Before implementing any form

Claude should:

1. inspect the corresponding forms in all three supplied reference projects;
2. identify the common NetBeans GUI Builder pattern;
3. inspect both the `.java` and `.form` files;
4. inspect the relevant `nbproject` configuration;
5. follow that pattern when creating the new form;
6. verify that the resulting form can be opened in NetBeans Design view.

## Important distinction

Reference projects answer:

> "HOW should the Java/Swing/NetBeans project be structured and how should
> forms be created?"

The reservation-system specification answers:

> "WHAT should the application do and what domain/business rules must it
> implement?"

Never use a reference project's unrelated business concepts to override the
reservation-system specification.

## NetBeans verification

Whenever a GUI form is added or significantly changed, verify that:

- the `.java` file exists;
- the `.form` file exists;
- both belong to the correct NetBeans source package;
- the form is recognized by NetBeans GUI Builder;
- there are no broken references in the `.form`;
- the generated code remains valid;
- the project can still build through its existing NetBeans/Ant setup.

If Claude cannot verify a form's NetBeans compatibility from the available
project files, it must say so instead of claiming that the form is compatible.

# 4. DOMAIN MODEL — IMMUTABLE SPECIFICATION

The domain model consists of exactly these concepts:

1. Agent
2. Aranzman
3. Mesto
4. Region
5. Putnik
6. Rezervacija
7. StavkaRezervacije
8. Zaduzenje

The exact attributes are defined in `docs/domain-model.md`.

The conceptual UML relationships, cardinalities, composition/aggregation
semantics, and associative-class semantics are mandatory.

The black diamond in the conceptual model means COMPOSITION.
The white diamond means AGGREGATION.

Never change the relationship type just to make Java implementation easier.

`Zaduzenje` is an associative class between `Agent` and `Region`.

`StavkaRezervacije` is part of `Rezervacija` according to the supplied
conceptual model and must preserve the specified composition semantics.

---

# 5. RELATIONAL MODEL — IMMUTABLE SPECIFICATION

The relational model is fixed to these eight relations:

1. Agent
2. Aranzman
3. Mesto
4. Region
5. Putnik
6. Rezervacija
7. StavkaRezervacije
8. Zaduzenje

Exact attributes and relationship/foreign-key rules are documented in
`docs/relational-model.md` and `docs/constraints.md`.

Do not add tables merely because an ORM or implementation pattern would make
them convenient.

Do not rename columns or relations without explicit approval.

---

# 6. IMPLEMENTATION SCOPE — ONLY NINE USE CASES

IMPORTANT: The seminar implementation scope is LIMITED to these nine use cases:

- SK1 — Kreiraj rezervaciju
- SK2 — Pretrazi rezervaciju
- SK3 — Promeni rezervaciju
- SK5 — Kreiraj putnika
- SK6 — Pretrazi putnika
- SK7 — Promeni putnika
- SK8 — Obrisi putnika
- SK9 — Prijavi agenta
- SK22 — Ubaci region

ONLY these nine use cases are to be implemented. The remaining use cases from the broader specification are OUT OF SCOPE unless the user explicitly asks for them later.

The system operations to implement are ONLY those required by these nine use cases. Do not implement unrelated CRUD operations, SOs, GUI forms, controllers, repository methods, communication operations, or menu items merely because they appear in the complete specification.

The supplied detailed scenarios for these nine use cases are authoritative. Preserve the separation between client actions, validation, system operations, server behavior, and UI interaction.


# 6A. AUTHORITATIVE IMPLEMENTATION SCOPE AND CONTRACTS

The seminar implementation scope is exactly these nine use cases:

- SK1 — Kreiraj rezervaciju
- SK2 — Pretraži rezervaciju
- SK3 — Promeni rezervaciju
- SK5 — Kreiraj putnika
- SK6 — Pretraži putnika
- SK7 — Promeni putnika
- SK8 — Obriši putnika
- SK9 — Prijavi agenta
- SK22 — Ubaci region

The following eight system-operation contracts are the authoritative contracts
for the implementation scope:

### UG1 — PrijaviAgent
Operation:
`PrijaviAgent(korisnickoIme, sifra): signal`

Use case:
SK9

Preconditions:
/

Postconditions:
Korisnik je prijavljen na sistem.

### UG2 — KreirajRezervacija
Operation:
`KreirajRezervacija(Rezervacija): signal`

Use case:
SK1

Preconditions:
Strukturna i vrednosna ograničenja nad objektom klase Rezervacija moraju biti
zadovoljena.

Postconditions:
Napravljen je novi objekat klase Rezervacija.

### UG3 — UbaciRegion
Operation:
`UbaciRegion(Region): signal`

Use case:
SK22

Preconditions:
Strukturna i vrednosna ograničenja nad objektom klase Region moraju biti
zadovoljena.

Postconditions:
Napravljen je novi objekat klase Region.

### UG4 — PromeniRezervacija
Operation:
`PromeniRezervacija(Rezervacija): signal`

Use case:
SK3

Preconditions:
Strukturna i vrednosna ograničenja nad objektom klase Rezervacija moraju biti
zadovoljena.

Postconditions:
Objekat klase Rezervacija je promenjen.

### UG5 — ObrisiPutnik
Operation:
`ObrisiPutnik(Putnik): signal`

Use case:
SK8

Preconditions:
Strukturna i vrednosna ograničenja nad objektom klase Putnik moraju biti
zadovoljena.

Postconditions:
Objekat klase Putnik je obrisan.

### UG6 — PretraziRezervacija
Operation:
`PretraziRezervacija(Rezervacija): signal`

Use case:
SK2

Preconditions:
/

Postconditions:
Pronađen je traženi objekat klase Rezervacija.

### UG7 — vratiListuRezervacija
Operation:
`vratiListuRezervacija(kriterijumRezervacija, Lista<Rezervacija>): signal`

Use cases:
SK2, SK3

Preconditions:
/

Postconditions:
Pronađena je lista traženih objekata klase Rezervacija.

IMPORTANT:
The full specification contains multiple reservation-list criteria
operations. Implement only the variants actually required by the detailed
SK2/SK3 scenarios and the project's existing communication design. Do not
implement unrelated list operations for out-of-scope use cases.

### UG8 — vratiListuSviPutnik
Operation:
`vratiListuSviPutnik(Lista<Putnik>): signal`

Use cases:
SK1, SK3

Preconditions:
/

Postconditions:
Pronađena je lista svih objekata klase Putnik.

## Contract rule

These eight UG contracts MUST be explicitly represented in the project's
implementation planning.

The operation-to-use-case mapping MUST NOT be changed.

For SK1, SK2, SK3, SK5, SK6, SK7, SK8, SK9 and SK22, Claude must also inspect
the detailed scenario and identify any additional supporting list/search
system operations required by that scenario. Supporting operations are
allowed ONLY when they are necessary to complete one of these nine selected
use cases.

Do not implement the entire 41-operation catalog.

Do not implement system operations that belong exclusively to the other
sixteen use cases.


# 7. SYSTEM OPERATIONS — ONLY THOSE REQUIRED BY THE NINE SKs

Implement ONLY system operations needed by the nine selected use cases. The complete 41-operation list in `docs/system-operations.md` is reference documentation, NOT a request to implement all 41.

Required operation families include:

## Reservation — SK1, SK2, SK3
- `KreirajRezervacija(Rezervacija)`
- `PromeniRezervacija(Rezervacija)`
- `PretraziRezervacija(Rezervacija)`
- reservation list operations required by the search/edit scenarios
- list-loading operations required for Agent, Putnik and Aranzman selections

## Passenger — SK5, SK6, SK7, SK8
- `KreirajPutnik(Putnik)`
- `PromeniPutnik(Putnik)`
- `ObrisiPutnik(Putnik)`
- `PretraziPutnik(Putnik)`
- passenger list operations required by the scenarios
- `vratiListuSviMesto(...)` for the Mesto selection

## Login — SK9
- `PrijaviAgent(korisnickoIme, sifra)`

## Region — SK22
- `UbaciRegion(Region)`

Do NOT implement system operations belonging only to the other 16 use cases.
Do NOT create SO classes for unrelated operations.
Do NOT create GUI functionality for unrelated use cases.

The exact operation signatures and contracts supplied in the specification remain authoritative.

# 8. SYSTEM-OPERATION CONTRACTS

System-operation contracts are mandatory behavioral specifications.

The supplied documentation explicitly defines eight representative contracts,
including:

- UG1 `PrijaviAgent`
- UG2 `KreirajRezervacija`
- UG3 `UbaciRegion`
- UG4 `PromeniRezervacija`
- UG5 `ObrisiPutnik`
- UG6 `PretraziRezervacija`
- UG7 `vratiListuRezervacija`
- UG8 `vratiListuSviPutnik`

Before implementing a system operation, check:

1. its use case,
2. its contract if defined,
3. relevant domain constraints,
4. relational constraints,
5. the sequence/scenario behavior.

Do not silently invent preconditions or postconditions.

---

# 9. CONSTRAINTS

All structural and value constraints are mandatory.

Examples:

Agent:
- idAgent: Integer, NOT NULL
- ime: String, NOT NULL
- prezime: String, NOT NULL
- email: String, NOT NULL, contains `@`
- telefon: String, NOT NULL
- korisnickoIme: String, NOT NULL
- sifra: String, NOT NULL, length > 6

Aranzman:
- idAranzman: Integer, NOT NULL
- naziv: String, NOT NULL
- opis: String, optional
- tipAranzmana: String, NOT NULL
- cenaPoOsobi: Double, NOT NULL, > 0

Mesto:
- idMesto: Integer, NOT NULL
- naziv: String, NOT NULL
- pttBroj: String, NOT NULL, length = 5
- drzava: String, NOT NULL
- the specification also contains `pozivniBroj` in the relational model;
  preserve it exactly.

Region:
- idRegion: Integer, NOT NULL
- naziv: String, NOT NULL
- oznaka: String, length <= 6

Putnik:
- idPutnik: Integer, NOT NULL
- ime: String, NOT NULL
- prezime: String, NOT NULL
- email: String, NOT NULL, contains `@`
- telefon: String, NOT NULL
- jmbg: String, NOT NULL, length = 13
- brojPasosa: String, NOT NULL
- idMesto: Integer, NOT NULL

Rezervacija:
- idRezervacija: Integer, NOT NULL
- datumKreiranja: Date, NOT NULL
- ukupanIznos > 0
- ukupanIznos = SUM(StavkaRezervacije.cena)
- statusPlacanja: String, NOT NULL
- napomena: optional
- idAgent: Integer, NOT NULL
- idPutnik: Integer, NOT NULL

StavkaRezervacije:
- idRezervacija: Integer, NOT NULL
- rb: Integer, NOT NULL
- brojOsoba: Integer, NOT NULL, > 0
- datumPolaska: Date, NOT NULL
- datumDolaska: Date, NOT NULL
- datumDolaska > datumPolaska
- popust: Double, range 0.0–1.0
- if brojOsoba >= 3, popust = 0.1; otherwise popust = 0
- cena: Double, NOT NULL, > 0
- cena = (brojOsoba * Aranzman.cenaPoOsobi) * (1 - popust)
- idAranzman: Integer, NOT NULL

Zaduzenje:
- idAgent: Integer, NOT NULL
- idRegion: Integer, NOT NULL
- datumOd: Date, NOT NULL
- datumDo: optional
- if datumDo is present, datumDo > datumOd
- mesecnaKvota > 0
- napomena: optional

Full structural/foreign-key rules are in `docs/constraints.md`.

---

# 10. CLIENT RULES

The Client is responsible for:

- Swing GUI/forms
- collecting user input
- client-side validation where specified
- displaying data
- calling the server through the communication layer
- displaying success/error messages

The Client must not:

- execute SQL
- instantiate database repositories
- contain server-side business rules
- bypass system operations
- communicate directly with database classes

The GUI should follow the terminology and scenarios from the specification.

---

# 11. COMMONLIB RULES

CommonLib is shared by Client and Server.

It contains:

- domain classes
- communication classes
- operation identifiers / protocol definitions

Communication classes include:

- `Operations`
- `Receiver`
- `Request`
- `Response`
- `ResponseType`
- `Sender`

Domain classes must be serializable/usable across the client-server boundary
according to the implementation pattern used by the reference project.

Do not place server-only repository or database code in CommonLib.

Do not place GUI code in CommonLib.

---

# 12. SERVER RULES

The Server is responsible for:

- accepting client connections
- receiving requests
- dispatching operations
- executing service operations
- repository/database access
- returning responses

Expected server flow:

Client
 -> Request
 -> Server communication/thread
 -> Controller
 -> Service Operation
 -> Repository
 -> Database
 -> Response
 -> Client

Concrete system operations should be implemented as Service Operations
following the reference project's `AbstractSO` pattern.

Database access belongs in Repository implementations.

The Controller dispatches the requested operation; it must not become a giant
class containing all business logic.

---

# 13. REPOSITORY RULES

Repository is the database-access abstraction.

Expected structure follows the reference project:

- `repository.Repository`
- `repository.db.DbRepository`
- `repository.db.DbConnectionFactory`
- `repository.db.impl.RepositoryDbGeneric`
- entity-specific repository implementations

Repository code performs persistence/database operations.

Business workflows belong in SO classes, not in repositories.

Do not put Swing or client code into repositories.

Do not access JDBC from a Swing form.

---

# 14. SERVICE OPERATION RULES

A Service Operation represents one server-side system operation.

Follow the reference project's `AbstractSO` pattern.

An SO should:

1. receive the operation input,
2. validate/ensure the applicable business constraints,
3. perform the required repository operations,
4. preserve transaction consistency where multiple DB operations are needed,
5. return the appropriate result or propagate an appropriate failure.

For composite operations, do not split the workflow randomly across layers.

Example:
creating/saving a reservation must respect the Reservation and
StavkaRezervacije constraints and the relationship between them.

---

# 15. COMMUNICATION RULES

The exact communication mechanism should follow the reference project.

Use the shared Request/Response protocol.

The Client sends a request describing the requested operation and its argument.
The Server processes it and sends a Response.

Do not invent a REST/HTTP API.

Do not replace the socket/request/response pattern with another protocol
without explicit user approval.

---

# 16. ERROR HANDLING

The specification contains explicit user-facing messages.

When a scenario specifies an error message, preserve its meaning and wording
as closely as possible.

Examples include:

- "Sistem ne može da kreira rezervaciju"
- "Sistem ne može da zapamti rezervaciju"
- "Sistem je našao rezervacije po zadatim kriterijumima"
- "Sistem ne može da nađe rezervacije po zadatim kriterijumima"
- "Sistem ne može da nađe rezervaciju"
- "Sistem je zapamtio putnika"
- "Sistem ne može da zapamti putnika"
- "Sistem ne može da obriše putnika"
- "Korisničko ime i šifra nisu ispravni"

Do not replace specified behavior with silent failures.

---

# 17. IMPLEMENTATION WORKFLOW

When the user asks to implement a feature:

1. Identify the related SK.
2. Read the corresponding use-case scenario.
3. Identify all system operations involved.
4. Check the operation contract, if one exists.
5. Check the domain model.
6. Check the relational model.
7. Check all applicable constraints.
8. Determine which Client classes are affected.
9. Determine which CommonLib classes are affected.
10. Determine which Server controller/SO/repository classes are affected.
11. Implement the smallest coherent change.
12. Compile/build the affected project(s).
13. Fix compile errors.
14. Verify the Client -> Server -> SO -> Repository flow.
15. Verify constraints and database relationships.
16. Verify that no architecture rule was violated.
17. Report exactly what was changed.

Do not perform broad unrelated refactors while implementing a feature.

---

# 18. CHANGE DISCIPLINE

Before changing an existing class:

- inspect its current implementation,
- inspect callers,
- inspect related communication objects,
- inspect related SO/repository classes,
- inspect the corresponding specification.

Do not delete or rename classes simply because a different design would be
cleaner.

Do not rewrite working code unnecessarily.

Do not modify generated `build/` output manually.

---

# 19. VERIFICATION CHECKLIST

After implementing a feature, verify:

- [ ] correct use case
- [ ] correct system operation name
- [ ] correct operation argument/result
- [ ] correct domain object
- [ ] correct constraints
- [ ] correct relational mapping
- [ ] correct Client/Server boundary
- [ ] CommonLib contains shared types only
- [ ] database access is server-side
- [ ] SO contains application/business workflow
- [ ] Repository contains persistence logic
- [ ] GUI contains presentation/input logic
- [ ] specified error behavior is preserved
- [ ] project compiles
- [ ] no unrelated architecture changes were introduced

---

# 20. SOURCE OF TRUTH

Use these project documentation files:

- `docs/architecture.md`
- `docs/domain-model.md`
- `docs/relational-model.md`
- `docs/constraints.md`
- `docs/use-cases.md`
- `docs/system-operations.md`
- `docs/implementation-rules.md`

When uncertain, stop and state the ambiguity.

Never silently make up a requirement.

# FINAL DOMAIN RELATIONSHIP AND DATABASE RULES

The user will additionally provide the ORIGINAL IMAGE of the conceptual UML
model. Claude MUST inspect that image together with `docs/domain-model.md`.

The conceptual UML image is authoritative for the exact relationship type,
multiplicity/cardinality, black/white diamond placement, and associative-class
notation.

MANDATORY UML SEMANTICS:
- BLACK DIAMOND = COMPOSITION.
- WHITE DIAMOND = AGGREGATION.
- Never change composition into aggregation or aggregation into composition.
- Never change multiplicities/cardinalities without explicit user approval.
- `Zaduzenje` is an ASSOCIATIVE CLASS between `Agent` and `Region`.
- `Zaduzenje` contains `datumOd`, `datumDo`, `mesecnaKvota`, `napomena`.
- `Rezervacija` -> `StavkaRezervacije` is a COMPOSITION and its lifecycle
  semantics must be respected.
- Do not treat `Zaduzenje` as an unrelated ordinary entity.
- Do not infer UML multiplicity solely from SQL foreign keys.

The relational model and UML model are related but are not identical concepts:
SQL foreign keys implement the relational model; Java/domain logic must
preserve the intended UML composition/aggregation/associative-class semantics.

DATABASE:
- DBMS is MySQL / MariaDB.
- The database deliverable is `database/baza.sql`.
- It must be executable MySQL/MariaDB SQL.
- It must contain exactly the required relations:
  Agent, Aranzman, Mesto, Region, Putnik, Rezervacija,
  StavkaRezervacije, Zaduzenje.
- Keep the SQL synchronized with `docs/relational-model.md` and
  `docs/constraints.md`.


## CONTRACT-FIRST RULE

Before implementing any of the nine selected use cases:

1. identify the SK;
2. identify its UG contract(s);
3. inspect the detailed scenario;
4. identify only the supporting SOs required by that scenario;
5. implement the contract and scenario without adding unrelated operations.

The eight supplied UG contracts above are authoritative. Do not silently
change their operation names, arguments, preconditions, postconditions, or
SK mappings.


# COMPLETE CONSTRAINTS REFERENCE

The file `docs/constraints-full.md` contains the complete constraints from
the user's supplied relational-model tables. It is authoritative.

Before implementing or modifying any SO, Repository, domain validation, SQL,
or GUI validation related to the selected use cases, Claude MUST consult this
file and preserve all applicable constraints.


# WORKSPACE LAYOUT AND REFERENCE PROJECT ROLES

The working directory contains THREE projects that Claude is allowed to
modify:

- `Client/`
- `CommonLib/`
- `Server/`

These are the actual seminar projects being developed.

The working directory also contains ONE separate MAIN REFERENCE PROJECT.
That project is the reference for the STRUCTURE AND ARCHITECTURE that the
three actual projects must follow.

The working directory also contains THREE separate AUXILIARY REFERENCE
PROJECTS.

## Main reference project

Use the MAIN REFERENCE PROJECT to understand and follow:

- overall Client/CommonLib/Server organization;
- package structure;
- class organization;
- communication architecture;
- repository architecture;
- controllers;
- threads;
- NetBeans forms and `.form` files;
- Ant/NetBeans project configuration;
- general architectural conventions.

The actual `Client`, `CommonLib`, and `Server` projects must follow the main
reference project's structural conventions, adapted to this seminar's
specified domain and requirements.

## Three auxiliary reference projects

The three auxiliary projects are ONLY examples.

They may be inspected when useful to understand:
- Service Operation (SO) implementation;
- concrete SO implementation patterns;
- relevant Repository/SO interaction;
- similar implementation details.

IMPORTANT:
Their overall project structure, package organization, naming organization,
or architecture MUST NOT be copied or followed.

If an auxiliary project's structure differs from the main reference project,
FOLLOW THE MAIN REFERENCE PROJECT.

## What Claude may modify

ONLY:
- `Client/`
- `CommonLib/`
- `Server/`
- required project-owned documentation/database files when explicitly
  necessary.

Do NOT modify the main reference project or the three auxiliary reference
projects.

Before modifying anything, determine which folders are actual projects and
which are references.



# SELECTED IMPLEMENTATION SCOPE — FINAL

Implement ONLY these nine use cases:

- SK1 — Kreiraj rezervaciju
- SK2 — Pretraži rezervaciju
- SK3 — Promeni rezervaciju
- SK5 — Kreiraj putnika
- SK6 — Pretraži putnika
- SK7 — Promeni putnika
- SK8 — Obriši putnika
- SK9 — Prijavi agenta
- SK22 — Ubaci region

Implement the system operations required by these nine use cases, including
the eight authoritative UG contracts documented in
`docs/system-operation-contracts.md`.

Do NOT implement the other use cases or unrelated system operations merely
because they exist in the full specification.


# REFERENCE PROJECTS — FINAL RULES

There are TWO reference projects in the workspace, and they have DIFFERENT
purposes. They MUST NOT be treated as equivalent references.

## 1. EXERCISE PROJECT — AUTHORITATIVE FOR STRUCTURE

There is one project originating from the course exercises.

This project is the AUTHORITATIVE reference for the PROJECT STRUCTURE.

Follow this project for:
- project/module organization;
- package names;
- package hierarchy;
- class/package placement;
- naming conventions used by the exercise;
- Client / CommonLib / Server organization;
- Ant / NetBeans project organization;
- general structural conventions.

IMPORTANT:
This exercise project is NOT a complete implementation of the seminar
application. Therefore, do NOT expect it to contain every class, form,
controller, server feature, configuration feature, or other component that
the final seminar project requires.

The fact that something is missing from the exercise project does NOT mean
that it should be omitted from the final project.

The FINAL PROJECT MUST preserve the STRUCTURE of the exercise project while
adding any missing functionality required by the specification.

## 2. COMPLETE REFERENCE PROJECT — FUNCTIONALITY/IMPLEMENTATION REFERENCE

There is also ONE separate, already completed project.

This completed project does NOT have the same structure/package organization
as the exercise project.

Therefore:
- DO NOT copy its package structure;
- DO NOT rename our packages to match it;
- DO NOT reorganize Client/CommonLib/Server according to it;
- DO NOT treat its architecture as the structural authority.

Instead, use this completed project as an IMPLEMENTATION/FEATURE REFERENCE
for things that the exercise project does not demonstrate but the final
seminar project needs.

In particular, inspect it for:
- how multiple Controllers are organized;
- the pattern where a GUI form has its own Controller;
- complete form/controller interaction;
- server-side GUI/configuration functionality;
- reading configuration from a `.properties` file;
- updating/writing properties back to the `.properties` file;
- other complete features that are absent from the exercise project.

When a feature exists in the completed reference but not in the exercise
project, reproduce the REQUIRED FUNCTIONAL BEHAVIOR in our project while
placing the classes/packages according to the EXERCISE PROJECT STRUCTURE.

## PRIORITY RULE

When the two reference projects differ:

STRUCTURE:
    Exercise Project wins.

FUNCTIONAL/IMPLEMENTATION EXAMPLE for missing features:
    Completed Project may be consulted.

SPECIFICATION:
    CLAUDE.md + docs/ + supplied UML/relational model/constraints/use cases
    always determine WHAT the seminar project must actually do.

Therefore:

    WHAT to implement -> specification
    WHERE/how to organize it -> exercise project structure
    HOW a missing complete feature may be implemented -> completed project
       as an example, adapted to the exercise structure

## SERVER CONFIGURATION FORM / PROPERTIES FILE

The final project requires the server-side configuration form to be able to:

1. READ the required configuration values from the `.properties` file.
2. Display those values in the server configuration GUI.
3. Allow the user to change the relevant properties.
4. UPDATE/WRITE the changed values back to the `.properties` file.
5. Use the resulting configuration appropriately when the Server starts or
   when the configuration is applied, according to the established project
   architecture.

Use the completed reference project to understand how this functionality is
implemented if the exercise project does not contain it.

However, the resulting classes MUST be placed and named according to the
exercise project's package/structure conventions.

Do not invent a different configuration architecture merely because the
completed reference project uses different packages.

## CONTROLLER ORGANIZATION

The completed reference project demonstrates a client-side pattern in which
forms may have their own Controllers.

Use it as the implementation reference for this behavior if the final
project requires it.

Do NOT derive Controller package names or overall project structure from the
completed project. The exercise project's package structure remains
authoritative.

IMPORTANT: Do NOT confuse "one Form = one Controller" with "one System
Operation = one Controller".

The mandatory rule is:
    ONE FORM = ONE DEDICATED CONTROLLER.

A Controller belongs to its GUI form and may invoke one or multiple System
Operations required by that form. Do not create controllers solely because a
System Operation exists.


# FORM / CONTROLLER RULE — MANDATORY

EVERY GUI FORM in the final seminar application MUST have its own dedicated
Controller class.

This applies to:
- Client-side GUI forms;
- Server-side GUI/configuration forms;
- every other Swing form that belongs to the final application.

The relationship is:

    Form -> its own Controller

Do NOT create a single generic Controller shared by multiple forms when that
would violate the one-form/one-controller requirement.

Do NOT create a Controller for a System Operation merely because the SO exists.
The Controller belongs to the GUI FORM. A form's Controller may invoke one or
multiple System Operations required by that form/use case.

The completed reference project is the implementation reference for the
"each form has its own Controller" pattern, but its package names and overall
structure MUST NOT be copied.

The exercise project remains authoritative for package names, package
hierarchy and class placement.

For every newly created form, verify that:
1. the form has its corresponding Controller;
2. the Controller is placed in the package dictated by the exercise project;
3. the Controller is responsible for the form's listeners/interaction;
4. the form and Controller follow the conventions of the completed reference
   project where applicable;
5. the form remains editable in NetBeans GUI Builder (`.java` + `.form`).

This is a mandatory architectural requirement, not an optional suggestion.
