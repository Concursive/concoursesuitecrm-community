/* Action list tables */
CREATE TABLE action_list (
  action_id INT IDENTITY PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  owner INT NOT NULL references access(user_id),
  completedate DATETIME,
  link_module_id INT NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT NOT NULL DEFAULT 1
);

CREATE TABLE action_item (
  item_id INT IDENTITY PRIMARY KEY,
  action_id INT NOT NULL references action_list(action_id),
  link_item_id INT NOT NULL,
  completedate DATETIME,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT NOT NULL DEFAULT 1
);


CREATE TABLE action_item_log (
  log_id INT IDENTITY PRIMARY KEY,
  item_id INT NOT NULL references action_item(item_id),
  link_item_id INT DEFAULT -1,
  type INT NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

/* Campaigns now have a fundamental type */
ALTER TABLE campaign ADD type INT DEFAULT 1;
UPDATE campaign SET type = 1;
