-- This schema represents an Account History and Contact History.
-- REQUIRES: new_cdb.sql

-- The details of the organization and contact history are listed.
-- The organization history captures the current events of an organization.
-- or a contact. The org_id is set for organization history.
-- The contact_id is set for the contact history.
-- The organization history contains all its contact's histories.

CREATE SEQUENCE history_history_id_seq;
CREATE TABLE history (
  history_id INTEGER NOT NULL,
  contact_id INTEGER REFERENCES contact(contact_id),
  org_id INTEGER REFERENCES organization(org_id),
  link_object_id INTEGER NOT NULL,
  link_item_id INTEGER,
  status NVARCHAR2(255),
  "type" NVARCHAR2(255),
  description CLOB,
  "level" INTEGER DEFAULT 10,
  enabled CHAR(1) DEFAULT 1,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (HISTORY_ID)
);

