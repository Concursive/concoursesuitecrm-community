/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 19, 2002
 *@version    $Id$
 */

CREATE TABLE sites (
  site_id INT IDENTITY PRIMARY KEY,
  sitecode VARCHAR(255) NOT NULL,				
  vhost VARCHAR(255) NOT NULL DEFAULT '',	
  dbhost VARCHAR(255) NOT NULL DEFAULT '',	
  dbname VARCHAR(255) NOT NULL DEFAULT '',	
  dbport INT NOT NULL DEFAULT 1433,	
  dbuser VARCHAR(255) NOT NULL DEFAULT '',
  dbpw VARCHAR(255) NOT NULL DEFAULT '',
  driver VARCHAR(255) NOT NULL DEFAULT '',
  code VARCHAR(255),
  enabled BIT NOT NULL DEFAULT 0
);

