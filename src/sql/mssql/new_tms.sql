/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 20, 2002
 *@version    $Id$
 */
 
CREATE TABLE ticket_level (
  id INT IDENTITY
  ,level_code int NOT NULL PRIMARY KEY
  ,level VARCHAR(300) NOT NULL UNIQUE
  ,default_item BIT DEFAULT 0
  ,enabled BIT DEFAULT 1
);


CREATE TABLE ticket_severity (
  code INT IDENTITY PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,style text NOT NULL DEFAULT ''
  ,default_item BIT DEFAULT 0
  ,level INTEGER DEFAULT 0
  ,enabled BIT DEFAULT 1
);


CREATE TABLE lookup_ticketsource (
  code INT IDENTITY PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,default_item BIT DEFAULT 0
  ,level INTEGER DEFAULT 0
  ,enabled BIT DEFAULT 1
);


CREATE TABLE ticket_priority (
  code INT IDENTITY PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,style text NOT NULL DEFAULT '' 
  ,default_item BIT DEFAULT 0
  ,level INTEGER DEFAULT 0
  ,enabled BIT DEFAULT 1
);


CREATE TABLE ticket_source (
  source_code int NOT NULL PRIMARY KEY
  ,source VARCHAR(300) NOT NULL UNIQUE 
);


CREATE TABLE ticket_category ( 
  id INT IDENTITY PRIMARY KEY
  ,cat_level int  NOT NULL DEFAULT 0 
  ,parent_cat_code int  NOT NULL 
  ,description VARCHAR(300) NOT NULL 
  ,full_description text NOT NULL DEFAULT ''
  ,default_item BIT DEFAULT 0
  ,level INTEGER DEFAULT 0
  ,enabled BIT DEFAULT 1
);


CREATE TABLE ticket (
  ticketid INT IDENTITY PRIMARY KEY,
  org_id INT NOT NULL REFERENCES organization, 
  contact_id INT, 
  problem TEXT NOT NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  closed DATETIME,
  pri_code INT NOT NULL DEFAULT -1, 
  level_code INT NOT NULL DEFAULT -1,
  department_code INT NOT NULL DEFAULT -1,
  source_code INT NOT NULL DEFAULT -1, 
  cat_code INT NOT NULL DEFAULT 0,
  subcat_code1 INT NOT NULL DEFAULT 0,
  subcat_code2 INT NOT NULL DEFAULT 0,
  subcat_code3 INT NOT NULL DEFAULT 0,
  assigned_to int default -1,
  comment TEXT,
  solution TEXT,
  scode INT NOT NULL DEFAULT -1, 
  critical DATETIME,
  notified DATETIME,
  custom_data TEXT
);

CREATE INDEX "ticket_cidx" ON "ticket" ("assigned_to", "closed");

CREATE TABLE ticketlog (
  id INT IDENTITY
  ,ticketid int NOT NULL
  ,assigned_to int
  ,comment text
  ,closed BIT NOT NULL 
  ,pri_code int NOT NULL 
  ,level_code int NOT NULL 
  ,department_code int NOT NULL 
  ,cat_code int NOT NULL 
  ,scode int NOT NULL 
  ,entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,enteredby INT NOT NULL
  ,modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,modifiedby INT NOT NULL
);

