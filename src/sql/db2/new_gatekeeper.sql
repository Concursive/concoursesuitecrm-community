

CREATE SEQUENCE sites_site_id_seq AS DECIMAL(27,0);


CREATE TABLE sites(
    site_id INTEGER NOT NULL,
    sitecode VARGRAPHIC(255) NOT NULL,
    vhost VARGRAPHIC(255) DEFAULT NULL NOT NULL,
    dbhost VARGRAPHIC(255) DEFAULT NULL NOT NULL,
    dbname VARGRAPHIC(255) DEFAULT NULL NOT NULL,
    dbport INTEGER DEFAULT 3456 NOT NULL,
    dbuser VARGRAPHIC(255) DEFAULT NULL NOT NULL,
    dbpw VARGRAPHIC(255) DEFAULT NULL NOT NULL,
    driver VARGRAPHIC(255) DEFAULT NULL NOT NULL,
    code VARGRAPHIC(255),
    enabled CHAR(1) DEFAULT '0' NOT NULL,
    "language" VARGRAPHIC(11),
    PRIMARY KEY(site_id)
);


CREATE SEQUENCE events_event_id_seq AS DECIMAL(27,0);



CREATE TABLE events(
    event_id INTEGER NOT NULL,
    "second" VARGRAPHIC(64) DEFAULT G'0',
    "minute" VARGRAPHIC(64) DEFAULT G'*',
    "hour" VARGRAPHIC(64) DEFAULT G'*',
    dayofmonth VARGRAPHIC(64) DEFAULT G'*',
    "month" VARGRAPHIC(64) DEFAULT G'*',
    "dayofweek" VARGRAPHIC(64) DEFAULT G'*',
    "year" VARGRAPHIC(64) DEFAULT G'*',
    task VARGRAPHIC(255),
    extrainfo VARGRAPHIC(255),
    businessDays VARGRAPHIC(6) DEFAULT G'true',
    enabled CHAR(1) DEFAULT '0',
    entered    TIMESTAMP      DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(event_id)
);



CREATE SEQUENCE events_log_log_id_seq AS DECIMAL(27,0);



CREATE TABLE events_log(
    log_id INTEGER NOT NULL,
    event_id INTEGER NOT NULL  REFERENCES events(event_id),
    entered    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status INTEGER,
    "message" CLOB(2G) NOT LOGGED,
    PRIMARY KEY(log_id)
);

