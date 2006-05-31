-- Script to add site_id to the category and category draft tables
ALTER TABLE ticket_category_draft ADD site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE ticket_category ADD site_id INTEGER REFERENCES lookup_site_id(code);

ALTER TABLE asset_category_draft ADD site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE asset_category ADD site_id INTEGER REFERENCES lookup_site_id(code);

ALTER TABLE action_plan_category_draft ADD site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE action_plan_category ADD site_id INTEGER REFERENCES lookup_site_id(code);

