DROP TABLE menu CASCADE CONSTRAINT;
CREATE TABLE menu
    (menu_id CHAR(8) NOT NULL,
     start_date DATE NOT NULL,
     end_date DATE NOT NULL,
     PRIMARY KEY (menu_id)
     );

DROP TABLE menu_item CASCADE CONSTRAINT;
CREATE TABLE menu_item
    (item_id CHAR(8) NOT NULL,
     name VARCHAR(50) NOT NULL,
     unit_price NUMERIC(8,2) NOT NULL,
	 item_quantity NUMBER,
     category VARCHAR(20) NOT NULL,
     Soldout CHAR(1) GENERATED ALWAYS AS (
        CASE
            WHEN item_quantity <= 0 then 'T'
            ELSE 'F'
        END
        ) VIRTUAL,
     PRIMARY KEY (item_id)
     );
     
DROP TABLE ingredient CASCADE CONSTRAINT;
CREATE TABLE ingredient
    (ingredient_id CHAR(8) NOT NULL,
     name VARCHAR(30) NOT NULL,
     unit_price NUMERIC(8,2) NOT NULL,
     quantity NUMBER NOT NULL,
     PRIMARY KEY(ingredient_id)
     );

DROP TABLE manager CASCADE CONSTRAINT;
CREATE TABLE manager
    (manager_id CHAR(8) NOT NULL,
     name VARCHAR(70) NOT NULL,
     password VARCHAR(20),
     phone_number CHAR(11),
     PRIMARY KEY(manager_id)
     );
     
DROP TABLE managed_item CASCADE CONSTRAINT;
Create Table managed_item
    (manager_id CHAR(8) NOT NULL,
     item_id CHAR(8) NOT NULL,
     datetime DATE,
	 PRIMARY KEY (item_id, manager_id),
     FOREIGN KEY (item_id) REFERENCES menu_item ON DELETE CASCADE,
     FOREIGN KEY (manager_id) REFERENCES manager ON DELETE CASCADE
     );
         
DROP TABLE chef CASCADE CONSTRAINT;
CREATE TABLE chef
    (chef_id CHAR(8) NOT NULL,
     name VARCHAR(70) NOT NULL,
     phone_number CHAR(11),
     PRIMARY KEY (chef_id)
     );
         

DROP TABLE customer CASCADE CONSTRAINT;
CREATE TABLE customer 
    (customer_id CHAR(8) NOT NULL,
     name VARCHAR(70) NOT NULL,
     password VARCHAR(20),
	 phone_number CHAR(11),
     PRIMARY KEY (customer_id)
     );
     
DROP TABLE PAYMENT CASCADE CONSTRAINT;
CREATE TABLE PAYMENT
    (order_id CHAR(8) NOT NULL,
	 customer_id CHAR(8) NOT NULL,
     total_price Numeric(12, 2),
     payment_type VARCHAR(13) NOT NULL,
     card_info VARCHAR(16),
     chef_id CHAR(8) NOT NULL,
     PRIMARY KEY (order_id, customer_id),
     FOREIGN KEY (customer_id) REFERENCES customer ON DELETE CASCADE,
     FOREIGN KEY (chef_id) REFERENCES chef ON DELETE CASCADE
     );

DROP TABLE part_of CASCADE CONSTRAINT;
CREATE TABLE part_of
    (order_id CHAR(8) NOT NULL,
     customer_id CHAR(8) NOT NULL,
     item_id CHAR(8) NOT NULL,
     amount numeric(8,2) NOT NULL,
	 primary key (order_id, customer_id, item_id),
     foreign key (order_id, customer_id) references PAYMENT on delete cascade,
     foreign key (item_id) references menu_item on delete cascade
     );
    
DROP TABLE need CASCADE CONSTRAINT;
CREATE TABLE need 
    (item_id CHAR(8) NOT NULL,
     ingredient_id CHAR(8) NOT NULL,
     PRIMARY KEY (item_id, ingredient_id),
     FOREIGN KEY (item_id) REFERENCES MENU_ITEM ON DELETE CASCADE,
     FOREIGN KEY (ingredient_id) REFERENCES INGREDIENT ON DELETE CASCADE
     );
    
DROP TABLE contains CASCADE CONSTRAINT;
CREATE TABLE contains 
    (menu_id CHAR(8) NOT NULL,
     item_id CHAR(8) NOT NULL,
     PRIMARY KEY (item_id, menu_id),
     FOREIGN KEY (menu_id) REFERENCES MENU ON DELETE CASCADE,
     FOREIGN KEY (item_id) REFERENCES MENU_ITEM ON DELETE CASCADE
     );
     
DROP TABLE edited_menu CASCADE CONSTRAINT;
CREATE TABLE edited_menu
    (manager_id CHAR(8) NOT NULL,
     menu_id CHAR(8) NOT NULL,
     PRIMARY KEY (manager_id, menu_id),
     FOREIGN KEY (manager_id) REFERENCES MANAGER ON DELETE CASCADE,
     FOREIGN KEY (menu_id) REFERENCES MENU ON DELETE CASCADE
     );
	 
