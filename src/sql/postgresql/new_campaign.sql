
CREATE TABLE search_fields (
  id serial PRIMARY KEY,
  field varchar(80),
  description VARCHAR(255),
  searchable BOOLEAN NOT NULL DEFAULT 't',
  field_typeID int NOT NULL DEFAULT -1,
	table_name varchar(80),
  object_class varchar(80)
);

CREATE TABLE field_types (
  id serial PRIMARY KEY,
  data_typeID int NOT NULL DEFAULT -1,
	data_type VARCHAR(20),
  operator VARCHAR(50),
  display_text varchar(50)
);


