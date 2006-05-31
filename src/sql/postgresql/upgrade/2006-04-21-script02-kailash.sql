CREATE SEQUENCE portfolio_cat_y_category_id_seq;
CREATE TABLE portfolio_category (
  category_id INTEGER DEFAULT nextval('portfolio_cat_y_category_id_seq') NOT NULL PRIMARY KEY,
  category_name VARCHAR(300) NOT NULL,
  category_description TEXT,
  category_position_id INT REFERENCES portfolio_category(category_id),
  parent_category_id INT REFERENCES portfolio_category(category_id),
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE portfolio_item (
  item_id SERIAL PRIMARY KEY,
  item_name VARCHAR(300) NOT NULL,
  item_description TEXT,
  item_position_id INT REFERENCES portfolio_item(item_id),
  image_id INT REFERENCES project_files(item_id),
  caption VARCHAR(300),
  portfolio_category_id INT REFERENCES portfolio_category(category_id),
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);
