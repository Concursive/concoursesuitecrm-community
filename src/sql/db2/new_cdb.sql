CREATE SEQUENCE lookup_site_id_code_seq AS DECIMAL(27,0);

CREATE TABLE lookup_site_id(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL,
    short_description VARGRAPHIC(300),
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);

CREATE SEQUENCE access_user_id_seq AS DECIMAL(27,0)
    INCREMENT BY 1
    START WITH 0
    MINVALUE -1;

CREATE TABLE "access"(
    user_id INTEGER NOT NULL,
    username VARGRAPHIC(80) NOT NULL,
    "password" VARGRAPHIC(80),
    contact_id INTEGER DEFAULT -1,
    role_id INTEGER DEFAULT -1,
    manager_id INTEGER DEFAULT -1,
    startofday INTEGER DEFAULT 8,
    endofday INTEGER DEFAULT 18,
    locale VARGRAPHIC(255),
    timezone VARGRAPHIC(255) DEFAULT G'America/New_York',
    last_ip VARGRAPHIC(15),
    last_login TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT TIMESTAMP NOT NULL,
    expires TIMESTAMP DEFAULT NULL,
    alias INTEGER DEFAULT -1,
    assistant INTEGER DEFAULT -1,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    currency VARGRAPHIC(5),
    "language" VARGRAPHIC(20),
    webdav_password VARGRAPHIC(80),
    hidden CHAR(1) DEFAULT '0',
    site_id INTEGER REFERENCES lookup_site_id(code),
    allow_webdav_access CHAR(1) DEFAULT '1' NOT NULL,
    allow_httpapi_access CHAR(1) DEFAULT '1' NOT NULL,
    PRIMARY KEY(user_id)
);


CREATE SEQUENCE lookup_industry_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_industry(
    code INTEGER NOT NULL,
    order_id INTEGER,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);

CREATE SEQUENCE access_log_id_seq AS DECIMAL(27,0);
CREATE TABLE access_log(
    id INTEGER NOT NULL,
    user_id INTEGER NOT NULL  REFERENCES "access"(user_id),
    username VARGRAPHIC(80) NOT NULL,
    ip VARGRAPHIC(15),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    browser VARGRAPHIC(255),
    PRIMARY KEY(id)
);


CREATE SEQUENCE usage_log_usage_id_seq AS DECIMAL(27,0);
CREATE TABLE usage_log(
    usage_id INTEGER NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER,
    "action" INTEGER NOT NULL,
    record_id INTEGER,
    record_size INTEGER,
    PRIMARY KEY(usage_id)
);

CREATE SEQUENCE lookup_contact_types_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_contact_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    user_id INTEGER REFERENCES "access"(user_id),
    category INTEGER DEFAULT 0 NOT NULL,
    PRIMARY KEY(code)
);

CREATE SEQUENCE lookup_account_types_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_account_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);

CREATE TABLE state(
    state_code CHAR(2) NOT NULL,
    state VARGRAPHIC(80) NOT NULL,
    PRIMARY KEY(state_code)
);


CREATE SEQUENCE lookup_department_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_department(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_orgadd_ss_type_code_seq AS DECIMAL(27,0);

CREATE TABLE lookup_orgaddress_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);

CREATE SEQUENCE lookup_orgemail_types_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_orgemail_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);

CREATE SEQUENCE lookup_orgphone_types_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_orgphone_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);

CREATE SEQUENCE lookup_im_types_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_im_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);

CREATE SEQUENCE lookup_im_services_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_im_services(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_contact_source_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_contact_source(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);



CREATE SEQUENCE lookup_contact_rating_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_contact_rating(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_textme_age_typ_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_textmessage_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_employ_nt_type_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_employment_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);

CREATE SEQUENCE lookup_locale_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_locale(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_contac_ddress__code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_contactaddress_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_contac_mail_ty_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_contactemail_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);

CREATE SEQUENCE lookup_contac_hone_ty_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_contactphone_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);

