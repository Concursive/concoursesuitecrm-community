/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 20, 2002
 *@version    $Id$
 */
 
CREATE TABLE ticket_level (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL UNIQUE,
  default_item BIT DEFAULT 0,
  level INT DEFAULT 0,
  enabled BIT DEFAULT 1
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

CREATE TABLE ticket_category_draft (
  id INT IDENTITY PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int NOT NULL DEFAULT 0,
  parent_cat_code int NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description text NOT NULL DEFAULT '',
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);


CREATE TABLE ticket (
  ticketid INT IDENTITY PRIMARY KEY,
  org_id INT REFERENCES organization, 
  contact_id INT REFERENCES contact,
  problem TEXT NOT NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  closed DATETIME,
  pri_code INT REFERENCES ticket_priority(code), 
  level_code INT REFERENCES ticket_level(code),
  department_code INT REFERENCES lookup_department,
  source_code INT REFERENCES lookup_ticketsource(code),
  cat_code INT,
  subcat_code1 INT,
  subcat_code2 INT,
  subcat_code3 INT,
  assigned_to INT REFERENCES access,
  comment TEXT,
  solution TEXT,
  scode INT REFERENCES ticket_severity(code),
  critical DATETIME,
  notified DATETIME,
  custom_data TEXT
);

CREATE INDEX "ticket_cidx" ON "ticket" ("assigned_to", "closed");

CREATE TABLE ticketlog (
  id INT IDENTITY PRIMARY KEY
  ,ticketid INT REFERENCES ticket(ticketid)
  ,assigned_to INT REFERENCES access(user_id)
  ,comment TEXT
  ,closed BIT
  ,pri_code INT REFERENCES ticket_priority(code)
  ,level_code INT
  ,department_code INT REFERENCES lookup_department(code)
  ,cat_code INT
  ,scode INT REFERENCES ticket_severity(code)
  ,entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,enteredby INT NOT NULL REFERENCES access(user_id)
  ,modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,modifiedby INT NOT NULL REFERENCES access(user_id)
);

