-- Script (C) 2004 Dark Horse Ventures LLC, all rights reserved
-- Database upgrade v2.9 (2004-08-30)

ALTER TABLE access ADD [currency] [varchar] (5);
ALTER TABLE access ADD [language] [varchar] (20);

ALTER TABLE organization ADD [alertdate_timezone] [varchar] (255);
ALTER TABLE organization ADD [contract_end_timezone] [varchar] (255);

ALTER TABLE contact_address ADD [primary_address] [bit] NULL;
UPDATE contact_address SET primary_address = 0;
ALTER TABLE contact_address ALTER COLUMN [primary_address] [bit] NOT NULL;

ALTER TABLE contact_phone ADD [primary_number] [bit] NULL;
UPDATE contact_phone SET primary_number = 0;
ALTER TABLE contact_phone ALTER COLUMN [primary_number] [bit] NOT NULL;


CREATE TABLE [lookup_call_priority] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[weight] [int] NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE [lookup_call_priority] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_ca__defau__5006DFF2] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_ca__level__50FB042B] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_ca__enabl__51EF2864] DEFAULT (1) FOR [enabled]
GO


CREATE TABLE [lookup_call_reminder] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[base_value] [int] NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

ALTER TABLE [lookup_call_reminder] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_ca__base___54CB950F] DEFAULT (0) FOR [base_value],
	CONSTRAINT [DF__lookup_ca__defau__55BFB948] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_ca__level__56B3DD81] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_ca__enabl__57A801BA] DEFAULT (1) FOR [enabled]
GO


CREATE TABLE [lookup_call_result] (
	[result_id] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (100) NOT NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[next_required] [bit] NOT NULL ,
	[next_days] [int] NOT NULL ,
	[next_call_type_id] [int] NULL ,
	[canceled_type] [bit] NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE opportunity_component ADD [alertdate_timezone] [varchar] (255);

ALTER TABLE call_log ALTER COLUMN [alert] [varchar] (255);

ALTER TABLE call_log ADD [alert_call_type_id] integer;
ALTER TABLE call_log ADD [parent_id] integer;
ALTER TABLE call_log ADD [owner] integer;
ALTER TABLE call_log ADD [assignedby] integer;
ALTER TABLE call_log ADD [assign_date] DATETIME;
ALTER TABLE call_log ADD [completedby] integer;
ALTER TABLE call_log ADD [complete_date] DATETIME;
ALTER TABLE call_log ADD [result_id] integer;
ALTER TABLE call_log ADD [priority_id] integer;
ALTER TABLE call_log ADD [status_id] [integer] NULL;
UPDATE call_log SET status_id = 2;
ALTER TABLE call_log ALTER COLUMN [status_id] [integer] NOT NULL;
ALTER TABLE call_log ADD [reminder_value] integer;
ALTER TABLE call_log ADD [reminder_type_id] integer;
UPDATE call_log SET result_id = 1;
ALTER table call_log ADD [alertdate_timezone] VARCHAR(255);
UPDATE call_log set alertdate_timezone = 'America/New_York';

ALTER TABLE [call_log] WITH NOCHECK ADD 
	CONSTRAINT [DF__call_log__assign__0EF836A4] DEFAULT (getdate()) FOR [assign_date],
	CONSTRAINT [DF__call_log__status__12C8C788] DEFAULT (1) FOR [status_id],
	CONSTRAINT [DF__call_log__alertd__14B10FFA] DEFAULT ('America/New_York') FOR [alertdate_timezone]
GO


-----
--TODO:verify this...
--ALTER TABLE project_issues DROP CONSTRAINT "$2";
-- "$2" FOREIGN KEY (category_id) REFERENCES project_issues_categories(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE project_issues ALTER COLUMN [category_id] [int] NULL;
-----

DROP TABLE lookup_project_issues;

CREATE TABLE [lookup_project_role] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[group_id] [int] NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE [lookup_project_role] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__314D4EA8] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__324172E1] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__3335971A] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__lookup_pr__group__3429BB53] DEFAULT (0) FOR [group_id]
GO


CREATE TABLE [lookup_project_category] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (80) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[group_id] [int] NOT NULL 
) ON [PRIMARY]
GO


ALTER TABLE projects ADD approvalby integer;
ALTER TABLE projects ADD category_id integer;

ALTER TABLE projects ADD portal BIT;
UPDATE projects SET portal = 0 WHERE portal IS NULL;
ALTER TABLE projects ADD portal BIT NOT NULL;

