
BEGIN WORK;


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


INSERT INTO events VALUES (1, '0', '*/5', '*', '*', '*', '*', '*', 'org.aspcfs.apps.notifier.Notifier#doTask', '${FILEPATH}', 'true', true, '2003-11-13 08:11:37.411');
INSERT INTO events VALUES (2, '0', '*/1', '*', '*', '*', '*', '*', 'org.aspcfs.apps.reportRunner.ReportRunner#doTask', '${FILEPATH}', 'true', true, '2003-11-13 08:11:37.411');
INSERT INTO events VALUES (3, '0', '0', '*/12', '*', '*', '*', '*', 'org.aspcfs.apps.reportRunner.ReportCleanup#doTask', '${FILEPATH}', 'true', true, '2003-11-13 08:11:37.411');
INSERT INTO events VALUES (4, '0', '0', '0', '*', '*', '*', '*', 'org.aspcfs.modules.service.tasks.GetURL#doTask', 'http://${WEBSERVER.URL}/ProcessSystem.do?command=ClearGraphData', 'true', true, '2003-11-13 08:11:37.411');


ALTER TABLE ONLY sites
    ADD CONSTRAINT sites_pkey PRIMARY KEY (site_id);



ALTER TABLE ONLY events
    ADD CONSTRAINT events_pkey PRIMARY KEY (event_id);



ALTER TABLE ONLY events_log
    ADD CONSTRAINT events_log_pkey PRIMARY KEY (log_id);



ALTER TABLE ONLY events_log
    ADD CONSTRAINT "$1" FOREIGN KEY (event_id) REFERENCES events(event_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



SELECT pg_catalog.setval ('sites_site_id_seq', 1, false);



SELECT pg_catalog.setval ('events_event_id_seq', 4, true);



SELECT pg_catalog.setval ('events_log_log_id_seq', 1, false);


SET search_path = public, pg_catalog;


CREATE SEQUENCE access_user_id_seq
    START 0
    INCREMENT 1
    MAXVALUE 9223372036854775807
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
    timezone character varying(255) DEFAULT 'America/New_York',
    last_ip character varying(15),
    last_login timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    expires timestamp(3) without time zone,
    alias integer DEFAULT -1,
    assistant integer DEFAULT -1,
    enabled boolean DEFAULT true NOT NULL
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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



CREATE SEQUENCE lookup_instantmessenge_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE lookup_instantmessenger_types (
    code integer DEFAULT nextval('lookup_instantmessenge_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_employment_type_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE lookup_contactaddress_types (
    code integer DEFAULT nextval('lookup_contactaddress__code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_contactemail_ty_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE lookup_contactemail_types (
    code integer DEFAULT nextval('lookup_contactemail_ty_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_contactphone_ty_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 0
    INCREMENT 1
    MAXVALUE 9223372036854775807
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
    miner_only boolean DEFAULT 'f' NOT NULL,
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
    namesuffix character varying(80)
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
    imname character varying(30),
    imservice integer,
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
    access_type integer
);



CREATE TABLE role (
    role_id serial NOT NULL,
    role character varying(80) NOT NULL,
    description character varying(255) DEFAULT '' NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enabled boolean DEFAULT true NOT NULL
);



CREATE SEQUENCE permission_cate_category_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    categories boolean DEFAULT false,
    scheduled_events boolean DEFAULT false,
    object_events boolean DEFAULT false,
    reports boolean DEFAULT false
);



CREATE TABLE permission (
    permission_id serial NOT NULL,
    category_id integer NOT NULL,
    permission character varying(80) NOT NULL,
    permission_view boolean DEFAULT false NOT NULL,
    permission_add boolean DEFAULT false NOT NULL,
    permission_edit boolean DEFAULT false NOT NULL,
    permission_delete boolean DEFAULT false NOT NULL,
    description character varying(255) DEFAULT '' NOT NULL,
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    modifiedby integer NOT NULL
);



CREATE SEQUENCE organization__emailaddress__seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE organization_emailaddress (
    emailaddress_id integer DEFAULT nextval('organization__emailaddress__seq'::text) NOT NULL,
    org_id integer,
    emailaddress_type integer,
    email character varying(256),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE SEQUENCE organization_phone_phone_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    modifiedby integer NOT NULL
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
    modifiedby integer NOT NULL
);



CREATE SEQUENCE contact_email_emailaddress__seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE contact_emailaddress (
    emailaddress_id integer DEFAULT nextval('contact_email_emailaddress__seq'::text) NOT NULL,
    contact_id integer,
    emailaddress_type integer,
    email character varying(256),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
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
    modifiedby integer NOT NULL
);



CREATE SEQUENCE notification_notification_i_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    delete_flag boolean DEFAULT 'f'
);



CREATE TABLE cfsinbox_messagelink (
    id integer NOT NULL,
    sent_to integer NOT NULL,
    status integer DEFAULT 0 NOT NULL,
    viewed timestamp(3) without time zone,
    enabled boolean DEFAULT 't' NOT NULL,
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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



CREATE TABLE database_version (
    version_id serial NOT NULL,
    script_filename character varying(255) NOT NULL,
    script_version character varying(255) NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);



CREATE TABLE lookup_call_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_opportunity_typ_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE lookup_opportunity_types (
    code integer DEFAULT nextval('lookup_opportunity_typ_code_seq'::text) NOT NULL,
    order_id integer,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE opportunity_header (
    opp_id serial NOT NULL,
    description character varying(80),
    acctlink integer DEFAULT -1,
    contactlink integer DEFAULT -1,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
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
    closed timestamp without time zone,
    alert character varying(100),
    enabled boolean DEFAULT true NOT NULL,
    notes text
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
    alert character varying(100)
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
    style text DEFAULT '' NOT NULL,
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



CREATE TABLE ticket_priority (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    style text DEFAULT '' NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE ticket_category (
    id serial NOT NULL,
    cat_level integer DEFAULT 0 NOT NULL,
    parent_cat_code integer NOT NULL,
    description character varying(300) NOT NULL,
    full_description text DEFAULT '' NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE ticket_category_draft (
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
    cause text
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



CREATE SEQUENCE module_field_categorylin_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE custom_field_category (
    module_id integer NOT NULL,
    category_id integer DEFAULT nextval('custom_field_ca_category_id_seq'::text) NOT NULL,
    category_name character varying(255) NOT NULL,
    "level" integer DEFAULT 0,
    description text,
    start_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    end_date timestamp without time zone,
    default_item boolean DEFAULT false,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    enabled boolean DEFAULT true,
    multiple_records boolean DEFAULT false,
    read_only boolean DEFAULT false
);



CREATE SEQUENCE custom_field_group_group_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE custom_field_group (
    category_id integer NOT NULL,
    group_id integer DEFAULT nextval('custom_field_group_group_id_seq'::text) NOT NULL,
    group_name character varying(255) NOT NULL,
    "level" integer DEFAULT 0,
    description text,
    start_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    end_date timestamp without time zone,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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



CREATE SEQUENCE lookup_project_activit_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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



CREATE TABLE lookup_project_issues (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL
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
    modifiedby integer NOT NULL
);



CREATE SEQUENCE project_requi_requirement_i_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    modifiedby integer NOT NULL
);



CREATE SEQUENCE project_assig_assignment_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE project_assignments (
    assignment_id integer DEFAULT nextval('project_assig_assignment_id_seq'::text) NOT NULL,
    project_id integer NOT NULL,
    requirement_id integer,
    assignedby integer,
    user_assign_id integer,
    activity_id integer,
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
    modifiedby integer NOT NULL
);



CREATE SEQUENCE project_assignmen_status_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE project_assignments_status (
    status_id integer DEFAULT nextval('project_assignmen_status_id_seq'::text) NOT NULL,
    assignment_id integer NOT NULL,
    user_id integer NOT NULL,
    description text NOT NULL,
    status_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone
);



CREATE TABLE project_issues (
    issue_id serial NOT NULL,
    project_id integer NOT NULL,
    type_id integer,
    subject character varying(255) NOT NULL,
    message text NOT NULL,
    importance integer DEFAULT 0,
    enabled boolean DEFAULT true,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);



CREATE SEQUENCE project_issue_repl_reply_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE project_issue_replies (
    reply_id integer DEFAULT nextval('project_issue_repl_reply_id_seq'::text) NOT NULL,
    issue_id integer NOT NULL,
    reply_to integer DEFAULT 0,
    subject character varying(50) NOT NULL,
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
    parent integer
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
    modifiedby integer NOT NULL
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



CREATE TABLE project_team (
    project_id integer NOT NULL,
    user_id integer NOT NULL,
    userlevel integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    modifiedby integer NOT NULL
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
    "type" integer DEFAULT 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE active_survey_items (
    item_id integer DEFAULT nextval('active_survey_items_item_id_seq'::text) NOT NULL,
    question_id integer NOT NULL,
    "type" integer DEFAULT -1,
    description character varying(255)
);



CREATE SEQUENCE active_survey_r_response_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE active_survey_responses (
    response_id integer DEFAULT nextval('active_survey_r_response_id_seq'::text) NOT NULL,
    active_survey_id integer NOT NULL,
    contact_id integer DEFAULT -1 NOT NULL,
    unique_code character varying(255),
    ip_address character varying(15) NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);



CREATE SEQUENCE active_survey_ans_answer_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE active_survey_answer_items (
    id integer DEFAULT nextval('active_survey_answer_ite_id_seq'::text) NOT NULL,
    item_id integer NOT NULL,
    answer_id integer NOT NULL,
    comments text
);



CREATE SEQUENCE active_survey_answer_avg_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    anchor timestamp(3) without time zone
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE task (
    task_id serial NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    priority integer NOT NULL,
    description character varying(80),
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
    category_id integer
);



CREATE TABLE tasklink_contact (
    task_id integer NOT NULL,
    contact_id integer NOT NULL
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



CREATE SEQUENCE business_process_com_lb_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
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
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE business_process_parameter (
    id integer DEFAULT nextval('business_process_param_id_seq'::text) NOT NULL,
    process_id integer NOT NULL,
    param_name character varying(255),
    param_value character varying(4000),
    enabled boolean DEFAULT true NOT NULL
);



CREATE SEQUENCE business_process_comp_pa_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE business_process_component_parameter (
    id integer DEFAULT nextval('business_process_comp_pa_id_seq'::text) NOT NULL,
    component_id integer NOT NULL,
    parameter_id integer NOT NULL,
    param_value character varying(4000),
    enabled boolean DEFAULT true NOT NULL
);



CREATE SEQUENCE business_process_e_event_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE business_process_events (
    event_id integer DEFAULT nextval('business_process_e_event_id_seq'::text) NOT NULL,
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
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    process_id integer NOT NULL
);



CREATE TABLE business_process_log (
    process_name character varying(255) NOT NULL,
    anchor timestamp(3) without time zone NOT NULL
);



CREATE SEQUENCE business_process_hl_hook_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE business_process_hook_library (
    hook_id integer DEFAULT nextval('business_process_hl_hook_id_seq'::text) NOT NULL,
    link_module_id integer NOT NULL,
    hook_class character varying(255) NOT NULL,
    enabled boolean DEFAULT false
);



CREATE SEQUENCE business_process_ho_trig_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE business_process_hook_triggers (
    trigger_id integer DEFAULT nextval('business_process_ho_trig_id_seq'::text) NOT NULL,
    action_type_id integer NOT NULL,
    hook_id integer NOT NULL,
    enabled boolean DEFAULT false
);



CREATE SEQUENCE business_process_ho_hook_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE business_process_hook (
    id integer DEFAULT nextval('business_process_ho_hook_id_seq'::text) NOT NULL,
    trigger_id integer NOT NULL,
    process_id integer NOT NULL,
    enabled boolean DEFAULT false
);



INSERT INTO "access" VALUES (0, 'dhvadmin', '---', -1, 1, -1, 8, 18, NULL, 'America/New_York', NULL, '2004-01-26 09:17:45.061', 0, '2004-01-26 09:17:45.061', 0, '2004-01-26 09:17:45.061', NULL, -1, -1, true);



INSERT INTO lookup_industry VALUES (1, NULL, 'Automotive', false, 0, true);
INSERT INTO lookup_industry VALUES (2, NULL, 'Biotechnology', false, 0, true);
INSERT INTO lookup_industry VALUES (3, NULL, 'Broadcasting and Cable', false, 0, true);
INSERT INTO lookup_industry VALUES (4, NULL, 'Computer', false, 0, true);
INSERT INTO lookup_industry VALUES (5, NULL, 'Consulting', false, 0, true);
INSERT INTO lookup_industry VALUES (6, NULL, 'Defense', false, 0, true);
INSERT INTO lookup_industry VALUES (7, NULL, 'Energy', false, 0, true);
INSERT INTO lookup_industry VALUES (8, NULL, 'Financial Services', false, 0, true);
INSERT INTO lookup_industry VALUES (9, NULL, 'Food', false, 0, true);
INSERT INTO lookup_industry VALUES (10, NULL, 'Healthcare', false, 0, true);
INSERT INTO lookup_industry VALUES (11, NULL, 'Hospitality', false, 0, true);
INSERT INTO lookup_industry VALUES (12, NULL, 'Insurance', false, 0, true);
INSERT INTO lookup_industry VALUES (13, NULL, 'Internet', false, 0, true);
INSERT INTO lookup_industry VALUES (14, NULL, 'Law Firms', false, 0, true);
INSERT INTO lookup_industry VALUES (15, NULL, 'Media', false, 0, true);
INSERT INTO lookup_industry VALUES (16, NULL, 'Pharmaceuticals', false, 0, true);
INSERT INTO lookup_industry VALUES (17, NULL, 'Real Estate', false, 0, true);
INSERT INTO lookup_industry VALUES (18, NULL, 'Retail', false, 0, true);
INSERT INTO lookup_industry VALUES (19, NULL, 'Telecommunications', false, 0, true);
INSERT INTO lookup_industry VALUES (20, NULL, 'Transportation', false, 0, true);









INSERT INTO lookup_contact_types VALUES (1, 'Acquaintance', false, 0, true, NULL, 0);
INSERT INTO lookup_contact_types VALUES (2, 'Competitor', false, 0, true, NULL, 0);
INSERT INTO lookup_contact_types VALUES (3, 'Customer', false, 0, true, NULL, 0);
INSERT INTO lookup_contact_types VALUES (4, 'Friend', false, 0, true, NULL, 0);
INSERT INTO lookup_contact_types VALUES (5, 'Prospect', false, 0, true, NULL, 0);
INSERT INTO lookup_contact_types VALUES (6, 'Shareholder', false, 0, true, NULL, 0);
INSERT INTO lookup_contact_types VALUES (7, 'Vendor', false, 0, true, NULL, 0);
INSERT INTO lookup_contact_types VALUES (8, 'Accounting', false, 0, true, NULL, 1);
INSERT INTO lookup_contact_types VALUES (9, 'Administrative', false, 0, true, NULL, 1);
INSERT INTO lookup_contact_types VALUES (10, 'Business Development', false, 0, true, NULL, 1);
INSERT INTO lookup_contact_types VALUES (11, 'Customer Service', false, 0, true, NULL, 1);
INSERT INTO lookup_contact_types VALUES (12, 'Engineering', false, 0, true, NULL, 1);
INSERT INTO lookup_contact_types VALUES (13, 'Executive', false, 0, true, NULL, 1);
INSERT INTO lookup_contact_types VALUES (14, 'Finance', false, 0, true, NULL, 1);
INSERT INTO lookup_contact_types VALUES (15, 'Marketing', false, 0, true, NULL, 1);
INSERT INTO lookup_contact_types VALUES (16, 'Operations', false, 0, true, NULL, 1);
INSERT INTO lookup_contact_types VALUES (17, 'Procurement', false, 0, true, NULL, 1);
INSERT INTO lookup_contact_types VALUES (18, 'Sales', false, 0, true, NULL, 1);
INSERT INTO lookup_contact_types VALUES (19, 'Shipping/Receiving', false, 0, true, NULL, 1);
INSERT INTO lookup_contact_types VALUES (20, 'Technology', false, 0, true, NULL, 1);



INSERT INTO lookup_account_types VALUES (1, 'Small', false, 10, true);
INSERT INTO lookup_account_types VALUES (2, 'Medium', false, 20, true);
INSERT INTO lookup_account_types VALUES (3, 'Large', false, 30, true);
INSERT INTO lookup_account_types VALUES (4, 'Contract', false, 40, true);
INSERT INTO lookup_account_types VALUES (5, 'Territory 1: Northeast', false, 50, true);
INSERT INTO lookup_account_types VALUES (6, 'Territory 2: Southeast', false, 60, true);
INSERT INTO lookup_account_types VALUES (7, 'Territory 3: Midwest', false, 70, true);
INSERT INTO lookup_account_types VALUES (8, 'Territory 4: Northwest', false, 80, true);
INSERT INTO lookup_account_types VALUES (9, 'Territory 5: Southwest', false, 90, true);



INSERT INTO state VALUES ('AK', 'Alaska');
INSERT INTO state VALUES ('AL', 'Alabama');
INSERT INTO state VALUES ('AR', 'Arkansas');
INSERT INTO state VALUES ('AZ', 'Arizona');
INSERT INTO state VALUES ('CA', 'California');
INSERT INTO state VALUES ('CO', 'Colorado');
INSERT INTO state VALUES ('CT', 'Connecticut');
INSERT INTO state VALUES ('DE', 'Delaware');
INSERT INTO state VALUES ('FL', 'Florida');
INSERT INTO state VALUES ('GA', 'Georgia');
INSERT INTO state VALUES ('HI', 'Hawaii');
INSERT INTO state VALUES ('ID', 'Idaho');
INSERT INTO state VALUES ('IL', 'Illinois');
INSERT INTO state VALUES ('IN', 'Indiana');
INSERT INTO state VALUES ('KS', 'Kansas');
INSERT INTO state VALUES ('KY', 'Kentucky');
INSERT INTO state VALUES ('LA', 'Louisiana');
INSERT INTO state VALUES ('ME', 'Maine');
INSERT INTO state VALUES ('MD', 'Maryland');
INSERT INTO state VALUES ('MA', 'Massachusetts');
INSERT INTO state VALUES ('MI', 'Michigan');
INSERT INTO state VALUES ('MN', 'Minnesota');
INSERT INTO state VALUES ('MS', 'Mississippi');
INSERT INTO state VALUES ('MO', 'Mossouri');
INSERT INTO state VALUES ('MT', 'Montana');
INSERT INTO state VALUES ('NE', 'Nebraska');
INSERT INTO state VALUES ('NV', 'Nevada');
INSERT INTO state VALUES ('NH', 'New Hampshire');
INSERT INTO state VALUES ('NJ', 'New Jersey');
INSERT INTO state VALUES ('NM', 'New Mexico');
INSERT INTO state VALUES ('NY', 'New York');
INSERT INTO state VALUES ('NC', 'North Carolina');
INSERT INTO state VALUES ('ND', 'North Dakota');
INSERT INTO state VALUES ('OH', 'Ohio');
INSERT INTO state VALUES ('OK', 'Oklahoma');
INSERT INTO state VALUES ('OR', 'Oregon');
INSERT INTO state VALUES ('PA', 'Pennsylvania');
INSERT INTO state VALUES ('RI', 'Rhode Island');
INSERT INTO state VALUES ('SC', 'South Carolina');
INSERT INTO state VALUES ('SD', 'South Dakota');
INSERT INTO state VALUES ('TN', 'Tennessee');
INSERT INTO state VALUES ('TX', 'Texas');
INSERT INTO state VALUES ('UT', 'Utah');
INSERT INTO state VALUES ('VA', 'Virginia');
INSERT INTO state VALUES ('VT', 'Vermont');
INSERT INTO state VALUES ('WA', 'Washington');
INSERT INTO state VALUES ('DC', 'Washington D.C.');
INSERT INTO state VALUES ('WI', 'Wisconsin');
INSERT INTO state VALUES ('WV', 'West Virginia');
INSERT INTO state VALUES ('WY', 'Wyoming');



INSERT INTO lookup_department VALUES (1, 'Accounting', false, 0, true);
INSERT INTO lookup_department VALUES (2, 'Administration', false, 0, true);
INSERT INTO lookup_department VALUES (3, 'Billing', false, 0, true);
INSERT INTO lookup_department VALUES (4, 'Customer Relations', false, 0, true);
INSERT INTO lookup_department VALUES (5, 'Engineering', false, 0, true);
INSERT INTO lookup_department VALUES (6, 'Finance', false, 0, true);
INSERT INTO lookup_department VALUES (7, 'Human Resources', false, 0, true);
INSERT INTO lookup_department VALUES (8, 'Legal', false, 0, true);
INSERT INTO lookup_department VALUES (9, 'Marketing', false, 0, true);
INSERT INTO lookup_department VALUES (10, 'Operations', false, 0, true);
INSERT INTO lookup_department VALUES (11, 'Purchasing', false, 0, true);
INSERT INTO lookup_department VALUES (12, 'Sales', false, 0, true);
INSERT INTO lookup_department VALUES (13, 'Shipping/Receiving', false, 0, true);



INSERT INTO lookup_orgaddress_types VALUES (1, 'Primary', false, 10, true);
INSERT INTO lookup_orgaddress_types VALUES (2, 'Auxiliary', false, 20, true);
INSERT INTO lookup_orgaddress_types VALUES (3, 'Billing', false, 30, true);
INSERT INTO lookup_orgaddress_types VALUES (4, 'Shipping', false, 40, true);



INSERT INTO lookup_orgemail_types VALUES (1, 'Primary', false, 10, true);
INSERT INTO lookup_orgemail_types VALUES (2, 'Auxiliary', false, 20, true);



INSERT INTO lookup_orgphone_types VALUES (1, 'Main', false, 10, true);
INSERT INTO lookup_orgphone_types VALUES (2, 'Fax', false, 20, true);












INSERT INTO lookup_contactaddress_types VALUES (1, 'Business', false, 10, true);
INSERT INTO lookup_contactaddress_types VALUES (2, 'Home', false, 20, true);
INSERT INTO lookup_contactaddress_types VALUES (3, 'Other', false, 30, true);



INSERT INTO lookup_contactemail_types VALUES (1, 'Business', false, 10, true);
INSERT INTO lookup_contactemail_types VALUES (2, 'Personal', false, 20, true);
INSERT INTO lookup_contactemail_types VALUES (3, 'Other', false, 30, true);



INSERT INTO lookup_contactphone_types VALUES (1, 'Business', false, 10, true);
INSERT INTO lookup_contactphone_types VALUES (2, 'Business2', false, 20, true);
INSERT INTO lookup_contactphone_types VALUES (3, 'Business Fax', false, 30, true);
INSERT INTO lookup_contactphone_types VALUES (4, 'Home', false, 40, true);
INSERT INTO lookup_contactphone_types VALUES (5, 'Home2', false, 50, true);
INSERT INTO lookup_contactphone_types VALUES (6, 'Home Fax', false, 60, true);
INSERT INTO lookup_contactphone_types VALUES (7, 'Mobile', false, 70, true);
INSERT INTO lookup_contactphone_types VALUES (8, 'Pager', false, 80, true);
INSERT INTO lookup_contactphone_types VALUES (9, 'Other', false, 90, true);



INSERT INTO lookup_access_types VALUES (1, 626030330, 'Controlled-Hierarchy', true, 1, true, 626030335);
INSERT INTO lookup_access_types VALUES (2, 626030330, 'Public', false, 2, true, 626030334);
INSERT INTO lookup_access_types VALUES (3, 626030330, 'Personal', false, 3, true, 626030333);
INSERT INTO lookup_access_types VALUES (4, 626030331, 'Public', true, 1, true, 626030334);
INSERT INTO lookup_access_types VALUES (5, 626030332, 'Public', true, 1, true, 626030334);
INSERT INTO lookup_access_types VALUES (6, 707031028, 'Controlled-Hierarchy', true, 1, true, 626030335);
INSERT INTO lookup_access_types VALUES (7, 707031028, 'Public', false, 2, true, 626030334);
INSERT INTO lookup_access_types VALUES (8, 707031028, 'Personal', false, 3, true, 626030333);



INSERT INTO organization VALUES (0, 'My Company', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, false, NULL, NULL, '2004-01-26 09:17:45.076', 0, '2004-01-26 09:17:45.076', 0, true, NULL, 0, -1, -1, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);






INSERT INTO role VALUES (1, 'Administrator', 'Performs system configuration and maintenance', 0, '2004-01-26 09:18:16.133', 0, '2004-01-26 09:18:16.133', true);
INSERT INTO role VALUES (2, 'Operations Manager', 'Manages operations', 0, '2004-01-26 09:18:16.868', 0, '2004-01-26 09:18:16.868', true);
INSERT INTO role VALUES (3, 'Sales Manager', 'Manages all accounts and opportunities', 0, '2004-01-26 09:18:17.635', 0, '2004-01-26 09:18:17.635', true);
INSERT INTO role VALUES (4, 'Salesperson', 'Manages own accounts and opportunities', 0, '2004-01-26 09:18:18.467', 0, '2004-01-26 09:18:18.467', true);
INSERT INTO role VALUES (5, 'Customer Service Manager', 'Manages all tickets', 0, '2004-01-26 09:18:19.215', 0, '2004-01-26 09:18:19.215', true);
INSERT INTO role VALUES (6, 'Customer Service Representative', 'Manages own tickets', 0, '2004-01-26 09:18:19.743', 0, '2004-01-26 09:18:19.743', true);
INSERT INTO role VALUES (7, 'Marketing Manager', 'Manages communications', 0, '2004-01-26 09:18:20.27', 0, '2004-01-26 09:18:20.27', true);
INSERT INTO role VALUES (8, 'Accounting Manager', 'Reviews revenue and opportunities', 0, '2004-01-26 09:18:21.132', 0, '2004-01-26 09:18:21.132', true);
INSERT INTO role VALUES (9, 'HR Representative', 'Manages employee information', 0, '2004-01-26 09:18:21.862', 0, '2004-01-26 09:18:21.862', true);



INSERT INTO permission_category VALUES (1, 'Accounts', NULL, 600, true, true, true, true, false, false, false, false, true);
INSERT INTO permission_category VALUES (2, 'Contacts', NULL, 400, true, true, true, true, false, false, false, false, true);
INSERT INTO permission_category VALUES (3, 'Auto Guide', NULL, 700, false, false, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (4, 'Pipeline', NULL, 500, true, true, false, true, true, false, false, false, true);
INSERT INTO permission_category VALUES (5, 'Demo', NULL, 1500, false, false, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (6, 'Communications', NULL, 800, true, true, false, false, false, false, false, false, true);
INSERT INTO permission_category VALUES (7, 'Projects', NULL, 900, false, false, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (8, 'Help Desk', NULL, 1000, true, true, true, true, false, true, true, true, true);
INSERT INTO permission_category VALUES (9, 'Admin', NULL, 1200, true, true, false, false, false, false, false, false, true);
INSERT INTO permission_category VALUES (10, 'Help', NULL, 1300, true, true, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (11, 'System', NULL, 100, true, true, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (12, 'My Home Page', NULL, 200, true, true, false, false, false, false, false, false, true);
INSERT INTO permission_category VALUES (13, 'QA', NULL, 1400, false, false, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (14, 'Reports', NULL, 1100, true, true, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (15, 'Employees', NULL, 300, true, true, false, true, false, false, false, false, true);



INSERT INTO permission VALUES (1, 1, 'accounts', true, false, false, false, 'Access to Accounts module', 10, true, true, false);
INSERT INTO permission VALUES (2, 1, 'accounts-accounts', true, true, true, true, 'Account Records', 20, true, true, false);
INSERT INTO permission VALUES (3, 1, 'accounts-accounts-folders', true, true, true, true, 'Folders', 30, true, true, false);
INSERT INTO permission VALUES (4, 1, 'accounts-accounts-contacts', true, true, true, true, 'Contacts', 40, true, true, false);
INSERT INTO permission VALUES (5, 1, 'accounts-accounts-contacts-opportunities', true, true, true, true, 'Contact Opportunities', 50, true, true, false);
INSERT INTO permission VALUES (6, 1, 'accounts-accounts-contacts-calls', true, true, true, true, 'Contact Calls', 60, true, true, false);
INSERT INTO permission VALUES (7, 1, 'accounts-accounts-contacts-messages', true, true, true, true, 'Contact Messages', 70, true, true, false);
INSERT INTO permission VALUES (8, 1, 'accounts-accounts-opportunities', true, true, true, true, 'Opportunities', 80, true, true, false);
INSERT INTO permission VALUES (9, 1, 'accounts-accounts-tickets', true, true, true, true, 'Tickets', 90, true, true, false);
INSERT INTO permission VALUES (10, 1, 'accounts-accounts-tickets-tasks', true, true, true, true, 'Ticket Tasks', 100, true, true, false);
INSERT INTO permission VALUES (11, 1, 'accounts-accounts-tickets-folders', true, true, true, true, 'Ticket Folders', 110, true, true, false);
INSERT INTO permission VALUES (12, 1, 'accounts-accounts-tickets-documents', true, true, true, true, 'Ticket Documents', 120, true, true, false);
INSERT INTO permission VALUES (13, 1, 'accounts-accounts-documents', true, true, true, true, 'Documents', 130, true, true, false);
INSERT INTO permission VALUES (14, 1, 'accounts-accounts-reports', true, true, false, true, 'Export Account Data', 140, true, true, false);
INSERT INTO permission VALUES (15, 1, 'accounts-dashboard', true, false, false, false, 'Dashboard', 150, true, true, false);
INSERT INTO permission VALUES (16, 1, 'accounts-accounts-revenue', true, true, true, true, 'Revenue', 160, false, false, false);
INSERT INTO permission VALUES (17, 1, 'accounts-autoguide-inventory', true, true, true, true, 'Auto Guide Vehicle Inventory', 170, false, false, false);
INSERT INTO permission VALUES (18, 2, 'contacts', true, false, false, false, 'Access to Contacts module', 10, true, true, false);
INSERT INTO permission VALUES (19, 2, 'contacts-external_contacts', true, true, true, true, 'General Contact Records', 20, true, true, false);
INSERT INTO permission VALUES (20, 2, 'contacts-external_contacts-reports', true, true, false, true, 'Export Contact Data', 30, true, true, false);
INSERT INTO permission VALUES (21, 2, 'contacts-external_contacts-folders', true, true, true, true, 'Folders', 40, true, true, false);
INSERT INTO permission VALUES (22, 2, 'contacts-external_contacts-calls', true, true, true, true, 'Calls', 50, true, true, false);
INSERT INTO permission VALUES (23, 2, 'contacts-external_contacts-messages', true, false, false, false, 'Messages', 60, true, true, false);
INSERT INTO permission VALUES (24, 2, 'contacts-external_contacts-opportunities', true, true, true, true, 'Opportunities', 70, true, true, false);
INSERT INTO permission VALUES (25, 3, 'autoguide', true, false, false, false, 'Access to the Auto Guide module', 10, true, true, false);
INSERT INTO permission VALUES (26, 3, 'autoguide-adruns', false, false, true, false, 'Ad Run complete status', 20, true, true, false);
INSERT INTO permission VALUES (27, 4, 'pipeline', true, false, false, false, 'Access to Pipeline module', 10, true, true, true);
INSERT INTO permission VALUES (28, 4, 'pipeline-opportunities', true, true, true, true, 'Opportunity Records', 20, true, true, false);
INSERT INTO permission VALUES (29, 4, 'pipeline-dashboard', true, false, false, false, 'Dashboard', 30, true, true, false);
INSERT INTO permission VALUES (30, 4, 'pipeline-reports', true, true, false, true, 'Export Opportunity Data', 40, true, true, false);
INSERT INTO permission VALUES (31, 4, 'pipeline-opportunities-calls', true, true, true, true, 'Calls', 50, true, true, false);
INSERT INTO permission VALUES (32, 4, 'pipeline-opportunities-documents', true, true, true, true, 'Documents', 60, true, true, false);
INSERT INTO permission VALUES (33, 5, 'demo', true, true, true, true, 'Access to Demo/Non-working features', 10, true, true, false);
INSERT INTO permission VALUES (34, 6, 'campaign', true, false, false, false, 'Access to Communications module', 10, true, true, false);
INSERT INTO permission VALUES (35, 6, 'campaign-dashboard', true, false, false, false, 'Dashboard', 20, true, true, false);
INSERT INTO permission VALUES (36, 6, 'campaign-campaigns', true, true, true, true, 'Campaign Records', 30, true, true, false);
INSERT INTO permission VALUES (37, 6, 'campaign-campaigns-groups', true, true, true, true, 'Group Records', 40, true, true, false);
INSERT INTO permission VALUES (38, 6, 'campaign-campaigns-messages', true, true, true, true, 'Message Records', 50, true, true, false);
INSERT INTO permission VALUES (39, 6, 'campaign-campaigns-surveys', true, true, true, true, 'Survey Records', 60, true, true, false);
INSERT INTO permission VALUES (40, 7, 'projects', true, false, false, false, 'Access to Project Management module', 10, true, true, false);
INSERT INTO permission VALUES (41, 7, 'projects-personal', true, false, false, false, 'Personal View', 20, true, true, false);
INSERT INTO permission VALUES (42, 7, 'projects-enterprise', true, false, false, false, 'Enterprise View', 30, true, true, false);
INSERT INTO permission VALUES (43, 7, 'projects-projects', true, true, true, true, 'Project Records', 40, true, true, false);
INSERT INTO permission VALUES (44, 8, 'tickets', true, false, false, false, 'Access to Help Desk module', 10, true, true, false);
INSERT INTO permission VALUES (45, 8, 'tickets-tickets', true, true, true, true, 'Ticket Records', 20, true, true, false);
INSERT INTO permission VALUES (46, 8, 'tickets-reports', true, true, true, true, 'Export Ticket Data', 30, true, true, false);
INSERT INTO permission VALUES (47, 8, 'tickets-tickets-tasks', true, true, true, true, 'Tasks', 40, true, true, false);
INSERT INTO permission VALUES (48, 9, 'admin', true, false, false, false, 'Access to Admin module', 10, true, true, false);
INSERT INTO permission VALUES (49, 9, 'admin-users', true, true, true, true, 'Users', 20, true, true, false);
INSERT INTO permission VALUES (50, 9, 'admin-roles', true, true, true, true, 'Roles', 30, true, true, false);
INSERT INTO permission VALUES (51, 9, 'admin-usage', true, false, false, false, 'System Usage', 40, true, true, false);
INSERT INTO permission VALUES (52, 9, 'admin-sysconfig', true, false, true, false, 'System Configuration', 50, true, true, false);
INSERT INTO permission VALUES (53, 9, 'admin-sysconfig-lists', true, false, true, false, 'Configure Lookup Lists', 60, true, true, false);
INSERT INTO permission VALUES (54, 9, 'admin-sysconfig-folders', true, true, true, true, 'Configure Custom Folders & Fields', 70, true, true, false);
INSERT INTO permission VALUES (55, 9, 'admin-object-workflow', true, true, true, true, 'Configure Object Workflow', 80, true, true, false);
INSERT INTO permission VALUES (56, 9, 'admin-sysconfig-categories', true, true, true, true, 'Categories', 90, true, true, false);
INSERT INTO permission VALUES (57, 10, 'help', true, false, false, false, 'Access to Help System', 10, true, true, false);
INSERT INTO permission VALUES (58, 11, 'globalitems-search', true, false, false, false, 'Access to Global Search', 10, true, true, false);
INSERT INTO permission VALUES (59, 11, 'globalitems-myitems', true, false, false, false, 'Access to My Items', 20, true, true, false);
INSERT INTO permission VALUES (60, 11, 'globalitems-recentitems', true, false, false, false, 'Access to Recent Items', 30, true, true, false);
INSERT INTO permission VALUES (61, 12, 'myhomepage', true, false, false, false, 'Access to My Home Page module', 10, true, true, false);
INSERT INTO permission VALUES (62, 12, 'myhomepage-dashboard', true, false, false, false, 'View Performance Dashboard', 20, true, true, false);
INSERT INTO permission VALUES (63, 12, 'myhomepage-miner', true, true, false, true, 'Industry News records', 30, false, false, false);
INSERT INTO permission VALUES (64, 12, 'myhomepage-inbox', true, false, false, false, 'My Mailbox', 40, true, true, false);
INSERT INTO permission VALUES (65, 12, 'myhomepage-tasks', true, true, true, true, 'My Tasks', 50, true, true, false);
INSERT INTO permission VALUES (66, 12, 'myhomepage-reassign', true, false, true, false, 'Re-assign Items', 60, true, true, false);
INSERT INTO permission VALUES (67, 12, 'myhomepage-profile', true, false, false, false, 'My Profile', 70, true, true, false);
INSERT INTO permission VALUES (68, 12, 'myhomepage-profile-personal', true, false, true, false, 'Personal Information', 80, true, true, false);
INSERT INTO permission VALUES (69, 12, 'myhomepage-profile-settings', true, false, true, false, 'Settings', 90, false, false, false);
INSERT INTO permission VALUES (70, 12, 'myhomepage-profile-password', false, false, true, false, 'Password', 100, true, true, false);
INSERT INTO permission VALUES (71, 12, 'myhomepage-action-lists', true, true, true, true, 'My Action Lists', 110, true, true, false);
INSERT INTO permission VALUES (72, 13, 'qa', true, true, true, true, 'Access to QA Tool', 10, true, true, false);
INSERT INTO permission VALUES (73, 14, 'reports', true, false, false, false, 'Access to Reports module', 10, true, true, false);
INSERT INTO permission VALUES (74, 15, 'employees', true, false, false, false, 'Access to Employee module', 10, true, true, false);
INSERT INTO permission VALUES (75, 15, 'contacts-internal_contacts', true, true, true, true, 'Employees', 20, true, true, false);



INSERT INTO role_permission VALUES (1, 1, 61, true, false, false, false);
INSERT INTO role_permission VALUES (2, 1, 62, true, false, false, false);
INSERT INTO role_permission VALUES (3, 1, 63, true, true, false, true);
INSERT INTO role_permission VALUES (4, 1, 64, true, false, false, false);
INSERT INTO role_permission VALUES (5, 1, 65, true, true, true, true);
INSERT INTO role_permission VALUES (6, 1, 67, true, false, false, false);
INSERT INTO role_permission VALUES (7, 1, 68, true, false, true, false);
INSERT INTO role_permission VALUES (8, 1, 69, true, false, true, false);
INSERT INTO role_permission VALUES (9, 1, 70, false, false, true, false);
INSERT INTO role_permission VALUES (10, 1, 66, true, false, true, false);
INSERT INTO role_permission VALUES (11, 1, 18, true, false, false, false);
INSERT INTO role_permission VALUES (12, 1, 19, true, true, true, true);
INSERT INTO role_permission VALUES (13, 1, 1, true, false, false, false);
INSERT INTO role_permission VALUES (14, 1, 2, true, true, true, true);
INSERT INTO role_permission VALUES (15, 1, 3, true, true, true, true);
INSERT INTO role_permission VALUES (16, 1, 4, true, true, true, true);
INSERT INTO role_permission VALUES (17, 1, 5, true, true, true, true);
INSERT INTO role_permission VALUES (18, 1, 6, true, true, true, true);
INSERT INTO role_permission VALUES (19, 1, 7, true, true, true, true);
INSERT INTO role_permission VALUES (20, 1, 8, true, true, true, true);
INSERT INTO role_permission VALUES (21, 1, 9, true, true, true, true);
INSERT INTO role_permission VALUES (22, 1, 10, true, true, true, true);
INSERT INTO role_permission VALUES (23, 1, 11, true, true, true, true);
INSERT INTO role_permission VALUES (24, 1, 12, true, true, true, true);
INSERT INTO role_permission VALUES (25, 1, 13, true, true, true, true);
INSERT INTO role_permission VALUES (26, 1, 14, true, true, false, true);
INSERT INTO role_permission VALUES (27, 1, 34, true, false, false, false);
INSERT INTO role_permission VALUES (28, 1, 35, true, false, false, false);
INSERT INTO role_permission VALUES (29, 1, 36, true, true, true, true);
INSERT INTO role_permission VALUES (30, 1, 44, true, false, false, false);
INSERT INTO role_permission VALUES (31, 1, 45, true, true, true, true);
INSERT INTO role_permission VALUES (32, 1, 46, true, true, true, true);
INSERT INTO role_permission VALUES (33, 1, 47, true, true, true, true);
INSERT INTO role_permission VALUES (34, 1, 74, true, true, true, false);
INSERT INTO role_permission VALUES (35, 1, 75, true, true, true, true);
INSERT INTO role_permission VALUES (36, 1, 73, true, false, false, false);
INSERT INTO role_permission VALUES (37, 1, 48, true, false, false, false);
INSERT INTO role_permission VALUES (38, 1, 49, true, true, true, true);
INSERT INTO role_permission VALUES (39, 1, 50, true, true, true, true);
INSERT INTO role_permission VALUES (40, 1, 52, true, false, true, false);
INSERT INTO role_permission VALUES (41, 1, 51, true, false, false, false);
INSERT INTO role_permission VALUES (42, 1, 53, true, false, true, false);
INSERT INTO role_permission VALUES (43, 1, 54, true, true, true, true);
INSERT INTO role_permission VALUES (44, 1, 55, true, true, true, true);
INSERT INTO role_permission VALUES (45, 1, 56, true, true, true, true);
INSERT INTO role_permission VALUES (46, 1, 57, true, false, false, false);
INSERT INTO role_permission VALUES (47, 1, 58, true, false, false, false);
INSERT INTO role_permission VALUES (48, 1, 59, true, false, false, false);
INSERT INTO role_permission VALUES (49, 1, 60, true, false, false, false);
INSERT INTO role_permission VALUES (50, 2, 61, true, false, false, false);
INSERT INTO role_permission VALUES (51, 2, 62, true, false, false, false);
INSERT INTO role_permission VALUES (52, 2, 64, true, false, false, false);
INSERT INTO role_permission VALUES (53, 2, 65, true, true, true, true);
INSERT INTO role_permission VALUES (54, 2, 66, true, false, true, false);
INSERT INTO role_permission VALUES (55, 2, 67, true, false, false, false);
INSERT INTO role_permission VALUES (56, 2, 68, true, false, true, false);
INSERT INTO role_permission VALUES (57, 2, 69, true, false, true, false);
INSERT INTO role_permission VALUES (58, 2, 70, false, false, true, false);
INSERT INTO role_permission VALUES (59, 2, 71, true, true, true, true);
INSERT INTO role_permission VALUES (60, 2, 18, true, false, false, false);
INSERT INTO role_permission VALUES (61, 2, 19, true, true, true, true);
INSERT INTO role_permission VALUES (62, 2, 20, true, true, false, true);
INSERT INTO role_permission VALUES (63, 2, 21, true, true, true, true);
INSERT INTO role_permission VALUES (64, 2, 22, true, true, true, true);
INSERT INTO role_permission VALUES (65, 2, 23, true, false, false, false);
INSERT INTO role_permission VALUES (66, 2, 24, true, true, true, true);
INSERT INTO role_permission VALUES (67, 2, 27, true, false, false, false);
INSERT INTO role_permission VALUES (68, 2, 28, true, true, true, true);
INSERT INTO role_permission VALUES (69, 2, 29, true, false, false, false);
INSERT INTO role_permission VALUES (70, 2, 30, true, true, false, true);
INSERT INTO role_permission VALUES (71, 2, 31, true, true, true, true);
INSERT INTO role_permission VALUES (72, 2, 32, true, true, true, true);
INSERT INTO role_permission VALUES (73, 2, 1, true, false, false, false);
INSERT INTO role_permission VALUES (74, 2, 2, true, true, true, true);
INSERT INTO role_permission VALUES (75, 2, 3, true, true, true, false);
INSERT INTO role_permission VALUES (76, 2, 4, true, true, true, true);
INSERT INTO role_permission VALUES (77, 2, 5, true, true, true, true);
INSERT INTO role_permission VALUES (78, 2, 6, true, true, true, true);
INSERT INTO role_permission VALUES (79, 2, 7, true, true, true, true);
INSERT INTO role_permission VALUES (80, 2, 8, true, true, true, true);
INSERT INTO role_permission VALUES (81, 2, 9, true, true, true, true);
INSERT INTO role_permission VALUES (82, 2, 13, true, true, true, true);
INSERT INTO role_permission VALUES (83, 2, 14, true, true, false, true);
INSERT INTO role_permission VALUES (84, 2, 16, true, true, true, true);
INSERT INTO role_permission VALUES (85, 2, 12, true, true, true, true);
INSERT INTO role_permission VALUES (86, 2, 11, true, true, true, true);
INSERT INTO role_permission VALUES (87, 2, 10, true, true, true, true);
INSERT INTO role_permission VALUES (88, 2, 34, true, false, false, false);
INSERT INTO role_permission VALUES (89, 2, 35, true, false, false, false);
INSERT INTO role_permission VALUES (90, 2, 36, true, true, true, true);
INSERT INTO role_permission VALUES (91, 2, 37, true, true, true, true);
INSERT INTO role_permission VALUES (92, 2, 38, true, true, true, true);
INSERT INTO role_permission VALUES (93, 2, 39, true, true, true, true);
INSERT INTO role_permission VALUES (94, 2, 44, true, false, false, false);
INSERT INTO role_permission VALUES (95, 2, 45, true, true, true, false);
INSERT INTO role_permission VALUES (96, 2, 46, true, true, true, true);
INSERT INTO role_permission VALUES (97, 2, 47, true, true, true, false);
INSERT INTO role_permission VALUES (98, 2, 74, true, false, false, false);
INSERT INTO role_permission VALUES (99, 2, 75, true, false, false, false);
INSERT INTO role_permission VALUES (100, 2, 73, true, false, false, false);
INSERT INTO role_permission VALUES (101, 2, 57, true, false, false, false);
INSERT INTO role_permission VALUES (102, 2, 58, true, false, false, false);
INSERT INTO role_permission VALUES (103, 2, 59, true, false, false, false);
INSERT INTO role_permission VALUES (104, 2, 60, true, false, false, false);
INSERT INTO role_permission VALUES (105, 3, 61, true, false, false, false);
INSERT INTO role_permission VALUES (106, 3, 62, true, false, false, false);
INSERT INTO role_permission VALUES (107, 3, 64, true, false, false, false);
INSERT INTO role_permission VALUES (108, 3, 65, true, true, true, true);
INSERT INTO role_permission VALUES (109, 3, 66, true, false, true, false);
INSERT INTO role_permission VALUES (110, 3, 67, true, false, false, false);
INSERT INTO role_permission VALUES (111, 3, 68, true, false, true, false);
INSERT INTO role_permission VALUES (112, 3, 69, true, false, true, false);
INSERT INTO role_permission VALUES (113, 3, 70, false, false, true, false);
INSERT INTO role_permission VALUES (114, 3, 71, true, true, true, true);
INSERT INTO role_permission VALUES (115, 3, 18, true, false, false, false);
INSERT INTO role_permission VALUES (116, 3, 19, true, true, true, true);
INSERT INTO role_permission VALUES (117, 3, 20, true, true, false, true);
INSERT INTO role_permission VALUES (118, 3, 21, true, true, true, true);
INSERT INTO role_permission VALUES (119, 3, 22, true, true, true, true);
INSERT INTO role_permission VALUES (120, 3, 23, true, false, false, false);
INSERT INTO role_permission VALUES (121, 3, 24, true, true, true, true);
INSERT INTO role_permission VALUES (122, 3, 27, true, false, false, false);
INSERT INTO role_permission VALUES (123, 3, 28, true, true, true, true);
INSERT INTO role_permission VALUES (124, 3, 29, true, false, false, false);
INSERT INTO role_permission VALUES (125, 3, 30, true, true, false, true);
INSERT INTO role_permission VALUES (126, 3, 31, true, true, true, true);
INSERT INTO role_permission VALUES (127, 3, 32, true, true, true, true);
INSERT INTO role_permission VALUES (128, 3, 1, true, false, false, false);
INSERT INTO role_permission VALUES (129, 3, 2, true, true, true, true);
INSERT INTO role_permission VALUES (130, 3, 3, true, true, true, false);
INSERT INTO role_permission VALUES (131, 3, 4, true, true, true, true);
INSERT INTO role_permission VALUES (132, 3, 5, true, true, true, true);
INSERT INTO role_permission VALUES (133, 3, 6, true, true, true, true);
INSERT INTO role_permission VALUES (134, 3, 7, true, true, true, true);
INSERT INTO role_permission VALUES (135, 3, 8, true, true, true, true);
INSERT INTO role_permission VALUES (136, 3, 9, true, true, true, true);
INSERT INTO role_permission VALUES (137, 3, 13, true, true, true, true);
INSERT INTO role_permission VALUES (138, 3, 14, true, true, false, true);
INSERT INTO role_permission VALUES (139, 3, 15, true, false, false, false);
INSERT INTO role_permission VALUES (140, 3, 16, true, true, true, true);
INSERT INTO role_permission VALUES (141, 3, 12, true, true, true, true);
INSERT INTO role_permission VALUES (142, 3, 11, true, true, true, true);
INSERT INTO role_permission VALUES (143, 3, 10, true, true, true, true);
INSERT INTO role_permission VALUES (144, 3, 34, true, false, false, false);
INSERT INTO role_permission VALUES (145, 3, 35, true, false, false, false);
INSERT INTO role_permission VALUES (146, 3, 36, true, true, true, true);
INSERT INTO role_permission VALUES (147, 3, 37, true, true, true, true);
INSERT INTO role_permission VALUES (148, 3, 38, true, true, true, true);
INSERT INTO role_permission VALUES (149, 3, 39, true, true, true, true);
INSERT INTO role_permission VALUES (150, 3, 44, true, false, false, false);
INSERT INTO role_permission VALUES (151, 3, 45, true, true, true, false);
INSERT INTO role_permission VALUES (152, 3, 46, true, true, true, true);
INSERT INTO role_permission VALUES (153, 3, 47, true, true, true, false);
INSERT INTO role_permission VALUES (154, 3, 74, true, false, false, false);
INSERT INTO role_permission VALUES (155, 3, 75, true, false, false, false);
INSERT INTO role_permission VALUES (156, 3, 73, true, false, false, false);
INSERT INTO role_permission VALUES (157, 3, 57, true, false, false, false);
INSERT INTO role_permission VALUES (158, 3, 58, true, false, false, false);
INSERT INTO role_permission VALUES (159, 3, 59, true, false, false, false);
INSERT INTO role_permission VALUES (160, 3, 60, true, false, false, false);
INSERT INTO role_permission VALUES (161, 4, 61, true, false, false, false);
INSERT INTO role_permission VALUES (162, 4, 62, true, false, false, false);
INSERT INTO role_permission VALUES (163, 4, 64, true, false, false, false);
INSERT INTO role_permission VALUES (164, 4, 65, true, true, true, true);
INSERT INTO role_permission VALUES (165, 4, 67, true, false, false, false);
INSERT INTO role_permission VALUES (166, 4, 68, true, false, true, false);
INSERT INTO role_permission VALUES (167, 4, 69, true, false, true, false);
INSERT INTO role_permission VALUES (168, 4, 70, false, false, true, false);
INSERT INTO role_permission VALUES (169, 4, 71, true, true, true, true);
INSERT INTO role_permission VALUES (170, 4, 18, true, false, false, false);
INSERT INTO role_permission VALUES (171, 4, 19, true, true, true, true);
INSERT INTO role_permission VALUES (172, 4, 20, true, true, false, true);
INSERT INTO role_permission VALUES (173, 4, 22, true, true, true, true);
INSERT INTO role_permission VALUES (174, 4, 23, true, true, true, true);
INSERT INTO role_permission VALUES (175, 4, 24, true, false, false, false);
INSERT INTO role_permission VALUES (176, 4, 27, true, false, false, false);
INSERT INTO role_permission VALUES (177, 4, 28, true, true, true, false);
INSERT INTO role_permission VALUES (178, 4, 29, true, false, false, false);
INSERT INTO role_permission VALUES (179, 4, 30, true, true, false, true);
INSERT INTO role_permission VALUES (180, 4, 31, true, true, true, true);
INSERT INTO role_permission VALUES (181, 4, 32, true, true, true, true);
INSERT INTO role_permission VALUES (182, 4, 1, true, false, false, false);
INSERT INTO role_permission VALUES (183, 4, 2, true, true, true, false);
INSERT INTO role_permission VALUES (184, 4, 3, true, true, true, false);
INSERT INTO role_permission VALUES (185, 4, 4, true, true, true, false);
INSERT INTO role_permission VALUES (186, 4, 5, true, true, true, false);
INSERT INTO role_permission VALUES (187, 4, 6, true, true, true, false);
INSERT INTO role_permission VALUES (188, 4, 7, true, true, true, false);
INSERT INTO role_permission VALUES (189, 4, 8, true, true, true, false);
INSERT INTO role_permission VALUES (190, 4, 9, true, true, true, false);
INSERT INTO role_permission VALUES (191, 4, 13, true, true, true, false);
INSERT INTO role_permission VALUES (192, 4, 14, true, true, false, true);
INSERT INTO role_permission VALUES (193, 4, 15, true, false, false, false);
INSERT INTO role_permission VALUES (194, 4, 16, true, true, true, false);
INSERT INTO role_permission VALUES (195, 4, 12, true, true, true, false);
INSERT INTO role_permission VALUES (196, 4, 11, true, true, true, false);
INSERT INTO role_permission VALUES (197, 4, 10, true, true, true, false);
INSERT INTO role_permission VALUES (198, 4, 34, true, false, false, false);
INSERT INTO role_permission VALUES (199, 4, 35, true, false, false, false);
INSERT INTO role_permission VALUES (200, 4, 36, true, true, true, true);
INSERT INTO role_permission VALUES (201, 4, 37, true, true, true, true);
INSERT INTO role_permission VALUES (202, 4, 38, true, true, true, true);
INSERT INTO role_permission VALUES (203, 4, 39, true, true, true, true);
INSERT INTO role_permission VALUES (204, 4, 44, true, false, false, false);
INSERT INTO role_permission VALUES (205, 4, 45, true, true, true, false);
INSERT INTO role_permission VALUES (206, 4, 46, true, true, false, true);
INSERT INTO role_permission VALUES (207, 4, 47, true, true, true, false);
INSERT INTO role_permission VALUES (208, 4, 74, true, false, false, false);
INSERT INTO role_permission VALUES (209, 4, 75, true, false, false, false);
INSERT INTO role_permission VALUES (210, 4, 73, true, false, false, false);
INSERT INTO role_permission VALUES (211, 4, 57, true, false, false, false);
INSERT INTO role_permission VALUES (212, 4, 58, true, false, false, false);
INSERT INTO role_permission VALUES (213, 4, 59, true, false, false, false);
INSERT INTO role_permission VALUES (214, 4, 60, true, false, false, false);
INSERT INTO role_permission VALUES (215, 5, 61, true, false, false, false);
INSERT INTO role_permission VALUES (216, 5, 62, true, false, false, false);
INSERT INTO role_permission VALUES (217, 5, 64, true, false, false, false);
INSERT INTO role_permission VALUES (218, 5, 65, true, true, true, true);
INSERT INTO role_permission VALUES (219, 5, 66, true, false, true, false);
INSERT INTO role_permission VALUES (220, 5, 67, true, false, false, false);
INSERT INTO role_permission VALUES (221, 5, 68, true, false, true, false);
INSERT INTO role_permission VALUES (222, 5, 69, true, false, true, false);
INSERT INTO role_permission VALUES (223, 5, 70, false, false, true, false);
INSERT INTO role_permission VALUES (224, 5, 1, true, false, false, false);
INSERT INTO role_permission VALUES (225, 5, 2, true, true, true, false);
INSERT INTO role_permission VALUES (226, 5, 3, true, true, true, false);
INSERT INTO role_permission VALUES (227, 5, 4, true, true, true, false);
INSERT INTO role_permission VALUES (228, 5, 6, true, true, true, false);
INSERT INTO role_permission VALUES (229, 5, 7, true, true, true, false);
INSERT INTO role_permission VALUES (230, 5, 9, true, true, true, false);
INSERT INTO role_permission VALUES (231, 5, 13, true, true, true, false);
INSERT INTO role_permission VALUES (232, 5, 14, true, true, false, true);
INSERT INTO role_permission VALUES (233, 5, 12, true, true, true, false);
INSERT INTO role_permission VALUES (234, 5, 11, true, true, true, false);
INSERT INTO role_permission VALUES (235, 5, 10, true, true, true, false);
INSERT INTO role_permission VALUES (236, 5, 34, true, false, false, false);
INSERT INTO role_permission VALUES (237, 5, 35, true, false, false, false);
INSERT INTO role_permission VALUES (238, 5, 36, true, true, true, true);
INSERT INTO role_permission VALUES (239, 5, 37, true, true, true, true);
INSERT INTO role_permission VALUES (240, 5, 38, true, true, true, true);
INSERT INTO role_permission VALUES (241, 5, 39, true, true, true, true);
INSERT INTO role_permission VALUES (242, 5, 44, true, false, false, false);
INSERT INTO role_permission VALUES (243, 5, 45, true, true, true, true);
INSERT INTO role_permission VALUES (244, 5, 46, true, true, true, true);
INSERT INTO role_permission VALUES (245, 5, 47, true, true, true, true);
INSERT INTO role_permission VALUES (246, 5, 74, true, false, false, false);
INSERT INTO role_permission VALUES (247, 5, 75, true, false, false, false);
INSERT INTO role_permission VALUES (248, 5, 73, true, false, false, false);
INSERT INTO role_permission VALUES (249, 5, 57, true, false, false, false);
INSERT INTO role_permission VALUES (250, 5, 58, true, false, false, false);
INSERT INTO role_permission VALUES (251, 5, 59, true, false, false, false);
INSERT INTO role_permission VALUES (252, 5, 60, true, false, false, false);
INSERT INTO role_permission VALUES (253, 6, 61, true, false, false, false);
INSERT INTO role_permission VALUES (254, 6, 62, true, false, false, false);
INSERT INTO role_permission VALUES (255, 6, 64, true, false, false, false);
INSERT INTO role_permission VALUES (256, 6, 65, true, true, true, true);
INSERT INTO role_permission VALUES (257, 6, 66, true, false, true, false);
INSERT INTO role_permission VALUES (258, 6, 67, true, false, false, false);
INSERT INTO role_permission VALUES (259, 6, 68, true, false, true, false);
INSERT INTO role_permission VALUES (260, 6, 69, true, false, true, false);
INSERT INTO role_permission VALUES (261, 6, 70, false, false, true, false);
INSERT INTO role_permission VALUES (262, 6, 1, true, false, false, false);
INSERT INTO role_permission VALUES (263, 6, 2, true, true, true, false);
INSERT INTO role_permission VALUES (264, 6, 3, true, true, true, false);
INSERT INTO role_permission VALUES (265, 6, 4, true, true, true, false);
INSERT INTO role_permission VALUES (266, 6, 6, true, true, true, false);
INSERT INTO role_permission VALUES (267, 6, 7, true, true, true, false);
INSERT INTO role_permission VALUES (268, 6, 9, true, true, true, false);
INSERT INTO role_permission VALUES (269, 6, 13, true, true, true, false);
INSERT INTO role_permission VALUES (270, 6, 14, true, true, false, true);
INSERT INTO role_permission VALUES (271, 6, 12, true, true, true, false);
INSERT INTO role_permission VALUES (272, 6, 11, true, true, true, false);
INSERT INTO role_permission VALUES (273, 6, 10, true, true, true, false);
INSERT INTO role_permission VALUES (274, 6, 34, true, false, false, false);
INSERT INTO role_permission VALUES (275, 6, 35, true, false, false, false);
INSERT INTO role_permission VALUES (276, 6, 36, true, true, true, true);
INSERT INTO role_permission VALUES (277, 6, 37, true, true, true, true);
INSERT INTO role_permission VALUES (278, 6, 38, true, true, true, true);
INSERT INTO role_permission VALUES (279, 6, 39, true, true, true, true);
INSERT INTO role_permission VALUES (280, 6, 44, true, false, false, false);
INSERT INTO role_permission VALUES (281, 6, 45, true, true, true, false);
INSERT INTO role_permission VALUES (282, 6, 46, true, true, true, false);
INSERT INTO role_permission VALUES (283, 6, 47, true, true, true, false);
INSERT INTO role_permission VALUES (284, 6, 74, true, false, false, false);
INSERT INTO role_permission VALUES (285, 6, 75, true, false, false, false);
INSERT INTO role_permission VALUES (286, 6, 73, true, false, false, false);
INSERT INTO role_permission VALUES (287, 6, 57, true, false, false, false);
INSERT INTO role_permission VALUES (288, 6, 58, true, false, false, false);
INSERT INTO role_permission VALUES (289, 6, 59, true, false, false, false);
INSERT INTO role_permission VALUES (290, 6, 60, true, false, false, false);
INSERT INTO role_permission VALUES (291, 7, 61, true, false, false, false);
INSERT INTO role_permission VALUES (292, 7, 62, true, false, false, false);
INSERT INTO role_permission VALUES (293, 7, 64, true, false, false, false);
INSERT INTO role_permission VALUES (294, 7, 65, true, true, true, true);
INSERT INTO role_permission VALUES (295, 7, 66, true, false, true, false);
INSERT INTO role_permission VALUES (296, 7, 67, true, false, false, false);
INSERT INTO role_permission VALUES (297, 7, 68, true, false, true, false);
INSERT INTO role_permission VALUES (298, 7, 69, true, false, true, false);
INSERT INTO role_permission VALUES (299, 7, 70, false, false, true, false);
INSERT INTO role_permission VALUES (300, 7, 71, true, true, true, true);
INSERT INTO role_permission VALUES (301, 7, 18, true, false, false, false);
INSERT INTO role_permission VALUES (302, 7, 19, true, true, true, true);
INSERT INTO role_permission VALUES (303, 7, 20, true, true, false, true);
INSERT INTO role_permission VALUES (304, 7, 21, true, true, true, true);
INSERT INTO role_permission VALUES (305, 7, 22, true, true, true, true);
INSERT INTO role_permission VALUES (306, 7, 23, true, false, false, false);
INSERT INTO role_permission VALUES (307, 7, 24, true, true, true, true);
INSERT INTO role_permission VALUES (308, 7, 27, true, false, false, false);
INSERT INTO role_permission VALUES (309, 7, 28, true, true, true, true);
INSERT INTO role_permission VALUES (310, 7, 29, true, false, false, false);
INSERT INTO role_permission VALUES (311, 7, 30, true, true, false, true);
INSERT INTO role_permission VALUES (312, 7, 31, true, true, true, true);
INSERT INTO role_permission VALUES (313, 7, 32, true, true, true, true);
INSERT INTO role_permission VALUES (314, 7, 1, true, false, false, false);
INSERT INTO role_permission VALUES (315, 7, 2, true, true, true, false);
INSERT INTO role_permission VALUES (316, 7, 3, true, true, true, false);
INSERT INTO role_permission VALUES (317, 7, 4, true, true, true, false);
INSERT INTO role_permission VALUES (318, 7, 5, true, true, true, false);
INSERT INTO role_permission VALUES (319, 7, 6, true, true, true, false);
INSERT INTO role_permission VALUES (320, 7, 7, true, true, true, false);
INSERT INTO role_permission VALUES (321, 7, 8, true, true, true, false);
INSERT INTO role_permission VALUES (322, 7, 9, true, true, true, false);
INSERT INTO role_permission VALUES (323, 7, 13, true, true, true, false);
INSERT INTO role_permission VALUES (324, 7, 14, true, true, false, true);
INSERT INTO role_permission VALUES (325, 7, 15, true, false, false, false);
INSERT INTO role_permission VALUES (326, 7, 16, true, true, true, false);
INSERT INTO role_permission VALUES (327, 7, 12, true, true, true, false);
INSERT INTO role_permission VALUES (328, 7, 11, true, true, true, false);
INSERT INTO role_permission VALUES (329, 7, 10, true, true, true, false);
INSERT INTO role_permission VALUES (330, 7, 34, true, false, false, false);
INSERT INTO role_permission VALUES (331, 7, 35, true, false, false, false);
INSERT INTO role_permission VALUES (332, 7, 36, true, true, true, true);
INSERT INTO role_permission VALUES (333, 7, 37, true, true, true, true);
INSERT INTO role_permission VALUES (334, 7, 38, true, true, true, true);
INSERT INTO role_permission VALUES (335, 7, 39, true, true, true, true);
INSERT INTO role_permission VALUES (336, 7, 44, true, false, false, false);
INSERT INTO role_permission VALUES (337, 7, 45, true, true, true, false);
INSERT INTO role_permission VALUES (338, 7, 46, true, true, true, false);
INSERT INTO role_permission VALUES (339, 7, 47, true, true, true, false);
INSERT INTO role_permission VALUES (340, 7, 74, true, false, false, false);
INSERT INTO role_permission VALUES (341, 7, 75, true, false, false, false);
INSERT INTO role_permission VALUES (342, 7, 73, true, false, false, false);
INSERT INTO role_permission VALUES (343, 7, 57, true, false, false, false);
INSERT INTO role_permission VALUES (344, 7, 58, true, false, false, false);
INSERT INTO role_permission VALUES (345, 7, 59, true, false, false, false);
INSERT INTO role_permission VALUES (346, 7, 60, true, false, false, false);
INSERT INTO role_permission VALUES (347, 8, 61, true, false, false, false);
INSERT INTO role_permission VALUES (348, 8, 62, true, false, false, false);
INSERT INTO role_permission VALUES (349, 8, 64, true, false, false, false);
INSERT INTO role_permission VALUES (350, 8, 65, true, true, true, true);
INSERT INTO role_permission VALUES (351, 8, 66, true, false, true, false);
INSERT INTO role_permission VALUES (352, 8, 67, true, false, false, false);
INSERT INTO role_permission VALUES (353, 8, 68, true, false, true, false);
INSERT INTO role_permission VALUES (354, 8, 69, true, false, true, false);
INSERT INTO role_permission VALUES (355, 8, 70, false, false, true, false);
INSERT INTO role_permission VALUES (356, 8, 18, true, false, false, false);
INSERT INTO role_permission VALUES (357, 8, 19, true, true, true, true);
INSERT INTO role_permission VALUES (358, 8, 20, true, true, false, true);
INSERT INTO role_permission VALUES (359, 8, 21, true, true, true, true);
INSERT INTO role_permission VALUES (360, 8, 22, true, true, true, true);
INSERT INTO role_permission VALUES (361, 8, 23, true, false, false, false);
INSERT INTO role_permission VALUES (362, 8, 24, true, true, true, true);
INSERT INTO role_permission VALUES (363, 8, 27, true, false, false, false);
INSERT INTO role_permission VALUES (364, 8, 28, true, false, false, false);
INSERT INTO role_permission VALUES (365, 8, 29, true, false, false, false);
INSERT INTO role_permission VALUES (366, 8, 30, true, true, false, true);
INSERT INTO role_permission VALUES (367, 8, 31, true, false, false, false);
INSERT INTO role_permission VALUES (368, 8, 32, true, false, false, false);
INSERT INTO role_permission VALUES (369, 8, 1, true, false, false, false);
INSERT INTO role_permission VALUES (370, 8, 2, true, true, true, true);
INSERT INTO role_permission VALUES (371, 8, 3, true, true, true, false);
INSERT INTO role_permission VALUES (372, 8, 4, true, true, true, true);
INSERT INTO role_permission VALUES (373, 8, 6, true, false, false, false);
INSERT INTO role_permission VALUES (374, 8, 7, true, false, false, false);
INSERT INTO role_permission VALUES (375, 8, 8, true, false, false, false);
INSERT INTO role_permission VALUES (376, 8, 9, true, false, false, false);
INSERT INTO role_permission VALUES (377, 8, 13, true, false, false, false);
INSERT INTO role_permission VALUES (378, 8, 14, true, true, false, true);
INSERT INTO role_permission VALUES (379, 8, 16, true, true, true, true);
INSERT INTO role_permission VALUES (380, 8, 12, true, false, false, false);
INSERT INTO role_permission VALUES (381, 8, 11, true, false, false, false);
INSERT INTO role_permission VALUES (382, 8, 10, true, false, false, false);
INSERT INTO role_permission VALUES (383, 8, 34, true, false, false, false);
INSERT INTO role_permission VALUES (384, 8, 35, true, false, false, false);
INSERT INTO role_permission VALUES (385, 8, 36, true, true, true, true);
INSERT INTO role_permission VALUES (386, 8, 37, true, true, true, true);
INSERT INTO role_permission VALUES (387, 8, 38, true, true, true, true);
INSERT INTO role_permission VALUES (388, 8, 39, true, true, true, true);
INSERT INTO role_permission VALUES (389, 8, 44, true, false, false, false);
INSERT INTO role_permission VALUES (390, 8, 45, true, false, false, false);
INSERT INTO role_permission VALUES (391, 8, 46, true, true, true, true);
INSERT INTO role_permission VALUES (392, 8, 47, true, false, false, false);
INSERT INTO role_permission VALUES (393, 8, 74, true, false, false, false);
INSERT INTO role_permission VALUES (394, 8, 75, true, false, false, false);
INSERT INTO role_permission VALUES (395, 8, 73, true, false, false, false);
INSERT INTO role_permission VALUES (396, 8, 57, true, false, false, false);
INSERT INTO role_permission VALUES (397, 8, 58, true, false, false, false);
INSERT INTO role_permission VALUES (398, 8, 59, true, false, false, false);
INSERT INTO role_permission VALUES (399, 8, 60, true, false, false, false);
INSERT INTO role_permission VALUES (400, 9, 61, true, false, false, false);
INSERT INTO role_permission VALUES (401, 9, 62, true, false, false, false);
INSERT INTO role_permission VALUES (402, 9, 64, true, false, false, false);
INSERT INTO role_permission VALUES (403, 9, 65, true, true, true, true);
INSERT INTO role_permission VALUES (404, 9, 66, true, false, true, false);
INSERT INTO role_permission VALUES (405, 9, 67, true, false, false, false);
INSERT INTO role_permission VALUES (406, 9, 68, true, false, true, false);
INSERT INTO role_permission VALUES (407, 9, 69, true, false, true, false);
INSERT INTO role_permission VALUES (408, 9, 70, false, false, true, false);
INSERT INTO role_permission VALUES (409, 9, 34, true, false, false, false);
INSERT INTO role_permission VALUES (410, 9, 35, true, false, false, false);
INSERT INTO role_permission VALUES (411, 9, 36, true, true, true, true);
INSERT INTO role_permission VALUES (412, 9, 37, true, true, true, true);
INSERT INTO role_permission VALUES (413, 9, 38, true, true, true, true);
INSERT INTO role_permission VALUES (414, 9, 39, true, true, true, true);
INSERT INTO role_permission VALUES (415, 9, 74, true, true, true, true);
INSERT INTO role_permission VALUES (416, 9, 75, true, true, true, true);
INSERT INTO role_permission VALUES (417, 9, 73, true, false, false, false);
INSERT INTO role_permission VALUES (418, 9, 57, true, false, false, false);
INSERT INTO role_permission VALUES (419, 9, 58, true, false, false, false);
INSERT INTO role_permission VALUES (420, 9, 59, true, false, false, false);
INSERT INTO role_permission VALUES (421, 9, 60, true, false, false, false);



INSERT INTO lookup_stage VALUES (1, 1, 'Prospecting', false, 1, true);
INSERT INTO lookup_stage VALUES (2, 2, 'Qualification', false, 2, true);
INSERT INTO lookup_stage VALUES (3, 3, 'Needs Analysis', false, 3, true);
INSERT INTO lookup_stage VALUES (4, 4, 'Value Proposition', false, 4, true);
INSERT INTO lookup_stage VALUES (5, 5, 'Perception Analysis', false, 5, true);
INSERT INTO lookup_stage VALUES (6, 6, 'Proposal/Price Quote', false, 6, true);
INSERT INTO lookup_stage VALUES (7, 7, 'Negotiation/Review', false, 7, true);
INSERT INTO lookup_stage VALUES (8, 8, 'Closed Won', false, 8, true);
INSERT INTO lookup_stage VALUES (9, 9, 'Closed Lost', false, 9, true);



INSERT INTO lookup_delivery_options VALUES (1, 'Email only', false, 1, true);
INSERT INTO lookup_delivery_options VALUES (2, 'Fax only', false, 2, true);
INSERT INTO lookup_delivery_options VALUES (3, 'Letter only', false, 3, true);
INSERT INTO lookup_delivery_options VALUES (4, 'Email then Fax', false, 4, true);
INSERT INTO lookup_delivery_options VALUES (5, 'Email then Letter', false, 5, true);
INSERT INTO lookup_delivery_options VALUES (6, 'Email, Fax, then Letter', false, 6, true);







































INSERT INTO lookup_lists_lookup VALUES (1, 1, 1, 'lookupList', 'lookup_account_types', 10, 'Account Types', '2004-01-26 09:18:14.505', 1);
INSERT INTO lookup_lists_lookup VALUES (2, 1, 2, 'lookupList', 'lookup_revenue_types', 20, 'Revenue Types', '2004-01-26 09:18:14.547', 1);
INSERT INTO lookup_lists_lookup VALUES (3, 1, 3, 'contactType', 'lookup_contact_types', 30, 'Contact Types', '2004-01-26 09:18:14.566', 1);
INSERT INTO lookup_lists_lookup VALUES (4, 2, 1, 'contactType', 'lookup_contact_types', 10, 'Types', '2004-01-26 09:18:14.866', 2);
INSERT INTO lookup_lists_lookup VALUES (5, 2, 2, 'lookupList', 'lookup_contactemail_types', 20, 'Email Types', '2004-01-26 09:18:14.872', 2);
INSERT INTO lookup_lists_lookup VALUES (6, 2, 3, 'lookupList', 'lookup_contactaddress_types', 30, 'Address Types', '2004-01-26 09:18:14.896', 2);
INSERT INTO lookup_lists_lookup VALUES (7, 2, 4, 'lookupList', 'lookup_contactphone_types', 40, 'Phone Types', '2004-01-26 09:18:14.902', 2);
INSERT INTO lookup_lists_lookup VALUES (8, 4, 1, 'lookupList', 'lookup_stage', 10, 'Stage', '2004-01-26 09:18:15.103', 4);
INSERT INTO lookup_lists_lookup VALUES (9, 4, 2, 'lookupList', 'lookup_opportunity_types', 20, 'Opportunity Types', '2004-01-26 09:18:15.117', 4);
INSERT INTO lookup_lists_lookup VALUES (10, 8, 1, 'lookupList', 'lookup_ticketsource', 10, 'Ticket Source', '2004-01-26 09:18:15.465', 8);
INSERT INTO lookup_lists_lookup VALUES (11, 8, 2, 'lookupList', 'ticket_severity', 20, 'Ticket Severity', '2004-01-26 09:18:15.488', 8);
INSERT INTO lookup_lists_lookup VALUES (12, 8, 3, 'lookupList', 'ticket_priority', 30, 'Ticket Priority', '2004-01-26 09:18:15.495', 8);
INSERT INTO lookup_lists_lookup VALUES (13, 15, 1111031132, 'lookupList', 'lookup_department', 10, 'Departments', '2004-01-26 09:18:16.065', 15);









INSERT INTO report VALUES (1, 1, NULL, 'accounts_type.xml', 1, 'Accounts by Type', 'What are my accounts by type?', '2004-01-26 09:18:14.585', 0, '2004-01-26 09:18:14.585', 0, true, false);
INSERT INTO report VALUES (2, 1, NULL, 'accounts_recent.xml', 1, 'Accounts by Date Added', 'What are my recent accounts?', '2004-01-26 09:18:14.674', 0, '2004-01-26 09:18:14.674', 0, true, false);
INSERT INTO report VALUES (3, 1, NULL, 'accounts_expire.xml', 1, 'Accounts by Contract End Date', 'Which accounts are expiring?', '2004-01-26 09:18:14.677', 0, '2004-01-26 09:18:14.677', 0, true, false);
INSERT INTO report VALUES (4, 1, NULL, 'accounts_current.xml', 1, 'Current Accounts', 'What are my current accounts?', '2004-01-26 09:18:14.71', 0, '2004-01-26 09:18:14.71', 0, true, false);
INSERT INTO report VALUES (5, 1, NULL, 'accounts_contacts.xml', 1, 'Account Contacts', 'Who are the contacts in each account?', '2004-01-26 09:18:14.717', 0, '2004-01-26 09:18:14.717', 0, true, false);
INSERT INTO report VALUES (6, 1, NULL, 'folder_accounts.xml', 1, 'Account Folders', 'What is the folder data for each account?', '2004-01-26 09:18:14.736', 0, '2004-01-26 09:18:14.736', 0, true, false);
INSERT INTO report VALUES (7, 2, NULL, 'contacts_user.xml', 1, 'Contacts', 'Who are my contacts?', '2004-01-26 09:18:14.926', 0, '2004-01-26 09:18:14.926', 0, true, false);
INSERT INTO report VALUES (8, 4, NULL, 'opportunity_pipeline.xml', 1, 'Opportunities by Stage', 'What are my upcoming opportunities by stage?', '2004-01-26 09:18:15.135', 0, '2004-01-26 09:18:15.135', 0, true, false);
INSERT INTO report VALUES (9, 4, NULL, 'opportunity_account.xml', 1, 'Opportunities by Account', 'What are all the accounts associated with my opportunities?', '2004-01-26 09:18:15.141', 0, '2004-01-26 09:18:15.141', 0, true, false);
INSERT INTO report VALUES (10, 4, NULL, 'opportunity_owner.xml', 1, 'Opportunities by Owner', 'What are all the opportunities based on ownership?', '2004-01-26 09:18:15.159', 0, '2004-01-26 09:18:15.159', 0, true, false);
INSERT INTO report VALUES (11, 4, NULL, 'opportunity_contact.xml', 1, 'Opportunity Contacts', 'Who are the contacts of my opportunities?', '2004-01-26 09:18:15.165', 0, '2004-01-26 09:18:15.165', 0, true, false);
INSERT INTO report VALUES (12, 6, NULL, 'campaign.xml', 1, 'Campaigns by date', 'What are my active campaigns?', '2004-01-26 09:18:15.31', 0, '2004-01-26 09:18:15.31', 0, true, false);
INSERT INTO report VALUES (13, 8, NULL, 'tickets_department.xml', 1, 'Tickets by Department', 'What tickets are there in each department?', '2004-01-26 09:18:15.518', 0, '2004-01-26 09:18:15.518', 0, true, false);
INSERT INTO report VALUES (14, 8, NULL, 'ticket_summary_date.xml', 1, 'Ticket counts by Department', 'How man tickets are there in the system on a particular date?', '2004-01-26 09:18:15.532', 0, '2004-01-26 09:18:15.532', 0, true, false);
INSERT INTO report VALUES (15, 8, NULL, 'ticket_summary_range.xml', 1, 'Ticket activity by Department', 'How many tickets exist within a date range?', '2004-01-26 09:18:15.543', 0, '2004-01-26 09:18:15.543', 0, true, false);
INSERT INTO report VALUES (16, 9, NULL, 'users.xml', 1, 'System Users', 'Who are all the users of the system?', '2004-01-26 09:18:15.693', 0, '2004-01-26 09:18:15.693', 0, true, false);
INSERT INTO report VALUES (17, 12, NULL, 'task_date.xml', 1, 'Task list by due date', 'What are the tasks due withing a date range?', '2004-01-26 09:18:15.951', 0, '2004-01-26 09:18:15.951', 0, true, false);
INSERT INTO report VALUES (18, 12, NULL, 'task_nodate.xml', 1, 'Task list', 'What are all the tasks in the system?', '2004-01-26 09:18:15.956', 0, '2004-01-26 09:18:15.956', 0, true, false);
INSERT INTO report VALUES (19, 15, NULL, 'employees.xml', 1, 'Employees', 'Who are the employees in my organization?', '2004-01-26 09:18:16.082', 0, '2004-01-26 09:18:16.082', 0, true, false);
























INSERT INTO database_version VALUES (1, 'postgresql.sql', '2004-01-26', '2004-01-26 17:12:12.628');



INSERT INTO lookup_call_types VALUES (1, 'Incoming Call', true, 10, true);
INSERT INTO lookup_call_types VALUES (2, 'Outgoing Call', false, 20, true);
INSERT INTO lookup_call_types VALUES (3, 'Proactive Call', false, 30, true);
INSERT INTO lookup_call_types VALUES (4, 'Inhouse Meeting', false, 40, true);
INSERT INTO lookup_call_types VALUES (5, 'Outside Appointment', false, 50, true);
INSERT INTO lookup_call_types VALUES (6, 'Proactive Meeting', false, 60, true);
INSERT INTO lookup_call_types VALUES (7, 'Email Servicing', false, 70, true);
INSERT INTO lookup_call_types VALUES (8, 'Email Proactive', false, 80, true);
INSERT INTO lookup_call_types VALUES (9, 'Fax Servicing', false, 90, true);
INSERT INTO lookup_call_types VALUES (10, 'Fax Proactive', false, 100, true);



INSERT INTO lookup_opportunity_types VALUES (1, NULL, 'Annuity', false, 0, true);
INSERT INTO lookup_opportunity_types VALUES (2, NULL, 'Consultation', false, 1, true);
INSERT INTO lookup_opportunity_types VALUES (3, NULL, 'Development', false, 2, true);
INSERT INTO lookup_opportunity_types VALUES (4, NULL, 'Maintenance', false, 3, true);
INSERT INTO lookup_opportunity_types VALUES (5, NULL, 'Product Sales', false, 4, true);
INSERT INTO lookup_opportunity_types VALUES (6, NULL, 'Services', false, 5, true);















INSERT INTO ticket_level VALUES (1, 'Entry level', false, 0, true);
INSERT INTO ticket_level VALUES (2, 'First level', false, 1, true);
INSERT INTO ticket_level VALUES (3, 'Second level', false, 2, true);
INSERT INTO ticket_level VALUES (4, 'Third level', false, 3, true);
INSERT INTO ticket_level VALUES (5, 'Top level', false, 4, true);



INSERT INTO ticket_severity VALUES (1, 'Normal', 'background-color:lightgreen;color:black;', true, 0, true);
INSERT INTO ticket_severity VALUES (2, 'Important', 'background-color:yellow;color:black;', false, 1, true);
INSERT INTO ticket_severity VALUES (3, 'Critical', 'background-color:red;color:black;font-weight:bold;', false, 2, true);



INSERT INTO lookup_ticketsource VALUES (1, 'Phone', false, 1, true);
INSERT INTO lookup_ticketsource VALUES (2, 'Email', false, 2, true);
INSERT INTO lookup_ticketsource VALUES (3, 'Web', false, 3, true);
INSERT INTO lookup_ticketsource VALUES (4, 'Letter', false, 4, true);
INSERT INTO lookup_ticketsource VALUES (5, 'Other', false, 5, true);



INSERT INTO ticket_priority VALUES (1, 'As Scheduled', 'background-color:lightgreen;color:black;', true, 0, true);
INSERT INTO ticket_priority VALUES (2, 'Urgent', 'background-color:yellow;color:black;', false, 1, true);
INSERT INTO ticket_priority VALUES (3, 'Critical', 'background-color:red;color:black;font-weight:bold;', false, 2, true);



INSERT INTO ticket_category VALUES (1, 0, 0, 'Sales', '', false, 1, true);
INSERT INTO ticket_category VALUES (2, 0, 0, 'Billing', '', false, 2, true);
INSERT INTO ticket_category VALUES (3, 0, 0, 'Technical', '', false, 3, true);
INSERT INTO ticket_category VALUES (4, 0, 0, 'Order', '', false, 4, true);
INSERT INTO ticket_category VALUES (5, 0, 0, 'Other', '', false, 5, true);












INSERT INTO module_field_categorylink VALUES (1, 1, 1, 10, 'Accounts', '2004-01-26 09:18:14.466');
INSERT INTO module_field_categorylink VALUES (2, 2, 2, 10, 'Contacts', '2004-01-26 09:18:14.849');
INSERT INTO module_field_categorylink VALUES (3, 8, 11072003, 10, 'Tickets', '2004-01-26 09:18:15.459');





















INSERT INTO lookup_project_activity VALUES (1, 'Project Initialization', false, 1, true, 0, 0);
INSERT INTO lookup_project_activity VALUES (2, 'Analysis/Software Requirements', false, 2, true, 0, 0);
INSERT INTO lookup_project_activity VALUES (3, 'Functional Specifications', false, 3, true, 0, 0);
INSERT INTO lookup_project_activity VALUES (4, 'Prototype', false, 4, true, 0, 0);
INSERT INTO lookup_project_activity VALUES (5, 'System Development', false, 5, true, 0, 0);
INSERT INTO lookup_project_activity VALUES (6, 'Testing', false, 6, true, 0, 0);
INSERT INTO lookup_project_activity VALUES (7, 'Training', false, 7, true, 0, 0);
INSERT INTO lookup_project_activity VALUES (8, 'Documentation', false, 8, true, 0, 0);
INSERT INTO lookup_project_activity VALUES (9, 'Deployment', false, 9, true, 0, 0);
INSERT INTO lookup_project_activity VALUES (10, 'Post Implementation Review', false, 10, true, 0, 0);



INSERT INTO lookup_project_priority VALUES (1, 'Low', false, 1, true, 0, NULL, 10);
INSERT INTO lookup_project_priority VALUES (2, 'Normal', true, 2, true, 0, NULL, 20);
INSERT INTO lookup_project_priority VALUES (3, 'High', false, 3, true, 0, NULL, 30);



INSERT INTO lookup_project_issues VALUES (1, 'Status Update', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (2, 'Bug Report', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (3, 'Network', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (4, 'Hardware', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (5, 'Permissions', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (6, 'User', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (7, 'Documentation', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (8, 'Feature', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (9, 'Procedure', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (10, 'Training', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (11, '3rd-Party Software', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (12, 'Database', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (13, 'Information', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (14, 'Testing', false, 0, true, 0);
INSERT INTO lookup_project_issues VALUES (15, 'Security', false, 0, true, 0);



INSERT INTO lookup_project_status VALUES (1, 'Not Started', false, 1, true, 0, 'box.gif', 1);
INSERT INTO lookup_project_status VALUES (2, 'In Progress', false, 2, true, 0, 'box.gif', 2);
INSERT INTO lookup_project_status VALUES (3, 'On Hold', false, 5, true, 0, 'box-hold.gif', 5);
INSERT INTO lookup_project_status VALUES (4, 'Waiting on Reqs', false, 6, true, 0, 'box-hold.gif', 5);
INSERT INTO lookup_project_status VALUES (5, 'Complete', false, 3, true, 0, 'box-checked.gif', 3);
INSERT INTO lookup_project_status VALUES (6, 'Closed', false, 4, true, 0, 'box-checked.gif', 4);



INSERT INTO lookup_project_loe VALUES (1, 'Minute(s)', 60, false, 1, true, 0);
INSERT INTO lookup_project_loe VALUES (2, 'Hour(s)', 3600, true, 1, true, 0);
INSERT INTO lookup_project_loe VALUES (3, 'Day(s)', 86400, false, 1, true, 0);
INSERT INTO lookup_project_loe VALUES (4, 'Week(s)', 604800, false, 1, true, 0);
INSERT INTO lookup_project_loe VALUES (5, 'Month(s)', 18144000, false, 1, true, 0);

























































INSERT INTO lookup_survey_types VALUES (1, 'Open-Ended', false, 0, true);
INSERT INTO lookup_survey_types VALUES (2, 'Quantitative (no comments)', false, 0, true);
INSERT INTO lookup_survey_types VALUES (3, 'Quantitative (with comments)', false, 0, true);
INSERT INTO lookup_survey_types VALUES (4, 'Item List', false, 0, true);




































INSERT INTO field_types VALUES (1, 0, 'string', '=', 'is', true);
INSERT INTO field_types VALUES (2, 0, 'string', '!=', 'is not', true);
INSERT INTO field_types VALUES (3, 0, 'string', '= | or field_name is null', 'is empty', false);
INSERT INTO field_types VALUES (4, 0, 'string', '!= | and field_name is not null', 'is not empty', false);
INSERT INTO field_types VALUES (5, 0, 'string', 'like %search_value%', 'contains', false);
INSERT INTO field_types VALUES (6, 0, 'string', 'not like %search_value%', 'does not contain', false);
INSERT INTO field_types VALUES (7, 1, 'date', '<', 'before', true);
INSERT INTO field_types VALUES (8, 1, 'date', '>', 'after', true);
INSERT INTO field_types VALUES (9, 1, 'date', 'between', 'between', false);
INSERT INTO field_types VALUES (10, 1, 'date', '<=', 'on or before', true);
INSERT INTO field_types VALUES (11, 1, 'date', '>=', 'on or after', true);
INSERT INTO field_types VALUES (12, 2, 'number', '>', 'greater than', true);
INSERT INTO field_types VALUES (13, 2, 'number', '<', 'less than', true);
INSERT INTO field_types VALUES (14, 2, 'number', '=', 'equals', true);
INSERT INTO field_types VALUES (15, 2, 'number', '>=', 'greater than or equal to', true);
INSERT INTO field_types VALUES (16, 2, 'number', '<=', 'less than or equal to', true);
INSERT INTO field_types VALUES (17, 2, 'number', 'is not null', 'exist', true);
INSERT INTO field_types VALUES (18, 2, 'number', 'is null', 'does not exist', true);



INSERT INTO search_fields VALUES (1, 'company', 'Company Name', true, 0, NULL, NULL, true);
INSERT INTO search_fields VALUES (2, 'namefirst', 'Contact First Name', true, 0, NULL, NULL, true);
INSERT INTO search_fields VALUES (3, 'namelast', 'Contact Last Name', true, 0, NULL, NULL, true);
INSERT INTO search_fields VALUES (4, 'entered', 'Entered Date', true, 1, NULL, NULL, true);
INSERT INTO search_fields VALUES (5, 'zip', 'Zip Code', true, 0, NULL, NULL, true);
INSERT INTO search_fields VALUES (6, 'areacode', 'Area Code', true, 0, NULL, NULL, true);
INSERT INTO search_fields VALUES (7, 'city', 'City', true, 0, NULL, NULL, true);
INSERT INTO search_fields VALUES (8, 'typeId', 'Contact Type', true, 0, NULL, NULL, true);
INSERT INTO search_fields VALUES (9, 'contactId', 'Contact ID', false, 0, NULL, NULL, true);
INSERT INTO search_fields VALUES (10, 'title', 'Contact Title', false, 0, NULL, NULL, true);
INSERT INTO search_fields VALUES (11, 'accountTypeId', 'Account Type', true, 0, NULL, NULL, true);












INSERT INTO help_module VALUES (1, 12, 'This is the "Home Page". The top set of tabs shows the individual modules that you can access. The second row are the functions available to you in this module.
If you are looking for a particular module or function you have seen during training or on someone else''s machine, and it''s NOT visible, then it probably means that you do not have permission for that module or function.

Permissions within the system are assigned to Roles and then Users are assigned to Roles by the System Administrator.
', 'The Home Page has several main features.

Welcome: This is the welcome page. Here you will see a calendar of all of your upcoming alerts in the system, as well as for users that report to you.

Mailbox: The message system is designed to support INTERNAL business messaging needs and to prepare OUTGOING emails to addresses who are already in the system. Messaging is NOT a normal email replacement. It will not send email to addresses assigned "on-the-fly" and it will not receive OUTSIDE email.

Tasks: Tasks allows you to create and assign tasks. Tasks created can be assigned to the creator of the task or an employee working in the system. This page lists the tasks present along with their priorities, due dates and age.

Action Lists: Action Lists allow you to create a list of contacts that are related in some way. For each of the contacts in a list, you can add calls, opportunities, tickets, tasks or send a message, which would correspondingly show up in their respective tabs. For example, adding a ticket to the contact would be reflected in the Ticket module.

Re-assignments: You can reassign data from one employee to another employee as long as each employee reports to you. The data can be of different types related to accounts, contacts, opportunities, activities, tickets etc, which the newly reassigned employee could view.

Settings: This feature allows you to modify the information about yourself and your location, and also change your password to the system.');
INSERT INTO help_module VALUES (2, 2, 'The purpose of this module is for the users to view contacts and add new contacts. You can also search for contacts as well as export contact data.
', 'The Contacts module has three main features

Add: A new contact can be added into the system. The contact can be a general contact, or one that is associated with an account. All the details about the contact like the email address, phone numbers, address and some additional details can be added.

Search: Use this page to search for contacts in the system based on different filters.

Export: Contact data can be exported and displayed in different formats, and can be filtered in different ways. The Export page also lets you view the data, download it, and show the number of times the exported data has been downloaded.');
INSERT INTO help_module VALUES (3, 4, 'Pipeline helps in creating opportunities or leads in the company. Each opportunity helps to follow-up on a lead, which might eventually turn into a client. Here you can create an opportunity, search for an existing opportunity in the system, or export the data in different formats. The dashboard displays a progress chart in different views for all the employees working under the hierarchy of the owner of the opportunity.
', 'This Pipeline System has four main features:

Dashboard: Gives you a quick, visual overview of opportunities.

Add: This page lets you add an opportunity into the system. Here a new opportunity can be added by giving the description of the opportunity. You can add a component that is associated with the opportunity. Each component can be assigned to an employee and the type of the component can be selected. For each component the probability of closing the component, the date estimated, the best guess for closing the deal and the duration for that component can be entered.

Search: You can search for an opportunity already existing in the system based on different filters.

Export: The data can be filtered, exported and displayed in different formats.');
INSERT INTO help_module VALUES (4, 1, 'You are looking at the Accounts module homepage, which has a dashboard view of all your accounts. This view is based on a date range selected from the calendar; by default it shows the schedule for the next seven days. You can optionally view the calendar of those below you in your hierarchy. The scheduled actions of each user can also be viewed. In the Accounts Module, new accounts can be added, existing accounts can be searched based on different filters, revenue for each account can be created/maintained and finally, data can be exported to different formats.
', 'The Accounts module has five main features.

Dashboard: Displays a dashboard view account contract expirations.

Add: Allows you to add a new account

Search: This page provides a search feature for the accounts present in the system

Revenue: Graphically visualizes revenue if the historical data is present in the system. All the accounts with revenue are shown along with a list of employees working under you under the progress chart. You can add accounts, search for the existing ones in the system based on different filters and export the data to different formats

Export: Data can be exported and displayed in different formats. The exported data can be filtered in different ways. You can view the data, download it, and see the number of times an exported report has been downloaded ');
INSERT INTO help_module VALUES (5, 6, 'Communications is a "Campaign Manager" Module where you can manage complex email. fax, or mail communications with your customers. Communications allows you to create and choose Groups, Messages, and Schedules to define communications scenarios from the simple to the very complex. Groups can range from a single contact chosen from a pick list to the result of a complex query of the Account/Contact database. Messages can be anything from a single-line email to a rich, multimedia product catalog to an interactive survey.', 'The Communication module has six main features.

Dashboard: Track and analyze campaigns that have been activated and executed.
Messages can be sent out by any combination of email, fax, or mail merge. The Dashboard shows an overview of sent messages and allows you to drill down and view recipients and any survey results.

Add: This page lets you add a new campaign.

Campaign List: This page lets you add a new campaign into the system.

Groups: Each campaign needs at least one group to send a message to. Use criteria to filter the contacts you need to reach and use them over and over again. As new contacts meet the criteria, they will be included in future campaigns. This page lists the group names. It shows the groups created by you or everybody i.e. all the groups. 

Messages: Compose a message to reach your audience. Each campaign requires a message that will be sent to selected groups. Write the message once, then use it in any number of future campaigns. Modified messages will only affect future campaigns. Displays the list of messages, with the description and other details.

Attachments: Customize and configure your Campaigns with attachments. Attachments can include interactive items, like surveys, or provide additional materials like files. ');
INSERT INTO help_module VALUES (6, 8, 'You are looking at the Help Desk module home page. The dashboard shows the most recent tickets that have been assigned to you, as well as tickets that are in your department, and tickets that have been created by you. You can add new tickets, search for existing tickets based on different filters and export ticket data.
', 'The Help Desk module has four main features.

View: Lists all the tickets assigned to you and the tickets assigned in your department. Details such as the ticket number, priority, age, i.e. how old the ticket is, the company and the tickets assignment are displayed.

Add: You can add a new ticket here.

Search: Form used for searching the tickets that already exist in the system based on different filters and parameters.

Export: This page shows exported data. The data can be exported to different formats. The exported data can be viewed with its subject, the size of the exported data file, when it was created and by whom. It also shows the number of times that particular exported file was downloaded. The exported data, created by you or all the employees can be viewed in two different views. A new data file can also be exported. ');
INSERT INTO help_module VALUES (7, 15, 'You are looking at the Employee module home page. This page displays the details of all the employees present in the system.', 'The main feature of the employee module is the view.

View: This page displays the details of each employee, which can be viewed, modified or deleted. Each employee record contains details such as the name of the employee, their department, title and phone number. This also lets you add a new employee into the system.');
INSERT INTO help_module VALUES (8, 14, 'You are looking at the Reports module home page. This page displays a list of generated reports that are ready to be downloaded. It also displays a list of reports that are scheduled to be processed by server. You can add new reports too.
', 'The Reports module has two main features.

Queue: Queue shows the list of reports that are scheduled to be processed by the server.

Add: This shows the different modules present and displays the list of corresponding reports present in each module, allowing you to add a report to the schedule queue. ');
INSERT INTO help_module VALUES (9, 9, 'The admin module lets the user review the system usage, configure modules, and configure the global/system parameters.', 'This Admin System has five main features.

Users: This section allows the administrator to view and add users and manage user hierarchies. The users are typically employees in your company who interact with your clients or customers, but can be outsides that you have granted permissions on the system.

Roles: This page lists the different roles you have defined in the system, their role description and the number of people present in the system who carry out that role. New roles can be added into the system at any time.

Modules: This page lets you configure various parameters in the modules that meet the needs of your organization, including configuration of lookup lists and custom fields. There are four types of modules. Each module has different number of configure options. The changes in the module affect all the users.

System: You can configure the system for the session timeout and set the time limit for the time out.

Usage: Current System Usage and Billing Usage Information are displayed.
');
INSERT INTO help_module VALUES (10, 3, 'Auto Guide Brief', 'Auto Guide Detail');
INSERT INTO help_module VALUES (11, 10, 'Help Brief', 'Help Detail');
INSERT INTO help_module VALUES (12, 5, 'Demo Brief', 'Demo Detail');
INSERT INTO help_module VALUES (13, 11, 'System Brief', 'System Detail');



INSERT INTO help_contents VALUES (1, 12, 1, 'MyCFS.do', 'Home', NULL, 'Overview', 'You are looking at Home Page view, which has a dashboard view of all your assigned tasks, tickets, assignments, calls you have to make, and accounts that need attention. This view is based on a time selected from the calendar; by default it shows the schedule for the next seven days. You can optionally view the calendar of those below you in your hierarchy.', NULL, NULL, NULL, 0, '2004-01-26 09:18:23.481', 0, '2004-01-26 09:18:23.481', true);
INSERT INTO help_contents VALUES (2, 12, 1, 'MyCFSInbox.do', 'Inbox', NULL, 'Mailbox', 'The messaging feature is designed to support INTERNAL business messaging needs and to prepare OUTGOING emails to addresses who are already in the system. Messaging is NOT a normal email replacement. It will not send email to address assigned "on-the-fly" and it will not receive OUTSIDE email.', NULL, NULL, NULL, 0, '2004-01-26 09:18:23.658', 0, '2004-01-26 09:18:23.658', true);
INSERT INTO help_contents VALUES (3, 12, 1, 'MyCFSInbox.do', 'CFSNoteDetails', NULL, 'Message Details', 'This page shows the message details, shows who sent the mail, when it was received and also the text in the mail box.', NULL, NULL, NULL, 0, '2004-01-26 09:18:23.754', 0, '2004-01-26 09:18:23.754', true);
INSERT INTO help_contents VALUES (4, 12, 1, 'MyCFSInbox.do', 'NewMessage', NULL, 'New Message ', 'Sending mail to other users of the system', NULL, NULL, NULL, 0, '2004-01-26 09:18:23.778', 0, '2004-01-26 09:18:23.778', true);
INSERT INTO help_contents VALUES (5, 12, 1, 'MyCFSInbox.do', 'ReplyToMessage', NULL, 'Reply Message', 'This pages lets you reply to email. You can also select the list of recipients for your email by clicking the Add Recipients link.', NULL, NULL, NULL, 0, '2004-01-26 09:18:23.802', 0, '2004-01-26 09:18:23.802', true);
INSERT INTO help_contents VALUES (6, 12, 1, 'MyCFSInbox.do', 'SendMessage', NULL, NULL, 'This page shows the list of recipients for whom your email has been sent to', NULL, NULL, NULL, 0, '2004-01-26 09:18:23.859', 0, '2004-01-26 09:18:23.859', true);
INSERT INTO help_contents VALUES (7, 12, 1, 'MyCFSInbox.do', 'ForwardMessage', NULL, 'Forward message', 'Each message can be forwarded to any number of recipients', NULL, NULL, NULL, 0, '2004-01-26 09:18:23.875', 0, '2004-01-26 09:18:23.875', true);
INSERT INTO help_contents VALUES (8, 12, 1, 'MyTasks.do', NULL, NULL, 'Tasks', 'Tasks created can be assigned to the owner/creator of the task or an employee working in the system. This page lists the tasks present along with their priorities, their due dates and age.', NULL, NULL, NULL, 0, '2004-01-26 09:18:23.922', 0, '2004-01-26 09:18:23.922', true);
INSERT INTO help_contents VALUES (9, 12, 1, 'MyTasks.do', 'New', NULL, 'Advanced Task', 'Allows an advanced task to be created', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.006', 0, '2004-01-26 09:18:24.006', true);
INSERT INTO help_contents VALUES (10, 12, 1, 'MyTasksForward.do', 'ForwardMessage', NULL, 'Forwarding a Task ', 'A task can be forwarded to any of the recipients. Recipients can either be users of the system or any of the contacts stored in the system. Checking the options fields check box indicates that if the recipient is a user of the system, then a copy of the task is also send to the recipient''s mailbox.', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.078', 0, '2004-01-26 09:18:24.078', true);
INSERT INTO help_contents VALUES (11, 12, 1, 'MyTasks.do', 'Modify', NULL, 'Modify task', 'Allows you to modify any infomation about a task', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.131', 0, '2004-01-26 09:18:24.131', true);
INSERT INTO help_contents VALUES (12, 12, 1, 'MyActionLists.do', 'List', NULL, 'Action Lists', 'Action Lists allow you to create new Action Lists and assign contacts to the Action Lists created. For each of the contacts in a list, you can add a call, opportunity, ticket, task or send a message, which would correspondingly show up in their respective tabs. For example, adding a ticket to the contact would be reflected in the Ticket module.', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.197', 0, '2004-01-26 09:18:24.197', true);
INSERT INTO help_contents VALUES (13, 12, 1, 'MyActionContacts.do', 'List', NULL, 'Action Contacts', 'This page will list out all the contacts present for the particular contact list. This also shows the status of the call, opportunity, ticket, task, or the message associated with the contact. This also shows when the contact information was last updated.', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.258', 0, '2004-01-26 09:18:24.258', true);
INSERT INTO help_contents VALUES (14, 12, 1, 'MyActionLists.do', 'Add', NULL, 'Add Action List', 'Allows you to add an action list. Basically, you describe the group of contacts you will add, then visually design a query to generate the group of contacts.', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.323', 0, '2004-01-26 09:18:24.323', true);
INSERT INTO help_contents VALUES (15, 12, 1, 'MyActionLists.do', 'Modify', NULL, 'Modify Action', 'The Action Lists details, like the description and status of the Action Lists, can be modified.', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.437', 0, '2004-01-26 09:18:24.437', true);
INSERT INTO help_contents VALUES (16, 12, 1, 'Reassignments.do', 'Reassign', NULL, 'Re-assignments', 'A user can reassign data from one employee to another employee working under him. The data can be of different types related to accounts, contacts opportunities, activities, tickets etc, which the newly reassigned employee could view in his schedule. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.497', 0, '2004-01-26 09:18:24.497', true);
INSERT INTO help_contents VALUES (17, 12, 1, 'MyCFS.do', 'MyProfile', NULL, 'My Settings', 'This is the personal settings page, where you can modify the information about yourself, your location and also change your password to the system.', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.559', 0, '2004-01-26 09:18:24.559', true);
INSERT INTO help_contents VALUES (18, 12, 1, 'MyCFSProfile.do', 'MyCFSProfile', NULL, 'Personal Information ', 'This page lets you update/add your personal information.', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.608', 0, '2004-01-26 09:18:24.608', true);
INSERT INTO help_contents VALUES (19, 12, 1, 'MyCFSSettings.do', 'MyCFSSettings', NULL, 'Location Settings', 'You can change your location settings', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.636', 0, '2004-01-26 09:18:24.636', true);
INSERT INTO help_contents VALUES (20, 12, 1, 'MyCFSPassword.do', 'MyCFSPassword', NULL, 'Update password', 'Your password to the system can be changed', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.666', 0, '2004-01-26 09:18:24.666', true);
INSERT INTO help_contents VALUES (21, 12, 1, 'MyTasks.do', 'ListTasks', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:24.691', 0, '2004-01-26 09:18:24.691', true);
INSERT INTO help_contents VALUES (22, 12, 1, 'MyTasks.do', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:24.802', 0, '2004-01-26 09:18:24.802', true);
INSERT INTO help_contents VALUES (23, 12, 1, 'MyCFS.do', 'Home ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:24.821', 0, '2004-01-26 09:18:24.821', true);
INSERT INTO help_contents VALUES (24, 12, 1, 'MyTasksForward.do', 'SendMessage', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:24.828', 0, '2004-01-26 09:18:24.828', true);
INSERT INTO help_contents VALUES (25, 12, 1, 'MyCFSProfile.do', 'UpdateProfile', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:24.837', 0, '2004-01-26 09:18:24.837', true);
INSERT INTO help_contents VALUES (26, 12, 1, 'MyCFSInbox.do', 'CFSNoteTrash', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:24.852', 0, '2004-01-26 09:18:24.852', true);
INSERT INTO help_contents VALUES (27, 12, 1, 'MyActionContacts.do', 'Update', NULL, NULL, 'No introduction available', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.858', 0, '2004-01-26 09:18:24.858', true);
INSERT INTO help_contents VALUES (28, 12, 1, 'MyActionContacts.do', 'Prepare', NULL, NULL, 'You can add the new Action List and also select the contacts to be present in the Action List.', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.875', 0, '2004-01-26 09:18:24.875', true);
INSERT INTO help_contents VALUES (29, 12, 1, 'MyTasks.do', 'ListTasks ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:24.905', 0, '2004-01-26 09:18:24.905', true);
INSERT INTO help_contents VALUES (30, 12, 1, 'MyCFSInbox.do', 'Inbox ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:24.928', 0, '2004-01-26 09:18:24.928', true);
INSERT INTO help_contents VALUES (31, 12, 1, 'MyActionContacts.do', 'Modify', NULL, 'Modify Action List', 'Allows you to manually add or remove contacts to or from an existing Action List.', NULL, NULL, NULL, 0, '2004-01-26 09:18:24.936', 0, '2004-01-26 09:18:24.936', true);
INSERT INTO help_contents VALUES (32, 12, 1, 'Reassignments.do', 'DoReassign', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:24.983', 0, '2004-01-26 09:18:24.983', true);
INSERT INTO help_contents VALUES (33, 12, 1, 'TaskForm.do', 'Prepare', NULL, NULL, 'Addition of a new Advanced task that can be assigned to the owner or any other employee working  under him.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.007', 0, '2004-01-26 09:18:25.007', true);
INSERT INTO help_contents VALUES (34, 2, 2, 'ExternalContacts.do', 'Prepare', NULL, 'Add a Contact', 'A new contact can be added to the system. The contact can be a general contact, or one that is associated with an account. All the details about the contact such as the email address, phone numbers, address and some additional details can be added.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.043', 0, '2004-01-26 09:18:25.043', true);
INSERT INTO help_contents VALUES (35, 2, 2, 'ExternalContacts.do', 'SearchContactsForm', NULL, 'Search Contacts ', 'Use this page to search for contacts in the system based on different filters', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.121', 0, '2004-01-26 09:18:25.121', true);
INSERT INTO help_contents VALUES (36, 2, 2, 'ExternalContacts.do', 'Reports', NULL, 'Export Data ', 'Contact data can be exported and displayed in different formats, and can be filtered in different ways. The Export page also lets you view the data, download it, and shows the number of times the exported data has been downloaded.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.145', 0, '2004-01-26 09:18:25.145', true);
INSERT INTO help_contents VALUES (37, 2, 2, 'ExternalContacts.do', 'GenerateForm', NULL, 'Exporting data', 'Use this page to generate a Contacts export report.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.205', 0, '2004-01-26 09:18:25.205', true);
INSERT INTO help_contents VALUES (38, 2, 2, 'ExternalContacts.do', 'ModifyContact', NULL, 'Modify Contact ', 'The details about the contact can be modified here.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.294', 0, '2004-01-26 09:18:25.294', true);
INSERT INTO help_contents VALUES (39, 2, 2, 'ExternalContacts.do ', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:25.318', 0, '2004-01-26 09:18:25.318', true);
INSERT INTO help_contents VALUES (40, 2, 2, 'ExternalContacts.do', 'Save', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:25.325', 0, '2004-01-26 09:18:25.325', true);
INSERT INTO help_contents VALUES (41, 2, 2, 'ExternalContactsCalls.do', 'View', NULL, 'Call Details', 'The calls related to the contact are listed here along with the other details such as the length of the call and the date the call was made. You can also add a call.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.349', 0, '2004-01-26 09:18:25.349', true);
INSERT INTO help_contents VALUES (42, 2, 2, 'ExternalContactsCalls.do', 'Add', NULL, 'Adding a Call', 'A new call can be added which is associated with the contact. The type of call can be selected using the drop down list present.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.396', 0, '2004-01-26 09:18:25.396', true);
INSERT INTO help_contents VALUES (43, 2, 2, 'ExternalContactsCallsForward.do', 'ForwardMessage', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:25.42', 0, '2004-01-26 09:18:25.42', true);
INSERT INTO help_contents VALUES (44, 2, 2, 'ExternalContactsCallsForward.do', 'SendMessage', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:25.427', 0, '2004-01-26 09:18:25.427', true);
INSERT INTO help_contents VALUES (45, 2, 2, 'ExternalContactsOpps.do', 'ViewOpps', NULL, 'Opportunity Details', 'All the opportunities associated with the contact are shown here, with its best possible total and the when the opportunity was last modified. You can filter the different types of opportunities using the drop down.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.458', 0, '2004-01-26 09:18:25.458', true);
INSERT INTO help_contents VALUES (46, 2, 2, 'ExternalContactsCalls.do', 'Details', NULL, 'Call Details', 'Calls associated with the contacts are displayed. The call details are shown with the length of the call, associated notes, alert description, alert date etc.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.522', 0, '2004-01-26 09:18:25.522', true);
INSERT INTO help_contents VALUES (47, 2, 2, 'ExternalContactsOppComponents.do', 'Prepare', NULL, 'Add a component', 'A component can be added to an opportunity and associated to any employee present in the system. The component type can also be selected.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.552', 0, '2004-01-26 09:18:25.552', true);
INSERT INTO help_contents VALUES (48, 2, 2, 'ExternalContactsOpps.do', 'DetailsOpp', NULL, 'Opportunity Details ', 'You can view all the details about the components here, such as the status, the guess amount and the current stage. A new component can also be added to an existing opportunity. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.60', 0, '2004-01-26 09:18:25.60', true);
INSERT INTO help_contents VALUES (49, 2, 2, 'ExternalContactsOppComponents.do', 'DetailsComponent', NULL, 'Component Details ', 'This page shows the details about the opportunity such as the probability of closing the opportunity, the current stage of the opportunity, etc.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.654', 0, '2004-01-26 09:18:25.654', true);
INSERT INTO help_contents VALUES (50, 2, 2, 'ExternalContactsCalls.do', 'Modify', NULL, 'Modifying call details ', 'You can modify all the details of the calls.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.678', 0, '2004-01-26 09:18:25.678', true);
INSERT INTO help_contents VALUES (51, 2, 2, 'ExternalContacts.do', 'InsertFields', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:25.72', 0, '2004-01-26 09:18:25.72', true);
INSERT INTO help_contents VALUES (52, 2, 2, 'ExternalContacts.do', 'UpdateFields', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:25.738', 0, '2004-01-26 09:18:25.738', true);
INSERT INTO help_contents VALUES (53, 2, 2, 'ExternalContacts.do', 'ListContacts', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:25.745', 0, '2004-01-26 09:18:25.745', true);
INSERT INTO help_contents VALUES (54, 2, 2, 'ExternalContacts.do', 'Clone', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:25.768', 0, '2004-01-26 09:18:25.768', true);
INSERT INTO help_contents VALUES (55, 2, 2, 'ExternalContacts.do', 'AddFolderRecord', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:25.775', 0, '2004-01-26 09:18:25.775', true);
INSERT INTO help_contents VALUES (56, 2, 2, 'ExternalContactsOpps.do', 'Save', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:25.80', 0, '2004-01-26 09:18:25.80', true);
INSERT INTO help_contents VALUES (57, 2, 2, 'ExternalContacts.do', 'SearchContacts ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:25.805', 0, '2004-01-26 09:18:25.805', true);
INSERT INTO help_contents VALUES (58, 2, 2, 'ExternalContacts.do', 'MessageDetails', NULL, NULL, 'The selected message is displayed with the message text and attachments, if any.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.828', 0, '2004-01-26 09:18:25.828', true);
INSERT INTO help_contents VALUES (59, 2, 2, 'ExternalContactsOppComponents.do', 'SaveComponent', NULL, NULL, 'This page shows the details of an opportunity, such as the probability of closing the opportunity, the current stage of the opportunity, etc.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.857', 0, '2004-01-26 09:18:25.857', true);
INSERT INTO help_contents VALUES (60, 2, 2, 'ExternalContacts.do', 'SearchContacts', NULL, 'List of Contacts', 'This page shows the list of contacts in the system. The name of the contact along with the company name and phone numbers are shown. If the name of the contact is an account, it''s shown right next to it. You can also add a contact.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.882', 0, '2004-01-26 09:18:25.882', true);
INSERT INTO help_contents VALUES (61, 2, 2, 'ExternalContacts.do', 'ExportReport', NULL, 'Overview', 'Data can be filtered, exported and displayed in different formats. This also shows the number of times an exported data has been downloaded. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.93', 0, '2004-01-26 09:18:25.93', true);
INSERT INTO help_contents VALUES (62, 2, 2, 'ExternalContacts.do', 'ContactDetails', NULL, 'Contact Details ', 'The details about the contact are displayed here along with the record information containing the owner, the employee who entered the details and finally the person who last modified these details.', NULL, NULL, NULL, 0, '2004-01-26 09:18:25.978', 0, '2004-01-26 09:18:25.978', true);
INSERT INTO help_contents VALUES (63, 2, 2, 'ExternalContacts.do', 'ViewMessages', NULL, 'Message Details', 'This page lists all the messages associated with the contact, showing the name of the message, its date and its status.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.002', 0, '2004-01-26 09:18:26.002', true);
INSERT INTO help_contents VALUES (64, 2, 2, 'ExternalContactsCallsForward.do', 'ForwardCall', NULL, 'Forwarding a call', 'The details of the calls that are associated with a contact can be forwarded to different employees present in the system.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.116', 0, '2004-01-26 09:18:26.116', true);
INSERT INTO help_contents VALUES (65, 2, 2, 'ExternalContactsCallsForward.do', 'SendCall', NULL, 'List of recipients', 'This page shows the list of recipients to whom the call details that are associated with a contact have been forwarded.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.212', 0, '2004-01-26 09:18:26.212', true);
INSERT INTO help_contents VALUES (66, 2, 2, 'ExternalContactsOppComponents.do', 'ModifyComponent', NULL, 'Modifying the component details ', 'The details of the component associated with an opportunity can be modified.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.248', 0, '2004-01-26 09:18:26.248', true);
INSERT INTO help_contents VALUES (67, 2, 2, 'ExternalContactsOpps.do', 'ModifyOpp', NULL, 'Modify the opportunity', 'The description of the opportunity can be renamed / updated.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.307', 0, '2004-01-26 09:18:26.307', true);
INSERT INTO help_contents VALUES (68, 2, 2, 'ExternalContacts.do', 'Fields', NULL, 'List of Folder Records', 'Each contact can have several folders, and each folder further can have multiple records. You can add a record to a folder. Each record present in the folder displays the record name, when it was entered, who modified this record last and when.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.337', 0, '2004-01-26 09:18:26.337', true);
INSERT INTO help_contents VALUES (69, 2, 2, 'ExternalContacts.do', 'ModifyFields', NULL, 'Modify Folder Record ', 'Here you can modify the details of the folder record.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.409', 0, '2004-01-26 09:18:26.409', true);
INSERT INTO help_contents VALUES (70, 2, 2, 'ExternalContactsOpps.do', 'UpdateOpp', NULL, 'Opportunity Details', 'All the opportunities associated with the contact are shown here, with its best possible total and the when the opportunity was last modified. You can filter the different types of opportunities that can be selected using the drop down and display them.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.433', 0, '2004-01-26 09:18:26.433', true);
INSERT INTO help_contents VALUES (71, 2, 2, 'ExternalContacts.do', NULL, NULL, 'Overview', 'If a contact already exists in the system, you can search for that contact by name, company, title, contact type or source, by typing the search term in the appropriate field, and clicking the Search button.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.504', 0, '2004-01-26 09:18:26.504', true);
INSERT INTO help_contents VALUES (72, 2, 2, 'ContactsList.do', 'ContactList', NULL, NULL, 'Enables you to select contacts from a list and then add them to the Action List. It shows the name of the contact along with his email and type of contact. 

', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.582', 0, '2004-01-26 09:18:26.582', true);
INSERT INTO help_contents VALUES (73, 2, 2, 'Contacts.do', 'Prepare', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:26.624', 0, '2004-01-26 09:18:26.624', true);
INSERT INTO help_contents VALUES (74, 2, 2, 'Contacts.do', 'Details', NULL, 'Contact detail page', 'The contact details associated with the account are displayed here. The details such as the account number, email address, phone number and the addresses are shown.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.643', 0, '2004-01-26 09:18:26.643', true);
INSERT INTO help_contents VALUES (75, 2, 2, 'Contacts.do', 'Modify', NULL, 'Modify contact', 'The details of a contact can be modified or updated here.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.672', 0, '2004-01-26 09:18:26.672', true);
INSERT INTO help_contents VALUES (76, 2, 2, 'Contacts.do', 'ViewMessages', NULL, 'List of Messages', 'The list of messages related to the contacts.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.721', 0, '2004-01-26 09:18:26.721', true);
INSERT INTO help_contents VALUES (77, 2, 2, 'ContactForm.do', 'Prepare', NULL, NULL, 'Adding/Modifying a new contact', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.75', 0, '2004-01-26 09:18:26.75', true);
INSERT INTO help_contents VALUES (78, 4, 3, 'Leads.do', 'Dashboard', NULL, 'Overview', 'The progress chart is displayed in different views for all the employees working under the owner or creator of the opportunity. The opportunities are shown, with their names and the probable gross revenue associated with that opportunity. Finally the list of employees reporting to a particular employee/supervisor is also shown below the progress chart', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.805', 0, '2004-01-26 09:18:26.805', true);
INSERT INTO help_contents VALUES (79, 4, 3, 'Leads.do', 'Prepare', NULL, 'Add a Opportunity', 'This page lets you add a opportunity into the system.

Here a new opportunity can be added by giving a description of the opportunity, then adding a component that is associated with the opportunity. An opportunity can have one or more components. Each component can be assigned to a different employee and the type of the component can be selected. For each component the probability of closing the component, the date estimated, the best guess for closing the deal and the duration for that component are required. 
', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.852', 0, '2004-01-26 09:18:26.852', true);
INSERT INTO help_contents VALUES (80, 4, 3, 'Leads.do', 'SearchForm', NULL, 'Search Opportunities ', 'You can search for an existing opportunity based on different filters.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.93', 0, '2004-01-26 09:18:26.93', true);
INSERT INTO help_contents VALUES (81, 4, 3, 'LeadsReports.do', 'Default', NULL, 'Export Data', 'Pipeline data can be exported, filtered, and displayed in different formats. You can also view the data online in html format, and see number of times the exported data has been downloaded.', NULL, NULL, NULL, 0, '2004-01-26 09:18:26.965', 0, '2004-01-26 09:18:26.965', true);
INSERT INTO help_contents VALUES (82, 4, 3, 'Leads.do', 'SearchOpp', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:27.026', 0, '2004-01-26 09:18:27.026', true);
INSERT INTO help_contents VALUES (83, 4, 3, 'Leads.do', 'Reports', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:27.033', 0, '2004-01-26 09:18:27.033', true);
INSERT INTO help_contents VALUES (84, 4, 3, 'LeadsComponents.do', 'ModifyComponent', NULL, 'Modify Component', 'You can modify the component details associated with an opportunity in a pipeline.', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.063', 0, '2004-01-26 09:18:27.063', true);
INSERT INTO help_contents VALUES (85, 4, 3, 'LeadsCalls.do', 'Add', NULL, 'Adding a call', 'You can add a new call here associated with the opportunity. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.14', 0, '2004-01-26 09:18:27.14', true);
INSERT INTO help_contents VALUES (86, 4, 3, 'Leads.do', 'ModifyOpp', NULL, 'Modify the opportunity:', 'The description of the opportunity can be modified / updated.', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.182', 0, '2004-01-26 09:18:27.182', true);
INSERT INTO help_contents VALUES (87, 4, 3, 'LeadsCalls.do', 'Insert', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:27.223', 0, '2004-01-26 09:18:27.223', true);
INSERT INTO help_contents VALUES (88, 4, 3, 'LeadsDocuments.do', 'Modify', NULL, 'Modify Document', 'Modify the Subject or filename of a document.', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.23', 0, '2004-01-26 09:18:27.23', true);
INSERT INTO help_contents VALUES (89, 4, 3, 'LeadsCallsForward.do', 'ForwardMessage', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:27.254', 0, '2004-01-26 09:18:27.254', true);
INSERT INTO help_contents VALUES (90, 4, 3, 'Leads.do', 'Save', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:27.278', 0, '2004-01-26 09:18:27.278', true);
INSERT INTO help_contents VALUES (91, 4, 3, 'Leads.do', 'GenerateForm', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:27.284', 0, '2004-01-26 09:18:27.284', true);
INSERT INTO help_contents VALUES (92, 4, 3, 'LeadsComponents.do', 'DetailsComponent', NULL, 'Component Details', 'The component details for the opportunity are shown here. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.308', 0, '2004-01-26 09:18:27.308', true);
INSERT INTO help_contents VALUES (93, 4, 3, 'LeadsDocuments.do', 'AddVersion', NULL, 'Upload a new version of document', 'You can upload a new version of the document and the new version of the file can be selected and uploaded.', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.337', 0, '2004-01-26 09:18:27.337', true);
INSERT INTO help_contents VALUES (94, 4, 3, 'Leads.do', 'Search', NULL, 'List of components', 'The components resulted from the search are shown here. Different views of the components and its types are displayed. The name of the component with the estimated amount of money associated with the opportunity and the probability of that components being a success is shown. This also displays the time for closing the deal (term) and the organization name or the contact name if they are associated with the opportunity. A new opportunity can also be added.', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.368', 0, '2004-01-26 09:18:27.368', true);
INSERT INTO help_contents VALUES (95, 4, 3, 'Leads.do', 'UpdateOpp', NULL, 'Opportunity Details', 'You can view all the details about the components here and also add a new component to a particular opportunity. Calls and the documents can be associated with the opportunity', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.433', 0, '2004-01-26 09:18:27.433', true);
INSERT INTO help_contents VALUES (96, 4, 3, 'LeadsCallsForward.do', 'ForwardCall', NULL, 'Forwarding a call', 'The details of calls associated with a contact can be forwarded to different users present in the system.', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.553', 0, '2004-01-26 09:18:27.553', true);
INSERT INTO help_contents VALUES (97, 4, 3, 'LeadsDocuments.do', 'View', NULL, 'Document Details', 'In the Documents tab, the documents associated with a particular opportunity can be added to, and viewed.', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.601', 0, '2004-01-26 09:18:27.601', true);
INSERT INTO help_contents VALUES (98, 4, 3, 'LeadsCalls.do', 'Modify', NULL, 'Modify call details', 'You can modify the details of the calls.', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.649', 0, '2004-01-26 09:18:27.649', true);
INSERT INTO help_contents VALUES (99, 4, 3, 'Leads.do', 'DetailsOpp', NULL, 'Opportunity Details', 'You can view all the details about an opportunity components here and also add a new component to a particular opportunity. Calls and documents can also be added to an opportunity or viewed by clicking on the appropriate tab.', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.714', 0, '2004-01-26 09:18:27.714', true);
INSERT INTO help_contents VALUES (100, 4, 3, 'LeadsCalls.do', 'View', NULL, 'Call Details', 'Calls associated with the opportunity are shown. Calls can be added to the opportunity, and details about listed calls can be examined.', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.817', 0, '2004-01-26 09:18:27.817', true);
INSERT INTO help_contents VALUES (101, 4, 3, 'Leads.do', 'ViewOpp', NULL, NULL, 'The opportunities resulted from the search are shown here. Different views of the opportunities and its types are displayed. The name of the component with the estimated amount of money associated with the opportunity and the probability of that opportunity being a success are shown. This also displays the time for closing the deal (term) and the organization name or the contact name if they are associated with the opportunity.', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.853', 0, '2004-01-26 09:18:27.853', true);
INSERT INTO help_contents VALUES (102, 4, 3, 'Leads.do', NULL, NULL, NULL, 'Pipeline helps in creating prospective opportunities or leads in the company. Each opportunity helps to follow up a lead, who might eventually turn into a client. Here you can create an opportunity, search for an existing opportunity in the system, export the data to different formats. The dashboard reflects the progress chart in different views for all the employees working under the owner/creator of the opportunity. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.929', 0, '2004-01-26 09:18:27.929', true);
INSERT INTO help_contents VALUES (103, 4, 3, 'LeadsComponents.do', 'SaveComponent', NULL, 'Component Details', '
The component details for the opportunity are shown here.
', NULL, NULL, NULL, 0, '2004-01-26 09:18:27.979', 0, '2004-01-26 09:18:27.979', true);
INSERT INTO help_contents VALUES (104, 4, 3, 'LeadsComponents.do', 'Prepare', NULL, 'Add a component', 'A component can be added to an opportunity and assigned to any employee in the system. The component type can also be selected.', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.003', 0, '2004-01-26 09:18:28.003', true);
INSERT INTO help_contents VALUES (105, 4, 3, 'LeadsCalls.do', 'Update', NULL, 'Call Details', 'The calls associated with the opportunity are shown. Calls can be added to the opportunity.', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.04', 0, '2004-01-26 09:18:28.04', true);
INSERT INTO help_contents VALUES (106, 4, 3, 'LeadsCalls.do', 'Details', NULL, 'Call Details ', 'Details about the call associated with the opportunity', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.093', 0, '2004-01-26 09:18:28.093', true);
INSERT INTO help_contents VALUES (107, 4, 3, 'LeadsDocuments.do', 'Add', NULL, 'Upload a document', 'New documents related to the opportunity can be uploaded into the system.', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.128', 0, '2004-01-26 09:18:28.128', true);
INSERT INTO help_contents VALUES (108, 4, 3, 'LeadsDocuments.do', 'Details', NULL, 'Document Details ', 'This shows all versions of the updated document. The name of the file with it''s size, version and the number of downloads are shown here.', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.182', 0, '2004-01-26 09:18:28.182', true);
INSERT INTO help_contents VALUES (109, 4, 3, 'LeadsReports.do', 'ExportList', NULL, 'List of exported data', 'The data present can be used to export data and display that in different formats. The exported data can be filtered in different ways. This would also let you view the data download it. This also shows the number of times an exported data has been downloaded', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.206', 0, '2004-01-26 09:18:28.206', true);
INSERT INTO help_contents VALUES (110, 4, 3, 'LeadsReports.do', 'ExportForm', NULL, 'Generating Export data', 'Generate an exported report from pipeline data', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.261', 0, '2004-01-26 09:18:28.261', true);
INSERT INTO help_contents VALUES (111, 1, 4, 'Accounts.do', 'Dashboard', NULL, 'Overview', 'The date range can be modified which is shown in the right hand window by clicking on a specific date on the calendar. Accounts with contract end dates or other required actions appear in the right hand window reminding the user to take action on them. The schedule, actions, alert dates and contract end dates are displayed for user or the employees under him by using the dropdown at the top of the page. Clicking on the alert link will let the user modify the details of the account owner. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.369', 0, '2004-01-26 09:18:28.369', true);
INSERT INTO help_contents VALUES (112, 1, 4, 'Accounts.do', 'Add', NULL, 'Add an Account', 'A new account can be added here', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.434', 0, '2004-01-26 09:18:28.434', true);
INSERT INTO help_contents VALUES (113, 1, 4, 'Accounts.do', 'Modify', NULL, 'Modify Account', 'The details of an account can be modified here', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.524', 0, '2004-01-26 09:18:28.524', true);
INSERT INTO help_contents VALUES (114, 1, 4, 'Contacts.do', 'View', NULL, 'Contact Details', 'A contact can be associated with an account. The lists of the contacts associated with the account are shown along with the title.', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.62', 0, '2004-01-26 09:18:28.62', true);
INSERT INTO help_contents VALUES (115, 1, 4, 'Accounts.do', 'Fields', NULL, 'Folder Record Details', 'You create folders for accounts. Each folder can have one or more records associated with it, depending on the type of the folder. The details about records associated with the folder are shown', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.686', 0, '2004-01-26 09:18:28.686', true);
INSERT INTO help_contents VALUES (116, 1, 4, 'Opportunities.do', 'View', NULL, 'Opportunity Details', 'Opportunities associated with the contact, showing the best guess total and last modified date.', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.751', 0, '2004-01-26 09:18:28.751', true);
INSERT INTO help_contents VALUES (117, 1, 4, 'RevenueManager.do', 'View', NULL, 'Revenue Details', 'The revenue associated with the account is shown here. The details about the revenue like the description, the date and the amount associated are displayed.', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.806', 0, '2004-01-26 09:18:28.806', true);
INSERT INTO help_contents VALUES (118, 1, 4, 'RevenueManager.do', 'View', NULL, 'Revenue Details', 'The revenue associated with the account is shown here. Details about the revenue such as the description, the date, and the amount associated are displayed.', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.889', 0, '2004-01-26 09:18:28.889', true);
INSERT INTO help_contents VALUES (119, 1, 4, 'RevenueManager.do', 'Add', NULL, 'Add Revenue ', 'Adding new revenue to an account', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.944', 0, '2004-01-26 09:18:28.944', true);
INSERT INTO help_contents VALUES (120, 1, 4, 'RevenueManager.do', 'Modify', NULL, 'Modify Revenue ', 'Here revenue details can be modified', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.967', 0, '2004-01-26 09:18:28.967', true);
INSERT INTO help_contents VALUES (121, 1, 4, 'Accounts.do', 'ViewTickets', NULL, 'Ticket Details', 'This page shows the complete list of the tickets related to an account, and lets you add a new ticket', NULL, NULL, NULL, 0, '2004-01-26 09:18:28.995', 0, '2004-01-26 09:18:28.995', true);
INSERT INTO help_contents VALUES (122, 1, 4, 'AccountsDocuments.do', 'View', NULL, 'Document Details', 'Here the documents associated with the account are listed. New documents related to the account can be added.', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.045', 0, '2004-01-26 09:18:29.045', true);
INSERT INTO help_contents VALUES (123, 1, 4, 'Accounts.do', 'SearchForm', NULL, 'Search Accounts ', 'This page provides the search feature for accounts in the system.', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.129', 0, '2004-01-26 09:18:29.129', true);
INSERT INTO help_contents VALUES (124, 1, 4, 'Accounts.do', 'Details', NULL, 'Account Details', 'This shows the details of the account, which can be modified. Each account can have folders, contacts, opportunities, revenue, tickets, and documents, for which there are separate tabs.', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.153', 0, '2004-01-26 09:18:29.153', true);
INSERT INTO help_contents VALUES (125, 1, 4, 'RevenueManager.do', 'Dashboard', NULL, 'Revenue Dashboard ', 'This revenue dashboard shows a progress chart for different years and types. All the accounts with revenue are also shown along with a list of employees working under you are also listed under the progress chart. You can add accounts, search for the existing ones in the system based on different filters and export the data in different formats.', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.18', 0, '2004-01-26 09:18:29.18', true);
INSERT INTO help_contents VALUES (126, 1, 4, 'Accounts.do', 'Reports', NULL, 'Export Data ', 'The data can be filtered, exported, displayed, and downloaded in different formats.You can also see the number of times an exported report has been downloaded.', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.243', 0, '2004-01-26 09:18:29.243', true);
INSERT INTO help_contents VALUES (127, 1, 4, 'Accounts.do', 'ModifyFields', NULL, 'Modify folder record ', 'The Folder record details can be updated. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.291', 0, '2004-01-26 09:18:29.291', true);
INSERT INTO help_contents VALUES (128, 1, 4, 'AccountTickets.do', 'ReopenTicket', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:29.321', 0, '2004-01-26 09:18:29.321', true);
INSERT INTO help_contents VALUES (129, 1, 4, 'Accounts.do', 'Delete', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:29.346', 0, '2004-01-26 09:18:29.346', true);
INSERT INTO help_contents VALUES (130, 1, 4, 'Accounts.do', 'GenerateForm', NULL, 'Generate New Export', 'To generate the Export data', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.352', 0, '2004-01-26 09:18:29.352', true);
INSERT INTO help_contents VALUES (131, 1, 4, 'AccountContactsCalls.do', 'View', NULL, 'Call Details', 'Calls associated with the contact', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.381', 0, '2004-01-26 09:18:29.381', true);
INSERT INTO help_contents VALUES (132, 1, 4, 'Accounts.do', 'AddFolderRecord', NULL, 'Add folder record', 'A new Folder record can be added to the Folder.', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.435', 0, '2004-01-26 09:18:29.435', true);
INSERT INTO help_contents VALUES (133, 1, 4, 'AccountContactsCalls.do', 'Add', NULL, 'Add a call', 'You can add a new call, which is associated with a particular contact.', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.459', 0, '2004-01-26 09:18:29.459', true);
INSERT INTO help_contents VALUES (134, 1, 4, 'AccountsDocuments.do', 'Add', NULL, 'Upload Document ', 'New documents can be uploaded and associated with an account', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.501', 0, '2004-01-26 09:18:29.501', true);
INSERT INTO help_contents VALUES (135, 1, 4, 'AccountsDocuments.do', 'Add ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:29.531', 0, '2004-01-26 09:18:29.531', true);
INSERT INTO help_contents VALUES (136, 1, 4, 'AccountContactsOpps.do', 'UpdateOpp', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:29.538', 0, '2004-01-26 09:18:29.538', true);
INSERT INTO help_contents VALUES (137, 1, 4, 'AccountTicketsDocuments.do', 'AddVersion', NULL, NULL, 'Upload a New Version of an existing Document ', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.561', 0, '2004-01-26 09:18:29.561', true);
INSERT INTO help_contents VALUES (138, 1, 4, 'Accounts.do', 'Details ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:29.59', 0, '2004-01-26 09:18:29.59', true);
INSERT INTO help_contents VALUES (139, 1, 4, 'Accounts.do', 'View', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:29.594', 0, '2004-01-26 09:18:29.594', true);
INSERT INTO help_contents VALUES (140, 1, 4, 'AccountTickets.do', 'AddTicket', NULL, 'Adding a new Ticket', 'This page lets you create a new ticket for the account ', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.598', 0, '2004-01-26 09:18:29.598', true);
INSERT INTO help_contents VALUES (141, 1, 4, 'AccountTicketsDocuments.do', 'View', NULL, 'Document Details', 'Here the documents associated with the ticket are listed. New documents related to the ticket can be added', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.627', 0, '2004-01-26 09:18:29.627', true);
INSERT INTO help_contents VALUES (142, 1, 4, 'AccountTickets.do', 'UpdateTicket', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:29.692', 0, '2004-01-26 09:18:29.692', true);
INSERT INTO help_contents VALUES (143, 1, 4, 'AccountContactsOppComponents.do', 'Prepare', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:29.699', 0, '2004-01-26 09:18:29.699', true);
INSERT INTO help_contents VALUES (144, 1, 4, 'Accounts.do', 'InsertFields', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:29.717', 0, '2004-01-26 09:18:29.717', true);
INSERT INTO help_contents VALUES (145, 1, 4, 'AccountContactsOpps.do', 'Save', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:29.733', 0, '2004-01-26 09:18:29.733', true);
INSERT INTO help_contents VALUES (146, 1, 4, 'Accounts.do', 'Search', NULL, NULL, 'Lists the Accounts present and also lets you create an account', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.741', 0, '2004-01-26 09:18:29.741', true);
INSERT INTO help_contents VALUES (147, 1, 4, 'AccountTicketsDocuments.do', 'Details', NULL, NULL, 'Page shows all the versions of the current document', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.80', 0, '2004-01-26 09:18:29.80', true);
INSERT INTO help_contents VALUES (148, 1, 4, 'AccountTicketsDocuments.do', 'Modify', NULL, NULL, 'Modify the current document', NULL, NULL, NULL, 0, '2004-01-26 09:18:29.83', 0, '2004-01-26 09:18:29.83', true);
INSERT INTO help_contents VALUES (149, 1, 4, 'Accounts.do', 'Insert', NULL, 'Account Details', 'Displays the details of the account, which can be modified. Each account can have folders, the contacts, opportunities, revenue, and tickets. You can update several documents associated with each account. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.214', 0, '2004-01-26 09:18:30.214', true);
INSERT INTO help_contents VALUES (150, 1, 4, 'AccountContactsCalls.do', 'Details', NULL, 'Call details', 'The details of the call are shown here which can be modified, deleted or forwarded to any of the users.', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.243', 0, '2004-01-26 09:18:30.243', true);
INSERT INTO help_contents VALUES (151, 1, 4, 'AccountContactsCalls.do', 'Modify', NULL, 'Add / update a call', 'You can add a new call to a contact.', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.267', 0, '2004-01-26 09:18:30.267', true);
INSERT INTO help_contents VALUES (152, 1, 4, 'AccountContactsCalls.do', 'Save', NULL, 'Call details', 'The details of the call are shown here, and can be modified, deleted or forwarded to any of the employees', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.309', 0, '2004-01-26 09:18:30.309', true);
INSERT INTO help_contents VALUES (153, 1, 4, 'AccountContactsCalls.do', 'ForwardCall', NULL, 'Forward Call ', 'The details of the calls that are associated with a contact can be forwarded to different employees.', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.333', 0, '2004-01-26 09:18:30.333', true);
INSERT INTO help_contents VALUES (154, 1, 4, 'AccountContactsOpps.do', 'ViewOpps', NULL, 'List of Opportunities', 'Opportunities associated with the contact, showing the best guess total and last modified date.', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.357', 0, '2004-01-26 09:18:30.357', true);
INSERT INTO help_contents VALUES (155, 1, 4, 'AccountContactsOpps.do', 'DetailsOpp', NULL, 'Opportunity Details', 'You can view all the details about the components here and also add a new component to a particular opportunity. The opportunity can be renamed and its details can be modified', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.441', 0, '2004-01-26 09:18:30.441', true);
INSERT INTO help_contents VALUES (156, 1, 4, 'AccountTickets.do', 'TicketDetails', NULL, 'Ticket Details ', 'This page lets you view the details of the ticket, and also lets you modify or delete the ticket.', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.495', 0, '2004-01-26 09:18:30.495', true);
INSERT INTO help_contents VALUES (157, 1, 4, 'AccountTickets.do', 'ModifyTicket', NULL, 'Modify ticket', 'This page lets you modify the ticket information and update its details', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.525', 0, '2004-01-26 09:18:30.525', true);
INSERT INTO help_contents VALUES (158, 1, 4, 'AccountTicketTasks.do', 'List', NULL, 'Task Details', 'This page lists the tasks assigned for a particular account. New tasks can be added, which would then appear in the list of tasks, showing their priority and their assignment.', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.567', 0, '2004-01-26 09:18:30.567', true);
INSERT INTO help_contents VALUES (159, 1, 4, 'AccountTicketsDocuments.do', 'Add', NULL, 'Uploading a Document', 'Upload a new document related to the account.', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.615', 0, '2004-01-26 09:18:30.615', true);
INSERT INTO help_contents VALUES (160, 1, 4, 'AccountTickets.do', 'ViewHistory', NULL, 'Ticket Log History', 'This page maintains a complete log history of each ticket from its creation till the ticket is closed.', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.644', 0, '2004-01-26 09:18:30.644', true);
INSERT INTO help_contents VALUES (161, 1, 4, 'AccountsDocuments.do', 'Details', NULL, 'Document Details ', 'All the versions of the current document are listed here', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.651', 0, '2004-01-26 09:18:30.651', true);
INSERT INTO help_contents VALUES (162, 1, 4, 'AccountsDocuments.do', 'Modify', NULL, 'Modify Document ', 'Modify the document information', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.675', 0, '2004-01-26 09:18:30.675', true);
INSERT INTO help_contents VALUES (163, 1, 4, 'AccountsDocuments.do', 'AddVersion', NULL, 'Upload New Version ', 'Upload a new document version', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.705', 0, '2004-01-26 09:18:30.705', true);
INSERT INTO help_contents VALUES (164, 1, 4, 'Accounts.do', 'ExportReport', NULL, 'List of Exported data', 'The data can be filtered, exported and displayed in different formats. You can then view the data and also download it.', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.728', 0, '2004-01-26 09:18:30.728', true);
INSERT INTO help_contents VALUES (165, 1, 4, 'RevenueManager.do', 'Details', NULL, 'Revenue Details ', 'Details about revenue', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.776', 0, '2004-01-26 09:18:30.776', true);
INSERT INTO help_contents VALUES (166, 1, 4, 'RevenueManager.do', 'Dashboard ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:30.80', 0, '2004-01-26 09:18:30.80', true);
INSERT INTO help_contents VALUES (167, 1, 4, 'OpportunityForm.do', 'Prepare', NULL, 'Add Opportunity', 'A new opportunity associated with the contact can be added ', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.807', 0, '2004-01-26 09:18:30.807', true);
INSERT INTO help_contents VALUES (168, 1, 4, 'Opportunities.do', 'Add', NULL, 'Add opportunity ', 'A new opportunity associated with the contact can be added', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.831', 0, '2004-01-26 09:18:30.831', true);
INSERT INTO help_contents VALUES (169, 1, 4, 'Opportunities.do', 'Details', NULL, 'Opportunity Details', 'You can view all the details about the components here like the status, the guess amount and the current stage. A new component can also be added to a particular opportunity. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.855', 0, '2004-01-26 09:18:30.855', true);
INSERT INTO help_contents VALUES (170, 1, 4, 'Opportunities.do', 'Modify', NULL, 'Modify Opportunity ', 'The details of the opportunity can be modified', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.956', 0, '2004-01-26 09:18:30.956', true);
INSERT INTO help_contents VALUES (171, 1, 4, 'OpportunitiesComponents.do', 'DetailsComponent', NULL, 'Component Details ', 'This page shows the details about the opportunity like what is the probability of closing the opportunity, what is the current stage of the opportunity etc', NULL, NULL, NULL, 0, '2004-01-26 09:18:30.98', 0, '2004-01-26 09:18:30.98', true);
INSERT INTO help_contents VALUES (172, 1, 4, 'OpportunitiesComponents.do', 'Prepare', NULL, 'Add a component', 'A component can be added to an opportunity and assigned to any employee present in the system. The component type can also be selected.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.004', 0, '2004-01-26 09:18:31.004', true);
INSERT INTO help_contents VALUES (173, 1, 4, 'OpportunitiesComponents.do', 'ModifyComponent', NULL, 'Modify Component ', 'The details of the component can be added / updated to an opportunity and assigned to any employee present in the system. The component type can also be selected.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.034', 0, '2004-01-26 09:18:31.034', true);
INSERT INTO help_contents VALUES (174, 1, 4, 'OpportunitiesComponents.do', 'SaveComponent', NULL, 'Component Details', 'This page shows the details about the opportunity like what is the probability of closing the opportunity, what is the current stage of the opportunity etc', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.082', 0, '2004-01-26 09:18:31.082', true);
INSERT INTO help_contents VALUES (175, 6, 5, 'CampaignManager.do', 'Dashboard', NULL, 'Communications Dashboard ', 'Track and analyze campaigns that have been activated and executed.

Messages can be sent out by any combination of email, fax, or mail merge. The Dashboard shows an overview of sent messages and allows you to drill down and view recipients and any survey results.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.137', 0, '2004-01-26 09:18:31.137', true);
INSERT INTO help_contents VALUES (176, 6, 5, 'CampaignManager.do', 'Add', NULL, 'Add a campaign', 'This page lets you add a new campaign into the system.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.238', 0, '2004-01-26 09:18:31.238', true);
INSERT INTO help_contents VALUES (177, 6, 5, 'CampaignManager.do', 'View', NULL, 'Campaign List ', 'Create or work on existing campaigns.

The Campaign Builder allows you to select groups of contacts that you would like to send a message to, as well as schedule a delivery date. Additional options are available.
', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.262', 0, '2004-01-26 09:18:31.262', true);
INSERT INTO help_contents VALUES (178, 6, 5, 'CampaignManagerGroup.do', 'View', NULL, 'View Groups ', 'Each campaign needs at least one group to which to send a message. Use criteria to filter the contacts you need to reach and use them over and over again. As new contacts meet the criteria, they will be automatically included in future campaigns. This page lists the group names. It shows the groups created by you or everybody; i.e. all the groups.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.34', 0, '2004-01-26 09:18:31.34', true);
INSERT INTO help_contents VALUES (179, 6, 5, 'CampaignManagerGroup.do', 'Add', NULL, 'Add a Group', 'A new contact group can be added. Separate criteria can be specified when creating the group. The criteria can be defined to generate a list. The list to be added can be previewed before saving the group details. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.394', 0, '2004-01-26 09:18:31.394', true);
INSERT INTO help_contents VALUES (180, 6, 5, 'CampaignManagerMessage.do', 'View', NULL, 'Message List ', 'Compose a message to reach your audience.
Each campaign requires a message that will be sent to selected groups. Write the message once, then use it in any number of future campaigns. Modified messages will only affect future campaigns. Displays the list of messages, with the description and other details.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.477', 0, '2004-01-26 09:18:31.477', true);
INSERT INTO help_contents VALUES (181, 6, 5, 'CampaignManagerMessage.do', 'Add', NULL, 'Adding a Message', 'You can add a new message for the campaign, which would show up in the message list.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.533', 0, '2004-01-26 09:18:31.533', true);
INSERT INTO help_contents VALUES (182, 6, 5, 'CampaignManagerAttachment.do', NULL, NULL, 'Create Attachments ', 'Customize and configure your Campaigns with attachments. Attachments can include interactive items, like surveys, or provide additional materials like files.
', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.573', 0, '2004-01-26 09:18:31.573', true);
INSERT INTO help_contents VALUES (183, 6, 5, 'CampaignManagerGroup.do', 'Details', NULL, NULL, 'Contacts of the group are displayed', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.597', 0, '2004-01-26 09:18:31.597', true);
INSERT INTO help_contents VALUES (184, 6, 5, 'CampaignDocuments.do', 'AddVersion', NULL, NULL, 'version change of a document', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.635', 0, '2004-01-26 09:18:31.635', true);
INSERT INTO help_contents VALUES (185, 6, 5, 'CampaignManager.do', 'Dashboard ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:31.663', 0, '2004-01-26 09:18:31.663', true);
INSERT INTO help_contents VALUES (186, 6, 5, 'CampaignManagerMessage.do', 'Insert', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:31.67', 0, '2004-01-26 09:18:31.67', true);
INSERT INTO help_contents VALUES (187, 6, 5, 'CampaignManagerGroup.do', 'Update', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:31.694', 0, '2004-01-26 09:18:31.694', true);
INSERT INTO help_contents VALUES (188, 6, 5, 'CampaignDocuments.do', 'Add', NULL, NULL, 'New documents can be uploaded and be associated with the campaign.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.70', 0, '2004-01-26 09:18:31.70', true);
INSERT INTO help_contents VALUES (189, 6, 5, 'CampaignManager.do', 'ResponseDetails', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:31.729', 0, '2004-01-26 09:18:31.729', true);
INSERT INTO help_contents VALUES (190, 6, 5, 'CampaignManagerGroup.do', 'Preview', NULL, NULL, 'Here details about the contacts, i.e. the name, their company, and their email address are displayed.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.748', 0, '2004-01-26 09:18:31.748', true);
INSERT INTO help_contents VALUES (191, 6, 5, 'CampaignManager.do', 'PrepareDownload', NULL, 'Campaign Details', 'This page shows the details about the campaign and also shows the list of available documents.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.771', 0, '2004-01-26 09:18:31.771', true);
INSERT INTO help_contents VALUES (192, 6, 5, 'CampaignManager.do', 'ViewGroups', NULL, 'Groups', 'The group name along with the contacts present in the Group are listed ', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.795', 0, '2004-01-26 09:18:31.795', true);
INSERT INTO help_contents VALUES (193, 6, 5, 'CampaignManagerSurvey.do', 'Add', NULL, 'Adding a survey', 'You can add a new survey here', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.802', 0, '2004-01-26 09:18:31.802', true);
INSERT INTO help_contents VALUES (194, 6, 5, 'CampaignManagerSurvey.do', 'MoveQuestion', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:31.825', 0, '2004-01-26 09:18:31.825', true);
INSERT INTO help_contents VALUES (195, 6, 5, 'CampaignManager.do', 'Details', NULL, 'Campaign Details ', 'Campaign details are the number of groups selected for the campaign, the text message of the campaign, when is it scheduled to run, how the delivery of the message done, who entered these details and who modified it are shown here.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.843', 0, '2004-01-26 09:18:31.843', true);
INSERT INTO help_contents VALUES (196, 6, 5, 'CampaignDocuments.do', 'Details', NULL, NULL, 'All Versions of this current Document are shown here with the details like the size of the uploaded file, the number of downloads etc.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.867', 0, '2004-01-26 09:18:31.867', true);
INSERT INTO help_contents VALUES (197, 6, 5, 'CampaignDocuments.do', 'Modify', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:31.897', 0, '2004-01-26 09:18:31.897', true);
INSERT INTO help_contents VALUES (198, 6, 5, 'CampaignManager.do', 'Insert', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:31.903', 0, '2004-01-26 09:18:31.903', true);
INSERT INTO help_contents VALUES (199, 6, 5, 'CampaignManagerSurvey.do', 'ViewReturn', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:31.927', 0, '2004-01-26 09:18:31.927', true);
INSERT INTO help_contents VALUES (200, 6, 5, 'CampaignManagerMessage.do', 'Details ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:31.933', 0, '2004-01-26 09:18:31.933', true);
INSERT INTO help_contents VALUES (201, 6, 5, 'CampaignManager.do', 'ViewSchedule', NULL, NULL, 'For the campaign you can schedule a delivery date to send the message to the recipients.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.957', 0, '2004-01-26 09:18:31.957', true);
INSERT INTO help_contents VALUES (202, 6, 5, 'CampaignManagerGroup.do', 'Modify', NULL, NULL, 'Here you can update the group details and also the update contact criteria.', NULL, NULL, NULL, 0, '2004-01-26 09:18:31.987', 0, '2004-01-26 09:18:31.987', true);
INSERT INTO help_contents VALUES (203, 6, 5, 'CampaignManager.do', 'PreviewRecipients', NULL, 'List of Recipients', 'The page displays a list of recipients with their name, company name, the date when the campaign was sent to those recipients and its status. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.041', 0, '2004-01-26 09:18:32.041', true);
INSERT INTO help_contents VALUES (204, 6, 5, 'CampaignManager.do', 'PreviewMessage', NULL, 'Message Details ', 'The message details are shown here, in the form of an email with a subject and message.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.049', 0, '2004-01-26 09:18:32.049', true);
INSERT INTO help_contents VALUES (205, 6, 5, 'CampaignManager.do', 'PreviewSchedule', NULL, 'Campaign Schedule', 'This shows the delivery date and the delivery method or the campaign. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.077', 0, '2004-01-26 09:18:32.077', true);
INSERT INTO help_contents VALUES (206, 6, 5, 'CampaignManager.do', 'ViewResults', NULL, 'Campaign Results', 'This page shows the results of the responses received from all the recipients in the group. This also shows the Last response received.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.089', 0, '2004-01-26 09:18:32.089', true);
INSERT INTO help_contents VALUES (207, 6, 5, 'CampaignManager.do', 'ViewResponse', NULL, 'Campaign Response', 'This page shows the responses of all the recipients along with their system IP addresses and their email address', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.097', 0, '2004-01-26 09:18:32.097', true);
INSERT INTO help_contents VALUES (208, 6, 5, 'CampaignDocuments.do', 'View', NULL, 'Campaign Document details', 'This page lists all the documents associated with this campaign and for each document it lists the size of the file, the extension and the version of the file.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.114', 0, '2004-01-26 09:18:32.114', true);
INSERT INTO help_contents VALUES (209, 6, 5, 'CampaignManager.do', 'ViewDetails', NULL, 'Campaign Details', 'This is the detail page for the campaign, where step-by-step information is given on how to activate the campaign; i.e. what should be selected before a campaign is activated.

This campaign can be configured and can now be activated.
Once activated, today''s campaigns will begin processing in under 5 minutes and cannot be cancelled.
', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.179', 0, '2004-01-26 09:18:32.179', true);
INSERT INTO help_contents VALUES (210, 6, 5, 'CampaignManager.do', 'AddGroups', NULL, 'Choose Groups ', 'Selecting or updating the group / groups for the campaign can be done here.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.233', 0, '2004-01-26 09:18:32.233', true);
INSERT INTO help_contents VALUES (211, 6, 5, 'CampaignManager.do', 'ViewMessage', NULL, 'Message Details', 'Updating a message for the campaign', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.281', 0, '2004-01-26 09:18:32.281', true);
INSERT INTO help_contents VALUES (212, 6, 5, 'CampaignManager.do', 'ViewAttachmentsOverview', NULL, 'Attachment Details', 'For each message, we can add the attachments.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.323', 0, '2004-01-26 09:18:32.323', true);
INSERT INTO help_contents VALUES (213, 6, 5, 'CampaignManager.do', 'ViewAttachment', NULL, 'Surveys ', 'A survey can be selected for this campaign.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.347', 0, '2004-01-26 09:18:32.347', true);
INSERT INTO help_contents VALUES (214, 6, 5, 'CampaignManager.do', 'ManageFileAttachments', NULL, 'File Attachments ', 'Campaign can also have a file as an attachment', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.383', 0, '2004-01-26 09:18:32.383', true);
INSERT INTO help_contents VALUES (215, 6, 5, 'CampaignManager.do', 'Modify', NULL, 'Modify Campaign Details', 'Updating the campaign name /description', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.412', 0, '2004-01-26 09:18:32.412', true);
INSERT INTO help_contents VALUES (216, 6, 5, 'CampaignManagerMessage.do', 'Details', NULL, 'Message Details ', 'This page shows the details of the message.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.442', 0, '2004-01-26 09:18:32.442', true);
INSERT INTO help_contents VALUES (217, 6, 5, 'CampaignManagerMessage.do', 'Modify', NULL, 'Modify Message ', 'This page lets you Add/Update a new message. The message can have an access type, limiting who can view a message.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.473', 0, '2004-01-26 09:18:32.473', true);
INSERT INTO help_contents VALUES (218, 6, 5, 'CampaignManagerMessage.do', 'Update', NULL, 'Message Details ', 'This page shows the details of the message.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.503', 0, '2004-01-26 09:18:32.503', true);
INSERT INTO help_contents VALUES (219, 6, 5, 'CampaignManagerMessage.do', 'Clone', NULL, 'Adding a Message ', 'This page lets you Add a new message or Update an existing one. The message can have an access type, defining who can view it.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.538', 0, '2004-01-26 09:18:32.538', true);
INSERT INTO help_contents VALUES (220, 6, 5, 'CampaignManagerSurvey.do', 'View', NULL, 'List of Surveys', 'This page displays the surveys created and lets you update them.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.571', 0, '2004-01-26 09:18:32.571', true);
INSERT INTO help_contents VALUES (221, 6, 5, 'CampaignManagerSurvey.do', 'InsertAndAdd', NULL, 'Survey Questions', 'Here you can add a new survey question. If the question type is "Item List", you can edit the list of items present in that list and also mark whether the particular question is required or not.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.646', 0, '2004-01-26 09:18:32.646', true);
INSERT INTO help_contents VALUES (222, 6, 5, 'CampaignManagerSurvey.do', 'Details', NULL, 'Survey Details ', 'The details about the survey are displayed here along with the option to modify, delete and preview the survey. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.713', 0, '2004-01-26 09:18:32.713', true);
INSERT INTO help_contents VALUES (223, 6, 5, 'CampaignManagerSurvey.do', 'Modify', NULL, 'Survey Details', 'This page displays all the questions added to a particular survey. It also enables you to add new questions. The order of the questions can be changed by moving questions up or down in the list. Questions can also be edited or deleted. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.773', 0, '2004-01-26 09:18:32.773', true);
INSERT INTO help_contents VALUES (224, 6, 5, 'CampaignManager.do', NULL, NULL, 'Overview', 'You are looking at the communications module. This page reviews and manages campaigns with the following options: Dashboard, Campaign Builder, Build groups, Create messages and create attachments.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.862', 0, '2004-01-26 09:18:32.862', true);
INSERT INTO help_contents VALUES (225, 8, 6, 'TroubleTickets.do', 'Details', NULL, 'Ticket Details ', 'This page lets you view the details of the ticket also lets you modify or delete the ticket.', NULL, NULL, NULL, 0, '2004-01-26 09:18:32.977', 0, '2004-01-26 09:18:32.977', true);
INSERT INTO help_contents VALUES (226, 8, 6, 'TroubleTickets.do', 'Add', NULL, 'Add a Ticket', 'You can add a new ticket here', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.018', 0, '2004-01-26 09:18:33.018', true);
INSERT INTO help_contents VALUES (227, 8, 6, 'TroubleTickets.do', 'SearchTicketsForm', NULL, 'Search Existing Tickets', 'Form used for searching existing tickets based on different filters and parameters.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.048', 0, '2004-01-26 09:18:33.048', true);
INSERT INTO help_contents VALUES (228, 8, 6, 'TroubleTickets.do', 'Reports', NULL, 'Export Data ', 'This is the page shows exported data.
The data can be exported in different formats. The exported data can be viewed with its subject, the size of the exported data file, when it was created and by whom. It also shows the number of times that particular exported file was downloaded. A new data file can also be exported.
', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.072', 0, '2004-01-26 09:18:33.072', true);
INSERT INTO help_contents VALUES (229, 8, 6, 'TroubleTickets.do', 'Modify', NULL, 'Modify Ticket Details', 'Here you can modify ticket details like information, classification, assignment and resolution.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.138', 0, '2004-01-26 09:18:33.138', true);
INSERT INTO help_contents VALUES (230, 8, 6, 'TroubleTickets.do', 'Modify', NULL, 'Modify Ticket Details', 'Here you can modify the ticket details like ticket information, it''s classification, the ticket��s assignment and it''s resolution.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.162', 0, '2004-01-26 09:18:33.162', true);
INSERT INTO help_contents VALUES (231, 8, 6, 'TroubleTicketTasks.do', 'List', NULL, 'List of Tasks', 'This page lists the tasks assigned for a particular ticket. New tasks can be added, which would then appear in the list of tasks, showing their priority, their assignment and other details.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.192', 0, '2004-01-26 09:18:33.192', true);
INSERT INTO help_contents VALUES (232, 8, 6, 'TroubleTicketsDocuments.do', 'View', NULL, 'List of Documents ', 'Here the documents associated with a ticket are listed. New documents related to the ticket can be added. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.27', 0, '2004-01-26 09:18:33.27', true);
INSERT INTO help_contents VALUES (233, 8, 6, 'TroubleTicketsFolders.do', 'Fields', NULL, 'List of Folder Records', 'New folders can be created for each ticket. New Folders are defined and configured in the Admin Module. This page also displays a list of records with their details such as when the record was created, last modified, the action performed on the record etc.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.336', 0, '2004-01-26 09:18:33.336', true);
INSERT INTO help_contents VALUES (234, 8, 6, 'TroubleTicketsFolders.do', 'AddFolderRecord', NULL, 'Add Folder Record', 'The details of the record are added
', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.378', 0, '2004-01-26 09:18:33.378', true);
INSERT INTO help_contents VALUES (235, 8, 6, 'TroubleTickets.do', 'ViewHistory', NULL, 'Ticket Log History', 'The log history of the ticket is maintained. ', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.402', 0, '2004-01-26 09:18:33.402', true);
INSERT INTO help_contents VALUES (236, 8, 6, 'TroubleTickets.do', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:33.426', 0, '2004-01-26 09:18:33.426', true);
INSERT INTO help_contents VALUES (237, 8, 6, 'TroubleTickets.do', 'Insert', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:33.451', 0, '2004-01-26 09:18:33.451', true);
INSERT INTO help_contents VALUES (238, 8, 6, 'TroubleTicketsFolders.do', 'InsertFields', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:33.456', 0, '2004-01-26 09:18:33.456', true);
INSERT INTO help_contents VALUES (239, 8, 6, 'TroubleTickets.do', 'GenerateForm', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:33.48', 0, '2004-01-26 09:18:33.48', true);
INSERT INTO help_contents VALUES (240, 8, 6, 'TroubleTickets.do', 'Details ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:33.486', 0, '2004-01-26 09:18:33.486', true);
INSERT INTO help_contents VALUES (241, 8, 6, 'TroubleTickets.do', 'Update', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:33.51', 0, '2004-01-26 09:18:33.51', true);
INSERT INTO help_contents VALUES (242, 8, 6, 'TroubleTickets.do', 'ExportReport', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:33.516', 0, '2004-01-26 09:18:33.516', true);
INSERT INTO help_contents VALUES (243, 8, 6, 'TroubleTicketsFolders.do', 'ModifyFields', NULL, 'Modify Folder Record', 'This lists the details of the folder record, which can be modified.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.534', 0, '2004-01-26 09:18:33.534', true);
INSERT INTO help_contents VALUES (244, 8, 6, 'TroubleTicketsFolders.do', 'UpdateFields', NULL, 'Folder Record Details ', 'The details about the folder along with the record information such as when the record was created and when it was modified is displayed here.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.557', 0, '2004-01-26 09:18:33.557', true);
INSERT INTO help_contents VALUES (245, 8, 6, 'TroubleTickets.do', 'SearchTickets', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:33.587', 0, '2004-01-26 09:18:33.587', true);
INSERT INTO help_contents VALUES (246, 8, 6, 'TroubleTickets.do', 'Home ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:33.594', 0, '2004-01-26 09:18:33.594', true);
INSERT INTO help_contents VALUES (247, 8, 6, 'TroubleTicketsDocuments.do', 'Add', NULL, 'Adding a Document', 'A new document related to a ticket can be uploaded.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.612', 0, '2004-01-26 09:18:33.612', true);
INSERT INTO help_contents VALUES (248, 8, 6, 'TroubleTicketsDocuments.do', 'Details', NULL, 'Document Details', 'This page shows all the versions of the current document.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.635', 0, '2004-01-26 09:18:33.635', true);
INSERT INTO help_contents VALUES (249, 8, 6, 'TroubleTicketsDocuments.do', 'Modify', NULL, 'Modify Document Details', 'This page lets you modify the ticket information and update the details.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.665', 0, '2004-01-26 09:18:33.665', true);
INSERT INTO help_contents VALUES (250, 8, 6, 'TroubleTicketsDocuments.do', 'AddVersion', NULL, 'Add version number to Documents', 'You can upload a new version of an existing document.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.719', 0, '2004-01-26 09:18:33.719', true);
INSERT INTO help_contents VALUES (251, 8, 6, 'TroubleTickets.do', 'Home', NULL, 'Overview', 'This page displays the complete list of the tickets assigned to the user, the list of the tickets present in his department and finally the list of the tickets created by the user. For each ticket, the details about the ticket, such as the ticket number, priority, age of the ticket, the company and finally the assignment details are displayed. The issue details are also shown separately for each ticket.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.749', 0, '2004-01-26 09:18:33.749', true);
INSERT INTO help_contents VALUES (252, 15, 7, 'CompanyDirectory.do', 'ListEmployees', NULL, 'Overview', 'The details of each employee can be viewed, modified or deleted and a new detailed employee record can be added.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.882', 0, '2004-01-26 09:18:33.882', true);
INSERT INTO help_contents VALUES (253, 15, 7, 'CompanyDirectory.do', 'EmployeeDetails', NULL, 'Employee Details ', 'This is the employee detail page. This page displays the email, phone number, addresses and additional details of each employee.', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.929', 0, '2004-01-26 09:18:33.929', true);
INSERT INTO help_contents VALUES (254, 15, 7, 'CompanyDirectory.do', 'Prepare', NULL, 'Add an Employee', 'You can add an employee into the system. The details of the employee such as his email address; phone numbers, address and other additional details can be given along with his name', NULL, NULL, NULL, 0, '2004-01-26 09:18:33.959', 0, '2004-01-26 09:18:33.959', true);
INSERT INTO help_contents VALUES (255, 15, 7, 'CompanyDirectory.do', 'ModifyEmployee', NULL, 'Modify Employee Details ', 'Employee details such as the name of the employee, email address, phone numbers and address can be modified here.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.013', 0, '2004-01-26 09:18:34.013', true);
INSERT INTO help_contents VALUES (256, 15, 7, 'CompanyDirectory.do', 'Save', NULL, NULL, 'This page shows the details of the employee record, which can be modified or deleted.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.037', 0, '2004-01-26 09:18:34.037', true);
INSERT INTO help_contents VALUES (257, 14, 8, 'Reports.do', 'ViewQueue', NULL, 'Overview', 'This is the home of the reports. 

A list of customized reports can be viewed and the queue of the reports that are scheduled to be processed by the server are also displayed. Each report that is ready to be retrieved is displayed along with its details such as the subject of the report, the date when the report was generated, report status and finally the size of the report for the user to download.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.068', 0, '2004-01-26 09:18:34.068', true);
INSERT INTO help_contents VALUES (258, 14, 8, 'Reports.do', 'RunReport', NULL, 'List of Modules', 'This shows the different modules present and displays the list of corresponding reports present in each module.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.115', 0, '2004-01-26 09:18:34.115', true);
INSERT INTO help_contents VALUES (259, 14, 8, 'Reports.do ', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:34.139', 0, '2004-01-26 09:18:34.139', true);
INSERT INTO help_contents VALUES (260, 14, 8, 'Reports.do', 'CancelReport', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:34.158', 0, '2004-01-26 09:18:34.158', true);
INSERT INTO help_contents VALUES (261, 14, 8, 'Reports.do', 'ParameterList', NULL, 'Parameters specification', 'This page takes the parameters that need to be specified to run the report.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.174', 0, '2004-01-26 09:18:34.174', true);
INSERT INTO help_contents VALUES (262, 14, 8, 'Reports.do', 'ListReports', NULL, 'Lis of Reports', 'In this module, you can choose the report that you want to run from the list of the reports.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.211', 0, '2004-01-26 09:18:34.211', true);
INSERT INTO help_contents VALUES (263, 14, 8, 'Reports.do', 'CriteriaList', NULL, 'Criteria List ', 'You can choose to base this report on previously saved criteria, or continue and create new criteria.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.241', 0, '2004-01-26 09:18:34.241', true);
INSERT INTO help_contents VALUES (264, 14, 8, 'Reports.do', 'GenerateReport', NULL, 'Reports Added To Queue', 'This page shows that the requested report is added to the queue. Also lets you know the details about the report and queue status.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.271', 0, '2004-01-26 09:18:34.271', true);
INSERT INTO help_contents VALUES (265, 14, 8, 'Reports.do', NULL, NULL, 'Overview', '
', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.301', 0, '2004-01-26 09:18:34.301', true);
INSERT INTO help_contents VALUES (266, 9, 9, 'Users.do', 'ListUsers', NULL, 'List of Users', 'This section allows the administrator to view and add users and manage user hierarchies. The users are typically employees in your company who interact with your clients or customers.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.367', 0, '2004-01-26 09:18:34.367', true);
INSERT INTO help_contents VALUES (267, 9, 9, 'Users.do', 'InsertUserForm', NULL, 'Adding a New User', 'This form allows new users to be added to the system and records their contact information.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.468', 0, '2004-01-26 09:18:34.468', true);
INSERT INTO help_contents VALUES (268, 9, 9, 'Users.do', 'ModifyUser', NULL, 'Modify User Details', 'This form provides the administrator with an editable view of the user information, and also allows the administrator to view the users login history and view points.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.576', 0, '2004-01-26 09:18:34.576', true);
INSERT INTO help_contents VALUES (269, 9, 9, 'Users.do', 'ViewLog', NULL, 'User Login History', 'Provides a login history of the chosen user.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.631', 0, '2004-01-26 09:18:34.631', true);
INSERT INTO help_contents VALUES (270, 9, 9, 'Viewpoints.do', 'ListViewpoints', NULL, 'Viewpoints of User', 'The page displays the viewpoints of the employees regarding a particular module in the system. Lets you add a new viewpoint. The details displayed are when the viewpoint was entered and whether it is enabled or not.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.672', 0, '2004-01-26 09:18:34.672', true);
INSERT INTO help_contents VALUES (271, 9, 9, 'Viewpoints.do', 'InsertViewpointForm', NULL, 'Add Viewpoint', 'The contact name can be selected and the permissions /access for the modules can be given.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.732', 0, '2004-01-26 09:18:34.732', true);
INSERT INTO help_contents VALUES (272, 9, 9, 'Viewpoints.do', 'ViewpointDetails', NULL, 'Update Viewpoint ', 'You can update a viewpoint and set the permissions to access different modules.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.786', 0, '2004-01-26 09:18:34.786', true);
INSERT INTO help_contents VALUES (273, 9, 9, 'Roles.do', 'ListRoles', NULL, 'List of Roles', 'You are looking at roles. 

This page lists the different roles present in the system, their role descriptions and the number of people present in the system who are assigned that role. New roles can be added at any time.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.834', 0, '2004-01-26 09:18:34.834', true);
INSERT INTO help_contents VALUES (274, 9, 9, 'Roles.do', 'InsertRoleForm', NULL, 'Add a New Role', 'This page will let you Add/Update the roles in the system. Also lets you change the permissions. The permissions can be changed or set for each module separately depending on the role.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.882', 0, '2004-01-26 09:18:34.882', true);
INSERT INTO help_contents VALUES (275, 9, 9, 'Roles.do', 'RoleDetails', NULL, 'Update Role ', 'This page will let you update the roles in the system. Also lets you change the permissions. The permissions can be changed or set for each module separately depending on the role.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.906', 0, '2004-01-26 09:18:34.906', true);
INSERT INTO help_contents VALUES (276, 9, 9, 'Admin.do', 'Config', NULL, 'Configure Modules', 'This page lets you configure modules that meet the needs of your organization, including configuration of lookup lists and custom fields. Depending on permissions, each module that you can configure is listed and each module has a different number of configure options. The changes typically affect all users immediately.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.936', 0, '2004-01-26 09:18:34.936', true);
INSERT INTO help_contents VALUES (277, 9, 9, 'Admin.do', 'ConfigDetails', NULL, 'Configuration Options ', 'You can configure different options in each module.The following are some of the configuration options that you might see in the modules. Some of these options are specific to the module so they might NOT be present in all the modules.', NULL, NULL, NULL, 0, '2004-01-26 09:18:34.984', 0, '2004-01-26 09:18:34.984', true);
INSERT INTO help_contents VALUES (278, 9, 9, 'Admin.do', 'ModifyList', NULL, 'Edit Lookup List ', 'This page lets you edit and add to the list items.', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.068', 0, '2004-01-26 09:18:35.068', true);
INSERT INTO help_contents VALUES (279, 9, 9, 'AdminFieldsFolder.do', 'AddFolder', NULL, 'Adding a New Folder', 'Add/Update the existing folder here', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.094', 0, '2004-01-26 09:18:35.094', true);
INSERT INTO help_contents VALUES (280, 9, 9, 'AdminFieldsFolder.do', 'ModifyFolder', NULL, 'Modify Existing Folder', 'Update the existing folder here', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.116', 0, '2004-01-26 09:18:35.116', true);
INSERT INTO help_contents VALUES (281, 9, 9, 'AdminConfig.do', 'ListGlobalParams', NULL, 'Configure System', 'You can configure the system for the session idle timeout and set the time for the time out.', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.14', 0, '2004-01-26 09:18:35.14', true);
INSERT INTO help_contents VALUES (282, 9, 9, 'AdminConfig.do', 'ModifyTimeout', NULL, 'Modify Timeout ', 'The session timeout is the time in which a user will automatically be logged out if the specified period of inactivity is reached.
', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.166', 0, '2004-01-26 09:18:35.166', true);
INSERT INTO help_contents VALUES (283, 9, 9, 'Admin.do', 'Usage', NULL, 'Resource Usage Details', 'Current System Usage and Billing Usage Information are displayed.', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.205', 0, '2004-01-26 09:18:35.205', true);
INSERT INTO help_contents VALUES (284, 9, 9, 'Admin.do ', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:35.241', 0, '2004-01-26 09:18:35.241', true);
INSERT INTO help_contents VALUES (285, 9, 9, 'Users.do', 'DisableUserConfirm', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:35.266', 0, '2004-01-26 09:18:35.266', true);
INSERT INTO help_contents VALUES (286, 9, 9, 'AdminFieldsFolder.do', NULL, NULL, 'Custom Folders ', 'This page lists all the custom folders created in the General Contacts; let''s you edit them and also allow you to enable/disable the folders.', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.272', 0, '2004-01-26 09:18:35.272', true);
INSERT INTO help_contents VALUES (287, 9, 9, 'AdminFieldsFolder.do', 'ListFolders', NULL, 'List of Custom Folders ', 'This page lists all the custom folders created in the General Contacts; let''s you edit them and also allow you to enable/disable the folders.
', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.356', 0, '2004-01-26 09:18:35.356', true);
INSERT INTO help_contents VALUES (288, 9, 9, 'AdminFields.do', 'ModifyField', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:35.427', 0, '2004-01-26 09:18:35.427', true);
INSERT INTO help_contents VALUES (289, 9, 9, 'Admin.do', 'ListGlobalParams', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:35.434', 0, '2004-01-26 09:18:35.434', true);
INSERT INTO help_contents VALUES (290, 9, 9, 'Admin.do', 'ModifyTimeout', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:35.458', 0, '2004-01-26 09:18:35.458', true);
INSERT INTO help_contents VALUES (291, 9, 9, 'AdminObjectEvents.do', NULL, NULL, 'Object Events:', 'The list of Object Events are displayed along with the corresponding Triggered Processes. The number of components and whether that Object Event is available or not is also shown.', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.464', 0, '2004-01-26 09:18:35.464', true);
INSERT INTO help_contents VALUES (292, 9, 9, 'AdminFieldsGroup.do', 'AddGroup', NULL, NULL, 'Add a group', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.517', 0, '2004-01-26 09:18:35.517', true);
INSERT INTO help_contents VALUES (293, 9, 9, 'AdminFields.do', 'AddField', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:35.541', 0, '2004-01-26 09:18:35.541', true);
INSERT INTO help_contents VALUES (294, 9, 9, 'Admin.do', 'UpdateList', NULL, NULL, 'The Lookup List displays all the list names, which can be edited, the number of items can be known and the ones present can be previewed.

', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.56', 0, '2004-01-26 09:18:35.56', true);
INSERT INTO help_contents VALUES (295, 9, 9, 'AdminScheduledEvents.do', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:35.589', 0, '2004-01-26 09:18:35.589', true);
INSERT INTO help_contents VALUES (296, 9, 9, 'Admin.do', 'Config ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:35.608', 0, '2004-01-26 09:18:35.608', true);
INSERT INTO help_contents VALUES (297, 9, 9, 'Admin.do', 'EditLists', NULL, 'Lookup Lists ', 'The Lookup List displays all the list names, which can be edited, the number of items is displayed and the ones present can be previewed.', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.614', 0, '2004-01-26 09:18:35.614', true);
INSERT INTO help_contents VALUES (298, 9, 9, 'AdminFieldsGroup.do', 'ListGroups', NULL, 'Folder Details', 'This page lists the folder details and the groups added to this folder. Each group can further have a custom field created or deleted. You can also place it in the desired position in the dropdown list.', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.661', 0, '2004-01-26 09:18:35.661', true);
INSERT INTO help_contents VALUES (299, 9, 9, 'AdminCategories.do', 'ViewActive', NULL, 'Active Category Details', 'The four different levels for the "Active" and "Draft" categories are displayed. The level1 has the category name, which is further classified into sub directories/levels. The level1 has the sublevel called level2 which in turn has sublevel called level3 and so on.

', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.775', 0, '2004-01-26 09:18:35.775', true);
INSERT INTO help_contents VALUES (300, 9, 9, 'AdminCategories.do', 'View', NULL, 'Draft Category Details', 'The four different levels for the active and the draft categories are displayed. The level1 has the category name, which is further classified into sub directories/levels. The level1 has the sublevel called level2 which in turn has sublevel called level3 and so on. The draft categories can be edited and activated. The activated draft categories would be then reflected in the Active Categories list. The modified/updated draft category can also be reverted to its original state.', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.811', 0, '2004-01-26 09:18:35.811', true);
INSERT INTO help_contents VALUES (301, 9, 9, 'Users.do', 'InsertUserForm ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:35.883', 0, '2004-01-26 09:18:35.883', true);
INSERT INTO help_contents VALUES (302, 9, 9, 'Users.do', 'UpdateUser', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:35.901', 0, '2004-01-26 09:18:35.901', true);
INSERT INTO help_contents VALUES (303, 9, 9, 'Users.do', 'AddUser', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:35.908', 0, '2004-01-26 09:18:35.908', true);
INSERT INTO help_contents VALUES (304, 9, 9, 'Users.do', 'UserDetails', NULL, 'User Details', 'This form provides the administrator with more information about the user, namely information pertaining to the users login history and view points.', NULL, NULL, NULL, 0, '2004-01-26 09:18:35.931', 0, '2004-01-26 09:18:35.931', true);
INSERT INTO help_contents VALUES (305, 9, 9, 'Roles.do', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:36.009', 0, '2004-01-26 09:18:36.009', true);
INSERT INTO help_contents VALUES (306, 9, 9, 'Roles.do', 'ListRoles ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:36.027', 0, '2004-01-26 09:18:36.027', true);
INSERT INTO help_contents VALUES (307, 9, 9, 'Viewpoints.do', 'InsertViewpoint ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:36.033', 0, '2004-01-26 09:18:36.033', true);
INSERT INTO help_contents VALUES (308, 9, 9, 'Viewpoints.do', 'InsertViewpoint', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:36.051', 0, '2004-01-26 09:18:36.051', true);
INSERT INTO help_contents VALUES (309, 9, 9, 'Admin.do', NULL, NULL, 'Overview', 'You are looking at the Admin module home page. Here you can manage the system by reviewing its usage, configuring specific modules and system parameters. 
', NULL, NULL, NULL, 0, '2004-01-26 09:18:36.057', 0, '2004-01-26 09:18:36.057', true);
INSERT INTO help_contents VALUES (310, 11, 13, 'Search.do', 'SiteSearch ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2004-01-26 09:18:36.177', 0, '2004-01-26 09:18:36.177', true);
INSERT INTO help_contents VALUES (311, 11, 13, 'Search.do', 'SiteSearch', NULL, 'General Search', 'You can search the system for data associated with a particular key term. This can be done using the search data text box present on the left side of the window. The data associated with the corresponding key term is looked for in different modules for a match and the results are displayed per module. The search results are shown with detail description.', NULL, NULL, NULL, 0, '2004-01-26 09:18:36.183', 0, '2004-01-26 09:18:36.183', true);



INSERT INTO help_tableof_contents VALUES (1, 'Modules', NULL, NULL, NULL, NULL, 1, 5, 0, '2004-01-26 09:18:36.217', 0, '2004-01-26 09:18:36.217', true);
INSERT INTO help_tableof_contents VALUES (2, 'My Home Page', NULL, NULL, 1, 12, 2, 5, 0, '2004-01-26 09:18:36.238', 0, '2004-01-26 09:18:36.238', true);
INSERT INTO help_tableof_contents VALUES (3, 'Overview', NULL, NULL, 2, 12, 3, 5, 0, '2004-01-26 09:18:36.261', 0, '2004-01-26 09:18:36.261', true);
INSERT INTO help_tableof_contents VALUES (4, 'Mailbox', NULL, NULL, 2, 12, 3, 10, 0, '2004-01-26 09:18:36.318', 0, '2004-01-26 09:18:36.318', true);
INSERT INTO help_tableof_contents VALUES (5, 'Message Details', NULL, NULL, 4, 12, 4, 15, 0, '2004-01-26 09:18:36.344', 0, '2004-01-26 09:18:36.344', true);
INSERT INTO help_tableof_contents VALUES (6, 'New Message ', NULL, NULL, 4, 12, 4, 20, 0, '2004-01-26 09:18:36.371', 0, '2004-01-26 09:18:36.371', true);
INSERT INTO help_tableof_contents VALUES (7, 'Reply Message', NULL, NULL, 4, 12, 4, 25, 0, '2004-01-26 09:18:36.398', 0, '2004-01-26 09:18:36.398', true);
INSERT INTO help_tableof_contents VALUES (8, 'SendMessage', NULL, NULL, 4, 12, 4, 30, 0, '2004-01-26 09:18:36.428', 0, '2004-01-26 09:18:36.428', true);
INSERT INTO help_tableof_contents VALUES (9, 'Forward message', NULL, NULL, 4, 12, 4, 35, 0, '2004-01-26 09:18:36.458', 0, '2004-01-26 09:18:36.458', true);
INSERT INTO help_tableof_contents VALUES (10, 'Tasks', NULL, NULL, 2, 12, 3, 40, 0, '2004-01-26 09:18:36.488', 0, '2004-01-26 09:18:36.488', true);
INSERT INTO help_tableof_contents VALUES (11, 'Advanced Task', NULL, NULL, 10, 12, 4, 45, 0, '2004-01-26 09:18:36.512', 0, '2004-01-26 09:18:36.512', true);
INSERT INTO help_tableof_contents VALUES (12, 'Forwarding a Task ', NULL, NULL, 10, 12, 4, 50, 0, '2004-01-26 09:18:36.535', 0, '2004-01-26 09:18:36.535', true);
INSERT INTO help_tableof_contents VALUES (13, 'Modify task', NULL, NULL, 10, 12, 4, 55, 0, '2004-01-26 09:18:36.56', 0, '2004-01-26 09:18:36.56', true);
INSERT INTO help_tableof_contents VALUES (14, 'Action Lists', NULL, NULL, 2, 12, 3, 60, 0, '2004-01-26 09:18:36.584', 0, '2004-01-26 09:18:36.584', true);
INSERT INTO help_tableof_contents VALUES (15, 'Action Contacts', NULL, NULL, 14, 12, 4, 65, 0, '2004-01-26 09:18:36.608', 0, '2004-01-26 09:18:36.608', true);
INSERT INTO help_tableof_contents VALUES (16, 'Add Action List', NULL, NULL, 14, 12, 4, 70, 0, '2004-01-26 09:18:36.632', 0, '2004-01-26 09:18:36.632', true);
INSERT INTO help_tableof_contents VALUES (17, 'Modify Action', NULL, NULL, 14, 12, 4, 75, 0, '2004-01-26 09:18:36.662', 0, '2004-01-26 09:18:36.662', true);
INSERT INTO help_tableof_contents VALUES (18, 'Re-assignments', NULL, NULL, 2, 12, 3, 80, 0, '2004-01-26 09:18:36.691', 0, '2004-01-26 09:18:36.691', true);
INSERT INTO help_tableof_contents VALUES (19, 'My Settings', NULL, NULL, 2, 12, 3, 85, 0, '2004-01-26 09:18:36.721', 0, '2004-01-26 09:18:36.721', true);
INSERT INTO help_tableof_contents VALUES (20, 'Personal Information ', NULL, NULL, 19, 12, 4, 90, 0, '2004-01-26 09:18:36.751', 0, '2004-01-26 09:18:36.751', true);
INSERT INTO help_tableof_contents VALUES (21, 'Location Settings', NULL, NULL, 19, 12, 4, 95, 0, '2004-01-26 09:18:36.775', 0, '2004-01-26 09:18:36.775', true);
INSERT INTO help_tableof_contents VALUES (22, 'Update password', NULL, NULL, 19, 12, 4, 100, 0, '2004-01-26 09:18:36.799', 0, '2004-01-26 09:18:36.799', true);
INSERT INTO help_tableof_contents VALUES (23, 'Contacts', NULL, NULL, 1, 2, 2, 10, 0, '2004-01-26 09:18:36.823', 0, '2004-01-26 09:18:36.823', true);
INSERT INTO help_tableof_contents VALUES (24, 'Add a Contact', NULL, NULL, 23, 2, 3, 5, 0, '2004-01-26 09:18:36.841', 0, '2004-01-26 09:18:36.841', true);
INSERT INTO help_tableof_contents VALUES (25, 'Search Contacts ', NULL, NULL, 23, 2, 3, 10, 0, '2004-01-26 09:18:36.865', 0, '2004-01-26 09:18:36.865', true);
INSERT INTO help_tableof_contents VALUES (26, 'Export Data ', NULL, NULL, 23, 2, 3, 15, 0, '2004-01-26 09:18:36.889', 0, '2004-01-26 09:18:36.889', true);
INSERT INTO help_tableof_contents VALUES (27, 'Exporting data', NULL, NULL, 23, 2, 3, 20, 0, '2004-01-26 09:18:36.919', 0, '2004-01-26 09:18:36.919', true);
INSERT INTO help_tableof_contents VALUES (28, 'Pipeline', NULL, NULL, 1, 4, 2, 15, 0, '2004-01-26 09:18:36.949', 0, '2004-01-26 09:18:36.949', true);
INSERT INTO help_tableof_contents VALUES (29, 'Overview', NULL, NULL, 28, 4, 3, 5, 0, '2004-01-26 09:18:36.973', 0, '2004-01-26 09:18:36.973', true);
INSERT INTO help_tableof_contents VALUES (30, 'Add a Opportunity', NULL, NULL, 28, 4, 3, 10, 0, '2004-01-26 09:18:37.003', 0, '2004-01-26 09:18:37.003', true);
INSERT INTO help_tableof_contents VALUES (31, 'Search Opportunities ', NULL, NULL, 28, 4, 3, 15, 0, '2004-01-26 09:18:37.032', 0, '2004-01-26 09:18:37.032', true);
INSERT INTO help_tableof_contents VALUES (32, 'Export Data', NULL, NULL, 28, 4, 3, 20, 0, '2004-01-26 09:18:37.056', 0, '2004-01-26 09:18:37.056', true);
INSERT INTO help_tableof_contents VALUES (33, 'Accounts', NULL, NULL, 1, 1, 2, 20, 0, '2004-01-26 09:18:37.08', 0, '2004-01-26 09:18:37.08', true);
INSERT INTO help_tableof_contents VALUES (34, 'Overview', NULL, NULL, 33, 1, 3, 5, 0, '2004-01-26 09:18:37.087', 0, '2004-01-26 09:18:37.087', true);
INSERT INTO help_tableof_contents VALUES (35, 'Add an Account', NULL, NULL, 33, 1, 3, 10, 0, '2004-01-26 09:18:37.111', 0, '2004-01-26 09:18:37.111', true);
INSERT INTO help_tableof_contents VALUES (36, 'Modify Account', NULL, NULL, 33, 1, 3, 15, 0, '2004-01-26 09:18:37.141', 0, '2004-01-26 09:18:37.141', true);
INSERT INTO help_tableof_contents VALUES (37, 'Contact Details', NULL, NULL, 36, 1, 4, 20, 0, '2004-01-26 09:18:37.172', 0, '2004-01-26 09:18:37.172', true);
INSERT INTO help_tableof_contents VALUES (38, 'Folder Record Details', NULL, NULL, 36, 1, 4, 25, 0, '2004-01-26 09:18:37.219', 0, '2004-01-26 09:18:37.219', true);
INSERT INTO help_tableof_contents VALUES (39, 'Opportunity Details', NULL, NULL, 36, 1, 4, 30, 0, '2004-01-26 09:18:37.248', 0, '2004-01-26 09:18:37.248', true);
INSERT INTO help_tableof_contents VALUES (40, 'Revenue Details', NULL, NULL, 36, 1, 4, 35, 0, '2004-01-26 09:18:37.272', 0, '2004-01-26 09:18:37.272', true);
INSERT INTO help_tableof_contents VALUES (41, 'Revenue Details', NULL, NULL, 40, 1, 5, 40, 0, '2004-01-26 09:18:37.296', 0, '2004-01-26 09:18:37.296', true);
INSERT INTO help_tableof_contents VALUES (42, 'Add Revenue ', NULL, NULL, 40, 1, 5, 45, 0, '2004-01-26 09:18:37.32', 0, '2004-01-26 09:18:37.32', true);
INSERT INTO help_tableof_contents VALUES (43, 'Modify Revenue ', NULL, NULL, 40, 1, 5, 50, 0, '2004-01-26 09:18:37.344', 0, '2004-01-26 09:18:37.344', true);
INSERT INTO help_tableof_contents VALUES (44, 'Ticket Details', NULL, NULL, 36, 1, 4, 55, 0, '2004-01-26 09:18:37.368', 0, '2004-01-26 09:18:37.368', true);
INSERT INTO help_tableof_contents VALUES (45, 'Document Details', NULL, NULL, 36, 1, 4, 60, 0, '2004-01-26 09:18:37.392', 0, '2004-01-26 09:18:37.392', true);
INSERT INTO help_tableof_contents VALUES (46, 'Search Accounts ', NULL, NULL, 33, 1, 3, 65, 0, '2004-01-26 09:18:37.422', 0, '2004-01-26 09:18:37.422', true);
INSERT INTO help_tableof_contents VALUES (47, 'Account Details', NULL, NULL, 33, 1, 3, 70, 0, '2004-01-26 09:18:37.452', 0, '2004-01-26 09:18:37.452', true);
INSERT INTO help_tableof_contents VALUES (48, 'Revenue Dashboard ', NULL, NULL, 33, 1, 3, 75, 0, '2004-01-26 09:18:37.482', 0, '2004-01-26 09:18:37.482', true);
INSERT INTO help_tableof_contents VALUES (49, 'Export Data ', NULL, NULL, 33, 1, 3, 80, 0, '2004-01-26 09:18:37.506', 0, '2004-01-26 09:18:37.506', true);
INSERT INTO help_tableof_contents VALUES (50, 'Communications', NULL, NULL, 1, 6, 2, 25, 0, '2004-01-26 09:18:37.53', 0, '2004-01-26 09:18:37.53', true);
INSERT INTO help_tableof_contents VALUES (51, 'Communications Dashboard ', NULL, NULL, 50, 6, 3, 5, 0, '2004-01-26 09:18:37.549', 0, '2004-01-26 09:18:37.549', true);
INSERT INTO help_tableof_contents VALUES (52, 'Add a campaign', NULL, NULL, 50, 6, 3, 10, 0, '2004-01-26 09:18:37.572', 0, '2004-01-26 09:18:37.572', true);
INSERT INTO help_tableof_contents VALUES (53, 'Campaign List ', NULL, NULL, 50, 6, 3, 15, 0, '2004-01-26 09:18:37.608', 0, '2004-01-26 09:18:37.608', true);
INSERT INTO help_tableof_contents VALUES (54, 'View Groups ', NULL, NULL, 50, 6, 3, 20, 0, '2004-01-26 09:18:37.632', 0, '2004-01-26 09:18:37.632', true);
INSERT INTO help_tableof_contents VALUES (55, 'Add a Group', NULL, NULL, 50, 6, 3, 25, 0, '2004-01-26 09:18:37.656', 0, '2004-01-26 09:18:37.656', true);
INSERT INTO help_tableof_contents VALUES (56, 'Message List ', NULL, NULL, 50, 6, 3, 30, 0, '2004-01-26 09:18:37.686', 0, '2004-01-26 09:18:37.686', true);
INSERT INTO help_tableof_contents VALUES (57, 'Adding a Message', NULL, NULL, 50, 6, 3, 35, 0, '2004-01-26 09:18:37.716', 0, '2004-01-26 09:18:37.716', true);
INSERT INTO help_tableof_contents VALUES (58, 'Create Attachments ', NULL, NULL, 50, 6, 3, 40, 0, '2004-01-26 09:18:37.80', 0, '2004-01-26 09:18:37.80', true);
INSERT INTO help_tableof_contents VALUES (59, 'Help Desk', NULL, NULL, 1, 8, 2, 30, 0, '2004-01-26 09:18:37.807', 0, '2004-01-26 09:18:37.807', true);
INSERT INTO help_tableof_contents VALUES (60, 'Ticket Details ', NULL, NULL, 59, 8, 3, 5, 0, '2004-01-26 09:18:37.824', 0, '2004-01-26 09:18:37.824', true);
INSERT INTO help_tableof_contents VALUES (61, 'Add a Ticket', NULL, NULL, 59, 8, 3, 10, 0, '2004-01-26 09:18:37.848', 0, '2004-01-26 09:18:37.848', true);
INSERT INTO help_tableof_contents VALUES (62, 'Search Existing Tickets', NULL, NULL, 59, 8, 3, 15, 0, '2004-01-26 09:18:37.883', 0, '2004-01-26 09:18:37.883', true);
INSERT INTO help_tableof_contents VALUES (63, 'Export Data ', NULL, NULL, 59, 8, 3, 20, 0, '2004-01-26 09:18:37.907', 0, '2004-01-26 09:18:37.907', true);
INSERT INTO help_tableof_contents VALUES (64, 'Modify Ticket Details', NULL, NULL, 59, 8, 3, 25, 0, '2004-01-26 09:18:37.931', 0, '2004-01-26 09:18:37.931', true);
INSERT INTO help_tableof_contents VALUES (65, 'Modify Ticket Details', NULL, NULL, 64, 8, 4, 30, 0, '2004-01-26 09:18:37.961', 0, '2004-01-26 09:18:37.961', true);
INSERT INTO help_tableof_contents VALUES (66, 'List of Tasks', NULL, NULL, 64, 8, 4, 35, 0, '2004-01-26 09:18:37.991', 0, '2004-01-26 09:18:37.991', true);
INSERT INTO help_tableof_contents VALUES (67, 'List of Documents ', NULL, NULL, 64, 8, 4, 40, 0, '2004-01-26 09:18:38.021', 0, '2004-01-26 09:18:38.021', true);
INSERT INTO help_tableof_contents VALUES (68, 'List of Folder Records', NULL, NULL, 64, 8, 4, 45, 0, '2004-01-26 09:18:38.051', 0, '2004-01-26 09:18:38.051', true);
INSERT INTO help_tableof_contents VALUES (69, 'Add Folder Record', NULL, NULL, 64, 8, 4, 50, 0, '2004-01-26 09:18:38.075', 0, '2004-01-26 09:18:38.075', true);
INSERT INTO help_tableof_contents VALUES (70, 'Ticket Log History', NULL, NULL, 64, 8, 4, 55, 0, '2004-01-26 09:18:38.099', 0, '2004-01-26 09:18:38.099', true);
INSERT INTO help_tableof_contents VALUES (71, 'Employees', NULL, NULL, 1, 15, 2, 35, 0, '2004-01-26 09:18:38.123', 0, '2004-01-26 09:18:38.123', true);
INSERT INTO help_tableof_contents VALUES (72, 'Overview', NULL, NULL, 71, 15, 3, 5, 0, '2004-01-26 09:18:38.129', 0, '2004-01-26 09:18:38.129', true);
INSERT INTO help_tableof_contents VALUES (73, 'Employee Details ', NULL, NULL, 71, 15, 3, 10, 0, '2004-01-26 09:18:38.153', 0, '2004-01-26 09:18:38.153', true);
INSERT INTO help_tableof_contents VALUES (74, 'Add an Employee', NULL, NULL, 71, 15, 3, 15, 0, '2004-01-26 09:18:38.189', 0, '2004-01-26 09:18:38.189', true);
INSERT INTO help_tableof_contents VALUES (75, 'Modify Employee Details ', NULL, NULL, 71, 15, 3, 20, 0, '2004-01-26 09:18:38.213', 0, '2004-01-26 09:18:38.213', true);
INSERT INTO help_tableof_contents VALUES (76, 'Reports', NULL, NULL, 1, 14, 2, 40, 0, '2004-01-26 09:18:38.243', 0, '2004-01-26 09:18:38.243', true);
INSERT INTO help_tableof_contents VALUES (77, 'Overview', NULL, NULL, 76, 14, 3, 5, 0, '2004-01-26 09:18:38.267', 0, '2004-01-26 09:18:38.267', true);
INSERT INTO help_tableof_contents VALUES (78, 'List of Modules', NULL, NULL, 76, 14, 3, 10, 0, '2004-01-26 09:18:38.297', 0, '2004-01-26 09:18:38.297', true);
INSERT INTO help_tableof_contents VALUES (79, 'Admin', NULL, NULL, 1, 9, 2, 45, 0, '2004-01-26 09:18:38.321', 0, '2004-01-26 09:18:38.321', true);
INSERT INTO help_tableof_contents VALUES (80, 'List of Users', NULL, NULL, 79, 9, 3, 5, 0, '2004-01-26 09:18:38.327', 0, '2004-01-26 09:18:38.327', true);
INSERT INTO help_tableof_contents VALUES (81, 'Adding a New User', NULL, NULL, 80, 9, 4, 10, 0, '2004-01-26 09:18:38.363', 0, '2004-01-26 09:18:38.363', true);
INSERT INTO help_tableof_contents VALUES (82, 'Modify User Details', NULL, NULL, 80, 9, 4, 15, 0, '2004-01-26 09:18:38.387', 0, '2004-01-26 09:18:38.387', true);
INSERT INTO help_tableof_contents VALUES (83, 'User Login History', NULL, NULL, 80, 9, 4, 20, 0, '2004-01-26 09:18:38.411', 0, '2004-01-26 09:18:38.411', true);
INSERT INTO help_tableof_contents VALUES (84, 'Viewpoints of User', NULL, NULL, 80, 9, 4, 25, 0, '2004-01-26 09:18:38.434', 0, '2004-01-26 09:18:38.434', true);
INSERT INTO help_tableof_contents VALUES (85, 'Add Viewpoint', NULL, NULL, 84, 9, 5, 30, 0, '2004-01-26 09:18:38.464', 0, '2004-01-26 09:18:38.464', true);
INSERT INTO help_tableof_contents VALUES (86, 'Update Viewpoint ', NULL, NULL, 84, 9, 5, 35, 0, '2004-01-26 09:18:38.494', 0, '2004-01-26 09:18:38.494', true);
INSERT INTO help_tableof_contents VALUES (87, 'List of Roles', NULL, NULL, 79, 9, 3, 40, 0, '2004-01-26 09:18:38.536', 0, '2004-01-26 09:18:38.536', true);
INSERT INTO help_tableof_contents VALUES (88, 'Add a New Role', NULL, NULL, 87, 9, 4, 45, 0, '2004-01-26 09:18:38.566', 0, '2004-01-26 09:18:38.566', true);
INSERT INTO help_tableof_contents VALUES (89, 'Update Role ', NULL, NULL, 87, 9, 4, 50, 0, '2004-01-26 09:18:38.59', 0, '2004-01-26 09:18:38.59', true);
INSERT INTO help_tableof_contents VALUES (90, 'Configure Modules', NULL, NULL, 79, 9, 3, 55, 0, '2004-01-26 09:18:38.614', 0, '2004-01-26 09:18:38.614', true);
INSERT INTO help_tableof_contents VALUES (91, 'Configuration Options ', NULL, NULL, 90, 9, 4, 60, 0, '2004-01-26 09:18:38.638', 0, '2004-01-26 09:18:38.638', true);
INSERT INTO help_tableof_contents VALUES (92, 'Edit Lookup List ', NULL, NULL, 91, 9, 5, 65, 0, '2004-01-26 09:18:38.662', 0, '2004-01-26 09:18:38.662', true);
INSERT INTO help_tableof_contents VALUES (93, 'Adding a New Folder', NULL, NULL, 91, 9, 5, 70, 0, '2004-01-26 09:18:38.686', 0, '2004-01-26 09:18:38.686', true);
INSERT INTO help_tableof_contents VALUES (94, 'Modify Existing Folder', NULL, NULL, 91, 9, 5, 75, 0, '2004-01-26 09:18:38.716', 0, '2004-01-26 09:18:38.716', true);
INSERT INTO help_tableof_contents VALUES (95, 'Configure System', NULL, NULL, 79, 9, 3, 80, 0, '2004-01-26 09:18:38.746', 0, '2004-01-26 09:18:38.746', true);
INSERT INTO help_tableof_contents VALUES (96, 'Modify Timeout ', NULL, NULL, 95, 9, 4, 85, 0, '2004-01-26 09:18:38.776', 0, '2004-01-26 09:18:38.776', true);
INSERT INTO help_tableof_contents VALUES (97, 'Resource Usage Details', NULL, NULL, 79, 9, 3, 90, 0, '2004-01-26 09:18:38.806', 0, '2004-01-26 09:18:38.806', true);



INSERT INTO help_tableofcontentitem_links VALUES (1, 3, 1, 0, '2004-01-26 09:18:36.279', 0, '2004-01-26 09:18:36.279', true);
INSERT INTO help_tableofcontentitem_links VALUES (2, 4, 2, 0, '2004-01-26 09:18:36.322', 0, '2004-01-26 09:18:36.322', true);
INSERT INTO help_tableofcontentitem_links VALUES (3, 5, 3, 0, '2004-01-26 09:18:36.351', 0, '2004-01-26 09:18:36.351', true);
INSERT INTO help_tableofcontentitem_links VALUES (4, 6, 4, 0, '2004-01-26 09:18:36.375', 0, '2004-01-26 09:18:36.375', true);
INSERT INTO help_tableofcontentitem_links VALUES (5, 7, 5, 0, '2004-01-26 09:18:36.405', 0, '2004-01-26 09:18:36.405', true);
INSERT INTO help_tableofcontentitem_links VALUES (6, 8, 6, 0, '2004-01-26 09:18:36.435', 0, '2004-01-26 09:18:36.435', true);
INSERT INTO help_tableofcontentitem_links VALUES (7, 9, 7, 0, '2004-01-26 09:18:36.465', 0, '2004-01-26 09:18:36.465', true);
INSERT INTO help_tableofcontentitem_links VALUES (8, 10, 8, 0, '2004-01-26 09:18:36.495', 0, '2004-01-26 09:18:36.495', true);
INSERT INTO help_tableofcontentitem_links VALUES (9, 11, 9, 0, '2004-01-26 09:18:36.518', 0, '2004-01-26 09:18:36.518', true);
INSERT INTO help_tableofcontentitem_links VALUES (10, 12, 10, 0, '2004-01-26 09:18:36.542', 0, '2004-01-26 09:18:36.542', true);
INSERT INTO help_tableofcontentitem_links VALUES (11, 13, 11, 0, '2004-01-26 09:18:36.567', 0, '2004-01-26 09:18:36.567', true);
INSERT INTO help_tableofcontentitem_links VALUES (12, 14, 12, 0, '2004-01-26 09:18:36.602', 0, '2004-01-26 09:18:36.602', true);
INSERT INTO help_tableofcontentitem_links VALUES (13, 15, 13, 0, '2004-01-26 09:18:36.626', 0, '2004-01-26 09:18:36.626', true);
INSERT INTO help_tableofcontentitem_links VALUES (14, 16, 14, 0, '2004-01-26 09:18:36.656', 0, '2004-01-26 09:18:36.656', true);
INSERT INTO help_tableofcontentitem_links VALUES (15, 17, 15, 0, '2004-01-26 09:18:36.686', 0, '2004-01-26 09:18:36.686', true);
INSERT INTO help_tableofcontentitem_links VALUES (16, 18, 16, 0, '2004-01-26 09:18:36.715', 0, '2004-01-26 09:18:36.715', true);
INSERT INTO help_tableofcontentitem_links VALUES (17, 19, 17, 0, '2004-01-26 09:18:36.745', 0, '2004-01-26 09:18:36.745', true);
INSERT INTO help_tableofcontentitem_links VALUES (18, 20, 18, 0, '2004-01-26 09:18:36.769', 0, '2004-01-26 09:18:36.769', true);
INSERT INTO help_tableofcontentitem_links VALUES (19, 21, 19, 0, '2004-01-26 09:18:36.793', 0, '2004-01-26 09:18:36.793', true);
INSERT INTO help_tableofcontentitem_links VALUES (20, 22, 20, 0, '2004-01-26 09:18:36.817', 0, '2004-01-26 09:18:36.817', true);
INSERT INTO help_tableofcontentitem_links VALUES (21, 24, 34, 0, '2004-01-26 09:18:36.857', 0, '2004-01-26 09:18:36.857', true);
INSERT INTO help_tableofcontentitem_links VALUES (22, 25, 35, 0, '2004-01-26 09:18:36.883', 0, '2004-01-26 09:18:36.883', true);
INSERT INTO help_tableofcontentitem_links VALUES (23, 26, 36, 0, '2004-01-26 09:18:36.913', 0, '2004-01-26 09:18:36.913', true);
INSERT INTO help_tableofcontentitem_links VALUES (24, 27, 37, 0, '2004-01-26 09:18:36.943', 0, '2004-01-26 09:18:36.943', true);
INSERT INTO help_tableofcontentitem_links VALUES (25, 29, 78, 0, '2004-01-26 09:18:36.979', 0, '2004-01-26 09:18:36.979', true);
INSERT INTO help_tableofcontentitem_links VALUES (26, 30, 79, 0, '2004-01-26 09:18:37.015', 0, '2004-01-26 09:18:37.015', true);
INSERT INTO help_tableofcontentitem_links VALUES (27, 31, 80, 0, '2004-01-26 09:18:37.039', 0, '2004-01-26 09:18:37.039', true);
INSERT INTO help_tableofcontentitem_links VALUES (28, 32, 81, 0, '2004-01-26 09:18:37.063', 0, '2004-01-26 09:18:37.063', true);
INSERT INTO help_tableofcontentitem_links VALUES (29, 34, 111, 0, '2004-01-26 09:18:37.105', 0, '2004-01-26 09:18:37.105', true);
INSERT INTO help_tableofcontentitem_links VALUES (30, 35, 112, 0, '2004-01-26 09:18:37.135', 0, '2004-01-26 09:18:37.135', true);
INSERT INTO help_tableofcontentitem_links VALUES (31, 36, 113, 0, '2004-01-26 09:18:37.165', 0, '2004-01-26 09:18:37.165', true);
INSERT INTO help_tableofcontentitem_links VALUES (32, 37, 114, 0, '2004-01-26 09:18:37.195', 0, '2004-01-26 09:18:37.195', true);
INSERT INTO help_tableofcontentitem_links VALUES (33, 38, 115, 0, '2004-01-26 09:18:37.243', 0, '2004-01-26 09:18:37.243', true);
INSERT INTO help_tableofcontentitem_links VALUES (34, 39, 116, 0, '2004-01-26 09:18:37.267', 0, '2004-01-26 09:18:37.267', true);
INSERT INTO help_tableofcontentitem_links VALUES (35, 40, 117, 0, '2004-01-26 09:18:37.291', 0, '2004-01-26 09:18:37.291', true);
INSERT INTO help_tableofcontentitem_links VALUES (36, 41, 118, 0, '2004-01-26 09:18:37.314', 0, '2004-01-26 09:18:37.314', true);
INSERT INTO help_tableofcontentitem_links VALUES (37, 42, 119, 0, '2004-01-26 09:18:37.338', 0, '2004-01-26 09:18:37.338', true);
INSERT INTO help_tableofcontentitem_links VALUES (38, 43, 120, 0, '2004-01-26 09:18:37.362', 0, '2004-01-26 09:18:37.362', true);
INSERT INTO help_tableofcontentitem_links VALUES (39, 44, 121, 0, '2004-01-26 09:18:37.386', 0, '2004-01-26 09:18:37.386', true);
INSERT INTO help_tableofcontentitem_links VALUES (40, 45, 122, 0, '2004-01-26 09:18:37.417', 0, '2004-01-26 09:18:37.417', true);
INSERT INTO help_tableofcontentitem_links VALUES (41, 46, 123, 0, '2004-01-26 09:18:37.447', 0, '2004-01-26 09:18:37.447', true);
INSERT INTO help_tableofcontentitem_links VALUES (42, 47, 124, 0, '2004-01-26 09:18:37.477', 0, '2004-01-26 09:18:37.477', true);
INSERT INTO help_tableofcontentitem_links VALUES (43, 48, 125, 0, '2004-01-26 09:18:37.501', 0, '2004-01-26 09:18:37.501', true);
INSERT INTO help_tableofcontentitem_links VALUES (44, 49, 126, 0, '2004-01-26 09:18:37.525', 0, '2004-01-26 09:18:37.525', true);
INSERT INTO help_tableofcontentitem_links VALUES (45, 51, 175, 0, '2004-01-26 09:18:37.555', 0, '2004-01-26 09:18:37.555', true);
INSERT INTO help_tableofcontentitem_links VALUES (46, 52, 176, 0, '2004-01-26 09:18:37.591', 0, '2004-01-26 09:18:37.591', true);
INSERT INTO help_tableofcontentitem_links VALUES (47, 53, 177, 0, '2004-01-26 09:18:37.615', 0, '2004-01-26 09:18:37.615', true);
INSERT INTO help_tableofcontentitem_links VALUES (48, 54, 178, 0, '2004-01-26 09:18:37.639', 0, '2004-01-26 09:18:37.639', true);
INSERT INTO help_tableofcontentitem_links VALUES (49, 55, 179, 0, '2004-01-26 09:18:37.663', 0, '2004-01-26 09:18:37.663', true);
INSERT INTO help_tableofcontentitem_links VALUES (50, 56, 180, 0, '2004-01-26 09:18:37.704', 0, '2004-01-26 09:18:37.704', true);
INSERT INTO help_tableofcontentitem_links VALUES (51, 57, 181, 0, '2004-01-26 09:18:37.74', 0, '2004-01-26 09:18:37.74', true);
INSERT INTO help_tableofcontentitem_links VALUES (52, 58, 182, 0, '2004-01-26 09:18:37.805', 0, '2004-01-26 09:18:37.805', true);
INSERT INTO help_tableofcontentitem_links VALUES (53, 60, 225, 0, '2004-01-26 09:18:37.83', 0, '2004-01-26 09:18:37.83', true);
INSERT INTO help_tableofcontentitem_links VALUES (54, 61, 226, 0, '2004-01-26 09:18:37.866', 0, '2004-01-26 09:18:37.866', true);
INSERT INTO help_tableofcontentitem_links VALUES (55, 62, 227, 0, '2004-01-26 09:18:37.89', 0, '2004-01-26 09:18:37.89', true);
INSERT INTO help_tableofcontentitem_links VALUES (56, 63, 228, 0, '2004-01-26 09:18:37.914', 0, '2004-01-26 09:18:37.914', true);
INSERT INTO help_tableofcontentitem_links VALUES (57, 64, 229, 0, '2004-01-26 09:18:37.938', 0, '2004-01-26 09:18:37.938', true);
INSERT INTO help_tableofcontentitem_links VALUES (58, 65, 230, 0, '2004-01-26 09:18:37.968', 0, '2004-01-26 09:18:37.968', true);
INSERT INTO help_tableofcontentitem_links VALUES (59, 66, 231, 0, '2004-01-26 09:18:37.998', 0, '2004-01-26 09:18:37.998', true);
INSERT INTO help_tableofcontentitem_links VALUES (60, 67, 232, 0, '2004-01-26 09:18:38.027', 0, '2004-01-26 09:18:38.027', true);
INSERT INTO help_tableofcontentitem_links VALUES (61, 68, 233, 0, '2004-01-26 09:18:38.057', 0, '2004-01-26 09:18:38.057', true);
INSERT INTO help_tableofcontentitem_links VALUES (62, 69, 234, 0, '2004-01-26 09:18:38.081', 0, '2004-01-26 09:18:38.081', true);
INSERT INTO help_tableofcontentitem_links VALUES (63, 70, 235, 0, '2004-01-26 09:18:38.105', 0, '2004-01-26 09:18:38.105', true);
INSERT INTO help_tableofcontentitem_links VALUES (64, 72, 252, 0, '2004-01-26 09:18:38.148', 0, '2004-01-26 09:18:38.148', true);
INSERT INTO help_tableofcontentitem_links VALUES (65, 73, 253, 0, '2004-01-26 09:18:38.183', 0, '2004-01-26 09:18:38.183', true);
INSERT INTO help_tableofcontentitem_links VALUES (66, 74, 254, 0, '2004-01-26 09:18:38.207', 0, '2004-01-26 09:18:38.207', true);
INSERT INTO help_tableofcontentitem_links VALUES (67, 75, 255, 0, '2004-01-26 09:18:38.239', 0, '2004-01-26 09:18:38.239', true);
INSERT INTO help_tableofcontentitem_links VALUES (68, 77, 257, 0, '2004-01-26 09:18:38.273', 0, '2004-01-26 09:18:38.273', true);
INSERT INTO help_tableofcontentitem_links VALUES (69, 78, 258, 0, '2004-01-26 09:18:38.303', 0, '2004-01-26 09:18:38.303', true);
INSERT INTO help_tableofcontentitem_links VALUES (70, 80, 266, 0, '2004-01-26 09:18:38.345', 0, '2004-01-26 09:18:38.345', true);
INSERT INTO help_tableofcontentitem_links VALUES (71, 81, 267, 0, '2004-01-26 09:18:38.381', 0, '2004-01-26 09:18:38.381', true);
INSERT INTO help_tableofcontentitem_links VALUES (72, 82, 268, 0, '2004-01-26 09:18:38.405', 0, '2004-01-26 09:18:38.405', true);
INSERT INTO help_tableofcontentitem_links VALUES (73, 83, 269, 0, '2004-01-26 09:18:38.429', 0, '2004-01-26 09:18:38.429', true);
INSERT INTO help_tableofcontentitem_links VALUES (74, 84, 270, 0, '2004-01-26 09:18:38.459', 0, '2004-01-26 09:18:38.459', true);
INSERT INTO help_tableofcontentitem_links VALUES (75, 85, 271, 0, '2004-01-26 09:18:38.488', 0, '2004-01-26 09:18:38.488', true);
INSERT INTO help_tableofcontentitem_links VALUES (76, 86, 272, 0, '2004-01-26 09:18:38.53', 0, '2004-01-26 09:18:38.53', true);
INSERT INTO help_tableofcontentitem_links VALUES (77, 87, 273, 0, '2004-01-26 09:18:38.56', 0, '2004-01-26 09:18:38.56', true);
INSERT INTO help_tableofcontentitem_links VALUES (78, 88, 274, 0, '2004-01-26 09:18:38.584', 0, '2004-01-26 09:18:38.584', true);
INSERT INTO help_tableofcontentitem_links VALUES (79, 89, 275, 0, '2004-01-26 09:18:38.608', 0, '2004-01-26 09:18:38.608', true);
INSERT INTO help_tableofcontentitem_links VALUES (80, 90, 276, 0, '2004-01-26 09:18:38.632', 0, '2004-01-26 09:18:38.632', true);
INSERT INTO help_tableofcontentitem_links VALUES (81, 91, 277, 0, '2004-01-26 09:18:38.656', 0, '2004-01-26 09:18:38.656', true);
INSERT INTO help_tableofcontentitem_links VALUES (82, 92, 278, 0, '2004-01-26 09:18:38.68', 0, '2004-01-26 09:18:38.68', true);
INSERT INTO help_tableofcontentitem_links VALUES (83, 93, 279, 0, '2004-01-26 09:18:38.71', 0, '2004-01-26 09:18:38.71', true);
INSERT INTO help_tableofcontentitem_links VALUES (84, 94, 280, 0, '2004-01-26 09:18:38.74', 0, '2004-01-26 09:18:38.74', true);
INSERT INTO help_tableofcontentitem_links VALUES (85, 95, 281, 0, '2004-01-26 09:18:38.77', 0, '2004-01-26 09:18:38.77', true);
INSERT INTO help_tableofcontentitem_links VALUES (86, 96, 282, 0, '2004-01-26 09:18:38.80', 0, '2004-01-26 09:18:38.80', true);
INSERT INTO help_tableofcontentitem_links VALUES (87, 97, 283, 0, '2004-01-26 09:18:38.824', 0, '2004-01-26 09:18:38.824', true);






INSERT INTO help_features VALUES (1, 1, NULL, 'You can view the accounts that need attention', 0, '2004-01-26 09:18:23.561', 0, '2004-01-26 09:18:23.561', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (2, 1, NULL, 'You can make calls with the contact information readily accessible', 0, '2004-01-26 09:18:23.575', 0, '2004-01-26 09:18:23.575', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (3, 1, NULL, 'You can view the tasks assigned to you', 0, '2004-01-26 09:18:23.598', 0, '2004-01-26 09:18:23.598', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (4, 1, NULL, 'You can view the tickets assigned to you', 0, '2004-01-26 09:18:23.606', 0, '2004-01-26 09:18:23.606', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (5, 2, NULL, 'The select button can be used to view the details, reply, forward or delete a particular message.', 0, '2004-01-26 09:18:23.665', 0, '2004-01-26 09:18:23.665', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (6, 2, NULL, 'You can add a new message', 0, '2004-01-26 09:18:23.694', 0, '2004-01-26 09:18:23.694', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (7, 2, NULL, 'Clicking on the message will show the details of the message', 0, '2004-01-26 09:18:23.704', 0, '2004-01-26 09:18:23.704', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (8, 2, NULL, 'The drop down can be used to select the messages present in the inbox, sent messages, or archived ones', 0, '2004-01-26 09:18:23.729', 0, '2004-01-26 09:18:23.729', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (9, 2, NULL, 'Sort on one of the column headers by clicking on the column of your choice', 0, '2004-01-26 09:18:23.732', 0, '2004-01-26 09:18:23.732', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (10, 3, NULL, 'You can reply, archive, forward or delete each message by clicking the corresponding button', 0, '2004-01-26 09:18:23.761', 0, '2004-01-26 09:18:23.761', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (11, 4, NULL, 'A new message can be composed either to the contacts or the employees present in the recipients list. The options field can be checked to send a copy to the employees task list apart from sending the employee an email.', 0, '2004-01-26 09:18:23.784', 0, '2004-01-26 09:18:23.784', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (12, 5, NULL, 'You can send the email by clicking the send button', 0, '2004-01-26 09:18:23.809', 0, '2004-01-26 09:18:23.809', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (13, 5, NULL, 'You can add to the list of recipients by using the link "Add Recipients"', 0, '2004-01-26 09:18:23.826', 0, '2004-01-26 09:18:23.826', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (14, 5, NULL, 'You can click the check box to send an Internet email to the recipients', 0, '2004-01-26 09:18:23.832', 0, '2004-01-26 09:18:23.832', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (15, 5, NULL, 'Type directly in the Body test field to modify the message', 0, '2004-01-26 09:18:23.85', 0, '2004-01-26 09:18:23.85', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (16, 7, NULL, 'You can add the list of recipients by using the link "Add Recipients" and also click the check box which would send an email to the recipients ', 0, '2004-01-26 09:18:23.88', 0, '2004-01-26 09:18:23.88', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (17, 7, NULL, 'You can send the email by clicking the send button ', 0, '2004-01-26 09:18:23.898', 0, '2004-01-26 09:18:23.898', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (18, 7, NULL, 'You can edit the message by typing directly in the Body text area', 0, '2004-01-26 09:18:23.904', 0, '2004-01-26 09:18:23.904', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (19, 8, NULL, 'You can add a quick task. This task would have just the description and whether the task is personal or not', 0, '2004-01-26 09:18:23.941', 0, '2004-01-26 09:18:23.941', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (20, 8, NULL, 'For each of the existing tasks, you can view, modify, forward or delete the tasks by clicking on the Action button, and making a selection.', 0, '2004-01-26 09:18:23.952', 0, '2004-01-26 09:18:23.952', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (21, 8, NULL, 'You can select to view your tasks or tasks assigned by you to others working under you. Each can be viewed in three different modes. i.e. the completed tasks, uncompleted tasks or both.', 0, '2004-01-26 09:18:23.979', 0, '2004-01-26 09:18:23.979', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (22, 8, NULL, 'You can add a detailed (advanced) task, where you can set up the priority, status, whether the task is shared or not, task assignment, give the estimated time and add some detailed notes for it.', 0, '2004-01-26 09:18:23.982', 0, '2004-01-26 09:18:23.982', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (23, 9, NULL, 'Link this task to a contact and when you look at the task list, there will be a link to the contact record next to the task, allowing you to go directly to the contact.', 0, '2004-01-26 09:18:24.012', 0, '2004-01-26 09:18:24.012', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (24, 9, NULL, 'Filling in a Due Date will make ths task show up on that date in the Home Page calendar.', 0, '2004-01-26 09:18:24.029', 0, '2004-01-26 09:18:24.029', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (25, 9, NULL, 'Making the task personal will hide it from your hierarchy.', 0, '2004-01-26 09:18:24.036', 0, '2004-01-26 09:18:24.036', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (26, 9, NULL, 'You can assign a task to people lower than you in your hierarchy.', 0, '2004-01-26 09:18:24.053', 0, '2004-01-26 09:18:24.053', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (27, 9, NULL, 'Marking a task as complete will document the task as having been done, and immediately remove it from the Task List.', 0, '2004-01-26 09:18:24.06', 0, '2004-01-26 09:18:24.06', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (28, 10, NULL, 'Allows you to forward a task to one or more users of the system. Checking the options fields check box indicates that if the recipient is a user of the system, then a copy of the task is send to the recipient''s Internet email.', 0, '2004-01-26 09:18:24.088', 0, '2004-01-26 09:18:24.088', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (29, 10, NULL, 'The Subject line is mandatory', 0, '2004-01-26 09:18:24.103', 0, '2004-01-26 09:18:24.103', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (30, 10, NULL, 'You can add more text to the body of the message by typing directly in the Body text area', 0, '2004-01-26 09:18:24.126', 0, '2004-01-26 09:18:24.126', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (31, 11, NULL, 'Due dates will show on the Home Page calendar', 0, '2004-01-26 09:18:24.144', 0, '2004-01-26 09:18:24.144', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (32, 11, NULL, 'Completing a task will remove it from the task list', 0, '2004-01-26 09:18:24.15', 0, '2004-01-26 09:18:24.15', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (33, 11, NULL, 'You can assign a task to someone lower than you in your heirarchy', 0, '2004-01-26 09:18:24.167', 0, '2004-01-26 09:18:24.167', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (34, 11, NULL, 'You can Add or Change the contact that this task is linked to. When viewing the task, you will be able to view the contact information with one click.', 0, '2004-01-26 09:18:24.174', 0, '2004-01-26 09:18:24.174', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (35, 12, NULL, 'You can also view all the in progress Action Lists, completed lists, or both together.', 0, '2004-01-26 09:18:24.216', 0, '2004-01-26 09:18:24.216', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (36, 12, NULL, 'You can also keep track of the progress of your contacts. The number of them Completed and the Total are shown in the Progress Columns.', 0, '2004-01-26 09:18:24.227', 0, '2004-01-26 09:18:24.227', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (37, 12, NULL, 'You can add a new Action List with a description and status. You can select the contacts for this new Action List. For each of the contacts in the Action List, you can select a corresponding action with the Action Button: view details, modify contact, add contacts or delete the Action List.', 0, '2004-01-26 09:18:24.251', 0, '2004-01-26 09:18:24.251', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (38, 13, NULL, 'Clicking on the contact name will give you a pop up with more details about the contact and also about the related folders, calls, messages and opportunities.', 0, '2004-01-26 09:18:24.276', 0, '2004-01-26 09:18:24.276', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (39, 13, NULL, 'You can add contacts to the list and also Modify the List using "Add Contacts to list" and "Modify List" respectively.', 0, '2004-01-26 09:18:24.281', 0, '2004-01-26 09:18:24.281', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (40, 13, NULL, 'For the Action List you can also view all the in progress contacts, completed contacts or both.', 0, '2004-01-26 09:18:24.30', 0, '2004-01-26 09:18:24.30', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (41, 13, NULL, 'For each of the contacts you can add a call, opportunity, ticket, task or send a message, which would correspondingly appear in their respective tabs. For example, adding a ticket to the contact would be reflected in the Ticket tab.', 0, '2004-01-26 09:18:24.305', 0, '2004-01-26 09:18:24.305', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (42, 14, NULL, 'Select where the contacts will come from (General Contact, Account Contacts) in the From dropdown.', 0, '2004-01-26 09:18:24.33', 0, '2004-01-26 09:18:24.33', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (43, 14, NULL, 'Enter some search text, depending on the Operator you chose.', 0, '2004-01-26 09:18:24.347', 0, '2004-01-26 09:18:24.347', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (44, 14, NULL, 'Choose an Operator based on the Field you chose.', 0, '2004-01-26 09:18:24.353', 0, '2004-01-26 09:18:24.353', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (45, 14, NULL, 'Choose one of the many Field Names on which to base your query.', 0, '2004-01-26 09:18:24.371', 0, '2004-01-26 09:18:24.371', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (46, 14, NULL, 'You can Add or Remove contacts manually with the Add/Remove Contacts link.', 0, '2004-01-26 09:18:24.377', 0, '2004-01-26 09:18:24.377', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (47, 14, NULL, 'Add your query with the Add button at the bottom of the query frame. You can have multipe queries that make up the criteria for a group. You will get the result of all the queries.', 0, '2004-01-26 09:18:24.407', 0, '2004-01-26 09:18:24.407', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (48, 14, NULL, 'Save the Action List and generate the list of contacts by clicking the Save button at the bottom or top of the page.', 0, '2004-01-26 09:18:24.413', 0, '2004-01-26 09:18:24.413', NULL, NULL, true, 7);
INSERT INTO help_features VALUES (49, 15, NULL, 'You can check the status checkbox to indicate that the New Action List is complete.', 0, '2004-01-26 09:18:24.444', 0, '2004-01-26 09:18:24.444', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (50, 15, NULL, 'The details of the New Action List can be saved by clicking the save button.', 0, '2004-01-26 09:18:24.468', 0, '2004-01-26 09:18:24.468', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (51, 16, NULL, 'Click Update at the bottom of the page to save your reassignment, Cancel to quit the page without saving, and Reset to reset all the fields to their defaults and start over.', 0, '2004-01-26 09:18:24.512', 0, '2004-01-26 09:18:24.512', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (52, 16, NULL, 'Choose a User to reassign data from in the top dropdown. Only users below you in your hierarchy will be present here.', 0, '2004-01-26 09:18:24.522', 0, '2004-01-26 09:18:24.522', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (53, 16, NULL, 'Select one or more To Users in the To User column to reassign the various assets to. The number of each type of asset available to be reassigned is shown in parentheses after the asset in the first column.', 0, '2004-01-26 09:18:24.534', 0, '2004-01-26 09:18:24.534', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (54, 17, NULL, 'The location of the employee can be changed, i.e. the time zone can be changed by clicking on "Configure my location."', 0, '2004-01-26 09:18:24.571', 0, '2004-01-26 09:18:24.571', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (55, 17, NULL, 'You can update your personal information by clicking on "Update my personal information."', 0, '2004-01-26 09:18:24.582', 0, '2004-01-26 09:18:24.582', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (56, 17, NULL, 'You can change your password by clicking on "Change my password."', 0, '2004-01-26 09:18:24.588', 0, '2004-01-26 09:18:24.588', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (57, 18, NULL, 'Save your changes by clicking the Update button at the top or bottom of the page. The Cancel button forgets the changes and quits the page. The Reset button resets all fields to their original values so you can start over.', 0, '2004-01-26 09:18:24.615', 0, '2004-01-26 09:18:24.615', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (58, 18, NULL, 'The only required field is your last name, but you should fill in as much as you can to make the system as useful as possible. Email address is particularly useful.', 0, '2004-01-26 09:18:24.629', 0, '2004-01-26 09:18:24.629', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (59, 19, NULL, 'The location settings can be changed by selecting the time zone from the drop down list and clicking the update button to update the settings.', 0, '2004-01-26 09:18:24.66', 0, '2004-01-26 09:18:24.66', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (60, 20, NULL, 'You can update your password by clicking on the update button.', 0, '2004-01-26 09:18:24.685', 0, '2004-01-26 09:18:24.685', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (61, 21, NULL, 'For each of the existing tasks, you can view, modify, forward or delete the tasks by clicking on the Action button', 0, '2004-01-26 09:18:24.714', 0, '2004-01-26 09:18:24.714', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (62, 21, NULL, 'You can add a quick task. This task would have just the description and whether the task is personal or not', 0, '2004-01-26 09:18:24.72', 0, '2004-01-26 09:18:24.72', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (63, 21, NULL, 'You can add or update a detailed task called advanced task, wherein you can set up the priority, the status, whether the task is shared or not, also is the task assigned to self or someone working under the owner of the tasks, give the estimated time and add some detailed notes in it.', 0, '2004-01-26 09:18:24.754', 0, '2004-01-26 09:18:24.754', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (64, 21, NULL, 'Checking the existing task''s check box indicates that the particular task is completed.', 0, '2004-01-26 09:18:24.757', 0, '2004-01-26 09:18:24.757', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (65, 21, NULL, 'You can select to view your tasks or tasks assigned by you to others. Each task can be viewed in three different modes i.e. the completed tasks, uncompleted tasks or all the tasks.', 0, '2004-01-26 09:18:24.773', 0, '2004-01-26 09:18:24.773', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (66, 28, NULL, 'You can add the new Action List here. Along with description and the status you need to select the contacts you want in this Action List. You can populate the list in two ways. The first is to use the Add/Remove contacts.', 0, '2004-01-26 09:18:24.882', 0, '2004-01-26 09:18:24.882', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (67, 28, NULL, 'The second is to define the criteria to generate the list. Once it''s generated we can add them to the selected criteria and contacts by using the add feature present.', 0, '2004-01-26 09:18:24.901', 0, '2004-01-26 09:18:24.901', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (68, 31, NULL, 'Check new contacts to add them to your list, uncheck existing contacts to remove them from your list.', 0, '2004-01-26 09:18:24.947', 0, '2004-01-26 09:18:24.947', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (69, 31, NULL, 'Select All Contact, My Contacts or Account Contacts from the dropdown at the top.', 0, '2004-01-26 09:18:24.953', 0, '2004-01-26 09:18:24.953', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (70, 31, NULL, 'Finish by clicking Done at the bottom of the page.', 0, '2004-01-26 09:18:24.977', 0, '2004-01-26 09:18:24.977', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (71, 33, NULL, 'You can add or update a detailed task called advanced task, wherein you can set up a priority, status, whether the task is shared or not, also is the task assigned to you or someone working under the owner of the tasks, give the estimated time and add some detailed notes to it.', 0, '2004-01-26 09:18:25.013', 0, '2004-01-26 09:18:25.013', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (72, 34, NULL, 'You can select the contact category using the radio button and if the contact category is someone permanently associated with an account, then you can select the contact using the "select" next to it.', 0, '2004-01-26 09:18:25.067', 0, '2004-01-26 09:18:25.067', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (73, 34, NULL, 'You can save the details about the employee using the "Save" button.', 0, '2004-01-26 09:18:25.073', 0, '2004-01-26 09:18:25.073', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (74, 34, NULL, 'The "Save & New" button saves the details of the employee and also opens up a blank form start another contact.', 0, '2004-01-26 09:18:25.092', 0, '2004-01-26 09:18:25.092', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (75, 34, NULL, 'The only mandatory field is the Last Name, however, it is important to fill in as much as possible. These fields can be used for various types of queries later.', 0, '2004-01-26 09:18:25.097', 0, '2004-01-26 09:18:25.097', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (76, 34, NULL, 'The contact type can be selected using the "select" link next to the contact type.', 0, '2004-01-26 09:18:25.114', 0, '2004-01-26 09:18:25.114', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (77, 35, NULL, 'If the contact already exists in the system, you can search for that contact by name, company, title, contact type or source.', 0, '2004-01-26 09:18:25.139', 0, '2004-01-26 09:18:25.139', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (78, 36, NULL, 'New export data can be generated by choosing the "Generate new export" link at the top of the page', 0, '2004-01-26 09:18:25.163', 0, '2004-01-26 09:18:25.163', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (79, 36, NULL, 'Use the dropdown to choose which data to display: the list of all the exported data in the system or only your own.', 0, '2004-01-26 09:18:25.181', 0, '2004-01-26 09:18:25.181', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (80, 36, NULL, 'The exported data can be viewed in html format by clicking on the report name. The exported data can also be downloaded in CSV format or deleted by clicking the Select button in the action field.', 0, '2004-01-26 09:18:25.198', 0, '2004-01-26 09:18:25.198', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (81, 37, NULL, 'You can add all the fields or add / delete single fields from the report by using the buttons in the middle of the page. First highlight a field on the left to add or a field on the right to delete.', 0, '2004-01-26 09:18:25.223', 0, '2004-01-26 09:18:25.223', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (82, 37, NULL, 'Use the Up and Down buttons on the right to sort the fields.', 0, '2004-01-26 09:18:25.229', 0, '2004-01-26 09:18:25.229', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (83, 37, NULL, 'The Subject is mandatory. Select which set of contacts the export will come from with Criteria. Select the Primary sort with the Sorting dropdown.', 0, '2004-01-26 09:18:25.252', 0, '2004-01-26 09:18:25.252', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (84, 37, NULL, 'Click the Generate button when you are ready to generate the exported report.', 0, '2004-01-26 09:18:25.259', 0, '2004-01-26 09:18:25.259', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (85, 38, NULL, 'You can update, cancel or reset the details of the contact using the corresponding buttons.', 0, '2004-01-26 09:18:25.301', 0, '2004-01-26 09:18:25.301', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (86, 41, NULL, 'You can also click on the select button under the action field to view, modify, forward or delete a call.', 0, '2004-01-26 09:18:25.355', 0, '2004-01-26 09:18:25.355', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (87, 41, NULL, 'Clicking on the subject of the call will give complete details about the call.', 0, '2004-01-26 09:18:25.372', 0, '2004-01-26 09:18:25.372', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (88, 41, NULL, 'You can add a call associated with a contact.', 0, '2004-01-26 09:18:25.378', 0, '2004-01-26 09:18:25.378', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (89, 42, NULL, 'The save button lets you create a new call which is associated with the call.', 0, '2004-01-26 09:18:25.403', 0, '2004-01-26 09:18:25.403', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (90, 45, NULL, 'You can click the select button under the action column for viewing, modifying and deleting an opportunity.', 0, '2004-01-26 09:18:25.463', 0, '2004-01-26 09:18:25.463', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (91, 45, NULL, 'Clicking on the name of the opportunity will show a detailed description of the opportunity.', 0, '2004-01-26 09:18:25.486', 0, '2004-01-26 09:18:25.486', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (92, 45, NULL, 'Choosing the different types of opportunities from the drop down can filter the display.', 0, '2004-01-26 09:18:25.492', 0, '2004-01-26 09:18:25.492', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (93, 45, NULL, 'Add an opportunity associated with a contact.', 0, '2004-01-26 09:18:25.516', 0, '2004-01-26 09:18:25.516', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (94, 46, NULL, 'You can modify, delete or forward each call using the corresponding buttons.', 0, '2004-01-26 09:18:25.546', 0, '2004-01-26 09:18:25.546', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (95, 47, NULL, 'The component type can be selected using the "select" button.', 0, '2004-01-26 09:18:25.576', 0, '2004-01-26 09:18:25.576', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (96, 47, NULL, 'You can assign the component to any of the employees present using the dropdown list present.', 0, '2004-01-26 09:18:25.582', 0, '2004-01-26 09:18:25.582', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (97, 48, NULL, 'An opportunity can be renamed or deleted using the buttons present at the bottom of the page.', 0, '2004-01-26 09:18:25.607', 0, '2004-01-26 09:18:25.607', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (98, 48, NULL, 'Clicking on the select button lets you view, modify or delete the details about a component.', 0, '2004-01-26 09:18:25.624', 0, '2004-01-26 09:18:25.624', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (99, 48, NULL, 'Clicking on the name of the component shows the details about that component.', 0, '2004-01-26 09:18:25.63', 0, '2004-01-26 09:18:25.63', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (100, 48, NULL, 'Add a new component associated with the contact.', 0, '2004-01-26 09:18:25.648', 0, '2004-01-26 09:18:25.648', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (101, 49, NULL, 'You can modify or delete the opportunity using the modify or delete button.', 0, '2004-01-26 09:18:25.672', 0, '2004-01-26 09:18:25.672', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (102, 50, NULL, 'The type of the call can be selected using the drop down list and all the other details related to the call are updated using the update button.', 0, '2004-01-26 09:18:25.714', 0, '2004-01-26 09:18:25.714', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (103, 58, NULL, 'You can click the attachments or the surveys link present along with the message text.', 0, '2004-01-26 09:18:25.835', 0, '2004-01-26 09:18:25.835', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (104, 59, NULL, 'You can modify or delete the opportunity using the modify or the delete button.', 0, '2004-01-26 09:18:25.865', 0, '2004-01-26 09:18:25.865', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (105, 60, NULL, 'You can also click the select button under the action field to view, modify, clone or delete a contact.', 0, '2004-01-26 09:18:25.897', 0, '2004-01-26 09:18:25.897', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (106, 60, NULL, 'Clicking the name of the contact will display additional details about the contact.', 0, '2004-01-26 09:18:25.906', 0, '2004-01-26 09:18:25.906', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (107, 60, NULL, 'Add a contact using the link "Add a Contact" at the top of the page', 0, '2004-01-26 09:18:25.924', 0, '2004-01-26 09:18:25.924', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (108, 61, NULL, 'You can also choose to display the list of all the exported data in the system or the exported data created by you.', 0, '2004-01-26 09:18:25.948', 0, '2004-01-26 09:18:25.948', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (109, 61, NULL, 'The exported data can be viewed as a .csv file or in html format. The exported data can also be deleted when the select button in the action field is clicked.', 0, '2004-01-26 09:18:25.954', 0, '2004-01-26 09:18:25.954', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (110, 61, NULL, 'New export data can be generated, which lets you choose from the contacts list.', 0, '2004-01-26 09:18:25.971', 0, '2004-01-26 09:18:25.971', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (111, 62, NULL, 'You can modify, clone, or delete the details of the contact. ', 0, '2004-01-26 09:18:25.997', 0, '2004-01-26 09:18:25.997', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (112, 63, NULL, 'Clicking on the name of the message displays more details about the message.', 0, '2004-01-26 09:18:26.029', 0, '2004-01-26 09:18:26.029', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (113, 63, NULL, 'You can view the messages in two different views, i.e. all the messages present or the messages created/assigned by you.', 0, '2004-01-26 09:18:26.094', 0, '2004-01-26 09:18:26.094', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (114, 64, NULL, 'You can select the list of the recipients to whom you want to forward the particular call to by using the "Add Recipients" link.', 0, '2004-01-26 09:18:26.198', 0, '2004-01-26 09:18:26.198', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (115, 66, NULL, 'The component type can be selected using the "select" link.', 0, '2004-01-26 09:18:26.277', 0, '2004-01-26 09:18:26.277', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (116, 66, NULL, 'You can assign the component to any user using the dropdown list provided. ', 0, '2004-01-26 09:18:26.283', 0, '2004-01-26 09:18:26.283', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (117, 67, NULL, 'You can update or cancel the information changed using the "update" or "cancel" button.', 0, '2004-01-26 09:18:26.313', 0, '2004-01-26 09:18:26.313', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (118, 68, NULL, 'The folders can be selected using the drop down list.', 0, '2004-01-26 09:18:26.343', 0, '2004-01-26 09:18:26.343', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (119, 68, NULL, 'You can click on the record name, to view the folder record details. ', 0, '2004-01-26 09:18:26.361', 0, '2004-01-26 09:18:26.361', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (120, 68, NULL, 'You can view the details and modify them by clicking the select button under the action column.', 0, '2004-01-26 09:18:26.367', 0, '2004-01-26 09:18:26.367', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (121, 68, NULL, 'You can add a new record to a folder using the "Add a record to this folder" link.', 0, '2004-01-26 09:18:26.385', 0, '2004-01-26 09:18:26.385', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (122, 68, NULL, 'The folders can be selected using the drop down list.', 0, '2004-01-26 09:18:26.391', 0, '2004-01-26 09:18:26.391', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (123, 69, NULL, 'The changes made in the details of the folders can be updated or canceled using the "Update" or "Cancel" button.', 0, '2004-01-26 09:18:26.415', 0, '2004-01-26 09:18:26.415', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (124, 70, NULL, 'You can also click the select button under the action column for viewing, modifying and deleting an opportunity.', 0, '2004-01-26 09:18:26.449', 0, '2004-01-26 09:18:26.449', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (125, 70, NULL, 'Clicking on the name of the opportunity will display the details of the opportunity.', 0, '2004-01-26 09:18:26.457', 0, '2004-01-26 09:18:26.457', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (126, 70, NULL, 'Choosing the different types of opportunities from the drop down filters the display. ', 0, '2004-01-26 09:18:26.474', 0, '2004-01-26 09:18:26.474', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (127, 70, NULL, 'Add an opportunity associated with a contact.', 0, '2004-01-26 09:18:26.481', 0, '2004-01-26 09:18:26.481', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (128, 71, NULL, 'If the contact already exists in the system, you can search for that contact by name, company, title, contact type or source, by typing the search term in the appropriate field, and clicking the Search button.', 0, '2004-01-26 09:18:26.511', 0, '2004-01-26 09:18:26.511', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (129, 71, NULL, 'You can filter, export, and display data in different formats by clicking the Export link at the top of the page.', 0, '2004-01-26 09:18:26.552', 0, '2004-01-26 09:18:26.552', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (130, 71, NULL, 'Click Add to add a new contact into the system.', 0, '2004-01-26 09:18:26.561', 0, '2004-01-26 09:18:26.561', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (131, 72, NULL, 'You can filter the contact list in three different views. The views are all contacts, your contacts and Account contacts.', 0, '2004-01-26 09:18:26.589', 0, '2004-01-26 09:18:26.589', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (132, 72, NULL, 'Check any or all the contacts from the list you want to assign to your action List.', 0, '2004-01-26 09:18:26.607', 0, '2004-01-26 09:18:26.607', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (133, 74, NULL, 'You can also view, modify, clone or delete the contact by clicking the corresponding button.', 0, '2004-01-26 09:18:26.649', 0, '2004-01-26 09:18:26.649', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (134, 74, NULL, 'You can associate calls, messages and opportunities with each of the contacts already in the system.', 0, '2004-01-26 09:18:26.666', 0, '2004-01-26 09:18:26.666', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (135, 75, NULL, 'The contact type can be selected using the ��select�� link.', 0, '2004-01-26 09:18:26.693', 0, '2004-01-26 09:18:26.693', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (136, 75, NULL, 'The details can be updated using the update button.', 0, '2004-01-26 09:18:26.714', 0, '2004-01-26 09:18:26.714', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (137, 76, NULL, 'You can view all the messages related to the contact or only the messages owned by you. (My messages)', 0, '2004-01-26 09:18:26.745', 0, '2004-01-26 09:18:26.745', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (138, 77, NULL, 'This is for adding or updating a new detailed employee record into the system. The last name is the only mandatory field in creating an employee record, However it is important to add as much information as you can.', 0, '2004-01-26 09:18:26.775', 0, '2004-01-26 09:18:26.775', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (139, 78, NULL, 'You can view the progress chart in different views for all the employees working under the owner or creator of the opportunity. The views can be selected from the drop down box present under the chart. The mouse over or a click on the break point on the progress chart will give the date and exact value associated with that point.', 0, '2004-01-26 09:18:26.811', 0, '2004-01-26 09:18:26.811', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (140, 78, NULL, 'The opportunities created are also shown, with their names and the probable gross revenue associated with that opportunity. Clicking on the opportunities shows a details page for the opportunity.', 0, '2004-01-26 09:18:26.828', 0, '2004-01-26 09:18:26.828', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (141, 78, NULL, 'The list of employees reporting to a particular employee/supervisor is also shown below the progress chart. Clicking on an employee shows the Opportunity page from that person''s point of view. You can then work your way back up the chain by clicking the Up One Level link.', 0, '2004-01-26 09:18:26.834', 0, '2004-01-26 09:18:26.834', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (142, 79, NULL, 'The probability of Close, Estimated Close Date, Best Guess Estimate (what will the gross revenue be for this component?), and Estimated Term (over what time period?), are mandatory fields.', 0, '2004-01-26 09:18:26.859', 0, '2004-01-26 09:18:26.859', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (143, 79, NULL, 'You can assign the component to yourself or one of the users in your hierarchy.', 0, '2004-01-26 09:18:26.876', 0, '2004-01-26 09:18:26.876', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (144, 79, NULL, 'The Component Description is a mandatory field. Be descriptive as you will be using this to search on later.', 0, '2004-01-26 09:18:26.882', 0, '2004-01-26 09:18:26.882', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (145, 79, NULL, 'Use the Save button to save your changes and exit, Cancel to cancel your changes and exit, and Reset to cancel your changes and start over.', 0, '2004-01-26 09:18:26.90', 0, '2004-01-26 09:18:26.90', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (146, 79, NULL, 'Enter an Alert Description and Date to remind yourself to follow up on this component at a later date.', 0, '2004-01-26 09:18:26.906', 0, '2004-01-26 09:18:26.906', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (147, 79, NULL, 'You must associate the component with either a Contact or an Account. Choose one of the radio buttons, then one of the Select links.', 0, '2004-01-26 09:18:26.924', 0, '2004-01-26 09:18:26.924', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (148, 80, NULL, 'Existing opportunities can be searched using this feature. Opportunities can be searched on description, account name, or contact name with whom the opportunity is associated. It can also be searched by current progress / stage of the opportunity or the closing date range.', 0, '2004-01-26 09:18:26.948', 0, '2004-01-26 09:18:26.948', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (149, 81, NULL, 'The exported data can be viewed or downloaded as a .csv file or in html format. The exported data can also be deleted when the select button in the action column is clicked.', 0, '2004-01-26 09:18:26.973', 0, '2004-01-26 09:18:26.973', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (150, 81, NULL, 'You can also choose to display the list of all the exported data in the system or the exported data created by you.', 0, '2004-01-26 09:18:26.996', 0, '2004-01-26 09:18:26.996', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (151, 81, NULL, 'New export data can be generated by choosing from the contacts list. ', 0, '2004-01-26 09:18:27.002', 0, '2004-01-26 09:18:27.002', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (152, 84, NULL, 'Component Description is a mandatory field', 0, '2004-01-26 09:18:27.066', 0, '2004-01-26 09:18:27.066', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (153, 84, NULL, 'Use Update at the top or bottom to save your changes, Cancel to quit this page without saving, and Reset to reset all fields to default and start over.', 0, '2004-01-26 09:18:27.086', 0, '2004-01-26 09:18:27.086', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (154, 84, NULL, 'Add an Alert Description and Date to alert you via a CRM System Message to take a new action', 0, '2004-01-26 09:18:27.092', 0, '2004-01-26 09:18:27.092', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (155, 84, NULL, 'Probability of close, Estimated Close Date (when you will get the revenue), Best Guess Estimate (how much revenue you will get), and Estimated Term (what term the revenue will be realized over) are all the mandatory fields.', 0, '2004-01-26 09:18:27.11', 0, '2004-01-26 09:18:27.11', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (156, 84, NULL, 'You can select a Component Type from the dropdown. These component types are configurable by your System Administrator.', 0, '2004-01-26 09:18:27.116', 0, '2004-01-26 09:18:27.116', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (157, 84, NULL, 'You can assign the component to any User using the dropdown list present.', 0, '2004-01-26 09:18:27.134', 0, '2004-01-26 09:18:27.134', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (158, 85, NULL, 'The type of the call can be a phone, fax or in person. Some notes regarding the call can be noted. You can add an alert to remind you to follow up on this call.', 0, '2004-01-26 09:18:27.158', 0, '2004-01-26 09:18:27.158', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (159, 85, NULL, 'The Contact dropdown is automatically populated with the correct contacts for the company or account you are dealing with.', 0, '2004-01-26 09:18:27.164', 0, '2004-01-26 09:18:27.164', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (160, 86, NULL, 'You can update or cancel the information changed using the "update" or "cancel" button present.', 0, '2004-01-26 09:18:27.206', 0, '2004-01-26 09:18:27.206', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (161, 88, NULL, 'You can update the details of the documents using the update button.', 0, '2004-01-26 09:18:27.248', 0, '2004-01-26 09:18:27.248', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (162, 92, NULL, 'The component details are shown with additional options for modifying and deleting the component.', 0, '2004-01-26 09:18:27.314', 0, '2004-01-26 09:18:27.314', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (163, 93, NULL, 'The document can be uploaded using the upload button.', 0, '2004-01-26 09:18:27.344', 0, '2004-01-26 09:18:27.344', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (164, 93, NULL, 'The new version of the document can be selected from your local computer using the browse button.', 0, '2004-01-26 09:18:27.361', 0, '2004-01-26 09:18:27.361', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (165, 94, NULL, 'For each of the component you can view the details, modify the content or delete it completely using the select button in the Action column.', 0, '2004-01-26 09:18:27.386', 0, '2004-01-26 09:18:27.386', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (166, 94, NULL, 'You can add an opportunity here by giving complete details about the opportunity.', 0, '2004-01-26 09:18:27.392', 0, '2004-01-26 09:18:27.392', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (167, 94, NULL, 'The search results for existing opportunities are displayed here.', 0, '2004-01-26 09:18:27.409', 0, '2004-01-26 09:18:27.409', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (168, 94, NULL, 'There are different views of the opportunities you can choose from the drop down list and the corresponding types for the opportunities.', 0, '2004-01-26 09:18:27.419', 0, '2004-01-26 09:18:27.419', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (169, 95, NULL, 'In the Documents tab, documents associated with an opportunity can be added. This also displays the documents already linked with this opportunity and other details about the document. Details can be viewed, downloaded, modified or deleted by using the select button in the action column ', 0, '2004-01-26 09:18:27.44', 0, '2004-01-26 09:18:27.44', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (170, 95, NULL, 'In the Calls tab you can add a call associated with the opportunity. This also displays the calls already linked with this opportunity and other details about the call. The call details can be viewed, modified, forwarded or deleted by using the select button in the action column. ', 0, '2004-01-26 09:18:27.458', 0, '2004-01-26 09:18:27.458', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (171, 95, NULL, 'You can rename or delete the opportunity itself using the buttons below.', 0, '2004-01-26 09:18:27.475', 0, '2004-01-26 09:18:27.475', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (172, 95, NULL, 'You can modify, view and delete the details of any particular component by clicking the select button in the action column.', 0, '2004-01-26 09:18:27.493', 0, '2004-01-26 09:18:27.493', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (173, 95, NULL, 'In the Components tab, you can add a component. It also displays the status, amount and the date when the component will be closed. ', 0, '2004-01-26 09:18:27.499', 0, '2004-01-26 09:18:27.499', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (174, 95, NULL, 'There are three tabs in each opportunity i.e. components, calls and documents.', 0, '2004-01-26 09:18:27.523', 0, '2004-01-26 09:18:27.523', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (175, 95, NULL, 'You get the organization name or the contact name at the top, which on clicking will take you to the Account details. ', 0, '2004-01-26 09:18:27.529', 0, '2004-01-26 09:18:27.529', NULL, NULL, true, 7);
INSERT INTO help_features VALUES (176, 96, NULL, 'You can select the list of the recipients to whom you want to forward a call to by using the "Add Recipients" link. This will bring up a window with all users, from which you can then choose using check boxes.', 0, '2004-01-26 09:18:27.56', 0, '2004-01-26 09:18:27.56', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (177, 96, NULL, 'You can email a copy of the call to a user''s Internet email by checking the Email check box.', 0, '2004-01-26 09:18:27.577', 0, '2004-01-26 09:18:27.577', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (178, 96, NULL, 'You can add to the message by simply typing in the Body text box.', 0, '2004-01-26 09:18:27.583', 0, '2004-01-26 09:18:27.583', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (179, 97, NULL, 'The version of the particular document can be modified using the add version link.', 0, '2004-01-26 09:18:27.608', 0, '2004-01-26 09:18:27.608', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (180, 97, NULL, 'A document can be viewed, downloaded, modified or deleted by using the select button in the action column.', 0, '2004-01-26 09:18:27.625', 0, '2004-01-26 09:18:27.625', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (181, 97, NULL, 'A click on the subject of the document will show all the versions present.', 0, '2004-01-26 09:18:27.631', 0, '2004-01-26 09:18:27.631', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (182, 98, NULL, 'The type of the call can be selected using the drop down list and all the other details related to the call are updated using the update button', 0, '2004-01-26 09:18:27.662', 0, '2004-01-26 09:18:27.662', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (183, 99, NULL, 'In the Calls tab you can add a call to the opportunity. This also displays the calls already linked with this opportunity and other details about the call. The call details can be viewed, modified, forwarded or deleted by using the select button in the action column', 0, '2004-01-26 09:18:27.718', 0, '2004-01-26 09:18:27.718', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (184, 99, NULL, 'In the Components tab, you can add a component. It also displays the status, amount and the date when the component will be closed.', 0, '2004-01-26 09:18:27.733', 0, '2004-01-26 09:18:27.733', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (185, 99, NULL, 'There are three tabs in each opportunity i.e. components, calls and documents.', 0, '2004-01-26 09:18:27.739', 0, '2004-01-26 09:18:27.739', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (186, 99, NULL, 'You can rename or delete the whole opportunity (not just one of these components) using the buttons at the bottom.', 0, '2004-01-26 09:18:27.757', 0, '2004-01-26 09:18:27.757', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (187, 99, NULL, 'The organization or contact name appears on top, above the Components Tab, which when clicked, will take you to the Account details.', 0, '2004-01-26 09:18:27.763', 0, '2004-01-26 09:18:27.763', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (188, 99, NULL, 'You can modify, view and delete the details of any component by clicking the select button in the Action column, on the far left.', 0, '2004-01-26 09:18:27.787', 0, '2004-01-26 09:18:27.787', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (189, 99, NULL, 'In the Documents tab, documents associated with the particular opportunity can be added. This also displays the documents already linked with this opportunity and other details about the document. Details can be viewed, downloaded, modified or deleted by using the select button in the action column', 0, '2004-01-26 09:18:27.793', 0, '2004-01-26 09:18:27.793', NULL, NULL, true, 7);
INSERT INTO help_features VALUES (190, 100, NULL, 'In the Calls tab you can add a call associated with the opportunity. This also displays the calls already linked with this opportunity and other details about the call. The call details can be viewed, modified, forwarded or deleted by using the select button in the action column', 0, '2004-01-26 09:18:27.824', 0, '2004-01-26 09:18:27.824', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (191, 100, NULL, 'If the call subject is clicked then complete details about the call are displayed. ', 0, '2004-01-26 09:18:27.847', 0, '2004-01-26 09:18:27.847', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (192, 101, NULL, 'There are different views of the opportunities you can choose from the dropdown list and the corresponding types of the opportunities. ', 0, '2004-01-26 09:18:27.871', 0, '2004-01-26 09:18:27.871', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (193, 101, NULL, 'You can add an opportunity here by giving complete details about the opportunity.', 0, '2004-01-26 09:18:27.877', 0, '2004-01-26 09:18:27.877', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (194, 101, NULL, 'The search results for existing opportunities are displayed here.', 0, '2004-01-26 09:18:27.895', 0, '2004-01-26 09:18:27.895', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (195, 101, NULL, 'For each of the components you can view the details, modify the content or delete it completely using the select button in the Action column. You can click on any of the component names, which shows more details about the component, such as the calls and documents associated with it.', 0, '2004-01-26 09:18:27.901', 0, '2004-01-26 09:18:27.901', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (196, 102, NULL, 'The list of employees reporting to a particular employee/supervisor is also shown below the progress chart.', 0, '2004-01-26 09:18:27.933', 0, '2004-01-26 09:18:27.933', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (197, 102, NULL, 'Opportunities are displayed, with name and probable revemue. Clicking on the opportunities displays more details of the opportunity.', 0, '2004-01-26 09:18:27.954', 0, '2004-01-26 09:18:27.954', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (198, 102, NULL, 'You can view the progress chart in different views for all the employees working under the owner or creator of the opportunity. The views can be selected from the drop down box under the chart. The mouse over or a click on the break point on the progress chart will give the date and exact value associated with that point.', 0, '2004-01-26 09:18:27.961', 0, '2004-01-26 09:18:27.961', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (199, 103, NULL, 'The component details are shown with additional options for modifying and deleting the component.', 0, '2004-01-26 09:18:27.986', 0, '2004-01-26 09:18:27.986', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (200, 104, NULL, 'The component type can be selected using the "select" link.', 0, '2004-01-26 09:18:28.01', 0, '2004-01-26 09:18:28.01', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (201, 104, NULL, 'You can assign the component to any of the employee present using the dropdown list.', 0, '2004-01-26 09:18:28.033', 0, '2004-01-26 09:18:28.033', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (202, 105, NULL, 'In the Calls tab you can add a call associated with the opportunity. This also displays the calls already linked with this opportunity and other details about the call. Call details can be viewed, modified, forwarded or deleted by using the select button in the action column. ', 0, '2004-01-26 09:18:28.063', 0, '2004-01-26 09:18:28.063', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (203, 105, NULL, 'If the call subject is clicked then it will display complete details about the call.', 0, '2004-01-26 09:18:28.069', 0, '2004-01-26 09:18:28.069', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (204, 106, NULL, 'You can modify, delete and forward each of the calls.', 0, '2004-01-26 09:18:28.10', 0, '2004-01-26 09:18:28.10', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (205, 107, NULL, 'Clicking the Upload button will upload the selected document into the system.', 0, '2004-01-26 09:18:28.135', 0, '2004-01-26 09:18:28.135', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (206, 107, NULL, 'Clicking the Browse button opens a file browser on your own system. Simply navigate to the file on your drive that you want to upload and click Open. This will close the window and bring you back to the upload page.', 0, '2004-01-26 09:18:28.153', 0, '2004-01-26 09:18:28.153', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (207, 107, NULL, 'Add a very descriptive Subject for the file. This is a mandatory field.', 0, '2004-01-26 09:18:28.165', 0, '2004-01-26 09:18:28.165', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (208, 108, NULL, 'All the versions of the document can be downloaded from here. Simply select the version you want and click the Download link on the far left.', 0, '2004-01-26 09:18:28.189', 0, '2004-01-26 09:18:28.189', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (209, 109, NULL, 'The exported data can be viewed as a .csv file or in the html format. The exported data can also be deleted when the select button in the action field is clicked.', 0, '2004-01-26 09:18:28.213', 0, '2004-01-26 09:18:28.213', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (210, 109, NULL, 'You can also choose to display the list of all the exported data in the system or the exported data created by you.', 0, '2004-01-26 09:18:28.23', 0, '2004-01-26 09:18:28.23', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (211, 109, NULL, 'New export data can be generated, which lets you choose from the contacts list.', 0, '2004-01-26 09:18:28.236', 0, '2004-01-26 09:18:28.236', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (212, 109, NULL, 'Click on the subject of the new export, the data is displayed in html format', 0, '2004-01-26 09:18:28.254', 0, '2004-01-26 09:18:28.254', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (213, 110, NULL, 'Click the Generate button at top or bottom to generate the report from the fields you have included. Click cancel to quit and go back to the Export Data page.', 0, '2004-01-26 09:18:28.285', 0, '2004-01-26 09:18:28.285', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (214, 110, NULL, 'Highlight the fields you want to include in the left column and click the Add or All link. Highlight fields in the right column and click the Del link to remove them.', 0, '2004-01-26 09:18:28.291', 0, '2004-01-26 09:18:28.291', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (215, 110, NULL, 'Use the Sorting dropdown to sort the report by one of a variety of fields', 0, '2004-01-26 09:18:28.314', 0, '2004-01-26 09:18:28.314', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (216, 110, NULL, 'Use the Criteria dropdown to use opportunities from My or All Opportunities', 0, '2004-01-26 09:18:28.321', 0, '2004-01-26 09:18:28.321', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (217, 110, NULL, 'The Subject is a mandatory field.', 0, '2004-01-26 09:18:28.344', 0, '2004-01-26 09:18:28.344', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (218, 111, NULL, 'Clicking on the alert link will let you modify the details of the account owner.', 0, '2004-01-26 09:18:28.387', 0, '2004-01-26 09:18:28.387', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (219, 111, NULL, 'Accounts with contract end dates or other required actions will appear in the right hand window where you can take action on them.', 0, '2004-01-26 09:18:28.392', 0, '2004-01-26 09:18:28.392', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (220, 111, NULL, 'You can view the schedule, actions, alert dates and contract end dates for yourself or your employees by using the dropdown at the top of the page.', 0, '2004-01-26 09:18:28.41', 0, '2004-01-26 09:18:28.41', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (221, 111, NULL, 'You can modify the date range shown in the right hand window by clicking on a specific date on the calendar, or on one of the arrows to the left of each week on the calendar to give you a week''s view. Clicking on "Back To Next 7 Days View" at the top of the right window changes the view to the next seven days. The day or week you are currently viewing is highlighted in yellow. Today''s date is highlighted in blue. You can change the month and year using the dropdowns at the top of the calendar, and you can always return to today by using the Today link, also at the top of the calendar.', 0, '2004-01-26 09:18:28.416', 0, '2004-01-26 09:18:28.416', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (222, 112, NULL, 'Use the Insert button at top or bottom to save your changes, Cancel to quit without saving, and Reset to reset all the fields to their default values and start over.', 0, '2004-01-26 09:18:28.441', 0, '2004-01-26 09:18:28.441', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (223, 112, NULL, 'It''s a faily straightforward "fill in the blanks" exercise. There should be a "Primary" or "Business", or "Main" version of phone/fax numbers and addresses because other modules such as Communications Manager use these to perform other actions.', 0, '2004-01-26 09:18:28.458', 0, '2004-01-26 09:18:28.458', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (224, 112, NULL, 'Fill in as many fields as possible. Most of them can be used later as search terms and for queries in reports.', 0, '2004-01-26 09:18:28.464', 0, '2004-01-26 09:18:28.464', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (225, 112, NULL, 'Depending on whether you have chosen Organization or Individual, there are mandatory description fields to fill out about the account.', 0, '2004-01-26 09:18:28.482', 0, '2004-01-26 09:18:28.482', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (226, 112, NULL, 'Choose whether this account is an Organization or an Individual with the appropriate radio button.', 0, '2004-01-26 09:18:28.488', 0, '2004-01-26 09:18:28.488', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (227, 112, NULL, 'Clicking the Select link next to Account Type(s) will open a window with a variety of choices for Account Types. You cah choose and number by clicking the checkboxes to the left. It is important to use this feature as your choice(s) are used for searches and as the subject of querries in reports in other parts of the application.', 0, '2004-01-26 09:18:28.52', 0, '2004-01-26 09:18:28.52', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (228, 113, NULL, 'The account owner can also be changed using the drop down list', 0, '2004-01-26 09:18:28.548', 0, '2004-01-26 09:18:28.548', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (229, 113, NULL, 'The account type can be selected using the "Select" button', 0, '2004-01-26 09:18:28.554', 0, '2004-01-26 09:18:28.554', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (230, 113, NULL, 'This is for adding or updating account details. The last name or the organization name, based on the classification, is the only mandatory field in creating a new account. The type of account can be selected using the select option given next to the account type', 0, '2004-01-26 09:18:28.578', 0, '2004-01-26 09:18:28.578', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (231, 113, NULL, 'If the Account has a contract, you should enter a contract end date in the fields provided. This will generate an icon on the Home Page and an alert for the owner of the account that action must be taken at a prearranged time.', 0, '2004-01-26 09:18:28.584', 0, '2004-01-26 09:18:28.584', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (232, 114, NULL, 'You can also view, modify, clone and delete the contact by clicking the select button under the action column.', 0, '2004-01-26 09:18:28.626', 0, '2004-01-26 09:18:28.626', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (233, 114, NULL, 'When the name of the contact is clicked, it shows details of that contact, with the options to modify, clone and delete the contact details.', 0, '2004-01-26 09:18:28.65', 0, '2004-01-26 09:18:28.65', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (234, 114, NULL, 'You can add a contact, which is associated with the account.', 0, '2004-01-26 09:18:28.675', 0, '2004-01-26 09:18:28.675', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (235, 115, NULL, 'Using the select button in the action column you can view details and modify the record.', 0, '2004-01-26 09:18:28.704', 0, '2004-01-26 09:18:28.704', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (236, 115, NULL, 'You can click on the record type to view the folders details and modify them.', 0, '2004-01-26 09:18:28.712', 0, '2004-01-26 09:18:28.712', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (237, 115, NULL, 'A new record can be added to the folder.', 0, '2004-01-26 09:18:28.728', 0, '2004-01-26 09:18:28.728', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (238, 115, NULL, 'The folders can be populated by configuring the module in the admin tab.. The type of the folder can be changed using the drop down list shown.', 0, '2004-01-26 09:18:28.734', 0, '2004-01-26 09:18:28.734', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (239, 116, NULL, 'Opportunities associated with the contact, showing the best guess total and last modified date.', 0, '2004-01-26 09:18:28.758', 0, '2004-01-26 09:18:28.758', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (240, 116, NULL, 'You can add an opportunity.', 0, '2004-01-26 09:18:28.775', 0, '2004-01-26 09:18:28.775', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (241, 116, NULL, 'Three types of opportunities are present which can be selected from the drop down list.', 0, '2004-01-26 09:18:28.782', 0, '2004-01-26 09:18:28.782', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (242, 116, NULL, 'When the description of the opportunity is clicked, it will give you more details about the opportunity and the components present in it.', 0, '2004-01-26 09:18:28.799', 0, '2004-01-26 09:18:28.799', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (243, 117, NULL, 'By clicking on the description of the revenue you get the details about that revenue along with the options to modify and delete its details.', 0, '2004-01-26 09:18:28.83', 0, '2004-01-26 09:18:28.83', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (244, 117, NULL, 'You can view your revenue or all the revenues associated with the account using the drop down box.', 0, '2004-01-26 09:18:28.835', 0, '2004-01-26 09:18:28.835', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (245, 117, NULL, 'You can also view, modify and delete the details of the revenue by clicking the select button in the action column.', 0, '2004-01-26 09:18:28.859', 0, '2004-01-26 09:18:28.859', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (246, 117, NULL, 'Add / update a new revenue associated with the account.', 0, '2004-01-26 09:18:28.865', 0, '2004-01-26 09:18:28.865', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (247, 118, NULL, 'Clicking on the description of the revenue displays its details, along with options to modify and delete them.', 0, '2004-01-26 09:18:28.896', 0, '2004-01-26 09:18:28.896', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (248, 118, NULL, 'You can view your revenue or all the revenues associated with the account using the drop down box.', 0, '2004-01-26 09:18:28.913', 0, '2004-01-26 09:18:28.913', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (249, 118, NULL, 'You can also view, modify and delete the details of the revenue by clicking the select button in the action column.', 0, '2004-01-26 09:18:28.92', 0, '2004-01-26 09:18:28.92', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (250, 118, NULL, 'Add / update a new revenue associated with the account.', 0, '2004-01-26 09:18:28.937', 0, '2004-01-26 09:18:28.937', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (251, 119, NULL, 'Add new revenue to an account.', 0, '2004-01-26 09:18:28.962', 0, '2004-01-26 09:18:28.962', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (252, 120, NULL, 'Fill in the blanks and use "Update" to save your changes or "Reset" to return to the original values.', 0, '2004-01-26 09:18:28.986', 0, '2004-01-26 09:18:28.986', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (253, 121, NULL, 'You can also click the select button under the action column to view, modify or delete the ticket.', 0, '2004-01-26 09:18:29.01', 0, '2004-01-26 09:18:29.01', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (254, 121, NULL, 'Clicking on the ticket number will let you view the details, modify or delete the ticket.', 0, '2004-01-26 09:18:29.015', 0, '2004-01-26 09:18:29.015', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (255, 121, NULL, 'Add a new ticket.', 0, '2004-01-26 09:18:29.039', 0, '2004-01-26 09:18:29.039', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (256, 122, NULL, 'The details of the documents can be viewed or modified by clicking on the select button under the Action column. ', 0, '2004-01-26 09:18:29.069', 0, '2004-01-26 09:18:29.069', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (257, 122, NULL, 'Document versions can be updated by using the "add version" link. ', 0, '2004-01-26 09:18:29.075', 0, '2004-01-26 09:18:29.075', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (258, 122, NULL, 'A new document can be added which is associated with the account.', 0, '2004-01-26 09:18:29.099', 0, '2004-01-26 09:18:29.099', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (259, 122, NULL, 'You can view the details of, modify, download or delete the documents associated with the account.', 0, '2004-01-26 09:18:29.105', 0, '2004-01-26 09:18:29.105', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (260, 123, NULL, 'You can search for accounts in the system. The search can be based on the account name, phone number or the account type. Three types of accounts can be selected from the drop down list shown.', 0, '2004-01-26 09:18:29.136', 0, '2004-01-26 09:18:29.136', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (261, 124, NULL, 'Click Modify at the top or bottom of the page to modify these datails.', 0, '2004-01-26 09:18:29.16', 0, '2004-01-26 09:18:29.16', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (262, 125, NULL, 'The list of employees reporting to a particular employee/supervisor is also shown below the progress chart. ', 0, '2004-01-26 09:18:29.204', 0, '2004-01-26 09:18:29.204', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (263, 125, NULL, 'The Accounts present are also shown, with name and the amount of money associated with that Account. Clicking on the Account displays the details of the Account.', 0, '2004-01-26 09:18:29.213', 0, '2004-01-26 09:18:29.213', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (264, 125, NULL, 'You can view the progress chart in different views for all the employees working under the owner or creator of the Account. The views can be selected from the drop down box present under the chart. A mouse over or a click on the break point on the progress chart will give the date and exact value associated with that point. ', 0, '2004-01-26 09:18:29.237', 0, '2004-01-26 09:18:29.237', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (265, 126, NULL, 'The exported data can be viewed as a .csv file or in the html format. The exported data can also be deleted when the select button in the action field is clicked. ', 0, '2004-01-26 09:18:29.261', 0, '2004-01-26 09:18:29.261', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (266, 126, NULL, 'You can also choose to display the list of all the exported data in the system or the exported data created by you. ', 0, '2004-01-26 09:18:29.267', 0, '2004-01-26 09:18:29.267', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (267, 126, NULL, 'New export data can be generated using the "Generate new export" link.', 0, '2004-01-26 09:18:29.285', 0, '2004-01-26 09:18:29.285', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (268, 127, NULL, 'The details are updated by clicking the Update button.', 0, '2004-01-26 09:18:29.316', 0, '2004-01-26 09:18:29.316', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (269, 130, NULL, 'There are filters through which you can exactly select the data needed to generate the export data. Apart from selecting the type of accounts and the criteria, you can also select the fields required and then sort them.', 0, '2004-01-26 09:18:29.376', 0, '2004-01-26 09:18:29.376', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (270, 131, NULL, 'Using the select button under the action column you can view the details about the call, modify the call, forward the call or delete the call on the whole.', 0, '2004-01-26 09:18:29.405', 0, '2004-01-26 09:18:29.405', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (271, 131, NULL, 'Clicking on the subject of the call will show you the details about the call that was made to the contact.', 0, '2004-01-26 09:18:29.411', 0, '2004-01-26 09:18:29.411', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (272, 131, NULL, 'You can add a call associated with the contact using the "Add a call" link.', 0, '2004-01-26 09:18:29.429', 0, '2004-01-26 09:18:29.429', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (273, 132, NULL, 'Record details can be saved using the save button.', 0, '2004-01-26 09:18:29.453', 0, '2004-01-26 09:18:29.453', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (274, 133, NULL, 'The details of the new call can be saved using the save button.', 0, '2004-01-26 09:18:29.477', 0, '2004-01-26 09:18:29.477', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (275, 133, NULL, 'The call type can be selected from the dropdown box.', 0, '2004-01-26 09:18:29.483', 0, '2004-01-26 09:18:29.483', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (276, 134, NULL, 'You can browse your local system to select a new document to upload.', 0, '2004-01-26 09:18:29.508', 0, '2004-01-26 09:18:29.508', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (277, 137, NULL, 'You can upload a new version of an existing document.', 0, '2004-01-26 09:18:29.567', 0, '2004-01-26 09:18:29.567', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (278, 140, NULL, 'You can insert a new ticket, add the ticket source and also assign new contact.', 0, '2004-01-26 09:18:29.621', 0, '2004-01-26 09:18:29.621', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (279, 141, NULL, 'The details of the documents can be viewed or modified by clicking on the select button under the Action column ', 0, '2004-01-26 09:18:29.646', 0, '2004-01-26 09:18:29.646', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (280, 141, NULL, 'You can view the details, modify, download or delete the documents associated with the ticket', 0, '2004-01-26 09:18:29.651', 0, '2004-01-26 09:18:29.651', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (281, 141, NULL, 'A new document can be added which is associated with the ticket', 0, '2004-01-26 09:18:29.668', 0, '2004-01-26 09:18:29.668', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (282, 141, NULL, 'The document versions can be updated by using the "add version" link', 0, '2004-01-26 09:18:29.675', 0, '2004-01-26 09:18:29.675', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (283, 146, NULL, 'Clicking on the account name shows complete details about the account', 0, '2004-01-26 09:18:29.765', 0, '2004-01-26 09:18:29.765', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (284, 146, NULL, 'You can add a new account', 0, '2004-01-26 09:18:29.77', 0, '2004-01-26 09:18:29.77', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (285, 146, NULL, 'The select button in the Action column allows you to view, modify and archive the account. Archiving makes the account invisible, but it is still in the database.', 0, '2004-01-26 09:18:29.794', 0, '2004-01-26 09:18:29.794', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (286, 147, NULL, 'You can download all the versions of the documents', 0, '2004-01-26 09:18:29.824', 0, '2004-01-26 09:18:29.824', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (287, 148, NULL, 'You can modify / update the current document information, such as the subject and the filename', 0, '2004-01-26 09:18:30.202', 0, '2004-01-26 09:18:30.202', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (288, 149, NULL, 'The details of the account can be modified here. The details can be saved using the Modify button.', 0, '2004-01-26 09:18:30.237', 0, '2004-01-26 09:18:30.237', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (289, 150, NULL, 'You can modify, delete or forward the calls using the corresponding buttons.', 0, '2004-01-26 09:18:30.261', 0, '2004-01-26 09:18:30.261', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (290, 151, NULL, 'The details of the new call can be saved using the save button.', 0, '2004-01-26 09:18:30.285', 0, '2004-01-26 09:18:30.285', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (291, 151, NULL, 'The call type can be selected from the dropdown box.', 0, '2004-01-26 09:18:30.293', 0, '2004-01-26 09:18:30.293', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (292, 152, NULL, 'You can modify, delete or forward the calls using the corresponding buttons.', 0, '2004-01-26 09:18:30.316', 0, '2004-01-26 09:18:30.316', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (293, 153, NULL, 'You can select the list of the recipients to whom you want to forward the particular call to by using the "Add Recipients" link.', 0, '2004-01-26 09:18:30.35', 0, '2004-01-26 09:18:30.35', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (294, 154, NULL, 'You can also view, modify and delete the opportunity associated with the contact.', 0, '2004-01-26 09:18:30.381', 0, '2004-01-26 09:18:30.381', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (295, 154, NULL, 'When the description of the opportunity is clicked, it will display more details about the opportunity and its components.', 0, '2004-01-26 09:18:30.387', 0, '2004-01-26 09:18:30.387', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (296, 154, NULL, 'Add an opportunity.', 0, '2004-01-26 09:18:30.411', 0, '2004-01-26 09:18:30.411', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (297, 154, NULL, 'Select an opportunity type from the drop down list.', 0, '2004-01-26 09:18:30.417', 0, '2004-01-26 09:18:30.417', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (298, 155, NULL, 'You can rename or delete the opportunity itself using the buttons below.', 0, '2004-01-26 09:18:30.448', 0, '2004-01-26 09:18:30.448', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (299, 155, NULL, 'You can modify, view and delete the details of any particular component by clicking the select button in the action column. ', 0, '2004-01-26 09:18:30.471', 0, '2004-01-26 09:18:30.471', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (300, 155, NULL, 'You can add a new component associated with the account. It also displays the status, amount and the date when the component will be closed. ', 0, '2004-01-26 09:18:30.477', 0, '2004-01-26 09:18:30.477', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (301, 156, NULL, 'Lets you modify or delete the ticket information', 0, '2004-01-26 09:18:30.501', 0, '2004-01-26 09:18:30.501', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (302, 156, NULL, 'You can view the tasks and documents related to a ticket along with the history of that document by clicking on the corresponding links.', 0, '2004-01-26 09:18:30.519', 0, '2004-01-26 09:18:30.519', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (303, 157, NULL, 'You can also have tasks and documents related to a ticket along with the document history.', 0, '2004-01-26 09:18:30.543', 0, '2004-01-26 09:18:30.543', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (304, 157, NULL, 'Lets you modify / update the ticket information.', 0, '2004-01-26 09:18:30.549', 0, '2004-01-26 09:18:30.549', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (305, 158, NULL, 'The details of the task can be viewed or modified by clicking on the select button under the Action column. ', 0, '2004-01-26 09:18:30.575', 0, '2004-01-26 09:18:30.575', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (306, 158, NULL, 'You can update the task by clicking on the description of the task.', 0, '2004-01-26 09:18:30.591', 0, '2004-01-26 09:18:30.591', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (307, 158, NULL, 'You can add a task which is associated with the existing ticket.', 0, '2004-01-26 09:18:30.597', 0, '2004-01-26 09:18:30.597', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (308, 159, NULL, 'The document can be uploaded using the browse button.', 0, '2004-01-26 09:18:30.621', 0, '2004-01-26 09:18:30.621', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (309, 161, NULL, 'You can download all the different versions of the documents using the "Download" link in the Action column.', 0, '2004-01-26 09:18:30.654', 0, '2004-01-26 09:18:30.654', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (310, 162, NULL, 'The subject and the filename of the document can be modified.', 0, '2004-01-26 09:18:30.681', 0, '2004-01-26 09:18:30.681', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (311, 163, NULL, 'The subject and the file name can be changed. The version number is updated when an updated document is uploaded.', 0, '2004-01-26 09:18:30.711', 0, '2004-01-26 09:18:30.711', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (312, 164, NULL, 'The exported data can be viewed as a .csv file or in html format. The exported data can also be deleted when the select button in the action field is clicked. ', 0, '2004-01-26 09:18:30.735', 0, '2004-01-26 09:18:30.735', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (313, 164, NULL, 'You can also choose to display a list of all the exported data in the system or the exported data created by you.', 0, '2004-01-26 09:18:30.752', 0, '2004-01-26 09:18:30.752', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (314, 164, NULL, 'New export data can be generated using the "Generate new export" link.', 0, '2004-01-26 09:18:30.759', 0, '2004-01-26 09:18:30.759', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (315, 165, NULL, 'Revenue details along with the option to modify and delete revenue.', 0, '2004-01-26 09:18:30.783', 0, '2004-01-26 09:18:30.783', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (316, 167, NULL, 'You can add / update an opportunity here and assign it to an employee. The opportunity can be associated with an account or a contact. Each opportunity created requires the estimate or the probability of closing the deal, the duration and the best estimate of the person following up the lead.', 0, '2004-01-26 09:18:30.825', 0, '2004-01-26 09:18:30.825', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (317, 168, NULL, 'You can add / update an opportunity here and assign it to an employee. The opportunity can be associated with an account or a contact. Each opportunity created requires the estimate or the probability of closing the deal, the duration and the best estimate of the person following up the lead. ', 0, '2004-01-26 09:18:30.849', 0, '2004-01-26 09:18:30.849', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (318, 169, NULL, 'An opportunity can be renamed or deleted using the buttons present at the bottom of the page', 0, '2004-01-26 09:18:30.891', 0, '2004-01-26 09:18:30.891', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (319, 169, NULL, 'Clicking on the select button lets you view, modify or delete the details about the component ', 0, '2004-01-26 09:18:30.896', 0, '2004-01-26 09:18:30.896', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (320, 169, NULL, 'Clicking on the name of the component would show the details about the component ', 0, '2004-01-26 09:18:30.926', 0, '2004-01-26 09:18:30.926', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (321, 169, NULL, 'Add a new component which is associated with the account.', 0, '2004-01-26 09:18:30.932', 0, '2004-01-26 09:18:30.932', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (322, 170, NULL, 'The description of the opportunity can be changed using the update button.', 0, '2004-01-26 09:18:30.963', 0, '2004-01-26 09:18:30.963', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (323, 171, NULL, 'You can modify and delete the opportunity created using the modify and the delete buttons ', 0, '2004-01-26 09:18:30.987', 0, '2004-01-26 09:18:30.987', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (324, 172, NULL, 'The component type can be selected using the ��select �� link', 0, '2004-01-26 09:18:31.011', 0, '2004-01-26 09:18:31.011', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (325, 172, NULL, 'You can assign the component to any of the employee present using the dropdown list present. ', 0, '2004-01-26 09:18:31.028', 0, '2004-01-26 09:18:31.028', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (326, 173, NULL, 'The component type can be selected using the ��select �� link', 0, '2004-01-26 09:18:31.052', 0, '2004-01-26 09:18:31.052', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (327, 173, NULL, 'You can assign the component to any of the employee present using the dropdown list present. ', 0, '2004-01-26 09:18:31.058', 0, '2004-01-26 09:18:31.058', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (328, 173, NULL, 'Clicking the update button can save the changes made to the component', 0, '2004-01-26 09:18:31.076', 0, '2004-01-26 09:18:31.076', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (329, 174, NULL, 'You can modify and delete the opportunity created using the modify and the delete buttons ', 0, '2004-01-26 09:18:31.101', 0, '2004-01-26 09:18:31.101', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (330, 175, NULL, 'Clicking the select button under the action column gives you the option to view the details about the campaign, download the mail merge and also lets you to export it to Excel.', 0, '2004-01-26 09:18:31.154', 0, '2004-01-26 09:18:31.154', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (331, 175, NULL, 'Clicking on the campaign name gives you complete details about the campaign.', 0, '2004-01-26 09:18:31.19', 0, '2004-01-26 09:18:31.19', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (332, 175, NULL, 'You can display the campaigns created and their details using three different views by selecting from the drop down list.', 0, '2004-01-26 09:18:31.192', 0, '2004-01-26 09:18:31.192', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (333, 176, NULL, 'This creates a new Campaign. This takes in both the campaign and its description.', 0, '2004-01-26 09:18:31.244', 0, '2004-01-26 09:18:31.244', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (334, 177, NULL, 'You can view, modify and delete details by clicking the select button under the action column.', 0, '2004-01-26 09:18:31.274', 0, '2004-01-26 09:18:31.274', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (335, 177, NULL, 'For each of the campaign, the groups, message and delivery columns show whether they are complete or not. Clicking on these will help you choose the group, message and the delivery date.', 0, '2004-01-26 09:18:31.292', 0, '2004-01-26 09:18:31.292', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (336, 177, NULL, 'Clicking the name of the campaign shows you more details about the campaign and also shows the list of the things to be selected before a campaign can be activated', 0, '2004-01-26 09:18:31.298', 0, '2004-01-26 09:18:31.298', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (337, 177, NULL, 'You can view your incomplete campaigns or all the incomplete campaigns. You can select the view with the drop down list at the top.', 0, '2004-01-26 09:18:31.316', 0, '2004-01-26 09:18:31.316', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (338, 177, NULL, 'Add a campaign ', 0, '2004-01-26 09:18:31.322', 0, '2004-01-26 09:18:31.322', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (339, 178, NULL, 'You can also click the select button under the Action column for viewing, modifying or deleting the details.', 0, '2004-01-26 09:18:31.346', 0, '2004-01-26 09:18:31.346', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (340, 178, NULL, 'Clicking the group name will show the list of contacts present in the group.', 0, '2004-01-26 09:18:31.364', 0, '2004-01-26 09:18:31.364', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (341, 178, NULL, 'Add a contact group using the link "Add a Contact Group".', 0, '2004-01-26 09:18:31.37', 0, '2004-01-26 09:18:31.37', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (342, 178, NULL, 'You can filter the list of groups displayed by selecting from the drop down.', 0, '2004-01-26 09:18:31.388', 0, '2004-01-26 09:18:31.388', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (343, 179, NULL, 'You can preview the details of the group by clicking on the preview button.', 0, '2004-01-26 09:18:31.418', 0, '2004-01-26 09:18:31.418', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (344, 179, NULL, 'You can also select from the list of "Selected criteria and contacts" and remove them by clicking the remove button.', 0, '2004-01-26 09:18:31.424', 0, '2004-01-26 09:18:31.424', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (345, 179, NULL, 'You can define the criteria to generate the list by using the different filters present and then add them to the "Selected criteria and contacts".', 0, '2004-01-26 09:18:31.447', 0, '2004-01-26 09:18:31.447', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (346, 179, NULL, 'You can select the criteria for the group to be created. Clicking the "Add/Remove Contacts" can choose the specific contacts.', 0, '2004-01-26 09:18:31.454', 0, '2004-01-26 09:18:31.454', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (347, 180, NULL, 'You can view, modify, clone or delete each of the messages.', 0, '2004-01-26 09:18:31.484', 0, '2004-01-26 09:18:31.484', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (348, 180, NULL, 'The dropdown list acts as filters for displaying the messages that meet certain criteria.', 0, '2004-01-26 09:18:31.501', 0, '2004-01-26 09:18:31.501', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (349, 180, NULL, 'Clicking on the message name will show details about the message, which can be updated.', 0, '2004-01-26 09:18:31.508', 0, '2004-01-26 09:18:31.508', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (350, 180, NULL, 'Add a new message', 0, '2004-01-26 09:18:31.531', 0, '2004-01-26 09:18:31.531', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (351, 181, NULL, 'The new message can be saved by clicking the save message button.', 0, '2004-01-26 09:18:31.55', 0, '2004-01-26 09:18:31.55', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (352, 181, NULL, 'The permissions or the access type for the message can be chosen from drop down box.', 0, '2004-01-26 09:18:31.555', 0, '2004-01-26 09:18:31.555', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (353, 182, NULL, 'Clicking on the "surveys" will let you create new interactive surveys.', 0, '2004-01-26 09:18:31.58', 0, '2004-01-26 09:18:31.58', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (354, 183, NULL, 'You can use the preview button to view the details about the contacts in a group.', 0, '2004-01-26 09:18:31.60', 0, '2004-01-26 09:18:31.60', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (355, 183, NULL, 'You can modify or delete a group.', 0, '2004-01-26 09:18:31.604', 0, '2004-01-26 09:18:31.604', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (356, 184, NULL, 'You can change the version of the document when ever an updated document is uploaded.', 0, '2004-01-26 09:18:31.64', 0, '2004-01-26 09:18:31.64', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (357, 188, NULL, 'You can browse to select a new document to upload if its related to the campaign.', 0, '2004-01-26 09:18:31.726', 0, '2004-01-26 09:18:31.726', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (358, 190, NULL, 'You can also go back from the current detailed view to the group details criteria.', 0, '2004-01-26 09:18:31.754', 0, '2004-01-26 09:18:31.754', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (359, 191, NULL, 'You can download from the list of documents available by using the "download" link under the action column.', 0, '2004-01-26 09:18:31.778', 0, '2004-01-26 09:18:31.778', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (360, 193, NULL, 'The name of the survey is a mandatory field for creating a survey. A description, introduction and thank-you note can also be added.', 0, '2004-01-26 09:18:31.819', 0, '2004-01-26 09:18:31.819', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (361, 195, NULL, 'You can download the mail merge shown at the bottom of the details.', 0, '2004-01-26 09:18:31.85', 0, '2004-01-26 09:18:31.85', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (362, 196, NULL, 'Different versions of the document can be downloaded using the "download" link in the action column.', 0, '2004-01-26 09:18:31.874', 0, '2004-01-26 09:18:31.874', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (363, 201, NULL, 'You can update the campaign schedule by filling in the run date and the delivery method whether it''s an email, fax or letter or any other method.', 0, '2004-01-26 09:18:31.963', 0, '2004-01-26 09:18:31.963', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (364, 202, NULL, 'You can also generate a list of contacts be selecting from the filters and adding them to the "selected criteria and contacts" list.', 0, '2004-01-26 09:18:31.994', 0, '2004-01-26 09:18:31.994', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (365, 202, NULL, 'You can choose the contacts in the group using the "Add / Remove contacts" link present.', 0, '2004-01-26 09:18:32.017', 0, '2004-01-26 09:18:32.017', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (366, 202, NULL, 'You can update the name of the group ', 0, '2004-01-26 09:18:32.023', 0, '2004-01-26 09:18:32.023', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (367, 204, NULL, 'There can be multiple attachments to a single message. The attachment that needs to be downloaded has to be selected first and then downloaded.', 0, '2004-01-26 09:18:32.075', 0, '2004-01-26 09:18:32.075', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (368, 208, NULL, 'The details of the documents can be viewed or modified by clicking on the select button under the Action column. ', 0, '2004-01-26 09:18:32.12', 0, '2004-01-26 09:18:32.12', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (369, 208, NULL, 'You can view the details, modify, download or delete the documents associated with the account.', 0, '2004-01-26 09:18:32.143', 0, '2004-01-26 09:18:32.143', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (370, 208, NULL, 'A new document can be added to the account. ', 0, '2004-01-26 09:18:32.149', 0, '2004-01-26 09:18:32.149', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (371, 208, NULL, 'The document versions can be updated by using the "add version" link. ', 0, '2004-01-26 09:18:32.173', 0, '2004-01-26 09:18:32.173', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (372, 209, NULL, 'The name of the campaign can be changed or deleted by using the buttons at the bottom of the page.', 0, '2004-01-26 09:18:32.204', 0, '2004-01-26 09:18:32.204', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (373, 209, NULL, 'You can choose a group / groups, a message for the campaign, and a delivery date for the campaign to start. You can also add attachments to the messages you send to recipients.', 0, '2004-01-26 09:18:32.215', 0, '2004-01-26 09:18:32.215', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (374, 210, NULL, 'You can check the groups you want for the current campaign.', 0, '2004-01-26 09:18:32.24', 0, '2004-01-26 09:18:32.24', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (375, 210, NULL, 'You can also add attachments to the messages you send to recipients by clicking the preview recipient''s link next to each group.', 0, '2004-01-26 09:18:32.257', 0, '2004-01-26 09:18:32.257', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (376, 210, NULL, 'You can view all the groups present or the groups created by you just by choosing from the drop down box.', 0, '2004-01-26 09:18:32.274', 0, '2004-01-26 09:18:32.274', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (377, 211, NULL, 'You can select a message for this campaign from the dropdown list of all the messages or just your messages.', 0, '2004-01-26 09:18:32.299', 0, '2004-01-26 09:18:32.299', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (378, 211, NULL, 'The messages can be of multiple types, which can be used as filters and can be selected from the drop down list. For each type you have further classification.', 0, '2004-01-26 09:18:32.305', 0, '2004-01-26 09:18:32.305', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (379, 212, NULL, 'The attachments configured are the surveys or the file attachments. Using the links "change survey" and "change file attachments", you can change either of them.', 0, '2004-01-26 09:18:32.33', 0, '2004-01-26 09:18:32.33', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (380, 213, NULL, 'You can view and select from all, or only your own surveys.', 0, '2004-01-26 09:18:32.36', 0, '2004-01-26 09:18:32.36', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (381, 214, NULL, 'You can download or remove the file name. You can also upload files using the browse button.', 0, '2004-01-26 09:18:32.389', 0, '2004-01-26 09:18:32.389', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (382, 215, NULL, 'The name and the description of the campaign can be changed.', 0, '2004-01-26 09:18:32.419', 0, '2004-01-26 09:18:32.419', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (383, 216, NULL, 'You can modify, delete or clone the message details by clicking on corresponding buttons.', 0, '2004-01-26 09:18:32.449', 0, '2004-01-26 09:18:32.449', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (384, 217, NULL, 'You can select font properties for the text of the message along with the size and indentation.', 0, '2004-01-26 09:18:32.479', 0, '2004-01-26 09:18:32.479', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (385, 217, NULL, 'The name of the message and the access type can be given, which specifies who can view the message.', 0, '2004-01-26 09:18:32.496', 0, '2004-01-26 09:18:32.496', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (386, 218, NULL, 'You can modify, delete or clone the message details by clicking on corresponding buttons.', 0, '2004-01-26 09:18:32.521', 0, '2004-01-26 09:18:32.521', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (387, 219, NULL, 'You can select font properties for the text of the message along with the size and indentation. ', 0, '2004-01-26 09:18:32.545', 0, '2004-01-26 09:18:32.545', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (388, 219, NULL, 'The name of the message and the access type can be given, which specifies who can view the message.', 0, '2004-01-26 09:18:32.562', 0, '2004-01-26 09:18:32.562', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (389, 220, NULL, 'You can also view, modify and delete the details of a survey. ', 0, '2004-01-26 09:18:32.587', 0, '2004-01-26 09:18:32.587', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (390, 220, NULL, 'Clicking on the name of the survey shows its details.', 0, '2004-01-26 09:18:32.592', 0, '2004-01-26 09:18:32.592', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (391, 220, NULL, 'Add a new survey', 0, '2004-01-26 09:18:32.616', 0, '2004-01-26 09:18:32.616', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (392, 220, NULL, 'You can view all or your own surveys using the drop down list.', 0, '2004-01-26 09:18:32.622', 0, '2004-01-26 09:18:32.622', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (393, 221, NULL, 'The "Save & Add" button saves the current question and lets you add another one immediately.', 0, '2004-01-26 09:18:32.653', 0, '2004-01-26 09:18:32.653', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (394, 221, NULL, 'You can also specify whether the particular question is required or not by checking the checkbox.', 0, '2004-01-26 09:18:32.681', 0, '2004-01-26 09:18:32.681', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (395, 221, NULL, 'If the selected question type is "Item List", then an Edit button is enabled which helps in adding new elements to the existing list.', 0, '2004-01-26 09:18:32.685', 0, '2004-01-26 09:18:32.685', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (396, 221, NULL, 'A new question type can be selected through the drop down list.', 0, '2004-01-26 09:18:32.707', 0, '2004-01-26 09:18:32.707', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (397, 222, NULL, 'The preview button shows you the survey questions in a pop-up window.', 0, '2004-01-26 09:18:32.737', 0, '2004-01-26 09:18:32.737', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (398, 222, NULL, 'You can modify, delete, and preview the survey details using the buttons at the top of the page.', 0, '2004-01-26 09:18:32.743', 0, '2004-01-26 09:18:32.743', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (399, 222, NULL, 'You can view the survey introduction text, the questions and the thank-you text. ', 0, '2004-01-26 09:18:32.767', 0, '2004-01-26 09:18:32.767', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (400, 223, NULL, 'You can add questions to the survey here.', 0, '2004-01-26 09:18:32.797', 0, '2004-01-26 09:18:32.797', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (401, 223, NULL, 'Clicking the "Done" button can save the survey and you can also traverse back by clicking the "Back" button.', 0, '2004-01-26 09:18:32.82', 0, '2004-01-26 09:18:32.82', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (402, 223, NULL, 'The survey questions can be moved up or down using the "Up" or "Down" links present in the action field.', 0, '2004-01-26 09:18:32.829', 0, '2004-01-26 09:18:32.829', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (403, 223, NULL, 'You can edit or delete any of the survey questions using the "edit" or "del" link under the action field.', 0, '2004-01-26 09:18:32.838', 0, '2004-01-26 09:18:32.838', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (404, 223, NULL, 'You can add new survey questions here. ', 0, '2004-01-26 09:18:32.844', 0, '2004-01-26 09:18:32.844', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (405, 224, NULL, 'You can click on "Create Attachments" and include interactive items, like surveys, or provide additional materials like files.', 0, '2004-01-26 09:18:32.875', 0, '2004-01-26 09:18:32.875', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (406, 224, NULL, 'Clicking the "Create Message"  link lets you compose a message to reach your audience.', 0, '2004-01-26 09:18:32.898', 0, '2004-01-26 09:18:32.898', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (407, 224, NULL, 'You can click the "Build Groups" link to assemble dynamic distribution of groups. Each campaign needs at least one group to send a message to.', 0, '2004-01-26 09:18:32.904', 0, '2004-01-26 09:18:32.904', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (408, 224, NULL, 'The "Campaign Builder" can be clicked to select groups of contacts that you would like to send a message to, schedule a delivery date, etc.', 0, '2004-01-26 09:18:32.928', 0, '2004-01-26 09:18:32.928', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (409, 224, NULL, 'You can click on the "Dashboard" to view the sent messages and to drill down and view recipients and survey results.', 0, '2004-01-26 09:18:32.934', 0, '2004-01-26 09:18:32.934', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (410, 225, NULL, 'Lets you modify or delete ticket information ', 0, '2004-01-26 09:18:32.989', 0, '2004-01-26 09:18:32.989', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (411, 225, NULL, 'You can also store tasks and documents related to a ticket.', 0, '2004-01-26 09:18:33.012', 0, '2004-01-26 09:18:33.012', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (412, 226, NULL, 'For each new ticket you can select the organization, the contact and also the issue for which the ticket is being created. The assignment and the resolution of the ticket can also be entered.', 0, '2004-01-26 09:18:33.043', 0, '2004-01-26 09:18:33.043', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (413, 227, NULL, 'The search can be done based on different parameters like the ticket number, account associated, priority, employee whom the ticket is assigned etc.', 0, '2004-01-26 09:18:33.066', 0, '2004-01-26 09:18:33.066', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (414, 228, NULL, 'Clicking on the subject of the exported data shows you the details of the ticket like the ticket ID, the organization and its issue (why the particular ticket was generated).', 0, '2004-01-26 09:18:33.09', 0, '2004-01-26 09:18:33.09', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (415, 228, NULL, 'Clicking on the select button under the action column lets you view the data, download the data in .CSV format or delete the data.', 0, '2004-01-26 09:18:33.096', 0, '2004-01-26 09:18:33.096', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (416, 228, NULL, 'You can filter the exported date generated, by you or by all employees using the dropdown list.', 0, '2004-01-26 09:18:33.114', 0, '2004-01-26 09:18:33.114', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (417, 228, NULL, 'You can generate a new exported data by clicking the link "Generate new export".', 0, '2004-01-26 09:18:33.12', 0, '2004-01-26 09:18:33.12', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (418, 229, NULL, 'You can save the details of the modified ticket by clicking the "Update" button.', 0, '2004-01-26 09:18:33.145', 0, '2004-01-26 09:18:33.145', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (419, 230, NULL, 'You can save the details of the modified ticket by clicking the "Update" button.', 0, '2004-01-26 09:18:33.17', 0, '2004-01-26 09:18:33.17', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (420, 231, NULL, 'The details of the task can be viewed or modified by clicking on the select button under the Action column. ', 0, '2004-01-26 09:18:33.216', 0, '2004-01-26 09:18:33.216', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (421, 231, NULL, 'You can update the task by clicking on the description of the task. ', 0, '2004-01-26 09:18:33.24', 0, '2004-01-26 09:18:33.24', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (422, 231, NULL, 'You can add a task which is associated with the existing ticket. ', 0, '2004-01-26 09:18:33.246', 0, '2004-01-26 09:18:33.246', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (423, 232, NULL, 'The details of the documents can be viewed or modified by clicking on the select button under the Action column. ', 0, '2004-01-26 09:18:33.276', 0, '2004-01-26 09:18:33.276', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (424, 232, NULL, 'You can view the details, modify, download or delete the documents associated with the ticket. ', 0, '2004-01-26 09:18:33.299', 0, '2004-01-26 09:18:33.299', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (425, 232, NULL, 'A new document associated with the ticket can be added. ', 0, '2004-01-26 09:18:33.306', 0, '2004-01-26 09:18:33.306', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (426, 232, NULL, 'The document versions can be updated by using the "add version" link. ', 0, '2004-01-26 09:18:33.324', 0, '2004-01-26 09:18:33.324', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (427, 233, NULL, 'A new record is added into the folder using the link "Add a record to this folder". Multiple records can be added to this folder if the folder has the necessary settings.', 0, '2004-01-26 09:18:33.354', 0, '2004-01-26 09:18:33.354', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (428, 233, NULL, 'You can select the custom folder using the drop down list. ', 0, '2004-01-26 09:18:33.369', 0, '2004-01-26 09:18:33.369', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (429, 234, NULL, 'The details are saved by clicking the save button.', 0, '2004-01-26 09:18:33.396', 0, '2004-01-26 09:18:33.396', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (430, 235, NULL, 'A chronological history of all actions associated with a ticket is maintined.', 0, '2004-01-26 09:18:33.42', 0, '2004-01-26 09:18:33.42', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (431, 243, NULL, 'The changes can be saved using the "Update" button.', 0, '2004-01-26 09:18:33.54', 0, '2004-01-26 09:18:33.54', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (432, 244, NULL, 'You can modify the folder information along with the record details by clicking on the Modify button.', 0, '2004-01-26 09:18:33.564', 0, '2004-01-26 09:18:33.564', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (433, 247, NULL, 'The document can be uploaded using the browse button.', 0, '2004-01-26 09:18:33.627', 0, '2004-01-26 09:18:33.627', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (434, 248, NULL, 'You can download all the versions of a document.', 0, '2004-01-26 09:18:33.659', 0, '2004-01-26 09:18:33.659', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (435, 249, NULL, 'You can also have tasks and documents related to a ticket. ', 0, '2004-01-26 09:18:33.689', 0, '2004-01-26 09:18:33.689', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (436, 249, NULL, 'Lets you modify / update the ticket information. ', 0, '2004-01-26 09:18:33.695', 0, '2004-01-26 09:18:33.695', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (437, 250, NULL, 'A new version of a file can be uploaded using the browse button.', 0, '2004-01-26 09:18:33.726', 0, '2004-01-26 09:18:33.726', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (438, 251, NULL, 'You can delete a record by clicking on "Del" next to the record.', 0, '2004-01-26 09:18:33.756', 0, '2004-01-26 09:18:33.756', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (439, 251, NULL, 'You can add a record by clicking on "Add Ticket".', 0, '2004-01-26 09:18:33.779', 0, '2004-01-26 09:18:33.779', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (440, 251, NULL, 'You can view more records in a particular section by clicking "Show More".', 0, '2004-01-26 09:18:33.785', 0, '2004-01-26 09:18:33.785', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (441, 251, NULL, 'You can view more details by clicking on the record.', 0, '2004-01-26 09:18:33.803', 0, '2004-01-26 09:18:33.803', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (442, 251, NULL, 'You can update a record by clicking on "Edit" next to the record.', 0, '2004-01-26 09:18:33.809', 0, '2004-01-26 09:18:33.809', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (443, 252, NULL, 'A new detailed employee record can be added. ', 0, '2004-01-26 09:18:33.899', 0, '2004-01-26 09:18:33.899', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (444, 252, NULL, 'The details of each employee can be viewed, modified or deleted using the select button in the action column.', 0, '2004-01-26 09:18:33.923', 0, '2004-01-26 09:18:33.923', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (445, 253, NULL, 'You can modify or delete the employee details using the modify or delete buttons.', 0, '2004-01-26 09:18:33.953', 0, '2004-01-26 09:18:33.953', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (446, 254, NULL, 'The "Save" button saves the details of the employee entered. ', 0, '2004-01-26 09:18:33.986', 0, '2004-01-26 09:18:33.986', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (447, 254, NULL, 'The "Save & New" button lets you to save the details of one employee and enter another employee in one operation.', 0, '2004-01-26 09:18:33.989', 0, '2004-01-26 09:18:33.989', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (448, 255, NULL, 'Clicking on the update button saves the modified details of the employee.', 0, '2004-01-26 09:18:34.02', 0, '2004-01-26 09:18:34.02', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (449, 256, NULL, 'The employee record can be modified or deleted from the system completely.', 0, '2004-01-26 09:18:34.044', 0, '2004-01-26 09:18:34.044', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (450, 257, NULL, 'You can cancel the reports that are scheduled to be processed by the server by the clicking the select button.', 0, '2004-01-26 09:18:34.086', 0, '2004-01-26 09:18:34.086', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (451, 257, NULL, 'The generated reports can be deleted or viewed/downloaded in .pdf format by clicking the select button under the action column. ', 0, '2004-01-26 09:18:34.091', 0, '2004-01-26 09:18:34.091', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (452, 257, NULL, 'Add a new report', 0, '2004-01-26 09:18:34.109', 0, '2004-01-26 09:18:34.109', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (453, 258, NULL, 'There are four different modules and you can click on the module where you want to generate the report.', 0, '2004-01-26 09:18:34.134', 0, '2004-01-26 09:18:34.134', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (454, 261, NULL, 'You can use the "generate report" button to run the report.', 0, '2004-01-26 09:18:34.182', 0, '2004-01-26 09:18:34.182', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (455, 261, NULL, 'If the parameters exist, you can specify the name of the criteria for future reference and click the check box present at the bottom of the page.', 0, '2004-01-26 09:18:34.205', 0, '2004-01-26 09:18:34.205', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (456, 262, NULL, 'You can run the report by clicking on the title of the report.', 0, '2004-01-26 09:18:34.235', 0, '2004-01-26 09:18:34.235', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (457, 263, NULL, 'If the criteria are present, select the criteria, then continue to enter the parameters to run the report.', 0, '2004-01-26 09:18:34.265', 0, '2004-01-26 09:18:34.265', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (458, 264, NULL, 'You can view the queue either by using the link in the text or using the view queue button.', 0, '2004-01-26 09:18:34.295', 0, '2004-01-26 09:18:34.295', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (459, 265, NULL, 'You can cancel the report that is scheduled to be processed by the server by clicking the select button and selecting "Cancel".', 0, '2004-01-26 09:18:34.319', 0, '2004-01-26 09:18:34.319', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (460, 265, NULL, 'You can view the reports generated, download them or delete them by clicking on the select button under the action column.', 0, '2004-01-26 09:18:34.325', 0, '2004-01-26 09:18:34.325', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (461, 265, NULL, 'A new report can be generated by clicking on the link "Add a Report".', 0, '2004-01-26 09:18:34.342', 0, '2004-01-26 09:18:34.342', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (462, 266, NULL, 'The alphabetical slide rule allows users to be listed based on their last name. Simply click on the starting letter desired.', 0, '2004-01-26 09:18:34.374', 0, '2004-01-26 09:18:34.374', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (463, 266, NULL, 'The columns "Name", ''Username" and "Role" can be clicked to display the users in the ascending or descending order of the chosen criteria.', 0, '2004-01-26 09:18:34.39', 0, '2004-01-26 09:18:34.39', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (464, 266, NULL, 'The "Add New User" link opens a window that allows the administrator to add new users.', 0, '2004-01-26 09:18:34.397', 0, '2004-01-26 09:18:34.397', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (465, 266, NULL, 'The "select" buttons in the "Action" column alongside the name of each user opens a pop-up menu that provides the administrator with options to view more information, modify user information, or disable (or inactivate) the user.', 0, '2004-01-26 09:18:34.414', 0, '2004-01-26 09:18:34.414', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (466, 266, NULL, 'The list is displayed with 10 names per page by default. Additional items in the list may be viewed by clicking on the "Previous" and "Next" navigation links at the bottom of the table or by changing the number of items to be displayed per page.', 0, '2004-01-26 09:18:34.421', 0, '2004-01-26 09:18:34.421', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (467, 266, NULL, 'The users of the CRM system are listed in alphabetical order. Their user name, role and who they report to are also listed to provide a quick overview of information for each user.', 0, '2004-01-26 09:18:34.438', 0, '2004-01-26 09:18:34.438', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (468, 266, NULL, 'The drop list provides a filter to either view only the active or only the inactive users. Inactive users are those who do not have the privilege to use the system currently either because their user names have been disabled or they have expired. These users may be activated (enabled) at a later time.', 0, '2004-01-26 09:18:34.456', 0, '2004-01-26 09:18:34.456', NULL, NULL, true, 7);
INSERT INTO help_features VALUES (469, 267, NULL, 'The ''Reports To" field allows the administrator to setup a user hierarchy. The drop list displays all the users of the system and allows one to be chosen.', 0, '2004-01-26 09:18:34.495', 0, '2004-01-26 09:18:34.495', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (470, 267, NULL, 'The "Role" drop list allows a role to be associated with a user. This association determines the privileges the user may have when he accesses the system.', 0, '2004-01-26 09:18:34.499', 0, '2004-01-26 09:18:34.499', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (471, 267, NULL, 'The "Password" fields allows the administrator to setup a password for the user. The password is used along with the Username to login to the system. Since the password is stored in encrypted form and cannot be interpreted, the administrator is asked to confirm the users password. The user may subsequently change his password according to personal preferences.', 0, '2004-01-26 09:18:34.523', 0, '2004-01-26 09:18:34.523', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (472, 267, NULL, 'The Username is the phrase that is used by the user to login to the system. It must be unique.', 0, '2004-01-26 09:18:34.534', 0, '2004-01-26 09:18:34.534', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (473, 267, NULL, 'An "Expire Date" may be set for each user after which the user is disabled. If this field is left blank the user is active indefinitely. This date can either be typed in the mm/dd/yyyy format or chosen from a calendar that can be accessed from the icon at the right of the field.', 0, '2004-01-26 09:18:34.552', 0, '2004-01-26 09:18:34.552', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (474, 267, NULL, 'The contact field allows the administrator to associate contact information with the user. The administrator may either create new contact information or choose one from the existing list of contacts. This information provides the administrator with the user''s e-mail, telephone and (or) fax number, postal address and any other information that may help the administrator or the system manager to contact the user.
', 0, '2004-01-26 09:18:34.558', 0, '2004-01-26 09:18:34.558', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (475, 268, NULL, 'The "Cancel" button allows current and uncommitted changes to be undone.', 0, '2004-01-26 09:18:34.583', 0, '2004-01-26 09:18:34.583', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (476, 268, NULL, 'When the "Generate new password" field is checked, the system constructs a password for the user and uses the contact information to email the new password to the user.', 0, '2004-01-26 09:18:34.60', 0, '2004-01-26 09:18:34.60', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (477, 268, NULL, 'The "Disable" button provides a quick link to the administrator to disable the user.', 0, '2004-01-26 09:18:34.606', 0, '2004-01-26 09:18:34.606', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (478, 268, NULL, 'The "Username", "Role", "Reports To" and password of the user are editable. For more information about each of these fields see help on "Add user".', 0, '2004-01-26 09:18:34.624', 0, '2004-01-26 09:18:34.624', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (479, 269, NULL, 'The list is displayed by default with 10 items per page, additional items in the login history may be viewed by clicking on the "Previous" and "Next" navigation links at the bottom of the table or by changing the number of items to be displayed on a page.', 0, '2004-01-26 09:18:34.649', 0, '2004-01-26 09:18:34.649', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (480, 269, NULL, 'The login history of the user displays the IP address of the computer from which the user logged in, and the date/time when the user logged in.', 0, '2004-01-26 09:18:34.654', 0, '2004-01-26 09:18:34.654', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (481, 270, NULL, 'Clicking on the select button under the action column would let you to view the details about the viewpoint also modify them.', 0, '2004-01-26 09:18:34.679', 0, '2004-01-26 09:18:34.679', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (482, 270, NULL, 'You can click on the contact under the viewpoint column to know more details about that viewpoint and its permissions.', 0, '2004-01-26 09:18:34.702', 0, '2004-01-26 09:18:34.702', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (483, 270, NULL, 'You can add a new viewpoint using the link "Add New Viewpoint".', 0, '2004-01-26 09:18:34.72', 0, '2004-01-26 09:18:34.72', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (484, 271, NULL, 'You can add a new viewpoint by any employee by clicking the add button.', 0, '2004-01-26 09:18:34.757', 0, '2004-01-26 09:18:34.757', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (485, 271, NULL, 'The permissions for the different modules can be given by checking the Access checkbox.', 0, '2004-01-26 09:18:34.762', 0, '2004-01-26 09:18:34.762', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (486, 271, NULL, 'The contact can be selected and removed using the links "change contact" and "clear contact".', 0, '2004-01-26 09:18:34.78', 0, '2004-01-26 09:18:34.78', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (487, 272, NULL, 'The details can be updated using the update button.', 0, '2004-01-26 09:18:34.805', 0, '2004-01-26 09:18:34.805', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (488, 272, NULL, 'You can also set the permissions to access different modules by checking the check box under the Access column.', 0, '2004-01-26 09:18:34.81', 0, '2004-01-26 09:18:34.81', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (489, 272, NULL, 'You can enable the viewpoint by checking the "Enabled" checkbox.', 0, '2004-01-26 09:18:34.828', 0, '2004-01-26 09:18:34.828', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (490, 273, NULL, 'You can also click the select button under the action column to view or modify the details of roles.', 0, '2004-01-26 09:18:34.853', 0, '2004-01-26 09:18:34.853', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (491, 273, NULL, 'Clicking on the role name gives you details about the role and the permissions it provides. ', 0, '2004-01-26 09:18:34.858', 0, '2004-01-26 09:18:34.858', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (492, 273, NULL, 'You can add a new role into the system.', 0, '2004-01-26 09:18:34.876', 0, '2004-01-26 09:18:34.876', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (493, 274, NULL, 'Clicking the update button updates the role.', 0, '2004-01-26 09:18:34.90', 0, '2004-01-26 09:18:34.90', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (494, 275, NULL, 'The update of the role can be done by clicking the update button. ', 0, '2004-01-26 09:18:34.93', 0, '2004-01-26 09:18:34.93', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (495, 276, NULL, 'Clicking on the module name will display a list of module items that can be configured.', 0, '2004-01-26 09:18:34.961', 0, '2004-01-26 09:18:34.961', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (496, 277, NULL, 'Scheduled Events: A timer triggers a customizable workflow process.', 0, '2004-01-26 09:18:34.997', 0, '2004-01-26 09:18:34.997', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (497, 277, NULL, 'Object Events: An Action triggers customizable workflow process. For example, when an object is inserted, updated, deleted or selected, a process is triggered.', 0, '2004-01-26 09:18:35.02', 0, '2004-01-26 09:18:35.02', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (498, 277, NULL, 'Categories: This lets you create hierarchical categories for a specific feature in the module.', 0, '2004-01-26 09:18:35.026', 0, '2004-01-26 09:18:35.026', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (499, 277, NULL, 'Lookup Lists: You can view the drop-down lists used in the module and make changes.', 0, '2004-01-26 09:18:35.044', 0, '2004-01-26 09:18:35.044', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (500, 277, NULL, 'Custom Folders and Fields: Custom folders allows you to create forms that will be present within each module, essentially custom fields.', 0, '2004-01-26 09:18:35.05', 0, '2004-01-26 09:18:35.05', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (501, 278, NULL, 'You can create a new item type using the add button and add it to the existing list. You can position the item in the list using the up and down buttons, remove it using the remove button and also sort the list. The final changes can be saved using the "Save Changes" button.', 0, '2004-01-26 09:18:35.075', 0, '2004-01-26 09:18:35.075', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (502, 279, NULL, 'You can update the existing the folder, set the options for the records and the permissions for the users.', 0, '2004-01-26 09:18:35.099', 0, '2004-01-26 09:18:35.099', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (503, 280, NULL, 'You can update the existing the folder, set the options for the records and the permissions for the users.', 0, '2004-01-26 09:18:35.123', 0, '2004-01-26 09:18:35.123', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (504, 281, NULL, 'The "Edit" link will let you alter the time for which the users session ends.', 0, '2004-01-26 09:18:35.146', 0, '2004-01-26 09:18:35.146', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (505, 282, NULL, 'The time out can be set by selecting the time from the drop down and clicking the update button.', 0, '2004-01-26 09:18:35.171', 0, '2004-01-26 09:18:35.171', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (506, 283, NULL, 'The usage can be displayed for the current date or a custom date can be specified. This can be selected from the drop down of the date range.', 0, '2004-01-26 09:18:35.212', 0, '2004-01-26 09:18:35.212', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (507, 283, NULL, 'The start date and the end date can be specified if the date range is "custom date range". The update can be done using the update button.', 0, '2004-01-26 09:18:35.235', 0, '2004-01-26 09:18:35.235', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (508, 286, NULL, 'You can also enable or disable the custom folders by clicking "yes" or "no". ', 0, '2004-01-26 09:18:35.296', 0, '2004-01-26 09:18:35.296', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (509, 286, NULL, 'Clicking on the custom folder will give details about that folder and also lets you add groups.', 0, '2004-01-26 09:18:35.302', 0, '2004-01-26 09:18:35.302', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (510, 286, NULL, 'You can update an existing folder using the edit button under the action column.', 0, '2004-01-26 09:18:35.32', 0, '2004-01-26 09:18:35.32', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (511, 286, NULL, 'You can update an existing folder using the edit button under the action column.', 0, '2004-01-26 09:18:35.332', 0, '2004-01-26 09:18:35.332', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (512, 286, NULL, 'Add a folder to the general contacts module.', 0, '2004-01-26 09:18:35.349', 0, '2004-01-26 09:18:35.349', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (513, 287, NULL, 'You can also enable or disable the custom folders by clicking "yes" or "no". ', 0, '2004-01-26 09:18:35.374', 0, '2004-01-26 09:18:35.374', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (514, 287, NULL, 'You can update an existing folder using the edit button under the action column. ', 0, '2004-01-26 09:18:35.376', 0, '2004-01-26 09:18:35.376', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (515, 287, NULL, 'Clicking on the custom folder will give details about that folder and also lets you add groups.', 0, '2004-01-26 09:18:35.38', 0, '2004-01-26 09:18:35.38', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (516, 287, NULL, 'You can update an existing folder using the edit button under the action column. ', 0, '2004-01-26 09:18:35.397', 0, '2004-01-26 09:18:35.397', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (517, 287, NULL, 'Add a folder to the general contacts module. ', 0, '2004-01-26 09:18:35.404', 0, '2004-01-26 09:18:35.404', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (518, 291, NULL, 'You can view the process details by clicking on the select button under the Action column or by clicking on the name of the Triggered Process.', 0, '2004-01-26 09:18:35.488', 0, '2004-01-26 09:18:35.488', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (519, 291, NULL, 'You can view the process details by clicking on the select button under the Action column or by clicking on the name of the Triggered Process.', 0, '2004-01-26 09:18:35.508', 0, '2004-01-26 09:18:35.508', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (520, 292, NULL, 'You can add a group name and save it using the "save" button.', 0, '2004-01-26 09:18:35.536', 0, '2004-01-26 09:18:35.536', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (521, 294, NULL, 'You can click "Edit" in the Action column to update or delete a contact type.', 0, '2004-01-26 09:18:35.566', 0, '2004-01-26 09:18:35.566', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (522, 294, NULL, 'You can preview all the items present in a List name using the drop down in the preview column. ', 0, '2004-01-26 09:18:35.584', 0, '2004-01-26 09:18:35.584', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (523, 297, NULL, 'You can click "Edit" in the Action column to update or delete a contact type.', 0, '2004-01-26 09:18:35.632', 0, '2004-01-26 09:18:35.632', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (524, 297, NULL, 'You can preview all the items present in a List name using the drop down in the preview column.', 0, '2004-01-26 09:18:35.637', 0, '2004-01-26 09:18:35.637', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (525, 298, NULL, 'You can also delete the folder and all the fields using the "Delete this folder and all fields" at the bottom of the page.', 0, '2004-01-26 09:18:35.668', 0, '2004-01-26 09:18:35.668', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (526, 298, NULL, 'The groups can also be moved up or down using the "Up" and "Down". They can also be edited and deleted using the "Edit" and "Del" links.', 0, '2004-01-26 09:18:35.691', 0, '2004-01-26 09:18:35.691', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (527, 298, NULL, 'The custom field can also be edited and deleted using the corresponding links "Edit" and "Del".', 0, '2004-01-26 09:18:35.697', 0, '2004-01-26 09:18:35.697', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (528, 298, NULL, 'The custom field created can be moved up or down for the display using the corresponding links "Up" and "Down".', 0, '2004-01-26 09:18:35.721', 0, '2004-01-26 09:18:35.721', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (529, 298, NULL, 'You can add a custom field for the group using the "Add a custom field" link.', 0, '2004-01-26 09:18:35.727', 0, '2004-01-26 09:18:35.727', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (530, 298, NULL, 'Add a group to the folder selected', 0, '2004-01-26 09:18:35.751', 0, '2004-01-26 09:18:35.751', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (531, 298, NULL, 'You can select the folder by using the drop down box under the general contacts module.', 0, '2004-01-26 09:18:35.757', 0, '2004-01-26 09:18:35.757', NULL, NULL, true, 7);
INSERT INTO help_features VALUES (532, 299, NULL, 'Clicking on the list of categories displayed in level1 shows you its sub levels or sub-directories present in level2 and clicking on these in turn shows its subdirectories in level3 and so on.', 0, '2004-01-26 09:18:35.782', 0, '2004-01-26 09:18:35.782', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (533, 299, NULL, 'You can select to display either the Active Categories or the Draft Categories by clicking on the tabs "Active Categories" and "Draft Categories" respectively.', 0, '2004-01-26 09:18:35.805', 0, '2004-01-26 09:18:35.805', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (534, 300, NULL, 'The activated list can be brought back / reverted to the active list by clicking the "Revert to Active List".', 0, '2004-01-26 09:18:35.829', 0, '2004-01-26 09:18:35.829', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (535, 300, NULL, 'You can activate each level by using the "Activate now" button.', 0, '2004-01-26 09:18:35.835', 0, '2004-01-26 09:18:35.835', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (536, 300, NULL, 'In the draft categories you can edit your category using the edit button present at the bottom of each level.', 0, '2004-01-26 09:18:35.853', 0, '2004-01-26 09:18:35.853', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (537, 300, NULL, 'You can select to display either the Active Categories or the Draft Categories by clicking on the tabs "Active Categories" and "Draft Categories" respectively.', 0, '2004-01-26 09:18:35.859', 0, '2004-01-26 09:18:35.859', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (538, 300, NULL, 'Clicking on the list of categories displayed in level1 shows you its sub-levels or sub-directories present in level2 and clicking on these in turn would shows their subdirectories in level3 and so on.', 0, '2004-01-26 09:18:35.877', 0, '2004-01-26 09:18:35.877', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (539, 304, NULL, 'The "Modify" button in the "Details" tab provides a quick link that allows the users information to be modified without having to browse back to the previous window.', 0, '2004-01-26 09:18:35.938', 0, '2004-01-26 09:18:35.938', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (540, 304, NULL, 'The "Employee Link" in the ''Primary Information" table header provides a quick link to view the user''s contact information.', 0, '2004-01-26 09:18:35.96', 0, '2004-01-26 09:18:35.96', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (541, 304, NULL, 'The "Details" tab displays the information about the user in a non-editable format.', 0, '2004-01-26 09:18:35.969', 0, '2004-01-26 09:18:35.969', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (542, 304, NULL, 'The "Disable" button provides a quick link to disable/inactivate the user.', 0, '2004-01-26 09:18:36.002', 0, '2004-01-26 09:18:36.002', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (543, 309, NULL, 'The user and module section allows the administrator to manage users, roles, role hierarchy and manage modules.', 0, '2004-01-26 09:18:36.075', 0, '2004-01-26 09:18:36.075', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (544, 309, NULL, 'The global parameters and server configuration module allows the administrator to set the session timeout parameter.', 0, '2004-01-26 09:18:36.081', 0, '2004-01-26 09:18:36.081', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (545, 309, NULL, 'The usage section allows the administrator to view the total number of users, memory used, and system usage parameters for various time intervals.', 0, '2004-01-26 09:18:36.098', 0, '2004-01-26 09:18:36.098', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (546, 309, NULL, 'The administration module is divided into distinct categories such as managing users, module configuration, setting global parameters, server configuration and monitoring system usage and resources.', 0, '2004-01-26 09:18:36.105', 0, '2004-01-26 09:18:36.105', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (547, 311, NULL, 'Clicking on the different links of the search results will direct you to the corresponding details in the modules. ', 0, '2004-01-26 09:18:36.207', 0, '2004-01-26 09:18:36.207', NULL, NULL, true, 1);









INSERT INTO help_business_rules VALUES (1, 1, 'You can view your calendar and the calendars of those who work for you', 0, '2004-01-26 09:18:23.623', 0, '2004-01-26 09:18:23.623', NULL, NULL, true);
INSERT INTO help_business_rules VALUES (2, 251, 'Other tickets in my Department: These are records that are assigned to anyone in your department, are unassigned in your department, and are open.', 0, '2004-01-26 09:18:33.827', 0, '2004-01-26 09:18:33.827', NULL, NULL, true);
INSERT INTO help_business_rules VALUES (3, 251, 'Tickets assigned to me: These are records that are assigned to you and are open', 0, '2004-01-26 09:18:33.833', 0, '2004-01-26 09:18:33.833', NULL, NULL, true);
INSERT INTO help_business_rules VALUES (4, 251, 'Tickets created by me: These are records that have been entered by you and are open', 0, '2004-01-26 09:18:33.851', 0, '2004-01-26 09:18:33.851', NULL, NULL, true);






INSERT INTO help_tips VALUES (1, 1, 'Assign due dates for tasks so that you can be alerted', 0, '2004-01-26 09:18:23.629', 0, '2004-01-26 09:18:23.629', true);
INSERT INTO help_tips VALUES (2, 251, 'Make sure to resolve your tickets as soon as possible so they don''t appear here!', 0, '2004-01-26 09:18:33.857', 0, '2004-01-26 09:18:33.857', true);






INSERT INTO sync_system VALUES (1, 'Vport Telemarketing', true);
INSERT INTO sync_system VALUES (2, 'Land Mark: Auto Guide PocketPC', true);
INSERT INTO sync_system VALUES (3, 'Street Smart Speakers: Web Portal', true);
INSERT INTO sync_system VALUES (4, 'CFSHttpXMLWriter', true);
INSERT INTO sync_system VALUES (5, 'Fluency', true);



INSERT INTO sync_table VALUES (1, 1, 'ticket', 'org.aspcfs.modules.troubletickets.base.Ticket', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, 'id');
INSERT INTO sync_table VALUES (2, 2, 'syncClient', 'org.aspcfs.modules.service.base.SyncClient', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, 2, false, NULL);
INSERT INTO sync_table VALUES (3, 2, 'user', 'org.aspcfs.modules.admin.base.User', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, 4, false, NULL);
INSERT INTO sync_table VALUES (4, 2, 'account', 'org.aspcfs.modules.accounts.base.Organization', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, 5, false, NULL);
INSERT INTO sync_table VALUES (5, 2, 'accountInventory', 'org.aspcfs.modules.media.autoguide.base.Inventory', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, 6, false, NULL);
INSERT INTO sync_table VALUES (6, 2, 'inventoryOption', 'org.aspcfs.modules.media.autoguide.base.InventoryOption', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, 8, false, NULL);
INSERT INTO sync_table VALUES (7, 2, 'adRun', 'org.aspcfs.modules.media.autoguide.base.AdRun', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, 10, false, NULL);
INSERT INTO sync_table VALUES (8, 2, 'tableList', 'org.aspcfs.modules.service.base.SyncTableList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, 12, false, NULL);
INSERT INTO sync_table VALUES (9, 2, 'status_master', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, 14, false, NULL);
INSERT INTO sync_table VALUES (10, 2, 'system', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, 16, false, NULL);
INSERT INTO sync_table VALUES (11, 2, 'userList', 'org.aspcfs.modules.admin.base.UserList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE TABLE users ( user_id              int NOT NULL, record_status_id     int NULL, user_name            nvarchar(20) NULL, pin                  nvarchar(20) NULL, modified             datetime NULL, PRIMARY KEY (user_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 50, true, NULL);
INSERT INTO sync_table VALUES (12, 2, 'XIF18users', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF18users ON users ( record_status_id )', 60, false, NULL);
INSERT INTO sync_table VALUES (13, 2, 'makeList', 'org.aspcfs.modules.media.autoguide.base.MakeList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE TABLE make ( make_id              int NOT NULL, make_name            nvarchar(20) NULL, record_status_id     int NULL, entered              datetime NULL, modified             datetime NULL, enteredby            int NULL, modifiedby           int NULL, PRIMARY KEY (make_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 70, true, NULL);
INSERT INTO sync_table VALUES (14, 2, 'XIF2make', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF2make ON make ( record_status_id )', 80, false, NULL);
INSERT INTO sync_table VALUES (15, 2, 'modelList', 'org.aspcfs.modules.media.autoguide.base.ModelList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE TABLE model ( model_id             int NOT NULL, make_id              int NULL, record_status_id     int NULL, model_name           nvarchar(40) NULL, entered              datetime NULL, modified             datetime NULL, enteredby            int NULL, modifiedby           int NULL, PRIMARY KEY (model_id), FOREIGN KEY (make_id) REFERENCES make (make_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 100, true, NULL);
INSERT INTO sync_table VALUES (16, 2, 'XIF3model', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF3model ON model ( record_status_id )', 110, false, NULL);
INSERT INTO sync_table VALUES (17, 2, 'XIF5model', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF5model ON model ( make_id )', 120, false, NULL);
INSERT INTO sync_table VALUES (18, 2, 'vehicleList', 'org.aspcfs.modules.media.autoguide.base.VehicleList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE TABLE vehicle ( year                 nvarchar(4) NOT NULL, vehicle_id           int NOT NULL, model_id             int NULL, make_id              int NULL, record_status_id     int NULL, entered              datetime NULL, modified             datetime NULL, enteredby            int NULL, modifiedby           int NULL, PRIMARY KEY (vehicle_id), FOREIGN KEY (model_id) REFERENCES model (model_id), FOREIGN KEY (make_id) REFERENCES make (make_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 130, true, NULL);
INSERT INTO sync_table VALUES (19, 2, 'XIF30vehicle', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF30vehicle ON vehicle ( make_id )', 140, false, NULL);
INSERT INTO sync_table VALUES (20, 2, 'XIF31vehicle', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF31vehicle ON vehicle ( model_id )', 150, false, NULL);
INSERT INTO sync_table VALUES (21, 2, 'XIF4vehicle', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF4vehicle ON vehicle ( record_status_id )', 160, false, NULL);
INSERT INTO sync_table VALUES (22, 2, 'accountList', 'org.aspcfs.modules.accounts.base.OrganizationList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE TABLE account ( account_id           int NOT NULL, account_name         nvarchar(80) NULL, record_status_id     int NULL, address              nvarchar(80) NULL, modified             datetime NULL, city                 nvarchar(80) NULL, state                nvarchar(2) NULL, notes                nvarchar(255) NULL, zip                  nvarchar(11) NULL, phone                nvarchar(20) NULL, contact              nvarchar(20) NULL, dmv_number           nvarchar(20) NULL, owner_id             int NULL, entered              datetime NULL, enteredby            int NULL, modifiedby           int NULL, PRIMARY KEY (account_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 170, true, NULL);
INSERT INTO sync_table VALUES (23, 2, 'XIF16account', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF16account ON account ( record_status_id )', 180, false, NULL);
INSERT INTO sync_table VALUES (24, 2, 'accountInventoryList', 'org.aspcfs.modules.media.autoguide.base.InventoryList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE TABLE account_inventory ( inventory_id         int NOT NULL, vin                  nvarchar(20) NULL, vehicle_id           int NULL, account_id           int NULL, mileage              nvarchar(20) NULL, enteredby            int NULL, new                  bit, condition            nvarchar(20) NULL, comments             nvarchar(255) NULL, stock_no             nvarchar(20) NULL, ext_color            nvarchar(20) NULL, int_color            nvarchar(20) NULL, style                nvarchar(40) NULL, invoice_price        money NULL, selling_price        money NULL, selling_price_text		nvarchar(100) NULL, modified             datetime NULL, sold                 int NULL, modifiedby           int NULL, record_status_id     int NULL, entered              datetime NULL, PRIMARY KEY (inventory_id), FOREIGN KEY (account_id) REFERENCES account (account_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 190, true, NULL);
INSERT INTO sync_table VALUES (25, 2, 'XIF10account_inventory', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF10account_inventory ON account_inventory ( record_status_id )', 200, false, NULL);
INSERT INTO sync_table VALUES (26, 2, 'XIF10account_inventory', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF11account_inventory ON account_inventory ( modifiedby )', 210, false, NULL);
INSERT INTO sync_table VALUES (27, 2, 'XIF19account_inventory', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF19account_inventory ON account_inventory ( account_id )', 220, false, NULL);
INSERT INTO sync_table VALUES (28, 2, 'XIF35account_inventory', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF35account_inventory ON account_inventory ( vehicle_id )', 230, false, NULL);
INSERT INTO sync_table VALUES (29, 2, 'optionList', 'org.aspcfs.modules.media.autoguide.base.OptionList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE TABLE options ( option_id            int NOT NULL, option_name          nvarchar(20) NULL, record_status_id     int NULL, record_status_date   datetime NULL, PRIMARY KEY (option_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 330, true, NULL);
INSERT INTO sync_table VALUES (30, 2, 'XIF24options', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF24options ON options ( record_status_id )', 340, false, NULL);
INSERT INTO sync_table VALUES (31, 2, 'inventoryOptionList', 'org.aspcfs.modules.media.autoguide.base.InventoryOptionList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE TABLE inventory_options ( inventory_id         int NOT NULL, option_id            int NOT NULL, record_status_id     int NULL, modified             datetime NULL, PRIMARY KEY (option_id, inventory_id), FOREIGN KEY (inventory_id) REFERENCES account_inventory (inventory_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id), FOREIGN KEY (option_id) REFERENCES options (option_id) )', 350, true, NULL);
INSERT INTO sync_table VALUES (32, 2, 'XIF25inventory_options', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF25inventory_options ON inventory_options ( option_id )', 360, false, NULL);
INSERT INTO sync_table VALUES (33, 2, 'XIF27inventory_options', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF27inventory_options ON inventory_options ( record_status_id )', 370, false, NULL);
INSERT INTO sync_table VALUES (34, 2, 'XIF33inventory_options', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF33inventory_options ON inventory_options ( inventory_id )', 380, false, NULL);
INSERT INTO sync_table VALUES (35, 2, 'adTypeList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE TABLE ad_type ( ad_type_id           int NOT NULL, ad_type_name         nvarchar(20) NULL, PRIMARY KEY (ad_type_id) )', 385, true, NULL);
INSERT INTO sync_table VALUES (36, 2, 'adRunList', 'org.aspcfs.modules.media.autoguide.base.AdRunList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE TABLE ad_run ( ad_run_id            int NOT NULL, record_status_id     int NULL, inventory_id         int NULL, ad_type_id           int NULL, ad_run_date          datetime NULL, has_picture          int NULL, modified             datetime NULL, entered              datetime NULL, modifiedby           int NULL, enteredby            int NULL, PRIMARY KEY (ad_run_id), FOREIGN KEY (inventory_id) REFERENCES account_inventory (inventory_id), FOREIGN KEY (ad_type_id) REFERENCES ad_type (ad_type_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 390, true, NULL);
INSERT INTO sync_table VALUES (37, 2, 'XIF22ad_run', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF22ad_run ON ad_run ( record_status_id )', 400, false, NULL);
INSERT INTO sync_table VALUES (38, 2, 'XIF36ad_run', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF36ad_run ON ad_run ( ad_type_id )', 402, false, NULL);
INSERT INTO sync_table VALUES (39, 2, 'XIF37ad_run', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF37ad_run ON ad_run ( inventory_id )', 404, false, NULL);
INSERT INTO sync_table VALUES (40, 2, 'inventory_picture', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE TABLE inventory_picture ( picture_name         nvarchar(20) NOT NULL, inventory_id         int NOT NULL, record_status_id     int NULL, entered              datetime NULL, modified             datetime NULL, modifiedby           int NULL, enteredby            int NULL, PRIMARY KEY (picture_name, inventory_id), FOREIGN KEY (inventory_id) REFERENCES account_inventory (inventory_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 410, false, NULL);
INSERT INTO sync_table VALUES (41, 2, 'XIF23inventory_picture', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF23inventory_picture ON inventory_picture ( record_status_id )', 420, false, NULL);
INSERT INTO sync_table VALUES (42, 2, 'XIF32inventory_picture', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF32inventory_picture ON inventory_picture ( inventory_id )', 430, false, NULL);
INSERT INTO sync_table VALUES (43, 2, 'preferences', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE TABLE preferences ( user_id              int NOT NULL, record_status_id     int NULL, modified             datetime NULL, PRIMARY KEY (user_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id), FOREIGN KEY (user_id) REFERENCES users (user_id) )', 440, false, NULL);
INSERT INTO sync_table VALUES (44, 2, 'XIF29preferences', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF29preferences ON preferences ( record_status_id )', 450, false, NULL);
INSERT INTO sync_table VALUES (45, 2, 'user_account', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE TABLE user_account ( user_id              int NOT NULL, account_id           int NOT NULL, record_status_id     int NULL, modified             datetime NULL, PRIMARY KEY (user_id, account_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id), FOREIGN KEY (account_id) REFERENCES account (account_id), FOREIGN KEY (user_id) REFERENCES users (user_id) )', 460, false, NULL);
INSERT INTO sync_table VALUES (46, 2, 'XIF14user_account', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF14user_account ON user_account ( user_id )', 470, false, NULL);
INSERT INTO sync_table VALUES (47, 2, 'XIF15user_account', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF15user_account ON user_account ( account_id )', 480, false, NULL);
INSERT INTO sync_table VALUES (48, 2, 'XIF17user_account', NULL, '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', 'CREATE INDEX XIF17user_account ON user_account ( record_status_id )', 490, false, NULL);
INSERT INTO sync_table VALUES (49, 2, 'deleteInventoryCache', 'org.aspcfs.modules.media.autoguide.actions.DeleteInventoryCache', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, 500, false, NULL);
INSERT INTO sync_table VALUES (50, 4, 'lookupIndustry', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (51, 4, 'lookupIndustryList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (52, 4, 'systemPrefs', 'org.aspcfs.utils.web.CustomLookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (53, 4, 'systemModules', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (54, 4, 'systemModulesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (55, 4, 'lookupContactTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (56, 4, 'lookupContactTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (57, 4, 'lookupAccountTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (58, 4, 'lookupAccountTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (59, 4, 'lookupDepartment', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (60, 4, 'lookupDepartmentList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (61, 4, 'lookupOrgAddressTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (62, 4, 'lookupOrgAddressTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (63, 4, 'lookupOrgEmailTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (64, 4, 'lookupOrgEmailTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (65, 4, 'lookupOrgPhoneTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (66, 4, 'lookupOrgPhoneTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (67, 4, 'lookupInstantMessengerTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (68, 4, 'lookupInstantMessengerTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (69, 4, 'lookupEmploymentTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (70, 4, 'lookupEmploymentTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (71, 4, 'lookupLocale', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (72, 4, 'lookupLocaleList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (73, 4, 'lookupContactAddressTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (74, 4, 'lookupContactAddressTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (75, 4, 'lookupContactEmailTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (76, 4, 'lookupContactEmailTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (77, 4, 'lookupContactPhoneTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (78, 4, 'lookupContactPhoneTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (79, 4, 'lookupStage', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (80, 4, 'lookupStageList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (81, 4, 'lookupDeliveryOptions', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (82, 4, 'lookupDeliveryOptionsList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (83, 4, 'lookupCallTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (84, 4, 'lookupCallTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (85, 4, 'ticketCategory', 'org.aspcfs.modules.troubletickets.base.TicketCategory', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (86, 4, 'ticketCategoryList', 'org.aspcfs.modules.troubletickets.base.TicketCategoryList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (87, 4, 'ticketSeverity', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (88, 4, 'ticketSeverityList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (89, 4, 'lookupTicketSource', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (90, 4, 'lookupTicketSourceList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (91, 4, 'ticketPriority', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (92, 4, 'ticketPriorityList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (93, 4, 'lookupRevenueTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (94, 4, 'lookupRevenueTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (95, 4, 'lookupRevenueDetailTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (96, 4, 'lookupRevenueDetailTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (97, 4, 'lookupSurveyTypes', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (98, 4, 'lookupSurveyTypesList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (99, 4, 'syncClient', 'org.aspcfs.modules.service.base.SyncClient', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (100, 4, 'user', 'org.aspcfs.modules.admin.base.User', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (101, 4, 'userList', 'org.aspcfs.modules.admin.base.UserList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (102, 4, 'contact', 'org.aspcfs.modules.contacts.base.Contact', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, 'id');
INSERT INTO sync_table VALUES (103, 4, 'contactList', 'org.aspcfs.modules.contacts.base.ContactList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (104, 4, 'ticket', 'org.aspcfs.modules.troubletickets.base.Ticket', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, 'id');
INSERT INTO sync_table VALUES (105, 4, 'ticketList', 'org.aspcfs.modules.troubletickets.base.TicketList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (106, 4, 'account', 'org.aspcfs.modules.accounts.base.Organization', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, 'id');
INSERT INTO sync_table VALUES (107, 4, 'accountList', 'org.aspcfs.modules.accounts.base.OrganizationList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (108, 4, 'role', 'org.aspcfs.modules.admin.base.Role', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (109, 4, 'roleList', 'org.aspcfs.modules.admin.base.RoleList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (110, 4, 'permissionCategory', 'org.aspcfs.modules.admin.base.PermissionCategory', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (111, 4, 'permissionCategoryList', 'org.aspcfs.modules.admin.base.PermissionCategoryList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (112, 4, 'permission', 'org.aspcfs.modules.admin.base.Permission', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (113, 4, 'permissionList', 'org.aspcfs.modules.admin.base.PermissionList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (114, 4, 'rolePermission', 'org.aspcfs.modules.admin.base.RolePermission', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (115, 4, 'rolePermissionList', 'org.aspcfs.modules.admin.base.RolePermissionList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (116, 4, 'opportunity', 'org.aspcfs.modules.pipeline.base.Opportunity', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (117, 4, 'opportunityList', 'org.aspcfs.modules.pipeline.base.OpportunityList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (118, 4, 'call', 'org.aspcfs.modules.contacts.base.Call', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (119, 4, 'callList', 'org.aspcfs.modules.contacts.base.CallList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (120, 4, 'customFieldCategory', 'org.aspcfs.modules.base.CustomFieldCategory', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (121, 4, 'customFieldCategoryList', 'org.aspcfs.modules.base.CustomFieldCategoryList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (122, 4, 'customFieldGroup', 'org.aspcfs.modules.base.CustomFieldGroup', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (123, 4, 'customFieldGroupList', 'org.aspcfs.modules.base.CustomFieldGroupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (124, 4, 'customField', 'org.aspcfs.modules.base.CustomField', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (125, 4, 'customFieldList', 'org.aspcfs.modules.base.CustomFieldList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (126, 4, 'customFieldLookup', 'org.aspcfs.utils.web.LookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (127, 4, 'customFieldLookupList', 'org.aspcfs.utils.web.LookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (128, 4, 'customFieldRecord', 'org.aspcfs.modules.base.CustomFieldRecord', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (129, 4, 'customFieldRecordList', 'org.aspcfs.modules.base.CustomFieldRecordList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (130, 4, 'contactEmailAddress', 'org.aspcfs.modules.contacts.base.ContactEmailAddress', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (131, 4, 'contactEmailAddressList', 'org.aspcfs.modules.contacts.base.ContactEmailAddressList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (132, 4, 'customFieldData', 'org.aspcfs.modules.base.CustomFieldData', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (133, 4, 'lookupProjectActivity', 'org.aspcfs.utils.web.CustomLookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (134, 4, 'lookupProjectActivityList', 'org.aspcfs.utils.web.CustomLookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (135, 4, 'lookupProjectIssues', 'org.aspcfs.utils.web.CustomLookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (136, 4, 'lookupProjectIssuesList', 'org.aspcfs.utils.web.CustomLookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (137, 4, 'lookupProjectLoe', 'org.aspcfs.utils.web.CustomLookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (138, 4, 'lookupProjectLoeList', 'org.aspcfs.utils.web.CustomLookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (139, 4, 'lookupProjectPriority', 'org.aspcfs.utils.web.CustomLookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (140, 4, 'lookupProjectPriorityList', 'org.aspcfs.utils.web.CustomLookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (141, 4, 'lookupProjectStatus', 'org.aspcfs.utils.web.CustomLookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (142, 4, 'lookupProjectStatusList', 'org.aspcfs.utils.web.CustomLookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (143, 4, 'project', 'com.zeroio.iteam.base.Project', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (144, 4, 'projectList', 'com.zeroio.iteam.base.ProjectList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (145, 4, 'requirement', 'com.zeroio.iteam.base.Requirement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (146, 4, 'requirementList', 'com.zeroio.iteam.base.RequirementList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (147, 4, 'assignment', 'com.zeroio.iteam.base.Assignment', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (148, 4, 'assignmentList', 'com.zeroio.iteam.base.AssignmentList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (149, 4, 'issue', 'com.zeroio.iteam.base.Issue', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (150, 4, 'issueList', 'com.zeroio.iteam.base.IssueList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (151, 4, 'issueReply', 'com.zeroio.iteam.base.IssueReply', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (152, 4, 'issueReplyList', 'com.zeroio.iteam.base.IssueReplyList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (153, 4, 'teamMember', 'com.zeroio.iteam.base.TeamMember', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (154, 4, 'fileItem', 'com.zeroio.iteam.base.FileItem', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (155, 4, 'fileItemList', 'com.zeroio.iteam.base.FileItemList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (156, 4, 'fileItemVersion', 'com.zeroio.iteam.base.FileItemVersion', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (157, 4, 'fileItemVersionList', 'com.zeroio.iteam.base.FileItemVersionList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (158, 4, 'fileDownloadLog', 'com.zeroio.iteam.base.FileDownloadLog', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (159, 4, 'contactAddress', 'org.aspcfs.modules.contacts.base.ContactAddress', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (160, 4, 'contactAddressList', 'org.aspcfs.modules.contacts.base.ContactAddressList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (161, 4, 'contactPhoneNumber', 'org.aspcfs.modules.contacts.base.ContactPhoneNumber', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (162, 4, 'contactPhoneNumberList', 'org.aspcfs.modules.contacts.base.ContactPhoneNumberList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (163, 4, 'organizationPhoneNumber', 'org.aspcfs.modules.accounts.base.OrganizationPhoneNumber', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (164, 4, 'organizationPhoneNumberList', 'org.aspcfs.modules.accounts.base.OrganizationPhoneNumberList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (165, 4, 'organizationEmailAddress', 'org.aspcfs.modules.accounts.base.OrganizationEmailAddress', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (166, 4, 'organizationEmailAddressList', 'org.aspcfs.modules.accounts.base.OrganizationEmailAddressList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (167, 4, 'organizationAddress', 'org.aspcfs.modules.accounts.base.OrganizationAddress', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (168, 4, 'organizationAddressList', 'org.aspcfs.modules.accounts.base.OrganizationAddressList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (169, 4, 'ticketLog', 'org.aspcfs.modules.troubletickets.base.TicketLog', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (170, 4, 'ticketLogList', 'org.aspcfs.modules.troubletickets.base.TicketLogList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (171, 4, 'message', 'org.aspcfs.modules.communications.base.Message', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (172, 4, 'messageList', 'org.aspcfs.modules.communications.base.MessageList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (173, 4, 'searchCriteriaElements', 'org.aspcfs.modules.communications.base.SearchCriteriaList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (174, 4, 'searchCriteriaElementsList', 'org.aspcfs.modules.communications.base.SearchCriteriaListList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (175, 4, 'savedCriteriaElement', 'org.aspcfs.modules.communications.base.SavedCriteriaElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (176, 4, 'searchFieldElement', 'org.aspcfs.utils.web.CustomLookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (177, 4, 'searchFieldElementList', 'org.aspcfs.utils.web.CustomLookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (178, 4, 'revenue', 'org.aspcfs.modules.accounts.base.Revenue', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (179, 4, 'revenueList', 'org.aspcfs.modules.accounts.base.RevenueList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (180, 4, 'campaign', 'org.aspcfs.modules.communications.base.Campaign', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (181, 4, 'campaignList', 'org.aspcfs.modules.communications.base.CampaignList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (182, 4, 'scheduledRecipient', 'org.aspcfs.modules.communications.base.ScheduledRecipient', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (183, 4, 'scheduledRecipientList', 'org.aspcfs.modules.communications.base.ScheduledRecipientList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (184, 4, 'accessLog', 'org.aspcfs.modules.admin.base.AccessLog', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (185, 4, 'accessLogList', 'org.aspcfs.modules.admin.base.AccessLogList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (186, 4, 'accountTypeLevels', 'org.aspcfs.modules.accounts.base.AccountTypeLevel', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (187, 4, 'fieldTypes', 'org.aspcfs.utils.web.CustomLookupElement', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (188, 4, 'fieldTypesList', 'org.aspcfs.utils.web.CustomLookupList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (189, 4, 'excludedRecipient', 'org.aspcfs.modules.communications.base.ExcludedRecipient', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (190, 4, 'campaignRun', 'org.aspcfs.modules.communications.base.CampaignRun', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (191, 4, 'campaignRunList', 'org.aspcfs.modules.communications.base.CampaignRunList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (192, 4, 'campaignListGroups', 'org.aspcfs.modules.communications.base.CampaignListGroup', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (193, 5, 'ticket', 'org.aspcfs.modules.troubletickets.base.Ticket', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, 'id');
INSERT INTO sync_table VALUES (194, 5, 'ticketCategory', 'org.aspcfs.modules.troubletickets.base.TicketCategory', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (195, 5, 'ticketCategoryList', 'org.aspcfs.modules.troubletickets.base.TicketCategoryList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (196, 5, 'syncClient', 'org.aspcfs.modules.service.base.SyncClient', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, 2, false, NULL);
INSERT INTO sync_table VALUES (197, 5, 'accountList', 'org.aspcfs.modules.accounts.base.OrganizationList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (198, 5, 'userList', 'org.aspcfs.modules.admin.base.UserList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (199, 5, 'contactList', 'org.aspcfs.modules.contacts.base.ContactList', '2004-01-26 09:18:06.563', '2004-01-26 09:18:06.563', NULL, -1, false, NULL);






























INSERT INTO autoguide_options VALUES (1, 'A/T', false, 10, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (2, '4-CYL', false, 20, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (3, '6-CYL', false, 30, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (4, 'V-8', false, 40, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (5, 'CRUISE', false, 50, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (6, '5-SPD', false, 60, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (7, '4X4', false, 70, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (8, '2-DOOR', false, 80, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (9, '4-DOOR', false, 90, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (10, 'LEATHER', false, 100, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (11, 'P/DL', false, 110, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (12, 'T/W', false, 120, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (13, 'P/SEATS', false, 130, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (14, 'P/WIND', false, 140, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (15, 'P/S', false, 150, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (16, 'BEDLINE', false, 160, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (17, 'LOW MILES', false, 170, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (18, 'EX CLEAN', false, 180, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (19, 'LOADED', false, 190, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (20, 'A/C', false, 200, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (21, 'SUNROOF', false, 210, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (22, 'AM/FM ST', false, 220, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (23, 'CASS', false, 225, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (24, 'CD PLYR', false, 230, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (25, 'ABS', false, 240, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (26, 'ALARM', false, 250, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (27, 'SLD R. WIN', false, 260, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (28, 'AIRBAG', false, 270, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (29, '1 OWNER', false, 280, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_options VALUES (30, 'ALLOY WH', false, 290, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');









INSERT INTO autoguide_ad_run_types VALUES (1, 'In Column', false, 1, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_ad_run_types VALUES (2, 'Display', false, 2, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');
INSERT INTO autoguide_ad_run_types VALUES (3, 'Both', false, 3, true, '2004-01-26 09:18:07.87', '2004-01-26 09:18:07.87');



INSERT INTO lookup_revenue_types VALUES (1, 'Technical', false, 0, true);












INSERT INTO lookup_task_priority VALUES (1, '1', true, 1, true);
INSERT INTO lookup_task_priority VALUES (2, '2', false, 2, true);
INSERT INTO lookup_task_priority VALUES (3, '3', false, 3, true);
INSERT INTO lookup_task_priority VALUES (4, '4', false, 4, true);
INSERT INTO lookup_task_priority VALUES (5, '5', false, 5, true);



INSERT INTO lookup_task_loe VALUES (1, 'Minute(s)', false, 1, true);
INSERT INTO lookup_task_loe VALUES (2, 'Hour(s)', true, 2, true);
INSERT INTO lookup_task_loe VALUES (3, 'Day(s)', false, 3, true);
INSERT INTO lookup_task_loe VALUES (4, 'Week(s)', false, 4, true);
INSERT INTO lookup_task_loe VALUES (5, 'Month(s)', false, 5, true);





















INSERT INTO business_process_component_library VALUES (1, 'org.aspcfs.modules.troubletickets.components.LoadTicketDetails', 1, 'org.aspcfs.modules.troubletickets.components.LoadTicketDetails', 'Load all ticket information for use in other steps', true);
INSERT INTO business_process_component_library VALUES (2, 'org.aspcfs.modules.troubletickets.components.QueryTicketJustClosed', 1, 'org.aspcfs.modules.troubletickets.components.QueryTicketJustClosed', 'Was the ticket just closed?', true);
INSERT INTO business_process_component_library VALUES (3, 'org.aspcfs.modules.components.SendUserNotification', 1, 'org.aspcfs.modules.components.SendUserNotification', 'Send an email notification to a user', true);
INSERT INTO business_process_component_library VALUES (4, 'org.aspcfs.modules.troubletickets.components.SendTicketSurvey', 1, 'org.aspcfs.modules.troubletickets.components.SendTicketSurvey', 'org.aspcfs.modules.troubletickets.components.SendTicketSurvey', true);
INSERT INTO business_process_component_library VALUES (5, 'org.aspcfs.modules.troubletickets.components.QueryTicketJustAssigned', 1, 'org.aspcfs.modules.troubletickets.components.QueryTicketJustAssigned', 'Was the ticket just assigned or reassigned?', true);
INSERT INTO business_process_component_library VALUES (6, 'org.aspcfs.modules.troubletickets.components.GenerateTicketList', 2, 'org.aspcfs.modules.troubletickets.components.GenerateTicketList', 'Generate a list of tickets based on specified parameters.  Are there any tickets matching the parameters?', true);
INSERT INTO business_process_component_library VALUES (7, 'org.aspcfs.modules.troubletickets.components.SendTicketListReport', 2, 'org.aspcfs.modules.troubletickets.components.SendTicketListReport', 'Sends a ticket report to specified users with the specified parameters', true);



INSERT INTO business_process_component_result_lookup VALUES (1, 2, 1, 'Yes', 0, true);
INSERT INTO business_process_component_result_lookup VALUES (2, 2, 0, 'No', 1, true);
INSERT INTO business_process_component_result_lookup VALUES (3, 5, 1, 'Yes', 0, true);



INSERT INTO business_process_parameter_library VALUES (1, 3, 'notification.module', NULL, 'Tickets', true);
INSERT INTO business_process_parameter_library VALUES (2, 3, 'notification.itemId', NULL, '${this.id}', true);
INSERT INTO business_process_parameter_library VALUES (3, 3, 'notification.itemModified', NULL, '${this.modified}', true);
INSERT INTO business_process_parameter_library VALUES (4, 3, 'notification.userToNotify', NULL, '${previous.enteredBy}', true);
INSERT INTO business_process_parameter_library VALUES (5, 3, 'notification.subject', NULL, 'Dark Horse CRM Ticket Closed: ${this.paddedId}', true);
INSERT INTO business_process_parameter_library VALUES (6, 3, 'notification.body', NULL, 'The following ticket in Dark Horse CRM has been closed:


Ticket # ${this.paddedId}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Comment: ${this.comment}

Closed by: ${ticketModifiedByContact.nameFirstLast}

Solution: ${this.solution}
', true);
INSERT INTO business_process_parameter_library VALUES (7, 6, 'notification.module', NULL, 'Tickets', true);
INSERT INTO business_process_parameter_library VALUES (8, 6, 'notification.itemId', NULL, '${this.id}', true);
INSERT INTO business_process_parameter_library VALUES (9, 6, 'notification.itemModified', NULL, '${this.modified}', true);
INSERT INTO business_process_parameter_library VALUES (10, 6, 'notification.userToNotify', NULL, '${this.assignedTo}', true);
INSERT INTO business_process_parameter_library VALUES (11, 6, 'notification.subject', NULL, 'Dark Horse CRM Ticket Assigned: ${this.paddedId}', true);
INSERT INTO business_process_parameter_library VALUES (12, 6, 'notification.body', NULL, 'The following ticket in Dark Horse CRM has been assigned to you:


Ticket # ${this.paddedId}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Assigned By: ${ticketModifiedByContact.nameFirstLast}
Comment: ${this.comment}
', true);
INSERT INTO business_process_parameter_library VALUES (13, 7, 'ticketList.onlyOpen', NULL, 'true', true);
INSERT INTO business_process_parameter_library VALUES (14, 7, 'ticketList.onlyAssigned', NULL, 'true', true);
INSERT INTO business_process_parameter_library VALUES (15, 7, 'ticketList.onlyUnassigned', NULL, 'true', true);
INSERT INTO business_process_parameter_library VALUES (16, 7, 'ticketList.minutesOlderThan', NULL, '10', true);
INSERT INTO business_process_parameter_library VALUES (17, 7, 'ticketList.lastAnchor', NULL, '${process.lastAnchor}', true);
INSERT INTO business_process_parameter_library VALUES (18, 7, 'ticketList.nextAnchor', NULL, '${process.nextAnchor}', true);
INSERT INTO business_process_parameter_library VALUES (19, 8, 'notification.users.to', NULL, '${this.enteredBy}', true);
INSERT INTO business_process_parameter_library VALUES (20, 8, 'notification.contacts.to', NULL, '${this.contactId}', true);
INSERT INTO business_process_parameter_library VALUES (21, 8, 'notification.subject', NULL, 'Dark Horse CRM Unassigned Ticket Report (${objects.size})', true);
INSERT INTO business_process_parameter_library VALUES (22, 8, 'notification.body', NULL, '** This is an automated message **

The following tickets in Dark Horse CRM are unassigned and need attention:

', true);
INSERT INTO business_process_parameter_library VALUES (23, 8, 'report.ticket.content', NULL, '----- Ticket Details -----
Ticket # ${this.paddedId}
Created: ${this.enteredString}
Organization: ${ticketOrganization.name}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Last Modified By: ${ticketModifiedByContact.nameFirstLast}
Comment: ${this.comment}


', true);



INSERT INTO business_process VALUES (1, 'dhv.ticket.insert', 'Ticket change notification', 1, 8, 1, true, '2004-01-26 09:18:40.176');
INSERT INTO business_process VALUES (2, 'dhv.report.ticketList.overdue', 'Overdue ticket notification', 2, 8, 7, true, '2004-01-26 09:18:40.176');



INSERT INTO business_process_component VALUES (1, 1, 1, NULL, NULL, true);
INSERT INTO business_process_component VALUES (2, 1, 2, 1, NULL, true);
INSERT INTO business_process_component VALUES (3, 1, 3, 2, 1, true);
INSERT INTO business_process_component VALUES (4, 1, 4, 2, 1, false);
INSERT INTO business_process_component VALUES (5, 1, 5, 2, 0, true);
INSERT INTO business_process_component VALUES (6, 1, 3, 5, 1, true);
INSERT INTO business_process_component VALUES (7, 2, 6, NULL, NULL, true);
INSERT INTO business_process_component VALUES (8, 2, 7, 7, NULL, true);






INSERT INTO business_process_component_parameter VALUES (1, 3, 1, 'Tickets', true);
INSERT INTO business_process_component_parameter VALUES (2, 3, 2, '${this.id}', true);
INSERT INTO business_process_component_parameter VALUES (3, 3, 3, '${this.modified}', true);
INSERT INTO business_process_component_parameter VALUES (4, 3, 4, '${previous.enteredBy}', true);
INSERT INTO business_process_component_parameter VALUES (5, 3, 5, 'Dark Horse CRM Ticket Closed: ${this.paddedId}', true);
INSERT INTO business_process_component_parameter VALUES (6, 3, 6, 'The following ticket in Dark Horse CRM has been closed:


Ticket # ${this.paddedId}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Comment: ${this.comment}

Closed by: ${ticketModifiedByContact.nameFirstLast}

Solution: ${this.solution}
', true);
INSERT INTO business_process_component_parameter VALUES (7, 6, 7, 'Tickets', true);
INSERT INTO business_process_component_parameter VALUES (8, 6, 8, '${this.id}', true);
INSERT INTO business_process_component_parameter VALUES (9, 6, 9, '${this.modified}', true);
INSERT INTO business_process_component_parameter VALUES (10, 6, 10, '${this.assignedTo}', true);
INSERT INTO business_process_component_parameter VALUES (11, 6, 11, 'Dark Horse CRM Ticket Assigned: ${this.paddedId}', true);
INSERT INTO business_process_component_parameter VALUES (12, 6, 12, 'The following ticket in Dark Horse CRM has been assigned to you:


Ticket # ${this.paddedId}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Assigned By: ${ticketModifiedByContact.nameFirstLast}
Comment: ${this.comment}
', true);
INSERT INTO business_process_component_parameter VALUES (13, 7, 13, 'true', true);
INSERT INTO business_process_component_parameter VALUES (14, 7, 14, 'true', false);
INSERT INTO business_process_component_parameter VALUES (15, 7, 15, 'true', true);
INSERT INTO business_process_component_parameter VALUES (16, 7, 16, '10', true);
INSERT INTO business_process_component_parameter VALUES (17, 7, 17, '${process.lastAnchor}', true);
INSERT INTO business_process_component_parameter VALUES (18, 7, 18, '${process.nextAnchor}', true);
INSERT INTO business_process_component_parameter VALUES (19, 8, 19, '${this.enteredBy}', true);
INSERT INTO business_process_component_parameter VALUES (20, 8, 20, '${this.contactId}', false);
INSERT INTO business_process_component_parameter VALUES (21, 8, 21, 'Dark Horse CRM Unassigned Ticket Report (${objects.size})', true);
INSERT INTO business_process_component_parameter VALUES (22, 8, 22, '** This is an automated message **

The following tickets in Dark Horse CRM are unassigned and need attention:

', true);
INSERT INTO business_process_component_parameter VALUES (23, 8, 23, '----- Ticket Details -----
Ticket # ${this.paddedId}
Created: ${this.enteredString}
Organization: ${ticketOrganization.name}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Last Modified By: ${ticketModifiedByContact.nameFirstLast}
Comment: ${this.comment}


', true);









INSERT INTO business_process_hook_library VALUES (1, 8, 'org.aspcfs.modules.troubletickets.base.Ticket', true);



INSERT INTO business_process_hook_triggers VALUES (1, 2, 1, true);
INSERT INTO business_process_hook_triggers VALUES (2, 1, 1, true);



INSERT INTO business_process_hook VALUES (1, 1, 1, true);
INSERT INTO business_process_hook VALUES (2, 2, 1, true);



CREATE INDEX orglist_name ON organization USING btree (name);



CREATE INDEX contact_user_id_idx ON contact USING btree (user_id);



CREATE INDEX contactlist_namecompany ON contact USING btree (namelast, namefirst, company);



CREATE INDEX contactlist_company ON contact USING btree (company, namelast, namefirst);



CREATE INDEX oppcomplist_closedate ON opportunity_component USING btree (closedate);



CREATE INDEX oppcomplist_description ON opportunity_component USING btree (description);



CREATE INDEX call_log_cidx ON call_log USING btree (alertdate, enteredby);



CREATE INDEX ticket_cidx ON ticket USING btree (assigned_to, closed);



CREATE INDEX ticketlist_entered ON ticket USING btree (entered);



CREATE INDEX custom_field_cat_idx ON custom_field_category USING btree (module_id);



CREATE INDEX custom_field_grp_idx ON custom_field_group USING btree (category_id);



CREATE INDEX custom_field_inf_idx ON custom_field_info USING btree (group_id);



CREATE INDEX custom_field_rec_idx ON custom_field_record USING btree (link_module_id, link_item_id, category_id);



CREATE INDEX custom_field_dat_idx ON custom_field_data USING btree (record_id, field_id);



CREATE INDEX projects_idx ON projects USING btree (group_id, project_id);



CREATE INDEX project_assignments_idx ON project_assignments USING btree (activity_id);



CREATE INDEX project_assignments_cidx ON project_assignments USING btree (complete_date, user_assign_id);



CREATE INDEX project_issues_limit_idx ON project_issues USING btree (type_id, project_id, enteredby);



CREATE INDEX project_issues_idx ON project_issues USING btree (issue_id);



CREATE INDEX project_files_cidx ON project_files USING btree (link_module_id, link_item_id);



CREATE UNIQUE INDEX idx_sync_map ON sync_map USING btree (client_id, table_id, record_id);



CREATE UNIQUE INDEX idx_autog_inv_opt ON autoguide_inventory_options USING btree (inventory_id, option_id);



ALTER TABLE ONLY "access"
    ADD CONSTRAINT access_pkey PRIMARY KEY (user_id);



ALTER TABLE ONLY lookup_industry
    ADD CONSTRAINT lookup_industry_pkey PRIMARY KEY (code);



ALTER TABLE ONLY access_log
    ADD CONSTRAINT access_log_pkey PRIMARY KEY (id);



ALTER TABLE ONLY access_log
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY usage_log
    ADD CONSTRAINT usage_log_pkey PRIMARY KEY (usage_id);



ALTER TABLE ONLY lookup_contact_types
    ADD CONSTRAINT lookup_contact_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_contact_types
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



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



ALTER TABLE ONLY lookup_instantmessenger_types
    ADD CONSTRAINT lookup_instantmessenger_types_pkey PRIMARY KEY (code);



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



ALTER TABLE ONLY organization
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization
    ADD CONSTRAINT "$3" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact
    ADD CONSTRAINT contact_pkey PRIMARY KEY (contact_id);



ALTER TABLE ONLY contact
    ADD CONSTRAINT contact_employee_id_key UNIQUE (employee_id);



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$2" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$3" FOREIGN KEY (department) REFERENCES lookup_department(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$4" FOREIGN KEY (super) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$5" FOREIGN KEY (assistant) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$8" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact
    ADD CONSTRAINT "$9" FOREIGN KEY (access_type) REFERENCES lookup_access_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (role_id);



ALTER TABLE ONLY role
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY role
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY permission_category
    ADD CONSTRAINT permission_category_pkey PRIMARY KEY (category_id);



ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (permission_id);



ALTER TABLE ONLY permission
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY role_permission
    ADD CONSTRAINT role_permission_pkey PRIMARY KEY (id);



ALTER TABLE ONLY role_permission
    ADD CONSTRAINT "$1" FOREIGN KEY (role_id) REFERENCES role(role_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY role_permission
    ADD CONSTRAINT "$2" FOREIGN KEY (permission_id) REFERENCES permission(permission_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_stage
    ADD CONSTRAINT lookup_stage_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_delivery_options
    ADD CONSTRAINT lookup_delivery_options_pkey PRIMARY KEY (code);



ALTER TABLE ONLY news
    ADD CONSTRAINT news_pkey PRIMARY KEY (rec_id);



ALTER TABLE ONLY news
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization_address
    ADD CONSTRAINT organization_address_pkey PRIMARY KEY (address_id);



ALTER TABLE ONLY organization_address
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization_address
    ADD CONSTRAINT "$2" FOREIGN KEY (address_type) REFERENCES lookup_orgaddress_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization_address
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization_address
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT organization_emailaddress_pkey PRIMARY KEY (emailaddress_id);



ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT "$2" FOREIGN KEY (emailaddress_type) REFERENCES lookup_orgemail_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT organization_phone_pkey PRIMARY KEY (phone_id);



ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT "$2" FOREIGN KEY (phone_type) REFERENCES lookup_orgphone_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_address
    ADD CONSTRAINT contact_address_pkey PRIMARY KEY (address_id);



ALTER TABLE ONLY contact_address
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_address
    ADD CONSTRAINT "$2" FOREIGN KEY (address_type) REFERENCES lookup_contactaddress_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_address
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_address
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT contact_emailaddress_pkey PRIMARY KEY (emailaddress_id);



ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT "$2" FOREIGN KEY (emailaddress_type) REFERENCES lookup_contactemail_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT contact_phone_pkey PRIMARY KEY (phone_id);



ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT "$2" FOREIGN KEY (phone_type) REFERENCES lookup_contactphone_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (notification_id);



ALTER TABLE ONLY cfsinbox_message
    ADD CONSTRAINT cfsinbox_message_pkey PRIMARY KEY (id);



ALTER TABLE ONLY cfsinbox_message
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY cfsinbox_message
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY cfsinbox_messagelink
    ADD CONSTRAINT "$1" FOREIGN KEY (id) REFERENCES cfsinbox_message(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY cfsinbox_messagelink
    ADD CONSTRAINT "$2" FOREIGN KEY (sent_to) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY cfsinbox_messagelink
    ADD CONSTRAINT "$3" FOREIGN KEY (sent_from) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY account_type_levels
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY account_type_levels
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_account_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_type_levels
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY contact_type_levels
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_contact_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_lists_lookup
    ADD CONSTRAINT lookup_lists_lookup_pkey PRIMARY KEY (id);



ALTER TABLE ONLY lookup_lists_lookup
    ADD CONSTRAINT "$1" FOREIGN KEY (module_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT viewpoint_pkey PRIMARY KEY (viewpoint_id);



ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT "$2" FOREIGN KEY (vp_user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY viewpoint_permission
    ADD CONSTRAINT viewpoint_permission_pkey PRIMARY KEY (vp_permission_id);



ALTER TABLE ONLY viewpoint_permission
    ADD CONSTRAINT "$1" FOREIGN KEY (viewpoint_id) REFERENCES viewpoint(viewpoint_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY viewpoint_permission
    ADD CONSTRAINT "$2" FOREIGN KEY (permission_id) REFERENCES permission(permission_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY report
    ADD CONSTRAINT report_pkey PRIMARY KEY (report_id);



ALTER TABLE ONLY report
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY report
    ADD CONSTRAINT "$2" FOREIGN KEY (permission_id) REFERENCES permission(permission_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY report
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY report
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT report_criteria_pkey PRIMARY KEY (criteria_id);



ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT "$1" FOREIGN KEY (report_id) REFERENCES report(report_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT "$2" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY report_criteria_parameter
    ADD CONSTRAINT report_criteria_parameter_pkey PRIMARY KEY (parameter_id);



ALTER TABLE ONLY report_criteria_parameter
    ADD CONSTRAINT "$1" FOREIGN KEY (criteria_id) REFERENCES report_criteria(criteria_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY report_queue
    ADD CONSTRAINT report_queue_pkey PRIMARY KEY (queue_id);



ALTER TABLE ONLY report_queue
    ADD CONSTRAINT "$1" FOREIGN KEY (report_id) REFERENCES report(report_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY report_queue
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY report_queue_criteria
    ADD CONSTRAINT report_queue_criteria_pkey PRIMARY KEY (criteria_id);



ALTER TABLE ONLY report_queue_criteria
    ADD CONSTRAINT "$1" FOREIGN KEY (queue_id) REFERENCES report_queue(queue_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY action_list
    ADD CONSTRAINT action_list_pkey PRIMARY KEY (action_id);



ALTER TABLE ONLY action_list
    ADD CONSTRAINT "$1" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY action_list
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY action_list
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY action_item
    ADD CONSTRAINT action_item_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY action_item
    ADD CONSTRAINT "$1" FOREIGN KEY (action_id) REFERENCES action_list(action_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY action_item
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY action_item
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY action_item_log
    ADD CONSTRAINT action_item_log_pkey PRIMARY KEY (log_id);



ALTER TABLE ONLY action_item_log
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES action_item(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY action_item_log
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY action_item_log
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY database_version
    ADD CONSTRAINT database_version_pkey PRIMARY KEY (version_id);



ALTER TABLE ONLY lookup_call_types
    ADD CONSTRAINT lookup_call_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_opportunity_types
    ADD CONSTRAINT lookup_opportunity_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY opportunity_header
    ADD CONSTRAINT opportunity_header_pkey PRIMARY KEY (opp_id);



ALTER TABLE ONLY opportunity_header
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY opportunity_header
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT opportunity_component_pkey PRIMARY KEY (id);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$1" FOREIGN KEY (opp_id) REFERENCES opportunity_header(opp_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$2" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$3" FOREIGN KEY (stage) REFERENCES lookup_stage(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY opportunity_component_levels
    ADD CONSTRAINT "$1" FOREIGN KEY (opp_id) REFERENCES opportunity_component(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY opportunity_component_levels
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_opportunity_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY call_log
    ADD CONSTRAINT call_log_pkey PRIMARY KEY (call_id);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$3" FOREIGN KEY (opp_id) REFERENCES opportunity_header(opp_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$4" FOREIGN KEY (call_type_id) REFERENCES lookup_call_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



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



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$5" FOREIGN KEY (pri_code) REFERENCES ticket_priority(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$6" FOREIGN KEY (level_code) REFERENCES ticket_level(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$7" FOREIGN KEY (department_code) REFERENCES lookup_department(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$8" FOREIGN KEY (source_code) REFERENCES lookup_ticketsource(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$9" FOREIGN KEY (assigned_to) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$10" FOREIGN KEY (scode) REFERENCES ticket_severity(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT ticketlog_pkey PRIMARY KEY (id);



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$1" FOREIGN KEY (ticketid) REFERENCES ticket(ticketid) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$2" FOREIGN KEY (assigned_to) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$3" FOREIGN KEY (pri_code) REFERENCES ticket_priority(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$4" FOREIGN KEY (department_code) REFERENCES lookup_department(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$5" FOREIGN KEY (scode) REFERENCES ticket_severity(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY module_field_categorylink
    ADD CONSTRAINT module_field_categorylink_pkey PRIMARY KEY (id);



ALTER TABLE ONLY module_field_categorylink
    ADD CONSTRAINT module_field_categorylink_category_id_key UNIQUE (category_id);



ALTER TABLE ONLY module_field_categorylink
    ADD CONSTRAINT "$1" FOREIGN KEY (module_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY custom_field_category
    ADD CONSTRAINT custom_field_category_pkey PRIMARY KEY (category_id);



ALTER TABLE ONLY custom_field_category
    ADD CONSTRAINT "$1" FOREIGN KEY (module_id) REFERENCES module_field_categorylink(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY custom_field_group
    ADD CONSTRAINT custom_field_group_pkey PRIMARY KEY (group_id);



ALTER TABLE ONLY custom_field_group
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES custom_field_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY custom_field_info
    ADD CONSTRAINT custom_field_info_pkey PRIMARY KEY (field_id);



ALTER TABLE ONLY custom_field_info
    ADD CONSTRAINT "$1" FOREIGN KEY (group_id) REFERENCES custom_field_group(group_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY custom_field_lookup
    ADD CONSTRAINT custom_field_lookup_pkey PRIMARY KEY (code);



ALTER TABLE ONLY custom_field_lookup
    ADD CONSTRAINT "$1" FOREIGN KEY (field_id) REFERENCES custom_field_info(field_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY custom_field_record
    ADD CONSTRAINT custom_field_record_pkey PRIMARY KEY (record_id);



ALTER TABLE ONLY custom_field_record
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES custom_field_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY custom_field_record
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY custom_field_record
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY custom_field_data
    ADD CONSTRAINT "$1" FOREIGN KEY (record_id) REFERENCES custom_field_record(record_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY custom_field_data
    ADD CONSTRAINT "$2" FOREIGN KEY (field_id) REFERENCES custom_field_info(field_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_project_activity
    ADD CONSTRAINT lookup_project_activity_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_priority
    ADD CONSTRAINT lookup_project_priority_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_issues
    ADD CONSTRAINT lookup_project_issues_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_status
    ADD CONSTRAINT lookup_project_status_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_loe
    ADD CONSTRAINT lookup_project_loe_pkey PRIMARY KEY (code);



ALTER TABLE ONLY projects
    ADD CONSTRAINT projects_pkey PRIMARY KEY (project_id);



ALTER TABLE ONLY projects
    ADD CONSTRAINT "$1" FOREIGN KEY (department_id) REFERENCES lookup_department(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY projects
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY projects
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT project_requirements_pkey PRIMARY KEY (requirement_id);



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$2" FOREIGN KEY (estimated_loetype) REFERENCES lookup_project_loe(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$3" FOREIGN KEY (actual_loetype) REFERENCES lookup_project_loe(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$4" FOREIGN KEY (approvedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$5" FOREIGN KEY (closedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT project_assignments_pkey PRIMARY KEY (assignment_id);



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$2" FOREIGN KEY (requirement_id) REFERENCES project_requirements(requirement_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$3" FOREIGN KEY (assignedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$4" FOREIGN KEY (user_assign_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$5" FOREIGN KEY (activity_id) REFERENCES lookup_project_activity(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$6" FOREIGN KEY (estimated_loetype) REFERENCES lookup_project_loe(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$7" FOREIGN KEY (actual_loetype) REFERENCES lookup_project_loe(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$8" FOREIGN KEY (priority_id) REFERENCES lookup_project_priority(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$9" FOREIGN KEY (status_id) REFERENCES lookup_project_status(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$10" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$11" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments_status
    ADD CONSTRAINT project_assignments_status_pkey PRIMARY KEY (status_id);



ALTER TABLE ONLY project_assignments_status
    ADD CONSTRAINT "$1" FOREIGN KEY (assignment_id) REFERENCES project_assignments(assignment_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments_status
    ADD CONSTRAINT "$2" FOREIGN KEY (user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_issues
    ADD CONSTRAINT project_issues_pkey PRIMARY KEY (issue_id);



ALTER TABLE ONLY project_issues
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_issues
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_project_issues(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_issues
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_issues
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_issue_replies
    ADD CONSTRAINT project_issue_replies_pkey PRIMARY KEY (reply_id);



ALTER TABLE ONLY project_issue_replies
    ADD CONSTRAINT "$1" FOREIGN KEY (issue_id) REFERENCES project_issues(issue_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_issue_replies
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_issue_replies
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_folders
    ADD CONSTRAINT project_folders_pkey PRIMARY KEY (folder_id);



ALTER TABLE ONLY project_files
    ADD CONSTRAINT project_files_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY project_files
    ADD CONSTRAINT "$1" FOREIGN KEY (folder_id) REFERENCES project_folders(folder_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_files
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_files
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_files_version
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_files_version
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_files_version
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_files_download
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_files_download
    ADD CONSTRAINT "$2" FOREIGN KEY (user_download_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$2" FOREIGN KEY (user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY saved_criterialist
    ADD CONSTRAINT saved_criterialist_pkey PRIMARY KEY (id);



ALTER TABLE ONLY saved_criterialist
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY saved_criterialist
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY saved_criterialist
    ADD CONSTRAINT "$3" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY campaign
    ADD CONSTRAINT campaign_pkey PRIMARY KEY (campaign_id);



ALTER TABLE ONLY campaign
    ADD CONSTRAINT "$1" FOREIGN KEY (approvedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY campaign
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY campaign
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY campaign_run
    ADD CONSTRAINT campaign_run_pkey PRIMARY KEY (id);



ALTER TABLE ONLY campaign_run
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY excluded_recipient
    ADD CONSTRAINT excluded_recipient_pkey PRIMARY KEY (id);



ALTER TABLE ONLY excluded_recipient
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY excluded_recipient
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY campaign_list_groups
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY campaign_list_groups
    ADD CONSTRAINT "$2" FOREIGN KEY (group_id) REFERENCES saved_criterialist(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_campaign_groups
    ADD CONSTRAINT active_campaign_groups_pkey PRIMARY KEY (id);



ALTER TABLE ONLY active_campaign_groups
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY scheduled_recipient
    ADD CONSTRAINT scheduled_recipient_pkey PRIMARY KEY (id);



ALTER TABLE ONLY scheduled_recipient
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY scheduled_recipient
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_survey_types
    ADD CONSTRAINT lookup_survey_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY survey
    ADD CONSTRAINT survey_pkey PRIMARY KEY (survey_id);



ALTER TABLE ONLY survey
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY survey
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY campaign_survey_link
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY campaign_survey_link
    ADD CONSTRAINT "$2" FOREIGN KEY (survey_id) REFERENCES survey(survey_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY survey_questions
    ADD CONSTRAINT survey_questions_pkey PRIMARY KEY (question_id);



ALTER TABLE ONLY survey_questions
    ADD CONSTRAINT "$1" FOREIGN KEY (survey_id) REFERENCES survey(survey_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY survey_questions
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_survey_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY survey_items
    ADD CONSTRAINT survey_items_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY survey_items
    ADD CONSTRAINT "$1" FOREIGN KEY (question_id) REFERENCES survey_questions(question_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey
    ADD CONSTRAINT active_survey_pkey PRIMARY KEY (active_survey_id);



ALTER TABLE ONLY active_survey
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_survey_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey_questions
    ADD CONSTRAINT active_survey_questions_pkey PRIMARY KEY (question_id);



ALTER TABLE ONLY active_survey_questions
    ADD CONSTRAINT "$1" FOREIGN KEY (active_survey_id) REFERENCES active_survey(active_survey_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey_questions
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_survey_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey_items
    ADD CONSTRAINT active_survey_items_pkey PRIMARY KEY (item_id);



ALTER TABLE ONLY active_survey_items
    ADD CONSTRAINT "$1" FOREIGN KEY (question_id) REFERENCES active_survey_questions(question_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey_responses
    ADD CONSTRAINT active_survey_responses_pkey PRIMARY KEY (response_id);



ALTER TABLE ONLY active_survey_responses
    ADD CONSTRAINT "$1" FOREIGN KEY (active_survey_id) REFERENCES active_survey(active_survey_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey_answers
    ADD CONSTRAINT active_survey_answers_pkey PRIMARY KEY (answer_id);



ALTER TABLE ONLY active_survey_answers
    ADD CONSTRAINT "$1" FOREIGN KEY (response_id) REFERENCES active_survey_responses(response_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey_answers
    ADD CONSTRAINT "$2" FOREIGN KEY (question_id) REFERENCES active_survey_questions(question_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey_answer_items
    ADD CONSTRAINT active_survey_answer_items_pkey PRIMARY KEY (id);



ALTER TABLE ONLY active_survey_answer_items
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES active_survey_items(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey_answer_items
    ADD CONSTRAINT "$2" FOREIGN KEY (answer_id) REFERENCES active_survey_answers(answer_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey_answer_avg
    ADD CONSTRAINT active_survey_answer_avg_pkey PRIMARY KEY (id);



ALTER TABLE ONLY active_survey_answer_avg
    ADD CONSTRAINT "$1" FOREIGN KEY (question_id) REFERENCES active_survey_questions(question_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY active_survey_answer_avg
    ADD CONSTRAINT "$2" FOREIGN KEY (item_id) REFERENCES active_survey_items(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY field_types
    ADD CONSTRAINT field_types_pkey PRIMARY KEY (id);



ALTER TABLE ONLY search_fields
    ADD CONSTRAINT search_fields_pkey PRIMARY KEY (id);



ALTER TABLE ONLY message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);



ALTER TABLE ONLY message
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY message
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY message
    ADD CONSTRAINT "$3" FOREIGN KEY (access_type) REFERENCES lookup_access_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY message_template
    ADD CONSTRAINT message_template_pkey PRIMARY KEY (id);



ALTER TABLE ONLY message_template
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY message_template
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY saved_criteriaelement
    ADD CONSTRAINT "$1" FOREIGN KEY (id) REFERENCES saved_criterialist(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY saved_criteriaelement
    ADD CONSTRAINT "$2" FOREIGN KEY (field) REFERENCES search_fields(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY saved_criteriaelement
    ADD CONSTRAINT "$3" FOREIGN KEY (operatorid) REFERENCES field_types(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_module
    ADD CONSTRAINT help_module_pkey PRIMARY KEY (module_id);



ALTER TABLE ONLY help_module
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT help_contents_pkey PRIMARY KEY (help_id);



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$2" FOREIGN KEY (link_module_id) REFERENCES help_module(module_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$3" FOREIGN KEY (nextcontent) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$4" FOREIGN KEY (prevcontent) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$5" FOREIGN KEY (upcontent) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT help_tableof_contents_pkey PRIMARY KEY (content_id);



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$1" FOREIGN KEY (firstchild) REFERENCES help_tableof_contents(content_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$2" FOREIGN KEY (nextsibling) REFERENCES help_tableof_contents(content_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$3" FOREIGN KEY (parent) REFERENCES help_tableof_contents(content_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$4" FOREIGN KEY (category_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT help_tableofcontentitem_links_pkey PRIMARY KEY (link_id);



ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT "$1" FOREIGN KEY (global_link_id) REFERENCES help_tableof_contents(content_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT "$2" FOREIGN KEY (linkto_content_id) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_help_features
    ADD CONSTRAINT lookup_help_features_pkey PRIMARY KEY (code);



ALTER TABLE ONLY help_features
    ADD CONSTRAINT help_features_pkey PRIMARY KEY (feature_id);



ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$1" FOREIGN KEY (link_help_id) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$2" FOREIGN KEY (link_feature_id) REFERENCES lookup_help_features(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$5" FOREIGN KEY (completedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT help_related_links_pkey PRIMARY KEY (relatedlink_id);



ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT "$1" FOREIGN KEY (owning_module_id) REFERENCES help_module(module_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT "$2" FOREIGN KEY (linkto_content_id) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT help_faqs_pkey PRIMARY KEY (faq_id);



ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT "$1" FOREIGN KEY (owning_module_id) REFERENCES help_module(module_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT "$4" FOREIGN KEY (completedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT help_business_rules_pkey PRIMARY KEY (rule_id);



ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT "$1" FOREIGN KEY (link_help_id) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT "$4" FOREIGN KEY (completedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_notes
    ADD CONSTRAINT help_notes_pkey PRIMARY KEY (note_id);



ALTER TABLE ONLY help_notes
    ADD CONSTRAINT "$1" FOREIGN KEY (link_help_id) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_notes
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_notes
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_notes
    ADD CONSTRAINT "$4" FOREIGN KEY (completedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_tips
    ADD CONSTRAINT help_tips_pkey PRIMARY KEY (tip_id);



ALTER TABLE ONLY help_tips
    ADD CONSTRAINT "$1" FOREIGN KEY (link_help_id) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_tips
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY help_tips
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY sync_client
    ADD CONSTRAINT sync_client_pkey PRIMARY KEY (client_id);



ALTER TABLE ONLY sync_system
    ADD CONSTRAINT sync_system_pkey PRIMARY KEY (system_id);



ALTER TABLE ONLY sync_table
    ADD CONSTRAINT sync_table_pkey PRIMARY KEY (table_id);



ALTER TABLE ONLY sync_table
    ADD CONSTRAINT "$1" FOREIGN KEY (system_id) REFERENCES sync_system(system_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY sync_map
    ADD CONSTRAINT "$1" FOREIGN KEY (client_id) REFERENCES sync_client(client_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY sync_map
    ADD CONSTRAINT "$2" FOREIGN KEY (table_id) REFERENCES sync_table(table_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY sync_conflict_log
    ADD CONSTRAINT "$1" FOREIGN KEY (client_id) REFERENCES sync_client(client_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY sync_conflict_log
    ADD CONSTRAINT "$2" FOREIGN KEY (table_id) REFERENCES sync_table(table_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY sync_log
    ADD CONSTRAINT sync_log_pkey PRIMARY KEY (log_id);



ALTER TABLE ONLY sync_log
    ADD CONSTRAINT "$1" FOREIGN KEY (system_id) REFERENCES sync_system(system_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY sync_log
    ADD CONSTRAINT "$2" FOREIGN KEY (client_id) REFERENCES sync_client(client_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY sync_transaction_log
    ADD CONSTRAINT sync_transaction_log_pkey PRIMARY KEY (transaction_id);



ALTER TABLE ONLY sync_transaction_log
    ADD CONSTRAINT "$1" FOREIGN KEY (log_id) REFERENCES sync_log(log_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY process_log
    ADD CONSTRAINT process_log_pkey PRIMARY KEY (process_id);



ALTER TABLE ONLY process_log
    ADD CONSTRAINT "$1" FOREIGN KEY (system_id) REFERENCES sync_system(system_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY process_log
    ADD CONSTRAINT "$2" FOREIGN KEY (client_id) REFERENCES sync_client(client_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY autoguide_make
    ADD CONSTRAINT autoguide_make_pkey PRIMARY KEY (make_id);



ALTER TABLE ONLY autoguide_model
    ADD CONSTRAINT autoguide_model_pkey PRIMARY KEY (model_id);



ALTER TABLE ONLY autoguide_model
    ADD CONSTRAINT "$1" FOREIGN KEY (make_id) REFERENCES autoguide_make(make_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY autoguide_vehicle
    ADD CONSTRAINT autoguide_vehicle_pkey PRIMARY KEY (vehicle_id);



ALTER TABLE ONLY autoguide_vehicle
    ADD CONSTRAINT "$1" FOREIGN KEY (make_id) REFERENCES autoguide_make(make_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY autoguide_vehicle
    ADD CONSTRAINT "$2" FOREIGN KEY (model_id) REFERENCES autoguide_model(model_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY autoguide_inventory
    ADD CONSTRAINT autoguide_inventory_pkey PRIMARY KEY (inventory_id);



ALTER TABLE ONLY autoguide_inventory
    ADD CONSTRAINT "$1" FOREIGN KEY (vehicle_id) REFERENCES autoguide_vehicle(vehicle_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY autoguide_inventory
    ADD CONSTRAINT "$2" FOREIGN KEY (account_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY autoguide_options
    ADD CONSTRAINT autoguide_options_pkey PRIMARY KEY (option_id);



ALTER TABLE ONLY autoguide_inventory_options
    ADD CONSTRAINT "$1" FOREIGN KEY (inventory_id) REFERENCES autoguide_inventory(inventory_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY autoguide_ad_run
    ADD CONSTRAINT autoguide_ad_run_pkey PRIMARY KEY (ad_run_id);



ALTER TABLE ONLY autoguide_ad_run
    ADD CONSTRAINT "$1" FOREIGN KEY (inventory_id) REFERENCES autoguide_inventory(inventory_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY autoguide_ad_run_types
    ADD CONSTRAINT autoguide_ad_run_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_revenue_types
    ADD CONSTRAINT lookup_revenue_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_revenuedetail_types
    ADD CONSTRAINT lookup_revenuedetail_types_pkey PRIMARY KEY (code);



ALTER TABLE ONLY revenue
    ADD CONSTRAINT revenue_pkey PRIMARY KEY (id);



ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_revenue_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$3" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT revenue_detail_pkey PRIMARY KEY (id);



ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$1" FOREIGN KEY (revenue_id) REFERENCES revenue(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_revenue_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$3" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_task_priority
    ADD CONSTRAINT lookup_task_priority_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_task_loe
    ADD CONSTRAINT lookup_task_loe_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_task_category
    ADD CONSTRAINT lookup_task_category_pkey PRIMARY KEY (code);



ALTER TABLE ONLY task
    ADD CONSTRAINT task_pkey PRIMARY KEY (task_id);



ALTER TABLE ONLY task
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY task
    ADD CONSTRAINT "$2" FOREIGN KEY (priority) REFERENCES lookup_task_priority(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY task
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY task
    ADD CONSTRAINT "$4" FOREIGN KEY (estimatedloetype) REFERENCES lookup_task_loe(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY task
    ADD CONSTRAINT "$5" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY task
    ADD CONSTRAINT "$6" FOREIGN KEY (category_id) REFERENCES lookup_task_category(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY tasklink_contact
    ADD CONSTRAINT "$1" FOREIGN KEY (task_id) REFERENCES task(task_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY tasklink_contact
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY tasklink_ticket
    ADD CONSTRAINT "$1" FOREIGN KEY (task_id) REFERENCES task(task_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY tasklink_ticket
    ADD CONSTRAINT "$2" FOREIGN KEY (ticket_id) REFERENCES ticket(ticketid) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY tasklink_project
    ADD CONSTRAINT "$1" FOREIGN KEY (task_id) REFERENCES task(task_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY tasklink_project
    ADD CONSTRAINT "$2" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY taskcategory_project
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES lookup_task_category(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY taskcategory_project
    ADD CONSTRAINT "$2" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY business_process_component_library
    ADD CONSTRAINT business_process_component_library_pkey PRIMARY KEY (component_id);



ALTER TABLE ONLY business_process_component_result_lookup
    ADD CONSTRAINT business_process_component_result_lookup_pkey PRIMARY KEY (result_id);



ALTER TABLE ONLY business_process_component_result_lookup
    ADD CONSTRAINT "$1" FOREIGN KEY (component_id) REFERENCES business_process_component_library(component_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY business_process_parameter_library
    ADD CONSTRAINT business_process_parameter_library_pkey PRIMARY KEY (parameter_id);



ALTER TABLE ONLY business_process
    ADD CONSTRAINT business_process_pkey PRIMARY KEY (process_id);



ALTER TABLE ONLY business_process
    ADD CONSTRAINT business_process_process_name_key UNIQUE (process_name);



ALTER TABLE ONLY business_process
    ADD CONSTRAINT "$1" FOREIGN KEY (link_module_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY business_process_component
    ADD CONSTRAINT business_process_component_pkey PRIMARY KEY (id);



ALTER TABLE ONLY business_process_component
    ADD CONSTRAINT "$1" FOREIGN KEY (process_id) REFERENCES business_process(process_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY business_process_component
    ADD CONSTRAINT "$2" FOREIGN KEY (component_id) REFERENCES business_process_component_library(component_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY business_process_component
    ADD CONSTRAINT "$3" FOREIGN KEY (parent_id) REFERENCES business_process_component(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY business_process_parameter
    ADD CONSTRAINT business_process_parameter_pkey PRIMARY KEY (id);



ALTER TABLE ONLY business_process_parameter
    ADD CONSTRAINT "$1" FOREIGN KEY (process_id) REFERENCES business_process(process_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY business_process_component_parameter
    ADD CONSTRAINT business_process_component_parameter_pkey PRIMARY KEY (id);



ALTER TABLE ONLY business_process_component_parameter
    ADD CONSTRAINT "$1" FOREIGN KEY (component_id) REFERENCES business_process_component(id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY business_process_component_parameter
    ADD CONSTRAINT "$2" FOREIGN KEY (parameter_id) REFERENCES business_process_parameter_library(parameter_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY business_process_events
    ADD CONSTRAINT business_process_events_pkey PRIMARY KEY (event_id);



ALTER TABLE ONLY business_process_events
    ADD CONSTRAINT "$1" FOREIGN KEY (process_id) REFERENCES business_process(process_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY business_process_log
    ADD CONSTRAINT business_process_log_process_name_key UNIQUE (process_name);



ALTER TABLE ONLY business_process_hook_library
    ADD CONSTRAINT business_process_hook_library_pkey PRIMARY KEY (hook_id);



ALTER TABLE ONLY business_process_hook_library
    ADD CONSTRAINT "$1" FOREIGN KEY (link_module_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY business_process_hook_triggers
    ADD CONSTRAINT business_process_hook_triggers_pkey PRIMARY KEY (trigger_id);



ALTER TABLE ONLY business_process_hook_triggers
    ADD CONSTRAINT "$1" FOREIGN KEY (hook_id) REFERENCES business_process_hook_library(hook_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY business_process_hook
    ADD CONSTRAINT business_process_hook_pkey PRIMARY KEY (id);



ALTER TABLE ONLY business_process_hook
    ADD CONSTRAINT "$1" FOREIGN KEY (trigger_id) REFERENCES business_process_hook_triggers(trigger_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY business_process_hook
    ADD CONSTRAINT "$2" FOREIGN KEY (process_id) REFERENCES business_process(process_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



SELECT pg_catalog.setval ('access_user_id_seq', 0, true);



SELECT pg_catalog.setval ('lookup_industry_code_seq', 20, true);



SELECT pg_catalog.setval ('access_log_id_seq', 1, false);



SELECT pg_catalog.setval ('usage_log_usage_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_contact_types_code_seq', 20, true);



SELECT pg_catalog.setval ('lookup_account_types_code_seq', 9, true);



SELECT pg_catalog.setval ('lookup_department_code_seq', 13, true);



SELECT pg_catalog.setval ('lookup_orgaddress_type_code_seq', 4, true);



SELECT pg_catalog.setval ('lookup_orgemail_types_code_seq', 2, true);



SELECT pg_catalog.setval ('lookup_orgphone_types_code_seq', 2, true);



SELECT pg_catalog.setval ('lookup_instantmessenge_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_employment_type_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_locale_code_seq', 1, false);



SELECT pg_catalog.setval ('lookup_contactaddress__code_seq', 3, true);



SELECT pg_catalog.setval ('lookup_contactemail_ty_code_seq', 3, true);



SELECT pg_catalog.setval ('lookup_contactphone_ty_code_seq', 9, true);



SELECT pg_catalog.setval ('lookup_access_types_code_seq', 8, true);



SELECT pg_catalog.setval ('organization_org_id_seq', 0, true);



SELECT pg_catalog.setval ('contact_contact_id_seq', 1, false);



SELECT pg_catalog.setval ('role_role_id_seq', 9, true);



SELECT pg_catalog.setval ('permission_cate_category_id_seq', 15, true);



SELECT pg_catalog.setval ('permission_permission_id_seq', 75, true);



SELECT pg_catalog.setval ('role_permission_id_seq', 421, true);



SELECT pg_catalog.setval ('lookup_stage_code_seq', 9, true);



SELECT pg_catalog.setval ('lookup_delivery_option_code_seq', 6, true);



SELECT pg_catalog.setval ('news_rec_id_seq', 1, false);



SELECT pg_catalog.setval ('organization_add_address_id_seq', 1, false);



SELECT pg_catalog.setval ('organization__emailaddress__seq', 1, false);



SELECT pg_catalog.setval ('organization_phone_phone_id_seq', 1, false);



SELECT pg_catalog.setval ('contact_address_address_id_seq', 1, false);



SELECT pg_catalog.setval ('contact_email_emailaddress__seq', 1, false);



SELECT pg_catalog.setval ('contact_phone_phone_id_seq', 1, false);



SELECT pg_catalog.setval ('notification_notification_i_seq', 1, false);



SELECT pg_catalog.setval ('cfsinbox_message_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_lists_lookup_id_seq', 13, true);



SELECT pg_catalog.setval ('viewpoint_viewpoint_id_seq', 1, false);



SELECT pg_catalog.setval ('viewpoint_per_vp_permission_seq', 1, false);



SELECT pg_catalog.setval ('report_report_id_seq', 19, true);



SELECT pg_catalog.setval ('report_criteria_criteria_id_seq', 1, false);



SELECT pg_catalog.setval ('report_criteria_parameter_parameter_id_seq', 1, false);



SELECT pg_catalog.setval ('report_queue_queue_id_seq', 1, false);



SELECT pg_catalog.setval ('report_queue_criteria_criteria_id_seq', 1, false);



SELECT pg_catalog.setval ('action_list_code_seq', 1, false);



SELECT pg_catalog.setval ('action_item_code_seq', 1, false);



SELECT pg_catalog.setval ('action_item_log_code_seq', 1, false);



SELECT pg_catalog.setval ('database_version_version_id_seq', 1, true);



SELECT pg_catalog.setval ('lookup_call_types_code_seq', 10, true);



SELECT pg_catalog.setval ('lookup_opportunity_typ_code_seq', 6, true);



SELECT pg_catalog.setval ('opportunity_header_opp_id_seq', 1, false);



SELECT pg_catalog.setval ('opportunity_component_id_seq', 1, false);



SELECT pg_catalog.setval ('call_log_call_id_seq', 1, false);



SELECT pg_catalog.setval ('ticket_level_code_seq', 5, true);



SELECT pg_catalog.setval ('ticket_severity_code_seq', 3, true);



SELECT pg_catalog.setval ('lookup_ticketsource_code_seq', 5, true);



SELECT pg_catalog.setval ('ticket_priority_code_seq', 3, true);



SELECT pg_catalog.setval ('ticket_category_id_seq', 5, true);



SELECT pg_catalog.setval ('ticket_category_draft_id_seq', 1, false);



SELECT pg_catalog.setval ('ticket_ticketid_seq', 1, false);



SELECT pg_catalog.setval ('ticketlog_id_seq', 1, false);



SELECT pg_catalog.setval ('module_field_categorylin_id_seq', 3, true);



SELECT pg_catalog.setval ('custom_field_ca_category_id_seq', 1, false);



SELECT pg_catalog.setval ('custom_field_group_group_id_seq', 1, false);



SELECT pg_catalog.setval ('custom_field_info_field_id_seq', 1, false);



SELECT pg_catalog.setval ('custom_field_lookup_code_seq', 1, false);



SELECT pg_catalog.setval ('custom_field_reco_record_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_project_activit_code_seq', 10, true);



SELECT pg_catalog.setval ('lookup_project_priorit_code_seq', 3, true);



SELECT pg_catalog.setval ('lookup_project_issues_code_seq', 15, true);



SELECT pg_catalog.setval ('lookup_project_status_code_seq', 6, true);



SELECT pg_catalog.setval ('lookup_project_loe_code_seq', 5, true);



SELECT pg_catalog.setval ('projects_project_id_seq', 1, false);



SELECT pg_catalog.setval ('project_requi_requirement_i_seq', 1, false);



SELECT pg_catalog.setval ('project_assig_assignment_id_seq', 1, false);



SELECT pg_catalog.setval ('project_assignmen_status_id_seq', 1, false);



SELECT pg_catalog.setval ('project_issues_issue_id_seq', 1, false);



SELECT pg_catalog.setval ('project_issue_repl_reply_id_seq', 1, false);



SELECT pg_catalog.setval ('project_folders_folder_id_seq', 1, false);



SELECT pg_catalog.setval ('project_files_item_id_seq', 1, false);



SELECT pg_catalog.setval ('saved_criterialist_id_seq', 1, false);



SELECT pg_catalog.setval ('campaign_campaign_id_seq', 1, false);



SELECT pg_catalog.setval ('campaign_run_id_seq', 1, false);



SELECT pg_catalog.setval ('excluded_recipient_id_seq', 1, false);



SELECT pg_catalog.setval ('active_campaign_groups_id_seq', 1, false);



SELECT pg_catalog.setval ('scheduled_recipient_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_survey_types_code_seq', 4, true);



SELECT pg_catalog.setval ('survey_survey_id_seq', 1, false);



SELECT pg_catalog.setval ('survey_question_question_id_seq', 1, false);



SELECT pg_catalog.setval ('survey_items_item_id_seq', 1, false);



SELECT pg_catalog.setval ('active_survey_active_survey_seq', 1, false);



SELECT pg_catalog.setval ('active_survey_q_question_id_seq', 1, false);



SELECT pg_catalog.setval ('active_survey_items_item_id_seq', 1, false);



SELECT pg_catalog.setval ('active_survey_r_response_id_seq', 1, false);



SELECT pg_catalog.setval ('active_survey_ans_answer_id_seq', 1, false);



SELECT pg_catalog.setval ('active_survey_answer_ite_id_seq', 1, false);



SELECT pg_catalog.setval ('active_survey_answer_avg_id_seq', 1, false);



SELECT pg_catalog.setval ('field_types_id_seq', 18, true);



SELECT pg_catalog.setval ('search_fields_id_seq', 11, true);



SELECT pg_catalog.setval ('message_id_seq', 1, false);



SELECT pg_catalog.setval ('message_template_id_seq', 1, false);



SELECT pg_catalog.setval ('help_module_module_id_seq', 13, true);



SELECT pg_catalog.setval ('help_contents_help_id_seq', 311, true);



SELECT pg_catalog.setval ('help_tableof_contents_content_id_seq', 97, true);



SELECT pg_catalog.setval ('help_tableofcontentitem_links_link_id_seq', 87, true);



SELECT pg_catalog.setval ('lookup_help_features_code_seq', 1, false);



SELECT pg_catalog.setval ('help_features_feature_id_seq', 547, true);



SELECT pg_catalog.setval ('help_related_links_relatedlink_id_seq', 1, false);



SELECT pg_catalog.setval ('help_faqs_faq_id_seq', 1, false);



SELECT pg_catalog.setval ('help_business_rules_rule_id_seq', 4, true);



SELECT pg_catalog.setval ('help_notes_note_id_seq', 1, false);



SELECT pg_catalog.setval ('help_tips_tip_id_seq', 2, true);



SELECT pg_catalog.setval ('sync_client_client_id_seq', 1, false);



SELECT pg_catalog.setval ('sync_system_system_id_seq', 5, true);



SELECT pg_catalog.setval ('sync_table_table_id_seq', 199, true);



SELECT pg_catalog.setval ('sync_log_log_id_seq', 1, false);



SELECT pg_catalog.setval ('sync_transact_transaction_i_seq', 1, false);



SELECT pg_catalog.setval ('process_log_process_id_seq', 1, false);



SELECT pg_catalog.setval ('autoguide_make_make_id_seq', 1, false);



SELECT pg_catalog.setval ('autoguide_model_model_id_seq', 1, false);



SELECT pg_catalog.setval ('autoguide_vehicl_vehicle_id_seq', 1, false);



SELECT pg_catalog.setval ('autoguide_inve_inventory_id_seq', 1, false);



SELECT pg_catalog.setval ('autoguide_options_option_id_seq', 30, true);



SELECT pg_catalog.setval ('autoguide_ad_run_ad_run_id_seq', 1, false);



SELECT pg_catalog.setval ('autoguide_ad_run_types_code_seq', 3, true);



SELECT pg_catalog.setval ('lookup_revenue_types_code_seq', 1, true);



SELECT pg_catalog.setval ('lookup_revenuedetail_t_code_seq', 1, false);



SELECT pg_catalog.setval ('revenue_id_seq', 1, false);



SELECT pg_catalog.setval ('revenue_detail_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_task_priority_code_seq', 5, true);



SELECT pg_catalog.setval ('lookup_task_loe_code_seq', 5, true);



SELECT pg_catalog.setval ('lookup_task_category_code_seq', 1, false);



SELECT pg_catalog.setval ('task_task_id_seq', 1, false);



SELECT pg_catalog.setval ('business_process_com_lb_id_seq', 7, true);



SELECT pg_catalog.setval ('business_process_comp_re_id_seq', 3, true);



SELECT pg_catalog.setval ('business_process_pa_lib_id_seq', 23, true);



SELECT pg_catalog.setval ('business_process_process_id_seq', 2, true);



SELECT pg_catalog.setval ('business_process_compone_id_seq', 8, true);



SELECT pg_catalog.setval ('business_process_param_id_seq', 1, false);



SELECT pg_catalog.setval ('business_process_comp_pa_id_seq', 23, true);



SELECT pg_catalog.setval ('business_process_e_event_id_seq', 1, false);



SELECT pg_catalog.setval ('business_process_hl_hook_id_seq', 1, true);



SELECT pg_catalog.setval ('business_process_ho_trig_id_seq', 2, true);



SELECT pg_catalog.setval ('business_process_ho_hook_id_seq', 2, true);

COMMIT;

