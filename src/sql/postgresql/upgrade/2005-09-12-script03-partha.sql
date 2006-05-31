-- Upgrade script for all previous action plans

ALTER TABLE action_plan ADD COLUMN link_object_id INTEGER REFERENCES action_plan_constants(map_id);

CREATE SEQUENCE lookup_step_actions_code_seq;
CREATE TABLE lookup_step_actions (
  code INTEGER DEFAULT nextval('lookup_step_actions_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

