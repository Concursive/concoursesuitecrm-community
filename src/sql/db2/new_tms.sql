
CREATE SEQUENCE ticket_level_code_seq AS DECIMAL(27,0);


CREATE TABLE ticket_level(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL  UNIQUE,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);


CREATE SEQUENCE ticket_severity_code_seq AS DECIMAL(27,0);


CREATE TABLE ticket_severity(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL  UNIQUE,
    style CLOB(2G) NOT LOGGED DEFAULT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);


CREATE SEQUENCE lookup_ticketsource_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_ticketsource(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL  UNIQUE,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);


CREATE SEQUENCE lookup_ticket_status_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_ticket_status(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL  UNIQUE,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);


CREATE SEQUENCE ticket_priority_code_seq AS DECIMAL(27,0);


CREATE TABLE ticket_priority(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL  UNIQUE,
    style CLOB(2G) NOT LOGGED DEFAULT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);


CREATE SEQUENCE lookup_ticket_scalati_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_ticket_escalation(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL  UNIQUE,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);


CREATE SEQUENCE ticket_category_id_seq AS DECIMAL(27,0);


CREATE TABLE ticket_category(
    id INTEGER NOT NULL  PRIMARY KEY,
    cat_level INTEGER DEFAULT 0 NOT NULL,
    parent_cat_code INTEGER DEFAULT 0 NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    full_description CLOB(2G) NOT LOGGED DEFAULT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    site_id INTEGER REFERENCES lookup_site_id(code)
);


CREATE SEQUENCE ticket_category_draft_id_seq AS DECIMAL(27,0);


CREATE TABLE ticket_category_draft(
    id INTEGER NOT NULL  PRIMARY KEY,
    link_id INTEGER DEFAULT -1,
    cat_level INTEGER DEFAULT 0 NOT NULL,
    parent_cat_code INTEGER DEFAULT 0 NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    full_description CLOB(2G) NOT LOGGED DEFAULT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    site_id INTEGER REFERENCES lookup_site_id(code)
);


CREATE SEQUENCE ticket_catego_nment_map_id_seq AS DECIMAL(27,0);


CREATE TABLE ticket_category_dra_assignment(
    map_id INTEGER NOT NULL  PRIMARY KEY,
    category_id INTEGER NOT NULL  REFERENCES ticket_category_draft(id),
    department_id INTEGER REFERENCES lookup_department(code),
    assigned_to INTEGER REFERENCES "access"(user_id),
    group_id INTEGER REFERENCES user_group(group_id)
);


CREATE TABLE ticket_category_assignment(
    map_id INTEGER NOT NULL  PRIMARY KEY,
    category_id INTEGER NOT NULL  REFERENCES ticket_category(id),
    department_id INTEGER REFERENCES lookup_department(code),
    assigned_to INTEGER REFERENCES "access"(user_id),
    group_id INTEGER REFERENCES user_group(group_id)
);


CREATE SEQUENCE ticket_ticketid_seq AS DECIMAL(27,0);


CREATE TABLE ticket(
    ticketid INTEGER NOT NULL  PRIMARY KEY,
    org_id INTEGER REFERENCES organization,
    contact_id INTEGER REFERENCES contact,
    problem CLOB(2G) NOT LOGGED NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    closed TIMESTAMP,
    pri_code INTEGER REFERENCES ticket_priority(code),
    level_code INTEGER REFERENCES ticket_level(code),
    department_code INTEGER REFERENCES lookup_department,
    source_code INTEGER REFERENCES lookup_ticketsource(code),
    cat_code INTEGER REFERENCES ticket_category(id),
    subcat_code1 INTEGER REFERENCES ticket_category(id),
    subcat_code2 INTEGER REFERENCES ticket_category(id),
    subcat_code3 INTEGER REFERENCES ticket_category(id),
    assigned_to INTEGER REFERENCES "access"(user_id),
    "comment" VARGRAPHIC(2000),
    solution CLOB(2G) NOT LOGGED,
    scode INTEGER REFERENCES ticket_severity(code),
    critical TIMESTAMP,
    notified TIMESTAMP,
    custom_data CLOB(2G) NOT LOGGED,
    location VARGRAPHIC(256),
    assigned_date TIMESTAMP,
    est_resolution_date TIMESTAMP,
    resolution_date TIMESTAMP,
    cause CLOB(2G) NOT LOGGED,
    link_contract_id INTEGER REFERENCES service_contract(contract_id),
    link_asset_id INTEGER REFERENCES asset(asset_id),
    product_id INTEGER REFERENCES product_catalog(product_id)
);


