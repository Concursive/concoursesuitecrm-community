/* This update goes into the gatekeeper database for cron to work */

CREATE TABLE events (
  event_id SERIAL PRIMARY KEY,
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
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

GRANT ALL ON events TO gatekeeper;

CREATE TABLE events_log (
  log_id SERIAL PRIMARY KEY,
  event_id INTEGER NOT NULL REFERENCES events(event_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status INT,
  message TEXT
);

GRANT ALL ON events_log TO gatekeeper;
