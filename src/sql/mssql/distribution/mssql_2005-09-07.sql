ALTER TABLE action_step ADD action_required BIT NOT NULL DEFAULT 0;

-- Specific
UPDATE action_step SET action_required = 1 WHERE action_id <> 2 AND action_id IS NOT NULL;
