CREATE SEQUENCE lookup_account_stage_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_account_stage (
  code INTEGER NOT NULL KEY,
  description VARGRAPHIC(300) NOT NULL,
  default_item CHAR(1) DEFAULT '0',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT '1',
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

ALTER TABLE organization
  ADD COLUMN stage_id INTEGER REFERENCES lookup_account_stage(code);
