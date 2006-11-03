
CREATE SEQUENCE lookup_currency_code_seq;
CREATE TABLE lookup_currency (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

-- Each category can be associated with a type
-- Example: Publication, Target Audience

CREATE SEQUENCE lookup_product_categor_code_seq;
CREATE TABLE lookup_product_category_type (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

-- A category groups one or more products
-- Example: Name of a publication, Name of a target audience
CREATE SEQUENCE product_category_category_id_seq;
CREATE TABLE product_category (
  category_id INT  PRIMARY KEY,
  parent_id INTEGER REFERENCES product_category(category_id),
  category_name VARCHAR(255) NOT NULL,
  abbreviation VARCHAR(30),
  short_description CLOB,
  long_description CLOB,
  type_id INTEGER REFERENCES lookup_product_category_type(code),
  thumbnail_image_id INTEGER REFERENCES project_files(item_id),
  small_image_id INTEGER REFERENCES project_files(item_id),
  large_image_id INTEGER REFERENCES project_files(item_id),
  list_order INTEGER DEFAULT 10,
  enteredby INT REFERENCES access(user_id) NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT  REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  start_date TIMESTAMP ,
  expiration_date TIMESTAMP,
  enabled boolean  DEFAULT true NOT NULL,
  import_id INTEGER REFERENCES import(import_id),
  status_id INTEGER
);

-- Each category can be associated with multiple categories
CREATE SEQUENCE product_category_map_id_seq;
CREATE TABLE product_category_map (
  id INT PRIMARY KEY,
  category1_id INTEGER  REFERENCES product_category(category_id) NOT NULL,
  category2_id INTEGER REFERENCES product_category(category_id) NOT NULL
);

-- Each product can be associated with a type
-- Example: Ad, Design Ad, Subscription
CREATE SEQUENCE lookup_product_type_code_seq;
CREATE TABLE lookup_product_type (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

-- Each product can have a manufacturer
-- Example: Nokia, Compaq
CREATE SEQUENCE lookup_product_manufac_code_seq;
CREATE TABLE lookup_product_manufacturer (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

-- Each product can have a format
-- Example: Physical, Digital
CREATE SEQUENCE lookup_product_format_code_seq;
CREATE TABLE lookup_product_format (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

-- Each product can have a shipping code for shipping company
CREATE SEQUENCE lookup_product_shippin_code_seq;
CREATE TABLE lookup_product_shipping (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

-- Each product can have an estimated shipping time
-- Example: Ships within 24 hours
CREATE SEQUENCE lookup_product_ship_ti_code_seq;
CREATE TABLE lookup_product_ship_time (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

-- Each product can have a tax code
CREATE SEQUENCE lookup_product_tax_code_seq;
CREATE TABLE lookup_product_tax (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

-- Each price is either fixed or recurring, if recurring then when?
-- Example: Monthly, Weekly, Yearly, etc.
CREATE SEQUENCE lookup_recurring_type_code_seq;
CREATE TABLE lookup_recurring_type (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

-- The details of a product are listed
-- If this product is to replace another product, then the parent_id is set
-- Example: 1/16 Page Ad
CREATE SEQUENCE product_catalog_product_id_seq;
CREATE TABLE product_catalog (
  product_id INT  PRIMARY KEY,
  parent_id INTEGER REFERENCES product_catalog(product_id),
  product_name VARCHAR(255) NOT NULL,
  abbreviation VARCHAR(30),
  short_description CLOB,
  long_description CLOB,
  type_id INTEGER REFERENCES lookup_product_type(code),
  special_notes VARCHAR(255),
  sku VARCHAR(40),
  in_stock boolean DEFAULT true NOT NULL ,
  format_id INTEGER REFERENCES lookup_product_format(code),
  shipping_id INTEGER REFERENCES lookup_product_shipping(code),
  estimated_ship_time INTEGER REFERENCES lookup_product_ship_time(code),
  thumbnail_image_id INTEGER REFERENCES project_files(item_id),
  small_image_id INTEGER REFERENCES project_files(item_id),
  large_image_id INTEGER REFERENCES project_files(item_id),
  list_order INTEGER DEFAULT 10,
  enteredby INT references access(user_id) NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT references access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  start_date TIMESTAMP DEFAULT NULL,
  expiration_date TIMESTAMP DEFAULT NULL,
  enabled boolean DEFAULT true NOT NULL ,
  manufacturer_id INTEGER REFERENCES lookup_product_manufacturer(code),
  trashed_date TIMESTAMP,
  active boolean DEFAULT true NOT NULL,
  comments VARCHAR(255) DEFAULT NULL,
  import_id INTEGER REFERENCES import(import_id),
  status_id INTEGER
);

-- Each product can have a price, which can change over time
CREATE SEQUENCE product_catalog_pricing_price_id_seq;
CREATE TABLE product_catalog_pricing (
  price_id INT  PRIMARY KEY,
  product_id INTEGER REFERENCES product_catalog(product_id),
  tax_id INTEGER REFERENCES lookup_product_tax(code),
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT  DEFAULT 0 NOT NULL,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 ,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT DEFAULT 0 NOT NULL,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  enteredby INT references access(user_id) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT references access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  start_date TIMESTAMP DEFAULT NULL,
  expiration_date TIMESTAMP DEFAULT NULL,
  enabled boolean DEFAULT false,
  cost_currency INTEGER REFERENCES lookup_currency(code),
  cost_amount FLOAT DEFAULT 0 NOT NULL
);

-- A package can consist of products with discounted prices
CREATE SEQUENCE package_package_id_seq;
CREATE TABLE package (
  package_id INT  PRIMARY KEY,
  category_id INT REFERENCES product_category(category_id),
  package_name VARCHAR(255) NOT NULL,
  abbreviation VARCHAR(30),
  short_description CLOB,
  long_description CLOB,
  thumbnail_image_id INTEGER REFERENCES project_files(item_id),
  small_image_id INTEGER REFERENCES project_files(item_id),
  large_image_id INTEGER REFERENCES project_files(item_id),
  list_order INTEGER DEFAULT 10,
  enteredby INT REFERENCES access(user_id) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  start_date TIMESTAMP ,
  expiration_date TIMESTAMP ,
  enabled boolean DEFAULT true NOT NULL
);

-- Each package consists of one or more products
CREATE SEQUENCE package_products_map_id_seq;
CREATE TABLE package_products_map (
  id INT PRIMARY KEY,
  package_id INT REFERENCES package(package_id) NOT NULL,
  product_id INT REFERENCES product_catalog(product_id) NOT NULL,
  description CLOB,
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT DEFAULT 0 NOT NULL ,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 NOT NULL ,
  enteredby INT REFERENCES access(user_id) NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  start_date TIMESTAMP DEFAULT NULL,
  expiration_date TIMESTAMP DEFAULT NULL
);

-- Each product can be associated with multiple categories
CREATE SEQUENCE product_catalog_category_map_id_seq;
CREATE TABLE product_catalog_category_map (
  id INT PRIMARY KEY,
  product_id INTEGER REFERENCES product_catalog(product_id) NOT NULL ,
  category_id INTEGER REFERENCES product_category(category_id) NOT NULL 
);

-- Each configuration of an option has a result type that maps to the application
-- Example: Integer, Double, Text, Timestamp
CREATE SEQUENCE lookup_product_conf_re_code_seq;
CREATE TABLE lookup_product_conf_result (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

-- A configurator allows options to be displayed, validated
-- Example: A color configurator with 4 Options: None, B&W, Spot Color, Process Color
-- PROBLEM: How is the configured item price indicated?
CREATE SEQUENCE product_option_configurator_configurator_id_seq;
CREATE TABLE product_option_configurator (
  configurator_id INT  PRIMARY KEY,
  short_description CLOB,
  long_description CLOB,
  class_name VARCHAR(255),
  result_type INTEGER REFERENCES lookup_product_conf_result(code) NOT NULL ,
  configurator_name VARCHAR(300) NOT NULL
);

-- An option library allows products to be further customized by 
-- specifying what the option is and if/how it can be configured.
-- If this option is to replace another, then the parent_id is set
CREATE SEQUENCE product_option_option_id_seq;
CREATE TABLE product_option (
  option_id INT  PRIMARY KEY,
  configurator_id INTEGER REFERENCES product_option_configurator(configurator_id) NOT NULL ,
  parent_id INTEGER REFERENCES product_option(option_id),
  short_description VARCHAR(4192),
  long_description CLOB,
  allow_customer_configure BOOLEAN DEFAULT false NOT NULL,
  allow_user_configure BOOLEAN DEFAULT false NOT NULL,
  required BOOLEAN DEFAULT false NOT NULL ,
  start_date TIMESTAMP DEFAULT NULL,
  end_date TIMESTAMP DEFAULT NULL,
  enabled BOOLEAN DEFAULT false,
  option_name VARCHAR(300) NOT NULL,
  has_range boolean DEFAULT false,
  has_multiplier boolean DEFAULT false
);

-- Each option will have it's configurator defined when the option is created
CREATE SEQUENCE product_option_values_value_id_seq;
CREATE TABLE product_option_values (
  value_id INT  PRIMARY KEY,
  option_id INTEGER REFERENCES product_option(option_id) NOT NULL ,
  result_id INTEGER NOT NULL,
  description CLOB,
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT DEFAULT 0 NOT NULL ,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 NOT NULL , 
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT DEFAULT 0 NOT NULL ,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  enabled boolean DEFAULT true,
  value FLOAT DEFAULT 0,
  multiplier FLOAT DEFAULT 1,
  range_min INTEGER DEFAULT 1,
  range_max INTEGER DEFAULT 1,
  cost_currency INTEGER REFERENCES lookup_currency(code),
  cost_amount FLOAT DEFAULT 0 NOT NULL 
);
CREATE INDEX idx_pr_opt_val ON product_option_values (option_id, result_id);

-- Each product can be associated with multiple configurable options
CREATE SEQUENCE product_option_map_product_option_id_seq;
CREATE TABLE product_option_map (
  product_option_id INT PRIMARY KEY,
  product_id INTEGER REFERENCES product_catalog(product_id) NOT NULL ,
  option_id INTEGER REFERENCES product_option(option_id) NOT NULL 
);

-- For each option, there are structure fields specified by the configurator
-- which determine the option's structure
CREATE TABLE product_option_boolean (
  product_option_id INTEGER REFERENCES product_option(option_id) NOT NULL ,
  value BOOLEAN NOT NULL,
  id INTEGER
);

CREATE TABLE product_option_float (
  product_option_id INTEGER REFERENCES product_option(option_id) NOT NULL ,
  value FLOAT NOT NULL,
  id INTEGER
);

CREATE TABLE product_option_timestamp (
  product_option_id INTEGER REFERENCES product_option(option_id) NOT NULL ,
  value TIMESTAMP NOT NULL,
  id INTEGER
);

CREATE TABLE product_option_integer (
  product_option_id INTEGER REFERENCES product_option(option_id) NOT NULL ,
  value INTEGER NOT NULL,
  id INTEGER
);

CREATE TABLE product_option_text (
  product_option_id INTEGER REFERENCES product_option(option_id) NOT NULL ,
  value CLOB NOT NULL,
  id INTEGER
);

-- Each product can be associated with multiple keywords
-- Example: printer, printing, laser
CREATE SEQUENCE lookup_product_keyword_code_seq;
CREATE TABLE lookup_product_keyword (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE TABLE product_keyword_map (
  product_id INTEGER REFERENCES product_catalog(product_id) NOT NULL ,
  keyword_id INTEGER REFERENCES lookup_product_keyword(code) NOT NULL 
);
CREATE INDEX idx_pr_key_map ON product_keyword_map (product_id, keyword_id);

-- Each product_category can have "custom folders"
-- TABLES ALREADY EXIST, MUST CREATE A CONSTANT ID


-- Create Indexes

create index pcatalog_pid on product_catalog (parent_id);

create index pcatalog_name on product_catalog (product_name);

create index product_category_map_cid on product_catalog_category_map (category_id);

create index pcatalog_enteredby on product_catalog (enteredby);
      
create index pcatalog_estimated_ship_time on product_catalog (estimated_ship_time);

create index pcatalog_format_id on product_catalog (format_id);

create index pcatalog_import_id on product_catalog (import_id);

create index pcatalog_large_image_id on product_catalog (large_image_id);

create index pcatalog_manufacturer_id on product_catalog (manufacturer_id);

create index pcatalog_modifiedby on product_catalog (modifiedby);

create index pcatalog_shipping_id on product_catalog (shipping_id);

create index pcatalog_small_image_id on product_catalog (small_image_id);

create index pcatalog_thumbnail_image_id on product_catalog (thumbnail_image_id);

create index pcatalog_type_id on product_catalog (type_id);

create index pcategory_enteredby on product_category (enteredby);

create index pcategory_import_id on product_category (import_id);

create index pcategory_large_image_id on product_category (large_image_id);

create index pcategory_modifiedby on product_category (modifiedby);

create index pcategory_parent_id on product_category (parent_id);

create index pcategory_small_image_id on product_category (small_image_id);

create index pcategory_thumbnail_image_id on product_category (thumbnail_image_id);

create index pcategory_type_id on product_category (type_id);

