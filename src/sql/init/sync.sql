/*	
  sync init
	
  The synchronization process requires that the system be registered so that
  objects can be mapped to requests.
*/

INSERT INTO sync_system (application_name) VALUES ('Vport Telemarketing');
INSERT INTO sync_system (application_name) VALUES ('Land Mark: Auto Guide PocketPC');
INSERT INTO sync_system (application_name) VALUES ('Street Smart Speakers: Web Portal');

/* VPORT */

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (1, 'ticket', 'com.darkhorseventures.cfsbase.Ticket');

/* AUTO GUIDE */

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'syncClient', 'com.darkhorseventures.cfsbase.SyncClient', 10);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'user', 'com.darkhorseventures.cfsbase.User', 20);

 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'tableList', 'com.darkhorseventures.cfsbase.SyncTableList', 30,
'CREATE TABLE sync_table (
       table_id             int NOT NULL,
       element_name           nchar varying(20) NULL,
       order_id             int NULL,
       create_statement     nchar varying(255) NULL,
       PRIMARY KEY (table_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'status_master', null, 40,
'CREATE TABLE status_master (
       record_status_id     int NOT NULL,
       record_status_name   nchar varying(20) NULL,
       PRIMARY KEY (record_status_id)
)'
);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'makeList', 'com.darkhorseventures.autoguide.base.MakeList', 50, true, 
'CREATE TABLE makes (
       make_id              int NOT NULL,
       make_name            nchar varying(20) NULL,
       record_status_id     int NULL,
       record_status_date   datetime NULL,
       PRIMARY KEY (make_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF2makes', null, 60,
'CREATE INDEX XIF2makes ON makes
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'system', null, 70,
'CREATE TABLE system (
       pda_id               int NOT NULL,
       version              datetime NULL,
       state                int NULL,
       sync_anchor          datetime NULL,
       PRIMARY KEY (pda_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'modelList', 'com.darkhorseventures.autoguide.base.ModelList', 80, true, 
'CREATE TABLE make_model (
       model_id             int NOT NULL,
       make_id              int NULL,
       record_status_id     int NULL,
       model_name           nchar varying(40) NULL,
       record_status_date   datetime NULL,
       PRIMARY KEY (model_id), 
       FOREIGN KEY (make_id)
                             REFERENCES makes (make_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF3make_model', null, 90,
'CREATE INDEX XIF3make_model ON make_model
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF5make_model', null, 100,
'CREATE INDEX XIF5make_model ON make_model
(
       make_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'vehicleList', 'com.darkhorseventures.autoguide.base.VehicleList', 110, true, 
'CREATE TABLE make_model_year (
       year                 nchar varying(4) NOT NULL,
       make_id              int NOT NULL,
       model_id             int NOT NULL,
       record_status_id     int NULL,
       record_status_date   datetime NULL,
       PRIMARY KEY (year, make_id, model_id), 
       FOREIGN KEY (model_id)
                             REFERENCES make_model (model_id), 
       FOREIGN KEY (make_id)
                             REFERENCES makes (make_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF4make_model_year', null, 120,
'CREATE INDEX XIF4make_model_year ON make_model_year
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF7make_model_year', null, 130,
'CREATE INDEX XIF7make_model_year ON make_model_year
(
       make_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF8make_model_year', null, 140,
'CREATE INDEX XIF8make_model_year ON make_model_year
(
       model_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'account', null, 150,
'CREATE TABLE account (
       account_id           int NOT NULL,
       account_name         nchar varying(20) NULL,
       record_status_id     int NULL,
       record_status_date   datetime NULL,
       PRIMARY KEY (account_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF16account', null, 160,
'CREATE INDEX XIF16account ON account
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'userList', 'com.darkhorseventures.cfsbase.UserList', 170, true, 
'CREATE TABLE sales_rep (
       rep_id               int NOT NULL,
       record_status_id     int NULL,
       user_name            nchar varying(20) NULL,
       pin                  nchar varying(20) NULL,
       record_status_date   datetime NULL,
       PRIMARY KEY (rep_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF18sales_rep', null, 180,
'CREATE INDEX XIF18sales_rep ON sales_rep
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'accountInventoryList', null, 190,
'CREATE TABLE vehicles (
       vehicle_id           int NOT NULL,
       vin                  nchar varying(20) NULL,
       account_id           int NULL,
       make_id              int NULL,
       model_id             int NULL,
       year                 nchar varying(4) NULL,
       adtype               nchar varying(20) NULL,
       mileage              nchar varying(20) NULL,
       entered              int NULL,
       modified             int NULL,
       condition            nchar varying(20) NULL,
       comments             nchar varying(255) NULL,
       stock_id             nchar varying(20) NULL,
       ext_color            nchar varying(20) NULL,
       int_color            nchar varying(20) NULL,
       selling_price        nchar varying(20) NULL,
       status               nchar varying(20) NULL,
       rep_id               int NULL,
       record_status_id     int NULL,
       record_status_date   nchar varying(20) NULL,
       PRIMARY KEY (vehicle_id), 
       FOREIGN KEY (account_id)
                             REFERENCES account (account_id), 
       FOREIGN KEY (rep_id)
                             REFERENCES sales_rep (rep_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id), 
       FOREIGN KEY (year, make_id, model_id)
                             REFERENCES make_model_year (year, make_id, model_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF10vehicles', null, 200,
'CREATE INDEX XIF10vehicles ON vehicles
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF11vehicles', null, 210,
'CREATE INDEX XIF11vehicles ON vehicles
(
       rep_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF19vehicles', null, 220,
'CREATE INDEX XIF19vehicles ON vehicles
(
       account_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF9vehicles', null, 230,
'CREATE INDEX XIF9vehicles ON vehicles
(
       year,
       make_id,
       model_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'sales_rep_account', null, 240,
'CREATE TABLE sales_rep_account (
       rep_id               int NOT NULL,
       account_id           int NOT NULL,
       record_status_id     int NULL,
       record_status_date   nchar varying(20) NULL,
       PRIMARY KEY (rep_id, account_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id), 
       FOREIGN KEY (account_id)
                             REFERENCES account (account_id), 
       FOREIGN KEY (rep_id)
                             REFERENCES sales_rep (rep_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF14sales_rep_account', null, 250,
'CREATE INDEX XIF14sales_rep_account ON sales_rep_account
(
       rep_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF14sales_rep_account', null, 260,
'CREATE INDEX XIF15sales_rep_account ON sales_rep_account
(
       account_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF17sales_rep_account', null, 270,
'CREATE INDEX XIF17sales_rep_account ON sales_rep_account
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'vehicle_picture', null, 280,
'CREATE TABLE vehicle_picture (
       pitcure_name         nchar varying(20) NOT NULL,
       vehicle_id           int NOT NULL,
       record_status_id     int NULL,
       record_status_date   datetime NULL,
       PRIMARY KEY (pitcure_name, vehicle_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id), 
       FOREIGN KEY (vehicle_id)
                             REFERENCES vehicles (vehicle_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF20vehicle_picture', null, 290,
'CREATE INDEX XIF20vehicle_picture ON vehicle_picture
(
       vehicle_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF23vehicle_picture', null, 300,
'CREATE INDEX XIF23vehicle_picture ON vehicle_picture
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'ad_run', null, 310,
'CREATE TABLE ad_run (
       vehicle_id           int NOT NULL,
       record_status_id     int NULL,
       start_date           datetime NULL,
       end_date             datetime NULL,
       record_status_date   nchar varying(20) NULL,
       PRIMARY KEY (vehicle_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id), 
       FOREIGN KEY (vehicle_id)
                             REFERENCES vehicles (vehicle_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF22ad_run', null, 320,
'CREATE INDEX XIF22ad_run ON ad_run
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'options', null, 330,
'CREATE TABLE options (
       option_id            int NOT NULL,
       option_name          nchar varying(20) NULL,
       record_status_id     int NULL,
       record_status_date   datetime NULL,
       PRIMARY KEY (option_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF24options', null, 340,
'CREATE INDEX XIF24options ON options
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'vehicle_options', null, 350,
'CREATE TABLE vehicle_options (
       vehicle_id           int NOT NULL,
       option_id            int NOT NULL,
       record_status_id     int NULL,
       record_status_date   nchar varying(20) NULL,
       PRIMARY KEY (vehicle_id, option_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id), 
       FOREIGN KEY (vehicle_id)
                             REFERENCES vehicles (vehicle_id), 
       FOREIGN KEY (option_id)
                             REFERENCES options (option_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF25vehicle_options', null, 360,
'CREATE INDEX XIF25vehicle_options ON vehicle_options
(
       option_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF26vehicle_options', null, 370,
'CREATE INDEX XIF26vehicle_options ON vehicle_options
(
       vehicle_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF27vehicle_options', null, 380,
'CREATE INDEX XIF27vehicle_options ON vehicle_options
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'preferences', null, 390,
'CREATE TABLE preferences (
       rep_id               int NOT NULL,
       record_status_id     int NULL,
       record_status_date   nchar varying(20) NULL,
       PRIMARY KEY (rep_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id), 
       FOREIGN KEY (rep_id)
                             REFERENCES sales_rep (rep_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF29preferences', null, 400,
'CREATE INDEX XIF29preferences ON preferences
(
       record_status_id
)'
);







