/* New ticket fields */

ALTER TABLE ticket ADD COLUMN location VARCHAR(256);
ALTER TABLE ticket ADD COLUMN assigned_date TIMESTAMP(3);
ALTER TABLE ticket ADD COLUMN est_resolution_date TIMESTAMP(3);
ALTER TABLE ticket ADD COLUMN resolution_date TIMESTAMP(3);
ALTER TABLE ticket ADD COLUMN cause TEXT;

