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


-- Dumping database structure for ZCC_AMC
DROP DATABASE IF EXISTS `ZCC_AMC`;
CREATE DATABASE IF NOT EXISTS `ZCC_AMC` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `ZCC_AMC`;

-- Dumping structure for table ZCC_AMC.AMC_ASSET
DROP TABLE IF EXISTS `AMC_ASSET`;
CREATE TABLE IF NOT EXISTS `AMC_ASSET` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` char(100) NOT NULL DEFAULT '-1' COMMENT '抵押物名称',
  `type` int(2) NOT NULL DEFAULT '-1' COMMENT '抵押物类别',
  `status` int(2) NOT NULL DEFAULT '-1' COMMENT '抵押物处置状态',
  `state` int(2) NOT NULL DEFAULT '-1' COMMENT '抵押状态 质押 1、冻结 2、抵押 3、查封 4',
  `edit_status` int(2) NOT NULL DEFAULT '-1' COMMENT '1-发布， 2-未发布， 0-已删除， 3-已放弃， 4-已售出',
  `amc_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '抵押物所属AMC ID',
  `amc_asset_code` char(15) NOT NULL DEFAULT '-1' COMMENT 'AMC内部业务编码',
  `zcc_asset_code` char(15) NOT NULL DEFAULT '-1' COMMENT '平台编码',
  `is_main_asset` smallint(2) NOT NULL DEFAULT '-1' COMMENT '1- 是主资产 -1 - 非主资产',
  `estm_price` bigint(20) NOT NULL DEFAULT '-1' COMMENT '抵押物类别司法评估价精确到分',
  `debt_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '资产关联债权ID',
  `init_price` bigint(20) NOT NULL DEFAULT '-1' COMMENT '拍卖起拍价',
  `restrictions` int(2) NOT NULL DEFAULT '-1' COMMENT '是否限购',
  `area` bigint(20) NOT NULL DEFAULT '-1' COMMENT '面积(只适用不动产)精确到平方米后2位',
  `land_area` bigint(20) NOT NULL DEFAULT '-1' COMMENT '土地面积精确到平方米后2位',
  `publish_date` date NOT NULL DEFAULT '1900-01-01' COMMENT '发布时间',
  `province` char(20) NOT NULL DEFAULT '-1' COMMENT '抵押物所在省',
  `city` char(20) NOT NULL DEFAULT '-1' COMMENT '抵押物所在市',
  `county` char(20) NOT NULL DEFAULT '-1' COMMENT '抵押物所在县',
  `street` char(100) NOT NULL DEFAULT '-1' COMMENT '抵押物地址',
  `building_name` char(50) NOT NULL DEFAULT '-1' COMMENT '小区(楼盘)名字 爬取使用',
  `gps_lng` char(15) NOT NULL DEFAULT '0' COMMENT 'gps 坐标经度',
  `gps_lat` char(15) NOT NULL DEFAULT '0' COMMENT 'gps 坐标纬度',
  `origin_debt_id` char(20) NOT NULL DEFAULT '-1',
  `create_by` bigint(20) NOT NULL DEFAULT '-1' COMMENT 'Amc创建人Id',
  `amc_conactor` bigint(20) NOT NULL DEFAULT '-1' COMMENT 'AMC 承办人ID',
  PRIMARY KEY (`id`),
  KEY `title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=272 DEFAULT CHARSET=utf8mb4 COMMENT='债权';

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.AMC_CMPY
DROP TABLE IF EXISTS `AMC_CMPY`;
CREATE TABLE IF NOT EXISTS `AMC_CMPY` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` char(50) NOT NULL DEFAULT '-1' COMMENT '公司名称',
  `reg_id` char(45) NOT NULL DEFAULT '-1' COMMENT '工商注册号',
  `social_creditid` char(45) NOT NULL DEFAULT '-1' COMMENT '统一社会信用代码',
  `related_url` char(150) NOT NULL DEFAULT '-1' COMMENT '查看报告的url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公司信息';

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.AMC_CMPY_SHAREHOLDER
DROP TABLE IF EXISTS `AMC_CMPY_SHAREHOLDER`;
CREATE TABLE IF NOT EXISTS `AMC_CMPY_SHAREHOLDER` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `amount` char(20) NOT NULL,
  `percentage` char(20) NOT NULL,
  `name` char(20) NOT NULL,
  `person_id` bigint(20) NOT NULL,
  `cmpy_id` bigint(20) NOT NULL,
  `subscribed_amount` char(20) NOT NULL,
  `subscribed_date` datetime NOT NULL DEFAULT '1900-01-01 00:00:00',
  `paid_amount` char(20) NOT NULL,
  `paid_date` datetime NOT NULL DEFAULT '1900-01-01 00:00:00',
  `parent_holder_id` bigint(20) unsigned NOT NULL,
  `type` int(2) NOT NULL DEFAULT '-1',
  `link` varchar(50) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公司股份持有信息\r\n';

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.AMC_CREDITOR
DROP TABLE IF EXISTS `AMC_CREDITOR`;
CREATE TABLE IF NOT EXISTS `AMC_CREDITOR` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` char(50) DEFAULT NULL COMMENT '名称',
  `type` int(2) DEFAULT NULL COMMENT '借款人类型',
  `person_id` char(50) DEFAULT NULL COMMENT '借款人身份证ID',
  `company_id` bigint(20) DEFAULT NULL COMMENT '公司ID',
  `orig_cmpy_id` bigint(20) DEFAULT NULL COMMENT '原始公司ID',
  `note` varchar(20) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.AMC_CREDITOR_DEBT
