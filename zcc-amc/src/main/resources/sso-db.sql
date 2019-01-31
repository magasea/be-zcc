-- --------------------------------------------------------
-- 主机:                           10.20.100.70
-- Server version:               5.7.25-0ubuntu0.16.04.2 - (Ubuntu)
-- Server OS:                    Linux
-- HeidiSQL 版本:                  10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for ZCC_SSO
DROP DATABASE IF EXISTS `ZCC_SSO`;
CREATE DATABASE IF NOT EXISTS `ZCC_SSO` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `ZCC_SSO`;

-- Dumping structure for table ZCC_SSO.AMC_PERMISSION
DROP TABLE IF EXISTS `AMC_PERMISSION`;
CREATE TABLE IF NOT EXISTS `AMC_PERMISSION` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.
-- Dumping structure for table ZCC_SSO.AMC_ROLE
DROP TABLE IF EXISTS `AMC_ROLE`;
CREATE TABLE IF NOT EXISTS `AMC_ROLE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` char(20) NOT NULL DEFAULT '0' COMMENT '角色名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.
-- Dumping structure for table ZCC_SSO.AMC_ROLE_PERMISSION
DROP TABLE IF EXISTS `AMC_ROLE_PERMISSION`;
CREATE TABLE IF NOT EXISTS `AMC_ROLE_PERMISSION` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_permission` (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='角色权限';

-- Data exporting was unselected.
-- Dumping structure for table ZCC_SSO.AMC_USER
DROP TABLE IF EXISTS `AMC_USER`;
CREATE TABLE IF NOT EXISTS `AMC_USER` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` char(20) NOT NULL COMMENT '姓名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `mobile_phone` char(20) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `group_id` bigint(20) DEFAULT NULL COMMENT '用户组id',
  `company_id` bigint(20) DEFAULT NULL COMMENT '用户公司id',
  `valid` int(2) NOT NULL DEFAULT '-1' COMMENT '是否有效',
  `wxid` varchar(20) NOT NULL DEFAULT '-1' COMMENT '微信号',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table ZCC_SSO.AMC_USER_ROLE
DROP TABLE IF EXISTS `AMC_USER_ROLE`;
CREATE TABLE IF NOT EXISTS `AMC_USER_ROLE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_role` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AMC 用户角色关系表\r\n';

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
