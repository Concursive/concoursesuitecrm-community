alter table  permission_category add importer BOOLEAN DEFAULT false NOT NULL;

alter table  product_catalog add comments VARCHAR(255) DEFAULT NULL;

alter table  product_catalog add import_id INTEGER REFERENCES import(import_id);

alter table  product_catalog add status_id INTEGER;

alter table  product_category add import_id INTEGER REFERENCES import(import_id);

alter table  product_category add status_id INTEGER;