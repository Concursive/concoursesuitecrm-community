/*	
  init script for the sync client running SQLServerCE
*/

CREATE TABLE sync_table (
       table_id             int NOT NULL,
       table_name           nvarchar(20) NULL,
       order_id             int NULL,
       create_statement     nvarchar(255) NULL,
       sync_item            int NULL,
       PRIMARY KEY (table_id)
)

CREATE TABLE status_master (
       record_status_id     int NOT NULL,
       record_status_name   nchar varying(20) NULL,
       PRIMARY KEY (record_status_id)
)

CREATE TABLE system (
       pda_id               int NOT NULL,
       version              datetime NULL,
       state                int NULL,
       sync_anchor          datetime NULL,
       PRIMARY KEY (pda_id)
)
