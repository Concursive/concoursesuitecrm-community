CREATE TABLE lookup_account_stage (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

ALTER TABLE organization
  ADD stage_id INTEGER REFERENCES lookup_account_stage(code);

UPDATE permission SET active = 1 WHERE permission = 'product-catalog-product';
