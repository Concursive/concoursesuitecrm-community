-- Copyright (C) 2005 Concursive Corporation, all rights reserved
-- Database upgrade v3.0 (2005-03-30)

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CRFK CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+QUOTENAME(USER_NAME(OBJECTPROPERTY(fkeyid,''OwnerId'')))+''.''+QUOTENAME(OBJECT_NAME(fkeyid))+'' DROP CONSTRAINT ''+QUOTENAME(OBJECT_NAME(constid))
     FROM dbo.sysreferences
     WHERE rkeyid=OBJECT_ID(@table)
OPEN CRFK
FETCH CRFK INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN 
    EXEC sp_executesql @sql
    FETCH CRFK INTO @sql
END
CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)','[lookup_instantmessenger_types]'
GO

DROP TABLE [lookup_instantmessenger_types]
GO

-- call_log remove alertd default

ALTER TABLE [customer_product_history] ALTER COLUMN [order_id] int NOT NULL
GO

ALTER TABLE [customer_product_history] ADD [order_item_id] int NULL
GO

ALTER TABLE [customer_product_history] ALTER COLUMN [order_item_id] int NOT NULL
GO

ALTER TABLE [order_entry] ADD [submitted] datetime NULL
GO

ALTER TABLE [order_payment] ADD [order_item_id] int NULL
GO

ALTER TABLE [order_payment] ADD [history_id] int NULL
GO

ALTER TABLE [order_payment] ADD [date_to_process] datetime NULL
GO

ALTER TABLE [order_payment] ADD [creditcard_id] int NULL
GO

ALTER TABLE [order_payment] ADD [bank_id] int NULL
GO

ALTER TABLE [order_payment] ADD [status_id] int NULL
GO

CREATE TABLE [order_payment_status] (
    [payment_status_id] int NOT NULL IDENTITY(1, 1),
    [payment_id] int NOT NULL,
    [status_id] int NULL,
    [entered] datetime NOT NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NOT NULL,
    [modifiedby] int NOT NULL
)
GO

ALTER TABLE [order_payment_status] ADD CONSTRAINT [PK__order_payment_st__3592E0D8] PRIMARY KEY ([payment_status_id])
GO

ALTER TABLE [order_payment_status] ADD CONSTRAINT [DF__order_pay__enter__386F4D83] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [order_payment_status] ADD CONSTRAINT [DF__order_pay__modif__3A5795F5] DEFAULT (getdate()) FOR [modified]
GO

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CRFK CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+QUOTENAME(USER_NAME(OBJECTPROPERTY(fkeyid,''OwnerId'')))+''.''+QUOTENAME(OBJECT_NAME(fkeyid))+'' DROP CONSTRAINT ''+QUOTENAME(OBJECT_NAME(constid))
     FROM dbo.sysreferences
     WHERE rkeyid=OBJECT_ID(@table)
OPEN CRFK
FETCH CRFK INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN 
    EXEC sp_executesql @sql
    FETCH CRFK INTO @sql
END
CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)','[payment_creditcard]'
GO

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CRFK CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(OBJECT_NAME(constid))
     FROM dbo.sysreferences
     WHERE fkeyid=OBJECT_ID(@table)
OPEN CRFK
FETCH CRFK INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN 
    EXEC sp_executesql @sql
    FETCH CRFK INTO @sql
END
CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)','[payment_creditcard]'
GO

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CNSTR CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(o.name)
     FROM dbo.sysobjects o
     WHERE parent_obj=OBJECT_ID(@table)
     AND OBJECTPROPERTY(o.id,''IsDefaultCnst'')=1
OPEN CNSTR
FETCH CNSTR INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN 
    EXEC sp_executesql @sql
    FETCH CNSTR INTO @sql
END
CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)','[payment_creditcard]'
GO

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE @pk_name sysname
SELECT @pk_name=o.name
FROM dbo.sysobjects o
WHERE parent_obj=OBJECT_ID(@table)
AND OBJECTPROPERTY(o.id,''IsPrimaryKey'')=1
IF @pk_name IS NOT NULL
BEGIN
   SET @sql=''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(@pk_name)
   EXEC sp_executesql @sql
END',N'@table nvarchar(261)','[payment_creditcard]'
GO

ALTER TABLE [payment_creditcard] DROP COLUMN [payment_id]
GO

ALTER TABLE [payment_creditcard] ADD [order_id] int NULL
GO

ALTER TABLE [payment_creditcard] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[creditcard_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [payment_creditcard] WITH NOCHECK ADD 
	CONSTRAINT [DF__payment_c__enter__092A4EB5] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__payment_c__modif__0B129727] DEFAULT (getdate()) FOR [modified]
GO

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CRFK CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+QUOTENAME(USER_NAME(OBJECTPROPERTY(fkeyid,''OwnerId'')))+''.''+QUOTENAME(OBJECT_NAME(fkeyid))+'' DROP CONSTRAINT ''+QUOTENAME(OBJECT_NAME(constid))
     FROM dbo.sysreferences
     WHERE rkeyid=OBJECT_ID(@table)
OPEN CRFK
FETCH CRFK INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN 
    EXEC sp_executesql @sql
    FETCH CRFK INTO @sql
END
CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)','[payment_eft]'
GO

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CRFK CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(OBJECT_NAME(constid))
     FROM dbo.sysreferences
     WHERE fkeyid=OBJECT_ID(@table)
OPEN CRFK
FETCH CRFK INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN 
    EXEC sp_executesql @sql
    FETCH CRFK INTO @sql
END
CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)','[payment_eft]'
GO

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CNSTR CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(o.name)
     FROM dbo.sysobjects o
     WHERE parent_obj=OBJECT_ID(@table)
     AND OBJECTPROPERTY(o.id,''IsDefaultCnst'')=1
OPEN CNSTR
FETCH CNSTR INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN 
    EXEC sp_executesql @sql
    FETCH CNSTR INTO @sql
END
CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)','[payment_eft]'
GO

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE @pk_name sysname
SELECT @pk_name=o.name
FROM dbo.sysobjects o
WHERE parent_obj=OBJECT_ID(@table)
AND OBJECTPROPERTY(o.id,''IsPrimaryKey'')=1
IF @pk_name IS NOT NULL
BEGIN
   SET @sql=''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(@pk_name)
   EXEC sp_executesql @sql
END',N'@table nvarchar(261)','[payment_eft]'
GO

ALTER TABLE [payment_eft] DROP COLUMN [payment_id]
GO

ALTER TABLE [payment_eft] ADD [order_id] int NULL
GO

ALTER TABLE [payment_eft] ADD CONSTRAINT [PK__payment_eft__08C03A61] PRIMARY KEY ([bank_id])
GO

ALTER TABLE [payment_eft] ADD CONSTRAINT [DF__payment_e__enter__09B45E9A] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [payment_eft] ADD CONSTRAINT [DF__payment_e__modif__0B9CA70C] DEFAULT (getdate()) FOR [modified]
GO

CREATE TABLE [quote_condition] (
    [map_id] int NOT NULL IDENTITY(1, 1),
    [quote_id] int NOT NULL,
    [condition_id] int NOT NULL
)
GO

ALTER TABLE [quote_condition] ADD CONSTRAINT [PK__quote_condition__03E676AB] PRIMARY KEY ([map_id])
GO

ALTER TABLE [quote_entry] ALTER COLUMN [org_id] int NULL
GO

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CNSTR CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(o.name)
     FROM dbo.syscolumns
     INNER JOIN dbo.sysobjects ON syscolumns.id = sysobjects.id
     INNER JOIN dbo.sysreferences ON syscolumns.id = sysreferences.fkeyid
     INNER JOIN dbo.sysobjects o ON sysreferences.constid = o.id
     WHERE sysobjects.name = ''quote_entry''
       AND syscolumns.name = ''product_id''
       AND o.name LIKE ''FK__quote_ent__produ%''
OPEN CNSTR
FETCH CNSTR INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN 
    EXEC sp_executesql @sql
    FETCH CNSTR INTO @sql
END
CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)','[quote_entry]'
GO

ALTER TABLE [quote_entry] DROP COLUMN [product_id]
GO

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CNSTR CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(o.name)
     FROM dbo.syscolumns
     INNER JOIN dbo.sysobjects ON syscolumns.id = sysobjects.id
     INNER JOIN dbo.sysreferences ON syscolumns.id = sysreferences.fkeyid
     INNER JOIN dbo.sysobjects o ON sysreferences.constid = o.id
     WHERE sysobjects.name = ''quote_entry''
       AND syscolumns.name = ''customer_product_id''
       AND o.name LIKE ''FK__quote_ent__custo%''
OPEN CNSTR
FETCH CNSTR INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN 
    EXEC sp_executesql @sql
    FETCH CNSTR INTO @sql
END
CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)','[quote_entry]'
GO

ALTER TABLE [quote_entry] DROP COLUMN [customer_product_id]
GO

ALTER TABLE [quote_entry] ADD [product_id] int NULL
GO

ALTER TABLE [quote_entry] ADD [customer_product_id] int NULL
GO

ALTER TABLE [quote_entry] ADD [opp_id] int NULL
GO

ALTER TABLE [quote_entry] ADD [version] varchar(255) NOT NULL CONSTRAINT [DF__quote_ent__versi__7D39791C] DEFAULT ('0')
GO

ALTER TABLE [quote_entry] ADD [group_id] int NULL
GO

ALTER TABLE [quote_entry] ALTER COLUMN [group_id] int NOT NULL
GO

ALTER TABLE [quote_entry] ADD [delivery_id] int NULL
GO

ALTER TABLE [quote_entry] ADD [email_address] text NULL
GO

ALTER TABLE [quote_entry] ADD [phone_number] text NULL
GO

ALTER TABLE [quote_entry] ADD [address] text NULL
GO

ALTER TABLE [quote_entry] ADD [fax_number] text NULL
GO

ALTER TABLE [quote_entry] ADD [submit_action] int NULL
GO

ALTER TABLE [quote_entry] ADD [closed] datetime NULL
GO

ALTER TABLE [quote_entry] ADD [show_total] bit NULL
GO

ALTER TABLE [quote_entry] ADD [show_subtotal] bit NULL
GO

ALTER TABLE [quote_entry] ADD [logo_file_id] int NULL
GO

ALTER TABLE [quote_entry] ADD CONSTRAINT [DF__quote_ent__show___0015E5C7] DEFAULT (1) FOR [show_total]
GO

ALTER TABLE [quote_entry] ADD CONSTRAINT [DF__quote_ent__show___010A0A00] DEFAULT (1) FOR [show_subtotal]
GO

ALTER TABLE [quote_product] ADD [estimated_delivery] text NULL
GO

ALTER TABLE [quote_product] ADD [comment] varchar(300) NULL
GO

ALTER TABLE [quote_product_option_boolean] ADD [id] int NULL
GO

ALTER TABLE [quote_product_option_float] ADD [id] int NULL
GO

ALTER TABLE [quote_product_option_integer] ADD [id] int NULL
GO

ALTER TABLE [quote_product_option_text] ADD [id] int NULL
GO

ALTER TABLE [quote_product_option_timestamp] ADD [id] int NULL
GO

CREATE TABLE [quote_remark] (
    [map_id] int NOT NULL IDENTITY(1, 1),
    [quote_id] int NOT NULL,
    [remark_id] int NOT NULL
)
GO

ALTER TABLE [quote_remark] ADD CONSTRAINT [PK__quote_remark__17ED6F58] PRIMARY KEY ([map_id])
GO

CREATE TABLE [quotelog] (
    [id] int NOT NULL IDENTITY(1, 1),
    [quote_id] int NOT NULL,
    [source_id] int NULL,
    [status_id] int NULL,
    [terms_id] int NULL,
    [type_id] int NULL,
    [delivery_id] int NULL,
    [notes] text NULL,
    [grand_total] float NULL,
    [enteredby] int NOT NULL,
    [entered] datetime NOT NULL,
    [modifiedby] int NOT NULL,
    [modified] datetime NOT NULL,
    [issued_date] datetime NULL,
    [submit_action] int NULL,
    [closed] datetime NULL
)
GO

ALTER TABLE [quotelog] ADD CONSTRAINT [PK__quotelog__07B7078F] PRIMARY KEY ([id])
GO

ALTER TABLE [quotelog] ADD CONSTRAINT [DF__quotelog__entere__0F582957] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [quotelog] ADD CONSTRAINT [DF__quotelog__modifi__114071C9] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [ticket] ADD [status_id] int NULL
GO

ALTER TABLE [access] ADD [webdav_password] varchar(80) NULL
GO

ALTER TABLE [access] ADD [hidden] bit NULL
GO

UPDATE access SET hidden = 0
GO

ALTER TABLE [access] ADD CONSTRAINT [DF__access__hidden__0425A276] DEFAULT (0) FOR [hidden]
GO

ALTER TABLE [asset_category] ADD CONSTRAINT [DF__asset_cat__paren__4668671F] DEFAULT (0) FOR [parent_cat_code]
GO

ALTER TABLE [asset_category_draft] ADD CONSTRAINT [DF__asset_cat__paren__4EFDAD20] DEFAULT (0) FOR [parent_cat_code]
GO

CREATE TABLE [lookup_contact_rating] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(50) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

