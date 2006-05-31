-- Script (C) 2004 Dark Horse Ventures, all rights reserved
-- Database upgrade v2.8 (2004-06-15)

ALTER TABLE organization ADD [import_id] [int] NULL;
ALTER TABLE organization ADD [status_id] [int] NULL;

ALTER TABLE contact ADD [status_id] [int] NULL;
ALTER TABLE contact ADD [import_id] [int] NULL;

ALTER TABLE role ADD [role_type] [int] NULL;
UPDATE role SET role_type = 0 WHERE role_type IS NULL;

ALTER TABLE permission_category ADD [products] [bit] NULL;
UPDATE permission_category SET products = 0;
ALTER TABLE permission_category ALTER COLUMN [products] [bit] NOT NULL;

ALTER TABLE contact_emailaddress ADD [primary_email] [bit] NULL;
UPDATE contact_emailaddress SET primary_email = 0;
ALTER TABLE contact_emailaddress ALTER COLUMN [primary_email] [bit] NOT NULL;

UPDATE permission_category SET level = 700 WHERE category = 'Accounts';
UPDATE permission_category SET level = 500 WHERE category = 'Contacts';
UPDATE permission_category SET level = 800 WHERE category = 'Auto Guide';
UPDATE permission_category SET level = 600 WHERE category = 'Pipeline';
UPDATE permission_category SET level = 2100 WHERE category = 'Demo';
UPDATE permission_category SET level = 1200 WHERE category = 'Communications';
UPDATE permission_category SET level = 1300 WHERE category = 'Projects';
UPDATE permission_category SET level = 1600 WHERE category = 'Help Desk';
UPDATE permission_category SET level = 1800 WHERE category = 'Admin';
UPDATE permission_category SET level = 1900 WHERE category = 'Help';
UPDATE permission_category SET level = 100 WHERE category = 'System';
UPDATE permission_category SET level = 200 WHERE category = 'My Home Page';
UPDATE permission_category SET level = 2000 WHERE category = 'QA';
UPDATE permission_category SET level = 1700 WHERE category = 'Reports';
UPDATE permission_category SET level = 400 WHERE category = 'Employees';

ALTER TABLE sync_client ADD [enabled] [bit] NULL;
ALTER TABLE sync_client ADD [code] [varchar] (255) NULL;

