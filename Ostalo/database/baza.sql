-- Travel agency reservation system
-- REQUIRED DATABASE DELIVERABLE
-- DBMS: MySQL / MariaDB
--
-- Generated from docs/relational-model.md and docs/constraints-full.md.
-- Region uses the 5-attribute shape confirmed against the supplied
-- conceptual UML model (idRegion, naziv, oznaka, kontinent, opis).
--
-- All entity identifiers (idAgent, idAranzman, idMesto, idRegion, idPutnik,
-- idRezervacija) use BIGINT, matching the Java domain model's Long id type.
-- rb is part of StavkaRezervacije's composite identifying key
-- (idRezervacija, rb), but per the conceptual model rb : Integer, so it is
-- INT (no AUTO_INCREMENT), not BIGINT. brojOsoba and mesecnaKvota are
-- ordinary INT quantities, not identifiers, and stay INT.
--
-- FK rule used throughout this script (derived directly from the
-- INSERT/UPDATE/DELETE structural rules in docs/constraints-full.md, which
-- are uniform across every relationship: every parent row's INSERT/UPDATE
-- is never restricted by existing children -> ON UPDATE CASCADE; every
-- parent DELETE is blocked while children still reference it ->
-- ON DELETE RESTRICT):
--   every foreign key below is ON UPDATE CASCADE ON DELETE RESTRICT.
--
-- Derived/complex business rules that depend on multiple rows or other
-- tables (StavkaRezervacije.popust/cena formulas, Rezervacija.ukupanIznos =
-- SUM(StavkaRezervacije.cena)) are NOT encoded as SQL triggers — per
-- docs/implementation-rules.md they belong in the Server/SO layer, not the
-- database. Only row-local, single-table constraints are expressed here as
-- CHECK constraints.

/*SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS Zaduzenje;
DROP TABLE IF EXISTS StavkaRezervacije;
DROP TABLE IF EXISTS Rezervacija;
DROP TABLE IF EXISTS Putnik;
DROP TABLE IF EXISTS Region;
DROP TABLE IF EXISTS Mesto;
DROP TABLE IF EXISTS Aranzman;
DROP TABLE IF EXISTS Agent;

SET FOREIGN_KEY_CHECKS = 1;*/

DROP DATABASE IF EXISTS turisticka_agencija;
CREATE DATABASE IF NOT EXISTS turisticka_agencija;
USE turisticka_agencija;

-- ---------------------------------------------------------------------
-- 1. Agent
-- ---------------------------------------------------------------------
CREATE TABLE Agent (
    idAgent       BIGINT AUTO_INCREMENT PRIMARY KEY,
    ime           VARCHAR(100) NOT NULL,
    prezime       VARCHAR(100) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    telefon       VARCHAR(30)  NOT NULL,
    korisnickoIme VARCHAR(100) NOT NULL,
    sifra         VARCHAR(255) NOT NULL,
    CONSTRAINT uq_Agent_korisnickoIme UNIQUE (korisnickoIme),
    CONSTRAINT uq_Agent_telefon UNIQUE (telefon),
    CONSTRAINT chk_Agent_email CHECK (email LIKE '%@%.%'),
    CONSTRAINT chk_Agent_telefon CHECK (telefon REGEXP '^\\+[0-9]{12}$'),
    CONSTRAINT chk_Agent_sifra CHECK (CHAR_LENGTH(sifra) > 6)
);

-- ---------------------------------------------------------------------
-- 2. Aranzman
-- ---------------------------------------------------------------------
CREATE TABLE Aranzman (
    idAranzman    BIGINT AUTO_INCREMENT PRIMARY KEY,
    naziv         VARCHAR(255) NOT NULL,
    opis          VARCHAR(2000),
    tipAranzmana  VARCHAR(100) NOT NULL,
    cenaPoOsobi   DOUBLE NOT NULL,
    CONSTRAINT chk_Aranzman_cena CHECK (cenaPoOsobi > 0)
);

