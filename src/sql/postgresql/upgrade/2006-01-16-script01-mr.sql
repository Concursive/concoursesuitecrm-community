ALTER TABLE action_plan_work_notes ADD COLUMN description2 VARCHAR(4096);
UPDATE action_plan_work_notes SET description2 = description;
ALTER TABLE action_plan_work_notes DROP COLUMN description;
ALTER TABLE action_plan_work_notes ADD COLUMN description VARCHAR(4096);
UPDATE action_plan_work_notes SET description = description2;
ALTER TABLE action_plan_work_notes DROP COLUMN description2;
