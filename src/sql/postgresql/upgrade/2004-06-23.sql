-- Enable inserting folder data through XML API
UPDATE sync_table SET object_key = 'id' WHERE element_name = 'customFieldRecord';


