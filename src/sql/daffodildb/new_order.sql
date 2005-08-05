-- Each order can have a status, which changes as the order is completed

-- Example: Pending, In Progress, Cancelled, Rejected, Completed
CREATE SEQUENCE lookup_order_status_code_seq;
CREATE TABLE lookup_order_status (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);

-- Each order has a type which defines the flow of product to/from customer
-- Example: New, Change, Upgrade/Downgrade, Disconnect, Move, Refund, Suspend, Unsuspend, Re-Run
CREATE SEQUENCE lookup_order_type_code_seq;
CREATE TABLE lookup_order_type (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);

-- Each order has terms in which the order was placed
CREATE SEQUENCE lookup_order_terms_code_seq;
CREATE TABLE lookup_order_terms (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
	"level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);

-- Each order has a type of origination
-- Example: Online, Email, Incoming Phone Call, Outgoing Phone Call, Mail
CREATE SEQUENCE lookup_order_source_code_seq;
CREATE TABLE lookup_order_source (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
	"level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);

-- The details of an order are listed
CREATE SEQUENCE order_entry_order_id_seq;
CREATE TABLE order_entry (
  order_id INT  PRIMARY KEY,
  parent_id INT REFERENCES order_entry(order_id),
  org_id INTEGER REFERENCES organization(org_id) NOT NULL,
  quote_id INTEGER REFERENCES quote_entry(quote_id),
  sales_id INTEGER REFERENCES access(user_id),
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
  notes CLOB ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL,
  submitted TIMESTAMP
);

-- Each order can contain multiple products
CREATE SEQUENCE order_product_item_id_seq;
CREATE TABLE order_product (
  item_id INT  PRIMARY KEY,
  order_id INTEGER REFERENCES order_entry(order_id) NOT NULL,
  product_id INTEGER REFERENCES product_catalog(product_id) NOT NULL,
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
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Each order_product has a status, as the status changes,
-- the previous status is stored here for reference and tracking
CREATE SEQUENCE order_product_status_order_product_status_id_seq;
CREATE TABLE order_product_status (
  order_product_status_id INT  PRIMARY KEY,
  order_id INTEGER REFERENCES order_entry(order_id) NOT NULL,
  item_id INTEGER REFERENCES order_product(item_id) NOT NULL,
  status_id INTEGER REFERENCES lookup_order_status(code),
   entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL 
);

-- Each product can have configurable options
CREATE SEQUENCE order_product_options_order_product_option_id_seq;
CREATE TABLE order_product_options (
  order_product_option_id INT  PRIMARY KEY,
  item_id INTEGER REFERENCES order_product(item_id) NOT NULL,
  product_option_id INTEGER REFERENCES product_option_map(product_option_id) NOT NULL ,
  quantity INTEGER DEFAULT 0 NOT NULL ,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 NOT NULL ,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT DEFAULT 0 NOT NULL , 
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  extended_price FLOAT DEFAULT 0 NOT NULL ,
  total_price FLOAT DEFAULT 0 NOT NULL,
  status_id INTEGER REFERENCES lookup_order_status(code)
);

CREATE TABLE order_product_option_boolean (
	order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
	value boolean NOT NULL
);

CREATE TABLE order_product_option_float (
	order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
	value FLOAT NOT NULL
);

CREATE TABLE order_product_option_timestamp (
	order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
	value TIMESTAMP NOT NULL
);

CREATE TABLE order_product_option_integer (
	order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
	value INTEGER NOT NULL
);

CREATE TABLE order_product_option_text (
	order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
	value CLOB NOT NULL
);

-- Example: Billing, Shipping
CREATE SEQUENCE lookup_orderaddress_types_code_seq;
CREATE TABLE lookup_orderaddress_types (
	code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);

CREATE SEQUENCE order_address_address_id_seq;
CREATE TABLE order_address (
	address_id INT  PRIMARY KEY,
	order_id INT REFERENCES order_entry(order_id) NOT NULL,
	address_type INT REFERENCES lookup_orderaddress_types(code),
	addrline1 VARCHAR(300),
	addrline2 VARCHAR(300),
	addrline3 VARCHAR(300),
	city VARCHAR(300),
	state VARCHAR(300),
	country VARCHAR(300),
	postalcode VARCHAR(40),
	entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	enteredby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL 
);

-- The method in which a payment is made
-- Example: Credit Card, EFT, Cash, Check, Money Order
CREATE SEQUENCE lookup_payment_methods_code_seq;
CREATE TABLE lookup_payment_methods (
	code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);

-- Example: Visa, Master Card, Discover, American Express
CREATE SEQUENCE lookup_creditcard_type_code_seq;
CREATE TABLE lookup_creditcard_types (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);

CREATE SEQUENCE payment_creditcard_creditcard_id_seq;
CREATE TABLE payment_creditcard (
	creditcard_id INT  PRIMARY KEY,
	card_type INT REFERENCES lookup_creditcard_types(code),
	card_number VARCHAR(300),
	card_security_code VARCHAR(300),
	expiration_month INT,
	expiration_year INT,
  name_on_card VARCHAR(300),
  company_name_on_card VARCHAR(300),
	entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	enteredby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL ,
  order_id INTEGER REFERENCES order_entry(order_id)
);

CREATE SEQUENCE payment_eft_bank_id_seq;
CREATE TABLE payment_eft (
	bank_id INT  PRIMARY KEY,
	bank_name VARCHAR(300),
	routing_number VARCHAR(300),
	account_number VARCHAR(300),
	name_on_account VARCHAR(300),
  company_name_on_account VARCHAR(300),
	entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	enteredby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL ,
  order_id INTEGER REFERENCES order_entry(order_id)
);

-- List of products created by the ordering of a product
CREATE SEQUENCE customer_product_customer_product_id_seq;
CREATE TABLE customer_product (
  customer_product_id INT  PRIMARY KEY,
  org_id INTEGER REFERENCES organization(org_id) NOT NULL ,
  order_id INTEGER REFERENCES order_entry(order_id),
  order_item_id INTEGER REFERENCES order_product(item_id),
  description VARCHAR(2048),
  status_id INTEGER REFERENCES lookup_order_status(code),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,  
  modifiedby INT REFERENCES access(user_id) NOT NULL ,
  enabled boolean DEFAULT true
);

-- Some products get returned or are finished being used
CREATE SEQUENCE customer_product_history_history_id_seq;
CREATE TABLE customer_product_history (
  history_id INT  PRIMARY KEY,
  customer_product_id INTEGER REFERENCES customer_product(customer_product_id) NOT NULL ,
  org_id INTEGER REFERENCES organization(org_id) NOT NULL ,
  order_id INTEGER REFERENCES order_entry(order_id) NOT NULL ,
  product_start_date TIMESTAMP,
  product_end_date TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL , 
  modifiedby INT REFERENCES access(user_id) NOT NULL ,
  order_item_id INTEGER REFERENCES order_product(item_id) NOT NULL 
);

-- Each order_payment has an associated status
-- Example: Pending, In Progress, Approved, Declined
CREATE SEQUENCE lookup_payment_status_code_seq;
CREATE TABLE lookup_payment_status (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
	enabled boolean DEFAULT true
);

CREATE SEQUENCE order_payment_payment_id_seq;
CREATE TABLE order_payment (
	payment_id INT  PRIMARY KEY,
	order_id INT REFERENCES order_entry(order_id) NOT NULL,
  order_item_id INT REFERENCES order_product(item_id),
  history_id INT REFERENCES customer_product_history(history_id),
	payment_method_id INT REFERENCES lookup_payment_methods(code) NOT NULL ,
	payment_amount FLOAT,
  authorization_ref_number VARCHAR(30),
  authorization_code VARCHAR(30),
  authorization_date TIMESTAMP,
	entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
	enteredby INT REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT REFERENCES access(user_id) NOT NULL ,
  date_to_process TIMESTAMP,
  creditcard_id INTEGER REFERENCES payment_creditcard(creditcard_id),
  bank_id INTEGER REFERENCES payment_eft(bank_id),
  status_id INTEGER REFERENCES lookup_payment_status(code)
);

-- Each order_payment has a status, as the status changes,
-- the previous status is stored here for reference and tracking
CREATE SEQUENCE order_payment_status_payment_status_id_seq;
CREATE TABLE order_payment_status (
  payment_status_id INT  PRIMARY KEY,
  payment_id INTEGER REFERENCES order_payment(payment_id) NOT NULL ,
  status_id INTEGER REFERENCES lookup_payment_status(code),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT REFERENCES access(user_id) NOT NULL 
);

