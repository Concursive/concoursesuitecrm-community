/* 3/14/2002 */

ALTER TABLE saved_criterialist ADD COLUMN contact_source INT;
ALTER TABLE saved_criterialist ALTER COLUMN contact_source SET DEFAULT -1;
UPDATE saved_criterialist SET contact_source = 4;

/* 3/12/2002 */

CREATE INDEX "project_files_cidx" ON "project_files" 
  USING btree ("link_module_id", "link_item_id");

