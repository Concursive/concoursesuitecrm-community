DROP TABLE lookup_ticket_form;

UPDATE service_contract SET contract_value = NULL WHERE contract_value = -1.0;

ALTER TABLE ticket ADD product_id INTEGER REFERENCES product_catalog(product_id);
ALTER TABLE ticket ADD customer_product_id INTEGER REFERENCES customer_product(customer_product_id);
ALTER TABLE ticket ADD expectation INTEGER;

