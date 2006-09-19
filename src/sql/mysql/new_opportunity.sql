-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------
 
CREATE TABLE lookup_call_types (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_call_priority (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  weight INTEGER NOT NULL
);

CREATE TABLE lookup_call_reminder (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  base_value INTEGER DEFAULT 0 NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_call_result (
  result_id INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(100) NOT NULL,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  next_required BOOLEAN NOT NULL DEFAULT false,
  next_days INT NOT NULL DEFAULT 0,
  next_call_type_id INTEGER,
  canceled_type BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE lookup_opportunity_types (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  order_id INT,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

--Environment - What stuff is the account already using
CREATE TABLE lookup_opportunity_environment (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

--Competitors - Who else is competing for this business
CREATE TABLE lookup_opportunity_competitors (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

--Compelling Event - What event is driving the timeline for purchase
CREATE TABLE lookup_opportunity_event_compelling (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

--Budget - Where are they getting the money to pay for the purchasse
CREATE TABLE lookup_opportunity_budget (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE opportunity_header (
  opp_id INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(80),
  acctlink INT REFERENCES organization(org_id),
  contactlink INT REFERENCES contact(contact_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  trashed_date TIMESTAMP NULL,
  access_type INT NOT NULL REFERENCES lookup_access_types(code),
  manager INT NOT NULL REFERENCES `access`(user_id),
  `lock` BOOLEAN DEFAULT false,
  contact_org_id INTEGER REFERENCES organization(org_id),
  custom1_integer INTEGER,
  site_id INTEGER REFERENCES lookup_site_id(code)
);

CREATE INDEX `opp_contactlink_idx` ON `opportunity_header` (contactlink);
CREATE INDEX `opp_header_contact_org_id_idx` ON `opportunity_header` (contact_org_id);
CREATE INDEX `oppheader_description_idx` ON `opportunity_header` (description);

CREATE TABLE opportunity_component (
  id INT AUTO_INCREMENT PRIMARY KEY,
  opp_id INT REFERENCES opportunity_header(opp_id),
  owner INT NOT NULL REFERENCES `access`(user_id),
  description VARCHAR(80),
  closedate TIMESTAMP NULL,
  closeprob FLOAT,
  terms FLOAT,
  units CHAR(1),
  lowvalue FLOAT,
  guessvalue FLOAT,
  highvalue FLOAT,
  stage INT REFERENCES lookup_stage(code),
  stagedate TIMESTAMP NULL,
  commission FLOAT,
  type CHAR(1),
  alertdate TIMESTAMP NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),  
  closed TIMESTAMP NULL,
  alert VARCHAR(100) DEFAULT NULL,
  enabled BOOLEAN NOT NULL DEFAULT true,
  notes TEXT,
  alertdate_timezone VARCHAR(255),
  closedate_timezone VARCHAR(255),
  trashed_date TIMESTAMP NULL,
  environment INT REFERENCES lookup_opportunity_environment(code),
  competitors INT REFERENCES lookup_opportunity_competitors(code),
  compelling_event INT REFERENCES lookup_opportunity_event_compelling(code),
  budget INT REFERENCES lookup_opportunity_budget(code),
  status_id INTEGER
);

CREATE INDEX `oppcomplist_header_idx` ON `opportunity_component` (opp_id);
CREATE INDEX `oppcomplist_closedate` ON `opportunity_component` (closedate);
CREATE INDEX `oppcomplist_description` ON `opportunity_component` (description);


CREATE TABLE opportunity_component_levels (
  opp_id INT NOT NULL REFERENCES opportunity_component(id),
  type_id INT NOT NULL REFERENCES lookup_opportunity_types(code),
  level INTEGER NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE TABLE call_log (
  call_id INT AUTO_INCREMENT PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  contact_id INT REFERENCES contact(contact_id),
  opp_id INT REFERENCES opportunity_header(opp_id),
  call_type_id INT REFERENCES lookup_call_types(code),
  length INTEGER,
  subject VARCHAR(255),
  notes TEXT,
  followup_date TIMESTAMP NULL,
  alertdate TIMESTAMP NULL,
  followup_notes TEXT,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  alert VARCHAR(255) DEFAULT NULL,
  alert_call_type_id INT REFERENCES lookup_call_types(code),
  parent_id INT NULL REFERENCES call_log(call_id),
  owner INT NULL REFERENCES `access`(user_id),
  assignedby INT REFERENCES `access`(user_id),
  assign_date TIMESTAMP NULL,
  completedby INT REFERENCES `access`(user_id),
  complete_date TIMESTAMP NULL,
  result_id INT REFERENCES lookup_call_result(result_id),
  priority_id INT REFERENCES lookup_call_priority(code),
  status_id INT NOT NULL DEFAULT 1,
  reminder_value INT NULL,
  reminder_type_id INT NULL REFERENCES lookup_call_reminder(code),
  alertdate_timezone VARCHAR(255),
  trashed_date TIMESTAMP NULL,
  followup_contact_id INT REFERENCES contact(contact_id)
);

CREATE INDEX `call_log_cidx` USING BTREE ON `call_log` (`alertdate`, `enteredby`);
CREATE INDEX `call_log_entered_idx` ON `call_log` (entered);
CREATE INDEX `call_contact_id_idx` ON `call_log` (contact_id);
CREATE INDEX `call_org_id_idx` ON `call_log` (org_id);
CREATE INDEX `call_opp_id_idx` ON `call_log` (opp_id);

ALTER TABLE lookup_call_result ADD FOREIGN KEY(next_call_type_id) REFERENCES call_log(call_id); 

CREATE TABLE opportunity_component_log(
  id INT AUTO_INCREMENT PRIMARY KEY,
  component_id INT REFERENCES opportunity_component(id),
  header_id INT REFERENCES opportunity_header(opp_id),
  description VARCHAR(80),
  closeprob FLOAT,
  closedate TIMESTAMP NULL,
  terms FLOAT,
  units CHAR(1),
  lowvalue FLOAT,
  guessvalue FLOAT,
  highvalue FLOAT,
  stage INT REFERENCES lookup_stage(code),
  owner INT NOT NULL REFERENCES `access`(user_id),    
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  closedate_timezone VARCHAR(255),
  closed TIMESTAMP NULL 
);

