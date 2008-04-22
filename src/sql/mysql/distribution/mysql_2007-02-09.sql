ALTER TABLE action_step ADD COLUMN quick_complete bool DEFAULT false;
UPDATE action_step SET quick_complete = false;

ALTER TABLE document_store ADD COLUMN public_store bool NOT NULL DEFAULT false;
UPDATE document_store SET public_store = false;
