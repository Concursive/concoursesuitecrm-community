-- missing sync fields
ALTER TABLE project_files_download ADD COLUMN entered TIMESTAMP(3);
UPDATE project_files_download SET entered = CURRENT_TIMESTAMP;
ALTER TABLE project_files_download ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE project_files_download ALTER COLUMN entered SET NOT NULL;

ALTER TABLE project_files_download ADD COLUMN modified TIMESTAMP(3);
UPDATE project_files_download SET modified = CURRENT_TIMESTAMP;
ALTER TABLE project_files_download ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE project_files_download ALTER COLUMN modified SET NOT NULL;
