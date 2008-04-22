-- TASK: "Offline Client"
-- NOTE: Added to new_cdb.sql 2006-11-09

ALTER TABLE permission ADD COLUMN permission_offline_view BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE permission ADD COLUMN permission_offline_add BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE permission ADD COLUMN permission_offline_edit BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE permission ADD COLUMN permission_offline_delete BOOLEAN NOT NULL DEFAULT false;

ALTER TABLE role_permission ADD COLUMN role_offline_view BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE role_permission ADD COLUMN role_offline_add BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE role_permission ADD COLUMN role_offline_edit BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE role_permission ADD COLUMN role_offline_delete BOOLEAN NOT NULL DEFAULT false;
