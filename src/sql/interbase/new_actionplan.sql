-- Action Plan Category
CREATE GENERATOR action_plan_category_id_seq;
CREATE TABLE action_plan_category (
  id INT NOT NULL PRIMARY KEY,
  cat_level int DEFAULT 0 NOT NULL,
  parent_cat_code int DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL, 
  full_description BLOB SUB_TYPE 1 SEGMENT SIZE 100 DEFAULT '' NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  site_id INTEGER REFERENCES lookup_site_id(code)
);

-- Action Plan Category Draft
CREATE GENERATOR action_plan_c_gory_draft_id_seq;
CREATE TABLE action_plan_category_draft (
  id INT NOT NULL PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int DEFAULT 0 NOT NULL,
  parent_cat_code int DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description BLOB SUB_TYPE 1 SEGMENT SIZE 100 DEFAULT '' NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  site_id INTEGER REFERENCES lookup_site_id(code)
);

-- Create a new table for identifying objects used by action plans
CREATE GENERATOR action_plan_c_stants_map_id_seq;
CREATE TABLE action_plan_constants (
  map_id INT NOT NULL PRIMARY KEY,
  constant_id INTEGER NOT NULL,
  description VARCHAR(300)
);
CREATE INDEX action_plan_constant_id ON action_plan_constants(constant_id);

CREATE GENERATOR action_plan_editor_loo_id_seq;
CREATE TABLE action_plan_editor_lookup (
  id INTEGER NOT NULL PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  constant_id INT NOT NULL REFERENCES action_plan_constants(map_id),
  "level" INTEGER DEFAULT 0,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  category_id INT NOT NULL
);

-- Action Plan
CREATE GENERATOR action_plan_plan_id_seq;
CREATE TABLE action_plan (
  plan_id INT NOT NULL PRIMARY KEY,
  plan_name VARCHAR(255) NOT NULL,
  description VARCHAR(2048),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  approved TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL REFERENCES "access"(user_id),
  archive_date TIMESTAMP,
  cat_code INT REFERENCES action_plan_category(id),
  subcat_code1 INT REFERENCES action_plan_category(id),
  subcat_code2 INT REFERENCES action_plan_category(id),
  subcat_code3 INT REFERENCES action_plan_category(id),
  link_object_id INT REFERENCES action_plan_constants(map_id),
  site_id INTEGER REFERENCES lookup_site_id(code)
);

-- Action Phase table
CREATE GENERATOR action_phase_phase_id_seq;
CREATE TABLE action_phase (
  phase_id INT NOT NULL PRIMARY KEY,
  parent_id INTEGER REFERENCES action_phase(phase_id),
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  phase_name VARCHAR(255) NOT NULL,
  description VARCHAR(2048),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
	entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  random BOOLEAN DEFAULT FALSE,
  "global" BOOLEAN DEFAULT FALSE
);

