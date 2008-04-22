UPDATE permission SET category_id = (SELECT category_id FROM permission_category WHERE constant = 10) WHERE permission = 'qa';
DELETE from permission_category WHERE constant = 15;
UPDATE permission SET description = 'Help Content Editor' WHERE permission = 'qa';
