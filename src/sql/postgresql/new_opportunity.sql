/**
 *  PostgreSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 27, 2002
 *@version    $Id$
 */
 
CREATE TABLE opportunity (
  opp_id serial PRIMARY KEY
  ,owner INT not null
  ,description VARCHAR(80)
  ,acctlink INT not null default -1
  ,contactlink INT not null default -1
  ,closedate date not null
  ,closeprob float
  ,terms float
  ,units char(1)
  ,lowvalue float
  ,guessvalue float
  ,highvalue float
  ,stage INT
  ,stagedate date
  ,commission float
  ,type char(1)
  ,alertdate date
  ,entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,enteredby INT NOT NULL
  ,modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,modifiedby INT NOT NULL
  ,custom1 int default -1
  ,custom2 int default -1
  ,closed TIMESTAMP,
  ,custom_data TEXT
  ,alert varchar(100) default null
  enabled BOOLEAN DEFAULT true
);
