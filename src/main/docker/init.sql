-- MySQL dump 10.13  Distrib 5.7.23, for Linux (x86_64)
--
-- Host: localhost    Database: welding
-- ------------------------------------------------------
-- Server version	5.7.23-0ubuntu0.18.04.1

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
-- Table structure for table `brands`
--

USE welding;

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brands` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  `version_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_oce3937d2f4mpfqrycbr0l93m` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'2018-09-10 10:23:12','2018-09-10 10:23:12',1,'Kemppi'),(2,'2018-09-10 10:41:22','2018-09-10 10:41:22',1,'EWM'),(3,'2018-09-10 10:41:57','2018-09-10 10:41:57',1,'OZAS'),(4,'2018-09-10 10:42:34','2018-09-10 11:04:25',1,'Merkle');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  `version_id` bigint(20) DEFAULT NULL,
  `city` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `nip` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `short_name` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `street` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `zip` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_h9btfnrft6cc3k2p18iwyo6ke` (`nip`),
  UNIQUE KEY `UK_7dh1ce7v1ve5ri1l4e81174b2` (`short_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'2018-09-17 12:20:17','2018-09-17 12:20:17',1,'Katowice',NULL,'Firma sp. z O.O.',NULL,'Firma','Kościuszki 18','40-584');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dictionary`
--

DROP TABLE IF EXISTS `dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `value` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dictionary`
--

LOCK TABLES `dictionary` WRITE;
/*!40000 ALTER TABLE `dictionary` DISABLE KEYS */;
INSERT INTO `dictionary` VALUES (1,'validNumber','1/2018');
/*!40000 ALTER TABLE `dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `files` (
  `id` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `data` longblob,
  `file_name` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `file_type` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `files`
--

LOCK TABLES `files` WRITE;
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
/*!40000 ALTER TABLE `files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machines`
--

DROP TABLE IF EXISTS `machines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `machines` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  `version_id` bigint(20) DEFAULT NULL,
  `inw_number` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `serial_number` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `model_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKny56bx2k7sgdbyym2xobe5rqi` (`customer_id`),
  KEY `FKinjkr1tldkgi6a0881rryaqjn` (`model_id`),
  CONSTRAINT `FKinjkr1tldkgi6a0881rryaqjn` FOREIGN KEY (`model_id`) REFERENCES `models` (`id`),
  CONSTRAINT `FKny56bx2k7sgdbyym2xobe5rqi` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machines`
--

LOCK TABLES `machines` WRITE;
/*!40000 ALTER TABLE `machines` DISABLE KEYS */;
INSERT INTO `machines` VALUES (1,'2018-09-17 13:52:50','2018-09-17 13:52:50',1,NULL,'1234567',1,2);
/*!40000 ALTER TABLE `machines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `measures`
--

DROP TABLE IF EXISTS `measures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `measures` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  `version_id` bigint(20) DEFAULT NULL,
  `i_adjust` decimal(4,0) DEFAULT NULL,
  `i_error` decimal(4,1) DEFAULT NULL,
  `i_power` decimal(4,0) DEFAULT NULL,
  `i_valid` decimal(4,0) DEFAULT NULL,
  `result` bit(1) NOT NULL,
  `u_adjust` decimal(4,1) DEFAULT NULL,
  `u_error` decimal(4,1) DEFAULT NULL,
  `u_power` decimal(4,1) DEFAULT NULL,
  `u_valid` decimal(4,1) DEFAULT NULL,
  `validprotocol_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8c8cro5kff2b56cskfpt613im` (`validprotocol_id`),
  CONSTRAINT `FK8c8cro5kff2b56cskfpt613im` FOREIGN KEY (`validprotocol_id`) REFERENCES `validations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `measures`
--

LOCK TABLES `measures` WRITE;
/*!40000 ALTER TABLE `measures` DISABLE KEYS */;
INSERT INTO `measures` VALUES (1,'2018-09-19 09:24:58','2018-09-19 09:24:58',1,5,NULL,NULL,NULL,_binary '\0',10.2,NULL,NULL,NULL,1),(2,'2018-09-19 09:24:58','2018-09-19 09:24:58',1,79,NULL,NULL,NULL,_binary '\0',13.2,NULL,NULL,NULL,1),(3,'2018-09-19 09:24:58','2018-09-19 09:24:58',1,153,NULL,NULL,NULL,_binary '\0',16.1,NULL,NULL,NULL,1),(4,'2018-09-19 09:24:58','2018-09-19 09:24:58',1,226,NULL,NULL,NULL,_binary '\0',19.1,NULL,NULL,NULL,1),(5,'2018-09-19 09:24:58','2018-09-19 09:24:58',1,300,NULL,NULL,NULL,_binary '\0',22.0,NULL,NULL,NULL,1),(6,'2018-09-19 15:40:19','2018-09-19 15:40:19',1,5,NULL,NULL,NULL,_binary '\0',10.2,NULL,NULL,NULL,3),(7,'2018-09-19 15:40:19','2018-09-19 15:40:19',1,79,NULL,NULL,NULL,_binary '\0',13.2,NULL,NULL,NULL,3),(8,'2018-09-19 15:40:19','2018-09-19 15:40:19',1,153,NULL,NULL,NULL,_binary '\0',16.1,NULL,NULL,NULL,3),(9,'2018-09-19 15:40:19','2018-09-19 15:40:19',1,226,NULL,NULL,NULL,_binary '\0',19.1,NULL,NULL,NULL,3),(10,'2018-09-19 15:40:19','2018-09-19 15:40:19',1,300,NULL,NULL,NULL,_binary '\0',22.0,NULL,NULL,NULL,3),(11,'2018-09-19 15:44:23','2018-09-19 15:44:23',1,20,NULL,NULL,NULL,_binary '\0',100.0,NULL,NULL,NULL,3),(12,'2018-09-19 16:13:17','2018-09-19 16:13:17',1,20,NULL,NULL,NULL,_binary '\0',10.8,NULL,NULL,NULL,3),(13,'2018-09-19 16:13:28','2018-09-19 16:13:28',1,50,NULL,NULL,NULL,_binary '\0',12.0,NULL,NULL,NULL,3),(14,'2018-09-19 16:13:34','2018-09-19 16:13:34',1,300,NULL,NULL,NULL,_binary '\0',22.0,NULL,NULL,NULL,3),(15,'2018-09-19 16:13:41','2018-09-19 16:13:41',1,320,NULL,NULL,NULL,_binary '\0',22.0,NULL,NULL,NULL,3),(16,'2018-09-19 16:26:29','2018-09-19 16:26:29',1,320,NULL,NULL,NULL,_binary '\0',22.0,NULL,NULL,NULL,3),(17,'2018-09-19 16:29:09','2018-09-19 16:29:09',1,5,NULL,NULL,NULL,_binary '\0',10.2,NULL,NULL,NULL,3);
/*!40000 ALTER TABLE `measures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `models`
--

DROP TABLE IF EXISTS `models`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `models` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  `version_id` bigint(20) DEFAULT NULL,
  `current_meter` bit(1) DEFAULT NULL,
  `mig` bit(1) DEFAULT NULL,
  `mma` bit(1) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `step_control` bit(1) DEFAULT NULL,
  `tig` bit(1) DEFAULT NULL,
  `voltage_meter` bit(1) DEFAULT NULL,
  `brand_id` bigint(20) DEFAULT NULL,
  `mig_range` bigint(20) DEFAULT NULL,
  `mma_range` bigint(20) DEFAULT NULL,
  `tig_range` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k88mmbltkn8c5cke9r3u5tk8c` (`name`),
  KEY `FK95s72g5hnsl3o0bqeuhnokdxu` (`brand_id`),
  KEY `FK813s3g4fv4f49f73cmvrnps01` (`mig_range`),
  KEY `FKmsa2riqhkrg1rd1u3bja5iqyc` (`mma_range`),
  KEY `FKdbdmr7d9r7hklriwoq7e0tj3a` (`tig_range`),
  CONSTRAINT `FK813s3g4fv4f49f73cmvrnps01` FOREIGN KEY (`mig_range`) REFERENCES `ranges` (`id`),
  CONSTRAINT `FK95s72g5hnsl3o0bqeuhnokdxu` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FKdbdmr7d9r7hklriwoq7e0tj3a` FOREIGN KEY (`tig_range`) REFERENCES `ranges` (`id`),
  CONSTRAINT `FKmsa2riqhkrg1rd1u3bja5iqyc` FOREIGN KEY (`mma_range`) REFERENCES `ranges` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `models`
--

LOCK TABLES `models` WRITE;
/*!40000 ALTER TABLE `models` DISABLE KEYS */;
INSERT INTO `models` VALUES (2,'2018-09-14 11:47:21','2018-09-17 12:10:59',2,_binary '\0',_binary '\0',_binary '','MasterTig 3000',_binary '\0',_binary '',_binary '\0',2,NULL,8,9),(3,'2018-09-14 11:47:31','2018-09-17 12:13:47',2,_binary '\0',_binary '\0',_binary '','MasterTig 4000',_binary '\0',_binary '',_binary '\0',2,NULL,10,11);
/*!40000 ALTER TABLE `models` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ranges`
--

DROP TABLE IF EXISTS `ranges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ranges` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `i_max` decimal(4,0) DEFAULT NULL,
  `i_min` decimal(4,0) DEFAULT NULL,
  `u_max` decimal(4,1) DEFAULT NULL,
  `u_min` decimal(4,1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ranges`
--

LOCK TABLES `ranges` WRITE;
/*!40000 ALTER TABLE `ranges` DISABLE KEYS */;
INSERT INTO `ranges` VALUES (8,250,10,30.0,20.5),(9,300,5,22.0,10.0),(10,350,10,34.0,20.5),(11,400,5,26.0,10.0);
/*!40000 ALTER TABLE `ranges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  `version_id` bigint(20) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2018-09-05 09:35:00','2018-09-05 09:35:00',1,_binary '','user@user.pl','$2a$04$d1nIF6bPEJMsM6Qzaw7O.OKXlEhMSAsI70tu.8jyY8PdGxw7VMtgm','user');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `validations`
--

DROP TABLE IF EXISTS `validations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `validations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  `version_id` bigint(20) DEFAULT NULL,
  `date_validation` datetime DEFAULT NULL,
  `finalized` bit(1) NOT NULL,
  `next_validation` datetime DEFAULT NULL,
  `protocol_number` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `result` bit(1) DEFAULT NULL,
  `type` varchar(3) COLLATE utf8_polish_ci DEFAULT NULL,
  `machine_id` bigint(20) DEFAULT NULL,
  `protocol_id` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o5c6qlhn2rwkkp1bjjog30ux8` (`protocol_number`),
  KEY `FK91yuoupwd07akpgksr1nyit7r` (`machine_id`),
  KEY `FKc4omu0xeo2tfu6xqr1sdbv5aa` (`protocol_id`),
  CONSTRAINT `FK91yuoupwd07akpgksr1nyit7r` FOREIGN KEY (`machine_id`) REFERENCES `machines` (`id`),
  CONSTRAINT `FKc4omu0xeo2tfu6xqr1sdbv5aa` FOREIGN KEY (`protocol_id`) REFERENCES `files` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `validations`
--

LOCK TABLES `validations` WRITE;
/*!40000 ALTER TABLE `validations` DISABLE KEYS */;
INSERT INTO `validations` VALUES (1,'2018-09-18 13:27:26','2018-09-18 13:27:26',1,NULL,_binary '\0',NULL,NULL,NULL,'MMA',1,NULL),(2,'2018-09-18 13:27:26','2018-09-18 13:27:26',1,NULL,_binary '\0',NULL,NULL,NULL,'TIG',1,NULL),(3,'2018-09-19 15:40:19','2018-09-19 15:40:19',1,NULL,_binary '\0',NULL,NULL,NULL,'TIG',1,NULL);
/*!40000 ALTER TABLE `validations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-20 15:55:49