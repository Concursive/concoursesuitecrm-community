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
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE autoguide_model (
  model_id SERIAL PRIMARY KEY,
  make_id INTEGER NOT NULL,
  model_name VARCHAR(50),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE autoguide_vehicle (
  vehicle_id SERIAL PRIMARY KEY,
  year VARCHAR(4) NOT NULL,
  make_id INTEGER NOT NULL,
  model_id INTEGER NOT NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE autoguide_account_inventory (
  inventory_id SERIAL PRIMARY KEY,
  vehicle_id INTEGER NOT NULL,
  account_id INTEGER,
  vin VARCHAR(20),
  adtype VARCHAR(20) NULL,
  mileage VARCHAR(20) NULL,
  is_new BOOLEAN DEFAULT false,
  condition VARCHAR(20) NULL,
  comments VARCHAR(255) NULL,
  stock_no VARCHAR(20) NULL,
  ext_color VARCHAR(20) NULL,
  int_color VARCHAR(20) NULL,
  invoice_price FLOAT NULL,
  selling_price FLOAT NULL,
  status VARCHAR(20) NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE autoguide_options (
  option_id SERIAL PRIMARY KEY,
  option_name VARCHAR(20) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE autoguide_inventory_options (
  inventory_id INTEGER NOT NULL,
  option_id INTEGER NOT NULL
);

CREATE TABLE autoguide_ad_run (
  ad_run_id SERIAL PRIMARY KEY,
  inventory_id INTEGER NOT NULL,
  start_date DATETIME NOT NULL,
  end_date DATETIME NOT NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);
