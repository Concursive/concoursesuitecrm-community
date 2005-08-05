
CREATE SEQUENCE lookup_asset_status_code_seq;
CREATE TABLE lookup_asset_status(
 code INT PRIMARY KEY,
 description VARCHAR(300),
 default_item boolean DEFAULT false,
 "level" INTEGER,
 enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_sc_category_code_seq;
CREATE TABLE lookup_sc_category(
 code INT PRIMARY KEY,
 description VARCHAR(300),
 default_item boolean DEFAULT false,
 "level" INTEGER,
 enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_sc_type_code_seq;
CREATE TABLE lookup_sc_type(
 code INT PRIMARY KEY,
 description VARCHAR(300),
 default_item boolean DEFAULT false,
 "level" INTEGER,
 enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_response_model_code_seq;
CREATE TABLE lookup_response_model(
 code INT PRIMARY KEY,
 description VARCHAR(300),
 default_item boolean DEFAULT false,
 "level" INTEGER,
 enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_phone_model_code_seq;
CREATE TABLE lookup_phone_model(
 code INT PRIMARY KEY,
 description VARCHAR(300),
 default_item boolean DEFAULT false,
 "level" INTEGER,
 enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_onsite_model_code_seq;
CREATE TABLE lookup_onsite_model(
 code INT PRIMARY KEY,
 description VARCHAR(300),
 default_item boolean DEFAULT false,
 "level" INTEGER,
 enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_email_model_code_seq;
CREATE TABLE lookup_email_model(
 code INT PRIMARY KEY,
 description VARCHAR(300),
 default_item boolean DEFAULT false,
 "level" INTEGER,
 enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_hours_reason_code_seq;
CREATE TABLE lookup_hours_reason(
 code INT PRIMARY KEY,
 description VARCHAR(300),
 default_item boolean DEFAULT false,
 "level" INTEGER,
 enabled boolean DEFAULT true
);

CREATE SEQUENCE service_contract_contract_id_seq;
CREATE TABLE service_contract (
  contract_id INT  PRIMARY KEY,
  contract_number VARCHAR(30),
  account_id INT REFERENCES organization(org_id) NOT NULL,
  initial_start_date TIMESTAMP NOT NULL,
  current_start_date TIMESTAMP,
  current_end_date TIMESTAMP,
  category INT REFERENCES lookup_sc_category(code),
  type INT REFERENCES lookup_sc_type(code),
  contact_id INT REFERENCES contact(contact_id),
  description CLOB,
  contract_billing_notes CLOB,
  response_time INT REFERENCES lookup_response_model(code),
  telephone_service_model INT REFERENCES lookup_phone_model(code),
  onsite_service_model INT REFERENCES lookup_onsite_model(code),
  email_service_model INT REFERENCES lookup_email_model(code),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT  REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT REFERENCES access(user_id) NOT NULL ,
  enabled boolean DEFAULT true,
  contract_value FLOAT,
  total_hours_remaining FLOAT,
  service_model_notes CLOB,
  initial_start_date_timezone VARCHAR(255),
  current_start_date_timezone VARCHAR(255),
  current_end_date_timezone VARCHAR(255),
  trashed_date TIMESTAMP
);

CREATE SEQUENCE service_contract_hours_history_id_seq;
CREATE TABLE service_contract_hours (
  history_id INT  PRIMARY KEY,
  link_contract_id INT REFERENCES service_contract(contract_id),
  adjustment_hours FLOAT,
  adjustment_reason INT REFERENCES lookup_hours_reason(code),
  adjustment_notes CLOB,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT REFERENCES access(user_id) NOT NULL 
); 

CREATE SEQUENCE service_contract_products_id_seq;
CREATE TABLE service_contract_products(
  id INT  PRIMARY KEY,
  link_contract_id INT REFERENCES service_contract(contract_id),
  link_product_id INT REFERENCES product_catalog(product_id)
);

CREATE SEQUENCE asset_category_id_seq;
CREATE TABLE asset_category (
  id INT PRIMARY KEY,
  cat_level int DEFAULT 0 NOT NULL ,
  parent_cat_code int DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description CLOB DEFAULT '' NOT NULL ,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE asset_category_draft_id_seq;
CREATE TABLE asset_category_draft (
  id INT PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int DEFAULT 0 NOT NULL ,
  parent_cat_code int DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description CLOB DEFAULT '' NOT NULL ,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE asset_asset_id_seq;
CREATE TABLE asset (
  asset_id INT  PRIMARY KEY,
  account_id INT REFERENCES organization(org_id),
  contract_id INT REFERENCES service_contract(contract_id),
  date_listed TIMESTAMP,
  asset_tag VARCHAR(30),
  status INT,
  location VARCHAR(256),
  level1 INT REFERENCES asset_category(id),
  level2 INT REFERENCES asset_category(id),
  level3 INT REFERENCES asset_category(id),
  vendor VARCHAR(30),
  manufacturer VARCHAR(30),
  serial_number VARCHAR(30),
  model_version VARCHAR(30),
  description CLOB,
  expiration_date TIMESTAMP,
  inclusions CLOB,
  exclusions CLOB,
  purchase_date TIMESTAMP,
  po_number VARCHAR(30),
  purchased_from VARCHAR(30),
  contact_id INT REFERENCES contact(contact_id),
  notes CLOB,
  response_time INT REFERENCES lookup_response_model(code),
  telephone_service_model INT REFERENCES lookup_phone_model(code),
  onsite_service_model INT REFERENCES lookup_onsite_model(code),
  email_service_model INT REFERENCES lookup_email_model(code),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT REFERENCES access(user_id) ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT REFERENCES access(user_id) NOT NULL ,
  enabled boolean DEFAULT true,
  purchase_cost FLOAT,
  date_listed_timezone VARCHAR(255),
  expiration_date_timezone VARCHAR(255),
  purchase_date_timezone VARCHAR(255),
  trashed_date TIMESTAMP
);
