/*
   This script needs to be executed on all deployments that have
   category names in english
*/
UPDATE permission_category
SET constant=13, level=100
WHERE category='System';

UPDATE permission_category
SET constant=14, level=200
WHERE category='My Home Page';

UPDATE permission_category
SET constant=420041014, level=300
WHERE category='Products and Services';

UPDATE permission_category
SET constant=228051100, level=400
WHERE category='Leads';

UPDATE permission_category
SET constant=2, level=500
WHERE category='Contacts';

UPDATE permission_category
SET constant=4, level=600
WHERE category='Pipeline';

UPDATE permission_category
SET constant=1, level=700
WHERE category='Accounts';

UPDATE permission_category
SET constant=3, level=800
WHERE category='Auto Guide';

UPDATE permission_category
SET constant=420041017, level=900
WHERE category='Quotes';

UPDATE permission_category
SET constant=420041018, level=1000
WHERE category='Orders';

UPDATE permission_category
SET constant=6, level=1100
WHERE category='Communications';

UPDATE permission_category
SET constant=7, level=1200
WHERE category='Projects';

UPDATE permission_category
SET constant=130041100, level=1300
WHERE category='Service Contracts';

UPDATE permission_category
SET constant=130041000, level=1400
WHERE category='Assets';

UPDATE permission_category
SET constant=8, level=1500
WHERE category='Help Desk';

UPDATE permission_category
SET constant=1202041528, level=1600
WHERE category='Documents';

UPDATE permission_category
SET constant=1111031131, level=1700
WHERE category='Employees';

UPDATE permission_category
SET constant=330041409, level=1800
WHERE category='Product Catalog';

UPDATE permission_category
SET constant=16, level=1900
WHERE category='Reports';

UPDATE permission_category
SET constant=9, level=2000
WHERE category='Admin';

UPDATE permission_category
SET constant=10, level=2100
WHERE category='Help';

UPDATE permission_category
SET constant=15, level=2200
WHERE category='QA';

UPDATE permission_category
SET constant=5, level=2300
WHERE category='Demo';
