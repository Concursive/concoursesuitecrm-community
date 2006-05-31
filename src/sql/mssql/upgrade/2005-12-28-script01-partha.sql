--Upgrade script to add the column site_id to the opportunity_header table.
ALTER TABLE opportunity_header ADD site_id INT REFERENCES lookup_site_id(code);
