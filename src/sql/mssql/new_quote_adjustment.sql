-- New columns to accomadate product and customer_product in quotes

-- Each quote can have a delivery type, which is entered on creation of the quote.
-- Example: Email, Fax, Mail, etc.
CREATE TABLE lookup_quote_delivery (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- Each quote can have several conditions. They can be entered over the lifetime of a quote.
-- Example: Condition 1, Condition 2 etc
CREATE TABLE lookup_quote_condition (
  code INTEGER IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- Create a new table to group quote versions
CREATE TABLE quote_group (
  group_id INT IDENTITY (1000, 1) PRIMARY KEY,
  unused CHAR(1) NULL
);

-- Adjustments to the quote_entry table
ALTER TABLE quote_entry ADD product_id INTEGER REFERENCES product_catalog(product_id);
ALTER TABLE quote_entry ADD customer_product_id INTEGER REFERENCES customer_product(customer_product_id);
ALTER TABLE quote_entry ADD opp_id INT REFERENCES opportunity_header(opp_id);
ALTER TABLE quote_entry ADD "version" VARCHAR(255) DEFAULT '0' NOT NULL;
ALTER TABLE quote_entry ADD group_id INTEGER REFERENCES quote_group(group_id);
ALTER TABLE quote_entry ALTER COLUMN group_id INTEGER NOT NULL;
ALTER TABLE quote_entry ADD delivery_id INTEGER REFERENCES lookup_quote_delivery(code);
ALTER TABLE quote_entry ADD email_address TEXT;
ALTER TABLE quote_entry ADD phone_number TEXT;
ALTER TABLE quote_entry ADD address TEXT;
ALTER TABLE quote_entry ADD fax_number TEXT;
ALTER TABLE quote_entry ADD submit_action INTEGER;
ALTER TABLE quote_entry ADD closed DATETIME;
ALTER TABLE quote_entry ADD show_total BIT DEFAULT 1;
ALTER TABLE quote_entry ADD show_subtotal BIT DEFAULT 1;
ALTER TABLE quote_entry ADD logo_file_id INTEGER REFERENCES project_files(item_id);

-- Create a new table to map quote conditions to a quote
CREATE TABLE quote_condition (
  map_id INTEGER IDENTITY PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  condition_id INTEGER NOT NULL REFERENCES lookup_quote_condition(code)
);

-- Create a new table to log the quote history
CREATE TABLE quotelog (
  id INTEGER IDENTITY PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  source_id INTEGER REFERENCES lookup_quote_source(code),
  status_id INTEGER REFERENCES lookup_quote_status(code),
  terms_id INTEGER REFERENCES lookup_quote_terms(code),
  type_id INTEGER REFERENCES lookup_quote_type(code),
  delivery_id INTEGER REFERENCES lookup_quote_delivery(code),
  notes TEXT NULL,
  grand_total FLOAT,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	issued_date DATETIME,
  submit_action INTEGER,
  closed DATETIME
);

-- Create a new table to lookup quote remarks
CREATE TABLE lookup_quote_remarks (
-- Each quote can have several remarks. They can be entered over the lifetime of a quote.
-- Example: Condition 1, Condition 2 etc
  code INTEGER IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

-- Create a new table to map remarks to a quote
CREATE TABLE quote_remark (
  map_id INTEGER IDENTITY PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  remark_id INTEGER NOT NULL REFERENCES lookup_quote_remarks(code)
);

-- Each quote has notes
-- Example: Notes entered by Advertiser, Notes entered by Designer
CREATE TABLE quote_notes (
  notes_id INT IDENTITY PRIMARY KEY,
  quote_id INTEGER REFERENCES quote_entry(quote_id),
  notes TEXT,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

