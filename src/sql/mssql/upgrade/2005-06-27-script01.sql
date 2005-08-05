-- This schema represents an Account History and Contact History.
-- REQUIRES: new_cdb.sql

-- The details of the organization and contact history are listed.
-- The organization history captures the current events of an organization.
-- or a contact. The org_id is set for organization history.
-- The contact_id is set for the contact history.
-- The organization history contains all its contact's histories.

CREATE TABLE history (
  history_id INT IDENTITY PRIMARY KEY,
  contact_id INTEGER REFERENCES contact(contact_id),
	org_id INTEGER REFERENCES organization(org_id),
  link_object_id INTEGER NOT NULL,
  link_item_id INTEGER,
  status VARCHAR(255),
  type VARCHAR(255),
  description TEXT,
  level INTEGER DEFAULT 10,
  enabled BIT DEFAULT 1,
  -- record status
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

ALTER TABLE asset ADD trashed_date DATETIME;
ALTER TABLE contact ADD trashed_date DATETIME;
ALTER TABLE opportunity_header ADD trashed_date DATETIME;
ALTER TABLE opportunity_component ADD trashed_date DATETIME;
ALTER TABLE organization ADD trashed_date DATETIME;
ALTER TABLE quote_entry ADD trashed_date DATETIME;
ALTER TABLE service_contract ADD trashed_date DATETIME;
ALTER TABLE ticket ADD trashed_date DATETIME;
ALTER TABLE call_log ADD trashed_date DATETIME;
ALTER TABLE projects ADD trashed_date DATETIME;
ALTER TABLE document_store ADD trashed_date DATETIME;
ALTER TABLE product_catalog ADD trashed_date DATETIME;
ALTER TABLE product_catalog ADD active BIT;
UPDATE product_catalog SET active = enabled;
UPDATE product_catalog SET enabled = 1;
ALTER TABLE product_catalog ALTER COLUMN active BIT NOT NULL DEFAULT 1;
ALTER TABLE task ADD trashed_date DATETIME;
ALTER TABLE relationship ADD trashed_date DATETIME;

--Code to add cc and bcc fields into the campaign table for instant campaigns
ALTER TABLE campaign ADD cc VARCHAR(1024);
ALTER TABLE campaign ADD bcc VARCHAR(1024);
ALTER TABLE campaign ADD trashed_date DATETIME;

--Code to add level and application fields into the business_process_hook table
ALTER TABLE business_process_hook ADD priority INTEGER;
UPDATE business_process_hook SET priority = 1 WHERE priority IS NULL;
ALTER TABLE business_process_hook ALTER COLUMN priority INTEGER NOT NULL DEFAULT 0;

