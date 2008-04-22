--Email server types 

CREATE TABLE lookup_emailserver_types (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered timestamp DEFAULT CURRENT_TIMESTAMP,
  modified timestamp
);

--Email account inbox behavior

CREATE TABLE lookup_emailaccount_inbox_behavior (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered timestamp DEFAULT CURRENT_TIMESTAMP,
  modified timestamp
);


--Email account processing behavior

CREATE TABLE lookup_emailaccount_processing_behavior (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered timestamp DEFAULT CURRENT_TIMESTAMP,
  modified timestamp
);

ALTER TABLE permission_category ADD CONSTRAINT permission_category_constant_uk UNIQUE(constant);

ALTER TABLE permission_category ADD COLUMN email_accounts BOOLEAN DEFAULT false;

--Email account

CREATE TABLE email_account (
  email_id INT AUTO_INCREMENT PRIMARY KEY,
  server_type INTEGER NOT NULL REFERENCES lookup_emailserver_types(code),
  alias VARCHAR(255) NOT NULL,
  description TEXT,
  email_address VARCHAR(255) NOT NULL,
  account_password VARCHAR(255) NOT NULL,
  server_address VARCHAR(255) NOT NULL,
  inbox_behavior INTEGER NOT NULL REFERENCES lookup_emailaccount_inbox_behavior(code),
  imap_path_prefix VARCHAR(255),
  port INTEGER NOT NULL,
  `ssl` BOOLEAN DEFAULT false,
  include_domain BOOLEAN DEFAULT false,
  schedule INTEGER NOT NULL,
  processing_behavior INTEGER NOT NULL REFERENCES lookup_emailaccount_processing_behavior(code),
  module_id INTEGER NOT NULL REFERENCES permission_category(constant),
  entered timestamp DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified timestamp,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);
