-- Custom List Views Editor
CREATE TABLE custom_list_view_editor (
  editor_id INT IDENTITY PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  constant_id INTEGER NOT NULL,
  description TEXT,
  level INTEGER default 0,
  category_id INT NOT NULL
);
