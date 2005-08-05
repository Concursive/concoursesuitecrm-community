/**
 *  PostgreSQL Table Creation
 *
 *@version    $Id$
 */

CREATE SEQUENCE access_user_id_seq MINVALUE 0 START 0;
CREATE TABLE access (
  user_id INTEGER DEFAULT nextval('access_user_id_seq') NOT NULL PRIMARY KEY,
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
  last_login TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  expires TIMESTAMP(3) DEFAULT NULL,
  alias INT DEFAULT -1,
  assistant INT DEFAULT -1,
  enabled BOOLEAN NOT NULL DEFAULT true,
  currency VARCHAR(5),
  language VARCHAR(20),
  webdav_password VARCHAR(80),
  hidden BOOLEAN DEFAULT false
);

CREATE TABLE lookup_industry (
  code SERIAL PRIMARY KEY,
  order_id INT,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE access_log (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES access(user_id),
  username VARCHAR(80) NOT NULL,
  ip VARCHAR(15),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  browser VARCHAR(255)
);

CREATE TABLE usage_log (
  usage_id SERIAL PRIMARY KEY,
  entered TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NULL,
  action INT NOT NULL,
  record_id INT NULL,
  record_size INT NULL
);

CREATE TABLE lookup_contact_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  user_id INT references access(user_id),
  category INT NOT NULL DEFAULT 0
);

CREATE TABLE lookup_account_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE state (
  state_code CHAR(2) PRIMARY KEY NOT NULL,
  state VARCHAR(80) NOT NULL
);


