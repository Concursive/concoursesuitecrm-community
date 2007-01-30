CREATE TABLE lookup_report_type (
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INT DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  constant INT DEFAULT 1 NOT NULL,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP NOT NULL
);
ALTER TABLE report_queue ADD COLUMN output_type INT REFERENCES lookup_report_type(code);
ALTER TABLE report_queue ADD COLUMN email BOOLEAN DEFAULT false;
