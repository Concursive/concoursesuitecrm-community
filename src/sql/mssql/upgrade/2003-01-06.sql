/* May 25, 2002  All MSSQL servers up-to-date */

/* May 30, 2002 */

UPDATE autoguide_options SET level = (option_id * 10);

INSERT INTO autoguide_options (option_name, level) VALUES ('CASS', 225);

/* June 3, 2002 */

DROP TABLE sync_map;
CREATE TABLE sync_map (
  client_id INT NOT NULL,
  table_id INT NOT NULL,
  record_id INT NOT NULL,
  cuid VARCHAR(50) NOT NULL,
  complete BIT DEFAULT false,
  status_date DATETIME
);

CREATE UNIQUE INDEX idx_sync_map ON sync_map (client_id, table_id, record_id);

UPDATE STATISTICS sync_map;

