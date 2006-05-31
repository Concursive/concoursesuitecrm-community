
SET NOEXEC OFF
SET ANSI_WARNINGS ON
SET XACT_ABORT ON
SET IMPLICIT_TRANSACTIONS OFF
SET NOCOUNT ON
SET QUOTED_IDENTIFIER ON
SET ARITHABORT ON
SET NUMERIC_ROUNDABORT OFF
SET CONCAT_NULL_YIELDS_NULL ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
GO


BEGIN TRAN
GO

-- =======================================================
-- Synchronization Script for: [lookup_instantmessenger_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_instantmessenger_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the table
DROP TABLE [lookup_instantmessenger_types]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [access]
-- =======================================================
Print 'Synchronization Script for: [access]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [access] ADD [hidden] bit NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [access] ADD [webdav_password] varchar(80) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [access] ADD DEFAULT (0) FOR [hidden]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [access_log]
-- =======================================================
Print 'Synchronization Script for: [access_log]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[access_log] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [account_type_levels]
-- =======================================================
Print 'Synchronization Script for: [account_type_levels]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[account_type_levels] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_item]
-- =======================================================
Print 'Synchronization Script for: [action_item]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[action_item] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_item_log]
-- =======================================================
Print 'Synchronization Script for: [action_item_log]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[action_item_log] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_item_work]
-- =======================================================
Print 'Synchronization Script for: [action_item_work]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_item_work] (
    [item_work_id] int NOT NULL IDENTITY(1, 1),
    [phase_work_id] int NOT NULL,
    [action_step_id] int NOT NULL,
    [status_id] int NULL,
    [owner] int NULL,
    [start_date] datetime NULL,
    [end_date] datetime NULL,
    [link_module_id] int NULL,
    [link_item_id] int NULL,
    [level] int NULL,
    [entered] datetime NOT NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NOT NULL,
    [modifiedby] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_item_work] ADD CONSTRAINT [PK__action_item_work__373B3228] PRIMARY KEY ([item_work_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_item_work] ADD CONSTRAINT [DF__action_it__enter__5B6E70FD] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_item_work] ADD CONSTRAINT [DF__action_it__level__5A7A4CC4] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_item_work] ADD CONSTRAINT [DF__action_it__modif__5D56B96F] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_item_work_notes]
-- =======================================================
Print 'Synchronization Script for: [action_item_work_notes]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_item_work_notes] (
    [note_id] int NOT NULL IDENTITY(1, 1),
    [item_work_id] int NOT NULL,
    [description] varchar(300) NOT NULL,
    [submitted] datetime NOT NULL,
    [submittedby] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_item_work_notes] ADD CONSTRAINT [PK__action_item_work__382F5661] PRIMARY KEY ([note_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_item_work_notes] ADD CONSTRAINT [DF__action_it__submi__621B6E8C] DEFAULT (getdate()) FOR [submitted]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_item_work_selection]
-- =======================================================
Print 'Synchronization Script for: [action_item_work_selection]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_item_work_selection] (
    [selection_id] int NOT NULL IDENTITY(1, 1),
    [item_work_id] int NOT NULL,
    [selection] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_item_work_selection] ADD CONSTRAINT [PK__action_item_work__39237A9A] PRIMARY KEY ([selection_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_list]
-- =======================================================
Print 'Synchronization Script for: [action_list]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[action_list] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_phase]
-- =======================================================
Print 'Synchronization Script for: [action_phase]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_phase] (
    [phase_id] int NOT NULL IDENTITY(1, 1),
    [parent_id] int NULL,
    [plan_id] int NOT NULL,
    [phase_name] varchar(255) NOT NULL,
    [description] varchar(2048) NULL,
    [enabled] bit NOT NULL,
    [entered] datetime NOT NULL,
    [random] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_phase] ADD CONSTRAINT [PK__action_phase__3B0BC30C] PRIMARY KEY ([phase_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_phase] ADD CONSTRAINT [DF__action_ph__enabl__204DAB2F] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_phase] ADD CONSTRAINT [DF__action_ph__enter__2141CF68] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_phase] ADD CONSTRAINT [DF__action_ph__rando__2235F3A1] DEFAULT (0) FOR [random]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_phase_work]
-- =======================================================
Print 'Synchronization Script for: [action_phase_work]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_phase_work] (
    [phase_work_id] int NOT NULL IDENTITY(1, 1),
    [plan_work_id] int NOT NULL,
    [action_phase_id] int NOT NULL,
    [status_id] int NULL,
    [start_date] datetime NULL,
    [end_date] datetime NULL,
    [level] int NULL,
    [entered] datetime NOT NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NOT NULL,
    [modifiedby] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_phase_work] ADD CONSTRAINT [PK__action_phase_wor__3BFFE745] PRIMARY KEY ([phase_work_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_phase_work] ADD CONSTRAINT [DF__action_ph__enter__50F0E28A] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_phase_work] ADD CONSTRAINT [DF__action_ph__level__4FFCBE51] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_phase_work] ADD CONSTRAINT [DF__action_ph__modif__52D92AFC] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_plan]
-- =======================================================
Print 'Synchronization Script for: [action_plan]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_plan] (
    [plan_id] int NOT NULL IDENTITY(1, 1),
    [plan_name] varchar(255) NOT NULL,
    [description] varchar(2048) NULL,
    [enabled] bit NOT NULL,
    [approved] datetime NULL,
    [entered] datetime NOT NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NOT NULL,
    [modifiedby] int NOT NULL,
    [archive_date] datetime NULL,
    [cat_code] int NULL,
    [subcat_code1] int NULL,
    [subcat_code2] int NULL,
    [subcat_code3] int NULL,
    [link_object_id] int NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_plan] ADD CONSTRAINT [PK__action_plan__3CF40B7E] PRIMARY KEY ([plan_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan] ADD CONSTRAINT [DF__action_pl__appro__12F3B011] DEFAULT (null) FOR [approved]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan] ADD CONSTRAINT [DF__action_pl__enabl__11FF8BD8] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan] ADD CONSTRAINT [DF__action_pl__enter__13E7D44A] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan] ADD CONSTRAINT [DF__action_pl__modif__15D01CBC] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_plan_category]
-- =======================================================
Print 'Synchronization Script for: [action_plan_category]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_plan_category] (
    [id] int NOT NULL IDENTITY(1, 1),
    [cat_level] int NOT NULL,
    [parent_cat_code] int NOT NULL,
    [description] varchar(300) NOT NULL,
    [full_description] text NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_plan_category] ADD CONSTRAINT [PK__action_plan_cate__3DE82FB7] PRIMARY KEY ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_category] ADD CONSTRAINT [DF__action_pl__cat_l__7A280247] DEFAULT (0) FOR [cat_level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_category] ADD CONSTRAINT [DF__action_pl__defau__7D046EF2] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_category] ADD CONSTRAINT [DF__action_pl__enabl__7EECB764] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_category] ADD CONSTRAINT [DF__action_pl__full___7C104AB9] DEFAULT ('') FOR [full_description]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_category] ADD CONSTRAINT [DF__action_pl__level__7DF8932B] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_category] ADD CONSTRAINT [DF__action_pl__paren__7B1C2680] DEFAULT (0) FOR [parent_cat_code]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_plan_category_draft]
-- =======================================================
Print 'Synchronization Script for: [action_plan_category_draft]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_plan_category_draft] (
    [id] int NOT NULL IDENTITY(1, 1),
    [link_id] int NULL,
    [cat_level] int NOT NULL,
    [parent_cat_code] int NOT NULL,
    [description] varchar(300) NOT NULL,
    [full_description] text NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_plan_category_draft] ADD CONSTRAINT [PK__action_plan_cate__3EDC53F0] PRIMARY KEY ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_category_draft] ADD CONSTRAINT [DF__action_pl__cat_l__02BD4848] DEFAULT (0) FOR [cat_level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_category_draft] ADD CONSTRAINT [DF__action_pl__defau__0599B4F3] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_category_draft] ADD CONSTRAINT [DF__action_pl__enabl__0781FD65] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_category_draft] ADD CONSTRAINT [DF__action_pl__full___04A590BA] DEFAULT ('') FOR [full_description]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_category_draft] ADD CONSTRAINT [DF__action_pl__level__068DD92C] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_category_draft] ADD CONSTRAINT [DF__action_pl__link___01C9240F] DEFAULT ((-1)) FOR [link_id]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_category_draft] ADD CONSTRAINT [DF__action_pl__paren__03B16C81] DEFAULT (0) FOR [parent_cat_code]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_plan_constants]
-- =======================================================
Print 'Synchronization Script for: [action_plan_constants]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_plan_constants] (
    [map_id] int NOT NULL IDENTITY(1, 1),
    [constant_id] int NOT NULL,
    [description] varchar(300) NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_plan_constants] ADD CONSTRAINT [PK__action_plan_cons__3FD07829] PRIMARY KEY ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating an index on the table
CREATE INDEX [action_plan_constant_id] ON [action_plan_constants] ([constant_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_plan_editor_lookup]
-- =======================================================
Print 'Synchronization Script for: [action_plan_editor_lookup]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_plan_editor_lookup] (
    [id] int NOT NULL IDENTITY(1, 1),
    [module_id] int NOT NULL,
    [constant_id] int NOT NULL,
    [level] int NULL,
    [description] text NULL,
    [entered] datetime NULL,
    [category_id] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_plan_editor_lookup] ADD CONSTRAINT [PK__action_plan_edit__40C49C62] PRIMARY KEY ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_editor_lookup] ADD CONSTRAINT [DF__action_pl__enter__0F231F2D] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_editor_lookup] ADD CONSTRAINT [DF__action_pl__level__0E2EFAF4] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_plan_work]
-- =======================================================
Print 'Synchronization Script for: [action_plan_work]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_plan_work] (
    [plan_work_id] int NOT NULL IDENTITY(1, 1),
    [action_plan_id] int NOT NULL,
    [manager] int NULL,
    [assignedTo] int NOT NULL,
    [link_module_id] int NOT NULL,
    [link_item_id] int NOT NULL,
    [enabled] bit NOT NULL,
    [entered] datetime NOT NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NOT NULL,
    [modifiedby] int NOT NULL,
    [current_phase] int NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_plan_work] ADD CONSTRAINT [PK__action_plan_work__41B8C09B] PRIMARY KEY ([plan_work_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_work] ADD CONSTRAINT [DF__action_pl__enabl__42A2C333] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_work] ADD CONSTRAINT [DF__action_pl__enter__4396E76C] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_work] ADD CONSTRAINT [DF__action_pl__modif__457F2FDE] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_plan_work_notes]
-- =======================================================
Print 'Synchronization Script for: [action_plan_work_notes]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_plan_work_notes] (
    [note_id] int NOT NULL IDENTITY(1, 1),
    [plan_work_id] int NOT NULL,
    [description] varchar(300) NOT NULL,
    [submitted] datetime NOT NULL,
    [submittedby] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_plan_work_notes] ADD CONSTRAINT [PK__action_plan_work__42ACE4D4] PRIMARY KEY ([note_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_plan_work_notes] ADD CONSTRAINT [DF__action_pl__submi__4A43E4FB] DEFAULT (getdate()) FOR [submitted]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_step]
-- =======================================================
Print 'Synchronization Script for: [action_step]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_step] (
    [step_id] int NOT NULL IDENTITY(1, 1),
    [parent_id] int NULL,
    [phase_id] int NOT NULL,
    [description] varchar(2048) NULL,
    [action_id] int NULL,
    [duration_type_id] int NULL,
    [estimated_duration] int NULL,
    [category_id] int NULL,
    [field_id] int NULL,
    [permission_type] int NULL,
    [role_id] int NULL,
    [department_id] int NULL,
    [enabled] bit NOT NULL,
    [entered] datetime NOT NULL,
    [allow_skip_to_here] bit NOT NULL,
    [label] varchar(80) NULL,
    [action_required] bit NOT NULL,
    [group_id] int NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_step] ADD CONSTRAINT [PK__action_step__43A1090D] PRIMARY KEY ([step_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_step] ADD CONSTRAINT [DF__action_st__actio__391958F9] DEFAULT (0) FOR [action_required]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_step] ADD CONSTRAINT [DF__action_st__allow__382534C0] DEFAULT (0) FOR [allow_skip_to_here]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_step] ADD CONSTRAINT [DF__action_st__enabl__363CEC4E] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_step] ADD CONSTRAINT [DF__action_st__enter__37311087] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [action_step_lookup]
-- =======================================================
Print 'Synchronization Script for: [action_step_lookup]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [action_step_lookup] (
    [code] int NOT NULL IDENTITY(1, 1),
    [step_id] int NOT NULL,
    [description] varchar(255) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [action_step_lookup] ADD CONSTRAINT [PK__action_step_look__44952D46] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_step_lookup] ADD CONSTRAINT [DF__action_st__defau__66E023A9] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_step_lookup] ADD CONSTRAINT [DF__action_st__enabl__68C86C1B] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [action_step_lookup] ADD CONSTRAINT [DF__action_st__level__67D447E2] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [active_campaign_groups]
-- =======================================================
Print 'Synchronization Script for: [active_campaign_groups]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[active_campaign_groups] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [active_survey]
-- =======================================================
Print 'Synchronization Script for: [active_survey]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[active_survey] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [active_survey_answer_avg]
-- =======================================================
Print 'Synchronization Script for: [active_survey_answer_avg]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[active_survey_answer_avg] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [active_survey_answer_items]
-- =======================================================
Print 'Synchronization Script for: [active_survey_answer_items]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[active_survey_answer_items] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [active_survey_answers]
-- =======================================================
Print 'Synchronization Script for: [active_survey_answers]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[active_survey_answers] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [active_survey_items]
-- =======================================================
Print 'Synchronization Script for: [active_survey_items]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[active_survey_items] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [active_survey_questions]
-- =======================================================
Print 'Synchronization Script for: [active_survey_questions]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[active_survey_questions] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [active_survey_responses]
-- =======================================================
Print 'Synchronization Script for: [active_survey_responses]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [active_survey_responses] ADD [address_updated] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [asset]
-- =======================================================
Print 'Synchronization Script for: [asset]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [asset] ADD [parent_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [asset] ADD [trashed_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [asset_category]
-- =======================================================
Print 'Synchronization Script for: [asset_category]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [asset_category] ADD DEFAULT (0) FOR [parent_cat_code]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [asset_category_draft]
-- =======================================================
Print 'Synchronization Script for: [asset_category_draft]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [asset_category_draft] ADD DEFAULT (0) FOR [parent_cat_code]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [asset_materials_map]
-- =======================================================
Print 'Synchronization Script for: [asset_materials_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [asset_materials_map] (
    [map_id] int NOT NULL IDENTITY(1, 1),
    [asset_id] int NOT NULL,
    [code] int NOT NULL,
    [quantity] float NULL,
    [entered] datetime NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [asset_materials_map] ADD CONSTRAINT [PK__asset_materials___5006DFF2] PRIMARY KEY ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [asset_materials_map] ADD CONSTRAINT [DF__asset_mat__enter__17786E0C] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [autoguide_ad_run]
-- =======================================================
Print 'Synchronization Script for: [autoguide_ad_run]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[autoguide_ad_run] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [autoguide_ad_run_types]
-- =======================================================
Print 'Synchronization Script for: [autoguide_ad_run_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[autoguide_ad_run_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [autoguide_inventory]
-- =======================================================
Print 'Synchronization Script for: [autoguide_inventory]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[autoguide_inventory] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [autoguide_inventory_options]
-- =======================================================
Print 'Synchronization Script for: [autoguide_inventory_options]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[autoguide_inventory_options] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [autoguide_make]
-- =======================================================
Print 'Synchronization Script for: [autoguide_make]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[autoguide_make] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [autoguide_model]
-- =======================================================
Print 'Synchronization Script for: [autoguide_model]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[autoguide_model] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [autoguide_options]
-- =======================================================
Print 'Synchronization Script for: [autoguide_options]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[autoguide_options] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [autoguide_vehicle]
-- =======================================================
Print 'Synchronization Script for: [autoguide_vehicle]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[autoguide_vehicle] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [business_process]
-- =======================================================
Print 'Synchronization Script for: [business_process]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[business_process] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [business_process_component]
-- =======================================================
Print 'Synchronization Script for: [business_process_component]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[business_process_component] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [business_process_component_library]
-- =======================================================
Print 'Synchronization Script for: [business_process_component_library]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[business_process_component_library] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [business_process_component_parameter]
-- =======================================================
Print 'Synchronization Script for: [business_process_component_parameter]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[business_process_component_parameter] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [business_process_component_result_lookup]
-- =======================================================
Print 'Synchronization Script for: [business_process_component_result_lookup]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[business_process_component_result_lookup] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [business_process_events]
-- =======================================================
Print 'Synchronization Script for: [business_process_events]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[business_process_events] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [business_process_hook]
-- =======================================================
Print 'Synchronization Script for: [business_process_hook]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [business_process_hook] ADD [priority] int NOT NULL CONSTRAINT [DF__business___prior__4218B34E] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [business_process_hook_library]
-- =======================================================
Print 'Synchronization Script for: [business_process_hook_library]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[business_process_hook_library] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [business_process_hook_triggers]
-- =======================================================
Print 'Synchronization Script for: [business_process_hook_triggers]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[business_process_hook_triggers] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [business_process_log]
-- =======================================================
Print 'Synchronization Script for: [business_process_log]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[business_process_log] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [business_process_parameter]
-- =======================================================
Print 'Synchronization Script for: [business_process_parameter]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[business_process_parameter] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [business_process_parameter_library]
-- =======================================================
Print 'Synchronization Script for: [business_process_parameter_library]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[business_process_parameter_library] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [call_log]
-- =======================================================
Print 'Synchronization Script for: [call_log]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the default constraint
--ALTER TABLE [call_log] DROP CONSTRAINT [DF__call_log__alertd__14B10FFA]
--GO
EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CNSTR CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(o.name)
     FROM sysobjects o
     INNER JOIN sysconstraints sc ON o.id = sc.constid
     WHERE object_name(o.parent_obj) = ''call_log''
     AND o.xtype = ''D''
     AND o.name LIKE ''DF__call_log__alertd__%''
