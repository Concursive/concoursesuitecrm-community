-- Script (C) 2005 Concursive Corporation, all rights reserved
-- Database upgrade v3.1 (2005-07-08)

DELETE FROM events WHERE task = 'org.aspcfs.apps.users.UserCleanup#doTask';
DELETE FROM events WHERE extrainfo = 'http://${WEBSERVER.URL}/ProcessSystem.do?command=ClearGraphData';
DELETE FROM events WHERE task = 'org.aspcfs.apps.reportRunner.ReportCleanup#doTask';
DELETE FROM events WHERE task = 'org.aspcfs.apps.reportRunner.ReportRunner#doTask';
DELETE FROM events WHERE task = 'org.aspcfs.modules.base.Trasher#doTask';
DELETE FROM events WHERE task = 'org.aspcfs.apps.notifier.Notifier#doTask';

UPDATE field_types SET enabled = 0 WHERE enabled IS NULL;
ALTER TABLE [field_types] ALTER COLUMN [enabled] [bit] NOT NULL;

ALTER TABLE [campaign] ADD [cc] [varchar] (1024) NULL;
ALTER TABLE [campaign] ADD [bcc] [varchar] (1024) NULL;
ALTER TABLE [campaign] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [document_store] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [organization] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [projects] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [relationship] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [task] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [contact] DROP COLUMN [imname];
ALTER TABLE [contact] DROP COLUMN [imservice];
ALTER TABLE [contact] ADD [additional_names] [varchar] (255) NULL;
ALTER TABLE [contact] ADD [nickname] [varchar] (80) NULL;
ALTER TABLE [contact] ADD [role] [varchar] (255) NULL;
ALTER TABLE [contact] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [business_process_hook] ADD [priority] [int] NULL;
UPDATE business_process_hook SET priority = 1 WHERE priority IS NULL;
ALTER TABLE [business_process_hook] ALTER COLUMN [priority] [int] NOT NULL;

CREATE TABLE [history] (
	[history_id] [int] IDENTITY (1, 1) NOT NULL ,
	[contact_id] [int] NULL ,
	[org_id] [int] NULL ,
	[link_object_id] [int] NOT NULL ,
	[link_item_id] [int] NULL ,
	[status] [varchar] (255) NULL ,
	[type] [varchar] (255) NULL ,
	[description] [text] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [opportunity_header] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [product_catalog] ADD [trashed_date] [datetime] NULL;
ALTER TABLE [product_catalog] ADD [active] [bit] NULL;
UPDATE product_catalog SET active = enabled;
UPDATE product_catalog SET enabled = 1;
ALTER TABLE [product_catalog] ALTER COLUMN [active] [bit] NOT NULL;

ALTER TABLE [service_contract] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [asset] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [opportunity_component] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [call_log] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [quote_entry] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [ticket] ADD [trashed_date] [datetime] NULL;

ALTER TABLE [history] WITH NOCHECK ADD
	 PRIMARY KEY  CLUSTERED
	(
		[history_id]
	)  ON [PRIMARY]
GO

ALTER TABLE [business_process_hook] WITH NOCHECK ADD 
	CONSTRAINT [DF__business___prior__4218B34E] DEFAULT (0) FOR [priority]
GO

ALTER TABLE [history] WITH NOCHECK ADD 
	CONSTRAINT [DF__history__level__7B5130AA] DEFAULT (10) FOR [level],
	CONSTRAINT [DF__history__enabled__7C4554E3] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__history__entered__7D39791C] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__history__modifie__7F21C18E] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [product_catalog] WITH NOCHECK ADD 
	CONSTRAINT [DF__product_c__activ__216BEC9A] DEFAULT (1) FOR [active]
GO

ALTER TABLE [history] ADD 
	 FOREIGN KEY 
	(
		[contact_id]
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
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	)
GO

ALTER TABLE [quote_entry] ADD
	 FOREIGN KEY 
	(
		[customer_product_id]
	) REFERENCES [customer_product] (
		[customer_product_id]
	),
	 FOREIGN KEY 
	(
		[product_id]
	) REFERENCES [product_catalog] (
		[product_id]
	)
GO

ALTER TABLE sites ADD language VARCHAR(11);
UPDATE sites SET language = 'en_US';

CREATE TABLE [lookup_opportunity_budget] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL
) ON [PRIMARY]
GO

CREATE TABLE [lookup_opportunity_competitors] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL
) ON [PRIMARY]
GO

CREATE TABLE [lookup_opportunity_environment] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL
) ON [PRIMARY]
GO

CREATE TABLE [lookup_opportunity_event_compelling] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL
) ON [PRIMARY]
GO

ALTER TABLE opportunity_component ADD [environment] [int] NULL;
ALTER TABLE opportunity_component ADD [competitors] [int] NULL;
ALTER TABLE opportunity_component ADD [compelling_event] [int] NULL;
ALTER TABLE opportunity_component ADD [budget] [int] NULL;

ALTER TABLE [lookup_opportunity_budget] WITH NOCHECK ADD
	 PRIMARY KEY  CLUSTERED
	(
		[code]
	)  ON [PRIMARY]
GO

ALTER TABLE [lookup_opportunity_competitors] WITH NOCHECK ADD
	 PRIMARY KEY  CLUSTERED
	(
		[code]
	)  ON [PRIMARY]
GO

ALTER TABLE [lookup_opportunity_environment] WITH NOCHECK ADD
	 PRIMARY KEY  CLUSTERED
	(
		[code]
	)  ON [PRIMARY]
GO

ALTER TABLE [lookup_opportunity_event_compelling] WITH NOCHECK ADD
	 PRIMARY KEY  CLUSTERED
	(
		[code]
	)  ON [PRIMARY]
GO

ALTER TABLE [lookup_opportunity_budget] WITH NOCHECK ADD
	CONSTRAINT [DF__lookup_op__defau__436BFEE3] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_op__level__4460231C] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_op__enabl__45544755] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_opportunity_competitors] WITH NOCHECK ADD
	CONSTRAINT [DF__lookup_op__defau__37FA4C37] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_op__level__38EE7070] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_op__enabl__39E294A9] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_opportunity_environment] WITH NOCHECK ADD
	CONSTRAINT [DF__lookup_op__defau__324172E1] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_op__level__3335971A] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_op__enabl__3429BB53] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_opportunity_event_compelling] WITH NOCHECK ADD
	CONSTRAINT [DF__lookup_op__defau__3DB3258D] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_op__level__3EA749C6] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_op__enabl__3F9B6DFF] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [opportunity_component] ADD
	 FOREIGN KEY
	(
		[budget]
	) REFERENCES [lookup_opportunity_budget] (
		[code]
	),
	 FOREIGN KEY
	(
		[competitors]
	) REFERENCES [lookup_opportunity_competitors] (
		[code]
	),
	 FOREIGN KEY
	(
		[compelling_event]
	) REFERENCES [lookup_opportunity_event_compelling] (
		[code]
	),
	 FOREIGN KEY
	(
		[environment]
	) REFERENCES [lookup_opportunity_environment] (
		[code]
	)
GO


INSERT [database_version] ([script_filename],[script_version])VALUES('mssql_2005-07-08.sql','2005-07-08');
