ALTER TABLE organization_emailaddress ADD primary_email BIT;
UPDATE organization_emailaddress SET primary_email = 0;
ALTER TABLE organization_emailaddress ALTER COLUMN primary_email BIT NOT NULL DEFAULT 0;

ALTER TABLE organization_phone ADD primary_number BIT;
UPDATE organization_phone SET primary_number = 0;
ALTER TABLE organization_emailaddress ALTER COLUMN primary_number BIT NOT NULL DEFAULT 0;

ALTER TABLE organization_address ADD primary_address BIT;
UPDATE organization_address SET primary_address = 0;
ALTER TABLE organization_emailaddress ALTER COLUMN primary_address BIT NOT NULL DEFAULT 0;