OPEN CNSTR
FETCH CNSTR INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN
    EXEC sp_executesql @sql
    FETCH CNSTR INTO @sql
END
CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)','[call_log]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Changing the column's definition
ALTER TABLE [call_log] ALTER COLUMN [status_id] int NOT NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [call_log] ADD [trashed_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [call_log] ADD DEFAULT (null) FOR [alert]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [campaign]
-- =======================================================
Print 'Synchronization Script for: [campaign]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [campaign] ADD [bcc] varchar(1024) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [campaign] ADD [cc] varchar(1024) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [campaign] ADD [trashed_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [campaign_list_groups]
-- =======================================================
Print 'Synchronization Script for: [campaign_list_groups]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[campaign_list_groups] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [campaign_run]
-- =======================================================
Print 'Synchronization Script for: [campaign_run]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[campaign_run] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [campaign_survey_link]
-- =======================================================
Print 'Synchronization Script for: [campaign_survey_link]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[campaign_survey_link] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [category_editor_lookup]
-- =======================================================
Print 'Synchronization Script for: [category_editor_lookup]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[category_editor_lookup] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [cfsinbox_message]
-- =======================================================
Print 'Synchronization Script for: [cfsinbox_message]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[cfsinbox_message] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [cfsinbox_messagelink]
-- =======================================================
Print 'Synchronization Script for: [cfsinbox_messagelink]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[cfsinbox_messagelink] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [contact]
-- =======================================================
Print 'Synchronization Script for: [contact]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping references to the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[contact]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all foreign keys of the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[contact]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all defaults of the table
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
  CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)',N'[contact]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all indexes of the table
  EXEC sp_executesql N'
  DECLARE @sql nvarchar(4000)
  DECLARE CRIDX CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
       SELECT ''DROP INDEX ''+@table+''.''+QUOTENAME(i.name)
       FROM dbo.sysindexes i
       WHERE i.id=OBJECT_ID(@table)
       AND i.indid NOT IN (0,255)
       AND INDEXPROPERTY(i.id, i.name, ''IsHypothetical'')=0
       AND INDEXPROPERTY(i.id, i.name, ''IsStatistics'')=0
       AND INDEXPROPERTY(i.id, i.name, ''IsAutoStatistics'')=0
       AND i.status&2048=0 AND i.status&4096=0
  OPEN CRIDX
  FETCH CRIDX INTO @sql
  WHILE @@FETCH_STATUS=0
  BEGIN 
      EXEC sp_executesql @sql
      FETCH CRIDX INTO @sql
  END
  CLOSE CRIDX DEALLOCATE CRIDX',N'@table nvarchar(261)',N'[contact]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [contact] DROP CONSTRAINT [PK__contact__18B6AB08]
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the column
ALTER TABLE [contact] DROP COLUMN [imservice]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Changing the column's definition
-- Renaming the column
EXEC sp_rename N'[contact].[imname]',N'nickname','COLUMN'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

