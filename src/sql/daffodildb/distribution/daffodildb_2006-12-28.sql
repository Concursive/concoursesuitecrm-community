CREATE SEQUENCE lookup_account_stage_code_seq;
CREATE TABLE lookup_account_stage (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

ALTER TABLE organization
  ADD stage_id INTEGER REFERENCES lookup_account_stage(code);
