--Action Item Work Notes
CREATE TABLE action_item_work_notes (
  note_id SERIAL PRIMARY KEY,
  item_work_id INTEGER NOT NULL REFERENCES action_item_work(item_work_id),
  description VARCHAR(300) NOT NULL,
  submitted TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--Action Step lookup
CREATE TABLE action_step_lookup (
  code SERIAL PRIMARY KEY,
  step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  description VARCHAR(255) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

--Action Item Work Selection
CREATE TABLE action_item_work_selection (
  selection_id SERIAL PRIMARY KEY,
  item_work_id INTEGER NOT NULL REFERENCES action_item_work(item_work_id),
  selection INTEGER NOT NULL REFERENCES action_step_lookup(code)
);
