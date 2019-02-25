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
CREATE DATABASE IF NOT EXISTS `ZCC_SSO` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `ZCC_SSO`;

-- Dumping structure for table ZCC_SSO.AMC_PERMISSION
CREATE TABLE IF NOT EXISTS `AMC_PERMISSION` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table ZCC_SSO.AMC_PERMISSION: ~7 rows (approximately)
DELETE FROM `AMC_PERMISSION`;
/*!40000 ALTER TABLE `AMC_PERMISSION` DISABLE KEYS */;
INSERT INTO `AMC_PERMISSION` (`id`, `name`) VALUES
	(2, 'PERM_AMC_CRUD'),
	(5, 'PERM_AMC_REVIEW'),
	(3, 'PERM_AMC_VIEW'),
	(7, 'PERM_BASIC_INFO'),
	(6, 'PERM_CREATE_AMCADMIN'),
	(1, 'PERM_CREATE_AMCUSER'),
	(4, 'PERM_LAWYERAMC_VIEW');
/*!40000 ALTER TABLE `AMC_PERMISSION` ENABLE KEYS */;

-- Dumping structure for table ZCC_SSO.AMC_PHONE_MSG
CREATE TABLE IF NOT EXISTS `AMC_PHONE_MSG` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone_num` char(30) NOT NULL DEFAULT '0',
  `message` varchar(20) NOT NULL DEFAULT '0',
  `check_code` char(10) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='used for phone verify when user registry in system';

-- Dumping data for table ZCC_SSO.AMC_PHONE_MSG: ~0 rows (approximately)
DELETE FROM `AMC_PHONE_MSG`;
/*!40000 ALTER TABLE `AMC_PHONE_MSG` DISABLE KEYS */;
INSERT INTO `AMC_PHONE_MSG` (`id`, `phone_num`, `message`, `check_code`, `create_date`) VALUES
	(1, '13611894398', '0', '497897', '2019-02-22 04:16:47');
/*!40000 ALTER TABLE `AMC_PHONE_MSG` ENABLE KEYS */;

-- Dumping structure for table ZCC_SSO.AMC_ROLE
CREATE TABLE IF NOT EXISTS `AMC_ROLE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` char(20) NOT NULL DEFAULT '0' COMMENT '角色名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table ZCC_SSO.AMC_ROLE: ~5 rows (approximately)
DELETE FROM `AMC_ROLE`;
/*!40000 ALTER TABLE `AMC_ROLE` DISABLE KEYS */;
INSERT INTO `AMC_ROLE` (`id`, `name`) VALUES
	(2, 'ROLE_AMC_ADMIN'),
	(3, 'ROLE_AMC_USER'),
	(5, 'ROLE_LAWYER'),
	(1, 'ROLE_SYSTEM_ADMIN'),
	(4, 'ROLE_ZCC_USER');
/*!40000 ALTER TABLE `AMC_ROLE` ENABLE KEYS */;

-- Dumping structure for table ZCC_SSO.AMC_ROLE_PERMISSION
CREATE TABLE IF NOT EXISTS `AMC_ROLE_PERMISSION` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_permission` (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='角色权限';

-- Dumping data for table ZCC_SSO.AMC_ROLE_PERMISSION: ~11 rows (approximately)
DELETE FROM `AMC_ROLE_PERMISSION`;
/*!40000 ALTER TABLE `AMC_ROLE_PERMISSION` DISABLE KEYS */;
INSERT INTO `AMC_ROLE_PERMISSION` (`id`, `role_id`, `permission_id`, `create_date`, `create_by`) VALUES
	(1, 1, 1, '2019-01-30 17:50:29', -1),
	(2, 1, 6, '2019-01-30 17:56:04', -1),
	(3, 2, 2, '2019-01-30 18:26:42', -1),
	(4, 2, 3, '2019-01-30 18:27:01', -1),
	(5, 1, 5, '2019-01-30 18:27:18', -1),
	(6, 3, 2, '2019-01-30 18:29:28', -1),
	(7, 3, 3, '2019-01-30 18:29:48', -1),
	(8, 4, 3, '2019-01-30 18:30:50', -1),
	(9, 5, 3, '2019-01-30 18:31:29', -1),
	(10, 5, 4, '2019-01-30 18:31:59', -1),
	(11, 1, 7, '2019-01-31 11:33:09', -1);
/*!40000 ALTER TABLE `AMC_ROLE_PERMISSION` ENABLE KEYS */;

-- Dumping structure for table ZCC_SSO.AMC_USER
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table ZCC_SSO.AMC_USER: ~0 rows (approximately)
DELETE FROM `AMC_USER`;
/*!40000 ALTER TABLE `AMC_USER` DISABLE KEYS */;
INSERT INTO `AMC_USER` (`id`, `name`, `password`, `mobile_phone`, `email`, `group_id`, `company_id`, `valid`, `wxid`) VALUES
	(1, 'chenwei', '$2a$10$fCc3t8/bcE1IZSt1ZaBq3O1A73kU7ok1FVlRyvetAUAEhTPiuqA3e', '13611894398', NULL, NULL, 2, -1, '-1');
/*!40000 ALTER TABLE `AMC_USER` ENABLE KEYS */;

-- Dumping structure for table ZCC_SSO.AMC_USER_ROLE
CREATE TABLE IF NOT EXISTS `AMC_USER_ROLE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` bigint(20) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_role` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='AMC 用户角色关系表\r\n';

-- Dumping data for table ZCC_SSO.AMC_USER_ROLE: ~1 rows (approximately)
DELETE FROM `AMC_USER_ROLE`;
/*!40000 ALTER TABLE `AMC_USER_ROLE` DISABLE KEYS */;
INSERT INTO `AMC_USER_ROLE` (`id`, `user_id`, `role_id`, `create_date`, `create_by`) VALUES
	(1, 1, 1, '2019-02-01 09:45:44', -1);
/*!40000 ALTER TABLE `AMC_USER_ROLE` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
