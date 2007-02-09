ALTER TABLE action_step ADD quick_complete BIT DEFAULT 0;
UPDATE action_step SET quick_complete = 0;

ALTER TABLE document_store ADD public_store BIT DEFAULT 0 NOT NULL;
UPDATE document_store SET public_store = 0;
