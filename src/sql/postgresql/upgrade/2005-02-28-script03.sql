-- new columns to accommodate a lead in the contact table

-- create the mappings for skipped and read lead records
CREATE TABLE contact_lead_skipped_map (
  map_id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES access(user_id),
  contact_id INT NOT NULL REFERENCES contact(contact_id)
);

CREATE TABLE contact_lead_read_map (
  map_id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES access(user_id),
  contact_id INT NOT NULL REFERENCES contact(contact_id)
);

-- lookup tables
CREATE SEQUENCE lookup_contact_source_code_seq;
CREATE TABLE lookup_contact_source (
  code INTEGER DEFAULT nextval('lookup_contact_source_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

INSERT INTO lookup_contact_source (description) VALUES ('Advertisement');
INSERT INTO lookup_contact_source (description) VALUES ('Employee Referral');
INSERT INTO lookup_contact_source (description) VALUES ('External Referral');
INSERT INTO lookup_contact_source (description) VALUES ('Partner');
INSERT INTO lookup_contact_source (description) VALUES ('Public Relations');
INSERT INTO lookup_contact_source (description) VALUES ('Trade Show');
INSERT INTO lookup_contact_source (description) VALUES ('Web');
INSERT INTO lookup_contact_source (description) VALUES ('Word of Mouth');
INSERT INTO lookup_contact_source (description) VALUES ('Other');

CREATE SEQUENCE lookup_contact_rating_code_seq;
CREATE TABLE lookup_contact_rating (
  code INTEGER DEFAULT nextval('lookup_contact_rating_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);
INSERT INTO lookup_contact_rating (description) VALUES ('Qualified');
INSERT INTO lookup_contact_rating (description) VALUES ('Unqualified');

-- changes to the contact table
ALTER TABLE contact ADD COLUMN lead BOOLEAN;
ALTER TABLE contact ALTER COLUMN lead SET DEFAULT false;
UPDATE contact SET lead = false WHERE lead IS NULL;
ALTER TABLE contact ADD COLUMN lead_status INTEGER NULL;
ALTER TABLE contact ADD COLUMN source INTEGER REFERENCES lookup_contact_source(code);
ALTER TABLE contact ADD COLUMN rating INTEGER REFERENCES lookup_contact_rating(code);
ALTER TABLE contact ADD COLUMN comments VARCHAR(255) NULL;

-- new column to capture the conversion date of a lead to a contact in the contact table
ALTER TABLE contact ADD COLUMN conversion_date TIMESTAMP(3) NULL;
