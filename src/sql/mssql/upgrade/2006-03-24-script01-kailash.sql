ALTER TABLE contact_textmessageaddress ADD textmessageaddress_type_temp INTEGER;
UPDATE contact_textmessageaddress SET textmessageaddress_type_temp = textmessageaddress_type;

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CNSTR CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(o.name)
     FROM dbo.syscolumns
     INNER JOIN dbo.sysobjects ON syscolumns.id = sysobjects.id
     INNER JOIN dbo.sysreferences ON syscolumns.id = sysreferences.fkeyid
     INNER JOIN dbo.sysobjects o ON sysreferences.constid = o.id
     WHERE sysobjects.name = ''contact_textmessageaddress''
       AND syscolumns.name = ''textmessageaddress_type''
       AND o.name LIKE ''FK__contact_t__textm__%''
OPEN CNSTR
FETCH CNSTR INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN
    EXEC sp_executesql @sql
    FETCH CNSTR INTO @sql
END
CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)','[contact_textmessageaddress]'
GO

ALTER TABLE contact_textmessageaddress DROP COLUMN textmessageaddress_type;

ALTER TABLE contact_textmessageaddress ADD textmessageaddress_type INTEGER REFERENCES lookup_textmessage_types(code);
UPDATE contact_textmessageaddress SET textmessageaddress_type = textmessageaddress_type_temp;
ALTER TABLE contact_textmessageaddress DROP COLUMN textmessageaddress_type_temp;
