/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 19, 2002
 *@version    $Id$
 */
 
/* Each module can have multiple categories or folders of custom data */
CREATE TABLE custom_field_category (
  module_id INTEGER NOT NULL,
  category_id INT IDENTITY,
  category_name VARCHAR(255) NOT NULL,
  level INTEGER DEFAULT 0,
  description TEXT,
  start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME,
  default_item BIT DEFAULT 0,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT DEFAULT 1,
  multiple_records BIT DEFAULT 0,
  read_only BIT DEFAULT 0
);

CREATE INDEX "custom_field_cat_idx" ON "custom_field_category" ("module_id");

/* Each category can have multiple groups of fields */
CREATE TABLE custom_field_group (
  category_id INTEGER NOT NULL,
  group_id INT IDENTITY,
  group_name VARCHAR(255) NOT NULL,
  level INTEGER DEFAULT 0,
  description TEXT,
  start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT DEFAULT 1
);

CREATE INDEX "custom_field_grp_idx" ON "custom_field_group" ("category_id");


/* Each folder has defined custom fields */
CREATE TABLE custom_field_info (
  group_id INTEGER NOT NULL,
  field_id INT IDENTITY,
  field_name VARCHAR(255) NOT NULL,
  level INTEGER DEFAULT 0,
  field_type INTEGER NOT NULL,
  validation_type INTEGER DEFAULT 0,
  required BIT DEFAULT 0,
  parameters TEXT,
  start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME DEFAULT NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT DEFAULT 1,
  additional_text VARCHAR(255)
);

CREATE INDEX "custom_field_inf_idx" ON "custom_field_info" ("group_id");

/* List of values for type lookup table */
CREATE TABLE custom_field_lookup (
  field_id INTEGER NOT NULL,
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT DEFAULT 1
);

/* The saved records in a folder associated with each category_id */
CREATE TABLE custom_field_record (
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  category_id INTEGER NOT NULL,
  record_id INT IDENTITY PRIMARY KEY,
  /*private BIT DEFAULT 0,
  department_id INTEGER DEFAULT -1,
  role_id INTEGER DEFAULT -1,*/
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  enabled BIT DEFAULT 1
);

CREATE INDEX "custom_field_rec_idx" ON "custom_field_record" ("link_module_id", "link_item_id", "category_id");

/* The saved custom field data related to a record_id (link_id) */
CREATE TABLE custom_field_data (
  record_id INTEGER NOT NULL,
  field_id INTEGER NOT NULL,
  selected_item_id INTEGER DEFAULT 0,
  entered_value TEXT,
  entered_number INTEGER,
  entered_float FLOAT
);

CREATE INDEX "custom_field_dat_idx" ON "custom_field_data" ("record_id", "field_id");

