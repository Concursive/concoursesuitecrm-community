/* Permission names to match the menu names */
UPDATE permission SET description = 'Mailbox' WHERE permission = 'myhomepage-inbox';
UPDATE permission SET description = 'Tasks' WHERE permission = 'myhomepage-tasks';
UPDATE permission SET description = 'Profile' WHERE permission = 'myhomepage-profile';
UPDATE permission SET description = 'Action Lists' WHERE permission = 'myhomepage-action-lists';

/* Add referential integrity to opportunity_header 
   NOTE: THE CONSTRAINT ID MUST BE UNIQUE!! 
         LOOK IN THE TABLE AND SEE IF 2 IS THE LAST ENTRY
*/
ALTER TABLE ONLY opportunity_header
  ADD CONSTRAINT "$3" FOREIGN KEY (acctlink) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;
  
ALTER TABLE ONLY opportunity_header
  ADD CONSTRAINT "$4" FOREIGN KEY (contactlink) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;

