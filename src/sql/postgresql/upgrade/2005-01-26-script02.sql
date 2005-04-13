-- New column for default files
ALTER TABLE project_files ADD COLUMN default_file boolean;
ALTER TABLE project_files ALTER default_file SET DEFAULT false;
UPDATE project_files SET default_file = false;
