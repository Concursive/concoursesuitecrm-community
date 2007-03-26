-- TASK: "Offline Client"
-- NOTE: Added to new_sync.sql 2006-11-13

CREATE TABLE sync_package (
  package_id SERIAL PRIMARY KEY,
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  type INT NOT NULL,
  size INT DEFAULT 0,
  status_id INT NOT NULL,
  recipient INT NOT NULL,
  status_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_anchor TIMESTAMP(3) DEFAULT NULL,
  next_anchor TIMESTAMP(3) NOT NULL,
  package_file_id INT REFERENCES project_files(item_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);



CREATE TABLE sync_package_data (
  data_id SERIAL PRIMARY KEY,
  package_id INT NOT NULL REFERENCES sync_package(package_id),
  table_id INT NOT NULL REFERENCES sync_table(table_id),
  action INT,
  identity_start INT NOT NULL,
  "offset" INT,
  items INT,
  last_anchor TIMESTAMP(3) DEFAULT NULL,
  next_anchor TIMESTAMP(3) NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE sync_client ADD COLUMN package_file_id INT REFERENCES project_files(item_id);