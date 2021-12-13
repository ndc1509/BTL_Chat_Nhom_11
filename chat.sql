CREATE DATABASE  IF NOT EXISTS `chat` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (0,'SERVER','123',0),(1,'ad','ad',0),(2,'admin','admin',0),(3,'asd','123',0),(4,'cuong','123',0),(5,'ez','123',0),(6,'gg','123',0),(7,'hieu','123',0),(8,'ndc','123',0),(9,'xyz','123',0),(10,'new','123',0),(11,'acc1','123',0);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatlog`
--

DROP TABLE IF EXISTS `chatlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatlog` (
  `id_chat_log` int NOT NULL AUTO_INCREMENT,
  `sender` int DEFAULT NULL,
  `receiver` int DEFAULT NULL,
  `mess` varchar(255) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `room` int DEFAULT NULL,
  PRIMARY KEY (`id_chat_log`),
  KEY `send_idx` (`sender`),
  KEY `receive_idx` (`receiver`),
  KEY `room_idx` (`room`),
  CONSTRAINT `receive` FOREIGN KEY (`receiver`) REFERENCES `account` (`id`),
  CONSTRAINT `room` FOREIGN KEY (`room`) REFERENCES `room` (`room_id`),
  CONSTRAINT `send` FOREIGN KEY (`sender`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatlog`
--

LOCK TABLES `chatlog` WRITE;
/*!40000 ALTER TABLE `chatlog` DISABLE KEYS */;
INSERT INTO `chatlog` VALUES (1,8,1,'hi','2021-12-12 16:58:15',NULL),(3,8,1,'ok','2021-12-12 17:17:03',NULL),(4,1,8,'ok','2021-12-12 17:25:25',NULL),(5,1,8,'hmm','2021-12-12 17:25:27',NULL),(6,1,8,'dc roi','2021-12-12 17:25:30',NULL),(7,8,1,'123','2021-12-12 19:40:10',NULL),(11,1,8,'hmm','2021-12-12 22:51:36',NULL),(12,1,8,'dc r','2021-12-12 22:51:39',NULL),(13,1,NULL,'hi','2021-12-12 23:09:50',1),(14,1,8,'fine','2021-12-12 23:14:52',NULL),(15,1,NULL,'ok','2021-12-12 23:15:21',1),(16,1,NULL,'hgasdfg','2021-12-12 23:15:25',1),(17,8,NULL,'hmm','2021-12-12 23:16:29',1),(18,8,NULL,'yo','2021-12-12 23:16:33',1),(19,1,NULL,'123','2021-12-12 23:17:03',1),(20,8,NULL,'gg','2021-12-12 23:17:13',1),(21,1,NULL,'123','2021-12-12 23:17:19',1),(22,8,NULL,'123','2021-12-12 23:17:24',1),(23,8,NULL,'hi','2021-12-12 23:18:13',9),(24,1,NULL,'hi','2021-12-12 23:18:16',9),(25,8,NULL,'?','2021-12-12 23:18:19',9),(26,1,NULL,'?','2021-12-12 23:18:47',9),(27,1,NULL,'hmm','2021-12-13 14:59:33',1),(28,1,NULL,'yo','2021-12-13 14:59:39',1),(29,8,NULL,'123','2021-12-13 14:59:43',1),(30,8,NULL,'hi','2021-12-13 15:51:14',2),(31,1,NULL,'chao ong','2021-12-13 15:51:18',2),(32,0,NULL,'admin đã được thêm vào phòng bởi ad','2021-12-13 16:24:07',1),(33,8,NULL,'hi','2021-12-13 16:56:14',1),(34,8,NULL,'?','2021-12-13 16:56:16',1),(35,6,NULL,'123','2021-12-13 16:56:19',1),(36,6,NULL,'312','2021-12-13 16:56:27',1),(37,8,NULL,'123','2021-12-13 16:56:57',1),(38,2,NULL,'hmm','2021-12-13 17:02:12',1),(39,6,NULL,'alo','2021-12-13 17:02:17',1),(40,6,NULL,'123','2021-12-13 17:02:19',1),(41,2,NULL,'456','2021-12-13 17:02:22',1),(42,1,NULL,'789','2021-12-13 17:02:44',1),(43,2,NULL,'10','2021-12-13 17:02:51',1),(44,2,NULL,'11','2021-12-13 17:03:01',1),(45,6,NULL,'a','2021-12-13 17:03:59',1),(46,2,NULL,'b','2021-12-13 17:04:02',1),(47,6,NULL,'c','2021-12-13 17:04:04',1),(48,2,NULL,'d','2021-12-13 17:04:06',1),(49,1,NULL,'e','2021-12-13 17:04:42',1),(50,6,NULL,'f','2021-12-13 17:04:45',1),(51,6,NULL,'g','2021-12-13 17:04:51',1),(52,8,NULL,'hi','2021-12-13 17:13:57',1),(53,1,NULL,'?','2021-12-13 17:13:59',1),(54,1,NULL,'1','2021-12-13 17:14:48',1),(55,8,NULL,'2','2021-12-13 17:14:50',1),(56,1,NULL,'3','2021-12-13 17:14:52',1),(57,8,NULL,'4','2021-12-13 17:14:54',1),(58,1,NULL,'5','2021-12-13 17:19:56',1),(59,8,NULL,'6','2021-12-13 17:19:58',1),(60,8,NULL,'hi','2021-12-13 17:51:54',1),(61,1,NULL,'hi','2021-12-13 17:52:04',1),(62,1,NULL,'1','2021-12-13 17:54:10',1),(63,8,NULL,'1','2021-12-13 17:54:16',1),(64,1,NULL,'2','2021-12-13 17:58:10',1),(65,8,NULL,'3','2021-12-13 17:58:21',1),(66,1,NULL,'4','2021-12-13 17:59:59',1),(67,8,NULL,'5','2021-12-13 18:00:01',1),(68,1,NULL,'gg','2021-12-13 18:01:14',1),(69,1,NULL,'6pm','2021-12-13 18:06:03',1),(70,8,NULL,'6pm','2021-12-13 18:06:11',1),(71,8,NULL,'432','2021-12-13 18:27:27',1),(72,1,NULL,'234','2021-12-13 18:27:30',1),(73,8,NULL,'4321','2021-12-13 18:27:50',1),(74,1,NULL,'1234','2021-12-13 18:27:54',1),(75,1,NULL,'zo','2021-12-13 18:28:25',1),(76,8,NULL,'ok','2021-12-13 18:28:37',1),(77,8,NULL,'1','2021-12-13 18:31:01',1),(78,1,NULL,'2','2021-12-13 18:31:03',1),(79,1,NULL,'3','2021-12-13 18:31:34',1),(80,8,NULL,'4','2021-12-13 18:31:37',1),(81,8,NULL,'5','2021-12-13 18:31:39',1),(82,1,NULL,'6','2021-12-13 18:31:41',1),(83,1,NULL,'7','2021-12-13 18:32:08',1),(84,8,NULL,'8','2021-12-13 18:32:10',1),(85,8,NULL,'9','2021-12-13 18:32:12',1),(86,1,NULL,'10','2021-12-13 18:32:14',1);
/*!40000 ALTER TABLE `chatlog` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filesharing`
--

LOCK TABLES `filesharing` WRITE;
/*!40000 ALTER TABLE `filesharing` DISABLE KEYS */;
INSERT INTO `filesharing` VALUES (4,'pwd.txt',8,1);
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
  `message` varchar(255) DEFAULT NULL,
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
INSERT INTO `friendrequest` VALUES (1,2,3,0,NULL),(2,2,4,1,NULL),(3,2,5,1,NULL),(4,3,4,0,NULL),(5,1,3,0,NULL),(7,10,1,1,NULL),(8,6,2,1,NULL),(9,1,8,1,NULL),(10,6,1,1,NULL),(11,6,5,1,NULL);
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

