CREATE TABLE lookup_ticket_task_category (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

ALTER TABLE task ADD ticket_task_category_id INTEGER REFERENCES lookup_ticket_task_category(code);

