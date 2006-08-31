
CREATE SEQUENCE module_field_categorylin_id_seq;
CREATE TABLE module_field_categorylink (
  id INTEGER PRIMARY KEY,
  module_id INTEGER REFERENCES permission_category(category_id) NOT NULL,
  category_id INT UNIQUE ,
  "level" INTEGER DEFAULT 0,
  description CLOB,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE SEQUENCE custom_field_ca_category_id_seq;
CREATE TABLE custom_field_category (
  module_id INTEGER REFERENCES module_field_categorylink(category_id) NOT NULL,
  category_id INTEGER PRIMARY KEY,
  category_name VARCHAR(255) NOT NULL,
  "level" INTEGER DEFAULT 0,
  description CLOB,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP,
  default_item boolean DEFAULT false,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled boolean DEFAULT true,
  multiple_records boolean DEFAULT false,
  read_only boolean DEFAULT false
);

CREATE INDEX "custom_field_cat_idx" ON "custom_field_category" ("module_id");

CREATE SEQUENCE custom_field_group_group_id_seq;
CREATE TABLE custom_field_group (
  category_id INTEGER REFERENCES custom_field_category(category_id) NOT NULL,
  group_id INTEGER PRIMARY KEY,
  group_name VARCHAR(255) NOT NULL,
  "level" INTEGER DEFAULT 0,
  description CLOB,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled boolean DEFAULT true
);

CREATE INDEX "custom_field_grp_idx" ON "custom_field_group" ("category_id");


CREATE SEQUENCE custom_field_info_field_id_seq;
CREATE TABLE custom_field_info (
  group_id INTEGER REFERENCES custom_field_group(group_id) NOT NULL,
  field_id INT  PRIMARY KEY,
  field_name VARCHAR(255) NOT NULL,
  "level" INTEGER DEFAULT 0,
  field_type INTEGER NOT NULL,
  validation_type INTEGER DEFAULT 0,
  required boolean DEFAULT false,
  parameters CLOB,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP DEFAULT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled boolean DEFAULT true,
  additional_text VARCHAR(255)
);

CREATE INDEX "custom_field_inf_idx" ON "custom_field_info" ("group_id");

CREATE SEQUENCE custom_field_lookup_code_seq;
CREATE TABLE custom_field_lookup (
  field_id INTEGER  REFERENCES custom_field_info(field_id) NOT NULL,
  code INT PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled boolean DEFAULT true
);


CREATE SEQUENCE custom_field_reco_record_id_seq;
CREATE TABLE custom_field_record (
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  category_id INTEGER NOT NULL REFERENCES custom_field_category(category_id),
  record_id INT  PRIMARY KEY,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT  REFERENCES access(user_id) NOT NULL,
  enabled boolean DEFAULT true
);
CREATE INDEX "custom_field_rec_idx" ON "custom_field_record" ("link_module_id", "link_item_id", "category_id");


CREATE TABLE custom_field_data (
  record_id INTEGER  REFERENCES custom_field_record(record_id) NOT NULL,
  field_id INTEGER REFERENCES custom_field_info(field_id) NOT NULL,
  selected_item_id INTEGER DEFAULT 0,
  entered_value CLOB,
  entered_number INTEGER,
  entered_float FLOAT
);

CREATE INDEX "custom_field_dat_idx" ON "custom_field_data" ("record_id", "field_id");
