/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 19, 2002
 *@version    $Id$
 */
 
CREATE TABLE help_module (
  module_id INT IDENTITY PRIMARY KEY,
  category_id INT REFERENCES permission_category(category_id), 
  module_brief_description TEXT,
  module_detail_description TEXT
);

CREATE TABLE help_contents (
  help_id INT IDENTITY PRIMARY KEY,
  category_id INT REFERENCES permission_category(category_id), 
  link_module_id INT NULL REFERENCES help_module(module_id),
  module VARCHAR(255),
  section VARCHAR(255),
  subsection VARCHAR(255),
  title VARCHAR (255),
  description TEXT,
  nextcontent INT REFERENCES help_contents(help_id),
  prevcontent INT REFERENCES help_contents(help_id),
  upcontent INT REFERENCES help_contents(help_id),
  enteredby INT NOT NULL REFERENCES access,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT DEFAULT 1
);

CREATE TABLE help_tableof_contents (
  content_id INT IDENTITY PRIMARY KEY,
  displaytext VARCHAR (255),
  firstchild INT REFERENCES help_tableof_contents (content_id),
  nextsibling INT REFERENCES help_tableof_contents (content_id),
  parent INT REFERENCES help_tableof_contents (content_id),
  category_id INT REFERENCES permission_category(category_id),
  contentlevel INT NOT NULL,
  contentorder INT NOT NULL,
  enteredby INT NOT NULL REFERENCES access,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT DEFAULT 1
);


CREATE TABLE help_tableofcontentitem_links (
  link_id INT IDENTITY PRIMARY KEY,
  global_link_id INT NOT NULL REFERENCES help_tableof_contents(content_id),
  linkto_content_id INT NOT NULL REFERENCES help_contents(help_id),
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
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  completedate DATETIME,
  completedby INT REFERENCES access(user_id),
  enabled BIT NOT NULL DEFAULT 1,
  level INT DEFAULT 0
);

CREATE TABLE help_related_links (
  relatedlink_id INT IDENTITY PRIMARY KEY,
  owning_module_id INT REFERENCES  help_module(module_id),
  linkto_content_id INT REFERENCES help_contents(help_id),
  displaytext VARCHAR(255) NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT NOT NULL DEFAULT 1
);

CREATE TABLE help_faqs (
  faq_id INT IDENTITY PRIMARY KEY,
  owning_module_id INT NOT NULL REFERENCES help_module(module_id),
  question VARCHAR(1000) NOT NULL,
  answer VARCHAR(1000) NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
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
