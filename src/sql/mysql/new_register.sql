-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

/* Tables for registration process */
CREATE TABLE registration (
  registration_id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(300),
  profile VARCHAR(300),
  name_first VARCHAR(300),
  name_last VARCHAR(300),
  company VARCHAR(300),
  registration_text VARCHAR(300),
  os_version VARCHAR(300),
  java_version VARCHAR(300),
  webserver VARCHAR(300),
  ip_address VARCHAR(20),
  ip_name VARCHAR(300),
  edition VARCHAR(300),
  crc TEXT,
  key_file TEXT,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL,
  key_hex TEXT
);

CREATE TRIGGER registration_entries BEFORE INSERT ON registration FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);
