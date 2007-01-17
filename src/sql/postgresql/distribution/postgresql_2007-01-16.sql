ALTER TABLE action_step ADD COLUMN display_in_plan_list BOOLEAN;
UPDATE action_step SET display_in_plan_list = false;
ALTER TABLE action_step ALTER COLUMN display_in_plan_list SET NOT NULL;
ALTER TABLE action_step ALTER COLUMN display_in_plan_list SET DEFAULT false;

ALTER TABLE action_step ADD COLUMN plan_list_label varchar(300);
