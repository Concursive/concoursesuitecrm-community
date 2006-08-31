
CREATE SEQUENCE lookup_asset_status_code_seq;
CREATE TABLE lookup_asset_status(
 code INTEGER NOT NULL,
 description NVARCHAR2(300),
 default_item CHAR(1) DEFAULT 0,
 "level" INTEGER,
 enabled CHAR(1) DEFAULT 1,
 PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_sc_category_code_seq;
CREATE TABLE lookup_sc_category(
 code INTEGER NOT NULL,
 description NVARCHAR2(300),
 default_item CHAR(1) DEFAULT 0,
 "level" INTEGER,
 enabled CHAR(1) DEFAULT 1,
 PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_sc_type_code_seq;
CREATE TABLE lookup_sc_type(
 code INTEGER NOT NULL,
 description NVARCHAR2(300),
 default_item CHAR(1) DEFAULT 0,
 "level" INTEGER,
 enabled CHAR(1) DEFAULT 1,
 PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_response_model_code_seq;
CREATE TABLE lookup_response_model(
 code INTEGER NOT NULL,
 description NVARCHAR2(300),
 default_item CHAR(1) DEFAULT 0,
 "level" INTEGER,
 enabled CHAR(1) DEFAULT 1,
 PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_phone_model_code_seq;
CREATE TABLE lookup_phone_model(
 code INTEGER NOT NULL,
 description NVARCHAR2(300),
 default_item CHAR(1) DEFAULT 0,
 "level" INTEGER,
 enabled CHAR(1) DEFAULT 1,
 PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_onsite_model_code_seq;
CREATE TABLE lookup_onsite_model(
 code INTEGER NOT NULL,
 description NVARCHAR2(300),
 default_item CHAR(1) DEFAULT 0,
 "level" INTEGER,
 enabled CHAR(1) DEFAULT 1,
 PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_email_model_code_seq;
CREATE TABLE lookup_email_model(
 code INTEGER NOT NULL,
 description NVARCHAR2(300),
 default_item CHAR(1) DEFAULT 0,
 "level" INTEGER,
 enabled CHAR(1) DEFAULT 1,
 PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_hours_reason_code_seq;
CREATE TABLE lookup_hours_reason(
 code INTEGER NOT NULL,
 description NVARCHAR2(300),
 default_item CHAR(1) DEFAULT 0,
 "level" INTEGER,
 enabled CHAR(1) DEFAULT 1,
 PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_asset__nufactu_code_seq;
CREATE TABLE lookup_asset_manufacturer(
 code INTEGER NOT NULL PRIMARY KEY,
 description NVARCHAR2(300),
 default_item CHAR(1) DEFAULT 0,
 "level" INTEGER,
 enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE lookup_asset_vendor_code_seq;
CREATE TABLE lookup_asset_vendor(
 code INT NOT NULL PRIMARY KEY,
 description NVARCHAR2(300),
 default_item CHAR(1) DEFAULT 0,
 "level" INTEGER,
 enabled CHAR(1) DEFAULT 1
);

-- Old Name: service_contract_contract_id_seq;
CREATE SEQUENCE service_contr__contract_id_seq;
CREATE TABLE service_contract (
  contract_id INTEGER  NOT NULL,
  contract_number NVARCHAR2(30),
  account_id INTEGER NOT NULL REFERENCES organization(org_id),
  initial_start_date TIMESTAMP NOT NULL,
  current_start_date TIMESTAMP,
  current_end_date TIMESTAMP,
  category INTEGER REFERENCES lookup_sc_category(code),
  "type" INTEGER REFERENCES lookup_sc_type(code),
  contact_id INTEGER REFERENCES contact(contact_id),
  description CLOB,
  contract_billing_notes CLOB,
  response_time INTEGER REFERENCES lookup_response_model(code),
  telephone_service_model INTEGER REFERENCES lookup_phone_model(code),
  onsite_service_model INTEGER REFERENCES lookup_onsite_model(code),
  email_service_model INTEGER REFERENCES lookup_email_model(code),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 1,
  contract_value FLOAT,
  total_hours_remaining FLOAT,
  service_model_notes CLOB,
  initial_start_date_timezone NVARCHAR2(255),
  current_start_date_timezone NVARCHAR2(255),
  current_end_date_timezone NVARCHAR2(255),
  trashed_date TIMESTAMP,
  PRIMARY KEY (CONTRACT_ID)
);

-- Old Name: service_contract_hours_history_id_seq;
CREATE SEQUENCE service_contr_s_history_id_seq;
CREATE TABLE service_contract_hours (
  history_id INTEGER  NOT NULL,
  link_contract_id INTEGER REFERENCES service_contract(contract_id),
  adjustment_hours FLOAT,
  adjustment_reason INTEGER REFERENCES lookup_hours_reason(code),
  adjustment_notes CLOB,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (HISTORY_ID)
);

-- Old Name: service_contract_products_id_seq;
CREATE SEQUENCE service_contr__products_id_seq;
CREATE TABLE service_contract_products(
  id INTEGER  NOT NULL,
  link_contract_id INTEGER REFERENCES service_contract(contract_id),
  link_product_id INTEGER REFERENCES product_catalog(product_id),
  PRIMARY KEY (ID)
);

CREATE SEQUENCE asset_category_id_seq;
CREATE TABLE asset_category (
  id INTEGER NOT NULL,
  cat_level INTEGER DEFAULT 0 NOT NULL ,
  parent_cat_code INTEGER DEFAULT 0 NOT NULL,
  description NVARCHAR2(300) NOT NULL,
  full_description CLOB DEFAULT '',
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  site_id INTEGER REFERENCES lookup_site_id(code),
  PRIMARY KEY (ID)
);

CREATE SEQUENCE asset_category_draft_id_seq;
CREATE TABLE asset_category_draft (
  id INTEGER NOT NULL,
  link_id INTEGER DEFAULT -1,
  cat_level INTEGER DEFAULT 0 NOT NULL ,
  parent_cat_code INTEGER DEFAULT 0 NOT NULL,
  description NVARCHAR2(300) NOT NULL,
  full_description CLOB DEFAULT '',
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  site_id INTEGER REFERENCES lookup_site_id(code),
  PRIMARY KEY (ID)
);

CREATE SEQUENCE asset_asset_id_seq;
CREATE TABLE asset (
  asset_id INTEGER NOT NULL PRIMARY KEY,
  account_id INTEGER REFERENCES organization(org_id),
  contract_id INTEGER REFERENCES service_contract(contract_id),
  date_listed TIMESTAMP,
  asset_tag NVARCHAR2(30),
  status INTEGER,
  location NVARCHAR2(256),
  level1 INTEGER REFERENCES asset_category(id),
  level2 INTEGER REFERENCES asset_category(id),
  level3 INTEGER REFERENCES asset_category(id),
  serial_number NVARCHAR2(30),
  model_version NVARCHAR2(30),
  description CLOB,
  expiration_date TIMESTAMP,
  inclusions CLOB,
  exclusions CLOB,
  purchase_date TIMESTAMP,
  po_number NVARCHAR2(30),
  purchased_from NVARCHAR2(30),
  contact_id INTEGER REFERENCES contact(contact_id),
  notes CLOB,
  response_time INTEGER REFERENCES lookup_response_model(code),
  telephone_service_model INTEGER REFERENCES lookup_phone_model(code),
  onsite_service_model INTEGER REFERENCES lookup_onsite_model(code),
  email_service_model INTEGER REFERENCES lookup_email_model(code),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id) ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 1,
  purchase_cost FLOAT,
  date_listed_timezone NVARCHAR2(255),
  expiration_date_timezone NVARCHAR2(255),
  purchase_date_timezone NVARCHAR2(255),
  trashed_date TIMESTAMP,
  parent_id INTEGER REFERENCES asset(asset_id),
  vendor_code INT REFERENCES lookup_asset_vendor(code),
  manufacturer_code INT REFERENCES lookup_asset_manufacturer(code)
);

CREATE SEQUENCE lookup_asset__terials_code_seq;
CREATE TABLE lookup_asset_materials(
 code INT NOT NULL PRIMARY KEY,
 description NVARCHAR2(300),
 default_item CHAR(1) DEFAULT 0,
 "level" INTEGER,
 enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE asset_materials_map_map_id_seq;
CREATE TABLE asset_materials_map (
  map_id INT NOT NULL PRIMARY KEY,
  asset_id INTEGER NOT NULL REFERENCES asset(asset_id),
  code INTEGER NOT NULL REFERENCES lookup_asset_materials(code),
  quantity FLOAT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

