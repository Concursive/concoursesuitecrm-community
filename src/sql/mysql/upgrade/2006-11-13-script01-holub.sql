-- TASK: "Offline Client"
-- NOTE: Added to new_sync.sql 2006-11-13

CREATE TABLE sync_package (
  package_id INT AUTO_INCREMENT PRIMARY KEY,
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  type INT NOT NULL,
  size INT DEFAULT 0,
  status_id INT NOT NULL,
  recipient INT NOT NULL,
  status_date TIMESTAMP NULL,
  last_anchor TIMESTAMP NULL,
  next_anchor TIMESTAMP NULL,
  package_file_id INT REFERENCES project_files(item_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sync_package_data (
  data_id INT AUTO_INCREMENT PRIMARY KEY,
  package_id INT NOT NULL REFERENCES sync_package(package_id),
  table_id INT NOT NULL REFERENCES sync_table(table_id),
  action INT NOT NULL,
  identity_start INT NOT NULL,
  `offset` INT,
  items INT,
  last_anchor TIMESTAMP NULL,
  next_anchor TIMESTAMP NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE sync_client ADD COLUMN package_file_id INT REFERENCES project_files(item_id);