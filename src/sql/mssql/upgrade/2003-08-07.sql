/* Projects are no longer a special case and must specify the link_module_id */
UPDATE project_files SET link_module_id = 4, link_item_id = project_id WHERE project_id > 0;
ALTER TABLE project_files DROP COLUMN project_id;

