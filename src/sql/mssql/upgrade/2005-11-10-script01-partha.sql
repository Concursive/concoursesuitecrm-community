CREATE TABLE ticket_category_draft_assignment (
  map_id INT IDENTITY PRIMARY KEY,
  category_id INTEGER NOT NULL REFERENCES ticket_category_draft(id),
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES access(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);

CREATE TABLE ticket_category_assignment (
  map_id INT IDENTITY PRIMARY KEY,
  category_id INTEGER NOT NULL REFERENCES ticket_category(id),
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES access(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);