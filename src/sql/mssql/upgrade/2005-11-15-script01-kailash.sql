ALTER TABLE ticket_defect ADD site_id INT REFERENCES lookup_site_id(code);

