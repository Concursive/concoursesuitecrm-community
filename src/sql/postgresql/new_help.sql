/**
 *  PostgreSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 19, 2002
 *@version    $Id$
 */
 
CREATE TABLE help_contents (
  help_id SERIAL PRIMARY KEY,
  module VARCHAR(255) NOT NULL,
  section VARCHAR(255),
  subsection VARCHAR(255),
  display1 VARCHAR(255),
  display2 VARCHAR(255),
  display3 VARCHAR(255),
  display4 VARCHAR(255),
  description TEXT,
  level1 INTEGER DEFAULT 0,
  level2 INTEGER DEFAULT 0,
  level3 INTEGER DEFAULT 0,
  level4 INTEGER DEFAULT 0,
  permission VARCHAR(255),
  enteredby INT NOT NULL REFERENCES access,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BOOLEAN DEFAULT true
);
