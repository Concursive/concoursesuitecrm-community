-- TASK: "Various Improvements 2006-09"
--
-- Add new Lead import fields: DUNS Number, Business Name 2, SIC
-- Description, Number of Employees, Yearly Sales, Type, Year Started

-- NOTE: Added to new_cdb.sql 2006-10-04
CREATE SEQUENCE lookup_sic_codes_code_seq;

CREATE TABLE lookup_sic_codes(
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  "level" INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT FALSE,
  constant_id INTEGER UNIQUE NOT NULL
);

ALTER TABLE organization ADD COLUMN duns_type VARCHAR(300);
ALTER TABLE organization ADD COLUMN duns_number VARCHAR(30);
ALTER TABLE organization ADD COLUMN business_name_two VARCHAR(300);
ALTER TABLE organization DROP COLUMN sic_code CASCADE;
ALTER TABLE organization ADD COLUMN sic_code INTEGER REFERENCES lookup_sic_codes(code);
ALTER TABLE organization ADD COLUMN year_started INTEGER;
ALTER TABLE organization ADD COLUMN sic_description VARCHAR(300);

ALTER TABLE contact ADD COLUMN employees INTEGER;
ALTER TABLE contact ADD COLUMN duns_type VARCHAR(300);
ALTER TABLE contact ADD COLUMN duns_number VARCHAR(30);
ALTER TABLE contact ADD COLUMN business_name_two VARCHAR(300);
ALTER TABLE contact ADD COLUMN sic_code INTEGER REFERENCES lookup_sic_codes(code);
ALTER TABLE contact ADD COLUMN year_started INTEGER;
ALTER TABLE contact ADD COLUMN sic_description VARCHAR(300);

