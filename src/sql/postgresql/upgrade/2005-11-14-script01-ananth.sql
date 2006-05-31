-- Custom List View
CREATE TABLE custom_list_view (
  view_id SERIAL PRIMARY KEY,
  editor_id INT NOT NULL REFERENCES custom_list_view_editor(editor_id),
  name VARCHAR(80) NOT NULL,
  description TEXT,
  is_default BOOLEAN DEFAULT FALSE,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);

-- Custom List View Field
CREATE TABLE custom_list_view_field (
  field_id SERIAL PRIMARY KEY,
  view_id INT NOT NULL REFERENCES custom_list_view(view_id),
  name VARCHAR(80) NOT NULL
);
