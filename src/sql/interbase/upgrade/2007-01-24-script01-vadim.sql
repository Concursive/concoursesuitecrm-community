CREATE GENERATOR lookup_report_type_code_seq;
CREATE TABLE lookup_report_type (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  "level" INT DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  constant INT DEFAULT 1 NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
ALTER TABLE report_queue ADD output_type INT REFERENCES lookup_report_type(code);
ALTER TABLE report_queue ADD email BOOLEAN DEFAULT false;