CREATE TABLE [asset_category] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[cat_level] [int] NOT NULL ,
	[parent_cat_code] [int] NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[full_description] [text] NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [asset_category_draft] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_id] [int] NULL ,
	[cat_level] [int] NOT NULL ,
	[parent_cat_code] [int] NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[full_description] [text] NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [lookup_asset_status] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_creditcard_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_currency] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_email_model] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_hours_reason] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_onsite_model] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_order_source] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_order_status] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_order_terms] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_order_type] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_orderaddress_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_payment_methods] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_phone_model] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_product_category_type] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_product_conf_result] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_product_format] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_product_keyword] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_product_ship_time] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_product_shipping] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_product_tax] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_product_type] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_quote_source] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_quote_status] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_quote_terms] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_quote_type] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_recurring_type] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_response_model] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_sc_category] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_sc_type] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [category_editor_lookup] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[module_id] [int] NOT NULL ,
	[constant_id] [int] NOT NULL ,
	[table_name] [varchar] (60) NULL ,
	[level] [int] NULL ,
	[description] [text] NULL ,
	[entered] [datetime] NULL ,
	[category_id] [int] NOT NULL ,
	[max_levels] [int] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [import] (
	[import_id] [int] IDENTITY (1, 1) NOT NULL ,
	[type] [int] NOT NULL ,
	[name] [varchar] (250) NOT NULL ,
	[description] [text] NULL ,
	[source_type] [int] NULL ,
	[source] [varchar] (1000) NULL ,
	[record_delimiter] [varchar] (10) NULL ,
	[column_delimiter] [varchar] (10) NULL ,
	[total_imported_records] [int] NULL ,
	[total_failed_records] [int] NULL ,
	[status_id] [int] NULL ,
	[file_type] [int] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [product_option_configurator] (
	[configurator_id] [int] IDENTITY (1, 1) NOT NULL ,
	[short_description] [text] NULL ,
	[long_description] [text] NULL ,
	[class_name] [varchar] (255) NULL ,
	[result_type] [int] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [product_catalog] (
	[product_id] [int] IDENTITY (1, 1) NOT NULL ,
	[parent_id] [int] NULL ,
	[product_name] [varchar] (255) NOT NULL ,
	[abbreviation] [varchar] (30) NULL ,
	[short_description] [text] NULL ,
	[long_description] [text] NULL ,
	[type_id] [int] NULL ,
	[special_notes] [varchar] (255) NULL ,
	[sku] [varchar] (40) NULL ,
	[in_stock] [bit] NOT NULL ,
	[format_id] [int] NULL ,
	[shipping_id] [int] NULL ,
	[estimated_ship_time] [int] NULL ,
	[thumbnail_image_id] [int] NULL ,
	[small_image_id] [int] NULL ,
	[large_image_id] [int] NULL ,
	[list_order] [int] NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[start_date] [datetime] NULL ,
	[expiration_date] [datetime] NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [product_category] (
	[category_id] [int] IDENTITY (1, 1) NOT NULL ,
	[parent_id] [int] NULL ,
	[category_name] [varchar] (255) NOT NULL ,
	[abbreviation] [varchar] (30) NULL ,
	[short_description] [text] NULL ,
	[long_description] [text] NULL ,
	[type_id] [int] NULL ,
	[thumbnail_image_id] [int] NULL ,
	[small_image_id] [int] NULL ,
	[large_image_id] [int] NULL ,
	[list_order] [int] NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[start_date] [datetime] NULL ,
	[expiration_date] [datetime] NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [product_option] (
	[option_id] [int] IDENTITY (1, 1) NOT NULL ,
	[configurator_id] [int] NOT NULL ,
	[parent_id] [int] NULL ,
	[short_description] [text] NULL ,
	[long_description] [text] NULL ,
	[allow_customer_configure] [bit] NOT NULL ,
	[allow_user_configure] [bit] NOT NULL ,
	[required] [bit] NOT NULL ,
	[start_date] [datetime] NULL ,
	[end_date] [datetime] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [package] (
	[package_id] [int] IDENTITY (1, 1) NOT NULL ,
	[category_id] [int] NULL ,
	[package_name] [varchar] (255) NOT NULL ,
	[abbreviation] [varchar] (30) NULL ,
	[short_description] [text] NULL ,
	[long_description] [text] NULL ,
	[thumbnail_image_id] [int] NULL ,
	[small_image_id] [int] NULL ,
	[large_image_id] [int] NULL ,
	[list_order] [int] NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[start_date] [datetime] NULL ,
	[expiration_date] [datetime] NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [product_catalog_category_map] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[product_id] [int] NOT NULL ,
	[category_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [product_catalog_pricing] (
	[price_id] [int] IDENTITY (1, 1) NOT NULL ,
	[product_id] [int] NULL ,
	[tax_id] [int] NULL ,
	[msrp_currency] [int] NULL ,
	[msrp_amount] [float] NOT NULL ,
	[price_currency] [int] NULL ,
	[price_amount] [float] NOT NULL ,
	[recurring_currency] [int] NULL ,
	[recurring_amount] [float] NOT NULL ,
	[recurring_type] [int] NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[start_date] [datetime] NULL ,
	[expiration_date] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [product_category_map] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[category1_id] [int] NOT NULL ,
	[category2_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [product_keyword_map] (
	[product_id] [int] NOT NULL ,
	[keyword_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [product_option_boolean] (
	[product_option_id] [int] NOT NULL ,
	[value] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [product_option_float] (
	[product_option_id] [int] NOT NULL ,
	[value] [float] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [product_option_integer] (
	[product_option_id] [int] NOT NULL ,
	[value] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [product_option_text] (
	[product_option_id] [int] NOT NULL ,
	[value] [text] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [product_option_timestamp] (
	[product_option_id] [int] NOT NULL ,
	[value] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [product_option_values] (
	[value_id] [int] IDENTITY (1, 1) NOT NULL ,
	[option_id] [int] NOT NULL ,
	[result_id] [int] NOT NULL ,
	[description] [text] NULL ,
	[msrp_currency] [int] NULL ,
	[msrp_amount] [float] NOT NULL ,
	[price_currency] [int] NULL ,
	[price_amount] [float] NOT NULL ,
	[recurring_currency] [int] NULL ,
	[recurring_amount] [float] NOT NULL ,
	[recurring_type] [int] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [service_contract] (
	[contract_id] [int] IDENTITY (1, 1) NOT NULL ,
	[contract_number] [varchar] (30) NULL ,
	[account_id] [int] NOT NULL ,
	[initial_start_date] [datetime] NOT NULL ,
	[current_start_date] [datetime] NULL ,
	[current_end_date] [datetime] NULL ,
	[category] [int] NULL ,
	[type] [int] NULL ,
	[contact_id] [int] NULL ,
	[description] [text] NULL ,
	[contract_billing_notes] [text] NULL ,
	[response_time] [int] NULL ,
	[telephone_service_model] [int] NULL ,
	[onsite_service_model] [int] NULL ,
	[email_service_model] [int] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[enabled] [bit] NULL ,
	[contract_value] [float] NULL ,
	[total_hours_remaining] [float] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [asset] (
	[asset_id] [int] IDENTITY (1, 1) NOT NULL ,
	[account_id] [int] NULL ,
	[contract_id] [int] NULL ,
	[date_listed] [datetime] NULL ,
	[asset_tag] [varchar] (30) NULL ,
	[status] [int] NULL ,
	[location] [varchar] (256) NULL ,
	[level1] [int] NULL ,
	[level2] [int] NULL ,
	[level3] [int] NULL ,
	[vendor] [varchar] (30) NULL ,
	[manufacturer] [varchar] (30) NULL ,
	[serial_number] [varchar] (30) NULL ,
	[model_version] [varchar] (30) NULL ,
	[description] [text] NULL ,
	[expiration_date] [datetime] NULL ,
	[inclusions] [text] NULL ,
	[exclusions] [text] NULL ,
	[purchase_date] [datetime] NULL ,
	[po_number] [varchar] (30) NULL ,
	[purchased_from] [varchar] (30) NULL ,
	[contact_id] [int] NULL ,
	[notes] [text] NULL ,
	[response_time] [int] NULL ,
	[telephone_service_model] [int] NULL ,
	[onsite_service_model] [int] NULL ,
	[email_service_model] [int] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[enabled] [bit] NULL ,
	[purchase_cost] [float] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [package_products_map] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[package_id] [int] NOT NULL ,
	[product_id] [int] NOT NULL ,
	[description] [text] NULL ,
	[msrp_currency] [int] NULL ,
	[msrp_amount] [float] NOT NULL ,
	[price_currency] [int] NULL ,
	[price_amount] [float] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[start_date] [datetime] NULL ,
	[expiration_date] [datetime] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [product_option_map] (
	[product_option_id] [int] IDENTITY (1, 1) NOT NULL ,
	[product_id] [int] NOT NULL ,
	[option_id] [int] NOT NULL ,
	[value_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [service_contract_hours] (
	[history_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_contract_id] [int] NULL ,
	[adjustment_hours] [float] NULL ,
	[adjustment_reason] [int] NULL ,
	[adjustment_notes] [text] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [service_contract_products] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_contract_id] [int] NULL ,
	[link_product_id] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [customer_product] (
	[customer_product_id] [int] IDENTITY (1, 1) NOT NULL ,
	[org_id] [int] NOT NULL ,
	[order_id] [int] NULL ,
	[order_item_id] [int] NULL ,
	[description] [varchar] (2048) NULL ,
	[status_id] [int] NULL ,
	[status_date] [datetime] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [customer_product_history] (
	[history_id] [int] IDENTITY (1, 1) NOT NULL ,
	[customer_product_id] [int] NOT NULL ,
	[org_id] [int] NOT NULL ,
	[order_id] [int] NULL ,
	[product_start_date] [datetime] NULL ,
	[product_end_date] [datetime] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [order_address] (
	[address_id] [int] IDENTITY (1, 1) NOT NULL ,
	[order_id] [int] NOT NULL ,
	[address_type] [int] NULL ,
	[addrline1] [varchar] (300) NULL ,
	[addrline2] [varchar] (300) NULL ,
	[addrline3] [varchar] (300) NULL ,
	[city] [varchar] (300) NULL ,
	[state] [varchar] (300) NULL ,
	[country] [varchar] (300) NULL ,
	[postalcode] [varchar] (40) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [order_entry] (
	[order_id] [int] IDENTITY (1, 1) NOT NULL ,
	[parent_id] [int] NULL ,
	[org_id] [int] NOT NULL ,
	[quote_id] [int] NULL ,
	[sales_id] [int] NULL ,
	[orderedby] [int] NULL ,
	[billing_contact_id] [int] NULL ,
	[source_id] [int] NULL ,
	[grand_total] [float] NULL ,
	[status_id] [int] NULL ,
	[status_date] [datetime] NULL ,
	[contract_date] [datetime] NULL ,
	[expiration_date] [datetime] NULL ,
	[order_terms_id] [int] NULL ,
	[order_type_id] [int] NULL ,
	[description] [varchar] (2048) NULL ,
	[notes] [text] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [order_payment] (
	[payment_id] [int] IDENTITY (1, 1) NOT NULL ,
	[order_id] [int] NOT NULL ,
	[payment_method_id] [int] NOT NULL ,
	[payment_amount] [float] NULL ,
	[authorization_ref_number] [varchar] (30) NULL ,
	[authorization_code] [varchar] (30) NULL ,
	[authorization_date] [datetime] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [order_product] (
	[item_id] [int] IDENTITY (1, 1) NOT NULL ,
	[order_id] [int] NOT NULL ,
	[product_id] [int] NOT NULL ,
	[quantity] [int] NOT NULL ,
	[msrp_currency] [int] NULL ,
	[msrp_amount] [float] NOT NULL ,
	[price_currency] [int] NULL ,
	[price_amount] [float] NOT NULL ,
	[recurring_currency] [int] NULL ,
	[recurring_amount] [float] NOT NULL ,
	[recurring_type] [int] NULL ,
	[extended_price] [float] NOT NULL ,
	[total_price] [float] NOT NULL ,
	[status_id] [int] NULL ,
	[status_date] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [order_product_option_boolean] (
	[order_product_option_id] [int] NULL ,
	[value] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [order_product_option_float] (
	[order_product_option_id] [int] NULL ,
	[value] [float] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [order_product_option_integer] (
	[order_product_option_id] [int] NULL ,
	[value] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [order_product_option_text] (
	[order_product_option_id] [int] NULL ,
	[value] [text] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [order_product_option_timestamp] (
	[order_product_option_id] [int] NULL ,
	[value] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [order_product_options] (
	[order_product_option_id] [int] IDENTITY (1, 1) NOT NULL ,
	[item_id] [int] NOT NULL ,
	[product_option_id] [int] NOT NULL ,
	[quantity] [int] NOT NULL ,
	[price_currency] [int] NULL ,
	[price_amount] [float] NOT NULL ,
	[recurring_currency] [int] NULL ,
	[recurring_amount] [float] NOT NULL ,
	[recurring_type] [int] NULL ,
	[extended_price] [float] NOT NULL ,
	[total_price] [float] NOT NULL ,
	[status_id] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [order_product_status] (
	[order_product_status_id] [int] IDENTITY (1, 1) NOT NULL ,
	[order_id] [int] NOT NULL ,
	[item_id] [int] NOT NULL ,
	[status_id] [int] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [payment_creditcard] (
	[creditcard_id] [int] IDENTITY (1, 1) NOT NULL ,
	[payment_id] [int] NOT NULL ,
	[card_type] [int] NULL ,
	[card_number] [varchar] (300) NULL ,
	[card_security_code] [varchar] (300) NULL ,
	[expiration_month] [int] NULL ,
	[expiration_year] [int] NULL ,
	[name_on_card] [varchar] (300) NULL ,
	[company_name_on_card] [varchar] (300) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [payment_eft] (
	[bank_id] [int] IDENTITY (1, 1) NOT NULL ,
	[payment_id] [int] NOT NULL ,
	[bank_name] [varchar] (300) NULL ,
	[routing_number] [varchar] (300) NULL ,
	[account_number] [varchar] (300) NULL ,
	[name_on_account] [varchar] (300) NULL ,
	[company_name_on_account] [varchar] (300) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [quote_entry] (
	[quote_id] [int] IDENTITY (1, 1) NOT NULL ,
	[parent_id] [int] NULL ,
	[org_id] [int] NOT NULL ,
	[contact_id] [int] NULL ,
	[source_id] [int] NULL ,
	[grand_total] [float] NULL ,
	[status_id] [int] NULL ,
	[status_date] [datetime] NULL ,
	[expiration_date] [datetime] NULL ,
	[quote_terms_id] [int] NULL ,
	[quote_type_id] [int] NULL ,
	[issued] [datetime] NULL ,
	[short_description] [text] NULL ,
	[notes] [text] NULL ,
	[ticketid] [int] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[product_id] [int] NULL ,
	[customer_product_id] [int] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [quote_notes] (
	[notes_id] [int] IDENTITY (1, 1) NOT NULL ,
	[quote_id] [int] NULL ,
	[notes] [text] NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [quote_product] (
	[item_id] [int] IDENTITY (1, 1) NOT NULL ,
	[quote_id] [int] NOT NULL ,
	[product_id] [int] NOT NULL ,
	[quantity] [int] NOT NULL ,
	[price_currency] [int] NULL ,
	[price_amount] [float] NOT NULL ,
	[recurring_currency] [int] NULL ,
	[recurring_amount] [float] NOT NULL ,
	[recurring_type] [int] NULL ,
	[extended_price] [float] NOT NULL ,
	[total_price] [float] NOT NULL ,
	[estimated_delivery_date] [datetime] NULL ,
	[status_id] [int] NULL ,
	[status_date] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [quote_product_option_boolean] (
	[quote_product_option_id] [int] NULL ,
	[value] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [quote_product_option_float] (
	[quote_product_option_id] [int] NULL ,
	[value] [float] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [quote_product_option_integer] (
	[quote_product_option_id] [int] NULL ,
	[value] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [quote_product_option_text] (
	[quote_product_option_id] [int] NULL ,
	[value] [text] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [quote_product_option_timestamp] (
	[quote_product_option_id] [int] NULL ,
	[value] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [quote_product_options] (
	[quote_product_option_id] [int] IDENTITY (1, 1) NOT NULL ,
	[item_id] [int] NOT NULL ,
	[product_option_id] [int] NOT NULL ,
	[quantity] [int] NOT NULL ,
	[price_currency] [int] NULL ,
	[price_amount] [float] NOT NULL ,
	[recurring_currency] [int] NULL ,
	[recurring_amount] [float] NOT NULL ,
	[recurring_type] [int] NULL ,
	[extended_price] [float] NOT NULL ,
	[total_price] [float] NOT NULL ,
	[status_id] [int] NULL 
) ON [PRIMARY]
GO

ALTER TABLE ticket ADD [link_contract_id] [int] NULL;
ALTER TABLE ticket ADD [link_asset_id] [int] NULL;
ALTER TABLE ticket ADD [product_id] [int] NULL;
ALTER TABLE ticket ADD [customer_product_id] [int] NULL;
ALTER TABLE ticket ADD [expectation] [int] NULL;

CREATE TABLE [ticket_csstm_form] (
	[form_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_ticket_id] [int] NULL ,
	[phone_response_time] [varchar] (10) NULL ,
	[engineer_response_time] [varchar] (10) NULL ,
	[follow_up_required] [bit] NULL ,
	[follow_up_description] [varchar] (2048) NULL ,
	[alert_date] [datetime] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[enabled] [bit] NULL ,
	[travel_towards_sc] [bit] NULL ,
	[labor_towards_sc] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [ticket_activity_item] (
	[item_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_form_id] [int] NULL ,
	[activity_date] [datetime] NULL ,
	[description] [text] NULL ,
	[travel_hours] [int] NULL ,
	[travel_minutes] [int] NULL ,
	[labor_hours] [int] NULL ,
	[labor_minutes] [int] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [ticket_sun_form] (
	[form_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_ticket_id] [int] NULL ,
	[description_of_service] [text] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [trouble_asset_replacement] (
	[replacement_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_form_id] [int] NULL ,
	[part_number] [varchar] (256) NULL ,
	[part_description] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [asset_category] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [asset_category_draft] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_asset_status] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_creditcard_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_currency] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_email_model] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_hours_reason] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_onsite_model] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_order_source] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_order_status] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_order_terms] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_order_type] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_orderaddress_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_payment_methods] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_phone_model] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_product_category_type] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_product_conf_result] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_product_format] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_product_keyword] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_product_ship_time] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_product_shipping] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_product_tax] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_product_type] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_quote_source] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_quote_status] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_quote_terms] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_quote_type] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_recurring_type] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_response_model] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_sc_category] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_sc_type] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [category_editor_lookup] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [import] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[import_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [product_option_configurator] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[configurator_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [product_catalog] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[product_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [product_category] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[category_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [product_option] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[option_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [package] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[package_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [product_catalog_category_map] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [product_catalog_pricing] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[price_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [product_category_map] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [product_option_values] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[value_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [service_contract] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[contract_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [asset] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[asset_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [package_products_map] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [product_option_map] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[product_option_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [service_contract_hours] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[history_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [service_contract_products] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [customer_product] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[customer_product_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [customer_product_history] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[history_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [order_address] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[address_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [order_entry] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[order_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [order_payment] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[payment_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [order_product] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[item_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [order_product_options] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[order_product_option_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [order_product_status] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[order_product_status_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [payment_creditcard] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[creditcard_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [payment_eft] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[bank_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [quote_entry] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[quote_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [quote_notes] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[notes_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [quote_product] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[item_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [quote_product_options] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[quote_product_option_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [ticket_activity_item] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[item_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [ticket_csstm_form] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[form_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [ticket_sun_form] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[form_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [trouble_asset_replacement] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[replacement_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [asset_category] WITH NOCHECK ADD 
	CONSTRAINT [DF__asset_cat__cat_l__542C7691] DEFAULT (0) FOR [cat_level],
	CONSTRAINT [DF__asset_cat__full___55209ACA] DEFAULT ('') FOR [full_description],
	CONSTRAINT [DF__asset_cat__defau__5614BF03] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__asset_cat__level__5708E33C] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__asset_cat__enabl__57FD0775] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [asset_category_draft] WITH NOCHECK ADD 
	CONSTRAINT [DF__asset_cat__link___5AD97420] DEFAULT ((-1)) FOR [link_id],
	CONSTRAINT [DF__asset_cat__cat_l__5BCD9859] DEFAULT (0) FOR [cat_level],
	CONSTRAINT [DF__asset_cat__full___5CC1BC92] DEFAULT ('') FOR [full_description],
	CONSTRAINT [DF__asset_cat__defau__5DB5E0CB] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__asset_cat__level__5EAA0504] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__asset_cat__enabl__5F9E293D] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_asset_status] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_as__defau__1BE81D6E] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_as__enabl__1CDC41A7] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_creditcard_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_cr__defau__7ADC2F5E] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_cr__level__7BD05397] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_cr__enabl__7CC477D0] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_currency] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_cu__defau__7226EDCC] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_cu__level__731B1205] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_cu__enabl__740F363E] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_email_model] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_em__defau__32CB82C6] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_em__enabl__33BFA6FF] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_hours_reason] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_ho__defau__369C13AA] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_ho__enabl__379037E3] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_onsite_model] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_on__defau__2EFAF1E2] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_on__enabl__2FEF161B] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_order_source] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_or__defau__25077354] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_or__level__25FB978D] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_or__enabl__26EFBBC6] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_order_status] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_or__defau__16B953FD] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_or__level__17AD7836] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_or__enabl__18A19C6F] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_order_terms] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_or__defau__2042BE37] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_or__level__2136E270] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_or__enabl__222B06A9] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_order_type] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_or__defau__1B7E091A] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_or__level__1C722D53] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_or__enabl__1D66518C] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_orderaddress_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_or__defau__69B1A35C] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_or__level__6AA5C795] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_or__enabl__6B99EBCE] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_payment_methods] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pa__defau__76177A41] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pa__level__770B9E7A] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pa__enabl__77FFC2B3] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_phone_model] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_ph__defau__2B2A60FE] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_ph__enabl__2C1E8537] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_product_category_type] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__76EBA2E9] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__77DFC722] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__78D3EB5B] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_product_conf_result] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__6B44E613] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__6C390A4C] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__6D2D2E85] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_product_format] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__1293BD5E] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__1387E197] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__147C05D0] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_product_keyword] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__1446FBA6] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__153B1FDF] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__162F4418] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_product_ship_time] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__1C1D2798] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__1D114BD1] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__1E05700A] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_product_shipping] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__1758727B] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__184C96B4] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__1940BAED] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_product_tax] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__20E1DCB5] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__21D600EE] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__22CA2527] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_product_type] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__0DCF0841] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__0EC32C7A] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__0FB750B3] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_quote_source] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_qu__defau__5E74FADA] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_qu__level__5F691F13] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_qu__enabl__605D434C] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_quote_status] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_qu__defau__5026DB83] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_qu__level__511AFFBC] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_qu__enabl__520F23F5] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_quote_terms] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_qu__defau__59B045BD] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_qu__level__5AA469F6] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_qu__enabl__5B988E2F] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_quote_type] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_qu__defau__54EB90A0] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_qu__level__55DFB4D9] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_qu__enabl__56D3D912] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_recurring_type] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_re__defau__25A691D2] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_re__level__269AB60B] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_re__enabl__278EDA44] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_response_model] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_re__defau__2759D01A] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_re__enabl__284DF453] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_sc_category] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_sc__defau__1FB8AE52] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_sc__enabl__20ACD28B] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_sc_type] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_sc__defau__23893F36] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_sc__enabl__247D636F] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [permission_category] WITH NOCHECK ADD 
	CONSTRAINT [DF__permissio__produ__03F0984C] DEFAULT (0) FOR [products]
