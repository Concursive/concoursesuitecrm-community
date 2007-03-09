--
-- table to store document store accounts
--
CREATE SEQUENCE document_accounts_id_seq;
CREATE TABLE document_accounts (
  id INT PRIMARY KEY,
  document_store_id INTEGER NOT NULL REFERENCES document_store(document_store_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