ALTER TABLE projects ADD allow_guests BIT;
UPDATE projects SET allow_guests = 0 WHERE allow_guests IS NULL;
ALTER TABLE projects ADD allow_guests BIT NOT NULL;

ALTER TABLE projects ADD news_enabled BIT;
UPDATE projects SET news_enabled = 1 WHERE news_enabled IS NULL;
ALTER TABLE projects ADD news_enabled BIT NOT NULL;

ALTER TABLE projects ADD details_enabled BIT;
UPDATE projects SET details_enabled = 1 WHERE details_enabled IS NULL;
ALTER TABLE projects ADD details_enabled BIT NOT NULL;

ALTER TABLE projects ADD team_enabled BIT;
UPDATE projects SET team_enabled = 1 WHERE team_enabled IS NULL;
ALTER TABLE projects ADD team_enabled BIT NOT NULL;

ALTER TABLE projects ADD plan_enabled BIT;
UPDATE projects SET plan_enabled = 1 WHERE plan_enabled IS NULL;
ALTER TABLE projects ADD plan_enabled BIT NOT NULL;

ALTER TABLE projects ADD lists_enabled BIT;
UPDATE projects SET lists_enabled = 1 WHERE lists_enabled IS NULL;
ALTER TABLE projects ADD lists_enabled BIT NOT NULL;

ALTER TABLE projects ADD discussion_enabled BIT;
UPDATE projects SET discussion_enabled = 1 WHERE discussion_enabled IS NULL;
ALTER TABLE projects ADD discussion_enabled BIT NOT NULL;

ALTER TABLE projects ADD tickets_enabled BIT;
UPDATE projects SET tickets_enabled = 1 WHERE tickets_enabled IS NULL;
ALTER TABLE projects ADD tickets_enabled BIT NOT NULL;

ALTER TABLE projects ADD documents_enabled BIT;
UPDATE projects SET documents_enabled = 1 WHERE documents_enabled IS NULL;
ALTER TABLE projects ADD documents_enabled BIT NOT NULL;

ALTER TABLE projects ADD news_label [varchar] (50);
ALTER TABLE projects ADD details_label [varchar] (50);
ALTER TABLE projects ADD team_label [varchar] (50);
ALTER TABLE projects ADD plan_label [varchar] (50);
ALTER TABLE projects ADD lists_label [varchar] (50);
ALTER TABLE projects ADD discussion_label [varchar] (50);
ALTER TABLE projects ADD tickets_label [varchar] (50);
ALTER TABLE projects ADD documents_label [varchar] (50);
ALTER TABLE projects ADD est_closedate DATETIME;
ALTER TABLE projects ADD budget float;
ALTER TABLE projects ADD budget_currency [varchar] (5);

ALTER TABLE [projects] WITH NOCHECK ADD 
	CONSTRAINT [DF__projects__portal__4460231C] DEFAULT (0) FOR [portal],
	CONSTRAINT [DF__projects__allow___45544755] DEFAULT (0) FOR [allow_guests],
	CONSTRAINT [DF__projects__news_e__46486B8E] DEFAULT (1) FOR [news_enabled],
	CONSTRAINT [DF__projects__detail__473C8FC7] DEFAULT (1) FOR [details_enabled],
	CONSTRAINT [DF__projects__team_e__4830B400] DEFAULT (1) FOR [team_enabled],
	CONSTRAINT [DF__projects__plan_e__4924D839] DEFAULT (1) FOR [plan_enabled],
	CONSTRAINT [DF__projects__lists___4A18FC72] DEFAULT (1) FOR [lists_enabled],
	CONSTRAINT [DF__projects__discus__4B0D20AB] DEFAULT (1) FOR [discussion_enabled],
	CONSTRAINT [DF__projects__ticket__4C0144E4] DEFAULT (1) FOR [tickets_enabled],
	CONSTRAINT [DF__projects__docume__4CF5691D] DEFAULT (1) FOR [documents_enabled]
GO


ALTER TABLE project_requirements ADD startdate DATETIME;

CREATE TABLE [project_assignments_folder] (
	[folder_id] [int] IDENTITY (1, 1) NOT NULL ,
	[parent_id] [int] NULL ,
	[requirement_id] [int] NOT NULL ,
	[name] [varchar] (255) NOT NULL ,
	[description] [text] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredBy] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedBy] [int] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE project_assignments DROP activity_id;
ALTER TABLE project_assignments ADD folder_id integer;
ALTER TABLE project_assignments ADD percent_complete integer;

