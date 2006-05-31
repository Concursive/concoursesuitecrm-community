-- Action Plan datastructure

-- Action Plan
CREATE TABLE action_plan (
  plan_id INT IDENTITY PRIMARY KEY,
  plan_name VARCHAR(255) NOT NULL,
  description VARCHAR(2048),
  enabled BIT NOT NULL DEFAULT 1,
  approved DATETIME DEFAULT NULL,
	-- record status
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
	modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modifiedby INT NOT NULL REFERENCES access(user_id),
  -- continuation
  archive_date DATETIME
);

-- Action Phase table
CREATE TABLE action_phase (
  phase_id INT IDENTITY PRIMARY KEY,
  parent_id INTEGER REFERENCES action_phase(phase_id),
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  phase_name VARCHAR(255) NOT NULL,
  description VARCHAR(2048),
  enabled BIT NOT NULL DEFAULT 1,
	-- record status
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Each action step can have an estimated duration type
-- Example: months, weeks, days, hours, minutes, etc..
CREATE TABLE lookup_duration_type (
  code INT IDENTITY NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- Action Step table
CREATE TABLE action_step (
  step_id INT IDENTITY PRIMARY KEY,
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
  enabled BIT NOT NULL DEFAULT 1,
	-- record status
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

