/**
 *  PostgreSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 27, 2002
 *@version    $Id$
 */
 
CREATE TABLE lookup_call_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_call_priority (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  weight INTEGER NOT NULL
);

CREATE TABLE lookup_call_reminder (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  base_value INTEGER DEFAULT 0 NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_call_result (
  result_id SERIAL PRIMARY KEY,
  description VARCHAR(100) NOT NULL,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  next_required BOOLEAN NOT NULL DEFAULT false,
  next_days INT NOT NULL DEFAULT 0,
  next_call_type_id INTEGER,
  canceled_type BOOLEAN NOT NULL DEFAULT false
);

CREATE SEQUENCE lookup_opportunity_typ_code_seq;
CREATE TABLE lookup_opportunity_types (
  code INTEGER DEFAULT nextval('lookup_opportunity_typ_code_seq') NOT NULL PRIMARY KEY,
  order_id INT,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE opportunity_header (
  opp_id SERIAL PRIMARY KEY,
  description VARCHAR(80),
  acctlink INT REFERENCES organization(org_id),
  contactlink INT REFERENCES contact(contact_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE opportunity_component (
  id SERIAL PRIMARY KEY,
  opp_id INT REFERENCES opportunity_header(opp_id),
  owner INT NOT NULL REFERENCES access(user_id),
  description VARCHAR(80),
  closedate TIMESTAMP(3) NOT NULL,
  closeprob FLOAT,
  terms FLOAT,
  units CHAR(1),
  lowvalue FLOAT,
  guessvalue FLOAT,
  highvalue FLOAT,
  stage INT REFERENCES lookup_stage(code),
  stagedate TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  commission FLOAT,
  type CHAR(1),
  alertdate TIMESTAMP(3),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),  
  closed TIMESTAMP(3),
  alert VARCHAR(100) DEFAULT NULL,
  enabled BOOLEAN NOT NULL DEFAULT true,
  notes TEXT,
  alertdate_timezone VARCHAR(255),
  closedate_timezone VARCHAR(255)
);

CREATE INDEX "oppcomplist_closedate" ON "opportunity_component" (closedate);
CREATE INDEX "oppcomplist_description" ON "opportunity_component" (description);


CREATE TABLE opportunity_component_levels (
  opp_id INT NOT NULL REFERENCES opportunity_component(id),
  type_id INT NOT NULL REFERENCES lookup_opportunity_types(code),
  level INTEGER NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE call_log (
  call_id SERIAL PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  contact_id INT REFERENCES contact(contact_id),
  opp_id INT REFERENCES opportunity_header(opp_id),
  call_type_id INT REFERENCES lookup_call_types(code),
  length INTEGER,
  subject VARCHAR(255),
  notes TEXT,
  followup_date TIMESTAMP(3),
  alertdate TIMESTAMP(3),
  followup_notes TEXT,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  alert VARCHAR(255) DEFAULT NULL,
  alert_call_type_id INT REFERENCES lookup_call_types(code),
  parent_id INT NULL REFERENCES call_log(call_id),
  owner INT NULL REFERENCES access(user_id),
  assignedby INT REFERENCES access(user_id),
  assign_date TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  completedby INT REFERENCES access(user_id),
  complete_date TIMESTAMP(3) NULL,
  result_id INT REFERENCES lookup_call_result(result_id),
  priority_id INT REFERENCES lookup_call_priority(code),
  status_id INT NOT NULL DEFAULT 1,
  reminder_value INT NULL,
  reminder_type_id INT NULL REFERENCES lookup_call_reminder(code),
  alertdate_timezone VARCHAR(255)
);

CREATE INDEX "call_log_cidx" ON "call_log" USING btree ("alertdate", "enteredby");
CREATE INDEX "call_log_entered_idx" ON "call_log" (entered);
CREATE INDEX "call_contact_id_idx" ON "call_log" (contact_id);
CREATE INDEX "call_org_id_idx" ON "call_log" (org_id);
CREATE INDEX "call_opp_id_idx" ON "call_log" (opp_id);

ALTER TABLE lookup_call_result ADD FOREIGN KEY(next_call_type_id) REFERENCES call_log(call_id); 
