
CREATE GENERATOR autoguide_make_make_id_seq;
CREATE TABLE autoguide_make (
  make_id INTEGER NOT NULL,
  make_name VARCHAR(30),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (make_id)
);

CREATE GENERATOR autoguide_model_model_id_seq;
CREATE TABLE autoguide_model (
  model_id INTEGER NOT NULL,
  make_id INTEGER NOT NULL REFERENCES autoguide_make(make_id),
  model_name VARCHAR(50),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (model_id)
);

CREATE GENERATOR autoguide_vehicl_vehicle_id_seq;
CREATE TABLE autoguide_vehicle (
  vehicle_id INTEGER NOT NULL,
  "year" VARCHAR(4) NOT NULL,
  make_id INTEGER NOT NULL REFERENCES autoguide_make(make_id),
  model_id INTEGER NOT NULL REFERENCES autoguide_model(model_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (vehicle_id)
);

CREATE GENERATOR autoguide_inve_inventory_id_seq;
CREATE TABLE autoguide_inventory (
  inventory_id INTEGER NOT NULL,
  vehicle_id INTEGER NOT NULL REFERENCES autoguide_vehicle(vehicle_id),
  account_id INTEGER REFERENCES organization(org_id),
  vin VARCHAR(20),
  mileage VARCHAR(20),
  is_new BOOLEAN DEFAULT FALSE,
  condition VARCHAR(20),
  comments VARCHAR(255),
  stock_no VARCHAR(20),
  ext_color VARCHAR(20),
  int_color VARCHAR(20),
  style VARCHAR(40),
  invoice_price FLOAT,
  selling_price FLOAT,
  selling_price_text VARCHAR(100),
  sold BOOLEAN DEFAULT FALSE,
  status VARCHAR(20),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (inventory_id)
);

CREATE GENERATOR autoguide_options_option_id_seq;
CREATE TABLE autoguide_options (
  option_id INTEGER NOT NULL,
  option_name VARCHAR(20) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (option_id)
);

CREATE TABLE autoguide_inventory_options (
  inventory_id INTEGER NOT NULL REFERENCES autoguide_inventory(inventory_id),
  option_id INTEGER NOT NULL
);

CREATE UNIQUE INDEX idx_autog_inv_opt ON autoguide_inventory_options (inventory_id, option_id);

CREATE GENERATOR autoguide_ad_run_ad_run_id_seq;
CREATE TABLE autoguide_ad_run (
  ad_run_id INTEGER NOT NULL,
  inventory_id INTEGER NOT NULL REFERENCES autoguide_inventory(inventory_id),
  run_date TIMESTAMP NOT NULL,
  ad_type VARCHAR(20),
  include_photo BOOLEAN DEFAULT FALSE,
  complete_date TIMESTAMP,
  completedby INT DEFAULT -1,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (ad_run_id)
);

CREATE GENERATOR autoguide_ad_run_types_code_seq;
CREATE TABLE autoguide_ad_run_types (
  code INTEGER NOT NULL,
  description VARCHAR(20) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT FALSE,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (code)
);

CREATE GENERATOR autoguide_make_make_id_seq;
CREATE TABLE autoguide_make (
  make_id INTEGER NOT NULL,
  make_name VARCHAR(30),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (make_id)
);

CREATE GENERATOR autoguide_model_model_id_seq;
CREATE TABLE autoguide_model (
  model_id INTEGER NOT NULL,
  make_id INTEGER NOT NULL REFERENCES autoguide_make(make_id),
  model_name VARCHAR(50),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (model_id)
);

CREATE GENERATOR autoguide_vehicl_vehicle_id_seq;
CREATE TABLE autoguide_vehicle (
  vehicle_id INTEGER NOT NULL,
  "year" VARCHAR(4) NOT NULL,
  make_id INTEGER NOT NULL REFERENCES autoguide_make(make_id),
  model_id INTEGER NOT NULL REFERENCES autoguide_model(model_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (vehicle_id)
);

CREATE GENERATOR autoguide_inve_inventory_id_seq;
CREATE TABLE autoguide_inventory (
  inventory_id INTEGER NOT NULL,
  vehicle_id INTEGER NOT NULL REFERENCES autoguide_vehicle(vehicle_id),
  account_id INTEGER REFERENCES organization(org_id),
  vin VARCHAR(20),
  mileage VARCHAR(20),
  is_new BOOLEAN DEFAULT FALSE,
  condition VARCHAR(20),
  comments VARCHAR(255),
  stock_no VARCHAR(20),
  ext_color VARCHAR(20),
  int_color VARCHAR(20),
  style VARCHAR(40),
  invoice_price FLOAT,
  selling_price FLOAT,
  selling_price_text VARCHAR(100),
  sold BOOLEAN DEFAULT FALSE,
  status VARCHAR(20),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (inventory_id)
);

CREATE GENERATOR autoguide_options_option_id_seq;
CREATE TABLE autoguide_options (
  option_id INTEGER NOT NULL,
  option_name VARCHAR(20) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (option_id)
);

CREATE TABLE autoguide_inventory_options (
  inventory_id INTEGER NOT NULL REFERENCES autoguide_inventory(inventory_id),
  option_id INTEGER NOT NULL
);

CREATE UNIQUE INDEX idx_autog_inv_opt ON autoguide_inventory_options (inventory_id, option_id);

CREATE GENERATOR autoguide_ad_run_ad_run_id_seq;
CREATE TABLE autoguide_ad_run (
  ad_run_id INTEGER NOT NULL,
  inventory_id INTEGER NOT NULL REFERENCES autoguide_inventory(inventory_id),
  run_date TIMESTAMP NOT NULL,
  ad_type VARCHAR(20),
  include_photo BOOLEAN DEFAULT FALSE,
  complete_date TIMESTAMP,
  completedby INT DEFAULT -1,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (ad_run_id)
);

CREATE GENERATOR autoguide_ad_run_types_code_seq;
CREATE TABLE autoguide_ad_run_types (
  code INTEGER NOT NULL,
  description VARCHAR(20) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT FALSE,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (code)
);
