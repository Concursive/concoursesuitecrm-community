--Code to insert the site_id into action_plan table
ALTER TABLE action_plan ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);