CREATE SEQUENCE lookup_access_types_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_access_types(
    code INTEGER NOT NULL,
    link_module_id INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER,
    enabled CHAR(1) DEFAULT '1',
    rule_id INTEGER NOT NULL,
    PRIMARY KEY(code)
);

CREATE SEQUENCE lookup_account_size_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_account_size(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);

CREATE SEQUENCE lookup_segments_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_segments(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);

CREATE SEQUENCE lookup_sub_segment_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_sub_segment(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL,
    segment_id INTEGER REFERENCES lookup_segments(code),
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);

CREATE SEQUENCE lookup_title_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_title(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);


CREATE SEQUENCE organization_org_id_seq AS DECIMAL(27,0)
    INCREMENT BY 1
    START WITH 0
    MINVALUE -1;
CREATE TABLE organization(
    org_id INTEGER NOT NULL,
    name VARGRAPHIC(80) NOT NULL,
    account_number VARGRAPHIC(50),
    account_group INTEGER,
    url CLOB(2G) NOT LOGGED,
    revenue FLOAT,
    employees INTEGER,
    notes CLOB(2G) NOT LOGGED,
    sic_code VARGRAPHIC(40),
    ticker_symbol VARGRAPHIC(10),
    taxid CHAR(80),
    lead VARGRAPHIC(40),
    sales_rep INTEGER DEFAULT 0 NOT NULL,
    miner_only CHAR(1) DEFAULT '0' NOT NULL,
    defaultlocale INTEGER,
    fiscalmonth INTEGER,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    enabled CHAR(1) DEFAULT '1',
    industry_temp_code SMALLINT,
    owner INTEGER REFERENCES "access"(user_id),
    duplicate_id INTEGER DEFAULT -1,
    custom1 INTEGER DEFAULT -1,
    custom2 INTEGER DEFAULT -1,
    contract_end TIMESTAMP,
    alertdate TIMESTAMP,
    alert VARGRAPHIC(100),
    custom_data CLOB(2G) NOT LOGGED,
    namesalutation VARGRAPHIC(80),
    namelast VARGRAPHIC(80),
    namefirst VARGRAPHIC(80),
    namemiddle VARGRAPHIC(80),
    namesuffix VARGRAPHIC(80),
    import_id INTEGER,
    status_id INTEGER,
    alertdate_timezone VARGRAPHIC(255),
    contract_end_timezone VARGRAPHIC(255),
    trashed_date TIMESTAMP,
    source INTEGER REFERENCES lookup_contact_source(code),
    rating INTEGER REFERENCES lookup_contact_rating(code),
    potential FLOAT,
    segment_id INTEGER REFERENCES lookup_segments(code),
    sub_segment_id INTEGER REFERENCES lookup_sub_segment(code),
    direct_bill CHAR(1) DEFAULT '0',
    account_size INTEGER REFERENCES lookup_account_size(code),
    site_id INTEGER REFERENCES lookup_site_id(code),
    PRIMARY KEY(org_id)
);



CREATE INDEX orglist_name
    ON organization(name);


CREATE SEQUENCE contact_contact_id_seq AS DECIMAL(27,0);


