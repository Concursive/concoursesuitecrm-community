/* 4/8/2002 All databases up-to-date */

/* 4/17/2002 */

CREATE TABLE sync_client (
  client_id SERIAL PRIMARY KEY,
  type VARCHAR(100),
  version VARCHAR(50),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE sync_system (
  system_id SERIAL PRIMARY KEY,
  application_name VARCHAR(255),
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE sync_table (
  table_id SERIAL PRIMARY KEY,
  system_id INT NOT NULL,
  element_name VARCHAR(255),
  mapped_class_name VARCHAR(255),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  create_statement TEXT,
  order_id INT DEFAULT -1,
  sync_item BOOLEAN DEFAULT false
);

/* RUN sync.init as well */

/* 05/09/2002 - ds21 up to date */

CREATE TABLE lookup_account_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

INSERT INTO lookup_account_types (description) VALUES ('Customer');
INSERT INTO lookup_account_types (description) VALUES ('Competitor');
INSERT INTO lookup_account_types (description) VALUES ('Partner');
INSERT INTO lookup_account_types (description) VALUES ('Vendor');

CREATE TABLE account_type_levels (
  id int not null,
  type_id int not null,
  level INTEGER not null,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
)
;

DROP TABLE search_fields;
drop SEQUENCE search_fields_id_seq; 

CREATE TABLE search_fields (
  id serial PRIMARY KEY,
  field varchar(80),
  description VARCHAR(255),
  searchable BOOLEAN NOT NULL DEFAULT 't',
  field_typeID int NOT NULL DEFAULT -1,
  table_name varchar(80),
  object_class varchar(80)
);

INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('company', 'Company Name', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('namefirst', 'Contact First Name', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('namelast', 'Contact Last Name', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('entered', 'Entered Date', true, 1);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('zip', 'Zip Code', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('areacode', 'Area Code', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('city', 'City', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('typeId', 'Contact Type', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('contactId', 'Contact ID', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('accountTypeId', 'Account Type', true, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('title', 'Contact Title', false, 0);


/* */
