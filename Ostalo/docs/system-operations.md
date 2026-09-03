# IMPLEMENTATION SCOPE AND AUTHORITATIVE CONTRACTS

Only system operations necessary for the nine selected use cases may be
implemented:

- SK1 — Kreiraj rezervaciju
- SK2 — Pretraži rezervaciju
- SK3 — Promeni rezervaciju
- SK5 — Kreiraj putnika
- SK6 — Pretraži putnika
- SK7 — Promeni putnika
- SK8 — Obriši putnika
- SK9 — Prijavi agenta
- SK22 — Ubaci region

The following eight contracts are explicitly supplied by the user:

1. UG1 `PrijaviAgent(korisnickoIme, sifra): signal` — SK9
2. UG2 `KreirajRezervacija(Rezervacija): signal` — SK1
3. UG3 `UbaciRegion(Region): signal` — SK22
4. UG4 `PromeniRezervacija(Rezervacija): signal` — SK3
5. UG5 `ObrisiPutnik(Putnik): signal` — SK8
6. UG6 `PretraziRezervacija(Rezervacija): signal` — SK2
7. UG7 `vratiListuRezervacija(kriterijumRezervacija, Lista<Rezervacija>): signal` — SK2, SK3
8. UG8 `vratiListuSviPutnik(Lista<Putnik>): signal` — SK1, SK3

For each of these:
- preserve the operation name;
- preserve the operation arguments;
- preserve the SK mapping;
- preserve the supplied preconditions;
- preserve the supplied postconditions.

The detailed scenarios determine which additional supporting operations are
needed to complete these nine SKs. Such supporting operations are allowed
only when they are directly required by one of these nine scenarios.

The 41-operation catalog later in this document is reference material; it is
NOT an instruction to implement all 41 operations.


# IMPLEMENTATION SCOPE — IMPORTANT

Only system operations required by SK1, SK2, SK3, SK5, SK6, SK7, SK8, SK9 and SK22 must be implemented.

The complete 41-operation list below is reference documentation only. Do NOT implement all 41 operations.

# System Operations

The specification defines 41 system operations.

## Complete list

1. `KreirajAgent(Agent)`
2. `KreirajAranzman(Aranzman)`
3. `KreirajMesto(Mesto)`
4. `KreirajPutnik(Putnik)`
5. `KreirajRezervacija(Rezervacija)`
6. `ObrisiAgent(Agent)`
7. `ObrisiAranzman(Aranzman)`
8. `ObrisiMesto(Mesto)`
9. `ObrisiPutnik(Putnik)`
10. `ObrisiRegion(Region)`
11. `ObrisiRezervacija(Rezervacija)`
12. `PretraziAgent(Agent)`
13. `PretraziAranzman(Aranzman)`
14. `PretraziMesto(Mesto)`
15. `PretraziPutnik(Putnik)`
16. `PretraziRegion(Region)`
17. `PretraziRezervacija(Rezervacija)`
18. `PrijaviAgent(korisnickoIme, sifra)`
19. `PromeniAgent(Agent)`
20. `PromeniAranzman(Aranzman)`
21. `PromeniMesto(Mesto)`
22. `PromeniPutnik(Putnik)`
23. `PromeniRegion(Region)`
24. `PromeniRezervacija(Rezervacija)`
25. `UbaciRegion(Region)`
26. `vratiListuAgent(kriterijumAgent, Lista<Agent>)`
27. `vratiListuAgent(kriterijumRegion, Lista<Agent>)`
28. `vratiListuAranzman(kriterijumAranzman, Lista<Aranzman>)`
29. `vratiListuMesto(kriterijumMesto, Lista<Mesto>)`
30. `vratiListuPutnik(kriterijumMesto, Lista<Putnik>)`
31. `vratiListuPutnik(kriterijumPutnik, Lista<Putnik>)`
32. `vratiListuRegion(kriterijumRegion, Lista<Region>)`
33. `vratiListuRezervacija(kriterijumAgent, Lista<Rezervacija>)`
34. `vratiListuRezervacija(kriterijumAranzman, Lista<Rezervacija>)`
35. `vratiListuRezervacija(kriterijumPutnik, Lista<Rezervacija>)`
36. `vratiListuRezervacija(kriterijumRezervacija, Lista<Rezervacija>)`
37. `vratiListuSviAgent(Lista<Agent>)`
38. `vratiListuSviAranzman(Lista<Aranzman>)`
39. `vratiListuSviMesto(Lista<Mesto>)`
40. `vratiListuSviPutnik(Lista<Putnik>)`
41. `vratiListuSviRegion(Lista<Region>)`

## Mapping from use cases

### Reservation

SK1:
- `KreirajRezervacija`
- `PromeniRezervacija`
- `vratiListuSviAgent`
- `vratiListuSviPutnik`
- `vratiListuSviAranzman`

