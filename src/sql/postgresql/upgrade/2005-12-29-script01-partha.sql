UPDATE opportunity_header
SET site_id = organization.site_id
FROM organization
WHERE opportunity_header.acctlink = organization.org_id
AND opportunity_header.acctlink > -1
AND opportunity_header.acctlink IS NOT NULL;

UPDATE opportunity_header
SET site_id = contact.site_id
FROM contact
WHERE opportunity_header.contactlink = contact.contact_id
AND opportunity_header.contactlink > -1
AND opportunity_header.contactlink IS NOT NULL;
