-- missing entered and modified columns

-- action_plan_category
ALTER TABLE action_plan_category ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_plan_category SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_plan_category ADD COLUMN modified TIMESTAMP NULL;

-- action_plan_editor_lookup
ALTER TABLE action_plan_editor_lookup ADD COLUMN modified TIMESTAMP NULL;

-- action_phase
ALTER TABLE action_phase ADD COLUMN modified TIMESTAMP NULL;

-- action_step
ALTER TABLE action_step ADD COLUMN modified TIMESTAMP NULL;

-- action_plan_work_notes
ALTER TABLE action_plan_work_notes MODIFY submitted TIMESTAMP NULL;
ALTER TABLE action_plan_work_notes ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_plan_work_notes SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_plan_work_notes ADD COLUMN modified TIMESTAMP NULL;

-- action_item_work_notes
ALTER TABLE action_item_work_notes MODIFY submitted TIMESTAMP NULL;
ALTER TABLE action_item_work_notes ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_item_work_notes SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_item_work_notes ADD COLUMN modified TIMESTAMP NULL;

-- action_step_lookup
ALTER TABLE action_step_lookup ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_step_lookup SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_step_lookup ADD COLUMN modified TIMESTAMP NULL;
