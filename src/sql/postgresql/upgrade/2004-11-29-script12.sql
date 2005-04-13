-- Add the show_subtotal field to quote_entry
ALTER TABLE quote_entry ADD COLUMN show_subtotal BOOLEAN;
ALTER TABLE quote_entry ALTER COLUMN show_subtotal SET DEFAULT true;

-- Add the comment field to quote_product
ALTER TABLE quote_product ADD COLUMN comment VARCHAR(300);

-- Updates permission_category to enable lookup lists for the Quotes
UPDATE permission_category SET lookups = true WHERE category = 'Quotes';
