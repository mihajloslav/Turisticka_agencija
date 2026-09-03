/*
SQLyog Community v13.3.1 (64 bit)
MySQL - 12.3.3-MariaDB-ubu2404 : Database - turisticka_agencija
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`turisticka_agencija` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci */;

USE `turisticka_agencija`;

/*Table structure for table `Agent` */

DROP TABLE IF EXISTS `Agent`;

CREATE TABLE `Agent` (
  `idAgent` bigint(20) NOT NULL AUTO_INCREMENT,
  `ime` varchar(100) NOT NULL,
  `prezime` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telefon` varchar(30) NOT NULL,
  `korisnickoIme` varchar(100) NOT NULL,
  `sifra` varchar(255) NOT NULL,
  PRIMARY KEY (`idAgent`),
  UNIQUE KEY `uq_Agent_korisnickoIme` (`korisnickoIme`),
  UNIQUE KEY `uq_Agent_telefon` (`telefon`),
  CONSTRAINT `chk_Agent_email` CHECK (`email` like '%@%.%'),
  CONSTRAINT `chk_Agent_telefon` CHECK (`telefon` regexp '^\\+[0-9]{12}$'),
  CONSTRAINT `chk_Agent_sifra` CHECK (char_length(`sifra`) > 6)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

/*Data for the table `Agent` */

insert  into `Agent`(`idAgent`,`ime`,`prezime`,`email`,`telefon`,`korisnickoIme`,`sifra`) values 
(1,'Марко','Марковић','marko.markovic@agencija.rs','+381600000001','mmarkovic','sifra123'),
(2,'Ана','Анић','ana.anic@agencija.rs','+381600000002','aanic','sifra123'),
(3,'Петар','Петровић','petar.petrovic@agencija.rs','+381600000003','ppetrovic','sifra123'),
(4,'Јована','Јовановић','jovana.jovanovic@agencija.rs','+381600000004','jjovanovic','sifra123'),
(5,'Никола','Николић','nikola.nikolic@agencija.rs','+381600000005','nnikolic','sifra123'),
(6,'Милица','Милић','milica.milic@agencija.rs','+381600000006','mmilic','sifra123'),
(7,'Стефан','Стефановић','stefan.stefanovic@agencija.rs','+381600000007','sstefanovic','sifra123'),
(8,'Ивана','Ивановић','ivana.ivanovic@agencija.rs','+381600000008','iivanovic','sifra123'),
(9,'Александар','Алексић','aleksandar.aleksic@agencija.rs','+381600000009','aaleksic','sifra123'),
(10,'Милан','Милановић','milan.milanovic@agencija.rs','+381600000010','mmilanovic','sifra123');

/*Table structure for table `Aranzman` */

DROP TABLE IF EXISTS `Aranzman`;

CREATE TABLE `Aranzman` (
  `idAranzman` bigint(20) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) NOT NULL,
  `opis` varchar(2000) DEFAULT NULL,
  `tipAranzmana` varchar(100) NOT NULL,
  `cenaPoOsobi` double NOT NULL,
  PRIMARY KEY (`idAranzman`),
  CONSTRAINT `chk_Aranzman_cena` CHECK (`cenaPoOsobi` > 0)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

/*Data for the table `Aranzman` */

insert  into `Aranzman`(`idAranzman`,`naziv`,`opis`,`tipAranzmana`,`cenaPoOsobi`) values 
(1,'Летовање Халкидики','Превоз, смештај на бази полупансиона и трансфер.','Летовање',120),
(2,'Зимовање Копаоник','Ски пакет са смештајем у апартману и ски пасом.','Зимовање',180),
(3,'Екскурзија Париз','Петодневна екскурзија са разгледањем града.','Екскурзија',250),
(4,'Летовање Крф','Смештај у хотелу са базеном, све укључено.','Летовање',200),
(5,'Викенд Будимпешта','Кратак одмор са разгледањем града.','Викенд пакет',90),
(6,'Крстарење Медитеран','Седмодневно крстарење бродом по Медитерану.','Крстарење',600),
(7,'Зимовање Јахорина','Смештај и ски карте за Јахорину.','Зимовање',150),
(8,'Летовање Закинтос','Смештај на плажи са доручком.','Летовање',220),
(9,'Екскурзија Рим','Петодневни обилазак Рима и околине.','Екскурзија',270),
(10,'Бањски одмор Врњачка Бања','Велнес програм и смештај у бањи.','Бањски одмор',80);

/*Table structure for table `Mesto` */

DROP TABLE IF EXISTS `Mesto`;

CREATE TABLE `Mesto` (
  `idMesto` bigint(20) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) NOT NULL,
  `pttBroj` varchar(5) NOT NULL,
  `pozivniBroj` varchar(10) NOT NULL,
  `drzava` varchar(100) NOT NULL,
  PRIMARY KEY (`idMesto`),
  UNIQUE KEY `uq_Mesto_naziv` (`naziv`),
  UNIQUE KEY `uq_Mesto_pttBroj` (`pttBroj`),
  UNIQUE KEY `uq_Mesto_pozivniBroj` (`pozivniBroj`),
  CONSTRAINT `chk_Mesto_pttBroj` CHECK (char_length(`pttBroj`) = 5)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

/*Data for the table `Mesto` */

insert  into `Mesto`(`idMesto`,`naziv`,`pttBroj`,`pozivniBroj`,`drzava`) values 
(1,'Београд','11000','011','Србија'),
(2,'Нови Сад','21000','021','Србија'),
(3,'Ниш','18000','018','Србија'),
(4,'Крагујевац','34000','034','Србија'),
(5,'Суботица','24000','024','Србија'),
(6,'Зрењанин','23000','023','Србија'),
(7,'Панчево','26000','013','Србија'),
(8,'Чачак','32000','032','Србија'),
(9,'Краљево','36000','036','Србија'),
(10,'Лесковац','16000','016','Србија');

/*Table structure for table `Putnik` */

DROP TABLE IF EXISTS `Putnik`;

CREATE TABLE `Putnik` (
  `idPutnik` bigint(20) NOT NULL AUTO_INCREMENT,
  `ime` varchar(100) NOT NULL,
  `prezime` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telefon` varchar(30) NOT NULL,
  `jmbg` varchar(13) NOT NULL,
  `brojPasosa` varchar(50) NOT NULL,
  `idMesto` bigint(20) NOT NULL,
  `datumRodjenja` date NOT NULL,
  PRIMARY KEY (`idPutnik`),
  UNIQUE KEY `uq_Putnik_jmbg` (`jmbg`),
  UNIQUE KEY `uq_Putnik_telefon` (`telefon`),
  KEY `fk_Putnik_Mesto` (`idMesto`),
  CONSTRAINT `fk_Putnik_Mesto` FOREIGN KEY (`idMesto`) REFERENCES `Mesto` (`idMesto`) ON UPDATE CASCADE,
  CONSTRAINT `chk_Putnik_email` CHECK (`email` like '%@%'),
  CONSTRAINT `chk_Putnik_jmbg` CHECK (char_length(`jmbg`) = 13),
  CONSTRAINT `chk_Putnik_telefon` CHECK (`telefon` regexp '^\\+[0-9]{12}$')
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

/*Data for the table `Putnik` */

insert  into `Putnik`(`idPutnik`,`ime`,`prezime`,`email`,`telefon`,`jmbg`,`brojPasosa`,`idMesto`,`datumRodjenja`) values 
(1,'Јелена','Јелић','jelena.jelic@example.com','+381611111111','0101990710012','P1234567',1,'1990-01-01'),
(2,'Драган','Драгић','dragan.dragic@example.com','+381612222222','0202985710023','P1234568',2,'1985-02-02'),
(3,'Снежана','Симић','snezana.simic@example.com','+381613333333','1503988710034','P1234569',3,'1988-03-15'),
(4,'Владимир','Васић','vladimir.vasic@example.com','+381614444444','2207992710045','P1234570',4,'1992-07-22'),
(5,'Тамара','Тасић','tamara.tasic@example.com','+381615555555','0511993710056','P1234571',5,'1993-11-05'),
(6,'Бојан','Благојевић','bojan.blagojevic@example.com','+381616666666','1809994710067','P1234572',6,'1994-09-18'),
(7,'Марина','Марковић','marina.markovic@example.com','+381617777777','2312995710078','P1234573',7,'1995-12-23'),
(8,'Игор','Илић','igor.ilic@example.com','+381618888888','0904996710089','P1234574',8,'1996-04-09'),
(9,'Сања','Савић','sanja.savic@example.com','+381619999999','1706997710090','P1234575',9,'1997-06-17'),
(10,'Ђорђе','Ђорђевић','djordje.djordjevic@example.com','+381611010101','2801998710001','P1234576',10,'1998-01-28');

/*Table structure for table `Region` */

DROP TABLE IF EXISTS `Region`;

CREATE TABLE `Region` (
  `idRegion` bigint(20) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) NOT NULL,
  `oznaka` varchar(6) NOT NULL,
  `kontinent` varchar(100) NOT NULL,
  `opis` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`idRegion`),
  UNIQUE KEY `uq_Region_oznaka` (`oznaka`),
  CONSTRAINT `chk_Region_oznaka` CHECK (char_length(`oznaka`) <= 6)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

