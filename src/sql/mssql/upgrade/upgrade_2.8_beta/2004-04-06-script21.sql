update permission
set description='Ticket Maintenance Notes'
where description='Ticket Asset Maintenance Notes'
and category_id IN (SELECT category_id FROM permission_category WHERE category = 'Accounts');

update permission 
set description='Ticket Activities'
where description='Ticket Activities'
and category_id IN (SELECT category_id FROM permission_category WHERE category = 'Accounts');

update permission 
set description='Activities'
where description='Ticket Activity Log'
and category_id IN (SELECT category_id FROM permission_category WHERE category = 'Help Desk');

update permission 
set description='Maintenance Notes'
where description='Asset Maintenance Notes'
and category_id IN (SELECT category_id FROM permission_category WHERE category = 'Help Desk');
