-- Store the categories in the document store namely (e.g, documents, team,setup, etc.)

-- Old Name: lookup_document_store_permission_category_code_seq;
CREATE GENERATOR lookup_docume_category_code_seq;
--Name shortened from  lookup_document_store_permission_category
CREATE TABLE lookup_doc_store_perm_cat (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  group_id INTEGER DEFAULT 0 NOT NULL
);

-- Store the document store roles (e.g., owner, contributor, guest, etc.)
-- Old Name: lookup_document_store_role_code_seq;
CREATE GENERATOR lookup_docume_ore_role_code_seq;
CREATE TABLE lookup_document_store_role (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  group_id INTEGER DEFAULT 0 NOT NULL
);

-- Store the permissions in a document store (e.g., upload file, create version, create folder, download file, etc.)
-- Old Name: lookup_document_store_permission_code_seq;
CREATE GENERATOR lookup_docume_rmission_code_seq;
-- Name shortened from  lookup_document_store_permission
CREATE TABLE lookup_doc_store_perm (
  code INTEGER NOT NULL PRIMARY KEY,
  category_id INTEGER REFERENCES lookup_doc_store_perm_cat(code),
  permission VARCHAR(300) NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  group_id INTEGER DEFAULT 0 NOT NULL ,
  default_role INTEGER REFERENCES lookup_document_store_role(code)
);

-- Store the document store details
-- Old Name: document_store_document_store_id_seq;
CREATE GENERATOR document_stor_ment_store_id_seq;
CREATE TABLE document_store(
  document_store_id INTEGER NOT NULL PRIMARY KEY,
  template_id INTEGER,
  title VARCHAR(100) NOT NULL ,
  shortDescription VARCHAR(200) NOT NULL ,
  requestedBy VARCHAR(50)  ,
  requestedDept VARCHAR(50)  ,
  requestDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ,
  requestDate_timezone VARCHAR(255)  ,
  approvalDate TIMESTAMP ,
  approvalBy INTEGER  REFERENCES "access"(user_id),
  closeDate TIMESTAMP ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES "access"(user_id),
  trashed_date TIMESTAMP
);

-- Stores the mapping of document_store, lookup_document_store_role and lookup_document_store_permission
-- Old Name: document_store_permissions_id_seq;
CREATE GENERATOR document_stor_ermissions_id_seq;
CREATE TABLE document_store_permissions (
  id INTEGER NOT NULL PRIMARY KEY,
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  permission_id INTEGER NOT NULL REFERENCES lookup_doc_store_perm(code),
  userlevel INTEGER NOT NULL REFERENCES lookup_document_store_role(code)
);

-- Stores the scope of usage of the document stores for a user
CREATE TABLE document_store_user_member (
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  item_id INTEGER NOT NULL REFERENCES "access"(user_id),
  userlevel INTEGER NOT NULL REFERENCES lookup_document_store_role(code),
  status INTEGER ,
  last_accessed TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  site_id INTEGER REFERENCES lookup_site_id(code),
  role_type INTEGER
);


-- Stores the scope of usage of the document stores for a role

CREATE TABLE document_store_role_member (
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  item_id INTEGER NOT NULL REFERENCES "role"(role_id),
  userlevel INTEGER NOT NULL REFERENCES lookup_document_store_role(code),
  status INTEGER ,
  last_accessed TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  site_id INTEGER REFERENCES lookup_site_id(code),
  role_type INTEGER
);

-- Stores the scope of usage of the document stores for a department(i.e., members of a department)
-- Name shorten from - document_store_department_member
CREATE TABLE doc_store_depart_member (
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  item_id INTEGER NOT NULL REFERENCES lookup_department(code),
  userlevel INTEGER NOT NULL REFERENCES lookup_document_store_role(code),
  status INTEGER,
  last_accessed TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  site_id INTEGER REFERENCES lookup_site_id(code),
  role_type INTEGER
);

CREATE GENERATOR document_accounts_id_seq;
CREATE TABLE document_accounts (
  id INTEGER NOT NULL PRIMARY KEY,
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
