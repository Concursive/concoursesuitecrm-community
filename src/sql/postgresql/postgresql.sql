-- Script (C) 2005 Dark Horse Ventures LLC, all rights reserved

SET search_path = public, pg_catalog;


CREATE TABLE sites (
    site_id serial NOT NULL,
    sitecode character varying(255) NOT NULL,
    vhost character varying(255) DEFAULT '' NOT NULL,
    dbhost character varying(255) DEFAULT '' NOT NULL,
    dbname character varying(255) DEFAULT '' NOT NULL,
    dbport integer DEFAULT 5432 NOT NULL,
    dbuser character varying(255) DEFAULT '' NOT NULL,
    dbpw character varying(255) DEFAULT '' NOT NULL,
    driver character varying(255) DEFAULT '' NOT NULL,
    code character varying(255),
    enabled boolean DEFAULT false NOT NULL
);


CREATE TABLE events (
    event_id serial NOT NULL,
    "second" character varying(64) DEFAULT '0',
    "minute" character varying(64) DEFAULT '*',
    "hour" character varying(64) DEFAULT '*',
    dayofmonth character varying(64) DEFAULT '*',
    "month" character varying(64) DEFAULT '*',
    dayofweek character varying(64) DEFAULT '*',
    "year" character varying(64) DEFAULT '*',
    task character varying(255),
    extrainfo character varying(255),
    businessdays character varying(6) DEFAULT 'true',
    enabled boolean DEFAULT false,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);


CREATE TABLE events_log (
    log_id serial NOT NULL,
    event_id integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    status integer,
    message text
);


ALTER TABLE ONLY sites
    ADD CONSTRAINT sites_pkey PRIMARY KEY (site_id);



ALTER TABLE ONLY events
    ADD CONSTRAINT events_pkey PRIMARY KEY (event_id);



ALTER TABLE ONLY events_log
    ADD CONSTRAINT events_log_pkey PRIMARY KEY (log_id);



