alter table permission_category
 drop column products;

alter table permission_category
 drop column importer;

update role_permission 
 set permission_id = (select permission_id from permission where permission = 'product-catalog-product')
 where permission_id = (select permission_id from permission where permission = 'admin-sysconfig-products');

update permission
 set enabled = 'Y',
     active = 'Y'
 where permission = 'product-catalog-product';

update permission
 set enabled = 'N',
     active = 'N'
 where permission = 'admin-sysconfig-products';

delete from permission 
 where permission = 'admin-sysconfig-products';