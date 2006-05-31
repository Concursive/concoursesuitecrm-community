-- ACCESS TABLE IS CREATED
CREATE SEQUENCE lookup_site_id_code_seq  start with 0 minvalue -1  increment by 1;
CREATE TABLE lookup_site_id (
  code INT PRIMARY KEY, 
  description VARCHAR(300) NOT NULL, 
  short_description VARCHAR(300), 
  default_item boolean DEFAULT false, 
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true 
);

CREATE SEQUENCE access_user_id_seq  start with 0 minvalue -1  increment by 1;
CREATE TABLE access (
  user_id INT  PRIMARY KEY,
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
  last_login timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL,
  entered timestamp  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL,
  modified timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  expires timestamp DEFAULT NULL,
  alias INT DEFAULT -1,
  assistant INT DEFAULT -1,
  enabled boolean DEFAULT true NOT NULL,
  currency VARCHAR(5),
  "language" VARCHAR(20),
  webdav_password VARCHAR(80),
  hidden boolean DEFAULT false,
  site_id INT REFERENCES lookup_site_id(code),
  allow_webdav_access BOOLEAN DEFAULT true NOT NULL,
  allow_httpapi_access BOOLEAN DEFAULT true NOT NULL
);

CREATE SEQUENCE lookup_industry_code_seq;
CREATE TABLE lookup_industry (
  code INT PRIMARY KEY,
  order_id INT,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE access_log_id_seq;
CREATE TABLE access_log (
  id INT  PRIMARY KEY,
  user_id INT REFERENCES access(user_id) NOT NULL ,
  username VARCHAR(80) NOT NULL,
  ip VARCHAR(15),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  browser VARCHAR(255)
);


CREATE SEQUENCE usage_log_usage_id_seq;
CREATE TABLE usage_log (
  usage_id INT PRIMARY KEY,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT ,
  action INT NOT NULL,
  record_id INT ,
  record_size INT
);

CREATE SEQUENCE lookup_contact_types_code_seq;
CREATE TABLE lookup_contact_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  user_id INT references access(user_id),
  category INT  DEFAULT 0 NOT NULL
);

CREATE SEQUENCE lookup_account_types_code_seq;
CREATE TABLE lookup_account_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE TABLE state (
  state_code CHAR(2)PRIMARY KEY NOT NULL,
  state VARCHAR(80) NOT NULL
);

