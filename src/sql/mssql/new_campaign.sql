/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 19, 2002
 *@version    $Id$
 */
 
CREATE TABLE search_fields (
  id INT IDENTITY PRIMARY KEY,
  field varchar(80),
  description VARCHAR(255),
  searchable BIT NOT NULL DEFAULT 1,
  field_typeID int NOT NULL DEFAULT -1,
	table_name varchar(80),
  object_class varchar(80)
);

CREATE TABLE field_types (
  id INT IDENTITY PRIMARY KEY,
  data_typeID int NOT NULL DEFAULT -1,
	data_type VARCHAR(20),
  operator VARCHAR(50),
  display_text varchar(50)
);