ALTER TABLE [lookup_contact_rating] ADD CONSTRAINT [PK__lookup_contact_r__403A8C7D] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_contact_rating] ADD CONSTRAINT [DF__lookup_co__defau__412EB0B6] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_contact_rating] ADD CONSTRAINT [DF__lookup_co__enabl__4316F928] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_contact_rating] ADD CONSTRAINT [DF__lookup_co__level__4222D4EF] DEFAULT (0) FOR [level]
GO

CREATE TABLE [lookup_contact_source] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(50) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

ALTER TABLE [lookup_contact_source] ADD CONSTRAINT [PK__lookup_contact_s__3B75D760] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_contact_source] ADD CONSTRAINT [DF__lookup_co__defau__3C69FB99] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_contact_source] ADD CONSTRAINT [DF__lookup_co__enabl__3E52440B] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_contact_source] ADD CONSTRAINT [DF__lookup_co__level__3D5E1FD2] DEFAULT (0) FOR [level]
GO

CREATE TABLE [lookup_document_store_permission_category] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL,
    [group_id] int NOT NULL
)
GO

ALTER TABLE [lookup_document_store_permission_category] ADD CONSTRAINT [PK__lookup_document___7E62A77F] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_document_store_permission_category] ADD CONSTRAINT [DF__lookup_do__defau__7F56CBB8] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_document_store_permission_category] ADD CONSTRAINT [DF__lookup_do__enabl__013F142A] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_document_store_permission_category] ADD CONSTRAINT [DF__lookup_do__group__02333863] DEFAULT (0) FOR [group_id]
GO

ALTER TABLE [lookup_document_store_permission_category] ADD CONSTRAINT [DF__lookup_do__level__004AEFF1] DEFAULT (0) FOR [level]
GO

CREATE TABLE [lookup_document_store_role] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(50) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL,
    [group_id] int NOT NULL
)
GO

ALTER TABLE [lookup_document_store_role] ADD CONSTRAINT [PK__lookup_document___041B80D5] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_document_store_role] ADD CONSTRAINT [DF__lookup_do__defau__050FA50E] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_document_store_role] ADD CONSTRAINT [DF__lookup_do__enabl__06F7ED80] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_document_store_role] ADD CONSTRAINT [DF__lookup_do__group__07EC11B9] DEFAULT (0) FOR [group_id]
GO

ALTER TABLE [lookup_document_store_role] ADD CONSTRAINT [DF__lookup_do__level__0603C947] DEFAULT (0) FOR [level]
GO

CREATE TABLE [lookup_im_services] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(50) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

ALTER TABLE [lookup_im_services] ADD CONSTRAINT [PK__lookup_im_servic__36B12243] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_im_services] ADD CONSTRAINT [DF__lookup_im__defau__37A5467C] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_im_services] ADD CONSTRAINT [DF__lookup_im__enabl__398D8EEE] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_im_services] ADD CONSTRAINT [DF__lookup_im__level__38996AB5] DEFAULT (0) FOR [level]
GO

CREATE TABLE [lookup_im_types] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(50) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

ALTER TABLE [lookup_im_types] ADD CONSTRAINT [PK__lookup_im_types__31EC6D26] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_im_types] ADD CONSTRAINT [DF__lookup_im__defau__32E0915F] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_im_types] ADD CONSTRAINT [DF__lookup_im__enabl__34C8D9D1] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_im_types] ADD CONSTRAINT [DF__lookup_im__level__33D4B598] DEFAULT (0) FOR [level]
GO

CREATE TABLE [lookup_news_template] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(255) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL,
    [group_id] int NOT NULL,
    [load_article] bit NULL,
    [load_project_article_list] bit NULL,
    [load_article_linked_list] bit NULL,
    [load_public_projects] bit NULL,
    [load_article_category_list] bit NULL,
    [mapped_jsp] varchar(255) NOT NULL
)
GO

ALTER TABLE [lookup_news_template] ADD CONSTRAINT [PK__lookup_news_temp__0169315C] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__defau__025D5595] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__enabl__04459E07] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__group__0539C240] DEFAULT (0) FOR [group_id]
GO

ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__level__035179CE] DEFAULT (0) FOR [level]
GO

ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__load___062DE679] DEFAULT (0) FOR [load_article]
GO

ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__load___07220AB2] DEFAULT (0) FOR [load_project_article_list]
GO

ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__load___08162EEB] DEFAULT (0) FOR [load_article_linked_list]
GO

ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__load___090A5324] DEFAULT (0) FOR [load_public_projects]
GO

ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__load___09FE775D] DEFAULT (0) FOR [load_article_category_list]
GO

CREATE TABLE [lookup_payment_status] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

ALTER TABLE [lookup_payment_status] ADD CONSTRAINT [PK__lookup_payment_s__246854D6] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_payment_status] ADD CONSTRAINT [DF__lookup_pa__defau__255C790F] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_payment_status] ADD CONSTRAINT [DF__lookup_pa__enabl__2744C181] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_payment_status] ADD CONSTRAINT [DF__lookup_pa__level__26509D48] DEFAULT (0) FOR [level]
GO

CREATE TABLE [lookup_product_manufacturer] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

ALTER TABLE [lookup_product_manufacturer] ADD CONSTRAINT [PK__lookup_product_m__72B0FDB1] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_product_manufacturer] ADD CONSTRAINT [DF__lookup_pr__defau__73A521EA] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_product_manufacturer] ADD CONSTRAINT [DF__lookup_pr__enabl__758D6A5C] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_product_manufacturer] ADD CONSTRAINT [DF__lookup_pr__level__74994623] DEFAULT (0) FOR [level]
GO

CREATE TABLE [lookup_quote_condition] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

ALTER TABLE [lookup_quote_condition] ADD CONSTRAINT [PK__lookup_quote_con__75985754] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_quote_condition] ADD CONSTRAINT [DF__lookup_qu__defau__768C7B8D] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_quote_condition] ADD CONSTRAINT [DF__lookup_qu__enabl__7874C3FF] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_quote_condition] ADD CONSTRAINT [DF__lookup_qu__level__77809FC6] DEFAULT (0) FOR [level]
GO

CREATE TABLE [lookup_quote_delivery] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

ALTER TABLE [lookup_quote_delivery] ADD CONSTRAINT [PK__lookup_quote_del__70D3A237] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_quote_delivery] ADD CONSTRAINT [DF__lookup_qu__defau__71C7C670] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_quote_delivery] ADD CONSTRAINT [DF__lookup_qu__enabl__73B00EE2] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_quote_delivery] ADD CONSTRAINT [DF__lookup_qu__level__72BBEAA9] DEFAULT (0) FOR [level]
GO

CREATE TABLE [lookup_quote_remarks] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

ALTER TABLE [lookup_quote_remarks] ADD CONSTRAINT [PK__lookup_quote_rem__1328BA3B] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_quote_remarks] ADD CONSTRAINT [DF__lookup_qu__defau__141CDE74] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_quote_remarks] ADD CONSTRAINT [DF__lookup_qu__enabl__160526E6] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_quote_remarks] ADD CONSTRAINT [DF__lookup_qu__level__151102AD] DEFAULT (0) FOR [level]
GO

CREATE TABLE [lookup_relationship_types] (
    [type_id] int NOT NULL IDENTITY(1, 1),
    [category_id_maps_from] int NOT NULL,
    [category_id_maps_to] int NOT NULL,
    [reciprocal_name_1] varchar(512) NULL,
    [reciprocal_name_2] varchar(512) NULL,
    [level] int NULL,
    [default_item] bit NULL,
    [enabled] bit NULL
)
GO

ALTER TABLE [lookup_relationship_types] ADD CONSTRAINT [PK__lookup_relations__0C1BC9F9] PRIMARY KEY ([type_id])
GO

ALTER TABLE [lookup_relationship_types] ADD CONSTRAINT [DF__lookup_re__defau__0E04126B] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_relationship_types] ADD CONSTRAINT [DF__lookup_re__enabl__0EF836A4] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_relationship_types] ADD CONSTRAINT [DF__lookup_re__level__0D0FEE32] DEFAULT (0) FOR [level]
GO

UPDATE lookup_task_category SET description = '' WHERE description IS NULL
GO

ALTER TABLE [lookup_task_category] ALTER COLUMN [description] varchar(255) NOT NULL
GO

CREATE TABLE [lookup_textmessage_types] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(50) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

ALTER TABLE [lookup_textmessage_types] ADD CONSTRAINT [PK__lookup_textmessa__44FF419A] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_textmessage_types] ADD CONSTRAINT [DF__lookup_te__defau__45F365D3] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_textmessage_types] ADD CONSTRAINT [DF__lookup_te__enabl__47DBAE45] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_textmessage_types] ADD CONSTRAINT [DF__lookup_te__level__46E78A0C] DEFAULT (0) FOR [level]
GO

CREATE TABLE [lookup_ticket_status] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

ALTER TABLE [lookup_ticket_status] ADD CONSTRAINT [PK__lookup_ticket_st__770B9E7A] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_ticket_status] ADD CONSTRAINT [UQ__lookup_ticket_st__77FFC2B3] UNIQUE ([description])
GO

ALTER TABLE [lookup_ticket_status] ADD CONSTRAINT [DF__lookup_ti__defau__78F3E6EC] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_ticket_status] ADD CONSTRAINT [DF__lookup_ti__enabl__7ADC2F5E] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_ticket_status] ADD CONSTRAINT [DF__lookup_ti__level__79E80B25] DEFAULT (0) FOR [level]
GO

ALTER TABLE [permission_category] ADD [webdav] bit NULL CONSTRAINT [DF__permissio__webda__245D67DE] DEFAULT (0)
GO

UPDATE permission_category SET webdav = 0
GO

ALTER TABLE [permission_category] ALTER COLUMN [webdav] bit NOT NULL
GO

ALTER TABLE [permission_category] ADD [logos] bit NULL CONSTRAINT [DF__permissio__logos__25518C17] DEFAULT (0)
GO

UPDATE permission_category SET logos = 0
GO

ALTER TABLE [permission_category] ALTER COLUMN [logos] bit NOT NULL
GO

ALTER TABLE [permission_category] ADD [constant] int NULL
GO

UPDATE permission_category SET constant = 0
GO

ALTER TABLE [permission_category] ALTER COLUMN [constant] int NOT NULL
GO

CREATE TABLE [quote_group] (
    [group_id] int NOT NULL IDENTITY(1000, 1),
    [unused] char(1) NULL
)
GO

ALTER TABLE [quote_group] ADD CONSTRAINT [PK__quote_group__7A5D0C71] PRIMARY KEY ([group_id])
GO

ALTER TABLE [ticket_category] ADD CONSTRAINT [DF__ticket_ca__paren__0559BDD1] DEFAULT (0) FOR [parent_cat_code]
GO

ALTER TABLE [ticket_category_draft] ADD CONSTRAINT [DF__ticket_ca__paren__0DEF03D2] DEFAULT (0) FOR [parent_cat_code]
GO

CREATE TABLE [document_store] (
    [document_store_id] int NOT NULL IDENTITY(1, 1),
    [template_id] int NULL,
    [title] varchar(100) NOT NULL,
    [shortDescription] varchar(200) NOT NULL,
    [requestedBy] varchar(50) NULL,
    [requestedDept] varchar(50) NULL,
    [requestDate] datetime NULL,
    [requestDate_timezone] varchar(255) NULL,
    [approvalDate] datetime NULL,
    [approvalBy] int NULL,
    [closeDate] datetime NULL,
    [entered] datetime NULL,
    [enteredBy] int NOT NULL,
    [modified] datetime NULL,
    [modifiedBy] int NOT NULL
)
GO

ALTER TABLE [document_store] ADD CONSTRAINT [PK__document_store__1269A02C] PRIMARY KEY ([document_store_id])
GO

ALTER TABLE [document_store] ADD CONSTRAINT [DF__document___enter__15460CD7] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [document_store] ADD CONSTRAINT [DF__document___modif__172E5549] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [document_store] ADD CONSTRAINT [DF__document___reque__135DC465] DEFAULT (getdate()) FOR [requestDate]
GO

CREATE TABLE [lookup_document_store_permission] (
    [code] int NOT NULL IDENTITY(1, 1),
    [category_id] int NULL,
    [permission] varchar(300) NOT NULL,
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL,
    [group_id] int NOT NULL,
    [default_role] int NULL
)
GO

ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [PK__lookup_document___09D45A2B] PRIMARY KEY ([code])
GO

ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [UQ__lookup_document___0AC87E64] UNIQUE ([permission])
GO

ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [DF__lookup_do__defau__0CB0C6D6] DEFAULT (0) FOR [default_item]
GO

ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [DF__lookup_do__enabl__0E990F48] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [DF__lookup_do__group__0F8D3381] DEFAULT (0) FOR [group_id]
GO

ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [DF__lookup_do__level__0DA4EB0F] DEFAULT (0) FOR [level]
GO

ALTER TABLE [product_option_configurator] ADD [configurator_name] varchar(300) NULL
GO

ALTER TABLE [product_option_configurator] ALTER COLUMN [configurator_name] varchar(300) NOT NULL
GO

EXEC sp_rename '[projects].[requestdate_timezone]','tmp_requestDate_timezone','COLUMN'
GO
EXEC sp_rename '[projects].[tmp_requestDate_timezone]','requestDate_timezone','COLUMN'
GO

