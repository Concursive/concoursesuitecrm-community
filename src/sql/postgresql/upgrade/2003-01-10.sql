/* December 6, 2002 */

DROP TABLE mod_log;

CREATE TABLE usage_log (
  usage_id SERIAL PRIMARY KEY,
  entered TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NULL,
  action INT NOT NULL,
  record_id INT NULL,
  record_size INT NULL
);

alter table organization add column namesalutation varchar(80);
alter table organization add column namelast varchar(80);
alter table organization add column namefirst varchar(80);
alter table organization add column namemiddle varchar(80);
alter table organization add column namesuffix varchar(80);


/* some new opportunity component stuff */

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

CREATE TABLE opportunity_component_levels (
  opp_id INT NOT NULL REFERENCES opportunity_component(id),
  type_id INT NOT NULL REFERENCES lookup_opportunity_types(code),
  level INTEGER not null,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);
  

