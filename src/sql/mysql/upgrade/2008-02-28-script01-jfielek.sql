/* 
  This script removes the processing_behavior column 
  from the email_account tables and its supporting lookup 
  list, as wella s adding in the server_type_name string 
  for the email_account
*/
ALTER TABLE email_account DROP COLUMN processing_behavior ;
DROP TABLE lookup_emailaccount_processing_behavior;

ALTER TABLE email_account ADD server_type_name CHARACTER(32);

