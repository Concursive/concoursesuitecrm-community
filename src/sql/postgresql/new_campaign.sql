
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
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('title', 'Contact Title', 'f', 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('entered', 'Entered Date', 't', 1);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('zip', 'Zip Code', 't', 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('areacode', 'Area Code', 't', 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('city', 'City', 't', 0);

/*
INSERT INTO search_fields (id,field,description,searchable,field_typeid,table_name,object_class) VALUES (1,'company','Company Name','t',0,NULL,NULL);
INSERT INTO search_fields (id,field,description,searchable,field_typeid,table_name,object_class) VALUES (2,'namefirst','Contact First Name','t',0,NULL,NULL);
INSERT INTO search_fields (id,field,description,searchable,field_typeid,table_name,object_class) VALUES (3,'namelast','Contact Last Name','t',0,NULL,NULL);
INSERT INTO search_fields (id,field,description,searchable,field_typeid,table_name,object_class) VALUES (4,'entered','Entered Date','t',1,NULL,NULL);
INSERT INTO search_fields (id,field,description,searchable,field_typeid,table_name,object_class) VALUES (5,'zip','Zip Code','t',0,NULL,NULL);
INSERT INTO search_fields (id,field,description,searchable,field_typeid,table_name,object_class) VALUES (6,'areacode','Area Code','t',0,NULL,NULL);
INSERT INTO search_fields (id,field,description,searchable,field_typeid,table_name,object_class) VALUES (7,'city','City','t',0,NULL,NULL);
INSERT INTO search_fields (id,field,description,searchable,field_typeid,table_name,object_class) VALUES (8,'title','Contact Title','f',0,NULL,NULL);
*/

CREATE TABLE field_types (
  id serial PRIMARY KEY,
  data_typeID int NOT NULL DEFAULT -1,
	data_type VARCHAR(20),
  operator VARCHAR(50),
  display_text varchar(50)
);

INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', '=', 'is');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', '!=', 'is not');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', '= | or field_name is null', 'is empty');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', '!= | and field_name is not null', 'is not empty');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', 'like %search_value%', 'contains');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', 'not like %search_value%', 'does not contain');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '<', 'before');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '>', 'after');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', 'between', 'between');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '<=', 'on or before');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '>=', 'on or after');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', 'is not null', 'exist');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', 'is null', 'does not exist');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '>', 'greater than');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '<', 'less than');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '=', 'equals');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '>=', 'greater than or equal to');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '<=', 'less than or equal to');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', 'is not null', 'exist');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', 'is null', 'does not exist');

