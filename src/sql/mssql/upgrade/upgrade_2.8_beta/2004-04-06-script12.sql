ALTER TABLE service_contract ADD total_hours_remaining_to_delete float;

UPDATE service_contract SET total_hours_remaining_to_delete = total_hours_remaining;

ALTER TABLE service_contract DROP COLUMN total_hours_remaining;

ALTER TABLE service_contract ADD total_hours_remaining float;

UPDATE service_contract SET total_hours_remaining = total_hours_remaining_to_delete;

ALTER TABLE service_contract DROP COLUMN total_hours_remaining_to_delete;
