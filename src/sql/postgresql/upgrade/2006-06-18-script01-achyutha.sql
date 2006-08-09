--
-- table to store document store accounts
--
CREATE TABLE document_accounts (
  id SERIAL PRIMARY KEY,
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);

