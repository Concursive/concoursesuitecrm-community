ALTER TABLE action_step ADD COLUMN display_in_plan_list BOOLEAN DEFAULT FALSE NOT NULL;
ALTER TABLE action_step ADD COLUMN plan_list_label VARCHAR(300);
