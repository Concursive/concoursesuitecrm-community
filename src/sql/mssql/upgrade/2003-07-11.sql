/* Implements the help/QA system for CFS */

DROP TABLE help_contents;

CREATE TABLE help_contents (
  help_id INT IDENTITY PRIMARY KEY,
  module VARCHAR(255) NOT NULL,
  section VARCHAR(255),
  subsection VARCHAR(255),
  description TEXT,
  enteredby INT NOT NULL REFERENCES access,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT DEFAULT 1
);

CREATE TABLE lookup_help_features (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(1000) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

CREATE TABLE help_features (
  feature_id INT IDENTITY PRIMARY KEY,
  link_help_id INT NOT NULL REFERENCES help_contents(help_id),
  link_feature_id INT REFERENCES lookup_help_features(code),
  description VARCHAR(1000) NOT NULL,
  enteredby INT NOT NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  completedate DATETIME,
  completedby INT REFERENCES access(user_id),
  enabled BIT NOT NULL DEFAULT 1
);

CREATE TABLE help_business_rules (
  rule_id INT IDENTITY PRIMARY KEY,
  link_help_id INT NOT NULL REFERENCES help_contents(help_id),
  description VARCHAR(1000) NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  completedate DATETIME,
  completedby INT REFERENCES access(user_id),
  enabled BIT NOT NULL DEFAULT 1
);

CREATE TABLE help_notes (
  note_id INT IDENTITY PRIMARY KEY,
  link_help_id INT NOT NULL REFERENCES help_contents(help_id),
  description VARCHAR(1000) NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  completedate DATETIME,
  completedby INT REFERENCES access(user_id),
  enabled BIT NOT NULL DEFAULT 1
);

CREATE TABLE help_tips (
  tip_id INT IDENTITY PRIMARY KEY,
  link_help_id INT NOT NULL REFERENCES help_contents(help_id),
  description VARCHAR(1000) NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT NOT NULL DEFAULT 1
);

update permission set permission_edit = 1 and where permission='help';
