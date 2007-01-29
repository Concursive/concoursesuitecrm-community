ALTER TABLE action_step ADD COLUMN display_in_plan_list CHAR(1) DEFAULT '0' NOT NULL;
ALTER TABLE action_step ADD COLUMN plan_list_label VARGRAPHIC(300);
