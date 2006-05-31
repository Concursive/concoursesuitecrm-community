
CREATE GENERATOR sync_client_client_id_seq;
CREATE TABLE sync_client (
  client_id INTEGER NOT NULL,
  "type" VARCHAR(100),
  "version" VARCHAR(50),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL,
  anchor TIMESTAMP ,
  enabled CHAR(1) DEFAULT 'N',
  code VARCHAR(255),
  PRIMARY KEY (CLIENT_ID)
);

CREATE GENERATOR sync_system_system_id_seq;
CREATE TABLE sync_system (
  system_id INTEGER NOT NULL,
  application_name VARCHAR(255),
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (SYSTEM_ID)
);

CREATE GENERATOR sync_table_table_id_seq;
CREATE TABLE sync_table (
  table_id INTEGER NOT NULL,
  system_id INTEGER NOT NULL REFERENCES sync_system(system_id),
  element_name VARCHAR(255),
  mapped_class_name VARCHAR(255),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  create_statement BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  order_id INTEGER DEFAULT -1,
  sync_item CHAR(1) DEFAULT 'N',
  object_key VARCHAR(50),
  PRIMARY KEY (TABLE_ID)
);

CREATE TABLE sync_map (
  client_id INTEGER NOT NULL REFERENCES sync_client(client_id),
  table_id INTEGER NOT NULL REFERENCES sync_table(table_id),
  record_id INTEGER NOT NULL,
  cuid INTEGER NOT NULL,
  complete CHAR(1) DEFAULT 'N',
  status_date TIMESTAMP
);

CREATE INDEX idx_sync_map ON sync_map (client_id, table_id, record_id);

CREATE TABLE sync_conflict_log (
  client_id INTEGER NOT NULL REFERENCES sync_client(client_id),
  table_id INTEGER NOT NULL REFERENCES sync_table(table_id),
  record_id INTEGER NOT NULL,
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE GENERATOR sync_log_log_id_seq;
CREATE TABLE sync_log (
  log_id INTEGER  NOT NULL,
  system_id INTEGER NOT NULL REFERENCES sync_system(system_id),
  client_id INTEGER NOT NULL REFERENCES sync_client(client_id),
  ip VARCHAR(15),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (LOG_ID)
);

CREATE GENERATOR sync_transact_transaction_i_seq;
CREATE TABLE sync_transaction_log (
  transaction_id INTEGER  NOT NULL,
  log_id INTEGER NOT NULL REFERENCES sync_log(log_id),
  reference_id VARCHAR(50),
  element_name VARCHAR(255),
  "action" VARCHAR(20),
  link_item_id INTEGER,
  status_code INTEGER,
  record_count INTEGER,
  "message" BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (TRANSACTION_ID)
);

CREATE GENERATOR process_log_process_id_seq;
CREATE TABLE process_log (
  process_id INTEGER  NOT NULL,
  system_id INTEGER NOT NULL REFERENCES sync_system(system_id),
  client_id INTEGER NOT NULL REFERENCES sync_client(client_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  process_name VARCHAR(255),
  process_version VARCHAR(20),
  status INTEGER,
  "message" BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (PROCESS_ID)
);