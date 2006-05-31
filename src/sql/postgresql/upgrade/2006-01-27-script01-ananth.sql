-- Action Step new column 'allow_update'
ALTER TABLE action_step ADD COLUMN allow_update BOOLEAN;
UPDATE action_step SET allow_update = true;
ALTER TABLE action_step ALTER COLUMN allow_update SET NOT NULL;
ALTER TABLE action_step ALTER COLUMN allow_update SET DEFAULT TRUE;

-- Remove constraint
ALTER TABLE action_step ALTER COLUMN action_id DROP NOT NULL;
