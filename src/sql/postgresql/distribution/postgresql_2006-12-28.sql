CREATE TABLE lookup_account_stage (
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE organization
  ADD COLUMN stage_id int4 REFERENCES lookup_account_stage(code);

UPDATE permission SET active = true WHERE permission = 'product-catalog-product';
