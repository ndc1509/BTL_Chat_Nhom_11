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
  `mess` varchar(1000) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `room` int DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_chat_log`),
  KEY `send_idx` (`sender`),
  KEY `receive_idx` (`receiver`),
  KEY `room_idx` (`room`),
  CONSTRAINT `receive` FOREIGN KEY (`receiver`) REFERENCES `account` (`id`),
  CONSTRAINT `room` FOREIGN KEY (`room`) REFERENCES `room` (`room_id`),
  CONSTRAINT `send` FOREIGN KEY (`sender`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=278 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatlog`
--

LOCK TABLES `chatlog` WRITE;
/*!40000 ALTER TABLE `chatlog` DISABLE KEYS */;
INSERT INTO `chatlog` VALUES (187,1,NULL,'upwk61813380-wikimedia-image.jpg','2021-12-16 18:59:36',1,'img'),(188,8,NULL,'grinning-face_1f600.png','2021-12-16 19:01:05',1,'emoji'),(189,8,NULL,'grinning-face_1f600.png','2021-12-16 19:01:07',1,'emoji'),(190,1,NULL,'grinning-face_1f600.png','2021-12-16 19:01:36',1,'emoji'),(191,8,NULL,'hi','2021-12-16 19:02:43',1,'message'),(192,1,NULL,'hinh-dep-1.jpg','2021-12-16 19:05:33',1,'img'),(193,8,NULL,'ad.PNG','2021-12-16 19:09:53',1,'img'),(194,1,NULL,'ad2.PNG','2021-12-16 19:15:03',1,'img'),(195,6,NULL,'hi','2021-12-16 19:27:10',1,'message'),(196,6,NULL,'yô','2021-12-16 19:28:28',1,'message'),(197,1,NULL,'731ab52a-44c1-45f0-9b6f-2c7143c7c72a.png','2021-12-16 19:28:37',1,'img'),(198,1,NULL,'image.png','2021-12-16 19:56:51',1,'img'),(199,6,NULL,'tree-736885__480.jpg','2021-12-16 19:57:39',1,'img'),(200,1,NULL,'ad.PNG','2021-12-16 20:52:53',1,'img'),(244,1,NULL,'=D','2021-12-16 23:11:39',1,'message'),(245,1,NULL,'123','2021-12-16 23:14:26',1,'message'),(246,1,NULL,'=D','2021-12-16 23:14:30',1,'message'),(247,1,NULL,'123456 :O 654321','2021-12-16 23:14:41',1,'message'),(248,1,NULL,'hi','2021-12-16 23:15:06',1,'message'),(249,1,NULL,'wow :O','2021-12-16 23:15:54',1,'message'),(250,1,NULL,'hi','2021-12-16 23:18:43',1,'message'),(251,1,NULL,'=D','2021-12-16 23:18:47',1,'message'),(252,1,NULL,'123 =D','2021-12-16 23:21:11',1,'message'),(253,1,NULL,'=D =D =D','2021-12-16 23:21:20',1,'message'),(254,1,NULL,'=)D','2021-12-16 23:29:59',1,'message'),(255,1,NULL,'=DDD','2021-12-16 23:30:02',1,'message'),(256,1,NULL,'871q63 lsdf=D','2021-12-16 23:30:08',1,'message'),(257,1,NULL,'=:D =D','2021-12-16 23:30:31',1,'message'),(258,1,NULL,'hi','2021-12-16 23:34:53',1,'message'),(259,1,NULL,'72534asfd =D','2021-12-16 23:35:00',1,'message'),(260,1,NULL,'gg :(','2021-12-16 23:35:53',1,'message'),(261,1,NULL,'=D','2021-12-16 23:35:55',1,'message'),(262,1,NULL,'well','2021-12-16 23:36:08',1,'message'),(263,1,NULL,'123','2021-12-16 23:36:13',1,'message'),(264,1,NULL,':P','2021-12-16 23:36:22',1,'message'),(265,1,NULL,':O','2021-12-16 23:36:26',1,'message'),(266,1,NULL,'smiling-face-with-heart-eyes_1f60d.png','2021-12-16 23:36:56',1,'emoji'),(267,1,NULL,'1234 431 =))','2021-12-16 23:37:19',1,'message'),(268,1,NULL,'hello mai fen','2021-12-16 23:39:33',1,'message'),(269,1,NULL,'=D','2021-12-16 23:39:35',1,'message'),(270,1,NULL,'123 @@','2021-12-16 23:39:42',1,'message'),(271,1,NULL,'=D','2021-12-16 23:39:44',1,'message'),(272,1,NULL,'hasdf :O','2021-12-16 23:39:50',1,'message'),(273,1,NULL,'1','2021-12-16 23:39:54',1,'message'),(274,1,NULL,'hi','2021-12-16 23:42:11',1,'message'),(275,1,NULL,'=)','2021-12-16 23:45:08',1,'message'),(276,1,NULL,'osdf :P','2021-12-16 23:45:14',1,'message'),(277,1,NULL,':O :P','2021-12-16 23:45:18',1,'message');
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
  `sender` int DEFAULT NULL,
  `receiver` int DEFAULT NULL,
  `room` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sender_idx` (`sender`),
  KEY `fk_receiver_idx` (`receiver`),
  KEY `fk_room_idx` (`room`),
  CONSTRAINT `fk_receiver` FOREIGN KEY (`receiver`) REFERENCES `account` (`id`),
  CONSTRAINT `fk_room` FOREIGN KEY (`room`) REFERENCES `room` (`room_id`),
  CONSTRAINT `fk_sender` FOREIGN KEY (`sender`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filesharing`
