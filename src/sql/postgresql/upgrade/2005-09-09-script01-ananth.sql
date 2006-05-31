--Action Plan Work Notes
CREATE TABLE action_plan_work_notes (
  note_id SERIAL PRIMARY KEY,
  plan_work_id INTEGER NOT NULL REFERENCES action_plan_work(plan_work_id),
  description VARCHAR(300) NOT NULL,
  submitted TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

