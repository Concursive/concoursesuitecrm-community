/* Add a new field to lookup_lists_lookup to handle non-sequential inserts */
ALTER TABLE lookup_lists_lookup ADD COLUMN category_id INT;
UPDATE lookup_lists_lookup SET category_id = module_id;
ALTER TABLE lookup_lists_lookup ADD CONSTRAINT notnullchk CHECK (category_id IS NOT NULL);

