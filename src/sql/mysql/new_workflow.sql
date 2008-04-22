-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

/* The component library is a repository of components that can be used in 
   a business process */
 
CREATE TABLE business_process_component_library (
  component_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  component_name VARCHAR(255) NOT NULL,
  type_id INTEGER NOT NULL,
  class_name VARCHAR(255) NOT NULL,
  description VARCHAR(510),
  enabled BOOLEAN DEFAULT true NOT NULL
);

/* A component in the library has a result type: typically a Yes (1) or No (0), 
   but could be any String */
CREATE TABLE business_process_component_result_lookup (
  result_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  component_id INTEGER NOT NULL REFERENCES business_process_component_library,
  return_id INTEGER NOT NULL,
  description VARCHAR(255),
  level INTEGER DEFAULT 0 NOT NULL,
  enabled BOOLEAN DEFAULT true NOT NULL
);

/* Each component in the library has default parameters */

CREATE TABLE business_process_parameter_library (
  parameter_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  component_id INTEGER NULL,
  param_name VARCHAR(255),
  description VARCHAR(510),
  default_value TEXT,
  enabled BOOLEAN DEFAULT true NOT NULL
);

/* Custom business processes, built from the library */

CREATE TABLE business_process (
  process_id INT AUTO_INCREMENT PRIMARY KEY,
  process_name VARCHAR(255) UNIQUE NOT NULL,
  description VARCHAR(510),
  type_id INTEGER NOT NULL,
  link_module_id INTEGER NOT NULL REFERENCES permission_category,
  component_start_id INTEGER NULL,
  enabled BOOLEAN DEFAULT true NOT NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER business_process_entries BEFORE INSERT ON  business_process FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered);
/* Components that have been added to the business_process from the library */

CREATE TABLE business_process_component (
  id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  process_id INTEGER NOT NULL REFERENCES business_process,
  component_id INTEGER NOT NULL REFERENCES business_process_component_library,
  parent_id INTEGER NULL REFERENCES business_process_component,
  parent_result_id INTEGER NULL,
  enabled BOOLEAN DEFAULT true NOT NULL
);

/* Global process parameters that have been escalated from component parameters */

CREATE TABLE business_process_parameter (
  id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  process_id INTEGER NOT NULL REFERENCES business_process,
  param_name VARCHAR(255),
  param_value TEXT,
  enabled BOOLEAN DEFAULT true NOT NULL
);

/* Component parameters that are copied from the library and can be changed */

CREATE TABLE business_process_component_parameter (
  id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  component_id INTEGER NOT NULL REFERENCES business_process_component,
  parameter_id INTEGER NOT NULL REFERENCES business_process_parameter_library,
  param_value TEXT,
  enabled BOOLEAN DEFAULT true NOT NULL
);

/* Scheduled business processes are configured here, the task is the process name */

CREATE TABLE business_process_events (
  event_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  second VARCHAR(64) DEFAULT '0',
  minute VARCHAR(64) DEFAULT '*', 
  hour VARCHAR(64) DEFAULT '*',
  dayofmonth VARCHAR(64) DEFAULT '*',
  month VARCHAR(64) DEFAULT '*', 
  dayofweek VARCHAR(64) DEFAULT '*',
  year VARCHAR(64) DEFAULT '*',
  task VARCHAR(255),
  extrainfo VARCHAR(255),
  businessDays VARCHAR(6) DEFAULT 'true',
  enabled BOOLEAN DEFAULT false,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  process_id INTEGER NOT NULL REFERENCES business_process
);

CREATE TRIGGER business_process_events_entries BEFORE INSERT ON business_process_events FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered);
/* Records when scheduled events have been executed */

CREATE TABLE business_process_log (
  process_name VARCHAR(255) UNIQUE NOT NULL,
  anchor TIMESTAMP NOT NULL
);

/* A class that can be associated with a business process is identified here */

CREATE TABLE business_process_hook_library (
  hook_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  link_module_id INTEGER NOT NULL REFERENCES permission_category,
  hook_class VARCHAR(255) NOT NULL,
  enabled BOOLEAN DEFAULT false
);

/* The specific action of the class that can be hooked is identified here */

CREATE TABLE business_process_hook_triggers (
  trigger_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  action_type_id INTEGER NOT NULL,
  hook_id INTEGER NOT NULL REFERENCES business_process_hook_library,
  enabled BOOLEAN DEFAULT false
);

/* Combine the hook and the action with a business process to activate */

CREATE TABLE business_process_hook (
  id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  trigger_id INTEGER NOT NULL REFERENCES business_process_hook_triggers,
  process_id INTEGER NOT NULL REFERENCES business_process,
  enabled BOOLEAN DEFAULT false,
  priority INTEGER NOT NULL DEFAULT 0
);

