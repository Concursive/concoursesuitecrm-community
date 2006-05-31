ALTER TABLE lookup_step_actions ADD constant_id INT;
--Add constant values
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
--Set constraint
ALTER TABLE lookup_step_actions ALTER COLUMN constant_id INT  NOT NULL;
ALTER TABLE lookup_step_actions WITH NOCHECK ADD
   UNIQUE  NONCLUSTERED
	(
 	  [constant_id]
	)  ON [PRIMARY];

--Fix step_action_map to use 'Action' constants
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

--Fix action_step to use 'Action' constants
--Create a temp column 'action_constant_id' and copy action constants based on existing action_step.action_id values

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

ALTER TABLE action_step ADD action_constant_id INT;
UPDATE action_step SET action_constant_id = (SELECT constant_id FROM lookup_step_actions lsa WHERE action_step.action_id = lsa.code);
ALTER TABLE action_step DROP COLUMN action_id;

ALTER TABLE action_step ADD action_id INT;
UPDATE action_step SET action_id = (SELECT action_constant_id FROM action_step asp WHERE action_step.step_id = asp.step_id);
ALTER TABLE action_step DROP COLUMN action_constant_id;
