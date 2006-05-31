ALTER TABLE ticket ADD COLUMN user_group_id INTEGER REFERENCES user_group(group_id);

-- Ticket Cause lookup
CREATE SEQUENCE lookup_ticket_cause_code_seq;
CREATE TABLE lookup_ticket_cause (
  code INTEGER DEFAULT nextval('lookup_ticket_cause_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

-- Ticket Resolution lookup
CREATE SEQUENCE lookup_ticket_resoluti_code_seq;
CREATE TABLE lookup_ticket_resolution (
  code INTEGER DEFAULT nextval('lookup_ticket_resoluti_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

ALTER TABLE ticket ADD COLUMN cause_id INTEGER REFERENCES lookup_ticket_cause(code);
ALTER TABLE ticket ADD COLUMN resolution_id INTEGER REFERENCES lookup_ticket_resolution(code);

