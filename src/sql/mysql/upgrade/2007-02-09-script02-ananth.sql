-- missing id field in custom_field_data
-- TODO: Verify script on existing systems. Primary Key Data?

ALTER TABLE custom_field_data ADD COLUMN data_id INT AUTO_INCREMENT PRIMARY KEY;

-- missing id field in project_files_version

ALTER TABLE project_files_version ADD COLUMN version_id INT AUTO_INCREMENT PRIMARY KEY;

-- missing id field in project_files_download

ALTER TABLE project_files_download ADD COLUMN download_id INT AUTO_INCREMENT PRIMARY KEY;
