/*
	Gatekeeper database tables.
	Assumes the user 'gatekeeper' is already created.
*/

CREATE TABLE sites (
  site_id SERIAL PRIMARY KEY,
  sitecode TEXT NOT NULL,				
  vhost TEXT NOT NULL DEFAULT '',	
  dbhost TEXT NOT NULL DEFAULT '',	
  dbname TEXT NOT NULL DEFAULT '',	
  dbport INT NOT NULL DEFAULT 5432,	
  dbuser TEXT NOT NULL DEFAULT '',
  dbpw TEXT NOT NULL DEFAULT '',
  driver TEXT NOT NULL DEFAULT '',
  code VARCHAR(255),
  enabled BOOLEAN NOT NULL DEFAULT false
);

GRANT ALL ON sites TO gatekeeper;

