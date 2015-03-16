/*
SQLyog Community v12.08 (64 bit)
MySQL - 5.5.25 : Database - contacts_tatarintseva
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`contacts_tatarintseva` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `contacts_tatarintseva`;

/*Table structure for table `adress` */

DROP TABLE IF EXISTS `adress`;

CREATE TABLE `adress` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `country` varchar(20) DEFAULT NULL,
  `town` varchar(20) DEFAULT NULL,
  `street` varchar(20) DEFAULT NULL,
  `house` int(10) unsigned DEFAULT NULL,
  `place` int(10) unsigned DEFAULT NULL,
  `post_index` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

/*Data for the table `adress` */

insert  into `adress`(`id`,`country`,`town`,`street`,`house`,`place`,`post_index`) values (56,'Belarus','Minsk','Kirova',NULL,NULL,220030),(57,'','','',NULL,NULL,NULL),(58,'','Moscow','',NULL,NULL,NULL),(59,'France','Paris','',NULL,NULL,NULL),(60,'null','','',NULL,NULL,NULL),(61,'','St.','',NULL,NULL,NULL),(62,'','','',NULL,NULL,NULL),(63,'','','',NULL,NULL,NULL),(64,'','','',NULL,NULL,NULL),(65,'','','',NULL,NULL,NULL),(66,'','','',NULL,NULL,NULL),(67,'','','',NULL,NULL,NULL),(68,'','','',NULL,NULL,NULL),(69,'','','',NULL,NULL,NULL),(70,'','','',NULL,NULL,NULL),(71,'','','',NULL,NULL,NULL),(72,'','','',NULL,NULL,NULL),(73,'','','',NULL,NULL,NULL),(74,'','Минск','Купалы 11-99',NULL,NULL,NULL),(75,'','','',NULL,NULL,NULL),(77,'','','',NULL,NULL,NULL),(78,'рб','Минск','Купалы',11,99,220030),(79,'','','',NULL,NULL,NULL),(80,'','','',NULL,NULL,NULL),(81,'','','',NULL,NULL,NULL),(82,'','','',NULL,NULL,NULL),(83,'','','',NULL,NULL,NULL),(84,'','','',NULL,NULL,NULL),(85,'','','',NULL,NULL,NULL),(86,'','','',NULL,NULL,NULL),(87,'','','',NULL,NULL,NULL),(88,'','','',NULL,NULL,NULL),(89,'','','',NULL,NULL,NULL),(90,'','','',NULL,NULL,NULL),(91,'','','',NULL,NULL,NULL);

/*Table structure for table `attachment` */

DROP TABLE IF EXISTS `attachment`;

CREATE TABLE `attachment` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) DEFAULT NULL,
  `DATE` timestamp NULL DEFAULT NULL,
  `COMMENT` varchar(20) DEFAULT NULL,
  `CONTACT_ID` int(11) DEFAULT NULL,
  `PATH` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `attachment` */

insert  into `attachment`(`ID`,`NAME`,`DATE`,`COMMENT`,`CONTACT_ID`,`PATH`) values (1,'12','2015-03-10 00:04:48','',17,'D:\\itechart\\project\\ContactList\\target\\ContactList\\attachments\\2015-03-10288052\\bb.TXT'),(2,'Name','2015-03-12 05:53:07','Comment',50,'C:\\Temp\\attachments-2015-03-1221187651\\MyMain.txt'),(3,'Attach#',NULL,'file#',49,NULL),(5,'11',NULL,'222',57,NULL),(6,'mp3',NULL,'Поставить на звонок',51,NULL),(7,'12',NULL,'345',56,NULL),(8,'qwe',NULL,'www',51,NULL),(9,'qw',NULL,'qqq',51,NULL),(10,NULL,'2015-03-16 10:18:11',NULL,64,NULL);

/*Table structure for table `contact` */

DROP TABLE IF EXISTS `contact`;

