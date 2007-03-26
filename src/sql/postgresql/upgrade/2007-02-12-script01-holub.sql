ALTER TABLE action_plan_constants ADD COLUMN entered TIMESTAMP(3);
UPDATE action_plan_constants SET entered = CURRENT_TIMESTAMP;
ALTER TABLE action_plan_constants ALTER COLUMN entered SET NOT NULL;
ALTER TABLE action_plan_constants ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE action_plan_constants ADD COLUMN modified TIMESTAMP(3);
UPDATE action_plan_constants SET modified = CURRENT_TIMESTAMP;
ALTER TABLE action_plan_constants ALTER COLUMN modified SET NOT NULL;
ALTER TABLE action_plan_constants ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE step_action_map ADD COLUMN entered TIMESTAMP(3);
UPDATE step_action_map SET entered = CURRENT_TIMESTAMP;
ALTER TABLE step_action_map ALTER COLUMN entered SET NOT NULL;
ALTER TABLE step_action_map ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE step_action_map ADD COLUMN modified TIMESTAMP(3);
UPDATE step_action_map SET modified = CURRENT_TIMESTAMP;
ALTER TABLE step_action_map ALTER COLUMN modified SET NOT NULL;
ALTER TABLE step_action_map ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE action_step_account_types ADD COLUMN entered TIMESTAMP(3);
UPDATE action_step_account_types SET entered = CURRENT_TIMESTAMP;
ALTER TABLE action_step_account_types ALTER COLUMN entered SET NOT NULL;
ALTER TABLE action_step_account_types ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE action_step_account_types ADD COLUMN modified TIMESTAMP(3);
UPDATE action_step_account_types SET modified = CURRENT_TIMESTAMP;
ALTER TABLE action_step_account_types ALTER COLUMN modified SET NOT NULL;
ALTER TABLE action_step_account_types ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE action_item_work_selection ADD COLUMN entered TIMESTAMP(3);
UPDATE action_item_work_selection SET entered = CURRENT_TIMESTAMP;
ALTER TABLE action_item_work_selection ALTER COLUMN entered SET NOT NULL;
ALTER TABLE action_item_work_selection ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE action_item_work_selection ADD COLUMN modified TIMESTAMP(3);
UPDATE action_item_work_selection SET modified = CURRENT_TIMESTAMP;
ALTER TABLE action_item_work_selection ALTER COLUMN modified SET NOT NULL;
ALTER TABLE action_item_work_selection ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE ticket_category_plan_map ADD COLUMN entered TIMESTAMP(3);
UPDATE ticket_category_plan_map SET entered = CURRENT_TIMESTAMP;
ALTER TABLE ticket_category_plan_map ALTER COLUMN entered SET NOT NULL;
ALTER TABLE ticket_category_plan_map ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE ticket_category_plan_map ADD COLUMN modified TIMESTAMP(3);
UPDATE ticket_category_plan_map SET modified = CURRENT_TIMESTAMP;
ALTER TABLE ticket_category_plan_map ALTER COLUMN modified SET NOT NULL;
ALTER TABLE ticket_category_plan_map ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE ticket_category_draft_plan_map ADD COLUMN entered TIMESTAMP(3);
UPDATE ticket_category_draft_plan_map SET entered = CURRENT_TIMESTAMP;
ALTER TABLE ticket_category_draft_plan_map ALTER COLUMN entered SET NOT NULL;
ALTER TABLE ticket_category_draft_plan_map ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE ticket_category_draft_plan_map ADD COLUMN modified TIMESTAMP(3);
UPDATE ticket_category_draft_plan_map SET modified = CURRENT_TIMESTAMP;
ALTER TABLE ticket_category_draft_plan_map ALTER COLUMN modified SET NOT NULL;
ALTER TABLE ticket_category_draft_plan_map ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
