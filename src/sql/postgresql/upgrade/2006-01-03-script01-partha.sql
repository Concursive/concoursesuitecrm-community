-- Upgrade script for adding the field site_id to the document store team
ALTER TABLE document_store_role_member ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE document_store_user_member ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE document_store_department_member ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);

