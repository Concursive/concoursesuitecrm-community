/* Permission names to match the menu names */
UPDATE permission SET description = 'Mailbox' WHERE permission = 'myhomepage-inbox';
UPDATE permission SET description = 'Tasks' WHERE permission = 'myhomepage-tasks';
UPDATE permission SET description = 'Profile' WHERE permission = 'myhomepage-profile';
UPDATE permission SET description = 'Action Lists' WHERE permission = 'myhomepage-action-lists';

