/*
  New columns to accomadate portal users
*/
ALTER TABLE role ADD role_type INT;
ALTER TABLE contact_emailaddress ADD primary_email BIT;

UPDATE role SET role_type=0;
