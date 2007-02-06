ALTER TABLE action_step ADD COLUMN quick_complete bool;
UPDATE action_step SET quick_complete = false;
ALTER TABLE action_step ALTER COLUMN quick_complete SET DEFAULT false;
