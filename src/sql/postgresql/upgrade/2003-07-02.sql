/* Adds access types to General Contacts module */
CREATE TABLE lookup_access_types (
  code SERIAL PRIMARY KEY,
  link_module_id INT NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INT, 
  enabled BOOLEAN DEFAULT true,
  rule_id INT NOT NULL
);

insert into lookup_access_types (link_module_id, description, default_item, level, rule_id) values (626030330, 'Controlled-Hierarchy', true, 1, 626030335);
insert into lookup_access_types (link_module_id, description, default_item, level, rule_id) values (626030330, 'Public', false, 2, 626030334);
insert into lookup_access_types (link_module_id, description, default_item, level, rule_id) values (626030330, 'Personal', false, 3, 626030333);
insert into lookup_access_types (link_module_id, description, default_item, level, rule_id) values (626030331, 'Public', true, 1, 626030334);
insert into lookup_access_types (link_module_id, description, default_item, level, rule_id) values (626030332, 'Public', true, 1, 626030334);

ALTER TABLE contact ADD COLUMN access_type INT;
ALTER TABLE contact ADD FOREIGN KEY (access_type) REFERENCES lookup_access_types(code); 
UPDATE contact SET access_type = lookup_access_types.code WHERE lookup_access_types.description = 'Personal' AND lookup_access_types.link_module_id = 626030330 AND contact.personal = true;
UPDATE contact SET access_type = lookup_access_types.code WHERE lookup_access_types.description = 'Public' AND lookup_access_types.link_module_id = 626030331 AND contact.org_id > -1;
UPDATE contact SET access_type = lookup_access_types.code WHERE lookup_access_types.description = 'Controlled-Hierarchy' AND lookup_access_types.link_module_id = 626030330 AND contact.org_id IS NULL AND contact.personal = false;

ALTER TABLE contact DROP COLUMN personal;

