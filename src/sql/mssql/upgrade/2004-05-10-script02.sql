CREATE TABLE service_contract_products(
  id INT IDENTITY PRIMARY KEY,
  link_contract_id INT REFERENCES service_contract(contract_id),
  link_product_id INT REFERENCES product_catalog(product_id),
);

/* assuming upgrade scripts to add product tables are in place*/ 
INSERT INTO  product_category(category_name, enteredby,modifiedby) 
VALUES ('Labor Category',0,0);
