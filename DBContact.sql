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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `adress` */

insert  into `adress`(`id`,`country`,`town`,`street`,`house`,`place`,`post_index`) values (1,'Bel1','Gomel','Kirova',11,22,NULL),(2,NULL,NULL,NULL,NULL,NULL,NULL),(3,'Bel','Mnsk','Kirova',12,11,NULL),(4,'','','',NULL,NULL,NULL),(5,'','','',NULL,NULL,NULL),(6,'','','',NULL,NULL,NULL),(7,'','','',NULL,NULL,NULL),(8,'','','',NULL,NULL,NULL),(9,'','','',NULL,NULL,NULL),(10,'','','',NULL,NULL,NULL),(11,'','','',NULL,NULL,NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `attachment` */

insert  into `attachment`(`ID`,`NAME`,`DATE`,`COMMENT`,`CONTACT_ID`,`PATH`) values (1,'12','2015-03-10 00:04:48','',17,'D:\\itechart\\project\\ContactList\\target\\ContactList\\attachments\\2015-03-10288052\\bb.TXT');

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
  `FAMILY` enum('SINGLE','MARRIED','DATING','DIVORCED','WIDOW') DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID` (`ID`),
  KEY `contact_ibfk_1` (`adress_id`),
  CONSTRAINT `contact_ibfk_1` FOREIGN KEY (`adress_id`) REFERENCES `adress` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

/*Data for the table `contact` */

insert  into `contact`(`ID`,`NAME`,`SURNAME`,`FATHERNAME`,`BIRTHDAY`,`JOB`,`PHOTO`,`EMAIL`,`adress_id`,`GENDER`,`FAMILY`) values (10,'Petya','Petrov','',NULL,'',NULL,'',1,NULL,NULL),(14,'NotNotPetr','NotPetrov','',NULL,'',NULL,'',NULL,NULL,NULL),(17,'aaa','qqq-q','','1010-09-12','','avatars/2015-03-0981978066/x_54HmV7Czs.jpg','',NULL,NULL,NULL),(21,'Sergey','Sergeew','','1991-03-12','',NULL,'',NULL,NULL,NULL),(22,'Petrov','Petya1','',NULL,'',NULL,'',NULL,NULL,NULL),(23,'NewContact','Contact','New',NULL,'',NULL,'',NULL,NULL,NULL),(24,'trert','qwewq','',NULL,'',NULL,'',NULL,NULL,NULL),(25,'CNewC','CCNewCC','','1991-02-12','',NULL,'',11,NULL,NULL),(26,'Name','Surnam','',NULL,'','','123',NULL,NULL,NULL),(27,'qweweq','asdsa','',NULL,'',NULL,'',NULL,NULL,NULL),(28,'Qweewq','asdad','',NULL,'',NULL,'sdfasf',NULL,NULL,NULL),(29,'ertre',' ddvd ','',NULL,'',NULL,'',NULL,NULL,NULL),(30,'qweq','sfdsf','',NULL,'',NULL,' d ad',NULL,NULL,NULL),(31,'qwewq','sdfds','',NULL,'',NULL,'',NULL,NULL,NULL),(33,'rtyuytr','ertytre','',NULL,'',NULL,'',NULL,NULL,NULL),(34,'NewNewNEW','0703','','1234-05-10','',NULL,'qwe@qwe.ru',NULL,NULL,NULL),(35,'qwe','asd','',NULL,'',NULL,'',NULL,NULL,NULL),(39,'NewContact','Contact2309','','1990-08-21','','','email@mail.com',NULL,NULL,NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `phone` */

insert  into `phone`(`id`,`number`,`type`,`comment`,`contact_id`,`country`,`operator`) values (11,'+316934','CELL','Comment',10,NULL,NULL),(12,'+31629934','CELL','Comment2',10,NULL,NULL),(17,'3','HOME','Comment',39,'1','2'),(18,'123','HOME','',25,'1','2'),(19,'321','CELL','',25,'123','12'),(20,'12','HOME','Ð¹',25,'12','123'),(21,'5','CELL','zz',25,'23','234');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
