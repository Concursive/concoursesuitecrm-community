
CREATE SEQUENCE sync_client_client_id_seq;
CREATE TABLE sync_client (
  client_id INTEGER NOT NULL,
  "type" NVARCHAR2(100),
  "version" NVARCHAR2(50),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INTEGER NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INTEGER NOT NULL,
  anchor TIMESTAMP ,
  enabled CHAR(1) DEFAULT 0,
  code NVARCHAR2(255),
  PRIMARY KEY (CLIENT_ID)
);

CREATE SEQUENCE sync_system_system_id_seq;
CREATE TABLE sync_system (
  system_id INTEGER NOT NULL,
  application_name NVARCHAR2(255),
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (SYSTEM_ID)
);

CREATE SEQUENCE sync_table_table_id_seq;
CREATE TABLE sync_table (
  table_id INTEGER NOT NULL,
  system_id INTEGER NOT NULL REFERENCES sync_system(system_id),
  element_name NVARCHAR2(255),
  mapped_class_name NVARCHAR2(255),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  create_statement CLOB,
  order_id INTEGER DEFAULT -1,
  sync_item CHAR(1) DEFAULT 0,
  object_key NVARCHAR2(50),
  PRIMARY KEY (TABLE_ID)
);

CREATE TABLE sync_map (
  client_id INTEGER NOT NULL REFERENCES sync_client(client_id),
  table_id INTEGER NOT NULL REFERENCES sync_table(table_id),
  record_id INTEGER NOT NULL,
  cuid INTEGER NOT NULL,
  complete CHAR(1) DEFAULT 0,
  status_date TIMESTAMP
);

CREATE INDEX idx_sync_map ON sync_map (client_id, table_id, record_id);

CREATE TABLE sync_conflict_log (
  client_id INTEGER NOT NULL REFERENCES sync_client(client_id),
  table_id INTEGER NOT NULL REFERENCES sync_table(table_id),
  record_id INTEGER NOT NULL,
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE SEQUENCE sync_log_log_id_seq;
CREATE TABLE sync_log (
  log_id INTEGER  NOT NULL,
  system_id INTEGER NOT NULL REFERENCES sync_system(system_id),
  client_id INTEGER NOT NULL REFERENCES sync_client(client_id),
  ip NVARCHAR2(15),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (LOG_ID)
);

CREATE SEQUENCE sync_transact_ransaction_i_seq;
CREATE TABLE sync_transaction_log (
  transaction_id INTEGER  NOT NULL,
  log_id INTEGER NOT NULL REFERENCES sync_log(log_id),
  reference_id NVARCHAR2(50),
  element_name NVARCHAR2(255),
  "action" NVARCHAR2(20),
  link_item_id INTEGER,
  status_code INTEGER,
  record_count INTEGER,
  "message" CLOB,
  PRIMARY KEY (TRANSACTION_ID)
);

CREATE SEQUENCE process_log_process_id_seq;
CREATE TABLE process_log (
  process_id INTEGER  NOT NULL,
  system_id INTEGER NOT NULL REFERENCES sync_system(system_id),
  client_id INTEGER NOT NULL REFERENCES sync_client(client_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  process_name NVARCHAR2(255),
  process_version NVARCHAR2(20),
  status INTEGER,
  "message" CLOB,
  PRIMARY KEY (PROCESS_ID)
);
