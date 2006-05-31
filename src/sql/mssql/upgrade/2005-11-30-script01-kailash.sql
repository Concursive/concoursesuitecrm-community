ALTER TABLE contact ADD site_id INT REFERENCES lookup_site_id(code);
