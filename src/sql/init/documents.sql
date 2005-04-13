INSERT INTO lookup_document_store_role (description, default_item, level, enabled, group_id) VALUES ('Manager', @FALSE@, 1, @TRUE@, 1);
INSERT INTO lookup_document_store_role (description, default_item, level, enabled, group_id) VALUES ('Contributor level 3', @FALSE@, 2, @TRUE@, 1);
INSERT INTO lookup_document_store_role (description, default_item, level, enabled, group_id) VALUES ('Contributor level 2', @FALSE@, 3, @TRUE@, 1);
INSERT INTO lookup_document_store_role (description, default_item, level, enabled, group_id) VALUES ('Contributor level 1', @FALSE@, 4, @TRUE@, 1);
INSERT INTO lookup_document_store_role (description, default_item, level, enabled, group_id) VALUES ('Guest', @FALSE@, 5, @TRUE@, 1);

--Permissions
INSERT INTO lookup_document_store_permission_category (description,default_item, level, enabled, group_id) VALUES ('Document Store Details', @FALSE@, 10, @TRUE@, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (1, 'documentcenter-details-view', 'View document store details', @FALSE@, 10, @TRUE@, 1, 5);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (1, 'documentcenter-details-edit', 'Modify document store details', @FALSE@, 20, @TRUE@, 1, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (1, 'documentcenter-details-delete', 'Delete document store', @FALSE@, 30, @TRUE@, 1, 1);

INSERT INTO lookup_document_store_permission_category (description,default_item, level, enabled, group_id) VALUES ('Team Members', @FALSE@, 20, @TRUE@, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (2, 'documentcenter-team-view', 'View team members', @FALSE@, 10, @TRUE@, 1, 5);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (2, 'documentcenter-team-view-email', 'See team member email addresses', @FALSE@, 20, @TRUE@, 1, 4);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (2, 'documentcenter-team-edit', 'Modify team', @FALSE@, 30, @TRUE@, 1, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (2, 'documentcenter-team-edit-role', 'Modify team member role', @FALSE@, 40, @TRUE@, 1, 1);

INSERT INTO lookup_document_store_permission_category (description,default_item, level, enabled, group_id) VALUES ('Document Library', @FALSE@, 80, @TRUE@, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-view', 'View documents', @FALSE@, 10, @TRUE@, 1, 5);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-folders-add', 'Create folders', @FALSE@, 20, @TRUE@, 1, 2);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-folders-edit', 'Modify folders', @FALSE@, 30, @TRUE@, 1, 3);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-folders-delete', 'Delete folders', @FALSE@, 40, @TRUE@, 1, 2);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-files-upload', 'Upload files', @FALSE@, 50, @TRUE@, 1, 4);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-files-download', 'Download files', @FALSE@, 60, @TRUE@, 1, 5);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-files-rename', 'Rename files', @FALSE@, 70, @TRUE@, 1, 3);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-files-delete', 'Delete files', @FALSE@, 80, @TRUE@, 1, 2);

INSERT INTO lookup_document_store_permission_category (description,default_item, level, enabled, group_id) VALUES ('Setup', @FALSE@, 90, @TRUE@, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (4, 'documentcenter-setup-permissions', 'Configure document store permissions', @FALSE@, 20, @TRUE@, 1, 1);

