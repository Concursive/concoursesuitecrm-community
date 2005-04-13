ALTER TABLE permission_category ADD COLUMN constant INTEGER;
UPDATE permission_category SET constant=0;
ALTER TABLE permission_category ALTER COLUMN constant SET NOT NULL;