GO

ALTER TABLE [category_editor_lookup] WITH NOCHECK ADD 
	CONSTRAINT [DF__category___level__793DFFAF] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__category___enter__7A3223E8] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [import] WITH NOCHECK ADD 
	CONSTRAINT [DF__import__entered__4589517F] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__import__modified__477199F1] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [product_catalog] WITH NOCHECK ADD 
	CONSTRAINT [DF__product_c__in_st__2C538F61] DEFAULT (1) FOR [in_stock],
	CONSTRAINT [DF__product_c__list___33008CF0] DEFAULT (10) FOR [list_order],
	CONSTRAINT [DF__product_c__enter__34E8D562] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__product_c__modif__36D11DD4] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__product_c__start__37C5420D] DEFAULT (null) FOR [start_date],
	CONSTRAINT [DF__product_c__expir__38B96646] DEFAULT (null) FOR [expiration_date],
	CONSTRAINT [DF__product_c__enabl__39AD8A7F] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [product_category] WITH NOCHECK ADD 
	CONSTRAINT [DF__product_c__list___00750D23] DEFAULT (10) FOR [list_order],
	CONSTRAINT [DF__product_c__enter__025D5595] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__product_c__modif__04459E07] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__product_c__start__0539C240] DEFAULT (null) FOR [start_date],
	CONSTRAINT [DF__product_c__expir__062DE679] DEFAULT (null) FOR [expiration_date],
	CONSTRAINT [DF__product_c__enabl__07220AB2] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [product_option] WITH NOCHECK ADD 
	CONSTRAINT [DF__product_o__allow__74CE504D] DEFAULT (0) FOR [allow_customer_configure],
	CONSTRAINT [DF__product_o__allow__75C27486] DEFAULT (0) FOR [allow_user_configure],
	CONSTRAINT [DF__product_o__requi__76B698BF] DEFAULT (0) FOR [required],
	CONSTRAINT [DF__product_o__start__77AABCF8] DEFAULT (null) FOR [start_date],
	CONSTRAINT [DF__product_o__end_d__789EE131] DEFAULT (null) FOR [end_date],
	CONSTRAINT [DF__product_o__enabl__7993056A] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [contact_emailaddress] WITH NOCHECK ADD 
	CONSTRAINT [DF__contact_e__prima__4A8310C6] DEFAULT (0) FOR [primary_email]
