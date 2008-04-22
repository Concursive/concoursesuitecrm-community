-- set start_date to current timestamp upon insert
DROP TRIGGER custom_field_category_entries;
CREATE TRIGGER custom_field_category_entries BEFORE INSERT ON custom_field_category FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified),
NEW.start_date = IF (NEW.start_date IS NULL OR NEW.start_date = '0000-00-00 00:00:00', NEW.entered, NEW.start_date);
DROP TRIGGER custom_field_group_entries;
CREATE TRIGGER custom_field_group_entries BEFORE INSERT ON custom_field_group FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified),
NEW.start_date = IF (NEW.start_date IS NULL OR NEW.start_date = '0000-00-00 00:00:00', NEW.entered, NEW.start_date);
DROP TRIGGER custom_field_info_entries;
CREATE TRIGGER custom_field_info_entries BEFORE INSERT ON custom_field_info FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified),
NEW.start_date = IF (NEW.start_date IS NULL OR NEW.start_date = '0000-00-00 00:00:00', NEW.entered, NEW.start_date);
DROP TRIGGER custom_field_lookup_entries;
CREATE TRIGGER custom_field_lookup_entries BEFORE INSERT ON custom_field_lookup FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified),
NEW.start_date = IF (NEW.start_date IS NULL OR NEW.start_date = '0000-00-00 00:00:00', NEW.entered, NEW.start_date);