CREATE TABLE [project_issues_categories] (
	[category_id] [int] IDENTITY (1, 1) NOT NULL ,
	[project_id] [int] NOT NULL ,
	[subject] [varchar] (255) NOT NULL ,
	[description] [text] NULL ,
	[enabled] [bit] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredBy] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedBy] [int] NOT NULL ,
	[topics_count] [int] NOT NULL ,
	[posts_count] [int] NOT NULL ,
	[last_post_date] [datetime] NULL ,
	[last_post_by] [int] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE project_issues DROP type_id;
ALTER TABLE project_issues ADD category_id integer;

ALTER TABLE project_issues ADD reply_count INTEGER;
UPDATE project_issues SET reply_count = 0 WHERE reply_count IS NULL;
ALTER TABLE project_issues ALTER COLUMN reply_count INTEGER NOT NULL;

ALTER TABLE project_issues ADD last_reply_date DATETIME;
UPDATE project_issues SET last_reply_date = modified WHERE last_reply_date IS NULL;
ALTER TABLE project_issues ADD last_reply_date DATETIME NOT NULL;

ALTER TABLE project_issues ADD last_reply_by integer;

ALTER TABLE [project_issues] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_i__reply__08162EEB] DEFAULT (0) FOR [reply_count],
	CONSTRAINT [DF__project_i__last___090A5324] DEFAULT (getdate()) FOR [last_reply_date]
GO


ALTER TABLE project_folders DROP parent;
ALTER TABLE project_folders ADD parent_id integer;

ALTER TABLE project_folders ADD entered DATETIME;
UPDATE project_folders SET entered = CURRENT_TIMESTAMP WHERE entered IS NULL;
ALTER TABLE project_folders ALTER COLUMN entered DATETIME NOT NULL;

ALTER TABLE project_folders ADD enteredBy INTEGER REFERENCES access(user_id);
UPDATE project_folders SET enteredBy = 0 WHERE enteredBy IS NULL;
ALTER TABLE project_folders ALTER COLUMN enteredBy INTEGER REFERENCES access(user_id) NOT NULL;

ALTER TABLE project_folders ADD modified DATETIME;
UPDATE project_folders SET modified = CURRENT_TIMESTAMP WHERE modified IS NULL;
ALTER TABLE project_folders ALTER COLUMN modified DATETIME NOT NULL;

ALTER TABLE project_folders ADD modifiedBy INTEGER REFERENCES access(user_id);
UPDATE project_folders SET modifiedBy = 0 WHERE modifiedBy IS NULL;
ALTER TABLE project_folders ADD modifiedBy INTEGER REFERENCES access(user_id) NOT NULL;

ALTER TABLE project_folders ADD display integer;

ALTER TABLE [project_folders] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_f__enter__1387E197] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__project_f__modif__15702A09] DEFAULT (getdate()) FOR [modified]
GO

CREATE TABLE [project_files_thumbnail] (
	[item_id] [int] NULL ,
	[filename] [varchar] (255) NOT NULL ,
	[size] [int] NULL ,
	[version] [float] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredBy] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedBy] [int] NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE project_team ALTER COLUMN userlevel INT NOT NULL;
ALTER TABLE project_team ADD status integer;
ALTER TABLE project_team ADD last_accessed DATETIME;

