/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 27, 2002
 *@version    $Id$
 */
 
CREATE TABLE opportunity (
  opp_id INT IDENTITY PRIMARY KEY,
  owner INT not null REFERENCES access(user_id),
  description VARCHAR(80),
  acctlink INT not null default -1,
  contactlink INT not null default -1,
  closedate DATETIME not null,
  closeprob float,
  terms float,
  units char(1),
  lowvalue float,
  guessvalue float,
  highvalue float,
  stage INT references lookup_stage(code),
  stagedate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  commission float,
  type char(1),
  alertdate DATETIME,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  custom1 int default -1,
  custom2 int default -1,
  closed DATETIME,
  custom_data TEXT,
  alert varchar(100) default null,
  enabled BIT NOT NULL DEFAULT 1,
  notes TEXT
);
 
CREATE TABLE lookup_call_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)
;

CREATE TABLE call_log (
  call_id INT IDENTITY PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  contact_id INT REFERENCES contact(contact_id),
  opp_id INT REFERENCES opportunity(opp_id),
  call_type_id INT REFERENCES lookup_call_types(code),
  length INTEGER,
  subject VARCHAR(255),
  notes TEXT,
  followup_date DATETIME,
  alertdate DATETIME,
  followup_notes TEXT,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  alert varchar(100) default null
);

CREATE INDEX "call_log_cidx" ON "call_log" ("alertdate", "enteredby");
