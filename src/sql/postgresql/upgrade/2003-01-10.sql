DROP TABLE search_fields;
DROP SEQUENCE search_fields_id_seq;

CREATE TABLE search_fields (
id serial PRIMARY KEY,
field varchar(80),
description VARCHAR(255),
searchable BOOLEAN NOT NULL DEFAULT 't',
field_typeID int NOT NULL DEFAULT -1,
table_name varchar(80),
object_class varchar(80)
);

INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('company', 'Company Name', 't', 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('namefirst', 'Contact First Name', 't', 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('namelast', 'Contact Last Name', 't', 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('entered', 'Entered Date', 't', 1);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('zip', 'Zip Code', 't', 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('areacode', 'Area Code', 't', 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('city', 'City', 't', 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('typeId', 'Contact Type', 't', 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('contactId', 'Contact ID', 't', 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('title', 'Contact Title', 'f', 0);

alter table contact add column url varchar(100);

/*   3/25/2002  

  On the next update of ASPCFS.com, make sure to update the following
  in cfs2gk:
  
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_cfs' WHERE site_id = 1;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_cfs' WHERE site_id = 2;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_demo' WHERE site_id = 7;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_edit' WHERE site_id = 5;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_dhv' WHERE site_id = 6;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_sss' WHERE site_id = 4;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_vport' WHERE site_id = 8;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_partners' WHERE site_id = 9;
  UPDATE sites SET dbhost = 'jdbc:postgresql://127.0.0.1:5432/cdb_insurance' WHERE site_id = 10;

*/


/*  3/29/2002  */
ALTER TABLE custom_field_category ADD COLUMN multiple_records BOOLEAN;
ALTER TABLE custom_field_category ALTER COLUMN multiple_records SET DEFAULT false;
UPDATE custom_field_category SET multiple_records = true;

/*  4/2/2002  */
ALTER TABLE custom_field_category ADD COLUMN read_only BOOLEAN;
ALTER TABLE custom_field_category ALTER COLUMN read_only SET DEFAULT false;
UPDATE custom_field_category SET read_only = false;

ALTER TABLE custom_field_info ADD COLUMN additional_text VARCHAR(255);