CREATE TABLE [project_news] (
	[news_id] [int] IDENTITY (1, 1) NOT NULL ,
	[project_id] [int] NOT NULL ,
	[category_id] [int] NULL ,
	[subject] [varchar] (255) NOT NULL ,
	[intro] [varchar] (2048) NULL ,
	[message] [text] NULL ,
	[entered] [datetime] NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NULL ,
	[modifiedby] [int] NOT NULL ,
	[start_date] [datetime] NULL ,
	[end_date] [datetime] NULL ,
	[allow_replies] [bit] NULL ,
	[allow_rating] [bit] NULL ,
	[rating_count] [int] NOT NULL ,
	[avg_rating] [float] NULL ,
	[priority_id] [int] NULL ,
	[read_count] [int] NOT NULL ,
	[enabled] [bit] NULL ,
	[status] [int] NULL ,
	[html] [bit] NOT NULL ,
	[start_date_timezone] [varchar] (255) NULL ,
	[end_date_timezone] [varchar] (255) NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [project_requirements_map] (
	[map_id] [int] IDENTITY (1, 1) NOT NULL ,
	[project_id] [int] NOT NULL ,
	[requirement_id] [int] NOT NULL ,
	[position] [int] NOT NULL ,
	[indent] [int] NOT NULL ,
	[folder_id] [int] NULL ,
	[assignment_id] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_project_permission_category] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[group_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_project_permission] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[category_id] [int] NULL ,
	[permission] [varchar] (300) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[group_id] [int] NOT NULL ,
	[default_role] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [project_permissions] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[project_id] [int] NOT NULL ,
	[permission_id] [int] NOT NULL ,
	[userlevel] [int] NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE service_contract ADD service_model_notes text;

ALTER TABLE ticket ADD key_count integer;
ALTER TABLE ticket ADD est_resolution_date_timezone [VARCHAR] (255);

CREATE TABLE [project_ticket_count] (
	[project_id] [int] NOT NULL ,
	[key_count] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [ticketlink_project] (
	[ticket_id] [int] NOT NULL ,
	[project_id] [int] NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE task ADD duedate_timezone [varchar] (255);

ALTER TABLE tasklink_contact ADD COLUMN notes text;

-- Insert default lookup_call_priority
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_call_priority] ON
GO
INSERT [lookup_call_priority] ([code],[description],[default_item],[level],[enabled],[weight])VALUES(1,'Low',1,1,1,10)
INSERT [lookup_call_priority] ([code],[description],[default_item],[level],[enabled],[weight])VALUES(2,'Medium',0,2,1,20)
INSERT [lookup_call_priority] ([code],[description],[default_item],[level],[enabled],[weight])VALUES(3,'High',0,3,1,30)

SET IDENTITY_INSERT [lookup_call_priority] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_call_reminder
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_call_reminder] ON
GO
INSERT [lookup_call_reminder] ([code],[description],[base_value],[default_item],[level],[enabled])VALUES(1,'Minute(s)',60,1,1,1)
INSERT [lookup_call_reminder] ([code],[description],[base_value],[default_item],[level],[enabled])VALUES(2,'Hour(s)',3600,0,2,1)
INSERT [lookup_call_reminder] ([code],[description],[base_value],[default_item],[level],[enabled])VALUES(3,'Day(s)',86400,0,3,1)
INSERT [lookup_call_reminder] ([code],[description],[base_value],[default_item],[level],[enabled])VALUES(4,'Week(s)',604800,0,4,1)
INSERT [lookup_call_reminder] ([code],[description],[base_value],[default_item],[level],[enabled])VALUES(5,'Month(s)',18144000,0,5,1)

SET IDENTITY_INSERT [lookup_call_reminder] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_call_result
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_call_result] ON
GO
INSERT [lookup_call_result] ([result_id],[description],[level],[enabled],[next_required],[next_days],[next_call_type_id],[canceled_type])VALUES(1,'Yes - Business progressing',10,1,1,0,NULL,0)
INSERT [lookup_call_result] ([result_id],[description],[level],[enabled],[next_required],[next_days],[next_call_type_id],[canceled_type])VALUES(2,'No - No business at this time',20,1,0,0,NULL,0)
INSERT [lookup_call_result] ([result_id],[description],[level],[enabled],[next_required],[next_days],[next_call_type_id],[canceled_type])VALUES(3,'Unsure - Unsure or no contact made',30,1,1,0,NULL,0)
INSERT [lookup_call_result] ([result_id],[description],[level],[enabled],[next_required],[next_days],[next_call_type_id],[canceled_type])VALUES(4,'Lost to competitor',140,1,0,0,NULL,1)
INSERT [lookup_call_result] ([result_id],[description],[level],[enabled],[next_required],[next_days],[next_call_type_id],[canceled_type])VALUES(5,'No further interest',150,1,0,0,NULL,1)
INSERT [lookup_call_result] ([result_id],[description],[level],[enabled],[next_required],[next_days],[next_call_type_id],[canceled_type])VALUES(6,'Event postponed/canceled',160,1,0,0,NULL,1)
INSERT [lookup_call_result] ([result_id],[description],[level],[enabled],[next_required],[next_days],[next_call_type_id],[canceled_type])VALUES(7,'Another pending action',170,1,0,0,NULL,1)
INSERT [lookup_call_result] ([result_id],[description],[level],[enabled],[next_required],[next_days],[next_call_type_id],[canceled_type])VALUES(8,'Another contact handling event',180,1,0,0,NULL,1)
INSERT [lookup_call_result] ([result_id],[description],[level],[enabled],[next_required],[next_days],[next_call_type_id],[canceled_type])VALUES(9,'Contact no longer with company',190,1,0,0,NULL,1)
INSERT [lookup_call_result] ([result_id],[description],[level],[enabled],[next_required],[next_days],[next_call_type_id],[canceled_type])VALUES(10,'Servicing',120,1,0,0,NULL,0)

