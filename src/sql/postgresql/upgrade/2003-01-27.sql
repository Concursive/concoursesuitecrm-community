/* January 27, 2003 */

/* Reassign is now a homepage permission, instead of admin */
UPDATE permission 
SET permission = 'myhomepage-reassign'
WHERE permission = 'admin-reassign';

UPDATE permission
SET category_id = permission_category.category_id
FROM permission_category
WHERE permission = 'myhomepage-reassign'
AND permission_category.category = 'My Home Page';

/* Nomenclature Change */
UPDATE permission_category
SET category = 'General Contacts'
WHERE category = 'Contacts & Resources';

