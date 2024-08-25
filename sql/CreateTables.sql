
-- 按顺序，每一条语句单独执行

CREATE TABLE `product` (
                           `product_code` varchar(20) NOT NULL COMMENT '产品代码',
                           `product_net_value` decimal(10,4) NOT NULL COMMENT '产品当日净值',
                           `net_value_date` date NOT NULL COMMENT '净值日期',
                           `product_name` varchar(50) NOT NULL COMMENT '产品名称',
                           `risk_level` enum('低','中','高') DEFAULT NULL COMMENT '风险等级',
                           `product_type` varchar(20) DEFAULT NULL COMMENT '产品类型',
                           `description` text COMMENT '产品描述',
                           `product_status` char(255)  DEFAULT NULL COMMENT '产品状态',
                           PRIMARY KEY (`product_code`),
                           KEY `idx_product_code` (`product_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品表';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bankcard` (
                            `card_number` varchar(19) NOT NULL,
                            `bank_name` varchar(50) NOT NULL,
                            `balance` double(18,2) NOT NULL DEFAULT 0.00,
                            `last_updated` date NOT NULL,
                            `fund_account` varchar(45) NOT NULL,
                            `trading_account` varchar(45) NOT NULL,
                            PRIMARY KEY (`card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `holding` (
                           `product_code` varchar(20) NOT NULL,
                           `card_number` varchar(19) NOT NULL,
                           `shares` double(15,4) NOT NULL,
                           `update_date` date NOT NULL,
                           PRIMARY KEY (`product_code`,`card_number`),
                           KEY `card_number` (`card_number`),
                           CONSTRAINT `holding_ibfk_1` FOREIGN KEY (`product_code`) REFERENCES `product` (`product_code`),
                           CONSTRAINT `holding_ibfk_2` FOREIGN KEY (`card_number`) REFERENCES `bankcard` (`card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product_quotation` (
                                     `date` date NOT NULL,
                                     `nav` decimal(10,4) NOT NULL,
                                     `product_code` varchar(20) NOT NULL,
                                     PRIMARY KEY (`date`,`product_code`),
                                     KEY `settlement_ibfk_1` (`product_code`),
                                     CONSTRAINT `settlement_ibfk_1` FOREIGN KEY (`product_code`) REFERENCES `product` (`product_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 新增产品触发器
DELIMITER $$
CREATE TRIGGER `product_AFTER_INSERT` AFTER INSERT ON `product` FOR EACH ROW
BEGIN
    -- 插入到 product_quotation 表中
    INSERT INTO product_quotation (date, product_code, nav)
    VALUES (NEW.net_value_date, NEW.product_code, NEW.product_net_value);
    END$$
    DELIMITER ;

-- 产品净值更新触发器
DELIMITER $$
    CREATE TRIGGER `product_AFTER_UPDATE` AFTER UPDATE ON `product` FOR EACH ROW
    BEGIN
        -- 检查 netvalueDate 是否发生变化
        IF NEW.net_value_date <> OLD.net_value_date THEN
        -- 插入到 product_quotation 表中
        INSERT INTO product_quotation (date, product_code, nav)
        VALUES (NEW.net_value_date, NEW.product_code, NEW.product_net_value);
    END IF;
    END$$
    DELIMITER ;

-- 申购确认触发器
DELIMITER $$
    CREATE TRIGGER `request_AFTER_UPDATE` AFTER UPDATE ON `request` FOR EACH ROW
    BEGIN
        IF OLD.status = '待确认' AND NEW.status = '确认' AND NEW.type = '申购' THEN
        -- 尝试插入新记录，如果已存在则更新shares
        INSERT INTO holding (product_code, card_number, shares,update_date)
        VALUES (NEW.product_code, NEW.card_number, NEW.shares, NEW.date)
        ON DUPLICATE KEY UPDATE shares = shares + NEW.shares, update_date = NEW.date;
    END IF;
    END$$
    DELIMITER ;

-- 赎回确认触发器
DELIMITER $$
    CREATE TRIGGER `transaction_AFTER_INSERT` AFTER INSERT ON `transaction` FOR EACH ROW
    BEGIN
        IF NEW.description = '赎回确认' THEN
        -- 更新银行卡金额
        UPDATE bankcard SET balance = balance + NEW.amount, last_updated = NEW.date;
    END IF;
    END$$
    DELIMITER ;