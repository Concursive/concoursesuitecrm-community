create index prod_cat_map_cid_i on product_catalog_category_map (category_id);

create index pcat_pid_idx on product_catalog (parent_id);
create index pcat_name_idx on product_catalog (product_name);
create index pcat_enteredby_idx on product_catalog (enteredby);
create index pcat_est_sh_ti_idx on product_catalog (estimated_ship_time);
create index pcat_format_id on product_catalog (format_id);
create index pcat_import_id on product_catalog (import_id);
create index pcat_lrg_img_i_idx on product_catalog (large_image_id);
create index pcat_manuf_id_idx on product_catalog (manufacturer_id);
create index pcat_mod_by_idx on product_catalog (modifiedby);
create index pcat_ship_id_idx on product_catalog (shipping_id);
create index pcat_sm_img_id_idx on product_catalog (small_image_id);
create index pcat_th_img_id_idx on product_catalog (thumbnail_image_id);
create index pcat_type_id_idx on product_catalog (type_id);

create index pctg_enteredby_idx on product_category (enteredby);
create index pctg_import_id_idx on product_category (import_id);
create index pctg_l_img_id_idx on product_category (large_image_id);
create index pctg_mod_by_idx on product_category (modifiedby);
create index pctg_parent_id_idx on product_category (parent_id);
create index pctg_sm_img_id_idx on product_category (small_image_id);
create index pctg_th_img_id_idx on product_category (thumbnail_image_id);
create index pctg_type_id_idx on product_category (type_id);

create index pcatpr_product_idx on product_catalog_pricing (product_id);



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