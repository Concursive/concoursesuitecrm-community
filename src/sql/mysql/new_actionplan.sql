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
  full_description text,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  site_id INTEGER REFERENCES lookup_site_id(code),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER action_plan_category_entries BEFORE INSERT ON  action_plan_category FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Action Plan Category Draft
CREATE TABLE action_plan_category_draft (
  id INT AUTO_INCREMENT PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int  NOT NULL DEFAULT 0,
  parent_cat_code int NOT NULL DEFAULT 0,
  description VARCHAR(300) NOT NULL,
  full_description text,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  site_id INTEGER REFERENCES lookup_site_id(code),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER action_plan_category_draft_entries BEFORE INSERT ON action_plan_category_draft FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Create a new table for identifying objects used by action plans
CREATE TABLE action_plan_constants (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  constant_id INTEGER NOT NULL,
  description VARCHAR(300),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER action_plan_constants_entries BEFORE INSERT ON  action_plan_constants FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE INDEX action_plan_constant_id ON action_plan_constants(constant_id);

-- Action Plan Lookup table
CREATE TABLE action_plan_editor_lookup (
  id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  constant_id INT NOT NULL REFERENCES action_plan_constants(map_id),
  level INTEGER DEFAULT 0,
  description TEXT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  category_id INT NOT NULL,
  modified TIMESTAMP NULL
);

CREATE TRIGGER action_plan_editor_lookup_entries BEFORE INSERT ON  action_plan_editor_lookup FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

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

CREATE TRIGGER action_plan_entries BEFORE INSERT ON  action_plan FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

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
  global BOOLEAN DEFAULT false,
  modified TIMESTAMP NULL
);

CREATE TRIGGER action_phase_entries BEFORE INSERT ON  action_phase FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Each action step can have an estimated duration type
-- Example: months, weeks, days, hours, minutes, etc..
CREATE TABLE lookup_duration_type (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_duration_type_entries BEFORE INSERT ON  lookup_duration_type FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_step_actions (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  constant_id INT UNIQUE NOT NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_step_actions_entries BEFORE INSERT ON  lookup_step_actions FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);


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
  allow_duplicate_recipient boolean NOT NULL DEFAULT FALSE,
  display_in_plan_list BOOLEAN DEFAULT FALSE NOT NULL,
  plan_list_label VARCHAR(300),
  quick_complete BOOLEAN DEFAULT FALSE NOT NULL,
  modified TIMESTAMP NULL
);

CREATE TRIGGER action_step_entries BEFORE INSERT ON  action_step FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE step_action_map (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  constant_id INTEGER NOT NULL REFERENCES action_plan_constants(map_id),
  action_constant_id INTEGER NOT NULL REFERENCES lookup_step_actions(constant_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP  NULL
);

CREATE TRIGGER step_action_map_entries BEFORE INSERT ON  step_action_map FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

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

CREATE TRIGGER action_plan_work_entries BEFORE INSERT ON  action_plan_work FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

--Action Plan Work Notes
CREATE TABLE action_plan_work_notes (
  note_id INT AUTO_INCREMENT PRIMARY KEY,
  plan_work_id INTEGER NOT NULL REFERENCES action_plan_work(plan_work_id),
  description VARCHAR(4096) NOT NULL,
  submitted TIMESTAMP NULL,
  submittedby INTEGER NOT NULL REFERENCES `access`(user_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER action_plan_work_notes_entries BEFORE INSERT ON  action_plan_work_notes FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

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

CREATE TRIGGER action_phase_work_entries BEFORE INSERT ON  action_phase_work FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

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

CREATE TRIGGER action_item_work_entries BEFORE INSERT ON  action_item_work FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

--Action Item Work Notes
CREATE TABLE action_item_work_notes (
  note_id INT AUTO_INCREMENT PRIMARY KEY,
  item_work_id INTEGER NOT NULL REFERENCES action_item_work(item_work_id),
  description VARCHAR(4096) NOT NULL,
  submitted TIMESTAMP NULL,
  submittedby INTEGER NOT NULL REFERENCES `access`(user_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER action_item_work_notes_entries BEFORE INSERT ON  action_item_work_notes FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

--Action Step lookup
CREATE TABLE action_step_lookup (
  code INT AUTO_INCREMENT PRIMARY KEY,
  step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  description VARCHAR(255) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER action_step_lookup_entries BEFORE INSERT ON  action_step_lookup FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

--Action Step Account Types
CREATE TABLE action_step_account_types (
  id INT AUTO_INCREMENT PRIMARY KEY,
  step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  type_id INTEGER NOT NULL REFERENCES lookup_account_types(code),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER action_step_accunt_tps_entries BEFORE INSERT ON  action_step_account_types FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

--Action Item Work Selection
CREATE TABLE action_item_work_selection (
  selection_id INT AUTO_INCREMENT PRIMARY KEY,
  item_work_id INTEGER NOT NULL REFERENCES action_item_work(item_work_id),
  selection INTEGER NOT NULL REFERENCES action_step_lookup(code),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER action_item_work_select_entries BEFORE INSERT ON  action_item_work_selection FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Ticket Category To Action Plan map table
CREATE TABLE ticket_category_plan_map (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  category_id INTEGER NOT NULL REFERENCES ticket_category(id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER ticket_category_pln_mp_entries BEFORE INSERT ON  ticket_category_plan_map FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Ticket Category Draft To Action Plan map table
CREATE TABLE ticket_category_draft_plan_map (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  category_id INTEGER NOT NULL REFERENCES ticket_category_draft(id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER ticket_cat_draft_pln_mp_entries BEFORE INSERT ON  ticket_category_draft_plan_map FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);
