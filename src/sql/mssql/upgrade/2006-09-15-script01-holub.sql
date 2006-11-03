-- TASK: "Various Improvements 2006-09"
--
-- Add new Lead import fields: DUNS Number, Business Name 2, SIC
-- Description, Number of Employees, Yearly Sales, Type, Year Started

-- NOTE: Added to new_cdb.sql 2006-10-04

CREATE TABLE lookup_sic_codes(
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER,
  enabled BIT DEFAULT 0,
  constant_id INTEGER NOT NULL
);

ALTER TABLE organization ADD duns_type VARCHAR(300);
ALTER TABLE organization ADD duns_number VARCHAR(30);
ALTER TABLE organization ADD business_name_two VARCHAR(300);
ALTER TABLE organization DROP sic_code;
ALTER TABLE organization ADD sic_code INTEGER REFERENCES lookup_sic_codes(code);
ALTER TABLE organization ADD year_started INTEGER;
ALTER TABLE organization ADD sic_description VARCHAR(300);

ALTER TABLE contact ADD employees INTEGER;
ALTER TABLE contact ADD duns_type VARCHAR(300);
ALTER TABLE contact ADD duns_number VARCHAR(30);
ALTER TABLE contact ADD business_name_two VARCHAR(300);
ALTER TABLE contact ADD sic_code INTEGER REFERENCES lookup_sic_codes(code);
ALTER TABLE contact ADD year_started INTEGER;
ALTER TABLE contact ADD sic_description VARCHAR(300);