ALTER TABLE [contact] ALTER COLUMN [nickname] varchar(80) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [account_number] varchar(50) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [additional_names] varchar(255) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [comments] varchar(255) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [conversion_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [industry_temp_code] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [information_update_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [lead] bit NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [lead_status] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [potential] float NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [rating] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [revenue] float NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [role] varchar(255) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [secret_word] varchar(255) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [source] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [contact] ADD [trashed_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [contact] ADD PRIMARY KEY ([contact_id])
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [contact] ADD DEFAULT ((-1)) FOR [custom1]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [contact] ADD DEFAULT (0) FOR [employee]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [contact] ADD DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [contact] ADD DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [contact] ADD DEFAULT (getdate()) FOR [information_update_date]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [contact] ADD DEFAULT (0) FOR [lead]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [contact] ADD DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [contact] ADD DEFAULT (0) FOR [primary_contact]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating an index on the table
CREATE INDEX [contact_import_id_idx] ON [contact] ([import_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating an index on the table
CREATE INDEX [contact_user_id_idx] ON [contact] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating an index on the table
CREATE INDEX [contactlist_company] ON [contact] ([company], [namelast], [namefirst])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating an index on the table
CREATE INDEX [contactlist_namecompany] ON [contact] ([namelast], [namefirst], [company])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [contact_address]
-- =======================================================
Print 'Synchronization Script for: [contact_address]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Changing the column's definition
ALTER TABLE [contact_address] ALTER COLUMN [primary_address] bit NOT NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the index
CREATE INDEX [contact_address_contact_id_idx] ON [contact_address] ([contact_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the index
CREATE INDEX [contact_address_postalcode_idx] ON [contact_address] ([postalcode])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the index
CREATE INDEX [contact_city_idx] ON [contact_address] ([city])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [contact_emailaddress]
-- =======================================================
Print 'Synchronization Script for: [contact_emailaddress]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

UPDATE contact_emailaddress SET primary_email=0;
GO

-- Changing the column's definition
ALTER TABLE [contact_emailaddress] ALTER COLUMN [primary_email] bit NOT NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [contact_emailaddress] ADD DEFAULT (0) FOR [primary_email]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the index
CREATE INDEX [contact_email_contact_id_idx] ON [contact_emailaddress] ([contact_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [contact_imaddress]
-- =======================================================
Print 'Synchronization Script for: [contact_imaddress]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
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

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [PK__contact_imaddres__7B264821] PRIMARY KEY ([address_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [DF__contact_i__enter__7EF6D905] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [DF__contact_i__modif__00DF2177] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [DF__contact_i__prima__02C769E9] DEFAULT (0) FOR [primary_im]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [contact_lead_read_map]
-- =======================================================
Print 'Synchronization Script for: [contact_lead_read_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [contact_lead_read_map] (
    [map_id] int NOT NULL IDENTITY(1, 1),
    [user_id] int NOT NULL,
    [contact_id] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [contact_lead_read_map] ADD CONSTRAINT [PK__contact_lead_rea__0D7A0286] PRIMARY KEY ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [contact_lead_skipped_map]
-- =======================================================
Print 'Synchronization Script for: [contact_lead_skipped_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [contact_lead_skipped_map] (
    [map_id] int NOT NULL IDENTITY(1, 1),
    [user_id] int NOT NULL,
    [contact_id] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [contact_lead_skipped_map] ADD CONSTRAINT [PK__contact_lead_ski__09A971A2] PRIMARY KEY ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [contact_phone]
-- =======================================================
Print 'Synchronization Script for: [contact_phone]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Changing the column's definition
ALTER TABLE [contact_phone] ALTER COLUMN [primary_number] bit NOT NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the index
CREATE INDEX [contact_phone_contact_id_idx] ON [contact_phone] ([contact_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [contact_textmessageaddress]
-- =======================================================
Print 'Synchronization Script for: [contact_textmessageaddress]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
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

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [PK__contact_textmess__04AFB25B] PRIMARY KEY ([address_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [DF__contact_t__enter__078C1F06] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [DF__contact_t__modif__09746778] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [DF__contact_t__prima__0B5CAFEA] DEFAULT (0) FOR [primary_textmessage_address]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [contact_type_levels]
-- =======================================================
Print 'Synchronization Script for: [contact_type_levels]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[contact_type_levels] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [custom_field_category]
-- =======================================================
Print 'Synchronization Script for: [custom_field_category]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[custom_field_category] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [custom_field_data]
-- =======================================================
Print 'Synchronization Script for: [custom_field_data]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[custom_field_data] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [custom_field_group]
-- =======================================================
Print 'Synchronization Script for: [custom_field_group]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[custom_field_group] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [custom_field_info]
-- =======================================================
Print 'Synchronization Script for: [custom_field_info]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[custom_field_info] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [custom_field_lookup]
-- =======================================================
Print 'Synchronization Script for: [custom_field_lookup]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[custom_field_lookup] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [custom_field_record]
-- =======================================================
Print 'Synchronization Script for: [custom_field_record]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[custom_field_record] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [customer_product]
-- =======================================================
Print 'Synchronization Script for: [customer_product]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[customer_product] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [customer_product_history]
-- =======================================================
Print 'Synchronization Script for: [customer_product_history]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Changing the column's definition
ALTER TABLE [customer_product_history] ALTER COLUMN [order_id] int NOT NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [customer_product_history] ADD [order_item_id] int NOT NULL DEFAULT 0
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [database_version]
-- =======================================================
Print 'Synchronization Script for: [database_version]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[database_version] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [document_store]
-- =======================================================
Print 'Synchronization Script for: [document_store]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
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
    [modifiedBy] int NOT NULL,
    [trashed_date] datetime NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [document_store] ADD CONSTRAINT [PK__document_store__1269A02C] PRIMARY KEY ([document_store_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [document_store] ADD CONSTRAINT [DF__document___enter__15460CD7] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [document_store] ADD CONSTRAINT [DF__document___modif__172E5549] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [document_store] ADD CONSTRAINT [DF__document___reque__135DC465] DEFAULT (getdate()) FOR [requestDate]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [document_store_department_member]
-- =======================================================
Print 'Synchronization Script for: [document_store_department_member]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
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

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [document_store_department_member] ADD CONSTRAINT [DF__document___enter__30EE274C] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [document_store_department_member] ADD CONSTRAINT [DF__document___modif__32D66FBE] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [document_store_permissions]
-- =======================================================
Print 'Synchronization Script for: [document_store_permissions]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [document_store_permissions] (
    [id] int NOT NULL IDENTITY(1, 1),
    [document_store_id] int NOT NULL,
    [permission_id] int NOT NULL,
    [userlevel] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [document_store_permissions] ADD CONSTRAINT [PK__document_store_p__1A0AC1F4] PRIMARY KEY ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [document_store_role_member]
-- =======================================================
Print 'Synchronization Script for: [document_store_role_member]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
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

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [document_store_role_member] ADD CONSTRAINT [DF__document___enter__294D0584] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [document_store_role_member] ADD CONSTRAINT [DF__document___modif__2B354DF6] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [document_store_user_member]
-- =======================================================
Print 'Synchronization Script for: [document_store_user_member]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
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

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [document_store_user_member] ADD CONSTRAINT [DF__document___enter__21ABE3BC] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [document_store_user_member] ADD CONSTRAINT [DF__document___modif__23942C2E] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [events]
-- =======================================================
Print 'Synchronization Script for: [events]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[events] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [events_log]
-- =======================================================
Print 'Synchronization Script for: [events_log]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[events_log] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [excluded_recipient]
-- =======================================================
Print 'Synchronization Script for: [excluded_recipient]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[excluded_recipient] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [field_types]
-- =======================================================
Print 'Synchronization Script for: [field_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Changing the column's definition
ALTER TABLE [field_types] ALTER COLUMN [enabled] bit NOT NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [help_business_rules]
-- =======================================================
Print 'Synchronization Script for: [help_business_rules]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[help_business_rules] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [help_contents]
-- =======================================================
Print 'Synchronization Script for: [help_contents]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[help_contents] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [help_faqs]
-- =======================================================
Print 'Synchronization Script for: [help_faqs]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[help_faqs] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [help_features]
-- =======================================================
Print 'Synchronization Script for: [help_features]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[help_features] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [help_module]
-- =======================================================
Print 'Synchronization Script for: [help_module]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[help_module] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [help_notes]
-- =======================================================
Print 'Synchronization Script for: [help_notes]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[help_notes] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [help_related_links]
-- =======================================================
Print 'Synchronization Script for: [help_related_links]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[help_related_links] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [help_tableof_contents]
-- =======================================================
Print 'Synchronization Script for: [help_tableof_contents]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[help_tableof_contents] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [help_tableofcontentitem_links]
-- =======================================================
Print 'Synchronization Script for: [help_tableofcontentitem_links]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[help_tableofcontentitem_links] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [help_tips]
-- =======================================================
Print 'Synchronization Script for: [help_tips]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[help_tips] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [history]
-- =======================================================
Print 'Synchronization Script for: [history]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [history] (
    [history_id] int NOT NULL IDENTITY(1, 1),
    [contact_id] int NULL,
    [org_id] int NULL,
    [link_object_id] int NOT NULL,
    [link_item_id] int NULL,
    [status] varchar(255) NULL,
    [type] varchar(255) NULL,
    [description] text NULL,
    [level] int NULL,
    [enabled] bit NULL,
    [entered] datetime NOT NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NOT NULL,
    [modifiedby] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [history] ADD CONSTRAINT [PK__history__056ECC6A] PRIMARY KEY ([history_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [history] ADD CONSTRAINT [DF__history__enabled__7C4554E3] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [history] ADD CONSTRAINT [DF__history__entered__7D39791C] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [history] ADD CONSTRAINT [DF__history__level__7B5130AA] DEFAULT (10) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [history] ADD CONSTRAINT [DF__history__modifie__7F21C18E] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [import]
-- =======================================================
Print 'Synchronization Script for: [import]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[import] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [knowledge_base]
-- =======================================================
Print 'Synchronization Script for: [knowledge_base]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [knowledge_base] (
    [kb_id] int NOT NULL IDENTITY(1, 1),
    [category_id] int NULL,
    [title] varchar(255) NOT NULL,
    [description] text NULL,
    [item_id] int NULL,
    [entered] datetime NOT NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NOT NULL,
    [modifiedby] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [knowledge_base] ADD CONSTRAINT [PK__knowledge_base__075714DC] PRIMARY KEY ([kb_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [knowledge_base] ADD CONSTRAINT [DF__knowledge__enter__78FED3E4] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [knowledge_base] ADD CONSTRAINT [DF__knowledge__modif__7AE71C56] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_access_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_access_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_access_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_account_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_account_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_account_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_asset_manufacturer]
-- =======================================================
Print 'Synchronization Script for: [lookup_asset_manufacturer]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_asset_manufacturer] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_asset_materials]
-- =======================================================
Print 'Synchronization Script for: [lookup_asset_materials]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_asset_materials] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_asset_materials] ADD CONSTRAINT [PK__lookup_asset_mat__0B27A5C0] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_asset_materials] ADD CONSTRAINT [DF__lookup_as__defau__11BF94B6] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_asset_materials] ADD CONSTRAINT [DF__lookup_as__enabl__12B3B8EF] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_asset_status]
-- =======================================================
Print 'Synchronization Script for: [lookup_asset_status]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_asset_status] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_asset_vendor]
-- =======================================================
Print 'Synchronization Script for: [lookup_asset_vendor]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_asset_vendor] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_call_priority]
-- =======================================================
Print 'Synchronization Script for: [lookup_call_priority]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_call_priority] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_call_reminder]
-- =======================================================
Print 'Synchronization Script for: [lookup_call_reminder]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_call_reminder] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_call_result]
-- =======================================================
Print 'Synchronization Script for: [lookup_call_result]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_call_result] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_call_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_call_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_call_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_contact_rating]
-- =======================================================
Print 'Synchronization Script for: [lookup_contact_rating]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_contact_rating] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(50) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_contact_rating] ADD CONSTRAINT [PK__lookup_contact_r__403A8C7D] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_contact_rating] ADD CONSTRAINT [DF__lookup_co__defau__412EB0B6] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_contact_rating] ADD CONSTRAINT [DF__lookup_co__enabl__4316F928] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_contact_rating] ADD CONSTRAINT [DF__lookup_co__level__4222D4EF] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_contact_source]
-- =======================================================
Print 'Synchronization Script for: [lookup_contact_source]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_contact_source] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(50) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_contact_source] ADD CONSTRAINT [PK__lookup_contact_s__3B75D760] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_contact_source] ADD CONSTRAINT [DF__lookup_co__defau__3C69FB99] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_contact_source] ADD CONSTRAINT [DF__lookup_co__enabl__3E52440B] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_contact_source] ADD CONSTRAINT [DF__lookup_co__level__3D5E1FD2] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_contact_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_contact_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_contact_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_contactaddress_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_contactaddress_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_contactaddress_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_contactemail_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_contactemail_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_contactemail_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_contactphone_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_contactphone_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_contactphone_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_creditcard_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_creditcard_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_creditcard_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_currency]
-- =======================================================
Print 'Synchronization Script for: [lookup_currency]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_currency] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_delivery_options]
-- =======================================================
Print 'Synchronization Script for: [lookup_delivery_options]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_delivery_options] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_department]
-- =======================================================
Print 'Synchronization Script for: [lookup_department]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_department] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_document_store_permission]
-- =======================================================
Print 'Synchronization Script for: [lookup_document_store_permission]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
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

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [PK__lookup_document___09D45A2B] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a unique constraint for the table
ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [UQ__lookup_document___0AC87E64] UNIQUE ([permission])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [DF__lookup_do__defau__0CB0C6D6] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [DF__lookup_do__enabl__0E990F48] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [DF__lookup_do__group__0F8D3381] DEFAULT (0) FOR [group_id]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [DF__lookup_do__level__0DA4EB0F] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_document_store_permission_category]
-- =======================================================
Print 'Synchronization Script for: [lookup_document_store_permission_category]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_document_store_permission_category] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL,
    [group_id] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_document_store_permission_category] ADD CONSTRAINT [PK__lookup_document___7E62A77F] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_document_store_permission_category] ADD CONSTRAINT [DF__lookup_do__defau__7F56CBB8] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_document_store_permission_category] ADD CONSTRAINT [DF__lookup_do__enabl__013F142A] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_document_store_permission_category] ADD CONSTRAINT [DF__lookup_do__group__02333863] DEFAULT (0) FOR [group_id]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_document_store_permission_category] ADD CONSTRAINT [DF__lookup_do__level__004AEFF1] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_document_store_role]
-- =======================================================
Print 'Synchronization Script for: [lookup_document_store_role]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_document_store_role] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(50) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL,
    [group_id] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_document_store_role] ADD CONSTRAINT [PK__lookup_document___041B80D5] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_document_store_role] ADD CONSTRAINT [DF__lookup_do__defau__050FA50E] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_document_store_role] ADD CONSTRAINT [DF__lookup_do__enabl__06F7ED80] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_document_store_role] ADD CONSTRAINT [DF__lookup_do__group__07EC11B9] DEFAULT (0) FOR [group_id]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_document_store_role] ADD CONSTRAINT [DF__lookup_do__level__0603C947] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_duration_type]
-- =======================================================
Print 'Synchronization Script for: [lookup_duration_type]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_duration_type] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_duration_type] ADD CONSTRAINT [PK__lookup_duration___1E3A7A34] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_duration_type] ADD CONSTRAINT [DF__lookup_du__defau__2512604C] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_duration_type] ADD CONSTRAINT [DF__lookup_du__enabl__26FAA8BE] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_duration_type] ADD CONSTRAINT [DF__lookup_du__level__26068485] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_email_model]
-- =======================================================
Print 'Synchronization Script for: [lookup_email_model]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_email_model] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_employment_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_employment_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_employment_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_help_features]
-- =======================================================
Print 'Synchronization Script for: [lookup_help_features]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_help_features] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_hours_reason]
-- =======================================================
Print 'Synchronization Script for: [lookup_hours_reason]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_hours_reason] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_im_services]
-- =======================================================
Print 'Synchronization Script for: [lookup_im_services]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_im_services] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(50) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_im_services] ADD CONSTRAINT [PK__lookup_im_servic__36B12243] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_im_services] ADD CONSTRAINT [DF__lookup_im__defau__37A5467C] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_im_services] ADD CONSTRAINT [DF__lookup_im__enabl__398D8EEE] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_im_services] ADD CONSTRAINT [DF__lookup_im__level__38996AB5] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_im_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_im_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_im_types] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(50) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_im_types] ADD CONSTRAINT [PK__lookup_im_types__31EC6D26] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_im_types] ADD CONSTRAINT [DF__lookup_im__defau__32E0915F] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_im_types] ADD CONSTRAINT [DF__lookup_im__enabl__34C8D9D1] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_im_types] ADD CONSTRAINT [DF__lookup_im__level__33D4B598] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_industry]
-- =======================================================
Print 'Synchronization Script for: [lookup_industry]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_industry] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_lists_lookup]
-- =======================================================
Print 'Synchronization Script for: [lookup_lists_lookup]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_lists_lookup] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_locale]
-- =======================================================
Print 'Synchronization Script for: [lookup_locale]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_locale] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_news_template]
-- =======================================================
Print 'Synchronization Script for: [lookup_news_template]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
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

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_news_template] ADD CONSTRAINT [PK__lookup_news_temp__0169315C] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__defau__025D5595] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__enabl__04459E07] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__group__0539C240] DEFAULT (0) FOR [group_id]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__level__035179CE] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__load___062DE679] DEFAULT (0) FOR [load_article]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__load___07220AB2] DEFAULT (0) FOR [load_project_article_list]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__load___08162EEB] DEFAULT (0) FOR [load_article_linked_list]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__load___090A5324] DEFAULT (0) FOR [load_public_projects]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_news_template] ADD CONSTRAINT [DF__lookup_ne__load___09FE775D] DEFAULT (0) FOR [load_article_category_list]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_onsite_model]
-- =======================================================
Print 'Synchronization Script for: [lookup_onsite_model]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_onsite_model] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_opportunity_budget]
-- =======================================================
Print 'Synchronization Script for: [lookup_opportunity_budget]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_opportunity_budget] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_opportunity_budget] ADD CONSTRAINT [PK__lookup_opportuni__29AC2CE0] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_opportunity_budget] ADD CONSTRAINT [DF__lookup_op__defau__436BFEE3] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_opportunity_budget] ADD CONSTRAINT [DF__lookup_op__enabl__45544755] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_opportunity_budget] ADD CONSTRAINT [DF__lookup_op__level__4460231C] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_opportunity_competitors]
-- =======================================================
Print 'Synchronization Script for: [lookup_opportunity_competitors]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_opportunity_competitors] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_opportunity_competitors] ADD CONSTRAINT [PK__lookup_opportuni__2AA05119] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_opportunity_competitors] ADD CONSTRAINT [DF__lookup_op__defau__37FA4C37] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_opportunity_competitors] ADD CONSTRAINT [DF__lookup_op__enabl__39E294A9] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_opportunity_competitors] ADD CONSTRAINT [DF__lookup_op__level__38EE7070] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_opportunity_environment]
-- =======================================================
Print 'Synchronization Script for: [lookup_opportunity_environment]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_opportunity_environment] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_opportunity_environment] ADD CONSTRAINT [PK__lookup_opportuni__2B947552] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_opportunity_environment] ADD CONSTRAINT [DF__lookup_op__defau__324172E1] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_opportunity_environment] ADD CONSTRAINT [DF__lookup_op__enabl__3429BB53] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_opportunity_environment] ADD CONSTRAINT [DF__lookup_op__level__3335971A] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_opportunity_event_compelling]
-- =======================================================
Print 'Synchronization Script for: [lookup_opportunity_event_compelling]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_opportunity_event_compelling] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_opportunity_event_compelling] ADD CONSTRAINT [PK__lookup_opportuni__2C88998B] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_opportunity_event_compelling] ADD CONSTRAINT [DF__lookup_op__defau__3DB3258D] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_opportunity_event_compelling] ADD CONSTRAINT [DF__lookup_op__enabl__3F9B6DFF] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_opportunity_event_compelling] ADD CONSTRAINT [DF__lookup_op__level__3EA749C6] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_opportunity_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_opportunity_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_opportunity_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_order_source]
-- =======================================================
Print 'Synchronization Script for: [lookup_order_source]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_order_source] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_order_status]
-- =======================================================
Print 'Synchronization Script for: [lookup_order_status]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_order_status] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_order_terms]
-- =======================================================
Print 'Synchronization Script for: [lookup_order_terms]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_order_terms] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_order_type]
-- =======================================================
Print 'Synchronization Script for: [lookup_order_type]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_order_type] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_orderaddress_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_orderaddress_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_orderaddress_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_orgaddress_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_orgaddress_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_orgaddress_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_orgemail_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_orgemail_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_orgemail_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_orgphone_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_orgphone_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_orgphone_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_payment_methods]
-- =======================================================
Print 'Synchronization Script for: [lookup_payment_methods]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_payment_methods] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_payment_status]
-- =======================================================
Print 'Synchronization Script for: [lookup_payment_status]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_payment_status] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_payment_status] ADD CONSTRAINT [PK__lookup_payment_s__246854D6] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_payment_status] ADD CONSTRAINT [DF__lookup_pa__defau__255C790F] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_payment_status] ADD CONSTRAINT [DF__lookup_pa__enabl__2744C181] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_payment_status] ADD CONSTRAINT [DF__lookup_pa__level__26509D48] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_phone_model]
-- =======================================================
Print 'Synchronization Script for: [lookup_phone_model]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_phone_model] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_product_category_type]
-- =======================================================
Print 'Synchronization Script for: [lookup_product_category_type]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_product_category_type] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_product_conf_result]
-- =======================================================
Print 'Synchronization Script for: [lookup_product_conf_result]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_product_conf_result] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_product_format]
-- =======================================================
Print 'Synchronization Script for: [lookup_product_format]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_product_format] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_product_keyword]
-- =======================================================
Print 'Synchronization Script for: [lookup_product_keyword]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_product_keyword] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_product_manufacturer]
-- =======================================================
Print 'Synchronization Script for: [lookup_product_manufacturer]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_product_manufacturer] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_product_manufacturer] ADD CONSTRAINT [PK__lookup_product_m__72B0FDB1] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_product_manufacturer] ADD CONSTRAINT [DF__lookup_pr__defau__73A521EA] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_product_manufacturer] ADD CONSTRAINT [DF__lookup_pr__enabl__758D6A5C] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_product_manufacturer] ADD CONSTRAINT [DF__lookup_pr__level__74994623] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_product_ship_time]
-- =======================================================
Print 'Synchronization Script for: [lookup_product_ship_time]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_product_ship_time] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_product_shipping]
-- =======================================================
Print 'Synchronization Script for: [lookup_product_shipping]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_product_shipping] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_product_tax]
-- =======================================================
Print 'Synchronization Script for: [lookup_product_tax]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_product_tax] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_product_type]
-- =======================================================
Print 'Synchronization Script for: [lookup_product_type]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_product_type] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_project_activity]
-- =======================================================
Print 'Synchronization Script for: [lookup_project_activity]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_project_activity] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_project_category]
-- =======================================================
Print 'Synchronization Script for: [lookup_project_category]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_project_category] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_project_loe]
-- =======================================================
Print 'Synchronization Script for: [lookup_project_loe]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_project_loe] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_project_permission]
-- =======================================================
Print 'Synchronization Script for: [lookup_project_permission]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_project_permission] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_project_permission_category]
-- =======================================================
Print 'Synchronization Script for: [lookup_project_permission_category]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_project_permission_category] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_project_priority]
-- =======================================================
Print 'Synchronization Script for: [lookup_project_priority]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_project_priority] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_project_role]
-- =======================================================
Print 'Synchronization Script for: [lookup_project_role]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_project_role] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_project_status]
-- =======================================================
Print 'Synchronization Script for: [lookup_project_status]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_project_status] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_quote_condition]
-- =======================================================
Print 'Synchronization Script for: [lookup_quote_condition]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_quote_condition] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_quote_condition] ADD CONSTRAINT [PK__lookup_quote_con__75985754] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_quote_condition] ADD CONSTRAINT [DF__lookup_qu__defau__768C7B8D] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_quote_condition] ADD CONSTRAINT [DF__lookup_qu__enabl__7874C3FF] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_quote_condition] ADD CONSTRAINT [DF__lookup_qu__level__77809FC6] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_quote_delivery]
-- =======================================================
Print 'Synchronization Script for: [lookup_quote_delivery]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_quote_delivery] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_quote_delivery] ADD CONSTRAINT [PK__lookup_quote_del__70D3A237] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_quote_delivery] ADD CONSTRAINT [DF__lookup_qu__defau__71C7C670] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_quote_delivery] ADD CONSTRAINT [DF__lookup_qu__enabl__73B00EE2] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_quote_delivery] ADD CONSTRAINT [DF__lookup_qu__level__72BBEAA9] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_quote_remarks]
-- =======================================================
Print 'Synchronization Script for: [lookup_quote_remarks]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_quote_remarks] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_quote_remarks] ADD CONSTRAINT [PK__lookup_quote_rem__1328BA3B] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_quote_remarks] ADD CONSTRAINT [DF__lookup_qu__defau__141CDE74] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_quote_remarks] ADD CONSTRAINT [DF__lookup_qu__enabl__160526E6] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_quote_remarks] ADD CONSTRAINT [DF__lookup_qu__level__151102AD] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_quote_source]
-- =======================================================
Print 'Synchronization Script for: [lookup_quote_source]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_quote_source] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_quote_status]
-- =======================================================
Print 'Synchronization Script for: [lookup_quote_status]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_quote_status] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_quote_terms]
-- =======================================================
Print 'Synchronization Script for: [lookup_quote_terms]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_quote_terms] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_quote_type]
-- =======================================================
Print 'Synchronization Script for: [lookup_quote_type]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_quote_type] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_recurring_type]
-- =======================================================
Print 'Synchronization Script for: [lookup_recurring_type]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_recurring_type] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_relationship_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_relationship_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
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

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_relationship_types] ADD CONSTRAINT [PK__lookup_relations__0C1BC9F9] PRIMARY KEY ([type_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_relationship_types] ADD CONSTRAINT [DF__lookup_re__defau__0E04126B] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_relationship_types] ADD CONSTRAINT [DF__lookup_re__enabl__0EF836A4] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_relationship_types] ADD CONSTRAINT [DF__lookup_re__level__0D0FEE32] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_response_model]
-- =======================================================
Print 'Synchronization Script for: [lookup_response_model]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_response_model] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_revenue_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_revenue_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_revenue_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_revenuedetail_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_revenuedetail_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_revenuedetail_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_sc_category]
-- =======================================================
Print 'Synchronization Script for: [lookup_sc_category]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_sc_category] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_sc_type]
-- =======================================================
Print 'Synchronization Script for: [lookup_sc_type]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_sc_type] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_stage]
-- =======================================================
Print 'Synchronization Script for: [lookup_stage]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_stage] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_step_actions]
-- =======================================================
Print 'Synchronization Script for: [lookup_step_actions]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_step_actions] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_step_actions] ADD CONSTRAINT [PK__lookup_step_acti__5772F790] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_step_actions] ADD CONSTRAINT [DF__lookup_st__defau__29D71569] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_step_actions] ADD CONSTRAINT [DF__lookup_st__enabl__2BBF5DDB] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_step_actions] ADD CONSTRAINT [DF__lookup_st__level__2ACB39A2] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_survey_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_survey_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_survey_types] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_task_category]
-- =======================================================
Print 'Synchronization Script for: [lookup_task_category]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Changing the column's definition
ALTER TABLE [lookup_task_category] ALTER COLUMN [description] varchar(255) NOT NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_task_loe]
-- =======================================================
Print 'Synchronization Script for: [lookup_task_loe]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_task_loe] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_task_priority]
-- =======================================================
Print 'Synchronization Script for: [lookup_task_priority]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_task_priority] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_textmessage_types]
-- =======================================================
Print 'Synchronization Script for: [lookup_textmessage_types]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_textmessage_types] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(50) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_textmessage_types] ADD CONSTRAINT [PK__lookup_textmessa__44FF419A] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_textmessage_types] ADD CONSTRAINT [DF__lookup_te__defau__45F365D3] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_textmessage_types] ADD CONSTRAINT [DF__lookup_te__enabl__47DBAE45] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_textmessage_types] ADD CONSTRAINT [DF__lookup_te__level__46E78A0C] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_ticket_cause]
-- =======================================================
Print 'Synchronization Script for: [lookup_ticket_cause]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_ticket_cause] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_ticket_cause] ADD CONSTRAINT [PK__lookup_ticket_ca__5D2BD0E6] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_ticket_cause] ADD CONSTRAINT [DF__lookup_ti__defau__0618D7E0] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_ticket_cause] ADD CONSTRAINT [DF__lookup_ti__enabl__08012052] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_ticket_cause] ADD CONSTRAINT [DF__lookup_ti__level__070CFC19] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_ticket_escalation]
-- =======================================================
Print 'Synchronization Script for: [lookup_ticket_escalation]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_ticket_escalation] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_ticket_resolution]
-- =======================================================
Print 'Synchronization Script for: [lookup_ticket_resolution]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_ticket_resolution] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_ticket_resolution] ADD CONSTRAINT [PK__lookup_ticket_re__5F141958] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_ticket_resolution] ADD CONSTRAINT [DF__lookup_ti__defau__0ADD8CFD] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_ticket_resolution] ADD CONSTRAINT [DF__lookup_ti__enabl__0CC5D56F] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_ticket_resolution] ADD CONSTRAINT [DF__lookup_ti__level__0BD1B136] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_ticket_status]
-- =======================================================
Print 'Synchronization Script for: [lookup_ticket_status]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_ticket_status] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(300) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_ticket_status] ADD CONSTRAINT [PK__lookup_ticket_st__770B9E7A] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a unique constraint for the table
ALTER TABLE [lookup_ticket_status] ADD CONSTRAINT [UQ__lookup_ticket_st__77FFC2B3] UNIQUE ([description])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_ticket_status] ADD CONSTRAINT [DF__lookup_ti__defau__78F3E6EC] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_ticket_status] ADD CONSTRAINT [DF__lookup_ti__enabl__7ADC2F5E] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_ticket_status] ADD CONSTRAINT [DF__lookup_ti__level__79E80B25] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_ticket_task_category]
-- =======================================================
Print 'Synchronization Script for: [lookup_ticket_task_category]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [lookup_ticket_task_category] (
    [code] int NOT NULL IDENTITY(1, 1),
    [description] varchar(255) NOT NULL,
    [default_item] bit NULL,
    [level] int NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [lookup_ticket_task_category] ADD CONSTRAINT [PK__lookup_ticket_ta__60FC61CA] PRIMARY KEY ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_ticket_task_category] ADD CONSTRAINT [DF__lookup_ti__defau__257C74A0] DEFAULT (0) FOR [default_item]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_ticket_task_category] ADD CONSTRAINT [DF__lookup_ti__enabl__2764BD12] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [lookup_ticket_task_category] ADD CONSTRAINT [DF__lookup_ti__level__267098D9] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [lookup_ticketsource]
