DROP DATABASE IF EXISTS `wholesale`;

CREATE DATABASE `wholesale`;

use wholesale;

CREATE TABLE chipboard_size (
	id 			INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    length 		INT NOT NULL COMMENT 'unit: milimeters',
    width		INT NOT NULL COMMENT 'unit: milimeters',
    thicknes 	INT NOT NULL COMMENT 'unit: milimeters'
);

CREATE TABLE chipboard (
	id		INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sizeId	INT NOT NULL,
    cost	DOUBLE NOT NULL,
    
    FOREIGN KEY (sizeId) REFERENCES chipboard_size(id)
);

CREATE TABLE warehouse (
	chipboardId	INT NOT NULL,
    quantity	INT,
    
    FOREIGN KEY (chipboardId) REFERENCES chipboard(id)
);

CREATE TABLE customers ( 
	id			INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NIP			INT NOT NULL UNIQUE KEY,
    name		VARCHAR(45),
    discount	DOUBLE NOT NULL
);

CREATE TABLE orders ( /* USUNALEM CHARGE */
	id				INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customerId		INT NOT NULL,
    paymentStatus	ENUM('DONE', 'PENDING'),
    orderStatus		ENUM('SUSPENDED', 'PENDING', 'DONE', 'LACK_OF_MATERIALS', 'CONSTRUCTION', 'ISSUED'),
    
    FOREIGN KEY (customerId) REFERENCES customers(id)
);

