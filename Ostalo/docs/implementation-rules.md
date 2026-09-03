# Implementation Rules

## 1. General rule

Implement the specification, not an imagined application.

The source specification has priority over:
- personal design preferences
- framework conventions
- "cleaner" alternative architectures
- speculative refactoring

## 2. Naming

Preserve the domain and system-operation names from the specification:

- Agent
- Putnik
- Rezervacija
- Aranzman
- Mesto
- Region
- StavkaRezervacije
- Zaduzenje

Preserve operation names such as:
- `KreirajRezervacija`
- `PromeniRezervacija`
- `ObrisiRezervacija`
- `PretraziRezervacija`
- `PrijaviAgent`
- `UbaciRegion`

Do not translate these names into English unless the user explicitly asks.

## 3. Client implementation

A GUI action should invoke the appropriate Controller/communication method,
which sends a Request to the Server.

The form should not:
- execute SQL
- call repository methods
- construct database connections
- contain server business workflows

## 4. Validation

Client-side validation is part of the documented scenarios.

Server-side SO logic must still preserve server/database consistency.

Do not assume client validation alone is enough to guarantee database
constraints.

## 5. Repository

Repositories are persistence abstractions.

They should contain:
- SQL/JDBC interaction
- mapping between ResultSet/data and domain objects
- CRUD/search persistence operations

They should not contain:
- Swing logic
- user interaction
- complete multi-step business workflows that belong in SO

## 6. Service Operations

An SO corresponds to a system operation.

A concrete SO should be small and focused.

Use the reference project's `AbstractSO` pattern.

If an operation needs several repository calls, keep the business workflow in
the SO and use repositories for persistence.

## 7. Controller

The Server Controller dispatches operations to the appropriate SO.

Do not make Controller responsible for:
- GUI
- SQL
- all business rules

## 8. Threads

Follow the reference pattern with:
- `ServerThread`
- `HandleClientThread`

Do not create an unrelated concurrency architecture.

## 9. Database

Only Server-side code talks to the database.

Use the repository/database package structure from the reference project.

The actual database schema must correspond to the relational model.

## 10. Transactions

When one logical operation consists of multiple dependent database changes,
preserve consistency. Do not leave half-completed reservation changes if the
operation is supposed to be atomic.

The exact transaction mechanism should follow the reference implementation
pattern or the project's explicitly chosen implementation.

## 11. Communication

Use:
- Request
- Response
- ResponseType
- Sender
- Receiver
- Operations

as the shared communication vocabulary.

Do not create REST endpoints or JSON APIs unless explicitly requested.

## 12. Error handling

Use explicit response/error behavior.

When the specification says the system cannot perform an operation, the Client
should present the appropriate message rather than silently ignoring the
failure.

## 13. GUI

The supplied reference project uses Java Swing forms and `.form` files.

If the actual project is configured for NetBeans GUI Builder, preserve the
generated structure and do not manually rewrite generated GUI code unless
necessary.

## 14. Build

The reference project uses Ant/NetBeans:
- `build.xml`
- `nbproject`

Do not replace the build system unless explicitly requested.

## 15. No speculative refactoring

Do not:
- rename all packages
- introduce new frameworks
- convert to Maven/Gradle
- convert Swing to JavaFX
- convert to REST
- convert JDBC to JPA
- redesign the domain model

unless the user explicitly changes the project requirements.

## 16. Feature implementation checklist

For every feature:

1. identify SK
2. identify system operations
3. inspect scenario
4. inspect contract
5. inspect domain object
6. inspect constraints
7. inspect relational mapping
8. modify CommonLib if needed
9. modify Server SO/repository/controller if needed
10. modify Client communication/controller/view if needed
11. build
12. test
13. verify architecture
14. report changes

## 17. When uncertain

Ask the user.

Examples:
- unknown Java version
- unknown database credentials
- unspecified GUI behavior
- unspecified search semantics
- conflict between a supplied diagram and a textual requirement
- missing repository implementation detail

Do not invent an answer just to keep coding.
