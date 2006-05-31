/**
 *  MSSQL Table Creation
 *
 *@author       matt rajkowski
 *@created      February 2, 2004
 *@version      $Id$
 */
 
CREATE TABLE lookup_asset_status(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE lookup_sc_category(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE lookup_sc_type(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE lookup_response_model(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE lookup_phone_model(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE lookup_onsite_model(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE lookup_email_model(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE lookup_hours_reason(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE lookup_asset_manufacturer(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE lookup_asset_vendor(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE service_contract (
  contract_id INT IDENTITY PRIMARY KEY,
  contract_number VARCHAR(30),
  account_id INT NOT NULL REFERENCES organization(org_id),
  initial_start_date DATETIME NOT NULL,
  current_start_date DATETIME,
  current_end_date DATETIME,
  category INT REFERENCES lookup_sc_category(code),
  type INT REFERENCES lookup_sc_type(code),
  contact_id INT REFERENCES contact(contact_id),
  description TEXT,
  contract_billing_notes TEXT,
  response_time INT REFERENCES lookup_response_model(code),
  telephone_service_model INT REFERENCES lookup_phone_model(code),
  onsite_service_model INT REFERENCES lookup_onsite_model(code),
  email_service_model INT REFERENCES lookup_email_model(code),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  enabled BIT DEFAULT 1,
  contract_value FLOAT,
  total_hours_remaining FLOAT,
  service_model_notes TEXT,
  initial_start_date_timezone VARCHAR(255),
  current_start_date_timezone VARCHAR(255),
  current_end_date_timezone VARCHAR(255),
  trashed_date DATETIME
);

CREATE TABLE service_contract_hours (
  history_id INT IDENTITY PRIMARY KEY,
  link_contract_id INT REFERENCES service_contract(contract_id),
  adjustment_hours FLOAT,
  adjustment_reason INT REFERENCES lookup_hours_reason(code),
  adjustment_notes TEXT,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
); 

CREATE TABLE service_contract_products(
  id INT IDENTITY PRIMARY KEY,
  link_contract_id INT REFERENCES service_contract(contract_id),
  link_product_id INT REFERENCES product_catalog(product_id)
);

CREATE TABLE asset_category ( 
  id INT IDENTITY PRIMARY KEY,
  cat_level int NOT NULL DEFAULT 0,
  parent_cat_code int NOT NULL DEFAULT 0,
  description VARCHAR(300) NOT NULL,
  full_description text NOT NULL DEFAULT '',
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  site_id INTEGER REFERENCES lookup_site_id(code)
);

CREATE TABLE asset_category_draft (
  id INT IDENTITY PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int NOT NULL DEFAULT 0,
  parent_cat_code int NOT NULL DEFAULT 0,
  description VARCHAR(300) NOT NULL,
  full_description text NOT NULL DEFAULT '',
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  site_id INTEGER REFERENCES lookup_site_id(code)
);

CREATE TABLE asset (
  asset_id INT IDENTITY PRIMARY KEY,
  account_id INT REFERENCES organization(org_id),
  contract_id INT REFERENCES service_contract(contract_id),
  date_listed DATETIME,
  asset_tag VARCHAR(30),
  status INT,
  location VARCHAR(256),
  level1 INT REFERENCES asset_category(id),
  level2 INT REFERENCES asset_category(id),
  level3 INT REFERENCES asset_category(id),
  serial_number VARCHAR(30),
  model_version VARCHAR(30),
  description TEXT,
  expiration_date DATETIME,
  inclusions TEXT,
  exclusions TEXT,
  purchase_date DATETIME,
  po_number VARCHAR(30),
  purchased_from VARCHAR(30),
  contact_id INT REFERENCES contact(contact_id),
  notes TEXT,
  response_time INT REFERENCES lookup_response_model(code),
  telephone_service_model INT REFERENCES lookup_phone_model(code),
  onsite_service_model INT REFERENCES lookup_onsite_model(code),
  email_service_model INT REFERENCES lookup_email_model(code),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  enabled BIT DEFAULT 1,
  purchase_cost FLOAT,
  date_listed_timezone VARCHAR(255),
  expiration_date_timezone VARCHAR(255),
  purchase_date_timezone VARCHAR(255),
  trashed_date DATETIME,
  parent_id INTEGER REFERENCES asset(asset_id),
  vendor_code INT REFERENCES lookup_asset_vendor(code),
  manufacturer_code INT REFERENCES lookup_asset_manufacturer(code)
);

CREATE TABLE lookup_asset_materials(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE asset_materials_map (
  map_id INT IDENTITY PRIMARY KEY,
  asset_id INTEGER NOT NULL REFERENCES asset(asset_id),
  code INTEGER NOT NULL REFERENCES lookup_asset_materials(code),
  quantity FLOAT,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