CREATE TABLE `contact` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) NOT NULL,
  `SURNAME` varchar(30) NOT NULL,
  `FATHERNAME` varchar(30) DEFAULT NULL,
  `BIRTHDAY` date DEFAULT NULL,
  `JOB` varchar(20) DEFAULT NULL,
  `PHOTO` blob,
  `EMAIL` varchar(50) DEFAULT NULL,
  `adress_id` int(20) unsigned DEFAULT NULL,
  `GENDER` enum('MALE','FEMALE') DEFAULT NULL,
  `FAMILY` enum('SINGLE','MARRIED','DATING','DIVORCED','WIDOW','NOT_SPECIFIED') DEFAULT NULL,
  `CITIZENSHIP` varchar(30) DEFAULT NULL,
  `WEBSITE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID` (`ID`),
  KEY `contact_ibfk_1` (`adress_id`),
  CONSTRAINT `contact_ibfk_1` FOREIGN KEY (`adress_id`) REFERENCES `adress` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

/*Data for the table `contact` */

insert  into `contact`(`ID`,`NAME`,`SURNAME`,`FATHERNAME`,`BIRTHDAY`,`JOB`,`PHOTO`,`EMAIL`,`adress_id`,`GENDER`,`FAMILY`,`CITIZENSHIP`,`WEBSITE`) values (49,'Ivan','Ivanov',NULL,'1990-03-15','BSU','avatars/2015-03-1635157969/0UdvC-6yP48.jpg','email1@contacts.com',56,'MALE','MARRIED',NULL,NULL),(51,'Elena','Sidorova',NULL,NULL,NULL,'avatars/2015-03-1634676647/0_a539_d5bb9c67_L.jpg','elena@abc.com',58,'FEMALE','DIVORCED','russian',NULL),(54,'Vasily','Vasilev',NULL,NULL,NULL,NULL,NULL,61,'MALE','DATING',NULL,NULL),(56,'Ernst','Ernst',NULL,'1988-03-15','БГУ',NULL,'ernst@gmail.com',63,'MALE','NOT_SPECIFIED',NULL,NULL),(57,'Irina','Petrova',NULL,NULL,'McDonald\'s',NULL,'mashuga.08@mail.ru',64,'FEMALE','MARRIED','russian',NULL),(58,'Natalie','Kuznetsova',NULL,NULL,NULL,NULL,NULL,65,'FEMALE','NOT_SPECIFIED',NULL,NULL),(59,'Yahn','Yahnkovsky',NULL,NULL,'student',NULL,'yahn@mail.by',66,'MALE','SINGLE',NULL,NULL),(60,'Николай','Николаенко',NULL,NULL,NULL,NULL,NULL,67,'MALE','NOT_SPECIFIED',NULL,NULL),(61,'Елена','Татаринцева',NULL,'1971-02-02','дом',NULL,'tatarintseva@tut,by',78,'FEMALE','DIVORCED','дом',NULL),(64,'Hello','World',NULL,NULL,NULL,'avatars/2015-03-1637071781/001-women-marilyn-monroe-alfred-eisenstaedt-life-www.huy.com.ua.jpg',NULL,89,'MALE','NOT_SPECIFIED',NULL,NULL),(66,'Name','Snm',NULL,NULL,NULL,NULL,NULL,91,'MALE','NOT_SPECIFIED',NULL,NULL);

/*Table structure for table `phone` */

DROP TABLE IF EXISTS `phone`;

CREATE TABLE `phone` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `number` int(50) unsigned DEFAULT NULL,
  `type` enum('HOME','CELL') DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `contact_id` int(10) unsigned DEFAULT NULL,
  `country` int(10) unsigned DEFAULT NULL,
  `operator` int(10) unsigned DEFAULT NULL,
  KEY `id` (`id`),
  KEY `contact_id` (`contact_id`),
  CONSTRAINT `contact_id` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

/*Data for the table `phone` */

insert  into `phone`(`id`,`number`,`type`,`comment`,`contact_id`,`country`,`operator`) values (5,0,'CELL','',59,12,345),(37,NULL,'HOME','',49,111,NULL),(40,2345432,'HOME','',57,NULL,NULL),(43,11112,'HOME','',57,NULL,NULL),(47,34,'HOME','',64,1,222),(48,NULL,'HOME','',64,NULL,5555),(49,345,'HOME','',57,1,2),(50,888,'HOME','',56,12,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
