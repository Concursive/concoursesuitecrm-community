-- TASK: "Various Improvements 2006-09"
-- Add new address fields for organization and contact: County, longitude and latitude.

-- NOTE: Added to new_cdb.sql 2006-10-04

ALTER TABLE organization_address ADD county VARCHAR(80);
ALTER TABLE organization_address ADD latitude FLOAT DEFAULT 0;
ALTER TABLE organization_address ADD longitude FLOAT DEFAULT 0;

ALTER TABLE contact_address ADD county VARCHAR(80);
ALTER TABLE contact_address ADD latitude FLOAT DEFAULT 0;
ALTER TABLE contact_address ADD longitude FLOAT DEFAULT 0;