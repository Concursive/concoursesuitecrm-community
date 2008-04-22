-- missing modified column
ALTER TABLE custom_field_category ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE custom_field_group ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE custom_field_info ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE custom_field_lookup ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE custom_field_data ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE custom_field_data SET entered = CURRENT_TIMESTAMP;

ALTER TABLE custom_field_data ADD COLUMN modified TIMESTAMP NULL;