GO

ALTER TABLE [package] WITH NOCHECK ADD 
	CONSTRAINT [DF__package__list_or__5090EFD7] DEFAULT (10) FOR [list_order],
	CONSTRAINT [DF__package__entered__52793849] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__package__modifie__546180BB] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__package__start_d__5555A4F4] DEFAULT (null) FOR [start_date],
	CONSTRAINT [DF__package__expirat__5649C92D] DEFAULT (null) FOR [expiration_date],
	CONSTRAINT [DF__package__enabled__573DED66] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [product_catalog_pricing] WITH NOCHECK ADD 
	CONSTRAINT [DF__product_c__msrp___3F6663D5] DEFAULT (0) FOR [msrp_amount],
	CONSTRAINT [DF__product_c__price__414EAC47] DEFAULT (0) FOR [price_amount],
	CONSTRAINT [DF__product_c__recur__4336F4B9] DEFAULT (0) FOR [recurring_amount],
	CONSTRAINT [DF__product_c__enter__46136164] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__product_c__modif__47FBA9D6] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__product_c__start__48EFCE0F] DEFAULT (null) FOR [start_date],
	CONSTRAINT [DF__product_c__expir__49E3F248] DEFAULT (null) FOR [expiration_date]
GO

ALTER TABLE [product_option_values] WITH NOCHECK ADD 
	CONSTRAINT [DF__product_o__msrp___7E57BA87] DEFAULT (0) FOR [msrp_amount],
	CONSTRAINT [DF__product_o__price__004002F9] DEFAULT (0) FOR [price_amount],
	CONSTRAINT [DF__product_o__recur__02284B6B] DEFAULT (0) FOR [recurring_amount]
GO

ALTER TABLE [service_contract] WITH NOCHECK ADD 
	CONSTRAINT [DF__service_c__enter__420DC656] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__service_c__modif__43F60EC8] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__service_c__enabl__45DE573A] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [asset] WITH NOCHECK ADD 
	CONSTRAINT [DF__asset__entered__6C040022] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__asset__modified__6DEC4894] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__asset__enabled__6FD49106] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [package_products_map] WITH NOCHECK ADD 
	CONSTRAINT [DF__package_p__msrp___5CF6C6BC] DEFAULT (0) FOR [msrp_amount],
	CONSTRAINT [DF__package_p__price__5EDF0F2E] DEFAULT (0) FOR [price_amount],
	CONSTRAINT [DF__package_p__enter__60C757A0] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__package_p__modif__62AFA012] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__package_p__start__63A3C44B] DEFAULT (null) FOR [start_date],
	CONSTRAINT [DF__package_p__expir__6497E884] DEFAULT (null) FOR [expiration_date]
GO

ALTER TABLE [service_contract_hours] WITH NOCHECK ADD 
	CONSTRAINT [DF__service_c__enter__4AA30C57] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__service_c__modif__4C8B54C9] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [customer_product] WITH NOCHECK ADD 
	CONSTRAINT [DF__customer___statu__1960B67E] DEFAULT (getdate()) FOR [status_date],
	CONSTRAINT [DF__customer___enter__1A54DAB7] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__customer___modif__1C3D2329] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__customer___enabl__1E256B9B] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [customer_product_history] WITH NOCHECK ADD 
	CONSTRAINT [DF__customer___enter__23DE44F1] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__customer___modif__25C68D63] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [order_address] WITH NOCHECK ADD 
	CONSTRAINT [DF__order_add__enter__705EA0EB] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__order_add__modif__7246E95D] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [order_entry] WITH NOCHECK ADD 
	CONSTRAINT [DF__order_ent__statu__316D4A39] DEFAULT (getdate()) FOR [status_date],
	CONSTRAINT [DF__order_ent__contr__32616E72] DEFAULT (null) FOR [contract_date],
	CONSTRAINT [DF__order_ent__expir__335592AB] DEFAULT (null) FOR [expiration_date],
	CONSTRAINT [DF__order_ent__enter__3631FF56] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__order_ent__modif__381A47C8] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [order_payment] WITH NOCHECK ADD 
	CONSTRAINT [DF__order_pay__enter__01892CED] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__order_pay__modif__0371755F] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [order_product] WITH NOCHECK ADD 
	CONSTRAINT [DF__order_pro__quant__3DD3211E] DEFAULT (0) FOR [quantity],
	CONSTRAINT [DF__order_pro__msrp___3FBB6990] DEFAULT (0) FOR [msrp_amount],
	CONSTRAINT [DF__order_pro__price__41A3B202] DEFAULT (0) FOR [price_amount],
	CONSTRAINT [DF__order_pro__recur__438BFA74] DEFAULT (0) FOR [recurring_amount],
	CONSTRAINT [DF__order_pro__exten__457442E6] DEFAULT (0) FOR [extended_price],
	CONSTRAINT [DF__order_pro__total__4668671F] DEFAULT (0) FOR [total_price],
	CONSTRAINT [DF__order_pro__statu__4850AF91] DEFAULT (getdate()) FOR [status_date]
GO

ALTER TABLE [order_product_options] WITH NOCHECK ADD 
	CONSTRAINT [DF__order_pro__quant__55AAAAAF] DEFAULT (0) FOR [quantity],
	CONSTRAINT [DF__order_pro__price__5792F321] DEFAULT (0) FOR [price_amount],
	CONSTRAINT [DF__order_pro__recur__597B3B93] DEFAULT (0) FOR [recurring_amount],
	CONSTRAINT [DF__order_pro__exten__5B638405] DEFAULT (0) FOR [extended_price],
	CONSTRAINT [DF__order_pro__total__5C57A83E] DEFAULT (0) FOR [total_price]
GO

