-- The account contact has the same site as the organization
UPDATE contact
SET site_id = organization.site_id
FROM organization
WHERE contact.org_id = organization.org_id
AND contact.org_id > 0;

-- The employee has the same site as the its user account
UPDATE contact
SET site_id = access.site_id
FROM access
WHERE contact.user_id = access.user_id
AND contact.org_id = 0
AND contact.user_id > 0
AND contact.site_id IS NULL;

-- The account contact has the same site as the import
UPDATE contact
SET site_id = import.site_id
FROM import
WHERE contact.import_id = import.import_id
AND contact.site_id IS NULL;


-- The account contact has the same site as the user who entered it.
UPDATE contact
SET site_id = access.site_id
FROM access
WHERE contact.enteredby = access.user_id
AND contact.import_id IS NULL
AND contact.site_id IS NULL;
