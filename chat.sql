CREATE DATABASE  IF NOT EXISTS `chat` 
/*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `chat`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: chat
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `isOnline` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'ad','ad',0),(2,'admin','admin',0),(3,'asd','123',0),(4,'cuong','123',0),(5,'ez','123',0),(6,'gg','123',0),(7,'hieu','123',0),(8,'ndc','123',0),(9,'xyz','123',0),(10,'new','123',0);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filesharing`
--

DROP TABLE IF EXISTS `filesharing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `filesharing` (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(45) NOT NULL,
  `sender` int NOT NULL,
  `receiver` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sender_idx` (`sender`),
  KEY `fk_receiver_idx` (`receiver`),
  CONSTRAINT `fk_receiver` FOREIGN KEY (`receiver`) REFERENCES `account` (`id`),
  CONSTRAINT `fk_sender` FOREIGN KEY (`sender`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filesharing`
--

LOCK TABLES `filesharing` WRITE;
/*!40000 ALTER TABLE `filesharing` DISABLE KEYS */;
/*!40000 ALTER TABLE `filesharing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friendrequest`
--

DROP TABLE IF EXISTS `friendrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friendrequest` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fromuser` int NOT NULL,
  `touser` int NOT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_fromuser_idx` (`fromuser`),
  KEY `fk_touser_idx` (`touser`),
  CONSTRAINT `fk_fromuser` FOREIGN KEY (`fromuser`) REFERENCES `account` (`id`),
  CONSTRAINT `fk_touser` FOREIGN KEY (`touser`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friendrequest`
--

LOCK TABLES `friendrequest` WRITE;
/*!40000 ALTER TABLE `friendrequest` DISABLE KEYS */;
INSERT INTO `friendrequest` VALUES (1,2,3,0),(2,2,4,1),(3,2,5,1),(4,3,4,0),(5,1,3,0),(7,10,1,1),(8,6,2,1),(9,1,8,1),(10,6,1,1),(11,6,5,1);
/*!40000 ALTER TABLE `friendrequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friendship`
--

DROP TABLE IF EXISTS `friendship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friendship` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user1` int NOT NULL,
  `user2` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user1_idx` (`user1`),
  KEY `fk_user2_idx` (`user2`),
  CONSTRAINT `fk_user1` FOREIGN KEY (`user1`) REFERENCES `account` (`id`),
  CONSTRAINT `fk_user2` FOREIGN KEY (`user2`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `chatlog`;
CREATE TABLE `chatlog` (
  `id_chat_log` int NOT NULL AUTO_INCREMENT,
  `sender` varchar(45) DEFAULT NULL,
  `receiver` varchar(45) DEFAULT NULL,
  `mess` varchar(255) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id_chat_log`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `friendship`
--

LOCK TABLES `friendship` WRITE;
/*!40000 ALTER TABLE `friendship` DISABLE KEYS */;
INSERT INTO `friendship` VALUES (1,1,2),(2,2,1),(3,3,1),(4,1,3),(5,1,4),(6,4,1),(7,1,5),(8,5,1),(19,1,8),(20,8,1),(21,6,1),(22,1,6),(23,6,5),(24,5,6);
/*!40000 ALTER TABLE `friendship` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-21 18:15:18