ALTER TABLE [projects] ADD [portal_default] bit NULL CONSTRAINT [DF__projects__portal__1E05700A] DEFAULT (0)
GO

UPDATE projects SET portal_default = 0
GO

ALTER TABLE [projects] ALTER COLUMN [portal_default] bit NOT NULL
GO

ALTER TABLE [projects] ADD [portal_header] varchar(255) NULL
GO

ALTER TABLE [projects] ADD [portal_format] varchar(255) NULL
GO

ALTER TABLE [projects] ADD [portal_key] varchar(255) NULL
GO

ALTER TABLE [projects] ADD [portal_build_news_body] bit NULL CONSTRAINT [DF__projects__portal__1EF99443] DEFAULT (0)
GO

UPDATE projects SET portal_build_news_body = 0
GO

ALTER TABLE [projects] ALTER COLUMN [portal_build_news_body] bit NOT NULL
GO

ALTER TABLE [projects] ADD [portal_news_menu] bit NULL CONSTRAINT [DF__projects__portal__1FEDB87C] DEFAULT (0)
GO

UPDATE projects SET portal_news_menu = 0
GO

ALTER TABLE [projects] ALTER COLUMN [portal_news_menu] bit NOT NULL
GO

ALTER TABLE [projects] ADD [description] text NULL
GO

ALTER TABLE [projects] ADD [allows_user_observers] bit NULL CONSTRAINT [DF__projects__allows__20E1DCB5] DEFAULT (0)
GO

UPDATE projects SET allows_user_observers = 0
GO

ALTER TABLE [projects] ALTER COLUMN [allows_user_observers] bit NOT NULL
GO

ALTER TABLE [projects] ADD [level] int NULL CONSTRAINT [DF__projects__level__21D600EE] DEFAULT (10)
GO

UPDATE projects SET level = 10
GO

ALTER TABLE [projects] ALTER COLUMN [level] int NOT NULL
GO

ALTER TABLE [projects] ADD [portal_page_type] int NULL
GO

ALTER TABLE [projects] ADD [calendar_enabled] bit NULL CONSTRAINT [DF__projects__calend__22CA2527] DEFAULT (1)
GO

UPDATE projects SET calendar_enabled = 0
GO

ALTER TABLE [projects] ALTER COLUMN [calendar_enabled] bit NOT NULL
GO

ALTER TABLE [projects] ADD [calendar_label] varchar(50) NULL
GO

ALTER TABLE [projects] ADD [accounts_enabled] bit NULL CONSTRAINT [DF__projects__accoun__23BE4960] DEFAULT (1)
GO

UPDATE projects SET accounts_enabled = 1
GO

ALTER TABLE [projects] ALTER COLUMN [accounts_enabled] bit NOT NULL
GO

ALTER TABLE [projects] ADD [accounts_label] varchar(50) NULL
GO

CREATE TABLE [relationship] (
    [relationship_id] int NOT NULL IDENTITY(1, 1),
    [type_id] int NULL,
    [object_id_maps_from] int NOT NULL,
    [category_id_maps_from] int NOT NULL,
    [object_id_maps_to] int NOT NULL,
    [category_id_maps_to] int NOT NULL,
    [entered] datetime NOT NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NOT NULL,
    [modifiedby] int NOT NULL
)
GO

ALTER TABLE [relationship] ADD CONSTRAINT [PK__relationship__10E07F16] PRIMARY KEY ([relationship_id])
GO

ALTER TABLE [relationship] ADD CONSTRAINT [DF__relations__enter__12C8C788] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [relationship] ADD CONSTRAINT [DF__relations__modif__13BCEBC1] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [task] ALTER COLUMN [description] varchar(255) NULL
GO

CREATE TABLE [webdav] (
    [id] int NOT NULL IDENTITY(1, 1),
    [category_id] int NOT NULL,
    [class_name] varchar(300) NOT NULL,
    [entered] datetime NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NULL,
    [modifiedby] int NOT NULL
)
GO

ALTER TABLE [webdav] ADD CONSTRAINT [PK__webdav__2F9A1060] PRIMARY KEY ([id])
GO

ALTER TABLE [webdav] ADD CONSTRAINT [DF__webdav__entered__318258D2] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [webdav] ADD CONSTRAINT [DF__webdav__modified__336AA144] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [contact] ADD [information_update_date] datetime NULL
GO

ALTER TABLE [contact] ADD [lead] bit NULL
GO

UPDATE contact SET lead = 0
GO

ALTER TABLE [contact] ADD [lead_status] int NULL
GO

ALTER TABLE [contact] ADD [source] int NULL
GO

ALTER TABLE [contact] ADD [rating] int NULL
GO

ALTER TABLE [contact] ADD [comments] varchar(255) NULL
GO

ALTER TABLE [contact] ADD [conversion_date] datetime NULL
GO

ALTER TABLE [contact] ADD CONSTRAINT [DF__contact__informa__04E4BC85] DEFAULT (getdate()) FOR [information_update_date]
GO

ALTER TABLE [contact] ADD CONSTRAINT [DF__contact__lead__05D8E0BE] DEFAULT (0) FOR [lead]
GO

CREATE TABLE [document_store_department_member] (
    [document_store_id] int NOT NULL,
    [item_id] int NOT NULL,
    [userlevel] int NOT NULL,
    [status] int NULL,
    [last_accessed] datetime NULL,
    [entered] datetime NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NULL,
    [modifiedby] int NOT NULL
)
GO

ALTER TABLE [document_store_department_member] ADD CONSTRAINT [DF__document___enter__30EE274C] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [document_store_department_member] ADD CONSTRAINT [DF__document___modif__32D66FBE] DEFAULT (getdate()) FOR [modified]
GO

CREATE TABLE [document_store_permissions] (
    [id] int NOT NULL IDENTITY(1, 1),
    [document_store_id] int NOT NULL,
    [permission_id] int NOT NULL,
    [userlevel] int NOT NULL
)
GO

ALTER TABLE [document_store_permissions] ADD CONSTRAINT [PK__document_store_p__1A0AC1F4] PRIMARY KEY ([id])
GO

CREATE TABLE [document_store_role_member] (
    [document_store_id] int NOT NULL,
    [item_id] int NOT NULL,
    [userlevel] int NOT NULL,
    [status] int NULL,
    [last_accessed] datetime NULL,
    [entered] datetime NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NULL,
    [modifiedby] int NOT NULL
)
GO

ALTER TABLE [document_store_role_member] ADD CONSTRAINT [DF__document___enter__294D0584] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [document_store_role_member] ADD CONSTRAINT [DF__document___modif__2B354DF6] DEFAULT (getdate()) FOR [modified]
GO

CREATE TABLE [document_store_user_member] (
    [document_store_id] int NOT NULL,
    [item_id] int NOT NULL,
    [userlevel] int NOT NULL,
    [status] int NULL,
    [last_accessed] datetime NULL,
    [entered] datetime NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NULL,
    [modifiedby] int NOT NULL
)
GO

ALTER TABLE [document_store_user_member] ADD CONSTRAINT [DF__document___enter__21ABE3BC] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [document_store_user_member] ADD CONSTRAINT [DF__document___modif__23942C2E] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [organization_address] ADD [primary_address] bit NULL CONSTRAINT [DF__organizat__prima__4E53A1AA] DEFAULT (0)
GO

UPDATE organization_address SET primary_address = 0
GO

ALTER TABLE [organization_address] ALTER COLUMN [primary_address] bit NOT NULL
GO

ALTER TABLE [organization_emailaddress] ADD [primary_email] bit NULL CONSTRAINT [DF__organizat__prima__56E8E7AB] DEFAULT (0)
GO

UPDATE organization_emailaddress SET primary_email = 0
GO

ALTER TABLE [organization_emailaddress] ALTER COLUMN [primary_email] bit NOT NULL
GO

ALTER TABLE [organization_phone] ADD [primary_number] bit NULL CONSTRAINT [DF__organizat__prima__5F7E2DAC] DEFAULT (0)
GO

UPDATE organization_phone SET primary_number = 0
GO

ALTER TABLE [organization_phone] ALTER COLUMN [primary_number] bit NOT NULL
GO

ALTER TABLE [product_option] DROP CONSTRAINT [DF__product_o__enabl__7993056A]
GO

ALTER TABLE [product_option] ADD [option_name] varchar(300) NULL
GO

ALTER TABLE [product_option] ADD [has_range] bit NULL
GO

ALTER TABLE [product_option] ADD [has_multiplier] bit NULL
GO

ALTER TABLE [product_option] ADD CONSTRAINT [DF__product_o__enabl__6339AFF7] DEFAULT (0) FOR [enabled]
GO

ALTER TABLE [product_option] ADD CONSTRAINT [DF__product_o__has_m__6521F869] DEFAULT (0) FOR [has_multiplier]
GO

ALTER TABLE [product_option] ADD CONSTRAINT [DF__product_o__has_r__642DD430] DEFAULT (0) FOR [has_range]
GO

CREATE TABLE [project_accounts] (
    [id] int NOT NULL IDENTITY(1, 1),
    [project_id] int NOT NULL,
    [org_id] int NOT NULL,
    [entered] datetime NULL
)
GO

ALTER TABLE [project_accounts] ADD CONSTRAINT [PK__project_accounts__4D7F7902] PRIMARY KEY ([id])
GO

ALTER TABLE [project_accounts] ADD CONSTRAINT [DF__project_a__enter__505BE5AD] DEFAULT (getdate()) FOR [entered]
GO

CREATE INDEX [proj_acct_org_idx] ON [project_accounts] ([org_id])
GO

CREATE INDEX [proj_acct_project_idx] ON [project_accounts] ([project_id])
GO

ALTER TABLE [project_files] ADD [default_file] bit NULL
GO

UPDATE project_files SET default_file = 0
GO

ALTER TABLE [project_files] ADD CONSTRAINT [DF__project_f__defau__7B7B4DDC] DEFAULT (0) FOR [default_file]
GO

ALTER TABLE [project_issues_categories] ADD [allow_files] bit NULL CONSTRAINT [DF__project_i__allow__573DED66] DEFAULT (0)
GO

UPDATE project_issues_categories SET allow_files = 0
GO

ALTER TABLE [project_issues_categories] ALTER COLUMN [allow_files] bit NOT NULL
GO

CREATE TABLE [project_news_category] (
    [category_id] int NOT NULL IDENTITY(1, 1),
    [project_id] int NOT NULL,
    [category_name] varchar(255) NULL,
    [entered] datetime NULL,
    [level] int NOT NULL,
    [enabled] bit NULL
)
GO

ALTER TABLE [project_news_category] ADD CONSTRAINT [PK__project_news_cat__1AF3F935] PRIMARY KEY ([category_id])
GO

ALTER TABLE [project_news_category] ADD CONSTRAINT [DF__project_n__enabl__1EC48A19] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [project_news_category] ADD CONSTRAINT [DF__project_n__enter__1CDC41A7] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [project_news_category] ADD CONSTRAINT [DF__project_n__level__1DD065E0] DEFAULT (0) FOR [level]
GO

ALTER TABLE [active_survey_responses] ADD [address_updated] int NULL
GO

CREATE TABLE [contact_imaddress] (
    [address_id] int NOT NULL IDENTITY(1, 1),
    [contact_id] int NULL,
    [imaddress_type] int NULL,
    [imaddress_service] int NULL,
    [imaddress] varchar(256) NULL,
    [entered] datetime NOT NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NOT NULL,
    [modifiedby] int NOT NULL,
    [primary_im] bit NOT NULL
)
GO

ALTER TABLE [contact_imaddress] ADD CONSTRAINT [PK__contact_imaddres__7B264821] PRIMARY KEY ([address_id])
GO

ALTER TABLE [contact_imaddress] ADD CONSTRAINT [DF__contact_i__enter__7EF6D905] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [contact_imaddress] ADD CONSTRAINT [DF__contact_i__modif__00DF2177] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [contact_imaddress] ADD CONSTRAINT [DF__contact_i__prima__02C769E9] DEFAULT (0) FOR [primary_im]
GO

CREATE TABLE [contact_lead_read_map] (
    [map_id] int NOT NULL IDENTITY(1, 1),
    [user_id] int NOT NULL,
    [contact_id] int NOT NULL
)
GO

ALTER TABLE [contact_lead_read_map] ADD CONSTRAINT [PK__contact_lead_rea__0D7A0286] PRIMARY KEY ([map_id])
GO

CREATE TABLE [contact_lead_skipped_map] (
    [map_id] int NOT NULL IDENTITY(1, 1),
    [user_id] int NOT NULL,
    [contact_id] int NOT NULL
)
GO

ALTER TABLE [contact_lead_skipped_map] ADD CONSTRAINT [PK__contact_lead_ski__09A971A2] PRIMARY KEY ([map_id])
GO

CREATE TABLE [contact_textmessageaddress] (
    [address_id] int NOT NULL IDENTITY(1, 1),
    [contact_id] int NULL,
    [textmessageaddress_type] int NULL,
    [textmessageaddress] varchar(256) NULL,
    [entered] datetime NOT NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NOT NULL,
    [modifiedby] int NOT NULL,
    [primary_textmessage_address] bit NOT NULL
)
GO

ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [PK__contact_textmess__04AFB25B] PRIMARY KEY ([address_id])
GO

ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [DF__contact_t__enter__078C1F06] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [DF__contact_t__modif__09746778] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [DF__contact_t__prima__0B5CAFEA] DEFAULT (0) FOR [primary_textmessage_address]
GO