--
-- Dumping data for table `friendship`
--

LOCK TABLES `friendship` WRITE;
/*!40000 ALTER TABLE `friendship` DISABLE KEYS */;
INSERT INTO `friendship` VALUES (1,1,2),(2,2,1),(3,3,1),(4,1,3),(5,1,4),(6,4,1),(7,1,5),(8,5,1),(19,1,8),(20,8,1),(21,6,1),(22,1,6),(23,6,5),(24,5,6);
/*!40000 ALTER TABLE `friendship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `room_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `creator` int NOT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'test',1),(2,'test2',2),(6,'test3',1),(7,'test4',1),(8,'test5',1),(9,'test3',8);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_member`
--

DROP TABLE IF EXISTS `room_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_member` (
  `key` int NOT NULL AUTO_INCREMENT,
  `r_id` int NOT NULL,
  `member` int NOT NULL,
  PRIMARY KEY (`key`),
  KEY `fk_room_id_idx` (`r_id`),
  CONSTRAINT `fk_room_id` FOREIGN KEY (`r_id`) REFERENCES `room` (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_member`
--

LOCK TABLES `room_member` WRITE;
/*!40000 ALTER TABLE `room_member` DISABLE KEYS */;
INSERT INTO `room_member` VALUES (1,1,1),(2,2,2),(3,2,1),(4,6,1),(5,1,6),(7,1,8),(8,2,8),(12,1,2);
/*!40000 ALTER TABLE `room_member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-13 21:18:18
