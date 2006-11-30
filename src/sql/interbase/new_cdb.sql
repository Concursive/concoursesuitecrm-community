-- Functions for InterBase, they are included in the udf directory in every InterBase install
--/*****************************************
-- *
-- *	l o w e r
-- *
-- *****************************************
-- *
-- * Functional description:
-- *	Returns the input string into lower 
-- *	case characters.  Note: This function
-- *	will not work with international and 
-- *	non-ascii characters.
--  *	Note: This function is NOT limited to
--  *	receiving and returning only 80 characters,
--  *	rather, it can use as long as 32767 
--  * 	characters which is the limit on an 
--  *	INTERBASE character string.
--  *
--  *****************************************/

DECLARE EXTERNAL FUNCTION lower
CSTRING(256)
RETURNS CSTRING(256) FREE_IT
ENTRY_POINT 'IB_UDF_lower' MODULE_NAME 'ib_udf';

-- /*****************************************
--  *
--  *	s u b s t r
--  *
--  *****************************************
--  *
--  * Functional description:
--  *	substr(s,m,n) returns the substring 
--  *	of s which starts at position m and
--  *	ending at position n.
--  *	Note: This function is NOT limited to
--  *	receiving and returning only 80 characters,
--  *	rather, it can use as long as 32767 
--  * 	characters which is the limit on an 
--  *	INTERBASE character string.
--  *
--  *****************************************/

DECLARE EXTERNAL FUNCTION substr 
	CSTRING(80), SMALLINT, SMALLINT
	RETURNS CSTRING(80) FREE_IT
	ENTRY_POINT 'IB_UDF_substr' MODULE_NAME 'ib_udf';

