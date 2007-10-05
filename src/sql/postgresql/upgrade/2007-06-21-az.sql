CREATE TABLE recent_items (
  item_id SERIAL PRIMARY KEY,
  link_module_id int NOT NULL,
  link_item_id int NOT NULL,
  url varchar(1000) NOT NULL,
  item_name varchar(255) NOT NULL,
  user_id int NOT NULL references access (user_id),
  entered timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);
