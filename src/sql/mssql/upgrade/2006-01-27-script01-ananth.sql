-- Action Step new column 'allow_update'
ALTER TABLE action_step ADD allow_update BIT NOT NULL DEFAULT 1;
UPDATE action_step SET allow_update = 1;

-- Allow nulls for action_id
ALTER TABLE action_step ALTER COLUMN action_id BIT NULL;
