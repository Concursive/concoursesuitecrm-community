/* ticket category editor */

CREATE TABLE ticket_category_draft (
  id INT IDENTITY PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int NOT NULL DEFAULT 0,
  parent_cat_code int NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description text NOT NULL DEFAULT '',
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

ALTER TABLE permission_category ADD categories BIT NOT NULL DEFAULT 0;
UPDATE permission_category SET categories = 0;
UPDATE permission_category SET categories = 1 where category = 'Tickets';

ALTER TABLE permission_category ADD scheduled_events BIT NOT NULL DEFAULT 0;
UPDATE permission_category SET scheduled_events = false;

ALTER TABLE permission_category ADD object_events BIT NOT NULL DEFAULT 0;
UPDATE permission_category SET object_events = false;

UPDATE permission_category SET scheduled_events = true, object_events = true WHERE category = 'Tickets';

/* New business process tables */

CREATE TABLE business_process_component_library (
  component_id INT IDENTITY PRIMARY KEY,
  component_name VARCHAR(255) NOT NULL,
  type_id INTEGER NOT NULL,
  class_name VARCHAR(255) NOT NULL,
  description VARCHAR(510),
  enabled BIT DEFAULT 1 NOT NULL
);

CREATE TABLE business_process_component_result_lookup (
  result_id INTEGER IDENTITY PRIMARY KEY,
  component_id INTEGER NOT NULL REFERENCES business_process_component_library,
  return_id INTEGER NOT NULL,
  description VARCHAR(255),
  level INTEGER DEFAULT 0 NOT NULL,
  enabled BIT DEFAULT 1 NOT NULL
);

CREATE TABLE business_process_parameter_library (
  parameter_id INTEGER IDENTITY NOT NULL PRIMARY KEY,
  component_id INTEGER NULL,
  param_name VARCHAR(255),
  description VARCHAR(510),
  default_value VARCHAR(4000),
  enabled BIT DEFAULT 1 NOT NULL
);

CREATE TABLE business_process (
  process_id INT IDENTITY PRIMARY KEY,
  process_name VARCHAR(255) UNIQUE NOT NULL,
  description VARCHAR(510),
  type_id INTEGER NOT NULL,
  link_module_id INTEGER NOT NULL REFERENCES permission_category,
  component_start_id INTEGER NULL,
  enabled BIT DEFAULT 1 NOT NULL,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE business_process_component (
  id INTEGER IDENTITY NOT NULL PRIMARY KEY,
  process_id INTEGER NOT NULL REFERENCES business_process,
  component_id INTEGER NOT NULL REFERENCES business_process_component_library,
  parent_id INTEGER NULL REFERENCES business_process_component,
  parent_result_id INTEGER NULL,
  enabled BIT DEFAULT 1 NOT NULL
);

CREATE TABLE business_process_parameter (
  id INTEGER IDENTITY NOT NULL PRIMARY KEY,
  process_id INTEGER NOT NULL REFERENCES business_process,
  param_name VARCHAR(255),
  param_value VARCHAR(4000),
  enabled BIT DEFAULT 1 NOT NULL
);

CREATE TABLE business_process_component_parameter (
  id INTEGER IDENTITY NOT NULL PRIMARY KEY,
  component_id INTEGER NOT NULL REFERENCES business_process_component,
  parameter_id INTEGER NOT NULL REFERENCES business_process_parameter_library,
  param_value VARCHAR(4000),
  enabled BIT DEFAULT 1 NOT NULL
);

CREATE TABLE business_process_hook_library (
  hook_id INTEGER IDENTITY NOT NULL PRIMARY KEY,
  link_module_id INTEGER NOT NULL REFERENCES permission_category,
  hook_class VARCHAR(255) NOT NULL,
  enabled BIT DEFAULT 0
);

CREATE TABLE business_process_hook_triggers (
  trigger_id INTEGER IDENTITY NOT NULL PRIMARY KEY,
  action_type_id INTEGER NOT NULL,
  hook_id INTEGER NOT NULL REFERENCES business_process_hook_library,
  enabled BIT DEFAULT 0
);

CREATE TABLE business_process_hook (
  id INTEGER IDENTITY NOT NULL PRIMARY KEY,
  trigger_id INTEGER NOT NULL REFERENCES business_process_hook_triggers,
  process_id INTEGER NOT NULL REFERENCES business_process,
  enabled BIT DEFAULT 0
);
