/**
 *  PostgreSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 27, 2002
 *@version    $Id$
 */
 
CREATE TABLE message (
  id serial PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  template_id INT,
  subject VARCHAR(255) DEFAULT NULL,
  body TEXT,
  reply_addr VARCHAR(100),
  url VARCHAR(100),
  img VARCHAR(80),  
  enabled BOOLEAN NOT NULL DEFAULT 't',
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

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
  enabled BOOLEAN NOT NULL DEFAULT 't',
  average float default 0.00,
  total1 int default 0,
  total2 int default 0,
  total3 int default 0,
  total4 int default 0,
  total5 int default 0,
  total6 int default 0,
  total7 int default 0
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

CREATE TABLE message_template (
  id serial PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  template_file varchar(80),
  num_imgs INT,
  num_urls INT,
  enabled BOOLEAN NOT NULL DEFAULT 't',
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE campaign (
  id serial PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  list_id int,
  message_id int DEFAULT -1,
	reply_addr VARCHAR(255) DEFAULT NULL,
	subject VARCHAR(255) DEFAULT NULL,
	message TEXT DEFAULT NULL,
  status_id INT DEFAULT 0,
  status VARCHAR(255),
  active BOOLEAN DEFAULT false,
  active_date DATE DEFAULT NULL,
	send_method_id INT DEFAULT -1 NOT NULL,
  inactive_date DATE DEFAULT NULL,
  approval_date TIMESTAMP DEFAULT NULL,
  approvedby INT DEFAULT -1,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  survey_id int
);


CREATE TABLE saved_criterialist (
  id SERIAL PRIMARY KEY,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL,
  owner INTEGER NOT NULL,
  name VARCHAR(80) NOT NULL,
  contact_source INTEGER NOT NULL DEFAULT -1
);


CREATE TABLE saved_criteriaelement (
  id INTEGER NOT NULL,
  field INTEGER NOT NULL DEFAULT -1,
  operator VARCHAR(50) NOT NULL,
  operatorid INTEGER NOT NULL DEFAULT -1,
  value VARCHAR(80) NOT NULL
);

CREATE TABLE recipient_list (
  id serial PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  search_id int,
  dynamic BOOLEAN NOT NULL DEFAULT true,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);
 

CREATE TABLE campaign_run (
  id serial PRIMARY KEY,
  campaign_id INTEGER NOT NULL DEFAULT -1,
  status INTEGER NOT NULL DEFAULT 0,
  run_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  total_contacts INTEGER DEFAULT 0,
  total_sent INTEGER DEFAULT 0,
  total_replied INTEGER DEFAULT 0,
  total_bounced INTEGER DEFAULT 0
);

CREATE TABLE scheduled_recipient (
  id serial PRIMARY KEY,
  campaign_id INT NOT NULL DEFAULT -1,
  contact_id INT NOT NULL,
  run_id INT DEFAULT -1,
  status_id INT DEFAULT 0,
  status VARCHAR(255),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  scheduled_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  sent_date TIMESTAMP DEFAULT NULL,
  reply_date TIMESTAMP DEFAULT NULL,
  bounce_date TIMESTAMP DEFAULT NULL
);

CREATE TABLE excluded_recipient (
  id serial PRIMARY KEY,
  campaign_id INT NOT NULL DEFAULT -1,
  contact_id INT NOT NULL
);

CREATE TABLE campaign_list_groups (
  campaign_id int not null,
  group_id int not null
);

CREATE TABLE search_fields (
  id serial PRIMARY KEY,
  field varchar(80),
  description VARCHAR(255),
  searchable BOOLEAN NOT NULL DEFAULT 't',
  field_typeID int NOT NULL DEFAULT -1,
  table_name varchar(80),
  object_class varchar(80)
);

CREATE TABLE field_types (
  id serial PRIMARY KEY,
  data_typeID int NOT NULL DEFAULT -1,
	data_type VARCHAR(20),
  operator VARCHAR(50),
  display_text varchar(50)
);

CREATE TABLE survey_answer (
  id SERIAL primary key,
  question_id int not null,
  comments VARCHAR(100) default null,
  quant_ans int DEFAULT -1,
  text_ans VARCHAR(100) DEFAULT null,
  enteredby int not null
);


