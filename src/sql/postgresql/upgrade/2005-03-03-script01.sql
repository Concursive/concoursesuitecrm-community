ALTER TABLE asset_category ALTER COLUMN parent_cat_code SET DEFAULT 0;
ALTER TABLE ticket_category ALTER COLUMN parent_cat_code SET DEFAULT 0;
ALTER TABLE asset_category_draft ALTER COLUMN parent_cat_code SET DEFAULT 0;
ALTER TABLE ticket_category_draft ALTER COLUMN parent_cat_code SET DEFAULT 0;
