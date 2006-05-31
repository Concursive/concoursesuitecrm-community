-- Adds a new column to permission_category

ALTER TABLE permission_category ADD COLUMN custom_list_views BOOLEAN;
UPDATE permission_category SET custom_list_views = false;
ALTER TABLE permission_category ALTER COLUMN custom_list_views SET NOT NULL;
ALTER TABLE permission_category ALTER COLUMN custom_list_views SET DEFAULT FALSE;
