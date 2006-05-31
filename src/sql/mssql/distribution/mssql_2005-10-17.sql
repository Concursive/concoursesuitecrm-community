-- Add action plans to the permission category

ALTER TABLE permission_category ADD action_plans BIT DEFAULT 0 NOT NULL;
UPDATE permission_category SET action_plans = 0;

UPDATE permission_category SET action_plans = 1 WHERE constant IN (1,8);

-- Action Plan Lookup table
CREATE TABLE action_plan_editor_lookup (
  id INTEGER IDENTITY PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  constant_id INT NOT NULL,
  level INTEGER DEFAULT 0,
  description TEXT,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  category_id INT NOT NULL
);

-- Create a new table for identifying objects used by action plans
CREATE TABLE action_plan_constants (
  map_id INT IDENTITY PRIMARY KEY,
  constant_id INTEGER NOT NULL,
  description VARCHAR(300)
);
CREATE INDEX action_plan_constant_id ON action_plan_constants(constant_id);

-- Script to insert values into action plan constants and editors
INSERT INTO action_plan_constants (constant_id, description) VALUES(42420034,'accounts');
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051329, 'tickets');
INSERT INTO action_plan_constants (constant_id, description) VALUES(2, 'contacts');
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051328, 'quotes');
INSERT INTO action_plan_constants (constant_id, description) VALUES(3, 'pipeline');
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051330, 'projects');
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051331, 'communications');
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051332, 'admin');
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051333, 'calls');
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051334, 'cfsnote');
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051335, 'pipeline_calls');
INSERT INTO action_plan_constants (constant_id, description) VALUES(912051336, 'myhomepage');
INSERT INTO action_plan_constants (constant_id, description) VALUES(831200519, 'note');
INSERT INTO action_plan_constants (constant_id, description) VALUES(831200520, 'selection');
INSERT INTO action_plan_constants (constant_id, description) VALUES(1011200517, 'pipeline_component');

ALTER TABLE action_plan ADD link_object_id INTEGER REFERENCES action_plan_constants(map_id);

UPDATE action_plan SET link_object_id = (SELECT map_id from action_plan_constants where constant_id=42420034);

CREATE TABLE lookup_step_actions (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

CREATE TABLE step_action_map (
  map_id INT IDENTITY PRIMARY KEY,
  constant_id INTEGER NOT NULL REFERENCES action_plan_constants(map_id),
  action_id INTEGER NOT NULL REFERENCES lookup_step_actions(code)
);

UPDATE action_plan_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=42420034) WHERE link_module_id=1;

ALTER TABLE [action_plan_work] ADD
	 FOREIGN KEY
	(
		[link_module_id]
	) REFERENCES [action_plan_constants] (
		[map_id]
	)
GO


ALTER TABLE action_item_work ALTER COLUMN link_module_id INTEGER NULL;

UPDATE action_item_work SET link_module_id = NULL WHERE link_module_id = -1;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=2) WHERE link_module_id=2;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=3) WHERE link_module_id=3;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=1) WHERE link_module_id=1;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=831200519) WHERE link_module_id=831200519;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=831200520) WHERE link_module_id=831200520;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=42420034) WHERE link_module_id=42420034;
UPDATE action_item_work SET link_module_id = (SELECT map_id FROM action_plan_constants WHERE constant_id=1011200517) WHERE link_module_id=1011200517;

ALTER TABLE [action_item_work] ADD
	 FOREIGN KEY
	(
		[link_module_id]
	) REFERENCES [action_plan_constants] (
		[map_id]
	)
GO

ALTER TABLE action_item_work ALTER COLUMN link_item_id INTEGER NULL;

UPDATE action_item_work SET link_item_id = NULL WHERE link_item_id = -1;

-- Create a new table to group users
CREATE TABLE user_group (
  group_id INT IDENTITY PRIMARY KEY,
  group_name VARCHAR(255) NOT NULL,
  description text,
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id)
);

-- Create the user group map table
CREATE TABLE user_group_map (
  group_map_id INT IDENTITY PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  group_id INTEGER NOT NULL REFERENCES user_group(group_id),
  level INTEGER NOT NULL DEFAULT 10,
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE ticket ADD user_group_id INTEGER REFERENCES user_group(group_id);

-- Ticket Cause lookup
CREATE TABLE lookup_ticket_cause (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- Ticket Resolution lookup
CREATE TABLE lookup_ticket_resolution (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

ALTER TABLE ticket ADD cause_id INTEGER REFERENCES lookup_ticket_cause(code);
ALTER TABLE ticket ADD resolution_id INTEGER REFERENCES lookup_ticket_resolution(code);

CREATE TABLE lookup_asset_materials(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE asset_materials_map (
  map_id INT IDENTITY PRIMARY KEY,
  asset_id INTEGER NOT NULL REFERENCES asset(asset_id),
  code INTEGER NOT NULL REFERENCES lookup_asset_materials(code),
  quantity FLOAT,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--Ticket Defect table
CREATE TABLE ticket_defect (
  defect_id INT IDENTITY PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  start_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME,
  enabled BIT NOT NULL DEFAULT 1,
  trashed_date DATETIME
);

ALTER TABLE ticket ADD defect_id INTEGER REFERENCES ticket_defect(defect_id);

-- Knowledge Base table
CREATE TABLE knowledge_base (
  kb_id INT IDENTITY PRIMARY KEY,
  category_id INTEGER REFERENCES ticket_category(id),
  title VARCHAR(255) NOT NULL,
  description TEXT,
  item_id INTEGER REFERENCES project_files(item_id),
	-- record status
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
	modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modifiedby INT NOT NULL REFERENCES access(user_id)
);

ALTER TABLE action_phase ADD random BIT DEFAULT 0;
UPDATE action_phase SET random = 0;

ALTER TABLE asset ADD parent_id INTEGER REFERENCES asset(asset_id);

-- Loose ends

ALTER TABLE lookup_opportunity_environment ALTER COLUMN description VARCHAR(300);
ALTER TABLE lookup_opportunity_competitors ALTER COLUMN description VARCHAR(300);
ALTER TABLE lookup_opportunity_event_compelling ALTER COLUMN description VARCHAR(300);
ALTER TABLE lookup_opportunity_budget ALTER COLUMN description VARCHAR(300);

CREATE TABLE lookup_ticket_task_category (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

ALTER TABLE task ADD ticket_task_category_id INTEGER REFERENCES lookup_ticket_task_category(code);

