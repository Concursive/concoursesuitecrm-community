-- New colums for contacts
ALTER TABLE contact ADD additional_names VARCHAR(255);
ALTER TABLE contact ADD nickname VARCHAR(80);
ALTER TABLE contact ADD role VARCHAR(255);

-- Removes redundant fields in Contact
ALTER TABLE contact DROP COLUMN imname;
ALTER TABLE contact DROP COLUMN imservice;
