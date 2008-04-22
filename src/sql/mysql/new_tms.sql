-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

-- REQUIRES: new_product.sql
-- REQUIRES: new_project.sql
 
CREATE TABLE ticket_level (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL UNIQUE,
  default_item BOOLEAN DEFAULT false,
  level INT DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER ticket_level_entries BEFORE INSERT ON  ticket_level FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE ticket_severity (
  code INT AUTO_INCREMENT PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,style text
  ,default_item BOOLEAN DEFAULT false
  ,level INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT true
  ,entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER ticket_severity_entries BEFORE INSERT ON ticket_severity FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_ticketsource (
  code INT AUTO_INCREMENT PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,default_item BOOLEAN DEFAULT false
  ,level INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT true
  ,entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_ticketsource_entries BEFORE INSERT ON lookup_ticketsource FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_ticket_status (
  code INT AUTO_INCREMENT PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,default_item BOOLEAN DEFAULT false
  ,level INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT true
  ,entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_ticket_status_entries BEFORE INSERT ON lookup_ticket_status FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE ticket_priority (
  code INT AUTO_INCREMENT PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,style text
  ,default_item BOOLEAN DEFAULT false
  ,level INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT true
  ,entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER ticket_priority_entries BEFORE INSERT ON ticket_priority FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_ticket_escalation(
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,default_item BOOLEAN DEFAULT false
  ,level INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT true
  ,entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_ticket_escalation_entries BEFORE INSERT ON lookup_ticket_escalation FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE ticket_category ( 
  id INT AUTO_INCREMENT PRIMARY KEY
  ,cat_level int  NOT NULL DEFAULT 0 
  ,parent_cat_code int NOT NULL DEFAULT 0
  ,description VARCHAR(300) NOT NULL 
  ,full_description text
  ,default_item BOOLEAN DEFAULT false
  ,level INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT true
  ,site_id INTEGER REFERENCES lookup_site_id(code)
  ,entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER ticket_category_entries BEFORE INSERT ON ticket_category FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE ticket_category_draft (
  id INT AUTO_INCREMENT PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int  NOT NULL DEFAULT 0,
  parent_cat_code int NOT NULL DEFAULT 0,
  description VARCHAR(300) NOT NULL,
  full_description text,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  site_id INTEGER REFERENCES lookup_site_id(code),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER ticket_category_draft_entries BEFORE INSERT ON ticket_category_draft FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Ticket Category Draft Assignment table
CREATE TABLE ticket_category_draft_assignment (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  category_id INTEGER NOT NULL REFERENCES ticket_category_draft(id),
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES `access`(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);

-- Ticket Category Assignment table
CREATE TABLE ticket_category_assignment (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  category_id INTEGER NOT NULL REFERENCES ticket_category(id),
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES `access`(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);

CREATE TABLE ticket (
  ticketid INT AUTO_INCREMENT PRIMARY KEY,
  org_id INT REFERENCES organization, 
  contact_id INT REFERENCES contact, 
  problem TEXT NOT NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  closed TIMESTAMP NULL,
  pri_code INT REFERENCES ticket_priority(code), 
  level_code INT REFERENCES ticket_level(code),
  department_code INT REFERENCES lookup_department,
  source_code INT REFERENCES lookup_ticketsource(code), 
  cat_code INT REFERENCES ticket_category(id),
  subcat_code1 INT REFERENCES ticket_category(id),
  subcat_code2 INT REFERENCES ticket_category(id),
  subcat_code3 INT REFERENCES ticket_category(id),
  assigned_to INT REFERENCES `access`(user_id),
  comment TEXT,
  solution TEXT,
  scode INT REFERENCES ticket_severity(code),
  critical TIMESTAMP NULL,
  notified TIMESTAMP NULL,
  custom_data TEXT,
  location VARCHAR(256),
  assigned_date TIMESTAMP NULL,
  est_resolution_date TIMESTAMP NULL,
  resolution_date TIMESTAMP NULL,
  cause TEXT,
  link_contract_id INTEGER REFERENCES service_contract(contract_id),
  link_asset_id INTEGER REFERENCES asset(asset_id),
  product_id INTEGER REFERENCES product_catalog(product_id)
  -- DO NOT PUT ANY MORE FIELDS IN THIS TABLE HERE
  -- THEY MUST BE APPENDED to new_tms_append_fields.sql because of referential
  -- integrity
);

CREATE TRIGGER ticket_entries BEFORE INSERT ON ticket FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE INDEX `ticket_cidx` USING btree ON `ticket` (`assigned_to`, `closed`);
CREATE INDEX `ticketlist_entered` ON `ticket` (entered);
CREATE INDEX `ticket_problem_idx` ON `ticket` (problem(100));
CREATE INDEX `ticket_comment_idx` ON `ticket` (comment(100));
CREATE INDEX `ticket_solution_idx` ON `ticket` (solution(100));

CREATE TABLE project_ticket_count (
  project_id INT UNIQUE NOT NULL REFERENCES projects(project_id),
  key_count INT NOT NULL DEFAULT 0
);

CREATE TABLE ticketlog (
  id INT AUTO_INCREMENT PRIMARY KEY
  ,ticketid INT REFERENCES ticket(ticketid)
  ,assigned_to INT REFERENCES `access`(user_id)
  ,comment TEXT
  ,closed BOOLEAN
  ,pri_code INT REFERENCES ticket_priority(code)
  ,level_code INT 
  ,department_code INT REFERENCES lookup_department(code)
  ,cat_code INT
  ,scode INT REFERENCES ticket_severity(code)
  ,entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,enteredby INT NOT NULL REFERENCES `access`(user_id)
  ,modified TIMESTAMP NULL
  ,modifiedby INT NOT NULL REFERENCES `access`(user_id)
  -- DO NOT PUT ANY MORE FIELDS IN THIS TABLE HERE
  -- THEY MUST BE APPENDED to new_tms_append_fields.sql because of referential
  -- integrity
);

CREATE TRIGGER ticketlog_entries BEFORE INSERT ON ticketlog FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE ticket_csstm_form(
  form_id INT AUTO_INCREMENT PRIMARY KEY,
  link_ticket_id INT REFERENCES ticket(ticketid), 
  phone_response_time VARCHAR(10),
  engineer_response_time VARCHAR(10),
  follow_up_required BOOLEAN DEFAULT false,
  follow_up_description VARCHAR(2048),
  alert_date TIMESTAMP NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  enabled BOOLEAN DEFAULT true,
  travel_towards_sc BOOLEAN DEFAULT true,
  labor_towards_sc BOOLEAN DEFAULT true,
  alert_date_timezone VARCHAR(255)
);

CREATE TRIGGER ticket_csstm_form_entries BEFORE INSERT ON ticket_csstm_form FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE ticket_activity_item(
  item_id INT AUTO_INCREMENT PRIMARY KEY,
  link_form_id INT REFERENCES ticket_csstm_form(form_id),
  activity_date TIMESTAMP NULL,
  description TEXT,
  travel_hours INT,
  travel_minutes INT,
  labor_hours INT,
  labor_minutes INT,
  activity_date_timezone VARCHAR(255)
);

CREATE TABLE ticket_sun_form(
  form_id INT AUTO_INCREMENT PRIMARY KEY,
  link_ticket_id INT REFERENCES ticket(ticketid), 
  description_of_service TEXT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  enabled boolean DEFAULT true
);

CREATE TRIGGER ticket_sun_form_entries BEFORE INSERT ON ticket_sun_form FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE trouble_asset_replacement(
  replacement_id INT AUTO_INCREMENT PRIMARY KEY,
  link_form_id INT REFERENCES ticket_sun_form(form_id),
  part_number VARCHAR(256),
  part_description TEXT
);

CREATE TABLE ticketlink_project (
  ticket_id INT NOT NULL REFERENCES ticket(ticketid),
  project_id INT NOT NULL REFERENCES projects(project_id)
);

CREATE INDEX ticketlink_project_idx ON ticketlink_project(ticket_id);

-- Ticket Cause lookup
CREATE TABLE lookup_ticket_cause (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_ticket_cause_entries BEFORE INSERT ON lookup_ticket_cause FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Ticket Resolution lookup
CREATE TABLE lookup_ticket_resolution (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_ticket_resolution_entries BEFORE INSERT ON lookup_ticket_resolution FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

--Ticket Defect table
CREATE TABLE ticket_defect (
  defect_id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  start_date TIMESTAMP NULL,
  end_date TIMESTAMP NULL,
  enabled BOOLEAN NOT NULL DEFAULT true,
  trashed_date TIMESTAMP NULL,
  site_id INT REFERENCES lookup_site_id(code)  
);

-- Ticket State lookup
CREATE TABLE lookup_ticket_state (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_ticket_state_entries BEFORE INSERT ON lookup_ticket_state FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);
