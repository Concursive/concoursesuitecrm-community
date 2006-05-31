ALTER TABLE opportunity_header ADD site_id INT REFERENCES lookup_site_id(code);

UPDATE opportunity_header
SET site_id = organization.site_id
FROM organization
WHERE opportunity_header.acctlink = organization.org_id
AND opportunity_header.acctlink > -1
AND opportunity_header.acctlink IS NOT NULL;

UPDATE opportunity_header
SET site_id = contact.site_id
FROM contact
WHERE opportunity_header.contactlink = contact.contact_id
AND opportunity_header.contactlink > -1
AND opportunity_header.contactlink IS NOT NULL;

ALTER TABLE document_store_role_member ADD site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE document_store_user_member ADD site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE document_store_department_member ADD site_id INTEGER REFERENCES lookup_site_id(code);

UPDATE document_store_user_member SET site_id = (SELECT site_id FROM access ac WHERE ac.user_id = item_id);

UPDATE lookup_im_services SET description = 'Jabber' where description = 'Jabber Instant Messenger';
UPDATE lookup_im_services SET description = 'AIM' where description = 'AOL Instant Messenger';
UPDATE lookup_im_services SET description = 'MSN' where description = 'MSN Instant Messenger';

ALTER TABLE action_step ADD target_relationship VARCHAR(80);

CREATE TABLE action_step_account_types (
  step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  type_id INTEGER NOT NULL REFERENCES lookup_account_types(code)
);

UPDATE lookup_step_actions SET constant_id = 110061030 WHERE description='Require user to attach or create an opportunity';
UPDATE lookup_step_actions SET constant_id = 110061031 WHERE description='Require user to attach or upload a document';
UPDATE lookup_step_actions SET constant_id = 110061032 WHERE description='Require user to attach or create an activity';
UPDATE lookup_step_actions SET constant_id = 110061033 WHERE description='Require user to update a specific action plan folder';
UPDATE lookup_step_actions SET constant_id = 110061034 WHERE description='Require user to update the Rating';
UPDATE lookup_step_actions SET constant_id = 110061035 WHERE description='Require user to attach or create a single Note';
UPDATE lookup_step_actions SET constant_id = 110061036 WHERE description='Require user to attach or create multiple Notes';
UPDATE lookup_step_actions SET constant_id = 110061037 WHERE description='Require user to attach or create a Lookup List';
UPDATE lookup_step_actions SET constant_id = 110061038 WHERE description='View Account';
UPDATE lookup_step_actions SET constant_id = 110061039 WHERE description='Require user to attach or create an account contact';
UPDATE lookup_step_actions SET constant_id = 110061040 WHERE description='Require user to create account relationships';
ALTER TABLE lookup_step_actions ALTER COLUMN constant_id INT  NOT NULL;
ALTER TABLE lookup_step_actions WITH NOCHECK ADD
   UNIQUE  NONCLUSTERED
	(
 	  [constant_id]
	)  ON [PRIMARY];

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CNSTR CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(o.name)
     FROM dbo.syscolumns
     INNER JOIN dbo.sysobjects ON syscolumns.id = sysobjects.id
     INNER JOIN dbo.sysreferences ON syscolumns.id = sysreferences.fkeyid
     INNER JOIN dbo.sysobjects o ON sysreferences.constid = o.id
     WHERE sysobjects.name = ''step_action_map''
       AND syscolumns.name = ''action_id''
       AND o.name LIKE ''FK__step_acti__actio%''
OPEN CNSTR
FETCH CNSTR INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN
    EXEC sp_executesql @sql
    FETCH CNSTR INTO @sql
END
CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)','[step_action_map]'
GO

ALTER TABLE step_action_map ADD action_constant_id INT REFERENCES lookup_step_actions(constant_id);
UPDATE step_action_map SET action_constant_id = (SELECT constant_id FROM lookup_step_actions lsa WHERE step_action_map.action_id = lsa.code);
ALTER TABLE step_action_map ALTER COLUMN action_constant_id INT NOT NULL;
ALTER TABLE step_action_map DROP COLUMN action_id;

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CNSTR CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(o.name)
     FROM dbo.syscolumns
     INNER JOIN dbo.sysobjects ON syscolumns.id = sysobjects.id
     INNER JOIN dbo.sysreferences ON syscolumns.id = sysreferences.fkeyid
     INNER JOIN dbo.sysobjects o ON sysreferences.constid = o.id
     WHERE sysobjects.name = ''action_step''
       AND syscolumns.name = ''action_id''
       AND o.name LIKE ''FK__action_st__actio%''
OPEN CNSTR
FETCH CNSTR INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN
    EXEC sp_executesql @sql
    FETCH CNSTR INTO @sql
END
CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)','[action_step]'
GO

UPDATE action_step SET action_id = 10 WHERE action_id = 0;
ALTER TABLE action_step ADD action_constant_id INT;
UPDATE action_step SET action_constant_id = (SELECT constant_id FROM lookup_step_actions lsa WHERE action_step.action_id = lsa.code);
ALTER TABLE action_step DROP COLUMN action_id;

ALTER TABLE action_step ADD action_id INT;
UPDATE action_step SET action_id = action_constant_id;
ALTER TABLE action_step DROP COLUMN action_constant_id;

ALTER TABLE action_phase ADD global BIT DEFAULT 0;
UPDATE action_phase SET global = 0;

INSERT INTO action_plan_constants (constant_id, description) VALUES(110061020, 'relationship');

ALTER TABLE action_plan_work_notes ALTER COLUMN description VARCHAR(4096);
ALTER TABLE action_item_work_notes ALTER COLUMN description VARCHAR(4096);

ALTER TABLE action_step ADD allow_update BIT NOT NULL DEFAULT 1;
UPDATE action_step SET allow_update = 1;

ALTER TABLE action_step ALTER COLUMN action_id INT NULL;
