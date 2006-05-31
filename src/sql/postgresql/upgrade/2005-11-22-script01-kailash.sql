ALTER TABLE import ADD COLUMN site_id INT REFERENCES lookup_site_id(code);

