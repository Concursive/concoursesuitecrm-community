/**
 *  PostgreSQL Table Creation
 *
 *@author     mrajkowski
 *@created    April 18, 2002
 *@version    $Id$
 */
 
CREATE TABLE autoguide_make (
  make_id SERIAL PRIMARY KEY,
  make_name VARCHAR(30),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE autoguide_model (
  model_id SERIAL PRIMARY KEY,
  make_id INTEGER NOT NULL REFERENCES autoguide_make(make_id),
  model_name VARCHAR(50),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE SEQUENCE autoguide_vehicl_vehicle_id_seq;
CREATE TABLE autoguide_vehicle (
  vehicle_id INTEGER DEFAULT nextval('autoguide_vehicl_vehicle_id_seq') NOT NULL PRIMARY KEY,
  year VARCHAR(4) NOT NULL,
  make_id INTEGER NOT NULL REFERENCES autoguide_make(make_id),
  model_id INTEGER NOT NULL REFERENCES autoguide_model(model_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE SEQUENCE autoguide_inve_inventory_id_seq;
CREATE TABLE autoguide_inventory (
  inventory_id INTEGER DEFAULT nextval('autoguide_inve_inventory_id_seq') NOT NULL PRIMARY KEY,
  vehicle_id INTEGER NOT NULL REFERENCES autoguide_vehicle(vehicle_id),
  account_id INTEGER REFERENCES organization(org_id),
  vin VARCHAR(20),
  mileage VARCHAR(20) NULL,
  is_new BOOLEAN DEFAULT false,
  condition VARCHAR(20) NULL,
  comments VARCHAR(255) NULL,
  stock_no VARCHAR(20) NULL,
  ext_color VARCHAR(20) NULL,
  int_color VARCHAR(20) NULL,
  invoice_price FLOAT NULL,
  selling_price FLOAT NULL,
  sold BOOLEAN DEFAULT false,
  status VARCHAR(20) NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE SEQUENCE autoguide_options_option_id_seq;
CREATE TABLE autoguide_options (
  option_id INTEGER DEFAULT nextval('autoguide_options_option_id_seq') NOT NULL PRIMARY KEY,
  option_name VARCHAR(20) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE autoguide_inventory_options (
  inventory_id INTEGER NOT NULL,
  option_id INTEGER NOT NULL
);

CREATE UNIQUE INDEX idx_autog_inv_opt ON autoguide_inventory_options (inventory_id, option_id);

CREATE TABLE autoguide_ad_run (
  ad_run_id SERIAL PRIMARY KEY,
  inventory_id INTEGER NOT NULL,
  run_date TIMESTAMP(3) NOT NULL,
  ad_type VARCHAR(20) NULL,
  include_photo BOOLEAN DEFAULT false,
  complete_date TIMESTAMP(3) NULL,
  completedby INT DEFAULT -1,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE SEQUENCE autoguide_ad_run_types_code_seq;
CREATE TABLE autoguide_ad_run_types (
  code INTEGER DEFAULT nextval('autoguide_ad_run_types_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(20) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT false,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);
