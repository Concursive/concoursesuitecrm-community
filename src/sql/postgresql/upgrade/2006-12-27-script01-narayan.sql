--Email server types
CREATE SEQUENCE lookup_emailserver_typ_code_seq;

CREATE TABLE lookup_emailserver_types (
  code INTEGER DEFAULT nextval('lookup_emailserver_typ_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--Email account inbox behavior
CREATE SEQUENCE lookup_emailaccount_in_code_seq;

CREATE TABLE lookup_emailaccount_inbox_behavior (
  code INTEGER DEFAULT nextval('lookup_emailaccount_in_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);


--Email account processing behavior
CREATE SEQUENCE lookup_emailaccount_pr_code_seq;

CREATE TABLE lookup_emailaccount_processing_behavior (
  code INTEGER DEFAULT nextval('lookup_emailaccount_pr_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE permission_category ADD CONSTRAINT permission_category_constant_uk UNIQUE(constant);

ALTER TABLE permission_category ADD COLUMN email_accounts BOOLEAN DEFAULT false;

--Email account

CREATE TABLE email_account (
  email_id SERIAL PRIMARY KEY,
  server_type INTEGER NOT NULL REFERENCES lookup_emailserver_types(code),
  alias VARCHAR(255) NOT NULL,
  description TEXT,
  email_address VARCHAR(255) NOT NULL,
  account_password VARCHAR(255) NOT NULL,
  server_address VARCHAR(255) NOT NULL,
  inbox_behavior INTEGER NOT NULL REFERENCES lookup_emailaccount_inbox_behavior(code),
  imap_path_prefix VARCHAR(255),
  port INTEGER NOT NULL,
  ssl BOOLEAN DEFAULT false,
  include_domain BOOLEAN DEFAULT false,
  schedule INTEGER NOT NULL,
  processing_behavior INTEGER NOT NULL REFERENCES lookup_emailaccount_processing_behavior(code),
  module_id INTEGER NOT NULL REFERENCES permission_category(constant),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);


--Lookup data
INSERT INTO lookup_emailserver_types(description) VALUES('POP3');
INSERT INTO lookup_emailserver_types(description) VALUES ('IMAP');

INSERT INTO lookup_emailaccount_processing_behavior(description) VALUES('Manual');
INSERT INTO lookup_emailaccount_processing_behavior(description) VALUES('Automatic');

INSERT INTO lookup_emailaccount_inbox_behavior(description) VALUES('Delete messages on server');
INSERT INTO lookup_emailaccount_inbox_behavior(description) VALUES('Leave messages on server');






