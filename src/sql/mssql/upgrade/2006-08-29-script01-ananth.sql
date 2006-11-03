ALTER TABLE contact ADD assigned_date DATETIME;
ALTER TABLE contact ADD lead_trashed_date DATETIME;

UPDATE permission_category SET reports = 1 WHERE constant = 228051100;
