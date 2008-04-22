-- TASK: "Various Improvements 2006-09"
-- Add new address fields for organization and contact: County, longitude and latitude.

-- NOTE: Added to new_cdb.sql 2006-10-04

ALTER TABLE organization_address ADD COLUMN county VARCHAR(80);
ALTER TABLE organization_address ADD COLUMN latitude FLOAT DEFAULT 0;
ALTER TABLE organization_address ADD COLUMN longitude FLOAT DEFAULT 0;

ALTER TABLE contact_address ADD COLUMN county VARCHAR(80);
ALTER TABLE contact_address ADD COLUMN latitude FLOAT DEFAULT 0;
ALTER TABLE contact_address ADD COLUMN longitude FLOAT DEFAULT 0;