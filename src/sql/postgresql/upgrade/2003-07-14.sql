/* No longer are these items stored here.  A previous update moved the entries already */

ALTER TABLE contact DROP COLUMN type_id;
ALTER TABLE contact DROP COLUMN custom2;
ALTER TABLE contact DROP COLUMN custom_data;

DELETE FROM contact_type_levels WHERE type_id IN 
  (SELECT code FROM lookup_contact_types WHERE description = 'Employee' AND code < 5);
DELETE FROM lookup_contact_types WHERE description = 'Employee' AND code < 5;

DELETE FROM contact_type_levels WHERE type_id IN 
  (SELECT code FROM lookup_contact_types WHERE description = 'Personal' AND code < 5);
DELETE FROM lookup_contact_types WHERE description = 'Personal' AND code < 5;


