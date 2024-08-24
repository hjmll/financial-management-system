CREATE TABLE `bankcard` (
  `card_number` varchar(19) NOT NULL,
  `bank_name` varchar(50) NOT NULL,
  `balance` double(18,2) NOT NULL DEFAULT ''0.00'',
  `last_updated` date NOT NULL,
  `fund_account` varchar(45) NOT NULL,
  `trading_account` varchar(45) NOT NULL,
  PRIMARY KEY (`card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `customer` (
  `customer_type` varchar(10) NOT NULL,
  `id_type` varchar(10) NOT NULL,
  `id_number` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `gender` varchar(2) NOT NULL,
  `phone` char(11) NOT NULL,
  `risk_level` enum('低','中','高') NOT NULL,
  `fund_account` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`customer_type`,`id_type`,`id_number`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `holding` (
  `product_code` varchar(20) NOT NULL,
  `card_number` varchar(19) NOT NULL,
  `shares` double(15,4) NOT NULL,
  `update_date` date NOT NULL,
  PRIMARY KEY (`product_code`,`card_number`),
  KEY `card_number` (`card_number`),
  CONSTRAINT `holding_ibfk_1` FOREIGN KEY (`product_code`) REFERENCES `product` (`product_code`),
  CONSTRAINT `holding_ibfk_2` FOREIGN KEY (`card_number`) REFERENCES `bankcard` (`card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `request` (
   `id` char(14) NOT NULL,
   `product_code` varchar(20) NOT NULL,
   `card_number` varchar(19) NOT NULL,
   `type` enum('申购','赎回') NOT NULL,
   `amount` decimal(15,2) DEFAULT NULL,
   `shares` decimal(15,4) DEFAULT NULL,
   `date` date NOT NULL,
   `status` varchar(20) NOT NULL,
   PRIMARY KEY (`id`),
   KEY `card_number` (`card_number`),
   KEY `request_ibfk_1` (`product_code`),
   CONSTRAINT `request_ibfk_1` FOREIGN KEY (`product_code`) REFERENCES `product` (`product_code`),
   CONSTRAINT `request_ibfk_2` FOREIGN KEY (`card_number`) REFERENCES `bankcard` (`card_number`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

 CREATE TABLE `settlement` (
   `date` date NOT NULL,
   `nav` decimal(10,4) NOT NULL,
   `product_code` varchar(20) NOT NULL,
   PRIMARY KEY (`date`,`product_code`),
   KEY `settlement_ibfk_1` (`product_code`),
   CONSTRAINT `settlement_ibfk_1` FOREIGN KEY (`product_code`) REFERENCES `product` (`product_code`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `transaction` (
   `id` char(14) NOT NULL,
   `card_number` varchar(19) NOT NULL,
   `date` date NOT NULL,
   `description` varchar(20) NOT NULL,
   `amount` decimal(18,2) NOT NULL DEFAULT '0.00',
   `type` enum('IN','OUT') NOT NULL,
   PRIMARY KEY (`id`),
   KEY `card_number` (`card_number`),
   CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`card_number`) REFERENCES `bankcard` (`card_number`),
   CONSTRAINT `transaction_chk_1` CHECK ((`amount` >= 0))
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
