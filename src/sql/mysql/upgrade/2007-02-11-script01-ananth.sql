-- missing sync fields
ALTER TABLE project_files_download MODIFY download_date TIMESTAMP NULL;
ALTER TABLE project_files_download ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE project_files_download SET entered = CURRENT_TIMESTAMP;

ALTER TABLE project_files_download ADD COLUMN modified TIMESTAMP NULL;
