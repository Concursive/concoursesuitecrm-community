--
-- table to store document store accounts
--
CREATE TABLE document_accounts (
  id INT IDENTITY PRIMARY KEY,
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  entered DATETIME DEFAULT CURRENT_TIMESTAMP
);
