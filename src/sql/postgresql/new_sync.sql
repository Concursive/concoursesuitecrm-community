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
  modifiedby INT NOT NULL,
  anchor TIMESTAMP DEFAULT NULL
);

CREATE TABLE sync_system (
  system_id SERIAL PRIMARY KEY,
  application_name VARCHAR(255),
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE sync_table (
  table_id SERIAL PRIMARY KEY,
  system_id INT NOT NULL,
  element_name VARCHAR(255),
  mapped_class_name VARCHAR(255),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  create_statement TEXT,
  order_id INT DEFAULT -1,
  sync_item BOOLEAN DEFAULT false
);

CREATE TABLE sync_map (
  client_id INT NOT NULL,
  table_id INT NOT NULL,
  record_id INT NOT NULL,
  cuid INT NOT NULL,
  complete BOOLEAN DEFAULT false,
  status_date TIMESTAMP
);

CREATE UNIQUE INDEX idx_sync_map ON sync_map (client_id, table_id, record_id);

CREATE TABLE sync_conflict_log (
  client_id INT NOT NULL,
  table_id INT NOT NULL,
  record_id INT NOT NULL,
  status_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
