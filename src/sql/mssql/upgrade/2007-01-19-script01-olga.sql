ALTER TABLE document_store ADD public_store BIT NOT NULL DEFAULT 0;
UPDATE document_store SET public_store = 0;
