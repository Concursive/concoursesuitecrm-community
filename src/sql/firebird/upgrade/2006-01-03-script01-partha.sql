-- Upgrade script for adding the field site_id to the document store team
ALTER TABLE document_store_role_member ADD site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE document_store_user_member ADD site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE doc_store_depart_member ADD site_id INTEGER REFERENCES lookup_site_id(code);
