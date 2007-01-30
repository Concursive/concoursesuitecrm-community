CREATE SEQUENCE lookup_report_type_code_seq;
CREATE TABLE lookup_report_type (
  code INT NOT NULL PRIMARY KEY,
  description NVARCHAR2(300) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INT DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  constant INT DEFAULT 1 NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
ALTER TABLE report_queue ADD output_type INT REFERENCES lookup_report_type(code);
ALTER TABLE report_queue ADD email CHAR(1) DEFAULT 0;
