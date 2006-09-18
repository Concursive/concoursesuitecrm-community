-- Table ORDER_ADDRESS

ALTER TABLE order_address
 ADD COLUMN addrline4 varchar(300) ;

-- Index: contact_city_idx

CREATE INDEX order_city_idx
  ON order_address
  USING btree
  (city);

-- Table ORDER_ENTRY

ALTER TABLE ORDER_ENTRY 
  ADD COLUMN approx_ship_date timestamp(3);
  
ALTER TABLE ORDER_ENTRY   
  ADD COLUMN approx_delivery_date timestamp(3);

-- Table CUSTOMER_PRODUCT

ALTER TABLE customer_product
 ADD COLUMN contact_id int4 ;

-- Table CUSTOMER_PRODUCT_HISTORY

ALTER TABLE customer_product_history
 ADD COLUMN contact_id int4 ;
 
-- Table CREDIT_CARD

CREATE SEQUENCE creditcard_creditcard_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE credit_card
(
  creditcard_id int4 NOT NULL DEFAULT nextval('creditcard_creditcard_id_seq'::regclass),
  card_type int4,
  card_number varchar(300),
  card_security_code varchar(300),
  expiration_month int4,
  expiration_year int4,
  name_on_card varchar(300),
  company_name_on_card varchar(300),
  entered timestamp(3) NOT NULL DEFAULT now(),
  enteredby int4 NOT NULL,
  modified timestamp(3) NOT NULL DEFAULT now(),
  modifiedby int4 NOT NULL,
  CONSTRAINT creditcard_pkey PRIMARY KEY (creditcard_id),
  CONSTRAINT creditcard_card_type_fkey FOREIGN KEY (card_type)
      REFERENCES lookup_creditcard_types (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT creditcard_enteredby_fkey FOREIGN KEY (enteredby)
      REFERENCES "access" (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT creditcard_modifiedby_fkey FOREIGN KEY (modifiedby)
      REFERENCES "access" (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITHOUT OIDS;

-- Table LOOKUP_PAYMENT_GATEWAY
CREATE SEQUENCE lookup_payment_gateway_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE lookup_payment_gateway
(
  code int4 NOT NULL DEFAULT nextval('lookup_payment_gateway_seq'::regclass),
  description varchar(50) NOT NULL,
  default_item bool DEFAULT false,
  "level" int4 DEFAULT 0,
  enabled bool DEFAULT true,
  constant_id int4,
  CONSTRAINT lookup_payment_gateway_pkey PRIMARY KEY (code)
) 
WITHOUT OIDS;

INSERT INTO lookup_payment_gateway
        ( description, level, enabled, constant_id) 
        VALUES ('Authorize.net',10, true, 8110621);
INSERT INTO lookup_payment_gateway
        ( description, level, enabled, constant_id) 
        VALUES ('PPIPaymover',10, true, 8110622);
        
-- Table MERCHANT_PAYMENT_GATEWAY
CREATE SEQUENCE merchant_payment_gateway_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE merchant_payment_gateway
(
  merchant_payment_gateway_id int4 NOT NULL DEFAULT nextval('merchant_payment_gateway_seq'::regclass),
  gateway_id int4,
  merchant_id varchar(300),
  merchant_code varchar(1024),
  entered timestamp(3) NOT NULL DEFAULT now(),
  enteredby int4 NOT NULL,
  modified timestamp(3) NOT NULL DEFAULT now(),
  modifiedby int4 NOT NULL,
  CONSTRAINT merchant_payment_gateway_id_pkey PRIMARY KEY (merchant_payment_gateway_id),
  CONSTRAINT merchant_payment_gateway_gateway_id_fkey FOREIGN KEY (gateway_id)
      REFERENCES lookup_payment_gateway (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITHOUT OIDS;