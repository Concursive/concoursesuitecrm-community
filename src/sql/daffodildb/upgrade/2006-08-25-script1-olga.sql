-- Table ORDER_ADDRESS
ALTER TABLE order_address
 ADD COLUMN addrline4 varchar(300) ;
-- Index: contact_city_idx

CREATE INDEX order_city_idx
  ON order_address(city);

-- Table ORDER_ENTRY

ALTER TABLE ORDER_ENTRY 
  ADD COLUMN approx_ship_date timestamp(3);
  
ALTER TABLE ORDER_ENTRY   
  ADD COLUMN approx_delivery_date timestamp(3);

-- Table CUSTOMER_PRODUCT

ALTER TABLE customer_product
 ADD COLUMN contact_id INTEGER ;

-- Table CUSTOMER_PRODUCT_HISTORY

ALTER TABLE customer_product_history
 ADD COLUMN contact_id INTEGER ;
 
-- Table CREDIT_CARD
CREATE SEQUENCE credit_card_creditcard_id_seq;
CREATE TABLE credit_card (
 creditcard_id INT PRIMARY KEY,
 card_type INTEGER REFERENCES lookup_creditcard_types (code),
 card_number varchar(300),
 card_security_code varchar(300),
 expiration_month INTEGER,
 expiration_year INTEGER,
 name_on_card varchar(300),
 company_name_on_card varchar(300),
 entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
 enteredby INT NOT NULL REFERENCES "access"(user_id),
 modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
 modifiedby INT NOT NULL REFERENCES "access"(user_id)
);

CREATE SEQUENCE lookup_payment_gateway_code_seq;
CREATE TABLE lookup_payment_gateway (
  code INT PRIMARY KEY,
  description varchar(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  "level" int DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  constant_id int
);

CREATE SEQUENCE merchant_payment_gateway_merchant_payment_gateway_id_seq;        
CREATE TABLE merchant_payment_gateway (
  merchant_payment_gateway_id INT PRIMARY KEY,
  gateway_id int REFERENCES lookup_payment_gateway(code),
  merchant_id varchar(300),
  merchant_code varchar(1024),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL REFERENCES "access"(user_id)
);
