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

CREATE TRIGGER lookup_emailserver_types_entries BEFORE INSERT ON lookup_emailserver_types FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

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

CREATE TRIGGER lookup_emailaccount_inbox_behavior_entries BEFORE INSERT ON lookup_emailaccount_inbox_behavior FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);


--Email Account
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
  module_id INTEGER NOT NULL REFERENCES permission_category(constant),
  entered timestamp DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified timestamp,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  enabled BOOLEAN DEFAULT false,
  auto_associate BOOLEAN DEFAULT false,
  next_schedule TIMESTAMP NULL,
  server_type_name VARCHAR(32)
);

CREATE TRIGGER email_account_entries BEFORE INSERT ON email_account FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

--alter table cfsinbox_messagelink add  CONSTRAINT cfsinbox_messagelink_email_account_id_fkey FOREIGN KEY (email_account_id)
--      REFERENCES "email_account" (email_id) MATCH SIMPLE
--      ON UPDATE NO ACTION ON DELETE NO ACTION;

-- Email account user preferences table
CREATE TABLE email_account_user_preferences (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT REFERENCES `access` (user_id),
  email_account_id INT NOT NULL REFERENCES email_account (email_id)
);
