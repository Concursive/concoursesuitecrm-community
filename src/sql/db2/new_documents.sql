
CREATE SEQUENCE lookup_docume_ategory_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_doc_store_perm_cat(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    group_id INTEGER DEFAULT 0 NOT NULL
);

CREATE SEQUENCE lookup_docume_re_role_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_document_store_role(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    group_id INTEGER DEFAULT 0 NOT NULL
);


CREATE SEQUENCE lookup_docume_mission_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_doc_store_perm(
    code INTEGER NOT NULL  PRIMARY KEY,
    category_id INTEGER REFERENCES lookup_doc_store_perm_cat(code),
    permission VARGRAPHIC(300) NOT NULL  UNIQUE,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    group_id INTEGER DEFAULT 0 NOT NULL,
    default_role INTEGER REFERENCES lookup_document_store_role(code)
);

CREATE SEQUENCE document_stor_ent_store_id_seq AS DECIMAL(27,0);
CREATE TABLE document_store(
    document_store_id INTEGER NOT NULL  PRIMARY KEY,
    template_id INTEGER,
    title VARGRAPHIC(100) NOT NULL,
    shortDescription VARGRAPHIC(200) NOT NULL,
    requestedBy VARGRAPHIC(50),
    requestedDept VARGRAPHIC(50),
    requestDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    requestDate_timezone VARGRAPHIC(255),
    approvalDate TIMESTAMP,
    approvalBy INTEGER REFERENCES "access"(user_id),
    closeDate TIMESTAMP,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    trashed_date TIMESTAMP
);

CREATE SEQUENCE document_stor_rmissions_id_seq AS DECIMAL(27,0);
CREATE TABLE document_store_permissions(
    id INTEGER NOT NULL  PRIMARY KEY,
    document_store_id INTEGER NOT NULL  REFERENCES document_store(document_store_id),
    permission_id INTEGER NOT NULL  REFERENCES lookup_doc_store_perm(code),
    userlevel INTEGER NOT NULL  REFERENCES lookup_document_store_role(code)
);

CREATE TABLE document_store_user_member(
    document_store_id INTEGER NOT NULL  REFERENCES document_store(document_store_id),
    item_id INTEGER NOT NULL  REFERENCES "access"(user_id),
    userlevel INTEGER NOT NULL  REFERENCES lookup_document_store_role(code),
    status INTEGER,
    last_accessed TIMESTAMP,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    site_id INTEGER REFERENCES lookup_site_id(code),
    role_type int
);

CREATE TABLE document_store_role_member(
    document_store_id INTEGER NOT NULL  REFERENCES document_store(document_store_id),
    item_id INTEGER NOT NULL  REFERENCES "role"(role_id),
    userlevel INTEGER NOT NULL  REFERENCES lookup_document_store_role(code),
    status INTEGER,
    last_accessed TIMESTAMP,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    site_id INTEGER REFERENCES lookup_site_id(code),
    role_type int
);


CREATE TABLE doc_store_depart_member(
    document_store_id INTEGER NOT NULL  REFERENCES document_store(document_store_id),
    item_id INTEGER NOT NULL  REFERENCES lookup_department(code),
    userlevel INTEGER NOT NULL  REFERENCES lookup_document_store_role(code),
    status INTEGER,
    last_accessed TIMESTAMP,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    site_id INTEGER REFERENCES lookup_site_id(code),
    role_type int
);

CREATE SEQUENCE document_accounts_id_seq AS DECIMAL(27,0);
CREATE TABLE document_accounts (
  id INTEGER NOT NULL PRIMARY KEY,
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
