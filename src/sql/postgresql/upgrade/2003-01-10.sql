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
