/* Each module can have multiple categories or folders of custom data */
CREATE TABLE custom_field_category (
  module_id INTEGER NOT NULL,
  category_id SERIAL,
  category_name VARCHAR(255) NOT NULL,
  level INTEGER DEFAULT 0,
  description TEXT,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP,
  default_item BOOLEAN DEFAULT false,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enabled BOOLEAN DEFAULT true
);

CREATE INDEX "custom_field_cat_idx" ON "custom_field_category" USING btree ("module_id");

/* Each category can have multiple groups of fields */
CREATE TABLE custom_field_group (
  category_id INTEGER NOT NULL,
  group_id SERIAL,
  group_name VARCHAR(255) NOT NULL,
  level INTEGER DEFAULT 0,
  description TEXT,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enabled BOOLEAN DEFAULT true
);

CREATE INDEX "custom_field_grp_idx" ON "custom_field_group" USING btree ("category_id");


/* Each folder has defined custom fields */
CREATE TABLE custom_field_info (
  group_id INTEGER NOT NULL,
  field_id SERIAL,
  field_name VARCHAR(255) NOT NULL,
  level INTEGER DEFAULT 0,
  
  field_type INTEGER NOT NULL,
  validation_type INTEGER DEFAULT 0,
  required BOOLEAN DEFAULT false,
  parameters TEXT,
  
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP DEFAULT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enabled BOOLEAN DEFAULT true
);

CREATE INDEX "custom_field_inf_idx" ON "custom_field_info" USING btree ("group_id");

/* List of values for type lookup table */
CREATE TABLE custom_field_lookup (
  field_id INTEGER NOT NULL,
  code SERIAL PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enabled BOOLEAN DEFAULT true
);

/* The saved records in a folder associated with each category_id */
CREATE TABLE custom_field_record (
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  category_id INTEGER NOT NULL,
  record_id SERIAL PRIMARY KEY,
  /*private BOOLEAN DEFAULT false,
  department_id INTEGER DEFAULT -1,
  role_id INTEGER DEFAULT -1,*/
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  enabled BOOLEAN DEFAULT true
);

CREATE INDEX "custom_field_rec_idx" ON "custom_field_record" USING btree ("link_module_id", "link_item_id", "category_id");

/* The saved custom field data related to a record_id (link_id) */
CREATE TABLE custom_field_data (
  record_id INTEGER NOT NULL,
  field_id INTEGER NOT NULL,
  selected_item_id INTEGER DEFAULT 0,
  entered_value TEXT,
  entered_number INTEGER,
  entered_float FLOAT
);

CREATE INDEX "custom_field_dat_idx" ON "custom_field_data" USING btree ("record_id", "field_id");

