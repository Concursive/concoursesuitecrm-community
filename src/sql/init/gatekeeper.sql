/*
	Data records for gatekeeper database
*/

DELETE FROM sites;

INSERT INTO sites 
(sitecode,vhost,dbhost,dbname,dbport,dbuser,dbpw,driver) 
VALUES 
('ds21','ds21.darkhorseventures.com'
,'jdbc:postgresql://216.54.81.101','cdb_ds21',5432,'cfsdba','','org.postgresql.Driver');

INSERT INTO sites 
(sitecode,vhost,dbhost,dbname,dbport,dbuser,dbpw,driver) 
VALUES 
('ds22','ds23.darkhorseventures.com'
,'jdbc:postgresql://12.101.73.29','cdb_ds23',5432,'cfsdba','','org.postgresql.Driver');



