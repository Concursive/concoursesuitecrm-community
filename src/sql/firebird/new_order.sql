-- PostgreSQL Table Creation
-- @created    March 18, 2004
-- @version    $Id$
-- This schema represents an Order Entry System.
-- REQUIRES: new_product.sql
-- REQUIRES: new_project.sql
-- REQUIRES: new_quote.sql

-- Each order can have a status, which changes as the order is completed
-- Example: Pending, In Progress, Cancelled, Rejected, Completed
CREATE GENERATOR lookup_order_status_code_seq;
CREATE TABLE lookup_order_status (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- Each order has a type which defines the flow of product to/from customer
-- Example: New, Change, Upgrade/Downgrade, Disconnect, Move, Refund, Suspend, Unsuspend, Re-Run
CREATE GENERATOR lookup_order_type_code_seq;
CREATE TABLE lookup_order_type (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- Each order has terms in which the order was placed
CREATE GENERATOR lookup_order_terms_code_seq;
CREATE TABLE lookup_order_terms (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- Each order has a type of origination
-- Example: Online, Email, Incoming Phone Call, Outgoing Phone Call, Mail
CREATE GENERATOR lookup_order_source_code_seq;
CREATE TABLE lookup_order_source (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- The details of an order are listed
CREATE GENERATOR order_entry_order_id_seq;
CREATE TABLE order_entry (
  order_id INTEGER  NOT NULL,
  parent_id INTEGER,
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  quote_id INTEGER REFERENCES quote_entry(quote_id),
  sales_id INTEGER REFERENCES "access"(user_id),
  orderedby INTEGER REFERENCES contact(contact_id),
  billing_contact_id INTEGER REFERENCES contact(contact_id),
  source_id INTEGER REFERENCES lookup_order_source(code),
  grand_total FLOAT,
  status_id INTEGER REFERENCES lookup_order_status(code),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  contract_date TIMESTAMP ,
  expiration_date TIMESTAMP ,
  order_terms_id INTEGER REFERENCES lookup_order_terms(code),
  order_type_id INTEGER REFERENCES lookup_order_type(code),
  description VARCHAR(2048),
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100 ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  submitted TIMESTAMP,
  approx_ship_date TIMESTAMP DEFAULT NULL,
  approx_delivery_date TIMESTAMP DEFAULT NULL,  
  PRIMARY KEY (ORDER_ID)
);

-- REQUIRED HERE - Firebird can not create a FK on itself during table create
ALTER TABLE ORDER_ENTRY ADD CONSTRAINT FK_ORDER_ENTRY_ORDER_ID
  FOREIGN KEY (PARENT_ID) REFERENCES ORDER_ENTRY
  (ORDER_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Each order can contain multiple products
CREATE GENERATOR order_product_item_id_seq;
CREATE TABLE order_product (
  item_id INTEGER  NOT NULL,
  order_id INTEGER NOT NULL REFERENCES order_entry(order_id),
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  quantity INTEGER DEFAULT 0 NOT NULL,
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT DEFAULT 0 NOT NULL ,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 NOT NULL ,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT DEFAULT 0 NOT NULL,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  extended_price FLOAT DEFAULT 0 NOT NULL ,
  total_price FLOAT DEFAULT 0 NOT NULL ,
  status_id INTEGER REFERENCES lookup_order_status(code),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ITEM_ID)
);

-- Each order_product has a status, as the status changes,
-- the previous status is stored here for reference and tracking

-- Old Name: order_product_status_order_product_status_id_seq;
CREATE GENERATOR order_product_uct_status_id_seq;
CREATE TABLE order_product_status (
  order_product_status_id INTEGER  NOT NULL,
  order_id INTEGER NOT NULL REFERENCES order_entry(order_id),
  item_id INTEGER NOT NULL REFERENCES order_product(item_id),
  status_id INTEGER REFERENCES lookup_order_status(code),
   entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ORDER_PRODUCT_STATUS_ID)
);

-- Each product can have configurable options
-- Old Name: order_product_options_order_product_option_id_seq;
CREATE GENERATOR order_product_uct_option_id_seq;
CREATE TABLE order_product_options (
  order_product_option_id INTEGER  NOT NULL,
  item_id INTEGER NOT NULL REFERENCES order_product(item_id),
  product_option_id INTEGER NOT NULL REFERENCES product_option_map(product_option_id),
  quantity INTEGER DEFAULT 0 NOT NULL ,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 NOT NULL ,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT DEFAULT 0 NOT NULL ,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  extended_price FLOAT DEFAULT 0 NOT NULL ,
  total_price FLOAT DEFAULT 0 NOT NULL,
  status_id INTEGER REFERENCES lookup_order_status(code),
  PRIMARY KEY (ORDER_PRODUCT_OPTION_ID)
);

CREATE TABLE order_product_option_boolean (
  order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
  "value" CHAR(1) NOT NULL
);

CREATE TABLE order_product_option_float (
  order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
  "value" FLOAT NOT NULL
);

CREATE TABLE order_product_option_timestamp (
  order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
  "value" TIMESTAMP NOT NULL
);

CREATE TABLE order_product_option_integer (
   order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
   "value" INTEGER NOT NULL
);

CREATE TABLE order_product_option_text (
   order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
   "value" BLOB SUB_TYPE 1 SEGMENT SIZE 100 NOT NULL
);

-- Example: Billing, Shipping
-- Old Name: lookup_orderaddress_types_code_seq;
CREATE GENERATOR lookup_ordera_ss_types_code_seq;
CREATE TABLE lookup_orderaddress_types (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

CREATE GENERATOR order_address_address_id_seq;
CREATE TABLE order_address (
  address_id INTEGER  NOT NULL,
  order_id INTEGER NOT NULL REFERENCES order_entry(order_id),
  address_type INTEGER REFERENCES lookup_orderaddress_types(code),
  addrline1 VARCHAR(300),
  addrline2 VARCHAR(300),
  addrline3 VARCHAR(300),
  city VARCHAR(300),
  state VARCHAR(300),
  country VARCHAR(300),
  postalcode VARCHAR(40),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  addrline4 VARCHAR(300),
  PRIMARY KEY (ADDRESS_ID)
);

-- The method in which a payment is made
-- Example: Credit Card, EFT, Cash, Check, Money Order
CREATE GENERATOR lookup_payment_methods_code_seq;
CREATE TABLE lookup_payment_methods (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- Example: Visa, Master Card, Discover, American Express
CREATE GENERATOR lookup_creditcard_type_code_seq;
CREATE TABLE lookup_creditcard_types (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- Old Name: payment_creditcard_creditcard_id_seq;
CREATE GENERATOR payment_credi_creditcard_id_seq;
CREATE TABLE payment_creditcard (
  creditcard_id INTEGER  NOT NULL,
  card_type INTEGER REFERENCES lookup_creditcard_types(code),
  card_number VARCHAR(300),
  card_security_code VARCHAR(300),
  expiration_month INTEGER,
  expiration_year INTEGER,
  name_on_card VARCHAR(300),
  company_name_on_card VARCHAR(300),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  order_id INTEGER REFERENCES order_entry(order_id),
  PRIMARY KEY (CREDITCARD_ID)
);

CREATE GENERATOR payment_eft_bank_id_seq;
CREATE TABLE payment_eft (
  bank_id INTEGER  NOT NULL,
  bank_name VARCHAR(300),
  routing_number VARCHAR(300),
  account_number VARCHAR(300),
  name_on_account VARCHAR(300),
  company_name_on_account VARCHAR(300),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  order_id INTEGER REFERENCES order_entry(order_id),
  PRIMARY KEY (BANK_ID)
);

-- List of products created by the ordering of a product
-- Old Name: customer_product_customer_product_id_seq;
CREATE GENERATOR customer_prod_er_product_id_seq;
CREATE TABLE customer_product (
  customer_product_id INTEGER  NOT NULL,
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  order_id INTEGER REFERENCES order_entry(order_id),
  order_item_id INTEGER REFERENCES order_product(item_id),
  description VARCHAR(2048),
  status_id INTEGER REFERENCES lookup_order_status(code),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 'Y',
  contact_id INTEGER,  
  PRIMARY KEY (CUSTOMER_PRODUCT_ID)
);

-- Some products get returned or are finished being used
-- Old Name: customer_product_history_history_id_seq;
CREATE GENERATOR customer_prod_ry_history_id_seq;
CREATE TABLE customer_product_history (
  history_id INTEGER  NOT NULL,
  customer_product_id INTEGER NOT NULL REFERENCES customer_product(customer_product_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  order_id INTEGER NOT NULL REFERENCES order_entry(order_id),
  product_start_date TIMESTAMP,
  product_end_date TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  order_item_id INTEGER NOT NULL REFERENCES order_product(item_id),
  contact_id INTEGER,  
  PRIMARY KEY (HISTORY_ID)
);

-- Each order_payment has an associated status
-- Example: Pending, In Progress, Approved, Declined
CREATE GENERATOR lookup_payment_status_code_seq;
CREATE TABLE lookup_payment_status (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

CREATE GENERATOR order_payment_payment_id_seq;
CREATE TABLE order_payment (
  payment_id INTEGER  NOT NULL,
  order_id INTEGER NOT NULL REFERENCES order_entry(order_id),
  payment_method_id INTEGER NOT NULL REFERENCES lookup_payment_methods(code),
  payment_amount FLOAT,
  authorization_ref_number VARCHAR(30),
  authorization_code VARCHAR(30),
  authorization_date TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  order_item_id INT REFERENCES order_product(item_id),
  history_id INT REFERENCES customer_product_history(history_id),
  date_to_process TIMESTAMP,
  creditcard_id INTEGER REFERENCES payment_creditcard(creditcard_id),
  bank_id INTEGER REFERENCES payment_eft(bank_id),
  status_id INTEGER REFERENCES lookup_payment_status(code),
  PRIMARY KEY (PAYMENT_ID)
);

-- Each order_payment has a status, as the status changes,
-- the previous status is stored here for reference and tracking
-- Old Name: order_payment_status_payment_status_id_seq;
CREATE GENERATOR order_payment_ent_status_id_seq;
CREATE TABLE order_payment_status (
  payment_status_id INTEGER NOT NULL,
  payment_id INTEGER NOT NULL REFERENCES order_payment(payment_id),
  status_id INTEGER REFERENCES lookup_payment_status(code),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (PAYMENT_STATUS_ID)
);

CREATE GENERATOR credit_card_creditcard_id_seq;
CREATE TABLE credit_card (
  creditcard_id INTEGER NOT NULL PRIMARY KEY,
  card_type INT REFERENCES lookup_creditcard_types (code),
  card_number varchar(300),
  card_security_code varchar(300),
  expiration_month int,
  expiration_year int,
  name_on_card varchar(300),
  company_name_on_card varchar(300),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL REFERENCES "access"(user_id)
);

CREATE GENERATOR lookup_payment_gateway_seq;
CREATE TABLE lookup_payment_gateway (
  code INTEGER NOT NULL PRIMARY KEY,
  description varchar(50) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" int DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  constant_id int
);

CREATE GENERATOR merchant_paym_nt_gateway_id_seq;
CREATE TABLE merchant_payment_gateway (
  merchant_payment_gateway_id INTEGER NOT NULL PRIMARY KEY,
  gateway_id int REFERENCES lookup_payment_gateway(code),
  merchant_id varchar(300),
  merchant_code varchar(1024),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL REFERENCES "access"(user_id)
);
