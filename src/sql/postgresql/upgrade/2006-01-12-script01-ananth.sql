ALTER TABLE action_phase ADD COLUMN global BOOLEAN;
ALTER TABLE action_phase ALTER COLUMN global SET DEFAULT false;
UPDATE action_phase SET global = false;
