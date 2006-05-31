--Action Plan Work Notes
CREATE TABLE action_plan_work_notes (
  note_id INT IDENTITY PRIMARY KEY,
  plan_work_id INTEGER NOT NULL REFERENCES action_plan_work(plan_work_id),
  description VARCHAR(300) NOT NULL,
  submitted DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

