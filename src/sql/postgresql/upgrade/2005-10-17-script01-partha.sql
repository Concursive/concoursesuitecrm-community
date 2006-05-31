CREATE SEQUENCE lookup_ticket_task_cat_code_seq;
CREATE TABLE lookup_ticket_task_category (
  code INTEGER DEFAULT nextval('lookup_ticket_task_cat_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

ALTER TABLE task ADD COLUMN ticket_task_category_id INTEGER REFERENCES lookup_ticket_task_category(code);

