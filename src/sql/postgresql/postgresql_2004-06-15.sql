-- Script (C) 2004 Dark Horse Ventures, all rights reserved
-- Database upgrade v2.8 (2004-06-15)

ALTER TABLE organization ADD COLUMN import_id integer;
ALTER TABLE organization ADD COLUMN status_id integer;

ALTER TABLE contact ADD COLUMN status_id integer;
ALTER TABLE contact ADD COLUMN import_id integer;

ALTER TABLE role ADD COLUMN role_type integer;
UPDATE role SET role_type = 0 WHERE role_type IS NULL;

ALTER TABLE permission_category ADD COLUMN products boolean;
ALTER TABLE permission_category ALTER products SET DEFAULT false;
UPDATE permission_category SET products = false WHERE products IS NULL;
ALTER TABLE permission_category ADD CONSTRAINT products_not_null CHECK(products IS NOT NULL) ;

ALTER TABLE contact_emailaddress ADD COLUMN primary_email boolean;
ALTER TABLE contact_emailaddress ALTER primary_email SET DEFAULT false;
UPDATE contact_emailaddress SET primary_email = false WHERE primary_email IS NULL;
ALTER TABLE contact_emailaddress ADD CONSTRAINT primary_email_not_null CHECK(primary_email IS NOT NULL) ;

CREATE TABLE category_editor_lookup (
    id serial NOT NULL,
    module_id integer NOT NULL,
    constant_id integer NOT NULL,
    table_name character varying(60),
    "level" integer DEFAULT 0,
    description text,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    category_id integer NOT NULL,
    max_levels integer NOT NULL
);

CREATE TABLE import (
    import_id serial NOT NULL,
    "type" integer NOT NULL,
    name character varying(250) NOT NULL,
    description text,
    source_type integer,
    source character varying(1000),
    record_delimiter character varying(10),
    column_delimiter character varying(10),
    total_imported_records integer,
    total_failed_records integer,
    status_id integer,
    file_type integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);