ALTER TABLE [product_catalog] ADD [manufacturer_id] int NULL
GO

ALTER TABLE [product_option_boolean] ADD [id] int NULL
GO

ALTER TABLE [product_option_float] ADD [id] int NULL
GO

ALTER TABLE [product_option_integer] ADD [id] int NULL
GO

ALTER TABLE [product_option_text] ADD [id] int NULL
GO

ALTER TABLE [product_option_timestamp] ADD [id] int NULL
GO

ALTER TABLE [product_option_values] ADD [enabled] bit NULL
GO

ALTER TABLE [product_option_values] ADD [value] float NULL
GO

ALTER TABLE [product_option_values] ADD [multiplier] float NULL
GO

ALTER TABLE [product_option_values] ADD [range_min] int NULL
GO

ALTER TABLE [product_option_values] ADD [range_max] int NULL
GO

ALTER TABLE [product_option_values] ADD [cost_currency] int NULL
GO

ALTER TABLE [product_option_values] ADD [cost_amount] float NULL CONSTRAINT [DF__product_o__cost___75586032] DEFAULT (0)
GO

ALTER TABLE [product_option_values] ALTER COLUMN [cost_amount] float NOT NULL
GO

ALTER TABLE [product_option_values] ADD CONSTRAINT [DF__product_o__enabl__6F9F86DC] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [product_option_values] ADD CONSTRAINT [DF__product_o__multi__7187CF4E] DEFAULT (1) FOR [multiplier]
GO

ALTER TABLE [product_option_values] ADD CONSTRAINT [DF__product_o__range__727BF387] DEFAULT (1) FOR [range_min]
GO

ALTER TABLE [product_option_values] ADD CONSTRAINT [DF__product_o__range__737017C0] DEFAULT (1) FOR [range_max]
GO

ALTER TABLE [product_option_values] ADD CONSTRAINT [DF__product_o__value__7093AB15] DEFAULT (0) FOR [value]
GO

ALTER TABLE [project_news] ALTER COLUMN [intro] text NULL
GO

ALTER TABLE [project_news] ADD [classification_id] int NULL
GO

UPDATE project_news SET classification_id = 20
GO

ALTER TABLE [project_news] ALTER COLUMN [classification_id] int NOT NULL
GO

ALTER TABLE [project_news] ADD [template_id] int NULL
GO

ALTER TABLE [product_catalog_pricing] ADD [enabled] bit NULL
GO

UPDATE product_catalog_pricing SET enabled = 1
GO

ALTER TABLE [product_catalog_pricing] ADD [cost_currency] int NULL
GO

ALTER TABLE [product_catalog_pricing] ADD [cost_amount] float NOT NULL CONSTRAINT [DF__product_c__cost___338A9CD5] DEFAULT (0)
GO

UPDATE product_catalog_pricing SET cost_amount = 0
GO

ALTER TABLE [product_catalog_pricing] ADD CONSTRAINT [DF__product_c__enabl__31A25463] DEFAULT (0) FOR [enabled]
GO

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CRFK CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+QUOTENAME(USER_NAME(OBJECTPROPERTY(fkeyid,''OwnerId'')))+''.''+QUOTENAME(OBJECT_NAME(fkeyid))+'' DROP CONSTRAINT ''+QUOTENAME(OBJECT_NAME(constid))
     FROM dbo.sysreferences
     WHERE rkeyid=OBJECT_ID(@table)
OPEN CRFK
FETCH CRFK INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN 
    EXEC sp_executesql @sql
    FETCH CRFK INTO @sql
END
CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)','[product_option_map]'
GO

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CRFK CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(OBJECT_NAME(constid))
     FROM dbo.sysreferences
     WHERE fkeyid=OBJECT_ID(@table)
OPEN CRFK
FETCH CRFK INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN 
    EXEC sp_executesql @sql
    FETCH CRFK INTO @sql
END
CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)','[product_option_map]'
GO

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE @pk_name sysname
SELECT @pk_name=o.name
FROM dbo.sysobjects o
WHERE parent_obj=OBJECT_ID(@table)
AND OBJECTPROPERTY(o.id,''IsPrimaryKey'')=1
IF @pk_name IS NOT NULL
BEGIN
   SET @sql=''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(@pk_name)
   EXEC sp_executesql @sql
END',N'@table nvarchar(261)','[product_option_map]'
GO

ALTER TABLE [product_option_map] DROP COLUMN [value_id]
GO

ALTER TABLE [product_option_map] ADD CONSTRAINT [PK__product_option_m__7740A8A4] PRIMARY KEY ([product_option_id])
GO

CREATE INDEX [proj_assign_req_id_idx] ON [project_assignments] ([requirement_id]) ON [PRIMARY]
GO

ALTER TABLE [project_issue_replies] ALTER COLUMN [subject] varchar(255) NOT NULL
GO

CREATE TABLE [taskcategorylink_news] (
    [news_id] int NOT NULL,
    [category_id] int NOT NULL
)
GO

ALTER TABLE [project_assignments_status] ADD [percent_complete] int NULL
GO

ALTER TABLE [project_assignments_status] ADD [project_status_id] int NULL
GO

ALTER TABLE [project_assignments_status] ADD [user_assign_id] int NULL
GO

ALTER TABLE [contact_address] WITH NOCHECK ADD
	CONSTRAINT [DF__contact_a__prima__43D61337] DEFAULT (0) FOR [primary_address]
GO

ALTER TABLE [contact_phone] WITH NOCHECK ADD
	CONSTRAINT [DF__contact_p__prima__55009F39] DEFAULT (0) FOR [primary_number]
GO

IF OBJECT_ID('[customer_product_history]') IS NOT NULL AND OBJECT_ID('[order_product]') IS NOT NULL AND OBJECT_ID('[FK__customer___order__22800C64]') IS NULL
ALTER TABLE [customer_product_history] ADD CONSTRAINT [FK__customer___order__22800C64] FOREIGN KEY ([order_item_id]) REFERENCES [order_product] ([item_id])
GO

IF OBJECT_ID('[order_payment]') IS NOT NULL AND OBJECT_ID('[payment_eft]') IS NOT NULL AND OBJECT_ID('[FK__order_pay__bank___32B6742D]') IS NULL
ALTER TABLE [order_payment] ADD CONSTRAINT [FK__order_pay__bank___32B6742D] FOREIGN KEY ([bank_id]) REFERENCES [payment_eft] ([bank_id])
GO

IF OBJECT_ID('[order_payment]') IS NOT NULL AND OBJECT_ID('[payment_creditcard]') IS NOT NULL AND OBJECT_ID('[FK__order_pay__credi__31C24FF4]') IS NULL
ALTER TABLE [order_payment] ADD CONSTRAINT [FK__order_pay__credi__31C24FF4] FOREIGN KEY ([creditcard_id]) REFERENCES [payment_creditcard] ([creditcard_id])
GO

IF OBJECT_ID('[order_payment]') IS NOT NULL AND OBJECT_ID('[customer_product_history]') IS NOT NULL AND OBJECT_ID('[FK__order_pay__histo__2C09769E]') IS NULL
ALTER TABLE [order_payment] ADD CONSTRAINT [FK__order_pay__histo__2C09769E] FOREIGN KEY ([history_id]) REFERENCES [customer_product_history] ([history_id])
GO

IF OBJECT_ID('[order_payment]') IS NOT NULL AND OBJECT_ID('[order_product]') IS NOT NULL AND OBJECT_ID('[FK__order_pay__order__2B155265]') IS NULL
ALTER TABLE [order_payment] ADD CONSTRAINT [FK__order_pay__order__2B155265] FOREIGN KEY ([order_item_id]) REFERENCES [order_product] ([item_id])
GO

IF OBJECT_ID('[order_payment]') IS NOT NULL AND OBJECT_ID('[lookup_payment_status]') IS NOT NULL AND OBJECT_ID('[FK__order_pay__statu__33AA9866]') IS NULL
ALTER TABLE [order_payment] ADD CONSTRAINT [FK__order_pay__statu__33AA9866] FOREIGN KEY ([status_id]) REFERENCES [lookup_payment_status] ([code])
GO

IF OBJECT_ID('[order_payment_status]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__order_pay__enter__396371BC]') IS NULL
ALTER TABLE [order_payment_status] ADD CONSTRAINT [FK__order_pay__enter__396371BC] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[order_payment_status]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__order_pay__modif__3B4BBA2E]') IS NULL
ALTER TABLE [order_payment_status] ADD CONSTRAINT [FK__order_pay__modif__3B4BBA2E] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[order_payment_status]') IS NOT NULL AND OBJECT_ID('[order_payment]') IS NOT NULL AND OBJECT_ID('[FK__order_pay__payme__36870511]') IS NULL
ALTER TABLE [order_payment_status] ADD CONSTRAINT [FK__order_pay__payme__36870511] FOREIGN KEY ([payment_id]) REFERENCES [order_payment] ([payment_id])
GO

IF OBJECT_ID('[order_payment_status]') IS NOT NULL AND OBJECT_ID('[lookup_payment_status]') IS NOT NULL AND OBJECT_ID('[FK__order_pay__statu__377B294A]') IS NULL
ALTER TABLE [order_payment_status] ADD CONSTRAINT [FK__order_pay__statu__377B294A] FOREIGN KEY ([status_id]) REFERENCES [lookup_payment_status] ([code])
GO

IF OBJECT_ID('[payment_creditcard]') IS NOT NULL AND OBJECT_ID('[lookup_creditcard_types]') IS NOT NULL AND OBJECT_ID('[FK__payment_c__card___02133CD2]') IS NULL
ALTER TABLE [payment_creditcard] ADD CONSTRAINT [FK__payment_c__card___02133CD2] FOREIGN KEY ([card_type]) REFERENCES [lookup_creditcard_types] ([code])
GO

IF OBJECT_ID('[payment_creditcard]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__payment_c__enter__03FB8544]') IS NULL
ALTER TABLE [payment_creditcard] ADD CONSTRAINT [FK__payment_c__enter__03FB8544] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[payment_creditcard]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__payment_c__modif__05E3CDB6]') IS NULL
ALTER TABLE [payment_creditcard] ADD CONSTRAINT [FK__payment_c__modif__05E3CDB6] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[payment_creditcard]') IS NOT NULL AND OBJECT_ID('[order_entry]') IS NOT NULL AND OBJECT_ID('[FK__payment_c__order__06D7F1EF]') IS NULL
ALTER TABLE [payment_creditcard] ADD CONSTRAINT [FK__payment_c__order__06D7F1EF] FOREIGN KEY ([order_id]) REFERENCES [order_entry] ([order_id])
GO

IF OBJECT_ID('[payment_eft]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__payment_e__enter__0AA882D3]') IS NULL
ALTER TABLE [payment_eft] ADD CONSTRAINT [FK__payment_e__enter__0AA882D3] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[payment_eft]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__payment_e__modif__0C90CB45]') IS NULL
ALTER TABLE [payment_eft] ADD CONSTRAINT [FK__payment_e__modif__0C90CB45] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[payment_eft]') IS NOT NULL AND OBJECT_ID('[order_entry]') IS NOT NULL AND OBJECT_ID('[FK__payment_e__order__0D84EF7E]') IS NULL
ALTER TABLE [payment_eft] ADD CONSTRAINT [FK__payment_e__order__0D84EF7E] FOREIGN KEY ([order_id]) REFERENCES [order_entry] ([order_id])
GO

IF OBJECT_ID('[quote_condition]') IS NOT NULL AND OBJECT_ID('[lookup_quote_condition]') IS NOT NULL AND OBJECT_ID('[FK__quote_con__condi__05CEBF1D]') IS NULL
ALTER TABLE [quote_condition] ADD CONSTRAINT [FK__quote_con__condi__05CEBF1D] FOREIGN KEY ([condition_id]) REFERENCES [lookup_quote_condition] ([code])
GO

IF OBJECT_ID('[quote_condition]') IS NOT NULL AND OBJECT_ID('[quote_entry]') IS NOT NULL AND OBJECT_ID('[FK__quote_con__quote__04DA9AE4]') IS NULL
ALTER TABLE [quote_condition] ADD CONSTRAINT [FK__quote_con__quote__04DA9AE4] FOREIGN KEY ([quote_id]) REFERENCES [quote_entry] ([quote_id])
GO

IF OBJECT_ID('[quote_entry]') IS NOT NULL AND OBJECT_ID('[lookup_quote_delivery]') IS NOT NULL AND OBJECT_ID('[FK__quote_ent__deliv__7F21C18E]') IS NULL
ALTER TABLE [quote_entry] ADD CONSTRAINT [FK__quote_ent__deliv__7F21C18E] FOREIGN KEY ([delivery_id]) REFERENCES [lookup_quote_delivery] ([code])
GO

