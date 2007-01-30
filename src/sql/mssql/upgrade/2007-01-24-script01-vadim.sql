CREATE TABLE lookup_report_type (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  constant INTEGER NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE report_queue ADD output_type INT REFERENCES lookup_report_type(code);
ALTER TABLE report_queue ADD email BIT DEFAULT 0;
