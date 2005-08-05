-- New colums for contacts
ALTER TABLE contact ADD COLUMN additional_names VARCHAR(255);
ALTER TABLE contact ADD COLUMN nickname VARCHAR(80);
ALTER TABLE contact ADD COLUMN role VARCHAR(255);

-- Removes redundant fields in Contact
ALTER TABLE contact DROP COLUMN imname;
ALTER TABLE contact DROP COLUMN imservice;
