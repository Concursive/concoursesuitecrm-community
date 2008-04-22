
CREATE TABLE sites (
  site_id INT AUTO_INCREMENT PRIMARY KEY,
  sitecode VARCHAR(255) NOT NULL,
  vhost VARCHAR(255) NOT NULL DEFAULT '',
  dbhost VARCHAR(255) NOT NULL DEFAULT '',
  dbname VARCHAR(255) NOT NULL DEFAULT '',
  dbport INT NOT NULL DEFAULT 5432,
  dbuser VARCHAR(255) NOT NULL DEFAULT '',
  dbpw VARCHAR(255) NOT NULL DEFAULT '',
  driver VARCHAR(255) NOT NULL DEFAULT '',
  code VARCHAR(255),
  enabled BOOLEAN NOT NULL DEFAULT false,
  language VARCHAR(11)
);


CREATE TABLE events (
  event_id INT AUTO_INCREMENT PRIMARY KEY,
  second VARCHAR(64) DEFAULT '0',
  minute VARCHAR(64) DEFAULT '*',
  hour VARCHAR(64) DEFAULT '*',
  dayofmonth VARCHAR(64) DEFAULT '*',
  month VARCHAR(64) DEFAULT '*',
  dayofweek VARCHAR(64) DEFAULT '*',
  year VARCHAR(64) DEFAULT '*',
  task VARCHAR(255),
  extrainfo VARCHAR(255),
  businessDays VARCHAR(6) DEFAULT 'true',
  enabled BOOLEAN DEFAULT false,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER events_entries BEFORE INSERT ON  events FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered);

CREATE TABLE events_log (
  log_id INT AUTO_INCREMENT PRIMARY KEY,
  event_id INTEGER NOT NULL REFERENCES events(event_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status INT,
  message TEXT
);

CREATE TRIGGER events_log_entries BEFORE INSERT ON  events_log FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered);