/*
	Gatekeeper database tables.
	Assumes the user 'gatekeeper' is already created.
*/

CREATE TABLE sites (
  site_id SERIAL PRIMARY KEY,
  sitecode VARCHAR(255) NOT NULL,				
  vhost VARCHAR(255) NOT NULL DEFAULT '',	
  dbhost VARCHAR(255) NOT NULL DEFAULT '',	
  dbname VARCHAR(255) NOT NULL DEFAULT '',	
  dbport INT NOT NULL DEFAULT 5432,	
  dbuser VARCHAR(255) NOT NULL DEFAULT '',
  dbpw VARCHAR(255) NOT NULL DEFAULT '',
  driver VARCHAR(255) NOT NULL DEFAULT '',
  code VARCHAR(255),
  enabled BOOLEAN NOT NULL DEFAULT false
);

GRANT ALL ON sites TO gatekeeper;

