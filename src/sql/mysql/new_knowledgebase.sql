-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

CREATE TABLE lookup_kb_status (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  constant_id INTEGER NOT NULL,
  entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp
);

CREATE TRIGGER lookup_kb_status_entries BEFORE INSERT ON lookup_kb_status FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Knowledge Base table
CREATE TABLE knowledge_base (
  kb_id INT AUTO_INCREMENT PRIMARY KEY,
  category_id INTEGER REFERENCES ticket_category(id),
  title VARCHAR(255) NOT NULL,
  description TEXT,
  item_id INTEGER REFERENCES project_files(item_id),
	entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
	modified TIMESTAMP NULL,
	modifiedby INT NOT NULL REFERENCES `access`(user_id),
	status INTEGER NOT NULL DEFAULT 1 REFERENCES lookup_kb_status(code),
	portal_access_allowed BOOLEAN NOT NULL DEFAULT false
);

CREATE TRIGGER knowledge_base_entries BEFORE INSERT ON knowledge_base FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);