--

LOCK TABLES `filesharing` WRITE;
/*!40000 ALTER TABLE `filesharing` DISABLE KEYS */;
INSERT INTO `filesharing` VALUES (5,'jdbc.sql',1,NULL,1),(6,'pwd.txt',8,1,NULL),(7,'pwd.txt',1,5,NULL),(8,'jdbc.sql',1,NULL,1),(9,'image.png',8,NULL,1),(10,'image.png',1,NULL,1),(11,'hinh-dep-1.jpg',8,NULL,1),(12,'upwk61813380-wikimedia-image.jpg',8,NULL,1),(13,'tree-736885__480.jpg',8,NULL,1),(14,'731ab52a-44c1-45f0-9b6f-2c7143c7c72a.png',8,NULL,1),(15,'tree-736885__480.jpg',8,NULL,1),(16,'upwk61813380-wikimedia-image.jpg',1,NULL,1),(17,'hinh-dep-1.jpg',1,NULL,1),(18,'images.jpeg',8,NULL,1),(19,'hinh-dep-1.jpg',8,NULL,1),(20,'tree-736885__480.jpg',6,NULL,1),(21,'hinh-dep-1.jpg',1,NULL,1),(22,'upwk61813380-wikimedia-image.jpg',8,NULL,1),(23,'hinh-dep-1.jpg',1,NULL,1),(24,'image.png',1,NULL,1),(25,'731ab52a-44c1-45f0-9b6f-2c7143c7c72a.png',6,NULL,1),(26,'hinh-dep-1.jpg',8,NULL,1),(27,'images.jpeg',8,NULL,1),(28,'upwk61813380-wikimedia-image.jpg',6,NULL,1),(29,'images.jpeg',1,NULL,1),(30,'ad.PNG',1,NULL,1),(31,'731ab52a-44c1-45f0-9b6f-2c7143c7c72a.png',8,NULL,1),(32,'731ab52a-44c1-45f0-9b6f-2c7143c7c72a.png',6,NULL,1),(33,'ad4.PNG',6,NULL,1),(34,'f.png',1,NULL,1),(35,'ad4.PNG',1,NULL,1),(36,'SRS Model.PNG',1,NULL,1),(37,'image.png',6,NULL,1),(38,'731ab52a-44c1-45f0-9b6f-2c7143c7c72a.png',1,NULL,1),(39,'upwk61813380-wikimedia-image.jpg',1,NULL,1),(40,'hinh-dep-1.jpg',1,NULL,1),(41,'ad.PNG',8,NULL,1),(42,'ad2.PNG',1,NULL,1),(43,'731ab52a-44c1-45f0-9b6f-2c7143c7c72a.png',1,NULL,1),(44,'image.png',1,NULL,1),(45,'tree-736885__480.jpg',6,NULL,1),(46,'ad.PNG',1,NULL,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_member`
--

LOCK TABLES `room_member` WRITE;
/*!40000 ALTER TABLE `room_member` DISABLE KEYS */;
INSERT INTO `room_member` VALUES (1,1,1),(2,2,2),(3,2,1),(4,6,1),(5,1,6),(7,1,8),(8,2,8);
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

-- Dump completed on 2021-12-17 14:59:51
