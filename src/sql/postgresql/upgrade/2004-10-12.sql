-- Renamed some permissions
UPDATE permission SET description = 'Activities' WHERE permission = 'pipeline-opportunities-calls';
UPDATE permission SET description = 'Contact Activities' WHERE permission = 'accounts-accounts-contacts-calls';
UPDATE permission SET description = 'Activities' WHERE permission = 'contacts-external_contacts-calls';


