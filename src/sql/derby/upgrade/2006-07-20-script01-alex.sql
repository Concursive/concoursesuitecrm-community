-- Derby supports adding but not dropping columns!!!
-- alter table permission_category drop column products;

-- alter table permission_category drop column importer;

update role_permission 
 set permission_id = (select permission_id from permission where permission = 'product-catalog-product')
 where permission_id = (select permission_id from permission where permission = 'admin-sysconfig-products');

update permission
 set enabled = '1',
    "active" = '1'
 where permission = 'product-catalog-product';

update permission
 set enabled = '0',
    "active" = '0'
 where permission = 'admin-sysconfig-products';