ALTER TABLE [order_product_status] WITH NOCHECK ADD 
	CONSTRAINT [DF__order_pro__enter__4E0988E7] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__order_pro__modif__4FF1D159] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [payment_creditcard] WITH NOCHECK ADD 
	CONSTRAINT [DF__payment_c__enter__092A4EB5] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__payment_c__modif__0B129727] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [payment_eft] WITH NOCHECK ADD 
	CONSTRAINT [DF__payment_e__enter__0FD74C44] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__payment_e__modif__11BF94B6] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [quote_entry] WITH NOCHECK ADD 
	CONSTRAINT [DF__quote_ent__statu__67FE6514] DEFAULT (getdate()) FOR [status_date],
	CONSTRAINT [DF__quote_ent__expir__68F2894D] DEFAULT (null) FOR [expiration_date],
	CONSTRAINT [DF__quote_ent__issue__6BCEF5F8] DEFAULT (getdate()) FOR [issued],
	CONSTRAINT [DF__quote_ent__enter__6DB73E6A] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__quote_ent__modif__6F9F86DC] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [quote_notes] WITH NOCHECK ADD 
	CONSTRAINT [DF__quote_not__enter__25E688F4] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__quote_not__modif__27CED166] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [quote_product] WITH NOCHECK ADD 
	CONSTRAINT [DF__quote_pro__quant__75586032] DEFAULT (0) FOR [quantity],
	CONSTRAINT [DF__quote_pro__price__7740A8A4] DEFAULT (0) FOR [price_amount],
	CONSTRAINT [DF__quote_pro__recur__7928F116] DEFAULT (0) FOR [recurring_amount],
	CONSTRAINT [DF__quote_pro__exten__7B113988] DEFAULT (0) FOR [extended_price],
	CONSTRAINT [DF__quote_pro__total__7C055DC1] DEFAULT (0) FOR [total_price],
	CONSTRAINT [DF__quote_pro__statu__7DEDA633] DEFAULT (getdate()) FOR [status_date]
GO

ALTER TABLE [quote_product_options] WITH NOCHECK ADD 
	CONSTRAINT [DF__quote_pro__quant__02B25B50] DEFAULT (0) FOR [quantity],
	CONSTRAINT [DF__quote_pro__price__049AA3C2] DEFAULT (0) FOR [price_amount],
	CONSTRAINT [DF__quote_pro__recur__0682EC34] DEFAULT (0) FOR [recurring_amount],
	CONSTRAINT [DF__quote_pro__exten__086B34A6] DEFAULT (0) FOR [extended_price],
	CONSTRAINT [DF__quote_pro__total__095F58DF] DEFAULT (0) FOR [total_price]
GO

ALTER TABLE [ticket_csstm_form] WITH NOCHECK ADD 
	CONSTRAINT [DF__ticket_cs__follo__3943762B] DEFAULT (0) FOR [follow_up_required],
	CONSTRAINT [DF__ticket_cs__enter__3A379A64] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__ticket_cs__modif__3C1FE2D6] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__ticket_cs__enabl__3E082B48] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__ticket_cs__trave__3EFC4F81] DEFAULT (1) FOR [travel_towards_sc],
	CONSTRAINT [DF__ticket_cs__labor__3FF073BA] DEFAULT (1) FOR [labor_towards_sc]
GO

ALTER TABLE [ticket_sun_form] WITH NOCHECK ADD 
	CONSTRAINT [DF__ticket_su__enter__469D7149] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__ticket_su__modif__4885B9BB] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__ticket_su__enabl__4A6E022D] DEFAULT (1) FOR [enabled]
GO


-- Insert default lookup_asset_status
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_asset_status] ON
GO
INSERT [lookup_asset_status] ([code],[description],[default_item],[level],[enabled])VALUES(1,'In use',0,10,1)
INSERT [lookup_asset_status] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Not in use',0,20,1)
INSERT [lookup_asset_status] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Requires maintenance',0,30,1)
INSERT [lookup_asset_status] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Retired',0,40,1)

SET IDENTITY_INSERT [lookup_asset_status] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_sc_category
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_sc_category] ON
GO
INSERT [lookup_sc_category] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Consulting',0,10,1)
INSERT [lookup_sc_category] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Hardware Maintenance',0,20,1)
INSERT [lookup_sc_category] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Manufacturer''s Maintenance',0,30,1)
INSERT [lookup_sc_category] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Monitoring',0,40,1)
INSERT [lookup_sc_category] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Time and Materials',0,50,1)
INSERT [lookup_sc_category] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Warranty',0,60,1)

