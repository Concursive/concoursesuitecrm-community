ALTER TABLE ticket ADD user_group_id INTEGER REFERENCES user_group(group_id);

-- Ticket Cause lookup
CREATE TABLE lookup_ticket_cause (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- Ticket Resolution lookup
CREATE TABLE lookup_ticket_resolution (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

ALTER TABLE ticket ADD cause_id INTEGER REFERENCES lookup_ticket_cause(code);
ALTER TABLE ticket ADD resolution_id INTEGER REFERENCES lookup_ticket_resolution(code);

