/* Import table */
CREATE TABLE import(
  import_id INT IDENTITY PRIMARY KEY,
  type INT NOT NULL,
  name VARCHAR(250) NOT NULL,
  description TEXT,
  source_type INT,
  source VARCHAR(1000),
  record_delimiter VARCHAR(10),
  column_delimiter VARCHAR(10),
  total_imported_records INT,
  total_failed_records INT,
  status_id INT,
  file_type INT,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

/* Indexes on sortable fields */
CREATE INDEX "import_entered_idx" ON "import" (entered);
CREATE INDEX "import_name_idx" ON "import" (name);

ALTER TABLE organization ADD import_id INT;
ALTER TABLE contact ADD status_id INT;
ALTER TABLE contact ADD import_id INT;
CREATE INDEX "contact_import_id_idx" ON "contact" ("import_id");

