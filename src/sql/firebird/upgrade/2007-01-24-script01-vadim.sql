CREATE GENERATOR lookup_report_type_code_seq;
CREATE TABLE lookup_report_type (
  code INTEGER NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INT DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  constant INT DEFAULT 1 NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
ALTER TABLE report_queue ADD output_type INT REFERENCES lookup_report_type(code);
ALTER TABLE report_queue ADD email CHAR(1) DEFAULT 'N';