CREATE TABLE contact(
    contact_id INTEGER NOT NULL,
    user_id INTEGER REFERENCES "access"(user_id),
    org_id INTEGER REFERENCES organization(org_id),
    company VARGRAPHIC(255),
    title VARGRAPHIC(80),
    department INTEGER REFERENCES lookup_department(code),
    super INTEGER REFERENCES contact(contact_id),
    namesalutation VARGRAPHIC(80),
    namelast VARGRAPHIC(80) NOT NULL,
    namefirst VARGRAPHIC(80) NOT NULL,
    namemiddle VARGRAPHIC(80),
    namesuffix VARGRAPHIC(80),
    assistant INTEGER REFERENCES contact(contact_id),
    birthdate TIMESTAMP,
    notes CLOB(2G) NOT LOGGED,
    site INTEGER,
    locale INTEGER,
    employee_id VARGRAPHIC(80),
    employmenttype INTEGER,
    startofday VARGRAPHIC(10),
    endofday VARGRAPHIC(10),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    enabled CHAR(1) DEFAULT '1',
    owner INTEGER REFERENCES "access"(user_id),
    custom1 INTEGER DEFAULT -1,
    url VARGRAPHIC(100),
    primary_contact CHAR(1) DEFAULT '0',
    employee CHAR(1) DEFAULT '0' NOT NULL,
    org_name VARGRAPHIC(255),
    access_type INTEGER REFERENCES lookup_access_types(code),
    status_id INTEGER,
    import_id INTEGER,
    information_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lead CHAR(1) DEFAULT '0',
    lead_status INTEGER,
    source INTEGER REFERENCES lookup_contact_source(code),
    rating INTEGER REFERENCES lookup_contact_rating(code),
    comments VARGRAPHIC(255),
    conversion_date TIMESTAMP,
    additional_names VARGRAPHIC(255),
    nickname VARGRAPHIC(80),
    "role" VARGRAPHIC(255),
    trashed_date TIMESTAMP,
    secret_word VARGRAPHIC(255),
    account_number VARGRAPHIC(50),
    revenue FLOAT,
    industry_temp_code INTEGER REFERENCES lookup_industry(code),
    potential FLOAT,
    no_email CHAR(1) DEFAULT '0',
    no_mail CHAR(1) DEFAULT '0',
    no_phone CHAR(1) DEFAULT '0',
    no_textmessage CHAR(1) DEFAULT '0',
    no_im CHAR(1) DEFAULT '0',
    no_fax CHAR(1) DEFAULT '0',
    site_id INTEGER REFERENCES lookup_site_id(code),
    PRIMARY KEY(contact_id)
);

CREATE INDEX contact_user_id_i1
    ON contact(user_id);
CREATE INDEX contactlist_namec1
    ON contact(namelast,namefirst);
CREATE INDEX contact_import_id1
    ON contact(import_id);
CREATE INDEX contact_org_id_idx
    ON contact(org_id);
CREATE INDEX contact_islead_idx
    ON contact(lead);

CREATE SEQUENCE contact_lead__d_map_map_id_seq AS DECIMAL(27,0);
CREATE TABLE contact_lead_skipped_map(
    map_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL  REFERENCES "access"(user_id),
    contact_id INTEGER NOT NULL  REFERENCES contact(contact_id),
    PRIMARY KEY(map_id)
);

CREATE INDEX contact_lead_skip1
    ON contact_lead_skipped_map(user_id);


CREATE TABLE contact_lead_read_map(
    map_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL  REFERENCES "access"(user_id),
    contact_id INTEGER NOT NULL  REFERENCES contact(contact_id),
    PRIMARY KEY(map_id)
);

CREATE INDEX contact_lead_read1
    ON contact_lead_read_map(user_id);
CREATE INDEX contact_lead_read2
    ON contact_lead_read_map(contact_id);


CREATE SEQUENCE role_role_id_seq AS DECIMAL(27,0);
CREATE TABLE "role"(
    role_id INTEGER NOT NULL,
    "role" VARGRAPHIC(80) NOT NULL,
    description VARGRAPHIC(255) DEFAULT NULL NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    role_type INTEGER,
    PRIMARY KEY(role_id)
);


CREATE SEQUENCE permission_ca__category_id_seq AS DECIMAL(27,0);


CREATE TABLE permission_category(
    category_id INTEGER NOT NULL,
    category VARGRAPHIC(80),
    description VARGRAPHIC(255),
    "level" INTEGER DEFAULT 0 NOT NULL,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    "active" CHAR(1) DEFAULT '1' NOT NULL,
    folders CHAR(1) DEFAULT '0' NOT NULL,
    lookups CHAR(1) DEFAULT '0' NOT NULL,
    viewpoints CHAR(1) DEFAULT '0',
    categories CHAR(1) DEFAULT '0' NOT NULL,
    scheduled_events CHAR(1) DEFAULT '0' NOT NULL,
    object_events CHAR(1) DEFAULT '0' NOT NULL,
    reports CHAR(1) DEFAULT '0' NOT NULL,
    products CHAR(1) DEFAULT '0' NOT NULL,
    webdav CHAR(1) DEFAULT '0' NOT NULL,
    logos CHAR(1) DEFAULT '0' NOT NULL,
    constant INTEGER NOT NULL,
    action_plans CHAR(1) DEFAULT '0' NOT NULL,
    custom_list_views CHAR(1) DEFAULT '0' NOT NULL,
    importer CHAR(1) DEFAULT '0' NOT NULL,
    PRIMARY KEY(category_id)
);


