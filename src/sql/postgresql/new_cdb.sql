/**
 *  PostgreSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 19, 2002
 *@version    $Id$
 */

CREATE TABLE system_prefs (
  category VARCHAR(255) UNIQUE NOT NULL,
  data TEXT DEFAULT '' NOT NULL
);

INSERT INTO system_prefs (category, data) VALUES ('graphic', '/images/logo.gif');
INSERT INTO system_prefs (category, data) VALUES ('template', 'template0');
INSERT INTO system_prefs (category, data) VALUES ('css', 'template0');
INSERT INTO system_prefs (category, data) VALUES ('license', 'fdb61e1f82a9e32b89263c81db0115d5');

CREATE TABLE system_modules (
  code SERIAL PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

INSERT INTO system_modules (description) VALUES ('Account Management');
INSERT INTO system_modules (description) VALUES ('Contacts & Resources');


CREATE TABLE mod_log (
datetime			timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
,username			varchar(80) NOT NULL
,tablename			varchar(80) NOT NULL
,action				text
,record_id			text
);

CREATE TABLE org_type (
 org_type_code		char(2) NOT NULL DEFAULT 'CU' 
,org_type			text NOT NULL 
,order_id			int NOT NULL DEFAULT 0 
,PRIMARY KEY(org_type_code) 
);


CREATE TABLE lookup_contact_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

INSERT INTO lookup_contact_types (description) VALUES ('Employee');
INSERT INTO lookup_contact_types (description) VALUES ('Personal');
INSERT INTO lookup_contact_types (description) VALUES ('Sales');
INSERT INTO lookup_contact_types (description) VALUES ('Billing');
INSERT INTO lookup_contact_types (description) VALUES ('Technical');

CREATE TABLE lookup_account_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

INSERT INTO lookup_account_types (description) VALUES ('Customer');
INSERT INTO lookup_account_types (description) VALUES ('Competitor');
INSERT INTO lookup_account_types (description) VALUES ('Partner');
INSERT INTO lookup_account_types (description) VALUES ('Vendor');

CREATE TABLE account_type_levels (
  id int not null,
  type_id int not null,
  level INTEGER not null,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
)
;

CREATE TABLE state (
  state_code CHAR(2) PRIMARY KEY NOT NULL,
  state VARCHAR(80) NOT NULL
);


CREATE TABLE lookup_department (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL UNIQUE,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE config (
rec_id				serial
,name				varchar(80) NOT NULL 
,textkey			text 
,textvalue			text 
);

CREATE TABLE lookup_orgaddress_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;
INSERT INTO lookup_orgaddress_types (description) VALUES ('Primary');
INSERT INTO lookup_orgaddress_types (description) VALUES ('Auxiliary');
INSERT INTO lookup_orgaddress_types (description) VALUES ('Billing');
INSERT INTO lookup_orgaddress_types (description) VALUES ('Shipping');


CREATE TABLE lookup_orgemail_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

INSERT INTO lookup_orgemail_types (description) VALUES ('Primary');
INSERT INTO lookup_orgemail_types (description) VALUES ('Auxiliary');

CREATE TABLE note (
  id serial PRIMARY KEY,
  org_id int not null,
  contact_id int not null,
  opp_id int not null,
  subject varchar(80),
  body text,
  dateentered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  lastmodified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL, 
  enabled BOOLEAN DEFAULT true
)
;

CREATE TABLE lookup_orgphone_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;
INSERT INTO lookup_orgphone_types (description) VALUES ('Main');
INSERT INTO lookup_orgphone_types (description) VALUES ('Fax');


CREATE TABLE lookup_instantmessenger_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE TABLE lookup_employment_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_locale (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_contactaddress_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;
INSERT INTO lookup_contactaddress_types (description) VALUES ('Business');
INSERT INTO lookup_contactaddress_types (description) VALUES ('Home');
INSERT INTO lookup_contactaddress_types (description) VALUES ('Other');

CREATE TABLE lookup_contactemail_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;
INSERT INTO lookup_contactemail_types (description) VALUES ('Business');
INSERT INTO lookup_contactemail_types (description) VALUES ('Personal');
INSERT INTO lookup_contactemail_types (description) VALUES ('Other');


CREATE TABLE lookup_contactphone_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;
INSERT INTO lookup_contactphone_types (description) VALUES ('Business');
INSERT INTO lookup_contactphone_types (description) VALUES ('Business2');
INSERT INTO lookup_contactphone_types (description) VALUES ('Business Fax');
INSERT INTO lookup_contactphone_types (description) VALUES ('Home');
INSERT INTO lookup_contactphone_types (description) VALUES ('Home2');
INSERT INTO lookup_contactphone_types (description) VALUES ('Home Fax');
INSERT INTO lookup_contactphone_types (description) VALUES ('Mobile');
INSERT INTO lookup_contactphone_types (description) VALUES ('Pager');
INSERT INTO lookup_contactphone_types (description) VALUES ('Other');

CREATE TABLE access (
  user_id SERIAL PRIMARY KEY,
  username VARCHAR(80) NOT NULL, 
  password VARCHAR(80),
  contact_id INT DEFAULT -1,
  role_id INT DEFAULT -1,
  manager_id INT DEFAULT -1,
  startofday INTEGER DEFAULT 8,
  endofday INTEGER DEFAULT 18,
  locale VARCHAR(255),
  timezone VARCHAR(255) DEFAULT 'America/New_York',
  last_ip VARCHAR(15),
  last_login TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  expires date DEFAULT NULL,
  alias INT DEFAULT -1,
  assistant INT DEFAULT -1,
  enabled boolean NOT NULL DEFAULT true
);

DROP SEQUENCE access_user_id_seq;
CREATE SEQUENCE access_user_id_seq start 0 increment 1 maxvalue 2147483647 minvalue 0 cache 1 ;

CREATE TABLE role (
  role_id SERIAL PRIMARY KEY,
  role VARCHAR(80) NOT NULL,
  description VARCHAR(255) NOT NULL DEFAULT '',
  enteredby INT NOT NULL REFERENCES access,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled boolean NOT NULL DEFAULT true
);

CREATE TABLE permission_category (
  category_id SERIAL PRIMARY KEY,
  category VARCHAR(80),
  description VARCHAR(255),
  level INT NOT NULL DEFAULT 0,
  enabled boolean NOT NULL DEFAULT true,
  active boolean NOT NULL DEFAULT true
);

CREATE TABLE permission (
  permission_id SERIAL PRIMARY KEY,
  category_id INT NOT NULL REFERENCES permission_category,
  permission VARCHAR(80) NOT NULL,
  permission_view BOOLEAN NOT NULL DEFAULT false,
  permission_add BOOLEAN NOT NULL DEFAULT false,
  permission_edit BOOLEAN NOT NULL DEFAULT false,
  permission_delete BOOLEAN NOT NULL DEFAULT false,
  description VARCHAR(255) NOT NULL DEFAULT '',
  level INT NOT NULL DEFAULT 0,
  enabled BOOLEAN NOT NULL DEFAULT true,
  active BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE role_permission (
  id SERIAL PRIMARY KEY,
  role_id INT NOT NULL REFERENCES role,
  permission_id INT NOT NULL REFERENCES permission,
  role_view BOOLEAN NOT NULL DEFAULT false,
  role_add BOOLEAN NOT NULL DEFAULT false,
  role_edit BOOLEAN NOT NULL DEFAULT false,
  role_delete BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE organization (
  org_id serial PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  account_number VARCHAR(50),
  account_group INT,
  url TEXT,
  revenue FLOAT,
  employees INT,
  notes TEXT,
  type char(2), 
  industry_desc VARCHAR(80),
  ins_type VARCHAR(80),
  cust_status VARCHAR(80),
  sic_code VARCHAR(40),
  ticker_symbol VARCHAR(10) DEFAULT NULL,
  area CHAR(1),
  taxid CHAR(80),
  industry_code CHAR(2),
  lead VARCHAR(40),
  sales_rep int NOT NULL DEFAULT 0, 
  miner_only BOOLEAN NOT NULL DEFAULT 'f',
  defaultlocale INT,
  fiscalmonth INT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  enabled BOOLEAN DEFAULT true,
  industry_temp_code SMALLINT,
  owner INT NOT NULL,
  duplicate boolean default 'f',
  duplicate_id int default -1,
  custom1 int default -1,
  custom2 int default -1,
  contract_end date default null,
  alertdate date default null,
  alert varchar(100) default null,
  custom_data TEXT
);

DROP SEQUENCE organization_org_id_seq;
CREATE SEQUENCE organization_org_id_seq start 0 increment 1 maxvalue 2147483647 minvalue 0 cache 1 ;

CREATE TABLE lookup_stage (
  code SERIAL PRIMARY KEY,
  order_id INT,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE TABLE lookup_delivery_options (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

insert into lookup_delivery_options (description,level) values ('Email only',1);
insert into lookup_delivery_options (description,level) values ('Fax only',2);
insert into lookup_delivery_options (description,level) values ('Letter only',3);
insert into lookup_delivery_options (description,level) values ('Email then Fax',4);
insert into lookup_delivery_options (description,level) values ('Email then Letter',5);
insert into lookup_delivery_options (description,level) values ('Email, Fax, then Letter',6);


CREATE TABLE contact (
  contact_id serial PRIMARY KEY,
  user_id INT,
  org_id int REFERENCES organization,
  company VARCHAR(255),
  title VARCHAR(80),
  department INT,
  super INT REFERENCES contact,
  nameSalutation varchar(80),
  nameLast VARCHAR(80) NOT NULL,
  nameFirst VARCHAR(80) NOT NULL,
  nameMiddle VARCHAR(80),
  nameSuffix VARCHAR(80),
  assistant INT REFERENCES contact,
  birthdate DATE,
  type_id INT DEFAULT 0,
  notes TEXT,
  site INT,
  imName VARCHAR(30),
  imService INT,
  locale INT,
  employee_id varchar(80) UNIQUE,
  employmenttype INT,
  startofday VARCHAR(10),
  endofday VARCHAR(10),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  enabled BOOLEAN DEFAULT true,
  owner INT NOT NULL,
  custom1 int default -1,
  custom2 int default -1,
  custom_data TEXT,
  url VARCHAR(100)
);

CREATE INDEX "contact_user_id_idx" ON "contact" USING btree ("user_id");

CREATE TABLE lookup_call_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Phone Call', true, 10);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Fax', false, 20);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('In-Person', false, 30);

CREATE TABLE call_log (
  call_id SERIAL PRIMARY KEY,
  org_id INTEGER DEFAULT 0,
  contact_id INTEGER DEFAULT 0,
  opp_id INTEGER DEFAULT 0,
  call_type_id INTEGER,
  length INTEGER,
  subject VARCHAR(255),
  notes TEXT,
  followup_date DATE,
  alertdate DATE,
  followup_notes TEXT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE INDEX "call_log_cidx" ON "call_log" USING btree ("alertdate", "enteredby");

CREATE TABLE news (
  rec_id SERIAL PRIMARY KEY,
  org_id INT,
  url TEXT,
  base TEXT,
  headline TEXT,
  body TEXT,
  dateEntered DATE,
  type CHAR(1),
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE motd (
  motd_id SERIAL PRIMARY KEY,
  start_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP DEFAULT NULL,
  department_id INTEGER DEFAULT 0,
  headline TEXT,
  body TEXT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

create user gatekeeper nocreatedb nocreateuser;
grant select on access to gatekeeper;
grant insert on mod_log to gatekeeper;



CREATE TABLE organization_address (
  address_id SERIAL PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  address_type INT,
  addrline1 VARCHAR(80),
  addrline2 VARCHAR(80),
  city VARCHAR(80),
  state VARCHAR(80),
  country VARCHAR(80),
  postalcode VARCHAR(12),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
)
;

CREATE TABLE organization_emailaddress (
  emailaddress_id SERIAL PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  emailaddress_type INT,
  email VARCHAR(256),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
)
;

CREATE TABLE organization_phone (
  phone_id SERIAL PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  phone_type INT,
  number VARCHAR(20),
  extension VARCHAR(10),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
)
;

CREATE TABLE contact_address (
  address_id SERIAL PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  address_type INT,
  addrline1 VARCHAR(80),
  addrline2 VARCHAR(80),
  city VARCHAR(80),
  state VARCHAR(80),
  country VARCHAR(80),
  postalcode VARCHAR(12),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
)
;

CREATE TABLE contact_emailaddress (
  emailaddress_id SERIAL PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  emailaddress_type INT,
  email VARCHAR(256),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
)
;

CREATE TABLE contact_phone (
  phone_id SERIAL PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  phone_type INT,
  number VARCHAR(20),
  extension VARCHAR(10),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
)
;

CREATE TABLE notification (
  notification_id SERIAL PRIMARY KEY,
  notify_user INT NOT NULL,
  module VARCHAR(255) NOT NULL,
  item_id INT NOT NULL,
  item_modified TIMESTAMP,
  attempt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  notify_type VARCHAR(30),
  subject TEXT,
  message TEXT,
  result INT NOT NULL,
  errorMessage TEXT
);

CREATE TABLE cfsinbox_message (
  id serial PRIMARY KEY,
  subject VARCHAR(255) DEFAULT NULL,
  body TEXT NOT NULL,
  reply_id INT NOT NULL,
  enteredby INT NOT NULL,
  sent TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   type int not null default -1,
  modifiedby INT NOT NULL
);

CREATE TABLE cfsinbox_messagelink (
  id INT NOT NULL,
  sent_to INT NOT NULL,
  status INT NOT NULL DEFAULT 0,
  viewed TIMESTAMP DEFAULT NULL,
  enabled BOOLEAN NOT NULL DEFAULT 't',
  sent_from INT NOT NULL
);
  



