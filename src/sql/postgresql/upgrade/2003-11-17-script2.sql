UPDATE sync_table SET object_key = 'id' WHERE element_name = 'ticket';
UPDATE sync_table SET object_key = 'id' WHERE element_name = 'customFieldRecord';

/* Adding folders to tickets module */
UPDATE permission_category SET folders = true WHERE category = 'Tickets';

