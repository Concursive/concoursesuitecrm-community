ALTER TABLE service_contract ADD contract_value_delete float;

UPDATE service_contract SET contract_value_delete = contract_value;

ALTER TABLE service_contract DROP COLUMN contract_value;

ALTER TABLE service_contract ADD contract_value float;

UPDATE service_contract SET contract_value = contract_value_delete;

ALTER TABLE service_contract DROP COLUMN contract_value_delete;