CREATE TABLE lookup_currency (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_product_categor_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE lookup_product_category_type (
    code integer DEFAULT nextval('lookup_product_categor_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE product_category (
    category_id serial NOT NULL,
    parent_id integer,
    category_name character varying(255) NOT NULL,
    abbreviation character varying(30),
    short_description text,
    long_description text,
    type_id integer,
    thumbnail_image_id integer,
    small_image_id integer,
    large_image_id integer,
    list_order integer DEFAULT 10,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    start_date timestamp(3) without time zone,
    expiration_date timestamp(3) without time zone,
    enabled boolean DEFAULT true NOT NULL
);



CREATE TABLE product_category_map (
    id serial NOT NULL,
    category1_id integer NOT NULL,
    category2_id integer NOT NULL
);



CREATE TABLE lookup_product_type (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_product_format (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_product_shippin_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE lookup_product_shipping (
    code integer DEFAULT nextval('lookup_product_shippin_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_product_ship_ti_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE lookup_product_ship_time (
    code integer DEFAULT nextval('lookup_product_ship_ti_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_product_tax (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_recurring_type (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE product_catalog (
    product_id serial NOT NULL,
    parent_id integer,
    product_name character varying(255) NOT NULL,
    abbreviation character varying(30),
    short_description text,
    long_description text,
    type_id integer,
    special_notes character varying(255),
    sku character varying(40),
    in_stock boolean DEFAULT true NOT NULL,
    format_id integer,
    shipping_id integer,
    estimated_ship_time integer,
    thumbnail_image_id integer,
    small_image_id integer,
    large_image_id integer,
    list_order integer DEFAULT 10,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    start_date timestamp(3) without time zone,
    expiration_date timestamp(3) without time zone,
    enabled boolean DEFAULT true NOT NULL
);



CREATE TABLE product_catalog_pricing (
    price_id serial NOT NULL,
    product_id integer,
    tax_id integer,
    msrp_currency integer,
    msrp_amount double precision DEFAULT 0 NOT NULL,
    price_currency integer,
    price_amount double precision DEFAULT 0 NOT NULL,
    recurring_currency integer,
    recurring_amount double precision DEFAULT 0 NOT NULL,
    recurring_type integer,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    start_date timestamp(3) without time zone,
    expiration_date timestamp(3) without time zone
);



CREATE TABLE package (
    package_id serial NOT NULL,
    category_id integer,
    package_name character varying(255) NOT NULL,
    abbreviation character varying(30),
    short_description text,
    long_description text,
    thumbnail_image_id integer,
    small_image_id integer,
    large_image_id integer,
    list_order integer DEFAULT 10,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    start_date timestamp(3) without time zone,
    expiration_date timestamp(3) without time zone,
    enabled boolean DEFAULT true NOT NULL
);



CREATE TABLE package_products_map (
    id serial NOT NULL,
    package_id integer NOT NULL,
    product_id integer NOT NULL,
    description text,
    msrp_currency integer,
    msrp_amount double precision DEFAULT 0 NOT NULL,
    price_currency integer,
    price_amount double precision DEFAULT 0 NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    start_date timestamp(3) without time zone,
    expiration_date timestamp(3) without time zone
);



CREATE TABLE product_catalog_category_map (
    id serial NOT NULL,
    product_id integer NOT NULL,
    category_id integer NOT NULL
);



CREATE SEQUENCE lookup_product_conf_re_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE lookup_product_conf_result (
    code integer DEFAULT nextval('lookup_product_conf_re_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE product_option_configurator (
    configurator_id serial NOT NULL,
    short_description text,
    long_description text,
    class_name character varying(255),
    result_type integer NOT NULL
);



CREATE TABLE product_option (
    option_id serial NOT NULL,
    configurator_id integer NOT NULL,
    parent_id integer,
    short_description text,
    long_description text,
    allow_customer_configure boolean DEFAULT false NOT NULL,
    allow_user_configure boolean DEFAULT false NOT NULL,
    required boolean DEFAULT false NOT NULL,
    start_date timestamp(3) without time zone,
    end_date timestamp(3) without time zone,
    enabled boolean DEFAULT true
);



CREATE TABLE product_option_values (
    value_id serial NOT NULL,
    option_id integer NOT NULL,
    result_id integer NOT NULL,
    description text,
    msrp_currency integer,
    msrp_amount double precision DEFAULT 0 NOT NULL,
    price_currency integer,
    price_amount double precision DEFAULT 0 NOT NULL,
    recurring_currency integer,
    recurring_amount double precision DEFAULT 0 NOT NULL,
    recurring_type integer
);



CREATE TABLE product_option_map (
    product_option_id serial NOT NULL,
    product_id integer NOT NULL,
    option_id integer NOT NULL,
    value_id integer NOT NULL
);



CREATE TABLE product_option_boolean (
    product_option_id integer NOT NULL,
    value boolean NOT NULL
);



CREATE TABLE product_option_float (
    product_option_id integer NOT NULL,
    value double precision NOT NULL
);



CREATE TABLE product_option_timestamp (
    product_option_id integer NOT NULL,
    value timestamp without time zone NOT NULL
);



CREATE TABLE product_option_integer (
    product_option_id integer NOT NULL,
    value integer NOT NULL
);



CREATE TABLE product_option_text (
    product_option_id integer NOT NULL,
    value text NOT NULL
);



CREATE TABLE lookup_product_keyword (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE product_keyword_map (
    product_id integer NOT NULL,
    keyword_id integer NOT NULL
);



CREATE TABLE lookup_asset_status (
    code serial NOT NULL,
    description character varying(300),
    default_item boolean DEFAULT false,
    "level" integer,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_sc_category (
    code serial NOT NULL,
    description character varying(300),
    default_item boolean DEFAULT false,
    "level" integer,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_sc_type (
    code serial NOT NULL,
    description character varying(300),
    default_item boolean DEFAULT false,
    "level" integer,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_response_model (
    code serial NOT NULL,
    description character varying(300),
    default_item boolean DEFAULT false,
    "level" integer,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_phone_model (
    code serial NOT NULL,
    description character varying(300),
    default_item boolean DEFAULT false,
    "level" integer,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_onsite_model (
    code serial NOT NULL,
    description character varying(300),
    default_item boolean DEFAULT false,
    "level" integer,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_email_model (
    code serial NOT NULL,
    description character varying(300),
    default_item boolean DEFAULT false,
    "level" integer,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_hours_reason (
    code serial NOT NULL,
    description character varying(300),
    default_item boolean DEFAULT false,
    "level" integer,
    enabled boolean DEFAULT true
);



CREATE TABLE service_contract (
    contract_id serial NOT NULL,
    contract_number character varying(30),
    account_id integer NOT NULL,
    initial_start_date timestamp(3) without time zone NOT NULL,
    current_start_date timestamp(3) without time zone,
    current_end_date timestamp(3) without time zone,
    category integer,
    "type" integer,
    contact_id integer,
    description text,
    contract_billing_notes text,
    response_time integer,
    telephone_service_model integer,
    onsite_service_model integer,
    email_service_model integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    enabled boolean DEFAULT true,
    contract_value double precision,
    total_hours_remaining double precision
);



CREATE TABLE service_contract_hours (
    history_id serial NOT NULL,
    link_contract_id integer,
    adjustment_hours double precision,
    adjustment_reason integer,
    adjustment_notes text,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE service_contract_products (
    id serial NOT NULL,
    link_contract_id integer,
    link_product_id integer
);



CREATE TABLE asset_category (
    id serial NOT NULL,
    cat_level integer DEFAULT 0 NOT NULL,
    parent_cat_code integer NOT NULL,
    description character varying(300) NOT NULL,
    full_description text DEFAULT '' NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE asset_category_draft (
    id serial NOT NULL,
    link_id integer DEFAULT -1,
    cat_level integer DEFAULT 0 NOT NULL,
    parent_cat_code integer NOT NULL,
    description character varying(300) NOT NULL,
    full_description text DEFAULT '' NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE asset (
    asset_id serial NOT NULL,
    account_id integer,
    contract_id integer,
    date_listed timestamp(3) without time zone,
    asset_tag character varying(30),
    status integer,
    "location" character varying(256),
    level1 integer,
    level2 integer,
    level3 integer,
    vendor character varying(30),
    manufacturer character varying(30),
    serial_number character varying(30),
    model_version character varying(30),
    description text,
    expiration_date timestamp(3) without time zone,
    inclusions text,
    exclusions text,
    purchase_date timestamp(3) without time zone,
    po_number character varying(30),
    purchased_from character varying(30),
    contact_id integer,
    notes text,
    response_time integer,
    telephone_service_model integer,
    onsite_service_model integer,
    email_service_model integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    enabled boolean DEFAULT true,
    purchase_cost double precision
);

ALTER TABLE ticket ADD COLUMN link_contract_id integer;
ALTER TABLE ticket ADD COLUMN link_asset_id integer;
ALTER TABLE ticket ADD COLUMN product_id integer;
ALTER TABLE ticket ADD COLUMN customer_product_id integer;
ALTER TABLE ticket ADD COLUMN expectation integer;


CREATE TABLE ticket_csstm_form (
    form_id serial NOT NULL,
    link_ticket_id integer,
    phone_response_time character varying(10),
    engineer_response_time character varying(10),
    follow_up_required boolean DEFAULT false,
    follow_up_description character varying(2048),
    alert_date timestamp(3) without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    enabled boolean DEFAULT true,
    travel_towards_sc boolean DEFAULT true,
    labor_towards_sc boolean DEFAULT true
);



CREATE TABLE ticket_activity_item (
    item_id serial NOT NULL,
    link_form_id integer,
    activity_date timestamp(3) without time zone,
    description text,
    travel_hours integer,
    travel_minutes integer,
    labor_hours integer,
    labor_minutes integer
);



CREATE TABLE ticket_sun_form (
    form_id serial NOT NULL,
    link_ticket_id integer,
    description_of_service text,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    enabled boolean DEFAULT true
);



CREATE TABLE trouble_asset_replacement (
    replacement_id serial NOT NULL,
    link_form_id integer,
    part_number character varying(256),
    part_description text
);



CREATE TABLE lookup_quote_status (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_quote_type (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_quote_terms (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_quote_source (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE quote_entry (
    quote_id serial NOT NULL,
    parent_id integer,
    org_id integer NOT NULL,
    contact_id integer,
    source_id integer,
    grand_total double precision,
    status_id integer,
    status_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    expiration_date timestamp(3) without time zone,
    quote_terms_id integer,
    quote_type_id integer,
    issued timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    short_description text,
    notes text,
    ticketid integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    product_id integer,
    customer_product_id integer
);



CREATE TABLE quote_product (
    item_id serial NOT NULL,
    quote_id integer NOT NULL,
    product_id integer NOT NULL,
    quantity integer DEFAULT 0 NOT NULL,
    price_currency integer,
    price_amount double precision DEFAULT 0 NOT NULL,
    recurring_currency integer,
    recurring_amount double precision DEFAULT 0 NOT NULL,
    recurring_type integer,
    extended_price double precision DEFAULT 0 NOT NULL,
    total_price double precision DEFAULT 0 NOT NULL,
    estimated_delivery_date timestamp(3) without time zone,
    status_id integer,
    status_date timestamp without time zone DEFAULT ('now'::text)::timestamp(6) with time zone
);



CREATE TABLE quote_product_options (
    quote_product_option_id serial NOT NULL,
    item_id integer NOT NULL,
    product_option_id integer NOT NULL,
    quantity integer DEFAULT 0 NOT NULL,
    price_currency integer,
    price_amount double precision DEFAULT 0 NOT NULL,
    recurring_currency integer,
    recurring_amount double precision DEFAULT 0 NOT NULL,
    recurring_type integer,
    extended_price double precision DEFAULT 0 NOT NULL,
    total_price double precision DEFAULT 0 NOT NULL,
    status_id integer
);



CREATE TABLE quote_product_option_boolean (
    quote_product_option_id integer,
    value boolean NOT NULL
);



CREATE TABLE quote_product_option_float (
    quote_product_option_id integer,
    value double precision NOT NULL
);



CREATE TABLE quote_product_option_timestamp (
    quote_product_option_id integer,
    value timestamp without time zone NOT NULL
);



CREATE TABLE quote_product_option_integer (
    quote_product_option_id integer,
    value integer NOT NULL
);



CREATE TABLE quote_product_option_text (
    quote_product_option_id integer,
    value text NOT NULL
);



CREATE TABLE lookup_order_status (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_order_type (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_order_terms (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_order_source (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE order_entry (
    order_id serial NOT NULL,
    parent_id integer,
    org_id integer NOT NULL,
    quote_id integer,
    sales_id integer,
    orderedby integer,
    billing_contact_id integer,
    source_id integer,
    grand_total double precision,
    status_id integer,
    status_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    contract_date timestamp(3) without time zone,
    expiration_date timestamp(3) without time zone,
    order_terms_id integer,
    order_type_id integer,
    description character varying(2048),
    notes text,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE order_product (
    item_id serial NOT NULL,
    order_id integer NOT NULL,
    product_id integer NOT NULL,
    quantity integer DEFAULT 0 NOT NULL,
    msrp_currency integer,
    msrp_amount double precision DEFAULT 0 NOT NULL,
    price_currency integer,
    price_amount double precision DEFAULT 0 NOT NULL,
    recurring_currency integer,
    recurring_amount double precision DEFAULT 0 NOT NULL,
    recurring_type integer,
    extended_price double precision DEFAULT 0 NOT NULL,
    total_price double precision DEFAULT 0 NOT NULL,
    status_id integer,
    status_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone
);



CREATE TABLE order_product_status (
    order_product_status_id serial NOT NULL,
    order_id integer NOT NULL,
    item_id integer NOT NULL,
    status_id integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE order_product_options (
    order_product_option_id serial NOT NULL,
    item_id integer NOT NULL,
    product_option_id integer NOT NULL,
    quantity integer DEFAULT 0 NOT NULL,
    price_currency integer,
    price_amount double precision DEFAULT 0 NOT NULL,
    recurring_currency integer,
    recurring_amount double precision DEFAULT 0 NOT NULL,
    recurring_type integer,
    extended_price double precision DEFAULT 0 NOT NULL,
    total_price double precision DEFAULT 0 NOT NULL,
    status_id integer
);



CREATE TABLE order_product_option_boolean (
    order_product_option_id integer,
    value boolean NOT NULL
);



CREATE TABLE order_product_option_float (
    order_product_option_id integer,
    value double precision NOT NULL
);



CREATE TABLE order_product_option_timestamp (
    order_product_option_id integer,
    value timestamp(3) without time zone NOT NULL
);



CREATE TABLE order_product_option_integer (
    order_product_option_id integer,
    value integer NOT NULL
);



CREATE TABLE order_product_option_text (
    order_product_option_id integer,
    value text NOT NULL
);



CREATE TABLE lookup_orderaddress_types (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE order_address (
    address_id serial NOT NULL,
    order_id integer NOT NULL,
    address_type integer,
    addrline1 character varying(300),
    addrline2 character varying(300),
    addrline3 character varying(300),
    city character varying(300),
    state character varying(300),
    country character varying(300),
    postalcode character varying(40),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE lookup_payment_methods (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_creditcard_type_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE lookup_creditcard_types (
    code integer DEFAULT nextval('lookup_creditcard_type_code_seq'::text) NOT NULL,
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
    modifiedby integer NOT NULL
);



CREATE TABLE payment_creditcard (
    creditcard_id serial NOT NULL,
    payment_id integer NOT NULL,
    card_type integer,
    card_number character varying(300),
    card_security_code character varying(300),
    expiration_month integer,
    expiration_year integer,
    name_on_card character varying(300),
    company_name_on_card character varying(300),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE payment_eft (
    bank_id serial NOT NULL,
    payment_id integer NOT NULL,
    bank_name character varying(300),
    routing_number character varying(300),
    account_number character varying(300),
    name_on_account character varying(300),
    company_name_on_account character varying(300),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE customer_product (
    customer_product_id serial NOT NULL,
    org_id integer NOT NULL,
    order_id integer,
    order_item_id integer,
    description character varying(2048),
    status_id integer,
    status_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    enabled boolean DEFAULT true
);



CREATE TABLE customer_product_history (
    history_id serial NOT NULL,
    customer_product_id integer NOT NULL,
    org_id integer NOT NULL,
    order_id integer,
    product_start_date timestamp(3) without time zone,
    product_end_date timestamp(3) without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);


ALTER TABLE sync_client ADD COLUMN enabled boolean;
ALTER TABLE sync_client ALTER enabled SET DEFAULT false;

ALTER TABLE sync_client ADD COLUMN code character varying(255);

CREATE TABLE quote_notes (
    notes_id serial NOT NULL,
    quote_id integer,
    notes text,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);

-- TODO: NEED TO CHANGE THE INSERTS SO THEY DO NOT USE THE SERIAL ID

-- TODO: UPDATE THE LEVELS

INSERT INTO lookup_asset_status VALUES (1, 'In use', false, 10, true);
INSERT INTO lookup_asset_status VALUES (2, 'Not in use', false, 20, true);
INSERT INTO lookup_asset_status VALUES (3, 'Requires maintenance', false, 30, true);
INSERT INTO lookup_asset_status VALUES (4, 'Retired', false, 40, true);



INSERT INTO lookup_sc_category VALUES (1, 'Consulting', false, 10, true);
INSERT INTO lookup_sc_category VALUES (2, 'Hardware Maintenance', false, 20, true);
INSERT INTO lookup_sc_category VALUES (3, 'Manufacturer''s Maintenance', false, 30, true);
INSERT INTO lookup_sc_category VALUES (4, 'Monitoring', false, 40, true);
INSERT INTO lookup_sc_category VALUES (5, 'Time and Materials', false, 50, true);
INSERT INTO lookup_sc_category VALUES (6, 'Warranty', false, 60, true);






INSERT INTO lookup_response_model VALUES (1, 'M-F 8AM-5PM 8 hours', false, 10, true);
INSERT INTO lookup_response_model VALUES (2, 'M-F 8AM-5PM 6 hours', false, 20, true);
INSERT INTO lookup_response_model VALUES (3, 'M-F 8AM-5PM 4 hours', false, 30, true);
INSERT INTO lookup_response_model VALUES (4, 'M-F 8AM-5PM same day', false, 40, true);
INSERT INTO lookup_response_model VALUES (5, 'M-F 8AM-5PM next business day', false, 50, true);
INSERT INTO lookup_response_model VALUES (6, 'M-F 8AM-8PM 4 hours', false, 60, true);
INSERT INTO lookup_response_model VALUES (7, 'M-F 8AM-8PM 2 hours', false, 70, true);
INSERT INTO lookup_response_model VALUES (8, '24x7 8 hours', false, 80, true);
INSERT INTO lookup_response_model VALUES (9, '24x7 4 hours', false, 90, true);
INSERT INTO lookup_response_model VALUES (10, '24x7 2 hours', false, 100, true);
INSERT INTO lookup_response_model VALUES (11, 'No response time guaranteed', false, 110, true);



INSERT INTO lookup_phone_model VALUES (1, '< 15 minutes', false, 10, true);
INSERT INTO lookup_phone_model VALUES (2, '< 5 minutes', false, 20, true);
INSERT INTO lookup_phone_model VALUES (3, 'M-F 7AM-4PM', false, 30, true);
INSERT INTO lookup_phone_model VALUES (4, 'M-F 8AM-5PM', false, 40, true);
INSERT INTO lookup_phone_model VALUES (5, 'M-F 8AM-8PM', false, 50, true);
INSERT INTO lookup_phone_model VALUES (6, '24x7', false, 60, true);
INSERT INTO lookup_phone_model VALUES (7, 'No phone support', false, 70, true);



INSERT INTO lookup_onsite_model VALUES (1, 'M-F 7AM-4PM', false, 10, true);
INSERT INTO lookup_onsite_model VALUES (2, 'M-F 8AM-5PM', false, 20, true);
INSERT INTO lookup_onsite_model VALUES (3, 'M-F 8AM-8PM', false, 30, true);
INSERT INTO lookup_onsite_model VALUES (4, '24x7', false, 40, true);
INSERT INTO lookup_onsite_model VALUES (5, 'No onsite service', false, 50, true);



INSERT INTO lookup_email_model VALUES (1, '2 hours', false, 10, true);
INSERT INTO lookup_email_model VALUES (2, '4 hours', false, 20, true);
INSERT INTO lookup_email_model VALUES (3, 'Same day', false, 30, true);
INSERT INTO lookup_email_model VALUES (4, 'Next business day', false, 40, true);



INSERT INTO lookup_hours_reason VALUES (1, 'Purchase', false, 10, true);
INSERT INTO lookup_hours_reason VALUES (2, 'Renewal', false, 20, true);
INSERT INTO lookup_hours_reason VALUES (3, 'Correction', false, 30, true);


INSERT INTO lookup_quote_status VALUES (1, 'Incomplete', false, 0, true);
INSERT INTO lookup_quote_status VALUES (2, 'Pending internal approval', false, 0, true);
INSERT INTO lookup_quote_status VALUES (3, 'Approved internally', false, 0, true);
INSERT INTO lookup_quote_status VALUES (4, 'Unapproved internally', false, 0, true);
INSERT INTO lookup_quote_status VALUES (5, 'Pending customer acceptance', false, 0, true);
INSERT INTO lookup_quote_status VALUES (6, 'Accepted by customer', false, 0, true);
INSERT INTO lookup_quote_status VALUES (7, 'Rejected by customer', false, 0, true);
INSERT INTO lookup_quote_status VALUES (8, 'Changes requested by customer', false, 0, true);
INSERT INTO lookup_quote_status VALUES (9, 'Cancelled', false, 0, true);
INSERT INTO lookup_quote_status VALUES (10, 'Complete', false, 0, true);


INSERT INTO lookup_order_status VALUES (1, 'Pending', false, 0, true);
INSERT INTO lookup_order_status VALUES (2, 'In Progress', false, 0, true);
INSERT INTO lookup_order_status VALUES (3, 'Cancelled', false, 0, true);
INSERT INTO lookup_order_status VALUES (4, 'Rejected', false, 0, true);
INSERT INTO lookup_order_status VALUES (5, 'Complete', false, 0, true);
INSERT INTO lookup_order_status VALUES (6, 'Closed', false, 0, true);



INSERT INTO lookup_order_type VALUES (1, 'New', false, 0, true);
INSERT INTO lookup_order_type VALUES (2, 'Change', false, 0, true);
INSERT INTO lookup_order_type VALUES (3, 'Upgrade', false, 0, true);
INSERT INTO lookup_order_type VALUES (4, 'Downgrade', false, 0, true);
INSERT INTO lookup_order_type VALUES (5, 'Disconnect', false, 0, true);
INSERT INTO lookup_order_type VALUES (6, 'Move', false, 0, true);
INSERT INTO lookup_order_type VALUES (7, 'Return', false, 0, true);
INSERT INTO lookup_order_type VALUES (8, 'Suspend', false, 0, true);
INSERT INTO lookup_order_type VALUES (9, 'Unsuspend', false, 0, true);

INSERT INTO lookup_orderaddress_types VALUES (1, 'Billing', false, 0, true);
INSERT INTO lookup_orderaddress_types VALUES (2, 'Shipping', false, 0, true);






INSERT INTO lookup_payment_methods VALUES (1, 'Cash', false, 0, true);
INSERT INTO lookup_payment_methods VALUES (2, 'Credit Card', false, 0, true);
INSERT INTO lookup_payment_methods VALUES (3, 'Personal Check', false, 0, true);
INSERT INTO lookup_payment_methods VALUES (4, 'Money Order', false, 0, true);
INSERT INTO lookup_payment_methods VALUES (5, 'Certified Check', false, 0, true);



INSERT INTO lookup_creditcard_types VALUES (1, 'Visa', false, 0, true);
INSERT INTO lookup_creditcard_types VALUES (2, 'Master Card', false, 0, true);
INSERT INTO lookup_creditcard_types VALUES (3, 'American Express', false, 0, true);
INSERT INTO lookup_creditcard_types VALUES (4, 'Discover', false, 0, true);

INSERT INTO product_category (category_name, enteredby, modifiedby, enabled) VALUES ('Labor Categories', 0, 0, true);

UPDATE sync_table SET object_key = 'id' WHERE element_name = 'customFieldRecord';

-- rebuild help

CREATE INDEX contact_import_id_idx ON contact USING btree (import_id);



CREATE INDEX import_entered_idx ON import USING btree (entered);



CREATE INDEX import_name_idx ON import USING btree (name);



CREATE UNIQUE INDEX idx_pr_opt_val ON product_option_values USING btree (option_id, result_id);



CREATE UNIQUE INDEX idx_pr_key_map ON product_keyword_map USING btree (product_id, keyword_id);


ALTER TABLE ONLY category_editor_lookup
    ADD CONSTRAINT category_editor_lookup_pkey PRIMARY KEY (id);



ALTER TABLE ONLY category_editor_lookup
    ADD CONSTRAINT "$1" FOREIGN KEY (module_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY import
    ADD CONSTRAINT import_pkey PRIMARY KEY (import_id);



ALTER TABLE ONLY import
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY import
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_currency
    ADD CONSTRAINT lookup_currency_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_product_category_type
    ADD CONSTRAINT lookup_product_category_type_pkey PRIMARY KEY (code);



ALTER TABLE ONLY product_category
    ADD CONSTRAINT product_category_pkey PRIMARY KEY (category_id);



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$1" FOREIGN KEY (parent_id) REFERENCES product_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_product_category_type(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$3" FOREIGN KEY (thumbnail_image_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$4" FOREIGN KEY (small_image_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$5" FOREIGN KEY (large_image_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_category_map
    ADD CONSTRAINT product_category_map_pkey PRIMARY KEY (id);



ALTER TABLE ONLY product_category_map
    ADD CONSTRAINT "$1" FOREIGN KEY (category1_id) REFERENCES product_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_category_map
    ADD CONSTRAINT "$2" FOREIGN KEY (category2_id) REFERENCES product_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_product_type
    ADD CONSTRAINT lookup_product_type_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_product_format
    ADD CONSTRAINT lookup_product_format_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_product_shipping
    ADD CONSTRAINT lookup_product_shipping_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_product_ship_time
    ADD CONSTRAINT lookup_product_ship_time_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_product_tax
    ADD CONSTRAINT lookup_product_tax_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_recurring_type
    ADD CONSTRAINT lookup_recurring_type_pkey PRIMARY KEY (code);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT product_catalog_pkey PRIMARY KEY (product_id);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$1" FOREIGN KEY (parent_id) REFERENCES product_catalog(product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_product_type(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$3" FOREIGN KEY (format_id) REFERENCES lookup_product_format(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$4" FOREIGN KEY (shipping_id) REFERENCES lookup_product_shipping(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$5" FOREIGN KEY (estimated_ship_time) REFERENCES lookup_product_ship_time(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$6" FOREIGN KEY (thumbnail_image_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$7" FOREIGN KEY (small_image_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$8" FOREIGN KEY (large_image_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$9" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$10" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT product_catalog_pricing_pkey PRIMARY KEY (price_id);



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$1" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$2" FOREIGN KEY (tax_id) REFERENCES lookup_product_tax(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$3" FOREIGN KEY (msrp_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$4" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$5" FOREIGN KEY (recurring_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$6" FOREIGN KEY (recurring_type) REFERENCES lookup_recurring_type(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$7" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$8" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY package
    ADD CONSTRAINT package_pkey PRIMARY KEY (package_id);



ALTER TABLE ONLY package
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES product_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY package
    ADD CONSTRAINT "$2" FOREIGN KEY (thumbnail_image_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY package
    ADD CONSTRAINT "$3" FOREIGN KEY (small_image_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY package
    ADD CONSTRAINT "$4" FOREIGN KEY (large_image_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY package
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY package
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT package_products_map_pkey PRIMARY KEY (id);



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT "$1" FOREIGN KEY (package_id) REFERENCES package(package_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT "$2" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT "$3" FOREIGN KEY (msrp_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT "$4" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog_category_map
    ADD CONSTRAINT product_catalog_category_map_pkey PRIMARY KEY (id);



ALTER TABLE ONLY product_catalog_category_map
    ADD CONSTRAINT "$1" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_catalog_category_map
    ADD CONSTRAINT "$2" FOREIGN KEY (category_id) REFERENCES product_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_product_conf_result
    ADD CONSTRAINT lookup_product_conf_result_pkey PRIMARY KEY (code);



ALTER TABLE ONLY product_option_configurator
    ADD CONSTRAINT product_option_configurator_pkey PRIMARY KEY (configurator_id);



ALTER TABLE ONLY product_option_configurator
    ADD CONSTRAINT "$1" FOREIGN KEY (result_type) REFERENCES lookup_product_conf_result(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option
    ADD CONSTRAINT product_option_pkey PRIMARY KEY (option_id);



ALTER TABLE ONLY product_option
    ADD CONSTRAINT "$1" FOREIGN KEY (configurator_id) REFERENCES product_option_configurator(configurator_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option
    ADD CONSTRAINT "$2" FOREIGN KEY (parent_id) REFERENCES product_option(option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT product_option_values_pkey PRIMARY KEY (value_id);



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT "$1" FOREIGN KEY (option_id) REFERENCES product_option(option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT "$2" FOREIGN KEY (msrp_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT "$3" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT "$4" FOREIGN KEY (recurring_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT "$5" FOREIGN KEY (recurring_type) REFERENCES lookup_recurring_type(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option_map
    ADD CONSTRAINT product_option_map_pkey PRIMARY KEY (product_option_id);



ALTER TABLE ONLY product_option_map
    ADD CONSTRAINT "$1" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option_map
    ADD CONSTRAINT "$2" FOREIGN KEY (option_id) REFERENCES product_option(option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option_map
    ADD CONSTRAINT "$3" FOREIGN KEY (value_id) REFERENCES product_option_values(value_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option_boolean
    ADD CONSTRAINT "$1" FOREIGN KEY (product_option_id) REFERENCES product_option(option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option_float
    ADD CONSTRAINT "$1" FOREIGN KEY (product_option_id) REFERENCES product_option(option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option_timestamp
    ADD CONSTRAINT "$1" FOREIGN KEY (product_option_id) REFERENCES product_option(option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option_integer
    ADD CONSTRAINT "$1" FOREIGN KEY (product_option_id) REFERENCES product_option(option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_option_text
    ADD CONSTRAINT "$1" FOREIGN KEY (product_option_id) REFERENCES product_option(option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_product_keyword
    ADD CONSTRAINT lookup_product_keyword_pkey PRIMARY KEY (code);



ALTER TABLE ONLY product_keyword_map
    ADD CONSTRAINT "$1" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY product_keyword_map
    ADD CONSTRAINT "$2" FOREIGN KEY (keyword_id) REFERENCES lookup_product_keyword(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_asset_status
    ADD CONSTRAINT lookup_asset_status_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_sc_category
    ADD CONSTRAINT lookup_sc_category_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_sc_type
    ADD CONSTRAINT lookup_sc_type_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_response_model
    ADD CONSTRAINT lookup_response_model_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_phone_model
    ADD CONSTRAINT lookup_phone_model_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_onsite_model
    ADD CONSTRAINT lookup_onsite_model_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_email_model
    ADD CONSTRAINT lookup_email_model_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_hours_reason
    ADD CONSTRAINT lookup_hours_reason_pkey PRIMARY KEY (code);



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT service_contract_pkey PRIMARY KEY (contract_id);



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$1" FOREIGN KEY (account_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$2" FOREIGN KEY (category) REFERENCES lookup_sc_category(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$3" FOREIGN KEY ("type") REFERENCES lookup_sc_type(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$4" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$5" FOREIGN KEY (response_time) REFERENCES lookup_response_model(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$6" FOREIGN KEY (telephone_service_model) REFERENCES lookup_phone_model(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$7" FOREIGN KEY (onsite_service_model) REFERENCES lookup_onsite_model(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$8" FOREIGN KEY (email_service_model) REFERENCES lookup_email_model(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$9" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$10" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract_hours
    ADD CONSTRAINT service_contract_hours_pkey PRIMARY KEY (history_id);



ALTER TABLE ONLY service_contract_hours
    ADD CONSTRAINT "$1" FOREIGN KEY (link_contract_id) REFERENCES service_contract(contract_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract_hours
    ADD CONSTRAINT "$2" FOREIGN KEY (adjustment_reason) REFERENCES lookup_hours_reason(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract_hours
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract_hours
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract_products
    ADD CONSTRAINT service_contract_products_pkey PRIMARY KEY (id);



ALTER TABLE ONLY service_contract_products
    ADD CONSTRAINT "$1" FOREIGN KEY (link_contract_id) REFERENCES service_contract(contract_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY service_contract_products
    ADD CONSTRAINT "$2" FOREIGN KEY (link_product_id) REFERENCES product_catalog(product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY asset_category
    ADD CONSTRAINT asset_category_pkey PRIMARY KEY (id);



ALTER TABLE ONLY asset_category_draft
    ADD CONSTRAINT asset_category_draft_pkey PRIMARY KEY (id);



ALTER TABLE ONLY asset
    ADD CONSTRAINT asset_pkey PRIMARY KEY (asset_id);



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$1" FOREIGN KEY (account_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$2" FOREIGN KEY (contract_id) REFERENCES service_contract(contract_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$3" FOREIGN KEY (level1) REFERENCES asset_category(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$4" FOREIGN KEY (level2) REFERENCES asset_category(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$5" FOREIGN KEY (level3) REFERENCES asset_category(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$6" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$7" FOREIGN KEY (response_time) REFERENCES lookup_response_model(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$8" FOREIGN KEY (telephone_service_model) REFERENCES lookup_phone_model(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$9" FOREIGN KEY (onsite_service_model) REFERENCES lookup_onsite_model(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$10" FOREIGN KEY (email_service_model) REFERENCES lookup_email_model(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$11" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$12" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$11" FOREIGN KEY (cat_code) REFERENCES ticket_category(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$12" FOREIGN KEY (subcat_code1) REFERENCES ticket_category(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$13" FOREIGN KEY (subcat_code2) REFERENCES ticket_category(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$14" FOREIGN KEY (subcat_code3) REFERENCES ticket_category(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$15" FOREIGN KEY (link_contract_id) REFERENCES service_contract(contract_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$16" FOREIGN KEY (link_asset_id) REFERENCES asset(asset_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$17" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket_csstm_form
    ADD CONSTRAINT ticket_csstm_form_pkey PRIMARY KEY (form_id);



ALTER TABLE ONLY ticket_csstm_form
    ADD CONSTRAINT "$1" FOREIGN KEY (link_ticket_id) REFERENCES ticket(ticketid) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket_csstm_form
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket_csstm_form
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket_activity_item
    ADD CONSTRAINT ticket_activity_item_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY ticket_activity_item
    ADD CONSTRAINT "$1" FOREIGN KEY (link_form_id) REFERENCES ticket_csstm_form(form_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket_sun_form
    ADD CONSTRAINT ticket_sun_form_pkey PRIMARY KEY (form_id);



ALTER TABLE ONLY ticket_sun_form
    ADD CONSTRAINT "$1" FOREIGN KEY (link_ticket_id) REFERENCES ticket(ticketid) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket_sun_form
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket_sun_form
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY trouble_asset_replacement
    ADD CONSTRAINT trouble_asset_replacement_pkey PRIMARY KEY (replacement_id);



ALTER TABLE ONLY trouble_asset_replacement
    ADD CONSTRAINT "$1" FOREIGN KEY (link_form_id) REFERENCES ticket_sun_form(form_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_quote_status
    ADD CONSTRAINT lookup_quote_status_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_quote_type
    ADD CONSTRAINT lookup_quote_type_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_quote_terms
    ADD CONSTRAINT lookup_quote_terms_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_quote_source
    ADD CONSTRAINT lookup_quote_source_pkey PRIMARY KEY (code);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT quote_entry_pkey PRIMARY KEY (quote_id);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$1" FOREIGN KEY (parent_id) REFERENCES quote_entry(quote_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$2" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$3" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$4" FOREIGN KEY (source_id) REFERENCES lookup_quote_source(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$5" FOREIGN KEY (status_id) REFERENCES lookup_quote_status(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$6" FOREIGN KEY (quote_terms_id) REFERENCES lookup_quote_terms(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$7" FOREIGN KEY (quote_type_id) REFERENCES lookup_quote_type(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$8" FOREIGN KEY (ticketid) REFERENCES ticket(ticketid) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$9" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$10" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT quote_product_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_id) REFERENCES quote_entry(quote_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT "$2" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT "$3" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT "$4" FOREIGN KEY (recurring_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT "$5" FOREIGN KEY (recurring_type) REFERENCES lookup_recurring_type(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT "$6" FOREIGN KEY (status_id) REFERENCES lookup_quote_status(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT quote_product_options_pkey PRIMARY KEY (quote_product_option_id);



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES quote_product(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT "$2" FOREIGN KEY (product_option_id) REFERENCES product_option_map(product_option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT "$3" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT "$4" FOREIGN KEY (recurring_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT "$5" FOREIGN KEY (recurring_type) REFERENCES lookup_recurring_type(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT "$6" FOREIGN KEY (status_id) REFERENCES lookup_quote_status(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product_option_boolean
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_product_option_id) REFERENCES quote_product_options(quote_product_option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product_option_float
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_product_option_id) REFERENCES quote_product_options(quote_product_option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product_option_timestamp
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_product_option_id) REFERENCES quote_product_options(quote_product_option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product_option_integer
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_product_option_id) REFERENCES quote_product_options(quote_product_option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_product_option_text
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_product_option_id) REFERENCES quote_product_options(quote_product_option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_order_status
    ADD CONSTRAINT lookup_order_status_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_order_type
    ADD CONSTRAINT lookup_order_type_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_order_terms
    ADD CONSTRAINT lookup_order_terms_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_order_source
    ADD CONSTRAINT lookup_order_source_pkey PRIMARY KEY (code);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT order_entry_pkey PRIMARY KEY (order_id);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$1" FOREIGN KEY (parent_id) REFERENCES order_entry(order_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$2" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$3" FOREIGN KEY (quote_id) REFERENCES quote_entry(quote_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$4" FOREIGN KEY (sales_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$5" FOREIGN KEY (orderedby) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$6" FOREIGN KEY (billing_contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$7" FOREIGN KEY (source_id) REFERENCES lookup_order_source(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$8" FOREIGN KEY (status_id) REFERENCES lookup_order_status(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$9" FOREIGN KEY (order_terms_id) REFERENCES lookup_order_terms(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$10" FOREIGN KEY (order_type_id) REFERENCES lookup_order_type(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$11" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$12" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product
    ADD CONSTRAINT order_product_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$1" FOREIGN KEY (order_id) REFERENCES order_entry(order_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$2" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$3" FOREIGN KEY (msrp_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$4" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$5" FOREIGN KEY (recurring_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$6" FOREIGN KEY (recurring_type) REFERENCES lookup_recurring_type(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$7" FOREIGN KEY (status_id) REFERENCES lookup_order_status(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_status
    ADD CONSTRAINT order_product_status_pkey PRIMARY KEY (order_product_status_id);



ALTER TABLE ONLY order_product_status
    ADD CONSTRAINT "$1" FOREIGN KEY (order_id) REFERENCES order_entry(order_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_status
    ADD CONSTRAINT "$2" FOREIGN KEY (item_id) REFERENCES order_product(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_status
    ADD CONSTRAINT "$3" FOREIGN KEY (status_id) REFERENCES lookup_order_status(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_status
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_status
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT order_product_options_pkey PRIMARY KEY (order_product_option_id);



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES order_product(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT "$2" FOREIGN KEY (product_option_id) REFERENCES product_option_map(product_option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT "$3" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT "$4" FOREIGN KEY (recurring_currency) REFERENCES lookup_currency(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT "$5" FOREIGN KEY (recurring_type) REFERENCES lookup_recurring_type(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT "$6" FOREIGN KEY (status_id) REFERENCES lookup_order_status(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_option_boolean
    ADD CONSTRAINT "$1" FOREIGN KEY (order_product_option_id) REFERENCES order_product_options(order_product_option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_option_float
    ADD CONSTRAINT "$1" FOREIGN KEY (order_product_option_id) REFERENCES order_product_options(order_product_option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_option_timestamp
    ADD CONSTRAINT "$1" FOREIGN KEY (order_product_option_id) REFERENCES order_product_options(order_product_option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_option_integer
    ADD CONSTRAINT "$1" FOREIGN KEY (order_product_option_id) REFERENCES order_product_options(order_product_option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_product_option_text
    ADD CONSTRAINT "$1" FOREIGN KEY (order_product_option_id) REFERENCES order_product_options(order_product_option_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_orderaddress_types
    ADD CONSTRAINT lookup_orderaddress_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY order_address
    ADD CONSTRAINT order_address_pkey PRIMARY KEY (address_id);



ALTER TABLE ONLY order_address
    ADD CONSTRAINT "$1" FOREIGN KEY (order_id) REFERENCES order_entry(order_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_address
    ADD CONSTRAINT "$2" FOREIGN KEY (address_type) REFERENCES lookup_orderaddress_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_address
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_address
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_payment_methods
    ADD CONSTRAINT lookup_payment_methods_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_creditcard_types
    ADD CONSTRAINT lookup_creditcard_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT order_payment_pkey PRIMARY KEY (payment_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$1" FOREIGN KEY (order_id) REFERENCES order_entry(order_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$2" FOREIGN KEY (payment_method_id) REFERENCES lookup_payment_methods(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY payment_creditcard
    ADD CONSTRAINT payment_creditcard_pkey PRIMARY KEY (creditcard_id);



ALTER TABLE ONLY payment_creditcard
    ADD CONSTRAINT "$1" FOREIGN KEY (payment_id) REFERENCES order_payment(payment_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY payment_creditcard
    ADD CONSTRAINT "$2" FOREIGN KEY (card_type) REFERENCES lookup_creditcard_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY payment_creditcard
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY payment_creditcard
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY payment_eft
    ADD CONSTRAINT payment_eft_pkey PRIMARY KEY (bank_id);



ALTER TABLE ONLY payment_eft
    ADD CONSTRAINT "$1" FOREIGN KEY (payment_id) REFERENCES order_payment(payment_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY payment_eft
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY payment_eft
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT customer_product_pkey PRIMARY KEY (customer_product_id);



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT "$2" FOREIGN KEY (order_id) REFERENCES order_entry(order_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT "$3" FOREIGN KEY (order_item_id) REFERENCES order_product(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT "$4" FOREIGN KEY (status_id) REFERENCES lookup_order_status(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT customer_product_history_pkey PRIMARY KEY (history_id);



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT "$1" FOREIGN KEY (customer_product_id) REFERENCES customer_product(customer_product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT "$2" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT "$3" FOREIGN KEY (order_id) REFERENCES order_entry(order_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$18" FOREIGN KEY (customer_product_id) REFERENCES customer_product(customer_product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$11" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$12" FOREIGN KEY (customer_product_id) REFERENCES customer_product(customer_product_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_notes
    ADD CONSTRAINT quote_notes_pkey PRIMARY KEY (notes_id);



ALTER TABLE ONLY quote_notes
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_id) REFERENCES quote_entry(quote_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_notes
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY quote_notes
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;






SELECT pg_catalog.setval ('import_import_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_currency_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_product_categor_code_seq', 1, false);



SELECT pg_catalog.setval ('product_category_category_id_seq', 1, false);



SELECT pg_catalog.setval ('product_category_map_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_product_type_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_product_format_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_product_shippin_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_product_ship_ti_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_product_tax_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_recurring_type_code_seq', 1, false);



SELECT pg_catalog.setval ('product_catalog_product_id_seq', 1, false);



SELECT pg_catalog.setval ('product_catalog_pricing_price_id_seq', 1, false);



SELECT pg_catalog.setval ('package_package_id_seq', 1, false);



SELECT pg_catalog.setval ('package_products_map_id_seq', 1, false);



SELECT pg_catalog.setval ('product_catalog_category_map_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_product_conf_re_code_seq', 1, false);



SELECT pg_catalog.setval ('product_option_configurator_configurator_id_seq', 1, false);



SELECT pg_catalog.setval ('product_option_option_id_seq', 1, false);



SELECT pg_catalog.setval ('product_option_values_value_id_seq', 1, false);



SELECT pg_catalog.setval ('product_option_map_product_option_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_product_keyword_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_asset_status_code_seq', 4, true);



SELECT pg_catalog.setval ('lookup_sc_category_code_seq', 6, true);



SELECT pg_catalog.setval ('lookup_sc_type_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_response_model_code_seq', 11, true);



SELECT pg_catalog.setval ('lookup_phone_model_code_seq', 7, true);



SELECT pg_catalog.setval ('lookup_onsite_model_code_seq', 5, true);



SELECT pg_catalog.setval ('lookup_email_model_code_seq', 4, true);



SELECT pg_catalog.setval ('lookup_hours_reason_code_seq', 3, true);



SELECT pg_catalog.setval ('service_contract_contract_id_seq', 1, false);



SELECT pg_catalog.setval ('service_contract_hours_history_id_seq', 1, false);



SELECT pg_catalog.setval ('service_contract_products_id_seq', 1, false);



SELECT pg_catalog.setval ('asset_category_id_seq', 1, false);



SELECT pg_catalog.setval ('asset_category_draft_id_seq', 1, false);



SELECT pg_catalog.setval ('asset_asset_id_seq', 1, false);



SELECT pg_catalog.setval ('ticket_csstm_form_form_id_seq', 1, false);



SELECT pg_catalog.setval ('ticket_activity_item_item_id_seq', 1, false);



SELECT pg_catalog.setval ('ticket_sun_form_form_id_seq', 1, false);



SELECT pg_catalog.setval ('trouble_asset_replacement_replacement_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_quote_status_code_seq', 10, true);



SELECT pg_catalog.setval ('lookup_quote_type_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_quote_terms_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_quote_source_code_seq', 1, false);



SELECT pg_catalog.setval ('quote_entry_quote_id_seq', 1, false);



SELECT pg_catalog.setval ('quote_product_item_id_seq', 1, false);



SELECT pg_catalog.setval ('quote_product_options_quote_product_option_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_order_status_code_seq', 6, true);



SELECT pg_catalog.setval ('lookup_order_type_code_seq', 9, true);



SELECT pg_catalog.setval ('lookup_order_terms_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_order_source_code_seq', 1, false);



SELECT pg_catalog.setval ('order_entry_order_id_seq', 1, false);



SELECT pg_catalog.setval ('order_product_item_id_seq', 1, false);



SELECT pg_catalog.setval ('order_product_status_order_product_status_id_seq', 1, false);



SELECT pg_catalog.setval ('order_product_options_order_product_option_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_orderaddress_types_code_seq', 2, true);



SELECT pg_catalog.setval ('order_address_address_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_payment_methods_code_seq', 5, true);



SELECT pg_catalog.setval ('lookup_creditcard_type_code_seq', 4, true);



SELECT pg_catalog.setval ('order_payment_payment_id_seq', 1, false);



SELECT pg_catalog.setval ('payment_creditcard_creditcard_id_seq', 1, false);



SELECT pg_catalog.setval ('payment_eft_bank_id_seq', 1, false);



SELECT pg_catalog.setval ('customer_product_customer_product_id_seq', 1, false);



SELECT pg_catalog.setval ('customer_product_history_history_id_seq', 1, false);



SELECT pg_catalog.setval ('quote_notes_notes_id_seq', 1, false);

INSERT INTO database_version (script_filename, script_version) VALUES ('postgresql_2004-06-15.sql', '2004-06-15');

