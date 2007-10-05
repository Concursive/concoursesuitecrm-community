-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

-- This schema represents a product catalog.  A product catalog is a
-- classification and definition of products that a company can sell.
-- REQUIRES: new_cdb.sql
-- REQUIRES: new_project.sql

-- Currency table, could extend to exchange rates
-- Example: USD
CREATE TABLE lookup_currency (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_currency_entries BEFORE INSERT ON lookup_currency FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Each category can be associated with a type
-- Example: Publication, Target Audience

CREATE TABLE lookup_product_category_type (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lokp_prdct_ctgo_tp_entries BEFORE INSERT ON lookup_product_category_type FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- A category groups one or more products
-- Example: Name of a publication, Name of a target audience
CREATE TABLE product_category (
  category_id INT AUTO_INCREMENT PRIMARY KEY,
  parent_id INTEGER REFERENCES product_category(category_id),
  category_name VARCHAR(255) NOT NULL,
  abbreviation VARCHAR(30),
  short_description TEXT,
  long_description TEXT,
  type_id INTEGER REFERENCES lookup_product_category_type(code),
  thumbnail_image_id INTEGER REFERENCES project_files(item_id),
  small_image_id INTEGER REFERENCES project_files(item_id),
  large_image_id INTEGER REFERENCES project_files(item_id),
  list_order INTEGER DEFAULT 10,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  start_date TIMESTAMP NULL,
  expiration_date TIMESTAMP NULL,
  enabled boolean NOT NULL DEFAULT true,
  import_id INTEGER REFERENCES import(import_id),
  status_id INTEGER
);

CREATE TRIGGER product_category_entries BEFORE INSERT ON product_category FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Each category can be associated with multiple categories
CREATE TABLE product_category_map (
  id INT AUTO_INCREMENT PRIMARY KEY,
  category1_id INTEGER NOT NULL REFERENCES product_category(category_id),
  category2_id INTEGER NOT NULL REFERENCES product_category(category_id)
);


-- Each product can be associated with a type
-- Example: Ad, Design Ad, Subscription
CREATE TABLE lookup_product_type (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_product_type_entries BEFORE INSERT ON lookup_product_type FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Each product can have a manufacturer
-- Example: Nokia, Compaq
CREATE TABLE lookup_product_manufacturer (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lkp_prdct_mnfctrr_entries BEFORE INSERT ON lookup_product_manufacturer FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Each product can have a format
-- Example: Physical, Digital
CREATE TABLE lookup_product_format (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_prdct_frmt_entries BEFORE INSERT ON lookup_product_format FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Each product can have a shipping code for shipping company
CREATE TABLE lookup_product_shipping (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_prdct_shppng_entries BEFORE INSERT ON lookup_product_shipping FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Each product can have an estimated shipping time
-- Example: Ships within 24 hours
CREATE TABLE lookup_product_ship_time (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lkp_prdct_shp_tm_entries BEFORE INSERT ON lookup_product_ship_time FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Each product can have a tax code
CREATE TABLE lookup_product_tax (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lkp_prdct_tx_entries BEFORE INSERT ON lookup_product_tax FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Each price is either fixed or recurring, if recurring then when?
-- Example: Monthly, Weekly, Yearly, etc.
CREATE TABLE lookup_recurring_type (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lkp_rcrrng_tp_entries BEFORE INSERT ON lookup_recurring_type FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- The details of a product are listed
-- If this product is to replace another product, then the parent_id is set
-- Example: 1/16 Page Ad
CREATE TABLE product_catalog (
  product_id INT AUTO_INCREMENT PRIMARY KEY,
  parent_id INTEGER REFERENCES product_catalog(product_id),
  product_name VARCHAR(255) NOT NULL,
  abbreviation VARCHAR(30),
  short_description TEXT,
  long_description TEXT,
  type_id INTEGER REFERENCES lookup_product_type(code),
  special_notes VARCHAR(255),
  sku VARCHAR(40),
  in_stock BOOLEAN NOT NULL DEFAULT true,
  format_id INTEGER REFERENCES lookup_product_format(code),
  shipping_id INTEGER REFERENCES lookup_product_shipping(code),
  estimated_ship_time INTEGER REFERENCES lookup_product_ship_time(code),
  thumbnail_image_id INTEGER REFERENCES project_files(item_id),
  small_image_id INTEGER REFERENCES project_files(item_id),
  large_image_id INTEGER REFERENCES project_files(item_id),
  list_order INTEGER DEFAULT 10,
  enteredby INT NOT NULL references `access`(user_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  start_date TIMESTAMP NULL,
  expiration_date TIMESTAMP NULL,
  enabled boolean NOT NULL DEFAULT true,
  manufacturer_id INTEGER REFERENCES lookup_product_manufacturer(code),
  trashed_date TIMESTAMP NULL,
  active BOOLEAN NOT NULL DEFAULT true,
  comments VARCHAR(255) DEFAULT NULL,
  import_id INTEGER REFERENCES import(import_id),
  status_id INTEGER
);

CREATE TRIGGER product_catalog_entries BEFORE INSERT ON product_catalog FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Each product can have a price, which can change over time
CREATE TABLE product_catalog_pricing (
  price_id INT AUTO_INCREMENT PRIMARY KEY,
  product_id INTEGER REFERENCES product_catalog(product_id),
  tax_id INTEGER REFERENCES lookup_product_tax(code),
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT NOT NULL DEFAULT 0,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT NOT NULL DEFAULT 0,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT NOT NULL DEFAULT 0,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  enteredby INT NOT NULL references `access`(user_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  start_date TIMESTAMP NULL,
  expiration_date TIMESTAMP NULL,
  enabled boolean DEFAULT false,
  cost_currency INTEGER REFERENCES lookup_currency(code),
  cost_amount FLOAT NOT NULL DEFAULT 0
);

CREATE TRIGGER product_catalog_pricing_entries BEFORE INSERT ON product_catalog_pricing FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- A package can consist of products with discounted prices
CREATE TABLE package (
  package_id INT AUTO_INCREMENT PRIMARY KEY,
  category_id INT REFERENCES product_category(category_id),
  package_name VARCHAR(255) NOT NULL,
  abbreviation VARCHAR(30),
  short_description TEXT,
  long_description TEXT,
  thumbnail_image_id INTEGER REFERENCES project_files(item_id),
  small_image_id INTEGER REFERENCES project_files(item_id),
  large_image_id INTEGER REFERENCES project_files(item_id),
  list_order INTEGER DEFAULT 10,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  start_date TIMESTAMP NULL,
  expiration_date TIMESTAMP NULL,
  enabled boolean NOT NULL DEFAULT true
);

CREATE TRIGGER package_entries BEFORE INSERT ON package FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Each package consists of one or more products
CREATE TABLE package_products_map (
  id INT AUTO_INCREMENT PRIMARY KEY,
  package_id INT NOT NULL REFERENCES package(package_id),
  product_id INT NOT NULL REFERENCES product_catalog(product_id),
  description TEXT,
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT NOT NULL DEFAULT 0,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT NOT NULL DEFAULT 0,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  start_date TIMESTAMP NULL,
  expiration_date TIMESTAMP NULL
);

CREATE TRIGGER package_products_map_entries BEFORE INSERT ON package_products_map FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Each product can be associated with multiple categories
CREATE TABLE product_catalog_category_map (
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  category_id INTEGER NOT NULL REFERENCES product_category(category_id)
);

-- Each configuration of an option has a result type that maps to the application
-- Example: Integer, Double, Text, Timestamp
CREATE TABLE lookup_product_conf_result (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_product_conf_result_entries BEFORE INSERT ON lookup_product_conf_result FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- A configurator allows options to be displayed, validated
-- Example: A color configurator with 4 Options: None, B&W, Spot Color, Process Color
-- PROBLEM: How is the configured item price indicated?
CREATE TABLE product_option_configurator (
  configurator_id INT AUTO_INCREMENT PRIMARY KEY,
  short_description TEXT,
  long_description TEXT,
  class_name VARCHAR(255),
  result_type INTEGER NOT NULL REFERENCES lookup_product_conf_result(code),
  configurator_name VARCHAR(300) NOT NULL
);

-- An option library allows products to be further customized by 
-- specifying what the option is and if/how it can be configured.
-- If this option is to replace another, then the parent_id is set
CREATE TABLE product_option (
  option_id INT AUTO_INCREMENT PRIMARY KEY,
  configurator_id INTEGER NOT NULL REFERENCES product_option_configurator(configurator_id),
  parent_id INTEGER REFERENCES product_option(option_id),
  short_description TEXT,
  long_description TEXT,
  allow_customer_configure BOOLEAN NOT NULL DEFAULT false,
  allow_user_configure BOOLEAN NOT NULL DEFAULT false,
  required BOOLEAN NOT NULL DEFAULT false,
  start_date TIMESTAMP NULL,
  end_date TIMESTAMP NULL,
  enabled BOOLEAN DEFAULT false,
  option_name VARCHAR(300),
  has_range boolean DEFAULT false,
  has_multiplier boolean DEFAULT false
);

-- Each option will have it's configurator defined when the option is created
CREATE TABLE product_option_values (
  value_id INT AUTO_INCREMENT PRIMARY KEY,
  option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  result_id INTEGER NOT NULL,
  description TEXT,
  msrp_currency INTEGER REFERENCES lookup_currency(code),
  msrp_amount FLOAT NOT NULL DEFAULT 0,
  price_currency INTEGER REFERENCES lookup_currency(code),
  price_amount FLOAT NOT NULL DEFAULT 0,
  recurring_currency INTEGER REFERENCES lookup_currency(code),
  recurring_amount FLOAT NOT NULL DEFAULT 0,
  recurring_type INTEGER REFERENCES lookup_recurring_type(code),
  enabled boolean DEFAULT true,
  value FLOAT DEFAULT 0,
  multiplier FLOAT DEFAULT 1,
  range_min INTEGER DEFAULT 1,
  range_max INTEGER DEFAULT 1,
  cost_currency INTEGER REFERENCES lookup_currency(code),
  cost_amount FLOAT NOT NULL DEFAULT 0
);
CREATE UNIQUE INDEX idx_pr_opt_val ON product_option_values (value_id, option_id, result_id);

-- Each product can be associated with multiple configurable options
CREATE TABLE product_option_map (
  product_option_id INT AUTO_INCREMENT PRIMARY KEY,
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  option_id INTEGER NOT NULL REFERENCES product_option(option_id)
);

-- For each option, there are structure fields specified by the configurator
-- which determine the option's structure
CREATE TABLE product_option_boolean (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  value BOOLEAN NOT NULL,
  id INTEGER
);

CREATE TABLE product_option_float (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  value FLOAT NOT NULL,
  id INTEGER
);

CREATE TABLE product_option_timestamp (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  value TIMESTAMP NULL,
  id INTEGER
);

CREATE TABLE product_option_integer (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  value INTEGER NOT NULL,
  id INTEGER
);

CREATE TABLE product_option_text (
  product_option_id INTEGER NOT NULL REFERENCES product_option(option_id),
  value TEXT NOT NULL,
  id INTEGER
);

-- Each product can be associated with multiple keywords
-- Example: printer, printing, laser
CREATE TABLE lookup_product_keyword (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_product_keyword_entries BEFORE INSERT ON lookup_product_keyword FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE product_keyword_map (
  product_id INTEGER NOT NULL REFERENCES product_catalog(product_id),
  keyword_id INTEGER NOT NULL REFERENCES lookup_product_keyword(code)
);
CREATE UNIQUE INDEX idx_pr_key_map ON product_keyword_map (product_id, keyword_id);

-- Each product_category can have "custom folders"
-- TABLES ALREADY EXIST, MUST CREATE A CONSTANT ID

-- Create Indexes

create index product_category_map_cid on product_catalog_category_map (category_id);

create index pcatalog_pid on product_catalog (parent_id);
create index pcatalog_name on product_catalog (product_name);
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

create index product_pricing_product_idx on  product_catalog_pricing (product_id);

