ALTER TABLE action_plan_constants ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_plan_constants SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_plan_constants ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE step_action_map ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE step_action_map SET entered = CURRENT_TIMESTAMP;

ALTER TABLE step_action_map ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE action_step_account_types ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_step_account_types SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_step_account_types ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE action_item_work_selection ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_item_work_selection SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_item_work_selection ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE ticket_category_plan_map ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE ticket_category_plan_map SET entered = CURRENT_TIMESTAMP;

ALTER TABLE ticket_category_plan_map ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE ticket_category_draft_plan_map ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE ticket_category_draft_plan_map SET entered = CURRENT_TIMESTAMP;

ALTER TABLE ticket_category_draft_plan_map ADD COLUMN modified TIMESTAMP NULL;
