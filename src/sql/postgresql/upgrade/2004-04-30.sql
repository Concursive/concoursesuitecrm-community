UPDATE service_contract SET contract_value = NULL WHERE contract_value = -1.0;

ALTER TABLE ticket ADD COLUMN link_contract_id INTEGER REFERENCES service_contract(contract_id);
ALTER TABLE ticket ADD COLUMN link_asset_id INTEGER REFERENCES asset(asset_id);
ALTER TABLE ticket ADD COLUMN product_id INTEGER REFERENCES product_catalog(product_id);
ALTER TABLE ticket ADD COLUMN customer_product_id INTEGER REFERENCES customer_product(customer_product_id);
ALTER TABLE ticket ADD COLUMN expectation INTEGER;


