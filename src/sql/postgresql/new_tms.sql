CREATE TABLE ticket_level (
 id serial
,level_code int NOT NULL PRIMARY KEY
,level text NOT NULL UNIQUE
,default_item BOOLEAN DEFAULT false
,enabled BOOLEAN DEFAULT true
);

INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (0,'Entry level','f','t');
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (1,'First level','f','t');
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (2,'Second level','f','t');
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (3,'Third level','f','t');
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (4,'Top level','f','t');

CREATE TABLE ticket_severity (
 code serial PRIMARY KEY
,description text NOT NULL UNIQUE
,style text NOT NULL DEFAULT ''
,default_item BOOLEAN DEFAULT false
,level INTEGER DEFAULT 0
,enabled BOOLEAN DEFAULT true
);

INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES ('Normal','background-color:lightgreen;colo
r:black;','f',0,'t');
INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES ('Important','background-color:yellow;color
:black;','f',1,'t');
INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES ('Critical','background-color:red;color:bla
ck;font-weight:bold;','f',2,'t');

CREATE TABLE lookup_ticketsource (
 code serial PRIMARY KEY
,description text NOT NULL UNIQUE
,default_item BOOLEAN DEFAULT false
,level INTEGER DEFAULT 0
,enabled BOOLEAN DEFAULT true
);

INSERT INTO lookup_ticketsource (level,description) VALUES (1,'Phone');
INSERT INTO lookup_ticketsource (level,description) VALUES (2,'Email');
INSERT INTO lookup_ticketsource (level,description) VALUES (3,'Letter');
INSERT INTO lookup_ticketsource (level,description) VALUES (4,'Other');

CREATE TABLE ticket_priority (
 code serial PRIMARY KEY
,description text NOT NULL UNIQUE
,style text NOT NULL DEFAULT '' 
,default_item BOOLEAN DEFAULT false
,level INTEGER DEFAULT 0
,enabled BOOLEAN DEFAULT true
);

INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES ('Scheduled','background-color:lightgreen;c
olor:black;','f',0,'t');
INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES ('Next','background-color:yellow;color:blac
k;','f',1,'t');
INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES ('Immediate','background-color:red;color:bl
ack;font-weight:bold;','f',2,'t');



CREATE TABLE ticket_source (
 source_code		int NOT NULL PRIMARY KEY
,source 		text NOT NULL UNIQUE 
);

INSERT INTO ticket_source (source_code,source) VALUES (0,'Phone');
INSERT INTO ticket_source (source_code,source) VALUES (1,'Email');
INSERT INTO ticket_source (source_code,source) VALUES (2,'System');
INSERT INTO ticket_source (source_code,source) VALUES (3,'Other');
INSERT INTO ticket_source (source_code,source) VALUES (4,'Message');

CREATE TABLE ticket_category ( 
 id serial PRIMARY KEY
,cat_level int  NOT NULL DEFAULT 0 
,parent_cat_code int  NOT NULL 
,description text NOT NULL 
,full_description text NOT NULL DEFAULT ''
,default_item BOOLEAN DEFAULT false
,level INTEGER DEFAULT 0
,enabled BOOLEAN DEFAULT true
);

INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Sales','','f',1,'t');
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Billing','','f',2,'t');
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Technical','','f',3,'t');
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Order','','f',4,'t');
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Other','','f',5,'t');

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
,closed bool NOT NULL 
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

