CREATE TABLE [events] (
	[event_id] [int] IDENTITY (1, 1) NOT NULL ,
	[second] [varchar] (64) NULL ,
	[minute] [varchar] (64) NULL ,
	[hour] [varchar] (64) NULL ,
	[dayofmonth] [varchar] (64) NULL ,
	[month] [varchar] (64) NULL ,
	[dayofweek] [varchar] (64) NULL ,
	[year] [varchar] (64) NULL ,
	[task] [varchar] (255) NULL ,
	[extrainfo] [varchar] (255) NULL ,
	[businessDays] [varchar] (6) NULL ,
	[enabled] [bit] NULL ,
	[entered] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [events_log] (
	[log_id] [int] IDENTITY (1, 1) NOT NULL ,
	[event_id] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[status] [int] NULL ,
	[message] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [sites] (
	[site_id] [int] IDENTITY (1, 1) NOT NULL ,
	[sitecode] [varchar] (255) NOT NULL ,
	[vhost] [varchar] (255) NOT NULL ,
	[dbhost] [varchar] (255) NOT NULL ,
	[dbname] [varchar] (255) NOT NULL ,
	[dbport] [int] NOT NULL ,
	[dbuser] [varchar] (255) NOT NULL ,
	[dbpw] [varchar] (255) NOT NULL ,
	[driver] [varchar] (255) NOT NULL ,
	[code] [varchar] (255) NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE [events] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[event_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [events_log] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[log_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [sites] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[site_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [events] WITH NOCHECK ADD 
	CONSTRAINT [DF__events__second__014935CB] DEFAULT ('0') FOR [second],
	CONSTRAINT [DF__events__minute__023D5A04] DEFAULT ('*') FOR [minute],
	CONSTRAINT [DF__events__hour__03317E3D] DEFAULT ('*') FOR [hour],
	CONSTRAINT [DF__events__dayofmon__0425A276] DEFAULT ('*') FOR [dayofmonth],
	CONSTRAINT [DF__events__month__0519C6AF] DEFAULT ('*') FOR [month],
	CONSTRAINT [DF__events__dayofwee__060DEAE8] DEFAULT ('*') FOR [dayofweek],
	CONSTRAINT [DF__events__year__07020F21] DEFAULT ('*') FOR [year],
	CONSTRAINT [DF__events__business__07F6335A] DEFAULT ('true') FOR [businessDays],
	CONSTRAINT [DF__events__enabled__08EA5793] DEFAULT (0) FOR [enabled],
	CONSTRAINT [DF__events__entered__09DE7BCC] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [events_log] WITH NOCHECK ADD 
	CONSTRAINT [DF__events_lo__enter__0DAF0CB0] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [sites] WITH NOCHECK ADD 
	CONSTRAINT [DF__sites__vhost__77BFCB91] DEFAULT ('') FOR [vhost],
	CONSTRAINT [DF__sites__dbhost__78B3EFCA] DEFAULT ('') FOR [dbhost],
	CONSTRAINT [DF__sites__dbname__79A81403] DEFAULT ('') FOR [dbname],
	CONSTRAINT [DF__sites__dbport__7A9C383C] DEFAULT (1433) FOR [dbport],
	CONSTRAINT [DF__sites__dbuser__7B905C75] DEFAULT ('') FOR [dbuser],
	CONSTRAINT [DF__sites__dbpw__7C8480AE] DEFAULT ('') FOR [dbpw],
	CONSTRAINT [DF__sites__driver__7D78A4E7] DEFAULT ('') FOR [driver],
	CONSTRAINT [DF__sites__enabled__7E6CC920] DEFAULT (0) FOR [enabled]
GO

ALTER TABLE [events_log] ADD 
	 FOREIGN KEY 
	(
		[event_id]
	) REFERENCES [events] (
		[event_id]
	)
GO

