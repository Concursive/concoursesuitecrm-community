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