CREATE TABLE order_item (
	chipboardId		INT NOT NULL,
    quantity		INT,
    orderId			INT NOT NULL,
    
    FOREIGN KEY (chipboardId) REFERENCES chipboard(Id),
    FOREIGN KEY (orderId) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE TABLE income (
	operationId		INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    operation_value	DOUBLE,
    orderId			INT NOT NULL,
    operation_date	DATETIME,
    
    FOREIGN KEY (orderId) REFERENCES orders(id) ON DELETE CASCADE
);

/*frequent searches*/
CREATE INDEX idx ON order_item (orderId);


DELIMITER $$
CREATE TRIGGER sizes_before_insert
BEFORE INSERT ON chipboard_size
FOR EACH ROW
BEGIN
	IF (new.length < 5 OR new.width < 5 OR new.thicknes < 5
		OR
        new.length > 4000 OR new.width > 4000 OR new.thicknes > 50) THEN
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'InvalidChipboardSizeException';
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER sizes_before_update
BEFORE UPDATE ON chipboard_size
FOR EACH ROW
BEGIN
	IF (new.length < 5 OR new.width < 5 OR new.thicknes < 5
		OR
        new.length > 4000 OR new.width > 4000 OR new.thicknes > 50) THEN
		SIGNAL SQLSTATE '45001'
        SET MESSAGE_TEXT = 'InvalidChipboardSizeException';
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER sizes_before_delete
BEFORE DELETE ON chipboard_size
FOR EACH ROW
BEGIN
	IF (SELECT EXISTS (SELECT *
					   FROM chipboard
                       WHERE sizeId = old.id)) THEN
		SIGNAL SQLSTATE '45002'
        SET MESSAGE_TEXT = 'UsedInOtherDataException';
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER orders_after_update
BEFORE UPDATE ON orders
FOR EACH ROW
BEGIN    
	IF (new.paymentStatus = 'DONE') THEN
		SET @var :=(SELECT SUM(cost)*cust.discount
					FROM order_item items
					JOIN chipboard board
						ON board.id = items.chipboardId
					JOIN customers cust
						ON cust.id = old.customerId
					WHERE items.orderId = old.id);
		INSERT INTO income (operation_value, orderId, operation_date)
        VALUE (@var, old.id, NOW());
        
                          
		UPDATE customers
		SET discount = discount + @var/10000
		WHERE id = old.customerId;
	END IF;
    
	IF (new.orderStatus = 'ISSUED') THEN
		CALL updateWarehouse(old.id);
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE updateWarehouse(IN id INT)
BEGIN    
	DECLARE v_finished INTEGER DEFAULT 0;
    DECLARE tempId INTEGER DEFAULT 0;
    DECLARE tempQ INTEGER DEFAULT 0;
    
    DECLARE myCursor CURSOR FOR 
	SELECT chipboardId, quantity
	FROM order_item
	WHERE orderId = id;
    
    DECLARE CONTINUE HANDLER 
	FOR NOT FOUND SET v_finished = 1;
    
    OPEN myCursor;
    
    substract: LOOP
		FETCH myCursor INTO tempId, tempQ;
        IF (v_finished = 1) THEN
			LEAVE substract;
		END IF;
        
        UPDATE warehouse
        SET quantity = quantity - tempQ
        WHERE chipboardId = tempId;
    END LOOP;
    
END $$
DELIMITER ;

CREATE USER 'office'@'localhost' IDENTIFIED BY '1234';

GRANT SELECT
	ON wholesale.chipboard
    TO 'office'@'localhost';
    
GRANT SELECT
	ON wholesale.chipboard_size
    TO 'office'@'localhost';
    
GRANT SELECT, INSERT, UPDATE
	ON wholesale.customers
    TO 'office'@'localhost';
    
GRANT SELECT
	ON wholesale.order_item
    TO 'office'@'localhost';
    
GRANT SELECT, INSERT
	ON wholesale.orders
    TO 'office'@'localhost';
    
GRANT SELECT
	ON wholesale.warehouse
    TO 'office'@'localhost';
    
flush PRIVILEGES;

CREATE USER 'ceo'@'localhost' IDENTIFIED BY '1234';

GRANT SELECT, INSERT, UPDATE, DELETE
	ON wholesale.chipboard
    TO 'ceo'@'localhost';
    
GRANT SELECT, INSERT, UPDATE, DELETE
	ON wholesale.chipboard_size
    TO 'ceo'@'localhost';
    
GRANT SELECT, INSERT, UPDATE
	ON wholesale.customers
    TO 'ceo'@'localhost';
    
GRANT SELECT
	ON wholesale.income
    TO 'ceo'@'localhost';
    
GRANT SELECT
	ON wholesale.order_item
    TO 'ceo'@'localhost';
    
GRANT SELECT, INSERT, DELETE
	ON wholesale.orders
    TO 'ceo'@'localhost';
    
GRANT SELECT, INSERT, UPDATE
	ON wholesale.warehouse
    TO 'ceo'@'localhost';
    
flush PRIVILEGES;

CREATE USER 'warehouseman'@'localhost' IDENTIFIED BY '1234';

GRANT SELECT
	ON wholesale.chipboard
    TO 'warehouseman'@'localhost';
    
GRANT SELECT
	ON wholesale.chipboard_size
    TO 'warehouseman'@'localhost';
    
GRANT SELECT, UPDATE
	ON wholesale.order_item
    TO 'warehouseman'@'localhost';
    
GRANT SELECT, UPDATE
	ON wholesale.orders
    TO 'warehouseman'@'localhost';
    
GRANT SELECT, UPDATE
	ON wholesale.warehouse
    TO 'warehouseman'@'localhost';
    
flush PRIVILEGES;

DELIMITER $$
CREATE PROCEDURE fill_chipboard_table()
BEGIN
DECLARE bound INT UNSIGNED DEFAULT 100;
DECLARE iterator INT UNSIGNED DEFAULT 0;

WHILE iterator < bound do
	IF (iterator < 33) THEN
		INSERT INTO wholesale.chipboard (sizeId, cost)
        VALUE (1, FLOOR(RAND()*2000 + 1000));
	ELSEIF (iterator < 66) THEN
		INSERT INTO wholesale.chipboard (sizeId, cost)
        VALUE (2, FLOOR(RAND()*2000 + 1000));
	ELSE 
		INSERT INTO wholesale.chipboard (sizeId, cost)
        VALUE (3, FLOOR(RAND()*2000 + 1000));
	END IF;
    SET iterator = iterator + 1;
END WHILE;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE fill_orderItem(IN IDOrder INT)
BEGIN
DECLARE bound INT UNSIGNED DEFAULT FLOOR(RAND()*5+3);
DECLARE iterator INT UNSIGNED DEFAULT 0;
	WHILE iterator < bound DO
		INSERT INTO wholesale.order_item (chipboardId, quantity, orderId)
        VALUE (FLOOR(RAND()*100)+1, FLOOR(RAND()*5+3), IDOrder);
        SET iterator = iterator + 1;
    END WHILE;
END $$;
DELIMITER;

DELIMITER $$
CREATE PROCEDURE fill_orders_and_orderItem()
BEGIN
DECLARE bound INT UNSIGNED DEFAULT 20;
DECLARE iterator INT UNSIGNED DEFAULT 0;
DECLARE paymentStatus VARCHAR(20) DEFAULT 'DONE';
DECLARE orderStatus VARCHAR(20) DEFAULT 'DONE';
	WHILE iterator < bound DO 
		IF (iterator % 4 = 0) THEN
			SET paymentStatus = 'DONE';
            SET orderStatus = 'DONE';
		ELSEIF (iterator % 4 = 1) THEN
			SET paymentStatus = 'DONE';
            SET orderStatus = 'PENDING';
        ELSE
			SET paymentStatus = 'PENDING';
            SET orderStatus = 'SUSPENDED';
        END IF;
        
	INSERT INTO wholesale.orders (customerId, paymentStatus, orderStatus)
	VALUE (FLOOR(RAND()*10)+1, paymentStatus, orderStatus);
    SET iterator = iterator + 1;
    CALL fill_orderItem(iterator);
END WHILE;
END $$
DELIMITER ;

INSERT INTO wholesale.chipboard_size (length, width, thicknes)
VALUES 	(2000, 3000, 20),
		(2000, 3000, 30),
        (2000, 3000, 40);
        
CALL fill_chipboard_table();

INSERT INTO wholesale.customers (NIP, name, discount)
VALUES 	(222222221, 'TOMPOL', 0.1),
		(222222231, 'KAMPOL', 0),
        (222222211, 'PRZEMPOL', 0),
        (222222111, 'KUBAPOL', 0.3),
        (222221111, 'RAFPOL', 0.6),
        (222211111, 'BARTPOL', 0),
        (222111111, 'PAWPOL', 0.25),
        (221111111, 'MATPOL', 0),
        (211111111, 'PIOTRPOL', 0),
        (111111111, 'MICHPOL', 0.5);

CALL fill_orders_and_orderItem();

INSERT INTO income (operation_value, orderId, operation_date)
SELECT SUM(quantity*cost), id, NOW()
FROM (
	SELECT item.quantity, C.cost, O.id
	FROM orders O
	INNER JOIN order_item item
		ON O.id = item.orderId
	INNER JOIN chipboard C
		ON item.chipboardId = C.id
	WHERE O.paymentStatus = 'DONE') AS temp
GROUP BY id;

INSERT INTO warehouse (chipboardId, quantity)
SELECT id, FLOOR(RAND()*30 + 20) AS quantity
FROM chipboard;

