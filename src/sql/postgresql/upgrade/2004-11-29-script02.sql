-- Add the cost columns to the product catalog tables

ALTER TABLE product_catalog_pricing ADD COLUMN cost_currency INTEGER REFERENCES lookup_currency(code);
ALTER TABLE product_catalog_pricing ADD COLUMN cost_amount FLOAT;
ALTER TABLE product_catalog_pricing ALTER COLUMN cost_amount SET DEFAULT 0;
UPDATE product_catalog_pricing SET cost_amount=0.0;
ALTER TABLE product_catalog_pricing ALTER COLUMN cost_amount SET NOT NULL;
ALTER TABLE product_option_values ADD COLUMN cost_currency INTEGER REFERENCES lookup_currency(code);
ALTER TABLE product_option_values ADD COLUMN cost_amount FLOAT;
ALTER TABLE product_option_values ALTER COLUMN cost_amount SET DEFAULT 0;
UPDATE product_option_values SET cost_amount=0.0;
ALTER TABLE product_option_values ALTER COLUMN cost_amount SET NOT NULL;