IF OBJECT_ID('[quote_entry]') IS NOT NULL AND OBJECT_ID('[quote_group]') IS NOT NULL AND OBJECT_ID('[FK__quote_ent__group__7E2D9D55]') IS NULL
ALTER TABLE [quote_entry] ADD CONSTRAINT [FK__quote_ent__group__7E2D9D55] FOREIGN KEY ([group_id]) REFERENCES [quote_group] ([group_id])
GO

IF OBJECT_ID('[quote_entry]') IS NOT NULL AND OBJECT_ID('[project_files]') IS NOT NULL AND OBJECT_ID('[FK__quote_ent__logo___01FE2E39]') IS NULL
ALTER TABLE [quote_entry] ADD CONSTRAINT [FK__quote_ent__logo___01FE2E39] FOREIGN KEY ([logo_file_id]) REFERENCES [project_files] ([item_id])
GO

IF OBJECT_ID('[quote_entry]') IS NOT NULL AND OBJECT_ID('[opportunity_header]') IS NOT NULL AND OBJECT_ID('[FK__quote_ent__opp_i__7306036C]') IS NULL
ALTER TABLE [quote_entry] ADD CONSTRAINT [FK__quote_ent__opp_i__7306036C] FOREIGN KEY ([opp_id]) REFERENCES [opportunity_header] ([opp_id])
GO

IF OBJECT_ID('[quote_remark]') IS NOT NULL AND OBJECT_ID('[quote_entry]') IS NOT NULL AND OBJECT_ID('[FK__quote_rem__quote__18E19391]') IS NULL
ALTER TABLE [quote_remark] ADD CONSTRAINT [FK__quote_rem__quote__18E19391] FOREIGN KEY ([quote_id]) REFERENCES [quote_entry] ([quote_id])
GO

IF OBJECT_ID('[quote_remark]') IS NOT NULL AND OBJECT_ID('[lookup_quote_remarks]') IS NOT NULL AND OBJECT_ID('[FK__quote_rem__remar__19D5B7CA]') IS NULL
ALTER TABLE [quote_remark] ADD CONSTRAINT [FK__quote_rem__remar__19D5B7CA] FOREIGN KEY ([remark_id]) REFERENCES [lookup_quote_remarks] ([code])
GO

