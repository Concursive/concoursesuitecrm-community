CREATE SEQUENCE sync_client_client_id_seq;
CREATE TABLE sync_client (
  client_id INT PRIMARY KEY,
  type VARCHAR(100),
  version VARCHAR(50),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedby INT NOT NULL,
  anchor TIMESTAMP ,
  enabled boolean DEFAULT false,
  code VARCHAR(255)
);

CREATE SEQUENCE sync_system_system_id_seq;
CREATE TABLE sync_system (
  system_id INT PRIMARY KEY,
  application_name VARCHAR(255),
  enabled boolean DEFAULT true
);

CREATE SEQUENCE sync_table_table_id_seq;
CREATE TABLE sync_table (
  table_id INT PRIMARY KEY,
  system_id INT NOT NULL REFERENCES sync_system(system_id),
  element_name VARCHAR(255),
  mapped_class_name VARCHAR(255),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  create_statement CLOB,
  order_id INT DEFAULT -1,
  sync_item boolean DEFAULT false,
  object_key VARCHAR(50)
);

CREATE TABLE sync_map (
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  table_id INT NOT NULL REFERENCES sync_table(table_id),
  record_id INT NOT NULL,
  cuid INT NOT NULL,
  complete boolean DEFAULT false,
  status_date TIMESTAMP
);

CREATE INDEX idx_sync_map ON sync_map (client_id, table_id, record_id);

CREATE TABLE sync_conflict_log (
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  table_id INT NOT NULL REFERENCES sync_table(table_id),
  record_id INT NOT NULL,
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL 
);

CREATE SEQUENCE sync_log_log_id_seq;
CREATE TABLE sync_log (
  log_id INT  PRIMARY KEY,
  system_id INT NOT NULL REFERENCES sync_system(system_id),
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  ip VARCHAR(15),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE SEQUENCE sync_transact_transaction_i_seq;
CREATE TABLE sync_transaction_log (
  transaction_id INT  PRIMARY KEY,
  log_id INT NOT NULL REFERENCES sync_log(log_id),
  reference_id VARCHAR(50),
  element_name VARCHAR(255),
  action VARCHAR(20),
  link_item_id INT,
  status_code INT,
  record_count INT,
  message CLOB
);

CREATE SEQUENCE process_log_process_id_seq;
CREATE TABLE process_log (
  process_id INT  PRIMARY KEY,
  system_id INT NOT NULL REFERENCES sync_system(system_id),
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  process_name VARCHAR(255),
  process_version VARCHAR(20),
  status INT,
  message CLOB
);
