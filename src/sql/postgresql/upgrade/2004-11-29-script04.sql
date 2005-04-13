
-- Create a new table to lookup quote remarks
CREATE TABLE lookup_quote_remarks (
-- Each quote can have several remarks. They can be entered over the lifetime of a quote.
-- Example: Condition 1, Condition 2 etc
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

-- Create a new table to map remarks to a quote
CREATE TABLE quote_remark (
  map_id SERIAL PRIMARY KEY,
  quote_id INTEGER NOT NULL REFERENCES quote_entry(quote_id),
  remark_id INTEGER NOT NULL REFERENCES lookup_quote_remarks(code)
);

ALTER TABLE quote_product ADD COLUMN estimated_delivery TEXT;
ALTER TABLE quote_entry ADD COLUMN email_address TEXT;
ALTER TABLE quote_entry ADD COLUMN phone_number TEXT;
ALTER TABLE quote_entry ADD COLUMN address TEXT;
ALTER TABLE quote_entry ADD COLUMN fax_number TEXT;

