/**
 *  PostgreSQL Table Creation
 *
 *@author     mrajkowski
 *@created    April 18, 2002
 *@version    $Id$
 */
 
CREATE TABLE autoguide_make (
  make_id SERIAL PRIMARY KEY,
  make_name VARCHAR(30),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE autoguide_model (
  model_id SERIAL PRIMARY KEY,
  make_id INTEGER NOT NULL,
  model_name VARCHAR(50),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE autoguide_vehicle (
  year VARCHAR(4) NOT NULL,
  make_id INTEGER NOT NULL,
  model_id INTEGER NOT NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);
