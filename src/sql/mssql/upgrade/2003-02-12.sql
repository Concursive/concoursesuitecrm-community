/* Add a new field to lookup_lists_lookup to handle non-sequential inserts */
ALTER TABLE lookup_lists_lookup ADD category_id INT;
GO
UPDATE lookup_lists_lookup SET category_id = module_id;
GO
ALTER TABLE lookup_lists_lookup ALTER COLUMN category_id INT NOT NULL;
GO

UPDATE permission 
SET description = 'Access to General Contacts module' 
WHERE description = 'Access to Contacts & Resources module';
GO

/* TODO: VERIFY THESE ENTRIES BEFORE INSTALL */
UPDATE lookup_lists_lookup SET category_id = 1 WHERE module_id = 5
UPDATE lookup_lists_lookup SET category_id = 2 WHERE module_id = 3
UPDATE lookup_lists_lookup SET category_id = 4 WHERE module_id = 4
UPDATE lookup_lists_lookup SET category_id = 8 WHERE module_id = 8
GO