/*Data for the table `Region` */

insert  into `Region`(`idRegion`,`naziv`,`oznaka`,`kontinent`,`opis`) values 
(1,'Западна Европа','ZEU','Европа','Регион западне Европе.'),
(2,'Јужна Европа','JEU','Европа','Регион јужне Европе.'),
(3,'Северна Европа','SEU','Европа','Регион северне Европе.'),
(4,'Источна Европа','IEU','Европа','Регион источне Европе.'),
(5,'Северна Африка','SAF','Африка','Регион северне Африке.'),
(6,'Блиски исток','BI','Азија','Регион блиског истока.'),
(7,'Југоисточна Азија','JIA','Азија','Регион југоисточне Азије.'),
(8,'Северна Америка','SAM','Америка','Регион северне Америке.'),
(9,'Јужна Америка','JAM','Америка','Регион јужне Америке.'),
(10,'Океанија','OKE','Океанија','Регион Океаније.');

/*Table structure for table `Rezervacija` */

DROP TABLE IF EXISTS `Rezervacija`;

CREATE TABLE `Rezervacija` (
  `idRezervacija` bigint(20) NOT NULL AUTO_INCREMENT,
  `datumKreiranja` date NOT NULL,
  `ukupanIznos` double NOT NULL,
  `statusPlacanja` varchar(50) NOT NULL,
  `napomena` varchar(2000) DEFAULT NULL,
  `idAgent` bigint(20) NOT NULL,
  `idPutnik` bigint(20) NOT NULL,
  PRIMARY KEY (`idRezervacija`),
  KEY `fk_Rezervacija_Agent` (`idAgent`),
  KEY `fk_Rezervacija_Putnik` (`idPutnik`),
  CONSTRAINT `fk_Rezervacija_Agent` FOREIGN KEY (`idAgent`) REFERENCES `Agent` (`idAgent`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Rezervacija_Putnik` FOREIGN KEY (`idPutnik`) REFERENCES `Putnik` (`idPutnik`) ON UPDATE CASCADE,
  CONSTRAINT `chk_Rezervacija_ukupanIznos` CHECK (`ukupanIznos` > 0),
  CONSTRAINT `chk_Rezervacija_statusPlacanja` CHECK (`statusPlacanja` in ('Плаћено','На чекању'))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

/*Data for the table `Rezervacija` */

insert  into `Rezervacija`(`idRezervacija`,`datumKreiranja`,`ukupanIznos`,`statusPlacanja`,`napomena`,`idAgent`,`idPutnik`) values 
(1,'2026-06-01',240,'Плаћено',NULL,1,1),
(2,'2026-06-05',648,'Плаћено',NULL,2,2),
(3,'2026-06-10',500,'На чекању','Чека се потврда уплате.',3,3),
(4,'2026-06-15',540,'Плаћено',NULL,4,4),
(5,'2026-06-20',90,'Плаћено',NULL,5,5),
(6,'2026-06-25',1200,'На чекању','Клијент чека визу.',6,6),
(7,'2026-06-30',675,'Плаћено',NULL,7,7),
(8,'2026-07-05',440,'Плаћено',NULL,8,8),
(9,'2026-07-10',828,'Плаћено','Два путовања у једној резервацији.',9,9),
(10,'2026-07-15',414,'На чекању','Два путовања у једној резервацији.',10,10);

/*Table structure for table `StavkaRezervacije` */

DROP TABLE IF EXISTS `StavkaRezervacije`;

CREATE TABLE `StavkaRezervacije` (
  `idRezervacija` bigint(20) NOT NULL,
  `rb` int(11) NOT NULL,
  `brojOsoba` int(11) NOT NULL,
  `datumPolaska` date NOT NULL,
  `datumDolaska` date NOT NULL,
  `popust` double NOT NULL,
  `cena` double NOT NULL,
  `idAranzman` bigint(20) NOT NULL,
  PRIMARY KEY (`idRezervacija`,`rb`),
  KEY `fk_Stavka_Aranzman` (`idAranzman`),
  CONSTRAINT `fk_Stavka_Aranzman` FOREIGN KEY (`idAranzman`) REFERENCES `Aranzman` (`idAranzman`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Stavka_Rezervacija` FOREIGN KEY (`idRezervacija`) REFERENCES `Rezervacija` (`idRezervacija`) ON UPDATE CASCADE,
  CONSTRAINT `chk_Stavka_brojOsoba` CHECK (`brojOsoba` > 0),
  CONSTRAINT `chk_Stavka_datumi` CHECK (`datumDolaska` > `datumPolaska`),
  CONSTRAINT `chk_Stavka_popust` CHECK (`popust` >= 0.0 and `popust` <= 1.0),
  CONSTRAINT `chk_Stavka_cena` CHECK (`cena` > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

/*Data for the table `StavkaRezervacije` */

insert  into `StavkaRezervacije`(`idRezervacija`,`rb`,`brojOsoba`,`datumPolaska`,`datumDolaska`,`popust`,`cena`,`idAranzman`) values 
(1,1,2,'2026-07-01','2026-07-10',0,240,1),
(2,1,4,'2026-07-05','2026-07-12',0.1,648,2),
(3,1,2,'2026-08-01','2026-08-08',0,500,3),
(4,1,3,'2026-08-10','2026-08-17',0.1,540,4),
(5,1,1,'2026-09-01','2026-09-04',0,90,5),
(6,1,2,'2026-09-15','2026-09-22',0,1200,6),
(7,1,5,'2026-10-01','2026-10-10',0.1,675,7),
(8,1,2,'2026-10-15','2026-10-20',0,440,8),
(9,1,2,'2026-11-01','2026-11-08',0,540,9),
(9,2,4,'2026-11-10','2026-11-14',0.1,288,10),
(10,1,3,'2026-12-01','2026-12-05',0.1,324,1),
(10,2,1,'2026-12-10','2026-12-12',0,90,5);

/*Table structure for table `Zaduzenje` */

DROP TABLE IF EXISTS `Zaduzenje`;

CREATE TABLE `Zaduzenje` (
  `idAgent` bigint(20) NOT NULL,
  `idRegion` bigint(20) NOT NULL,
  `datumOd` date NOT NULL,
  `datumDo` date DEFAULT NULL,
  `mesecnaKvota` int(11) NOT NULL,
  `napomena` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`idAgent`,`idRegion`,`datumOd`),
  KEY `fk_Zaduzenje_Region` (`idRegion`),
  CONSTRAINT `fk_Zaduzenje_Agent` FOREIGN KEY (`idAgent`) REFERENCES `Agent` (`idAgent`) ON UPDATE CASCADE,
  CONSTRAINT `fk_Zaduzenje_Region` FOREIGN KEY (`idRegion`) REFERENCES `Region` (`idRegion`) ON UPDATE CASCADE,
  CONSTRAINT `chk_Zaduzenje_datumi` CHECK (`datumDo` is null or `datumDo` > `datumOd`),
  CONSTRAINT `chk_Zaduzenje_kvota` CHECK (`mesecnaKvota` > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

/*Data for the table `Zaduzenje` */

insert  into `Zaduzenje`(`idAgent`,`idRegion`,`datumOd`,`datumDo`,`mesecnaKvota`,`napomena`) values 
(1,1,'2025-01-01',NULL,10,'Задужен за западну Европу.'),
(2,2,'2025-01-01','2026-06-30',8,NULL),
(3,3,'2025-02-01',NULL,12,'Задужен за северну Европу.'),
(4,4,'2025-02-15',NULL,9,NULL),
(5,5,'2025-03-01','2026-12-31',7,'Привремено задужење.'),
(6,6,'2025-03-15',NULL,15,NULL),
(7,7,'2025-04-01',NULL,6,'Задужен за југоисточну Азију.'),
(8,8,'2025-04-15',NULL,11,NULL),
(9,9,'2025-05-01','2026-11-30',5,NULL),
(10,10,'2025-05-15',NULL,13,'Задужен за Океанију.');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
