/* Adds action list tables */
CREATE SEQUENCE action_list_code_seq;
CREATE TABLE action_list (
  action_id INTEGER DEFAULT nextval('action_list_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  owner INT NOT NULL references access(user_id),
  completedate TIMESTAMP(3),
  link_module_id INT NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled boolean NOT NULL DEFAULT true
);

CREATE SEQUENCE action_item_code_seq;
CREATE TABLE action_item (
  item_id INTEGER DEFAULT nextval('action_item_code_seq') NOT NULL PRIMARY KEY,
  action_id INT NOT NULL references action_list(action_id),
  link_item_id INT NOT NULL,
  completedate TIMESTAMP(3),
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled boolean NOT NULL DEFAULT true
);


CREATE SEQUENCE action_item_log_code_seq;
CREATE TABLE action_item_log (
  log_id INTEGER DEFAULT nextval('action_item_log_code_seq') NOT NULL PRIMARY KEY,
  item_id INT NOT NULL references action_item(item_id),
  link_item_id INT DEFAULT -1,
  type INT NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

/* Campaigns now have a fundamental type */
ALTER TABLE campaign ADD COLUMN type INT;
ALTER TABLE campaign ALTER type SET DEFAULT 1;
UPDATE campaign SET type = 1;
