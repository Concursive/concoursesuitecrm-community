/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 27, 2002
 *@version    $Id$
 */
 
CREATE TABLE opportunity (
  opp_id				INT IDENTITY PRIMARY KEY
  ,owner				INT not null
  ,description			VARCHAR(80)
  ,acctlink			INT not null default -1
  ,contactlink			INT not null default -1
  ,closedate			DATETIME not null
  ,closeprob			float
  ,terms				float
  ,units				char(1)
  ,lowvalue			float
  ,guessvalue			float
  ,highvalue			float
  ,stage				INT
  ,stagedate			DATETIME
  ,commission			float
  ,type				char(1)
  ,alertdate			DATETIME
  ,entered 			DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,enteredby 			INT NOT NULL
  ,modified 			DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,modifiedby 			INT NOT NULL
  ,custom1     			int default -1
  ,custom2 			int default -1
  ,closed 			DATETIME,
  custom_data TEXT
);