ALTER TABLE ONLY events_log
    ADD CONSTRAINT "$1" FOREIGN KEY (event_id) REFERENCES events(event_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



SELECT pg_catalog.setval ('sites_site_id_seq', 1, false);



SELECT pg_catalog.setval ('events_event_id_seq', 1, true);



SELECT pg_catalog.setval ('events_log_log_id_seq', 1, false);


SET search_path = public, pg_catalog;


CREATE SEQUENCE access_user_id_seq
    START WITH 0
    INCREMENT BY 1
    NO MAXVALUE
    MINVALUE 0
    CACHE 1;



CREATE TABLE "access" (
    user_id integer DEFAULT nextval('access_user_id_seq'::text) NOT NULL,
    username character varying(80) NOT NULL,
    "password" character varying(80),
    contact_id integer DEFAULT -1,
    role_id integer DEFAULT -1,
    manager_id integer DEFAULT -1,
    startofday integer DEFAULT 8,
    endofday integer DEFAULT 18,
    locale character varying(255),
    timezone character varying(255) DEFAULT 'America/New_York'::character varying,
    last_ip character varying(15),
    last_login timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    expires timestamp(3) without time zone,
    alias integer DEFAULT -1,
    assistant integer DEFAULT -1,
    enabled boolean DEFAULT true NOT NULL,
    currency character varying(5),
    "language" character varying(20),
    webdav_password character varying(80),
    hidden boolean DEFAULT false
);



CREATE TABLE lookup_industry (
    code serial NOT NULL,
    order_id integer,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE access_log (
    id serial NOT NULL,
    user_id integer NOT NULL,
    username character varying(80) NOT NULL,
    ip character varying(15),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    browser character varying(255)
);



CREATE TABLE usage_log (
    usage_id serial NOT NULL,
    entered timestamp(6) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer,
    "action" integer NOT NULL,
    record_id integer,
    record_size integer
);



CREATE TABLE lookup_contact_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    user_id integer,
    category integer DEFAULT 0 NOT NULL
);



CREATE TABLE lookup_account_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE state (
    state_code character(2) NOT NULL,
    state character varying(80) NOT NULL
);



CREATE TABLE lookup_department (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_orgaddress_type_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_orgaddress_types (
    code integer DEFAULT nextval('lookup_orgaddress_type_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_orgemail_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_orgphone_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



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
    START WITH 1
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



CREATE SEQUENCE lookup_employment_type_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_employment_types (
    code integer DEFAULT nextval('lookup_employment_type_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_locale (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_contactaddress__code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_contactaddress_types (
    code integer DEFAULT nextval('lookup_contactaddress__code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_contactemail_ty_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_contactemail_types (
    code integer DEFAULT nextval('lookup_contactemail_ty_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_contactphone_ty_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_contactphone_types (
    code integer DEFAULT nextval('lookup_contactphone_ty_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_access_types (
    code serial NOT NULL,
    link_module_id integer NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer,
    enabled boolean DEFAULT true,
    rule_id integer NOT NULL
);



CREATE SEQUENCE organization_org_id_seq
    START WITH 0
    INCREMENT BY 1
    NO MAXVALUE
    MINVALUE 0
    CACHE 1;



CREATE TABLE organization (
    org_id integer DEFAULT nextval('organization_org_id_seq'::text) NOT NULL,
    name character varying(80) NOT NULL,
    account_number character varying(50),
    account_group integer,
    url text,
    revenue double precision,
    employees integer,
    notes text,
    sic_code character varying(40),
    ticker_symbol character varying(10),
    taxid character(80),
    lead character varying(40),
    sales_rep integer DEFAULT 0 NOT NULL,
    miner_only boolean DEFAULT false NOT NULL,
    defaultlocale integer,
    fiscalmonth integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    enabled boolean DEFAULT true,
    industry_temp_code smallint,
    "owner" integer,
    duplicate_id integer DEFAULT -1,
    custom1 integer DEFAULT -1,
    custom2 integer DEFAULT -1,
    contract_end timestamp(3) without time zone,
    alertdate timestamp(3) without time zone,
    alert character varying(100),
    custom_data text,
    namesalutation character varying(80),
    namelast character varying(80),
    namefirst character varying(80),
    namemiddle character varying(80),
    namesuffix character varying(80),
    import_id integer,
    status_id integer,
    alertdate_timezone character varying(255),
    contract_end_timezone character varying(255),
    trashed_date timestamp(3) without time zone
);



CREATE TABLE contact (
    contact_id serial NOT NULL,
    user_id integer,
    org_id integer,
    company character varying(255),
    title character varying(80),
    department integer,
    super integer,
    namesalutation character varying(80),
    namelast character varying(80) NOT NULL,
    namefirst character varying(80) NOT NULL,
    namemiddle character varying(80),
    namesuffix character varying(80),
    assistant integer,
    birthdate date,
    notes text,
    site integer,
    locale integer,
    employee_id character varying(80),
    employmenttype integer,
    startofday character varying(10),
    endofday character varying(10),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    enabled boolean DEFAULT true,
    "owner" integer,
    custom1 integer DEFAULT -1,
    url character varying(100),
    primary_contact boolean DEFAULT false,
    employee boolean DEFAULT false NOT NULL,
    org_name character varying(255),
    access_type integer,
    status_id integer,
    import_id integer,
    information_update_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    lead boolean DEFAULT false,
    lead_status integer,
    source integer,
    rating integer,
    comments character varying(255),
    conversion_date timestamp(3) without time zone,
    additional_names character varying(255),
    nickname character varying(80),
    role character varying(255),
    trashed_date timestamp(3) without time zone
);



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



CREATE TABLE role (
    role_id serial NOT NULL,
    role character varying(80) NOT NULL,
    description character varying(255) DEFAULT ''::character varying NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enabled boolean DEFAULT true NOT NULL,
    role_type integer
);



CREATE SEQUENCE permission_cate_category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE permission_category (
    category_id integer DEFAULT nextval('permission_cate_category_id_seq'::text) NOT NULL,
    category character varying(80),
    description character varying(255),
    "level" integer DEFAULT 0 NOT NULL,
    enabled boolean DEFAULT true NOT NULL,
    active boolean DEFAULT true NOT NULL,
    folders boolean DEFAULT false NOT NULL,
    lookups boolean DEFAULT false NOT NULL,
    viewpoints boolean DEFAULT false,
    categories boolean DEFAULT false NOT NULL,
    scheduled_events boolean DEFAULT false NOT NULL,
    object_events boolean DEFAULT false NOT NULL,
    reports boolean DEFAULT false NOT NULL,
    products boolean DEFAULT false NOT NULL,
    webdav boolean DEFAULT false NOT NULL,
    logos boolean DEFAULT false NOT NULL,
    constant integer NOT NULL
);



CREATE TABLE permission (
    permission_id serial NOT NULL,
    category_id integer NOT NULL,
    permission character varying(80) NOT NULL,
    permission_view boolean DEFAULT false NOT NULL,
    permission_add boolean DEFAULT false NOT NULL,
    permission_edit boolean DEFAULT false NOT NULL,
    permission_delete boolean DEFAULT false NOT NULL,
    description character varying(255) DEFAULT ''::character varying NOT NULL,
    "level" integer DEFAULT 0 NOT NULL,
    enabled boolean DEFAULT true NOT NULL,
    active boolean DEFAULT true NOT NULL,
    viewpoints boolean DEFAULT false
);



CREATE TABLE role_permission (
    id serial NOT NULL,
    role_id integer NOT NULL,
    permission_id integer NOT NULL,
    role_view boolean DEFAULT false NOT NULL,
    role_add boolean DEFAULT false NOT NULL,
    role_edit boolean DEFAULT false NOT NULL,
    role_delete boolean DEFAULT false NOT NULL
);



CREATE TABLE lookup_stage (
    code serial NOT NULL,
    order_id integer,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_delivery_option_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_delivery_options (
    code integer DEFAULT nextval('lookup_delivery_option_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE news (
    rec_id serial NOT NULL,
    org_id integer,
    url text,
    base text,
    headline text,
    body text,
    dateentered date,
    "type" character(1),
    created timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);



CREATE SEQUENCE organization_add_address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE organization_address (
    address_id integer DEFAULT nextval('organization_add_address_id_seq'::text) NOT NULL,
    org_id integer,
    address_type integer,
    addrline1 character varying(80),
    addrline2 character varying(80),
    addrline3 character varying(80),
    city character varying(80),
    state character varying(80),
    country character varying(80),
    postalcode character varying(12),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    primary_address boolean DEFAULT false NOT NULL
);



CREATE SEQUENCE organization__emailaddress__seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE organization_emailaddress (
    emailaddress_id integer DEFAULT nextval('organization__emailaddress__seq'::text) NOT NULL,
    org_id integer,
    emailaddress_type integer,
    email character varying(256),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    primary_email boolean DEFAULT false NOT NULL
);



CREATE SEQUENCE organization_phone_phone_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE organization_phone (
    phone_id integer DEFAULT nextval('organization_phone_phone_id_seq'::text) NOT NULL,
    org_id integer,
    phone_type integer,
    number character varying(30),
    extension character varying(10),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    primary_number boolean DEFAULT false NOT NULL
);



CREATE TABLE contact_address (
    address_id serial NOT NULL,
    contact_id integer,
    address_type integer,
    addrline1 character varying(80),
    addrline2 character varying(80),
    addrline3 character varying(80),
    city character varying(80),
    state character varying(80),
    country character varying(80),
    postalcode character varying(12),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    primary_address boolean DEFAULT false NOT NULL
);



CREATE SEQUENCE contact_email_emailaddress__seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE contact_emailaddress (
    emailaddress_id integer DEFAULT nextval('contact_email_emailaddress__seq'::text) NOT NULL,
    contact_id integer,
    emailaddress_type integer,
    email character varying(256),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    primary_email boolean DEFAULT false NOT NULL
);



CREATE TABLE contact_phone (
    phone_id serial NOT NULL,
    contact_id integer,
    phone_type integer,
    number character varying(30),
    extension character varying(10),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    primary_number boolean DEFAULT false NOT NULL
);



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



CREATE SEQUENCE notification_notification_i_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE notification (
    notification_id integer DEFAULT nextval('notification_notification_i_seq'::text) NOT NULL,
    notify_user integer NOT NULL,
    module character varying(255) NOT NULL,
    item_id integer NOT NULL,
    item_modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    attempt timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    notify_type character varying(30),
    subject text,
    message text,
    result integer NOT NULL,
    errormessage text
);



CREATE TABLE cfsinbox_message (
    id serial NOT NULL,
    subject character varying(255),
    body text NOT NULL,
    reply_id integer NOT NULL,
    enteredby integer NOT NULL,
    sent timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    "type" integer DEFAULT -1 NOT NULL,
    modifiedby integer NOT NULL,
    delete_flag boolean DEFAULT false
);



CREATE TABLE cfsinbox_messagelink (
    id integer NOT NULL,
    sent_to integer NOT NULL,
    status integer DEFAULT 0 NOT NULL,
    viewed timestamp(3) without time zone,
    enabled boolean DEFAULT true NOT NULL,
    sent_from integer NOT NULL
);



CREATE TABLE account_type_levels (
    org_id integer NOT NULL,
    type_id integer NOT NULL,
    "level" integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);



CREATE TABLE contact_type_levels (
    contact_id integer NOT NULL,
    type_id integer NOT NULL,
    "level" integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);



CREATE TABLE lookup_lists_lookup (
    id serial NOT NULL,
    module_id integer NOT NULL,
    lookup_id integer NOT NULL,
    class_name character varying(20),
    table_name character varying(60),
    "level" integer DEFAULT 0,
    description text,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    category_id integer NOT NULL
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



CREATE TABLE viewpoint (
    viewpoint_id serial NOT NULL,
    user_id integer NOT NULL,
    vp_user_id integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE viewpoint_per_vp_permission_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE viewpoint_permission (
    vp_permission_id integer DEFAULT nextval('viewpoint_per_vp_permission_seq'::text) NOT NULL,
    viewpoint_id integer NOT NULL,
    permission_id integer NOT NULL,
    viewpoint_view boolean DEFAULT false NOT NULL,
    viewpoint_add boolean DEFAULT false NOT NULL,
    viewpoint_edit boolean DEFAULT false NOT NULL,
    viewpoint_delete boolean DEFAULT false NOT NULL
);



CREATE TABLE report (
    report_id serial NOT NULL,
    category_id integer NOT NULL,
    permission_id integer,
    filename character varying(300) NOT NULL,
    "type" integer DEFAULT 1 NOT NULL,
    title character varying(300) NOT NULL,
    description character varying(1024) NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    enabled boolean DEFAULT true,
    custom boolean DEFAULT false
);



CREATE TABLE report_criteria (
    criteria_id serial NOT NULL,
    report_id integer NOT NULL,
    "owner" integer NOT NULL,
    subject character varying(512) NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    enabled boolean DEFAULT true
);



CREATE TABLE report_criteria_parameter (
    parameter_id serial NOT NULL,
    criteria_id integer NOT NULL,
    parameter character varying(255) NOT NULL,
    value text
);



CREATE TABLE report_queue (
    queue_id serial NOT NULL,
    report_id integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    processed timestamp(3) without time zone,
    status integer DEFAULT 0 NOT NULL,
    filename character varying(256),
    filesize integer DEFAULT -1,
    enabled boolean DEFAULT true
);



CREATE TABLE report_queue_criteria (
    criteria_id serial NOT NULL,
    queue_id integer NOT NULL,
    parameter character varying(255) NOT NULL,
    value text
);



CREATE SEQUENCE action_list_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE action_list (
    action_id integer DEFAULT nextval('action_list_code_seq'::text) NOT NULL,
    description character varying(255) NOT NULL,
    "owner" integer NOT NULL,
    completedate timestamp(3) without time zone,
    link_module_id integer NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enabled boolean DEFAULT true NOT NULL
);



CREATE SEQUENCE action_item_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE action_item (
    item_id integer DEFAULT nextval('action_item_code_seq'::text) NOT NULL,
    action_id integer NOT NULL,
    link_item_id integer NOT NULL,
    completedate timestamp(3) without time zone,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enabled boolean DEFAULT true NOT NULL
);



CREATE SEQUENCE action_item_log_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE action_item_log (
    log_id integer DEFAULT nextval('action_item_log_code_seq'::text) NOT NULL,
    item_id integer NOT NULL,
    link_item_id integer DEFAULT -1,
    "type" integer NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
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



CREATE TABLE database_version (
    version_id serial NOT NULL,
    script_filename character varying(255) NOT NULL,
    script_version character varying(255) NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
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
    modifiedby integer NOT NULL,
    trashed_date timestamp(3) without time zone
);



CREATE TABLE lookup_call_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_call_priority (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    weight integer NOT NULL
);



CREATE TABLE lookup_call_reminder (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    base_value integer DEFAULT 0 NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_call_result (
    result_id serial NOT NULL,
    description character varying(100) NOT NULL,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    next_required boolean DEFAULT false NOT NULL,
    next_days integer DEFAULT 0 NOT NULL,
    next_call_type_id integer,
    canceled_type boolean DEFAULT false NOT NULL
);



CREATE SEQUENCE lookup_opportunity_typ_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_opportunity_types (
    code integer DEFAULT nextval('lookup_opportunity_typ_code_seq'::text) NOT NULL,
    order_id integer,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_opportunity_env_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_opportunity_environment (
    code integer DEFAULT nextval('lookup_opportunity_env_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_opportunity_com_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_opportunity_competitors (
    code integer DEFAULT nextval('lookup_opportunity_com_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_opportunity_eve_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_opportunity_event_compelling (
    code integer DEFAULT nextval('lookup_opportunity_eve_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_opportunity_bud_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_opportunity_budget (
    code integer DEFAULT nextval('lookup_opportunity_bud_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE opportunity_header (
    opp_id serial NOT NULL,
    description character varying(80),
    acctlink integer,
    contactlink integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    trashed_date timestamp(3) without time zone
);



CREATE TABLE opportunity_component (
    id serial NOT NULL,
    opp_id integer,
    "owner" integer NOT NULL,
    description character varying(80),
    closedate timestamp(3) without time zone NOT NULL,
    closeprob double precision,
    terms double precision,
    units character(1),
    lowvalue double precision,
    guessvalue double precision,
    highvalue double precision,
    stage integer,
    stagedate timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    commission double precision,
    "type" character(1),
    alertdate timestamp(3) without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    closed timestamp(3) without time zone,
    alert character varying(100),
    enabled boolean DEFAULT true NOT NULL,
    notes text,
    alertdate_timezone character varying(255),
    closedate_timezone character varying(255),
    trashed_date timestamp(3) without time zone,
    environment integer,
    competitors integer,
    compelling_event integer,
    budget integer
);



CREATE TABLE opportunity_component_levels (
    opp_id integer NOT NULL,
    type_id integer NOT NULL,
    "level" integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);



CREATE TABLE call_log (
    call_id serial NOT NULL,
    org_id integer,
    contact_id integer,
    opp_id integer,
    call_type_id integer,
    length integer,
    subject character varying(255),
    notes text,
    followup_date timestamp(3) without time zone,
    alertdate timestamp(3) without time zone,
    followup_notes text,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    alert character varying(255),
    alert_call_type_id integer,
    parent_id integer,
    "owner" integer,
    assignedby integer,
    assign_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    completedby integer,
    complete_date timestamp(3) without time zone,
    result_id integer,
    priority_id integer,
    status_id integer DEFAULT 1 NOT NULL,
    reminder_value integer,
    reminder_type_id integer,
    alertdate_timezone character varying(255),
    trashed_date timestamp(3) without time zone
);



CREATE SEQUENCE lookup_project_activit_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_project_activity (
    code integer DEFAULT nextval('lookup_project_activit_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL,
    template_id integer DEFAULT 0
);



CREATE SEQUENCE lookup_project_priorit_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_project_priority (
    code integer DEFAULT nextval('lookup_project_priorit_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL,
    graphic character varying(75),
    "type" integer NOT NULL
);



CREATE TABLE lookup_project_status (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL,
    graphic character varying(75),
    "type" integer NOT NULL
);



CREATE TABLE lookup_project_loe (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    base_value integer DEFAULT 0 NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL
);



CREATE TABLE lookup_project_role (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL
);



CREATE SEQUENCE lookup_project_cat_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_project_category (
    code integer DEFAULT nextval('lookup_project_cat_code_seq'::text) NOT NULL,
    description character varying(80) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL
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



CREATE TABLE projects (
    project_id serial NOT NULL,
    group_id integer,
    department_id integer,
    template_id integer,
    title character varying(100) NOT NULL,
    shortdescription character varying(200) NOT NULL,
    requestedby character varying(50),
    requesteddept character varying(50),
    requestdate timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    approvaldate timestamp(3) without time zone,
    closedate timestamp(3) without time zone,
    "owner" integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    modifiedby integer NOT NULL,
    approvalby integer,
    category_id integer,
    portal boolean DEFAULT false NOT NULL,
    allow_guests boolean DEFAULT false NOT NULL,
    news_enabled boolean DEFAULT true NOT NULL,
    details_enabled boolean DEFAULT true NOT NULL,
    team_enabled boolean DEFAULT true NOT NULL,
    plan_enabled boolean DEFAULT true NOT NULL,
    lists_enabled boolean DEFAULT true NOT NULL,
    discussion_enabled boolean DEFAULT true NOT NULL,
    tickets_enabled boolean DEFAULT true NOT NULL,
    documents_enabled boolean DEFAULT true NOT NULL,
    news_label character varying(50),
    details_label character varying(50),
    team_label character varying(50),
    plan_label character varying(50),
    lists_label character varying(50),
    discussion_label character varying(50),
    tickets_label character varying(50),
    documents_label character varying(50),
    est_closedate timestamp(3) without time zone,
    budget double precision,
    budget_currency character varying(5),
    requestdate_timezone character varying(255),
    est_closedate_timezone character varying(255),
    portal_default boolean DEFAULT false NOT NULL,
    portal_header character varying(255),
    portal_format character varying(255),
    portal_key character varying(255),
    portal_build_news_body boolean DEFAULT false NOT NULL,
    portal_news_menu boolean DEFAULT false NOT NULL,
    description text,
    allows_user_observers boolean DEFAULT false NOT NULL,
    "level" integer DEFAULT 10 NOT NULL,
    portal_page_type integer,
    calendar_enabled boolean DEFAULT true NOT NULL,
    calendar_label character varying(50),
    accounts_enabled boolean DEFAULT true NOT NULL,
    accounts_label character varying(50),
    trashed_date timestamp(3) without time zone
);



CREATE SEQUENCE project_requi_requirement_i_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE project_requirements (
    requirement_id integer DEFAULT nextval('project_requi_requirement_i_seq'::text) NOT NULL,
    project_id integer NOT NULL,
    submittedby character varying(50),
    departmentby character varying(30),
    shortdescription character varying(255) NOT NULL,
    description text NOT NULL,
    datereceived timestamp(3) without time zone,
    estimated_loevalue integer,
    estimated_loetype integer,
    actual_loevalue integer,
    actual_loetype integer,
    deadline timestamp(3) without time zone,
    approvedby integer,
    approvaldate timestamp(3) without time zone,
    closedby integer,
    closedate timestamp(3) without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    startdate timestamp(3) without time zone,
    startdate_timezone character varying(255),
    deadline_timezone character varying(255),
    due_date_timezone character varying(255)
);



CREATE SEQUENCE project_assignmen_folder_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE project_assignments_folder (
    folder_id integer DEFAULT nextval('project_assignmen_folder_id_seq'::text) NOT NULL,
    parent_id integer,
    requirement_id integer NOT NULL,
    name character varying(255) NOT NULL,
    description text,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE SEQUENCE project_assig_assignment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE project_assignments (
    assignment_id integer DEFAULT nextval('project_assig_assignment_id_seq'::text) NOT NULL,
    project_id integer NOT NULL,
    requirement_id integer,
    assignedby integer,
    user_assign_id integer,
    technology character varying(50),
    role character varying(255),
    estimated_loevalue integer,
    estimated_loetype integer,
    actual_loevalue integer,
    actual_loetype integer,
    priority_id integer,
    assign_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    est_start_date timestamp(3) without time zone,
    start_date timestamp(3) without time zone,
    due_date timestamp(3) without time zone,
    status_id integer,
    status_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    complete_date timestamp(3) without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    folder_id integer,
    percent_complete integer,
    due_date_timezone character varying(255)
);



CREATE SEQUENCE project_assignmen_status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE project_assignments_status (
    status_id integer DEFAULT nextval('project_assignmen_status_id_seq'::text) NOT NULL,
    assignment_id integer NOT NULL,
    user_id integer NOT NULL,
    description text NOT NULL,
    status_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    percent_complete integer,
    project_status_id integer,
    user_assign_id integer
);



CREATE SEQUENCE project_issue_cate_categ_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE project_issues_categories (
    category_id integer DEFAULT nextval('project_issue_cate_categ_id_seq'::text) NOT NULL,
    project_id integer NOT NULL,
    subject character varying(255) NOT NULL,
    description text,
    enabled boolean DEFAULT true NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    topics_count integer DEFAULT 0 NOT NULL,
    posts_count integer DEFAULT 0 NOT NULL,
    last_post_date timestamp(3) without time zone,
    last_post_by integer,
    allow_files boolean DEFAULT false NOT NULL
);



CREATE TABLE project_issues (
    issue_id serial NOT NULL,
    project_id integer NOT NULL,
    subject character varying(255) NOT NULL,
    message text NOT NULL,
    importance integer DEFAULT 0,
    enabled boolean DEFAULT true,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    category_id integer,
    reply_count integer DEFAULT 0 NOT NULL,
    last_reply_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    last_reply_by integer
);



CREATE SEQUENCE project_issue_repl_reply_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE project_issue_replies (
    reply_id integer DEFAULT nextval('project_issue_repl_reply_id_seq'::text) NOT NULL,
    issue_id integer NOT NULL,
    reply_to integer DEFAULT 0,
    subject character varying(255) NOT NULL,
    message text NOT NULL,
    importance integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE project_folders (
    folder_id serial NOT NULL,
    link_module_id integer NOT NULL,
    link_item_id integer NOT NULL,
    subject character varying(255) NOT NULL,
    description text,
    parent_id integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    display integer
);



CREATE TABLE project_files (
    item_id serial NOT NULL,
    link_module_id integer NOT NULL,
    link_item_id integer NOT NULL,
    folder_id integer,
    client_filename character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    subject character varying(500) NOT NULL,
    size integer DEFAULT 0,
    "version" double precision DEFAULT 0,
    enabled boolean DEFAULT true,
    downloads integer DEFAULT 0,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    default_file boolean DEFAULT false
);



CREATE TABLE project_files_version (
    item_id integer,
    client_filename character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    subject character varying(500) NOT NULL,
    size integer DEFAULT 0,
    "version" double precision DEFAULT 0,
    enabled boolean DEFAULT true,
    downloads integer DEFAULT 0,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE project_files_download (
    item_id integer NOT NULL,
    "version" double precision DEFAULT 0,
    user_download_id integer,
    download_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);



CREATE TABLE project_files_thumbnail (
    item_id integer,
    filename character varying(255) NOT NULL,
    size integer DEFAULT 0,
    "version" double precision DEFAULT 0,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE project_team (
    project_id integer NOT NULL,
    user_id integer NOT NULL,
    userlevel integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    modifiedby integer NOT NULL,
    status integer,
    last_accessed timestamp(3) without time zone
);



CREATE TABLE project_news_category (
    category_id serial NOT NULL,
    project_id integer NOT NULL,
    category_name character varying(255),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    "level" integer DEFAULT 0 NOT NULL,
    enabled boolean DEFAULT true
);



CREATE TABLE project_news (
    news_id serial NOT NULL,
    project_id integer NOT NULL,
    category_id integer,
    subject character varying(255) NOT NULL,
    intro text,
    message text,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    modifiedby integer NOT NULL,
    start_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    end_date timestamp(3) without time zone,
    allow_replies boolean DEFAULT false,
    allow_rating boolean DEFAULT false,
    rating_count integer DEFAULT 0 NOT NULL,
    avg_rating double precision DEFAULT 0,
    priority_id integer DEFAULT 10,
    read_count integer DEFAULT 0 NOT NULL,
    enabled boolean DEFAULT true,
    status integer,
    html boolean DEFAULT true NOT NULL,
    start_date_timezone character varying(255),
    end_date_timezone character varying(255),
    classification_id integer NOT NULL,
    template_id integer
);



CREATE TABLE project_requirements_map (
    map_id serial NOT NULL,
    project_id integer NOT NULL,
    requirement_id integer NOT NULL,
    "position" integer NOT NULL,
    indent integer DEFAULT 0 NOT NULL,
    folder_id integer,
    assignment_id integer
);



CREATE TABLE lookup_project_permission_category (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL
);



CREATE TABLE lookup_project_permission (
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



CREATE TABLE project_permissions (
    id serial NOT NULL,
    project_id integer NOT NULL,
    permission_id integer NOT NULL,
    userlevel integer NOT NULL
);



CREATE TABLE project_accounts (
    id serial NOT NULL,
    project_id integer NOT NULL,
    org_id integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone
);



CREATE TABLE lookup_currency (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_product_categor_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
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



CREATE SEQUENCE lookup_product_manufac_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_product_manufacturer (
    code integer DEFAULT nextval('lookup_product_manufac_code_seq'::text) NOT NULL,
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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_product_shipping (
    code integer DEFAULT nextval('lookup_product_shippin_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_product_ship_ti_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
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
    enabled boolean DEFAULT true NOT NULL,
    manufacturer_id integer,
    trashed_date timestamp(3) without time zone,
    active boolean DEFAULT true NOT NULL
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
    expiration_date timestamp(3) without time zone,
    enabled boolean DEFAULT false,
    cost_currency integer,
    cost_amount double precision DEFAULT 0 NOT NULL
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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
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
    result_type integer NOT NULL,
    configurator_name character varying(300) NOT NULL
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
    enabled boolean DEFAULT false,
    option_name character varying(300),
    has_range boolean DEFAULT false,
    has_multiplier boolean DEFAULT false
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
    recurring_type integer,
    enabled boolean DEFAULT true,
    value double precision DEFAULT 0,
    multiplier double precision DEFAULT 1,
    range_min integer DEFAULT 1,
    range_max integer DEFAULT 1,
    cost_currency integer,
    cost_amount double precision DEFAULT 0 NOT NULL
);



CREATE TABLE product_option_map (
    product_option_id serial NOT NULL,
    product_id integer NOT NULL,
    option_id integer NOT NULL
);



CREATE TABLE product_option_boolean (
    product_option_id integer NOT NULL,
    value boolean NOT NULL,
    id integer
);



CREATE TABLE product_option_float (
    product_option_id integer NOT NULL,
    value double precision NOT NULL,
    id integer
);



CREATE TABLE product_option_timestamp (
    product_option_id integer NOT NULL,
    value timestamp(3) without time zone NOT NULL,
    id integer
);



CREATE TABLE product_option_integer (
    product_option_id integer NOT NULL,
    value integer NOT NULL,
    id integer
);



CREATE TABLE product_option_text (
    product_option_id integer NOT NULL,
    value text NOT NULL,
    id integer
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
    total_hours_remaining double precision,
    service_model_notes text,
    initial_start_date_timezone character varying(255),
    current_start_date_timezone character varying(255),
    current_end_date_timezone character varying(255),
    trashed_date timestamp(3) without time zone
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
    parent_cat_code integer DEFAULT 0 NOT NULL,
    description character varying(300) NOT NULL,
    full_description text DEFAULT ''::text NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE asset_category_draft (
    id serial NOT NULL,
    link_id integer DEFAULT -1,
    cat_level integer DEFAULT 0 NOT NULL,
    parent_cat_code integer DEFAULT 0 NOT NULL,
    description character varying(300) NOT NULL,
    full_description text DEFAULT ''::text NOT NULL,
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
    purchase_cost double precision,
    date_listed_timezone character varying(255),
    expiration_date_timezone character varying(255),
    purchase_date_timezone character varying(255),
    trashed_date timestamp(3) without time zone
);



CREATE TABLE ticket_level (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE ticket_severity (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    style text DEFAULT ''::text NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_ticketsource (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_ticket_status (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE ticket_priority (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    style text DEFAULT ''::text NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE ticket_category (
    id serial NOT NULL,
    cat_level integer DEFAULT 0 NOT NULL,
    parent_cat_code integer DEFAULT 0 NOT NULL,
    description character varying(300) NOT NULL,
    full_description text DEFAULT ''::text NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE ticket_category_draft (
    id serial NOT NULL,
    link_id integer DEFAULT -1,
    cat_level integer DEFAULT 0 NOT NULL,
    parent_cat_code integer DEFAULT 0 NOT NULL,
    description character varying(300) NOT NULL,
    full_description text DEFAULT ''::text NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE ticket (
    ticketid serial NOT NULL,
    org_id integer,
    contact_id integer,
    problem text NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    closed timestamp without time zone,
    pri_code integer,
    level_code integer,
    department_code integer,
    source_code integer,
    cat_code integer,
    subcat_code1 integer,
    subcat_code2 integer,
    subcat_code3 integer,
    assigned_to integer,
    "comment" text,
    solution text,
    scode integer,
    critical timestamp without time zone,
    notified timestamp without time zone,
    custom_data text,
    "location" character varying(256),
    assigned_date timestamp(3) without time zone,
    est_resolution_date timestamp(3) without time zone,
    resolution_date timestamp(3) without time zone,
    cause text,
    link_contract_id integer,
    link_asset_id integer,
    product_id integer,
    customer_product_id integer,
    expectation integer,
    key_count integer,
    est_resolution_date_timezone character varying(255),
    assigned_date_timezone character varying(255),
    resolution_date_timezone character varying(255),
    status_id integer,
    trashed_date timestamp(3) without time zone
);



CREATE TABLE project_ticket_count (
    project_id integer NOT NULL,
    key_count integer DEFAULT 0 NOT NULL
);



CREATE TABLE ticketlog (
    id serial NOT NULL,
    ticketid integer,
    assigned_to integer,
    "comment" text,
    closed boolean,
    pri_code integer,
    level_code integer,
    department_code integer,
    cat_code integer,
    scode integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



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
    labor_towards_sc boolean DEFAULT true,
    alert_date_timezone character varying(255)
);



CREATE TABLE ticket_activity_item (
    item_id serial NOT NULL,
    link_form_id integer,
    activity_date timestamp(3) without time zone,
    description text,
    travel_hours integer,
    travel_minutes integer,
    labor_hours integer,
    labor_minutes integer,
    activity_date_timezone character varying(255)
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



CREATE TABLE ticketlink_project (
    ticket_id integer NOT NULL,
    project_id integer NOT NULL
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
    org_id integer,
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
    customer_product_id integer,
    opp_id integer,
    "version" character varying(255) DEFAULT '0'::character varying NOT NULL,
    group_id integer NOT NULL,
    delivery_id integer,
    email_address text,
    phone_number text,
    address text,
    fax_number text,
    submit_action integer,
    closed timestamp(3) without time zone,
    show_total boolean DEFAULT true,
    show_subtotal boolean DEFAULT true,
    logo_file_id integer,
    trashed_date timestamp(3) without time zone
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
    status_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    estimated_delivery text,
    "comment" character varying(300)
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
    value boolean NOT NULL,
    id integer
);



CREATE TABLE quote_product_option_float (
    quote_product_option_id integer,
    value double precision NOT NULL,
    id integer
);



CREATE TABLE quote_product_option_timestamp (
    quote_product_option_id integer,
    value timestamp(3) without time zone NOT NULL,
    id integer
);



CREATE TABLE quote_product_option_integer (
    quote_product_option_id integer,
    value integer NOT NULL,
    id integer
);



CREATE TABLE quote_product_option_text (
    quote_product_option_id integer,
    value text NOT NULL,
    id integer
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
    modifiedby integer NOT NULL,
    submitted timestamp(3) without time zone
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
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_creditcard_types (
    code integer DEFAULT nextval('lookup_creditcard_type_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE payment_creditcard (
    creditcard_id serial NOT NULL,
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
    modifiedby integer NOT NULL,
    order_id integer
);



CREATE TABLE payment_eft (
    bank_id serial NOT NULL,
    bank_name character varying(300),
    routing_number character varying(300),
    account_number character varying(300),
    name_on_account character varying(300),
    company_name_on_account character varying(300),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    order_id integer
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
    order_id integer NOT NULL,
    product_start_date timestamp(3) without time zone,
    product_end_date timestamp(3) without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    order_item_id integer NOT NULL
);



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



CREATE SEQUENCE module_field_categorylin_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE module_field_categorylink (
    id integer DEFAULT nextval('module_field_categorylin_id_seq'::text) NOT NULL,
    module_id integer NOT NULL,
    category_id integer NOT NULL,
    "level" integer DEFAULT 0,
    description text,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone
);



CREATE SEQUENCE custom_field_ca_category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE custom_field_category (
    module_id integer NOT NULL,
    category_id integer DEFAULT nextval('custom_field_ca_category_id_seq'::text) NOT NULL,
    category_name character varying(255) NOT NULL,
    "level" integer DEFAULT 0,
    description text,
    start_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    end_date timestamp(3) without time zone,
    default_item boolean DEFAULT false,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enabled boolean DEFAULT true,
    multiple_records boolean DEFAULT false,
    read_only boolean DEFAULT false
);



CREATE SEQUENCE custom_field_group_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE custom_field_group (
    category_id integer NOT NULL,
    group_id integer DEFAULT nextval('custom_field_group_group_id_seq'::text) NOT NULL,
    group_name character varying(255) NOT NULL,
    "level" integer DEFAULT 0,
    description text,
    start_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    end_date timestamp(3) without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enabled boolean DEFAULT true
);



CREATE TABLE custom_field_info (
    group_id integer NOT NULL,
    field_id serial NOT NULL,
    field_name character varying(255) NOT NULL,
    "level" integer DEFAULT 0,
    field_type integer NOT NULL,
    validation_type integer DEFAULT 0,
    required boolean DEFAULT false,
    parameters text,
    start_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    end_date timestamp(3) without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    enabled boolean DEFAULT true,
    additional_text character varying(255)
);



CREATE TABLE custom_field_lookup (
    field_id integer NOT NULL,
    code serial NOT NULL,
    description character varying(255) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    start_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    end_date timestamp without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE custom_field_reco_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE custom_field_record (
    link_module_id integer NOT NULL,
    link_item_id integer NOT NULL,
    category_id integer NOT NULL,
    record_id integer DEFAULT nextval('custom_field_reco_record_id_seq'::text) NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    enabled boolean DEFAULT true
);



CREATE TABLE custom_field_data (
    record_id integer NOT NULL,
    field_id integer NOT NULL,
    selected_item_id integer DEFAULT 0,
    entered_value text,
    entered_number integer,
    entered_float double precision
);



CREATE TABLE saved_criterialist (
    id serial NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    "owner" integer NOT NULL,
    name character varying(80) NOT NULL,
    contact_source integer DEFAULT -1,
    enabled boolean DEFAULT true NOT NULL
);



CREATE TABLE campaign (
    campaign_id serial NOT NULL,
    name character varying(80) NOT NULL,
    description character varying(255),
    list_id integer,
    message_id integer DEFAULT -1,
    reply_addr character varying(255),
    subject character varying(255),
    message text,
    status_id integer DEFAULT 0,
    status character varying(255),
    active boolean DEFAULT false,
    active_date timestamp(3) without time zone,
    send_method_id integer DEFAULT -1 NOT NULL,
    inactive_date timestamp(3) without time zone,
    approval_date timestamp(3) without time zone,
    approvedby integer,
    enabled boolean DEFAULT true NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    "type" integer DEFAULT 1,
    active_date_timezone character varying(255),
    cc character varying(1024),
    bcc character varying(1024),
    trashed_date timestamp(3) without time zone
);



CREATE TABLE campaign_run (
    id serial NOT NULL,
    campaign_id integer NOT NULL,
    status integer DEFAULT 0 NOT NULL,
    run_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    total_contacts integer DEFAULT 0,
    total_sent integer DEFAULT 0,
    total_replied integer DEFAULT 0,
    total_bounced integer DEFAULT 0
);



CREATE TABLE excluded_recipient (
    id serial NOT NULL,
    campaign_id integer NOT NULL,
    contact_id integer NOT NULL
);



CREATE TABLE campaign_list_groups (
    campaign_id integer NOT NULL,
    group_id integer NOT NULL
);



CREATE TABLE active_campaign_groups (
    id serial NOT NULL,
    campaign_id integer NOT NULL,
    groupname character varying(80) NOT NULL,
    groupcriteria text
);



CREATE TABLE scheduled_recipient (
    id serial NOT NULL,
    campaign_id integer NOT NULL,
    contact_id integer NOT NULL,
    run_id integer DEFAULT -1,
    status_id integer DEFAULT 0,
    status character varying(255),
    status_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    scheduled_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    sent_date timestamp(3) without time zone,
    reply_date timestamp(3) without time zone,
    bounce_date timestamp(3) without time zone
);



CREATE TABLE lookup_survey_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE survey (
    survey_id serial NOT NULL,
    name character varying(80) NOT NULL,
    description character varying(255),
    intro text,
    outro text,
    itemlength integer DEFAULT -1,
    "type" integer DEFAULT -1,
    enabled boolean DEFAULT true NOT NULL,
    status integer DEFAULT -1 NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE campaign_survey_link (
    campaign_id integer,
    survey_id integer
);



CREATE SEQUENCE survey_question_question_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE survey_questions (
    question_id integer DEFAULT nextval('survey_question_question_id_seq'::text) NOT NULL,
    survey_id integer NOT NULL,
    "type" integer NOT NULL,
    description character varying(255),
    required boolean DEFAULT false NOT NULL,
    "position" integer DEFAULT 0 NOT NULL
);



CREATE TABLE survey_items (
    item_id serial NOT NULL,
    question_id integer NOT NULL,
    "type" integer DEFAULT -1,
    description character varying(255)
);



CREATE SEQUENCE active_survey_active_survey_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE active_survey (
    active_survey_id integer DEFAULT nextval('active_survey_active_survey_seq'::text) NOT NULL,
    campaign_id integer NOT NULL,
    name character varying(80) NOT NULL,
    description character varying(255),
    intro text,
    outro text,
    itemlength integer DEFAULT -1,
    "type" integer NOT NULL,
    enabled boolean DEFAULT true NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE SEQUENCE active_survey_q_question_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE active_survey_questions (
    question_id integer DEFAULT nextval('active_survey_q_question_id_seq'::text) NOT NULL,
    active_survey_id integer,
    "type" integer NOT NULL,
    description character varying(255),
    required boolean DEFAULT false NOT NULL,
    "position" integer DEFAULT 0 NOT NULL,
    average double precision DEFAULT 0.00,
    total1 integer DEFAULT 0,
    total2 integer DEFAULT 0,
    total3 integer DEFAULT 0,
    total4 integer DEFAULT 0,
    total5 integer DEFAULT 0,
    total6 integer DEFAULT 0,
    total7 integer DEFAULT 0
);



CREATE SEQUENCE active_survey_items_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE active_survey_items (
    item_id integer DEFAULT nextval('active_survey_items_item_id_seq'::text) NOT NULL,
    question_id integer NOT NULL,
    "type" integer DEFAULT -1,
    description character varying(255)
);



CREATE SEQUENCE active_survey_r_response_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE active_survey_responses (
    response_id integer DEFAULT nextval('active_survey_r_response_id_seq'::text) NOT NULL,
    active_survey_id integer NOT NULL,
    contact_id integer DEFAULT -1 NOT NULL,
    unique_code character varying(255),
    ip_address character varying(15) NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    address_updated integer
);



CREATE SEQUENCE active_survey_ans_answer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE active_survey_answers (
    answer_id integer DEFAULT nextval('active_survey_ans_answer_id_seq'::text) NOT NULL,
    response_id integer NOT NULL,
    question_id integer NOT NULL,
    comments text,
    quant_ans integer DEFAULT -1,
    text_ans text
);



CREATE SEQUENCE active_survey_answer_ite_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE active_survey_answer_items (
    id integer DEFAULT nextval('active_survey_answer_ite_id_seq'::text) NOT NULL,
    item_id integer NOT NULL,
    answer_id integer NOT NULL,
    comments text
);



CREATE SEQUENCE active_survey_answer_avg_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE active_survey_answer_avg (
    id integer DEFAULT nextval('active_survey_answer_avg_id_seq'::text) NOT NULL,
    question_id integer NOT NULL,
    item_id integer NOT NULL,
    total integer DEFAULT 0 NOT NULL
);



CREATE TABLE field_types (
    id serial NOT NULL,
    data_typeid integer DEFAULT -1 NOT NULL,
    data_type character varying(20),
    "operator" character varying(50),
    display_text character varying(50),
    enabled boolean DEFAULT true NOT NULL
);



CREATE TABLE search_fields (
    id serial NOT NULL,
    field character varying(80),
    description character varying(255),
    searchable boolean DEFAULT true NOT NULL,
    field_typeid integer DEFAULT -1 NOT NULL,
    table_name character varying(80),
    object_class character varying(80),
    enabled boolean DEFAULT true
);



CREATE TABLE message (
    id serial NOT NULL,
    name character varying(80) NOT NULL,
    description character varying(255),
    template_id integer,
    subject character varying(255),
    body text,
    reply_addr character varying(100),
    url character varying(255),
    img character varying(80),
    enabled boolean DEFAULT true NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    access_type integer
);



CREATE TABLE message_template (
    id serial NOT NULL,
    name character varying(80) NOT NULL,
    description character varying(255),
    template_file character varying(80),
    num_imgs integer,
    num_urls integer,
    enabled boolean DEFAULT true NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE saved_criteriaelement (
    id integer NOT NULL,
    field integer NOT NULL,
    "operator" character varying(50) NOT NULL,
    operatorid integer NOT NULL,
    value character varying(80) NOT NULL,
    source integer DEFAULT -1 NOT NULL,
    value_id integer
);



CREATE TABLE help_module (
    module_id serial NOT NULL,
    category_id integer,
    module_brief_description text,
    module_detail_description text
);



CREATE TABLE help_contents (
    help_id serial NOT NULL,
    category_id integer,
    link_module_id integer,
    module character varying(255),
    section character varying(255),
    subsection character varying(255),
    title character varying(255),
    description text,
    nextcontent integer,
    prevcontent integer,
    upcontent integer,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enabled boolean DEFAULT true
);



CREATE TABLE help_tableof_contents (
    content_id serial NOT NULL,
    displaytext character varying(255),
    firstchild integer,
    nextsibling integer,
    parent integer,
    category_id integer,
    contentlevel integer NOT NULL,
    contentorder integer NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enabled boolean DEFAULT true
);



CREATE TABLE help_tableofcontentitem_links (
    link_id serial NOT NULL,
    global_link_id integer NOT NULL,
    linkto_content_id integer NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_help_features (
    code serial NOT NULL,
    description character varying(1000) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE help_features (
    feature_id serial NOT NULL,
    link_help_id integer NOT NULL,
    link_feature_id integer,
    description character varying(1000) NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    completedate timestamp(3) without time zone,
    completedby integer,
    enabled boolean DEFAULT true NOT NULL,
    "level" integer DEFAULT 0
);



CREATE TABLE help_related_links (
    relatedlink_id serial NOT NULL,
    owning_module_id integer,
    linkto_content_id integer,
    displaytext character varying(255) NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enabled boolean DEFAULT true NOT NULL
);



CREATE TABLE help_faqs (
    faq_id serial NOT NULL,
    owning_module_id integer NOT NULL,
    question character varying(1000) NOT NULL,
    answer character varying(1000) NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    completedate timestamp(3) without time zone,
    completedby integer,
    enabled boolean DEFAULT true NOT NULL
);



CREATE TABLE help_business_rules (
    rule_id serial NOT NULL,
    link_help_id integer NOT NULL,
    description character varying(1000) NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    completedate timestamp(3) without time zone,
    completedby integer,
    enabled boolean DEFAULT true NOT NULL
);



CREATE TABLE help_notes (
    note_id serial NOT NULL,
    link_help_id integer NOT NULL,
    description character varying(1000) NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    completedate timestamp(3) without time zone,
    completedby integer,
    enabled boolean DEFAULT true NOT NULL
);



CREATE TABLE help_tips (
    tip_id serial NOT NULL,
    link_help_id integer NOT NULL,
    description character varying(1000) NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enabled boolean DEFAULT true NOT NULL
);



CREATE TABLE sync_client (
    client_id serial NOT NULL,
    "type" character varying(100),
    "version" character varying(50),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    anchor timestamp(3) without time zone,
    enabled boolean DEFAULT false,
    code character varying(255)
);



CREATE TABLE sync_system (
    system_id serial NOT NULL,
    application_name character varying(255),
    enabled boolean DEFAULT true
);



CREATE TABLE sync_table (
    table_id serial NOT NULL,
    system_id integer NOT NULL,
    element_name character varying(255),
    mapped_class_name character varying(255),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    create_statement text,
    order_id integer DEFAULT -1,
    sync_item boolean DEFAULT false,
    object_key character varying(50)
);



CREATE TABLE sync_map (
    client_id integer NOT NULL,
    table_id integer NOT NULL,
    record_id integer NOT NULL,
    cuid integer NOT NULL,
    complete boolean DEFAULT false,
    status_date timestamp(3) without time zone
);



CREATE TABLE sync_conflict_log (
    client_id integer NOT NULL,
    table_id integer NOT NULL,
    record_id integer NOT NULL,
    status_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);



CREATE TABLE sync_log (
    log_id serial NOT NULL,
    system_id integer NOT NULL,
    client_id integer NOT NULL,
    ip character varying(15),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);



CREATE SEQUENCE sync_transact_transaction_i_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE sync_transaction_log (
    transaction_id integer DEFAULT nextval('sync_transact_transaction_i_seq'::text) NOT NULL,
    log_id integer NOT NULL,
    reference_id character varying(50),
    element_name character varying(255),
    "action" character varying(20),
    link_item_id integer,
    status_code integer,
    record_count integer,
    message text
);



CREATE TABLE process_log (
    process_id serial NOT NULL,
    system_id integer NOT NULL,
    client_id integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    process_name character varying(255),
    process_version character varying(20),
    status integer,
    message text
);



CREATE TABLE autoguide_make (
    make_id serial NOT NULL,
    make_name character varying(30),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE autoguide_model (
    model_id serial NOT NULL,
    make_id integer NOT NULL,
    model_name character varying(50),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE SEQUENCE autoguide_vehicl_vehicle_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE autoguide_vehicle (
    vehicle_id integer DEFAULT nextval('autoguide_vehicl_vehicle_id_seq'::text) NOT NULL,
    "year" character varying(4) NOT NULL,
    make_id integer NOT NULL,
    model_id integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE SEQUENCE autoguide_inve_inventory_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE autoguide_inventory (
    inventory_id integer DEFAULT nextval('autoguide_inve_inventory_id_seq'::text) NOT NULL,
    vehicle_id integer NOT NULL,
    account_id integer,
    vin character varying(20),
    mileage character varying(20),
    is_new boolean DEFAULT false,
    condition character varying(20),
    comments character varying(255),
    stock_no character varying(20),
    ext_color character varying(20),
    int_color character varying(20),
    style character varying(40),
    invoice_price double precision,
    selling_price double precision,
    selling_price_text character varying(100),
    sold boolean DEFAULT false,
    status character varying(20),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE SEQUENCE autoguide_options_option_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE autoguide_options (
    option_id integer DEFAULT nextval('autoguide_options_option_id_seq'::text) NOT NULL,
    option_name character varying(20) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);



CREATE TABLE autoguide_inventory_options (
    inventory_id integer NOT NULL,
    option_id integer NOT NULL
);



CREATE TABLE autoguide_ad_run (
    ad_run_id serial NOT NULL,
    inventory_id integer NOT NULL,
    run_date timestamp(3) without time zone NOT NULL,
    ad_type character varying(20),
    include_photo boolean DEFAULT false,
    complete_date timestamp(3) without time zone,
    completedby integer DEFAULT -1,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE SEQUENCE autoguide_ad_run_types_code_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE autoguide_ad_run_types (
    code integer DEFAULT nextval('autoguide_ad_run_types_code_seq'::text) NOT NULL,
    description character varying(20) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT false,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);



CREATE TABLE lookup_revenue_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_revenuedetail_t_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_revenuedetail_types (
    code integer DEFAULT nextval('lookup_revenuedetail_t_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE revenue (
    id serial NOT NULL,
    org_id integer,
    transaction_id integer DEFAULT -1,
    "month" integer DEFAULT -1,
    "year" integer DEFAULT -1,
    amount double precision DEFAULT 0,
    "type" integer,
    "owner" integer,
    description character varying(255),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE revenue_detail (
    id serial NOT NULL,
    revenue_id integer,
    amount double precision DEFAULT 0,
    "type" integer,
    "owner" integer,
    description character varying(255),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE TABLE lookup_task_priority (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_task_loe (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_task_category (
    code serial NOT NULL,
    description character varying(255) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE task (
    task_id serial NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    priority integer NOT NULL,
    description character varying(255),
    duedate timestamp(3) without time zone,
    reminderid integer,
    notes text,
    sharing integer NOT NULL,
    complete boolean DEFAULT false NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer,
    estimatedloe double precision,
    estimatedloetype integer,
    "type" integer DEFAULT 1,
    "owner" integer,
    completedate timestamp(3) without time zone,
    category_id integer,
    duedate_timezone character varying(255),
    trashed_date timestamp(3) without time zone
);



CREATE TABLE tasklink_contact (
    task_id integer NOT NULL,
    contact_id integer NOT NULL,
    notes text
);



CREATE TABLE tasklink_ticket (
    task_id integer NOT NULL,
    ticket_id integer NOT NULL
);



CREATE TABLE tasklink_project (
    task_id integer NOT NULL,
    project_id integer NOT NULL
);



CREATE TABLE taskcategory_project (
    category_id integer NOT NULL,
    project_id integer NOT NULL
);



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
    modifiedby integer NOT NULL,
    trashed_date timestamp(3) without time zone
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



CREATE SEQUENCE business_process_com_lb_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE business_process_component_library (
    component_id integer DEFAULT nextval('business_process_com_lb_id_seq'::text) NOT NULL,
    component_name character varying(255) NOT NULL,
    type_id integer NOT NULL,
    class_name character varying(255) NOT NULL,
    description character varying(510),
    enabled boolean DEFAULT true NOT NULL
);



CREATE SEQUENCE business_process_comp_re_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE business_process_component_result_lookup (
    result_id integer DEFAULT nextval('business_process_comp_re_id_seq'::text) NOT NULL,
    component_id integer NOT NULL,
    return_id integer NOT NULL,
    description character varying(255),
    "level" integer DEFAULT 0 NOT NULL,
    enabled boolean DEFAULT true NOT NULL
);



CREATE SEQUENCE business_process_pa_lib_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE business_process_parameter_library (
    parameter_id integer DEFAULT nextval('business_process_pa_lib_id_seq'::text) NOT NULL,
    component_id integer,
    param_name character varying(255),
    description character varying(510),
    default_value character varying(4000),
    enabled boolean DEFAULT true NOT NULL
);



CREATE TABLE business_process (
    process_id serial NOT NULL,
    process_name character varying(255) NOT NULL,
    description character varying(510),
    type_id integer NOT NULL,
    link_module_id integer NOT NULL,
    component_start_id integer,
    enabled boolean DEFAULT true NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);



CREATE SEQUENCE business_process_compone_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE business_process_component (
    id integer DEFAULT nextval('business_process_compone_id_seq'::text) NOT NULL,
    process_id integer NOT NULL,
    component_id integer NOT NULL,
    parent_id integer,
    parent_result_id integer,
    enabled boolean DEFAULT true NOT NULL
);



CREATE SEQUENCE business_process_param_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE business_process_parameter (
    id integer DEFAULT nextval('business_process_param_id_seq'::text) NOT NULL,
    process_id integer NOT NULL,
    param_name character varying(255),
    param_value character varying(4000),
    enabled boolean DEFAULT true NOT NULL
);



CREATE SEQUENCE business_process_comp_pa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE business_process_component_parameter (
    id integer DEFAULT nextval('business_process_comp_pa_id_seq'::text) NOT NULL,
    component_id integer NOT NULL,
    parameter_id integer NOT NULL,
    param_value character varying(4000),
    enabled boolean DEFAULT true NOT NULL
);



CREATE SEQUENCE business_process_e_event_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE business_process_events (
    event_id integer DEFAULT nextval('business_process_e_event_id_seq'::text) NOT NULL,
    "second" character varying(64) DEFAULT '0'::character varying,
    "minute" character varying(64) DEFAULT '*'::character varying,
    "hour" character varying(64) DEFAULT '*'::character varying,
    dayofmonth character varying(64) DEFAULT '*'::character varying,
    "month" character varying(64) DEFAULT '*'::character varying,
    dayofweek character varying(64) DEFAULT '*'::character varying,
    "year" character varying(64) DEFAULT '*'::character varying,
    task character varying(255),
    extrainfo character varying(255),
    businessdays character varying(6) DEFAULT 'true'::character varying,
    enabled boolean DEFAULT false,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    process_id integer NOT NULL
);



CREATE TABLE business_process_log (
    process_name character varying(255) NOT NULL,
    anchor timestamp(3) without time zone NOT NULL
);



CREATE SEQUENCE business_process_hl_hook_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE business_process_hook_library (
    hook_id integer DEFAULT nextval('business_process_hl_hook_id_seq'::text) NOT NULL,
    link_module_id integer NOT NULL,
    hook_class character varying(255) NOT NULL,
    enabled boolean DEFAULT false
);



CREATE SEQUENCE business_process_ho_trig_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE business_process_hook_triggers (
    trigger_id integer DEFAULT nextval('business_process_ho_trig_id_seq'::text) NOT NULL,
    action_type_id integer NOT NULL,
    hook_id integer NOT NULL,
    enabled boolean DEFAULT false
);



CREATE SEQUENCE business_process_ho_hook_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE business_process_hook (
    id integer DEFAULT nextval('business_process_ho_hook_id_seq'::text) NOT NULL,
    trigger_id integer NOT NULL,
    process_id integer NOT NULL,
    enabled boolean DEFAULT false,
    priority integer DEFAULT 0 NOT NULL
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



CREATE TABLE quote_notes (
    notes_id serial NOT NULL,
    quote_id integer,
    notes text,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);



CREATE TABLE history (
    history_id serial NOT NULL,
    contact_id integer,
    org_id integer,
    link_object_id integer NOT NULL,
    link_item_id integer,
    status character varying(255),
    "type" character varying(255),
    description text,
    "level" integer DEFAULT 10,
    enabled boolean DEFAULT true,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE INDEX orglist_name ON organization USING btree (name);



CREATE INDEX contact_user_id_idx ON contact USING btree (user_id);



CREATE INDEX contactlist_namecompany ON contact USING btree (namelast, namefirst, company);



CREATE INDEX contactlist_company ON contact USING btree (company, namelast, namefirst);



CREATE INDEX contact_import_id_idx ON contact USING btree (import_id);



CREATE INDEX contact_address_contact_id_idx ON contact_address USING btree (contact_id);



CREATE INDEX contact_email_contact_id_idx ON contact_emailaddress USING btree (contact_id);



CREATE INDEX contact_phone_contact_id_idx ON contact_phone USING btree (contact_id);



CREATE INDEX import_entered_idx ON import USING btree (entered);



CREATE INDEX import_name_idx ON import USING btree (name);



CREATE INDEX oppcomplist_closedate ON opportunity_component USING btree (closedate);



CREATE INDEX oppcomplist_description ON opportunity_component USING btree (description);



CREATE INDEX call_log_cidx ON call_log USING btree (alertdate, enteredby);



CREATE INDEX call_log_entered_idx ON call_log USING btree (entered);



CREATE INDEX call_contact_id_idx ON call_log USING btree (contact_id);



CREATE INDEX call_org_id_idx ON call_log USING btree (org_id);



CREATE INDEX call_opp_id_idx ON call_log USING btree (opp_id);



CREATE INDEX projects_idx ON projects USING btree (group_id, project_id);



CREATE INDEX project_assignments_cidx ON project_assignments USING btree (complete_date, user_assign_id);



CREATE INDEX proj_assign_req_id_idx ON project_assignments USING btree (requirement_id);



CREATE INDEX project_files_cidx ON project_files USING btree (link_module_id, link_item_id);



CREATE UNIQUE INDEX project_team_uni_idx ON project_team USING btree (project_id, user_id);



CREATE INDEX proj_req_map_pr_req_pos_idx ON project_requirements_map USING btree (project_id, requirement_id, "position");



CREATE INDEX proj_acct_project_idx ON project_accounts USING btree (project_id);



CREATE INDEX proj_acct_org_idx ON project_accounts USING btree (org_id);



CREATE UNIQUE INDEX idx_pr_opt_val ON product_option_values USING btree (value_id, option_id, result_id);



CREATE UNIQUE INDEX idx_pr_key_map ON product_keyword_map USING btree (product_id, keyword_id);



CREATE INDEX ticket_cidx ON ticket USING btree (assigned_to, closed);



CREATE INDEX ticketlist_entered ON ticket USING btree (entered);



CREATE INDEX ticket_problem_idx ON ticket USING btree (problem);



CREATE INDEX ticket_comment_idx ON ticket USING btree ("comment");



CREATE INDEX ticket_solution_idx ON ticket USING btree (solution);



CREATE INDEX custom_field_cat_idx ON custom_field_category USING btree (module_id);



CREATE INDEX custom_field_grp_idx ON custom_field_group USING btree (category_id);



CREATE INDEX custom_field_inf_idx ON custom_field_info USING btree (group_id);



CREATE INDEX custom_field_rec_idx ON custom_field_record USING btree (link_module_id, link_item_id, category_id);



CREATE INDEX custom_field_dat_idx ON custom_field_data USING btree (record_id, field_id);



CREATE UNIQUE INDEX idx_sync_map ON sync_map USING btree (client_id, table_id, record_id);



CREATE UNIQUE INDEX idx_autog_inv_opt ON autoguide_inventory_options USING btree (inventory_id, option_id);



ALTER TABLE ONLY "access"
    ADD CONSTRAINT access_pkey PRIMARY KEY (user_id);



ALTER TABLE ONLY lookup_industry
    ADD CONSTRAINT lookup_industry_pkey PRIMARY KEY (code);



ALTER TABLE ONLY access_log
    ADD CONSTRAINT access_log_pkey PRIMARY KEY (id);



ALTER TABLE ONLY usage_log
    ADD CONSTRAINT usage_log_pkey PRIMARY KEY (usage_id);



ALTER TABLE ONLY lookup_contact_types
    ADD CONSTRAINT lookup_contact_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_account_types
    ADD CONSTRAINT lookup_account_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY state
    ADD CONSTRAINT state_pkey PRIMARY KEY (state_code);



ALTER TABLE ONLY lookup_department
    ADD CONSTRAINT lookup_department_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_orgaddress_types
    ADD CONSTRAINT lookup_orgaddress_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_orgemail_types
    ADD CONSTRAINT lookup_orgemail_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_orgphone_types
    ADD CONSTRAINT lookup_orgphone_types_pkey PRIMARY KEY (code);



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



ALTER TABLE ONLY lookup_employment_types
    ADD CONSTRAINT lookup_employment_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_locale
    ADD CONSTRAINT lookup_locale_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_contactaddress_types
    ADD CONSTRAINT lookup_contactaddress_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_contactemail_types
    ADD CONSTRAINT lookup_contactemail_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_contactphone_types
    ADD CONSTRAINT lookup_contactphone_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_access_types
    ADD CONSTRAINT lookup_access_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY organization
    ADD CONSTRAINT organization_pkey PRIMARY KEY (org_id);



ALTER TABLE ONLY contact
    ADD CONSTRAINT contact_pkey PRIMARY KEY (contact_id);



ALTER TABLE ONLY contact
    ADD CONSTRAINT contact_employee_id_key UNIQUE (employee_id);



ALTER TABLE ONLY contact_lead_skipped_map
    ADD CONSTRAINT contact_lead_skipped_map_pkey PRIMARY KEY (map_id);



ALTER TABLE ONLY contact_lead_read_map
    ADD CONSTRAINT contact_lead_read_map_pkey PRIMARY KEY (map_id);



ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (role_id);



ALTER TABLE ONLY permission_category
    ADD CONSTRAINT permission_category_pkey PRIMARY KEY (category_id);



ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (permission_id);



ALTER TABLE ONLY role_permission
    ADD CONSTRAINT role_permission_pkey PRIMARY KEY (id);



ALTER TABLE ONLY lookup_stage
    ADD CONSTRAINT lookup_stage_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_delivery_options
    ADD CONSTRAINT lookup_delivery_options_pkey PRIMARY KEY (code);



ALTER TABLE ONLY news
    ADD CONSTRAINT news_pkey PRIMARY KEY (rec_id);



ALTER TABLE ONLY organization_address
    ADD CONSTRAINT organization_address_pkey PRIMARY KEY (address_id);



ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT organization_emailaddress_pkey PRIMARY KEY (emailaddress_id);



ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT organization_phone_pkey PRIMARY KEY (phone_id);



ALTER TABLE ONLY contact_address
    ADD CONSTRAINT contact_address_pkey PRIMARY KEY (address_id);



ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT contact_emailaddress_pkey PRIMARY KEY (emailaddress_id);



ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT contact_phone_pkey PRIMARY KEY (phone_id);



ALTER TABLE ONLY contact_imaddress
    ADD CONSTRAINT contact_imaddress_pkey PRIMARY KEY (address_id);



ALTER TABLE ONLY contact_textmessageaddress
    ADD CONSTRAINT contact_textmessageaddress_pkey PRIMARY KEY (address_id);



ALTER TABLE ONLY notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (notification_id);



ALTER TABLE ONLY cfsinbox_message
    ADD CONSTRAINT cfsinbox_message_pkey PRIMARY KEY (id);



ALTER TABLE ONLY lookup_lists_lookup
    ADD CONSTRAINT lookup_lists_lookup_pkey PRIMARY KEY (id);



ALTER TABLE ONLY webdav
    ADD CONSTRAINT webdav_pkey PRIMARY KEY (id);



ALTER TABLE ONLY category_editor_lookup
    ADD CONSTRAINT category_editor_lookup_pkey PRIMARY KEY (id);



ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT viewpoint_pkey PRIMARY KEY (viewpoint_id);



ALTER TABLE ONLY viewpoint_permission
    ADD CONSTRAINT viewpoint_permission_pkey PRIMARY KEY (vp_permission_id);



ALTER TABLE ONLY report
    ADD CONSTRAINT report_pkey PRIMARY KEY (report_id);



ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT report_criteria_pkey PRIMARY KEY (criteria_id);



ALTER TABLE ONLY report_criteria_parameter
    ADD CONSTRAINT report_criteria_parameter_pkey PRIMARY KEY (parameter_id);



ALTER TABLE ONLY report_queue
    ADD CONSTRAINT report_queue_pkey PRIMARY KEY (queue_id);



ALTER TABLE ONLY report_queue_criteria
    ADD CONSTRAINT report_queue_criteria_pkey PRIMARY KEY (criteria_id);



ALTER TABLE ONLY action_list
    ADD CONSTRAINT action_list_pkey PRIMARY KEY (action_id);



ALTER TABLE ONLY action_item
    ADD CONSTRAINT action_item_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY action_item_log
    ADD CONSTRAINT action_item_log_pkey PRIMARY KEY (log_id);



ALTER TABLE ONLY import
    ADD CONSTRAINT import_pkey PRIMARY KEY (import_id);



ALTER TABLE ONLY database_version
    ADD CONSTRAINT database_version_pkey PRIMARY KEY (version_id);



ALTER TABLE ONLY lookup_relationship_types
    ADD CONSTRAINT lookup_relationship_types_pkey PRIMARY KEY (type_id);



ALTER TABLE ONLY relationship
    ADD CONSTRAINT relationship_pkey PRIMARY KEY (relationship_id);



ALTER TABLE ONLY lookup_call_types
    ADD CONSTRAINT lookup_call_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_call_priority
    ADD CONSTRAINT lookup_call_priority_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_call_reminder
    ADD CONSTRAINT lookup_call_reminder_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_call_result
    ADD CONSTRAINT lookup_call_result_pkey PRIMARY KEY (result_id);



ALTER TABLE ONLY lookup_opportunity_types
    ADD CONSTRAINT lookup_opportunity_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_opportunity_environment
    ADD CONSTRAINT lookup_opportunity_environment_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_opportunity_competitors
    ADD CONSTRAINT lookup_opportunity_competitors_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_opportunity_event_compelling
    ADD CONSTRAINT lookup_opportunity_event_compelling_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_opportunity_budget
    ADD CONSTRAINT lookup_opportunity_budget_pkey PRIMARY KEY (code);



ALTER TABLE ONLY opportunity_header
    ADD CONSTRAINT opportunity_header_pkey PRIMARY KEY (opp_id);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT opportunity_component_pkey PRIMARY KEY (id);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT call_log_pkey PRIMARY KEY (call_id);



ALTER TABLE ONLY lookup_project_activity
    ADD CONSTRAINT lookup_project_activity_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_priority
    ADD CONSTRAINT lookup_project_priority_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_status
    ADD CONSTRAINT lookup_project_status_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_loe
    ADD CONSTRAINT lookup_project_loe_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_role
    ADD CONSTRAINT lookup_project_role_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_category
    ADD CONSTRAINT lookup_project_category_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_news_template
    ADD CONSTRAINT lookup_news_template_pkey PRIMARY KEY (code);



ALTER TABLE ONLY projects
    ADD CONSTRAINT projects_pkey PRIMARY KEY (project_id);



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT project_requirements_pkey PRIMARY KEY (requirement_id);



ALTER TABLE ONLY project_assignments_folder
    ADD CONSTRAINT project_assignments_folder_pkey PRIMARY KEY (folder_id);



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT project_assignments_pkey PRIMARY KEY (assignment_id);



ALTER TABLE ONLY project_assignments_status
    ADD CONSTRAINT project_assignments_status_pkey PRIMARY KEY (status_id);



ALTER TABLE ONLY project_issues_categories
    ADD CONSTRAINT project_issues_categories_pkey PRIMARY KEY (category_id);



ALTER TABLE ONLY project_issues
    ADD CONSTRAINT project_issues_pkey PRIMARY KEY (issue_id);



ALTER TABLE ONLY project_issue_replies
    ADD CONSTRAINT project_issue_replies_pkey PRIMARY KEY (reply_id);



ALTER TABLE ONLY project_folders
    ADD CONSTRAINT project_folders_pkey PRIMARY KEY (folder_id);



ALTER TABLE ONLY project_files
    ADD CONSTRAINT project_files_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY project_news_category
    ADD CONSTRAINT project_news_category_pkey PRIMARY KEY (category_id);



ALTER TABLE ONLY project_news
    ADD CONSTRAINT project_news_pkey PRIMARY KEY (news_id);



ALTER TABLE ONLY project_requirements_map
    ADD CONSTRAINT project_requirements_map_pkey PRIMARY KEY (map_id);



ALTER TABLE ONLY lookup_project_permission_category
    ADD CONSTRAINT lookup_project_permission_category_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_permission
    ADD CONSTRAINT lookup_project_permission_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_permission
    ADD CONSTRAINT lookup_project_permission_permission_key UNIQUE (permission);



ALTER TABLE ONLY project_permissions
    ADD CONSTRAINT project_permissions_pkey PRIMARY KEY (id);



ALTER TABLE ONLY project_accounts
    ADD CONSTRAINT project_accounts_pkey PRIMARY KEY (id);



ALTER TABLE ONLY lookup_currency
    ADD CONSTRAINT lookup_currency_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_product_category_type
    ADD CONSTRAINT lookup_product_category_type_pkey PRIMARY KEY (code);



ALTER TABLE ONLY product_category
    ADD CONSTRAINT product_category_pkey PRIMARY KEY (category_id);



ALTER TABLE ONLY product_category_map
    ADD CONSTRAINT product_category_map_pkey PRIMARY KEY (id);



ALTER TABLE ONLY lookup_product_type
    ADD CONSTRAINT lookup_product_type_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_product_manufacturer
    ADD CONSTRAINT lookup_product_manufacturer_pkey PRIMARY KEY (code);



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



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT product_catalog_pricing_pkey PRIMARY KEY (price_id);



ALTER TABLE ONLY package
    ADD CONSTRAINT package_pkey PRIMARY KEY (package_id);



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT package_products_map_pkey PRIMARY KEY (id);



ALTER TABLE ONLY product_catalog_category_map
    ADD CONSTRAINT product_catalog_category_map_pkey PRIMARY KEY (id);



ALTER TABLE ONLY lookup_product_conf_result
    ADD CONSTRAINT lookup_product_conf_result_pkey PRIMARY KEY (code);



ALTER TABLE ONLY product_option_configurator
    ADD CONSTRAINT product_option_configurator_pkey PRIMARY KEY (configurator_id);



ALTER TABLE ONLY product_option
    ADD CONSTRAINT product_option_pkey PRIMARY KEY (option_id);



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT product_option_values_pkey PRIMARY KEY (value_id);



ALTER TABLE ONLY product_option_map
    ADD CONSTRAINT product_option_map_pkey PRIMARY KEY (product_option_id);



ALTER TABLE ONLY lookup_product_keyword
    ADD CONSTRAINT lookup_product_keyword_pkey PRIMARY KEY (code);



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



ALTER TABLE ONLY service_contract_hours
    ADD CONSTRAINT service_contract_hours_pkey PRIMARY KEY (history_id);



ALTER TABLE ONLY service_contract_products
    ADD CONSTRAINT service_contract_products_pkey PRIMARY KEY (id);



ALTER TABLE ONLY asset_category
    ADD CONSTRAINT asset_category_pkey PRIMARY KEY (id);



ALTER TABLE ONLY asset_category_draft
    ADD CONSTRAINT asset_category_draft_pkey PRIMARY KEY (id);



ALTER TABLE ONLY asset
    ADD CONSTRAINT asset_pkey PRIMARY KEY (asset_id);



ALTER TABLE ONLY ticket_level
    ADD CONSTRAINT ticket_level_pkey PRIMARY KEY (code);



ALTER TABLE ONLY ticket_level
    ADD CONSTRAINT ticket_level_description_key UNIQUE (description);



ALTER TABLE ONLY ticket_severity
    ADD CONSTRAINT ticket_severity_pkey PRIMARY KEY (code);



ALTER TABLE ONLY ticket_severity
    ADD CONSTRAINT ticket_severity_description_key UNIQUE (description);



ALTER TABLE ONLY lookup_ticketsource
    ADD CONSTRAINT lookup_ticketsource_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_ticketsource
    ADD CONSTRAINT lookup_ticketsource_description_key UNIQUE (description);



ALTER TABLE ONLY lookup_ticket_status
    ADD CONSTRAINT lookup_ticket_status_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_ticket_status
    ADD CONSTRAINT lookup_ticket_status_description_key UNIQUE (description);



ALTER TABLE ONLY ticket_priority
    ADD CONSTRAINT ticket_priority_pkey PRIMARY KEY (code);



ALTER TABLE ONLY ticket_priority
    ADD CONSTRAINT ticket_priority_description_key UNIQUE (description);



ALTER TABLE ONLY ticket_category
    ADD CONSTRAINT ticket_category_pkey PRIMARY KEY (id);



ALTER TABLE ONLY ticket_category_draft
    ADD CONSTRAINT ticket_category_draft_pkey PRIMARY KEY (id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (ticketid);



ALTER TABLE ONLY project_ticket_count
    ADD CONSTRAINT project_ticket_count_project_id_key UNIQUE (project_id);



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT ticketlog_pkey PRIMARY KEY (id);



ALTER TABLE ONLY ticket_csstm_form
    ADD CONSTRAINT ticket_csstm_form_pkey PRIMARY KEY (form_id);



ALTER TABLE ONLY ticket_activity_item
    ADD CONSTRAINT ticket_activity_item_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY ticket_sun_form
    ADD CONSTRAINT ticket_sun_form_pkey PRIMARY KEY (form_id);



ALTER TABLE ONLY trouble_asset_replacement
    ADD CONSTRAINT trouble_asset_replacement_pkey PRIMARY KEY (replacement_id);



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



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT quote_product_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT quote_product_options_pkey PRIMARY KEY (quote_product_option_id);



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



ALTER TABLE ONLY order_product
    ADD CONSTRAINT order_product_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY order_product_status
    ADD CONSTRAINT order_product_status_pkey PRIMARY KEY (order_product_status_id);



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT order_product_options_pkey PRIMARY KEY (order_product_option_id);



ALTER TABLE ONLY lookup_orderaddress_types
    ADD CONSTRAINT lookup_orderaddress_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY order_address
    ADD CONSTRAINT order_address_pkey PRIMARY KEY (address_id);



ALTER TABLE ONLY lookup_payment_methods
    ADD CONSTRAINT lookup_payment_methods_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_creditcard_types
    ADD CONSTRAINT lookup_creditcard_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY payment_creditcard
    ADD CONSTRAINT payment_creditcard_pkey PRIMARY KEY (creditcard_id);



ALTER TABLE ONLY payment_eft
    ADD CONSTRAINT payment_eft_pkey PRIMARY KEY (bank_id);



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT customer_product_pkey PRIMARY KEY (customer_product_id);



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT customer_product_history_pkey PRIMARY KEY (history_id);



ALTER TABLE ONLY lookup_payment_status
    ADD CONSTRAINT lookup_payment_status_pkey PRIMARY KEY (code);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT order_payment_pkey PRIMARY KEY (payment_id);



ALTER TABLE ONLY order_payment_status
    ADD CONSTRAINT order_payment_status_pkey PRIMARY KEY (payment_status_id);



ALTER TABLE ONLY module_field_categorylink
    ADD CONSTRAINT module_field_categorylink_pkey PRIMARY KEY (id);



ALTER TABLE ONLY module_field_categorylink
    ADD CONSTRAINT module_field_categorylink_category_id_key UNIQUE (category_id);



ALTER TABLE ONLY custom_field_category
    ADD CONSTRAINT custom_field_category_pkey PRIMARY KEY (category_id);



ALTER TABLE ONLY custom_field_group
    ADD CONSTRAINT custom_field_group_pkey PRIMARY KEY (group_id);



ALTER TABLE ONLY custom_field_info
    ADD CONSTRAINT custom_field_info_pkey PRIMARY KEY (field_id);



ALTER TABLE ONLY custom_field_lookup
    ADD CONSTRAINT custom_field_lookup_pkey PRIMARY KEY (code);



ALTER TABLE ONLY custom_field_record
    ADD CONSTRAINT custom_field_record_pkey PRIMARY KEY (record_id);



ALTER TABLE ONLY saved_criterialist
    ADD CONSTRAINT saved_criterialist_pkey PRIMARY KEY (id);



ALTER TABLE ONLY campaign
    ADD CONSTRAINT campaign_pkey PRIMARY KEY (campaign_id);



ALTER TABLE ONLY campaign_run
    ADD CONSTRAINT campaign_run_pkey PRIMARY KEY (id);



ALTER TABLE ONLY excluded_recipient
    ADD CONSTRAINT excluded_recipient_pkey PRIMARY KEY (id);



ALTER TABLE ONLY active_campaign_groups
    ADD CONSTRAINT active_campaign_groups_pkey PRIMARY KEY (id);



ALTER TABLE ONLY scheduled_recipient
    ADD CONSTRAINT scheduled_recipient_pkey PRIMARY KEY (id);



ALTER TABLE ONLY lookup_survey_types
    ADD CONSTRAINT lookup_survey_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY survey
    ADD CONSTRAINT survey_pkey PRIMARY KEY (survey_id);



ALTER TABLE ONLY survey_questions
    ADD CONSTRAINT survey_questions_pkey PRIMARY KEY (question_id);



ALTER TABLE ONLY survey_items
    ADD CONSTRAINT survey_items_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY active_survey
    ADD CONSTRAINT active_survey_pkey PRIMARY KEY (active_survey_id);



ALTER TABLE ONLY active_survey_questions
    ADD CONSTRAINT active_survey_questions_pkey PRIMARY KEY (question_id);



ALTER TABLE ONLY active_survey_items
    ADD CONSTRAINT active_survey_items_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY active_survey_responses
    ADD CONSTRAINT active_survey_responses_pkey PRIMARY KEY (response_id);



ALTER TABLE ONLY active_survey_answers
    ADD CONSTRAINT active_survey_answers_pkey PRIMARY KEY (answer_id);



ALTER TABLE ONLY active_survey_answer_items
    ADD CONSTRAINT active_survey_answer_items_pkey PRIMARY KEY (id);



ALTER TABLE ONLY active_survey_answer_avg
    ADD CONSTRAINT active_survey_answer_avg_pkey PRIMARY KEY (id);



ALTER TABLE ONLY field_types
    ADD CONSTRAINT field_types_pkey PRIMARY KEY (id);



ALTER TABLE ONLY search_fields
    ADD CONSTRAINT search_fields_pkey PRIMARY KEY (id);



ALTER TABLE ONLY message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);



ALTER TABLE ONLY message_template
    ADD CONSTRAINT message_template_pkey PRIMARY KEY (id);



ALTER TABLE ONLY help_module
    ADD CONSTRAINT help_module_pkey PRIMARY KEY (module_id);



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT help_contents_pkey PRIMARY KEY (help_id);



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT help_tableof_contents_pkey PRIMARY KEY (content_id);



ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT help_tableofcontentitem_links_pkey PRIMARY KEY (link_id);



ALTER TABLE ONLY lookup_help_features
    ADD CONSTRAINT lookup_help_features_pkey PRIMARY KEY (code);



ALTER TABLE ONLY help_features
    ADD CONSTRAINT help_features_pkey PRIMARY KEY (feature_id);



ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT help_related_links_pkey PRIMARY KEY (relatedlink_id);



ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT help_faqs_pkey PRIMARY KEY (faq_id);



ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT help_business_rules_pkey PRIMARY KEY (rule_id);



ALTER TABLE ONLY help_notes
    ADD CONSTRAINT help_notes_pkey PRIMARY KEY (note_id);



ALTER TABLE ONLY help_tips
    ADD CONSTRAINT help_tips_pkey PRIMARY KEY (tip_id);



ALTER TABLE ONLY sync_client
    ADD CONSTRAINT sync_client_pkey PRIMARY KEY (client_id);



ALTER TABLE ONLY sync_system
    ADD CONSTRAINT sync_system_pkey PRIMARY KEY (system_id);



ALTER TABLE ONLY sync_table
    ADD CONSTRAINT sync_table_pkey PRIMARY KEY (table_id);



ALTER TABLE ONLY sync_log
    ADD CONSTRAINT sync_log_pkey PRIMARY KEY (log_id);



ALTER TABLE ONLY sync_transaction_log
    ADD CONSTRAINT sync_transaction_log_pkey PRIMARY KEY (transaction_id);



ALTER TABLE ONLY process_log
    ADD CONSTRAINT process_log_pkey PRIMARY KEY (process_id);



ALTER TABLE ONLY autoguide_make
    ADD CONSTRAINT autoguide_make_pkey PRIMARY KEY (make_id);



ALTER TABLE ONLY autoguide_model
    ADD CONSTRAINT autoguide_model_pkey PRIMARY KEY (model_id);



ALTER TABLE ONLY autoguide_vehicle
    ADD CONSTRAINT autoguide_vehicle_pkey PRIMARY KEY (vehicle_id);



ALTER TABLE ONLY autoguide_inventory
    ADD CONSTRAINT autoguide_inventory_pkey PRIMARY KEY (inventory_id);



ALTER TABLE ONLY autoguide_options
    ADD CONSTRAINT autoguide_options_pkey PRIMARY KEY (option_id);



ALTER TABLE ONLY autoguide_ad_run
    ADD CONSTRAINT autoguide_ad_run_pkey PRIMARY KEY (ad_run_id);



ALTER TABLE ONLY autoguide_ad_run_types
    ADD CONSTRAINT autoguide_ad_run_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_revenue_types
    ADD CONSTRAINT lookup_revenue_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_revenuedetail_types
    ADD CONSTRAINT lookup_revenuedetail_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY revenue
    ADD CONSTRAINT revenue_pkey PRIMARY KEY (id);



ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT revenue_detail_pkey PRIMARY KEY (id);



ALTER TABLE ONLY lookup_task_priority
    ADD CONSTRAINT lookup_task_priority_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_task_loe
    ADD CONSTRAINT lookup_task_loe_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_task_category
    ADD CONSTRAINT lookup_task_category_pkey PRIMARY KEY (code);



ALTER TABLE ONLY task
    ADD CONSTRAINT task_pkey PRIMARY KEY (task_id);



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



ALTER TABLE ONLY business_process_component_library
    ADD CONSTRAINT business_process_component_library_pkey PRIMARY KEY (component_id);



ALTER TABLE ONLY business_process_component_result_lookup
    ADD CONSTRAINT business_process_component_result_lookup_pkey PRIMARY KEY (result_id);



ALTER TABLE ONLY business_process_parameter_library
    ADD CONSTRAINT business_process_parameter_library_pkey PRIMARY KEY (parameter_id);



ALTER TABLE ONLY business_process
    ADD CONSTRAINT business_process_pkey PRIMARY KEY (process_id);



ALTER TABLE ONLY business_process
    ADD CONSTRAINT business_process_process_name_key UNIQUE (process_name);



ALTER TABLE ONLY business_process_component
    ADD CONSTRAINT business_process_component_pkey PRIMARY KEY (id);



ALTER TABLE ONLY business_process_parameter
    ADD CONSTRAINT business_process_parameter_pkey PRIMARY KEY (id);



ALTER TABLE ONLY business_process_component_parameter
    ADD CONSTRAINT business_process_component_parameter_pkey PRIMARY KEY (id);



ALTER TABLE ONLY business_process_events
    ADD CONSTRAINT business_process_events_pkey PRIMARY KEY (event_id);



ALTER TABLE ONLY business_process_log
    ADD CONSTRAINT business_process_log_process_name_key UNIQUE (process_name);



ALTER TABLE ONLY business_process_hook_library
    ADD CONSTRAINT business_process_hook_library_pkey PRIMARY KEY (hook_id);



ALTER TABLE ONLY business_process_hook_triggers
    ADD CONSTRAINT business_process_hook_triggers_pkey PRIMARY KEY (trigger_id);



ALTER TABLE ONLY business_process_hook
    ADD CONSTRAINT business_process_hook_pkey PRIMARY KEY (id);



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



ALTER TABLE ONLY quote_notes
    ADD CONSTRAINT quote_notes_pkey PRIMARY KEY (notes_id);



ALTER TABLE ONLY history
    ADD CONSTRAINT history_pkey PRIMARY KEY (history_id);



ALTER TABLE ONLY access_log
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY lookup_contact_types
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY organization
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY organization
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY organization
    ADD CONSTRAINT "$3" FOREIGN KEY ("owner") REFERENCES "access"(user_id);



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$2" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$3" FOREIGN KEY (department) REFERENCES lookup_department(code);



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$4" FOREIGN KEY (super) REFERENCES contact(contact_id);



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$5" FOREIGN KEY (assistant) REFERENCES contact(contact_id);



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$8" FOREIGN KEY ("owner") REFERENCES "access"(user_id);



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$9" FOREIGN KEY (access_type) REFERENCES lookup_access_types(code);



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



ALTER TABLE ONLY role
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY role
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY permission
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id);



ALTER TABLE ONLY role_permission
    ADD CONSTRAINT "$1" FOREIGN KEY (role_id) REFERENCES role(role_id);



ALTER TABLE ONLY role_permission
    ADD CONSTRAINT "$2" FOREIGN KEY (permission_id) REFERENCES permission(permission_id);



ALTER TABLE ONLY news
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY organization_address
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY organization_address
    ADD CONSTRAINT "$2" FOREIGN KEY (address_type) REFERENCES lookup_orgaddress_types(code);



ALTER TABLE ONLY organization_address
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY organization_address
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT "$2" FOREIGN KEY (emailaddress_type) REFERENCES lookup_orgemail_types(code);



ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT "$2" FOREIGN KEY (phone_type) REFERENCES lookup_orgphone_types(code);



ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact_address
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY contact_address
    ADD CONSTRAINT "$2" FOREIGN KEY (address_type) REFERENCES lookup_contactaddress_types(code);



ALTER TABLE ONLY contact_address
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact_address
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT "$2" FOREIGN KEY (emailaddress_type) REFERENCES lookup_contactemail_types(code);



ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT "$2" FOREIGN KEY (phone_type) REFERENCES lookup_contactphone_types(code);



ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



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



ALTER TABLE ONLY cfsinbox_message
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY cfsinbox_message
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY cfsinbox_messagelink
    ADD CONSTRAINT "$1" FOREIGN KEY (id) REFERENCES cfsinbox_message(id);



ALTER TABLE ONLY cfsinbox_messagelink
    ADD CONSTRAINT "$2" FOREIGN KEY (sent_to) REFERENCES contact(contact_id);



ALTER TABLE ONLY cfsinbox_messagelink
    ADD CONSTRAINT "$3" FOREIGN KEY (sent_from) REFERENCES "access"(user_id);



ALTER TABLE ONLY account_type_levels
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY account_type_levels
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_account_types(code);



ALTER TABLE ONLY contact_type_levels
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY contact_type_levels
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_contact_types(code);



ALTER TABLE ONLY lookup_lists_lookup
    ADD CONSTRAINT "$1" FOREIGN KEY (module_id) REFERENCES permission_category(category_id);



ALTER TABLE ONLY webdav
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id);



ALTER TABLE ONLY webdav
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY webdav
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY category_editor_lookup
    ADD CONSTRAINT "$1" FOREIGN KEY (module_id) REFERENCES permission_category(category_id);



ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT "$2" FOREIGN KEY (vp_user_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY viewpoint_permission
    ADD CONSTRAINT "$1" FOREIGN KEY (viewpoint_id) REFERENCES viewpoint(viewpoint_id);



ALTER TABLE ONLY viewpoint_permission
    ADD CONSTRAINT "$2" FOREIGN KEY (permission_id) REFERENCES permission(permission_id);



ALTER TABLE ONLY report
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id);



ALTER TABLE ONLY report
    ADD CONSTRAINT "$2" FOREIGN KEY (permission_id) REFERENCES permission(permission_id);



ALTER TABLE ONLY report
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY report
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT "$1" FOREIGN KEY (report_id) REFERENCES report(report_id);



ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT "$2" FOREIGN KEY ("owner") REFERENCES "access"(user_id);



ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY report_criteria_parameter
    ADD CONSTRAINT "$1" FOREIGN KEY (criteria_id) REFERENCES report_criteria(criteria_id);



ALTER TABLE ONLY report_queue
    ADD CONSTRAINT "$1" FOREIGN KEY (report_id) REFERENCES report(report_id);



ALTER TABLE ONLY report_queue
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY report_queue_criteria
    ADD CONSTRAINT "$1" FOREIGN KEY (queue_id) REFERENCES report_queue(queue_id);



ALTER TABLE ONLY action_list
    ADD CONSTRAINT "$1" FOREIGN KEY ("owner") REFERENCES "access"(user_id);



ALTER TABLE ONLY action_list
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY action_list
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY action_item
    ADD CONSTRAINT "$1" FOREIGN KEY (action_id) REFERENCES action_list(action_id);



ALTER TABLE ONLY action_item
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY action_item
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY action_item_log
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES action_item(item_id);



ALTER TABLE ONLY action_item_log
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY action_item_log
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY import
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY import
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY relationship
    ADD CONSTRAINT "$1" FOREIGN KEY (type_id) REFERENCES lookup_relationship_types(type_id);



ALTER TABLE ONLY opportunity_header
    ADD CONSTRAINT "$1" FOREIGN KEY (acctlink) REFERENCES organization(org_id);



ALTER TABLE ONLY opportunity_header
    ADD CONSTRAINT "$2" FOREIGN KEY (contactlink) REFERENCES contact(contact_id);



ALTER TABLE ONLY opportunity_header
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY opportunity_header
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$1" FOREIGN KEY (opp_id) REFERENCES opportunity_header(opp_id);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$2" FOREIGN KEY ("owner") REFERENCES "access"(user_id);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$3" FOREIGN KEY (stage) REFERENCES lookup_stage(code);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$6" FOREIGN KEY (environment) REFERENCES lookup_opportunity_environment(code);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$7" FOREIGN KEY (competitors) REFERENCES lookup_opportunity_competitors(code);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$8" FOREIGN KEY (compelling_event) REFERENCES lookup_opportunity_event_compelling(code);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$9" FOREIGN KEY (budget) REFERENCES lookup_opportunity_budget(code);



ALTER TABLE ONLY opportunity_component_levels
    ADD CONSTRAINT "$1" FOREIGN KEY (opp_id) REFERENCES opportunity_component(id);



ALTER TABLE ONLY opportunity_component_levels
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_opportunity_types(code);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$3" FOREIGN KEY (opp_id) REFERENCES opportunity_header(opp_id);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$4" FOREIGN KEY (call_type_id) REFERENCES lookup_call_types(code);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$7" FOREIGN KEY (alert_call_type_id) REFERENCES lookup_call_types(code);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$8" FOREIGN KEY (parent_id) REFERENCES call_log(call_id);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$9" FOREIGN KEY ("owner") REFERENCES "access"(user_id);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$10" FOREIGN KEY (assignedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$11" FOREIGN KEY (completedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$12" FOREIGN KEY (result_id) REFERENCES lookup_call_result(result_id);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$13" FOREIGN KEY (priority_id) REFERENCES lookup_call_priority(code);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$14" FOREIGN KEY (reminder_type_id) REFERENCES lookup_call_reminder(code);



ALTER TABLE ONLY lookup_call_result
    ADD CONSTRAINT "$1" FOREIGN KEY (next_call_type_id) REFERENCES call_log(call_id);



ALTER TABLE ONLY projects
    ADD CONSTRAINT "$1" FOREIGN KEY (department_id) REFERENCES lookup_department(code);



ALTER TABLE ONLY projects
    ADD CONSTRAINT "$2" FOREIGN KEY (approvalby) REFERENCES "access"(user_id);

    
    ALTER TABLE ONLY projects
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY projects
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY projects
    ADD CONSTRAINT "$5" FOREIGN KEY (category_id) REFERENCES lookup_project_category(code);



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$2" FOREIGN KEY (estimated_loetype) REFERENCES lookup_project_loe(code);



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$3" FOREIGN KEY (actual_loetype) REFERENCES lookup_project_loe(code);



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$4" FOREIGN KEY (approvedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$5" FOREIGN KEY (closedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_assignments_folder
    ADD CONSTRAINT "$1" FOREIGN KEY (parent_id) REFERENCES project_assignments_folder(folder_id);



ALTER TABLE ONLY project_assignments_folder
    ADD CONSTRAINT "$2" FOREIGN KEY (requirement_id) REFERENCES project_requirements(requirement_id);



ALTER TABLE ONLY project_assignments_folder
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_assignments_folder
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$2" FOREIGN KEY (requirement_id) REFERENCES project_requirements(requirement_id);



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$3" FOREIGN KEY (assignedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$4" FOREIGN KEY (user_assign_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$5" FOREIGN KEY (estimated_loetype) REFERENCES lookup_project_loe(code);



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$6" FOREIGN KEY (actual_loetype) REFERENCES lookup_project_loe(code);



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$7" FOREIGN KEY (priority_id) REFERENCES lookup_project_priority(code);



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$8" FOREIGN KEY (status_id) REFERENCES lookup_project_status(code);



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$9" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$10" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$11" FOREIGN KEY (folder_id) REFERENCES project_assignments_folder(folder_id);



ALTER TABLE ONLY project_assignments_status
    ADD CONSTRAINT "$1" FOREIGN KEY (assignment_id) REFERENCES project_assignments(assignment_id);



ALTER TABLE ONLY project_assignments_status
    ADD CONSTRAINT "$2" FOREIGN KEY (user_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_assignments_status
    ADD CONSTRAINT "$3" FOREIGN KEY (project_status_id) REFERENCES lookup_project_status(code);



ALTER TABLE ONLY project_assignments_status
    ADD CONSTRAINT "$4" FOREIGN KEY (user_assign_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_issues_categories
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY project_issues_categories
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_issues_categories
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_issues
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY project_issues
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_issues
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_issues
    ADD CONSTRAINT "$2" FOREIGN KEY (category_id) REFERENCES project_issues_categories(category_id);



ALTER TABLE ONLY project_issue_replies
    ADD CONSTRAINT "$1" FOREIGN KEY (issue_id) REFERENCES project_issues(issue_id);



ALTER TABLE ONLY project_issue_replies
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_issue_replies
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_folders
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_folders
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_files
    ADD CONSTRAINT "$1" FOREIGN KEY (folder_id) REFERENCES project_folders(folder_id);



ALTER TABLE ONLY project_files
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_files
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_files_version
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES project_files(item_id);



ALTER TABLE ONLY project_files_version
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_files_version
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_files_download
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES project_files(item_id);



ALTER TABLE ONLY project_files_download
    ADD CONSTRAINT "$2" FOREIGN KEY (user_download_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_files_thumbnail
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES project_files(item_id);



ALTER TABLE ONLY project_files_thumbnail
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_files_thumbnail
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$2" FOREIGN KEY (user_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$3" FOREIGN KEY (userlevel) REFERENCES lookup_project_role(code);



ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_news_category
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY project_news
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY project_news
    ADD CONSTRAINT "$2" FOREIGN KEY (category_id) REFERENCES project_news_category(category_id);



ALTER TABLE ONLY project_news
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_news
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY project_news
    ADD CONSTRAINT "$5" FOREIGN KEY (template_id) REFERENCES lookup_news_template(code);



ALTER TABLE ONLY project_requirements_map
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY project_requirements_map
    ADD CONSTRAINT "$2" FOREIGN KEY (requirement_id) REFERENCES project_requirements(requirement_id);



ALTER TABLE ONLY project_requirements_map
    ADD CONSTRAINT "$3" FOREIGN KEY (folder_id) REFERENCES project_assignments_folder(folder_id);



ALTER TABLE ONLY project_requirements_map
    ADD CONSTRAINT "$4" FOREIGN KEY (assignment_id) REFERENCES project_assignments(assignment_id);



ALTER TABLE ONLY lookup_project_permission
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES lookup_project_permission_category(code);



ALTER TABLE ONLY lookup_project_permission
    ADD CONSTRAINT "$2" FOREIGN KEY (default_role) REFERENCES lookup_project_role(code);



ALTER TABLE ONLY project_permissions
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY project_permissions
    ADD CONSTRAINT "$2" FOREIGN KEY (permission_id) REFERENCES lookup_project_permission(code);



ALTER TABLE ONLY project_permissions
    ADD CONSTRAINT "$3" FOREIGN KEY (userlevel) REFERENCES lookup_project_role(code);



ALTER TABLE ONLY project_accounts
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY project_accounts
    ADD CONSTRAINT "$2" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$1" FOREIGN KEY (parent_id) REFERENCES product_category(category_id);



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_product_category_type(code);



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$3" FOREIGN KEY (thumbnail_image_id) REFERENCES project_files(item_id);



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$4" FOREIGN KEY (small_image_id) REFERENCES project_files(item_id);



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$5" FOREIGN KEY (large_image_id) REFERENCES project_files(item_id);



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY product_category
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY product_category_map
    ADD CONSTRAINT "$1" FOREIGN KEY (category1_id) REFERENCES product_category(category_id);



ALTER TABLE ONLY product_category_map
    ADD CONSTRAINT "$2" FOREIGN KEY (category2_id) REFERENCES product_category(category_id);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$1" FOREIGN KEY (parent_id) REFERENCES product_catalog(product_id);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_product_type(code);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$3" FOREIGN KEY (format_id) REFERENCES lookup_product_format(code);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$4" FOREIGN KEY (shipping_id) REFERENCES lookup_product_shipping(code);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$5" FOREIGN KEY (estimated_ship_time) REFERENCES lookup_product_ship_time(code);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$6" FOREIGN KEY (thumbnail_image_id) REFERENCES project_files(item_id);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$7" FOREIGN KEY (small_image_id) REFERENCES project_files(item_id);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$8" FOREIGN KEY (large_image_id) REFERENCES project_files(item_id);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$9" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$10" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY product_catalog
    ADD CONSTRAINT "$11" FOREIGN KEY (manufacturer_id) REFERENCES lookup_product_manufacturer(code);



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$1" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id);



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$2" FOREIGN KEY (tax_id) REFERENCES lookup_product_tax(code);



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$3" FOREIGN KEY (msrp_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$4" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$5" FOREIGN KEY (recurring_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$6" FOREIGN KEY (recurring_type) REFERENCES lookup_recurring_type(code);



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$7" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$8" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY product_catalog_pricing
    ADD CONSTRAINT "$9" FOREIGN KEY (cost_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY package
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES product_category(category_id);



ALTER TABLE ONLY package
    ADD CONSTRAINT "$2" FOREIGN KEY (thumbnail_image_id) REFERENCES project_files(item_id);



ALTER TABLE ONLY package
    ADD CONSTRAINT "$3" FOREIGN KEY (small_image_id) REFERENCES project_files(item_id);



ALTER TABLE ONLY package
    ADD CONSTRAINT "$4" FOREIGN KEY (large_image_id) REFERENCES project_files(item_id);



ALTER TABLE ONLY package
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY package
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT "$1" FOREIGN KEY (package_id) REFERENCES package(package_id);



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT "$2" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id);



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT "$3" FOREIGN KEY (msrp_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT "$4" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY package_products_map
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY product_catalog_category_map
    ADD CONSTRAINT "$1" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id);



ALTER TABLE ONLY product_catalog_category_map
    ADD CONSTRAINT "$2" FOREIGN KEY (category_id) REFERENCES product_category(category_id);



ALTER TABLE ONLY product_option_configurator
    ADD CONSTRAINT "$1" FOREIGN KEY (result_type) REFERENCES lookup_product_conf_result(code);



ALTER TABLE ONLY product_option
    ADD CONSTRAINT "$1" FOREIGN KEY (configurator_id) REFERENCES product_option_configurator(configurator_id);



ALTER TABLE ONLY product_option
    ADD CONSTRAINT "$2" FOREIGN KEY (parent_id) REFERENCES product_option(option_id);



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT "$1" FOREIGN KEY (option_id) REFERENCES product_option(option_id);



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT "$2" FOREIGN KEY (msrp_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT "$3" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT "$4" FOREIGN KEY (recurring_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT "$5" FOREIGN KEY (recurring_type) REFERENCES lookup_recurring_type(code);



ALTER TABLE ONLY product_option_values
    ADD CONSTRAINT "$6" FOREIGN KEY (cost_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY product_option_map
    ADD CONSTRAINT "$1" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id);



ALTER TABLE ONLY product_option_map
    ADD CONSTRAINT "$2" FOREIGN KEY (option_id) REFERENCES product_option(option_id);



ALTER TABLE ONLY product_option_boolean
    ADD CONSTRAINT "$1" FOREIGN KEY (product_option_id) REFERENCES product_option(option_id);



ALTER TABLE ONLY product_option_float
    ADD CONSTRAINT "$1" FOREIGN KEY (product_option_id) REFERENCES product_option(option_id);



ALTER TABLE ONLY product_option_timestamp
    ADD CONSTRAINT "$1" FOREIGN KEY (product_option_id) REFERENCES product_option(option_id);



ALTER TABLE ONLY product_option_integer
    ADD CONSTRAINT "$1" FOREIGN KEY (product_option_id) REFERENCES product_option(option_id);



ALTER TABLE ONLY product_option_text
    ADD CONSTRAINT "$1" FOREIGN KEY (product_option_id) REFERENCES product_option(option_id);



ALTER TABLE ONLY product_keyword_map
    ADD CONSTRAINT "$1" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id);



ALTER TABLE ONLY product_keyword_map
    ADD CONSTRAINT "$2" FOREIGN KEY (keyword_id) REFERENCES lookup_product_keyword(code);



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$1" FOREIGN KEY (account_id) REFERENCES organization(org_id);



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$2" FOREIGN KEY (category) REFERENCES lookup_sc_category(code);



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$3" FOREIGN KEY ("type") REFERENCES lookup_sc_type(code);



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$4" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$5" FOREIGN KEY (response_time) REFERENCES lookup_response_model(code);



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$6" FOREIGN KEY (telephone_service_model) REFERENCES lookup_phone_model(code);



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$7" FOREIGN KEY (onsite_service_model) REFERENCES lookup_onsite_model(code);



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$8" FOREIGN KEY (email_service_model) REFERENCES lookup_email_model(code);



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$9" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY service_contract
    ADD CONSTRAINT "$10" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY service_contract_hours
    ADD CONSTRAINT "$1" FOREIGN KEY (link_contract_id) REFERENCES service_contract(contract_id);



ALTER TABLE ONLY service_contract_hours
    ADD CONSTRAINT "$2" FOREIGN KEY (adjustment_reason) REFERENCES lookup_hours_reason(code);



ALTER TABLE ONLY service_contract_hours
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY service_contract_hours
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY service_contract_products
    ADD CONSTRAINT "$1" FOREIGN KEY (link_contract_id) REFERENCES service_contract(contract_id);



ALTER TABLE ONLY service_contract_products
    ADD CONSTRAINT "$2" FOREIGN KEY (link_product_id) REFERENCES product_catalog(product_id);



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$1" FOREIGN KEY (account_id) REFERENCES organization(org_id);



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$2" FOREIGN KEY (contract_id) REFERENCES service_contract(contract_id);



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$3" FOREIGN KEY (level1) REFERENCES asset_category(id);



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$4" FOREIGN KEY (level2) REFERENCES asset_category(id);



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$5" FOREIGN KEY (level3) REFERENCES asset_category(id);



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$6" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$7" FOREIGN KEY (response_time) REFERENCES lookup_response_model(code);



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$8" FOREIGN KEY (telephone_service_model) REFERENCES lookup_phone_model(code);



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$9" FOREIGN KEY (onsite_service_model) REFERENCES lookup_onsite_model(code);



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$10" FOREIGN KEY (email_service_model) REFERENCES lookup_email_model(code);



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$11" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY asset
    ADD CONSTRAINT "$12" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$5" FOREIGN KEY (pri_code) REFERENCES ticket_priority(code);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$6" FOREIGN KEY (level_code) REFERENCES ticket_level(code);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$7" FOREIGN KEY (department_code) REFERENCES lookup_department(code);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$8" FOREIGN KEY (source_code) REFERENCES lookup_ticketsource(code);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$9" FOREIGN KEY (cat_code) REFERENCES ticket_category(id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$10" FOREIGN KEY (subcat_code1) REFERENCES ticket_category(id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$11" FOREIGN KEY (subcat_code2) REFERENCES ticket_category(id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$12" FOREIGN KEY (subcat_code3) REFERENCES ticket_category(id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$13" FOREIGN KEY (assigned_to) REFERENCES "access"(user_id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$14" FOREIGN KEY (scode) REFERENCES ticket_severity(code);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$15" FOREIGN KEY (link_contract_id) REFERENCES service_contract(contract_id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$16" FOREIGN KEY (link_asset_id) REFERENCES asset(asset_id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$17" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id);



ALTER TABLE ONLY project_ticket_count
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$1" FOREIGN KEY (ticketid) REFERENCES ticket(ticketid);



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$2" FOREIGN KEY (assigned_to) REFERENCES "access"(user_id);



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$3" FOREIGN KEY (pri_code) REFERENCES ticket_priority(code);



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$4" FOREIGN KEY (department_code) REFERENCES lookup_department(code);



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$5" FOREIGN KEY (scode) REFERENCES ticket_severity(code);



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY ticket_csstm_form
    ADD CONSTRAINT "$1" FOREIGN KEY (link_ticket_id) REFERENCES ticket(ticketid);



ALTER TABLE ONLY ticket_csstm_form
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY ticket_csstm_form
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY ticket_activity_item
    ADD CONSTRAINT "$1" FOREIGN KEY (link_form_id) REFERENCES ticket_csstm_form(form_id);



ALTER TABLE ONLY ticket_sun_form
    ADD CONSTRAINT "$1" FOREIGN KEY (link_ticket_id) REFERENCES ticket(ticketid);



ALTER TABLE ONLY ticket_sun_form
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY ticket_sun_form
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY trouble_asset_replacement
    ADD CONSTRAINT "$1" FOREIGN KEY (link_form_id) REFERENCES ticket_sun_form(form_id);



ALTER TABLE ONLY ticketlink_project
    ADD CONSTRAINT "$1" FOREIGN KEY (ticket_id) REFERENCES ticket(ticketid);



ALTER TABLE ONLY ticketlink_project
    ADD CONSTRAINT "$2" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$1" FOREIGN KEY (parent_id) REFERENCES quote_entry(quote_id);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$2" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$3" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$4" FOREIGN KEY (source_id) REFERENCES lookup_quote_source(code);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$5" FOREIGN KEY (status_id) REFERENCES lookup_quote_status(code);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$6" FOREIGN KEY (quote_terms_id) REFERENCES lookup_quote_terms(code);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$7" FOREIGN KEY (quote_type_id) REFERENCES lookup_quote_type(code);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$8" FOREIGN KEY (ticketid) REFERENCES ticket(ticketid);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$9" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$10" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_id) REFERENCES quote_entry(quote_id);



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT "$2" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id);



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT "$3" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT "$4" FOREIGN KEY (recurring_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT "$5" FOREIGN KEY (recurring_type) REFERENCES lookup_recurring_type(code);



ALTER TABLE ONLY quote_product
    ADD CONSTRAINT "$6" FOREIGN KEY (status_id) REFERENCES lookup_quote_status(code);



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES quote_product(item_id);



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT "$2" FOREIGN KEY (product_option_id) REFERENCES product_option_map(product_option_id);



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT "$3" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT "$4" FOREIGN KEY (recurring_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT "$5" FOREIGN KEY (recurring_type) REFERENCES lookup_recurring_type(code);



ALTER TABLE ONLY quote_product_options
    ADD CONSTRAINT "$6" FOREIGN KEY (status_id) REFERENCES lookup_quote_status(code);



ALTER TABLE ONLY quote_product_option_boolean
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_product_option_id) REFERENCES quote_product_options(quote_product_option_id);



ALTER TABLE ONLY quote_product_option_float
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_product_option_id) REFERENCES quote_product_options(quote_product_option_id);



ALTER TABLE ONLY quote_product_option_timestamp
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_product_option_id) REFERENCES quote_product_options(quote_product_option_id);



ALTER TABLE ONLY quote_product_option_integer
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_product_option_id) REFERENCES quote_product_options(quote_product_option_id);



ALTER TABLE ONLY quote_product_option_text
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_product_option_id) REFERENCES quote_product_options(quote_product_option_id);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$1" FOREIGN KEY (parent_id) REFERENCES order_entry(order_id);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$2" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$3" FOREIGN KEY (quote_id) REFERENCES quote_entry(quote_id);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$4" FOREIGN KEY (sales_id) REFERENCES "access"(user_id);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$5" FOREIGN KEY (orderedby) REFERENCES contact(contact_id);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$6" FOREIGN KEY (billing_contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$7" FOREIGN KEY (source_id) REFERENCES lookup_order_source(code);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$8" FOREIGN KEY (status_id) REFERENCES lookup_order_status(code);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$9" FOREIGN KEY (order_terms_id) REFERENCES lookup_order_terms(code);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$10" FOREIGN KEY (order_type_id) REFERENCES lookup_order_type(code);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$11" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY order_entry
    ADD CONSTRAINT "$12" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$1" FOREIGN KEY (order_id) REFERENCES order_entry(order_id);



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$2" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id);



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$3" FOREIGN KEY (msrp_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$4" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$5" FOREIGN KEY (recurring_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$6" FOREIGN KEY (recurring_type) REFERENCES lookup_recurring_type(code);



ALTER TABLE ONLY order_product
    ADD CONSTRAINT "$7" FOREIGN KEY (status_id) REFERENCES lookup_order_status(code);



ALTER TABLE ONLY order_product_status
    ADD CONSTRAINT "$1" FOREIGN KEY (order_id) REFERENCES order_entry(order_id);



ALTER TABLE ONLY order_product_status
    ADD CONSTRAINT "$2" FOREIGN KEY (item_id) REFERENCES order_product(item_id);



ALTER TABLE ONLY order_product_status
    ADD CONSTRAINT "$3" FOREIGN KEY (status_id) REFERENCES lookup_order_status(code);



ALTER TABLE ONLY order_product_status
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY order_product_status
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES order_product(item_id);



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT "$2" FOREIGN KEY (product_option_id) REFERENCES product_option_map(product_option_id);



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT "$3" FOREIGN KEY (price_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT "$4" FOREIGN KEY (recurring_currency) REFERENCES lookup_currency(code);



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT "$5" FOREIGN KEY (recurring_type) REFERENCES lookup_recurring_type(code);



ALTER TABLE ONLY order_product_options
    ADD CONSTRAINT "$6" FOREIGN KEY (status_id) REFERENCES lookup_order_status(code);



ALTER TABLE ONLY order_product_option_boolean
    ADD CONSTRAINT "$1" FOREIGN KEY (order_product_option_id) REFERENCES order_product_options(order_product_option_id);



ALTER TABLE ONLY order_product_option_float
    ADD CONSTRAINT "$1" FOREIGN KEY (order_product_option_id) REFERENCES order_product_options(order_product_option_id);



ALTER TABLE ONLY order_product_option_timestamp
    ADD CONSTRAINT "$1" FOREIGN KEY (order_product_option_id) REFERENCES order_product_options(order_product_option_id);



ALTER TABLE ONLY order_product_option_integer
    ADD CONSTRAINT "$1" FOREIGN KEY (order_product_option_id) REFERENCES order_product_options(order_product_option_id);



ALTER TABLE ONLY order_product_option_text
    ADD CONSTRAINT "$1" FOREIGN KEY (order_product_option_id) REFERENCES order_product_options(order_product_option_id);



ALTER TABLE ONLY order_address
    ADD CONSTRAINT "$1" FOREIGN KEY (order_id) REFERENCES order_entry(order_id);



ALTER TABLE ONLY order_address
    ADD CONSTRAINT "$2" FOREIGN KEY (address_type) REFERENCES lookup_orderaddress_types(code);



ALTER TABLE ONLY order_address
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY order_address
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY payment_creditcard
    ADD CONSTRAINT "$1" FOREIGN KEY (card_type) REFERENCES lookup_creditcard_types(code);



ALTER TABLE ONLY payment_creditcard
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY payment_creditcard
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY payment_creditcard
    ADD CONSTRAINT "$4" FOREIGN KEY (order_id) REFERENCES order_entry(order_id);



ALTER TABLE ONLY payment_eft
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY payment_eft
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY payment_eft
    ADD CONSTRAINT "$3" FOREIGN KEY (order_id) REFERENCES order_entry(order_id);



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT "$2" FOREIGN KEY (order_id) REFERENCES order_entry(order_id);



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT "$3" FOREIGN KEY (order_item_id) REFERENCES order_product(item_id);



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT "$4" FOREIGN KEY (status_id) REFERENCES lookup_order_status(code);



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY customer_product
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT "$1" FOREIGN KEY (customer_product_id) REFERENCES customer_product(customer_product_id);



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT "$2" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT "$3" FOREIGN KEY (order_id) REFERENCES order_entry(order_id);



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY customer_product_history
    ADD CONSTRAINT "$6" FOREIGN KEY (order_item_id) REFERENCES order_product(item_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$1" FOREIGN KEY (order_id) REFERENCES order_entry(order_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$4" FOREIGN KEY (payment_method_id) REFERENCES lookup_payment_methods(code);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$2" FOREIGN KEY (order_item_id) REFERENCES order_product(item_id);



ALTER TABLE ONLY order_payment
    ADD CONSTRAINT "$3" FOREIGN KEY (history_id) REFERENCES customer_product_history(history_id);



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



ALTER TABLE ONLY module_field_categorylink
    ADD CONSTRAINT "$1" FOREIGN KEY (module_id) REFERENCES permission_category(category_id);



ALTER TABLE ONLY custom_field_category
    ADD CONSTRAINT "$1" FOREIGN KEY (module_id) REFERENCES module_field_categorylink(category_id);



ALTER TABLE ONLY custom_field_group
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES custom_field_category(category_id);



ALTER TABLE ONLY custom_field_info
    ADD CONSTRAINT "$1" FOREIGN KEY (group_id) REFERENCES custom_field_group(group_id);



ALTER TABLE ONLY custom_field_lookup
    ADD CONSTRAINT "$1" FOREIGN KEY (field_id) REFERENCES custom_field_info(field_id);



ALTER TABLE ONLY custom_field_record
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES custom_field_category(category_id);



ALTER TABLE ONLY custom_field_record
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY custom_field_record
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY custom_field_data
    ADD CONSTRAINT "$1" FOREIGN KEY (record_id) REFERENCES custom_field_record(record_id);



ALTER TABLE ONLY custom_field_data
    ADD CONSTRAINT "$2" FOREIGN KEY (field_id) REFERENCES custom_field_info(field_id);



ALTER TABLE ONLY saved_criterialist
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY saved_criterialist
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY saved_criterialist
    ADD CONSTRAINT "$3" FOREIGN KEY ("owner") REFERENCES "access"(user_id);



ALTER TABLE ONLY campaign
    ADD CONSTRAINT "$1" FOREIGN KEY (approvedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY campaign
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY campaign
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY campaign_run
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id);



ALTER TABLE ONLY excluded_recipient
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id);



ALTER TABLE ONLY excluded_recipient
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY campaign_list_groups
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id);



ALTER TABLE ONLY campaign_list_groups
    ADD CONSTRAINT "$2" FOREIGN KEY (group_id) REFERENCES saved_criterialist(id);



ALTER TABLE ONLY active_campaign_groups
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id);



ALTER TABLE ONLY scheduled_recipient
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id);



ALTER TABLE ONLY scheduled_recipient
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY survey
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY survey
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY campaign_survey_link
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id);



ALTER TABLE ONLY campaign_survey_link
    ADD CONSTRAINT "$2" FOREIGN KEY (survey_id) REFERENCES survey(survey_id);



ALTER TABLE ONLY survey_questions
    ADD CONSTRAINT "$1" FOREIGN KEY (survey_id) REFERENCES survey(survey_id);



ALTER TABLE ONLY survey_questions
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_survey_types(code);



ALTER TABLE ONLY survey_items
    ADD CONSTRAINT "$1" FOREIGN KEY (question_id) REFERENCES survey_questions(question_id);



ALTER TABLE ONLY active_survey
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id);



ALTER TABLE ONLY active_survey
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_survey_types(code);



ALTER TABLE ONLY active_survey
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY active_survey
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY active_survey_questions
    ADD CONSTRAINT "$1" FOREIGN KEY (active_survey_id) REFERENCES active_survey(active_survey_id);



ALTER TABLE ONLY active_survey_questions
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_survey_types(code);



ALTER TABLE ONLY active_survey_items
    ADD CONSTRAINT "$1" FOREIGN KEY (question_id) REFERENCES active_survey_questions(question_id);



ALTER TABLE ONLY active_survey_responses
    ADD CONSTRAINT "$1" FOREIGN KEY (active_survey_id) REFERENCES active_survey(active_survey_id);



ALTER TABLE ONLY active_survey_answers
    ADD CONSTRAINT "$1" FOREIGN KEY (response_id) REFERENCES active_survey_responses(response_id);



ALTER TABLE ONLY active_survey_answers
    ADD CONSTRAINT "$2" FOREIGN KEY (question_id) REFERENCES active_survey_questions(question_id);



ALTER TABLE ONLY active_survey_answer_items
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES active_survey_items(item_id);



ALTER TABLE ONLY active_survey_answer_items
    ADD CONSTRAINT "$2" FOREIGN KEY (answer_id) REFERENCES active_survey_answers(answer_id);



ALTER TABLE ONLY active_survey_answer_avg
    ADD CONSTRAINT "$1" FOREIGN KEY (question_id) REFERENCES active_survey_questions(question_id);



ALTER TABLE ONLY active_survey_answer_avg
    ADD CONSTRAINT "$2" FOREIGN KEY (item_id) REFERENCES active_survey_items(item_id);



ALTER TABLE ONLY message
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY message
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY message
    ADD CONSTRAINT "$3" FOREIGN KEY (access_type) REFERENCES lookup_access_types(code);



ALTER TABLE ONLY message_template
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY message_template
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY saved_criteriaelement
    ADD CONSTRAINT "$1" FOREIGN KEY (id) REFERENCES saved_criterialist(id);



ALTER TABLE ONLY saved_criteriaelement
    ADD CONSTRAINT "$2" FOREIGN KEY (field) REFERENCES search_fields(id);



ALTER TABLE ONLY saved_criteriaelement
    ADD CONSTRAINT "$3" FOREIGN KEY (operatorid) REFERENCES field_types(id);



ALTER TABLE ONLY help_module
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id);



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id);



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$2" FOREIGN KEY (link_module_id) REFERENCES help_module(module_id);



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$3" FOREIGN KEY (nextcontent) REFERENCES help_contents(help_id);



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$4" FOREIGN KEY (prevcontent) REFERENCES help_contents(help_id);



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$5" FOREIGN KEY (upcontent) REFERENCES help_contents(help_id);



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$1" FOREIGN KEY (firstchild) REFERENCES help_tableof_contents(content_id);



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$2" FOREIGN KEY (nextsibling) REFERENCES help_tableof_contents(content_id);



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$3" FOREIGN KEY (parent) REFERENCES help_tableof_contents(content_id);



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$4" FOREIGN KEY (category_id) REFERENCES permission_category(category_id);



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT "$1" FOREIGN KEY (global_link_id) REFERENCES help_tableof_contents(content_id);



ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT "$2" FOREIGN KEY (linkto_content_id) REFERENCES help_contents(help_id);



ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$1" FOREIGN KEY (link_help_id) REFERENCES help_contents(help_id);



ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$2" FOREIGN KEY (link_feature_id) REFERENCES lookup_help_features(code);



ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$5" FOREIGN KEY (completedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT "$1" FOREIGN KEY (owning_module_id) REFERENCES help_module(module_id);



ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT "$2" FOREIGN KEY (linkto_content_id) REFERENCES help_contents(help_id);



ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT "$1" FOREIGN KEY (owning_module_id) REFERENCES help_module(module_id);



ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT "$4" FOREIGN KEY (completedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT "$1" FOREIGN KEY (link_help_id) REFERENCES help_contents(help_id);



ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT "$4" FOREIGN KEY (completedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_notes
    ADD CONSTRAINT "$1" FOREIGN KEY (link_help_id) REFERENCES help_contents(help_id);



ALTER TABLE ONLY help_notes
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_notes
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_notes
    ADD CONSTRAINT "$4" FOREIGN KEY (completedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_tips
    ADD CONSTRAINT "$1" FOREIGN KEY (link_help_id) REFERENCES help_contents(help_id);



ALTER TABLE ONLY help_tips
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY help_tips
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY sync_table
    ADD CONSTRAINT "$1" FOREIGN KEY (system_id) REFERENCES sync_system(system_id);



ALTER TABLE ONLY sync_map
    ADD CONSTRAINT "$1" FOREIGN KEY (client_id) REFERENCES sync_client(client_id);



ALTER TABLE ONLY sync_map
    ADD CONSTRAINT "$2" FOREIGN KEY (table_id) REFERENCES sync_table(table_id);



ALTER TABLE ONLY sync_conflict_log
    ADD CONSTRAINT "$1" FOREIGN KEY (client_id) REFERENCES sync_client(client_id);



ALTER TABLE ONLY sync_conflict_log
    ADD CONSTRAINT "$2" FOREIGN KEY (table_id) REFERENCES sync_table(table_id);



ALTER TABLE ONLY sync_log
    ADD CONSTRAINT "$1" FOREIGN KEY (system_id) REFERENCES sync_system(system_id);



ALTER TABLE ONLY sync_log
    ADD CONSTRAINT "$2" FOREIGN KEY (client_id) REFERENCES sync_client(client_id);



ALTER TABLE ONLY sync_transaction_log
    ADD CONSTRAINT "$1" FOREIGN KEY (log_id) REFERENCES sync_log(log_id);



ALTER TABLE ONLY process_log
    ADD CONSTRAINT "$1" FOREIGN KEY (system_id) REFERENCES sync_system(system_id);



ALTER TABLE ONLY process_log
    ADD CONSTRAINT "$2" FOREIGN KEY (client_id) REFERENCES sync_client(client_id);



ALTER TABLE ONLY autoguide_model
    ADD CONSTRAINT "$1" FOREIGN KEY (make_id) REFERENCES autoguide_make(make_id);



ALTER TABLE ONLY autoguide_vehicle
    ADD CONSTRAINT "$1" FOREIGN KEY (make_id) REFERENCES autoguide_make(make_id);



ALTER TABLE ONLY autoguide_vehicle
    ADD CONSTRAINT "$2" FOREIGN KEY (model_id) REFERENCES autoguide_model(model_id);



ALTER TABLE ONLY autoguide_inventory
    ADD CONSTRAINT "$1" FOREIGN KEY (vehicle_id) REFERENCES autoguide_vehicle(vehicle_id);



ALTER TABLE ONLY autoguide_inventory
    ADD CONSTRAINT "$2" FOREIGN KEY (account_id) REFERENCES organization(org_id);



ALTER TABLE ONLY autoguide_inventory_options
    ADD CONSTRAINT "$1" FOREIGN KEY (inventory_id) REFERENCES autoguide_inventory(inventory_id);



ALTER TABLE ONLY autoguide_ad_run
    ADD CONSTRAINT "$1" FOREIGN KEY (inventory_id) REFERENCES autoguide_inventory(inventory_id);



ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_revenue_types(code);



ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$3" FOREIGN KEY ("owner") REFERENCES "access"(user_id);



ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$1" FOREIGN KEY (revenue_id) REFERENCES revenue(id);



ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_revenue_types(code);



ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$3" FOREIGN KEY ("owner") REFERENCES "access"(user_id);



ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY task
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY task
    ADD CONSTRAINT "$2" FOREIGN KEY (priority) REFERENCES lookup_task_priority(code);



ALTER TABLE ONLY task
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY task
    ADD CONSTRAINT "$4" FOREIGN KEY (estimatedloetype) REFERENCES lookup_task_loe(code);



ALTER TABLE ONLY task
    ADD CONSTRAINT "$5" FOREIGN KEY ("owner") REFERENCES "access"(user_id);



ALTER TABLE ONLY task
    ADD CONSTRAINT "$6" FOREIGN KEY (category_id) REFERENCES lookup_task_category(code);



ALTER TABLE ONLY tasklink_contact
    ADD CONSTRAINT "$1" FOREIGN KEY (task_id) REFERENCES task(task_id);



ALTER TABLE ONLY tasklink_contact
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY tasklink_ticket
    ADD CONSTRAINT "$1" FOREIGN KEY (task_id) REFERENCES task(task_id);



ALTER TABLE ONLY tasklink_ticket
    ADD CONSTRAINT "$2" FOREIGN KEY (ticket_id) REFERENCES ticket(ticketid);



ALTER TABLE ONLY tasklink_project
    ADD CONSTRAINT "$1" FOREIGN KEY (task_id) REFERENCES task(task_id);



ALTER TABLE ONLY tasklink_project
    ADD CONSTRAINT "$2" FOREIGN KEY (project_id) REFERENCES projects(project_id);



ALTER TABLE ONLY taskcategory_project
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES lookup_task_category(code);



ALTER TABLE ONLY taskcategory_project
    ADD CONSTRAINT "$2" FOREIGN KEY (project_id) REFERENCES projects(project_id);



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



ALTER TABLE ONLY business_process_component_result_lookup
    ADD CONSTRAINT "$1" FOREIGN KEY (component_id) REFERENCES business_process_component_library(component_id);



ALTER TABLE ONLY business_process
    ADD CONSTRAINT "$1" FOREIGN KEY (link_module_id) REFERENCES permission_category(category_id);



ALTER TABLE ONLY business_process_component
    ADD CONSTRAINT "$1" FOREIGN KEY (process_id) REFERENCES business_process(process_id);



ALTER TABLE ONLY business_process_component
    ADD CONSTRAINT "$2" FOREIGN KEY (component_id) REFERENCES business_process_component_library(component_id);



ALTER TABLE ONLY business_process_component
    ADD CONSTRAINT "$3" FOREIGN KEY (parent_id) REFERENCES business_process_component(id);



ALTER TABLE ONLY business_process_parameter
    ADD CONSTRAINT "$1" FOREIGN KEY (process_id) REFERENCES business_process(process_id);



ALTER TABLE ONLY business_process_component_parameter
    ADD CONSTRAINT "$1" FOREIGN KEY (component_id) REFERENCES business_process_component(id);



ALTER TABLE ONLY business_process_component_parameter
    ADD CONSTRAINT "$2" FOREIGN KEY (parameter_id) REFERENCES business_process_parameter_library(parameter_id);



ALTER TABLE ONLY business_process_events
    ADD CONSTRAINT "$1" FOREIGN KEY (process_id) REFERENCES business_process(process_id);



ALTER TABLE ONLY business_process_hook_library
    ADD CONSTRAINT "$1" FOREIGN KEY (link_module_id) REFERENCES permission_category(category_id);



ALTER TABLE ONLY business_process_hook_triggers
    ADD CONSTRAINT "$1" FOREIGN KEY (hook_id) REFERENCES business_process_hook_library(hook_id);



ALTER TABLE ONLY business_process_hook
    ADD CONSTRAINT "$1" FOREIGN KEY (trigger_id) REFERENCES business_process_hook_triggers(trigger_id);



ALTER TABLE ONLY business_process_hook
    ADD CONSTRAINT "$2" FOREIGN KEY (process_id) REFERENCES business_process(process_id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$18" FOREIGN KEY (customer_product_id) REFERENCES customer_product(customer_product_id);



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$19" FOREIGN KEY (status_id) REFERENCES lookup_ticket_status(code);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$12" FOREIGN KEY (product_id) REFERENCES product_catalog(product_id);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$13" FOREIGN KEY (customer_product_id) REFERENCES customer_product(customer_product_id);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$11" FOREIGN KEY (opp_id) REFERENCES opportunity_header(opp_id);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$14" FOREIGN KEY (group_id) REFERENCES quote_group(group_id);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$15" FOREIGN KEY (delivery_id) REFERENCES lookup_quote_delivery(code);



ALTER TABLE ONLY quote_entry
    ADD CONSTRAINT "$16" FOREIGN KEY (logo_file_id) REFERENCES project_files(item_id);



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



ALTER TABLE ONLY quote_notes
    ADD CONSTRAINT "$1" FOREIGN KEY (quote_id) REFERENCES quote_entry(quote_id);



ALTER TABLE ONLY quote_notes
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY quote_notes
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



ALTER TABLE ONLY history
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY history
    ADD CONSTRAINT "$2" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY history
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY history
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



SELECT pg_catalog.setval('access_user_id_seq', 0, false);



SELECT pg_catalog.setval('lookup_industry_code_seq', 1, false);



SELECT pg_catalog.setval('access_log_id_seq', 1, false);



SELECT pg_catalog.setval('usage_log_usage_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_contact_types_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_account_types_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_department_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_orgaddress_type_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_orgemail_types_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_orgphone_types_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_im_types_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_im_services_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_contact_source_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_contact_rating_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_textmessage_typ_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_employment_type_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_locale_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_contactaddress__code_seq', 1, false);



SELECT pg_catalog.setval('lookup_contactemail_ty_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_contactphone_ty_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_access_types_code_seq', 1, false);



SELECT pg_catalog.setval('organization_org_id_seq', 0, false);



SELECT pg_catalog.setval('contact_contact_id_seq', 1, false);



SELECT pg_catalog.setval('contact_lead_skipped_map_map_id_seq', 1, false);



SELECT pg_catalog.setval('contact_lead_read_map_map_id_seq', 1, false);



SELECT pg_catalog.setval('role_role_id_seq', 1, false);



SELECT pg_catalog.setval('permission_cate_category_id_seq', 1, false);



SELECT pg_catalog.setval('permission_permission_id_seq', 1, false);



SELECT pg_catalog.setval('role_permission_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_stage_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_delivery_option_code_seq', 1, false);



SELECT pg_catalog.setval('news_rec_id_seq', 1, false);



SELECT pg_catalog.setval('organization_add_address_id_seq', 1, false);



SELECT pg_catalog.setval('organization__emailaddress__seq', 1, false);



SELECT pg_catalog.setval('organization_phone_phone_id_seq', 1, false);



SELECT pg_catalog.setval('contact_address_address_id_seq', 1, false);



SELECT pg_catalog.setval('contact_email_emailaddress__seq', 1, false);



SELECT pg_catalog.setval('contact_phone_phone_id_seq', 1, false);



SELECT pg_catalog.setval('contact_imaddress_address_id_seq', 1, false);



SELECT pg_catalog.setval('contact_textmessageaddress_address_id_seq', 1, false);



SELECT pg_catalog.setval('notification_notification_i_seq', 1, false);



SELECT pg_catalog.setval('cfsinbox_message_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_lists_lookup_id_seq', 1, false);



SELECT pg_catalog.setval('webdav_id_seq', 1, false);



SELECT pg_catalog.setval('category_editor_lookup_id_seq', 1, false);



SELECT pg_catalog.setval('viewpoint_viewpoint_id_seq', 1, false);



SELECT pg_catalog.setval('viewpoint_per_vp_permission_seq', 1, false);



SELECT pg_catalog.setval('report_report_id_seq', 1, false);



SELECT pg_catalog.setval('report_criteria_criteria_id_seq', 1, false);



SELECT pg_catalog.setval('report_criteria_parameter_parameter_id_seq', 1, false);



SELECT pg_catalog.setval('report_queue_queue_id_seq', 1, false);



SELECT pg_catalog.setval('report_queue_criteria_criteria_id_seq', 1, false);



SELECT pg_catalog.setval('action_list_code_seq', 1, false);



SELECT pg_catalog.setval('action_item_code_seq', 1, false);



SELECT pg_catalog.setval('action_item_log_code_seq', 1, false);



SELECT pg_catalog.setval('import_import_id_seq', 1, false);



SELECT pg_catalog.setval('database_version_version_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_relationship_types_type_id_seq', 1, false);



SELECT pg_catalog.setval('relationship_relationship_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_call_types_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_call_priority_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_call_reminder_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_call_result_result_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_opportunity_typ_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_opportunity_env_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_opportunity_com_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_opportunity_eve_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_opportunity_bud_code_seq', 1, false);



SELECT pg_catalog.setval('opportunity_header_opp_id_seq', 1, false);



SELECT pg_catalog.setval('opportunity_component_id_seq', 1, false);



SELECT pg_catalog.setval('call_log_call_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_project_activit_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_project_priorit_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_project_status_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_project_loe_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_project_role_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_project_cat_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_news_template_code_seq', 1, false);



SELECT pg_catalog.setval('projects_project_id_seq', 1, false);



SELECT pg_catalog.setval('project_requi_requirement_i_seq', 1, false);



SELECT pg_catalog.setval('project_assignmen_folder_id_seq', 1, false);



SELECT pg_catalog.setval('project_assig_assignment_id_seq', 1, false);



SELECT pg_catalog.setval('project_assignmen_status_id_seq', 1, false);



SELECT pg_catalog.setval('project_issue_cate_categ_id_seq', 1, false);



SELECT pg_catalog.setval('project_issues_issue_id_seq', 1, false);



SELECT pg_catalog.setval('project_issue_repl_reply_id_seq', 1, false);



SELECT pg_catalog.setval('project_folders_folder_id_seq', 1, false);



SELECT pg_catalog.setval('project_files_item_id_seq', 1, false);



SELECT pg_catalog.setval('project_news_category_category_id_seq', 1, false);



SELECT pg_catalog.setval('project_news_news_id_seq', 1, false);



SELECT pg_catalog.setval('project_requirements_map_map_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_project_permission_category_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_project_permission_code_seq', 1, false);



SELECT pg_catalog.setval('project_permissions_id_seq', 1, false);



SELECT pg_catalog.setval('project_accounts_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_currency_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_product_categor_code_seq', 1, false);



SELECT pg_catalog.setval('product_category_category_id_seq', 1, false);



SELECT pg_catalog.setval('product_category_map_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_product_type_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_product_manufac_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_product_format_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_product_shippin_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_product_ship_ti_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_product_tax_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_recurring_type_code_seq', 1, false);



SELECT pg_catalog.setval('product_catalog_product_id_seq', 1, false);



SELECT pg_catalog.setval('product_catalog_pricing_price_id_seq', 1, false);



SELECT pg_catalog.setval('package_package_id_seq', 1, false);



SELECT pg_catalog.setval('package_products_map_id_seq', 1, false);



SELECT pg_catalog.setval('product_catalog_category_map_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_product_conf_re_code_seq', 1, false);



SELECT pg_catalog.setval('product_option_configurator_configurator_id_seq', 1, false);



SELECT pg_catalog.setval('product_option_option_id_seq', 1, false);



SELECT pg_catalog.setval('product_option_values_value_id_seq', 1, false);



SELECT pg_catalog.setval('product_option_map_product_option_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_product_keyword_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_asset_status_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_sc_category_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_sc_type_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_response_model_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_phone_model_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_onsite_model_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_email_model_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_hours_reason_code_seq', 1, false);



SELECT pg_catalog.setval('service_contract_contract_id_seq', 1, false);



SELECT pg_catalog.setval('service_contract_hours_history_id_seq', 1, false);



SELECT pg_catalog.setval('service_contract_products_id_seq', 1, false);



SELECT pg_catalog.setval('asset_category_id_seq', 1, false);



SELECT pg_catalog.setval('asset_category_draft_id_seq', 1, false);



SELECT pg_catalog.setval('asset_asset_id_seq', 1, false);



SELECT pg_catalog.setval('ticket_level_code_seq', 1, false);



SELECT pg_catalog.setval('ticket_severity_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_ticketsource_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_ticket_status_code_seq', 1, false);



SELECT pg_catalog.setval('ticket_priority_code_seq', 1, false);



SELECT pg_catalog.setval('ticket_category_id_seq', 1, false);



SELECT pg_catalog.setval('ticket_category_draft_id_seq', 1, false);



SELECT pg_catalog.setval('ticket_ticketid_seq', 1, false);



SELECT pg_catalog.setval('ticketlog_id_seq', 1, false);



SELECT pg_catalog.setval('ticket_csstm_form_form_id_seq', 1, false);



SELECT pg_catalog.setval('ticket_activity_item_item_id_seq', 1, false);



SELECT pg_catalog.setval('ticket_sun_form_form_id_seq', 1, false);



SELECT pg_catalog.setval('trouble_asset_replacement_replacement_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_quote_status_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_quote_type_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_quote_terms_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_quote_source_code_seq', 1, false);



SELECT pg_catalog.setval('quote_entry_quote_id_seq', 1, false);



SELECT pg_catalog.setval('quote_product_item_id_seq', 1, false);



SELECT pg_catalog.setval('quote_product_options_quote_product_option_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_order_status_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_order_type_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_order_terms_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_order_source_code_seq', 1, false);



SELECT pg_catalog.setval('order_entry_order_id_seq', 1, false);



SELECT pg_catalog.setval('order_product_item_id_seq', 1, false);



SELECT pg_catalog.setval('order_product_status_order_product_status_id_seq', 1, false);



SELECT pg_catalog.setval('order_product_options_order_product_option_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_orderaddress_types_code_seq', 1, false);



SELECT pg_catalog.setval('order_address_address_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_payment_methods_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_creditcard_type_code_seq', 1, false);



SELECT pg_catalog.setval('payment_creditcard_creditcard_id_seq', 1, false);



SELECT pg_catalog.setval('payment_eft_bank_id_seq', 1, false);



SELECT pg_catalog.setval('customer_product_customer_product_id_seq', 1, false);



SELECT pg_catalog.setval('customer_product_history_history_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_payment_status_code_seq', 1, false);



SELECT pg_catalog.setval('order_payment_payment_id_seq', 1, false);



SELECT pg_catalog.setval('order_payment_status_payment_status_id_seq', 1, false);



SELECT pg_catalog.setval('module_field_categorylin_id_seq', 1, false);



SELECT pg_catalog.setval('custom_field_ca_category_id_seq', 1, false);



SELECT pg_catalog.setval('custom_field_group_group_id_seq', 1, false);



SELECT pg_catalog.setval('custom_field_info_field_id_seq', 1, false);



SELECT pg_catalog.setval('custom_field_lookup_code_seq', 1, false);



SELECT pg_catalog.setval('custom_field_reco_record_id_seq', 1, false);



SELECT pg_catalog.setval('saved_criterialist_id_seq', 1, false);



SELECT pg_catalog.setval('campaign_campaign_id_seq', 1, false);



SELECT pg_catalog.setval('campaign_run_id_seq', 1, false);



SELECT pg_catalog.setval('excluded_recipient_id_seq', 1, false);



SELECT pg_catalog.setval('active_campaign_groups_id_seq', 1, false);



SELECT pg_catalog.setval('scheduled_recipient_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_survey_types_code_seq', 1, false);



SELECT pg_catalog.setval('survey_survey_id_seq', 1, false);



SELECT pg_catalog.setval('survey_question_question_id_seq', 1, false);



SELECT pg_catalog.setval('survey_items_item_id_seq', 1, false);



SELECT pg_catalog.setval('active_survey_active_survey_seq', 1, false);



SELECT pg_catalog.setval('active_survey_q_question_id_seq', 1, false);



SELECT pg_catalog.setval('active_survey_items_item_id_seq', 1, false);



SELECT pg_catalog.setval('active_survey_r_response_id_seq', 1, false);



SELECT pg_catalog.setval('active_survey_ans_answer_id_seq', 1, false);



SELECT pg_catalog.setval('active_survey_answer_ite_id_seq', 1, false);



SELECT pg_catalog.setval('active_survey_answer_avg_id_seq', 1, false);



SELECT pg_catalog.setval('field_types_id_seq', 1, false);



SELECT pg_catalog.setval('search_fields_id_seq', 1, false);



SELECT pg_catalog.setval('message_id_seq', 1, false);



SELECT pg_catalog.setval('message_template_id_seq', 1, false);



SELECT pg_catalog.setval('help_module_module_id_seq', 1, false);



SELECT pg_catalog.setval('help_contents_help_id_seq', 1, false);



SELECT pg_catalog.setval('help_tableof_contents_content_id_seq', 1, false);



SELECT pg_catalog.setval('help_tableofcontentitem_links_link_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_help_features_code_seq', 1, false);



SELECT pg_catalog.setval('help_features_feature_id_seq', 1, false);



SELECT pg_catalog.setval('help_related_links_relatedlink_id_seq', 1, false);



SELECT pg_catalog.setval('help_faqs_faq_id_seq', 1, false);



SELECT pg_catalog.setval('help_business_rules_rule_id_seq', 1, false);



SELECT pg_catalog.setval('help_notes_note_id_seq', 1, false);



SELECT pg_catalog.setval('help_tips_tip_id_seq', 1, false);



SELECT pg_catalog.setval('sync_client_client_id_seq', 1, false);



SELECT pg_catalog.setval('sync_system_system_id_seq', 1, false);



SELECT pg_catalog.setval('sync_table_table_id_seq', 1, false);



SELECT pg_catalog.setval('sync_log_log_id_seq', 1, false);



SELECT pg_catalog.setval('sync_transact_transaction_i_seq', 1, false);



SELECT pg_catalog.setval('process_log_process_id_seq', 1, false);



SELECT pg_catalog.setval('autoguide_make_make_id_seq', 1, false);



SELECT pg_catalog.setval('autoguide_model_model_id_seq', 1, false);



SELECT pg_catalog.setval('autoguide_vehicl_vehicle_id_seq', 1, false);



SELECT pg_catalog.setval('autoguide_inve_inventory_id_seq', 1, false);



SELECT pg_catalog.setval('autoguide_options_option_id_seq', 1, false);



SELECT pg_catalog.setval('autoguide_ad_run_ad_run_id_seq', 1, false);



SELECT pg_catalog.setval('autoguide_ad_run_types_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_revenue_types_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_revenuedetail_t_code_seq', 1, false);



SELECT pg_catalog.setval('revenue_id_seq', 1, false);



SELECT pg_catalog.setval('revenue_detail_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_task_priority_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_task_loe_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_task_category_code_seq', 1, false);



SELECT pg_catalog.setval('task_task_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_document_store_permission_category_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_document_store_role_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_document_store_permission_code_seq', 1, false);



SELECT pg_catalog.setval('document_store_document_store_id_seq', 1, false);



SELECT pg_catalog.setval('document_store_permissions_id_seq', 1, false);



SELECT pg_catalog.setval('business_process_com_lb_id_seq', 1, false);



SELECT pg_catalog.setval('business_process_comp_re_id_seq', 1, false);



SELECT pg_catalog.setval('business_process_pa_lib_id_seq', 1, false);



SELECT pg_catalog.setval('business_process_process_id_seq', 1, false);



SELECT pg_catalog.setval('business_process_compone_id_seq', 1, false);



SELECT pg_catalog.setval('business_process_param_id_seq', 1, false);



SELECT pg_catalog.setval('business_process_comp_pa_id_seq', 1, false);



SELECT pg_catalog.setval('business_process_e_event_id_seq', 1, false);



SELECT pg_catalog.setval('business_process_hl_hook_id_seq', 1, false);



SELECT pg_catalog.setval('business_process_ho_trig_id_seq', 1, false);



SELECT pg_catalog.setval('business_process_ho_hook_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_quote_delivery_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_quote_condition_code_seq', 1, false);



SELECT pg_catalog.setval('quote_group_group_id_seq', 1000, false);



SELECT pg_catalog.setval('quote_condition_map_id_seq', 1, false);



SELECT pg_catalog.setval('quotelog_id_seq', 1, false);



SELECT pg_catalog.setval('lookup_quote_remarks_code_seq', 1, false);



SELECT pg_catalog.setval('quote_remark_map_id_seq', 1, false);



SELECT pg_catalog.setval('quote_notes_notes_id_seq', 1, false);



SELECT pg_catalog.setval('history_history_id_seq', 1, false);



COMMENT ON SCHEMA public IS 'Standard public schema';


