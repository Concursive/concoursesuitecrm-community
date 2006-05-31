ALTER TABLE action_plan_work_notes ADD description2 VARCHAR(4096);
UPDATE action_plan_work_notes SET description2 = description;
ALTER TABLE action_plan_work_notes DROP description;
ALTER TABLE action_plan_work_notes ADD description VARCHAR(4096);
UPDATE action_plan_work_notes SET description = description2;
ALTER TABLE action_plan_work_notes DROP description2;
