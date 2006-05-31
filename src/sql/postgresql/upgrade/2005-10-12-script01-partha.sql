ALTER TABLE asset ADD COLUMN parent_id INTEGER REFERENCES asset(asset_id);
