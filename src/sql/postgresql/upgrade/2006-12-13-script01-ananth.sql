-- Fields "entered" and "modified" for enabling syncing of lookup tables

ALTER TABLE lookup_duration_type ADD COLUMN entered TIMESTAMP(3);
UPDATE lookup_duration_type SET entered = CURRENT_TIMESTAMP;
ALTER TABLE lookup_duration_type ALTER COLUMN entered SET NOT NULL;
ALTER TABLE lookup_duration_type ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE lookup_duration_type ADD COLUMN modified TIMESTAMP(3);
UPDATE lookup_duration_type SET modified = CURRENT_TIMESTAMP;
ALTER TABLE lookup_duration_type ALTER COLUMN modified SET NOT NULL;
ALTER TABLE lookup_duration_type ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
