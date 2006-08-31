-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

-- New columns to accomadate product and customer_product in quotes

-- Each quote can have a delivery type, which is entered on creation of the quote.
-- Example: Email, Fax, Mail, etc.
CREATE TABLE lookup_quote_delivery (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

-- Each quote can have several conditions. They can be entered over the lifetime of a quote.
-- Example: Condition 1, Condition 2 etc
CREATE TABLE lookup_quote_condition (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

-- Create a new table to group quote versions
CREATE TABLE quote_group (
  group_id INT AUTO_INCREMENT PRIMARY KEY,
  unused CHAR(1)
);

-- Adjustments to the quote_entry table
ALTER TABLE quote_entry ADD COLUMN product_id INTEGER REFERENCES product_catalog(product_id);
ALTER TABLE quote_entry ADD COLUMN customer_product_id INTEGER REFERENCES customer_product(customer_product_id);
ALTER TABLE quote_entry ADD COLUMN opp_id INTEGER REFERENCES opportunity_header(opp_id);
ALTER TABLE quote_entry ADD COLUMN `version` VARCHAR(255) NOT NULL DEFAULT '0';
ALTER TABLE quote_entry ADD COLUMN group_id INTEGER NOT NULL REFERENCES quote_group(group_id);
ALTER TABLE quote_entry ADD COLUMN delivery_id INTEGER REFERENCES lookup_quote_delivery(code);
ALTER TABLE quote_entry ADD COLUMN email_address TEXT;
ALTER TABLE quote_entry ADD COLUMN phone_number TEXT;
ALTER TABLE quote_entry ADD COLUMN address TEXT;
ALTER TABLE quote_entry ADD COLUMN fax_number TEXT;
ALTER TABLE quote_entry ADD COLUMN submit_action INTEGER;
ALTER TABLE quote_entry ADD COLUMN closed TIMESTAMP NULL;
ALTER TABLE quote_entry ADD COLUMN show_total BOOLEAN;
ALTER TABLE quote_entry ALTER COLUMN show_total SET DEFAULT true;
ALTER TABLE quote_entry ADD COLUMN show_subtotal BOOLEAN;
ALTER TABLE quote_entry ALTER COLUMN show_subtotal SET DEFAULT true;
ALTER TABLE quote_entry ADD COLUMN logo_file_id INTEGER REFERENCES project_files(item_id);
ALTER TABLE quote_entry ADD COLUMN trashed_date TIMESTAMP NULL;

-- Create a new table to map quote conditions to a quote
CREATE TABLE quote_condition (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  condition_id INTEGER NOT NULL REFERENCES lookup_quote_condition(code)
);

-- Create a new table to log the quote history
CREATE TABLE quotelog (
  id INT AUTO_INCREMENT PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  source_id INTEGER REFERENCES lookup_quote_source(code),
  status_id INTEGER REFERENCES lookup_quote_status(code),
  terms_id INTEGER REFERENCES lookup_quote_terms(code),
  type_id INTEGER REFERENCES lookup_quote_type(code),
  delivery_id INTEGER REFERENCES lookup_quote_delivery(code),
  notes TEXT NULL,
  grand_total FLOAT,
  enteredby INTEGER NOT NULL REFERENCES `access`(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  issued_date TIMESTAMP NULL,
  submit_action INTEGER,
  closed TIMESTAMP NULL
);

-- Create a new table to lookup quote remarks
CREATE TABLE lookup_quote_remarks (
-- Each quote can have several remarks. They can be entered over the lifetime of a quote.
-- Example: Condition 1, Condition 2 etc
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

-- Create a new table to map remarks to a quote
CREATE TABLE quote_remark (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  remark_id INTEGER NOT NULL REFERENCES lookup_quote_remarks(code)
);

-- Each quote has notes
-- Example: Notes entered by Advertiser, Notes entered by Designer
CREATE TABLE quote_notes (
  notes_id INT AUTO_INCREMENT PRIMARY KEY,
  quote_id INTEGER REFERENCES quote_entry(quote_id),
  notes TEXT,
  enteredby INTEGER NOT NULL REFERENCES `access`(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL
);

