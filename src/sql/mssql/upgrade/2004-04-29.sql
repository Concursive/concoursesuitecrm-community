-- PostgreSQL Table Creation
-- @created    March 18, 2004
-- @version    $Id$
-- This schema represents a product catalog.  A product catalog is a
-- classification and definition of products that a company can sell.
-- REQUIRES: new_cdb.sql
-- REQUIRES: new_project.sql

-- Currency table, could extend to exchange rates
-- Example: USD
CREATE TABLE lookup_currency (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

-- Each category can be associated with a type
-- Example: Publication, Target Audience

CREATE TABLE lookup_product_category_type (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

-- A category groups one or more products
-- Example: Name of a publication, Name of a target audience
CREATE TABLE product_category (
  category_id INT IDENTITY PRIMARY KEY,
  parent_id INTEGER REFERENCES product_category(category_id),
  category_name VARCHAR(255) NOT NULL,
  abbreviation VARCHAR(30),
  short_description TEXT,
  long_description TEXT,
  type_id INTEGER REFERENCES lookup_product_category_type(code),
  -- images
  thumbnail_image_id INTEGER REFERENCES project_files(item_id),
  small_image_id INTEGER REFERENCES project_files(item_id),
  large_image_id INTEGER REFERENCES project_files(item_id),
  -- record status
  list_order INTEGER DEFAULT 10,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  start_date DATETIME DEFAULT NULL,
  expiration_date DATETIME DEFAULT NULL,
  enabled BIT NOT NULL DEFAULT 1
);

-- Each category can be associated with multiple categories
CREATE TABLE product_category_map (
  id INT IDENTITY PRIMARY KEY,
  category1_id INTEGER NOT NULL REFERENCES product_category(category_id),
  category2_id INTEGER NOT NULL REFERENCES product_category(category_id)
);

-- Each product can be associated with a type
-- Example: Ad, Design Ad, Subscription
CREATE TABLE lookup_product_type (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

-- Each product can have a format
-- Example: Physical, Digital
CREATE TABLE lookup_product_format (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

-- Each product can have a shipping code for shipping company
CREATE TABLE lookup_product_shipping (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

-- Each product can have an estimated shipping time
-- Example: Ships within 24 hours
CREATE TABLE lookup_product_ship_time (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

-- Each product can have a tax code
CREATE TABLE lookup_product_tax (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

-- Each price is either fixed or recurring, if recurring then when?
-- Example: Monthly, Weekly, Yearly, etc.
CREATE TABLE lookup_recurring_type (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

-- The details of a product are listed
-- If this product is to replace another product, then the parent_id is set
-- Example: 1/16 Page Ad
CREATE TABLE product_catalog (
  product_id INT IDENTITY PRIMARY KEY,
  parent_id INTEGER REFERENCES product_catalog(product_id),
  product_name VARCHAR(255) NOT NULL,
  abbreviation VARCHAR(30),
  short_description TEXT,
  long_description TEXT,
  type_id INTEGER REFERENCES lookup_product_type(code),
  special_notes VARCHAR(255),
  -- ecommerce
  sku VARCHAR(40),
  in_stock BIT NOT NULL DEFAULT 1,
  format_id INTEGER REFERENCES lookup_product_format(code),
  shipping_id INTEGER REFERENCES lookup_product_shipping(code),
  estimated_ship_time INTEGER REFERENCES lookup_product_ship_time(code),
  -- images
  thumbnail_image_id INTEGER REFERENCES project_files(item_id),
  small_image_id INTEGER REFERENCES project_files(item_id),
  large_image_id INTEGER REFERENCES project_files(item_id),
  -- record status
  list_order INTEGER DEFAULT 10,
  enteredby INT NOT NULL references access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  start_date DATETIME DEFAULT NULL,
  expiration_date DATETIME DEFAULT NULL,
  enabled BIT NOT NULL DEFAULT 1
);

-- Each product can have a price, which can change over time
CREATE TABLE product_catalog_pricing (
  price_id INT IDENTITY PRIMARY KEY,
  product_id INTEGER REFERENCES product_catalog(product_id),
  -- pricing
  tax_id INTEGER REFERENCES lookup_product_tax(code),
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT NOT NULL DEFAULT 0,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT NOT NULL DEFAULT 0,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT NOT NULL DEFAULT 0,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  enteredby INT NOT NULL references access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  start_date DATETIME DEFAULT NULL,
  expiration_date DATETIME DEFAULT NULL
);

-- A package can consist of products with discounted prices
CREATE TABLE package (
  package_id INT IDENTITY PRIMARY KEY,
  category_id INT REFERENCES product_category(category_id),
  package_name VARCHAR(255) NOT NULL,
  abbreviation VARCHAR(30),
  short_description TEXT,
  long_description TEXT,
  --images
  thumbnail_image_id INTEGER REFERENCES project_files(item_id),
  small_image_id INTEGER REFERENCES project_files(item_id),
  large_image_id INTEGER REFERENCES project_files(item_id),
  -- record status
  list_order INTEGER DEFAULT 10,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  start_date DATETIME DEFAULT NULL,
  expiration_date DATETIME DEFAULT NULL,
  enabled BIT NOT NULL DEFAULT 1
);

-- Each package consists of one or more products
CREATE TABLE package_products_map (
  id INT IDENTITY PRIMARY KEY,
  package_id INT NOT NULL REFERENCES package(package_id),
  product_id INT NOT NULL REFERENCES product_catalog(product_id),
  description TEXT,
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT NOT NULL DEFAULT 0,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT NOT NULL DEFAULT 0,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  start_date DATETIME DEFAULT NULL,
  expiration_date DATETIME DEFAULT NULL
);

-- Each product can be associated with multiple categories
CREATE TABLE product_catalog_category_map (
  id INT IDENTITY PRIMARY KEY,
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  category_id INTEGER NOT NULL REFERENCES product_category(category_id)
);

-- Each configuration of an option has a result type that maps to the application
-- Example: Integer, Double, Text, Timestamp
CREATE TABLE lookup_product_conf_result (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

-- A configurator allows options to be displayed, validated
-- Example: A color configurator with 4 Options: None, B&W, Spot Color, Process Color
-- PROBLEM: How is the configured item price indicated?
CREATE TABLE product_option_configurator (
  configurator_id INT IDENTITY PRIMARY KEY,
  short_description TEXT,
  long_description TEXT,
  class_name VARCHAR(255),
  result_type INTEGER NOT NULL REFERENCES lookup_product_conf_result(code)
);

-- An option library allows products to be further customized by 
-- specifying what the option is and if/how it can be configured.
-- If this option is to replace another, then the parent_id is set
CREATE TABLE product_option (
  option_id INT IDENTITY PRIMARY KEY,
  configurator_id INTEGER NOT NULL REFERENCES product_option_configurator(configurator_id),
  parent_id INTEGER REFERENCES product_option(option_id),
  short_description TEXT,
  long_description TEXT,
  allow_customer_configure BIT NOT NULL DEFAULT 0,
  allow_user_configure BIT NOT NULL DEFAULT 0,
  required BIT NOT NULL DEFAULT 0,
  -- record information
  start_date DATETIME DEFAULT NULL,
  end_date DATETIME DEFAULT NULL,
  enabled BIT DEFAULT 1
);

-- Each option will have it's configurator defined when the option is created
CREATE TABLE product_option_values (
  value_id INT IDENTITY PRIMARY KEY,
  option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  result_id INTEGER NOT NULL,
  description TEXT,
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT NOT NULL DEFAULT 0,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT NOT NULL DEFAULT 0,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT NOT NULL DEFAULT 0,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code)
);
CREATE UNIQUE INDEX idx_pr_opt_val ON product_option_values (option_id, result_id);

-- Each product can be associated with multiple configurable options
CREATE TABLE product_option_map (
  product_option_id INT IDENTITY PRIMARY KEY,
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  value_id INTEGER NOT NULL REFERENCES product_option_values(value_id)
);

-- For each option, there are structure fields specified by the configurator
-- which determine the option's structure
CREATE TABLE product_option_BIT (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  value BIT NOT NULL
);

CREATE TABLE product_option_float (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  value FLOAT NOT NULL
);

CREATE TABLE product_option_timestamp (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  value TIMESTAMP NOT NULL
);

CREATE TABLE product_option_integer (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  value INTEGER NOT NULL
);

CREATE TABLE product_option_text (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  value TEXT NOT NULL
);

-- Each product can be associated with multiple keywords
-- Example: printer, printing, laser
CREATE TABLE lookup_product_keyword (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

CREATE TABLE product_keyword_map (
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  keyword_id INTEGER NOT NULL REFERENCES lookup_product_keyword(code)
);
CREATE UNIQUE INDEX idx_pr_key_map ON product_keyword_map (product_id, keyword_id);

-- Each product_category can have "custom folders"
-- TABLES ALREADY EXIST, MUST CREATE A CONSTANT ID



-- This schema represents an Quote Entry System.
-- REQUIRES: new_product.sql
-- REQUIRES: new_project.sql

-- Each quote can have a status, which changes as the quote is completed
-- Example: Pending, In Progress, Cancelled, Rejected, Completed
CREATE TABLE lookup_quote_status (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- Each quote has a type
-- Example: New, Change, Upgrade/Downgrade, Disconnect, Move, Refund, Suspend, Unsuspend
CREATE TABLE lookup_quote_type (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- Each quote has terms in which the quote was placed
CREATE TABLE lookup_quote_terms (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
	level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- Each quote has a type of origination
-- Example: Online, Email, Incoming Phone Call, Outgoing Phone Call, Mail
CREATE TABLE lookup_quote_source (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
	level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);


-- The details of a quote are listed
-- A quote can be requested by an organization, or a specific contact in an organization.
CREATE TABLE quote_entry (
  quote_id INT IDENTITY PRIMARY KEY,
  parent_id INT REFERENCES quote_entry(quote_id),
	org_id INTEGER NOT NULL REFERENCES organization(org_id),
  contact_id INT REFERENCES contact(contact_id),
  source_id INTEGER REFERENCES lookup_quote_source(code),
  grand_total FLOAT,
  -- quote status
	status_id INTEGER REFERENCES lookup_quote_status(code),
  status_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  expiration_date DATETIME DEFAULT NULL,
  -- quote terms and type
	quote_terms_id INTEGER REFERENCES lookup_quote_terms(code),
  quote_type_id INTEGER REFERENCES lookup_quote_type(code),
  issued DATETIME DEFAULT CURRENT_TIMESTAMP,
  short_description TEXT,
  notes TEXT NULL,
  ticketid INTEGER REFERENCES ticket(ticketid),
	-- record status
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
	modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modifiedby INT NOT NULL REFERENCES access(user_id)
);

-- Each quote can contain multiple products(line items)
CREATE TABLE quote_product (
  item_id INT IDENTITY PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  -- pricing and quantity of the base quote product
	quantity INTEGER NOT NULL DEFAULT 0,
	price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT NOT NULL DEFAULT 0,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT NOT NULL DEFAULT 0,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
	extended_price FLOAT NOT NULL DEFAULT 0,
  total_price FLOAT NOT NULL DEFAULT 0,
  estimated_delivery_date DATETIME,
  -- quote status
	status_id INTEGER REFERENCES lookup_quote_status(code),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Each quote_product can have configurable options
CREATE TABLE quote_product_options (
	quote_product_option_id INT IDENTITY PRIMARY KEY,
	item_id INTEGER NOT NULL REFERENCES quote_product(item_id),
	product_option_id INTEGER NOT NULL REFERENCES product_option_map(product_option_id),
	quantity INTEGER NOT NULL DEFAULT 0,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT NOT NULL DEFAULT 0,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT NOT NULL DEFAULT 0,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
	extended_price FLOAT NOT NULL DEFAULT 0,
  total_price FLOAT NOT NULL DEFAULT 0,
  status_id INTEGER REFERENCES lookup_quote_status(code)
);

CREATE TABLE quote_product_option_BIT (
	quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
	value BIT NOT NULL
);

CREATE TABLE quote_product_option_float (
	quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
	value FLOAT NOT NULL
);

CREATE TABLE quote_product_option_timestamp (
	quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
	value TIMESTAMP NOT NULL
);

CREATE TABLE quote_product_option_integer (
	quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
	value INTEGER NOT NULL
);

CREATE TABLE quote_product_option_text (
	quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
	value TEXT NOT NULL
);



-- MSSQL Table Creation
-- @created    May 4, 2004
-- @version    $Id$
-- This schema represents an Order Entry System.
-- REQUIRES: new_product.sql
-- REQUIRES: new_project.sql
-- REQUIRES: new_quote.sql

-- Each order can have a status, which changes as the order is completed
-- Example: Pending, In Progress, Cancelled, Rejected, Completed
CREATE TABLE lookup_order_status (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- Each order has a type which defines the flow of product to/from customer
-- Example: New, Change, Upgrade/Downgrade, Disconnect, Move, Refund, Suspend, Unsuspend, Re-Run
CREATE TABLE lookup_order_type (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- Each order has terms in which the order was placed
CREATE TABLE lookup_order_terms (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
	level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- Each order has a type of origination
-- Example: Online, Email, Incoming Phone Call, Outgoing Phone Call, Mail
CREATE TABLE lookup_order_source (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
	level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- The details of an order are listed
CREATE TABLE order_entry (
  order_id INT IDENTITY PRIMARY KEY,
  parent_id INT REFERENCES order_entry(order_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  quote_id INTEGER REFERENCES quote_entry(quote_id),
  sales_id INTEGER REFERENCES access(user_id),
  orderedby INTEGER REFERENCES contact(contact_id),
  billing_contact_id INTEGER REFERENCES contact(contact_id),
  source_id INTEGER REFERENCES lookup_order_source(code),
  grand_total FLOAT,
  -- order status
  status_id INTEGER REFERENCES lookup_order_status(code),
  status_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  contract_date DATETIME DEFAULT NULL,
  expiration_date DATETIME DEFAULT NULL,
  -- order terms and type
  order_terms_id INTEGER REFERENCES lookup_order_terms(code),
  order_type_id INTEGER REFERENCES lookup_order_type(code),
  description VARCHAR(2048),
  notes TEXT NULL,
  -- record status
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

-- Each order can contain multiple products
CREATE TABLE order_product (
  item_id INT IDENTITY PRIMARY KEY,
  order_id INTEGER NOT NULL REFERENCES order_entry(order_id),
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  -- pricing and quantity of base product
  quantity INTEGER NOT NULL DEFAULT 0,
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT NOT NULL DEFAULT 0,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT NOT NULL DEFAULT 0,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT NOT NULL DEFAULT 0,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  extended_price FLOAT NOT NULL DEFAULT 0,
  total_price FLOAT NOT NULL DEFAULT 0,
  -- order item status
  status_id INTEGER REFERENCES lookup_order_status(code),
  status_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Each order_product has a status, as the status changes,
-- the previous status is stored here for reference and tracking
CREATE TABLE order_product_status (
  order_product_status_id INT IDENTITY PRIMARY KEY,
  order_id INTEGER NOT NULL REFERENCES order_entry(order_id),
  item_id INTEGER NOT NULL REFERENCES order_product(item_id),
  status_id INTEGER REFERENCES lookup_order_status(code),
  -- record keeping
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

-- Each product can have configurable options
CREATE TABLE order_product_options (
  order_product_option_id INT IDENTITY PRIMARY KEY,
  item_id INTEGER NOT NULL REFERENCES order_product(item_id),
  product_option_id INTEGER NOT NULL REFERENCES product_option_map(product_option_id),
  quantity INTEGER NOT NULL DEFAULT 0,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT NOT NULL DEFAULT 0,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT NOT NULL DEFAULT 0,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  extended_price FLOAT NOT NULL DEFAULT 0,
  total_price FLOAT NOT NULL DEFAULT 0,
  status_id INTEGER REFERENCES lookup_order_status(code)
);

CREATE TABLE order_product_option_BIT (
	order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
	value BIT NOT NULL
);

CREATE TABLE order_product_option_float (
	order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
	value FLOAT NOT NULL
);

CREATE TABLE order_product_option_timestamp (
	order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
	value DATETIME NOT NULL
);

CREATE TABLE order_product_option_integer (
	order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
	value INTEGER NOT NULL
);

CREATE TABLE order_product_option_text (
	order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
	value TEXT NOT NULL
);

-- Example: Billing, Shipping
CREATE TABLE lookup_orderaddress_types (
	code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

CREATE TABLE order_address (
	address_id INT IDENTITY PRIMARY KEY,
	order_id INT NOT NULL REFERENCES order_entry(order_id),
	address_type INT REFERENCES lookup_orderaddress_types(code),
	addrline1 VARCHAR(300),
	addrline2 VARCHAR(300),
	addrline3 VARCHAR(300),
	city VARCHAR(300),
	state VARCHAR(300),
	country VARCHAR(300),
	postalcode VARCHAR(40),
	-- record keeping
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

-- The method in which a payment is made
-- Example: Credit Card, EFT, Cash, Check, Money Order
CREATE TABLE lookup_payment_methods (
	code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- Example: Visa, Master Card, Discover, American Express

CREATE TABLE lookup_creditcard_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

CREATE TABLE order_payment (
	payment_id INT IDENTITY PRIMARY KEY,
	order_id INT NOT NULL REFERENCES order_entry(order_id),
	payment_method_id INT NOT NULL REFERENCES lookup_payment_methods(code),
	payment_amount FLOAT,
  authorization_ref_number VARCHAR(30),
  authorization_code VARCHAR(30),
  authorization_date DATETIME,
	-- record keeping
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE payment_creditcard (
	creditcard_id INT IDENTITY PRIMARY KEY,
	payment_id INT NOT NULL REFERENCES order_payment(payment_id),
	-- credit card information
	card_type INT REFERENCES lookup_creditcard_types(code),
	card_number VARCHAR(300),
	card_security_code VARCHAR(10),
	expiration_month INT,
	expiration_year INT,
  name_on_card VARCHAR(300),
  company_name_on_card VARCHAR(300),
	-- record keeping
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE payment_eft (
	bank_id INT IDENTITY PRIMARY KEY,
	payment_id INT NOT NULL REFERENCES order_payment(payment_id),
	-- bank details
	bank_name VARCHAR(300),
	routing_number VARCHAR(300),
	account_number VARCHAR(300),
	name_on_account VARCHAR(300),
  company_name_on_account VARCHAR(300),
	-- record keeping
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

-- List of products created by the ordering of a product
CREATE TABLE customer_product (
  customer_product_id INT IDENTITY PRIMARY KEY,
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  order_id INTEGER REFERENCES order_entry(order_id),
  order_item_id INTEGER REFERENCES order_product(item_id),
  description VARCHAR(2048),
  status_id INTEGER REFERENCES lookup_order_status(code),
  status_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  modifiedby INT NOT NULL REFERENCES access(user_id),
  enabled BIT DEFAULT 1
);

-- Some products get returned or are finished being used
CREATE TABLE customer_product_history (
  history_id INT IDENTITY PRIMARY KEY,
  customer_product_id INTEGER NOT NULL REFERENCES customer_product(customer_product_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  order_id INTEGER REFERENCES order_entry(order_id),
  product_start_date DATETIME,
  product_end_date DATETIME,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  modifiedby INT NOT NULL REFERENCES access(user_id)
);
