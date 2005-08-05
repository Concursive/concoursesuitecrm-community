/**
 *  MSSQL Table Creation
 *  Workflow tables to support the WorkflowManager
 *
 *@author     mrajkowski
 *@created    May 15, 2003
 *@version    $Id$
 */

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

CREATE TABLE business_process_events (
  event_id INT IDENTITY PRIMARY KEY,
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
  enabled BIT DEFAULT 0,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  process_id INTEGER NOT NULL REFERENCES business_process
);

CREATE TABLE business_process_log (
  process_name VARCHAR(255) UNIQUE NOT NULL,
  anchor DATETIME NOT NULL
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
  enabled BIT DEFAULT 0,
  priority INTEGER NOT NULL DEFAULT 0
);
