/**
 *  PostgreSQL Table Creation
 *
 *@author     chris price
 *@created    April 10, 2002
 *@version    $Id$
 */
 
CREATE TABLE ticket_level (
  id serial
  ,level_code int NOT NULL PRIMARY KEY
  ,level VARCHAR(300) NOT NULL UNIQUE
  ,default_item BOOLEAN DEFAULT false
  ,enabled BOOLEAN DEFAULT true
);


CREATE TABLE ticket_severity (
  code serial PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,style text NOT NULL DEFAULT ''
  ,default_item BOOLEAN DEFAULT false
  ,level INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT true
);


CREATE TABLE lookup_ticketsource (
  code serial PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,default_item BOOLEAN DEFAULT false
  ,level INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT true
);


CREATE TABLE ticket_priority (
  code serial PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,style text NOT NULL DEFAULT '' 
  ,default_item BOOLEAN DEFAULT false
  ,level INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT true
);


CREATE TABLE ticket_source (
  source_code int NOT NULL PRIMARY KEY
  ,source VARCHAR(300) NOT NULL UNIQUE 
);


CREATE TABLE ticket_category ( 
  id serial PRIMARY KEY
  ,cat_level int  NOT NULL DEFAULT 0 
  ,parent_cat_code int  NOT NULL 
  ,description VARCHAR(300) NOT NULL 
  ,full_description text NOT NULL DEFAULT ''
  ,default_item BOOLEAN DEFAULT false
  ,level INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT true
);


CREATE TABLE ticket (
  ticketid SERIAL PRIMARY KEY,
  org_id INT NOT NULL REFERENCES organization, 
  contact_id INT, 
  problem TEXT NOT NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  closed TIMESTAMP,
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
  critical TIMESTAMP,
  notified TIMESTAMP,
  custom_data TEXT
);

CREATE INDEX "ticket_cidx" ON "ticket" USING btree ("assigned_to", "closed");

CREATE TABLE ticketlog (
  id serial
  ,ticketid int NOT NULL
  ,assigned_to int
  ,comment text
  ,closed BOOLEAN NOT NULL 
  ,pri_code int NOT NULL 
  ,level_code int NOT NULL 
  ,department_code int NOT NULL 
  ,cat_code int NOT NULL 
  ,scode int NOT NULL 
  ,entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,enteredby INT NOT NULL
  ,modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,modifiedby INT NOT NULL
);

