
CREATE GENERATOR business_process_com_lb_id_seq;
-- Old Name: business_process_component_library (
CREATE TABLE business_process_comp_library (
  component_id INTEGER  NOT NULL,
  component_name VARCHAR(255) NOT NULL,
  type_id INTEGER NOT NULL,
  class_name VARCHAR(255) NOT NULL,
  description VARCHAR(510),
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  PRIMARY KEY (COMPONENT_ID)
);

CREATE GENERATOR business_process_comp_re_id_seq;
-- Old Name: business_process_component_result_lookup
CREATE TABLE business_pro_comp_result_lookup (
  result_id INTEGER  NOT NULL,
  component_id INTEGER NOT NULL REFERENCES business_process_comp_library(component_id),
  return_id INTEGER NOT NULL,
  description VARCHAR(255),
  "level" INTEGER DEFAULT 0 NOT NULL,
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  PRIMARY KEY (RESULT_ID)
);


CREATE GENERATOR business_process_pa_lib_id_seq;
-- Old Name: business_process_parameter_library
CREATE TABLE business_process_param_library (
  parameter_id INTEGER  NOT NULL,
  component_id INTEGER ,
  param_name VARCHAR(255),
  description VARCHAR(510),
  default_value VARCHAR(4000),
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  PRIMARY KEY (PARAMETER_ID)
);


CREATE GENERATOR business_process_process_id_seq;
CREATE TABLE business_process (
  process_id INTEGER  NOT NULL,
  process_name VARCHAR(255) NOT NULL,
  description VARCHAR(510),
  type_id INTEGER NOT NULL,
  link_module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  component_start_id INTEGER ,
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (PROCESS_ID)
);

CREATE GENERATOR business_process_compone_id_seq;
CREATE TABLE business_process_component (
  id INTEGER  NOT NULL,
  process_id INTEGER NOT NULL REFERENCES business_process(process_id),
  component_id INTEGER NOT NULL REFERENCES business_process_comp_library(component_id),
  parent_id INTEGER ,
  parent_result_id INTEGER ,
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  PRIMARY KEY (ID)
);

ALTER TABLE BUSINESS_PROCESS_COMPONENT ADD CONSTRAINT FK_BUS_PRO_COMP_ID
  FOREIGN KEY (PARENT_ID) REFERENCES BUSINESS_PROCESS_COMPONENT
  (ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE GENERATOR business_process_param_id_seq;
CREATE TABLE business_process_parameter (
  id INTEGER  NOT NULL,
  process_id INTEGER NOT NULL REFERENCES business_process(process_id),
  param_name VARCHAR(255),
  param_value VARCHAR(4000),
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  PRIMARY KEY (ID)
);

CREATE GENERATOR business_process_comp_pa_id_seq;
-- Old Name: business_process_component_parameter (
CREATE TABLE business_pro_comp_parameter (
  id INTEGER  NOT NULL,
  component_id INTEGER NOT NULL REFERENCES business_process_component(id),
  parameter_id INTEGER NOT NULL REFERENCES business_process_param_library(parameter_id),
  param_value VARCHAR(4000),
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  PRIMARY KEY (ID)
);

CREATE GENERATOR business_process_e_event_id_seq;
CREATE TABLE business_process_events (
  event_id INTEGER NOT NULL,
  "second" VARCHAR(64) DEFAULT '0',
  "minute" VARCHAR(64) DEFAULT '*',
  "hour" VARCHAR(64) DEFAULT '*',
  dayofmonth VARCHAR(64) DEFAULT '*',
  "month" VARCHAR(64) DEFAULT '*',
  "dayofweek" VARCHAR(64) DEFAULT '*',
  "year" VARCHAR(64) DEFAULT '*',
  task VARCHAR(255),
  extrainfo VARCHAR(255),
  businessDays VARCHAR(6) DEFAULT 'true',
  enabled CHAR(1) DEFAULT 'N',
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  process_id INTEGER NOT NULL REFERENCES business_process(process_id),
  PRIMARY KEY (event_id)
);

-- previously had UNIQUE setting.. not allowed on this large of a field.
CREATE TABLE business_process_log (
  process_name VARCHAR(255) NOT NULL,
  anchor TIMESTAMP NOT NULL
);

CREATE GENERATOR business_process_hl_hook_id_seq;
CREATE TABLE business_process_hook_library (
  hook_id INTEGER  NOT NULL,
  link_module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  hook_class VARCHAR(255) NOT NULL,
  enabled CHAR(1) DEFAULT 'N',
  PRIMARY KEY (HOOK_ID)
);


CREATE GENERATOR business_process_ho_trig_id_seq;
CREATE TABLE business_process_hook_triggers (
  trigger_id INTEGER  NOT NULL,
  action_type_id INTEGER NOT NULL,
  hook_id INTEGER NOT NULL REFERENCES business_process_hook_library(hook_id),
  enabled CHAR(1) DEFAULT 'N',
  CONSTRAINT PK_BUS_PRO_HOOK_TRIGGERS PRIMARY KEY (TRIGGER_ID)
);


CREATE GENERATOR business_process_ho_hook_id_seq;
CREATE TABLE business_process_hook (
  id INTEGER  NOT NULL,
  trigger_id INTEGER NOT NULL REFERENCES business_process_hook_triggers(trigger_id),
  process_id INTEGER NOT NULL REFERENCES business_process(process_id),
  enabled CHAR(1) DEFAULT 'N',
  priority INTEGER DEFAULT 0 NOT NULL,
  PRIMARY KEY (ID)
);