/*   3/25/2002  

  On the next update of ASPCFS.com, make sure to update the following
  in cfs2gk:
  
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_cfs' WHERE site_id = 1;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_cfs' WHERE site_id = 2;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_demo' WHERE site_id = 7;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_edit' WHERE site_id = 5;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_dhv' WHERE site_id = 6;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_sss' WHERE site_id = 4;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_vport' WHERE site_id = 8;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_partners' WHERE site_id = 9;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_insurance' WHERE site_id = 10;

*/

/* 4/5/2002 in the works */

DROP TABLE role;
DROP SEQUENCE role_role_id_seq;

DROP TABLE permission_category;
DROP SEQUENCE permission_cate_category_id_seq;

DROP TABLE permission;
DROP SEQUENCE permission_permission_id_seq;

DROP TABLE role_permission;
DROP SEQUENCE role_permission_id_seq;

CREATE TABLE role (
  role_id SERIAL PRIMARY KEY,
  role VARCHAR(80) NOT NULL,
  description VARCHAR(255) NOT NULL DEFAULT '',
  enteredby INT NOT NULL REFERENCES access,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled boolean NOT NULL DEFAULT true
);

CREATE TABLE permission_category (
  category_id SERIAL PRIMARY KEY,
  category VARCHAR(80),
  description VARCHAR(255),
  level INT NOT NULL DEFAULT 0,
  enabled boolean NOT NULL DEFAULT true,
  active boolean NOT NULL DEFAULT true
);

CREATE TABLE permission (
  permission_id SERIAL PRIMARY KEY,
  category_id INT NOT NULL REFERENCES permission_category,
  permission VARCHAR(80) NOT NULL,
  permission_view BOOLEAN NOT NULL DEFAULT false,
  permission_add BOOLEAN NOT NULL DEFAULT false,
  permission_edit BOOLEAN NOT NULL DEFAULT false,
  permission_delete BOOLEAN NOT NULL DEFAULT false,
  description VARCHAR(255) NOT NULL DEFAULT '',
  level INT NOT NULL DEFAULT 0,
  enabled BOOLEAN NOT NULL DEFAULT true,
  active BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE role_permission (
  id SERIAL PRIMARY KEY,
  role_id INT NOT NULL REFERENCES role,
  permission_id INT NOT NULL REFERENCES permission,
  role_view BOOLEAN NOT NULL DEFAULT false,
  role_add BOOLEAN NOT NULL DEFAULT false,
  role_edit BOOLEAN NOT NULL DEFAULT false,
  role_delete BOOLEAN NOT NULL DEFAULT false
);

INSERT INTO role (role, description, enteredby, modifiedby) VALUES ('Administrator', 'Performs system configuration and maintenance', 0, 0);
INSERT INTO role (role, description, enteredby, modifiedby) VALUES ('Default Role', 'Sample user role', 0, 0);

INSERT INTO permission_category (category, level) VALUES ('System', 10);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (1, 'globalitems-search', true, false, false, false, 'Access to Global Search', 10);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (1, 'globalitems-myitems', true, false, false, false, 'Access to My Items', 20);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (1, 'globalitems-recentitems', true, false, false, false, 'Access to Recent Items', 30);

INSERT INTO permission_category (category, level) VALUES ('My Home Page', 20);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (2, 'myhomepage', true, false, false, false, 'Access to My Home Page module', 10);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (2, 'myhomepage-dashboard', true, false, false, false, 'View Performance Dashboard', 20);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (2, 'myhomepage-miner', true, true, false, true, 'Industry News records', 30);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (2, 'myhomepage-profile', true, false, false, false, 'My Profile', 40);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (2, 'myhomepage-profile-personal', true, false, true, false, 'Personal Information', 50);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (2, 'myhomepage-profile-settings', true, false, true, false, 'Settings', 60);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (2, 'myhomepage-profile-password', false, false, true, false, 'Password', 70);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (2, 'myhomepage-inbox', true, false, false, false, 'My Inbox', 80);


INSERT INTO permission_category (category, level) VALUES ('Contacts & Resources', 30);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (3, 'contacts', true, false, false, false, 'Access to Contacts & Resources module', 10);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (3, 'contacts-external_contacts', true, true, true, true, 'External Contacts Records', 20);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (3, 'contacts-internal_contacts', true, true, true, true, 'Internal Contacts Records', 30);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (3, 'contacts-external_contacts-reports', true, true, false, true, 'Reports', 40);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (3, 'contacts-external_contacts-folders', true, true, true, false, 'Folders', 50);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (3, 'contacts-external_contacts-calls', true, true, true, true, 'Calls', 60);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (3, 'contacts-external_contacts-messages', true, false, false, false, 'Messages', 70);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (3, 'contacts-external_contacts-opportunities', true, true, true, true, 'Opportunities', 80);

INSERT INTO permission_category (category, level) VALUES ('Pipeline Management', 40);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (4, 'pipeline', true, false, false, false, 'Access to Pipeline Management module', 10);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (4, 'pipeline-opportunities', true, false, true, true, 'Opportunity Records', 20);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (4, 'pipeline-dashboard', true, false, false, false, 'Dashboard', 30);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (4, 'pipeline-reports', true, true, false, true, 'Reports', 40);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (4, 'pipeline-opportunities-calls', true, true, true, true, 'Calls', 50);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (4, 'pipeline-opportunities-documents', true, true, true, true, 'Documents', 60);

INSERT INTO permission_category (category, level) VALUES ('Account Management', 50);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (5, 'accounts', true, false, false, false, 'Access to Account Management module', 10);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (5, 'accounts-accounts', true, true, true, true, 'Accounts Records', 20);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (5, 'accounts-accounts-folders', true, true, true, false, 'Folders', 30);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (5, 'accounts-accounts-contacts', true, true, true, true, 'Contacts', 40);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (5, 'accounts-accounts-opportunities', true, true, true, true, 'Opportunities', 50);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (5, 'accounts-accounts-tickets', true, true, true, true, 'Tickets', 60);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (5, 'accounts-accounts-documents', true, true, true, true, 'Documents', 70);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (5, 'accounts-accounts-reports', true, true, false, true, 'Reports', 80);
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (5, 'accounts-dashboard', true, false, false, false, 'Dashboard', 90);



