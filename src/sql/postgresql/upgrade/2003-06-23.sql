ALTER TABLE contact ADD COLUMN org_name VARCHAR(255);
UPDATE contact SET org_name = company;
UPDATE contact SET org_name = organization.name FROM organization WHERE contact.org_id = organization.org_id;

