/* Add a new field to lookup_lists_lookup to handle non-sequential inserts */
ALTER TABLE lookup_lists_lookup ADD category_id INT;
UPDATE lookup_lists_lookup SET category_id = module_id;
ALTER TABLE lookup_lists_lookup ALTER COLUMN category_id INT NOT NULL;

UPDATE permission 
SET description = 'Access to General Contacts module' 
WHERE description = 'Access to Contacts & Resources module';
