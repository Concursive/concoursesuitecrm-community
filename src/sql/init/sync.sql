/*	
  sync init
	
  The synchronization process requires that the system be registered so that
  objects can be mapped to requests.
*/

INSERT INTO sync_system (application_name) VALUES ('Vport Telemarketing');
INSERT INTO sync_system (application_name) VALUES ('Land Mark: Auto Guide PocketPC');
INSERT INTO sync_system (application_name) VALUES ('Street Smart Speakers: Web Portal');

INSERT INTO sync_table (system_id, table_name, mapped_class_name)
 VALUES (1, 'ticket', 'com.darkhorseventures.cfsbase.Ticket');

INSERT INTO sync_table (system_id, table_name, mapped_class_name)
 VALUES (2, 'user', 'com.darkhorseventures.cfsbase.User');
INSERT INTO sync_table (system_id, table_name, mapped_class_name)
 VALUES (2, 'syncClient', 'com.darkhorseventures.cfsbase.SyncClient');
INSERT INTO sync_table (system_id, table_name, mapped_class_name)
 VALUES (2, 'tableList', 'com.darkhorseventures.cfsbase.SyncTableList');

