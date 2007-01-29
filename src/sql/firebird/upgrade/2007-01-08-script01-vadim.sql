ALTER TABLE action_step ADD display_in_plan_list CHAR(1) DEFAULT 'N' NOT NULL;
ALTER TABLE action_step ADD plan_list_label VARCHAR(300);
