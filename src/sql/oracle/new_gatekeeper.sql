
CREATE SEQUENCE sites_site_id_seq;
CREATE TABLE sites (
  site_id      INTEGER                   NOT NULL,
  sitecode     NVARCHAR2(255)              NOT NULL,
  vhost        NVARCHAR2(255)  DEFAULT '' NOT NULL ,
  dbhost       NVARCHAR2(255)  DEFAULT '' NOT NULL ,
  dbname       NVARCHAR2(255)  DEFAULT '' NOT NULL ,
  dbport       INTEGER       DEFAULT 3456 NOT NULL ,
  dbuser       NVARCHAR2(255)  DEFAULT '' NOT NULL ,
  dbpw         NVARCHAR2(255)  DEFAULT '' NOT NULL ,
  driver       NVARCHAR2(255)  DEFAULT '' NOT NULL ,
  code         NVARCHAR2(255),
  enabled      CHAR(1)   DEFAULT 0 NOT NULL,
  "language"   NVARCHAR2(11),
  PRIMARY KEY (SITE_ID)
);

CREATE SEQUENCE events_event_id_seq;
CREATE TABLE events (
  event_id     INTEGER                   NOT NULL ,
  "second"     NVARCHAR2(64)  DEFAULT '0',
  "minute"     NVARCHAR2(64)  DEFAULT '*',
  "hour"       NVARCHAR2(64)  DEFAULT '*',
  dayofmonth   NVARCHAR2(64)  DEFAULT '*',
  "month"      NVARCHAR2(64)  DEFAULT '*',
  "dayofweek"    NVARCHAR2(64)  DEFAULT '*',
  "year"       NVARCHAR2(64)  DEFAULT '*',
  task         NVARCHAR2(255),
  extrainfo    NVARCHAR2(255),
  businessDays NVARCHAR2(6)   DEFAULT 'true',
  enabled         CHAR(1)       DEFAULT 0,
  entered    TIMESTAMP      DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (EVENT_ID)
);

CREATE SEQUENCE events_log_log_id_seq;
CREATE TABLE events_log (
  log_id       INTEGER NOT NULL,
  event_id     INTEGER  NOT NULL REFERENCES EVENTS(EVENT_ID) ,
  entered    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  status       INTEGER,
  "message"       CLOB,
  PRIMARY KEY (LOG_ID)
);
