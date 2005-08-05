
CREATE SEQUENCE autoguide_make_make_id_seq;
CREATE TABLE autoguide_make (
  make_id INT PRIMARY KEY,
  make_name VARCHAR(30),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL
);

CREATE SEQUENCE autoguide_model_model_id_seq;
CREATE TABLE autoguide_model (
  model_id INT PRIMARY KEY,
  make_id INTEGER REFERENCES autoguide_make(make_id) NOT NULL,
  model_name VARCHAR(50),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL
);

CREATE SEQUENCE autoguide_vehicl_vehicle_id_seq;
CREATE TABLE autoguide_vehicle (
  vehicle_id INTEGER PRIMARY KEY,
  year VARCHAR(4) NOT NULL,
  make_id INTEGER REFERENCES autoguide_make(make_id) NOT NULL,
  model_id INTEGER REFERENCES autoguide_model(model_id) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL
);

CREATE SEQUENCE autoguide_inve_inventory_id_seq;
CREATE TABLE autoguide_inventory (
  inventory_id INTEGER PRIMARY KEY,
  vehicle_id INTEGER REFERENCES autoguide_vehicle(vehicle_id) NOT NULL,
  account_id INTEGER REFERENCES organization(org_id),
  vin VARCHAR(20),
  mileage VARCHAR(20),
  is_new BOOLEAN DEFAULT false,
  "condition" VARCHAR(20),
  comments VARCHAR(255),
  stock_no VARCHAR(20),
  ext_color VARCHAR(20),
  int_color VARCHAR(20),
	style VARCHAR(40),
  invoice_price FLOAT,
  selling_price FLOAT,
	selling_price_text VARCHAR(100),
  sold BOOLEAN DEFAULT false,
  status VARCHAR(20),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL
);

CREATE SEQUENCE autoguide_options_option_id_seq;
CREATE TABLE autoguide_options (
  option_id INTEGER PRIMARY KEY,
  option_name VARCHAR(20) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE autoguide_inventory_options (
  inventory_id INTEGER REFERENCES autoguide_inventory(inventory_id) NOT NULL,
  option_id INTEGER NOT NULL
);

CREATE INDEX idx_autog_inv_opt ON autoguide_inventory_options (inventory_id, option_id);

CREATE TABLE autoguide_ad_run (
  ad_run_id INT PRIMARY KEY,
  inventory_id INTEGER REFERENCES autoguide_inventory(inventory_id) NOT NULL,
  run_date TIMESTAMP(3) NOT NULL,
  ad_type VARCHAR(20),
  include_photo BOOLEAN DEFAULT false,
  complete_date TIMESTAMP(3),
  completedby INT DEFAULT -1,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL
);

CREATE SEQUENCE autoguide_ad_run_types_code_seq;
CREATE TABLE autoguide_ad_run_types (
  code INTEGER PRIMARY KEY,
  description VARCHAR(20) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT false,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
