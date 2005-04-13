
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('company', 'Company Name', @TRUE@, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('namefirst', 'Contact First Name', @TRUE@, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('namelast', 'Contact Last Name', @TRUE@, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('entered', 'Entered Date', @TRUE@, 1);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('zip', 'Zip Code', @TRUE@, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('areacode', 'Area Code', @TRUE@, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('city', 'City', @TRUE@, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('typeId', 'Contact Type', @TRUE@, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('contactId', 'Contact ID', @FALSE@, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('title', 'Contact Title', @FALSE@, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('accountTypeId', 'Account Type', @TRUE@, 0);

INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', '=', 'is');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', '!=', 'is not');
INSERT INTO field_types (data_typeID, data_type, operator, display_text, enabled) VALUES (0, 'string', '= | or field_name is null', 'is empty', @FALSE@);
INSERT INTO field_types (data_typeID, data_type, operator, display_text, enabled) VALUES (0, 'string', '!= | and field_name is not null', 'is not empty', @FALSE@);
INSERT INTO field_types (data_typeID, data_type, operator, display_text, enabled) VALUES (0, 'string', 'like %search_value%', 'contains', @FALSE@);
INSERT INTO field_types (data_typeID, data_type, operator, display_text, enabled) VALUES (0, 'string', 'not like %search_value%', 'does not contain', @FALSE@);
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '<', 'before');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '>', 'after');
INSERT INTO field_types (data_typeID, data_type, operator, display_text, enabled) VALUES (1, 'date', 'between', 'between', @FALSE@);
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '<=', 'on or before');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '>=', 'on or after');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '>', 'greater than');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '<', 'less than');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '=', 'equals');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '>=', 'greater than or equal to');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '<=', 'less than or equal to');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', 'is not null', 'exist');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', 'is null', 'does not exist');

INSERT INTO lookup_survey_types (description) VALUES ('Open-Ended');
INSERT INTO lookup_survey_types (description) VALUES ('Quantitative (no comments)');
INSERT INTO lookup_survey_types (description) VALUES ('Quantitative (with comments)');
INSERT INTO lookup_survey_types (description) VALUES ('Item List');
/* General Contacts */
INSERT INTO lookup_access_types (link_module_id, description, default_item, level, rule_id) VALUES (626030330, 'Controlled-Hierarchy', @TRUE@, 1, 626030335);
INSERT INTO lookup_access_types (link_module_id, description, default_item, level, rule_id) VALUES (626030330, 'Public', @FALSE@, 2, 626030334);
INSERT INTO lookup_access_types (link_module_id, description, default_item, level, rule_id) VALUES (626030330, 'Personal', @FALSE@, 3, 626030333);
/* Account Contacts */                                                                      
INSERT INTO lookup_access_types (link_module_id, description, default_item, level, rule_id) VALUES (626030331, 'Public', @TRUE@, 1, 626030334);
/* Employees */                                                                             
INSERT INTO lookup_access_types (link_module_id, description, default_item, level, rule_id) VALUES (626030332, 'Public', @TRUE@, 1, 626030334);
/* Communication Messages */                                                                
INSERT INTO lookup_access_types (link_module_id, description, default_item, level, rule_id) VALUES (707031028, 'Controlled-Hierarchy', @TRUE@, 1, 626030335);
INSERT INTO lookup_access_types (link_module_id, description, default_item, level, rule_id) VALUES (707031028, 'Public', @FALSE@, 2, 626030334);
INSERT INTO lookup_access_types (link_module_id, description, default_item, level, rule_id) VALUES (707031028, 'Personal', @FALSE@, 3, 626030333);
                                                                                            
INSERT INTO survey (name, description, intro, outro, type, enteredby, modifiedby) values ('Address Update Request', '','','',2,0,0);
