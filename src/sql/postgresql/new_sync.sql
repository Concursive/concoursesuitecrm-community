/**
 *  PostgreSQL Table Creation
 *
 *@author     mrajkowski
 *@created    April 10, 2002
 *@version    $Id$
 */
 
CREATE TABLE sync_client (
  client_id SERIAL PRIMARY KEY,
  type VARCHAR(100),
  version VARCHAR(50),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE sync_system (
  system_id SERIAL PRIMARY KEY,
  application_name VARCHAR(255),
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE sync_table (
  table_id SERIAL PRIMARY KEY,
  system_id INT NOT NULL,
  table_name VARCHAR(255),
  mapped_class_name VARCHAR(255),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  create_statement TEXT,
  order_id INT DEFAULT -1
);

