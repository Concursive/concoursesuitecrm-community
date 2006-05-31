-- Upgrade script for all previous Accounts action plans

ALTER TABLE action_plan ADD link_object_id INTEGER REFERENCES action_plan_constants(map_id);

UPDATE action_plan SET link_object_id = (SELECT map_id from action_plan_constants where constant_id=42420034);

CREATE TABLE lookup_step_actions (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

