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
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;

/*Data for the table `adress` */

insert  into `adress`(`id`,`country`,`town`,`street`,`house`,`place`,`post_index`) values (56,'Belarus','Minsk','Kirova',NULL,NULL,220030),(57,'','','',NULL,NULL,NULL),(58,'','Moscow','',NULL,NULL,NULL),(59,'France','Paris',NULL,NULL,NULL,NULL),(60,NULL,NULL,NULL,NULL,NULL,NULL),(61,'','St.','',NULL,NULL,NULL),(62,'','','',NULL,NULL,NULL),(63,'','','',NULL,NULL,NULL),(64,'','','',NULL,NULL,NULL),(65,'','','',NULL,NULL,NULL),(66,'','','',NULL,NULL,NULL),(67,'','','',NULL,NULL,NULL),(68,'','','',NULL,NULL,NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `attachment` */

insert  into `attachment`(`ID`,`NAME`,`DATE`,`COMMENT`,`CONTACT_ID`,`PATH`) values (1,'12','2015-03-10 00:04:48','',17,'D:\\itechart\\project\\ContactList\\target\\ContactList\\attachments\\2015-03-10288052\\bb.TXT'),(2,'Name','2015-03-12 05:53:07','Comment',50,'C:\\Temp\\attachments-2015-03-1221187651\\MyMain.txt');

/*Table structure for table `contact` */

DROP TABLE IF EXISTS `contact`;

CREATE TABLE `contact` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) NOT NULL,
  `SURNAME` varchar(20) NOT NULL,
  `FATHERNAME` varchar(20) DEFAULT NULL,
  `BIRTHDAY` date DEFAULT NULL,
  `JOB` varchar(20) DEFAULT NULL,
  `PHOTO` blob,
  `EMAIL` varchar(20) DEFAULT NULL,
  `adress_id` int(10) unsigned DEFAULT NULL,
  `GENDER` enum('MALE','FEMALE') DEFAULT NULL,
  `FAMILY` enum('SINGLE','MARRIED','DATING','DIVORCED','WIDOW','NOT_SPECIFIED') DEFAULT NULL,
  `CITIZENSHIP` varchar(15) DEFAULT NULL,
  `WEBSITE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID` (`ID`),
  KEY `contact_ibfk_1` (`adress_id`),
  CONSTRAINT `contact_ibfk_1` FOREIGN KEY (`adress_id`) REFERENCES `adress` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

/*Data for the table `contact` */

insert  into `contact`(`ID`,`NAME`,`SURNAME`,`FATHERNAME`,`BIRTHDAY`,`JOB`,`PHOTO`,`EMAIL`,`adress_id`,`GENDER`,`FAMILY`,`CITIZENSHIP`,`WEBSITE`) values (49,'Ivan','Ivanov',NULL,'1990-01-12','BSU',NULL,'email1@contacts.com',56,'MALE','MARRIED',NULL,NULL),(50,'Petr','Petrov',NULL,'1990-01-10',NULL,NULL,NULL,57,'MALE','NOT_SPECIFIED',NULL,NULL),(51,'Elena','Sidorova',NULL,NULL,NULL,'avatars/2015-03-1333907869/x_54HmV7Czs.jpg','elena@abc.com',58,'FEMALE','DIVORCED',NULL,NULL),(52,'Pierre','Levacances',NULL,NULL,'IBM',NULL,'pierre@gmail.com',59,'MALE','','french',NULL),(53,'Serge','Levacances',NULL,NULL,'IBM',NULL,'pierre@gmail.com',60,'MALE','','french',NULL),(54,'Vasily','Vasilev',NULL,NULL,NULL,NULL,NULL,61,'MALE','DATING',NULL,NULL),(55,'Nicolai','Sasev',NULL,NULL,NULL,'avatars/2015-03-1332119021/incognito.gif',NULL,62,'MALE','NOT_SPECIFIED',NULL,NULL),(56,'Ernst','Ernst',NULL,'1988-04-10',NULL,'avatars/2015-03-1332683170/8yun5CXGn20.jpg',NULL,63,'MALE','NOT_SPECIFIED',NULL,NULL),(57,'Irina','Petrova',NULL,NULL,'McDonald\'s',NULL,'mashuga.08@mail.ru',64,'FEMALE','MARRIED','McDonald\'s',NULL),(58,'Natalie','Kuznetsova',NULL,NULL,NULL,NULL,NULL,65,'MALE','NOT_SPECIFIED',NULL,NULL),(59,'Yahn','Yahnkovsky',NULL,NULL,'student',NULL,'yahn@mail.by',66,'MALE','SINGLE',NULL,NULL),(60,'Николай','Николаенко',NULL,NULL,NULL,NULL,NULL,67,'MALE','NOT_SPECIFIED',NULL,NULL);

/*Table structure for table `phone` */

DROP TABLE IF EXISTS `phone`;

CREATE TABLE `phone` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `number` varchar(50) DEFAULT NULL,
  `type` enum('HOME','CELL') DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `contact_id` int(10) unsigned DEFAULT NULL,
  `country` varchar(5) DEFAULT NULL,
  `operator` varchar(5) DEFAULT NULL,
  KEY `id` (`id`),
  KEY `contact_id` (`contact_id`),
  CONSTRAINT `contact_id` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `phone` */

insert  into `phone`(`id`,`number`,`type`,`comment`,`contact_id`,`country`,`operator`) values (1,'345','CELL','Comment',54,'1','2'),(2,'345','HOME','',54,'2',''),(3,'2342','HOME','Comment',54,'12',''),(4,'234','HOME','Comment',54,'1','234');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
