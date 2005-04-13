-- new columns to accommodate a lead in the contact table

-- new tables for mappings
CREATE TABLE contact_lead_skipped_map (
  map_id INT IDENTITY PRIMARY KEY,
  user_id INT NOT NULL REFERENCES access(user_id),
  contact_id INT NOT NULL REFERENCES contact(contact_id)
);

CREATE TABLE contact_lead_read_map (
  map_id INT IDENTITY PRIMARY KEY,
  user_id INT NOT NULL REFERENCES access(user_id),
  contact_id INT NOT NULL REFERENCES contact(contact_id)
);

-- lookup tables
CREATE TABLE lookup_contact_source (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
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

CREATE TABLE lookup_contact_rating (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

INSERT INTO lookup_contact_rating (description) VALUES ('Qualified');
INSERT INTO lookup_contact_rating (description) VALUES ('Unqualified');

ALTER TABLE contact ADD lead BIT DEFAULT 0;
UPDATE contact SET lead = 0 WHERE lead IS NULL;
ALTER TABLE contact ADD lead_status INT NULL;
ALTER TABLE contact ADD source INT REFERENCES lookup_contact_source(code);
ALTER TABLE contact ADD rating INT REFERENCES lookup_contact_rating(code);
ALTER TABLE contact ADD comments VARCHAR(255);

