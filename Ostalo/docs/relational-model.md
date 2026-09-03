# Relational Model

The relational model is fixed to the following eight relations.

## 1. Agent

`Agent(idAgent, ime, prezime, email, telefon, korisnickoIme, sifra)`

## 2. Aranzman

`Aranzman(idAranzman, naziv, opis, tipAranzmana, cenaPoOsobi)`

## 3. Mesto

`Mesto(idMesto, naziv, pttBroj, pozivniBroj, drzava)`

## 4. Region

`Region(idRegion, naziv, oznaka, kontinent, opis)`

## 5. Putnik

`Putnik(idPutnik, ime, prezime, email, telefon, jmbg, brojPasosa, idMesto,
datumRodjenja)`

## 6. Rezervacija

`Rezervacija(idRezervacija, datumKreiranja, ukupanIznos, statusPlacanja,
napomena, idAgent, idPutnik)`

## 7. StavkaRezervacije

`StavkaRezervacije(idRezervacija, rb, brojOsoba, datumPolaska,
datumDolaska, popust, cena, idAranzman)`

## 8. Zaduzenje

`Zaduzenje(idAgent, idRegion, datumOd, datumDo, mesecnaKvota, napomena)`

## Important

Do not introduce a separate table for a relationship merely because the Java
implementation looks easier that way.

`Zaduzenje` is already the relational representation of the Agent-Region
associative relationship.

`StavkaRezervacije` uses `idRezervacija` and `rb` as its identifying attributes
according to the supplied model.

Foreign-key and cascade/restrict rules are specified in `constraints.md`.
