--Action Item Work Notes
CREATE TABLE action_item_work_notes (
  note_id INT IDENTITY PRIMARY KEY,
  item_work_id INTEGER NOT NULL REFERENCES action_item_work(item_work_id),
  description VARCHAR(300) NOT NULL,
  submitted DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--Action Step lookup
CREATE TABLE action_step_lookup (
  code INT IDENTITY PRIMARY KEY,
  step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  description VARCHAR(255) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

--Action Item Work Selection
CREATE TABLE action_item_work_selection (
  selection_id INT IDENTITY PRIMARY KEY,
  item_work_id INTEGER NOT NULL REFERENCES action_item_work(item_work_id),
  selection INTEGER NOT NULL REFERENCES action_step_lookup(code)
);
