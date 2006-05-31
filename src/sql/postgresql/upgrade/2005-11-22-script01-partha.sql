-- Ticket State lookup
CREATE SEQUENCE lookup_ticket_state_code_seq;
CREATE TABLE lookup_ticket_state (
  code INTEGER DEFAULT nextval('lookup_ticket_state_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

ALTER TABLE ticket ADD COLUMN state_id INTEGER REFERENCES lookup_ticket_state(code);
ALTER TABLE ticketlog ADD COLUMN state_id INT REFERENCES lookup_ticket_state(code);

