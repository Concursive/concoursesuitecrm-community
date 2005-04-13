-- Create a new table to provide lookups for the quote delivery options
CREATE TABLE lookup_quote_delivery (
-- Each quote can have a delivery type, which is entered on creation of the quote.
-- Example: Email, Fax, Mail, etc.
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

INSERT INTO lookup_quote_delivery (description) VALUES ('Email');
INSERT INTO lookup_quote_delivery (description) VALUES ('Fax');
INSERT INTO lookup_quote_delivery (description) VALUES ('USPS');
INSERT INTO lookup_quote_delivery (description) VALUES ('FedEx');
INSERT INTO lookup_quote_delivery (description) VALUES ('UPS');
INSERT INTO lookup_quote_delivery (description) VALUES ('In Person');

-- Alter quote_entry to add a delivery column.
ALTER TABLE quote_entry ADD COLUMN delivery_id INTEGER REFERENCES lookup_quote_delivery(code);

-- Create a new table to lookup quote conditions
CREATE TABLE lookup_quote_condition (
-- Each quote can have several conditions. They can be entered over the lifetime of a quote.
-- Example: Condition 1, Condition 2 etc
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

-- Create a new table to map quote conditions to a quote
CREATE TABLE quote_condition (
  map_id SERIAL PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  condition_id INTEGER NOT NULL REFERENCES lookup_quote_condition(code)
);

-- Create a new table to log the quote history
CREATE TABLE quotelog (
  id SERIAL PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  source_id INTEGER REFERENCES lookup_quote_source(code),
  status_id INTEGER REFERENCES lookup_quote_status(code),
  terms_id INTEGER REFERENCES lookup_quote_terms(code),
  type_id INTEGER REFERENCES lookup_quote_type(code),
  delivery_id INTEGER REFERENCES lookup_quote_delivery(code),
  notes TEXT NULL,
  grand_total FLOAT,
  -- record status
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);


