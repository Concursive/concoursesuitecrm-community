/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 19, 2002
 *@version    $Id$
 */

CREATE TABLE message (
  id INT IDENTITY PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  template_id INT,
  subject VARCHAR(255) DEFAULT NULL,
  body TEXT,
  reply_addr VARCHAR(100),
  url VARCHAR(100),
  img VARCHAR(80),  
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);
 
CREATE TABLE message_template (
  id INT IDENTITY PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  template_file varchar(80),
  num_imgs INT,
  num_urls INT,
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE campaign (
  id INT IDENTITY PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  list_id int,
  message_id int DEFAULT -1,
  reply_addr VARCHAR(255) DEFAULT NULL,
  subject VARCHAR(255) DEFAULT NULL,
  message TEXT DEFAULT NULL,
  status_id INT DEFAULT 0,
  status VARCHAR(255),
  active BIT DEFAULT 0,
  active_date DATETIME DEFAULT NULL,
  send_method_id INT DEFAULT -1 NOT NULL,
  inactive_date DATETIME DEFAULT NULL,
  approval_date DATETIME DEFAULT NULL,
  approvedby INT DEFAULT -1,
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);


CREATE TABLE saved_criterialist (
  id INT IDENTITY PRIMARY KEY,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
  id INT IDENTITY PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  search_id int,
  dynamic BIT NOT NULL DEFAULT 1,
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);
 

CREATE TABLE campaign_run (
  id INT IDENTITY PRIMARY KEY,
  campaign_id INTEGER NOT NULL DEFAULT -1,
  status INTEGER NOT NULL DEFAULT 0,
  run_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  total_contacts INTEGER DEFAULT 0,
  total_sent INTEGER DEFAULT 0,
  total_replied INTEGER DEFAULT 0,
  total_bounced INTEGER DEFAULT 0
);

CREATE TABLE scheduled_recipient (
  id INT IDENTITY PRIMARY KEY,
  campaign_id INT NOT NULL DEFAULT -1,
  contact_id INT NOT NULL,
  run_id INT DEFAULT -1,
  status_id INT DEFAULT 0,
  status VARCHAR(255),
  status_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  scheduled_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  sent_date DATETIME DEFAULT NULL,
  reply_date DATETIME DEFAULT NULL,
  bounce_date DATETIME DEFAULT NULL
);

CREATE TABLE excluded_recipient (
  id INT IDENTITY PRIMARY KEY,
  campaign_id INT NOT NULL DEFAULT -1,
  contact_id INT NOT NULL
);

CREATE TABLE campaign_list_groups (
  campaign_id int not null,
  group_id int not null
);
 
CREATE TABLE search_fields (
  id INT IDENTITY PRIMARY KEY,
  field varchar(80),
  description VARCHAR(255),
  searchable BIT NOT NULL DEFAULT 1,
  field_typeID int NOT NULL DEFAULT -1,
  table_name varchar(80),
  object_class varchar(80)
);

CREATE TABLE field_types (
  id INT IDENTITY PRIMARY KEY,
  data_typeID int NOT NULL DEFAULT -1,
  data_type VARCHAR(20),
  operator VARCHAR(50),
  display_text varchar(50)
);


