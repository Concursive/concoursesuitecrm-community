/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    May 14, 2002
 *@version    $Id$
 */

CREATE TABLE autoguide_make (
  make_id INT IDENTITY PRIMARY KEY,
  make_name VARCHAR(30),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE autoguide_model (
  model_id INT IDENTITY PRIMARY KEY,
  make_id INTEGER NOT NULL REFERENCES autoguide_make(make_id),
  model_name VARCHAR(50),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE autoguide_vehicle (
  vehicle_id INT IDENTITY PRIMARY KEY,
  year VARCHAR(4) NOT NULL,
  make_id INTEGER NOT NULL REFERENCES autoguide_make(make_id),
  model_id INTEGER NOT NULL REFERENCES autoguide_model(model_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE autoguide_inventory (
  inventory_id INT IDENTITY PRIMARY KEY,
  vehicle_id INTEGER NOT NULL REFERENCES autoguide_vehicle(vehicle_id),
  account_id INTEGER REFERENCES organization(org_id),
  vin VARCHAR(20),
  mileage VARCHAR(20) NULL,
  is_new BIT DEFAULT 0,
  condition VARCHAR(20) NULL,
  comments VARCHAR(255) NULL,
  stock_no VARCHAR(20) NULL,
  ext_color VARCHAR(20) NULL,
  int_color VARCHAR(20) NULL,
  invoice_price FLOAT NULL,
  selling_price FLOAT NULL,
  sold BIT DEFAULT 0,
  status VARCHAR(20) NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE autoguide_options (
  option_id INT IDENTITY PRIMARY KEY,
  option_name VARCHAR(20) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 0,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE autoguide_inventory_options (
  inventory_id INTEGER NOT NULL REFERENCES autoguide_inventory(inventory_id),
  option_id INTEGER NOT NULL
);

CREATE UNIQUE INDEX idx_autog_inv_opt ON autoguide_inventory_options (inventory_id, option_id);

CREATE TABLE autoguide_ad_run (
  ad_run_id INT IDENTITY PRIMARY KEY,
  inventory_id INTEGER NOT NULL REFERENCES autoguide_inventory(inventory_id),
  run_date DATETIME NOT NULL,
  ad_type VARCHAR(20) NULL,
  include_photo BIT DEFAULT 0,
  complete_date DATETIME NULL,
  completedby INT DEFAULT -1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE autoguide_ad_run_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(20) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 0,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