CREATE GENERATOR lookup_site_id_code_seq;
CREATE TABLE lookup_site_id (
  code INT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  short_description VARCHAR(300),
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR access_user_id_seq;
SET GENERATOR access_user_id_seq TO -1;
CREATE TABLE "access" (
  user_id INTEGER  NOT NULL,
  username VARCHAR(80) NOT NULL,
  "password" VARCHAR(80),
  contact_id INTEGER DEFAULT -1,
  role_id INTEGER DEFAULT -1,
  manager_id INTEGER DEFAULT -1,
  startofday INTEGER DEFAULT 8,
  endofday INTEGER DEFAULT 18,
  locale VARCHAR(255),
  timezone VARCHAR(255) DEFAULT 'America/New_York',
  last_ip VARCHAR(15),
  last_login timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL,
  entered timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL,
  modified timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  expires timestamp DEFAULT NULL,
  alias INTEGER DEFAULT -1,
  assistant INTEGER DEFAULT -1,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  currency VARCHAR(5),
  "language" VARCHAR(20),
  webdav_password VARCHAR(80),
  hidden BOOLEAN DEFAULT FALSE,
  site_id INT REFERENCES lookup_site_id(code),
  allow_webdav_access BOOLEAN DEFAULT TRUE NOT NULL,
  allow_httpapi_access BOOLEAN DEFAULT TRUE NOT NULL,
  PRIMARY KEY (USER_ID)
);

CREATE GENERATOR lookup_sic_codes_code_seq;
CREATE TABLE lookup_sic_codes(
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER,
  enabled BOOLEAN DEFAULT TRUE,
  constant_id INTEGER NOT NULL UNIQUE 
);
-- gotta be not null before it is unique

CREATE GENERATOR lookup_industry_code_seq;
CREATE TABLE lookup_industry (
  code INTEGER NOT NULL,
  order_id INTEGER,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR access_log_id_seq;
CREATE TABLE access_log (
  id INTEGER  NOT NULL,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  username VARCHAR(80) NOT NULL,
  ip VARCHAR(15),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  browser VARCHAR(255),
  PRIMARY KEY (ID)
);


CREATE GENERATOR usage_log_usage_id_seq;
CREATE TABLE usage_log (
  usage_id INTEGER NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER ,
  "action" INTEGER NOT NULL,
  record_id INTEGER ,
  record_size INTEGER,
  PRIMARY KEY (USAGE_ID)
);

CREATE GENERATOR lookup_contact_types_code_seq;
CREATE TABLE lookup_contact_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  user_id INTEGER REFERENCES "access"(USER_ID) ,
  category INTEGER  DEFAULT 0 NOT NULL,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_account_types_code_seq;
CREATE TABLE lookup_account_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE TABLE state (
  state_code CHAR(2) NOT NULL,
  state VARCHAR(80) NOT NULL,
  PRIMARY KEY (STATE_CODE)
);


CREATE GENERATOR lookup_department_code_seq;
CREATE TABLE lookup_department (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_orgaddress_type_code_seq;
CREATE TABLE lookup_orgaddress_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_orgemail_types_code_seq;
CREATE TABLE lookup_orgemail_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_orgphone_types_code_seq;
CREATE TABLE lookup_orgphone_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_im_types_code_seq;
CREATE TABLE lookup_im_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_im_services_code_seq;
CREATE TABLE lookup_im_services (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);


CREATE GENERATOR lookup_contact_source_code_seq;
CREATE TABLE lookup_contact_source (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_contact_rating_code_seq;
CREATE TABLE lookup_contact_rating (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_textmessage_typ_code_seq;
CREATE TABLE lookup_textmessage_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);


CREATE GENERATOR lookup_employment_type_code_seq;
CREATE TABLE lookup_employment_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_locale_code_seq;
CREATE TABLE lookup_locale (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_contactaddress__code_seq;
CREATE TABLE lookup_contactaddress_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_contactemail_ty_code_seq;
CREATE TABLE lookup_contactemail_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_contactphone_ty_code_seq;
CREATE TABLE lookup_contactphone_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_access_types_code_seq;
CREATE TABLE lookup_access_types (
  code INTEGER NOT NULL,
  link_module_id INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER,
  enabled BOOLEAN DEFAULT TRUE,
  rule_id INTEGER NOT NULL,
  PRIMARY KEY (CODE)
);
create index laccess_types_rule_id on lookup_access_types (rule_id);

CREATE GENERATOR lookup_account_size_code_seq;
CREATE TABLE lookup_account_size (
  code INT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR lookup_segments_code_seq;
CREATE TABLE lookup_segments (
  code INT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR lookup_sub_segment_code_seq;
CREATE TABLE lookup_sub_segment (
  code INT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  segment_id  INT REFERENCES lookup_segments(code),
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR lookup_title_code_seq;
CREATE TABLE lookup_title (
  code INT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR organization_org_id_seq;
SET GENERATOR organization_org_id_seq TO -1;
CREATE TABLE organization (
  org_id INTEGER NOT NULL,
  name VARCHAR(80) NOT NULL,
  account_number VARCHAR(50),
  account_group INTEGER,
  url BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  revenue FLOAT,
  employees INTEGER,
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  ticker_symbol VARCHAR(10) ,
  taxid CHAR(80),
  lead VARCHAR(40),
  sales_rep INTEGER DEFAULT 0 NOT NULL ,
  miner_only BOOLEAN DEFAULT FALSE NOT NULL ,
  defaultlocale INTEGER,
  fiscalmonth INTEGER,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  industry_temp_code SMALLINT,
  owner INTEGER REFERENCES "access"(user_id),
  duplicate_id INTEGER default -1,
  custom1 INTEGER default -1,
  custom2 INTEGER default -1,
  contract_end TIMESTAMP ,
  alertdate TIMESTAMP ,
  alert varchar(100) ,
  custom_data BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  namesalutation varchar(80),
  namelast varchar(80),
  namefirst varchar(80),
  namemiddle varchar(80),
  namesuffix varchar(80),
  import_id INTEGER,
  status_id INTEGER,
  alertdate_timezone VARCHAR(255),
  contract_end_timezone VARCHAR(255),
  trashed_date TIMESTAMP,
  source INTEGER REFERENCES lookup_contact_source(code),
  rating INTEGER REFERENCES lookup_contact_rating(code),
  potential FLOAT,
  segment_id INT REFERENCES lookup_segments(code),
  sub_segment_id INT REFERENCES lookup_sub_segment(code),
  direct_bill BOOLEAN DEFAULT FALSE,
  account_size INT REFERENCES lookup_account_size(code),
  site_id INT REFERENCES lookup_site_id(code),
  duns_type VARCHAR(300),
  duns_number VARCHAR(30),
  business_name_two VARCHAR(300),
  sic_code INTEGER REFERENCES lookup_sic_codes(code),
  year_started INTEGER,
  sic_description VARCHAR(300),
  PRIMARY KEY (ORG_ID)
);

CREATE INDEX orglist_name ON organization ( name );

CREATE GENERATOR contact_contact_id_seq;
CREATE TABLE contact (
  contact_id INTEGER NOT NULL,
  user_id INTEGER REFERENCES "access"(user_id),
  org_id INTEGER REFERENCES organization(org_id),
  company VARCHAR(255),
  title VARCHAR(80),
  department INTEGER references lookup_department(code),
  super INTEGER ,
  namesalutation varchar(80),
  namelast VARCHAR(80) NOT NULL,
  namefirst VARCHAR(80) NOT NULL,
  namemiddle VARCHAR(80),
  namesuffix VARCHAR(80),
  assistant INTEGER,
  birthdate TIMESTAMP,
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  site INTEGER,
  locale INTEGER,
  employee_id varchar(80),
  employmenttype INTEGER,
  startofday VARCHAR(10),
  endofday VARCHAR(10),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  owner INTEGER REFERENCES "access"(user_id),
  custom1 INTEGER default -1,
  url VARCHAR(100),
  primary_contact BOOLEAN DEFAULT FALSE,
  employee BOOLEAN DEFAULT FALSE NOT NULL,
  org_name VARCHAR(255),
  access_type INTEGER REFERENCES lookup_access_types(code),
  status_id INTEGER,
  import_id INTEGER,
  information_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lead BOOLEAN DEFAULT FALSE,
  lead_status INTEGER,
  source INTEGER REFERENCES lookup_contact_source(code),
  rating INTEGER REFERENCES lookup_contact_rating(code),
  comments VARCHAR(255),
  conversion_date TIMESTAMP,
  additional_names VARCHAR(255),
  nickname VARCHAR(80),
  "role" VARCHAR(255),
  trashed_date TIMESTAMP,
  secret_word VARCHAR(255),
  account_number VARCHAR(50),
  revenue FLOAT,
  industry_temp_code INTEGER REFERENCES lookup_industry(code),
  potential FLOAT,
  no_email BOOLEAN DEFAULT FALSE,
  no_mail BOOLEAN DEFAULT FALSE,
  no_phone BOOLEAN DEFAULT FALSE,
  no_textmessage BOOLEAN DEFAULT FALSE,
  no_im BOOLEAN DEFAULT FALSE,
  no_fax BOOLEAN DEFAULT FALSE,
  site_id INTEGER REFERENCES lookup_site_id(code),
  assigned_date TIMESTAMP,
  lead_trashed_date TIMESTAMP,
  employees INTEGER,
  duns_type VARCHAR(300),
  duns_number VARCHAR(30),
  business_name_two VARCHAR(300),
  sic_code INTEGER REFERENCES lookup_sic_codes(code),
  year_started INTEGER,
  sic_description VARCHAR(300),
  PRIMARY KEY (CONTACT_ID)
);

-- THESE ARE REQUIRED - Firebird cannot reference itself (primary key) during table creation
ALTER TABLE CONTACT ADD CONSTRAINT FK_CONTACT_CONTACT_SUPER
  FOREIGN KEY (SUPER) REFERENCES CONTACT
  (CONTACT_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE CONTACT ADD CONSTRAINT FK_CONTACT_CONTACT_ASSIST
  FOREIGN KEY (ASSISTANT) REFERENCES CONTACT
  (CONTACT_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


CREATE INDEX contact_user_id_idx ON contact ( user_id );
CREATE INDEX contactlist_namecompany ON contact ( namelast, namefirst );
-- CREATE INDEX contactlist_namecompany ON contact ( namelast, namefirst, company );
-- CREATE INDEX contactlist_company ON contact ( company, namelast, namefirst );
CREATE INDEX contact_import_id_idx ON contact ( import_id );
CREATE INDEX contact_org_id_idx ON contact(org_id);
CREATE INDEX contact_islead_idx ON contact(lead);


-- Old Name: contact_lead_skipped_map_map_id_seq;
CREATE GENERATOR contact_lead__ed_map_map_id_seq;
CREATE TABLE contact_lead_skipped_map (
  map_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  contact_id INTEGER NOT NULL REFERENCES contact(contact_id),
  PRIMARY KEY (MAP_ID)
);

CREATE INDEX contact_lead_skip_u_idx ON contact_lead_skipped_map(user_id);


-- Old Name contact_lead_read_map_map_id_seq;
CREATE GENERATOR contact_lead__ad_map_map_id_seq;
CREATE TABLE contact_lead_read_map (
  map_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  contact_id INTEGER NOT NULL REFERENCES contact(contact_id),
  PRIMARY KEY (MAP_ID)
);

CREATE INDEX contact_lead_read_u_idx ON contact_lead_read_map(user_id);
CREATE INDEX contact_lead_read_c_idx ON contact_lead_read_map(contact_id);


CREATE GENERATOR role_role_id_seq;
CREATE TABLE "role" (
  role_id INTEGER  NOT NULL,
  "role" VARCHAR(80) NOT NULL,
  description VARCHAR(255) DEFAULT '' NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  role_type INTEGER,
  PRIMARY KEY (ROLE_ID)
);


CREATE GENERATOR permission_cate_category_id_seq;
CREATE TABLE permission_category (
  category_id INTEGER  NOT NULL,
  category VARCHAR(80),
  description VARCHAR(255),
  "level" INTEGER DEFAULT 0 NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  "active" BOOLEAN DEFAULT TRUE NOT NULL,
  folders BOOLEAN DEFAULT FALSE NOT NULL,
  lookups BOOLEAN DEFAULT FALSE NOT NULL,
  viewpoints BOOLEAN DEFAULT FALSE,
  categories BOOLEAN DEFAULT FALSE NOT NULL,
  scheduled_events BOOLEAN DEFAULT FALSE NOT NULL,
  object_events BOOLEAN DEFAULT FALSE NOT NULL,
  reports BOOLEAN DEFAULT FALSE NOT NULL,
  webdav BOOLEAN DEFAULT FALSE NOT NULL,
  logos BOOLEAN DEFAULT FALSE NOT NULL,
  constant INTEGER NOT NULL,
  action_plans BOOLEAN DEFAULT FALSE NOT NULL,
  custom_list_views BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (CATEGORY_ID)
);


CREATE GENERATOR permission_permission_id_seq;
CREATE TABLE permission (
  permission_id INTEGER  NOT NULL,
  category_id INTEGER NOT NULL REFERENCES permission_category,
  permission VARCHAR(80) NOT NULL,
  permission_view BOOLEAN DEFAULT FALSE NOT NULL,
  permission_add BOOLEAN DEFAULT FALSE NOT NULL,
  permission_edit BOOLEAN DEFAULT FALSE NOT NULL,
  permission_delete BOOLEAN DEFAULT FALSE NOT NULL,
  description VARCHAR(255)  DEFAULT '' NOT NULL,
  "level" INTEGER DEFAULT 0 NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  "active" BOOLEAN DEFAULT TRUE NOT NULL,
  viewpoints BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (PERMISSION_ID)
);


CREATE GENERATOR role_permission_id_seq;
CREATE TABLE role_permission (
  id INTEGER  NOT NULL,
  role_id INTEGER NOT NULL REFERENCES "role"(role_id),
  permission_id INTEGER NOT NULL REFERENCES permission(permission_id),
  role_view BOOLEAN DEFAULT FALSE NOT NULL,
  role_add BOOLEAN DEFAULT FALSE NOT NULL,
  role_edit BOOLEAN DEFAULT FALSE NOT NULL,
  role_delete BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (ID)
);


CREATE GENERATOR lookup_stage_code_seq;
CREATE TABLE lookup_stage (
  code INTEGER NOT NULL,
  order_id INTEGER,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_delivery_option_code_seq;
CREATE TABLE lookup_delivery_options (
  code INTEGER NOT NULL,
  description VARCHAR(100) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR news_rec_id_seq;
CREATE TABLE news (
  rec_id INTEGER NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  url BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  base BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  headline BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  body BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  dateEntered TIMESTAMP,
  "type" CHAR(1),
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (REC_ID)
);

CREATE GENERATOR organization_add_address_id_seq;
CREATE TABLE organization_address (
  address_id INTEGER NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  address_type INTEGER references lookup_orgaddress_types(code),
  addrline1 VARCHAR(80),
  addrline2 VARCHAR(80),
  addrline3 VARCHAR(80),
  city VARCHAR(80),
  state VARCHAR(80),
  country VARCHAR(80),
  postalcode VARCHAR(12),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_address BOOLEAN DEFAULT FALSE NOT NULL,
  addrline4 VARCHAR(80),
  county VARCHAR(80),
  latitude FLOAT DEFAULT 0,
  longitude FLOAT DEFAULT 0,
  PRIMARY KEY (ADDRESS_ID)
);

CREATE INDEX organization_address_postal_idx ON organization_address(postalcode);


CREATE GENERATOR organization__emailaddress__seq;
CREATE TABLE organization_emailaddress (
  emailaddress_id INTEGER  NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  emailaddress_type INTEGER REFERENCES lookup_orgemail_types(code),
  email VARCHAR(256),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_email BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (EMAILADDRESS_ID)
);


CREATE GENERATOR organization_phone_phone_id_seq;
CREATE TABLE organization_phone (
  phone_id INTEGER  NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  phone_type INTEGER REFERENCES lookup_orgphone_types(code),
  "number" VARCHAR(30),
  extension VARCHAR(10),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_number BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (PHONE_ID)
);


CREATE GENERATOR contact_address_address_id_seq;
CREATE TABLE contact_address (
  address_id INTEGER  NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  address_type INTEGER REFERENCES lookup_contactaddress_types(code),
  addrline1 VARCHAR(80),
  addrline2 VARCHAR(80),
  addrline3 VARCHAR(80),
  city VARCHAR(80),
  state VARCHAR(80),
  country VARCHAR(80),
  postalcode VARCHAR(12),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER  NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER  NOT NULL REFERENCES "access"(user_id),
  primary_address BOOLEAN DEFAULT FALSE NOT NULL,
  addrline4 VARCHAR(80),
  county VARCHAR(80),
  latitude FLOAT DEFAULT 0,
  longitude FLOAT DEFAULT 0,
  PRIMARY KEY (ADDRESS_ID)
);

CREATE INDEX contact_address_contact_id_idx ON contact_address ( contact_id );
CREATE INDEX contact_address_postalcode_idx ON contact_address(postalcode);
CREATE INDEX contact_city_idx on contact_address(city);
CREATE INDEX contact_address_prim_idx ON contact_address(primary_address);

CREATE GENERATOR contact_email_emailaddress__seq;
CREATE TABLE contact_emailaddress (
  emailaddress_id INTEGER  NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  emailaddress_type INTEGER REFERENCES lookup_contactemail_types(code),
  email VARCHAR(256),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_email BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (EMAILADDRESS_ID)
);

CREATE INDEX contact_email_contact_id_idx ON contact_emailaddress ( contact_id );
CREATE INDEX contact_email_prim_idx ON contact_emailaddress(primary_email);


CREATE GENERATOR contact_phone_phone_id_seq;
CREATE TABLE contact_phone (
  phone_id INTEGER NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  phone_type INTEGER REFERENCES lookup_contactphone_types(code),
  "number" VARCHAR(30),
  extension VARCHAR(10),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_number BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (PHONE_ID)
);

CREATE INDEX contact_phone_contact_id_idx ON contact_phone ( contact_id );

-- Old Name: contact_imaddress_address_id_seq;
CREATE GENERATOR contact_imadd_ss_address_id_seq;
CREATE TABLE contact_imaddress (
  address_id INTEGER NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  imaddress_type INTEGER REFERENCES lookup_im_types(code),
  imaddress_service INTEGER REFERENCES lookup_im_services(code),
  imaddress VARCHAR(256),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_im BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (ADDRESS_ID)
);


-- Old Name: contact_textmessageaddress_address_id_seq;
CREATE GENERATOR contact_textm_ss_address_id_seq;
CREATE TABLE contact_textmessageaddress (
  address_id INTEGER  NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  textmessageaddress VARCHAR(256),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_textmessage_address BOOLEAN DEFAULT FALSE NOT NULL,
  textmessageaddress_type INTEGER REFERENCES lookup_textmessage_types(code),
  PRIMARY KEY (ADDRESS_ID)
);


CREATE GENERATOR notification_notification_i_seq;
CREATE TABLE notification (
  notification_id INTEGER NOT NULL,
  notify_user INTEGER NOT NULL,
  "module" VARCHAR(255) NOT NULL,
  item_id INTEGER NOT NULL,
  item_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  attempt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  notify_type VARCHAR(30),
  subject BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  "message" BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  result INTEGER NOT NULL,
  errorMessage BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (NOTIFICATION_ID)
);

CREATE GENERATOR cfsinbox_message_id_seq;
CREATE TABLE cfsinbox_message (
  id INTEGER  NOT NULL,
  subject VARCHAR(255) ,
  body BLOB SUB_TYPE 1 SEGMENT SIZE 100 NOT NULL,
  reply_id INTEGER NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  sent TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  "type" INTEGER  default -1 NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  delete_flag BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (ID)
);


CREATE TABLE cfsinbox_messagelink (
  id INTEGER NOT NULL REFERENCES cfsinbox_message(id),
  sent_to INTEGER NOT NULL REFERENCES contact(contact_id),
  status INTEGER  DEFAULT 0 NOT NULL,
  viewed TIMESTAMP DEFAULT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  sent_from INTEGER NOT NULL REFERENCES "access"(user_id)
);

CREATE TABLE account_type_levels (
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  type_id INTEGER NOT NULL REFERENCES lookup_account_types(code),
  "level" INTEGER NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE contact_type_levels (
  contact_id INTEGER NOT NULL REFERENCES contact(contact_id),
  type_id INTEGER NOT NULL REFERENCES lookup_contact_types(code),
  "level" INTEGER NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE GENERATOR lookup_lists_lookup_id_seq;
CREATE TABLE lookup_lists_lookup(
  id INTEGER NOT NULL,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  lookup_id INTEGER NOT NULL,
  class_name VARCHAR(20),
  table_name VARCHAR(60),
  "level" INTEGER DEFAULT 0,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  category_id INTEGER NOT NULL,
  PRIMARY KEY (ID)
);

CREATE GENERATOR webdav_id_seq;
CREATE TABLE webdav (
  id INTEGER  NOT NULL,
  category_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  class_name VARCHAR(300) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ID)
);


CREATE GENERATOR category_editor_lookup_id_seq;
CREATE TABLE category_editor_lookup(
  id INTEGER NOT NULL,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  constant_id INTEGER NOT NULL,
  table_name VARCHAR(60),
  "level" INTEGER DEFAULT 0,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  category_id INTEGER NOT NULL,
  max_levels INTEGER NOT NULL,
  PRIMARY KEY (ID)
);


CREATE GENERATOR viewpoint_viewpoint_id_seq;
CREATE TABLE viewpoint(
  viewpoint_id INTEGER  NOT NULL,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  vp_user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (VIEWPOINT_ID)
);


CREATE GENERATOR viewpoint_per_vp_permission_seq;
CREATE TABLE viewpoint_permission (
 vp_permission_id INTEGER  NOT NULL,
 viewpoint_id INTEGER NOT NULL REFERENCES viewpoint(viewpoint_id),
 permission_id INTEGER NOT NULL REFERENCES permission(permission_id),
 viewpoint_view BOOLEAN DEFAULT FALSE NOT NULL,
 viewpoint_add BOOLEAN DEFAULT FALSE NOT NULL,
 viewpoint_edit BOOLEAN DEFAULT FALSE  NOT NULL,
 viewpoint_delete BOOLEAN DEFAULT FALSE  NOT NULL,
 PRIMARY KEY (VP_PERMISSION_ID)
);


CREATE GENERATOR report_report_id_seq;
CREATE TABLE report (
  report_id INTEGER  NOT NULL,
  category_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  permission_id INTEGER REFERENCES permission(permission_id),
  filename VARCHAR(300) NOT NULL,
  "type" INTEGER  DEFAULT 1  NOT NULL,
  title VARCHAR(300) NOT NULL,
  description VARCHAR(1024) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  custom BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (REPORT_ID)
);


CREATE GENERATOR report_criteria_criteria_id_seq;
CREATE TABLE report_criteria (
  criteria_id INTEGER  NOT NULL,
  report_id INTEGER NOT NULL REFERENCES report(report_id),
  owner INTEGER NOT NULL REFERENCES "access"(user_id),
  subject VARCHAR(512) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CRITERIA_ID)
);


-- Old Name: report_criteria_parameter_parameter_id_seq;
CREATE GENERATOR report_criter__parameter_id_seq;
CREATE TABLE report_criteria_parameter (
  parameter_id INTEGER  NOT NULL,
  criteria_id INTEGER NOT NULL REFERENCES report_criteria(criteria_id),
  "parameter" VARCHAR(255) NOT NULL,
  "value" BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (PARAMETER_ID)
);


CREATE GENERATOR report_queue_queue_id_seq;
CREATE TABLE report_queue (
  queue_id INTEGER NOT NULL,
  report_id INTEGER NOT NULL REFERENCES report(report_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  processed TIMESTAMP ,
  status INTEGER  DEFAULT 0 NOT NULL,
  filename VARCHAR(256),
  filesize INTEGER DEFAULT -1,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (QUEUE_ID)
);


-- Old Name: report_queue_criteria_criteria_id_seq;
CREATE GENERATOR report_queue__a_criteria_id_seq;
CREATE TABLE report_queue_criteria (
  criteria_id INTEGER NOT NULL,
  queue_id INTEGER NOT NULL REFERENCES report_queue(queue_id),
  "parameter" VARCHAR(255) NOT NULL,
  "value" BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (CRITERIA_ID)
);


CREATE GENERATOR action_list_code_seq;
CREATE TABLE action_list (
  action_id INTEGER  NOT NULL,
  description VARCHAR(255) NOT NULL,
  owner INTEGER NOT NULL REFERENCES "access"(user_id),
  completedate TIMESTAMP,
  link_module_id INTEGER NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  PRIMARY KEY (ACTION_ID)
);


CREATE GENERATOR action_item_code_seq;
CREATE TABLE action_item (
  item_id INTEGER  NOT NULL,
  action_id INTEGER NOT NULL REFERENCES action_list(action_id),
  link_item_id INTEGER NOT NULL,
  completedate TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  PRIMARY KEY (ITEM_ID)
);


CREATE GENERATOR action_item_log_code_seq;
CREATE TABLE action_item_log (
  log_id INTEGER  NOT NULL,
  item_id INTEGER NOT NULL REFERENCES action_item(item_id),
  link_item_id INTEGER DEFAULT -1,
  "type" INTEGER NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (LOG_ID)
);


CREATE GENERATOR import_import_id_seq;
CREATE TABLE import(
  import_id INTEGER  NOT NULL,
  "type" INTEGER NOT NULL,
  name VARCHAR(250) NOT NULL,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  source_type INTEGER,
  source VARCHAR(1000),
  record_delimiter VARCHAR(10),
  column_delimiter VARCHAR(10),
  total_imported_records INTEGER,
  total_failed_records INTEGER,
  status_id INTEGER,
  file_type INTEGER,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  site_id INT REFERENCES lookup_site_id(code),
  rating INT REFERENCES lookup_contact_rating(code),
  comments BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (IMPORT_ID)
);

CREATE INDEX import_entered_idx ON import ( entered );
CREATE INDEX import_name_idx ON import ( name );


CREATE GENERATOR database_version_version_id_seq;
CREATE TABLE database_version (
  version_id INTEGER NOT NULL,
  script_filename VARCHAR(255) NOT NULL,
  script_version VARCHAR(255) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (VERSION_ID)
);

-- Old Name: lookup_relationship_types_type_id_seq;
CREATE GENERATOR lookup_relati_types_type_id_seq;
CREATE TABLE lookup_relationship_types (
  type_id INTEGER NOT NULL,
  category_id_maps_from INTEGER NOT NULL,
  category_id_maps_to INTEGER NOT NULL,
  reciprocal_name_1 VARCHAR(512),
  reciprocal_name_2 VARCHAR(512),
  "level" INTEGER DEFAULT 0,
  default_item BOOLEAN DEFAULT FALSE,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (TYPE_ID)
);

-- Old Name: relationship_relationship_id_seq;
CREATE GENERATOR relationship__lationship_id_seq;
CREATE TABLE relationship (
  relationship_id INTEGER  NOT NULL,
  type_id INTEGER REFERENCES lookup_relationship_types(type_id),
  object_id_maps_from INTEGER NOT NULL,
  category_id_maps_from INTEGER NOT NULL,
  object_id_maps_to INTEGER NOT NULL,
  category_id_maps_to INTEGER NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL,
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL,
  trashed_date TIMESTAMP,
  PRIMARY KEY (RELATIONSHIP_ID)
);

-- Create a new table to group users
CREATE GENERATOR user_group_group_id_seq;
CREATE TABLE user_group (
  group_id INT NOT NULL PRIMARY KEY,
  group_name VARCHAR(255) NOT NULL,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  site_id INTEGER REFERENCES lookup_site_id(code)
);

-- Create the user group map table
CREATE GENERATOR user_group_map_group_map_id_seq;
CREATE TABLE user_group_map (
  group_map_id INT NOT NULL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  group_id INTEGER NOT NULL REFERENCES user_group(group_id),
  "level" INTEGER DEFAULT 10 NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Custom List Views Editor
CREATE GENERATOR custom_list_v_tor_editor_id_seq;
CREATE TABLE custom_list_view_editor (
  editor_id INT NOT NULL PRIMARY KEY,
  module_id INT NOT NULL REFERENCES permission_category(category_id),
  constant_id INT NOT NULL,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  "level" INT default 0,
  category_id INT NOT NULL
);

-- Custom List View
CREATE GENERATOR custom_list_view_view_id_seq;
CREATE TABLE custom_list_view (
  view_id INT NOT NULL PRIMARY KEY,
  editor_id INT NOT NULL REFERENCES custom_list_view_editor(editor_id),
  name VARCHAR(80) NOT NULL,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  is_default BOOLEAN DEFAULT FALSE,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Custom List View Field
CREATE GENERATOR custom_list_v_ield_field_id_seq;
CREATE TABLE custom_list_view_field (
  field_id INT NOT NULL PRIMARY KEY,
  view_id INT NOT NULL REFERENCES custom_list_view(view_id),
  name VARCHAR(80) NOT NULL
);
-- Functions for InterBase, they are included in the udf directory in every InterBase install
--/*****************************************
-- *
-- *	l o w e r
-- *
-- *****************************************
-- *
-- * Functional description:
-- *	Returns the input string into lower 
-- *	case characters.  Note: This function
-- *	will not work with international and 
-- *	non-ascii characters.
--  *	Note: This function is NOT limited to
--  *	receiving and returning only 80 characters,
--  *	rather, it can use as long as 32767 
--  * 	characters which is the limit on an 
--  *	INTERBASE character string.
--  *
--  *****************************************/

DECLARE EXTERNAL FUNCTION lower
CSTRING(256)
RETURNS CSTRING(256) FREE_IT
ENTRY_POINT 'IB_UDF_lower' MODULE_NAME 'ib_udf';

-- /*****************************************
--  *
--  *	s u b s t r
--  *
--  *****************************************
--  *
--  * Functional description:
--  *	substr(s,m,n) returns the substring 
--  *	of s which starts at position m and
--  *	ending at position n.
--  *	Note: This function is NOT limited to
--  *	receiving and returning only 80 characters,
--  *	rather, it can use as long as 32767 
--  * 	characters which is the limit on an 
--  *	INTERBASE character string.
--  *
--  *****************************************/

DECLARE EXTERNAL FUNCTION substr 
	CSTRING(80), SMALLINT, SMALLINT
	RETURNS CSTRING(80) FREE_IT
	ENTRY_POINT 'IB_UDF_substr' MODULE_NAME 'ib_udf';

CREATE GENERATOR lookup_site_id_code_seq;
CREATE TABLE lookup_site_id (
  code INT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  short_description VARCHAR(300),
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR access_user_id_seq;
SET GENERATOR access_user_id_seq TO -1;
CREATE TABLE "access" (
  user_id INTEGER  NOT NULL,
  username VARCHAR(80) NOT NULL,
  "password" VARCHAR(80),
  contact_id INTEGER DEFAULT -1,
  role_id INTEGER DEFAULT -1,
  manager_id INTEGER DEFAULT -1,
  startofday INTEGER DEFAULT 8,
  endofday INTEGER DEFAULT 18,
  locale VARCHAR(255),
  timezone VARCHAR(255) DEFAULT 'America/New_York',
  last_ip VARCHAR(15),
  last_login timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL,
  entered timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL,
  modified timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  expires timestamp DEFAULT NULL,
  alias INTEGER DEFAULT -1,
  assistant INTEGER DEFAULT -1,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  currency VARCHAR(5),
  "language" VARCHAR(20),
  webdav_password VARCHAR(80),
  hidden BOOLEAN DEFAULT FALSE,
  site_id INT REFERENCES lookup_site_id(code),
  allow_webdav_access BOOLEAN DEFAULT TRUE NOT NULL,
  allow_httpapi_access BOOLEAN DEFAULT TRUE NOT NULL,
  PRIMARY KEY (USER_ID)
);

CREATE GENERATOR lookup_sic_codes_code_seq;
CREATE TABLE lookup_sic_codes(
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER,
  enabled BOOLEAN DEFAULT TRUE,
  constant_id INTEGER NOT NULL UNIQUE 
);
-- gotta be not null before it is unique

CREATE GENERATOR lookup_industry_code_seq;
CREATE TABLE lookup_industry (
  code INTEGER NOT NULL,
  order_id INTEGER,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR access_log_id_seq;
CREATE TABLE access_log (
  id INTEGER  NOT NULL,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  username VARCHAR(80) NOT NULL,
  ip VARCHAR(15),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  browser VARCHAR(255),
  PRIMARY KEY (ID)
);


CREATE GENERATOR usage_log_usage_id_seq;
CREATE TABLE usage_log (
  usage_id INTEGER NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER ,
  "action" INTEGER NOT NULL,
  record_id INTEGER ,
  record_size INTEGER,
  PRIMARY KEY (USAGE_ID)
);

CREATE GENERATOR lookup_contact_types_code_seq;
CREATE TABLE lookup_contact_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  user_id INTEGER REFERENCES "access"(USER_ID) ,
  category INTEGER  DEFAULT 0 NOT NULL,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_account_types_code_seq;
CREATE TABLE lookup_account_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE TABLE state (
  state_code CHAR(2) NOT NULL,
  state VARCHAR(80) NOT NULL,
  PRIMARY KEY (STATE_CODE)
);


CREATE GENERATOR lookup_department_code_seq;
CREATE TABLE lookup_department (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_orgaddress_type_code_seq;
CREATE TABLE lookup_orgaddress_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_orgemail_types_code_seq;
CREATE TABLE lookup_orgemail_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_orgphone_types_code_seq;
CREATE TABLE lookup_orgphone_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_im_types_code_seq;
CREATE TABLE lookup_im_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_im_services_code_seq;
CREATE TABLE lookup_im_services (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);


CREATE GENERATOR lookup_contact_source_code_seq;
CREATE TABLE lookup_contact_source (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_contact_rating_code_seq;
CREATE TABLE lookup_contact_rating (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_textmessage_typ_code_seq;
CREATE TABLE lookup_textmessage_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);


CREATE GENERATOR lookup_employment_type_code_seq;
CREATE TABLE lookup_employment_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_locale_code_seq;
CREATE TABLE lookup_locale (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_contactaddress__code_seq;
CREATE TABLE lookup_contactaddress_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_contactemail_ty_code_seq;
CREATE TABLE lookup_contactemail_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_contactphone_ty_code_seq;
CREATE TABLE lookup_contactphone_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
 "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_access_types_code_seq;
CREATE TABLE lookup_access_types (
  code INTEGER NOT NULL,
  link_module_id INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER,
  enabled BOOLEAN DEFAULT TRUE,
  rule_id INTEGER NOT NULL,
  PRIMARY KEY (CODE)
);
create index laccess_types_rule_id on lookup_access_types (rule_id);

CREATE GENERATOR lookup_account_size_code_seq;
CREATE TABLE lookup_account_size (
  code INT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR lookup_segments_code_seq;
CREATE TABLE lookup_segments (
  code INT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR lookup_sub_segment_code_seq;
CREATE TABLE lookup_sub_segment (
  code INT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  segment_id  INT REFERENCES lookup_segments(code),
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR lookup_title_code_seq;
CREATE TABLE lookup_title (
  code INT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR organization_org_id_seq;
SET GENERATOR organization_org_id_seq TO -1;
CREATE TABLE organization (
  org_id INTEGER NOT NULL,
  name VARCHAR(80) NOT NULL,
  account_number VARCHAR(50),
  account_group INTEGER,
  url BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  revenue FLOAT,
  employees INTEGER,
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  ticker_symbol VARCHAR(10) ,
  taxid CHAR(80),
  lead VARCHAR(40),
  sales_rep INTEGER DEFAULT 0 NOT NULL ,
  miner_only BOOLEAN DEFAULT FALSE NOT NULL ,
  defaultlocale INTEGER,
  fiscalmonth INTEGER,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  industry_temp_code SMALLINT,
  owner INTEGER REFERENCES "access"(user_id),
  duplicate_id INTEGER default -1,
  custom1 INTEGER default -1,
  custom2 INTEGER default -1,
  contract_end TIMESTAMP ,
  alertdate TIMESTAMP ,
  alert varchar(100) ,
  custom_data BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  namesalutation varchar(80),
  namelast varchar(80),
  namefirst varchar(80),
  namemiddle varchar(80),
  namesuffix varchar(80),
  import_id INTEGER,
  status_id INTEGER,
  alertdate_timezone VARCHAR(255),
  contract_end_timezone VARCHAR(255),
  trashed_date TIMESTAMP,
  source INTEGER REFERENCES lookup_contact_source(code),
  rating INTEGER REFERENCES lookup_contact_rating(code),
  potential FLOAT,
  segment_id INT REFERENCES lookup_segments(code),
  sub_segment_id INT REFERENCES lookup_sub_segment(code),
  direct_bill BOOLEAN DEFAULT FALSE,
  account_size INT REFERENCES lookup_account_size(code),
  site_id INT REFERENCES lookup_site_id(code),
  duns_type VARCHAR(300),
  duns_number VARCHAR(30),
  business_name_two VARCHAR(300),
  sic_code INTEGER REFERENCES lookup_sic_codes(code),
  year_started INTEGER,
  sic_description VARCHAR(300),
  PRIMARY KEY (ORG_ID)
);

CREATE INDEX orglist_name ON organization ( name );

CREATE GENERATOR contact_contact_id_seq;
CREATE TABLE contact (
  contact_id INTEGER NOT NULL,
  user_id INTEGER REFERENCES "access"(user_id),
  org_id INTEGER REFERENCES organization(org_id),
  company VARCHAR(255),
  title VARCHAR(80),
  department INTEGER references lookup_department(code),
  super INTEGER ,
  namesalutation varchar(80),
  namelast VARCHAR(80) NOT NULL,
  namefirst VARCHAR(80) NOT NULL,
  namemiddle VARCHAR(80),
  namesuffix VARCHAR(80),
  assistant INTEGER,
  birthdate TIMESTAMP,
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  site INTEGER,
  locale INTEGER,
  employee_id varchar(80),
  employmenttype INTEGER,
  startofday VARCHAR(10),
  endofday VARCHAR(10),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  owner INTEGER REFERENCES "access"(user_id),
  custom1 INTEGER default -1,
  url VARCHAR(100),
  primary_contact BOOLEAN DEFAULT FALSE,
  employee BOOLEAN DEFAULT FALSE NOT NULL,
  org_name VARCHAR(255),
  access_type INTEGER REFERENCES lookup_access_types(code),
  status_id INTEGER,
  import_id INTEGER,
  information_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lead BOOLEAN DEFAULT FALSE,
  lead_status INTEGER,
  source INTEGER REFERENCES lookup_contact_source(code),
  rating INTEGER REFERENCES lookup_contact_rating(code),
  comments VARCHAR(255),
  conversion_date TIMESTAMP,
  additional_names VARCHAR(255),
  nickname VARCHAR(80),
  "role" VARCHAR(255),
  trashed_date TIMESTAMP,
  secret_word VARCHAR(255),
  account_number VARCHAR(50),
  revenue FLOAT,
  industry_temp_code INTEGER REFERENCES lookup_industry(code),
  potential FLOAT,
  no_email BOOLEAN DEFAULT FALSE,
  no_mail BOOLEAN DEFAULT FALSE,
  no_phone BOOLEAN DEFAULT FALSE,
  no_textmessage BOOLEAN DEFAULT FALSE,
  no_im BOOLEAN DEFAULT FALSE,
  no_fax BOOLEAN DEFAULT FALSE,
  site_id INTEGER REFERENCES lookup_site_id(code),
  assigned_date TIMESTAMP,
  lead_trashed_date TIMESTAMP,
  employees INTEGER,
  duns_type VARCHAR(300),
  duns_number VARCHAR(30),
  business_name_two VARCHAR(300),
  sic_code INTEGER REFERENCES lookup_sic_codes(code),
  year_started INTEGER,
  sic_description VARCHAR(300),
  PRIMARY KEY (CONTACT_ID)
);

-- THESE ARE REQUIRED - Firebird cannot reference itself (primary key) during table creation
ALTER TABLE CONTACT ADD CONSTRAINT FK_CONTACT_CONTACT_SUPER
  FOREIGN KEY (SUPER) REFERENCES CONTACT
  (CONTACT_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE CONTACT ADD CONSTRAINT FK_CONTACT_CONTACT_ASSIST
  FOREIGN KEY (ASSISTANT) REFERENCES CONTACT
  (CONTACT_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


CREATE INDEX contact_user_id_idx ON contact ( user_id );
CREATE INDEX contactlist_namecompany ON contact ( namelast, namefirst );
-- CREATE INDEX contactlist_namecompany ON contact ( namelast, namefirst, company );
-- CREATE INDEX contactlist_company ON contact ( company, namelast, namefirst );
CREATE INDEX contact_import_id_idx ON contact ( import_id );
CREATE INDEX contact_org_id_idx ON contact(org_id);
CREATE INDEX contact_islead_idx ON contact(lead);


-- Old Name: contact_lead_skipped_map_map_id_seq;
CREATE GENERATOR contact_lead__ed_map_map_id_seq;
CREATE TABLE contact_lead_skipped_map (
  map_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  contact_id INTEGER NOT NULL REFERENCES contact(contact_id),
  PRIMARY KEY (MAP_ID)
);

CREATE INDEX contact_lead_skip_u_idx ON contact_lead_skipped_map(user_id);


-- Old Name contact_lead_read_map_map_id_seq;
CREATE GENERATOR contact_lead__ad_map_map_id_seq;
CREATE TABLE contact_lead_read_map (
  map_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  contact_id INTEGER NOT NULL REFERENCES contact(contact_id),
  PRIMARY KEY (MAP_ID)
);

CREATE INDEX contact_lead_read_u_idx ON contact_lead_read_map(user_id);
CREATE INDEX contact_lead_read_c_idx ON contact_lead_read_map(contact_id);


CREATE GENERATOR role_role_id_seq;
CREATE TABLE "role" (
  role_id INTEGER  NOT NULL,
  "role" VARCHAR(80) NOT NULL,
  description VARCHAR(255) DEFAULT '' NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  role_type INTEGER,
  PRIMARY KEY (ROLE_ID)
);


CREATE GENERATOR permission_cate_category_id_seq;
CREATE TABLE permission_category (
  category_id INTEGER  NOT NULL,
  category VARCHAR(80),
  description VARCHAR(255),
  "level" INTEGER DEFAULT 0 NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  "active" BOOLEAN DEFAULT TRUE NOT NULL,
  folders BOOLEAN DEFAULT FALSE NOT NULL,
  lookups BOOLEAN DEFAULT FALSE NOT NULL,
  viewpoints BOOLEAN DEFAULT FALSE,
  categories BOOLEAN DEFAULT FALSE NOT NULL,
  scheduled_events BOOLEAN DEFAULT FALSE NOT NULL,
  object_events BOOLEAN DEFAULT FALSE NOT NULL,
  reports BOOLEAN DEFAULT FALSE NOT NULL,
  webdav BOOLEAN DEFAULT FALSE NOT NULL,
  logos BOOLEAN DEFAULT FALSE NOT NULL,
  constant INTEGER NOT NULL,
  action_plans BOOLEAN DEFAULT FALSE NOT NULL,
  custom_list_views BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (CATEGORY_ID)
);


CREATE GENERATOR permission_permission_id_seq;
CREATE TABLE permission (
  permission_id INTEGER  NOT NULL,
  category_id INTEGER NOT NULL REFERENCES permission_category,
  permission VARCHAR(80) NOT NULL,
  permission_view BOOLEAN DEFAULT FALSE NOT NULL,
  permission_add BOOLEAN DEFAULT FALSE NOT NULL,
  permission_edit BOOLEAN DEFAULT FALSE NOT NULL,
  permission_delete BOOLEAN DEFAULT FALSE NOT NULL,
  description VARCHAR(255)  DEFAULT '' NOT NULL,
  "level" INTEGER DEFAULT 0 NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  "active" BOOLEAN DEFAULT TRUE NOT NULL,
  viewpoints BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (PERMISSION_ID)
);


CREATE GENERATOR role_permission_id_seq;
CREATE TABLE role_permission (
  id INTEGER  NOT NULL,
  role_id INTEGER NOT NULL REFERENCES "role"(role_id),
  permission_id INTEGER NOT NULL REFERENCES permission(permission_id),
  role_view BOOLEAN DEFAULT FALSE NOT NULL,
  role_add BOOLEAN DEFAULT FALSE NOT NULL,
  role_edit BOOLEAN DEFAULT FALSE NOT NULL,
  role_delete BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (ID)
);


CREATE GENERATOR lookup_stage_code_seq;
CREATE TABLE lookup_stage (
  code INTEGER NOT NULL,
  order_id INTEGER,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_delivery_option_code_seq;
CREATE TABLE lookup_delivery_options (
  code INTEGER NOT NULL,
  description VARCHAR(100) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR news_rec_id_seq;
CREATE TABLE news (
  rec_id INTEGER NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  url BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  base BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  headline BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  body BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  dateEntered TIMESTAMP,
  "type" CHAR(1),
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (REC_ID)
);

CREATE GENERATOR organization_add_address_id_seq;
CREATE TABLE organization_address (
  address_id INTEGER NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  address_type INTEGER references lookup_orgaddress_types(code),
  addrline1 VARCHAR(80),
  addrline2 VARCHAR(80),
  addrline3 VARCHAR(80),
  city VARCHAR(80),
  state VARCHAR(80),
  country VARCHAR(80),
  postalcode VARCHAR(12),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_address BOOLEAN DEFAULT FALSE NOT NULL,
  addrline4 VARCHAR(80),
  county VARCHAR(80),
  latitude FLOAT DEFAULT 0,
  longitude FLOAT DEFAULT 0,
  PRIMARY KEY (ADDRESS_ID)
);

CREATE INDEX organization_address_postal_idx ON organization_address(postalcode);


CREATE GENERATOR organization__emailaddress__seq;
CREATE TABLE organization_emailaddress (
  emailaddress_id INTEGER  NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  emailaddress_type INTEGER REFERENCES lookup_orgemail_types(code),
  email VARCHAR(256),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_email BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (EMAILADDRESS_ID)
);


CREATE GENERATOR organization_phone_phone_id_seq;
CREATE TABLE organization_phone (
  phone_id INTEGER  NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  phone_type INTEGER REFERENCES lookup_orgphone_types(code),
  "number" VARCHAR(30),
  extension VARCHAR(10),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_number BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (PHONE_ID)
);


CREATE GENERATOR contact_address_address_id_seq;
CREATE TABLE contact_address (
  address_id INTEGER  NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  address_type INTEGER REFERENCES lookup_contactaddress_types(code),
  addrline1 VARCHAR(80),
  addrline2 VARCHAR(80),
  addrline3 VARCHAR(80),
  city VARCHAR(80),
  state VARCHAR(80),
  country VARCHAR(80),
  postalcode VARCHAR(12),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER  NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER  NOT NULL REFERENCES "access"(user_id),
  primary_address BOOLEAN DEFAULT FALSE NOT NULL,
  addrline4 VARCHAR(80),
  county VARCHAR(80),
  latitude FLOAT DEFAULT 0,
  longitude FLOAT DEFAULT 0,
  PRIMARY KEY (ADDRESS_ID)
);

CREATE INDEX contact_address_contact_id_idx ON contact_address ( contact_id );
CREATE INDEX contact_address_postalcode_idx ON contact_address(postalcode);
CREATE INDEX contact_city_idx on contact_address(city);
CREATE INDEX contact_address_prim_idx ON contact_address(primary_address);

CREATE GENERATOR contact_email_emailaddress__seq;
CREATE TABLE contact_emailaddress (
  emailaddress_id INTEGER  NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  emailaddress_type INTEGER REFERENCES lookup_contactemail_types(code),
  email VARCHAR(256),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_email BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (EMAILADDRESS_ID)
);

CREATE INDEX contact_email_contact_id_idx ON contact_emailaddress ( contact_id );
CREATE INDEX contact_email_prim_idx ON contact_emailaddress(primary_email);


CREATE GENERATOR contact_phone_phone_id_seq;
CREATE TABLE contact_phone (
  phone_id INTEGER NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  phone_type INTEGER REFERENCES lookup_contactphone_types(code),
  "number" VARCHAR(30),
  extension VARCHAR(10),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_number BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (PHONE_ID)
);

CREATE INDEX contact_phone_contact_id_idx ON contact_phone ( contact_id );

-- Old Name: contact_imaddress_address_id_seq;
CREATE GENERATOR contact_imadd_ss_address_id_seq;
CREATE TABLE contact_imaddress (
  address_id INTEGER NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  imaddress_type INTEGER REFERENCES lookup_im_types(code),
  imaddress_service INTEGER REFERENCES lookup_im_services(code),
  imaddress VARCHAR(256),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_im BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (ADDRESS_ID)
);


-- Old Name: contact_textmessageaddress_address_id_seq;
CREATE GENERATOR contact_textm_ss_address_id_seq;
CREATE TABLE contact_textmessageaddress (
  address_id INTEGER  NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  textmessageaddress VARCHAR(256),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_textmessage_address BOOLEAN DEFAULT FALSE NOT NULL,
  textmessageaddress_type INTEGER REFERENCES lookup_textmessage_types(code),
  PRIMARY KEY (ADDRESS_ID)
);


CREATE GENERATOR notification_notification_i_seq;
CREATE TABLE notification (
  notification_id INTEGER NOT NULL,
  notify_user INTEGER NOT NULL,
  "module" VARCHAR(255) NOT NULL,
  item_id INTEGER NOT NULL,
  item_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  attempt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  notify_type VARCHAR(30),
  subject BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  "message" BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  result INTEGER NOT NULL,
  errorMessage BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (NOTIFICATION_ID)
);

CREATE GENERATOR cfsinbox_message_id_seq;
CREATE TABLE cfsinbox_message (
  id INTEGER  NOT NULL,
  subject VARCHAR(255) ,
  body BLOB SUB_TYPE 1 SEGMENT SIZE 100 NOT NULL,
  reply_id INTEGER NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  sent TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  "type" INTEGER  default -1 NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  delete_flag BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (ID)
);


CREATE TABLE cfsinbox_messagelink (
  id INTEGER NOT NULL REFERENCES cfsinbox_message(id),
  sent_to INTEGER NOT NULL REFERENCES contact(contact_id),
  status INTEGER  DEFAULT 0 NOT NULL,
  viewed TIMESTAMP DEFAULT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  sent_from INTEGER NOT NULL REFERENCES "access"(user_id)
);

CREATE TABLE account_type_levels (
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  type_id INTEGER NOT NULL REFERENCES lookup_account_types(code),
  "level" INTEGER NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE contact_type_levels (
  contact_id INTEGER NOT NULL REFERENCES contact(contact_id),
  type_id INTEGER NOT NULL REFERENCES lookup_contact_types(code),
  "level" INTEGER NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE GENERATOR lookup_lists_lookup_id_seq;
CREATE TABLE lookup_lists_lookup(
  id INTEGER NOT NULL,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  lookup_id INTEGER NOT NULL,
  class_name VARCHAR(20),
  table_name VARCHAR(60),
  "level" INTEGER DEFAULT 0,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  category_id INTEGER NOT NULL,
  PRIMARY KEY (ID)
);

CREATE GENERATOR webdav_id_seq;
CREATE TABLE webdav (
  id INTEGER  NOT NULL,
  category_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  class_name VARCHAR(300) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ID)
);


CREATE GENERATOR category_editor_lookup_id_seq;
CREATE TABLE category_editor_lookup(
  id INTEGER NOT NULL,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  constant_id INTEGER NOT NULL,
  table_name VARCHAR(60),
  "level" INTEGER DEFAULT 0,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  category_id INTEGER NOT NULL,
  max_levels INTEGER NOT NULL,
  PRIMARY KEY (ID)
);


CREATE GENERATOR viewpoint_viewpoint_id_seq;
CREATE TABLE viewpoint(
  viewpoint_id INTEGER  NOT NULL,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  vp_user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (VIEWPOINT_ID)
);


CREATE GENERATOR viewpoint_per_vp_permission_seq;
CREATE TABLE viewpoint_permission (
 vp_permission_id INTEGER  NOT NULL,
 viewpoint_id INTEGER NOT NULL REFERENCES viewpoint(viewpoint_id),
 permission_id INTEGER NOT NULL REFERENCES permission(permission_id),
 viewpoint_view BOOLEAN DEFAULT FALSE NOT NULL,
 viewpoint_add BOOLEAN DEFAULT FALSE NOT NULL,
 viewpoint_edit BOOLEAN DEFAULT FALSE  NOT NULL,
 viewpoint_delete BOOLEAN DEFAULT FALSE  NOT NULL,
 PRIMARY KEY (VP_PERMISSION_ID)
);


CREATE GENERATOR report_report_id_seq;
CREATE TABLE report (
  report_id INTEGER  NOT NULL,
  category_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  permission_id INTEGER REFERENCES permission(permission_id),
  filename VARCHAR(300) NOT NULL,
  "type" INTEGER  DEFAULT 1  NOT NULL,
  title VARCHAR(300) NOT NULL,
  description VARCHAR(1024) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  custom BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (REPORT_ID)
);


CREATE GENERATOR report_criteria_criteria_id_seq;
CREATE TABLE report_criteria (
  criteria_id INTEGER  NOT NULL,
  report_id INTEGER NOT NULL REFERENCES report(report_id),
  owner INTEGER NOT NULL REFERENCES "access"(user_id),
  subject VARCHAR(512) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CRITERIA_ID)
);


-- Old Name: report_criteria_parameter_parameter_id_seq;
CREATE GENERATOR report_criter__parameter_id_seq;
CREATE TABLE report_criteria_parameter (
  parameter_id INTEGER  NOT NULL,
  criteria_id INTEGER NOT NULL REFERENCES report_criteria(criteria_id),
  "parameter" VARCHAR(255) NOT NULL,
  "value" BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (PARAMETER_ID)
);


CREATE GENERATOR report_queue_queue_id_seq;
CREATE TABLE report_queue (
  queue_id INTEGER NOT NULL,
  report_id INTEGER NOT NULL REFERENCES report(report_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  processed TIMESTAMP ,
  status INTEGER  DEFAULT 0 NOT NULL,
  filename VARCHAR(256),
  filesize INTEGER DEFAULT -1,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (QUEUE_ID)
);


-- Old Name: report_queue_criteria_criteria_id_seq;
CREATE GENERATOR report_queue__a_criteria_id_seq;
CREATE TABLE report_queue_criteria (
  criteria_id INTEGER NOT NULL,
  queue_id INTEGER NOT NULL REFERENCES report_queue(queue_id),
  "parameter" VARCHAR(255) NOT NULL,
  "value" BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (CRITERIA_ID)
);


CREATE GENERATOR action_list_code_seq;
CREATE TABLE action_list (
  action_id INTEGER  NOT NULL,
  description VARCHAR(255) NOT NULL,
  owner INTEGER NOT NULL REFERENCES "access"(user_id),
  completedate TIMESTAMP,
  link_module_id INTEGER NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  PRIMARY KEY (ACTION_ID)
);


CREATE GENERATOR action_item_code_seq;
CREATE TABLE action_item (
  item_id INTEGER  NOT NULL,
  action_id INTEGER NOT NULL REFERENCES action_list(action_id),
  link_item_id INTEGER NOT NULL,
  completedate TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  PRIMARY KEY (ITEM_ID)
);


CREATE GENERATOR action_item_log_code_seq;
CREATE TABLE action_item_log (
  log_id INTEGER  NOT NULL,
  item_id INTEGER NOT NULL REFERENCES action_item(item_id),
  link_item_id INTEGER DEFAULT -1,
  "type" INTEGER NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (LOG_ID)
);


CREATE GENERATOR import_import_id_seq;
CREATE TABLE import(
  import_id INTEGER  NOT NULL,
  "type" INTEGER NOT NULL,
  name VARCHAR(250) NOT NULL,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  source_type INTEGER,
  source VARCHAR(1000),
  record_delimiter VARCHAR(10),
  column_delimiter VARCHAR(10),
  total_imported_records INTEGER,
  total_failed_records INTEGER,
  status_id INTEGER,
  file_type INTEGER,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  site_id INT REFERENCES lookup_site_id(code),
  rating INT REFERENCES lookup_contact_rating(code),
  comments BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (IMPORT_ID)
);

CREATE INDEX import_entered_idx ON import ( entered );
CREATE INDEX import_name_idx ON import ( name );


CREATE GENERATOR database_version_version_id_seq;
CREATE TABLE database_version (
  version_id INTEGER NOT NULL,
  script_filename VARCHAR(255) NOT NULL,
  script_version VARCHAR(255) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (VERSION_ID)
);

-- Old Name: lookup_relationship_types_type_id_seq;
CREATE GENERATOR lookup_relati_types_type_id_seq;
CREATE TABLE lookup_relationship_types (
  type_id INTEGER NOT NULL,
  category_id_maps_from INTEGER NOT NULL,
  category_id_maps_to INTEGER NOT NULL,
  reciprocal_name_1 VARCHAR(512),
  reciprocal_name_2 VARCHAR(512),
  "level" INTEGER DEFAULT 0,
  default_item BOOLEAN DEFAULT FALSE,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (TYPE_ID)
);

-- Old Name: relationship_relationship_id_seq;
CREATE GENERATOR relationship__lationship_id_seq;
CREATE TABLE relationship (
  relationship_id INTEGER  NOT NULL,
  type_id INTEGER REFERENCES lookup_relationship_types(type_id),
  object_id_maps_from INTEGER NOT NULL,
  category_id_maps_from INTEGER NOT NULL,
  object_id_maps_to INTEGER NOT NULL,
  category_id_maps_to INTEGER NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL,
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL,
  trashed_date TIMESTAMP,
  PRIMARY KEY (RELATIONSHIP_ID)
);

-- Create a new table to group users
CREATE GENERATOR user_group_group_id_seq;
CREATE TABLE user_group (
  group_id INT NOT NULL PRIMARY KEY,
  group_name VARCHAR(255) NOT NULL,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  site_id INTEGER REFERENCES lookup_site_id(code)
);

-- Create the user group map table
CREATE GENERATOR user_group_map_group_map_id_seq;
CREATE TABLE user_group_map (
  group_map_id INT NOT NULL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  group_id INTEGER NOT NULL REFERENCES user_group(group_id),
  "level" INTEGER DEFAULT 10 NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Custom List Views Editor
CREATE GENERATOR custom_list_v_tor_editor_id_seq;
CREATE TABLE custom_list_view_editor (
  editor_id INT NOT NULL PRIMARY KEY,
  module_id INT NOT NULL REFERENCES permission_category(category_id),
  constant_id INT NOT NULL,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  "level" INT default 0,
  category_id INT NOT NULL
);

-- Custom List View
CREATE GENERATOR custom_list_view_view_id_seq;
CREATE TABLE custom_list_view (
  view_id INT NOT NULL PRIMARY KEY,
  editor_id INT NOT NULL REFERENCES custom_list_view_editor(editor_id),
  name VARCHAR(80) NOT NULL,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  is_default BOOLEAN DEFAULT FALSE,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Custom List View Field
CREATE GENERATOR custom_list_v_ield_field_id_seq;
CREATE TABLE custom_list_view_field (
  field_id INT NOT NULL PRIMARY KEY,
  view_id INT NOT NULL REFERENCES custom_list_view(view_id),
  name VARCHAR(80) NOT NULL
);
