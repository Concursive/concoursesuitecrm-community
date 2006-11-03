
UPDATE permission SET permission_add = 0, permission_edit = 0, permission_delete = 0 WHERE permission = 'product-catalog';
UPDATE permission SET enabled = 1 WHERE permission = 'product-catalog-product';
UPDATE report SET enabled = 1 WHERE filename = 'leads_user.xml';

