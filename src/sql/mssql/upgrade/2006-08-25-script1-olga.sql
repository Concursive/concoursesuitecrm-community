-- Table ORDER_ADDRESS

ALTER TABLE order_address
 ADD addrline4 varchar(300) ;

-- Index: contact_city_idx

CREATE INDEX order_city_idx
  ON order_address (city);

-- Table ORDER_ENTRY

ALTER TABLE ORDER_ENTRY 
  ADD approx_ship_date DATETIME;
  
ALTER TABLE ORDER_ENTRY   
  ADD approx_delivery_date DATETIME;

-- Table CUSTOMER_PRODUCT

ALTER TABLE customer_product
 ADD contact_id INTEGER ;

-- Table CUSTOMER_PRODUCT_HISTORY

ALTER TABLE customer_product_history
 ADD contact_id INTEGER ;
 
-- Table CREDIT_CARD

CREATE TABLE credit_card (
  creditcard_id INT IDENTITY PRIMARY KEY,
  card_type INT REFERENCES lookup_creditcard_types (code),
  card_number varchar(300),
  card_security_code varchar(300),
  expiration_month int,
  expiration_year int,
  name_on_card varchar(300),
  company_name_on_card varchar(300),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);


CREATE TABLE lookup_payment_gateway (
  code INT IDENTITY PRIMARY KEY,
  description varchar(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level int DEFAULT 0,
  enabled BIT DEFAULT 1,
  constant_id int
);

        
CREATE TABLE merchant_payment_gateway (
  merchant_payment_gateway_id INT IDENTITY PRIMARY KEY,
  gateway_id int REFERENCES lookup_payment_gateway (code),
  merchant_id varchar(300),
  merchant_code varchar(1024),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);
