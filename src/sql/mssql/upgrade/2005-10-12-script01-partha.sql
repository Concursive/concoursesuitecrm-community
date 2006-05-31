ALTER TABLE asset ADD parent_id INTEGER REFERENCES asset(asset_id);
