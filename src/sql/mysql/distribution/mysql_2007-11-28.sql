ALTER TABLE web_style ADD COLUMN active_style BOOLEAN DEFAULT false NOT NULL;
UPDATE web_style SET active_style = false WHERE custom = false; 
ALTER TABLE organization ADD COLUMN name_tmp VARCHAR(255);
UPDATE organization SET name_tmp = name;
ALTER TABLE organization DROP COLUMN name;
ALTER TABLE organization ADD COLUMN name VARCHAR(255);
UPDATE organization SET name = name_tmp;
ALTER TABLE organization MODIFY name VARCHAR(255) NOT NULL;
CREATE INDEX `orglist_name` ON `organization` (name);
ALTER TABLE organization DROP COLUMN name_tmp;
UPDATE permission SET description = 'Custom Editor' WHERE permission = 'qa';
ALTER TABLE organization ADD COLUMN no_phone BOOLEAN DEFAULT false;
ALTER TABLE organization ADD COLUMN no_fax BOOLEAN DEFAULT false;
ALTER TABLE organization ADD COLUMN no_mail BOOLEAN DEFAULT false;
ALTER TABLE organization ADD COLUMN no_email BOOLEAN DEFAULT false;
CREATE TRIGGER lookup_emailserver_types_entries BEFORE INSERT ON lookup_emailserver_types FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);
CREATE TRIGGER lookup_emailaccount_inbox_behavior_entries BEFORE INSERT ON lookup_emailaccount_inbox_behavior FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);
CREATE TRIGGER lookup_emailaccount_processing_behavior_entries BEFORE INSERT ON lookup_emailaccount_processing_behavior FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);
CREATE TRIGGER email_account_entries BEFORE INSERT ON email_account FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);
