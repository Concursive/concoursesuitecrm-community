/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 19, 2002
 *@version    $Id$
 */
 
CREATE TABLE system_prefs (
  category VARCHAR(255) UNIQUE NOT NULL,
  data TEXT DEFAULT '' NOT NULL
);

INSERT INTO system_prefs (category, data) VALUES ('graphic', '/images/virginian-pilot.gif');
INSERT INTO system_prefs (category, data) VALUES ('template', 'template0');
INSERT INTO system_prefs (category, data) VALUES ('css', 'template0');
INSERT INTO system_prefs (category, data) VALUES ('license', 'fdb61e1f82a9e32b89263c81db0115d5');

CREATE TABLE system_modules (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

INSERT INTO system_modules (description) VALUES ('Account Management');
INSERT INTO system_modules (description) VALUES ('Contacts & Resources');


CREATE TABLE mod_log (
datetime			DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
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
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

INSERT INTO lookup_contact_types (description) VALUES ('Employee');
INSERT INTO lookup_contact_types (description) VALUES ('Personal');
INSERT INTO lookup_contact_types (description) VALUES ('Sales');
INSERT INTO lookup_contact_types (description) VALUES ('Billing');
INSERT INTO lookup_contact_types (description) VALUES ('Technical');

CREATE TABLE lookup_account_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

INSERT INTO lookup_account_types (description) VALUES ('Customer');
INSERT INTO lookup_account_types (description) VALUES ('Competitor');
INSERT INTO lookup_account_types (description) VALUES ('Partner');
INSERT INTO lookup_account_types (description) VALUES ('Vendor');

CREATE TABLE account_type_levels (
  id int not null,
  type_id int not null,
  level INTEGER not null,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
)
;

CREATE TABLE state (
  state_code CHAR(2) PRIMARY KEY NOT NULL,
  state VARCHAR(80) NOT NULL
);


CREATE TABLE lookup_department (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL UNIQUE,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

CREATE TABLE config (
rec_id				INT IDENTITY
,name				varchar(80) NOT NULL 
,textkey			text 
,textvalue			text 
);

CREATE TABLE lookup_orgaddress_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)
;
INSERT INTO lookup_orgaddress_types (description) VALUES ('Primary');
INSERT INTO lookup_orgaddress_types (description) VALUES ('Auxiliary');
INSERT INTO lookup_orgaddress_types (description) VALUES ('Billing');
INSERT INTO lookup_orgaddress_types (description) VALUES ('Shipping');


CREATE TABLE lookup_orgemail_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)
;

INSERT INTO lookup_orgemail_types (description) VALUES ('Primary');
INSERT INTO lookup_orgemail_types (description) VALUES ('Auxiliary');

CREATE TABLE note (
  id INT IDENTITY PRIMARY KEY,
  org_id int not null,
  contact_id int not null,
  opp_id int not null,
  subject varchar(80),
  body text,
  dateentered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  lastmodified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL, 
  enabled BIT DEFAULT 1
)
;

CREATE TABLE lookup_orgphone_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)
;
INSERT INTO lookup_orgphone_types (description) VALUES ('Main');
INSERT INTO lookup_orgphone_types (description) VALUES ('Fax');


CREATE TABLE lookup_instantmessenger_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)
;

CREATE TABLE lookup_employment_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

CREATE TABLE lookup_locale (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

CREATE TABLE lookup_contactaddress_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)
;
INSERT INTO lookup_contactaddress_types (description) VALUES ('Business');
INSERT INTO lookup_contactaddress_types (description) VALUES ('Home');
INSERT INTO lookup_contactaddress_types (description) VALUES ('Other');

CREATE TABLE lookup_contactemail_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)
;
INSERT INTO lookup_contactemail_types (description) VALUES ('Business');
INSERT INTO lookup_contactemail_types (description) VALUES ('Personal');
INSERT INTO lookup_contactemail_types (description) VALUES ('Other');


CREATE TABLE lookup_contactphone_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
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
  user_id INT IDENTITY(0,1) PRIMARY KEY,
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
  last_login DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  expires DATETIME DEFAULT NULL,
  alias INT DEFAULT -1,
  assistant INT DEFAULT -1,
  enabled BIT NOT NULL DEFAULT 1
);




CREATE TABLE role (
  role_id INT IDENTITY PRIMARY KEY,
  role VARCHAR(80) NOT NULL,
  description VARCHAR(255) NOT NULL DEFAULT '',
  enteredby INT NOT NULL REFERENCES access,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT NOT NULL DEFAULT 1
);

CREATE TABLE permission_category (
  category_id INT IDENTITY PRIMARY KEY,
  category VARCHAR(80),
  description VARCHAR(255),
  level INT NOT NULL DEFAULT 0,
  enabled BIT NOT NULL DEFAULT 1,
  active BIT NOT NULL DEFAULT 1
);

CREATE TABLE permission (
  permission_id INT IDENTITY PRIMARY KEY,
  category_id INT NOT NULL REFERENCES permission_category,
  permission VARCHAR(80) NOT NULL,
  permission_view BIT NOT NULL DEFAULT 0,
  permission_add BIT NOT NULL DEFAULT 0,
  permission_edit BIT NOT NULL DEFAULT 0,
  permission_delete BIT NOT NULL DEFAULT 0,
  description VARCHAR(255) NOT NULL DEFAULT '',
  level INT NOT NULL DEFAULT 0,
  enabled BIT NOT NULL DEFAULT 1,
  active BIT NOT NULL DEFAULT 1
);

