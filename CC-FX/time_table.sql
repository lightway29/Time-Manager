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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` int(11) DEFAULT NULL,
  `class_group_id` int(11) DEFAULT NULL,
  `total_no_of_periods` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
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
-- Table structure for table `class_subject`
--

DROP TABLE IF EXISTS `class_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_group_id` int(11) DEFAULT NULL,
  `subject` varchar(45) DEFAULT NULL,
  `max_per_week` int(11) DEFAULT NULL,
  `max_per_day` int(11) DEFAULT NULL,
  `min_per_day` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_class_group_idx` (`class_group_id`),
  KEY `fk2_subject_idx` (`subject`),
  CONSTRAINT `fk1_class` FOREIGN KEY (`class_group_id`) REFERENCES `class_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sub` FOREIGN KEY (`subject`) REFERENCES `subject` (`subject`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_subject`
--

LOCK TABLES `class_subject` WRITE;
/*!40000 ALTER TABLE `class_subject` DISABLE KEYS */;
INSERT INTO `class_subject` VALUES (1,1,'Maths',6,2,1),(2,1,'English',4,1,1),(3,1,'History',5,2,0),(4,1,'Religion',3,1,0),(5,1,'Science',6,2,1);
/*!40000 ALTER TABLE `class_subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_teacher_timetable`
--

DROP TABLE IF EXISTS `class_teacher_timetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class_teacher_timetable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) DEFAULT NULL,
  `time_slot` varchar(45) DEFAULT NULL,
  `monday` varchar(45) DEFAULT NULL,
  `tuesday` varchar(45) DEFAULT NULL,
  `wednesday` varchar(45) DEFAULT NULL,
  `thursday` varchar(45) DEFAULT NULL,
  `friday` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_teacher_id_idx` (`teacher_id`),
  CONSTRAINT `fk1_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_teacher_timetable`
--

LOCK TABLES `class_teacher_timetable` WRITE;
/*!40000 ALTER TABLE `class_teacher_timetable` DISABLE KEYS */;
/*!40000 ALTER TABLE `class_teacher_timetable` ENABLE KEYS */;
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
  `time` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_class_group_idx` (`class_group_id`),
  KEY `fk2_mon_idx` (`mon`),
  KEY `fk3_time_table_idx` (`class_time_table_id`),
  CONSTRAINT `fk2_group` FOREIGN KEY (`class_group_id`) REFERENCES `class_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk3_time_table` FOREIGN KEY (`class_time_table_id`) REFERENCES `class_time_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_time_table_reg`
--

LOCK TABLES `class_time_table_reg` WRITE;
/*!40000 ALTER TABLE `class_time_table_reg` DISABLE KEYS */;
INSERT INTO `class_time_table_reg` VALUES (1,1,1,'English','Maths','Science','Lit','Science','1A','1','7:50 a.m. - 8:20 a.m.'),(2,1,1,'Maths','Science','Lit','Maths','Science','1A','2','8:20 a.m. - 8:50 a.m.'),(3,1,1,'Science','Lit','Tamil','Maths','Tamil','1A','3','8:50 a.m. - 9:20 a.m.'),(4,1,1,'Maths','Science','Tamil','English','Tamil','1A','4','9:20 a.m. - 9:50 a.m.'),(5,1,1,'Sinhala','Lit','Tamil','English','Science','1A','5','9:50 a.m. - 10:20 a.m.'),(6,1,1,'Science','Maths','Science','Science','Tamil','1A','6','10:20 a.m. - 10:50 a.m.'),(7,1,1,'English','Science','Science','Lit','Science','1A','7','11:20 a.m. - 11:50 a.m.'),(8,1,1,'English','Maths','English','English','Lit','1A','8','11:50 a.m. - 12:20 a.m.');
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
INSERT INTO `subject` VALUES (14,'Art'),(5,'Business Studies'),(11,'Citizenship'),(20,'Eng Gram'),(19,'Eng Story'),(1,'English'),(17,'Env'),(9,'Geography'),(10,'GK'),(18,'HC & ART'),(8,'History'),(12,'IT'),(15,'Library'),(7,'Literature'),(3,'Maths'),(13,'PT'),(21,'Reading'),(6,'Religion'),(2,'Science'),(16,'Second Language'),(4,'SIN / Tamil');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher_subjects`
--

DROP TABLE IF EXISTS `teacher_subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher_subjects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) DEFAULT NULL,
  `subject_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_teacher_id_idx` (`teacher_id`),
  KEY `fk2_subject_idx` (`subject_id`),
  CONSTRAINT `fk1_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk2_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_subjects`
