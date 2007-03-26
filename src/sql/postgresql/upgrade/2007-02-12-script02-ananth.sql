-- missing entered and modified columns

-- action_plan_category
ALTER TABLE action_plan_category ADD COLUMN entered TIMESTAMP(3);
UPDATE action_plan_category SET entered = CURRENT_TIMESTAMP;
ALTER TABLE action_plan_category ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE action_plan_category ALTER COLUMN entered SET NOT NULL;

ALTER TABLE action_plan_category ADD COLUMN modified TIMESTAMP(3);
UPDATE action_plan_category SET modified = CURRENT_TIMESTAMP;
ALTER TABLE action_plan_category ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE action_plan_category ALTER COLUMN modified SET NOT NULL;

-- action_plan_editor_lookup
ALTER TABLE action_plan_editor_lookup ADD COLUMN modified TIMESTAMP(3);
UPDATE action_plan_editor_lookup SET modified = CURRENT_TIMESTAMP;
ALTER TABLE action_plan_editor_lookup ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE action_plan_editor_lookup ALTER COLUMN modified SET NOT NULL;

-- action_phase
ALTER TABLE action_phase ADD COLUMN modified TIMESTAMP(3);
UPDATE action_phase SET modified = CURRENT_TIMESTAMP;
ALTER TABLE action_phase ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE action_phase ALTER COLUMN modified SET NOT NULL;

-- action_step
ALTER TABLE action_step ADD COLUMN modified TIMESTAMP(3);
UPDATE action_step SET modified = CURRENT_TIMESTAMP;
ALTER TABLE action_step ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE action_step ALTER COLUMN modified SET NOT NULL;

-- action_plan_work_notes
ALTER TABLE action_plan_work_notes ADD COLUMN entered TIMESTAMP(3);
UPDATE action_plan_work_notes SET entered = CURRENT_TIMESTAMP;
ALTER TABLE action_plan_work_notes ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE action_plan_work_notes ALTER COLUMN entered SET NOT NULL;

ALTER TABLE action_plan_work_notes ADD COLUMN modified TIMESTAMP(3);
UPDATE action_plan_work_notes SET modified = CURRENT_TIMESTAMP;
ALTER TABLE action_plan_work_notes ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE action_plan_work_notes ALTER COLUMN modified SET NOT NULL;

-- action_item_work_notes
ALTER TABLE action_item_work_notes ADD COLUMN entered TIMESTAMP(3);
UPDATE action_item_work_notes SET entered = CURRENT_TIMESTAMP;
ALTER TABLE action_item_work_notes ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE action_item_work_notes ALTER COLUMN entered SET NOT NULL;


ALTER TABLE action_item_work_notes ADD COLUMN modified TIMESTAMP(3);
UPDATE action_item_work_notes SET modified = CURRENT_TIMESTAMP;
ALTER TABLE action_item_work_notes ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE action_item_work_notes ALTER COLUMN modified SET NOT NULL;

-- action_step_lookup
ALTER TABLE action_step_lookup ADD COLUMN entered TIMESTAMP(3);
UPDATE action_step_lookup SET entered = CURRENT_TIMESTAMP;
ALTER TABLE action_step_lookup ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE action_step_lookup ALTER COLUMN entered SET NOT NULL;

ALTER TABLE action_step_lookup ADD COLUMN modified TIMESTAMP(3);
UPDATE action_step_lookup SET modified = CURRENT_TIMESTAMP;
ALTER TABLE action_step_lookup ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE action_step_lookup ALTER COLUMN modified SET NOT NULL;
