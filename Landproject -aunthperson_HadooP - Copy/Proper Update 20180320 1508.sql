-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.67-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema landbinami
--

CREATE DATABASE IF NOT EXISTS landbinami;
USE landbinami;

--
-- Definition of table `bank_details`
--

DROP TABLE IF EXISTS `bank_details`;
CREATE TABLE `bank_details` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `aadhaar_no` varchar(45) NOT NULL,
  `pan_no` varchar(45) NOT NULL,
  `bank` varchar(45) NOT NULL,
  `amount` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bank_details`
--

/*!40000 ALTER TABLE `bank_details` DISABLE KEYS */;
INSERT INTO `bank_details` (`id`,`name`,`aadhaar_no`,`pan_no`,`bank`,`amount`) VALUES 
 (12,'vicky','123412341234','1234','Canara','5000'),
 (13,'vicky','123412341234','4100','Axis','5000');
/*!40000 ALTER TABLE `bank_details` ENABLE KEYS */;


--
-- Definition of table `corporation_details`
--

DROP TABLE IF EXISTS `corporation_details`;
CREATE TABLE `corporation_details` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `certificate_no` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `aadhaar_no` varchar(45) NOT NULL,
  `age` varchar(45) NOT NULL,
  `voter_id` varchar(45) NOT NULL,
  `gender` varchar(45) NOT NULL,
  `dob` varchar(45) NOT NULL,
  `certificate` varchar(45) NOT NULL,
  `certifi_number` varchar(45) NOT NULL,
  `eventdate` varchar(45) NOT NULL,
  `event_time` varchar(45) NOT NULL,
  `legal_name` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `corporation_details`
--

/*!40000 ALTER TABLE `corporation_details` DISABLE KEYS */;
INSERT INTO `corporation_details` (`id`,`certificate_no`,`name`,`aadhaar_no`,`age`,`voter_id`,`gender`,`dob`,`certificate`,`certifi_number`,`eventdate`,`event_time`,`legal_name`) VALUES 
 (13,'123123','vicky','123412341234','46','12341234','Male','12-12-1976','Death','321321','12-12-2012','4.00','anish'),
 (14,'456456','vichu','567856785678','47','56785678','Male','12-12-1975','Death','654654','12-12-2012','5.00','anisha');
/*!40000 ALTER TABLE `corporation_details` ENABLE KEYS */;


--
-- Definition of table `doc_detail`
--

DROP TABLE IF EXISTS `doc_detail`;
CREATE TABLE `doc_detail` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `aadhaarno` varchar(45) NOT NULL,
  `legal_certifi` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `doc_detail`
--

/*!40000 ALTER TABLE `doc_detail` DISABLE KEYS */;
INSERT INTO `doc_detail` (`id`,`aadhaarno`,`legal_certifi`) VALUES 
 (11,'123412341234','321321'),
 (12,'567856785678','654654');
/*!40000 ALTER TABLE `doc_detail` ENABLE KEYS */;


--
-- Definition of table `hadoop_table`
--

DROP TABLE IF EXISTS `hadoop_table`;
CREATE TABLE `hadoop_table` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `docno` varchar(45) NOT NULL,
  `toaadhaar` varchar(45) NOT NULL,
  `legalcertificate` varchar(45) NOT NULL,
  `amount` varchar(45) NOT NULL,
  `fromaadhaar` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hadoop_table`
--

/*!40000 ALTER TABLE `hadoop_table` DISABLE KEYS */;
INSERT INTO `hadoop_table` (`id`,`docno`,`toaadhaar`,`legalcertificate`,`amount`,`fromaadhaar`,`name`,`status`) VALUES 
 (17,'123123','567856785678','321321','85000','123412341234','vichu','PENDING');
/*!40000 ALTER TABLE `hadoop_table` ENABLE KEYS */;


--
-- Definition of table `police_details`
--

DROP TABLE IF EXISTS `police_details`;
CREATE TABLE `police_details` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `age` varchar(45) NOT NULL,
  `aadhaar` varchar(45) NOT NULL,
  `vote` varchar(45) NOT NULL,
  `complaint` varchar(45) NOT NULL,
  `date` varchar(45) NOT NULL,
  `crime` varchar(45) NOT NULL,
  `mobile` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `police_details`
--

/*!40000 ALTER TABLE `police_details` DISABLE KEYS */;
INSERT INTO `police_details` (`id`,`name`,`age`,`aadhaar`,`vote`,`complaint`,`date`,`crime`,`mobile`) VALUES 
 (6,'123412341234','43','123412341234','12341234','none','12-12-2012','Land Forgery','7811089398');
/*!40000 ALTER TABLE `police_details` ENABLE KEYS */;


--
-- Definition of table `transaction_details`
--

DROP TABLE IF EXISTS `transaction_details`;
CREATE TABLE `transaction_details` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `aadhaar_no` varchar(45) NOT NULL,
  `bank` varchar(45) NOT NULL,
  `amount` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction_details`
--

/*!40000 ALTER TABLE `transaction_details` DISABLE KEYS */;
INSERT INTO `transaction_details` (`id`,`aadhaar_no`,`bank`,`amount`) VALUES 
 (14,'123412341234','Canara','45000'),
 (15,'123412341234','Axis','45000');
/*!40000 ALTER TABLE `transaction_details` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
