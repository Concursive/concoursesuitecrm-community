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

