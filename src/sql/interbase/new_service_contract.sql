
CREATE GENERATOR lookup_asset_status_code_seq;
CREATE TABLE lookup_asset_status(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_sc_category_code_seq;
CREATE TABLE lookup_sc_category(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_sc_type_code_seq;
CREATE TABLE lookup_sc_type(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_response_model_code_seq;
CREATE TABLE lookup_response_model(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_phone_model_code_seq;
CREATE TABLE lookup_phone_model(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_onsite_model_code_seq;
CREATE TABLE lookup_onsite_model(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_email_model_code_seq;
CREATE TABLE lookup_email_model(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_hours_reason_code_seq;
CREATE TABLE lookup_hours_reason(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_asset_manufactu_code_seq;
CREATE TABLE lookup_asset_manufacturer(
 code INTEGER NOT NULL PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR lookup_asset_vendor_code_seq;
CREATE TABLE lookup_asset_vendor(
 code INT NOT NULL PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE
);

-- Old Name: service_contract_contract_id_seq;
CREATE GENERATOR service_contr_t_contract_id_seq;
CREATE TABLE service_contract (
  contract_id INTEGER  NOT NULL,
  contract_number VARCHAR(30),
  account_id INTEGER NOT NULL REFERENCES organization(org_id),
  initial_start_date TIMESTAMP NOT NULL,
  current_start_date TIMESTAMP,
  current_end_date TIMESTAMP,
  category INTEGER REFERENCES lookup_sc_category(code),
  "type" INTEGER REFERENCES lookup_sc_type(code),
  contact_id INTEGER REFERENCES contact(contact_id),
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  contract_billing_notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  response_time INTEGER REFERENCES lookup_response_model(code),
  telephone_service_model INTEGER REFERENCES lookup_phone_model(code),
  onsite_service_model INTEGER REFERENCES lookup_onsite_model(code),
  email_service_model INTEGER REFERENCES lookup_email_model(code),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  contract_value FLOAT,
  total_hours_remaining FLOAT,
  service_model_notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  initial_start_date_timezone VARCHAR(255),
  current_start_date_timezone VARCHAR(255),
  current_end_date_timezone VARCHAR(255),
  trashed_date TIMESTAMP,
  PRIMARY KEY (CONTRACT_ID)
);

-- Old Name: service_contract_hours_history_id_seq;
CREATE GENERATOR service_contr_rs_history_id_seq;
CREATE TABLE service_contract_hours (
  history_id INTEGER  NOT NULL,
  link_contract_id INTEGER REFERENCES service_contract(contract_id),
  adjustment_hours FLOAT,
  adjustment_reason INTEGER REFERENCES lookup_hours_reason(code),
  adjustment_notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (HISTORY_ID)
);

-- Old Name: service_contract_products_id_seq;
CREATE GENERATOR service_contr_t_products_id_seq;
CREATE TABLE service_contract_products(
  id INTEGER  NOT NULL,
  link_contract_id INTEGER REFERENCES service_contract(contract_id),
  link_product_id INTEGER REFERENCES product_catalog(product_id),
  PRIMARY KEY (ID)
);

CREATE GENERATOR asset_category_id_seq;
CREATE TABLE asset_category (
  id INTEGER NOT NULL,
  cat_level INTEGER DEFAULT 0 NOT NULL ,
  parent_cat_code INTEGER DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description BLOB SUB_TYPE 1 SEGMENT SIZE 100 DEFAULT '' NOT NULL ,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  site_id INTEGER REFERENCES lookup_site_id(code),
  PRIMARY KEY (ID)
);

CREATE GENERATOR asset_category_draft_id_seq;
CREATE TABLE asset_category_draft (
  id INTEGER NOT NULL,
  link_id INTEGER DEFAULT -1,
  cat_level INTEGER DEFAULT 0 NOT NULL ,
  parent_cat_code INTEGER DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description BLOB SUB_TYPE 1 SEGMENT SIZE 100 DEFAULT '' NOT NULL ,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  site_id INTEGER REFERENCES lookup_site_id(code),
  PRIMARY KEY (ID)
);

CREATE GENERATOR asset_asset_id_seq;
CREATE TABLE asset (
  asset_id INTEGER NOT NULL PRIMARY KEY,
  account_id INTEGER REFERENCES organization(org_id),
  contract_id INTEGER REFERENCES service_contract(contract_id),
  date_listed TIMESTAMP,
  asset_tag VARCHAR(30),
  status INTEGER,
  location VARCHAR(256),
  level1 INTEGER REFERENCES asset_category(id),
  level2 INTEGER REFERENCES asset_category(id),
  level3 INTEGER REFERENCES asset_category(id),
  serial_number VARCHAR(30),
  model_version VARCHAR(30),
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  expiration_date TIMESTAMP,
  inclusions BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  exclusions BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  purchase_date TIMESTAMP,
  po_number VARCHAR(30),
  purchased_from VARCHAR(30),
  contact_id INTEGER REFERENCES contact(contact_id),
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  response_time INTEGER REFERENCES lookup_response_model(code),
  telephone_service_model INTEGER REFERENCES lookup_phone_model(code),
  onsite_service_model INTEGER REFERENCES lookup_onsite_model(code),
  email_service_model INTEGER REFERENCES lookup_email_model(code),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id) ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  purchase_cost FLOAT,
  date_listed_timezone VARCHAR(255),
  expiration_date_timezone VARCHAR(255),
  purchase_date_timezone VARCHAR(255),
  trashed_date TIMESTAMP,
  parent_id INTEGER,
  vendor_code INT REFERENCES lookup_asset_vendor(code),
  manufacturer_code INT REFERENCES lookup_asset_manufacturer(code)
);

-- Firebird cannot reference itself (primary key) during table creation
ALTER TABLE asset ADD CONSTRAINT FK_ASSET_PARENT_ID
  FOREIGN KEY (parent_id) REFERENCES asset(asset_id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE GENERATOR lookup_asset_materials_code_seq;
CREATE TABLE lookup_asset_materials(
 code INT NOT NULL PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR asset_materials_map_map_id_seq;
CREATE TABLE asset_materials_map (
  map_id INT NOT NULL PRIMARY KEY,
  asset_id INTEGER NOT NULL REFERENCES asset(asset_id),
  code INTEGER NOT NULL REFERENCES lookup_asset_materials(code),
  quantity FLOAT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE GENERATOR lookup_asset_status_code_seq;
CREATE TABLE lookup_asset_status(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_sc_category_code_seq;
CREATE TABLE lookup_sc_category(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_sc_type_code_seq;
CREATE TABLE lookup_sc_type(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_response_model_code_seq;
CREATE TABLE lookup_response_model(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_phone_model_code_seq;
CREATE TABLE lookup_phone_model(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_onsite_model_code_seq;
CREATE TABLE lookup_onsite_model(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_email_model_code_seq;
CREATE TABLE lookup_email_model(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_hours_reason_code_seq;
CREATE TABLE lookup_hours_reason(
 code INTEGER NOT NULL,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE,
 PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_asset_manufactu_code_seq;
CREATE TABLE lookup_asset_manufacturer(
 code INTEGER NOT NULL PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR lookup_asset_vendor_code_seq;
CREATE TABLE lookup_asset_vendor(
 code INT NOT NULL PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE
);

-- Old Name: service_contract_contract_id_seq;
CREATE GENERATOR service_contr_t_contract_id_seq;
CREATE TABLE service_contract (
  contract_id INTEGER  NOT NULL,
  contract_number VARCHAR(30),
  account_id INTEGER NOT NULL REFERENCES organization(org_id),
  initial_start_date TIMESTAMP NOT NULL,
  current_start_date TIMESTAMP,
  current_end_date TIMESTAMP,
  category INTEGER REFERENCES lookup_sc_category(code),
  "type" INTEGER REFERENCES lookup_sc_type(code),
  contact_id INTEGER REFERENCES contact(contact_id),
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  contract_billing_notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  response_time INTEGER REFERENCES lookup_response_model(code),
  telephone_service_model INTEGER REFERENCES lookup_phone_model(code),
  onsite_service_model INTEGER REFERENCES lookup_onsite_model(code),
  email_service_model INTEGER REFERENCES lookup_email_model(code),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  contract_value FLOAT,
  total_hours_remaining FLOAT,
  service_model_notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  initial_start_date_timezone VARCHAR(255),
  current_start_date_timezone VARCHAR(255),
  current_end_date_timezone VARCHAR(255),
  trashed_date TIMESTAMP,
  PRIMARY KEY (CONTRACT_ID)
);

-- Old Name: service_contract_hours_history_id_seq;
CREATE GENERATOR service_contr_rs_history_id_seq;
CREATE TABLE service_contract_hours (
  history_id INTEGER  NOT NULL,
  link_contract_id INTEGER REFERENCES service_contract(contract_id),
  adjustment_hours FLOAT,
  adjustment_reason INTEGER REFERENCES lookup_hours_reason(code),
  adjustment_notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (HISTORY_ID)
);

-- Old Name: service_contract_products_id_seq;
CREATE GENERATOR service_contr_t_products_id_seq;
CREATE TABLE service_contract_products(
  id INTEGER  NOT NULL,
  link_contract_id INTEGER REFERENCES service_contract(contract_id),
  link_product_id INTEGER REFERENCES product_catalog(product_id),
  PRIMARY KEY (ID)
);

CREATE GENERATOR asset_category_id_seq;
CREATE TABLE asset_category (
  id INTEGER NOT NULL,
  cat_level INTEGER DEFAULT 0 NOT NULL ,
  parent_cat_code INTEGER DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description BLOB SUB_TYPE 1 SEGMENT SIZE 100 DEFAULT '' NOT NULL ,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  site_id INTEGER REFERENCES lookup_site_id(code),
  PRIMARY KEY (ID)
);

CREATE GENERATOR asset_category_draft_id_seq;
CREATE TABLE asset_category_draft (
  id INTEGER NOT NULL,
  link_id INTEGER DEFAULT -1,
  cat_level INTEGER DEFAULT 0 NOT NULL ,
  parent_cat_code INTEGER DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description BLOB SUB_TYPE 1 SEGMENT SIZE 100 DEFAULT '' NOT NULL ,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  site_id INTEGER REFERENCES lookup_site_id(code),
  PRIMARY KEY (ID)
);

CREATE GENERATOR asset_asset_id_seq;
CREATE TABLE asset (
  asset_id INTEGER NOT NULL PRIMARY KEY,
  account_id INTEGER REFERENCES organization(org_id),
  contract_id INTEGER REFERENCES service_contract(contract_id),
  date_listed TIMESTAMP,
  asset_tag VARCHAR(30),
  status INTEGER,
  location VARCHAR(256),
  level1 INTEGER REFERENCES asset_category(id),
  level2 INTEGER REFERENCES asset_category(id),
  level3 INTEGER REFERENCES asset_category(id),
  serial_number VARCHAR(30),
  model_version VARCHAR(30),
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  expiration_date TIMESTAMP,
  inclusions BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  exclusions BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  purchase_date TIMESTAMP,
  po_number VARCHAR(30),
  purchased_from VARCHAR(30),
  contact_id INTEGER REFERENCES contact(contact_id),
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  response_time INTEGER REFERENCES lookup_response_model(code),
  telephone_service_model INTEGER REFERENCES lookup_phone_model(code),
  onsite_service_model INTEGER REFERENCES lookup_onsite_model(code),
  email_service_model INTEGER REFERENCES lookup_email_model(code),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id) ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  purchase_cost FLOAT,
  date_listed_timezone VARCHAR(255),
  expiration_date_timezone VARCHAR(255),
  purchase_date_timezone VARCHAR(255),
  trashed_date TIMESTAMP,
  parent_id INTEGER,
  vendor_code INT REFERENCES lookup_asset_vendor(code),
  manufacturer_code INT REFERENCES lookup_asset_manufacturer(code)
);

-- Firebird cannot reference itself (primary key) during table creation
ALTER TABLE asset ADD CONSTRAINT FK_ASSET_PARENT_ID
  FOREIGN KEY (parent_id) REFERENCES asset(asset_id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE GENERATOR lookup_asset_materials_code_seq;
CREATE TABLE lookup_asset_materials(
 code INT NOT NULL PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER,
 enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR asset_materials_map_map_id_seq;
CREATE TABLE asset_materials_map (
  map_id INT NOT NULL PRIMARY KEY,
  asset_id INTEGER NOT NULL REFERENCES asset(asset_id),
  code INTEGER NOT NULL REFERENCES lookup_asset_materials(code),
  quantity FLOAT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
