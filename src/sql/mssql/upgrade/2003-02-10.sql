/* gatekeeper database, additional tables */

CREATE TABLE events (
  event_id INT IDENTITY PRIMARY KEY,
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
  enabled BIT DEFAULT 0,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE events_log (
  log_id INT IDENTITY PRIMARY KEY,
  event_id INTEGER NOT NULL REFERENCES events(event_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status INT,
  message TEXT
);
