--  Upgrade script for integrating the following
--  into the product catalog
--  - Manufacturer details
--  - product pricing
-- Each product can have a manufacturer
-- Example: Nokia, Compaq

CREATE SEQUENCE lookup_product_manufac_code_seq;
CREATE TABLE lookup_product_manufacturer (
    code INTEGER DEFAULT nextval('lookup_product_manufac_code_seq') NOT NULL PRIMARY KEY,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);

ALTER TABLE product_catalog ADD COLUMN manufacturer_id INTEGER REFERENCES lookup_product_manufacturer(code);
ALTER TABLE product_catalog_pricing ADD COLUMN enabled boolean;
UPDATE product_catalog_pricing SET enabled = true;
ALTER TABLE product_catalog_pricing ALTER enabled SET DEFAULT false;

