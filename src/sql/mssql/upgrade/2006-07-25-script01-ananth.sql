-- New Sync API Mappings to support web services

-- Account History
INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'accountHistory', 'org.aspcfs.modules.accounts.base.OrganizationHistory');
INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'accountHistoryList', 'org.aspcfs.modules.accounts.base.OrganizationHistoryList');

-- Action Plan Notes
INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'actionPlanWorkNote', 'org.aspcfs.modules.actionplans.base.ActionPlanWorkNote');
INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'actionPlanWorkNoteList', 'org.aspcfs.modules.actionplans.base.ActionPlanWorkNoteList');

-- Action Item Notes
INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'actionItemWorkNote', 'org.aspcfs.modules.actionplans.base.ActionItemWorkNote');
INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'actionItemWorkNoteList', 'org.aspcfs.modules.actionplans.base.ActionItemWorkNoteList');



