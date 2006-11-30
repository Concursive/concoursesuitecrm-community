CREATE GENERATOR ticket_level_code_seq;
-- REMOVED UNIQUE SETTING ON "description" Index field too large for Firebird
CREATE TABLE ticket_level (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR ticket_severity_code_seq;
-- REMOVED UNIQUE SETTING ON "description" Index field too large for Firebird
CREATE TABLE ticket_severity (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  style BLOB SUB_TYPE 1 SEGMENT SIZE 100  DEFAULT ''  NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_ticketsource_code_seq;
-- REMOVED UNIQUE SETTING ON "description" Index field too large for Firebird
CREATE TABLE lookup_ticketsource (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_ticket_status_code_seq;
-- REMOVED UNIQUE SETTING ON "description" Index field too large for Firebird
CREATE TABLE lookup_ticket_status (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0 ,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR ticket_priority_code_seq;
-- REMOVED UNIQUE SETTING ON "description" Index field too large for Firebird
CREATE TABLE ticket_priority (
  code INTEGER NOT NULL,
  description VARCHAR(300)  NOT NULL,
  style BLOB SUB_TYPE 1 SEGMENT SIZE 100 DEFAULT ''  NOT NULL ,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_ticket_escalati_code_seq;
CREATE TABLE lookup_ticket_escalation(
  code INTEGER NOT NULL PRIMARY KEY
  ,description VARCHAR(300) NOT NULL
  ,default_item BOOLEAN DEFAULT FALSE
  ,"level" INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR ticket_category_id_seq;
CREATE TABLE ticket_category (
  id INTEGER  NOT NULL,
  cat_level INTEGER  DEFAULT 0  NOT NULL,
  parent_cat_code INTEGER DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description BLOB SUB_TYPE 1 SEGMENT SIZE 100 DEFAULT '' NOT NULL ,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  site_id INTEGER REFERENCES lookup_site_id(code),
  PRIMARY KEY (ID)
);

CREATE GENERATOR ticket_category_draft_id_seq;
CREATE TABLE ticket_category_draft (
  id INTEGER NOT NULL,
  link_id INTEGER DEFAULT -1,
  cat_level INTEGER  DEFAULT 0 NOT NULL,
  parent_cat_code INTEGER DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description BLOB SUB_TYPE 1 SEGMENT SIZE 100  DEFAULT '' NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  site_id INTEGER REFERENCES lookup_site_id(code),
  PRIMARY KEY (ID)
);

CREATE GENERATOR ticket_catego_gnment_map_id_seq;
CREATE TABLE ticket_category_draf_assignment (
  map_id INT NOT NULL PRIMARY KEY,
  category_id INTEGER NOT NULL REFERENCES ticket_category_draft(id),
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES "access"(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);

-- Ticket Category Assignment table
-- reuse CREATE GENERATOR ticket_catego_gnment_map_id_seq;
CREATE TABLE ticket_category_assignment (
  map_id INT NOT NULL PRIMARY KEY,
  category_id INTEGER NOT NULL REFERENCES ticket_category(id),
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES "access"(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);

CREATE GENERATOR ticket_ticketid_seq;
CREATE TABLE ticket (
  ticketid INTEGER  NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  contact_id INTEGER REFERENCES contact(contact_id),
  problem VARCHAR(4192) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  closed TIMESTAMP ,
  pri_code INTEGER REFERENCES ticket_priority(code),
  level_code INTEGER REFERENCES ticket_level(code),
  department_code INTEGER REFERENCES lookup_department(code),
  source_code INTEGER REFERENCES lookup_ticketsource(code),
  cat_code INTEGER REFERENCES ticket_category(id),
  subcat_code1 INTEGER REFERENCES ticket_category(id),
  subcat_code2 INTEGER REFERENCES ticket_category(id),
  subcat_code3 INTEGER REFERENCES ticket_category(id),
  assigned_to INTEGER REFERENCES "access"(user_id),
  "comment" VARCHAR(4192),
  solution VARCHAR(4192),
  scode INTEGER REFERENCES ticket_severity(code),
  critical TIMESTAMP,
  notified TIMESTAMP,
  custom_data BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  location VARCHAR(256),
  assigned_date TIMESTAMP,
  est_resolution_date TIMESTAMP,
  resolution_date TIMESTAMP,
  cause BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  link_contract_id INTEGER REFERENCES service_contract(contract_id),
  link_asset_id INTEGER REFERENCES asset(asset_id),
  product_id INTEGER REFERENCES product_catalog(product_id),
  PRIMARY KEY (TICKETID)
);

CREATE INDEX ticket_cidx ON ticket (assigned_to, closed);
CREATE INDEX ticketlist_entered ON ticket (entered);
--CREATE INDEX "ticket_problem_idx" ON "ticket" (problem);
--CREATE INDEX "ticket_comment_idx" ON "ticket" (comment);
--CREATE INDEX "ticket_solution_idx" ON "ticket" (solution);

CREATE TABLE project_ticket_count (
  project_id INTEGER NOT NULL UNIQUE REFERENCES projects(project_id),
  key_count INTEGER  DEFAULT 0 NOT NULL
);

CREATE GENERATOR ticketlog_id_seq;
CREATE TABLE ticketlog (
  id INTEGER  NOT NULL,
  ticketid INTEGER REFERENCES ticket(ticketid),
  assigned_to INTEGER REFERENCES "access"(user_id),
  "comment" BLOB SUB_TYPE 1 SEGMENT SIZE 100 ,
  closed BOOLEAN,
  pri_code INTEGER REFERENCES ticket_priority(code),
  level_code INTEGER,
  department_code INTEGER REFERENCES lookup_department(code),
  cat_code INTEGER ,
  scode INTEGER REFERENCES ticket_severity(code),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ID)
 );

CREATE GENERATOR ticket_csstm_form_form_id_seq;
CREATE TABLE ticket_csstm_form(
  form_id INTEGER NOT NULL,
  link_ticket_id INTEGER REFERENCES ticket(ticketid),
  phone_response_time VARCHAR(10),
  engineer_response_time VARCHAR(10),
  follow_up_required BOOLEAN DEFAULT FALSE,
  follow_up_description VARCHAR(2048),
  alert_date TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  travel_towards_sc BOOLEAN DEFAULT TRUE,
  labor_towards_sc BOOLEAN DEFAULT TRUE,
  alert_date_timezone VARCHAR(255),
  PRIMARY KEY (FORM_ID)
);

-- Old Name: ticket_activity_item_item_id_seq;
CREATE GENERATOR ticket_activi__item_item_id_seq;
CREATE TABLE ticket_activity_item(
  item_id INTEGER NOT NULL,
  link_form_id INTEGER REFERENCES ticket_csstm_form(form_id),
  activity_date TIMESTAMP,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  travel_hours INTEGER,
  travel_minutes INTEGER,
  labor_hours INTEGER,
  labor_minutes INTEGER,
  activity_date_timezone VARCHAR(255),
  PRIMARY KEY (ITEM_ID)
);

CREATE GENERATOR ticket_sun_form_form_id_seq;
CREATE TABLE ticket_sun_form(
  form_id INTEGER NOT NULL,
  link_ticket_id INTEGER REFERENCES ticket(ticketid),
  description_of_service BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (FORM_ID)
);

-- Old Name: trouble_asset_replacement_replacement_id_seq;
CREATE GENERATOR trouble_asset_eplacement_id_seq;
CREATE TABLE trouble_asset_replacement(
  replacement_id INTEGER NOT NULL,
  link_form_id INTEGER REFERENCES ticket_sun_form(form_id),
  part_number VARCHAR(256),
  part_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (REPLACEMENT_ID)
);

CREATE TABLE ticketlink_project (
  ticket_id INTEGER NOT NULL REFERENCES ticket(ticketid),
  project_id INTEGER NOT NULL REFERENCES projects(project_id)
);

CREATE INDEX ticketlink_project_idx ON ticketlink_project(ticket_id);

-- Ticket Cause lookup
CREATE GENERATOR lookup_ticket_cause_code_seq;
CREATE TABLE lookup_ticket_cause (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT TRUE
);

-- Ticket Resolution lookup
CREATE GENERATOR lookup_ticket_resoluti_code_seq;
CREATE TABLE lookup_ticket_resolution (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT TRUE
);

--Ticket Defect table
CREATE GENERATOR ticket_defect_defect_id_seq;
CREATE TABLE ticket_defect (
  defect_id INT NOT NULL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  end_date TIMESTAMP,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  trashed_date TIMESTAMP,
  site_id INT REFERENCES lookup_site_id(code)
);

-- Ticket State lookup
CREATE GENERATOR lookup_ticket_state_code_seq;
CREATE TABLE lookup_ticket_state (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR ticket_level_code_seq;
-- REMOVED UNIQUE SETTING ON "description" Index field too large for Firebird
CREATE TABLE ticket_level (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR ticket_severity_code_seq;
-- REMOVED UNIQUE SETTING ON "description" Index field too large for Firebird
CREATE TABLE ticket_severity (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  style BLOB SUB_TYPE 1 SEGMENT SIZE 100  DEFAULT ''  NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_ticketsource_code_seq;
-- REMOVED UNIQUE SETTING ON "description" Index field too large for Firebird
CREATE TABLE lookup_ticketsource (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_ticket_status_code_seq;
-- REMOVED UNIQUE SETTING ON "description" Index field too large for Firebird
CREATE TABLE lookup_ticket_status (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0 ,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR ticket_priority_code_seq;
-- REMOVED UNIQUE SETTING ON "description" Index field too large for Firebird
CREATE TABLE ticket_priority (
  code INTEGER NOT NULL,
  description VARCHAR(300)  NOT NULL,
  style BLOB SUB_TYPE 1 SEGMENT SIZE 100 DEFAULT ''  NOT NULL ,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_ticket_escalati_code_seq;
CREATE TABLE lookup_ticket_escalation(
  code INTEGER NOT NULL PRIMARY KEY
  ,description VARCHAR(300) NOT NULL
  ,default_item BOOLEAN DEFAULT FALSE
  ,"level" INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT TRUE
);

CREATE GENERATOR ticket_category_id_seq;
CREATE TABLE ticket_category (
  id INTEGER  NOT NULL,
  cat_level INTEGER  DEFAULT 0  NOT NULL,
  parent_cat_code INTEGER DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description BLOB SUB_TYPE 1 SEGMENT SIZE 100 DEFAULT '' NOT NULL ,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  site_id INTEGER REFERENCES lookup_site_id(code),
  PRIMARY KEY (ID)
);

CREATE GENERATOR ticket_category_draft_id_seq;
CREATE TABLE ticket_category_draft (
  id INTEGER NOT NULL,
  link_id INTEGER DEFAULT -1,
  cat_level INTEGER  DEFAULT 0 NOT NULL,
  parent_cat_code INTEGER DEFAULT 0 NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description BLOB SUB_TYPE 1 SEGMENT SIZE 100  DEFAULT '' NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE,
  site_id INTEGER REFERENCES lookup_site_id(code),
  PRIMARY KEY (ID)
);

CREATE GENERATOR ticket_catego_gnment_map_id_seq;
CREATE TABLE ticket_category_draf_assignment (
  map_id INT NOT NULL PRIMARY KEY,
  category_id INTEGER NOT NULL REFERENCES ticket_category_draft(id),
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES "access"(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);

-- Ticket Category Assignment table
-- reuse CREATE GENERATOR ticket_catego_gnment_map_id_seq;
CREATE TABLE ticket_category_assignment (
  map_id INT NOT NULL PRIMARY KEY,
  category_id INTEGER NOT NULL REFERENCES ticket_category(id),
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES "access"(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);

CREATE GENERATOR ticket_ticketid_seq;
CREATE TABLE ticket (
  ticketid INTEGER  NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  contact_id INTEGER REFERENCES contact(contact_id),
  problem VARCHAR(4192) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  closed TIMESTAMP ,
  pri_code INTEGER REFERENCES ticket_priority(code),
  level_code INTEGER REFERENCES ticket_level(code),
  department_code INTEGER REFERENCES lookup_department(code),
  source_code INTEGER REFERENCES lookup_ticketsource(code),
  cat_code INTEGER REFERENCES ticket_category(id),
  subcat_code1 INTEGER REFERENCES ticket_category(id),
  subcat_code2 INTEGER REFERENCES ticket_category(id),
  subcat_code3 INTEGER REFERENCES ticket_category(id),
  assigned_to INTEGER REFERENCES "access"(user_id),
  "comment" VARCHAR(4192),
  solution VARCHAR(4192),
  scode INTEGER REFERENCES ticket_severity(code),
  critical TIMESTAMP,
  notified TIMESTAMP,
  custom_data BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  location VARCHAR(256),
  assigned_date TIMESTAMP,
  est_resolution_date TIMESTAMP,
  resolution_date TIMESTAMP,
  cause BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  link_contract_id INTEGER REFERENCES service_contract(contract_id),
  link_asset_id INTEGER REFERENCES asset(asset_id),
  product_id INTEGER REFERENCES product_catalog(product_id),
  PRIMARY KEY (TICKETID)
);

CREATE INDEX ticket_cidx ON ticket (assigned_to, closed);
CREATE INDEX ticketlist_entered ON ticket (entered);
--CREATE INDEX "ticket_problem_idx" ON "ticket" (problem);
--CREATE INDEX "ticket_comment_idx" ON "ticket" (comment);
--CREATE INDEX "ticket_solution_idx" ON "ticket" (solution);

CREATE TABLE project_ticket_count (
  project_id INTEGER NOT NULL UNIQUE REFERENCES projects(project_id),
  key_count INTEGER  DEFAULT 0 NOT NULL
);

CREATE GENERATOR ticketlog_id_seq;
CREATE TABLE ticketlog (
  id INTEGER  NOT NULL,
  ticketid INTEGER REFERENCES ticket(ticketid),
  assigned_to INTEGER REFERENCES "access"(user_id),
  "comment" BLOB SUB_TYPE 1 SEGMENT SIZE 100 ,
  closed BOOLEAN,
  pri_code INTEGER REFERENCES ticket_priority(code),
  level_code INTEGER,
  department_code INTEGER REFERENCES lookup_department(code),
  cat_code INTEGER ,
  scode INTEGER REFERENCES ticket_severity(code),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ID)
 );

CREATE GENERATOR ticket_csstm_form_form_id_seq;
CREATE TABLE ticket_csstm_form(
  form_id INTEGER NOT NULL,
  link_ticket_id INTEGER REFERENCES ticket(ticketid),
  phone_response_time VARCHAR(10),
  engineer_response_time VARCHAR(10),
  follow_up_required BOOLEAN DEFAULT FALSE,
  follow_up_description VARCHAR(2048),
  alert_date TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  travel_towards_sc BOOLEAN DEFAULT TRUE,
  labor_towards_sc BOOLEAN DEFAULT TRUE,
  alert_date_timezone VARCHAR(255),
  PRIMARY KEY (FORM_ID)
);

-- Old Name: ticket_activity_item_item_id_seq;
CREATE GENERATOR ticket_activi__item_item_id_seq;
CREATE TABLE ticket_activity_item(
  item_id INTEGER NOT NULL,
  link_form_id INTEGER REFERENCES ticket_csstm_form(form_id),
  activity_date TIMESTAMP,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  travel_hours INTEGER,
  travel_minutes INTEGER,
  labor_hours INTEGER,
  labor_minutes INTEGER,
  activity_date_timezone VARCHAR(255),
  PRIMARY KEY (ITEM_ID)
);

CREATE GENERATOR ticket_sun_form_form_id_seq;
CREATE TABLE ticket_sun_form(
  form_id INTEGER NOT NULL,
  link_ticket_id INTEGER REFERENCES ticket(ticketid),
  description_of_service BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  enabled BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (FORM_ID)
);

-- Old Name: trouble_asset_replacement_replacement_id_seq;
CREATE GENERATOR trouble_asset_eplacement_id_seq;
CREATE TABLE trouble_asset_replacement(
  replacement_id INTEGER NOT NULL,
  link_form_id INTEGER REFERENCES ticket_sun_form(form_id),
  part_number VARCHAR(256),
  part_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (REPLACEMENT_ID)
);

CREATE TABLE ticketlink_project (
  ticket_id INTEGER NOT NULL REFERENCES ticket(ticketid),
  project_id INTEGER NOT NULL REFERENCES projects(project_id)
);

CREATE INDEX ticketlink_project_idx ON ticketlink_project(ticket_id);

-- Ticket Cause lookup
CREATE GENERATOR lookup_ticket_cause_code_seq;
CREATE TABLE lookup_ticket_cause (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT TRUE
);

-- Ticket Resolution lookup
CREATE GENERATOR lookup_ticket_resoluti_code_seq;
CREATE TABLE lookup_ticket_resolution (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT TRUE
);

--Ticket Defect table
CREATE GENERATOR ticket_defect_defect_id_seq;
CREATE TABLE ticket_defect (
  defect_id INT NOT NULL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  end_date TIMESTAMP,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  trashed_date TIMESTAMP,
  site_id INT REFERENCES lookup_site_id(code)
);

-- Ticket State lookup
CREATE GENERATOR lookup_ticket_state_code_seq;
CREATE TABLE lookup_ticket_state (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT TRUE
);