-- Each action step can have an estimated duration type
-- Example: months, weeks, days, hours, minutes, etc..
CREATE GENERATOR lookup_duration_type_code_seq;
CREATE TABLE lookup_duration_type (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR lookup_step_actions_code_seq;
CREATE TABLE lookup_step_actions (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT TRUE,
  constant_id INT NOT NULL UNIQUE
);

-- Action Step table
CREATE GENERATOR action_step_step_id_seq;
CREATE TABLE action_step (
  step_id INT NOT NULL PRIMARY KEY,
  parent_id INTEGER REFERENCES action_step(step_id),
  phase_id INTEGER NOT NULL REFERENCES action_phase(phase_id),
  description VARCHAR(2048),
  duration_type_id INTEGER REFERENCES lookup_duration_type(code),
  estimated_duration INTEGER,
  category_id INTEGER REFERENCES custom_field_category(category_id),
  field_id INTEGER REFERENCES custom_field_info(field_id),
  permission_type INTEGER,
  role_id INTEGER REFERENCES "role"(role_id),
  department_id INTEGER REFERENCES lookup_department(code),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  allow_skip_to_here BOOLEAN DEFAULT FALSE NOT NULL,
  label VARCHAR(80),
  action_required BOOLEAN DEFAULT FALSE NOT NULL,
  group_id INTEGER REFERENCES user_group(group_id),
  target_relationship VARCHAR(80),
  action_id INTEGER REFERENCES lookup_step_actions(constant_id),
  allow_update BOOLEAN DEFAULT TRUE NOT NULL,
  campaign_id INTEGER REFERENCES campaign(campaign_id),
  allow_duplicate_recipient BOOLEAN DEFAULT FALSE NOT NULL,
  display_in_plan_list BOOLEAN DEFAULT FALSE NOT NULL,
  plan_list_label VARCHAR(300)
);

CREATE GENERATOR step_action_map_map_id_seq;
CREATE TABLE step_action_map (
  map_id INT NOT NULL PRIMARY KEY,
  constant_id INTEGER NOT NULL REFERENCES action_plan_constants(map_id),
  action_constant_id INTEGER NOT NULL REFERENCES lookup_step_actions(constant_id)
);

--Action Plan Work
CREATE GENERATOR action_plan_w__plan_work_id_seq;
CREATE TABLE action_plan_work (
  plan_work_id INT NOT NULL PRIMARY KEY,
  action_plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  manager INTEGER,
  assignedTo INTEGER NOT NULL REFERENCES "access"(user_id),
  link_module_id INTEGER NOT NULL REFERENCES action_plan_constants(map_id),
  link_item_id INTEGER NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  current_phase INTEGER REFERENCES action_phase(phase_id)
);

--Action Plan Work Notes
CREATE GENERATOR action_plan_w_notes_note_id_seq;
CREATE TABLE action_plan_work_notes (
  note_id INT NOT NULL PRIMARY KEY,
  plan_work_id INTEGER NOT NULL REFERENCES action_plan_work(plan_work_id),
  description VARCHAR(4096) NOT NULL,
  submitted TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  submittedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

--Action Phase Work
CREATE GENERATOR action_phase__phase_work_id_seq;
CREATE TABLE action_phase_work (
  phase_work_id INT NOT NULL PRIMARY KEY,
  plan_work_id INTEGER NOT NULL REFERENCES action_plan_work(plan_work_id),
  action_phase_id INTEGER NOT NULL REFERENCES action_phase(phase_id),
  status_id INTEGER,
  start_date TIMESTAMP,
  end_date TIMESTAMP,
  "level" INTEGER DEFAULT 0,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

--Action Item Work
CREATE GENERATOR action_item_w__item_work_id_seq;
CREATE TABLE action_item_work (
  item_work_id INT NOT NULL PRIMARY KEY,
  phase_work_id INTEGER NOT NULL REFERENCES action_phase_work(phase_work_id),
  action_step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  status_id INTEGER,
  owner INTEGER REFERENCES "access"(user_id),
  start_date TIMESTAMP,
  end_date TIMESTAMP,
  link_module_id INTEGER REFERENCES action_plan_constants(map_id),
  link_item_id INTEGER,
  "level" INTEGER DEFAULT 0,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

--Action Item Work Notes
CREATE GENERATOR action_item_w_notes_note_id_seq;
CREATE TABLE action_item_work_notes (
  note_id INT NOT NULL PRIMARY KEY,
  item_work_id INTEGER NOT NULL REFERENCES action_item_work(item_work_id),
  description VARCHAR(4096) NOT NULL,
  submitted TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  submittedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

--Action Step lookup
CREATE GENERATOR action_step_lookup_code_seq;
CREATE TABLE action_step_lookup (
  code INT NOT NULL PRIMARY KEY,
  step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  description VARCHAR(255) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

--Action Step Account Types
CREATE TABLE action_step_account_types (
  step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  type_id INTEGER NOT NULL REFERENCES lookup_account_types(code)
);

--Action Item Work Selection
CREATE GENERATOR action_item_w__selection_id_seq;
CREATE TABLE action_item_work_selection (
  selection_id INT NOT NULL PRIMARY KEY,
  item_work_id INTEGER NOT NULL REFERENCES action_item_work(item_work_id),
  selection INTEGER NOT NULL REFERENCES action_step_lookup(code)
);

-- Ticket Category To Action Plan map table
CREATE GENERATOR ticket_catego_an_map_map_id_seq;
CREATE TABLE ticket_category_plan_map (
  map_id INT NOT NULL PRIMARY KEY,
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  category_id INTEGER NOT NULL REFERENCES ticket_category(id)
);

-- Ticket Category Draft To Action Plan map table
-- Reuse duplicate: CREATE GENERATOR ticket_catego_an_map_map_id_seq;
CREATE TABLE ticket_category_draft_plan_map (
  map_id INT NOT NULL PRIMARY KEY,
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  category_id INTEGER NOT NULL REFERENCES ticket_category_draft(id)
);
-- Action Plan Category
CREATE GENERATOR action_plan_category_id_seq;
CREATE TABLE action_plan_category (
  id INT NOT NULL PRIMARY KEY,
  cat_level int DEFAULT 0 NOT NULL,
  parent_cat_code int DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL, 
  full_description BLOB SUB_TYPE 1 SEGMENT SIZE 100 DEFAULT '' NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  site_id INTEGER REFERENCES lookup_site_id(code)
);

-- Action Plan Category Draft
CREATE GENERATOR action_plan_c_gory_draft_id_seq;
CREATE TABLE action_plan_category_draft (
  id INT NOT NULL PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int DEFAULT 0 NOT NULL,
  parent_cat_code int DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description BLOB SUB_TYPE 1 SEGMENT SIZE 100 DEFAULT '' NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  site_id INTEGER REFERENCES lookup_site_id(code)
);

-- Create a new table for identifying objects used by action plans
CREATE GENERATOR action_plan_c_stants_map_id_seq;
CREATE TABLE action_plan_constants (
  map_id INT NOT NULL PRIMARY KEY,
  constant_id INTEGER NOT NULL,
  description VARCHAR(300)
);
CREATE INDEX action_plan_constant_id ON action_plan_constants(constant_id);

CREATE GENERATOR action_plan_editor_loo_id_seq;
CREATE TABLE action_plan_editor_lookup (
  id INTEGER NOT NULL PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  constant_id INT NOT NULL REFERENCES action_plan_constants(map_id),
  "level" INTEGER DEFAULT 0,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  category_id INT NOT NULL
);

-- Action Plan
CREATE GENERATOR action_plan_plan_id_seq;
CREATE TABLE action_plan (
  plan_id INT NOT NULL PRIMARY KEY,
  plan_name VARCHAR(255) NOT NULL,
  description VARCHAR(2048),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  approved TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL REFERENCES "access"(user_id),
  archive_date TIMESTAMP,
  cat_code INT REFERENCES action_plan_category(id),
  subcat_code1 INT REFERENCES action_plan_category(id),
  subcat_code2 INT REFERENCES action_plan_category(id),
  subcat_code3 INT REFERENCES action_plan_category(id),
  link_object_id INT REFERENCES action_plan_constants(map_id),
  site_id INTEGER REFERENCES lookup_site_id(code)
);

-- Action Phase table
CREATE GENERATOR action_phase_phase_id_seq;
CREATE TABLE action_phase (
  phase_id INT NOT NULL PRIMARY KEY,
  parent_id INTEGER REFERENCES action_phase(phase_id),
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  phase_name VARCHAR(255) NOT NULL,
  description VARCHAR(2048),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
	entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  random BOOLEAN DEFAULT FALSE,
  "global" BOOLEAN DEFAULT FALSE
);

-- Each action step can have an estimated duration type
-- Example: months, weeks, days, hours, minutes, etc..
CREATE GENERATOR lookup_duration_type_code_seq;
CREATE TABLE lookup_duration_type (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR lookup_step_actions_code_seq;
CREATE TABLE lookup_step_actions (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT TRUE,
  constant_id INT NOT NULL UNIQUE
);

-- Action Step table
CREATE GENERATOR action_step_step_id_seq;
CREATE TABLE action_step (
  step_id INT NOT NULL PRIMARY KEY,
  parent_id INTEGER REFERENCES action_step(step_id),
  phase_id INTEGER NOT NULL REFERENCES action_phase(phase_id),
  description VARCHAR(2048),
  duration_type_id INTEGER REFERENCES lookup_duration_type(code),
  estimated_duration INTEGER,
  category_id INTEGER REFERENCES custom_field_category(category_id),
  field_id INTEGER REFERENCES custom_field_info(field_id),
  permission_type INTEGER,
  role_id INTEGER REFERENCES "role"(role_id),
  department_id INTEGER REFERENCES lookup_department(code),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  allow_skip_to_here BOOLEAN DEFAULT FALSE NOT NULL,
  label VARCHAR(80),
  action_required BOOLEAN DEFAULT FALSE NOT NULL,
  group_id INTEGER REFERENCES user_group(group_id),
  target_relationship VARCHAR(80),
  action_id INTEGER REFERENCES lookup_step_actions(constant_id),
  allow_update BOOLEAN DEFAULT TRUE NOT NULL,
  campaign_id INTEGER REFERENCES campaign(campaign_id),
  allow_duplicate_recipient BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE GENERATOR step_action_map_map_id_seq;
CREATE TABLE step_action_map (
  map_id INT NOT NULL PRIMARY KEY,
  constant_id INTEGER NOT NULL REFERENCES action_plan_constants(map_id),
  action_constant_id INTEGER NOT NULL REFERENCES lookup_step_actions(constant_id)
);

--Action Plan Work
CREATE GENERATOR action_plan_w__plan_work_id_seq;
CREATE TABLE action_plan_work (
  plan_work_id INT NOT NULL PRIMARY KEY,
  action_plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  manager INTEGER,
  assignedTo INTEGER NOT NULL REFERENCES "access"(user_id),
  link_module_id INTEGER NOT NULL REFERENCES action_plan_constants(map_id),
  link_item_id INTEGER NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  current_phase INTEGER REFERENCES action_phase(phase_id)
);

--Action Plan Work Notes
CREATE GENERATOR action_plan_w_notes_note_id_seq;
CREATE TABLE action_plan_work_notes (
  note_id INT NOT NULL PRIMARY KEY,
  plan_work_id INTEGER NOT NULL REFERENCES action_plan_work(plan_work_id),
  description VARCHAR(4096) NOT NULL,
  submitted TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  submittedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

--Action Phase Work
CREATE GENERATOR action_phase__phase_work_id_seq;
CREATE TABLE action_phase_work (
  phase_work_id INT NOT NULL PRIMARY KEY,
  plan_work_id INTEGER NOT NULL REFERENCES action_plan_work(plan_work_id),
  action_phase_id INTEGER NOT NULL REFERENCES action_phase(phase_id),
  status_id INTEGER,
  start_date TIMESTAMP,
  end_date TIMESTAMP,
  "level" INTEGER DEFAULT 0,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

--Action Item Work
CREATE GENERATOR action_item_w__item_work_id_seq;
CREATE TABLE action_item_work (
  item_work_id INT NOT NULL PRIMARY KEY,
  phase_work_id INTEGER NOT NULL REFERENCES action_phase_work(phase_work_id),
  action_step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  status_id INTEGER,
  owner INTEGER REFERENCES "access"(user_id),
  start_date TIMESTAMP,
  end_date TIMESTAMP,
  link_module_id INTEGER REFERENCES action_plan_constants(map_id),
  link_item_id INTEGER,
  "level" INTEGER DEFAULT 0,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

--Action Item Work Notes
CREATE GENERATOR action_item_w_notes_note_id_seq;
CREATE TABLE action_item_work_notes (
  note_id INT NOT NULL PRIMARY KEY,
  item_work_id INTEGER NOT NULL REFERENCES action_item_work(item_work_id),
  description VARCHAR(4096) NOT NULL,
  submitted TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  submittedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

--Action Step lookup
CREATE GENERATOR action_step_lookup_code_seq;
CREATE TABLE action_step_lookup (
  code INT NOT NULL PRIMARY KEY,
  step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  description VARCHAR(255) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

--Action Step Account Types
CREATE TABLE action_step_account_types (
  step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  type_id INTEGER NOT NULL REFERENCES lookup_account_types(code)
);

--Action Item Work Selection
CREATE GENERATOR action_item_w__selection_id_seq;
CREATE TABLE action_item_work_selection (
  selection_id INT NOT NULL PRIMARY KEY,
  item_work_id INTEGER NOT NULL REFERENCES action_item_work(item_work_id),
  selection INTEGER NOT NULL REFERENCES action_step_lookup(code)
);

-- Ticket Category To Action Plan map table
CREATE GENERATOR ticket_catego_an_map_map_id_seq;
CREATE TABLE ticket_category_plan_map (
  map_id INT NOT NULL PRIMARY KEY,
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  category_id INTEGER NOT NULL REFERENCES ticket_category(id)
);

-- Ticket Category Draft To Action Plan map table
-- Reuse duplicate: CREATE GENERATOR ticket_catego_an_map_map_id_seq;
CREATE TABLE ticket_category_draft_plan_map (
  map_id INT NOT NULL PRIMARY KEY,
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  category_id INTEGER NOT NULL REFERENCES ticket_category_draft(id)
);
