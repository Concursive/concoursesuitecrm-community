-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------
 
CREATE TABLE lookup_asset_status(
 code INT AUTO_INCREMENT PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_asset_status_entries BEFORE INSERT ON lookup_asset_status FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_sc_category(
 code INT AUTO_INCREMENT PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_sc_category_entries BEFORE INSERT ON lookup_sc_category FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_sc_type(
 code INT AUTO_INCREMENT PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_sc_type_entries BEFORE INSERT ON lookup_sc_type FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_response_model(
 code INT AUTO_INCREMENT PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_response_model_entries BEFORE INSERT ON lookup_response_model FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_phone_model(
 code INT AUTO_INCREMENT PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_phone_model_entries BEFORE INSERT ON lookup_phone_model FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_onsite_model(
 code INT AUTO_INCREMENT PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_onsite_model_entries BEFORE INSERT ON lookup_onsite_model FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_email_model(
 code INT AUTO_INCREMENT PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_email_model_entries BEFORE INSERT ON lookup_email_model FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_hours_reason(
 code INT AUTO_INCREMENT PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_hours_reason_entries BEFORE INSERT ON lookup_hours_reason FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_asset_manufacturer(
 code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_asset_manufacturer_entries BEFORE INSERT ON lookup_asset_manufacturer FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_asset_vendor(
 code INT AUTO_INCREMENT PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_asset_vendor_entries BEFORE INSERT ON lookup_asset_vendor FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE service_contract (
  contract_id INT AUTO_INCREMENT PRIMARY KEY,
  contract_number VARCHAR(30),
  account_id INT NOT NULL REFERENCES organization(org_id),
  initial_start_date TIMESTAMP NULL,
  current_start_date TIMESTAMP NULL,
  current_end_date TIMESTAMP NULL,
  category INT REFERENCES lookup_sc_category(code),
  type INT REFERENCES lookup_sc_type(code),
  contact_id INT REFERENCES contact(contact_id),
  description TEXT,
  contract_billing_notes TEXT,
  response_time INT REFERENCES lookup_response_model(code),
  telephone_service_model INT REFERENCES lookup_phone_model(code),
  onsite_service_model INT REFERENCES lookup_onsite_model(code),
  email_service_model INT REFERENCES lookup_email_model(code),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  enabled boolean DEFAULT true,
  contract_value FLOAT,
  total_hours_remaining FLOAT,
  service_model_notes TEXT,
  initial_start_date_timezone VARCHAR(255),
  current_start_date_timezone VARCHAR(255),
  current_end_date_timezone VARCHAR(255),
  trashed_date TIMESTAMP NULL,
  submitter_id INT REFERENCES organization
);

CREATE TRIGGER service_contract_entries BEFORE INSERT ON service_contract FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE service_contract_hours (
  history_id INT AUTO_INCREMENT PRIMARY KEY,
  link_contract_id INT REFERENCES service_contract(contract_id),
  adjustment_hours FLOAT,
  adjustment_reason INT REFERENCES lookup_hours_reason(code),
  adjustment_notes TEXT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);

CREATE TRIGGER service_contract_hours_entries BEFORE INSERT ON service_contract_hours FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE service_contract_products(
  id INT AUTO_INCREMENT PRIMARY KEY,
  link_contract_id INT REFERENCES service_contract(contract_id),
  link_product_id INT REFERENCES product_catalog(product_id)
);

CREATE TABLE asset_category ( 
  id INT AUTO_INCREMENT PRIMARY KEY,
  cat_level int NOT NULL DEFAULT 0,
  parent_cat_code int NOT NULL DEFAULT 0,
  description VARCHAR(300) NOT NULL,
  full_description text,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  site_id INTEGER REFERENCES lookup_site_id(code)
);

CREATE TABLE asset_category_draft (
  id INT AUTO_INCREMENT PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int NOT NULL DEFAULT 0,
  parent_cat_code int NOT NULL DEFAULT 0,
  description VARCHAR(300) NOT NULL,
  full_description text,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  site_id INTEGER REFERENCES lookup_site_id(code)
);

CREATE TABLE asset (
  asset_id INT AUTO_INCREMENT PRIMARY KEY,
  account_id INT REFERENCES organization(org_id),
  contract_id INT REFERENCES service_contract(contract_id),
  date_listed TIMESTAMP NULL,
  asset_tag VARCHAR(30),
  status INT,
  location VARCHAR(256),
  level1 INT REFERENCES asset_category(id),
  level2 INT REFERENCES asset_category(id),
  level3 INT REFERENCES asset_category(id),
  serial_number VARCHAR(30),
  model_version VARCHAR(30),
  description TEXT,
  expiration_date TIMESTAMP NULL,
  inclusions TEXT,
  exclusions TEXT,
  purchase_date TIMESTAMP NULL,
  po_number VARCHAR(30),
  purchased_from VARCHAR(30),
  contact_id INT REFERENCES contact(contact_id),
  notes TEXT,
  response_time INT REFERENCES lookup_response_model(code),
  telephone_service_model INT REFERENCES lookup_phone_model(code),
  onsite_service_model INT REFERENCES lookup_onsite_model(code),
  email_service_model INT REFERENCES lookup_email_model(code),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  enabled boolean DEFAULT true,
  purchase_cost FLOAT,
  date_listed_timezone VARCHAR(255),
  expiration_date_timezone VARCHAR(255),
  purchase_date_timezone VARCHAR(255),
  trashed_date TIMESTAMP NULL,
  parent_id INTEGER REFERENCES asset(asset_id),
  vendor_code INT REFERENCES lookup_asset_vendor(code),
  manufacturer_code INT REFERENCES lookup_asset_manufacturer(code)
);

CREATE TRIGGER asset_entries BEFORE INSERT ON asset FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_asset_materials(
 code INT AUTO_INCREMENT PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_asset_materials_entries BEFORE INSERT ON lookup_asset_materials FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE asset_materials_map (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  asset_id INTEGER NOT NULL REFERENCES asset(asset_id),
  code INTEGER NOT NULL REFERENCES lookup_asset_materials(code),
  quantity FLOAT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER asset_materials_map_entries BEFORE INSERT ON asset_materials_map FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered);
