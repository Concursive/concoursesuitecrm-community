/**
 *  PostgreSQL Table Creation
 *
 *@author     chris price
 *@created    April 10, 2002
 *@version    $Id$
 */
 
/* Not used, re-write as a lookup */
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
  org_id INT REFERENCES organization, 
  contact_id INT REFERENCES contact, 
  problem TEXT NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  closed TIMESTAMP,
  pri_code INT REFERENCES ticket_priority(code), 
  level_code INT,
  department_code INT REFERENCES lookup_department,
  source_code INT REFERENCES lookup_ticketsource(code), 
  cat_code INT,
  subcat_code1 INT,
  subcat_code2 INT,
  subcat_code3 INT,
  assigned_to int REFERENCES access,
  comment TEXT,
  solution TEXT,
  scode INT REFERENCES ticket_severity(code), 
  critical TIMESTAMP,
  notified TIMESTAMP,
  custom_data TEXT
);

CREATE INDEX "ticket_cidx" ON "ticket" USING btree ("assigned_to", "closed");

CREATE TABLE ticketlog (
  id serial
  ,ticketid int REFERENCES ticket(ticketid)
  ,assigned_to int REFERENCES access
  ,comment text
  ,closed BOOLEAN
  ,pri_code int NOT NULL REFERENCES ticket_priority(code)
  ,level_code int NOT NULL 
  ,department_code INT REFERENCES lookup_department
  ,cat_code INT
  ,scode INT REFERENCES ticket_severity(code)
  ,entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,enteredby INT NOT NULL REFERENCES access(user_id)
  ,modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,modifiedby INT NOT NULL REFERENCES access(user_id)
);

