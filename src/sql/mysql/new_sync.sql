-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------
 
CREATE TABLE sync_client (
  client_id INT AUTO_INCREMENT PRIMARY KEY,
  type VARCHAR(100),
  version VARCHAR(50),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL,
  anchor TIMESTAMP NULL,
  enabled BOOLEAN DEFAULT false,
  code VARCHAR(255)
);

CREATE TABLE sync_system (
  system_id INT AUTO_INCREMENT PRIMARY KEY,
  application_name VARCHAR(255),
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE sync_table (
  table_id INT AUTO_INCREMENT PRIMARY KEY,
  system_id INT NOT NULL REFERENCES sync_system(system_id),
  element_name VARCHAR(255),
  mapped_class_name VARCHAR(255),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL,
  create_statement TEXT,
  order_id INT DEFAULT -1,
  sync_item BOOLEAN DEFAULT false,
  object_key VARCHAR(50)
);

CREATE TABLE sync_map (
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  table_id INT NOT NULL REFERENCES sync_table(table_id),
  record_id INT NOT NULL,
  cuid INT NOT NULL,
  complete BOOLEAN DEFAULT false,
  status_date TIMESTAMP NULL
);

CREATE UNIQUE INDEX idx_sync_map ON sync_map (client_id, table_id, record_id);

CREATE TABLE sync_conflict_log (
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  table_id INT NOT NULL REFERENCES sync_table(table_id),
  record_id INT NOT NULL,
  status_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sync_log (
  log_id INT AUTO_INCREMENT PRIMARY KEY,
  system_id INT NOT NULL REFERENCES sync_system(system_id),
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  ip VARCHAR(15),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sync_transaction_log (
  transaction_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
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
  process_id INT AUTO_INCREMENT PRIMARY KEY,
  system_id INT NOT NULL REFERENCES sync_system(system_id),
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  process_name VARCHAR(255),
  process_version VARCHAR(20),
  status INT,
  message TEXT
);
