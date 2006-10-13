-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

-- Action Plan datastructure

-- Action Plan Category
CREATE TABLE action_plan_category (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cat_level int  NOT NULL DEFAULT 0, 
  parent_cat_code int NOT NULL DEFAULT 0, 
  description VARCHAR(300) NOT NULL, 
  full_description text NOT NULL DEFAULT '',
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  site_id INTEGER REFERENCES lookup_site_id(code)
);

-- Action Plan Category Draft
CREATE TABLE action_plan_category_draft (
  id INT AUTO_INCREMENT PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int  NOT NULL DEFAULT 0,
  parent_cat_code int NOT NULL DEFAULT 0,
  description VARCHAR(300) NOT NULL,
  full_description text NOT NULL DEFAULT '',
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  site_id INTEGER REFERENCES lookup_site_id(code)
);

-- Create a new table for identifying objects used by action plans
CREATE TABLE action_plan_constants (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  constant_id INTEGER NOT NULL,
  description VARCHAR(300)
);
CREATE INDEX action_plan_constant_id ON action_plan_constants(constant_id);

-- Action Plan Lookup table
CREATE TABLE action_plan_editor_lookup (
  id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  constant_id INT NOT NULL REFERENCES action_plan_constants(map_id),
  level INTEGER DEFAULT 0,
  description TEXT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  category_id INT NOT NULL
);

-- Action Plan
CREATE TABLE action_plan (
  plan_id INT AUTO_INCREMENT PRIMARY KEY,
  plan_name VARCHAR(255) NOT NULL,
  description VARCHAR(2048),
  enabled boolean NOT NULL DEFAULT true,
  approved TIMESTAMP NULL,
	entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
	modified TIMESTAMP NULL,
	modifiedby INT NOT NULL REFERENCES `access`(user_id),
  archive_date TIMESTAMP NULL,
  cat_code INT REFERENCES action_plan_category(id),
  subcat_code1 INT REFERENCES action_plan_category(id),
  subcat_code2 INT REFERENCES action_plan_category(id),
  subcat_code3 INT REFERENCES action_plan_category(id),
  link_object_id INT REFERENCES action_plan_constants(map_id),
  site_id INTEGER REFERENCES lookup_site_id(code)
);

-- Action Phase table
CREATE TABLE action_phase (
  phase_id INT AUTO_INCREMENT PRIMARY KEY,
  parent_id INTEGER REFERENCES action_phase(phase_id),
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  phase_name VARCHAR(255) NOT NULL,
  description VARCHAR(2048),
  enabled boolean NOT NULL DEFAULT true,
	entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  random BOOLEAN DEFAULT false,
  global BOOLEAN DEFAULT false
);

