
CREATE GENERATOR module_field_categorylin_id_seq;
CREATE TABLE module_field_categorylink (
  id INTEGER NOT NULL,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  category_id INTEGER NOT NULL UNIQUE ,
  "level" INTEGER DEFAULT 0,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID, MODULE_ID, CATEGORY_ID)
);


CREATE GENERATOR custom_field_ca_category_id_seq;
CREATE TABLE custom_field_category (
  module_id INTEGER NOT NULL REFERENCES module_field_categorylink(category_id),
  category_id INTEGER  NOT NULL UNIQUE,
  category_name VARCHAR(255) NOT NULL,
  "level" INTEGER DEFAULT 0,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP,
  default_item CHAR(1) DEFAULT 'N',
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled CHAR(1) DEFAULT 'Y',
  multiple_records CHAR(1) DEFAULT 'N',
  read_only CHAR(1) DEFAULT 'N',
  PRIMARY KEY (MODULE_ID, CATEGORY_ID)
);

CREATE INDEX custom_field_cat_idx ON custom_field_category (module_id);

CREATE GENERATOR custom_field_group_group_id_seq;
CREATE TABLE custom_field_group (
  category_id INTEGER NOT NULL REFERENCES custom_field_category(category_id),
  group_id INTEGER  NOT NULL UNIQUE,
  group_name VARCHAR(255) NOT NULL,
  "level" INTEGER DEFAULT 0,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CATEGORY_ID, GROUP_ID)
);

CREATE INDEX custom_field_grp_idx ON custom_field_group (category_id);


CREATE GENERATOR custom_field_info_field_id_seq;
CREATE TABLE custom_field_info (
  group_id INTEGER NOT NULL REFERENCES custom_field_group(group_id),
  field_id INTEGER  NOT NULL UNIQUE,
  field_name VARCHAR(255) NOT NULL,
  "level" INTEGER DEFAULT 0,
  field_type INTEGER NOT NULL,
  validation_type INTEGER DEFAULT 0,
  required CHAR(1) DEFAULT 'N',
  parameters BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP DEFAULT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled CHAR(1) DEFAULT 'Y',
  additional_text VARCHAR(255),
  PRIMARY KEY (GROUP_ID, FIELD_ID)
);

CREATE INDEX custom_field_inf_idx ON custom_field_info (group_id);

CREATE GENERATOR custom_field_lookup_code_seq;
CREATE TABLE custom_field_lookup (
  field_id INTEGER NOT NULL REFERENCES custom_field_info(field_id),
  code INTEGER NOT NULL,
  description VARCHAR(255) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (FIELD_ID, CODE)
);


CREATE GENERATOR custom_field_reco_record_id_seq;
CREATE TABLE custom_field_record (
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  category_id INTEGER NOT NULL REFERENCES custom_field_category(category_id),
  record_id INTEGER  NOT NULL UNIQUE,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (LINK_MODULE_ID, LINK_ITEM_ID, CATEGORY_ID, RECORD_ID)
);
CREATE INDEX custom_field_rec_idx ON custom_field_record (link_module_id, link_item_id, category_id);


CREATE TABLE custom_field_data (
  record_id INTEGER NOT NULL REFERENCES custom_field_record(record_id),
  field_id INTEGER NOT NULL REFERENCES custom_field_info(field_id),
  selected_item_id INTEGER DEFAULT 0,
  entered_value BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  entered_number INTEGER,
  entered_float FLOAT
);

CREATE INDEX custom_field_dat_idx ON custom_field_data (record_id, field_id);