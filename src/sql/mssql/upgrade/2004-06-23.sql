-- Enable inserting folder data through XML API
UPDATE sync_table SET object_key = 'id' WHERE element_name = 'customFieldRecord';

-- cleanup any dangling records
DELETE FROM action_item_log WHERE item_id IN (SELECT item_id FROM action_item WHERE link_item_id NOT IN (SELECT contact_id FROM contact));
DELETE FROM action_item WHERE link_item_id NOT IN (SELECT contact_id FROM contact);

