CREATE SEQUENCE lookup_quote_status_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_quote_status(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_quote_type_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_quote_type(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);

CREATE SEQUENCE lookup_quote_terms_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_quote_terms(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_quote_source_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_quote_source(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE quote_entry_quote_id_seq AS DECIMAL(27,0);


CREATE TABLE quote_entry(
    quote_id INTEGER NOT NULL,
    parent_id INTEGER REFERENCES quote_entry(quote_id),
    org_id INTEGER REFERENCES organization(org_id),
    contact_id INTEGER REFERENCES contact(contact_id),
    source_id INTEGER REFERENCES lookup_quote_source(code),
    grand_total FLOAT,
    status_id INTEGER REFERENCES lookup_quote_status(code),
    status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiration_date TIMESTAMP,
    quote_terms_id INTEGER REFERENCES lookup_quote_terms(code),
    quote_type_id INTEGER REFERENCES lookup_quote_type(code),
    issued TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    short_description CLOB(2G) NOT LOGGED,
    notes CLOB(2G) NOT LOGGED,
    ticketid INTEGER REFERENCES ticket(ticketid),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    PRIMARY KEY(quote_id)
);


CREATE SEQUENCE quote_product_item_id_seq AS DECIMAL(27,0);


CREATE TABLE quote_product(
    item_id INTEGER NOT NULL,
    quote_id INTEGER NOT NULL  REFERENCES quote_entry(quote_id),
    product_id INTEGER NOT NULL  REFERENCES product_catalog(product_id),
    quantity INTEGER DEFAULT 0 NOT NULL,
    price_currency INTEGER REFERENCES lookup_currency(code),
    price_amount FLOAT DEFAULT 0 NOT NULL,
    recurring_currency INTEGER REFERENCES lookup_currency(code),
    recurring_amount FLOAT DEFAULT 0 NOT NULL,
    recurring_type INTEGER REFERENCES lookup_recurring_type(code),
    extended_price FLOAT DEFAULT 0 NOT NULL,
    total_price FLOAT DEFAULT 0 NOT NULL,
    estimated_delivery_date TIMESTAMP,
    status_id INTEGER REFERENCES lookup_quote_status(code),
    status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estimated_delivery CLOB(2G) NOT LOGGED,
    "comment" VARGRAPHIC(300),
    PRIMARY KEY(item_id)
);



CREATE SEQUENCE quote_product_ct_option_id_seq AS DECIMAL(27,0);


CREATE TABLE quote_product_options(
    quote_product_option_id INTEGER NOT NULL,
    item_id INTEGER NOT NULL  REFERENCES quote_product(item_id),
    product_option_id INTEGER NOT NULL  REFERENCES product_option_map(product_option_id),
    quantity INTEGER DEFAULT 0 NOT NULL,
    price_currency INTEGER REFERENCES lookup_currency(code),
    price_amount FLOAT DEFAULT 0 NOT NULL,
    recurring_currency INTEGER REFERENCES lookup_currency(code),
    recurring_amount FLOAT DEFAULT 0 NOT NULL,
    recurring_type INTEGER REFERENCES lookup_recurring_type(code),
    extended_price FLOAT DEFAULT 0 NOT NULL,
    total_price FLOAT DEFAULT 0 NOT NULL,
    status_id INTEGER REFERENCES lookup_quote_status(code),
    PRIMARY KEY(quote_product_option_id)
);

CREATE TABLE quote_product_option_boolean(
    quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
    "value" CHAR(1) NOT NULL,
    id INTEGER
);

CREATE TABLE quote_product_option_float(
    quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
    "value" FLOAT NOT NULL,
    id INTEGER
);

CREATE TABLE quote_product_option_timestamp(
    quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
    "value" TIMESTAMP NOT NULL,
    id INTEGER
);

CREATE TABLE quote_product_option_integer(
    quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
    "value" INTEGER NOT NULL,
    id INTEGER
);

CREATE TABLE quote_product_option_text(
    quote_product_option_id INTEGER REFERENCES quote_product_options(quote_product_option_id),
    "value" CLOB(2G) NOT LOGGED NOT NULL,
    id INTEGER
);
