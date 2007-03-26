-- New columns to accomadate product and customer_product in quotes

-- Each quote can have a delivery type, which is entered on creation of the quote.
-- Example: Email, Fax, Mail, etc.
CREATE TABLE lookup_quote_delivery (
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Each quote can have several conditions. They can be entered over the lifetime of a quote.
-- Example: Condition 1, Condition 2 etc
CREATE TABLE lookup_quote_condition (
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create a new table to group quote versions
CREATE TABLE quote_group (
  group_id SERIAL PRIMARY KEY,
  unused CHAR(1)
);
SELECT setval ('quote_group_group_id_seq', 1000, false);

-- Adjustments to the quote_entry table
ALTER TABLE quote_entry ADD COLUMN product_id INTEGER REFERENCES product_catalog(product_id);
ALTER TABLE quote_entry ADD COLUMN customer_product_id INTEGER REFERENCES customer_product(customer_product_id);
ALTER TABLE quote_entry ADD COLUMN opp_id INTEGER REFERENCES opportunity_header(opp_id);
ALTER TABLE quote_entry ADD COLUMN "version" VARCHAR(255);
ALTER TABLE quote_entry ALTER COLUMN "version" SET DEFAULT '0';
ALTER TABLE quote_entry ALTER COLUMN "version" SET NOT NULL;
ALTER TABLE quote_entry ADD COLUMN group_id INTEGER REFERENCES quote_group(group_id);
ALTER TABLE quote_entry ALTER COLUMN group_id SET NOT NULL;
ALTER TABLE quote_entry ADD COLUMN delivery_id INTEGER REFERENCES lookup_quote_delivery(code);
ALTER TABLE quote_entry ADD COLUMN email_address TEXT;
ALTER TABLE quote_entry ADD COLUMN phone_number TEXT;
ALTER TABLE quote_entry ADD COLUMN address TEXT;
ALTER TABLE quote_entry ADD COLUMN fax_number TEXT;
ALTER TABLE quote_entry ADD COLUMN submit_action INTEGER;
ALTER TABLE quote_entry ADD COLUMN closed TIMESTAMP(3);
ALTER TABLE quote_entry ADD COLUMN show_total BOOLEAN;
ALTER TABLE quote_entry ALTER COLUMN show_total SET DEFAULT true;
ALTER TABLE quote_entry ADD COLUMN show_subtotal BOOLEAN;
ALTER TABLE quote_entry ALTER COLUMN show_subtotal SET DEFAULT true;
ALTER TABLE quote_entry ADD COLUMN logo_file_id INTEGER REFERENCES project_files(item_id);
ALTER TABLE quote_entry ADD COLUMN trashed_date TIMESTAMP(3);

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
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  issued_date TIMESTAMP(3),
  submit_action INTEGER,
  closed TIMESTAMP(3)
);

-- Create a new table to lookup quote remarks
CREATE TABLE lookup_quote_remarks (
-- Each quote can have several remarks. They can be entered over the lifetime of a quote.
-- Example: Condition 1, Condition 2 etc
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create a new table to map remarks to a quote
CREATE TABLE quote_remark (
  map_id SERIAL PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  remark_id INTEGER NOT NULL REFERENCES lookup_quote_remarks(code)
);

-- Each quote has notes
-- Example: Notes entered by Advertiser, Notes entered by Designer
CREATE TABLE quote_notes (
  notes_id SERIAL PRIMARY KEY,
  quote_id INTEGER REFERENCES quote_entry(quote_id),
  notes TEXT,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

