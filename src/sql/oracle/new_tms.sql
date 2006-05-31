CREATE SEQUENCE ticket_level_code_seq;
CREATE TABLE ticket_level (
  code INT PRIMARY KEY,
  description NVARCHAR2(300) UNIQUE NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INT DEFAULT 0,
  enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE ticket_severity_code_seq;
CREATE TABLE ticket_severity (
  code INT PRIMARY KEY
  ,description NVARCHAR2(300) NOT NULL UNIQUE
  ,style CLOB  DEFAULT ''
  ,default_item CHAR(1) DEFAULT 0
  ,"level" INTEGER DEFAULT 0
  ,enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE lookup_ticketsource_code_seq;
CREATE TABLE lookup_ticketsource (
  code INT PRIMARY KEY
  ,description NVARCHAR2(300) UNIQUE NOT NULL
  ,default_item CHAR(1) DEFAULT 0
  ,"level" INTEGER DEFAULT 0
  ,enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE lookup_ticket_status_code_seq;
CREATE TABLE lookup_ticket_status (
  code INT PRIMARY KEY
  ,description NVARCHAR2(300) UNIQUE NOT NULL
  ,default_item CHAR(1) DEFAULT 0
  ,"level" INTEGER DEFAULT 0
  ,enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE ticket_priority_code_seq;
CREATE TABLE ticket_priority (
  code INT PRIMARY KEY,
  description NVARCHAR2(300) NOT NULL UNIQUE,
  style CLOB DEFAULT '',
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE lookup_ticket_scalati_code_seq;
CREATE TABLE lookup_ticket_escalation(
  code INTEGER PRIMARY KEY
  ,description NVARCHAR2(300) NOT NULL UNIQUE
  ,default_item CHAR(1) DEFAULT 0
  ,"level" INTEGER DEFAULT 0
  ,enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE ticket_category_id_seq;
CREATE TABLE ticket_category (
  id INT  PRIMARY KEY
  ,cat_level int  DEFAULT 0  NOT NULL 
  ,parent_cat_code int DEFAULT 0 NOT NULL
  ,description NVARCHAR2(300) NOT NULL
  ,full_description CLOB DEFAULT ''
  ,default_item CHAR(1) DEFAULT 0
  ,"level" INTEGER DEFAULT 0
  ,enabled CHAR(1) DEFAULT 1
  ,site_id INTEGER REFERENCES lookup_site_id(code)
);

CREATE SEQUENCE ticket_category_draft_id_seq;
CREATE TABLE ticket_category_draft (
  id INT PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int  DEFAULT 0 NOT NULL,
  parent_cat_code int DEFAULT 0 NOT NULL,
  description NVARCHAR2(300) NOT NULL,
  full_description CLOB  DEFAULT '',
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  site_id INTEGER REFERENCES lookup_site_id(code)
);

-- Ticket Category Draft Assignment table
CREATE SEQUENCE ticket_catego_nment_map_id_seq;
CREATE TABLE ticket_category_dra_assignment (
  map_id INT PRIMARY KEY,
  category_id INTEGER REFERENCES ticket_category_draft(id) NOT NULL,
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES "access"(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);

-- Ticket Category Assignment table
-- CREATE SEQUENCE ticket_catego_nment_map_id_seq;
CREATE TABLE ticket_category_assignment (
  map_id INT PRIMARY KEY,
  category_id INTEGER REFERENCES ticket_category(id) NOT NULL,
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES "access"(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);

CREATE SEQUENCE ticket_ticketid_seq;
CREATE TABLE ticket (
  ticketid INT  PRIMARY KEY,
  org_id INT REFERENCES organization, 
  contact_id INT REFERENCES contact,
  problem CLOB NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT REFERENCES "access"(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT  REFERENCES "access"(user_id) NOT NULL ,
  closed TIMESTAMP ,
  pri_code INT REFERENCES ticket_priority(code), 
  level_code INT REFERENCES ticket_level(code),
  department_code INT REFERENCES lookup_department,
  source_code INT REFERENCES lookup_ticketsource(code),
  cat_code INT REFERENCES ticket_category(id),
  subcat_code1 INT REFERENCES ticket_category(id),
  subcat_code2 INT REFERENCES ticket_category(id),
  subcat_code3 INT REFERENCES ticket_category(id),
  assigned_to INT REFERENCES "access",
  "comment" NVARCHAR2(2000),
  solution CLOB,
  scode INT REFERENCES ticket_severity(code),
  critical TIMESTAMP,
  notified TIMESTAMP,
  custom_data CLOB,
  location NVARCHAR2(256),
  assigned_date TIMESTAMP,
  est_resolution_date TIMESTAMP,
  resolution_date TIMESTAMP,
  cause CLOB,
  link_contract_id INTEGER REFERENCES service_contract(contract_id),
  link_asset_id INTEGER REFERENCES asset(asset_id),
  product_id INTEGER REFERENCES product_catalog(product_id)
);

CREATE INDEX "ticket_cidx" ON ticket (assigned_to, closed);
CREATE INDEX "ticketlist_entered" ON ticket (entered);
--CREATE INDEX "ticket_problem_idx" ON "ticket" (problem);
--CREATE INDEX "ticket_comment_idx" ON "ticket" (comment);
--CREATE INDEX "ticket_solution_idx" ON "ticket" (solution);

CREATE TABLE project_ticket_count (
  project_id INT UNIQUE NOT NULL REFERENCES projects(project_id),
  key_count INT DEFAULT 0 NOT NULL
);

CREATE SEQUENCE ticketlog_id_seq;
CREATE TABLE ticketlog (
  id INT  PRIMARY KEY
  ,ticketid INT REFERENCES ticket(ticketid)
  ,assigned_to INT REFERENCES "access"(user_id)
  ,"comment" CLOB
  ,closed CHAR(1)
  ,pri_code INT REFERENCES ticket_priority(code)
  ,level_code INT
  ,department_code INT REFERENCES lookup_department(code)
  ,cat_code INT
  ,scode INT REFERENCES ticket_severity(code)
  ,entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL
  ,enteredby INT NOT NULL REFERENCES "access"(user_id)
  ,modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL
  ,modifiedby INT NOT NULL REFERENCES "access"(user_id)
 );

CREATE SEQUENCE ticket_csstm_form_form_id_seq;
CREATE TABLE ticket_csstm_form(
  form_id INT PRIMARY KEY,
  link_ticket_id INT REFERENCES ticket(ticketid), 
  phone_response_time NVARCHAR2(10),
  engineer_response_time NVARCHAR2(10),
  follow_up_required CHAR(1) DEFAULT 0,
  follow_up_description NVARCHAR2(2000),
  alert_date TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT NOT NULL REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 1,
  travel_towards_sc CHAR(1) DEFAULT 1,
  labor_towards_sc CHAR(1) DEFAULT 1,
  alert_date_timezone NVARCHAR2(255)
);

CREATE SEQUENCE ticket_activi_item_item_id_seq;
CREATE TABLE ticket_activity_item(
  item_id INT PRIMARY KEY,
  link_form_id INT REFERENCES ticket_csstm_form(form_id),
  activity_date TIMESTAMP,
  description CLOB,
  travel_hours INT,
  travel_minutes INT,
  labor_hours INT,
  labor_minutes INT,
  activity_date_timezone NVARCHAR2(255)
);

CREATE SEQUENCE ticket_sun_form_form_id_seq;
CREATE TABLE ticket_sun_form(
  form_id INT PRIMARY KEY,
  link_ticket_id INT REFERENCES ticket(ticketid), 
  description_of_service CLOB,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 1
);

CREATE SEQUENCE trouble_asset_placement_id_seq;
CREATE TABLE trouble_asset_replacement(
  replacement_id INT PRIMARY KEY,
  link_form_id INT REFERENCES ticket_sun_form(form_id),
  part_number NVARCHAR2(256),
  part_description CLOB
);

CREATE TABLE ticketlink_project (
  ticket_id INT NOT NULL REFERENCES ticket(ticketid),
  project_id INT NOT NULL REFERENCES projects(project_id)
);

CREATE INDEX ticketlink_project_idx ON ticketlink_project(ticket_id);

-- Ticket Cause lookup
CREATE SEQUENCE lookup_ticket_cause_code_seq;
CREATE TABLE lookup_ticket_cause (
  code INTEGER PRIMARY KEY,
  description NVARCHAR2(300) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
	enabled CHAR(1) DEFAULT 1
);

-- Ticket Resolution lookup
CREATE SEQUENCE lookup_ticket_esoluti_code_seq;
CREATE TABLE lookup_ticket_resolution (
  code INTEGER PRIMARY KEY,
  description NVARCHAR2(300) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
	enabled CHAR(1) DEFAULT 1
);

--Ticket Defect table
CREATE SEQUENCE ticket_defect_defect_id_seq;
CREATE TABLE ticket_defect (
  defect_id INT PRIMARY KEY,
  title NVARCHAR2(255) NOT NULL,
  description CLOB,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  end_date TIMESTAMP,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  trashed_date TIMESTAMP,
  site_id INT REFERENCES lookup_site_id(code)
);

-- Ticket State lookup
CREATE SEQUENCE lookup_ticket_state_code_seq;
CREATE TABLE lookup_ticket_state (
  code INTEGER PRIMARY KEY,
  description NVARCHAR2(300) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
	enabled CHAR(1) DEFAULT 1
);

