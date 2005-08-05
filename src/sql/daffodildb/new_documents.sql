-- Store the categories in the document store namely (e.g, documents, team,setup, etc.)

CREATE SEQUENCE lookup_document_store_permission_category_code_seq;
CREATE TABLE lookup_document_store_permission_category (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  group_id INTEGER DEFAULT 0 NOT NULL 
);


-- Store the document store roles (e.g., owner, contributor, guest, etc.)

CREATE SEQUENCE lookup_document_store_role_code_seq;
CREATE TABLE lookup_document_store_role (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  group_id INTEGER DEFAULT 0  NOT NULL 
);

-- Store the permissions in a document store (e.g., upload file, create version, create folder, download file, etc.)

CREATE SEQUENCE lookup_document_store_permission_code_seq;
CREATE TABLE lookup_document_store_permission (
  code INT PRIMARY KEY,
  category_id INTEGER REFERENCES lookup_document_store_permission_category(code),
  permission VARCHAR(300) UNIQUE NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  group_id INTEGER DEFAULT 0 NOT NULL , 
  default_role INTEGER REFERENCES lookup_document_store_role(code)
);

-- Store the document store details
CREATE SEQUENCE document_store_document_store_id_seq;
CREATE TABLE document_store(
  document_store_id INT  PRIMARY KEY,
  template_id INTEGER,
  title VARCHAR(100) NOT NULL ,
  shortDescription VARCHAR(200) NOT NULL ,
  requestedBy VARCHAR(50)  ,
  requestedDept VARCHAR(50)  ,
  requestDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ,
  requestDate_timezone VARCHAR(255)  ,
  approvalDate TIMESTAMP ,
  approvalBy INTEGER  REFERENCES access(user_id),
  closeDate TIMESTAMP ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER REFERENCES access(user_id) NOT NULL,
  trashed_date TIMESTAMP
);

-- Stores the mapping of document_store, lookup_document_store_role and lookup_document_store_permission
CREATE SEQUENCE document_store_permissions_id_seq;
CREATE TABLE document_store_permissions (
  id INT PRIMARY KEY,
  document_store_id INTEGER REFERENCES document_store(document_store_id) NOT NULL ,
  permission_id INTEGER REFERENCES lookup_document_store_permission(code) NOT NULL ,
  userlevel INTEGER REFERENCES lookup_document_store_role(code) NOT NULL 
);


-- Stores the scope of usage of the document stores for a user

CREATE TABLE document_store_user_member (
  document_store_id INTEGER REFERENCES document_store(document_store_id) NOT NULL ,
  item_id INTEGER REFERENCES access(user_id) NOT NULL ,
  userlevel INTEGER REFERENCES lookup_document_store_role(code) NOT NULL ,
  status INTEGER ,
  last_accessed TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER  REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER REFERENCES access(user_id) NOT NULL 
);


-- Stores the scope of usage of the document stores for a role

CREATE TABLE document_store_role_member (
  document_store_id INTEGER REFERENCES document_store(document_store_id) NOT NULL,
  item_id INTEGER REFERENCES "role"(role_id) NOT NULL,
  userlevel INTEGER  REFERENCES lookup_document_store_role(code) NOT NULL,
  status INTEGER ,
  last_accessed TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER  REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER  REFERENCES access(user_id) NOT NULL
);


-- Stores the scope of usage of the document stores for a department(i.e., members of a department)

CREATE TABLE document_store_department_member (
  document_store_id INTEGER REFERENCES document_store(document_store_id) NOT NULL ,
  item_id INTEGER REFERENCES lookup_department(code) NOT NULL ,
  userlevel INTEGER REFERENCES lookup_document_store_role(code) NOT NULL,
  status INTEGER ,
  last_accessed TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER  REFERENCES access(user_id) NOT NULL 
);