SET IDENTITY_INSERT [lookup_call_result] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_project_role
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_project_role] ON
GO
INSERT [lookup_project_role] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(1,'Project Lead',0,10,1,1)
INSERT [lookup_project_role] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(2,'Contributor',0,20,1,1)
INSERT [lookup_project_role] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(3,'Observer',0,30,1,1)
INSERT [lookup_project_role] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(4,'Guest',0,100,1,1)

SET IDENTITY_INSERT [lookup_project_role] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_project_permission_category
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_project_permission_category] ON
GO
INSERT [lookup_project_permission_category] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(1,'Project Details',0,10,1,1)
INSERT [lookup_project_permission_category] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(2,'Team Members',0,20,1,1)
INSERT [lookup_project_permission_category] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(3,'News',0,30,1,1)
INSERT [lookup_project_permission_category] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(4,'Plan/Outlines',0,40,1,1)
INSERT [lookup_project_permission_category] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(5,'Lists',0,50,1,1)
INSERT [lookup_project_permission_category] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(6,'Discussion',0,60,1,1)
INSERT [lookup_project_permission_category] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(7,'Tickets',0,70,1,1)
INSERT [lookup_project_permission_category] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(8,'Document Library',0,80,1,1)
INSERT [lookup_project_permission_category] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(9,'Setup',0,90,1,1)

SET IDENTITY_INSERT [lookup_project_permission_category] OFF
GO
SET NOCOUNT OFF
 
