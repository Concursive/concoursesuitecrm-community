ALTER TABLE document_store ADD COLUMN public_store bool;
UPDATE document_store SET public_store = false;
ALTER TABLE document_store ALTER COLUMN public_store SET DEFAULT false;
ALTER TABLE document_store ALTER COLUMN public_store SET NOT NULL;
