/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : atm

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2019-11-06 16:57:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `accountinfo`
-- ----------------------------
DROP TABLE IF EXISTS `accountinfo`;
CREATE TABLE `accountinfo` (
  `ID` varchar(20) NOT NULL COMMENT 'account’s ID',
  `CustID` int(11) NOT NULL COMMENT 'customer’s ID',
  `AccountType` int(11) NOT NULL COMMENT 'account’s type: 1.Saving; 2.Checking; 3.Loan',
  `CurrencyID` int(11) NOT NULL COMMENT 'currency’s type: 1.USD; 2.CNY; 3.JPY etc',
  `Money` double NOT NULL COMMENT 'money',
  `Con` int(11) NOT NULL,
  PRIMARY KEY (`ID`,`AccountType`,`CurrencyID`),
  KEY `AccountType` (`AccountType`),
  KEY `CurrencyID` (`CurrencyID`),
  KEY `accountinfo_ibfk_1` (`CustID`),
  CONSTRAINT `accountinfo_ibfk_1` FOREIGN KEY (`CustID`) REFERENCES `customerinfo` (`ID`),
  CONSTRAINT `accountinfo_ibfk_3` FOREIGN KEY (`CurrencyID`) REFERENCES `currencytype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of accountinfo
-- ----------------------------
INSERT INTO `accountinfo` VALUES ('00000001', '1', '1', '1', '0', '1');
INSERT INTO `accountinfo` VALUES ('00000002', '2', '1', '1', '868.989013671875', '1');
INSERT INTO `accountinfo` VALUES ('00000003', '3', '1', '1', '0', '1');
INSERT INTO `accountinfo` VALUES ('00000004', '2', '2', '1', '0', '0');
INSERT INTO `accountinfo` VALUES ('00000005', '2', '3', '1', '0', '0');
INSERT INTO `accountinfo` VALUES ('00000006', '2', '2', '1', '8226.936237445314', '1');
INSERT INTO `accountinfo` VALUES ('00000007', '2', '2', '1', '0', '0');
INSERT INTO `accountinfo` VALUES ('00000008', '2', '3', '1', '0', '0');
INSERT INTO `accountinfo` VALUES ('00000009', '2', '3', '1', '0', '0');
INSERT INTO `accountinfo` VALUES ('00000010', '2', '1', '1', '0', '1');
INSERT INTO `accountinfo` VALUES ('00000011', '2', '1', '1', '555', '1');
INSERT INTO `accountinfo` VALUES ('00000012', '2', '3', '1', '9989', '1');
INSERT INTO `accountinfo` VALUES ('00000013', '2', '2', '1', '6024.024', '1');
INSERT INTO `accountinfo` VALUES ('00000014', '2', '2', '1', '1000', '1');

-- ----------------------------
-- Table structure for `accounttype`
-- ----------------------------
DROP TABLE IF EXISTS `accounttype`;
CREATE TABLE `accounttype` (
  `ID` int(1) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of accounttype
-- ----------------------------
INSERT INTO `accounttype` VALUES ('1', 'Saving');
INSERT INTO `accounttype` VALUES ('2', 'Checking');
INSERT INTO `accounttype` VALUES ('3', 'Loan');

-- ----------------------------
-- Table structure for `currencytype`
-- ----------------------------
DROP TABLE IF EXISTS `currencytype`;
CREATE TABLE `currencytype` (
  `ID` int(2) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of currencytype
-- ----------------------------
INSERT INTO `currencytype` VALUES ('1', 'USD');
INSERT INTO `currencytype` VALUES ('2', 'CNY');
INSERT INTO `currencytype` VALUES ('3', 'JPY');

-- ----------------------------
-- Table structure for `customerinfo`
-- ----------------------------
DROP TABLE IF EXISTS `customerinfo`;
CREATE TABLE `customerinfo` (
  `ID` int(11) NOT NULL COMMENT 'customer’s ID',
  `Name` varchar(40) NOT NULL COMMENT 'customer’s name (first name and last name etc)',
  `Address` varchar(50) DEFAULT NULL COMMENT 'customer’s address',
  `Password` varchar(20) NOT NULL COMMENT 'password',
  `Username` varchar(40) NOT NULL COMMENT 'customized name for login etc',
  `PhoneNo` varchar(20) DEFAULT NULL COMMENT 'phone number',
  `Collateral` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customerinfo
-- ----------------------------
INSERT INTO `customerinfo` VALUES ('1', 'Johns Smith', '880 Brighton ST', '123456', 'Poor man', '874910433', null);
INSERT INTO `customerinfo` VALUES ('2', 'Tom Jackson', '66 Skytree Avenue', '123456', 'Rich man', '0371337198', 'Very rich man');
INSERT INTO `customerinfo` VALUES ('3', 'James Joseph', '777 Lucky Avenue', '123456', 'Customer', '0234215193', null);

-- ----------------------------
-- Table structure for `managerinfo`
-- ----------------------------
DROP TABLE IF EXISTS `managerinfo`;
CREATE TABLE `managerinfo` (
  `ID` int(11) NOT NULL COMMENT 'customer’s ID',
  `Password` varchar(20) NOT NULL COMMENT 'password',
  `Username` varchar(40) NOT NULL COMMENT 'customized name for login etc',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of managerinfo
-- ----------------------------
INSERT INTO `managerinfo` VALUES ('1', 'money', 'Happy admin');

-- ----------------------------
-- Table structure for `marketopdetails`
-- ----------------------------
DROP TABLE IF EXISTS `marketopdetails`;
CREATE TABLE `marketopdetails` (
  `OpID` varchar(20) NOT NULL COMMENT 'operation ID',
  `SAID` varchar(20) NOT NULL COMMENT 'security account ID',
  `StockID` varchar(7) NOT NULL COMMENT 'stock ID',
  `ShareNumber` bigint(20) NOT NULL COMMENT 'the number of shares customer is holding',
  `CostPrice` double DEFAULT NULL,
  `OpTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`OpID`),
  KEY `SAID` (`SAID`),
  KEY `StockID` (`StockID`),
  CONSTRAINT `marketopdetails_ibfk_1` FOREIGN KEY (`SAID`) REFERENCES `securityinfo` (`ID`),
  CONSTRAINT `marketopdetails_ibfk_2` FOREIGN KEY (`StockID`) REFERENCES `stockinfo` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of marketopdetails