CREATE SEQUENCE permission_permission_id_seq AS DECIMAL(27,0);


CREATE TABLE permission(
    permission_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL  REFERENCES permission_category,
    permission VARGRAPHIC(80) NOT NULL,
    permission_view CHAR(1) DEFAULT '0' NOT NULL,
    permission_add CHAR(1) DEFAULT '0' NOT NULL,
    permission_edit CHAR(1) DEFAULT '0' NOT NULL,
    permission_delete CHAR(1) DEFAULT '0' NOT NULL,
    description VARGRAPHIC(255) DEFAULT NULL NOT NULL,
    "level" INTEGER DEFAULT 0 NOT NULL,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    "active" CHAR(1) DEFAULT '1' NOT NULL,
    viewpoints CHAR(1) DEFAULT '0',
    PRIMARY KEY(permission_id)
);

CREATE SEQUENCE role_permission_id_seq AS DECIMAL(27,0);
CREATE TABLE role_permission(
    id INTEGER NOT NULL,
    role_id INTEGER NOT NULL  REFERENCES "role"(role_id),
    permission_id INTEGER NOT NULL  REFERENCES permission(permission_id),
    role_view CHAR(1) DEFAULT '0' NOT NULL,
    role_add CHAR(1) DEFAULT '0' NOT NULL,
    role_edit CHAR(1) DEFAULT '0' NOT NULL,
    role_delete CHAR(1) DEFAULT '0' NOT NULL,
    PRIMARY KEY(id)
);


