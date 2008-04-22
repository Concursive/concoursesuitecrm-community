
UPDATE permission SET permission_add = false, permission_edit = false, permission_delete = false WHERE permission = 'product-catalog';
UPDATE permission SET enabled = true WHERE permission = 'product-catalog-product';
UPDATE report SET enabled = true WHERE filename = 'leads_user.xml';

