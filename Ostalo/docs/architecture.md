# Architecture

## Required architecture

The assignment uses a Java client-server architecture with three Java
projects plus a database:

- Client
- CommonLib
- Server
- Database

## Reference source structure

The supplied reference project is a NetBeans/Ant-style project. Important
source packages are:

### Client

- `client`
- `client.communication`
- `controller`
- `validation`
- `view`
- `view.components`
- `view.form`

### CommonLib

- `communication`
- `domain`

### Server

- `constant`
- `controller`
- `repository`
- `repository.db`
- `repository.db.impl`
- `server`
- `so`
- `threads`
- `view`

The reference project contains `build.xml`, `nbproject`, `.form` Swing
files and a MySQL JDBC driver on the Server side.

## Layer responsibilities

### Client

Responsible for:
- GUI
- user input
- client-side validation
- calling system operations
- displaying responses

Must NOT:
- execute SQL
- access Repository
- access the database directly
- contain server business workflows

### CommonLib

Responsible for shared:
- domain objects
- Request/Response communication objects
- operation identifiers

Must NOT contain:
- database code
- repositories
- Swing forms
- server-only logic

### Server

Responsible for:
- network communication
- request handling
- controller dispatch
- service operations
- repositories
- database access

### Database

Only the Server accesses the database.

## Communication

Expected flow:

Client
 -> Request
 -> communication layer
 -> Server thread
 -> Controller
 -> Service Operation
 -> Repository
 -> Database

Then:

Database
 -> Repository
 -> Service Operation
 -> Controller
 -> Response
 -> Client

The exact communication implementation should follow the supplied reference
project rather than introducing REST/HTTP or another protocol.

## Important

Do not introduce Spring Boot, REST, Hibernate/JPA, ORM, Maven, Gradle, or other
frameworks unless explicitly approved by the user and assignment.
