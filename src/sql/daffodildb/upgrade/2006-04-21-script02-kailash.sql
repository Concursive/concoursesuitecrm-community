CREATE SEQUENCE portfolio_cat_y_category_id_seq;
CREATE TABLE portfolio_category (
  category_id INTEGER NOT NULL,
  category_name VARCHAR(300) NOT NULL,
  category_description CLOB,
  category_position_id INTEGER REFERENCES portfolio_category(category_id),
  parent_category_id INTEGER REFERENCES portfolio_category(category_id),
  enabled BOOLEAN DEFAULT true NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (CATEGORY_ID)
);

CREATE SEQUENCE portfolio_item_item_id_seq;
CREATE TABLE portfolio_item (
  item_id INTEGER NOT NULL,
  item_name VARCHAR(300) NOT NULL,
  item_description CLOB,
  item_position_id INTEGER REFERENCES portfolio_item(item_id),
  image_id INTEGER REFERENCES project_files(item_id),
  caption VARCHAR(300),
  portfolio_category_id INTEGER REFERENCES portfolio_category(category_id),
  enabled BOOLEAN DEFAULT true NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ITEM_ID)
);