-- Each action step can have an estimated duration type
-- Example: months, weeks, days, hours, minutes, etc..
CREATE TABLE lookup_duration_type (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_step_actions (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true,
  constant_id INT UNIQUE NOT NULL
);

-- Action Step table
CREATE TABLE action_step (
  step_id INT AUTO_INCREMENT PRIMARY KEY,
  parent_id INTEGER REFERENCES action_step(step_id),
  phase_id INTEGER NOT NULL REFERENCES action_phase(phase_id),
  description VARCHAR(2048),
  duration_type_id INTEGER REFERENCES lookup_duration_type(code),
  estimated_duration INTEGER,
  category_id INTEGER REFERENCES custom_field_category(category_id),
  field_id INTEGER REFERENCES custom_field_info(field_id),
  permission_type INTEGER,
  role_id INTEGER REFERENCES role(role_id),
  department_id INTEGER REFERENCES lookup_department(code),
  enabled boolean NOT NULL DEFAULT true,
	entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  allow_skip_to_here boolean NOT NULL DEFAULT FALSE,
  label VARCHAR(80),
  action_required boolean NOT NULL DEFAULT FALSE,
  group_id INTEGER REFERENCES user_group(group_id),
  target_relationship VARCHAR(80),
  action_id INTEGER REFERENCES lookup_step_actions(constant_id),
  allow_update boolean NOT NULL DEFAULT TRUE,
  campaign_id INTEGER REFERENCES campaign(campaign_id),
  allow_duplicate_recipient boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE step_action_map (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  constant_id INTEGER NOT NULL REFERENCES action_plan_constants(map_id),
  action_constant_id INTEGER NOT NULL REFERENCES lookup_step_actions(constant_id)
);

--Action Plan Work
CREATE TABLE action_plan_work (
  plan_work_id INT AUTO_INCREMENT PRIMARY KEY, 
  action_plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  manager INTEGER,
  assignedTo INTEGER NOT NULL REFERENCES `access`(user_id),
  link_module_id INTEGER NOT NULL REFERENCES action_plan_constants(map_id),
  link_item_id INTEGER NOT NULL,
  enabled boolean NOT NULL DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INTEGER NOT NULL REFERENCES `access`(user_id),
  current_phase INTEGER REFERENCES action_phase(phase_id)
);

--Action Plan Work Notes
CREATE TABLE action_plan_work_notes (
  note_id INT AUTO_INCREMENT PRIMARY KEY,
  plan_work_id INTEGER NOT NULL REFERENCES action_plan_work(plan_work_id),
  description VARCHAR(4096) NOT NULL,
  submitted TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  submittedby INTEGER NOT NULL REFERENCES `access`(user_id)
);

--Action Phase Work
CREATE TABLE action_phase_work (
  phase_work_id INT AUTO_INCREMENT PRIMARY KEY, 
  plan_work_id INTEGER NOT NULL REFERENCES action_plan_work(plan_work_id),
  action_phase_id INTEGER NOT NULL REFERENCES action_phase(phase_id),
  status_id INTEGER,
  start_date TIMESTAMP NULL,
  end_date TIMESTAMP NULL,
  level INTEGER DEFAULT 0,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INTEGER NOT NULL REFERENCES `access`(user_id)
);

--Action Item Work
CREATE TABLE action_item_work (
  item_work_id INT AUTO_INCREMENT PRIMARY KEY,
  phase_work_id INTEGER NOT NULL REFERENCES action_phase_work(phase_work_id),
  action_step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  status_id INTEGER,
  owner INTEGER REFERENCES `access`(user_id),
  start_date TIMESTAMP NULL,
  end_date TIMESTAMP NULL,
  link_module_id INTEGER REFERENCES action_plan_constants(map_id),
  link_item_id INTEGER,
  level INTEGER DEFAULT 0,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INTEGER NOT NULL REFERENCES `access`(user_id)
);

--Action Item Work Notes
CREATE TABLE action_item_work_notes (
  note_id INT AUTO_INCREMENT PRIMARY KEY,
  item_work_id INTEGER NOT NULL REFERENCES action_item_work(item_work_id),
  description VARCHAR(4096) NOT NULL,
  submitted TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  submittedby INTEGER NOT NULL REFERENCES `access`(user_id)
);

--Action Step lookup
CREATE TABLE action_step_lookup (
  code INT AUTO_INCREMENT PRIMARY KEY,
  step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  description VARCHAR(255) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

--Action Step Account Types
CREATE TABLE action_step_account_types (
  step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  type_id INTEGER NOT NULL REFERENCES lookup_account_types(code)
);

--Action Item Work Selection
CREATE TABLE action_item_work_selection (
  selection_id INT AUTO_INCREMENT PRIMARY KEY,
  item_work_id INTEGER NOT NULL REFERENCES action_item_work(item_work_id),
  selection INTEGER NOT NULL REFERENCES action_step_lookup(code)
);

-- Ticket Category To Action Plan map table
CREATE TABLE ticket_category_plan_map (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  category_id INTEGER NOT NULL REFERENCES ticket_category(id)
);

-- Ticket Category Draft To Action Plan map table
CREATE TABLE ticket_category_draft_plan_map (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  category_id INTEGER NOT NULL REFERENCES ticket_category_draft(id)
);
