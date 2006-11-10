/* Tables for registration process */
CREATE TABLE registration (
  registration_id SERIAL PRIMARY KEY,
  email VARCHAR(300),
  profile VARCHAR(300),
  name_first VARCHAR(300),
  name_last VARCHAR(300),
  company VARCHAR(300),
  registration_text VARCHAR(300),
  os_version VARCHAR(300),
  java_version VARCHAR(300),
  webserver VARCHAR(300),
  ip_address VARCHAR(20),
  ip_name VARCHAR(300),
  edition VARCHAR(300),
  crc TEXT,
  key_file TEXT,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  key_hex TEXT
);

