-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: project_7
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `bidlist`
--

DROP TABLE IF EXISTS `bidlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bidlist` (
  `bid_list_id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `ask` double DEFAULT NULL,
  `ask_quantity` double DEFAULT NULL,
  `benchmark` varchar(255) DEFAULT NULL,
  `bid` double DEFAULT NULL,
  `bid_list_date` datetime DEFAULT NULL,
  `bid_quantity` double DEFAULT NULL,
  `book` varchar(255) DEFAULT NULL,
  `commentary` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `creation_name` varchar(255) DEFAULT NULL,
  `deal_name` varchar(255) DEFAULT NULL,
  `deal_type` varchar(255) DEFAULT NULL,
  `revision_date` datetime DEFAULT NULL,
  `revision_name` varchar(255) DEFAULT NULL,
  `security` varchar(255) DEFAULT NULL,
  `side` varchar(255) DEFAULT NULL,
  `source_list_id` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `trader` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bid_list_id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-16 16:28:12