-- ----------------------------

-- ----------------------------
-- Table structure for `securityinfo`
-- ----------------------------
DROP TABLE IF EXISTS `securityinfo`;
CREATE TABLE `securityinfo` (
  `ID` varchar(20) NOT NULL,
  `AccountID` varchar(20) NOT NULL COMMENT 'account’s ID to which this security account is linked',
  PRIMARY KEY (`ID`),
  KEY `AccountID` (`AccountID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of securityinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `sharesinfo`
-- ----------------------------
DROP TABLE IF EXISTS `sharesinfo`;
CREATE TABLE `sharesinfo` (
  `ID` varchar(20) NOT NULL,
  `SecID` varchar(20) NOT NULL COMMENT 'security account ID',
  `StockID` varchar(7) NOT NULL COMMENT 'stock ID',
  `ShareNumber` int(11) NOT NULL COMMENT 'the number of shares customer is holding',
  PRIMARY KEY (`ID`),
  KEY `SAID` (`SecID`),
  KEY `StockID` (`StockID`),
  CONSTRAINT `sharesinfo_ibfk_1` FOREIGN KEY (`SecID`) REFERENCES `securityinfo` (`ID`),
  CONSTRAINT `sharesinfo_ibfk_2` FOREIGN KEY (`StockID`) REFERENCES `stockinfo` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sharesinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `stockinfo`
-- ----------------------------
DROP TABLE IF EXISTS `stockinfo`;
CREATE TABLE `stockinfo` (
  `ID` varchar(7) NOT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `CurrentPrice` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stockinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `stockpricechange`
-- ----------------------------
DROP TABLE IF EXISTS `stockpricechange`;
CREATE TABLE `stockpricechange` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `systime` timestamp NULL DEFAULT NULL,
  `stockID` varchar(20) DEFAULT NULL,
  `pricechanged` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stockID` (`stockID`),
  CONSTRAINT `stockpricechange_ibfk_1` FOREIGN KEY (`stockID`) REFERENCES `stockinfo` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stockpricechange
-- ----------------------------

-- ----------------------------
-- Table structure for `transactiondetails`
-- ----------------------------
DROP TABLE IF EXISTS `transactiondetails`;
CREATE TABLE `transactiondetails` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `OpAccountID` varchar(20) NOT NULL COMMENT 'the operation account ID ',
  `TargetAccountID` varchar(20) DEFAULT NULL COMMENT 'the target account ID, null if no target',
  `Amount` double NOT NULL COMMENT 'the amount of money being operated',
  `CurrencyId` int(11) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `CustomerId` int(11) NOT NULL,
  `Info` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `OpAccountID` (`OpAccountID`),
  KEY `TargetAccountID` (`TargetAccountID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of transactiondetails
-- ----------------------------


-- ----------------------------
-- CREATE OR REPLACE VIEW pricechanged
-- ----------------------------
CREATE OR REPLACE VIEW `pricechanged` AS
    SELECT 
        s.stockID AS ID, s.pricechanged
    FROM
        (SELECT 
            t.stockID, t.pricechanged
        FROM
            (SELECT 
            stockID, MAX(systime) latesttime
        FROM
            StockPriceChange
        GROUP BY stockID) s, StockPriceChange t
        WHERE
            s.stockID = t.stockID
                AND t.systime = s.latesttime) s;

-- ----------------------------
-- CREATE PROCEDURE StockPriceChange()
-- ----------------------------
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `StockPriceChange`()
BEGIN

insert into StockPriceChange
select null,sysdate(),ID,format(-0.2 + rand()*0.4,4) from StockInfo; 

UPDATE StockInfo t 
SET 
    t.currentprice = t.currentprice * (SELECT 
            (1 + s.pricechanged)
        FROM
            pricechanged s
        WHERE
            s.ID = t.ID)
WHERE
    EXISTS( SELECT 
            1
        FROM
            pricechanged s
        WHERE
            s.ID = t.ID);

UPDATE StockInfo t 
SET 
    t.currentprice = FORMAT(t.currentprice, 2);
            
END$$
DELIMITER ;

-- ----------------------------
-- CREATE EVENT changestockprice
-- ----------------------------
CREATE EVENT changestockprice
    ON SCHEDULE EVERY 5 SECOND
    DO
      call StockPriceChange();
