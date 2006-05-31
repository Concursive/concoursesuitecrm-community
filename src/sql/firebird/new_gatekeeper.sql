
CREATE GENERATOR sites_site_id_seq;
CREATE TABLE sites (
  site_id      INTEGER                   NOT NULL,
  sitecode     VARCHAR(255)              NOT NULL,
  vhost        VARCHAR(255)  DEFAULT '' NOT NULL ,
  dbhost       VARCHAR(255)  DEFAULT '' NOT NULL ,
  dbname       VARCHAR(255)  DEFAULT '' NOT NULL ,
  dbport       INTEGER       DEFAULT 3456 NOT NULL ,
  dbuser       VARCHAR(255)  DEFAULT '' NOT NULL ,
  dbpw         VARCHAR(255)  DEFAULT '' NOT NULL ,
  driver       VARCHAR(255)  DEFAULT '' NOT NULL ,
  code         VARCHAR(255),
  enabled      CHAR(1)   DEFAULT 'N' NOT NULL,
  "language"   VARCHAR(11),
  PRIMARY KEY (SITE_ID)
);

CREATE GENERATOR events_event_id_seq;
CREATE TABLE events (
  event_id     INTEGER                   NOT NULL ,
  "second"     VARCHAR(64)  DEFAULT '0',
  "minute"     VARCHAR(64)  DEFAULT '*',
  "hour"       VARCHAR(64)  DEFAULT '*',
  dayofmonth   VARCHAR(64)  DEFAULT '*',
  "month"      VARCHAR(64)  DEFAULT '*',
  "dayofweek"    VARCHAR(64)  DEFAULT '*',
  "year"       VARCHAR(64)  DEFAULT '*',
  task         VARCHAR(255),
  extrainfo    VARCHAR(255),
  businessDays VARCHAR(6)   DEFAULT 'true',
  enabled         CHAR(1)       DEFAULT 'N',
  entered    TIMESTAMP      DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (EVENT_ID)
);

CREATE GENERATOR events_log_log_id_seq;
CREATE TABLE events_log (
  log_id       INTEGER NOT NULL,
  event_id     INTEGER  NOT NULL REFERENCES EVENTS(EVENT_ID) ,
  entered    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  status       INTEGER,
  "message"       BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (LOG_ID)
);