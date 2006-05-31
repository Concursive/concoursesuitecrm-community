
CREATE GENERATOR lookup_call_types_code_seq;
CREATE TABLE lookup_call_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_call_priority_code_seq;
CREATE TABLE lookup_call_priority (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  weight INTEGER NOT NULL,
  PRIMARY KEY (CODE)
);

CREATE GENERATOR lookup_call_reminder_code_seq;
CREATE TABLE lookup_call_reminder (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  base_value INTEGER DEFAULT 0 NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

-- Old Name: lookup_call_result_result_id_seq;
CREATE GENERATOR lookup_call_r_ult_result_id_seq;
CREATE TABLE lookup_call_result (
  result_id INTEGER NOT NULL,
  description VARCHAR(100) NOT NULL,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  next_required CHAR(1) DEFAULT 'N' NOT NULL ,
  next_days INTEGER DEFAULT 0 NOT NULL ,
  next_call_type_id INTEGER,
  canceled_type CHAR(1) DEFAULT 'N' NOT NULL,
  PRIMARY KEY (RESULT_ID)
);

CREATE GENERATOR lookup_opportunity_typ_code_seq;
CREATE TABLE lookup_opportunity_types (
  code INTEGER NOT NULL,
  order_id INTEGER,
  description VARCHAR(50) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

--Environment - What stuff is the account already using
CREATE GENERATOR lookup_opportunity_env_code_seq;
CREATE TABLE lookup_opportunity_environment (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

--Competitors - Who else is competing for this business
CREATE GENERATOR lookup_opportunity_com_code_seq;
CREATE TABLE lookup_opportunity_competitors (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

--Compelling Event - What event is driving the timeline for purchase
CREATE GENERATOR lookup_opportunity_eve_code_seq;

-- Old Name: lookup_opportunity_event_compelling
CREATE TABLE lookup_opt_event_compelling (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

--Budget - Where are they getting the money to pay for the purchasse
CREATE GENERATOR lookup_opportunity_bud_code_seq;
CREATE TABLE lookup_opportunity_budget (
  code INTEGER NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

CREATE GENERATOR opportunity_header_opp_id_seq;
CREATE TABLE opportunity_header (
  opp_id INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(80),
  acctlink INTEGER REFERENCES organization(org_id),
  contactlink INTEGER REFERENCES contact(contact_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  trashed_date TIMESTAMP,
  access_type INT NOT NULL REFERENCES lookup_access_types(code),
  manager INT NOT NULL REFERENCES "access"(user_id),
  "lock" CHAR(1) DEFAULT 'N',
  contact_org_id INTEGER REFERENCES organization(org_id),
  custom1_integer INTEGER,
  site_id INT REFERENCES lookup_site_id(code)
);

CREATE GENERATOR opportunity_component_id_seq;
CREATE TABLE opportunity_component (
  id INTEGER NOT NULL PRIMARY KEY,
  opp_id INTEGER REFERENCES opportunity_header(opp_id),
  owner INTEGER  NOT NULL REFERENCES "access"(user_id),
  description VARCHAR(80),
  closedate TIMESTAMP NOT NULL,
  closeprob FLOAT,
  terms FLOAT,
  units CHAR(1),
  lowvalue FLOAT,
  guessvalue FLOAT,
  highvalue FLOAT,
  stage INTEGER REFERENCES lookup_stage(code),
  stagedate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  commission FLOAT,
  "type" CHAR(1),
  alertdate TIMESTAMP,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  closed TIMESTAMP,
  alert VARCHAR(100) DEFAULT NULL,
  enabled CHAR(1) DEFAULT 'N' NOT NULL ,
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  alertdate_timezone VARCHAR(255),
  closedate_timezone VARCHAR(255),
  trashed_date TIMESTAMP,
  environment INTEGER REFERENCES lookup_opportunity_environment(code),
  competitors INTEGER REFERENCES lookup_opportunity_competitors(code),
  compelling_event INTEGER REFERENCES lookup_opt_event_compelling(code),
  budget INTEGER REFERENCES lookup_opportunity_budget(code),
  status_id INTEGER
);

CREATE INDEX oppcomplist_closedate ON opportunity_component (closedate);
CREATE INDEX oppcomplist_description ON opportunity_component (description);

CREATE TABLE opportunity_component_levels (
  opp_id INTEGER NOT NULL REFERENCES opportunity_component(id),
  type_id INTEGER NOT NULL REFERENCES lookup_opportunity_types(code),
  "level" INTEGER NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE GENERATOR call_log_call_id_seq;
CREATE TABLE call_log (
  call_id INTEGER  NOT NULL,
  org_id INTEGER REFERENCES organization(org_id),
  contact_id INTEGER REFERENCES contact(contact_id),
  opp_id INTEGER REFERENCES opportunity_header(opp_id),
  call_type_id INTEGER REFERENCES lookup_call_types(code),
  "length" INTEGER,
  subject VARCHAR(255),
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  followup_date TIMESTAMP,
  alertdate TIMESTAMP,
  followup_notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  alert VARCHAR(255) ,
  alert_call_type_id INTEGER REFERENCES lookup_call_types(code),
  parent_id INTEGER,
  owner INTEGER  REFERENCES "access"(user_id),
  assignedby INTEGER REFERENCES "access"(user_id),
  assign_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  completedby INTEGER REFERENCES "access"(user_id),
  complete_date TIMESTAMP ,
  result_id INTEGER REFERENCES lookup_call_result(result_id),
  priority_id INTEGER REFERENCES lookup_call_priority(code),
  status_id INTEGER DEFAULT 1 NOT NULL ,
  reminder_value INTEGER ,
  reminder_type_id INTEGER  REFERENCES lookup_call_reminder(code),
  alertdate_timezone VARCHAR(255),
  trashed_date TIMESTAMP,
  PRIMARY KEY (CALL_ID)
);

-- REQUIRED HERE - Firebird can not create a FK on itself during table create
ALTER TABLE CALL_LOG ADD CONSTRAINT FK_CALL_LOG_CALL_LOG
  FOREIGN KEY (PARENT_ID) REFERENCES CALL_LOG
  (CALL_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE INDEX call_log_cidx ON call_log ( alertdate, enteredby );
CREATE INDEX call_log_entered_idx ON call_log ( entered );
CREATE INDEX call_contact_id_idx ON call_log ( contact_id );
CREATE INDEX call_org_id_idx ON call_log ( org_id );
CREATE INDEX call_opp_id_idx ON call_log ( opp_id );

-- Used at end of script because it references a table that has not been created
-- =============================================================================
ALTER TABLE lookup_call_result ADD CONSTRAINT FK_LOOKUP_CALL_RESULT_CALL_LOG
  FOREIGN KEY(next_call_type_id) REFERENCES call_log(call_id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE GENERATOR opportunity_c_ponent_log_id_seq;
CREATE TABLE opportunity_component_log (
 id INTEGER NOT NULL PRIMARY KEY,
 component_id INTEGER REFERENCES opportunity_component(id),
 header_id INTEGER REFERENCES opportunity_header(opp_id),
 description VARCHAR(80),
 closeprob FLOAT,
 closedate TIMESTAMP NOT NULL,
 terms FLOAT,
 units CHAR(1),
 lowvalue FLOAT,
 guessvalue FLOAT,
 highvalue FLOAT,
 stage INTEGER REFERENCES lookup_stage(code),
 owner INTEGER NOT NULL REFERENCES "access"(user_id),
 entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
 enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
 closedate_timezone VARCHAR(255),
 closed TIMESTAMP
);

