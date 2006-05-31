INSERT INTO sync_table (table_id, system_id, element_name, mapped_class_name)
VALUES ((SELECT GEN_ID ("SYNC_TABLE_TABLE_ID_SEQ", 1) FROM RDB$DATABASE ), 4, 'customFieldDataList', 'org.aspcfs.modules.base.CustomFieldDataList');
