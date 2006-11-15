-- Remove constraints

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CNSTR CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(o.name)
     FROM sysobjects o
     INNER JOIN sysconstraints sc ON o.id = sc.constid
     WHERE object_name(o.parent_obj) = ''permission_category''
     AND o.xtype = ''D''
     AND o.name LIKE ''DF__permissio__produ__%''
OPEN CNSTR
FETCH CNSTR INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN
    EXEC sp_executesql @sql
    FETCH CNSTR INTO @sql
END
CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)','[permission_category]'
GO

ALTER TABLE permission_category ALTER COLUMN [products] [bit] NULL;

ALTER TABLE permission_category DROP COLUMN products;

-- Remove constraints

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CNSTR CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(o.name)
     FROM sysobjects o
     INNER JOIN sysconstraints sc ON o.id = sc.constid
     WHERE object_name(o.parent_obj) = ''permission_category''
     AND o.xtype = ''D''
     AND o.name LIKE ''DF__permissio__impor__%''
OPEN CNSTR
FETCH CNSTR INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN
    EXEC sp_executesql @sql
    FETCH CNSTR INTO @sql
END
CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)','[permission_category]'
GO

ALTER TABLE permission_category ALTER COLUMN [importer] [bit] NULL;

ALTER TABLE permission_category DROP COLUMN importer;


update role_permission 
 set permission_id = (select permission_id from permission where permission = 'product-catalog-product')
 where permission_id = (select permission_id from permission where permission = 'admin-sysconfig-products');

update permission
 set enabled = 1,
     active = 1
 where permission = 'product-catalog-product';

update permission
 set enabled = 0,
     active = 0
 where permission = 'admin-sysconfig-products';

delete from permission 
 where permission = 'admin-sysconfig-products';