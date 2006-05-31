
CREATE SEQUENCE autoguide_make_make_id_seq;
CREATE TABLE autoguide_make (
  make_id INTEGER NOT NULL,
  make_name NVARCHAR2(30),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (make_id)
);

CREATE SEQUENCE autoguide_model_model_id_seq;
CREATE TABLE autoguide_model (
  model_id INTEGER NOT NULL,
  make_id INTEGER NOT NULL REFERENCES autoguide_make(make_id),
  model_name NVARCHAR2(50),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (model_id)
);

CREATE SEQUENCE autoguide_veh_l_vehicle_id_seq;
CREATE TABLE autoguide_vehicle (
  vehicle_id INTEGER NOT NULL,
  "year" NVARCHAR2(4) NOT NULL,
  make_id INTEGER NOT NULL REFERENCES autoguide_make(make_id),
  model_id INTEGER NOT NULL REFERENCES autoguide_model(model_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (vehicle_id)
);

CREATE SEQUENCE autoguide_inv_inventory_id_seq;
CREATE TABLE autoguide_inventory (
  inventory_id INTEGER NOT NULL,
  vehicle_id INTEGER NOT NULL REFERENCES autoguide_vehicle(vehicle_id),
  account_id INTEGER REFERENCES organization(org_id),
  vin NVARCHAR2(20),
  mileage NVARCHAR2(20),
  is_new CHAR(1) DEFAULT 0,
  condition NVARCHAR2(20),
  comments NVARCHAR2(255),
  stock_no NVARCHAR2(20),
  ext_color NVARCHAR2(20),
  int_color NVARCHAR2(20),
  style NVARCHAR2(40),
  invoice_price FLOAT,
  selling_price FLOAT,
  selling_price_text NVARCHAR2(100),
  sold CHAR(1) DEFAULT 0,
  status NVARCHAR2(20),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (inventory_id)
);

CREATE SEQUENCE autoguide_opt_ns_option_id_seq;
CREATE TABLE autoguide_options (
  option_id INTEGER NOT NULL,
  option_name NVARCHAR2(20) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (option_id)
);

CREATE TABLE autoguide_inventory_options (
  inventory_id INTEGER NOT NULL REFERENCES autoguide_inventory(inventory_id),
  option_id INTEGER NOT NULL
);

CREATE UNIQUE INDEX idx_autog_inv_opt ON autoguide_inventory_options (inventory_id, option_id);

CREATE SEQUENCE autoguide_ad_run_ad_run_id_seq;
CREATE TABLE autoguide_ad_run (
  ad_run_id INTEGER NOT NULL,
  inventory_id INTEGER NOT NULL REFERENCES autoguide_inventory(inventory_id),
  run_date TIMESTAMP NOT NULL,
  ad_type NVARCHAR2(20),
  include_photo CHAR(1) DEFAULT 0,
  complete_date TIMESTAMP,
  completedby INT DEFAULT -1,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  PRIMARY KEY (ad_run_id)
);

CREATE SEQUENCE autoguide_ad__n_types_code_seq;
CREATE TABLE autoguide_ad_run_types (
  code INTEGER NOT NULL,
  description NVARCHAR2(20) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 0,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (code)
);
