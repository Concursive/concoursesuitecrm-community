/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 19, 2002
 *@version    $Id$
 */

CREATE TABLE sites (
  site_id INT IDENTITY PRIMARY KEY,
  sitecode TEXT NOT NULL,				
  vhost TEXT NOT NULL DEFAULT '',	
  dbhost TEXT NOT NULL DEFAULT '',	
  dbname TEXT NOT NULL DEFAULT '',	
  dbport INT NOT NULL DEFAULT 1433,	
  dbuser TEXT NOT NULL DEFAULT '',
  dbpw TEXT NOT NULL DEFAULT '',
  driver TEXT NOT NULL DEFAULT '',
  code VARCHAR(255),
  enabled BIT NOT NULL DEFAULT 0
);

GRANT ALL ON sites TO gatekeeper;