SET IDENTITY_INSERT [lookup_sc_category] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_response_model
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_response_model] ON
GO
INSERT [lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(1,'M-F 8AM-5PM 8 hours',0,10,1)
INSERT [lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(2,'M-F 8AM-5PM 6 hours',0,20,1)
INSERT [lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(3,'M-F 8AM-5PM 4 hours',0,30,1)
INSERT [lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(4,'M-F 8AM-5PM same day',0,40,1)
INSERT [lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(5,'M-F 8AM-5PM next business day',0,50,1)
INSERT [lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(6,'M-F 8AM-8PM 4 hours',0,60,1)
INSERT [lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(7,'M-F 8AM-8PM 2 hours',0,70,1)
INSERT [lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(8,'24x7 8 hours',0,80,1)
INSERT [lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(9,'24x7 4 hours',0,90,1)
INSERT [lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(10,'24x7 2 hours',0,100,1)
INSERT [lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(11,'No response time guaranteed',0,110,1)

SET IDENTITY_INSERT [lookup_response_model] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_phone_model
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_phone_model] ON
GO
INSERT [lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(1,'< 15 minutes',0,10,1)
INSERT [lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(2,'< 5 minutes',0,20,1)
INSERT [lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(3,'M-F 7AM-4PM',0,30,1)
INSERT [lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(4,'M-F 8AM-5PM',0,40,1)
INSERT [lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(5,'M-F 8AM-8PM',0,50,1)
INSERT [lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(6,'24x7',0,60,1)
INSERT [lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(7,'No phone support',0,70,1)

SET IDENTITY_INSERT [lookup_phone_model] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_onsite_model
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_onsite_model] ON
GO
INSERT [lookup_onsite_model] ([code],[description],[default_item],[level],[enabled])VALUES(1,'M-F 7AM-4PM',0,10,1)
INSERT [lookup_onsite_model] ([code],[description],[default_item],[level],[enabled])VALUES(2,'M-F 8AM-5PM',0,20,1)
INSERT [lookup_onsite_model] ([code],[description],[default_item],[level],[enabled])VALUES(3,'M-F 8AM-8PM',0,30,1)
INSERT [lookup_onsite_model] ([code],[description],[default_item],[level],[enabled])VALUES(4,'24x7',0,40,1)
INSERT [lookup_onsite_model] ([code],[description],[default_item],[level],[enabled])VALUES(5,'No onsite service',0,50,1)

SET IDENTITY_INSERT [lookup_onsite_model] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_email_model
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_email_model] ON
GO
INSERT [lookup_email_model] ([code],[description],[default_item],[level],[enabled])VALUES(1,'2 hours',0,10,1)
INSERT [lookup_email_model] ([code],[description],[default_item],[level],[enabled])VALUES(2,'4 hours',0,20,1)
INSERT [lookup_email_model] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Same day',0,30,1)
INSERT [lookup_email_model] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Next business day',0,40,1)

SET IDENTITY_INSERT [lookup_email_model] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_hours_reason
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_hours_reason] ON
GO
INSERT [lookup_hours_reason] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Purchase',0,10,1)
INSERT [lookup_hours_reason] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Renewal',0,20,1)
INSERT [lookup_hours_reason] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Correction',0,30,1)

SET IDENTITY_INSERT [lookup_hours_reason] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_quote_status
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_quote_status] ON
GO
INSERT [lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Incomplete',0,0,1)
INSERT [lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Pending internal approval',0,0,1)
INSERT [lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Approved internally',0,0,1)
INSERT [lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Unapproved internally',0,0,1)
INSERT [lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Pending customer acceptance',0,0,1)
INSERT [lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Accepted by customer',0,0,1)
INSERT [lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(7,'Rejected by customer',0,0,1)
INSERT [lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(8,'Changes requested by customer',0,0,1)
INSERT [lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(9,'Cancelled',0,0,1)
INSERT [lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(10,'Complete',0,0,1)

SET IDENTITY_INSERT [lookup_quote_status] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_order_status
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_order_status] ON
GO
INSERT [lookup_order_status] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Pending',0,0,1)
INSERT [lookup_order_status] ([code],[description],[default_item],[level],[enabled])VALUES(2,'In Progress',0,0,1)
INSERT [lookup_order_status] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Cancelled',0,0,1)
INSERT [lookup_order_status] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Rejected',0,0,1)
INSERT [lookup_order_status] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Complete',0,0,1)
INSERT [lookup_order_status] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Closed',0,0,1)

SET IDENTITY_INSERT [lookup_order_status] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_order_type
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_order_type] ON
GO
INSERT [lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(1,'New',0,0,1)
INSERT [lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Change',0,0,1)
INSERT [lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Upgrade',0,0,1)
INSERT [lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Downgrade',0,0,1)
INSERT [lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Disconnect',0,0,1)
INSERT [lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Move',0,0,1)
INSERT [lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(7,'Return',0,0,1)
INSERT [lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(8,'Suspend',0,0,1)
INSERT [lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(9,'Unsuspend',0,0,1)

SET IDENTITY_INSERT [lookup_order_type] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_orderaddress_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_orderaddress_types] ON
GO
INSERT [lookup_orderaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Billing',0,0,1)
INSERT [lookup_orderaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Shipping',0,0,1)

SET IDENTITY_INSERT [lookup_orderaddress_types] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_payment_methods
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_payment_methods] ON
GO
INSERT [lookup_payment_methods] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Cash',0,0,1)
INSERT [lookup_payment_methods] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Credit Card',0,0,1)
INSERT [lookup_payment_methods] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Personal Check',0,0,1)
INSERT [lookup_payment_methods] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Money Order',0,0,1)
INSERT [lookup_payment_methods] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Certified Check',0,0,1)

SET IDENTITY_INSERT [lookup_payment_methods] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_creditcard_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_creditcard_types] ON
GO
INSERT [lookup_creditcard_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Visa',0,0,1)
INSERT [lookup_creditcard_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Master Card',0,0,1)
INSERT [lookup_creditcard_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'American Express',0,0,1)
INSERT [lookup_creditcard_types] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Discover',0,0,1)

SET IDENTITY_INSERT [lookup_creditcard_types] OFF
GO
SET NOCOUNT OFF

INSERT INTO product_category (category_name, enteredby, modifiedby, enabled) VALUES ('Labor Categories', 0, 0, 1);

UPDATE sync_table SET object_key = 'id' WHERE element_name = 'customFieldRecord';

 CREATE  INDEX [import_entered_idx] ON [import]([entered]) ON [PRIMARY]
GO

 CREATE  INDEX [import_name_idx] ON [import]([name]) ON [PRIMARY]
GO

 CREATE  INDEX [contact_import_id_idx] ON [contact]([import_id]) ON [PRIMARY]
GO

 CREATE  UNIQUE  INDEX [idx_pr_key_map] ON [product_keyword_map]([product_id], [keyword_id]) ON [PRIMARY]
GO

 CREATE  UNIQUE  INDEX [idx_pr_opt_val] ON [product_option_values]([option_id], [result_id]) ON [PRIMARY]
GO

ALTER TABLE [category_editor_lookup] ADD 
	 FOREIGN KEY 
	(
		[module_id]
	) REFERENCES [permission_category] (
		[category_id]
	)
GO

ALTER TABLE [import] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [product_option_configurator] ADD 
	 FOREIGN KEY 
	(
		[result_type]
	) REFERENCES [lookup_product_conf_result] (
		[code]
	)
GO

ALTER TABLE [product_catalog] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[estimated_ship_time]
	) REFERENCES [lookup_product_ship_time] (
		[code]
	),
	 FOREIGN KEY 
	(
		[format_id]
	) REFERENCES [lookup_product_format] (
		[code]
	),
	 FOREIGN KEY 
	(
		[large_image_id]
	) REFERENCES [project_files] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[parent_id]
	) REFERENCES [product_catalog] (
		[product_id]
	),
	 FOREIGN KEY 
	(
		[shipping_id]
	) REFERENCES [lookup_product_shipping] (
		[code]
	),
	 FOREIGN KEY 
	(
		[small_image_id]
	) REFERENCES [project_files] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[thumbnail_image_id]
	) REFERENCES [project_files] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[type_id]
	) REFERENCES [lookup_product_type] (
		[code]
	)
GO

ALTER TABLE [product_category] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[large_image_id]
	) REFERENCES [project_files] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[parent_id]
	) REFERENCES [product_category] (
		[category_id]
	),
	 FOREIGN KEY 
	(
		[small_image_id]
	) REFERENCES [project_files] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[thumbnail_image_id]
	) REFERENCES [project_files] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[type_id]
	) REFERENCES [lookup_product_category_type] (
		[code]
	)
GO

ALTER TABLE [product_option] ADD 
	 FOREIGN KEY 
	(
		[configurator_id]
	) REFERENCES [product_option_configurator] (
		[configurator_id]
	),
	 FOREIGN KEY 
	(
		[parent_id]
	) REFERENCES [product_option] (
		[option_id]
	)
GO

ALTER TABLE [package] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [product_category] (
		[category_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[large_image_id]
	) REFERENCES [project_files] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[small_image_id]
	) REFERENCES [project_files] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[thumbnail_image_id]
	) REFERENCES [project_files] (
		[item_id]
	)
GO

ALTER TABLE [product_catalog_category_map] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [product_category] (
		[category_id]
	),
	 FOREIGN KEY 
	(
		[product_id]
	) REFERENCES [product_catalog] (
		[product_id]
	)
GO

ALTER TABLE [product_catalog_pricing] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[msrp_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[price_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[product_id]
	) REFERENCES [product_catalog] (
		[product_id]
	),
	 FOREIGN KEY 
	(
		[recurring_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[recurring_type]
	) REFERENCES [lookup_recurring_type] (
		[code]
	),
	 FOREIGN KEY 
	(
		[tax_id]
	) REFERENCES [lookup_product_tax] (
		[code]
	)
GO

ALTER TABLE [product_category_map] ADD 
	 FOREIGN KEY 
	(
		[category1_id]
	) REFERENCES [product_category] (
		[category_id]
	),
	 FOREIGN KEY 
	(
		[category2_id]
	) REFERENCES [product_category] (
		[category_id]
	)
GO

ALTER TABLE [product_keyword_map] ADD 
	 FOREIGN KEY 
	(
		[keyword_id]
	) REFERENCES [lookup_product_keyword] (
		[code]
	),
	 FOREIGN KEY 
	(
		[product_id]
	) REFERENCES [product_catalog] (
		[product_id]
	)
GO

ALTER TABLE [product_option_boolean] ADD 
	 FOREIGN KEY 
	(
		[product_option_id]
	) REFERENCES [product_option] (
		[option_id]
	)
GO

ALTER TABLE [product_option_float] ADD 
	 FOREIGN KEY 
	(
		[product_option_id]
	) REFERENCES [product_option] (
		[option_id]
	)
GO

ALTER TABLE [product_option_integer] ADD 
	 FOREIGN KEY 
	(
		[product_option_id]
	) REFERENCES [product_option] (
		[option_id]
	)
GO

ALTER TABLE [product_option_text] ADD 
	 FOREIGN KEY 
	(
		[product_option_id]
	) REFERENCES [product_option] (
		[option_id]
	)
GO

ALTER TABLE [product_option_timestamp] ADD 
	 FOREIGN KEY 
	(
		[product_option_id]
	) REFERENCES [product_option] (
		[option_id]
	)
GO

ALTER TABLE [product_option_values] ADD 
	 FOREIGN KEY 
	(
		[msrp_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[option_id]
	) REFERENCES [product_option] (
		[option_id]
	),
	 FOREIGN KEY 
	(
		[price_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[recurring_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[recurring_type]
	) REFERENCES [lookup_recurring_type] (
		[code]
	)
GO

ALTER TABLE [service_contract] ADD 
	 FOREIGN KEY 
	(
		[account_id]
	) REFERENCES [organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[category]
	) REFERENCES [lookup_sc_category] (
		[code]
	),
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[email_service_model]
	) REFERENCES [lookup_email_model] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[onsite_service_model]
	) REFERENCES [lookup_onsite_model] (
		[code]
	),
	 FOREIGN KEY 
	(
		[response_time]
	) REFERENCES [lookup_response_model] (
		[code]
	),
	 FOREIGN KEY 
	(
		[telephone_service_model]
	) REFERENCES [lookup_phone_model] (
		[code]
	),
	 FOREIGN KEY 
	(
		[type]
	) REFERENCES [lookup_sc_type] (
		[code]
	)
GO

ALTER TABLE [asset] ADD 
	 FOREIGN KEY 
	(
		[account_id]
	) REFERENCES [organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[contract_id]
	) REFERENCES [service_contract] (
		[contract_id]
	),
	 FOREIGN KEY 
	(
		[email_service_model]
	) REFERENCES [lookup_email_model] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[level1]
	) REFERENCES [asset_category] (
		[id]
	),
	 FOREIGN KEY 
	(
		[level2]
	) REFERENCES [asset_category] (
		[id]
	),
	 FOREIGN KEY 
	(
		[level3]
	) REFERENCES [asset_category] (
		[id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[onsite_service_model]
	) REFERENCES [lookup_onsite_model] (
		[code]
	),
	 FOREIGN KEY 
	(
		[response_time]
	) REFERENCES [lookup_response_model] (
		[code]
	),
	 FOREIGN KEY 
	(
		[telephone_service_model]
	) REFERENCES [lookup_phone_model] (
		[code]
	)
GO

ALTER TABLE [package_products_map] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[msrp_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[package_id]
	) REFERENCES [package] (
		[package_id]
	),
	 FOREIGN KEY 
	(
		[price_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[product_id]
	) REFERENCES [product_catalog] (
		[product_id]
	)
GO

ALTER TABLE [product_option_map] ADD 
	 FOREIGN KEY 
	(
		[option_id]
	) REFERENCES [product_option] (
		[option_id]
	),
	 FOREIGN KEY 
	(
		[product_id]
	) REFERENCES [product_catalog] (
		[product_id]
	),
	 FOREIGN KEY 
	(
		[value_id]
	) REFERENCES [product_option_values] (
		[value_id]
	)
GO

ALTER TABLE [service_contract_hours] ADD 
	 FOREIGN KEY 
	(
		[adjustment_reason]
	) REFERENCES [lookup_hours_reason] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[link_contract_id]
	) REFERENCES [service_contract] (
		[contract_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [service_contract_products] ADD 
	 FOREIGN KEY 
	(
		[link_contract_id]
	) REFERENCES [service_contract] (
		[contract_id]
	),
	 FOREIGN KEY 
	(
		[link_product_id]
	) REFERENCES [product_catalog] (
		[product_id]
	)
GO

ALTER TABLE [customer_product] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[order_id]
	) REFERENCES [order_entry] (
		[order_id]
	),
	 FOREIGN KEY 
	(
		[order_item_id]
	) REFERENCES [order_product] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[status_id]
	) REFERENCES [lookup_order_status] (
		[code]
	)
GO

ALTER TABLE [customer_product_history] ADD 
	 FOREIGN KEY 
	(
		[customer_product_id]
	) REFERENCES [customer_product] (
		[customer_product_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[order_id]
	) REFERENCES [order_entry] (
		[order_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	)
GO

ALTER TABLE [order_address] ADD 
	 FOREIGN KEY 
	(
		[address_type]
	) REFERENCES [lookup_orderaddress_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[order_id]
	) REFERENCES [order_entry] (
		[order_id]
	)
GO

ALTER TABLE [order_entry] ADD 
	 FOREIGN KEY 
	(
		[billing_contact_id]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[orderedby]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[order_terms_id]
	) REFERENCES [lookup_order_terms] (
		[code]
	),
	 FOREIGN KEY 
	(
		[order_type_id]
	) REFERENCES [lookup_order_type] (
		[code]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[parent_id]
	) REFERENCES [order_entry] (
		[order_id]
	),
	 FOREIGN KEY 
	(
		[quote_id]
	) REFERENCES [quote_entry] (
		[quote_id]
	),
	 FOREIGN KEY 
	(
		[sales_id]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[source_id]
	) REFERENCES [lookup_order_source] (
		[code]
	),
	 FOREIGN KEY 
	(
		[status_id]
	) REFERENCES [lookup_order_status] (
		[code]
	)
GO

ALTER TABLE [order_payment] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[order_id]
	) REFERENCES [order_entry] (
		[order_id]
	),
	 FOREIGN KEY 
	(
		[payment_method_id]
	) REFERENCES [lookup_payment_methods] (
		[code]
	)
GO

ALTER TABLE [order_product] ADD 
	 FOREIGN KEY 
	(
		[msrp_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[order_id]
	) REFERENCES [order_entry] (
		[order_id]
	),
	 FOREIGN KEY 
	(
		[price_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[product_id]
	) REFERENCES [product_catalog] (
		[product_id]
	),
	 FOREIGN KEY 
	(
		[recurring_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[recurring_type]
	) REFERENCES [lookup_recurring_type] (
		[code]
	),
	 FOREIGN KEY 
	(
		[status_id]
	) REFERENCES [lookup_order_status] (
		[code]
	)
GO

ALTER TABLE [order_product_option_boolean] ADD 
	 FOREIGN KEY 
	(
		[order_product_option_id]
	) REFERENCES [order_product_options] (
		[order_product_option_id]
	)
GO

ALTER TABLE [order_product_option_float] ADD 
	 FOREIGN KEY 
	(
		[order_product_option_id]
	) REFERENCES [order_product_options] (
		[order_product_option_id]
	)
GO

ALTER TABLE [order_product_option_integer] ADD 
	 FOREIGN KEY 
	(
		[order_product_option_id]
	) REFERENCES [order_product_options] (
		[order_product_option_id]
	)
GO

ALTER TABLE [order_product_option_text] ADD 
	 FOREIGN KEY 
	(
		[order_product_option_id]
	) REFERENCES [order_product_options] (
		[order_product_option_id]
	)
GO

ALTER TABLE [order_product_option_timestamp] ADD 
	 FOREIGN KEY 
	(
		[order_product_option_id]
	) REFERENCES [order_product_options] (
		[order_product_option_id]
	)
GO

ALTER TABLE [order_product_options] ADD 
	 FOREIGN KEY 
	(
		[item_id]
	) REFERENCES [order_product] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[price_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[product_option_id]
	) REFERENCES [product_option_map] (
		[product_option_id]
	),
	 FOREIGN KEY 
	(
		[recurring_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[recurring_type]
	) REFERENCES [lookup_recurring_type] (
		[code]
	),
	 FOREIGN KEY 
	(
		[status_id]
	) REFERENCES [lookup_order_status] (
		[code]
	)
GO

ALTER TABLE [order_product_status] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[item_id]
	) REFERENCES [order_product] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[order_id]
	) REFERENCES [order_entry] (
		[order_id]
	),
	 FOREIGN KEY 
	(
		[status_id]
	) REFERENCES [lookup_order_status] (
		[code]
	)
GO

ALTER TABLE [payment_creditcard] ADD 
	 FOREIGN KEY 
	(
		[card_type]
	) REFERENCES [lookup_creditcard_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[payment_id]
	) REFERENCES [order_payment] (
		[payment_id]
	)
GO

ALTER TABLE [payment_eft] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[payment_id]
	) REFERENCES [order_payment] (
		[payment_id]
	)
GO

ALTER TABLE [quote_entry] ADD 
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[customer_product_id]
	) REFERENCES [customer_product] (
		[customer_product_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[parent_id]
	) REFERENCES [quote_entry] (
		[quote_id]
	),
	 FOREIGN KEY 
	(
		[product_id]
	) REFERENCES [product_catalog] (
		[product_id]
	),
	 FOREIGN KEY 
	(
		[quote_terms_id]
	) REFERENCES [lookup_quote_terms] (
		[code]
	),
	 FOREIGN KEY 
	(
		[quote_type_id]
	) REFERENCES [lookup_quote_type] (
		[code]
	),
	 FOREIGN KEY 
	(
		[source_id]
	) REFERENCES [lookup_quote_source] (
		[code]
	),
	 FOREIGN KEY 
	(
		[status_id]
	) REFERENCES [lookup_quote_status] (
		[code]
	),
	 FOREIGN KEY 
	(
		[ticketid]
	) REFERENCES [ticket] (
		[ticketid]
	)
GO

ALTER TABLE [quote_notes] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[quote_id]
	) REFERENCES [quote_entry] (
		[quote_id]
	)
GO

ALTER TABLE [quote_product] ADD 
	 FOREIGN KEY 
	(
		[price_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[product_id]
	) REFERENCES [product_catalog] (
		[product_id]
	),
	 FOREIGN KEY 
	(
		[quote_id]
	) REFERENCES [quote_entry] (
		[quote_id]
	),
	 FOREIGN KEY 
	(
		[recurring_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[recurring_type]
	) REFERENCES [lookup_recurring_type] (
		[code]
	),
	 FOREIGN KEY 
	(
		[status_id]
	) REFERENCES [lookup_quote_status] (
		[code]
	)
GO

ALTER TABLE [quote_product_option_boolean] ADD 
	 FOREIGN KEY 
	(
		[quote_product_option_id]
	) REFERENCES [quote_product_options] (
		[quote_product_option_id]
	)
GO

ALTER TABLE [quote_product_option_float] ADD 
	 FOREIGN KEY 
	(
		[quote_product_option_id]
	) REFERENCES [quote_product_options] (
		[quote_product_option_id]
	)
GO

ALTER TABLE [quote_product_option_integer] ADD 
	 FOREIGN KEY 
	(
		[quote_product_option_id]
	) REFERENCES [quote_product_options] (
		[quote_product_option_id]
	)
GO

ALTER TABLE [quote_product_option_text] ADD 
	 FOREIGN KEY 
	(
		[quote_product_option_id]
	) REFERENCES [quote_product_options] (
		[quote_product_option_id]
	)
GO

ALTER TABLE [quote_product_option_timestamp] ADD 
	 FOREIGN KEY 
	(
		[quote_product_option_id]
	) REFERENCES [quote_product_options] (
		[quote_product_option_id]
	)
GO

ALTER TABLE [quote_product_options] ADD 
	 FOREIGN KEY 
	(
		[item_id]
	) REFERENCES [quote_product] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[price_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[product_option_id]
	) REFERENCES [product_option_map] (
		[product_option_id]
	),
	 FOREIGN KEY 
	(
		[recurring_currency]
	) REFERENCES [lookup_currency] (
		[code]
	),
	 FOREIGN KEY 
	(
		[recurring_type]
	) REFERENCES [lookup_recurring_type] (
		[code]
	),
	 FOREIGN KEY 
	(
		[status_id]
	) REFERENCES [lookup_quote_status] (
		[code]
	)
GO

ALTER TABLE [ticket] ADD 
	 FOREIGN KEY 
	(
		[customer_product_id]
	) REFERENCES [customer_product] (
		[customer_product_id]
	),
	 FOREIGN KEY 
	(
		[cat_code]
	) REFERENCES [ticket_category] (
		[id]
	),
	 FOREIGN KEY 
	(
		[subcat_code1]
	) REFERENCES [ticket_category] (
		[id]
	),
	 FOREIGN KEY 
	(
		[subcat_code2]
	) REFERENCES [ticket_category] (
		[id]
	),
	 FOREIGN KEY 
	(
		[subcat_code3]
	) REFERENCES [ticket_category] (
		[id]
	),
	 FOREIGN KEY 
	(
		[link_asset_id]
	) REFERENCES [asset] (
		[asset_id]
	),
	 FOREIGN KEY 
	(
		[link_contract_id]
	) REFERENCES [service_contract] (
		[contract_id]
	),
	 FOREIGN KEY 
	(
		[product_id]
	) REFERENCES [product_catalog] (
		[product_id]
	)
GO

ALTER TABLE [ticket_activity_item] ADD 
	 FOREIGN KEY 
	(
		[link_form_id]
	) REFERENCES [ticket_csstm_form] (
		[form_id]
	)
GO

ALTER TABLE [ticket_csstm_form] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[link_ticket_id]
	) REFERENCES [ticket] (
		[ticketid]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [ticket_sun_form] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[link_ticket_id]
	) REFERENCES [ticket] (
		[ticketid]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [trouble_asset_replacement] ADD 
	 FOREIGN KEY 
	(
		[link_form_id]
	) REFERENCES [ticket_sun_form] (
		[form_id]
	)
GO

DROP TABLE help_tips;
DROP TABLE help_notes;
DROP TABLE help_business_rules;
DROP TABLE help_faqs;
DROP TABLE help_related_links;
DROP TABLE help_features;
DROP TABLE lookup_help_features;
DROP TABLE help_tableofcontentitem_links;
DROP TABLE help_tableof_contents;
DROP TABLE help_contents;
DROP TABLE help_module;

CREATE TABLE [lookup_help_features] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (1000) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_module] (
	[module_id] [int] IDENTITY (1, 1) NOT NULL ,
	[category_id] [int] NULL ,
	[module_brief_description] [text] NULL ,
	[module_detail_description] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [help_tableof_contents] (
	[content_id] [int] IDENTITY (1, 1) NOT NULL ,
	[displaytext] [varchar] (255) NULL ,
	[firstchild] [int] NULL ,
	[nextsibling] [int] NULL ,
	[parent] [int] NULL ,
	[category_id] [int] NULL ,
	[contentlevel] [int] NOT NULL ,
	[contentorder] [int] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_contents] (
	[help_id] [int] IDENTITY (1, 1) NOT NULL ,
	[category_id] [int] NULL ,
	[link_module_id] [int] NULL ,
	[module] [varchar] (255) NULL ,
	[section] [varchar] (255) NULL ,
	[subsection] [varchar] (255) NULL ,
	[title] [varchar] (255) NULL ,
	[description] [text] NULL ,
	[nextcontent] [int] NULL ,
	[prevcontent] [int] NULL ,
	[upcontent] [int] NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [help_faqs] (
	[faq_id] [int] IDENTITY (1, 1) NOT NULL ,
	[owning_module_id] [int] NOT NULL ,
	[question] [varchar] (1000) NOT NULL ,
	[answer] [varchar] (1000) NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[completedate] [datetime] NULL ,
	[completedby] [int] NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_business_rules] (
	[rule_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_help_id] [int] NOT NULL ,
	[description] [varchar] (1000) NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[completedate] [datetime] NULL ,
	[completedby] [int] NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_features] (
	[feature_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_help_id] [int] NOT NULL ,
	[link_feature_id] [int] NULL ,
	[description] [varchar] (1000) NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[completedate] [datetime] NULL ,
	[completedby] [int] NULL ,
	[enabled] [bit] NOT NULL ,
	[level] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_notes] (
	[note_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_help_id] [int] NOT NULL ,
	[description] [varchar] (1000) NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[completedate] [datetime] NULL ,
	[completedby] [int] NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_related_links] (
	[relatedlink_id] [int] IDENTITY (1, 1) NOT NULL ,
	[owning_module_id] [int] NULL ,
	[linkto_content_id] [int] NULL ,
	[displaytext] [varchar] (255) NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_tableofcontentitem_links] (
	[link_id] [int] IDENTITY (1, 1) NOT NULL ,
	[global_link_id] [int] NOT NULL ,
	[linkto_content_id] [int] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_tips] (
	[tip_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_help_id] [int] NOT NULL ,
	[description] [varchar] (1000) NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE [lookup_help_features] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_module] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[module_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_tableof_contents] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[content_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_contents] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[help_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_faqs] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[faq_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_business_rules] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[rule_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_features] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[feature_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_notes] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[note_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_related_links] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[relatedlink_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_tableofcontentitem_links] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[link_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_tips] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[tip_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_help_features] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_he__defau__150615B5] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_he__level__15FA39EE] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_he__enabl__16EE5E27] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_tableof_contents] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_tabl__enter__06B7F65E] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_tabl__modif__08A03ED0] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_tabl__enabl__09946309] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_contents] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_cont__enter__7C3A67EB] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_cont__modif__7E22B05D] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_cont__enabl__7F16D496] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_faqs] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_faqs__enter__2EC5E7B8] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_faqs__modif__30AE302A] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_faqs__enabl__3296789C] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_business_rules] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_busi__enter__375B2DB9] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_busi__modif__3943762B] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_busi__enabl__3B2BBE9D] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_features] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_feat__enter__1CA7377D] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_feat__modif__1E8F7FEF] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_feat__enabl__2077C861] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__help_feat__level__216BEC9A] DEFAULT (0) FOR [level]
GO

ALTER TABLE [help_notes] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_note__enter__3FF073BA] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_note__modif__41D8BC2C] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_note__enabl__43C1049E] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_related_links] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_rela__enter__2724C5F0] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_rela__modif__290D0E62] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_rela__enabl__2A01329B] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_tableofcontentitem_links] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_tabl__enter__0F4D3C5F] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_tabl__modif__113584D1] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_tabl__enabl__1229A90A] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_tips] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_tips__enter__4885B9BB] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_tips__modif__4A6E022D] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_tips__enabl__4B622666] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_module] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [permission_category] (
		[category_id]
	)
