/* Add employee and personal field to contact table for optimization of ContactList */
ALTER TABLE contact ADD COLUMN employee BOOLEAN;
ALTER TABLE contact ALTER employee SET DEFAULT FALSE;
ALTER TABLE contact ADD COLUMN personal BOOLEAN;
ALTER TABLE contact ALTER personal SET DEFAULT FALSE;

update contact set employee = 't' where contact.contact_id in (select contact_id from contact_type_levels ctl where ctl.contact_id = contact.contact_id AND ctl.type_id = '1');
update contact set employee = false where employee is null;
update contact set personal = 'f';