IF OBJECT_ID('[quotelog]') IS NOT NULL AND OBJECT_ID('[lookup_quote_delivery]') IS NOT NULL AND OBJECT_ID('[FK__quotelog__delive__0D6FE0E5]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__delive__0D6FE0E5] FOREIGN KEY ([delivery_id]) REFERENCES [lookup_quote_delivery] ([code])
GO

IF OBJECT_ID('[quotelog]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__quotelog__entere__0E64051E]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__entere__0E64051E] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[quotelog]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__quotelog__modifi__104C4D90]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__modifi__104C4D90] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[quotelog]') IS NOT NULL AND OBJECT_ID('[quote_entry]') IS NOT NULL AND OBJECT_ID('[FK__quotelog__quote___08AB2BC8]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__quote___08AB2BC8] FOREIGN KEY ([quote_id]) REFERENCES [quote_entry] ([quote_id])
GO

IF OBJECT_ID('[quotelog]') IS NOT NULL AND OBJECT_ID('[lookup_quote_source]') IS NOT NULL AND OBJECT_ID('[FK__quotelog__source__099F5001]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__source__099F5001] FOREIGN KEY ([source_id]) REFERENCES [lookup_quote_source] ([code])
GO

IF OBJECT_ID('[quotelog]') IS NOT NULL AND OBJECT_ID('[lookup_quote_status]') IS NOT NULL AND OBJECT_ID('[FK__quotelog__status__0A93743A]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__status__0A93743A] FOREIGN KEY ([status_id]) REFERENCES [lookup_quote_status] ([code])
GO

IF OBJECT_ID('[quotelog]') IS NOT NULL AND OBJECT_ID('[lookup_quote_terms]') IS NOT NULL AND OBJECT_ID('[FK__quotelog__terms___0B879873]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__terms___0B879873] FOREIGN KEY ([terms_id]) REFERENCES [lookup_quote_terms] ([code])
GO

IF OBJECT_ID('[quotelog]') IS NOT NULL AND OBJECT_ID('[lookup_quote_type]') IS NOT NULL AND OBJECT_ID('[FK__quotelog__type_i__0C7BBCAC]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__type_i__0C7BBCAC] FOREIGN KEY ([type_id]) REFERENCES [lookup_quote_type] ([code])
GO

IF OBJECT_ID('[ticket]') IS NOT NULL AND OBJECT_ID('[lookup_ticket_status]') IS NOT NULL AND OBJECT_ID('[FK__ticket__status_i__6EEB59C5]') IS NULL
ALTER TABLE [ticket] ADD CONSTRAINT [FK__ticket__status_i__6EEB59C5] FOREIGN KEY ([status_id]) REFERENCES [lookup_ticket_status] ([code])
GO

IF OBJECT_ID('[document_store]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__document___appro__1451E89E]') IS NULL
ALTER TABLE [document_store] ADD CONSTRAINT [FK__document___appro__1451E89E] FOREIGN KEY ([approvalBy]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[document_store]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__document___enter__163A3110]') IS NULL
ALTER TABLE [document_store] ADD CONSTRAINT [FK__document___enter__163A3110] FOREIGN KEY ([enteredBy]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[document_store]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__document___modif__18227982]') IS NULL
ALTER TABLE [document_store] ADD CONSTRAINT [FK__document___modif__18227982] FOREIGN KEY ([modifiedBy]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[lookup_document_store_permission]') IS NOT NULL AND OBJECT_ID('[lookup_document_store_permission_category]') IS NOT NULL AND OBJECT_ID('[FK__lookup_do__categ__0BBCA29D]') IS NULL
ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [FK__lookup_do__categ__0BBCA29D] FOREIGN KEY ([category_id]) REFERENCES [lookup_document_store_permission_category] ([code])
GO

IF OBJECT_ID('[lookup_document_store_permission]') IS NOT NULL AND OBJECT_ID('[lookup_document_store_role]') IS NOT NULL AND OBJECT_ID('[FK__lookup_do__defau__108157BA]') IS NULL
ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [FK__lookup_do__defau__108157BA] FOREIGN KEY ([default_role]) REFERENCES [lookup_document_store_role] ([code])
GO

IF OBJECT_ID('[relationship]') IS NOT NULL AND OBJECT_ID('[lookup_relationship_types]') IS NOT NULL AND OBJECT_ID('[FK__relations__type___11D4A34F]') IS NULL
ALTER TABLE [relationship] ADD CONSTRAINT [FK__relations__type___11D4A34F] FOREIGN KEY ([type_id]) REFERENCES [lookup_relationship_types] ([type_id])
GO

IF OBJECT_ID('[webdav]') IS NOT NULL AND OBJECT_ID('[permission_category]') IS NOT NULL AND OBJECT_ID('[FK__webdav__category__308E3499]') IS NULL
ALTER TABLE [webdav] ADD CONSTRAINT [FK__webdav__category__308E3499] FOREIGN KEY ([category_id]) REFERENCES [permission_category] ([category_id])
GO

IF OBJECT_ID('[webdav]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__webdav__enteredb__32767D0B]') IS NULL
ALTER TABLE [webdav] ADD CONSTRAINT [FK__webdav__enteredb__32767D0B] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[webdav]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__webdav__modified__345EC57D]') IS NULL
ALTER TABLE [webdav] ADD CONSTRAINT [FK__webdav__modified__345EC57D] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[contact]') IS NOT NULL AND OBJECT_ID('[lookup_contact_rating]') IS NOT NULL AND OBJECT_ID('[FK__contact__rating__07C12930]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__rating__07C12930] FOREIGN KEY ([rating]) REFERENCES [lookup_contact_rating] ([code])
GO

IF OBJECT_ID('[contact]') IS NOT NULL AND OBJECT_ID('[lookup_contact_source]') IS NOT NULL AND OBJECT_ID('[FK__contact__source__06CD04F7]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__source__06CD04F7] FOREIGN KEY ([source]) REFERENCES [lookup_contact_source] ([code])
GO

IF OBJECT_ID('[document_store_department_member]') IS NOT NULL AND OBJECT_ID('[document_store]') IS NOT NULL AND OBJECT_ID('[FK__document___docum__2E11BAA1]') IS NULL
ALTER TABLE [document_store_department_member] ADD CONSTRAINT [FK__document___docum__2E11BAA1] FOREIGN KEY ([document_store_id]) REFERENCES [document_store] ([document_store_id])
GO

IF OBJECT_ID('[document_store_department_member]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__document___enter__31E24B85]') IS NULL
ALTER TABLE [document_store_department_member] ADD CONSTRAINT [FK__document___enter__31E24B85] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[document_store_department_member]') IS NOT NULL AND OBJECT_ID('[lookup_department]') IS NOT NULL AND OBJECT_ID('[FK__document___item___2F05DEDA]') IS NULL
ALTER TABLE [document_store_department_member] ADD CONSTRAINT [FK__document___item___2F05DEDA] FOREIGN KEY ([item_id]) REFERENCES [lookup_department] ([code])
GO

IF OBJECT_ID('[document_store_department_member]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__document___modif__33CA93F7]') IS NULL
ALTER TABLE [document_store_department_member] ADD CONSTRAINT [FK__document___modif__33CA93F7] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[document_store_department_member]') IS NOT NULL AND OBJECT_ID('[lookup_document_store_role]') IS NOT NULL AND OBJECT_ID('[FK__document___userl__2FFA0313]') IS NULL
ALTER TABLE [document_store_department_member] ADD CONSTRAINT [FK__document___userl__2FFA0313] FOREIGN KEY ([userlevel]) REFERENCES [lookup_document_store_role] ([code])
GO

IF OBJECT_ID('[document_store_permissions]') IS NOT NULL AND OBJECT_ID('[document_store]') IS NOT NULL AND OBJECT_ID('[FK__document___docum__1AFEE62D]') IS NULL
ALTER TABLE [document_store_permissions] ADD CONSTRAINT [FK__document___docum__1AFEE62D] FOREIGN KEY ([document_store_id]) REFERENCES [document_store] ([document_store_id])
GO

IF OBJECT_ID('[document_store_permissions]') IS NOT NULL AND OBJECT_ID('[lookup_document_store_permission]') IS NOT NULL AND OBJECT_ID('[FK__document___permi__1BF30A66]') IS NULL
ALTER TABLE [document_store_permissions] ADD CONSTRAINT [FK__document___permi__1BF30A66] FOREIGN KEY ([permission_id]) REFERENCES [lookup_document_store_permission] ([code])
GO

IF OBJECT_ID('[document_store_permissions]') IS NOT NULL AND OBJECT_ID('[lookup_document_store_role]') IS NOT NULL AND OBJECT_ID('[FK__document___userl__1CE72E9F]') IS NULL
ALTER TABLE [document_store_permissions] ADD CONSTRAINT [FK__document___userl__1CE72E9F] FOREIGN KEY ([userlevel]) REFERENCES [lookup_document_store_role] ([code])
GO

IF OBJECT_ID('[document_store_role_member]') IS NOT NULL AND OBJECT_ID('[document_store]') IS NOT NULL AND OBJECT_ID('[FK__document___docum__267098D9]') IS NULL
ALTER TABLE [document_store_role_member] ADD CONSTRAINT [FK__document___docum__267098D9] FOREIGN KEY ([document_store_id]) REFERENCES [document_store] ([document_store_id])
GO

IF OBJECT_ID('[document_store_role_member]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__document___enter__2A4129BD]') IS NULL
ALTER TABLE [document_store_role_member] ADD CONSTRAINT [FK__document___enter__2A4129BD] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[document_store_role_member]') IS NOT NULL AND OBJECT_ID('[role]') IS NOT NULL AND OBJECT_ID('[FK__document___item___2764BD12]') IS NULL
ALTER TABLE [document_store_role_member] ADD CONSTRAINT [FK__document___item___2764BD12] FOREIGN KEY ([item_id]) REFERENCES [role] ([role_id])
GO

IF OBJECT_ID('[document_store_role_member]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__document___modif__2C29722F]') IS NULL
ALTER TABLE [document_store_role_member] ADD CONSTRAINT [FK__document___modif__2C29722F] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[document_store_role_member]') IS NOT NULL AND OBJECT_ID('[lookup_document_store_role]') IS NOT NULL AND OBJECT_ID('[FK__document___userl__2858E14B]') IS NULL
ALTER TABLE [document_store_role_member] ADD CONSTRAINT [FK__document___userl__2858E14B] FOREIGN KEY ([userlevel]) REFERENCES [lookup_document_store_role] ([code])
GO

IF OBJECT_ID('[document_store_user_member]') IS NOT NULL AND OBJECT_ID('[document_store]') IS NOT NULL AND OBJECT_ID('[FK__document___docum__1ECF7711]') IS NULL
ALTER TABLE [document_store_user_member] ADD CONSTRAINT [FK__document___docum__1ECF7711] FOREIGN KEY ([document_store_id]) REFERENCES [document_store] ([document_store_id])
GO

IF OBJECT_ID('[document_store_user_member]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__document___enter__22A007F5]') IS NULL
ALTER TABLE [document_store_user_member] ADD CONSTRAINT [FK__document___enter__22A007F5] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[document_store_user_member]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__document___item___1FC39B4A]') IS NULL
ALTER TABLE [document_store_user_member] ADD CONSTRAINT [FK__document___item___1FC39B4A] FOREIGN KEY ([item_id]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[document_store_user_member]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__document___modif__24885067]') IS NULL
ALTER TABLE [document_store_user_member] ADD CONSTRAINT [FK__document___modif__24885067] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[document_store_user_member]') IS NOT NULL AND OBJECT_ID('[lookup_document_store_role]') IS NOT NULL AND OBJECT_ID('[FK__document___userl__20B7BF83]') IS NULL
ALTER TABLE [document_store_user_member] ADD CONSTRAINT [FK__document___userl__20B7BF83] FOREIGN KEY ([userlevel]) REFERENCES [lookup_document_store_role] ([code])
GO

IF OBJECT_ID('[project_accounts]') IS NOT NULL AND OBJECT_ID('[organization]') IS NOT NULL AND OBJECT_ID('[FK__project_a__org_i__4F67C174]') IS NULL
ALTER TABLE [project_accounts] ADD CONSTRAINT [FK__project_a__org_i__4F67C174] FOREIGN KEY ([org_id]) REFERENCES [organization] ([org_id])
GO

IF OBJECT_ID('[project_accounts]') IS NOT NULL AND OBJECT_ID('[projects]') IS NOT NULL AND OBJECT_ID('[FK__project_a__proje__4E739D3B]') IS NULL
ALTER TABLE [project_accounts] ADD CONSTRAINT [FK__project_a__proje__4E739D3B] FOREIGN KEY ([project_id]) REFERENCES [projects] ([project_id])
GO

IF OBJECT_ID('[project_news_category]') IS NOT NULL AND OBJECT_ID('[projects]') IS NOT NULL AND OBJECT_ID('[FK__project_n__proje__1BE81D6E]') IS NULL
ALTER TABLE [project_news_category] ADD CONSTRAINT [FK__project_n__proje__1BE81D6E] FOREIGN KEY ([project_id]) REFERENCES [projects] ([project_id])
GO

IF OBJECT_ID('[contact_imaddress]') IS NOT NULL AND OBJECT_ID('[contact]') IS NOT NULL AND OBJECT_ID('[FK__contact_i__conta__7C1A6C5A]') IS NULL
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [FK__contact_i__conta__7C1A6C5A] FOREIGN KEY ([contact_id]) REFERENCES [contact] ([contact_id])
GO

IF OBJECT_ID('[contact_imaddress]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__contact_i__enter__7FEAFD3E]') IS NULL
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [FK__contact_i__enter__7FEAFD3E] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[contact_imaddress]') IS NOT NULL AND OBJECT_ID('[lookup_im_types]') IS NOT NULL AND OBJECT_ID('[FK__contact_i__imadd__7D0E9093]') IS NULL
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [FK__contact_i__imadd__7D0E9093] FOREIGN KEY ([imaddress_type]) REFERENCES [lookup_im_types] ([code])
GO

IF OBJECT_ID('[contact_imaddress]') IS NOT NULL AND OBJECT_ID('[lookup_im_services]') IS NOT NULL AND OBJECT_ID('[FK__contact_i__imadd__7E02B4CC]') IS NULL
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [FK__contact_i__imadd__7E02B4CC] FOREIGN KEY ([imaddress_service]) REFERENCES [lookup_im_services] ([code])
GO

IF OBJECT_ID('[contact_imaddress]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__contact_i__modif__01D345B0]') IS NULL
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [FK__contact_i__modif__01D345B0] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[contact_lead_read_map]') IS NOT NULL AND OBJECT_ID('[contact]') IS NOT NULL AND OBJECT_ID('[FK__contact_l__conta__0F624AF8]') IS NULL
ALTER TABLE [contact_lead_read_map] ADD CONSTRAINT [FK__contact_l__conta__0F624AF8] FOREIGN KEY ([contact_id]) REFERENCES [contact] ([contact_id])
GO

IF OBJECT_ID('[contact_lead_read_map]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__contact_l__user___0E6E26BF]') IS NULL
ALTER TABLE [contact_lead_read_map] ADD CONSTRAINT [FK__contact_l__user___0E6E26BF] FOREIGN KEY ([user_id]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[contact_lead_skipped_map]') IS NOT NULL AND OBJECT_ID('[contact]') IS NOT NULL AND OBJECT_ID('[FK__contact_l__conta__0B91BA14]') IS NULL
ALTER TABLE [contact_lead_skipped_map] ADD CONSTRAINT [FK__contact_l__conta__0B91BA14] FOREIGN KEY ([contact_id]) REFERENCES [contact] ([contact_id])
GO

IF OBJECT_ID('[contact_lead_skipped_map]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__contact_l__user___0A9D95DB]') IS NULL
ALTER TABLE [contact_lead_skipped_map] ADD CONSTRAINT [FK__contact_l__user___0A9D95DB] FOREIGN KEY ([user_id]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[contact_textmessageaddress]') IS NOT NULL AND OBJECT_ID('[contact]') IS NOT NULL AND OBJECT_ID('[FK__contact_t__conta__05A3D694]') IS NULL
ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [FK__contact_t__conta__05A3D694] FOREIGN KEY ([contact_id]) REFERENCES [contact] ([contact_id])
GO

IF OBJECT_ID('[contact_textmessageaddress]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__contact_t__enter__0880433F]') IS NULL
ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [FK__contact_t__enter__0880433F] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[contact_textmessageaddress]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__contact_t__modif__0A688BB1]') IS NULL
ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [FK__contact_t__modif__0A688BB1] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

IF OBJECT_ID('[contact_textmessageaddress]') IS NOT NULL AND OBJECT_ID('[lookup_im_types]') IS NOT NULL AND OBJECT_ID('[FK__contact_t__textm__0697FACD]') IS NULL
ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [FK__contact_t__textm__0697FACD] FOREIGN KEY ([textmessageaddress_type]) REFERENCES [lookup_im_types] ([code])
GO

IF OBJECT_ID('[product_catalog]') IS NOT NULL AND OBJECT_ID('[lookup_product_manufacturer]') IS NOT NULL AND OBJECT_ID('[FK__product_c__manuf__2077C861]') IS NULL
ALTER TABLE [product_catalog] ADD CONSTRAINT [FK__product_c__manuf__2077C861] FOREIGN KEY ([manufacturer_id]) REFERENCES [lookup_product_manufacturer] ([code])
GO

IF OBJECT_ID('[product_option_values]') IS NOT NULL AND OBJECT_ID('[lookup_currency]') IS NOT NULL AND OBJECT_ID('[FK__product_o__cost___74643BF9]') IS NULL
ALTER TABLE [product_option_values] ADD CONSTRAINT [FK__product_o__cost___74643BF9] FOREIGN KEY ([cost_currency]) REFERENCES [lookup_currency] ([code])
GO

IF OBJECT_ID('[project_news]') IS NOT NULL AND OBJECT_ID('[project_news_category]') IS NOT NULL AND OBJECT_ID('[FK__project_n__categ__22951AFD]') IS NULL
ALTER TABLE [project_news] ADD CONSTRAINT [FK__project_n__categ__22951AFD] FOREIGN KEY ([category_id]) REFERENCES [project_news_category] ([category_id])
GO

IF OBJECT_ID('[project_news]') IS NOT NULL AND OBJECT_ID('[lookup_news_template]') IS NOT NULL AND OBJECT_ID('[FK__project_n__templ__31D75E8D]') IS NULL
ALTER TABLE [project_news] ADD CONSTRAINT [FK__project_n__templ__31D75E8D] FOREIGN KEY ([template_id]) REFERENCES [lookup_news_template] ([code])
GO

IF OBJECT_ID('[product_catalog_pricing]') IS NOT NULL AND OBJECT_ID('[lookup_currency]') IS NOT NULL AND OBJECT_ID('[FK__product_c__cost___3296789C]') IS NULL
ALTER TABLE [product_catalog_pricing] ADD CONSTRAINT [FK__product_c__cost___3296789C] FOREIGN KEY ([cost_currency]) REFERENCES [lookup_currency] ([code])
GO

IF OBJECT_ID('[product_option_map]') IS NOT NULL AND OBJECT_ID('[product_option]') IS NOT NULL AND OBJECT_ID('[FK__product_o__optio__7928F116]') IS NULL
ALTER TABLE [product_option_map] ADD CONSTRAINT [FK__product_o__optio__7928F116] FOREIGN KEY ([option_id]) REFERENCES [product_option] ([option_id])
GO

IF OBJECT_ID('[product_option_map]') IS NOT NULL AND OBJECT_ID('[product_catalog]') IS NOT NULL AND OBJECT_ID('[FK__product_o__produ__7834CCDD]') IS NULL
ALTER TABLE [product_option_map] ADD CONSTRAINT [FK__product_o__produ__7834CCDD] FOREIGN KEY ([product_id]) REFERENCES [product_catalog] ([product_id])
GO

IF OBJECT_ID('[order_product_options]') IS NOT NULL AND OBJECT_ID('[product_option_map]') IS NOT NULL AND OBJECT_ID('[FK__order_pro__produ__713DB68B]') IS NULL
ALTER TABLE [order_product_options] ADD CONSTRAINT [FK__order_pro__produ__713DB68B] FOREIGN KEY ([product_option_id]) REFERENCES [product_option_map] ([product_option_id])
GO

IF OBJECT_ID('[quote_product_options]') IS NOT NULL AND OBJECT_ID('[product_option_map]') IS NOT NULL AND OBJECT_ID('[FK__quote_pro__produ__1B33F057]') IS NULL
ALTER TABLE [quote_product_options] ADD CONSTRAINT [FK__quote_pro__produ__1B33F057] FOREIGN KEY ([product_option_id]) REFERENCES [product_option_map] ([product_option_id])
GO

IF OBJECT_ID('[taskcategorylink_news]') IS NOT NULL AND OBJECT_ID('[lookup_task_category]') IS NOT NULL AND OBJECT_ID('[FK__taskcateg__categ__7C7A5F0D]') IS NULL
ALTER TABLE [taskcategorylink_news] ADD CONSTRAINT [FK__taskcateg__categ__7C7A5F0D] FOREIGN KEY ([category_id]) REFERENCES [lookup_task_category] ([code])
GO

IF OBJECT_ID('[taskcategorylink_news]') IS NOT NULL AND OBJECT_ID('[project_news]') IS NOT NULL AND OBJECT_ID('[FK__taskcateg__news___7B863AD4]') IS NULL
ALTER TABLE [taskcategorylink_news] ADD CONSTRAINT [FK__taskcateg__news___7B863AD4] FOREIGN KEY ([news_id]) REFERENCES [project_news] ([news_id])
GO

IF OBJECT_ID('[project_assignments_status]') IS NOT NULL AND OBJECT_ID('[lookup_project_status]') IS NOT NULL AND OBJECT_ID('[FK__project_a__proje__4BCC3ABA]') IS NULL
ALTER TABLE [project_assignments_status] ADD CONSTRAINT [FK__project_a__proje__4BCC3ABA] FOREIGN KEY ([project_status_id]) REFERENCES [lookup_project_status] ([code])
GO

IF OBJECT_ID('[project_assignments_status]') IS NOT NULL AND OBJECT_ID('[access]') IS NOT NULL AND OBJECT_ID('[FK__project_a__user___4CC05EF3]') IS NULL
ALTER TABLE [project_assignments_status] ADD CONSTRAINT [FK__project_a__user___4CC05EF3] FOREIGN KEY ([user_assign_id]) REFERENCES [access] ([user_id])
GO

-- INSERTS
--UPDATE [business_process_component_parameter] SET [param_value]='The following ticket in Concourse Suite Community Edition has been assigned to you:'+CHAR(10)+''+CHAR(10)+'--- Ticket Details ---'+CHAR(10)+''+CHAR(10)+'Ticket # ${this.paddedId}'+CHAR(10)+'Priority: ${ticketPriorityLookup.description}'+CHAR(10)+'Severity: ${ticketSeverityLookup.description}'+CHAR(10)+'Issue: ${this.problem}'+CHAR(10)+''+CHAR(10)+'Assigned By: ${ticketModifiedByContact.nameFirstLast}'+CHAR(10)+'Comment: ${this.comment}'+CHAR(10)+'' WHERE [id]=12
--UPDATE [business_process_component_parameter] SET [param_value]='** This is an automated message **'+CHAR(10)+''+CHAR(10)+'The following tickets in Concourse Suite Community Edition are unassigned and need attention:'+CHAR(10)+''+CHAR(10)+'' WHERE [id]=22
--UPDATE [business_process_component_parameter] SET [param_value]='----- Ticket Details -----'+CHAR(10)+'Ticket # ${this.paddedId}'+CHAR(10)+'Created: ${this.enteredString}'+CHAR(10)+'Organization: ${ticketOrganization.name}'+CHAR(10)+'Priority: ${ticketPriorityLookup.description}'+CHAR(10)+'Severity: ${ticketSeverityLookup.description}'+CHAR(10)+'Issue: ${this.problem}'+CHAR(10)+''+CHAR(10)+'Last Modified By: ${ticketModifiedByContact.nameFirstLast}'+CHAR(10)+'Comment: ${this.comment}'+CHAR(10)+''+CHAR(10)+''+CHAR(10)+'' WHERE [id]=23
--UPDATE [business_process_component_parameter] SET [param_value]='The following ticket in Concourse Suite Community Edition has been closed:'+CHAR(10)+''+CHAR(10)+'--- Ticket Details ---'+CHAR(10)+''+CHAR(10)+'Ticket # ${this.paddedId}'+CHAR(10)+'Priority: ${ticketPriorityLookup.description}'+CHAR(10)+'Severity: ${ticketSeverityLookup.description}'+CHAR(10)+'Issue: ${this.problem}'+CHAR(10)+''+CHAR(10)+'Comment: ${this.comment}'+CHAR(10)+''+CHAR(10)+'Closed by: ${ticketModifiedByContact.nameFirstLast}'+CHAR(10)+''+CHAR(10)+'Solution: ${this.solution}'+CHAR(10)+'' WHERE [id]=6

--UPDATE [business_process_parameter_library] SET [default_value]='The following ticket in Concourse Suite Community Edition has been assigned to you:'+CHAR(10)+''+CHAR(10)+'--- Ticket Details ---'+CHAR(10)+''+CHAR(10)+'Ticket # ${this.paddedId}'+CHAR(10)+'Priority: ${ticketPriorityLookup.description}'+CHAR(10)+'Severity: ${ticketSeverityLookup.description}'+CHAR(10)+'Issue: ${this.problem}'+CHAR(10)+''+CHAR(10)+'Assigned By: ${ticketModifiedByContact.nameFirstLast}'+CHAR(10)+'Comment: ${this.comment}'+CHAR(10)+'' WHERE [parameter_id]=12
--UPDATE [business_process_parameter_library] SET [default_value]='** This is an automated message **'+CHAR(10)+''+CHAR(10)+'The following tickets in Concourse Suite Community Edition are unassigned and need attention:'+CHAR(10)+''+CHAR(10)+'' WHERE [parameter_id]=22
--UPDATE [business_process_parameter_library] SET [default_value]='----- Ticket Details -----'+CHAR(10)+'Ticket # ${this.paddedId}'+CHAR(10)+'Created: ${this.enteredString}'+CHAR(10)+'Organization: ${ticketOrganization.name}'+CHAR(10)+'Priority: ${ticketPriorityLookup.description}'+CHAR(10)+'Severity: ${ticketSeverityLookup.description}'+CHAR(10)+'Issue: ${this.problem}'+CHAR(10)+''+CHAR(10)+'Last Modified By: ${ticketModifiedByContact.nameFirstLast}'+CHAR(10)+'Comment: ${this.comment}'+CHAR(10)+''+CHAR(10)+''+CHAR(10)+'' WHERE [parameter_id]=23
--UPDATE [business_process_parameter_library] SET [default_value]='The following ticket in Concourse Suite Community Edition has been closed:'+CHAR(10)+''+CHAR(10)+'--- Ticket Details ---'+CHAR(10)+''+CHAR(10)+'Ticket # ${this.paddedId}'+CHAR(10)+'Priority: ${ticketPriorityLookup.description}'+CHAR(10)+'Severity: ${ticketSeverityLookup.description}'+CHAR(10)+'Issue: ${this.problem}'+CHAR(10)+''+CHAR(10)+'Comment: ${this.comment}'+CHAR(10)+''+CHAR(10)+'Closed by: ${ticketModifiedByContact.nameFirstLast}'+CHAR(10)+''+CHAR(10)+'Solution: ${this.solution}'+CHAR(10)+'' WHERE [parameter_id]=6


SET IDENTITY_INSERT [lookup_im_types] ON
INSERT INTO lookup_im_types ([code], [description], [default_item], [level], [enabled]) VALUES (1, 'Business', 0, 10, 1);
INSERT INTO lookup_im_types ([code], [description], [default_item], [level], [enabled]) VALUES (2, 'Personal', 0, 20, 1);
INSERT INTO lookup_im_types ([code], [description], [default_item], [level], [enabled]) VALUES (3, 'Other', 0, 30, 1);
SET IDENTITY_INSERT [lookup_im_types] OFF



SET IDENTITY_INSERT [lookup_im_services] ON
INSERT INTO lookup_im_services ([code], [description], [default_item], [level], [enabled]) VALUES (1, 'AOL Instant Messenger', 0, 10, 1);
INSERT INTO lookup_im_services ([code], [description], [default_item], [level], [enabled]) VALUES (2, 'Jabber Instant Messenger', 0, 20, 1);
INSERT INTO lookup_im_services ([code], [description], [default_item], [level], [enabled]) VALUES (3, 'MSN Instant Messenger', 0, 30, 1);
SET IDENTITY_INSERT [lookup_im_services] OFF



SET IDENTITY_INSERT [lookup_contact_source] ON
INSERT INTO lookup_contact_source ([code], [description], [default_item], [level], [enabled]) VALUES (1, 'Advertisement', 0, 10, 1);
INSERT INTO lookup_contact_source ([code], [description], [default_item], [level], [enabled]) VALUES (2, 'Employee Referral', 0, 20, 1);
INSERT INTO lookup_contact_source ([code], [description], [default_item], [level], [enabled]) VALUES (3, 'External Referral', 0, 30, 1);
INSERT INTO lookup_contact_source ([code], [description], [default_item], [level], [enabled]) VALUES (4, 'Partner', 0, 40, 1);
INSERT INTO lookup_contact_source ([code], [description], [default_item], [level], [enabled]) VALUES (5, 'Public Relations', 0, 50, 1);
INSERT INTO lookup_contact_source ([code], [description], [default_item], [level], [enabled]) VALUES (6, 'Trade Show', 0, 60, 1);
INSERT INTO lookup_contact_source ([code], [description], [default_item], [level], [enabled]) VALUES (7, 'Web', 0, 70, 1);
INSERT INTO lookup_contact_source ([code], [description], [default_item], [level], [enabled]) VALUES (8, 'Word of Mouth', 0, 80, 1);
INSERT INTO lookup_contact_source ([code], [description], [default_item], [level], [enabled]) VALUES (9, 'Other', 0, 90, 1);
SET IDENTITY_INSERT [lookup_contact_source] OFF

SET IDENTITY_INSERT [lookup_textmessage_types] ON
INSERT INTO lookup_textmessage_types ([code], [description], [default_item], [level], [enabled]) VALUES (1, 'Business', 0, 10, 1);
INSERT INTO lookup_textmessage_types ([code], [description], [default_item], [level], [enabled]) VALUES (2, 'Personal', 0, 20, 1);
INSERT INTO lookup_textmessage_types ([code], [description], [default_item], [level], [enabled]) VALUES (3, 'Other', 0, 30, 1);
SET IDENTITY_INSERT [lookup_textmessage_types] OFF


SET IDENTITY_INSERT [permission_category] ON
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports],[products],[webdav],[logos],[constant])VALUES(22,'Leads',NULL,400,1,1,0,1,0,0,0,0,0,0,0,0,228051100)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports],[products],[webdav],[logos],[constant])VALUES(23,'Documents',NULL,1600,1,1,0,0,0,0,0,0,0,0,1,0,1202041528)
SET IDENTITY_INSERT [permission_category] OFF

UPDATE permission_category SET enabled = 1, active = 1 WHERE category = 'Projects';

UPDATE permission SET description = 'Product Catalog Editor' where permission = 'admin-sysconfig-products';

UPDATE permission SET permission_add = 0, permission_edit = 0, permission_delete = 0, description = 'Access to Quotes module' where permission = 'quotes';

UPDATE permission SET enabled = 1, active = 1 WHERE permission = 'accounts-quotes';


SET IDENTITY_INSERT [lookup_delivery_options] ON
INSERT INTO [lookup_delivery_options] ([code], [description], [default_item], [level], [enabled]) VALUES (7, 'Broadcast', 0, 70, 1)
INSERT INTO [lookup_delivery_options] ([code], [description], [default_item], [level], [enabled]) VALUES (8, 'Instant Message', 0, 80, 0)
INSERT INTO [lookup_delivery_options] ([code], [description], [default_item], [level], [enabled]) VALUES (9, 'Secure Socket', 0, 90, 0)
SET IDENTITY_INSERT [lookup_delivery_options] OFF

UPDATE [lookup_delivery_options] SET [level]=10 WHERE [code]=1
UPDATE [lookup_delivery_options] SET [level]=20 WHERE [code]=2
UPDATE [lookup_delivery_options] SET [level]=30 WHERE [code]=3
UPDATE [lookup_delivery_options] SET [level]=40 WHERE [code]=4
UPDATE [lookup_delivery_options] SET [level]=50 WHERE [code]=5
UPDATE [lookup_delivery_options] SET [level]=60 WHERE [code]=6

SET IDENTITY_INSERT [lookup_product_conf_result] ON
INSERT INTO [lookup_product_conf_result] ([code], [description], [default_item], [level], [enabled]) VALUES (1, 'integer', 0, 10, 1)
INSERT INTO [lookup_product_conf_result] ([code], [description], [default_item], [level], [enabled]) VALUES (2, 'float', 0, 20, 1)
INSERT INTO [lookup_product_conf_result] ([code], [description], [default_item], [level], [enabled]) VALUES (3, 'boolean', 0, 30, 1)
INSERT INTO [lookup_product_conf_result] ([code], [description], [default_item], [level], [enabled]) VALUES (4, 'timestamp', 0, 40, 1)
INSERT INTO [lookup_product_conf_result] ([code], [description], [default_item], [level], [enabled]) VALUES (5, 'string', 0, 50, 1)
SET IDENTITY_INSERT [lookup_product_conf_result] OFF


SET IDENTITY_INSERT [lookup_product_ship_time] ON
INSERT INTO [lookup_product_ship_time] ([code], [description], [default_item], [level], [enabled]) VALUES (1, '24 Hours', 0, 10, 1)
INSERT INTO [lookup_product_ship_time] ([code], [description], [default_item], [level], [enabled]) VALUES (2, '2 Days', 0, 20, 1)
INSERT INTO [lookup_product_ship_time] ([code], [description], [default_item], [level], [enabled]) VALUES (3, '1 Week', 0, 30, 1)
SET IDENTITY_INSERT [lookup_product_ship_time] OFF

SET IDENTITY_INSERT [lookup_product_shipping] ON
INSERT INTO [lookup_product_shipping] ([code], [description], [default_item], [level], [enabled]) VALUES (1, 'DHL', 0, 10, 1)
INSERT INTO [lookup_product_shipping] ([code], [description], [default_item], [level], [enabled]) VALUES (2, 'FEDEX', 0, 20, 1)
INSERT INTO [lookup_product_shipping] ([code], [description], [default_item], [level], [enabled]) VALUES (3, 'UPS', 0, 30, 1)
INSERT INTO [lookup_product_shipping] ([code], [description], [default_item], [level], [enabled]) VALUES (4, 'USPS', 0, 40, 1)
SET IDENTITY_INSERT [lookup_product_shipping] OFF


SET IDENTITY_INSERT [lookup_project_permission_category] ON
INSERT INTO [lookup_project_permission_category] ([code], [description], [default_item], [level], [enabled], [group_id]) VALUES (10, 'Accounts', 0, 85, 1, 1)
SET IDENTITY_INSERT [lookup_project_permission_category] OFF


SET IDENTITY_INSERT [lookup_project_permission] ON
INSERT INTO [lookup_project_permission] ([code], [category_id], [permission], [description], [default_item], [level], [enabled], [group_id], [default_role]) VALUES (52, 10, 'project-accounts-view', 'View account links', 0, 500, 1, 1, 3)
INSERT INTO [lookup_project_permission] ([code], [category_id], [permission], [description], [default_item], [level], [enabled], [group_id], [default_role]) VALUES (53, 10, 'project-accounts-manage', 'Manage account links', 0, 510, 1, 1, 1)
SET IDENTITY_INSERT [lookup_project_permission] OFF


SET IDENTITY_INSERT [lookup_quote_source] ON
INSERT INTO [lookup_quote_source] ([code], [description], [default_item], [level], [enabled]) VALUES (1, 'Email', 0, 10, 1)
INSERT INTO [lookup_quote_source] ([code], [description], [default_item], [level], [enabled]) VALUES (2, 'Fax', 0, 20, 1)
INSERT INTO [lookup_quote_source] ([code], [description], [default_item], [level], [enabled]) VALUES (3, 'Incoming Phone Call', 0, 30, 1)
INSERT INTO [lookup_quote_source] ([code], [description], [default_item], [level], [enabled]) VALUES (4, 'Outgoing Phone Call', 0, 40, 1)
INSERT INTO [lookup_quote_source] ([code], [description], [default_item], [level], [enabled]) VALUES (5, 'In Person', 0, 50, 1)
INSERT INTO [lookup_quote_source] ([code], [description], [default_item], [level], [enabled]) VALUES (6, 'Mail', 0, 60, 1)
INSERT INTO [lookup_quote_source] ([code], [description], [default_item], [level], [enabled]) VALUES (7, 'Agent Request', 0, 70, 1)
SET IDENTITY_INSERT [lookup_quote_source] OFF

SET IDENTITY_INSERT [lookup_quote_type] ON
INSERT INTO [lookup_quote_type] ([code], [description], [default_item], [level], [enabled]) VALUES (1, 'New', 0, 10, 1)
INSERT INTO [lookup_quote_type] ([code], [description], [default_item], [level], [enabled]) VALUES (2, 'Change', 0, 20, 1)
INSERT INTO [lookup_quote_type] ([code], [description], [default_item], [level], [enabled]) VALUES (3, 'Upgrade/Downgrade', 0, 30, 1)
INSERT INTO [lookup_quote_type] ([code], [description], [default_item], [level], [enabled]) VALUES (4, 'Disconnect', 0, 40, 1)
INSERT INTO [lookup_quote_type] ([code], [description], [default_item], [level], [enabled]) VALUES (5, 'Move', 0, 50, 1)
SET IDENTITY_INSERT [lookup_quote_type] OFF


SET IDENTITY_INSERT [lookup_product_format] ON
INSERT [lookup_product_format] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Physical',0,10,1)
INSERT [lookup_product_format] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Electronic',0,20,1)
SET IDENTITY_INSERT [lookup_product_format] OFF



SET IDENTITY_INSERT [lookup_product_tax] ON
INSERT [lookup_product_tax] ([code],[description],[default_item],[level],[enabled])VALUES(1,'State Tax',0,10,1)
INSERT [lookup_product_tax] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Sales Tax',0,20,1)
SET IDENTITY_INSERT [lookup_product_tax] OFF


SET IDENTITY_INSERT [product_option_configurator] ON
INSERT [product_option_configurator] ([configurator_id],[short_description],[long_description],[class_name],[result_type],[configurator_name])VALUES(1,'A text field for free-form additional information','String Configurator','org.aspcfs.modules.products.configurator.StringConfigurator',1,'Text')
INSERT [product_option_configurator] ([configurator_id],[short_description],[long_description],[class_name],[result_type],[configurator_name])VALUES(2,'A check box for yes/no information','Checkbox Configurator','org.aspcfs.modules.products.configurator.CheckboxConfigurator',1,'Check Box')
INSERT [product_option_configurator] ([configurator_id],[short_description],[long_description],[class_name],[result_type],[configurator_name])VALUES(3,'A list of available choices that can be selected','LookupList Configurator','org.aspcfs.modules.products.configurator.LookupListConfigurator',1,'Lookup List')
INSERT [product_option_configurator] ([configurator_id],[short_description],[long_description],[class_name],[result_type],[configurator_name])VALUES(4,'An input field allowing numbers only','Numerical Configurator','org.aspcfs.modules.products.configurator.NumericalConfigurator',1,'Number')
SET IDENTITY_INSERT [product_option_configurator] OFF


SET IDENTITY_INSERT [lookup_payment_status] ON
INSERT [lookup_payment_status] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Pending',0,10,1)
INSERT [lookup_payment_status] ([code],[description],[default_item],[level],[enabled])VALUES(2,'In Progress',0,20,1)
INSERT [lookup_payment_status] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Approved',0,30,1)
INSERT [lookup_payment_status] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Declined',0,40,1)
SET IDENTITY_INSERT [lookup_payment_status] OFF


SET IDENTITY_INSERT [lookup_recurring_type] ON
INSERT INTO [lookup_recurring_type] ([code], [description], [default_item], [level], [enabled]) VALUES (1, 'Weekly', 0, 10, 1)
INSERT INTO [lookup_recurring_type] ([code], [description], [default_item], [level], [enabled]) VALUES (2, 'Monthly', 0, 20, 1)
INSERT INTO [lookup_recurring_type] ([code], [description], [default_item], [level], [enabled]) VALUES (3, 'Yearly', 0, 30, 1)
SET IDENTITY_INSERT [lookup_recurring_type] OFF

SET IDENTITY_INSERT [module_field_categorylink] ON
INSERT INTO [module_field_categorylink] ([id], [module_id], [category_id], [level], [description], [entered]) VALUES (5, 21, 120200513, 10, 'Employees', '20050331 09:20:06.500')
SET IDENTITY_INSERT [module_field_categorylink] OFF


INSERT [survey] ([name], [description], [intro], [outro], [itemLength], [type], [enabled], [status], [entered], [enteredby], [modified], [modifiedby]) VALUES ('Address Update Request', NULL, NULL, NULL, -1, 2, 1, -1, '20050331 09:20:24.060', 0, '20050331 09:20:24.060', 0)


SET IDENTITY_INSERT [webdav] ON
INSERT [webdav] ([id],[category_id],[class_name],[entered],[enteredby],[modified],[modifiedby])VALUES(1,1,'org.aspcfs.modules.accounts.webdav.AccountsWebdavContext','Apr  1 2005  9:44:04:127AM',0,'Apr  1 2005  9:44:04:127AM',0)
INSERT [webdav] ([id],[category_id],[class_name],[entered],[enteredby],[modified],[modifiedby])VALUES(2,7,'com.zeroio.iteam.webdav.ProjectsWebdavContext','Apr  1 2005  9:44:04:417AM',0,'Apr  1 2005  9:44:04:417AM',0)
INSERT [webdav] ([id],[category_id],[class_name],[entered],[enteredby],[modified],[modifiedby])VALUES(3,23,'org.aspcfs.modules.documents.webdav.DocumentsWebdavContext','Apr  1 2005  9:44:05:980AM',0,'Apr  1 2005  9:44:05:980AM',0)
SET IDENTITY_INSERT [webdav] OFF


SET IDENTITY_INSERT [lookup_relationship_types] ON
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(1,42420034,42420034,'Subsidiary of','Parent of',10,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(2,42420034,42420034,'Customer of','Supplier to',20,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(3,42420034,42420034,'Partner of','Partner of',30,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(4,42420034,42420034,'Competitor of','Competitor of',40,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(5,42420034,42420034,'Employee of','Employer of',50,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(6,42420034,42420034,'Department of','Organization made up of',60,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(7,42420034,42420034,'Group of','Organization made up of',70,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(8,42420034,42420034,'Member of','Organization made up of',80,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(9,42420034,42420034,'Consultant to','Consultant of',90,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(10,42420034,42420034,'Influencer of','Influenced by',100,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(11,42420034,42420034,'Enemy of','Enemy of',110,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(12,42420034,42420034,'Proponent of','Endorsed by',120,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(13,42420034,42420034,'Ally of','Ally of',130,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(14,42420034,42420034,'Sponsor of','Sponsored by',140,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(15,42420034,42420034,'Relative of','Relative of',150,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(16,42420034,42420034,'Affiliated with','Affiliated with',160,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(17,42420034,42420034,'Teammate of','Teammate of',170,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(18,42420034,42420034,'Financier of','Financed by',180,0,1)
SET IDENTITY_INSERT [lookup_relationship_types] OFF



SET IDENTITY_INSERT [lookup_document_store_permission_category] ON
INSERT [lookup_document_store_permission_category] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(1,'Document Store Details',0,10,1,0)
INSERT [lookup_document_store_permission_category] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(2,'Team Members',0,20,1,0)
INSERT [lookup_document_store_permission_category] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(3,'Document Library',0,30,1,0)
INSERT [lookup_document_store_permission_category] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(4,'Setup',0,40,1,0)
SET IDENTITY_INSERT [lookup_document_store_permission_category] OFF


SET IDENTITY_INSERT [lookup_document_store_role] ON
INSERT [lookup_document_store_role] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(1,'Manager',0,1,1,1)
INSERT [lookup_document_store_role] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(2,'Contributor level 3',0,2,1,1)
INSERT [lookup_document_store_role] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(3,'Contributor level 2',0,3,1,1)
INSERT [lookup_document_store_role] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(4,'Contributor level 1',0,4,1,1)
INSERT [lookup_document_store_role] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(5,'Guest',0,5,1,1)
SET IDENTITY_INSERT [lookup_document_store_role] OFF



SET IDENTITY_INSERT [lookup_document_store_permission] ON
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(1,1,'documentcenter-details-view','View document store details',0,10,1,1,5)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(2,1,'documentcenter-details-edit','Modify document store details',0,20,1,1,1)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(3,1,'documentcenter-details-delete','Delete document store',0,30,1,1,1)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(4,2,'documentcenter-team-view','View team members',0,40,1,1,5)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(5,2,'documentcenter-team-view-email','See team member email addresses',0,50,1,1,4)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(6,2,'documentcenter-team-edit','Modify team',0,60,1,1,1)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(7,2,'documentcenter-team-edit-role','Modify team member role',0,70,1,1,1)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(8,3,'documentcenter-documents-view','View documents',0,80,1,1,5)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(9,3,'documentcenter-documents-folders-add','Create folders',0,90,1,1,2)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(10,3,'documentcenter-documents-folders-edit','Modify folders',0,100,1,1,3)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(11,3,'documentcenter-documents-folders-delete','Delete folders',0,110,1,1,2)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(12,3,'documentcenter-documents-files-upload','Upload files',0,120,1,1,4)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(13,3,'documentcenter-documents-files-download','Download files',0,130,1,1,5)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(14,3,'documentcenter-documents-files-rename','Rename files',0,140,1,1,3)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(15,3,'documentcenter-documents-files-delete','View document store details',0,150,1,1,2)
INSERT [lookup_document_store_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(16,4,'documentcenter-setup-permissions','Configure document store permissions',0,160,1,1,1)
SET IDENTITY_INSERT [lookup_document_store_permission] OFF


SET IDENTITY_INSERT [lookup_quote_delivery] ON
INSERT [lookup_quote_delivery] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Email',0,10,1)
INSERT [lookup_quote_delivery] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Fax',0,20,1)
INSERT [lookup_quote_delivery] ([code],[description],[default_item],[level],[enabled])VALUES(3,'In Person',0,30,1)
INSERT [lookup_quote_delivery] ([code],[description],[default_item],[level],[enabled])VALUES(4,'DHL',0,40,1)
INSERT [lookup_quote_delivery] ([code],[description],[default_item],[level],[enabled])VALUES(5,'FEDEX',0,50,1)
INSERT [lookup_quote_delivery] ([code],[description],[default_item],[level],[enabled])VALUES(6,'UPS',0,60,1)
INSERT [lookup_quote_delivery] ([code],[description],[default_item],[level],[enabled])VALUES(7,'USPS',0,70,1)
SET IDENTITY_INSERT [lookup_quote_delivery] OFF


-- UPDATES --

UPDATE permission_category
SET constant=13, level=100
WHERE category='System';

UPDATE permission_category
SET constant=14, level=200
WHERE category='My Home Page';

UPDATE permission_category
SET constant=420041014, level=300
WHERE category='Products and Services';

UPDATE permission_category
SET constant=228051100, level=400
WHERE category='Leads';

UPDATE permission_category
SET constant=2, level=500
WHERE category='Contacts';

UPDATE permission_category
SET constant=4, level=600
WHERE category='Pipeline';

UPDATE permission_category
SET constant=1, level=700
WHERE category='Accounts';

UPDATE permission_category
SET constant=3, level=800
WHERE category='Auto Guide';

UPDATE permission_category
SET constant=420041017, level=900
WHERE category='Quotes';

UPDATE permission_category
SET constant=420041018, level=1000
WHERE category='Orders';

UPDATE permission_category
SET constant=6, level=1100
WHERE category='Communications';

UPDATE permission_category
SET constant=7, level=1200
WHERE category='Projects';

UPDATE permission_category
SET constant=130041100, level=1300
WHERE category='Service Contracts';

UPDATE permission_category
SET constant=130041000, level=1400
WHERE category='Assets';

UPDATE permission_category
SET constant=8, level=1500
WHERE category='Help Desk';

UPDATE permission_category
SET constant=1202041528, level=1600
WHERE category='Documents';

UPDATE permission_category
SET constant=1111031131, level=1700
WHERE category='Employees';

UPDATE permission_category
SET constant=330041409, level=1800
WHERE category='Product Catalog';

UPDATE permission_category
SET constant=16, level=1900
WHERE category='Reports';

UPDATE permission_category
SET constant=9, level=2000
WHERE category='Admin';

UPDATE permission_category
SET constant=10, level=2100
WHERE category='Help';

UPDATE permission_category
SET constant=15, level=2200
WHERE category='QA';

UPDATE permission_category
SET constant=5, level=2300
WHERE category='Demo';

UPDATE permission_category SET webdav = 1 WHERE constant = 1;
UPDATE permission_category SET webdav = 1 WHERE constant = 7;
UPDATE permission_category SET lookups = 1 WHERE constant = 330041409;
UPDATE permission_category SET enabled = 1 WHERE constant = 420041017;
UPDATE permission_category SET active = 1 WHERE constant = 420041017;
UPDATE permission_category SET lookups = 1 WHERE constant = 420041017;
UPDATE permission_category SET logos = 1 WHERE constant = 420041017;
UPDATE permission_category SET folders = 1 WHERE constant = 1111031131;

DELETE FROM contact_type_levels WHERE contact_id IN (SELECT contact_id FROM contact
WHERE org_id = 0);

UPDATE contact SET employee = 1 WHERE org_id = 0 AND employee = 0;

DROP INDEX product_option_values.idx_pr_opt_val;
CREATE UNIQUE INDEX idx_pr_opt_val ON product_option_values (value_id, option_id, result_id);




INSERT [database_version] ([script_filename],[script_version])VALUES('mssql_2005-03-30.sql','2005-03-30');

