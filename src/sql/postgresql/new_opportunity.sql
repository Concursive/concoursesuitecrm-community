/**
 *  PostgreSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 27, 2002
 *@version    $Id$
 */
 
CREATE TABLE opportunity (
	opp_id serial PRIMARY KEY,
	owner INT NOT NULL REFERENCES access(user_id),
	description VARCHAR(80),
	acctlink INT default -1,
	contactlink INT default -1,
	closedate date not null,
	closeprob float,
	terms float,
	units char(1),
	lowvalue float,
	guessvalue float,
	highvalue float,
	stage INT references lookup_stage(code),
	stagedate date NOT NULL DEFAULT CURRENT_TIMESTAMP,
	commission float,
	type char(1),
	alertdate date,
	entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	enteredby INT NOT NULL REFERENCES access(user_id),
	modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modifiedby INT NOT NULL REFERENCES access(user_id),
	custom1 int default -1,
	custom2 int default -1,
	closed TIMESTAMP,
	custom_data TEXT,
	alert varchar(100) default null,
  enabled BOOLEAN NOT NULL DEFAULT true,
  notes TEXT
);

CREATE TABLE lookup_call_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE TABLE lookup_opportunity_types (
  code SERIAL PRIMARY KEY,
  order_id INT,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE opportunity_header (
  opp_id serial PRIMARY KEY,
  description VARCHAR(80),
	acctlink INT default -1,
	contactlink INT default -1,
	entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	enteredby INT NOT NULL REFERENCES access(user_id),
	modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE opportunity_component (
  id serial PRIMARY KEY,
  opp_id int references opportunity_header(opp_id),
	owner INT NOT NULL REFERENCES access(user_id),
	description VARCHAR(80),
	closedate date not null,
	closeprob float,
	terms float,
	units char(1),
	lowvalue float,
	guessvalue float,
	highvalue float,
	stage INT references lookup_stage(code),
	stagedate date NOT NULL DEFAULT CURRENT_TIMESTAMP,
	commission float,
	type char(1),
	alertdate date,
	entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	enteredby INT NOT NULL REFERENCES access(user_id),
	modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modifiedby INT NOT NULL REFERENCES access(user_id),  
  closed TIMESTAMP,
  alert varchar(100) default null,
  enabled BOOLEAN NOT NULL DEFAULT true,
  notes TEXT
);  

CREATE TABLE opportunity_type_levels (
  opp_id INT NOT NULL REFERENCES opportunity(opp_id),
  type_id INT NOT NULL REFERENCES lookup_opportunity_types(code),
  level INTEGER not null,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE opportunity_component_levels (
  opp_id INT NOT NULL REFERENCES opportunity_component(id),
  type_id INT NOT NULL REFERENCES lookup_opportunity_types(code),
  level INTEGER not null,
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
  followup_date DATE,
  alertdate DATE,
  followup_notes TEXT,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  alert varchar(100) default null
);

CREATE INDEX "call_log_cidx" ON "call_log" USING btree ("alertdate", "enteredby");
