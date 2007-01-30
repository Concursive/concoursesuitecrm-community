CREATE TABLE lookup_report_type (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  constant INTEGER DEFAULT 1 NOT NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);
ALTER TABLE report_queue ADD COLUMN output_type INT REFERENCES lookup_report_type(code);
ALTER TABLE report_queue ADD COLUMN email BOOLEAN DEFAULT false;
