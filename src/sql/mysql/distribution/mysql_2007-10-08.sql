ALTER TABLE web_page ADD page_alias INT REFERENCES web_page(page_id);

CREATE TABLE lookup_container_menu (
  code INT AUTO_INCREMENT PRIMARY KEY,
  cname VARCHAR(300),
  description VARCHAR(300),
  default_item BOOLEAN DEFAULT FALSE,
  level INTEGER,
  enabled BOOLEAN DEFAULT TRUE,
  link_module_id INT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

--CREATE UNIQUE INDEX u_lcontmenu_cname_lmoduleid ON lookup_container_menu (cname);

CREATE TABLE web_page_role_map (
  page_role_map_id INT AUTO_INCREMENT PRIMARY KEY,
  web_page_id INT NOT NULL REFERENCES web_page(page_id),
  role_id INT NOT NULL REFERENCES role(role_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);

create table web_icelet_dashboard_map (
  dashboard_map_id INT AUTO_INCREMENT PRIMARY KEY,
  icelet_id INT REFERENCES web_icelet(icelet_id),
  link_module_id INT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL references `access`(user_id)
);

create table web_icelet_customtab_map (
  custom_map_id INT AUTO_INCREMENT PRIMARY KEY,
  icelet_id INT REFERENCES web_icelet(icelet_id),
  link_container_id int REFERENCES lookup_container_menu (code),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL references `access`(user_id)
);

create table web_icelet_publicwebsite (
  icelet_publicwebsite_id INT AUTO_INCREMENT PRIMARY KEY,
  icelet_id INT references web_icelet(icelet_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL references `access`(user_id)
);

alter table web_page add column link_module_id int references permission_category (category_id);
alter table web_page add column link_container_id int references lookup_container_menu(code);

alter table permission_category add column dashboards BOOLEAN NOT NULL DEFAULT false;
UPDATE permission_category SET dashboards = false;

alter table permission_category add column customtabs BOOLEAN NOT NULL DEFAULT false;
UPDATE permission_category SET customtabs = false;
--Email server types 

CREATE TABLE lookup_emailserver_types (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered timestamp DEFAULT CURRENT_TIMESTAMP,
  modified timestamp
);

--Email account inbox behavior

CREATE TABLE lookup_emailaccount_inbox_behavior (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered timestamp DEFAULT CURRENT_TIMESTAMP,
  modified timestamp
);


--Email account processing behavior

CREATE TABLE lookup_emailaccount_processing_behavior (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered timestamp DEFAULT CURRENT_TIMESTAMP,
  modified timestamp
);

ALTER TABLE permission_category ADD CONSTRAINT permission_category_constant_uk UNIQUE(constant);

ALTER TABLE permission_category ADD COLUMN email_accounts BOOLEAN DEFAULT false;

--Email account

CREATE TABLE email_account (
  email_id INT AUTO_INCREMENT PRIMARY KEY,
  server_type INTEGER NOT NULL REFERENCES lookup_emailserver_types(code),
  alias VARCHAR(255) NOT NULL,
  description TEXT,
  email_address VARCHAR(255) NOT NULL,
  account_password VARCHAR(255) NOT NULL,
  server_address VARCHAR(255) NOT NULL,
  inbox_behavior INTEGER NOT NULL REFERENCES lookup_emailaccount_inbox_behavior(code),
  imap_path_prefix VARCHAR(255),
  port INTEGER NOT NULL,
  `ssl` BOOLEAN DEFAULT false,
  include_domain BOOLEAN DEFAULT false,
  schedule INTEGER NOT NULL,
  processing_behavior INTEGER NOT NULL REFERENCES lookup_emailaccount_processing_behavior(code),
  module_id INTEGER NOT NULL REFERENCES permission_category(constant),
  entered timestamp DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified timestamp,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);
-- TASK: "Offline Client"
-- NOTE: Added to new_sync.sql 2006-10-30 by holub

ALTER TABLE sync_client ADD COLUMN user_id INT REFERENCES access(user_id);
-- TASK: "Offline Client"
-- NOTE: Added to new_cdb.sql 2006-11-09

ALTER TABLE permission ADD COLUMN permission_offline_view BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE permission ADD COLUMN permission_offline_add BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE permission ADD COLUMN permission_offline_edit BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE permission ADD COLUMN permission_offline_delete BOOLEAN NOT NULL DEFAULT false;

ALTER TABLE role_permission ADD COLUMN role_offline_view BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE role_permission ADD COLUMN role_offline_add BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE role_permission ADD COLUMN role_offline_edit BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE role_permission ADD COLUMN role_offline_delete BOOLEAN NOT NULL DEFAULT false;
-- TASK: "Offline Client"
-- NOTE: Added to new_sync.sql 2006-11-13

CREATE TABLE sync_package (
  package_id INT AUTO_INCREMENT PRIMARY KEY,
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  type INT NOT NULL,
  size INT DEFAULT 0,
  status_id INT NOT NULL,
  recipient INT NOT NULL,
  status_date TIMESTAMP NULL,
  last_anchor TIMESTAMP NULL,
  next_anchor TIMESTAMP NULL,
  package_file_id INT REFERENCES project_files(item_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sync_package_data (
  data_id INT AUTO_INCREMENT PRIMARY KEY,
  package_id INT NOT NULL REFERENCES sync_package(package_id),
  table_id INT NOT NULL REFERENCES sync_table(table_id),
  action INT NOT NULL,
  identity_start INT NOT NULL,
  `offset` INT,
  items INT,
  last_anchor TIMESTAMP NULL,
  next_anchor TIMESTAMP NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE sync_client ADD COLUMN package_file_id INT REFERENCES project_files(item_id);

-- sync_package_data
ALTER TABLE sync_package_data MODIFY action INT NOT NULL;
-- Fields "entered" and "modified" for enabling syncing of lookup tables

ALTER TABLE lookup_site_id              ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_step_actions         ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_survey_types         ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_sic_codes            ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_industry             ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_contact_types        ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_account_types        ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_department           ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_orgaddress_types     ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_orgemail_types       ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_orgphone_types       ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_im_types             ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_im_services          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_contact_source       ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_contact_rating       ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_textmessage_types    ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_employment_types     ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_locale               ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_contactaddress_types ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_contactemail_types   ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_contactphone_types   ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_access_types         ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_account_size         ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_segments             ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_sub_segment          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_title                ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE permission_category         ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE permission                  ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE role_permission             ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_stage                ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_delivery_options     ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE notification                CHANGE item_modified item_modified timestamp NULL;
ALTER TABLE notification                ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_relationship_types   ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_document_store_permission_category ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_document_store_role  ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_document_store_permission ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_help_features        ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_call_types           ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_call_priority        ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_call_reminder        ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_call_result          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_opportunity_types    ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_opportunity_environment ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_opportunity_competitors ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_opportunity_event_compelling ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_opportunity_budget ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_order_status       ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_order_type         ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_order_terms        ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_order_source       ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_orderaddress_types ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_payment_methods    ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_creditcard_types   ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_payment_status     ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_payment_gateway    ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_currency           ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_product_category_type ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_product_type          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_product_manufacturer  ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_product_format        ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_product_shipping      ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_product_ship_time     ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_product_tax           ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_recurring_type        ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_product_conf_result   ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_product_keyword       ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_project_activity      ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_project_priority      ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_project_status        ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_project_loe           ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_project_role          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_project_category      ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_news_template         ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_project_permission_category ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_project_permission    ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_quote_status          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_quote_type            ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_quote_terms           ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_quote_source          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_quote_delivery        ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_quote_condition       ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_quote_remarks         ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_revenue_types         ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_revenuedetail_types   ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_asset_status          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_sc_category           ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_sc_type               ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_response_model        ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_phone_model           ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_onsite_model          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_email_model           ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_hours_reason          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_asset_manufacturer    ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_asset_vendor          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_asset_materials       ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_task_priority         ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_task_loe              ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_task_category         ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_ticket_task_category  ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE ticket_level                 ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE ticket_severity              ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_ticketsource          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_ticket_status         ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE ticket_priority              ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_ticket_escalation     ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_ticket_cause          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_ticket_resolution     ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE lookup_ticket_state          ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

UPDATE lookup_site_id                            SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_step_actions                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_survey_types                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_sic_codes                          SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_industry                           SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_contact_types                      SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_account_types                      SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_department                         SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_orgaddress_types                   SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_orgemail_types                     SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_orgphone_types                     SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_im_types                           SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_im_services                        SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_contact_source                     SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_contact_rating                     SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_textmessage_types                  SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_employment_types                   SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_locale                             SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_contactaddress_types               SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_contactemail_types                 SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_contactphone_types                 SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_access_types                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_account_size                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_segments                           SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_sub_segment                        SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_title                              SET entered = CURRENT_TIMESTAMP;
UPDATE permission_category                       SET entered = CURRENT_TIMESTAMP;
UPDATE permission                                SET entered = CURRENT_TIMESTAMP;
UPDATE role_permission                           SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_stage                              SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_delivery_options                   SET entered = CURRENT_TIMESTAMP;
UPDATE notification                              SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_relationship_types                 SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_document_store_permission_category SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_document_store_role                SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_document_store_permission          SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_help_features                      SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_call_types                         SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_call_priority                      SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_call_reminder                      SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_call_result                        SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_opportunity_types                  SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_opportunity_environment            SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_opportunity_competitors            SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_opportunity_event_compelling       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_opportunity_budget                 SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_order_status                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_order_type                         SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_order_terms                        SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_order_source                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_orderaddress_types                 SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_payment_methods                    SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_creditcard_types                   SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_payment_status                     SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_payment_gateway                    SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_currency                           SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_product_category_type              SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_product_type                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_product_manufacturer               SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_product_format                     SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_product_shipping                   SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_product_ship_time                  SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_product_tax                        SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_recurring_type                     SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_product_conf_result                SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_product_keyword                    SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_project_activity                   SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_project_priority                   SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_project_status                     SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_project_loe                        SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_project_role                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_project_category                   SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_news_template                      SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_project_permission_category        SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_project_permission                 SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_quote_status                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_quote_type                         SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_quote_terms                        SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_quote_source                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_quote_delivery                     SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_quote_condition                    SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_quote_remarks                      SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_revenue_types                      SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_revenuedetail_types                SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_asset_status                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_sc_category                        SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_sc_type                            SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_response_model                     SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_phone_model                        SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_onsite_model                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_email_model                        SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_hours_reason                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_asset_manufacturer                 SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_asset_vendor                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_asset_materials                    SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_task_priority                      SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_task_loe                           SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_task_category                      SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_ticket_task_category               SET entered = CURRENT_TIMESTAMP;
UPDATE ticket_level                              SET entered = CURRENT_TIMESTAMP;
UPDATE ticket_severity                           SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_ticketsource                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_ticket_status                      SET entered = CURRENT_TIMESTAMP;
UPDATE ticket_priority                           SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_ticket_escalation                  SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_ticket_cause                       SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_ticket_resolution                  SET entered = CURRENT_TIMESTAMP;
UPDATE lookup_ticket_state                       SET entered = CURRENT_TIMESTAMP;

ALTER TABLE lookup_site_id              ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_step_actions         ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_survey_types         ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_sic_codes            ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_industry             ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_contact_types        ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_account_types        ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_department           ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_orgaddress_types     ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_orgemail_types       ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_orgphone_types       ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_im_types             ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_im_services          ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_contact_source       ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_contact_rating       ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_textmessage_types    ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_employment_types     ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_locale               ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_contactaddress_types ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_contactemail_types   ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_contactphone_types   ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_access_types         ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_account_size         ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_segments             ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_sub_segment          ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_title                ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE permission_category         ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE permission                  ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE role_permission             ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_stage                ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_delivery_options     ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE notification                ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_relationship_types   ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_document_store_permission_category ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_document_store_role  ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_document_store_permission ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_help_features        ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_call_types           ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_call_priority        ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_call_reminder        ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_call_result          ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_opportunity_types    ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_opportunity_environment ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_opportunity_competitors ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_opportunity_event_compelling ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_opportunity_budget ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_order_status       ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_order_type         ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_order_terms        ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_order_source       ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_orderaddress_types ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_payment_methods    ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_creditcard_types   ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_payment_status     ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_payment_gateway    ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_currency           ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_product_category_type ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_product_type          ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_product_manufacturer  ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_product_format        ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_product_shipping      ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_product_ship_time     ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_product_tax           ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_recurring_type        ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_product_conf_result   ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_product_keyword       ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_project_activity      ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_project_priority      ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_project_status        ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_project_loe           ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_project_role          ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_project_category      ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_news_template         ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_project_permission_category ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_project_permission    ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_quote_status          ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_quote_type            ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_quote_terms           ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_quote_source          ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_quote_delivery        ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_quote_condition       ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_quote_remarks         ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_revenue_types         ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_revenuedetail_types   ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_asset_status          ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_sc_category           ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_sc_type               ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_response_model        ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_phone_model           ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_onsite_model          ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_email_model           ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_hours_reason          ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_asset_manufacturer    ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_asset_vendor          ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_asset_materials       ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_task_priority         ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_task_loe              ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_task_category         ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_ticket_task_category  ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE ticket_level                 ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE ticket_severity              ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_ticketsource          ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_ticket_status         ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE ticket_priority              ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_ticket_escalation     ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_ticket_cause          ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_ticket_resolution     ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE lookup_ticket_state          ADD COLUMN modified TIMESTAMP NULL;
-- ip addr fields need to have length = 30
ALTER TABLE `access` MODIFY last_ip VARCHAR(30);
ALTER TABLE access_log MODIFY ip VARCHAR(30);
ALTER TABLE active_survey_responses MODIFY ip_address VARCHAR(30);
ALTER TABLE sync_log MODIFY ip VARCHAR(30);
-- Fields "entered" and "modified" for enabling syncing of lookup tables

ALTER TABLE lookup_duration_type ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE lookup_duration_type SET entered = CURRENT_TIMESTAMP;

ALTER TABLE lookup_duration_type ADD COLUMN modified TIMESTAMP NULL;
-- missing modified column
ALTER TABLE module_field_categorylink ADD COLUMN modified TIMESTAMP NULL;
-- missing modified column
ALTER TABLE custom_field_category ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE custom_field_group ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE custom_field_info ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE custom_field_lookup ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE custom_field_data ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE custom_field_data SET entered = CURRENT_TIMESTAMP;

ALTER TABLE custom_field_data ADD COLUMN modified TIMESTAMP NULL;
-- missing sync fields
ALTER TABLE project_files_download MODIFY download_date TIMESTAMP NULL;
ALTER TABLE project_files_download ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE project_files_download SET entered = CURRENT_TIMESTAMP;

ALTER TABLE project_files_download ADD COLUMN modified TIMESTAMP NULL;
-- missing entered and modified columns

-- action_plan_category
ALTER TABLE action_plan_category ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_plan_category SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_plan_category ADD COLUMN modified TIMESTAMP NULL;

-- action_plan_editor_lookup
ALTER TABLE action_plan_editor_lookup ADD COLUMN modified TIMESTAMP NULL;

-- action_phase
ALTER TABLE action_phase ADD COLUMN modified TIMESTAMP NULL;

-- action_step
ALTER TABLE action_step ADD COLUMN modified TIMESTAMP NULL;

-- action_plan_work_notes
ALTER TABLE action_plan_work_notes MODIFY submitted TIMESTAMP NULL;
ALTER TABLE action_plan_work_notes ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_plan_work_notes SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_plan_work_notes ADD COLUMN modified TIMESTAMP NULL;

-- action_item_work_notes
ALTER TABLE action_item_work_notes MODIFY submitted TIMESTAMP NULL;
ALTER TABLE action_item_work_notes ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_item_work_notes SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_item_work_notes ADD COLUMN modified TIMESTAMP NULL;

-- action_step_lookup
ALTER TABLE action_step_lookup ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_step_lookup SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_step_lookup ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE action_plan_constants ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_plan_constants SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_plan_constants ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE step_action_map ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE step_action_map SET entered = CURRENT_TIMESTAMP;

ALTER TABLE step_action_map ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE action_step_account_types ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_step_account_types SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_step_account_types ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE action_item_work_selection ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE action_item_work_selection SET entered = CURRENT_TIMESTAMP;

ALTER TABLE action_item_work_selection ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE ticket_category_plan_map ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE ticket_category_plan_map SET entered = CURRENT_TIMESTAMP;

ALTER TABLE ticket_category_plan_map ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE ticket_category_draft_plan_map ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE ticket_category_draft_plan_map SET entered = CURRENT_TIMESTAMP;

ALTER TABLE ticket_category_draft_plan_map ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE opportunity_component_log ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE ticket_category ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE ticket_category SET entered = CURRENT_TIMESTAMP;

ALTER TABLE ticket_category ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE ticket_category_draft ADD COLUMN entered TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE ticket_category_draft SET entered = CURRENT_TIMESTAMP;

ALTER TABLE ticket_category_draft ADD COLUMN modified TIMESTAMP NULL;

UPDATE permission_category SET dashboards = true, customtabs = true WHERE constant = 1;
UPDATE permission_category SET dashboards = true, customtabs = true WHERE constant = 2;
UPDATE permission_category SET dashboards = true, customtabs = true WHERE constant = 228051100;
ALTER TABLE campaign_run MODIFY run_date TIMESTAMP NULL;
ALTER TABLE campaign_run ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE campaign_run SET entered = CURRENT_TIMESTAMP;

ALTER TABLE campaign_run ADD COLUMN modified TIMESTAMP NULL;

ALTER TABLE scheduled_recipient ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE scheduled_recipient SET entered = CURRENT_TIMESTAMP;

ALTER TABLE scheduled_recipient ADD COLUMN modified TIMESTAMP NULL;

UPDATE sync_table
	SET mapped_class_name = 'org.aspcfs.modules.communications.base.ScheduledRecipient'
	WHERE element_name = 'scheduledRecipient';

UPDATE sync_table
	SET mapped_class_name = 'org.aspcfs.modules.communications.base.ScheduledRecipientList'
	WHERE element_name = 'scheduledRecipientList';

UPDATE sync_table
	SET mapped_class_name = 'org.aspcfs.modules.communications.base.CampaignRun'
	WHERE element_name = 'campaignRun';

UPDATE sync_table
	SET mapped_class_name = 'org.aspcfs.modules.communications.base.CampaignRunList'
	WHERE element_name = 'campaignRunList';

--Table for Graph Types
CREATE TABLE lookup_graph_type(
 code INT AUTO_INCREMENT PRIMARY KEY,
 description VARCHAR(300) NOT NULL,
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER DEFAULT 0,
 enabled BOOLEAN DEFAULT TRUE,
 entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified timestamp NULL
);
UPDATE project_files_version SET modified = entered WHERE modified IS NULL and entered IS NOT NULL;
ALTER TABLE custom_field_info ADD COLUMN default_value text;
ALTER TABLE web_tab ADD COLUMN keywords varchar(300);
ALTER TABLE web_page ADD COLUMN keywords varchar(300);
ALTER TABLE call_log ADD followup_end_date TIMESTAMP NULL;
ALTER TABLE call_log ADD followup_end_date_timezone VARCHAR(255);
ALTER TABLE call_log ADD followup_location VARCHAR(255);
ALTER TABLE call_log ADD followup_length INT;
ALTER TABLE call_log ADD followup_length_duration INT REFERENCES lookup_call_reminder(code);

ALTER TABLE call_log ADD call_start_date TIMESTAMP NULL;
ALTER TABLE call_log ADD call_start_date_timezone VARCHAR(255);
ALTER TABLE call_log ADD call_end_date TIMESTAMP NULL;
ALTER TABLE call_log ADD call_end_date_timezone VARCHAR(255);
ALTER TABLE call_log ADD call_location VARCHAR(255);
ALTER TABLE call_log ADD call_length_duration INT REFERENCES lookup_call_reminder(code);
CREATE TABLE call_log_participant(
  participant_id INT AUTO_INCREMENT PRIMARY KEY,
  call_id INT NOT NULL REFERENCES call_log (call_id),
  contact_id INT NOT NULL REFERENCES contact (contact_id),
  is_available INT DEFAULT 1,
  entered timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified timestamp,
  enteredby INT NOT NULL REFERENCES `access` (user_id),
  modifiedby INT NOT NULL REFERENCES `access` (user_id),
  is_followup INT DEFAULT 0
);

ALTER TABLE call_log ADD COLUMN email_participants BOOLEAN DEFAULT false;
UPDATE call_log SET email_participants = false;

ALTER TABLE call_log ADD COLUMN email_followup_participants BOOLEAN DEFAULT false;
UPDATE call_log SET email_followup_participants = false;

ALTER TABLE call_log DROP COLUMN followup_date;
ALTER TABLE `access` ADD COLUMN temp_password varchar(80);
ALTER TABLE `access` ADD COLUMN temp_webdav_password varchar(80);

-- Change the datatype to TEXT
ALTER TABLE business_process_parameter_library ADD COLUMN default_value_temp VARCHAR(4000);
UPDATE business_process_parameter_library SET default_value_temp = default_value;
ALTER TABLE business_process_parameter_library DROP default_value;
ALTER TABLE business_process_parameter_library ADD COLUMN default_value TEXT;
UPDATE business_process_parameter_library SET default_value = default_value_temp;
ALTER TABLE business_process_parameter_library DROP default_value_temp;

ALTER TABLE business_process_parameter ADD COLUMN param_value_temp VARCHAR(4000);
UPDATE business_process_parameter SET param_value_temp = param_value;
ALTER TABLE business_process_parameter DROP param_value;
ALTER TABLE business_process_parameter ADD COLUMN param_value TEXT;
UPDATE business_process_parameter SET param_value = param_value_temp;
ALTER TABLE business_process_parameter DROP param_value_temp;

ALTER TABLE business_process_component_parameter ADD COLUMN param_value_temp VARCHAR(4000);
UPDATE business_process_component_parameter SET param_value_temp = param_value;
ALTER TABLE business_process_component_parameter DROP param_value;
ALTER TABLE business_process_component_parameter ADD COLUMN param_value TEXT;
UPDATE business_process_component_parameter SET param_value = param_value_temp;
ALTER TABLE business_process_component_parameter DROP param_value_temp;

-- Add index for contact LEFT JOIN import i ON (c.import_id = i.import_id); add to install scripts too


CREATE TABLE lookup_contact_stage (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

ALTER TABLE contact ADD COLUMN stage INT REFERENCES lookup_contact_stage(code);
ALTER TABLE organization ADD COLUMN comments TEXT;
ALTER TABLE saved_criterialist ADD COLUMN  source int DEFAULT -1;
update saved_criterialist set source = contact_source;
alter table saved_criterialist drop contact_source;
CREATE TABLE recent_items (
  item_id INT AUTO_INCREMENT PRIMARY KEY,
  link_module_id int NOT NULL,
  link_item_id int NOT NULL,
  url varchar(1000) NOT NULL,
  item_name varchar(255) NOT NULL,
  user_id int NOT NULL references access (user_id),
  entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp NULL
);
ALTER TABLE web_site ADD COLUMN scripts text;
CREATE TABLE lookup_webpage_priority (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300),
  default_item boolean DEFAULT false,
  "level" integer,
  constant float,
  enabled boolean DEFAULT true,
  entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp NULL
) ;

CREATE TABLE lookup_sitechange_frequency (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300),
  default_item boolean DEFAULT false,
  "level" integer,
  constant VARCHAR(300),
  enabled boolean DEFAULT true,
  entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp NULL
);

alter table web_site add column url varchar (2000);

alter table web_page add column change_freq integer REFERENCES lookup_sitechange_frequency(code);
alter table web_page add column page_priority INT REFERENCES lookup_webpage_priority(code);
create table help_introduction_preference (
		user_id INT NOT NULL REFERENCES `access`(user_id),
		module_id INT NOT NULL REFERENCES help_module(module_id)
);
-- Droping few not null values from cfsinbox_message table

ALTER TABLE cfsinbox_message CHANGE reply_id reply_id INT; 
ALTER TABLE cfsinbox_message CHANGE enteredby enteredby INT;
ALTER TABLE cfsinbox_message ADD CONSTRAINT cfsinbox_message_enteredby_fkey FOREIGN KEY (enteredby)
      REFERENCES `access`(user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cfsinbox_message CHANGE sent sent TIMESTAMP NULL;
ALTER TABLE cfsinbox_message CHANGE modifiedby modifiedby INT;
ALTER TABLE cfsinbox_message ADD CONSTRAINT cfsinbox_message_modifiedby_fkey FOREIGN KEY (modifiedby)
      REFERENCES `access`(user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Droping few not null values from cfsinbox_messagelink table
ALTER TABLE cfsinbox_messagelink CHANGE sent_to sent_to INT;
ALTER TABLE cfsinbox_messagelink ADD CONSTRAINT cfsinbox_messagelink_sent_to_fkey FOREIGN KEY (sent_to)
      REFERENCES contact(contact_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE cfsinbox_messagelink CHANGE sent_from sent_from INT;
ALTER TABLE cfsinbox_messagelink ADD CONSTRAINT cfsinbox_messagelink_sent_from_fkey FOREIGN KEY (sent_from)
      REFERENCES `access`(user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

-- Adding few fields and constraints to cfsinbox_messagelink table 
alter table cfsinbox_messagelink add sent_to_mail_id varchar(255); 
alter table cfsinbox_messagelink add sent_from_mail_id varchar(255) ;
alter table cfsinbox_messagelink add email_account_id int4;
alter table cfsinbox_messagelink add replied_to_message_id int4;
alter table cfsinbox_messagelink add item_id int4;
alter table cfsinbox_messagelink add last_action int4;



alter table cfsinbox_messagelink add  CONSTRAINT cfsinbox_messagelink_email_account_id_fkey FOREIGN KEY (email_account_id)
      REFERENCES `email_account` (email_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

-- Email account user preferences table

CREATE TABLE email_account_user_preferences (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT REFERENCES `access` (user_id),
  email_account_id INT NOT NULL REFERENCES email_account (email_id)
);


-- Adding few fields  to knowledge_base table
  
ALTER TABLE knowledge_base ADD COLUMN status varchar(10) DEFAULT 'Draft' NOT NULL ;
ALTER TABLE knowledge_base ADD COLUMN portal_access_allowed BOOLEAN  NOT NULL DEFAULT false;














CREATE TABLE lookup_kb_status (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  constant_id INTEGER NOT NULL,
  entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp NULL
);
-- Adding few fields  to knowledge_base table
  
ALTER TABLE knowledge_base DROP COLUMN status;
ALTER TABLE knowledge_base ADD COLUMN status INTEGER NOT NULL default 1 REFERENCES lookup_kb_status(code);
UPDATE knowledge_base SET status = 1;


-- Adding  field  tocfsinbox_messagelink table

ALTER TABLE cfsinbox_messagelink ADD COLUMN replyto varchar(255) ;



-- Inserting the Record into    lookup_kb_status table
-- Done in .bsh
--INSERT INTO lookup_kb_status(description,constant_id) VALUES('Draft',1);
--INSERT INTO lookup_kb_status(description, constant_id) VALUES('Approved',2);



-- Add the message_summary field in ticketlog table
ALTER TABLE ticketlog ADD COLUMN message_summary text;


-- Add the messages_deleted field in ticketlog table
  
ALTER TABLE ticketlog ADD COLUMN messages_deleted BOOLEAN DEFAULT false;











-- Add the messages_deleted field in ticketlog table
  
ALTER TABLE email_account ADD COLUMN enabled BOOLEAN DEFAULT false;











-- Add the messages_deleted field in ticketlog table
  
ALTER TABLE email_account ADD COLUMN auto_associate BOOLEAN DEFAULT false;











ALTER TABLE ticket ADD COLUMN submitter_id INT REFERENCES organization;
ALTER TABLE ticket ADD COLUMN submitter_contact_id INT REFERENCES contact;

--postponed this until the application is updated with the submitter functionality
--update ticket set submitter_id = org_id, submitter_contact_id = contact_id;

INSERT INTO lookup_relationship_types (category_id_maps_from, category_id_maps_to, reciprocal_name_1, reciprocal_name_2, level, default_item, enabled)
    VALUES (42420034, 42420034, 'Reseller of', 'Client of', 200, false, true);

ALTER TABLE service_contract ADD COLUMN submitter_id INT REFERENCES organization;
UPDATE lookup_call_types SET default_item = false WHERE default_item = true;
ALTER TABLE asset_category_draft ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE asset_category_draft ADD COLUMN modified TIMESTAMP NULL;
ALTER TABLE action_plan_category_draft ADD COLUMN
  entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE action_plan_category_draft ADD COLUMN
  modified timestamp NULL;
UPDATE permission SET category_id = (SELECT category_id FROM permission_category WHERE constant = 10) WHERE permission = 'qa';
DELETE from permission_category WHERE constant = 15;
UPDATE permission SET description = 'Help Content Editor' WHERE permission = 'qa';