DROP TABLE views CASCADE CONSTRAINT;
CREATE TABLE views
    (customer_id CHAR(8) NOT NULL,
     menu_id CHAR(8) NOT NULL,
     PRIMARY KEY (customer_id, menu_id),
     FOREIGN KEY (customer_id) REFERENCES CUSTOMER ON DELETE CASCADE,
     FOREIGN KEY (menu_id) REFERENCES MENU ON DELETE CASCADE
     );



CREATE OR REPLACE TRIGGER update_item_quantity
BEFORE INSERT OR UPDATE ON need
FOR EACH ROW
DECLARE
    v_item_id CHAR(8);
    v_ingredient_id CHAR(8);
    v_total_quantity NUMBER;
    v_new_quantity NUMBER;
    v_count NUMBER;
BEGIN
    -- need 테이블에 대한 변경 사항을 추적하고 해당하는 Menu_item의 item_quantity를 업데이트
    v_item_id := :NEW.item_id;
    v_ingredient_id := :NEW.ingredient_id;

    SELECT i.quantity INTO v_new_quantity
    FROM ingredient i
    WHERE i.ingredient_id = v_ingredient_id;
    
    SELECT COUNT(*) INTO v_count
    FROM need ne
    WHERE ne.item_id = v_item_id;
    
    if v_count = 0 THEN
        IF v_new_quantity <= 0 THEN
            -- quantity가 0 이하일 경우 item_quantity를 0으로 업데이트
            UPDATE menu_item
            SET item_quantity = 0
            WHERE item_id = v_item_id;
        ELSE
            UPDATE menu_item
            SET item_quantity = v_new_quantity
            WHERE item_id = v_item_id;
        END IF;
    ELSE
        SELECT MIN(i.quantity) INTO v_total_quantity
        FROM need ne, ingredient i
        WHERE ne.item_id = v_item_id
        AND ne.ingredient_id = i.ingredient_id;
        
        IF v_new_quantity < v_total_quantity THEN
            -- quantity가 기존보다 작으면 해당하는 재료의 개수로 item_quantity를 업데이트
            UPDATE menu_item
            SET item_quantity = v_new_quantity
            WHERE item_id = v_item_id;
        END IF;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER update_item_quantity2
BEFORE UPDATE ON ingredient
FOR EACH ROW
DECLARE
    v_ingredient_id CHAR(8);
    v_total_quantity NUMBER;
    v_new_quantity NUMBER;
    v_count NUMBER;
    CURSOR C1(c_ingredient_id ingredient.ingredient_id%TYPE)
    IS
    SELECT mi.item_id as item_id, mi.item_quantity as item_quantity
    FROM menu_item mi, need ne
    WHERE mi.item_id = ne.item_id
    AND ne.ingredient_id = c_ingredient_id;
BEGIN
    v_ingredient_id := :NEW.ingredient_id;
    v_new_quantity := :NEW.quantity;
    
    FOR REC IN C1(v_ingredient_id)
    LOOP
        IF v_new_quantity < REC.item_quantity THEN
            -- quantity가 기존보다 작으면 해당하는 재료의 개수로 item_quantity를 업데이트
            UPDATE menu_item
            SET item_quantity = v_new_quantity
            WHERE item_id = REC.item_id;
        END IF;
    END LOOP;
END;
/


-- 트리거 생성
CREATE OR REPLACE TRIGGER calculate_total_price
BEFORE INSERT OR UPDATE ON part_of
FOR EACH ROW
DECLARE
    v_order_id CHAR(8);
    v_customer_id CHAR(8);
    v_item_id CHAR(8);
    v_amount NUMBER(8,2);
    v_total_price NUMERIC(8,2);
    v_prev_total_price NUMERIC(8, 2);
BEGIN
    v_order_id := :NEW.order_id;
    v_customer_id := :NEW.customer_id;
    v_item_id := :NEW.item_id;
    v_amount := :NEW.amount;
    
    SELECT (mi.unit_price * v_amount)
    INTO v_total_price
    FROM menu_item mi
    WHERE mi.item_id = v_item_id;
    
    SELECT SUM(p.amount * mi.unit_price)
    INTO v_prev_total_price
    FROM part_of p
    JOIN menu_item mi ON p.item_id = mi.item_id
    WHERE p.order_id = v_order_id AND p.customer_id = v_customer_id;
    
    IF v_total_price IS NULL THEN
        v_total_price := 0;
    END IF;
    
    IF v_prev_total_price IS NULL THEN
        v_prev_total_price := 0;
    END IF;
    
    -- total_price 업데이트
    UPDATE payment
    SET total_price = (v_total_price + v_prev_total_price)
    WHERE order_id = v_order_id AND customer_id = v_customer_id;
END;
/

commit;