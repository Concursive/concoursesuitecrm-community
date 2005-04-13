/**
 *  PostgreSQL Table Creation
 *  Description: Tables required for the top level Documents module
 *
 *@author     kailash
 *@created    2004-11-04
 *@version    $Id$
 */


--
-- Store the categories in the document store namely (e.g, documents, team,setup, etc.)
--
CREATE TABLE lookup_document_store_permission_category (
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0
);

--
-- Store the document store roles (e.g., owner, contributor, guest, etc.)
--
CREATE TABLE lookup_document_store_role (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0
);

--
-- Store the permissions in a document store (e.g., upload file, create version, create folder, download file, etc.)
--
CREATE TABLE lookup_document_store_permission (
  code SERIAL PRIMARY KEY,
  category_id INTEGER REFERENCES lookup_document_store_permission_category(code),
  permission VARCHAR(300) UNIQUE NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  default_role INTEGER REFERENCES lookup_document_store_role(code)
);

--
-- Store the document store details
--
CREATE TABLE document_store(
  document_store_id SERIAL PRIMARY KEY,
  template_id INTEGER,
  title VARCHAR(100) NOT NULL ,
  shortDescription VARCHAR(200) NOT NULL ,
  requestedBy VARCHAR(50) NULL ,
  requestedDept VARCHAR(50) NULL ,
  requestDate TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP NULL ,
  requestDate_timezone VARCHAR(255) NULL ,
  approvalDate TIMESTAMP(3) NULL,
  approvalBy INTEGER NULL REFERENCES access(user_id),
  closeDate TIMESTAMP(3) NULL,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
);

--
-- Stores the mapping of document_store, lookup_document_store_role and lookup_document_store_permission
--
CREATE TABLE document_store_permissions (
  id SERIAL PRIMARY KEY,
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  permission_id INTEGER NOT NULL REFERENCES lookup_document_store_permission(code),
  userlevel INTEGER NOT NULL REFERENCES lookup_document_store_role(code)
);

--
-- Stores the scope of usage of the document stores for a user
--
CREATE TABLE document_store_user_member (
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  item_id INTEGER NOT NULL REFERENCES access(user_id),
  userlevel INTEGER NOT NULL REFERENCES lookup_document_store_role(code),
  status INTEGER NULL,
  last_accessed TIMESTAMP(3),
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id)
);

--
-- Stores the scope of usage of the document stores for a role
--
CREATE TABLE document_store_role_member (
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  item_id INTEGER NOT NULL REFERENCES role(role_id),
  userlevel INTEGER NOT NULL REFERENCES lookup_document_store_role(code),
  status INTEGER NULL,
  last_accessed TIMESTAMP(3),
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id)
);

--
-- Stores the scope of usage of the document stores for a department(i.e., members of a department)
--
CREATE TABLE document_store_department_member (
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  item_id INTEGER NOT NULL REFERENCES lookup_department(code),
  userlevel INTEGER NOT NULL REFERENCES lookup_document_store_role(code),
  status INTEGER NULL,
  last_accessed TIMESTAMP(3),
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id)
);



--I am assuming the folowing tables can be used for the documents module 
--1. project_files
--2. project_files_thumbnail
--3. project_folders
--4. project_files_download
--5. project_files_version

INSERT INTO lookup_document_store_role (description, default_item, level, enabled, group_id) VALUES ('Manager', false, 1, true, 1);
INSERT INTO lookup_document_store_role (description, default_item, level, enabled, group_id) VALUES ('Contributor level 3', false, 2, true, 1);
INSERT INTO lookup_document_store_role (description, default_item, level, enabled, group_id) VALUES ('Contributor level 2', false, 3, true, 1);
INSERT INTO lookup_document_store_role (description, default_item, level, enabled, group_id) VALUES ('Contributor level 1', false, 4, true, 1);
INSERT INTO lookup_document_store_role (description, default_item, level, enabled, group_id) VALUES ('Guest', false, 5, true, 1);

--Permissions
INSERT INTO lookup_document_store_permission_category (description,default_item, level, enabled, group_id) VALUES ('Document Store Details', false, 10, true, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (1, 'documentcenter-details-view', 'View document store details', false, 10, true, 1, 5);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (1, 'documentcenter-details-edit', 'Modify document store details', false, 20, true, 1, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (1, 'documentcenter-details-delete', 'Delete document store', false, 30, true, 1, 1);

INSERT INTO lookup_document_store_permission_category (description,default_item, level, enabled, group_id) VALUES ('Team Members', false, 20, true, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (2, 'documentcenter-team-view', 'View team members', false, 10, true, 1, 5);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (2, 'documentcenter-team-view-email', 'See team member email addresses', false, 20, true, 1, 4);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (2, 'documentcenter-team-edit', 'Modify team', false, 30, true, 1, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (2, 'documentcenter-team-edit-role', 'Modify team member role', false, 40, true, 1, 1);

INSERT INTO lookup_document_store_permission_category (description,default_item, level, enabled, group_id) VALUES ('Document Library', false, 80, true, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-view', 'View documents', false, 10, true, 1, 5);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-folders-add', 'Create folders', false, 20, true, 1, 2);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-folders-edit', 'Modify folders', false, 30, true, 1, 3);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-folders-delete', 'Delete folders', false, 40, true, 1, 2);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-files-upload', 'Upload files', false, 50, true, 1, 4);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-files-download', 'Download files', false, 60, true, 1, 5);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-files-rename', 'Rename files', false, 70, true, 1, 3);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-files-delete', 'Delete files', false, 80, true, 1, 2);

INSERT INTO lookup_document_store_permission_category (description,default_item, level, enabled, group_id) VALUES ('Setup', false, 90, true, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (4, 'documentcenter-setup-permissions', 'Configure document store permissions', false, 20, true, 1, 1);

