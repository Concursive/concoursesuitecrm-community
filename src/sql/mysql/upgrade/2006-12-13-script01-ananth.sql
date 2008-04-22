-- Fields "entered" and "modified" for enabling syncing of lookup tables

ALTER TABLE lookup_duration_type ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE lookup_duration_type SET entered = CURRENT_TIMESTAMP;

ALTER TABLE lookup_duration_type ADD COLUMN modified TIMESTAMP NULL;
