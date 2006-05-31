ALTER TABLE action_phase_work ADD level INTEGER;
UPDATE action_phase_work set level=0 where level IS NULL;
ALTER TABLE action_phase_work ALTER COLUMN level SET DEFAULT 0;
ALTER TABLE action_item_work ADD level INTEGER;
UPDATE action_item_work set level=0 where level IS NULL;
ALTER TABLE action_item_work ALTER COLUMN level SET DEFAULT 0;
