ALTER TABLE action_step ADD COLUMN action_required boolean;
UPDATE action_step SET action_required = false;
ALTER TABLE action_step ALTER COLUMN action_required SET NOT NULL;
ALTER TABLE action_step ALTER COLUMN action_required SET DEFAULT false;
