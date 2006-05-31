-- Custom List Views Editor
CREATE TABLE custom_list_view_editor (
  editor_id SERIAL PRIMARY KEY,
  module_id INT NOT NULL REFERENCES permission_category(category_id),
  constant_id INT NOT NULL,
  description TEXT,
  level INT default 0,
  category_id INT NOT NULL
);
