/*
  New columns to accomadate portal users
*/
ALTER TABLE access ADD COLUMN role_type INT;
ALTER TABLE role ADD COLUMN role_type INT;
ALTER TABLE contact_emailaddress ADD COLUMN primary_email BOOLEAN;

UPDATE access SET role_type=0;
UPDATE role SET role_type=0;
