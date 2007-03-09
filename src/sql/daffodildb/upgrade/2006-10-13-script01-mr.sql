
UPDATE permission SET permission_add = FALSE, permission_edit = FALSE, permission_delete = FALSE WHERE permission = 'product-catalog';
UPDATE permission SET enabled = TRUE WHERE permission = 'product-catalog-product';
UPDATE report SET enabled = TRUE WHERE filename = 'leads_user.xml';

