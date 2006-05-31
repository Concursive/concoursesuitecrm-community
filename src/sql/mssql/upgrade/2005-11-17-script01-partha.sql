-- script to insert the siteId into the user_group table.
ALTER TABLE user_group ADD site_id INTEGER REFERENCES lookup_site_id(code);