CREATE SEQUENCE lookup_stage_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_stage(
    code INTEGER NOT NULL,
    order_id INTEGER,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_delive__option_code_seq AS DECIMAL(27,0);
CREATE TABLE lookup_delivery_options(
    code INTEGER NOT NULL,
    description VARGRAPHIC(100) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE news_rec_id_seq AS DECIMAL(27,0);
CREATE TABLE news(
    rec_id INTEGER NOT NULL,
    org_id INTEGER REFERENCES organization(org_id),
    url CLOB(2G) NOT LOGGED,
    base CLOB(2G) NOT LOGGED,
    headline CLOB(2G) NOT LOGGED,
    body CLOB(2G) NOT LOGGED,
    dateEntered TIMESTAMP,
    "type" CHAR(1),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(rec_id)
);


CREATE SEQUENCE organization__d_address_id_seq AS DECIMAL(27,0);
CREATE TABLE organization_address(
    address_id INTEGER NOT NULL,
    org_id INTEGER REFERENCES organization(org_id),
    address_type INTEGER REFERENCES lookup_orgaddress_types(code),
    addrline1 VARGRAPHIC(80),
    addrline2 VARGRAPHIC(80),
    addrline3 VARGRAPHIC(80),
    city VARGRAPHIC(80),
    state VARGRAPHIC(80),
    country VARGRAPHIC(80),
    postalcode VARGRAPHIC(12),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    primary_address CHAR(1) DEFAULT '0' NOT NULL,
    addrline4 VARGRAPHIC(80),
    PRIMARY KEY(address_id)
);

CREATE INDEX organization_addr1
    ON organization_address(postalcode);

CREATE SEQUENCE organization__mailaddress__seq AS DECIMAL(27,0);
CREATE TABLE organization_emailaddress(
    emailaddress_id INTEGER NOT NULL,
    org_id INTEGER REFERENCES organization(org_id),
    emailaddress_type INTEGER REFERENCES lookup_orgemail_types(code),
    email VARGRAPHIC(256),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    primary_email CHAR(1) DEFAULT '0' NOT NULL,
    PRIMARY KEY(emailaddress_id)
);

CREATE SEQUENCE organization__one_phone_id_seq AS DECIMAL(27,0);
CREATE TABLE organization_phone(
    phone_id INTEGER NOT NULL,
    org_id INTEGER REFERENCES organization(org_id),
    phone_type INTEGER REFERENCES lookup_orgphone_types(code),
    "number" VARGRAPHIC(30),
    extension VARGRAPHIC(10),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    primary_number CHAR(1) DEFAULT '0' NOT NULL,
    PRIMARY KEY(phone_id)
);

CREATE SEQUENCE contact_address_address_id_seq AS DECIMAL(27,0);
CREATE TABLE contact_address(
    address_id INTEGER NOT NULL,
    contact_id INTEGER REFERENCES contact(contact_id),
    address_type INTEGER REFERENCES lookup_contactaddress_types(code),
    addrline1 VARGRAPHIC(80),
    addrline2 VARGRAPHIC(80),
    addrline3 VARGRAPHIC(80),
    city VARGRAPHIC(80),
    state VARGRAPHIC(80),
    country VARGRAPHIC(80),
    postalcode VARGRAPHIC(12),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    primary_address CHAR(1) DEFAULT '0' NOT NULL,
    addrline4 VARGRAPHIC(80),
    PRIMARY KEY(address_id)
);

CREATE INDEX contact_address_c1
    ON contact_address(contact_id);
CREATE INDEX contact_address_p1
    ON contact_address(postalcode);
CREATE INDEX contact_city_idx
    ON contact_address(city);
CREATE INDEX contact_address_p2
    ON contact_address(primary_address);


CREATE SEQUENCE contact_email_mailaddress__seq AS DECIMAL(27,0);
CREATE TABLE contact_emailaddress(
    emailaddress_id INTEGER NOT NULL,
    contact_id INTEGER REFERENCES contact(contact_id),
    emailaddress_type INTEGER REFERENCES lookup_contactemail_types(code),
    email VARGRAPHIC(256),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    primary_email CHAR(1) DEFAULT '0' NOT NULL,
    PRIMARY KEY(emailaddress_id)
);

CREATE INDEX contact_email_con1
    ON contact_emailaddress(contact_id);
CREATE INDEX contact_email_pri1
    ON contact_emailaddress(primary_email);


CREATE SEQUENCE contact_phone_phone_id_seq AS DECIMAL(27,0);
CREATE TABLE contact_phone(
    phone_id INTEGER NOT NULL,
    contact_id INTEGER REFERENCES contact(contact_id),
    phone_type INTEGER REFERENCES lookup_contactphone_types(code),
    "number" VARGRAPHIC(30),
    extension VARGRAPHIC(10),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    primary_number CHAR(1) DEFAULT '0' NOT NULL,
    PRIMARY KEY(phone_id)
);

CREATE INDEX contact_phone_con1
    ON contact_phone(contact_id);

CREATE SEQUENCE contact_imadd_s_address_id_seq AS DECIMAL(27,0);
CREATE TABLE contact_imaddress(
    address_id INTEGER NOT NULL,
    contact_id INTEGER REFERENCES contact(contact_id),
    imaddress_type INTEGER REFERENCES lookup_im_types(code),
    imaddress_service INTEGER REFERENCES lookup_im_services(code),
    imaddress VARGRAPHIC(256),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    primary_im CHAR(1) DEFAULT '0' NOT NULL,
    PRIMARY KEY(address_id)
);


CREATE SEQUENCE contact_textm_s_address_id_seq AS DECIMAL(27,0);
CREATE TABLE contact_textmessageaddress(
    address_id INTEGER NOT NULL,
    contact_id INTEGER REFERENCES contact(contact_id),
    textmessageaddress VARGRAPHIC(256),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    primary_textmessage_address CHAR(1) DEFAULT '0' NOT NULL,
    textmessageaddress_type INTEGER REFERENCES lookup_textmessage_types(code),
    PRIMARY KEY(address_id)
);

CREATE SEQUENCE notification__tification_i_seq AS DECIMAL(27,0);
CREATE TABLE notification(
    notification_id INTEGER NOT NULL,
    notify_user INTEGER NOT NULL,
    "module" VARGRAPHIC(255) NOT NULL,
    item_id INTEGER NOT NULL,
    item_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    attempt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    notify_type VARGRAPHIC(30),
    subject CLOB(2G) NOT LOGGED,
    "message" CLOB(2G) NOT LOGGED,
    result INTEGER NOT NULL,
    errorMessage CLOB(2G) NOT LOGGED,
    PRIMARY KEY(notification_id)
);


CREATE SEQUENCE cfsinbox_message_id_seq AS DECIMAL(27,0);
CREATE TABLE cfsinbox_message(
    id INTEGER NOT NULL,
    subject VARGRAPHIC(255),
    body CLOB(2G) NOT LOGGED NOT NULL,
    reply_id INTEGER NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    sent TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "type" INTEGER DEFAULT -1 NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    delete_flag CHAR(1) DEFAULT '0',
    PRIMARY KEY(id)
);

CREATE TABLE cfsinbox_messagelink(
    id INTEGER NOT NULL  REFERENCES cfsinbox_message(id),
    sent_to INTEGER NOT NULL  REFERENCES contact(contact_id),
    status INTEGER DEFAULT 0 NOT NULL,
    viewed TIMESTAMP DEFAULT NULL,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    sent_from INTEGER NOT NULL  REFERENCES "access"(user_id)
);

CREATE TABLE account_type_levels(
    org_id INTEGER NOT NULL  REFERENCES organization(org_id),
    type_id INTEGER NOT NULL  REFERENCES lookup_account_types(code),
    "level" INTEGER NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE contact_type_levels(
    contact_id INTEGER NOT NULL  REFERENCES contact(contact_id),
    type_id INTEGER NOT NULL  REFERENCES lookup_contact_types(code),
    "level" INTEGER NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE SEQUENCE lookup_lists_lookup_id_seq AS DECIMAL(27,0);
CREATE TABLE lookup_lists_lookup(
    id INTEGER NOT NULL,
    module_id INTEGER NOT NULL  REFERENCES permission_category(category_id),
    lookup_id INTEGER NOT NULL,
    class_name VARGRAPHIC(20),
    table_name VARGRAPHIC(60),
    "level" INTEGER DEFAULT 0,
    description CLOB(2G) NOT LOGGED,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    category_id INTEGER NOT NULL,
    PRIMARY KEY(id)
);

CREATE SEQUENCE webdav_id_seq AS DECIMAL(27,0);
CREATE TABLE webdav(
    id INTEGER NOT NULL,
    category_id INTEGER NOT NULL  REFERENCES permission_category(category_id),
    class_name VARGRAPHIC(300) NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    PRIMARY KEY(id)
);

CREATE SEQUENCE category_editor_lookup_id_seq AS DECIMAL(27,0);
CREATE TABLE category_editor_lookup(
    id INTEGER NOT NULL,
    module_id INTEGER NOT NULL  REFERENCES permission_category(category_id),
    constant_id INTEGER NOT NULL,
    table_name VARGRAPHIC(60),
    "level" INTEGER DEFAULT 0,
    description CLOB(2G) NOT LOGGED,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    category_id INTEGER NOT NULL,
    max_levels INTEGER NOT NULL,
    PRIMARY KEY(id)
);

CREATE SEQUENCE viewpoint_viewpoint_id_seq AS DECIMAL(27,0);
CREATE TABLE viewpoint(
    viewpoint_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL  REFERENCES "access"(user_id),
    vp_user_id INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(viewpoint_id)
);

CREATE SEQUENCE viewpoint_per_p_permission_seq AS DECIMAL(27,0);
CREATE TABLE viewpoint_permission(
    vp_permission_id INTEGER NOT NULL,
    viewpoint_id INTEGER NOT NULL  REFERENCES viewpoint(viewpoint_id),
    permission_id INTEGER NOT NULL  REFERENCES permission(permission_id),
    viewpoint_view CHAR(1) DEFAULT '0' NOT NULL,
    viewpoint_add CHAR(1) DEFAULT '0' NOT NULL,
    viewpoint_edit CHAR(1) DEFAULT '0' NOT NULL,
    viewpoint_delete CHAR(1) DEFAULT '0' NOT NULL,
    PRIMARY KEY(vp_permission_id)
);

CREATE SEQUENCE report_report_id_seq AS DECIMAL(27,0);
CREATE TABLE report(
    report_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL  REFERENCES permission_category(category_id),
    permission_id INTEGER REFERENCES permission(permission_id),
    filename VARGRAPHIC(300) NOT NULL,
    "type" INTEGER DEFAULT 1 NOT NULL,
    title VARGRAPHIC(300) NOT NULL,
    description VARGRAPHIC(1024) NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    custom CHAR(1) DEFAULT '0' NOT NULL,
    PRIMARY KEY(report_id)
);


CREATE SEQUENCE report_criter__criteria_id_seq AS DECIMAL(27,0);
CREATE TABLE report_criteria(
    criteria_id INTEGER NOT NULL,
    report_id INTEGER NOT NULL  REFERENCES report(report_id),
    owner INTEGER NOT NULL  REFERENCES "access"(user_id),
    subject VARGRAPHIC(512) NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(criteria_id)
);

CREATE SEQUENCE report_criter_parameter_id_seq AS DECIMAL(27,0);
CREATE TABLE report_criteria_parameter(
    parameter_id INTEGER NOT NULL,
    criteria_id INTEGER NOT NULL  REFERENCES report_criteria(criteria_id),
    "parameter" VARGRAPHIC(255) NOT NULL,
    "value" CLOB(2G) NOT LOGGED,
    PRIMARY KEY(parameter_id)
);

CREATE SEQUENCE report_queue_queue_id_seq AS DECIMAL(27,0);
CREATE TABLE report_queue(
    queue_id INTEGER NOT NULL,
    report_id INTEGER NOT NULL  REFERENCES report(report_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    processed TIMESTAMP,
    status INTEGER DEFAULT 0 NOT NULL,
    filename VARGRAPHIC(256),
    filesize INTEGER DEFAULT -1,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(queue_id)
);

CREATE SEQUENCE report_queue___criteria_id_seq AS DECIMAL(27,0);
CREATE TABLE report_queue_criteria(
    criteria_id INTEGER NOT NULL,
    queue_id INTEGER NOT NULL  REFERENCES report_queue(queue_id),
    "parameter" VARGRAPHIC(255) NOT NULL,
    "value" CLOB(2G) NOT LOGGED,
    PRIMARY KEY(criteria_id)
);

CREATE SEQUENCE action_list_code_seq AS DECIMAL(27,0);
CREATE TABLE action_list(
    action_id INTEGER NOT NULL,
    description VARGRAPHIC(255) NOT NULL,
    owner INTEGER NOT NULL  REFERENCES "access"(user_id),
    completedate TIMESTAMP,
    link_module_id INTEGER NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    PRIMARY KEY(action_id)
);

CREATE SEQUENCE action_item_code_seq AS DECIMAL(27,0);
CREATE TABLE action_item(
    item_id INTEGER NOT NULL,
    action_id INTEGER NOT NULL  REFERENCES action_list(action_id),
    link_item_id INTEGER NOT NULL,
    completedate TIMESTAMP,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    PRIMARY KEY(item_id)
);

CREATE SEQUENCE action_item_log_code_seq AS DECIMAL(27,0);
CREATE TABLE action_item_log(
    log_id INTEGER NOT NULL,
    item_id INTEGER NOT NULL  REFERENCES action_item(item_id),
    link_item_id INTEGER DEFAULT -1,
    "type" INTEGER NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(log_id)
);



CREATE SEQUENCE import_import_id_seq AS DECIMAL(27,0);


CREATE TABLE import(
    import_id INTEGER NOT NULL,
    "type" INTEGER NOT NULL,
    name VARGRAPHIC(250) NOT NULL,
    description CLOB(2G) NOT LOGGED,
    source_type INTEGER,
    source VARGRAPHIC(1000),
    record_delimiter VARGRAPHIC(10),
    column_delimiter VARGRAPHIC(10),
    total_imported_records INTEGER,
    total_failed_records INTEGER,
    status_id INTEGER,
    file_type INTEGER,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    site_id INTEGER REFERENCES lookup_site_id(code),
    rating INTEGER REFERENCES lookup_contact_rating(code),
    comments CLOB(2G) NOT LOGGED,
    PRIMARY KEY(import_id)
);

CREATE INDEX import_entered_idx
    ON import(entered);
CREATE INDEX import_name_idx
    ON import(name);


CREATE SEQUENCE database_vers_n_version_id_seq AS DECIMAL(27,0);
CREATE TABLE database_version(
    version_id INTEGER NOT NULL,
    script_filename VARGRAPHIC(255) NOT NULL,
    script_version VARGRAPHIC(255) NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(version_id)
);

CREATE SEQUENCE lookup_relati_ypes_type_id_seq AS DECIMAL(27,0);
CREATE TABLE lookup_relationship_types(
    type_id INTEGER NOT NULL,
    category_id_maps_from INTEGER NOT NULL,
    category_id_maps_to INTEGER NOT NULL,
    reciprocal_name_1 VARGRAPHIC(512),
    reciprocal_name_2 VARGRAPHIC(512),
    "level" INTEGER DEFAULT 0,
    default_item CHAR(1) DEFAULT '0',
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(type_id)
);


CREATE SEQUENCE relationship__ationship_id_seq AS DECIMAL(27,0);
CREATE TABLE relationship(
    relationship_id INTEGER NOT NULL,
    type_id INTEGER REFERENCES lookup_relationship_types(type_id),
    object_id_maps_from INTEGER NOT NULL,
    category_id_maps_from INTEGER NOT NULL,
    object_id_maps_to INTEGER NOT NULL,
    category_id_maps_to INTEGER NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL,
    trashed_date TIMESTAMP,
    PRIMARY KEY(relationship_id)
);



CREATE SEQUENCE user_group_group_id_seq AS DECIMAL(27,0);
CREATE TABLE user_group(
    group_id INTEGER NOT NULL  PRIMARY KEY,
    group_name VARGRAPHIC(255) NOT NULL,
    description CLOB(2G) NOT LOGGED,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    site_id INTEGER REFERENCES lookup_site_id(code)
);

CREATE SEQUENCE user_group_ma_group_map_id_seq AS DECIMAL(27,0);
CREATE TABLE user_group_map(
    group_map_id INTEGER NOT NULL  PRIMARY KEY,
    user_id INTEGER NOT NULL  REFERENCES "access"(user_id),
    group_id INTEGER NOT NULL  REFERENCES user_group(group_id),
    "level" INTEGER DEFAULT 10 NOT NULL,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE custom_list_v_or_editor_id_seq AS DECIMAL(27,0);
CREATE TABLE custom_list_view_editor(
    editor_id INTEGER NOT NULL  PRIMARY KEY,
    module_id INTEGER NOT NULL  REFERENCES permission_category(category_id),
    constant_id INTEGER NOT NULL,
    description CLOB(2G) NOT LOGGED,
    "level" INTEGER DEFAULT 0,
    category_id INTEGER NOT NULL
);

CREATE SEQUENCE custom_list_view_view_id_seq AS DECIMAL(27,0);
CREATE TABLE custom_list_view(
    view_id INTEGER NOT NULL  PRIMARY KEY,
    editor_id INTEGER NOT NULL  REFERENCES custom_list_view_editor(editor_id),
    name VARGRAPHIC(80) NOT NULL,
    description CLOB(2G) NOT LOGGED,
    is_default CHAR(1) DEFAULT '0',
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE custom_list_v_eld_field_id_seq AS DECIMAL(27,0);
CREATE TABLE custom_list_view_field(
    field_id INTEGER NOT NULL  PRIMARY KEY,
    view_id INTEGER NOT NULL  REFERENCES custom_list_view(view_id),
    name VARGRAPHIC(80) NOT NULL
);
