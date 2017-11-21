-- MySQL dump 10.13  Distrib 5.7.20, for Win64 (x86_64)
--
-- Host: localhost    Database: infocollect
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `categoryid` int(11) NOT NULL,
  `theme` varchar(500) NOT NULL,
  `content` varchar(500) NOT NULL,
  `contact` varchar(50) NOT NULL,
  `place` varchar(50) NOT NULL,
  `starttime` datetime NOT NULL,
  `endtime` datetime NOT NULL,
  `createtime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (11,9,'金溢杯编程大赛',7,'主题','活动内容','活动联系方式','活动地点','1970-01-01 00:00:00','1970-01-01 00:00:00','2017-11-16 09:32:13','2017-11-16 09:32:13'),(12,9,'ACM趣味赛',8,'主题','活动内容','活动联系方式','活动地点','1970-01-01 00:00:00','1970-01-01 00:00:00','2017-11-16 09:34:09','2017-11-16 09:34:09'),(14,11,'第二届电子设计大赛',9,'主题','活动内容','活动联系方式','活动地点','1970-01-01 00:00:00','1970-01-01 00:00:00','2017-11-16 09:39:49','2017-11-16 09:39:49'),(15,11,'第三届电子设计大赛',9,'主题','活动内容','活动联系方式','活动地点','1970-01-01 00:00:00','1970-01-01 00:00:00','2017-11-16 09:39:53','2017-11-16 09:39:53');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('admin','201506cs?',1,1),('test','test',1,10),('中文测试','test',1,11);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `parentid` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `status` int(11) DEFAULT '1',
  `createtime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'0','讲座',1,'2017-11-14 07:06:28','2017-11-14 07:06:28'),(2,'0','竞赛',1,'2017-11-14 07:06:28','2017-11-14 07:06:28'),(3,'0','分享',1,'2017-11-14 07:06:28','2017-11-14 07:06:28'),(4,'1','出国',1,'2017-11-14 07:06:28','2017-11-14 07:06:28'),(5,'1','工作室',1,'2017-11-14 07:06:28','2017-11-14 07:06:28'),(6,'1','科协',1,'2017-11-14 07:06:28','2017-11-14 07:06:28'),(7,'2','编程',1,'2017-11-14 07:06:28','2017-11-14 07:06:28'),(8,'2','ACM',1,'2017-11-14 07:06:28','2017-11-14 07:06:28'),(9,'2','电子设计',1,'2017-11-14 06:06:28','2017-11-14 07:06:28'),(10,'2','互联网',1,'2017-11-14 06:06:28','2017-11-14 07:06:28'),(11,'2','数学建模',1,'2017-11-14 07:06:28','2017-11-14 07:06:28');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(50) NOT NULL,
  `role` int(11) DEFAULT '1',
  `institution` varchar(50) NOT NULL,
  `question` varchar(50) NOT NULL,
  `answer` varchar(50) NOT NULL,
  `ifchecked` int(11) DEFAULT '0',
  `createtime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (9,'shuwagongzuoshi@163.com','C85EA103B3A00AC566A1EFE8C66E483D',0,'树蛙工作室','shuwa','shuwa',0,'2017-11-16 09:03:22','2017-11-16 09:03:22'),(11,'jisuanjikexie@163.com','8C50A24F402AD0FAEBDFF2A650DF8200',0,'计算机科协','jisuanji','jisuanji',0,'2017-11-16 09:10:13','2017-11-16 10:15:59'),(13,'heihugongzuoshi@163.com','80CECA2F999DB27D96941BE94E2EE0EB',0,'黑虎工作室','heihugongzuoshi','heihugongzuoshi',0,'2017-11-16 10:07:04','2017-11-16 10:07:04');
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

-- Dump completed on 2017-11-16 19:59:16
