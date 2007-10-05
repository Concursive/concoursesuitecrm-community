-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

CREATE TABLE lookup_revenue_types (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_revenue_types_entries BEFORE INSERT ON lookup_revenue_types FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_revenuedetail_types (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_revenuedetail_types_entries BEFORE INSERT ON lookup_revenuedetail_types FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE revenue (
  id INT AUTO_INCREMENT PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  transaction_id INT DEFAULT -1,
  month INT DEFAULT -1,
  year INT DEFAULT -1,
  amount FLOAT DEFAULT 0,
  type INT REFERENCES lookup_revenue_types(code),
  owner INT REFERENCES `access`(user_id),
  description VARCHAR(255),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL references `access`(user_id)
);

CREATE TRIGGER revenue_entries BEFORE INSERT ON revenue FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE revenue_detail (
  id INT AUTO_INCREMENT PRIMARY KEY,
  revenue_id INT REFERENCES revenue(id),
  amount FLOAT DEFAULT 0,
  type INT REFERENCES lookup_revenue_types(code),
  owner INT REFERENCES `access`(user_id),
  description VARCHAR(255),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL references `access`(user_id)
);

CREATE TRIGGER revenue_detail_entries BEFORE INSERT ON revenue_detail FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);
