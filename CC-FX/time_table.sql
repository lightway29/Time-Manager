CREATE DATABASE  IF NOT EXISTS `ccs_db` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ccs_db`;
-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: localhost    Database: ccs_db
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (2,'1'),(6,'4'),(7,'5');
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_group`
--

DROP TABLE IF EXISTS `class_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_id` int(11) NOT NULL,
  `sub_group_id` int(11) NOT NULL,
  `gender` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `class_group_fk2_idx` (`sub_group_id`),
  KEY `class_group_fk1` (`class_id`),
  CONSTRAINT `class_group_fk1` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `class_group_fk2` FOREIGN KEY (`sub_group_id`) REFERENCES `sub_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_group`
--

LOCK TABLES `class_group` WRITE;
/*!40000 ALTER TABLE `class_group` DISABLE KEYS */;
INSERT INTO `class_group` VALUES (1,2,5,'Female');
/*!40000 ALTER TABLE `class_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_section`
--

DROP TABLE IF EXISTS `class_section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class_section` (
  `id` int(11) NOT NULL,
  `subject` int(11) DEFAULT NULL,
  `class_group_id` int(11) DEFAULT NULL,
  `total_no_of_periods` varchar(45) DEFAULT NULL,
  KEY `fk1_subject_idx` (`subject`),
  KEY `fk2_class_group_idx` (`class_group_id`),
  CONSTRAINT `fk2_class_group` FOREIGN KEY (`class_group_id`) REFERENCES `class_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_section`
--

LOCK TABLES `class_section` WRITE;
/*!40000 ALTER TABLE `class_section` DISABLE KEYS */;
/*!40000 ALTER TABLE `class_section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_time_table`
--

DROP TABLE IF EXISTS `class_time_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class_time_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time_table_title` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_time_table`
--

LOCK TABLES `class_time_table` WRITE;
/*!40000 ALTER TABLE `class_time_table` DISABLE KEYS */;
INSERT INTO `class_time_table` VALUES (1,'2018'),(2,'2019');
/*!40000 ALTER TABLE `class_time_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_time_table_reg`
--

DROP TABLE IF EXISTS `class_time_table_reg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class_time_table_reg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_time_table_id` int(11) DEFAULT NULL,
  `class_group_id` int(11) DEFAULT NULL,
  `mon` varchar(45) DEFAULT NULL,
  `tue` varchar(45) DEFAULT NULL,
  `wed` varchar(45) DEFAULT NULL,
  `thu` varchar(45) DEFAULT NULL,
  `fri` varchar(45) DEFAULT NULL,
  `class_title` varchar(45) DEFAULT NULL,
  `slot` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_class_group_idx` (`class_group_id`),
  KEY `fk2_mon_idx` (`mon`),
  KEY `fk3_time_table_idx` (`class_time_table_id`),
  CONSTRAINT `fk2_group` FOREIGN KEY (`class_group_id`) REFERENCES `class_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk3_time_table` FOREIGN KEY (`class_time_table_id`) REFERENCES `class_time_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_time_table_reg`
--

LOCK TABLES `class_time_table_reg` WRITE;
/*!40000 ALTER TABLE `class_time_table_reg` DISABLE KEYS */;
INSERT INTO `class_time_table_reg` VALUES (1,1,1,'English','Maths','Science','Lit','Science','1A','1'),(2,1,1,'Maths','Science','Lit','Maths','Science','1A','2'),(3,1,1,'Science','Lit','Tamil','Maths','Tamil','1A','3'),(4,1,1,'Maths','Science','Tamil','English','Tamil','1A','4'),(5,1,1,'Sinhala','Lit','Tamil','English','Science','1A','5');
/*!40000 ALTER TABLE `class_time_table_reg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cus_id` varchar(45) NOT NULL,
  `cus_title` varchar(45) DEFAULT NULL,
  `cus_name` varchar(300) DEFAULT NULL,
  `cus_address` varchar(300) DEFAULT NULL,
  `cus_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cus_id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (11,'CUS0001','Ms.','Isper Catering Equpment & supplys (Pvt) Ltd','No 477, Nawala RD, Rajagiriya','456546565'),(12,'CUS0002','Mr.','nimal','colombo','Item 2'),(13,'CUS0003','Mr.','kamal','hatton','Vehicle'),(14,'CUS0004','Mr.','sunimal','test','WP-BD-105');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_email`
--

DROP TABLE IF EXISTS `customer_email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_email` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cus_id` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cus` (`cus_id`),
  CONSTRAINT `cus` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_email`
--

LOCK TABLES `customer_email` WRITE;
/*!40000 ALTER TABLE `customer_email` DISABLE KEYS */;
INSERT INTO `customer_email` VALUES (22,'CUS0001','Niresh@gmail.com'),(23,'CUS0002','miren2002@gmail.com'),(24,'CUS0004','hjhj@sds.com');
/*!40000 ALTER TABLE `customer_email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_meter_reading`
--

DROP TABLE IF EXISTS `customer_meter_reading`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_meter_reading` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cus_id` varchar(45) NOT NULL,
  `meter_reading_at_service` varchar(60) DEFAULT NULL,
  `next_service_meter_reading` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`cus_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  CONSTRAINT `customer_meter_reading_fk1` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_meter_reading`
--

LOCK TABLES `customer_meter_reading` WRITE;
/*!40000 ALTER TABLE `customer_meter_reading` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_meter_reading` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_mobile`
--

DROP TABLE IF EXISTS `customer_mobile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_mobile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cus_id` varchar(45) NOT NULL,
  `mob_no` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cus4` (`cus_id`),
  CONSTRAINT `cus4` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_mobile`
--

LOCK TABLES `customer_mobile` WRITE;
/*!40000 ALTER TABLE `customer_mobile` DISABLE KEYS */;
INSERT INTO `customer_mobile` VALUES (120,'CUS0001','0778620956'),(121,'CUS0001','0775412845'),(122,'CUS0001','0312245741'),(123,'CUS0004','1245675484');
/*!40000 ALTER TABLE `customer_mobile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_tel`
--

DROP TABLE IF EXISTS `customer_tel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_tel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cus_id` varchar(45) NOT NULL,
  `tel_no` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cus6` (`cus_id`),
  CONSTRAINT `cus6` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_tel`
--

LOCK TABLES `customer_tel` WRITE;
/*!40000 ALTER TABLE `customer_tel` DISABLE KEYS */;
INSERT INTO `customer_tel` VALUES (124,'CUS0001','0778620956'),(125,'CUS0001','0778458167'),(126,'CUS0001','0312245741'),(127,'CUS0002','0312279041'),(128,'CUS0003','0312279041'),(129,'CUS0004','0312245784');
/*!40000 ALTER TABLE `customer_tel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_type`
--

DROP TABLE IF EXISTS `customer_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_type`
--

LOCK TABLES `customer_type` WRITE;
/*!40000 ALTER TABLE `customer_type` DISABLE KEYS */;
INSERT INTO `customer_type` VALUES (1,'Vehicle'),(3,'Gass');
/*!40000 ALTER TABLE `customer_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_vehicle_no`
--

DROP TABLE IF EXISTS `customer_vehicle_no`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_vehicle_no` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cus_id` varchar(45) NOT NULL,
  `vehicle_no` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cus2` (`cus_id`),
  CONSTRAINT `cus2` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_vehicle_no`
--

LOCK TABLES `customer_vehicle_no` WRITE;
/*!40000 ALTER TABLE `customer_vehicle_no` DISABLE KEYS */;
INSERT INTO `customer_vehicle_no` VALUES (12,'CUS0001','0112506500'),(13,'CUS0003','KD-3845'),(14,'CUS0004','WP-WE856'),(15,'CUS0002','WP-12532DB');
/*!40000 ALTER TABLE `customer_vehicle_no` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drivers`
--

DROP TABLE IF EXISTS `drivers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `drivers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `driver` varchar(100) DEFAULT NULL,
  `cus_id` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `drivers_fk1_idx` (`cus_id`),
  CONSTRAINT `drivers_fk1` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drivers`
--

LOCK TABLES `drivers` WRITE;
/*!40000 ALTER TABLE `drivers` DISABLE KEYS */;
INSERT INTO `drivers` VALUES (1,'sumanapala','CUS0002'),(2,'test','CUS0004');
/*!40000 ALTER TABLE `drivers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `external_grn`
--

DROP TABLE IF EXISTS `external_grn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `external_grn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `external_grn_id` varchar(45) NOT NULL,
  `purchase_order_id` varchar(45) NOT NULL,
  `date` date DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `is_approved` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `user_id` varchar(45) NOT NULL,
  `approve_user_id` varchar(45) DEFAULT NULL,
  `approve_date` date DEFAULT NULL,
  PRIMARY KEY (`external_grn_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `external_grn_fk2` (`user_id`),
  KEY `external_grn_fk3` (`user_id`),
  KEY `external_grn_fk1` (`purchase_order_id`),
  CONSTRAINT `external_grn_fk1` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`purchase_order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `external_grn_fk2` FOREIGN KEY (`user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `external_grn_fk3` FOREIGN KEY (`user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `external_grn`
--

LOCK TABLES `external_grn` WRITE;
/*!40000 ALTER TABLE `external_grn` DISABLE KEYS */;
/*!40000 ALTER TABLE `external_grn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `external_grn_item`
--

DROP TABLE IF EXISTS `external_grn_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `external_grn_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `external_grn_id` varchar(45) NOT NULL,
  `item_id` varchar(45) NOT NULL,
  `batch_no` varchar(45) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `qty` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `external_grn_item_fk1` (`external_grn_id`),
  KEY `external_grn_item_fk2` (`item_id`),
  CONSTRAINT `external_grn_item_fk1` FOREIGN KEY (`external_grn_id`) REFERENCES `external_grn` (`external_grn_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `external_grn_item_fk2` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `external_grn_item`
--

LOCK TABLES `external_grn_item` WRITE;
/*!40000 ALTER TABLE `external_grn_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `external_grn_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `external_return_note`
--

DROP TABLE IF EXISTS `external_return_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `external_return_note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `external_return_note_id` varchar(45) NOT NULL,
  `return_note_type` varchar(50) DEFAULT NULL,
  `return_note_date` date DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `is_approved` int(11) DEFAULT '0',
  `user_id` varchar(45) NOT NULL,
  `approve_user_id` varchar(45) DEFAULT NULL,
  `approve_date` date DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `time_stamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `supplier_id` varchar(45) NOT NULL,
  PRIMARY KEY (`external_return_note_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `external_reurn_note_fk1` (`user_id`),
  KEY `external_reurn_note_fk2` (`approve_user_id`),
  KEY `external_reurn_note_fk3_idx` (`supplier_id`),
  CONSTRAINT `external_reurn_note_fk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `external_reurn_note_fk2` FOREIGN KEY (`approve_user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `external_reurn_note_fk3` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `external_return_note`
--

LOCK TABLES `external_return_note` WRITE;
/*!40000 ALTER TABLE `external_return_note` DISABLE KEYS */;
/*!40000 ALTER TABLE `external_return_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `external_return_note_item`
--

DROP TABLE IF EXISTS `external_return_note_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `external_return_note_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `external_return_note_id` varchar(45) NOT NULL,
  `item_id` varchar(45) NOT NULL,
  `batch_no` varchar(45) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `qty` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `external_return_note_item_fk1` (`external_return_note_id`),
  KEY `external_return_note_item_fk2` (`item_id`),
  CONSTRAINT `external_return_note_item_fk1` FOREIGN KEY (`external_return_note_id`) REFERENCES `external_return_note` (`external_return_note_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `external_return_note_item_fk2` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `external_return_note_item`
--

LOCK TABLES `external_return_note_item` WRITE;
/*!40000 ALTER TABLE `external_return_note_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `external_return_note_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `general_log`
--

DROP TABLE IF EXISTS `general_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `general_log` (
  `event_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_host` mediumtext NOT NULL,
  `thread_id` bigint(21) unsigned NOT NULL,
  `server_id` int(10) unsigned NOT NULL,
  `command_type` varchar(64) NOT NULL,
  `argument` mediumtext NOT NULL
) ENGINE=CSV DEFAULT CHARSET=utf8 COMMENT='General log';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `general_log`
--

LOCK TABLES `general_log` WRITE;
/*!40000 ALTER TABLE `general_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `general_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inv_no` varchar(45) NOT NULL,
  `date` date DEFAULT NULL,
  `salse_executive` varchar(45) DEFAULT NULL,
  `cus_type` varchar(100) DEFAULT NULL,
  `cus_id` varchar(45) NOT NULL,
  `vehicle_no` varchar(100) DEFAULT NULL,
  `total_discount` decimal(10,0) DEFAULT NULL,
  `payment_term` varchar(45) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `net_amount` double DEFAULT NULL,
  `net_amount_word` varchar(500) DEFAULT NULL,
  `user_id` varchar(45) NOT NULL,
  `status` int(11) DEFAULT '0',
  `time_stamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `remarks` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`inv_no`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `invoice_fk1_idx` (`user_id`),
  KEY `invoice_fk2` (`cus_id`),
  CONSTRAINT `invoice_fk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `invoice_fk2` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (5,'INT0001','2015-04-20','',NULL,'CUS0001',NULL,80,'Cash',19720,22326.98,'twenty two thousand three hundred twenty six','EM0004',0,NULL,NULL),(6,'INT0002','2015-06-08','',NULL,'CUS0001',NULL,0,'Cash',25000,28560,'twenty eight thousand five hundred sixty','EM0004',0,NULL,NULL),(7,'INT0003','2016-08-12','Saiton','Vehicle','CUS0002','WC-CAB-1215',0,'Cash',1400,1400,'one thousand four hundred','EM0004',0,'2016-08-12 09:04:47','test remarks created by saiton solutions (pvt) Ltd.'),(8,'INT0004','2016-08-21','Saiton','Vehicle','CUS0003','KD-3845',0,'Cash',5800,5800,'five thousand eight hundred','EM0004',0,'2016-08-21 12:21:11','test'),(9,'INT0005','2016-08-21','Saiton','Vehicle','CUS0002','WP-12532DB',0,'Cash',6400,6400,'six thousand four hundred','EM0004',0,'2016-08-21 12:22:15','test'),(10,'INT0006','2016-08-21','Saiton','Vehicle','CUS0004','WP-WE856',0,'Cash',5200,5200,'five thousand two hundred','EM0004',0,'2016-08-21 12:23:40','mm');
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_driver`
--

DROP TABLE IF EXISTS `invoice_driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_driver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice_id` varchar(45) NOT NULL,
  `driver_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `invoice_driver_fk1_idx` (`invoice_id`),
  KEY `invoice_driver_fk2_idx` (`driver_id`),
  CONSTRAINT `invoice_driver_fk1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`inv_no`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `invoice_driver_fk2` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_driver`
--

LOCK TABLES `invoice_driver` WRITE;
/*!40000 ALTER TABLE `invoice_driver` DISABLE KEYS */;
INSERT INTO `invoice_driver` VALUES (1,'INT0005',1),(2,'INT0006',2);
/*!40000 ALTER TABLE `invoice_driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_id_reset`
--

DROP TABLE IF EXISTS `invoice_id_reset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_id_reset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reset_invoice_id` varchar(45) DEFAULT NULL,
  `is_tax_invoice` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_id_reset`
--

LOCK TABLES `invoice_id_reset` WRITE;
/*!40000 ALTER TABLE `invoice_id_reset` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_id_reset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_item`
--

DROP TABLE IF EXISTS `invoice_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inv_no` varchar(45) NOT NULL,
  `item_id` varchar(45) NOT NULL,
  `part_no` varchar(45) DEFAULT NULL,
  `batch_no` varchar(45) NOT NULL,
  `unit` varchar(45) DEFAULT NULL,
  `unit_qty` int(11) DEFAULT NULL,
  `description` varchar(450) DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `net_price` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `discount_rate` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `invoice_item_fk1_idx` (`inv_no`),
  KEY `invoice_item_fk2_idx` (`item_id`,`batch_no`),
  CONSTRAINT `invoice_item_fk1` FOREIGN KEY (`inv_no`) REFERENCES `invoice` (`inv_no`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `invoice_item_fk2` FOREIGN KEY (`item_id`, `batch_no`) REFERENCES `item_sub` (`item_id`, `batch_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_item`
--

LOCK TABLES `invoice_item` WRITE;
/*!40000 ALTER TABLE `invoice_item` DISABLE KEYS */;
INSERT INTO `invoice_item` VALUES (1,'INT0003','ITM0001','partNo','BAT0001','unit',0,'gear oil',2,700,1400,0,0),(2,'INT0004','ITM0001','partNo','BAT0001','unit',0,'gear oil',4,700,2800,0,0),(3,'INT0004','ITM0002','partNo','BAT0001','unit',0,'mm',5,600,3000,0,0),(4,'INT0005','ITM0001','partNo','BAT0001','unit',0,'gear oil',4,700,2800,0,0),(5,'INT0005','ITM0004','partNo','BAT0001','unit',0,'ty',4,900,3600,0,0),(6,'INT0006','ITM0002','partNo','BAT0001','unit',0,'mm',4,600,2400,0,0),(7,'INT0006','ITM0001','partNo','BAT0001','unit',0,'gear oil',4,700,2800,0,0);
/*!40000 ALTER TABLE `invoice_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_meter`
--

DROP TABLE IF EXISTS `invoice_meter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_meter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice_no` varchar(45) DEFAULT NULL,
  `service_meter_reading` varchar(45) DEFAULT NULL,
  `next_service_meter_reading` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `invoice_meater_fk1_idx` (`invoice_no`),
  CONSTRAINT `invoice_meater_fk1` FOREIGN KEY (`invoice_no`) REFERENCES `invoice` (`inv_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_meter`
--

LOCK TABLES `invoice_meter` WRITE;
/*!40000 ALTER TABLE `invoice_meter` DISABLE KEYS */;
INSERT INTO `invoice_meter` VALUES (1,'INT0003','51246598','51248098'),(2,'INT0004','56456354654','456546456'),(3,'INT0005','dfdfdf','dsfdsf'),(4,'INT0006','45454','4545454');
/*!40000 ALTER TABLE `invoice_meter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_service`
--

DROP TABLE IF EXISTS `invoice_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice_id` varchar(45) NOT NULL,
  `service_id` varchar(45) NOT NULL,
  PRIMARY KEY (`invoice_id`,`service_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `invoice_service_fk2_idx` (`service_id`),
  CONSTRAINT `invoice_service_fk1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`inv_no`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `invoice_service_fk2` FOREIGN KEY (`service_id`) REFERENCES `services` (`service_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_service`
--

LOCK TABLES `invoice_service` WRITE;
/*!40000 ALTER TABLE `invoice_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` varchar(45) NOT NULL,
  `item_name` varchar(200) DEFAULT NULL,
  `qty` double DEFAULT '0',
  `user_id` varchar(45) NOT NULL,
  `item_description` varchar(300) DEFAULT NULL,
  `part_no` varchar(200) DEFAULT NULL,
  `item_main_category` int(11) NOT NULL,
  `item_sub_category` int(11) NOT NULL,
  PRIMARY KEY (`item_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `item_fk2` (`user_id`),
  KEY `item_fk4_idx` (`item_sub_category`),
  KEY `item_fk3_idx` (`item_main_category`),
  CONSTRAINT `item_fk2` FOREIGN KEY (`user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `item_fk3` FOREIGN KEY (`item_main_category`) REFERENCES `class` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_fk4` FOREIGN KEY (`item_sub_category`) REFERENCES `item_sub_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'ITM0001','gear oil',-9,'EM0004','gear oil test','XC123',2,3),(2,'ITM0002','mm',-3,'EM0004','ss','DF456',2,4),(4,'ITM0004','ty',2,'EM0004','rt','YSD4584',2,4);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_category`
--

DROP TABLE IF EXISTS `item_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemMainCategory` int(11) NOT NULL,
  `itemSubCategorycol` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `item_category_fk1_idx` (`itemMainCategory`),
  KEY `item_category_fk2_idx` (`itemSubCategorycol`),
  CONSTRAINT `item_category_fk1` FOREIGN KEY (`itemMainCategory`) REFERENCES `class` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `item_category_fk2` FOREIGN KEY (`itemSubCategorycol`) REFERENCES `item_sub_category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_category`
--

LOCK TABLES `item_category` WRITE;
/*!40000 ALTER TABLE `item_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_sub`
--

DROP TABLE IF EXISTS `item_sub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_sub` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` varchar(45) NOT NULL,
  `batch_no` varchar(45) NOT NULL,
  `qty` double DEFAULT '0',
  `buying_price` double DEFAULT NULL,
  `reorder_level` double NOT NULL DEFAULT '0',
  `selling_price` double DEFAULT NULL,
  `unit` int(11) NOT NULL,
  PRIMARY KEY (`item_id`,`batch_no`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `item_sub_fk1` (`item_id`),
  KEY `item_sub_fk2_idx` (`unit`),
  CONSTRAINT `item_sub_fk1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `item_sub_fk2` FOREIGN KEY (`unit`) REFERENCES `item_unit_value` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_sub`
--

LOCK TABLES `item_sub` WRITE;
/*!40000 ALTER TABLE `item_sub` DISABLE KEYS */;
INSERT INTO `item_sub` VALUES (1,'ITM0001','BAT0001',-9,600,10,700,1),(3,'ITM0002','BAT0001',-3,500,10,600,1),(5,'ITM0004','BAT0001',2,800,10,900,1);
/*!40000 ALTER TABLE `item_sub` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_sub_category`
--

DROP TABLE IF EXISTS `item_sub_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_sub_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_Sub_Category` varchar(45) DEFAULT NULL,
  `item_main_category` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `item_sub_category_fk1_idx` (`item_main_category`),
  CONSTRAINT `item_sub_category_fk1` FOREIGN KEY (`item_main_category`) REFERENCES `class` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_sub_category`
--

LOCK TABLES `item_sub_category` WRITE;
/*!40000 ALTER TABLE `item_sub_category` DISABLE KEYS */;
INSERT INTO `item_sub_category` VALUES (3,'ioc',2),(4,'lanka oil',2),(5,'tt',2);
/*!40000 ALTER TABLE `item_sub_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_unit`
--

DROP TABLE IF EXISTS `item_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `unit` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_unit`
--

LOCK TABLES `item_unit` WRITE;
/*!40000 ALTER TABLE `item_unit` DISABLE KEYS */;
INSERT INTO `item_unit` VALUES (1,'ml'),(2,'L');
/*!40000 ALTER TABLE `item_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_unit_value`
--

DROP TABLE IF EXISTS `item_unit_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_unit_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `unit_qty` varchar(45) DEFAULT NULL,
  `item_unit` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `item_unit_value_fk1_idx` (`item_unit`),
  CONSTRAINT `item_unit_value_fk1` FOREIGN KEY (`item_unit`) REFERENCES `item_unit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_unit_value`
--

LOCK TABLES `item_unit_value` WRITE;
/*!40000 ALTER TABLE `item_unit_value` DISABLE KEYS */;
INSERT INTO `item_unit_value` VALUES (1,'4',1),(2,'1',2),(3,'2',2);
/*!40000 ALTER TABLE `item_unit_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nid` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `description` varchar(100) NOT NULL,
  `ui` varchar(45) NOT NULL,
  `added_user` varchar(45) DEFAULT NULL,
  `date_added` date NOT NULL,
  `date_resolved` date DEFAULT NULL,
  `is_resolved` int(11) NOT NULL DEFAULT '0',
  `resolved_user` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_notify_notify_idx` (`type`),
  CONSTRAINT `fk_notify_notify` FOREIGN KEY (`type`) REFERENCES `user_notification_type` (`type`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_type`
--

DROP TABLE IF EXISTS `payment_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_type`
--

LOCK TABLES `payment_type` WRITE;
/*!40000 ALTER TABLE `payment_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `printer`
--

DROP TABLE IF EXISTS `printer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `printer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `description` varchar(200) NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_printer_printer_type_idx` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `printer`
--

LOCK TABLES `printer` WRITE;
/*!40000 ALTER TABLE `printer` DISABLE KEYS */;
/*!40000 ALTER TABLE `printer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `printer_report`
--

DROP TABLE IF EXISTS `printer_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `printer_report` (
  `pid` varchar(45) NOT NULL,
  `rid` varchar(45) NOT NULL,
  PRIMARY KEY (`pid`,`rid`),
  KEY `fk_pr_r_idx` (`rid`),
  CONSTRAINT `fk_pr_p` FOREIGN KEY (`pid`) REFERENCES `printer` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_pr_r` FOREIGN KEY (`rid`) REFERENCES `report` (`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `printer_report`
--

LOCK TABLES `printer_report` WRITE;
/*!40000 ALTER TABLE `printer_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `printer_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_order`
--

DROP TABLE IF EXISTS `purchase_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchase_order_id` varchar(45) NOT NULL,
  `supplier_id` varchar(45) NOT NULL,
  `date` date DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `is_approved` varchar(45) DEFAULT '0',
  `user_id` varchar(45) NOT NULL,
  `approve_user_id` varchar(45) DEFAULT NULL,
  `approve_date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`purchase_order_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `purchase_order_fk1` (`user_id`),
  KEY `purchase_order_fk2` (`supplier_id`),
  CONSTRAINT `purchase_order_fk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchase_order_fk2` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order`
--

LOCK TABLES `purchase_order` WRITE;
/*!40000 ALTER TABLE `purchase_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_order_item`
--

DROP TABLE IF EXISTS `purchase_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchase_order_id` varchar(45) NOT NULL,
  `item_id` varchar(45) NOT NULL,
  `batch_no` varchar(45) NOT NULL,
  `qty` double DEFAULT NULL,
  `remarks` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `purchase_order_item_fk1` (`purchase_order_id`),
  KEY `purchase_order_item_fk3` (`item_id`,`batch_no`),
  CONSTRAINT `purchase_order_item_fk1` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`purchase_order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchase_order_item_fk3` FOREIGN KEY (`item_id`, `batch_no`) REFERENCES `item_sub` (`item_id`, `batch_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_order_item`
--

LOCK TABLES `purchase_order_item` WRITE;
/*!40000 ALTER TABLE `purchase_order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase_order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `is_Delete_privilege` int(11) DEFAULT '1',
  `is_Network` int(11) DEFAULT '1',
  PRIMARY KEY (`rid`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_repot_report_type_idx` (`type`),
  CONSTRAINT `fk_repot_report_type_idx` FOREIGN KEY (`type`) REFERENCES `report_type` (`type`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (1,'RPT0001','Profit And Loss Report','Finance','.//Reports//Profit&Loss.jasper',0,1,1),(32,'RPT0002','Stock Report','Stock','.//Reports//Stock.jasper',0,1,1);
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report_reg`
--

DROP TABLE IF EXISTS `report_reg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_reg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `report_id` varchar(45) NOT NULL,
  `printer_id` varchar(45) NOT NULL,
  `status` int(11) DEFAULT '0',
  `user_id` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `report_reg_fk1` (`report_id`),
  KEY `report_reg_fk2` (`printer_id`),
  KEY `report_reg_fk3` (`user_id`),
  CONSTRAINT `report_reg_fk1` FOREIGN KEY (`report_id`) REFERENCES `report` (`rid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `report_reg_fk2` FOREIGN KEY (`printer_id`) REFERENCES `printer` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `report_reg_fk3` FOREIGN KEY (`user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_reg`
--

LOCK TABLES `report_reg` WRITE;
/*!40000 ALTER TABLE `report_reg` DISABLE KEYS */;
/*!40000 ALTER TABLE `report_reg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report_type`
--

DROP TABLE IF EXISTS `report_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`type`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_type`
--

LOCK TABLES `report_type` WRITE;
/*!40000 ALTER TABLE `report_type` DISABLE KEYS */;
INSERT INTO `report_type` VALUES (1,'Finance'),(2,'Stock');
/*!40000 ALTER TABLE `report_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `req_note`
--

DROP TABLE IF EXISTS `req_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `req_note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `req_note_id` varchar(45) NOT NULL,
  `date` date DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `req_note_type` varchar(45) DEFAULT NULL,
  `is_approved` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `user_id` varchar(45) NOT NULL,
  `approve_user_id` varchar(45) DEFAULT NULL,
  `approve_date` date DEFAULT NULL,
  PRIMARY KEY (`req_note_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `req_note_fk1` (`user_id`),
  KEY `req_note_fk2` (`user_id`),
  CONSTRAINT `req_note_fk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `req_note_fk2` FOREIGN KEY (`user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `req_note`
--

LOCK TABLES `req_note` WRITE;
/*!40000 ALTER TABLE `req_note` DISABLE KEYS */;
/*!40000 ALTER TABLE `req_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `req_note_item`
--

DROP TABLE IF EXISTS `req_note_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `req_note_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `req_note_id` varchar(45) NOT NULL,
  `item_id` varchar(45) NOT NULL,
  `description` varchar(450) DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `issue_qty` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `req_note_item_fk1` (`req_note_id`),
  KEY `req_note_item_fk2` (`item_id`),
  CONSTRAINT `req_note_item_fk1` FOREIGN KEY (`req_note_id`) REFERENCES `req_note` (`req_note_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `req_note_item_fk2` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `req_note_item`
--

LOCK TABLES `req_note_item` WRITE;
/*!40000 ALTER TABLE `req_note_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `req_note_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `return_note`
--

DROP TABLE IF EXISTS `return_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `return_note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `return_note_id` varchar(45) NOT NULL,
  `return_note_type` varchar(50) DEFAULT NULL,
  `return_note_date` date DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `is_approved` int(11) DEFAULT '0',
  `user_id` varchar(45) NOT NULL,
  `approve_user_id` varchar(45) DEFAULT NULL,
  `approve_date` date DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`return_note_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `return_note_fk1` (`user_id`),
  KEY `return_note_fk2` (`approve_user_id`),
  CONSTRAINT `return_note_fk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `return_note_fk2` FOREIGN KEY (`approve_user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return_note`
--

LOCK TABLES `return_note` WRITE;
/*!40000 ALTER TABLE `return_note` DISABLE KEYS */;
/*!40000 ALTER TABLE `return_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `return_note_item`
--

DROP TABLE IF EXISTS `return_note_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `return_note_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `return_note_id` varchar(45) NOT NULL,
  `item_id` varchar(45) NOT NULL,
  `batch_no` varchar(45) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `qty` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `return_note_item_fk1` (`return_note_id`),
  KEY `return_note_item_fk2` (`item_id`),
  CONSTRAINT `return_note_item_fk1` FOREIGN KEY (`return_note_id`) REFERENCES `return_note` (`return_note_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `return_note_item_fk2` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return_note_item`
--

LOCK TABLES `return_note_item` WRITE;
/*!40000 ALTER TABLE `return_note_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `return_note_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `return_resolve`
--

DROP TABLE IF EXISTS `return_resolve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `return_resolve` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resolve_id` varchar(45) NOT NULL,
  `return_note_id` varchar(45) DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `item_id` varchar(45) NOT NULL,
  `batch_no` varchar(45) NOT NULL,
  `resolve_date` date DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `user_id` varchar(45) NOT NULL,
  PRIMARY KEY (`resolve_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `return_resolve_fk1` (`item_id`,`batch_no`),
  KEY `return_resolve_fk2` (`user_id`),
  CONSTRAINT `return_resolve_fk1` FOREIGN KEY (`item_id`, `batch_no`) REFERENCES `item_sub` (`item_id`, `batch_no`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `return_resolve_fk2` FOREIGN KEY (`user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return_resolve`
--

LOCK TABLES `return_resolve` WRITE;
/*!40000 ALTER TABLE `return_resolve` DISABLE KEYS */;
/*!40000 ALTER TABLE `return_resolve` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `server_config`
--

DROP TABLE IF EXISTS `server_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(20) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `server_config`
--

LOCK TABLES `server_config` WRITE;
/*!40000 ALTER TABLE `server_config` DISABLE KEYS */;
INSERT INTO `server_config` VALUES (1,'127.0.0.1',1099);
/*!40000 ALTER TABLE `server_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `services` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` varchar(45) NOT NULL,
  `service` varchar(100) DEFAULT NULL,
  `service_description` varchar(400) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `user_id` varchar(45) NOT NULL,
  PRIMARY KEY (`service_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `services_fk1_idx` (`user_id`),
  CONSTRAINT `services_fk1` FOREIGN KEY (`user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (3,'SVC0007','uu','oo',400,'EM0004'),(4,'SVC0008','mmmm','iiiii',200,'EM0004'),(5,'SVC0009','ghgh','ghgh',300,'EM0004'),(6,'SVC0010','hjj','hjhj',400,'EM0004'),(7,'SVC0011','hjhjk','jhkjk',500,'EM0004'),(8,'SVC0012','Super Service','Test',1000,'EM0005');
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slow_log`
--

DROP TABLE IF EXISTS `slow_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slow_log` (
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_host` mediumtext NOT NULL,
  `query_time` time NOT NULL,
  `lock_time` time NOT NULL,
  `rows_sent` int(11) NOT NULL,
  `rows_examined` int(11) NOT NULL,
  `db` varchar(512) NOT NULL,
  `last_insert_id` int(11) NOT NULL,
  `insert_id` int(11) NOT NULL,
  `server_id` int(10) unsigned NOT NULL,
  `sql_text` mediumtext NOT NULL,
  `thread_id` bigint(21) unsigned NOT NULL
) ENGINE=CSV DEFAULT CHARSET=utf8 COMMENT='Slow log';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slow_log`
--

LOCK TABLES `slow_log` WRITE;
/*!40000 ALTER TABLE `slow_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `slow_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_handler`
--

DROP TABLE IF EXISTS `stock_handler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_handler` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `item_id` varchar(45) NOT NULL,
  `qty` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `user_id` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `stock_handler_fk1` (`item_id`),
  KEY `stock_handler_fk2` (`user_id`),
  CONSTRAINT `stock_handler_fk1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stock_handler_fk2` FOREIGN KEY (`user_id`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_handler`
--

LOCK TABLES `stock_handler` WRITE;
/*!40000 ALTER TABLE `stock_handler` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_handler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_group`
--

DROP TABLE IF EXISTS `sub_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sub_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_group`
--

LOCK TABLES `sub_group` WRITE;
/*!40000 ALTER TABLE `sub_group` DISABLE KEYS */;
INSERT INTO `sub_group` VALUES (5,'A'),(7,'B'),(8,'C'),(9,'D');
/*!40000 ALTER TABLE `sub_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject` (
  `id` int(11) NOT NULL,
  `subject` varchar(45) NOT NULL,
  PRIMARY KEY (`subject`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `reg_no` varchar(200) NOT NULL,
  `address` varchar(500) NOT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'SUP0001','Saman','200000000V','Malabe');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_item`
--

DROP TABLE IF EXISTS `supplier_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supplier_item` (
  `item_id` varchar(45) NOT NULL,
  `sid` varchar(45) NOT NULL,
  PRIMARY KEY (`item_id`,`sid`),
  KEY `fk_ss_ss_idx` (`sid`),
  CONSTRAINT `fk_ss_item` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ss_ss` FOREIGN KEY (`sid`) REFERENCES `supplier` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_item`
--

LOCK TABLES `supplier_item` WRITE;
/*!40000 ALTER TABLE `supplier_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplier_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_tel`
--

DROP TABLE IF EXISTS `supplier_tel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supplier_tel` (
  `tel` varchar(45) NOT NULL,
  `sid` varchar(45) NOT NULL,
  PRIMARY KEY (`tel`,`sid`),
  KEY `fk_ss_tel_fk_idx` (`sid`),
  CONSTRAINT `fk_ss_tel_fk` FOREIGN KEY (`sid`) REFERENCES `supplier` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_tel`
--

LOCK TABLES `supplier_tel` WRITE;
/*!40000 ALTER TABLE `supplier_tel` DISABLE KEYS */;
INSERT INTO `supplier_tel` VALUES ('0719195161','SUP0001');
/*!40000 ALTER TABLE `supplier_tel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `EID` varchar(45) NOT NULL,
  `title` varchar(10) NOT NULL,
  `name` varchar(200) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `spec_code` varchar(45) DEFAULT NULL,
  `flag` varchar(45) DEFAULT NULL,
  `category_type` varchar(45) NOT NULL,
  `is_canceled` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`EID`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  KEY `usertype` (`flag`),
  KEY `categoryFK_idx` (`category_type`),
  KEY `user` (`flag`),
  KEY `user_fk2` (`category_type`),
  CONSTRAINT `user_fk1` FOREIGN KEY (`flag`) REFERENCES `user_type` (`type`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `user_fk2` FOREIGN KEY (`category_type`) REFERENCES `user_sub_type` (`type`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (8,'EM0004','Mr.','Saiton','saiton','3CRO+GzZlkI=',NULL,'Manager','Administrator',0),(9,'EM0005','Mr.','admin','admin','3CRO+GzZlkI=',NULL,'Ultra User','Administrator',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notification_type`
--

DROP TABLE IF EXISTS `user_notification_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_notification_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`type`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notification_type`
--

LOCK TABLES `user_notification_type` WRITE;
/*!40000 ALTER TABLE `user_notification_type` DISABLE KEYS */;
INSERT INTO `user_notification_type` VALUES (8,'Stock');
/*!40000 ALTER TABLE `user_notification_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notifications`
--

DROP TABLE IF EXISTS `user_notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_notifications` (
  `EID` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `show` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`EID`,`type`),
  KEY `fkusernotf2_idx` (`type`),
  CONSTRAINT `fkusernotf1` FOREIGN KEY (`EID`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkusernotf2` FOREIGN KEY (`type`) REFERENCES `user_notification_type` (`type`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notifications`
--

LOCK TABLES `user_notifications` WRITE;
/*!40000 ALTER TABLE `user_notifications` DISABLE KEYS */;
INSERT INTO `user_notifications` VALUES ('EM0005','Stock',0);
/*!40000 ALTER TABLE `user_notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_permission_type`
--

DROP TABLE IF EXISTS `user_permission_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_permission_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`type`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_permission_type`
--

LOCK TABLES `user_permission_type` WRITE;
/*!40000 ALTER TABLE `user_permission_type` DISABLE KEYS */;
INSERT INTO `user_permission_type` VALUES (72,'Customer Registration'),(73,'User Registration'),(78,'Invoice'),(112,'Item Registration'),(115,'Supplier Registration'),(118,'External GRN'),(119,'External GRN Overview'),(120,'External Return Note'),(121,'External Return Note Overview'),(122,'Purchase Order'),(126,'Invoice Settings'),(127,'Purchase Order Overview'),(128,'Service Registration'),(129,'Report Generator'),(130,'Report Registration'),(131,'Report Settings'),(132,'Printer Registration'),(133,'Stock Report'),(134,'Teacher Registration'),(135,'Subject Registration'),(136,'Class Registration');
/*!40000 ALTER TABLE `user_permission_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_permissions`
--

DROP TABLE IF EXISTS `user_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_permissions` (
  `EID` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `allow_insert` int(11) NOT NULL DEFAULT '0',
  `allow_update` int(11) NOT NULL DEFAULT '0',
  `allow_delete` int(11) NOT NULL DEFAULT '0',
  `allow_view` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`EID`,`type`),
  KEY `fkuserperf2_idx` (`type`),
  CONSTRAINT `fkuserperf1` FOREIGN KEY (`EID`) REFERENCES `user` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkuserperf2` FOREIGN KEY (`type`) REFERENCES `user_permission_type` (`type`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_permissions`
--

LOCK TABLES `user_permissions` WRITE;
/*!40000 ALTER TABLE `user_permissions` DISABLE KEYS */;
INSERT INTO `user_permissions` VALUES ('EM0004','Customer Registration',1,1,1,1),('EM0004','External GRN',1,1,1,1),('EM0004','External GRN Overview',1,1,1,1),('EM0004','External Return Note',1,1,1,1),('EM0004','External Return Note Overview',1,1,1,1),('EM0004','Invoice',1,1,1,1),('EM0004','Invoice Settings',1,1,1,1),('EM0004','Item Registration',1,1,1,1),('EM0004','Printer Registration',1,1,1,1),('EM0004','Purchase Order',1,1,1,1),('EM0004','Purchase Order Overview',1,1,1,1),('EM0004','Report Generator',1,1,1,1),('EM0004','Report Registration',1,1,1,1),('EM0004','Report Settings',1,1,1,1),('EM0004','Service Registration',1,1,1,1),('EM0004','Stock Report',1,1,1,1),('EM0004','Supplier Registration',1,1,1,1),('EM0004','User Registration',1,1,1,1),('EM0005','Class Registration',1,1,1,1),('EM0005','Customer Registration',1,1,1,1),('EM0005','External GRN',0,0,0,0),('EM0005','External GRN Overview',0,0,0,0),('EM0005','External Return Note',0,0,0,0),('EM0005','External Return Note Overview',0,0,0,0),('EM0005','Invoice',1,1,1,1),('EM0005','Invoice Settings',1,1,1,1),('EM0005','Item Registration',1,1,1,1),('EM0005','Printer Registration',1,1,1,1),('EM0005','Purchase Order',0,0,0,0),('EM0005','Purchase Order Overview',0,0,0,0),('EM0005','Report Generator',1,1,1,1),('EM0005','Report Registration',1,1,1,1),('EM0005','Report Settings',1,1,1,1),('EM0005','Service Registration',1,1,1,1),('EM0005','Subject Registration',1,1,1,1),('EM0005','Supplier Registration',0,0,0,0),('EM0005','Teacher Registration',1,1,1,1),('EM0005','User Registration',1,1,1,1);
/*!40000 ALTER TABLE `user_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_sub_type`
--

DROP TABLE IF EXISTS `user_sub_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_sub_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`type`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_sub_type`
--

LOCK TABLES `user_sub_type` WRITE;
/*!40000 ALTER TABLE `user_sub_type` DISABLE KEYS */;
INSERT INTO `user_sub_type` VALUES (1,'Stock'),(2,'Administrator'),(3,'Staff');
/*!40000 ALTER TABLE `user_sub_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`type`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES (1,'Ultra User'),(2,'Super User'),(3,'Accountant'),(4,'Manager'),(5,'Walker');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-30 23:46:20
