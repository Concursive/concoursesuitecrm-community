-- Copyright (C) 2006 Concursive Corporation, all rights reserved

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [business_process_parameter_library](
	[parameter_id] [int] IDENTITY(1,1) NOT NULL,
	[component_id] [int] NULL,
	[param_name] [varchar](255) NULL,
	[description] [varchar](510) NULL,
	[default_value] [varchar](4000) NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[parameter_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_title](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_sc_category](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_quote_type](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_opportunity_event_compelling](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_sc_type](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket_priority](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[style] [text] NOT NULL DEFAULT (''),
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[description] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_quote_terms](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_opportunity_budget](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_response_model](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_task_priority](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_ticket_escalation](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[description] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_page_access_log](
	[page_id] [int] NULL,
	[site_log_id] [int] NULL,
	[entered] [datetime] NULL DEFAULT (getdate())
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_quote_source](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_product_access_log](
	[product_id] [int] NULL,
	[site_log_id] [int] NULL,
	[entered] [datetime] NULL DEFAULT (getdate())
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_phone_model](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_plan_constants](
	[map_id] [int] IDENTITY(1,1) NOT NULL,
	[constant_id] [int] NOT NULL,
	[description] [varchar](300) NULL,
PRIMARY KEY CLUSTERED 
(
	[map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [action_plan_constant_id] ON [action_plan_constants] 
(
	[constant_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_task_loe](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_product_email_log](
	[product_id] [int] NULL,
	[emails_to] [text] NOT NULL,
	[from_name] [varchar](300) NOT NULL,
	[comments] [varchar](1024) NULL,
	[site_log_id] [int] NULL,
	[entered] [datetime] NULL DEFAULT (getdate())
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [sites](
	[site_id] [int] IDENTITY(1,1) NOT NULL,
	[sitecode] [varchar](255) NOT NULL,
	[vhost] [varchar](255) NOT NULL DEFAULT (''),
	[dbhost] [varchar](255) NOT NULL DEFAULT (''),
	[dbname] [varchar](255) NOT NULL DEFAULT (''),
	[dbport] [int] NOT NULL DEFAULT ((1433)),
	[dbuser] [varchar](255) NOT NULL DEFAULT (''),
	[dbpw] [varchar](255) NOT NULL DEFAULT (''),
	[driver] [varchar](255) NOT NULL DEFAULT (''),
	[code] [varchar](255) NULL,
	[enabled] [bit] NOT NULL DEFAULT ((0)),
	[language] [varchar](11) NULL,
PRIMARY KEY CLUSTERED 
(
	[site_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_onsite_model](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_industry](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[order_id] [int] NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_task_category](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](255) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_email_model](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_orderaddress_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_ticket_task_category](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](255) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [events](
	[event_id] [int] IDENTITY(1,1) NOT NULL,
	[second] [varchar](64) NULL DEFAULT ('0'),
	[minute] [varchar](64) NULL DEFAULT ('*'),
	[hour] [varchar](64) NULL DEFAULT ('*'),
	[dayofmonth] [varchar](64) NULL DEFAULT ('*'),
	[month] [varchar](64) NULL DEFAULT ('*'),
	[dayofweek] [varchar](64) NULL DEFAULT ('*'),
	[year] [varchar](64) NULL DEFAULT ('*'),
	[task] [varchar](255) NULL,
	[extrainfo] [varchar](255) NULL,
	[businessDays] [varchar](6) NULL DEFAULT ('true'),
	[enabled] [bit] NULL DEFAULT ((0)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[event_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [usage_log](
	[usage_id] [int] IDENTITY(1,1) NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NULL,
	[action] [int] NOT NULL,
	[record_id] [int] NULL,
	[record_size] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[usage_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [sync_client](
	[client_id] [int] IDENTITY(1,1) NOT NULL,
	[type] [varchar](100) NULL,
	[version] [varchar](50) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[anchor] [datetime] NULL DEFAULT (NULL),
	[enabled] [bit] NULL DEFAULT ((0)),
	[code] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[client_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [field_types](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[data_typeid] [int] NOT NULL DEFAULT ((-1)),
	[data_type] [varchar](20) NULL,
	[operator] [varchar](50) NULL,
	[display_text] [varchar](50) NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_hours_reason](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_layout](
	[layout_id] [int] IDENTITY(1,1) NOT NULL,
	[layout_constant] [int] NULL,
	[layout_name] [varchar](300) NOT NULL,
	[jsp] [varchar](300) NULL,
	[thumbnail] [varchar](300) NULL,
	[custom] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[layout_id] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[layout_constant] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_asset_manufacturer](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [search_fields](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[field] [varchar](80) NULL,
	[description] [varchar](255) NULL,
	[searchable] [bit] NOT NULL DEFAULT ((1)),
	[field_typeid] [int] NOT NULL DEFAULT ((-1)),
	[table_name] [varchar](80) NULL,
	[object_class] [varchar](80) NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_asset_vendor](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [sync_system](
	[system_id] [int] IDENTITY(1,1) NOT NULL,
	[application_name] [varchar](255) NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[system_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [business_process_log](
	[process_name] [varchar](255) NOT NULL,
	[anchor] [datetime] NOT NULL,
UNIQUE NONCLUSTERED 
(
	[process_name] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_payment_methods](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_account_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_creditcard_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_project_permission_category](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[group_id] [int] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [state](
	[state_code] [char](2) NOT NULL,
	[state] [varchar](80) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[state_code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_department](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_duration_type](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_orgaddress_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_step_actions](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[constant_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[constant_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_orgemail_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [database_version](
	[version_id] [int] IDENTITY(1,1) NOT NULL,
	[script_filename] [varchar](255) NOT NULL,
	[script_version] [varchar](255) NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[version_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_orgphone_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_document_store_permission_category](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[group_id] [int] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [notification](
	[notification_id] [int] IDENTITY(1,1) NOT NULL,
	[notify_user] [int] NOT NULL,
	[module] [varchar](255) NOT NULL,
	[item_id] [int] NOT NULL,
	[item_modified] [datetime] NOT NULL DEFAULT (getdate()),
	[attempt] [datetime] NOT NULL DEFAULT (getdate()),
	[notify_type] [varchar](30) NULL,
	[subject] [text] NULL,
	[message] [text] NULL,
	[result] [int] NOT NULL,
	[errorMessage] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[notification_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_relationship_types](
	[type_id] [int] IDENTITY(1,1) NOT NULL,
	[category_id_maps_from] [int] NOT NULL,
	[category_id_maps_to] [int] NOT NULL,
	[reciprocal_name_1] [varchar](512) NULL,
	[reciprocal_name_2] [varchar](512) NULL,
	[level] [int] NULL DEFAULT ((0)),
	[default_item] [bit] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[type_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_im_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [autoguide_make](
	[make_id] [int] IDENTITY(1,1) NOT NULL,
	[make_name] [varchar](30) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[make_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_quote_delivery](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_currency](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_document_store_role](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[group_id] [int] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_im_services](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [permission_category](
	[category_id] [int] IDENTITY(1,1) NOT NULL,
	[category] [varchar](80) NULL,
	[description] [varchar](255) NULL,
	[level] [int] NOT NULL DEFAULT ((0)),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[active] [bit] NOT NULL DEFAULT ((1)),
	[folders] [bit] NOT NULL DEFAULT ((0)),
	[lookups] [bit] NOT NULL DEFAULT ((0)),
	[viewpoints] [bit] NULL DEFAULT ((0)),
	[categories] [bit] NOT NULL DEFAULT ((0)),
	[scheduled_events] [bit] NOT NULL DEFAULT ((0)),
	[object_events] [bit] NOT NULL DEFAULT ((0)),
	[reports] [bit] NOT NULL DEFAULT ((0)),
	[products] [bit] NOT NULL DEFAULT ((0)),
	[webdav] [bit] NOT NULL DEFAULT ((0)),
	[logos] [bit] NOT NULL DEFAULT ((0)),
	[constant] [int] NOT NULL,
	[action_plans] [bit] NOT NULL DEFAULT ((0)),
	[custom_list_views] [bit] NOT NULL DEFAULT ((0)),
	[importer] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[category_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_quote_condition](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_product_category_type](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_order_status](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_product_conf_result](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_contact_source](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_order_type](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_project_activity](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[group_id] [int] NOT NULL DEFAULT ((0)),
	[template_id] [int] NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [quote_group](
	[group_id] [int] IDENTITY(1000,1) NOT NULL,
	[unused] [char](1) NULL,
PRIMARY KEY CLUSTERED 
(
	[group_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_contact_rating](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_order_terms](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_project_priority](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[group_id] [int] NOT NULL DEFAULT ((0)),
	[graphic] [varchar](75) NULL,
	[type] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_payment_status](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_textmessage_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_order_source](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_survey_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [autoguide_options](
	[option_id] [int] IDENTITY(1,1) NOT NULL,
	[option_name] [varchar](20) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((0)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[option_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_employment_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_project_status](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[group_id] [int] NOT NULL DEFAULT ((0)),
	[graphic] [varchar](75) NULL,
	[type] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_locale](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_project_loe](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[base_value] [int] NOT NULL DEFAULT ((0)),
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[group_id] [int] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_product_type](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_call_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_contactaddress_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_icelet](
	[icelet_id] [int] IDENTITY(1,1) NOT NULL,
	[icelet_name] [varchar](300) NOT NULL,
	[icelet_description] [text] NULL,
	[icelet_configurator_class] [varchar](300) NOT NULL,
	[icelet_version] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[icelet_id] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[icelet_configurator_class] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_product_manufacturer](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_project_role](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[group_id] [int] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_help_features](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](1000) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_asset_materials](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_call_priority](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[weight] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_quote_remarks](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [autoguide_ad_run_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](20) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((0)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_contactemail_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_product_format](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_project_category](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](80) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[group_id] [int] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_call_reminder](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[base_value] [int] NOT NULL DEFAULT ((0)),
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_stage](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[order_id] [int] NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_contactphone_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_product_shipping](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_revenue_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_ticket_cause](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket_level](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[description] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_delivery_options](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](100) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_access_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[link_module_id] [int] NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
	[rule_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_news_template](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](255) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[group_id] [int] NOT NULL DEFAULT ((0)),
	[load_article] [bit] NULL DEFAULT ((0)),
	[load_project_article_list] [bit] NULL DEFAULT ((0)),
	[load_article_linked_list] [bit] NULL DEFAULT ((0)),
	[load_public_projects] [bit] NULL DEFAULT ((0)),
	[load_article_category_list] [bit] NULL DEFAULT ((0)),
	[mapped_jsp] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_revenuedetail_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_ticket_resolution](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_product_ship_time](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_segments](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket_severity](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[style] [text] NOT NULL DEFAULT (''),
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[description] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_product_tax](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_opportunity_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[order_id] [int] NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_account_size](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_product_keyword](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_ticketsource](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[description] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [business_process_component_library](
	[component_id] [int] IDENTITY(1,1) NOT NULL,
	[component_name] [varchar](255) NOT NULL,
	[type_id] [int] NOT NULL,
	[class_name] [varchar](255) NOT NULL,
	[description] [varchar](510) NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[component_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_ticket_state](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_opportunity_environment](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_recurring_type](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_site_id](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[short_description] [varchar](300) NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_quote_status](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_ticket_status](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[description] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_opportunity_competitors](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_asset_status](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_catalog](
	[product_id] [int] IDENTITY(1,1) NOT NULL,
	[parent_id] [int] NULL,
	[product_name] [varchar](255) NOT NULL,
	[abbreviation] [varchar](30) NULL,
	[short_description] [text] NULL,
	[long_description] [text] NULL,
	[type_id] [int] NULL,
	[special_notes] [varchar](255) NULL,
	[sku] [varchar](40) NULL,
	[in_stock] [bit] NOT NULL DEFAULT ((1)),
	[format_id] [int] NULL,
	[shipping_id] [int] NULL,
	[estimated_ship_time] [int] NULL,
	[thumbnail_image_id] [int] NULL,
	[small_image_id] [int] NULL,
	[large_image_id] [int] NULL,
	[list_order] [int] NULL DEFAULT ((10)),
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[start_date] [datetime] NULL DEFAULT (NULL),
	[expiration_date] [datetime] NULL DEFAULT (NULL),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[manufacturer_id] [int] NULL,
	[trashed_date] [datetime] NULL,
	[active] [bit] NOT NULL DEFAULT ((1)),
	[comments] [varchar](255) NULL DEFAULT (NULL),
	[import_id] [int] NULL,
	[status_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[product_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [portfolio_item](
	[item_id] [int] IDENTITY(1,1) NOT NULL,
	[item_name] [varchar](300) NOT NULL,
	[item_description] [text] NULL,
	[item_position_id] [int] NULL,
	[image_id] [int] NULL,
	[caption] [varchar](300) NULL,
	[portfolio_category_id] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[item_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [organization_emailaddress](
	[emailaddress_id] [int] IDENTITY(1,1) NOT NULL,
	[org_id] [int] NULL,
	[emailaddress_type] [int] NULL,
	[email] [varchar](256) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[primary_email] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[emailaddress_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [help_faqs](
	[faq_id] [int] IDENTITY(1,1) NOT NULL,
	[owning_module_id] [int] NOT NULL,
	[question] [varchar](1000) NOT NULL,
	[answer] [varchar](1000) NOT NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[completedate] [datetime] NULL,
	[completedby] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[faq_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_files_thumbnail](
	[item_id] [int] NULL,
	[filename] [varchar](255) NOT NULL,
	[size] [int] NULL DEFAULT ((0)),
	[version] [float] NULL DEFAULT ((0)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [order_product_status](
	[order_product_status_id] [int] IDENTITY(1,1) NOT NULL,
	[order_id] [int] NOT NULL,
	[item_id] [int] NOT NULL,
	[status_id] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[order_product_status_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [projects](
	[project_id] [int] IDENTITY(1,1) NOT NULL,
	[group_id] [int] NULL,
	[department_id] [int] NULL,
	[template_id] [int] NULL,
	[title] [varchar](100) NOT NULL,
	[shortDescription] [varchar](200) NOT NULL,
	[requestedBy] [varchar](50) NULL,
	[requestedDept] [varchar](50) NULL,
	[requestDate] [datetime] NULL DEFAULT (getdate()),
	[approvalDate] [datetime] NULL,
	[closeDate] [datetime] NULL,
	[owner] [int] NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL,
	[approvalBy] [int] NULL,
	[category_id] [int] NULL,
	[portal] [bit] NOT NULL DEFAULT ((0)),
	[allow_guests] [bit] NOT NULL DEFAULT ((0)),
	[news_enabled] [bit] NOT NULL DEFAULT ((1)),
	[details_enabled] [bit] NOT NULL DEFAULT ((1)),
	[team_enabled] [bit] NOT NULL DEFAULT ((1)),
	[plan_enabled] [bit] NOT NULL DEFAULT ((1)),
	[lists_enabled] [bit] NOT NULL DEFAULT ((1)),
	[discussion_enabled] [bit] NOT NULL DEFAULT ((1)),
	[tickets_enabled] [bit] NOT NULL DEFAULT ((1)),
	[documents_enabled] [bit] NOT NULL DEFAULT ((1)),
	[news_label] [varchar](50) NULL,
	[details_label] [varchar](50) NULL,
	[team_label] [varchar](50) NULL,
	[plan_label] [varchar](50) NULL,
	[lists_label] [varchar](50) NULL,
	[discussion_label] [varchar](50) NULL,
	[tickets_label] [varchar](50) NULL,
	[documents_label] [varchar](50) NULL,
	[est_closedate] [datetime] NULL,
	[budget] [float] NULL,
	[budget_currency] [varchar](5) NULL,
	[requestDate_timezone] [varchar](255) NULL,
	[est_closedate_timezone] [varchar](255) NULL,
	[portal_default] [bit] NOT NULL DEFAULT ((0)),
	[portal_header] [varchar](255) NULL,
	[portal_format] [varchar](255) NULL,
	[portal_key] [varchar](255) NULL,
	[portal_build_news_body] [bit] NOT NULL DEFAULT ((0)),
	[portal_news_menu] [bit] NOT NULL DEFAULT ((0)),
	[description] [text] NULL,
	[allows_user_observers] [bit] NOT NULL DEFAULT ((0)),
	[level] [int] NOT NULL DEFAULT ((10)),
	[portal_page_type] [int] NULL,
	[calendar_enabled] [bit] NOT NULL DEFAULT ((1)),
	[calendar_label] [varchar](50) NULL,
	[accounts_enabled] [bit] NOT NULL DEFAULT ((1)),
	[accounts_label] [varchar](50) NULL,
	[trashed_date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[project_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [projects_idx] ON [projects] 
(
	[group_id] ASC,
	[project_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [report](
	[report_id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NOT NULL,
	[permission_id] [int] NULL,
	[filename] [varchar](300) NOT NULL,
	[type] [int] NOT NULL DEFAULT ((1)),
	[title] [varchar](300) NOT NULL,
	[description] [varchar](1024) NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[custom] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[report_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_files_download](
	[item_id] [int] NOT NULL,
	[version] [float] NULL DEFAULT ((0)),
	[user_download_id] [int] NULL,
	[download_date] [datetime] NOT NULL DEFAULT (getdate())
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [organization_address](
	[address_id] [int] IDENTITY(1,1) NOT NULL,
	[org_id] [int] NULL,
	[address_type] [int] NULL,
	[addrline1] [varchar](80) NULL,
	[addrline2] [varchar](80) NULL,
	[addrline3] [varchar](80) NULL,
	[city] [varchar](80) NULL,
	[state] [varchar](80) NULL,
	[country] [varchar](80) NULL,
	[postalcode] [varchar](12) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[primary_address] [bit] NOT NULL DEFAULT ((0)),
	[addrline4] [varchar](80) NULL,
PRIMARY KEY CLUSTERED 
(
	[address_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [organization_address_postalcode_idx] ON [organization_address] 
(
	[postalcode] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [portfolio_category](
	[category_id] [int] IDENTITY(1,1) NOT NULL,
	[category_name] [varchar](300) NOT NULL,
	[category_description] [text] NULL,
	[category_position_id] [int] NULL,
	[parent_category_id] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[category_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [history](
	[history_id] [int] IDENTITY(1,1) NOT NULL,
	[contact_id] [int] NULL,
	[org_id] [int] NULL,
	[link_object_id] [int] NOT NULL,
	[link_item_id] [int] NULL,
	[status] [varchar](255) NULL,
	[type] [varchar](255) NULL,
	[description] [text] NULL,
	[level] [int] NULL DEFAULT ((10)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[history_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [revenue](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[org_id] [int] NULL,
	[transaction_id] [int] NULL DEFAULT ((-1)),
	[month] [int] NULL DEFAULT ((-1)),
	[year] [int] NULL DEFAULT ((-1)),
	[amount] [float] NULL DEFAULT ((0)),
	[type] [int] NULL,
	[owner] [int] NULL,
	[description] [varchar](255) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [help_related_links](
	[relatedlink_id] [int] IDENTITY(1,1) NOT NULL,
	[owning_module_id] [int] NULL,
	[linkto_content_id] [int] NULL,
	[displaytext] [varchar](255) NOT NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[relatedlink_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_icelet_property](
	[property_id] [int] IDENTITY(1,1) NOT NULL,
	[property_type_constant] [int] NULL,
	[property_value] [text] NULL,
	[row_column_id] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[property_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [active_survey](
	[active_survey_id] [int] IDENTITY(1,1) NOT NULL,
	[campaign_id] [int] NOT NULL,
	[name] [varchar](80) NOT NULL,
	[description] [varchar](255) NULL,
	[intro] [text] NULL,
	[outro] [text] NULL,
	[itemLength] [int] NULL DEFAULT ((-1)),
	[type] [int] NOT NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[active_survey_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_item_work_notes](
	[note_id] [int] IDENTITY(1,1) NOT NULL,
	[item_work_id] [int] NOT NULL,
	[description] [varchar](4096) NOT NULL,
	[submitted] [datetime] NOT NULL DEFAULT (getdate()),
	[submittedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[note_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [quote_notes](
	[notes_id] [int] IDENTITY(1,1) NOT NULL,
	[quote_id] [int] NULL,
	[notes] [text] NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[notes_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [document_store_department_member](
	[document_store_id] [int] NOT NULL,
	[item_id] [int] NOT NULL,
	[userlevel] [int] NOT NULL,
	[status] [int] NULL,
	[last_accessed] [datetime] NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[site_id] [int] NULL,
	[role_type] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_files_version](
	[item_id] [int] NULL,
	[client_filename] [varchar](255) NOT NULL,
	[filename] [varchar](255) NOT NULL,
	[subject] [varchar](500) NOT NULL,
	[size] [int] NULL DEFAULT ((0)),
	[version] [float] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[downloads] [int] NULL DEFAULT ((0)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL,
	[allow_portal_access] [bit] NULL DEFAULT ((0))
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [viewpoint](
	[viewpoint_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[vp_user_id] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[viewpoint_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [help_features](
	[feature_id] [int] IDENTITY(1,1) NOT NULL,
	[link_help_id] [int] NOT NULL,
	[link_feature_id] [int] NULL,
	[description] [varchar](1000) NOT NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[completedate] [datetime] NULL,
	[completedby] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[level] [int] NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[feature_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_row_column](
	[row_column_id] [int] IDENTITY(1,1) NOT NULL,
	[column_position] [int] NOT NULL,
	[width] [int] NULL,
	[page_row_id] [int] NULL,
	[icelet_id] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[row_column_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [document_store_role_member](
	[document_store_id] [int] NOT NULL,
	[item_id] [int] NOT NULL,
	[userlevel] [int] NOT NULL,
	[status] [int] NULL,
	[last_accessed] [datetime] NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[site_id] [int] NULL,
	[role_type] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_item_work](
	[item_work_id] [int] IDENTITY(1,1) NOT NULL,
	[phase_work_id] [int] NOT NULL,
	[action_step_id] [int] NOT NULL,
	[status_id] [int] NULL,
	[owner] [int] NULL,
	[start_date] [datetime] NULL,
	[end_date] [datetime] NULL,
	[link_module_id] [int] NULL,
	[link_item_id] [int] NULL,
	[level] [int] NULL DEFAULT ((0)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[item_work_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_files](
	[item_id] [int] IDENTITY(1,1) NOT NULL,
	[link_module_id] [int] NOT NULL,
	[link_item_id] [int] NOT NULL,
	[folder_id] [int] NULL,
	[client_filename] [varchar](255) NOT NULL,
	[filename] [varchar](255) NOT NULL,
	[subject] [varchar](500) NOT NULL,
	[size] [int] NULL DEFAULT ((0)),
	[version] [float] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[downloads] [int] NULL DEFAULT ((0)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL,
	[default_file] [bit] NULL DEFAULT ((0)),
	[allow_portal_access] [bit] NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[item_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [project_files_cidx] ON [project_files] 
(
	[link_module_id] ASC,
	[link_item_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [order_payment_status](
	[payment_status_id] [int] IDENTITY(1,1) NOT NULL,
	[payment_id] [int] NOT NULL,
	[status_id] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[payment_status_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket_sun_form](
	[form_id] [int] IDENTITY(1,1) NOT NULL,
	[link_ticket_id] [int] NULL,
	[description_of_service] [text] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[form_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [webdav](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NOT NULL,
	[class_name] [varchar](300) NOT NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [document_store_user_member](
	[document_store_id] [int] NOT NULL,
	[item_id] [int] NOT NULL,
	[userlevel] [int] NOT NULL,
	[status] [int] NULL,
	[last_accessed] [datetime] NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[site_id] [int] NULL,
	[role_type] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [survey](
	[survey_id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](80) NOT NULL,
	[description] [varchar](255) NULL,
	[intro] [text] NULL,
	[outro] [text] NULL,
	[itemLength] [int] NULL DEFAULT ((-1)),
	[type] [int] NULL DEFAULT ((-1)),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[status] [int] NOT NULL DEFAULT ((-1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[survey_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [help_tableofcontentitem_links](
	[link_id] [int] IDENTITY(1,1) NOT NULL,
	[global_link_id] [int] NOT NULL,
	[linkto_content_id] [int] NOT NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[link_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_phase_work](
	[phase_work_id] [int] IDENTITY(1,1) NOT NULL,
	[plan_work_id] [int] NOT NULL,
	[action_phase_id] [int] NOT NULL,
	[status_id] [int] NULL,
	[start_date] [datetime] NULL,
	[end_date] [datetime] NULL,
	[level] [int] NULL DEFAULT ((0)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[phase_work_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_folders](
	[folder_id] [int] IDENTITY(1,1) NOT NULL,
	[link_module_id] [int] NOT NULL,
	[link_item_id] [int] NOT NULL,
	[subject] [varchar](255) NOT NULL,
	[description] [text] NULL,
	[parent_id] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL,
	[display] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[folder_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_page_row](
	[page_row_id] [int] IDENTITY(1,1) NOT NULL,
	[row_position] [int] NOT NULL,
	[page_version_id] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[row_column_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[page_row_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_plan_work_notes](
	[note_id] [int] IDENTITY(1,1) NOT NULL,
	[plan_work_id] [int] NOT NULL,
	[description] [varchar](4096) NOT NULL,
	[submitted] [datetime] NOT NULL DEFAULT (getdate()),
	[submittedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[note_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [quotelog](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[quote_id] [int] NOT NULL,
	[source_id] [int] NULL,
	[status_id] [int] NULL,
	[terms_id] [int] NULL,
	[type_id] [int] NULL,
	[delivery_id] [int] NULL,
	[notes] [text] NULL,
	[grand_total] [float] NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[issued_date] [datetime] NULL,
	[submit_action] [int] NULL,
	[closed] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [order_payment](
	[payment_id] [int] IDENTITY(1,1) NOT NULL,
	[order_id] [int] NOT NULL,
	[payment_method_id] [int] NOT NULL,
	[payment_amount] [float] NULL,
	[authorization_ref_number] [varchar](30) NULL,
	[authorization_code] [varchar](30) NULL,
	[authorization_date] [datetime] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[order_item_id] [int] NULL,
	[history_id] [int] NULL,
	[date_to_process] [datetime] NULL,
	[creditcard_id] [int] NULL,
	[bank_id] [int] NULL,
	[status_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[payment_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [order_entry](
	[order_id] [int] IDENTITY(1,1) NOT NULL,
	[parent_id] [int] NULL,
	[org_id] [int] NOT NULL,
	[quote_id] [int] NULL,
	[sales_id] [int] NULL,
	[orderedby] [int] NULL,
	[billing_contact_id] [int] NULL,
	[source_id] [int] NULL,
	[grand_total] [float] NULL,
	[status_id] [int] NULL,
	[status_date] [datetime] NULL DEFAULT (getdate()),
	[contract_date] [datetime] NULL DEFAULT (NULL),
	[expiration_date] [datetime] NULL DEFAULT (NULL),
	[order_terms_id] [int] NULL,
	[order_type_id] [int] NULL,
	[description] [varchar](2048) NULL,
	[notes] [text] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[submitted] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[order_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_issue_replies](
	[reply_id] [int] IDENTITY(1,1) NOT NULL,
	[issue_id] [int] NOT NULL,
	[reply_to] [int] NULL DEFAULT ((0)),
	[subject] [varchar](255) NOT NULL,
	[message] [text] NOT NULL,
	[importance] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[reply_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [asset](
	[asset_id] [int] IDENTITY(1,1) NOT NULL,
	[account_id] [int] NULL,
	[contract_id] [int] NULL,
	[date_listed] [datetime] NULL,
	[asset_tag] [varchar](30) NULL,
	[status] [int] NULL,
	[location] [varchar](256) NULL,
	[level1] [int] NULL,
	[level2] [int] NULL,
	[level3] [int] NULL,
	[serial_number] [varchar](30) NULL,
	[model_version] [varchar](30) NULL,
	[description] [text] NULL,
	[expiration_date] [datetime] NULL,
	[inclusions] [text] NULL,
	[exclusions] [text] NULL,
	[purchase_date] [datetime] NULL,
	[po_number] [varchar](30) NULL,
	[purchased_from] [varchar](30) NULL,
	[contact_id] [int] NULL,
	[notes] [text] NULL,
	[response_time] [int] NULL,
	[telephone_service_model] [int] NULL,
	[onsite_service_model] [int] NULL,
	[email_service_model] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
	[purchase_cost] [float] NULL,
	[date_listed_timezone] [varchar](255) NULL,
	[expiration_date_timezone] [varchar](255) NULL,
	[purchase_date_timezone] [varchar](255) NULL,
	[trashed_date] [datetime] NULL,
	[parent_id] [int] NULL,
	[vendor_code] [int] NULL,
	[manufacturer_code] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[asset_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [document_store](
	[document_store_id] [int] IDENTITY(1,1) NOT NULL,
	[template_id] [int] NULL,
	[title] [varchar](100) NOT NULL,
	[shortDescription] [varchar](200) NOT NULL,
	[requestedBy] [varchar](50) NULL,
	[requestedDept] [varchar](50) NULL,
	[requestDate] [datetime] NULL DEFAULT (getdate()),
	[requestDate_timezone] [varchar](255) NULL,
	[approvalDate] [datetime] NULL,
	[approvalBy] [int] NULL,
	[closeDate] [datetime] NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL,
	[trashed_date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[document_store_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [help_tableof_contents](
	[content_id] [int] IDENTITY(1,1) NOT NULL,
	[displaytext] [varchar](255) NULL,
	[firstchild] [int] NULL,
	[nextsibling] [int] NULL,
	[parent] [int] NULL,
	[category_id] [int] NULL,
	[contentlevel] [int] NOT NULL,
	[contentorder] [int] NOT NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[content_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket_csstm_form](
	[form_id] [int] IDENTITY(1,1) NOT NULL,
	[link_ticket_id] [int] NULL,
	[phone_response_time] [varchar](10) NULL,
	[engineer_response_time] [varchar](10) NULL,
	[follow_up_required] [bit] NULL DEFAULT ((0)),
	[follow_up_description] [varchar](2048) NULL,
	[alert_date] [datetime] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
	[travel_towards_sc] [bit] NULL DEFAULT ((1)),
	[labor_towards_sc] [bit] NULL DEFAULT ((1)),
	[alert_date_timezone] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[form_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [user_group_map](
	[group_map_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[group_id] [int] NOT NULL,
	[level] [int] NOT NULL DEFAULT ((10)),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[group_map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_page](
	[page_id] [int] IDENTITY(1,1) NOT NULL,
	[page_name] [varchar](300) NULL,
	[page_position] [int] NOT NULL,
	[active_page_version_id] [int] NULL,
	[construction_page_version_id] [int] NULL,
	[page_group_id] [int] NULL,
	[tab_banner_id] [int] NULL,
	[notes] [text] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[page_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_category](
	[category_id] [int] IDENTITY(1,1) NOT NULL,
	[parent_id] [int] NULL,
	[category_name] [varchar](255) NOT NULL,
	[abbreviation] [varchar](30) NULL,
	[short_description] [text] NULL,
	[long_description] [text] NULL,
	[type_id] [int] NULL,
	[thumbnail_image_id] [int] NULL,
	[small_image_id] [int] NULL,
	[large_image_id] [int] NULL,
	[list_order] [int] NULL DEFAULT ((10)),
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[start_date] [datetime] NULL DEFAULT (NULL),
	[expiration_date] [datetime] NULL DEFAULT (NULL),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[import_id] [int] NULL,
	[status_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[category_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_plan_work](
	[plan_work_id] [int] IDENTITY(1,1) NOT NULL,
	[action_plan_id] [int] NOT NULL,
	[manager] [int] NULL,
	[assignedTo] [int] NOT NULL,
	[link_module_id] [int] NOT NULL,
	[link_item_id] [int] NOT NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[current_phase] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[plan_work_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cfsinbox_messagelink](
	[id] [int] NOT NULL,
	[sent_to] [int] NOT NULL,
	[status] [int] NOT NULL DEFAULT ((0)),
	[viewed] [datetime] NULL DEFAULT (NULL),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[sent_from] [int] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_tab_banner](
	[tab_banner_id] [int] IDENTITY(1,1) NOT NULL,
	[tab_id] [int] NULL,
	[image_id] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[tab_banner_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticketlog](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ticketid] [int] NULL,
	[assigned_to] [int] NULL,
	[comment] [text] NULL,
	[closed] [bit] NULL,
	[pri_code] [int] NULL,
	[level_code] [int] NULL,
	[department_code] [int] NULL,
	[cat_code] [int] NULL,
	[scode] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[escalation_code] [int] NULL,
	[state_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [customer_product_history](
	[history_id] [int] IDENTITY(1,1) NOT NULL,
	[customer_product_id] [int] NOT NULL,
	[org_id] [int] NOT NULL,
	[order_id] [int] NOT NULL,
	[product_start_date] [datetime] NULL,
	[product_end_date] [datetime] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[order_item_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[history_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [user_group](
	[group_id] [int] IDENTITY(1,1) NOT NULL,
	[group_name] [varchar](255) NOT NULL,
	[description] [text] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[site_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[group_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_issues](
	[issue_id] [int] IDENTITY(1,1) NOT NULL,
	[project_id] [int] NOT NULL,
	[subject] [varchar](255) NOT NULL,
	[message] [text] NOT NULL,
	[importance] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL,
	[category_id] [int] NULL,
	[reply_count] [int] NOT NULL DEFAULT ((0)),
	[last_reply_date] [datetime] NOT NULL DEFAULT (getdate()),
	[last_reply_by] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[issue_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [help_contents](
	[help_id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NULL,
	[link_module_id] [int] NULL,
	[module] [varchar](255) NULL,
	[section] [varchar](255) NULL,
	[subsection] [varchar](255) NULL,
	[title] [varchar](255) NULL,
	[description] [text] NULL,
	[nextcontent] [int] NULL,
	[prevcontent] [int] NULL,
	[upcontent] [int] NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[help_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [opportunity_component_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[component_id] [int] NULL,
	[header_id] [int] NULL,
	[description] [varchar](80) NULL,
	[closeprob] [float] NULL,
	[closedate] [datetime] NOT NULL,
	[terms] [float] NULL,
	[units] [char](1) NULL,
	[lowvalue] [float] NULL,
	[guessvalue] [float] NULL,
	[highvalue] [float] NULL,
	[stage] [int] NULL,
	[owner] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[closedate_timezone] [varchar](255) NULL,
	[closed] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_page_group](
	[page_group_id] [int] IDENTITY(1,1) NOT NULL,
	[group_name] [varchar](300) NULL,
	[internal_description] [text] NULL,
	[group_position] [int] NOT NULL,
	[tab_id] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[page_group_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cfsinbox_message](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[subject] [varchar](255) NULL DEFAULT (NULL),
	[body] [text] NOT NULL,
	[reply_id] [int] NOT NULL,
	[enteredby] [int] NOT NULL,
	[sent] [datetime] NOT NULL DEFAULT (getdate()),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[type] [int] NOT NULL DEFAULT ((-1)),
	[modifiedby] [int] NOT NULL,
	[delete_flag] [bit] NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [role](
	[role_id] [int] IDENTITY(1,1) NOT NULL,
	[role] [varchar](80) NOT NULL,
	[description] [varchar](255) NOT NULL DEFAULT (''),
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[role_type] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[role_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [customer_product](
	[customer_product_id] [int] IDENTITY(1,1) NOT NULL,
	[org_id] [int] NOT NULL,
	[order_id] [int] NULL,
	[order_item_id] [int] NULL,
	[description] [varchar](2048) NULL,
	[status_id] [int] NULL,
	[status_date] [datetime] NULL DEFAULT (getdate()),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[customer_product_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_issues_categories](
	[category_id] [int] IDENTITY(1,1) NOT NULL,
	[project_id] [int] NOT NULL,
	[subject] [varchar](255) NOT NULL,
	[description] [text] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL,
	[topics_count] [int] NOT NULL DEFAULT ((0)),
	[posts_count] [int] NOT NULL DEFAULT ((0)),
	[last_post_date] [datetime] NULL,
	[last_post_by] [int] NULL,
	[allow_files] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[category_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [contact_message](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[message_id] [int] NOT NULL,
	[received_date] [datetime] NOT NULL,
	[received_from] [int] NOT NULL,
	[received_by] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [payment_eft](
	[bank_id] [int] IDENTITY(1,1) NOT NULL,
	[bank_name] [varchar](300) NULL,
	[routing_number] [varchar](300) NULL,
	[account_number] [varchar](300) NULL,
	[name_on_account] [varchar](300) NULL,
	[company_name_on_account] [varchar](300) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[order_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[bank_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [contact_lead_read_map](
	[map_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[contact_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [contact_lead_read_c_idx] ON [contact_lead_read_map] 
(
	[contact_id] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [contact_lead_read_u_idx] ON [contact_lead_read_map] 
(
	[user_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_page_version](
	[page_version_id] [int] IDENTITY(1,1) NOT NULL,
	[version_number] [int] NOT NULL,
	[internal_description] [text] NULL,
	[notes] [text] NULL,
	[parent_page_version_id] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[page_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[page_version_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [package_products_map](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[package_id] [int] NOT NULL,
	[product_id] [int] NOT NULL,
	[description] [text] NULL,
	[msrp_currency] [int] NULL,
	[msrp_amount] [float] NOT NULL DEFAULT ((0)),
	[price_currency] [int] NULL,
	[price_amount] [float] NOT NULL DEFAULT ((0)),
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[start_date] [datetime] NULL DEFAULT (NULL),
	[expiration_date] [datetime] NULL DEFAULT (NULL),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [contact_textmessageaddress](
	[address_id] [int] IDENTITY(1,1) NOT NULL,
	[contact_id] [int] NULL,
	[textmessageaddress] [varchar](256) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[primary_textmessage_address] [bit] NOT NULL DEFAULT ((0)),
	[textmessageaddress_type] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[address_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [service_contract_hours](
	[history_id] [int] IDENTITY(1,1) NOT NULL,
	[link_contract_id] [int] NULL,
	[adjustment_hours] [float] NULL,
	[adjustment_reason] [int] NULL,
	[adjustment_notes] [text] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[history_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [contact_lead_skipped_map](
	[map_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[contact_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [contact_lead_skip_u_idx] ON [contact_lead_skipped_map] 
(
	[user_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_assignments_status](
	[status_id] [int] IDENTITY(1,1) NOT NULL,
	[assignment_id] [int] NOT NULL,
	[user_id] [int] NOT NULL,
	[description] [text] NOT NULL,
	[status_date] [datetime] NULL DEFAULT (getdate()),
	[percent_complete] [int] NULL,
	[project_status_id] [int] NULL,
	[user_assign_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[status_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_tab](
	[tab_id] [int] IDENTITY(1,1) NOT NULL,
	[display_text] [varchar](300) NOT NULL,
	[internal_description] [text] NULL,
	[site_id] [int] NULL,
	[tab_position] [int] NOT NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[tab_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [import](
	[import_id] [int] IDENTITY(1,1) NOT NULL,
	[type] [int] NOT NULL,
	[name] [varchar](250) NOT NULL,
	[description] [text] NULL,
	[source_type] [int] NULL,
	[source] [varchar](1000) NULL,
	[record_delimiter] [varchar](10) NULL,
	[column_delimiter] [varchar](10) NULL,
	[total_imported_records] [int] NULL,
	[total_failed_records] [int] NULL,
	[status_id] [int] NULL,
	[file_type] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[site_id] [int] NULL,
	[rating] [int] NULL,
	[comments] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[import_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [import_entered_idx] ON [import] 
(
	[entered] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [import_name_idx] ON [import] 
(
	[name] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket](
	[ticketid] [int] IDENTITY(1,1) NOT NULL,
	[org_id] [int] NULL,
	[contact_id] [int] NULL,
	[problem] [text] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[closed] [datetime] NULL,
	[pri_code] [int] NULL,
	[level_code] [int] NULL,
	[department_code] [int] NULL,
	[source_code] [int] NULL,
	[cat_code] [int] NULL,
	[subcat_code1] [int] NULL,
	[subcat_code2] [int] NULL,
	[subcat_code3] [int] NULL,
	[assigned_to] [int] NULL,
	[comment] [text] NULL,
	[solution] [text] NULL,
	[scode] [int] NULL,
	[critical] [datetime] NULL,
	[notified] [datetime] NULL,
	[custom_data] [text] NULL,
	[location] [varchar](256) NULL,
	[assigned_date] [datetime] NULL,
	[est_resolution_date] [datetime] NULL,
	[resolution_date] [datetime] NULL,
	[cause] [text] NULL,
	[link_contract_id] [int] NULL,
	[link_asset_id] [int] NULL,
	[product_id] [int] NULL,
	[customer_product_id] [int] NULL,
	[expectation] [int] NULL,
	[key_count] [int] NULL,
	[est_resolution_date_timezone] [varchar](255) NULL,
	[assigned_date_timezone] [varchar](255) NULL,
	[resolution_date_timezone] [varchar](255) NULL,
	[status_id] [int] NULL,
	[trashed_date] [datetime] NULL,
	[user_group_id] [int] NULL,
	[cause_id] [int] NULL,
	[resolution_id] [int] NULL,
	[defect_id] [int] NULL,
	[escalation_level] [int] NULL,
	[resolvable] [bit] NOT NULL DEFAULT ((1)),
	[resolvedby] [int] NULL,
	[resolvedby_department_code] [int] NULL,
	[state_id] [int] NULL,
	[site_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ticketid] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [ticket_cidx] ON [ticket] 
(
	[assigned_to] ASC,
	[closed] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [ticketlist_entered] ON [ticket] 
(
	[entered] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_site_log](
	[site_log_id] [int] IDENTITY(1,1) NOT NULL,
	[site_id] [int] NULL,
	[user_id] [int] NULL,
	[username] [varchar](80) NOT NULL,
	[ip] [varchar](80) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[browser] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[site_log_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [message_template](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](80) NOT NULL,
	[description] [varchar](255) NULL,
	[template_file] [varchar](80) NULL,
	[num_imgs] [int] NULL,
	[num_urls] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [payment_creditcard](
	[creditcard_id] [int] IDENTITY(1,1) NOT NULL,
	[card_type] [int] NULL,
	[card_number] [varchar](300) NULL,
	[card_security_code] [varchar](300) NULL,
	[expiration_month] [int] NULL,
	[expiration_year] [int] NULL,
	[name_on_card] [varchar](300) NULL,
	[company_name_on_card] [varchar](300) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[order_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[creditcard_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [call_log](
	[call_id] [int] IDENTITY(1,1) NOT NULL,
	[org_id] [int] NULL,
	[contact_id] [int] NULL,
	[opp_id] [int] NULL,
	[call_type_id] [int] NULL,
	[length] [int] NULL,
	[subject] [varchar](255) NULL,
	[notes] [text] NULL,
	[followup_date] [datetime] NULL,
	[alertdate] [datetime] NULL,
	[followup_notes] [text] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[alert] [varchar](255) NULL DEFAULT (NULL),
	[alert_call_type_id] [int] NULL,
	[parent_id] [int] NULL,
	[owner] [int] NULL,
	[assignedby] [int] NULL,
	[assign_date] [datetime] NULL DEFAULT (getdate()),
	[completedby] [int] NULL,
	[complete_date] [datetime] NULL,
	[result_id] [int] NULL,
	[priority_id] [int] NULL,
	[status_id] [int] NOT NULL DEFAULT ((1)),
	[reminder_value] [int] NULL,
	[reminder_type_id] [int] NULL,
	[alertdate_timezone] [varchar](255) NULL,
	[trashed_date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[call_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [call_contact_id_idx] ON [call_log] 
(
	[contact_id] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [call_log_cidx] ON [call_log] 
(
	[alertdate] ASC,
	[enteredby] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [call_log_entered_idx] ON [call_log] 
(
	[entered] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [call_opp_id_idx] ON [call_log] 
(
	[opp_id] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [call_org_id_idx] ON [call_log] 
(
	[org_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_item_log](
	[log_id] [int] IDENTITY(1,1) NOT NULL,
	[item_id] [int] NOT NULL,
	[link_item_id] [int] NULL DEFAULT ((-1)),
	[type] [int] NOT NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[log_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [package](
	[package_id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NULL,
	[package_name] [varchar](255) NOT NULL,
	[abbreviation] [varchar](30) NULL,
	[short_description] [text] NULL,
	[long_description] [text] NULL,
	[thumbnail_image_id] [int] NULL,
	[small_image_id] [int] NULL,
	[large_image_id] [int] NULL,
	[list_order] [int] NULL DEFAULT ((10)),
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[start_date] [datetime] NULL DEFAULT (NULL),
	[expiration_date] [datetime] NULL DEFAULT (NULL),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[package_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [contact_imaddress](
	[address_id] [int] IDENTITY(1,1) NOT NULL,
	[contact_id] [int] NULL,
	[imaddress_type] [int] NULL,
	[imaddress_service] [int] NULL,
	[imaddress] [varchar](256) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[primary_im] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[address_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [message](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](80) NOT NULL,
	[description] [varchar](255) NULL,
	[template_id] [int] NULL,
	[subject] [varchar](255) NULL DEFAULT (NULL),
	[body] [text] NULL,
	[reply_addr] [varchar](100) NULL,
	[url] [varchar](255) NULL,
	[img] [varchar](80) NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[access_type] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_site](
	[site_id] [int] IDENTITY(1,1) NOT NULL,
	[site_name] [varchar](300) NOT NULL,
	[internal_description] [text] NULL,
	[hit_count] [int] NULL,
	[notes] [text] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[layout_id] [int] NULL,
	[style_id] [int] NULL,
	[logo_image_id] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[site_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_assignments](
	[assignment_id] [int] IDENTITY(1,1) NOT NULL,
	[project_id] [int] NOT NULL,
	[requirement_id] [int] NULL,
	[assignedBy] [int] NULL,
	[user_assign_id] [int] NULL,
	[technology] [varchar](50) NULL,
	[role] [varchar](255) NULL,
	[estimated_loevalue] [int] NULL,
	[estimated_loetype] [int] NULL,
	[actual_loevalue] [int] NULL,
	[actual_loetype] [int] NULL,
	[priority_id] [int] NULL,
	[assign_date] [datetime] NULL DEFAULT (getdate()),
	[est_start_date] [datetime] NULL,
	[start_date] [datetime] NULL,
	[due_date] [datetime] NULL,
	[status_id] [int] NULL,
	[status_date] [datetime] NOT NULL DEFAULT (getdate()),
	[complete_date] [datetime] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL,
	[folder_id] [int] NULL,
	[percent_complete] [int] NULL,
	[due_date_timezone] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[assignment_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [proj_assign_req_id_idx] ON [project_assignments] 
(
	[requirement_id] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [project_assignments_cidx] ON [project_assignments] 
(
	[complete_date] ASC,
	[user_assign_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [service_contract](
	[contract_id] [int] IDENTITY(1,1) NOT NULL,
	[contract_number] [varchar](30) NULL,
	[account_id] [int] NOT NULL,
	[initial_start_date] [datetime] NOT NULL,
	[current_start_date] [datetime] NULL,
	[current_end_date] [datetime] NULL,
	[category] [int] NULL,
	[type] [int] NULL,
	[contact_id] [int] NULL,
	[description] [text] NULL,
	[contract_billing_notes] [text] NULL,
	[response_time] [int] NULL,
	[telephone_service_model] [int] NULL,
	[onsite_service_model] [int] NULL,
	[email_service_model] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
	[contract_value] [float] NULL,
	[total_hours_remaining] [float] NULL,
	[service_model_notes] [text] NULL,
	[initial_start_date_timezone] [varchar](255) NULL,
	[current_start_date_timezone] [varchar](255) NULL,
	[current_end_date_timezone] [varchar](255) NULL,
	[trashed_date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[contract_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [campaign](
	[campaign_id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](80) NOT NULL,
	[description] [varchar](255) NULL,
	[list_id] [int] NULL,
	[message_id] [int] NULL DEFAULT ((-1)),
	[reply_addr] [varchar](255) NULL DEFAULT (NULL),
	[subject] [varchar](255) NULL DEFAULT (NULL),
	[message] [text] NULL DEFAULT (NULL),
	[status_id] [int] NULL DEFAULT ((0)),
	[status] [varchar](255) NULL,
	[active] [bit] NULL DEFAULT ((0)),
	[active_date] [datetime] NULL DEFAULT (NULL),
	[send_method_id] [int] NOT NULL DEFAULT ((-1)),
	[inactive_date] [datetime] NULL DEFAULT (NULL),
	[approval_date] [datetime] NULL DEFAULT (NULL),
	[approvedby] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[type] [int] NULL DEFAULT ((1)),
	[active_date_timezone] [varchar](255) NULL,
	[cc] [varchar](1024) NULL,
	[bcc] [varchar](1024) NULL,
	[trashed_date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[campaign_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket_category_assignment](
	[map_id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NOT NULL,
	[department_id] [int] NULL,
	[assigned_to] [int] NULL,
	[group_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_item](
	[item_id] [int] IDENTITY(1,1) NOT NULL,
	[action_id] [int] NOT NULL,
	[link_item_id] [int] NOT NULL,
	[completedate] [datetime] NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[item_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_contact_types](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](50) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[user_id] [int] NULL,
	[category] [int] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket_category_draft_assignment](
	[map_id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NOT NULL,
	[department_id] [int] NULL,
	[assigned_to] [int] NULL,
	[group_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [contact_phone](
	[phone_id] [int] IDENTITY(1,1) NOT NULL,
	[contact_id] [int] NULL,
	[phone_type] [int] NULL,
	[number] [varchar](30) NULL,
	[extension] [varchar](10) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[primary_number] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[phone_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [contact_phone_contact_id_idx] ON [contact_phone] 
(
	[contact_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [task](
	[task_id] [int] IDENTITY(1,1) NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[priority] [int] NOT NULL,
	[description] [varchar](255) NULL,
	[duedate] [datetime] NULL,
	[reminderid] [int] NULL,
	[notes] [text] NULL,
	[sharing] [int] NOT NULL,
	[complete] [bit] NOT NULL DEFAULT ((0)),
	[enabled] [bit] NOT NULL DEFAULT ((0)),
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NULL,
	[estimatedloe] [float] NULL,
	[estimatedloetype] [int] NULL,
	[type] [int] NULL DEFAULT ((1)),
	[owner] [int] NULL,
	[completedate] [datetime] NULL,
	[category_id] [int] NULL,
	[duedate_timezone] [varchar](255) NULL,
	[trashed_date] [datetime] NULL,
	[ticket_task_category_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[task_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_list](
	[action_id] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](255) NOT NULL,
	[owner] [int] NOT NULL,
	[completedate] [datetime] NULL,
	[link_module_id] [int] NOT NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[action_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_assignments_folder](
	[folder_id] [int] IDENTITY(1,1) NOT NULL,
	[parent_id] [int] NULL,
	[requirement_id] [int] NOT NULL,
	[name] [varchar](255) NOT NULL,
	[description] [text] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[folder_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [opportunity_component](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[opp_id] [int] NULL,
	[owner] [int] NOT NULL,
	[description] [varchar](80) NULL,
	[closedate] [datetime] NOT NULL,
	[closeprob] [float] NULL,
	[terms] [float] NULL,
	[units] [char](1) NULL,
	[lowvalue] [float] NULL,
	[guessvalue] [float] NULL,
	[highvalue] [float] NULL,
	[stage] [int] NULL,
	[stagedate] [datetime] NOT NULL DEFAULT (getdate()),
	[commission] [float] NULL,
	[type] [char](1) NULL,
	[alertdate] [datetime] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[closed] [datetime] NULL,
	[alert] [varchar](100) NULL DEFAULT (NULL),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[notes] [text] NULL,
	[alertdate_timezone] [varchar](255) NULL,
	[closedate_timezone] [varchar](255) NULL,
	[trashed_date] [datetime] NULL,
	[environment] [int] NULL,
	[competitors] [int] NULL,
	[compelling_event] [int] NULL,
	[budget] [int] NULL,
	[status_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [oppcomplist_closedate] ON [opportunity_component] 
(
	[closedate] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [oppcomplist_description] ON [opportunity_component] 
(
	[description] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [oppcomplist_header_idx] ON [opportunity_component] 
(
	[opp_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [saved_criterialist](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[owner] [int] NOT NULL,
	[name] [varchar](80) NOT NULL,
	[contact_source] [int] NULL DEFAULT ((-1)),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [order_address](
	[address_id] [int] IDENTITY(1,1) NOT NULL,
	[order_id] [int] NOT NULL,
	[address_type] [int] NULL,
	[addrline1] [varchar](300) NULL,
	[addrline2] [varchar](300) NULL,
	[addrline3] [varchar](300) NULL,
	[city] [varchar](300) NULL,
	[state] [varchar](300) NULL,
	[country] [varchar](300) NULL,
	[postalcode] [varchar](40) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[address_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [contact](
	[contact_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NULL,
	[org_id] [int] NULL,
	[company] [varchar](255) NULL,
	[title] [varchar](80) NULL,
	[department] [int] NULL,
	[super] [int] NULL,
	[namesalutation] [varchar](80) NULL,
	[namelast] [varchar](80) NOT NULL,
	[namefirst] [varchar](80) NOT NULL,
	[namemiddle] [varchar](80) NULL,
	[namesuffix] [varchar](80) NULL,
	[assistant] [int] NULL,
	[birthdate] [datetime] NULL,
	[notes] [text] NULL,
	[site] [int] NULL,
	[locale] [int] NULL,
	[employee_id] [varchar](80) NULL,
	[employmenttype] [int] NULL,
	[startofday] [varchar](10) NULL,
	[endofday] [varchar](10) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
	[owner] [int] NULL,
	[custom1] [int] NULL DEFAULT ((-1)),
	[url] [varchar](100) NULL,
	[primary_contact] [bit] NULL DEFAULT ((0)),
	[employee] [bit] NULL DEFAULT ((0)),
	[org_name] [varchar](255) NULL,
	[access_type] [int] NULL,
	[status_id] [int] NULL,
	[import_id] [int] NULL,
	[information_update_date] [datetime] NULL DEFAULT (getdate()),
	[lead] [bit] NULL DEFAULT ((0)),
	[lead_status] [int] NULL,
	[source] [int] NULL,
	[rating] [int] NULL,
	[comments] [varchar](255) NULL,
	[conversion_date] [datetime] NULL,
	[additional_names] [varchar](255) NULL,
	[nickname] [varchar](80) NULL,
	[role] [varchar](255) NULL,
	[trashed_date] [datetime] NULL,
	[secret_word] [varchar](255) NULL,
	[account_number] [varchar](50) NULL,
	[revenue] [float] NULL,
	[industry_temp_code] [int] NULL,
	[potential] [float] NULL,
	[no_email] [bit] NULL DEFAULT ((0)),
	[no_mail] [bit] NULL DEFAULT ((0)),
	[no_phone] [bit] NULL DEFAULT ((0)),
	[no_textmessage] [bit] NULL DEFAULT ((0)),
	[no_im] [bit] NULL DEFAULT ((0)),
	[no_fax] [bit] NULL DEFAULT ((0)),
	[site_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[contact_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [contact_import_id_idx] ON [contact] 
(
	[import_id] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [contact_islead_idx] ON [contact] 
(
	[lead] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [contact_org_id_idx] ON [contact] 
(
	[org_id] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [contact_user_id_idx] ON [contact] 
(
	[user_id] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [contactlist_company] ON [contact] 
(
	[company] ASC,
	[namelast] ASC,
	[namefirst] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [contactlist_namecompany] ON [contact] 
(
	[namelast] ASC,
	[namefirst] ASC,
	[company] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [contact_emailaddress](
	[emailaddress_id] [int] IDENTITY(1,1) NOT NULL,
	[contact_id] [int] NULL,
	[emailaddress_type] [int] NULL,
	[email] [varchar](256) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[primary_email] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[emailaddress_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [contact_email_contact_id_idx] ON [contact_emailaddress] 
(
	[contact_id] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [contact_email_prim_idx] ON [contact_emailaddress] 
(
	[primary_email] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [access_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[username] [varchar](80) NOT NULL,
	[ip] [varchar](15) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[browser] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [help_tips](
	[tip_id] [int] IDENTITY(1,1) NOT NULL,
	[link_help_id] [int] NOT NULL,
	[description] [varchar](1000) NOT NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[tip_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_plan](
	[plan_id] [int] IDENTITY(1,1) NOT NULL,
	[plan_name] [varchar](255) NOT NULL,
	[description] [varchar](2048) NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[approved] [datetime] NULL DEFAULT (NULL),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[archive_date] [datetime] NULL,
	[cat_code] [int] NULL,
	[subcat_code1] [int] NULL,
	[subcat_code2] [int] NULL,
	[subcat_code3] [int] NULL,
	[link_object_id] [int] NULL,
	[site_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[plan_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_requirements](
	[requirement_id] [int] IDENTITY(1,1) NOT NULL,
	[project_id] [int] NOT NULL,
	[submittedBy] [varchar](50) NULL,
	[departmentBy] [varchar](30) NULL,
	[shortDescription] [varchar](255) NOT NULL,
	[description] [text] NOT NULL,
	[dateReceived] [datetime] NULL,
	[estimated_loevalue] [int] NULL,
	[estimated_loetype] [int] NULL,
	[actual_loevalue] [int] NULL,
	[actual_loetype] [int] NULL,
	[deadline] [datetime] NULL,
	[approvedBy] [int] NULL,
	[approvalDate] [datetime] NULL,
	[closedBy] [int] NULL,
	[closeDate] [datetime] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL,
	[startdate] [datetime] NULL,
	[startdate_timezone] [varchar](255) NULL,
	[deadline_timezone] [varchar](255) NULL,
	[due_date_timezone] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[requirement_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [custom_field_record](
	[link_module_id] [int] NOT NULL,
	[link_item_id] [int] NOT NULL,
	[category_id] [int] NOT NULL,
	[record_id] [int] IDENTITY(1,1) NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[record_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [custom_field_rec_idx] ON [custom_field_record] 
(
	[link_module_id] ASC,
	[link_item_id] ASC,
	[category_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [netapp_contractexpiration_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[expiration_id] [int] NULL,
	[quote_amount] [float] NULL,
	[quotegenerateddate] [datetime] NULL,
	[quoteaccepteddate] [datetime] NULL,
	[quoterejecteddate] [datetime] NULL,
	[comment] [text] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_catalog_pricing](
	[price_id] [int] IDENTITY(1,1) NOT NULL,
	[product_id] [int] NULL,
	[tax_id] [int] NULL,
	[msrp_currency] [int] NULL,
	[msrp_amount] [float] NOT NULL DEFAULT ((0)),
	[price_currency] [int] NULL,
	[price_amount] [float] NOT NULL DEFAULT ((0)),
	[recurring_currency] [int] NULL,
	[recurring_amount] [float] NOT NULL DEFAULT ((0)),
	[recurring_type] [int] NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[start_date] [datetime] NULL DEFAULT (NULL),
	[expiration_date] [datetime] NULL DEFAULT (NULL),
	[enabled] [bit] NULL DEFAULT ((0)),
	[cost_currency] [int] NULL,
	[cost_amount] [float] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[price_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [contact_address](
	[address_id] [int] IDENTITY(1,1) NOT NULL,
	[contact_id] [int] NULL,
	[address_type] [int] NULL,
	[addrline1] [varchar](80) NULL,
	[addrline2] [varchar](80) NULL,
	[addrline3] [varchar](80) NULL,
	[city] [varchar](80) NULL,
	[state] [varchar](80) NULL,
	[country] [varchar](80) NULL,
	[postalcode] [varchar](12) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[primary_address] [bit] NOT NULL DEFAULT ((0)),
	[addrline4] [varchar](80) NULL,
PRIMARY KEY CLUSTERED 
(
	[address_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [contact_address_contact_id_idx] ON [contact_address] 
(
	[contact_id] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [contact_address_postalcode_idx] ON [contact_address] 
(
	[postalcode] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [contact_address_prim_idx] ON [contact_address] 
(
	[primary_address] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [contact_city_idx] ON [contact_address] 
(
	[city] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [quote_entry](
	[quote_id] [int] IDENTITY(1,1) NOT NULL,
	[parent_id] [int] NULL,
	[org_id] [int] NULL,
	[contact_id] [int] NULL,
	[source_id] [int] NULL,
	[grand_total] [float] NULL,
	[status_id] [int] NULL,
	[status_date] [datetime] NULL DEFAULT (getdate()),
	[expiration_date] [datetime] NULL DEFAULT (NULL),
	[quote_terms_id] [int] NULL,
	[quote_type_id] [int] NULL,
	[issued] [datetime] NULL DEFAULT (getdate()),
	[short_description] [text] NULL,
	[notes] [text] NULL,
	[ticketid] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[product_id] [int] NULL,
	[customer_product_id] [int] NULL,
	[opp_id] [int] NULL,
	[version] [varchar](255) NOT NULL DEFAULT ('0'),
	[group_id] [int] NOT NULL,
	[delivery_id] [int] NULL,
	[email_address] [text] NULL,
	[phone_number] [text] NULL,
	[address] [text] NULL,
	[fax_number] [text] NULL,
	[submit_action] [int] NULL,
	[closed] [datetime] NULL,
	[show_total] [bit] NULL DEFAULT ((1)),
	[show_subtotal] [bit] NULL DEFAULT ((1)),
	[logo_file_id] [int] NULL,
	[trashed_date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[quote_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [report_queue](
	[queue_id] [int] IDENTITY(1,1) NOT NULL,
	[report_id] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[processed] [datetime] NULL DEFAULT (NULL),
	[status] [int] NOT NULL DEFAULT ((0)),
	[filename] [varchar](256) NULL,
	[filesize] [int] NULL DEFAULT ((-1)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[queue_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_news](
	[news_id] [int] IDENTITY(1,1) NOT NULL,
	[project_id] [int] NOT NULL,
	[category_id] [int] NULL,
	[subject] [varchar](255) NOT NULL,
	[intro] [text] NULL,
	[message] [text] NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[start_date] [datetime] NULL DEFAULT (getdate()),
	[end_date] [datetime] NULL DEFAULT (NULL),
	[allow_replies] [bit] NULL DEFAULT ((0)),
	[allow_rating] [bit] NULL DEFAULT ((0)),
	[rating_count] [int] NOT NULL DEFAULT ((0)),
	[avg_rating] [float] NULL DEFAULT ((0)),
	[priority_id] [int] NULL DEFAULT ((10)),
	[read_count] [int] NOT NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[status] [int] NULL DEFAULT (NULL),
	[html] [bit] NOT NULL DEFAULT ((1)),
	[start_date_timezone] [varchar](255) NULL,
	[end_date_timezone] [varchar](255) NULL,
	[classification_id] [int] NOT NULL,
	[template_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[news_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [netapp_contractexpiration](
	[expiration_id] [int] IDENTITY(1,1) NOT NULL,
	[serial_number] [varchar](255) NULL,
	[agreement_number] [varchar](255) NULL,
	[services] [varchar](1024) NULL,
	[startdate] [datetime] NULL,
	[enddate] [datetime] NULL,
	[installed_at_company_name] [varchar](1024) NULL,
	[installed_at_site_name] [varchar](1024) NULL,
	[group_name] [varchar](255) NULL,
	[product_number] [varchar](255) NULL,
	[system_name] [varchar](255) NULL,
	[operating_system] [varchar](255) NULL,
	[no_of_shelves] [int] NULL,
	[no_of_disks] [int] NULL,
	[nvram] [int] NULL,
	[memory] [int] NULL,
	[auto_support_status] [varchar](255) NULL,
	[installed_at_address] [varchar](1024) NULL,
	[city] [varchar](255) NULL,
	[state_province] [varchar](255) NULL,
	[postal_code] [varchar](255) NULL,
	[country] [varchar](255) NULL,
	[installed_at_contact_firstname] [varchar](255) NULL,
	[contact_lastname] [varchar](255) NULL,
	[contact_email] [varchar](255) NULL,
	[agreement_company] [varchar](255) NULL,
	[quote_amount] [float] NULL,
	[quotegenerateddate] [datetime] NULL,
	[quoteaccepteddate] [datetime] NULL,
	[quoterejecteddate] [datetime] NULL,
	[comment] [text] NULL,
	[import_id] [int] NULL,
	[status_id] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredBy] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedBy] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[expiration_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [opportunity_header](
	[opp_id] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](80) NULL,
	[acctlink] [int] NULL,
	[contactlink] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[trashed_date] [datetime] NULL,
	[access_type] [int] NOT NULL,
	[manager] [int] NOT NULL,
	[lock] [bit] NULL DEFAULT ((0)),
	[contact_org_id] [int] NULL,
	[custom1_integer] [int] NULL,
	[site_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[opp_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [opp_contactlink_idx] ON [opportunity_header] 
(
	[contactlink] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [opp_header_contact_org_id_idx] ON [opportunity_header] 
(
	[contact_org_id] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [oppheader_description_idx] ON [opportunity_header] 
(
	[description] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [help_notes](
	[note_id] [int] IDENTITY(1,1) NOT NULL,
	[link_help_id] [int] NOT NULL,
	[description] [varchar](1000) NOT NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[completedate] [datetime] NULL,
	[completedby] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[note_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_site_access_log](
	[site_log_id] [int] IDENTITY(1,1) NOT NULL,
	[site_id] [int] NULL,
	[user_id] [int] NULL,
	[ip] [varchar](300) NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
	[browser] [varchar](255) NULL,
	[referrer] [varchar](1024) NULL,
PRIMARY KEY CLUSTERED 
(
	[site_log_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [organization_phone](
	[phone_id] [int] IDENTITY(1,1) NOT NULL,
	[org_id] [int] NULL,
	[phone_type] [int] NULL,
	[number] [varchar](30) NULL,
	[extension] [varchar](10) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[primary_number] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[phone_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [knowledge_base](
	[kb_id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NULL,
	[title] [varchar](255) NOT NULL,
	[description] [text] NULL,
	[item_id] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[kb_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [report_criteria](
	[criteria_id] [int] IDENTITY(1,1) NOT NULL,
	[report_id] [int] NOT NULL,
	[owner] [int] NOT NULL,
	[subject] [varchar](512) NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[criteria_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [organization](
	[org_id] [int] IDENTITY(0,1) NOT NULL,
	[name] [varchar](80) NOT NULL,
	[account_number] [varchar](50) NULL,
	[account_group] [int] NULL,
	[url] [text] NULL,
	[revenue] [float] NULL,
	[employees] [int] NULL,
	[notes] [text] NULL,
	[sic_code] [varchar](40) NULL,
	[ticker_symbol] [varchar](10) NULL,
	[taxid] [char](80) NULL,
	[lead] [varchar](40) NULL,
	[sales_rep] [int] NOT NULL DEFAULT ((0)),
	[miner_only] [bit] NOT NULL DEFAULT ((0)),
	[defaultlocale] [int] NULL,
	[fiscalmonth] [int] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
	[industry_temp_code] [smallint] NULL,
	[owner] [int] NULL,
	[duplicate_id] [int] NULL DEFAULT ((-1)),
	[custom1] [int] NULL DEFAULT ((-1)),
	[custom2] [int] NULL DEFAULT ((-1)),
	[contract_end] [datetime] NULL,
	[alertdate] [datetime] NULL,
	[alert] [varchar](100) NULL,
	[custom_data] [text] NULL,
	[namesalutation] [varchar](80) NULL,
	[namelast] [varchar](80) NULL,
	[namefirst] [varchar](80) NULL,
	[namemiddle] [varchar](80) NULL,
	[namesuffix] [varchar](80) NULL,
	[import_id] [int] NULL,
	[status_id] [int] NULL,
	[alertdate_timezone] [varchar](255) NULL,
	[contract_end_timezone] [varchar](255) NULL,
	[trashed_date] [datetime] NULL,
	[source] [int] NULL,
	[rating] [int] NULL,
	[potential] [float] NULL,
	[segment_id] [int] NULL,
	[sub_segment_id] [int] NULL,
	[direct_bill] [bit] NULL DEFAULT ((0)),
	[account_size] [int] NULL,
	[site_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[org_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [orglist_name] ON [organization] 
(
	[name] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [help_business_rules](
	[rule_id] [int] IDENTITY(1,1) NOT NULL,
	[link_help_id] [int] NOT NULL,
	[description] [varchar](1000) NOT NULL,
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[completedate] [datetime] NULL,
	[completedby] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[rule_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_team](
	[project_id] [int] NOT NULL,
	[user_id] [int] NOT NULL,
	[userlevel] [int] NOT NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[status] [int] NULL,
	[last_accessed] [datetime] NULL,
	[role_type] [int] NULL
) ON [PRIMARY]

GO

CREATE UNIQUE NONCLUSTERED INDEX [project_team_uni_idx] ON [project_team] 
(
	[project_id] ASC,
	[user_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [revenue_detail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[revenue_id] [int] NULL,
	[amount] [float] NULL DEFAULT ((0)),
	[type] [int] NULL,
	[owner] [int] NULL,
	[description] [varchar](255) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [business_process_component_parameter](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[component_id] [int] NOT NULL,
	[parameter_id] [int] NOT NULL,
	[param_value] [varchar](4000) NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [order_product_option_timestamp](
	[order_product_option_id] [int] NULL,
	[value] [datetime] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [order_product_option_float](
	[order_product_option_id] [int] NULL,
	[value] [float] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [order_product_option_text](
	[order_product_option_id] [int] NULL,
	[value] [text] NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [order_product_option_integer](
	[order_product_option_id] [int] NULL,
	[value] [int] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [order_product_option_boolean](
	[order_product_option_id] [int] NULL,
	[value] [bit] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [report_criteria_parameter](
	[parameter_id] [int] IDENTITY(1,1) NOT NULL,
	[criteria_id] [int] NOT NULL,
	[parameter] [varchar](255) NOT NULL,
	[value] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[parameter_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [business_process_parameter](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[process_id] [int] NOT NULL,
	[param_name] [varchar](255) NULL,
	[param_value] [varchar](4000) NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [business_process_component](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[process_id] [int] NOT NULL,
	[component_id] [int] NOT NULL,
	[parent_id] [int] NULL,
	[parent_result_id] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [business_process_events](
	[event_id] [int] IDENTITY(1,1) NOT NULL,
	[second] [varchar](64) NULL DEFAULT ('0'),
	[minute] [varchar](64) NULL DEFAULT ('*'),
	[hour] [varchar](64) NULL DEFAULT ('*'),
	[dayofmonth] [varchar](64) NULL DEFAULT ('*'),
	[month] [varchar](64) NULL DEFAULT ('*'),
	[dayofweek] [varchar](64) NULL DEFAULT ('*'),
	[year] [varchar](64) NULL DEFAULT ('*'),
	[task] [varchar](255) NULL,
	[extrainfo] [varchar](255) NULL,
	[businessDays] [varchar](6) NULL DEFAULT ('true'),
	[enabled] [bit] NULL DEFAULT ((0)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[process_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[event_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [business_process_hook](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[trigger_id] [int] NOT NULL,
	[process_id] [int] NOT NULL,
	[enabled] [bit] NULL DEFAULT ((0)),
	[priority] [int] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_accounts](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[project_id] [int] NOT NULL,
	[org_id] [int] NOT NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [proj_acct_org_idx] ON [project_accounts] 
(
	[org_id] ASC
) ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX [proj_acct_project_idx] ON [project_accounts] 
(
	[project_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [autoguide_inventory](
	[inventory_id] [int] IDENTITY(1,1) NOT NULL,
	[vehicle_id] [int] NOT NULL,
	[account_id] [int] NULL,
	[vin] [varchar](20) NULL,
	[mileage] [varchar](20) NULL,
	[is_new] [bit] NULL DEFAULT ((0)),
	[condition] [varchar](20) NULL,
	[comments] [varchar](255) NULL,
	[stock_no] [varchar](20) NULL,
	[ext_color] [varchar](20) NULL,
	[int_color] [varchar](20) NULL,
	[style] [varchar](40) NULL,
	[invoice_price] [float] NULL,
	[selling_price] [float] NULL,
	[selling_price_text] [varchar](100) NULL,
	[sold] [bit] NULL DEFAULT ((0)),
	[status] [varchar](20) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[inventory_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [account_type_levels](
	[org_id] [int] NOT NULL,
	[type_id] [int] NOT NULL,
	[level] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modified] [datetime] NOT NULL DEFAULT (getdate())
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [news](
	[rec_id] [int] IDENTITY(1,1) NOT NULL,
	[org_id] [int] NULL,
	[url] [text] NULL,
	[base] [text] NULL,
	[headline] [text] NULL,
	[body] [text] NULL,
	[dateEntered] [datetime] NULL,
	[type] [char](1) NULL,
	[created] [datetime] NOT NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[rec_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [document_accounts](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[document_store_id] [int] NOT NULL,
	[org_id] [int] NOT NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [active_survey_answer_avg](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[question_id] [int] NOT NULL,
	[item_id] [int] NOT NULL,
	[total] [int] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [active_survey_answer_items](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[item_id] [int] NOT NULL,
	[answer_id] [int] NOT NULL,
	[comments] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [active_survey_answers](
	[answer_id] [int] IDENTITY(1,1) NOT NULL,
	[response_id] [int] NOT NULL,
	[question_id] [int] NOT NULL,
	[comments] [text] NULL,
	[quant_ans] [int] NULL DEFAULT ((-1)),
	[text_ans] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[answer_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_plan_editor_lookup](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[module_id] [int] NOT NULL,
	[constant_id] [int] NOT NULL,
	[level] [int] NULL DEFAULT ((0)),
	[description] [text] NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
	[category_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [step_action_map](
	[map_id] [int] IDENTITY(1,1) NOT NULL,
	[constant_id] [int] NOT NULL,
	[action_constant_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [report_queue_criteria](
	[criteria_id] [int] IDENTITY(1,1) NOT NULL,
	[queue_id] [int] NOT NULL,
	[parameter] [varchar](255) NOT NULL,
	[value] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[criteria_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [taskcategorylink_news](
	[news_id] [int] NOT NULL,
	[category_id] [int] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket_category_plan_map](
	[map_id] [int] IDENTITY(1,1) NOT NULL,
	[plan_id] [int] NOT NULL,
	[category_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [custom_field_data](
	[record_id] [int] NOT NULL,
	[field_id] [int] NOT NULL,
	[selected_item_id] [int] NULL DEFAULT ((0)),
	[entered_value] [text] NULL,
	[entered_number] [int] NULL,
	[entered_float] [float] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [custom_field_dat_idx] ON [custom_field_data] 
(
	[record_id] ASC,
	[field_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [quote_product](
	[item_id] [int] IDENTITY(1,1) NOT NULL,
	[quote_id] [int] NOT NULL,
	[product_id] [int] NOT NULL,
	[quantity] [int] NOT NULL DEFAULT ((0)),
	[price_currency] [int] NULL,
	[price_amount] [float] NOT NULL DEFAULT ((0)),
	[recurring_currency] [int] NULL,
	[recurring_amount] [float] NOT NULL DEFAULT ((0)),
	[recurring_type] [int] NULL,
	[extended_price] [float] NOT NULL DEFAULT ((0)),
	[total_price] [float] NOT NULL DEFAULT ((0)),
	[estimated_delivery_date] [datetime] NULL,
	[status_id] [int] NULL,
	[status_date] [datetime] NULL DEFAULT (getdate()),
	[estimated_delivery] [text] NULL,
	[comment] [varchar](300) NULL,
PRIMARY KEY CLUSTERED 
(
	[item_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [quote_remark](
	[map_id] [int] IDENTITY(1,1) NOT NULL,
	[quote_id] [int] NOT NULL,
	[remark_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [quote_condition](
	[map_id] [int] IDENTITY(1,1) NOT NULL,
	[quote_id] [int] NOT NULL,
	[condition_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_requirements_map](
	[map_id] [int] IDENTITY(1,1) NOT NULL,
	[project_id] [int] NOT NULL,
	[requirement_id] [int] NOT NULL,
	[position] [int] NOT NULL,
	[indent] [int] NOT NULL DEFAULT ((0)),
	[folder_id] [int] NULL,
	[assignment_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [proj_req_map_pr_req_pos_idx] ON [project_requirements_map] 
(
	[project_id] ASC,
	[requirement_id] ASC,
	[position] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [taskcategory_project](
	[category_id] [int] NOT NULL,
	[project_id] [int] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_phase](
	[phase_id] [int] IDENTITY(1,1) NOT NULL,
	[parent_id] [int] NULL,
	[plan_id] [int] NOT NULL,
	[phase_name] [varchar](255) NOT NULL,
	[description] [varchar](2048) NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[random] [bit] NULL DEFAULT ((0)),
	[global] [bit] NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[phase_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket_category_draft_plan_map](
	[map_id] [int] IDENTITY(1,1) NOT NULL,
	[plan_id] [int] NOT NULL,
	[category_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tasklink_ticket](
	[task_id] [int] NOT NULL,
	[ticket_id] [int] NOT NULL,
	[category_id] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tasklink_contact](
	[task_id] [int] NOT NULL,
	[contact_id] [int] NOT NULL,
	[notes] [text] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [scheduled_recipient](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[campaign_id] [int] NOT NULL,
	[contact_id] [int] NOT NULL,
	[run_id] [int] NULL DEFAULT ((-1)),
	[status_id] [int] NULL DEFAULT ((0)),
	[status] [varchar](255) NULL,
	[status_date] [datetime] NULL DEFAULT (getdate()),
	[scheduled_date] [datetime] NULL DEFAULT (getdate()),
	[sent_date] [datetime] NULL DEFAULT (NULL),
	[reply_date] [datetime] NULL DEFAULT (NULL),
	[bounce_date] [datetime] NULL DEFAULT (NULL),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [excluded_recipient](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[campaign_id] [int] NOT NULL,
	[contact_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [contact_type_levels](
	[contact_id] [int] NOT NULL,
	[type_id] [int] NOT NULL,
	[level] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modified] [datetime] NOT NULL DEFAULT (getdate())
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [opportunity_component_levels](
	[opp_id] [int] NOT NULL,
	[type_id] [int] NOT NULL,
	[level] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modified] [datetime] NOT NULL DEFAULT (getdate())
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [web_style](
	[style_id] [int] IDENTITY(1,1) NOT NULL,
	[style_constant] [int] NULL,
	[style_name] [varchar](300) NOT NULL,
	[css] [varchar](300) NULL,
	[thumbnail] [varchar](300) NULL,
	[custom] [bit] NOT NULL DEFAULT ((0)),
	[layout_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[style_id] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[style_constant] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [events_log](
	[log_id] [int] IDENTITY(1,1) NOT NULL,
	[event_id] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[status] [int] NULL,
	[message] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[log_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [sync_map](
	[client_id] [int] NOT NULL,
	[table_id] [int] NOT NULL,
	[record_id] [int] NOT NULL,
	[cuid] [int] NOT NULL,
	[complete] [bit] NULL DEFAULT ((0)),
	[status_date] [datetime] NULL
) ON [PRIMARY]

GO

CREATE UNIQUE NONCLUSTERED INDEX [idx_sync_map] ON [sync_map] 
(
	[client_id] ASC,
	[table_id] ASC,
	[record_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [sync_conflict_log](
	[client_id] [int] NOT NULL,
	[table_id] [int] NOT NULL,
	[record_id] [int] NOT NULL,
	[status_date] [datetime] NOT NULL DEFAULT (getdate())
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [sync_log](
	[log_id] [int] IDENTITY(1,1) NOT NULL,
	[system_id] [int] NOT NULL,
	[client_id] [int] NOT NULL,
	[ip] [varchar](15) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[log_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [process_log](
	[process_id] [int] IDENTITY(1,1) NOT NULL,
	[system_id] [int] NOT NULL,
	[client_id] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[process_name] [varchar](255) NULL,
	[process_version] [varchar](20) NULL,
	[status] [int] NULL,
	[message] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[process_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [saved_criteriaelement](
	[id] [int] NOT NULL,
	[field] [int] NOT NULL,
	[operator] [varchar](50) NOT NULL,
	[operatorid] [int] NOT NULL,
	[value] [varchar](80) NOT NULL,
	[source] [int] NOT NULL DEFAULT ((-1)),
	[value_id] [int] NULL,
	[site_id] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [campaign_list_groups](
	[campaign_id] [int] NOT NULL,
	[group_id] [int] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tasklink_project](
	[task_id] [int] NOT NULL,
	[project_id] [int] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [quote_product_options](
	[quote_product_option_id] [int] IDENTITY(1,1) NOT NULL,
	[item_id] [int] NOT NULL,
	[product_option_id] [int] NOT NULL,
	[quantity] [int] NOT NULL DEFAULT ((0)),
	[price_currency] [int] NULL,
	[price_amount] [float] NOT NULL DEFAULT ((0)),
	[recurring_currency] [int] NULL,
	[recurring_amount] [float] NOT NULL DEFAULT ((0)),
	[recurring_type] [int] NULL,
	[extended_price] [float] NOT NULL DEFAULT ((0)),
	[total_price] [float] NOT NULL DEFAULT ((0)),
	[status_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[quote_product_option_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [sync_table](
	[table_id] [int] IDENTITY(1,1) NOT NULL,
	[system_id] [int] NOT NULL,
	[element_name] [varchar](255) NULL,
	[mapped_class_name] [varchar](255) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[create_statement] [text] NULL,
	[order_id] [int] NULL DEFAULT ((-1)),
	[sync_item] [bit] NULL DEFAULT ((0)),
	[object_key] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[table_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_step](
	[step_id] [int] IDENTITY(1,1) NOT NULL,
	[parent_id] [int] NULL,
	[phase_id] [int] NOT NULL,
	[description] [varchar](2048) NULL,
	[duration_type_id] [int] NULL,
	[estimated_duration] [int] NULL,
	[category_id] [int] NULL,
	[field_id] [int] NULL,
	[permission_type] [int] NULL,
	[role_id] [int] NULL,
	[department_id] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[allow_skip_to_here] [bit] NOT NULL DEFAULT ((0)),
	[label] [varchar](80) NULL,
	[action_required] [bit] NOT NULL DEFAULT ((0)),
	[group_id] [int] NULL,
	[target_relationship] [varchar](80) NULL,
	[action_id] [int] NULL,
	[allow_update] [bit] NOT NULL DEFAULT ((1)),
	[campaign_id] [int] NULL,
	[allow_duplicate_recipient] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[step_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_step_account_types](
	[step_id] [int] NOT NULL,
	[type_id] [int] NOT NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [service_contract_products](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[link_contract_id] [int] NULL,
	[link_product_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [active_campaign_groups](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[campaign_id] [int] NOT NULL,
	[groupname] [varchar](80) NOT NULL,
	[groupcriteria] [text] NULL DEFAULT (NULL),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [campaign_survey_link](
	[campaign_id] [int] NULL,
	[survey_id] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [campaign_run](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[campaign_id] [int] NOT NULL,
	[status] [int] NOT NULL DEFAULT ((0)),
	[run_date] [datetime] NOT NULL DEFAULT (getdate()),
	[total_contacts] [int] NULL DEFAULT ((0)),
	[total_sent] [int] NULL DEFAULT ((0)),
	[total_replied] [int] NULL DEFAULT ((0)),
	[total_bounced] [int] NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [campaign_group_map](
	[map_id] [int] IDENTITY(1,1) NOT NULL,
	[campaign_id] [int] NOT NULL,
	[user_group_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [business_process_hook_triggers](
	[trigger_id] [int] IDENTITY(1,1) NOT NULL,
	[action_type_id] [int] NOT NULL,
	[hook_id] [int] NOT NULL,
	[enabled] [bit] NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[trigger_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_project_permission](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NULL,
	[permission] [varchar](300) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[group_id] [int] NOT NULL DEFAULT ((0)),
	[default_role] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[permission] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_call_result](
	[result_id] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](100) NOT NULL,
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[next_required] [bit] NOT NULL DEFAULT ((0)),
	[next_days] [int] NOT NULL DEFAULT ((0)),
	[next_call_type_id] [int] NULL,
	[canceled_type] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[result_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticketlink_project](
	[ticket_id] [int] NOT NULL,
	[project_id] [int] NOT NULL
) ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [ticketlink_project_idx] ON [ticketlink_project] 
(
	[ticket_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_permissions](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[project_id] [int] NOT NULL,
	[permission_id] [int] NOT NULL,
	[userlevel] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [quote_product_option_float](
	[quote_product_option_id] [int] NULL,
	[value] [float] NOT NULL,
	[id] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [quote_product_option_timestamp](
	[quote_product_option_id] [int] NULL,
	[value] [datetime] NOT NULL,
	[id] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [quote_product_option_boolean](
	[quote_product_option_id] [int] NULL,
	[value] [bit] NOT NULL,
	[id] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [quote_product_option_integer](
	[quote_product_option_id] [int] NULL,
	[value] [int] NOT NULL,
	[id] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [quote_product_option_text](
	[quote_product_option_id] [int] NULL,
	[value] [text] NOT NULL,
	[id] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [sync_transaction_log](
	[transaction_id] [int] IDENTITY(1,1) NOT NULL,
	[log_id] [int] NOT NULL,
	[reference_id] [varchar](50) NULL,
	[element_name] [varchar](255) NULL,
	[action] [varchar](20) NULL,
	[link_item_id] [int] NULL,
	[status_code] [int] NULL,
	[record_count] [int] NULL,
	[message] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[transaction_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_step_lookup](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[step_id] [int] NOT NULL,
	[description] [varchar](255) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_document_store_permission](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NULL,
	[permission] [varchar](300) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[group_id] [int] NOT NULL DEFAULT ((0)),
	[default_role] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[permission] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [relationship](
	[relationship_id] [int] IDENTITY(1,1) NOT NULL,
	[type_id] [int] NULL,
	[object_id_maps_from] [int] NOT NULL,
	[category_id_maps_from] [int] NOT NULL,
	[object_id_maps_to] [int] NOT NULL,
	[category_id_maps_to] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[trashed_date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[relationship_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [role_permission](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role_id] [int] NOT NULL,
	[permission_id] [int] NOT NULL,
	[role_view] [bit] NOT NULL DEFAULT ((0)),
	[role_add] [bit] NOT NULL DEFAULT ((0)),
	[role_edit] [bit] NOT NULL DEFAULT ((0)),
	[role_delete] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [autoguide_vehicle](
	[vehicle_id] [int] IDENTITY(1,1) NOT NULL,
	[year] [varchar](4) NOT NULL,
	[make_id] [int] NOT NULL,
	[model_id] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[vehicle_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [autoguide_model](
	[model_id] [int] IDENTITY(1,1) NOT NULL,
	[make_id] [int] NOT NULL,
	[model_name] [varchar](50) NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[model_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [document_store_permissions](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[document_store_id] [int] NOT NULL,
	[permission_id] [int] NOT NULL,
	[userlevel] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [order_product_options](
	[order_product_option_id] [int] IDENTITY(1,1) NOT NULL,
	[item_id] [int] NOT NULL,
	[product_option_id] [int] NOT NULL,
	[quantity] [int] NOT NULL DEFAULT ((0)),
	[price_currency] [int] NULL,
	[price_amount] [float] NOT NULL DEFAULT ((0)),
	[recurring_currency] [int] NULL,
	[recurring_amount] [float] NOT NULL DEFAULT ((0)),
	[recurring_type] [int] NULL,
	[extended_price] [float] NOT NULL DEFAULT ((0)),
	[total_price] [float] NOT NULL DEFAULT ((0)),
	[status_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[order_product_option_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_option_values](
	[value_id] [int] IDENTITY(1,1) NOT NULL,
	[option_id] [int] NOT NULL,
	[result_id] [int] NOT NULL,
	[description] [text] NULL,
	[msrp_currency] [int] NULL,
	[msrp_amount] [float] NOT NULL DEFAULT ((0)),
	[price_currency] [int] NULL,
	[price_amount] [float] NOT NULL DEFAULT ((0)),
	[recurring_currency] [int] NULL,
	[recurring_amount] [float] NOT NULL DEFAULT ((0)),
	[recurring_type] [int] NULL,
	[enabled] [bit] NULL DEFAULT ((1)),
	[value] [float] NULL DEFAULT ((0)),
	[multiplier] [float] NULL DEFAULT ((1)),
	[range_min] [int] NULL DEFAULT ((1)),
	[range_max] [int] NULL DEFAULT ((1)),
	[cost_currency] [int] NULL,
	[cost_amount] [float] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[value_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

CREATE UNIQUE NONCLUSTERED INDEX [idx_pr_opt_val] ON [product_option_values] 
(
	[value_id] ASC,
	[option_id] ASC,
	[result_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [order_product](
	[item_id] [int] IDENTITY(1,1) NOT NULL,
	[order_id] [int] NOT NULL,
	[product_id] [int] NOT NULL,
	[quantity] [int] NOT NULL DEFAULT ((0)),
	[msrp_currency] [int] NULL,
	[msrp_amount] [float] NOT NULL DEFAULT ((0)),
	[price_currency] [int] NULL,
	[price_amount] [float] NOT NULL DEFAULT ((0)),
	[recurring_currency] [int] NULL,
	[recurring_amount] [float] NOT NULL DEFAULT ((0)),
	[recurring_type] [int] NULL,
	[extended_price] [float] NOT NULL DEFAULT ((0)),
	[total_price] [float] NOT NULL DEFAULT ((0)),
	[status_id] [int] NULL,
	[status_date] [datetime] NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[item_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [business_process_hook_library](
	[hook_id] [int] IDENTITY(1,1) NOT NULL,
	[link_module_id] [int] NOT NULL,
	[hook_class] [varchar](255) NOT NULL,
	[enabled] [bit] NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[hook_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [business_process](
	[process_id] [int] IDENTITY(1,1) NOT NULL,
	[process_name] [varchar](255) NOT NULL,
	[description] [varchar](510) NULL,
	[type_id] [int] NOT NULL,
	[link_module_id] [int] NOT NULL,
	[component_start_id] [int] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[process_id] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[process_name] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [help_module](
	[module_id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NULL,
	[module_brief_description] [text] NULL,
	[module_detail_description] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[module_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [category_editor_lookup](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[module_id] [int] NOT NULL,
	[constant_id] [int] NOT NULL,
	[table_name] [varchar](60) NULL,
	[level] [int] NULL DEFAULT ((0)),
	[description] [text] NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
	[category_id] [int] NOT NULL,
	[max_levels] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [permission](
	[permission_id] [int] IDENTITY(1,1) NOT NULL,
	[category_id] [int] NOT NULL,
	[permission] [varchar](80) NOT NULL,
	[permission_view] [bit] NOT NULL DEFAULT ((0)),
	[permission_add] [bit] NOT NULL DEFAULT ((0)),
	[permission_edit] [bit] NOT NULL DEFAULT ((0)),
	[permission_delete] [bit] NOT NULL DEFAULT ((0)),
	[description] [varchar](255) NOT NULL DEFAULT (''),
	[level] [int] NOT NULL DEFAULT ((0)),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[active] [bit] NOT NULL DEFAULT ((1)),
	[viewpoints] [bit] NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[permission_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_lists_lookup](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[module_id] [int] NOT NULL,
	[lookup_id] [int] NOT NULL,
	[class_name] [varchar](20) NULL,
	[table_name] [varchar](60) NULL,
	[level] [int] NULL DEFAULT ((0)),
	[description] [text] NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
	[category_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [custom_list_view_editor](
	[editor_id] [int] IDENTITY(1,1) NOT NULL,
	[module_id] [int] NOT NULL,
	[constant_id] [int] NOT NULL,
	[description] [text] NULL,
	[level] [int] NULL DEFAULT ((0)),
	[category_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[editor_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [module_field_categorylink](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[module_id] [int] NOT NULL,
	[category_id] [int] NOT NULL,
	[level] [int] NULL DEFAULT ((0)),
	[description] [text] NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[category_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_option_configurator](
	[configurator_id] [int] IDENTITY(1,1) NOT NULL,
	[short_description] [text] NULL,
	[long_description] [text] NULL,
	[class_name] [varchar](255) NULL,
	[result_type] [int] NOT NULL,
	[configurator_name] [varchar](300) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[configurator_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_category_map](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[category1_id] [int] NOT NULL,
	[category2_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_catalog_category_map](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[product_id] [int] NOT NULL,
	[category_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_option](
	[option_id] [int] IDENTITY(1,1) NOT NULL,
	[configurator_id] [int] NOT NULL,
	[parent_id] [int] NULL,
	[short_description] [text] NULL,
	[long_description] [text] NULL,
	[allow_customer_configure] [bit] NOT NULL DEFAULT ((0)),
	[allow_user_configure] [bit] NOT NULL DEFAULT ((0)),
	[required] [bit] NOT NULL DEFAULT ((0)),
	[start_date] [datetime] NULL DEFAULT (NULL),
	[end_date] [datetime] NULL DEFAULT (NULL),
	[enabled] [bit] NULL DEFAULT ((0)),
	[option_name] [varchar](300) NULL,
	[has_range] [bit] NULL DEFAULT ((0)),
	[has_multiplier] [bit] NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[option_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [autoguide_inventory_options](
	[inventory_id] [int] NOT NULL,
	[option_id] [int] NOT NULL
) ON [PRIMARY]

GO

CREATE UNIQUE NONCLUSTERED INDEX [idx_autog_inv_opt] ON [autoguide_inventory_options] 
(
	[inventory_id] ASC,
	[option_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [autoguide_ad_run](
	[ad_run_id] [int] IDENTITY(1,1) NOT NULL,
	[inventory_id] [int] NOT NULL,
	[run_date] [datetime] NOT NULL,
	[ad_type] [varchar](20) NULL,
	[include_photo] [bit] NULL DEFAULT ((0)),
	[complete_date] [datetime] NULL,
	[completedby] [int] NULL DEFAULT ((-1)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ad_run_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_option_map](
	[product_option_id] [int] IDENTITY(1,1) NOT NULL,
	[product_id] [int] NOT NULL,
	[option_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[product_option_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_option_boolean](
	[product_option_id] [int] NOT NULL,
	[value] [bit] NOT NULL,
	[id] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_option_float](
	[product_option_id] [int] NOT NULL,
	[value] [float] NOT NULL,
	[id] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_option_timestamp](
	[product_option_id] [int] NOT NULL,
	[value] [datetime] NOT NULL,
	[id] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_option_integer](
	[product_option_id] [int] NOT NULL,
	[value] [int] NOT NULL,
	[id] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_option_text](
	[product_option_id] [int] NOT NULL,
	[value] [text] NOT NULL,
	[id] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket_activity_item](
	[item_id] [int] IDENTITY(1,1) NOT NULL,
	[link_form_id] [int] NULL,
	[activity_date] [datetime] NULL,
	[description] [text] NULL,
	[travel_hours] [int] NULL,
	[travel_minutes] [int] NULL,
	[labor_hours] [int] NULL,
	[labor_minutes] [int] NULL,
	[activity_date_timezone] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[item_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [asset_materials_map](
	[map_id] [int] IDENTITY(1,1) NOT NULL,
	[asset_id] [int] NOT NULL,
	[code] [int] NOT NULL,
	[quantity] [float] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[map_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [custom_list_view](
	[view_id] [int] IDENTITY(1,1) NOT NULL,
	[editor_id] [int] NOT NULL,
	[name] [varchar](80) NOT NULL,
	[description] [text] NULL,
	[is_default] [bit] NULL DEFAULT ((0)),
	[entered] [datetime] NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[view_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [survey_questions](
	[question_id] [int] IDENTITY(1,1) NOT NULL,
	[survey_id] [int] NOT NULL,
	[type] [int] NOT NULL,
	[description] [varchar](255) NULL,
	[required] [bit] NOT NULL DEFAULT ((0)),
	[position] [int] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[question_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [active_survey_questions](
	[question_id] [int] IDENTITY(1,1) NOT NULL,
	[active_survey_id] [int] NULL,
	[type] [int] NOT NULL,
	[description] [varchar](255) NULL,
	[required] [bit] NOT NULL DEFAULT ((0)),
	[position] [int] NOT NULL DEFAULT ((0)),
	[average] [float] NULL DEFAULT ((0.00)),
	[total1] [int] NULL DEFAULT ((0)),
	[total2] [int] NULL DEFAULT ((0)),
	[total3] [int] NULL DEFAULT ((0)),
	[total4] [int] NULL DEFAULT ((0)),
	[total5] [int] NULL DEFAULT ((0)),
	[total6] [int] NULL DEFAULT ((0)),
	[total7] [int] NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[question_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [viewpoint_permission](
	[vp_permission_id] [int] IDENTITY(1,1) NOT NULL,
	[viewpoint_id] [int] NOT NULL,
	[permission_id] [int] NOT NULL,
	[viewpoint_view] [bit] NOT NULL DEFAULT ((0)),
	[viewpoint_add] [bit] NOT NULL DEFAULT ((0)),
	[viewpoint_edit] [bit] NOT NULL DEFAULT ((0)),
	[viewpoint_delete] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[vp_permission_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [custom_list_view_field](
	[field_id] [int] IDENTITY(1,1) NOT NULL,
	[view_id] [int] NOT NULL,
	[name] [varchar](80) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[field_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [trouble_asset_replacement](
	[replacement_id] [int] IDENTITY(1,1) NOT NULL,
	[link_form_id] [int] NULL,
	[part_number] [varchar](256) NULL,
	[part_description] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[replacement_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_item_work_selection](
	[selection_id] [int] IDENTITY(1,1) NOT NULL,
	[item_work_id] [int] NOT NULL,
	[selection] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[selection_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [survey_items](
	[item_id] [int] IDENTITY(1,1) NOT NULL,
	[question_id] [int] NOT NULL,
	[type] [int] NULL DEFAULT ((-1)),
	[description] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[item_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [custom_field_category](
	[module_id] [int] NOT NULL,
	[category_id] [int] IDENTITY(1,1) NOT NULL,
	[category_name] [varchar](255) NOT NULL,
	[level] [int] NULL DEFAULT ((0)),
	[description] [text] NULL,
	[start_date] [datetime] NULL DEFAULT (getdate()),
	[end_date] [datetime] NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enabled] [bit] NULL DEFAULT ((1)),
	[multiple_records] [bit] NULL DEFAULT ((0)),
	[read_only] [bit] NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[category_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [custom_field_cat_idx] ON [custom_field_category] 
(
	[module_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [active_survey_responses](
	[response_id] [int] IDENTITY(1,1) NOT NULL,
	[active_survey_id] [int] NOT NULL,
	[contact_id] [int] NOT NULL DEFAULT ((-1)),
	[unique_code] [varchar](255) NULL,
	[ip_address] [varchar](15) NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[address_updated] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[response_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [custom_field_group](
	[category_id] [int] NOT NULL,
	[group_id] [int] IDENTITY(1,1) NOT NULL,
	[group_name] [varchar](255) NOT NULL,
	[level] [int] NULL DEFAULT ((0)),
	[description] [text] NULL,
	[start_date] [datetime] NULL DEFAULT (getdate()),
	[end_date] [datetime] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[group_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [custom_field_grp_idx] ON [custom_field_group] 
(
	[category_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [lookup_sub_segment](
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](300) NOT NULL,
	[segment_id] [int] NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [product_keyword_map](
	[product_id] [int] NOT NULL,
	[keyword_id] [int] NOT NULL
) ON [PRIMARY]

GO

CREATE UNIQUE NONCLUSTERED INDEX [idx_pr_key_map] ON [product_keyword_map] 
(
	[product_id] ASC,
	[keyword_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_news_category](
	[category_id] [int] IDENTITY(1,1) NOT NULL,
	[project_id] [int] NOT NULL,
	[category_name] [varchar](255) NULL,
	[entered] [datetime] NULL DEFAULT (getdate()),
	[level] [int] NOT NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[category_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [project_ticket_count](
	[project_id] [int] NOT NULL,
	[key_count] [int] NOT NULL DEFAULT ((0)),
UNIQUE NONCLUSTERED 
(
	[project_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [active_survey_items](
	[item_id] [int] IDENTITY(1,1) NOT NULL,
	[question_id] [int] NOT NULL,
	[type] [int] NULL DEFAULT ((-1)),
	[description] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[item_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [custom_field_info](
	[group_id] [int] NOT NULL,
	[field_id] [int] IDENTITY(1,1) NOT NULL,
	[field_name] [varchar](255) NOT NULL,
	[level] [int] NULL DEFAULT ((0)),
	[field_type] [int] NOT NULL,
	[validation_type] [int] NULL DEFAULT ((0)),
	[required] [bit] NULL DEFAULT ((0)),
	[parameters] [text] NULL,
	[start_date] [datetime] NULL DEFAULT (getdate()),
	[end_date] [datetime] NULL DEFAULT (NULL),
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enabled] [bit] NULL DEFAULT ((1)),
	[additional_text] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[field_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

CREATE NONCLUSTERED INDEX [custom_field_inf_idx] ON [custom_field_info] 
(
	[group_id] ASC
) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [business_process_component_result_lookup](
	[result_id] [int] IDENTITY(1,1) NOT NULL,
	[component_id] [int] NOT NULL,
	[return_id] [int] NOT NULL,
	[description] [varchar](255) NULL,
	[level] [int] NOT NULL DEFAULT ((0)),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[result_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [access](
	[user_id] [int] IDENTITY(0,1) NOT NULL,
	[username] [varchar](80) NOT NULL,
	[password] [varchar](80) NULL,
	[contact_id] [int] NULL DEFAULT ((-1)),
	[role_id] [int] NULL DEFAULT ((-1)),
	[manager_id] [int] NULL DEFAULT ((-1)),
	[startofday] [int] NULL DEFAULT ((8)),
	[endofday] [int] NULL DEFAULT ((18)),
	[locale] [varchar](255) NULL,
	[timezone] [varchar](255) NULL DEFAULT ('America/New_York'),
	[last_ip] [varchar](15) NULL,
	[last_login] [datetime] NOT NULL DEFAULT (getdate()),
	[enteredby] [int] NOT NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[modifiedby] [int] NOT NULL,
	[modified] [datetime] NOT NULL DEFAULT (getdate()),
	[expires] [datetime] NULL DEFAULT (NULL),
	[alias] [int] NULL DEFAULT ((-1)),
	[assistant] [int] NULL DEFAULT ((-1)),
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[currency] [varchar](5) NULL,
	[language] [varchar](20) NULL,
	[webdav_password] [varchar](80) NULL,
	[hidden] [bit] NULL DEFAULT ((0)),
	[site_id] [int] NULL,
	[allow_webdav_access] [bit] NOT NULL DEFAULT ((1)),
	[allow_httpapi_access] [bit] NOT NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_plan_category_draft](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[link_id] [int] NULL DEFAULT ((-1)),
	[cat_level] [int] NOT NULL DEFAULT ((0)),
	[parent_cat_code] [int] NOT NULL DEFAULT ((0)),
	[description] [varchar](300) NOT NULL,
	[full_description] [text] NOT NULL DEFAULT (''),
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[site_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket_category](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[cat_level] [int] NOT NULL DEFAULT ((0)),
	[parent_cat_code] [int] NOT NULL DEFAULT ((0)),
	[description] [varchar](300) NOT NULL,
	[full_description] [text] NOT NULL DEFAULT (''),
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[site_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket_category_draft](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[link_id] [int] NULL DEFAULT ((-1)),
	[cat_level] [int] NOT NULL DEFAULT ((0)),
	[parent_cat_code] [int] NOT NULL DEFAULT ((0)),
	[description] [varchar](300) NOT NULL,
	[full_description] [text] NOT NULL DEFAULT (''),
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[site_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [asset_category_draft](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[link_id] [int] NULL DEFAULT ((-1)),
	[cat_level] [int] NOT NULL DEFAULT ((0)),
	[parent_cat_code] [int] NOT NULL DEFAULT ((0)),
	[description] [varchar](300) NOT NULL,
	[full_description] [text] NOT NULL DEFAULT (''),
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[site_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [asset_category](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[cat_level] [int] NOT NULL DEFAULT ((0)),
	[parent_cat_code] [int] NOT NULL DEFAULT ((0)),
	[description] [varchar](300) NOT NULL,
	[full_description] [text] NOT NULL DEFAULT (''),
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[site_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [ticket_defect](
	[defect_id] [int] IDENTITY(1,1) NOT NULL,
	[title] [varchar](255) NOT NULL,
	[description] [text] NULL,
	[start_date] [datetime] NOT NULL DEFAULT (getdate()),
	[end_date] [datetime] NULL,
	[enabled] [bit] NOT NULL DEFAULT ((1)),
	[trashed_date] [datetime] NULL,
	[site_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[defect_id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [action_plan_category](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[cat_level] [int] NOT NULL DEFAULT ((0)),
	[parent_cat_code] [int] NOT NULL DEFAULT ((0)),
	[description] [varchar](300) NOT NULL,
	[full_description] [text] NOT NULL DEFAULT (''),
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[enabled] [bit] NULL DEFAULT ((1)),
	[site_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [custom_field_lookup](
	[field_id] [int] NOT NULL,
	[code] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](255) NOT NULL,
	[default_item] [bit] NULL DEFAULT ((0)),
	[level] [int] NULL DEFAULT ((0)),
	[start_date] [datetime] NULL DEFAULT (getdate()),
	[end_date] [datetime] NULL,
	[entered] [datetime] NOT NULL DEFAULT (getdate()),
	[enabled] [bit] NULL DEFAULT ((1)),
PRIMARY KEY CLUSTERED 
(
	[code] ASC
) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [product_catalog]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [product_catalog]  WITH CHECK ADD FOREIGN KEY([estimated_ship_time])
REFERENCES [lookup_product_ship_time] ([code])
GO
ALTER TABLE [product_catalog]  WITH CHECK ADD FOREIGN KEY([format_id])
REFERENCES [lookup_product_format] ([code])
GO
ALTER TABLE [product_catalog]  WITH CHECK ADD FOREIGN KEY([import_id])
REFERENCES [import] ([import_id])
GO
ALTER TABLE [product_catalog]  WITH CHECK ADD FOREIGN KEY([large_image_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [product_catalog]  WITH CHECK ADD FOREIGN KEY([manufacturer_id])
REFERENCES [lookup_product_manufacturer] ([code])
GO
ALTER TABLE [product_catalog]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [product_catalog]  WITH CHECK ADD FOREIGN KEY([parent_id])
REFERENCES [product_catalog] ([product_id])
GO
ALTER TABLE [product_catalog]  WITH CHECK ADD FOREIGN KEY([shipping_id])
REFERENCES [lookup_product_shipping] ([code])
GO
ALTER TABLE [product_catalog]  WITH CHECK ADD FOREIGN KEY([small_image_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [product_catalog]  WITH CHECK ADD FOREIGN KEY([thumbnail_image_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [product_catalog]  WITH CHECK ADD FOREIGN KEY([type_id])
REFERENCES [lookup_product_type] ([code])
GO
ALTER TABLE [portfolio_item]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [portfolio_item]  WITH CHECK ADD FOREIGN KEY([image_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [portfolio_item]  WITH CHECK ADD FOREIGN KEY([item_position_id])
REFERENCES [portfolio_item] ([item_id])
GO
ALTER TABLE [portfolio_item]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [portfolio_item]  WITH CHECK ADD FOREIGN KEY([portfolio_category_id])
REFERENCES [portfolio_category] ([category_id])
GO
ALTER TABLE [organization_emailaddress]  WITH CHECK ADD FOREIGN KEY([emailaddress_type])
REFERENCES [lookup_orgemail_types] ([code])
GO
ALTER TABLE [organization_emailaddress]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [organization_emailaddress]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [organization_emailaddress]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [help_faqs]  WITH CHECK ADD FOREIGN KEY([completedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_faqs]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_faqs]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_faqs]  WITH CHECK ADD FOREIGN KEY([owning_module_id])
REFERENCES [help_module] ([module_id])
GO
ALTER TABLE [project_files_thumbnail]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_files_thumbnail]  WITH CHECK ADD FOREIGN KEY([item_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [project_files_thumbnail]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_product_status]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_product_status]  WITH CHECK ADD FOREIGN KEY([item_id])
REFERENCES [order_product] ([item_id])
GO
ALTER TABLE [order_product_status]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_product_status]  WITH CHECK ADD FOREIGN KEY([order_id])
REFERENCES [order_entry] ([order_id])
GO
ALTER TABLE [order_product_status]  WITH CHECK ADD FOREIGN KEY([status_id])
REFERENCES [lookup_order_status] ([code])
GO
ALTER TABLE [projects]  WITH CHECK ADD FOREIGN KEY([approvalBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [projects]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [lookup_project_category] ([code])
GO
ALTER TABLE [projects]  WITH CHECK ADD FOREIGN KEY([department_id])
REFERENCES [lookup_department] ([code])
GO
ALTER TABLE [projects]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [projects]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [report]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [permission_category] ([category_id])
GO
ALTER TABLE [report]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [report]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [report]  WITH CHECK ADD FOREIGN KEY([permission_id])
REFERENCES [permission] ([permission_id])
GO
ALTER TABLE [project_files_download]  WITH CHECK ADD FOREIGN KEY([item_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [project_files_download]  WITH CHECK ADD FOREIGN KEY([user_download_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [organization_address]  WITH CHECK ADD FOREIGN KEY([address_type])
REFERENCES [lookup_orgaddress_types] ([code])
GO
ALTER TABLE [organization_address]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [organization_address]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [organization_address]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [portfolio_category]  WITH CHECK ADD FOREIGN KEY([category_position_id])
REFERENCES [portfolio_category] ([category_id])
GO
ALTER TABLE [portfolio_category]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [portfolio_category]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [portfolio_category]  WITH CHECK ADD FOREIGN KEY([parent_category_id])
REFERENCES [portfolio_category] ([category_id])
GO
ALTER TABLE [history]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [history]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [history]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [history]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [revenue]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [revenue]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [revenue]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [revenue]  WITH CHECK ADD FOREIGN KEY([owner])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [revenue]  WITH CHECK ADD FOREIGN KEY([type])
REFERENCES [lookup_revenue_types] ([code])
GO
ALTER TABLE [help_related_links]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_related_links]  WITH CHECK ADD FOREIGN KEY([linkto_content_id])
REFERENCES [help_contents] ([help_id])
GO
ALTER TABLE [help_related_links]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_related_links]  WITH CHECK ADD FOREIGN KEY([owning_module_id])
REFERENCES [help_module] ([module_id])
GO
ALTER TABLE [web_icelet_property]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_icelet_property]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_icelet_property]  WITH CHECK ADD FOREIGN KEY([row_column_id])
REFERENCES [web_row_column] ([row_column_id])
GO
ALTER TABLE [active_survey]  WITH CHECK ADD FOREIGN KEY([campaign_id])
REFERENCES [campaign] ([campaign_id])
GO
ALTER TABLE [active_survey]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [active_survey]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [active_survey]  WITH CHECK ADD FOREIGN KEY([type])
REFERENCES [lookup_survey_types] ([code])
GO
ALTER TABLE [action_item_work_notes]  WITH CHECK ADD FOREIGN KEY([item_work_id])
REFERENCES [action_item_work] ([item_work_id])
GO
ALTER TABLE [action_item_work_notes]  WITH CHECK ADD FOREIGN KEY([submittedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [quote_notes]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [quote_notes]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [quote_notes]  WITH CHECK ADD FOREIGN KEY([quote_id])
REFERENCES [quote_entry] ([quote_id])
GO
ALTER TABLE [document_store_department_member]  WITH CHECK ADD FOREIGN KEY([document_store_id])
REFERENCES [document_store] ([document_store_id])
GO
ALTER TABLE [document_store_department_member]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [document_store_department_member]  WITH CHECK ADD FOREIGN KEY([item_id])
REFERENCES [lookup_department] ([code])
GO
ALTER TABLE [document_store_department_member]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [document_store_department_member]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [document_store_department_member]  WITH CHECK ADD FOREIGN KEY([userlevel])
REFERENCES [lookup_document_store_role] ([code])
GO
ALTER TABLE [project_files_version]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_files_version]  WITH CHECK ADD FOREIGN KEY([item_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [project_files_version]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [viewpoint]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [viewpoint]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [viewpoint]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [viewpoint]  WITH CHECK ADD FOREIGN KEY([vp_user_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_features]  WITH CHECK ADD FOREIGN KEY([completedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_features]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_features]  WITH CHECK ADD FOREIGN KEY([link_help_id])
REFERENCES [help_contents] ([help_id])
GO
ALTER TABLE [help_features]  WITH CHECK ADD FOREIGN KEY([link_feature_id])
REFERENCES [lookup_help_features] ([code])
GO
ALTER TABLE [help_features]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_row_column]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_row_column]  WITH CHECK ADD FOREIGN KEY([icelet_id])
REFERENCES [web_icelet] ([icelet_id])
GO
ALTER TABLE [web_row_column]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_row_column]  WITH CHECK ADD FOREIGN KEY([page_row_id])
REFERENCES [web_page_row] ([page_row_id])
GO
ALTER TABLE [document_store_role_member]  WITH CHECK ADD FOREIGN KEY([document_store_id])
REFERENCES [document_store] ([document_store_id])
GO
ALTER TABLE [document_store_role_member]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [document_store_role_member]  WITH CHECK ADD FOREIGN KEY([item_id])
REFERENCES [role] ([role_id])
GO
ALTER TABLE [document_store_role_member]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [document_store_role_member]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [document_store_role_member]  WITH CHECK ADD FOREIGN KEY([userlevel])
REFERENCES [lookup_document_store_role] ([code])
GO
ALTER TABLE [action_item_work]  WITH CHECK ADD FOREIGN KEY([action_step_id])
REFERENCES [action_step] ([step_id])
GO
ALTER TABLE [action_item_work]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_item_work]  WITH CHECK ADD FOREIGN KEY([link_module_id])
REFERENCES [action_plan_constants] ([map_id])
GO
ALTER TABLE [action_item_work]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_item_work]  WITH CHECK ADD FOREIGN KEY([owner])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_item_work]  WITH CHECK ADD FOREIGN KEY([phase_work_id])
REFERENCES [action_phase_work] ([phase_work_id])
GO
ALTER TABLE [project_files]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_files]  WITH CHECK ADD FOREIGN KEY([folder_id])
REFERENCES [project_folders] ([folder_id])
GO
ALTER TABLE [project_files]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_payment_status]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_payment_status]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_payment_status]  WITH CHECK ADD FOREIGN KEY([payment_id])
REFERENCES [order_payment] ([payment_id])
GO
ALTER TABLE [order_payment_status]  WITH CHECK ADD FOREIGN KEY([status_id])
REFERENCES [lookup_payment_status] ([code])
GO
ALTER TABLE [ticket_sun_form]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [ticket_sun_form]  WITH CHECK ADD FOREIGN KEY([link_ticket_id])
REFERENCES [ticket] ([ticketid])
GO
ALTER TABLE [ticket_sun_form]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [webdav]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [permission_category] ([category_id])
GO
ALTER TABLE [webdav]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [webdav]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [document_store_user_member]  WITH CHECK ADD FOREIGN KEY([document_store_id])
REFERENCES [document_store] ([document_store_id])
GO
ALTER TABLE [document_store_user_member]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [document_store_user_member]  WITH CHECK ADD FOREIGN KEY([item_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [document_store_user_member]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [document_store_user_member]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [document_store_user_member]  WITH CHECK ADD FOREIGN KEY([userlevel])
REFERENCES [lookup_document_store_role] ([code])
GO
ALTER TABLE [survey]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [survey]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_tableofcontentitem_links]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_tableofcontentitem_links]  WITH CHECK ADD FOREIGN KEY([global_link_id])
REFERENCES [help_tableof_contents] ([content_id])
GO
ALTER TABLE [help_tableofcontentitem_links]  WITH CHECK ADD FOREIGN KEY([linkto_content_id])
REFERENCES [help_contents] ([help_id])
GO
ALTER TABLE [help_tableofcontentitem_links]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_phase_work]  WITH CHECK ADD FOREIGN KEY([action_phase_id])
REFERENCES [action_phase] ([phase_id])
GO
ALTER TABLE [action_phase_work]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_phase_work]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_phase_work]  WITH CHECK ADD FOREIGN KEY([plan_work_id])
REFERENCES [action_plan_work] ([plan_work_id])
GO
ALTER TABLE [project_folders]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_folders]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_page_row]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_page_row]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_page_row]  WITH CHECK ADD FOREIGN KEY([page_version_id])
REFERENCES [web_page_version] ([page_version_id])
GO
ALTER TABLE [web_page_row]  WITH CHECK ADD FOREIGN KEY([row_column_id])
REFERENCES [web_row_column] ([row_column_id])
GO
ALTER TABLE [action_plan_work_notes]  WITH CHECK ADD FOREIGN KEY([plan_work_id])
REFERENCES [action_plan_work] ([plan_work_id])
GO
ALTER TABLE [action_plan_work_notes]  WITH CHECK ADD FOREIGN KEY([submittedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [quotelog]  WITH CHECK ADD FOREIGN KEY([delivery_id])
REFERENCES [lookup_quote_delivery] ([code])
GO
ALTER TABLE [quotelog]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [quotelog]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [quotelog]  WITH CHECK ADD FOREIGN KEY([quote_id])
REFERENCES [quote_entry] ([quote_id])
GO
ALTER TABLE [quotelog]  WITH CHECK ADD FOREIGN KEY([source_id])
REFERENCES [lookup_quote_source] ([code])
GO
ALTER TABLE [quotelog]  WITH CHECK ADD FOREIGN KEY([status_id])
REFERENCES [lookup_quote_status] ([code])
GO
ALTER TABLE [quotelog]  WITH CHECK ADD FOREIGN KEY([terms_id])
REFERENCES [lookup_quote_terms] ([code])
GO
ALTER TABLE [quotelog]  WITH CHECK ADD FOREIGN KEY([type_id])
REFERENCES [lookup_quote_type] ([code])
GO
ALTER TABLE [order_payment]  WITH CHECK ADD FOREIGN KEY([bank_id])
REFERENCES [payment_eft] ([bank_id])
GO
ALTER TABLE [order_payment]  WITH CHECK ADD FOREIGN KEY([creditcard_id])
REFERENCES [payment_creditcard] ([creditcard_id])
GO
ALTER TABLE [order_payment]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_payment]  WITH CHECK ADD FOREIGN KEY([history_id])
REFERENCES [customer_product_history] ([history_id])
GO
ALTER TABLE [order_payment]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_payment]  WITH CHECK ADD FOREIGN KEY([order_id])
REFERENCES [order_entry] ([order_id])
GO
ALTER TABLE [order_payment]  WITH CHECK ADD FOREIGN KEY([order_item_id])
REFERENCES [order_product] ([item_id])
GO
ALTER TABLE [order_payment]  WITH CHECK ADD FOREIGN KEY([payment_method_id])
REFERENCES [lookup_payment_methods] ([code])
GO
ALTER TABLE [order_payment]  WITH CHECK ADD FOREIGN KEY([status_id])
REFERENCES [lookup_payment_status] ([code])
GO
ALTER TABLE [order_entry]  WITH CHECK ADD FOREIGN KEY([billing_contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [order_entry]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_entry]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_entry]  WITH CHECK ADD FOREIGN KEY([orderedby])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [order_entry]  WITH CHECK ADD FOREIGN KEY([order_terms_id])
REFERENCES [lookup_order_terms] ([code])
GO
ALTER TABLE [order_entry]  WITH CHECK ADD FOREIGN KEY([order_type_id])
REFERENCES [lookup_order_type] ([code])
GO
ALTER TABLE [order_entry]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [order_entry]  WITH CHECK ADD FOREIGN KEY([parent_id])
REFERENCES [order_entry] ([order_id])
GO
ALTER TABLE [order_entry]  WITH CHECK ADD FOREIGN KEY([quote_id])
REFERENCES [quote_entry] ([quote_id])
GO
ALTER TABLE [order_entry]  WITH CHECK ADD FOREIGN KEY([sales_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_entry]  WITH CHECK ADD FOREIGN KEY([source_id])
REFERENCES [lookup_order_source] ([code])
GO
ALTER TABLE [order_entry]  WITH CHECK ADD FOREIGN KEY([status_id])
REFERENCES [lookup_order_status] ([code])
GO
ALTER TABLE [project_issue_replies]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_issue_replies]  WITH CHECK ADD FOREIGN KEY([issue_id])
REFERENCES [project_issues] ([issue_id])
GO
ALTER TABLE [project_issue_replies]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([account_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([contract_id])
REFERENCES [service_contract] ([contract_id])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([email_service_model])
REFERENCES [lookup_email_model] ([code])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([level1])
REFERENCES [asset_category] ([id])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([level2])
REFERENCES [asset_category] ([id])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([level3])
REFERENCES [asset_category] ([id])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([manufacturer_code])
REFERENCES [lookup_asset_manufacturer] ([code])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([onsite_service_model])
REFERENCES [lookup_onsite_model] ([code])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([parent_id])
REFERENCES [asset] ([asset_id])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([response_time])
REFERENCES [lookup_response_model] ([code])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([telephone_service_model])
REFERENCES [lookup_phone_model] ([code])
GO
ALTER TABLE [asset]  WITH CHECK ADD FOREIGN KEY([vendor_code])
REFERENCES [lookup_asset_vendor] ([code])
GO
ALTER TABLE [document_store]  WITH CHECK ADD FOREIGN KEY([approvalBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [document_store]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [document_store]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_tableof_contents]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [permission_category] ([category_id])
GO
ALTER TABLE [help_tableof_contents]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_tableof_contents]  WITH CHECK ADD FOREIGN KEY([firstchild])
REFERENCES [help_tableof_contents] ([content_id])
GO
ALTER TABLE [help_tableof_contents]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_tableof_contents]  WITH CHECK ADD FOREIGN KEY([nextsibling])
REFERENCES [help_tableof_contents] ([content_id])
GO
ALTER TABLE [help_tableof_contents]  WITH CHECK ADD FOREIGN KEY([parent])
REFERENCES [help_tableof_contents] ([content_id])
GO
ALTER TABLE [ticket_csstm_form]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [ticket_csstm_form]  WITH CHECK ADD FOREIGN KEY([link_ticket_id])
REFERENCES [ticket] ([ticketid])
GO
ALTER TABLE [ticket_csstm_form]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [user_group_map]  WITH CHECK ADD FOREIGN KEY([group_id])
REFERENCES [user_group] ([group_id])
GO
ALTER TABLE [user_group_map]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_page]  WITH CHECK ADD FOREIGN KEY([active_page_version_id])
REFERENCES [web_page_version] ([page_version_id])
GO
ALTER TABLE [web_page]  WITH CHECK ADD FOREIGN KEY([construction_page_version_id])
REFERENCES [web_page_version] ([page_version_id])
GO
ALTER TABLE [web_page]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_page]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_page]  WITH CHECK ADD FOREIGN KEY([page_group_id])
REFERENCES [web_page_group] ([page_group_id])
GO
ALTER TABLE [web_page]  WITH CHECK ADD FOREIGN KEY([tab_banner_id])
REFERENCES [web_tab_banner] ([tab_banner_id])
GO
ALTER TABLE [product_category]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [product_category]  WITH CHECK ADD FOREIGN KEY([import_id])
REFERENCES [import] ([import_id])
GO
ALTER TABLE [product_category]  WITH CHECK ADD FOREIGN KEY([large_image_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [product_category]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [product_category]  WITH CHECK ADD FOREIGN KEY([parent_id])
REFERENCES [product_category] ([category_id])
GO
ALTER TABLE [product_category]  WITH CHECK ADD FOREIGN KEY([small_image_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [product_category]  WITH CHECK ADD FOREIGN KEY([thumbnail_image_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [product_category]  WITH CHECK ADD FOREIGN KEY([type_id])
REFERENCES [lookup_product_category_type] ([code])
GO
ALTER TABLE [action_plan_work]  WITH CHECK ADD FOREIGN KEY([action_plan_id])
REFERENCES [action_plan] ([plan_id])
GO
ALTER TABLE [action_plan_work]  WITH CHECK ADD FOREIGN KEY([assignedTo])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_plan_work]  WITH CHECK ADD FOREIGN KEY([current_phase])
REFERENCES [action_phase] ([phase_id])
GO
ALTER TABLE [action_plan_work]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_plan_work]  WITH CHECK ADD FOREIGN KEY([link_module_id])
REFERENCES [action_plan_constants] ([map_id])
GO
ALTER TABLE [action_plan_work]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [cfsinbox_messagelink]  WITH CHECK ADD FOREIGN KEY([sent_to])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [cfsinbox_messagelink]  WITH CHECK ADD FOREIGN KEY([sent_from])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [cfsinbox_messagelink]  WITH CHECK ADD FOREIGN KEY([id])
REFERENCES [cfsinbox_message] ([id])
GO
ALTER TABLE [web_tab_banner]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_tab_banner]  WITH CHECK ADD FOREIGN KEY([image_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [web_tab_banner]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_tab_banner]  WITH CHECK ADD FOREIGN KEY([tab_id])
REFERENCES [web_tab] ([tab_id])
GO
ALTER TABLE [ticketlog]  WITH CHECK ADD FOREIGN KEY([assigned_to])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [ticketlog]  WITH CHECK ADD FOREIGN KEY([department_code])
REFERENCES [lookup_department] ([code])
GO
ALTER TABLE [ticketlog]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [ticketlog]  WITH CHECK ADD FOREIGN KEY([escalation_code])
REFERENCES [lookup_ticket_escalation] ([code])
GO
ALTER TABLE [ticketlog]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [ticketlog]  WITH CHECK ADD FOREIGN KEY([pri_code])
REFERENCES [ticket_priority] ([code])
GO
ALTER TABLE [ticketlog]  WITH CHECK ADD FOREIGN KEY([scode])
REFERENCES [ticket_severity] ([code])
GO
ALTER TABLE [ticketlog]  WITH CHECK ADD FOREIGN KEY([state_id])
REFERENCES [lookup_ticket_state] ([code])
GO
ALTER TABLE [ticketlog]  WITH CHECK ADD FOREIGN KEY([ticketid])
REFERENCES [ticket] ([ticketid])
GO
ALTER TABLE [customer_product_history]  WITH CHECK ADD FOREIGN KEY([customer_product_id])
REFERENCES [customer_product] ([customer_product_id])
GO
ALTER TABLE [customer_product_history]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [customer_product_history]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [customer_product_history]  WITH CHECK ADD FOREIGN KEY([order_id])
REFERENCES [order_entry] ([order_id])
GO
ALTER TABLE [customer_product_history]  WITH CHECK ADD FOREIGN KEY([order_item_id])
REFERENCES [order_product] ([item_id])
GO
ALTER TABLE [customer_product_history]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [user_group]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [user_group]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [user_group]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [project_issues]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [project_issues_categories] ([category_id])
GO
ALTER TABLE [project_issues]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_issues]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_issues]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [help_contents]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [permission_category] ([category_id])
GO
ALTER TABLE [help_contents]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_contents]  WITH CHECK ADD FOREIGN KEY([link_module_id])
REFERENCES [help_module] ([module_id])
GO
ALTER TABLE [help_contents]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_contents]  WITH CHECK ADD FOREIGN KEY([nextcontent])
REFERENCES [help_contents] ([help_id])
GO
ALTER TABLE [help_contents]  WITH CHECK ADD FOREIGN KEY([prevcontent])
REFERENCES [help_contents] ([help_id])
GO
ALTER TABLE [help_contents]  WITH CHECK ADD FOREIGN KEY([upcontent])
REFERENCES [help_contents] ([help_id])
GO
ALTER TABLE [opportunity_component_log]  WITH CHECK ADD FOREIGN KEY([component_id])
REFERENCES [opportunity_component] ([id])
GO
ALTER TABLE [opportunity_component_log]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [opportunity_component_log]  WITH CHECK ADD FOREIGN KEY([header_id])
REFERENCES [opportunity_header] ([opp_id])
GO
ALTER TABLE [opportunity_component_log]  WITH CHECK ADD FOREIGN KEY([owner])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [opportunity_component_log]  WITH CHECK ADD FOREIGN KEY([stage])
REFERENCES [lookup_stage] ([code])
GO
ALTER TABLE [web_page_group]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_page_group]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_page_group]  WITH CHECK ADD FOREIGN KEY([tab_id])
REFERENCES [web_tab] ([tab_id])
GO
ALTER TABLE [cfsinbox_message]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [cfsinbox_message]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [role]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [role]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [customer_product]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [customer_product]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [customer_product]  WITH CHECK ADD FOREIGN KEY([order_id])
REFERENCES [order_entry] ([order_id])
GO
ALTER TABLE [customer_product]  WITH CHECK ADD FOREIGN KEY([order_item_id])
REFERENCES [order_product] ([item_id])
GO
ALTER TABLE [customer_product]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [customer_product]  WITH CHECK ADD FOREIGN KEY([status_id])
REFERENCES [lookup_order_status] ([code])
GO
ALTER TABLE [project_issues_categories]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_issues_categories]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_issues_categories]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [contact_message]  WITH CHECK ADD FOREIGN KEY([message_id])
REFERENCES [message] ([id])
GO
ALTER TABLE [contact_message]  WITH CHECK ADD FOREIGN KEY([received_from])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [contact_message]  WITH CHECK ADD FOREIGN KEY([received_by])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [payment_eft]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [payment_eft]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [payment_eft]  WITH CHECK ADD FOREIGN KEY([order_id])
REFERENCES [order_entry] ([order_id])
GO
ALTER TABLE [contact_lead_read_map]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [contact_lead_read_map]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_page_version]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_page_version]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_page_version]  WITH CHECK ADD FOREIGN KEY([page_id])
REFERENCES [web_page] ([page_id])
GO
ALTER TABLE [web_page_version]  WITH CHECK ADD FOREIGN KEY([parent_page_version_id])
REFERENCES [web_page_version] ([page_version_id])
GO
ALTER TABLE [package_products_map]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [package_products_map]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [package_products_map]  WITH CHECK ADD FOREIGN KEY([msrp_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [package_products_map]  WITH CHECK ADD FOREIGN KEY([package_id])
REFERENCES [package] ([package_id])
GO
ALTER TABLE [package_products_map]  WITH CHECK ADD FOREIGN KEY([price_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [package_products_map]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [product_catalog] ([product_id])
GO
ALTER TABLE [contact_textmessageaddress]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [contact_textmessageaddress]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [contact_textmessageaddress]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [contact_textmessageaddress]  WITH CHECK ADD FOREIGN KEY([textmessageaddress_type])
REFERENCES [lookup_textmessage_types] ([code])
GO
ALTER TABLE [service_contract_hours]  WITH CHECK ADD FOREIGN KEY([adjustment_reason])
REFERENCES [lookup_hours_reason] ([code])
GO
ALTER TABLE [service_contract_hours]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [service_contract_hours]  WITH CHECK ADD FOREIGN KEY([link_contract_id])
REFERENCES [service_contract] ([contract_id])
GO
ALTER TABLE [service_contract_hours]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [contact_lead_skipped_map]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [contact_lead_skipped_map]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_assignments_status]  WITH CHECK ADD FOREIGN KEY([assignment_id])
REFERENCES [project_assignments] ([assignment_id])
GO
ALTER TABLE [project_assignments_status]  WITH CHECK ADD FOREIGN KEY([project_status_id])
REFERENCES [lookup_project_status] ([code])
GO
ALTER TABLE [project_assignments_status]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_assignments_status]  WITH CHECK ADD FOREIGN KEY([user_assign_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_tab]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_tab]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_tab]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [web_site] ([site_id])
GO
ALTER TABLE [import]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [import]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [import]  WITH CHECK ADD FOREIGN KEY([rating])
REFERENCES [lookup_contact_rating] ([code])
GO
ALTER TABLE [import]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([assigned_to])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([cat_code])
REFERENCES [ticket_category] ([id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([cause_id])
REFERENCES [lookup_ticket_cause] ([code])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([customer_product_id])
REFERENCES [customer_product] ([customer_product_id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([defect_id])
REFERENCES [ticket_defect] ([defect_id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([department_code])
REFERENCES [lookup_department] ([code])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([escalation_level])
REFERENCES [lookup_ticket_escalation] ([code])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([level_code])
REFERENCES [ticket_level] ([code])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([link_asset_id])
REFERENCES [asset] ([asset_id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([link_contract_id])
REFERENCES [service_contract] ([contract_id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([pri_code])
REFERENCES [ticket_priority] ([code])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [product_catalog] ([product_id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([resolution_id])
REFERENCES [lookup_ticket_resolution] ([code])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([resolvedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([resolvedby_department_code])
REFERENCES [lookup_department] ([code])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([scode])
REFERENCES [ticket_severity] ([code])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([source_code])
REFERENCES [lookup_ticketsource] ([code])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([state_id])
REFERENCES [lookup_ticket_state] ([code])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([status_id])
REFERENCES [lookup_ticket_status] ([code])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([subcat_code1])
REFERENCES [ticket_category] ([id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([subcat_code2])
REFERENCES [ticket_category] ([id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([subcat_code3])
REFERENCES [ticket_category] ([id])
GO
ALTER TABLE [ticket]  WITH CHECK ADD FOREIGN KEY([user_group_id])
REFERENCES [user_group] ([group_id])
GO
ALTER TABLE [web_site_log]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [web_site] ([site_id])
GO
ALTER TABLE [web_site_log]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [message_template]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [message_template]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [payment_creditcard]  WITH CHECK ADD FOREIGN KEY([card_type])
REFERENCES [lookup_creditcard_types] ([code])
GO
ALTER TABLE [payment_creditcard]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [payment_creditcard]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [payment_creditcard]  WITH CHECK ADD FOREIGN KEY([order_id])
REFERENCES [order_entry] ([order_id])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([alert_call_type_id])
REFERENCES [lookup_call_types] ([code])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([assignedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([call_type_id])
REFERENCES [lookup_call_types] ([code])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([completedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([opp_id])
REFERENCES [opportunity_header] ([opp_id])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([owner])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([parent_id])
REFERENCES [call_log] ([call_id])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([priority_id])
REFERENCES [lookup_call_priority] ([code])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([reminder_type_id])
REFERENCES [lookup_call_reminder] ([code])
GO
ALTER TABLE [call_log]  WITH CHECK ADD FOREIGN KEY([result_id])
REFERENCES [lookup_call_result] ([result_id])
GO
ALTER TABLE [action_item_log]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_item_log]  WITH CHECK ADD FOREIGN KEY([item_id])
REFERENCES [action_item] ([item_id])
GO
ALTER TABLE [action_item_log]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [package]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [product_category] ([category_id])
GO
ALTER TABLE [package]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [package]  WITH CHECK ADD FOREIGN KEY([large_image_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [package]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [package]  WITH CHECK ADD FOREIGN KEY([small_image_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [package]  WITH CHECK ADD FOREIGN KEY([thumbnail_image_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [contact_imaddress]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [contact_imaddress]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [contact_imaddress]  WITH CHECK ADD FOREIGN KEY([imaddress_type])
REFERENCES [lookup_im_types] ([code])
GO
ALTER TABLE [contact_imaddress]  WITH CHECK ADD FOREIGN KEY([imaddress_service])
REFERENCES [lookup_im_services] ([code])
GO
ALTER TABLE [contact_imaddress]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [message]  WITH CHECK ADD FOREIGN KEY([access_type])
REFERENCES [lookup_access_types] ([code])
GO
ALTER TABLE [message]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [message]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_site]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_site]  WITH CHECK ADD FOREIGN KEY([layout_id])
REFERENCES [web_layout] ([layout_id])
GO
ALTER TABLE [web_site]  WITH CHECK ADD FOREIGN KEY([logo_image_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [web_site]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_site]  WITH CHECK ADD FOREIGN KEY([style_id])
REFERENCES [web_style] ([style_id])
GO
ALTER TABLE [project_assignments]  WITH CHECK ADD FOREIGN KEY([actual_loetype])
REFERENCES [lookup_project_loe] ([code])
GO
ALTER TABLE [project_assignments]  WITH CHECK ADD FOREIGN KEY([assignedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_assignments]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_assignments]  WITH CHECK ADD FOREIGN KEY([estimated_loetype])
REFERENCES [lookup_project_loe] ([code])
GO
ALTER TABLE [project_assignments]  WITH CHECK ADD FOREIGN KEY([folder_id])
REFERENCES [project_assignments_folder] ([folder_id])
GO
ALTER TABLE [project_assignments]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_assignments]  WITH CHECK ADD FOREIGN KEY([priority_id])
REFERENCES [lookup_project_priority] ([code])
GO
ALTER TABLE [project_assignments]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [project_assignments]  WITH CHECK ADD FOREIGN KEY([requirement_id])
REFERENCES [project_requirements] ([requirement_id])
GO
ALTER TABLE [project_assignments]  WITH CHECK ADD FOREIGN KEY([status_id])
REFERENCES [lookup_project_status] ([code])
GO
ALTER TABLE [project_assignments]  WITH CHECK ADD FOREIGN KEY([user_assign_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [service_contract]  WITH CHECK ADD FOREIGN KEY([account_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [service_contract]  WITH CHECK ADD FOREIGN KEY([category])
REFERENCES [lookup_sc_category] ([code])
GO
ALTER TABLE [service_contract]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [service_contract]  WITH CHECK ADD FOREIGN KEY([email_service_model])
REFERENCES [lookup_email_model] ([code])
GO
ALTER TABLE [service_contract]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [service_contract]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [service_contract]  WITH CHECK ADD FOREIGN KEY([onsite_service_model])
REFERENCES [lookup_onsite_model] ([code])
GO
ALTER TABLE [service_contract]  WITH CHECK ADD FOREIGN KEY([response_time])
REFERENCES [lookup_response_model] ([code])
GO
ALTER TABLE [service_contract]  WITH CHECK ADD FOREIGN KEY([telephone_service_model])
REFERENCES [lookup_phone_model] ([code])
GO
ALTER TABLE [service_contract]  WITH CHECK ADD FOREIGN KEY([type])
REFERENCES [lookup_sc_type] ([code])
GO
ALTER TABLE [campaign]  WITH CHECK ADD FOREIGN KEY([approvedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [campaign]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [campaign]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [ticket_category_assignment]  WITH CHECK ADD FOREIGN KEY([assigned_to])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [ticket_category_assignment]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [ticket_category] ([id])
GO
ALTER TABLE [ticket_category_assignment]  WITH CHECK ADD FOREIGN KEY([department_id])
REFERENCES [lookup_department] ([code])
GO
ALTER TABLE [ticket_category_assignment]  WITH CHECK ADD FOREIGN KEY([group_id])
REFERENCES [user_group] ([group_id])
GO
ALTER TABLE [action_item]  WITH CHECK ADD FOREIGN KEY([action_id])
REFERENCES [action_list] ([action_id])
GO
ALTER TABLE [action_item]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_item]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [lookup_contact_types]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [ticket_category_draft_assignment]  WITH CHECK ADD FOREIGN KEY([assigned_to])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [ticket_category_draft_assignment]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [ticket_category_draft] ([id])
GO
ALTER TABLE [ticket_category_draft_assignment]  WITH CHECK ADD FOREIGN KEY([department_id])
REFERENCES [lookup_department] ([code])
GO
ALTER TABLE [ticket_category_draft_assignment]  WITH CHECK ADD FOREIGN KEY([group_id])
REFERENCES [user_group] ([group_id])
GO
ALTER TABLE [contact_phone]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [contact_phone]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [contact_phone]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [contact_phone]  WITH CHECK ADD FOREIGN KEY([phone_type])
REFERENCES [lookup_contactphone_types] ([code])
GO
ALTER TABLE [task]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [lookup_task_category] ([code])
GO
ALTER TABLE [task]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [task]  WITH CHECK ADD FOREIGN KEY([estimatedloetype])
REFERENCES [lookup_task_loe] ([code])
GO
ALTER TABLE [task]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [task]  WITH CHECK ADD FOREIGN KEY([owner])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [task]  WITH CHECK ADD FOREIGN KEY([priority])
REFERENCES [lookup_task_priority] ([code])
GO
ALTER TABLE [task]  WITH CHECK ADD FOREIGN KEY([ticket_task_category_id])
REFERENCES [lookup_ticket_task_category] ([code])
GO
ALTER TABLE [action_list]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_list]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_list]  WITH CHECK ADD FOREIGN KEY([owner])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_assignments_folder]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_assignments_folder]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_assignments_folder]  WITH CHECK ADD FOREIGN KEY([parent_id])
REFERENCES [project_assignments_folder] ([folder_id])
GO
ALTER TABLE [project_assignments_folder]  WITH CHECK ADD FOREIGN KEY([requirement_id])
REFERENCES [project_requirements] ([requirement_id])
GO
ALTER TABLE [opportunity_component]  WITH CHECK ADD FOREIGN KEY([budget])
REFERENCES [lookup_opportunity_budget] ([code])
GO
ALTER TABLE [opportunity_component]  WITH CHECK ADD FOREIGN KEY([competitors])
REFERENCES [lookup_opportunity_competitors] ([code])
GO
ALTER TABLE [opportunity_component]  WITH CHECK ADD FOREIGN KEY([compelling_event])
REFERENCES [lookup_opportunity_event_compelling] ([code])
GO
ALTER TABLE [opportunity_component]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [opportunity_component]  WITH CHECK ADD FOREIGN KEY([environment])
REFERENCES [lookup_opportunity_environment] ([code])
GO
ALTER TABLE [opportunity_component]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [opportunity_component]  WITH CHECK ADD FOREIGN KEY([opp_id])
REFERENCES [opportunity_header] ([opp_id])
GO
ALTER TABLE [opportunity_component]  WITH CHECK ADD FOREIGN KEY([owner])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [opportunity_component]  WITH CHECK ADD FOREIGN KEY([stage])
REFERENCES [lookup_stage] ([code])
GO
ALTER TABLE [saved_criterialist]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [saved_criterialist]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [saved_criterialist]  WITH CHECK ADD FOREIGN KEY([owner])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_address]  WITH CHECK ADD FOREIGN KEY([address_type])
REFERENCES [lookup_orderaddress_types] ([code])
GO
ALTER TABLE [order_address]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_address]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [order_address]  WITH CHECK ADD FOREIGN KEY([order_id])
REFERENCES [order_entry] ([order_id])
GO
ALTER TABLE [contact]  WITH CHECK ADD FOREIGN KEY([access_type])
REFERENCES [lookup_access_types] ([code])
GO
ALTER TABLE [contact]  WITH CHECK ADD FOREIGN KEY([assistant])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [contact]  WITH CHECK ADD FOREIGN KEY([department])
REFERENCES [lookup_department] ([code])
GO
ALTER TABLE [contact]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [contact]  WITH CHECK ADD FOREIGN KEY([industry_temp_code])
REFERENCES [lookup_industry] ([code])
GO
ALTER TABLE [contact]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [contact]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [contact]  WITH CHECK ADD FOREIGN KEY([owner])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [contact]  WITH CHECK ADD FOREIGN KEY([rating])
REFERENCES [lookup_contact_rating] ([code])
GO
ALTER TABLE [contact]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [contact]  WITH CHECK ADD FOREIGN KEY([source])
REFERENCES [lookup_contact_source] ([code])
GO
ALTER TABLE [contact]  WITH CHECK ADD FOREIGN KEY([super])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [contact]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [contact_emailaddress]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [contact_emailaddress]  WITH CHECK ADD FOREIGN KEY([emailaddress_type])
REFERENCES [lookup_contactemail_types] ([code])
GO
ALTER TABLE [contact_emailaddress]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [contact_emailaddress]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [access_log]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_tips]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_tips]  WITH CHECK ADD FOREIGN KEY([link_help_id])
REFERENCES [help_contents] ([help_id])
GO
ALTER TABLE [help_tips]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_plan]  WITH CHECK ADD FOREIGN KEY([cat_code])
REFERENCES [action_plan_category] ([id])
GO
ALTER TABLE [action_plan]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_plan]  WITH CHECK ADD FOREIGN KEY([link_object_id])
REFERENCES [action_plan_constants] ([map_id])
GO
ALTER TABLE [action_plan]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [action_plan]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [action_plan]  WITH CHECK ADD FOREIGN KEY([subcat_code1])
REFERENCES [action_plan_category] ([id])
GO
ALTER TABLE [action_plan]  WITH CHECK ADD FOREIGN KEY([subcat_code2])
REFERENCES [action_plan_category] ([id])
GO
ALTER TABLE [action_plan]  WITH CHECK ADD FOREIGN KEY([subcat_code3])
REFERENCES [action_plan_category] ([id])
GO
ALTER TABLE [project_requirements]  WITH CHECK ADD FOREIGN KEY([actual_loetype])
REFERENCES [lookup_project_loe] ([code])
GO
ALTER TABLE [project_requirements]  WITH CHECK ADD FOREIGN KEY([approvedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_requirements]  WITH CHECK ADD FOREIGN KEY([closedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_requirements]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_requirements]  WITH CHECK ADD FOREIGN KEY([estimated_loetype])
REFERENCES [lookup_project_loe] ([code])
GO
ALTER TABLE [project_requirements]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_requirements]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [custom_field_record]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [custom_field_category] ([category_id])
GO
ALTER TABLE [custom_field_record]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [custom_field_record]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [netapp_contractexpiration_log]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [netapp_contractexpiration_log]  WITH CHECK ADD FOREIGN KEY([expiration_id])
REFERENCES [netapp_contractexpiration] ([expiration_id])
GO
ALTER TABLE [netapp_contractexpiration_log]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [product_catalog_pricing]  WITH CHECK ADD FOREIGN KEY([cost_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [product_catalog_pricing]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [product_catalog_pricing]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [product_catalog_pricing]  WITH CHECK ADD FOREIGN KEY([msrp_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [product_catalog_pricing]  WITH CHECK ADD FOREIGN KEY([price_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [product_catalog_pricing]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [product_catalog] ([product_id])
GO
ALTER TABLE [product_catalog_pricing]  WITH CHECK ADD FOREIGN KEY([recurring_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [product_catalog_pricing]  WITH CHECK ADD FOREIGN KEY([recurring_type])
REFERENCES [lookup_recurring_type] ([code])
GO
ALTER TABLE [product_catalog_pricing]  WITH CHECK ADD FOREIGN KEY([tax_id])
REFERENCES [lookup_product_tax] ([code])
GO
ALTER TABLE [contact_address]  WITH CHECK ADD FOREIGN KEY([address_type])
REFERENCES [lookup_contactaddress_types] ([code])
GO
ALTER TABLE [contact_address]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [contact_address]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [contact_address]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([customer_product_id])
REFERENCES [customer_product] ([customer_product_id])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([delivery_id])
REFERENCES [lookup_quote_delivery] ([code])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([group_id])
REFERENCES [quote_group] ([group_id])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([logo_file_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([opp_id])
REFERENCES [opportunity_header] ([opp_id])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([parent_id])
REFERENCES [quote_entry] ([quote_id])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [product_catalog] ([product_id])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([quote_terms_id])
REFERENCES [lookup_quote_terms] ([code])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([quote_type_id])
REFERENCES [lookup_quote_type] ([code])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([source_id])
REFERENCES [lookup_quote_source] ([code])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([status_id])
REFERENCES [lookup_quote_status] ([code])
GO
ALTER TABLE [quote_entry]  WITH CHECK ADD FOREIGN KEY([ticketid])
REFERENCES [ticket] ([ticketid])
GO
ALTER TABLE [report_queue]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [report_queue]  WITH CHECK ADD FOREIGN KEY([report_id])
REFERENCES [report] ([report_id])
GO
ALTER TABLE [project_news]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [project_news_category] ([category_id])
GO
ALTER TABLE [project_news]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_news]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_news]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [project_news]  WITH CHECK ADD FOREIGN KEY([template_id])
REFERENCES [lookup_news_template] ([code])
GO
ALTER TABLE [netapp_contractexpiration]  WITH CHECK ADD FOREIGN KEY([enteredBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [netapp_contractexpiration]  WITH CHECK ADD FOREIGN KEY([modifiedBy])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [opportunity_header]  WITH CHECK ADD FOREIGN KEY([access_type])
REFERENCES [lookup_access_types] ([code])
GO
ALTER TABLE [opportunity_header]  WITH CHECK ADD FOREIGN KEY([acctlink])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [opportunity_header]  WITH CHECK ADD FOREIGN KEY([contactlink])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [opportunity_header]  WITH CHECK ADD FOREIGN KEY([contact_org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [opportunity_header]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [opportunity_header]  WITH CHECK ADD FOREIGN KEY([manager])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [opportunity_header]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [opportunity_header]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [help_notes]  WITH CHECK ADD FOREIGN KEY([completedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_notes]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_notes]  WITH CHECK ADD FOREIGN KEY([link_help_id])
REFERENCES [help_contents] ([help_id])
GO
ALTER TABLE [help_notes]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [web_site_access_log]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [web_site] ([site_id])
GO
ALTER TABLE [web_site_access_log]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [organization_phone]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [organization_phone]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [organization_phone]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [organization_phone]  WITH CHECK ADD FOREIGN KEY([phone_type])
REFERENCES [lookup_orgphone_types] ([code])
GO
ALTER TABLE [knowledge_base]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [ticket_category] ([id])
GO
ALTER TABLE [knowledge_base]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [knowledge_base]  WITH CHECK ADD FOREIGN KEY([item_id])
REFERENCES [project_files] ([item_id])
GO
ALTER TABLE [knowledge_base]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [report_criteria]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [report_criteria]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [report_criteria]  WITH CHECK ADD FOREIGN KEY([owner])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [report_criteria]  WITH CHECK ADD FOREIGN KEY([report_id])
REFERENCES [report] ([report_id])
GO
ALTER TABLE [organization]  WITH CHECK ADD FOREIGN KEY([account_size])
REFERENCES [lookup_account_size] ([code])
GO
ALTER TABLE [organization]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [organization]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [organization]  WITH CHECK ADD FOREIGN KEY([owner])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [organization]  WITH CHECK ADD FOREIGN KEY([rating])
REFERENCES [lookup_contact_rating] ([code])
GO
ALTER TABLE [organization]  WITH CHECK ADD FOREIGN KEY([segment_id])
REFERENCES [lookup_segments] ([code])
GO
ALTER TABLE [organization]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [organization]  WITH CHECK ADD FOREIGN KEY([source])
REFERENCES [lookup_contact_source] ([code])
GO
ALTER TABLE [organization]  WITH CHECK ADD FOREIGN KEY([sub_segment_id])
REFERENCES [lookup_sub_segment] ([code])
GO
ALTER TABLE [help_business_rules]  WITH CHECK ADD FOREIGN KEY([completedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_business_rules]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [help_business_rules]  WITH CHECK ADD FOREIGN KEY([link_help_id])
REFERENCES [help_contents] ([help_id])
GO
ALTER TABLE [help_business_rules]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_team]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_team]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_team]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [project_team]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [project_team]  WITH CHECK ADD FOREIGN KEY([userlevel])
REFERENCES [lookup_project_role] ([code])
GO
ALTER TABLE [revenue_detail]  WITH CHECK ADD FOREIGN KEY([enteredby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [revenue_detail]  WITH CHECK ADD FOREIGN KEY([modifiedby])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [revenue_detail]  WITH CHECK ADD FOREIGN KEY([owner])
REFERENCES [access] ([user_id])
GO
ALTER TABLE [revenue_detail]  WITH CHECK ADD FOREIGN KEY([revenue_id])
REFERENCES [revenue] ([id])
GO
ALTER TABLE [revenue_detail]  WITH CHECK ADD FOREIGN KEY([type])
REFERENCES [lookup_revenue_types] ([code])
GO
ALTER TABLE [business_process_component_parameter]  WITH CHECK ADD FOREIGN KEY([component_id])
REFERENCES [business_process_component] ([id])
GO
ALTER TABLE [business_process_component_parameter]  WITH CHECK ADD FOREIGN KEY([parameter_id])
REFERENCES [business_process_parameter_library] ([parameter_id])
GO
ALTER TABLE [order_product_option_timestamp]  WITH CHECK ADD FOREIGN KEY([order_product_option_id])
REFERENCES [order_product_options] ([order_product_option_id])
GO
ALTER TABLE [order_product_option_float]  WITH CHECK ADD FOREIGN KEY([order_product_option_id])
REFERENCES [order_product_options] ([order_product_option_id])
GO
ALTER TABLE [order_product_option_text]  WITH CHECK ADD FOREIGN KEY([order_product_option_id])
REFERENCES [order_product_options] ([order_product_option_id])
GO
ALTER TABLE [order_product_option_integer]  WITH CHECK ADD FOREIGN KEY([order_product_option_id])
REFERENCES [order_product_options] ([order_product_option_id])
GO
ALTER TABLE [order_product_option_boolean]  WITH CHECK ADD FOREIGN KEY([order_product_option_id])
REFERENCES [order_product_options] ([order_product_option_id])
GO
ALTER TABLE [report_criteria_parameter]  WITH CHECK ADD FOREIGN KEY([criteria_id])
REFERENCES [report_criteria] ([criteria_id])
GO
ALTER TABLE [business_process_parameter]  WITH CHECK ADD FOREIGN KEY([process_id])
REFERENCES [business_process] ([process_id])
GO
ALTER TABLE [business_process_component]  WITH CHECK ADD FOREIGN KEY([component_id])
REFERENCES [business_process_component_library] ([component_id])
GO
ALTER TABLE [business_process_component]  WITH CHECK ADD FOREIGN KEY([parent_id])
REFERENCES [business_process_component] ([id])
GO
ALTER TABLE [business_process_component]  WITH CHECK ADD FOREIGN KEY([process_id])
REFERENCES [business_process] ([process_id])
GO
ALTER TABLE [business_process_events]  WITH CHECK ADD FOREIGN KEY([process_id])
REFERENCES [business_process] ([process_id])
GO
ALTER TABLE [business_process_hook]  WITH CHECK ADD FOREIGN KEY([process_id])
REFERENCES [business_process] ([process_id])
GO
ALTER TABLE [business_process_hook]  WITH CHECK ADD FOREIGN KEY([trigger_id])
REFERENCES [business_process_hook_triggers] ([trigger_id])
GO
ALTER TABLE [project_accounts]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [project_accounts]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [autoguide_inventory]  WITH CHECK ADD FOREIGN KEY([account_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [autoguide_inventory]  WITH CHECK ADD FOREIGN KEY([vehicle_id])
REFERENCES [autoguide_vehicle] ([vehicle_id])
GO
ALTER TABLE [account_type_levels]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [account_type_levels]  WITH CHECK ADD FOREIGN KEY([type_id])
REFERENCES [lookup_account_types] ([code])
GO
ALTER TABLE [news]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [document_accounts]  WITH CHECK ADD FOREIGN KEY([document_store_id])
REFERENCES [document_store] ([document_store_id])
GO
ALTER TABLE [document_accounts]  WITH CHECK ADD FOREIGN KEY([org_id])
REFERENCES [organization] ([org_id])
GO
ALTER TABLE [active_survey_answer_avg]  WITH CHECK ADD FOREIGN KEY([item_id])
REFERENCES [active_survey_items] ([item_id])
GO
ALTER TABLE [active_survey_answer_avg]  WITH CHECK ADD FOREIGN KEY([question_id])
REFERENCES [active_survey_questions] ([question_id])
GO
ALTER TABLE [active_survey_answer_items]  WITH CHECK ADD FOREIGN KEY([answer_id])
REFERENCES [active_survey_answers] ([answer_id])
GO
ALTER TABLE [active_survey_answer_items]  WITH CHECK ADD FOREIGN KEY([item_id])
REFERENCES [active_survey_items] ([item_id])
GO
ALTER TABLE [active_survey_answers]  WITH CHECK ADD FOREIGN KEY([question_id])
REFERENCES [active_survey_questions] ([question_id])
GO
ALTER TABLE [active_survey_answers]  WITH CHECK ADD FOREIGN KEY([response_id])
REFERENCES [active_survey_responses] ([response_id])
GO
ALTER TABLE [action_plan_editor_lookup]  WITH CHECK ADD FOREIGN KEY([constant_id])
REFERENCES [action_plan_constants] ([map_id])
GO
ALTER TABLE [action_plan_editor_lookup]  WITH CHECK ADD FOREIGN KEY([module_id])
REFERENCES [permission_category] ([category_id])
GO
ALTER TABLE [step_action_map]  WITH CHECK ADD FOREIGN KEY([action_constant_id])
REFERENCES [lookup_step_actions] ([constant_id])
GO
ALTER TABLE [step_action_map]  WITH CHECK ADD FOREIGN KEY([constant_id])
REFERENCES [action_plan_constants] ([map_id])
GO
ALTER TABLE [report_queue_criteria]  WITH CHECK ADD FOREIGN KEY([queue_id])
REFERENCES [report_queue] ([queue_id])
GO
ALTER TABLE [taskcategorylink_news]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [lookup_task_category] ([code])
GO
ALTER TABLE [taskcategorylink_news]  WITH CHECK ADD FOREIGN KEY([news_id])
REFERENCES [project_news] ([news_id])
GO
ALTER TABLE [ticket_category_plan_map]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [ticket_category] ([id])
GO
ALTER TABLE [ticket_category_plan_map]  WITH CHECK ADD FOREIGN KEY([plan_id])
REFERENCES [action_plan] ([plan_id])
GO
ALTER TABLE [custom_field_data]  WITH CHECK ADD FOREIGN KEY([field_id])
REFERENCES [custom_field_info] ([field_id])
GO
ALTER TABLE [custom_field_data]  WITH CHECK ADD FOREIGN KEY([record_id])
REFERENCES [custom_field_record] ([record_id])
GO
ALTER TABLE [quote_product]  WITH CHECK ADD FOREIGN KEY([price_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [quote_product]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [product_catalog] ([product_id])
GO
ALTER TABLE [quote_product]  WITH CHECK ADD FOREIGN KEY([quote_id])
REFERENCES [quote_entry] ([quote_id])
GO
ALTER TABLE [quote_product]  WITH CHECK ADD FOREIGN KEY([recurring_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [quote_product]  WITH CHECK ADD FOREIGN KEY([recurring_type])
REFERENCES [lookup_recurring_type] ([code])
GO
ALTER TABLE [quote_product]  WITH CHECK ADD FOREIGN KEY([status_id])
REFERENCES [lookup_quote_status] ([code])
GO
ALTER TABLE [quote_remark]  WITH CHECK ADD FOREIGN KEY([quote_id])
REFERENCES [quote_entry] ([quote_id])
GO
ALTER TABLE [quote_remark]  WITH CHECK ADD FOREIGN KEY([remark_id])
REFERENCES [lookup_quote_remarks] ([code])
GO
ALTER TABLE [quote_condition]  WITH CHECK ADD FOREIGN KEY([condition_id])
REFERENCES [lookup_quote_condition] ([code])
GO
ALTER TABLE [quote_condition]  WITH CHECK ADD FOREIGN KEY([quote_id])
REFERENCES [quote_entry] ([quote_id])
GO
ALTER TABLE [project_requirements_map]  WITH CHECK ADD FOREIGN KEY([assignment_id])
REFERENCES [project_assignments] ([assignment_id])
GO
ALTER TABLE [project_requirements_map]  WITH CHECK ADD FOREIGN KEY([folder_id])
REFERENCES [project_assignments_folder] ([folder_id])
GO
ALTER TABLE [project_requirements_map]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [project_requirements_map]  WITH CHECK ADD FOREIGN KEY([requirement_id])
REFERENCES [project_requirements] ([requirement_id])
GO
ALTER TABLE [taskcategory_project]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [lookup_task_category] ([code])
GO
ALTER TABLE [taskcategory_project]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [action_phase]  WITH CHECK ADD FOREIGN KEY([parent_id])
REFERENCES [action_phase] ([phase_id])
GO
ALTER TABLE [action_phase]  WITH CHECK ADD FOREIGN KEY([plan_id])
REFERENCES [action_plan] ([plan_id])
GO
ALTER TABLE [ticket_category_draft_plan_map]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [ticket_category_draft] ([id])
GO
ALTER TABLE [ticket_category_draft_plan_map]  WITH CHECK ADD FOREIGN KEY([plan_id])
REFERENCES [action_plan] ([plan_id])
GO
ALTER TABLE [tasklink_ticket]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [lookup_ticket_task_category] ([code])
GO
ALTER TABLE [tasklink_ticket]  WITH CHECK ADD FOREIGN KEY([task_id])
REFERENCES [task] ([task_id])
GO
ALTER TABLE [tasklink_ticket]  WITH CHECK ADD FOREIGN KEY([ticket_id])
REFERENCES [ticket] ([ticketid])
GO
ALTER TABLE [tasklink_contact]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [tasklink_contact]  WITH CHECK ADD FOREIGN KEY([task_id])
REFERENCES [task] ([task_id])
GO
ALTER TABLE [scheduled_recipient]  WITH CHECK ADD FOREIGN KEY([campaign_id])
REFERENCES [campaign] ([campaign_id])
GO
ALTER TABLE [scheduled_recipient]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [excluded_recipient]  WITH CHECK ADD FOREIGN KEY([campaign_id])
REFERENCES [campaign] ([campaign_id])
GO
ALTER TABLE [excluded_recipient]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [contact_type_levels]  WITH CHECK ADD FOREIGN KEY([contact_id])
REFERENCES [contact] ([contact_id])
GO
ALTER TABLE [contact_type_levels]  WITH CHECK ADD FOREIGN KEY([type_id])
REFERENCES [lookup_contact_types] ([code])
GO
ALTER TABLE [opportunity_component_levels]  WITH CHECK ADD FOREIGN KEY([opp_id])
REFERENCES [opportunity_component] ([id])
GO
ALTER TABLE [opportunity_component_levels]  WITH CHECK ADD FOREIGN KEY([type_id])
REFERENCES [lookup_opportunity_types] ([code])
GO
ALTER TABLE [web_style]  WITH CHECK ADD FOREIGN KEY([layout_id])
REFERENCES [web_layout] ([layout_id])
GO
ALTER TABLE [events_log]  WITH CHECK ADD FOREIGN KEY([event_id])
REFERENCES [events] ([event_id])
GO
ALTER TABLE [sync_map]  WITH CHECK ADD FOREIGN KEY([client_id])
REFERENCES [sync_client] ([client_id])
GO
ALTER TABLE [sync_map]  WITH CHECK ADD FOREIGN KEY([table_id])
REFERENCES [sync_table] ([table_id])
GO
ALTER TABLE [sync_conflict_log]  WITH CHECK ADD FOREIGN KEY([client_id])
REFERENCES [sync_client] ([client_id])
GO
ALTER TABLE [sync_conflict_log]  WITH CHECK ADD FOREIGN KEY([table_id])
REFERENCES [sync_table] ([table_id])
GO
ALTER TABLE [sync_log]  WITH CHECK ADD FOREIGN KEY([client_id])
REFERENCES [sync_client] ([client_id])
GO
ALTER TABLE [sync_log]  WITH CHECK ADD FOREIGN KEY([system_id])
REFERENCES [sync_system] ([system_id])
GO
ALTER TABLE [process_log]  WITH CHECK ADD FOREIGN KEY([client_id])
REFERENCES [sync_client] ([client_id])
GO
ALTER TABLE [process_log]  WITH CHECK ADD FOREIGN KEY([system_id])
REFERENCES [sync_system] ([system_id])
GO
ALTER TABLE [saved_criteriaelement]  WITH CHECK ADD FOREIGN KEY([field])
REFERENCES [search_fields] ([id])
GO
ALTER TABLE [saved_criteriaelement]  WITH CHECK ADD FOREIGN KEY([operatorid])
REFERENCES [field_types] ([id])
GO
ALTER TABLE [saved_criteriaelement]  WITH CHECK ADD FOREIGN KEY([id])
REFERENCES [saved_criterialist] ([id])
GO
ALTER TABLE [campaign_list_groups]  WITH CHECK ADD FOREIGN KEY([campaign_id])
REFERENCES [campaign] ([campaign_id])
GO
ALTER TABLE [campaign_list_groups]  WITH CHECK ADD FOREIGN KEY([group_id])
REFERENCES [saved_criterialist] ([id])
GO
ALTER TABLE [tasklink_project]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [tasklink_project]  WITH CHECK ADD FOREIGN KEY([task_id])
REFERENCES [task] ([task_id])
GO
ALTER TABLE [quote_product_options]  WITH CHECK ADD FOREIGN KEY([item_id])
REFERENCES [quote_product] ([item_id])
GO
ALTER TABLE [quote_product_options]  WITH CHECK ADD FOREIGN KEY([price_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [quote_product_options]  WITH CHECK ADD FOREIGN KEY([product_option_id])
REFERENCES [product_option_map] ([product_option_id])
GO
ALTER TABLE [quote_product_options]  WITH CHECK ADD FOREIGN KEY([recurring_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [quote_product_options]  WITH CHECK ADD FOREIGN KEY([recurring_type])
REFERENCES [lookup_recurring_type] ([code])
GO
ALTER TABLE [quote_product_options]  WITH CHECK ADD FOREIGN KEY([status_id])
REFERENCES [lookup_quote_status] ([code])
GO
ALTER TABLE [sync_table]  WITH CHECK ADD FOREIGN KEY([system_id])
REFERENCES [sync_system] ([system_id])
GO
ALTER TABLE [action_step]  WITH CHECK ADD FOREIGN KEY([action_id])
REFERENCES [lookup_step_actions] ([constant_id])
GO
ALTER TABLE [action_step]  WITH CHECK ADD FOREIGN KEY([campaign_id])
REFERENCES [campaign] ([campaign_id])
GO
ALTER TABLE [action_step]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [custom_field_category] ([category_id])
GO
ALTER TABLE [action_step]  WITH CHECK ADD FOREIGN KEY([department_id])
REFERENCES [lookup_department] ([code])
GO
ALTER TABLE [action_step]  WITH CHECK ADD FOREIGN KEY([duration_type_id])
REFERENCES [lookup_duration_type] ([code])
GO
ALTER TABLE [action_step]  WITH CHECK ADD FOREIGN KEY([field_id])
REFERENCES [custom_field_info] ([field_id])
GO
ALTER TABLE [action_step]  WITH CHECK ADD FOREIGN KEY([group_id])
REFERENCES [user_group] ([group_id])
GO
ALTER TABLE [action_step]  WITH CHECK ADD FOREIGN KEY([parent_id])
REFERENCES [action_step] ([step_id])
GO
ALTER TABLE [action_step]  WITH CHECK ADD FOREIGN KEY([phase_id])
REFERENCES [action_phase] ([phase_id])
GO
ALTER TABLE [action_step]  WITH CHECK ADD FOREIGN KEY([role_id])
REFERENCES [role] ([role_id])
GO
ALTER TABLE [action_step_account_types]  WITH CHECK ADD FOREIGN KEY([step_id])
REFERENCES [action_step] ([step_id])
GO
ALTER TABLE [action_step_account_types]  WITH CHECK ADD FOREIGN KEY([type_id])
REFERENCES [lookup_account_types] ([code])
GO
ALTER TABLE [service_contract_products]  WITH CHECK ADD FOREIGN KEY([link_contract_id])
REFERENCES [service_contract] ([contract_id])
GO
ALTER TABLE [service_contract_products]  WITH CHECK ADD FOREIGN KEY([link_product_id])
REFERENCES [product_catalog] ([product_id])
GO
ALTER TABLE [active_campaign_groups]  WITH CHECK ADD FOREIGN KEY([campaign_id])
REFERENCES [campaign] ([campaign_id])
GO
ALTER TABLE [campaign_survey_link]  WITH CHECK ADD FOREIGN KEY([campaign_id])
REFERENCES [campaign] ([campaign_id])
GO
ALTER TABLE [campaign_survey_link]  WITH CHECK ADD FOREIGN KEY([survey_id])
REFERENCES [survey] ([survey_id])
GO
ALTER TABLE [campaign_run]  WITH CHECK ADD FOREIGN KEY([campaign_id])
REFERENCES [campaign] ([campaign_id])
GO
ALTER TABLE [campaign_group_map]  WITH CHECK ADD FOREIGN KEY([campaign_id])
REFERENCES [campaign] ([campaign_id])
GO
ALTER TABLE [campaign_group_map]  WITH CHECK ADD FOREIGN KEY([user_group_id])
REFERENCES [user_group] ([group_id])
GO
ALTER TABLE [business_process_hook_triggers]  WITH CHECK ADD FOREIGN KEY([hook_id])
REFERENCES [business_process_hook_library] ([hook_id])
GO
ALTER TABLE [lookup_project_permission]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [lookup_project_permission_category] ([code])
GO
ALTER TABLE [lookup_project_permission]  WITH CHECK ADD FOREIGN KEY([default_role])
REFERENCES [lookup_project_role] ([code])
GO
ALTER TABLE [lookup_call_result]  WITH CHECK ADD FOREIGN KEY([next_call_type_id])
REFERENCES [call_log] ([call_id])
GO
ALTER TABLE [ticketlink_project]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [ticketlink_project]  WITH CHECK ADD FOREIGN KEY([ticket_id])
REFERENCES [ticket] ([ticketid])
GO
ALTER TABLE [project_permissions]  WITH CHECK ADD FOREIGN KEY([permission_id])
REFERENCES [lookup_project_permission] ([code])
GO
ALTER TABLE [project_permissions]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [project_permissions]  WITH CHECK ADD FOREIGN KEY([userlevel])
REFERENCES [lookup_project_role] ([code])
GO
ALTER TABLE [quote_product_option_float]  WITH CHECK ADD FOREIGN KEY([quote_product_option_id])
REFERENCES [quote_product_options] ([quote_product_option_id])
GO
ALTER TABLE [quote_product_option_timestamp]  WITH CHECK ADD FOREIGN KEY([quote_product_option_id])
REFERENCES [quote_product_options] ([quote_product_option_id])
GO
ALTER TABLE [quote_product_option_boolean]  WITH CHECK ADD FOREIGN KEY([quote_product_option_id])
REFERENCES [quote_product_options] ([quote_product_option_id])
GO
ALTER TABLE [quote_product_option_integer]  WITH CHECK ADD FOREIGN KEY([quote_product_option_id])
REFERENCES [quote_product_options] ([quote_product_option_id])
GO
ALTER TABLE [quote_product_option_text]  WITH CHECK ADD FOREIGN KEY([quote_product_option_id])
REFERENCES [quote_product_options] ([quote_product_option_id])
GO
ALTER TABLE [sync_transaction_log]  WITH CHECK ADD FOREIGN KEY([log_id])
REFERENCES [sync_log] ([log_id])
GO
ALTER TABLE [action_step_lookup]  WITH CHECK ADD FOREIGN KEY([step_id])
REFERENCES [action_step] ([step_id])
GO
ALTER TABLE [lookup_document_store_permission]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [lookup_document_store_permission_category] ([code])
GO
ALTER TABLE [lookup_document_store_permission]  WITH CHECK ADD FOREIGN KEY([default_role])
REFERENCES [lookup_document_store_role] ([code])
GO
ALTER TABLE [relationship]  WITH CHECK ADD FOREIGN KEY([type_id])
REFERENCES [lookup_relationship_types] ([type_id])
GO
ALTER TABLE [role_permission]  WITH CHECK ADD FOREIGN KEY([permission_id])
REFERENCES [permission] ([permission_id])
GO
ALTER TABLE [role_permission]  WITH CHECK ADD FOREIGN KEY([role_id])
REFERENCES [role] ([role_id])
GO
ALTER TABLE [autoguide_vehicle]  WITH CHECK ADD FOREIGN KEY([make_id])
REFERENCES [autoguide_make] ([make_id])
GO
ALTER TABLE [autoguide_vehicle]  WITH CHECK ADD FOREIGN KEY([model_id])
REFERENCES [autoguide_model] ([model_id])
GO
ALTER TABLE [autoguide_model]  WITH CHECK ADD FOREIGN KEY([make_id])
REFERENCES [autoguide_make] ([make_id])
GO
ALTER TABLE [document_store_permissions]  WITH CHECK ADD FOREIGN KEY([document_store_id])
REFERENCES [document_store] ([document_store_id])
GO
ALTER TABLE [document_store_permissions]  WITH CHECK ADD FOREIGN KEY([permission_id])
REFERENCES [lookup_document_store_permission] ([code])
GO
ALTER TABLE [document_store_permissions]  WITH CHECK ADD FOREIGN KEY([userlevel])
REFERENCES [lookup_document_store_role] ([code])
GO
ALTER TABLE [order_product_options]  WITH CHECK ADD FOREIGN KEY([item_id])
REFERENCES [order_product] ([item_id])
GO
ALTER TABLE [order_product_options]  WITH CHECK ADD FOREIGN KEY([price_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [order_product_options]  WITH CHECK ADD FOREIGN KEY([product_option_id])
REFERENCES [product_option_map] ([product_option_id])
GO
ALTER TABLE [order_product_options]  WITH CHECK ADD FOREIGN KEY([recurring_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [order_product_options]  WITH CHECK ADD FOREIGN KEY([recurring_type])
REFERENCES [lookup_recurring_type] ([code])
GO
ALTER TABLE [order_product_options]  WITH CHECK ADD FOREIGN KEY([status_id])
REFERENCES [lookup_order_status] ([code])
GO
ALTER TABLE [product_option_values]  WITH CHECK ADD FOREIGN KEY([cost_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [product_option_values]  WITH CHECK ADD FOREIGN KEY([msrp_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [product_option_values]  WITH CHECK ADD FOREIGN KEY([option_id])
REFERENCES [product_option] ([option_id])
GO
ALTER TABLE [product_option_values]  WITH CHECK ADD FOREIGN KEY([price_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [product_option_values]  WITH CHECK ADD FOREIGN KEY([recurring_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [product_option_values]  WITH CHECK ADD FOREIGN KEY([recurring_type])
REFERENCES [lookup_recurring_type] ([code])
GO
ALTER TABLE [order_product]  WITH CHECK ADD FOREIGN KEY([msrp_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [order_product]  WITH CHECK ADD FOREIGN KEY([order_id])
REFERENCES [order_entry] ([order_id])
GO
ALTER TABLE [order_product]  WITH CHECK ADD FOREIGN KEY([price_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [order_product]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [product_catalog] ([product_id])
GO
ALTER TABLE [order_product]  WITH CHECK ADD FOREIGN KEY([recurring_currency])
REFERENCES [lookup_currency] ([code])
GO
ALTER TABLE [order_product]  WITH CHECK ADD FOREIGN KEY([recurring_type])
REFERENCES [lookup_recurring_type] ([code])
GO
ALTER TABLE [order_product]  WITH CHECK ADD FOREIGN KEY([status_id])
REFERENCES [lookup_order_status] ([code])
GO
ALTER TABLE [business_process_hook_library]  WITH CHECK ADD FOREIGN KEY([link_module_id])
REFERENCES [permission_category] ([category_id])
GO
ALTER TABLE [business_process]  WITH CHECK ADD FOREIGN KEY([link_module_id])
REFERENCES [permission_category] ([category_id])
GO
ALTER TABLE [help_module]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [permission_category] ([category_id])
GO
ALTER TABLE [category_editor_lookup]  WITH CHECK ADD FOREIGN KEY([module_id])
REFERENCES [permission_category] ([category_id])
GO
ALTER TABLE [permission]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [permission_category] ([category_id])
GO
ALTER TABLE [lookup_lists_lookup]  WITH CHECK ADD FOREIGN KEY([module_id])
REFERENCES [permission_category] ([category_id])
GO
ALTER TABLE [custom_list_view_editor]  WITH CHECK ADD FOREIGN KEY([module_id])
REFERENCES [permission_category] ([category_id])
GO
ALTER TABLE [module_field_categorylink]  WITH CHECK ADD FOREIGN KEY([module_id])
REFERENCES [permission_category] ([category_id])
GO
ALTER TABLE [product_option_configurator]  WITH CHECK ADD FOREIGN KEY([result_type])
REFERENCES [lookup_product_conf_result] ([code])
GO
ALTER TABLE [product_category_map]  WITH CHECK ADD FOREIGN KEY([category1_id])
REFERENCES [product_category] ([category_id])
GO
ALTER TABLE [product_category_map]  WITH CHECK ADD FOREIGN KEY([category2_id])
REFERENCES [product_category] ([category_id])
GO
ALTER TABLE [product_catalog_category_map]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [product_category] ([category_id])
GO
ALTER TABLE [product_catalog_category_map]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [product_catalog] ([product_id])
GO
ALTER TABLE [product_option]  WITH CHECK ADD FOREIGN KEY([configurator_id])
REFERENCES [product_option_configurator] ([configurator_id])
GO
ALTER TABLE [product_option]  WITH CHECK ADD FOREIGN KEY([parent_id])
REFERENCES [product_option] ([option_id])
GO
ALTER TABLE [autoguide_inventory_options]  WITH CHECK ADD FOREIGN KEY([inventory_id])
REFERENCES [autoguide_inventory] ([inventory_id])
GO
ALTER TABLE [autoguide_ad_run]  WITH CHECK ADD FOREIGN KEY([inventory_id])
REFERENCES [autoguide_inventory] ([inventory_id])
GO
ALTER TABLE [product_option_map]  WITH CHECK ADD FOREIGN KEY([option_id])
REFERENCES [product_option] ([option_id])
GO
ALTER TABLE [product_option_map]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [product_catalog] ([product_id])
GO
ALTER TABLE [product_option_boolean]  WITH CHECK ADD FOREIGN KEY([product_option_id])
REFERENCES [product_option] ([option_id])
GO
ALTER TABLE [product_option_float]  WITH CHECK ADD FOREIGN KEY([product_option_id])
REFERENCES [product_option] ([option_id])
GO
ALTER TABLE [product_option_timestamp]  WITH CHECK ADD FOREIGN KEY([product_option_id])
REFERENCES [product_option] ([option_id])
GO
ALTER TABLE [product_option_integer]  WITH CHECK ADD FOREIGN KEY([product_option_id])
REFERENCES [product_option] ([option_id])
GO
ALTER TABLE [product_option_text]  WITH CHECK ADD FOREIGN KEY([product_option_id])
REFERENCES [product_option] ([option_id])
GO
ALTER TABLE [ticket_activity_item]  WITH CHECK ADD FOREIGN KEY([link_form_id])
REFERENCES [ticket_csstm_form] ([form_id])
GO
ALTER TABLE [asset_materials_map]  WITH CHECK ADD FOREIGN KEY([asset_id])
REFERENCES [asset] ([asset_id])
GO
ALTER TABLE [asset_materials_map]  WITH CHECK ADD FOREIGN KEY([code])
REFERENCES [lookup_asset_materials] ([code])
GO
ALTER TABLE [custom_list_view]  WITH CHECK ADD FOREIGN KEY([editor_id])
REFERENCES [custom_list_view_editor] ([editor_id])
GO
ALTER TABLE [survey_questions]  WITH CHECK ADD FOREIGN KEY([survey_id])
REFERENCES [survey] ([survey_id])
GO
ALTER TABLE [survey_questions]  WITH CHECK ADD FOREIGN KEY([type])
REFERENCES [lookup_survey_types] ([code])
GO
ALTER TABLE [active_survey_questions]  WITH CHECK ADD FOREIGN KEY([active_survey_id])
REFERENCES [active_survey] ([active_survey_id])
GO
ALTER TABLE [active_survey_questions]  WITH CHECK ADD FOREIGN KEY([type])
REFERENCES [lookup_survey_types] ([code])
GO
ALTER TABLE [viewpoint_permission]  WITH CHECK ADD FOREIGN KEY([permission_id])
REFERENCES [permission] ([permission_id])
GO
ALTER TABLE [viewpoint_permission]  WITH CHECK ADD FOREIGN KEY([viewpoint_id])
REFERENCES [viewpoint] ([viewpoint_id])
GO
ALTER TABLE [custom_list_view_field]  WITH CHECK ADD FOREIGN KEY([view_id])
REFERENCES [custom_list_view] ([view_id])
GO
ALTER TABLE [trouble_asset_replacement]  WITH CHECK ADD FOREIGN KEY([link_form_id])
REFERENCES [ticket_sun_form] ([form_id])
GO
ALTER TABLE [action_item_work_selection]  WITH CHECK ADD FOREIGN KEY([item_work_id])
REFERENCES [action_item_work] ([item_work_id])
GO
ALTER TABLE [action_item_work_selection]  WITH CHECK ADD FOREIGN KEY([selection])
REFERENCES [action_step_lookup] ([code])
GO
ALTER TABLE [survey_items]  WITH CHECK ADD FOREIGN KEY([question_id])
REFERENCES [survey_questions] ([question_id])
GO
ALTER TABLE [custom_field_category]  WITH CHECK ADD FOREIGN KEY([module_id])
REFERENCES [module_field_categorylink] ([category_id])
GO
ALTER TABLE [active_survey_responses]  WITH CHECK ADD FOREIGN KEY([active_survey_id])
REFERENCES [active_survey] ([active_survey_id])
GO
ALTER TABLE [custom_field_group]  WITH CHECK ADD FOREIGN KEY([category_id])
REFERENCES [custom_field_category] ([category_id])
GO
ALTER TABLE [lookup_sub_segment]  WITH CHECK ADD FOREIGN KEY([segment_id])
REFERENCES [lookup_segments] ([code])
GO
ALTER TABLE [product_keyword_map]  WITH CHECK ADD FOREIGN KEY([keyword_id])
REFERENCES [lookup_product_keyword] ([code])
GO
ALTER TABLE [product_keyword_map]  WITH CHECK ADD FOREIGN KEY([product_id])
REFERENCES [product_catalog] ([product_id])
GO
ALTER TABLE [project_news_category]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [project_ticket_count]  WITH CHECK ADD FOREIGN KEY([project_id])
REFERENCES [projects] ([project_id])
GO
ALTER TABLE [active_survey_items]  WITH CHECK ADD FOREIGN KEY([question_id])
REFERENCES [active_survey_questions] ([question_id])
GO
ALTER TABLE [custom_field_info]  WITH CHECK ADD FOREIGN KEY([group_id])
REFERENCES [custom_field_group] ([group_id])
GO
ALTER TABLE [business_process_component_result_lookup]  WITH CHECK ADD FOREIGN KEY([component_id])
REFERENCES [business_process_component_library] ([component_id])
GO
ALTER TABLE [access]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [action_plan_category_draft]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [ticket_category]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [ticket_category_draft]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [asset_category_draft]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [asset_category]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [ticket_defect]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [action_plan_category]  WITH CHECK ADD FOREIGN KEY([site_id])
REFERENCES [lookup_site_id] ([code])
GO
ALTER TABLE [custom_field_lookup]  WITH CHECK ADD FOREIGN KEY([field_id])
REFERENCES [custom_field_info] ([field_id])
