/*
  New columns to accomadate portal users
*/
ALTER TABLE role ADD COLUMN role_type INT;
ALTER TABLE contact_emailaddress ADD COLUMN primary_email BOOLEAN;

UPDATE role SET role_type=0;
