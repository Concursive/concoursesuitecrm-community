-- Relationships

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

IF OBJECT_ID('[relationship]') IS NOT NULL AND OBJECT_ID('[lookup_relationship_types]') IS NOT NULL AND OBJECT_ID('[FK__relations__type___11D4A34F]') IS NULL
ALTER TABLE [relationship] ADD CONSTRAINT [FK__relations__type___11D4A34F] FOREIGN KEY ([type_id]) REFERENCES [lookup_relationship_types] ([type_id])
GO

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



