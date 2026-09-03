# Use Cases

There are 25 use cases.

## Complete list

| Code | Name | Search criteria / loaded lists |
|---|---|---|
| SK1 | Kreiraj rezervaciju | loaded Agent, Putnik, Aranzman |
| SK2 | Pretrazi rezervaciju | Rezervacija, Agent, Putnik, Aranzman |
| SK3 | Promeni rezervaciju | loaded Agent, Putnik, Aranzman; search Rezervacija, Agent, Putnik, Aranzman |
| SK4 | Obrisi rezervaciju | Rezervacija, Agent, Putnik, Aranzman |
| SK5 | Kreiraj putnika | loaded Mesto |
| SK6 | Pretrazi putnika | Putnik, Mesto |
| SK7 | Promeni putnika | loaded Mesto; search Putnik, Mesto |
| SK8 | Obrisi putnika | Putnik, Mesto |
| SK9 | Prijavi agenta | username/password |
| SK10 | Kreiraj agenta | loaded Region |
| SK11 | Pretrazi agenta | Agent, Region |
| SK12 | Promeni agenta | loaded Region; search Agent, Region |
| SK13 | Obrisi agenta | Agent, Region |
| SK14 | Kreiraj aranzman | — |
| SK15 | Pretrazi aranzman | Aranzman |
| SK16 | Promeni aranzman | Aranzman |
| SK17 | Obrisi aranzman | Aranzman |
| SK18 | Kreiraj mesto | — |
| SK19 | Pretrazi mesto | Mesto |
| SK20 | Promeni mesto | Mesto |
| SK21 | Obrisi mesto | Mesto |
| SK22 | Ubaci region | — |
| SK23 | Pretrazi region | Region |
| SK24 | Promeni region | Region |
| SK25 | Obrisi region | Region |

## Detailed scenarios supplied by the specification

Detailed scenario documentation exists for:

- SK1 Kreiraj rezervaciju
- SK2 Pretrazi rezervaciju
- SK3 Promeni rezervaciju
- SK5 Kreiraj putnika
- SK6 Pretrazi putnika
- SK7 Promeni putnika
- SK8 Obrisi putnika
- SK9 Prijavi agenta
- SK22 Ubaci region

## Scenario rules

### SK1 — Kreiraj rezervaciju

Preconditions include:
- Client and Server are running.
- Agent is logged in.
- Reservation form is shown.
- lists of Agent, Putnik and Aranzman are loaded.

Main scenario:
1. Agent calls the system to create a reservation.
2. System creates the reservation.
3. System displays reservation and success message.
4. Agent enters reservation data.
5. Agent validates entered data.
6. Agent calls the system to save the reservation.
7. System saves reservation data.
8. System displays reservation and success message.

Failure cases include inability to create or save the reservation.

### SK2 — Pretrazi rezervaciju

Search criteria:
- Rezervacija
- Agent
- Putnik
- Aranzman

Main flow:
1. Agent selects criteria.
2. Agent requests search.
3. System searches.
4. System displays matching reservations.
5. Agent selects a reservation.
6. Agent requests the selected reservation.
7. System searches for it.
8. System displays it.

Failure cases:
- no reservations for criteria
- selected reservation cannot be found

### SK3 — Promeni rezervaciju

The scenario first loads Agent, Putnik and Aranzman lists, searches for
reservations, selects a reservation, modifies it, validates it, and asks the
system to save it.

Failure cases exist for:
- no search results
- selected reservation not found
- reservation cannot be saved

### SK5 — Kreiraj putnika

Preconditions:
- Client and Server running
- Agent logged in
- passenger form shown
- Mesto list loaded

Flow:
1. create passenger
2. enter data
3. validate data
4. save passenger
5. display result

### SK6 — Pretrazi putnika

Search criteria:
- Putnik
- Mesto

Flow:
1. choose criteria
2. request search
3. system searches
4. display list
5. select passenger
6. request passenger
7. system finds passenger
8. display passenger

### SK7 — Promeni putnika

Flow:
1. search passengers
2. select passenger
3. find passenger
4. modify data
5. validate
6. save
7. display result

### SK8 — Obrisi putnika

Flow:
1. search passengers
2. select passenger
3. find passenger
4. request deletion
5. system deletes passenger
6. display result

Failure cases include inability to search/find/delete.

### SK9 — Prijavi agenta

Flow:
1. Agent enters username/password.
2. Client validates input.
3. Client asks server to verify credentials.
4. Server verifies credentials.
5. System displays success.
6. Client opens main form/menu.

Failure cases:
- invalid credentials
- main form cannot be opened

### SK22 — Ubaci region

Flow:
1. Agent enters region data.
2. Client validates data.
3. Client asks system to save the region.
4. System saves it.
5. System displays region and success message.

Failure:
- region cannot be saved.


# IMPLEMENTATION SCOPE

Only these nine use cases are to be implemented:

1. SK1 — Kreiraj rezervaciju
2. SK2 — Pretrazi rezervaciju
3. SK3 — Promeni rezervaciju
4. SK5 — Kreiraj putnika
5. SK6 — Pretrazi putnika
6. SK7 — Promeni putnika
7. SK8 — Obrisi putnika
8. SK9 — Prijavi agenta
9. SK22 — Ubaci region

All other use cases are OUT OF SCOPE unless explicitly requested.


# AUTHORITATIVE SEMINAR SCOPE

Only these nine use cases are implemented:

- SK1 — Kreiraj rezervaciju
- SK2 — Pretraži rezervaciju
- SK3 — Promeni rezervaciju
- SK5 — Kreiraj putnika
- SK6 — Pretraži putnika
- SK7 — Promeni putnika
- SK8 — Obriši putnika
- SK9 — Prijavi agenta
- SK22 — Ubaci region

The detailed scenarios for these nine use cases are authoritative for which
supporting operations are necessary.

All other use cases are out of scope.
