ALTER TABLE action_plan_work_notes ADD submittedby INTEGER REFERENCES access(user_id);
UPDATE action_plan_work_notes SET submittedby = (SELECT assignedto FROM action_plan_work apw WHERE action_plan_work_notes.plan_work_id = apw.plan_work_id);
ALTER TABLE action_plan_work_notes ALTER COLUMN submittedby INTEGER NOT NULL;
