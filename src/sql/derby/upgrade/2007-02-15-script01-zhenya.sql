-- missing id field in contact_type_levels
ALTER TABLE contact_type_levels ADD COLUMN id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY;

-- missing id field in opportunity_component_levels
ALTER TABLE opportunity_component_levels ADD COLUMN id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY;