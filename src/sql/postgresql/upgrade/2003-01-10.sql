/* May 30, 2002  All PostgreSQL servers up-to-date */


/* June 3, 2002 */

DROP TABLE sync_map;
DROP SEQUENCE sync_map_map_id_seq;

CREATE TABLE sync_map (
  client_id INT NOT NULL,
  table_id INT NOT NULL,
  record_id INT NOT NULL,
  cuid VARCHAR(50) NOT NULL,
  complete BOOLEAN DEFAULT false,
  status_date TIMESTAMP
);

CREATE UNIQUE INDEX idx_sync_map ON sync_map (client_id, table_id, record_id);

vacuum;


