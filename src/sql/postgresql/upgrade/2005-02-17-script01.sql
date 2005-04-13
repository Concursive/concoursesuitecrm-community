ALTER TABLE organization_emailaddress ADD COLUMN primary_email BOOLEAN;
UPDATE organization_emailaddress SET primary_email = false;
ALTER TABLE organization_emailaddress ALTER COLUMN primary_email SET NOT NULL;
ALTER TABLE organization_emailaddress ALTER COLUMN primary_email SET DEFAULT false;

ALTER TABLE organization_phone ADD COLUMN primary_number BOOLEAN;
UPDATE organization_phone SET primary_number = false;
ALTER TABLE organization_phone ALTER COLUMN primary_number SET NOT NULL;
ALTER TABLE organization_phone ALTER COLUMN primary_number SET DEFAULT false;

ALTER TABLE organization_address ADD COLUMN primary_address BOOLEAN;
UPDATE organization_address SET primary_address = false;
ALTER TABLE organization_address ALTER COLUMN primary_address SET NOT NULL;
ALTER TABLE organization_address ALTER COLUMN primary_address SET DEFAULT false;
