/* Rename the categories */
UPDATE permission_category SET category = 'Contacts' where category = 'General Contacts';
UPDATE permission_category SET category = 'Pipeline' where category = 'Pipeline Management';
UPDATE permission_category SET category = 'Accounts' where category = 'Account Management';
UPDATE permission_category SET category = 'Communications' where category = 'Campaign Management';

/* Rename the permissions */
UPDATE permission SET description = 'Access to Contacts module' WHERE permission = 'contacts';
UPDATE permission SET description = 'General Contact Records' WHERE permission = 'contacts-external_contacts';
UPDATE permission SET description = 'Access to Pipeline module' WHERE permission = 'pipeline';
UPDATE permission SET description = 'Access to Accounts module' WHERE permission = 'accounts';
UPDATE permission SET description = 'Contact Opportunities' WHERE permission = 'accounts-accounts-contacts-opportunities';
UPDATE permission SET description = 'Contact Calls' WHERE permission = 'accounts-accounts-contacts-calls';
UPDATE permission SET description = 'Contact Messages' WHERE permission = 'accounts-accounts-contacts-messages';
UPDATE permission SET description = 'Access to Communications module' WHERE permission = 'campaign';
UPDATE permission SET description = 'Group Records' WHERE permission = 'campaign-campaigns-groups';
UPDATE permission SET description = 'Message Records' WHERE permission = 'campaign-campaigns-messages';
UPDATE permission SET description = 'Survey Records' WHERE permission = 'campaign-campaigns-surveys';