-- ---------------------------------------------------------------------
-- 3. Mesto
-- ---------------------------------------------------------------------
CREATE TABLE Mesto (
    idMesto      BIGINT AUTO_INCREMENT PRIMARY KEY,
    naziv        VARCHAR(255) NOT NULL,
    pttBroj      VARCHAR(5) NOT NULL,
    pozivniBroj  VARCHAR(10) NOT NULL,
    drzava       VARCHAR(100) NOT NULL,
    CONSTRAINT uq_Mesto_naziv UNIQUE (naziv),
    CONSTRAINT uq_Mesto_pttBroj UNIQUE (pttBroj),
    CONSTRAINT uq_Mesto_pozivniBroj UNIQUE (pozivniBroj),
    CONSTRAINT chk_Mesto_pttBroj CHECK (CHAR_LENGTH(pttBroj) = 5)
);

-- ---------------------------------------------------------------------
-- 4. Region (5 attributes: idRegion, naziv, oznaka, kontinent, opis)
-- ---------------------------------------------------------------------
CREATE TABLE Region (
    idRegion   BIGINT AUTO_INCREMENT PRIMARY KEY,
    naziv      VARCHAR(255) NOT NULL,
    oznaka     VARCHAR(6) NOT NULL,
    kontinent  VARCHAR(100) NOT NULL,
    opis       VARCHAR(2000),
    CONSTRAINT uq_Region_oznaka UNIQUE (oznaka),
    CONSTRAINT chk_Region_oznaka CHECK (CHAR_LENGTH(oznaka) <= 6)
);