CREATE SEQUENCE lookup_department_code_seq;
CREATE TABLE lookup_department (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_orgaddress_type_code_seq;
CREATE TABLE lookup_orgaddress_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_orgemail_types_code_seq;
CREATE TABLE lookup_orgemail_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_orgphone_types_code_seq;
CREATE TABLE lookup_orgphone_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_im_types_code_seq;
CREATE TABLE lookup_im_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_im_services_code_seq;
CREATE TABLE lookup_im_services (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_contact_source_code_seq;
CREATE TABLE lookup_contact_source (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_contact_rating_code_seq;
CREATE TABLE lookup_contact_rating (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_textmessage_typ_code_seq;
CREATE TABLE lookup_textmessage_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);


CREATE SEQUENCE lookup_employment_type_code_seq;
CREATE TABLE lookup_employment_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_locale_code_seq;
CREATE TABLE lookup_locale (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_contactaddress__code_seq;
CREATE TABLE lookup_contactaddress_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_contactemail_ty_code_seq;
CREATE TABLE lookup_contactemail_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_contactphone_ty_code_seq;
CREATE TABLE lookup_contactphone_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_access_types_code_seq;
CREATE TABLE lookup_access_types (
  code INT PRIMARY KEY,
  link_module_id INT NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER, 
  enabled boolean DEFAULT true,
  rule_id INT NOT NULL
);

CREATE SEQUENCE lookup_account_size_code_seq;
CREATE TABLE lookup_account_size (
  code INT PRIMARY KEY, 
  description VARCHAR(300) NOT NULL, 
  default_item boolean DEFAULT false, 
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true 
);

CREATE SEQUENCE lookup_segments_code_seq;
CREATE TABLE lookup_segments (
  code INT PRIMARY KEY, 
  description VARCHAR(300) NOT NULL, 
  default_item boolean DEFAULT false, 
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true 
);

CREATE SEQUENCE lookup_sub_segment_code_seq;
CREATE TABLE lookup_sub_segment (
  code INT PRIMARY KEY, 
  description VARCHAR(300) NOT NULL, 
  segment_id  INT REFERENCES lookup_segments(code),
  default_item boolean DEFAULT false, 
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true 
);

CREATE SEQUENCE lookup_title_code_seq;
CREATE TABLE lookup_title (
  code INT PRIMARY KEY, 
  description VARCHAR(300) NOT NULL, 
  default_item boolean DEFAULT false, 
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true 
);

CREATE SEQUENCE organization_org_id_seq start with 0 minvalue -1  increment by 1;
CREATE TABLE organization (
  org_id INT  PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  account_number VARCHAR(50),
  account_group INT,
  url CLOB,
  revenue FLOAT,
  employees INT,
  notes CLOB,
  sic_code VARCHAR(40),
  ticker_symbol VARCHAR(10) ,
  taxid CHAR(80),
  lead VARCHAR(40),
  sales_rep int DEFAULT 0 NOT NULL,
  miner_only boolean DEFAULT false NOT NULL,
  defaultlocale INT,
  fiscalmonth INT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT  references access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT references access(user_id) NOT NULL ,
  enabled boolean DEFAULT true,
  industry_temp_code SMALLINT,
  owner INT references access(user_id),
  duplicate_id int default -1,
  custom1 int default -1,
  custom2 int default -1,
  contract_end TIMESTAMP ,
  alertdate TIMESTAMP ,
  alert varchar(100) ,
  custom_data CLOB,
  namesalutation varchar(80),
  namelast varchar(80),
  namefirst varchar(80),
  namemiddle varchar(80),
  namesuffix varchar(80),
  import_id INT,
  status_id INT,
  alertdate_timezone VARCHAR(255),
  contract_end_timezone VARCHAR(255),
  trashed_date TIMESTAMP,
  source INTEGER references lookup_contact_source(code),
  rating INTEGER references lookup_contact_rating(code),
  potential FLOAT,
  segment_id INT references lookup_segments(code),
  sub_segment_id INT references lookup_sub_segment(code),
  direct_bill BOOLEAN DEFAULT false,
  account_size INT references lookup_account_size(code),
  site_id INT references lookup_site_id(code)
);

CREATE INDEX "orglist_name" ON "organization" (name);

CREATE SEQUENCE contact_contact_id_seq;
CREATE TABLE contact (
  contact_id INT  PRIMARY KEY,
  user_id INT references access(user_id),
  org_id int REFERENCES organization(org_id),
  company VARCHAR(255),
  title VARCHAR(80),
  department INT references lookup_department(code),
  super INT REFERENCES contact,
  namesalutation varchar(80),
  namelast VARCHAR(80) NOT NULL,
  namefirst VARCHAR(80) NOT NULL,
  namemiddle VARCHAR(80),
  namesuffix VARCHAR(80),
  assistant INT REFERENCES contact,
  birthdate TIMESTAMP,
  notes CLOB,
  site INT,
  locale INT,
  employee_id varchar(80),
  employmenttype INT,
  startofday VARCHAR(10),
  endofday VARCHAR(10),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL,
  enabled boolean DEFAULT true,
  owner INT REFERENCES access(user_id),
  custom1 int default -1,
  url VARCHAR(100),
  primary_contact boolean DEFAULT false,
  employee boolean DEFAULT false NOT NULL,
  org_name VARCHAR(255),
  access_type INT REFERENCES lookup_access_types(code),
  status_id INT,
  import_id INT,
  information_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lead boolean DEFAULT false,
  lead_status INT,
  source INT REFERENCES lookup_contact_source(code),
  rating INT REFERENCES lookup_contact_rating(code),
  comments VARCHAR(255),
  conversion_date TIMESTAMP(3),
  additional_names VARCHAR(255),
  nickname VARCHAR(80),
  "role" VARCHAR(255),
  trashed_date TIMESTAMP,
  secret_word VARCHAR(255),
  account_number VARCHAR(50),
  revenue FLOAT,
  industry_temp_code INTEGER REFERENCES lookup_industry(code),
  potential FLOAT,
  no_email boolean DEFAULT false,
  no_mail boolean DEFAULT false,
  no_phone boolean DEFAULT false,
  no_textmessage boolean DEFAULT false,
  no_im boolean DEFAULT false,
  no_fax boolean DEFAULT false,
  site_id INTEGER REFERENCES lookup_site_id(code)
);

CREATE INDEX "contact_user_id_idx" ON "contact" ("user_id");
CREATE INDEX "contactlist_namecompany" ON "contact" (namelast, namefirst, company);
CREATE INDEX "contactlist_company" ON "contact" (company, namelast, namefirst);
CREATE INDEX "contact_import_id_idx" ON "contact" ("import_id");
CREATE INDEX contact_org_id_idx ON contact(org_id);
CREATE INDEX contact_islead_idx ON contact(lead);


CREATE SEQUENCE contact_lead_skipped_map_map_id_seq;
CREATE TABLE contact_lead_skipped_map (
  map_id INT PRIMARY KEY,
  user_id INT NOT NULL REFERENCES access(user_id),
  contact_id INT NOT NULL REFERENCES contact(contact_id)
);

CREATE INDEX contact_lead_skip_u_idx ON contact_lead_skipped_map(user_id);


CREATE SEQUENCE contact_lead_read_map_map_id_seq;
CREATE TABLE contact_lead_read_map (
  map_id INT PRIMARY KEY,
  user_id INT NOT NULL REFERENCES access(user_id),
  contact_id INT NOT NULL REFERENCES contact(contact_id)
);

CREATE INDEX contact_lead_read_u_idx ON contact_lead_read_map(user_id);
CREATE INDEX contact_lead_read_c_idx ON contact_lead_read_map(contact_id);

CREATE SEQUENCE role_role_id_seq;
CREATE TABLE "role" (
  role_id INT  PRIMARY KEY,
  "role" VARCHAR(80) NOT NULL,
  description VARCHAR(255) DEFAULT '' NOT NULL,
  enteredby INT REFERENCES access(user_id) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled boolean DEFAULT true NOT NULL,
  role_type INT
);

CREATE SEQUENCE permission_cate_category_id_seq;
CREATE TABLE permission_category (
  category_id INT PRIMARY KEY,
  category VARCHAR(80),
  description VARCHAR(255),
  "level" INT DEFAULT 0 NOT NULL,
  enabled boolean DEFAULT true NOT NULL,
  active boolean DEFAULT true NOT NULL,
  folders boolean DEFAULT false NOT NULL,
  lookups boolean  DEFAULT false NOT NULL,
  viewpoints boolean DEFAULT false,
  categories boolean DEFAULT false NOT NULL,
  scheduled_events boolean DEFAULT false NOT NULL,
  object_events boolean DEFAULT false NOT NULL,
  reports boolean DEFAULT false NOT NULL,
  products boolean DEFAULT false NOT NULL,
  webdav boolean DEFAULT false NOT NULL,
	logos boolean DEFAULT false NOT NULL,
	constant INT NOT NULL,
	action_plans BOOLEAN DEFAULT false NOT NULL,
	custom_list_views BOOLEAN DEFAULT false NOT NULL
);


CREATE SEQUENCE permission_permission_id_seq;
CREATE TABLE permission (
  permission_id INT  PRIMARY KEY,
  category_id INT  REFERENCES permission_category NOT NULL,
  permission VARCHAR(80) NOT NULL,
  permission_view boolean  DEFAULT false NOT NULL,
  permission_add boolean  DEFAULT false NOT NULL,
  permission_edit boolean  DEFAULT false NOT NULL,
  permission_delete boolean  DEFAULT false NOT NULL,
  description VARCHAR(255)  DEFAULT '' NOT NULL,
  "level" INT  DEFAULT 0 NOT NULL,
  enabled boolean  DEFAULT true NOT NULL,
  active boolean DEFAULT true NOT NULL,
  viewpoints boolean DEFAULT false
);


CREATE SEQUENCE role_permission_id_seq;
CREATE TABLE role_permission (
  id INT  PRIMARY KEY,
  role_id INT REFERENCES "role"(role_id) NOT NULL,
  permission_id INT REFERENCES permission(permission_id) NOT NULL,
  role_view boolean  DEFAULT false NOT NULL,
  role_add boolean DEFAULT false NOT NULL,
  role_edit boolean DEFAULT false NOT NULL,
  role_delete boolean DEFAULT false NOT NULL
);


CREATE SEQUENCE lookup_stage_code_seq;
CREATE TABLE lookup_stage (
  code INT PRIMARY KEY,
  order_id INT,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_delivery_option_code_seq;
CREATE TABLE lookup_delivery_options (
  code INT PRIMARY KEY,
  description VARCHAR(100) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE news_rec_id_seq;
CREATE TABLE news (
  rec_id INT PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  url CLOB,
  base CLOB,
  headline CLOB,
  body CLOB,
  dateEntered TIMESTAMP,
  type CHAR(1),
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE SEQUENCE organization_add_address_id_seq;
CREATE TABLE organization_address (
  address_id INT  PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  address_type INT references lookup_orgaddress_types(code),
  addrline1 VARCHAR(80),
  addrline2 VARCHAR(80),
  addrline3 VARCHAR(80),
  city VARCHAR(80),
  state VARCHAR(80),
  country VARCHAR(80),
  postalcode VARCHAR(12),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT  references access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT references access(user_id) NOT NULL,
  primary_address boolean DEFAULT false NOT NULL,
  addrline4 VARCHAR(80)
);

CREATE INDEX organization_address_postalcode_idx ON organization_address(postalcode);

CREATE SEQUENCE organization__emailaddress__seq;
CREATE TABLE organization_emailaddress (
  emailaddress_id INT  PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  emailaddress_type INT references lookup_orgemail_types(code),
  email VARCHAR(256),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT  references access(user_id) NOT NULL,
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT  references access(user_id) NOT NULL,
  primary_email boolean DEFAULT false NOT NULL
);


CREATE SEQUENCE organization_phone_phone_id_seq;
CREATE TABLE organization_phone (
  phone_id INT  PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  phone_type INT references lookup_orgphone_types(code),
  "number" VARCHAR(30),
  extension VARCHAR(10),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT references access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT  references access(user_id) NOT NULL,
  primary_number boolean DEFAULT false NOT NULL
);


CREATE SEQUENCE contact_address_address_id_seq;
CREATE TABLE contact_address (
  address_id INT  PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  address_type INT references lookup_contactaddress_types(code),
  addrline1 VARCHAR(80),
  addrline2 VARCHAR(80),
  addrline3 VARCHAR(80),
  city VARCHAR(80),
  state VARCHAR(80),
  country VARCHAR(80),
  postalcode VARCHAR(12),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT  references access(user_id) NOT NULL,
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT  references access(user_id) NOT NULL,
  primary_address boolean DEFAULT false NOT NULL,
  addrline4 VARCHAR(80)
);

CREATE INDEX "contact_address_contact_id_idx" ON "contact_address" (contact_id);
CREATE INDEX contact_address_postalcode_idx ON contact_address(postalcode);
CREATE INDEX "contact_city_idx" on contact_address(city);
CREATE INDEX contact_address_prim_idx ON contact_address(primary_address);


CREATE SEQUENCE contact_email_emailaddress__seq;
CREATE TABLE contact_emailaddress (
  emailaddress_id INT  PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  emailaddress_type INT references lookup_contactemail_types(code),
  email VARCHAR(256),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT references access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT references access(user_id) NOT NULL,
  primary_email boolean DEFAULT false NOT NULL
);

CREATE INDEX "contact_email_contact_id_idx" ON "contact_emailaddress" (contact_id);
CREATE INDEX contact_email_prim_idx ON contact_emailaddress(primary_email);


CREATE SEQUENCE contact_phone_phone_id_seq;
CREATE TABLE contact_phone (
  phone_id INT PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  phone_type INT references lookup_contactphone_types(code),
  "number" VARCHAR(30),
  extension VARCHAR(10),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT references access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT  references access(user_id) NOT NULL,
  primary_number boolean DEFAULT false NOT NULL
);

CREATE INDEX "contact_phone_contact_id_idx" ON "contact_phone" (contact_id);

CREATE SEQUENCE contact_imaddress_address_id_seq;
CREATE TABLE contact_imaddress (
  address_id INT PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  imaddress_type INT references lookup_im_types(code),
  imaddress_service INT references lookup_im_services(code),
  imaddress VARCHAR(256),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT  references access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT references access(user_id) NOT NULL ,
  primary_im boolean DEFAULT false NOT NULL
);


CREATE SEQUENCE contact_textmessageaddress_address_id_seq;
CREATE TABLE contact_textmessageaddress (
  address_id INT  PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  textmessageaddress VARCHAR(256),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT references access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT  references access(user_id) NOT NULL ,
  primary_textmessage_address boolean DEFAULT false NOT NULL,
  textmessageaddress_type INT references lookup_textmessage_types(code)
);


CREATE SEQUENCE notification_notification_i_seq;
CREATE TABLE notification (
  notification_id INT PRIMARY KEY,
  notify_user INT NOT NULL,
  "module" VARCHAR(255) NOT NULL,
  item_id INT NOT NULL,
  item_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  attempt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  notify_type VARCHAR(30),
  subject CLOB,
  message CLOB,
  result INT NOT NULL,
  errorMessage CLOB
);

CREATE SEQUENCE cfsinbox_message_id_seq;
CREATE TABLE cfsinbox_message (
  id INT  PRIMARY KEY,
  subject VARCHAR(255) ,
  body CLOB NOT NULL,
  reply_id INT NOT NULL,
  enteredby INT REFERENCES access(user_id)  NOT NULL ,
  sent TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  type int  default -1 not null,
  modifiedby INT REFERENCES access(user_id)  NOT NULL,
  delete_flag boolean default false
);

CREATE TABLE cfsinbox_messagelink (
  id INT REFERENCES cfsinbox_message(id) NOT NULL,
  sent_to INT REFERENCES contact(contact_id)  NOT NULL,
  status INT  DEFAULT 0 NOT NULL,
  viewed TIMESTAMP DEFAULT NULL,
  enabled boolean  DEFAULT true NOT NULL,
  sent_from INT REFERENCES access(user_id) NOT NULL
);
  
CREATE TABLE account_type_levels (
  org_id INT REFERENCES organization(org_id) NOT NULL ,
  type_id INT REFERENCES lookup_account_types(code)  NOT NULL,
  "level" INTEGER not null,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL 
);

CREATE TABLE contact_type_levels (
  contact_id INT  REFERENCES contact(contact_id) NOT NULL ,
  type_id INT REFERENCES lookup_contact_types(code) NOT NULL,
  "level" INTEGER not null,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE SEQUENCE lookup_lists_lookup_id_seq;
CREATE TABLE lookup_lists_lookup(
  id INT PRIMARY KEY,
 module_id INTEGER REFERENCES permission_category(category_id) NOT NULL,
  lookup_id INT NOT NULL,
  class_name VARCHAR(20),
  table_name VARCHAR(60),
  "level" INTEGER DEFAULT 0,
  description CLOB,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  category_id INT NOT NULL
);

CREATE SEQUENCE webdav_id_seq;
CREATE TABLE webdav (
  id INT  PRIMARY KEY,
  category_id INTEGER REFERENCES permission_category(category_id) NOT NULL,
  class_name VARCHAR(300) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INT REFERENCES access(user_id)  NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT  REFERENCES access(user_id)  NOT NULL
);

CREATE SEQUENCE category_editor_lookup_id_seq;
CREATE TABLE category_editor_lookup(
  id INT PRIMARY KEY,
  module_id INTEGER REFERENCES permission_category(category_id)  NOT NULL,
  constant_id INT NOT NULL,
  table_name VARCHAR(60),
  "level" INTEGER DEFAULT 0,
  description CLOB,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  category_id INT NOT NULL,
  max_levels INT NOT NULL
);

CREATE SEQUENCE viewpoint_viewpoint_id_seq;
CREATE TABLE viewpoint(
  viewpoint_id INT  PRIMARY KEY,
  user_id INT REFERENCES access(user_id) NOT NULL,
  vp_user_id INT REFERENCES access(user_id) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT  REFERENCES access(user_id) NOT NULL,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE viewpoint_per_vp_permission_seq;
CREATE TABLE viewpoint_permission (
 vp_permission_id INT  PRIMARY KEY,
 viewpoint_id INT REFERENCES viewpoint(viewpoint_id) NOT NULL,
 permission_id INT REFERENCES permission(permission_id) NOT NULL,
 viewpoint_view boolean DEFAULT false NOT NULL,
 viewpoint_add boolean DEFAULT false NOT NULL,
 viewpoint_edit boolean DEFAULT false  NOT NULL,
 viewpoint_delete boolean DEFAULT false  NOT NULL
);

CREATE SEQUENCE report_report_id_seq;
CREATE TABLE report (
  report_id INT  PRIMARY KEY,
  category_id INT  REFERENCES permission_category(category_id)  NOT NULL,
  permission_id INT REFERENCES permission(permission_id),
  filename VARCHAR(300) NOT NULL,
  type INTEGER  DEFAULT 1  NOT NULL,
  title VARCHAR(300) NOT NULL,
  description VARCHAR(1024) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  enteredby INT REFERENCES access(user_id)  NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INT REFERENCES access(user_id)  NOT NULL,
  enabled boolean  DEFAULT true NOT NULL,
  custom boolean DEFAULT false NOT NULL 
);

CREATE SEQUENCE report_criteria_criteria_id_seq;
CREATE TABLE report_criteria (
  criteria_id INT  PRIMARY KEY,
  report_id INT  REFERENCES report(report_id) NOT NULL,
  owner INT REFERENCES access(user_id) NOT NULL,
  subject VARCHAR(512) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT  REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL,
  enabled boolean DEFAULT true
);


CREATE SEQUENCE report_criteria_parameter_parameter_id_seq;
CREATE TABLE report_criteria_parameter (
  parameter_id INT  PRIMARY KEY,
  criteria_id INTEGER REFERENCES report_criteria(criteria_id) NOT NULL,
  "parameter" VARCHAR(255) NOT NULL,
  value CLOB
);


CREATE SEQUENCE report_queue_queue_id_seq;
CREATE TABLE report_queue (
  queue_id INT PRIMARY KEY,
  report_id INTEGER REFERENCES report(report_id) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT REFERENCES access(user_id) NOT NULL,
  processed TIMESTAMP ,
  status INT  DEFAULT 0 NOT NULL,
  filename VARCHAR(256),
  filesize INT DEFAULT -1,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE report_queue_criteria_criteria_id_seq;
CREATE TABLE report_queue_criteria (
  criteria_id INT PRIMARY KEY,
  queue_id INTEGER REFERENCES report_queue(queue_id) NOT NULL ,
  "parameter" VARCHAR(255) NOT NULL,
  value CLOB
);
 

CREATE SEQUENCE action_list_code_seq;
CREATE TABLE action_list (
  action_id INT  PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  owner INT references access(user_id) NOT NULL ,
  completedate TIMESTAMP,
  link_module_id INT NOT NULL,
  enteredby INT REFERENCES access(user_id) NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL , 
  modifiedby INT REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enabled boolean DEFAULT true NOT NULL
);


CREATE SEQUENCE action_item_code_seq;
CREATE TABLE action_item (
  item_id INT  PRIMARY KEY,
  action_id INT references action_list(action_id) NOT NULL,
  link_item_id INT NOT NULL,
  completedate TIMESTAMP,
  enteredby INT REFERENCES access(user_id) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enabled boolean DEFAULT true NOT NULL
);


CREATE SEQUENCE action_item_log_code_seq;
CREATE TABLE action_item_log (
  log_id INT  PRIMARY KEY,
  item_id INT  references action_item(item_id) NOT NULL,
  link_item_id INT DEFAULT -1,
  type INT NOT NULL,
  enteredby INT  REFERENCES access(user_id) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT  REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL
);


CREATE SEQUENCE import_import_id_seq;
CREATE TABLE import(
  import_id INT  PRIMARY KEY,
  type INT NOT NULL,
  name VARCHAR(250) NOT NULL,
  description CLOB,
  source_type INT,
  source VARCHAR(1000),
  record_delimiter VARCHAR(10),
  column_delimiter VARCHAR(10),
  total_imported_records INT,
  total_failed_records INT,
  status_id INT,
  file_type INT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,  
  enteredby INT REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT  REFERENCES access(user_id) NOT NULL, 
  site_id INT REFERENCES lookup_site_id(code),
  rating INT REFERENCES lookup_contact_rating(code),
  comments CLOB
);

CREATE INDEX "import_entered_idx" ON "import" (entered);
CREATE INDEX "import_name_idx" ON "import" (name);


CREATE SEQUENCE database_version_version_id_seq;
CREATE TABLE database_version (
  version_id INT PRIMARY KEY,
  script_filename VARCHAR(255) NOT NULL,
  script_version VARCHAR(255) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL 
);

CREATE SEQUENCE lookup_relationship_types_type_id_seq;
CREATE TABLE lookup_relationship_types (
  type_id INT PRIMARY KEY,
  category_id_maps_from INT NOT NULL,
  category_id_maps_to INT NOT NULL,
  reciprocal_name_1 VARCHAR(512),
  reciprocal_name_2 VARCHAR(512),
  "level" INTEGER DEFAULT 0,
  default_item boolean DEFAULT false,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE relationship_relationship_id_seq;
CREATE TABLE relationship (
  relationship_id INT  PRIMARY KEY,
  type_id INT REFERENCES lookup_relationship_types(type_id),
  object_id_maps_from INT NOT NULL,
  category_id_maps_from INT NOT NULL,
  object_id_maps_to INT NOT NULL,
  category_id_maps_to INT NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT NOT NULL,
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT NOT NULL,
  trashed_date TIMESTAMP
);

CREATE SEQUENCE user_group_group_id_seq;
CREATE TABLE user_group (
  group_id INT PRIMARY KEY,
  group_name VARCHAR(255) NOT NULL,
  description CLOB,
  enabled boolean DEFAULT true NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  site_id INTEGER REFERENCES lookup_site_id(code)
);

CREATE SEQUENCE user_group_map_group_map_id_seq;
CREATE TABLE user_group_map (
  group_map_id INT PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  group_id INTEGER NOT NULL REFERENCES user_group(group_id),
  "level" INTEGER DEFAULT 10 NOT NULL,
  enabled BOOLEAN DEFAULT true NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Custom List Views Editor
CREATE SEQUENCE custom_list_view_editor_editor_id_seq;
CREATE TABLE custom_list_view_editor (
  editor_id INT PRIMARY KEY,
  module_id INT NOT NULL REFERENCES permission_category(category_id),
  constant_id INT NOT NULL,
  description VARCHAR(1000),
  "level" INT default 0,
  category_id INT NOT NULL
);

-- Custom List View
CREATE SEQUENCE custom_list_view_view_id_seq;
CREATE TABLE custom_list_view (
  view_id INT PRIMARY KEY,
  editor_id INT NOT NULL REFERENCES custom_list_view_editor(editor_id),
  name VARCHAR(80) NOT NULL,
  description VARCHAR(1000),
  is_default BOOLEAN DEFAULT FALSE,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);

-- Custom List View Field
CREATE SEQUENCE custom_list_view_field_field_id_seq;
CREATE TABLE custom_list_view_field (
  field_id INT PRIMARY KEY,
  view_id INT NOT NULL REFERENCES custom_list_view(view_id),
  name VARCHAR(80) NOT NULL
);