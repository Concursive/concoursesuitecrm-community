--- NOTICE Add BEGIN WORK and COMMIT as well as the CFS2GK tables

BEGIN WORK;

--
-- PostgreSQL database dump
--

SET search_path = public, pg_catalog;

--
-- TOC entry 2 (OID 476839)
-- Name: sites; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 4 (OID 476857)
-- Name: events; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 6 (OID 476877)
-- Name: events_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE events_log (
    log_id serial NOT NULL,
    event_id integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    status integer,
    message text
);

--
-- Data for TOC entry 15 (OID 476857)
-- Name: events; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO events VALUES (1, '0', '*/5', '*', '*', '*', '*', '*', 'org.aspcfs.apps.notifier.Notifier#doTask', '${FILEPATH}', 'true', true, '2003-11-13 08:11:37.411');
INSERT INTO events VALUES (2, '0', '*/1', '*', '*', '*', '*', '*', 'org.aspcfs.apps.reportRunner.ReportRunner#doTask', '${FILEPATH}', 'true', true, '2003-11-13 08:11:37.411');
INSERT INTO events VALUES (3, '0', '0', '*/12', '*', '*', '*', '*', 'org.aspcfs.apps.reportRunner.ReportCleanup#doTask', '${FILEPATH}', 'true', true, '2003-11-13 08:11:37.411');
INSERT INTO events VALUES (4, '0', '0', '0', '*', '*', '*', '*', 'org.aspcfs.modules.service.tasks.GetURL#doTask', 'http://${WEBSERVER.URL}/ProcessSystem.do?command=ClearGraphData', 'true', true, '2003-11-13 08:11:37.411');

--
-- TOC entry 11 (OID 476853)
-- Name: sites_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sites
    ADD CONSTRAINT sites_pkey PRIMARY KEY (site_id);


--
-- TOC entry 12 (OID 476873)
-- Name: events_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY events
    ADD CONSTRAINT events_pkey PRIMARY KEY (event_id);


--
-- TOC entry 13 (OID 476884)
-- Name: events_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY events_log
    ADD CONSTRAINT events_log_pkey PRIMARY KEY (log_id);


--
-- TOC entry 17 (OID 476886)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY events_log
    ADD CONSTRAINT "$1" FOREIGN KEY (event_id) REFERENCES events(event_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 8 (OID 476837)
-- Name: sites_site_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval ('sites_site_id_seq', 1, false);


--
-- TOC entry 9 (OID 476855)
-- Name: events_event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval ('events_event_id_seq', 4, true);


--
-- TOC entry 10 (OID 476875)
-- Name: events_log_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval ('events_log_log_id_seq', 1, false);


--
-- PostgreSQL database dump
--

SET search_path = public, pg_catalog;

--
-- TOC entry 2 (OID 31002)
-- Name: access_user_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE access_user_id_seq
    START 0
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 0
    CACHE 1;


--
-- TOC entry 110 (OID 31004)
-- Name: access; Type: TABLE; Schema: public; Owner: matt
--

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
    expires date,
    alias integer DEFAULT -1,
    assistant integer DEFAULT -1,
    enabled boolean DEFAULT true NOT NULL
);


--
-- TOC entry 111 (OID 31026)
-- Name: lookup_industry; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_industry (
    code serial NOT NULL,
    order_id integer,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 112 (OID 31036)
-- Name: access_log; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE access_log (
    id serial NOT NULL,
    user_id integer NOT NULL,
    username character varying(80) NOT NULL,
    ip character varying(15),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    browser character varying(255)
);


--
-- TOC entry 113 (OID 31048)
-- Name: usage_log; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE usage_log (
    usage_id serial NOT NULL,
    entered timestamp(6) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer,
    "action" integer NOT NULL,
    record_id integer,
    record_size integer
);


--
-- TOC entry 114 (OID 31056)
-- Name: lookup_contact_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_contact_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    user_id integer,
    category integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 115 (OID 31071)
-- Name: lookup_account_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_account_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 116 (OID 31079)
-- Name: state; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE state (
    state_code character(2) NOT NULL,
    state character varying(80) NOT NULL
);


--
-- TOC entry 117 (OID 31085)
-- Name: lookup_department; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_department (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 4 (OID 31093)
-- Name: lookup_orgaddress_type_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE lookup_orgaddress_type_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 118 (OID 31095)
-- Name: lookup_orgaddress_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_orgaddress_types (
    code integer DEFAULT nextval('lookup_orgaddress_type_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 119 (OID 31105)
-- Name: lookup_orgemail_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_orgemail_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 120 (OID 31115)
-- Name: lookup_orgphone_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_orgphone_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 6 (OID 31123)
-- Name: lookup_instantmessenge_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE lookup_instantmessenge_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 121 (OID 31125)
-- Name: lookup_instantmessenger_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_instantmessenger_types (
    code integer DEFAULT nextval('lookup_instantmessenge_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 8 (OID 31133)
-- Name: lookup_employment_type_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE lookup_employment_type_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 122 (OID 31135)
-- Name: lookup_employment_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_employment_types (
    code integer DEFAULT nextval('lookup_employment_type_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 123 (OID 31145)
-- Name: lookup_locale; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_locale (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 10 (OID 31153)
-- Name: lookup_contactaddress__code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE lookup_contactaddress__code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 124 (OID 31155)
-- Name: lookup_contactaddress_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_contactaddress_types (
    code integer DEFAULT nextval('lookup_contactaddress__code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 12 (OID 31163)
-- Name: lookup_contactemail_ty_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE lookup_contactemail_ty_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 125 (OID 31165)
-- Name: lookup_contactemail_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_contactemail_types (
    code integer DEFAULT nextval('lookup_contactemail_ty_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 14 (OID 31173)
-- Name: lookup_contactphone_ty_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE lookup_contactphone_ty_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 126 (OID 31175)
-- Name: lookup_contactphone_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_contactphone_types (
    code integer DEFAULT nextval('lookup_contactphone_ty_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 127 (OID 31185)
-- Name: lookup_access_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_access_types (
    code serial NOT NULL,
    link_module_id integer NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer,
    enabled boolean DEFAULT true,
    rule_id integer NOT NULL
);


--
-- TOC entry 16 (OID 31192)
-- Name: organization_org_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE organization_org_id_seq
    START 0
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 0
    CACHE 1;


--
-- TOC entry 128 (OID 31194)
-- Name: organization; Type: TABLE; Schema: public; Owner: matt
--

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
    contract_end date,
    alertdate date,
    alert character varying(100),
    custom_data text,
    namesalutation character varying(80),
    namelast character varying(80),
    namefirst character varying(80),
    namemiddle character varying(80),
    namesuffix character varying(80)
);


--
-- TOC entry 129 (OID 31225)
-- Name: contact; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 130 (OID 31282)
-- Name: role; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 18 (OID 31299)
-- Name: permission_cate_category_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE permission_cate_category_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 131 (OID 31301)
-- Name: permission_category; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 132 (OID 31318)
-- Name: permission; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 133 (OID 31338)
-- Name: role_permission; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE role_permission (
    id serial NOT NULL,
    role_id integer NOT NULL,
    permission_id integer NOT NULL,
    role_view boolean DEFAULT false NOT NULL,
    role_add boolean DEFAULT false NOT NULL,
    role_edit boolean DEFAULT false NOT NULL,
    role_delete boolean DEFAULT false NOT NULL
);


--
-- TOC entry 134 (OID 31357)
-- Name: lookup_stage; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_stage (
    code serial NOT NULL,
    order_id integer,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 20 (OID 31365)
-- Name: lookup_delivery_option_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE lookup_delivery_option_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 135 (OID 31367)
-- Name: lookup_delivery_options; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_delivery_options (
    code integer DEFAULT nextval('lookup_delivery_option_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 136 (OID 31377)
-- Name: news; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 22 (OID 31390)
-- Name: organization_add_address_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE organization_add_address_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 137 (OID 31392)
-- Name: organization_address; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 24 (OID 31415)
-- Name: organization__emailaddress__seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE organization__emailaddress__seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 138 (OID 31417)
-- Name: organization_emailaddress; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 26 (OID 31440)
-- Name: organization_phone_phone_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE organization_phone_phone_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 139 (OID 31442)
-- Name: organization_phone; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 140 (OID 31467)
-- Name: contact_address; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 28 (OID 31490)
-- Name: contact_email_emailaddress__seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE contact_email_emailaddress__seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 141 (OID 31492)
-- Name: contact_emailaddress; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 142 (OID 31517)
-- Name: contact_phone; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 30 (OID 31540)
-- Name: notification_notification_i_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE notification_notification_i_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 143 (OID 31542)
-- Name: notification; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 144 (OID 31554)
-- Name: cfsinbox_message; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 145 (OID 31575)
-- Name: cfsinbox_messagelink; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE cfsinbox_messagelink (
    id integer NOT NULL,
    sent_to integer NOT NULL,
    status integer DEFAULT 0 NOT NULL,
    viewed timestamp(3) without time zone,
    enabled boolean DEFAULT 't' NOT NULL,
    sent_from integer NOT NULL
);


--
-- TOC entry 146 (OID 31591)
-- Name: account_type_levels; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE account_type_levels (
    org_id integer NOT NULL,
    type_id integer NOT NULL,
    "level" integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);


--
-- TOC entry 147 (OID 31603)
-- Name: contact_type_levels; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE contact_type_levels (
    contact_id integer NOT NULL,
    type_id integer NOT NULL,
    "level" integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);


--
-- TOC entry 148 (OID 31617)
-- Name: lookup_lists_lookup; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 149 (OID 31633)
-- Name: viewpoint; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 32 (OID 31657)
-- Name: viewpoint_per_vp_permission_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE viewpoint_per_vp_permission_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 150 (OID 31659)
-- Name: viewpoint_permission; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE viewpoint_permission (
    vp_permission_id integer DEFAULT nextval('viewpoint_per_vp_permission_seq'::text) NOT NULL,
    viewpoint_id integer NOT NULL,
    permission_id integer NOT NULL,
    viewpoint_view boolean DEFAULT false NOT NULL,
    viewpoint_add boolean DEFAULT false NOT NULL,
    viewpoint_edit boolean DEFAULT false NOT NULL,
    viewpoint_delete boolean DEFAULT false NOT NULL
);


--
-- TOC entry 151 (OID 31678)
-- Name: report; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 152 (OID 31709)
-- Name: report_criteria; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 153 (OID 31735)
-- Name: report_criteria_parameter; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE report_criteria_parameter (
    parameter_id serial NOT NULL,
    criteria_id integer NOT NULL,
    parameter character varying(255) NOT NULL,
    value text
);


--
-- TOC entry 154 (OID 31749)
-- Name: report_queue; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 155 (OID 31768)
-- Name: report_queue_criteria; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE report_queue_criteria (
    criteria_id serial NOT NULL,
    queue_id integer NOT NULL,
    parameter character varying(255) NOT NULL,
    value text
);


--
-- TOC entry 34 (OID 31780)
-- Name: action_list_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE action_list_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 156 (OID 31782)
-- Name: action_list; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 36 (OID 31802)
-- Name: action_item_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE action_item_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 157 (OID 31804)
-- Name: action_item; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 38 (OID 31824)
-- Name: action_item_log_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE action_item_log_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 158 (OID 31826)
-- Name: action_item_log; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 159 (OID 31848)
-- Name: database_version; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE database_version (
    version_id serial NOT NULL,
    script_filename character varying(255) NOT NULL,
    script_version character varying(255) NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);


--
-- TOC entry 160 (OID 31973)
-- Name: lookup_call_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_call_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 40 (OID 31981)
-- Name: lookup_opportunity_typ_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE lookup_opportunity_typ_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 161 (OID 31983)
-- Name: lookup_opportunity_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_opportunity_types (
    code integer DEFAULT nextval('lookup_opportunity_typ_code_seq'::text) NOT NULL,
    order_id integer,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 162 (OID 31993)
-- Name: opportunity_header; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 163 (OID 32012)
-- Name: opportunity_component; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE opportunity_component (
    id serial NOT NULL,
    opp_id integer,
    "owner" integer NOT NULL,
    description character varying(80),
    closedate date NOT NULL,
    closeprob double precision,
    terms double precision,
    units character(1),
    lowvalue double precision,
    guessvalue double precision,
    highvalue double precision,
    stage integer,
    stagedate date DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    commission double precision,
    "type" character(1),
    alertdate date,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    closed timestamp without time zone,
    alert character varying(100),
    enabled boolean DEFAULT true NOT NULL,
    notes text
);


--
-- TOC entry 164 (OID 32046)
-- Name: opportunity_component_levels; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE opportunity_component_levels (
    opp_id integer NOT NULL,
    type_id integer NOT NULL,
    "level" integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);


--
-- TOC entry 165 (OID 32060)
-- Name: call_log; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE call_log (
    call_id serial NOT NULL,
    org_id integer,
    contact_id integer,
    opp_id integer,
    call_type_id integer,
    length integer,
    subject character varying(255),
    notes text,
    followup_date date,
    alertdate date,
    followup_notes text,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    alert character varying(100)
);


--
-- TOC entry 166 (OID 32113)
-- Name: ticket_level; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE ticket_level (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 167 (OID 32125)
-- Name: ticket_severity; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE ticket_severity (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    style text DEFAULT '' NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 168 (OID 32141)
-- Name: lookup_ticketsource; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_ticketsource (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 169 (OID 32153)
-- Name: ticket_priority; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE ticket_priority (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    style text DEFAULT '' NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 170 (OID 32169)
-- Name: ticket_category; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 171 (OID 32184)
-- Name: ticket_category_draft; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 172 (OID 32200)
-- Name: ticket; Type: TABLE; Schema: public; Owner: matt
--

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
    custom_data text
);


--
-- TOC entry 173 (OID 32254)
-- Name: ticketlog; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 42 (OID 32312)
-- Name: module_field_categorylin_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE module_field_categorylin_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 174 (OID 32314)
-- Name: module_field_categorylink; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE module_field_categorylink (
    id integer DEFAULT nextval('module_field_categorylin_id_seq'::text) NOT NULL,
    module_id integer NOT NULL,
    category_id integer NOT NULL,
    "level" integer DEFAULT 0,
    description text,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone
);


--
-- TOC entry 44 (OID 32330)
-- Name: custom_field_ca_category_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE custom_field_ca_category_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 175 (OID 32332)
-- Name: custom_field_category; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 46 (OID 32352)
-- Name: custom_field_group_group_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE custom_field_group_group_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 176 (OID 32354)
-- Name: custom_field_group; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 177 (OID 32373)
-- Name: custom_field_info; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 178 (OID 32394)
-- Name: custom_field_lookup; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 48 (OID 32408)
-- Name: custom_field_reco_record_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE custom_field_reco_record_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 179 (OID 32410)
-- Name: custom_field_record; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 180 (OID 32431)
-- Name: custom_field_data; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE custom_field_data (
    record_id integer NOT NULL,
    field_id integer NOT NULL,
    selected_item_id integer DEFAULT 0,
    entered_value text,
    entered_number integer,
    entered_float double precision
);


--
-- TOC entry 50 (OID 32446)
-- Name: lookup_project_activit_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE lookup_project_activit_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 181 (OID 32448)
-- Name: lookup_project_activity; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_project_activity (
    code integer DEFAULT nextval('lookup_project_activit_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL,
    template_id integer DEFAULT 0
);


--
-- TOC entry 52 (OID 32458)
-- Name: lookup_project_priorit_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE lookup_project_priorit_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 182 (OID 32460)
-- Name: lookup_project_priority; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 183 (OID 32471)
-- Name: lookup_project_issues; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_project_issues (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 184 (OID 32482)
-- Name: lookup_project_status; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 185 (OID 32493)
-- Name: lookup_project_loe; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_project_loe (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    base_value integer DEFAULT 0 NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 186 (OID 32505)
-- Name: projects; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 54 (OID 32526)
-- Name: project_requi_requirement_i_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE project_requi_requirement_i_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 187 (OID 32528)
-- Name: project_requirements; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 56 (OID 32566)
-- Name: project_assig_assignment_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE project_assig_assignment_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 188 (OID 32568)
-- Name: project_assignments; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 58 (OID 32623)
-- Name: project_assignmen_status_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE project_assignmen_status_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 189 (OID 32625)
-- Name: project_assignments_status; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE project_assignments_status (
    status_id integer DEFAULT nextval('project_assignmen_status_id_seq'::text) NOT NULL,
    assignment_id integer NOT NULL,
    user_id integer NOT NULL,
    description text NOT NULL,
    status_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone
);


--
-- TOC entry 190 (OID 32644)
-- Name: project_issues; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 60 (OID 32674)
-- Name: project_issue_repl_reply_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE project_issue_repl_reply_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 191 (OID 32676)
-- Name: project_issue_replies; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 192 (OID 32701)
-- Name: project_folders; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE project_folders (
    folder_id serial NOT NULL,
    link_module_id integer NOT NULL,
    link_item_id integer NOT NULL,
    subject character varying(255) NOT NULL,
    description text,
    parent integer
);


--
-- TOC entry 193 (OID 32711)
-- Name: project_files; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 194 (OID 32738)
-- Name: project_files_version; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 195 (OID 32761)
-- Name: project_files_download; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE project_files_download (
    item_id integer NOT NULL,
    "version" double precision DEFAULT 0,
    user_download_id integer,
    download_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);


--
-- TOC entry 196 (OID 32773)
-- Name: project_team; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE project_team (
    project_id integer NOT NULL,
    user_id integer NOT NULL,
    userlevel integer,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    modifiedby integer NOT NULL
);


--
-- TOC entry 197 (OID 32834)
-- Name: saved_criterialist; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 198 (OID 32857)
-- Name: campaign; Type: TABLE; Schema: public; Owner: matt
--

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
    active_date date,
    send_method_id integer DEFAULT -1 NOT NULL,
    inactive_date date,
    approval_date timestamp(3) without time zone,
    approvedby integer,
    enabled boolean DEFAULT true NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    "type" integer DEFAULT 1
);


--
-- TOC entry 199 (OID 32887)
-- Name: campaign_run; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 200 (OID 32904)
-- Name: excluded_recipient; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE excluded_recipient (
    id serial NOT NULL,
    campaign_id integer NOT NULL,
    contact_id integer NOT NULL
);


--
-- TOC entry 201 (OID 32917)
-- Name: campaign_list_groups; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE campaign_list_groups (
    campaign_id integer NOT NULL,
    group_id integer NOT NULL
);


--
-- TOC entry 202 (OID 32929)
-- Name: active_campaign_groups; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE active_campaign_groups (
    id serial NOT NULL,
    campaign_id integer NOT NULL,
    groupname character varying(80) NOT NULL,
    groupcriteria text
);


--
-- TOC entry 203 (OID 32943)
-- Name: scheduled_recipient; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 204 (OID 32962)
-- Name: lookup_survey_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_survey_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 205 (OID 32972)
-- Name: survey; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 206 (OID 32994)
-- Name: campaign_survey_link; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE campaign_survey_link (
    campaign_id integer,
    survey_id integer
);


--
-- TOC entry 62 (OID 33004)
-- Name: survey_question_question_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE survey_question_question_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 207 (OID 33006)
-- Name: survey_questions; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE survey_questions (
    question_id integer DEFAULT nextval('survey_question_question_id_seq'::text) NOT NULL,
    survey_id integer NOT NULL,
    "type" integer NOT NULL,
    description character varying(255),
    required boolean DEFAULT false NOT NULL,
    "position" integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 208 (OID 33023)
-- Name: survey_items; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE survey_items (
    item_id serial NOT NULL,
    question_id integer NOT NULL,
    "type" integer DEFAULT -1,
    description character varying(255)
);


--
-- TOC entry 64 (OID 33033)
-- Name: active_survey_active_survey_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE active_survey_active_survey_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 209 (OID 33035)
-- Name: active_survey; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 66 (OID 33063)
-- Name: active_survey_q_question_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE active_survey_q_question_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 210 (OID 33065)
-- Name: active_survey_questions; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 68 (OID 33088)
-- Name: active_survey_items_item_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE active_survey_items_item_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 211 (OID 33090)
-- Name: active_survey_items; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE active_survey_items (
    item_id integer DEFAULT nextval('active_survey_items_item_id_seq'::text) NOT NULL,
    question_id integer NOT NULL,
    "type" integer DEFAULT -1,
    description character varying(255)
);


--
-- TOC entry 70 (OID 33100)
-- Name: active_survey_r_response_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE active_survey_r_response_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 212 (OID 33102)
-- Name: active_survey_responses; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE active_survey_responses (
    response_id integer DEFAULT nextval('active_survey_r_response_id_seq'::text) NOT NULL,
    active_survey_id integer NOT NULL,
    contact_id integer DEFAULT -1 NOT NULL,
    unique_code character varying(255),
    ip_address character varying(15) NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);


--
-- TOC entry 72 (OID 33113)
-- Name: active_survey_ans_answer_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE active_survey_ans_answer_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 213 (OID 33115)
-- Name: active_survey_answers; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE active_survey_answers (
    answer_id integer DEFAULT nextval('active_survey_ans_answer_id_seq'::text) NOT NULL,
    response_id integer NOT NULL,
    question_id integer NOT NULL,
    comments text,
    quant_ans integer DEFAULT -1,
    text_ans text
);


--
-- TOC entry 74 (OID 33132)
-- Name: active_survey_answer_ite_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE active_survey_answer_ite_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 214 (OID 33134)
-- Name: active_survey_answer_items; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE active_survey_answer_items (
    id integer DEFAULT nextval('active_survey_answer_ite_id_seq'::text) NOT NULL,
    item_id integer NOT NULL,
    answer_id integer NOT NULL,
    comments text
);


--
-- TOC entry 76 (OID 33150)
-- Name: active_survey_answer_avg_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE active_survey_answer_avg_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 215 (OID 33152)
-- Name: active_survey_answer_avg; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE active_survey_answer_avg (
    id integer DEFAULT nextval('active_survey_answer_avg_id_seq'::text) NOT NULL,
    question_id integer NOT NULL,
    item_id integer NOT NULL,
    total integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 216 (OID 33168)
-- Name: field_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE field_types (
    id serial NOT NULL,
    data_typeid integer DEFAULT -1 NOT NULL,
    data_type character varying(20),
    "operator" character varying(50),
    display_text character varying(50),
    enabled boolean DEFAULT true NOT NULL
);


--
-- TOC entry 217 (OID 33177)
-- Name: search_fields; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 218 (OID 33187)
-- Name: message; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 219 (OID 33212)
-- Name: message_template; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 220 (OID 33228)
-- Name: saved_criteriaelement; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE saved_criteriaelement (
    id integer NOT NULL,
    field integer NOT NULL,
    "operator" character varying(50) NOT NULL,
    operatorid integer NOT NULL,
    value character varying(80) NOT NULL,
    source integer DEFAULT -1 NOT NULL
);


--
-- TOC entry 221 (OID 33286)
-- Name: help_module; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE help_module (
    module_id serial NOT NULL,
    category_id integer,
    module_brief_description text,
    module_detail_description text
);


--
-- TOC entry 222 (OID 33300)
-- Name: help_contents; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 223 (OID 33341)
-- Name: help_tableof_contents; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 224 (OID 33375)
-- Name: help_tableofcontentitem_links; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 225 (OID 33401)
-- Name: lookup_help_features; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_help_features (
    code serial NOT NULL,
    description character varying(1000) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 226 (OID 33414)
-- Name: help_features; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 227 (OID 33448)
-- Name: help_related_links; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 228 (OID 33474)
-- Name: help_faqs; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 229 (OID 33503)
-- Name: help_business_rules; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 230 (OID 33532)
-- Name: help_notes; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 231 (OID 33561)
-- Name: help_tips; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 232 (OID 33586)
-- Name: sync_client; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 233 (OID 33595)
-- Name: sync_system; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE sync_system (
    system_id serial NOT NULL,
    application_name character varying(255),
    enabled boolean DEFAULT true
);


--
-- TOC entry 234 (OID 33603)
-- Name: sync_table; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 235 (OID 33619)
-- Name: sync_map; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE sync_map (
    client_id integer NOT NULL,
    table_id integer NOT NULL,
    record_id integer NOT NULL,
    cuid integer NOT NULL,
    complete boolean DEFAULT false,
    status_date timestamp(3) without time zone
);


--
-- TOC entry 236 (OID 33631)
-- Name: sync_conflict_log; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE sync_conflict_log (
    client_id integer NOT NULL,
    table_id integer NOT NULL,
    record_id integer NOT NULL,
    status_date timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);


--
-- TOC entry 237 (OID 33644)
-- Name: sync_log; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE sync_log (
    log_id serial NOT NULL,
    system_id integer NOT NULL,
    client_id integer NOT NULL,
    ip character varying(15),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);


--
-- TOC entry 78 (OID 33658)
-- Name: sync_transact_transaction_i_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE sync_transact_transaction_i_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 238 (OID 33660)
-- Name: sync_transaction_log; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 239 (OID 33674)
-- Name: process_log; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 240 (OID 33897)
-- Name: autoguide_make; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE autoguide_make (
    make_id serial NOT NULL,
    make_name character varying(30),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);


--
-- TOC entry 241 (OID 33906)
-- Name: autoguide_model; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE autoguide_model (
    model_id serial NOT NULL,
    make_id integer NOT NULL,
    model_name character varying(50),
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);


--
-- TOC entry 80 (OID 33917)
-- Name: autoguide_vehicl_vehicle_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE autoguide_vehicl_vehicle_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 242 (OID 33919)
-- Name: autoguide_vehicle; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 82 (OID 33934)
-- Name: autoguide_inve_inventory_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE autoguide_inve_inventory_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 243 (OID 33936)
-- Name: autoguide_inventory; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 84 (OID 33953)
-- Name: autoguide_options_option_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE autoguide_options_option_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 244 (OID 33955)
-- Name: autoguide_options; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE autoguide_options (
    option_id integer DEFAULT nextval('autoguide_options_option_id_seq'::text) NOT NULL,
    option_name character varying(20) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);


--
-- TOC entry 245 (OID 33965)
-- Name: autoguide_inventory_options; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE autoguide_inventory_options (
    inventory_id integer NOT NULL,
    option_id integer NOT NULL
);


--
-- TOC entry 246 (OID 33974)
-- Name: autoguide_ad_run; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 86 (OID 33987)
-- Name: autoguide_ad_run_types_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE autoguide_ad_run_types_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 247 (OID 33989)
-- Name: autoguide_ad_run_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE autoguide_ad_run_types (
    code integer DEFAULT nextval('autoguide_ad_run_types_code_seq'::text) NOT NULL,
    description character varying(20) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT false,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL
);


--
-- TOC entry 248 (OID 34034)
-- Name: lookup_revenue_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_revenue_types (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 88 (OID 34042)
-- Name: lookup_revenuedetail_t_code_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE lookup_revenuedetail_t_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 249 (OID 34044)
-- Name: lookup_revenuedetail_types; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_revenuedetail_types (
    code integer DEFAULT nextval('lookup_revenuedetail_t_code_seq'::text) NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 250 (OID 34054)
-- Name: revenue; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 251 (OID 34087)
-- Name: revenue_detail; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 252 (OID 34118)
-- Name: lookup_task_priority; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_task_priority (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 253 (OID 34128)
-- Name: lookup_task_loe; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_task_loe (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 254 (OID 34138)
-- Name: lookup_task_category; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE lookup_task_category (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);


--
-- TOC entry 255 (OID 34148)
-- Name: task; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE task (
    task_id serial NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    priority integer NOT NULL,
    description character varying(80),
    duedate date,
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


--
-- TOC entry 256 (OID 34185)
-- Name: tasklink_contact; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE tasklink_contact (
    task_id integer NOT NULL,
    contact_id integer NOT NULL
);


--
-- TOC entry 257 (OID 34195)
-- Name: tasklink_ticket; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE tasklink_ticket (
    task_id integer NOT NULL,
    ticket_id integer NOT NULL
);


--
-- TOC entry 258 (OID 34205)
-- Name: tasklink_project; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE tasklink_project (
    task_id integer NOT NULL,
    project_id integer NOT NULL
);


--
-- TOC entry 259 (OID 34215)
-- Name: taskcategory_project; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE taskcategory_project (
    category_id integer NOT NULL,
    project_id integer NOT NULL
);


--
-- TOC entry 90 (OID 34235)
-- Name: business_process_com_lb_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE business_process_com_lb_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 260 (OID 34237)
-- Name: business_process_component_library; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE business_process_component_library (
    component_id integer DEFAULT nextval('business_process_com_lb_id_seq'::text) NOT NULL,
    component_name character varying(255) NOT NULL,
    type_id integer NOT NULL,
    class_name character varying(255) NOT NULL,
    description character varying(510),
    enabled boolean DEFAULT true NOT NULL
);


--
-- TOC entry 92 (OID 34246)
-- Name: business_process_comp_re_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE business_process_comp_re_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 261 (OID 34248)
-- Name: business_process_component_result_lookup; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE business_process_component_result_lookup (
    result_id integer DEFAULT nextval('business_process_comp_re_id_seq'::text) NOT NULL,
    component_id integer NOT NULL,
    return_id integer NOT NULL,
    description character varying(255),
    "level" integer DEFAULT 0 NOT NULL,
    enabled boolean DEFAULT true NOT NULL
);


--
-- TOC entry 94 (OID 34259)
-- Name: business_process_pa_lib_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE business_process_pa_lib_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 262 (OID 34261)
-- Name: business_process_parameter_library; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE business_process_parameter_library (
    parameter_id integer DEFAULT nextval('business_process_pa_lib_id_seq'::text) NOT NULL,
    component_id integer,
    param_name character varying(255),
    description character varying(510),
    default_value character varying(4000),
    enabled boolean DEFAULT true NOT NULL
);


--
-- TOC entry 263 (OID 34272)
-- Name: business_process; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 96 (OID 34288)
-- Name: business_process_compone_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE business_process_compone_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 264 (OID 34290)
-- Name: business_process_component; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE business_process_component (
    id integer DEFAULT nextval('business_process_compone_id_seq'::text) NOT NULL,
    process_id integer NOT NULL,
    component_id integer NOT NULL,
    parent_id integer,
    parent_result_id integer,
    enabled boolean DEFAULT true NOT NULL
);


--
-- TOC entry 98 (OID 34308)
-- Name: business_process_param_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE business_process_param_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 265 (OID 34310)
-- Name: business_process_parameter; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE business_process_parameter (
    id integer DEFAULT nextval('business_process_param_id_seq'::text) NOT NULL,
    process_id integer NOT NULL,
    param_name character varying(255),
    param_value character varying(4000),
    enabled boolean DEFAULT true NOT NULL
);


--
-- TOC entry 100 (OID 34323)
-- Name: business_process_comp_pa_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE business_process_comp_pa_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 266 (OID 34325)
-- Name: business_process_component_parameter; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE business_process_component_parameter (
    id integer DEFAULT nextval('business_process_comp_pa_id_seq'::text) NOT NULL,
    component_id integer NOT NULL,
    parameter_id integer NOT NULL,
    param_value character varying(4000),
    enabled boolean DEFAULT true NOT NULL
);


--
-- TOC entry 102 (OID 34342)
-- Name: business_process_e_event_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE business_process_e_event_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 267 (OID 34344)
-- Name: business_process_events; Type: TABLE; Schema: public; Owner: matt
--

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


--
-- TOC entry 268 (OID 34366)
-- Name: business_process_log; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE business_process_log (
    process_name character varying(255) NOT NULL,
    anchor timestamp(3) without time zone NOT NULL
);


--
-- TOC entry 104 (OID 34370)
-- Name: business_process_hl_hook_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE business_process_hl_hook_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 269 (OID 34372)
-- Name: business_process_hook_library; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE business_process_hook_library (
    hook_id integer DEFAULT nextval('business_process_hl_hook_id_seq'::text) NOT NULL,
    link_module_id integer NOT NULL,
    hook_class character varying(255) NOT NULL,
    enabled boolean DEFAULT false
);


--
-- TOC entry 106 (OID 34382)
-- Name: business_process_ho_trig_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE business_process_ho_trig_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 270 (OID 34384)
-- Name: business_process_hook_triggers; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE business_process_hook_triggers (
    trigger_id integer DEFAULT nextval('business_process_ho_trig_id_seq'::text) NOT NULL,
    action_type_id integer NOT NULL,
    hook_id integer NOT NULL,
    enabled boolean DEFAULT false
);


--
-- TOC entry 108 (OID 34394)
-- Name: business_process_ho_hook_id_seq; Type: SEQUENCE; Schema: public; Owner: matt
--

CREATE SEQUENCE business_process_ho_hook_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;


--
-- TOC entry 271 (OID 34396)
-- Name: business_process_hook; Type: TABLE; Schema: public; Owner: matt
--

CREATE TABLE business_process_hook (
    id integer DEFAULT nextval('business_process_ho_hook_id_seq'::text) NOT NULL,
    trigger_id integer NOT NULL,
    process_id integer NOT NULL,
    enabled boolean DEFAULT false
);


--
-- Data for TOC entry 533 (OID 31004)
-- Name: access; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO "access" VALUES (0, 'dhvadmin', '---', -1, 1, -1, 8, 18, NULL, 'America/New_York', NULL, '2003-12-10 13:43:21.023', 0, '2003-12-10 13:43:21.023', 0, '2003-12-10 13:43:21.023', NULL, -1, -1, true);


--
-- Data for TOC entry 534 (OID 31026)
-- Name: lookup_industry; Type: TABLE DATA; Schema: public; Owner: matt
--

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


--
-- Data for TOC entry 535 (OID 31036)
-- Name: access_log; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 536 (OID 31048)
-- Name: usage_log; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 537 (OID 31056)
-- Name: lookup_contact_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_contact_types VALUES (1, 'Sales', false, 0, true, NULL, 0);
INSERT INTO lookup_contact_types VALUES (2, 'Billing', false, 0, true, NULL, 0);
INSERT INTO lookup_contact_types VALUES (3, 'Technical', false, 0, true, NULL, 0);


--
-- Data for TOC entry 538 (OID 31071)
-- Name: lookup_account_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_account_types VALUES (1, 'Customer', false, 0, true);
INSERT INTO lookup_account_types VALUES (2, 'Competitor', false, 0, true);
INSERT INTO lookup_account_types VALUES (3, 'Partner', false, 0, true);
INSERT INTO lookup_account_types VALUES (4, 'Vendor', false, 0, true);
INSERT INTO lookup_account_types VALUES (5, 'Investor', false, 0, true);
INSERT INTO lookup_account_types VALUES (6, 'Prospect', false, 0, true);


--
-- Data for TOC entry 539 (OID 31079)
-- Name: state; Type: TABLE DATA; Schema: public; Owner: matt
--

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


--
-- Data for TOC entry 540 (OID 31085)
-- Name: lookup_department; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_department VALUES (1, 'Customer Relations', false, 0, true);
INSERT INTO lookup_department VALUES (2, 'Engineering', false, 0, true);
INSERT INTO lookup_department VALUES (3, 'Billing', false, 0, true);
INSERT INTO lookup_department VALUES (4, 'Shipping/Receiving', false, 0, true);
INSERT INTO lookup_department VALUES (5, 'Purchasing', false, 0, true);
INSERT INTO lookup_department VALUES (6, 'Accounting', false, 0, true);
INSERT INTO lookup_department VALUES (7, 'Human Resources', false, 0, true);


--
-- Data for TOC entry 541 (OID 31095)
-- Name: lookup_orgaddress_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_orgaddress_types VALUES (1, 'Primary', false, 0, true);
INSERT INTO lookup_orgaddress_types VALUES (2, 'Auxiliary', false, 0, true);
INSERT INTO lookup_orgaddress_types VALUES (3, 'Billing', false, 0, true);
INSERT INTO lookup_orgaddress_types VALUES (4, 'Shipping', false, 0, true);


--
-- Data for TOC entry 542 (OID 31105)
-- Name: lookup_orgemail_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_orgemail_types VALUES (1, 'Primary', false, 0, true);
INSERT INTO lookup_orgemail_types VALUES (2, 'Auxiliary', false, 0, true);


--
-- Data for TOC entry 543 (OID 31115)
-- Name: lookup_orgphone_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_orgphone_types VALUES (1, 'Main', false, 0, true);
INSERT INTO lookup_orgphone_types VALUES (2, 'Fax', false, 0, true);


--
-- Data for TOC entry 544 (OID 31125)
-- Name: lookup_instantmessenger_types; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 545 (OID 31135)
-- Name: lookup_employment_types; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 546 (OID 31145)
-- Name: lookup_locale; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 547 (OID 31155)
-- Name: lookup_contactaddress_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_contactaddress_types VALUES (1, 'Business', false, 0, true);
INSERT INTO lookup_contactaddress_types VALUES (2, 'Home', false, 0, true);
INSERT INTO lookup_contactaddress_types VALUES (3, 'Other', false, 0, true);


--
-- Data for TOC entry 548 (OID 31165)
-- Name: lookup_contactemail_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_contactemail_types VALUES (1, 'Business', false, 0, true);
INSERT INTO lookup_contactemail_types VALUES (2, 'Personal', false, 0, true);
INSERT INTO lookup_contactemail_types VALUES (3, 'Other', false, 0, true);


--
-- Data for TOC entry 549 (OID 31175)
-- Name: lookup_contactphone_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_contactphone_types VALUES (1, 'Business', false, 0, true);
INSERT INTO lookup_contactphone_types VALUES (2, 'Business2', false, 0, true);
INSERT INTO lookup_contactphone_types VALUES (3, 'Business Fax', false, 0, true);
INSERT INTO lookup_contactphone_types VALUES (4, 'Home', false, 0, true);
INSERT INTO lookup_contactphone_types VALUES (5, 'Home2', false, 0, true);
INSERT INTO lookup_contactphone_types VALUES (6, 'Home Fax', false, 0, true);
INSERT INTO lookup_contactphone_types VALUES (7, 'Mobile', false, 0, true);
INSERT INTO lookup_contactphone_types VALUES (8, 'Pager', false, 0, true);
INSERT INTO lookup_contactphone_types VALUES (9, 'Other', false, 0, true);


--
-- Data for TOC entry 550 (OID 31185)
-- Name: lookup_access_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_access_types VALUES (1, 626030330, 'Controlled-Hierarchy', true, 1, true, 626030335);
INSERT INTO lookup_access_types VALUES (2, 626030330, 'Public', false, 2, true, 626030334);
INSERT INTO lookup_access_types VALUES (3, 626030330, 'Personal', false, 3, true, 626030333);
INSERT INTO lookup_access_types VALUES (4, 626030331, 'Public', true, 1, true, 626030334);
INSERT INTO lookup_access_types VALUES (5, 626030332, 'Public', true, 1, true, 626030334);
INSERT INTO lookup_access_types VALUES (6, 707031028, 'Controlled-Hierarchy', true, 1, true, 626030335);
INSERT INTO lookup_access_types VALUES (7, 707031028, 'Public', false, 2, true, 626030334);
INSERT INTO lookup_access_types VALUES (8, 707031028, 'Personal', false, 3, true, 626030333);


--
-- Data for TOC entry 551 (OID 31194)
-- Name: organization; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO organization VALUES (0, 'My Company', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, false, NULL, NULL, '2003-12-10 13:43:21.089', 0, '2003-12-10 13:43:21.089', 0, true, NULL, 0, -1, -1, -1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- Data for TOC entry 552 (OID 31225)
-- Name: contact; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 553 (OID 31282)
-- Name: role; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO role VALUES (1, 'Administrator', 'Performs system configuration and maintenance', 0, '2003-12-10 13:43:57.172', 0, '2003-12-10 13:43:57.172', true);
INSERT INTO role VALUES (2, 'Operations Manager', 'Manages operations', 0, '2003-12-10 13:43:57.591', 0, '2003-12-10 13:43:57.591', true);
INSERT INTO role VALUES (3, 'Sales Manager', 'Manages all accounts and opportunities', 0, '2003-12-10 13:43:57.92', 0, '2003-12-10 13:43:57.92', true);
INSERT INTO role VALUES (4, 'Salesperson', 'Manages own accounts and opportunities', 0, '2003-12-10 13:43:58.225', 0, '2003-12-10 13:43:58.225', true);
INSERT INTO role VALUES (5, 'Customer Service Manager', 'Manages all tickets', 0, '2003-12-10 13:43:58.593', 0, '2003-12-10 13:43:58.593', true);
INSERT INTO role VALUES (6, 'Customer Service Representative', 'Manages own tickets', 0, '2003-12-10 13:43:58.787', 0, '2003-12-10 13:43:58.787', true);
INSERT INTO role VALUES (7, 'Marketing Manager', 'Manages communications', 0, '2003-12-10 13:43:58.979', 0, '2003-12-10 13:43:58.979', true);
INSERT INTO role VALUES (8, 'Accounting Manager', 'Reviews revenue and opportunities', 0, '2003-12-10 13:43:59.247', 0, '2003-12-10 13:43:59.247', true);
INSERT INTO role VALUES (9, 'HR Representative', 'Manages employee information', 0, '2003-12-10 13:43:59.504', 0, '2003-12-10 13:43:59.504', true);


--
-- Data for TOC entry 554 (OID 31301)
-- Name: permission_category; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO permission_category VALUES (1, 'Accounts', NULL, 500, true, true, true, true, false, false, false, false, true);
INSERT INTO permission_category VALUES (2, 'Contacts', NULL, 300, true, true, true, true, false, false, false, false, true);
INSERT INTO permission_category VALUES (3, 'Auto Guide', NULL, 600, false, false, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (4, 'Pipeline', NULL, 400, true, true, false, true, true, false, false, false, true);
INSERT INTO permission_category VALUES (5, 'Demo', NULL, 1500, false, false, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (6, 'Communications', NULL, 700, true, true, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (7, 'Projects', NULL, 800, false, false, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (8, 'Tickets', NULL, 900, true, true, true, true, false, false, false, false, true);
INSERT INTO permission_category VALUES (9, 'Admin', NULL, 1200, true, true, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (10, 'Help', NULL, 1300, true, true, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (11, 'System', NULL, 100, true, true, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (12, 'My Home Page', NULL, 200, true, true, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (13, 'QA', NULL, 1400, true, true, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (14, 'Reports', NULL, 1100, true, true, false, false, false, false, false, false, false);
INSERT INTO permission_category VALUES (15, 'Employees', NULL, 1000, true, true, false, true, false, false, false, false, true);


--
-- Data for TOC entry 555 (OID 31318)
-- Name: permission; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO permission VALUES (1, 1, 'accounts', true, false, false, false, 'Access to Accounts module', 10, true, true, false);
INSERT INTO permission VALUES (2, 1, 'accounts-accounts', true, true, true, true, 'Account Records', 20, true, true, false);
INSERT INTO permission VALUES (3, 1, 'accounts-accounts-folders', true, true, true, false, 'Folders', 30, true, true, false);
INSERT INTO permission VALUES (4, 1, 'accounts-accounts-contacts', true, true, true, true, 'Contacts', 40, true, true, false);
INSERT INTO permission VALUES (5, 1, 'accounts-accounts-contacts-opportunities', true, true, true, true, 'Contact Opportunities', 50, true, true, false);
INSERT INTO permission VALUES (6, 1, 'accounts-accounts-contacts-calls', true, true, true, true, 'Contact Calls', 60, true, true, false);
INSERT INTO permission VALUES (7, 1, 'accounts-accounts-contacts-messages', true, true, true, true, 'Contact Messages', 70, true, true, false);
INSERT INTO permission VALUES (8, 1, 'accounts-accounts-opportunities', true, true, true, true, 'Opportunities', 80, true, true, false);
INSERT INTO permission VALUES (9, 1, 'accounts-accounts-tickets', true, true, true, true, 'Tickets', 90, true, true, false);
INSERT INTO permission VALUES (10, 1, 'accounts-accounts-documents', true, true, true, true, 'Documents', 100, true, true, false);
INSERT INTO permission VALUES (11, 1, 'accounts-accounts-reports', true, true, false, true, 'Export Account Data', 110, true, true, false);
INSERT INTO permission VALUES (12, 1, 'accounts-dashboard', true, false, false, false, 'Dashboard', 120, true, true, false);
INSERT INTO permission VALUES (13, 1, 'accounts-accounts-revenue', true, true, true, true, 'Revenue', 130, false, false, false);
INSERT INTO permission VALUES (14, 1, 'accounts-autoguide-inventory', true, true, true, true, 'Auto Guide Vehicle Inventory', 140, false, false, false);
INSERT INTO permission VALUES (15, 1, 'accounts-accounts-tickets-documents', true, true, true, true, 'Ticket Documents', 150, true, true, false);
INSERT INTO permission VALUES (16, 1, 'accounts-accounts-tickets-tasks', true, true, true, true, 'Ticket Tasks', 160, true, true, false);
INSERT INTO permission VALUES (17, 2, 'contacts', true, false, false, false, 'Access to Contacts module', 10, true, true, false);
INSERT INTO permission VALUES (18, 2, 'contacts-external_contacts', true, true, true, true, 'General Contact Records', 20, true, true, false);
INSERT INTO permission VALUES (19, 2, 'contacts-external_contacts-reports', true, true, false, true, 'Export Contact Data', 30, true, true, false);
INSERT INTO permission VALUES (20, 2, 'contacts-external_contacts-folders', true, true, true, true, 'Folders', 40, true, true, false);
INSERT INTO permission VALUES (21, 2, 'contacts-external_contacts-calls', true, true, true, true, 'Calls', 50, true, true, false);
INSERT INTO permission VALUES (22, 2, 'contacts-external_contacts-messages', true, false, false, false, 'Messages', 60, true, true, false);
INSERT INTO permission VALUES (23, 2, 'contacts-external_contacts-opportunities', true, true, true, true, 'Opportunities', 70, true, true, false);
INSERT INTO permission VALUES (24, 3, 'autoguide', true, false, false, false, 'Access to the Auto Guide module', 10, true, true, false);
INSERT INTO permission VALUES (25, 3, 'autoguide-adruns', false, false, true, false, 'Ad Run complete status', 20, true, true, false);
INSERT INTO permission VALUES (26, 4, 'pipeline', true, false, false, false, 'Access to Pipeline module', 10, true, true, true);
INSERT INTO permission VALUES (27, 4, 'pipeline-opportunities', true, true, true, true, 'Opportunity Records', 20, true, true, false);
INSERT INTO permission VALUES (28, 4, 'pipeline-dashboard', true, false, false, false, 'Dashboard', 30, true, true, false);
INSERT INTO permission VALUES (29, 4, 'pipeline-reports', true, true, false, true, 'Export Opportunity Data', 40, true, true, false);
INSERT INTO permission VALUES (30, 4, 'pipeline-opportunities-calls', true, true, true, true, 'Calls', 50, true, true, false);
INSERT INTO permission VALUES (31, 4, 'pipeline-opportunities-documents', true, true, true, true, 'Documents', 60, true, true, false);
INSERT INTO permission VALUES (32, 5, 'demo', true, true, true, true, 'Access to Demo/Non-working features', 10, true, true, false);
INSERT INTO permission VALUES (33, 6, 'campaign', true, false, false, false, 'Access to Communications module', 10, true, true, false);
INSERT INTO permission VALUES (34, 6, 'campaign-dashboard', true, false, false, false, 'Dashboard', 20, true, true, false);
INSERT INTO permission VALUES (35, 6, 'campaign-campaigns', true, true, true, true, 'Campaign Records', 30, true, true, false);
INSERT INTO permission VALUES (36, 6, 'campaign-campaigns-groups', true, true, true, true, 'Group Records', 40, true, true, false);
INSERT INTO permission VALUES (37, 6, 'campaign-campaigns-messages', true, true, true, true, 'Message Records', 50, true, true, false);
INSERT INTO permission VALUES (38, 6, 'campaign-campaigns-surveys', true, true, true, true, 'Survey Records', 60, true, true, false);
INSERT INTO permission VALUES (39, 7, 'projects', true, false, false, false, 'Access to Project Management module', 10, true, true, false);
INSERT INTO permission VALUES (40, 7, 'projects-personal', true, false, false, false, 'Personal View', 20, true, true, false);
INSERT INTO permission VALUES (41, 7, 'projects-enterprise', true, false, false, false, 'Enterprise View', 30, true, true, false);
INSERT INTO permission VALUES (42, 7, 'projects-projects', true, true, true, true, 'Project Records', 40, true, true, false);
INSERT INTO permission VALUES (43, 8, 'tickets', true, false, false, false, 'Access to Ticket module', 10, true, true, false);
INSERT INTO permission VALUES (44, 8, 'tickets-tickets', true, true, true, true, 'Ticket Records', 20, true, true, false);
INSERT INTO permission VALUES (45, 8, 'tickets-reports', true, true, true, true, 'Export Ticket Data', 30, true, true, false);
INSERT INTO permission VALUES (46, 8, 'tickets-tickets-tasks', true, true, true, true, 'Tasks', 40, true, true, false);
INSERT INTO permission VALUES (47, 9, 'admin', true, false, false, false, 'Access to Admin module', 10, true, true, false);
INSERT INTO permission VALUES (48, 9, 'admin-users', true, true, true, true, 'Users', 20, true, true, false);
INSERT INTO permission VALUES (49, 9, 'admin-roles', true, true, true, true, 'Roles', 30, true, true, false);
INSERT INTO permission VALUES (50, 9, 'admin-usage', true, false, false, false, 'System Usage', 40, true, true, false);
INSERT INTO permission VALUES (51, 9, 'admin-sysconfig', true, false, true, false, 'System Configuration', 50, true, true, false);
INSERT INTO permission VALUES (52, 9, 'admin-sysconfig-lists', true, false, true, false, 'Configure Lookup Lists', 60, true, true, false);
INSERT INTO permission VALUES (53, 9, 'admin-sysconfig-folders', true, true, true, true, 'Configure Custom Folders & Fields', 70, true, true, false);
INSERT INTO permission VALUES (54, 9, 'admin-object-workflow', true, true, true, true, 'Configure Object Workflow', 80, true, true, false);
INSERT INTO permission VALUES (55, 9, 'admin-sysconfig-categories', true, true, true, true, 'Categories', 90, true, true, false);
INSERT INTO permission VALUES (56, 10, 'help', true, false, false, false, 'Access to Help System', 10, true, true, false);
INSERT INTO permission VALUES (57, 11, 'globalitems-search', true, false, false, false, 'Access to Global Search', 10, true, true, false);
INSERT INTO permission VALUES (58, 11, 'globalitems-myitems', true, false, false, false, 'Access to My Items', 20, true, true, false);
INSERT INTO permission VALUES (59, 11, 'globalitems-recentitems', true, false, false, false, 'Access to Recent Items', 30, true, true, false);
INSERT INTO permission VALUES (60, 12, 'myhomepage', true, false, false, false, 'Access to My Home Page module', 10, true, true, false);
INSERT INTO permission VALUES (61, 12, 'myhomepage-dashboard', true, false, false, false, 'View Performance Dashboard', 20, true, true, false);
INSERT INTO permission VALUES (62, 12, 'myhomepage-miner', true, true, false, true, 'Industry News records', 30, false, false, false);
INSERT INTO permission VALUES (63, 12, 'myhomepage-inbox', true, false, false, false, 'My Mailbox', 40, true, true, false);
INSERT INTO permission VALUES (64, 12, 'myhomepage-tasks', true, true, true, true, 'My Tasks', 50, true, true, false);
INSERT INTO permission VALUES (65, 12, 'myhomepage-reassign', true, false, true, false, 'Re-assign Items', 60, true, true, false);
INSERT INTO permission VALUES (66, 12, 'myhomepage-profile', true, false, false, false, 'My Profile', 70, true, true, false);
INSERT INTO permission VALUES (67, 12, 'myhomepage-profile-personal', true, false, true, false, 'Personal Information', 80, true, true, false);
INSERT INTO permission VALUES (68, 12, 'myhomepage-profile-settings', true, false, true, false, 'Settings', 90, false, false, false);
INSERT INTO permission VALUES (69, 12, 'myhomepage-profile-password', false, false, true, false, 'Password', 100, true, true, false);
INSERT INTO permission VALUES (70, 12, 'myhomepage-action-lists', true, true, true, true, 'My Action Lists', 110, true, true, false);
INSERT INTO permission VALUES (71, 13, 'qa', true, true, true, true, 'Access to QA Tool', 10, true, true, false);
INSERT INTO permission VALUES (72, 14, 'reports', true, false, false, false, 'Access to Reports module', 10, true, true, false);
INSERT INTO permission VALUES (73, 15, 'employees', true, false, false, false, 'Access to Employee module', 10, true, true, false);
INSERT INTO permission VALUES (74, 15, 'contacts-internal_contacts', true, true, true, true, 'Employees', 20, true, true, false);


--
-- Data for TOC entry 556 (OID 31338)
-- Name: role_permission; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO role_permission VALUES (1, 1, 60, true, false, false, false);
INSERT INTO role_permission VALUES (2, 1, 61, true, false, false, false);
INSERT INTO role_permission VALUES (3, 1, 62, true, true, false, true);
INSERT INTO role_permission VALUES (4, 1, 63, true, false, false, false);
INSERT INTO role_permission VALUES (5, 1, 64, true, true, true, true);
INSERT INTO role_permission VALUES (6, 1, 66, true, false, false, false);
INSERT INTO role_permission VALUES (7, 1, 67, true, false, true, false);
INSERT INTO role_permission VALUES (8, 1, 68, true, false, true, false);
INSERT INTO role_permission VALUES (9, 1, 69, false, false, true, false);
INSERT INTO role_permission VALUES (10, 1, 65, true, false, true, false);
INSERT INTO role_permission VALUES (11, 1, 17, true, false, false, false);
INSERT INTO role_permission VALUES (12, 1, 18, true, true, true, true);
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
INSERT INTO role_permission VALUES (23, 1, 11, true, true, false, true);
INSERT INTO role_permission VALUES (24, 1, 33, true, false, false, false);
INSERT INTO role_permission VALUES (25, 1, 34, true, false, false, false);
INSERT INTO role_permission VALUES (26, 1, 35, true, true, true, true);
INSERT INTO role_permission VALUES (27, 1, 43, true, false, false, false);
INSERT INTO role_permission VALUES (28, 1, 44, true, true, true, true);
INSERT INTO role_permission VALUES (29, 1, 45, true, true, true, true);
INSERT INTO role_permission VALUES (30, 1, 46, true, true, true, true);
INSERT INTO role_permission VALUES (31, 1, 73, true, true, true, false);
INSERT INTO role_permission VALUES (32, 1, 74, true, true, true, true);
INSERT INTO role_permission VALUES (33, 1, 72, true, false, false, false);
INSERT INTO role_permission VALUES (34, 1, 47, true, false, false, false);
INSERT INTO role_permission VALUES (35, 1, 48, true, true, true, true);
INSERT INTO role_permission VALUES (36, 1, 49, true, true, true, true);
INSERT INTO role_permission VALUES (37, 1, 51, true, false, true, false);
INSERT INTO role_permission VALUES (38, 1, 50, true, false, false, false);
INSERT INTO role_permission VALUES (39, 1, 52, true, false, true, false);
INSERT INTO role_permission VALUES (40, 1, 53, true, true, true, true);
INSERT INTO role_permission VALUES (41, 1, 54, true, true, true, true);
INSERT INTO role_permission VALUES (42, 1, 55, true, true, true, true);
INSERT INTO role_permission VALUES (43, 1, 56, true, false, false, false);
INSERT INTO role_permission VALUES (44, 1, 57, true, false, false, false);
INSERT INTO role_permission VALUES (45, 1, 58, true, false, false, false);
INSERT INTO role_permission VALUES (46, 1, 59, true, false, false, false);
INSERT INTO role_permission VALUES (47, 2, 60, true, false, false, false);
INSERT INTO role_permission VALUES (48, 2, 61, true, false, false, false);
INSERT INTO role_permission VALUES (49, 2, 63, true, false, false, false);
INSERT INTO role_permission VALUES (50, 2, 64, true, true, true, true);
INSERT INTO role_permission VALUES (51, 2, 65, true, false, true, false);
INSERT INTO role_permission VALUES (52, 2, 66, true, false, false, false);
INSERT INTO role_permission VALUES (53, 2, 67, true, false, true, false);
INSERT INTO role_permission VALUES (54, 2, 68, true, false, true, false);
INSERT INTO role_permission VALUES (55, 2, 69, false, false, true, false);
INSERT INTO role_permission VALUES (56, 2, 70, true, true, true, true);
INSERT INTO role_permission VALUES (57, 2, 17, true, false, false, false);
INSERT INTO role_permission VALUES (58, 2, 18, true, true, true, true);
INSERT INTO role_permission VALUES (59, 2, 19, true, true, false, true);
INSERT INTO role_permission VALUES (60, 2, 20, true, true, true, true);
INSERT INTO role_permission VALUES (61, 2, 21, true, true, true, true);
INSERT INTO role_permission VALUES (62, 2, 22, true, false, false, false);
INSERT INTO role_permission VALUES (63, 2, 23, true, true, true, true);
INSERT INTO role_permission VALUES (64, 2, 26, true, false, false, false);
INSERT INTO role_permission VALUES (65, 2, 27, true, true, true, true);
INSERT INTO role_permission VALUES (66, 2, 28, true, false, false, false);
INSERT INTO role_permission VALUES (67, 2, 29, true, true, false, true);
INSERT INTO role_permission VALUES (68, 2, 30, true, true, true, true);
INSERT INTO role_permission VALUES (69, 2, 31, true, true, true, true);
INSERT INTO role_permission VALUES (70, 2, 1, true, false, false, false);
INSERT INTO role_permission VALUES (71, 2, 2, true, true, true, true);
INSERT INTO role_permission VALUES (72, 2, 3, true, true, true, false);
INSERT INTO role_permission VALUES (73, 2, 4, true, true, true, true);
INSERT INTO role_permission VALUES (74, 2, 5, true, true, true, true);
INSERT INTO role_permission VALUES (75, 2, 6, true, true, true, true);
INSERT INTO role_permission VALUES (76, 2, 7, true, true, true, true);
INSERT INTO role_permission VALUES (77, 2, 8, true, true, true, true);
INSERT INTO role_permission VALUES (78, 2, 9, true, true, true, true);
INSERT INTO role_permission VALUES (79, 2, 10, true, true, true, true);
INSERT INTO role_permission VALUES (80, 2, 11, true, true, false, true);
INSERT INTO role_permission VALUES (81, 2, 13, true, true, true, true);
INSERT INTO role_permission VALUES (82, 2, 15, true, true, true, true);
INSERT INTO role_permission VALUES (83, 2, 16, true, true, true, true);
INSERT INTO role_permission VALUES (84, 2, 33, true, false, false, false);
INSERT INTO role_permission VALUES (85, 2, 34, true, false, false, false);
INSERT INTO role_permission VALUES (86, 2, 35, true, true, true, true);
INSERT INTO role_permission VALUES (87, 2, 36, true, true, true, true);
INSERT INTO role_permission VALUES (88, 2, 37, true, true, true, true);
INSERT INTO role_permission VALUES (89, 2, 38, true, true, true, true);
INSERT INTO role_permission VALUES (90, 2, 43, true, false, false, false);
INSERT INTO role_permission VALUES (91, 2, 44, true, true, true, false);
INSERT INTO role_permission VALUES (92, 2, 45, true, true, true, true);
INSERT INTO role_permission VALUES (93, 2, 46, true, true, true, false);
INSERT INTO role_permission VALUES (94, 2, 73, true, false, false, false);
INSERT INTO role_permission VALUES (95, 2, 74, true, false, false, false);
INSERT INTO role_permission VALUES (96, 2, 72, true, false, false, false);
INSERT INTO role_permission VALUES (97, 2, 56, true, false, false, false);
INSERT INTO role_permission VALUES (98, 2, 57, true, false, false, false);
INSERT INTO role_permission VALUES (99, 2, 58, true, false, false, false);
INSERT INTO role_permission VALUES (100, 2, 59, true, false, false, false);
INSERT INTO role_permission VALUES (101, 3, 60, true, false, false, false);
INSERT INTO role_permission VALUES (102, 3, 61, true, false, false, false);
INSERT INTO role_permission VALUES (103, 3, 63, true, false, false, false);
INSERT INTO role_permission VALUES (104, 3, 64, true, true, true, true);
INSERT INTO role_permission VALUES (105, 3, 65, true, false, true, false);
INSERT INTO role_permission VALUES (106, 3, 66, true, false, false, false);
INSERT INTO role_permission VALUES (107, 3, 67, true, false, true, false);
INSERT INTO role_permission VALUES (108, 3, 68, true, false, true, false);
INSERT INTO role_permission VALUES (109, 3, 69, false, false, true, false);
INSERT INTO role_permission VALUES (110, 3, 70, true, true, true, true);
INSERT INTO role_permission VALUES (111, 3, 17, true, false, false, false);
INSERT INTO role_permission VALUES (112, 3, 18, true, true, true, true);
INSERT INTO role_permission VALUES (113, 3, 19, true, true, false, true);
INSERT INTO role_permission VALUES (114, 3, 20, true, true, true, true);
INSERT INTO role_permission VALUES (115, 3, 21, true, true, true, true);
INSERT INTO role_permission VALUES (116, 3, 22, true, false, false, false);
INSERT INTO role_permission VALUES (117, 3, 23, true, true, true, true);
INSERT INTO role_permission VALUES (118, 3, 26, true, false, false, false);
INSERT INTO role_permission VALUES (119, 3, 27, true, true, true, true);
INSERT INTO role_permission VALUES (120, 3, 28, true, false, false, false);
INSERT INTO role_permission VALUES (121, 3, 29, true, true, false, true);
INSERT INTO role_permission VALUES (122, 3, 30, true, true, true, true);
INSERT INTO role_permission VALUES (123, 3, 31, true, true, true, true);
INSERT INTO role_permission VALUES (124, 3, 1, true, false, false, false);
INSERT INTO role_permission VALUES (125, 3, 2, true, true, true, true);
INSERT INTO role_permission VALUES (126, 3, 3, true, true, true, false);
INSERT INTO role_permission VALUES (127, 3, 4, true, true, true, true);
INSERT INTO role_permission VALUES (128, 3, 5, true, true, true, true);
INSERT INTO role_permission VALUES (129, 3, 6, true, true, true, true);
INSERT INTO role_permission VALUES (130, 3, 7, true, true, true, true);
INSERT INTO role_permission VALUES (131, 3, 8, true, true, true, true);
INSERT INTO role_permission VALUES (132, 3, 9, true, true, true, true);
INSERT INTO role_permission VALUES (133, 3, 10, true, true, true, true);
INSERT INTO role_permission VALUES (134, 3, 11, true, true, false, true);
INSERT INTO role_permission VALUES (135, 3, 12, true, false, false, false);
INSERT INTO role_permission VALUES (136, 3, 13, true, true, true, true);
INSERT INTO role_permission VALUES (137, 3, 15, true, true, true, true);
INSERT INTO role_permission VALUES (138, 3, 16, true, true, true, true);
INSERT INTO role_permission VALUES (139, 3, 33, true, false, false, false);
INSERT INTO role_permission VALUES (140, 3, 34, true, false, false, false);
INSERT INTO role_permission VALUES (141, 3, 35, true, true, true, true);
INSERT INTO role_permission VALUES (142, 3, 36, true, true, true, true);
INSERT INTO role_permission VALUES (143, 3, 37, true, true, true, true);
INSERT INTO role_permission VALUES (144, 3, 38, true, true, true, true);
INSERT INTO role_permission VALUES (145, 3, 43, true, false, false, false);
INSERT INTO role_permission VALUES (146, 3, 44, true, true, true, false);
INSERT INTO role_permission VALUES (147, 3, 45, true, true, true, true);
INSERT INTO role_permission VALUES (148, 3, 46, true, true, true, false);
INSERT INTO role_permission VALUES (149, 3, 73, true, false, false, false);
INSERT INTO role_permission VALUES (150, 3, 74, true, false, false, false);
INSERT INTO role_permission VALUES (151, 3, 72, true, false, false, false);
INSERT INTO role_permission VALUES (152, 3, 56, true, false, false, false);
INSERT INTO role_permission VALUES (153, 3, 57, true, false, false, false);
INSERT INTO role_permission VALUES (154, 3, 58, true, false, false, false);
INSERT INTO role_permission VALUES (155, 3, 59, true, false, false, false);
INSERT INTO role_permission VALUES (156, 4, 60, true, false, false, false);
INSERT INTO role_permission VALUES (157, 4, 61, true, false, false, false);
INSERT INTO role_permission VALUES (158, 4, 63, true, false, false, false);
INSERT INTO role_permission VALUES (159, 4, 64, true, true, true, true);
INSERT INTO role_permission VALUES (160, 4, 66, true, false, false, false);
INSERT INTO role_permission VALUES (161, 4, 67, true, false, true, false);
INSERT INTO role_permission VALUES (162, 4, 68, true, false, true, false);
INSERT INTO role_permission VALUES (163, 4, 69, false, false, true, false);
INSERT INTO role_permission VALUES (164, 4, 70, true, true, true, true);
INSERT INTO role_permission VALUES (165, 4, 17, true, false, false, false);
INSERT INTO role_permission VALUES (166, 4, 18, true, true, true, true);
INSERT INTO role_permission VALUES (167, 4, 19, true, true, false, true);
INSERT INTO role_permission VALUES (168, 4, 21, true, true, true, true);
INSERT INTO role_permission VALUES (169, 4, 22, true, true, true, true);
INSERT INTO role_permission VALUES (170, 4, 23, true, false, false, false);
INSERT INTO role_permission VALUES (171, 4, 26, true, false, false, false);
INSERT INTO role_permission VALUES (172, 4, 27, true, true, true, false);
INSERT INTO role_permission VALUES (173, 4, 28, true, false, false, false);
INSERT INTO role_permission VALUES (174, 4, 29, true, true, false, true);
INSERT INTO role_permission VALUES (175, 4, 30, true, true, true, true);
INSERT INTO role_permission VALUES (176, 4, 31, true, true, true, true);
INSERT INTO role_permission VALUES (177, 4, 1, true, false, false, false);
INSERT INTO role_permission VALUES (178, 4, 2, true, true, true, false);
INSERT INTO role_permission VALUES (179, 4, 3, true, true, true, false);
INSERT INTO role_permission VALUES (180, 4, 4, true, true, true, false);
INSERT INTO role_permission VALUES (181, 4, 5, true, true, true, false);
INSERT INTO role_permission VALUES (182, 4, 6, true, true, true, false);
INSERT INTO role_permission VALUES (183, 4, 7, true, true, true, false);
INSERT INTO role_permission VALUES (184, 4, 8, true, true, true, false);
INSERT INTO role_permission VALUES (185, 4, 9, true, true, true, false);
INSERT INTO role_permission VALUES (186, 4, 10, true, true, true, false);
INSERT INTO role_permission VALUES (187, 4, 11, true, true, false, true);
INSERT INTO role_permission VALUES (188, 4, 12, true, false, false, false);
INSERT INTO role_permission VALUES (189, 4, 13, true, true, true, false);
INSERT INTO role_permission VALUES (190, 4, 15, true, true, true, false);
INSERT INTO role_permission VALUES (191, 4, 16, true, true, true, false);
INSERT INTO role_permission VALUES (192, 4, 33, true, false, false, false);
INSERT INTO role_permission VALUES (193, 4, 34, true, false, false, false);
INSERT INTO role_permission VALUES (194, 4, 35, true, true, true, true);
INSERT INTO role_permission VALUES (195, 4, 36, true, true, true, true);
INSERT INTO role_permission VALUES (196, 4, 37, true, true, true, true);
INSERT INTO role_permission VALUES (197, 4, 38, true, true, true, true);
INSERT INTO role_permission VALUES (198, 4, 43, true, false, false, false);
INSERT INTO role_permission VALUES (199, 4, 44, true, true, true, false);
INSERT INTO role_permission VALUES (200, 4, 45, true, true, false, true);
INSERT INTO role_permission VALUES (201, 4, 46, true, true, true, false);
INSERT INTO role_permission VALUES (202, 4, 73, true, false, false, false);
INSERT INTO role_permission VALUES (203, 4, 74, true, false, false, false);
INSERT INTO role_permission VALUES (204, 4, 72, true, false, false, false);
INSERT INTO role_permission VALUES (205, 4, 56, true, false, false, false);
INSERT INTO role_permission VALUES (206, 4, 57, true, false, false, false);
INSERT INTO role_permission VALUES (207, 4, 58, true, false, false, false);
INSERT INTO role_permission VALUES (208, 4, 59, true, false, false, false);
INSERT INTO role_permission VALUES (209, 5, 60, true, false, false, false);
INSERT INTO role_permission VALUES (210, 5, 61, true, false, false, false);
INSERT INTO role_permission VALUES (211, 5, 63, true, false, false, false);
INSERT INTO role_permission VALUES (212, 5, 64, true, true, true, true);
INSERT INTO role_permission VALUES (213, 5, 65, true, false, true, false);
INSERT INTO role_permission VALUES (214, 5, 66, true, false, false, false);
INSERT INTO role_permission VALUES (215, 5, 67, true, false, true, false);
INSERT INTO role_permission VALUES (216, 5, 68, true, false, true, false);
INSERT INTO role_permission VALUES (217, 5, 69, false, false, true, false);
INSERT INTO role_permission VALUES (218, 5, 1, true, false, false, false);
INSERT INTO role_permission VALUES (219, 5, 2, true, true, true, false);
INSERT INTO role_permission VALUES (220, 5, 3, true, true, true, false);
INSERT INTO role_permission VALUES (221, 5, 4, true, true, true, false);
INSERT INTO role_permission VALUES (222, 5, 6, true, true, true, false);
INSERT INTO role_permission VALUES (223, 5, 7, true, true, true, false);
INSERT INTO role_permission VALUES (224, 5, 9, true, true, true, false);
INSERT INTO role_permission VALUES (225, 5, 10, true, true, true, false);
INSERT INTO role_permission VALUES (226, 5, 11, true, true, false, true);
INSERT INTO role_permission VALUES (227, 5, 15, true, true, true, false);
INSERT INTO role_permission VALUES (228, 5, 16, true, true, true, false);
INSERT INTO role_permission VALUES (229, 5, 33, true, false, false, false);
INSERT INTO role_permission VALUES (230, 5, 34, true, false, false, false);
INSERT INTO role_permission VALUES (231, 5, 35, true, true, true, true);
INSERT INTO role_permission VALUES (232, 5, 36, true, true, true, true);
INSERT INTO role_permission VALUES (233, 5, 37, true, true, true, true);
INSERT INTO role_permission VALUES (234, 5, 38, true, true, true, true);
INSERT INTO role_permission VALUES (235, 5, 43, true, false, false, false);
INSERT INTO role_permission VALUES (236, 5, 44, true, true, true, true);
INSERT INTO role_permission VALUES (237, 5, 45, true, true, true, true);
INSERT INTO role_permission VALUES (238, 5, 46, true, true, true, true);
INSERT INTO role_permission VALUES (239, 5, 73, true, false, false, false);
INSERT INTO role_permission VALUES (240, 5, 74, true, false, false, false);
INSERT INTO role_permission VALUES (241, 5, 72, true, false, false, false);
INSERT INTO role_permission VALUES (242, 5, 56, true, false, false, false);
INSERT INTO role_permission VALUES (243, 5, 57, true, false, false, false);
INSERT INTO role_permission VALUES (244, 5, 58, true, false, false, false);
INSERT INTO role_permission VALUES (245, 5, 59, true, false, false, false);
INSERT INTO role_permission VALUES (246, 6, 60, true, false, false, false);
INSERT INTO role_permission VALUES (247, 6, 61, true, false, false, false);
INSERT INTO role_permission VALUES (248, 6, 63, true, false, false, false);
INSERT INTO role_permission VALUES (249, 6, 64, true, true, true, true);
INSERT INTO role_permission VALUES (250, 6, 65, true, false, true, false);
INSERT INTO role_permission VALUES (251, 6, 66, true, false, false, false);
INSERT INTO role_permission VALUES (252, 6, 67, true, false, true, false);
INSERT INTO role_permission VALUES (253, 6, 68, true, false, true, false);
INSERT INTO role_permission VALUES (254, 6, 69, false, false, true, false);
INSERT INTO role_permission VALUES (255, 6, 1, true, false, false, false);
INSERT INTO role_permission VALUES (256, 6, 2, true, true, true, false);
INSERT INTO role_permission VALUES (257, 6, 3, true, true, true, false);
INSERT INTO role_permission VALUES (258, 6, 4, true, true, true, false);
INSERT INTO role_permission VALUES (259, 6, 6, true, true, true, false);
INSERT INTO role_permission VALUES (260, 6, 7, true, true, true, false);
INSERT INTO role_permission VALUES (261, 6, 9, true, true, true, false);
INSERT INTO role_permission VALUES (262, 6, 10, true, true, true, false);
INSERT INTO role_permission VALUES (263, 6, 11, true, true, false, true);
INSERT INTO role_permission VALUES (264, 6, 15, true, true, true, false);
INSERT INTO role_permission VALUES (265, 6, 16, true, true, true, false);
INSERT INTO role_permission VALUES (266, 6, 33, true, false, false, false);
INSERT INTO role_permission VALUES (267, 6, 34, true, false, false, false);
INSERT INTO role_permission VALUES (268, 6, 35, true, true, true, true);
INSERT INTO role_permission VALUES (269, 6, 36, true, true, true, true);
INSERT INTO role_permission VALUES (270, 6, 37, true, true, true, true);
INSERT INTO role_permission VALUES (271, 6, 38, true, true, true, true);
INSERT INTO role_permission VALUES (272, 6, 43, true, false, false, false);
INSERT INTO role_permission VALUES (273, 6, 44, true, true, true, false);
INSERT INTO role_permission VALUES (274, 6, 45, true, true, true, false);
INSERT INTO role_permission VALUES (275, 6, 46, true, true, true, false);
INSERT INTO role_permission VALUES (276, 6, 73, true, false, false, false);
INSERT INTO role_permission VALUES (277, 6, 74, true, false, false, false);
INSERT INTO role_permission VALUES (278, 6, 72, true, false, false, false);
INSERT INTO role_permission VALUES (279, 6, 56, true, false, false, false);
INSERT INTO role_permission VALUES (280, 6, 57, true, false, false, false);
INSERT INTO role_permission VALUES (281, 6, 58, true, false, false, false);
INSERT INTO role_permission VALUES (282, 6, 59, true, false, false, false);
INSERT INTO role_permission VALUES (283, 7, 60, true, false, false, false);
INSERT INTO role_permission VALUES (284, 7, 61, true, false, false, false);
INSERT INTO role_permission VALUES (285, 7, 63, true, false, false, false);
INSERT INTO role_permission VALUES (286, 7, 64, true, true, true, true);
INSERT INTO role_permission VALUES (287, 7, 65, true, false, true, false);
INSERT INTO role_permission VALUES (288, 7, 66, true, false, false, false);
INSERT INTO role_permission VALUES (289, 7, 67, true, false, true, false);
INSERT INTO role_permission VALUES (290, 7, 68, true, false, true, false);
INSERT INTO role_permission VALUES (291, 7, 69, false, false, true, false);
INSERT INTO role_permission VALUES (292, 7, 70, true, true, true, true);
INSERT INTO role_permission VALUES (293, 7, 17, true, false, false, false);
INSERT INTO role_permission VALUES (294, 7, 18, true, true, true, true);
INSERT INTO role_permission VALUES (295, 7, 19, true, true, false, true);
INSERT INTO role_permission VALUES (296, 7, 20, true, true, true, true);
INSERT INTO role_permission VALUES (297, 7, 21, true, true, true, true);
INSERT INTO role_permission VALUES (298, 7, 22, true, false, false, false);
INSERT INTO role_permission VALUES (299, 7, 23, true, true, true, true);
INSERT INTO role_permission VALUES (300, 7, 26, true, false, false, false);
INSERT INTO role_permission VALUES (301, 7, 27, true, true, true, true);
INSERT INTO role_permission VALUES (302, 7, 28, true, false, false, false);
INSERT INTO role_permission VALUES (303, 7, 29, true, true, false, true);
INSERT INTO role_permission VALUES (304, 7, 30, true, true, true, true);
INSERT INTO role_permission VALUES (305, 7, 31, true, true, true, true);
INSERT INTO role_permission VALUES (306, 7, 1, true, false, false, false);
INSERT INTO role_permission VALUES (307, 7, 2, true, true, true, false);
INSERT INTO role_permission VALUES (308, 7, 3, true, true, true, false);
INSERT INTO role_permission VALUES (309, 7, 4, true, true, true, false);
INSERT INTO role_permission VALUES (310, 7, 5, true, true, true, false);
INSERT INTO role_permission VALUES (311, 7, 6, true, true, true, false);
INSERT INTO role_permission VALUES (312, 7, 7, true, true, true, false);
INSERT INTO role_permission VALUES (313, 7, 8, true, true, true, false);
INSERT INTO role_permission VALUES (314, 7, 9, true, true, true, false);
INSERT INTO role_permission VALUES (315, 7, 10, true, true, true, false);
INSERT INTO role_permission VALUES (316, 7, 11, true, true, false, true);
INSERT INTO role_permission VALUES (317, 7, 12, true, false, false, false);
INSERT INTO role_permission VALUES (318, 7, 13, true, true, true, false);
INSERT INTO role_permission VALUES (319, 7, 15, true, true, true, false);
INSERT INTO role_permission VALUES (320, 7, 16, true, true, true, false);
INSERT INTO role_permission VALUES (321, 7, 33, true, false, false, false);
INSERT INTO role_permission VALUES (322, 7, 34, true, false, false, false);
INSERT INTO role_permission VALUES (323, 7, 35, true, true, true, true);
INSERT INTO role_permission VALUES (324, 7, 36, true, true, true, true);
INSERT INTO role_permission VALUES (325, 7, 37, true, true, true, true);
INSERT INTO role_permission VALUES (326, 7, 38, true, true, true, true);
INSERT INTO role_permission VALUES (327, 7, 43, true, false, false, false);
INSERT INTO role_permission VALUES (328, 7, 44, true, true, true, false);
INSERT INTO role_permission VALUES (329, 7, 45, true, true, true, false);
INSERT INTO role_permission VALUES (330, 7, 46, true, true, true, false);
INSERT INTO role_permission VALUES (331, 7, 73, true, false, false, false);
INSERT INTO role_permission VALUES (332, 7, 74, true, false, false, false);
INSERT INTO role_permission VALUES (333, 7, 72, true, false, false, false);
INSERT INTO role_permission VALUES (334, 7, 56, true, false, false, false);
INSERT INTO role_permission VALUES (335, 7, 57, true, false, false, false);
INSERT INTO role_permission VALUES (336, 7, 58, true, false, false, false);
INSERT INTO role_permission VALUES (337, 7, 59, true, false, false, false);
INSERT INTO role_permission VALUES (338, 8, 60, true, false, false, false);
INSERT INTO role_permission VALUES (339, 8, 61, true, false, false, false);
INSERT INTO role_permission VALUES (340, 8, 63, true, false, false, false);
INSERT INTO role_permission VALUES (341, 8, 64, true, true, true, true);
INSERT INTO role_permission VALUES (342, 8, 65, true, false, true, false);
INSERT INTO role_permission VALUES (343, 8, 66, true, false, false, false);
INSERT INTO role_permission VALUES (344, 8, 67, true, false, true, false);
INSERT INTO role_permission VALUES (345, 8, 68, true, false, true, false);
INSERT INTO role_permission VALUES (346, 8, 69, false, false, true, false);
INSERT INTO role_permission VALUES (347, 8, 17, true, false, false, false);
INSERT INTO role_permission VALUES (348, 8, 18, true, true, true, true);
INSERT INTO role_permission VALUES (349, 8, 19, true, true, false, true);
INSERT INTO role_permission VALUES (350, 8, 20, true, true, true, true);
INSERT INTO role_permission VALUES (351, 8, 21, true, true, true, true);
INSERT INTO role_permission VALUES (352, 8, 22, true, false, false, false);
INSERT INTO role_permission VALUES (353, 8, 23, true, true, true, true);
INSERT INTO role_permission VALUES (354, 8, 26, true, false, false, false);
INSERT INTO role_permission VALUES (355, 8, 27, true, false, false, false);
INSERT INTO role_permission VALUES (356, 8, 28, true, false, false, false);
INSERT INTO role_permission VALUES (357, 8, 29, true, true, false, true);
INSERT INTO role_permission VALUES (358, 8, 30, true, false, false, false);
INSERT INTO role_permission VALUES (359, 8, 31, true, false, false, false);
INSERT INTO role_permission VALUES (360, 8, 1, true, false, false, false);
INSERT INTO role_permission VALUES (361, 8, 2, true, true, true, true);
INSERT INTO role_permission VALUES (362, 8, 3, true, true, true, false);
INSERT INTO role_permission VALUES (363, 8, 4, true, true, true, true);
INSERT INTO role_permission VALUES (364, 8, 6, true, false, false, false);
INSERT INTO role_permission VALUES (365, 8, 7, true, false, false, false);
INSERT INTO role_permission VALUES (366, 8, 8, true, false, false, false);
INSERT INTO role_permission VALUES (367, 8, 9, true, false, false, false);
INSERT INTO role_permission VALUES (368, 8, 10, true, false, false, false);
INSERT INTO role_permission VALUES (369, 8, 11, true, true, false, true);
INSERT INTO role_permission VALUES (370, 8, 13, true, true, true, true);
INSERT INTO role_permission VALUES (371, 8, 15, true, false, false, false);
INSERT INTO role_permission VALUES (372, 8, 16, true, false, false, false);
INSERT INTO role_permission VALUES (373, 8, 33, true, false, false, false);
INSERT INTO role_permission VALUES (374, 8, 34, true, false, false, false);
INSERT INTO role_permission VALUES (375, 8, 35, true, true, true, true);
INSERT INTO role_permission VALUES (376, 8, 36, true, true, true, true);
INSERT INTO role_permission VALUES (377, 8, 37, true, true, true, true);
INSERT INTO role_permission VALUES (378, 8, 38, true, true, true, true);
INSERT INTO role_permission VALUES (379, 8, 43, true, false, false, false);
INSERT INTO role_permission VALUES (380, 8, 44, true, false, false, false);
INSERT INTO role_permission VALUES (381, 8, 45, true, true, true, true);
INSERT INTO role_permission VALUES (382, 8, 46, true, false, false, false);
INSERT INTO role_permission VALUES (383, 8, 73, true, false, false, false);
INSERT INTO role_permission VALUES (384, 8, 74, true, false, false, false);
INSERT INTO role_permission VALUES (385, 8, 72, true, false, false, false);
INSERT INTO role_permission VALUES (386, 8, 56, true, false, false, false);
INSERT INTO role_permission VALUES (387, 8, 57, true, false, false, false);
INSERT INTO role_permission VALUES (388, 8, 58, true, false, false, false);
INSERT INTO role_permission VALUES (389, 8, 59, true, false, false, false);
INSERT INTO role_permission VALUES (390, 9, 60, true, false, false, false);
INSERT INTO role_permission VALUES (391, 9, 61, true, false, false, false);
INSERT INTO role_permission VALUES (392, 9, 63, true, false, false, false);
INSERT INTO role_permission VALUES (393, 9, 64, true, true, true, true);
INSERT INTO role_permission VALUES (394, 9, 65, true, false, true, false);
INSERT INTO role_permission VALUES (395, 9, 66, true, false, false, false);
INSERT INTO role_permission VALUES (396, 9, 67, true, false, true, false);
INSERT INTO role_permission VALUES (397, 9, 68, true, false, true, false);
INSERT INTO role_permission VALUES (398, 9, 69, false, false, true, false);
INSERT INTO role_permission VALUES (399, 9, 33, true, false, false, false);
INSERT INTO role_permission VALUES (400, 9, 34, true, false, false, false);
INSERT INTO role_permission VALUES (401, 9, 35, true, true, true, true);
INSERT INTO role_permission VALUES (402, 9, 36, true, true, true, true);
INSERT INTO role_permission VALUES (403, 9, 37, true, true, true, true);
INSERT INTO role_permission VALUES (404, 9, 38, true, true, true, true);
INSERT INTO role_permission VALUES (405, 9, 73, true, true, true, true);
INSERT INTO role_permission VALUES (406, 9, 74, true, true, true, true);
INSERT INTO role_permission VALUES (407, 9, 72, true, false, false, false);
INSERT INTO role_permission VALUES (408, 9, 56, true, false, false, false);
INSERT INTO role_permission VALUES (409, 9, 57, true, false, false, false);
INSERT INTO role_permission VALUES (410, 9, 58, true, false, false, false);
INSERT INTO role_permission VALUES (411, 9, 59, true, false, false, false);


--
-- Data for TOC entry 557 (OID 31357)
-- Name: lookup_stage; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_stage VALUES (1, 1, 'Prospecting', false, 1, true);
INSERT INTO lookup_stage VALUES (2, 2, 'Qualification', false, 2, true);
INSERT INTO lookup_stage VALUES (3, 3, 'Needs Analysis', false, 3, true);
INSERT INTO lookup_stage VALUES (4, 4, 'Value Proposition', false, 4, true);
INSERT INTO lookup_stage VALUES (5, 5, 'Perception Analysis', false, 5, true);
INSERT INTO lookup_stage VALUES (6, 6, 'Proposal/Price Quote', false, 6, true);
INSERT INTO lookup_stage VALUES (7, 7, 'Negotiation/Review', false, 7, true);
INSERT INTO lookup_stage VALUES (8, 8, 'Closed Won', false, 8, true);
INSERT INTO lookup_stage VALUES (9, 9, 'Closed Lost', false, 9, true);


--
-- Data for TOC entry 558 (OID 31367)
-- Name: lookup_delivery_options; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_delivery_options VALUES (1, 'Email only', false, 1, true);
INSERT INTO lookup_delivery_options VALUES (2, 'Fax only', false, 2, true);
INSERT INTO lookup_delivery_options VALUES (3, 'Letter only', false, 3, true);
INSERT INTO lookup_delivery_options VALUES (4, 'Email then Fax', false, 4, true);
INSERT INTO lookup_delivery_options VALUES (5, 'Email then Letter', false, 5, true);
INSERT INTO lookup_delivery_options VALUES (6, 'Email, Fax, then Letter', false, 6, true);


--
-- Data for TOC entry 559 (OID 31377)
-- Name: news; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 560 (OID 31392)
-- Name: organization_address; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 561 (OID 31417)
-- Name: organization_emailaddress; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 562 (OID 31442)
-- Name: organization_phone; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 563 (OID 31467)
-- Name: contact_address; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 564 (OID 31492)
-- Name: contact_emailaddress; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 565 (OID 31517)
-- Name: contact_phone; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 566 (OID 31542)
-- Name: notification; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 567 (OID 31554)
-- Name: cfsinbox_message; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 568 (OID 31575)
-- Name: cfsinbox_messagelink; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 569 (OID 31591)
-- Name: account_type_levels; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 570 (OID 31603)
-- Name: contact_type_levels; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 571 (OID 31617)
-- Name: lookup_lists_lookup; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_lists_lookup VALUES (1, 1, 1, 'lookupList', 'lookup_account_types', 10, 'Account Types', '2003-12-10 13:43:56.259', 1);
INSERT INTO lookup_lists_lookup VALUES (2, 1, 2, 'lookupList', 'lookup_revenue_types', 20, 'Revenue Types', '2003-12-10 13:43:56.271', 1);
INSERT INTO lookup_lists_lookup VALUES (3, 1, 3, 'contactType', 'lookup_contact_types', 30, 'Contact Types', '2003-12-10 13:43:56.275', 1);
INSERT INTO lookup_lists_lookup VALUES (4, 2, 1, 'contactType', 'lookup_contact_types', 10, 'Types', '2003-12-10 13:43:56.459', 2);
INSERT INTO lookup_lists_lookup VALUES (5, 2, 2, 'lookupList', 'lookup_contactemail_types', 20, 'Email Types', '2003-12-10 13:43:56.465', 2);
INSERT INTO lookup_lists_lookup VALUES (6, 2, 3, 'lookupList', 'lookup_contactaddress_types', 30, 'Address Types', '2003-12-10 13:43:56.469', 2);
INSERT INTO lookup_lists_lookup VALUES (7, 2, 4, 'lookupList', 'lookup_contactphone_types', 40, 'Phone Types', '2003-12-10 13:43:56.473', 2);
INSERT INTO lookup_lists_lookup VALUES (8, 4, 1, 'lookupList', 'lookup_stage', 10, 'Stage', '2003-12-10 13:43:56.582', 4);
INSERT INTO lookup_lists_lookup VALUES (9, 4, 2, 'lookupList', 'lookup_opportunity_types', 20, 'Opportunity Types', '2003-12-10 13:43:56.586', 4);
INSERT INTO lookup_lists_lookup VALUES (10, 8, 1, 'lookupList', 'lookup_ticketsource', 10, 'Ticket Source', '2003-12-10 13:43:56.785', 8);
INSERT INTO lookup_lists_lookup VALUES (11, 8, 2, 'lookupList', 'ticket_severity', 20, 'Ticket Severity', '2003-12-10 13:43:56.789', 8);
INSERT INTO lookup_lists_lookup VALUES (12, 8, 3, 'lookupList', 'ticket_priority', 30, 'Ticket Priority', '2003-12-10 13:43:56.792', 8);
INSERT INTO lookup_lists_lookup VALUES (13, 15, 1111031132, 'lookupList', 'lookup_department', 10, 'Departments', '2003-12-10 13:43:57.071', 15);


--
-- Data for TOC entry 572 (OID 31633)
-- Name: viewpoint; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 573 (OID 31659)
-- Name: viewpoint_permission; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 574 (OID 31678)
-- Name: report; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO report VALUES (1, 1, NULL, 'accounts_type.xml', 1, 'Accounts by Type', 'What are my accounts by type?', '2003-12-10 13:43:56.28', 0, '2003-12-10 13:43:56.28', 0, true, false);
INSERT INTO report VALUES (2, 1, NULL, 'accounts_recent.xml', 1, 'Accounts by Date Added', 'What are my recent accounts?', '2003-12-10 13:43:56.319', 0, '2003-12-10 13:43:56.319', 0, true, false);
INSERT INTO report VALUES (3, 1, NULL, 'accounts_expire.xml', 1, 'Accounts by Contract End Date', 'Which accounts are expiring?', '2003-12-10 13:43:56.324', 0, '2003-12-10 13:43:56.324', 0, true, false);
INSERT INTO report VALUES (4, 1, NULL, 'accounts_current.xml', 1, 'Current Accounts', 'What are my current accounts?', '2003-12-10 13:43:56.329', 0, '2003-12-10 13:43:56.329', 0, true, false);
INSERT INTO report VALUES (5, 1, NULL, 'accounts_contacts.xml', 1, 'Account Contacts', 'Who are the contacts in each account?', '2003-12-10 13:43:56.336', 0, '2003-12-10 13:43:56.336', 0, true, false);
INSERT INTO report VALUES (6, 1, NULL, 'folder_accounts.xml', 1, 'Account Folders', 'What is the folder data for each account?', '2003-12-10 13:43:56.341', 0, '2003-12-10 13:43:56.341', 0, true, false);
INSERT INTO report VALUES (7, 2, NULL, 'contacts_user.xml', 1, 'Contacts', 'Who are my contacts?', '2003-12-10 13:43:56.477', 0, '2003-12-10 13:43:56.477', 0, true, false);
INSERT INTO report VALUES (8, 4, NULL, 'opportunity_pipeline.xml', 1, 'Opportunities by Stage', 'What are my upcoming opportunities by stage?', '2003-12-10 13:43:56.59', 0, '2003-12-10 13:43:56.59', 0, true, false);
INSERT INTO report VALUES (9, 4, NULL, 'opportunity_account.xml', 1, 'Opportunities by Account', 'What are all the accounts associated with my opportunities?', '2003-12-10 13:43:56.594', 0, '2003-12-10 13:43:56.594', 0, true, false);
INSERT INTO report VALUES (10, 4, NULL, 'opportunity_owner.xml', 1, 'Opportunities by Owner', 'What are all the opportunities based on ownership?', '2003-12-10 13:43:56.599', 0, '2003-12-10 13:43:56.599', 0, true, false);
INSERT INTO report VALUES (11, 4, NULL, 'opportunity_contact.xml', 1, 'Opportunity Contacts', 'Who are the contacts of my opportunities?', '2003-12-10 13:43:56.603', 0, '2003-12-10 13:43:56.603', 0, true, false);
INSERT INTO report VALUES (12, 6, NULL, 'campaign.xml', 1, 'Campaigns by date', 'What are my active campaigns?', '2003-12-10 13:43:56.694', 0, '2003-12-10 13:43:56.694', 0, true, false);
INSERT INTO report VALUES (13, 8, NULL, 'tickets_department.xml', 1, 'Tickets by Department', 'What tickets are there in each department?', '2003-12-10 13:43:56.796', 0, '2003-12-10 13:43:56.796', 0, true, false);
INSERT INTO report VALUES (14, 8, NULL, 'ticket_summary_date.xml', 1, 'Ticket counts by Department', 'How man tickets are there in the system on a particular date?', '2003-12-10 13:43:56.80', 0, '2003-12-10 13:43:56.80', 0, true, false);
INSERT INTO report VALUES (15, 8, NULL, 'ticket_summary_range.xml', 1, 'Ticket activity by Department', 'How many tickets exist within a date range?', '2003-12-10 13:43:56.804', 0, '2003-12-10 13:43:56.804', 0, true, false);
INSERT INTO report VALUES (16, 9, NULL, 'users.xml', 1, 'System Users', 'Who are all the users of the system?', '2003-12-10 13:43:56.904', 0, '2003-12-10 13:43:56.904', 0, true, false);
INSERT INTO report VALUES (17, 12, NULL, 'task_date.xml', 1, 'Task list by due date', 'What are the tasks due withing a date range?', '2003-12-10 13:43:57.015', 0, '2003-12-10 13:43:57.015', 0, true, false);
INSERT INTO report VALUES (18, 12, NULL, 'task_nodate.xml', 1, 'Task list', 'What are all the tasks in the system?', '2003-12-10 13:43:57.019', 0, '2003-12-10 13:43:57.019', 0, true, false);
INSERT INTO report VALUES (19, 15, NULL, 'employees.xml', 1, 'Employees', 'Who are the employees in my organization?', '2003-12-10 13:43:57.074', 0, '2003-12-10 13:43:57.074', 0, true, false);


--
-- Data for TOC entry 575 (OID 31709)
-- Name: report_criteria; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 576 (OID 31735)
-- Name: report_criteria_parameter; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 577 (OID 31749)
-- Name: report_queue; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 578 (OID 31768)
-- Name: report_queue_criteria; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 579 (OID 31782)
-- Name: action_list; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 580 (OID 31804)
-- Name: action_item; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 581 (OID 31826)
-- Name: action_item_log; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 582 (OID 31848)
-- Name: database_version; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO database_version VALUES (1, 'att/source/dhv/cfs2/src/sql/init/workflow.bsh', 'att/source/dhv/cfs2/src/sql/init/workflow', '2003-12-10 13:44:13.187');


--
-- Data for TOC entry 583 (OID 31973)
-- Name: lookup_call_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_call_types VALUES (1, 'Phone Call', true, 10, true);
INSERT INTO lookup_call_types VALUES (2, 'Fax', false, 20, true);
INSERT INTO lookup_call_types VALUES (3, 'In-Person', false, 30, true);


--
-- Data for TOC entry 584 (OID 31983)
-- Name: lookup_opportunity_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_opportunity_types VALUES (1, NULL, 'Type 1', false, 0, true);
INSERT INTO lookup_opportunity_types VALUES (2, NULL, 'Type 2', false, 1, true);
INSERT INTO lookup_opportunity_types VALUES (3, NULL, 'Type 3', false, 2, true);
INSERT INTO lookup_opportunity_types VALUES (4, NULL, 'Type 4', false, 3, true);


--
-- Data for TOC entry 585 (OID 31993)
-- Name: opportunity_header; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 586 (OID 32012)
-- Name: opportunity_component; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 587 (OID 32046)
-- Name: opportunity_component_levels; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 588 (OID 32060)
-- Name: call_log; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 589 (OID 32113)
-- Name: ticket_level; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO ticket_level VALUES (1, 'Entry level', false, 0, true);
INSERT INTO ticket_level VALUES (2, 'First level', false, 1, true);
INSERT INTO ticket_level VALUES (3, 'Second level', false, 2, true);
INSERT INTO ticket_level VALUES (4, 'Third level', false, 3, true);
INSERT INTO ticket_level VALUES (5, 'Top level', false, 4, true);


--
-- Data for TOC entry 590 (OID 32125)
-- Name: ticket_severity; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO ticket_severity VALUES (1, 'Normal', 'background-color:lightgreen;color:black;', false, 0, true);
INSERT INTO ticket_severity VALUES (2, 'Important', 'background-color:yellow;color:black;', false, 1, true);
INSERT INTO ticket_severity VALUES (3, 'Critical', 'background-color:red;color:black;font-weight:bold;', false, 2, true);


--
-- Data for TOC entry 591 (OID 32141)
-- Name: lookup_ticketsource; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_ticketsource VALUES (1, 'Phone', false, 1, true);
INSERT INTO lookup_ticketsource VALUES (2, 'Email', false, 2, true);
INSERT INTO lookup_ticketsource VALUES (3, 'Letter', false, 3, true);
INSERT INTO lookup_ticketsource VALUES (4, 'Other', false, 4, true);


--
-- Data for TOC entry 592 (OID 32153)
-- Name: ticket_priority; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO ticket_priority VALUES (1, 'Scheduled', 'background-color:lightgreen;color:black;', false, 0, true);
INSERT INTO ticket_priority VALUES (2, 'Next', 'background-color:yellow;color:black;', false, 1, true);
INSERT INTO ticket_priority VALUES (3, 'Immediate', 'background-color:red;color:black;font-weight:bold;', false, 2, true);


--
-- Data for TOC entry 593 (OID 32169)
-- Name: ticket_category; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO ticket_category VALUES (1, 0, 0, 'Sales', '', false, 1, true);
INSERT INTO ticket_category VALUES (2, 0, 0, 'Billing', '', false, 2, true);
INSERT INTO ticket_category VALUES (3, 0, 0, 'Technical', '', false, 3, true);
INSERT INTO ticket_category VALUES (4, 0, 0, 'Order', '', false, 4, true);
INSERT INTO ticket_category VALUES (5, 0, 0, 'Other', '', false, 5, true);


--
-- Data for TOC entry 594 (OID 32184)
-- Name: ticket_category_draft; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 595 (OID 32200)
-- Name: ticket; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 596 (OID 32254)
-- Name: ticketlog; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 597 (OID 32314)
-- Name: module_field_categorylink; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO module_field_categorylink VALUES (1, 1, 1, 10, 'Accounts', '2003-12-10 13:43:56.241');
INSERT INTO module_field_categorylink VALUES (2, 2, 2, 10, 'Contacts', '2003-12-10 13:43:56.455');


--
-- Data for TOC entry 598 (OID 32332)
-- Name: custom_field_category; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 599 (OID 32354)
-- Name: custom_field_group; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 600 (OID 32373)
-- Name: custom_field_info; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 601 (OID 32394)
-- Name: custom_field_lookup; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 602 (OID 32410)
-- Name: custom_field_record; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 603 (OID 32431)
-- Name: custom_field_data; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 604 (OID 32448)
-- Name: lookup_project_activity; Type: TABLE DATA; Schema: public; Owner: matt
--

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


--
-- Data for TOC entry 605 (OID 32460)
-- Name: lookup_project_priority; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_project_priority VALUES (1, 'Low', false, 1, true, 0, NULL, 10);
INSERT INTO lookup_project_priority VALUES (2, 'Normal', true, 2, true, 0, NULL, 20);
INSERT INTO lookup_project_priority VALUES (3, 'High', false, 3, true, 0, NULL, 30);


--
-- Data for TOC entry 606 (OID 32471)
-- Name: lookup_project_issues; Type: TABLE DATA; Schema: public; Owner: matt
--

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


--
-- Data for TOC entry 607 (OID 32482)
-- Name: lookup_project_status; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_project_status VALUES (1, 'Not Started', false, 1, true, 0, 'box.gif', 1);
INSERT INTO lookup_project_status VALUES (2, 'In Progress', false, 2, true, 0, 'box.gif', 2);
INSERT INTO lookup_project_status VALUES (3, 'On Hold', false, 5, true, 0, 'box-hold.gif', 5);
INSERT INTO lookup_project_status VALUES (4, 'Waiting on Reqs', false, 6, true, 0, 'box-hold.gif', 5);
INSERT INTO lookup_project_status VALUES (5, 'Complete', false, 3, true, 0, 'box-checked.gif', 3);
INSERT INTO lookup_project_status VALUES (6, 'Closed', false, 4, true, 0, 'box-checked.gif', 4);


--
-- Data for TOC entry 608 (OID 32493)
-- Name: lookup_project_loe; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_project_loe VALUES (1, 'Minute(s)', 60, false, 1, true, 0);
INSERT INTO lookup_project_loe VALUES (2, 'Hour(s)', 3600, true, 1, true, 0);
INSERT INTO lookup_project_loe VALUES (3, 'Day(s)', 86400, false, 1, true, 0);
INSERT INTO lookup_project_loe VALUES (4, 'Week(s)', 604800, false, 1, true, 0);
INSERT INTO lookup_project_loe VALUES (5, 'Month(s)', 18144000, false, 1, true, 0);


--
-- Data for TOC entry 609 (OID 32505)
-- Name: projects; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 610 (OID 32528)
-- Name: project_requirements; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 611 (OID 32568)
-- Name: project_assignments; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 612 (OID 32625)
-- Name: project_assignments_status; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 613 (OID 32644)
-- Name: project_issues; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 614 (OID 32676)
-- Name: project_issue_replies; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 615 (OID 32701)
-- Name: project_folders; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 616 (OID 32711)
-- Name: project_files; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 617 (OID 32738)
-- Name: project_files_version; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 618 (OID 32761)
-- Name: project_files_download; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 619 (OID 32773)
-- Name: project_team; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 620 (OID 32834)
-- Name: saved_criterialist; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 621 (OID 32857)
-- Name: campaign; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 622 (OID 32887)
-- Name: campaign_run; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 623 (OID 32904)
-- Name: excluded_recipient; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 624 (OID 32917)
-- Name: campaign_list_groups; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 625 (OID 32929)
-- Name: active_campaign_groups; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 626 (OID 32943)
-- Name: scheduled_recipient; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 627 (OID 32962)
-- Name: lookup_survey_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_survey_types VALUES (1, 'Open-Ended', false, 0, true);
INSERT INTO lookup_survey_types VALUES (2, 'Quantitative (no comments)', false, 0, true);
INSERT INTO lookup_survey_types VALUES (3, 'Quantitative (with comments)', false, 0, true);
INSERT INTO lookup_survey_types VALUES (4, 'Item List', false, 0, true);


--
-- Data for TOC entry 628 (OID 32972)
-- Name: survey; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 629 (OID 32994)
-- Name: campaign_survey_link; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 630 (OID 33006)
-- Name: survey_questions; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 631 (OID 33023)
-- Name: survey_items; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 632 (OID 33035)
-- Name: active_survey; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 633 (OID 33065)
-- Name: active_survey_questions; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 634 (OID 33090)
-- Name: active_survey_items; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 635 (OID 33102)
-- Name: active_survey_responses; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 636 (OID 33115)
-- Name: active_survey_answers; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 637 (OID 33134)
-- Name: active_survey_answer_items; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 638 (OID 33152)
-- Name: active_survey_answer_avg; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 639 (OID 33168)
-- Name: field_types; Type: TABLE DATA; Schema: public; Owner: matt
--

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


--
-- Data for TOC entry 640 (OID 33177)
-- Name: search_fields; Type: TABLE DATA; Schema: public; Owner: matt
--

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


--
-- Data for TOC entry 641 (OID 33187)
-- Name: message; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 642 (OID 33212)
-- Name: message_template; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 643 (OID 33228)
-- Name: saved_criteriaelement; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 644 (OID 33286)
-- Name: help_module; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO help_module VALUES (12, 12, 'This is the "Home Page". The Top set of Tabs shows the individual modules that you can access. The second row are the functions available to you in this module.
If you are looking for a particular module or function you have seen during training or on someone else''s machine, and it''s NOT there it probably means that you don''t have permission for that module or function.
Permissions within the system are assigned to Roles and then Users are assigned to Roles by the System Administrator.
If you are looking for a module and can''t find it, it generally means you dont have permission to it.

', 'This Home Page system has six main features.

Welcome: This is the welcome page. This displays the different modules present in the system, based on their titles and the corresponding security for the title. For a particular user all the scheduled actions for the next seven days are displayed in this page.

Mailbox: The Message System is designed to support INTERNAL business messaging needs and to prepare OUTGOING emails to addresses who are already in the system. Messaging is NOT a normal email replacement. It will not send email to address assigned "on-the-fly" and it will not receive OUTSIDE email

Tasks: The Task System is designed to create and assign tasks. Tasks created can be assigned to the owner/creator of the task or an employee working in the system. This page lists the tasks present along with their priorities, their due dates and age.

Action Lists: The Action List System is designed to create new Action Lists and assign contacts to the Action Lists created. For each of the contacts in a List, you can add a call, opportunity, ticket, task or send a message, which would correspondingly show up in their respective tabs. For example, adding a ticket to the contact would be reflected in the Ticket tab.

Re-assignments: A user can reassign data from one employee to another employee working under him. The data can be of different types related to accounts, contacts opportunities, activities, tickets etc, which the newly reassigned employee could view in his schedule.

Settings: This is the personal settings system page, where in you can modify/update the information about yourself, your location and also change your password to the system ');
INSERT INTO help_module VALUES (2, 2, 'This is the Contacts system.

The purpose of this system is for the users to view contacts and add new contacts into the existing system. You can search for contacts in the existing list and also export the data present in the system to different formats.

', 'This Contacts system has three main features

Add: A new contact can be added into the system. The contact can be a general contact, or one that is associated with an account. All the details about the contact like the email address, phone numbers, address and some additional details can be added.

Search: Use this page to search for contacts existing in the system based on different filters

Export: Contact data can be exported and displayed in different formats, and can be filtered in different ways. The Export page also lets you view the data, download it, and show the number of times the exported data has been downloaded. ');
INSERT INTO help_module VALUES (4, 4, 'This is the Pipeline System

Pipeline helps in creating opportunities or leads in the company. Each opportunity helps to follow up a lead, who might eventually turn into a client. Here you can create an opportunity, search for an existing opportunity in the system, or export the data to different formats. The dashboard reflects the progress chart in different views for all the employees working under the hierarchy of the owner/creator of the opportunity.

', 'This Pipeline System has four main features:

Dashboard: Gives you a quick, visual overview of the whole Pipeline System.

Add: This page lets you add an opportunity into the system. Here a new opportunity can be added by giving the description of the opportunity. You can add a component that is associated with the opportunity. Each component can be assigned to an employee and the type of the component can be selected. For each component the probability of closing the component, the date estimated, the best guess for closing the deal and the duration for that component can be entered.

Search: You can search for an opportunity already existing in the system based on different filters.

Export: The data can be exported and displayed in different formats. The exported data can be filtered in different ways. ');
INSERT INTO help_module VALUES (1, 1, 'This is the Accounts System

You are looking at Accounts home, which has a dashboard view of all your accounts. This view is based on a date range selected from the calendar; by default it shows the schedule for the next seven days. You can optionally view the calendar of those below you in your hierarchy. The scheduled actions of each user can also be viewed. In the Accounts Module, new accounts can be added, existing accounts can be searched based on different filters, revenue for each account can be created/maintained and finally, data can be exported to different formats

', 'The Accounts System has five main features

Dashboard: Displays a dashboard view of the whole Account System.

Add: Add new Account

Search: This page provides a search feature for the accounts present in the system

Revenue: Graphically visualizes revenue if the historical data is present in the system. All the accounts with revenue are shown along with a list of employees working under you under the progress chart. You can add accounts, search for the existing ones in the system based on different filters and export the data to different formats

Export: Data can be exported and displayed in different formats. The exported data can be filtered in different ways. You can view the data, download it, and see the number of times an exported report has been downloaded ');
INSERT INTO help_module VALUES (8, 8, 'This is the Tickets System

You are looking at the ticket System home page. The dashboard shows the most recent tickets that have been assigned to you, as well as tickets that are in your department, and tickets that have been created by you. You can add new tickets, search for the existing ones based on different filters and export the data to different formats

', 'This Tickets System has four main features

View: Lists all the tickets assigned to you and the ones assigned in your department. The details like the ticket number, priority, age i.e. how old the ticket is, the company and the tickets assignment are displayed.

Add: You can add a new ticket here

Search: Form used for searching the tickets that already exist in the system based on different filters and parameters

Export: This is the page shows exported data. The data can be exported to different formats. The exported data can be viewed with its subject, the size of the exported data file, when it was created and by whom. It also shows the number of times that particular exported file is downloaded. The exported data, created by you or all the employees can be viewed in two different views. A new data file can also be exported. ');
INSERT INTO help_module VALUES (15, 15, 'You are looking at the Employee System home page. This page displays the details of all the employees present in the system.', ' 
The main feature of the employee module is the view.

View: This page displays the details of each of the existing employee, which can be viewed, modified or deleted. Each employee record contains details like the name of the employee, the department they are in, his title and phone number. This also lets you add a new employee into the system');
INSERT INTO help_module VALUES (14, 14, 'This is the Reports system.

You are looking at the Reports System home page. This page displays the list of generated reports that are ready to be downloaded. It also displays the list of reports, which are scheduled to be processed by server. You can add new reports too.

', 'This Reports System has two main features.

Queue: Queue shows the list of reports that are scheduled to be processed by server

Add: This shows the different modules present and displays the list of corresponding reports present in each module. ');
INSERT INTO help_module VALUES (9, 9, 'The admin module lets the user review the system usage, configure the specific modules and also configure the global/system parameters.', 'This Admin System has five main features.

Users: This section allows the administrator to view and add users and manage user hierarchies. The users are typically employees in your company who interact with your clients or customers.

Roles: This page lists the different roles present in the system, their role description and the number of people present in the system who carry out that role. New roles can be added into the system

Modules: This page lets you configure modules that meet the needs of your organization including configuration of lookup lists and custom fields. There are four types of modules. Each module has different number of configure options. The changes in the module affect all the users

System: You can configure the system for the session timeout and set the time for the time out

Usage: Current System Usage and Billing Usage Information is displayed
 
');
INSERT INTO help_module VALUES (3, 3, 'Auto Guide Brief', 'Auto Guide Detail');
INSERT INTO help_module VALUES (10, 10, 'Help Brief', 'Help Detail');
INSERT INTO help_module VALUES (5, 5, 'Demo Brief', 'Demo Detail');
INSERT INTO help_module VALUES (11, 11, 'System Brief', 'System Detail');


--
-- Data for TOC entry 645 (OID 33300)
-- Name: help_contents; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO help_contents VALUES (1, 12, 12, 'MyCFS.do', 'Home', NULL, 'Overview', 'You are looking at Home Page view, which has a dashboard view of all your assigned tasks, tickets, assignments, calls you have to make and accounts, which need attention. This view is based on a time selected from the calendar; by default it shows the schedule for the next seven days. You can optionally view the calendar of those below you.
 
', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.031', 0, '2003-12-10 13:44:03.031', true);
INSERT INTO help_contents VALUES (2, 12, 12, 'MyCFSInbox.do', 'Inbox', NULL, 'My Mailbox ', 'This is the  Message System.  
The  Message System is designed to support INTERNAL business messaging needs and to prepare OUTGOING emails to addresses who are already in the system. Messaging is NOT a normal email replacement.  It will not send email to address assigned "on-the-fly"  and it will not receive OUTSIDE email.', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.308', 0, '2003-12-10 13:44:03.308', true);
INSERT INTO help_contents VALUES (3, 12, 12, 'MyCFSInbox.do', 'CFSNoteDetails', NULL, 'Message Details', 'This page shows the  message details, shows who sent the mail, when it was received and also the text in the mail box.', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.348', 0, '2003-12-10 13:44:03.348', true);
INSERT INTO help_contents VALUES (4, 12, 12, 'MyCFSInbox.do', 'NewMessage', NULL, 'New Message ', 'Sending a email to the recipients present in the system', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.366', 0, '2003-12-10 13:44:03.366', true);
INSERT INTO help_contents VALUES (5, 12, 12, 'MyCFSInbox.do', 'ReplyToMessage', NULL, 'Reply Message', 'This pages lets you reply to the email. You can also select the list of recipients for your email by clicking the Add Recipients link.', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.421', 0, '2003-12-10 13:44:03.421', true);
INSERT INTO help_contents VALUES (6, 12, 12, 'MyCFSInbox.do', 'SendMessage', NULL, NULL, 'This page shows the list of recipients for whom your email has been sent to', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.448', 0, '2003-12-10 13:44:03.448', true);
INSERT INTO help_contents VALUES (7, 12, 12, 'MyCFSInbox.do', 'ForwardMessage', NULL, 'Forward message', 'Each message can be forwarded to any number of recipients', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.467', 0, '2003-12-10 13:44:03.467', true);
INSERT INTO help_contents VALUES (8, 12, 12, 'MyTasks.do', NULL, NULL, 'My Tasks ', 'This is the Task System. 

The Task System is designed to create and assign tasks.  Tasks created can be assigned to the owner/creator of the task or an employee working in the system. This page lists the tasks present along with their priorities, their due dates and age.', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.494', 0, '2003-12-10 13:44:03.494', true);
INSERT INTO help_contents VALUES (9, 12, 12, 'MyTasks.do', 'New', NULL, 'Advanced Task', 'Allows an advanced task to be created', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.558', 0, '2003-12-10 13:44:03.558', true);
INSERT INTO help_contents VALUES (10, 12, 12, 'MyTasksForward.do', 'ForwardMessage', NULL, 'Forwarding a Task ', 'A task can be forwarded to any of the recipients. The recipients can be either the users of the system or any of the contacts not present in the system. Checking the options fields check box indicates that if the recipient is a user of the system, then a copy of the task is also send to the recipient''s mailbox.', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.59', 0, '2003-12-10 13:44:03.59', true);
INSERT INTO help_contents VALUES (11, 12, 12, 'MyTasks.do', 'Modify', NULL, 'Modify task', 'Allows you to modify any infomation about a task', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.635', 0, '2003-12-10 13:44:03.635', true);
INSERT INTO help_contents VALUES (12, 12, 12, 'MyActionLists.do', 'List', NULL, 'My Action Lists ', 'This is the Action List System. 

The Action List System is designed to create new Action Lists and assign contacts to the Action Lists created. For each of the contacts in a List, you can add a call, opportunity, ticket, task or send a message, which would correspondingly show up in their respective tabs. For example, adding a ticket to the contact would be reflected in the Ticket tab.', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.665', 0, '2003-12-10 13:44:03.665', true);
INSERT INTO help_contents VALUES (13, 12, 12, 'MyActionContacts.do', 'List', NULL, 'Action Contacts', 'This page will list out all the contacts present for the particular contact list. This also shows the status of the call, opportunity, ticket, task, or the message associated with the contact. This also shows when the contact information was last updated.', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.686', 0, '2003-12-10 13:44:03.686', true);
INSERT INTO help_contents VALUES (14, 12, 12, 'MyActionLists.do', 'Add', NULL, 'Add Action List', 'Allows you to add an action list. Basically, you describe the group of contacts you will add, then design a query to generate the group of contacts.', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.756', 0, '2003-12-10 13:44:03.756', true);
INSERT INTO help_contents VALUES (15, 12, 12, 'MyActionLists.do', 'Modify', NULL, 'Modify Action', 'The Action Lists details like the description and the status of the Action Lists can be modified.', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.79', 0, '2003-12-10 13:44:03.79', true);
INSERT INTO help_contents VALUES (16, 12, 12, 'Reassignments.do', 'Reassign', NULL, 'Re-assignments ', 'This is the Reassignment system. 

A user can reassign data from one employee to another employee working under him. The data can be of different types related to accounts, contacts opportunities, activities, tickets etc, which the newly reassigned employee could view in his schedule. ', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.807', 0, '2003-12-10 13:44:03.807', true);
INSERT INTO help_contents VALUES (17, 12, 12, 'MyCFS.do', 'MyProfile', NULL, 'My Settings ', 'This is the personal settings system page, where in you can modify/update the information about yourself, your location and also change your password to the system.', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.846', 0, '2003-12-10 13:44:03.846', true);
INSERT INTO help_contents VALUES (18, 12, 12, 'MyCFSProfile.do', 'MyCFSProfile', NULL, 'Personal Information ', 'This page lets you update/add your personal information', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.865', 0, '2003-12-10 13:44:03.865', true);
INSERT INTO help_contents VALUES (19, 12, 12, 'MyCFSSettings.do', 'MyCFSSettings', NULL, 'Location Settings', 'You can change your location settings', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.882', 0, '2003-12-10 13:44:03.882', true);
INSERT INTO help_contents VALUES (20, 12, 12, 'MyCFSPassword.do', 'MyCFSPassword', NULL, 'Update password', 'Your password to the system can be changed', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.891', 0, '2003-12-10 13:44:03.891', true);
INSERT INTO help_contents VALUES (21, 12, 12, 'MyTasks.do', 'ListTasks', NULL, NULL, 'This is the Action List System. 

The Action List System is designed to create new Action List and assign contacts to the Action list created. For each of the contacts you can add a call, opportunity, ticket, task or send a message, which would correspondingly show up in their respective tabs. For e.g. adding a ticket to the contact would be reflected in the Ticket tab.
', NULL, NULL, NULL, 0, '2003-12-10 13:44:03.908', 0, '2003-12-10 13:44:03.908', true);
INSERT INTO help_contents VALUES (22, 12, 12, 'MyTasks.do ', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:03.962', 0, '2003-12-10 13:44:03.962', true);
INSERT INTO help_contents VALUES (23, 12, 12, 'MyCFS.do', 'Home ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:03.97', 0, '2003-12-10 13:44:03.97', true);
INSERT INTO help_contents VALUES (24, 12, 12, 'MyTasksForward.do', 'SendMessage', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:03.975', 0, '2003-12-10 13:44:03.975', true);
INSERT INTO help_contents VALUES (25, 12, 12, 'MyCFSProfile.do', 'UpdateProfile', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:03.984', 0, '2003-12-10 13:44:03.984', true);
INSERT INTO help_contents VALUES (26, 12, 12, 'MyCFSInbox.do', 'CFSNoteTrash', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:03.995', 0, '2003-12-10 13:44:03.995', true);
INSERT INTO help_contents VALUES (27, 12, 12, 'MyActionContacts.do', 'Update', NULL, NULL, 'No introduction available', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.001', 0, '2003-12-10 13:44:04.001', true);
INSERT INTO help_contents VALUES (28, 12, 12, 'MyActionContacts.do', 'Prepare', NULL, NULL, 'You can add the new Action List and also select the contacts to be present in the Action List.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.009', 0, '2003-12-10 13:44:04.009', true);
INSERT INTO help_contents VALUES (29, 12, 12, 'MyTasks.do', 'ListTasks ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.026', 0, '2003-12-10 13:44:04.026', true);
INSERT INTO help_contents VALUES (30, 12, 12, 'MyCFSInbox.do', 'Inbox ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.031', 0, '2003-12-10 13:44:04.031', true);
INSERT INTO help_contents VALUES (31, 12, 12, 'MyActionContacts.do', 'Modify', NULL, 'Modify Action List', 'Allows you to manually add or remove contacts to or from an existing Action List.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.036', 0, '2003-12-10 13:44:04.036', true);
INSERT INTO help_contents VALUES (32, 12, 12, 'Reassignments.do', 'DoReassign', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.053', 0, '2003-12-10 13:44:04.053', true);
INSERT INTO help_contents VALUES (33, 12, 12, 'TaskForm.do', 'Prepare', NULL, NULL, 'Addition of a new Advanced task which can be assigned to the owner itself or any other employee working  under him', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.059', 0, '2003-12-10 13:44:04.059', true);
INSERT INTO help_contents VALUES (34, 2, 2, 'ExternalContacts.do', 'Prepare', NULL, 'Add a Contact', 'A new contact can be added into the system. The contact can be a general contact, or one that is associated with an account. All the details about the contact like the email address, phone numbers, address and some additional details can be added.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.089', 0, '2003-12-10 13:44:04.089', true);
INSERT INTO help_contents VALUES (35, 2, 2, 'ExternalContacts.do', 'SearchContactsForm', NULL, 'Search Contacts ', 'Use this page to search for contacts existing in the system based on different filters', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.166', 0, '2003-12-10 13:44:04.166', true);
INSERT INTO help_contents VALUES (36, 2, 2, 'ExternalContacts.do', 'Reports', NULL, 'Export Data ', 'Contact data can be exported and displayed in different formats, and can be filtered in different ways. The Export page also lets you view the data, download it, and shows the number of times the exported data has been downloaded.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.186', 0, '2003-12-10 13:44:04.186', true);
INSERT INTO help_contents VALUES (37, 2, 2, 'ExternalContacts.do', 'GenerateForm', NULL, 'Exporting data', 'Use this page to generate a Contacts export report.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.206', 0, '2003-12-10 13:44:04.206', true);
INSERT INTO help_contents VALUES (38, 2, 2, 'ExternalContacts.do', 'ModifyContact', NULL, 'Modify Contact ', 'The details about the contact can be modified here.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.24', 0, '2003-12-10 13:44:04.24', true);
INSERT INTO help_contents VALUES (39, 2, 2, 'ExternalContacts.do ', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.253', 0, '2003-12-10 13:44:04.253', true);
INSERT INTO help_contents VALUES (40, 2, 2, 'ExternalContacts.do', 'Save', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.259', 0, '2003-12-10 13:44:04.259', true);
INSERT INTO help_contents VALUES (41, 2, 2, 'ExternalContactsCalls.do', 'View', NULL, 'Call Details', 'The calls related to the contact are listed here along with the other details like the length of the call and the date when the call was made. You can also add a call.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.275', 0, '2003-12-10 13:44:04.275', true);
INSERT INTO help_contents VALUES (42, 2, 2, 'ExternalContactsCalls.do', 'Add', NULL, 'Adding a Call', 'New call can be added which is associated with the contact. The type of call can be selected using the drop down list present', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.295', 0, '2003-12-10 13:44:04.295', true);
INSERT INTO help_contents VALUES (43, 2, 2, 'ExternalContactsCallsForward.do', 'ForwardMessage', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.305', 0, '2003-12-10 13:44:04.305', true);
INSERT INTO help_contents VALUES (44, 2, 2, 'ExternalContactsCallsForward.do', 'SendMessage', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.314', 0, '2003-12-10 13:44:04.314', true);
INSERT INTO help_contents VALUES (45, 2, 2, 'ExternalContactsOpps.do', 'ViewOpps', NULL, 'Opportunity Details', 'All the opportunities associated with the contact are shown here, with its best possible total and the when the opportunity was last modified. You can filter the different types of opportunities that can be selected using the drop down and display them.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.319', 0, '2003-12-10 13:44:04.319', true);
INSERT INTO help_contents VALUES (46, 2, 2, 'ExternalContactsCalls.do', 'Details', NULL, 'Call Details', 'Calls associated to the contacts are displayed. The call details are shown with the length of the call, the notes associated, alert description, alert date etc.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.351', 0, '2003-12-10 13:44:04.351', true);
INSERT INTO help_contents VALUES (47, 2, 2, 'ExternalContactsOppComponents.do', 'Prepare', NULL, 'Add a component', 'A component can be added to an opportunity and assigned to any employee present in the system. The component type can also be selected.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.367', 0, '2003-12-10 13:44:04.367', true);
INSERT INTO help_contents VALUES (48, 2, 2, 'ExternalContactsOpps.do', 'DetailsOpp', NULL, 'Opportunity Details ', 'You can view all the details about the components here like the status, the guess amount and the current stage. A new component can also be added to a particular opportunity. ', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.38', 0, '2003-12-10 13:44:04.38', true);
INSERT INTO help_contents VALUES (49, 2, 2, 'ExternalContactsOppComponents.do', 'DetailsComponent', NULL, 'Component Details ', 'This page shows the details about the opportunity like what is the probability of closing the opportunity, what is the current stage of the opportunity etc', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.408', 0, '2003-12-10 13:44:04.408', true);
INSERT INTO help_contents VALUES (50, 2, 2, 'ExternalContactsCalls.do', 'Modify', NULL, 'Modifying call details ', 'You can modify all the details of the calls.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.42', 0, '2003-12-10 13:44:04.42', true);
INSERT INTO help_contents VALUES (51, 2, 2, 'ExternalContacts.do', 'InsertFields', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.435', 0, '2003-12-10 13:44:04.435', true);
INSERT INTO help_contents VALUES (52, 2, 2, 'ExternalContacts.do', 'UpdateFields', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.44', 0, '2003-12-10 13:44:04.44', true);
INSERT INTO help_contents VALUES (53, 2, 2, 'ExternalContacts.do', 'ListContacts', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.445', 0, '2003-12-10 13:44:04.445', true);
INSERT INTO help_contents VALUES (54, 2, 2, 'ExternalContacts.do', 'Clone', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.462', 0, '2003-12-10 13:44:04.462', true);
INSERT INTO help_contents VALUES (55, 2, 2, 'ExternalContacts.do', 'AddFolderRecord', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.471', 0, '2003-12-10 13:44:04.471', true);
INSERT INTO help_contents VALUES (56, 2, 2, 'ExternalContactsOpps.do', 'Save', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.484', 0, '2003-12-10 13:44:04.484', true);
INSERT INTO help_contents VALUES (57, 2, 2, 'ExternalContacts.do', 'SearchContacts ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.493', 0, '2003-12-10 13:44:04.493', true);
INSERT INTO help_contents VALUES (58, 2, 2, 'ExternalContacts.do', 'MessageDetails', NULL, NULL, 'The selected message is displayed with the message text and attachments if any.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.51', 0, '2003-12-10 13:44:04.51', true);
INSERT INTO help_contents VALUES (59, 2, 2, 'ExternalContactsOppComponents.do', 'SaveComponent', NULL, NULL, 'This page shows the details about the opportunity like what is the probability of closing the opportunity, what is the current stage of the opportunity etc', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.52', 0, '2003-12-10 13:44:04.52', true);
INSERT INTO help_contents VALUES (60, 2, 2, 'ExternalContacts.do', 'SearchContacts', NULL, 'List of Contacts', 'This page shows the list of contacts existing in the system. The names of the contact along with the company name and phone numbers are shown. If the name of the contact is an account it''s shown right next to it. You can also add a contact.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.532', 0, '2003-12-10 13:44:04.532', true);
INSERT INTO help_contents VALUES (61, 2, 2, 'ExternalContacts.do', 'ExportReport', NULL, 'Overview', 'The data present can be used to export data and display that in different formats. The exported data can be filtered in different ways. This would also let you view the data download it. This also shows the number of times an exported data has been downloaded. ', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.552', 0, '2003-12-10 13:44:04.552', true);
INSERT INTO help_contents VALUES (62, 2, 2, 'ExternalContacts.do', 'ContactDetails', NULL, 'Contact Details ', 'The details about the contact are displayed here along with the record information containing the owner, the employee who entered the details and finally the person who last modified these details.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.569', 0, '2003-12-10 13:44:04.569', true);
INSERT INTO help_contents VALUES (63, 2, 2, 'ExternalContacts.do', 'ViewMessages', NULL, 'Message Details', 'This page lists all the messages associated with the contact, showing the name of the message, its date and its status.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.624', 0, '2003-12-10 13:44:04.624', true);
INSERT INTO help_contents VALUES (64, 2, 2, 'ExternalContactsCallsForward.do', 'ForwardCall', NULL, 'Forwarding a call', 'The details of the calls that are associated with a contact can be forwarded to different employees present in the system.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.649', 0, '2003-12-10 13:44:04.649', true);
INSERT INTO help_contents VALUES (65, 2, 2, 'ExternalContactsCallsForward.do', 'SendCall', NULL, 'List of recipients', 'This page shows the list of recipients to whom the call details that are associated with an contact have been forwarded.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.658', 0, '2003-12-10 13:44:04.658', true);
INSERT INTO help_contents VALUES (66, 2, 2, 'ExternalContactsOppComponents.do', 'ModifyComponent', NULL, 'Modifying the component details  ', 'The details of the component, associated with an opportunity can be modified', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.663', 0, '2003-12-10 13:44:04.663', true);
INSERT INTO help_contents VALUES (67, 2, 2, 'ExternalContactsOpps.do', 'ModifyOpp', NULL, 'Modify the opportunity', 'The description of the opportunity can be Renamed / updated.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.676', 0, '2003-12-10 13:44:04.676', true);
INSERT INTO help_contents VALUES (68, 2, 2, 'ExternalContacts.do', 'Fields', NULL, 'List of Folder Records', 'Each contact can have several folders and each folder further can have multiple records. You can add a record to a folder. Each record present in the folder displays the record name, when it was entered, who modified this record last and when.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.685', 0, '2003-12-10 13:44:04.685', true);
INSERT INTO help_contents VALUES (69, 2, 2, 'ExternalContacts.do', 'ModifyFields', NULL, 'Modify Folder Record ', 'Here you can modify the details of the folder record.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.726', 0, '2003-12-10 13:44:04.726', true);
INSERT INTO help_contents VALUES (70, 2, 2, 'ExternalContactsOpps.do', 'UpdateOpp', NULL, 'Opportunity Details', 'All the opportunities associated with the contact are shown here, with its best possible total and the when the opportunity was last modified. You can filter the different types of opportunities that can be selected using the drop down and display them.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.738', 0, '2003-12-10 13:44:04.738', true);
INSERT INTO help_contents VALUES (71, 2, 2, 'ExternalContacts.do', NULL, NULL, 'Overview', 'If a contact already exists in the system, you can search for that contact by name, company, title, contact type or source, by typing the search term in the appropriate field, and clicking the Search button.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.759', 0, '2003-12-10 13:44:04.759', true);
INSERT INTO help_contents VALUES (72, 2, 2, 'ContactsList.do', 'ContactList', NULL, NULL, 'Enables you to select the contacts from the list and then add them to the Action List. It shows the name of the contact along with the email and the type of contact. 

', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.799', 0, '2003-12-10 13:44:04.799', true);
INSERT INTO help_contents VALUES (73, 2, 2, 'Contacts.do', 'Prepare', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:04.812', 0, '2003-12-10 13:44:04.812', true);
INSERT INTO help_contents VALUES (74, 2, 2, 'Contacts.do', 'Details', NULL, 'Contact detail page', 'The contact details associated to the account are displayed here. The details like the account number, email address, phone number and the addresses are shown here.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.818', 0, '2003-12-10 13:44:04.818', true);
INSERT INTO help_contents VALUES (75, 2, 2, 'Contacts.do', 'Modify', NULL, 'Modify contact', 'The details of the contact can be modified or updated here.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.83', 0, '2003-12-10 13:44:04.83', true);
INSERT INTO help_contents VALUES (76, 2, 2, 'Contacts.do', 'ViewMessages', NULL, 'List of Messages', 'The list of the messages related to the contacts.', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.843', 0, '2003-12-10 13:44:04.843', true);
INSERT INTO help_contents VALUES (77, 2, 2, 'ContactForm.do', 'Prepare', NULL, NULL, 'Adding/Modifying a new contact', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.851', 0, '2003-12-10 13:44:04.851', true);
INSERT INTO help_contents VALUES (78, 4, 4, 'Leads.do', 'Dashboard', NULL, 'Overview', 'The progress chart is displayed in different views for all the employees working under the owner or creator of the opportunity. The opportunities are shown, with their names and the probable gross revenue associated with that opportunity. Finally the list of employees reporting to a particular employee/supervisor is also shown below the progress chart', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.903', 0, '2003-12-10 13:44:04.903', true);
INSERT INTO help_contents VALUES (79, 4, 4, 'Leads.do', 'Prepare', NULL, 'Add a Opportunity', 'This page lets you add a opportunity into the system.

Here a new opportunity can be added by giving a description of the opportunity, then adding a component that is associated with the opportunity. An opportunity can have one or more components. Each component can be assigned to a different employee and the type of the component can be selected. For each component the probability of closing the component, the date estimated, the best guess for closing the deal and the duration for that component are required. 
', NULL, NULL, NULL, 0, '2003-12-10 13:44:04.992', 0, '2003-12-10 13:44:04.992', true);
INSERT INTO help_contents VALUES (80, 4, 4, 'Leads.do', 'SearchForm', NULL, 'Search Opportunities ', 'You can search for an opportunity already existing in the system based on different filters', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.02', 0, '2003-12-10 13:44:05.02', true);
INSERT INTO help_contents VALUES (81, 4, 4, 'LeadsReports.do', 'Default', NULL, 'Export Data', 'Pipeline data can be exported, filtered, and displayed in different formats. You can also view the data online in html format, and see number of times an exported data has been downloaded.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.041', 0, '2003-12-10 13:44:05.041', true);
INSERT INTO help_contents VALUES (82, 4, 4, 'Leads.do', 'SearchOpp', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:05.057', 0, '2003-12-10 13:44:05.057', true);
INSERT INTO help_contents VALUES (83, 4, 4, 'Leads.do', 'Reports', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:05.088', 0, '2003-12-10 13:44:05.088', true);
INSERT INTO help_contents VALUES (84, 4, 4, 'LeadsComponents.do', 'ModifyComponent', NULL, 'Modify Component', 'You can modify the component details associated with an opportunity in a pipeline.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.098', 0, '2003-12-10 13:44:05.098', true);
INSERT INTO help_contents VALUES (85, 4, 4, 'LeadsCalls.do', 'Add', NULL, 'Adding a call', 'You can add a new call here associated with the opportunity. ', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.125', 0, '2003-12-10 13:44:05.125', true);
INSERT INTO help_contents VALUES (86, 4, 4, 'Leads.do', 'ModifyOpp', NULL, 'Modify the opportunity:', 'The description of the opportunity can be modified / updated.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.139', 0, '2003-12-10 13:44:05.139', true);
INSERT INTO help_contents VALUES (87, 4, 4, 'LeadsCalls.do', 'Insert', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:05.147', 0, '2003-12-10 13:44:05.147', true);
INSERT INTO help_contents VALUES (88, 4, 4, 'LeadsDocuments.do', 'Modify', NULL, 'Modify Document', 'Modify the Subject or filename of a document.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.156', 0, '2003-12-10 13:44:05.156', true);
INSERT INTO help_contents VALUES (89, 4, 4, 'LeadsCallsForward.do', 'ForwardMessage', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:05.17', 0, '2003-12-10 13:44:05.17', true);
INSERT INTO help_contents VALUES (90, 4, 4, 'Leads.do', 'Save', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:05.179', 0, '2003-12-10 13:44:05.179', true);
INSERT INTO help_contents VALUES (91, 4, 4, 'Leads.do', 'GenerateForm', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:05.184', 0, '2003-12-10 13:44:05.184', true);
INSERT INTO help_contents VALUES (92, 4, 4, 'LeadsComponents.do', 'DetailsComponent', NULL, 'Component Details', 'The component details for the opportunity are shown here ', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.217', 0, '2003-12-10 13:44:05.217', true);
INSERT INTO help_contents VALUES (93, 4, 4, 'LeadsDocuments.do', 'AddVersion', NULL, 'Upload a new version of document', 'You can upload a new version of the document and the new version of the file can be selected and uploaded', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.245', 0, '2003-12-10 13:44:05.245', true);
INSERT INTO help_contents VALUES (94, 4, 4, 'Leads.do', 'Search', NULL, 'List of components', 'The components resulted from the search are shown here. Different views of the components and its types are displayed. The name of the component with the estimated amount of money associated with the opportunity and the probability of that components being a success is shown. This also displays the time for closing the deal (term) and the organization name or the contact name if they are associated with the opportunity. A new opportunity can also be added.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.262', 0, '2003-12-10 13:44:05.262', true);
INSERT INTO help_contents VALUES (95, 4, 4, 'Leads.do', 'UpdateOpp', NULL, 'Opportunity Details', 'You can view all the details about the components here and also add a new component to a particular opportunity. The calls and the documents can be associated with that particular opportunity', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.286', 0, '2003-12-10 13:44:05.286', true);
INSERT INTO help_contents VALUES (96, 4, 4, 'LeadsCallsForward.do', 'ForwardCall', NULL, 'Forwarding a call', 'The details of calls associated with a contact can be forwarded to different users present in the system.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.321', 0, '2003-12-10 13:44:05.321', true);
INSERT INTO help_contents VALUES (97, 4, 4, 'LeadsDocuments.do', 'View', NULL, 'Document Details', 'In the Documents tab, the documents associated with a particular opportunity can be added and viewed.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.341', 0, '2003-12-10 13:44:05.341', true);
INSERT INTO help_contents VALUES (98, 4, 4, 'LeadsCalls.do', 'Modify', NULL, 'Modify call details', 'You can modify the details of the calls.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.365', 0, '2003-12-10 13:44:05.365', true);
INSERT INTO help_contents VALUES (99, 4, 4, 'Leads.do', 'DetailsOpp', NULL, 'Opportunity Details', 'You can view all the details about opportunity components here and also add a new component to a particular opportunity. Calls and the documents can also be added to an opportunity or viewed by clicking on the appropriate tab.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.374', 0, '2003-12-10 13:44:05.374', true);
INSERT INTO help_contents VALUES (100, 4, 4, 'LeadsCalls.do', 'View', NULL, 'Call Details', 'Calls associated with the opportunity are shown. Calls can be added to the opportunity, and details about listed calls can be examined.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.416', 0, '2003-12-10 13:44:05.416', true);
INSERT INTO help_contents VALUES (101, 4, 4, 'Leads.do', 'ViewOpp', NULL, NULL, 'The opportunities resulted from the search are shown here. Different views of the opportunities and its types are displayed. The name of the component with the estimated amount of money associated with the opportunity and the probability of that opportunity being a success is shown. This also displays the time for closing the deal (term) and the organization name or the contact name if they are associated with the opportunity', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.439', 0, '2003-12-10 13:44:05.439', true);
INSERT INTO help_contents VALUES (102, 4, 4, 'Leads.do', NULL, NULL, NULL, 'This is the Pipeline System

Pipeline helps in creating prospective opportunities or leads in the company. Each opportunity helps to follow up a lead, who might eventually turn into a client. Here you can create an opportunity, search for an existing opportunity in the system, export the data to different formats. The dashboard reflects the progress chart in different views for all the employees working under the owner/creator of the opportunity. ', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.499', 0, '2003-12-10 13:44:05.499', true);
INSERT INTO help_contents VALUES (103, 4, 4, 'LeadsComponents.do', 'SaveComponent', NULL, 'Component Details', '
The component details for the opportunity are shown here
', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.516', 0, '2003-12-10 13:44:05.516', true);
INSERT INTO help_contents VALUES (104, 4, 4, 'LeadsComponents.do', 'Prepare', NULL, 'Add a component', 'A component can be added to an opportunity and assigned to any employee present in the system. The component type can also be selected.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.529', 0, '2003-12-10 13:44:05.529', true);
INSERT INTO help_contents VALUES (105, 4, 4, 'LeadsCalls.do', 'Update', NULL, 'Call Details', 'The calls associated with the opportunity are shown. The calls can be added to the opportunity.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.542', 0, '2003-12-10 13:44:05.542', true);
INSERT INTO help_contents VALUES (106, 4, 4, 'LeadsCalls.do', 'Details', NULL, 'Call Details ', 'Details about the call associated with the opportunity', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.555', 0, '2003-12-10 13:44:05.555', true);
INSERT INTO help_contents VALUES (107, 4, 4, 'LeadsDocuments.do', 'Add', NULL, 'Upload a document', 'New documents related to the opportunity can be uploaded into the system.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.573', 0, '2003-12-10 13:44:05.573', true);
INSERT INTO help_contents VALUES (108, 4, 4, 'LeadsDocuments.do', 'Details', NULL, 'Document Details ', 'This shows all versions of the updated document. The name of the file with the size, the version and the number of downloads are shown here.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.601', 0, '2003-12-10 13:44:05.601', true);
INSERT INTO help_contents VALUES (109, 4, 4, 'LeadsReports.do', 'ExportList', NULL, 'List of exported data', 'The data present can be used to export data and display that in different formats. The exported data can be filtered in different ways. This would also let you view the data download it. This also shows the number of times an exported data has been downloaded', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.61', 0, '2003-12-10 13:44:05.61', true);
INSERT INTO help_contents VALUES (110, 4, 4, 'LeadsReports.do', 'ExportForm', NULL, 'Generating Export data', 'Generate an exported report from pipeline data', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.631', 0, '2003-12-10 13:44:05.631', true);
INSERT INTO help_contents VALUES (111, 1, 1, 'Accounts.do', 'Dashboard', NULL, 'Overview', 'The date range can be modified which is shown in the right hand window by clicking on a specific date on the calendar. Accounts with contract end dates or other required actions appear in the right hand window reminding the user to take action on them. The schedule, actions, alert dates and contract end dates are displayed for user or the employees under him by using the dropdown at the top of the page. Clicking on the alert link will let the user modify the details of the account owner. ', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.661', 0, '2003-12-10 13:44:05.661', true);
INSERT INTO help_contents VALUES (112, 1, 1, 'Accounts.do', 'Add', NULL, 'Add a Account', 'A new account can be added here', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.752', 0, '2003-12-10 13:44:05.752', true);
INSERT INTO help_contents VALUES (113, 1, 1, 'Accounts.do', 'Modify', NULL, 'Modify Account', 'The details of an account can be modified here', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.784', 0, '2003-12-10 13:44:05.784', true);
INSERT INTO help_contents VALUES (114, 1, 1, 'Contacts.do', 'View', NULL, 'Contact Details', 'A contact can be associated with an account. The lists of the contacts associated with the account are shown along with the title.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.827', 0, '2003-12-10 13:44:05.827', true);
INSERT INTO help_contents VALUES (115, 1, 1, 'Accounts.do', 'Fields', NULL, 'Folder Record Details', 'You create folders for accounts. Each folder can have one or more records associated with it, depending on the type of the folder. The details about records associated with the folder are shown', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.857', 0, '2003-12-10 13:44:05.857', true);
INSERT INTO help_contents VALUES (116, 1, 1, 'Opportunities.do', 'View', NULL, 'Opportunity Details', 'Opportunities associated with the contact, showing the best guess total and last modified date.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.878', 0, '2003-12-10 13:44:05.878', true);
INSERT INTO help_contents VALUES (117, 1, 1, 'RevenueManager.do', 'View', NULL, 'Revenue Details', 'The revenue associated with the account is shown here. The details about the revenue like the description, the date and the amount associated are displayed.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.911', 0, '2003-12-10 13:44:05.911', true);
INSERT INTO help_contents VALUES (118, 1, 1, 'RevenueManager.do', 'View', NULL, 'Revenue Details', 'The revenue associated with the account is shown here. The details about the revenue like the description, the date and the amount associated are displayed.', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.938', 0, '2003-12-10 13:44:05.938', true);
INSERT INTO help_contents VALUES (119, 1, 1, 'RevenueManager.do', 'Add', NULL, 'Add Revenue ', 'Adding a new revenue which is associated with the account', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.966', 0, '2003-12-10 13:44:05.966', true);
INSERT INTO help_contents VALUES (120, 1, 1, 'RevenueManager.do', 'Modify', NULL, 'Modify Revenue ', 'Here the revenue details can be modified', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.975', 0, '2003-12-10 13:44:05.975', true);
INSERT INTO help_contents VALUES (121, 1, 1, 'Accounts.do', 'ViewTickets', NULL, 'Ticket Details', 'This page shows the complete list of the tickets present related to a account. Also lets you add a new ticket', NULL, NULL, NULL, 0, '2003-12-10 13:44:05.984', 0, '2003-12-10 13:44:05.984', true);
INSERT INTO help_contents VALUES (122, 1, 1, 'AccountsDocuments.do', 'View', NULL, 'Document Details', 'Here the documents associated with the account are listed. New documents related to the account can be added', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.004', 0, '2003-12-10 13:44:06.004', true);
INSERT INTO help_contents VALUES (123, 1, 1, 'Accounts.do', 'SearchForm', NULL, 'Search Accounts ', 'This page provides the search feature for the accounts present in the system', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.024', 0, '2003-12-10 13:44:06.024', true);
INSERT INTO help_contents VALUES (124, 1, 1, 'Accounts.do', 'Details', NULL, 'Account Details', 'This shows the details of the account, which can be modified. Each account can have folders, contacts, opportunities, revenue, tickets, and documents, for which there are separate tabs.', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.04', 0, '2003-12-10 13:44:06.04', true);
INSERT INTO help_contents VALUES (125, 1, 1, 'RevenueManager.do', 'Dashboard', NULL, 'Revenue Dashboard ', 'This is the dashboard view of Revenue

This shows the progress chart for different years and types. All the accounts with the revenue are also shown along with the list of employees working under you are also listed under the progress chart. You can add accounts, search for the existing ones in the system based on different filters and export the data to different formats', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.06', 0, '2003-12-10 13:44:06.06', true);
INSERT INTO help_contents VALUES (126, 1, 1, 'Accounts.do', 'Reports', NULL, 'Export Data ', 'The data can be exported and displayed in different formats. The exported data can be filtered in different ways. This would also let you view the data and also download it. This also shows the number of times an exported data has been downloaded

', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.077', 0, '2003-12-10 13:44:06.077', true);
INSERT INTO help_contents VALUES (127, 1, 1, 'Accounts.do', 'ModifyFields', NULL, 'Modify folder record ', 'The Folder record details can be updated. ', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.099', 0, '2003-12-10 13:44:06.099', true);
INSERT INTO help_contents VALUES (128, 1, 1, 'AccountTickets.do', 'ReopenTicket', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:06.108', 0, '2003-12-10 13:44:06.108', true);
INSERT INTO help_contents VALUES (129, 1, 1, 'Accounts.do', 'Delete', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:06.115', 0, '2003-12-10 13:44:06.115', true);
INSERT INTO help_contents VALUES (130, 1, 1, 'Accounts.do', 'GenerateForm', NULL, 'Generate New Export', 'To generate the Export data', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.124', 0, '2003-12-10 13:44:06.124', true);
INSERT INTO help_contents VALUES (131, 1, 1, 'AccountContactsCalls.do', 'View', NULL, 'Call Details', 'Calls associated with the contact', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.14', 0, '2003-12-10 13:44:06.14', true);
INSERT INTO help_contents VALUES (132, 1, 1, 'Accounts.do', 'AddFolderRecord', NULL, 'Add folder record', 'A new Folder record can be added to the Folder.', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.157', 0, '2003-12-10 13:44:06.157', true);
INSERT INTO help_contents VALUES (133, 1, 1, 'AccountContactsCalls.do', 'Add', NULL, 'Add a call', 'You can add a new call, which is associated with a particular contact.', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.166', 0, '2003-12-10 13:44:06.166', true);
INSERT INTO help_contents VALUES (134, 1, 1, 'AccountsDocuments.do', 'Add', NULL, 'Upload Document ', 'New documents can be uploaded and be associated with the account', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.184', 0, '2003-12-10 13:44:06.184', true);
INSERT INTO help_contents VALUES (135, 1, 1, 'AccountsDocuments.do', 'Add ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:06.20', 0, '2003-12-10 13:44:06.20', true);
INSERT INTO help_contents VALUES (136, 1, 1, 'AccountContactsOpps.do', 'UpdateOpp', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:06.205', 0, '2003-12-10 13:44:06.205', true);
INSERT INTO help_contents VALUES (137, 1, 1, 'AccountTicketsDocuments.do', 'AddVersion', NULL, NULL, 'Upload a New Version of a existing Document ', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.214', 0, '2003-12-10 13:44:06.214', true);
INSERT INTO help_contents VALUES (138, 1, 1, 'Accounts.do', 'Details ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:06.222', 0, '2003-12-10 13:44:06.222', true);
INSERT INTO help_contents VALUES (139, 1, 1, 'Accounts.do', 'View', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:06.227', 0, '2003-12-10 13:44:06.227', true);
INSERT INTO help_contents VALUES (140, 1, 1, 'AccountTickets.do', 'AddTicket', NULL, 'Adding a new Ticket', 'This page lets you create a new ticket for the account ', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.236', 0, '2003-12-10 13:44:06.236', true);
INSERT INTO help_contents VALUES (141, 1, 1, 'AccountTicketsDocuments.do', 'View', NULL, 'Document Details', 'Here the documents associated with the ticket are listed. New documents related to the ticket can be added', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.253', 0, '2003-12-10 13:44:06.253', true);
INSERT INTO help_contents VALUES (142, 1, 1, 'AccountTickets.do', 'UpdateTicket', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:06.285', 0, '2003-12-10 13:44:06.285', true);
INSERT INTO help_contents VALUES (143, 1, 1, 'AccountContactsOppComponents.do', 'Prepare', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:06.29', 0, '2003-12-10 13:44:06.29', true);
INSERT INTO help_contents VALUES (144, 1, 1, 'Accounts.do', 'InsertFields', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:06.299', 0, '2003-12-10 13:44:06.299', true);
INSERT INTO help_contents VALUES (145, 1, 1, 'AccountContactsOpps.do', 'Save', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:06.308', 0, '2003-12-10 13:44:06.308', true);
INSERT INTO help_contents VALUES (146, 1, 1, 'Accounts.do', 'Search', NULL, NULL, 'Lists the Accounts present and also lets you create an account', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.313', 0, '2003-12-10 13:44:06.313', true);
INSERT INTO help_contents VALUES (147, 1, 1, 'AccountTicketsDocuments.do', 'Details', NULL, NULL, 'Page shows all the versions of the current document', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.329', 0, '2003-12-10 13:44:06.329', true);
INSERT INTO help_contents VALUES (148, 1, 1, 'AccountTicketsDocuments.do', 'Modify', NULL, NULL, 'Modify the current document', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.339', 0, '2003-12-10 13:44:06.339', true);
INSERT INTO help_contents VALUES (149, 1, 1, 'Accounts.do', 'Insert', NULL, 'Account Details', 'This shows the details of the account, which can be modified. Each account can have several folders, the contacts for that account, probable opportunities, the revenue generated, and the tickets for each of the account can also be added here. You can update several documents associated with each account. ', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.349', 0, '2003-12-10 13:44:06.349', true);
INSERT INTO help_contents VALUES (150, 1, 1, 'AccountContactsCalls.do', 'Details', NULL, 'Call details', 'The details of the call are shown here which can be modified, deleted or forwarded to any of the employees.', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.369', 0, '2003-12-10 13:44:06.369', true);
INSERT INTO help_contents VALUES (151, 1, 1, 'AccountContactsCalls.do', 'Modify', NULL, 'Add / update a call', 'You can add a new call, which is associated with a particular contact.', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.386', 0, '2003-12-10 13:44:06.386', true);
INSERT INTO help_contents VALUES (152, 1, 1, 'AccountContactsCalls.do', 'Save', NULL, 'Call details', 'The details of the call are shown here which can be modified, deleted or forwarded to any of the employees', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.399', 0, '2003-12-10 13:44:06.399', true);
INSERT INTO help_contents VALUES (153, 1, 1, 'AccountContactsCalls.do', 'ForwardCall', NULL, 'Forward Call ', 'The details of the calls that are associated with a contact can be forwarded to different employees present in the system', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.408', 0, '2003-12-10 13:44:06.408', true);
INSERT INTO help_contents VALUES (154, 1, 1, 'AccountContactsOpps.do', 'ViewOpps', NULL, 'List of Opportunities', 'Opportunities associated with the contact, showing the best guess total and last modified date.', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.417', 0, '2003-12-10 13:44:06.417', true);
INSERT INTO help_contents VALUES (155, 1, 1, 'AccountContactsOpps.do', 'DetailsOpp', NULL, 'Opportunity Details', 'You can view all the details about the components here and also add a new component to a particular opportunity. The opportunity can be renamed and its details can be modified', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.437', 0, '2003-12-10 13:44:06.437', true);
INSERT INTO help_contents VALUES (156, 1, 1, 'AccountTickets.do', 'TicketDetails', NULL, 'Ticket Details ', 'This page lets you view the details of the ticket also lets you modify and delete the ticket.', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.453', 0, '2003-12-10 13:44:06.453', true);
INSERT INTO help_contents VALUES (157, 1, 1, 'AccountTickets.do', 'ModifyTicket', NULL, 'Modify ticket', 'This page lets you modify the ticket information and update the details', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.466', 0, '2003-12-10 13:44:06.466', true);
INSERT INTO help_contents VALUES (158, 1, 1, 'AccountTicketTasks.do', 'List', NULL, 'Task Details', 'This page lists the tasks assigned for a particular account. New tasks can be added, which would then appear in the list of tasks, showing their priority and their assignment.', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.508', 0, '2003-12-10 13:44:06.508', true);
INSERT INTO help_contents VALUES (159, 1, 1, 'AccountTicketsDocuments.do', 'Add', NULL, 'Uploading a Document', 'New document related to the account can be uploaded', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.544', 0, '2003-12-10 13:44:06.544', true);
INSERT INTO help_contents VALUES (160, 1, 1, 'AccountTickets.do', 'ViewHistory', NULL, 'Ticket Log History', 'This page maintains the complete log history about each ticket from it''s creation till the ticket is closed', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.553', 0, '2003-12-10 13:44:06.553', true);
INSERT INTO help_contents VALUES (161, 1, 1, 'AccountsDocuments.do', 'Details', NULL, 'Document Details ', 'All the versions of the current document are listed here', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.562', 0, '2003-12-10 13:44:06.562', true);
INSERT INTO help_contents VALUES (162, 1, 1, 'AccountsDocuments.do', 'Modify', NULL, 'Modify Document ', 'Here the document information can be modified', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.571', 0, '2003-12-10 13:44:06.571', true);
INSERT INTO help_contents VALUES (163, 1, 1, 'AccountsDocuments.do', 'AddVersion', NULL, 'Upload New Version ', 'New versions of the document are uploaded', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.58', 0, '2003-12-10 13:44:06.58', true);
INSERT INTO help_contents VALUES (164, 1, 1, 'Accounts.do', 'ExportReport', NULL, 'List of Exported data', 'The data can be exported and displayed in different formats. The exported data can be filtered in different ways. This would also let you view the data and also download it. This also shows the number of times an exported data has been downloaded', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.589', 0, '2003-12-10 13:44:06.589', true);
INSERT INTO help_contents VALUES (165, 1, 1, 'RevenueManager.do', 'Details', NULL, 'Revenue Details ', 'Details about the revenue', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.605', 0, '2003-12-10 13:44:06.605', true);
INSERT INTO help_contents VALUES (166, 1, 1, 'RevenueManager.do', 'Dashboard ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:06.614', 0, '2003-12-10 13:44:06.614', true);
INSERT INTO help_contents VALUES (167, 1, 1, 'OpportunityForm.do', 'Prepare', NULL, 'Add Opportunity', 'A new opportunity associated with the contact can be added ', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.619', 0, '2003-12-10 13:44:06.619', true);
INSERT INTO help_contents VALUES (168, 1, 1, 'Opportunities.do', 'Add', NULL, 'Add opportunity ', 'A new opportunity associated with the contact can be added', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.675', 0, '2003-12-10 13:44:06.675', true);
INSERT INTO help_contents VALUES (169, 1, 1, 'Opportunities.do', 'Details', NULL, 'Opportunity Details', 'You can view all the details about the components here like the status, the guess amount and the current stage. A new component can also be added to a particular opportunity. ', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.684', 0, '2003-12-10 13:44:06.684', true);
INSERT INTO help_contents VALUES (170, 1, 1, 'Opportunities.do', 'Modify', NULL, 'Modify Opportunity ', 'The details of the opportunity can be modified', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.72', 0, '2003-12-10 13:44:06.72', true);
INSERT INTO help_contents VALUES (171, 1, 1, 'OpportunitiesComponents.do', 'DetailsComponent', NULL, 'Component Details ', 'This page shows the details about the opportunity like what is the probability of closing the opportunity, what is the current stage of the opportunity etc', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.729', 0, '2003-12-10 13:44:06.729', true);
INSERT INTO help_contents VALUES (172, 1, 1, 'OpportunitiesComponents.do', 'Prepare', NULL, 'Add a component', 'A component can be added to an opportunity and assigned to any employee present in the system. The component type can also be selected.', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.742', 0, '2003-12-10 13:44:06.742', true);
INSERT INTO help_contents VALUES (173, 1, 1, 'OpportunitiesComponents.do', 'ModifyComponent', NULL, 'Modify Component ', 'The details of the component can be added / updated to an opportunity and assigned to any employee present in the system. The component type can also be selected.', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.754', 0, '2003-12-10 13:44:06.754', true);
INSERT INTO help_contents VALUES (174, 1, 1, 'OpportunitiesComponents.do', 'SaveComponent', NULL, 'Component Details', 'This page shows the details about the opportunity like what is the probability of closing the opportunity, what is the current stage of the opportunity etc', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.781', 0, '2003-12-10 13:44:06.781', true);
INSERT INTO help_contents VALUES (175, 8, 8, 'TroubleTickets.do', 'Details', NULL, 'Ticket Details ', 'This page lets you view the details of the ticket also lets you modify and delete the ticket.', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.799', 0, '2003-12-10 13:44:06.799', true);
INSERT INTO help_contents VALUES (176, 8, 8, 'TroubleTickets.do', 'Add', NULL, 'Add a Ticket', 'You can add a new ticket  here', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.932', 0, '2003-12-10 13:44:06.932', true);
INSERT INTO help_contents VALUES (177, 8, 8, 'TroubleTickets.do', 'SearchTicketsForm', NULL, 'Search Existing Tickets', 'Form used for searching the tickets that are already existing in the system based on different filters and parameters', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.955', 0, '2003-12-10 13:44:06.955', true);
INSERT INTO help_contents VALUES (178, 8, 8, 'TroubleTickets.do', 'Reports', NULL, 'Export Data ', 'This is the page shows exported data.
The data can be exported to different formats. The exported data can be viewed with its subject, the size of the exported data file, when it was created and by whom. It also shows the number of times that particular exported file is downloaded. The exported data, created by you or all the employees can be viewed in two different views. A new data file can also be exported.
', NULL, NULL, NULL, 0, '2003-12-10 13:44:06.989', 0, '2003-12-10 13:44:06.989', true);
INSERT INTO help_contents VALUES (179, 8, 8, 'TroubleTickets.do', 'Modify', NULL, 'Modify Ticket Details', 'Here you can modify the ticket details like ticket information, it''s classification, the tickets assignment and it''s resolution.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.024', 0, '2003-12-10 13:44:07.024', true);
INSERT INTO help_contents VALUES (180, 8, 8, 'TroubleTickets.do', 'Modify', NULL, 'Modify Ticket Details', 'Here you can modify the ticket details like ticket information, it''s classification, the tickets assignment and it''s resolution.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.047', 0, '2003-12-10 13:44:07.047', true);
INSERT INTO help_contents VALUES (181, 8, 8, 'TroubleTicketTasks.do', 'List', NULL, 'List of Tasks', 'This page lists the tasks assigned for a particular ticket. New tasks can be added, which would then appear in the list of tasks, showing their priority, their assignment and other details', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.068', 0, '2003-12-10 13:44:07.068', true);
INSERT INTO help_contents VALUES (182, 8, 8, 'TroubleTicketsDocuments.do', 'View', NULL, 'List of Documents ', 'Here the documents associated with the ticket are listed. New documents related to the ticket can be added ', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.113', 0, '2003-12-10 13:44:07.113', true);
INSERT INTO help_contents VALUES (183, 8, 8, 'TroubleTicketsFolders.do', 'Fields', NULL, 'List of Folder Records', 'New folders can be created for each of the ticket. Configuring the admin module creates these custom folders. This page also displays the list of records with their details like when the particular record was created, last modified, the action performed on the record etc. associated with a particular folder', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.147', 0, '2003-12-10 13:44:07.147', true);
INSERT INTO help_contents VALUES (184, 8, 8, 'TroubleTicketsFolders.do', 'AddFolderRecord', NULL, 'Add Folder Record', 'The details of  the record are added
', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.16', 0, '2003-12-10 13:44:07.16', true);
INSERT INTO help_contents VALUES (185, 8, 8, 'TroubleTickets.do', 'ViewHistory', NULL, 'Ticket Log History', 'The log history of the ticket is maintained. ', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.169', 0, '2003-12-10 13:44:07.169', true);
INSERT INTO help_contents VALUES (186, 8, 8, 'TroubleTickets.do', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:07.185', 0, '2003-12-10 13:44:07.185', true);
INSERT INTO help_contents VALUES (187, 8, 8, 'TroubleTickets.do', 'Insert', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:07.19', 0, '2003-12-10 13:44:07.19', true);
INSERT INTO help_contents VALUES (188, 8, 8, 'TroubleTicketsFolders.do', 'InsertFields', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:07.199', 0, '2003-12-10 13:44:07.199', true);
INSERT INTO help_contents VALUES (189, 8, 8, 'TroubleTickets.do', 'GenerateForm', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:07.204', 0, '2003-12-10 13:44:07.204', true);
INSERT INTO help_contents VALUES (190, 8, 8, 'TroubleTickets.do', 'Details ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:07.227', 0, '2003-12-10 13:44:07.227', true);
INSERT INTO help_contents VALUES (191, 8, 8, 'TroubleTickets.do', 'Update', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:07.232', 0, '2003-12-10 13:44:07.232', true);
INSERT INTO help_contents VALUES (192, 8, 8, 'TroubleTickets.do', 'ExportReport', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:07.241', 0, '2003-12-10 13:44:07.241', true);
INSERT INTO help_contents VALUES (193, 8, 8, 'TroubleTicketsFolders.do', 'ModifyFields', NULL, 'Modify Folder Record', 'This lists the details of the folder record, which can be modified.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.25', 0, '2003-12-10 13:44:07.25', true);
INSERT INTO help_contents VALUES (194, 8, 8, 'TroubleTicketsFolders.do', 'UpdateFields', NULL, 'Folder Record Details ', 'The detail about the folder along with the record information like when the record was created and when it was modified is displayed here.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.259', 0, '2003-12-10 13:44:07.259', true);
INSERT INTO help_contents VALUES (195, 8, 8, 'TroubleTickets.do', 'SearchTickets', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:07.268', 0, '2003-12-10 13:44:07.268', true);
INSERT INTO help_contents VALUES (196, 8, 8, 'TroubleTickets.do', 'Home ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:07.29', 0, '2003-12-10 13:44:07.29', true);
INSERT INTO help_contents VALUES (197, 8, 8, 'TroubleTicketsDocuments.do', 'Add', NULL, 'Adding a Document', 'New document related to the ticket can be uploaded', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.296', 0, '2003-12-10 13:44:07.296', true);
INSERT INTO help_contents VALUES (198, 8, 8, 'TroubleTicketsDocuments.do', 'Details', NULL, 'Document Details', 'Page shows all the versions of the current document', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.305', 0, '2003-12-10 13:44:07.305', true);
INSERT INTO help_contents VALUES (199, 8, 8, 'TroubleTicketsDocuments.do', 'Modify', NULL, 'Modify Document Details', 'This page lets you modify the ticket information and update the details', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.314', 0, '2003-12-10 13:44:07.314', true);
INSERT INTO help_contents VALUES (200, 8, 8, 'TroubleTicketsDocuments.do', 'AddVersion', NULL, 'Add version number to Documents', 'You can upload a new version of a existing document', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.326', 0, '2003-12-10 13:44:07.326', true);
INSERT INTO help_contents VALUES (201, 8, 8, 'TroubleTickets.do', 'Home', NULL, 'Overview', 'This page displays the complete list of the tickets assigned to the user, the list of the tickets present in his department and finally the list of the tickets created by the user. For each ticket, the details about the ticket like the ticket number, priority; age of the ticket, the company and finally the assignment details are displayed. The issue details are also shown separately for each ticket.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.335', 0, '2003-12-10 13:44:07.335', true);
INSERT INTO help_contents VALUES (202, 15, 15, 'CompanyDirectory.do', 'ListEmployees', NULL, 'Overview', 'The details of each of the existing employee can be viewed, modified or deleted and a new detailed employee record can be added', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.398', 0, '2003-12-10 13:44:07.398', true);
INSERT INTO help_contents VALUES (203, 15, 15, 'CompanyDirectory.do', 'EmployeeDetails', NULL, 'Employee Details ', 'This is the employee detail page. This page displays the email, phone number, addresses and additional details of each employee', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.474', 0, '2003-12-10 13:44:07.474', true);
INSERT INTO help_contents VALUES (204, 15, 15, 'CompanyDirectory.do', 'Prepare', NULL, 'Add an Employee', 'You can add an employee into the system. The details of the employee like his email address; phone numbers, address and other additional details can be given along with his name', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.485', 0, '2003-12-10 13:44:07.485', true);
INSERT INTO help_contents VALUES (205, 15, 15, 'CompanyDirectory.do', 'ModifyEmployee', NULL, 'Modify Employee Details ', 'The employee details like the name of the employee, email address, phone numbers and address can be modified here.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.497', 0, '2003-12-10 13:44:07.497', true);
INSERT INTO help_contents VALUES (206, 15, 15, 'CompanyDirectory.do', 'Save', NULL, NULL, 'This page shows the details of the employee record, which could be either modified or deleted', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.506', 0, '2003-12-10 13:44:07.506', true);
INSERT INTO help_contents VALUES (207, 14, 14, 'Reports.do', 'ViewQueue', NULL, 'Overview', 'This is the home of the reports. 

A list of customized reports can be viewed and the queue of the reports that are scheduled to be processed by the server are also displayed. Each report that is ready to be retrieved is displayed along with its details like the subject of the report, the date when the report was generated, report status and finally the size of the report for the user to download.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.521', 0, '2003-12-10 13:44:07.521', true);
INSERT INTO help_contents VALUES (208, 14, 14, 'Reports.do', 'RunReport', NULL, 'List of Modules', 'This shows the different modules present and displays the list of corresponding reports present in each module.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.588', 0, '2003-12-10 13:44:07.588', true);
INSERT INTO help_contents VALUES (209, 14, 14, 'Reports.do ', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:07.598', 0, '2003-12-10 13:44:07.598', true);
INSERT INTO help_contents VALUES (210, 14, 14, 'Reports.do', 'CancelReport', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:07.604', 0, '2003-12-10 13:44:07.604', true);
INSERT INTO help_contents VALUES (211, 14, 14, 'Reports.do', 'ParameterList', NULL, 'Parameters specification', 'This page takes the parameters that need to be specified to run the report', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.612', 0, '2003-12-10 13:44:07.612', true);
INSERT INTO help_contents VALUES (212, 14, 14, 'Reports.do', 'ListReports', NULL, 'Lis of Reports', 'In this module, you can choose the report that you want to run from the list of the reports', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.625', 0, '2003-12-10 13:44:07.625', true);
INSERT INTO help_contents VALUES (213, 14, 14, 'Reports.do', 'CriteriaList', NULL, 'Criteria List ', 'You can choose to base this report on previously saved criteria, or continue and create new criteria', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.634', 0, '2003-12-10 13:44:07.634', true);
INSERT INTO help_contents VALUES (214, 14, 14, 'Reports.do', 'GenerateReport', NULL, 'Reports Added to queue', 'This page shows that the requested report is added to the queue. Also lets you know the details about the report and queue status', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.642', 0, '2003-12-10 13:44:07.642', true);
INSERT INTO help_contents VALUES (215, 14, 14, 'Reports.do', NULL, NULL, 'Overview', 'This
', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.651', 0, '2003-12-10 13:44:07.651', true);
INSERT INTO help_contents VALUES (216, 9, 9, 'Users.do', 'ListUsers', NULL, 'List of Users', 'This section allows the administrator to view and add users and manage user hierarchies. The users are typically employees in your company who interact with your clients or customers.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.678', 0, '2003-12-10 13:44:07.678', true);
INSERT INTO help_contents VALUES (217, 9, 9, 'Users.do', 'InsertUserForm', NULL, 'Adding a New User', 'This form allows new users to be added to the system and records their contact information.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.78', 0, '2003-12-10 13:44:07.78', true);
INSERT INTO help_contents VALUES (218, 9, 9, 'Users.do', 'ModifyUser', NULL, 'Modify User Details', 'This form provides the administrator with an editable view of the user information, and also allows the administrator  to view the users login history and view points.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.827', 0, '2003-12-10 13:44:07.827', true);
INSERT INTO help_contents VALUES (219, 9, 9, 'Users.do', 'ViewLog', NULL, 'User Login History', 'Provides a login history of the chosen user.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.855', 0, '2003-12-10 13:44:07.855', true);
INSERT INTO help_contents VALUES (220, 9, 9, 'Viewpoints.do', 'ListViewpoints', NULL, 'Viewpoints of User', 'The page displays the viewpoints of the employees regarding a particular module in the system. Lets you add a new viewpoint. The details displayed are when the viewpoint was entered and whether it enabled or not.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.868', 0, '2003-12-10 13:44:07.868', true);
INSERT INTO help_contents VALUES (221, 9, 9, 'Viewpoints.do', 'InsertViewpointForm', NULL, 'Add Viewpoint', 'The contact name can be selected and the permissions /access for the modules can be given.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.884', 0, '2003-12-10 13:44:07.884', true);
INSERT INTO help_contents VALUES (222, 9, 9, 'Viewpoints.do', 'ViewpointDetails', NULL, 'Update Viewpoint ', 'You can update a viewpoint and set the permissions to access different modules.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.90', 0, '2003-12-10 13:44:07.90', true);
INSERT INTO help_contents VALUES (223, 9, 9, 'Roles.do', 'ListRoles', NULL, 'List of Roles', 'You are looking at roles. 

This pages lists the different roles present in the system, their role description and the number of people present in the system who carry out that role. New roles can be added into the system', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.917', 0, '2003-12-10 13:44:07.917', true);
INSERT INTO help_contents VALUES (224, 9, 9, 'Roles.do', 'InsertRoleForm', NULL, 'Add a New Role', 'This page will let you Add/update the roles present in the system. Also lets you change the permissions. The permissions can be changed or set for each every module separately depending on the role.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.956', 0, '2003-12-10 13:44:07.956', true);
INSERT INTO help_contents VALUES (225, 9, 9, 'Roles.do', 'RoleDetails', NULL, 'Update Role ', 'This page will let you update the roles present in the system. Also lets you change the permissions. The permissions can be changed or set for each every module separately depending on the role.', NULL, NULL, NULL, 0, '2003-12-10 13:44:07.981', 0, '2003-12-10 13:44:07.981', true);
INSERT INTO help_contents VALUES (226, 9, 9, 'Admin.do', 'Config', NULL, 'Configure Modules ', 'This is the Modules page.

This page lets you configure modules that meet the needs of your organization including configuration of lookup lists and custom fields. There are four types of modules. Each module has different number of configure options. The changes in the module affect all the users', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.051', 0, '2003-12-10 13:44:08.051', true);
INSERT INTO help_contents VALUES (227, 9, 9, 'Admin.do', 'ConfigDetails', NULL, 'Configuration Options ', 'You can configure different options present in each module.The following are some of the configuration options that you might see in the modules. Some of these options are specific to the module so they might NOT be present in all the modules.', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.083', 0, '2003-12-10 13:44:08.083', true);
INSERT INTO help_contents VALUES (228, 9, 9, 'Admin.do', 'ModifyList', NULL, 'Edit Lookup List ', 'This page lets you edit and add the list items.', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.119', 0, '2003-12-10 13:44:08.119', true);
INSERT INTO help_contents VALUES (229, 9, 9, 'AdminFieldsFolder.do', 'AddFolder', NULL, 'Adding a New Folder', 'Add/Update the existing folder here', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.153', 0, '2003-12-10 13:44:08.153', true);
INSERT INTO help_contents VALUES (230, 9, 9, 'AdminFieldsFolder.do', 'ModifyFolder', NULL, 'Modify Existing Folder', 'Update the existing folder here', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.163', 0, '2003-12-10 13:44:08.163', true);
INSERT INTO help_contents VALUES (231, 9, 9, 'AdminConfig.do', 'ListGlobalParams', NULL, 'Configure System ', 'You can configure the system for the session timeout and set the time for the time out
', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.173', 0, '2003-12-10 13:44:08.173', true);
INSERT INTO help_contents VALUES (232, 9, 9, 'AdminConfig.do', 'ModifyTimeout', NULL, 'Modify Timeout ', 'The session timeout is the time in which a user will automatically be logged out if the specified period of inactivity is reached.
', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.184', 0, '2003-12-10 13:44:08.184', true);
INSERT INTO help_contents VALUES (233, 9, 9, 'Admin.do', 'Usage', NULL, 'Resource Usage Details', 'Current System Usage and Billing Usage Information is displayed', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.194', 0, '2003-12-10 13:44:08.194', true);
INSERT INTO help_contents VALUES (234, 9, 9, 'Admin.do ', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.225', 0, '2003-12-10 13:44:08.225', true);
INSERT INTO help_contents VALUES (235, 9, 9, 'Users.do', 'DisableUserConfirm', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.23', 0, '2003-12-10 13:44:08.23', true);
INSERT INTO help_contents VALUES (236, 9, 9, 'AdminFieldsFolder.do', NULL, NULL, 'Custom Folders ', 'This page lists all the custom folders created in the general contacts; let''s you edit them and also allow you to enable/disable the folders.', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.237', 0, '2003-12-10 13:44:08.237', true);
INSERT INTO help_contents VALUES (237, 9, 9, 'AdminFieldsFolder.do', 'ListFolders', NULL, 'List of Custom Folders ', 'This page lists all the custom folders created in the general contacts; let''s you edit them and also allow you to enable/disable the folders.
', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.264', 0, '2003-12-10 13:44:08.264', true);
INSERT INTO help_contents VALUES (238, 9, 9, 'AdminFields.do', 'ModifyField', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.297', 0, '2003-12-10 13:44:08.297', true);
INSERT INTO help_contents VALUES (239, 9, 9, 'Admin.do', 'ListGlobalParams', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.305', 0, '2003-12-10 13:44:08.305', true);
INSERT INTO help_contents VALUES (240, 9, 9, 'Admin.do', 'ModifyTimeout', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.314', 0, '2003-12-10 13:44:08.314', true);
INSERT INTO help_contents VALUES (241, 9, 9, 'AdminObjectEvents.do', NULL, NULL, 'Object Events:', 'The list of Object Events are displayed along with the corresponding Triggered Processes.  The number of components and whether that Object Event is available or not is also shown.', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.323', 0, '2003-12-10 13:44:08.323', true);
INSERT INTO help_contents VALUES (242, 9, 9, 'AdminFieldsGroup.do', 'AddGroup', NULL, NULL, 'Add a group', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.343', 0, '2003-12-10 13:44:08.343', true);
INSERT INTO help_contents VALUES (243, 9, 9, 'AdminFields.do', 'AddField', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.357', 0, '2003-12-10 13:44:08.357', true);
INSERT INTO help_contents VALUES (244, 9, 9, 'Admin.do', 'UpdateList', NULL, NULL, 'The Lookup List displays all the list names, which can be edited, the number of items can be known and the ones present can be previewed

', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.366', 0, '2003-12-10 13:44:08.366', true);
INSERT INTO help_contents VALUES (245, 9, 9, 'AdminScheduledEvents.do', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.379', 0, '2003-12-10 13:44:08.379', true);
INSERT INTO help_contents VALUES (246, 9, 9, 'Admin.do', 'Config ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.387', 0, '2003-12-10 13:44:08.387', true);
INSERT INTO help_contents VALUES (247, 9, 9, 'Admin.do', 'EditLists', NULL, 'Lookup Lists ', 'The Lookup List displays all the list names, which can be edited, the number of items can be known and the ones present can be previewed', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.393', 0, '2003-12-10 13:44:08.393', true);
INSERT INTO help_contents VALUES (248, 9, 9, 'AdminFieldsGroup.do', 'ListGroups', NULL, 'Folder Details', 'This page lists the folder details and the groups added to this folder. Each group can further have a custom field created or deleted. You can also place it in the desired position in the dropdown list.', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.413', 0, '2003-12-10 13:44:08.413', true);
INSERT INTO help_contents VALUES (249, 9, 9, 'AdminCategories.do', 'ViewActive', NULL, 'Active Category Details', 'The four different levels for the "Active" and the "Draft" categories are displayed. The level1 has the category name, which is further classified into sub directories/levels. The level1 has the sublevel called level2 which in turn has sublevel called level3 and so on.

', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.467', 0, '2003-12-10 13:44:08.467', true);
INSERT INTO help_contents VALUES (250, 9, 9, 'AdminCategories.do', 'View', NULL, 'Draft Category Details', 'The four different levels for the active and the draft categories are displayed. The level1 has the category name, which is further classified into sub directories/levels. The level1 has the sublevel called level2 which in turn has sublevel called level3 and so on.  The draft categories can be edited and activate. The activated draft categories would be then reflected in the Active Categories list. The modified/updated drafted category can also be reverted to its original state.', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.482', 0, '2003-12-10 13:44:08.482', true);
INSERT INTO help_contents VALUES (251, 9, 9, 'Users.do', 'InsertUserForm ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.526', 0, '2003-12-10 13:44:08.526', true);
INSERT INTO help_contents VALUES (252, 9, 9, 'Users.do', 'UpdateUser', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.532', 0, '2003-12-10 13:44:08.532', true);
INSERT INTO help_contents VALUES (253, 9, 9, 'Users.do', 'AddUser', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.537', 0, '2003-12-10 13:44:08.537', true);
INSERT INTO help_contents VALUES (254, 9, 9, 'Users.do', 'UserDetails', NULL, 'User Details', 'This form provides the administrator with more information about the user, namely information pertaining to the users login history and view points.', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.551', 0, '2003-12-10 13:44:08.551', true);
INSERT INTO help_contents VALUES (255, 9, 9, 'Roles.do', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.589', 0, '2003-12-10 13:44:08.589', true);
INSERT INTO help_contents VALUES (256, 9, 9, 'Roles.do', 'ListRoles ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.607', 0, '2003-12-10 13:44:08.607', true);
INSERT INTO help_contents VALUES (257, 9, 9, 'Viewpoints.do', 'InsertViewpoint ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.613', 0, '2003-12-10 13:44:08.613', true);
INSERT INTO help_contents VALUES (258, 9, 9, 'Viewpoints.do', 'InsertViewpoint', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:08.619', 0, '2003-12-10 13:44:08.619', true);
INSERT INTO help_contents VALUES (259, 9, 9, 'Admin.do', NULL, NULL, 'Overview', 'This is the Admin system.

You are looking at the Admin System home page. Here you can manage the system by reviewing its usage, configuring specific modules and system parameters.  
', NULL, NULL, NULL, 0, '2003-12-10 13:44:08.63', 0, '2003-12-10 13:44:08.63', true);
INSERT INTO help_contents VALUES (260, 11, 11, 'Search.do', 'SiteSearch ', NULL, NULL, NULL, NULL, NULL, NULL, 0, '2003-12-10 13:44:09.061', 0, '2003-12-10 13:44:09.061', true);
INSERT INTO help_contents VALUES (261, 11, 11, 'Search.do', 'SiteSearch', NULL, 'General Search', 'You can search the system for data associated with a particular key term. This can be done using the search data text box present on the left side of the window. The data associated with the corresponding key term is looked for in different modules for a match and the results are displayed per module. The search results are shown with detail description.', NULL, NULL, NULL, 0, '2003-12-10 13:44:09.184', 0, '2003-12-10 13:44:09.184', true);


--
-- Data for TOC entry 646 (OID 33341)
-- Name: help_tableof_contents; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO help_tableof_contents VALUES (1, 'Modules', NULL, NULL, NULL, NULL, 1, 5, 0, '2003-12-10 13:44:09.225', 0, '2003-12-10 13:44:09.225', true);
INSERT INTO help_tableof_contents VALUES (2, 'My Home Page', NULL, NULL, 1, 12, 2, 5, 0, '2003-12-10 13:44:09.349', 0, '2003-12-10 13:44:09.349', true);
INSERT INTO help_tableof_contents VALUES (3, 'Overview', NULL, NULL, 2, 12, 3, 5, 0, '2003-12-10 13:44:09.359', 0, '2003-12-10 13:44:09.359', true);
INSERT INTO help_tableof_contents VALUES (4, 'My Mailbox ', NULL, NULL, 2, 12, 3, 10, 0, '2003-12-10 13:44:09.412', 0, '2003-12-10 13:44:09.412', true);
INSERT INTO help_tableof_contents VALUES (5, 'Message Details', NULL, NULL, 4, 12, 4, 15, 0, '2003-12-10 13:44:09.437', 0, '2003-12-10 13:44:09.437', true);
INSERT INTO help_tableof_contents VALUES (6, 'New Message ', NULL, NULL, 4, 12, 4, 20, 0, '2003-12-10 13:44:09.477', 0, '2003-12-10 13:44:09.477', true);
INSERT INTO help_tableof_contents VALUES (7, 'Reply Message', NULL, NULL, 4, 12, 4, 25, 0, '2003-12-10 13:44:09.495', 0, '2003-12-10 13:44:09.495', true);
INSERT INTO help_tableof_contents VALUES (8, 'SendMessage', NULL, NULL, 4, 12, 4, 30, 0, '2003-12-10 13:44:09.512', 0, '2003-12-10 13:44:09.512', true);
INSERT INTO help_tableof_contents VALUES (9, 'Forward message', NULL, NULL, 4, 12, 4, 35, 0, '2003-12-10 13:44:09.521', 0, '2003-12-10 13:44:09.521', true);
INSERT INTO help_tableof_contents VALUES (10, 'My Tasks ', NULL, NULL, 2, 12, 3, 40, 0, '2003-12-10 13:44:09.529', 0, '2003-12-10 13:44:09.529', true);
INSERT INTO help_tableof_contents VALUES (11, 'Advanced Task', NULL, NULL, 10, 12, 4, 45, 0, '2003-12-10 13:44:09.587', 0, '2003-12-10 13:44:09.587', true);
INSERT INTO help_tableof_contents VALUES (12, 'Forwarding a Task ', NULL, NULL, 10, 12, 4, 50, 0, '2003-12-10 13:44:09.601', 0, '2003-12-10 13:44:09.601', true);
INSERT INTO help_tableof_contents VALUES (13, 'Modify task', NULL, NULL, 10, 12, 4, 55, 0, '2003-12-10 13:44:09.622', 0, '2003-12-10 13:44:09.622', true);
INSERT INTO help_tableof_contents VALUES (14, 'My Action Lists ', NULL, NULL, 2, 12, 3, 60, 0, '2003-12-10 13:44:09.639', 0, '2003-12-10 13:44:09.639', true);
INSERT INTO help_tableof_contents VALUES (15, 'Action Contacts', NULL, NULL, 14, 12, 4, 65, 0, '2003-12-10 13:44:09.65', 0, '2003-12-10 13:44:09.65', true);
INSERT INTO help_tableof_contents VALUES (16, 'Add Action List', NULL, NULL, 14, 12, 4, 70, 0, '2003-12-10 13:44:09.663', 0, '2003-12-10 13:44:09.663', true);
INSERT INTO help_tableof_contents VALUES (17, 'Modify Action', NULL, NULL, 14, 12, 4, 75, 0, '2003-12-10 13:44:09.671', 0, '2003-12-10 13:44:09.671', true);
INSERT INTO help_tableof_contents VALUES (18, 'Re-assignments ', NULL, NULL, 2, 12, 3, 80, 0, '2003-12-10 13:44:09.68', 0, '2003-12-10 13:44:09.68', true);
INSERT INTO help_tableof_contents VALUES (19, 'My Settings ', NULL, NULL, 2, 12, 3, 85, 0, '2003-12-10 13:44:09.689', 0, '2003-12-10 13:44:09.689', true);
INSERT INTO help_tableof_contents VALUES (20, 'Personal Information ', NULL, NULL, 19, 12, 4, 90, 0, '2003-12-10 13:44:09.698', 0, '2003-12-10 13:44:09.698', true);
INSERT INTO help_tableof_contents VALUES (21, 'Location Settings', NULL, NULL, 19, 12, 4, 95, 0, '2003-12-10 13:44:09.707', 0, '2003-12-10 13:44:09.707', true);
INSERT INTO help_tableof_contents VALUES (22, 'Update password', NULL, NULL, 19, 12, 4, 100, 0, '2003-12-10 13:44:09.716', 0, '2003-12-10 13:44:09.716', true);
INSERT INTO help_tableof_contents VALUES (23, 'Contacts', NULL, NULL, 1, 2, 2, 10, 0, '2003-12-10 13:44:09.756', 0, '2003-12-10 13:44:09.756', true);
INSERT INTO help_tableof_contents VALUES (24, 'Add a Contact', NULL, NULL, 23, 2, 3, 5, 0, '2003-12-10 13:44:09.793', 0, '2003-12-10 13:44:09.793', true);
INSERT INTO help_tableof_contents VALUES (25, 'Search Contacts ', NULL, NULL, 23, 2, 3, 10, 0, '2003-12-10 13:44:09.802', 0, '2003-12-10 13:44:09.802', true);
INSERT INTO help_tableof_contents VALUES (26, 'Export Data ', NULL, NULL, 23, 2, 3, 15, 0, '2003-12-10 13:44:09.81', 0, '2003-12-10 13:44:09.81', true);
INSERT INTO help_tableof_contents VALUES (27, 'Exporting data', NULL, NULL, 23, 2, 3, 20, 0, '2003-12-10 13:44:09.818', 0, '2003-12-10 13:44:09.818', true);
INSERT INTO help_tableof_contents VALUES (28, 'Pipeline', NULL, NULL, 1, 4, 2, 15, 0, '2003-12-10 13:44:09.827', 0, '2003-12-10 13:44:09.827', true);
INSERT INTO help_tableof_contents VALUES (29, 'Overview', NULL, NULL, 28, 4, 3, 5, 0, '2003-12-10 13:44:09.832', 0, '2003-12-10 13:44:09.832', true);
INSERT INTO help_tableof_contents VALUES (30, 'Add a Opportunity', NULL, NULL, 28, 4, 3, 10, 0, '2003-12-10 13:44:09.841', 0, '2003-12-10 13:44:09.841', true);
INSERT INTO help_tableof_contents VALUES (31, 'Search Opportunities ', NULL, NULL, 28, 4, 3, 15, 0, '2003-12-10 13:44:09.849', 0, '2003-12-10 13:44:09.849', true);
INSERT INTO help_tableof_contents VALUES (32, 'Export Data', NULL, NULL, 28, 4, 3, 20, 0, '2003-12-10 13:44:09.857', 0, '2003-12-10 13:44:09.857', true);
INSERT INTO help_tableof_contents VALUES (33, 'Accounts', NULL, NULL, 1, 1, 2, 20, 0, '2003-12-10 13:44:09.866', 0, '2003-12-10 13:44:09.866', true);
INSERT INTO help_tableof_contents VALUES (34, 'Overview', NULL, NULL, 33, 1, 3, 5, 0, '2003-12-10 13:44:09.87', 0, '2003-12-10 13:44:09.87', true);
INSERT INTO help_tableof_contents VALUES (35, 'Add a Account', NULL, NULL, 33, 1, 3, 10, 0, '2003-12-10 13:44:09.879', 0, '2003-12-10 13:44:09.879', true);
INSERT INTO help_tableof_contents VALUES (36, 'Modify Account', NULL, NULL, 33, 1, 3, 15, 0, '2003-12-10 13:44:09.888', 0, '2003-12-10 13:44:09.888', true);
INSERT INTO help_tableof_contents VALUES (37, 'Contact Details', NULL, NULL, 36, 1, 4, 20, 0, '2003-12-10 13:44:09.897', 0, '2003-12-10 13:44:09.897', true);
INSERT INTO help_tableof_contents VALUES (38, 'Folder Record Details', NULL, NULL, 36, 1, 4, 25, 0, '2003-12-10 13:44:09.905', 0, '2003-12-10 13:44:09.905', true);
INSERT INTO help_tableof_contents VALUES (39, 'Opportunity Details', NULL, NULL, 36, 1, 4, 30, 0, '2003-12-10 13:44:09.913', 0, '2003-12-10 13:44:09.913', true);
INSERT INTO help_tableof_contents VALUES (40, 'Revenue Details', NULL, NULL, 36, 1, 4, 35, 0, '2003-12-10 13:44:09.921', 0, '2003-12-10 13:44:09.921', true);
INSERT INTO help_tableof_contents VALUES (41, 'Revenue Details', NULL, NULL, 40, 1, 5, 40, 0, '2003-12-10 13:44:09.93', 0, '2003-12-10 13:44:09.93', true);
INSERT INTO help_tableof_contents VALUES (42, 'Add Revenue ', NULL, NULL, 40, 1, 5, 45, 0, '2003-12-10 13:44:09.938', 0, '2003-12-10 13:44:09.938', true);
INSERT INTO help_tableof_contents VALUES (43, 'Modify Revenue ', NULL, NULL, 40, 1, 5, 50, 0, '2003-12-10 13:44:09.946', 0, '2003-12-10 13:44:09.946', true);
INSERT INTO help_tableof_contents VALUES (44, 'Ticket Details', NULL, NULL, 36, 1, 4, 55, 0, '2003-12-10 13:44:09.954', 0, '2003-12-10 13:44:09.954', true);
INSERT INTO help_tableof_contents VALUES (45, 'Document Details', NULL, NULL, 36, 1, 4, 60, 0, '2003-12-10 13:44:09.962', 0, '2003-12-10 13:44:09.962', true);
INSERT INTO help_tableof_contents VALUES (46, 'Search Accounts ', NULL, NULL, 33, 1, 3, 65, 0, '2003-12-10 13:44:09.971', 0, '2003-12-10 13:44:09.971', true);
INSERT INTO help_tableof_contents VALUES (47, 'Account Details', NULL, NULL, 33, 1, 3, 70, 0, '2003-12-10 13:44:09.979', 0, '2003-12-10 13:44:09.979', true);
INSERT INTO help_tableof_contents VALUES (48, 'Revenue Dashboard ', NULL, NULL, 33, 1, 3, 75, 0, '2003-12-10 13:44:09.987', 0, '2003-12-10 13:44:09.987', true);
INSERT INTO help_tableof_contents VALUES (49, 'Export Data ', NULL, NULL, 33, 1, 3, 80, 0, '2003-12-10 13:44:09.996', 0, '2003-12-10 13:44:09.996', true);
INSERT INTO help_tableof_contents VALUES (50, 'Tickets', NULL, NULL, 1, 8, 2, 25, 0, '2003-12-10 13:44:10.004', 0, '2003-12-10 13:44:10.004', true);
INSERT INTO help_tableof_contents VALUES (51, 'Ticket Details ', NULL, NULL, 50, 8, 3, 5, 0, '2003-12-10 13:44:10.009', 0, '2003-12-10 13:44:10.009', true);
INSERT INTO help_tableof_contents VALUES (52, 'Add a Ticket', NULL, NULL, 50, 8, 3, 10, 0, '2003-12-10 13:44:10.018', 0, '2003-12-10 13:44:10.018', true);
INSERT INTO help_tableof_contents VALUES (53, 'Search Existing Tickets', NULL, NULL, 50, 8, 3, 15, 0, '2003-12-10 13:44:10.027', 0, '2003-12-10 13:44:10.027', true);
INSERT INTO help_tableof_contents VALUES (54, 'Export Data ', NULL, NULL, 50, 8, 3, 20, 0, '2003-12-10 13:44:10.035', 0, '2003-12-10 13:44:10.035', true);
INSERT INTO help_tableof_contents VALUES (55, 'Modify Ticket Details', NULL, NULL, 50, 8, 3, 25, 0, '2003-12-10 13:44:10.043', 0, '2003-12-10 13:44:10.043', true);
INSERT INTO help_tableof_contents VALUES (56, 'Modify Ticket Details', NULL, NULL, 55, 8, 4, 30, 0, '2003-12-10 13:44:10.051', 0, '2003-12-10 13:44:10.051', true);
INSERT INTO help_tableof_contents VALUES (57, 'List of Tasks', NULL, NULL, 55, 8, 4, 35, 0, '2003-12-10 13:44:10.06', 0, '2003-12-10 13:44:10.06', true);
INSERT INTO help_tableof_contents VALUES (58, 'List of Documents ', NULL, NULL, 55, 8, 4, 40, 0, '2003-12-10 13:44:10.068', 0, '2003-12-10 13:44:10.068', true);
INSERT INTO help_tableof_contents VALUES (59, 'List of Folder Records', NULL, NULL, 55, 8, 4, 45, 0, '2003-12-10 13:44:10.078', 0, '2003-12-10 13:44:10.078', true);
INSERT INTO help_tableof_contents VALUES (60, 'Add Folder Record', NULL, NULL, 55, 8, 4, 50, 0, '2003-12-10 13:44:10.088', 0, '2003-12-10 13:44:10.088', true);
INSERT INTO help_tableof_contents VALUES (61, 'Ticket Log History', NULL, NULL, 55, 8, 4, 55, 0, '2003-12-10 13:44:10.096', 0, '2003-12-10 13:44:10.096', true);
INSERT INTO help_tableof_contents VALUES (62, 'Employees', NULL, NULL, 1, 15, 2, 30, 0, '2003-12-10 13:44:10.104', 0, '2003-12-10 13:44:10.104', true);
INSERT INTO help_tableof_contents VALUES (63, 'Overview', NULL, NULL, 62, 15, 3, 5, 0, '2003-12-10 13:44:10.109', 0, '2003-12-10 13:44:10.109', true);
INSERT INTO help_tableof_contents VALUES (64, 'Employee Details ', NULL, NULL, 62, 15, 3, 10, 0, '2003-12-10 13:44:10.117', 0, '2003-12-10 13:44:10.117', true);
INSERT INTO help_tableof_contents VALUES (65, 'Add an Employee', NULL, NULL, 62, 15, 3, 15, 0, '2003-12-10 13:44:10.126', 0, '2003-12-10 13:44:10.126', true);
INSERT INTO help_tableof_contents VALUES (66, 'Modify Employee Details ', NULL, NULL, 62, 15, 3, 20, 0, '2003-12-10 13:44:10.134', 0, '2003-12-10 13:44:10.134', true);
INSERT INTO help_tableof_contents VALUES (67, 'Reports', NULL, NULL, 1, 14, 2, 35, 0, '2003-12-10 13:44:10.143', 0, '2003-12-10 13:44:10.143', true);
INSERT INTO help_tableof_contents VALUES (68, 'Overview', NULL, NULL, 67, 14, 3, 5, 0, '2003-12-10 13:44:10.147', 0, '2003-12-10 13:44:10.147', true);
INSERT INTO help_tableof_contents VALUES (69, 'List of Modules', NULL, NULL, 67, 14, 3, 10, 0, '2003-12-10 13:44:10.156', 0, '2003-12-10 13:44:10.156', true);
INSERT INTO help_tableof_contents VALUES (70, 'Admin', NULL, NULL, 1, 9, 2, 40, 0, '2003-12-10 13:44:10.164', 0, '2003-12-10 13:44:10.164', true);
INSERT INTO help_tableof_contents VALUES (71, 'List of Users', NULL, NULL, 70, 9, 3, 5, 0, '2003-12-10 13:44:10.169', 0, '2003-12-10 13:44:10.169', true);
INSERT INTO help_tableof_contents VALUES (72, 'Adding a New User', NULL, NULL, 71, 9, 4, 10, 0, '2003-12-10 13:44:10.177', 0, '2003-12-10 13:44:10.177', true);
INSERT INTO help_tableof_contents VALUES (73, 'Modify User Details', NULL, NULL, 71, 9, 4, 15, 0, '2003-12-10 13:44:10.186', 0, '2003-12-10 13:44:10.186', true);
INSERT INTO help_tableof_contents VALUES (74, 'User Login History', NULL, NULL, 71, 9, 4, 20, 0, '2003-12-10 13:44:10.194', 0, '2003-12-10 13:44:10.194', true);
INSERT INTO help_tableof_contents VALUES (75, 'Viewpoints of User', NULL, NULL, 71, 9, 4, 25, 0, '2003-12-10 13:44:10.202', 0, '2003-12-10 13:44:10.202', true);
INSERT INTO help_tableof_contents VALUES (76, 'Add Viewpoint', NULL, NULL, 75, 9, 5, 30, 0, '2003-12-10 13:44:10.21', 0, '2003-12-10 13:44:10.21', true);
INSERT INTO help_tableof_contents VALUES (77, 'Update Viewpoint ', NULL, NULL, 75, 9, 5, 35, 0, '2003-12-10 13:44:10.219', 0, '2003-12-10 13:44:10.219', true);
INSERT INTO help_tableof_contents VALUES (78, 'List of Roles', NULL, NULL, 70, 9, 3, 40, 0, '2003-12-10 13:44:10.228', 0, '2003-12-10 13:44:10.228', true);
INSERT INTO help_tableof_contents VALUES (79, 'Add a New Role', NULL, NULL, 78, 9, 4, 45, 0, '2003-12-10 13:44:10.237', 0, '2003-12-10 13:44:10.237', true);
INSERT INTO help_tableof_contents VALUES (80, 'Update Role ', NULL, NULL, 78, 9, 4, 50, 0, '2003-12-10 13:44:10.247', 0, '2003-12-10 13:44:10.247', true);
INSERT INTO help_tableof_contents VALUES (81, 'Configure Modules ', NULL, NULL, 70, 9, 3, 55, 0, '2003-12-10 13:44:10.255', 0, '2003-12-10 13:44:10.255', true);
INSERT INTO help_tableof_contents VALUES (82, 'Configuration Options ', NULL, NULL, 81, 9, 4, 60, 0, '2003-12-10 13:44:10.263', 0, '2003-12-10 13:44:10.263', true);
INSERT INTO help_tableof_contents VALUES (83, 'Edit Lookup List ', NULL, NULL, 82, 9, 5, 65, 0, '2003-12-10 13:44:10.272', 0, '2003-12-10 13:44:10.272', true);
INSERT INTO help_tableof_contents VALUES (84, 'Adding a New Folder', NULL, NULL, 82, 9, 5, 70, 0, '2003-12-10 13:44:10.281', 0, '2003-12-10 13:44:10.281', true);
INSERT INTO help_tableof_contents VALUES (85, 'Modify Existing Folder', NULL, NULL, 82, 9, 5, 75, 0, '2003-12-10 13:44:10.289', 0, '2003-12-10 13:44:10.289', true);
INSERT INTO help_tableof_contents VALUES (86, 'Configure System ', NULL, NULL, 70, 9, 3, 80, 0, '2003-12-10 13:44:10.297', 0, '2003-12-10 13:44:10.297', true);
INSERT INTO help_tableof_contents VALUES (87, 'Modify Timeout ', NULL, NULL, 86, 9, 4, 85, 0, '2003-12-10 13:44:10.306', 0, '2003-12-10 13:44:10.306', true);
INSERT INTO help_tableof_contents VALUES (88, 'Resource Usage Details', NULL, NULL, 70, 9, 3, 90, 0, '2003-12-10 13:44:10.316', 0, '2003-12-10 13:44:10.316', true);


--
-- Data for TOC entry 647 (OID 33375)
-- Name: help_tableofcontentitem_links; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO help_tableofcontentitem_links VALUES (1, 3, 1, 0, '2003-12-10 13:44:09.364', 0, '2003-12-10 13:44:09.364', true);
INSERT INTO help_tableofcontentitem_links VALUES (2, 4, 2, 0, '2003-12-10 13:44:09.425', 0, '2003-12-10 13:44:09.425', true);
INSERT INTO help_tableofcontentitem_links VALUES (3, 5, 3, 0, '2003-12-10 13:44:09.465', 0, '2003-12-10 13:44:09.465', true);
INSERT INTO help_tableofcontentitem_links VALUES (4, 6, 4, 0, '2003-12-10 13:44:09.482', 0, '2003-12-10 13:44:09.482', true);
INSERT INTO help_tableofcontentitem_links VALUES (5, 7, 5, 0, '2003-12-10 13:44:09.509', 0, '2003-12-10 13:44:09.509', true);
INSERT INTO help_tableofcontentitem_links VALUES (6, 8, 6, 0, '2003-12-10 13:44:09.517', 0, '2003-12-10 13:44:09.517', true);
INSERT INTO help_tableofcontentitem_links VALUES (7, 9, 7, 0, '2003-12-10 13:44:09.526', 0, '2003-12-10 13:44:09.526', true);
INSERT INTO help_tableofcontentitem_links VALUES (8, 10, 8, 0, '2003-12-10 13:44:09.534', 0, '2003-12-10 13:44:09.534', true);
INSERT INTO help_tableofcontentitem_links VALUES (9, 11, 9, 0, '2003-12-10 13:44:09.592', 0, '2003-12-10 13:44:09.592', true);
INSERT INTO help_tableofcontentitem_links VALUES (10, 12, 10, 0, '2003-12-10 13:44:09.609', 0, '2003-12-10 13:44:09.609', true);
INSERT INTO help_tableofcontentitem_links VALUES (11, 13, 11, 0, '2003-12-10 13:44:09.636', 0, '2003-12-10 13:44:09.636', true);
INSERT INTO help_tableofcontentitem_links VALUES (12, 14, 12, 0, '2003-12-10 13:44:09.646', 0, '2003-12-10 13:44:09.646', true);
INSERT INTO help_tableofcontentitem_links VALUES (13, 15, 13, 0, '2003-12-10 13:44:09.658', 0, '2003-12-10 13:44:09.658', true);
INSERT INTO help_tableofcontentitem_links VALUES (14, 16, 14, 0, '2003-12-10 13:44:09.668', 0, '2003-12-10 13:44:09.668', true);
INSERT INTO help_tableofcontentitem_links VALUES (15, 17, 15, 0, '2003-12-10 13:44:09.676', 0, '2003-12-10 13:44:09.676', true);
INSERT INTO help_tableofcontentitem_links VALUES (16, 18, 16, 0, '2003-12-10 13:44:09.686', 0, '2003-12-10 13:44:09.686', true);
INSERT INTO help_tableofcontentitem_links VALUES (17, 19, 17, 0, '2003-12-10 13:44:09.694', 0, '2003-12-10 13:44:09.694', true);
INSERT INTO help_tableofcontentitem_links VALUES (18, 20, 18, 0, '2003-12-10 13:44:09.703', 0, '2003-12-10 13:44:09.703', true);
INSERT INTO help_tableofcontentitem_links VALUES (19, 21, 19, 0, '2003-12-10 13:44:09.712', 0, '2003-12-10 13:44:09.712', true);
INSERT INTO help_tableofcontentitem_links VALUES (20, 22, 20, 0, '2003-12-10 13:44:09.739', 0, '2003-12-10 13:44:09.739', true);
INSERT INTO help_tableofcontentitem_links VALUES (21, 24, 34, 0, '2003-12-10 13:44:09.798', 0, '2003-12-10 13:44:09.798', true);
INSERT INTO help_tableofcontentitem_links VALUES (22, 25, 35, 0, '2003-12-10 13:44:09.806', 0, '2003-12-10 13:44:09.806', true);
INSERT INTO help_tableofcontentitem_links VALUES (23, 26, 36, 0, '2003-12-10 13:44:09.815', 0, '2003-12-10 13:44:09.815', true);
INSERT INTO help_tableofcontentitem_links VALUES (24, 27, 37, 0, '2003-12-10 13:44:09.824', 0, '2003-12-10 13:44:09.824', true);
INSERT INTO help_tableofcontentitem_links VALUES (25, 29, 78, 0, '2003-12-10 13:44:09.836', 0, '2003-12-10 13:44:09.836', true);
INSERT INTO help_tableofcontentitem_links VALUES (26, 30, 79, 0, '2003-12-10 13:44:09.846', 0, '2003-12-10 13:44:09.846', true);
INSERT INTO help_tableofcontentitem_links VALUES (27, 31, 80, 0, '2003-12-10 13:44:09.854', 0, '2003-12-10 13:44:09.854', true);
INSERT INTO help_tableofcontentitem_links VALUES (28, 32, 81, 0, '2003-12-10 13:44:09.862', 0, '2003-12-10 13:44:09.862', true);
INSERT INTO help_tableofcontentitem_links VALUES (29, 34, 111, 0, '2003-12-10 13:44:09.875', 0, '2003-12-10 13:44:09.875', true);
INSERT INTO help_tableofcontentitem_links VALUES (30, 35, 112, 0, '2003-12-10 13:44:09.884', 0, '2003-12-10 13:44:09.884', true);
INSERT INTO help_tableofcontentitem_links VALUES (31, 36, 113, 0, '2003-12-10 13:44:09.893', 0, '2003-12-10 13:44:09.893', true);
INSERT INTO help_tableofcontentitem_links VALUES (32, 37, 114, 0, '2003-12-10 13:44:09.901', 0, '2003-12-10 13:44:09.901', true);
INSERT INTO help_tableofcontentitem_links VALUES (33, 38, 115, 0, '2003-12-10 13:44:09.91', 0, '2003-12-10 13:44:09.91', true);
INSERT INTO help_tableofcontentitem_links VALUES (34, 39, 116, 0, '2003-12-10 13:44:09.918', 0, '2003-12-10 13:44:09.918', true);
INSERT INTO help_tableofcontentitem_links VALUES (35, 40, 117, 0, '2003-12-10 13:44:09.926', 0, '2003-12-10 13:44:09.926', true);
INSERT INTO help_tableofcontentitem_links VALUES (36, 41, 118, 0, '2003-12-10 13:44:09.934', 0, '2003-12-10 13:44:09.934', true);
INSERT INTO help_tableofcontentitem_links VALUES (37, 42, 119, 0, '2003-12-10 13:44:09.942', 0, '2003-12-10 13:44:09.942', true);
INSERT INTO help_tableofcontentitem_links VALUES (38, 43, 120, 0, '2003-12-10 13:44:09.951', 0, '2003-12-10 13:44:09.951', true);
INSERT INTO help_tableofcontentitem_links VALUES (39, 44, 121, 0, '2003-12-10 13:44:09.959', 0, '2003-12-10 13:44:09.959', true);
INSERT INTO help_tableofcontentitem_links VALUES (40, 45, 122, 0, '2003-12-10 13:44:09.967', 0, '2003-12-10 13:44:09.967', true);
INSERT INTO help_tableofcontentitem_links VALUES (41, 46, 123, 0, '2003-12-10 13:44:09.975', 0, '2003-12-10 13:44:09.975', true);
INSERT INTO help_tableofcontentitem_links VALUES (42, 47, 124, 0, '2003-12-10 13:44:09.984', 0, '2003-12-10 13:44:09.984', true);
INSERT INTO help_tableofcontentitem_links VALUES (43, 48, 125, 0, '2003-12-10 13:44:09.992', 0, '2003-12-10 13:44:09.992', true);
INSERT INTO help_tableofcontentitem_links VALUES (44, 49, 126, 0, '2003-12-10 13:44:10.001', 0, '2003-12-10 13:44:10.001', true);
INSERT INTO help_tableofcontentitem_links VALUES (45, 51, 175, 0, '2003-12-10 13:44:10.014', 0, '2003-12-10 13:44:10.014', true);
INSERT INTO help_tableofcontentitem_links VALUES (46, 52, 176, 0, '2003-12-10 13:44:10.023', 0, '2003-12-10 13:44:10.023', true);
INSERT INTO help_tableofcontentitem_links VALUES (47, 53, 177, 0, '2003-12-10 13:44:10.031', 0, '2003-12-10 13:44:10.031', true);
INSERT INTO help_tableofcontentitem_links VALUES (48, 54, 178, 0, '2003-12-10 13:44:10.04', 0, '2003-12-10 13:44:10.04', true);
INSERT INTO help_tableofcontentitem_links VALUES (49, 55, 179, 0, '2003-12-10 13:44:10.048', 0, '2003-12-10 13:44:10.048', true);
INSERT INTO help_tableofcontentitem_links VALUES (50, 56, 180, 0, '2003-12-10 13:44:10.056', 0, '2003-12-10 13:44:10.056', true);
INSERT INTO help_tableofcontentitem_links VALUES (51, 57, 181, 0, '2003-12-10 13:44:10.064', 0, '2003-12-10 13:44:10.064', true);
INSERT INTO help_tableofcontentitem_links VALUES (52, 58, 182, 0, '2003-12-10 13:44:10.075', 0, '2003-12-10 13:44:10.075', true);
INSERT INTO help_tableofcontentitem_links VALUES (53, 59, 183, 0, '2003-12-10 13:44:10.084', 0, '2003-12-10 13:44:10.084', true);
INSERT INTO help_tableofcontentitem_links VALUES (54, 60, 184, 0, '2003-12-10 13:44:10.092', 0, '2003-12-10 13:44:10.092', true);
INSERT INTO help_tableofcontentitem_links VALUES (55, 61, 185, 0, '2003-12-10 13:44:10.10', 0, '2003-12-10 13:44:10.10', true);
INSERT INTO help_tableofcontentitem_links VALUES (56, 63, 202, 0, '2003-12-10 13:44:10.113', 0, '2003-12-10 13:44:10.113', true);
INSERT INTO help_tableofcontentitem_links VALUES (57, 64, 203, 0, '2003-12-10 13:44:10.122', 0, '2003-12-10 13:44:10.122', true);
INSERT INTO help_tableofcontentitem_links VALUES (58, 65, 204, 0, '2003-12-10 13:44:10.131', 0, '2003-12-10 13:44:10.131', true);
INSERT INTO help_tableofcontentitem_links VALUES (59, 66, 205, 0, '2003-12-10 13:44:10.139', 0, '2003-12-10 13:44:10.139', true);
INSERT INTO help_tableofcontentitem_links VALUES (60, 68, 207, 0, '2003-12-10 13:44:10.152', 0, '2003-12-10 13:44:10.152', true);
INSERT INTO help_tableofcontentitem_links VALUES (61, 69, 208, 0, '2003-12-10 13:44:10.16', 0, '2003-12-10 13:44:10.16', true);
INSERT INTO help_tableofcontentitem_links VALUES (62, 71, 216, 0, '2003-12-10 13:44:10.174', 0, '2003-12-10 13:44:10.174', true);
INSERT INTO help_tableofcontentitem_links VALUES (63, 72, 217, 0, '2003-12-10 13:44:10.182', 0, '2003-12-10 13:44:10.182', true);
INSERT INTO help_tableofcontentitem_links VALUES (64, 73, 218, 0, '2003-12-10 13:44:10.19', 0, '2003-12-10 13:44:10.19', true);
INSERT INTO help_tableofcontentitem_links VALUES (65, 74, 219, 0, '2003-12-10 13:44:10.199', 0, '2003-12-10 13:44:10.199', true);
INSERT INTO help_tableofcontentitem_links VALUES (66, 75, 220, 0, '2003-12-10 13:44:10.207', 0, '2003-12-10 13:44:10.207', true);
INSERT INTO help_tableofcontentitem_links VALUES (67, 76, 221, 0, '2003-12-10 13:44:10.216', 0, '2003-12-10 13:44:10.216', true);
INSERT INTO help_tableofcontentitem_links VALUES (68, 77, 222, 0, '2003-12-10 13:44:10.224', 0, '2003-12-10 13:44:10.224', true);
INSERT INTO help_tableofcontentitem_links VALUES (69, 78, 223, 0, '2003-12-10 13:44:10.233', 0, '2003-12-10 13:44:10.233', true);
INSERT INTO help_tableofcontentitem_links VALUES (70, 79, 224, 0, '2003-12-10 13:44:10.243', 0, '2003-12-10 13:44:10.243', true);
INSERT INTO help_tableofcontentitem_links VALUES (71, 80, 225, 0, '2003-12-10 13:44:10.252', 0, '2003-12-10 13:44:10.252', true);
INSERT INTO help_tableofcontentitem_links VALUES (72, 81, 226, 0, '2003-12-10 13:44:10.26', 0, '2003-12-10 13:44:10.26', true);
INSERT INTO help_tableofcontentitem_links VALUES (73, 82, 227, 0, '2003-12-10 13:44:10.268', 0, '2003-12-10 13:44:10.268', true);
INSERT INTO help_tableofcontentitem_links VALUES (74, 83, 228, 0, '2003-12-10 13:44:10.277', 0, '2003-12-10 13:44:10.277', true);
INSERT INTO help_tableofcontentitem_links VALUES (75, 84, 229, 0, '2003-12-10 13:44:10.285', 0, '2003-12-10 13:44:10.285', true);
INSERT INTO help_tableofcontentitem_links VALUES (76, 85, 230, 0, '2003-12-10 13:44:10.294', 0, '2003-12-10 13:44:10.294', true);
INSERT INTO help_tableofcontentitem_links VALUES (77, 86, 231, 0, '2003-12-10 13:44:10.302', 0, '2003-12-10 13:44:10.302', true);
INSERT INTO help_tableofcontentitem_links VALUES (78, 87, 232, 0, '2003-12-10 13:44:10.312', 0, '2003-12-10 13:44:10.312', true);
INSERT INTO help_tableofcontentitem_links VALUES (79, 88, 233, 0, '2003-12-10 13:44:10.32', 0, '2003-12-10 13:44:10.32', true);


--
-- Data for TOC entry 648 (OID 33401)
-- Name: lookup_help_features; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 649 (OID 33414)
-- Name: help_features; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO help_features VALUES (1, 1, NULL, 'You can view the accounts that need attention', 0, '2003-12-10 13:44:03.085', 0, '2003-12-10 13:44:03.085', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (2, 1, NULL, 'You can make calls with the contact information readily accessible', 0, '2003-12-10 13:44:03.097', 0, '2003-12-10 13:44:03.097', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (3, 1, NULL, 'You can view the tasks assigned to you', 0, '2003-12-10 13:44:03.102', 0, '2003-12-10 13:44:03.102', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (4, 1, NULL, 'You can view the tickets assigned to you', 0, '2003-12-10 13:44:03.106', 0, '2003-12-10 13:44:03.106', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (5, 2, NULL, 'The select button can be used to view the details, reply, forward or delete a particular message.', 0, '2003-12-10 13:44:03.317', 0, '2003-12-10 13:44:03.317', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (6, 2, NULL, 'You can add a new message', 0, '2003-12-10 13:44:03.323', 0, '2003-12-10 13:44:03.323', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (7, 2, NULL, 'Clicking on the message would show the details of the message', 0, '2003-12-10 13:44:03.327', 0, '2003-12-10 13:44:03.327', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (8, 2, NULL, 'The drop down can be used to select the messages present in the inbox, sent messages or the archived ones', 0, '2003-12-10 13:44:03.331', 0, '2003-12-10 13:44:03.331', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (9, 2, NULL, 'Sort on one of the column headers by clicking on the column of your choice', 0, '2003-12-10 13:44:03.335', 0, '2003-12-10 13:44:03.335', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (10, 3, NULL, 'You can reply, archive, forward or delete each message by clicking the corresponding button', 0, '2003-12-10 13:44:03.356', 0, '2003-12-10 13:44:03.356', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (11, 4, NULL, 'A new message can be composed either to the contacts or the employees present in the recipients list. The options field can be checked to send a copy to the employees task list apart from sending the employee an email.', 0, '2003-12-10 13:44:03.372', 0, '2003-12-10 13:44:03.372', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (12, 5, NULL, 'You can send the email by clicking the send button', 0, '2003-12-10 13:44:03.427', 0, '2003-12-10 13:44:03.427', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (13, 5, NULL, 'You can add to the list of recipients by using the link "Add Recipients"', 0, '2003-12-10 13:44:03.431', 0, '2003-12-10 13:44:03.431', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (14, 5, NULL, 'You can click the check box to send an Internet email to the recipients', 0, '2003-12-10 13:44:03.436', 0, '2003-12-10 13:44:03.436', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (15, 5, NULL, 'Type directly in the Body test field to modify the message', 0, '2003-12-10 13:44:03.44', 0, '2003-12-10 13:44:03.44', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (16, 7, NULL, 'You can add the list of recipients by using the link "Add Recipients" and also click the check box which would send a email to the recipients ', 0, '2003-12-10 13:44:03.472', 0, '2003-12-10 13:44:03.472', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (17, 7, NULL, 'You can send the email by clicking the send button ', 0, '2003-12-10 13:44:03.477', 0, '2003-12-10 13:44:03.477', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (18, 7, NULL, 'You can edit the message by typing directly in the Body text area', 0, '2003-12-10 13:44:03.484', 0, '2003-12-10 13:44:03.484', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (19, 8, NULL, 'You can add a quick task. This task would have just the description and whether the task is personal or not', 0, '2003-12-10 13:44:03.501', 0, '2003-12-10 13:44:03.501', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (20, 8, NULL, 'For each of the existing tasks, you can view, modify, forward or delete the tasks by clicking on the Action button', 0, '2003-12-10 13:44:03.517', 0, '2003-12-10 13:44:03.517', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (21, 8, NULL, 'You can select to view your tasks or tasks assigned by you to others working under you. Each can be viewed in three different modes. i.e. the completed tasks, uncompleted tasks or both.', 0, '2003-12-10 13:44:03.521', 0, '2003-12-10 13:44:03.521', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (22, 8, NULL, 'You can add a detailed (advanced) task, where you can set up the priority, status, whether the task is shared or not, task assignment, give the estimated time and add some detailed notes for it.', 0, '2003-12-10 13:44:03.525', 0, '2003-12-10 13:44:03.525', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (23, 9, NULL, 'Link this task to a contact and when you look at the task list, there will be a link to the contact record next to the task, allowing you to go directly to the contact', 0, '2003-12-10 13:44:03.563', 0, '2003-12-10 13:44:03.563', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (24, 9, NULL, 'Filling in a Due Date will make ths task show up on that date in the Home Page calendar', 0, '2003-12-10 13:44:03.571', 0, '2003-12-10 13:44:03.571', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (25, 9, NULL, 'Making the task personal will hide it from your hierarchy', 0, '2003-12-10 13:44:03.575', 0, '2003-12-10 13:44:03.575', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (26, 9, NULL, 'You can assign a task to people lower than you in your hierarchy', 0, '2003-12-10 13:44:03.579', 0, '2003-12-10 13:44:03.579', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (27, 9, NULL, 'Marking a task as complete will document the task as having been done, and immediately remove it from the Task List', 0, '2003-12-10 13:44:03.583', 0, '2003-12-10 13:44:03.583', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (28, 10, NULL, 'Allows you to forward a task to one or more users of the system. Checking the options fields check box indicates that if the recipient is a user of the system, then a copy of the task is send to the recipient''s Internet email.', 0, '2003-12-10 13:44:03.598', 0, '2003-12-10 13:44:03.598', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (29, 10, NULL, 'The Subject line is mandatory', 0, '2003-12-10 13:44:03.603', 0, '2003-12-10 13:44:03.603', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (30, 10, NULL, 'You can add more text to the body of the message by typing directly in the Body text area', 0, '2003-12-10 13:44:03.607', 0, '2003-12-10 13:44:03.607', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (31, 11, NULL, 'Due dates will show on the Home Page calendar', 0, '2003-12-10 13:44:03.65', 0, '2003-12-10 13:44:03.65', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (32, 11, NULL, 'Completing a task will remove it from the task list', 0, '2003-12-10 13:44:03.654', 0, '2003-12-10 13:44:03.654', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (33, 11, NULL, 'You can assign a task to someone lower than you in your heirarchy', 0, '2003-12-10 13:44:03.657', 0, '2003-12-10 13:44:03.657', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (34, 11, NULL, 'You can Add or Change the contact that this task is linked to. When viewing the task, you will be able to view the contact information with one click.', 0, '2003-12-10 13:44:03.661', 0, '2003-12-10 13:44:03.661', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (35, 12, NULL, 'You can also view all the in progress Action Lists, completed list or both together.', 0, '2003-12-10 13:44:03.671', 0, '2003-12-10 13:44:03.671', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (36, 12, NULL, 'You can also keep track of the Progress of your contacts. The number of them Completed and the Total are shown in the Progress Columns.', 0, '2003-12-10 13:44:03.674', 0, '2003-12-10 13:44:03.674', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (37, 12, NULL, 'You can add a new Action List with a description and status. You can select the contacts for this new Action List. For each of the contacts in the Action List, you can select a corresponding action with the Action Button: view details, modify contact, add contacts or delete the Action List.', 0, '2003-12-10 13:44:03.678', 0, '2003-12-10 13:44:03.678', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (38, 13, NULL, 'Clicking on the contact name will give you a pop up with more details about the contact and also about the related folders, calls, messages and opportunities.', 0, '2003-12-10 13:44:03.691', 0, '2003-12-10 13:44:03.691', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (39, 13, NULL, 'You can add contacts to the list and also Modify the List using "Add Contacts to list" and "Modify List" respectively.', 0, '2003-12-10 13:44:03.695', 0, '2003-12-10 13:44:03.695', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (40, 13, NULL, 'For the Action List you can also view all the in progress contacts, completed contacts or both.', 0, '2003-12-10 13:44:03.699', 0, '2003-12-10 13:44:03.699', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (41, 13, NULL, 'For each of the contacts you can add a call, opportunity, ticket, task or send a message, which would correspondingly show up in their respective tabs. For example, adding a ticket to the contact would be reflected in the Ticket tab.', 0, '2003-12-10 13:44:03.704', 0, '2003-12-10 13:44:03.704', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (42, 14, NULL, 'Select where the contacts will come from (General Contact, Account Contacts) in the From dropdown', 0, '2003-12-10 13:44:03.761', 0, '2003-12-10 13:44:03.761', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (43, 14, NULL, 'Enter some search text, depending on the Operator you chose.', 0, '2003-12-10 13:44:03.765', 0, '2003-12-10 13:44:03.765', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (44, 14, NULL, 'Choose an Operator base on the Field you chose.', 0, '2003-12-10 13:44:03.769', 0, '2003-12-10 13:44:03.769', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (45, 14, NULL, 'Choose one of the many Field Names on which to base your query.', 0, '2003-12-10 13:44:03.772', 0, '2003-12-10 13:44:03.772', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (46, 14, NULL, 'You can Add or Remove contacts manually with the Add/Remove Contacts link', 0, '2003-12-10 13:44:03.778', 0, '2003-12-10 13:44:03.778', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (47, 14, NULL, 'Add your query with the Add button at the bottom of the query frame. You can have multipe queries that make up the criteria for a group. You will get the result of all the queries.', 0, '2003-12-10 13:44:03.782', 0, '2003-12-10 13:44:03.782', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (48, 14, NULL, 'Save the Action List and generate the list of contacts by clicking the Save button at the bottom or top of the page.', 0, '2003-12-10 13:44:03.786', 0, '2003-12-10 13:44:03.786', NULL, NULL, true, 7);
INSERT INTO help_features VALUES (49, 15, NULL, 'You can check the status checkbox to indicate that the New Action List is complete', 0, '2003-12-10 13:44:03.795', 0, '2003-12-10 13:44:03.795', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (50, 15, NULL, 'The details of the New Action List can be saved by clicking the save button', 0, '2003-12-10 13:44:03.799', 0, '2003-12-10 13:44:03.799', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (51, 16, NULL, 'Click Update at the bottom of the page to save your reassignment, Cancel to quit the page without saving, and Reset to reset all the fields to their defaults and start over.', 0, '2003-12-10 13:44:03.812', 0, '2003-12-10 13:44:03.812', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (52, 16, NULL, 'Choose a User to reassign date from in the top dropdown. Only users below you in your hierarchy will be present here.', 0, '2003-12-10 13:44:03.816', 0, '2003-12-10 13:44:03.816', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (53, 16, NULL, 'Select one or more To Users in the To User column to reassign the various assets to. The number of each type of asset available to be reassigned is shown in parentheses after the asset in the first column.', 0, '2003-12-10 13:44:03.819', 0, '2003-12-10 13:44:03.819', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (54, 17, NULL, 'The location of the employee can be changed, i.e. the time zone can be changed by clicking on "Configure my location"', 0, '2003-12-10 13:44:03.854', 0, '2003-12-10 13:44:03.854', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (55, 17, NULL, 'You can update your personal information by clicking on "Update my personal information"', 0, '2003-12-10 13:44:03.858', 0, '2003-12-10 13:44:03.858', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (56, 17, NULL, 'You can change your password by clicking on "Change my password"', 0, '2003-12-10 13:44:03.861', 0, '2003-12-10 13:44:03.861', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (57, 18, NULL, 'Save your changes by clicking the Update button at the top or bottom of the page. The Cancel button forgets the changes and quits the page. The Reset button resets all fields to their original values so you can start over.', 0, '2003-12-10 13:44:03.87', 0, '2003-12-10 13:44:03.87', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (58, 18, NULL, 'The only required field is your last name, but you should fill in as much as you can to make the system as useful as possible. Email address is particularly useful.', 0, '2003-12-10 13:44:03.874', 0, '2003-12-10 13:44:03.874', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (59, 19, NULL, 'The location settings can be changed by selecting the time zone from the drop down list and click the update button to update the settings', 0, '2003-12-10 13:44:03.887', 0, '2003-12-10 13:44:03.887', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (60, 20, NULL, 'You can update your password by clicking on the update button', 0, '2003-12-10 13:44:03.896', 0, '2003-12-10 13:44:03.896', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (61, 21, NULL, 'For each of the existing tasks, you can view, modify, forward or delete the tasks by clicking on the Action button', 0, '2003-12-10 13:44:03.913', 0, '2003-12-10 13:44:03.913', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (62, 21, NULL, 'You can add a quick task. This task would have just the description and whether the task is personal or not', 0, '2003-12-10 13:44:03.917', 0, '2003-12-10 13:44:03.917', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (63, 21, NULL, 'You can add or update a detailed task called advanced task, wherein you can set up the priority, the status, whether the task is shared or not, also is the task assigned to self or someone working under the owner of the tasks, give the estimated time and add some detailed notes in it.', 0, '2003-12-10 13:44:03.92', 0, '2003-12-10 13:44:03.92', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (64, 21, NULL, 'Checking the existing task''s check box indicates that the particular task is completed.', 0, '2003-12-10 13:44:03.924', 0, '2003-12-10 13:44:03.924', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (65, 21, NULL, 'You can select to view your tasks or tasks assigned by you to others. Each task can be viewed in three different modes i.e. the completed tasks, uncompleted tasks or all the tasks.', 0, '2003-12-10 13:44:03.928', 0, '2003-12-10 13:44:03.928', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (66, 28, NULL, 'You can add the new Action List here. Along with description and the status you need to select the contacts you want in this Action List. You can populate the list in two ways. The first is to use the Add/Remove contacts', 0, '2003-12-10 13:44:04.015', 0, '2003-12-10 13:44:04.015', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (67, 28, NULL, 'The second is to define the criteria to generate the list. Once its generated we can add them to the selected criteria and contactsby using the add feature present.', 0, '2003-12-10 13:44:04.018', 0, '2003-12-10 13:44:04.018', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (68, 31, NULL, 'Check new contacts to add them to your list, uncheck existing contacts to remove them from your list.', 0, '2003-12-10 13:44:04.042', 0, '2003-12-10 13:44:04.042', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (69, 31, NULL, 'Select All Contact, My Contacts or Account Contacts from the dropdown at the top.', 0, '2003-12-10 13:44:04.046', 0, '2003-12-10 13:44:04.046', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (70, 31, NULL, 'Finish by clicking Done at the bottom of the page.', 0, '2003-12-10 13:44:04.05', 0, '2003-12-10 13:44:04.05', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (71, 33, NULL, 'You can add or update a detailed task called advanced task, wherein you can set up the priority, the status, whether the task is shared or not, also is the task assigned to self or someone working under the owner of the tasks, give the estimated time and add some detailed notes in it.', 0, '2003-12-10 13:44:04.064', 0, '2003-12-10 13:44:04.064', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (72, 34, NULL, 'You can select the contact category using the radio button and if the contact category is someone permanently associated with an account, then you can select the contact using the select link next to it', 0, '2003-12-10 13:44:04.096', 0, '2003-12-10 13:44:04.096', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (73, 34, NULL, 'You can save the details about the employee using the Save button', 0, '2003-12-10 13:44:04.10', 0, '2003-12-10 13:44:04.10', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (74, 34, NULL, 'The Save & New button saves the details of the employee and also opens up a blank form start another contact.', 0, '2003-12-10 13:44:04.154', 0, '2003-12-10 13:44:04.154', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (75, 34, NULL, 'The only mandatory field is the Last Name, however, it is important to fill in as much as possible. These fields can be used for various types of queries later.', 0, '2003-12-10 13:44:04.158', 0, '2003-12-10 13:44:04.158', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (76, 34, NULL, 'The contact type can be selected using the select link next to the contact type.', 0, '2003-12-10 13:44:04.162', 0, '2003-12-10 13:44:04.162', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (77, 35, NULL, 'If the contact already exists in the system, you can search for that contact by name, company, title, contact type or source.', 0, '2003-12-10 13:44:04.171', 0, '2003-12-10 13:44:04.171', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (78, 36, NULL, 'New export data can be generated by choosing the "Generate new export" link at the top of the page', 0, '2003-12-10 13:44:04.191', 0, '2003-12-10 13:44:04.191', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (79, 36, NULL, 'Use the dropdown to choose which data to display: the list of all the exported data in the system or only your own.', 0, '2003-12-10 13:44:04.195', 0, '2003-12-10 13:44:04.195', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (80, 36, NULL, 'The exported data can be viewed in html format by clicking on the report name. The exported data can also be downloaded in CSV format or deleted by clicking the Select button in the action field.', 0, '2003-12-10 13:44:04.199', 0, '2003-12-10 13:44:04.199', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (81, 37, NULL, 'You can add all the fields or add / delete single fields from the report by using the buttons in the middle of the page. First highlight a field on the left to add or a field on the right to delete.', 0, '2003-12-10 13:44:04.212', 0, '2003-12-10 13:44:04.212', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (82, 37, NULL, 'Use the Up and Down buttons on the right to sort the fields', 0, '2003-12-10 13:44:04.215', 0, '2003-12-10 13:44:04.215', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (83, 37, NULL, 'The Subject is mandatory. Select which set of contacts the export will come from with Criteria. Select the Primary sort with the Sorting dropdown.', 0, '2003-12-10 13:44:04.219', 0, '2003-12-10 13:44:04.219', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (84, 37, NULL, 'Click the Generate button when you are ready to generate the exported report', 0, '2003-12-10 13:44:04.225', 0, '2003-12-10 13:44:04.225', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (85, 38, NULL, 'You can update, cancel or reset the details of the contact using the corresponding buttons present.', 0, '2003-12-10 13:44:04.25', 0, '2003-12-10 13:44:04.25', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (86, 41, NULL, 'You can also click on the select button under the action field to view, modify, forward or delete a call', 0, '2003-12-10 13:44:04.28', 0, '2003-12-10 13:44:04.28', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (87, 41, NULL, 'Clicking on the subject of the call will give complete details about the call.', 0, '2003-12-10 13:44:04.284', 0, '2003-12-10 13:44:04.284', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (88, 41, NULL, 'You can add a call associated with a contact', 0, '2003-12-10 13:44:04.287', 0, '2003-12-10 13:44:04.287', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (89, 42, NULL, 'The save button lets you create a new call which is associated with the call', 0, '2003-12-10 13:44:04.30', 0, '2003-12-10 13:44:04.30', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (90, 45, NULL, 'You can also click the select button under the action column for viewing, modifying and deleting an opportunity', 0, '2003-12-10 13:44:04.324', 0, '2003-12-10 13:44:04.324', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (91, 45, NULL, 'Clicking on the name of the opportunity will let you view the details about the opportunity.', 0, '2003-12-10 13:44:04.328', 0, '2003-12-10 13:44:04.328', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (92, 45, NULL, 'Choosing the different types of opportunities from the drop down can filter the display.', 0, '2003-12-10 13:44:04.332', 0, '2003-12-10 13:44:04.332', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (93, 45, NULL, 'Add an opportunity associated with a contact', 0, '2003-12-10 13:44:04.336', 0, '2003-12-10 13:44:04.336', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (94, 46, NULL, 'You can modify, delete or forward each call using the corresponding buttons', 0, '2003-12-10 13:44:04.356', 0, '2003-12-10 13:44:04.356', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (95, 47, NULL, 'The component type can be selected using the select  link.', 0, '2003-12-10 13:44:04.373', 0, '2003-12-10 13:44:04.373', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (96, 47, NULL, 'You can assign the component to any of the employee present using the dropdown list present.', 0, '2003-12-10 13:44:04.376', 0, '2003-12-10 13:44:04.376', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (97, 48, NULL, 'An opportunity can be renamed or deleted using the buttons present at the bottom of the page.', 0, '2003-12-10 13:44:04.385', 0, '2003-12-10 13:44:04.385', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (98, 48, NULL, 'Clicking on the select button lets you view, modify or delete the details about the component  ', 0, '2003-12-10 13:44:04.389', 0, '2003-12-10 13:44:04.389', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (99, 48, NULL, 'Clicking on the name of the component would show the details about the component', 0, '2003-12-10 13:44:04.393', 0, '2003-12-10 13:44:04.393', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (100, 48, NULL, 'Add a new component associated with the contact.', 0, '2003-12-10 13:44:04.396', 0, '2003-12-10 13:44:04.396', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (101, 49, NULL, 'You can modify and delete the opportunity created using the modify and the delete buttons', 0, '2003-12-10 13:44:04.413', 0, '2003-12-10 13:44:04.413', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (102, 50, NULL, 'The type of the call can be selected using the drop down list and all the other details related to the call are updated using the update button', 0, '2003-12-10 13:44:04.427', 0, '2003-12-10 13:44:04.427', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (103, 58, NULL, 'You can click the attachments or the surveys link present along the message text', 0, '2003-12-10 13:44:04.516', 0, '2003-12-10 13:44:04.516', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (104, 59, NULL, 'You can modify and delete the opportunity created using the modify and the delete buttons', 0, '2003-12-10 13:44:04.525', 0, '2003-12-10 13:44:04.525', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (105, 60, NULL, 'You can also click the select button under the action field to view, modify, clone or delete a contact', 0, '2003-12-10 13:44:04.538', 0, '2003-12-10 13:44:04.538', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (106, 60, NULL, 'Clicking the name of the contact will show the details about the contact', 0, '2003-12-10 13:44:04.541', 0, '2003-12-10 13:44:04.541', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (107, 60, NULL, 'Add a contact using the link  Add a contact at the top of the page', 0, '2003-12-10 13:44:04.545', 0, '2003-12-10 13:44:04.545', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (108, 61, NULL, 'You can also choose to display the list of all the exported data in the system or the exported data created by you.', 0, '2003-12-10 13:44:04.558', 0, '2003-12-10 13:44:04.558', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (109, 61, NULL, 'The exported data can be viewed as a .csv file or in the html format. The exported data can also be deleted when the select button in the action field is clicked.', 0, '2003-12-10 13:44:04.561', 0, '2003-12-10 13:44:04.561', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (110, 61, NULL, 'New export data can be generated, which lets you choose from the contacts list.', 0, '2003-12-10 13:44:04.565', 0, '2003-12-10 13:44:04.565', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (111, 62, NULL, 'You can modify, clone, or delete the details of the contact. ', 0, '2003-12-10 13:44:04.574', 0, '2003-12-10 13:44:04.574', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (112, 63, NULL, 'Clicking on the name of the message gives more details about the message.', 0, '2003-12-10 13:44:04.629', 0, '2003-12-10 13:44:04.629', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (113, 63, NULL, 'You can view the messages in two different views i.e. all the messages present or the messages created/assigned by you', 0, '2003-12-10 13:44:04.633', 0, '2003-12-10 13:44:04.633', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (114, 64, NULL, 'You can select the list of the recipients to whom you want to forward the particular call to by using the Add Recipients link.', 0, '2003-12-10 13:44:04.654', 0, '2003-12-10 13:44:04.654', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (115, 66, NULL, 'The component type can be selected using the select  link', 0, '2003-12-10 13:44:04.668', 0, '2003-12-10 13:44:04.668', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (116, 66, NULL, 'You can assign the component to any of the employee present using the dropdown list present. ', 0, '2003-12-10 13:44:04.672', 0, '2003-12-10 13:44:04.672', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (117, 67, NULL, 'You can update or cancel the information changed using the update or cancel button present.', 0, '2003-12-10 13:44:04.681', 0, '2003-12-10 13:44:04.681', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (118, 68, NULL, 'The folders can be selected using the drop down list.', 0, '2003-12-10 13:44:04.692', 0, '2003-12-10 13:44:04.692', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (119, 68, NULL, 'You can also click on the record name, to view the folder record details. ', 0, '2003-12-10 13:44:04.696', 0, '2003-12-10 13:44:04.696', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (120, 68, NULL, 'You can view the details and modify them by clicking the select button under the action column', 0, '2003-12-10 13:44:04.70', 0, '2003-12-10 13:44:04.70', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (121, 68, NULL, 'You can add a new record to a folder using the Add a record to this folder link present.', 0, '2003-12-10 13:44:04.704', 0, '2003-12-10 13:44:04.704', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (122, 68, NULL, 'The folders can be selected using the drop down list.', 0, '2003-12-10 13:44:04.707', 0, '2003-12-10 13:44:04.707', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (123, 69, NULL, 'The changes made in the details of the folders can be updated or canceled using the update or cancel button.', 0, '2003-12-10 13:44:04.731', 0, '2003-12-10 13:44:04.731', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (124, 70, NULL, 'You can also click the select button under the action column for viewing, modifying and deleting an opportunity ', 0, '2003-12-10 13:44:04.744', 0, '2003-12-10 13:44:04.744', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (125, 70, NULL, 'Clicking on the name of the opportunity will let you view the details about the opportunity', 0, '2003-12-10 13:44:04.747', 0, '2003-12-10 13:44:04.747', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (126, 70, NULL, 'Choosing the different types of opportunities from the drop down can filter the display. ', 0, '2003-12-10 13:44:04.751', 0, '2003-12-10 13:44:04.751', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (127, 70, NULL, 'Add an opportunity associated with a contact ', 0, '2003-12-10 13:44:04.755', 0, '2003-12-10 13:44:04.755', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (128, 71, NULL, 'If the contact already exists in the system, you can search for that contact by name, company, title, contact type or source, by typing the search term in the appropriate field, and clicking the Search button.', 0, '2003-12-10 13:44:04.767', 0, '2003-12-10 13:44:04.767', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (129, 71, NULL, 'The data present can be used to export data and display that in different formats by clicking the Export link at the top of the page.', 0, '2003-12-10 13:44:04.771', 0, '2003-12-10 13:44:04.771', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (130, 71, NULL, 'Click Add to add a new contact into the system', 0, '2003-12-10 13:44:04.778', 0, '2003-12-10 13:44:04.778', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (131, 72, NULL, 'You can filter the contact list in three different views. The views are all contacts, your contacts and Account contacts.', 0, '2003-12-10 13:44:04.804', 0, '2003-12-10 13:44:04.804', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (132, 72, NULL, 'Check any or all the contacts from the list you want to assign to your action List.', 0, '2003-12-10 13:44:04.809', 0, '2003-12-10 13:44:04.809', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (133, 74, NULL, 'You can also view, modify, clone and delete the contact by clicking the corresponding button ', 0, '2003-12-10 13:44:04.823', 0, '2003-12-10 13:44:04.823', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (134, 74, NULL, 'You can associate calls, messages and opportunities with each of the contact already existing in the system ', 0, '2003-12-10 13:44:04.826', 0, '2003-12-10 13:44:04.826', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (135, 75, NULL, 'The contact type can be selected using the select link.', 0, '2003-12-10 13:44:04.835', 0, '2003-12-10 13:44:04.835', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (136, 75, NULL, 'The details can be updated using update button.', 0, '2003-12-10 13:44:04.839', 0, '2003-12-10 13:44:04.839', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (137, 76, NULL, 'You can view all the messages related to the contact or only the messages related the owner (My messages)', 0, '2003-12-10 13:44:04.848', 0, '2003-12-10 13:44:04.848', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (138, 77, NULL, 'This is for adding or updating a new detailed employee record into the system. The last name is the only compulsory field in creating an employee record.', 0, '2003-12-10 13:44:04.857', 0, '2003-12-10 13:44:04.857', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (139, 78, NULL, 'You can view the progress chart in different views for all the employees working under the owner or creator of the opportunity. The views can be selected from the drop down box present under the chart. The mouse over or a click on the break point on the progress chart will give the date and exact value associated with that point.', 0, '2003-12-10 13:44:04.908', 0, '2003-12-10 13:44:04.908', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (140, 78, NULL, 'The opportunities created are also shown, with their names and the probable gross revenue associated with that opportunity. Clicking on the opportunities shows a details page for the opportunity.', 0, '2003-12-10 13:44:04.962', 0, '2003-12-10 13:44:04.962', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (141, 78, NULL, 'The list of employees reporting to a particular employee/supervisor is also shown below the progress chart. Clicking on an employee shows the Opportunity page from that person''s point of view. You can then work your way back up the chain by clicking the Up One Level link.', 0, '2003-12-10 13:44:04.966', 0, '2003-12-10 13:44:04.966', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (142, 79, NULL, 'The probability of Close, Estimated Close Date, Best Guess Estimate (what will the gross revenue be for this component?), and Estimated Term (over what time period?), are mandatory fields.', 0, '2003-12-10 13:44:04.998', 0, '2003-12-10 13:44:04.998', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (143, 79, NULL, 'You can assign the component to yourself or one of the users in your hierarchy.', 0, '2003-12-10 13:44:05.001', 0, '2003-12-10 13:44:05.001', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (144, 79, NULL, 'The Component Description is a mandatory field. Be descriptive as you will be using this to search on later.', 0, '2003-12-10 13:44:05.005', 0, '2003-12-10 13:44:05.005', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (145, 79, NULL, 'Use the Save button to save your changes and exit, Cancel to cancel your changes and exit, and Reset to cancel your changes and start over.', 0, '2003-12-10 13:44:05.009', 0, '2003-12-10 13:44:05.009', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (146, 79, NULL, 'Enter and Alert Description and Date to remind yourself to follow up on this component at a certain time.', 0, '2003-12-10 13:44:05.013', 0, '2003-12-10 13:44:05.013', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (147, 79, NULL, 'You must associate the component with either a Contact or an Account. Choose one of the radio buttons, then one of the Select links.', 0, '2003-12-10 13:44:05.016', 0, '2003-12-10 13:44:05.016', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (148, 80, NULL, 'The opportunities already present in the system can be searched using this featureOpportunities can be searched on description, the account name or the contact name with whom the opportunity is associated. It can also be searched by current progress / stage of the opportunity or the closing date range.', 0, '2003-12-10 13:44:05.025', 0, '2003-12-10 13:44:05.025', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (149, 81, NULL, 'The exported data can be viewed or downloaded as a .csv file or in html format. The exported data can also be deleted when the select button in the action column is clicked.', 0, '2003-12-10 13:44:05.046', 0, '2003-12-10 13:44:05.046', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (150, 81, NULL, 'You can also choose to display the list of all the exported data in the system or the exported data created by you.', 0, '2003-12-10 13:44:05.05', 0, '2003-12-10 13:44:05.05', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (151, 81, NULL, 'New export data can be generated by choosing from the contacts list. ', 0, '2003-12-10 13:44:05.054', 0, '2003-12-10 13:44:05.054', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (152, 84, NULL, 'Component Description is a mandatory field', 0, '2003-12-10 13:44:05.103', 0, '2003-12-10 13:44:05.103', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (153, 84, NULL, 'Use Update at the top or bottom to save your changes, Cancel to quit this page without saving, and Reset to reset all fields to default and start over.', 0, '2003-12-10 13:44:05.107', 0, '2003-12-10 13:44:05.107', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (154, 84, NULL, 'Add an Alert Description and Date to alert you via a CRM System Message to take a new action', 0, '2003-12-10 13:44:05.111', 0, '2003-12-10 13:44:05.111', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (155, 84, NULL, 'Probability of close, Estimated Close Date (when you will get the revenue), Best Guess Estimate (how much revenue you will get), and Estimated Term (what term the revenue will be realized over) are all the mandatory fields.', 0, '2003-12-10 13:44:05.114', 0, '2003-12-10 13:44:05.114', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (156, 84, NULL, 'You can select a Component Type from the dropdown. These component types are configurable by your System Administrator.', 0, '2003-12-10 13:44:05.118', 0, '2003-12-10 13:44:05.118', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (157, 84, NULL, 'You can assign the component to any User using the dropdown list present.', 0, '2003-12-10 13:44:05.122', 0, '2003-12-10 13:44:05.122', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (158, 85, NULL, 'The type of the call can be a phone, fax or in person. Some notes regarding the call can be noted. You can add an alert to remind you to follow up on this call.', 0, '2003-12-10 13:44:05.131', 0, '2003-12-10 13:44:05.131', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (159, 85, NULL, 'The Contact dropdown is automatically populated with the correct contacts for the company or account you are dealing with.', 0, '2003-12-10 13:44:05.134', 0, '2003-12-10 13:44:05.134', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (160, 86, NULL, 'You can update or cancel the information changed using the update or cancel button present.', 0, '2003-12-10 13:44:05.144', 0, '2003-12-10 13:44:05.144', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (161, 88, NULL, 'You can update the details of the documents using the update button.', 0, '2003-12-10 13:44:05.163', 0, '2003-12-10 13:44:05.163', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (162, 92, NULL, 'The component details are shown with the additional options for modifying and deleting the component', 0, '2003-12-10 13:44:05.222', 0, '2003-12-10 13:44:05.222', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (163, 93, NULL, 'The document can be uploaded using the upload button.', 0, '2003-12-10 13:44:05.25', 0, '2003-12-10 13:44:05.25', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (164, 93, NULL, 'The new version of the document can be selected using the browse button.', 0, '2003-12-10 13:44:05.259', 0, '2003-12-10 13:44:05.259', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (165, 94, NULL, 'For each of the component you can view the details, modify the content or delete it completely using the select button in the Action column.', 0, '2003-12-10 13:44:05.267', 0, '2003-12-10 13:44:05.267', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (166, 94, NULL, 'You can add an opportunity here by giving complete details about the opportunity.', 0, '2003-12-10 13:44:05.271', 0, '2003-12-10 13:44:05.271', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (167, 94, NULL, 'The search results for the opportunities present in the system are displayed here.', 0, '2003-12-10 13:44:05.275', 0, '2003-12-10 13:44:05.275', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (168, 94, NULL, 'There are different views of the opportunities you can choose from the drop down list and the corresponding types for the opportunities', 0, '2003-12-10 13:44:05.279', 0, '2003-12-10 13:44:05.279', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (169, 95, NULL, 'In the Documents tab, the documents associated with the particular opportunity can be added. This also displays the documents already linked with this opportunity and other details about the document. The details can be viewed, downloaded, modified or deleted by using the select button in the action column ', 0, '2003-12-10 13:44:05.292', 0, '2003-12-10 13:44:05.292', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (170, 95, NULL, 'In the Calls tab you can add a call associated with the opportunity. This also displays the calls already linked with this opportunity and other details about the call. The call details can be viewed, modified, forwarded or deleted by using the select button in the action column ', 0, '2003-12-10 13:44:05.298', 0, '2003-12-10 13:44:05.298', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (171, 95, NULL, 'You can rename or delete the opportunity itself using the buttons below', 0, '2003-12-10 13:44:05.302', 0, '2003-12-10 13:44:05.302', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (172, 95, NULL, 'You can modify, view and delete the details of any particular component by clicking the select button in the action column', 0, '2003-12-10 13:44:05.306', 0, '2003-12-10 13:44:05.306', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (173, 95, NULL, 'In the Components tab, you can add a component. It also displays the status, amount and the date when the component will be closed. ', 0, '2003-12-10 13:44:05.31', 0, '2003-12-10 13:44:05.31', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (174, 95, NULL, 'There are three tabs in each opportunity i.e. components, calls and documents', 0, '2003-12-10 13:44:05.313', 0, '2003-12-10 13:44:05.313', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (175, 95, NULL, 'You get the organization name or the contact name at the top, which on clicking will take you to the Account details ', 0, '2003-12-10 13:44:05.317', 0, '2003-12-10 13:44:05.317', NULL, NULL, true, 7);
INSERT INTO help_features VALUES (176, 96, NULL, 'You can select the list of the recipients to whom you want to forward the particular call to by using the Add Recipients link. This will bring up a window with all users, from which you can then choose using check boxes.', 0, '2003-12-10 13:44:05.33', 0, '2003-12-10 13:44:05.33', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (177, 96, NULL, 'You can email a copy of the call to a user''s Internet email by checking the Email check box.', 0, '2003-12-10 13:44:05.334', 0, '2003-12-10 13:44:05.334', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (178, 96, NULL, 'You can add to the message by simply typing in the Body text box.', 0, '2003-12-10 13:44:05.337', 0, '2003-12-10 13:44:05.337', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (179, 97, NULL, 'The version of the particular document can be modified using the add version link present.', 0, '2003-12-10 13:44:05.346', 0, '2003-12-10 13:44:05.346', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (180, 97, NULL, 'A document can be viewed, downloaded, modified or deleted by using the select button in the action column', 0, '2003-12-10 13:44:05.35', 0, '2003-12-10 13:44:05.35', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (181, 97, NULL, 'A click on the subject of the document will show all the versions present.', 0, '2003-12-10 13:44:05.354', 0, '2003-12-10 13:44:05.354', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (182, 98, NULL, 'The type of the call can be selected using the drop down list and all the other details related to the call are updated using the update button', 0, '2003-12-10 13:44:05.37', 0, '2003-12-10 13:44:05.37', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (183, 99, NULL, 'In the Calls tab you can add a call associated with the opportunity. This also displays the calls already linked with this opportunity and other details about the call. The call details can be viewed, modified, forwarded or deleted by using the select button in the action column', 0, '2003-12-10 13:44:05.379', 0, '2003-12-10 13:44:05.379', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (184, 99, NULL, 'In the Components tab, you can add a component. It also displays the status, amount and the date when the component will be closed.', 0, '2003-12-10 13:44:05.383', 0, '2003-12-10 13:44:05.383', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (185, 99, NULL, 'There are three tabs in each opportunity i.e. components, calls and documents.', 0, '2003-12-10 13:44:05.386', 0, '2003-12-10 13:44:05.386', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (186, 99, NULL, 'You can rename or delete the whole opportunity (not just one of these components) using the buttons at the bottom.', 0, '2003-12-10 13:44:05.39', 0, '2003-12-10 13:44:05.39', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (187, 99, NULL, 'The organization or contact name appears on top, above the Components Tab, which will take you to the Account details, when clicked.', 0, '2003-12-10 13:44:05.394', 0, '2003-12-10 13:44:05.394', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (188, 99, NULL, 'You can modify, view and delete the details of any particular component by clicking the select button in the Action column, on the far left.', 0, '2003-12-10 13:44:05.398', 0, '2003-12-10 13:44:05.398', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (189, 99, NULL, 'In the Documents tab, the documents associated with the particular opportunity can be added. This also displays the documents already linked with this opportunity and other details about the document. Details can be viewed, downloaded, modified or deleted by using the select button in the action column', 0, '2003-12-10 13:44:05.401', 0, '2003-12-10 13:44:05.401', NULL, NULL, true, 7);
INSERT INTO help_features VALUES (190, 100, NULL, 'In the Calls tab you can add a call associated with the opportunity. This also displays the calls already linked with this opportunity and other details about the call. The call details can be viewed, modified, forwarded or deleted by using the select button in the action column', 0, '2003-12-10 13:44:05.424', 0, '2003-12-10 13:44:05.424', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (191, 100, NULL, 'If the call subject is clicked then it will show you complete details about the call. ', 0, '2003-12-10 13:44:05.428', 0, '2003-12-10 13:44:05.428', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (192, 101, NULL, 'There are different views of the opportunities you can choose from the dropdown list and the corresponding types of the opportunities ', 0, '2003-12-10 13:44:05.445', 0, '2003-12-10 13:44:05.445', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (193, 101, NULL, 'You can add an opportunity here by giving complete details about the opportunity.', 0, '2003-12-10 13:44:05.448', 0, '2003-12-10 13:44:05.448', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (194, 101, NULL, 'The search results for the opportunities present in the system are displayed here.', 0, '2003-12-10 13:44:05.452', 0, '2003-12-10 13:44:05.452', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (195, 101, NULL, 'For each of the component you can view the details, modify the content or delete it completely using the select button in the Action column. You can click on any of the component names, which shows more details about the component like the calls and the documents associated with it.', 0, '2003-12-10 13:44:05.456', 0, '2003-12-10 13:44:05.456', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (196, 102, NULL, 'The list of employees reporting to a particular employee/supervisor is also shown below the progress chart.', 0, '2003-12-10 13:44:05.505', 0, '2003-12-10 13:44:05.505', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (197, 102, NULL, 'The opportunities created are also shown, with its name and the probable amount of money associated with that opportunity. Clicking on the opportunities would give a better understanding of the details of the opportunity', 0, '2003-12-10 13:44:05.508', 0, '2003-12-10 13:44:05.508', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (198, 102, NULL, 'You can view the progress chart in different views for all the employees working under the owner or creator of the opportunity. The views can be selected from the drop down box present under the chart. The mouse over or a click on the break point on the progress chart will give the date and exact value associated with that point.', 0, '2003-12-10 13:44:05.512', 0, '2003-12-10 13:44:05.512', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (199, 103, NULL, 'The component details are shown with the additional options for modifying and deleting the component', 0, '2003-12-10 13:44:05.521', 0, '2003-12-10 13:44:05.521', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (200, 104, NULL, 'The component type can be selected using the select  link.', 0, '2003-12-10 13:44:05.534', 0, '2003-12-10 13:44:05.534', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (201, 104, NULL, 'You can assign the component to any of the employee present using the dropdown list present.', 0, '2003-12-10 13:44:05.538', 0, '2003-12-10 13:44:05.538', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (202, 105, NULL, 'In the Calls tab you can add a call associated with the opportunity. This also displays the calls already linked with this opportunity and other details about the call. The call details can be viewed, modified, forwarded or deleted by using the select button in the action column ', 0, '2003-12-10 13:44:05.547', 0, '2003-12-10 13:44:05.547', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (203, 105, NULL, 'If the call subject is clicked then it will show you complete details about the call', 0, '2003-12-10 13:44:05.551', 0, '2003-12-10 13:44:05.551', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (204, 106, NULL, 'You can modify, delete and forward each of the calls.', 0, '2003-12-10 13:44:05.56', 0, '2003-12-10 13:44:05.56', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (205, 107, NULL, 'Clicking the Upload button will upload the selected document into the system.', 0, '2003-12-10 13:44:05.578', 0, '2003-12-10 13:44:05.578', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (206, 107, NULL, 'Clicking the Browse button opens a file browser on your own system. Simply navigate to the file on your drive that you want to upload and click Open. This will close the window and bring you back to the Upload page.', 0, '2003-12-10 13:44:05.582', 0, '2003-12-10 13:44:05.582', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (207, 107, NULL, 'Add a very descriptive Subject for the file. This is a mandatory field.', 0, '2003-12-10 13:44:05.586', 0, '2003-12-10 13:44:05.586', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (208, 108, NULL, 'All the versions of the particular document can be downloaded from here. Simply select the version you want and click the Download link on the far left.', 0, '2003-12-10 13:44:05.606', 0, '2003-12-10 13:44:05.606', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (209, 109, NULL, 'The exported data can be viewed as a .csv file or in the html format. The exported data can also be deleted when the select button in the action field is clicked.', 0, '2003-12-10 13:44:05.615', 0, '2003-12-10 13:44:05.615', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (210, 109, NULL, 'You can also choose to display the list of all the exported data in the system or the exported data created by you.', 0, '2003-12-10 13:44:05.618', 0, '2003-12-10 13:44:05.618', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (211, 109, NULL, 'New export data can be generated, which lets you choose from the contacts list.', 0, '2003-12-10 13:44:05.622', 0, '2003-12-10 13:44:05.622', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (212, 109, NULL, 'Click on the subject of the new export, the data is displayed in html format', 0, '2003-12-10 13:44:05.626', 0, '2003-12-10 13:44:05.626', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (213, 110, NULL, 'Click the Generate button at top or bottom to generate the report from the fields you have included. Click cancel to quit and go back to the Export Data page.', 0, '2003-12-10 13:44:05.636', 0, '2003-12-10 13:44:05.636', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (214, 110, NULL, 'Highlight the fields you want to include in the left column and click the Add or All link. Highlight fields in the right column and click the Del link to remove them.', 0, '2003-12-10 13:44:05.639', 0, '2003-12-10 13:44:05.639', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (215, 110, NULL, 'Use the Sorting dropdown to sort the report by one of a variety of fields', 0, '2003-12-10 13:44:05.643', 0, '2003-12-10 13:44:05.643', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (216, 110, NULL, 'Use the Criteria dropdown to use opportunities from My or All Opportunities', 0, '2003-12-10 13:44:05.647', 0, '2003-12-10 13:44:05.647', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (217, 110, NULL, 'The Subject is a mandatory field.', 0, '2003-12-10 13:44:05.65', 0, '2003-12-10 13:44:05.65', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (218, 111, NULL, 'Clicking on the alert link will let you modify the details of the account owner.', 0, '2003-12-10 13:44:05.666', 0, '2003-12-10 13:44:05.666', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (219, 111, NULL, 'Accounts with contract end dates or other required actions will appear in the right hand window where you can take action on them.', 0, '2003-12-10 13:44:05.67', 0, '2003-12-10 13:44:05.67', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (220, 111, NULL, 'You can view the schedule, actions, alert dates and contract end dates for yourself or your employees by using the dropdown at the top of the page.', 0, '2003-12-10 13:44:05.724', 0, '2003-12-10 13:44:05.724', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (221, 111, NULL, 'You can modify the date range shown in the right hand window by clicking on a specific date on the calendar, or on one of the arrows to the left of each week on the calendar to give you a week''s view. Clicking on "Back To Next 7 Days View" at the top of the right window changes the view to the next seven days. The day or week you are currently viewing is highlighted in yellow. Today''s date is highlighted in blue. You can change the month and year using the dropdowns at the top of the calendar, and you can always return to today by using the Today link, also at the top of the calendar.', 0, '2003-12-10 13:44:05.728', 0, '2003-12-10 13:44:05.728', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (222, 112, NULL, 'Use the Insert button at top or bottom to save your changes, Cancel to quit without saving, and Reset to reset all the fields to their default values and start over.', 0, '2003-12-10 13:44:05.757', 0, '2003-12-10 13:44:05.757', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (223, 112, NULL, 'It''s a faily straightforward "fill in the blanks" exercise. There should be a "Primary" or "Business", or "Main" version of phone/fax numbers and addresses because other modules such as Communications Manager use these to perform other actions.', 0, '2003-12-10 13:44:05.761', 0, '2003-12-10 13:44:05.761', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (224, 112, NULL, 'Fill in as many fields as possible. Most of them can be used later as search terms and for queries in reports.', 0, '2003-12-10 13:44:05.765', 0, '2003-12-10 13:44:05.765', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (225, 112, NULL, 'Depending on whether you have chosen Organization or Individual, there are mandatory description fields to fill out about the account.', 0, '2003-12-10 13:44:05.769', 0, '2003-12-10 13:44:05.769', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (226, 112, NULL, 'Choose whether this account is an Organization or an Individual with the appropriate radio button.', 0, '2003-12-10 13:44:05.773', 0, '2003-12-10 13:44:05.773', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (227, 112, NULL, 'Clicking the Select link next to Account Type(s) will open a window with a variety of choices for Account Types. You cah choose and number by clicking the checkboxes to the left. It is important to use this feature as your choice(s) are used for searches and as the subject of querries in reports in other parts of the application.', 0, '2003-12-10 13:44:05.776', 0, '2003-12-10 13:44:05.776', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (228, 113, NULL, 'The account owner can also be changed using the drop down list', 0, '2003-12-10 13:44:05.789', 0, '2003-12-10 13:44:05.789', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (229, 113, NULL, 'The account type can be selected using the Select link', 0, '2003-12-10 13:44:05.793', 0, '2003-12-10 13:44:05.793', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (230, 113, NULL, 'This is for adding or updating account details. The last name or the organization name, based on the classification, is the only mandatory field in creating a new account. The type of account can be selected using the select option given next to the account type', 0, '2003-12-10 13:44:05.797', 0, '2003-12-10 13:44:05.797', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (231, 113, NULL, 'If the Account has a contract, you should enter a contract end date in the fields provided. This will generate an icon on the Home Page and an alert for the owner of the account that action must be taken at a prearranged time.', 0, '2003-12-10 13:44:05.801', 0, '2003-12-10 13:44:05.801', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (232, 114, NULL, 'You can also view, modify, clone and delete the contact by clicking the select button under the action column.', 0, '2003-12-10 13:44:05.834', 0, '2003-12-10 13:44:05.834', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (233, 114, NULL, 'When the name of the contact is clicked, it shows details of that contact, with the options to modify, clone and delete the contact details.', 0, '2003-12-10 13:44:05.837', 0, '2003-12-10 13:44:05.837', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (234, 114, NULL, 'You can add a contact, which is associated with the account.', 0, '2003-12-10 13:44:05.841', 0, '2003-12-10 13:44:05.841', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (235, 115, NULL, 'Using the select button in the action column you can view details and modify the record.', 0, '2003-12-10 13:44:05.862', 0, '2003-12-10 13:44:05.862', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (236, 115, NULL, 'You can click on the record type to view the folders details and modify them.', 0, '2003-12-10 13:44:05.866', 0, '2003-12-10 13:44:05.866', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (237, 115, NULL, 'A new record can be added to the folder.', 0, '2003-12-10 13:44:05.87', 0, '2003-12-10 13:44:05.87', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (238, 115, NULL, 'The folders can be populated by configuring the module in the admin tab.. The type of the folder can be changed using the drop down list shown.', 0, '2003-12-10 13:44:05.874', 0, '2003-12-10 13:44:05.874', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (239, 116, NULL, 'Opportunities associated with the contact, showing the best guess total and last modified date.', 0, '2003-12-10 13:44:05.884', 0, '2003-12-10 13:44:05.884', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (240, 116, NULL, 'You can add an opportunity.', 0, '2003-12-10 13:44:05.887', 0, '2003-12-10 13:44:05.887', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (241, 116, NULL, 'Three types of opportunities are present which can be selected from the drop down list.', 0, '2003-12-10 13:44:05.891', 0, '2003-12-10 13:44:05.891', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (242, 116, NULL, 'When the description of the opportunity is clicked, it will give you more details about the opportunity and the components present in it.', 0, '2003-12-10 13:44:05.895', 0, '2003-12-10 13:44:05.895', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (243, 117, NULL, 'By clicking on the description of the revenue you get the details about that revenue along with the options to modify and delete its details.', 0, '2003-12-10 13:44:05.916', 0, '2003-12-10 13:44:05.916', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (244, 117, NULL, 'You can view your revenue or all the revenues associated with the account using the drop down box ', 0, '2003-12-10 13:44:05.92', 0, '2003-12-10 13:44:05.92', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (245, 117, NULL, 'You can also view, modify and delete the details of the revenue by clicking the select button in the action column.', 0, '2003-12-10 13:44:05.924', 0, '2003-12-10 13:44:05.924', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (246, 117, NULL, 'Add / update a new revenue associated with the account.', 0, '2003-12-10 13:44:05.927', 0, '2003-12-10 13:44:05.927', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (247, 118, NULL, 'By clicking on the description of the revenue you get the details about that revenue along with the options to modify and delete its details.', 0, '2003-12-10 13:44:05.944', 0, '2003-12-10 13:44:05.944', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (248, 118, NULL, 'You can view your revenue or all the revenues associated with the account using the drop down box ', 0, '2003-12-10 13:44:05.947', 0, '2003-12-10 13:44:05.947', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (249, 118, NULL, 'You can also view, modify and delete the details of the revenue by clicking the select button in the action column.', 0, '2003-12-10 13:44:05.951', 0, '2003-12-10 13:44:05.951', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (250, 118, NULL, 'Add / update a new revenue associated with the account.', 0, '2003-12-10 13:44:05.955', 0, '2003-12-10 13:44:05.955', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (251, 119, NULL, 'Add new revenue associated with the account.', 0, '2003-12-10 13:44:05.971', 0, '2003-12-10 13:44:05.971', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (252, 120, NULL, 'The revenue details can be updated or reset the changes', 0, '2003-12-10 13:44:05.98', 0, '2003-12-10 13:44:05.98', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (253, 121, NULL, 'You can also click the select button under the action column to view, modify and delete the ticket.', 0, '2003-12-10 13:44:05.989', 0, '2003-12-10 13:44:05.989', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (254, 121, NULL, 'Clicking on the ticket number will let you view the details, modify and also lets you delete the ticket.', 0, '2003-12-10 13:44:05.993', 0, '2003-12-10 13:44:05.993', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (255, 121, NULL, 'Add a new ticket.', 0, '2003-12-10 13:44:05.996', 0, '2003-12-10 13:44:05.996', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (256, 122, NULL, 'The details of the documents can be viewed or modified by clicking on the select button under the Action column ', 0, '2003-12-10 13:44:06.009', 0, '2003-12-10 13:44:06.009', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (257, 122, NULL, 'The document versions can be updated by using the "add version" link ', 0, '2003-12-10 13:44:06.012', 0, '2003-12-10 13:44:06.012', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (258, 122, NULL, 'A new document can be added which is associated with the account', 0, '2003-12-10 13:44:06.016', 0, '2003-12-10 13:44:06.016', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (259, 122, NULL, 'You can view the details, modify, download or delete the documents associated with the account', 0, '2003-12-10 13:44:06.02', 0, '2003-12-10 13:44:06.02', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (260, 123, NULL, 'You can search for the accounts present in the system. The search can be based on the Account name, phone number or the Account type. Three types of accounts can be selected from the drop down list shown, which can also be used for searching', 0, '2003-12-10 13:44:06.029', 0, '2003-12-10 13:44:06.029', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (261, 124, NULL, 'Click Modify at the top or bottom of the page to modify these datails.', 0, '2003-12-10 13:44:06.046', 0, '2003-12-10 13:44:06.046', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (262, 125, NULL, 'The list of employees reporting to a particular employee/supervisor is also shown below the progress chart. ', 0, '2003-12-10 13:44:06.066', 0, '2003-12-10 13:44:06.066', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (263, 125, NULL, 'The Accounts present are also shown, with its name and the amount of money associated with that Account. Clicking on the Account would give a better understanding of the details of the Account', 0, '2003-12-10 13:44:06.07', 0, '2003-12-10 13:44:06.07', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (264, 125, NULL, 'You can view the progress chart in different views for all the employees working under the owner or creator of the Account. The views can be selected from the drop down box present under the chart. The mouse over or a click on the break point on the progress chart will give the date and exact value associated with that point. ', 0, '2003-12-10 13:44:06.074', 0, '2003-12-10 13:44:06.074', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (265, 126, NULL, 'The exported data can be viewed as a .csv file or in the html format. The exported data can also be deleted when the select button in the action field is clicked. ', 0, '2003-12-10 13:44:06.083', 0, '2003-12-10 13:44:06.083', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (266, 126, NULL, 'You can also choose to display the list of all the exported data in the system or the exported data created by you. ', 0, '2003-12-10 13:44:06.086', 0, '2003-12-10 13:44:06.086', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (267, 126, NULL, 'New export data can be generated using the "Generate new export" link', 0, '2003-12-10 13:44:06.09', 0, '2003-12-10 13:44:06.09', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (268, 127, NULL, 'The details are updated by clicking the Update button.', 0, '2003-12-10 13:44:06.104', 0, '2003-12-10 13:44:06.104', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (269, 130, NULL, 'There are filters through which you can exactly select the data needed to generate the export data. Apart from selecting the type of accounts and the criteria, you can also select the fields required and then sort them.', 0, '2003-12-10 13:44:06.129', 0, '2003-12-10 13:44:06.129', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (270, 131, NULL, 'Using the select button under the action column you can view the details about the call, modify the call, forward the call or delete the call on the whole.', 0, '2003-12-10 13:44:06.145', 0, '2003-12-10 13:44:06.145', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (271, 131, NULL, 'Clicking on the subject of the call will show you the details about the call that was made to the contact.', 0, '2003-12-10 13:44:06.149', 0, '2003-12-10 13:44:06.149', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (272, 131, NULL, 'You can add a call associated with the contact using the Add a call link.', 0, '2003-12-10 13:44:06.154', 0, '2003-12-10 13:44:06.154', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (273, 132, NULL, 'The record details can be saved using the save button', 0, '2003-12-10 13:44:06.163', 0, '2003-12-10 13:44:06.163', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (274, 133, NULL, 'The details of the new call can be saved using the save button.', 0, '2003-12-10 13:44:06.172', 0, '2003-12-10 13:44:06.172', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (275, 133, NULL, 'The call type can be selected from the dropdown box.', 0, '2003-12-10 13:44:06.176', 0, '2003-12-10 13:44:06.176', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (276, 134, NULL, 'You can browse to select a new document to upload if its related to the account', 0, '2003-12-10 13:44:06.189', 0, '2003-12-10 13:44:06.189', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (277, 137, NULL, 'You can upload a new version of a existing document.', 0, '2003-12-10 13:44:06.219', 0, '2003-12-10 13:44:06.219', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (278, 140, NULL, 'You can insert a new ticket, add the ticket source and also assign new contact.', 0, '2003-12-10 13:44:06.241', 0, '2003-12-10 13:44:06.241', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (279, 141, NULL, 'The details of the documents can be viewed or modified by clicking on the select button under the Action column ', 0, '2003-12-10 13:44:06.258', 0, '2003-12-10 13:44:06.258', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (280, 141, NULL, 'You can view the details, modify, download or delete the documents associated with the ticket', 0, '2003-12-10 13:44:06.263', 0, '2003-12-10 13:44:06.263', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (281, 141, NULL, 'A new document can be added which is associated with the ticket', 0, '2003-12-10 13:44:06.267', 0, '2003-12-10 13:44:06.267', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (282, 141, NULL, 'The document versions can be updated by using the "add version" link', 0, '2003-12-10 13:44:06.27', 0, '2003-12-10 13:44:06.27', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (283, 146, NULL, 'Clicking on the account name, shows complete details about the account', 0, '2003-12-10 13:44:06.318', 0, '2003-12-10 13:44:06.318', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (284, 146, NULL, 'You can add a new account', 0, '2003-12-10 13:44:06.322', 0, '2003-12-10 13:44:06.322', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (285, 146, NULL, 'The select button in the Action column allows you to view, modify and archive the account. Archiving makes the account invisible, but it is still in the database.', 0, '2003-12-10 13:44:06.325', 0, '2003-12-10 13:44:06.325', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (286, 147, NULL, 'You can download all the versions of the documents', 0, '2003-12-10 13:44:06.334', 0, '2003-12-10 13:44:06.334', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (287, 148, NULL, 'You can modify / update the current document information like the subject and the filename', 0, '2003-12-10 13:44:06.344', 0, '2003-12-10 13:44:06.344', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (288, 149, NULL, 'The details of the account can be modified here. The details can be saved using the Modify button.', 0, '2003-12-10 13:44:06.354', 0, '2003-12-10 13:44:06.354', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (289, 150, NULL, 'You can modify, delete or forward the calls using the corresponding buttons.', 0, '2003-12-10 13:44:06.374', 0, '2003-12-10 13:44:06.374', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (290, 151, NULL, 'The details of the new call can be saved using the save button.', 0, '2003-12-10 13:44:06.391', 0, '2003-12-10 13:44:06.391', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (291, 151, NULL, 'The call type can be selected from the dropdown box.', 0, '2003-12-10 13:44:06.395', 0, '2003-12-10 13:44:06.395', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (292, 152, NULL, 'You can modify, delete or forward the calls using the corresponding buttons.', 0, '2003-12-10 13:44:06.404', 0, '2003-12-10 13:44:06.404', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (293, 153, NULL, 'You can select the list of the recipients to whom you want to forward the particular call to by using the Add Recipients link.', 0, '2003-12-10 13:44:06.413', 0, '2003-12-10 13:44:06.413', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (294, 154, NULL, 'You can also view, modify and delete the opportunity associated with the contact.', 0, '2003-12-10 13:44:06.422', 0, '2003-12-10 13:44:06.422', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (295, 154, NULL, 'When the description of the opportunity is clicked, it will give you more details about the opportunity and the components present in it.', 0, '2003-12-10 13:44:06.426', 0, '2003-12-10 13:44:06.426', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (296, 154, NULL, 'You can add an opportunity.', 0, '2003-12-10 13:44:06.43', 0, '2003-12-10 13:44:06.43', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (297, 154, NULL, 'Three types of opportunities are present which can be selected from the drop down list.', 0, '2003-12-10 13:44:06.433', 0, '2003-12-10 13:44:06.433', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (298, 155, NULL, 'You can rename or delete the opportunity itself using the buttons below', 0, '2003-12-10 13:44:06.442', 0, '2003-12-10 13:44:06.442', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (299, 155, NULL, 'You can modify, view and delete the details of any particular component by clicking the select button in the action column. ', 0, '2003-12-10 13:44:06.446', 0, '2003-12-10 13:44:06.446', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (300, 155, NULL, 'You can add a new associated with the account component. It also displays the status, amount and the date when the component will be closed. ', 0, '2003-12-10 13:44:06.45', 0, '2003-12-10 13:44:06.45', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (301, 156, NULL, 'Lets you modify or delete the ticket information', 0, '2003-12-10 13:44:06.458', 0, '2003-12-10 13:44:06.458', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (302, 156, NULL, 'You can also view the tasks, documents related to a ticket along with the history of that document by clicking on the corresponding links.', 0, '2003-12-10 13:44:06.462', 0, '2003-12-10 13:44:06.462', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (303, 157, NULL, 'You can also have tasks, documents related to a ticket along with the history of that document.', 0, '2003-12-10 13:44:06.471', 0, '2003-12-10 13:44:06.471', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (304, 157, NULL, 'Lets you modify / update the ticket information', 0, '2003-12-10 13:44:06.475', 0, '2003-12-10 13:44:06.475', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (305, 158, NULL, 'The details of the task can be viewed or modified by clicking on the select button under the Action column ', 0, '2003-12-10 13:44:06.514', 0, '2003-12-10 13:44:06.514', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (306, 158, NULL, 'You can update the task by clicking on the description of the task', 0, '2003-12-10 13:44:06.518', 0, '2003-12-10 13:44:06.518', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (307, 158, NULL, 'You can add a task which is associated with the existing ticket', 0, '2003-12-10 13:44:06.522', 0, '2003-12-10 13:44:06.522', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (308, 159, NULL, 'The document can be uploaded using the browse button.', 0, '2003-12-10 13:44:06.549', 0, '2003-12-10 13:44:06.549', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (309, 161, NULL, 'You can download all the different versions of the documents using the "Download" link in the Action column', 0, '2003-12-10 13:44:06.567', 0, '2003-12-10 13:44:06.567', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (310, 162, NULL, 'The subject and the filename of the document can be modified', 0, '2003-12-10 13:44:06.576', 0, '2003-12-10 13:44:06.576', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (311, 163, NULL, 'The subject and the file name can be changed. The version number is updated when a updated document is uploaded', 0, '2003-12-10 13:44:06.585', 0, '2003-12-10 13:44:06.585', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (312, 164, NULL, 'The exported data can be viewed as a .csv file or in the html format. The exported data can also be deleted when the select button in the action field is clicked. ', 0, '2003-12-10 13:44:06.594', 0, '2003-12-10 13:44:06.594', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (313, 164, NULL, 'You can also choose to display the list of all the exported data in the system or the exported data created by you. ', 0, '2003-12-10 13:44:06.597', 0, '2003-12-10 13:44:06.597', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (314, 164, NULL, 'New export data can be generated using the "Generate new export" link ', 0, '2003-12-10 13:44:06.601', 0, '2003-12-10 13:44:06.601', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (315, 165, NULL, 'Revenue details along with the option to modify and delete the revenue', 0, '2003-12-10 13:44:06.61', 0, '2003-12-10 13:44:06.61', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (316, 167, NULL, 'You can add / update an opportunity here and assign it to an employee. The opportunity can be associated with an account or a contact. Each opportunity created requires the estimate or the probability of closing the deal, the duration and the best estimate of the person following up the lead.', 0, '2003-12-10 13:44:06.624', 0, '2003-12-10 13:44:06.624', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (317, 168, NULL, 'You can add / update an opportunity here and assign it to an employee. The opportunity can be associated with an account or a contact. Each opportunity created requires the estimate or the probability of closing the deal, the duration and the best estimate of the person following up the lead. ', 0, '2003-12-10 13:44:06.68', 0, '2003-12-10 13:44:06.68', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (318, 169, NULL, 'An opportunity can be renamed or deleted using the buttons present at the bottom of the page', 0, '2003-12-10 13:44:06.691', 0, '2003-12-10 13:44:06.691', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (319, 169, NULL, 'Clicking on the select button lets you view, modify or delete the details about the component ', 0, '2003-12-10 13:44:06.694', 0, '2003-12-10 13:44:06.694', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (320, 169, NULL, 'Clicking on the name of the component would show the details about the component ', 0, '2003-12-10 13:44:06.698', 0, '2003-12-10 13:44:06.698', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (321, 169, NULL, 'Add a new component which is associated with the account.', 0, '2003-12-10 13:44:06.702', 0, '2003-12-10 13:44:06.702', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (322, 170, NULL, 'The description of the opportunity can be changed using the update button.', 0, '2003-12-10 13:44:06.725', 0, '2003-12-10 13:44:06.725', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (323, 171, NULL, 'You can modify and delete the opportunity created using the modify and the delete buttons ', 0, '2003-12-10 13:44:06.734', 0, '2003-12-10 13:44:06.734', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (324, 172, NULL, 'The component type can be selected using the select  link', 0, '2003-12-10 13:44:06.747', 0, '2003-12-10 13:44:06.747', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (325, 172, NULL, 'You can assign the component to any of the employee present using the dropdown list present. ', 0, '2003-12-10 13:44:06.751', 0, '2003-12-10 13:44:06.751', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (326, 173, NULL, 'The component type can be selected using the select  link', 0, '2003-12-10 13:44:06.759', 0, '2003-12-10 13:44:06.759', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (327, 173, NULL, 'You can assign the component to any of the employee present using the dropdown list present. ', 0, '2003-12-10 13:44:06.764', 0, '2003-12-10 13:44:06.764', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (328, 173, NULL, 'Clicking the update button can save the changes made to the component', 0, '2003-12-10 13:44:06.768', 0, '2003-12-10 13:44:06.768', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (329, 174, NULL, 'You can modify and delete the opportunity created using the modify and the delete buttons ', 0, '2003-12-10 13:44:06.789', 0, '2003-12-10 13:44:06.789', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (330, 175, NULL, 'Lets you modify or delete the ticket information ', 0, '2003-12-10 13:44:06.804', 0, '2003-12-10 13:44:06.804', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (331, 175, NULL, 'You can also have tasks, documents related to a ticket along with the history of that document.', 0, '2003-12-10 13:44:06.808', 0, '2003-12-10 13:44:06.808', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (332, 176, NULL, 'For each new ticket you can select the organization, the contact and also the issue for which the ticket is being created. The assignment and the resolution of the ticket can also be mentioned', 0, '2003-12-10 13:44:06.937', 0, '2003-12-10 13:44:06.937', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (333, 177, NULL, 'The search can be done based on different parameters like the ticket number, account associated, priority, employee whom the ticket is assigned etc.', 0, '2003-12-10 13:44:06.96', 0, '2003-12-10 13:44:06.96', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (334, 178, NULL, 'Clicking on the subject of the exported data shows you the details of the ticket like the ticket ID, the organization and its issue (why the particular ticket was generated)', 0, '2003-12-10 13:44:06.994', 0, '2003-12-10 13:44:06.994', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (335, 178, NULL, 'Clicking on the select button under the action column lets you view the data, download the data in .CSV format or delete the data.', 0, '2003-12-10 13:44:06.998', 0, '2003-12-10 13:44:06.998', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (336, 178, NULL, 'You can view the exported date generated, by you or all the employees. This view can be filtered using the dropdown list', 0, '2003-12-10 13:44:07.002', 0, '2003-12-10 13:44:07.002', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (337, 178, NULL, 'You can generate a new exported data by clicking the link Generate new export', 0, '2003-12-10 13:44:07.006', 0, '2003-12-10 13:44:07.006', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (338, 179, NULL, 'You can save the details of the modified ticket by clicking the Update button.', 0, '2003-12-10 13:44:07.032', 0, '2003-12-10 13:44:07.032', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (339, 180, NULL, 'You can save the details of the modified ticket by clicking the Update button.', 0, '2003-12-10 13:44:07.052', 0, '2003-12-10 13:44:07.052', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (340, 181, NULL, 'The details of the task can be viewed or modified by clicking on the select button under the Action column ', 0, '2003-12-10 13:44:07.073', 0, '2003-12-10 13:44:07.073', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (341, 181, NULL, 'You can update the task by clicking on the description of the task ', 0, '2003-12-10 13:44:07.076', 0, '2003-12-10 13:44:07.076', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (342, 181, NULL, 'You can add a task which is associated with the existing ticket ', 0, '2003-12-10 13:44:07.08', 0, '2003-12-10 13:44:07.08', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (343, 182, NULL, 'The details of the documents can be viewed or modified by clicking on the select button under the Action column ', 0, '2003-12-10 13:44:07.118', 0, '2003-12-10 13:44:07.118', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (344, 182, NULL, 'You can view the details, modify, download or delete the documents associated with the ticket ', 0, '2003-12-10 13:44:07.121', 0, '2003-12-10 13:44:07.121', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (345, 182, NULL, 'A new document can be added which is associated with the ticket ', 0, '2003-12-10 13:44:07.125', 0, '2003-12-10 13:44:07.125', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (346, 182, NULL, 'The document versions can be updated by using the "add version" link ', 0, '2003-12-10 13:44:07.129', 0, '2003-12-10 13:44:07.129', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (347, 183, NULL, 'A new record is added into the folder using the link Add a record to this folder. Multiple records can be added to this folder, if the custom folder has the necessary settings.', 0, '2003-12-10 13:44:07.153', 0, '2003-12-10 13:44:07.153', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (348, 183, NULL, 'you can select the custom folder using the drop down list. ', 0, '2003-12-10 13:44:07.156', 0, '2003-12-10 13:44:07.156', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (349, 184, NULL, 'The details are saved by clicking the save button.', 0, '2003-12-10 13:44:07.165', 0, '2003-12-10 13:44:07.165', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (350, 185, NULL, 'This history of the ticket, i.e. right from when the ticket was created to the time when the ticket was closed, the entire history log of that ticket is maintained', 0, '2003-12-10 13:44:07.174', 0, '2003-12-10 13:44:07.174', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (351, 193, NULL, 'The changes can be saved using the Update button.', 0, '2003-12-10 13:44:07.255', 0, '2003-12-10 13:44:07.255', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (352, 194, NULL, 'You can modify the folder information along with the record details by clicking on the Modify button.', 0, '2003-12-10 13:44:07.265', 0, '2003-12-10 13:44:07.265', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (353, 197, NULL, 'The document can be uploaded using the browse button.', 0, '2003-12-10 13:44:07.301', 0, '2003-12-10 13:44:07.301', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (354, 198, NULL, 'You can download all the versions of the documents', 0, '2003-12-10 13:44:07.31', 0, '2003-12-10 13:44:07.31', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (355, 199, NULL, 'You can also have tasks, documents related to a ticket along with the history of that document. ', 0, '2003-12-10 13:44:07.319', 0, '2003-12-10 13:44:07.319', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (356, 199, NULL, 'Lets you modify / update the ticket information ', 0, '2003-12-10 13:44:07.322', 0, '2003-12-10 13:44:07.322', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (357, 200, NULL, 'The new version file can be uploaded using the browse button.', 0, '2003-12-10 13:44:07.331', 0, '2003-12-10 13:44:07.331', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (358, 201, NULL, 'You can delete a record by clicking on "Del" next to the record', 0, '2003-12-10 13:44:07.34', 0, '2003-12-10 13:44:07.34', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (359, 201, NULL, 'You can add a record by clicking on "Add Ticket"', 0, '2003-12-10 13:44:07.344', 0, '2003-12-10 13:44:07.344', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (360, 201, NULL, 'You can view more records in a particular section by clicking "Show More"', 0, '2003-12-10 13:44:07.348', 0, '2003-12-10 13:44:07.348', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (361, 201, NULL, 'You can view more details by clicking on the record', 0, '2003-12-10 13:44:07.351', 0, '2003-12-10 13:44:07.351', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (362, 201, NULL, 'You can update a record by clicking on "Edit" next to the record', 0, '2003-12-10 13:44:07.355', 0, '2003-12-10 13:44:07.355', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (363, 202, NULL, 'A new detailed employee record can be added ', 0, '2003-12-10 13:44:07.403', 0, '2003-12-10 13:44:07.403', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (364, 202, NULL, 'The details of each of the existing employee can be viewed, modified or deleted. This can  be done through the  select button present in the action column', 0, '2003-12-10 13:44:07.407', 0, '2003-12-10 13:44:07.407', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (365, 203, NULL, 'You can modify or delete the employee details using the modify or delete buttons', 0, '2003-12-10 13:44:07.481', 0, '2003-12-10 13:44:07.481', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (366, 204, NULL, 'The Save  button saves the details of the employee entered. ', 0, '2003-12-10 13:44:07.49', 0, '2003-12-10 13:44:07.49', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (367, 204, NULL, 'The Save &New button lets you to save the details of one employee and lets you enter another employee details.', 0, '2003-12-10 13:44:07.494', 0, '2003-12-10 13:44:07.494', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (368, 205, NULL, 'Clicking on the update button saves the modified details of the employee.', 0, '2003-12-10 13:44:07.502', 0, '2003-12-10 13:44:07.502', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (369, 206, NULL, 'The employee record can be modified or deleted from the system completely.', 0, '2003-12-10 13:44:07.511', 0, '2003-12-10 13:44:07.511', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (370, 207, NULL, 'You can cancel the reports that are scheduled to be processed by server by the clicking the select button', 0, '2003-12-10 13:44:07.526', 0, '2003-12-10 13:44:07.526', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (371, 207, NULL, 'The generated reports can be deleted or viewed/downloaded in .pdf format by clicking the select button under the action column ', 0, '2003-12-10 13:44:07.529', 0, '2003-12-10 13:44:07.529', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (372, 207, NULL, 'Add a new report', 0, '2003-12-10 13:44:07.584', 0, '2003-12-10 13:44:07.584', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (373, 208, NULL, 'There are four different modules and you can click on the module where you want to generate the report', 0, '2003-12-10 13:44:07.595', 0, '2003-12-10 13:44:07.595', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (374, 211, NULL, 'You can use the "generate report" button to run the report', 0, '2003-12-10 13:44:07.617', 0, '2003-12-10 13:44:07.617', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (375, 211, NULL, 'If the parameters exist, you can specify the name of the criteria for future reference and click the check box present at the bottom of the page', 0, '2003-12-10 13:44:07.621', 0, '2003-12-10 13:44:07.621', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (376, 212, NULL, 'You can run the report by clicking on the title of the report', 0, '2003-12-10 13:44:07.63', 0, '2003-12-10 13:44:07.63', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (377, 213, NULL, 'If the criteria is present select the criteria then continue to enter the parameters to run the report', 0, '2003-12-10 13:44:07.639', 0, '2003-12-10 13:44:07.639', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (378, 214, NULL, 'You can view the queue either by using the link in the text or using the view queue button', 0, '2003-12-10 13:44:07.647', 0, '2003-12-10 13:44:07.647', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (379, 215, NULL, 'You can cancel the report that is scheduled to be processed by server by clicking the select button', 0, '2003-12-10 13:44:07.656', 0, '2003-12-10 13:44:07.656', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (380, 215, NULL, 'You can view the reports generated, download them or delete them by clicking on the select button under the action column', 0, '2003-12-10 13:44:07.664', 0, '2003-12-10 13:44:07.664', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (381, 215, NULL, 'A new report can be generated by clicking on the link "Add a Report"', 0, '2003-12-10 13:44:07.668', 0, '2003-12-10 13:44:07.668', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (382, 216, NULL, 'The alphabetical slide rule allows users to be listed based on their last name of the alphabet that is clicked.', 0, '2003-12-10 13:44:07.684', 0, '2003-12-10 13:44:07.684', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (383, 216, NULL, 'The columns "Name", ''Username" and "Role" can be clicked to display the users in the ascending or descending order of the chosen criteria.', 0, '2003-12-10 13:44:07.687', 0, '2003-12-10 13:44:07.687', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (384, 216, NULL, 'The "Add New User" link opens a window that allows the administrator to add new users.', 0, '2003-12-10 13:44:07.742', 0, '2003-12-10 13:44:07.742', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (385, 216, NULL, 'The "select" buttons in the "Action" column alongside the name of each user opens a pop up menu that provides the administrator with options to view more information, or modify user information or disable (or inactivate) the user.', 0, '2003-12-10 13:44:07.745', 0, '2003-12-10 13:44:07.745', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (386, 216, NULL, 'The list is displayed by default with 10 names per page, additional items in the list may be viewed by clicking on the "Previous" and "Next" navigation links at the bottom of the table or by changing the number of items to be displayed in a page.', 0, '2003-12-10 13:44:07.749', 0, '2003-12-10 13:44:07.749', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (387, 216, NULL, 'The users of the CRM system are listed in alphabetical order of their name. Their user name, role and the reporting person are also listed to provide a quick overview of information for each user.', 0, '2003-12-10 13:44:07.754', 0, '2003-12-10 13:44:07.754', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (388, 216, NULL, 'The drop list provides a filter to either view only the active or only the inactive users. Inactive users are those who do not have the privilege to use the system currently either because their user name''s have been disabled or they have expired. These users may be activated (enabled) at a later time.', 0, '2003-12-10 13:44:07.757', 0, '2003-12-10 13:44:07.757', NULL, NULL, true, 7);
INSERT INTO help_features VALUES (389, 217, NULL, 'The ''Reports To" field allows the administrator to setup a user hierarchy. The drop list displays all the users of the system and allows one to be chosen.', 0, '2003-12-10 13:44:07.785', 0, '2003-12-10 13:44:07.785', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (390, 217, NULL, 'The "Role" drop list allows a user to be associated with a role. This association determines the privileges the user may have when he accesses the system.', 0, '2003-12-10 13:44:07.789', 0, '2003-12-10 13:44:07.789', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (391, 217, NULL, 'The "Password" fields allows the administrator to setup a password for the user. The password is used along with the Username to login to the system. Since the password is stored in encrypted form and cannot be interpreted, the administrator is asked to confirm the users password. The user may subsequently change his password according to personal preferences.', 0, '2003-12-10 13:44:07.792', 0, '2003-12-10 13:44:07.792', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (392, 217, NULL, 'The Username is the phrase that would be used by the user to login to the system. It should be unique.', 0, '2003-12-10 13:44:07.797', 0, '2003-12-10 13:44:07.797', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (393, 217, NULL, 'An "Expire Date" may be set for each user after which the user is disabled. If this field is left blank the user is active indefinitely. This date can either be typed in the mm/dd/yyyy format or chosen from a calendar that can be accessed from the icon at the right of the field.', 0, '2003-12-10 13:44:07.801', 0, '2003-12-10 13:44:07.801', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (394, 217, NULL, 'The contact field allows the administrator to associate contact information with the user. The administrator may either create new contact information or choose one from the existing list of contacts. This information provides the administrator with the users e-mail, telephone and (or) fax number, postal address and any other information that may help the administrator or the system manager to contact the user.
', 0, '2003-12-10 13:44:07.805', 0, '2003-12-10 13:44:07.805', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (395, 218, NULL, 'The "Cancel" button allows current and uncommitted changes to be undone.', 0, '2003-12-10 13:44:07.833', 0, '2003-12-10 13:44:07.833', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (396, 218, NULL, 'When the "Generate new password" field is checked, the system constructs a password for the user and uses the contact information to mail the new password.', 0, '2003-12-10 13:44:07.836', 0, '2003-12-10 13:44:07.836', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (397, 218, NULL, 'The "Disable" button provides a quick link to the administrator to disable the user.', 0, '2003-12-10 13:44:07.84', 0, '2003-12-10 13:44:07.84', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (398, 218, NULL, 'The "Username", "Role", "Reports To" and password of the user are editable. For more information about each of these fields see help on "Add user".', 0, '2003-12-10 13:44:07.844', 0, '2003-12-10 13:44:07.844', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (399, 219, NULL, 'The list is displayed by default with 10 items per page, additional items in the login history may be viewed by clicking on the "Previous" and "Next" navigation links at the bottom of the table or by changing the number of items to be displayed in a page.', 0, '2003-12-10 13:44:07.86', 0, '2003-12-10 13:44:07.86', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (400, 219, NULL, 'The login history of the user displays the IP address of the computer from which the user logged in, and the date/time when the user logged in.', 0, '2003-12-10 13:44:07.864', 0, '2003-12-10 13:44:07.864', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (401, 220, NULL, 'Clicking on the select button under the action column would let you to view the details about the viewpoint also modify them.', 0, '2003-12-10 13:44:07.873', 0, '2003-12-10 13:44:07.873', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (402, 220, NULL, 'You can click on the contact under the viewpoint column to know more details about that viewpoint and its permissions.', 0, '2003-12-10 13:44:07.876', 0, '2003-12-10 13:44:07.876', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (403, 220, NULL, 'You can Add New Viewpoint using the link Add New Viewpoint.', 0, '2003-12-10 13:44:07.88', 0, '2003-12-10 13:44:07.88', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (404, 221, NULL, 'You can add a new viewpoint by any employee by clicking the add button.', 0, '2003-12-10 13:44:07.889', 0, '2003-12-10 13:44:07.889', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (405, 221, NULL, 'The permissions for the different modules can be given by checking the Access checkbox.', 0, '2003-12-10 13:44:07.893', 0, '2003-12-10 13:44:07.893', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (406, 221, NULL, 'The contact can be selected and removed using the links change contact and clear contact.', 0, '2003-12-10 13:44:07.896', 0, '2003-12-10 13:44:07.896', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (407, 222, NULL, 'The details can be updated using the update button', 0, '2003-12-10 13:44:07.905', 0, '2003-12-10 13:44:07.905', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (408, 222, NULL, 'You can also set the permissions to access different modules by checking the check box under the Access column', 0, '2003-12-10 13:44:07.909', 0, '2003-12-10 13:44:07.909', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (409, 222, NULL, 'You can enable the viewpoint by checking the Enabled checkbox.', 0, '2003-12-10 13:44:07.913', 0, '2003-12-10 13:44:07.913', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (410, 223, NULL, 'You can also click on the select button under the action column to view or modify the details of roles', 0, '2003-12-10 13:44:07.922', 0, '2003-12-10 13:44:07.922', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (411, 223, NULL, 'Clicking on the role name would give you details about the role and his permissions in the system. ', 0, '2003-12-10 13:44:07.925', 0, '2003-12-10 13:44:07.925', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (412, 223, NULL, 'You can add a new role into the system.', 0, '2003-12-10 13:44:07.93', 0, '2003-12-10 13:44:07.93', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (413, 224, NULL, 'Clicking the update button can do the update of the role', 0, '2003-12-10 13:44:07.961', 0, '2003-12-10 13:44:07.961', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (414, 225, NULL, 'The update of the role can be done by clicking the update button ', 0, '2003-12-10 13:44:07.986', 0, '2003-12-10 13:44:07.986', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (415, 226, NULL, 'Clicking on Tickets configures categories, Lookup Lists, Object Events, and Scheduled Events', 0, '2003-12-10 13:44:08.057', 0, '2003-12-10 13:44:08.057', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (416, 226, NULL, 'Clicking on Pipeline management configures only Lookup Lists', 0, '2003-12-10 13:44:08.061', 0, '2003-12-10 13:44:08.061', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (417, 226, NULL, 'Clicking on Account Management configures custom folders & fields and also Lookup Lists', 0, '2003-12-10 13:44:08.066', 0, '2003-12-10 13:44:08.066', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (418, 226, NULL, 'Clicking on General contacts configures custom folders & fields and also Lookup Lists', 0, '2003-12-10 13:44:08.07', 0, '2003-12-10 13:44:08.07', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (419, 227, NULL, 'Scheduled Events: A timer triggers a customizable workflow process.', 0, '2003-12-10 13:44:08.088', 0, '2003-12-10 13:44:08.088', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (420, 227, NULL, 'Object Events: The Action triggers customizable workflow process when a object is inserted, updated, deleted or selected.', 0, '2003-12-10 13:44:08.092', 0, '2003-12-10 13:44:08.092', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (421, 227, NULL, 'Categories: This lets you create categories for the tickets, which can be of two types active and the other is a draft category.', 0, '2003-12-10 13:44:08.096', 0, '2003-12-10 13:44:08.096', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (422, 227, NULL, 'Lookup Lists: You can view the List''s name and edit it by clicking on the "Look up Lists', 0, '2003-12-10 13:44:08.099', 0, '2003-12-10 13:44:08.099', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (423, 227, NULL, 'Custom Folders and fields: For each of the contacts Custom Folders and fields can be added, which would let you enable or disable the folders. You can do this by clicking on the "Custom Folders & Fields"', 0, '2003-12-10 13:44:08.103', 0, '2003-12-10 13:44:08.103', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (424, 228, NULL, 'You can create a new item type using the add button and add it to the existing list. You can position the item in the list using the up and down buttons, remove it using the remove button and also sort the list. The final changes can be saved using the save changes button.', 0, '2003-12-10 13:44:08.124', 0, '2003-12-10 13:44:08.124', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (425, 229, NULL, 'You can update the existing the folder, set the options for the records and the permissions for the users', 0, '2003-12-10 13:44:08.159', 0, '2003-12-10 13:44:08.159', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (426, 230, NULL, 'You can update the existing the folder, set the options for the records and the permissions for the users', 0, '2003-12-10 13:44:08.168', 0, '2003-12-10 13:44:08.168', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (427, 231, NULL, 'The "Edit" link will let you alter the time for which the users session ends', 0, '2003-12-10 13:44:08.179', 0, '2003-12-10 13:44:08.179', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (428, 232, NULL, 'The time out can be set by selecting the time from the drop down and clicking the update button', 0, '2003-12-10 13:44:08.19', 0, '2003-12-10 13:44:08.19', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (429, 233, NULL, 'The usage can be displayed for the current date or a custom date can be specified. This can be selected from the drop down of the date range', 0, '2003-12-10 13:44:08.199', 0, '2003-12-10 13:44:08.199', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (430, 233, NULL, 'The start date and the end date can be specified if the date range is "custom date range". The update can be done using the update button', 0, '2003-12-10 13:44:08.204', 0, '2003-12-10 13:44:08.204', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (431, 236, NULL, 'You can also enable or disable the custom folders by clicking the yes or no ', 0, '2003-12-10 13:44:08.242', 0, '2003-12-10 13:44:08.242', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (432, 236, NULL, 'Clicking on the custom folder will give details about that folder and also lets you add groups.', 0, '2003-12-10 13:44:08.246', 0, '2003-12-10 13:44:08.246', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (433, 236, NULL, 'You can update an existing folder using the edit button under the action column', 0, '2003-12-10 13:44:08.249', 0, '2003-12-10 13:44:08.249', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (434, 236, NULL, 'You can update an existing folder using the edit button under the action column', 0, '2003-12-10 13:44:08.253', 0, '2003-12-10 13:44:08.253', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (435, 236, NULL, 'Add a folder to the general contacts module.', 0, '2003-12-10 13:44:08.257', 0, '2003-12-10 13:44:08.257', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (436, 237, NULL, 'You can also enable or disable the custom folders by clicking the yes or no ', 0, '2003-12-10 13:44:08.269', 0, '2003-12-10 13:44:08.269', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (437, 237, NULL, 'You can update an existing folder using the edit button under the action column ', 0, '2003-12-10 13:44:08.273', 0, '2003-12-10 13:44:08.273', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (438, 237, NULL, 'Clicking on the custom folder will give details about that folder and also lets you add groups', 0, '2003-12-10 13:44:08.276', 0, '2003-12-10 13:44:08.276', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (439, 237, NULL, 'You can update an existing folder using the edit button under the action column ', 0, '2003-12-10 13:44:08.282', 0, '2003-12-10 13:44:08.282', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (440, 237, NULL, 'Add a folder to the general contacts module. ', 0, '2003-12-10 13:44:08.286', 0, '2003-12-10 13:44:08.286', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (441, 241, NULL, 'You can view the process details by clicking on the select button under the Action column or by clicking on the name of the Triggered Process.', 0, '2003-12-10 13:44:08.328', 0, '2003-12-10 13:44:08.328', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (442, 241, NULL, 'You can view the process details by clicking on the select button under the Action column or by clicking on the name of the Triggered Process.', 0, '2003-12-10 13:44:08.331', 0, '2003-12-10 13:44:08.331', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (443, 242, NULL, 'You can add a group name and save it using the "save" button', 0, '2003-12-10 13:44:08.35', 0, '2003-12-10 13:44:08.35', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (444, 244, NULL, 'You can click the "Edit" in the Action column to update a contact type or delete one ', 0, '2003-12-10 13:44:08.371', 0, '2003-12-10 13:44:08.371', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (445, 244, NULL, 'You can preview all the items present in a List name using the drop down in the preview column ', 0, '2003-12-10 13:44:08.374', 0, '2003-12-10 13:44:08.374', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (446, 247, NULL, 'You can click the "Edit" in the Action column to update a contact type or delete one', 0, '2003-12-10 13:44:08.398', 0, '2003-12-10 13:44:08.398', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (447, 247, NULL, 'You can preview all the items present in a List name using the drop down in the preview column', 0, '2003-12-10 13:44:08.401', 0, '2003-12-10 13:44:08.401', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (448, 248, NULL, 'You can also delete the folder and all the fields using the button Delete this folder and all fields present at the bottom of the page.', 0, '2003-12-10 13:44:08.419', 0, '2003-12-10 13:44:08.419', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (449, 248, NULL, 'The groups can also be moved up or down using the Up and Down.  They can also be edited and deleted using the edit and del links.', 0, '2003-12-10 13:44:08.423', 0, '2003-12-10 13:44:08.423', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (450, 248, NULL, 'The custom field can also be edited and deleted using the corresponding links Edit and Del.', 0, '2003-12-10 13:44:08.427', 0, '2003-12-10 13:44:08.427', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (451, 248, NULL, 'The custom field created can be moved up or down for the display using the corresponding links Up and down.', 0, '2003-12-10 13:44:08.431', 0, '2003-12-10 13:44:08.431', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (452, 248, NULL, 'You can add a custom field for the group using the  Add a custom field link', 0, '2003-12-10 13:44:08.435', 0, '2003-12-10 13:44:08.435', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (453, 248, NULL, 'Add a group to the folder selected', 0, '2003-12-10 13:44:08.439', 0, '2003-12-10 13:44:08.439', NULL, NULL, true, 6);
INSERT INTO help_features VALUES (454, 248, NULL, 'You can select the folder by using the drop down box under the general contacts module', 0, '2003-12-10 13:44:08.443', 0, '2003-12-10 13:44:08.443', NULL, NULL, true, 7);
INSERT INTO help_features VALUES (455, 249, NULL, 'Clicking on the list of categories displayed in level1 shows you its sub levels or sub-directories present level2 and clicking on which in turn would show its subdirectories in level3 and so on.', 0, '2003-12-10 13:44:08.473', 0, '2003-12-10 13:44:08.473', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (456, 249, NULL, 'You can select to display either the Active Categories or the Draft Categories by clicking on the tabs Active Categories and Draft Categories respectively.', 0, '2003-12-10 13:44:08.477', 0, '2003-12-10 13:44:08.477', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (457, 250, NULL, 'The activated list can be brought back / reverted to the active list by clicking the Revert to Active List', 0, '2003-12-10 13:44:08.487', 0, '2003-12-10 13:44:08.487', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (458, 250, NULL, 'You can activate each level by using the Activate now button.', 0, '2003-12-10 13:44:08.491', 0, '2003-12-10 13:44:08.491', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (459, 250, NULL, 'In the draft categories you can edit your category using the edit button present at the bottom of each level.', 0, '2003-12-10 13:44:08.495', 0, '2003-12-10 13:44:08.495', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (460, 250, NULL, 'You can select to display either the Active Categories or the Draft Categories by clicking on the tabs Active Categories and Draft Categories respectively.', 0, '2003-12-10 13:44:08.499', 0, '2003-12-10 13:44:08.499', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (461, 250, NULL, 'Clicking on the list of categories displayed in level1 shows you its sub levels or sub-directories present level2 and clicking on which in turn would show its subdirectories in level3 and so on.', 0, '2003-12-10 13:44:08.505', 0, '2003-12-10 13:44:08.505', NULL, NULL, true, 5);
INSERT INTO help_features VALUES (462, 254, NULL, 'The "Modify" button in the "Details" tab provides a quick link that allows the users information to be modified without having to browse back to the previous window.', 0, '2003-12-10 13:44:08.556', 0, '2003-12-10 13:44:08.556', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (463, 254, NULL, 'The "Employee Link" in the ''Primary Information" table header provides a quick link to view the users contact information.', 0, '2003-12-10 13:44:08.559', 0, '2003-12-10 13:44:08.559', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (464, 254, NULL, 'The "Details" tab displays the information about the user in a non-editable format.', 0, '2003-12-10 13:44:08.564', 0, '2003-12-10 13:44:08.564', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (465, 254, NULL, 'The "Disable" button provides a quick link to disable/inactivate the user.', 0, '2003-12-10 13:44:08.567', 0, '2003-12-10 13:44:08.567', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (466, 259, NULL, 'The user and module  section allows the administrator to manage users, roles, role hierarchy and manage modules.', 0, '2003-12-10 13:44:08.636', 0, '2003-12-10 13:44:08.636', NULL, NULL, true, 1);
INSERT INTO help_features VALUES (467, 259, NULL, 'The global parameters and server configuration module allows the administrator to set session timeout parameter.', 0, '2003-12-10 13:44:08.64', 0, '2003-12-10 13:44:08.64', NULL, NULL, true, 2);
INSERT INTO help_features VALUES (468, 259, NULL, 'The usage section allows the administrator to view the total number of users, memory used, and system usage parameters for various time intervals.', 0, '2003-12-10 13:44:08.644', 0, '2003-12-10 13:44:08.644', NULL, NULL, true, 3);
INSERT INTO help_features VALUES (469, 259, NULL, 'The administration module is divided into distinct categories like managing the  users, module configuration, setting the global parameters, server configuration and for monitoring system usage and resources.', 0, '2003-12-10 13:44:08.649', 0, '2003-12-10 13:44:08.649', NULL, NULL, true, 4);
INSERT INTO help_features VALUES (470, 261, NULL, 'Clicking on the different links of the search results will direct you to the corresponding details in the modules. ', 0, '2003-12-10 13:44:09.191', 0, '2003-12-10 13:44:09.191', NULL, NULL, true, 1);


--
-- Data for TOC entry 650 (OID 33448)
-- Name: help_related_links; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 651 (OID 33474)
-- Name: help_faqs; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 652 (OID 33503)
-- Name: help_business_rules; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO help_business_rules VALUES (1, 1, 'You can view your calendar and calendars of those who work for you', 0, '2003-12-10 13:44:03.288', 0, '2003-12-10 13:44:03.288', NULL, NULL, true);
INSERT INTO help_business_rules VALUES (2, 201, 'Other Tickets in My Department: These are records that are assigned to anyone in your department, are unassigned in your department, and are open', 0, '2003-12-10 13:44:07.376', 0, '2003-12-10 13:44:07.376', NULL, NULL, true);
INSERT INTO help_business_rules VALUES (3, 201, 'Tickets Assigned to Me: These are records that are assigned to you and are open', 0, '2003-12-10 13:44:07.381', 0, '2003-12-10 13:44:07.381', NULL, NULL, true);
INSERT INTO help_business_rules VALUES (4, 201, 'Tickets Created by Me: These are records that have been entered by you and are open', 0, '2003-12-10 13:44:07.385', 0, '2003-12-10 13:44:07.385', NULL, NULL, true);
INSERT INTO help_business_rules VALUES (5, 241, 'BUG: HIGH Priority: page cannot be found. Error shown "The included page caused a problem. " ', 0, '2003-12-10 13:44:08.339', 0, '2003-12-10 13:44:08.339', NULL, NULL, true);


--
-- Data for TOC entry 653 (OID 33532)
-- Name: help_notes; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO help_notes VALUES (1, 1, 'On this QA page, rename NOTES to QA NOTES and move them to the bottom of the QA screen', 0, '2003-12-10 13:44:03.112', 0, '2003-12-10 13:44:03.112', NULL, NULL, true);
INSERT INTO help_notes VALUES (2, 1, 'QA breaks on adding a note and marking it as complete', 0, '2003-12-10 13:44:03.122', 0, '2003-12-10 13:44:03.122', NULL, NULL, true);
INSERT INTO help_notes VALUES (3, 1, 'USABILITY: Opportunity Alert needs to have a link to the opportunity header and/or contact', 0, '2003-12-10 13:44:03.126', 0, '2003-12-10 13:44:03.126', NULL, NULL, true);
INSERT INTO help_notes VALUES (4, 1, 'USABILITY: Should expired users be shown in the hierarchy as a filter(ideally there can be no alerts for expired users)', 0, '2003-12-10 13:44:03.13', 0, '2003-12-10 13:44:03.13', NULL, NULL, true);
INSERT INTO help_notes VALUES (5, 1, 'FAQ: Account alerts show up only for the Accounts owned by you', 0, '2003-12-10 13:44:03.134', 0, '2003-12-10 13:44:03.134', NULL, NULL, true);
INSERT INTO help_notes VALUES (6, 1, 'BUG: Updating a task in the pop-up window says "requested page not found"', 0, '2003-12-10 13:44:03.139', 0, '2003-12-10 13:44:03.139', NULL, NULL, true);
INSERT INTO help_notes VALUES (7, 1, 'IE: On 5.0 a message appears asking to display non-secure items... can that go away?', 0, '2003-12-10 13:44:03.149', 0, '2003-12-10 13:44:03.149', NULL, NULL, true);
INSERT INTO help_notes VALUES (8, 1, 'The line breaks in the edit window do not appear in the intro text details', 0, '2003-12-10 13:44:03.161', 0, '2003-12-10 13:44:03.161', NULL, NULL, true);
INSERT INTO help_notes VALUES (9, 1, 'Contact Details popup: Rename Opportunity shows the modules menu', 0, '2003-12-10 13:44:03.165', 0, '2003-12-10 13:44:03.165', NULL, NULL, true);
INSERT INTO help_notes VALUES (10, 1, 'Contact Details pop up : Add a component under Opportunities gives a "Page Not Found"', 0, '2003-12-10 13:44:03.169', 0, '2003-12-10 13:44:03.169', NULL, NULL, true);
INSERT INTO help_notes VALUES (11, 1, 'Contact Details pop up: Modify call form does not show one of the "Cancel" buttons', 0, '2003-12-10 13:44:03.176', 0, '2003-12-10 13:44:03.176', NULL, NULL, true);
INSERT INTO help_notes VALUES (12, 1, 'Contact Details pop up call forward does not work. UPDATE: Removed forward utility from pop up contact details as the forward screens do not remember the sub menu', 0, '2003-12-10 13:44:03.18', 0, '2003-12-10 13:44:03.18', NULL, NULL, true);
INSERT INTO help_notes VALUES (13, 1, 'Projects needs to go from the filters list', 0, '2003-12-10 13:44:03.184', 0, '2003-12-10 13:44:03.184', NULL, NULL, true);
INSERT INTO help_notes VALUES (14, 1, 'Scheduled Actions filter needs to say "Opportunities" and not "Opportunity"', 0, '2003-12-10 13:44:03.188', 0, '2003-12-10 13:44:03.188', NULL, NULL, true);
INSERT INTO help_notes VALUES (15, 1, 'Update Contact Opportunity form does not show entries for drop down selections(lookups)', 0, '2003-12-10 13:44:03.192', 0, '2003-12-10 13:44:03.192', NULL, NULL, true);
INSERT INTO help_notes VALUES (16, 1, 'Not having access to Tasks raises a "Page Not Found" error instead of a "Permission Error"', 0, '2003-12-10 13:44:03.196', 0, '2003-12-10 13:44:03.196', NULL, NULL, true);
INSERT INTO help_notes VALUES (17, 1, 'Calendar does not take into account permissions, for example if tasks are disabled it still show tasks as a filter', 0, '2003-12-10 13:44:03.20', 0, '2003-12-10 13:44:03.20', NULL, NULL, true);
INSERT INTO help_notes VALUES (18, 1, 'Call Alert  shows the title as "null" for contact calls', 0, '2003-12-10 13:44:03.204', 0, '2003-12-10 13:44:03.204', NULL, NULL, true);
INSERT INTO help_notes VALUES (19, 1, 'The QA page should POP to to when reqested', 0, '2003-12-10 13:44:03.21', 0, '2003-12-10 13:44:03.21', NULL, NULL, true);
INSERT INTO help_notes VALUES (20, 1, 'JAVASCRIPT: edit introduction(QA)->submit pops error - ''dosubmit.value not an object''', 0, '2003-12-10 13:44:03.217', 0, '2003-12-10 13:44:03.217', NULL, NULL, true);
INSERT INTO help_notes VALUES (21, 1, 'DISPLAY: DS21 and the User details overlap at times when you log in', 0, '2003-12-10 13:44:03.221', 0, '2003-12-10 13:44:03.221', NULL, NULL, true);
INSERT INTO help_notes VALUES (22, 1, 'JAVASCRIPT: right click on the links ''Help'' and ''QA'' and click on open in a new window pops and error ''object expected''', 0, '2003-12-10 13:44:03.225', 0, '2003-12-10 13:44:03.225', NULL, NULL, true);
INSERT INTO help_notes VALUES (23, 1, 'BUG: Quick Actions->Add a Ticket-> Add a new Ticket | Organization  [Select] -> Add ''AmeriGroup''-> This pops up an error stating "Expecting '')'' " and another error saying ''Object Expected''.', 0, '2003-12-10 13:44:03.234', 0, '2003-12-10 13:44:03.234', NULL, NULL, true);
INSERT INTO help_notes VALUES (24, 1, 'IE BUG: Quick Actions->Add a Ticket-> Add a new Ticket | Organization [Select] -> Contact [Create Contact] pops up an error stating ''Object Expected''.', 0, '2003-12-10 13:44:03.238', 0, '2003-12-10 13:44:03.238', NULL, NULL, true);
INSERT INTO help_notes VALUES (25, 1, 'IE BUG: Quick Actions->Add a Ticket-> Add a new Ticket | Organization [Select] -> Contact  [Create New Contact] gives an error saying ''Object Expected''.', 0, '2003-12-10 13:44:03.242', 0, '2003-12-10 13:44:03.242', NULL, NULL, true);
INSERT INTO help_notes VALUES (26, 1, ' IE BUG: The cursor does not change to a hand when moved over the tabs ''My home page'',''General contacts'' etc.', 0, '2003-12-10 13:44:03.247', 0, '2003-12-10 13:44:03.247', NULL, NULL, true);
INSERT INTO help_notes VALUES (27, 1, 'DISPLAY:Adding an account, task, call, opputunity on the same date screws up the nice square arrangements of the calender on the left.', 0, '2003-12-10 13:44:03.251', 0, '2003-12-10 13:44:03.251', NULL, NULL, true);
INSERT INTO help_notes VALUES (28, 1, 'BUG:In the Day View just next to Calender, the Accounts category repeats whenever you add a new account, instead of showing all the accounts under one category ''Account''. Also the number of items under a particular category displayed after the category name in ''()'' is not updated.', 0, '2003-12-10 13:44:03.255', 0, '2003-12-10 13:44:03.255', NULL, NULL, true);
INSERT INTO help_notes VALUES (29, 1, 'LINK: Clicking on the ''Assigned Activities'' link in my items menu takes you to the Project Management Tab! ', 0, '2003-12-10 13:44:03.259', 0, '2003-12-10 13:44:03.259', NULL, NULL, true);
INSERT INTO help_notes VALUES (30, 1, 'LINK: The link ''Calls to make'' under My Items menu, takes you to the home page and does not show up anything to add calls or edit the same.', 0, '2003-12-10 13:44:03.263', 0, '2003-12-10 13:44:03.263', NULL, NULL, true);
INSERT INTO help_notes VALUES (31, 1, 'On updating a alert on a next or previous month, the view changes to that month', 0, '2003-12-10 13:44:03.267', 0, '2003-12-10 13:44:03.267', NULL, NULL, true);
INSERT INTO help_notes VALUES (32, 1, 'BUG:Call details are not displayed, though task description is(bpk)', 0, '2003-12-10 13:44:03.271', 0, '2003-12-10 13:44:03.271', NULL, NULL, true);
INSERT INTO help_notes VALUES (33, 1, 'JS Error: Click on the Contact link for a scheduled Call .Go to the folders tab and create a record. When the "select" button is clicked under the Action column a Java script error occurs. It does not show the menu at all. Priority :High', 0, '2003-12-10 13:44:03.275', 0, '2003-12-10 13:44:03.275', NULL, NULL, true);
INSERT INTO help_notes VALUES (34, 1, 'JS Error:Clicking on the QA:module on any page and then clicking the edit link of "Brief Description" gives a  JS error.', 0, '2003-12-10 13:44:03.284', 0, '2003-12-10 13:44:03.284', NULL, NULL, true);
INSERT INTO help_notes VALUES (35, 2, 'BUG: Message that should go to the outbox goes to the inbox too.', 0, '2003-12-10 13:44:03.343', 0, '2003-12-10 13:44:03.343', NULL, NULL, true);
INSERT INTO help_notes VALUES (36, 3, 'No confirmation before deleting message', 0, '2003-12-10 13:44:03.36', 0, '2003-12-10 13:44:03.36', NULL, NULL, true);
INSERT INTO help_notes VALUES (37, 4, 'Send and Cancel button need to be there at the top also', 0, '2003-12-10 13:44:03.376', 0, '2003-12-10 13:44:03.376', NULL, NULL, true);
INSERT INTO help_notes VALUES (38, 4, 'Contact pop up selector needs to have scrollbars on', 0, '2003-12-10 13:44:03.38', 0, '2003-12-10 13:44:03.38', NULL, NULL, true);
INSERT INTO help_notes VALUES (39, 4, 'Cancel button always goes back to the list even if you came to this screen from the details screen', 0, '2003-12-10 13:44:03.384', 0, '2003-12-10 13:44:03.384', NULL, NULL, true);
INSERT INTO help_notes VALUES (40, 4, 'BUG: My Home Page->My Mailbox->New Message->[Add Recipients]->Now in the popup click ''Done'' without selecting any contacts->Now click on Send. It says message sent to the following recipients, but there are no recipients since none were selected from the pop up list.', 0, '2003-12-10 13:44:03.388', 0, '2003-12-10 13:44:03.388', NULL, NULL, true);
INSERT INTO help_notes VALUES (41, 4, 'DISPLAY : Recipient Field in the new message form does not have an indicator to indicate a mandatory field.', 0, '2003-12-10 13:44:03.392', 0, '2003-12-10 13:44:03.392', NULL, NULL, true);
INSERT INTO help_notes VALUES (42, 4, 'BUG: When two recipients are added, the first one from choosing among "all contacts" and the second from "Employees" and later when the first recipient is deleted, the second also gets deleted from the recipient list(bpk)', 0, '2003-12-10 13:44:03.396', 0, '2003-12-10 13:44:03.396', NULL, NULL, true);
INSERT INTO help_notes VALUES (43, 4, 'DISPLAY: "Add Recipients" is a misnomer since it also allows chosen recipients to be deleted(bpk)', 0, '2003-12-10 13:44:03.40', 0, '2003-12-10 13:44:03.40', NULL, NULL, true);
INSERT INTO help_notes VALUES (44, 4, 'FEATURE: Deleting a recipient from the list should be allowed by choosing and then clicking the delete key(bpk)', 0, '2003-12-10 13:44:03.404', 0, '2003-12-10 13:44:03.404', NULL, NULL, true);
INSERT INTO help_notes VALUES (45, 4, 'BUG/USABILITY: Currently the message body is mandatory but the does not have a red star beside it. But, most mail applications do not have subject and message body as mandatory(bpk)', 0, '2003-12-10 13:44:03.408', 0, '2003-12-10 13:44:03.408', NULL, NULL, true);
INSERT INTO help_notes VALUES (46, 4, 'USABILITY: when clicked on [Add Recipients], could the popup window show a list of only contacts who have email addresses specified. It as of now shows contacts with no email id specified and hence when a mail is sent, i get a feedback message saying email not sent since there is no email id specified.', 0, '2003-12-10 13:44:03.416', 0, '2003-12-10 13:44:03.416', NULL, NULL, true);
INSERT INTO help_notes VALUES (47, 5, 'BUG: Cannot reply to an inbox message, error displays with funny illegal address ``', 0, '2003-12-10 13:44:03.444', 0, '2003-12-10 13:44:03.444', NULL, NULL, true);
INSERT INTO help_notes VALUES (48, 6, 'Message Details link  incorrectly takes you to the Mailbox', 0, '2003-12-10 13:44:03.454', 0, '2003-12-10 13:44:03.454', NULL, NULL, true);
INSERT INTO help_notes VALUES (49, 6, 'BUG: If an email is not being sent, just going to inbox, the recipient is not listed in the "message has been sent" table', 0, '2003-12-10 13:44:03.458', 0, '2003-12-10 13:44:03.458', NULL, NULL, true);
INSERT INTO help_notes VALUES (50, 6, 'BUG:do the following -  My Home Page > My Mailbox > New Message. Then click on [Add Recipients]. Now click on DONE without selecting any contacts. Now click on ''Send''. This pops-up and error message  ', 0, '2003-12-10 13:44:03.462', 0, '2003-12-10 13:44:03.462', NULL, NULL, true);
INSERT INTO help_notes VALUES (51, 7, 'USABILITY: Pop Up for Add Recipients should pop to front', 0, '2003-12-10 13:44:03.488', 0, '2003-12-10 13:44:03.488', NULL, NULL, true);
INSERT INTO help_notes VALUES (52, 8, 'Contact Link gives a exception when a ticket task is linked to a contact which does not have a owner', 0, '2003-12-10 13:44:03.53', 0, '2003-12-10 13:44:03.53', NULL, NULL, true);
INSERT INTO help_notes VALUES (53, 8, 'USAGE : when I am adding a new Task, I am able to select the status complete. Is this the intended normal operation??', 0, '2003-12-10 13:44:03.534', 0, '2003-12-10 13:44:03.534', NULL, NULL, true);
INSERT INTO help_notes VALUES (54, 8, 'BUG:view tasks assigned by -> click on one of the tasks -> All the includes (menu, my items, recent items etc) disappear and only task details are shown. (In the same page and not a popup)', 0, '2003-12-10 13:44:03.541', 0, '2003-12-10 13:44:03.541', NULL, NULL, true);
INSERT INTO help_notes VALUES (55, 8, 'BUG: The drop down to select between My Tasks and Assigned Tasks raises a javascript expression when the permission for Add, Edit and Delete are turned off. Browser: IE5.0', 0, '2003-12-10 13:44:03.547', 0, '2003-12-10 13:44:03.547', NULL, NULL, true);
INSERT INTO help_notes VALUES (56, 10, 'After forwarding a task, the screen goes over into My Mailbox for showing the confirmation message', 0, '2003-12-10 13:44:03.628', 0, '2003-12-10 13:44:03.628', NULL, NULL, true);
INSERT INTO help_notes VALUES (57, 12, 'No permissions implemented for Action Lists UPDATE:  If user has only view permissions for action lists, clicking on a action contact should just pop up the details without the other tabs to prevent adding a action item', 0, '2003-12-10 13:44:03.682', 0, '2003-12-10 13:44:03.682', NULL, NULL, true);
INSERT INTO help_notes VALUES (58, 13, 'Add Task against a action contact throws a "Page Not Found"', 0, '2003-12-10 13:44:03.71', 0, '2003-12-10 13:44:03.71', NULL, NULL, true);
INSERT INTO help_notes VALUES (59, 13, '"Send Message" does not send the message even when all the fields are filled in correctly', 0, '2003-12-10 13:44:03.714', 0, '2003-12-10 13:44:03.714', NULL, NULL, true);
INSERT INTO help_notes VALUES (60, 13, 'The history shows up correctly the first time under mozilla but not after that.', 0, '2003-12-10 13:44:03.718', 0, '2003-12-10 13:44:03.718', NULL, NULL, true);
INSERT INTO help_notes VALUES (61, 13, 'Added the image editor feature to "Send Message"', 0, '2003-12-10 13:44:03.723', 0, '2003-12-10 13:44:03.723', NULL, NULL, true);
INSERT INTO help_notes VALUES (62, 13, 'Trying to add a Call gives a Page Not Found', 0, '2003-12-10 13:44:03.73', 0, '2003-12-10 13:44:03.73', NULL, NULL, true);
INSERT INTO help_notes VALUES (63, 13, 'Select menu does not show all icons', 0, '2003-12-10 13:44:03.741', 0, '2003-12-10 13:44:03.741', NULL, NULL, true);
INSERT INTO help_notes VALUES (64, 13, 'BUG: When choosing "save" in the "add opportunity" window, the error "alertDate.value is not an object" pops up', 0, '2003-12-10 13:44:03.745', 0, '2003-12-10 13:44:03.745', NULL, NULL, true);
INSERT INTO help_notes VALUES (65, 13, 'USABILITY: If an action contact only has the company name, then the company name needs to be shown in the Name column', 0, '2003-12-10 13:44:03.748', 0, '2003-12-10 13:44:03.748', NULL, NULL, true);
INSERT INTO help_notes VALUES (66, 13, 'BUG: Looking at an Action List threw an exception - Ticket Not Found', 0, '2003-12-10 13:44:03.752', 0, '2003-12-10 13:44:03.752', NULL, NULL, true);
INSERT INTO help_notes VALUES (67, 15, 'BUG: Modify Action displays only the action description', 0, '2003-12-10 13:44:03.803', 0, '2003-12-10 13:44:03.803', NULL, NULL, true);
INSERT INTO help_notes VALUES (68, 16, 'CRITICAL: When Mathur selected his name from the hierarchy list, the system hangs and then gives a "Page Not Found". Interestingly this does not happen with other users. Similar bug in Pipeline Calls -- > Add a Call HINT: The contact list is the possible problem as Mathur has 25000 against him', 0, '2003-12-10 13:44:03.823', 0, '2003-12-10 13:44:03.823', NULL, NULL, true);
INSERT INTO help_notes VALUES (69, 16, 'BUG: "User to assign to" should be a required field -- there is no checking', 0, '2003-12-10 13:44:03.827', 0, '2003-12-10 13:44:03.827', NULL, NULL, true);
INSERT INTO help_notes VALUES (70, 16, 'With "nothing selected" and UPDATE the message is: An Error Has Occurred The system administrator has been notified.

You do not have the permissions to perform this action. ', 0, '2003-12-10 13:44:03.831', 0, '2003-12-10 13:44:03.831', NULL, NULL, true);
INSERT INTO help_notes VALUES (71, 16, 'Exception when I try to re-assign data from myself when I have nothing to reassign.UPDATE: This bug is already resolved ', 0, '2003-12-10 13:44:03.835', 0, '2003-12-10 13:44:03.835', NULL, NULL, true);
INSERT INTO help_notes VALUES (72, 16, 'FEATURE:The list of possible users to reassign contains certain users with *. An explanatory label for what it means would be useful.', 0, '2003-12-10 13:44:03.838', 0, '2003-12-10 13:44:03.838', NULL, NULL, true);
INSERT INTO help_notes VALUES (73, 16, 'BUG: The "To User" Dropdown does not show the list of all users to whome an item can be assigned to(bpk)', 0, '2003-12-10 13:44:03.842', 0, '2003-12-10 13:44:03.842', NULL, NULL, true);
INSERT INTO help_notes VALUES (74, 18, 'UI: The ''mark to remove'' field could be moved up by reducing some space in the previous text field.', 0, '2003-12-10 13:44:03.878', 0, '2003-12-10 13:44:03.878', NULL, NULL, true);
INSERT INTO help_notes VALUES (75, 20, 'On entering all the passwords correctly and hitting "Update" button a popup window asks about the system you want to update. It seems obvious that the user is trying to update it for the system he/she is logged in UPDATE: Happens only under mozilla, in hitting "Cancel" on the popup window the password gets updated anyhow', 0, '2003-12-10 13:44:03.90', 0, '2003-12-10 13:44:03.90', NULL, NULL, true);
INSERT INTO help_notes VALUES (76, 20, 'BUG: When I select ''change my password''. The form shown, has a value already present for the current password field which is incorrect as it does not match with my current password.', 0, '2003-12-10 13:44:03.904', 0, '2003-12-10 13:44:03.904', NULL, NULL, true);
INSERT INTO help_notes VALUES (77, 21, 'USABILITY: Delete asks "Do to delete this "OBJECT" ...,  user doesen''t know no OBJECTS', 0, '2003-12-10 13:44:03.932', 0, '2003-12-10 13:44:03.932', NULL, NULL, true);
INSERT INTO help_notes VALUES (78, 21, 'Tasks is not taking permissions into account', 0, '2003-12-10 13:44:03.935', 0, '2003-12-10 13:44:03.935', NULL, NULL, true);
INSERT INTO help_notes VALUES (79, 21, 'The clicking of image to mark a task as complete needs to take permissions into account', 0, '2003-12-10 13:44:03.939', 0, '2003-12-10 13:44:03.939', NULL, NULL, true);
INSERT INTO help_notes VALUES (80, 21, 'BUG: Under Quick Actions, adding a new task and saving does not update or refresh the new task immediately. For example, the Tasks(4 incomplete) under My Items section does not show the 5th task that was added.', 0, '2003-12-10 13:44:03.943', 0, '2003-12-10 13:44:03.943', NULL, NULL, true);
INSERT INTO help_notes VALUES (81, 21, 'FEATURE:Might be a good idea to denote the age of the task in days and hours and not just days.', 0, '2003-12-10 13:44:03.946', 0, '2003-12-10 13:44:03.946', NULL, NULL, true);
INSERT INTO help_notes VALUES (82, 21, 'FEATURE: A user if he does not have the permission to delete a task assigned by him to somebody else should not be shown the  delete option in the selet menu when he is viewing all the tasks assigned by him to others.', 0, '2003-12-10 13:44:03.951', 0, '2003-12-10 13:44:03.951', NULL, NULL, true);
INSERT INTO help_notes VALUES (83, 21, 'BUG: allows the user to add a blank task.  could check to see if the string has blanks and alert the user to enter a valid task name', 0, '2003-12-10 13:44:03.954', 0, '2003-12-10 13:44:03.954', NULL, NULL, true);
INSERT INTO help_notes VALUES (84, 25, 'Although everything is filled up correctly, the form cannot be saved. Possible reason could be the accessType added recently.', 0, '2003-12-10 13:44:03.99', 0, '2003-12-10 13:44:03.99', NULL, NULL, true);
INSERT INTO help_notes VALUES (85, 27, 'SQL:My Home Page > My Action Lists > List Details > Modify Action List > This shows a list of contacts included for this Action. Now deselect all the contacts and click on [DONE]. This gives an error:java.sql.SQLException: ERROR:  parser: parse error at or near ")" at character 134 ', 0, '2003-12-10 13:44:04.006', 0, '2003-12-10 13:44:04.006', NULL, NULL, true);
INSERT INTO help_notes VALUES (86, 28, 'BUG/USABILITY: Existing contacts are are not shown when a user chooses to add contacts(bpk)', 0, '2003-12-10 13:44:04.022', 0, '2003-12-10 13:44:04.022', NULL, NULL, true);
INSERT INTO help_notes VALUES (87, 33, 'Pop up contact selectors My and All Contacts filters seem slow', 0, '2003-12-10 13:44:04.068', 0, '2003-12-10 13:44:04.068', NULL, NULL, true);
INSERT INTO help_notes VALUES (88, 33, 'BUG: My Home Page->My Tasks->Add a Task->[Change Contact]->Add a contact without a name->[Save]->Now click on the task and the Link Contact field shows ''none''', 0, '2003-12-10 13:44:04.072', 0, '2003-12-10 13:44:04.072', NULL, NULL, true);
INSERT INTO help_notes VALUES (89, 33, 'BUG: My Home Page -> My Tasks -> Update a Task(with already a Link contact eg:Adaskus, Matt)->[Clear Contact]->Click Update->Click on the task to view and the contact which was cleared earlier still remains.', 0, '2003-12-10 13:44:04.076', 0, '2003-12-10 13:44:04.076', NULL, NULL, true);
INSERT INTO help_notes VALUES (90, 33, 'BUG:My Home Page->My Tasks->Select ''Tasks assigned by me'' from the ''My Tasks'' Drop down menu. Now click on the task and an error is reported stating - The system administrator has been notified. You do not have permissions to perform this action. ', 0, '2003-12-10 13:44:04.079', 0, '2003-12-10 13:44:04.079', NULL, NULL, true);
INSERT INTO help_notes VALUES (91, 35, 'FEATURE: a "Starts With" option would be really a useful addition.  One use would be to split work assignments on a Large List.  Possible implementation "pattern" = anywhere,  "%patter" = only at the front.', 0, '2003-12-10 13:44:04.175', 0, '2003-12-10 13:44:04.175', NULL, NULL, true);
INSERT INTO help_notes VALUES (92, 35, 'DONE: TM', 0, '2003-12-10 13:44:04.178', 0, '2003-12-10 13:44:04.178', NULL, NULL, true);
INSERT INTO help_notes VALUES (93, 35, 'Seems like the From filter is not working as intended', 0, '2003-12-10 13:44:04.182', 0, '2003-12-10 13:44:04.182', NULL, NULL, true);
INSERT INTO help_notes VALUES (94, 36, 'DONE: TM', 0, '2003-12-10 13:44:04.202', 0, '2003-12-10 13:44:04.202', NULL, NULL, true);
INSERT INTO help_notes VALUES (95, 37, 'DONE: TM', 0, '2003-12-10 13:44:04.229', 0, '2003-12-10 13:44:04.229', NULL, NULL, true);
INSERT INTO help_notes VALUES (96, 37, 'CRITICAL: If you have a lot of contacts then you get a "Page Not Found" error which stems from a java.lang.outOfMemory error caused by the ContactList ', 0, '2003-12-10 13:44:04.232', 0, '2003-12-10 13:44:04.232', NULL, NULL, true);
INSERT INTO help_notes VALUES (97, 37, 'JAVASCRIPT: click on the UP/DOWN buttons without selecting an item ', 0, '2003-12-10 13:44:04.236', 0, '2003-12-10 13:44:04.236', NULL, NULL, true);
INSERT INTO help_notes VALUES (98, 40, 'It is possible to add nonsense names to contacts. For instance, I added one with a last name of "-".', 0, '2003-12-10 13:44:04.264', 0, '2003-12-10 13:44:04.264', NULL, NULL, true);
INSERT INTO help_notes VALUES (99, 40, 'We don''t check for validity of email addresses. I added "darth#vader.com".', 0, '2003-12-10 13:44:04.267', 0, '2003-12-10 13:44:04.267', NULL, NULL, true);
INSERT INTO help_notes VALUES (100, 40, 'BUG: Access Type drop down does not change on changing the Contact Category but does change once focus is taken off from it. Note: I am using IE5.0', 0, '2003-12-10 13:44:04.271', 0, '2003-12-10 13:44:04.271', NULL, NULL, true);
INSERT INTO help_notes VALUES (101, 41, 'Deleting a Call fromt the list view causes the navigation menu to disappear', 0, '2003-12-10 13:44:04.291', 0, '2003-12-10 13:44:04.291', NULL, NULL, true);
INSERT INTO help_notes VALUES (102, 43, 'Needs "Send" and "Cancel"  buttons both at the top and bottom', 0, '2003-12-10 13:44:04.31', 0, '2003-12-10 13:44:04.31', NULL, NULL, true);
INSERT INTO help_notes VALUES (103, 45, 'USABILITY: Errors MUST provide a LOGOUT or a LINK back.  Otherwise the user must hit the BACK BUTTON.   I did and entered three duplicat opertunities.', 0, '2003-12-10 13:44:04.343', 0, '2003-12-10 13:44:04.343', NULL, NULL, true);
INSERT INTO help_notes VALUES (104, 45, 'Component modification from the  opportunity details screen does shows 2 cancel buttons', 0, '2003-12-10 13:44:04.346', 0, '2003-12-10 13:44:04.346', NULL, NULL, true);
INSERT INTO help_notes VALUES (105, 46, 'ERROR: On the Calls Details page, always get a "Page Not Found" error when clicking the Modify button.', 0, '2003-12-10 13:44:04.36', 0, '2003-12-10 13:44:04.36', NULL, NULL, true);
INSERT INTO help_notes VALUES (106, 46, 'USABILITY: If Delete and modify buttons are disabled then there is not space between the forward button and the details table', 0, '2003-12-10 13:44:04.364', 0, '2003-12-10 13:44:04.364', NULL, NULL, true);
INSERT INTO help_notes VALUES (107, 48, 'BUG: The "Add a Component" link is BROKEN on the Opportunity Details page', 0, '2003-12-10 13:44:04.40', 0, '2003-12-10 13:44:04.40', NULL, NULL, true);
INSERT INTO help_notes VALUES (108, 48, 'Unable to modify a component although I have Edit Permissions on General Contacts Opps', 0, '2003-12-10 13:44:04.404', 0, '2003-12-10 13:44:04.404', NULL, NULL, true);
INSERT INTO help_notes VALUES (109, 49, 'When only View Permissions are there, there is a space between the Content and Name of the Opportunity', 0, '2003-12-10 13:44:04.417', 0, '2003-12-10 13:44:04.417', NULL, NULL, true);
INSERT INTO help_notes VALUES (110, 50, 'Adding and modifying a call should show that subject is required', 0, '2003-12-10 13:44:04.431', 0, '2003-12-10 13:44:04.431', NULL, NULL, true);
INSERT INTO help_notes VALUES (111, 53, 'DONE: TM', 0, '2003-12-10 13:44:04.451', 0, '2003-12-10 13:44:04.451', NULL, NULL, true);
INSERT INTO help_notes VALUES (112, 53, 'Sorting on Company did not seem to work as expected for me when reversing the sort. TM', 0, '2003-12-10 13:44:04.454', 0, '2003-12-10 13:44:04.454', NULL, NULL, true);
INSERT INTO help_notes VALUES (113, 53, '"Name" field is picking up the name of the company if there isn''t a contact name.  It shouldn''t', 0, '2003-12-10 13:44:04.458', 0, '2003-12-10 13:44:04.458', NULL, NULL, true);
INSERT INTO help_notes VALUES (114, 54, 'BUG: If you come to this page from the contact details, it does not take you back to the details screen on hitting the Cancel button', 0, '2003-12-10 13:44:04.468', 0, '2003-12-10 13:44:04.468', NULL, NULL, true);
INSERT INTO help_notes VALUES (115, 55, 'DONE: TM', 0, '2003-12-10 13:44:04.476', 0, '2003-12-10 13:44:04.476', NULL, NULL, true);
INSERT INTO help_notes VALUES (116, 55, 'Typo : Fireworks spelt as Firewiorks', 0, '2003-12-10 13:44:04.48', 0, '2003-12-10 13:44:04.48', NULL, NULL, true);
INSERT INTO help_notes VALUES (117, 56, 'BUG: Add a Component link give me "page not found"', 0, '2003-12-10 13:44:04.489', 0, '2003-12-10 13:44:04.489', NULL, NULL, true);
INSERT INTO help_notes VALUES (118, 59, 'It is possible to enter a low estimate that is higher than the high estimate, which should be trapped for.', 0, '2003-12-10 13:44:04.528', 0, '2003-12-10 13:44:04.528', NULL, NULL, true);
INSERT INTO help_notes VALUES (119, 60, 'After deleting a contact, a page not found error displays, url should go to search not list', 0, '2003-12-10 13:44:04.549', 0, '2003-12-10 13:44:04.549', NULL, NULL, true);
INSERT INTO help_notes VALUES (120, 62, 'FEATURE:  Contact "history" shows a chronological history of all activities associated with the contact.  The history shows up in place of where details are today.  And can add another task like you do from "action lists"  today', 0, '2003-12-10 13:44:04.578', 0, '2003-12-10 13:44:04.578', NULL, NULL, true);
INSERT INTO help_notes VALUES (121, 62, 'When I have view only permission, there is still a clone button on this page, which generates an error when I click it.', 0, '2003-12-10 13:44:04.582', 0, '2003-12-10 13:44:04.582', NULL, NULL, true);
INSERT INTO help_notes VALUES (122, 62, 'One time Bug:  When I tried to change a general to an account contact, the first time I got an error message about the ACCESS type.  I clicked again and with no change it WORKED the SECOND TIME.', 0, '2003-12-10 13:44:04.586', 0, '2003-12-10 13:44:04.586', NULL, NULL, true);
INSERT INTO help_notes VALUES (123, 62, 'SQLException : General Contacts->Add Contact->Search for this Contact->view the contact->Delete Contact->Error:requested page could not be found->hit backspace->SQLException Error:Contact record not found ', 0, '2003-12-10 13:44:04.59', 0, '2003-12-10 13:44:04.59', NULL, NULL, true);
INSERT INTO help_notes VALUES (124, 62, 'BUG: get the error and denied page when the contact is clicked although the permissions for the role allows view,add,edit,delete on action lists(bpk)', 0, '2003-12-10 13:44:04.613', 0, '2003-12-10 13:44:04.613', NULL, NULL, true);
INSERT INTO help_notes VALUES (125, 62, 'When a contact is selected and the ''modify'' button pressed, I get an error in the next page after some significant delay. The error is "The included page caused a problem."', 0, '2003-12-10 13:44:04.617', 0, '2003-12-10 13:44:04.617', NULL, NULL, true);
INSERT INTO help_notes VALUES (126, 62, 'USABILITY: If edit and delete permissions are turned off then there is not space between the Clone button and the details', 0, '2003-12-10 13:44:04.62', 0, '2003-12-10 13:44:04.62', NULL, NULL, true);
INSERT INTO help_notes VALUES (127, 63, 'DONE: TM', 0, '2003-12-10 13:44:04.637', 0, '2003-12-10 13:44:04.637', NULL, NULL, true);
INSERT INTO help_notes VALUES (128, 63, 'Filter does not need the Controlled=Hierarchy and Personal messages here', 0, '2003-12-10 13:44:04.64', 0, '2003-12-10 13:44:04.64', NULL, NULL, true);
INSERT INTO help_notes VALUES (129, 63, 'Sorting on any of the columns resets the filter View to My Messages', 0, '2003-12-10 13:44:04.645', 0, '2003-12-10 13:44:04.645', NULL, NULL, true);
INSERT INTO help_notes VALUES (130, 68, 'DONE: TM', 0, '2003-12-10 13:44:04.712', 0, '2003-12-10 13:44:04.712', NULL, NULL, true);
INSERT INTO help_notes VALUES (131, 68, 'FEATURE:  We need FORWARD and BACK button to page thru folder records.', 0, '2003-12-10 13:44:04.715', 0, '2003-12-10 13:44:04.715', NULL, NULL, true);
INSERT INTO help_notes VALUES (132, 68, 'FEATURE: Folder Data export to spreadsheet needed.', 0, '2003-12-10 13:44:04.719', 0, '2003-12-10 13:44:04.719', NULL, NULL, true);
INSERT INTO help_notes VALUES (133, 68, 'FEATURE: Multiple Records/Page needed as folder data output option.', 0, '2003-12-10 13:44:04.722', 0, '2003-12-10 13:44:04.722', NULL, NULL, true);
INSERT INTO help_notes VALUES (134, 69, 'Sub menu needs to have tabs', 0, '2003-12-10 13:44:04.735', 0, '2003-12-10 13:44:04.735', NULL, NULL, true);
INSERT INTO help_notes VALUES (135, 71, 'FEATURE: "Import Contacts" from a csv file is something we have had to do several times,  It needs to be on the General Contacts page.', 0, '2003-12-10 13:44:04.784', 0, '2003-12-10 13:44:04.784', NULL, NULL, true);
INSERT INTO help_notes VALUES (136, 71, 'There are two "Add a Contact" links on the page, one on the secondary tabs, and one as an actual link above the contact dropdown. Only one is needed.', 0, '2003-12-10 13:44:04.787', 0, '2003-12-10 13:44:04.787', NULL, NULL, true);
INSERT INTO help_notes VALUES (137, 71, 'DONE: TM', 0, '2003-12-10 13:44:04.791', 0, '2003-12-10 13:44:04.791', NULL, NULL, true);
INSERT INTO help_notes VALUES (138, 71, 'USABILIITY: The Contacts main page should display all contacts instead of displaying the search page (bpk) ', 0, '2003-12-10 13:44:04.795', 0, '2003-12-10 13:44:04.795', NULL, NULL, true);
INSERT INTO help_notes VALUES (139, 77, 'DONE: TM', 0, '2003-12-10 13:44:04.86', 0, '2003-12-10 13:44:04.86', NULL, NULL, true);
INSERT INTO help_notes VALUES (140, 77, 'It is possible to add "nonsense names" like a last name of "-". Maybe we should do some simple checking?', 0, '2003-12-10 13:44:04.865', 0, '2003-12-10 13:44:04.865', NULL, NULL, true);
INSERT INTO help_notes VALUES (141, 77, 'Inserting an Employee breaks with a referential integrity violation error', 0, '2003-12-10 13:44:04.871', 0, '2003-12-10 13:44:04.871', NULL, NULL, true);
INSERT INTO help_notes VALUES (142, 77, 'Cancel button does not take you back to the details page ', 0, '2003-12-10 13:44:04.875', 0, '2003-12-10 13:44:04.875', NULL, NULL, true);
INSERT INTO help_notes VALUES (143, 77, 'FEATURE: Company should NOT be editable for account contacts because it doesn''t change anyway.', 0, '2003-12-10 13:44:04.878', 0, '2003-12-10 13:44:04.878', NULL, NULL, true);
INSERT INTO help_notes VALUES (144, 77, 'Contact Type pop up does not remember the types selected across multiple pages', 0, '2003-12-10 13:44:04.882', 0, '2003-12-10 13:44:04.882', NULL, NULL, true);
INSERT INTO help_notes VALUES (145, 77, 'Clicking on ''AddContact'' tab highlights the ''SearchResults'' tab ', 0, '2003-12-10 13:44:04.886', 0, '2003-12-10 13:44:04.886', NULL, NULL, true);
INSERT INTO help_notes VALUES (146, 77, 'BUG: click on [Select] for Contact Type(s) and then click DONE and then click on Save. This gives a message in red !', 0, '2003-12-10 13:44:04.89', 0, '2003-12-10 13:44:04.89', NULL, NULL, true);
INSERT INTO help_notes VALUES (147, 77, 'BUG:Clone Account Contact->Access Type=Controlled-Hierarchy->Fill other details->save->"error:form could not be submitted, review messages below". But there are no review messages and the Access Type drop down menu now only has ''public'' as its items. ''', 0, '2003-12-10 13:44:04.893', 0, '2003-12-10 13:44:04.893', NULL, NULL, true);
INSERT INTO help_notes VALUES (148, 78, 'BUG:  Closed opportunities not dropped from dashboard view ... though are dropped in "View Opportunities"', 0, '2003-12-10 13:44:04.97', 0, '2003-12-10 13:44:04.97', NULL, NULL, true);
INSERT INTO help_notes VALUES (149, 78, 'LOGIC: Pipeline / Dashboard:  When looking at Bill Roberts Dashboard, who reports to me, on his opportunity it shows (3) components to an opportunity when only 1 was assigned to him -- other 2 were somebody elses.  Shouldn''t it only show 1', 0, '2003-12-10 13:44:04.974', 0, '2003-12-10 13:44:04.974', NULL, NULL, true);
INSERT INTO help_notes VALUES (150, 78, 'Graphing:  The system will accept a negative commission rate (e.g. -10%) and will graph it correctly.  Should we allow this?  Not sure why not.', 0, '2003-12-10 13:44:04.978', 0, '2003-12-10 13:44:04.978', NULL, NULL, true);
INSERT INTO help_notes VALUES (151, 78, 'CALC ALGORITHM BUG:  Calculated graphs wrong.  If date closed is 1''st of month, it does it correctly across term.  If start is mid-month, some dollars are dropped.  Appears because it demonstrates it by "area under the curve" approach', 0, '2003-12-10 13:44:04.981', 0, '2003-12-10 13:44:04.981', NULL, NULL, true);
INSERT INTO help_notes VALUES (152, 78, 'When using "Up One Level" in Pipeline/Dashboard to navigate back to "Self" the "Up One Level" remains as a option even there are no superior levels.', 0, '2003-12-10 13:44:04.985', 0, '2003-12-10 13:44:04.985', NULL, NULL, true);
INSERT INTO help_notes VALUES (153, 78, 'BUG: Clicking on the ''add'' option produces a page with the following message. "The included page caused a problem."', 0, '2003-12-10 13:44:04.989', 0, '2003-12-10 13:44:04.989', NULL, NULL, true);
INSERT INTO help_notes VALUES (154, 80, 'FEATURE: not sure what the search is based on. Indicating to the user that the 5 input fields are individual choices could be useful. Suggestion...could use a simple ''or'' label.', 0, '2003-12-10 13:44:05.029', 0, '2003-12-10 13:44:05.029', NULL, NULL, true);
INSERT INTO help_notes VALUES (155, 80, 'LOGIC:  When should an opportunity with multiple components be considered closed?', 0, '2003-12-10 13:44:05.033', 0, '2003-12-10 13:44:05.033', NULL, NULL, true);
INSERT INTO help_notes VALUES (156, 80, 'BUG: Need to have an "All Stages" at top of search item', 0, '2003-12-10 13:44:05.037', 0, '2003-12-10 13:44:05.037', NULL, NULL, true);
INSERT INTO help_notes VALUES (157, 82, 'FEATURE:  In next release the "Organization" drop down should be replaced by the ability to pick from a list (like we just changed in Tickets).  Drop down unwieldy when lots of organizations', 0, '2003-12-10 13:44:05.062', 0, '2003-12-10 13:44:05.062', NULL, NULL, true);
INSERT INTO help_notes VALUES (158, 82, 'USABILITY:  Search on "Organization" brings back components, and not opportunities.  I''m expecting to see opportuntities', 0, '2003-12-10 13:44:05.066', 0, '2003-12-10 13:44:05.066', NULL, NULL, true);
INSERT INTO help_notes VALUES (159, 82, 'USABILITY:  Search on "Description" returns a results page that shows the components -- and thus the term searched on might not be anywhere in the component description.  Should return what field is searched on', 0, '2003-12-10 13:44:05.07', 0, '2003-12-10 13:44:05.07', NULL, NULL, true);
INSERT INTO help_notes VALUES (160, 82, 'PERFORMANCE: Account selection should be a pop up selection', 0, '2003-12-10 13:44:05.073', 0, '2003-12-10 13:44:05.073', NULL, NULL, true);
INSERT INTO help_notes VALUES (161, 82, 'NOMENCLATURE:  The "Description" should be renamed "Opportunity Description".  And "Organization" should be "Account"', 0, '2003-12-10 13:44:05.077', 0, '2003-12-10 13:44:05.077', NULL, NULL, true);
INSERT INTO help_notes VALUES (162, 82, 'BUG:  After doing a search, and assume nothing is found, it reflects these results in "View Opportunities" -- and nothing is shown because the criteria weren''t met.  But, these parameters are now somehow remembered and nothing will show up in "View Opportunities" if you click on that.', 0, '2003-12-10 13:44:05.081', 0, '2003-12-10 13:44:05.081', NULL, NULL, true);
INSERT INTO help_notes VALUES (163, 82, 'BUG:  Search Pipeline based on "Contact" doesn''t seem to work.  Put in a contact and it brings back everything.  Also, does it default to looking at "My" opp''s or "All" opp''s?  I think it should default to look at your hierarchy.  Thus, "all". UPDATE: It defaults to the view selected in View Opportunities', 0, '2003-12-10 13:44:05.084', 0, '2003-12-10 13:44:05.084', NULL, NULL, true);
INSERT INTO help_notes VALUES (164, 83, 'FEATURE (2.6.1):  Change the current "reports" to something like "export", and then create some canned, basic reports  through Jasper UPDATE: Done for 2.6', 0, '2003-12-10 13:44:05.094', 0, '2003-12-10 13:44:05.094', NULL, NULL, true);
INSERT INTO help_notes VALUES (165, 88, 'There is some extra space at the top of the Opportunity name', 0, '2003-12-10 13:44:05.167', 0, '2003-12-10 13:44:05.167', NULL, NULL, true);
INSERT INTO help_notes VALUES (166, 89, 'Needs "Send" and "Cancel" buttons at the top too', 0, '2003-12-10 13:44:05.175', 0, '2003-12-10 13:44:05.175', NULL, NULL, true);
INSERT INTO help_notes VALUES (167, 91, 'FEATURE: (NetDec) Output closed boolean field on report', 0, '2003-12-10 13:44:05.189', 0, '2003-12-10 13:44:05.189', NULL, NULL, true);
INSERT INTO help_notes VALUES (168, 91, 'FEATURE: (NetDec) Have a selection when running the report that will filter out all
opptys below a % probability (e.g. if we enter 20% as the value in that
field, only opptys with 20% or higher prob will print on that report run).', 0, '2003-12-10 13:44:05.193', 0, '2003-12-10 13:44:05.193', NULL, NULL, true);
INSERT INTO help_notes VALUES (169, 91, 'FEATURE: (NetDec) Have UK opps report in £ rather than $ - defaulting  by salesman user''s location', 0, '2003-12-10 13:44:05.197', 0, '2003-12-10 13:44:05.197', NULL, NULL, true);
INSERT INTO help_notes VALUES (170, 91, 'FEATURE: (NetDec) Add a sales territory (or similar) field for a sales person user', 0, '2003-12-10 13:44:05.20', 0, '2003-12-10 13:44:05.20', NULL, NULL, true);
INSERT INTO help_notes VALUES (171, 91, 'FEATURE: (NetDec) change date format, for example UK  "15 Jan 03" as opposed to "1/15/03" or "15/1/03" this has caused a bit of confusion as the UK reports dates in the latter format', 0, '2003-12-10 13:44:05.206', 0, '2003-12-10 13:44:05.206', NULL, NULL, true);
INSERT INTO help_notes VALUES (172, 91, 'LOGIC:  The report generates "components", not "opportunities", even though the type is called "Opportunities Listings".  The simple fix (2.6) is simply to just change what''s in the "Type" dropdown to "Component Listing".  But also need to export (2.6.1?) the component description with it, too.', 0, '2003-12-10 13:44:05.21', 0, '2003-12-10 13:44:05.21', NULL, NULL, true);
INSERT INTO help_notes VALUES (173, 91, 'BUG: Clicking on ''up'' or ''down'' button produces the a pop-up error window.', 0, '2003-12-10 13:44:05.213', 0, '2003-12-10 13:44:05.213', NULL, NULL, true);
INSERT INTO help_notes VALUES (174, 92, 'QUESTION: Entering a 1.5 value in months is accepted.  Should this be the case?  How does the calculation work?', 0, '2003-12-10 13:44:05.226', 0, '2003-12-10 13:44:05.226', NULL, NULL, true);
INSERT INTO help_notes VALUES (175, 92, 'QUESTION: Can make Probability of Close > than 100% and it will be accepted.  Is there a business reason for allowing values > 100% or should there be a limit?  A key reason for only accepting values between 0 and 1 is otherwise the calculation of risk adjusted is screwy', 0, '2003-12-10 13:44:05.23', 0, '2003-12-10 13:44:05.23', NULL, NULL, true);
INSERT INTO help_notes VALUES (176, 92, 'Adding a component allows dates in the past to be entered for estimated close date. This is a feature I''m not sure we need.', 0, '2003-12-10 13:44:05.234', 0, '2003-12-10 13:44:05.234', NULL, NULL, true);
INSERT INTO help_notes VALUES (177, 92, 'ERROR:Update Component''s details and refreshing the window throws an error', 0, '2003-12-10 13:44:05.237', 0, '2003-12-10 13:44:05.237', NULL, NULL, true);
INSERT INTO help_notes VALUES (178, 92, 'QUESTION: My Best Guess can be lower than the Low Estimate? Shouldnt the Best Guess be between the Low estimate and the High estimate', 0, '2003-12-10 13:44:05.241', 0, '2003-12-10 13:44:05.241', NULL, NULL, true);
INSERT INTO help_notes VALUES (179, 94, 'QUESTION:Search Oppurtunity->shows a list of components without the tab structure  -> clicking on a component to view its details again shows a list of components but now with a tab structure. So it actually takes two clicks to see a component after searching. Is this ok?', 0, '2003-12-10 13:44:05.282', 0, '2003-12-10 13:44:05.282', NULL, NULL, true);
INSERT INTO help_notes VALUES (180, 97, 'The color on this isn''t right', 0, '2003-12-10 13:44:05.358', 0, '2003-12-10 13:44:05.358', NULL, NULL, true);
INSERT INTO help_notes VALUES (181, 97, 'BUG: I reach an opportunity by the Dashboard trial. While trying to upload a document, the trial is " Pipeline >  Dashboard > Opportunity Details > Documents > Upload Document". But after I upload the document, the trial changes into "Pipeline >  Search Results > Opportunity Details > Documents" instead of "pipeline>dashboard>...."', 0, '2003-12-10 13:44:05.361', 0, '2003-12-10 13:44:05.361', NULL, NULL, true);
INSERT INTO help_notes VALUES (182, 99, 'When I reach an oppurtunity from the Dashboard, the trail list has "Pipeline>Dashboard>Oppurtunity details" in it. However, when I select the view details, the trial changes to "Pipleline>Search>..." instead of  "Pipeline>Dashboard>...."', 0, '2003-12-10 13:44:05.405', 0, '2003-12-10 13:44:05.405', NULL, NULL, true);
INSERT INTO help_notes VALUES (183, 99, 'BUG: Trying to add a component or modify an existing component produces a page with the message "The included page caused a problem."', 0, '2003-12-10 13:44:05.409', 0, '2003-12-10 13:44:05.409', NULL, NULL, true);
INSERT INTO help_notes VALUES (184, 99, 'BUG: What I think is happeneing is, when any action is selected by using the select option in the left column, the "dashboard" trial gets relaced by the "search results" trial.', 0, '2003-12-10 13:44:05.413', 0, '2003-12-10 13:44:05.413', NULL, NULL, true);
INSERT INTO help_notes VALUES (185, 100, 'Call forwarding has no text in the body ', 0, '2003-12-10 13:44:05.431', 0, '2003-12-10 13:44:05.431', NULL, NULL, true);
INSERT INTO help_notes VALUES (186, 100, 'Add a Call gives a "Page Not Found" UPDATE: Related to large number of contacts as one of the fields on this form is a dropdown selection of contact associated with the call', 0, '2003-12-10 13:44:05.435', 0, '2003-12-10 13:44:05.435', NULL, NULL, true);
INSERT INTO help_notes VALUES (187, 101, 'BUS. LOGIC (2.6.1):    When using the sort capability on the "Component" column (ascending/descending) it sorts on the opportunity (which you can''t see) and not on he component.  Should sort alphabetically on the Component ', 0, '2003-12-10 13:44:05.459', 0, '2003-12-10 13:44:05.459', NULL, NULL, true);
INSERT INTO help_notes VALUES (188, 101, 'USABILITY: Although the vie shows components on clicking on one of them it takes me to the opportunity', 0, '2003-12-10 13:44:05.463', 0, '2003-12-10 13:44:05.463', NULL, NULL, true);
INSERT INTO help_notes VALUES (189, 101, 'USEABILITY  (2.6):  "View Opportunities" sub-module should be renamed "View Components".  Then, we could create another, related sub-module (2.6.1?) called "View Opportunities" -- which would show Opportunity information versus component info.', 0, '2003-12-10 13:44:05.467', 0, '2003-12-10 13:44:05.467', NULL, NULL, true);
INSERT INTO help_notes VALUES (190, 101, '"Not Mine" component was open (Kristin''s), another component was closed (Matt''s), opportunity showed up under "All closed opps".  Shouldn''t', 0, '2003-12-10 13:44:05.47', 0, '2003-12-10 13:44:05.47', NULL, NULL, true);
INSERT INTO help_notes VALUES (191, 101, 'REMINDER: Put "All Users" first in list, only show users drop-down when not "My Open Opportunities"', 0, '2003-12-10 13:44:05.474', 0, '2003-12-10 13:44:05.474', NULL, NULL, true);
INSERT INTO help_notes VALUES (192, 101, 'Might be a good idea to show the oppurtunity in the table.', 0, '2003-12-10 13:44:05.481', 0, '2003-12-10 13:44:05.481', NULL, NULL, true);
INSERT INTO help_notes VALUES (193, 101, 'BUG or FEATURE ? Selecting "My Open Oppurtunites" takes me to the component list and not the oppurtunities list.', 0, '2003-12-10 13:44:05.484', 0, '2003-12-10 13:44:05.484', NULL, NULL, true);
INSERT INTO help_notes VALUES (194, 101, 'BUG:When clicked on the alphabetic links for navigation (0 A B C ...) and if the selected link does not have any results, the following label is displayed (Records 5 to 4 of 4 total ) for my example.', 0, '2003-12-10 13:44:05.488', 0, '2003-12-10 13:44:05.488', NULL, NULL, true);
INSERT INTO help_notes VALUES (195, 101, 'BUG:When search criteria cannot find any results, the labelling appears to be wrong.(1 -4 of 4 total) in my eample when 0 records were returned.', 0, '2003-12-10 13:44:05.492', 0, '2003-12-10 13:44:05.492', NULL, NULL, true);
INSERT INTO help_notes VALUES (196, 101, 'EA: The count feature of the number of results is stilll not corect. Even when there 4 records, the label says "No records to display".', 0, '2003-12-10 13:44:05.496', 0, '2003-12-10 13:44:05.496', NULL, NULL, true);
INSERT INTO help_notes VALUES (197, 103, 'BUG: When I enter 30000 in the Prob. of close, the system rightly asks me to enter something below a 100....but when I enter 120...it accepts the input. Bug in error checking.', 0, '2003-12-10 13:44:05.524', 0, '2003-12-10 13:44:05.524', NULL, NULL, true);
INSERT INTO help_notes VALUES (198, 106, 'BUG:  An Account/Opportunity/Call shows up, but can''t be modified.  It throws a "requested page not found" error', 0, '2003-12-10 13:44:05.566', 0, '2003-12-10 13:44:05.566', NULL, NULL, true);
INSERT INTO help_notes VALUES (199, 106, 'Pipeline: Pipleline Management>View Components >Oppurtunity Details>Calls >Call Details>----When I click on the Forward button this it gives a page not found.', 0, '2003-12-10 13:44:05.569', 0, '2003-12-10 13:44:05.569', NULL, NULL, true);
INSERT INTO help_notes VALUES (200, 107, 'IE: Extra space between trails and header', 0, '2003-12-10 13:44:05.59', 0, '2003-12-10 13:44:05.59', NULL, NULL, true);
INSERT INTO help_notes VALUES (201, 107, 'Extra space between the trails and the Opportunity symbol & description', 0, '2003-12-10 13:44:05.593', 0, '2003-12-10 13:44:05.593', NULL, NULL, true);
INSERT INTO help_notes VALUES (202, 107, 'Feature ? Is there a limiting size on the documents. I tried 17 MB...Works fine.', 0, '2003-12-10 13:44:05.597', 0, '2003-12-10 13:44:05.597', NULL, NULL, true);
INSERT INTO help_notes VALUES (203, 111, 'Only the account alerts show up on this calendar so the relevance for the description for other icons is little.', 0, '2003-12-10 13:44:05.732', 0, '2003-12-10 13:44:05.732', NULL, NULL, true);
INSERT INTO help_notes VALUES (204, 111, 'BUG: To remove popup "this page has secure and nonsecure items"', 0, '2003-12-10 13:44:05.736', 0, '2003-12-10 13:44:05.736', NULL, NULL, true);
INSERT INTO help_notes VALUES (205, 111, 'USABILITY: Since an account name is clickable the way it is displayed should indicate that(bpk)', 0, '2003-12-10 13:44:05.739', 0, '2003-12-10 13:44:05.739', NULL, NULL, true);
INSERT INTO help_notes VALUES (206, 111, 'BUG:add two accounts with expiry on the same date. This shows a category account on the dashboard, but the number of accounts is not updated.', 0, '2003-12-10 13:44:05.743', 0, '2003-12-10 13:44:05.743', NULL, NULL, true);
INSERT INTO help_notes VALUES (207, 111, 'BUG: https://ds21.darkhorseventures.com/Accounts.do?command=Dashboard produced and error message saying "The included page caused a problem" in red. There was a long delay before this came up.', 0, '2003-12-10 13:44:05.747', 0, '2003-12-10 13:44:05.747', NULL, NULL, true);
INSERT INTO help_notes VALUES (208, 112, 'BUG: max characters in a text field should be declared according to the database', 0, '2003-12-10 13:44:05.78', 0, '2003-12-10 13:44:05.78', NULL, NULL, true);
INSERT INTO help_notes VALUES (209, 113, 'VALIDATION: Email address needs to be validated', 0, '2003-12-10 13:44:05.805', 0, '2003-12-10 13:44:05.805', NULL, NULL, true);
INSERT INTO help_notes VALUES (210, 113, 'VALIDATION: Entering a non-integer in the phone number field throws a "Page Not Found"', 0, '2003-12-10 13:44:05.808', 0, '2003-12-10 13:44:05.808', NULL, NULL, true);
INSERT INTO help_notes VALUES (211, 113, 'VALIDATION: Entering a non-integer in the revenue should alert the user', 0, '2003-12-10 13:44:05.812', 0, '2003-12-10 13:44:05.812', NULL, NULL, true);
INSERT INTO help_notes VALUES (212, 113, 'BUG: Adding an individual account shows up as an organization account on the update screen. After fixing this check  if the org_name is being consistently maintained when the last and first name changes', 0, '2003-12-10 13:44:05.815', 0, '2003-12-10 13:44:05.815', NULL, NULL, true);
INSERT INTO help_notes VALUES (213, 113, 'BUG: Exception "object expected" when an account is modified, the changes are recorded though(bpk)', 0, '2003-12-10 13:44:05.819', 0, '2003-12-10 13:44:05.819', NULL, NULL, true);
INSERT INTO help_notes VALUES (214, 113, 'Account Details> Modify Account--- The Update button gives a java script error. The url is https://ds21.darkhorseventures.com/Accounts.do?command=Modify&orgId=80', 0, '2003-12-10 13:44:05.823', 0, '2003-12-10 13:44:05.823', NULL, NULL, true);
INSERT INTO help_notes VALUES (215, 114, 'FEATURE: On completing a QA item I want to mark it as complete and might want to add some comments', 0, '2003-12-10 13:44:05.845', 0, '2003-12-10 13:44:05.845', NULL, NULL, true);
INSERT INTO help_notes VALUES (216, 114, 'Although I do not have permissions to add a account contact, I can do so from the general contacts module if I had permissions to add a general contact. Pipeline also would have the same issue. Pipeline Management would have the same issue with Account and Contact Opps having different business rule. This issue also would also This goes to show that we should have a Business Rule Manager in place soon to enforce the business rules based on objects.', 0, '2003-12-10 13:44:05.848', 0, '2003-12-10 13:44:05.848', NULL, NULL, true);
INSERT INTO help_notes VALUES (217, 114, 'None of the fields on the list are sortable COMPLETE: Name is sortable now', 0, '2003-12-10 13:44:05.854', 0, '2003-12-10 13:44:05.854', NULL, NULL, true);
INSERT INTO help_notes VALUES (218, 116, 'Edit Action throws a "Page Not Found"', 0, '2003-12-10 13:44:05.90', 0, '2003-12-10 13:44:05.90', NULL, NULL, true);
INSERT INTO help_notes VALUES (219, 116, 'Although I do not have access to adding Account Opportunities, changing the URL string "View" to "Prepare" gives me access', 0, '2003-12-10 13:44:05.904', 0, '2003-12-10 13:44:05.904', NULL, NULL, true);
INSERT INTO help_notes VALUES (220, 116, 'BUG: When a new opportunity is added, existing opportunities are not listed unless the opportunity tab is refreshed(bpk)', 0, '2003-12-10 13:44:05.907', 0, '2003-12-10 13:44:05.907', NULL, NULL, true);
INSERT INTO help_notes VALUES (221, 117, 'Although I have permissions to delete Revenue records on trying to delete a record, a permission error is raised', 0, '2003-12-10 13:44:05.931', 0, '2003-12-10 13:44:05.931', NULL, NULL, true);
INSERT INTO help_notes VALUES (222, 117, '"All Revenue" view needs to show the owner field', 0, '2003-12-10 13:44:05.935', 0, '2003-12-10 13:44:05.935', NULL, NULL, true);
INSERT INTO help_notes VALUES (223, 118, 'Although I have permissions to delete Revenue records on trying to delete a record, a permission error is raised', 0, '2003-12-10 13:44:05.958', 0, '2003-12-10 13:44:05.958', NULL, NULL, true);
INSERT INTO help_notes VALUES (224, 118, '"All Revenue" view needs to show the owner field', 0, '2003-12-10 13:44:05.962', 0, '2003-12-10 13:44:05.962', NULL, NULL, true);
INSERT INTO help_notes VALUES (225, 121, 'The issue appears in a little section here, but in tickets it gets a whole line', 0, '2003-12-10 13:44:06', 0, '2003-12-10 13:44:06', NULL, NULL, true);
INSERT INTO help_notes VALUES (226, 123, 'On searching on an Account Number field, results gotten do not have a account number column.', 0, '2003-12-10 13:44:06.032', 0, '2003-12-10 13:44:06.032', NULL, NULL, true);
INSERT INTO help_notes VALUES (227, 123, 'BUG: Search fails both on account number and phone number(bpk)', 0, '2003-12-10 13:44:06.036', 0, '2003-12-10 13:44:06.036', NULL, NULL, true);
INSERT INTO help_notes VALUES (228, 124, 'Any thing can be typed for Account number/URL. Even junk values !', 0, '2003-12-10 13:44:06.049', 0, '2003-12-10 13:44:06.049', NULL, NULL, true);
INSERT INTO help_notes VALUES (229, 124, 'USABILITY: All tabs should indicate whether they have information or not, currently the user has to click on every tab to know whether any other infohas been recorded for the account(bpk)', 0, '2003-12-10 13:44:06.053', 0, '2003-12-10 13:44:06.053', NULL, NULL, true);
INSERT INTO help_notes VALUES (230, 126, 'The FileItem object inefficiently used getEnteredByName() and getModifiedByName() -- most actions are iterating the list to set these', 0, '2003-12-10 13:44:06.094', 0, '2003-12-10 13:44:06.094', NULL, NULL, true);
INSERT INTO help_notes VALUES (231, 129, 'ERROR:when a new account is created and then SELECT->''Archive'' gives an error:You do not have permissions to perform this action.', 0, '2003-12-10 13:44:06.12', 0, '2003-12-10 13:44:06.12', NULL, NULL, true);
INSERT INTO help_notes VALUES (232, 130, 'Generated a full report of All Contacts with all columns but the columns entered, enteredby, modified, modifiedby, owner, notes did not get generated with the report ', 0, '2003-12-10 13:44:06.132', 0, '2003-12-10 13:44:06.132', NULL, NULL, true);
INSERT INTO help_notes VALUES (233, 130, 'USABILITY: The Up and Down buttons and also the Add All, Remove buttons have the table border around them . Browser: IE5.0', 0, '2003-12-10 13:44:06.136', 0, '2003-12-10 13:44:06.136', NULL, NULL, true);
INSERT INTO help_notes VALUES (234, 133, 'UI: The manadatory subject field does not have an asterisk in red.', 0, '2003-12-10 13:44:06.18', 0, '2003-12-10 13:44:06.18', NULL, NULL, true);
INSERT INTO help_notes VALUES (235, 134, 'BUG: Some uploaded files have an extra 2 bytes appended CRLF', 0, '2003-12-10 13:44:06.192', 0, '2003-12-10 13:44:06.192', NULL, NULL, true);
INSERT INTO help_notes VALUES (236, 134, 'BUG: Adding a file with Apple Safari breaks  NOTE: Should be fixed now', 0, '2003-12-10 13:44:06.196', 0, '2003-12-10 13:44:06.196', NULL, NULL, true);
INSERT INTO help_notes VALUES (237, 136, 'BUG: Althought opportunity modify permissions are turned on, I am not able to modify a component. Add & Delete permissionss are turned off', 0, '2003-12-10 13:44:06.21', 0, '2003-12-10 13:44:06.21', NULL, NULL, true);
INSERT INTO help_notes VALUES (238, 139, 'On doing a "Search Accounts" on the name of a company it takes you to "View Accounts" and shows the search results. In the filter though it still says "All Accounts" instead of "Search Results". Also for all further queries of "All Accounts" only the "Search Results" are returned', 0, '2003-12-10 13:44:06.233', 0, '2003-12-10 13:44:06.233', NULL, NULL, true);
INSERT INTO help_notes VALUES (239, 140, 'When adding a new contact using the popup, if the name has a '' or a " then a javascript error is thrown', 0, '2003-12-10 13:44:06.245', 0, '2003-12-10 13:44:06.245', NULL, NULL, true);
INSERT INTO help_notes VALUES (240, 140, '"Create New Contact" link gives a "Page Not Found"', 0, '2003-12-10 13:44:06.249', 0, '2003-12-10 13:44:06.249', NULL, NULL, true);
INSERT INTO help_notes VALUES (241, 141, 'Account Ticket Documents need to have their own permissions', 0, '2003-12-10 13:44:06.274', 0, '2003-12-10 13:44:06.274', NULL, NULL, true);
INSERT INTO help_notes VALUES (242, 141, '"Add a Document" gives a permission error although I have permission to edit tickets.', 0, '2003-12-10 13:44:06.278', 0, '2003-12-10 13:44:06.278', NULL, NULL, true);
INSERT INTO help_notes VALUES (243, 141, 'Clicking on the Subject of the uploded document i.e. the Item column gives a Java Script error. Clicking on the select button under "Action" and going to the view details also gives a JS erro', 0, '2003-12-10 13:44:06.281', 0, '2003-12-10 13:44:06.281', NULL, NULL, true);
INSERT INTO help_notes VALUES (244, 143, 'BUG: Extra space between trails and Account Name. Browser: IE 5.0', 0, '2003-12-10 13:44:06.295', 0, '2003-12-10 13:44:06.295', NULL, NULL, true);
INSERT INTO help_notes VALUES (245, 144, 'BUG/DISPLAY: requires a top level message idicating all mandatory feilds have not been filled', 0, '2003-12-10 13:44:06.304', 0, '2003-12-10 13:44:06.304', NULL, NULL, true);
INSERT INTO help_notes VALUES (246, 149, 'BUG:When the page is submitted with just the"account type", an appropriate message is displayed, but the "account type(s)" list becomes empty(bpk)', 0, '2003-12-10 13:44:06.358', 0, '2003-12-10 13:44:06.358', NULL, NULL, true);
INSERT INTO help_notes VALUES (247, 149, 'USABILITY: Phone/fax should have seperate text feild for area code(bpk)', 0, '2003-12-10 13:44:06.361', 0, '2003-12-10 13:44:06.361', NULL, NULL, true);
INSERT INTO help_notes VALUES (248, 149, 'FEATURE: Phone number strictly accepts 10 digits, may not be suitable for internationalization', 0, '2003-12-10 13:44:06.365', 0, '2003-12-10 13:44:06.365', NULL, NULL, true);
INSERT INTO help_notes VALUES (249, 150, 'BUG: There is a space between the tabbed sub menu for account and the contact. Check all other screens on fixing', 0, '2003-12-10 13:44:06.377', 0, '2003-12-10 13:44:06.377', NULL, NULL, true);
INSERT INTO help_notes VALUES (250, 150, 'USABILITY: When the permissions for add,edit and delete are turned off the forward button does not have a space after it', 0, '2003-12-10 13:44:06.383', 0, '2003-12-10 13:44:06.383', NULL, NULL, true);
INSERT INTO help_notes VALUES (251, 157, 'PERFORMANCE: The drop down selection of a contact is inefficient from a performance standpoint', 0, '2003-12-10 13:44:06.497', 0, '2003-12-10 13:44:06.497', NULL, NULL, true);
INSERT INTO help_notes VALUES (252, 157, '"close" checkbox is not retained if the validation on some other field on the form fails', 0, '2003-12-10 13:44:06.501', 0, '2003-12-10 13:44:06.501', NULL, NULL, true);
INSERT INTO help_notes VALUES (253, 157, 'BUG: A ticket source "other" should involve a description(bpk)', 0, '2003-12-10 13:44:06.504', 0, '2003-12-10 13:44:06.504', NULL, NULL, true);
INSERT INTO help_notes VALUES (254, 158, 'Account Ticket Tasks need their own permissions', 0, '2003-12-10 13:44:06.525', 0, '2003-12-10 13:44:06.525', NULL, NULL, true);
INSERT INTO help_notes VALUES (255, 158, 'Business Rules of a Ticket Tasks are not defined clearly', 0, '2003-12-10 13:44:06.529', 0, '2003-12-10 13:44:06.529', NULL, NULL, true);
INSERT INTO help_notes VALUES (256, 158, 'The task description has the table border showing around the description and the status image, also if there is a contact linked to it then the details of that has a similar issue', 0, '2003-12-10 13:44:06.533', 0, '2003-12-10 13:44:06.533', NULL, NULL, true);
INSERT INTO help_notes VALUES (257, 158, 'Permission needs to be added for Account Ticket Tasks ', 0, '2003-12-10 13:44:06.537', 0, '2003-12-10 13:44:06.537', NULL, NULL, true);
INSERT INTO help_notes VALUES (258, 158, 'BUG: permission issue in ticket tasks. Only when a user is given permission to both edit and add a task, he is able to add the task. If no edit permissions, he cannot add a ticket although he has add permissions.', 0, '2003-12-10 13:44:06.54', 0, '2003-12-10 13:44:06.54', NULL, NULL, true);
INSERT INTO help_notes VALUES (259, 160, 'Coloring scheme is not very appealing on this screen', 0, '2003-12-10 13:44:06.558', 0, '2003-12-10 13:44:06.558', NULL, NULL, true);
INSERT INTO help_notes VALUES (260, 167, 'Cancel button takes you to Pipeline / View Opp''s -- should take to Pipeline / Add Opportunity', 0, '2003-12-10 13:44:06.628', 0, '2003-12-10 13:44:06.628', NULL, NULL, true);
INSERT INTO help_notes VALUES (261, 167, 'Date picker calander is too narrow - both month scroll arrows should show', 0, '2003-12-10 13:44:06.631', 0, '2003-12-10 13:44:06.631', NULL, NULL, true);
INSERT INTO help_notes VALUES (262, 167, 'If click to modify a component that''s already been closed, it brings back the page without the "Close this checkbox" checkbox checked (2.6.1?)', 0, '2003-12-10 13:44:06.635', 0, '2003-12-10 13:44:06.635', NULL, NULL, true);
INSERT INTO help_notes VALUES (263, 167, 'It is possible to add a low estimate that is higher than the high estimate. We should trap for this.', 0, '2003-12-10 13:44:06.638', 0, '2003-12-10 13:44:06.638', NULL, NULL, true);
INSERT INTO help_notes VALUES (264, 167, 'I see two (2) cancel buttons', 0, '2003-12-10 13:44:06.643', 0, '2003-12-10 13:44:06.643', NULL, NULL, true);
INSERT INTO help_notes VALUES (265, 167, 'Matt says this look like...extra space on Internet Explorer', 0, '2003-12-10 13:44:06.646', 0, '2003-12-10 13:44:06.646', NULL, NULL, true);
INSERT INTO help_notes VALUES (266, 167, 'BUG: Error checking needed for closing dates and percentage values.(problem is closing dates could be already past and percentage values even greater then 100 are accepted)', 0, '2003-12-10 13:44:06.65', 0, '2003-12-10 13:44:06.65', NULL, NULL, true);
INSERT INTO help_notes VALUES (267, 167, 'BUG:''Associate With'' field does not remember if the Contact radio is selected and when saved, it gives an error saying review message:An account or a contact needs to be selected', 0, '2003-12-10 13:44:06.653', 0, '2003-12-10 13:44:06.653', NULL, NULL, true);
INSERT INTO help_notes VALUES (268, 167, 'QUESTION:Low estimate and High estimate can take negative values ?', 0, '2003-12-10 13:44:06.657', 0, '2003-12-10 13:44:06.657', NULL, NULL, true);
INSERT INTO help_notes VALUES (269, 167, 'BUG: AlertDate.Vale is not an object on saving the opportunity', 0, '2003-12-10 13:44:06.661', 0, '2003-12-10 13:44:06.661', NULL, NULL, true);
INSERT INTO help_notes VALUES (270, 167, 'Pipeline: Pipleline Management > Add Oppurtunity--- add a oppurtunity and hit "save button" gives you a java script error', 0, '2003-12-10 13:44:06.664', 0, '2003-12-10 13:44:06.664', NULL, NULL, true);
INSERT INTO help_notes VALUES (271, 167, 'BUG:(inconsistent) https://ds21.darkhorseventures.com/LeadsComponents.do?command=SaveComponent&auto-populate=true. I tried submitting an invalid form and came to this page. The ''Select'' buttons in the ''Associate With'' part do not do any actions. The links do not respond. I could not successfully submit this form.', 0, '2003-12-10 13:44:06.668', 0, '2003-12-10 13:44:06.668', NULL, NULL, true);
INSERT INTO help_notes VALUES (272, 167, 'BUG:(inconsistent) Got a sql exception in https://ds21.darkhorseventures.com/LeadsComponents.do?command=SaveComponent&auto-populate=true. THe first line of the exceptions said "missing oppurtunity header"', 0, '2003-12-10 13:44:06.672', 0, '2003-12-10 13:44:06.672', NULL, NULL, true);
INSERT INTO help_notes VALUES (273, 169, 'BUG: On clicking the "Rename Opportunity" button there is an error ', 0, '2003-12-10 13:44:06.706', 0, '2003-12-10 13:44:06.706', NULL, NULL, true);
INSERT INTO help_notes VALUES (274, 169, 'Rename Opportunity button throws a "Page Not Found"', 0, '2003-12-10 13:44:06.709', 0, '2003-12-10 13:44:06.709', NULL, NULL, true);
INSERT INTO help_notes VALUES (275, 169, 'Although I have permissions to edit opportunites I cannot edit components of the opportunity', 0, '2003-12-10 13:44:06.713', 0, '2003-12-10 13:44:06.713', NULL, NULL, true);
INSERT INTO help_notes VALUES (276, 169, '"Add a Component" link is  broken', 0, '2003-12-10 13:44:06.716', 0, '2003-12-10 13:44:06.716', NULL, NULL, true);
INSERT INTO help_notes VALUES (277, 171, 'There is an extra space at the top of the Details table when the user has only view permissions ', 0, '2003-12-10 13:44:06.738', 0, '2003-12-10 13:44:06.738', NULL, NULL, true);
INSERT INTO help_notes VALUES (278, 173, 'USABILITY: Space between Cancel and Reset buttons at the top', 0, '2003-12-10 13:44:06.772', 0, '2003-12-10 13:44:06.772', NULL, NULL, true);
INSERT INTO help_notes VALUES (279, 175, 'Should have a ticket documents permission like account ticket documents', 0, '2003-12-10 13:44:06.919', 0, '2003-12-10 13:44:06.919', NULL, NULL, true);
INSERT INTO help_notes VALUES (280, 175, 'FEATURE: When the overview of the tickets are provided, such as source, issue..etc, it might be a good idea to show the number of tasks or documents associated with it . This could link to the "Tickets" section to make it more navigatable', 0, '2003-12-10 13:44:06.924', 0, '2003-12-10 13:44:06.924', NULL, NULL, true);
INSERT INTO help_notes VALUES (281, 176, 'On assigning a ticket to a user, the email which is sent as a alert does not have any content in it', 0, '2003-12-10 13:44:06.944', 0, '2003-12-10 13:44:06.944', NULL, NULL, true);
INSERT INTO help_notes VALUES (282, 176, 'BUG: Error checking not performed for date. It is possible to add a task whose due date has already passed.', 0, '2003-12-10 13:44:06.947', 0, '2003-12-10 13:44:06.947', NULL, NULL, true);
INSERT INTO help_notes VALUES (283, 176, 'Need to have a "Users without a department" like projects so tickets can be assigned to users that are not in a department', 0, '2003-12-10 13:44:06.951', 0, '2003-12-10 13:44:06.951', NULL, NULL, true);
INSERT INTO help_notes VALUES (284, 177, 'USABILITY: Search form should give the option for sorting by certain fields like status, entered date, number, severity', 0, '2003-12-10 13:44:06.964', 0, '2003-12-10 13:44:06.964', NULL, NULL, true);
INSERT INTO help_notes VALUES (285, 177, 'FEATURE: Add a freeform text search to find tickets based on issue text', 0, '2003-12-10 13:44:06.968', 0, '2003-12-10 13:44:06.968', NULL, NULL, true);
INSERT INTO help_notes VALUES (286, 177, 'FEATURE: Add a freeform text search to find tickets based on resolution text', 0, '2003-12-10 13:44:06.971', 0, '2003-12-10 13:44:06.971', NULL, NULL, true);
INSERT INTO help_notes VALUES (287, 177, 'FEATURE: Search form should remember the search criteria', 0, '2003-12-10 13:44:06.975', 0, '2003-12-10 13:44:06.975', NULL, NULL, true);
INSERT INTO help_notes VALUES (288, 177, 'FEATURE: Add search criteria to view tickets assigned to a specific user', 0, '2003-12-10 13:44:06.978', 0, '2003-12-10 13:44:06.978', NULL, NULL, true);
INSERT INTO help_notes VALUES (289, 177, 'FEATURE:The first search criteria is to search based on ticket number. This ticket number is system assigned and not provided by the creator. A user would have to have seen the ticket earlier to retrive it using this feature.', 0, '2003-12-10 13:44:06.982', 0, '2003-12-10 13:44:06.982', NULL, NULL, true);
INSERT INTO help_notes VALUES (290, 177, 'BUG:The ''organization'' should have the label "All" by default and the ''assigned to'' should have the label "Any" by default. The current labelling belies the search criteria.', 0, '2003-12-10 13:44:06.986', 0, '2003-12-10 13:44:06.986', NULL, NULL, true);
INSERT INTO help_notes VALUES (291, 178, 'USABILITY: On the reports page we should have sortable headers for "Subject", "Created Date", and "Created By".', 0, '2003-12-10 13:44:07.009', 0, '2003-12-10 13:44:07.009', NULL, NULL, true);
INSERT INTO help_notes VALUES (292, 178, 'FEATURE: As options for the select button, along with "view details, delete and download", a "print" option would be useful. ', 0, '2003-12-10 13:44:07.014', 0, '2003-12-10 13:44:07.014', NULL, NULL, true);
INSERT INTO help_notes VALUES (293, 178, 'FEATURE: The feature is downloaded as a CSV that could be opened in a Excel. Would be a useful feature to download a PDF file depending on user preferences.', 0, '2003-12-10 13:44:07.018', 0, '2003-12-10 13:44:07.018', NULL, NULL, true);
INSERT INTO help_notes VALUES (294, 179, 'FEATURE: A solution is mandatory when closing a ticket. This is not indicated anywhere. Might be a good idea to inform the user with a label or *.', 0, '2003-12-10 13:44:07.036', 0, '2003-12-10 13:44:07.036', NULL, NULL, true);
INSERT INTO help_notes VALUES (295, 179, 'BUG:When the user closes a ticket without providing a solution, "The included page caused a problem. " appers on the next screen with no navigatable links. Might be a good idea to do the error checking and popup a warning frame instead.', 0, '2003-12-10 13:44:07.04', 0, '2003-12-10 13:44:07.04', NULL, NULL, true);
INSERT INTO help_notes VALUES (296, 179, 'BUG: Alert user that the solution will not be saved unless the ticket is closed.', 0, '2003-12-10 13:44:07.043', 0, '2003-12-10 13:44:07.043', NULL, NULL, true);
INSERT INTO help_notes VALUES (297, 180, 'FEATURE: A solution is mandatory when closing a ticket. This is not indicated anywhere. Might be a good idea to inform the user with a label or *.', 0, '2003-12-10 13:44:07.056', 0, '2003-12-10 13:44:07.056', NULL, NULL, true);
INSERT INTO help_notes VALUES (298, 180, 'BUG:When the user closes a ticket without providing a solution, "The included page caused a problem. " appers on the next screen with no navigatable links. Might be a good idea to do the error checking and popup a warning frame instead.', 0, '2003-12-10 13:44:07.06', 0, '2003-12-10 13:44:07.06', NULL, NULL, true);
INSERT INTO help_notes VALUES (299, 180, 'BUG: Alert user that the solution will not be saved unless the ticket is closed.', 0, '2003-12-10 13:44:07.064', 0, '2003-12-10 13:44:07.064', NULL, NULL, true);
INSERT INTO help_notes VALUES (300, 181, 'DONE: TM', 0, '2003-12-10 13:44:07.084', 0, '2003-12-10 13:44:07.084', NULL, NULL, true);
INSERT INTO help_notes VALUES (301, 181, 'BUG: I cannot see any tasks that have been added to a ticket except my own, I don''t expect to be able to EDIT it, but I should be able to view it', 0, '2003-12-10 13:44:07.087', 0, '2003-12-10 13:44:07.087', NULL, NULL, true);
INSERT INTO help_notes VALUES (302, 181, 'BUG: When adding a ticket task, the task should only be allowed to be assigned to users of the system OR at least let the person who created the task modify it if the person is not a user of the system', 0, '2003-12-10 13:44:07.091', 0, '2003-12-10 13:44:07.091', NULL, NULL, true);
INSERT INTO help_notes VALUES (303, 181, 'Cannot add a Task in tickets. I think I was able to a few minutes ago. Not sure if it is only under some circumstances. UPDATE: Not able to simulate the bug', 0, '2003-12-10 13:44:07.095', 0, '2003-12-10 13:44:07.095', NULL, NULL, true);
INSERT INTO help_notes VALUES (304, 181, 'FEATURE:The task associated with a ticket is shown in the ticket section. However, the task could be modified or editied only under the tickets section. A link connecting the task from the ticket section to the task section would be useful.', 0, '2003-12-10 13:44:07.098', 0, '2003-12-10 13:44:07.098', NULL, NULL, true);
INSERT INTO help_notes VALUES (305, 181, 'BUG: The due date for the task could be given as a date which is already passed.', 0, '2003-12-10 13:44:07.102', 0, '2003-12-10 13:44:07.102', NULL, NULL, true);
INSERT INTO help_notes VALUES (306, 181, 'FEATURE: The age of the task is mentioned in terms of days. Would be more descriptive to mention it in terms of hours and days as in the case of tickets.', 0, '2003-12-10 13:44:07.105', 0, '2003-12-10 13:44:07.105', NULL, NULL, true);
INSERT INTO help_notes VALUES (307, 181, 'FEATURE ? or BUG ?   When creating a task associated with a ticket, the dude date could be a date earlier than date of entry. Should that be disallowed ?', 0, '2003-12-10 13:44:07.109', 0, '2003-12-10 13:44:07.109', NULL, NULL, true);
INSERT INTO help_notes VALUES (308, 182, 'FEATURE:Might be useful to tell the user that clicking on the navigational alphabets on top would actually produce all the matches from that alphabet and after.', 0, '2003-12-10 13:44:07.132', 0, '2003-12-10 13:44:07.132', NULL, NULL, true);
INSERT INTO help_notes VALUES (309, 182, 'BUG: Once in a while the text in the links "Details","tasks","document" and "History" appear to be skewed or only half visible. Becomes corrected when the mouse is moved over.', 0, '2003-12-10 13:44:07.136', 0, '2003-12-10 13:44:07.136', NULL, NULL, true);
INSERT INTO help_notes VALUES (310, 182, 'USABILITY: There is no way to tell that there is a document associated with a ticket until you actually go to the documents tab for the particular ticket. There should be an icon on the ticket summary page and on every page below.', 0, '2003-12-10 13:44:07.14', 0, '2003-12-10 13:44:07.14', NULL, NULL, true);
INSERT INTO help_notes VALUES (311, 182, 'BUG: Add Document link shows up although the permissions are turned off', 0, '2003-12-10 13:44:07.143', 0, '2003-12-10 13:44:07.143', NULL, NULL, true);
INSERT INTO help_notes VALUES (312, 185, 'DONE: TM', 0, '2003-12-10 13:44:07.178', 0, '2003-12-10 13:44:07.178', NULL, NULL, true);
INSERT INTO help_notes VALUES (313, 185, 'FEATURE:In the ''Ticket log History'' table, it might be a good idea to label the columns and tell the user what they mean. An example would be "Ticket creator, Modification time and modification".', 0, '2003-12-10 13:44:07.181', 0, '2003-12-10 13:44:07.181', NULL, NULL, true);
INSERT INTO help_notes VALUES (314, 187, 'USABILITY: On creating a ticket, the email which is sent should not have the "Comment" field if no comment was entered', 0, '2003-12-10 13:44:07.195', 0, '2003-12-10 13:44:07.195', NULL, NULL, true);
INSERT INTO help_notes VALUES (315, 189, 'BUG: For report generation, the subject line is a must. However, ther subject line in the report is not marked with a red * to indiacate that it is mandatory.', 0, '2003-12-10 13:44:07.209', 0, '2003-12-10 13:44:07.209', NULL, NULL, true);
INSERT INTO help_notes VALUES (316, 189, 'FEATURE: It might be a good idea to actually have a "delete all" button similar to the "add all" button.', 0, '2003-12-10 13:44:07.212', 0, '2003-12-10 13:44:07.212', NULL, NULL, true);
INSERT INTO help_notes VALUES (317, 189, 'BUG: When the "up" and "down" buttons are pressed, an "errors on page" window is pops up in IE. ', 0, '2003-12-10 13:44:07.216', 0, '2003-12-10 13:44:07.216', NULL, NULL, true);
INSERT INTO help_notes VALUES (318, 189, 'FEATURE: Along with "add all, add and delete", it might be a good idea to have a button which provides the option "Add all non-empty fields".', 0, '2003-12-10 13:44:07.22', 0, '2003-12-10 13:44:07.22', NULL, NULL, true);
INSERT INTO help_notes VALUES (319, 189, 'FEATURE: It might be a good idea to project the report to the user and ask for a confirmation before saving it. This might be useful because the users dont have any direct feedback on what is selected even when they press the generate button.', 0, '2003-12-10 13:44:07.223', 0, '2003-12-10 13:44:07.223', NULL, NULL, true);
INSERT INTO help_notes VALUES (320, 191, 'BUG: The table shown in the left reflects correctly the tickets assigned to and by me. The My Items box does not seem to reflect the changes. There is something wrong with the counts.', 0, '2003-12-10 13:44:07.237', 0, '2003-12-10 13:44:07.237', NULL, NULL, true);
INSERT INTO help_notes VALUES (321, 192, 'Download link throws a Page Not Found', 0, '2003-12-10 13:44:07.246', 0, '2003-12-10 13:44:07.246', NULL, NULL, true);
INSERT INTO help_notes VALUES (322, 195, 'DONE: TM', 0, '2003-12-10 13:44:07.275', 0, '2003-12-10 13:44:07.275', NULL, NULL, true);
INSERT INTO help_notes VALUES (323, 195, 'Search tickets needs to go back to search form after a previous search was done.  Not to the view tickets form.', 0, '2003-12-10 13:44:07.279', 0, '2003-12-10 13:44:07.279', NULL, NULL, true);
INSERT INTO help_notes VALUES (324, 195, 'BUG: When we delete a ticket, using the option in the ''search results'', a warning frame pops up. When ok is clicked a warning frame pops up in IE indicating errors in the page and a blank page with no content or links is shown. ', 0, '2003-12-10 13:44:07.282', 0, '2003-12-10 13:44:07.282', NULL, NULL, true);
INSERT INTO help_notes VALUES (325, 195, 'BUG: Action Column in the header of the list is missing', 0, '2003-12-10 13:44:07.286', 0, '2003-12-10 13:44:07.286', NULL, NULL, true);
INSERT INTO help_notes VALUES (326, 201, 'It is possible to have permission to view the ticket module, but not have permission to view tickets, which generates an error. UPDATE: It is a permissionError which is raised and one resolution could be to define minimal permissions you need to have turned on to have view access to a module and also to exclude add/edit/delete permissions when you do not have view permissions. This is a system wide issue.', 0, '2003-12-10 13:44:07.359', 0, '2003-12-10 13:44:07.359', NULL, NULL, true);
INSERT INTO help_notes VALUES (327, 201, 'It looks like a ticket entered by me and assigned to me will only be shown on the entered by me section, and not the assigned to me section. I now see that this is only sometimes true.UPDATE: Any ticket created by you and assigned to yourself is showing up in both the Tickets Assigned to Me and Tickets Created By Me, not sure what the real issue is  here', 0, '2003-12-10 13:44:07.363', 0, '2003-12-10 13:44:07.363', NULL, NULL, true);
INSERT INTO help_notes VALUES (328, 201, 'BUG:The number of assigned/unassigned tickets shown in the ''View Tickets'' and counts shown in the ''My Items'' dont correlate.', 0, '2003-12-10 13:44:07.366', 0, '2003-12-10 13:44:07.366', NULL, NULL, true);
INSERT INTO help_notes VALUES (329, 201, 'FEATURE: Would be a good idea to rename the link ''Tickets>View Tickets'' to where the ''Tickets>View Open Tickets''.', 0, '2003-12-10 13:44:07.372', 0, '2003-12-10 13:44:07.372', NULL, NULL, true);
INSERT INTO help_notes VALUES (330, 202, 'Cannot delete an employee to whom a communication has been sent', 0, '2003-12-10 13:44:07.463', 0, '2003-12-10 13:44:07.463', NULL, NULL, true);
INSERT INTO help_notes VALUES (331, 202, 'I am a EMPLOYEE and Do Not Show up as an employee (roberts) HINT(Mathur) : Contact List object still uses the contact_type_levels for an employee, there is a employee field in contact which can be used for this', 0, '2003-12-10 13:44:07.467', 0, '2003-12-10 13:44:07.467', NULL, NULL, true);
INSERT INTO help_notes VALUES (332, 202, 'USABILITY: Sub menu should also have a ''Add'' option', 0, '2003-12-10 13:44:07.471', 0, '2003-12-10 13:44:07.471', NULL, NULL, true);
INSERT INTO help_notes VALUES (333, 210, 'Refreshing screen produces java.sql.SQLException: Queue record not found.', 0, '2003-12-10 13:44:07.609', 0, '2003-12-10 13:44:07.609', NULL, NULL, true);
INSERT INTO help_notes VALUES (334, 216, 'DEVELOPER: ListUsers method raises an exception at the server level , a Nullpointer in the UserNameHandler tag lib ', 0, '2003-12-10 13:44:07.761', 0, '2003-12-10 13:44:07.761', NULL, NULL, true);
INSERT INTO help_notes VALUES (335, 216, 'JAVASCRIPT ERROR: System Administration->Add User->[Select Contact] opens a popup window. Now minimize the popup. Click on View users. Now maximize the popup and click on Add in the popup. This shows an error.', 0, '2003-12-10 13:44:07.765', 0, '2003-12-10 13:44:07.765', NULL, NULL, true);
INSERT INTO help_notes VALUES (336, 216, 'BUG:The ''disable login'' button should be enabled only when the role of the user permits deletion of users. Currently it is enabled even for users who dont have that permission.', 0, '2003-12-10 13:44:07.768', 0, '2003-12-10 13:44:07.768', NULL, NULL, true);
INSERT INTO help_notes VALUES (337, 216, 'BUG:The alphabet slide should display  "No users" when an alphabet is clicked if there are not users whose last name start with the clicked alphabet.', 0, '2003-12-10 13:44:07.776', 0, '2003-12-10 13:44:07.776', NULL, NULL, true);
INSERT INTO help_notes VALUES (338, 217, 'HINT:  A user being added MUST be either an ACCOUNT CONTACT or an EMPLOYEE.  You CANNOT make a GENERAL CONTACT into a USER', 0, '2003-12-10 13:44:07.808', 0, '2003-12-10 13:44:07.808', NULL, NULL, true);
INSERT INTO help_notes VALUES (339, 217, 'Create New Contact form allows you to add a General Contact by selecting the Account Contact option but not selecting an Account. General Contact should not be allowed as users', 0, '2003-12-10 13:44:07.812', 0, '2003-12-10 13:44:07.812', NULL, NULL, true);
INSERT INTO help_notes VALUES (340, 217, 'Add User Page - Create New Contact - Exception - java.sql.SQLException: ERROR:  $2 referential integrity violation - key referenced from contact_type_levels not found in lookup_contact_types

	at org.aspcfs.modules.contacts.base.Contact.insert(Unknown Source)', 0, '2003-12-10 13:44:07.816', 0, '2003-12-10 13:44:07.816', NULL, NULL, true);
INSERT INTO help_notes VALUES (341, 217, 'USABILITY: User being added (or activated) from a future starting date (bpk)', 0, '2003-12-10 13:44:07.82', 0, '2003-12-10 13:44:07.82', NULL, NULL, true);
INSERT INTO help_notes VALUES (342, 217, 'USABILITY: Multiple Contacts my be required (one for home, work, etc) Only 1 contact seems to be allowed now(bpk) ', 0, '2003-12-10 13:44:07.824', 0, '2003-12-10 13:44:07.824', NULL, NULL, true);
INSERT INTO help_notes VALUES (343, 218, 'FEATURE: Generate New Password - nice to have link to change the email address.', 0, '2003-12-10 13:44:07.847', 0, '2003-12-10 13:44:07.847', NULL, NULL, true);
INSERT INTO help_notes VALUES (344, 218, 'USABILITY/BUG: An invalid date can be typed in the date field (e.g., 14/41/04), though the date gets adjusted to a valid date, it does not provide any warning (bpk)', 0, '2003-12-10 13:44:07.851', 0, '2003-12-10 13:44:07.851', NULL, NULL, true);
INSERT INTO help_notes VALUES (345, 223, 'DONE:', 0, '2003-12-10 13:44:07.934', 0, '2003-12-10 13:44:07.934', NULL, NULL, true);
INSERT INTO help_notes VALUES (346, 223, 'BUG:In the select options even when the ''View Details'' is selected, the user is lead to the ''Modify'' screen which is editable.', 0, '2003-12-10 13:44:07.938', 0, '2003-12-10 13:44:07.938', NULL, NULL, true);
INSERT INTO help_notes VALUES (347, 223, 'BUG:Trying to delete a role provokes an exception. The resulting page states that an error has occured and exception is "java.sql.SQLException: Parameter index out of range."', 0, '2003-12-10 13:44:07.941', 0, '2003-12-10 13:44:07.941', NULL, NULL, true);
INSERT INTO help_notes VALUES (348, 223, 'USABILITY: A change in role privileges should ideally be followed by a message to the affected users (bpk)', 0, '2003-12-10 13:44:07.945', 0, '2003-12-10 13:44:07.945', NULL, NULL, true);
INSERT INTO help_notes VALUES (349, 223, 'BUG:the slide rule(A-Z) produces incorrect results when no records exist starting with the character that was clicked(bpk)', 0, '2003-12-10 13:44:07.948', 0, '2003-12-10 13:44:07.948', NULL, NULL, true);
INSERT INTO help_notes VALUES (350, 223, 'USABILITY: It may be nice to see the users assigned to each role. This may be useful when role permissions need to be modified and the administrator needs to be aware of the users who would be affected by a change in user privileges (bpk)', 0, '2003-12-10 13:44:07.952', 0, '2003-12-10 13:44:07.952', NULL, NULL, true);
INSERT INTO help_notes VALUES (351, 224, 'DONE:', 0, '2003-12-10 13:44:07.965', 0, '2003-12-10 13:44:07.965', NULL, NULL, true);
INSERT INTO help_notes VALUES (352, 224, 'FEATURE:  While adding a new Role, have a "Check All" for permissions across modules as well as for columns withing modules', 0, '2003-12-10 13:44:07.969', 0, '2003-12-10 13:44:07.969', NULL, NULL, true);
INSERT INTO help_notes VALUES (353, 224, 'BUG: Change the disabled checkboxes to "--" like on the modify role page', 0, '2003-12-10 13:44:07.976', 0, '2003-12-10 13:44:07.976', NULL, NULL, true);
INSERT INTO help_notes VALUES (354, 225, 'Under "Tickets", there are two entries for "Reports". UPDATE: The reports permission for tickets was not in the build script for permissions(permissions.xml). Some how it got added twice on ds21.', 0, '2003-12-10 13:44:07.99', 0, '2003-12-10 13:44:07.99', NULL, NULL, true);
INSERT INTO help_notes VALUES (355, 225, 'FEATURE: If the user does not have permission to ''Re-assign'', the update button in Re-assign is hidden. It might be a better idea to provide a label actually informing the user about the lack of permissions. Same problem in ''Personel Information'' edit option.', 0, '2003-12-10 13:44:07.995', 0, '2003-12-10 13:44:07.995', NULL, NULL, true);
INSERT INTO help_notes VALUES (356, 225, 'BUG:When permission for ''My Profile'' is revoked, the display in the welcome page is distorted with multiple frames and same content in multiple frames.', 0, '2003-12-10 13:44:07.998', 0, '2003-12-10 13:44:07.998', NULL, NULL, true);
INSERT INTO help_notes VALUES (357, 225, 'BUG: When the permission for ADD in ''My Home Page''/''My Tasks'' is revoked and the user clicks on the ''My Tasks'' button in the ''My Home Page'' module, a warning script pops up warning about page errors.', 0, '2003-12-10 13:44:08.003', 0, '2003-12-10 13:44:08.003', NULL, NULL, true);
INSERT INTO help_notes VALUES (358, 225, 'BUG: When the permission for ''My Home Page''/''View Performance Dashboard '' has been revoked and the user clicks on the ''Welcome'' tab in the ''My Homepage'' module, this produces an error. ', 0, '2003-12-10 13:44:08.007', 0, '2003-12-10 13:44:08.007', NULL, NULL, true);
INSERT INTO help_notes VALUES (359, 225, 'BUG: When permission for ''Pipeline Mgmt''/''Opportunity Records'' Access/view is revoked, clicking on ''View Components'' in Pipeline management produces a permission error. Suggestion: Could remove the ''View Components'' tab.', 0, '2003-12-10 13:44:08.011', 0, '2003-12-10 13:44:08.011', NULL, NULL, true);
INSERT INTO help_notes VALUES (360, 225, 'FEATURE:''Campaign Manager'' could be renamed as ''Communication manager''. The names are different here and in the menu.', 0, '2003-12-10 13:44:08.015', 0, '2003-12-10 13:44:08.015', NULL, NULL, true);
INSERT INTO help_notes VALUES (361, 225, 'FEATURE: When the communication manager is selected from the menu, a list of possible options (5.....example dashboard, build groups..etc are shown to the user). When certain permission are revoed, clicking on either of the options returns an error page. It might be better to not show these options at all to the user if he/she does not have permissions.', 0, '2003-12-10 13:44:08.019', 0, '2003-12-10 13:44:08.019', NULL, NULL, true);
INSERT INTO help_notes VALUES (362, 225, 'BUG:When permission for Access/view in ''Account Management''/''Accounts Records'' has been revoked, pressing on the Account Management tab, shows an error page with the permission denied method. Might be a better idea to redirect to one of the other available options (Ex: add account, Reports ...etc). Similar case with tickets module.', 0, '2003-12-10 13:44:08.023', 0, '2003-12-10 13:44:08.023', NULL, NULL, true);
INSERT INTO help_notes VALUES (363, 225, 'BUG: There are two groups of check boxes to control the ''Reports'' in ''Tickets'' module when actually only one is needed. ', 0, '2003-12-10 13:44:08.027', 0, '2003-12-10 13:44:08.027', NULL, NULL, true);
INSERT INTO help_notes VALUES (364, 225, 'BUG:Even when the ''view'' permissions to the ''Help'' system are removed, the help icon still shows up at the top pf the screen.', 0, '2003-12-10 13:44:08.031', 0, '2003-12-10 13:44:08.031', NULL, NULL, true);
INSERT INTO help_notes VALUES (365, 225, 'BUG:The access, view and control of the QA system, seem to be based on the Edit option leaving the other options as dummies.', 0, '2003-12-10 13:44:08.036', 0, '2003-12-10 13:44:08.036', NULL, NULL, true);
INSERT INTO help_notes VALUES (366, 225, 'FEATURE:having "Add" and "Edit" permissions without "view" is immaterial, an errroneas permission set that has "add" and "edit" enabled and "view" disabled prevents the user from adding and editing since the link is unavailable to the user. A feature that automatically enables "view" when "add, edit or delete" is chosen would be appropriate(bpk)', 0, '2003-12-10 13:44:08.04', 0, '2003-12-10 13:44:08.04', NULL, NULL, true);
INSERT INTO help_notes VALUES (367, 225, 'BUG:"System Configuration" is editable even if "edit" is disabled for software developer (bpk)', 0, '2003-12-10 13:44:08.044', 0, '2003-12-10 13:44:08.044', NULL, NULL, true);
INSERT INTO help_notes VALUES (368, 225, 'BUG: Custom folders are editable (new groups can be added and deleted) even when "edit" is disabled, the "up" and "down" keys behave appropriatly though(bpk)', 0, '2003-12-10 13:44:08.047', 0, '2003-12-10 13:44:08.047', NULL, NULL, true);
INSERT INTO help_notes VALUES (369, 226, 'DONE:', 0, '2003-12-10 13:44:08.075', 0, '2003-12-10 13:44:08.075', NULL, NULL, true);
INSERT INTO help_notes VALUES (370, 226, 'admin > Setup > Configure Modules > Clicking on any of the modules has the same QA window for all the modules', 0, '2003-12-10 13:44:08.079', 0, '2003-12-10 13:44:08.079', NULL, NULL, true);
INSERT INTO help_notes VALUES (371, 227, 'DONE:', 0, '2003-12-10 13:44:08.108', 0, '2003-12-10 13:44:08.108', NULL, NULL, true);
INSERT INTO help_notes VALUES (372, 227, 'BUG: Categories does not have a separate permission, it is linked to folders', 0, '2003-12-10 13:44:08.111', 0, '2003-12-10 13:44:08.111', NULL, NULL, true);
INSERT INTO help_notes VALUES (373, 227, 'BUG: Clicking on object events  displays a blank page with a breif message informing an error(bpk)', 0, '2003-12-10 13:44:08.115', 0, '2003-12-10 13:44:08.115', NULL, NULL, true);
INSERT INTO help_notes VALUES (374, 228, 'When a Contact Type is added to a FULL list, it cannot be seen till you SCROLL to the end of the list.', 0, '2003-12-10 13:44:08.128', 0, '2003-12-10 13:44:08.128', NULL, NULL, true);
INSERT INTO help_notes VALUES (375, 228, 'The is no check for duplicates when entering contact types.', 0, '2003-12-10 13:44:08.133', 0, '2003-12-10 13:44:08.133', NULL, NULL, true);
INSERT INTO help_notes VALUES (376, 228, 'If  you select multiple contact types to DELETE only the LAST ONE is Deleted', 0, '2003-12-10 13:44:08.136', 0, '2003-12-10 13:44:08.136', NULL, NULL, true);
INSERT INTO help_notes VALUES (377, 228, 'IE BUG: The Up and Down buttons throw errors if no item is selected ! ', 0, '2003-12-10 13:44:08.141', 0, '2003-12-10 13:44:08.141', NULL, NULL, true);
INSERT INTO help_notes VALUES (378, 228, 'BUG: The list allows multiple items to be selected but only the first item among those selected is deleted(bpk)', 0, '2003-12-10 13:44:08.145', 0, '2003-12-10 13:44:08.145', NULL, NULL, true);
INSERT INTO help_notes VALUES (379, 228, 'USABILITY: The "up"and "down" buttons are redundant(bpk)', 0, '2003-12-10 13:44:08.149', 0, '2003-12-10 13:44:08.149', NULL, NULL, true);
INSERT INTO help_notes VALUES (380, 233, 'Custom Date only shows 2 digit year - calculation is on  4 digit year OK.  Checked  2000 to 2004 and 2100 - 2104 got data and no data respectively but both show 00 and 04 as two digit year', 0, '2003-12-10 13:44:08.208', 0, '2003-12-10 13:44:08.208', NULL, NULL, true);
INSERT INTO help_notes VALUES (381, 233, 'DONE:', 0, '2003-12-10 13:44:08.213', 0, '2003-12-10 13:44:08.213', NULL, NULL, true);
INSERT INTO help_notes VALUES (382, 233, 'USABLITY: For the custom date range, i can go into the future either for the start date or the end date. Also start date can be after the end date.', 0, '2003-12-10 13:44:08.216', 0, '2003-12-10 13:44:08.216', NULL, NULL, true);
INSERT INTO help_notes VALUES (383, 233, 'USABILITY: When a an end date less the start date is provided, the user should be given a message rather than just  automatically re-adjusting to current date(bpk)', 0, '2003-12-10 13:44:08.221', 0, '2003-12-10 13:44:08.221', NULL, NULL, true);
INSERT INTO help_notes VALUES (384, 236, 'DONE:', 0, '2003-12-10 13:44:08.26', 0, '2003-12-10 13:44:08.26', NULL, NULL, true);
INSERT INTO help_notes VALUES (385, 237, 'The BACK button on this page creates additional folders.', 0, '2003-12-10 13:44:08.289', 0, '2003-12-10 13:44:08.289', NULL, NULL, true);
INSERT INTO help_notes VALUES (386, 237, 'NICE: a FLAG to indicate if the folder is set for "single/multiple" records ', 0, '2003-12-10 13:44:08.293', 0, '2003-12-10 13:44:08.293', NULL, NULL, true);
INSERT INTO help_notes VALUES (387, 238, 'BUG: Allows field length to be negative (bpk)', 0, '2003-12-10 13:44:08.302', 0, '2003-12-10 13:44:08.302', NULL, NULL, true);
INSERT INTO help_notes VALUES (388, 239, 'DONE:  works fine', 0, '2003-12-10 13:44:08.31', 0, '2003-12-10 13:44:08.31', NULL, NULL, true);
INSERT INTO help_notes VALUES (389, 240, 'DONE: Works OK', 0, '2003-12-10 13:44:08.319', 0, '2003-12-10 13:44:08.319', NULL, NULL, true);
INSERT INTO help_notes VALUES (390, 241, 'I don''t understand what or why this page is supposed to do.   Edit button is not active and all the "ticket" boxes are checked.  Update as a interesting display but I couldn''t figure it our.  The insert list is blank with nothing to do.', 0, '2003-12-10 13:44:08.335', 0, '2003-12-10 13:44:08.335', NULL, NULL, true);
INSERT INTO help_notes VALUES (391, 242, 'DONE:', 0, '2003-12-10 13:44:08.353', 0, '2003-12-10 13:44:08.353', NULL, NULL, true);
INSERT INTO help_notes VALUES (392, 243, 'DONE:', 0, '2003-12-10 13:44:08.362', 0, '2003-12-10 13:44:08.362', NULL, NULL, true);
INSERT INTO help_notes VALUES (393, 245, 'USABILITY: When no events are scheduled a message needs to shown saying the same', 0, '2003-12-10 13:44:08.384', 0, '2003-12-10 13:44:08.384', NULL, NULL, true);
INSERT INTO help_notes VALUES (394, 247, 'Notes under contact types apply to ALL lists about (Duplicates, Adding, & Deleting).', 0, '2003-12-10 13:44:08.405', 0, '2003-12-10 13:44:08.405', NULL, NULL, true);
INSERT INTO help_notes VALUES (395, 247, 'NONE of the CHANGES to ANY LIST showed up when I checked in General Contacts. UPDATE: It is possible that you could be making changes to Account Contact Types', 0, '2003-12-10 13:44:08.408', 0, '2003-12-10 13:44:08.408', NULL, NULL, true);
INSERT INTO help_notes VALUES (396, 248, 'FEATURE:  (1) Column Listing of Records - N/page & (2) Folder Records export to spreadsheet.', 0, '2003-12-10 13:44:08.447', 0, '2003-12-10 13:44:08.447', NULL, NULL, true);
INSERT INTO help_notes VALUES (397, 248, 'DONE:', 0, '2003-12-10 13:44:08.451', 0, '2003-12-10 13:44:08.451', NULL, NULL, true);
INSERT INTO help_notes VALUES (398, 248, 'NOTE:  I find the term GROUP confusing definition easy to forget.', 0, '2003-12-10 13:44:08.455', 0, '2003-12-10 13:44:08.455', NULL, NULL, true);
INSERT INTO help_notes VALUES (399, 248, 'USABILITY: A new "Group" is shown only at the end of the page, hence, when it is outside the view area, there is  no immediate visual imact of the successful addition unless the user scrolls down(bpk)', 0, '2003-12-10 13:44:08.459', 0, '2003-12-10 13:44:08.459', NULL, NULL, true);
INSERT INTO help_notes VALUES (400, 248, 'USABILITY/BUG: The Up/Down links allow custom fields to be moved across groups (This may cause unintentional errors)(bpk)', 0, '2003-12-10 13:44:08.463', 0, '2003-12-10 13:44:08.463', NULL, NULL, true);
INSERT INTO help_notes VALUES (401, 250, 'USABILITY: Trying to save the items in a level after editing them should not ask for a confirmation', 0, '2003-12-10 13:44:08.509', 0, '2003-12-10 13:44:08.509', NULL, NULL, true);
INSERT INTO help_notes VALUES (402, 250, 'BUG: The first time you go to DRAFT categories you cannot navigate all of the category levels', 0, '2003-12-10 13:44:08.513', 0, '2003-12-10 13:44:08.513', NULL, NULL, true);
INSERT INTO help_notes VALUES (403, 250, 'JAVASCRIPT:Draft Categories->Edit Categories->Click on rename button without selecting any item. This pops an error:itemList[].description is not an object', 0, '2003-12-10 13:44:08.518', 0, '2003-12-10 13:44:08.518', NULL, NULL, true);
INSERT INTO help_notes VALUES (404, 250, 'BUG:Categories->Draft Categories(Level 1)->Edit->Click on an item to rename->click Rename->now select a differenct item->Edit the description in the text box on the left->Add-> this edits the current item instead of the original one.', 0, '2003-12-10 13:44:08.522', 0, '2003-12-10 13:44:08.522', NULL, NULL, true);
INSERT INTO help_notes VALUES (405, 253, 'USAGE: When I create a new User, I can select a previous date for his account to expire. Is this valid? Also when i create a new account and view the details, it shows me that that the new user i just created had a last login on - ''The time i created this account''. Is this valid?', 0, '2003-12-10 13:44:08.542', 0, '2003-12-10 13:44:08.542', NULL, NULL, true);
INSERT INTO help_notes VALUES (406, 253, 'Error: when I create a new role which has none of the privileges available in the system, and then i create a new user with that role. If i login as that user. I get a page where i can see the user name on the top but get an error in place of the actual menu.', 0, '2003-12-10 13:44:08.547', 0, '2003-12-10 13:44:08.547', NULL, NULL, true);
INSERT INTO help_notes VALUES (407, 254, 'Can''t see which contact this user is associated with.', 0, '2003-12-10 13:44:08.571', 0, '2003-12-10 13:44:08.571', NULL, NULL, true);
INSERT INTO help_notes VALUES (408, 254, 'USABILITY:An account can be created which can expire backwards in time. ', 0, '2003-12-10 13:44:08.575', 0, '2003-12-10 13:44:08.575', NULL, NULL, true);
INSERT INTO help_notes VALUES (409, 254, 'USABILITY: Clicking on "Employee Link" forwards you to "My Home Page". Does not feel appropriate.(bpk)', 0, '2003-12-10 13:44:08.578', 0, '2003-12-10 13:44:08.578', NULL, NULL, true);
INSERT INTO help_notes VALUES (410, 254, 'BUG: When "Details->Modify" user details is chosen and the user goes to another tab and then clicks on details again, only the non-editable view is provided (bpk)', 0, '2003-12-10 13:44:08.585', 0, '2003-12-10 13:44:08.585', NULL, NULL, true);
INSERT INTO help_notes VALUES (411, 255, 'DISPLAY: when you set up permissions for general contacts with just the seach permissions and not the add permission, it shows redundant seperators ''|''. ', 0, '2003-12-10 13:44:08.596', 0, '2003-12-10 13:44:08.596', NULL, NULL, true);
INSERT INTO help_notes VALUES (412, 255, 'QUESTION: Should every role in the system start with a minimal set of permissions under each module ? Otherwise the display when the user with such a role logs in is screwed up.', 0, '2003-12-10 13:44:08.599', 0, '2003-12-10 13:44:08.599', NULL, NULL, true);
INSERT INTO help_notes VALUES (413, 255, 'BUG: Giving a role, access to just the home page module and the general contacts module with the least set of permissions causes a bug. When you log into the system as an user with that particular role, it will show the two modules, but clicking on the General contacts will take you back to the home page module.', 0, '2003-12-10 13:44:08.604', 0, '2003-12-10 13:44:08.604', NULL, NULL, true);
INSERT INTO help_notes VALUES (414, 258, 'DISPLAY:When I select a contact whose name is really long say - ''Ananth Balasubramanyam'', the [Change Contact] and [Clear Contact] are misplaced!', 0, '2003-12-10 13:44:08.625', 0, '2003-12-10 13:44:08.625', NULL, NULL, true);
INSERT INTO help_notes VALUES (415, 259, 'FEATURE: Reorder the blocks on the page moving "Usage" below "Global Parameters"', 0, '2003-12-10 13:44:08.654', 0, '2003-12-10 13:44:08.654', NULL, NULL, true);
INSERT INTO help_notes VALUES (416, 259, 'FEATURE: Blue Line Menu -- ( Users, Roles, Usage )', 0, '2003-12-10 13:44:08.658', 0, '2003-12-10 13:44:08.658', NULL, NULL, true);
INSERT INTO help_notes VALUES (417, 259, 'Exception when (roberts) chooses Modify Users : No such attribute c.type_id', 0, '2003-12-10 13:44:08.896', 0, '2003-12-10 13:44:08.896', NULL, NULL, true);
INSERT INTO help_notes VALUES (418, 259, 'BUG: On clicking "Manage Roles" the browsing sequence shows "Setup -> View Roles"(bpk)', 0, '2003-12-10 13:44:08.916', 0, '2003-12-10 13:44:08.916', NULL, NULL, true);
INSERT INTO help_notes VALUES (419, 259, 'USABILITY/BUG: On Clicking "Check system resource" the navigation sequence shows "Setup -> usage"(bpk)', 0, '2003-12-10 13:44:08.935', 0, '2003-12-10 13:44:08.935', NULL, NULL, true);
INSERT INTO help_notes VALUES (420, 259, 'APPEARANCE: "s" and "r" are lower case in "Check system resources" and is hence inconsistent compared to the other links(bpk)', 0, '2003-12-10 13:44:08.95', 0, '2003-12-10 13:44:08.95', NULL, NULL, true);
INSERT INTO help_notes VALUES (421, 259, 'USABILITY: If ''Session Timeout " remains the only parameter that can be set, the nomenclature of the link "Global Parameters and Server Configuration" should be changed to indicate that more clearly (bpk)', 0, '2003-12-10 13:44:08.961', 0, '2003-12-10 13:44:08.961', NULL, NULL, true);


--
-- Data for TOC entry 654 (OID 33561)
-- Name: help_tips; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO help_tips VALUES (1, 1, 'Assign due dates for tasks so that you can be alerted', 0, '2003-12-10 13:44:03.297', 0, '2003-12-10 13:44:03.297', true);
INSERT INTO help_tips VALUES (2, 21, 'USABILITY: If my Task list has,  say 10 items out of which 6 are already complete and I wish to delete all of them. I will have delete each one. Could we have an option of deleting all at once? ', 0, '2003-12-10 13:44:03.958', 0, '2003-12-10 13:44:03.958', true);
INSERT INTO help_tips VALUES (3, 201, 'Make sure to resolve your tickets as soon as possible so they don''t appear here!', 0, '2003-12-10 13:44:07.389', 0, '2003-12-10 13:44:07.389', true);


--
-- Data for TOC entry 655 (OID 33586)
-- Name: sync_client; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 656 (OID 33595)
-- Name: sync_system; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO sync_system VALUES (1, 'Vport Telemarketing', true);
INSERT INTO sync_system VALUES (2, 'Land Mark: Auto Guide PocketPC', true);
INSERT INTO sync_system VALUES (3, 'Street Smart Speakers: Web Portal', true);
INSERT INTO sync_system VALUES (4, 'CFSHttpXMLWriter', true);
INSERT INTO sync_system VALUES (5, 'Fluency', true);


--
-- Data for TOC entry 657 (OID 33603)
-- Name: sync_table; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO sync_table VALUES (1, 1, 'ticket', 'org.aspcfs.modules.troubletickets.base.Ticket', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, 'id');
INSERT INTO sync_table VALUES (2, 2, 'syncClient', 'org.aspcfs.modules.service.base.SyncClient', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, 2, false, NULL);
INSERT INTO sync_table VALUES (3, 2, 'user', 'org.aspcfs.modules.admin.base.User', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, 4, false, NULL);
INSERT INTO sync_table VALUES (4, 2, 'account', 'org.aspcfs.modules.accounts.base.Organization', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, 5, false, NULL);
INSERT INTO sync_table VALUES (5, 2, 'accountInventory', 'org.aspcfs.modules.media.autoguide.base.Inventory', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, 6, false, NULL);
INSERT INTO sync_table VALUES (6, 2, 'inventoryOption', 'org.aspcfs.modules.media.autoguide.base.InventoryOption', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, 8, false, NULL);
INSERT INTO sync_table VALUES (7, 2, 'adRun', 'org.aspcfs.modules.media.autoguide.base.AdRun', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, 10, false, NULL);
INSERT INTO sync_table VALUES (8, 2, 'tableList', 'org.aspcfs.modules.service.base.SyncTableList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, 12, false, NULL);
INSERT INTO sync_table VALUES (9, 2, 'status_master', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, 14, false, NULL);
INSERT INTO sync_table VALUES (10, 2, 'system', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, 16, false, NULL);
INSERT INTO sync_table VALUES (11, 2, 'userList', 'org.aspcfs.modules.admin.base.UserList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE TABLE users ( user_id              int NOT NULL, record_status_id     int NULL, user_name            nvarchar(20) NULL, pin                  nvarchar(20) NULL, modified             datetime NULL, PRIMARY KEY (user_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 50, true, NULL);
INSERT INTO sync_table VALUES (12, 2, 'XIF18users', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF18users ON users ( record_status_id )', 60, false, NULL);
INSERT INTO sync_table VALUES (13, 2, 'makeList', 'org.aspcfs.modules.media.autoguide.base.MakeList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE TABLE make ( make_id              int NOT NULL, make_name            nvarchar(20) NULL, record_status_id     int NULL, entered              datetime NULL, modified             datetime NULL, enteredby            int NULL, modifiedby           int NULL, PRIMARY KEY (make_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 70, true, NULL);
INSERT INTO sync_table VALUES (14, 2, 'XIF2make', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF2make ON make ( record_status_id )', 80, false, NULL);
INSERT INTO sync_table VALUES (15, 2, 'modelList', 'org.aspcfs.modules.media.autoguide.base.ModelList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE TABLE model ( model_id             int NOT NULL, make_id              int NULL, record_status_id     int NULL, model_name           nvarchar(40) NULL, entered              datetime NULL, modified             datetime NULL, enteredby            int NULL, modifiedby           int NULL, PRIMARY KEY (model_id), FOREIGN KEY (make_id) REFERENCES make (make_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 100, true, NULL);
INSERT INTO sync_table VALUES (16, 2, 'XIF3model', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF3model ON model ( record_status_id )', 110, false, NULL);
INSERT INTO sync_table VALUES (17, 2, 'XIF5model', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF5model ON model ( make_id )', 120, false, NULL);
INSERT INTO sync_table VALUES (18, 2, 'vehicleList', 'org.aspcfs.modules.media.autoguide.base.VehicleList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE TABLE vehicle ( year                 nvarchar(4) NOT NULL, vehicle_id           int NOT NULL, model_id             int NULL, make_id              int NULL, record_status_id     int NULL, entered              datetime NULL, modified             datetime NULL, enteredby            int NULL, modifiedby           int NULL, PRIMARY KEY (vehicle_id), FOREIGN KEY (model_id) REFERENCES model (model_id), FOREIGN KEY (make_id) REFERENCES make (make_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 130, true, NULL);
INSERT INTO sync_table VALUES (19, 2, 'XIF30vehicle', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF30vehicle ON vehicle ( make_id )', 140, false, NULL);
INSERT INTO sync_table VALUES (20, 2, 'XIF31vehicle', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF31vehicle ON vehicle ( model_id )', 150, false, NULL);
INSERT INTO sync_table VALUES (21, 2, 'XIF4vehicle', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF4vehicle ON vehicle ( record_status_id )', 160, false, NULL);
INSERT INTO sync_table VALUES (22, 2, 'accountList', 'org.aspcfs.modules.accounts.base.OrganizationList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE TABLE account ( account_id           int NOT NULL, account_name         nvarchar(80) NULL, record_status_id     int NULL, address              nvarchar(80) NULL, modified             datetime NULL, city                 nvarchar(80) NULL, state                nvarchar(2) NULL, notes                nvarchar(255) NULL, zip                  nvarchar(11) NULL, phone                nvarchar(20) NULL, contact              nvarchar(20) NULL, dmv_number           nvarchar(20) NULL, owner_id             int NULL, entered              datetime NULL, enteredby            int NULL, modifiedby           int NULL, PRIMARY KEY (account_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 170, true, NULL);
INSERT INTO sync_table VALUES (23, 2, 'XIF16account', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF16account ON account ( record_status_id )', 180, false, NULL);
INSERT INTO sync_table VALUES (24, 2, 'accountInventoryList', 'org.aspcfs.modules.media.autoguide.base.InventoryList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE TABLE account_inventory ( inventory_id         int NOT NULL, vin                  nvarchar(20) NULL, vehicle_id           int NULL, account_id           int NULL, mileage              nvarchar(20) NULL, enteredby            int NULL, new                  bit, condition            nvarchar(20) NULL, comments             nvarchar(255) NULL, stock_no             nvarchar(20) NULL, ext_color            nvarchar(20) NULL, int_color            nvarchar(20) NULL, style                nvarchar(40) NULL, invoice_price        money NULL, selling_price        money NULL, selling_price_text		nvarchar(100) NULL, modified             datetime NULL, sold                 int NULL, modifiedby           int NULL, record_status_id     int NULL, entered              datetime NULL, PRIMARY KEY (inventory_id), FOREIGN KEY (account_id) REFERENCES account (account_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 190, true, NULL);
INSERT INTO sync_table VALUES (25, 2, 'XIF10account_inventory', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF10account_inventory ON account_inventory ( record_status_id )', 200, false, NULL);
INSERT INTO sync_table VALUES (26, 2, 'XIF10account_inventory', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF11account_inventory ON account_inventory ( modifiedby )', 210, false, NULL);
INSERT INTO sync_table VALUES (27, 2, 'XIF19account_inventory', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF19account_inventory ON account_inventory ( account_id )', 220, false, NULL);
INSERT INTO sync_table VALUES (28, 2, 'XIF35account_inventory', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF35account_inventory ON account_inventory ( vehicle_id )', 230, false, NULL);
INSERT INTO sync_table VALUES (29, 2, 'optionList', 'org.aspcfs.modules.media.autoguide.base.OptionList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE TABLE options ( option_id            int NOT NULL, option_name          nvarchar(20) NULL, record_status_id     int NULL, record_status_date   datetime NULL, PRIMARY KEY (option_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 330, true, NULL);
INSERT INTO sync_table VALUES (30, 2, 'XIF24options', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF24options ON options ( record_status_id )', 340, false, NULL);
INSERT INTO sync_table VALUES (31, 2, 'inventoryOptionList', 'org.aspcfs.modules.media.autoguide.base.InventoryOptionList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE TABLE inventory_options ( inventory_id         int NOT NULL, option_id            int NOT NULL, record_status_id     int NULL, modified             datetime NULL, PRIMARY KEY (option_id, inventory_id), FOREIGN KEY (inventory_id) REFERENCES account_inventory (inventory_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id), FOREIGN KEY (option_id) REFERENCES options (option_id) )', 350, true, NULL);
INSERT INTO sync_table VALUES (32, 2, 'XIF25inventory_options', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF25inventory_options ON inventory_options ( option_id )', 360, false, NULL);
INSERT INTO sync_table VALUES (33, 2, 'XIF27inventory_options', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF27inventory_options ON inventory_options ( record_status_id )', 370, false, NULL);
INSERT INTO sync_table VALUES (34, 2, 'XIF33inventory_options', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF33inventory_options ON inventory_options ( inventory_id )', 380, false, NULL);
INSERT INTO sync_table VALUES (35, 2, 'adTypeList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE TABLE ad_type ( ad_type_id           int NOT NULL, ad_type_name         nvarchar(20) NULL, PRIMARY KEY (ad_type_id) )', 385, true, NULL);
INSERT INTO sync_table VALUES (36, 2, 'adRunList', 'org.aspcfs.modules.media.autoguide.base.AdRunList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE TABLE ad_run ( ad_run_id            int NOT NULL, record_status_id     int NULL, inventory_id         int NULL, ad_type_id           int NULL, ad_run_date          datetime NULL, has_picture          int NULL, modified             datetime NULL, entered              datetime NULL, modifiedby           int NULL, enteredby            int NULL, PRIMARY KEY (ad_run_id), FOREIGN KEY (inventory_id) REFERENCES account_inventory (inventory_id), FOREIGN KEY (ad_type_id) REFERENCES ad_type (ad_type_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 390, true, NULL);
INSERT INTO sync_table VALUES (37, 2, 'XIF22ad_run', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF22ad_run ON ad_run ( record_status_id )', 400, false, NULL);
INSERT INTO sync_table VALUES (38, 2, 'XIF36ad_run', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF36ad_run ON ad_run ( ad_type_id )', 402, false, NULL);
INSERT INTO sync_table VALUES (39, 2, 'XIF37ad_run', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF37ad_run ON ad_run ( inventory_id )', 404, false, NULL);
INSERT INTO sync_table VALUES (40, 2, 'inventory_picture', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE TABLE inventory_picture ( picture_name         nvarchar(20) NOT NULL, inventory_id         int NOT NULL, record_status_id     int NULL, entered              datetime NULL, modified             datetime NULL, modifiedby           int NULL, enteredby            int NULL, PRIMARY KEY (picture_name, inventory_id), FOREIGN KEY (inventory_id) REFERENCES account_inventory (inventory_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )', 410, false, NULL);
INSERT INTO sync_table VALUES (41, 2, 'XIF23inventory_picture', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF23inventory_picture ON inventory_picture ( record_status_id )', 420, false, NULL);
INSERT INTO sync_table VALUES (42, 2, 'XIF32inventory_picture', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF32inventory_picture ON inventory_picture ( inventory_id )', 430, false, NULL);
INSERT INTO sync_table VALUES (43, 2, 'preferences', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE TABLE preferences ( user_id              int NOT NULL, record_status_id     int NULL, modified             datetime NULL, PRIMARY KEY (user_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id), FOREIGN KEY (user_id) REFERENCES users (user_id) )', 440, false, NULL);
INSERT INTO sync_table VALUES (44, 2, 'XIF29preferences', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF29preferences ON preferences ( record_status_id )', 450, false, NULL);
INSERT INTO sync_table VALUES (45, 2, 'user_account', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE TABLE user_account ( user_id              int NOT NULL, account_id           int NOT NULL, record_status_id     int NULL, modified             datetime NULL, PRIMARY KEY (user_id, account_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id), FOREIGN KEY (account_id) REFERENCES account (account_id), FOREIGN KEY (user_id) REFERENCES users (user_id) )', 460, false, NULL);
INSERT INTO sync_table VALUES (46, 2, 'XIF14user_account', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF14user_account ON user_account ( user_id )', 470, false, NULL);
INSERT INTO sync_table VALUES (47, 2, 'XIF15user_account', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF15user_account ON user_account ( account_id )', 480, false, NULL);
INSERT INTO sync_table VALUES (48, 2, 'XIF17user_account', NULL, '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', 'CREATE INDEX XIF17user_account ON user_account ( record_status_id )', 490, false, NULL);
INSERT INTO sync_table VALUES (49, 2, 'deleteInventoryCache', 'org.aspcfs.modules.media.autoguide.actions.DeleteInventoryCache', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, 500, false, NULL);
INSERT INTO sync_table VALUES (50, 4, 'lookupIndustry', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (51, 4, 'lookupIndustryList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (52, 4, 'systemPrefs', 'org.aspcfs.utils.web.CustomLookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (53, 4, 'systemModules', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (54, 4, 'systemModulesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (55, 4, 'lookupContactTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (56, 4, 'lookupContactTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (57, 4, 'lookupAccountTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (58, 4, 'lookupAccountTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (59, 4, 'lookupDepartment', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (60, 4, 'lookupDepartmentList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (61, 4, 'lookupOrgAddressTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (62, 4, 'lookupOrgAddressTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (63, 4, 'lookupOrgEmailTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (64, 4, 'lookupOrgEmailTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (65, 4, 'lookupOrgPhoneTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (66, 4, 'lookupOrgPhoneTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (67, 4, 'lookupInstantMessengerTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (68, 4, 'lookupInstantMessengerTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (69, 4, 'lookupEmploymentTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (70, 4, 'lookupEmploymentTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (71, 4, 'lookupLocale', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (72, 4, 'lookupLocaleList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (73, 4, 'lookupContactAddressTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (74, 4, 'lookupContactAddressTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (75, 4, 'lookupContactEmailTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (76, 4, 'lookupContactEmailTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (77, 4, 'lookupContactPhoneTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (78, 4, 'lookupContactPhoneTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (79, 4, 'lookupStage', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (80, 4, 'lookupStageList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (81, 4, 'lookupDeliveryOptions', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (82, 4, 'lookupDeliveryOptionsList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (83, 4, 'lookupCallTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (84, 4, 'lookupCallTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (85, 4, 'ticketCategory', 'org.aspcfs.modules.troubletickets.base.TicketCategory', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (86, 4, 'ticketCategoryList', 'org.aspcfs.modules.troubletickets.base.TicketCategoryList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (87, 4, 'ticketSeverity', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (88, 4, 'ticketSeverityList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (89, 4, 'lookupTicketSource', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (90, 4, 'lookupTicketSourceList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (91, 4, 'ticketPriority', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (92, 4, 'ticketPriorityList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (93, 4, 'lookupRevenueTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (94, 4, 'lookupRevenueTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (95, 4, 'lookupRevenueDetailTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (96, 4, 'lookupRevenueDetailTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (97, 4, 'lookupSurveyTypes', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (98, 4, 'lookupSurveyTypesList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (99, 4, 'syncClient', 'org.aspcfs.modules.service.base.SyncClient', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (100, 4, 'user', 'org.aspcfs.modules.admin.base.User', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (101, 4, 'userList', 'org.aspcfs.modules.admin.base.UserList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (102, 4, 'contact', 'org.aspcfs.modules.contacts.base.Contact', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (103, 4, 'contactList', 'org.aspcfs.modules.contacts.base.ContactList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (104, 4, 'ticket', 'org.aspcfs.modules.troubletickets.base.Ticket', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, 'id');
INSERT INTO sync_table VALUES (105, 4, 'ticketList', 'org.aspcfs.modules.troubletickets.base.TicketList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (106, 4, 'account', 'org.aspcfs.modules.accounts.base.Organization', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (107, 4, 'accountList', 'org.aspcfs.modules.accounts.base.OrganizationList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (108, 4, 'role', 'org.aspcfs.modules.admin.base.Role', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (109, 4, 'roleList', 'org.aspcfs.modules.admin.base.RoleList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (110, 4, 'permissionCategory', 'org.aspcfs.modules.admin.base.PermissionCategory', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (111, 4, 'permissionCategoryList', 'org.aspcfs.modules.admin.base.PermissionCategoryList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (112, 4, 'permission', 'org.aspcfs.modules.admin.base.Permission', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (113, 4, 'permissionList', 'org.aspcfs.modules.admin.base.PermissionList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (114, 4, 'rolePermission', 'org.aspcfs.modules.admin.base.RolePermission', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (115, 4, 'rolePermissionList', 'org.aspcfs.modules.admin.base.RolePermissionList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (116, 4, 'opportunity', 'org.aspcfs.modules.pipeline.base.Opportunity', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (117, 4, 'opportunityList', 'org.aspcfs.modules.pipeline.base.OpportunityList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (118, 4, 'call', 'org.aspcfs.modules.contacts.base.Call', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (119, 4, 'callList', 'org.aspcfs.modules.contacts.base.CallList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (120, 4, 'customFieldCategory', 'org.aspcfs.modules.base.CustomFieldCategory', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (121, 4, 'customFieldCategoryList', 'org.aspcfs.modules.base.CustomFieldCategoryList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (122, 4, 'customFieldGroup', 'org.aspcfs.modules.base.CustomFieldGroup', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (123, 4, 'customFieldGroupList', 'org.aspcfs.modules.base.CustomFieldGroupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (124, 4, 'customField', 'org.aspcfs.modules.base.CustomField', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (125, 4, 'customFieldList', 'org.aspcfs.modules.base.CustomFieldList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (126, 4, 'customFieldLookup', 'org.aspcfs.utils.web.LookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (127, 4, 'customFieldLookupList', 'org.aspcfs.utils.web.LookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (128, 4, 'customFieldRecord', 'org.aspcfs.modules.base.CustomFieldRecord', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (129, 4, 'customFieldRecordList', 'org.aspcfs.modules.base.CustomFieldRecordList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (130, 4, 'contactEmailAddress', 'org.aspcfs.modules.contacts.base.ContactEmailAddress', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (131, 4, 'contactEmailAddressList', 'org.aspcfs.modules.contacts.base.ContactEmailAddressList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (132, 4, 'customFieldData', 'org.aspcfs.modules.base.CustomFieldData', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (133, 4, 'lookupProjectActivity', 'org.aspcfs.utils.web.CustomLookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (134, 4, 'lookupProjectActivityList', 'org.aspcfs.utils.web.CustomLookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (135, 4, 'lookupProjectIssues', 'org.aspcfs.utils.web.CustomLookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (136, 4, 'lookupProjectIssuesList', 'org.aspcfs.utils.web.CustomLookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (137, 4, 'lookupProjectLoe', 'org.aspcfs.utils.web.CustomLookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (138, 4, 'lookupProjectLoeList', 'org.aspcfs.utils.web.CustomLookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (139, 4, 'lookupProjectPriority', 'org.aspcfs.utils.web.CustomLookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (140, 4, 'lookupProjectPriorityList', 'org.aspcfs.utils.web.CustomLookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (141, 4, 'lookupProjectStatus', 'org.aspcfs.utils.web.CustomLookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (142, 4, 'lookupProjectStatusList', 'org.aspcfs.utils.web.CustomLookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (143, 4, 'project', 'com.zeroio.iteam.base.Project', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (144, 4, 'projectList', 'com.zeroio.iteam.base.ProjectList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (145, 4, 'requirement', 'com.zeroio.iteam.base.Requirement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (146, 4, 'requirementList', 'com.zeroio.iteam.base.RequirementList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (147, 4, 'assignment', 'com.zeroio.iteam.base.Assignment', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (148, 4, 'assignmentList', 'com.zeroio.iteam.base.AssignmentList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (149, 4, 'issue', 'com.zeroio.iteam.base.Issue', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (150, 4, 'issueList', 'com.zeroio.iteam.base.IssueList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (151, 4, 'issueReply', 'com.zeroio.iteam.base.IssueReply', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (152, 4, 'issueReplyList', 'com.zeroio.iteam.base.IssueReplyList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (153, 4, 'teamMember', 'com.zeroio.iteam.base.TeamMember', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (154, 4, 'fileItem', 'com.zeroio.iteam.base.FileItem', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (155, 4, 'fileItemList', 'com.zeroio.iteam.base.FileItemList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (156, 4, 'fileItemVersion', 'com.zeroio.iteam.base.FileItemVersion', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (157, 4, 'fileItemVersionList', 'com.zeroio.iteam.base.FileItemVersionList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (158, 4, 'fileDownloadLog', 'com.zeroio.iteam.base.FileDownloadLog', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (159, 4, 'contactAddress', 'org.aspcfs.modules.contacts.base.ContactAddress', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (160, 4, 'contactAddressList', 'org.aspcfs.modules.contacts.base.ContactAddressList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (161, 4, 'contactPhoneNumber', 'org.aspcfs.modules.contacts.base.ContactPhoneNumber', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (162, 4, 'contactPhoneNumberList', 'org.aspcfs.modules.contacts.base.ContactPhoneNumberList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (163, 4, 'organizationPhoneNumber', 'org.aspcfs.modules.accounts.base.OrganizationPhoneNumber', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (164, 4, 'organizationPhoneNumberList', 'org.aspcfs.modules.accounts.base.OrganizationPhoneNumberList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (165, 4, 'organizationEmailAddress', 'org.aspcfs.modules.accounts.base.OrganizationEmailAddress', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (166, 4, 'organizationEmailAddressList', 'org.aspcfs.modules.accounts.base.OrganizationEmailAddressList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (167, 4, 'organizationAddress', 'org.aspcfs.modules.accounts.base.OrganizationAddress', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (168, 4, 'organizationAddressList', 'org.aspcfs.modules.accounts.base.OrganizationAddressList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (169, 4, 'ticketLog', 'org.aspcfs.modules.troubletickets.base.TicketLog', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (170, 4, 'ticketLogList', 'org.aspcfs.modules.troubletickets.base.TicketLogList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (171, 4, 'message', 'org.aspcfs.modules.communications.base.Message', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (172, 4, 'messageList', 'org.aspcfs.modules.communications.base.MessageList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (173, 4, 'searchCriteriaElements', 'org.aspcfs.modules.communications.base.SearchCriteriaList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (174, 4, 'searchCriteriaElementsList', 'org.aspcfs.modules.communications.base.SearchCriteriaListList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (175, 4, 'savedCriteriaElement', 'org.aspcfs.modules.communications.base.SavedCriteriaElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (176, 4, 'searchFieldElement', 'org.aspcfs.utils.web.CustomLookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (177, 4, 'searchFieldElementList', 'org.aspcfs.utils.web.CustomLookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (178, 4, 'revenue', 'org.aspcfs.modules.accounts.base.Revenue', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (179, 4, 'revenueList', 'org.aspcfs.modules.accounts.base.RevenueList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (180, 4, 'campaign', 'org.aspcfs.modules.communications.base.Campaign', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (181, 4, 'campaignList', 'org.aspcfs.modules.communications.base.CampaignList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (182, 4, 'scheduledRecipient', 'org.aspcfs.modules.communications.base.ScheduledRecipient', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (183, 4, 'scheduledRecipientList', 'org.aspcfs.modules.communications.base.ScheduledRecipientList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (184, 4, 'accessLog', 'org.aspcfs.modules.admin.base.AccessLog', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (185, 4, 'accessLogList', 'org.aspcfs.modules.admin.base.AccessLogList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (186, 4, 'accountTypeLevels', 'org.aspcfs.modules.accounts.base.AccountTypeLevel', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (187, 4, 'fieldTypes', 'org.aspcfs.utils.web.CustomLookupElement', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (188, 4, 'fieldTypesList', 'org.aspcfs.utils.web.CustomLookupList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (189, 4, 'excludedRecipient', 'org.aspcfs.modules.communications.base.ExcludedRecipient', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (190, 4, 'campaignRun', 'org.aspcfs.modules.communications.base.CampaignRun', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (191, 4, 'campaignRunList', 'org.aspcfs.modules.communications.base.CampaignRunList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (192, 4, 'campaignListGroups', 'org.aspcfs.modules.communications.base.CampaignListGroup', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (193, 5, 'ticket', 'org.aspcfs.modules.troubletickets.base.Ticket', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, 'id');
INSERT INTO sync_table VALUES (194, 5, 'ticketCategory', 'org.aspcfs.modules.troubletickets.base.TicketCategory', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (195, 5, 'ticketCategoryList', 'org.aspcfs.modules.troubletickets.base.TicketCategoryList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (196, 5, 'syncClient', 'org.aspcfs.modules.service.base.SyncClient', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, 2, false, NULL);
INSERT INTO sync_table VALUES (197, 5, 'accountList', 'org.aspcfs.modules.accounts.base.OrganizationList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (198, 5, 'userList', 'org.aspcfs.modules.admin.base.UserList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);
INSERT INTO sync_table VALUES (199, 5, 'contactList', 'org.aspcfs.modules.contacts.base.ContactList', '2003-12-10 13:43:45.616', '2003-12-10 13:43:45.616', NULL, -1, false, NULL);


--
-- Data for TOC entry 658 (OID 33619)
-- Name: sync_map; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 659 (OID 33631)
-- Name: sync_conflict_log; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 660 (OID 33644)
-- Name: sync_log; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 661 (OID 33660)
-- Name: sync_transaction_log; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 662 (OID 33674)
-- Name: process_log; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 663 (OID 33897)
-- Name: autoguide_make; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 664 (OID 33906)
-- Name: autoguide_model; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 665 (OID 33919)
-- Name: autoguide_vehicle; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 666 (OID 33936)
-- Name: autoguide_inventory; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 667 (OID 33955)
-- Name: autoguide_options; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO autoguide_options VALUES (1, 'A/T', false, 10, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (2, '4-CYL', false, 20, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (3, '6-CYL', false, 30, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (4, 'V-8', false, 40, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (5, 'CRUISE', false, 50, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (6, '5-SPD', false, 60, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (7, '4X4', false, 70, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (8, '2-DOOR', false, 80, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (9, '4-DOOR', false, 90, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (10, 'LEATHER', false, 100, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (11, 'P/DL', false, 110, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (12, 'T/W', false, 120, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (13, 'P/SEATS', false, 130, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (14, 'P/WIND', false, 140, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (15, 'P/S', false, 150, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (16, 'BEDLINE', false, 160, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (17, 'LOW MILES', false, 170, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (18, 'EX CLEAN', false, 180, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (19, 'LOADED', false, 190, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (20, 'A/C', false, 200, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (21, 'SUNROOF', false, 210, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (22, 'AM/FM ST', false, 220, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (23, 'CASS', false, 225, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (24, 'CD PLYR', false, 230, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (25, 'ABS', false, 240, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (26, 'ALARM', false, 250, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (27, 'SLD R. WIN', false, 260, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (28, 'AIRBAG', false, 270, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (29, '1 OWNER', false, 280, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_options VALUES (30, 'ALLOY WH', false, 290, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');


--
-- Data for TOC entry 668 (OID 33965)
-- Name: autoguide_inventory_options; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 669 (OID 33974)
-- Name: autoguide_ad_run; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 670 (OID 33989)
-- Name: autoguide_ad_run_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO autoguide_ad_run_types VALUES (1, 'In Column', false, 1, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_ad_run_types VALUES (2, 'Display', false, 2, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');
INSERT INTO autoguide_ad_run_types VALUES (3, 'Both', false, 3, true, '2003-12-10 13:43:47.493', '2003-12-10 13:43:47.493');


--
-- Data for TOC entry 671 (OID 34034)
-- Name: lookup_revenue_types; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_revenue_types VALUES (1, 'Technical', false, 0, true);


--
-- Data for TOC entry 672 (OID 34044)
-- Name: lookup_revenuedetail_types; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 673 (OID 34054)
-- Name: revenue; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 674 (OID 34087)
-- Name: revenue_detail; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 675 (OID 34118)
-- Name: lookup_task_priority; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_task_priority VALUES (1, '1', true, 1, true);
INSERT INTO lookup_task_priority VALUES (2, '2', false, 2, true);
INSERT INTO lookup_task_priority VALUES (3, '3', false, 3, true);
INSERT INTO lookup_task_priority VALUES (4, '4', false, 4, true);
INSERT INTO lookup_task_priority VALUES (5, '5', false, 5, true);


--
-- Data for TOC entry 676 (OID 34128)
-- Name: lookup_task_loe; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO lookup_task_loe VALUES (1, 'Minute(s)', false, 1, true);
INSERT INTO lookup_task_loe VALUES (2, 'Hour(s)', true, 1, true);
INSERT INTO lookup_task_loe VALUES (3, 'Day(s)', false, 1, true);
INSERT INTO lookup_task_loe VALUES (4, 'Week(s)', false, 1, true);
INSERT INTO lookup_task_loe VALUES (5, 'Month(s)', false, 1, true);


--
-- Data for TOC entry 677 (OID 34138)
-- Name: lookup_task_category; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 678 (OID 34148)
-- Name: task; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 679 (OID 34185)
-- Name: tasklink_contact; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 680 (OID 34195)
-- Name: tasklink_ticket; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 681 (OID 34205)
-- Name: tasklink_project; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 682 (OID 34215)
-- Name: taskcategory_project; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 683 (OID 34237)
-- Name: business_process_component_library; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO business_process_component_library VALUES (1, 'org.aspcfs.modules.troubletickets.components.LoadTicketDetails', 1, 'org.aspcfs.modules.troubletickets.components.LoadTicketDetails', 'Load all ticket information for use in other steps', true);
INSERT INTO business_process_component_library VALUES (2, 'org.aspcfs.modules.troubletickets.components.QueryTicketJustClosed', 1, 'org.aspcfs.modules.troubletickets.components.QueryTicketJustClosed', 'Was the ticket just closed?', true);
INSERT INTO business_process_component_library VALUES (3, 'org.aspcfs.modules.components.SendUserNotification', 1, 'org.aspcfs.modules.components.SendUserNotification', 'Send an email notification to a user', true);
INSERT INTO business_process_component_library VALUES (4, 'org.aspcfs.modules.troubletickets.components.SendTicketSurvey', 1, 'org.aspcfs.modules.troubletickets.components.SendTicketSurvey', 'org.aspcfs.modules.troubletickets.components.SendTicketSurvey', true);
INSERT INTO business_process_component_library VALUES (5, 'org.aspcfs.modules.troubletickets.components.QueryTicketJustAssigned', 1, 'org.aspcfs.modules.troubletickets.components.QueryTicketJustAssigned', 'Was the ticket just assigned or re-assigned?', true);
INSERT INTO business_process_component_library VALUES (6, 'org.aspcfs.modules.troubletickets.components.GenerateTicketList', 2, 'org.aspcfs.modules.troubletickets.components.GenerateTicketList', 'Generate a list of tickets based on specified parameters.  Are there any tickets matching the parameters?', true);
INSERT INTO business_process_component_library VALUES (7, 'org.aspcfs.modules.troubletickets.components.SendTicketListReport', 2, 'org.aspcfs.modules.troubletickets.components.SendTicketListReport', 'Sends a ticket report to specified users with the specified parameters', true);


--
-- Data for TOC entry 684 (OID 34248)
-- Name: business_process_component_result_lookup; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO business_process_component_result_lookup VALUES (1, 2, 1, 'Yes', 0, true);
INSERT INTO business_process_component_result_lookup VALUES (2, 2, 0, 'No', 1, true);
INSERT INTO business_process_component_result_lookup VALUES (3, 5, 1, 'Yes', 0, true);


--
-- Data for TOC entry 685 (OID 34261)
-- Name: business_process_parameter_library; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO business_process_parameter_library VALUES (1, 3, 'notification.module', NULL, 'Tickets', true);
INSERT INTO business_process_parameter_library VALUES (2, 3, 'notification.itemId', NULL, '${this.id}', true);
INSERT INTO business_process_parameter_library VALUES (3, 3, 'notification.itemModified', NULL, '${this.modified}', true);
INSERT INTO business_process_parameter_library VALUES (4, 3, 'notification.userToNotify', NULL, '${previous.enteredBy}', true);
INSERT INTO business_process_parameter_library VALUES (5, 3, 'notification.subject', NULL, 'Dark Horse CRM Ticket Closed: ${this.paddedId}', true);
INSERT INTO business_process_parameter_library VALUES (6, 3, 'notification.body', NULL, 'The following ticket in Dark Horse CRM has been closed:

--- Ticket Details ---

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

--- Ticket Details ---

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


--
-- Data for TOC entry 686 (OID 34272)
-- Name: business_process; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO business_process VALUES (1, 'dhv.ticket.insert', 'Ticket change notification', 1, 8, 1, true, '2003-12-10 13:44:12.652');
INSERT INTO business_process VALUES (2, 'dhv.report.ticketList.overdue', 'Overdue ticket notification', 2, 8, 7, true, '2003-12-10 13:44:12.652');


--
-- Data for TOC entry 687 (OID 34290)
-- Name: business_process_component; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO business_process_component VALUES (1, 1, 1, NULL, NULL, true);
INSERT INTO business_process_component VALUES (2, 1, 2, 1, NULL, true);
INSERT INTO business_process_component VALUES (3, 1, 3, 2, 1, true);
INSERT INTO business_process_component VALUES (4, 1, 4, 2, 1, false);
INSERT INTO business_process_component VALUES (5, 1, 5, 2, 0, true);
INSERT INTO business_process_component VALUES (6, 1, 3, 5, 1, true);
INSERT INTO business_process_component VALUES (7, 2, 6, NULL, NULL, true);
INSERT INTO business_process_component VALUES (8, 2, 7, 7, NULL, true);


--
-- Data for TOC entry 688 (OID 34310)
-- Name: business_process_parameter; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 689 (OID 34325)
-- Name: business_process_component_parameter; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO business_process_component_parameter VALUES (1, 3, 1, 'Tickets', true);
INSERT INTO business_process_component_parameter VALUES (2, 3, 2, '${this.id}', true);
INSERT INTO business_process_component_parameter VALUES (3, 3, 3, '${this.modified}', true);
INSERT INTO business_process_component_parameter VALUES (4, 3, 4, '${previous.enteredBy}', true);
INSERT INTO business_process_component_parameter VALUES (5, 3, 5, 'Dark Horse CRM Ticket Closed: ${this.paddedId}', true);
INSERT INTO business_process_component_parameter VALUES (6, 3, 6, 'The following ticket in Dark Horse CRM has been closed:

--- Ticket Details ---

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

--- Ticket Details ---

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


--
-- Data for TOC entry 690 (OID 34344)
-- Name: business_process_events; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 691 (OID 34366)
-- Name: business_process_log; Type: TABLE DATA; Schema: public; Owner: matt
--



--
-- Data for TOC entry 692 (OID 34372)
-- Name: business_process_hook_library; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO business_process_hook_library VALUES (1, 8, 'org.aspcfs.modules.troubletickets.base.Ticket', true);


--
-- Data for TOC entry 693 (OID 34384)
-- Name: business_process_hook_triggers; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO business_process_hook_triggers VALUES (1, 2, 1, true);
INSERT INTO business_process_hook_triggers VALUES (2, 1, 1, true);


--
-- Data for TOC entry 694 (OID 34396)
-- Name: business_process_hook; Type: TABLE DATA; Schema: public; Owner: matt
--

INSERT INTO business_process_hook VALUES (1, 1, 1, true);
INSERT INTO business_process_hook VALUES (2, 2, 1, true);


--
-- TOC entry 379 (OID 31222)
-- Name: orglist_name; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX orglist_name ON organization USING btree (name);


--
-- TOC entry 382 (OID 31277)
-- Name: contact_user_id_idx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX contact_user_id_idx ON contact USING btree (user_id);


--
-- TOC entry 384 (OID 31278)
-- Name: contactlist_namecompany; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX contactlist_namecompany ON contact USING btree (namelast, namefirst, company);


--
-- TOC entry 383 (OID 31279)
-- Name: contactlist_company; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX contactlist_company ON contact USING btree (company, namelast, namefirst);


--
-- TOC entry 415 (OID 32044)
-- Name: oppcomplist_closedate; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX oppcomplist_closedate ON opportunity_component USING btree (closedate);


--
-- TOC entry 416 (OID 32045)
-- Name: oppcomplist_description; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX oppcomplist_description ON opportunity_component USING btree (description);


--
-- TOC entry 418 (OID 32094)
-- Name: call_log_cidx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX call_log_cidx ON call_log USING btree (alertdate, enteredby);


--
-- TOC entry 430 (OID 32250)
-- Name: ticket_cidx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX ticket_cidx ON ticket USING btree (assigned_to, closed);


--
-- TOC entry 432 (OID 32251)
-- Name: ticketlist_entered; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX ticketlist_entered ON ticket USING btree (entered);


--
-- TOC entry 436 (OID 32351)
-- Name: custom_field_cat_idx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX custom_field_cat_idx ON custom_field_category USING btree (module_id);


--
-- TOC entry 439 (OID 32370)
-- Name: custom_field_grp_idx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX custom_field_grp_idx ON custom_field_group USING btree (category_id);


--
-- TOC entry 440 (OID 32391)
-- Name: custom_field_inf_idx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX custom_field_inf_idx ON custom_field_info USING btree (group_id);


--
-- TOC entry 443 (OID 32430)
-- Name: custom_field_rec_idx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX custom_field_rec_idx ON custom_field_record USING btree (link_module_id, link_item_id, category_id);


--
-- TOC entry 445 (OID 32445)
-- Name: custom_field_dat_idx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX custom_field_dat_idx ON custom_field_data USING btree (record_id, field_id);


--
-- TOC entry 451 (OID 32525)
-- Name: projects_idx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX projects_idx ON projects USING btree (group_id, project_id);


--
-- TOC entry 455 (OID 32621)
-- Name: project_assignments_idx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX project_assignments_idx ON project_assignments USING btree (activity_id);


--
-- TOC entry 454 (OID 32622)
-- Name: project_assignments_cidx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX project_assignments_cidx ON project_assignments USING btree (complete_date, user_assign_id);


--
-- TOC entry 459 (OID 32672)
-- Name: project_issues_limit_idx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX project_issues_limit_idx ON project_issues USING btree (type_id, project_id, enteredby);


--
-- TOC entry 458 (OID 32673)
-- Name: project_issues_idx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX project_issues_idx ON project_issues USING btree (issue_id);


--
-- TOC entry 463 (OID 32737)
-- Name: project_files_cidx; Type: INDEX; Schema: public; Owner: matt
--

CREATE INDEX project_files_cidx ON project_files USING btree (link_module_id, link_item_id);


--
-- TOC entry 500 (OID 33630)
-- Name: idx_sync_map; Type: INDEX; Schema: public; Owner: matt
--

CREATE UNIQUE INDEX idx_sync_map ON sync_map USING btree (client_id, table_id, record_id);


--
-- TOC entry 509 (OID 33971)
-- Name: idx_autog_inv_opt; Type: INDEX; Schema: public; Owner: matt
--

CREATE UNIQUE INDEX idx_autog_inv_opt ON autoguide_inventory_options USING btree (inventory_id, option_id);


--
-- TOC entry 360 (OID 31022)
-- Name: access_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY "access"
    ADD CONSTRAINT access_pkey PRIMARY KEY (user_id);


--
-- TOC entry 361 (OID 31032)
-- Name: lookup_industry_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_industry
    ADD CONSTRAINT lookup_industry_pkey PRIMARY KEY (code);


--
-- TOC entry 362 (OID 31040)
-- Name: access_log_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY access_log
    ADD CONSTRAINT access_log_pkey PRIMARY KEY (id);


--
-- TOC entry 695 (OID 31042)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY access_log
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 363 (OID 31052)
-- Name: usage_log_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY usage_log
    ADD CONSTRAINT usage_log_pkey PRIMARY KEY (usage_id);


--
-- TOC entry 364 (OID 31063)
-- Name: lookup_contact_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_contact_types
    ADD CONSTRAINT lookup_contact_types_pkey PRIMARY KEY (code);


--
-- TOC entry 696 (OID 31065)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_contact_types
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 365 (OID 31077)
-- Name: lookup_account_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_account_types
    ADD CONSTRAINT lookup_account_types_pkey PRIMARY KEY (code);


--
-- TOC entry 366 (OID 31081)
-- Name: state_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY state
    ADD CONSTRAINT state_pkey PRIMARY KEY (state_code);


--
-- TOC entry 367 (OID 31091)
-- Name: lookup_department_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_department
    ADD CONSTRAINT lookup_department_pkey PRIMARY KEY (code);


--
-- TOC entry 368 (OID 31101)
-- Name: lookup_orgaddress_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_orgaddress_types
    ADD CONSTRAINT lookup_orgaddress_types_pkey PRIMARY KEY (code);


--
-- TOC entry 369 (OID 31111)
-- Name: lookup_orgemail_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_orgemail_types
    ADD CONSTRAINT lookup_orgemail_types_pkey PRIMARY KEY (code);


--
-- TOC entry 370 (OID 31121)
-- Name: lookup_orgphone_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_orgphone_types
    ADD CONSTRAINT lookup_orgphone_types_pkey PRIMARY KEY (code);


--
-- TOC entry 371 (OID 31131)
-- Name: lookup_instantmessenger_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_instantmessenger_types
    ADD CONSTRAINT lookup_instantmessenger_types_pkey PRIMARY KEY (code);


--
-- TOC entry 372 (OID 31141)
-- Name: lookup_employment_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_employment_types
    ADD CONSTRAINT lookup_employment_types_pkey PRIMARY KEY (code);


--
-- TOC entry 373 (OID 31151)
-- Name: lookup_locale_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_locale
    ADD CONSTRAINT lookup_locale_pkey PRIMARY KEY (code);


--
-- TOC entry 374 (OID 31161)
-- Name: lookup_contactaddress_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_contactaddress_types
    ADD CONSTRAINT lookup_contactaddress_types_pkey PRIMARY KEY (code);


--
-- TOC entry 375 (OID 31171)
-- Name: lookup_contactemail_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_contactemail_types
    ADD CONSTRAINT lookup_contactemail_types_pkey PRIMARY KEY (code);


--
-- TOC entry 376 (OID 31181)
-- Name: lookup_contactphone_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_contactphone_types
    ADD CONSTRAINT lookup_contactphone_types_pkey PRIMARY KEY (code);


--
-- TOC entry 377 (OID 31190)
-- Name: lookup_access_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_access_types
    ADD CONSTRAINT lookup_access_types_pkey PRIMARY KEY (code);


--
-- TOC entry 378 (OID 31208)
-- Name: organization_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization
    ADD CONSTRAINT organization_pkey PRIMARY KEY (org_id);


--
-- TOC entry 697 (OID 31210)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 698 (OID 31214)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 699 (OID 31218)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization
    ADD CONSTRAINT "$3" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 381 (OID 31237)
-- Name: contact_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT contact_pkey PRIMARY KEY (contact_id);


--
-- TOC entry 380 (OID 31239)
-- Name: contact_employee_id_key; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT contact_employee_id_key UNIQUE (employee_id);


--
-- TOC entry 700 (OID 31241)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 701 (OID 31245)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT "$2" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 702 (OID 31249)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT "$3" FOREIGN KEY (department) REFERENCES lookup_department(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 703 (OID 31253)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT "$4" FOREIGN KEY (super) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 704 (OID 31257)
-- Name: $5; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT "$5" FOREIGN KEY (assistant) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 705 (OID 31261)
-- Name: $6; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 706 (OID 31265)
-- Name: $7; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 707 (OID 31269)
-- Name: $8; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT "$8" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 708 (OID 31273)
-- Name: $9; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT "$9" FOREIGN KEY (access_type) REFERENCES lookup_access_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 385 (OID 31289)
-- Name: role_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (role_id);


--
-- TOC entry 709 (OID 31291)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY role
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 710 (OID 31295)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY role
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 386 (OID 31314)
-- Name: permission_category_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY permission_category
    ADD CONSTRAINT permission_category_pkey PRIMARY KEY (category_id);


--
-- TOC entry 387 (OID 31330)
-- Name: permission_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (permission_id);


--
-- TOC entry 711 (OID 31332)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 388 (OID 31345)
-- Name: role_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY role_permission
    ADD CONSTRAINT role_permission_pkey PRIMARY KEY (id);


--
-- TOC entry 712 (OID 31347)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY role_permission
    ADD CONSTRAINT "$1" FOREIGN KEY (role_id) REFERENCES role(role_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 713 (OID 31351)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY role_permission
    ADD CONSTRAINT "$2" FOREIGN KEY (permission_id) REFERENCES permission(permission_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 389 (OID 31363)
-- Name: lookup_stage_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_stage
    ADD CONSTRAINT lookup_stage_pkey PRIMARY KEY (code);


--
-- TOC entry 390 (OID 31373)
-- Name: lookup_delivery_options_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_delivery_options
    ADD CONSTRAINT lookup_delivery_options_pkey PRIMARY KEY (code);


--
-- TOC entry 391 (OID 31384)
-- Name: news_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY news
    ADD CONSTRAINT news_pkey PRIMARY KEY (rec_id);


--
-- TOC entry 714 (OID 31386)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY news
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 392 (OID 31397)
-- Name: organization_address_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_address
    ADD CONSTRAINT organization_address_pkey PRIMARY KEY (address_id);


--
-- TOC entry 715 (OID 31399)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_address
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 716 (OID 31403)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_address
    ADD CONSTRAINT "$2" FOREIGN KEY (address_type) REFERENCES lookup_orgaddress_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 717 (OID 31407)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_address
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 718 (OID 31411)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_address
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 393 (OID 31422)
-- Name: organization_emailaddress_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT organization_emailaddress_pkey PRIMARY KEY (emailaddress_id);


--
-- TOC entry 719 (OID 31424)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 720 (OID 31428)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT "$2" FOREIGN KEY (emailaddress_type) REFERENCES lookup_orgemail_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 721 (OID 31432)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 722 (OID 31436)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_emailaddress
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 394 (OID 31447)
-- Name: organization_phone_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT organization_phone_pkey PRIMARY KEY (phone_id);


--
-- TOC entry 723 (OID 31449)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 724 (OID 31453)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT "$2" FOREIGN KEY (phone_type) REFERENCES lookup_orgphone_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 725 (OID 31457)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 726 (OID 31461)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY organization_phone
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 395 (OID 31472)
-- Name: contact_address_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_address
    ADD CONSTRAINT contact_address_pkey PRIMARY KEY (address_id);


--
-- TOC entry 727 (OID 31474)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_address
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 728 (OID 31478)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_address
    ADD CONSTRAINT "$2" FOREIGN KEY (address_type) REFERENCES lookup_contactaddress_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 729 (OID 31482)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_address
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 730 (OID 31486)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_address
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 396 (OID 31497)
-- Name: contact_emailaddress_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT contact_emailaddress_pkey PRIMARY KEY (emailaddress_id);


--
-- TOC entry 731 (OID 31499)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 732 (OID 31503)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT "$2" FOREIGN KEY (emailaddress_type) REFERENCES lookup_contactemail_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 733 (OID 31507)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 734 (OID 31511)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_emailaddress
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 397 (OID 31522)
-- Name: contact_phone_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT contact_phone_pkey PRIMARY KEY (phone_id);


--
-- TOC entry 735 (OID 31524)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 736 (OID 31528)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT "$2" FOREIGN KEY (phone_type) REFERENCES lookup_contactphone_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 737 (OID 31532)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 738 (OID 31536)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_phone
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 398 (OID 31550)
-- Name: notification_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (notification_id);


--
-- TOC entry 399 (OID 31565)
-- Name: cfsinbox_message_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY cfsinbox_message
    ADD CONSTRAINT cfsinbox_message_pkey PRIMARY KEY (id);


--
-- TOC entry 739 (OID 31567)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY cfsinbox_message
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 740 (OID 31571)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY cfsinbox_message
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 741 (OID 31579)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY cfsinbox_messagelink
    ADD CONSTRAINT "$1" FOREIGN KEY (id) REFERENCES cfsinbox_message(id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 742 (OID 31583)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY cfsinbox_messagelink
    ADD CONSTRAINT "$2" FOREIGN KEY (sent_to) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 743 (OID 31587)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY cfsinbox_messagelink
    ADD CONSTRAINT "$3" FOREIGN KEY (sent_from) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 744 (OID 31595)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY account_type_levels
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 745 (OID 31599)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY account_type_levels
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_account_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 746 (OID 31607)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_type_levels
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 747 (OID 31611)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY contact_type_levels
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_contact_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 400 (OID 31625)
-- Name: lookup_lists_lookup_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_lists_lookup
    ADD CONSTRAINT lookup_lists_lookup_pkey PRIMARY KEY (id);


--
-- TOC entry 748 (OID 31627)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_lists_lookup
    ADD CONSTRAINT "$1" FOREIGN KEY (module_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 401 (OID 31639)
-- Name: viewpoint_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT viewpoint_pkey PRIMARY KEY (viewpoint_id);


--
-- TOC entry 749 (OID 31641)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT "$1" FOREIGN KEY (user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 750 (OID 31645)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT "$2" FOREIGN KEY (vp_user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 751 (OID 31649)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 752 (OID 31653)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY viewpoint
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 402 (OID 31666)
-- Name: viewpoint_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY viewpoint_permission
    ADD CONSTRAINT viewpoint_permission_pkey PRIMARY KEY (vp_permission_id);


--
-- TOC entry 753 (OID 31668)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY viewpoint_permission
    ADD CONSTRAINT "$1" FOREIGN KEY (viewpoint_id) REFERENCES viewpoint(viewpoint_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 754 (OID 31672)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY viewpoint_permission
    ADD CONSTRAINT "$2" FOREIGN KEY (permission_id) REFERENCES permission(permission_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 403 (OID 31689)
-- Name: report_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report
    ADD CONSTRAINT report_pkey PRIMARY KEY (report_id);


--
-- TOC entry 755 (OID 31691)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 756 (OID 31695)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report
    ADD CONSTRAINT "$2" FOREIGN KEY (permission_id) REFERENCES permission(permission_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 757 (OID 31699)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 758 (OID 31703)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 404 (OID 31715)
-- Name: report_criteria_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT report_criteria_pkey PRIMARY KEY (criteria_id);


--
-- TOC entry 759 (OID 31717)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT "$1" FOREIGN KEY (report_id) REFERENCES report(report_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 760 (OID 31721)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT "$2" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 761 (OID 31725)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 762 (OID 31729)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report_criteria
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 405 (OID 31741)
-- Name: report_criteria_parameter_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report_criteria_parameter
    ADD CONSTRAINT report_criteria_parameter_pkey PRIMARY KEY (parameter_id);


--
-- TOC entry 763 (OID 31743)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report_criteria_parameter
    ADD CONSTRAINT "$1" FOREIGN KEY (criteria_id) REFERENCES report_criteria(criteria_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 406 (OID 31756)
-- Name: report_queue_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report_queue
    ADD CONSTRAINT report_queue_pkey PRIMARY KEY (queue_id);


--
-- TOC entry 764 (OID 31758)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report_queue
    ADD CONSTRAINT "$1" FOREIGN KEY (report_id) REFERENCES report(report_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 765 (OID 31762)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report_queue
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 407 (OID 31774)
-- Name: report_queue_criteria_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report_queue_criteria
    ADD CONSTRAINT report_queue_criteria_pkey PRIMARY KEY (criteria_id);


--
-- TOC entry 766 (OID 31776)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY report_queue_criteria
    ADD CONSTRAINT "$1" FOREIGN KEY (queue_id) REFERENCES report_queue(queue_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 408 (OID 31788)
-- Name: action_list_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY action_list
    ADD CONSTRAINT action_list_pkey PRIMARY KEY (action_id);


--
-- TOC entry 767 (OID 31790)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY action_list
    ADD CONSTRAINT "$1" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 768 (OID 31794)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY action_list
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 769 (OID 31798)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY action_list
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 409 (OID 31810)
-- Name: action_item_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY action_item
    ADD CONSTRAINT action_item_pkey PRIMARY KEY (item_id);


--
-- TOC entry 770 (OID 31812)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY action_item
    ADD CONSTRAINT "$1" FOREIGN KEY (action_id) REFERENCES action_list(action_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 771 (OID 31816)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY action_item
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 772 (OID 31820)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY action_item
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 410 (OID 31832)
-- Name: action_item_log_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY action_item_log
    ADD CONSTRAINT action_item_log_pkey PRIMARY KEY (log_id);


--
-- TOC entry 773 (OID 31834)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY action_item_log
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES action_item(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 774 (OID 31838)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY action_item_log
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 775 (OID 31842)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY action_item_log
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 411 (OID 31852)
-- Name: database_version_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY database_version
    ADD CONSTRAINT database_version_pkey PRIMARY KEY (version_id);


--
-- TOC entry 412 (OID 31979)
-- Name: lookup_call_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_call_types
    ADD CONSTRAINT lookup_call_types_pkey PRIMARY KEY (code);


--
-- TOC entry 413 (OID 31989)
-- Name: lookup_opportunity_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_opportunity_types
    ADD CONSTRAINT lookup_opportunity_types_pkey PRIMARY KEY (code);


--
-- TOC entry 414 (OID 32000)
-- Name: opportunity_header_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY opportunity_header
    ADD CONSTRAINT opportunity_header_pkey PRIMARY KEY (opp_id);


--
-- TOC entry 776 (OID 32002)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY opportunity_header
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 777 (OID 32006)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY opportunity_header
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 417 (OID 32022)
-- Name: opportunity_component_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT opportunity_component_pkey PRIMARY KEY (id);


--
-- TOC entry 778 (OID 32024)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$1" FOREIGN KEY (opp_id) REFERENCES opportunity_header(opp_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 779 (OID 32028)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$2" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 780 (OID 32032)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$3" FOREIGN KEY (stage) REFERENCES lookup_stage(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 781 (OID 32036)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 782 (OID 32040)
-- Name: $5; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 783 (OID 32050)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY opportunity_component_levels
    ADD CONSTRAINT "$1" FOREIGN KEY (opp_id) REFERENCES opportunity_component(id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 784 (OID 32054)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY opportunity_component_levels
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_opportunity_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 419 (OID 32068)
-- Name: call_log_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY call_log
    ADD CONSTRAINT call_log_pkey PRIMARY KEY (call_id);


--
-- TOC entry 785 (OID 32070)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 786 (OID 32074)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 787 (OID 32078)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$3" FOREIGN KEY (opp_id) REFERENCES opportunity_header(opp_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 788 (OID 32082)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$4" FOREIGN KEY (call_type_id) REFERENCES lookup_call_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 789 (OID 32086)
-- Name: $5; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 790 (OID 32090)
-- Name: $6; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 421 (OID 32119)
-- Name: ticket_level_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket_level
    ADD CONSTRAINT ticket_level_pkey PRIMARY KEY (code);


--
-- TOC entry 420 (OID 32121)
-- Name: ticket_level_description_key; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket_level
    ADD CONSTRAINT ticket_level_description_key UNIQUE (description);


--
-- TOC entry 423 (OID 32135)
-- Name: ticket_severity_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket_severity
    ADD CONSTRAINT ticket_severity_pkey PRIMARY KEY (code);


--
-- TOC entry 422 (OID 32137)
-- Name: ticket_severity_description_key; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket_severity
    ADD CONSTRAINT ticket_severity_description_key UNIQUE (description);


--
-- TOC entry 425 (OID 32147)
-- Name: lookup_ticketsource_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_ticketsource
    ADD CONSTRAINT lookup_ticketsource_pkey PRIMARY KEY (code);


--
-- TOC entry 424 (OID 32149)
-- Name: lookup_ticketsource_description_key; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_ticketsource
    ADD CONSTRAINT lookup_ticketsource_description_key UNIQUE (description);


--
-- TOC entry 427 (OID 32163)
-- Name: ticket_priority_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket_priority
    ADD CONSTRAINT ticket_priority_pkey PRIMARY KEY (code);


--
-- TOC entry 426 (OID 32165)
-- Name: ticket_priority_description_key; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket_priority
    ADD CONSTRAINT ticket_priority_description_key UNIQUE (description);


--
-- TOC entry 428 (OID 32180)
-- Name: ticket_category_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket_category
    ADD CONSTRAINT ticket_category_pkey PRIMARY KEY (id);


--
-- TOC entry 429 (OID 32196)
-- Name: ticket_category_draft_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket_category_draft
    ADD CONSTRAINT ticket_category_draft_pkey PRIMARY KEY (id);


--
-- TOC entry 431 (OID 32208)
-- Name: ticket_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (ticketid);


--
-- TOC entry 791 (OID 32210)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 792 (OID 32214)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 793 (OID 32218)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 794 (OID 32222)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 795 (OID 32226)
-- Name: $5; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$5" FOREIGN KEY (pri_code) REFERENCES ticket_priority(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 796 (OID 32230)
-- Name: $6; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$6" FOREIGN KEY (level_code) REFERENCES ticket_level(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 797 (OID 32234)
-- Name: $7; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$7" FOREIGN KEY (department_code) REFERENCES lookup_department(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 798 (OID 32238)
-- Name: $8; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$8" FOREIGN KEY (source_code) REFERENCES lookup_ticketsource(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 799 (OID 32242)
-- Name: $9; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$9" FOREIGN KEY (assigned_to) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 800 (OID 32246)
-- Name: $10; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticket
    ADD CONSTRAINT "$10" FOREIGN KEY (scode) REFERENCES ticket_severity(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 433 (OID 32262)
-- Name: ticketlog_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT ticketlog_pkey PRIMARY KEY (id);


--
-- TOC entry 801 (OID 32264)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$1" FOREIGN KEY (ticketid) REFERENCES ticket(ticketid) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 802 (OID 32268)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$2" FOREIGN KEY (assigned_to) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 803 (OID 32272)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$3" FOREIGN KEY (pri_code) REFERENCES ticket_priority(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 804 (OID 32276)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$4" FOREIGN KEY (department_code) REFERENCES lookup_department(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 805 (OID 32280)
-- Name: $5; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$5" FOREIGN KEY (scode) REFERENCES ticket_severity(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 806 (OID 32284)
-- Name: $6; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 807 (OID 32288)
-- Name: $7; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY ticketlog
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 435 (OID 32322)
-- Name: module_field_categorylink_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY module_field_categorylink
    ADD CONSTRAINT module_field_categorylink_pkey PRIMARY KEY (id);


--
-- TOC entry 434 (OID 32324)
-- Name: module_field_categorylink_category_id_key; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY module_field_categorylink
    ADD CONSTRAINT module_field_categorylink_category_id_key UNIQUE (category_id);


--
-- TOC entry 808 (OID 32326)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY module_field_categorylink
    ADD CONSTRAINT "$1" FOREIGN KEY (module_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 437 (OID 32345)
-- Name: custom_field_category_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_category
    ADD CONSTRAINT custom_field_category_pkey PRIMARY KEY (category_id);


--
-- TOC entry 809 (OID 32347)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_category
    ADD CONSTRAINT "$1" FOREIGN KEY (module_id) REFERENCES module_field_categorylink(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 438 (OID 32364)
-- Name: custom_field_group_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_group
    ADD CONSTRAINT custom_field_group_pkey PRIMARY KEY (group_id);


--
-- TOC entry 810 (OID 32366)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_group
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES custom_field_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 441 (OID 32385)
-- Name: custom_field_info_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_info
    ADD CONSTRAINT custom_field_info_pkey PRIMARY KEY (field_id);


--
-- TOC entry 811 (OID 32387)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_info
    ADD CONSTRAINT "$1" FOREIGN KEY (group_id) REFERENCES custom_field_group(group_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 442 (OID 32402)
-- Name: custom_field_lookup_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_lookup
    ADD CONSTRAINT custom_field_lookup_pkey PRIMARY KEY (code);


--
-- TOC entry 812 (OID 32404)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_lookup
    ADD CONSTRAINT "$1" FOREIGN KEY (field_id) REFERENCES custom_field_info(field_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 444 (OID 32416)
-- Name: custom_field_record_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_record
    ADD CONSTRAINT custom_field_record_pkey PRIMARY KEY (record_id);


--
-- TOC entry 813 (OID 32418)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_record
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES custom_field_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 814 (OID 32422)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_record
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 815 (OID 32426)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_record
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 816 (OID 32437)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_data
    ADD CONSTRAINT "$1" FOREIGN KEY (record_id) REFERENCES custom_field_record(record_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 817 (OID 32441)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY custom_field_data
    ADD CONSTRAINT "$2" FOREIGN KEY (field_id) REFERENCES custom_field_info(field_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 446 (OID 32456)
-- Name: lookup_project_activity_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_project_activity
    ADD CONSTRAINT lookup_project_activity_pkey PRIMARY KEY (code);


--
-- TOC entry 447 (OID 32467)
-- Name: lookup_project_priority_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_project_priority
    ADD CONSTRAINT lookup_project_priority_pkey PRIMARY KEY (code);


--
-- TOC entry 448 (OID 32478)
-- Name: lookup_project_issues_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_project_issues
    ADD CONSTRAINT lookup_project_issues_pkey PRIMARY KEY (code);


--
-- TOC entry 449 (OID 32489)
-- Name: lookup_project_status_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_project_status
    ADD CONSTRAINT lookup_project_status_pkey PRIMARY KEY (code);


--
-- TOC entry 450 (OID 32501)
-- Name: lookup_project_loe_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_project_loe
    ADD CONSTRAINT lookup_project_loe_pkey PRIMARY KEY (code);


--
-- TOC entry 452 (OID 32511)
-- Name: projects_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY projects
    ADD CONSTRAINT projects_pkey PRIMARY KEY (project_id);


--
-- TOC entry 818 (OID 32513)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY projects
    ADD CONSTRAINT "$1" FOREIGN KEY (department_id) REFERENCES lookup_department(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 819 (OID 32517)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY projects
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 820 (OID 32521)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY projects
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 453 (OID 32536)
-- Name: project_requirements_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT project_requirements_pkey PRIMARY KEY (requirement_id);


--
-- TOC entry 821 (OID 32538)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 822 (OID 32542)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$2" FOREIGN KEY (estimated_loetype) REFERENCES lookup_project_loe(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 823 (OID 32546)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$3" FOREIGN KEY (actual_loetype) REFERENCES lookup_project_loe(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 824 (OID 32550)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$4" FOREIGN KEY (approvedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 825 (OID 32554)
-- Name: $5; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$5" FOREIGN KEY (closedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 826 (OID 32558)
-- Name: $6; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 827 (OID 32562)
-- Name: $7; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_requirements
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 456 (OID 32575)
-- Name: project_assignments_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT project_assignments_pkey PRIMARY KEY (assignment_id);


--
-- TOC entry 828 (OID 32577)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 829 (OID 32581)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$2" FOREIGN KEY (requirement_id) REFERENCES project_requirements(requirement_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 830 (OID 32585)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$3" FOREIGN KEY (assignedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 831 (OID 32589)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$4" FOREIGN KEY (user_assign_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 832 (OID 32593)
-- Name: $5; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$5" FOREIGN KEY (activity_id) REFERENCES lookup_project_activity(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 833 (OID 32597)
-- Name: $6; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$6" FOREIGN KEY (estimated_loetype) REFERENCES lookup_project_loe(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 834 (OID 32601)
-- Name: $7; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$7" FOREIGN KEY (actual_loetype) REFERENCES lookup_project_loe(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 835 (OID 32605)
-- Name: $8; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$8" FOREIGN KEY (priority_id) REFERENCES lookup_project_priority(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 836 (OID 32609)
-- Name: $9; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$9" FOREIGN KEY (status_id) REFERENCES lookup_project_status(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 837 (OID 32613)
-- Name: $10; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$10" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 838 (OID 32617)
-- Name: $11; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$11" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 457 (OID 32632)
-- Name: project_assignments_status_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments_status
    ADD CONSTRAINT project_assignments_status_pkey PRIMARY KEY (status_id);


--
-- TOC entry 839 (OID 32634)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments_status
    ADD CONSTRAINT "$1" FOREIGN KEY (assignment_id) REFERENCES project_assignments(assignment_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 840 (OID 32638)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_assignments_status
    ADD CONSTRAINT "$2" FOREIGN KEY (user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 460 (OID 32654)
-- Name: project_issues_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_issues
    ADD CONSTRAINT project_issues_pkey PRIMARY KEY (issue_id);


--
-- TOC entry 841 (OID 32656)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_issues
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 842 (OID 32660)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_issues
    ADD CONSTRAINT "$2" FOREIGN KEY (type_id) REFERENCES lookup_project_issues(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 843 (OID 32664)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_issues
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 844 (OID 32668)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_issues
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 461 (OID 32685)
-- Name: project_issue_replies_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_issue_replies
    ADD CONSTRAINT project_issue_replies_pkey PRIMARY KEY (reply_id);


--
-- TOC entry 845 (OID 32687)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_issue_replies
    ADD CONSTRAINT "$1" FOREIGN KEY (issue_id) REFERENCES project_issues(issue_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 846 (OID 32691)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_issue_replies
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 847 (OID 32695)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_issue_replies
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 462 (OID 32707)
-- Name: project_folders_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_folders
    ADD CONSTRAINT project_folders_pkey PRIMARY KEY (folder_id);


--
-- TOC entry 464 (OID 32723)
-- Name: project_files_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_files
    ADD CONSTRAINT project_files_pkey PRIMARY KEY (item_id);


--
-- TOC entry 848 (OID 32725)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_files
    ADD CONSTRAINT "$1" FOREIGN KEY (folder_id) REFERENCES project_folders(folder_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 849 (OID 32729)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_files
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 850 (OID 32733)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_files
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 851 (OID 32749)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_files_version
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 852 (OID 32753)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_files_version
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 853 (OID 32757)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_files_version
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 854 (OID 32765)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_files_download
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 855 (OID 32769)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_files_download
    ADD CONSTRAINT "$2" FOREIGN KEY (user_download_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 856 (OID 32777)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 857 (OID 32781)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$2" FOREIGN KEY (user_id) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 858 (OID 32785)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 859 (OID 32789)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 465 (OID 32841)
-- Name: saved_criterialist_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY saved_criterialist
    ADD CONSTRAINT saved_criterialist_pkey PRIMARY KEY (id);


--
-- TOC entry 860 (OID 32843)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY saved_criterialist
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 861 (OID 32847)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY saved_criterialist
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 862 (OID 32851)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY saved_criterialist
    ADD CONSTRAINT "$3" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 466 (OID 32871)
-- Name: campaign_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY campaign
    ADD CONSTRAINT campaign_pkey PRIMARY KEY (campaign_id);


--
-- TOC entry 863 (OID 32873)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY campaign
    ADD CONSTRAINT "$1" FOREIGN KEY (approvedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 864 (OID 32877)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY campaign
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 865 (OID 32881)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY campaign
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 467 (OID 32896)
-- Name: campaign_run_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY campaign_run
    ADD CONSTRAINT campaign_run_pkey PRIMARY KEY (id);


--
-- TOC entry 866 (OID 32898)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY campaign_run
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 468 (OID 32907)
-- Name: excluded_recipient_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY excluded_recipient
    ADD CONSTRAINT excluded_recipient_pkey PRIMARY KEY (id);


--
-- TOC entry 867 (OID 32909)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY excluded_recipient
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 868 (OID 32913)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY excluded_recipient
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 869 (OID 32919)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY campaign_list_groups
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 870 (OID 32923)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY campaign_list_groups
    ADD CONSTRAINT "$2" FOREIGN KEY (group_id) REFERENCES saved_criterialist(id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 469 (OID 32935)
-- Name: active_campaign_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_campaign_groups
    ADD CONSTRAINT active_campaign_groups_pkey PRIMARY KEY (id);


--
-- TOC entry 871 (OID 32937)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_campaign_groups
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 470 (OID 32950)
-- Name: scheduled_recipient_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY scheduled_recipient
    ADD CONSTRAINT scheduled_recipient_pkey PRIMARY KEY (id);


--
-- TOC entry 872 (OID 32952)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY scheduled_recipient
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 873 (OID 32956)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY scheduled_recipient
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 471 (OID 32968)
-- Name: lookup_survey_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_survey_types
    ADD CONSTRAINT lookup_survey_types_pkey PRIMARY KEY (code);


--
-- TOC entry 472 (OID 32984)
-- Name: survey_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY survey
    ADD CONSTRAINT survey_pkey PRIMARY KEY (survey_id);


--
-- TOC entry 874 (OID 32986)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY survey
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 875 (OID 32990)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY survey
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 876 (OID 32996)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY campaign_survey_link
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 877 (OID 33000)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY campaign_survey_link
    ADD CONSTRAINT "$2" FOREIGN KEY (survey_id) REFERENCES survey(survey_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 473 (OID 33011)
-- Name: survey_questions_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY survey_questions
    ADD CONSTRAINT survey_questions_pkey PRIMARY KEY (question_id);


--
-- TOC entry 878 (OID 33013)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY survey_questions
    ADD CONSTRAINT "$1" FOREIGN KEY (survey_id) REFERENCES survey(survey_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 879 (OID 33017)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY survey_questions
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_survey_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 474 (OID 33027)
-- Name: survey_items_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY survey_items
    ADD CONSTRAINT survey_items_pkey PRIMARY KEY (item_id);


--
-- TOC entry 880 (OID 33029)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY survey_items
    ADD CONSTRAINT "$1" FOREIGN KEY (question_id) REFERENCES survey_questions(question_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 475 (OID 33045)
-- Name: active_survey_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey
    ADD CONSTRAINT active_survey_pkey PRIMARY KEY (active_survey_id);


--
-- TOC entry 881 (OID 33047)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey
    ADD CONSTRAINT "$1" FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 882 (OID 33051)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_survey_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 883 (OID 33055)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 884 (OID 33059)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 476 (OID 33078)
-- Name: active_survey_questions_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_questions
    ADD CONSTRAINT active_survey_questions_pkey PRIMARY KEY (question_id);


--
-- TOC entry 885 (OID 33080)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_questions
    ADD CONSTRAINT "$1" FOREIGN KEY (active_survey_id) REFERENCES active_survey(active_survey_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 886 (OID 33084)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_questions
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_survey_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 477 (OID 33094)
-- Name: active_survey_items_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_items
    ADD CONSTRAINT active_survey_items_pkey PRIMARY KEY (item_id);


--
-- TOC entry 887 (OID 33096)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_items
    ADD CONSTRAINT "$1" FOREIGN KEY (question_id) REFERENCES active_survey_questions(question_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 478 (OID 33107)
-- Name: active_survey_responses_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_responses
    ADD CONSTRAINT active_survey_responses_pkey PRIMARY KEY (response_id);


--
-- TOC entry 888 (OID 33109)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_responses
    ADD CONSTRAINT "$1" FOREIGN KEY (active_survey_id) REFERENCES active_survey(active_survey_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 479 (OID 33122)
-- Name: active_survey_answers_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_answers
    ADD CONSTRAINT active_survey_answers_pkey PRIMARY KEY (answer_id);


--
-- TOC entry 889 (OID 33124)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_answers
    ADD CONSTRAINT "$1" FOREIGN KEY (response_id) REFERENCES active_survey_responses(response_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 890 (OID 33128)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_answers
    ADD CONSTRAINT "$2" FOREIGN KEY (question_id) REFERENCES active_survey_questions(question_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 480 (OID 33140)
-- Name: active_survey_answer_items_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_answer_items
    ADD CONSTRAINT active_survey_answer_items_pkey PRIMARY KEY (id);


--
-- TOC entry 891 (OID 33142)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_answer_items
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES active_survey_items(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 892 (OID 33146)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_answer_items
    ADD CONSTRAINT "$2" FOREIGN KEY (answer_id) REFERENCES active_survey_answers(answer_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 481 (OID 33156)
-- Name: active_survey_answer_avg_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_answer_avg
    ADD CONSTRAINT active_survey_answer_avg_pkey PRIMARY KEY (id);


--
-- TOC entry 893 (OID 33158)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_answer_avg
    ADD CONSTRAINT "$1" FOREIGN KEY (question_id) REFERENCES active_survey_questions(question_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 894 (OID 33162)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY active_survey_answer_avg
    ADD CONSTRAINT "$2" FOREIGN KEY (item_id) REFERENCES active_survey_items(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 482 (OID 33173)
-- Name: field_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY field_types
    ADD CONSTRAINT field_types_pkey PRIMARY KEY (id);


--
-- TOC entry 483 (OID 33183)
-- Name: search_fields_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY search_fields
    ADD CONSTRAINT search_fields_pkey PRIMARY KEY (id);


--
-- TOC entry 484 (OID 33196)
-- Name: message_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);


--
-- TOC entry 895 (OID 33198)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY message
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 896 (OID 33202)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY message
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 897 (OID 33206)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY message
    ADD CONSTRAINT "$3" FOREIGN KEY (access_type) REFERENCES lookup_access_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 485 (OID 33218)
-- Name: message_template_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY message_template
    ADD CONSTRAINT message_template_pkey PRIMARY KEY (id);


--
-- TOC entry 898 (OID 33220)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY message_template
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 899 (OID 33224)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY message_template
    ADD CONSTRAINT "$2" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 900 (OID 33231)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY saved_criteriaelement
    ADD CONSTRAINT "$1" FOREIGN KEY (id) REFERENCES saved_criterialist(id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 901 (OID 33235)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY saved_criteriaelement
    ADD CONSTRAINT "$2" FOREIGN KEY (field) REFERENCES search_fields(id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 902 (OID 33239)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY saved_criteriaelement
    ADD CONSTRAINT "$3" FOREIGN KEY (operatorid) REFERENCES field_types(id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 486 (OID 33292)
-- Name: help_module_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_module
    ADD CONSTRAINT help_module_pkey PRIMARY KEY (module_id);


--
-- TOC entry 903 (OID 33294)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_module
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 487 (OID 33309)
-- Name: help_contents_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_contents
    ADD CONSTRAINT help_contents_pkey PRIMARY KEY (help_id);


--
-- TOC entry 904 (OID 33311)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 905 (OID 33315)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$2" FOREIGN KEY (link_module_id) REFERENCES help_module(module_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 906 (OID 33319)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$3" FOREIGN KEY (nextcontent) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 907 (OID 33323)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$4" FOREIGN KEY (prevcontent) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 908 (OID 33327)
-- Name: $5; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$5" FOREIGN KEY (upcontent) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 909 (OID 33331)
-- Name: $6; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$6" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 910 (OID 33335)
-- Name: $7; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_contents
    ADD CONSTRAINT "$7" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 488 (OID 33347)
-- Name: help_tableof_contents_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT help_tableof_contents_pkey PRIMARY KEY (content_id);


--
-- TOC entry 911 (OID 33349)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$1" FOREIGN KEY (firstchild) REFERENCES help_tableof_contents(content_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 912 (OID 33353)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$2" FOREIGN KEY (nextsibling) REFERENCES help_tableof_contents(content_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 913 (OID 33357)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$3" FOREIGN KEY (parent) REFERENCES help_tableof_contents(content_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 914 (OID 33361)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$4" FOREIGN KEY (category_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 915 (OID 33365)
-- Name: $5; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$5" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 916 (OID 33369)
-- Name: $6; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tableof_contents
    ADD CONSTRAINT "$6" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 489 (OID 33381)
-- Name: help_tableofcontentitem_links_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT help_tableofcontentitem_links_pkey PRIMARY KEY (link_id);


--
-- TOC entry 917 (OID 33383)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT "$1" FOREIGN KEY (global_link_id) REFERENCES help_tableof_contents(content_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 918 (OID 33387)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT "$2" FOREIGN KEY (linkto_content_id) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 919 (OID 33391)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 920 (OID 33395)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tableofcontentitem_links
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 490 (OID 33410)
-- Name: lookup_help_features_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_help_features
    ADD CONSTRAINT lookup_help_features_pkey PRIMARY KEY (code);


--
-- TOC entry 491 (OID 33424)
-- Name: help_features_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_features
    ADD CONSTRAINT help_features_pkey PRIMARY KEY (feature_id);


--
-- TOC entry 921 (OID 33426)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$1" FOREIGN KEY (link_help_id) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 922 (OID 33430)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$2" FOREIGN KEY (link_feature_id) REFERENCES lookup_help_features(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 923 (OID 33434)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 924 (OID 33438)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 925 (OID 33442)
-- Name: $5; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_features
    ADD CONSTRAINT "$5" FOREIGN KEY (completedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 492 (OID 33454)
-- Name: help_related_links_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT help_related_links_pkey PRIMARY KEY (relatedlink_id);


--
-- TOC entry 926 (OID 33456)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT "$1" FOREIGN KEY (owning_module_id) REFERENCES help_module(module_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 927 (OID 33460)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT "$2" FOREIGN KEY (linkto_content_id) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 928 (OID 33464)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 929 (OID 33468)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_related_links
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 493 (OID 33483)
-- Name: help_faqs_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT help_faqs_pkey PRIMARY KEY (faq_id);


--
-- TOC entry 930 (OID 33485)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT "$1" FOREIGN KEY (owning_module_id) REFERENCES help_module(module_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 931 (OID 33489)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 932 (OID 33493)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 933 (OID 33497)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_faqs
    ADD CONSTRAINT "$4" FOREIGN KEY (completedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 494 (OID 33512)
-- Name: help_business_rules_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT help_business_rules_pkey PRIMARY KEY (rule_id);


--
-- TOC entry 934 (OID 33514)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT "$1" FOREIGN KEY (link_help_id) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 935 (OID 33518)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 936 (OID 33522)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 937 (OID 33526)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_business_rules
    ADD CONSTRAINT "$4" FOREIGN KEY (completedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 495 (OID 33541)
-- Name: help_notes_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_notes
    ADD CONSTRAINT help_notes_pkey PRIMARY KEY (note_id);


--
-- TOC entry 938 (OID 33543)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_notes
    ADD CONSTRAINT "$1" FOREIGN KEY (link_help_id) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 939 (OID 33547)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_notes
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 940 (OID 33551)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_notes
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 941 (OID 33555)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_notes
    ADD CONSTRAINT "$4" FOREIGN KEY (completedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 496 (OID 33570)
-- Name: help_tips_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tips
    ADD CONSTRAINT help_tips_pkey PRIMARY KEY (tip_id);


--
-- TOC entry 942 (OID 33572)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tips
    ADD CONSTRAINT "$1" FOREIGN KEY (link_help_id) REFERENCES help_contents(help_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 943 (OID 33576)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tips
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 944 (OID 33580)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY help_tips
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 497 (OID 33591)
-- Name: sync_client_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY sync_client
    ADD CONSTRAINT sync_client_pkey PRIMARY KEY (client_id);


--
-- TOC entry 498 (OID 33599)
-- Name: sync_system_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY sync_system
    ADD CONSTRAINT sync_system_pkey PRIMARY KEY (system_id);


--
-- TOC entry 499 (OID 33613)
-- Name: sync_table_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY sync_table
    ADD CONSTRAINT sync_table_pkey PRIMARY KEY (table_id);


--
-- TOC entry 945 (OID 33615)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY sync_table
    ADD CONSTRAINT "$1" FOREIGN KEY (system_id) REFERENCES sync_system(system_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 946 (OID 33622)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY sync_map
    ADD CONSTRAINT "$1" FOREIGN KEY (client_id) REFERENCES sync_client(client_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 947 (OID 33626)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY sync_map
    ADD CONSTRAINT "$2" FOREIGN KEY (table_id) REFERENCES sync_table(table_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 948 (OID 33634)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY sync_conflict_log
    ADD CONSTRAINT "$1" FOREIGN KEY (client_id) REFERENCES sync_client(client_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 949 (OID 33638)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY sync_conflict_log
    ADD CONSTRAINT "$2" FOREIGN KEY (table_id) REFERENCES sync_table(table_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 501 (OID 33648)
-- Name: sync_log_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY sync_log
    ADD CONSTRAINT sync_log_pkey PRIMARY KEY (log_id);


--
-- TOC entry 950 (OID 33650)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY sync_log
    ADD CONSTRAINT "$1" FOREIGN KEY (system_id) REFERENCES sync_system(system_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 951 (OID 33654)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY sync_log
    ADD CONSTRAINT "$2" FOREIGN KEY (client_id) REFERENCES sync_client(client_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 502 (OID 33666)
-- Name: sync_transaction_log_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY sync_transaction_log
    ADD CONSTRAINT sync_transaction_log_pkey PRIMARY KEY (transaction_id);


--
-- TOC entry 952 (OID 33668)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY sync_transaction_log
    ADD CONSTRAINT "$1" FOREIGN KEY (log_id) REFERENCES sync_log(log_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 503 (OID 33681)
-- Name: process_log_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY process_log
    ADD CONSTRAINT process_log_pkey PRIMARY KEY (process_id);


--
-- TOC entry 953 (OID 33683)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY process_log
    ADD CONSTRAINT "$1" FOREIGN KEY (system_id) REFERENCES sync_system(system_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 954 (OID 33687)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY process_log
    ADD CONSTRAINT "$2" FOREIGN KEY (client_id) REFERENCES sync_client(client_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 504 (OID 33902)
-- Name: autoguide_make_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_make
    ADD CONSTRAINT autoguide_make_pkey PRIMARY KEY (make_id);


--
-- TOC entry 505 (OID 33911)
-- Name: autoguide_model_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_model
    ADD CONSTRAINT autoguide_model_pkey PRIMARY KEY (model_id);


--
-- TOC entry 955 (OID 33913)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_model
    ADD CONSTRAINT "$1" FOREIGN KEY (make_id) REFERENCES autoguide_make(make_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 506 (OID 33924)
-- Name: autoguide_vehicle_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_vehicle
    ADD CONSTRAINT autoguide_vehicle_pkey PRIMARY KEY (vehicle_id);


--
-- TOC entry 956 (OID 33926)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_vehicle
    ADD CONSTRAINT "$1" FOREIGN KEY (make_id) REFERENCES autoguide_make(make_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 957 (OID 33930)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_vehicle
    ADD CONSTRAINT "$2" FOREIGN KEY (model_id) REFERENCES autoguide_model(model_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 507 (OID 33943)
-- Name: autoguide_inventory_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_inventory
    ADD CONSTRAINT autoguide_inventory_pkey PRIMARY KEY (inventory_id);


--
-- TOC entry 958 (OID 33945)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_inventory
    ADD CONSTRAINT "$1" FOREIGN KEY (vehicle_id) REFERENCES autoguide_vehicle(vehicle_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 959 (OID 33949)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_inventory
    ADD CONSTRAINT "$2" FOREIGN KEY (account_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 508 (OID 33963)
-- Name: autoguide_options_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_options
    ADD CONSTRAINT autoguide_options_pkey PRIMARY KEY (option_id);


--
-- TOC entry 960 (OID 33967)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_inventory_options
    ADD CONSTRAINT "$1" FOREIGN KEY (inventory_id) REFERENCES autoguide_inventory(inventory_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 510 (OID 33981)
-- Name: autoguide_ad_run_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_ad_run
    ADD CONSTRAINT autoguide_ad_run_pkey PRIMARY KEY (ad_run_id);


--
-- TOC entry 961 (OID 33983)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_ad_run
    ADD CONSTRAINT "$1" FOREIGN KEY (inventory_id) REFERENCES autoguide_inventory(inventory_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 511 (OID 33997)
-- Name: autoguide_ad_run_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY autoguide_ad_run_types
    ADD CONSTRAINT autoguide_ad_run_types_pkey PRIMARY KEY (code);


--
-- TOC entry 512 (OID 34040)
-- Name: lookup_revenue_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_revenue_types
    ADD CONSTRAINT lookup_revenue_types_pkey PRIMARY KEY (code);


--
-- TOC entry 513 (OID 34050)
-- Name: lookup_revenuedetail_types_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_revenuedetail_types
    ADD CONSTRAINT lookup_revenuedetail_types_pkey PRIMARY KEY (code);


--
-- TOC entry 514 (OID 34063)
-- Name: revenue_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY revenue
    ADD CONSTRAINT revenue_pkey PRIMARY KEY (id);


--
-- TOC entry 962 (OID 34065)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$1" FOREIGN KEY (org_id) REFERENCES organization(org_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 963 (OID 34069)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_revenue_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 964 (OID 34073)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$3" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 965 (OID 34077)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 966 (OID 34081)
-- Name: $5; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY revenue
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 515 (OID 34093)
-- Name: revenue_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT revenue_detail_pkey PRIMARY KEY (id);


--
-- TOC entry 967 (OID 34095)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$1" FOREIGN KEY (revenue_id) REFERENCES revenue(id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 968 (OID 34099)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$2" FOREIGN KEY ("type") REFERENCES lookup_revenue_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 969 (OID 34103)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$3" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 970 (OID 34107)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$4" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 971 (OID 34111)
-- Name: $5; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY revenue_detail
    ADD CONSTRAINT "$5" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 516 (OID 34124)
-- Name: lookup_task_priority_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_task_priority
    ADD CONSTRAINT lookup_task_priority_pkey PRIMARY KEY (code);


--
-- TOC entry 517 (OID 34134)
-- Name: lookup_task_loe_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_task_loe
    ADD CONSTRAINT lookup_task_loe_pkey PRIMARY KEY (code);


--
-- TOC entry 518 (OID 34144)
-- Name: lookup_task_category_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY lookup_task_category
    ADD CONSTRAINT lookup_task_category_pkey PRIMARY KEY (code);


--
-- TOC entry 519 (OID 34159)
-- Name: task_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY task
    ADD CONSTRAINT task_pkey PRIMARY KEY (task_id);


--
-- TOC entry 972 (OID 34161)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY task
    ADD CONSTRAINT "$1" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 973 (OID 34165)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY task
    ADD CONSTRAINT "$2" FOREIGN KEY (priority) REFERENCES lookup_task_priority(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 974 (OID 34169)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY task
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 975 (OID 34173)
-- Name: $4; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY task
    ADD CONSTRAINT "$4" FOREIGN KEY (estimatedloetype) REFERENCES lookup_task_loe(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 976 (OID 34177)
-- Name: $5; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY task
    ADD CONSTRAINT "$5" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 977 (OID 34181)
-- Name: $6; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY task
    ADD CONSTRAINT "$6" FOREIGN KEY (category_id) REFERENCES lookup_task_category(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 978 (OID 34187)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY tasklink_contact
    ADD CONSTRAINT "$1" FOREIGN KEY (task_id) REFERENCES task(task_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 979 (OID 34191)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY tasklink_contact
    ADD CONSTRAINT "$2" FOREIGN KEY (contact_id) REFERENCES contact(contact_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 980 (OID 34197)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY tasklink_ticket
    ADD CONSTRAINT "$1" FOREIGN KEY (task_id) REFERENCES task(task_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 981 (OID 34201)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY tasklink_ticket
    ADD CONSTRAINT "$2" FOREIGN KEY (ticket_id) REFERENCES ticket(ticketid) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 982 (OID 34207)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY tasklink_project
    ADD CONSTRAINT "$1" FOREIGN KEY (task_id) REFERENCES task(task_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 983 (OID 34211)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY tasklink_project
    ADD CONSTRAINT "$2" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 984 (OID 34217)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY taskcategory_project
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES lookup_task_category(code) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 985 (OID 34221)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY taskcategory_project
    ADD CONSTRAINT "$2" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 520 (OID 34244)
-- Name: business_process_component_library_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_component_library
    ADD CONSTRAINT business_process_component_library_pkey PRIMARY KEY (component_id);


--
-- TOC entry 521 (OID 34253)
-- Name: business_process_component_result_lookup_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_component_result_lookup
    ADD CONSTRAINT business_process_component_result_lookup_pkey PRIMARY KEY (result_id);


--
-- TOC entry 986 (OID 34255)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_component_result_lookup
    ADD CONSTRAINT "$1" FOREIGN KEY (component_id) REFERENCES business_process_component_library(component_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 522 (OID 34268)
-- Name: business_process_parameter_library_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_parameter_library
    ADD CONSTRAINT business_process_parameter_library_pkey PRIMARY KEY (parameter_id);


--
-- TOC entry 523 (OID 34280)
-- Name: business_process_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process
    ADD CONSTRAINT business_process_pkey PRIMARY KEY (process_id);


--
-- TOC entry 524 (OID 34282)
-- Name: business_process_process_name_key; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process
    ADD CONSTRAINT business_process_process_name_key UNIQUE (process_name);


--
-- TOC entry 987 (OID 34284)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process
    ADD CONSTRAINT "$1" FOREIGN KEY (link_module_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 525 (OID 34294)
-- Name: business_process_component_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_component
    ADD CONSTRAINT business_process_component_pkey PRIMARY KEY (id);


--
-- TOC entry 988 (OID 34296)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_component
    ADD CONSTRAINT "$1" FOREIGN KEY (process_id) REFERENCES business_process(process_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 989 (OID 34300)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_component
    ADD CONSTRAINT "$2" FOREIGN KEY (component_id) REFERENCES business_process_component_library(component_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 990 (OID 34304)
-- Name: $3; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_component
    ADD CONSTRAINT "$3" FOREIGN KEY (parent_id) REFERENCES business_process_component(id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 526 (OID 34317)
-- Name: business_process_parameter_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_parameter
    ADD CONSTRAINT business_process_parameter_pkey PRIMARY KEY (id);


--
-- TOC entry 991 (OID 34319)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_parameter
    ADD CONSTRAINT "$1" FOREIGN KEY (process_id) REFERENCES business_process(process_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 527 (OID 34332)
-- Name: business_process_component_parameter_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_component_parameter
    ADD CONSTRAINT business_process_component_parameter_pkey PRIMARY KEY (id);


--
-- TOC entry 992 (OID 34334)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_component_parameter
    ADD CONSTRAINT "$1" FOREIGN KEY (component_id) REFERENCES business_process_component(id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 993 (OID 34338)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_component_parameter
    ADD CONSTRAINT "$2" FOREIGN KEY (parameter_id) REFERENCES business_process_parameter_library(parameter_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 528 (OID 34360)
-- Name: business_process_events_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_events
    ADD CONSTRAINT business_process_events_pkey PRIMARY KEY (event_id);


--
-- TOC entry 994 (OID 34362)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_events
    ADD CONSTRAINT "$1" FOREIGN KEY (process_id) REFERENCES business_process(process_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 529 (OID 34368)
-- Name: business_process_log_process_name_key; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_log
    ADD CONSTRAINT business_process_log_process_name_key UNIQUE (process_name);


--
-- TOC entry 530 (OID 34376)
-- Name: business_process_hook_library_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_hook_library
    ADD CONSTRAINT business_process_hook_library_pkey PRIMARY KEY (hook_id);


--
-- TOC entry 995 (OID 34378)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_hook_library
    ADD CONSTRAINT "$1" FOREIGN KEY (link_module_id) REFERENCES permission_category(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 531 (OID 34388)
-- Name: business_process_hook_triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_hook_triggers
    ADD CONSTRAINT business_process_hook_triggers_pkey PRIMARY KEY (trigger_id);


--
-- TOC entry 996 (OID 34390)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_hook_triggers
    ADD CONSTRAINT "$1" FOREIGN KEY (hook_id) REFERENCES business_process_hook_library(hook_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 532 (OID 34400)
-- Name: business_process_hook_pkey; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_hook
    ADD CONSTRAINT business_process_hook_pkey PRIMARY KEY (id);


--
-- TOC entry 997 (OID 34402)
-- Name: $1; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_hook
    ADD CONSTRAINT "$1" FOREIGN KEY (trigger_id) REFERENCES business_process_hook_triggers(trigger_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 998 (OID 34406)
-- Name: $2; Type: CONSTRAINT; Schema: public; Owner: matt
--

ALTER TABLE ONLY business_process_hook
    ADD CONSTRAINT "$2" FOREIGN KEY (process_id) REFERENCES business_process(process_id) ON UPDATE NO ACTION ON DELETE NO ACTION;


--
-- TOC entry 3 (OID 31002)
-- Name: access_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('access_user_id_seq', 0, true);


--
-- TOC entry 272 (OID 31024)
-- Name: lookup_industry_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_industry_code_seq', 20, true);


--
-- TOC entry 273 (OID 31034)
-- Name: access_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('access_log_id_seq', 1, false);


--
-- TOC entry 274 (OID 31046)
-- Name: usage_log_usage_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('usage_log_usage_id_seq', 1, false);


--
-- TOC entry 275 (OID 31054)
-- Name: lookup_contact_types_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_contact_types_code_seq', 3, true);


--
-- TOC entry 276 (OID 31069)
-- Name: lookup_account_types_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_account_types_code_seq', 6, true);


--
-- TOC entry 277 (OID 31083)
-- Name: lookup_department_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_department_code_seq', 7, true);


--
-- TOC entry 5 (OID 31093)
-- Name: lookup_orgaddress_type_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_orgaddress_type_code_seq', 4, true);


--
-- TOC entry 278 (OID 31103)
-- Name: lookup_orgemail_types_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_orgemail_types_code_seq', 2, true);


--
-- TOC entry 279 (OID 31113)
-- Name: lookup_orgphone_types_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_orgphone_types_code_seq', 2, true);


--
-- TOC entry 7 (OID 31123)
-- Name: lookup_instantmessenge_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_instantmessenge_code_seq', 1, false);


--
-- TOC entry 9 (OID 31133)
-- Name: lookup_employment_type_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_employment_type_code_seq', 1, false);


--
-- TOC entry 280 (OID 31143)
-- Name: lookup_locale_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_locale_code_seq', 1, false);


--
-- TOC entry 11 (OID 31153)
-- Name: lookup_contactaddress__code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_contactaddress__code_seq', 3, true);


--
-- TOC entry 13 (OID 31163)
-- Name: lookup_contactemail_ty_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_contactemail_ty_code_seq', 3, true);


--
-- TOC entry 15 (OID 31173)
-- Name: lookup_contactphone_ty_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_contactphone_ty_code_seq', 9, true);


--
-- TOC entry 281 (OID 31183)
-- Name: lookup_access_types_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_access_types_code_seq', 8, true);


--
-- TOC entry 17 (OID 31192)
-- Name: organization_org_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('organization_org_id_seq', 0, true);


--
-- TOC entry 282 (OID 31223)
-- Name: contact_contact_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('contact_contact_id_seq', 1, false);


--
-- TOC entry 283 (OID 31280)
-- Name: role_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('role_role_id_seq', 9, true);


--
-- TOC entry 19 (OID 31299)
-- Name: permission_cate_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('permission_cate_category_id_seq', 15, true);


--
-- TOC entry 284 (OID 31316)
-- Name: permission_permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('permission_permission_id_seq', 74, true);


--
-- TOC entry 285 (OID 31336)
-- Name: role_permission_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('role_permission_id_seq', 411, true);


--
-- TOC entry 286 (OID 31355)
-- Name: lookup_stage_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_stage_code_seq', 9, true);


--
-- TOC entry 21 (OID 31365)
-- Name: lookup_delivery_option_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_delivery_option_code_seq', 6, true);


--
-- TOC entry 287 (OID 31375)
-- Name: news_rec_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('news_rec_id_seq', 1, false);


--
-- TOC entry 23 (OID 31390)
-- Name: organization_add_address_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('organization_add_address_id_seq', 1, false);


--
-- TOC entry 25 (OID 31415)
-- Name: organization__emailaddress__seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('organization__emailaddress__seq', 1, false);


--
-- TOC entry 27 (OID 31440)
-- Name: organization_phone_phone_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('organization_phone_phone_id_seq', 1, false);


--
-- TOC entry 288 (OID 31465)
-- Name: contact_address_address_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('contact_address_address_id_seq', 1, false);


--
-- TOC entry 29 (OID 31490)
-- Name: contact_email_emailaddress__seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('contact_email_emailaddress__seq', 1, false);


--
-- TOC entry 289 (OID 31515)
-- Name: contact_phone_phone_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('contact_phone_phone_id_seq', 1, false);


--
-- TOC entry 31 (OID 31540)
-- Name: notification_notification_i_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('notification_notification_i_seq', 1, false);


--
-- TOC entry 290 (OID 31552)
-- Name: cfsinbox_message_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('cfsinbox_message_id_seq', 1, false);


--
-- TOC entry 291 (OID 31615)
-- Name: lookup_lists_lookup_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_lists_lookup_id_seq', 13, true);


--
-- TOC entry 292 (OID 31631)
-- Name: viewpoint_viewpoint_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('viewpoint_viewpoint_id_seq', 1, false);


--
-- TOC entry 33 (OID 31657)
-- Name: viewpoint_per_vp_permission_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('viewpoint_per_vp_permission_seq', 1, false);


--
-- TOC entry 293 (OID 31676)
-- Name: report_report_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('report_report_id_seq', 19, true);


--
-- TOC entry 294 (OID 31707)
-- Name: report_criteria_criteria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('report_criteria_criteria_id_seq', 1, false);


--
-- TOC entry 295 (OID 31733)
-- Name: report_criteria_parameter_parameter_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('report_criteria_parameter_parameter_id_seq', 1, false);


--
-- TOC entry 296 (OID 31747)
-- Name: report_queue_queue_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('report_queue_queue_id_seq', 1, false);


--
-- TOC entry 297 (OID 31766)
-- Name: report_queue_criteria_criteria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('report_queue_criteria_criteria_id_seq', 1, false);


--
-- TOC entry 35 (OID 31780)
-- Name: action_list_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('action_list_code_seq', 1, false);


--
-- TOC entry 37 (OID 31802)
-- Name: action_item_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('action_item_code_seq', 1, false);


--
-- TOC entry 39 (OID 31824)
-- Name: action_item_log_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('action_item_log_code_seq', 1, false);


--
-- TOC entry 298 (OID 31846)
-- Name: database_version_version_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('database_version_version_id_seq', 1, true);


--
-- TOC entry 299 (OID 31971)
-- Name: lookup_call_types_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_call_types_code_seq', 3, true);


--
-- TOC entry 41 (OID 31981)
-- Name: lookup_opportunity_typ_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_opportunity_typ_code_seq', 4, true);


--
-- TOC entry 300 (OID 31991)
-- Name: opportunity_header_opp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('opportunity_header_opp_id_seq', 1, false);


--
-- TOC entry 301 (OID 32010)
-- Name: opportunity_component_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('opportunity_component_id_seq', 1, false);


--
-- TOC entry 302 (OID 32058)
-- Name: call_log_call_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('call_log_call_id_seq', 1, false);


--
-- TOC entry 303 (OID 32111)
-- Name: ticket_level_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('ticket_level_code_seq', 5, true);


--
-- TOC entry 304 (OID 32123)
-- Name: ticket_severity_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('ticket_severity_code_seq', 3, true);


--
-- TOC entry 305 (OID 32139)
-- Name: lookup_ticketsource_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_ticketsource_code_seq', 4, true);


--
-- TOC entry 306 (OID 32151)
-- Name: ticket_priority_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('ticket_priority_code_seq', 3, true);


--
-- TOC entry 307 (OID 32167)
-- Name: ticket_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('ticket_category_id_seq', 5, true);


--
-- TOC entry 308 (OID 32182)
-- Name: ticket_category_draft_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('ticket_category_draft_id_seq', 1, false);


--
-- TOC entry 309 (OID 32198)
-- Name: ticket_ticketid_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('ticket_ticketid_seq', 1, false);


--
-- TOC entry 310 (OID 32252)
-- Name: ticketlog_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('ticketlog_id_seq', 1, false);


--
-- TOC entry 43 (OID 32312)
-- Name: module_field_categorylin_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('module_field_categorylin_id_seq', 2, true);


--
-- TOC entry 45 (OID 32330)
-- Name: custom_field_ca_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('custom_field_ca_category_id_seq', 1, false);


--
-- TOC entry 47 (OID 32352)
-- Name: custom_field_group_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('custom_field_group_group_id_seq', 1, false);


--
-- TOC entry 311 (OID 32371)
-- Name: custom_field_info_field_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('custom_field_info_field_id_seq', 1, false);


--
-- TOC entry 312 (OID 32392)
-- Name: custom_field_lookup_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('custom_field_lookup_code_seq', 1, false);


--
-- TOC entry 49 (OID 32408)
-- Name: custom_field_reco_record_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('custom_field_reco_record_id_seq', 1, false);


--
-- TOC entry 51 (OID 32446)
-- Name: lookup_project_activit_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_project_activit_code_seq', 10, true);


--
-- TOC entry 53 (OID 32458)
-- Name: lookup_project_priorit_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_project_priorit_code_seq', 3, true);


--
-- TOC entry 313 (OID 32469)
-- Name: lookup_project_issues_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_project_issues_code_seq', 15, true);


--
-- TOC entry 314 (OID 32480)
-- Name: lookup_project_status_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_project_status_code_seq', 6, true);


--
-- TOC entry 315 (OID 32491)
-- Name: lookup_project_loe_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_project_loe_code_seq', 5, true);


--
-- TOC entry 316 (OID 32503)
-- Name: projects_project_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('projects_project_id_seq', 1, false);


--
-- TOC entry 55 (OID 32526)
-- Name: project_requi_requirement_i_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('project_requi_requirement_i_seq', 1, false);


--
-- TOC entry 57 (OID 32566)
-- Name: project_assig_assignment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('project_assig_assignment_id_seq', 1, false);


--
-- TOC entry 59 (OID 32623)
-- Name: project_assignmen_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('project_assignmen_status_id_seq', 1, false);


--
-- TOC entry 317 (OID 32642)
-- Name: project_issues_issue_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('project_issues_issue_id_seq', 1, false);


--
-- TOC entry 61 (OID 32674)
-- Name: project_issue_repl_reply_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('project_issue_repl_reply_id_seq', 1, false);


--
-- TOC entry 318 (OID 32699)
-- Name: project_folders_folder_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('project_folders_folder_id_seq', 1, false);


--
-- TOC entry 319 (OID 32709)
-- Name: project_files_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('project_files_item_id_seq', 1, false);


--
-- TOC entry 320 (OID 32832)
-- Name: saved_criterialist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('saved_criterialist_id_seq', 1, false);


--
-- TOC entry 321 (OID 32855)
-- Name: campaign_campaign_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('campaign_campaign_id_seq', 1, false);


--
-- TOC entry 322 (OID 32885)
-- Name: campaign_run_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('campaign_run_id_seq', 1, false);


--
-- TOC entry 323 (OID 32902)
-- Name: excluded_recipient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('excluded_recipient_id_seq', 1, false);


--
-- TOC entry 324 (OID 32927)
-- Name: active_campaign_groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('active_campaign_groups_id_seq', 1, false);


--
-- TOC entry 325 (OID 32941)
-- Name: scheduled_recipient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('scheduled_recipient_id_seq', 1, false);


--
-- TOC entry 326 (OID 32960)
-- Name: lookup_survey_types_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_survey_types_code_seq', 4, true);


--
-- TOC entry 327 (OID 32970)
-- Name: survey_survey_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('survey_survey_id_seq', 1, false);


--
-- TOC entry 63 (OID 33004)
-- Name: survey_question_question_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('survey_question_question_id_seq', 1, false);


--
-- TOC entry 328 (OID 33021)
-- Name: survey_items_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('survey_items_item_id_seq', 1, false);


--
-- TOC entry 65 (OID 33033)
-- Name: active_survey_active_survey_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('active_survey_active_survey_seq', 1, false);


--
-- TOC entry 67 (OID 33063)
-- Name: active_survey_q_question_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('active_survey_q_question_id_seq', 1, false);


--
-- TOC entry 69 (OID 33088)
-- Name: active_survey_items_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('active_survey_items_item_id_seq', 1, false);


--
-- TOC entry 71 (OID 33100)
-- Name: active_survey_r_response_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('active_survey_r_response_id_seq', 1, false);


--
-- TOC entry 73 (OID 33113)
-- Name: active_survey_ans_answer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('active_survey_ans_answer_id_seq', 1, false);


--
-- TOC entry 75 (OID 33132)
-- Name: active_survey_answer_ite_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('active_survey_answer_ite_id_seq', 1, false);


--
-- TOC entry 77 (OID 33150)
-- Name: active_survey_answer_avg_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('active_survey_answer_avg_id_seq', 1, false);


--
-- TOC entry 329 (OID 33166)
-- Name: field_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('field_types_id_seq', 18, true);


--
-- TOC entry 330 (OID 33175)
-- Name: search_fields_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('search_fields_id_seq', 11, true);


--
-- TOC entry 331 (OID 33185)
-- Name: message_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('message_id_seq', 1, false);


--
-- TOC entry 332 (OID 33210)
-- Name: message_template_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('message_template_id_seq', 1, false);


--
-- TOC entry 333 (OID 33284)
-- Name: help_module_module_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('help_module_module_id_seq', 1, false);


--
-- TOC entry 334 (OID 33298)
-- Name: help_contents_help_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('help_contents_help_id_seq', 261, true);


--
-- TOC entry 335 (OID 33339)
-- Name: help_tableof_contents_content_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('help_tableof_contents_content_id_seq', 88, true);


--
-- TOC entry 336 (OID 33373)
-- Name: help_tableofcontentitem_links_link_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('help_tableofcontentitem_links_link_id_seq', 79, true);


--
-- TOC entry 337 (OID 33399)
-- Name: lookup_help_features_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_help_features_code_seq', 1, false);


--
-- TOC entry 338 (OID 33412)
-- Name: help_features_feature_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('help_features_feature_id_seq', 470, true);


--
-- TOC entry 339 (OID 33446)
-- Name: help_related_links_relatedlink_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('help_related_links_relatedlink_id_seq', 1, false);


--
-- TOC entry 340 (OID 33472)
-- Name: help_faqs_faq_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('help_faqs_faq_id_seq', 1, false);


--
-- TOC entry 341 (OID 33501)
-- Name: help_business_rules_rule_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('help_business_rules_rule_id_seq', 5, true);


--
-- TOC entry 342 (OID 33530)
-- Name: help_notes_note_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('help_notes_note_id_seq', 421, true);


--
-- TOC entry 343 (OID 33559)
-- Name: help_tips_tip_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('help_tips_tip_id_seq', 3, true);


--
-- TOC entry 344 (OID 33584)
-- Name: sync_client_client_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('sync_client_client_id_seq', 1, false);


--
-- TOC entry 345 (OID 33593)
-- Name: sync_system_system_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('sync_system_system_id_seq', 5, true);


--
-- TOC entry 346 (OID 33601)
-- Name: sync_table_table_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('sync_table_table_id_seq', 199, true);


--
-- TOC entry 347 (OID 33642)
-- Name: sync_log_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('sync_log_log_id_seq', 1, false);


--
-- TOC entry 79 (OID 33658)
-- Name: sync_transact_transaction_i_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('sync_transact_transaction_i_seq', 1, false);


--
-- TOC entry 348 (OID 33672)
-- Name: process_log_process_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('process_log_process_id_seq', 1, false);


--
-- TOC entry 349 (OID 33895)
-- Name: autoguide_make_make_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('autoguide_make_make_id_seq', 1, false);


--
-- TOC entry 350 (OID 33904)
-- Name: autoguide_model_model_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('autoguide_model_model_id_seq', 1, false);


--
-- TOC entry 81 (OID 33917)
-- Name: autoguide_vehicl_vehicle_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('autoguide_vehicl_vehicle_id_seq', 1, false);


--
-- TOC entry 83 (OID 33934)
-- Name: autoguide_inve_inventory_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('autoguide_inve_inventory_id_seq', 1, false);


--
-- TOC entry 85 (OID 33953)
-- Name: autoguide_options_option_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('autoguide_options_option_id_seq', 30, true);


--
-- TOC entry 351 (OID 33972)
-- Name: autoguide_ad_run_ad_run_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('autoguide_ad_run_ad_run_id_seq', 1, false);


--
-- TOC entry 87 (OID 33987)
-- Name: autoguide_ad_run_types_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('autoguide_ad_run_types_code_seq', 3, true);


--
-- TOC entry 352 (OID 34032)
-- Name: lookup_revenue_types_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_revenue_types_code_seq', 1, true);


--
-- TOC entry 89 (OID 34042)
-- Name: lookup_revenuedetail_t_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_revenuedetail_t_code_seq', 1, false);


--
-- TOC entry 353 (OID 34052)
-- Name: revenue_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('revenue_id_seq', 1, false);


--
-- TOC entry 354 (OID 34085)
-- Name: revenue_detail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('revenue_detail_id_seq', 1, false);


--
-- TOC entry 355 (OID 34116)
-- Name: lookup_task_priority_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_task_priority_code_seq', 5, true);


--
-- TOC entry 356 (OID 34126)
-- Name: lookup_task_loe_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_task_loe_code_seq', 5, true);


--
-- TOC entry 357 (OID 34136)
-- Name: lookup_task_category_code_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('lookup_task_category_code_seq', 1, false);


--
-- TOC entry 358 (OID 34146)
-- Name: task_task_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('task_task_id_seq', 1, false);


--
-- TOC entry 91 (OID 34235)
-- Name: business_process_com_lb_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('business_process_com_lb_id_seq', 7, true);


--
-- TOC entry 93 (OID 34246)
-- Name: business_process_comp_re_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('business_process_comp_re_id_seq', 3, true);


--
-- TOC entry 95 (OID 34259)
-- Name: business_process_pa_lib_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('business_process_pa_lib_id_seq', 23, true);


--
-- TOC entry 359 (OID 34270)
-- Name: business_process_process_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('business_process_process_id_seq', 2, true);


--
-- TOC entry 97 (OID 34288)
-- Name: business_process_compone_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('business_process_compone_id_seq', 8, true);


--
-- TOC entry 99 (OID 34308)
-- Name: business_process_param_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('business_process_param_id_seq', 1, false);


--
-- TOC entry 101 (OID 34323)
-- Name: business_process_comp_pa_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('business_process_comp_pa_id_seq', 23, true);


--
-- TOC entry 103 (OID 34342)
-- Name: business_process_e_event_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('business_process_e_event_id_seq', 1, false);


--
-- TOC entry 105 (OID 34370)
-- Name: business_process_hl_hook_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('business_process_hl_hook_id_seq', 1, true);


--
-- TOC entry 107 (OID 34382)
-- Name: business_process_ho_trig_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('business_process_ho_trig_id_seq', 2, true);


--
-- TOC entry 109 (OID 34394)
-- Name: business_process_ho_hook_id_seq; Type: SEQUENCE SET; Schema: public; Owner: matt
--

SELECT pg_catalog.setval ('business_process_ho_hook_id_seq', 2, true);

COMMIT;