DROP TABLE IF EXISTS `AMC_CREDITOR_DEBT`;
CREATE TABLE IF NOT EXISTS `AMC_CREDITOR_DEBT` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `debt_id` bigint(20) NOT NULL DEFAULT '0',
  `creditor_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.AMC_DEBT
DROP TABLE IF EXISTS `AMC_DEBT`;
CREATE TABLE IF NOT EXISTS `AMC_DEBT` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `debtpack_id` bigint(20) unsigned NOT NULL COMMENT '债权包Id',
  `amc_id` bigint(20) unsigned NOT NULL COMMENT '资产公司编号 wensheng-2',
  `title` char(100) NOT NULL DEFAULT '-1' COMMENT '债权名称',
  `base_amount` bigint(20) NOT NULL DEFAULT '-1' COMMENT '本金 精确到分',
  `base_amount_desc` varchar(20) NOT NULL DEFAULT '-1' COMMENT '本金描述',
  `base_date` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '借款日期',
  `total_amount` bigint(20) NOT NULL DEFAULT '-1' COMMENT '本息合计 精确到分',
  `total_amount_desc` varchar(20) NOT NULL DEFAULT '-1' COMMENT '本息合计描述',
  `settle_date` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '基准日期',
  `court_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '法院id',
  `amc_debt_code` varchar(30) NOT NULL DEFAULT '-1' COMMENT 'amc债权编号',
  `edit_status` int(2) NOT NULL DEFAULT '-1' COMMENT '编辑状态	0-已删除， 1-发布， 2-未发布， 3-已放弃，4-已售出，5-待审核，6-草稿，7-已下架，8-审核未通过。',
  `publish_date` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '发布时间',
  `law_stat_desc` varchar(50) NOT NULL DEFAULT '-1' COMMENT '法律状态描述',
  `estimated_price` bigint(20) NOT NULL DEFAULT '-1' COMMENT '债权评估价',
  `amc_contact1` bigint(20) NOT NULL DEFAULT '-1' COMMENT '联系人1',
  `amc_contact2` bigint(20) NOT NULL DEFAULT '-1' COMMENT '联系人2',
  `is_recommanded` int(2) NOT NULL DEFAULT '-1' COMMENT '推荐 不推荐',
  `start_date` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '推荐开始日期',
  `end_date` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '推荐截至日期',
  `orig_creditor_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '原始债权人ID',
  `debt_desc` varchar(50) NOT NULL DEFAULT '-1' COMMENT '债权描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2133 DEFAULT CHARSET=utf8mb4 COMMENT='债权';

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.AMC_DEBTOR
DROP TABLE IF EXISTS `AMC_DEBTOR`;
CREATE TABLE IF NOT EXISTS `AMC_DEBTOR` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `debt_id` bigint(20) NOT NULL DEFAULT '-1',
  `company_id` bigint(20) unsigned NOT NULL COMMENT '公司ID',
  `company_branch_id` bigint(20) unsigned NOT NULL COMMENT '分公司ID',
  `debt_contract` char(30) NOT NULL DEFAULT '-1' COMMENT '借款合同编号',
  `note` varchar(50) NOT NULL DEFAULT '-1' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='债权人';

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.AMC_DEBTPACK
DROP TABLE IF EXISTS `AMC_DEBTPACK`;
CREATE TABLE IF NOT EXISTS `AMC_DEBTPACK` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` char(150) NOT NULL COMMENT '资产包名称',
  `notes` varchar(150) NOT NULL DEFAULT '-1' COMMENT '备注',
  `amc_company_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT 'AMC',
  `amc_debtpack_code` char(50) NOT NULL DEFAULT '-1' COMMENT '资产包编码',
  `pack_status` int(2) NOT NULL DEFAULT '-1' COMMENT '状态 可能的状态有:新引进包 打包卖出',
  `amc_group_id` int(6) NOT NULL DEFAULT '-1' COMMENT 'AMC内部所属组ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='资产包';

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.AMC_GRNTCTRCT
DROP TABLE IF EXISTS `AMC_GRNTCTRCT`;
CREATE TABLE IF NOT EXISTS `AMC_GRNTCTRCT` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `debt_id` bigint(20) unsigned NOT NULL COMMENT '债权Id',
  `origin_debt_id` bigint(20) unsigned NOT NULL COMMENT '原系统的债权Id',
  `origin_contract_id` bigint(20) unsigned NOT NULL COMMENT '原系统的contract Id',
  `amount` bigint(20) NOT NULL DEFAULT '-1' COMMENT '精确到分',
  `origin_grantor_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '原系统中的担保人Id',
  `grantor_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '现系统中的担保人Id',
  `type` int(2) NOT NULL DEFAULT '-1' COMMENT '担保方式 抵押+保证 抵押 保证',
  `notes` char(150) NOT NULL DEFAULT '-1',
  `contract_number` char(50) NOT NULL DEFAULT '-1' COMMENT '合同编号',
  `sign_date` datetime NOT NULL DEFAULT '1900-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COMMENT='债权担保合同';

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.AMC_GRNTOR
DROP TABLE IF EXISTS `AMC_GRNTOR`;
CREATE TABLE IF NOT EXISTS `AMC_GRNTOR` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type` int(2) NOT NULL DEFAULT '-1' COMMENT '担保人类型 自然人 公司法人',
  `cmpy_id` bigint(20) NOT NULL DEFAULT '-1',
  `origin_cmpy_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '原先的company id',
  `person_id` char(50) NOT NULL DEFAULT '-1',
  `name` char(30) DEFAULT '0',
  `notes` char(150) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1484 DEFAULT CHARSET=utf8mb4 COMMENT='债权担保人';

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.AMC_ORGCREDITOR_DEBTPACK
DROP TABLE IF EXISTS `AMC_ORGCREDITOR_DEBTPACK`;
CREATE TABLE IF NOT EXISTS `AMC_ORGCREDITOR_DEBTPACK` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creditor_id` bigint(20) NOT NULL DEFAULT '-1',
  `debtpack_id` bigint(20) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.AMC_ORIG_CREDITOR
