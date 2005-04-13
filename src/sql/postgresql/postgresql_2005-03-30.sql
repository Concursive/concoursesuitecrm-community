-- Script (C) 2005 Dark Horse Ventures, all rights reserved
-- Database upgrade v3.0 (2005-03-28)

-- access --
    ALTER TABLE access ADD webdav_password character varying(80);
    ALTER TABLE access ADD hidden boolean;
    UPDATE access SET hidden = false;
    ALTER TABLE access ALTER COLUMN hidden SET DEFAULT false;

CREATE TABLE lookup_im_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_im_services (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_contact_source_code_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_contact_source (
    code integer DEFAULT nextval('lookup_contact_source_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_contact_rating_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_contact_rating (
    code integer DEFAULT nextval('lookup_contact_rating_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_textmessage_typ_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE TABLE lookup_textmessage_types (
    code integer DEFAULT nextval('lookup_textmessage_typ_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


DROP TABLE lookup_instantmessenger_types;
DROP SEQUENCE lookup_instantmessenge_code_seq;

-- contact --
    ALTER TABLE contact ADD information_update_date timestamp(3) without time zone;
    ALTER TABLE contact ALTER COLUMN information_update_date SET DEFAULT ('now'::text)::timestamp(6) with time zone;
    ALTER TABLE contact ADD lead boolean;
    UPDATE contact SET lead = false;
    ALTER TABLE contact ALTER COLUMN lead SET DEFAULT false;
    ALTER TABLE contact ADD lead_status integer;
    ALTER TABLE contact ADD source integer;
    ALTER TABLE contact ADD rating integer;
    ALTER TABLE contact ADD comments character varying(255);
    ALTER TABLE contact ADD conversion_date TIMESTAMP(3) NULL;

CREATE TABLE contact_lead_skipped_map (
    map_id serial NOT NULL,
    user_id integer NOT NULL,
    contact_id integer NOT NULL
);

CREATE TABLE contact_lead_read_map (
    map_id serial NOT NULL,
    user_id integer NOT NULL,
    contact_id integer NOT NULL
);

-- permission category --
    ALTER TABLE permission_category ADD webdav boolean;
    UPDATE permission_category SET webdav = false;
    ALTER TABLE permission_category ALTER COLUMN webdav SET DEFAULT false;
    ALTER TABLE permission_category ALTER COLUMN webdav SET NOT NULL;

    ALTER TABLE permission_category ADD logos boolean;
    UPDATE permission_category SET logos = false;
    ALTER TABLE permission_category ALTER COLUMN logos SET DEFAULT false;
    ALTER TABLE permission_category ALTER COLUMN logos SET NOT NULL;

    ALTER TABLE permission_category ADD constant integer;
    UPDATE permission_category SET constant=0;
    ALTER TABLE permission_category ALTER COLUMN constant SET NOT NULL;
    
-- organization_address --
    ALTER TABLE organization_address ADD primary_address boolean;
    UPDATE organization_address SET primary_address = false;
    ALTER TABLE organization_address ALTER COLUMN primary_address SET DEFAULT false;
    ALTER TABLE organization_address ALTER COLUMN primary_address SET NOT NULL;

-- organization_emailaddress --
    ALTER TABLE organization_emailaddress ADD primary_email boolean;
    UPDATE organization_emailaddress SET primary_email = false;
    ALTER TABLE organization_emailaddress ALTER COLUMN primary_email SET DEFAULT false;
    ALTER TABLE organization_emailaddress ALTER COLUMN primary_email SET NOT NULL;

-- organiztion_phone --
    ALTER TABLE organization_phone ADD primary_number boolean;
    UPDATE organization_phone SET primary_number = false;
    ALTER TABLE organization_phone ALTER COLUMN primary_number SET DEFAULT false;
    ALTER TABLE organization_phone ALTER COLUMN primary_number SET NOT NULL;

CREATE TABLE contact_imaddress (
    address_id serial NOT NULL,
    contact_id integer,
    imaddress_type integer,
    imaddress_service integer,
    imaddress character varying(256),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    primary_im boolean DEFAULT false NOT NULL
);



CREATE TABLE contact_textmessageaddress (
    address_id serial NOT NULL,
    contact_id integer,
    textmessageaddress_type integer,
    textmessageaddress character varying(256),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    primary_textmessage_address boolean DEFAULT false NOT NULL
);

CREATE TABLE webdav (
    id serial NOT NULL,
    category_id integer NOT NULL,
    class_name character varying(300) NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);

CREATE TABLE lookup_relationship_types (
    type_id serial NOT NULL,
    category_id_maps_from integer NOT NULL,
    category_id_maps_to integer NOT NULL,
    reciprocal_name_1 character varying(512),
    reciprocal_name_2 character varying(512),
    "level" integer DEFAULT 0,
    default_item boolean DEFAULT false,
    enabled boolean DEFAULT true
);



CREATE TABLE relationship (
    relationship_id serial NOT NULL,
    type_id integer,
    object_id_maps_from integer NOT NULL,
    category_id_maps_from integer NOT NULL,
    object_id_maps_to integer NOT NULL,
    category_id_maps_to integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);

CREATE TABLE lookup_news_template (
    code serial NOT NULL,
    description character varying(255) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL,
    load_article boolean DEFAULT false,
    load_project_article_list boolean DEFAULT false,
    load_article_linked_list boolean DEFAULT false,
    load_public_projects boolean DEFAULT false,
    load_article_category_list boolean DEFAULT false,
    mapped_jsp character varying(255) NOT NULL
);

-- projects --
    ALTER TABLE projects ADD portal_default boolean;
    UPDATE projects SET portal_default = false;
    ALTER TABLE projects ALTER COLUMN portal_default SET DEFAULT false;
    ALTER TABLE projects ALTER COLUMN portal_default SET NOT NULL;

    ALTER TABLE projects ADD portal_header character varying(255);
    ALTER TABLE projects ADD portal_format character varying(255);
    ALTER TABLE projects ADD portal_key character varying(255);
    ALTER TABLE projects ADD portal_build_news_body boolean;
    UPDATE projects SET portal_build_news_body = false;
    ALTER TABLE projects ALTER COLUMN portal_build_news_body SET DEFAULT false;
    ALTER TABLE projects ALTER COLUMN portal_build_news_body SET NOT NULL;

    ALTER TABLE projects ADD portal_news_menu boolean;
    UPDATE projects SET portal_news_menu = false;
    ALTER TABLE projects ALTER COLUMN portal_news_menu SET DEFAULT false;
    ALTER TABLE projects ALTER COLUMN portal_news_menu SET NOT NULL;

    ALTER TABLE projects ADD description text;
    ALTER TABLE projects ADD allows_user_observers boolean;
    UPDATE projects SET allows_user_observers = false;
    ALTER TABLE projects ALTER COLUMN allows_user_observers SET DEFAULT false;
    ALTER TABLE projects ALTER COLUMN allows_user_observers SET NOT NULL;

    ALTER TABLE projects ADD "level" integer;
    UPDATE projects SET "level" = 10;
    ALTER TABLE projects ALTER COLUMN "level" SET DEFAULT 10;
    ALTER TABLE projects ALTER COLUMN "level" SET NOT NULL;

    ALTER TABLE projects ADD portal_page_type integer;
    ALTER TABLE projects ADD calendar_enabled boolean;
    UPDATE projects SET calendar_enabled = false;
    ALTER TABLE projects ALTER COLUMN calendar_enabled SET DEFAULT true;
    ALTER TABLE projects ALTER COLUMN calendar_enabled SET NOT NULL;

    ALTER TABLE projects ADD calendar_label character varying(50);
    ALTER TABLE projects ADD accounts_enabled boolean;
    UPDATE projects SET accounts_enabled = true;
    ALTER TABLE projects ALTER COLUMN accounts_enabled SET DEFAULT true;
    ALTER TABLE projects ALTER COLUMN accounts_enabled SET NOT NULL;

    ALTER TABLE projects ADD accounts_label character varying(50);
    
-- project_assignments_status --
    ALTER TABLE project_assignments_status ADD percent_complete integer;
    ALTER TABLE project_assignments_status ADD project_status_id integer;
    ALTER TABLE project_assignments_status ADD user_assign_id integer;
    
-- project_issues_categories --
    ALTER TABLE project_issues_categories ADD allow_files boolean;
    UPDATE project_issues_categories SET allow_files = false;
    ALTER TABLE project_issues_categories ALTER COLUMN allow_files SET DEFAULT false;
    ALTER TABLE project_issues_categories ALTER COLUMN allow_files SET NOT NULL;

-- project_issue_replies --
    ALTER TABLE project_issue_replies ADD subject_tmp character varying(255);
    UPDATE project_issue_replies SET subject_tmp = subject;
    ALTER TABLE project_issue_replies DROP COLUMN subject;

    ALTER TABLE project_issue_replies ADD subject character varying(255);
    UPDATE project_issue_replies SET subject = subject_tmp;
    ALTER TABLE project_issue_replies ALTER subject SET NOT NULL;
    ALTER TABLE project_issue_replies DROP COLUMN subject_tmp;

-- project_files --
    ALTER TABLE project_files ADD default_file boolean;
    UPDATE project_files SET default_file = false;
    ALTER TABLE project_files ALTER COLUMN default_file SET DEFAULT false;


CREATE TABLE project_news_category (
    category_id serial NOT NULL,
    project_id integer NOT NULL,
    category_name character varying(255),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    "level" integer DEFAULT 0 NOT NULL,
    enabled boolean DEFAULT true
);

-- project_news --
    ALTER TABLE project_news ADD intro_tmp text;
    UPDATE project_news SET intro_tmp = intro;
    ALTER TABLE project_news DROP COLUMN intro;

    ALTER TABLE project_news ADD intro text;
    UPDATE project_news SET intro = intro_tmp;
    ALTER TABLE project_news DROP COLUMN intro_tmp;

    ALTER TABLE project_news ADD classification_id integer;
    UPDATE project_news SET classification_id = 20;
    ALTER TABLE project_news ALTER classification_id SET NOT NULL;
    ALTER TABLE project_news ADD template_id integer;

    
CREATE TABLE project_accounts (
    id serial NOT NULL,
    project_id integer NOT NULL,
    org_id integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone
);


CREATE SEQUENCE lookup_product_manufac_code_seq;
CREATE TABLE lookup_product_manufacturer (
    code INTEGER DEFAULT nextval('lookup_product_manufac_code_seq') NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);

-- product_catalog --
    ALTER TABLE product_catalog ADD manufacturer_id integer;

-- product_catalog_pricing --
    ALTER TABLE product_catalog_pricing ADD enabled boolean;
    UPDATE product_catalog_pricing SET enabled = true;
    ALTER TABLE product_catalog_pricing ALTER COLUMN enabled SET DEFAULT false;

    ALTER TABLE product_catalog_pricing ADD cost_currency integer;
    ALTER TABLE product_catalog_pricing ADD cost_amount double precision;
    UPDATE product_catalog_pricing SET cost_amount = 0;
    ALTER TABLE product_catalog_pricing ALTER COLUMN cost_amount SET DEFAULT 0;
    ALTER TABLE product_catalog_pricing ALTER COLUMN cost_amount SET NOT NULL;

    
-- product_option_configurator --
    ALTER TABLE product_option_configurator ADD configurator_name character varying(300);
    ALTER TABLE product_option_configurator ALTER COLUMN configurator_name SET NOT NULL;

-- product_option --
    ALTER TABLE product_option ADD option_name character varying(300);

    ALTER TABLE product_option ADD has_range boolean;
    ALTER TABLE product_option ALTER COLUMN has_range SET DEFAULT false;

    ALTER TABLE product_option ADD has_multiplier boolean;
    ALTER TABLE product_option ALTER COLUMN has_multiplier SET DEFAULT false;

-- product_option_values --
    ALTER TABLE product_option_values ADD enabled boolean;
    ALTER TABLE product_option_values ALTER COLUMN enabled SET DEFAULT true;

    ALTER TABLE product_option_values ADD value double precision;
    ALTER TABLE product_option_values ALTER COLUMN value SET DEFAULT 0;

    ALTER TABLE product_option_values ADD multiplier double precision;
    ALTER TABLE product_option_values ALTER COLUMN multiplier SET DEFAULT 1;

    ALTER TABLE product_option_values ADD range_min integer;
    ALTER TABLE product_option_values ALTER COLUMN range_min SET DEFAULT 1;

    ALTER TABLE product_option_values ADD range_max integer;
    ALTER TABLE product_option_values ALTER COLUMN range_max SET DEFAULT 1;

    ALTER TABLE product_option_values ADD cost_currency integer;
    ALTER TABLE product_option_values ADD cost_amount double precision;
    ALTER TABLE product_option_values ALTER COLUMN cost_amount SET DEFAULT 0;
    UPDATE product_option_values SET cost_amount = 0;
    ALTER TABLE product_option_values ALTER COLUMN cost_amount SET NOT NULL;

-- product_option_map --
    ALTER TABLE product_option_map DROP COLUMN value_id;

-- product_option_boolean --
    ALTER TABLE product_option_boolean ADD id integer;

-- product_option_float --
    ALTER TABLE product_option_float ADD id integer;

-- product_option_timestamp --
    ALTER TABLE product_option_timestamp ADD id integer;

-- product_option_integer --
    ALTER TABLE product_option_integer ADD id integer;

-- product_option_text --
    ALTER TABLE product_option_text ADD id integer;

-- asset_category --
    ALTER TABLE asset_category ALTER parent_cat_code SET DEFAULT 0;

-- asset_category_draft --
    ALTER TABLE asset_category_draft ALTER parent_cat_code SET DEFAULT 0;

CREATE TABLE lookup_ticket_status (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);

-- ticket_category --
    ALTER TABLE ticket_category ALTER parent_cat_code SET DEFAULT 0;

-- ticket_category_draft --
    ALTER TABLE ticket_category_draft ALTER parent_cat_code SET DEFAULT 0;

-- ticket --
    ALTER TABLE ticket ADD status_id integer;

-- quote_entry --
-- TODO? REMOVE DEFAULT org_id integer,--
    ALTER TABLE quote_entry DROP COLUMN product_id;
    ALTER TABLE quote_entry DROP COLUMN customer_product_id;

    ALTER TABLE quote_entry ADD product_id integer;
    ALTER TABLE quote_entry ADD customer_product_id integer;
    ALTER TABLE quote_entry ADD opp_id integer;
    ALTER TABLE quote_entry ADD "version" character varying(255);
    ALTER TABLE quote_entry ALTER "version" SET DEFAULT '0'::character varying;
    ALTER TABLE quote_entry ALTER "version" SET NOT NULL;
    ALTER TABLE quote_entry ADD group_id integer;
    ALTER TABLE quote_entry ALTER group_id SET NOT NULL;
    ALTER TABLE quote_entry ADD delivery_id integer;
    ALTER TABLE quote_entry ADD email_address text;
    ALTER TABLE quote_entry ADD phone_number text;
    ALTER TABLE quote_entry ADD address text;
    ALTER TABLE quote_entry ADD fax_number text;
    ALTER TABLE quote_entry ADD submit_action integer;
    ALTER TABLE quote_entry ADD closed timestamp(3) without time zone;
    ALTER TABLE quote_entry ADD show_total boolean;
    ALTER TABLE quote_entry ALTER show_total SET DEFAULT true;
    ALTER TABLE quote_entry ADD show_subtotal boolean;
    ALTER TABLE quote_entry ALTER show_subtotal SET DEFAULT true;
    ALTER TABLE quote_entry ADD logo_file_id integer;
    
-- quote_product --
    ALTER TABLE quote_product ADD estimated_delivery text;
    ALTER TABLE quote_product ADD "comment" character varying(300);
    
-- order_entry --
    ALTER TABLE order_entry ADD submitted timestamp(3) without time zone;

-- payment_creditcard --
    ALTER TABLE payment_creditcard DROP COLUMN payment_id;
    ALTER TABLE payment_creditcard ADD order_id integer;

-- payment_eft --
    ALTER TABLE payment_eft DROP COLUMN payment_id;
    ALTER TABLE payment_eft ADD order_id integer;

-- order_payment --
    DROP TABLE order_payment;

-- customer_product_history --
    ALTER TABLE customer_product_history ALTER order_id SET NOT NULL;
    ALTER TABLE customer_product_history ADD order_item_id integer;
    ALTER TABLE customer_product_history ALTER order_item_id SET NOT NULL;

CREATE TABLE lookup_payment_status (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);

CREATE TABLE order_payment (
    payment_id serial NOT NULL,
    order_id integer NOT NULL,
    payment_method_id integer NOT NULL,
    payment_amount double precision,
    authorization_ref_number character varying(30),
    authorization_code character varying(30),
    authorization_date timestamp(3) without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    order_item_id integer,
    history_id integer,
    date_to_process timestamp(3) without time zone,
    creditcard_id integer,
    bank_id integer,
    status_id integer
);

CREATE TABLE order_payment_status (
    payment_status_id serial NOT NULL,
    payment_id integer NOT NULL,
    status_id integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);

-- active_survey_responses --
    ALTER TABLE active_survey_responses ADD address_updated integer;

-- lookup_task_category --
    ALTER TABLE lookup_task_category ADD description_tmp character varying(255);
    UPDATE lookup_task_category SET description_tmp = description;
    ALTER TABLE lookup_task_category DROP COLUMN description;

    ALTER TABLE lookup_task_category ADD description character varying(255);
    UPDATE lookup_task_category SET description = description_tmp;
    ALTER TABLE lookup_task_category ALTER COLUMN description SET NOT NULL;
    ALTER TABLE lookup_task_category DROP COLUMN description_tmp;

-- task --
    ALTER TABLE task ADD description_tmp character varying(255);
    UPDATE task SET description_tmp = description;
    ALTER TABLE task DROP COLUMN description;

    ALTER TABLE task ADD description character varying(255);
    UPDATE task SET description = description_tmp;
    ALTER TABLE task ALTER COLUMN description SET NOT NULL;
    ALTER TABLE task DROP COLUMN description_tmp;


CREATE TABLE taskcategorylink_news (
    news_id integer NOT NULL,
    category_id integer NOT NULL
);



CREATE TABLE lookup_document_store_permission_category (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL
);



CREATE TABLE lookup_document_store_role (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL
);



CREATE TABLE lookup_document_store_permission (
    code serial NOT NULL,
    category_id integer,
    permission character varying(300) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL,
    default_role integer
);



CREATE TABLE document_store (
    document_store_id serial NOT NULL,
    template_id integer,
    title character varying(100) NOT NULL,
    shortdescription character varying(200) NOT NULL,
    requestedby character varying(50),
    requesteddept character varying(50),
    requestdate timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    requestdate_timezone character varying(255),
    approvaldate timestamp(3) without time zone,
    approvalby integer,
    closedate timestamp(3) without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    modifiedby integer NOT NULL
);



CREATE TABLE document_store_permissions (
    id serial NOT NULL,
    document_store_id integer NOT NULL,
    permission_id integer NOT NULL,
    userlevel integer NOT NULL
);



CREATE TABLE document_store_user_member (
    document_store_id integer NOT NULL,
    item_id integer NOT NULL,
    userlevel integer NOT NULL,
    status integer,
    last_accessed timestamp(3) without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    modifiedby integer NOT NULL
);



CREATE TABLE document_store_role_member (
    document_store_id integer NOT NULL,
    item_id integer NOT NULL,
    userlevel integer NOT NULL,
    status integer,
    last_accessed timestamp(3) without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    modifiedby integer NOT NULL
);



CREATE TABLE document_store_department_member (
    document_store_id integer NOT NULL,
    item_id integer NOT NULL,
    userlevel integer NOT NULL,
    status integer,
    last_accessed timestamp(3) without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    modifiedby integer NOT NULL
);

CREATE TABLE lookup_quote_delivery (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_quote_condition (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE quote_group (
    group_id serial NOT NULL,
    unused character(1)
);



CREATE TABLE quote_condition (
    map_id serial NOT NULL,
    quote_id integer NOT NULL,
    condition_id integer NOT NULL
);



CREATE TABLE quotelog (
    id serial NOT NULL,
    quote_id integer NOT NULL,
    source_id integer,
    status_id integer,
    terms_id integer,
    type_id integer,
    delivery_id integer,
    notes text,
    grand_total double precision,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    issued_date timestamp(3) without time zone,
    submit_action integer,
    closed timestamp(3) without time zone
);



CREATE TABLE lookup_quote_remarks (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE quote_remark (
    map_id serial NOT NULL,
    quote_id integer NOT NULL,
    remark_id integer NOT NULL
);


-- Quote product options properties
ALTER TABLE quote_product_option_text ADD COLUMN id INTEGER;
ALTER TABLE quote_product_option_float ADD COLUMN id INTEGER;
ALTER TABLE quote_product_option_timestamp ADD COLUMN id INTEGER;
ALTER TABLE quote_product_option_integer ADD COLUMN id INTEGER;
ALTER TABLE quote_product_option_boolean ADD COLUMN id INTEGER;


--------------  INSERTS --------------------

INSERT INTO lookup_im_types VALUES (1, 'Business', false, 10, true);
INSERT INTO lookup_im_types VALUES (2, 'Personal', false, 20, true);
INSERT INTO lookup_im_types VALUES (3, 'Other', false, 30, true);



INSERT INTO lookup_im_services VALUES (1, 'AOL Instant Messenger', false, 10, true);
INSERT INTO lookup_im_services VALUES (2, 'Jabber Instant Messenger', false, 20, true);
INSERT INTO lookup_im_services VALUES (3, 'MSN Instant Messenger', false, 30, true);



INSERT INTO lookup_contact_source VALUES (1, 'Advertisement', false, 10, true);
INSERT INTO lookup_contact_source VALUES (2, 'Employee Referral', false, 20, true);
INSERT INTO lookup_contact_source VALUES (3, 'External Referral', false, 30, true);
INSERT INTO lookup_contact_source VALUES (4, 'Partner', false, 40, true);
INSERT INTO lookup_contact_source VALUES (5, 'Public Relations', false, 50, true);
INSERT INTO lookup_contact_source VALUES (6, 'Trade Show', false, 60, true);
INSERT INTO lookup_contact_source VALUES (7, 'Web', false, 70, true);
INSERT INTO lookup_contact_source VALUES (8, 'Word of Mouth', false, 80, true);
INSERT INTO lookup_contact_source VALUES (9, 'Other', false, 90, true);






INSERT INTO lookup_textmessage_types VALUES (1, 'Business', false, 10, true);
INSERT INTO lookup_textmessage_types VALUES (2, 'Personal', false, 20, true);
INSERT INTO lookup_textmessage_types VALUES (3, 'Other', false, 30, true);


-- lookup_access_types ?? --
INSERT INTO permission_category VALUES (22, 'Leads', NULL, 400, true, true, false, true, false, false, false, false, false, false, false, false, 228051100);
INSERT INTO permission_category VALUES (23, 'Documents', NULL, 1600, true, true, false, false, false, false, false, false, false, false, true, false, 1202041528);

UPDATE permission_category SET enabled = true, active = true WHERE category = 'Projects';

UPDATE permission SET description = 'Product Catalog Editor' where permission = 'admin-sysconfig-products';

INSERT INTO permission VALUES (95, 1, 'accounts-accounts-contacts-move', true, false, false, false, 'Move contacts to other accounts', 90, true, true, false);
INSERT INTO permission VALUES (96, 1, 'accounts-accounts-relationships', true, true, true, true, 'Relationships', 290, true, true, false);
INSERT INTO permission VALUES (97, 1, 'accounts-accounts-contact-updater', true, false, false, false, 'Request contact information update', 300, true, true, false);
INSERT INTO permission VALUES (98, 1, 'accounts-projects', true, false, false, false, 'Projects', 310, true, true, false);
INSERT INTO permission VALUES (99, 2, 'contacts-external-contact-updater', true, false, false, false, 'Request contact information update', 90, true, true, false);
INSERT INTO permission VALUES (100, 9, 'admin-sysconfig-logos', true, true, true, true, 'Configure Module Logos', 110, true, true, false);
INSERT INTO permission VALUES (101, 22, 'sales', true, false, false, false, 'Access to Leads Module', 10, true, true, false);
INSERT INTO permission VALUES (102, 22, 'sales-leads', true, true, true, true, 'Lead Records', 20, true, true, false);
INSERT INTO permission VALUES (103, 22, 'sales-import', true, false, false, false, 'Access to Import Leads', 30, true, true, false);
INSERT INTO permission VALUES (104, 19, 'quotes-quotes', true, true, true, true, 'Quote Records', 20, true, true, false);
INSERT INTO permission VALUES (105, 20, 'orders', true, false, false, false, 'Access to Orders module', 10, true, true, false);
INSERT INTO permission VALUES (106, 20, 'orders-orders', true, true, true, true, 'Order Records', 20, true, true, false);
INSERT INTO permission VALUES (107, 21, 'contacts-internal_contacts-folders', true, true, true, true, 'Folders', 30, true, true, false);
INSERT INTO permission VALUES (108, 21, 'contacts-internal_contacts-projects', true, false, false, false, 'Projects', 40, true, true, false);
INSERT INTO permission VALUES (109, 23, 'documents', true, false, false, false, 'Access to Documents module', 10, true, true, false);
INSERT INTO permission VALUES (110, 23, 'documents_documentstore', true, true, true, true, 'Manage Document Stores', 20, true, true, false);

UPDATE permission SET permission_add = false, permission_edit = false, permission_delete = false, description = 'Access to Quotes module' where permission = 'quotes';
UPDATE permission SET enabled = true, active = true WHERE permission = 'accounts-quotes';


INSERT INTO lookup_delivery_options VALUES (7, 'Broadcast', false, 70, true);
INSERT INTO lookup_delivery_options VALUES (8, 'Instant Message', false, 80, false);
INSERT INTO lookup_delivery_options VALUES (9, 'Secure Socket', false, 90, false);


INSERT INTO lookup_lists_lookup VALUES (25, 22, 228051102, 'lookupList', 'lookup_contact_rating', 10, 'Contact Rating', '2005-03-16 11:27:45.046', 228051100);
INSERT INTO lookup_lists_lookup VALUES (26, 22, 228051103, 'lookupList', 'lookup_contact_source', 20, 'Contact Source', '2005-03-16 11:27:45.056', 228051100);
INSERT INTO lookup_lists_lookup VALUES (27, 17, 1017040901, 'lookupList', 'lookup_product_type', 10, 'Product Types', '2005-03-16 11:27:45.142', 330041409);
INSERT INTO lookup_lists_lookup VALUES (28, 17, 1017040902, 'lookupList', 'lookup_product_format', 20, 'Product Format Types', '2005-03-16 11:27:45.15', 330041409);
INSERT INTO lookup_lists_lookup VALUES (29, 17, 1017040903, 'lookupList', 'lookup_product_shipping', 30, 'Product Shipping Types', '2005-03-16 11:27:45.162', 330041409);
INSERT INTO lookup_lists_lookup VALUES (30, 17, 1017040904, 'lookupList', 'lookup_product_ship_time', 40, 'Product Shipping Times', '2005-03-16 11:27:45.171', 330041409);
INSERT INTO lookup_lists_lookup VALUES (31, 17, 1017040905, 'lookupList', 'lookup_product_category_type', 50, 'Product Category Types', '2005-03-16 11:27:45.19', 330041409);
INSERT INTO lookup_lists_lookup VALUES (32, 17, 1017040906, 'lookupList', 'lookup_product_tax', 60, 'Product Tax Types', '2005-03-16 11:27:45.202', 330041409);
INSERT INTO lookup_lists_lookup VALUES (33, 17, 1017040907, 'lookupList', 'lookup_currency', 70, 'Currency Types', '2005-03-16 11:27:45.212', 330041409);
INSERT INTO lookup_lists_lookup VALUES (34, 17, 1017040908, 'lookupList', 'lookup_recurring_type', 80, 'Price Recurring Types', '2005-03-16 11:27:45.223', 330041409);
INSERT INTO lookup_lists_lookup VALUES (35, 17, 1017040909, 'lookupList', 'lookup_product_manufacturer', 90, 'Product Manufacturer Types', '2005-03-16 11:27:45.237', 330041409);
INSERT INTO lookup_lists_lookup VALUES (36, 19, 1123041000, 'lookupList', 'lookup_quote_status', 10, 'Quote Status', '2005-03-16 11:27:45.342', 420041017);
--INSERT INTO lookup_lists_lookup VALUES (37, 19, 1123041001, 'lookupList', 'lookup_quote_type', 20, 'Quote Types', '2005-03-16 11:27:45.352', 420041017);
--INSERT INTO lookup_lists_lookup VALUES (38, 19, 1123041002, 'lookupList', 'lookup_quote_terms', 30, 'Quote Terms', '2005-03-16 11:27:45.366', 420041017);
INSERT INTO lookup_lists_lookup VALUES (39, 19, 1123041003, 'lookupList', 'lookup_quote_source', 40, 'Quote Source', '2005-03-16 11:27:45.373', 420041017);
INSERT INTO lookup_lists_lookup VALUES (40, 19, 1123041004, 'lookupList', 'lookup_quote_delivery', 50, 'Quote Delivery', '2005-03-16 11:27:45.393', 420041017);
INSERT INTO lookup_lists_lookup VALUES (41, 19, 1123041005, 'lookupList', 'lookup_quote_condition', 60, 'Quote Terms & Conditions', '2005-03-16 11:27:45.408', 420041017);
INSERT INTO lookup_lists_lookup VALUES (42, 19, 1123041006, 'lookupList', 'lookup_quote_remarks', 70, 'Quote Remarks', '2005-03-16 11:27:45.419', 420041017);
INSERT INTO lookup_lists_lookup VALUES (43, 1, 302051030, 'lookupList', 'lookup_industry', 70, 'Industry Types', '2005-03-16 11:27:42.354', 1);
INSERT INTO lookup_lists_lookup VALUES (44, 2, 111051354, 'lookupList', 'lookup_textmessage_types', 50, 'Text Messaging Types', '2005-03-16 11:27:42.354', 2);


INSERT INTO webdav VALUES (1, 1, 'org.aspcfs.modules.accounts.webdav.AccountsWebdavContext', '2005-03-16 11:27:42.489', 0, '2005-03-16 11:27:42.489', 0);
INSERT INTO webdav VALUES (2, 7, 'com.zeroio.iteam.webdav.ProjectsWebdavContext', '2005-03-16 11:27:43.541', 0, '2005-03-16 11:27:43.541', 0);
INSERT INTO webdav VALUES (3, 23, 'org.aspcfs.modules.documents.webdav.DocumentsWebdavContext', '2005-03-16 11:27:45.677', 0, '2005-03-16 11:27:45.677', 0);


INSERT INTO lookup_relationship_types VALUES (1, 42420034, 42420034, 'Subsidiary of', 'Parent of', 10, false, true);
INSERT INTO lookup_relationship_types VALUES (2, 42420034, 42420034, 'Customer of', 'Supplier to', 20, false, true);
INSERT INTO lookup_relationship_types VALUES (3, 42420034, 42420034, 'Partner of', 'Partner of', 30, false, true);
INSERT INTO lookup_relationship_types VALUES (4, 42420034, 42420034, 'Competitor of', 'Competitor of', 40, false, true);
INSERT INTO lookup_relationship_types VALUES (5, 42420034, 42420034, 'Employee of', 'Employer of', 50, false, true);
INSERT INTO lookup_relationship_types VALUES (6, 42420034, 42420034, 'Department of', 'Organization made up of', 60, false, true);
INSERT INTO lookup_relationship_types VALUES (7, 42420034, 42420034, 'Group of', 'Organization made up of', 70, false, true);
INSERT INTO lookup_relationship_types VALUES (8, 42420034, 42420034, 'Member of', 'Organization made up of', 80, false, true);
INSERT INTO lookup_relationship_types VALUES (9, 42420034, 42420034, 'Consultant to', 'Consultant of', 90, false, true);
INSERT INTO lookup_relationship_types VALUES (10, 42420034, 42420034, 'Influencer of', 'Influenced by', 100, false, true);
INSERT INTO lookup_relationship_types VALUES (11, 42420034, 42420034, 'Enemy of', 'Enemy of', 110, false, true);
INSERT INTO lookup_relationship_types VALUES (12, 42420034, 42420034, 'Proponent of', 'Endorsed by', 120, false, true);
INSERT INTO lookup_relationship_types VALUES (13, 42420034, 42420034, 'Ally of', 'Ally of', 130, false, true);
INSERT INTO lookup_relationship_types VALUES (14, 42420034, 42420034, 'Sponsor of', 'Sponsored by', 140, false, true);
INSERT INTO lookup_relationship_types VALUES (15, 42420034, 42420034, 'Relative of', 'Relative of', 150, false, true);
INSERT INTO lookup_relationship_types VALUES (16, 42420034, 42420034, 'Affiliated with', 'Affiliated with', 160, false, true);
INSERT INTO lookup_relationship_types VALUES (17, 42420034, 42420034, 'Teammate of', 'Teammate of', 170, false, true);
INSERT INTO lookup_relationship_types VALUES (18, 42420034, 42420034, 'Financier of', 'Financed by', 180, false, true);

-- lookup_call_priority --
--NOTE: levels changed


-- lookup_project_activity --
--NOTE: Values no longer in DB insert

-- lookup_project_priority --
--NOTE: Levels again...

-- lookup_project_status --
--NOTE: verify records

-- lookup_project_loe --
--NOTE: verify records

INSERT INTO lookup_project_permission_category VALUES (10, 'Accounts', false, 85, true, 1);

INSERT INTO lookup_project_permission VALUES (52, 10, 'project-accounts-view', 'View account links', false, 10, true, 1, 3);
INSERT INTO lookup_project_permission VALUES (53, 10, 'project-accounts-manage', 'Manage account links', false, 20, true, 1, 1);

INSERT INTO lookup_product_format VALUES (1, 'Physical', false, 10, true);
INSERT INTO lookup_product_format VALUES (2, 'Electronic', false, 20, true);



INSERT INTO lookup_product_shipping VALUES (1, 'DHL', false, 10, true);
INSERT INTO lookup_product_shipping VALUES (2, 'FEDEX', false, 20, true);
INSERT INTO lookup_product_shipping VALUES (3, 'UPS', false, 30, true);
INSERT INTO lookup_product_shipping VALUES (4, 'USPS', false, 40, true);



INSERT INTO lookup_product_ship_time VALUES (1, '24 Hours', false, 10, true);
INSERT INTO lookup_product_ship_time VALUES (2, '2 Days', false, 20, true);
INSERT INTO lookup_product_ship_time VALUES (3, '1 Week', false, 30, true);



INSERT INTO lookup_product_tax VALUES (1, 'State Tax', false, 10, true);
INSERT INTO lookup_product_tax VALUES (2, 'Sales Tax', false, 20, true);



INSERT INTO lookup_recurring_type VALUES (1, 'Weekly', false, 10, true);
INSERT INTO lookup_recurring_type VALUES (2, 'Monthly', false, 20, true);
INSERT INTO lookup_recurring_type VALUES (3, 'Yearly', false, 30, true);


















INSERT INTO lookup_product_conf_result VALUES (1, 'integer', false, 10, true);
INSERT INTO lookup_product_conf_result VALUES (2, 'float', false, 20, true);
INSERT INTO lookup_product_conf_result VALUES (3, 'boolean', false, 30, true);
INSERT INTO lookup_product_conf_result VALUES (4, 'timestamp', false, 40, true);
INSERT INTO lookup_product_conf_result VALUES (5, 'string', false, 50, true);



INSERT INTO product_option_configurator VALUES (1, 'A text field for free-form additional information', 'String Configurator', 'org.aspcfs.modules.products.configurator.StringConfigurator', 1, 'Text');
INSERT INTO product_option_configurator VALUES (2, 'A check box for yes/no information', 'Checkbox Configurator', 'org.aspcfs.modules.products.configurator.CheckboxConfigurator', 1, 'Check Box');
INSERT INTO product_option_configurator VALUES (3, 'A list of available choices that can be selected', 'LookupList Configurator', 'org.aspcfs.modules.products.configurator.LookupListConfigurator', 1, 'Lookup List');
INSERT INTO product_option_configurator VALUES (4, 'An input field allowing numbers only', 'Numerical Configurator', 'org.aspcfs.modules.products.configurator.NumericalConfigurator', 1, 'Number');

-- NOTE: verify ticket_level values --
-- NOTE: verify ticket_severity --

-- NOTE: Need to insert ticket_category --
-- NOTE: Need to insert asset_category --

INSERT INTO lookup_quote_type VALUES (1, 'New', false, 10, true);
INSERT INTO lookup_quote_type VALUES (2, 'Change', false, 20, true);
INSERT INTO lookup_quote_type VALUES (3, 'Upgrade/Downgrade', false, 30, true);
INSERT INTO lookup_quote_type VALUES (4, 'Disconnect', false, 40, true);
INSERT INTO lookup_quote_type VALUES (5, 'Move', false, 50, true);


INSERT INTO lookup_quote_source VALUES (1, 'Email', false, 10, true);
INSERT INTO lookup_quote_source VALUES (2, 'Fax', false, 20, true);
INSERT INTO lookup_quote_source VALUES (3, 'Incoming Phone Call', false, 30, true);
INSERT INTO lookup_quote_source VALUES (4, 'Outgoing Phone Call', false, 40, true);
INSERT INTO lookup_quote_source VALUES (5, 'In Person', false, 50, true);
INSERT INTO lookup_quote_source VALUES (6, 'Mail', false, 60, true);
INSERT INTO lookup_quote_source VALUES (7, 'Agent Request', false, 70, true);

INSERT INTO lookup_payment_status VALUES (1, 'Pending', false, 10, true);
INSERT INTO lookup_payment_status VALUES (2, 'In Progress', false, 20, true);
INSERT INTO lookup_payment_status VALUES (3, 'Approved', false, 30, true);
INSERT INTO lookup_payment_status VALUES (4, 'Declined', false, 40, true);

INSERT INTO survey (name, type, enabled, enteredby, modifiedby) VALUES ('Address Update Request', 2, true, 0, 0);

-- verify task_priority --
-- verify lookup_task_loe --

INSERT INTO lookup_document_store_permission_category VALUES (1, 'Document Store Details', false, 10, true, 0);
INSERT INTO lookup_document_store_permission_category VALUES (2, 'Team Members', false, 20, true, 0);
INSERT INTO lookup_document_store_permission_category VALUES (3, 'Document Library', false, 30, true, 0);
INSERT INTO lookup_document_store_permission_category VALUES (4, 'Setup', false, 40, true, 0);



INSERT INTO lookup_document_store_role VALUES (1, 'Manager', false, 1, true, 1);
INSERT INTO lookup_document_store_role VALUES (2, 'Contributor level 3', false, 2, true, 1);
INSERT INTO lookup_document_store_role VALUES (3, 'Contributor level 2', false, 3, true, 1);
INSERT INTO lookup_document_store_role VALUES (4, 'Contributor level 1', false, 4, true, 1);
INSERT INTO lookup_document_store_role VALUES (5, 'Guest', false, 5, true, 1);



INSERT INTO lookup_document_store_permission VALUES (1, 1, 'documentcenter-details-view', 'View document store details', false, 10, true, 1, 5);
INSERT INTO lookup_document_store_permission VALUES (2, 1, 'documentcenter-details-edit', 'Modify document store details', false, 20, true, 1, 1);
INSERT INTO lookup_document_store_permission VALUES (3, 1, 'documentcenter-details-delete', 'Delete document store', false, 30, true, 1, 1);
INSERT INTO lookup_document_store_permission VALUES (4, 2, 'documentcenter-team-view', 'View team members', false, 40, true, 1, 5);
INSERT INTO lookup_document_store_permission VALUES (5, 2, 'documentcenter-team-view-email', 'See team member email addresses', false, 50, true, 1, 4);
INSERT INTO lookup_document_store_permission VALUES (6, 2, 'documentcenter-team-edit', 'Modify team', false, 60, true, 1, 1);
INSERT INTO lookup_document_store_permission VALUES (7, 2, 'documentcenter-team-edit-role', 'Modify team member role', false, 70, true, 1, 1);
INSERT INTO lookup_document_store_permission VALUES (8, 3, 'documentcenter-documents-view', 'View documents', false, 80, true, 1, 5);
INSERT INTO lookup_document_store_permission VALUES (9, 3, 'documentcenter-documents-folders-add', 'Create folders', false, 90, true, 1, 2);
INSERT INTO lookup_document_store_permission VALUES (10, 3, 'documentcenter-documents-folders-edit', 'Modify folders', false, 100, true, 1, 3);
INSERT INTO lookup_document_store_permission VALUES (11, 3, 'documentcenter-documents-folders-delete', 'Delete folders', false, 110, true, 1, 2);
INSERT INTO lookup_document_store_permission VALUES (12, 3, 'documentcenter-documents-files-upload', 'Upload files', false, 120, true, 1, 4);
INSERT INTO lookup_document_store_permission VALUES (13, 3, 'documentcenter-documents-files-download', 'Download files', false, 130, true, 1, 5);
INSERT INTO lookup_document_store_permission VALUES (14, 3, 'documentcenter-documents-files-rename', 'Rename files', false, 140, true, 1, 3);
INSERT INTO lookup_document_store_permission VALUES (15, 3, 'documentcenter-documents-files-delete', 'View document store details', false, 150, true, 1, 2);
INSERT INTO lookup_document_store_permission VALUES (16, 4, 'documentcenter-setup-permissions', 'Configure document store permissions', false, 160, true, 1, 1);

INSERT INTO lookup_quote_delivery VALUES (1, 'Email', false, 10, true);
INSERT INTO lookup_quote_delivery VALUES (2, 'Fax', false, 20, true);
INSERT INTO lookup_quote_delivery VALUES (3, 'In Person', false, 30, true);
INSERT INTO lookup_quote_delivery VALUES (4, 'DHL', false, 40, true);
INSERT INTO lookup_quote_delivery VALUES (5, 'FEDEX', false, 50, true);
INSERT INTO lookup_quote_delivery VALUES (6, 'UPS', false, 60, true);
INSERT INTO lookup_quote_delivery VALUES (7, 'USPS', false, 70, true);

INSERT INTO module_field_categorylink VALUES (5, 21, 120200513, 10, 'Employees', '2004-08-31 09:16:15.500');




-- UPDATES --

UPDATE permission_category
SET constant=13, level=100
WHERE category='System';

UPDATE permission_category
SET constant=14, level=200
WHERE category='My Home Page';

UPDATE permission_category
SET constant=420041014, level=300
WHERE category='Products and Services';

UPDATE permission_category
SET constant=228051100, level=400
WHERE category='Leads';

UPDATE permission_category
SET constant=2, level=500
WHERE category='Contacts';

UPDATE permission_category
SET constant=4, level=600
WHERE category='Pipeline';

UPDATE permission_category
SET constant=1, level=700
WHERE category='Accounts';

UPDATE permission_category
SET constant=3, level=800
WHERE category='Auto Guide';

UPDATE permission_category
SET constant=420041017, level=900
WHERE category='Quotes';

UPDATE permission_category
SET constant=420041018, level=1000
WHERE category='Orders';

UPDATE permission_category
SET constant=6, level=1100
WHERE category='Communications';

UPDATE permission_category
SET constant=7, level=1200
WHERE category='Projects';

UPDATE permission_category
SET constant=130041100, level=1300
WHERE category='Service Contracts';

UPDATE permission_category
SET constant=130041000, level=1400
WHERE category='Assets';

UPDATE permission_category
SET constant=8, level=1500
WHERE category='Help Desk';

UPDATE permission_category
SET constant=1202041528, level=1600
WHERE category='Documents';

UPDATE permission_category
SET constant=1111031131, level=1700
WHERE category='Employees';

UPDATE permission_category
SET constant=330041409, level=1800
WHERE category='Product Catalog';

UPDATE permission_category
SET constant=16, level=1900
WHERE category='Reports';

UPDATE permission_category
SET constant=9, level=2000
WHERE category='Admin';

UPDATE permission_category
SET constant=10, level=2100
WHERE category='Help';

UPDATE permission_category
SET constant=15, level=2200
WHERE category='QA';

UPDATE permission_category
SET constant=5, level=2300
WHERE category='Demo';

UPDATE permission_category SET webdav = true WHERE constant = 1;
UPDATE permission_category SET webdav = true WHERE constant = 7;
UPDATE permission_category SET lookups = true WHERE constant = 330041409;
UPDATE permission_category SET enabled = true WHERE constant = 420041017;
UPDATE permission_category SET active = true WHERE constant = 420041017;
UPDATE permission_category SET lookups = true WHERE constant = 420041017;
UPDATE permission_category SET logos = true WHERE constant = 420041017;
UPDATE permission_category SET folders = true WHERE constant = 1111031131;

DELETE FROM contact_type_levels WHERE contact_id IN (SELECT contact_id FROM contact
WHERE org_id = 0);

UPDATE contact SET employee = true WHERE org_id = 0 AND employee = false;


-- INDEX --

CREATE INDEX proj_assign_req_id_idx ON project_assignments USING btree (requirement_id);

CREATE INDEX proj_acct_project_idx ON project_accounts USING btree (project_id);

CREATE INDEX proj_acct_org_idx ON project_accounts USING btree (org_id);

DROP INDEX idx_pr_opt_val;
CREATE UNIQUE INDEX idx_pr_opt_val ON product_option_values USING btree (value_id, option_id, result_id);

-- CONSTRAINTS --
ALTER TABLE ONLY lookup_im_types
    ADD CONSTRAINT lookup_im_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_im_services
    ADD CONSTRAINT lookup_im_services_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_contact_source
    ADD CONSTRAINT lookup_contact_source_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_contact_rating
    ADD CONSTRAINT lookup_contact_rating_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_textmessage_types
    ADD CONSTRAINT lookup_textmessage_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY contact_lead_skipped_map
    ADD CONSTRAINT contact_lead_skipped_map_pkey PRIMARY KEY (map_id);



ALTER TABLE ONLY contact_lead_read_map
    ADD CONSTRAINT contact_lead_read_map_pkey PRIMARY KEY (map_id);



ALTER TABLE ONLY contact_imaddress
    ADD CONSTRAINT contact_imaddress_pkey PRIMARY KEY (address_id);



ALTER TABLE ONLY contact_textmessageaddress
    ADD CONSTRAINT contact_textmessageaddress_pkey PRIMARY KEY (address_id);



ALTER TABLE ONLY webdav
    ADD CONSTRAINT webdav_pkey PRIMARY KEY (id);



ALTER TABLE ONLY lookup_relationship_types
    ADD CONSTRAINT lookup_relationship_types_pkey PRIMARY KEY (type_id);



ALTER TABLE ONLY relationship
    ADD CONSTRAINT relationship_pkey PRIMARY KEY (relationship_id);



ALTER TABLE ONLY lookup_news_template
    ADD CONSTRAINT lookup_news_template_pkey PRIMARY KEY (code);



ALTER TABLE ONLY project_news_category
    ADD CONSTRAINT project_news_category_pkey PRIMARY KEY (category_id);



ALTER TABLE ONLY project_accounts
    ADD CONSTRAINT project_accounts_pkey PRIMARY KEY (id);



ALTER TABLE ONLY lookup_product_manufacturer
    ADD CONSTRAINT lookup_product_manufacturer_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_ticket_status
    ADD CONSTRAINT lookup_ticket_status_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_ticket_status
    ADD CONSTRAINT lookup_ticket_status_description_key UNIQUE (description);



ALTER TABLE ONLY lookup_payment_status
    ADD CONSTRAINT lookup_payment_status_pkey PRIMARY KEY (code);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT order_payment_pkey PRIMARY KEY (payment_id);



ALTER TABLE ONLY order_payment_status
    ADD CONSTRAINT order_payment_status_pkey PRIMARY KEY (payment_status_id);



ALTER TABLE ONLY lookup_document_store_permission_category
    ADD CONSTRAINT lookup_document_store_permission_category_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_document_store_role
    ADD CONSTRAINT lookup_document_store_role_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_document_store_permission
    ADD CONSTRAINT lookup_document_store_permission_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_document_store_permission
    ADD CONSTRAINT lookup_document_store_permission_permission_key UNIQUE (permission);



ALTER TABLE ONLY document_store
    ADD CONSTRAINT document_store_pkey PRIMARY KEY (document_store_id);



ALTER TABLE ONLY document_store_permissions
    ADD CONSTRAINT document_store_permissions_pkey PRIMARY KEY (id);



ALTER TABLE ONLY lookup_quote_delivery
    ADD CONSTRAINT lookup_quote_delivery_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_quote_condition
    ADD CONSTRAINT lookup_quote_condition_pkey PRIMARY KEY (code);



ALTER TABLE ONLY quote_group
    ADD CONSTRAINT quote_group_pkey PRIMARY KEY (group_id);



ALTER TABLE ONLY quote_condition
    ADD CONSTRAINT quote_condition_pkey PRIMARY KEY (map_id);



ALTER TABLE ONLY quotelog
    ADD CONSTRAINT quotelog_pkey PRIMARY KEY (id);



ALTER TABLE ONLY lookup_quote_remarks
    ADD CONSTRAINT lookup_quote_remarks_pkey PRIMARY KEY (code);



ALTER TABLE ONLY quote_remark
    ADD CONSTRAINT quote_remark_pkey PRIMARY KEY (map_id);



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$10" FOREIGN KEY (source) REFERENCES lookup_contact_source(code);



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$11" FOREIGN KEY (rating) REFERENCES lookup_contact_rating(code);



ALTER TABLE ONLY contact_lead_skipped_map
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact_lead_skipped_map
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY contact_lead_read_map
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact_lead_read_map
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY contact_imaddress
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY contact_imaddress
    ADD CONSTRAINT "$2" FOREIGN KEY (imaddress_type) REFERENCES lookup_im_types(code);



ALTER TABLE ONLY contact_imaddress
    ADD CONSTRAINT "$3" FOREIGN KEY (imaddress_service) REFERENCES lookup_im_services(code);



ALTER TABLE ONLY contact_imaddress
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact_imaddress
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact_textmessageaddress
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY contact_textmessageaddress
    ADD CONSTRAINT "$2" FOREIGN KEY (textmessageaddress_type) REFERENCES lookup_im_types(code);



ALTER TABLE ONLY contact_textmessageaddress
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact_textmessageaddress
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY webdav
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id);



ALTER TABLE ONLY webdav
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY webdav
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY relationship
    ADD CONSTRAINT "$1" FOREIGN KEY (type_id) REFERENCES lookup_relationship_types(type_id);



ALTER TABLE ONLY project_assignments_status
    ADD CONSTRAINT "$3" FOREIGN KEY (project_status_id) REFERENCES lookup_project_status(code);



ALTER TABLE ONLY project_assignments_status
    ADD CONSTRAINT "$4" FOREIGN KEY (user_assign_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_news_category
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY project_news
    ADD CONSTRAINT "$4" FOREIGN KEY (category_id) REFERENCES project_news_category(category_id);



ALTER TABLE ONLY project_news
    ADD CONSTRAINT "$5" FOREIGN KEY (template_id) REFERENCES lookup_news_template(code);



ALTER TABLE ONLY project_accounts
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY project_accounts
    ADD CONSTRAINT "$2" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$11" FOREIGN KEY (manufacturer_id) REFERENCES lookup_product_manufacturer(code);



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$9" FOREIGN KEY (cost_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT "$6" FOREIGN KEY (cost_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$11" FOREIGN KEY (opp_id) REFERENCES opportunity_header(opp_id);



ALTER TABLE ONLY payment_creditcard
    ADD CONSTRAINT "$5" FOREIGN KEY (order_id) REFERENCES order_entry(order_id);



ALTER TABLE ONLY payment_eft
    ADD CONSTRAINT "$4" FOREIGN KEY (order_id) REFERENCES order_entry(order_id);



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT "$6" FOREIGN KEY (order_item_id) REFERENCES order_product(item_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$1" FOREIGN KEY (order_id) REFERENCES order_entry(order_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$2" FOREIGN KEY (order_item_id) REFERENCES order_product(item_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$3" FOREIGN KEY (history_id) REFERENCES customer_product_history(history_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$4" FOREIGN KEY (payment_method_id) REFERENCES lookup_payment_methods(code);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$7" FOREIGN KEY (creditcard_id) REFERENCES payment_creditcard(creditcard_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$8" FOREIGN KEY (bank_id) REFERENCES payment_eft(bank_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$9" FOREIGN KEY (status_id) REFERENCES lookup_payment_status(code);



ALTER TABLE ONLY order_payment_status
    ADD CONSTRAINT "$1" FOREIGN KEY (payment_id) REFERENCES order_payment(payment_id);



ALTER TABLE ONLY order_payment_status
    ADD CONSTRAINT "$2" FOREIGN KEY (status_id) REFERENCES lookup_payment_status(code);



ALTER TABLE ONLY order_payment_status
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY order_payment_status
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY taskcategorylink_news
    ADD CONSTRAINT "$1" FOREIGN KEY (news_id) REFERENCES project_news(news_id);



ALTER TABLE ONLY taskcategorylink_news
    ADD CONSTRAINT "$2" FOREIGN KEY (category_id) REFERENCES lookup_task_category(code);



ALTER TABLE ONLY lookup_document_store_permission
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES lookup_document_store_permission_category(code);



ALTER TABLE ONLY lookup_document_store_permission
    ADD CONSTRAINT "$2" FOREIGN KEY (default_role) REFERENCES lookup_document_store_role(code);



ALTER TABLE ONLY document_store
    ADD CONSTRAINT "$1" FOREIGN KEY (approvalby) REFERENCES "access"(user_id);



ALTER TABLE ONLY document_store
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY document_store
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY document_store_permissions
    ADD CONSTRAINT "$1" FOREIGN KEY (document_store_id) REFERENCES document_store(document_store_id);



ALTER TABLE ONLY document_store_permissions
    ADD CONSTRAINT "$2" FOREIGN KEY (permission_id) REFERENCES lookup_document_store_permission(code);



ALTER TABLE ONLY document_store_permissions
    ADD CONSTRAINT "$3" FOREIGN KEY (userlevel) REFERENCES lookup_document_store_role(code);



ALTER TABLE ONLY document_store_user_member
    ADD CONSTRAINT "$1" FOREIGN KEY (document_store_id) REFERENCES document_store(document_store_id);



ALTER TABLE ONLY document_store_user_member
    ADD CONSTRAINT "$2" FOREIGN KEY (item_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY document_store_user_member
    ADD CONSTRAINT "$3" FOREIGN KEY (userlevel) REFERENCES lookup_document_store_role(code);



ALTER TABLE ONLY document_store_user_member
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY document_store_user_member
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY document_store_role_member
    ADD CONSTRAINT "$1" FOREIGN KEY (document_store_id) REFERENCES document_store(document_store_id);



ALTER TABLE ONLY document_store_role_member
    ADD CONSTRAINT "$2" FOREIGN KEY (item_id) REFERENCES role(role_id);



ALTER TABLE ONLY document_store_role_member
    ADD CONSTRAINT "$3" FOREIGN KEY (userlevel) REFERENCES lookup_document_store_role(code);



ALTER TABLE ONLY document_store_role_member
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY document_store_role_member
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY document_store_department_member
    ADD CONSTRAINT "$1" FOREIGN KEY (document_store_id) REFERENCES document_store(document_store_id);



ALTER TABLE ONLY document_store_department_member
    ADD CONSTRAINT "$2" FOREIGN KEY (item_id) REFERENCES lookup_department(code);



ALTER TABLE ONLY document_store_department_member
    ADD CONSTRAINT "$3" FOREIGN KEY (userlevel) REFERENCES lookup_document_store_role(code);



ALTER TABLE ONLY document_store_department_member
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY document_store_department_member
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$19" FOREIGN KEY (status_id) REFERENCES lookup_ticket_status(code);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$13" FOREIGN KEY (group_id) REFERENCES quote_group(group_id);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$14" FOREIGN KEY (delivery_id) REFERENCES lookup_quote_delivery(code);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$15" FOREIGN KEY (logo_file_id) REFERENCES project_files(item_id);



ALTER TABLE ONLY quote_condition
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_id) REFERENCES quote_entry(quote_id);



ALTER TABLE ONLY quote_condition
    ADD CONSTRAINT "$2" FOREIGN KEY (condition_id) REFERENCES lookup_quote_condition(code);



ALTER TABLE ONLY quotelog
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_id) REFERENCES quote_entry(quote_id);



ALTER TABLE ONLY quotelog
    ADD CONSTRAINT "$2" FOREIGN KEY (source_id) REFERENCES lookup_quote_source(code);



ALTER TABLE ONLY quotelog
    ADD CONSTRAINT "$3" FOREIGN KEY (status_id) REFERENCES lookup_quote_status(code);



ALTER TABLE ONLY quotelog
    ADD CONSTRAINT "$4" FOREIGN KEY (terms_id) REFERENCES lookup_quote_terms(code);



ALTER TABLE ONLY quotelog
    ADD CONSTRAINT "$5" FOREIGN KEY (type_id) REFERENCES lookup_quote_type(code);



ALTER TABLE ONLY quotelog
    ADD CONSTRAINT "$6" FOREIGN KEY (delivery_id) REFERENCES lookup_quote_delivery(code);



ALTER TABLE ONLY quotelog
    ADD CONSTRAINT "$7" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY quotelog
    ADD CONSTRAINT "$8" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY quote_remark
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_id) REFERENCES quote_entry(quote_id);



ALTER TABLE ONLY quote_remark
    ADD CONSTRAINT "$2" FOREIGN KEY (remark_id) REFERENCES lookup_quote_remarks(code);



SELECT pg_catalog.setval('lookup_im_types_code_seq', 3, true);



SELECT pg_catalog.setval('lookup_im_services_code_seq', 3, true);



SELECT pg_catalog.setval('lookup_contact_source_code_seq', 9, true);



SELECT pg_catalog.setval('lookup_contact_rating_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_textmessage_typ_code_seq', 3, true);



SELECT pg_catalog.setval('contact_lead_skipped_map_map_id_seq', 1, false);



SELECT pg_catalog.setval('contact_lead_read_map_map_id_seq', 1, false);



SELECT pg_catalog.setval('contact_imaddress_address_id_seq', 1, false);



SELECT pg_catalog.setval('contact_textmessageaddress_address_id_seq', 1, false);



SELECT pg_catalog.setval('webdav_id_seq', 3, true);



SELECT pg_catalog.setval('lookup_relationship_types_type_id_seq', 18, true);



SELECT pg_catalog.setval('relationship_relationship_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_news_template_code_seq', 1, false);



SELECT pg_catalog.setval('project_news_category_category_id_seq', 1, false);



SELECT pg_catalog.setval('project_accounts_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_product_manufac_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_ticket_status_code_seq', 1, false);


SELECT pg_catalog.setval('lookup_payment_status_code_seq', 4, true);



SELECT pg_catalog.setval('order_payment_payment_id_seq', 1, false);



SELECT pg_catalog.setval('order_payment_status_payment_status_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_document_store_permission_category_code_seq', 4, true);



SELECT pg_catalog.setval('lookup_document_store_role_code_seq', 5, true);



SELECT pg_catalog.setval('lookup_document_store_permission_code_seq', 16, true);



SELECT pg_catalog.setval('document_store_document_store_id_seq', 1, false);



SELECT pg_catalog.setval('document_store_permissions_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_quote_delivery_code_seq', 7, true);



SELECT pg_catalog.setval('lookup_quote_condition_code_seq', 1, false);



SELECT pg_catalog.setval('quote_group_group_id_seq', 1000, false);



SELECT pg_catalog.setval('quote_condition_map_id_seq', 1, false);



SELECT pg_catalog.setval('quotelog_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_quote_remarks_code_seq', 1, false);



SELECT pg_catalog.setval('quote_remark_map_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_project_permission_category_code_seq', 10, false);
SELECT pg_catalog.setval('lookup_project_permission_code_seq', 53, false);
SELECT pg_catalog.setval('lookup_lists_lookup_id_seq', 44, false);
SELECT pg_catalog.setval('permission_cate_category_id_seq', 23, false);
SELECT pg_catalog.setval('permission_permission_id_seq', 110, false);
SELECT pg_catalog.setval('lookup_product_conf_re_code_seq', 5, true);
SELECT pg_catalog.setval('lookup_quote_source_code_seq', 7, true);
SELECT pg_catalog.setval('lookup_quote_type_code_seq', 5, true);
SELECT pg_catalog.setval('lookup_recurring_type_code_seq', 3, true);
SELECT pg_catalog.setval('product_option_configurator_configurator_id_seq', 4, true);
SELECT pg_catalog.setval('module_field_categorylin_id_seq', 5, true);


INSERT INTO database_version (script_filename, script_version) VALUES ('postgresql.sql', '2005-03-30');
