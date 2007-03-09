-- This schema represents an Account History and Contact History.
-- REQUIRES: new_cdb.sql

-- The details of the organization and contact history are listed.
-- The organization history captures the current events of an organization.
-- or a contact. The org_id is set for organization history.
-- The contact_id is set for the contact history.
-- The organization history contains all its contact's histories.

CREATE SEQUENCE history_history_id_seq;
CREATE TABLE history (
  history_id INT PRIMARY KEY,
  contact_id INTEGER REFERENCES contact(contact_id),
	org_id INTEGER REFERENCES organization(org_id),
  link_object_id INTEGER NOT NULL,
  link_item_id INTEGER,
  status VARCHAR(255),
  type VARCHAR(255),
  description CLOB,
  "level" INTEGER DEFAULT 10,
  enabled BOOLEAN DEFAULT true,
  -- record status
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL
);

ALTER TABLE asset ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE contact ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE opportunity_header ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE opportunity_component ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE organization ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE quote_entry ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE service_contract ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE ticket ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE call_log ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE campaign ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE projects ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE document_store ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE product_catalog ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE product_catalog ADD COLUMN active BOOLEAN;
UPDATE product_catalog SET active = enabled;
ALTER TABLE product_catalog ALTER COLUMN active SET NOT NULL;
ALTER TABLE product_catalog ALTER COLUMN active SET DEFAULT true;
ALTER TABLE task ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE relationship ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE product_catalog ALTER COLUMN resolvable SET NOT NULL;

--Code to add cc and bcc fields into the campaign table for instant campaigns
ALTER TABLE campaign ADD COLUMN cc VARCHAR(1024);
ALTER TABLE campaign ADD COLUMN bcc VARCHAR(1024);

--Code to add level and application fields into the business_process_hook table
ALTER TABLE business_process_hook ADD COLUMN priority INTEGER;
UPDATE business_process_hook SET priority = 0 WHERE priority IS NULL;
ALTER TABLE business_process_hook ALTER COLUMN priority SET NOT NULL;
ALTER TABLE business_process_hook ALTER COLUMN priority SET DEFAULT 0;

