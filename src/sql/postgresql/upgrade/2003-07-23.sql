/* This permission does not need the edit flag anymore */
UPDATE permission SET permission_edit = false WHERE permission='help';