DROP TABLE IF EXISTS `AMC_ORIG_CREDITOR`;
CREATE TABLE IF NOT EXISTS `AMC_ORIG_CREDITOR` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creditor_name` char(20) NOT NULL COMMENT '原始债权人名称',
  `note` varchar(20) DEFAULT NULL COMMENT '原始债权人描述信息',
  `bank_name` varchar(20) DEFAULT NULL COMMENT '银行名称',
  `branch_name` varchar(20) DEFAULT NULL COMMENT '分行名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bank_name` (`bank_name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.AMC_PERSON
DROP TABLE IF EXISTS `AMC_PERSON`;
CREATE TABLE IF NOT EXISTS `AMC_PERSON` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'mongodb contacts 关联',
  `dept_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '部门Id',
  `name` char(20) NOT NULL COMMENT '姓名',
  `phone_number` char(25) NOT NULL DEFAULT '-1' COMMENT '联系电话',
  `tel_number` char(25) NOT NULL DEFAULT '-1' COMMENT '固定电话',
  `gender` int(2) NOT NULL DEFAULT '-1',
  `personal_doc_type` int(2) NOT NULL DEFAULT '-1',
  `personal_doc` char(50) NOT NULL DEFAULT '-1',
  `nation` char(10) NOT NULL DEFAULT '-1' COMMENT '用CountryCode',
  `notes` varchar(200) NOT NULL DEFAULT '-1',
  `address` varchar(200) NOT NULL DEFAULT '-1',
  `amc_cmpy_id` bigint(20) unsigned NOT NULL COMMENT 'Amc Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.CURT_INFO
DROP TABLE IF EXISTS `CURT_INFO`;
CREATE TABLE IF NOT EXISTS `CURT_INFO` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `curt_name` char(50) DEFAULT NULL COMMENT '法院名称',
  `curt_province` char(20) DEFAULT NULL COMMENT '所在省',
  `curt_city` char(20) DEFAULT NULL COMMENT '所在市',
  `curt_county` char(20) DEFAULT NULL COMMENT '所在区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.
-- Dumping structure for table ZCC_AMC.RESOURCE_ROLE
DROP TABLE IF EXISTS `RESOURCE_ROLE`;
CREATE TABLE IF NOT EXISTS `RESOURCE_ROLE` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `asset_type` int(2) unsigned NOT NULL,
  `debt_type` int(2) unsigned NOT NULL,
  `access_role` int(2) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;