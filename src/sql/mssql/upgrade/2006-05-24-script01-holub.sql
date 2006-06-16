ALTER TABLE permission_category ADD importer BIT NOT NULL DEFAULT 0;

ALTER TABLE product_catalog ADD comments varchar(255);

ALTER TABLE product_catalog ADD import_id INT;

alter table  product_catalog add status_id INTEGER;

alter table  product_category add import_id INTEGER REFERENCES import(import_id);

alter table  product_category add status_id INTEGER;

