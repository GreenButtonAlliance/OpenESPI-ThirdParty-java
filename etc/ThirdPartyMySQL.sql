CREATE DATABASE  IF NOT EXISTS `thirdparty` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `thirdparty`;
-- MySQL dump 10.13  Distrib 5.5.34, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: thirdparty
-- ------------------------------------------------------
-- Server version	5.5.32

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
-- Table structure for table `line_item`
--

DROP TABLE IF EXISTS `line_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `line_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` bigint(20) NOT NULL,
  `dateTime` bigint(20) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `rounding` bigint(20) DEFAULT NULL,
  `electric_power_usage_summary_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_77124f2275734b4587e7898e68f` (`electric_power_usage_summary_id`),
  CONSTRAINT `FK_77124f2275734b4587e7898e68f` FOREIGN KEY (`electric_power_usage_summary_id`) REFERENCES `electric_power_usage_summaries` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_information_scopes`
--

DROP TABLE IF EXISTS `application_information_scopes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_information_scopes` (
  `application_information_id` bigint(20) NOT NULL,
  `scope` varchar(255) DEFAULT NULL,
  KEY `FK_91480e2c8c8c469aa05c5d79efa` (`application_information_id`),
  CONSTRAINT `FK_91480e2c8c8c469aa05c5d79efa` FOREIGN KEY (`application_information_id`) REFERENCES `application_information` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `interval_readings`
--

DROP TABLE IF EXISTS `interval_readings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interval_readings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cost` bigint(20) DEFAULT NULL,
  `duration` bigint(20) DEFAULT NULL,
  `start` bigint(20) DEFAULT NULL,
  `value` bigint(20) DEFAULT NULL,
  `interval_block_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8dc12fcbcc6f489fa4e0533ade5` (`interval_block_id`),
  KEY `FK_49b8a20b1ea84ce6bc85254a86a` (`interval_block_id`),
  CONSTRAINT `FK_49b8a20b1ea84ce6bc85254a86a` FOREIGN KEY (`interval_block_id`) REFERENCES `interval_blocks` (`id`),
  CONSTRAINT `FK_8dc12fcbcc6f489fa4e0533ade5` FOREIGN KEY (`interval_block_id`) REFERENCES `interval_blocks` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscriptions_usage_points`
--

DROP TABLE IF EXISTS `subscriptions_usage_points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscriptions_usage_points` (
  `subscriptions_id` bigint(20) NOT NULL,
  `usagePoints_id` bigint(20) NOT NULL,
  PRIMARY KEY (`subscriptions_id`,`usagePoints_id`),
  KEY `FK_c45e80dcbfb54c7abfde08ebea2` (`usagePoints_id`),
  KEY `FK_c3e9709e2f164172a87cc1102c5` (`subscriptions_id`),
  CONSTRAINT `FK_c3e9709e2f164172a87cc1102c5` FOREIGN KEY (`subscriptions_id`) REFERENCES `subscriptions` (`id`),
  CONSTRAINT `FK_c45e80dcbfb54c7abfde08ebea2` FOREIGN KEY (`usagePoints_id`) REFERENCES `usage_points` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usage_points`
--

DROP TABLE IF EXISTS `usage_points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usage_points` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `roleFlags` tinyblob,
  `status` smallint(6) DEFAULT NULL,
  `uri` varchar(255) DEFAULT NULL,
  `local_time_parameters_id` bigint(20) DEFAULT NULL,
  `retail_customer_id` bigint(20) DEFAULT NULL,
  `serviceCategory_kind` bigint(20) NOT NULL,
  `serviceDeliveryPoint_id` bigint(20) DEFAULT NULL,
  `subscription_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1709a8298ec043029f98d6ea73a` (`uuid`),
  UNIQUE KEY `UK_066e9a3ac2524885ae62bd5f1d3` (`uuid`),
  KEY `FK_0ed5362873ee412e8d014c297cf` (`local_time_parameters_id`),
  KEY `FK_0cf28b226e1c43c39e54a3dd86b` (`retail_customer_id`),
  KEY `FK_954b48a487304cee813ccea5d27` (`serviceCategory_kind`),
  KEY `FK_5573af641cc2483aa0ce17e1288` (`serviceDeliveryPoint_id`),
  KEY `FK_185c637eacf94a8299dcd079f86` (`subscription_id`),
  KEY `FK_260d4716c9514b87927637425b3` (`local_time_parameters_id`),
  KEY `FK_a865a79ef90844ef8dc6172e380` (`retail_customer_id`),
  KEY `FK_dba214b3cc3c4432b1920817a52` (`serviceCategory_kind`),
  KEY `FK_277228aa9ae54adfa3decfd38cd` (`serviceDeliveryPoint_id`),
  KEY `FK_42bd292185934bc0a6a89147902` (`subscription_id`),
  CONSTRAINT `FK_42bd292185934bc0a6a89147902` FOREIGN KEY (`subscription_id`) REFERENCES `subscriptions` (`id`),
  CONSTRAINT `FK_0cf28b226e1c43c39e54a3dd86b` FOREIGN KEY (`retail_customer_id`) REFERENCES `retail_customers` (`id`),
  CONSTRAINT `FK_0ed5362873ee412e8d014c297cf` FOREIGN KEY (`local_time_parameters_id`) REFERENCES `time_configurations` (`id`),
  CONSTRAINT `FK_185c637eacf94a8299dcd079f86` FOREIGN KEY (`subscription_id`) REFERENCES `subscriptions` (`id`),
  CONSTRAINT `FK_260d4716c9514b87927637425b3` FOREIGN KEY (`local_time_parameters_id`) REFERENCES `time_configurations` (`id`),
  CONSTRAINT `FK_277228aa9ae54adfa3decfd38cd` FOREIGN KEY (`serviceDeliveryPoint_id`) REFERENCES `service_delivery_points` (`id`),
  CONSTRAINT `FK_5573af641cc2483aa0ce17e1288` FOREIGN KEY (`serviceDeliveryPoint_id`) REFERENCES `service_delivery_points` (`id`),
  CONSTRAINT `FK_954b48a487304cee813ccea5d27` FOREIGN KEY (`serviceCategory_kind`) REFERENCES `service_categories` (`kind`),
  CONSTRAINT `FK_a865a79ef90844ef8dc6172e380` FOREIGN KEY (`retail_customer_id`) REFERENCES `retail_customers` (`id`),
  CONSTRAINT `FK_dba214b3cc3c4432b1920817a52` FOREIGN KEY (`serviceCategory_kind`) REFERENCES `service_categories` (`kind`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_categories`
--

DROP TABLE IF EXISTS `service_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service_categories` (
  `kind` bigint(20) NOT NULL,
  PRIMARY KEY (`kind`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meter_readings`
--

DROP TABLE IF EXISTS `meter_readings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meter_readings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `reading_type_id` bigint(20) DEFAULT NULL,
  `usage_point_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_72b530ab58144961a07becece92` (`uuid`),
  UNIQUE KEY `UK_eb2e5aa7ff0240ba88f41b2c2bf` (`uuid`),
  KEY `FK_eab10e805d5d4cb681e82ad51ea` (`reading_type_id`),
  KEY `FK_5c4ddc76bd084403b3954b9679e` (`usage_point_id`),
  KEY `FK_9b8fb97170a34d2d80a5caff77d` (`reading_type_id`),
  KEY `FK_a2c940091685405ca37b097c76e` (`usage_point_id`),
  CONSTRAINT `FK_a2c940091685405ca37b097c76e` FOREIGN KEY (`usage_point_id`) REFERENCES `usage_points` (`id`),
  CONSTRAINT `FK_5c4ddc76bd084403b3954b9679e` FOREIGN KEY (`usage_point_id`) REFERENCES `usage_points` (`id`),
  CONSTRAINT `FK_9b8fb97170a34d2d80a5caff77d` FOREIGN KEY (`reading_type_id`) REFERENCES `reading_types` (`id`),
  CONSTRAINT `FK_eab10e805d5d4cb681e82ad51ea` FOREIGN KEY (`reading_type_id`) REFERENCES `reading_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reading_types`
--

DROP TABLE IF EXISTS `reading_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reading_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `accumulationBehaviour` varchar(255) DEFAULT NULL,
  `rational_denominator` decimal(19,2) DEFAULT NULL,
  `rational_numerator` decimal(19,2) DEFAULT NULL,
  `commodity` varchar(255) DEFAULT NULL,
  `consumptionTier` varchar(255) DEFAULT NULL,
  `cpp` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `dataQualifier` varchar(255) DEFAULT NULL,
  `defaultQuality` varchar(255) DEFAULT NULL,
  `flowDirection` varchar(255) DEFAULT NULL,
  `interharmonic_denominator` decimal(19,2) DEFAULT NULL,
  `interharmonic_numerator` decimal(19,2) DEFAULT NULL,
  `intervalLength` bigint(20) DEFAULT NULL,
  `kind` varchar(255) DEFAULT NULL,
  `measuringPeriod` varchar(255) DEFAULT NULL,
  `phase` varchar(255) DEFAULT NULL,
  `powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `timeAttribute` varchar(255) DEFAULT NULL,
  `tou` varchar(255) DEFAULT NULL,
  `uom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ad0d472f41f84bd98905ab44c81` (`uuid`),
  UNIQUE KEY `UK_d7f828aeb05e4f0d8604c951017` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `batchlist`
--

DROP TABLE IF EXISTS `batchlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `batchlist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `interval_blocks`
--

DROP TABLE IF EXISTS `interval_blocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interval_blocks` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `duration` bigint(20) DEFAULT NULL,
  `start` bigint(20) DEFAULT NULL,
  `meter_reading_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3bfd8a4d10354835b72d06df120` (`uuid`),
  UNIQUE KEY `UK_564fb32570f24f89bb159fb0a08` (`uuid`),
  KEY `FK_f8b6b52937ba4db9abdf9484cc4` (`meter_reading_id`),
  KEY `FK_1337235e0155491ea007bef467e` (`meter_reading_id`),
  CONSTRAINT `FK_1337235e0155491ea007bef467e` FOREIGN KEY (`meter_reading_id`) REFERENCES `meter_readings` (`id`),
  CONSTRAINT `FK_f8b6b52937ba4db9abdf9484cc4` FOREIGN KEY (`meter_reading_id`) REFERENCES `meter_readings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `electric_power_quality_summaries`
--

DROP TABLE IF EXISTS `electric_power_quality_summaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `electric_power_quality_summaries` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `flickerPlt` bigint(20) DEFAULT NULL,
  `flickerPst` bigint(20) DEFAULT NULL,
  `harmonicVoltage` bigint(20) DEFAULT NULL,
  `longInterruptions` bigint(20) DEFAULT NULL,
  `mainsVoltage` bigint(20) DEFAULT NULL,
  `measurementProtocol` smallint(6) DEFAULT NULL,
  `powerFrequency` bigint(20) DEFAULT NULL,
  `rapidVoltageChanges` bigint(20) DEFAULT NULL,
  `shortInterruptions` bigint(20) DEFAULT NULL,
  `duration` bigint(20) DEFAULT NULL,
  `start` bigint(20) DEFAULT NULL,
  `supplyVoltageDips` bigint(20) DEFAULT NULL,
  `supplyVoltageImbalance` bigint(20) DEFAULT NULL,
  `supplyVoltageVariations` bigint(20) DEFAULT NULL,
  `tempOvervoltage` bigint(20) DEFAULT NULL,
  `usage_point_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_873cb5aa7ffe4171b30c6e9b9f8` (`uuid`),
  KEY `FK_37ec3b20ffd04feca65a63175ce` (`usage_point_id`),
  CONSTRAINT `FK_37ec3b20ffd04feca65a63175ce` FOREIGN KEY (`usage_point_id`) REFERENCES `usage_points` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `meter_reading_related_links`
--

DROP TABLE IF EXISTS `meter_reading_related_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meter_reading_related_links` (
  `meter_reading_id` bigint(20) NOT NULL,
  `href` varchar(255) DEFAULT NULL,
  `rel` varchar(255) DEFAULT NULL,
  KEY `FK_fe38c0413f9c46318c2399158c7` (`meter_reading_id`),
  CONSTRAINT `FK_fe38c0413f9c46318c2399158c7` FOREIGN KEY (`meter_reading_id`) REFERENCES `meter_readings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `time_configurations`
--

DROP TABLE IF EXISTS `time_configurations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `time_configurations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `dstEndRule` tinyblob,
  `dstOffset` bigint(20) NOT NULL,
  `dstStartRule` tinyblob,
  `tzOffset` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_c6e227bab8bb4c08a3a9f6acc22` (`uuid`),
  UNIQUE KEY `UK_7b5adb3cbb6444e1aa9940acc34` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `authorizations`
--

DROP TABLE IF EXISTS `authorizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authorizations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `access_token` varchar(255) DEFAULT NULL,
  `authorization_uri` varchar(255) DEFAULT NULL,
  `ap_duration` bigint(20) DEFAULT NULL,
  `ap_start` bigint(20) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `error` int(11) DEFAULT NULL,
  `errorDescription` varchar(255) DEFAULT NULL,
  `errorUri` varchar(255) DEFAULT NULL,
  `expiresIn` bigint(20) DEFAULT NULL,
  `grantType` int(11) DEFAULT NULL,
  `pp_duration` bigint(20) DEFAULT NULL,
  `pp_start` bigint(20) DEFAULT NULL,
  `refreshToken` varchar(255) DEFAULT NULL,
  `resourceURI` varchar(255) DEFAULT NULL,
  `responseType` int(11) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `subscriptionURI` varchar(255) DEFAULT NULL,
  `third_party` varchar(255) DEFAULT NULL,
  `tokenType` int(11) DEFAULT NULL,
  `application_information_id` bigint(20) DEFAULT NULL,
  `retail_customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_07d970a228174a4aac7769da9e9` (`application_information_id`),
  KEY `FK_af2eb44c080549d7b5594d2a337` (`retail_customer_id`),
  KEY `FK_de4f6ccf50234069bdb31861c17` (`application_information_id`),
  KEY `FK_468cce86579947a0862885b7989` (`retail_customer_id`),
  CONSTRAINT `FK_468cce86579947a0862885b7989` FOREIGN KEY (`retail_customer_id`) REFERENCES `retail_customers` (`id`),
  CONSTRAINT `FK_07d970a228174a4aac7769da9e9` FOREIGN KEY (`application_information_id`) REFERENCES `application_information` (`id`),
  CONSTRAINT `FK_af2eb44c080549d7b5594d2a337` FOREIGN KEY (`retail_customer_id`) REFERENCES `retail_customers` (`id`),
  CONSTRAINT `FK_de4f6ccf50234069bdb31861c17` FOREIGN KEY (`application_information_id`) REFERENCES `application_information` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reading_qualities`
--

DROP TABLE IF EXISTS `reading_qualities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reading_qualities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `quality` varchar(255) DEFAULT NULL,
  `interval_reading_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_a092fc86e8334af087bb7b8dfc7` (`interval_reading_id`),
  CONSTRAINT `FK_a092fc86e8334af087bb7b8dfc7` FOREIGN KEY (`interval_reading_id`) REFERENCES `interval_readings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscriptions`
--

DROP TABLE IF EXISTS `subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscriptions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `hashedId` varchar(255) DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `applicationInformation_id` bigint(20) NOT NULL,
  `authorization_id` bigint(20) DEFAULT NULL,
  `retail_customer_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3dcd6809f56749faae59f155a0a` (`applicationInformation_id`),
  KEY `FK_89be91e544b642d2a798aae6283` (`authorization_id`),
  KEY `FK_63dd7be615c547c3aec79df1f6e` (`retail_customer_id`),
  KEY `FK_2ee2e3604d3e414ca62abd9298a` (`applicationInformation_id`),
  KEY `FK_e9969fd46a8043a28ea0bd32e70` (`authorization_id`),
  KEY `FK_ce45a6b3ffb94980b48e679d68a` (`retail_customer_id`),
  CONSTRAINT `FK_ce45a6b3ffb94980b48e679d68a` FOREIGN KEY (`retail_customer_id`) REFERENCES `retail_customers` (`id`),
  CONSTRAINT `FK_2ee2e3604d3e414ca62abd9298a` FOREIGN KEY (`applicationInformation_id`) REFERENCES `application_information` (`id`),
  CONSTRAINT `FK_3dcd6809f56749faae59f155a0a` FOREIGN KEY (`applicationInformation_id`) REFERENCES `application_information` (`id`),
  CONSTRAINT `FK_63dd7be615c547c3aec79df1f6e` FOREIGN KEY (`retail_customer_id`) REFERENCES `retail_customers` (`id`),
  CONSTRAINT `FK_89be91e544b642d2a798aae6283` FOREIGN KEY (`authorization_id`) REFERENCES `authorizations` (`id`),
  CONSTRAINT `FK_e9969fd46a8043a28ea0bd32e70` FOREIGN KEY (`authorization_id`) REFERENCES `authorizations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usage_point_related_links`
--

DROP TABLE IF EXISTS `usage_point_related_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usage_point_related_links` (
  `usage_point_id` bigint(20) NOT NULL,
  `href` varchar(255) DEFAULT NULL,
  `rel` varchar(255) DEFAULT NULL,
  KEY `FK_23fe028b7c804869be8a3d56b83` (`usage_point_id`),
  CONSTRAINT `FK_23fe028b7c804869be8a3d56b83` FOREIGN KEY (`usage_point_id`) REFERENCES `usage_points` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `electric_power_usage_summaries`
--

DROP TABLE IF EXISTS `electric_power_usage_summaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `electric_power_usage_summaries` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `billLastPeriod` bigint(20) DEFAULT NULL,
  `billToDate` bigint(20) DEFAULT NULL,
  `billingPeriod_duration` bigint(20) DEFAULT NULL,
  `billingPeriod_start` bigint(20) DEFAULT NULL,
  `costAdditionalLastPeriod` bigint(20) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `currentBillingPeriodOverAllConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `currentBillingPeriodOverAllConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `currentBillingPeriodOverAllConsumption_uom` varchar(255) DEFAULT NULL,
  `currentBillingPeriodOverAllConsumption_value` bigint(20) DEFAULT NULL,
  `currentDayLastYearNetConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `currentDayLastYearNetConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `currentDayLastYearNetConsumption_uom` varchar(255) DEFAULT NULL,
  `currentDayLastYearNetConsumption_value` bigint(20) DEFAULT NULL,
  `currentDayNetConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `currentDayNetConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `currentDayNetConsumption_uom` varchar(255) DEFAULT NULL,
  `currentDayNetConsumption_value` bigint(20) DEFAULT NULL,
  `currentDayOverallConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `currentDayOverallConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `currentDayOverallConsumption_uom` varchar(255) DEFAULT NULL,
  `currentDayOverallConsumption_value` bigint(20) DEFAULT NULL,
  `overallConsumptionLastPeriod_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `overallConsumptionLastPeriod_timeStamp` bigint(20) DEFAULT NULL,
  `overallConsumptionLastPeriod_uom` varchar(255) DEFAULT NULL,
  `overallConsumptionLastPeriod_value` bigint(20) DEFAULT NULL,
  `peakDemand_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `peakDemand_timeStamp` bigint(20) DEFAULT NULL,
  `peakDemand_uom` varchar(255) DEFAULT NULL,
  `peakDemand_value` bigint(20) DEFAULT NULL,
  `previousDayLastYearOverallConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `previousDayLastYearOverallConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `previousDayLastYearOverallConsumption_uom` varchar(255) DEFAULT NULL,
  `previousDayLastYearOverallConsumption_value` bigint(20) DEFAULT NULL,
  `previousDayNetConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `previousDayNetConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `previousDayNetConsumption_uom` varchar(255) DEFAULT NULL,
  `previousDayNetConsumption_value` bigint(20) DEFAULT NULL,
  `previousDayOverallConsumption_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `previousDayOverallConsumption_timeStamp` bigint(20) DEFAULT NULL,
  `previousDayOverallConsumption_uom` varchar(255) DEFAULT NULL,
  `previousDayOverallConsumption_value` bigint(20) DEFAULT NULL,
  `qualityOfReading` varchar(255) DEFAULT NULL,
  `ratchetDemand_powerOfTenMultiplier` varchar(255) DEFAULT NULL,
  `ratchetDemand_timeStamp` bigint(20) DEFAULT NULL,
  `ratchetDemand_uom` varchar(255) DEFAULT NULL,
  `ratchetDemand_value` bigint(20) DEFAULT NULL,
  `ratchetDemandPeriod_duration` bigint(20) DEFAULT NULL,
  `ratchetDemandPeriod_start` bigint(20) DEFAULT NULL,
  `statusTimeStamp` bigint(20) NOT NULL,
  `usage_point_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_07aa22b86a2c4e998a19d8f4ef1` (`uuid`),
  UNIQUE KEY `UK_5c893c5f4f274b8d9fae7f7413a` (`uuid`),
  KEY `FK_d70e28ec2f214de9978ae0d3658` (`usage_point_id`),
  KEY `FK_1c47cfd3977d475b8d640aae8be` (`usage_point_id`),
  CONSTRAINT `FK_1c47cfd3977d475b8d640aae8be` FOREIGN KEY (`usage_point_id`) REFERENCES `usage_points` (`id`),
  CONSTRAINT `FK_d70e28ec2f214de9978ae0d3658` FOREIGN KEY (`usage_point_id`) REFERENCES `usage_points` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `retail_customers`
--

DROP TABLE IF EXISTS `retail_customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `retail_customers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_delivery_points`
--

DROP TABLE IF EXISTS `service_delivery_points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service_delivery_points` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customerAgreement` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tariffProfile` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `application_information`
--

DROP TABLE IF EXISTS `application_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application_information` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `published` datetime DEFAULT NULL,
  `self_link_href` varchar(255) DEFAULT NULL,
  `self_link_rel` varchar(255) DEFAULT NULL,
  `up_link_href` varchar(255) DEFAULT NULL,
  `up_link_rel` varchar(255) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `uuid` varchar(255) NOT NULL,
  `authorizationServerAuthorizationEndpoint` varchar(255) DEFAULT NULL,
  `authorizationServerRegistrationEndpoint` varchar(255) DEFAULT NULL,
  `authorizationServerTokenEndpoint` varchar(255) DEFAULT NULL,
  `authorizationServerUri` varchar(255) DEFAULT NULL,
  `clientId` varchar(64) NOT NULL,
  `clientIdIssuedAt` bigint(20) DEFAULT NULL,
  `clientName` varchar(255) DEFAULT NULL,
  `clientSecret` varchar(255) DEFAULT NULL,
  `clientSecretExpiresAt` bigint(20) DEFAULT NULL,
  `clientUri` varchar(255) DEFAULT NULL,
  `contacts` tinyblob,
  `dataCustodianApplicationStatus` varchar(255) DEFAULT NULL,
  `dataCustodianBulkRequestURI` varchar(255) DEFAULT NULL,
  `dataCustodianDefaultBatchResource` varchar(255) DEFAULT NULL,
  `dataCustodianDefaultSubscriptionResource` varchar(255) DEFAULT NULL,
  `dataCustodianId` varchar(64) DEFAULT NULL,
  `dataCustodianResourceEndpoint` varchar(255) DEFAULT NULL,
  `dataCustodianThirdPartySelectionScreenURI` varchar(255) DEFAULT NULL,
  `grantTypes` tinyblob,
  `logoUri` varchar(255) DEFAULT NULL,
  `policyUri` varchar(255) DEFAULT NULL,
  `redirectUri` varchar(255) DEFAULT NULL,
  `registrationAccessToken` varchar(255) DEFAULT NULL,
  `registrationClientUri` varchar(255) DEFAULT NULL,
  `responseTypes` int(11) DEFAULT NULL,
  `softwareId` varchar(255) DEFAULT NULL,
  `softwareVersion` varchar(255) DEFAULT NULL,
  `thirdPartyApplicationDescription` varchar(255) DEFAULT NULL,
  `thirdPartyApplicationName` varchar(64) NOT NULL,
  `thirdPartyApplicationStatus` varchar(255) DEFAULT NULL,
  `thirdPartyApplicationType` varchar(255) DEFAULT NULL,
  `thirdPartyApplicationUse` varchar(255) DEFAULT NULL,
  `thirdPartyDataCustodianSelectionScreenURI` varchar(255) DEFAULT NULL,
  `thirdPartyLoginScreenURI` varchar(255) DEFAULT NULL,
  `thirdPartyNotifyUri` varchar(255) DEFAULT NULL,
  `thirdPartyPhone` varchar(255) DEFAULT NULL,
  `thirdPartyScopeSelectionScreenURI` varchar(255) DEFAULT NULL,
  `thirdPartyUserPortalScreenURI` varchar(255) DEFAULT NULL,
  `tokenEndpointAuthMethod` varchar(255) DEFAULT NULL,
  `tosUri` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8c3fd005a8ab4220b00d6284710` (`dataCustodianId`,`clientId`),
  UNIQUE KEY `UK_6c217a733a034d288a8228dcf75` (`dataCustodianId`,`clientId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resources`
--

DROP TABLE IF EXISTS `resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resources` (
  `id` bigint(20) NOT NULL,
  `uri` varchar(255) DEFAULT NULL,
  KEY `FK_161c3a045a664320a97509fa5ef` (`id`),
  CONSTRAINT `FK_161c3a045a664320a97509fa5ef` FOREIGN KEY (`id`) REFERENCES `batchlist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-12-28 16:45:40
