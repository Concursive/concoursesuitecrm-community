/* May 30, 2002 All MSSQL servers up-to-date */

/* June 3, 2002 */

DROP TABLE sync_system;
CREATE TABLE sync_system (
  system_id INT IDENTITY PRIMARY KEY,
  application_name VARCHAR(255),
  enabled BIT DEFAULT 1
);

DROP TABLE sync_map;
CREATE TABLE sync_map (
  client_id INT NOT NULL,
  table_id INT NOT NULL,
  record_id INT NOT NULL,
  cuid VARCHAR(50) NOT NULL,
  complete BIT DEFAULT 0,
  status_date DATETIME
);

CREATE UNIQUE INDEX idx_sync_map ON sync_map (client_id, table_id, record_id);

DROP TABLE sync_table;
CREATE TABLE sync_table (
  table_id INT IDENTITY PRIMARY KEY,
  system_id INT NOT NULL,
  element_name VARCHAR(255),
  mapped_class_name VARCHAR(255),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  create_statement TEXT,
  order_id INT DEFAULT -1,
  sync_item BIT DEFAULT 0
);

DROP TABLE sync_client;
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

