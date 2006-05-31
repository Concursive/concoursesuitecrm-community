ALTER TABLE action_phase ADD COLUMN random BOOLEAN;
UPDATE action_phase set random=false;
ALTER TABLE action_phase ALTER COLUMN random SET DEFAULT false;
