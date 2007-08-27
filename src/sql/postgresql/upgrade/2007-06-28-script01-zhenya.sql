CREATE SEQUENCE lookup_contact_stage_code_seq;
CREATE TABLE lookup_contact_stage (
  code INTEGER DEFAULT nextval('lookup_contact_stage_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE contact ADD COLUMN stage INT REFERENCES lookup_contact_stage(code);

CREATE INDEX contact_stage_idx ON contact(stage);
