CREATE TABLE portfolio_category (
  category_id INT IDENTITY PRIMARY KEY,
  category_name VARCHAR(300) NOT NULL,
  category_description TEXT,
  category_position_id INT REFERENCES portfolio_category(category_id),
  parent_category_id INT REFERENCES portfolio_category(category_id),
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE portfolio_item (
  item_id INT IDENTITY PRIMARY KEY,
  item_name VARCHAR(300) NOT NULL,
  item_description TEXT,
  item_position_id INT REFERENCES portfolio_item(item_id),
  image_id INT REFERENCES project_files(item_id),
  caption VARCHAR(300),
  portfolio_category_id INT REFERENCES portfolio_category(category_id),
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);
