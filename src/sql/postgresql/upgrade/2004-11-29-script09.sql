-- Update the position of all categories

UPDATE permission_category SET level =  100 WHERE category = 'System';
UPDATE permission_category SET level =  200 WHERE category = 'My Home Page';
UPDATE permission_category SET level =  300 WHERE category = 'Products and Services';
UPDATE permission_category SET level =  400 WHERE category = 'Employees';
UPDATE permission_category SET level =  500 WHERE category = 'Contacts';
UPDATE permission_category SET level =  600 WHERE category = 'Pipeline';
UPDATE permission_category SET level =  700 WHERE category = 'Accounts';
UPDATE permission_category SET level =  800 WHERE category = 'Auto Guide';
UPDATE permission_category SET level =  900 WHERE category = 'Quotes';
UPDATE permission_category SET level = 1000 WHERE category = 'Orders';
UPDATE permission_category SET level = 1100 WHERE category = 'Product Catalog';
UPDATE permission_category SET level = 1200 WHERE category = 'Communications';
UPDATE permission_category SET level = 1300 WHERE category = 'Projects';
UPDATE permission_category SET level = 1400 WHERE category = 'Service Contracts';
UPDATE permission_category SET level = 1500 WHERE category = 'Assets';
UPDATE permission_category SET level = 1600 WHERE category = 'Help Desk';
UPDATE permission_category SET level = 1700 WHERE category = 'Reports';
UPDATE permission_category SET level = 1800 WHERE category = 'Admin';
UPDATE permission_category SET level = 1900 WHERE category = 'Help';
UPDATE permission_category SET level = 2000 WHERE category = 'QA';
UPDATE permission_category SET level = 2100 WHERE category = 'Demo';

