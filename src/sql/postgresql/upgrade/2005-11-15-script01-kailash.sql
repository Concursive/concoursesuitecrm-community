ALTER TABLE ticket_defect ADD COLUMN site_id INT REFERENCES lookup_site_id(code);

