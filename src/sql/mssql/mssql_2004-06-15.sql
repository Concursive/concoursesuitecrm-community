-- Database upgrade v2.8 (2004-06-15)
-- NOT FINISHED YET
-- TODO: Compare with postgresql for anything that might have been missed

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

CREATE TABLE [lookup_delivery_options] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
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

ALTER TABLE permission_category ADD [products] [bit] NOT NULL;
ALTER TABLE sync_client ADD [code] [varchar] (255) NULL;

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

ALTER TABLE contact ADD [import_id] [int] NULL;

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


