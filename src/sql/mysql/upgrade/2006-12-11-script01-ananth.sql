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
