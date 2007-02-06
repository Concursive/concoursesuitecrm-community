ALTER TABLE document_store ADD COLUMN public_store bool DEFAULT false;
UPDATE document_store SET public_store = false;
