-- 2005-10-17 Upgrade

-- Add action plans to the permission category

ALTER TABLE permission_category ADD COLUMN action_plans BOOLEAN;
UPDATE permission_category SET action_plans = false;
ALTER TABLE permission_category ALTER COLUMN action_plans SET NOT NULL;
ALTER TABLE permission_category ALTER COLUMN action_plans SET DEFAULT false;

UPDATE permission_category SET action_plans = true WHERE constant IN (1,8);

CREATE SEQUENCE action_plan_editor_loo_id_seq;
-- Action Plan Lookup table
CREATE TABLE action_plan_editor_lookup (
  id INTEGER DEFAULT nextval('action_plan_editor_loo_id_seq') NOT NULL PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  constant_id INT NOT NULL,
  level INTEGER DEFAULT 0,
  description TEXT,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  category_id INT NOT NULL
);

-- Create a new table for identifying objects used by action plans
CREATE TABLE action_plan_constants (
  map_id SERIAL PRIMARY KEY,
  constant_id INTEGER NOT NULL,
  description VARCHAR(300)
);
CREATE INDEX action_plan_constant_id ON action_plan_constants(constant_id);

-- Script to insert values into action plan constants and editors

-- ActionPlan.ACCOUNTS for the accounts module
INSERT INTO action_plan_constants (constant_id, description) VALUES(42420034,'accounts');
-- ActionPlan.TICKETS for the help desk module
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051329, 'tickets');
-- ActionPlan.CONTACTS = 2;
INSERT INTO action_plan_constants (constant_id, description) VALUES(2, 'contacts');
-- ActionPlan.QUOTES = 912051328;
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051328, 'quotes');
-- ActionPlan.PIPELINE = 3;
INSERT INTO action_plan_constants (constant_id, description) VALUES(3, 'pipeline');
-- ActionPlan.PROJECTS = 912051330;
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051330, 'projects');
-- ActionPlan.COMMUNICATIONS = 912051331;
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051331, 'communications');
-- ActionPlan.ADMIN = 912051332;
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051332, 'admin');
-- ActionPlan.CONTACTS_CALLS = 912051333;
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051333, 'calls');
-- ActionPlan.CFSNOTE = 912051334;
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051334, 'cfsnote');
-- ActionPlan.PIPELINE_CALLS = 912051335;
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051335, 'pipeline_calls');
-- ActionPlan.MYHOMEPAGE = 912051336;
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051336, 'myhomepage');
-- ActionPlan.ACTION_ITEM_WORK_NOTE_OBJECT
INSERT INTO action_plan_constants (constant_id, description) VALUES(831200519, 'note');
-- ActionPlan.ACTION_ITEM_WORK_SELECTION_OBJECT
INSERT INTO action_plan_constants (constant_id, description) VALUES(831200520, 'selection');
-- ActionPlan.PIPELINE_COMPONENT = 1011200517;
INSERT INTO action_plan_constants (constant_id, description) VALUES(1011200517, 'pipeline_component');

-- Upgrade script for all previous action plans

ALTER TABLE action_plan ADD COLUMN link_object_id INTEGER REFERENCES action_plan_constants(map_id);

UPDATE action_plan SET link_object_id = (SELECT map_id from action_plan_constants where constant_id=42420034);

CREATE SEQUENCE lookup_step_actions_code_seq;
CREATE TABLE lookup_step_actions (
  code INTEGER DEFAULT nextval('lookup_step_actions_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

CREATE TABLE step_action_map (
  map_id SERIAL PRIMARY KEY,
  constant_id INTEGER NOT NULL REFERENCES action_plan_constants(map_id),
  action_id INTEGER NOT NULL REFERENCES lookup_step_actions(code)
);

UPDATE action_plan_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=42420034) WHERE link_module_id=1;
ALTER TABLE action_plan_work ADD FOREIGN KEY (link_module_id) REFERENCES action_plan_constants(map_id);

ALTER TABLE action_item_work ALTER COLUMN link_module_id DROP NOT NULL;
UPDATE action_item_work SET link_module_id = NULL WHERE link_module_id = -1;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=2) WHERE link_module_id=2;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=3) WHERE link_module_id=3;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=1) WHERE link_module_id=1;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=831200519) WHERE link_module_id=831200519;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=831200520) WHERE link_module_id=831200520;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=42420034) WHERE link_module_id=42420034;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=1011200517) WHERE link_module_id=1011200517;
ALTER TABLE action_item_work ADD FOREIGN KEY (link_module_id) REFERENCES action_plan_constants(map_id);

ALTER TABLE action_item_work ALTER COLUMN link_item_id DROP NOT NULL;
UPDATE action_item_work SET link_item_id = NULL WHERE link_item_id = -1;

-- Create a new table to group users
CREATE TABLE user_group (
  group_id SERIAL PRIMARY KEY,
  group_name VARCHAR(255) NOT NULL,
  description text,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id)
);

-- Create the user group map table
CREATE TABLE user_group_map (
  group_map_id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  group_id INTEGER NOT NULL REFERENCES user_group(group_id),
  level INTEGER NOT NULL DEFAULT 10,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE ticket ADD COLUMN user_group_id INTEGER REFERENCES user_group(group_id);

-- Ticket Cause lookup
CREATE SEQUENCE lookup_ticket_cause_code_seq;
CREATE TABLE lookup_ticket_cause (
  code INTEGER DEFAULT nextval('lookup_ticket_cause_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

-- Ticket Resolution lookup
CREATE SEQUENCE lookup_ticket_resoluti_code_seq;
CREATE TABLE lookup_ticket_resolution (
  code INTEGER DEFAULT nextval('lookup_ticket_resoluti_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

ALTER TABLE ticket ADD COLUMN cause_id INTEGER REFERENCES lookup_ticket_cause(code);
ALTER TABLE ticket ADD COLUMN resolution_id INTEGER REFERENCES lookup_ticket_resolution(code);

CREATE TABLE lookup_asset_materials(
 code SERIAL PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE asset_materials_map (
  map_id SERIAL PRIMARY KEY,
  asset_id INTEGER NOT NULL REFERENCES asset(asset_id),
  code INTEGER NOT NULL REFERENCES lookup_asset_materials(code),
  quantity FLOAT,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--Ticket Defect table
CREATE TABLE ticket_defect (
  defect_id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  start_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP(3),
  enabled BOOLEAN NOT NULL DEFAULT true,
  trashed_date TIMESTAMP(3)
);

ALTER TABLE ticket ADD COLUMN defect_id INTEGER REFERENCES ticket_defect(defect_id);

-- Knowledge Base table
CREATE TABLE knowledge_base (
  kb_id SERIAL PRIMARY KEY,
  category_id INTEGER REFERENCES ticket_category(id),
  title VARCHAR(255) NOT NULL,
  description TEXT,
  item_id INTEGER REFERENCES project_files(item_id),
	-- record status
	entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
	modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modifiedby INT NOT NULL REFERENCES access(user_id)
);

ALTER TABLE action_phase ADD COLUMN random BOOLEAN;
UPDATE action_phase set random=false;
ALTER TABLE action_phase ALTER COLUMN random SET DEFAULT false;

ALTER TABLE asset ADD COLUMN parent_id INTEGER REFERENCES asset(asset_id);

CREATE SEQUENCE lookup_ticket_task_cat_code_seq;
CREATE TABLE lookup_ticket_task_category (
  code INTEGER DEFAULT nextval('lookup_ticket_task_cat_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

ALTER TABLE task ADD COLUMN ticket_task_category_id INTEGER REFERENCES lookup_ticket_task_category(code);

