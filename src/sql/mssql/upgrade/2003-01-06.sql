/* May 25, 2002  All MSSQL servers up-to-date */

/* May 30, 2002 */

UPDATE autoguide_options SET level = (option_id * 10);

INSERT INTO autoguide_options (option_name, level) VALUES ('CASS', 225);

/* June 3, 2002 */

ALTER TABLE sync_map DROP map_id;
ALTER TABLE sync_map ADD status_date DATETIME;

CREATE UNIQUE INDEX idx_sync_map ON sync_map (client_id, table_id, record_id);

UPDATE STATISTICS sync_map;

