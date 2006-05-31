-- Adds a new column to permission_category

ALTER TABLE permission_category ADD custom_list_views BIT NOT NULL DEFAULT 0;
UPDATE permission_category SET custom_list_views = 0;
