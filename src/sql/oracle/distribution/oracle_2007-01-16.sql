ALTER TABLE action_step ADD display_in_plan_list CHAR(1) DEFAULT 0 NOT NULL;
ALTER TABLE action_step ADD plan_list_label NVARCHAR2(300);
