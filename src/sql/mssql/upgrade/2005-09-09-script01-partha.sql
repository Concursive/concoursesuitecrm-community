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

