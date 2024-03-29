/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : atm

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2019-11-11 21:28:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Create and Use Database `ATM`
-- ----------------------------
create database ATM;

use ATM;

-- ----------------------------
-- Table structure for `accountinfo`
-- ----------------------------
DROP TABLE IF EXISTS `accountinfo`;
CREATE TABLE `accountinfo` (
  `ID` varchar(20) NOT NULL COMMENT 'account??s ID',
  `CustID` int(11) NOT NULL COMMENT 'customer??s ID',
  `AccountType` int(11) NOT NULL COMMENT 'account??s type: 1.Saving; 2.Checking; 3.Loan',
  `CurrencyID` int(11) NOT NULL COMMENT 'currency??s type: 1.USD; 2.CNY; 3.JPY etc',
  `Money` double(32,2) NOT NULL COMMENT 'money',
  `Con` int(11) NOT NULL,
  PRIMARY KEY (`ID`,`AccountType`,`CurrencyID`),
  KEY `AccountType` (`AccountType`),
  KEY `CurrencyID` (`CurrencyID`),
  KEY `accountinfo_ibfk_1` (`CustID`),
  KEY `ID` (`ID`),
  CONSTRAINT `accountinfo_ibfk_3` FOREIGN KEY (`CurrencyID`) REFERENCES `currencytype` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of accountinfo
-- ----------------------------
INSERT INTO `accountinfo` VALUES ('00000001', '1', '1', '1', '1.00', '1');
INSERT INTO `accountinfo` VALUES ('00000001', '1', '1', '2', '0.00', '1');
INSERT INTO `accountinfo` VALUES ('00000001', '1', '1', '3', '0.00', '1');
INSERT INTO `accountinfo` VALUES ('00000002', '2', '1', '1', '9971440.00', '1');
INSERT INTO `accountinfo` VALUES ('00000002', '2', '1', '2', '0.00', '1');
INSERT INTO `accountinfo` VALUES ('00000002', '2', '1', '3', '0.00', '1');
INSERT INTO `accountinfo` VALUES ('00000003', '3', '1', '1', '0.00', '1');
INSERT INTO `accountinfo` VALUES ('00000003', '3', '1', '2', '-30.00', '1');
INSERT INTO `accountinfo` VALUES ('00000003', '3', '1', '3', '0.00', '1');
INSERT INTO `accountinfo` VALUES ('00000004', '2', '2', '1', '0.00', '0');
INSERT INTO `accountinfo` VALUES ('00000005', '2', '3', '1', '0.00', '0');
INSERT INTO `accountinfo` VALUES ('00000006', '2', '2', '1', '6239.39', '0');
INSERT INTO `accountinfo` VALUES ('00000007', '2', '2', '1', '0.00', '0');
INSERT INTO `accountinfo` VALUES ('00000008', '2', '3', '1', '0.00', '0');
INSERT INTO `accountinfo` VALUES ('00000009', '2', '3', '1', '0.00', '0');
INSERT INTO `accountinfo` VALUES ('00000010', '2', '1', '1', '10200.00', '1');
INSERT INTO `accountinfo` VALUES ('00000011', '2', '1', '1', '555.00', '1');
INSERT INTO `accountinfo` VALUES ('00000012', '6', '1', '1', '-10.00', '1');
INSERT INTO `accountinfo` VALUES ('00000012', '6', '1', '2', '0.00', '1');

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
INSERT INTO `accounttype` VALUES ('1', 'Checking');
INSERT INTO `accounttype` VALUES ('2', 'Saving');
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
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'customer??s ID',
  `Name` varchar(40) NOT NULL COMMENT 'customer??s name (first name and last name etc)',
  `Address` varchar(50) DEFAULT NULL COMMENT 'customer??s address',
  `Password` varchar(20) NOT NULL COMMENT 'password',
  `Username` varchar(40) NOT NULL COMMENT 'customized name for login etc',
  `PhoneNo` varchar(20) DEFAULT NULL COMMENT 'phone number',
  `Collateral` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customerinfo
-- ----------------------------
INSERT INTO `customerinfo` VALUES ('1', 'Lucy Lucky', '680 CommonWealth Ave', '123456', 'Lucy', '0482619522', 'OOD');
INSERT INTO `customerinfo` VALUES ('2', 'Tom Jackson', '66 Skytree Avenue', '123456', 'Rich man', '0371337198', 'Very rich man');
INSERT INTO `customerinfo` VALUES ('3', 'James Joseph', '777 Lucky Avenue', '123456', 'Customer', '0234215193', null);
INSERT INTO `customerinfo` VALUES ('4', '1', '1', '1', '1', '1', '1');
INSERT INTO `customerinfo` VALUES ('5', '2', '3', '2', '1', '', '');

-- ----------------------------
-- Table structure for `managerinfo`
-- ----------------------------
DROP TABLE IF EXISTS `managerinfo`;
CREATE TABLE `managerinfo` (
  `ID` int(11) NOT NULL COMMENT 'customer??s ID',
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
  `CostPrice` double(32,2) DEFAULT NULL,
  `OpTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`OpID`),
  KEY `SAID` (`SAID`),
  KEY `StockID` (`StockID`),
  CONSTRAINT `marketopdetails_ibfk_1` FOREIGN KEY (`SAID`) REFERENCES `securityinfo` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `marketopdetails_ibfk_2` FOREIGN KEY (`StockID`) REFERENCES `stockinfo` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT
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
  `AccountID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'account ID to which this security account is linked',
  PRIMARY KEY (`ID`),
  KEY `AccountID` (`AccountID`),
  CONSTRAINT `FK1` FOREIGN KEY (`AccountID`) REFERENCES `accountinfo` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of securityinfo
-- ----------------------------
INSERT INTO `securityinfo` VALUES ('5', '00000002');
INSERT INTO `securityinfo` VALUES ('1', '00000006');

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
  CONSTRAINT `sharesinfo_ibfk_1` FOREIGN KEY (`SecID`) REFERENCES `securityinfo` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `sharesinfo_ibfk_2` FOREIGN KEY (`StockID`) REFERENCES `stockinfo` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sharesinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `stockinfo`
-- ----------------------------
DROP TABLE IF EXISTS `stockinfo`;
CREATE TABLE `stockinfo` (
  `ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Name` varchar(20) DEFAULT NULL,
  `CurrentPrice` double(32,2) DEFAULT NULL,
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
  CONSTRAINT `FK_stockpricechange_stockinfo` FOREIGN KEY (`stockID`) REFERENCES `stockinfo` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=75324 DEFAULT CHARSET=utf8;

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
  `Amount` double(32,2) NOT NULL COMMENT 'the amount of money being operated',
  `CurrencyId` int(11) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `CustomerId` int(11) NOT NULL,
  `Info` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `OpAccountID` (`OpAccountID`),
  KEY `TargetAccountID` (`TargetAccountID`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of transactiondetails
-- ----------------------------
INSERT INTO `transactiondetails` VALUES ('1', '00000002', '', '100.00', '1', 'Tom Jackson', '2', 'Withdrawing cash from checking account');
INSERT INTO `transactiondetails` VALUES ('2', '00000002', '', '100.00', '1', 'Tom Jackson', '2', 'Saving Money to Checking Account');
INSERT INTO `transactiondetails` VALUES ('3', '00000002', '00000010', '-100.00', '1', 'Tom Jackson', '2', 'Transfer Money');
INSERT INTO `transactiondetails` VALUES ('4', '00000002', '00000010', '-100.00', '1', 'Tom Jackson', '2', 'Transfer Money');
INSERT INTO `transactiondetails` VALUES ('5', '00000002', '00000010', '-100.00', '1', 'Tom Jackson', '2', 'Transfer Money');
INSERT INTO `transactiondetails` VALUES ('6', '00000002', '00000010', '-100.00', '1', 'Tom Jackson', '2', 'Transfer Money');
INSERT INTO `transactiondetails` VALUES ('7', '00000002', '00000010', '-100.00', '1', 'Tom Jackson', '2', 'Transfer Money');
INSERT INTO `transactiondetails` VALUES ('8', '00000002', '00000010', '-90.00', '1', 'Tom Jackson', '2', 'Transfer Money');
INSERT INTO `transactiondetails` VALUES ('9', '00000002', '00000010', '-100.00', '1', 'Tom Jackson', '2', 'Transfer Money');
INSERT INTO `transactiondetails` VALUES ('10', '00000002', '00000010', '-100.00', '1', 'Tom Jackson', '2', 'Transfer Money');
INSERT INTO `transactiondetails` VALUES ('11', '00000010', '00000002', '100.00', '1', 'Tom Jackson', '2', 'Transfer Money');
INSERT INTO `transactiondetails` VALUES ('12', '00000006', '', '100.00', '1', 'Tom Jackson', '2', 'Withdrawing cash from saving account');
INSERT INTO `transactiondetails` VALUES ('13', '00000006', '', '2000.00', '1', 'Tom Jackson', '2', 'Withdrawing cash from saving account');
INSERT INTO `transactiondetails` VALUES ('14', '00000006', '', '100.00', '1', 'Tom Jackson', '2', 'Saving Money to Saving Account');
INSERT INTO `transactiondetails` VALUES ('15', '00000012', '', '100.00', '1', 'Tom Jackson', '2', 'Requesting loans');
INSERT INTO `transactiondetails` VALUES ('16', '00000002', '', '-100.00', '1', 'Tom Jackson', '2', 'Returning loans');
INSERT INTO `transactiondetails` VALUES ('17', '00000020', '', '500.00', '1', 'Johns Smith', '1', 'Saving Money to Saving Account');
INSERT INTO `transactiondetails` VALUES ('18', '00000020', '', '100.00', '1', 'Johns Smith', '1', 'Saving Money to Saving Account');
INSERT INTO `transactiondetails` VALUES ('19', '00000002', '', '10000000.00', '1', 'Tom Jackson', '2', 'Saving Money to Checking Account');
INSERT INTO `transactiondetails` VALUES ('20', '00000002', '', '-9989.00', '1', 'Tom Jackson', '2', 'Returning loans');
INSERT INTO `transactiondetails` VALUES ('21', '00000021', '', '5000.00', '1', 'Tom Jackson', '2', 'Saving Money to Saving Account');
INSERT INTO `transactiondetails` VALUES ('22', '00000002', '', '10000.00', '1', 'Tom Jackson', '2', 'Withdrawing cash from checking account');
INSERT INTO `transactiondetails` VALUES ('23', '00000002', '', '2000.00', '1', 'Tom Jackson', '2', 'Saving Money to Checking Account');
INSERT INTO `transactiondetails` VALUES ('24', '00000002', '', '1000.00', '1', 'Tom Jackson', '2', 'Withdrawing cash from checking account');
INSERT INTO `transactiondetails` VALUES ('25', '00000021', '', '2000.00', '1', 'Tom Jackson', '2', 'Withdrawing cash from saving account');
INSERT INTO `transactiondetails` VALUES ('26', '00000021', '', '50000.00', '1', 'Tom Jackson', '2', 'Saving Money to Saving Account');
INSERT INTO `transactiondetails` VALUES ('27', '00000021', '', '50100.00', '1', 'Tom Jackson', '2', 'Withdrawing cash from saving account');
INSERT INTO `transactiondetails` VALUES ('28', '00000021', '', '50000.00', '1', 'Tom Jackson', '2', 'Saving Money to Saving Account');
INSERT INTO `transactiondetails` VALUES ('29', '00000021', '', '100000.00', '1', 'Tom Jackson', '2', 'Saving Money to Saving Account');
INSERT INTO `transactiondetails` VALUES ('30', '00000021', '', '1000000.00', '1', 'Tom Jackson', '2', 'Saving Money to Saving Account');
INSERT INTO `transactiondetails` VALUES ('31', '00000021', '', '10000000.00', '1', 'Tom Jackson', '2', 'Saving Money to Saving Account');
INSERT INTO `transactiondetails` VALUES ('32', '00000021', '', '1000000.00', '1', 'Tom Jackson', '2', 'Saving Money to Saving Account');
INSERT INTO `transactiondetails` VALUES ('33', '00000021', '', '1000.00', '1', 'Tom Jackson', '2', 'Saving Money to Saving Account');
INSERT INTO `transactiondetails` VALUES ('34', '00000002', '00000010', '-10000.00', '1', 'Tom Jackson', '2', 'Transfer Money');
INSERT INTO `transactiondetails` VALUES ('35', '00000010', '00000002', '10000.00', '1', 'Tom Jackson', '2', 'Transfer Money');
INSERT INTO `transactiondetails` VALUES ('36', '00000021', '', '100000.00', '1', 'Tom Jackson', '2', 'Saving Money to Saving Account');

-- ----------------------------
-- View structure for `pricechanged`
-- ----------------------------
DROP VIEW IF EXISTS `pricechanged`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `pricechanged` AS select `s`.`stockID` AS `ID`,`s`.`pricechanged` AS `pricechanged` from (select `t`.`stockID` AS `stockID`,`t`.`pricechanged` AS `pricechanged` from ((select `stockpricechange`.`stockID` AS `stockID`,max(`stockpricechange`.`systime`) AS `latesttime` from `stockpricechange` group by `stockpricechange`.`stockID`) `s` join `stockpricechange` `t`) where ((`s`.`stockID` = `t`.`stockID`) and (`t`.`systime` = `s`.`latesttime`))) `s` ;

-- ----------------------------
-- Procedure structure for `StockPriceChanges`
-- ----------------------------
DROP PROCEDURE IF EXISTS `StockPriceChanges`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `StockPriceChanges`()
BEGIN
insert into StockPriceChange
select null,sysdate(),ID,format(-0.1 + rand()*0.2,4) as changed from stockinfo;

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
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `changestockprice`
-- ----------------------------
DROP EVENT IF EXISTS `changestockprice`;
SET GLOBAL event_scheduler = ON;

CREATE EVENT changestockprice
    ON SCHEDULE EVERY 10 SECOND
    DO
      call StockPriceChanges();
