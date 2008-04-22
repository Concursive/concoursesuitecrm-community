-- increasing the name field size to 255

ALTER TABLE organization ADD COLUMN name_tmp VARCHAR(255);
UPDATE organization SET name_tmp = name;
ALTER TABLE organization DROP COLUMN name;
ALTER TABLE organization ADD COLUMN name VARCHAR(255);
UPDATE organization SET name = name_tmp;
ALTER TABLE organization MODIFY name VARCHAR(255) NOT NULL;
CREATE INDEX `orglist_name` ON `organization` (name);
ALTER TABLE organization DROP COLUMN name_tmp;