GO

ALTER TABLE [help_tableof_contents] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [permission_category] (
		[category_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[firstchild]
	) REFERENCES [help_tableof_contents] (
		[content_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[nextsibling]
	) REFERENCES [help_tableof_contents] (
		[content_id]
	),
	 FOREIGN KEY 
	(
		[parent]
	) REFERENCES [help_tableof_contents] (
		[content_id]
	)
GO

ALTER TABLE [help_contents] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [permission_category] (
		[category_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[link_module_id]
	) REFERENCES [help_module] (
		[module_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[nextcontent]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[prevcontent]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[upcontent]
	) REFERENCES [help_contents] (
		[help_id]
	)
GO

ALTER TABLE [help_faqs] ADD 
	 FOREIGN KEY 
	(
		[completedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[owning_module_id]
	) REFERENCES [help_module] (
		[module_id]
	)
GO

ALTER TABLE [help_business_rules] ADD 
	 FOREIGN KEY 
	(
		[completedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[link_help_id]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [help_features] ADD 
	 FOREIGN KEY 
	(
		[completedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[link_help_id]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[link_feature_id]
	) REFERENCES [lookup_help_features] (
		[code]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [help_notes] ADD 
	 FOREIGN KEY 
	(
		[completedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[link_help_id]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [help_related_links] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[linkto_content_id]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[owning_module_id]
	) REFERENCES [help_module] (
		[module_id]
	)
GO

ALTER TABLE [help_tableofcontentitem_links] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[global_link_id]
	) REFERENCES [help_tableof_contents] (
		[content_id]
	),
	 FOREIGN KEY 
	(
		[linkto_content_id]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [help_tips] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[link_help_id]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

EXEC sp_rename '[project_team].[userLevel]','tmp_userlevel','COLUMN'
GO
EXEC sp_rename '[project_team].[tmp_userlevel]','userlevel','COLUMN'
GO

-- cleanup
DELETE FROM action_item_log WHERE item_id IN (SELECT item_id FROM action_item WHERE link_item_id NOT IN (SELECT contact_id FROM contact));
DELETE FROM action_item WHERE link_item_id NOT IN (SELECT contact_id FROM contact);
UPDATE contact SET employee = 1 WHERE employee = 0 AND org_id = 0;

INSERT [database_version] ([script_filename],[script_version])VALUES('mssql_2004-06-15.sql','2004-06-15');

