UPDATE ticket
SET site_id = organization.site_id
FROM organization
WHERE ticket.org_id = organization.org_id
AND ticket.org_id > 0;