CREATE INDEX "ticket_cidx"
    ON ticket(assigned_to,closed);


CREATE INDEX "ticketlist_entered"
    ON ticket(entered);


CREATE TABLE project_ticket_count(
    project_id INTEGER NOT NULL  UNIQUE  REFERENCES projects(project_id),
    key_count INTEGER DEFAULT 0 NOT NULL
);


CREATE SEQUENCE ticketlog_id_seq AS DECIMAL(27,0);


CREATE TABLE ticketlog(
    id INTEGER NOT NULL  PRIMARY KEY,
    ticketid INTEGER REFERENCES ticket(ticketid),
    assigned_to INTEGER REFERENCES "access"(user_id),
    "comment" CLOB(2G) NOT LOGGED,
    closed CHAR(1),
    pri_code INTEGER REFERENCES ticket_priority(code),
    level_code INTEGER,
    department_code INTEGER REFERENCES lookup_department(code),
    cat_code INTEGER,
    scode INTEGER REFERENCES ticket_severity(code),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id)
);


CREATE SEQUENCE ticket_csstm_form_form_id_seq AS DECIMAL(27,0);


CREATE TABLE ticket_csstm_form(
    form_id INTEGER NOT NULL  PRIMARY KEY,
    link_ticket_id INTEGER REFERENCES ticket(ticketid),
    phone_response_time VARGRAPHIC(10),
    engineer_response_time VARGRAPHIC(10),
    follow_up_required CHAR(1) DEFAULT '0',
    follow_up_description VARGRAPHIC(2000),
    alert_date TIMESTAMP,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    enabled CHAR(1) DEFAULT '1',
    travel_towards_sc CHAR(1) DEFAULT '1',
    labor_towards_sc CHAR(1) DEFAULT '1',
    alert_date_timezone VARGRAPHIC(255)
);


CREATE SEQUENCE ticket_activi_item_item_id_seq AS DECIMAL(27,0);


CREATE TABLE ticket_activity_item(
    item_id INTEGER NOT NULL  PRIMARY KEY,
    link_form_id INTEGER REFERENCES ticket_csstm_form(form_id),
    activity_date TIMESTAMP,
    description CLOB(2G) NOT LOGGED,
    travel_hours INTEGER,
    travel_minutes INTEGER,
    labor_hours INTEGER,
    labor_minutes INTEGER,
    activity_date_timezone VARGRAPHIC(255)
);


CREATE SEQUENCE ticket_sun_form_form_id_seq AS DECIMAL(27,0);


CREATE TABLE ticket_sun_form(
    form_id INTEGER NOT NULL  PRIMARY KEY,
    link_ticket_id INTEGER REFERENCES ticket(ticketid),
    description_of_service CLOB(2G) NOT LOGGED,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    enabled CHAR(1) DEFAULT '1'
);


CREATE SEQUENCE trouble_asset_placement_id_seq AS DECIMAL(27,0);


CREATE TABLE trouble_asset_replacement(
    replacement_id INTEGER NOT NULL  PRIMARY KEY,
    link_form_id INTEGER REFERENCES ticket_sun_form(form_id),
    part_number VARGRAPHIC(256),
    part_description CLOB(2G) NOT LOGGED
);


CREATE TABLE ticketlink_project(
    ticket_id INTEGER NOT NULL  REFERENCES ticket(ticketid),
    project_id INTEGER NOT NULL  REFERENCES projects(project_id)
);


CREATE INDEX ticketlink_projec1
    ON ticketlink_project(ticket_id);


CREATE SEQUENCE lookup_ticket_cause_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_ticket_cause(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);


CREATE SEQUENCE lookup_ticket_esoluti_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_ticket_resolution(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);


CREATE SEQUENCE ticket_defect_defect_id_seq AS DECIMAL(27,0);


CREATE TABLE ticket_defect(
    defect_id INTEGER NOT NULL  PRIMARY KEY,
    title VARGRAPHIC(255) NOT NULL,
    description CLOB(2G) NOT LOGGED,
    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    end_date TIMESTAMP,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    trashed_date TIMESTAMP,
    site_id INTEGER REFERENCES lookup_site_id(code)
);


CREATE SEQUENCE lookup_ticket_state_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_ticket_state(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);
