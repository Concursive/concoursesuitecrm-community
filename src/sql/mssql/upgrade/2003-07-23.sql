/* This permission does not need the edit flag anymore */
UPDATE permission SET permission_edit = 0 WHERE permission='help';
