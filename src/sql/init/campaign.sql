
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('company', 'Company Name', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('namefirst', 'Contact First Name', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('namelast', 'Contact Last Name', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('entered', 'Entered Date', true, 1);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('zip', 'Zip Code', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('areacode', 'Area Code', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('city', 'City', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('typeId', 'Contact Type', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('contactId', 'Contact ID', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('title', 'Contact Title', false, 0);

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
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '>', 'greater than');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '<', 'less than');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '=', 'equals');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '>=', 'greater than or equal to');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '<=', 'less than or equal to');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', 'is not null', 'exist');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', 'is null', 'does not exist');

