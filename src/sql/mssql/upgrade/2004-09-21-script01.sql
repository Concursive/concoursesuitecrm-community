--  Upgrade script for integrating the following
--  into the product catalog
--  - Manufacturer details
--  - product pricing
--
-- Each product can have a manufacturer
-- Example: Nokia, Compaq
CREATE TABLE lookup_product_manufacturer (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

ALTER TABLE product_catalog ADD manufacturer_id INTEGER REFERENCES lookup_product_manufacturer(code);
ALTER TABLE product_catalog_pricing ADD enabled BIT DEFAULT 0;
UPDATE product_catalog_pricing SET enabled = true;
UPDATE product_catalog_pricing SET enabled = 0;
