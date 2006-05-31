--Upgrade script to add the field site_id to the opportunity_header table
ALTER TABLE opportunity_header ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);
