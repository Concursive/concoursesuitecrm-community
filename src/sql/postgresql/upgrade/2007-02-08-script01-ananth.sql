-- missing modified column
ALTER TABLE module_field_categorylink ADD COLUMN modified TIMESTAMP(3);
UPDATE module_field_categorylink SET modified = CURRENT_TIMESTAMP;
ALTER TABLE module_field_categorylink ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
