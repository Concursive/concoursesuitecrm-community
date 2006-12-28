CREATE SEQUENCE lookup_site_id_code_seq;
CREATE TABLE lookup_site_id (
  code INT NOT NULL PRIMARY KEY,
  description NVARCHAR2(300) NOT NULL,
  short_description NVARCHAR2(300),
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE access_user_id_seq  start with 0 minvalue -1  increment by 1;
CREATE TABLE "access" (
  user_id INTEGER  NOT NULL,
  username NVARCHAR2(80) NOT NULL,
  "password" NVARCHAR2(80),
  contact_id INTEGER DEFAULT -1,
  role_id INTEGER DEFAULT -1,
  manager_id INTEGER DEFAULT -1,
  startofday INTEGER DEFAULT 8,
  endofday INTEGER DEFAULT 18,
  locale NVARCHAR2(255),
  timezone NVARCHAR2(255) DEFAULT 'America/New_York',
  last_ip NVARCHAR2(15),
  last_login timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL,
  entered timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL,
  modified timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  expires timestamp DEFAULT NULL,
  alias INTEGER DEFAULT -1,
  assistant INTEGER DEFAULT -1,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  currency NVARCHAR2(5),
  "language" NVARCHAR2(20),
  webdav_password NVARCHAR2(80),
  hidden CHAR(1) DEFAULT 0,
  site_id INT REFERENCES lookup_site_id(code),
  allow_webdav_access CHAR(1) DEFAULT 1 NOT NULL,
  allow_httpapi_access CHAR(1) DEFAULT 1 NOT NULL,
  PRIMARY KEY (USER_ID)
);

CREATE SEQUENCE lookup_sic_codes_code_seq;
CREATE TABLE lookup_sic_codes(
  code INTEGER NOT NULL PRIMARY KEY,
  description NVARCHAR2(300) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER,
  enabled CHAR(1) DEFAULT 1,
  constant_id INTEGER UNIQUE NOT NULL
);

CREATE SEQUENCE lookup_industry_code_seq;
CREATE TABLE lookup_industry (
  code INTEGER NOT NULL,
  order_id INTEGER,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE access_log_id_seq;
CREATE TABLE access_log (
  id INTEGER NOT NULL,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  username NVARCHAR2(80) NOT NULL,
  ip NVARCHAR2(15),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  browser NVARCHAR2(255),
  PRIMARY KEY (ID)
);


CREATE SEQUENCE usage_log_usage_id_seq;
CREATE TABLE usage_log (
  usage_id INTEGER NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER ,
  "action" INTEGER NOT NULL,
  record_id INTEGER ,
  record_size INTEGER,
  PRIMARY KEY (USAGE_ID)
);

CREATE SEQUENCE lookup_contact_types_code_seq;
CREATE TABLE lookup_contact_types (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  user_id INTEGER REFERENCES "access"(USER_ID) ,
  category INTEGER  DEFAULT 0 NOT NULL,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_account_types_code_seq;
CREATE TABLE lookup_account_types (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE TABLE state (
  state_code CHAR(2) NOT NULL,
  state NVARCHAR2(80) NOT NULL,
  PRIMARY KEY (STATE_CODE)
);


CREATE SEQUENCE lookup_department_code_seq;
CREATE TABLE lookup_department (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_orgadd_ss_type_code_seq;
CREATE TABLE lookup_orgaddress_types (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_orgemail_types_code_seq;
CREATE TABLE lookup_orgemail_types (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_orgphone_types_code_seq;
CREATE TABLE lookup_orgphone_types (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_im_types_code_seq;
CREATE TABLE lookup_im_types (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_im_services_code_seq;
CREATE TABLE lookup_im_services (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_contact_source_code_seq;
CREATE TABLE lookup_contact_source (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_contact_rating_code_seq;
CREATE TABLE lookup_contact_rating (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_textme_age_typ_code_seq;
CREATE TABLE lookup_textmessage_types (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);


CREATE SEQUENCE lookup_employ_nt_type_code_seq;
CREATE TABLE lookup_employment_types (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_locale_code_seq;
CREATE TABLE lookup_locale (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_contac_ddress__code_seq;
CREATE TABLE lookup_contactaddress_types (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_contac_mail_ty_code_seq;
CREATE TABLE lookup_contactemail_types (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_contac_hone_ty_code_seq;
CREATE TABLE lookup_contactphone_types (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
 "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_access_types_code_seq;
CREATE TABLE lookup_access_types (
  code INTEGER NOT NULL,
  link_module_id INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER,
  enabled CHAR(1) DEFAULT 1,
  rule_id INTEGER NOT NULL,
  PRIMARY KEY (CODE)
);
create index laccess_types_rule_id on lookup_access_types (rule_id);

CREATE SEQUENCE lookup_account_size_code_seq;
CREATE TABLE lookup_account_size (
  code INT NOT NULL PRIMARY KEY,
  description NVARCHAR2(300) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE lookup_segments_code_seq;
CREATE TABLE lookup_segments (
  code INT NOT NULL PRIMARY KEY,
  description NVARCHAR2(300) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE lookup_sub_segment_code_seq;
CREATE TABLE lookup_sub_segment (
  code INT NOT NULL PRIMARY KEY,
  description NVARCHAR2(300) NOT NULL,
  segment_id  INT REFERENCES lookup_segments(code),
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE lookup_title_code_seq;
CREATE TABLE lookup_title (
  code INT NOT NULL PRIMARY KEY,
  description NVARCHAR2(300) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE lookup_account_stage_code_seq;
CREATE TABLE lookup_account_stage (
  code INT NOT NULL PRIMARY KEY,
  description NVARCHAR2(300) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE SEQUENCE organization_org_id_seq start with 0 minvalue -1  increment by 1;
CREATE TABLE organization (
  org_id INTEGER NOT NULL,
  name NVARCHAR2(80) NOT NULL,
  account_number NVARCHAR2(50),
  account_group INTEGER,
  url CLOB,
  revenue FLOAT,
  employees INTEGER,
  notes CLOB,
  ticker_symbol NVARCHAR2(10) ,
  taxid CHAR(80),
  lead NVARCHAR2(40),
  sales_rep INTEGER DEFAULT 0 NOT NULL ,
  miner_only CHAR(1) DEFAULT 0 NOT NULL ,
  defaultlocale INTEGER,
  fiscalmonth INTEGER,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL references "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL references "access"(user_id),
  enabled CHAR(1) DEFAULT 1,
  industry_temp_code SMALLINT,
  owner INTEGER references "access"(user_id),
  duplicate_id INTEGER default -1,
  custom1 INTEGER default -1,
  custom2 INTEGER default -1,
  contract_end TIMESTAMP ,
  alertdate TIMESTAMP ,
  alert NVARCHAR2(100) ,
  custom_data CLOB,
  namesalutation NVARCHAR2(80),
  namelast NVARCHAR2(80),
  namefirst NVARCHAR2(80),
  namemiddle NVARCHAR2(80),
  namesuffix NVARCHAR2(80),
  import_id INTEGER,
  status_id INTEGER,
  alertdate_timezone NVARCHAR2(255),
  contract_end_timezone NVARCHAR2(255),
  trashed_date TIMESTAMP,
  source INTEGER REFERENCES lookup_contact_source(code),
  rating INTEGER REFERENCES lookup_contact_rating(code),
  potential FLOAT,
  segment_id INT REFERENCES lookup_segments(code),
  sub_segment_id INT REFERENCES lookup_sub_segment(code),
  direct_bill CHAR(1) DEFAULT 0,
  account_size INT REFERENCES lookup_account_size(code),
  site_id INT REFERENCES lookup_site_id(code),
  duns_type NVARCHAR2(300),
  duns_number NVARCHAR2(30),
  business_name_two NVARCHAR2(300),
  sic_code INTEGER REFERENCES lookup_sic_codes(code),
  year_started INTEGER,
  sic_description NVARCHAR2(300),
  stage_id INTEGER REFERENCES lookup_account_stage(code),
  PRIMARY KEY (ORG_ID)
);

CREATE INDEX orglist_name ON organization ( name );

CREATE SEQUENCE contact_contact_id_seq;
CREATE TABLE contact (
  contact_id INTEGER NOT NULL,
  user_id INTEGER REFERENCES "access"(user_id),
  org_id INTEGER REFERENCES organization(org_id),
  company NVARCHAR2(255),
  title NVARCHAR2(80),
  department INTEGER references lookup_department(code),
  super INTEGER REFERENCES contact(contact_id),
  namesalutation NVARCHAR2(80),
  namelast NVARCHAR2(80) NOT NULL,
  namefirst NVARCHAR2(80) NOT NULL,
  namemiddle NVARCHAR2(80),
  namesuffix NVARCHAR2(80),
  assistant INTEGER REFERENCES contact(contact_id),
  birthdate TIMESTAMP,
  notes CLOB,
  site INTEGER,
  locale INTEGER,
  employee_id NVARCHAR2(80),
  employmenttype INTEGER,
  startofday NVARCHAR2(10),
  endofday NVARCHAR2(10),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 1,
  owner INTEGER REFERENCES "access"(user_id),
  custom1 INTEGER default -1,
  url NVARCHAR2(100),
  primary_contact CHAR(1) DEFAULT 0,
  employee CHAR(1) DEFAULT 0 NOT NULL,
  org_name NVARCHAR2(255),
  access_type INTEGER REFERENCES lookup_access_types(code),
  status_id INTEGER,
  import_id INTEGER,
  information_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lead CHAR(1) DEFAULT 0,
  lead_status INTEGER,
  source INTEGER REFERENCES lookup_contact_source(code),
  rating INTEGER REFERENCES lookup_contact_rating(code),
  comments NVARCHAR2(255),
  conversion_date TIMESTAMP,
  additional_names NVARCHAR2(255),
  nickname NVARCHAR2(80),
  "role" NVARCHAR2(255),
  trashed_date TIMESTAMP,
  secret_word NVARCHAR2(255),
  account_number NVARCHAR2(50),
  revenue FLOAT,
  industry_temp_code INTEGER REFERENCES lookup_industry(code),
  potential FLOAT,
  no_email CHAR(1) DEFAULT 0,
  no_mail CHAR(1) DEFAULT 0,
  no_phone CHAR(1) DEFAULT 0,
  no_textmessage CHAR(1) DEFAULT 0,
  no_im CHAR(1) DEFAULT 0,
  no_fax CHAR(1) DEFAULT 0,
  site_id INTEGER REFERENCES lookup_site_id(code),
  assigned_date TIMESTAMP,
  lead_trashed_date TIMESTAMP,
  employees INTEGER,
  duns_type NVARCHAR2(300),
  duns_number NVARCHAR2(30),
  business_name_two NVARCHAR2(300),
  sic_code INTEGER REFERENCES lookup_sic_codes(code),
  year_started INTEGER,
  sic_description NVARCHAR2(300),
  PRIMARY KEY (CONTACT_ID)
);

CREATE INDEX contact_user_id_idx ON contact ( user_id );
CREATE INDEX contactlist_namecompany ON contact ( namelast, namefirst );
-- CREATE INDEX contactlist_namecompany ON contact ( namelast, namefirst, company );
-- CREATE INDEX contactlist_company ON contact ( company, namelast, namefirst );
CREATE INDEX contact_import_id_idx ON contact ( import_id );
-- CREATE INDEX contact_org_id_idx ON contact(org_id);
CREATE INDEX contact_islead_idx ON contact(lead);
create index contact_access_type on contact  (access_type);
create index contact_assistant on contact  (assistant);
create index contact_department on contact  (department);
create index contact_enteredby on contact  (enteredby);
create index contact_industry_temp_code on contact  (industry_temp_code);
create index contact_modifiedby on contact  (modifiedby);
create index contact_org_id on contact  (org_id);
create index contact_owner on contact  (owner);
create index contact_rating on contact  (rating);
create index contact_site_id on contact  (site_id);
create index contact_source on contact  (source);
create index contact_super on contact  (super);
--create index contact_user_id on contact  (user_id);
create index contact_employee_id on contact (employee_id);
create index contact_entered on contact (entered);

-- Old Name: contact_lead_skipped_map_map_id_seq;
CREATE SEQUENCE contact_lead__d_map_map_id_seq;
CREATE TABLE contact_lead_skipped_map (
  map_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  contact_id INTEGER NOT NULL REFERENCES contact(contact_id),
  PRIMARY KEY (MAP_ID)
);

CREATE INDEX contact_lead_skip_u_idx ON contact_lead_skipped_map(user_id);


-- Old Name contact_lead_read_map_map_id_seq;
CREATE TABLE contact_lead_read_map (
  map_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  contact_id INTEGER NOT NULL REFERENCES contact(contact_id),
  PRIMARY KEY (MAP_ID)
);

CREATE INDEX contact_lead_read_u_idx ON contact_lead_read_map(user_id);
CREATE INDEX contact_lead_read_c_idx ON contact_lead_read_map(contact_id);


CREATE SEQUENCE role_role_id_seq;
CREATE TABLE "role" (
  role_id INTEGER  NOT NULL,
  "role" NVARCHAR2(80) NOT NULL,
  description NVARCHAR2(255) DEFAULT '' NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  role_type INTEGER,
  PRIMARY KEY (ROLE_ID)
);


CREATE SEQUENCE permission_ca__category_id_seq;
CREATE TABLE permission_category (
  category_id INTEGER  NOT NULL,
  category NVARCHAR2(80),
  description NVARCHAR2(255),
  "level" INTEGER DEFAULT 0 NOT NULL,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  "active" CHAR(1) DEFAULT 1 NOT NULL,
  folders CHAR(1) DEFAULT 0 NOT NULL,
  lookups CHAR(1) DEFAULT 0 NOT NULL,
  viewpoints CHAR(1) DEFAULT 0,
  categories CHAR(1) DEFAULT 0 NOT NULL,
  scheduled_events CHAR(1) DEFAULT 0 NOT NULL,
  object_events CHAR(1) DEFAULT 0 NOT NULL,
  reports CHAR(1) DEFAULT 0 NOT NULL,
  webdav CHAR(1) DEFAULT 0 NOT NULL,
  logos CHAR(1) DEFAULT 0 NOT NULL,
  constant INTEGER NOT NULL,
  action_plans CHAR(1) DEFAULT 0 NOT NULL,
  custom_list_views CHAR(1) DEFAULT 0 NOT NULL,
  PRIMARY KEY(category_id)
);


CREATE SEQUENCE permission_permission_id_seq;
CREATE TABLE permission (
  permission_id INTEGER  NOT NULL,
  category_id INTEGER NOT NULL REFERENCES permission_category,
  permission NVARCHAR2(80) NOT NULL,
  permission_view CHAR(1) DEFAULT 0 NOT NULL,
  permission_add CHAR(1) DEFAULT 0 NOT NULL,
  permission_edit CHAR(1) DEFAULT 0 NOT NULL,
  permission_delete CHAR(1) DEFAULT 0 NOT NULL,
  description NVARCHAR2(255)  DEFAULT '' NOT NULL,
  "level" INTEGER DEFAULT 0 NOT NULL,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  "active" CHAR(1) DEFAULT 1 NOT NULL,
  viewpoints CHAR(1) DEFAULT 0,
  PRIMARY KEY (PERMISSION_ID)
);


CREATE SEQUENCE role_permission_id_seq;
CREATE TABLE role_permission (
  id INTEGER  NOT NULL,
  role_id INTEGER NOT NULL REFERENCES "role"(role_id),
  permission_id INTEGER NOT NULL REFERENCES permission(permission_id),
  role_view CHAR(1) DEFAULT 0 NOT NULL,
  role_add CHAR(1) DEFAULT 0 NOT NULL,
  role_edit CHAR(1) DEFAULT 0 NOT NULL,
  role_delete CHAR(1) DEFAULT 0 NOT NULL,
  PRIMARY KEY (ID)
);


CREATE SEQUENCE lookup_stage_code_seq;
CREATE TABLE lookup_stage (
  code INTEGER NOT NULL,
  order_id INTEGER,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE lookup_delive__option_code_seq;
CREATE TABLE lookup_delivery_options (
  code INTEGER NOT NULL,
  description NVARCHAR2(100) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE news_rec_id_seq;
CREATE TABLE news (
  rec_id INTEGER NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  url CLOB,
  base CLOB,
  headline CLOB,
  body CLOB,
  dateEntered TIMESTAMP,
  "type" CHAR(1),
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (REC_ID)
);

CREATE SEQUENCE organization__d_address_id_seq;
CREATE TABLE organization_address (
  address_id INTEGER NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  address_type INTEGER references lookup_orgaddress_types(code),
  addrline1 NVARCHAR2(80),
  addrline2 NVARCHAR2(80),
  addrline3 NVARCHAR2(80),
  city NVARCHAR2(80),
  state NVARCHAR2(80),
  country NVARCHAR2(80),
  postalcode NVARCHAR2(12),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_address CHAR(1) DEFAULT 0 NOT NULL,
  addrline4 NVARCHAR2(80),
  county NVARCHAR2(80),
  latitude FLOAT DEFAULT 0,
  longitude FLOAT DEFAULT 0,
  PRIMARY KEY (ADDRESS_ID)
);

CREATE INDEX organization_address_posta_idx ON organization_address(postalcode);


CREATE SEQUENCE organization__mailaddress__seq;
CREATE TABLE organization_emailaddress (
  emailaddress_id INTEGER  NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  emailaddress_type INTEGER REFERENCES lookup_orgemail_types(code),
  email NVARCHAR2(256),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_email CHAR(1) DEFAULT 0 NOT NULL,
  PRIMARY KEY (EMAILADDRESS_ID)
);


CREATE SEQUENCE organization__one_phone_id_seq;
CREATE TABLE organization_phone (
  phone_id INTEGER  NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  phone_type INTEGER REFERENCES lookup_orgphone_types(code),
  "number" NVARCHAR2(30),
  extension NVARCHAR2(10),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_number CHAR(1) DEFAULT 0 NOT NULL,
  PRIMARY KEY (PHONE_ID)
);

CREATE SEQUENCE contact_address_address_id_seq;
CREATE TABLE contact_address (
  address_id INTEGER  NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  address_type INTEGER REFERENCES lookup_contactaddress_types(code),
  addrline1 NVARCHAR2(80),
  addrline2 NVARCHAR2(80),
  addrline3 NVARCHAR2(80),
  city NVARCHAR2(80),
  state NVARCHAR2(80),
  country NVARCHAR2(80),
  postalcode NVARCHAR2(12),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER  NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER  NOT NULL REFERENCES "access"(user_id),
  primary_address CHAR(1) DEFAULT 0 NOT NULL,
  addrline4 NVARCHAR2(80),
  county NVARCHAR2(80),
  latitude FLOAT DEFAULT 0,
  longitude FLOAT DEFAULT 0,
  PRIMARY KEY (ADDRESS_ID)
);

CREATE INDEX contact_address_contact_id_idx ON contact_address ( contact_id );
CREATE INDEX contact_address_postalcode_idx ON contact_address(postalcode);
CREATE INDEX contact_city_idx on contact_address(city);
CREATE INDEX contact_address_prim_idx ON contact_address(primary_address);

CREATE SEQUENCE contact_email_mailaddress__seq;
CREATE TABLE contact_emailaddress (
  emailaddress_id INTEGER  NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  emailaddress_type INTEGER REFERENCES lookup_contactemail_types(code),
  email NVARCHAR2(256),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_email CHAR(1) DEFAULT 0 NOT NULL,
  PRIMARY KEY (EMAILADDRESS_ID)
);

CREATE INDEX contact_email_contact_id_idx ON contact_emailaddress ( contact_id );
CREATE INDEX contact_email_prim_idx ON contact_emailaddress(primary_email);


CREATE SEQUENCE contact_phone_phone_id_seq;
CREATE TABLE contact_phone (
  phone_id INTEGER NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  phone_type INTEGER REFERENCES lookup_contactphone_types(code),
  "number" NVARCHAR2(30),
  extension NVARCHAR2(10),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_number CHAR(1) DEFAULT 0 NOT NULL,
  PRIMARY KEY (PHONE_ID)
);

CREATE INDEX contact_phone_contact_id_idx ON contact_phone ( contact_id );

-- Old Name: contact_imaddress_address_id_seq;
CREATE SEQUENCE contact_imadd_s_address_id_seq;
CREATE TABLE contact_imaddress (
  address_id INTEGER NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  imaddress_type INTEGER REFERENCES lookup_im_types(code),
  imaddress_service INTEGER REFERENCES lookup_im_services(code),
  imaddress NVARCHAR2(256),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_im CHAR(1) DEFAULT 0 NOT NULL,
  PRIMARY KEY (ADDRESS_ID)
);


-- Old Name: contact_textmessageaddress_address_id_seq;
CREATE SEQUENCE contact_textm_s_address_id_seq;
CREATE TABLE contact_textmessageaddress (
  address_id INTEGER NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  textmessageaddress NVARCHAR2(256),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  primary_textmessage_address CHAR(1) DEFAULT 0 NOT NULL,
  textmessageaddress_type INTEGER REFERENCES lookup_textmessage_types(code),
  PRIMARY KEY (ADDRESS_ID)
);


CREATE SEQUENCE notification__tification_i_seq;
CREATE TABLE notification (
  notification_id INTEGER NOT NULL,
  notify_user INTEGER NOT NULL,
  "module" NVARCHAR2(255) NOT NULL,
  item_id INTEGER NOT NULL,
  item_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  attempt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  notify_type NVARCHAR2(30),
  subject CLOB,
  "message" CLOB,
  result INTEGER NOT NULL,
  errorMessage CLOB,
  PRIMARY KEY (NOTIFICATION_ID)
);

CREATE SEQUENCE cfsinbox_message_id_seq;
CREATE TABLE cfsinbox_message (
  id INTEGER  NOT NULL,
  subject NVARCHAR2(255) ,
  body CLOB NOT NULL,
  reply_id INTEGER NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  sent TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  "type" INTEGER  default -1 NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  delete_flag CHAR(1) DEFAULT 0,
  PRIMARY KEY (ID)
);

CREATE TABLE cfsinbox_messagelink (
  id INTEGER NOT NULL REFERENCES cfsinbox_message(id),
  sent_to INTEGER NOT NULL REFERENCES contact(contact_id),
  status INTEGER  DEFAULT 0 NOT NULL,
  viewed TIMESTAMP DEFAULT NULL,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
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
create index tcontactlevels_level on contact_type_levels ("level");

CREATE SEQUENCE lookup_lists_lookup_id_seq;
CREATE TABLE lookup_lists_lookup(
  id INTEGER NOT NULL,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  lookup_id INTEGER NOT NULL,
  class_name NVARCHAR2(20),
  table_name NVARCHAR2(60),
  "level" INTEGER DEFAULT 0,
  description CLOB,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  category_id INTEGER NOT NULL,
  PRIMARY KEY (ID)
);

CREATE SEQUENCE webdav_id_seq;
CREATE TABLE webdav (
  id INTEGER  NOT NULL,
  category_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  class_name NVARCHAR2(300) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ID)
);

CREATE SEQUENCE category_editor_lookup_id_seq;
CREATE TABLE category_editor_lookup(
  id INTEGER NOT NULL,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  constant_id INTEGER NOT NULL,
  table_name NVARCHAR2(60),
  "level" INTEGER DEFAULT 0,
  description CLOB,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  category_id INTEGER NOT NULL,
  max_levels INTEGER NOT NULL,
  PRIMARY KEY (ID)
);

CREATE SEQUENCE viewpoint_viewpoint_id_seq;
CREATE TABLE viewpoint(
  viewpoint_id INTEGER  NOT NULL,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  vp_user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (VIEWPOINT_ID)
);


CREATE SEQUENCE viewpoint_per_p_permission_seq;
CREATE TABLE viewpoint_permission (
 vp_permission_id INTEGER  NOT NULL,
 viewpoint_id INTEGER NOT NULL REFERENCES viewpoint(viewpoint_id),
 permission_id INTEGER NOT NULL REFERENCES permission(permission_id),
 viewpoint_view CHAR(1) DEFAULT 0 NOT NULL,
 viewpoint_add CHAR(1) DEFAULT 0 NOT NULL,
 viewpoint_edit CHAR(1) DEFAULT 0  NOT NULL,
 viewpoint_delete CHAR(1) DEFAULT 0  NOT NULL,
 PRIMARY KEY (VP_PERMISSION_ID)
);

CREATE SEQUENCE report_report_id_seq;
CREATE TABLE report (
  report_id INTEGER  NOT NULL,
  category_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  permission_id INTEGER REFERENCES permission(permission_id),
  filename NVARCHAR2(300) NOT NULL,
  "type" INTEGER DEFAULT 1 NOT NULL,
  title NVARCHAR2(300) NOT NULL,
  description NVARCHAR2(1024) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  custom CHAR(1) DEFAULT 0 NOT NULL,
  PRIMARY KEY (REPORT_ID)
);

CREATE SEQUENCE report_criter__criteria_id_seq;
CREATE TABLE report_criteria (
  criteria_id INTEGER  NOT NULL,
  report_id INTEGER NOT NULL REFERENCES report(report_id),
  owner INTEGER NOT NULL REFERENCES "access"(user_id),
  subject NVARCHAR2(512) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CRITERIA_ID)
);

-- Old Name: report_criteria_parameter_parameter_id_seq;
CREATE SEQUENCE report_criter_parameter_id_seq;
CREATE TABLE report_criteria_parameter (
  parameter_id INTEGER  NOT NULL,
  criteria_id INTEGER NOT NULL REFERENCES report_criteria(criteria_id),
  "parameter" NVARCHAR2(255) NOT NULL,
  "value" CLOB,
  PRIMARY KEY (PARAMETER_ID)
);

CREATE SEQUENCE report_queue_queue_id_seq;
CREATE TABLE report_queue (
  queue_id INTEGER NOT NULL,
  report_id INTEGER NOT NULL REFERENCES report(report_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  processed TIMESTAMP ,
  status INTEGER  DEFAULT 0 NOT NULL,
  filename NVARCHAR2(256),
  filesize INTEGER DEFAULT -1,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (QUEUE_ID)
);

-- Old Name: report_queue_criteria_criteria_id_seq;
CREATE SEQUENCE report_queue___criteria_id_seq;
CREATE TABLE report_queue_criteria (
  criteria_id INTEGER NOT NULL,
  queue_id INTEGER NOT NULL REFERENCES report_queue(queue_id),
  "parameter" NVARCHAR2(255) NOT NULL,
  "value" CLOB,
  PRIMARY KEY (CRITERIA_ID)
);

CREATE SEQUENCE action_list_code_seq;
CREATE TABLE action_list (
  action_id INTEGER  NOT NULL,
  description NVARCHAR2(255) NOT NULL,
  owner INTEGER NOT NULL REFERENCES "access"(user_id),
  completedate TIMESTAMP,
  link_module_id INTEGER NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  PRIMARY KEY (ACTION_ID)
);

CREATE SEQUENCE action_item_code_seq;
CREATE TABLE action_item (
  item_id INTEGER  NOT NULL,
  action_id INTEGER NOT NULL REFERENCES action_list(action_id),
  link_item_id INTEGER NOT NULL,
  completedate TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  PRIMARY KEY (ITEM_ID)
);


CREATE SEQUENCE action_item_log_code_seq;
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


CREATE SEQUENCE import_import_id_seq;
CREATE TABLE import(
  import_id INTEGER  NOT NULL,
  "type" INTEGER NOT NULL,
  name NVARCHAR2(250) NOT NULL,
  description CLOB,
  source_type INTEGER,
  source NVARCHAR2(1000),
  record_delimiter NVARCHAR2(10),
  column_delimiter NVARCHAR2(10),
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
  comments CLOB,
  PRIMARY KEY (IMPORT_ID)
);

CREATE INDEX import_entered_idx ON import ( entered );
CREATE INDEX import_name_idx ON import ( name );


CREATE SEQUENCE database_vers_n_version_id_seq;
CREATE TABLE database_version (
  version_id INTEGER NOT NULL,
  script_filename NVARCHAR2(255) NOT NULL,
  script_version NVARCHAR2(255) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (VERSION_ID)
);

-- Old Name: lookup_relationship_types_type_id_seq;
CREATE SEQUENCE lookup_relati_ypes_type_id_seq;
CREATE TABLE lookup_relationship_types (
  type_id INTEGER NOT NULL,
  category_id_maps_from INTEGER NOT NULL,
  category_id_maps_to INTEGER NOT NULL,
  reciprocal_name_1 NVARCHAR2(512),
  reciprocal_name_2 NVARCHAR2(512),
  "level" INTEGER DEFAULT 0,
  default_item CHAR(1) DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (TYPE_ID)
);

-- Old Name: relationship_relationship_id_seq;
CREATE SEQUENCE relationship__ationship_id_seq;
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
CREATE SEQUENCE user_group_group_id_seq;
CREATE TABLE user_group (
  group_id INT NOT NULL PRIMARY KEY,
  group_name NVARCHAR2(255) NOT NULL,
  description CLOB,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  site_id INTEGER REFERENCES lookup_site_id(code)
);

-- Create the user group map table
CREATE SEQUENCE user_group_ma_group_map_id_seq;
CREATE TABLE user_group_map (
  group_map_id INT NOT NULL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES "access"(user_id),
  group_id INTEGER NOT NULL REFERENCES user_group(group_id),
  "level" INTEGER DEFAULT 10 NOT NULL,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Custom List Views Editor
CREATE SEQUENCE custom_list_v_or_editor_id_seq;
CREATE TABLE custom_list_view_editor (
  editor_id INT NOT NULL PRIMARY KEY,
  module_id INT NOT NULL REFERENCES permission_category(category_id),
  constant_id INT NOT NULL,
  description CLOB,
  "level" INT default 0,
  category_id INT NOT NULL
);

-- Custom List View
CREATE SEQUENCE custom_list_view_view_id_seq;
CREATE TABLE custom_list_view (
  view_id INT NOT NULL PRIMARY KEY,
  editor_id INT NOT NULL REFERENCES custom_list_view_editor(editor_id),
  name NVARCHAR2(80) NOT NULL,
  description CLOB,
  is_default CHAR(1) DEFAULT 0,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Custom List View Field
CREATE SEQUENCE custom_list_v_eld_field_id_seq;
CREATE TABLE custom_list_view_field (
  field_id INT NOT NULL PRIMARY KEY,
  view_id INT NOT NULL REFERENCES custom_list_view(view_id),
  name NVARCHAR2(80) NOT NULL
);

