
CREATE SEQUENCE sites_site_id_seq;
CREATE TABLE sites (
  site_id INT PRIMARY KEY,
  sitecode VARCHAR(255) NOT NULL,				
  vhost VARCHAR(255)  DEFAULT '' NOT NULL ,	
  dbhost VARCHAR(255) DEFAULT '' NOT NULL ,	
  dbname VARCHAR(255)  DEFAULT '' NOT NULL ,	
  dbport INT  DEFAULT 3456 NOT NULL ,
  dbuser VARCHAR(255)  DEFAULT '' NOT NULL ,
  dbpw VARCHAR(255)  DEFAULT '' NOT NULL ,
  driver VARCHAR(255) DEFAULT '' NOT NULL ,
  code VARCHAR(255),
  enabled boolean DEFAULT false NOT NULL,
  "language" VARCHAR(11)
);

CREATE SEQUENCE events_event_id_seq;
CREATE TABLE events (
  event_id INT PRIMARY KEY,
  "second" VARCHAR(64) DEFAULT '0',
  "minute" VARCHAR(64) DEFAULT '*', 
  "hour" VARCHAR(64) DEFAULT '*',
  dayofmonth VARCHAR(64) DEFAULT '*',
  "month" VARCHAR(64) DEFAULT '*', 
  dayofweek VARCHAR(64) DEFAULT '*',
  year VARCHAR(64) DEFAULT '*',
  task VARCHAR(255),
  extrainfo VARCHAR(255),
  businessDays VARCHAR(6) DEFAULT 'true',
  enabled boolean DEFAULT false,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL 
);

CREATE SEQUENCE events_log_log_id_seq;
CREATE TABLE events_log (
  log_id INT PRIMARY KEY,
  event_id INTEGER REFERENCES events(event_id) NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  status INT,
  message CLOB
);

