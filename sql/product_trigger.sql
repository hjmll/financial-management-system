
-- 触发器1

DELIMITER $$

CREATE TRIGGER insert_product_quotation
    AFTER INSERT ON product
    FOR EACH ROW
BEGIN
    -- 插入到 product_quotation 表中
    INSERT INTO product_quotation (productCode, productNetValue, tDate)
    VALUES (NEW.productCode, NEW.productNetValue, CURDATE());
    END$$

DELIMITER ;
-- 测试触发器1是否生效
INSERT INTO product (productCode, productName, productNetValue, netvalueDate, riskLevel, productType, description, productStatus)
VALUES ('PRODUCT127', 'Sample Fund', 900.00, CURDATE(), 'Low', 'Equity', 'Sample equity fund', 'Active');

INSERT INTO product (productCode, productName, productNetValue, netvalueDate, riskLevel, productType, description, productStatus)
VALUES ('PRODUCT128', 'Sample Fund', 800.00, CURDATE(), 'Low', 'Equity', 'Sample equity fund', 'Active');

-- 触发器2


DELIMITER $$

CREATE TRIGGER update_product_quotation
    AFTER UPDATE ON product
    FOR EACH ROW
BEGIN
    -- 检查 netvalueDate 是否发生变化
    IF NEW.netvalueDate <> OLD.netvalueDate THEN
        -- 插入到 product_quotation 表中
        INSERT INTO product_quotation (productCode, productNetValue, tDate)
        VALUES (NEW.productCode, NEW.productNetValue, NEW.netvalueDate);
    END IF;
END$$

DELIMITER ;

-- 测试触发器2是否生效

UPDATE product
SET productNetValue = 200.00, netvalueDate = '2024-08-24'
WHERE productCode = 'PRODUCT127';

DROP TRIGGER IF EXISTS update_product_quotation;

-- 触发器2.1
DELIMITER $$

CREATE TRIGGER update_product_quotation2
    AFTER UPDATE ON product
    FOR EACH ROW
BEGIN
    -- 检查 productNetValue 是否发生变化
    IF NEW.productNetValue <> OLD.productNetValue THEN
        -- 更新 product_quotation 表中对应的记录
        UPDATE product_quotation
        SET productNetValue = NEW.productNetValue
        WHERE productCode = NEW.productCode AND tDate = NEW.netvalueDate;
    END IF;
END$$

DELIMITER ;

-- 测试触发器2.1是否生效
UPDATE product
SET productNetValue = 199.00, netvalueDate = '2024-08-24'
WHERE productCode = 'PRODUCT127';