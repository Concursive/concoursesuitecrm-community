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
  group_id INTEGER NOT NULL DEFAULT 0
);

-- Store the document store roles (e.g., owner, contributor, guest, etc.)
CREATE TABLE lookup_document_store_role (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0
);

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
  default_role INTEGER REFERENCES lookup_document_store_role(code)
);

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
  trashed_date TIMESTAMP NULL
);

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

CREATE TABLE document_accounts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
