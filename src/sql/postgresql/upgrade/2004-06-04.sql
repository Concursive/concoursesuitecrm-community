/*
  New columns to accomadate product and customer_product in quotes
*/
ALTER TABLE quote_entry ADD COLUMN product_id INTEGER REFERENCES product_catalog(product_id);
ALTER TABLE quote_entry ADD COLUMN customer_product_id INTEGER REFERENCES customer_product(customer_product_id);

-- Each quote has notes
-- Example: Notes entered by Advertiser, Notes entered by Designer
CREATE TABLE quote_notes (
  notes_id SERIAL PRIMARY KEY,
  quote_id INTEGER REFERENCES quote_entry(quote_id),
  notes TEXT,
  -- record status
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

