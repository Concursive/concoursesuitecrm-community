-- missing modified column
ALTER TABLE custom_field_category ADD COLUMN modified TIMESTAMP(3);
UPDATE custom_field_category SET modified = CURRENT_TIMESTAMP;
ALTER TABLE custom_field_category ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE custom_field_group ADD COLUMN modified TIMESTAMP(3);
UPDATE custom_field_group SET modified = CURRENT_TIMESTAMP;
ALTER TABLE custom_field_group ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE custom_field_info ADD COLUMN modified TIMESTAMP(3);
UPDATE custom_field_info SET modified = CURRENT_TIMESTAMP;
ALTER TABLE custom_field_info ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE custom_field_lookup ADD COLUMN modified TIMESTAMP(3);
UPDATE custom_field_lookup SET modified = CURRENT_TIMESTAMP;
ALTER TABLE custom_field_lookup ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE custom_field_data ADD COLUMN entered TIMESTAMP(3);
UPDATE custom_field_data SET entered = CURRENT_TIMESTAMP;
ALTER TABLE custom_field_data ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE custom_field_data ADD COLUMN modified TIMESTAMP(3);
UPDATE custom_field_data SET modified = CURRENT_TIMESTAMP;
ALTER TABLE custom_field_data ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;

