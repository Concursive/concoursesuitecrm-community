update service_contract
set contract_value = null
where contract_value = -1.0;

ALTER TABLE ticket
ADD COLUMN product_id INTEGER REFERENCES product_catalog(product_id);

ALTER TABLE ticket
ADD COLUMN customer_product_id INTEGER REFERENCES customer_product(customer_product_id);

ALTER TABLE ticket ADD COLUMN expectation INTEGER;

ALTER TABLE ticketlog
ADD COLUMN product_id INTEGER REFERENCES product_catalog(product_id);

ALTER TABLE ticketlog
ADD COLUMN customer_product_id INTEGER REFERENCES customer_product(customer_product_id);


