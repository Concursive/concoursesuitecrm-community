INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'accountHistory', 'org.aspcfs.modules.accounts.base.OrganizationHistory');
INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'accountHistoryList', 'org.aspcfs.modules.accounts.base.OrganizationHistoryList');
INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'actionPlanWorkNote', 'org.aspcfs.modules.actionplans.base.ActionPlanWorkNote');
INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'actionPlanWorkNoteList', 'org.aspcfs.modules.actionplans.base.ActionPlanWorkNoteList');
INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'actionItemWorkNote', 'org.aspcfs.modules.actionplans.base.ActionItemWorkNote');
INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'actionItemWorkNoteList', 'org.aspcfs.modules.actionplans.base.ActionItemWorkNoteList');

CREATE TABLE url_map (
  url_id SERIAL PRIMARY KEY,
  time_in_millis NUMERIC(19) NOT NULL,
  url TEXT
);

ALTER TABLE order_address
 ADD COLUMN addrline4 varchar(300) ;

CREATE INDEX order_city_idx
  ON order_address
  USING btree
  (city);

ALTER TABLE ORDER_ENTRY
  ADD COLUMN approx_ship_date timestamp(3);

ALTER TABLE ORDER_ENTRY
  ADD COLUMN approx_delivery_date timestamp(3);

ALTER TABLE customer_product
 ADD COLUMN contact_id int4 ;

ALTER TABLE customer_product_history
 ADD COLUMN contact_id int4 ;

CREATE TABLE credit_card (
 creditcard_id SERIAL PRIMARY KEY,
 card_type INT REFERENCES lookup_creditcard_types (code),
 card_number varchar(300),
 card_security_code varchar(300),
 expiration_month int,
 expiration_year int,
 name_on_card varchar(300),
 company_name_on_card varchar(300),
 entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
 enteredby INT NOT NULL REFERENCES access(user_id),
 modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modifiedby INT NOT NULL REFERENCES access(user_id)
);


