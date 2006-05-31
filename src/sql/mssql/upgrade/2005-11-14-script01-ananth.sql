-- Custom List View
CREATE TABLE custom_list_view (
  view_id INT IDENTITY PRIMARY KEY,
  editor_id INTEGER NOT NULL REFERENCES custom_list_view_editor(editor_id),
  name VARCHAR(80) NOT NULL,
  description TEXT,
  is_default BIT DEFAULT 0,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Custom List View Field
CREATE TABLE custom_list_view_field (
  field_id INT IDENTITY PRIMARY KEY,
  view_id INTEGER NOT NULL REFERENCES custom_list_view(view_id),
  name VARCHAR(80) NOT NULL
);
