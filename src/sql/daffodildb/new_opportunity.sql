
CREATE SEQUENCE lookup_call_types_code_seq;
CREATE TABLE lookup_call_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_call_priority_code_seq;
CREATE TABLE lookup_call_priority (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  weight INTEGER NOT NULL
);

CREATE SEQUENCE lookup_call_reminder_code_seq;
CREATE TABLE lookup_call_reminder (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  base_value INTEGER DEFAULT 0 NOT NULL,
  default_item boolean DEFAULT false,
  "level" INT DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_call_result_result_id_seq;
CREATE TABLE lookup_call_result (
  result_id INT PRIMARY KEY,
  description VARCHAR(100) NOT NULL,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  next_required boolean DEFAULT false NOT NULL ,
  next_days INT DEFAULT 0 NOT NULL ,
  next_call_type_id INTEGER,
  canceled_type boolean  DEFAULT false NOT NULL
);

CREATE SEQUENCE lookup_opportunity_typ_code_seq;
CREATE TABLE lookup_opportunity_types (
  code INT PRIMARY KEY,
  order_id INT,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

--Environment - What stuff is the account already using
CREATE SEQUENCE lookup_opportunity_env_code_seq;
CREATE TABLE lookup_opportunity_environment (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INT DEFAULT 0,
  enabled boolean DEFAULT true
);

--Competitors - Who else is competing for this business
CREATE SEQUENCE lookup_opportunity_com_code_seq;
CREATE TABLE lookup_opportunity_competitors (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INT DEFAULT 0,
  enabled boolean DEFAULT true
);

--Compelling Event - What event is driving the timeline for purchase
CREATE SEQUENCE lookup_opportunity_eve_code_seq;
CREATE TABLE lookup_opportunity_event_compelling (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INT DEFAULT 0,
  enabled boolean DEFAULT true
);

--Budget - Where are they getting the money to pay for the purchasse
CREATE SEQUENCE lookup_opportunity_bud_code_seq;
CREATE TABLE lookup_opportunity_budget (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INT DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE opportunity_header_opp_id_seq;
CREATE TABLE opportunity_header (
  opp_id INT  PRIMARY KEY,
  description VARCHAR(80),
  acctlink INT REFERENCES organization(org_id),
  contactlink INT REFERENCES contact(contact_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT REFERENCES access(user_id) NOT NULL,
  trashed_date TIMESTAMP
);

CREATE SEQUENCE opportunity_component_id_seq;
CREATE TABLE opportunity_component (
  id INT  PRIMARY KEY,
  opp_id INT REFERENCES opportunity_header(opp_id),
  owner INT REFERENCES access(user_id) NOT NULL,
  description VARCHAR(80),
  closedate TIMESTAMP NOT NULL,
  closeprob FLOAT,
  terms FLOAT,
  units CHAR(1),
  lowvalue FLOAT,
  guessvalue FLOAT,
  highvalue FLOAT,
  stage INT REFERENCES lookup_stage(code),
  stagedate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  commission FLOAT,
  type CHAR(1),
  alertdate TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT REFERENCES access(user_id) NOT NULL ,  
  closed TIMESTAMP,
  alert VARCHAR(100) DEFAULT NULL,
  enabled boolean DEFAULT true NOT NULL ,
  notes CLOB,
  alertdate_timezone VARCHAR(255),
  closedate_timezone VARCHAR(255),
  trashed_date TIMESTAMP,
  environment INT REFERENCES lookup_opportunity_environment(code),
  competitors INT REFERENCES lookup_opportunity_competitors(code),
  compelling_event INT REFERENCES lookup_opportunity_event_compelling(code),
  budget INT REFERENCES lookup_opportunity_budget(code)
);

CREATE INDEX "oppcomplist_closedate" ON "opportunity_component" (closedate);
CREATE INDEX "oppcomplist_description" ON "opportunity_component" (description);

CREATE TABLE opportunity_component_levels (
  opp_id INT REFERENCES opportunity_component(id) NOT NULL ,
  type_id INT REFERENCES lookup_opportunity_types(code) NOT NULL ,
  "level" INTEGER NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL 
);

CREATE SEQUENCE call_log_call_id_seq;
CREATE TABLE call_log (
  call_id INT  PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  contact_id INT REFERENCES contact(contact_id),
  opp_id INT REFERENCES opportunity_header(opp_id),
  call_type_id INT REFERENCES lookup_call_types(code),
  length INTEGER,
  subject VARCHAR(255),
  notes CLOB,
  followup_date TIMESTAMP,
  alertdate TIMESTAMP,
  followup_notes CLOB,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT REFERENCES access(user_id) NOT NULL ,
  alert VARCHAR(255) ,
  alert_call_type_id INT REFERENCES lookup_call_types(code),
  parent_id INT  REFERENCES call_log(call_id),
  owner INT  REFERENCES access(user_id),
  assignedby INT REFERENCES access(user_id),
  assign_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  completedby INT REFERENCES access(user_id),
  complete_date TIMESTAMP ,
  result_id INT REFERENCES lookup_call_result(result_id),
  priority_id INT REFERENCES lookup_call_priority(code),
  status_id INT DEFAULT 1 NOT NULL ,
  reminder_value INT ,
  reminder_type_id INT  REFERENCES lookup_call_reminder(code),
  alertdate_timezone VARCHAR(255),
  trashed_date TIMESTAMP
);

CREATE INDEX "call_log_cidx" ON "call_log" ("alertdate", "enteredby");
CREATE INDEX "call_log_entered_idx" ON "call_log" (entered);
CREATE INDEX "call_contact_id_idx" ON "call_log" (contact_id);
CREATE INDEX "call_org_id_idx" ON "call_log" (org_id);
CREATE INDEX "call_opp_id_idx" ON "call_log" (opp_id);

ALTER TABLE lookup_call_result ADD FOREIGN KEY(next_call_type_id) REFERENCES call_log(call_id); 