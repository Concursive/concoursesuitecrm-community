
CREATE SEQUENCE lookup_order_status_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_order_status(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_order_type_code_seq AS DECIMAL(27,0);

CREATE TABLE lookup_order_type(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_order_terms_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_order_terms(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_order_source_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_order_source(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE order_entry_order_id_seq AS DECIMAL(27,0);


CREATE TABLE order_entry(
    order_id INTEGER NOT NULL,
    parent_id INTEGER REFERENCES order_entry(order_id),
    org_id INTEGER NOT NULL  REFERENCES organization(org_id),
    quote_id INTEGER REFERENCES quote_entry(quote_id),
    sales_id INTEGER REFERENCES "access"(user_id),
    orderedby INTEGER REFERENCES contact(contact_id),
    billing_contact_id INTEGER REFERENCES contact(contact_id),
    source_id INTEGER REFERENCES lookup_order_source(code),
    grand_total FLOAT,
    status_id INTEGER REFERENCES lookup_order_status(code),
    status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    contract_date TIMESTAMP,
    expiration_date TIMESTAMP,
    order_terms_id INTEGER REFERENCES lookup_order_terms(code),
    order_type_id INTEGER REFERENCES lookup_order_type(code),
    description VARGRAPHIC(2000),
    notes CLOB(2G) NOT LOGGED,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    submitted TIMESTAMP,
    approx_ship_date TIMESTAMP DEFAULT NULL,
    approx_delivery_date TIMESTAMP DEFAULT NULL,     
    PRIMARY KEY(order_id)
);


CREATE SEQUENCE order_product_item_id_seq AS DECIMAL(27,0);


CREATE TABLE order_product(
    item_id INTEGER NOT NULL,
    order_id INTEGER NOT NULL  REFERENCES order_entry(order_id),
    product_id INTEGER NOT NULL  REFERENCES product_catalog(product_id),
    quantity INTEGER DEFAULT 0 NOT NULL,
    msrp_currency INTEGER REFERENCES lookup_currency(code),
    msrp_amount FLOAT DEFAULT 0 NOT NULL,
    price_currency INTEGER REFERENCES lookup_currency(code),
    price_amount FLOAT DEFAULT 0 NOT NULL,
    recurring_currency INTEGER REFERENCES lookup_currency(code),
    recurring_amount FLOAT DEFAULT 0 NOT NULL,
    recurring_type INTEGER REFERENCES lookup_recurring_type(code),
    extended_price FLOAT DEFAULT 0 NOT NULL,
    total_price FLOAT DEFAULT 0 NOT NULL,
    status_id INTEGER REFERENCES lookup_order_status(code),
    status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(item_id)
);



CREATE SEQUENCE order_product_ct_status_id_seq AS DECIMAL(27,0);


CREATE TABLE order_product_status(
    order_product_status_id INTEGER NOT NULL,
    order_id INTEGER NOT NULL  REFERENCES order_entry(order_id),
    item_id INTEGER NOT NULL  REFERENCES order_product(item_id),
    status_id INTEGER REFERENCES lookup_order_status(code),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    PRIMARY KEY(order_product_status_id)
);


CREATE SEQUENCE order_product_ct_option_id_seq AS DECIMAL(27,0);


CREATE TABLE order_product_options(
    order_product_option_id INTEGER NOT NULL,
    item_id INTEGER NOT NULL  REFERENCES order_product(item_id),
    product_option_id INTEGER NOT NULL  REFERENCES product_option_map(product_option_id),
    quantity INTEGER DEFAULT 0 NOT NULL,
    price_currency INTEGER REFERENCES lookup_currency(code),
    price_amount FLOAT DEFAULT 0 NOT NULL,
    recurring_currency INTEGER REFERENCES lookup_currency(code),
    recurring_amount FLOAT DEFAULT 0 NOT NULL,
    recurring_type INTEGER REFERENCES lookup_recurring_type(code),
    extended_price FLOAT DEFAULT 0 NOT NULL,
    total_price FLOAT DEFAULT 0 NOT NULL,
    status_id INTEGER REFERENCES lookup_order_status(code),
    PRIMARY KEY(order_product_option_id)
);

CREATE TABLE order_product_option_boolean(
    order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
    "value" CHAR(1) NOT NULL
);


CREATE TABLE order_product_option_float(
    order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
    "value" FLOAT NOT NULL
);


CREATE TABLE order_product_option_timestamp(
    order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
    "value" TIMESTAMP NOT NULL
);


CREATE TABLE order_product_option_integer(
    order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
    "value" INTEGER NOT NULL
);


CREATE TABLE order_product_option_text(
    order_product_option_id INTEGER REFERENCES order_product_options(order_product_option_id),
    "value" CLOB(2G) NOT LOGGED NOT NULL
);


CREATE SEQUENCE lookup_ordera_s_types_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_orderaddress_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE order_address_address_id_seq AS DECIMAL(27,0);


CREATE TABLE order_address(
    address_id INTEGER NOT NULL,
    order_id INTEGER NOT NULL  REFERENCES order_entry(order_id),
    address_type INTEGER REFERENCES lookup_orderaddress_types(code),
    addrline1 VARGRAPHIC(300),
    addrline2 VARGRAPHIC(300),
    addrline3 VARGRAPHIC(300),
    city VARGRAPHIC(300),
    state VARGRAPHIC(300),
    country VARGRAPHIC(300),
    postalcode VARGRAPHIC(40),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    addrline4 VARGRAPHIC(300),
    PRIMARY KEY(address_id)
);


CREATE SEQUENCE lookup_paymen_methods_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_payment_methods(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_credit_rd_type_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_creditcard_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE payment_credi_reditcard_id_seq AS DECIMAL(27,0);


CREATE TABLE payment_creditcard(
    creditcard_id INTEGER NOT NULL,
    card_type INTEGER REFERENCES lookup_creditcard_types(code),
    card_number VARGRAPHIC(300),
    card_security_code VARGRAPHIC(300),
    expiration_month INTEGER,
    expiration_year INTEGER,
    name_on_card VARGRAPHIC(300),
    company_name_on_card VARGRAPHIC(300),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    order_id INTEGER REFERENCES order_entry(order_id),
    PRIMARY KEY(creditcard_id)
);


CREATE SEQUENCE payment_eft_bank_id_seq AS DECIMAL(27,0);


CREATE TABLE payment_eft(
    bank_id INTEGER NOT NULL,
    bank_name VARGRAPHIC(300),
    routing_number VARGRAPHIC(300),
    account_number VARGRAPHIC(300),
    name_on_account VARGRAPHIC(300),
    company_name_on_account VARGRAPHIC(300),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    order_id INTEGER REFERENCES order_entry(order_id),
    PRIMARY KEY(bank_id)
);


CREATE SEQUENCE customer_prod_r_product_id_seq AS DECIMAL(27,0);


CREATE TABLE customer_product(
    customer_product_id INTEGER NOT NULL,
    org_id INTEGER NOT NULL  REFERENCES organization(org_id),
    order_id INTEGER REFERENCES order_entry(order_id),
    order_item_id INTEGER REFERENCES order_product(item_id),
    description VARGRAPHIC(2000),
    status_id INTEGER REFERENCES lookup_order_status(code),
    status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    enabled CHAR(1) DEFAULT '1',
    contact_id INTEGER,    
    PRIMARY KEY(customer_product_id)
);


CREATE SEQUENCE customer_prod_y_history_id_seq AS DECIMAL(27,0);


CREATE TABLE customer_product_history(
    history_id INTEGER NOT NULL,
    customer_product_id INTEGER NOT NULL  REFERENCES customer_product(customer_product_id),
    org_id INTEGER NOT NULL  REFERENCES organization(org_id),
    order_id INTEGER NOT NULL  REFERENCES order_entry(order_id),
    product_start_date TIMESTAMP,
    product_end_date TIMESTAMP,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    order_item_id INTEGER NOT NULL  REFERENCES order_product(item_id),
    contact_id INTEGER,    
    PRIMARY KEY(history_id)
);


CREATE SEQUENCE lookup_payment_status_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_payment_status(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE order_payment_payment_id_seq AS DECIMAL(27,0);


CREATE TABLE order_payment(
    payment_id INTEGER NOT NULL,
    order_id INTEGER NOT NULL  REFERENCES order_entry(order_id),
    payment_method_id INTEGER NOT NULL  REFERENCES lookup_payment_methods(code),
    payment_amount FLOAT,
    authorization_ref_number VARGRAPHIC(30),
    authorization_code VARGRAPHIC(30),
    authorization_date TIMESTAMP,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    order_item_id INTEGER REFERENCES order_product(item_id),
    history_id INTEGER REFERENCES customer_product_history(history_id),
    date_to_process TIMESTAMP,
    creditcard_id INTEGER REFERENCES payment_creditcard(creditcard_id),
    bank_id INTEGER REFERENCES payment_eft(bank_id),
    status_id INTEGER REFERENCES lookup_payment_status(code),
    PRIMARY KEY(payment_id)
);


CREATE SEQUENCE order_payment_nt_status_id_seq AS DECIMAL(27,0);


CREATE TABLE order_payment_status(
    payment_status_id INTEGER NOT NULL,
    payment_id INTEGER NOT NULL  REFERENCES order_payment(payment_id),
    status_id INTEGER REFERENCES lookup_payment_status(code),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    PRIMARY KEY(payment_status_id)
);

-- Table CREDIT_CARD

CREATE SEQUENCE creditcard_creditcard_id_seq;

CREATE TABLE credit_card (
  creditcard_id int NOT NULL,
  card_type INTEGER REFERENCES lookup_creditcard_types(code),
  card_number VARGRAPHIC(300),
  card_security_code VARGRAPHIC(300),
  expiration_month int,
  expiration_year int,
  name_on_card VARGRAPHIC(300),
  company_name_on_card VARGRAPHIC(300),
  entered timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby int NOT NULL REFERENCES "access"(user_id),
  modified timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby int NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (creditcard_id)
);

-- Table LOOKUP_PAYMENT_GATEWAY
CREATE SEQUENCE lookup_payment_gateway_seq;

CREATE TABLE lookup_payment_gateway (
  code int NOT NULL,
  description VARGRAPHIC(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" int DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  constant_id int,
  PRIMARY KEY (code)
);        
-- Table MERCHANT_PAYMENT_GATEWAY
CREATE SEQUENCE merchant_payment_gateway_seq;

CREATE TABLE merchant_payment_gateway (
  merchant_payment_gateway_id int4 NOT NULL DEFAULT nextval('merchant_payment_gateway_seq'),
  gateway_id int REFERENCES lookup_payment_gateway (code),
  merchant_id VARGRAPHIC(300),
  merchant_code VARGRAPHIC(1024),
  entered timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby int NOT NULL,
  modified timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby int NOT NULL,
  PRIMARY KEY (merchant_payment_gateway_id)
);