CREATE TABLE role_permission (
  id INT IDENTITY PRIMARY KEY,
  role_id INT NOT NULL REFERENCES role,
  permission_id INT NOT NULL REFERENCES permission,
  role_view BIT NOT NULL DEFAULT 0,
  role_add BIT NOT NULL DEFAULT 0,
  role_edit BIT NOT NULL DEFAULT 0,
  role_delete BIT NOT NULL DEFAULT 0
);

CREATE TABLE organization (
  org_id INT IDENTITY(0,1) PRIMARY KEY,
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
  miner_only BIT NOT NULL DEFAULT 0,
  defaultlocale INT,
  fiscalmonth INT,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  enabled BIT DEFAULT 1,
  industry_temp_code SMALLINT,
  owner INT NOT NULL,
  duplicate BIT default 0,
  duplicate_id int default -1,
  custom1 int default -1,
  custom2 int default -1,
  contract_end DATETIME default null,
  alertdate DATETIME default null,
  alert varchar(100) default null,
  custom_data TEXT
);




CREATE TABLE lookup_stage (
  code INT IDENTITY PRIMARY KEY,
  order_id INT,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)
;

CREATE TABLE lookup_delivery_options (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)
;

insert into lookup_delivery_options (description,level) values ('Email only',1);
insert into lookup_delivery_options (description,level) values ('Fax only',2);
insert into lookup_delivery_options (description,level) values ('Letter only',3);
insert into lookup_delivery_options (description,level) values ('Email then Fax',4);
insert into lookup_delivery_options (description,level) values ('Email then Letter',5);
insert into lookup_delivery_options (description,level) values ('Email, Fax, then Letter',6);


CREATE TABLE contact (
  contact_id INT IDENTITY PRIMARY KEY,
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
  birthdate DATETIME,
  type_id INT DEFAULT 0,
  notes TEXT,
  site INT,
  imName VARCHAR(30),
  imService INT,
  locale INT,
  employee_id varchar(80),
  employmenttype INT,
  startofday VARCHAR(10),
  endofday VARCHAR(10),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  enabled BIT DEFAULT 1,
  owner INT NOT NULL,
  custom1 int default -1,
  custom2 int default -1,
  custom_data TEXT,
  url VARCHAR(100)
);

CREATE INDEX "contact_user_id_idx" ON "contact" ("user_id");

CREATE TABLE lookup_call_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)
;
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Phone Call', 1, 10);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Fax', 0, 20);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('In-Person', 0, 30);

CREATE TABLE call_log (
  call_id INT IDENTITY PRIMARY KEY,
  org_id INTEGER DEFAULT 0,
  contact_id INTEGER DEFAULT 0,
  opp_id INTEGER DEFAULT 0,
  call_type_id INTEGER,
  length INTEGER,
  subject VARCHAR(255),
  notes TEXT,
  followup_date DATETIME,
  alertdate DATETIME,
  followup_notes TEXT,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE INDEX "call_log_cidx" ON "call_log" ("alertdate", "enteredby");

CREATE TABLE news (
  rec_id INT IDENTITY PRIMARY KEY,
  org_id INT,
  url TEXT,
  base TEXT,
  headline TEXT,
  body TEXT,
  dateEntered DATETIME,
  type CHAR(1),
  created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE motd (
  motd_id INT IDENTITY PRIMARY KEY,
  start_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME DEFAULT NULL,
  department_id INTEGER DEFAULT 0,
  headline TEXT,
  body TEXT,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);





CREATE TABLE organization_address (
  address_id INT IDENTITY PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  address_type INT,
  addrline1 VARCHAR(80),
  addrline2 VARCHAR(80),
  city VARCHAR(80),
  state VARCHAR(80),
  country VARCHAR(80),
  postalcode VARCHAR(12),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
)
;

CREATE TABLE organization_emailaddress (
  emailaddress_id INT IDENTITY PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  emailaddress_type INT,
  email VARCHAR(256),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
)
;

CREATE TABLE organization_phone (
  phone_id INT IDENTITY PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  phone_type INT,
  number VARCHAR(20),
  extension VARCHAR(10),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
)
;

CREATE TABLE contact_address (
  address_id INT IDENTITY PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  address_type INT,
  addrline1 VARCHAR(80),
  addrline2 VARCHAR(80),
  city VARCHAR(80),
  state VARCHAR(80),
  country VARCHAR(80),
  postalcode VARCHAR(12),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
)
;

CREATE TABLE contact_emailaddress (
  emailaddress_id INT IDENTITY PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  emailaddress_type INT,
  email VARCHAR(256),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
)
;

CREATE TABLE contact_phone (
  phone_id INT IDENTITY PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  phone_type INT,
  number VARCHAR(20),
  extension VARCHAR(10),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
)
;

CREATE TABLE notification (
  notification_id INT IDENTITY PRIMARY KEY,
  notify_user INT NOT NULL,
  module VARCHAR(255) NOT NULL,
  item_id INT NOT NULL,
  item_modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  attempt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  notify_type VARCHAR(30),
  subject TEXT,
  message TEXT,
  result INT NOT NULL,
  errorMessage TEXT
);

CREATE TABLE cfsinbox_message (
  id INT IDENTITY PRIMARY KEY,
  subject VARCHAR(255) DEFAULT NULL,
  body TEXT NOT NULL,
  reply_id INT NOT NULL,
  enteredby INT NOT NULL,
  sent DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  type int not null default -1,
  modifiedby INT NOT NULL
);

CREATE TABLE cfsinbox_messagelink (
  id INT NOT NULL,
  sent_to INT NOT NULL,
  status INT NOT NULL DEFAULT 0,
  viewed DATETIME DEFAULT NULL,
  enabled BIT NOT NULL DEFAULT 1,
  sent_from INT NOT NULL
);
  