-- ---------------------------------------------------------------------
-- 5. Putnik
-- ---------------------------------------------------------------------
CREATE TABLE Putnik (
    idPutnik       BIGINT AUTO_INCREMENT PRIMARY KEY,
    ime            VARCHAR(100) NOT NULL,
    prezime        VARCHAR(100) NOT NULL,
    email          VARCHAR(255) NOT NULL,
    telefon        VARCHAR(30)  NOT NULL,
    jmbg           VARCHAR(13)  NOT NULL,
    brojPasosa     VARCHAR(50)  NOT NULL,
    idMesto        BIGINT NOT NULL,
    datumRodjenja  DATE NOT NULL,
    CONSTRAINT uq_Putnik_jmbg UNIQUE (jmbg),
    CONSTRAINT uq_Putnik_telefon UNIQUE (telefon),
    CONSTRAINT chk_Putnik_email CHECK (email LIKE '%@%'),
    CONSTRAINT chk_Putnik_jmbg CHECK (CHAR_LENGTH(jmbg) = 13),
    CONSTRAINT chk_Putnik_telefon CHECK (telefon REGEXP '^\\+[0-9]{12}$'),
    CONSTRAINT fk_Putnik_Mesto FOREIGN KEY (idMesto) REFERENCES Mesto (idMesto)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- ---------------------------------------------------------------------
-- 6. Rezervacija
-- ---------------------------------------------------------------------
CREATE TABLE Rezervacija (
    idRezervacija   BIGINT AUTO_INCREMENT PRIMARY KEY,
    datumKreiranja  DATE NOT NULL,
    ukupanIznos     DOUBLE NOT NULL,
    statusPlacanja  VARCHAR(50) NOT NULL,
    napomena        VARCHAR(2000),
    idAgent         BIGINT NOT NULL,
    idPutnik        BIGINT NOT NULL,
    CONSTRAINT chk_Rezervacija_ukupanIznos CHECK (ukupanIznos > 0),
    CONSTRAINT chk_Rezervacija_statusPlacanja CHECK (statusPlacanja IN ('Плаћено', 'На чекању')),
    CONSTRAINT fk_Rezervacija_Agent FOREIGN KEY (idAgent) REFERENCES Agent (idAgent)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_Rezervacija_Putnik FOREIGN KEY (idPutnik) REFERENCES Putnik (idPutnik)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- ---------------------------------------------------------------------
-- 7. StavkaRezervacije
-- identifying attributes per docs/relational-model.md: (idRezervacija, rb)
-- ---------------------------------------------------------------------
CREATE TABLE StavkaRezervacije (
    idRezervacija  BIGINT NOT NULL,
    rb             INT NOT NULL,
    brojOsoba      INT NOT NULL,
    datumPolaska   DATE NOT NULL,
    datumDolaska   DATE NOT NULL,
    popust         DOUBLE NOT NULL,
    cena           DOUBLE NOT NULL,
    idAranzman     BIGINT NOT NULL,
    PRIMARY KEY (idRezervacija, rb),
    CONSTRAINT chk_Stavka_brojOsoba CHECK (brojOsoba > 0),
    CONSTRAINT chk_Stavka_datumi CHECK (datumDolaska > datumPolaska),
    CONSTRAINT chk_Stavka_popust CHECK (popust >= 0.0 AND popust <= 1.0),
    CONSTRAINT chk_Stavka_cena CHECK (cena > 0),
    CONSTRAINT fk_Stavka_Rezervacija FOREIGN KEY (idRezervacija) REFERENCES Rezervacija (idRezervacija)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_Stavka_Aranzman FOREIGN KEY (idAranzman) REFERENCES Aranzman (idAranzman)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- ---------------------------------------------------------------------
-- 8. Zaduzenje — associative class between Agent and Region.
-- Not part of the nine in-scope use cases (no CRUD/SO/GUI), but the table
-- is required so the relational model stays complete (CLAUDE.md mandates
-- exactly these 8 relations). Composite key (idAgent, idRegion, datumOd)
-- since one Agent can be reassigned to the same Region across different,
-- non-overlapping date ranges.
-- ---------------------------------------------------------------------
CREATE TABLE Zaduzenje (
    idAgent       BIGINT NOT NULL,
    idRegion      BIGINT NOT NULL,
    datumOd       DATE NOT NULL,
    datumDo       DATE,
    mesecnaKvota  INT NOT NULL,
    napomena      VARCHAR(2000),
    PRIMARY KEY (idAgent, idRegion, datumOd),
    CONSTRAINT chk_Zaduzenje_datumi CHECK (datumDo IS NULL OR datumDo > datumOd),
    CONSTRAINT chk_Zaduzenje_kvota CHECK (mesecnaKvota > 0),
    CONSTRAINT fk_Zaduzenje_Agent FOREIGN KEY (idAgent) REFERENCES Agent (idAgent)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_Zaduzenje_Region FOREIGN KEY (idRegion) REFERENCES Region (idRegion)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- ---------------------------------------------------------------------
-- Test / demo data.
--
-- Standalone entities (Agent, Aranzman, Mesto, Region, Putnik,
-- Rezervacija) are inserted with explicit sequential ids 1..10. This is
-- compatible with AUTO_INCREMENT (MySQL/MariaDB accept explicit values on
-- an AUTO_INCREMENT column and advance the counter past the highest value
-- inserted), and keeps every foreign-key reference below deterministic and
-- readable instead of depending on insert order.
--
-- StavkaRezervacije.cena/popust follow the documented formulas exactly:
--   popust = 0.1 if brojOsoba >= 3, otherwise 0
--   cena   = brojOsoba * Aranzman.cenaPoOsobi * (1 - popust)
-- Rezervacija.ukupanIznos is the sum of its StavkaRezervacije.cena values.
-- ---------------------------------------------------------------------

-- 1. Agent (10)
INSERT INTO Agent (idAgent, ime, prezime, email, telefon, korisnickoIme, sifra) VALUES
(1,  'Марко',      'Марковић',    'marko.markovic@agencija.rs',      '+381600000001', 'mmarkovic',   'sifra123'),
(2,  'Ана',        'Анић',        'ana.anic@agencija.rs',            '+381600000002', 'aanic',       'sifra123'),
(3,  'Петар',      'Петровић',    'petar.petrovic@agencija.rs',      '+381600000003', 'ppetrovic',   'sifra123'),
(4,  'Јована',     'Јовановић',   'jovana.jovanovic@agencija.rs',    '+381600000004', 'jjovanovic',  'sifra123'),
(5,  'Никола',     'Николић',     'nikola.nikolic@agencija.rs',      '+381600000005', 'nnikolic',    'sifra123'),
(6,  'Милица',     'Милић',       'milica.milic@agencija.rs',        '+381600000006', 'mmilic',      'sifra123'),
(7,  'Стефан',     'Стефановић',  'stefan.stefanovic@agencija.rs',   '+381600000007', 'sstefanovic', 'sifra123'),
(8,  'Ивана',      'Ивановић',    'ivana.ivanovic@agencija.rs',      '+381600000008', 'iivanovic',   'sifra123'),
(9,  'Александар', 'Алексић',     'aleksandar.aleksic@agencija.rs',  '+381600000009', 'aaleksic',    'sifra123'),
(10, 'Милан',      'Милановић',   'milan.milanovic@agencija.rs',     '+381600000010', 'mmilanovic',  'sifra123');

-- 2. Aranzman (10)
INSERT INTO Aranzman (idAranzman, naziv, opis, tipAranzmana, cenaPoOsobi) VALUES
(1,  'Летовање Халкидики',         'Превоз, смештај на бази полупансиона и трансфер.',   'Летовање',       120.00),
(2,  'Зимовање Копаоник',          'Ски пакет са смештајем у апартману и ски пасом.',    'Зимовање',       180.00),
(3,  'Екскурзија Париз',           'Петодневна екскурзија са разгледањем града.',        'Екскурзија',     250.00),
(4,  'Летовање Крф',               'Смештај у хотелу са базеном, све укључено.',         'Летовање',       200.00),
(5,  'Викенд Будимпешта',          'Кратак одмор са разгледањем града.',                 'Викенд пакет',    90.00),
(6,  'Крстарење Медитеран',        'Седмодневно крстарење бродом по Медитерану.',        'Крстарење',      600.00),
(7,  'Зимовање Јахорина',          'Смештај и ски карте за Јахорину.',                   'Зимовање',       150.00),
(8,  'Летовање Закинтос',          'Смештај на плажи са доручком.',                      'Летовање',       220.00),
(9,  'Екскурзија Рим',             'Петодневни обилазак Рима и околине.',                'Екскурзија',     270.00),
(10, 'Бањски одмор Врњачка Бања',  'Велнес програм и смештај у бањи.',                   'Бањски одмор',    80.00);

-- 3. Mesto (10)
INSERT INTO Mesto (idMesto, naziv, pttBroj, pozivniBroj, drzava) VALUES
(1,  'Београд',     '11000', '011', 'Србија'),
(2,  'Нови Сад',    '21000', '021', 'Србија'),
(3,  'Ниш',         '18000', '018', 'Србија'),
(4,  'Крагујевац',  '34000', '034', 'Србија'),
(5,  'Суботица',    '24000', '024', 'Србија'),
(6,  'Зрењанин',    '23000', '023', 'Србија'),
(7,  'Панчево',     '26000', '013', 'Србија'),
(8,  'Чачак',       '32000', '032', 'Србија'),
(9,  'Краљево',     '36000', '036', 'Србија'),
(10, 'Лесковац',    '16000', '016', 'Србија');

-- 4. Region (10)
INSERT INTO Region (idRegion, naziv, oznaka, kontinent, opis) VALUES
(1,  'Западна Европа',     'ZEU', 'Европа',    'Регион западне Европе.'),
(2,  'Јужна Европа',       'JEU', 'Европа',    'Регион јужне Европе.'),
(3,  'Северна Европа',     'SEU', 'Европа',    'Регион северне Европе.'),
(4,  'Источна Европа',     'IEU', 'Европа',    'Регион источне Европе.'),
(5,  'Северна Африка',     'SAF', 'Африка',    'Регион северне Африке.'),
(6,  'Блиски исток',       'BI',  'Азија',     'Регион блиског истока.'),
(7,  'Југоисточна Азија',  'JIA', 'Азија',     'Регион југоисточне Азије.'),
(8,  'Северна Америка',    'SAM', 'Америка',   'Регион северне Америке.'),
(9,  'Јужна Америка',      'JAM', 'Америка',   'Регион јужне Америке.'),
(10, 'Океанија',           'OKE', 'Океанија',  'Регион Океаније.');

-- 5. Putnik (10) — each references an existing Mesto (1..10).
-- telefon values follow ^\+[0-9]{12}$ (+381 followed by the 9-digit local
-- number) and are unique within Putnik, independently of Agent.telefon.
-- datumRodjenja matches the birth date encoded in each row's own JMBG
-- (DDMMGGG... — e.g. jmbg '0101990710012' encodes 01.01.1990).
INSERT INTO Putnik (idPutnik, ime, prezime, email, telefon, jmbg, brojPasosa, idMesto, datumRodjenja) VALUES
(1,  'Јелена',    'Јелић',       'jelena.jelic@example.com',       '+381611111111', '0101990710012', 'P1234567', 1,  '1990-01-01'),
(2,  'Драган',    'Драгић',      'dragan.dragic@example.com',      '+381612222222', '0202985710023', 'P1234568', 2,  '1985-02-02'),
(3,  'Снежана',   'Симић',       'snezana.simic@example.com',      '+381613333333', '1503988710034', 'P1234569', 3,  '1988-03-15'),
(4,  'Владимир',  'Васић',       'vladimir.vasic@example.com',     '+381614444444', '2207992710045', 'P1234570', 4,  '1992-07-22'),
(5,  'Тамара',    'Тасић',       'tamara.tasic@example.com',       '+381615555555', '0511993710056', 'P1234571', 5,  '1993-11-05'),
(6,  'Бојан',     'Благојевић',  'bojan.blagojevic@example.com',   '+381616666666', '1809994710067', 'P1234572', 6,  '1994-09-18'),
(7,  'Марина',    'Марковић',    'marina.markovic@example.com',    '+381617777777', '2312995710078', 'P1234573', 7,  '1995-12-23'),
(8,  'Игор',      'Илић',        'igor.ilic@example.com',          '+381618888888', '0904996710089', 'P1234574', 8,  '1996-04-09'),
(9,  'Сања',      'Савић',       'sanja.savic@example.com',        '+381619999999', '1706997710090', 'P1234575', 9,  '1997-06-17'),
(10, 'Ђорђе',     'Ђорђевић',    'djordje.djordjevic@example.com', '+381611010101', '2801998710001', 'P1234576', 10, '1998-01-28');

-- 6. Rezervacija (10) — each references an existing Agent and Putnik.
-- ukupanIznos values below equal the sum of the matching StavkaRezervacije
-- rows inserted further down (verified by formula, see comment above).
INSERT INTO Rezervacija (idRezervacija, datumKreiranja, ukupanIznos, statusPlacanja, napomena, idAgent, idPutnik) VALUES
(1,  '2026-06-01', 240.00,  'Плаћено',     NULL,                              1,  1),
(2,  '2026-06-05', 648.00,  'Плаћено',     NULL,                              2,  2),
(3,  '2026-06-10', 500.00,  'На чекању',   'Чека се потврда уплате.',         3,  3),
(4,  '2026-06-15', 540.00,  'Плаћено',     NULL,                              4,  4),
(5,  '2026-06-20', 90.00,   'Плаћено',     NULL,                              5,  5),
(6,  '2026-06-25', 1200.00, 'На чекању',   'Клијент чека визу.',              6,  6),
(7,  '2026-06-30', 675.00,  'Плаћено',     NULL,                              7,  7),
(8,  '2026-07-05', 440.00,  'Плаћено',     NULL,                              8,  8),
(9,  '2026-07-10', 828.00,  'Плаћено',     'Два путовања у једној резервацији.', 9,  9),
(10, '2026-07-15', 414.00,  'На чекању',   'Два путовања у једној резервацији.', 10, 10);

-- 7. StavkaRezervacije (12) — each references an existing Rezervacija and
-- Aranzman. Reservations 9 and 10 have two items each (rb 1 and 2), all
-- others have one (rb 1), so no (idRezervacija, rb) pair repeats.
-- popust/cena computed per the documented formula:
--   R1:  2 os. x 120.00, <3  -> popust 0.0, cena 240.00
--   R2:  4 os. x 180.00, >=3 -> popust 0.1, cena 648.00
--   R3:  2 os. x 250.00, <3  -> popust 0.0, cena 500.00
--   R4:  3 os. x 200.00, >=3 -> popust 0.1, cena 540.00
--   R5:  1 os. x 90.00,  <3  -> popust 0.0, cena 90.00
--   R6:  2 os. x 600.00, <3  -> popust 0.0, cena 1200.00
--   R7:  5 os. x 150.00, >=3 -> popust 0.1, cena 675.00
--   R8:  2 os. x 220.00, <3  -> popust 0.0, cena 440.00
--   R9  rb1: 2 os. x 270.00, <3  -> popust 0.0, cena 540.00
--   R9  rb2: 4 os. x 80.00,  >=3 -> popust 0.1, cena 288.00   (sum = 828.00)
--   R10 rb1: 3 os. x 120.00, >=3 -> popust 0.1, cena 324.00
--   R10 rb2: 1 os. x 90.00,  <3  -> popust 0.0, cena 90.00    (sum = 414.00)
INSERT INTO StavkaRezervacije (idRezervacija, rb, brojOsoba, datumPolaska, datumDolaska, popust, cena, idAranzman) VALUES
(1,  1, 2, '2026-07-01', '2026-07-10', 0.0, 240.00,  1),
(2,  1, 4, '2026-07-05', '2026-07-12', 0.1, 648.00,  2),
(3,  1, 2, '2026-08-01', '2026-08-08', 0.0, 500.00,  3),
(4,  1, 3, '2026-08-10', '2026-08-17', 0.1, 540.00,  4),
(5,  1, 1, '2026-09-01', '2026-09-04', 0.0, 90.00,   5),
(6,  1, 2, '2026-09-15', '2026-09-22', 0.0, 1200.00, 6),
(7,  1, 5, '2026-10-01', '2026-10-10', 0.1, 675.00,  7),
(8,  1, 2, '2026-10-15', '2026-10-20', 0.0, 440.00,  8),
(9,  1, 2, '2026-11-01', '2026-11-08', 0.0, 540.00,  9),
(9,  2, 4, '2026-11-10', '2026-11-14', 0.1, 288.00,  10),
(10, 1, 3, '2026-12-01', '2026-12-05', 0.1, 324.00,  1),
(10, 2, 1, '2026-12-10', '2026-12-12', 0.0, 90.00,   5);

-- 8. Zaduzenje (10) — each references an existing Agent and Region; one
-- assignment per Agent keeps (idAgent, idRegion, datumOd) trivially unique.
INSERT INTO Zaduzenje (idAgent, idRegion, datumOd, datumDo, mesecnaKvota, napomena) VALUES
(1,  1,  '2025-01-01', NULL,         10, 'Задужен за западну Европу.'),
(2,  2,  '2025-01-01', '2026-06-30', 8,  NULL),
(3,  3,  '2025-02-01', NULL,         12, 'Задужен за северну Европу.'),
(4,  4,  '2025-02-15', NULL,         9,  NULL),
(5,  5,  '2025-03-01', '2026-12-31', 7,  'Привремено задужење.'),
(6,  6,  '2025-03-15', NULL,         15, NULL),
(7,  7,  '2025-04-01', NULL,         6,  'Задужен за југоисточну Азију.'),
(8,  8,  '2025-04-15', NULL,         11, NULL),
(9,  9,  '2025-05-01', '2026-11-30', 5,  NULL),
(10, 10, '2025-05-15', NULL,         13, 'Задужен за Океанију.');
