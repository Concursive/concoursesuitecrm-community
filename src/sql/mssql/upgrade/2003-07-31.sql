/* Table to log upgrade scripts in */
CREATE TABLE database_version (
  version_id INT IDENTITY PRIMARY KEY,
  script_filename VARCHAR(255) NOT NULL,
  script_version VARCHAR(255) NOT NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

