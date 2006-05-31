-- Ticket State lookup
CREATE TABLE lookup_ticket_state (
  code INTEGER IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

ALTER TABLE ticket ADD state_id INTEGER REFERENCES lookup_ticket_state(code);
ALTER TABLE ticketlog ADD state_id INT REFERENCES lookup_ticket_state(code);

