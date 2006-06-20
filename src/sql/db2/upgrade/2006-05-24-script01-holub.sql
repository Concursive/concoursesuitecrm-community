alter table  permission_category add importer CHAR(1) NOT NULL DEFAULT '0';

alter table  product_catalog add comments VARGRAPHIC(255) DEFAULT NULL;

alter table  product_catalog add import_id INTEGER REFERENCES import(import_id);

alter table  product_catalog add status_id INTEGER;

alter table  product_category add import_id INTEGER REFERENCES import(import_id);

alter table  product_category add status_id INTEGER;
