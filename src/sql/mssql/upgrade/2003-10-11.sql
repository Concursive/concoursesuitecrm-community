UPDATE permission SET description = 'Export Contact Data' WHERE permission = 'contacts-external_contacts-reports';
UPDATE permission SET description = 'Export Opportunity Data' WHERE permission = 'pipeline-reports';
UPDATE permission SET description = 'Export Account Data' WHERE permission = 'accounts-accounts-reports';
UPDATE permission SET description = 'Export Ticket Data' WHERE permission = 'tickets-reports';

