/* New ticket fields */

ALTER TABLE ticket ADD location VARCHAR(256);
ALTER TABLE ticket ADD assigned_date DATETIME;
ALTER TABLE ticket ADD est_resolution_date DATETIME;
ALTER TABLE ticket ADD resolution_date DATETIME;
ALTER TABLE ticket ADD cause TEXT;

