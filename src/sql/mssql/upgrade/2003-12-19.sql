UPDATE permission_category SET category = 'Help Desk' WHERE category = 'Tickets';
UPDATE permission SET description = 'Access to Help Desk module' WHERE permission = 'tickets';
UPDATE module_field_categorylink SET description = 'Help Desk' WHERE description = 'Tickets';

