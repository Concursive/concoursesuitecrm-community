-- Script (C) 2005 Dark Horse Ventures, all rights reserved
-- Database upgrade v3.2 part 1 (2005-08-23)

-- Action Plan datastructure

-- Action Plan
CREATE TABLE action_plan (
  plan_id SERIAL PRIMARY KEY,
  plan_name VARCHAR(255) NOT NULL,
  description VARCHAR(2048),
  enabled boolean NOT NULL DEFAULT true,
  approved TIMESTAMP(3) DEFAULT NULL,
	-- record status
	entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
	modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modifiedby INT NOT NULL REFERENCES access(user_id),
  -- continuation
  archive_date TIMESTAMP(3)
);

-- Action Phase table
CREATE TABLE action_phase (
  phase_id SERIAL PRIMARY KEY,
  parent_id INTEGER REFERENCES action_phase(phase_id),
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  phase_name VARCHAR(255) NOT NULL,
  description VARCHAR(2048),
  enabled boolean NOT NULL DEFAULT true,
	-- record status
	entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Each action step can have an estimated duration type
-- Example: months, weeks, days, hours, minutes, etc..
CREATE SEQUENCE lookup_duration_type_code_seq;
CREATE TABLE lookup_duration_type (
  code INTEGER DEFAULT nextval('lookup_duration_type_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

-- Action Step table
CREATE TABLE action_step (
  step_id SERIAL PRIMARY KEY,
  parent_id INTEGER REFERENCES action_step(step_id),
  phase_id INTEGER NOT NULL REFERENCES action_phase(phase_id),
  description VARCHAR(2048),
  action_id INTEGER,
  duration_type_id INTEGER REFERENCES lookup_duration_type(code),
  estimated_duration INTEGER,
  category_id INTEGER REFERENCES custom_field_category(category_id),
  field_id INTEGER REFERENCES custom_field_info(field_id),
  permission_type INTEGER,
  role_id INTEGER REFERENCES role(role_id),
  department_id INTEGER REFERENCES lookup_department(code),
  enabled boolean NOT NULL DEFAULT true,
	-- record status
	entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Action Plan Input

--Action Plan Work
CREATE TABLE action_plan_work (
  plan_work_id SERIAL PRIMARY KEY,
  action_plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  manager INTEGER,
  assignedTo INTEGER NOT NULL REFERENCES access(user_id),
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  enabled boolean NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id)
);

--Action Phase Work
CREATE TABLE action_phase_work (
  phase_work_id SERIAL PRIMARY KEY,
  plan_work_id INTEGER NOT NULL REFERENCES action_plan_work(plan_work_id),
  action_phase_id INTEGER NOT NULL REFERENCES action_phase(phase_id),
  status_id INTEGER,
  start_date TIMESTAMP(3),
  end_date TIMESTAMP(3),
  level INTEGER DEFAULT 0,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id)
);
--Action Item Work
CREATE TABLE action_item_work (
  item_work_id SERIAL PRIMARY KEY,
  phase_work_id INTEGER NOT NULL REFERENCES action_phase_work(phase_work_id),
  action_step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  status_id INTEGER,
  owner INTEGER REFERENCES access(user_id),
  start_date TIMESTAMP(3),
  end_date TIMESTAMP(3),
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  level INTEGER DEFAULT 0,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id)
);

ALTER TABLE action_step ADD allow_skip_to_here boolean;
UPDATE action_step SET allow_skip_to_here = false;
ALTER TABLE action_step ALTER COLUMN allow_skip_to_here SET NOT NULL;
ALTER TABLE action_step ALTER COLUMN allow_skip_to_here SET DEFAULT false;

