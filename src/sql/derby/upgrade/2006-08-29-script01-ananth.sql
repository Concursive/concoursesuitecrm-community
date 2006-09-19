ALTER TABLE contact ADD assigned_date TIMESTAMP;
ALTER TABLE contact ADD lead_trashed_date TIMESTAMP;

UPDATE permission_category SET reports = '1' WHERE constant = 228051100;
