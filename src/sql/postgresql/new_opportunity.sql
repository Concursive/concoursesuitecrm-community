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
	stagedate date,
	commission float,
	type char(1),
	alertdate date,
	entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	enteredby INT NOT NULL REFERENCES access(user_id) ,
	modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modifiedby INT NOT NULL REFERENCES access(user_id),
	custom1 int default -1,
	custom2 int default -1,
	closed TIMESTAMP,
	custom_data TEXT,
	alert varchar(100) default null,
  enabled BOOLEAN NOT NULL DEFAULT true
);

