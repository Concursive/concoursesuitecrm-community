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
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL
);

