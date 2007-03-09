ALTER TABLE contact ADD assigned_date TIMESTAMP(3); 
ALTER TABLE contact ADD lead_trashed_date TIMESTAMP(3);

UPDATE permission_category SET reports = true WHERE constant = 228051100;