-- Insert default lookup_project_permission
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_project_permission] ON
GO
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(1,1,'project-details-view','View project details',0,10,1,1,4)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(2,1,'project-details-edit','Modify project details',0,20,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(3,1,'project-details-delete','Delete project',0,30,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(4,2,'project-team-view','View team members',0,10,1,1,4)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(5,2,'project-team-view-email','See team member email addresses',0,20,1,1,3)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(6,2,'project-team-edit','Modify team',0,30,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(7,2,'project-team-edit-role','Modify team member role',0,40,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(8,3,'project-news-view','View current news',0,10,1,1,4)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(9,3,'project-news-view-unreleased','View unreleased news',0,20,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(10,3,'project-news-view-archived','View archived news',0,30,1,1,3)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(11,3,'project-news-add','Add news',0,40,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(12,3,'project-news-edit','Edit news',0,50,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(13,3,'project-news-delete','Delete news',0,60,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(14,4,'project-plan-view','View outlines',0,10,1,1,4)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(15,4,'project-plan-outline-add','Add an outline',0,20,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(16,4,'project-plan-outline-edit','Modify details of an existing outline',0,40,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(17,4,'project-plan-outline-delete','Delete an outline',0,50,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(18,4,'project-plan-outline-modify','Make changes to an outline',0,60,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(19,4,'project-plan-activities-assign','Re-assign activities',0,70,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(20,5,'project-lists-view','View lists',0,10,1,1,4)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(21,5,'project-lists-add','Add a list',0,20,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(22,5,'project-lists-edit','Modify details of an existing list',0,30,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(23,5,'project-lists-delete','Delete a list',0,40,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(24,5,'project-lists-modify','Make changes to list items',0,50,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(25,6,'project-discussion-forums-view','View discussion forums',0,10,1,1,4)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(26,6,'project-discussion-forums-add','Add discussion forum',0,20,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(27,6,'project-discussion-forums-edit','Modify discussion forum',0,30,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(28,6,'project-discussion-forums-delete','Delete discussion forum',0,40,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(29,6,'project-discussion-topics-view','View forum topics',0,50,1,1,4)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(30,6,'project-discussion-topics-add','Add forum topics',0,60,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(31,6,'project-discussion-topics-edit','Modify forum topics',0,70,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(32,6,'project-discussion-topics-delete','Delete forum topics',0,80,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(33,6,'project-discussion-messages-add','Post messages',0,90,1,1,3)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(34,6,'project-discussion-messages-reply','Reply to messages',0,100,1,1,3)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(35,6,'project-discussion-messages-edit','Modify existing messages',0,110,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(36,6,'project-discussion-messages-delete','Delete messages',0,120,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(37,7,'project-tickets-view','View tickets',0,10,1,1,4)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(38,7,'project-tickets-add','Add a ticket',0,20,1,1,3)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(39,7,'project-tickets-edit','Modify existing ticket',0,30,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(40,7,'project-tickets-delete','Delete tickets',0,40,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(41,7,'project-tickets-assign','Assign tickets',0,50,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(42,8,'project-documents-view','View documents',0,10,1,1,4)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(43,8,'project-documents-folders-add','Create folders',0,20,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(44,8,'project-documents-folders-edit','Modify folders',0,30,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(45,8,'project-documents-folders-delete','Delete folders',0,40,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(46,8,'project-documents-files-upload','Upload files',0,50,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(47,8,'project-documents-files-download','Download files',0,60,1,1,4)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(48,8,'project-documents-files-rename','Rename files',0,70,1,1,2)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(49,8,'project-documents-files-delete','Delete files',0,80,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(50,9,'project-setup-customize','Customize project features',0,10,1,1,1)
INSERT [lookup_project_permission] ([code],[category_id],[permission],[description],[default_item],[level],[enabled],[group_id],[default_role])VALUES(51,9,'project-setup-permissions','Configure project permissions',0,20,1,1,1)

SET IDENTITY_INSERT [lookup_project_permission] OFF
GO
SET NOCOUNT OFF
 

CREATE INDEX "contact_address_contact_id_idx" ON "contact_address" (contact_id);


CREATE INDEX "contact_email_contact_id_idx" ON "contact_emailaddress" (contact_id);


CREATE INDEX "contact_phone_contact_id_idx" ON "contact_phone" (contact_id);


 CREATE  INDEX [call_log_entered_idx] ON [call_log]([entered]) ON [PRIMARY]
GO



 CREATE  INDEX [call_contact_id_idx] ON [call_log]([contact_id]) ON [PRIMARY]
GO



 CREATE  INDEX [call_org_id_idx] ON [call_log]([org_id]) ON [PRIMARY]
GO



 CREATE  INDEX [call_opp_id_idx] ON [call_log]([opp_id]) ON [PRIMARY]
GO



DROP INDEX project_issues_idx;



 CREATE  UNIQUE  INDEX [project_team_uni_idx] ON [project_team]([project_id], [user_id]) ON [PRIMARY]
GO



 CREATE  INDEX [proj_req_map_pr_req_pos_idx] ON [project_requirements_map]([project_id], [requirement_id], [position]) ON [PRIMARY]
GO



CREATE INDEX "ticket_problem_idx" ON "ticket" (problem);
CREATE INDEX "ticket_comment_idx" ON "ticket" (comment);
CREATE INDEX "ticket_solution_idx" ON "ticket" (solution);



ALTER TABLE [lookup_call_priority] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO



ALTER TABLE [lookup_call_reminder] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO



ALTER TABLE [lookup_call_result] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[result_id]
	)  ON [PRIMARY] 
GO


ALTER TABLE [lookup_call_result] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_ca__level__5A846E65] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_ca__enabl__5B78929E] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__lookup_ca__next___5C6CB6D7] DEFAULT (0) FOR [next_required],
	CONSTRAINT [DF__lookup_ca__next___5D60DB10] DEFAULT (0) FOR [next_days],
	CONSTRAINT [DF__lookup_ca__cance__5E54FF49] DEFAULT (0) FOR [canceled_type]
GO


ALTER TABLE [call_log] ADD 
	 FOREIGN KEY 
	(
		[alert_call_type_id]
	) REFERENCES [lookup_call_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[assignedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[completedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[owner]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[parent_id]
	) REFERENCES [call_log] (
		[call_id]
	),
	 FOREIGN KEY 
	(
		[priority_id]
	) REFERENCES [lookup_call_priority] (
		[code]
	),
	 FOREIGN KEY 
	(
		[reminder_type_id]
	) REFERENCES [lookup_call_reminder] (
		[code]
	),
	 FOREIGN KEY 
	(
		[result_id]
	) REFERENCES [lookup_call_result] (
		[result_id]
	)
GO



ALTER TABLE [lookup_call_result] ADD 
	 FOREIGN KEY 
	(
		[next_call_type_id]
	) REFERENCES [call_log] (
		[call_id]
	)
GO



ALTER TABLE [lookup_project_role] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO



ALTER TABLE [lookup_project_category] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_project_category] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__370627FE] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__37FA4C37] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__38EE7070] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__lookup_pr__group__39E294A9] DEFAULT (0) FOR [group_id]
GO



ALTER TABLE [projects] ADD 
	 FOREIGN KEY 
	(
		[approvalBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [lookup_project_category] (
		[code]
	)
GO



ALTER TABLE ONLY project_assignments_folder
    ADD CONSTRAINT project_assignments_folder_pkey PRIMARY KEY (folder_id);

ALTER TABLE [project_assignments_folder] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_a__enter__5C37ACAD] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__project_a__modif__5E1FF51F] DEFAULT (getdate()) FOR [modified]
GO


ALTER TABLE [project_assignments_folder] ADD 
	 FOREIGN KEY 
	(
		[enteredBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[parent_id]
	) REFERENCES [project_assignments_folder] (
		[folder_id]
	),
	 FOREIGN KEY 
	(
		[requirement_id]
	) REFERENCES [project_requirements] (
		[requirement_id]
	)
GO



ALTER TABLE [project_assignments] ADD 
  FOREIGN KEY 
	(
		[folder_id]
	) REFERENCES [project_assignments_folder] (
		[folder_id]
	)
GO



ALTER TABLE [project_issues_categories] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[category_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [project_issues_categories] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_i__enabl__77DFC722] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__project_i__enter__78D3EB5B] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__project_i__modif__7ABC33CD] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__project_i__topic__7CA47C3F] DEFAULT (0) FOR [topics_count],
	CONSTRAINT [DF__project_i__posts__7D98A078] DEFAULT (0) FOR [posts_count]
GO



ALTER TABLE [project_issues_categories] ADD 
	 FOREIGN KEY 
	(
		[enteredBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[project_id]
	) REFERENCES [projects] (
		[project_id]
	)
GO


ALTER TABLE [project_issues] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_i__reply__08162EEB] DEFAULT (0) FOR [reply_count],
	CONSTRAINT [DF__project_i__last___090A5324] DEFAULT (getdate()) FOR [last_reply_date]
GO


ALTER TABLE [project_issues] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [project_issues_categories] (
		[category_id]
	)
GO



ALTER TABLE [project_files_thumbnail] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_fi__size__320C68B7] DEFAULT (0) FOR [size],
	CONSTRAINT [DF__project_f__versi__33008CF0] DEFAULT (0) FOR [version],
	CONSTRAINT [DF__project_f__enter__33F4B129] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__project_f__modif__35DCF99B] DEFAULT (getdate()) FOR [modified]
GO



ALTER TABLE [project_files_thumbnail] ADD 
	 FOREIGN KEY 
	(
		[enteredBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[item_id]
	) REFERENCES [project_files] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[modifiedBy]
	) REFERENCES [access] (
		[user_id]
	)
GO


ALTER TABLE [project_team] ADD 
	 FOREIGN KEY 
	(
		[userlevel]
	) REFERENCES [lookup_project_role] (
		[code]
	)
GO



ALTER TABLE [project_news] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[news_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [project_news] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_n__enter__4242D080] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__project_n__modif__442B18F2] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__project_n__start__46136164] DEFAULT (getdate()) FOR [start_date],
	CONSTRAINT [DF__project_n__end_d__4707859D] DEFAULT (null) FOR [end_date],
	CONSTRAINT [DF__project_n__allow__47FBA9D6] DEFAULT (0) FOR [allow_replies],
	CONSTRAINT [DF__project_n__allow__48EFCE0F] DEFAULT (0) FOR [allow_rating],
	CONSTRAINT [DF__project_n__ratin__49E3F248] DEFAULT (0) FOR [rating_count],
	CONSTRAINT [DF__project_n__avg_r__4AD81681] DEFAULT (0) FOR [avg_rating],
	CONSTRAINT [DF__project_n__prior__4BCC3ABA] DEFAULT (10) FOR [priority_id],
	CONSTRAINT [DF__project_n__read___4CC05EF3] DEFAULT (0) FOR [read_count],
	CONSTRAINT [DF__project_n__enabl__4DB4832C] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__project_n__statu__4EA8A765] DEFAULT (null) FOR [status],
	CONSTRAINT [DF__project_ne__html__4F9CCB9E] DEFAULT (1) FOR [html]
GO



ALTER TABLE [project_news] ADD 
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
		[project_id]
	) REFERENCES [projects] (
		[project_id]
	)
GO


ALTER TABLE [project_requirements_map] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[map_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [project_requirements_map] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_r__inden__546180BB] DEFAULT (0) FOR [indent]
GO


ALTER TABLE [project_requirements_map] ADD 
	 FOREIGN KEY 
	(
		[assignment_id]
	) REFERENCES [project_assignments] (
		[assignment_id]
	),
	 FOREIGN KEY 
	(
		[folder_id]
	) REFERENCES [project_assignments_folder] (
		[folder_id]
	),
	 FOREIGN KEY 
	(
		[project_id]
	) REFERENCES [projects] (
		[project_id]
	),
	 FOREIGN KEY 
	(
		[requirement_id]
	) REFERENCES [project_requirements] (
		[requirement_id]
	)
GO



ALTER TABLE [lookup_project_permission_category] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_project_permission_category] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__592635D8] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__5A1A5A11] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__5B0E7E4A] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__lookup_pr__group__5C02A283] DEFAULT (0) FOR [group_id]
GO




ALTER TABLE [lookup_project_permission] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_project_permission] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__60C757A0] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__61BB7BD9] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__62AFA012] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__lookup_pr__group__63A3C44B] DEFAULT (0) FOR [group_id],
	 UNIQUE  NONCLUSTERED 
	(
		[permission]
	)  ON [PRIMARY] 
GO


ALTER TABLE [lookup_project_permission] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [lookup_project_permission_category] (
		[code]
	),
	 FOREIGN KEY 
	(
		[default_role]
	) REFERENCES [lookup_project_role] (
		[code]
	)
GO




ALTER TABLE [project_permissions] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO



ALTER TABLE [project_permissions] ADD 
	 FOREIGN KEY 
	(
		[permission_id]
	) REFERENCES [lookup_project_permission] (
		[code]
	),
	 FOREIGN KEY 
	(
		[project_id]
	) REFERENCES [projects] (
		[project_id]
	),
	 FOREIGN KEY 
	(
		[userlevel]
	) REFERENCES [lookup_project_role] (
		[code]
	)
GO


ALTER TABLE [project_ticket_count] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_t__key_c__28D80438] DEFAULT (0) FOR [key_count],
	 UNIQUE  NONCLUSTERED 
	(
		[project_id]
	)  ON [PRIMARY] 
GO



ALTER TABLE [project_ticket_count] ADD 
	 FOREIGN KEY 
	(
		[project_id]
	) REFERENCES [projects] (
		[project_id]
	)
GO



ALTER TABLE [ticketlink_project] ADD 
	 FOREIGN KEY 
	(
		[project_id]
	) REFERENCES [projects] (
		[project_id]
	),
	 FOREIGN KEY 
	(
		[ticket_id]
	) REFERENCES [ticket] (
		[ticketid]
	)
GO


-- table time zone fields
ALTER TABLE opportunity_component ADD closedate_timezone VARCHAR(255);
ALTER TABLE service_contract ADD initial_start_date_timezone VARCHAR(255);
ALTER TABLE service_contract ADD current_start_date_timezone VARCHAR(255);
ALTER TABLE service_contract ADD current_end_date_timezone VARCHAR(255);
ALTER TABLE asset ADD date_listed_timezone VARCHAR(255);
ALTER TABLE asset ADD expiration_date_timezone VARCHAR(255);
ALTER TABLE asset ADD purchase_date_timezone VARCHAR(255);
ALTER TABLE ticket ADD assigned_date_timezone VARCHAR(255);
ALTER TABLE ticket ADD resolution_date_timezone VARCHAR(255);
ALTER TABLE ticket_activity_item ADD activity_date_timezone VARCHAR(255);
ALTER TABLE campaign ADD active_date_timezone VARCHAR(255);
ALTER TABLE project_news ADD start_date_timezone VARCHAR(255);
ALTER TABLE project_news ADD end_date_timezone VARCHAR(255);
ALTER TABLE project_requirements ADD startdate_timezone VARCHAR(255);
ALTER TABLE project_requirements ADD deadline_timezone VARCHAR(255);
ALTER TABLE project_assignments ADD due_date_timezone VARCHAR(255);
ALTER TABLE projects ADD requestDate_timezone VARCHAR(255);
ALTER TABLE projects ADD est_closedate_timezone VARCHAR(255);
ALTER TABLE ticket_csstm_form ADD alert_date_timezone VARCHAR(255);

-- Renamed some permissions
UPDATE permission SET description = 'Activities' WHERE permission = 'pipeline-opportunities-calls';
UPDATE permission SET description = 'Contact Activities' WHERE permission = 'accounts-accounts-contacts-calls';
UPDATE permission SET description = 'Activities' WHERE permission = 'contacts-external_contacts-calls';

INSERT [database_version] ([script_filename],[script_version])VALUES('mssql_2004-08-30.sql','2004-08-30');



