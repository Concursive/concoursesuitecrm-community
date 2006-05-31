ALTER TABLE action_step ADD allow_skip_to_here boolean;
UPDATE action_step SET allow_skip_to_here = false;
ALTER TABLE action_step ALTER COLUMN allow_skip_to_here SET NOT NULL;
ALTER TABLE action_step ALTER COLUMN allow_skip_to_here SET DEFAULT false;