--

LOCK TABLES `teacher_subjects` WRITE;
/*!40000 ALTER TABLE `teacher_subjects` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacher_subjects` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_permission_type`
--

LOCK TABLES `user_permission_type` WRITE;
/*!40000 ALTER TABLE `user_permission_type` DISABLE KEYS */;
INSERT INTO `user_permission_type` VALUES (72,'Customer Registration'),(73,'User Registration'),(78,'Invoice'),(112,'Item Registration'),(115,'Supplier Registration'),(118,'External GRN'),(119,'External GRN Overview'),(120,'External Return Note'),(121,'External Return Note Overview'),(122,'Purchase Order'),(126,'Invoice Settings'),(127,'Purchase Order Overview'),(128,'Service Registration'),(129,'Report Generator'),(130,'Report Registration'),(131,'Report Settings'),(132,'Printer Registration'),(133,'Stock Report'),(134,'Teacher Registration'),(135,'Subject Registration'),(136,'Class Registration'),(137,'Time Table');
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
INSERT INTO `user_permissions` VALUES ('EM0004','Customer Registration',1,1,1,1),('EM0004','External GRN',1,1,1,1),('EM0004','External GRN Overview',1,1,1,1),('EM0004','External Return Note',1,1,1,1),('EM0004','External Return Note Overview',1,1,1,1),('EM0004','Invoice',1,1,1,1),('EM0004','Invoice Settings',1,1,1,1),('EM0004','Item Registration',1,1,1,1),('EM0004','Printer Registration',1,1,1,1),('EM0004','Purchase Order',1,1,1,1),('EM0004','Purchase Order Overview',1,1,1,1),('EM0004','Report Generator',1,1,1,1),('EM0004','Report Registration',1,1,1,1),('EM0004','Report Settings',1,1,1,1),('EM0004','Service Registration',1,1,1,1),('EM0004','Stock Report',1,1,1,1),('EM0004','Supplier Registration',1,1,1,1),('EM0004','User Registration',1,1,1,1),('EM0005','Class Registration',1,1,1,1),('EM0005','Customer Registration',1,1,1,1),('EM0005','External GRN',0,0,0,0),('EM0005','External GRN Overview',0,0,0,0),('EM0005','External Return Note',0,0,0,0),('EM0005','External Return Note Overview',0,0,0,0),('EM0005','Invoice',1,1,1,1),('EM0005','Invoice Settings',1,1,1,1),('EM0005','Item Registration',1,1,1,1),('EM0005','Printer Registration',1,1,1,1),('EM0005','Purchase Order',0,0,0,0),('EM0005','Purchase Order Overview',0,0,0,0),('EM0005','Report Generator',1,1,1,1),('EM0005','Report Registration',1,1,1,1),('EM0005','Report Settings',1,1,1,1),('EM0005','Service Registration',1,1,1,1),('EM0005','Subject Registration',1,1,1,1),('EM0005','Supplier Registration',0,0,0,0),('EM0005','Teacher Registration',1,1,1,1),('EM0005','Time Table',1,1,1,1),('EM0005','User Registration',1,1,1,1);
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

-- Dump completed on 2018-05-06 22:34:24
