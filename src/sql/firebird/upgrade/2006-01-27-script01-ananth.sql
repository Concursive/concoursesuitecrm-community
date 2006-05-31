-- Remove NOT NULL constraint by using a temporary column
ALTER TABLE action_step ADD action_id2 INTEGER;
UPDATE action_step SET action_id2 = action_id;
ALTER TABLE action_step DROP action_id;
ALTER TABLE action_step ADD action_id INTEGER REFERENCES lookup_step_actions(constant_id);
UPDATE action_step SET action_id = action_id2;
ALTER TABLE action_step DROP action_id2;

-- Action Step new column 'allow_update'
ALTER TABLE action_step ADD allow_update CHAR(1) DEFAULT 'Y' NOT NULL;
UPDATE action_step SET allow_update = 'Y';
