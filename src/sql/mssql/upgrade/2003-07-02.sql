/* Adds access types to General Contacts module */
CREATE TABLE lookup_access_types (
  code INT IDENTITY PRIMARY KEY,
  link_module_id INT NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INT, 
  enabled BIT DEFAULT 1,
  rule_id INT NOT NULL
);

insert into lookup_access_types (link_module_id, description, default_item, level, rule_id) values (626030330, 'Controlled-Hierarchy', 1, 1, 626030335);
insert into lookup_access_types (link_module_id, description, default_item, level, rule_id) values (626030330, 'Public', 0, 2, 626030334);
insert into lookup_access_types (link_module_id, description, default_item, level, rule_id) values (626030330, 'Personal', 0, 3, 626030333);
insert into lookup_access_types (link_module_id, description, default_item, level, rule_id) values (626030331, 'Public', 1, 1, 626030334);
insert into lookup_access_types (link_module_id, description, default_item, level, rule_id) values (626030332, 'Public', 1, 1, 626030334);

ALTER TABLE contact ADD access_type INT;
ALTER TABLE contact ADD FOREIGN KEY (access_type) REFERENCES lookup_access_types(code);
UPDATE contact SET access_type = (SELECT code FROM lookup_access_types WHERE description = 'Personal' AND link_module_id = 626030330) WHERE contact.personal = 1;

/* This may need to be done manually if SQL Server complains about a constraint */

ALTER TABLE contact DROP COLUMN personal;