CREATE TABLE lookup_department (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE SEQUENCE lookup_orgaddress_type_code_seq;
CREATE TABLE lookup_orgaddress_types (
  code INTEGER DEFAULT nextval('lookup_orgaddress_type_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_orgemail_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
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

CREATE TABLE lookup_im_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_im_services (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE SEQUENCE lookup_contact_source_code_seq;
CREATE TABLE lookup_contact_source (
  code INTEGER DEFAULT nextval('lookup_contact_source_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE SEQUENCE lookup_contact_rating_code_seq;
CREATE TABLE lookup_contact_rating (
  code INTEGER DEFAULT nextval('lookup_contact_rating_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE SEQUENCE lookup_textmessage_typ_code_seq;
CREATE TABLE lookup_textmessage_types (
  code INTEGER DEFAULT nextval('lookup_textmessage_typ_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);


CREATE SEQUENCE lookup_employment_type_code_seq;
CREATE TABLE lookup_employment_types (
  code INTEGER DEFAULT nextval('lookup_employment_type_code_seq') NOT NULL PRIMARY KEY,
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

CREATE SEQUENCE lookup_contactaddress__code_seq;
CREATE TABLE lookup_contactaddress_types (
  code INTEGER DEFAULT nextval('lookup_contactaddress__code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE SEQUENCE lookup_contactemail_ty_code_seq;
CREATE TABLE lookup_contactemail_types (
  code INTEGER DEFAULT nextval('lookup_contactemail_ty_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE SEQUENCE lookup_contactphone_ty_code_seq;
CREATE TABLE lookup_contactphone_types (
  code INTEGER DEFAULT nextval('lookup_contactphone_ty_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE TABLE lookup_access_types (
  code SERIAL PRIMARY KEY,
  link_module_id INT NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER, 
  enabled BOOLEAN DEFAULT true,
  rule_id INT NOT NULL
);

CREATE SEQUENCE organization_org_id_seq MINVALUE 0 START 0;
CREATE TABLE organization (
  org_id INTEGER DEFAULT nextval('organization_org_id_seq') NOT NULL PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  account_number VARCHAR(50),
  account_group INT,
  url TEXT,
  revenue FLOAT,
  employees INT,
  notes TEXT,
  sic_code VARCHAR(40),
  ticker_symbol VARCHAR(10) DEFAULT NULL,
  taxid CHAR(80),
  lead VARCHAR(40),
  sales_rep int NOT NULL DEFAULT 0, 
  miner_only BOOLEAN NOT NULL DEFAULT false,
  defaultlocale INT,
  fiscalmonth INT,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  enabled BOOLEAN DEFAULT true,
  industry_temp_code SMALLINT,
  owner INT references access(user_id),
  duplicate_id int default -1,
  custom1 int default -1,
  custom2 int default -1,
  contract_end TIMESTAMP(3) default null,
  alertdate TIMESTAMP(3) default null,
  alert varchar(100) default null,
  custom_data TEXT,
  namesalutation varchar(80),
  namelast varchar(80),
  namefirst varchar(80),
  namemiddle varchar(80),
  namesuffix varchar(80),
  import_id INT,
  status_id INT,
  alertdate_timezone VARCHAR(255),
  contract_end_timezone VARCHAR(255),
  trashed_date TIMESTAMP(3)
);

CREATE INDEX "orglist_name" ON "organization" (name);

CREATE TABLE contact (
  contact_id SERIAL PRIMARY KEY,
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
  birthdate DATE,
  notes TEXT,
  site INT,
  locale INT,
  employee_id varchar(80) UNIQUE,
  employmenttype INT,
  startofday VARCHAR(10),
  endofday VARCHAR(10),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  enabled BOOLEAN DEFAULT true,
  owner INT REFERENCES access(user_id),
  custom1 int default -1,
  url VARCHAR(100),
  primary_contact BOOLEAN DEFAULT false,
  employee boolean NOT NULL DEFAULT false,
  org_name VARCHAR(255),
  access_type INT REFERENCES lookup_access_types(code),
  status_id INT,
  import_id INT,
  information_update_date TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  lead BOOLEAN DEFAULT false,
  lead_status INT NULL,
  source INT REFERENCES lookup_contact_source(code),
  rating INT REFERENCES lookup_contact_rating(code),
  comments VARCHAR(255) NULL,
  conversion_date TIMESTAMP(3) NULL,
  additional_names VARCHAR(255),
  nickname VARCHAR(80),
  role VARCHAR(255),
  trashed_date TIMESTAMP(3)
);

CREATE INDEX "contact_user_id_idx" ON "contact" USING btree ("user_id");
CREATE INDEX "contactlist_namecompany" ON "contact" (namelast, namefirst, company);
CREATE INDEX "contactlist_company" ON "contact" (company, namelast, namefirst);
CREATE INDEX "contact_import_id_idx" ON "contact" ("import_id");

CREATE TABLE contact_lead_skipped_map (
  map_id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES access(user_id),
  contact_id INT NOT NULL REFERENCES contact(contact_id)
);

CREATE TABLE contact_lead_read_map (
  map_id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES access(user_id),
  contact_id INT NOT NULL REFERENCES contact(contact_id)
);

CREATE TABLE role (
  role_id SERIAL PRIMARY KEY,
  role VARCHAR(80) NOT NULL,
  description VARCHAR(255) NOT NULL DEFAULT '',
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BOOLEAN NOT NULL DEFAULT true,
  role_type INT
);

CREATE SEQUENCE permission_cate_category_id_seq;
CREATE TABLE permission_category (
  category_id INTEGER DEFAULT nextval('permission_cate_category_id_seq') NOT NULL PRIMARY KEY,
  category VARCHAR(80),
  description VARCHAR(255),
  level INT NOT NULL DEFAULT 0,
  enabled BOOLEAN NOT NULL DEFAULT true,
  active BOOLEAN NOT NULL DEFAULT true,
  folders BOOLEAN NOT NULL DEFAULT false,
  lookups BOOLEAN NOT NULL DEFAULT false,
  viewpoints BOOLEAN DEFAULT false,
  categories BOOLEAN NOT NULL DEFAULT false,
  scheduled_events BOOLEAN NOT NULL DEFAULT false,
  object_events BOOLEAN NOT NULL DEFAULT false,
  reports BOOLEAN NOT NULL DEFAULT false,
  products BOOLEAN NOT NULL DEFAULT false,
  webdav BOOLEAN NOT NULL DEFAULT false,
	logos BOOLEAN NOT NULL DEFAULT false,
  constant INT NOT NULL
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
  active BOOLEAN NOT NULL DEFAULT true,
  viewpoints BOOLEAN DEFAULT false
);

CREATE TABLE role_permission (
  id SERIAL PRIMARY KEY,
  role_id INT NOT NULL REFERENCES role(role_id),
  permission_id INT NOT NULL REFERENCES permission(permission_id),
  role_view BOOLEAN NOT NULL DEFAULT false,
  role_add BOOLEAN NOT NULL DEFAULT false,
  role_edit BOOLEAN NOT NULL DEFAULT false,
  role_delete BOOLEAN NOT NULL DEFAULT false
);


CREATE TABLE lookup_stage (
  code SERIAL PRIMARY KEY,
  order_id INT,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE SEQUENCE lookup_delivery_option_code_seq;
CREATE TABLE lookup_delivery_options (
  code INTEGER DEFAULT nextval('lookup_delivery_option_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE TABLE news (
  rec_id SERIAL PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  url TEXT,
  base TEXT,
  headline TEXT,
  body TEXT,
  dateEntered DATE,
  type CHAR(1),
  created TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE organization_add_address_id_seq;
CREATE TABLE organization_address (
  address_id INTEGER DEFAULT nextval('organization_add_address_id_seq') NOT NULL PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  address_type INT references lookup_orgaddress_types(code),
  addrline1 VARCHAR(80),
  addrline2 VARCHAR(80),
  addrline3 VARCHAR(80),
  city VARCHAR(80),
  state VARCHAR(80),
  country VARCHAR(80),
  postalcode VARCHAR(12),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  primary_address BOOLEAN NOT NULL DEFAULT false
)
;

CREATE SEQUENCE organization__emailaddress__seq;
CREATE TABLE organization_emailaddress (
  emailaddress_id INTEGER DEFAULT nextval('organization__emailaddress__seq') NOT NULL PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  emailaddress_type INT references lookup_orgemail_types(code),
  email VARCHAR(256),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  primary_email BOOLEAN NOT NULL DEFAULT false
)
;

CREATE SEQUENCE organization_phone_phone_id_seq;
CREATE TABLE organization_phone (
  phone_id INTEGER DEFAULT nextval('organization_phone_phone_id_seq') NOT NULL PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  phone_type INT references lookup_orgphone_types(code),
  number VARCHAR(30),
  extension VARCHAR(10),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  primary_number BOOLEAN NOT NULL DEFAULT false
)
;

CREATE TABLE contact_address (
  address_id SERIAL PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  address_type INT references lookup_contactaddress_types(code),
  addrline1 VARCHAR(80),
  addrline2 VARCHAR(80),
  addrline3 VARCHAR(80),
  city VARCHAR(80),
  state VARCHAR(80),
  country VARCHAR(80),
  postalcode VARCHAR(12),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  primary_address BOOLEAN NOT NULL DEFAULT false
)
;

CREATE INDEX "contact_address_contact_id_idx" ON "contact_address" (contact_id);

CREATE SEQUENCE contact_email_emailaddress__seq;
CREATE TABLE contact_emailaddress (
  emailaddress_id INTEGER DEFAULT nextval('contact_email_emailaddress__seq') NOT NULL PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  emailaddress_type INT references lookup_contactemail_types(code),
  email VARCHAR(256),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  primary_email BOOLEAN NOT NULL DEFAULT false
)
;

CREATE INDEX "contact_email_contact_id_idx" ON "contact_emailaddress" (contact_id);

CREATE TABLE contact_phone (
  phone_id SERIAL PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  phone_type INT references lookup_contactphone_types(code),
  number VARCHAR(30),
  extension VARCHAR(10),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  primary_number BOOLEAN NOT NULL DEFAULT false
)
;

CREATE INDEX "contact_phone_contact_id_idx" ON "contact_phone" (contact_id);

CREATE TABLE contact_imaddress (
  address_id SERIAL PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  imaddress_type INT references lookup_im_types(code),
  imaddress_service INT references lookup_im_services(code),
  imaddress VARCHAR(256),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  primary_im BOOLEAN NOT NULL DEFAULT false
);


CREATE TABLE contact_textmessageaddress (
  address_id SERIAL PRIMARY KEY,
  contact_id INT REFERENCES contact(contact_id),
  textmessageaddress_type INT references lookup_im_types(code),
  textmessageaddress VARCHAR(256),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  primary_textmessage_address BOOLEAN NOT NULL DEFAULT false
);


CREATE SEQUENCE notification_notification_i_seq;
CREATE TABLE notification (
  notification_id INTEGER DEFAULT nextval('notification_notification_i_seq') NOT NULL PRIMARY KEY,
  notify_user INT NOT NULL,
  module VARCHAR(255) NOT NULL,
  item_id INT NOT NULL,
  item_modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  attempt TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  notify_type VARCHAR(30),
  subject TEXT,
  message TEXT,
  result INT NOT NULL,
  errorMessage TEXT
);

CREATE TABLE cfsinbox_message (
  id SERIAL PRIMARY KEY,
  subject VARCHAR(255) DEFAULT NULL,
  body TEXT NOT NULL,
  reply_id INT NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  sent TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  type int not null default -1,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  delete_flag BOOLEAN default false
);

CREATE TABLE cfsinbox_messagelink (
  id INT NOT NULL REFERENCES cfsinbox_message(id),
  sent_to INT NOT NULL REFERENCES contact(contact_id),
  status INT NOT NULL DEFAULT 0,
  viewed TIMESTAMP(3) DEFAULT NULL,
  enabled BOOLEAN NOT NULL DEFAULT true,
  sent_from INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE account_type_levels (
  org_id INT NOT NULL REFERENCES organization(org_id),
  type_id INT NOT NULL REFERENCES lookup_account_types(code),
  level INTEGER not null,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contact_type_levels (
  contact_id INT NOT NULL REFERENCES contact(contact_id),
  type_id INT NOT NULL REFERENCES lookup_contact_types(code),
  level INTEGER not null,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE lookup_lists_lookup (
  id SERIAL PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  lookup_id INT NOT NULL,
  class_name VARCHAR(20),
  table_name VARCHAR(60),
  level INTEGER DEFAULT 0,
  description TEXT,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  category_id INT NOT NULL
);

CREATE TABLE webdav (
  id SERIAL PRIMARY KEY,
  category_id INT NOT NULL REFERENCES permission_category(category_id),
  class_name VARCHAR(300) NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE category_editor_lookup (
  id SERIAL PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  constant_id INT NOT NULL,
  table_name VARCHAR(60),
  level INTEGER DEFAULT 0,
  description TEXT,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  category_id INT NOT NULL,
  max_levels INT NOT NULL
);

/* Viewpoints */
CREATE TABLE viewpoint(
  viewpoint_id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES access(user_id),
  vp_user_id INT NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  enabled BOOLEAN DEFAULT true
);

CREATE SEQUENCE viewpoint_per_vp_permission_seq;
CREATE TABLE viewpoint_permission (
  vp_permission_id INTEGER DEFAULT nextval('viewpoint_per_vp_permission_seq') NOT NULL PRIMARY KEY,
  viewpoint_id INT NOT NULL REFERENCES viewpoint(viewpoint_id),
  permission_id INT NOT NULL REFERENCES permission(permission_id),
  viewpoint_view BOOLEAN NOT NULL DEFAULT false,
  viewpoint_add BOOLEAN NOT NULL DEFAULT false,
  viewpoint_edit BOOLEAN NOT NULL DEFAULT false,
  viewpoint_delete BOOLEAN NOT NULL DEFAULT false
);

/* Reports */
CREATE TABLE report (
  report_id SERIAL PRIMARY KEY,
  category_id INT NOT NULL REFERENCES permission_category(category_id),
  permission_id INT NULL REFERENCES permission(permission_id),
  filename VARCHAR(300) NOT NULL,
  type INTEGER NOT NULL DEFAULT 1,
  title VARCHAR(300) NOT NULL,
  description VARCHAR(1024) NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  enabled BOOLEAN DEFAULT true,
  custom BOOLEAN DEFAULT false
);

CREATE TABLE report_criteria (
  criteria_id SERIAL PRIMARY KEY,
  report_id INT NOT NULL REFERENCES report(report_id),
  owner INT NOT NULL REFERENCES access(user_id),
  subject VARCHAR(512) NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE report_criteria_parameter (
  parameter_id SERIAL PRIMARY KEY,
  criteria_id INTEGER NOT NULL REFERENCES report_criteria(criteria_id),
  parameter VARCHAR(255) NOT NULL,
  value TEXT
);

CREATE TABLE report_queue (
  queue_id SERIAL PRIMARY KEY,
  report_id INTEGER NOT NULL REFERENCES report(report_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  processed TIMESTAMP(3) NULL DEFAULT NULL,
  status INT NOT NULL DEFAULT 0,
  filename VARCHAR(256),
  filesize INT DEFAULT -1,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE report_queue_criteria (
  criteria_id SERIAL PRIMARY KEY,
  queue_id INTEGER NOT NULL REFERENCES report_queue(queue_id),
  parameter VARCHAR(255) NOT NULL,
  value TEXT
);

/* Action Lists */
CREATE SEQUENCE action_list_code_seq;
CREATE TABLE action_list (
  action_id INTEGER DEFAULT nextval('action_list_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  owner INT NOT NULL references access(user_id),
  completedate TIMESTAMP(3),
  link_module_id INT NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BOOLEAN NOT NULL DEFAULT true
);

CREATE SEQUENCE action_item_code_seq;
CREATE TABLE action_item (
  item_id INTEGER DEFAULT nextval('action_item_code_seq') NOT NULL PRIMARY KEY,
  action_id INT NOT NULL references action_list(action_id),
  link_item_id INT NOT NULL,
  completedate TIMESTAMP(3),
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BOOLEAN NOT NULL DEFAULT true
);


CREATE SEQUENCE action_item_log_code_seq;
CREATE TABLE action_item_log (
  log_id INTEGER DEFAULT nextval('action_item_log_code_seq') NOT NULL PRIMARY KEY,
  item_id INT NOT NULL references action_item(item_id),
  link_item_id INT DEFAULT -1,
  type INT NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE import(
  import_id SERIAL PRIMARY KEY,
  type INT NOT NULL,
  name VARCHAR(250) NOT NULL,
  description TEXT,
  source_type INT,
  source VARCHAR(1000),
  record_delimiter VARCHAR(10),
  column_delimiter VARCHAR(10),
  total_imported_records INT,
  total_failed_records INT,
  status_id INT,
  file_type INT,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE INDEX "import_entered_idx" ON "import" (entered);
CREATE INDEX "import_name_idx" ON "import" (name);

CREATE TABLE database_version (
  version_id SERIAL PRIMARY KEY,
  script_filename VARCHAR(255) NOT NULL,
  script_version VARCHAR(255) NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- relationships
CREATE TABLE lookup_relationship_types (
  type_id SERIAL PRIMARY KEY,
  category_id_maps_from INT NOT NULL,
  category_id_maps_to INT NOT NULL,
  reciprocal_name_1 VARCHAR(512),
  reciprocal_name_2 VARCHAR(512),
  level INTEGER DEFAULT 0,
  default_item BOOLEAN DEFAULT false,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE relationship (
  relationship_id SERIAL PRIMARY KEY,
  type_id INT REFERENCES lookup_relationship_types(type_id),
  object_id_maps_from INT NOT NULL,
  category_id_maps_from INT NOT NULL,
  object_id_maps_to INT NOT NULL,
  category_id_maps_to INT NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  trashed_date TIMESTAMP(3)
);
