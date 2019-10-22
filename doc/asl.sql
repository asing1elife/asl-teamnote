-- MySQL dump 10.13  Distrib 8.0.15, for osx10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: asl_station
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;



--
-- Table structure for table `al_config`
--

DROP TABLE IF EXISTS `al_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `al_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `value` varchar(255) DEFAULT NULL COMMENT '值',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `al_config`
--

LOCK TABLES `al_config` WRITE;
/*!40000 ALTER TABLE `al_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `al_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `al_daily`
--

DROP TABLE IF EXISTS `al_daily`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `al_daily` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `organization_id` bigint(20) DEFAULT NULL COMMENT '所属组织',
  `year` int(11) DEFAULT NULL COMMENT '年份',
  `month` int(11) DEFAULT NULL COMMENT '月份',
  `expense` tinyint(1) DEFAULT NULL COMMENT '报销',
  `expenseAmount` double DEFAULT NULL COMMENT '报销金额',
  `totalDay` int(11) DEFAULT NULL COMMENT '总计天数',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_daily_organization` (`organization_id`),
  CONSTRAINT `fk_daily_organization` FOREIGN KEY (`organization_id`) REFERENCES `al_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `al_daily`
--

LOCK TABLES `al_daily` WRITE;
/*!40000 ALTER TABLE `al_daily` DISABLE KEYS */;
INSERT INTO `al_daily` VALUES (1,2,2019,3,0,0,0,'2019-03-26 12:31:03','2019-04-28 15:26:34'),(2,2,2018,3,0,0,0,'2019-03-26 12:31:03','2019-04-28 15:26:34'),(4,2,2019,1,0,0,0,'2019-03-26 12:31:03','2019-04-28 15:26:34'),(5,2,2019,2,0,0,0,'2019-03-26 12:31:03','2019-04-28 15:26:34'),(7,2,2019,4,0,0,0,'2019-04-01 19:46:27','2019-04-28 15:26:34'),(9,2,2019,5,0,0,0,'2019-05-30 12:03:06','2019-05-30 12:03:06'),(13,5,2019,5,0,0,0,'2019-05-31 01:20:02','2019-05-31 01:20:02'),(14,2,2019,6,0,0,0,'2019-06-02 14:59:29','2019-06-02 14:59:29'),(15,5,2019,6,0,0,0,'2019-06-02 20:04:52','2019-06-02 20:04:52'),(22,2,2019,7,0,0,0,'2019-06-30 03:03:57','2019-06-30 03:03:57');
/*!40000 ALTER TABLE `al_daily` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `al_daily_record`
--

DROP TABLE IF EXISTS `al_daily_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `al_daily_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `daily_id` bigint(20) DEFAULT NULL COMMENT '所属日志',
  `day` int(11) DEFAULT NULL COMMENT '日期',
  `extra` tinyint(1) DEFAULT NULL COMMENT '加班',
  `repay` tinyint(1) DEFAULT NULL COMMENT '还班',
  `rest` tinyint(1) DEFAULT NULL COMMENT '休息',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_daily_record_daily` (`daily_id`),
  CONSTRAINT `fk_daily_record_daily` FOREIGN KEY (`daily_id`) REFERENCES `al_daily` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `al_daily_record`
--

LOCK TABLES `al_daily_record` WRITE;
/*!40000 ALTER TABLE `al_daily_record` DISABLE KEYS */;
INSERT INTO `al_daily_record` VALUES (2,1,27,0,0,0,'2019-03-26 12:34:02','2019-04-02 14:43:04'),(3,1,26,0,0,0,'2019-03-27 01:42:18','2019-04-02 14:43:04'),(4,1,28,0,0,0,'2019-03-27 10:48:14','2019-04-02 14:43:04'),(5,1,30,0,0,0,'2019-03-29 10:45:37','2019-04-02 14:43:04'),(6,1,31,1,0,0,'2019-03-30 23:55:36','2019-04-02 14:43:04'),(8,7,2,1,1,0,'2019-04-01 19:46:27','2019-04-02 14:43:04'),(10,7,3,1,0,0,'2019-04-02 12:21:54','2019-04-02 12:21:54'),(11,7,7,0,0,0,'2019-04-06 23:16:44','2019-04-06 23:16:44'),(12,7,28,0,0,0,'2019-04-27 21:05:35','2019-04-27 21:05:35'),(13,7,29,0,0,0,'2019-04-28 03:05:48','2019-04-28 03:05:48'),(21,9,31,0,0,1,'2019-05-31 01:19:22','2019-05-31 01:19:22'),(22,13,31,0,0,0,'2019-05-31 01:20:02','2019-05-31 01:20:02'),(23,14,3,0,0,1,'2019-06-02 14:59:29','2019-06-02 14:59:29'),(24,15,3,0,0,0,'2019-06-02 20:04:52','2019-06-02 20:04:52'),(25,14,4,0,0,1,'2019-06-03 15:01:00','2019-06-03 15:01:00'),(26,14,5,0,1,1,'2019-06-04 10:48:26','2019-06-04 10:48:26'),(37,14,12,0,1,1,'2019-06-12 00:48:49','2019-06-12 00:48:49'),(38,14,19,1,0,0,'2019-06-19 00:16:39','2019-06-19 00:16:39'),(39,14,20,1,0,0,'2019-06-19 21:15:48','2019-06-19 21:15:48'),(40,14,26,0,0,0,'2019-06-26 00:24:40','2019-06-26 00:24:40'),(41,14,30,0,0,0,'2019-06-30 00:57:09','2019-06-30 00:57:09'),(42,22,1,0,0,0,'2019-06-30 03:03:57','2019-06-30 03:03:57'),(43,22,3,1,0,0,'2019-07-03 01:04:30','2019-07-03 01:04:30'),(44,22,4,0,0,0,'2019-07-03 11:03:13','2019-07-03 11:03:13');
/*!40000 ALTER TABLE `al_daily_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `al_daily_record_task`
--

DROP TABLE IF EXISTS `al_daily_record_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `al_daily_record_task` (
  `dailyRecord_id` bigint(20) DEFAULT NULL COMMENT '所属日志记录',
  `task_id` bigint(20) DEFAULT NULL COMMENT '所属任务',
  KEY `fk_daily_record_task_daily_record` (`dailyRecord_id`),
  KEY `fk_daily_record_task_task` (`task_id`),
  CONSTRAINT `fk_daily_record_task_daily_record` FOREIGN KEY (`dailyRecord_id`) REFERENCES `al_daily_record` (`id`),
  CONSTRAINT `fk_daily_record_task_task` FOREIGN KEY (`task_id`) REFERENCES `al_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `al_daily_record_task`
--

LOCK TABLES `al_daily_record_task` WRITE;
/*!40000 ALTER TABLE `al_daily_record_task` DISABLE KEYS */;
INSERT INTO `al_daily_record_task` VALUES (23,14),(23,15),(23,13),(38,14),(39,14),(39,16),(39,17),(40,14),(41,14),(42,14),(43,14),(44,14);
/*!40000 ALTER TABLE `al_daily_record_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `al_organization`
--

DROP TABLE IF EXISTS `al_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `al_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `isStar` tinyint(1) DEFAULT '0' COMMENT '是否星标',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `al_organization`
--

LOCK TABLES `al_organization` WRITE;
/*!40000 ALTER TABLE `al_organization` DISABLE KEYS */;
INSERT INTO `al_organization` VALUES (2,'asing1elife\'s team','我自己随便弄的一个组织我自己随便弄的一个组织我自己随便弄的一个组织我自己随便弄的一个组织我自己随便弄',0,'2019-06-19 01:18:54','2019-06-19 01:18:54'),(5,'test','test',0,'2019-05-31 01:00:07','2019-05-31 01:00:07'),(6,'1','2',0,'2019-06-12 02:04:38','2019-06-12 02:04:38'),(7,'2','3',0,'2019-06-12 02:05:17','2019-06-12 02:05:17');
/*!40000 ALTER TABLE `al_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `al_project`
--

DROP TABLE IF EXISTS `al_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `al_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `indexNo` int(11) DEFAULT '0' COMMENT '排序',
  `organization_id` bigint(20) DEFAULT NULL COMMENT '所属组织',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_idx_name` (`name`,`organization_id`),
  KEY `fk_organization_project` (`organization_id`),
  CONSTRAINT `fk_organization_project` FOREIGN KEY (`organization_id`) REFERENCES `al_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `al_project`
--

LOCK TABLES `al_project` WRITE;
/*!40000 ALTER TABLE `al_project` DISABLE KEYS */;
INSERT INTO `al_project` VALUES (2,'ppts-sop',NULL,0,2,'2019-03-03 03:47:21','2019-03-03 03:47:21'),(12,'ppts-pta',NULL,2,2,'2019-03-06 10:07:36','2019-03-06 10:07:36'),(13,'ppts-uop',NULL,1,2,'2019-03-03 03:56:27','2019-03-03 17:59:21');
/*!40000 ALTER TABLE `al_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `al_reimburse`
--

DROP TABLE IF EXISTS `al_reimburse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `al_reimburse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `daily_id` bigint(20) DEFAULT NULL COMMENT '所属日志',
  `status_code` varchar(255) DEFAULT NULL COMMENT '状态',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_reimburse_daily` (`daily_id`),
  CONSTRAINT `fk_reimburse_daily` FOREIGN KEY (`daily_id`) REFERENCES `al_daily` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `al_reimburse`
--

LOCK TABLES `al_reimburse` WRITE;
/*!40000 ALTER TABLE `al_reimburse` DISABLE KEYS */;
INSERT INTO `al_reimburse` VALUES (1,14,'RBST_Impl','2019-07-03 01:06:06','2019-07-03 01:06:06');
/*!40000 ALTER TABLE `al_reimburse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `al_reimburse_invoice`
--

DROP TABLE IF EXISTS `al_reimburse_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `al_reimburse_invoice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `reimburse_id` bigint(20) DEFAULT NULL COMMENT '所属报销',
  `number` varchar(255) DEFAULT NULL COMMENT '发票号码',
  `amount` double DEFAULT NULL COMMENT '发票金额',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_reimburse_invoice_reimburse` (`reimburse_id`),
  CONSTRAINT `fk_reimburse_invoice_reimburse` FOREIGN KEY (`reimburse_id`) REFERENCES `al_reimburse` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `al_reimburse_invoice`
--

LOCK TABLES `al_reimburse_invoice` WRITE;
/*!40000 ALTER TABLE `al_reimburse_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `al_reimburse_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `al_reimburse_item`
--

DROP TABLE IF EXISTS `al_reimburse_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `al_reimburse_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `reimburse_id` bigint(20) DEFAULT NULL COMMENT '所属报销',
  `dailyRecord_id` bigint(20) DEFAULT NULL COMMENT '所属日志记录',
  `type_code` varchar(255) DEFAULT NULL COMMENT '类型',
  `reimburseDate` date DEFAULT NULL COMMENT '报销日期',
  `amount` double DEFAULT NULL COMMENT '金额',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_reimburse_item_reimburse` (`reimburse_id`),
  KEY `fk_reimburse_item_daily_record` (`dailyRecord_id`),
  CONSTRAINT `fk_reimburse_item_daily_record` FOREIGN KEY (`dailyRecord_id`) REFERENCES `al_daily_record` (`id`),
  CONSTRAINT `fk_reimburse_item_reimburse` FOREIGN KEY (`reimburse_id`) REFERENCES `al_reimburse` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `al_reimburse_item`
--

LOCK TABLES `al_reimburse_item` WRITE;
/*!40000 ALTER TABLE `al_reimburse_item` DISABLE KEYS */;
INSERT INTO `al_reimburse_item` VALUES (1,1,38,'RITY_Extra','2019-06-19',18,'2019-07-03 01:06:06','2019-07-03 01:06:06'),(2,1,39,'RITY_Extra','2019-06-20',18,'2019-07-03 01:06:06','2019-07-03 01:06:06');
/*!40000 ALTER TABLE `al_reimburse_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `al_task`
--

DROP TABLE IF EXISTS `al_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `al_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` text COMMENT '描述',
  `level_code` varchar(255) DEFAULT NULL COMMENT '级别',
  `status_code` varchar(255) DEFAULT NULL COMMENT '状态',
  `taskTag_id` bigint(20) DEFAULT NULL COMMENT '所属标签',
  `project_id` bigint(20) DEFAULT NULL COMMENT '所属项目',
  `planBeginDate` datetime DEFAULT NULL COMMENT '计划开始日期',
  `planFinishDate` datetime DEFAULT NULL COMMENT '计划结束日期',
  `beginDate` datetime DEFAULT NULL COMMENT '开始日期',
  `finishDate` datetime DEFAULT NULL COMMENT '结束日期',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_task_tag` (`taskTag_id`),
  KEY `fk_task_project` (`project_id`),
  CONSTRAINT `fk_task_project` FOREIGN KEY (`project_id`) REFERENCES `al_project` (`id`),
  CONSTRAINT `fk_task_tag` FOREIGN KEY (`taskTag_id`) REFERENCES `al_task_tag` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `al_task`
--

LOCK TABLES `al_task` WRITE;
/*!40000 ALTER TABLE `al_task` DISABLE KEYS */;
INSERT INTO `al_task` VALUES (13,'testTask','','TALE_Normal','TAST_Finish',1,13,NULL,NULL,'2019-06-03 01:20:21','2019-06-03 01:20:25','2019-05-31 01:00:26','2019-05-31 01:00:26'),(14,'这就是一句非常非常非常非常非常废的话','','TALE_Very','TAST_Impl',1,13,NULL,NULL,'2019-06-19 08:19:26','2019-06-03 01:20:06','2019-05-31 02:20:07','2019-05-31 02:20:07'),(15,'3','','TALE_Normal','TAST_Finish',1,13,NULL,NULL,'2019-06-03 01:19:15','2019-06-03 01:20:09','2019-06-02 17:19:09','2019-06-02 17:19:09'),(16,'这是一条测试任务','','TALE_Very','TAST_Finish',1,12,NULL,NULL,'2019-06-20 09:30:55','2019-06-20 09:30:55','2019-06-20 01:30:53','2019-06-20 01:30:53'),(17,'这是一条测试任务2222','','TALE_Very','TAST_Finish',1,12,NULL,NULL,'2019-06-20 09:31:04','2019-06-20 09:31:04','2019-06-20 01:31:02','2019-06-20 01:31:02');
/*!40000 ALTER TABLE `al_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `al_task_tag`
--

DROP TABLE IF EXISTS `al_task_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `al_task_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `color` varchar(255) DEFAULT NULL COMMENT '颜色',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `al_task_tag`
--

LOCK TABLES `al_task_tag` WRITE;
/*!40000 ALTER TABLE `al_task_tag` DISABLE KEYS */;
INSERT INTO `al_task_tag` VALUES (1,'功能','#3da8f5','2019-03-07 10:52:12','2019-03-07 10:52:12'),(2,'除虫','#ff4f3d','2019-03-07 21:42:09','2019-04-02 23:50:57');
/*!40000 ALTER TABLE `al_task_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dictionary`
--

DROP TABLE IF EXISTS `sys_dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_dictionary` (
  `category` varchar(255) DEFAULT NULL COMMENT '类型',
  `code` varchar(255) NOT NULL COMMENT '编码',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `indexNo` int(11) DEFAULT NULL COMMENT '索引',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dictionary`
--

LOCK TABLES `sys_dictionary` WRITE;
/*!40000 ALTER TABLE `sys_dictionary` DISABLE KEYS */;
INSERT INTO `sys_dictionary` VALUES ('com.asing1elife.teamnote.model.dictionary.ReimburseStatus','RBST_Finish','已结算',1),('com.asing1elife.teamnote.model.dictionary.ReimburseStatus','RBST_Impl','报销中',0),('com.asing1elife.teamnote.model.dictionary.ReimburseItemType','RITY_Extra','加班',0),('com.asing1elife.teamnote.model.dictionary.ReimburseItemType','RITY_Fare','车费',1),('com.asing1elife.teamnote.model.dictionary.TaskLevel','TALE_Normal','普通',0),('com.asing1elife.teamnote.model.dictionary.TaskLevel','TALE_Urgency','紧急',1),('com.asing1elife.teamnote.model.dictionary.TaskLevel','TALE_Very','非常紧急',2),('com.asing1elife.teamnote.model.dictionary.TaskStatus','TAST_Finish','已完成',2),('com.asing1elife.teamnote.model.dictionary.TaskStatus','TAST_Impl','进行中',1),('com.asing1elife.teamnote.model.dictionary.TaskStatus','TAST_Init','初始化',0);
/*!40000 ALTER TABLE `sys_dictionary` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-06 13:56:43
