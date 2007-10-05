-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

-- Store the categories in the document store namely (e.g, documents, team,setup, etc.)
CREATE TABLE lookup_document_store_permission_category (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_document_store_permission_category_entries BEFORE INSERT ON  lookup_document_store_permission_category FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Store the document store roles (e.g., owner, contributor, guest, etc.)
CREATE TABLE lookup_document_store_role (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_document_store_role_entries BEFORE INSERT ON  lookup_document_store_role FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Store the permissions in a document store (e.g., upload file, create version, create folder, download file, etc.)
CREATE TABLE lookup_document_store_permission (
  code INT AUTO_INCREMENT PRIMARY KEY,
  category_id INTEGER REFERENCES lookup_document_store_permission_category(code),
  permission VARCHAR(255) UNIQUE NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  default_role INTEGER REFERENCES lookup_document_store_role(code),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_document_store_permission_entries BEFORE INSERT ON  lookup_document_store_permission FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Store the document store details
CREATE TABLE document_store(
  document_store_id INT AUTO_INCREMENT PRIMARY KEY,
  template_id INTEGER,
  title VARCHAR(100) NOT NULL ,
  shortDescription VARCHAR(200) NOT NULL ,
  requestedBy VARCHAR(50) NULL ,
  requestedDept VARCHAR(50) NULL ,
  requestDate TIMESTAMP NULL,
  requestDate_timezone VARCHAR(255) NULL ,
  approvalDate TIMESTAMP NULL,
  approvalBy INTEGER NULL REFERENCES `access`(user_id),
  closeDate TIMESTAMP NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INTEGER NOT NULL REFERENCES `access`(user_id),
  trashed_date TIMESTAMP NULL,
  public_store BOOLEAN DEFAULT false NOT NULL
);

CREATE TRIGGER document_store_entries BEFORE INSERT ON  document_store FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Stores the mapping of document_store, lookup_document_store_role and lookup_document_store_permission
CREATE TABLE document_store_permissions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  permission_id INTEGER NOT NULL REFERENCES lookup_document_store_permission(code),
  userlevel INTEGER NOT NULL REFERENCES lookup_document_store_role(code)
);

-- Stores the scope of usage of the document stores for a user
CREATE TABLE document_store_user_member (
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  item_id INTEGER NOT NULL REFERENCES `access`(user_id),
  userlevel INTEGER NOT NULL REFERENCES lookup_document_store_role(code),
  status INTEGER,
  last_accessed TIMESTAMP NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INTEGER NOT NULL REFERENCES `access`(user_id),
  site_id INTEGER REFERENCES lookup_site_id(code),
  role_type INTEGER
);

CREATE TRIGGER dcmnt_str_sr_mmbr_entries BEFORE INSERT ON  document_store_user_member FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

--
-- Stores the scope of usage of the document stores for a role
--
CREATE TABLE document_store_role_member (
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  item_id INTEGER NOT NULL REFERENCES role(role_id),
  userlevel INTEGER NOT NULL REFERENCES lookup_document_store_role(code),
  status INTEGER,
  last_accessed TIMESTAMP NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INTEGER NOT NULL REFERENCES `access`(user_id),
  site_id INTEGER REFERENCES lookup_site_id(code),
  role_type INTEGER
);

CREATE TRIGGER dcmnt_str_rl_mmbr_entries BEFORE INSERT ON  document_store_role_member FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Stores the scope of usage of the document stores for a department(i.e., members of a department)
CREATE TABLE document_store_department_member (
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  item_id INTEGER NOT NULL REFERENCES lookup_department(code),
  userlevel INTEGER NOT NULL REFERENCES lookup_document_store_role(code),
  status INTEGER,
  last_accessed TIMESTAMP NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INTEGER NOT NULL REFERENCES `access`(user_id),
  site_id INTEGER REFERENCES lookup_site_id(code),
  role_type INTEGER
);

CREATE TRIGGER dcmnt_str_dprtnt_mmbr_entries BEFORE INSERT ON  document_store_department_member FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE document_accounts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER document_accounts_entries BEFORE INSERT ON  document_accounts FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered);
