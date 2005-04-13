-- Updates permission_category to enable lookup lists for the Product Catalog
UPDATE permission_category SET lookups = true WHERE category = 'Product Catalog';
