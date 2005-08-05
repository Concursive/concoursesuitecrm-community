
CREATE SEQUENCE business_process_com_lb_id_seq;
CREATE TABLE business_process_component_library (
  component_id INT  PRIMARY KEY,
  component_name VARCHAR(255) NOT NULL,
  type_id INTEGER NOT NULL,
  class_name VARCHAR(255) NOT NULL,
  description VARCHAR(510),
  enabled boolean DEFAULT true NOT NULL
);

CREATE SEQUENCE business_process_comp_re_id_seq;
CREATE TABLE business_process_component_result_lookup (
  result_id INTEGER  PRIMARY KEY,
  component_id INTEGER NOT NULL REFERENCES business_process_component_library,
  return_id INTEGER NOT NULL,
  description VARCHAR(255),
  "level" INTEGER DEFAULT 0 NOT NULL,
  enabled boolean DEFAULT true NOT NULL
);


CREATE SEQUENCE business_process_pa_lib_id_seq;
CREATE TABLE business_process_parameter_library (
  parameter_id INTEGER  NOT NULL PRIMARY KEY,
  component_id INTEGER ,
  param_name VARCHAR(255),
  description VARCHAR(510),
  default_value VARCHAR(4000),
  enabled boolean DEFAULT true NOT NULL
);


CREATE SEQUENCE business_process_process_id_seq;
CREATE TABLE business_process (
  process_id INT  PRIMARY KEY,
  process_name VARCHAR(255) UNIQUE NOT NULL,
  description VARCHAR(510),
  type_id INTEGER NOT NULL,
  link_module_id INTEGER NOT NULL REFERENCES permission_category,
  component_start_id INTEGER ,
  enabled boolean DEFAULT true NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE SEQUENCE business_process_compone_id_seq;
CREATE TABLE business_process_component (
  id INTEGER  NOT NULL PRIMARY KEY,
  process_id INTEGER NOT NULL REFERENCES business_process,
  component_id INTEGER NOT NULL REFERENCES business_process_component_library,
  parent_id INTEGER  REFERENCES business_process_component,
  parent_result_id INTEGER ,
  enabled boolean DEFAULT true NOT NULL
);

CREATE SEQUENCE business_process_param_id_seq;
CREATE TABLE business_process_parameter (
  id INTEGER  NOT NULL PRIMARY KEY,
  process_id INTEGER NOT NULL REFERENCES business_process,
  param_name VARCHAR(255),
  param_value VARCHAR(4000),
  enabled boolean DEFAULT true NOT NULL
);

CREATE SEQUENCE business_process_comp_pa_id_seq;
CREATE TABLE business_process_component_parameter (
  id INTEGER  NOT NULL PRIMARY KEY,
  component_id INTEGER NOT NULL REFERENCES business_process_component,
  parameter_id INTEGER NOT NULL REFERENCES business_process_parameter_library,
  param_value VARCHAR(4000),
  enabled boolean DEFAULT true NOT NULL
);

CREATE SEQUENCE business_process_e_event_id_seq;
CREATE TABLE business_process_events (
  event_id INT PRIMARY KEY,
  "second" VARCHAR(64) DEFAULT '0',
  "minute" VARCHAR(64) DEFAULT '*', 
  "hour" VARCHAR(64) DEFAULT '*',
  dayofmonth VARCHAR(64) DEFAULT '*',
  "month" VARCHAR(64) DEFAULT '*', 
  dayofweek VARCHAR(64) DEFAULT '*',
  "year" VARCHAR(64) DEFAULT '*',
  task VARCHAR(255),
  extrainfo VARCHAR(255),
  businessDays VARCHAR(6) DEFAULT 'true',
  enabled boolean DEFAULT false,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  process_id INTEGER REFERENCES business_process NOT NULL 
);

CREATE TABLE business_process_log (
  process_name VARCHAR(255) UNIQUE NOT NULL,
  anchor TIMESTAMP NOT NULL
);

CREATE SEQUENCE business_process_hl_hook_id_seq;
CREATE TABLE business_process_hook_library (
  hook_id INTEGER  NOT NULL PRIMARY KEY,
  link_module_id INTEGER NOT NULL REFERENCES permission_category,
  hook_class VARCHAR(255) NOT NULL,
  enabled boolean DEFAULT false
);


CREATE SEQUENCE business_process_ho_trig_id_seq;
CREATE TABLE business_process_hook_triggers (
  trigger_id INTEGER  NOT NULL PRIMARY KEY,
  action_type_id INTEGER NOT NULL,
  hook_id INTEGER NOT NULL REFERENCES business_process_hook_library,
  enabled boolean DEFAULT false
);


CREATE SEQUENCE business_process_ho_hook_id_seq;
CREATE TABLE business_process_hook (
  id INTEGER  NOT NULL PRIMARY KEY,
  trigger_id INTEGER NOT NULL REFERENCES business_process_hook_triggers,
  process_id INTEGER NOT NULL REFERENCES business_process,
  enabled boolean DEFAULT false,
  priority INTEGER DEFAULT 0 NOT NULL
);

