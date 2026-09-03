# Authoritative System-Operation Contracts

These are the eight contracts explicitly selected for the seminar.

## UG1 — PrijaviAgent

- Operation: `PrijaviAgent(korisnickoIme, sifra): signal`
- SK: SK9
- Preconditions: /
- Postconditions: Korisnik je prijavljen na sistem.

## UG2 — KreirajRezervacija

- Operation: `KreirajRezervacija(Rezervacija): signal`
- SK: SK1
- Preconditions: Structural and value constraints over `Rezervacija` must be
  satisfied.
- Postconditions: A new `Rezervacija` object has been created.

## UG3 — UbaciRegion

- Operation: `UbaciRegion(Region): signal`
- SK: SK22
- Preconditions: Structural and value constraints over `Region` must be
  satisfied.
- Postconditions: A new `Region` object has been created.

## UG4 — PromeniRezervacija

- Operation: `PromeniRezervacija(Rezervacija): signal`
- SK: SK3
- Preconditions: Structural and value constraints over `Rezervacija` must be
  satisfied.
- Postconditions: The `Rezervacija` object has been changed.

## UG5 — ObrisiPutnik

- Operation: `ObrisiPutnik(Putnik): signal`
- SK: SK8
- Preconditions: Structural and value constraints over `Putnik` must be
  satisfied.
- Postconditions: The `Putnik` object has been deleted.

## UG6 — PretraziRezervacija

- Operation: `PretraziRezervacija(Rezervacija): signal`
- SK: SK2
- Preconditions: /
- Postconditions: The requested `Rezervacija` object has been found.

## UG7 — vratiListuRezervacija

- Operation:
  `vratiListuRezervacija(kriterijumRezervacija, Lista<Rezervacija>): signal`
- SK: SK2, SK3
- Preconditions: /
- Postconditions: The requested list of `Rezervacija` objects has been found.

## UG8 — vratiListuSviPutnik

- Operation: `vratiListuSviPutnik(Lista<Putnik>): signal`
- SK: SK1, SK3
- Preconditions: /
- Postconditions: The list of all `Putnik` objects has been found.

## Supporting-operation rule

These eight are the explicitly supplied contracts. A selected use-case
scenario may require additional supporting list/search operations (for
example loading Agent, Putnik, Aranzman or Mesto lists). Claude may implement
such operations ONLY when the detailed scenario for one of the nine selected
SKs requires them.

No operation that is unrelated to the nine selected SKs may be implemented.


## Final scope rule

These eight contracts are the explicitly supplied contracts for the nine
selected use cases. Additional supporting operations may be implemented only
when a detailed scenario for SK1, SK2, SK3, SK5, SK6, SK7, SK8, SK9 or SK22
requires them. Do not implement unrelated operations from the full catalog.
