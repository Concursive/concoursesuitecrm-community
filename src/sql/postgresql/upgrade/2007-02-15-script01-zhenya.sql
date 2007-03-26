-- missing id field in contact_type_levels

CREATE SEQUENCE contact_type_levels_id_seq;
ALTER TABLE contact_type_levels ADD COLUMN id INTEGER DEFAULT nextval('contact_type_levels_id_seq') NOT NULL PRIMARY KEY;

-- missing id field in opportunity_component_levels

CREATE SEQUENCE opportunity_component_levels_id_seq;
ALTER TABLE opportunity_component_levels ADD COLUMN id INTEGER DEFAULT nextval('opportunity_component_levels_id_seq') NOT NULL PRIMARY KEY;