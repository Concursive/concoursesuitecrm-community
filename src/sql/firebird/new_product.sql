-- PostgreSQL Table Creation
-- @created    March 18, 2004
-- @version    $Id$
-- This schema represents a product catalog.  A product catalog is a
-- classification and definition of products that a company can sell.
-- REQUIRES: new_cdb.sql
-- REQUIRES: new_project.sql

-- Currency table, could extend to exchange rates
-- Example: USD
CREATE GENERATOR lookup_currency_code_seq;
CREATE TABLE lookup_currency (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- Each category can be associated with a type
-- Example: Publication, Target Audience

CREATE GENERATOR lookup_product_categor_code_seq;
CREATE TABLE lookup_product_category_type (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- A category groups one or more products
-- Example: Name of a publication, Name of a target audience
-- Old Name: product_category_category_id_seq;
CREATE GENERATOR product_categ_y_category_id_seq;
CREATE TABLE product_category (
  category_id INTEGER  NOT NULL,
  parent_id INTEGER,
  category_name VARCHAR(255) NOT NULL,
  abbreviation VARCHAR(30),
  short_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  long_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  type_id INTEGER REFERENCES lookup_product_category_type(code),
  thumbnail_image_id INTEGER REFERENCES project_files(item_id),
  small_image_id INTEGER REFERENCES project_files(item_id),
  large_image_id INTEGER REFERENCES project_files(item_id),
  list_order INTEGER DEFAULT 10,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  start_date TIMESTAMP ,
  expiration_date TIMESTAMP,
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  import_id INTEGER REFERENCES import(import_id),
  status_id INTEGER,
  PRIMARY KEY (CATEGORY_ID)
);

-- REQUIRED HERE - Firebird can not create a FK on itself during table create
ALTER TABLE PRODUCT_CATEGORY ADD CONSTRAINT FK_PROD_CATEGORY_PROD_ID
  FOREIGN KEY (PARENT_ID) REFERENCES PRODUCT_CATEGORY
  (CATEGORY_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Each category can be associated with multiple categories
CREATE GENERATOR product_category_map_id_seq;
CREATE TABLE product_category_map (
  id INTEGER NOT NULL,
  category1_id INTEGER NOT NULL REFERENCES product_category(category_id),
  category2_id INTEGER NOT NULL REFERENCES product_category(category_id),
  PRIMARY KEY (ID)
);

-- Each product can be associated with a type
-- Example: Ad, Design Ad, Subscription
CREATE GENERATOR lookup_product_type_code_seq;
CREATE TABLE lookup_product_type (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- Each product can have a manufacturer
-- Example: Nokia, Compaq
CREATE GENERATOR lookup_product_manufac_code_seq;
CREATE TABLE lookup_product_manufacturer (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- Each product can have a format
-- Example: Physical, Digital
CREATE GENERATOR lookup_product_format_code_seq;
CREATE TABLE lookup_product_format (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- Each product can have a shipping code for shipping company
CREATE GENERATOR lookup_product_shippin_code_seq;
CREATE TABLE lookup_product_shipping (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- Each product can have an estimated shipping time
-- Example: Ships within 24 hours
CREATE GENERATOR lookup_product_ship_ti_code_seq;
CREATE TABLE lookup_product_ship_time (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- Each product can have a tax code
CREATE GENERATOR lookup_product_tax_code_seq;
CREATE TABLE lookup_product_tax (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- Each price is either fixed or recurring, if recurring then when?
-- Example: Monthly, Weekly, Yearly, etc.
CREATE GENERATOR lookup_recurring_type_code_seq;
CREATE TABLE lookup_recurring_type (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- The details of a product are listed
-- If this product is to replace another product, then the parent_id is set
-- Example: 1/16 Page Ad
CREATE GENERATOR product_catalog_product_id_seq;
CREATE TABLE product_catalog (
  product_id INTEGER  NOT NULL,
  parent_id INTEGER,
  product_name VARCHAR(255) NOT NULL,
  abbreviation VARCHAR(30),
  short_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  long_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  type_id INTEGER REFERENCES lookup_product_type(code),
  special_notes VARCHAR(255),
  sku VARCHAR(40),
  in_stock CHAR(1) DEFAULT 'Y' NOT NULL ,
  format_id INTEGER REFERENCES lookup_product_format(code),
  shipping_id INTEGER REFERENCES lookup_product_shipping(code),
  estimated_ship_time INTEGER REFERENCES lookup_product_ship_time(code),
  thumbnail_image_id INTEGER REFERENCES project_files(item_id),
  small_image_id INTEGER REFERENCES project_files(item_id),
  large_image_id INTEGER REFERENCES project_files(item_id),
  list_order INTEGER DEFAULT 10,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  start_date TIMESTAMP DEFAULT NULL,
  expiration_date TIMESTAMP DEFAULT NULL,
  enabled CHAR(1) DEFAULT 'Y' NOT NULL ,
  manufacturer_id INTEGER REFERENCES lookup_product_manufacturer(code),
  trashed_date TIMESTAMP,
  "active" CHAR(1) DEFAULT 'Y' NOT NULL,
  comments VARCHAR(255),
  import_id INTEGER REFERENCES import(import_id),
  status_id INTEGER,
  PRIMARY KEY (PRODUCT_ID)
);

-- REQUIRED HERE - Firebird can not create a FK on itself during table create
ALTER TABLE PRODUCT_CATALOG ADD CONSTRAINT FK_PROD_CATALOG_PROD_ID
  FOREIGN KEY (PARENT_ID) REFERENCES PRODUCT_CATALOG
  (PRODUCT_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Each product can have a price, which can change over time
-- Old Name: product_catalog_pricing_price_id_seq;
CREATE GENERATOR product_catal_cing_price_id_seq;
CREATE TABLE product_catalog_pricing (
  price_id INTEGER  NOT NULL,
  product_id INTEGER REFERENCES product_catalog(product_id),
  tax_id INTEGER REFERENCES lookup_product_tax(code),
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT  DEFAULT 0 NOT NULL,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 ,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT DEFAULT 0 NOT NULL,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  start_date TIMESTAMP DEFAULT NULL,
  expiration_date TIMESTAMP DEFAULT NULL,
  enabled CHAR(1) DEFAULT 'Y',
  cost_currency INTEGER REFERENCES lookup_currency(code),
  cost_amount FLOAT DEFAULT 0 NOT NULL,
  PRIMARY KEY (PRICE_ID)
);

-- A package can consist of products with discounted prices
CREATE GENERATOR package_package_id_seq;
CREATE TABLE package (
  package_id INTEGER  NOT NULL,
  category_id INTEGER REFERENCES product_category(category_id),
  package_name VARCHAR(255) NOT NULL,
  abbreviation VARCHAR(30),
  short_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  long_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  thumbnail_image_id INTEGER REFERENCES project_files(item_id),
  small_image_id INTEGER REFERENCES project_files(item_id),
  large_image_id INTEGER REFERENCES project_files(item_id),
  list_order INTEGER DEFAULT 10,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  start_date TIMESTAMP ,
  expiration_date TIMESTAMP ,
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  PRIMARY KEY (PACKAGE_ID)
);

-- Each package consists of one or more products
CREATE GENERATOR package_products_map_id_seq;
CREATE TABLE package_products_map (
  id INTEGER NOT NULL,
  package_id INTEGER NOT NULL REFERENCES package(package_id),
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT DEFAULT 0 NOT NULL ,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  start_date TIMESTAMP DEFAULT NULL,
  expiration_date TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (ID)
);

-- Each product can be associated with multiple categories
-- Old Name:  product_catalog_category_map_id_seq;
CREATE GENERATOR product_catal_tegory_map_id_seq;
CREATE TABLE product_catalog_category_map (
  id INTEGER NOT NULL,
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  category_id INTEGER NOT NULL REFERENCES product_category(category_id),
  PRIMARY KEY (ID)
);

-- Each configuration of an option has a result type that maps to the application
-- Example: Integer, Double, Text, Timestamp
CREATE GENERATOR lookup_product_conf_re_code_seq;
CREATE TABLE lookup_product_conf_result (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- A configurator allows options to be displayed, validated
-- Example: A color configurator with 4 Options: None, B&W, Spot Color, Process Color
-- PROBLEM: How is the configured item price indicated?

-- Old Name: product_option_configurator_configurator_id_seq;
CREATE GENERATOR product_optio_nfigurator_id_seq;
CREATE TABLE product_option_configurator (
  configurator_id INTEGER  NOT NULL,
  short_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  long_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  class_name VARCHAR(255),
  result_type INTEGER NOT NULL REFERENCES lookup_product_conf_result(code),
  configurator_name VARCHAR(300) NOT NULL,
  PRIMARY KEY (CONFIGURATOR_ID)
);

-- An option library allows products to be further customized by
-- specifying what the option is and if/how it can be configured.
-- If this option is to replace another, then the parent_id is set
CREATE GENERATOR product_option_option_id_seq;
CREATE TABLE product_option (
  option_id INTEGER  NOT NULL,
  configurator_id INTEGER NOT NULL REFERENCES product_option_configurator(configurator_id),
  parent_id INTEGER,
  short_description VARCHAR(4192),
  long_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  allow_customer_configure CHAR(1) DEFAULT 'N' NOT NULL ,
  allow_user_configure CHAR(1) DEFAULT 'N' NOT NULL ,
  required CHAR(1) DEFAULT 'N' NOT NULL ,
  start_date TIMESTAMP DEFAULT NULL,
  end_date TIMESTAMP DEFAULT NULL,
  enabled CHAR(1) DEFAULT 'N',
  option_name VARCHAR(300) NOT NULL,
  has_range CHAR(1) DEFAULT 'N',
  has_multiplier CHAR(1) DEFAULT 'N',
  PRIMARY KEY (OPTION_ID)
);

-- REQUIRED HERE - Firebird can not create a FK on itself during table create
ALTER TABLE PRODUCT_OPTION ADD CONSTRAINT FK_PROD_OPT_OPT_ID
  FOREIGN KEY (PARENT_ID) REFERENCES PRODUCT_OPTION
  (OPTION_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Each option will have it's configurator defined when the option is created
-- Old Name: product_option_values_value_id_seq;
CREATE GENERATOR product_optio_lues_value_id_seq;
CREATE TABLE product_option_values (
  value_id INTEGER  NOT NULL,
  option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  result_id INTEGER NOT NULL,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT DEFAULT 0 NOT NULL ,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT DEFAULT 0 NOT NULL ,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT DEFAULT 0 NOT NULL ,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  enabled CHAR(1) DEFAULT 'Y',
  "value" FLOAT DEFAULT 0,
  multiplier FLOAT DEFAULT 1,
  range_min INTEGER DEFAULT 1,
  range_max INTEGER DEFAULT 1,
  cost_currency INTEGER REFERENCES lookup_currency(code),
  cost_amount FLOAT DEFAULT 0 NOT NULL,
  PRIMARY KEY (VALUE_ID)
);

CREATE INDEX idx_pr_opt_val ON product_option_values (option_id, result_id);

-- Each product can be associated with multiple configurable options
-- Old Name: product_option_map_product_option_id_seq;
CREATE GENERATOR product_optio_uct_option_id_seq;
CREATE TABLE product_option_map (
  product_option_id INTEGER NOT NULL,
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  PRIMARY KEY (PRODUCT_OPTION_ID)
);

-- For each option, there are structure fields specified by the configurator
-- which determine the option's structure
CREATE TABLE product_option_boolean (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  "value" CHAR(1) NOT NULL,
  id INTEGER
);

CREATE TABLE product_option_float (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  "value" FLOAT NOT NULL,
  id INTEGER
);

CREATE TABLE product_option_timestamp (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  "value" TIMESTAMP NOT NULL,
  id INTEGER
);

CREATE TABLE product_option_integer (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  "value" INTEGER NOT NULL,
  id INTEGER
);

CREATE TABLE product_option_text (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  "value" BLOB SUB_TYPE 1 SEGMENT SIZE 100 NOT NULL,
  id INTEGER
);

-- Each product can be associated with multiple keywords
-- Example: printer, printing, laser
CREATE GENERATOR lookup_product_keyword_code_seq;
CREATE TABLE lookup_product_keyword (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

CREATE TABLE product_keyword_map (
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  keyword_id INTEGER NOT NULL REFERENCES lookup_product_keyword(code)
);

CREATE INDEX idx_pr_key_map ON product_keyword_map (product_id, keyword_id);
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

create index pcatalog_parent_id on product_catalog (parent_id);

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