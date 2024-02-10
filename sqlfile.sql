CREATE DATABASE  IF NOT EXISTS `mu_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mu_db`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: mu_db
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` bigint NOT NULL AUTO_INCREMENT,
  `fname` varchar(255) NOT NULL,
  `lname` varchar(255) NOT NULL,
  `pwd` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `date_of_birth` date NOT NULL,
  `verification_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (46,'fred','wu','$2a$10$zeTCf96o8Pk3y0q3KjXNiOoxUowijPpJhw6aIp0E35wCPRf3eZrJ.','bason29806@pursip.com','2024-01-03','verified');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_mf_history`
--

DROP TABLE IF EXISTS `customer_mf_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_mf_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `currency` varchar(255) DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `transaction_date` date DEFAULT NULL,
  `mf_fund_house` varchar(255) DEFAULT NULL,
  `mf_name` varchar(255) DEFAULT NULL,
  `mf_units` int DEFAULT NULL,
  `portfolio_id` bigint DEFAULT NULL,
  `mf_schema_id` int DEFAULT NULL,
  `mf_transaction_type` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKre7nue4aw3dtnrcy0oql20f1b` (`mf_schema_id`),
  KEY `FK47k25rn67o4sgi4pet8p3l2ib` (`mf_transaction_type`),
  CONSTRAINT `FK47k25rn67o4sgi4pet8p3l2ib` FOREIGN KEY (`mf_transaction_type`) REFERENCES `customer_transaction_type` (`type_id`),
  CONSTRAINT `FKre7nue4aw3dtnrcy0oql20f1b` FOREIGN KEY (`mf_schema_id`) REFERENCES `mfdetails` (`schema_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_mf_history`
--

LOCK TABLES `customer_mf_history` WRITE;
/*!40000 ALTER TABLE `customer_mf_history` DISABLE KEYS */;
INSERT INTO `customer_mf_history` VALUES (8,'REGLR',46,'2024-01-10','Regular Mutual Funds','Growth Fund',3,252,1,1),(9,'REGLR',46,'2024-01-10','Regular Mutual Funds','Growth Fund',2,252,1,2);
/*!40000 ALTER TABLE `customer_mf_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_otp`
--

DROP TABLE IF EXISTS `customer_otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_otp` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `opt` int DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlj7ewvxmaxxmkkgiugm4f49y8` (`customer_id`),
  CONSTRAINT `FKlj7ewvxmaxxmkkgiugm4f49y8` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_otp`
--

LOCK TABLES `customer_otp` WRITE;
/*!40000 ALTER TABLE `customer_otp` DISABLE KEYS */;
INSERT INTO `customer_otp` VALUES (9,7534,46);
/*!40000 ALTER TABLE `customer_otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_seq`
--

DROP TABLE IF EXISTS `customer_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_seq`
--

LOCK TABLES `customer_seq` WRITE;
/*!40000 ALTER TABLE `customer_seq` DISABLE KEYS */;
INSERT INTO `customer_seq` VALUES (1);
/*!40000 ALTER TABLE `customer_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_transaction_type`
--

DROP TABLE IF EXISTS `customer_transaction_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_transaction_type` (
  `type_id` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_transaction_type`
--