CREATE TABLE lookup_payment_gateway (
  code SERIAL PRIMARY KEY,
  description varchar(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  "level" int DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  constant_id int
);


CREATE TABLE merchant_payment_gateway (
  merchant_payment_gateway_id SERIAL PRIMARY KEY,
  gateway_id int REFERENCES lookup_payment_gateway(code),
  merchant_id varchar(300),
  merchant_code varchar(1024),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

ALTER TABLE call_log
  ADD COLUMN followup_contact_id int4 REFERENCES contact(contact_id);


CREATE INDEX call_fcontact_id_idx
  ON call_log
  USING btree
  (followup_contact_id);

UPDATE call_log
 SET followup_contact_id = contact_id
 WHERE followup_contact_id IS NULL AND contact_id IS NOT NULL;

alter table permission_category
 drop column products;

alter table permission_category
 drop column importer;

update role_permission
 set permission_id = (select permission_id from permission where permission = 'product-catalog')
 where permission_id = (select permission_id from permission where permission = 'admin-sysconfig-products');

update permission
 set enabled = true,
     active = true
 where permission = 'product-catalog';

update permission
 set enabled = false,
     active = false
 where permission = 'admin-sysconfig-products';

delete from permission
 where permission = 'admin-sysconfig-products';

create index pcatalog_pid on product_catalog (parent_id);

create index pcatalog_name on product_catalog (product_name);

create index  product_category_map_cid on product_catalog_category_map (category_id);

create index pcatalog_enteredby on product_catalog (enteredby);

create index pcatalog_estimated_ship_time on product_catalog (estimated_ship_time);

create index pcatalog_format_id on product_catalog (format_id);

create index pcatalog_import_id on product_catalog (import_id);

create index pcatalog_large_image_id on product_catalog (large_image_id);

create index pcatalog_manufacturer_id on product_catalog (manufacturer_id);

create index pcatalog_modifiedby on product_catalog (modifiedby);

create index pcatalog_shipping_id on product_catalog (shipping_id);

create index pcatalog_small_image_id on product_catalog (small_image_id);

create index pcatalog_thumbnail_image_id on product_catalog (thumbnail_image_id);

create index pcatalog_type_id on product_catalog (type_id);

create index pcategory_enteredby on product_category (enteredby);

create index pcategory_import_id on product_category (import_id);

create index pcategory_large_image_id on product_category (large_image_id);

create index pcategory_modifiedby on product_category (modifiedby);

create index pcategory_parent_id on product_category (parent_id);

create index pcategory_small_image_id on product_category (small_image_id);

create index pcategory_thumbnail_image_id on product_category (thumbnail_image_id);

create index pcategory_type_id on product_category (type_id);

create index contact_access_type on contact  (access_type);

create index contact_assistant on contact  (assistant);

create index contact_department on contact  (department);

create index contact_enteredby on contact  (enteredby);

create index contact_industry_temp_code on contact  (industry_temp_code);

create index contact_modifiedby on contact  (modifiedby);

create index contact_org_id on contact  (org_id);

create index contact_owner on contact  ("owner");

create index contact_rating on contact  (rating);

create index contact_site_id on contact  (site_id);

create index contact_source on contact  (source);

create index contact_super on contact  (super);

create index contact_user_id on contact  (user_id);

create index contact_employee_id on contact (employee_id);

create index tcontactlevels_level on contact_type_levels ("level");

create index caddress_primary_address on  contact_address (primary_address);

create index contact_entered on contact (entered);

create index laccess_types_rule_id on lookup_access_types (rule_id);

ALTER TABLE contact ADD assigned_date TIMESTAMP(3);
ALTER TABLE contact ADD lead_trashed_date TIMESTAMP(3);

UPDATE permission_category SET reports = true WHERE constant = 228051100;

ALTER TABLE organization_address ADD COLUMN county VARCHAR(80);
ALTER TABLE organization_address ADD COLUMN latitude FLOAT DEFAULT 0;
ALTER TABLE organization_address ADD COLUMN longitude FLOAT DEFAULT 0;

ALTER TABLE contact_address ADD COLUMN county VARCHAR(80);
ALTER TABLE contact_address ADD COLUMN latitude FLOAT DEFAULT 0;
ALTER TABLE contact_address ADD COLUMN longitude FLOAT DEFAULT 0;

CREATE TABLE lookup_sic_codes(
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT FALSE,
  level INTEGER,
  enabled BOOLEAN DEFAULT FALSE,
  constant_id INTEGER UNIQUE NOT NULL
);

ALTER TABLE organization ADD COLUMN duns_type VARCHAR(300);
ALTER TABLE organization ADD COLUMN duns_number VARCHAR(30);
ALTER TABLE organization ADD COLUMN business_name_two VARCHAR(300);
ALTER TABLE organization DROP COLUMN sic_code;
ALTER TABLE organization ADD COLUMN sic_code INTEGER REFERENCES lookup_sic_codes(code);
ALTER TABLE organization ADD COLUMN year_started INTEGER;
ALTER TABLE organization ADD COLUMN sic_description VARCHAR(300);

ALTER TABLE contact ADD COLUMN employees INTEGER;
ALTER TABLE contact ADD COLUMN duns_type VARCHAR(300);
ALTER TABLE contact ADD COLUMN duns_number VARCHAR(30);
ALTER TABLE contact ADD COLUMN business_name_two VARCHAR(300);
ALTER TABLE contact ADD COLUMN sic_code INTEGER REFERENCES lookup_sic_codes(code);
ALTER TABLE contact ADD COLUMN year_started INTEGER;
ALTER TABLE contact ADD COLUMN sic_description VARCHAR(300);

UPDATE permission SET permission_add = false, permission_edit = false, permission_delete = false WHERE permission = 'product-catalog';
UPDATE permission SET enabled = true WHERE permission = 'product-catalog-product';
UPDATE report SET enabled = true WHERE filename = 'leads_user.xml';