-- =======================================================
Print 'Synchronization Script for: [lookup_ticketsource]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[lookup_ticketsource] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [message]
-- =======================================================
Print 'Synchronization Script for: [message]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[message] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [message_template]
-- =======================================================
Print 'Synchronization Script for: [message_template]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[message_template] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [module_field_categorylink]
-- =======================================================
Print 'Synchronization Script for: [module_field_categorylink]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[module_field_categorylink] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [netapp_contractexpiration]
-- =======================================================
Print 'Synchronization Script for: [netapp_contractexpiration]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the index
DROP INDEX [netapp_contractexpiration].[IX_netapp_contractexpiration]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the index
DROP INDEX [netapp_contractexpiration].[IX_netapp_contractexpiration_1]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [netapp_contractexpiration_log]
-- =======================================================
Print 'Synchronization Script for: [netapp_contractexpiration_log]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[netapp_contractexpiration_log] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [news]
-- =======================================================
Print 'Synchronization Script for: [news]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[news] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [notification]
-- =======================================================
Print 'Synchronization Script for: [notification]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[notification] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [opportunity_component]
-- =======================================================
Print 'Synchronization Script for: [opportunity_component]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [opportunity_component] ADD [budget] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [opportunity_component] ADD [compelling_event] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [opportunity_component] ADD [competitors] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [opportunity_component] ADD [environment] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [opportunity_component] ADD [trashed_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [opportunity_component_levels]
-- =======================================================
Print 'Synchronization Script for: [opportunity_component_levels]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[opportunity_component_levels] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [opportunity_header]
-- =======================================================
Print 'Synchronization Script for: [opportunity_header]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [opportunity_header] ADD [access_type] int NOT NULL DEFAULT 0
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [opportunity_header] ADD [manager] int NOT NULL DEFAULT 0
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [opportunity_header] ADD [trashed_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [order_address]
-- =======================================================
Print 'Synchronization Script for: [order_address]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[order_address] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [order_entry]
-- =======================================================
Print 'Synchronization Script for: [order_entry]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [order_entry] ADD [submitted] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [order_payment]
-- =======================================================
Print 'Synchronization Script for: [order_payment]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [order_payment] ADD [bank_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [order_payment] ADD [creditcard_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [order_payment] ADD [date_to_process] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [order_payment] ADD [history_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [order_payment] ADD [order_item_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [order_payment] ADD [status_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [order_payment_status]
-- =======================================================
Print 'Synchronization Script for: [order_payment_status]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
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

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [order_payment_status] ADD CONSTRAINT [PK__order_payment_st__3592E0D8] PRIMARY KEY ([payment_status_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [order_payment_status] ADD CONSTRAINT [DF__order_pay__enter__386F4D83] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [order_payment_status] ADD CONSTRAINT [DF__order_pay__modif__3A5795F5] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [order_product]
-- =======================================================
Print 'Synchronization Script for: [order_product]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[order_product] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [order_product_option_boolean]
-- =======================================================
Print 'Synchronization Script for: [order_product_option_boolean]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[order_product_option_boolean] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [order_product_option_float]
-- =======================================================
Print 'Synchronization Script for: [order_product_option_float]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[order_product_option_float] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [order_product_option_integer]
-- =======================================================
Print 'Synchronization Script for: [order_product_option_integer]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[order_product_option_integer] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [order_product_option_text]
-- =======================================================
Print 'Synchronization Script for: [order_product_option_text]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[order_product_option_text] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [order_product_option_timestamp]
-- =======================================================
Print 'Synchronization Script for: [order_product_option_timestamp]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[order_product_option_timestamp] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [order_product_options]
-- =======================================================
Print 'Synchronization Script for: [order_product_options]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[order_product_options] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [order_product_status]
-- =======================================================
Print 'Synchronization Script for: [order_product_status]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[order_product_status] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [organization]
-- =======================================================
Print 'Synchronization Script for: [organization]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [organization] ADD [potential] float NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [organization] ADD [rating] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [organization] ADD [source] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [organization] ADD [trashed_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [organization_address]
-- =======================================================
Print 'Synchronization Script for: [organization_address]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [organization_address] ADD [primary_address] bit NOT NULL CONSTRAINT [DF__organizat__prima__4E53A1AA] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the index
CREATE INDEX [organization_address_postalcode_idx] ON [organization_address] ([postalcode])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [organization_emailaddress]
-- =======================================================
Print 'Synchronization Script for: [organization_emailaddress]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [organization_emailaddress] ADD [primary_email] bit NOT NULL CONSTRAINT [DF__organizat__prima__56E8E7AB] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [organization_phone]
-- =======================================================
Print 'Synchronization Script for: [organization_phone]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [organization_phone] ADD [primary_number] bit NOT NULL CONSTRAINT [DF__organizat__prima__5F7E2DAC] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [package]
-- =======================================================
Print 'Synchronization Script for: [package]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[package] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [package_products_map]
-- =======================================================
Print 'Synchronization Script for: [package_products_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[package_products_map] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [payment_creditcard]
-- =======================================================
Print 'Synchronization Script for: [payment_creditcard]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all foreign keys of the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[payment_creditcard]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all defaults of the table
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
  CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)',N'[payment_creditcard]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [payment_creditcard] DROP CONSTRAINT [PK__payment_creditca__093F5D4E]
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the column
ALTER TABLE [payment_creditcard] DROP COLUMN [payment_id]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [payment_creditcard] ADD [order_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [payment_creditcard] ADD PRIMARY KEY ([creditcard_id])
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [payment_creditcard] ADD DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [payment_creditcard] ADD DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [payment_eft]
-- =======================================================
Print 'Synchronization Script for: [payment_eft]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all foreign keys of the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[payment_eft]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all defaults of the table
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
  CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)',N'[payment_eft]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [payment_eft] DROP CONSTRAINT [PK__payment_eft__0A338187]
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the column
ALTER TABLE [payment_eft] DROP COLUMN [payment_id]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [payment_eft] ADD [order_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [payment_eft] ADD PRIMARY KEY ([bank_id])
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [payment_eft] ADD DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [payment_eft] ADD DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [permission]
-- =======================================================
Print 'Synchronization Script for: [permission]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[permission] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [permission_category]
-- =======================================================
Print 'Synchronization Script for: [permission_category]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [permission_category] ADD [action_plans] bit NOT NULL CONSTRAINT [DF__permissio__actio__29221CFB] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [permission_category] ADD [constant] int NOT NULL DEFAULT 0
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [permission_category] ADD [logos] bit NOT NULL CONSTRAINT [DF__permissio__logos__25518C17] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [permission_category] ADD [webdav] bit NOT NULL CONSTRAINT [DF__permissio__webda__245D67DE] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [process_log]
-- =======================================================
Print 'Synchronization Script for: [process_log]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[process_log] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_catalog]
-- =======================================================
Print 'Synchronization Script for: [product_catalog]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_catalog] ADD [active] bit NOT NULL CONSTRAINT [DF__product_c__activ__216BEC9A] DEFAULT (1)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_catalog] ADD [manufacturer_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_catalog] ADD [trashed_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_catalog_category_map]
-- =======================================================
Print 'Synchronization Script for: [product_catalog_category_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[product_catalog_category_map] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_catalog_pricing]
-- =======================================================
Print 'Synchronization Script for: [product_catalog_pricing]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_catalog_pricing] ADD [cost_amount] float NOT NULL CONSTRAINT [DF__product_c__cost___338A9CD5] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_catalog_pricing] ADD [cost_currency] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_catalog_pricing] ADD [enabled] bit NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [product_catalog_pricing] ADD DEFAULT (0) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_category]
-- =======================================================
Print 'Synchronization Script for: [product_category]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[product_category] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_category_map]
-- =======================================================
Print 'Synchronization Script for: [product_category_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[product_category_map] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_keyword_map]
-- =======================================================
Print 'Synchronization Script for: [product_keyword_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[product_keyword_map] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_option]
-- =======================================================
Print 'Synchronization Script for: [product_option]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the default constraint
--ALTER TABLE [product_option] DROP CONSTRAINT [DF__product_o__enabl__550B8C31]
--GO
EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CNSTR CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(o.name)
     FROM sysobjects o
     INNER JOIN sysconstraints sc ON o.id = sc.constid
     WHERE object_name(o.parent_obj) = ''product_option''
     AND o.xtype = ''D''
     AND o.name LIKE ''DF__product_o__enabl__%''
OPEN CNSTR
FETCH CNSTR INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN
    EXEC sp_executesql @sql
    FETCH CNSTR INTO @sql
END
CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)','[product_option]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option] ADD [has_multiplier] bit NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option] ADD [has_range] bit NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option] ADD [option_name] varchar(300) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [product_option] ADD DEFAULT (0) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [product_option] ADD DEFAULT (0) FOR [has_multiplier]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [product_option] ADD DEFAULT (0) FOR [has_range]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_option_boolean]
-- =======================================================
Print 'Synchronization Script for: [product_option_boolean]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option_boolean] ADD [id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_option_configurator]
-- =======================================================
Print 'Synchronization Script for: [product_option_configurator]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option_configurator] ADD [configurator_name] varchar(300) NOT NULL DEFAULT ''
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_option_float]
-- =======================================================
Print 'Synchronization Script for: [product_option_float]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option_float] ADD [id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_option_integer]
-- =======================================================
Print 'Synchronization Script for: [product_option_integer]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option_integer] ADD [id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_option_map]
-- =======================================================
Print 'Synchronization Script for: [product_option_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping references to the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[product_option_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all foreign keys of the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[product_option_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [product_option_map] DROP CONSTRAINT [PK__product_option_m__14B10FFA]
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the column
ALTER TABLE [product_option_map] DROP COLUMN [value_id]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [product_option_map] ADD PRIMARY KEY ([product_option_id])
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_option_text]
-- =======================================================
Print 'Synchronization Script for: [product_option_text]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option_text] ADD [id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_option_timestamp]
-- =======================================================
Print 'Synchronization Script for: [product_option_timestamp]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option_timestamp] ADD [id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [product_option_values]
-- =======================================================
Print 'Synchronization Script for: [product_option_values]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the index
DROP INDEX [product_option_values].[idx_pr_opt_val]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option_values] ADD [cost_amount] float NOT NULL CONSTRAINT [DF__product_o__cost___75586032] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option_values] ADD [cost_currency] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option_values] ADD [enabled] bit NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option_values] ADD [multiplier] float NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option_values] ADD [range_max] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option_values] ADD [range_min] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [product_option_values] ADD [value] float NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [product_option_values] ADD DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [product_option_values] ADD DEFAULT (1) FOR [multiplier]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [product_option_values] ADD DEFAULT (1) FOR [range_min]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [product_option_values] ADD DEFAULT (1) FOR [range_max]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [product_option_values] ADD DEFAULT (0) FOR [value]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the index
CREATE UNIQUE INDEX [idx_pr_opt_val] ON [product_option_values] ([value_id], [option_id], [result_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_accounts]
-- =======================================================
Print 'Synchronization Script for: [project_accounts]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [project_accounts] (
    [id] int NOT NULL IDENTITY(1, 1),
    [project_id] int NOT NULL,
    [org_id] int NOT NULL,
    [entered] datetime NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [project_accounts] ADD CONSTRAINT [PK__project_accounts__4D7F7902] PRIMARY KEY ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [project_accounts] ADD CONSTRAINT [DF__project_a__enter__505BE5AD] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating an index on the table
CREATE INDEX [proj_acct_org_idx] ON [project_accounts] ([org_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating an index on the table
CREATE INDEX [proj_acct_project_idx] ON [project_accounts] ([project_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_assignments]
-- =======================================================
Print 'Synchronization Script for: [project_assignments]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the index
CREATE INDEX [proj_assign_req_id_idx] ON [project_assignments] ([requirement_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_assignments_folder]
-- =======================================================
Print 'Synchronization Script for: [project_assignments_folder]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[project_assignments_folder] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_assignments_status]
-- =======================================================
Print 'Synchronization Script for: [project_assignments_status]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [project_assignments_status] ADD [percent_complete] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [project_assignments_status] ADD [project_status_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [project_assignments_status] ADD [user_assign_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_files]
-- =======================================================
Print 'Synchronization Script for: [project_files]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [project_files] ADD [default_file] bit NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [project_files] ADD DEFAULT (0) FOR [default_file]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_files_download]
-- =======================================================
Print 'Synchronization Script for: [project_files_download]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[project_files_download] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_files_thumbnail]
-- =======================================================
Print 'Synchronization Script for: [project_files_thumbnail]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[project_files_thumbnail] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_files_version]
-- =======================================================
Print 'Synchronization Script for: [project_files_version]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[project_files_version] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_folders]
-- =======================================================
Print 'Synchronization Script for: [project_folders]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping references to the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[project_folders]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all foreign keys of the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[project_folders]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all defaults of the table
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
  CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)',N'[project_folders]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [project_folders] DROP CONSTRAINT [PK__project_folders__1A69E950]
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the column
ALTER TABLE [project_folders] DROP COLUMN [parent]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [project_folders] ADD PRIMARY KEY ([folder_id])
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_folders] ADD DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_folders] ADD DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_issue_replies]
-- =======================================================
Print 'Synchronization Script for: [project_issue_replies]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Changing the column's definition
ALTER TABLE [project_issue_replies] ALTER COLUMN [subject] varchar(255) NOT NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_issues]
-- =======================================================
Print 'Synchronization Script for: [project_issues]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[project_issues] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_issues_categories]
-- =======================================================
Print 'Synchronization Script for: [project_issues_categories]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [project_issues_categories] ADD [allow_files] bit NOT NULL CONSTRAINT [DF__project_i__allow__573DED66] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_news]
-- =======================================================
Print 'Synchronization Script for: [project_news]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all foreign keys of the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[project_news]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all defaults of the table
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
  CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)',N'[project_news]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [project_news] DROP CONSTRAINT [PK__project_news__1E3A7A34]
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the column
ALTER TABLE [project_news] DROP COLUMN [intro]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [project_news] ADD [classification_id] int NOT NULL DEFAULT 0
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [project_news] ADD [intro] text NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [project_news] ADD [template_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [project_news] ADD PRIMARY KEY ([news_id])
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_news] ADD DEFAULT (0) FOR [allow_replies]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_news] ADD DEFAULT (0) FOR [allow_rating]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_news] ADD DEFAULT (0) FOR [avg_rating]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_news] ADD DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_news] ADD DEFAULT (null) FOR [end_date]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_news] ADD DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_news] ADD DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_news] ADD DEFAULT (10) FOR [priority_id]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_news] ADD DEFAULT (0) FOR [rating_count]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_news] ADD DEFAULT (0) FOR [read_count]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_news] ADD DEFAULT (getdate()) FOR [start_date]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_news] ADD DEFAULT (null) FOR [status]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [project_news] ADD DEFAULT (1) FOR [html]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_news_category]
-- =======================================================
Print 'Synchronization Script for: [project_news_category]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [project_news_category] (
    [category_id] int NOT NULL IDENTITY(1, 1),
    [project_id] int NOT NULL,
    [category_name] varchar(255) NULL,
    [entered] datetime NULL,
    [level] int NOT NULL,
    [enabled] bit NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [project_news_category] ADD CONSTRAINT [PK__project_news_cat__1AF3F935] PRIMARY KEY ([category_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [project_news_category] ADD CONSTRAINT [DF__project_n__enabl__1EC48A19] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [project_news_category] ADD CONSTRAINT [DF__project_n__enter__1CDC41A7] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [project_news_category] ADD CONSTRAINT [DF__project_n__level__1DD065E0] DEFAULT (0) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_permissions]
-- =======================================================
Print 'Synchronization Script for: [project_permissions]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[project_permissions] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_requirements]
-- =======================================================
Print 'Synchronization Script for: [project_requirements]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [project_requirements] ADD [due_date_timezone] varchar(255) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_requirements_map]
-- =======================================================
Print 'Synchronization Script for: [project_requirements_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[project_requirements_map] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_team]
-- =======================================================
Print 'Synchronization Script for: [project_team]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Changing the column's definition
-- Twice renaming the column
EXEC sp_rename N'[project_team].[userLevel]',N'tmp_userlevel','COLUMN'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

EXEC sp_rename N'[project_team].[tmp_userlevel]',N'userlevel','COLUMN'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

ALTER TABLE [project_team] ALTER COLUMN [userlevel] int NOT NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [project_ticket_count]
-- =======================================================
Print 'Synchronization Script for: [project_ticket_count]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[project_ticket_count] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [projects]
-- =======================================================
Print 'Synchronization Script for: [projects]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the foreign key
IF OBJECT_ID(N'[FK__projects__approv__455F344D]') IS NOT NULL
ALTER TABLE [projects] DROP CONSTRAINT [FK__projects__approv__455F344D]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Twice renaming the column
EXEC sp_rename N'[projects].[approvalby]',N'tmp_approvalBy','COLUMN'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

EXEC sp_rename N'[projects].[tmp_approvalBy]',N'approvalBy','COLUMN'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [accounts_enabled] bit NOT NULL CONSTRAINT [DF__projects__accoun__23BE4960] DEFAULT (1)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [accounts_label] varchar(50) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [allows_user_observers] bit NOT NULL CONSTRAINT [DF__projects__allows__20E1DCB5] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [calendar_enabled] bit NOT NULL CONSTRAINT [DF__projects__calend__22CA2527] DEFAULT (1)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [calendar_label] varchar(50) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [description] text NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [level] int NOT NULL CONSTRAINT [DF__projects__level__21D600EE] DEFAULT (10)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [portal_build_news_body] bit NOT NULL CONSTRAINT [DF__projects__portal__1EF99443] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [portal_default] bit NOT NULL CONSTRAINT [DF__projects__portal__1E05700A] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [portal_format] varchar(255) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [portal_header] varchar(255) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [portal_key] varchar(255) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [portal_news_menu] bit NOT NULL CONSTRAINT [DF__projects__portal__1FEDB87C] DEFAULT (0)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [portal_page_type] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [projects] ADD [trashed_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [quote_condition]
-- =======================================================
Print 'Synchronization Script for: [quote_condition]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [quote_condition] (
    [map_id] int NOT NULL IDENTITY(1, 1),
    [quote_id] int NOT NULL,
    [condition_id] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [quote_condition] ADD CONSTRAINT [PK__quote_condition__03E676AB] PRIMARY KEY ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [quote_entry]
-- =======================================================
Print 'Synchronization Script for: [quote_entry]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Changing the column's definition
ALTER TABLE [quote_entry] ALTER COLUMN [org_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [address] text NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [closed] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [delivery_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [email_address] text NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [fax_number] text NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [group_id] int NOT NULL DEFAULT 0
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [logo_file_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [opp_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [phone_number] text NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [show_subtotal] bit NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [show_total] bit NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [submit_action] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [trashed_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_entry] ADD [version] varchar(255) NOT NULL CONSTRAINT [DF__quote_ent__versi__7D39791C] DEFAULT ('0')
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [quote_entry] ADD DEFAULT (1) FOR [show_total]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [quote_entry] ADD DEFAULT (1) FOR [show_subtotal]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [quote_group]
-- =======================================================
Print 'Synchronization Script for: [quote_group]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [quote_group] (
    [group_id] int NOT NULL IDENTITY(1000, 1),
    [unused] char(1) NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [quote_group] ADD CONSTRAINT [PK__quote_group__7A5D0C71] PRIMARY KEY ([group_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [quote_notes]
-- =======================================================
Print 'Synchronization Script for: [quote_notes]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[quote_notes] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [quote_product]
-- =======================================================
Print 'Synchronization Script for: [quote_product]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_product] ADD [comment] varchar(300) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_product] ADD [estimated_delivery] text NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [quote_product_option_boolean]
-- =======================================================
Print 'Synchronization Script for: [quote_product_option_boolean]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_product_option_boolean] ADD [id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [quote_product_option_float]
-- =======================================================
Print 'Synchronization Script for: [quote_product_option_float]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_product_option_float] ADD [id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [quote_product_option_integer]
-- =======================================================
Print 'Synchronization Script for: [quote_product_option_integer]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_product_option_integer] ADD [id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [quote_product_option_text]
-- =======================================================
Print 'Synchronization Script for: [quote_product_option_text]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_product_option_text] ADD [id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [quote_product_option_timestamp]
-- =======================================================
Print 'Synchronization Script for: [quote_product_option_timestamp]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [quote_product_option_timestamp] ADD [id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [quote_product_options]
-- =======================================================
Print 'Synchronization Script for: [quote_product_options]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[quote_product_options] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [quote_remark]
-- =======================================================
Print 'Synchronization Script for: [quote_remark]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [quote_remark] (
    [map_id] int NOT NULL IDENTITY(1, 1),
    [quote_id] int NOT NULL,
    [remark_id] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [quote_remark] ADD CONSTRAINT [PK__quote_remark__17ED6F58] PRIMARY KEY ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [quotelog]
-- =======================================================
Print 'Synchronization Script for: [quotelog]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
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

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [quotelog] ADD CONSTRAINT [PK__quotelog__07B7078F] PRIMARY KEY ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [quotelog] ADD CONSTRAINT [DF__quotelog__entere__0F582957] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [quotelog] ADD CONSTRAINT [DF__quotelog__modifi__114071C9] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [relationship]
-- =======================================================
Print 'Synchronization Script for: [relationship]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
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
    [modifiedby] int NOT NULL,
    [trashed_date] datetime NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [relationship] ADD CONSTRAINT [PK__relationship__10E07F16] PRIMARY KEY ([relationship_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [relationship] ADD CONSTRAINT [DF__relations__enter__12C8C788] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [relationship] ADD CONSTRAINT [DF__relations__modif__13BCEBC1] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [report]
-- =======================================================
Print 'Synchronization Script for: [report]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[report] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [report_criteria]
-- =======================================================
Print 'Synchronization Script for: [report_criteria]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[report_criteria] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [report_criteria_parameter]
-- =======================================================
Print 'Synchronization Script for: [report_criteria_parameter]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[report_criteria_parameter] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [report_queue]
-- =======================================================
Print 'Synchronization Script for: [report_queue]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[report_queue] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [report_queue_criteria]
-- =======================================================
Print 'Synchronization Script for: [report_queue_criteria]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[report_queue_criteria] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [revenue]
-- =======================================================
Print 'Synchronization Script for: [revenue]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[revenue] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [revenue_detail]
-- =======================================================
Print 'Synchronization Script for: [revenue_detail]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[revenue_detail] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [role]
-- =======================================================
Print 'Synchronization Script for: [role]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[role] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [role_permission]
-- =======================================================
Print 'Synchronization Script for: [role_permission]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[role_permission] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [saved_criteriaelement]
-- =======================================================
Print 'Synchronization Script for: [saved_criteriaelement]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[saved_criteriaelement] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [saved_criterialist]
-- =======================================================
Print 'Synchronization Script for: [saved_criterialist]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[saved_criterialist] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [scheduled_recipient]
-- =======================================================
Print 'Synchronization Script for: [scheduled_recipient]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[scheduled_recipient] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [search_fields]
-- =======================================================
Print 'Synchronization Script for: [search_fields]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[search_fields] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [service_contract]
-- =======================================================
Print 'Synchronization Script for: [service_contract]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping references to the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[service_contract]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all foreign keys of the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[service_contract]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all defaults of the table
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
  CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)',N'[service_contract]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [service_contract] DROP CONSTRAINT [PK__service_contract__324172E1]
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the column
ALTER TABLE [service_contract] DROP COLUMN [total_hours_purchased]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [service_contract] ADD [trashed_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [service_contract] ADD PRIMARY KEY ([contract_id])
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [service_contract] ADD DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [service_contract] ADD DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [service_contract] ADD DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [service_contract_hours]
-- =======================================================
Print 'Synchronization Script for: [service_contract_hours]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[service_contract_hours] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [service_contract_products]
-- =======================================================
Print 'Synchronization Script for: [service_contract_products]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[service_contract_products] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [sites]
-- =======================================================
Print 'Synchronization Script for: [sites]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[sites] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [state]
-- =======================================================
Print 'Synchronization Script for: [state]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[state] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [step_action_map]
-- =======================================================
Print 'Synchronization Script for: [step_action_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [step_action_map] (
    [map_id] int NOT NULL IDENTITY(1, 1),
    [constant_id] int NOT NULL,
    [action_id] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [step_action_map] ADD CONSTRAINT [PK__step_action_map__2C538F61] PRIMARY KEY ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [survey]
-- =======================================================
Print 'Synchronization Script for: [survey]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[survey] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [survey_items]
-- =======================================================
Print 'Synchronization Script for: [survey_items]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[survey_items] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [survey_questions]
-- =======================================================
Print 'Synchronization Script for: [survey_questions]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[survey_questions] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [sync_client]
-- =======================================================
Print 'Synchronization Script for: [sync_client]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[sync_client] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [sync_conflict_log]
-- =======================================================
Print 'Synchronization Script for: [sync_conflict_log]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[sync_conflict_log] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [sync_log]
-- =======================================================
Print 'Synchronization Script for: [sync_log]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[sync_log] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [sync_map]
-- =======================================================
Print 'Synchronization Script for: [sync_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[sync_map] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [sync_system]
-- =======================================================
Print 'Synchronization Script for: [sync_system]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[sync_system] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [sync_table]
-- =======================================================
Print 'Synchronization Script for: [sync_table]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[sync_table] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [sync_transaction_log]
-- =======================================================
Print 'Synchronization Script for: [sync_transaction_log]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[sync_transaction_log] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [task]
-- =======================================================
Print 'Synchronization Script for: [task]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Changing the column's definition
ALTER TABLE [task] ALTER COLUMN [description] varchar(255) NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [task] ADD [ticket_task_category_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [task] ADD [trashed_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [taskcategory_project]
-- =======================================================
Print 'Synchronization Script for: [taskcategory_project]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[taskcategory_project] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [taskcategorylink_news]
-- =======================================================
Print 'Synchronization Script for: [taskcategorylink_news]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [taskcategorylink_news] (
    [news_id] int NOT NULL,
    [category_id] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [tasklink_contact]
-- =======================================================
Print 'Synchronization Script for: [tasklink_contact]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[tasklink_contact] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [tasklink_project]
-- =======================================================
Print 'Synchronization Script for: [tasklink_project]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[tasklink_project] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [tasklink_ticket]
-- =======================================================
Print 'Synchronization Script for: [tasklink_ticket]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [tasklink_ticket] ADD [category_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticket]
-- =======================================================
Print 'Synchronization Script for: [ticket]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [ticket] ADD [cause_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [ticket] ADD [defect_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [ticket] ADD [resolution_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [ticket] ADD [resolvable] bit NOT NULL CONSTRAINT [DF__ticket__resolvab__32375140] DEFAULT (1)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [ticket] ADD [resolvedby] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [ticket] ADD [resolvedby_department_code] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [ticket] ADD [status_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [ticket] ADD [trashed_date] datetime NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Adding the column
ALTER TABLE [ticket] ADD [user_group_id] int NULL
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticket_activity_item]
-- =======================================================
Print 'Synchronization Script for: [ticket_activity_item]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all foreign keys of the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[ticket_activity_item]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [ticket_activity_item] DROP CONSTRAINT [PK__ticket_activity___408F9238]
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the column
ALTER TABLE [ticket_activity_item] DROP COLUMN [labor_time]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the column
ALTER TABLE [ticket_activity_item] DROP COLUMN [travel_time]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [ticket_activity_item] ADD PRIMARY KEY ([item_id])
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticket_category]
-- =======================================================
Print 'Synchronization Script for: [ticket_category]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [ticket_category] ADD DEFAULT (0) FOR [parent_cat_code]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticket_category_draft]
-- =======================================================
Print 'Synchronization Script for: [ticket_category_draft]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the default constraint
ALTER TABLE [ticket_category_draft] ADD DEFAULT (0) FOR [parent_cat_code]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticket_category_draft_plan_map]
-- =======================================================
Print 'Synchronization Script for: [ticket_category_draft_plan_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [ticket_category_draft_plan_map] (
    [map_id] int NOT NULL IDENTITY(1, 1),
    [plan_id] int NOT NULL,
    [category_id] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [ticket_category_draft_plan_map] ADD CONSTRAINT [PK__ticket_category___39AD8A7F] PRIMARY KEY ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticket_category_plan_map]
-- =======================================================
Print 'Synchronization Script for: [ticket_category_plan_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [ticket_category_plan_map] (
    [map_id] int NOT NULL IDENTITY(1, 1),
    [plan_id] int NOT NULL,
    [category_id] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [ticket_category_plan_map] ADD CONSTRAINT [PK__ticket_category___3AA1AEB8] PRIMARY KEY ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticket_csstm_form]
-- =======================================================
Print 'Synchronization Script for: [ticket_csstm_form]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping references to the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[ticket_csstm_form]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all foreign keys of the table
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
  CLOSE CRFK DEALLOCATE CRFK',N'@table nvarchar(261)',N'[ticket_csstm_form]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

  -- Dropping all defaults of the table
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
  CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)',N'[ticket_csstm_form]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [ticket_csstm_form] DROP CONSTRAINT [PK__ticket_csstm_for__436BFEE3]
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Dropping the column
ALTER TABLE [ticket_csstm_form] DROP COLUMN [form_type]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--ALTER TABLE [ticket_csstm_form] ADD PRIMARY KEY ([form_id])
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [ticket_csstm_form] ADD DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [ticket_csstm_form] ADD DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [ticket_csstm_form] ADD DEFAULT (0) FOR [follow_up_required]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [ticket_csstm_form] ADD DEFAULT (1) FOR [labor_towards_sc]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [ticket_csstm_form] ADD DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Create a default constraint for the table
ALTER TABLE [ticket_csstm_form] ADD DEFAULT (1) FOR [travel_towards_sc]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticket_defect]
-- =======================================================
Print 'Synchronization Script for: [ticket_defect]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [ticket_defect] (
    [defect_id] int NOT NULL IDENTITY(1, 1),
    [title] varchar(255) NOT NULL,
    [description] text NULL,
    [start_date] datetime NOT NULL,
    [end_date] datetime NULL,
    [enabled] bit NOT NULL,
    [trashed_date] datetime NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [ticket_defect] ADD CONSTRAINT [PK__ticket_defect__3C89F72A] PRIMARY KEY ([defect_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [ticket_defect] ADD CONSTRAINT [DF__ticket_de__enabl__10966653] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [ticket_defect] ADD CONSTRAINT [DF__ticket_de__start__0FA2421A] DEFAULT (getdate()) FOR [start_date]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticket_level]
-- =======================================================
Print 'Synchronization Script for: [ticket_level]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[ticket_level] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticket_priority]
-- =======================================================
Print 'Synchronization Script for: [ticket_priority]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[ticket_priority] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticket_severity]
-- =======================================================
Print 'Synchronization Script for: [ticket_severity]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[ticket_severity] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticket_sun_form]
-- =======================================================
Print 'Synchronization Script for: [ticket_sun_form]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[ticket_sun_form] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticketlink_project]
-- =======================================================
Print 'Synchronization Script for: [ticketlink_project]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[ticketlink_project] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [ticketlog]
-- =======================================================
Print 'Synchronization Script for: [ticketlog]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[ticketlog] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [trouble_asset_replacement]
-- =======================================================
Print 'Synchronization Script for: [trouble_asset_replacement]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[trouble_asset_replacement] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [usage_log]
-- =======================================================
Print 'Synchronization Script for: [usage_log]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[usage_log] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [user_group]
-- =======================================================
Print 'Synchronization Script for: [user_group]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [user_group] (
    [group_id] int NOT NULL IDENTITY(1, 1),
    [group_name] varchar(255) NOT NULL,
    [description] text NULL,
    [enabled] bit NOT NULL,
    [entered] datetime NULL,
    [enteredby] int NOT NULL,
    [modified] datetime NULL,
    [modifiedby] int NOT NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [user_group] ADD CONSTRAINT [PK__user_group__442B18F2] PRIMARY KEY ([group_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [user_group] ADD CONSTRAINT [DF__user_grou__enabl__1A69E950] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [user_group] ADD CONSTRAINT [DF__user_grou__enter__1B5E0D89] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [user_group] ADD CONSTRAINT [DF__user_grou__modif__1D4655FB] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [user_group_map]
-- =======================================================
Print 'Synchronization Script for: [user_group_map]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING OFF
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
CREATE TABLE [user_group_map] (
    [group_map_id] int NOT NULL IDENTITY(1, 1),
    [user_id] int NOT NULL,
    [group_id] int NOT NULL,
    [level] int NOT NULL,
    [enabled] bit NOT NULL,
    [entered] datetime NULL
)
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [user_group_map] ADD CONSTRAINT [PK__user_group_map__451F3D2B] PRIMARY KEY ([group_map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [user_group_map] ADD CONSTRAINT [DF__user_grou__enabl__23F3538A] DEFAULT (1) FOR [enabled]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [user_group_map] ADD CONSTRAINT [DF__user_grou__enter__24E777C3] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [user_group_map] ADD CONSTRAINT [DF__user_grou__level__22FF2F51] DEFAULT (10) FOR [level]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [viewpoint]
-- =======================================================
Print 'Synchronization Script for: [viewpoint]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[viewpoint] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [viewpoint_permission]
-- =======================================================
Print 'Synchronization Script for: [viewpoint_permission]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

--   Synchronization skipped for:[viewpoint_permission] because objects are identical
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- =======================================================
-- Synchronization Script for: [webdav]
-- =======================================================
Print 'Synchronization Script for: [webdav]'
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Ansi settings for the object
SET ANSI_PADDING ON
SET ANSI_NULLS ON
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the table's body
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

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the primary key for the table
ALTER TABLE [webdav] ADD CONSTRAINT [PK__webdav__2F9A1060] PRIMARY KEY ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [webdav] ADD CONSTRAINT [DF__webdav__entered__318258D2] DEFAULT (getdate()) FOR [entered]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a default constraint for the table
ALTER TABLE [webdav] ADD CONSTRAINT [DF__webdav__modified__336AA144] DEFAULT (getdate()) FOR [modified]
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_item_work]') IS NOT NULL AND OBJECT_ID(N'[action_step]') IS NOT NULL AND OBJECT_ID(N'[FK__action_it__actio__14BBFCF2]') IS NULL
ALTER TABLE [action_item_work] ADD CONSTRAINT [FK__action_it__actio__14BBFCF2] FOREIGN KEY ([action_step_id]) REFERENCES [action_step] ([step_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_item_work]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__action_it__enter__15B0212B]') IS NULL
ALTER TABLE [action_item_work] ADD CONSTRAINT [FK__action_it__enter__15B0212B] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_item_work]') IS NOT NULL AND OBJECT_ID(N'[action_plan_constants]') IS NOT NULL AND OBJECT_ID(N'[FK__action_it__link___16A44564]') IS NULL
ALTER TABLE [action_item_work] ADD CONSTRAINT [FK__action_it__link___16A44564] FOREIGN KEY ([link_module_id]) REFERENCES [action_plan_constants] ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_item_work]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__action_it__modif__1798699D]') IS NULL
ALTER TABLE [action_item_work] ADD CONSTRAINT [FK__action_it__modif__1798699D] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_item_work]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__action_it__owner__188C8DD6]') IS NULL
ALTER TABLE [action_item_work] ADD CONSTRAINT [FK__action_it__owner__188C8DD6] FOREIGN KEY ([owner]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_item_work]') IS NOT NULL AND OBJECT_ID(N'[action_phase_work]') IS NOT NULL AND OBJECT_ID(N'[FK__action_it__phase__1980B20F]') IS NULL
ALTER TABLE [action_item_work] ADD CONSTRAINT [FK__action_it__phase__1980B20F] FOREIGN KEY ([phase_work_id]) REFERENCES [action_phase_work] ([phase_work_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_item_work_notes]') IS NOT NULL AND OBJECT_ID(N'[action_item_work]') IS NOT NULL AND OBJECT_ID(N'[FK__action_it__item___1A74D648]') IS NULL
ALTER TABLE [action_item_work_notes] ADD CONSTRAINT [FK__action_it__item___1A74D648] FOREIGN KEY ([item_work_id]) REFERENCES [action_item_work] ([item_work_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_item_work_notes]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__action_it__submi__1B68FA81]') IS NULL
ALTER TABLE [action_item_work_notes] ADD CONSTRAINT [FK__action_it__submi__1B68FA81] FOREIGN KEY ([submittedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_item_work_selection]') IS NOT NULL AND OBJECT_ID(N'[action_item_work]') IS NOT NULL AND OBJECT_ID(N'[FK__action_it__item___1C5D1EBA]') IS NULL
ALTER TABLE [action_item_work_selection] ADD CONSTRAINT [FK__action_it__item___1C5D1EBA] FOREIGN KEY ([item_work_id]) REFERENCES [action_item_work] ([item_work_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_item_work_selection]') IS NOT NULL AND OBJECT_ID(N'[action_step_lookup]') IS NOT NULL AND OBJECT_ID(N'[FK__action_it__selec__1D5142F3]') IS NULL
ALTER TABLE [action_item_work_selection] ADD CONSTRAINT [FK__action_it__selec__1D5142F3] FOREIGN KEY ([selection]) REFERENCES [action_step_lookup] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_phase]') IS NOT NULL AND OBJECT_ID(N'[action_phase]') IS NOT NULL AND OBJECT_ID(N'[FK__action_ph__paren__2121D3D7]') IS NULL
ALTER TABLE [action_phase] ADD CONSTRAINT [FK__action_ph__paren__2121D3D7] FOREIGN KEY ([parent_id]) REFERENCES [action_phase] ([phase_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_phase]') IS NOT NULL AND OBJECT_ID(N'[action_plan]') IS NOT NULL AND OBJECT_ID(N'[FK__action_ph__plan___2215F810]') IS NULL
ALTER TABLE [action_phase] ADD CONSTRAINT [FK__action_ph__plan___2215F810] FOREIGN KEY ([plan_id]) REFERENCES [action_plan] ([plan_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_phase_work]') IS NOT NULL AND OBJECT_ID(N'[action_phase]') IS NOT NULL AND OBJECT_ID(N'[FK__action_ph__actio__230A1C49]') IS NULL
ALTER TABLE [action_phase_work] ADD CONSTRAINT [FK__action_ph__actio__230A1C49] FOREIGN KEY ([action_phase_id]) REFERENCES [action_phase] ([phase_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_phase_work]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__action_ph__enter__23FE4082]') IS NULL
ALTER TABLE [action_phase_work] ADD CONSTRAINT [FK__action_ph__enter__23FE4082] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_phase_work]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__action_ph__modif__24F264BB]') IS NULL
ALTER TABLE [action_phase_work] ADD CONSTRAINT [FK__action_ph__modif__24F264BB] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_phase_work]') IS NOT NULL AND OBJECT_ID(N'[action_plan_work]') IS NOT NULL AND OBJECT_ID(N'[FK__action_ph__plan___25E688F4]') IS NULL
ALTER TABLE [action_phase_work] ADD CONSTRAINT [FK__action_ph__plan___25E688F4] FOREIGN KEY ([plan_work_id]) REFERENCES [action_plan_work] ([plan_work_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan]') IS NOT NULL AND OBJECT_ID(N'[action_plan_category]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__cat_c__26DAAD2D]') IS NULL
ALTER TABLE [action_plan] ADD CONSTRAINT [FK__action_pl__cat_c__26DAAD2D] FOREIGN KEY ([cat_code]) REFERENCES [action_plan_category] ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__enter__27CED166]') IS NULL
ALTER TABLE [action_plan] ADD CONSTRAINT [FK__action_pl__enter__27CED166] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan]') IS NOT NULL AND OBJECT_ID(N'[action_plan_constants]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__link___28C2F59F]') IS NULL
ALTER TABLE [action_plan] ADD CONSTRAINT [FK__action_pl__link___28C2F59F] FOREIGN KEY ([link_object_id]) REFERENCES [action_plan_constants] ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__modif__29B719D8]') IS NULL
ALTER TABLE [action_plan] ADD CONSTRAINT [FK__action_pl__modif__29B719D8] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan]') IS NOT NULL AND OBJECT_ID(N'[action_plan_category]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__subca__2AAB3E11]') IS NULL
ALTER TABLE [action_plan] ADD CONSTRAINT [FK__action_pl__subca__2AAB3E11] FOREIGN KEY ([subcat_code1]) REFERENCES [action_plan_category] ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan]') IS NOT NULL AND OBJECT_ID(N'[action_plan_category]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__subca__2B9F624A]') IS NULL
ALTER TABLE [action_plan] ADD CONSTRAINT [FK__action_pl__subca__2B9F624A] FOREIGN KEY ([subcat_code2]) REFERENCES [action_plan_category] ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan]') IS NOT NULL AND OBJECT_ID(N'[action_plan_category]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__subca__2C938683]') IS NULL
ALTER TABLE [action_plan] ADD CONSTRAINT [FK__action_pl__subca__2C938683] FOREIGN KEY ([subcat_code3]) REFERENCES [action_plan_category] ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan_editor_lookup]') IS NOT NULL AND OBJECT_ID(N'[action_plan_constants]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__const__2D87AABC]') IS NULL
ALTER TABLE [action_plan_editor_lookup] ADD CONSTRAINT [FK__action_pl__const__2D87AABC] FOREIGN KEY ([constant_id]) REFERENCES [action_plan_constants] ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan_editor_lookup]') IS NOT NULL AND OBJECT_ID(N'[permission_category]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__modul__2E7BCEF5]') IS NULL
ALTER TABLE [action_plan_editor_lookup] ADD CONSTRAINT [FK__action_pl__modul__2E7BCEF5] FOREIGN KEY ([module_id]) REFERENCES [permission_category] ([category_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan_work]') IS NOT NULL AND OBJECT_ID(N'[action_plan]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__actio__2F6FF32E]') IS NULL
ALTER TABLE [action_plan_work] ADD CONSTRAINT [FK__action_pl__actio__2F6FF32E] FOREIGN KEY ([action_plan_id]) REFERENCES [action_plan] ([plan_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan_work]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__assig__30641767]') IS NULL
ALTER TABLE [action_plan_work] ADD CONSTRAINT [FK__action_pl__assig__30641767] FOREIGN KEY ([assignedTo]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan_work]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__enter__31583BA0]') IS NULL
ALTER TABLE [action_plan_work] ADD CONSTRAINT [FK__action_pl__enter__31583BA0] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan_work]') IS NOT NULL AND OBJECT_ID(N'[action_plan_constants]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__link___324C5FD9]') IS NULL
ALTER TABLE [action_plan_work] ADD CONSTRAINT [FK__action_pl__link___324C5FD9] FOREIGN KEY ([link_module_id]) REFERENCES [action_plan_constants] ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan_work]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__modif__33408412]') IS NULL
ALTER TABLE [action_plan_work] ADD CONSTRAINT [FK__action_pl__modif__33408412] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan_work_notes]') IS NOT NULL AND OBJECT_ID(N'[action_plan_work]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__plan___3434A84B]') IS NULL
ALTER TABLE [action_plan_work_notes] ADD CONSTRAINT [FK__action_pl__plan___3434A84B] FOREIGN KEY ([plan_work_id]) REFERENCES [action_plan_work] ([plan_work_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_plan_work_notes]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__action_pl__submi__3528CC84]') IS NULL
ALTER TABLE [action_plan_work_notes] ADD CONSTRAINT [FK__action_pl__submi__3528CC84] FOREIGN KEY ([submittedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_step]') IS NOT NULL AND OBJECT_ID(N'[lookup_step_actions]') IS NOT NULL AND OBJECT_ID(N'[FK__action_st__actio__361CF0BD]') IS NULL
ALTER TABLE [action_step] ADD CONSTRAINT [FK__action_st__actio__361CF0BD] FOREIGN KEY ([action_id]) REFERENCES [lookup_step_actions] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_step]') IS NOT NULL AND OBJECT_ID(N'[custom_field_category]') IS NOT NULL AND OBJECT_ID(N'[FK__action_st__categ__371114F6]') IS NULL
ALTER TABLE [action_step] ADD CONSTRAINT [FK__action_st__categ__371114F6] FOREIGN KEY ([category_id]) REFERENCES [custom_field_category] ([category_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_step]') IS NOT NULL AND OBJECT_ID(N'[lookup_department]') IS NOT NULL AND OBJECT_ID(N'[FK__action_st__depar__3805392F]') IS NULL
ALTER TABLE [action_step] ADD CONSTRAINT [FK__action_st__depar__3805392F] FOREIGN KEY ([department_id]) REFERENCES [lookup_department] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_step]') IS NOT NULL AND OBJECT_ID(N'[lookup_duration_type]') IS NOT NULL AND OBJECT_ID(N'[FK__action_st__durat__38F95D68]') IS NULL
ALTER TABLE [action_step] ADD CONSTRAINT [FK__action_st__durat__38F95D68] FOREIGN KEY ([duration_type_id]) REFERENCES [lookup_duration_type] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_step]') IS NOT NULL AND OBJECT_ID(N'[custom_field_info]') IS NOT NULL AND OBJECT_ID(N'[FK__action_st__field__39ED81A1]') IS NULL
ALTER TABLE [action_step] ADD CONSTRAINT [FK__action_st__field__39ED81A1] FOREIGN KEY ([field_id]) REFERENCES [custom_field_info] ([field_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_step]') IS NOT NULL AND OBJECT_ID(N'[user_group]') IS NOT NULL AND OBJECT_ID(N'[FK__action_st__group__3AE1A5DA]') IS NULL
ALTER TABLE [action_step] ADD CONSTRAINT [FK__action_st__group__3AE1A5DA] FOREIGN KEY ([group_id]) REFERENCES [user_group] ([group_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_step]') IS NOT NULL AND OBJECT_ID(N'[action_step]') IS NOT NULL AND OBJECT_ID(N'[FK__action_st__paren__3BD5CA13]') IS NULL
ALTER TABLE [action_step] ADD CONSTRAINT [FK__action_st__paren__3BD5CA13] FOREIGN KEY ([parent_id]) REFERENCES [action_step] ([step_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_step]') IS NOT NULL AND OBJECT_ID(N'[action_phase]') IS NOT NULL AND OBJECT_ID(N'[FK__action_st__phase__3CC9EE4C]') IS NULL
ALTER TABLE [action_step] ADD CONSTRAINT [FK__action_st__phase__3CC9EE4C] FOREIGN KEY ([phase_id]) REFERENCES [action_phase] ([phase_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_step]') IS NOT NULL AND OBJECT_ID(N'[role]') IS NOT NULL AND OBJECT_ID(N'[FK__action_st__role___3DBE1285]') IS NULL
ALTER TABLE [action_step] ADD CONSTRAINT [FK__action_st__role___3DBE1285] FOREIGN KEY ([role_id]) REFERENCES [role] ([role_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[action_step_lookup]') IS NOT NULL AND OBJECT_ID(N'[action_step]') IS NOT NULL AND OBJECT_ID(N'[FK__action_st__step___3EB236BE]') IS NULL
ALTER TABLE [action_step_lookup] ADD CONSTRAINT [FK__action_st__step___3EB236BE] FOREIGN KEY ([step_id]) REFERENCES [action_step] ([step_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[asset]') IS NOT NULL AND OBJECT_ID(N'[asset]') IS NOT NULL
ALTER TABLE [asset] ADD FOREIGN KEY ([parent_id]) REFERENCES [asset] ([asset_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[asset_materials_map]') IS NOT NULL AND OBJECT_ID(N'[asset]') IS NOT NULL AND OBJECT_ID(N'[FK__asset_mat__asset__5C4299A5]') IS NULL
ALTER TABLE [asset_materials_map] ADD CONSTRAINT [FK__asset_mat__asset__5C4299A5] FOREIGN KEY ([asset_id]) REFERENCES [asset] ([asset_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[asset_materials_map]') IS NOT NULL AND OBJECT_ID(N'[lookup_asset_materials]') IS NOT NULL AND OBJECT_ID(N'[FK__asset_mate__code__5D36BDDE]') IS NULL
ALTER TABLE [asset_materials_map] ADD CONSTRAINT [FK__asset_mate__code__5D36BDDE] FOREIGN KEY ([code]) REFERENCES [lookup_asset_materials] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[lookup_access_types]') IS NOT NULL AND OBJECT_ID(N'[FK__contact__access___0BF1ACC7]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__access___0BF1ACC7] FOREIGN KEY ([access_type]) REFERENCES [lookup_access_types] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[FK__contact__assista__0CE5D100]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__assista__0CE5D100] FOREIGN KEY ([assistant]) REFERENCES [contact] ([contact_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[lookup_department]') IS NOT NULL AND OBJECT_ID(N'[FK__contact__departm__0DD9F539]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__departm__0DD9F539] FOREIGN KEY ([department]) REFERENCES [lookup_department] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__contact__entered__0ECE1972]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__entered__0ECE1972] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[lookup_industry]') IS NOT NULL AND OBJECT_ID(N'[FK__contact__industr__0FC23DAB]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__industr__0FC23DAB] FOREIGN KEY ([industry_temp_code]) REFERENCES [lookup_industry] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__contact__modifie__10B661E4]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__modifie__10B661E4] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[organization]') IS NOT NULL AND OBJECT_ID(N'[FK__contact__org_id__11AA861D]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__org_id__11AA861D] FOREIGN KEY ([org_id]) REFERENCES [organization] ([org_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__contact__owner__129EAA56]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__owner__129EAA56] FOREIGN KEY ([owner]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[lookup_contact_rating]') IS NOT NULL AND OBJECT_ID(N'[FK__contact__rating__07C12930]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__rating__07C12930] FOREIGN KEY ([rating]) REFERENCES [lookup_contact_rating] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[lookup_contact_source]') IS NOT NULL AND OBJECT_ID(N'[FK__contact__source__06CD04F7]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__source__06CD04F7] FOREIGN KEY ([source]) REFERENCES [lookup_contact_source] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[FK__contact__super__157B1701]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__super__157B1701] FOREIGN KEY ([super]) REFERENCES [contact] ([contact_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__contact__user_id__166F3B3A]') IS NULL
ALTER TABLE [contact] ADD CONSTRAINT [FK__contact__user_id__166F3B3A] FOREIGN KEY ([user_id]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact_imaddress]') IS NOT NULL AND OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[FK__contact_i__conta__7C1A6C5A]') IS NULL
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [FK__contact_i__conta__7C1A6C5A] FOREIGN KEY ([contact_id]) REFERENCES [contact] ([contact_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact_imaddress]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__contact_i__enter__7FEAFD3E]') IS NULL
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [FK__contact_i__enter__7FEAFD3E] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact_imaddress]') IS NOT NULL AND OBJECT_ID(N'[lookup_im_types]') IS NOT NULL AND OBJECT_ID(N'[FK__contact_i__imadd__7D0E9093]') IS NULL
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [FK__contact_i__imadd__7D0E9093] FOREIGN KEY ([imaddress_type]) REFERENCES [lookup_im_types] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact_imaddress]') IS NOT NULL AND OBJECT_ID(N'[lookup_im_services]') IS NOT NULL AND OBJECT_ID(N'[FK__contact_i__imadd__7E02B4CC]') IS NULL
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [FK__contact_i__imadd__7E02B4CC] FOREIGN KEY ([imaddress_service]) REFERENCES [lookup_im_services] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact_imaddress]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__contact_i__modif__01D345B0]') IS NULL
ALTER TABLE [contact_imaddress] ADD CONSTRAINT [FK__contact_i__modif__01D345B0] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact_lead_read_map]') IS NOT NULL AND OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[FK__contact_l__conta__0F624AF8]') IS NULL
ALTER TABLE [contact_lead_read_map] ADD CONSTRAINT [FK__contact_l__conta__0F624AF8] FOREIGN KEY ([contact_id]) REFERENCES [contact] ([contact_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact_lead_read_map]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__contact_l__user___0E6E26BF]') IS NULL
ALTER TABLE [contact_lead_read_map] ADD CONSTRAINT [FK__contact_l__user___0E6E26BF] FOREIGN KEY ([user_id]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact_lead_skipped_map]') IS NOT NULL AND OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[FK__contact_l__conta__0B91BA14]') IS NULL
ALTER TABLE [contact_lead_skipped_map] ADD CONSTRAINT [FK__contact_l__conta__0B91BA14] FOREIGN KEY ([contact_id]) REFERENCES [contact] ([contact_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact_lead_skipped_map]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__contact_l__user___0A9D95DB]') IS NULL
ALTER TABLE [contact_lead_skipped_map] ADD CONSTRAINT [FK__contact_l__user___0A9D95DB] FOREIGN KEY ([user_id]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact_textmessageaddress]') IS NOT NULL AND OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[FK__contact_t__conta__05A3D694]') IS NULL
ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [FK__contact_t__conta__05A3D694] FOREIGN KEY ([contact_id]) REFERENCES [contact] ([contact_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact_textmessageaddress]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__contact_t__enter__0880433F]') IS NULL
ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [FK__contact_t__enter__0880433F] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact_textmessageaddress]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__contact_t__modif__0A688BB1]') IS NULL
ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [FK__contact_t__modif__0A688BB1] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[contact_textmessageaddress]') IS NOT NULL AND OBJECT_ID(N'[lookup_im_types]') IS NOT NULL AND OBJECT_ID(N'[FK__contact_t__textm__0697FACD]') IS NULL
ALTER TABLE [contact_textmessageaddress] ADD CONSTRAINT [FK__contact_t__textm__0697FACD] FOREIGN KEY ([textmessageaddress_type]) REFERENCES [lookup_im_types] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[customer_product_history]') IS NOT NULL AND OBJECT_ID(N'[order_product]') IS NOT NULL
ALTER TABLE [customer_product_history] ADD FOREIGN KEY ([order_item_id]) REFERENCES [order_product] ([item_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__document___appro__1451E89E]') IS NULL
ALTER TABLE [document_store] ADD CONSTRAINT [FK__document___appro__1451E89E] FOREIGN KEY ([approvalBy]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__document___enter__163A3110]') IS NULL
ALTER TABLE [document_store] ADD CONSTRAINT [FK__document___enter__163A3110] FOREIGN KEY ([enteredBy]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__document___modif__18227982]') IS NULL
ALTER TABLE [document_store] ADD CONSTRAINT [FK__document___modif__18227982] FOREIGN KEY ([modifiedBy]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_department_member]') IS NOT NULL AND OBJECT_ID(N'[document_store]') IS NOT NULL AND OBJECT_ID(N'[FK__document___docum__2E11BAA1]') IS NULL
ALTER TABLE [document_store_department_member] ADD CONSTRAINT [FK__document___docum__2E11BAA1] FOREIGN KEY ([document_store_id]) REFERENCES [document_store] ([document_store_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_department_member]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__document___enter__31E24B85]') IS NULL
ALTER TABLE [document_store_department_member] ADD CONSTRAINT [FK__document___enter__31E24B85] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_department_member]') IS NOT NULL AND OBJECT_ID(N'[lookup_department]') IS NOT NULL AND OBJECT_ID(N'[FK__document___item___2F05DEDA]') IS NULL
ALTER TABLE [document_store_department_member] ADD CONSTRAINT [FK__document___item___2F05DEDA] FOREIGN KEY ([item_id]) REFERENCES [lookup_department] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_department_member]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__document___modif__33CA93F7]') IS NULL
ALTER TABLE [document_store_department_member] ADD CONSTRAINT [FK__document___modif__33CA93F7] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_department_member]') IS NOT NULL AND OBJECT_ID(N'[lookup_document_store_role]') IS NOT NULL AND OBJECT_ID(N'[FK__document___userl__2FFA0313]') IS NULL
ALTER TABLE [document_store_department_member] ADD CONSTRAINT [FK__document___userl__2FFA0313] FOREIGN KEY ([userlevel]) REFERENCES [lookup_document_store_role] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_permissions]') IS NOT NULL AND OBJECT_ID(N'[document_store]') IS NOT NULL AND OBJECT_ID(N'[FK__document___docum__1AFEE62D]') IS NULL
ALTER TABLE [document_store_permissions] ADD CONSTRAINT [FK__document___docum__1AFEE62D] FOREIGN KEY ([document_store_id]) REFERENCES [document_store] ([document_store_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_permissions]') IS NOT NULL AND OBJECT_ID(N'[lookup_document_store_permission]') IS NOT NULL AND OBJECT_ID(N'[FK__document___permi__1BF30A66]') IS NULL
ALTER TABLE [document_store_permissions] ADD CONSTRAINT [FK__document___permi__1BF30A66] FOREIGN KEY ([permission_id]) REFERENCES [lookup_document_store_permission] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_permissions]') IS NOT NULL AND OBJECT_ID(N'[lookup_document_store_role]') IS NOT NULL AND OBJECT_ID(N'[FK__document___userl__1CE72E9F]') IS NULL
ALTER TABLE [document_store_permissions] ADD CONSTRAINT [FK__document___userl__1CE72E9F] FOREIGN KEY ([userlevel]) REFERENCES [lookup_document_store_role] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_role_member]') IS NOT NULL AND OBJECT_ID(N'[document_store]') IS NOT NULL AND OBJECT_ID(N'[FK__document___docum__267098D9]') IS NULL
ALTER TABLE [document_store_role_member] ADD CONSTRAINT [FK__document___docum__267098D9] FOREIGN KEY ([document_store_id]) REFERENCES [document_store] ([document_store_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_role_member]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__document___enter__2A4129BD]') IS NULL
ALTER TABLE [document_store_role_member] ADD CONSTRAINT [FK__document___enter__2A4129BD] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_role_member]') IS NOT NULL AND OBJECT_ID(N'[role]') IS NOT NULL AND OBJECT_ID(N'[FK__document___item___2764BD12]') IS NULL
ALTER TABLE [document_store_role_member] ADD CONSTRAINT [FK__document___item___2764BD12] FOREIGN KEY ([item_id]) REFERENCES [role] ([role_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_role_member]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__document___modif__2C29722F]') IS NULL
ALTER TABLE [document_store_role_member] ADD CONSTRAINT [FK__document___modif__2C29722F] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_role_member]') IS NOT NULL AND OBJECT_ID(N'[lookup_document_store_role]') IS NOT NULL AND OBJECT_ID(N'[FK__document___userl__2858E14B]') IS NULL
ALTER TABLE [document_store_role_member] ADD CONSTRAINT [FK__document___userl__2858E14B] FOREIGN KEY ([userlevel]) REFERENCES [lookup_document_store_role] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_user_member]') IS NOT NULL AND OBJECT_ID(N'[document_store]') IS NOT NULL AND OBJECT_ID(N'[FK__document___docum__1ECF7711]') IS NULL
ALTER TABLE [document_store_user_member] ADD CONSTRAINT [FK__document___docum__1ECF7711] FOREIGN KEY ([document_store_id]) REFERENCES [document_store] ([document_store_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_user_member]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__document___enter__22A007F5]') IS NULL
ALTER TABLE [document_store_user_member] ADD CONSTRAINT [FK__document___enter__22A007F5] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_user_member]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__document___item___1FC39B4A]') IS NULL
ALTER TABLE [document_store_user_member] ADD CONSTRAINT [FK__document___item___1FC39B4A] FOREIGN KEY ([item_id]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_user_member]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__document___modif__24885067]') IS NULL
ALTER TABLE [document_store_user_member] ADD CONSTRAINT [FK__document___modif__24885067] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[document_store_user_member]') IS NOT NULL AND OBJECT_ID(N'[lookup_document_store_role]') IS NOT NULL AND OBJECT_ID(N'[FK__document___userl__20B7BF83]') IS NULL
ALTER TABLE [document_store_user_member] ADD CONSTRAINT [FK__document___userl__20B7BF83] FOREIGN KEY ([userlevel]) REFERENCES [lookup_document_store_role] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[history]') IS NOT NULL AND OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[FK__history__contact__041B80D5]') IS NULL
ALTER TABLE [history] ADD CONSTRAINT [FK__history__contact__041B80D5] FOREIGN KEY ([contact_id]) REFERENCES [contact] ([contact_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[history]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__history__entered__050FA50E]') IS NULL
ALTER TABLE [history] ADD CONSTRAINT [FK__history__entered__050FA50E] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[history]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__history__modifie__0603C947]') IS NULL
ALTER TABLE [history] ADD CONSTRAINT [FK__history__modifie__0603C947] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[history]') IS NOT NULL AND OBJECT_ID(N'[organization]') IS NOT NULL AND OBJECT_ID(N'[FK__history__org_id__06F7ED80]') IS NULL
ALTER TABLE [history] ADD CONSTRAINT [FK__history__org_id__06F7ED80] FOREIGN KEY ([org_id]) REFERENCES [organization] ([org_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[knowledge_base]') IS NOT NULL AND OBJECT_ID(N'[ticket_category]') IS NOT NULL AND OBJECT_ID(N'[FK__knowledge__categ__09D45A2B]') IS NULL
ALTER TABLE [knowledge_base] ADD CONSTRAINT [FK__knowledge__categ__09D45A2B] FOREIGN KEY ([category_id]) REFERENCES [ticket_category] ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[knowledge_base]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__knowledge__enter__0AC87E64]') IS NULL
ALTER TABLE [knowledge_base] ADD CONSTRAINT [FK__knowledge__enter__0AC87E64] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[knowledge_base]') IS NOT NULL AND OBJECT_ID(N'[project_files]') IS NOT NULL AND OBJECT_ID(N'[FK__knowledge__item___0BBCA29D]') IS NULL
ALTER TABLE [knowledge_base] ADD CONSTRAINT [FK__knowledge__item___0BBCA29D] FOREIGN KEY ([item_id]) REFERENCES [project_files] ([item_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[knowledge_base]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__knowledge__modif__0CB0C6D6]') IS NULL
ALTER TABLE [knowledge_base] ADD CONSTRAINT [FK__knowledge__modif__0CB0C6D6] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[lookup_document_store_permission]') IS NOT NULL AND OBJECT_ID(N'[lookup_document_store_permission_category]') IS NOT NULL AND OBJECT_ID(N'[FK__lookup_do__categ__0BBCA29D]') IS NULL
ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [FK__lookup_do__categ__0BBCA29D] FOREIGN KEY ([category_id]) REFERENCES [lookup_document_store_permission_category] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[lookup_document_store_permission]') IS NOT NULL AND OBJECT_ID(N'[lookup_document_store_role]') IS NOT NULL AND OBJECT_ID(N'[FK__lookup_do__defau__108157BA]') IS NULL
ALTER TABLE [lookup_document_store_permission] ADD CONSTRAINT [FK__lookup_do__defau__108157BA] FOREIGN KEY ([default_role]) REFERENCES [lookup_document_store_role] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[opportunity_component]') IS NOT NULL AND OBJECT_ID(N'[lookup_opportunity_budget]') IS NOT NULL
ALTER TABLE [opportunity_component] ADD FOREIGN KEY ([budget]) REFERENCES [lookup_opportunity_budget] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[opportunity_component]') IS NOT NULL AND OBJECT_ID(N'[lookup_opportunity_competitors]') IS NOT NULL
ALTER TABLE [opportunity_component] ADD FOREIGN KEY ([competitors]) REFERENCES [lookup_opportunity_competitors] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[opportunity_component]') IS NOT NULL AND OBJECT_ID(N'[lookup_opportunity_event_compelling]') IS NOT NULL
ALTER TABLE [opportunity_component] ADD FOREIGN KEY ([compelling_event]) REFERENCES [lookup_opportunity_event_compelling] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[opportunity_component]') IS NOT NULL AND OBJECT_ID(N'[lookup_opportunity_environment]') IS NOT NULL
ALTER TABLE [opportunity_component] ADD FOREIGN KEY ([environment]) REFERENCES [lookup_opportunity_environment] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
--IF OBJECT_ID(N'[opportunity_header]') IS NOT NULL AND OBJECT_ID(N'[lookup_access_types]') IS NOT NULL
--ALTER TABLE [opportunity_header] ADD FOREIGN KEY ([access_type]) REFERENCES [lookup_access_types] ([code])
--GO

--IF OBJECT_ID(N'[opportunity_header]') IS NOT NULL AND OBJECT_ID(N'[lookup_access_types]') IS NOT NULL AND OBJECT_ID(N'[FK__opportuni__acces__5D21AF45]') IS NULL
--ALTER TABLE [opportunity_header] ADD CONSTRAINT [FK__opportuni__acces__5D21AF45] FOREIGN KEY ([access_type]) REFERENCES [lookup_access_types] ([code])
--GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[opportunity_header]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL
ALTER TABLE [opportunity_header] ADD FOREIGN KEY ([manager]) REFERENCES [access] ([user_id])
GO

--IF OBJECT_ID(N'[opportunity_header]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL
--ALTER TABLE [opportunity_header] ADD CONSTRAINT [FK__opportuni__acces__5D21AF46] FOREIGN KEY ([manager]) REFERENCES [access] ([user_id])
--GO


-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[order_payment]') IS NOT NULL AND OBJECT_ID(N'[payment_eft]') IS NOT NULL
ALTER TABLE [order_payment] ADD FOREIGN KEY ([bank_id]) REFERENCES [payment_eft] ([bank_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[order_payment]') IS NOT NULL AND OBJECT_ID(N'[payment_creditcard]') IS NOT NULL
ALTER TABLE [order_payment] ADD FOREIGN KEY ([creditcard_id]) REFERENCES [payment_creditcard] ([creditcard_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[order_payment]') IS NOT NULL AND OBJECT_ID(N'[customer_product_history]') IS NOT NULL
ALTER TABLE [order_payment] ADD FOREIGN KEY ([history_id]) REFERENCES [customer_product_history] ([history_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[order_payment]') IS NOT NULL AND OBJECT_ID(N'[order_product]') IS NOT NULL
ALTER TABLE [order_payment] ADD FOREIGN KEY ([order_item_id]) REFERENCES [order_product] ([item_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[order_payment]') IS NOT NULL AND OBJECT_ID(N'[lookup_payment_status]') IS NOT NULL
ALTER TABLE [order_payment] ADD FOREIGN KEY ([status_id]) REFERENCES [lookup_payment_status] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[order_payment_status]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__order_pay__enter__396371BC]') IS NULL
ALTER TABLE [order_payment_status] ADD CONSTRAINT [FK__order_pay__enter__396371BC] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[order_payment_status]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__order_pay__modif__3B4BBA2E]') IS NULL
ALTER TABLE [order_payment_status] ADD CONSTRAINT [FK__order_pay__modif__3B4BBA2E] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[order_payment_status]') IS NOT NULL AND OBJECT_ID(N'[order_payment]') IS NOT NULL AND OBJECT_ID(N'[FK__order_pay__payme__36870511]') IS NULL
ALTER TABLE [order_payment_status] ADD CONSTRAINT [FK__order_pay__payme__36870511] FOREIGN KEY ([payment_id]) REFERENCES [order_payment] ([payment_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[order_payment_status]') IS NOT NULL AND OBJECT_ID(N'[lookup_payment_status]') IS NOT NULL AND OBJECT_ID(N'[FK__order_pay__statu__377B294A]') IS NULL
ALTER TABLE [order_payment_status] ADD CONSTRAINT [FK__order_pay__statu__377B294A] FOREIGN KEY ([status_id]) REFERENCES [lookup_payment_status] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[organization]') IS NOT NULL AND OBJECT_ID(N'[lookup_contact_rating]') IS NOT NULL
ALTER TABLE [organization] ADD FOREIGN KEY ([rating]) REFERENCES [lookup_contact_rating] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[organization]') IS NOT NULL AND OBJECT_ID(N'[lookup_contact_source]') IS NOT NULL
ALTER TABLE [organization] ADD FOREIGN KEY ([source]) REFERENCES [lookup_contact_source] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[payment_creditcard]') IS NOT NULL AND OBJECT_ID(N'[lookup_creditcard_types]') IS NOT NULL AND OBJECT_ID(N'[FK__payment_c__card___02133CD2]') IS NULL
ALTER TABLE [payment_creditcard] ADD CONSTRAINT [FK__payment_c__card___02133CD2] FOREIGN KEY ([card_type]) REFERENCES [lookup_creditcard_types] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[payment_creditcard]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__payment_c__enter__03FB8544]') IS NULL
ALTER TABLE [payment_creditcard] ADD CONSTRAINT [FK__payment_c__enter__03FB8544] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[payment_creditcard]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__payment_c__modif__05E3CDB6]') IS NULL
ALTER TABLE [payment_creditcard] ADD CONSTRAINT [FK__payment_c__modif__05E3CDB6] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[payment_creditcard]') IS NOT NULL AND OBJECT_ID(N'[order_entry]') IS NOT NULL AND OBJECT_ID(N'[FK__payment_c__order__06D7F1EF]') IS NULL
ALTER TABLE [payment_creditcard] ADD CONSTRAINT [FK__payment_c__order__06D7F1EF] FOREIGN KEY ([order_id]) REFERENCES [order_entry] ([order_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[payment_eft]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__payment_e__enter__0AA882D3]') IS NULL
ALTER TABLE [payment_eft] ADD CONSTRAINT [FK__payment_e__enter__0AA882D3] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[payment_eft]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__payment_e__modif__0C90CB45]') IS NULL
ALTER TABLE [payment_eft] ADD CONSTRAINT [FK__payment_e__modif__0C90CB45] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[payment_eft]') IS NOT NULL AND OBJECT_ID(N'[order_entry]') IS NOT NULL AND OBJECT_ID(N'[FK__payment_e__order__0D84EF7E]') IS NULL
ALTER TABLE [payment_eft] ADD CONSTRAINT [FK__payment_e__order__0D84EF7E] FOREIGN KEY ([order_id]) REFERENCES [order_entry] ([order_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[product_catalog]') IS NOT NULL AND OBJECT_ID(N'[lookup_product_manufacturer]') IS NOT NULL
ALTER TABLE [product_catalog] ADD FOREIGN KEY ([manufacturer_id]) REFERENCES [lookup_product_manufacturer] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[product_catalog_pricing]') IS NOT NULL AND OBJECT_ID(N'[lookup_currency]') IS NOT NULL
ALTER TABLE [product_catalog_pricing] ADD FOREIGN KEY ([cost_currency]) REFERENCES [lookup_currency] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[product_option_map]') IS NOT NULL AND OBJECT_ID(N'[product_option]') IS NOT NULL AND OBJECT_ID(N'[FK__product_o__optio__7928F116]') IS NULL
ALTER TABLE [product_option_map] ADD CONSTRAINT [FK__product_o__optio__7928F116] FOREIGN KEY ([option_id]) REFERENCES [product_option] ([option_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[product_option_map]') IS NOT NULL AND OBJECT_ID(N'[product_catalog]') IS NOT NULL AND OBJECT_ID(N'[FK__product_o__produ__7834CCDD]') IS NULL
ALTER TABLE [product_option_map] ADD CONSTRAINT [FK__product_o__produ__7834CCDD] FOREIGN KEY ([product_id]) REFERENCES [product_catalog] ([product_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[product_option_values]') IS NOT NULL AND OBJECT_ID(N'[lookup_currency]') IS NOT NULL
ALTER TABLE [product_option_values] ADD FOREIGN KEY ([cost_currency]) REFERENCES [lookup_currency] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[project_accounts]') IS NOT NULL AND OBJECT_ID(N'[organization]') IS NOT NULL AND OBJECT_ID(N'[FK__project_a__org_i__4F67C174]') IS NULL
ALTER TABLE [project_accounts] ADD CONSTRAINT [FK__project_a__org_i__4F67C174] FOREIGN KEY ([org_id]) REFERENCES [organization] ([org_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[project_accounts]') IS NOT NULL AND OBJECT_ID(N'[projects]') IS NOT NULL AND OBJECT_ID(N'[FK__project_a__proje__4E739D3B]') IS NULL
ALTER TABLE [project_accounts] ADD CONSTRAINT [FK__project_a__proje__4E739D3B] FOREIGN KEY ([project_id]) REFERENCES [projects] ([project_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[project_assignments_status]') IS NOT NULL AND OBJECT_ID(N'[lookup_project_status]') IS NOT NULL
ALTER TABLE [project_assignments_status] ADD FOREIGN KEY ([project_status_id]) REFERENCES [lookup_project_status] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[project_assignments_status]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL
ALTER TABLE [project_assignments_status] ADD FOREIGN KEY ([user_assign_id]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[project_folders]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__project_f__enter__5402595F]') IS NULL
ALTER TABLE [project_folders] ADD CONSTRAINT [FK__project_f__enter__5402595F] FOREIGN KEY ([enteredBy]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[project_folders]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__project_f__modif__54F67D98]') IS NULL
ALTER TABLE [project_folders] ADD CONSTRAINT [FK__project_f__modif__54F67D98] FOREIGN KEY ([modifiedBy]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[project_news]') IS NOT NULL AND OBJECT_ID(N'[project_news_category]') IS NOT NULL AND OBJECT_ID(N'[FK__project_n__categ__22951AFD]') IS NULL
ALTER TABLE [project_news] ADD CONSTRAINT [FK__project_n__categ__22951AFD] FOREIGN KEY ([category_id]) REFERENCES [project_news_category] ([category_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[project_news]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__project_n__enter__60683044]') IS NULL
ALTER TABLE [project_news] ADD CONSTRAINT [FK__project_n__enter__60683044] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[project_news]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__project_n__modif__615C547D]') IS NULL
ALTER TABLE [project_news] ADD CONSTRAINT [FK__project_n__modif__615C547D] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[project_news]') IS NOT NULL AND OBJECT_ID(N'[projects]') IS NOT NULL AND OBJECT_ID(N'[FK__project_n__proje__625078B6]') IS NULL
ALTER TABLE [project_news] ADD CONSTRAINT [FK__project_n__proje__625078B6] FOREIGN KEY ([project_id]) REFERENCES [projects] ([project_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[project_news]') IS NOT NULL AND OBJECT_ID(N'[lookup_news_template]') IS NOT NULL AND OBJECT_ID(N'[FK__project_n__templ__31D75E8D]') IS NULL
ALTER TABLE [project_news] ADD CONSTRAINT [FK__project_n__templ__31D75E8D] FOREIGN KEY ([template_id]) REFERENCES [lookup_news_template] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[project_news_category]') IS NOT NULL AND OBJECT_ID(N'[projects]') IS NOT NULL AND OBJECT_ID(N'[FK__project_n__proje__1BE81D6E]') IS NULL
ALTER TABLE [project_news_category] ADD CONSTRAINT [FK__project_n__proje__1BE81D6E] FOREIGN KEY ([project_id]) REFERENCES [projects] ([project_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[project_team]') IS NOT NULL AND OBJECT_ID(N'[lookup_project_role]') IS NOT NULL
ALTER TABLE [project_team] ADD FOREIGN KEY ([userlevel]) REFERENCES [lookup_project_role] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[projects]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL
ALTER TABLE [projects] ADD FOREIGN KEY ([approvalBy]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[quote_condition]') IS NOT NULL AND OBJECT_ID(N'[lookup_quote_condition]') IS NOT NULL AND OBJECT_ID(N'[FK__quote_con__condi__05CEBF1D]') IS NULL
ALTER TABLE [quote_condition] ADD CONSTRAINT [FK__quote_con__condi__05CEBF1D] FOREIGN KEY ([condition_id]) REFERENCES [lookup_quote_condition] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[quote_condition]') IS NOT NULL AND OBJECT_ID(N'[quote_entry]') IS NOT NULL AND OBJECT_ID(N'[FK__quote_con__quote__04DA9AE4]') IS NULL
ALTER TABLE [quote_condition] ADD CONSTRAINT [FK__quote_con__quote__04DA9AE4] FOREIGN KEY ([quote_id]) REFERENCES [quote_entry] ([quote_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[quote_entry]') IS NOT NULL AND OBJECT_ID(N'[lookup_quote_delivery]') IS NOT NULL
ALTER TABLE [quote_entry] ADD FOREIGN KEY ([delivery_id]) REFERENCES [lookup_quote_delivery] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[quote_entry]') IS NOT NULL AND OBJECT_ID(N'[quote_group]') IS NOT NULL
ALTER TABLE [quote_entry] ADD FOREIGN KEY ([group_id]) REFERENCES [quote_group] ([group_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[quote_entry]') IS NOT NULL AND OBJECT_ID(N'[project_files]') IS NOT NULL
ALTER TABLE [quote_entry] ADD FOREIGN KEY ([logo_file_id]) REFERENCES [project_files] ([item_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[quote_entry]') IS NOT NULL AND OBJECT_ID(N'[opportunity_header]') IS NOT NULL
ALTER TABLE [quote_entry] ADD FOREIGN KEY ([opp_id]) REFERENCES [opportunity_header] ([opp_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[quote_remark]') IS NOT NULL AND OBJECT_ID(N'[quote_entry]') IS NOT NULL AND OBJECT_ID(N'[FK__quote_rem__quote__18E19391]') IS NULL
ALTER TABLE [quote_remark] ADD CONSTRAINT [FK__quote_rem__quote__18E19391] FOREIGN KEY ([quote_id]) REFERENCES [quote_entry] ([quote_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[quote_remark]') IS NOT NULL AND OBJECT_ID(N'[lookup_quote_remarks]') IS NOT NULL AND OBJECT_ID(N'[FK__quote_rem__remar__19D5B7CA]') IS NULL
ALTER TABLE [quote_remark] ADD CONSTRAINT [FK__quote_rem__remar__19D5B7CA] FOREIGN KEY ([remark_id]) REFERENCES [lookup_quote_remarks] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[quotelog]') IS NOT NULL AND OBJECT_ID(N'[lookup_quote_delivery]') IS NOT NULL AND OBJECT_ID(N'[FK__quotelog__delive__0D6FE0E5]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__delive__0D6FE0E5] FOREIGN KEY ([delivery_id]) REFERENCES [lookup_quote_delivery] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[quotelog]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__quotelog__entere__0E64051E]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__entere__0E64051E] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[quotelog]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__quotelog__modifi__104C4D90]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__modifi__104C4D90] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[quotelog]') IS NOT NULL AND OBJECT_ID(N'[quote_entry]') IS NOT NULL AND OBJECT_ID(N'[FK__quotelog__quote___08AB2BC8]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__quote___08AB2BC8] FOREIGN KEY ([quote_id]) REFERENCES [quote_entry] ([quote_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[quotelog]') IS NOT NULL AND OBJECT_ID(N'[lookup_quote_source]') IS NOT NULL AND OBJECT_ID(N'[FK__quotelog__source__099F5001]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__source__099F5001] FOREIGN KEY ([source_id]) REFERENCES [lookup_quote_source] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[quotelog]') IS NOT NULL AND OBJECT_ID(N'[lookup_quote_status]') IS NOT NULL AND OBJECT_ID(N'[FK__quotelog__status__0A93743A]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__status__0A93743A] FOREIGN KEY ([status_id]) REFERENCES [lookup_quote_status] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[quotelog]') IS NOT NULL AND OBJECT_ID(N'[lookup_quote_terms]') IS NOT NULL AND OBJECT_ID(N'[FK__quotelog__terms___0B879873]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__terms___0B879873] FOREIGN KEY ([terms_id]) REFERENCES [lookup_quote_terms] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[quotelog]') IS NOT NULL AND OBJECT_ID(N'[lookup_quote_type]') IS NOT NULL AND OBJECT_ID(N'[FK__quotelog__type_i__0C7BBCAC]') IS NULL
ALTER TABLE [quotelog] ADD CONSTRAINT [FK__quotelog__type_i__0C7BBCAC] FOREIGN KEY ([type_id]) REFERENCES [lookup_quote_type] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[relationship]') IS NOT NULL AND OBJECT_ID(N'[lookup_relationship_types]') IS NOT NULL AND OBJECT_ID(N'[FK__relations__type___11D4A34F]') IS NULL
ALTER TABLE [relationship] ADD CONSTRAINT [FK__relations__type___11D4A34F] FOREIGN KEY ([type_id]) REFERENCES [lookup_relationship_types] ([type_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[service_contract]') IS NOT NULL AND OBJECT_ID(N'[organization]') IS NOT NULL AND OBJECT_ID(N'[FK__service_c__accou__4C2C2D6D]') IS NULL
ALTER TABLE [service_contract] ADD CONSTRAINT [FK__service_c__accou__4C2C2D6D] FOREIGN KEY ([account_id]) REFERENCES [organization] ([org_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[service_contract]') IS NOT NULL AND OBJECT_ID(N'[lookup_sc_category]') IS NOT NULL AND OBJECT_ID(N'[FK__service_c__categ__4D2051A6]') IS NULL
ALTER TABLE [service_contract] ADD CONSTRAINT [FK__service_c__categ__4D2051A6] FOREIGN KEY ([category]) REFERENCES [lookup_sc_category] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[service_contract]') IS NOT NULL AND OBJECT_ID(N'[contact]') IS NOT NULL AND OBJECT_ID(N'[FK__service_c__conta__4E1475DF]') IS NULL
ALTER TABLE [service_contract] ADD CONSTRAINT [FK__service_c__conta__4E1475DF] FOREIGN KEY ([contact_id]) REFERENCES [contact] ([contact_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[service_contract]') IS NOT NULL AND OBJECT_ID(N'[lookup_email_model]') IS NOT NULL AND OBJECT_ID(N'[FK__service_c__email__4F089A18]') IS NULL
ALTER TABLE [service_contract] ADD CONSTRAINT [FK__service_c__email__4F089A18] FOREIGN KEY ([email_service_model]) REFERENCES [lookup_email_model] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[service_contract]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__service_c__enter__4FFCBE51]') IS NULL
ALTER TABLE [service_contract] ADD CONSTRAINT [FK__service_c__enter__4FFCBE51] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[service_contract]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__service_c__modif__50F0E28A]') IS NULL
ALTER TABLE [service_contract] ADD CONSTRAINT [FK__service_c__modif__50F0E28A] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[service_contract]') IS NOT NULL AND OBJECT_ID(N'[lookup_onsite_model]') IS NOT NULL AND OBJECT_ID(N'[FK__service_c__onsit__51E506C3]') IS NULL
ALTER TABLE [service_contract] ADD CONSTRAINT [FK__service_c__onsit__51E506C3] FOREIGN KEY ([onsite_service_model]) REFERENCES [lookup_onsite_model] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[service_contract]') IS NOT NULL AND OBJECT_ID(N'[lookup_response_model]') IS NOT NULL AND OBJECT_ID(N'[FK__service_c__respo__52D92AFC]') IS NULL
ALTER TABLE [service_contract] ADD CONSTRAINT [FK__service_c__respo__52D92AFC] FOREIGN KEY ([response_time]) REFERENCES [lookup_response_model] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[service_contract]') IS NOT NULL AND OBJECT_ID(N'[lookup_phone_model]') IS NOT NULL AND OBJECT_ID(N'[FK__service_c__telep__53CD4F35]') IS NULL
ALTER TABLE [service_contract] ADD CONSTRAINT [FK__service_c__telep__53CD4F35] FOREIGN KEY ([telephone_service_model]) REFERENCES [lookup_phone_model] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[service_contract]') IS NOT NULL AND OBJECT_ID(N'[lookup_sc_type]') IS NOT NULL AND OBJECT_ID(N'[FK__service_co__type__54C1736E]') IS NULL
ALTER TABLE [service_contract] ADD CONSTRAINT [FK__service_co__type__54C1736E] FOREIGN KEY ([type]) REFERENCES [lookup_sc_type] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[step_action_map]') IS NOT NULL AND OBJECT_ID(N'[lookup_step_actions]') IS NOT NULL AND OBJECT_ID(N'[FK__step_acti__actio__5B6E70FD]') IS NULL
ALTER TABLE [step_action_map] ADD CONSTRAINT [FK__step_acti__actio__5B6E70FD] FOREIGN KEY ([action_id]) REFERENCES [lookup_step_actions] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[step_action_map]') IS NOT NULL AND OBJECT_ID(N'[action_plan_constants]') IS NOT NULL AND OBJECT_ID(N'[FK__step_acti__const__5C629536]') IS NULL
ALTER TABLE [step_action_map] ADD CONSTRAINT [FK__step_acti__const__5C629536] FOREIGN KEY ([constant_id]) REFERENCES [action_plan_constants] ([map_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[task]') IS NOT NULL AND OBJECT_ID(N'[lookup_ticket_task_category]') IS NOT NULL
ALTER TABLE [task] ADD FOREIGN KEY ([ticket_task_category_id]) REFERENCES [lookup_ticket_task_category] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[taskcategorylink_news]') IS NOT NULL AND OBJECT_ID(N'[lookup_task_category]') IS NOT NULL AND OBJECT_ID(N'[FK__taskcateg__categ__7C7A5F0D]') IS NULL
ALTER TABLE [taskcategorylink_news] ADD CONSTRAINT [FK__taskcateg__categ__7C7A5F0D] FOREIGN KEY ([category_id]) REFERENCES [lookup_task_category] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[taskcategorylink_news]') IS NOT NULL AND OBJECT_ID(N'[project_news]') IS NOT NULL AND OBJECT_ID(N'[FK__taskcateg__news___7B863AD4]') IS NULL
ALTER TABLE [taskcategorylink_news] ADD CONSTRAINT [FK__taskcateg__news___7B863AD4] FOREIGN KEY ([news_id]) REFERENCES [project_news] ([news_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[tasklink_ticket]') IS NOT NULL AND OBJECT_ID(N'[lookup_ticket_task_category]') IS NOT NULL
ALTER TABLE [tasklink_ticket] ADD FOREIGN KEY ([category_id]) REFERENCES [lookup_ticket_task_category] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[ticket]') IS NOT NULL AND OBJECT_ID(N'[lookup_ticket_cause]') IS NOT NULL
ALTER TABLE [ticket] ADD FOREIGN KEY ([cause_id]) REFERENCES [lookup_ticket_cause] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[ticket]') IS NOT NULL AND OBJECT_ID(N'[ticket_defect]') IS NOT NULL
ALTER TABLE [ticket] ADD FOREIGN KEY ([defect_id]) REFERENCES [ticket_defect] ([defect_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[ticket]') IS NOT NULL AND OBJECT_ID(N'[lookup_ticket_resolution]') IS NOT NULL
ALTER TABLE [ticket] ADD FOREIGN KEY ([resolution_id]) REFERENCES [lookup_ticket_resolution] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[ticket]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL
ALTER TABLE [ticket] ADD FOREIGN KEY ([resolvedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[ticket]') IS NOT NULL AND OBJECT_ID(N'[lookup_department]') IS NOT NULL
ALTER TABLE [ticket] ADD FOREIGN KEY ([resolvedby_department_code]) REFERENCES [lookup_department] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[ticket]') IS NOT NULL AND OBJECT_ID(N'[lookup_ticket_status]') IS NOT NULL
ALTER TABLE [ticket] ADD FOREIGN KEY ([status_id]) REFERENCES [lookup_ticket_status] ([code])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating the foreign key
IF OBJECT_ID(N'[ticket]') IS NOT NULL AND OBJECT_ID(N'[user_group]') IS NOT NULL
ALTER TABLE [ticket] ADD FOREIGN KEY ([user_group_id]) REFERENCES [user_group] ([group_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[ticket_activity_item]') IS NOT NULL AND OBJECT_ID(N'[ticket_csstm_form]') IS NOT NULL AND OBJECT_ID(N'[FK__ticket_ac__link___13B2CA20]') IS NULL
ALTER TABLE [ticket_activity_item] ADD CONSTRAINT [FK__ticket_ac__link___13B2CA20] FOREIGN KEY ([link_form_id]) REFERENCES [ticket_csstm_form] ([form_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[ticket_category_draft_plan_map]') IS NOT NULL AND OBJECT_ID(N'[ticket_category_draft]') IS NOT NULL AND OBJECT_ID(N'[FK__ticket_ca__categ__14A6EE59]') IS NULL
ALTER TABLE [ticket_category_draft_plan_map] ADD CONSTRAINT [FK__ticket_ca__categ__14A6EE59] FOREIGN KEY ([category_id]) REFERENCES [ticket_category_draft] ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[ticket_category_draft_plan_map]') IS NOT NULL AND OBJECT_ID(N'[action_plan]') IS NOT NULL AND OBJECT_ID(N'[FK__ticket_ca__plan___159B1292]') IS NULL
ALTER TABLE [ticket_category_draft_plan_map] ADD CONSTRAINT [FK__ticket_ca__plan___159B1292] FOREIGN KEY ([plan_id]) REFERENCES [action_plan] ([plan_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[ticket_category_plan_map]') IS NOT NULL AND OBJECT_ID(N'[ticket_category]') IS NOT NULL AND OBJECT_ID(N'[FK__ticket_ca__categ__168F36CB]') IS NULL
ALTER TABLE [ticket_category_plan_map] ADD CONSTRAINT [FK__ticket_ca__categ__168F36CB] FOREIGN KEY ([category_id]) REFERENCES [ticket_category] ([id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[ticket_category_plan_map]') IS NOT NULL AND OBJECT_ID(N'[action_plan]') IS NOT NULL AND OBJECT_ID(N'[FK__ticket_ca__plan___17835B04]') IS NULL
ALTER TABLE [ticket_category_plan_map] ADD CONSTRAINT [FK__ticket_ca__plan___17835B04] FOREIGN KEY ([plan_id]) REFERENCES [action_plan] ([plan_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[ticket_csstm_form]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__ticket_cs__enter__18777F3D]') IS NULL
ALTER TABLE [ticket_csstm_form] ADD CONSTRAINT [FK__ticket_cs__enter__18777F3D] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[ticket_csstm_form]') IS NOT NULL AND OBJECT_ID(N'[ticket]') IS NOT NULL AND OBJECT_ID(N'[FK__ticket_cs__link___196BA376]') IS NULL
ALTER TABLE [ticket_csstm_form] ADD CONSTRAINT [FK__ticket_cs__link___196BA376] FOREIGN KEY ([link_ticket_id]) REFERENCES [ticket] ([ticketid])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[ticket_csstm_form]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__ticket_cs__modif__1A5FC7AF]') IS NULL
ALTER TABLE [ticket_csstm_form] ADD CONSTRAINT [FK__ticket_cs__modif__1A5FC7AF] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[user_group]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__user_grou__enter__28ADE706]') IS NULL
ALTER TABLE [user_group] ADD CONSTRAINT [FK__user_grou__enter__28ADE706] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[user_group]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__user_grou__modif__29A20B3F]') IS NULL
ALTER TABLE [user_group] ADD CONSTRAINT [FK__user_grou__modif__29A20B3F] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[user_group_map]') IS NOT NULL AND OBJECT_ID(N'[user_group]') IS NOT NULL AND OBJECT_ID(N'[FK__user_grou__group__2A962F78]') IS NULL
ALTER TABLE [user_group_map] ADD CONSTRAINT [FK__user_grou__group__2A962F78] FOREIGN KEY ([group_id]) REFERENCES [user_group] ([group_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[user_group_map]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__user_grou__user___2B8A53B1]') IS NULL
ALTER TABLE [user_group_map] ADD CONSTRAINT [FK__user_grou__user___2B8A53B1] FOREIGN KEY ([user_id]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[webdav]') IS NOT NULL AND OBJECT_ID(N'[permission_category]') IS NOT NULL AND OBJECT_ID(N'[FK__webdav__category__308E3499]') IS NULL
ALTER TABLE [webdav] ADD CONSTRAINT [FK__webdav__category__308E3499] FOREIGN KEY ([category_id]) REFERENCES [permission_category] ([category_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[webdav]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__webdav__enteredb__32767D0B]') IS NULL
ALTER TABLE [webdav] ADD CONSTRAINT [FK__webdav__enteredb__32767D0B] FOREIGN KEY ([enteredby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

-- Creating a foreign key for the table
IF OBJECT_ID(N'[webdav]') IS NOT NULL AND OBJECT_ID(N'[access]') IS NOT NULL AND OBJECT_ID(N'[FK__webdav__modified__345EC57D]') IS NULL
ALTER TABLE [webdav] ADD CONSTRAINT [FK__webdav__modified__345EC57D] FOREIGN KEY ([modifiedby]) REFERENCES [access] ([user_id])
GO

-- TRANSACTION HANDLING
IF @@ERROR<>0 OR @@TRANCOUNT=0 BEGIN IF @@TRANCOUNT>0 ROLLBACK SET NOEXEC ON END
GO

COMMIT
GO

SET NOEXEC OFF
GO