LOCK TABLES `customer_transaction_type` WRITE;
/*!40000 ALTER TABLE `customer_transaction_type` DISABLE KEYS */;
INSERT INTO `customer_transaction_type` VALUES (1,'purchase'),(2,'withdraw');
/*!40000 ALTER TABLE `customer_transaction_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_transaction_type_seq`
--

DROP TABLE IF EXISTS `customer_transaction_type_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_transaction_type_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_transaction_type_seq`
--

LOCK TABLES `customer_transaction_type_seq` WRITE;
/*!40000 ALTER TABLE `customer_transaction_type_seq` DISABLE KEYS */;
INSERT INTO `customer_transaction_type_seq` VALUES (101);
/*!40000 ALTER TABLE `customer_transaction_type_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mfdetailhistory`
--

DROP TABLE IF EXISTS `mfdetailhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mfdetailhistory` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `schema_id` int NOT NULL,
  `nav` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `schema_id` (`schema_id`),
  CONSTRAINT `mfdetailhistory_ibfk_1` FOREIGN KEY (`schema_id`) REFERENCES `mfdetails` (`schema_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mfdetailhistory`
--

LOCK TABLES `mfdetailhistory` WRITE;
/*!40000 ALTER TABLE `mfdetailhistory` DISABLE KEYS */;
INSERT INTO `mfdetailhistory` VALUES (1,'2023-04-20',1,15.2),(2,'2023-04-21',1,17.2),(3,'2023-04-22',1,7.2),(4,'2023-04-23',1,20.15),(5,'2023-04-24',1,21.2),(6,'2023-04-20',2,12.2),(7,'2023-04-21',2,11.15),(8,'2023-04-22',2,5.03),(9,'2023-04-23',2,15.15),(10,'2023-04-24',2,9.22),(11,'2023-04-20',3,12.2),(12,'2023-04-21',3,11.15),(13,'2023-04-22',3,5.03),(14,'2023-04-23',3,15.15),(15,'2023-04-24',3,9.22),(16,'2023-04-20',4,12.2),(17,'2023-04-21',4,11.15),(18,'2023-04-22',4,5.03),(19,'2023-04-23',4,15.15),(20,'2023-04-24',4,9.22),(21,'2023-04-20',5,12.2),(22,'2023-04-21',5,11.15),(23,'2023-04-22',5,5.03),(24,'2023-04-23',5,15.15),(25,'2023-04-24',5,9.22),(26,'2023-04-20',6,12.2),(27,'2023-04-21',6,11.15),(28,'2023-04-22',6,5.03),(29,'2023-04-23',6,15.15),(30,'2023-04-24',6,9.22),(31,'2023-04-20',7,12.2),(32,'2023-04-21',7,11.15),(33,'2023-04-22',7,5.03),(34,'2023-04-23',7,15.15),(35,'2023-04-24',7,9.22),(36,'2023-04-20',8,12.2),(37,'2023-04-21',8,11.15),(38,'2023-04-22',8,5.03),(39,'2023-04-23',8,15.15),(40,'2023-04-24',8,9.22),(41,'2023-04-20',9,12.2),(42,'2023-04-21',9,11.15),(43,'2023-04-22',9,5.03),(44,'2023-04-23',9,15.15),(45,'2023-04-24',9,9.22),(46,'2023-04-20',10,12.2),(47,'2023-04-21',10,24.15),(48,'2023-04-22',10,5.03),(49,'2023-04-23',10,15.15),(50,'2023-04-24',10,9.22),(51,'2023-04-20',11,12.2),(52,'2023-04-21',11,27.15),(53,'2023-04-22',11,5.03),(54,'2023-04-23',11,15.15),(55,'2023-04-24',11,9.22),(56,'2023-04-20',12,12.2),(57,'2023-04-21',12,25.15),(58,'2023-04-22',12,5.03),(59,'2023-04-23',12,15.15),(60,'2023-04-24',12,9.22),(61,'2023-04-20',13,12.2),(62,'2023-04-21',13,22.15),(63,'2023-04-22',13,5.03),(64,'2023-04-23',13,15.15),(65,'2023-04-24',13,9.22),(66,'2023-04-20',14,12.2),(67,'2023-04-21',14,22.15),(68,'2023-04-22',14,5.03),(69,'2023-04-23',14,15.15),(70,'2023-04-24',14,9.22),(71,'2023-04-20',15,12.2),(72,'2023-04-21',15,22.15),(73,'2023-04-22',15,5.03),(74,'2023-04-23',15,15.15),(75,'2023-04-24',15,9.22),(76,'2023-04-20',16,12.2),(77,'2023-04-21',16,22.15),(78,'2023-04-22',16,5.03),(79,'2023-04-23',16,15.15),(80,'2023-04-24',16,9.22),(81,'2023-04-20',17,12.2),(82,'2023-04-21',17,24.15),(83,'2023-04-22',17,5.03),(84,'2023-04-23',17,15.15),(85,'2023-04-24',17,9.22),(86,'2023-04-20',18,12.2),(87,'2023-04-21',18,22.15),(88,'2023-04-22',18,5.03),(89,'2023-04-23',18,15.15),(90,'2023-04-24',18,9.22),(91,'2023-04-20',19,12.2),(92,'2023-04-21',19,22.15),(93,'2023-04-22',19,5.03),(94,'2023-04-23',19,15.15),(95,'2023-04-24',19,9.22),(96,'2023-04-20',20,12.2),(97,'2023-04-21',20,22.15),(98,'2023-04-22',20,5.03),(99,'2023-04-23',20,15.15),(100,'2023-04-24',20,9.22);
/*!40000 ALTER TABLE `mfdetailhistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mfdetails`
--

DROP TABLE IF EXISTS `mfdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mfdetails` (
  `schema_id` int NOT NULL AUTO_INCREMENT,
  `fund_house` varchar(255) NOT NULL,
  `schema_name` varchar(255) NOT NULL,
  `schema_category` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `curr_price` double NOT NULL,
  `delta` double NOT NULL,
  `symbol` varchar(255) NOT NULL,
  PRIMARY KEY (`schema_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mfdetails`
--

LOCK TABLES `mfdetails` WRITE;
/*!40000 ALTER TABLE `mfdetails` DISABLE KEYS */;
INSERT INTO `mfdetails` VALUES (1,'Regular Mutual Funds','Growth Fund','Equity','A growth-oriented mutual fund',23.45,2.34,'REGLR'),(2,'Extreme Asset Management','Bond Fund','Fixed Income','A fixed-income mutual fund',10.56,0.89,'EXTRM'),(3,'Apple Fund','Small Cap Fund','Equity','A small-cap equity mutual fund',15.67,-1.23,'APPLE'),(4,'Google Investments','International Fund','Equity','An international equity mutual fund',21.98,-1.56,'GOOGLE'),(5,'Netscape Asset Management','Balanced Fund','Hybrid','A balanced mutual fund with mix of equity and debt',25.34,-1.98,'NTSCPE'),(6,'Chrome Mutual Funds','Sector Fund','Equity','A sector-specific mutual fund',18.76,1.45,'CHROME'),(7,'Nexopia Investments','Mid Cap Fund','Equity','A mid-cap equity mutual fund',13.78,-1.01,'NEXOP'),(8,'DuckDuckGo Asset Management','Income Fund','Fixed Income','A fixed-income mutual fund',12.34,-0.67,'DUCK'),(9,'Reliance Funds','Large Cap Fund','Debt','A large-cap debt mutual fund',20.45,1.67,'RLNCE'),(10,'ABC Mutual Funds','Index Fund','Debt','An debt mutual fund that tracks a market index',17.89,1.34,'ABCDE'),(11,'Tata Mutual Funds','Sector Fund','Equity','A sector-specific mutual fund',18.76,1.45,'TATA'),(12,'Nvidia Investments','Mid Cap Fund','Equity','A mid-cap equity mutual fund',13.78,-1.01,'NVIDIA'),(13,'ICBC Funds','Income Fund','Fixed Income','A fixed-income mutual fund',12.34,-0.67,'ICBC'),(14,'Intel Funds','Large Cap Fund','Equity','A large-cap equity mutual fund',20.45,1.67,'INTEL'),(15,'Myspace Mutual Funds','Sector Fund','Debt','A sector-specific mutual fund',18.76,1.45,'MYSPACE'),(16,'EDGE Investments','Mid Cap Fund','Equity','A mid-cap equity mutual fund',13.78,-1.01,'EDGE'),(17,'Samsung Fund','Income Fund','Fixed Income','A fixed-income mutual fund',12.34,-0.67,'SAM'),(18,'HCL Funds','Large Cap Fund','Hybrid','A large-cap hybrid mutual fund',55.45,2.67,'HCL'),(19,'HIJ Mutual Funds','Index Fund','Hybrid','An hybrid mutual fund that tracks a market index',17.89,1.34,'HIJINDX'),(20,'FUN Fund','Mid Cap Fund','Balanced Fund','Extremely cautious mid cap mutual fund',31.36,2,'FUN');
/*!40000 ALTER TABLE `mfdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portfolio`
--

DROP TABLE IF EXISTS `portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `portfolio` (
  `portfolio_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` bigint DEFAULT NULL,
  `mf_name` varchar(64) DEFAULT NULL,
  `mf_fund_house` varchar(64) NOT NULL,
  `mf_units_available` int DEFAULT '0',
  `currency` varchar(64) NOT NULL,
  `transaction_date` date DEFAULT NULL,
  `schemaidfk` int DEFAULT NULL,
  PRIMARY KEY (`portfolio_id`),
  KEY `customer_id` (`customer_id`),
  KEY `FKjhuohmbrnysxtktn7xtqmgt68` (`schemaidfk`),
  CONSTRAINT `FKjhuohmbrnysxtktn7xtqmgt68` FOREIGN KEY (`schemaidfk`) REFERENCES `mfdetails` (`schema_id`),
  CONSTRAINT `portfolio_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portfolio`
--

LOCK TABLES `portfolio` WRITE;
/*!40000 ALTER TABLE `portfolio` DISABLE KEYS */;
INSERT INTO `portfolio` VALUES (252,46,'Growth Fund','Regular Mutual Funds',1,'REGLR','2024-01-10',1);
/*!40000 ALTER TABLE `portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portfolio_seq`
--

DROP TABLE IF EXISTS `portfolio_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `portfolio_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portfolio_seq`
--

LOCK TABLES `portfolio_seq` WRITE;
/*!40000 ALTER TABLE `portfolio_seq` DISABLE KEYS */;
INSERT INTO `portfolio_seq` VALUES (351);
/*!40000 ALTER TABLE `portfolio_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_id` int NOT NULL,
  `description` varchar(64) NOT NULL,
  `wallet_id` int NOT NULL,
  `customer_id` bigint NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `wallet_id` (`wallet_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`wallet_id`) REFERENCES `wallet` (`id`),
  CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (56,1,'Deposit',18,46,2222.00,'2024-01-10'),(57,2,'Withdrawal',18,46,70.35,'2024-01-10'),(58,1,'Deposit',18,46,46.90,'2024-01-10');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet`
--

DROP TABLE IF EXISTS `wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `balance` decimal(10,2) NOT NULL DEFAULT '0.00',
  `currency_symbol` varchar(10) DEFAULT 'CA$',
  `currency_type` varchar(64) DEFAULT 'CAD',
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `wallet_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet`
--

LOCK TABLES `wallet` WRITE;
/*!40000 ALTER TABLE `wallet` DISABLE KEYS */;
INSERT INTO `wallet` VALUES (18,46,2198.55,'CA$','CAD');
/*!40000 ALTER TABLE `wallet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist`
--

DROP TABLE IF EXISTS `wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist` (
  `wishlist_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `mf_name` varchar(64) NOT NULL,
  `mf_fund_house` varchar(64) NOT NULL,
  `mf_schema_id` int NOT NULL,
  PRIMARY KEY (`wishlist_id`),
  KEY `customer_id` (`customer_id`),
  KEY `wishlist_ibfk_2` (`mf_schema_id`),
  CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `wishlist_ibfk_2` FOREIGN KEY (`mf_schema_id`) REFERENCES `mfdetails` (`schema_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist`
--

LOCK TABLES `wishlist` WRITE;
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
INSERT INTO `wishlist` VALUES (15,46,'Growth Fund','Regular Mutual Funds',1);
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-10 18:10:35
