ALTER TABLE action_step ADD display_in_plan_list BOOLEAN DEFAULT FALSE NOT NULL;
ALTER TABLE action_step ADD plan_list_label VARCHAR(300);
