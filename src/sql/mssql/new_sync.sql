/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    April 10, 2002
 *@version    $Id$
 */
 
CREATE TABLE sync_client (
  client_id INT IDENTITY PRIMARY KEY,
  type VARCHAR(100),
  version VARCHAR(50),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL,
  anchor DATETIME DEFAULT NULL
);

CREATE TABLE sync_system (
  system_id INT IDENTITY PRIMARY KEY,
  application_name VARCHAR(255),
  enabled BIT DEFAULT 1
);

CREATE TABLE sync_table (
  table_id INT IDENTITY PRIMARY KEY,
  system_id INT NOT NULL REFERENCES sync_system(system_id),
  element_name VARCHAR(255),
  mapped_class_name VARCHAR(255),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  create_statement TEXT,
  order_id INT DEFAULT -1,
  sync_item BIT DEFAULT 0,
  object_key VARCHAR(50)
);

CREATE TABLE sync_map (
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  table_id INT NOT NULL REFERENCES sync_table(table_id),
  record_id INT NOT NULL,
  cuid INT NOT NULL,
  complete BIT DEFAULT 0,
  status_date DATETIME
);

CREATE UNIQUE INDEX idx_sync_map ON sync_map (client_id, table_id, record_id);

CREATE TABLE sync_conflict_log (
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  table_id INT NOT NULL REFERENCES sync_table(table_id),
  record_id INT NOT NULL,
  status_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sync_log (
  log_id INT IDENTITY PRIMARY KEY,
  system_id INT NOT NULL REFERENCES sync_system(system_id),
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  ip VARCHAR(15),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sync_transaction_log (
  transaction_id INT IDENTITY PRIMARY KEY,
  log_id INT NOT NULL REFERENCES sync_log(log_id),
  reference_id VARCHAR(50),
  element_name VARCHAR(255),
  action VARCHAR(20),
  link_item_id INT,
  status_code INT,
  record_count INT,
  message TEXT
);

CREATE TABLE process_log (
  process_id INT IDENTITY PRIMARY KEY,
  system_id INT NOT NULL REFERENCES sync_system(system_id),
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  process_name VARCHAR(255),
  process_version VARCHAR(20),
  status INT,
  message TEXT
);
