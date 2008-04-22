ALTER TABLE action_step ADD COLUMN quick_complete bool DEFAULT false;
UPDATE action_step SET quick_complete = false;

