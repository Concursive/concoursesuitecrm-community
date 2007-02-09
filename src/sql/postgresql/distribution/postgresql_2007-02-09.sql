ALTER TABLE action_step ADD COLUMN quick_complete bool;
UPDATE action_step SET quick_complete = false;
ALTER TABLE action_step ALTER COLUMN quick_complete SET DEFAULT false;

ALTER TABLE document_store ADD COLUMN public_store bool;
UPDATE document_store SET public_store = false;
ALTER TABLE document_store ALTER COLUMN public_store SET DEFAULT false;
