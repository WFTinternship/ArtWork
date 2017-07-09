CREATE DATABASE  IF NOT EXISTS `wft` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `wft`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: wft
-- ------------------------------------------------------
-- Server version	5.7.16-log

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
-- Table structure for table `artist`
--

DROP TABLE IF EXISTS `artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artist` (
  `user_id` int(11) NOT NULL,
  `spec_id` int(20) NOT NULL,
  `photo` blob NOT NULL,
  UNIQUE KEY `user_id` (`user_id`),
  KEY `artist_ibfk_2_idx` (`spec_id`),
  CONSTRAINT `artist_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `artist_ibfk_2` FOREIGN KEY (`spec_id`) REFERENCES `artist_specialization_lkp` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist`
--

LOCK TABLES `artist` WRITE;
/*!40000 ALTER TABLE `artist` DISABLE KEYS */;
INSERT INTO `artist` VALUES (1,3,'12321321'),(2,1,'12321321'),(3,4,'12321321'),(5,3,'12321321'),(46,1,'\0\0\0\0\0\0\0\0\0\0'),(48,1,'\0\0\0\0\0\0\0\0\0\0'),(50,1,'\0\0\0\0\0\0\0\0\0\0'),(52,1,'\0\0\0\0\0\0\0\0\0\0'),(54,1,'\0\0\0\0\0\0\0\0\0\0'),(56,1,'\0\0\0\0\0\0\0\0\0\0'),(58,1,'\0\0\0\0\0\0\0\0\0\0'),(60,1,'\0\0\0\0\0\0\0\0\0\0'),(157,1,'\0\0\0\0\0\0\0\0\0\0'),(159,1,'\0\0\0\0\0\0\0\0\0\0'),(161,1,'\0\0\0\0\0\0\0\0\0\0'),(163,1,'\0\0\0\0\0\0\0\0\0\0'),(165,1,'\0\0\0\0\0\0\0\0\0\0'),(167,1,'\0\0\0\0\0\0\0\0\0\0'),(169,1,'\0\0\0\0\0\0\0\0\0\0'),(171,1,'\0\0\0\0\0\0\0\0\0\0'),(217,1,'\0\0\0\0\0\0\0\0\0\0'),(219,1,'\0\0\0\0\0\0\0\0\0\0'),(221,1,'\0\0\0\0\0\0\0\0\0\0'),(223,1,'\0\0\0\0\0\0\0\0\0\0'),(225,1,'\0\0\0\0\0\0\0\0\0\0'),(227,1,'\0\0\0\0\0\0\0\0\0\0'),(229,1,'\0\0\0\0\0\0\0\0\0\0'),(231,1,'\0\0\0\0\0\0\0\0\0\0'),(277,1,'\0\0\0\0\0\0\0\0\0\0'),(279,1,'\0\0\0\0\0\0\0\0\0\0'),(281,1,'\0\0\0\0\0\0\0\0\0\0'),(283,1,'\0\0\0\0\0\0\0\0\0\0'),(285,1,'\0\0\0\0\0\0\0\0\0\0'),(287,1,'\0\0\0\0\0\0\0\0\0\0'),(289,1,'\0\0\0\0\0\0\0\0\0\0'),(291,1,'\0\0\0\0\0\0\0\0\0\0');
/*!40000 ALTER TABLE `artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artist_specialization_lkp`
--

DROP TABLE IF EXISTS `artist_specialization_lkp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artist_specialization_lkp` (
  `id` int(11) NOT NULL,
  `spec_type` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist_specialization_lkp`
--

LOCK TABLES `artist_specialization_lkp` WRITE;
/*!40000 ALTER TABLE `artist_specialization_lkp` DISABLE KEYS */;
INSERT INTO `artist_specialization_lkp` VALUES (1,'PAINTER'),(2,'SCULPTOR'),(3,'PHOTOGRAPHER'),(4,'OTHER');
/*!40000 ALTER TABLE `artist_specialization_lkp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) NOT NULL,
  `description` varchar(60) DEFAULT NULL,
  `photo_url` varchar(60) NOT NULL,
  `price` double NOT NULL,
  `artist_id` int(11) NOT NULL,
  `status` tinyint(4) unsigned NOT NULL,
  `type` varchar(60) NOT NULL,
  `addition_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `artist_id` (`artist_id`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'Title 1','Test Desc 1','../../resources/images/product/images (1).jpg',100,1,0,'SCULPTURE','2010-05-05 00:00:00'),(2,'Title 2','Test Desc 2','../../resources/images/product/images (2).jpg',250,1,0,'PHOTOGRAPHY','2011-05-05 00:00:00'),(3,'Title 3','Test Desc 3','../../resources/images/product/images (3).jpg',450,1,1,'PAINTING','2012-05-05 00:00:00'),(4,'Title 4','Test Desc 4','../../resources/images/product/images (4).jpg',50,1,0,'OTHER','2013-05-05 00:00:00'),(5,'Title 5','Test Desc 5','../../resources/images/product/images (5).jpg',180,2,0,'PHOTOGRAPHY','2014-05-05 00:00:00'),(6,'Title 6','Test Desc 6','../../resources/images/product/images (6).jpg',1000,2,1,'SCULPTURE','2015-05-05 00:00:00'),(7,'Title 7','Test Desc 7','../../resources/images/product/images (7).jpg',300,3,0,'PAINTING','2016-05-05 00:00:00'),(8,'Title 8','Test Desc 8','../../resources/images/product/images (8).jpg',560,5,0,'OTHER','2017-05-05 00:00:00'),(9,'Title 9','Test Desc 9','../../resources/images/product/images (9).jpg',1400,5,1,'OTHER','2017-07-07 00:00:00'),(10,'Title 10','Test Desc 10','../../resources/images/product/images (10).jpg',75,5,0,'PAINTING','2017-10-10 00:00:00');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_history`
--

DROP TABLE IF EXISTS `purchase_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_history` (
  `user_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `purchase_date` datetime NOT NULL,
  KEY `purchase_history_ibfk_1` (`user_id`),
  KEY `purchase_history_ibfk_2` (`item_id`),
  CONSTRAINT `purchase_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `purchase_history_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_history`
--

LOCK TABLES `purchase_history` WRITE;
/*!40000 ALTER TABLE `purchase_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopping_card`
--

DROP TABLE IF EXISTS `shopping_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shopping_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `balance` double NOT NULL,
  `buyer_id` int(11) NOT NULL,
  `type` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `buyer_id` (`buyer_id`),
  CONSTRAINT `shopping_card_ibfk_1` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopping_card`
--

LOCK TABLES `shopping_card` WRITE;
/*!40000 ALTER TABLE `shopping_card` DISABLE KEYS */;
INSERT INTO `shopping_card` VALUES (1,1000,1,''),(2,100000,2,''),(3,500,3,''),(4,0,4,''),(5,100,5,''),(6,30,6,'');
/*!40000 ALTER TABLE `shopping_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `age` int(11) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=309 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Adolf','Hitler',25,'adolf.hitler@gmail.com','hiHitler'),(2,'Iosif','Stalin',29,'iosif.stalin@gmail.com','stalinigrad'),(3,'Valod','Valodikyan',31,'valodik.valodikyan@gmail.com','valodik31'),(4,'Hamlet','Grigoryan',40,'hamlet.grigoryan@gmail.com','hamo40'),(5,'Testik','Testikyan',63,'test.testikyan@gmail.com','test1234'),(6,'Will','Smith',51,'will.smith@gmail.com','smith51'),(7,'cs','ert',34,'vb@mail.ru','test1234'),(8,'vah','gaha',34,'vah@mail','34r'),(9,'va','ba',34,'vb@dat.mail','dede'),(10,'paho','pahoik',23,'paho@mail.ru','test1234'),(11,'sdgs','gdfsgsd',45,'VAHSDS@HMAI','GSDHS'),(12,'vahana','bakaryan',34,'vahan.bakaryanasas@gmail.com','asjdgjasd'),(13,'poghos ','kirakosyan',57,'poghos.kirakos@mail.ru','test1234'),(14,'vahan','bakaryan',56,'har@gmail.com','test1234'),(15,'pppp','hhhhh',23,'vahdddd@mail','44444'),(16,'vahan','bakaryan',45,'vaha@mail.ru','vahan'),(17,'Test FirstName77837','Test LastName',26,'test59653@test.com','testPassword'),(46,'Test FirstName16605','Test LastName64839',26,'test82521@test.com','test123'),(48,'Test FirstName52006','Test LastName67972',26,'test32582@test.com','test123'),(50,'Test FirstName60267','Test LastName54337',26,'test89576@test.com','test123'),(52,'Test FirstName12593','Test LastName77370',26,'test38170@test.com','test123'),(54,'Test FirstName5697','Test LastName94725',26,'test50924@test.com','test123'),(56,'Test FirstName24368','Test LastName63835',26,'test23938@test.com','test123'),(58,'Test FirstName73604','Test LastName74920',26,'test77372@test.com','test123'),(60,'Test FirstName4048','Test LastName5419',26,'test67613@test.com','test123'),(157,'Test FirstName72005','Test LastName41556',26,'test19004@test.com','test123'),(159,'Test FirstName99990','Test LastName1984',26,'test46587@test.com','test123'),(161,'Test FirstName4886','Test LastName39645',26,'test24061@test.com','test123'),(163,'Test FirstName33847','Test LastName95227',26,'test5510@test.com','test123'),(165,'Test FirstName37289','Test LastName34141',26,'test9561@test.com','test123'),(167,'Test FirstName85852','Test LastName38188',26,'test27628@test.com','test123'),(169,'Test FirstName49269','Test LastName69226',26,'test36130@test.com','test123'),(171,'Test FirstName11290','Test LastName48316',26,'test97466@test.com','test123'),(217,'Test FirstName619','Test LastName86649',26,'test12257@test.com','test123'),(219,'Test FirstName4461','Test LastName40694',26,'test4404@test.com','test123'),(221,'Test FirstName78709','Test LastName78677',26,'test25403@test.com','test123'),(223,'Test FirstName63312','Test LastName68288',26,'test27107@test.com','test123'),(225,'Test FirstName58394','Test LastName96610',26,'test97324@test.com','test123'),(227,'Test FirstName49940','Test LastName76057',26,'test79712@test.com','test123'),(229,'Test FirstName59264','Test LastName69241',26,'test9169@test.com','test123'),(231,'Test FirstName90190','Test LastName15824',26,'test81576@test.com','test123'),(277,'Test FirstName64988','Test LastName30768',26,'test56875@test.com','test123'),(279,'Test FirstName30301','Test LastName79998',26,'test98856@test.com','test123'),(281,'Test FirstName3589','Test LastName59460',26,'test38678@test.com','test123'),(283,'Test FirstName4422','Test LastName22328',26,'test37482@test.com','test123'),(285,'Test FirstName46926','Test LastName83497',26,'test4208@test.com','test123'),(287,'Test FirstName92998','Test LastName77979',26,'test54841@test.com','test123'),(289,'Test FirstName23912','Test LastName10335',26,'test25121@test.com','test123'),(291,'Test FirstName78529','Test LastName32090',26,'test893@test.com','test123');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-24 15:45:46
