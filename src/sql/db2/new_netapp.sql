

CREATE SEQUENCE netapp_contra_xpiration_id_seq AS DECIMAL(27,0);



CREATE TABLE netapp_contractexpiration(
    expiration_id INTEGER NOT NULL  PRIMARY KEY,
    serial_number VARGRAPHIC(255),
    agreement_number VARGRAPHIC(255),
    services VARGRAPHIC(1024),
    startdate TIMESTAMP,
    enddate TIMESTAMP,
    installed_at_company_name VARGRAPHIC(1024),
    installed_at_site_name VARGRAPHIC(1024),
    group_name VARGRAPHIC(255),
    product_number VARGRAPHIC(255),
    system_name VARGRAPHIC(255),
    operating_system VARGRAPHIC(255),
    no_of_shelves INTEGER,
    no_of_disks INTEGER,
    nvram INTEGER,
    memory INTEGER,
    auto_support_status VARGRAPHIC(255),
    installed_at_address VARGRAPHIC(1024),
    city VARGRAPHIC(255),
    state_province VARGRAPHIC(255),
    postal_code VARGRAPHIC(255),
    country VARGRAPHIC(255),
    installed_at_contact_firstname VARGRAPHIC(255),
    contact_lastname VARGRAPHIC(255),
    contact_email VARGRAPHIC(255),
    agreement_company VARGRAPHIC(255),
    quote_amount FLOAT,
    quotegenerateddate TIMESTAMP,
    quoteaccepteddate TIMESTAMP,
    quoterejecteddate TIMESTAMP,
    "comment" CLOB(2G) NOT LOGGED,
    import_id INTEGER,
    status_id INTEGER,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id)
);

CREATE SEQUENCE netapp_contra_ation_log_id_seq AS DECIMAL(27,0);
CREATE TABLE netapp_contractexpiration_log(
    id INTEGER NOT NULL  PRIMARY KEY,
    expiration_id INTEGER REFERENCES netapp_contractexpiration(expiration_id),
    quote_amount FLOAT,
    quotegenerateddate TIMESTAMP,
    quoteaccepteddate TIMESTAMP,
    quoterejecteddate TIMESTAMP,
    "comment" CLOB(2G) NOT LOGGED,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id)
);
