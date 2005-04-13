-- Updates permission_category to enable lookup lists for the Product Catalog
UPDATE permission_category SET lookups = 1 WHERE category = 'Product Catalog';