SK2:
- `PretraziRezervacija`
- `vratiListuRezervacija(kriterijumRezervacija, ...)`
- `vratiListuRezervacija(kriterijumAgent, ...)`
- `vratiListuRezervacija(kriterijumPutnik, ...)`
- `vratiListuRezervacija(kriterijumAranzman, ...)`

SK3:
- `PromeniRezervacija`
- `PretraziRezervacija`
- `vratiListuSviAgent`
- `vratiListuSviPutnik`
- `vratiListuSviAranzman`
- the four reservation-list operations

SK4:
- `ObrisiRezervacija`
- the four reservation-list operations

### Passenger

SK5:
- `KreirajPutnik`
- `PromeniPutnik`
- `vratiListuSviMesto`

SK6:
- `PretraziPutnik`
- `vratiListuPutnik(kriterijumPutnik, ...)`
- `vratiListuPutnik(kriterijumMesto, ...)`

SK7:
- `PromeniPutnik`
- `PretraziPutnik`
- `vratiListuSviMesto`
- both passenger-list operations

SK8:
- `ObrisiPutnik`
- both passenger-list operations

### Agent

SK9:
- `PrijaviAgent`

SK10:
- `KreirajAgent`
- `PromeniAgent`
- `vratiListuSviRegion`

SK11:
- `PretraziAgent`
- `vratiListuAgent(kriterijumAgent, ...)`
- `vratiListuAgent(kriterijumRegion, ...)`

SK12:
- `PromeniAgent`
- `PretraziAgent`
- `vratiListuSviRegion`
- both Agent-list operations

SK13:
- `ObrisiAgent`
- both Agent-list operations

### Aranzman

SK14:
- `KreirajAranzman`
- `PromeniAranzman`

SK15:
- `PretraziAranzman`
- `vratiListuAranzman`

SK16:
- `PromeniAranzman`
- `PretraziAranzman`
- `vratiListuAranzman`

SK17:
- `ObrisiAranzman`
- `vratiListuAranzman`

### Mesto

SK18:
- `KreirajMesto`
- `PromeniMesto`

SK19:
- `PretraziMesto`
- `vratiListuMesto`

SK20:
- `PromeniMesto`
- `PretraziMesto`
- `vratiListuMesto`

SK21:
- `ObrisiMesto`
- `vratiListuMesto`

### Region

SK22:
- `UbaciRegion`
- `PromeniRegion`

SK23:
- `PretraziRegion`
- `vratiListuRegion`

SK24:
- `PromeniRegion`
- `PretraziRegion`
- `vratiListuRegion`

SK25:
- `ObrisiRegion`
- `vratiListuRegion`

---

# System-operation contracts supplied by the specification

## UG1 — PrijaviAgent

Operation:
`PrijaviAgent(korisnickoIme, sifra): signal`

Use case:
SK9

Preconditions:
/

Postconditions:
User is logged into the system.

## UG2 — KreirajRezervacija

Operation:
`KreirajRezervacija(Rezervacija): signal`

Use case:
SK1

Preconditions:
Structural and value constraints on Rezervacija must be satisfied.

Postconditions:
A new Rezervacija object has been created.

## UG3 — UbaciRegion

Operation:
`UbaciRegion(Region): signal`

Use case:
SK22

Preconditions:
Structural and value constraints on Region must be satisfied.

Postconditions:
A new Region object has been created.

## UG4 — PromeniRezervacija

Operation:
`PromeniRezervacija(Rezervacija): signal`

Use case:
SK3

Preconditions:
Structural and value constraints on Rezervacija must be satisfied.

Postconditions:
The Rezervacija object has been changed.

## UG5 — ObrisiPutnik

Operation:
`ObrisiPutnik(Putnik): signal`

Use case:
SK8

Preconditions:
Structural and value constraints on Putnik must be satisfied.

Postconditions:
The Putnik object has been deleted.

## UG6 — PretraziRezervacija

Operation:
`PretraziRezervacija(Rezervacija): signal`

Use case:
SK2

Preconditions:
/

Postconditions:
The requested Rezervacija object has been found.

## UG7 — vratiListuRezervacija

Operation:
`vratiListuRezervacija(kriterijumRezervacija, Lista<Rezervacija>): signal`

Use cases:
SK2, SK3

Preconditions:
/

Postconditions:
A list of requested Rezervacija objects has been found.

The other reservation criteria operations follow the same operation family
defined in the system-operation list.

## UG8 — vratiListuSviPutnik

Operation:
`vratiListuSviPutnik(Lista<Putnik>): signal`

Use cases:
SK1, SK3

Preconditions:
/

Postconditions:
A list of all Putnik objects has been found.
