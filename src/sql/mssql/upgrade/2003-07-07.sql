/* Adds access_type column to messages and uses lookup_access_types table for the same */
insert into lookup_access_types (link_module_id, description, default_item, level, rule_id) values (707031028, 'Controlled-Hierarchy', 1, 1, 626030335);
insert into lookup_access_types (link_module_id, description, default_item, level, rule_id) values (707031028, 'Public', 0, 2, 626030334);
insert into lookup_access_types (link_module_id, description, default_item, level, rule_id) values (707031028, 'Personal', 0, 3, 626030333);

ALTER TABLE message ADD access_type INT;
ALTER TABLE message ADD FOREIGN KEY (access_type) REFERENCES lookup_access_types(code);
UPDATE contact SET access_type = (SELECT code FROM lookup_access_types WHERE link_module_id = 707031028 AND rule_id = 626030335);
