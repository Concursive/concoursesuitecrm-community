/* May 30, 2002  All PostgreSQL servers up-to-date */


/* June 3, 2002 */

DROP TABLE sync_map;
DROP SEQUENCE sync_map_map_id_seq;

CREATE TABLE sync_map (
  client_id INT NOT NULL,
  table_id INT NOT NULL,
  record_id INT NOT NULL,
  cuid VARCHAR(50) NOT NULL,
  complete BOOLEAN DEFAULT false,
  status_date TIMESTAMP
);

CREATE UNIQUE INDEX idx_sync_map ON sync_map (client_id, table_id, record_id);

vacuum;

/* June 6 2002 */

CREATE TABLE survey (
  id serial PRIMARY KEY,
  message_id int default -1,
  items_id int default -1,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  intro TEXT,
  itemLength int default -1,
  type int default -1,
  enabled BOOLEAN NOT NULL DEFAULT 't',
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE survey_item (
  id serial PRIMARY KEY,
  survey_id int default -1,
  type int default -1,
  description VARCHAR(255),
  enabled BOOLEAN NOT NULL DEFAULT 't'
);

CREATE TABLE lookup_survey_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

insert into lookup_survey_types (description) values ('Open-Ended');
insert into lookup_survey_types (description) values ('Quantitative (no comments)');
insert into lookup_survey_types (description) values ('Quantitative (with comments)');

alter table campaign add column survey_id int;

