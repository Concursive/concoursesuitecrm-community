/* Adds new configuration options for various modules */

ALTER TABLE permission_category ADD COLUMN scheduled_events BOOLEAN;
ALTER TABLE permission_category ALTER scheduled_events SET DEFAULT false;
UPDATE permission_category SET scheduled_events = false;

ALTER TABLE permission_category ADD COLUMN object_events BOOLEAN;
ALTER TABLE permission_category ALTER object_events SET DEFAULT false;
UPDATE permission_category SET object_events = false;

UPDATE permission_category SET scheduled_events = true, object_events = true WHERE category = 'Tickets';

/* New business process tables */

CREATE SEQUENCE business_process_com_lb_id_seq;
CREATE TABLE business_process_component_library (
  component_id INTEGER DEFAULT nextval('business_process_com_lb_id_seq') NOT NULL PRIMARY KEY,
  component_name VARCHAR(255) NOT NULL,
  type_id INTEGER NOT NULL,
  class_name VARCHAR(255) NOT NULL,
  description VARCHAR(510),
  enabled BOOLEAN DEFAULT true NOT NULL
);

CREATE SEQUENCE business_process_comp_re_id_seq;
CREATE TABLE business_process_component_result_lookup (
  result_id INTEGER DEFAULT nextval('business_process_comp_re_id_seq') NOT NULL PRIMARY KEY,
  component_id INTEGER NOT NULL REFERENCES business_process_component_library,
  return_id INTEGER NOT NULL,
  description VARCHAR(255),
  level INTEGER DEFAULT 0 NOT NULL,
  enabled BOOLEAN DEFAULT true NOT NULL
);

CREATE SEQUENCE business_process_pa_lib_id_seq;
CREATE TABLE business_process_parameter_library (
  parameter_id INTEGER DEFAULT nextval('business_process_pa_lib_id_seq') NOT NULL PRIMARY KEY,
  component_id INTEGER NULL,
  param_name VARCHAR(255),
  description VARCHAR(510),
  default_value VARCHAR(4000),
  enabled BOOLEAN DEFAULT true NOT NULL
);

CREATE TABLE business_process (
  process_id SERIAL PRIMARY KEY,
  process_name VARCHAR(255) UNIQUE NOT NULL,
  description VARCHAR(510),
  type_id INTEGER NOT NULL,
  link_module_id INTEGER NOT NULL REFERENCES permission_category,
  component_start_id INTEGER NULL,
  enabled BOOLEAN DEFAULT true NOT NULL,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE SEQUENCE business_process_compone_id_seq;
CREATE TABLE business_process_component (
  id INTEGER DEFAULT nextval('business_process_compone_id_seq') NOT NULL PRIMARY KEY,
  process_id INTEGER NOT NULL REFERENCES business_process,
  component_id INTEGER NOT NULL REFERENCES business_process_component_library,
  parent_id INTEGER NULL REFERENCES business_process_component,
  parent_result_id INTEGER NULL,
  enabled BOOLEAN DEFAULT true NOT NULL
);

CREATE SEQUENCE business_process_param_id_seq;
CREATE TABLE business_process_parameter (
  id INTEGER DEFAULT nextval('business_process_param_id_seq') NOT NULL PRIMARY KEY,
  process_id INTEGER NOT NULL REFERENCES business_process,
  param_name VARCHAR(255),
  param_value VARCHAR(4000),
  enabled BOOLEAN DEFAULT true NOT NULL
);

CREATE SEQUENCE business_process_comp_pa_id_seq;
CREATE TABLE business_process_component_parameter (
  id INTEGER DEFAULT nextval('business_process_comp_pa_id_seq') NOT NULL PRIMARY KEY,
  component_id INTEGER NOT NULL REFERENCES business_process_component,
  parameter_id INTEGER NOT NULL REFERENCES business_process_parameter_library,
  param_value VARCHAR(4000),
  enabled BOOLEAN DEFAULT true NOT NULL
);

CREATE SEQUENCE business_process_hl_hook_id_seq;
CREATE TABLE business_process_hook_library (
  hook_id INTEGER DEFAULT nextval('business_process_hl_hook_id_seq') NOT NULL PRIMARY KEY,
  link_module_id INTEGER NOT NULL REFERENCES permission_category,
  hook_class VARCHAR(255) NOT NULL,
  enabled BOOLEAN DEFAULT false
);

CREATE SEQUENCE business_process_ho_trig_id_seq;
CREATE TABLE business_process_hook_triggers (
  trigger_id INTEGER DEFAULT nextval('business_process_ho_trig_id_seq') NOT NULL PRIMARY KEY,
  action_type_id INTEGER NOT NULL,
  hook_id INTEGER NOT NULL REFERENCES business_process_hook_library,
  enabled BOOLEAN DEFAULT false
);

CREATE SEQUENCE business_process_ho_hook_id_seq;
CREATE TABLE business_process_hook (
  id INTEGER DEFAULT nextval('business_process_ho_hook_id_seq') NOT NULL PRIMARY KEY,
  trigger_id INTEGER NOT NULL REFERENCES business_process_hook_triggers,
  process_id INTEGER NOT NULL REFERENCES business_process,
  enabled BOOLEAN DEFAULT false
);

