-- missing id field in custom_field_data
-- TODO: Verify script on existing systems. Primary Key Data?

CREATE SEQUENCE custom_field_data_data_id_seq;
ALTER TABLE custom_field_data ADD COLUMN data_id INTEGER DEFAULT nextval('custom_field_data_data_id_seq') NOT NULL PRIMARY KEY;

-- missing id field in project_files_version

CREATE SEQUENCE project_files_version_version_id_seq;
ALTER TABLE project_files_version ADD COLUMN version_id INTEGER DEFAULT nextval('project_files_version_version_id_seq') NOT NULL PRIMARY KEY;

-- missing id field in project_files_download

CREATE SEQUENCE project_files_download_download_id_seq;
ALTER TABLE project_files_download ADD COLUMN download_id INTEGER DEFAULT nextval('project_files_download_download_id_seq') NOT NULL PRIMARY KEY;
