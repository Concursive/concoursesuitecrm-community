 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[campaign_list_groups] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                    
----------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[campaign_list_groups] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[autoguide_inventory] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_inventory] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_inventory] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[autoguide_inventory] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[ticket_priority] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_priority] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[ticket_priority] ([code],[description],[style],[default_item],[level],[enabled])VALUES(1,'As Scheduled','background-color:lightgreen;color:black;',1,0,1)
INSERT [crm].[ticket_priority] ([code],[description],[style],[default_item],[level],[enabled])VALUES(2,'Urgent','background-color:yellow;color:black;',0,1,1)
INSERT [crm].[ticket_priority] ([code],[description],[style],[default_item],[level],[enabled])VALUES(3,'Critical','background-color:red;color:black;font-weight:bold;',0,2,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_priority] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[ticket_priority] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[viewpoint_permission] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[viewpoint_permission] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[viewpoint_permission] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[viewpoint_permission] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[product_option_map] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_map] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                               
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_map] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[product_option_map] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[active_campaign_groups] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_campaign_groups] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_campaign_groups] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[active_campaign_groups] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_project_issues] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_project_issues] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(1,'Status Update',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(2,'Bug Report',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(3,'Network',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(4,'Hardware',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(5,'Permissions',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(6,'User',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(7,'Documentation',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(8,'Feature',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(9,'Procedure',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(10,'Training',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(11,'3rd-Party Software',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(12,'Database',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(13,'Information',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(14,'Testing',0,0,1,0)
INSERT [crm].[lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(15,'Security',0,0,1,0)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_project_issues] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_project_issues] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[business_process_events] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_events] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_events] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[business_process_events] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[permission] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[permission] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(1,1,'accounts',1,0,0,0,'Access to Accounts module',1
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(2,1,'accounts-accounts',1,1,1,1,'Account Records',20
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(3,1,'accounts-accounts-folders',1,1,1,1,'Folders',30
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(4,1,'accounts-accounts-contacts',1,1,1,1,'Contacts',
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(5,1,'accounts-accounts-contacts-opportunities',1,1,1
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(6,1,'accounts-accounts-contacts-calls',1,1,1,1,'Cont
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(7,1,'accounts-accounts-contacts-messages',1,1,1,1,'C
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(8,1,'accounts-accounts-opportunities',1,1,1,1,'Oppor
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(9,1,'accounts-accounts-tickets',1,1,1,1,'Tickets',90
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(10,1,'accounts-accounts-tickets-tasks',1,1,1,1,'Tick
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(11,1,'accounts-accounts-tickets-folders',1,1,1,1,'Ti
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(12,1,'accounts-accounts-tickets-documents',1,1,1,1,'
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(13,1,'accounts-accounts-documents',1,1,1,1,'Document
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(14,1,'accounts-accounts-reports',1,1,0,1,'Export Acc
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(15,1,'accounts-dashboard',1,0,0,0,'Dashboard',150,1,
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(16,1,'accounts-accounts-revenue',1,1,1,1,'Revenue',1
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(17,1,'accounts-autoguide-inventory',1,1,1,1,'Auto Gu
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(18,1,'accounts-service-contracts',1,1,1,1,'Service C
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(19,1,'accounts-assets',1,1,1,1,'Assets',190,1,1,0)
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(20,1,'accounts-accounts-tickets-maintenance-report',
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(21,1,'accounts-accounts-tickets-activity-log',1,1,1,
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(22,1,'portal-user',1,1,1,1,'Customer Portal User',22
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(23,1,'accounts-quotes',1,1,1,1,'Quotes',230,0,0,0)
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(24,1,'accounts-orders',1,1,1,1,'Orders',240,0,0,0)
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(25,1,'accounts-products',1,1,1,1,'Products and Servi
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(26,2,'contacts',1,0,0,0,'Access to Contacts module',
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(27,2,'contacts-external_contacts',1,1,1,1,'General C
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(28,2,'contacts-external_contacts-reports',1,1,0,1,'E
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(29,2,'contacts-external_contacts-folders',1,1,1,1,'F
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(30,2,'contacts-external_contacts-calls',1,1,1,1,'Cal
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(31,2,'contacts-external_contacts-messages',1,0,0,0,'
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(32,2,'contacts-external_contacts-opportunities',1,1,
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(33,2,'contacts-external_contacts-imports',1,1,1,1,'I
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(34,3,'autoguide',1,0,0,0,'Access to the Auto Guide m
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(35,3,'autoguide-adruns',0,0,1,0,'Ad Run complete sta
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(36,4,'pipeline',1,0,0,0,'Access to Pipeline module',
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(37,4,'pipeline-opportunities',1,1,1,1,'Opportunity R
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(38,4,'pipeline-dashboard',1,0,0,0,'Dashboard',30,1,1
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(39,4,'pipeline-reports',1,1,0,1,'Export Opportunity 
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(40,4,'pipeline-opportunities-calls',1,1,1,1,'Calls',
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(41,4,'pipeline-opportunities-documents',1,1,1,1,'Doc
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(42,5,'demo',1,1,1,1,'Access to Demo/Non-working feat
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(43,6,'campaign',1,0,0,0,'Access to Communications mo
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(44,6,'campaign-dashboard',1,0,0,0,'Dashboard',20,1,1
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(45,6,'campaign-campaigns',1,1,1,1,'Campaign Records'
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(46,6,'campaign-campaigns-groups',1,1,1,1,'Group Reco
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(47,6,'campaign-campaigns-messages',1,1,1,1,'Message 
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(48,6,'campaign-campaigns-surveys',1,1,1,1,'Survey Re
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(49,7,'projects',1,0,0,0,'Access to Project Managemen
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(50,7,'projects-personal',1,0,0,0,'Personal View',20,
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(51,7,'projects-enterprise',1,0,0,0,'Enterprise View'
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(52,7,'projects-projects',1,1,1,1,'Project Records',4
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(53,8,'tickets',1,0,0,0,'Access to Help Desk module',
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(54,8,'tickets-tickets',1,1,1,1,'Ticket Records',20,1
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(55,8,'tickets-reports',1,1,1,1,'Export Ticket Data',
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(56,8,'tickets-tickets-tasks',1,1,1,1,'Tasks',40,1,1,
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(57,8,'tickets-maintenance-report',1,1,1,1,'Maintenan
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(58,8,'tickets-activity-log',1,1,1,1,'Activities',60,
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(59,9,'admin',1,0,0,0,'Access to Admin module',10,1,1
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(60,9,'admin-users',1,1,1,1,'Users',20,1,1,0)
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(61,9,'admin-roles',1,1,1,1,'Roles',30,1,1,0)
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(62,9,'admin-usage',1,0,0,0,'System Usage',40,1,1,0)
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(63,9,'admin-sysconfig',1,0,1,0,'System Configuration
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(64,9,'admin-sysconfig-lists',1,0,1,0,'Configure Look
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(65,9,'admin-sysconfig-folders',1,1,1,1,'Configure Cu
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(66,9,'admin-object-workflow',1,1,1,1,'Configure Obje
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(67,9,'admin-sysconfig-categories',1,1,1,1,'Categorie
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(68,10,'help',1,0,0,0,'Access to Help System',10,1,1,
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(69,11,'globalitems-search',1,0,0,0,'Access to Global
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(70,11,'globalitems-myitems',1,0,0,0,'Access to My It
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(71,11,'globalitems-recentitems',1,0,0,0,'Access to R
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(72,12,'myhomepage',1,0,0,0,'Access to My Home Page m
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(73,12,'myhomepage-dashboard',1,0,0,0,'View Performan
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(74,12,'myhomepage-miner',1,1,0,1,'Industry News reco
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(75,12,'myhomepage-inbox',1,0,0,0,'Mailbox',40,1,1,0)
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(76,12,'myhomepage-tasks',1,1,1,1,'Tasks',50,1,1,0)
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(77,12,'myhomepage-reassign',1,0,1,0,'Re-assign Items
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(78,12,'myhomepage-profile',1,0,0,0,'Profile',70,1,1,
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(79,12,'myhomepage-profile-personal',1,0,1,0,'Persona
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(80,12,'myhomepage-profile-settings',1,0,1,0,'Setting
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(81,12,'myhomepage-profile-password',0,0,1,0,'Passwor
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(82,12,'myhomepage-action-lists',1,1,1,1,'Action List
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(83,13,'qa',1,1,1,1,'Access to QA Tool',10,1,1,0)
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(84,14,'reports',1,0,0,0,'Access to Reports module',1
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(85,17,'product-catalog',1,0,0,0,'Access to Product C
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(86,17,'product-catalog-product',1,1,1,1,'Products',2
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(87,18,'products',1,0,0,0,'Access to Products and Ser
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(88,19,'quotes',1,1,1,1,'Access to Quotes module',10,
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(89,20,'orders',1,1,1,1,'Access to Orders module',10,
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(90,21,'employees',1,0,0,0,'Access to Employee module
INSERT [crm].[permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(91,21,'contacts-internal_contacts',1,1,1,1,'Employee

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[permission] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[permission] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[help_tableof_contents] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_tableof_contents] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(1,'Modules',NULL,NULL,NULL,NULL,1,5,0,'May 28 20
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(2,'My Home Page',NULL,NULL,1,12,2,5,0,'May 28 20
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(3,'Overview',NULL,NULL,2,12,3,5,0,'May 28 2004 1
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(4,'Mailbox',NULL,NULL,2,12,3,10,0,'May 28 2004 1
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(5,'Message Details',NULL,NULL,4,12,4,15,0,'May 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(6,'New Message',NULL,NULL,4,12,4,20,0,'May 28 20
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(7,'Reply Message',NULL,NULL,4,12,4,25,0,'May 28 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(8,'SendMessage',NULL,NULL,4,12,4,30,0,'May 28 20
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(9,'Forward message',NULL,NULL,4,12,4,35,0,'May 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(10,'Tasks',NULL,NULL,2,12,3,40,0,'May 28 2004 10
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(11,'Advanced Task',NULL,NULL,10,12,4,45,0,'May 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(12,'Forwarding a Task',NULL,NULL,10,12,4,50,0,'M
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(13,'Modify task',NULL,NULL,10,12,4,55,0,'May 28 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(14,'Action Lists',NULL,NULL,2,12,3,60,0,'May 28 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(15,'Action Contacts',NULL,NULL,14,12,4,65,0,'May
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(16,'Add Action List',NULL,NULL,14,12,4,70,0,'May
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(17,'Modify Action',NULL,NULL,14,12,4,75,0,'May 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(18,'Re-assignments',NULL,NULL,2,12,3,80,0,'May 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(19,'My Settings',NULL,NULL,2,12,3,85,0,'May 28 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(20,'Personal Information',NULL,NULL,19,12,4,90,0
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(21,'Location Settings',NULL,NULL,19,12,4,95,0,'M
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(22,'Update password',NULL,NULL,19,12,4,100,0,'Ma
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(23,'Contacts',NULL,NULL,1,2,2,10,0,'May 28 2004 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(24,'Add a Contact',NULL,NULL,23,2,3,5,0,'May 28 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(25,'Search Contacts',NULL,NULL,23,2,3,10,0,'May 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(26,'Export Data',NULL,NULL,23,2,3,15,0,'May 28 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(27,'Exporting data',NULL,NULL,23,2,3,20,0,'May 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(28,'Pipeline',NULL,NULL,1,4,2,15,0,'May 28 2004 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(29,'Overview',NULL,NULL,28,4,3,5,0,'May 28 2004 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(30,'Add a Opportunity',NULL,NULL,28,4,3,10,0,'Ma
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(31,'Search Opportunities',NULL,NULL,28,4,3,15,0,
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(32,'Export Data',NULL,NULL,28,4,3,20,0,'May 28 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(33,'Accounts',NULL,NULL,1,1,2,20,0,'May 28 2004 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(34,'Overview',NULL,NULL,33,1,3,5,0,'May 28 2004 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(35,'Add an Account',NULL,NULL,33,1,3,10,0,'May 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(36,'Modify Account',NULL,NULL,33,1,3,15,0,'May 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(37,'Contact Details',NULL,NULL,36,1,4,20,0,'May 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(38,'Folder Record Details',NULL,NULL,36,1,4,25,0
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(39,'Opportunity Details',NULL,NULL,36,1,4,30,0,'
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(40,'Revenue Details',NULL,NULL,36,1,4,35,0,'May 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(41,'Revenue Details',NULL,NULL,40,1,5,40,0,'May 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(42,'Add Revenue',NULL,NULL,40,1,5,45,0,'May 28 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(43,'Modify Revenue',NULL,NULL,40,1,5,50,0,'May 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(44,'Ticket Details',NULL,NULL,36,1,4,55,0,'May 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(45,'Document Details',NULL,NULL,36,1,4,60,0,'May
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(46,'Search Accounts',NULL,NULL,33,1,3,65,0,'May 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(47,'Account Details',NULL,NULL,33,1,3,70,0,'May 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(48,'Revenue Dashboard',NULL,NULL,33,1,3,75,0,'Ma
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(49,'Export Data',NULL,NULL,33,1,3,80,0,'May 28 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(50,'Communications',NULL,NULL,1,6,2,25,0,'May 28
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(51,'Communications Dashboard',NULL,NULL,50,6,3,5
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(52,'Add a campaign',NULL,NULL,50,6,3,10,0,'May 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(53,'Campaign List',NULL,NULL,50,6,3,15,0,'May 28
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(54,'View Groups',NULL,NULL,50,6,3,20,0,'May 28 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(55,'Add a Group',NULL,NULL,50,6,3,25,0,'May 28 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(56,'Message List',NULL,NULL,50,6,3,30,0,'May 28 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(57,'Adding a Message',NULL,NULL,50,6,3,35,0,'May
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(58,'Create Attachments',NULL,NULL,50,6,3,40,0,'M
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(59,'Help Desk',NULL,NULL,1,8,2,30,0,'May 28 2004
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(60,'Ticket Details',NULL,NULL,59,8,3,5,0,'May 28
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(61,'Add a Ticket',NULL,NULL,59,8,3,10,0,'May 28 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(62,'Search Existing Tickets',NULL,NULL,59,8,3,15
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(63,'Export Data',NULL,NULL,59,8,3,20,0,'May 28 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(64,'Modify Ticket Details',NULL,NULL,59,8,3,25,0
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(65,'Modify Ticket Details',NULL,NULL,64,8,4,30,0
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(66,'List of Tasks',NULL,NULL,64,8,4,35,0,'May 28
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(67,'List of Documents',NULL,NULL,64,8,4,40,0,'Ma
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(68,'List of Folder Records',NULL,NULL,64,8,4,45,
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(69,'Add Folder Record',NULL,NULL,64,8,4,50,0,'Ma
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(70,'Ticket Log History',NULL,NULL,64,8,4,55,0,'M
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(71,'Employees',NULL,NULL,1,21,2,35,0,'May 28 200
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(72,'Overview',NULL,NULL,71,21,3,5,0,'May 28 2004
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(73,'Employee Details',NULL,NULL,71,21,3,10,0,'Ma
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(74,'Add an Employee',NULL,NULL,71,21,3,15,0,'May
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(75,'Modify Employee Details',NULL,NULL,71,21,3,2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(76,'Reports',NULL,NULL,1,14,2,40,0,'May 28 2004 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(77,'Overview',NULL,NULL,76,14,3,5,0,'May 28 2004
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(78,'List of Modules',NULL,NULL,76,14,3,10,0,'May
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(79,'Admin',NULL,NULL,1,9,2,45,0,'May 28 2004 10:
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(80,'List of Users',NULL,NULL,79,9,3,5,0,'May 28 
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(81,'Adding a New User',NULL,NULL,80,9,4,10,0,'Ma
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(82,'Modify User Details',NULL,NULL,80,9,4,15,0,'
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(83,'User Login History',NULL,NULL,80,9,4,20,0,'M
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(84,'Viewpoints of User',NULL,NULL,80,9,4,25,0,'M
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(85,'Add Viewpoint',NULL,NULL,84,9,5,30,0,'May 28
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(86,'Update Viewpoint',NULL,NULL,84,9,5,35,0,'May
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(87,'List of Roles',NULL,NULL,79,9,3,40,0,'May 28
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(88,'Add a New Role',NULL,NULL,87,9,4,45,0,'May 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(89,'Update Role',NULL,NULL,87,9,4,50,0,'May 28 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(90,'Configure Modules',NULL,NULL,79,9,3,55,0,'Ma
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(91,'Configuration Options',NULL,NULL,90,9,4,60,0
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(92,'Edit Lookup List',NULL,NULL,91,9,5,65,0,'May
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(93,'Adding a New Folder',NULL,NULL,91,9,5,70,0,'
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(94,'Modify Existing Folder',NULL,NULL,91,9,5,75,
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(95,'Configure System',NULL,NULL,79,9,3,80,0,'May
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(96,'Modify Timeout',NULL,NULL,95,9,4,85,0,'May 2
INSERT [crm].[help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(97,'Resource Usage Details',NULL,NULL,79,9,3,90,

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_tableof_contents] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[help_tableof_contents] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_industry] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_industry] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(1,NULL,'Automotive',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(2,NULL,'Biotechnology',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(3,NULL,'Broadcasting and Cable',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(4,NULL,'Computer',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(5,NULL,'Consulting',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(6,NULL,'Defense',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(7,NULL,'Energy',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(8,NULL,'Financial Services',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(9,NULL,'Food',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(10,NULL,'Healthcare',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(11,NULL,'Hospitality',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(12,NULL,'Insurance',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(13,NULL,'Internet',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(14,NULL,'Law Firms',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(15,NULL,'Media',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(16,NULL,'Pharmaceuticals',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(17,NULL,'Real Estate',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(18,NULL,'Retail',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(19,NULL,'Telecommunications',0,0,1)
INSERT [crm].[lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(20,NULL,'Transportation',0,0,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_industry] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_industry] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[payment_creditcard] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[payment_creditcard] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[payment_creditcard] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[payment_creditcard] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[scheduled_recipient] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[scheduled_recipient] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[scheduled_recipient] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[scheduled_recipient] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[product_category_map] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_category_map] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                             
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_category_map] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[product_category_map] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_boolean] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                         
---------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_boolean] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[ticket_category] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_category] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[ticket_category] ([id],[cat_level],[parent_cat_code],[description],[full_description],[default_item],[level],[enabled])VALUES(1,0,0,'Sales',' ',0,1,1)
INSERT [crm].[ticket_category] ([id],[cat_level],[parent_cat_code],[description],[full_description],[default_item],[level],[enabled])VALUES(2,0,0,'Billing',' ',0,2,1)
INSERT [crm].[ticket_category] ([id],[cat_level],[parent_cat_code],[description],[full_description],[default_item],[level],[enabled])VALUES(3,0,0,'Technical',' ',0,3,1)
INSERT [crm].[ticket_category] ([id],[cat_level],[parent_cat_code],[description],[full_description],[default_item],[level],[enabled])VALUES(4,0,0,'Order',' ',0,4,1)
INSERT [crm].[ticket_category] ([id],[cat_level],[parent_cat_code],[description],[full_description],[default_item],[level],[enabled])VALUES(5,0,0,'Other',' ',0,5,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_category] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[ticket_category] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[access_log] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[access_log] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[access_log] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[access_log] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_project_status] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_project_status] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_project_status] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(1,'Not Started',0,1,1,0,'box.gif',1)
INSERT [crm].[lookup_project_status] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(2,'In Progress',0,2,1,0,'box.gif',2)
INSERT [crm].[lookup_project_status] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(3,'On Hold',0,5,1,0,'box-hold.gif',5)
INSERT [crm].[lookup_project_status] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(4,'Waiting on Reqs',0,6,1,0,'box-hold.gif',5)
INSERT [crm].[lookup_project_status] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(5,'Complete',0,3,1,0,'box-checked.gif',3)
INSERT [crm].[lookup_project_status] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(6,'Closed',0,4,1,0,'box-checked.gif',4)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_project_status] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_project_status] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[autoguide_options] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_options] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(1,'A/T',0,10,0,'May 28 2004 10:25:34:547AM','May 28 2004 10:25:34:547AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(2,'4-CYL',0,20,0,'May 28 2004 10:25:34:557AM','May 28 2004 10:25:34:557AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(3,'6-CYL',0,30,0,'May 28 2004 10:25:34:557AM','May 28 2004 10:25:34:557AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(4,'V-8',0,40,0,'May 28 2004 10:25:34:557AM','May 28 2004 10:25:34:557AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(5,'CRUISE',0,50,0,'May 28 2004 10:25:34:557AM','May 28 2004 10:25:34:557AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(6,'5-SPD',0,60,0,'May 28 2004 10:25:34:557AM','May 28 2004 10:25:34:557AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(7,'4X4',0,70,0,'May 28 2004 10:25:34:557AM','May 28 2004 10:25:34:557AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(8,'2-DOOR',0,80,0,'May 28 2004 10:25:34:557AM','May 28 2004 10:25:34:557AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(9,'4-DOOR',0,90,0,'May 28 2004 10:25:34:557AM','May 28 2004 10:25:34:557AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(10,'LEATHER',0,100,0,'May 28 2004 10:25:34:567AM','May 28 2004 10:25:34:567AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(11,'P/DL',0,110,0,'May 28 2004 10:25:34:567AM','May 28 2004 10:25:34:567AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(12,'T/W',0,120,0,'May 28 2004 10:25:34:567AM','May 28 2004 10:25:34:567AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(13,'P/SEATS',0,130,0,'May 28 2004 10:25:34:567AM','May 28 2004 10:25:34:567AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(14,'P/WIND',0,140,0,'May 28 2004 10:25:34:567AM','May 28 2004 10:25:34:567AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(15,'P/S',0,150,0,'May 28 2004 10:25:34:567AM','May 28 2004 10:25:34:567AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(16,'BEDLINE',0,160,0,'May 28 2004 10:25:34:567AM','May 28 2004 10:25:34:567AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(17,'LOW MILES',0,170,0,'May 28 2004 10:25:34:567AM','May 28 2004 10:25:34:567AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(18,'EX CLEAN',0,180,0,'May 28 2004 10:25:34:577AM','May 28 2004 10:25:34:577AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(19,'LOADED',0,190,0,'May 28 2004 10:25:34:577AM','May 28 2004 10:25:34:577AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(20,'A/C',0,200,0,'May 28 2004 10:25:34:577AM','May 28 2004 10:25:34:577AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(21,'SUNROOF',0,210,0,'May 28 2004 10:25:34:577AM','May 28 2004 10:25:34:577AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(22,'AM/FM ST',0,220,0,'May 28 2004 10:25:34:577AM','May 28 2004 10:25:34:577AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(23,'CASS',0,225,0,'May 28 2004 10:25:34:577AM','May 28 2004 10:25:34:577AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(24,'CD PLYR',0,230,0,'May 28 2004 10:25:34:577AM','May 28 2004 10:25:34:577AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(25,'ABS',0,240,0,'May 28 2004 10:25:34:577AM','May 28 2004 10:25:34:577AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(26,'ALARM',0,250,0,'May 28 2004 10:25:34:587AM','May 28 2004 10:25:34:587AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(27,'SLD R. WIN',0,260,0,'May 28 2004 10:25:34:587AM','May 28 2004 10:25:34:587AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(28,'AIRBAG',0,270,0,'May 28 2004 10:25:34:587AM','May 28 2004 10:25:34:587AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(29,'1 OWNER',0,280,0,'May 28 2004 10:25:34:587AM','May 28 2004 10:25:34:587AM')
INSERT [crm].[autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(30,'ALLOY WH',0,290,0,'May 28 2004 10:25:34:587AM','May 28 2004 10:25:34:587AM')

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_options] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[autoguide_options] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_float] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                       
-------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_float] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product_option_boolean] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                     
---------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product_option_boolean] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[report] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[report] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(1,1,NULL,'accounts_type.xml',1,'Accounts by Type','What are my accounts by type
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(2,1,NULL,'accounts_recent.xml',1,'Accounts by Date Added','What are my recent a
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(3,1,NULL,'accounts_expire.xml',1,'Accounts by Contract End Date','Which account
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(4,1,NULL,'accounts_current.xml',1,'Current Accounts','What are my current accou
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(5,1,NULL,'accounts_contacts.xml',1,'Account Contacts','Who are the contacts in 
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(6,1,NULL,'folder_accounts.xml',1,'Account Folders','What is the folder data for
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(7,2,NULL,'contacts_user.xml',1,'Contacts','Who are my contacts?','May 28 2004 1
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(8,4,NULL,'opportunity_pipeline.xml',1,'Opportunities by Stage','What are my upc
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(9,4,NULL,'opportunity_account.xml',1,'Opportunities by Account','What are all t
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(10,4,NULL,'opportunity_owner.xml',1,'Opportunities by Owner','What are all the 
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(11,4,NULL,'opportunity_contact.xml',1,'Opportunity Contacts','Who are the conta
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(12,6,NULL,'campaign.xml',1,'Campaigns by date','What are my active campaigns?',
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(13,8,NULL,'tickets_department.xml',1,'Tickets by Department','What tickets are 
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(14,8,NULL,'ticket_summary_date.xml',1,'Ticket counts by Department','How many t
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(15,8,NULL,'ticket_summary_range.xml',1,'Ticket activity by Department','How man
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(16,8,NULL,'open_calls_report.xml',1,'Open Calls','Which tickets are open?','May
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(17,8,NULL,'contract_review_report.xml',1,'Contract Review','What is the expirat
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(18,8,NULL,'call_history_report.xml',1,'Call History','How have tickets been res
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(19,8,NULL,'assets_under_contract_report.xml',1,'Assets Under Contract','Which a
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(20,8,NULL,'activity_log_report.xml',1,'Contract Activity Summary','What is the 
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(21,8,NULL,'callvolume_day_assignee.xml',1,'Call Volume by Assignee per Day','Ho
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(22,8,NULL,'callvolume_month_assignee.xml',1,'Call Volume by Assignee per Month'
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(23,8,NULL,'callvolume_day_cat.xml',1,'Call Volume by Category per Day','How man
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(24,8,NULL,'callvolume_month_cat.xml',1,'Call Volume by Category per Month','How
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(25,8,NULL,'callvolume_day_enteredby.xml',1,'Call Volume by User Entered per Day
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(26,8,NULL,'callvolume_month_ent.xml',1,'Call Volume by User Entered per Month',
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(27,9,NULL,'users.xml',1,'System Users','Who are all the users of the system?','
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(28,12,NULL,'task_date.xml',1,'Task list by due date','What are the tasks due wi
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(29,12,NULL,'task_nodate.xml',1,'Task list','What are all the tasks in the syste
INSERT [crm].[report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(30,21,NULL,'employees.xml',1,'Employees','Who are the employees in my organizat

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[report] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[report] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_product_type] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_type] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_type] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_product_type] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_timestamp] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                             
-------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_timestamp] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[payment_eft] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[payment_eft] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[payment_eft] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[payment_eft] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product_option_float] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                   
-------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product_option_float] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[usage_log] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[usage_log] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[usage_log] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[usage_log] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_integer] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                         
---------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_integer] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product_option_timestamp] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                         
-------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product_option_timestamp] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[help_tableofcontentitem_links] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_tableofcontentitem_links] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(1,3,1,0,'May 28 2004 10:25:54:303AM',0,'May 28 2004 10:25:54:303AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(2,4,2,0,'May 28 2004 10:25:54:313AM',0,'May 28 2004 10:25:54:313AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(3,5,3,0,'May 28 2004 10:25:54:323AM',0,'May 28 2004 10:25:54:323AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(4,6,4,0,'May 28 2004 10:25:54:333AM',0,'May 28 2004 10:25:54:333AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(5,7,5,0,'May 28 2004 10:25:54:343AM',0,'May 28 2004 10:25:54:343AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(6,8,6,0,'May 28 2004 10:25:54:353AM',0,'May 28 2004 10:25:54:353AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(7,9,7,0,'May 28 2004 10:25:54:353AM',0,'May 28 2004 10:25:54:353AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(8,10,8,0,'May 28 2004 10:25:54:363AM',0,'May 28 2004 10:25:54:363AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(9,11,9,0,'May 28 2004 10:25:54:373AM',0,'May 28 2004 10:25:54:373AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(10,12,10,0,'May 28 2004 10:25:54:383AM',0,'May 28 2004 10:25:54:383AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(11,13,11,0,'May 28 2004 10:25:54:393AM',0,'May 28 2004 10:25:54:393AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(12,14,12,0,'May 28 2004 10:25:54:403AM',0,'May 28 2004 10:25:54:403AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(13,15,13,0,'May 28 2004 10:25:54:403AM',0,'May 28 2004 10:25:54:403AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(14,16,14,0,'May 28 2004 10:25:54:413AM',0,'May 28 2004 10:25:54:413AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(15,17,15,0,'May 28 2004 10:25:54:423AM',0,'May 28 2004 10:25:54:423AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(16,18,16,0,'May 28 2004 10:25:54:423AM',0,'May 28 2004 10:25:54:423AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(17,19,17,0,'May 28 2004 10:25:54:433AM',0,'May 28 2004 10:25:54:433AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(18,20,18,0,'May 28 2004 10:25:54:443AM',0,'May 28 2004 10:25:54:443AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(19,21,19,0,'May 28 2004 10:25:54:453AM',0,'May 28 2004 10:25:54:453AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(20,22,20,0,'May 28 2004 10:25:54:463AM',0,'May 28 2004 10:25:54:463AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(21,24,34,0,'May 28 2004 10:25:54:503AM',0,'May 28 2004 10:25:54:503AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(22,25,35,0,'May 28 2004 10:25:54:513AM',0,'May 28 2004 10:25:54:513AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(23,26,36,0,'May 28 2004 10:25:54:523AM',0,'May 28 2004 10:25:54:523AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(24,27,37,0,'May 28 2004 10:25:54:627AM',0,'May 28 2004 10:25:54:627AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(25,29,79,0,'May 28 2004 10:25:54:637AM',0,'May 28 2004 10:25:54:637AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(26,30,80,0,'May 28 2004 10:25:54:647AM',0,'May 28 2004 10:25:54:647AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(27,31,81,0,'May 28 2004 10:25:54:657AM',0,'May 28 2004 10:25:54:657AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(28,32,82,0,'May 28 2004 10:25:54:657AM',0,'May 28 2004 10:25:54:657AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(29,34,112,0,'May 28 2004 10:25:54:677AM',0,'May 28 2004 10:25:54:677AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(30,35,113,0,'May 28 2004 10:25:54:687AM',0,'May 28 2004 10:25:54:687AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(31,36,114,0,'May 28 2004 10:25:54:687AM',0,'May 28 2004 10:25:54:687AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(32,37,115,0,'May 28 2004 10:25:54:697AM',0,'May 28 2004 10:25:54:697AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(33,38,116,0,'May 28 2004 10:25:54:707AM',0,'May 28 2004 10:25:54:707AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(34,39,117,0,'May 28 2004 10:25:54:707AM',0,'May 28 2004 10:25:54:707AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(35,40,118,0,'May 28 2004 10:25:54:727AM',0,'May 28 2004 10:25:54:727AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(36,41,119,0,'May 28 2004 10:25:54:727AM',0,'May 28 2004 10:25:54:727AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(37,42,120,0,'May 28 2004 10:25:54:737AM',0,'May 28 2004 10:25:54:737AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(38,43,121,0,'May 28 2004 10:25:54:747AM',0,'May 28 2004 10:25:54:747AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(39,44,122,0,'May 28 2004 10:25:54:747AM',0,'May 28 2004 10:25:54:747AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(40,45,123,0,'May 28 2004 10:25:54:757AM',0,'May 28 2004 10:25:54:757AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(41,46,124,0,'May 28 2004 10:25:54:767AM',0,'May 28 2004 10:25:54:767AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(42,47,125,0,'May 28 2004 10:25:54:777AM',0,'May 28 2004 10:25:54:777AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(43,48,126,0,'May 28 2004 10:25:54:787AM',0,'May 28 2004 10:25:54:787AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(44,49,127,0,'May 28 2004 10:25:54:797AM',0,'May 28 2004 10:25:54:797AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(45,51,190,0,'May 28 2004 10:25:54:807AM',0,'May 28 2004 10:25:54:807AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(46,52,191,0,'May 28 2004 10:25:54:817AM',0,'May 28 2004 10:25:54:817AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(47,53,192,0,'May 28 2004 10:25:54:917AM',0,'May 28 2004 10:25:54:917AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(48,54,193,0,'May 28 2004 10:25:54:917AM',0,'May 28 2004 10:25:54:917AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(49,55,194,0,'May 28 2004 10:25:54:927AM',0,'May 28 2004 10:25:54:927AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(50,56,195,0,'May 28 2004 10:25:54:937AM',0,'May 28 2004 10:25:54:937AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(51,57,196,0,'May 28 2004 10:25:54:987AM',0,'May 28 2004 10:25:54:987AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(52,58,197,0,'May 28 2004 10:25:54:997AM',0,'May 28 2004 10:25:54:997AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(53,60,240,0,'May 28 2004 10:25:55:007AM',0,'May 28 2004 10:25:55:007AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(54,61,241,0,'May 28 2004 10:25:55:017AM',0,'May 28 2004 10:25:55:017AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(55,62,242,0,'May 28 2004 10:25:55:027AM',0,'May 28 2004 10:25:55:027AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(56,63,243,0,'May 28 2004 10:25:55:027AM',0,'May 28 2004 10:25:55:027AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(57,64,244,0,'May 28 2004 10:25:55:037AM',0,'May 28 2004 10:25:55:037AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(58,65,245,0,'May 28 2004 10:25:55:047AM',0,'May 28 2004 10:25:55:047AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(59,66,246,0,'May 28 2004 10:25:55:057AM',0,'May 28 2004 10:25:55:057AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(60,67,247,0,'May 28 2004 10:25:55:067AM',0,'May 28 2004 10:25:55:067AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(61,68,248,0,'May 28 2004 10:25:55:067AM',0,'May 28 2004 10:25:55:067AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(62,69,249,0,'May 28 2004 10:25:55:077AM',0,'May 28 2004 10:25:55:077AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(63,70,250,0,'May 28 2004 10:25:55:087AM',0,'May 28 2004 10:25:55:087AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(64,72,274,0,'May 28 2004 10:25:55:097AM',0,'May 28 2004 10:25:55:097AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(65,73,275,0,'May 28 2004 10:25:55:107AM',0,'May 28 2004 10:25:55:107AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(66,74,276,0,'May 28 2004 10:25:55:117AM',0,'May 28 2004 10:25:55:117AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(67,75,277,0,'May 28 2004 10:25:55:117AM',0,'May 28 2004 10:25:55:117AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(68,77,279,0,'May 28 2004 10:25:55:137AM',0,'May 28 2004 10:25:55:137AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(69,78,280,0,'May 28 2004 10:25:55:137AM',0,'May 28 2004 10:25:55:137AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(70,80,288,0,'May 28 2004 10:25:55:247AM',0,'May 28 2004 10:25:55:247AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(71,81,289,0,'May 28 2004 10:25:55:257AM',0,'May 28 2004 10:25:55:257AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(72,82,290,0,'May 28 2004 10:25:55:257AM',0,'May 28 2004 10:25:55:257AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(73,83,291,0,'May 28 2004 10:25:55:267AM',0,'May 28 2004 10:25:55:267AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(74,84,292,0,'May 28 2004 10:25:55:277AM',0,'May 28 2004 10:25:55:277AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(75,85,293,0,'May 28 2004 10:25:55:277AM',0,'May 28 2004 10:25:55:277AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(76,86,294,0,'May 28 2004 10:25:55:297AM',0,'May 28 2004 10:25:55:297AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(77,87,295,0,'May 28 2004 10:25:55:297AM',0,'May 28 2004 10:25:55:297AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(78,88,296,0,'May 28 2004 10:25:55:307AM',0,'May 28 2004 10:25:55:307AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(79,89,297,0,'May 28 2004 10:25:55:317AM',0,'May 28 2004 10:25:55:317AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(80,90,298,0,'May 28 2004 10:25:55:317AM',0,'May 28 2004 10:25:55:317AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(81,91,299,0,'May 28 2004 10:25:55:327AM',0,'May 28 2004 10:25:55:327AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(82,92,300,0,'May 28 2004 10:25:55:337AM',0,'May 28 2004 10:25:55:337AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(83,93,301,0,'May 28 2004 10:25:55:347AM',0,'May 28 2004 10:25:55:347AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(84,94,302,0,'May 28 2004 10:25:55:357AM',0,'May 28 2004 10:25:55:357AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(85,95,303,0,'May 28 2004 10:25:55:357AM',0,'May 28 2004 10:25:55:357AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(86,96,304,0,'May 28 2004 10:25:55:367AM',0,'May 28 2004 10:25:55:367AM',1)
INSERT [crm].[help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(87,97,305,0,'May 28 2004 10:25:55:377AM',0,'May 28 2004 10:25:55:377AM',1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_tableofcontentitem_links] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[help_tableofcontentitem_links] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_project_loe] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_project_loe] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_project_loe] ([code],[description],[base_value],[default_item],[level],[enabled],[group_id])VALUES(1,'Minute(s)',60,0,1,1,0)
INSERT [crm].[lookup_project_loe] ([code],[description],[base_value],[default_item],[level],[enabled],[group_id])VALUES(2,'Hour(s)',3600,1,1,1,0)
INSERT [crm].[lookup_project_loe] ([code],[description],[base_value],[default_item],[level],[enabled],[group_id])VALUES(3,'Day(s)',86400,0,1,1,0)
INSERT [crm].[lookup_project_loe] ([code],[description],[base_value],[default_item],[level],[enabled],[group_id])VALUES(4,'Week(s)',604800,0,1,1,0)
INSERT [crm].[lookup_project_loe] ([code],[description],[base_value],[default_item],[level],[enabled],[group_id])VALUES(5,'Month(s)',18144000,0,1,1,0)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_project_loe] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_project_loe] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[ticket_category_draft] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_category_draft] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_category_draft] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[ticket_category_draft] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[role_permission] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[role_permission] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(1,1,72,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(2,1,73,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(3,1,74,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(4,1,75,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(5,1,76,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(6,1,78,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(7,1,79,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(8,1,80,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(9,1,81,0,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(10,1,77,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(11,1,26,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(12,1,27,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(13,1,1,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(14,1,2,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(15,1,3,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(16,1,4,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(17,1,5,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(18,1,6,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(19,1,7,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(20,1,8,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(21,1,9,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(22,1,10,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(23,1,11,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(24,1,12,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(25,1,13,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(26,1,14,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(27,1,18,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(28,1,19,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(29,1,20,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(30,1,21,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(31,1,22,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(32,1,43,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(33,1,44,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(34,1,45,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(35,1,46,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(36,1,47,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(37,1,48,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(38,1,53,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(39,1,54,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(40,1,55,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(41,1,56,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(42,1,57,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(43,1,58,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(44,1,90,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(45,1,91,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(46,1,84,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(47,1,59,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(48,1,60,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(49,1,61,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(50,1,63,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(51,1,62,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(52,1,64,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(53,1,65,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(54,1,66,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(55,1,67,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(56,1,68,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(57,1,69,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(58,1,70,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(59,1,71,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(60,2,72,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(61,2,73,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(62,2,75,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(63,2,76,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(64,2,77,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(65,2,78,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(66,2,79,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(67,2,80,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(68,2,81,0,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(69,2,82,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(70,2,26,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(71,2,27,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(72,2,28,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(73,2,29,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(74,2,30,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(75,2,31,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(76,2,32,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(77,2,36,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(78,2,37,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(79,2,38,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(80,2,39,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(81,2,40,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(82,2,41,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(83,2,1,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(84,2,2,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(85,2,3,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(86,2,4,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(87,2,5,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(88,2,6,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(89,2,7,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(90,2,8,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(91,2,9,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(92,2,13,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(93,2,14,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(94,2,16,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(95,2,12,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(96,2,11,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(97,2,10,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(98,2,18,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(99,2,19,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(100,2,20,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(101,2,21,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(102,2,43,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(103,2,44,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(104,2,45,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(105,2,46,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(106,2,47,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(107,2,48,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(108,2,53,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(109,2,54,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(110,2,55,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(111,2,56,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(112,2,57,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(113,2,58,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(114,2,90,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(115,2,91,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(116,2,84,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(117,2,68,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(118,2,69,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(119,2,70,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(120,2,71,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(121,3,72,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(122,3,73,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(123,3,75,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(124,3,76,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(125,3,77,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(126,3,78,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(127,3,79,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(128,3,80,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(129,3,81,0,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(130,3,82,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(131,3,26,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(132,3,27,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(133,3,28,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(134,3,29,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(135,3,30,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(136,3,31,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(137,3,32,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(138,3,36,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(139,3,37,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(140,3,38,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(141,3,39,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(142,3,40,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(143,3,41,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(144,3,1,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(145,3,2,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(146,3,3,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(147,3,4,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(148,3,5,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(149,3,6,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(150,3,7,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(151,3,8,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(152,3,9,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(153,3,13,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(154,3,14,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(155,3,15,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(156,3,16,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(157,3,12,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(158,3,11,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(159,3,10,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(160,3,18,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(161,3,19,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(162,3,20,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(163,3,21,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(164,3,43,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(165,3,44,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(166,3,45,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(167,3,46,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(168,3,47,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(169,3,48,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(170,3,53,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(171,3,54,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(172,3,55,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(173,3,56,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(174,3,57,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(175,3,58,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(176,3,90,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(177,3,91,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(178,3,84,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(179,3,68,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(180,3,69,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(181,3,70,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(182,3,71,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(183,4,72,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(184,4,73,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(185,4,75,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(186,4,76,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(187,4,78,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(188,4,79,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(189,4,80,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(190,4,81,0,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(191,4,82,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(192,4,26,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(193,4,27,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(194,4,28,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(195,4,30,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(196,4,31,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(197,4,32,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(198,4,36,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(199,4,37,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(200,4,38,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(201,4,39,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(202,4,40,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(203,4,41,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(204,4,1,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(205,4,2,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(206,4,3,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(207,4,4,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(208,4,5,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(209,4,6,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(210,4,7,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(211,4,8,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(212,4,9,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(213,4,13,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(214,4,14,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(215,4,15,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(216,4,16,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(217,4,12,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(218,4,11,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(219,4,10,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(220,4,18,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(221,4,19,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(222,4,20,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(223,4,21,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(224,4,43,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(225,4,44,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(226,4,45,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(227,4,46,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(228,4,47,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(229,4,48,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(230,4,53,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(231,4,54,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(232,4,55,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(233,4,56,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(234,4,57,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(235,4,58,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(236,4,90,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(237,4,91,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(238,4,84,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(239,4,68,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(240,4,69,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(241,4,70,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(242,4,71,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(243,5,72,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(244,5,73,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(245,5,75,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(246,5,76,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(247,5,77,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(248,5,78,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(249,5,79,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(250,5,80,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(251,5,81,0,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(252,5,1,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(253,5,2,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(254,5,3,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(255,5,4,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(256,5,6,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(257,5,7,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(258,5,9,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(259,5,13,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(260,5,14,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(261,5,12,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(262,5,11,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(263,5,10,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(264,5,18,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(265,5,19,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(266,5,20,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(267,5,21,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(268,5,43,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(269,5,44,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(270,5,45,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(271,5,46,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(272,5,47,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(273,5,48,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(274,5,53,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(275,5,54,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(276,5,55,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(277,5,56,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(278,5,57,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(279,5,58,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(280,5,90,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(281,5,91,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(282,5,84,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(283,5,68,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(284,5,69,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(285,5,70,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(286,5,71,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(287,6,72,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(288,6,73,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(289,6,75,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(290,6,76,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(291,6,77,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(292,6,78,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(293,6,79,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(294,6,80,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(295,6,81,0,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(296,6,1,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(297,6,2,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(298,6,3,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(299,6,4,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(300,6,6,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(301,6,7,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(302,6,9,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(303,6,13,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(304,6,14,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(305,6,12,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(306,6,11,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(307,6,10,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(308,6,18,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(309,6,19,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(310,6,20,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(311,6,21,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(312,6,43,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(313,6,44,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(314,6,45,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(315,6,46,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(316,6,47,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(317,6,48,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(318,6,53,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(319,6,54,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(320,6,55,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(321,6,56,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(322,6,57,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(323,6,58,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(324,6,90,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(325,6,91,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(326,6,84,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(327,6,68,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(328,6,69,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(329,6,70,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(330,6,71,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(331,7,72,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(332,7,73,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(333,7,75,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(334,7,76,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(335,7,77,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(336,7,78,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(337,7,79,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(338,7,80,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(339,7,81,0,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(340,7,82,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(341,7,26,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(342,7,27,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(343,7,28,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(344,7,29,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(345,7,30,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(346,7,31,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(347,7,32,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(348,7,36,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(349,7,37,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(350,7,38,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(351,7,39,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(352,7,40,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(353,7,41,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(354,7,1,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(355,7,2,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(356,7,3,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(357,7,4,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(358,7,5,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(359,7,6,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(360,7,7,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(361,7,8,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(362,7,9,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(363,7,13,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(364,7,14,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(365,7,15,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(366,7,16,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(367,7,12,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(368,7,11,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(369,7,10,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(370,7,18,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(371,7,19,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(372,7,20,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(373,7,21,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(374,7,43,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(375,7,44,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(376,7,45,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(377,7,46,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(378,7,47,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(379,7,48,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(380,7,53,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(381,7,54,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(382,7,55,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(383,7,56,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(384,7,57,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(385,7,58,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(386,7,90,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(387,7,91,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(388,7,84,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(389,7,68,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(390,7,69,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(391,7,70,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(392,7,71,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(393,8,72,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(394,8,73,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(395,8,75,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(396,8,76,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(397,8,77,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(398,8,78,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(399,8,79,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(400,8,80,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(401,8,81,0,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(402,8,26,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(403,8,27,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(404,8,28,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(405,8,29,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(406,8,30,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(407,8,31,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(408,8,32,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(409,8,36,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(410,8,37,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(411,8,38,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(412,8,39,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(413,8,40,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(414,8,41,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(415,8,1,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(416,8,2,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(417,8,3,1,1,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(418,8,4,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(419,8,6,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(420,8,7,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(421,8,8,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(422,8,9,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(423,8,13,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(424,8,14,1,1,0,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(425,8,16,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(426,8,12,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(427,8,11,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(428,8,10,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(429,8,18,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(430,8,19,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(431,8,20,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(432,8,21,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(433,8,43,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(434,8,44,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(435,8,45,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(436,8,46,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(437,8,47,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(438,8,48,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(439,8,53,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(440,8,54,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(441,8,55,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(442,8,56,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(443,8,57,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(444,8,58,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(445,8,90,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(446,8,91,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(447,8,84,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(448,8,68,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(449,8,69,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(450,8,70,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(451,8,71,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(452,9,72,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(453,9,73,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(454,9,75,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(455,9,76,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(456,9,77,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(457,9,78,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(458,9,79,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(459,9,80,1,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(460,9,81,0,0,1,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(461,9,43,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(462,9,44,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(463,9,45,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(464,9,46,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(465,9,47,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(466,9,48,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(467,9,90,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(468,9,91,1,1,1,1)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(469,9,84,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(470,9,68,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(471,9,69,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(472,9,70,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(473,9,71,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(474,10,1,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(475,10,2,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(476,10,4,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(477,10,18,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(478,10,19,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(479,10,20,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(480,10,21,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(481,10,4,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(482,10,9,1,1,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(483,11,72,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(484,11,73,1,0,0,0)
INSERT [crm].[role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(485,11,87,1,0,0,0)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[role_permission] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[role_permission] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_text] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_text] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_contact_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_contact_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(1,'Acquaintance',0,0,1,NULL,0)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(2,'Competitor',0,0,1,NULL,0)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(3,'Customer',0,0,1,NULL,0)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(4,'Friend',0,0,1,NULL,0)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(5,'Prospect',0,0,1,NULL,0)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(6,'Shareholder',0,0,1,NULL,0)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(7,'Vendor',0,0,1,NULL,0)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(8,'Accounting',0,0,1,NULL,1)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(9,'Administrative',0,0,1,NULL,1)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(10,'Business Development',0,0,1,NULL,1)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(11,'Customer Service',0,0,1,NULL,1)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(12,'Engineering',0,0,1,NULL,1)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(13,'Executive',0,0,1,NULL,1)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(14,'Finance',0,0,1,NULL,1)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(15,'Marketing',0,0,1,NULL,1)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(16,'Operations',0,0,1,NULL,1)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(17,'Procurement',0,0,1,NULL,1)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(18,'Sales',0,0,1,NULL,1)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(19,'Shipping/Receiving',0,0,1,NULL,1)
INSERT [crm].[lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(20,'Technology',0,0,1,NULL,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_contact_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_contact_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_product_format] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_format] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_format] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_product_format] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_log] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_log] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product_option_integer] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                     
---------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product_option_integer] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_inventory_options] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                             
-------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_inventory_options] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_product_keyword] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_keyword] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_keyword] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_product_keyword] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_survey_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_survey_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_survey_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Open-Ended',0,0,1)
INSERT [crm].[lookup_survey_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Quantitative (no comments)',0,0,1)
INSERT [crm].[lookup_survey_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Quantitative (with comments)',0,0,1)
INSERT [crm].[lookup_survey_types] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Item List',0,0,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_survey_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_survey_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[business_process_hook_library] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_hook_library] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[business_process_hook_library] ([hook_id],[link_module_id],[hook_class],[enabled])VALUES(1,8,'org.aspcfs.modules.troubletickets.base.Ticket',1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_hook_library] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[business_process_hook_library] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product_option_text] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product_option_text] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[autoguide_ad_run] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_ad_run] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_ad_run] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[autoguide_ad_run] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[customer_product] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[customer_product] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[customer_product] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[customer_product] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_order_status] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_order_status] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_order_status] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Pending',0,0,1)
INSERT [crm].[lookup_order_status] ([code],[description],[default_item],[level],[enabled])VALUES(2,'In Progress',0,0,1)
INSERT [crm].[lookup_order_status] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Cancelled',0,0,1)
INSERT [crm].[lookup_order_status] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Rejected',0,0,1)
INSERT [crm].[lookup_order_status] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Complete',0,0,1)
INSERT [crm].[lookup_order_status] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Closed',0,0,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_order_status] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_order_status] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_product_shipping] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_shipping] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_shipping] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_product_shipping] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[report_criteria] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[report_criteria] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[report_criteria] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[report_criteria] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[projects] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[projects] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[projects] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[projects] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[business_process_hook_triggers] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_hook_triggers] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                     
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 
INSERT [crm].[business_process_hook_triggers] ([trigger_id],[action_type_id],[hook_id],[enabled])VALUES(1,2,1,1)
INSERT [crm].[business_process_hook_triggers] ([trigger_id],[action_type_id],[hook_id],[enabled])VALUES(2,1,1,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_hook_triggers] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[business_process_hook_triggers] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_keyword_map] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                    
----------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_keyword_map] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_account_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_account_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Small',0,10,1)
INSERT [crm].[lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Medium',0,20,1)
INSERT [crm].[lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Large',0,30,1)
INSERT [crm].[lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Contract',0,40,1)
INSERT [crm].[lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Territory 1: Northeast',0,50,1)
INSERT [crm].[lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Territory 2: Southeast',0,60,1)
INSERT [crm].[lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(7,'Territory 3: Midwest',0,70,1)
INSERT [crm].[lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(8,'Territory 4: Northwest',0,80,1)
INSERT [crm].[lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(9,'Territory 5: Southwest',0,90,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_account_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_account_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[survey] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[survey] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[survey] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[survey] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[ticket] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[ticket] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_stage] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_stage] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(1,1,'Prospecting',0,1,1)
INSERT [crm].[lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(2,2,'Qualification',0,2,1)
INSERT [crm].[lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(3,3,'Needs Analysis',0,3,1)
INSERT [crm].[lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(4,4,'Value Proposition',0,4,1)
INSERT [crm].[lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(5,5,'Perception Analysis',0,5,1)
INSERT [crm].[lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(6,6,'Proposal/Price Quote',0,6,1)
INSERT [crm].[lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(7,7,'Negotiation/Review',0,7,1)
INSERT [crm].[lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(8,8,'Closed Won',0,8,1)
INSERT [crm].[lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(9,9,'Closed Lost',0,9,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_stage] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_stage] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_help_features] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_help_features] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_help_features] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_help_features] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_order_type] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_order_type] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(1,'New',0,0,1)
INSERT [crm].[lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Change',0,0,1)
INSERT [crm].[lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Upgrade',0,0,1)
INSERT [crm].[lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Downgrade',0,0,1)
INSERT [crm].[lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Disconnect',0,0,1)
INSERT [crm].[lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Move',0,0,1)
INSERT [crm].[lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(7,'Return',0,0,1)
INSERT [crm].[lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(8,'Suspend',0,0,1)
INSERT [crm].[lookup_order_type] ([code],[description],[default_item],[level],[enabled])VALUES(9,'Unsuspend',0,0,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_order_type] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_order_type] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[autoguide_ad_run_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_ad_run_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[autoguide_ad_run_types] ([code],[description],[default_item],[level],[enabled],[entered],[modified])VALUES(1,'In Column',0,1,1,'May 28 2004 10:25:34:587AM','May 28 2004 10:25:34:587AM')
INSERT [crm].[autoguide_ad_run_types] ([code],[description],[default_item],[level],[enabled],[entered],[modified])VALUES(2,'Display',0,2,1,'May 28 2004 10:25:34:597AM','May 28 2004 10:25:34:597AM')
INSERT [crm].[autoguide_ad_run_types] ([code],[description],[default_item],[level],[enabled],[entered],[modified])VALUES(3,'Both',0,3,1,'May 28 2004 10:25:34:597AM','May 28 2004 10:25:34:597AM')

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_ad_run_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[autoguide_ad_run_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_asset_status] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_asset_status] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_asset_status] ([code],[description],[default_item],[level],[enabled])VALUES(1,'In use',0,10,1)
INSERT [crm].[lookup_asset_status] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Not in use',0,20,1)
INSERT [crm].[lookup_asset_status] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Requires maintenance',0,30,1)
INSERT [crm].[lookup_asset_status] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Retired',0,40,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_asset_status] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_asset_status] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_product_ship_time] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_ship_time] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_ship_time] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_product_ship_time] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[business_process_hook] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_hook] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                   
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 
INSERT [crm].[business_process_hook] ([id],[trigger_id],[process_id],[enabled])VALUES(1,1,1,1)
INSERT [crm].[business_process_hook] ([id],[trigger_id],[process_id],[enabled])VALUES(2,2,1,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_hook] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[business_process_hook] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[state] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[state] ([state_code],[state])VALUES('AK','Alaska')
INSERT [crm].[state] ([state_code],[state])VALUES('AL','Alabama')
INSERT [crm].[state] ([state_code],[state])VALUES('AR','Arkansas')
INSERT [crm].[state] ([state_code],[state])VALUES('AZ','Arizona')
INSERT [crm].[state] ([state_code],[state])VALUES('CA','California')
INSERT [crm].[state] ([state_code],[state])VALUES('CO','Colorado')
INSERT [crm].[state] ([state_code],[state])VALUES('CT','Connecticut')
INSERT [crm].[state] ([state_code],[state])VALUES('DC','Washington D.C.')
INSERT [crm].[state] ([state_code],[state])VALUES('DE','Delaware')
INSERT [crm].[state] ([state_code],[state])VALUES('FL','Florida')
INSERT [crm].[state] ([state_code],[state])VALUES('GA','Georgia')
INSERT [crm].[state] ([state_code],[state])VALUES('HI','Hawaii')
INSERT [crm].[state] ([state_code],[state])VALUES('ID','Idaho')
INSERT [crm].[state] ([state_code],[state])VALUES('IL','Illinois')
INSERT [crm].[state] ([state_code],[state])VALUES('IN','Indiana')
INSERT [crm].[state] ([state_code],[state])VALUES('KS','Kansas')
INSERT [crm].[state] ([state_code],[state])VALUES('KY','Kentucky')
INSERT [crm].[state] ([state_code],[state])VALUES('LA','Louisiana')
INSERT [crm].[state] ([state_code],[state])VALUES('MA','Massachusetts')
INSERT [crm].[state] ([state_code],[state])VALUES('MD','Maryland')
INSERT [crm].[state] ([state_code],[state])VALUES('ME','Maine')
INSERT [crm].[state] ([state_code],[state])VALUES('MI','Michigan')
INSERT [crm].[state] ([state_code],[state])VALUES('MN','Minnesota')
INSERT [crm].[state] ([state_code],[state])VALUES('MO','Mossouri')
INSERT [crm].[state] ([state_code],[state])VALUES('MS','Mississippi')
INSERT [crm].[state] ([state_code],[state])VALUES('MT','Montana')
INSERT [crm].[state] ([state_code],[state])VALUES('NC','North Carolina')
INSERT [crm].[state] ([state_code],[state])VALUES('ND','North Dakota')
INSERT [crm].[state] ([state_code],[state])VALUES('NE','Nebraska')
INSERT [crm].[state] ([state_code],[state])VALUES('NH','New Hampshire')
INSERT [crm].[state] ([state_code],[state])VALUES('NJ','New Jersey')
INSERT [crm].[state] ([state_code],[state])VALUES('NM','New Mexico')
INSERT [crm].[state] ([state_code],[state])VALUES('NV','Nevada')
INSERT [crm].[state] ([state_code],[state])VALUES('NY','New York')
INSERT [crm].[state] ([state_code],[state])VALUES('OH','Ohio')
INSERT [crm].[state] ([state_code],[state])VALUES('OK','Oklahoma')
INSERT [crm].[state] ([state_code],[state])VALUES('OR','Oregon')
INSERT [crm].[state] ([state_code],[state])VALUES('PA','Pennsylvania')
INSERT [crm].[state] ([state_code],[state])VALUES('RI','Rhode Island')
INSERT [crm].[state] ([state_code],[state])VALUES('SC','South Carolina')
INSERT [crm].[state] ([state_code],[state])VALUES('SD','South Dakota')
INSERT [crm].[state] ([state_code],[state])VALUES('TN','Tennessee')
INSERT [crm].[state] ([state_code],[state])VALUES('TX','Texas')
INSERT [crm].[state] ([state_code],[state])VALUES('UT','Utah')
INSERT [crm].[state] ([state_code],[state])VALUES('VA','Virginia')
INSERT [crm].[state] ([state_code],[state])VALUES('VT','Vermont')
INSERT [crm].[state] ([state_code],[state])VALUES('WA','Washington')
INSERT [crm].[state] ([state_code],[state])VALUES('WI','Wisconsin')
INSERT [crm].[state] ([state_code],[state])VALUES('WV','West Virginia')
INSERT [crm].[state] ([state_code],[state])VALUES('WY','Wyoming')

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[state] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_delivery_options] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_delivery_options] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_delivery_options] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Email only',0,1,1)
INSERT [crm].[lookup_delivery_options] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Fax only',0,2,1)
INSERT [crm].[lookup_delivery_options] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Letter only',0,3,1)
INSERT [crm].[lookup_delivery_options] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Email then Fax',0,4,1)
INSERT [crm].[lookup_delivery_options] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Email then Letter',0,5,1)
INSERT [crm].[lookup_delivery_options] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Email, Fax, then Letter',0,6,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_delivery_options] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_delivery_options] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[help_features] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_features] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(1,1,NULL,'You can view the accounts that need attention',0,'May 28
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(2,1,NULL,'You can make calls with the contact information readily 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(3,1,NULL,'You can view the tasks assigned to you',0,'May 28 2004 1
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(4,1,NULL,'You can view the tickets assigned to you',0,'May 28 2004
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(5,2,NULL,'The select button can be used to view the details, reply
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(6,2,NULL,'You can add a new message',0,'May 28 2004 10:25:47:747AM
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(7,2,NULL,'Clicking on the message will show the details of the mes
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(8,2,NULL,'The drop down can be used to select the messages present
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(9,2,NULL,'Sort on one of the column headers by clicking on the col
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(10,3,NULL,'You can reply, archive, forward or delete each message 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(11,4,NULL,'A new message can be composed either to the contacts or
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(12,5,NULL,'You can send the email by clicking the send button',0,'
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(13,5,NULL,'You can add to the list of recipients by using the link
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(14,5,NULL,'You can click the check box to send an Internet email t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(15,5,NULL,'Type directly in the Body test field to modify the mess
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(16,7,NULL,'You can add the list of recipients by using the link "A
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(17,7,NULL,'You can send the email by clicking the send button',0,'
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(18,7,NULL,'You can edit the message by typing directly in the Body
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(19,8,NULL,'You can add a quick task. This task would have just the
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(20,8,NULL,'For each of the existing tasks, you can view, modify, f
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(21,8,NULL,'You can select to view your tasks or tasks assigned by 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(22,8,NULL,'You can add a detailed (advanced) task, where you can s
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(23,9,NULL,'Link this task to a contact and when you look at the ta
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(24,9,NULL,'Filling in a Due Date will make ths task show up on tha
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(25,9,NULL,'Making the task personal will hide it from your hierarc
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(26,9,NULL,'You can assign a task to people lower than you in your 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(27,9,NULL,'Marking a task as complete will document the task as ha
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(28,10,NULL,'Allows you to forward a task to one or more users of t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(29,10,NULL,'The Subject line is mandatory',0,'May 28 2004 10:25:48
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(30,10,NULL,'You can add more text to the body of the message by ty
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(31,11,NULL,'Due dates will show on the Home Page calendar',0,'May 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(32,11,NULL,'Completing a task will remove it from the task list',0
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(33,11,NULL,'You can assign a task to someone lower than you in you
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(34,11,NULL,'You can Add or Change the contact that this task is li
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(35,12,NULL,'You can also view all the in progress Action Lists, co
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(36,12,NULL,'You can also keep track of the progress of your contac
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(37,12,NULL,'You can add a new Action List with a description and s
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(38,13,NULL,'Clicking on the contact name will give you a pop up wi
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(39,13,NULL,'You can add contacts to the list and also Modify the L
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(40,13,NULL,'For the Action List you can also view all the in progr
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(41,13,NULL,'For each of the contacts you can add a call, opportuni
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(42,14,NULL,'Select where the contacts will come from (General Cont
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(43,14,NULL,'Enter some search text, depending on the Operator you 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(44,14,NULL,'Choose an Operator based on the Field you chose.',0,'M
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(45,14,NULL,'Choose one of the many Field Names on which to base yo
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(46,14,NULL,'You can Add or Remove contacts manually with the Add/R
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(47,14,NULL,'Add your query with the Add button at the bottom of th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(48,14,NULL,'Save the Action List and generate the list of contacts
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(49,15,NULL,'You can check the status checkbox to indicate that the
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(50,15,NULL,'The details of the New Action List can be saved by cli
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(51,16,NULL,'Click Update at the bottom of the page to save your re
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(52,16,NULL,'Choose a User to reassign data from in the top dropdow
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(53,16,NULL,'Select one or more To Users in the To User column to r
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(54,17,NULL,'The location of the employee can be changed, i.e. the 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(55,17,NULL,'You can update your personal information by clicking o
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(56,17,NULL,'You can change your password by clicking on "Change my
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(57,18,NULL,'Save your changes by clicking the Update button at the
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(58,18,NULL,'The only required field is your last name, but you sho
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(59,19,NULL,'The location settings can be changed by selecting the 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(60,20,NULL,'You can update your password by clicking on the update
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(61,21,NULL,'For each of the existing tasks, you can view, modify, 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(62,21,NULL,'You can add a quick task. This task would have just th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(63,21,NULL,'You can add or update a detailed task called advanced 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(64,21,NULL,'Checking the existing task''s check box indicates that
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(65,21,NULL,'You can select to view your tasks or tasks assigned by
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(66,28,NULL,'You can add the new Action List here. Along with descr
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(67,28,NULL,'The second is to define the criteria to generate the l
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(68,31,NULL,'Check new contacts to add them to your list, uncheck e
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(69,31,NULL,'Select All Contact, My Contacts or Account Contacts fr
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(70,31,NULL,'Finish by clicking Done at the bottom of the page.',0,
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(71,33,NULL,'You can add or update a detailed task called advanced 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(72,34,NULL,'You can select the contact category using the radio bu
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(73,34,NULL,'You can save the details about the employee using the 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(74,34,NULL,'The "Save & New" button saves the details of the emplo
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(75,34,NULL,'The only mandatory field is the Last Name, however, it
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(76,34,NULL,'The contact type can be selected using the "select" li
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(77,35,NULL,'If the contact already exists in the system, you can s
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(78,36,NULL,'New export data can be generated by choosing the "Gene
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(79,36,NULL,'Use the dropdown to choose which data to display: the 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(80,36,NULL,'The exported data can be viewed in html format by clic
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(81,37,NULL,'You can add all the fields or add / delete single fiel
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(82,37,NULL,'Use the Up and Down buttons on the right to sort the f
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(83,37,NULL,'The Subject is mandatory. Select which set of contacts
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(84,37,NULL,'Click the Generate button when you are ready to genera
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(85,38,NULL,'You can update, cancel or reset the details of the con
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(86,41,NULL,'You can also click on the select button under the acti
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(87,41,NULL,'Clicking on the subject of the call will give complete
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(88,41,NULL,'You can add a call associated with a contact.',0,'May 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(89,42,NULL,'The save button lets you create a new call which is as
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(90,45,NULL,'You can click the select button under the action colum
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(91,45,NULL,'Clicking on the name of the opportunity will show a de
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(92,45,NULL,'Choosing the different types of opportunities from the
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(93,45,NULL,'Add an opportunity associated with a contact.',0,'May 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(94,46,NULL,'You can modify, delete or forward each call using the 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(95,47,NULL,'The component type can be selected using the "select" 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(96,47,NULL,'You can assign the component to any of the employees p
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(97,48,NULL,'An opportunity can be renamed or deleted using the but
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(98,48,NULL,'Clicking on the select button lets you view, modify or
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(99,48,NULL,'Clicking on the name of the component shows the detail
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(100,48,NULL,'Add a new component associated with the contact.',0,'
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(101,49,NULL,'You can modify or delete the opportunity using the mo
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(102,50,NULL,'The type of the call can be selected using the drop d
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(103,58,NULL,'You can click the attachments or the surveys link pre
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(104,59,NULL,'You can modify or delete the opportunity using the mo
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(105,60,NULL,'You can also click the select button under the action
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(106,60,NULL,'Clicking the name of the contact will display additio
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(107,60,NULL,'Add a contact using the link "Add a Contact" at the t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(108,61,NULL,'You can also choose to display the list of all the ex
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(109,61,NULL,'The exported data can be viewed as a .csv file or in 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(110,61,NULL,'New export data can be generated, which lets you choo
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(111,62,NULL,'You can modify, clone, or delete the details of the c
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(112,63,NULL,'Clicking on the name of the message displays more det
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(113,63,NULL,'You can view the messages in two different views, i.e
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(114,64,NULL,'You can select the list of the recipients to whom you
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(115,66,NULL,'The component type can be selected using the "select"
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(116,66,NULL,'You can assign the component to any user using the dr
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(117,67,NULL,'You can update or cancel the information changed usin
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(118,68,NULL,'The folders can be selected using the drop down list.
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(119,68,NULL,'You can click on the record name, to view the folder 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(120,68,NULL,'You can view the details and modify them by clicking 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(121,68,NULL,'You can add a new record to a folder using the "Add a
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(122,68,NULL,'The folders can be selected using the drop down list.
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(123,69,NULL,'The changes made in the details of the folders can be
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(124,70,NULL,'You can also click the select button under the action
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(125,70,NULL,'Clicking on the name of the opportunity will display 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(126,70,NULL,'Choosing the different types of opportunities from th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(127,70,NULL,'Add an opportunity associated with a contact.',0,'May
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(128,71,NULL,'You can filter the contact list in three different vi
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(129,71,NULL,'Check any or all the contacts from the list you want 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(130,72,NULL,'You can also view, modify, clone or delete the contac
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(131,72,NULL,'You can associate calls, messages and opportunities w
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(132,73,NULL,'You can view all the messages related to the contact 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(133,74,NULL,'This is for adding or updating a new detailed employe
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(134,75,NULL,'If the contact already exists in the system, you can 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(135,75,NULL,'You can filter, export, and display data in different
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(136,75,NULL,'Click Add to add a new contact into the application.'
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(137,75,NULL,'You may also import your existing contacts from micro
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(138,77,NULL,'This page is four sections.',0,'May 28 2004 10:25:49:
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(139,77,NULL,'The ''Import Properties" section displays the file th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(140,77,NULL,'The next section displays the heading of the import f
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(141,77,NULL,'The "general errors/warnings" section displays errors
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(142,77,NULL,'The "Field Mappings" section lists all the columns in
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(143,77,NULL,'Against each listed column heading in the field mappi
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(144,78,NULL,'An import goes though various stages before finally b
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(145,78,NULL,'The first stage of an import is the unprocessed stage
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(146,78,NULL,'In the second stage, the unprocessed contacts file th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(147,78,NULL,'When an import is being processed (or running) the CR
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(148,78,NULL,'When the contacts are tagged as "Pending approval",  
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(149,78,NULL,'During the import process, some records in the cvs fi
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(150,78,NULL,'When an import is approved, the contacts of this impo
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(151,78,NULL,'When a import is approved, its results can only be vi
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(152,79,NULL,'You can view the progress chart in different views fo
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(153,79,NULL,'The opportunities created are also shown, with their 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(154,79,NULL,'The list of employees reporting to a particular emplo
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(155,80,NULL,'The probability of Close, Estimated Close Date, Best 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(156,80,NULL,'You can assign the component to yourself or one of th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(157,80,NULL,'The Component Description is a mandatory field. Be de
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(158,80,NULL,'Use the Save button to save your changes and exit, Ca
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(159,80,NULL,'Enter an Alert Description and Date to remind yoursel
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(160,80,NULL,'You must associate the component with either a Contac
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(161,81,NULL,'Existing opportunities can be searched using this fea
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(162,82,NULL,'The exported data can be viewed or downloaded as a .c
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(163,82,NULL,'You can also choose to display the list of all the ex
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(164,82,NULL,'New export data can be generated by choosing from the
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(165,85,NULL,'Component Description is a mandatory field',0,'May 28
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(166,85,NULL,'Use Update at the top or bottom to save your changes,
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(167,85,NULL,'Add an Alert Description and Date to alert you via a 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(168,85,NULL,'Probability of close, Estimated Close Date (when you 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(169,85,NULL,'You can select a Component Type from the dropdown. Th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(170,85,NULL,'You can assign the component to any User using the dr
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(171,86,NULL,'The type of the call can be a phone, fax or in person
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(172,86,NULL,'The Contact dropdown is automatically populated with 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(173,87,NULL,'You can update or cancel the information changed usin
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(174,89,NULL,'You can update the details of the documents using the
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(175,93,NULL,'The component details are shown with additional optio
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(176,94,NULL,'The document can be uploaded using the upload button.
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(177,94,NULL,'The new version of the document can be selected from 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(178,95,NULL,'For each of the component you can view the details, m
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(179,95,NULL,'You can add an opportunity here by giving complete de
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(180,95,NULL,'The search results for existing opportunities are dis
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(181,95,NULL,'There are different views of the opportunities you ca
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(182,96,NULL,'In the Documents tab, documents associated with an op
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(183,96,NULL,'In the Calls tab you can add a call associated with t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(184,96,NULL,'You can rename or delete the opportunity itself using
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(185,96,NULL,'You can modify, view and delete the details of any pa
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(186,96,NULL,'In the Components tab, you can add a component. It al
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(187,96,NULL,'There are three tabs in each opportunity i.e. compone
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(188,96,NULL,'You get the organization name or the contact name at 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(189,97,NULL,'You can select the list of the recipients to whom you
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(190,97,NULL,'You can email a copy of the call to a user''s Interne
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(191,97,NULL,'You can add to the message by simply typing in the Bo
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(192,98,NULL,'The version of the particular document can be modifie
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(193,98,NULL,'A document can be viewed, downloaded, modified or del
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(194,98,NULL,'A click on the subject of the document will show all 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(195,99,NULL,'The type of the call can be selected using the drop d
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(196,100,NULL,'In the Calls tab you can add a call to the opportuni
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(197,100,NULL,'In the Components tab, you can add a component. It a
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(198,100,NULL,'There are three tabs in each opportunity i.e. compon
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(199,100,NULL,'You can rename or delete the whole opportunity (not 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(200,100,NULL,'The organization or contact name appears on top, abo
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(201,100,NULL,'You can modify, view and delete the details of any c
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(202,100,NULL,'In the Documents tab, documents associated with the 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(203,101,NULL,'In the Calls tab you can add a call associated with 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(204,101,NULL,'If the call subject is clicked then complete details
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(205,102,NULL,'There are different views of the opportunities you c
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(206,102,NULL,'You can add an opportunity here by giving complete d
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(207,102,NULL,'The search results for existing opportunities are di
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(208,102,NULL,'For each of the components you can view the details,
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(209,103,NULL,'The list of employees reporting to a particular empl
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(210,103,NULL,'Opportunities are displayed, with name and probable 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(211,103,NULL,'You can view the progress chart in different views f
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(212,104,NULL,'The component details are shown with additional opti
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(213,105,NULL,'The component type can be selected using the "select
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(214,105,NULL,'You can assign the component to any of the employee 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(215,106,NULL,'In the Calls tab you can add a call associated with 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(216,106,NULL,'If the call subject is clicked then it will display 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(217,107,NULL,'You can modify, delete and forward each of the calls
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(218,108,NULL,'Clicking the Upload button will upload the selected 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(219,108,NULL,'Clicking the Browse button opens a file browser on y
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(220,108,NULL,'Add a very descriptive Subject for the file. This is
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(221,109,NULL,'All the versions of the document can be downloaded f
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(222,110,NULL,'The exported data can be viewed as a .csv file or in
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(223,110,NULL,'You can also choose to display the list of all the e
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(224,110,NULL,'New export data can be generated, which lets you cho
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(225,110,NULL,'Click on the subject of the new export, the data is 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(226,111,NULL,'Click the Generate button at top or bottom to genera
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(227,111,NULL,'Highlight the fields you want to include in the left
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(228,111,NULL,'Use the Sorting dropdown to sort the report by one o
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(229,111,NULL,'Use the Criteria dropdown to use opportunities from 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(230,111,NULL,'The Subject is a mandatory field.',0,'May 28 2004 10
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(231,112,NULL,'Clicking on the alert link will let you modify the d
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(232,112,NULL,'Accounts with contract end dates or other required a
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(233,112,NULL,'You can view the schedule, actions, alert dates and 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(234,112,NULL,'You can modify the date range shown in the right han
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(235,113,NULL,'Use the Insert button at top or bottom to save your 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(236,113,NULL,'It''s a faily straightforward "fill in the blanks" e
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(237,113,NULL,'Fill in as many fields as possible. Most of them can
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(238,113,NULL,'Depending on whether you have chosen Organization or
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(239,113,NULL,'Choose whether this account is an Organization or an
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(240,113,NULL,'Clicking the Select link next to Account Type(s) wil
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(241,114,NULL,'The account owner can also be changed using the drop
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(242,114,NULL,'The account type can be selected using the "Select" 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(243,114,NULL,'This is for adding or updating account details. The 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(244,114,NULL,'If the Account has a contract, you should enter a co
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(245,115,NULL,'You can also view, modify, clone and delete the cont
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(246,115,NULL,'When the name of the contact is clicked, it shows de
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(247,115,NULL,'You can add a contact, which is associated with the 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(248,116,NULL,'Using the select button in the action column you can
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(249,116,NULL,'You can click on the record type to view the folders
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(250,116,NULL,'A new record can be added to the folder.',0,'May 28 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(251,116,NULL,'The folders can be populated by configuring the modu
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(252,117,NULL,'Opportunities associated with the contact, showing t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(253,117,NULL,'You can add an opportunity.',0,'May 28 2004 10:25:50
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(254,117,NULL,'Three types of opportunities are present which can b
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(255,117,NULL,'When the description of the opportunity is clicked, 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(256,118,NULL,'By clicking on the description of the revenue you ge
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(257,118,NULL,'You can view your revenue or all the revenues associ
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(258,118,NULL,'You can also view, modify and delete the details of 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(259,118,NULL,'Add / update a new revenue associated with the accou
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(260,119,NULL,'Clicking on the description of the revenue displays 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(261,119,NULL,'You can view your revenue or all the revenues associ
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(262,119,NULL,'You can also view, modify and delete the details of 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(263,119,NULL,'Add / update a new revenue associated with the accou
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(264,120,NULL,'Add new revenue to an account.',0,'May 28 2004 10:25
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(265,121,NULL,'Fill in the blanks and use "Update" to save your cha
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(266,122,NULL,'You can also click the select button under the actio
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(267,122,NULL,'Clicking on the ticket number will let you view the 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(268,122,NULL,'Add a new ticket.',0,'May 28 2004 10:25:50:290AM',0,
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(269,123,NULL,'The details of the documents can be viewed or modifi
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(270,123,NULL,'Document versions can be updated by using the "add v
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(271,123,NULL,'A new document can be added which is associated with
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(272,123,NULL,'You can view the details of, modify, download or del
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(273,124,NULL,'You can search for accounts in the system. The searc
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(274,125,NULL,'Click Modify at the top or bottom of the page to mod
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(275,126,NULL,'The list of employees reporting to a particular empl
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(276,126,NULL,'The Accounts present are also shown, with name and t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(277,126,NULL,'You can view the progress chart in different views f
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(278,127,NULL,'The exported data can be viewed as a .csv file or in
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(279,127,NULL,'You can also choose to display the list of all the e
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(280,127,NULL,'New export data can be generated using the "Generate
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(281,128,NULL,'The details are updated by clicking the Update butto
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(282,131,NULL,'There are filters through which you can exactly sele
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(283,132,NULL,'Using the select button under the action column you 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(284,132,NULL,'Clicking on the subject of the call will show you th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(285,132,NULL,'You can add a call associated with the contact using
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(286,133,NULL,'Record details can be saved using the save button.',
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(287,134,NULL,'The details of the new call can be saved using the s
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(288,134,NULL,'The call type can be selected from the dropdown box.
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(289,135,NULL,'You can browse your local system to select a new doc
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(290,138,NULL,'You can upload a new version of an existing document
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(291,141,NULL,'You can insert a new ticket, add the ticket source a
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(292,142,NULL,'The details of the documents can be viewed or modifi
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(293,142,NULL,'You can view the details, modify, download or delete
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(294,142,NULL,'A new document can be added which is associated with
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(295,142,NULL,'The document versions can be updated by using the "a
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(296,147,NULL,'Clicking on the account name shows complete details 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(297,147,NULL,'You can add a new account',0,'May 28 2004 10:25:50:6
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(298,147,NULL,'The select button in the Action column allows you to
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(299,148,NULL,'You can download all the versions of the documents',
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(300,149,NULL,'You can modify / update the current document informa
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(301,150,NULL,'The details of the account can be modified here. The
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(302,151,NULL,'You can modify, delete or forward the calls using th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(303,152,NULL,'The details of the new call can be saved using the s
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(304,152,NULL,'The call type can be selected from the dropdown box.
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(305,153,NULL,'You can modify, delete or forward the calls using th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(306,154,NULL,'You can select the list of the recipients to whom yo
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(307,155,NULL,'You can also view, modify and delete the opportunity
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(308,155,NULL,'When the description of the opportunity is clicked, 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(309,155,NULL,'Add an opportunity.',0,'May 28 2004 10:25:50:740AM',
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(310,155,NULL,'Select an opportunity type from the drop down list.'
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(311,156,NULL,'You can rename or delete the opportunity itself usin
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(312,156,NULL,'You can modify, view and delete the details of any p
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(313,156,NULL,'You can add a new component associated with the acco
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(314,157,NULL,'Lets you modify or delete the ticket information',0,
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(315,157,NULL,'You can view the tasks and documents related to a ti
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(316,158,NULL,'You can also have tasks and documents related to a t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(317,158,NULL,'Lets you modify / update the ticket information.',0,
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(318,159,NULL,'The details of the task can be viewed or modified by
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(319,159,NULL,'You can update the task by clicking on the descripti
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(320,159,NULL,'You can add a task which is associated with the exis
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(321,160,NULL,'The document can be uploaded using the browse button
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(322,162,NULL,'You can download all the different versions of the d
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(323,163,NULL,'The subject and the filename of the document can be 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(324,164,NULL,'The subject and the file name can be changed. The ve
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(325,165,NULL,'The exported data can be viewed as a .csv file or in
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(326,165,NULL,'You can also choose to display a list of all the exp
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(327,165,NULL,'New export data can be generated using the "Generate
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(328,166,NULL,'Revenue details along with the option to modify and 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(329,168,NULL,'You can add / update an opportunity here and assign 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(330,169,NULL,'You can add / update an opportunity here and assign 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(331,170,NULL,'An opportunity can be renamed or deleted using the b
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(332,170,NULL,'Clicking on the select button lets you view, modify 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(333,170,NULL,'Clicking on the name of the component would show the
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(334,170,NULL,'Add a new component which is associated with the acc
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(335,171,NULL,'The description of the opportunity can be changed us
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(336,172,NULL,'You can modify and delete the opportunity created us
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(337,173,NULL,'The component type can be selected using the ?select
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(338,173,NULL,'You can assign the component to any of the employee 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(339,174,NULL,'The component type can be selected using the ?select
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(340,174,NULL,'You can assign the component to any of the employee 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(341,174,NULL,'Clicking the update button can save the changes made
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(342,175,NULL,'You can modify and delete the opportunity created us
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(343,176,NULL,'This page is contains a general information section,
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(344,176,NULL,'The general information section allows you to enter 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(345,176,NULL,'The category and type of for service contacts are co
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(346,176,NULL,'The block hour information allows you to specify or 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(347,176,NULL,'When your company performs work as a result of reque
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(348,176,NULL,'The service model options allow you to specify the t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(349,176,NULL,'The select link in the labor categories field, allow
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(350,177,NULL,'This page is contains a general information section,
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(351,177,NULL,'The functionality and business rule for the general 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(352,177,NULL,'The block hour information section allows you adjust
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(353,179,NULL,'A history link is visible if hours where specified f
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(354,179,NULL,'The history link opens a popup that shows a list of 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(355,179,NULL,'The labor categories field displays a list of comma 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(356,181,NULL,'The information about an asset is categorized into t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(357,181,NULL,'The serial number is mandatory to add an asset. It i
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(358,181,NULL,'The date listed is prefilled with the current date a
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(359,181,NULL,'The Asset Tag is an internal identifier provided for
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(360,181,NULL,'The category section allows you to categorize the as
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(361,181,NULL,'The information in the drop lists of the category se
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(362,181,NULL,'The service model options section allows you to spec
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(363,181,NULL,'In the service contract section of this page, the se
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(364,182,NULL,'The asset details are categorized into specific asse
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(365,182,NULL,'The service model options for this asset displays th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(366,185,NULL,'The account contact information  consists of the con
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(367,185,NULL,'The ''select'' link of the contact type field open''
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(368,185,NULL,'Checking the box at the end of this page enables you
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(369,186,NULL,'The ''select'' link of the contact type field open''
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(370,186,NULL,'The details can be updated using the update button.'
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(371,186,NULL,'The account contact information consists of the cont
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(372,187,NULL,'Based on your permissions, you can provide portal ac
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(373,187,NULL,'If the account contact does not have an email addres
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(374,188,NULL,'The information that is modified in this page affect
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(375,188,NULL,'The information than can be modified are, the portal
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(376,188,NULL,'The password is automatically generated and mailed t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(377,189,NULL,'This page allows you to specify the portall role, th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(378,189,NULL,'The password is automatically generated and mailed t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(379,190,NULL,'Clicking the select button under the action column g
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(380,190,NULL,'Clicking on the campaign name gives you complete det
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(381,190,NULL,'You can display the campaigns created and their deta
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(382,191,NULL,'This creates a new Campaign. This takes in both the 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(383,192,NULL,'You can view, modify and delete details by clicking 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(384,192,NULL,'For each of the campaign, the groups, message and de
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(385,192,NULL,'Clicking the name of the campaign shows you more det
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(386,192,NULL,'You can view your incomplete campaigns or all the in
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(387,192,NULL,'Add a campaign',0,'May 28 2004 10:25:51:750AM',0,'Ma
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(388,193,NULL,'You can also click the select button under the Actio
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(389,193,NULL,'Clicking the group name will show the list of contac
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(390,193,NULL,'Add a contact group using the link "Add a Contact Gr
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(391,193,NULL,'You can filter the list of groups displayed by selec
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(392,194,NULL,'You can preview the details of the group by clicking
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(393,194,NULL,'You can also select from the list of "Selected crite
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(394,194,NULL,'You can define the criteria to generate the list by 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(395,194,NULL,'You can select the criteria for the group to be crea
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(396,195,NULL,'You can view, modify, clone or delete each of the me
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(397,195,NULL,'The dropdown list acts as filters for displaying the
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(398,195,NULL,'Clicking on the message name will show details about
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(399,195,NULL,'Add a new message',0,'May 28 2004 10:25:51:800AM',0,
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(400,196,NULL,'The new message can be saved by clicking the save me
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(401,196,NULL,'The permissions or the access type for the message c
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(402,197,NULL,'Clicking on the "surveys" will let you create new in
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(403,198,NULL,'You can use the preview button to view the details a
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(404,198,NULL,'You can modify or delete a group.',0,'May 28 2004 10
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(405,199,NULL,'You can change the version of the document when ever
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(406,203,NULL,'You can browse to select a new document to upload if
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(407,205,NULL,'You can also go back from the current detailed view 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(408,206,NULL,'You can download from the list of documents availabl
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(409,208,NULL,'The name of the survey is a mandatory field for crea
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(410,210,NULL,'You can download the mail merge shown at the bottom 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(411,211,NULL,'Different versions of the document can be downloaded
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(412,216,NULL,'You can update the campaign schedule by filling in t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(413,217,NULL,'You can also generate a list of contacts be selectin
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(414,217,NULL,'You can choose the contacts in the group using the "
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(415,217,NULL,'You can update the name of the group',0,'May 28 2004
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(416,219,NULL,'There can be multiple attachments to a single messag
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(417,223,NULL,'The details of the documents can be viewed or modifi
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(418,223,NULL,'You can view the details, modify, download or delete
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(419,223,NULL,'A new document can be added to the account.',0,'May 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(420,223,NULL,'The document versions can be updated by using the "a
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(421,224,NULL,'The name of the campaign can be changed or deleted b
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(422,224,NULL,'You can choose a group / groups, a message for the c
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(423,225,NULL,'You can check the groups you want for the current ca
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(424,225,NULL,'You can also add attachments to the messages you sen
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(425,225,NULL,'You can view all the groups present or the groups cr
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(426,226,NULL,'You can select a message for this campaign from the 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(427,226,NULL,'The messages can be of multiple types, which can be 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(428,227,NULL,'The attachments configured are the surveys or the fi
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(429,228,NULL,'You can view and select from all, or only your own s
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(430,229,NULL,'You can download or remove the file name. You can al
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(431,230,NULL,'The name and the description of the campaign can be 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(432,231,NULL,'You can modify, delete or clone the message details 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(433,232,NULL,'You can select font properties for the text of the m
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(434,232,NULL,'The name of the message and the access type can be g
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(435,233,NULL,'You can modify, delete or clone the message details 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(436,234,NULL,'You can select font properties for the text of the m
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(437,234,NULL,'The name of the message and the access type can be g
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(438,235,NULL,'You can also view, modify and delete the details of 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(439,235,NULL,'Clicking on the name of the survey shows its details
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(440,235,NULL,'Add a new survey',0,'May 28 2004 10:25:52:430AM',0,'
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(441,235,NULL,'You can view all or your own surveys using the drop 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(442,236,NULL,'The "Save & Add" button saves the current question a
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(443,236,NULL,'You can also specify whether the particular question
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(444,236,NULL,'If the selected question type is "Item List", then a
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(445,236,NULL,'A new question type can be selected through the drop
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(446,237,NULL,'The preview button shows you the survey questions in
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(447,237,NULL,'You can modify, delete, and preview the survey detai
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(448,237,NULL,'You can view the survey introduction text, the quest
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(449,238,NULL,'You can add questions to the survey here.',0,'May 28
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(450,238,NULL,'Clicking the "Done" button can save the survey and y
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(451,238,NULL,'The survey questions can be moved up or down using t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(452,238,NULL,'You can edit or delete any of the survey questions u
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(453,238,NULL,'You can add new survey questions here.',0,'May 28 20
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(454,239,NULL,'You can click on "Create Attachments" and include in
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(455,239,NULL,'Clicking the "Create Message"  link lets you compose
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(456,239,NULL,'You can click the "Build Groups" link to assemble dy
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(457,239,NULL,'The "Campaign Builder" can be clicked to select grou
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(458,239,NULL,'You can click on the "Dashboard" to view the sent me
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(459,240,NULL,'Lets you modify or delete ticket information',0,'May
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(460,240,NULL,'You can also store tasks and documents related to a 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(461,241,NULL,'For each new ticket you can select the organization,
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(462,242,NULL,'The search can be done based on different parameters
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(463,243,NULL,'Clicking on the subject of the exported data shows y
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(464,243,NULL,'Clicking on the select button under the action colum
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(465,243,NULL,'You can filter the exported date generated, by you o
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(466,243,NULL,'You can generate a new exported data by clicking the
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(467,244,NULL,'You can save the details of the modified ticket by c
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(468,245,NULL,'You can save the details of the modified ticket by c
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(469,246,NULL,'The details of the task can be viewed or modified by
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(470,246,NULL,'You can update the task by clicking on the descripti
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(471,246,NULL,'You can add a task which is associated with the exis
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(472,247,NULL,'The details of the documents can be viewed or modifi
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(473,247,NULL,'You can view the details, modify, download or delete
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(474,247,NULL,'A new document associated with the ticket can be add
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(475,247,NULL,'The document versions can be updated by using the "a
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(476,248,NULL,'A new record is added into the folder using the link
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(477,248,NULL,'You can select the custom folder using the drop down
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(478,249,NULL,'The details are saved by clicking the save button.',
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(479,250,NULL,'A chronological history of all actions associated wi
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(480,258,NULL,'The changes can be saved using the "Update" button.'
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(481,259,NULL,'You can modify the folder information along with the
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(482,262,NULL,'The document can be uploaded using the browse button
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(483,263,NULL,'You can download all the versions of a document.',0,
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(484,264,NULL,'You can also have tasks and documents related to a t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(485,264,NULL,'Lets you modify / update the ticket information.',0,
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(486,265,NULL,'A new version of a file can be uploaded using the br
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(487,266,NULL,'You can delete a record by clicking on "Del" next to
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(488,266,NULL,'You can add a record by clicking on "Add Ticket".',0
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(489,266,NULL,'You can view more records in a particular section by
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(490,266,NULL,'You can view more details by clicking on the record.
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(491,266,NULL,'You can update a record by clicking on "Edit" next t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(492,267,NULL,'The first activity date displays the date at which w
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(493,267,NULL,'You may view, add, edit and delete activity logs fro
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(494,267,NULL,'Adding, editing and deleting activity logs with hour
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(495,268,NULL,'This page is divided into the general information, p
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(496,268,NULL,'The ''General Information'' section displays the ass
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(497,268,NULL,'The ''Per Day Description of Service'' section that 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(498,268,NULL,'The ''Additional information'' section that allows y
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(499,269,NULL,'This page is divided into the general information, p
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(500,269,NULL,'The travel time and the labor time in the per day de
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(501,270,NULL,'This page is divided into the general information, p
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(502,270,NULL,'The ''General Information'' section displays the ass
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(503,270,NULL,'The ''Per Day Description of Service'' section that 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(504,270,NULL,'# The ''Additional information'' section that allows
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(505,271,NULL,'This page displays information about an asset that i
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(506,271,NULL,'You may view, add, edit and delete maintenance notes
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(507,272,NULL,'This page is divided into a general maintenance info
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(508,272,NULL,'The general mainetenance information section allows 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(509,272,NULL,'The replacement parts section allows you to enter th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(510,273,NULL,'This page is divided into a general maintenance info
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(511,273,NULL,'The general mainetenance information section allows 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(512,273,NULL,'The replacement parts section allows you to enter th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(513,274,NULL,'A new detailed employee record can be added.',0,'May
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(514,274,NULL,'The details of each employee can be viewed, modified
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(515,275,NULL,'You can modify or delete the employee details using 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(516,276,NULL,'The "Save" button saves the details of the employee 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(517,276,NULL,'The "Save & New" button lets you to save the details
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(518,277,NULL,'Clicking on the update button saves the modified det
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(519,278,NULL,'The employee record can be modified or deleted from 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(520,279,NULL,'You can cancel the reports that are scheduled to be 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(521,279,NULL,'The generated reports can be deleted or viewed/downl
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(522,279,NULL,'Add a new report',0,'May 28 2004 10:25:53:293AM',0,'
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(523,280,NULL,'There are four different modules and you can click o
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(524,283,NULL,'You can use the "generate report" button to run the 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(525,283,NULL,'If the parameters exist, you can specify the name of
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(526,284,NULL,'You can run the report by clicking on the title of t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(527,285,NULL,'If the criteria are present, select the criteria, th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(528,286,NULL,'You can view the queue either by using the link in t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(529,287,NULL,'You can cancel the report that is scheduled to be pr
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(530,287,NULL,'You can view the reports generated, download them or
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(531,287,NULL,'A new report can be generated by clicking on the lin
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(532,288,NULL,'The alphabetical slide rule allows users to be liste
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(533,288,NULL,'The columns "Name", ''Username" and "Role" can be cl
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(534,288,NULL,'The "Add New User" link opens a window that allows t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(535,288,NULL,'The "select" buttons in the "Action" column alongsid
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(536,288,NULL,'The list is displayed with 10 names per page by defa
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(537,288,NULL,'The users of the CRM system are listed in alphabetic
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(538,288,NULL,'The drop list provides a filter to either view only 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(539,289,NULL,'The ''Reports To" field allows the administrator to 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(540,289,NULL,'The "Role" drop list allows a role to be associated 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(541,289,NULL,'The "Password" fields allows the administrator to se
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(542,289,NULL,'The Username is the phrase that is used by the user 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(543,289,NULL,'An "Expire Date" may be set for each user after whic
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(544,289,NULL,'The contact field allows the administrator to associ
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(545,290,NULL,'The "Cancel" button allows current and uncommitted c
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(546,290,NULL,'When the "Generate new password" field is checked, t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(547,290,NULL,'The "Disable" button provides a quick link to the ad
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(548,290,NULL,'The "Username", "Role", "Reports To" and password of
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(549,291,NULL,'The list is displayed by default with 10 items per p
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(550,291,NULL,'The login history of the user displays the IP addres
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(551,292,NULL,'Clicking on the select button under the action colum
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(552,292,NULL,'You can click on the contact under the viewpoint col
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(553,292,NULL,'You can add a new viewpoint using the link "Add New 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(554,293,NULL,'You can add a new viewpoint by any employee by click
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(555,293,NULL,'The permissions for the different modules can be giv
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(556,293,NULL,'The contact can be selected and removed using the li
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(557,294,NULL,'The details can be updated using the update button.'
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(558,294,NULL,'You can also set the permissions to access different
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(559,294,NULL,'You can enable the viewpoint by checking the "Enable
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(560,295,NULL,'You can also click the select button under the actio
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(561,295,NULL,'Clicking on the role name gives you details about th
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(562,295,NULL,'You can add a new role into the system.',0,'May 28 2
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(563,296,NULL,'Clicking the update button updates the role.',0,'May
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(564,297,NULL,'The update of the role can be done by clicking the u
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(565,298,NULL,'Clicking on the module name will display a list of m
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(566,299,NULL,'Scheduled Events: A timer triggers a customizable wo
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(567,299,NULL,'Object Events: An Action triggers customizable workf
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(568,299,NULL,'Categories: This lets you create hierarchical catego
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(569,299,NULL,'Lookup Lists: You can view the drop-down lists used 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(570,299,NULL,'Custom Folders and Fields: Custom folders allows you
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(571,300,NULL,'You can create a new item type using the add button 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(572,301,NULL,'You can update the existing the folder, set the opti
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(573,302,NULL,'You can update the existing the folder, set the opti
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(574,303,NULL,'The "Edit" link will let you alter the time for whic
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(575,304,NULL,'The time out can be set by selecting the time from t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(576,305,NULL,'The usage can be displayed for the current date or a
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(577,305,NULL,'The start date and the end date can be specified if 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(578,308,NULL,'You can also enable or disable the custom folders by
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(579,308,NULL,'Clicking on the custom folder will give details abou
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(580,308,NULL,'You can update an existing folder using the edit but
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(581,308,NULL,'You can update an existing folder using the edit but
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(582,308,NULL,'Add a folder to the general contacts module.',0,'May
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(583,309,NULL,'You can also enable or disable the custom folders by
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(584,309,NULL,'You can update an existing folder using the edit but
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(585,309,NULL,'Clicking on the custom folder will give details abou
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(586,309,NULL,'You can update an existing folder using the edit but
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(587,309,NULL,'Add a folder to the general contacts module.',0,'May
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(588,313,NULL,'You can view the process details by clicking on the 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(589,313,NULL,'You can view the process details by clicking on the 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(590,314,NULL,'You can add a group name and save it using the "save
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(591,316,NULL,'You can click "Edit" in the Action column to update 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(592,316,NULL,'You can preview all the items present in a List name
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(593,319,NULL,'You can click "Edit" in the Action column to update 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(594,319,NULL,'You can preview all the items present in a List name
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(595,320,NULL,'You can also delete the folder and all the fields us
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(596,320,NULL,'The groups can also be moved up or down using the "U
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(597,320,NULL,'The custom field can also be edited and deleted usin
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(598,320,NULL,'The custom field created can be moved up or down for
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(599,320,NULL,'You can add a custom field for the group using the "
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(600,320,NULL,'Add a group to the folder selected',0,'May 28 2004 1
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(601,320,NULL,'You can select the folder by using the drop down box
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(602,321,NULL,'Clicking on the list of categories displayed in leve
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(603,321,NULL,'You can select to display either the Active Categori
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(604,322,NULL,'The activated list can be brought back / reverted to
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(605,322,NULL,'You can activate each level by using the "Activate n
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(606,322,NULL,'In the draft categories you can edit your category u
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(607,322,NULL,'You can select to display either the Active Categori
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(608,322,NULL,'Clicking on the list of categories displayed in leve
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(609,326,NULL,'The "Modify" button in the "Details" tab provides a 
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(610,326,NULL,'The "Employee Link" in the ''Primary Information" ta
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(611,326,NULL,'The "Details" tab displays the information about the
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(612,326,NULL,'The "Disable" button provides a quick link to disabl
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(613,331,NULL,'The user and module section allows the administrator
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(614,331,NULL,'The global parameters and server configuration modul
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(615,331,NULL,'The usage section allows the administrator to view t
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(616,331,NULL,'The administration module is divided into distinct c
INSERT [crm].[help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(617,333,NULL,'Clicking on the different links of the search result

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_features] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[help_features] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_sc_category] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_sc_category] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_sc_category] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Consulting',0,10,1)
INSERT [crm].[lookup_sc_category] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Hardware Maintenance',0,20,1)
INSERT [crm].[lookup_sc_category] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Manufacturer''s Maintenance',0,30,1)
INSERT [crm].[lookup_sc_category] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Monitoring',0,40,1)
INSERT [crm].[lookup_sc_category] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Time and Materials',0,50,1)
INSERT [crm].[lookup_sc_category] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Warranty',0,60,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_sc_category] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_sc_category] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_department] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_department] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Accounting',0,0,1)
INSERT [crm].[lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Administration',0,0,1)
INSERT [crm].[lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Billing',0,0,1)
INSERT [crm].[lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Customer Relations',0,0,1)
INSERT [crm].[lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Engineering',0,0,1)
INSERT [crm].[lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Finance',0,0,1)
INSERT [crm].[lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(7,'Human Resources',0,0,1)
INSERT [crm].[lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(8,'Legal',0,0,1)
INSERT [crm].[lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(9,'Marketing',0,0,1)
INSERT [crm].[lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(10,'Operations',0,0,1)
INSERT [crm].[lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(11,'Purchasing',0,0,1)
INSERT [crm].[lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(12,'Sales',0,0,1)
INSERT [crm].[lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(13,'Shipping/Receiving',0,0,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_department] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_department] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[project_requirements] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_requirements] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_requirements] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[project_requirements] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_order_terms] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_order_terms] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_order_terms] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_order_terms] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[report_criteria_parameter] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[report_criteria_parameter] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[report_criteria_parameter] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[report_criteria_parameter] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_product_tax] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_tax] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_tax] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_product_tax] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[customer_product_history] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[customer_product_history] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[customer_product_history] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[customer_product_history] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_revenue_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_revenue_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_revenue_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Technical',0,0,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_revenue_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_revenue_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[campaign_survey_link] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                     
------------------------------------------------------------------------------------------------------------------------------------ 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[campaign_survey_link] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[report_queue] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[report_queue] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[report_queue] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[report_queue] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[news] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[news] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[news] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[news] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_sc_type] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_sc_type] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_sc_type] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_sc_type] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[quote_notes] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_notes] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_notes] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[quote_notes] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_orgaddress_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_orgaddress_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_orgaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Primary',0,10,1)
INSERT [crm].[lookup_orgaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Auxiliary',0,20,1)
INSERT [crm].[lookup_orgaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Billing',0,30,1)
INSERT [crm].[lookup_orgaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Shipping',0,40,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_orgaddress_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_orgaddress_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_order_source] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_order_source] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_order_source] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_order_source] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[survey_questions] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[survey_questions] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[survey_questions] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[survey_questions] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_recurring_type] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_recurring_type] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_recurring_type] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_recurring_type] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_revenuedetail_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_revenuedetail_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_revenuedetail_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_revenuedetail_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[organization_address] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[organization_address] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[organization_address] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[organization_address] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_response_model] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_response_model] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(1,'M-F 8AM-5PM 8 hours',0,10,1)
INSERT [crm].[lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(2,'M-F 8AM-5PM 6 hours',0,20,1)
INSERT [crm].[lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(3,'M-F 8AM-5PM 4 hours',0,30,1)
INSERT [crm].[lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(4,'M-F 8AM-5PM same day',0,40,1)
INSERT [crm].[lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(5,'M-F 8AM-5PM next business day',0,50,1)
INSERT [crm].[lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(6,'M-F 8AM-8PM 4 hours',0,60,1)
INSERT [crm].[lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(7,'M-F 8AM-8PM 2 hours',0,70,1)
INSERT [crm].[lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(8,'24x7 8 hours',0,80,1)
INSERT [crm].[lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(9,'24x7 4 hours',0,90,1)
INSERT [crm].[lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(10,'24x7 2 hours',0,100,1)
INSERT [crm].[lookup_response_model] ([code],[description],[default_item],[level],[enabled])VALUES(11,'No response time guaranteed',0,110,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_response_model] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_response_model] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[help_related_links] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_related_links] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_related_links] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[help_related_links] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_orgemail_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_orgemail_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_orgemail_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Primary',0,10,1)
INSERT [crm].[lookup_orgemail_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Auxiliary',0,20,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_orgemail_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_orgemail_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[module_field_categorylink] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[module_field_categorylink] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[module_field_categorylink] ([id],[module_id],[category_id],[level],[description],[entered])VALUES(1,1,1,10,'Accounts','May 28 2004 10:25:37:700AM')
INSERT [crm].[module_field_categorylink] ([id],[module_id],[category_id],[level],[description],[entered])VALUES(2,2,2,10,'Contacts','May 28 2004 10:25:37:850AM')
INSERT [crm].[module_field_categorylink] ([id],[module_id],[category_id],[level],[description],[entered])VALUES(3,8,11072003,10,'Tickets','May 28 2004 10:25:38:440AM')
INSERT [crm].[module_field_categorylink] ([id],[module_id],[category_id],[level],[description],[entered])VALUES(4,17,200403192,10,'Product Catalog Categories','May 28 2004 10:25:39:153AM')

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[module_field_categorylink] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[module_field_categorylink] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[order_entry] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_entry] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_entry] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[order_entry] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[product_catalog] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_catalog] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_catalog] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[product_catalog] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[project_assignments] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_assignments] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_assignments] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[project_assignments] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_phone_model] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_phone_model] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(1,'< 15 minutes',0,10,1)
INSERT [crm].[lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(2,'< 5 minutes',0,20,1)
INSERT [crm].[lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(3,'M-F 7AM-4PM',0,30,1)
INSERT [crm].[lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(4,'M-F 8AM-5PM',0,40,1)
INSERT [crm].[lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(5,'M-F 8AM-8PM',0,50,1)
INSERT [crm].[lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(6,'24x7',0,60,1)
INSERT [crm].[lookup_phone_model] ([code],[description],[default_item],[level],[enabled])VALUES(7,'No phone support',0,70,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_phone_model] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_phone_model] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[survey_items] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[survey_items] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[survey_items] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[survey_items] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[report_queue_criteria] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[report_queue_criteria] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[report_queue_criteria] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[report_queue_criteria] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[revenue] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[revenue] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[revenue] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[revenue] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[ticketlog] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticketlog] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticketlog] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[ticketlog] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_orgphone_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_orgphone_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_orgphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Main',0,10,1)
INSERT [crm].[lookup_orgphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Fax',0,20,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_orgphone_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_orgphone_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[action_list] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[action_list] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[action_list] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[action_list] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[organization_emailaddress] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[organization_emailaddress] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[organization_emailaddress] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[organization_emailaddress] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_onsite_model] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_onsite_model] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_onsite_model] ([code],[description],[default_item],[level],[enabled])VALUES(1,'M-F 7AM-4PM',0,10,1)
INSERT [crm].[lookup_onsite_model] ([code],[description],[default_item],[level],[enabled])VALUES(2,'M-F 8AM-5PM',0,20,1)
INSERT [crm].[lookup_onsite_model] ([code],[description],[default_item],[level],[enabled])VALUES(3,'M-F 8AM-8PM',0,30,1)
INSERT [crm].[lookup_onsite_model] ([code],[description],[default_item],[level],[enabled])VALUES(4,'24x7',0,40,1)
INSERT [crm].[lookup_onsite_model] ([code],[description],[default_item],[level],[enabled])VALUES(5,'No onsite service',0,50,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_onsite_model] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_onsite_model] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[active_survey] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[active_survey] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[custom_field_category] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[custom_field_category] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[custom_field_category] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[custom_field_category] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[help_faqs] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_faqs] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_faqs] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[help_faqs] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_email_model] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_email_model] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_email_model] ([code],[description],[default_item],[level],[enabled])VALUES(1,'2 hours',0,10,1)
INSERT [crm].[lookup_email_model] ([code],[description],[default_item],[level],[enabled])VALUES(2,'4 hours',0,20,1)
INSERT [crm].[lookup_email_model] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Same day',0,30,1)
INSERT [crm].[lookup_email_model] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Next business day',0,40,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_email_model] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_email_model] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_instantmessenger_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_instantmessenger_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_instantmessenger_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_instantmessenger_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[action_item] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[action_item] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[action_item] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[action_item] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[organization_phone] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[organization_phone] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[organization_phone] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[organization_phone] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_hours_reason] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_hours_reason] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_hours_reason] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Purchase',0,10,1)
INSERT [crm].[lookup_hours_reason] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Renewal',0,20,1)
INSERT [crm].[lookup_hours_reason] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Correction',0,30,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_hours_reason] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_hours_reason] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_employment_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_employment_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_employment_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_employment_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[revenue_detail] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[revenue_detail] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[revenue_detail] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[revenue_detail] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[ticket_csstm_form] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_csstm_form] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_csstm_form] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[ticket_csstm_form] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[active_survey_questions] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey_questions] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey_questions] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[active_survey_questions] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[custom_field_group] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[custom_field_group] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[custom_field_group] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[custom_field_group] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[help_business_rules] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_business_rules] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(1,1,'You can view your calendar and the calendars of those who work for you',0,'May 28 20
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(2,77,'Apart from email addresses, you may not map more than one column of the cvs file to
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(3,176,'The current end date should be greater than the current contract date if the curre
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(4,176,'The current contract date is not required. If it is left blank, it is prefilled wi
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(5,181,'For most assets it is percieved that the puchased date is earler than the expirati
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(6,183,'Similar to the adding an asset',0,'May 28 2004 10:25:51:540AM',0,'May 28 2004 10:2
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(7,185,'An account contact may have a number of email addresses, this page allows one emai
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(8,186,'An account contact may have a number of email addresses, this page allows one emai
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(9,266,'Other tickets in my Department: These are records that are assigned to anyone in y
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(10,266,'Tickets assigned to me: These are records that are assigned to you and are open',
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(11,266,'Tickets created by me: These are records that have been entered by you and are op
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(12,268,'If the follow up field is checked, the alert date and description are mandatory.'
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(13,268,'The phone and engineer response time are text fields that allow you to enter the 
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(14,268,'All fields in a row of the description of service section is mandatory.',0,'May 2
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(15,268,'The travel time and the labor time can be selected from the drop list. If you cho
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(16,270,'All fields in a row of the description of service section is mandatory.',0,'May 2
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(17,270,'If the follow up field is checked, the alert date and description are mandatory.'
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(18,270,'The phone and engineer response time are text fields that allow you to enter the 
INSERT [crm].[help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(19,270,'The travel time and the labor time can be selected from the drop list. If you cho

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_business_rules] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[help_business_rules] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[service_contract] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[service_contract] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[service_contract] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[service_contract] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[project_assignments_status] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_assignments_status] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_assignments_status] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[project_assignments_status] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[order_product] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[order_product] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_locale] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_locale] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_locale] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_locale] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[product_catalog_pricing] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_catalog_pricing] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_catalog_pricing] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[product_catalog_pricing] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[action_item_log] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[action_item_log] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[action_item_log] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[action_item_log] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[contact_address] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[contact_address] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[contact_address] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[contact_address] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[custom_field_info] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[custom_field_info] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[custom_field_info] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[custom_field_info] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[project_issues] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_issues] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_issues] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[project_issues] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_contactaddress_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_contactaddress_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_contactaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Business',0,10,1)
INSERT [crm].[lookup_contactaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Home',0,20,1)
INSERT [crm].[lookup_contactaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Other',0,30,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_contactaddress_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_contactaddress_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_task_priority] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_task_priority] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_task_priority] ([code],[description],[default_item],[level],[enabled])VALUES(1,'1',1,1,1)
INSERT [crm].[lookup_task_priority] ([code],[description],[default_item],[level],[enabled])VALUES(2,'2',0,2,1)
INSERT [crm].[lookup_task_priority] ([code],[description],[default_item],[level],[enabled])VALUES(3,'3',0,3,1)
INSERT [crm].[lookup_task_priority] ([code],[description],[default_item],[level],[enabled])VALUES(4,'4',0,4,1)
INSERT [crm].[lookup_task_priority] ([code],[description],[default_item],[level],[enabled])VALUES(5,'5',0,5,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_task_priority] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_task_priority] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[ticket_activity_item] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_activity_item] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_activity_item] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[ticket_activity_item] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[help_notes] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_notes] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_notes] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[help_notes] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[import] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[import] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[import] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[import] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[ticket_sun_form] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_sun_form] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_sun_form] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[ticket_sun_form] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[contact_emailaddress] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[contact_emailaddress] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[contact_emailaddress] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[contact_emailaddress] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_contactemail_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_contactemail_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_contactemail_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Business',0,10,1)
INSERT [crm].[lookup_contactemail_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Personal',0,20,1)
INSERT [crm].[lookup_contactemail_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Other',0,30,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_contactemail_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_contactemail_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[active_survey_items] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey_items] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey_items] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[active_survey_items] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_task_loe] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_task_loe] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_task_loe] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Minute(s)',0,1,1)
INSERT [crm].[lookup_task_loe] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Hour(s)',1,2,1)
INSERT [crm].[lookup_task_loe] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Day(s)',0,3,1)
INSERT [crm].[lookup_task_loe] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Week(s)',0,4,1)
INSERT [crm].[lookup_task_loe] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Month(s)',0,5,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_task_loe] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_task_loe] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[service_contract_hours] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[service_contract_hours] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[service_contract_hours] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[service_contract_hours] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[custom_field_lookup] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[custom_field_lookup] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[custom_field_lookup] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[custom_field_lookup] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[project_issue_replies] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_issue_replies] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_issue_replies] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[project_issue_replies] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[active_survey_responses] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey_responses] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey_responses] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[active_survey_responses] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_contactphone_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_contactphone_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Business',0,10,1)
INSERT [crm].[lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Business2',0,20,1)
INSERT [crm].[lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Business Fax',0,30,1)
INSERT [crm].[lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Home',0,40,1)
INSERT [crm].[lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Home2',0,50,1)
INSERT [crm].[lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Home Fax',0,60,1)
INSERT [crm].[lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(7,'Mobile',0,70,1)
INSERT [crm].[lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(8,'Pager',0,80,1)
INSERT [crm].[lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(9,'Other',0,90,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_contactphone_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_contactphone_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[order_product_status] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_status] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_status] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[order_product_status] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[database_version] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[database_version] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[database_version] ([version_id],[script_filename],[script_version],[entered])VALUES(1,'ce\dhv\cfs2\src\sql\init\workflow.bsh','ce\dhv\cfs2\src\sql\init\workflow','May 28 2004 10:25:59:370AM')

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[database_version] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[database_version] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_task_category] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_task_category] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_task_category] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_task_category] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[help_tips] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_tips] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[help_tips] ([tip_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(1,1,'Assign due dates for tasks so that you can be alerted',0,'May 28 2004 10:25:47:727AM',0,'May 28 2004 10:25:47:727AM',1)
INSERT [crm].[help_tips] ([tip_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(2,266,'Make sure to resolve your tickets as soon as possible so they don''t appear here!',0,'May 28 2004 10:25:52:893AM',0,'May 2

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_tips] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[help_tips] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[package] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[package] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[package] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[package] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[trouble_asset_replacement] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[trouble_asset_replacement] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[trouble_asset_replacement] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[trouble_asset_replacement] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_call_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_call_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Incoming Call',1,10,1)
INSERT [crm].[lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Outgoing Call',0,20,1)
INSERT [crm].[lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Proactive Call',0,30,1)
INSERT [crm].[lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Inhouse Meeting',0,40,1)
INSERT [crm].[lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Outside Appointment',0,50,1)
INSERT [crm].[lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Proactive Meeting',0,60,1)
INSERT [crm].[lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(7,'Email Servicing',0,70,1)
INSERT [crm].[lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(8,'Email Proactive',0,80,1)
INSERT [crm].[lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(9,'Fax Servicing',0,90,1)
INSERT [crm].[lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(10,'Fax Proactive',0,100,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_call_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_call_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[contact_phone] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[contact_phone] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[contact_phone] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[contact_phone] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[active_survey_answers] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey_answers] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey_answers] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[active_survey_answers] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_access_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_access_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(1,626030330,'Controlled-Hierarchy',1,1,1,626030335)
INSERT [crm].[lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(2,626030330,'Public',0,2,1,626030334)
INSERT [crm].[lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(3,626030330,'Personal',0,3,1,626030333)
INSERT [crm].[lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(4,626030331,'Public',1,1,1,626030334)
INSERT [crm].[lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(5,626030332,'Public',1,1,1,626030334)
INSERT [crm].[lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(6,707031028,'Controlled-Hierarchy',1,1,1,626030335)
INSERT [crm].[lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(7,707031028,'Public',0,2,1,626030334)
INSERT [crm].[lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(8,707031028,'Personal',0,3,1,626030333)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_access_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_access_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[task] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[task] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[task] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[task] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_quote_status] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_quote_status] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Incomplete',0,0,1)
INSERT [crm].[lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Pending internal approval',0,0,1)
INSERT [crm].[lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Approved internally',0,0,1)
INSERT [crm].[lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Unapproved internally',0,0,1)
INSERT [crm].[lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Pending customer acceptance',0,0,1)
INSERT [crm].[lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Accepted by customer',0,0,1)
INSERT [crm].[lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(7,'Rejected by customer',0,0,1)
INSERT [crm].[lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(8,'Changes requested by customer',0,0,1)
INSERT [crm].[lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(9,'Cancelled',0,0,1)
INSERT [crm].[lookup_quote_status] ([code],[description],[default_item],[level],[enabled])VALUES(10,'Complete',0,0,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_quote_status] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_quote_status] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[service_contract_products] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[service_contract_products] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                         
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[service_contract_products] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[service_contract_products] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[custom_field_record] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[custom_field_record] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[custom_field_record] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[custom_field_record] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[project_folders] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_folders] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_folders] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[project_folders] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[project_files] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_files] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_files] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[project_files] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_opportunity_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_opportunity_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_opportunity_types] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(1,NULL,'Annuity',0,0,1)
INSERT [crm].[lookup_opportunity_types] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(2,NULL,'Consultation',0,1,1)
INSERT [crm].[lookup_opportunity_types] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(3,NULL,'Development',0,2,1)
INSERT [crm].[lookup_opportunity_types] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(4,NULL,'Maintenance',0,3,1)
INSERT [crm].[lookup_opportunity_types] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(5,NULL,'Product Sales',0,4,1)
INSERT [crm].[lookup_opportunity_types] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(6,NULL,'Services',0,5,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_opportunity_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_opportunity_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[sync_client] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_client] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_client] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[sync_client] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[organization] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[organization] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[organization] ([org_id],[name],[account_number],[account_group],[url],[revenue],[employees],[notes],[sic_code],[ticker_symbol],[taxid],[lead],[sales_rep],[miner_only],[defaultlocale],[fiscalmonth],[entered],[enteredby],[modified],[modifiedby]

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[organization] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[organization] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[active_survey_answer_items] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey_answer_items] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey_answer_items] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[active_survey_answer_items] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[order_product_options] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_options] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_options] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[order_product_options] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[asset_category] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[asset_category] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[asset_category] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[asset_category] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_quote_type] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_quote_type] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_quote_type] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_quote_type] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[notification] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[notification] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[notification] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[notification] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[active_survey_answer_avg] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey_answer_avg] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                  
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[active_survey_answer_avg] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[active_survey_answer_avg] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[opportunity_header] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[opportunity_header] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[opportunity_header] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[opportunity_header] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[custom_field_data] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[custom_field_data] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[sync_system] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_system] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[sync_system] ([system_id],[application_name],[enabled])VALUES(1,'Deprecated',1)
INSERT [crm].[sync_system] ([system_id],[application_name],[enabled])VALUES(2,'Auto Guide PocketPC',1)
INSERT [crm].[sync_system] ([system_id],[application_name],[enabled])VALUES(3,'Unused',1)
INSERT [crm].[sync_system] ([system_id],[application_name],[enabled])VALUES(4,'CFSHttpXMLWriter',1)
INSERT [crm].[sync_system] ([system_id],[application_name],[enabled])VALUES(5,'Test',1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_system] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[sync_system] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_quote_terms] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_quote_terms] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_quote_terms] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_quote_terms] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[cfsinbox_message] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[cfsinbox_message] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[cfsinbox_message] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[cfsinbox_message] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[package_products_map] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[package_products_map] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[package_products_map] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[package_products_map] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[asset_category_draft] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[asset_category_draft] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[asset_category_draft] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[asset_category_draft] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[sync_table] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_table] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(1,1,'ticket','org.aspcfs.modules.troubletickets.base.Ticket','May 28 2004 10:25:34:107AM
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(2,2,'syncClient','org.aspcfs.modules.service.base.SyncClient','May 28 2004 10:25:34:107A
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(3,2,'user','org.aspcfs.modules.admin.base.User','May 28 2004 10:25:34:107AM','May 28 200
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(4,2,'account','org.aspcfs.modules.accounts.base.Organization','May 28 2004 10:25:34:117A
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(5,2,'accountInventory','org.aspcfs.modules.media.autoguide.base.Inventory','May 28 2004 
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(6,2,'inventoryOption','org.aspcfs.modules.media.autoguide.base.InventoryOption','May 28 
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(7,2,'adRun','org.aspcfs.modules.media.autoguide.base.AdRun','May 28 2004 10:25:34:117AM'
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(8,2,'tableList','org.aspcfs.modules.service.base.SyncTableList','May 28 2004 10:25:34:12
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(9,2,'status_master',NULL,'May 28 2004 10:25:34:127AM','May 28 2004 10:25:34:127AM',NULL,
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(10,2,'system',NULL,'May 28 2004 10:25:34:127AM','May 28 2004 10:25:34:127AM',NULL,16,0,N
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(11,2,'userList','org.aspcfs.modules.admin.base.UserList','May 28 2004 10:25:34:137AM','M
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(12,2,'XIF18users',NULL,'May 28 2004 10:25:34:147AM','May 28 2004 10:25:34:147AM','CREATE
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(13,2,'makeList','org.aspcfs.modules.media.autoguide.base.MakeList','May 28 2004 10:25:34
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(14,2,'XIF2make',NULL,'May 28 2004 10:25:34:147AM','May 28 2004 10:25:34:147AM','CREATE I
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(15,2,'modelList','org.aspcfs.modules.media.autoguide.base.ModelList','May 28 2004 10:25:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(16,2,'XIF3model',NULL,'May 28 2004 10:25:34:147AM','May 28 2004 10:25:34:147AM','CREATE 
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(17,2,'XIF5model',NULL,'May 28 2004 10:25:34:157AM','May 28 2004 10:25:34:157AM','CREATE 
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(18,2,'vehicleList','org.aspcfs.modules.media.autoguide.base.VehicleList','May 28 2004 10
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(19,2,'XIF30vehicle',NULL,'May 28 2004 10:25:34:157AM','May 28 2004 10:25:34:157AM','CREA
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(20,2,'XIF31vehicle',NULL,'May 28 2004 10:25:34:157AM','May 28 2004 10:25:34:157AM','CREA
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(21,2,'XIF4vehicle',NULL,'May 28 2004 10:25:34:157AM','May 28 2004 10:25:34:157AM','CREAT
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(22,2,'accountList','org.aspcfs.modules.accounts.base.OrganizationList','May 28 2004 10:2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(23,2,'XIF16account',NULL,'May 28 2004 10:25:34:167AM','May 28 2004 10:25:34:167AM','CREA
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(24,2,'accountInventoryList','org.aspcfs.modules.media.autoguide.base.InventoryList','May
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(25,2,'XIF10account_inventory',NULL,'May 28 2004 10:25:34:167AM','May 28 2004 10:25:34:16
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(26,2,'XIF10account_inventory',NULL,'May 28 2004 10:25:34:177AM','May 28 2004 10:25:34:17
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(27,2,'XIF19account_inventory',NULL,'May 28 2004 10:25:34:177AM','May 28 2004 10:25:34:17
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(28,2,'XIF35account_inventory',NULL,'May 28 2004 10:25:34:177AM','May 28 2004 10:25:34:17
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(29,2,'optionList','org.aspcfs.modules.media.autoguide.base.OptionList','May 28 2004 10:2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(30,2,'XIF24options',NULL,'May 28 2004 10:25:34:187AM','May 28 2004 10:25:34:187AM','CREA
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(31,2,'inventoryOptionList','org.aspcfs.modules.media.autoguide.base.InventoryOptionList'
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(32,2,'XIF25inventory_options',NULL,'May 28 2004 10:25:34:187AM','May 28 2004 10:25:34:18
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(33,2,'XIF27inventory_options',NULL,'May 28 2004 10:25:34:187AM','May 28 2004 10:25:34:18
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(34,2,'XIF33inventory_options',NULL,'May 28 2004 10:25:34:197AM','May 28 2004 10:25:34:19
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(35,2,'adTypeList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:197AM','May 28
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(36,2,'adRunList','org.aspcfs.modules.media.autoguide.base.AdRunList','May 28 2004 10:25:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(37,2,'XIF22ad_run',NULL,'May 28 2004 10:25:34:197AM','May 28 2004 10:25:34:197AM','CREAT
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(38,2,'XIF36ad_run',NULL,'May 28 2004 10:25:34:207AM','May 28 2004 10:25:34:207AM','CREAT
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(39,2,'XIF37ad_run',NULL,'May 28 2004 10:25:34:207AM','May 28 2004 10:25:34:207AM','CREAT
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(40,2,'inventory_picture',NULL,'May 28 2004 10:25:34:207AM','May 28 2004 10:25:34:207AM',
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(41,2,'XIF23inventory_picture',NULL,'May 28 2004 10:25:34:217AM','May 28 2004 10:25:34:21
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(42,2,'XIF32inventory_picture',NULL,'May 28 2004 10:25:34:217AM','May 28 2004 10:25:34:21
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(43,2,'preferences',NULL,'May 28 2004 10:25:34:217AM','May 28 2004 10:25:34:217AM','CREAT
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(44,2,'XIF29preferences',NULL,'May 28 2004 10:25:34:227AM','May 28 2004 10:25:34:227AM','
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(45,2,'user_account',NULL,'May 28 2004 10:25:34:227AM','May 28 2004 10:25:34:227AM','CREA
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(46,2,'XIF14user_account',NULL,'May 28 2004 10:25:34:227AM','May 28 2004 10:25:34:227AM',
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(47,2,'XIF15user_account',NULL,'May 28 2004 10:25:34:227AM','May 28 2004 10:25:34:227AM',
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(48,2,'XIF17user_account',NULL,'May 28 2004 10:25:34:227AM','May 28 2004 10:25:34:227AM',
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(49,2,'deleteInventoryCache','org.aspcfs.modules.media.autoguide.actions.DeleteInventoryC
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(50,4,'lookupIndustry','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:237AM',
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(51,4,'lookupIndustryList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:237AM'
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(52,4,'systemPrefs','org.aspcfs.utils.web.CustomLookupElement','May 28 2004 10:25:34:237A
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(53,4,'systemModules','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:237AM','
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(54,4,'systemModulesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:247AM',
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(55,4,'lookupContactTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:247
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(56,4,'lookupContactTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:24
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(57,4,'lookupAccountTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:247
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(58,4,'lookupAccountTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:24
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(59,4,'lookupDepartment','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:247AM
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(60,4,'lookupDepartmentList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:247A
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(61,4,'lookupOrgAddressTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(62,4,'lookupOrgAddressTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(63,4,'lookupOrgEmailTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:25
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(64,4,'lookupOrgEmailTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(65,4,'lookupOrgPhoneTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:25
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(66,4,'lookupOrgPhoneTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(67,4,'lookupInstantMessengerTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(68,4,'lookupInstantMessengerTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(69,4,'lookupEmploymentTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(70,4,'lookupEmploymentTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(71,4,'lookupLocale','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:277AM','M
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(72,4,'lookupLocaleList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:277AM','
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(73,4,'lookupContactAddressTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(74,4,'lookupContactAddressTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(75,4,'lookupContactEmailTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:3
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(76,4,'lookupContactEmailTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(77,4,'lookupContactPhoneTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:3
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(78,4,'lookupContactPhoneTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(79,4,'lookupStage','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:287AM','Ma
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(80,4,'lookupStageList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:287AM','M
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(81,4,'lookupDeliveryOptions','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(82,4,'lookupDeliveryOptionsList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(83,4,'lookupCallTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:287AM'
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(84,4,'lookupCallTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:287AM
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(85,4,'ticketCategory','org.aspcfs.modules.troubletickets.base.TicketCategory','May 28 20
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(86,4,'ticketCategoryList','org.aspcfs.modules.troubletickets.base.TicketCategoryList','M
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(87,4,'ticketSeverity','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:297AM',
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(88,4,'ticketSeverityList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:297AM'
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(89,4,'lookupTicketSource','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:297
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(90,4,'lookupTicketSourceList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:29
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(91,4,'ticketPriority','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:297AM',
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(92,4,'ticketPriorityList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:307AM'
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(93,4,'lookupRevenueTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:307
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(94,4,'lookupRevenueTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:30
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(95,4,'lookupRevenueDetailTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(96,4,'lookupRevenueDetailTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(97,4,'lookupSurveyTypes','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:307A
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(98,4,'lookupSurveyTypesList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:317
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(99,4,'syncClient','org.aspcfs.modules.service.base.SyncClient','May 28 2004 10:25:34:317
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(100,4,'user','org.aspcfs.modules.admin.base.User','May 28 2004 10:25:34:317AM','May 28 2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(101,4,'userList','org.aspcfs.modules.admin.base.UserList','May 28 2004 10:25:34:317AM','
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(102,4,'contact','org.aspcfs.modules.contacts.base.Contact','May 28 2004 10:25:34:317AM',
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(103,4,'contactList','org.aspcfs.modules.contacts.base.ContactList','May 28 2004 10:25:34
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(104,4,'ticket','org.aspcfs.modules.troubletickets.base.Ticket','May 28 2004 10:25:34:327
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(105,4,'ticketList','org.aspcfs.modules.troubletickets.base.TicketList','May 28 2004 10:2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(106,4,'account','org.aspcfs.modules.accounts.base.Organization','May 28 2004 10:25:34:32
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(107,4,'accountList','org.aspcfs.modules.accounts.base.OrganizationList','May 28 2004 10:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(108,4,'role','org.aspcfs.modules.admin.base.Role','May 28 2004 10:25:34:327AM','May 28 2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(109,4,'roleList','org.aspcfs.modules.admin.base.RoleList','May 28 2004 10:25:34:327AM','
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(110,4,'permissionCategory','org.aspcfs.modules.admin.base.PermissionCategory','May 28 20
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(111,4,'permissionCategoryList','org.aspcfs.modules.admin.base.PermissionCategoryList','M
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(112,4,'permission','org.aspcfs.modules.admin.base.Permission','May 28 2004 10:25:34:337A
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(113,4,'permissionList','org.aspcfs.modules.admin.base.PermissionList','May 28 2004 10:25
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(114,4,'rolePermission','org.aspcfs.modules.admin.base.RolePermission','May 28 2004 10:25
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(115,4,'rolePermissionList','org.aspcfs.modules.admin.base.RolePermissionList','May 28 20
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(116,4,'opportunity','org.aspcfs.modules.pipeline.base.Opportunity','May 28 2004 10:25:34
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(117,4,'opportunityList','org.aspcfs.modules.pipeline.base.OpportunityList','May 28 2004 
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(118,4,'call','org.aspcfs.modules.contacts.base.Call','May 28 2004 10:25:34:347AM','May 2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(119,4,'callList','org.aspcfs.modules.contacts.base.CallList','May 28 2004 10:25:34:347AM
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(120,4,'customFieldCategory','org.aspcfs.modules.base.CustomFieldCategory','May 28 2004 1
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(121,4,'customFieldCategoryList','org.aspcfs.modules.base.CustomFieldCategoryList','May 2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(122,4,'customFieldGroup','org.aspcfs.modules.base.CustomFieldGroup','May 28 2004 10:25:3
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(123,4,'customFieldGroupList','org.aspcfs.modules.base.CustomFieldGroupList','May 28 2004
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(124,4,'customField','org.aspcfs.modules.base.CustomField','May 28 2004 10:25:34:347AM','
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(125,4,'customFieldList','org.aspcfs.modules.base.CustomFieldList','May 28 2004 10:25:34:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(126,4,'customFieldLookup','org.aspcfs.utils.web.LookupElement','May 28 2004 10:25:34:357
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(127,4,'customFieldLookupList','org.aspcfs.utils.web.LookupList','May 28 2004 10:25:34:35
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(128,4,'customFieldRecord','org.aspcfs.modules.base.CustomFieldRecord','May 28 2004 10:25
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(129,4,'customFieldRecordList','org.aspcfs.modules.base.CustomFieldRecordList','May 28 20
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(130,4,'contactEmailAddress','org.aspcfs.modules.contacts.base.ContactEmailAddress','May 
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(131,4,'contactEmailAddressList','org.aspcfs.modules.contacts.base.ContactEmailAddressLis
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(132,4,'customFieldData','org.aspcfs.modules.base.CustomFieldData','May 28 2004 10:25:34:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(133,4,'lookupProjectActivity','org.aspcfs.utils.web.CustomLookupElement','May 28 2004 10
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(134,4,'lookupProjectActivityList','org.aspcfs.utils.web.CustomLookupList','May 28 2004 1
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(135,4,'lookupProjectIssues','org.aspcfs.utils.web.CustomLookupElement','May 28 2004 10:2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(136,4,'lookupProjectIssuesList','org.aspcfs.utils.web.CustomLookupList','May 28 2004 10:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(137,4,'lookupProjectLoe','org.aspcfs.utils.web.CustomLookupElement','May 28 2004 10:25:3
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(138,4,'lookupProjectLoeList','org.aspcfs.utils.web.CustomLookupList','May 28 2004 10:25:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(139,4,'lookupProjectPriority','org.aspcfs.utils.web.CustomLookupElement','May 28 2004 10
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(140,4,'lookupProjectPriorityList','org.aspcfs.utils.web.CustomLookupList','May 28 2004 1
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(141,4,'lookupProjectStatus','org.aspcfs.utils.web.CustomLookupElement','May 28 2004 10:2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(142,4,'lookupProjectStatusList','org.aspcfs.utils.web.CustomLookupList','May 28 2004 10:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(143,4,'project','com.zeroio.iteam.base.Project','May 28 2004 10:25:34:387AM','May 28 200
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(144,4,'projectList','com.zeroio.iteam.base.ProjectList','May 28 2004 10:25:34:397AM','Ma
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(145,4,'requirement','com.zeroio.iteam.base.Requirement','May 28 2004 10:25:34:397AM','Ma
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(146,4,'requirementList','com.zeroio.iteam.base.RequirementList','May 28 2004 10:25:34:39
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(147,4,'assignment','com.zeroio.iteam.base.Assignment','May 28 2004 10:25:34:397AM','May 
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(148,4,'assignmentList','com.zeroio.iteam.base.AssignmentList','May 28 2004 10:25:34:397A
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(149,4,'issue','com.zeroio.iteam.base.Issue','May 28 2004 10:25:34:397AM','May 28 2004 10
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(150,4,'issueList','com.zeroio.iteam.base.IssueList','May 28 2004 10:25:34:407AM','May 28
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(151,4,'issueReply','com.zeroio.iteam.base.IssueReply','May 28 2004 10:25:34:407AM','May 
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(152,4,'issueReplyList','com.zeroio.iteam.base.IssueReplyList','May 28 2004 10:25:34:407A
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(153,4,'teamMember','com.zeroio.iteam.base.TeamMember','May 28 2004 10:25:34:407AM','May 
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(154,4,'fileItem','com.zeroio.iteam.base.FileItem','May 28 2004 10:25:34:407AM','May 28 2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(155,4,'fileItemList','com.zeroio.iteam.base.FileItemList','May 28 2004 10:25:34:407AM','
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(156,4,'fileItemVersion','com.zeroio.iteam.base.FileItemVersion','May 28 2004 10:25:34:41
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(157,4,'fileItemVersionList','com.zeroio.iteam.base.FileItemVersionList','May 28 2004 10:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(158,4,'fileDownloadLog','com.zeroio.iteam.base.FileDownloadLog','May 28 2004 10:25:34:41
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(159,4,'contactAddress','org.aspcfs.modules.contacts.base.ContactAddress','May 28 2004 10
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(160,4,'contactAddressList','org.aspcfs.modules.contacts.base.ContactAddressList','May 28
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(161,4,'contactPhoneNumber','org.aspcfs.modules.contacts.base.ContactPhoneNumber','May 28
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(162,4,'contactPhoneNumberList','org.aspcfs.modules.contacts.base.ContactPhoneNumberList'
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(163,4,'organizationPhoneNumber','org.aspcfs.modules.accounts.base.OrganizationPhoneNumbe
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(164,4,'organizationPhoneNumberList','org.aspcfs.modules.accounts.base.OrganizationPhoneN
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(165,4,'organizationEmailAddress','org.aspcfs.modules.accounts.base.OrganizationEmailAddr
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(166,4,'organizationEmailAddressList','org.aspcfs.modules.accounts.base.OrganizationEmail
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(167,4,'organizationAddress','org.aspcfs.modules.accounts.base.OrganizationAddress','May 
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(168,4,'organizationAddressList','org.aspcfs.modules.accounts.base.OrganizationAddressLis
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(169,4,'ticketLog','org.aspcfs.modules.troubletickets.base.TicketLog','May 28 2004 10:25:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(170,4,'ticketLogList','org.aspcfs.modules.troubletickets.base.TicketLogList','May 28 200
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(171,4,'message','org.aspcfs.modules.communications.base.Message','May 28 2004 10:25:34:4
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(172,4,'messageList','org.aspcfs.modules.communications.base.MessageList','May 28 2004 10
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(173,4,'searchCriteriaElements','org.aspcfs.modules.communications.base.SearchCriteriaLis
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(174,4,'searchCriteriaElementsList','org.aspcfs.modules.communications.base.SearchCriteri
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(175,4,'savedCriteriaElement','org.aspcfs.modules.communications.base.SavedCriteriaElemen
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(176,4,'searchFieldElement','org.aspcfs.utils.web.CustomLookupElement','May 28 2004 10:25
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(177,4,'searchFieldElementList','org.aspcfs.utils.web.CustomLookupList','May 28 2004 10:2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(178,4,'revenue','org.aspcfs.modules.accounts.base.Revenue','May 28 2004 10:25:34:457AM',
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(179,4,'revenueList','org.aspcfs.modules.accounts.base.RevenueList','May 28 2004 10:25:34
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(180,4,'campaign','org.aspcfs.modules.communications.base.Campaign','May 28 2004 10:25:34
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(181,4,'campaignList','org.aspcfs.modules.communications.base.CampaignList','May 28 2004 
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(182,4,'scheduledRecipient','org.aspcfs.modules.communications.base.ScheduledRecipient','
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(183,4,'scheduledRecipientList','org.aspcfs.modules.communications.base.ScheduledRecipien
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(184,4,'accessLog','org.aspcfs.modules.admin.base.AccessLog','May 28 2004 10:25:34:467AM'
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(185,4,'accessLogList','org.aspcfs.modules.admin.base.AccessLogList','May 28 2004 10:25:3
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(186,4,'accountTypeLevels','org.aspcfs.modules.accounts.base.AccountTypeLevel','May 28 20
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(187,4,'fieldTypes','org.aspcfs.utils.web.CustomLookupElement','May 28 2004 10:25:34:467A
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(188,4,'fieldTypesList','org.aspcfs.utils.web.CustomLookupList','May 28 2004 10:25:34:477
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(189,4,'excludedRecipient','org.aspcfs.modules.communications.base.ExcludedRecipient','Ma
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(190,4,'campaignRun','org.aspcfs.modules.communications.base.CampaignRun','May 28 2004 10
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(191,4,'campaignRunList','org.aspcfs.modules.communications.base.CampaignRunList','May 28
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(192,4,'campaignListGroups','org.aspcfs.modules.communications.base.CampaignListGroup','M
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(193,5,'ticket','org.aspcfs.modules.troubletickets.base.Ticket','May 28 2004 10:25:34:477
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(194,5,'ticketCategory','org.aspcfs.modules.troubletickets.base.TicketCategory','May 28 2
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(195,5,'ticketCategoryList','org.aspcfs.modules.troubletickets.base.TicketCategoryList','
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(196,5,'syncClient','org.aspcfs.modules.service.base.SyncClient','May 28 2004 10:25:34:48
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(197,5,'accountList','org.aspcfs.modules.accounts.base.OrganizationList','May 28 2004 10:
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(198,5,'userList','org.aspcfs.modules.admin.base.UserList','May 28 2004 10:25:34:487AM','
INSERT [crm].[sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(199,5,'contactList','org.aspcfs.modules.contacts.base.ContactList','May 28 2004 10:25:34

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_table] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[sync_table] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[field_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[field_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(1,0,'string','=','is',1)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(2,0,'string','!=','is not',1)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(3,0,'string','= | or field_name is null','is empty',0)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(4,0,'string','!= | and field_name is not null','is not empty',0)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(5,0,'string','like %search_value%','contains',0)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(6,0,'string','not like %search_value%','does not contain',0)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(7,1,'date','<','before',1)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(8,1,'date','>','after',1)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(9,1,'date','between','between',0)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(10,1,'date','<=','on or before',1)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(11,1,'date','>=','on or after',1)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(12,2,'number','>','greater than',1)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(13,2,'number','<','less than',1)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(14,2,'number','=','equals',1)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(15,2,'number','>=','greater than or equal to',1)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(16,2,'number','<=','less than or equal to',1)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(17,2,'number','is not null','exist',1)
INSERT [crm].[field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(18,2,'number','is null','does not exist',1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[field_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[field_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[saved_criterialist] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[saved_criterialist] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[saved_criterialist] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[saved_criterialist] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[tasklink_contact] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                              
----------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[tasklink_contact] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_files_version] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_files_version] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_quote_source] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_quote_source] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_quote_source] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_quote_source] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[opportunity_component] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[opportunity_component] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[opportunity_component] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[opportunity_component] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[tasklink_ticket] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                            
--------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[tasklink_ticket] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[search_fields] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[search_fields] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(1,'company','Company Name',1,0,NULL,NULL,1)
INSERT [crm].[search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(2,'namefirst','Contact First Name',1,0,NULL,NULL,1)
INSERT [crm].[search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(3,'namelast','Contact Last Name',1,0,NULL,NULL,1)
INSERT [crm].[search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(4,'entered','Entered Date',1,1,NULL,NULL,1)
INSERT [crm].[search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(5,'zip','Zip Code',1,0,NULL,NULL,1)
INSERT [crm].[search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(6,'areacode','Area Code',1,0,NULL,NULL,1)
INSERT [crm].[search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(7,'city','City',1,0,NULL,NULL,1)
INSERT [crm].[search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(8,'typeId','Contact Type',1,0,NULL,NULL,1)
INSERT [crm].[search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(9,'contactId','Contact ID',0,0,NULL,NULL,1)
INSERT [crm].[search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(10,'title','Contact Title',0,0,NULL,NULL,1)
INSERT [crm].[search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(11,'accountTypeId','Account Type',1,0,NULL,NULL,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[search_fields] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[search_fields] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_option_boolean] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                     
---------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_option_boolean] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_option_float] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                   
-------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_option_float] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[tasklink_project] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                              
----------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[tasklink_project] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_map] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_map] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[asset] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[asset] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[asset] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[asset] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[quote_entry] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_entry] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_entry] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[quote_entry] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[cfsinbox_messagelink] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[cfsinbox_messagelink] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[contact] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[contact] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[contact] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[contact] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_option_timestamp] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                         
-------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_option_timestamp] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[message] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[message] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[message] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[message] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[campaign] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[campaign] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[campaign] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[campaign] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[taskcategory_project] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                      
------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[taskcategory_project] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_option_integer] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                     
---------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_option_integer] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_conflict_log] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                         
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_conflict_log] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_files_download] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                    
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_files_download] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[product_catalog_category_map] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_catalog_category_map] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                  
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_catalog_category_map] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[product_catalog_category_map] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_option_text] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_product_option_text] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[business_process_component_library] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_component_library] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(1,'org.aspcfs.modules.troubletickets.components.LoadTicketDetails',1,'org.aspcfs.modules.troubletickets.components.Load
INSERT [crm].[business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(2,'org.aspcfs.modules.troubletickets.components.QueryTicketJustClosed',1,'org.aspcfs.modules.troubletickets.components.
INSERT [crm].[business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(3,'org.aspcfs.modules.components.SendUserNotification',1,'org.aspcfs.modules.components.SendUserNotification','Send an 
INSERT [crm].[business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(4,'org.aspcfs.modules.troubletickets.components.SendTicketSurvey',1,'org.aspcfs.modules.troubletickets.components.SendT
INSERT [crm].[business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(5,'org.aspcfs.modules.troubletickets.components.QueryTicketJustAssigned',1,'org.aspcfs.modules.troubletickets.component
INSERT [crm].[business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(6,'org.aspcfs.modules.troubletickets.components.GenerateTicketList',2,'org.aspcfs.modules.troubletickets.components.Gen
INSERT [crm].[business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(7,'org.aspcfs.modules.troubletickets.components.SendTicketListReport',2,'org.aspcfs.modules.troubletickets.components.S

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_component_library] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[business_process_component_library] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_orderaddress_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_orderaddress_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_orderaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Billing',0,0,1)
INSERT [crm].[lookup_orderaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Shipping',0,0,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_orderaddress_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_orderaddress_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[account_type_levels] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                           
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[account_type_levels] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[sync_log] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_log] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_log] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[sync_log] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[opportunity_component_levels] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[opportunity_component_levels] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[business_process_component_result_lookup] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_component_result_lookup] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[business_process_component_result_lookup] ([result_id],[component_id],[return_id],[description],[level],[enabled])VALUES(1,2,1,'Yes',0,1)
INSERT [crm].[business_process_component_result_lookup] ([result_id],[component_id],[return_id],[description],[level],[enabled])VALUES(2,2,0,'No',1,1)
INSERT [crm].[business_process_component_result_lookup] ([result_id],[component_id],[return_id],[description],[level],[enabled])VALUES(3,5,1,'Yes',0,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_component_result_lookup] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[business_process_component_result_lookup] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_product_conf_result] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_conf_result] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_conf_result] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_product_conf_result] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_team] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[project_team] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[message_template] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[message_template] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[message_template] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[message_template] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[order_address] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_address] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_address] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[order_address] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[contact_type_levels] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                               
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[contact_type_levels] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[sync_transaction_log] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_transaction_log] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[sync_transaction_log] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[sync_transaction_log] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[call_log] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[call_log] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[call_log] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[call_log] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[business_process_parameter_library] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_parameter_library] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(1,3,'notification.module',NULL,'Tickets',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(2,3,'notification.itemId',NULL,'${this.id}',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(3,3,'notification.itemModified',NULL,'${this.modified}',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(4,3,'notification.userToNotify',NULL,'${previous.enteredBy}',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(5,3,'notification.subject',NULL,'Dark Horse CRM Ticket Closed: ${this.paddedId}',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(6,3,'notification.body',NULL,'The following ticket in Dark Horse CRM has been closed:

--- Ticket Details ---

Tick
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(7,6,'notification.module',NULL,'Tickets',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(8,6,'notification.itemId',NULL,'${this.id}',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(9,6,'notification.itemModified',NULL,'${this.modified}',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(10,6,'notification.userToNotify',NULL,'${this.assignedTo}',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(11,6,'notification.subject',NULL,'Dark Horse CRM Ticket Assigned: ${this.paddedId}',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(12,6,'notification.body',NULL,'The following ticket in Dark Horse CRM has been assigned to you:

--- Ticket Details
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(13,7,'ticketList.onlyOpen',NULL,'true',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(14,7,'ticketList.onlyAssigned',NULL,'true',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(15,7,'ticketList.onlyUnassigned',NULL,'true',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(16,7,'ticketList.minutesOlderThan',NULL,'10',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(17,7,'ticketList.lastAnchor',NULL,'${process.lastAnchor}',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(18,7,'ticketList.nextAnchor',NULL,'${process.nextAnchor}',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(19,8,'notification.users.to',NULL,'${this.enteredBy}',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(20,8,'notification.contacts.to',NULL,'${this.contactId}',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(21,8,'notification.subject',NULL,'Dark Horse CRM Unassigned Ticket Report (${objects.size})',1)
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(22,8,'notification.body',NULL,'** This is an automated message **

The following tickets in Dark Horse CRM are unas
INSERT [crm].[business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(23,8,'report.ticket.content',NULL,'----- Ticket Details -----
Ticket # ${this.paddedId}
Created: ${this.enteredStri

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_parameter_library] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[business_process_parameter_library] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[product_option_configurator] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_configurator] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_configurator] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[product_option_configurator] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[process_log] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[process_log] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[process_log] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[process_log] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_currency] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_currency] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_currency] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_currency] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[business_process] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[business_process] ([process_id],[process_name],[description],[type_id],[link_module_id],[component_start_id],[enabled],[entered])VALUES(1,'dhv.ticket.insert','Ticket change notification',1,8,1,1,'May 28 2004 10:25:58:020AM')
INSERT [crm].[business_process] ([process_id],[process_name],[description],[type_id],[link_module_id],[component_start_id],[enabled],[entered])VALUES(2,'dhv.report.ticketList.overdue','Overdue ticket notification',2,8,7,1,'May 28 2004 10:25:58:780AM')

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[business_process] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[ticket_level] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_level] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[ticket_level] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Entry level',0,0,1)
INSERT [crm].[ticket_level] ([code],[description],[default_item],[level],[enabled])VALUES(2,'First level',0,1,1)
INSERT [crm].[ticket_level] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Second level',0,2,1)
INSERT [crm].[ticket_level] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Third level',0,3,1)
INSERT [crm].[ticket_level] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Top level',0,4,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_level] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[ticket_level] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[product_option] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[product_option] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[quote_product] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[quote_product] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_lists_lookup] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_lists_lookup] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(1,1,1,'lookupList','lookup_account_types',10,'Account Types','May 28 2004 10:25:37:710AM',1)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(2,1,2,'lookupList','lookup_revenue_types',20,'Revenue Types','May 28 2004 10:25:37:710AM',1)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(3,1,3,'contactType','lookup_contact_types',30,'Contact Types','May 28 2004 10:25:37:720AM',1)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(4,2,1,'contactType','lookup_contact_types',10,'Types','May 28 2004 10:25:37:860AM',2)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(5,2,2,'lookupList','lookup_contactemail_types',20,'Email Types','May 28 2004 10:25:37:860AM',2)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(6,2,3,'lookupList','lookup_contactaddress_types',30,'Address Types','May 28 2004 10:25:37:860AM',2)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(7,2,4,'lookupList','lookup_contactphone_types',40,'Phone Types','May 28 2004 10:25:37:870AM',2)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(8,4,1,'lookupList','lookup_stage',10,'Stage','May 28 2004 10:25:38:060AM',4)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(9,4,2,'lookupList','lookup_opportunity_types',20,'Opportunity Types','May 28 2004 10:25:38:070AM',4)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(10,8,1,'lookupList','lookup_ticketsource',10,'Ticket Source','May 28 2004 10:25:38:440AM',8)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(11,8,2,'lookupList','ticket_severity',20,'Ticket Severity','May 28 2004 10:25:38:440AM',8)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(12,8,3,'lookupList','ticket_priority',30,'Ticket Priority','May 28 2004 10:25:38:440AM',8)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(13,15,130041304,'lookupList','lookup_asset_status',10,'Asset Status','May 28 2004 10:25:38:943AM',130041000)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(14,16,130041305,'lookupList','lookup_sc_category',10,'Service Contract Category','May 28 2004 10:25:38:953AM',13
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(15,16,130041306,'lookupList','lookup_sc_type',20,'Service Contract Type','May 28 2004 10:25:38:963AM',130041100)
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(16,16,116041409,'lookupList','lookup_response_model',30,'Response Time Model','May 28 2004 10:25:38:963AM',13004
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(17,16,116041410,'lookupList','lookup_phone_model',40,'Phone Service Model','May 28 2004 10:25:38:963AM',13004110
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(18,16,116041411,'lookupList','lookup_onsite_model',50,'Onsite Service Model','May 28 2004 10:25:39:103AM',130041
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(19,16,116041412,'lookupList','lookup_email_model',60,'Email Service Model','May 28 2004 10:25:39:113AM',13004110
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(20,16,308041546,'lookupList','lookup_hours_reason',70,'Contract Hours Adjustment Reason','May 28 2004 10:25:39:1
INSERT [crm].[lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(21,21,1111031132,'lookupList','lookup_department',10,'Departments','May 28 2004 10:25:39:233AM',1111031131)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_lists_lookup] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_lists_lookup] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[role] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[role] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled],[role_type])VALUES(1,'Administrator','Performs system configuration and maintenance',0,'May 28 2004 10:25:39:303AM',0,'May 28 2004 10:25:39:303AM',1
INSERT [crm].[role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled],[role_type])VALUES(2,'Operations Manager','Manages operations',0,'May 28 2004 10:25:40:083AM',0,'May 28 2004 10:25:40:083AM',1,0)
INSERT [crm].[role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled],[role_type])VALUES(3,'Sales Manager','Manages all accounts and opportunities',0,'May 28 2004 10:25:40:907AM',0,'May 28 2004 10:25:40:907AM',1,0)
INSERT [crm].[role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled],[role_type])VALUES(4,'Salesperson','Manages own accounts and opportunities',0,'May 28 2004 10:25:41:757AM',0,'May 28 2004 10:25:41:757AM',1,0)
INSERT [crm].[role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled],[role_type])VALUES(5,'Customer Service Manager','Manages all tickets',0,'May 28 2004 10:25:42:407AM',0,'May 28 2004 10:25:42:407AM',1,0)
INSERT [crm].[role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled],[role_type])VALUES(6,'Customer Service Representative','Manages own tickets',0,'May 28 2004 10:25:42:950AM',0,'May 28 2004 10:25:42:950AM',1,0)
INSERT [crm].[role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled],[role_type])VALUES(7,'Marketing Manager','Manages communications',0,'May 28 2004 10:25:43:430AM',0,'May 28 2004 10:25:43:430AM',1,0)
INSERT [crm].[role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled],[role_type])VALUES(8,'Accounting Manager','Reviews revenue and opportunities',0,'May 28 2004 10:25:44:080AM',0,'May 28 2004 10:25:44:080AM',1,0)
INSERT [crm].[role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled],[role_type])VALUES(9,'HR Representative','Manages employee information',0,'May 28 2004 10:25:44:660AM',0,'May 28 2004 10:25:44:660AM',1,0)
INSERT [crm].[role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled],[role_type])VALUES(10,'Customer','Customer portal user',0,'May 28 2004 10:25:44:900AM',0,'May 28 2004 10:25:44:900AM',1,1)
INSERT [crm].[role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled],[role_type])VALUES(11,'Products and Services Customer','Products and Services portal user',0,'May 28 2004 10:25:44:970AM',0,'May 28 2004 10:25:44:97

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[role] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[role] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[saved_criteriaelement] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[saved_criteriaelement] CHECK CONSTRAINT ALL

GO
 
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_payment_methods] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_payment_methods] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_payment_methods] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Cash',0,0,1)
INSERT [crm].[lookup_payment_methods] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Credit Card',0,0,1)
INSERT [crm].[lookup_payment_methods] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Personal Check',0,0,1)
INSERT [crm].[lookup_payment_methods] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Money Order',0,0,1)
INSERT [crm].[lookup_payment_methods] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Certified Check',0,0,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_payment_methods] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_payment_methods] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[autoguide_make] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_make] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_make] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[autoguide_make] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[campaign_run] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[campaign_run] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[campaign_run] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[campaign_run] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_product_category_type] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_category_type] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_product_category_type] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_product_category_type] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[business_process_component] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_component] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(1,1,1,NULL,NULL,1)
INSERT [crm].[business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(2,1,2,1,NULL,1)
INSERT [crm].[business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(3,1,3,2,1,1)
INSERT [crm].[business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(4,1,4,2,1,0)
INSERT [crm].[business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(5,1,5,2,0,1)
INSERT [crm].[business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(6,1,3,5,1,1)
INSERT [crm].[business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(7,2,6,NULL,NULL,1)
INSERT [crm].[business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(8,2,7,7,NULL,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_component] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[business_process_component] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[category_editor_lookup] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[category_editor_lookup] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[category_editor_lookup] ([id],[module_id],[constant_id],[table_name],[level],[description],[entered],[category_id],[max_levels])VALUES(1,8,202041401,'ticket_category',10,'Ticket Categories','May 28 2004 10:25:38:500AM',8,4)
INSERT [crm].[category_editor_lookup] ([id],[module_id],[constant_id],[table_name],[level],[description],[entered],[category_id],[max_levels])VALUES(2,15,202041400,'asset_category',10,'Asset Categories','May 28 2004 10:25:38:943AM',130041000,3)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[category_editor_lookup] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[category_editor_lookup] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[ticket_severity] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_severity] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[ticket_severity] ([code],[description],[style],[default_item],[level],[enabled])VALUES(1,'Normal','background-color:lightgreen;color:black;',1,0,1)
INSERT [crm].[ticket_severity] ([code],[description],[style],[default_item],[level],[enabled])VALUES(2,'Important','background-color:yellow;color:black;',0,1,1)
INSERT [crm].[ticket_severity] ([code],[description],[style],[default_item],[level],[enabled])VALUES(3,'Critical','background-color:red;color:black;font-weight:bold;',0,2,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[ticket_severity] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[ticket_severity] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[help_module] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_module] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(1,12,'This is the "Home Page". The top set of tabs shows the individual modules that you can access. The second row are the functions availa
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(2,2,'The purpose of this module is for the users to view contacts and add new contacts. You can also search for contacts as well as export c
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(3,4,'Pipeline helps in creating opportunities or leads in the company. Each opportunity helps to follow-up on a lead, which might eventually
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(4,1,'You are looking at the Accounts module homepage, which has a dashboard view of all your accounts. This view is based on a date range se
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(5,6,'Communications is a "Campaign Manager" Module where you can manage complex email. fax, or mail communications with your customers. Comm
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(6,8,'You are looking at the Help Desk module home page. The dashboard shows the most recent tickets that have been assigned to you, as well 
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(7,21,'You are looking at the Employee module home page. This page displays the details of all the employees present in the system.','The mai
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(8,14,'You are looking at the Reports module home page. This page displays a list of generated reports that are ready to be downloaded. It al
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(9,9,'The admin module lets the user review the system usage, configure modules, and configure the global/system parameters.','This Admin Sys
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(10,3,'Auto Guide Brief','Auto Guide Detail')
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(11,10,'Help Brief','Help Detail')
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(12,5,'Demo Brief','Demo Detail')
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(13,11,'System Brief','System Detail')
INSERT [crm].[help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(14,17,'Brief description for product catalog','detail description for product catalog')

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_module] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[help_module] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[access] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[access] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[access] ([user_id],[username],[password],[contact_id],[role_id],[manager_id],[startofday],[endofday],[locale],[timezone],[last_ip],[last_login],[enteredby],[entered],[modifiedby],[modified],[expires],[alias],[assistant],[enabled])VALUES(0,'dh

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[access] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[access] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_project_activity] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_project_activity] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(1,'Project Initialization',0,1,1,0,0)
INSERT [crm].[lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(2,'Analysis/Software Requirements',0,2,1,0,0)
INSERT [crm].[lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(3,'Functional Specifications',0,3,1,0,0)
INSERT [crm].[lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(4,'Prototype',0,4,1,0,0)
INSERT [crm].[lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(5,'System Development',0,5,1,0,0)
INSERT [crm].[lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(6,'Testing',0,6,1,0,0)
INSERT [crm].[lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(7,'Training',0,7,1,0,0)
INSERT [crm].[lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(8,'Documentation',0,8,1,0,0)
INSERT [crm].[lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(9,'Deployment',0,9,1,0,0)
INSERT [crm].[lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(10,'Post Implementation Review',0,10,1,0,0)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_project_activity] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_project_activity] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[autoguide_model] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_model] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_model] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[autoguide_model] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_creditcard_types] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_creditcard_types] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_creditcard_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Visa',0,0,1)
INSERT [crm].[lookup_creditcard_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Master Card',0,0,1)
INSERT [crm].[lookup_creditcard_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'American Express',0,0,1)
INSERT [crm].[lookup_creditcard_types] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Discover',0,0,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_creditcard_types] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_creditcard_types] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[permission_category] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[permission_category] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(1,'Accounts',NULL,700,1,1,1,1,0,0,0,0,1)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(2,'Contacts',NULL,500,1,1,1,1,0,0,0,0,1)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(3,'Auto Guide',NULL,800,0,0,0,0,0,0,0,0,0)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(4,'Pipeline',NULL,600,1,1,0,1,1,0,0,0,1)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(5,'Demo',NULL,2100,0,0,0,0,0,0,0,0,0)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(6,'Communications',NULL,1200,1,1,0,0,0,0,0,0,1)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(7,'Projects',NULL,1300,0,0,0,0,0,0,0,0,0)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(8,'Help Desk',NULL,1600,1,1,1,1,0,1,1,1,1)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(9,'Admin',NULL,1800,1,1,0,0,0,0,0,0,1)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(10,'Help',NULL,1900,1,1,0,0,0,0,0,0,0)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(11,'System',NULL,100,1,1,0,0,0,0,0,0,0)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(12,'My Home Page',NULL,200,1,1,0,0,0,0,0,0,1)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(13,'QA',NULL,2000,0,0,0,0,0,0,0,0,0)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(14,'Reports',NULL,1700,1,1,0,0,0,0,0,0,0)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(15,'Assets',NULL,1500,1,1,0,1,0,1,0,0,0)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(16,'Service Contracts',NULL,1400,1,1,0,1,0,0,0,0,0)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(17,'Product Catalog',NULL,1100,1,1,1,0,0,0,0,0,0)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(18,'Products and Services',NULL,300,0,0,0,0,0,0,0,0,0)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(19,'Quotes',NULL,900,1,1,0,0,0,0,0,0,0)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(20,'Orders',NULL,1000,1,1,0,0,0,0,0,0,1)
INSERT [crm].[permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(21,'Employees',NULL,400,1,1,0,1,0,0,0,0,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[permission_category] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[permission_category] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[help_contents] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_contents] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(1,12,1,'MyCFS.do','Home',NUL
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(2,12,1,'MyCFSInbox.do','Inbo
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(3,12,1,'MyCFSInbox.do','CFSN
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(4,12,1,'MyCFSInbox.do','NewM
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(5,12,1,'MyCFSInbox.do','Repl
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(6,12,1,'MyCFSInbox.do','Send
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(7,12,1,'MyCFSInbox.do','Forw
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(8,12,1,'MyTasks.do',NULL,NUL
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(9,12,1,'MyTasks.do','New',NU
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(10,12,1,'MyTasksForward.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(11,12,1,'MyTasks.do','Modify
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(12,12,1,'MyActionLists.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(13,12,1,'MyActionContacts.do
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(14,12,1,'MyActionLists.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(15,12,1,'MyActionLists.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(16,12,1,'Reassignments.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(17,12,1,'MyCFS.do','MyProfil
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(18,12,1,'MyCFSProfile.do','M
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(19,12,1,'MyCFSSettings.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(20,12,1,'MyCFSPassword.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(21,12,1,'MyTasks.do','ListTa
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(22,12,1,'MyTasks.do',NULL,NU
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(23,12,1,'MyCFS.do','Home',NU
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(24,12,1,'MyTasksForward.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(25,12,1,'MyCFSProfile.do','U
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(26,12,1,'MyCFSInbox.do','CFS
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(27,12,1,'MyActionContacts.do
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(28,12,1,'MyActionContacts.do
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(29,12,1,'MyTasks.do','ListTa
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(30,12,1,'MyCFSInbox.do','Inb
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(31,12,1,'MyActionContacts.do
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(32,12,1,'Reassignments.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(33,12,1,'TaskForm.do','Prepa
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(34,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(35,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(36,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(37,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(38,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(39,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(40,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(41,2,2,'ExternalContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(42,2,2,'ExternalContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(43,2,2,'ExternalContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(44,2,2,'ExternalContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(45,2,2,'ExternalContactsOpps
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(46,2,2,'ExternalContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(47,2,2,'ExternalContactsOppC
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(48,2,2,'ExternalContactsOpps
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(49,2,2,'ExternalContactsOppC
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(50,2,2,'ExternalContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(51,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(52,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(53,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(54,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(55,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(56,2,2,'ExternalContactsOpps
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(57,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(58,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(59,2,2,'ExternalContactsOppC
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(60,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(61,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(62,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(63,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(64,2,2,'ExternalContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(65,2,2,'ExternalContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(66,2,2,'ExternalContactsOppC
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(67,2,2,'ExternalContactsOpps
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(68,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(69,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(70,2,2,'ExternalContactsOpps
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(71,2,2,'ContactsList.do','Co
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(72,2,2,'Contacts.do','Detail
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(73,2,2,'Contacts.do','ViewMe
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(74,2,2,'ContactForm.do','Pre
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(75,2,2,'ExternalContacts.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(76,2,2,'ExternalContactsImpo
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(77,2,2,'ExternalContactsImpo
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(78,2,2,'ExternalContactsImpo
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(79,4,3,'Leads.do','Dashboard
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(80,4,3,'Leads.do','Prepare',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(81,4,3,'Leads.do','SearchFor
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(82,4,3,'LeadsReports.do','De
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(83,4,3,'Leads.do','SearchOpp
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(84,4,3,'Leads.do','Reports',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(85,4,3,'LeadsComponents.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(86,4,3,'LeadsCalls.do','Add'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(87,4,3,'Leads.do','ModifyOpp
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(88,4,3,'LeadsCalls.do','Inse
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(89,4,3,'LeadsDocuments.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(90,4,3,'LeadsCallsForward.do
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(91,4,3,'Leads.do','Save',NUL
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(92,4,3,'Leads.do','GenerateF
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(93,4,3,'LeadsComponents.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(94,4,3,'LeadsDocuments.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(95,4,3,'Leads.do','Search',N
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(96,4,3,'Leads.do','UpdateOpp
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(97,4,3,'LeadsCallsForward.do
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(98,4,3,'LeadsDocuments.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(99,4,3,'LeadsCalls.do','Modi
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(100,4,3,'Leads.do','DetailsO
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(101,4,3,'LeadsCalls.do','Vie
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(102,4,3,'Leads.do','ViewOpp'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(103,4,3,'Leads.do',NULL,NULL
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(104,4,3,'LeadsComponents.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(105,4,3,'LeadsComponents.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(106,4,3,'LeadsCalls.do','Upd
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(107,4,3,'LeadsCalls.do','Det
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(108,4,3,'LeadsDocuments.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(109,4,3,'LeadsDocuments.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(110,4,3,'LeadsReports.do','E
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(111,4,3,'LeadsReports.do','E
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(112,1,4,'Accounts.do','Dashb
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(113,1,4,'Accounts.do','Add',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(114,1,4,'Accounts.do','Modif
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(115,1,4,'Contacts.do','View'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(116,1,4,'Accounts.do','Field
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(117,1,4,'Opportunities.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(118,1,4,'RevenueManager.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(119,1,4,'RevenueManager.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(120,1,4,'RevenueManager.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(121,1,4,'RevenueManager.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(122,1,4,'Accounts.do','ViewT
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(123,1,4,'AccountsDocuments.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(124,1,4,'Accounts.do','Searc
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(125,1,4,'Accounts.do','Detai
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(126,1,4,'RevenueManager.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(127,1,4,'Accounts.do','Repor
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(128,1,4,'Accounts.do','Modif
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(129,1,4,'AccountTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(130,1,4,'Accounts.do','Delet
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(131,1,4,'Accounts.do','Gener
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(132,1,4,'AccountContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(133,1,4,'Accounts.do','AddFo
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(134,1,4,'AccountContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(135,1,4,'AccountsDocuments.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(136,1,4,'AccountsDocuments.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(137,1,4,'AccountContactsOpps
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(138,1,4,'AccountTicketsDocum
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(139,1,4,'Accounts.do','Detai
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(140,1,4,'Accounts.do','View'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(141,1,4,'AccountTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(142,1,4,'AccountTicketsDocum
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(143,1,4,'AccountTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(144,1,4,'AccountContactsOppC
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(145,1,4,'Accounts.do','Inser
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(146,1,4,'AccountContactsOpps
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(147,1,4,'Accounts.do','Searc
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(148,1,4,'AccountTicketsDocum
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(149,1,4,'AccountTicketsDocum
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(150,1,4,'Accounts.do','Inser
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(151,1,4,'AccountContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(152,1,4,'AccountContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(153,1,4,'AccountContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(154,1,4,'AccountContactsCall
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(155,1,4,'AccountContactsOpps
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(156,1,4,'AccountContactsOpps
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(157,1,4,'AccountTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(158,1,4,'AccountTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(159,1,4,'AccountTicketTasks.
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(160,1,4,'AccountTicketsDocum
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(161,1,4,'AccountTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(162,1,4,'AccountsDocuments.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(163,1,4,'AccountsDocuments.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(164,1,4,'AccountsDocuments.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(165,1,4,'Accounts.do','Expor
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(166,1,4,'RevenueManager.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(167,1,4,'RevenueManager.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(168,1,4,'OpportunityForm.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(169,1,4,'Opportunities.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(170,1,4,'Opportunities.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(171,1,4,'Opportunities.do','
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(172,1,4,'OpportunitiesCompon
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(173,1,4,'OpportunitiesCompon
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(174,1,4,'OpportunitiesCompon
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(175,1,4,'OpportunitiesCompon
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(176,1,4,'AccountsServiceCont
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(177,1,4,'AccountsServiceCont
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(178,1,4,'AccountsServiceCont
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(179,1,4,'AccountsServiceCont
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(180,1,4,'AccountsAssets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(181,1,4,'AccountsAssets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(182,1,4,'AccountsAssets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(183,1,4,'AccountsAssets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(184,1,4,'AccountsAssets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(185,1,4,'Contacts.do','Prepa
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(186,1,4,'Contacts.do','Modif
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(187,1,4,'ContactsPortal.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(188,1,4,'ContactsPortal.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(189,1,4,'ContactsPortal.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(190,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(191,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(192,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(193,6,5,'CampaignManagerGrou
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(194,6,5,'CampaignManagerGrou
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(195,6,5,'CampaignManagerMess
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(196,6,5,'CampaignManagerMess
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(197,6,5,'CampaignManagerAtta
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(198,6,5,'CampaignManagerGrou
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(199,6,5,'CampaignDocuments.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(200,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(201,6,5,'CampaignManagerMess
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(202,6,5,'CampaignManagerGrou
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(203,6,5,'CampaignDocuments.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(204,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(205,6,5,'CampaignManagerGrou
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(206,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(207,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(208,6,5,'CampaignManagerSurv
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(209,6,5,'CampaignManagerSurv
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(210,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(211,6,5,'CampaignDocuments.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(212,6,5,'CampaignDocuments.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(213,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(214,6,5,'CampaignManagerSurv
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(215,6,5,'CampaignManagerMess
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(216,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(217,6,5,'CampaignManagerGrou
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(218,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(219,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(220,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(221,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(222,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(223,6,5,'CampaignDocuments.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(224,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(225,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(226,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(227,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(228,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(229,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(230,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(231,6,5,'CampaignManagerMess
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(232,6,5,'CampaignManagerMess
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(233,6,5,'CampaignManagerMess
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(234,6,5,'CampaignManagerMess
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(235,6,5,'CampaignManagerSurv
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(236,6,5,'CampaignManagerSurv
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(237,6,5,'CampaignManagerSurv
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(238,6,5,'CampaignManagerSurv
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(239,6,5,'CampaignManager.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(240,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(241,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(242,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(243,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(244,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(245,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(246,8,6,'TroubleTicketTasks.
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(247,8,6,'TroubleTicketsDocum
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(248,8,6,'TroubleTicketsFolde
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(249,8,6,'TroubleTicketsFolde
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(250,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(251,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(252,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(253,8,6,'TroubleTicketsFolde
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(254,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(255,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(256,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(257,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(258,8,6,'TroubleTicketsFolde
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(259,8,6,'TroubleTicketsFolde
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(260,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(261,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(262,8,6,'TroubleTicketsDocum
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(263,8,6,'TroubleTicketsDocum
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(264,8,6,'TroubleTicketsDocum
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(265,8,6,'TroubleTicketsDocum
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(266,8,6,'TroubleTickets.do',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(267,8,6,'TroubleTicketActivi
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(268,8,6,'TroubleTicketActivi
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(269,8,6,'TroubleTicketActivi
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(270,8,6,'TroubleTicketActivi
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(271,8,6,'TroubleTicketMainte
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(272,8,6,'TroubleTicketMainte
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(273,8,6,'TroubleTicketMainte
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(274,21,7,'CompanyDirectory.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(275,21,7,'CompanyDirectory.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(276,21,7,'CompanyDirectory.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(277,21,7,'CompanyDirectory.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(278,21,7,'CompanyDirectory.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(279,14,8,'Reports.do','ViewQ
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(280,14,8,'Reports.do','RunRe
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(281,14,8,'Reports.do',NULL,N
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(282,14,8,'Reports.do','Cance
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(283,14,8,'Reports.do','Param
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(284,14,8,'Reports.do','ListR
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(285,14,8,'Reports.do','Crite
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(286,14,8,'Reports.do','Gener
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(287,14,8,'Reports.do',NULL,N
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(288,9,9,'Users.do','ListUser
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(289,9,9,'Users.do','InsertUs
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(290,9,9,'Users.do','ModifyUs
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(291,9,9,'Users.do','ViewLog'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(292,9,9,'Viewpoints.do','Lis
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(293,9,9,'Viewpoints.do','Ins
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(294,9,9,'Viewpoints.do','Vie
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(295,9,9,'Roles.do','ListRole
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(296,9,9,'Roles.do','InsertRo
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(297,9,9,'Roles.do','RoleDeta
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(298,9,9,'Admin.do','Config',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(299,9,9,'Admin.do','ConfigDe
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(300,9,9,'Admin.do','ModifyLi
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(301,9,9,'AdminFieldsFolder.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(302,9,9,'AdminFieldsFolder.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(303,9,9,'AdminConfig.do','Li
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(304,9,9,'AdminConfig.do','Mo
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(305,9,9,'Admin.do','Usage',N
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(306,9,9,'Admin.do',NULL,NULL
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(307,9,9,'Users.do','DisableU
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(308,9,9,'AdminFieldsFolder.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(309,9,9,'AdminFieldsFolder.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(310,9,9,'AdminFields.do','Mo
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(311,9,9,'Admin.do','ListGlob
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(312,9,9,'Admin.do','ModifyTi
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(313,9,9,'AdminObjectEvents.d
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(314,9,9,'AdminFieldsGroup.do
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(315,9,9,'AdminFields.do','Ad
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(316,9,9,'Admin.do','UpdateLi
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(317,9,9,'AdminScheduledEvent
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(318,9,9,'Admin.do','Config',
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(319,9,9,'Admin.do','EditList
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(320,9,9,'AdminFieldsGroup.do
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(321,9,9,'AdminCategories.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(322,9,9,'AdminCategories.do'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(323,9,9,'Users.do','InsertUs
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(324,9,9,'Users.do','UpdateUs
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(325,9,9,'Users.do','AddUser'
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(326,9,9,'Users.do','UserDeta
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(327,9,9,'Roles.do',NULL,NULL
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(328,9,9,'Roles.do','ListRole
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(329,9,9,'Viewpoints.do','Ins
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(330,9,9,'Viewpoints.do','Ins
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(331,9,9,'Admin.do',NULL,NULL
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(332,11,13,'Search.do','SiteS
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(333,11,13,'Search.do','SiteS
INSERT [crm].[help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(334,17,14,'ProductsCatalog.d

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[help_contents] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[help_contents] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[product_category] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_category] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_category] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[product_category] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[product_option_values] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_values] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[product_option_values] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[product_option_values] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[viewpoint] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[viewpoint] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[viewpoint] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[viewpoint] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[business_process_parameter] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_parameter] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_parameter] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[business_process_parameter] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[autoguide_vehicle] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_vehicle] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[autoguide_vehicle] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[autoguide_vehicle] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_ticketsource] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_ticketsource] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_ticketsource] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Phone',0,1,1)
INSERT [crm].[lookup_ticketsource] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Email',0,2,1)
INSERT [crm].[lookup_ticketsource] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Web',0,3,1)
INSERT [crm].[lookup_ticketsource] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Letter',0,4,1)
INSERT [crm].[lookup_ticketsource] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Other',0,5,1)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_ticketsource] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_ticketsource] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[excluded_recipient] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[excluded_recipient] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                        
----------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[excluded_recipient] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[excluded_recipient] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[order_payment] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_payment] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[order_payment] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[order_payment] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[lookup_project_priority] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_project_priority] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[lookup_project_priority] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(1,'Low',0,1,1,0,NULL,10)
INSERT [crm].[lookup_project_priority] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(2,'Normal',1,2,1,0,NULL,20)
INSERT [crm].[lookup_project_priority] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(3,'High',0,3,1,0,NULL,30)

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[lookup_project_priority] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[lookup_project_priority] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[quote_product_options] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product_options] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[quote_product_options] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[quote_product_options] OFF
GO
SET NOCOUNT OFF
 
SET NOCOUNT ON
 
SET IDENTITY_INSERT [crm].[business_process_component_parameter] ON
GO
 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_component_parameter] NOCHECK CONSTRAINT ALL

GO
 
                                                                                                                                                                                                                                                                 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(1,3,1,'Tickets',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(2,3,2,'${this.id}',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(3,3,3,'${this.modified}',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(4,3,4,'${previous.enteredBy}',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(5,3,5,'Dark Horse CRM Ticket Closed: ${this.paddedId}',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(6,3,6,'The following ticket in Dark Horse CRM has been closed:

--- Ticket Details ---

Ticket # ${this.paddedId}
Priority: ${ticketPrior
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(7,6,7,'Tickets',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(8,6,8,'${this.id}',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(9,6,9,'${this.modified}',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(10,6,10,'${this.assignedTo}',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(11,6,11,'Dark Horse CRM Ticket Assigned: ${this.paddedId}',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(12,6,12,'The following ticket in Dark Horse CRM has been assigned to you:

--- Ticket Details ---

Ticket # ${this.paddedId}
Priority: ${
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(13,7,13,'true',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(14,7,14,'true',0)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(15,7,15,'true',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(16,7,16,'10',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(17,7,17,'${process.lastAnchor}',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(18,7,18,'${process.nextAnchor}',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(19,8,19,'${this.enteredBy}',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(20,8,20,'${this.contactId}',0)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(21,8,21,'Dark Horse CRM Unassigned Ticket Report (${objects.size})',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(22,8,22,'** This is an automated message **

The following tickets in Dark Horse CRM are unassigned and need attention:

',1)
INSERT [crm].[business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(23,8,23,'----- Ticket Details -----
Ticket # ${this.paddedId}
Created: ${this.enteredString}
Organization: ${ticketOrganization.name}
Pri

 
--                                                                                                                                                                                                                                                               
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
ALTER TABLE [crm].[business_process_component_parameter] CHECK CONSTRAINT ALL

GO
 
SET IDENTITY_INSERT [crm].[business_process_component_parameter] OFF
GO
SET NOCOUNT OFF
