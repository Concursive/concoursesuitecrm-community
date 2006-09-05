UPDATE action_step SET action_id = null WHERE action_id = 110061037;
DELETE FROM step_action_map WHERE action_constant_id = 110061037;
DELETE FROM lookup_step_actions WHERE constant_id = 110061037;

UPDATE action_step SET action_id = null WHERE action_id = 110061032;
DELETE FROM step_action_map WHERE action_constant_id = 110061032;
DELETE FROM lookup_step_actions WHERE constant_id = 110061032;

update access set site_id = contact.site_id FROM contact WHERE access.user_id = contact.user_id;

CREATE TABLE document_accounts (
  id INT IDENTITY PRIMARY KEY,
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  entered DATETIME DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE project_files ADD allow_portal_access BIT DEFAULT 0;
UPDATE project_files SET allow_portal_access = 0;

ALTER TABLE project_team ADD role_type INTEGER;
ALTER TABLE document_store_user_member ADD role_type INTEGER;
ALTER TABLE document_store_role_member ADD role_type INTEGER;
ALTER TABLE document_store_department_member ADD role_type INTEGER;

ALTER TABLE project_files_version ADD allow_portal_access BIT DEFAULT 0;
UPDATE project_files_version SET allow_portal_access = 0;
