-- Store the categories in the document store namely (e.g, documents, team,setup, etc.)
CREATE SEQUENCE lookup_docume_ategory_code_seq;
CREATE TABLE lookup_doc_store_perm_cat (
  code INT PRIMARY KEY,
  description NVARCHAR2(300) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  group_id INTEGER DEFAULT 0 NOT NULL 
);

-- Store the document store roles (e.g., owner, contributor, guest, etc.)
CREATE SEQUENCE lookup_docume_re_role_code_seq;
CREATE TABLE lookup_document_store_role (
  code INT PRIMARY KEY,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  group_id INTEGER DEFAULT 0  NOT NULL 
);

-- Store the permissions in a document store (e.g., upload file, create version, create folder, download file, etc.)
CREATE SEQUENCE lookup_docume_mission_code_seq;
CREATE TABLE lookup_doc_store_perm (
  code INT PRIMARY KEY,
  category_id INTEGER REFERENCES lookup_doc_store_perm_cat(code),
  permission NVARCHAR2(300) UNIQUE NOT NULL,
  description NVARCHAR2(300) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  group_id INTEGER DEFAULT 0 NOT NULL , 
  default_role INTEGER REFERENCES lookup_document_store_role(code)
);

-- Store the document store details
CREATE SEQUENCE document_stor_ent_store_id_seq;
CREATE TABLE document_store(
  document_store_id INT  PRIMARY KEY,
  template_id INTEGER,
  title NVARCHAR2(100) NOT NULL ,
  shortDescription NVARCHAR2(200) NOT NULL ,
  requestedBy NVARCHAR2(50)  ,
  requestedDept NVARCHAR2(50)  ,
  requestDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ,
  requestDate_timezone NVARCHAR2(255)  ,
  approvalDate TIMESTAMP ,
  approvalBy INTEGER  REFERENCES "access"(user_id),
  closeDate TIMESTAMP ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER REFERENCES "access"(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER REFERENCES "access"(user_id) NOT NULL,
  trashed_date TIMESTAMP
);

-- Stores the mapping of document_store, lookup_document_store_role and lookup_document_store_permission
CREATE SEQUENCE document_stor_rmissions_id_seq;
CREATE TABLE document_store_permissions (
  id INT PRIMARY KEY,
  document_store_id INTEGER REFERENCES document_store(document_store_id) NOT NULL ,
  permission_id INTEGER REFERENCES lookup_doc_store_perm(code) NOT NULL ,
  userlevel INTEGER REFERENCES lookup_document_store_role(code) NOT NULL 
);

-- Stores the scope of usage of the document stores for a user
CREATE TABLE document_store_user_member (
  document_store_id INTEGER REFERENCES document_store(document_store_id) NOT NULL ,
  item_id INTEGER REFERENCES "access"(user_id) NOT NULL ,
  userlevel INTEGER REFERENCES lookup_document_store_role(code) NOT NULL ,
  status INTEGER ,
  last_accessed TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER  REFERENCES "access"(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER REFERENCES "access"(user_id) NOT NULL,
  site_id INTEGER REFERENCES lookup_site_id(code),
  role_type INTEGER
);


-- Stores the scope of usage of the document stores for a role

CREATE TABLE document_store_role_member (
  document_store_id INTEGER REFERENCES document_store(document_store_id) NOT NULL,
  item_id INTEGER REFERENCES "role"(role_id) NOT NULL,
  userlevel INTEGER REFERENCES lookup_document_store_role(code) NOT NULL,
  status INTEGER ,
  last_accessed TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER REFERENCES "access"(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER REFERENCES "access"(user_id) NOT NULL,
  site_id INTEGER REFERENCES lookup_site_id(code),
  role_type INTEGER
);

-- Stores the scope of usage of the document stores for a department(i.e., members of a department)
CREATE TABLE doc_store_depart_member (
  document_store_id INTEGER REFERENCES document_store(document_store_id) NOT NULL ,
  item_id INTEGER REFERENCES lookup_department(code) NOT NULL ,
  userlevel INTEGER REFERENCES lookup_document_store_role(code) NOT NULL,
  status INTEGER ,
  last_accessed TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER REFERENCES "access"(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER  REFERENCES "access"(user_id) NOT NULL,
  site_id INTEGER REFERENCES lookup_site_id(code),
  role_type INTEGER
);

CREATE SEQUENCE document_accounts_id_seq;
CREATE TABLE document_accounts (
  id INTEGER PRIMARY KEY,
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
