CREATE SEQUENCE lookup_currency_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_currency(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);

CREATE SEQUENCE lookup_produc_categor_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_product_category_type(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE product_categ__category_id_seq AS DECIMAL(27,0);


CREATE TABLE product_category(
    category_id INTEGER NOT NULL,
    parent_id INTEGER REFERENCES product_category(category_id),
    category_name VARGRAPHIC(255) NOT NULL,
    abbreviation VARGRAPHIC(30),
    short_description CLOB(2G) NOT LOGGED,
    long_description CLOB(2G) NOT LOGGED,
    type_id INTEGER REFERENCES lookup_product_category_type(code),
    thumbnail_image_id INTEGER REFERENCES project_files(item_id),
    small_image_id INTEGER REFERENCES project_files(item_id),
    large_image_id INTEGER REFERENCES project_files(item_id),
    list_order INTEGER DEFAULT 10,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    start_date TIMESTAMP,
    expiration_date TIMESTAMP,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    import_id INTEGER REFERENCES import(import_id),
    status_id INTEGER,
    PRIMARY KEY(category_id)
);


CREATE SEQUENCE product_category_map_id_seq AS DECIMAL(27,0);


CREATE TABLE product_category_map(
    id INTEGER NOT NULL,
    category1_id INTEGER NOT NULL  REFERENCES product_category(category_id),
    category2_id INTEGER NOT NULL  REFERENCES product_category(category_id),
    PRIMARY KEY(id)
);


CREATE SEQUENCE lookup_product_type_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_product_type(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_produc_manufac_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_product_manufacturer(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_product_format_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_product_format(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_produc_shippin_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_product_shipping(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_produc_ship_ti_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_product_ship_time(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_product_tax_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_product_tax(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_recurring_type_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_recurring_type(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE product_catalog_product_id_seq AS DECIMAL(27,0);


CREATE TABLE product_catalog(
    product_id INTEGER NOT NULL,
    parent_id INTEGER REFERENCES product_catalog(product_id),
    product_name VARGRAPHIC(255) NOT NULL,
    abbreviation VARGRAPHIC(30),
    short_description CLOB(2G) NOT LOGGED,
    long_description CLOB(2G) NOT LOGGED,
    type_id INTEGER REFERENCES lookup_product_type(code),
    special_notes VARGRAPHIC(255),
    sku VARGRAPHIC(40),
    in_stock CHAR(1) DEFAULT '1' NOT NULL,
    format_id INTEGER REFERENCES lookup_product_format(code),
    shipping_id INTEGER REFERENCES lookup_product_shipping(code),
    estimated_ship_time INTEGER REFERENCES lookup_product_ship_time(code),
    thumbnail_image_id INTEGER REFERENCES project_files(item_id),
    small_image_id INTEGER REFERENCES project_files(item_id),
    large_image_id INTEGER REFERENCES project_files(item_id),
    list_order INTEGER DEFAULT 10,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    start_date TIMESTAMP DEFAULT NULL,
    expiration_date TIMESTAMP DEFAULT NULL,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    manufacturer_id INTEGER REFERENCES lookup_product_manufacturer(code),
    trashed_date TIMESTAMP,
    "active" CHAR(1) DEFAULT '1' NOT NULL,
    comments VARGRAPHIC(255),
    import_id INTEGER REFERENCES import(import_id),
    status_id INTEGER,
    PRIMARY KEY(product_id)
);


CREATE SEQUENCE product_catal_ing_price_id_seq AS DECIMAL(27,0);


CREATE TABLE product_catalog_pricing(
    price_id INTEGER NOT NULL,
    product_id INTEGER REFERENCES product_catalog(product_id),
    tax_id INTEGER REFERENCES lookup_product_tax(code),
    msrp_currency INTEGER REFERENCES lookup_currency(code),
    msrp_amount FLOAT DEFAULT 0 NOT NULL,
    price_currency INTEGER REFERENCES lookup_currency(code),
    price_amount FLOAT DEFAULT 0,
    recurring_currency INTEGER REFERENCES lookup_currency(code),
    recurring_amount FLOAT DEFAULT 0 NOT NULL,
    recurring_type INTEGER REFERENCES lookup_recurring_type(code),
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    start_date TIMESTAMP DEFAULT NULL,
    expiration_date TIMESTAMP DEFAULT NULL,
    enabled CHAR(1) DEFAULT '1',
    cost_currency INTEGER REFERENCES lookup_currency(code),
    cost_amount FLOAT DEFAULT 0 NOT NULL,
    PRIMARY KEY(price_id)
);



CREATE SEQUENCE package_package_id_seq AS DECIMAL(27,0);


CREATE TABLE package(
    package_id INTEGER NOT NULL,
    category_id INTEGER REFERENCES product_category(category_id),
    package_name VARGRAPHIC(255) NOT NULL,
    abbreviation VARGRAPHIC(30),
    short_description CLOB(2G) NOT LOGGED,
    long_description CLOB(2G) NOT LOGGED,
    thumbnail_image_id INTEGER REFERENCES project_files(item_id),
    small_image_id INTEGER REFERENCES project_files(item_id),
    large_image_id INTEGER REFERENCES project_files(item_id),
    list_order INTEGER DEFAULT 10,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    start_date TIMESTAMP,
    expiration_date TIMESTAMP,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    PRIMARY KEY(package_id)
);



CREATE SEQUENCE package_products_map_id_seq AS DECIMAL(27,0);


CREATE TABLE package_products_map(
    id INTEGER NOT NULL,
    package_id INTEGER NOT NULL  REFERENCES package(package_id),
    product_id INTEGER NOT NULL  REFERENCES product_catalog(product_id),
    description CLOB(2G) NOT LOGGED,
    msrp_currency INTEGER REFERENCES lookup_currency(code),
    msrp_amount FLOAT DEFAULT 0 NOT NULL,
    price_currency INTEGER REFERENCES lookup_currency(code),
    price_amount FLOAT DEFAULT 0 NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    start_date TIMESTAMP DEFAULT NULL,
    expiration_date TIMESTAMP DEFAULT NULL,
    PRIMARY KEY(id)
);


CREATE SEQUENCE product_catal_egory_map_id_seq AS DECIMAL(27,0);


CREATE TABLE product_catalog_category_map(
    id INTEGER NOT NULL,
    product_id INTEGER NOT NULL  REFERENCES product_catalog(product_id),
    category_id INTEGER NOT NULL  REFERENCES product_category(category_id),
    PRIMARY KEY(id)
);


CREATE SEQUENCE lookup_produc_conf_re_code_seq AS DECIMAL(27,0);

CREATE TABLE lookup_product_conf_result(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE product_optio_figurator_id_seq AS DECIMAL(27,0);


CREATE TABLE product_option_configurator(
    configurator_id INTEGER NOT NULL,
    short_description CLOB(2G) NOT LOGGED,
    long_description CLOB(2G) NOT LOGGED,
    class_name VARGRAPHIC(255),
    result_type INTEGER NOT NULL  REFERENCES lookup_product_conf_result(code),
    configurator_name VARGRAPHIC(300) NOT NULL,
    PRIMARY KEY(configurator_id)
);


CREATE SEQUENCE product_option_option_id_seq AS DECIMAL(27,0);


CREATE TABLE product_option(
    option_id INTEGER NOT NULL,
    configurator_id INTEGER NOT NULL  REFERENCES product_option_configurator(configurator_id),
    parent_id INTEGER REFERENCES product_option(option_id),
    short_description VARGRAPHIC(2000),
    long_description CLOB(2G) NOT LOGGED,
    allow_customer_configure CHAR(1) DEFAULT '0' NOT NULL,
    allow_user_configure CHAR(1) DEFAULT '0' NOT NULL,
    required CHAR(1) DEFAULT '0' NOT NULL,
    start_date TIMESTAMP DEFAULT NULL,
    end_date TIMESTAMP DEFAULT NULL,
    enabled CHAR(1) DEFAULT '0',
    option_name VARGRAPHIC(300) NOT NULL,
    has_range CHAR(1) DEFAULT '0',
    has_multiplier CHAR(1) DEFAULT '0',
    PRIMARY KEY(option_id)
);


CREATE SEQUENCE product_optio_ues_value_id_seq AS DECIMAL(27,0);


CREATE TABLE product_option_values(
    value_id INTEGER NOT NULL,
    option_id INTEGER NOT NULL  REFERENCES product_option(option_id),
    result_id INTEGER NOT NULL,
    description CLOB(2G) NOT LOGGED,
    msrp_currency INTEGER REFERENCES lookup_currency(code),
    msrp_amount FLOAT DEFAULT 0 NOT NULL,
    price_currency INTEGER REFERENCES lookup_currency(code),
    price_amount FLOAT DEFAULT 0 NOT NULL,
    recurring_currency INTEGER REFERENCES lookup_currency(code),
    recurring_amount FLOAT DEFAULT 0 NOT NULL,
    recurring_type INTEGER REFERENCES lookup_recurring_type(code),
    enabled CHAR(1) DEFAULT '1',
    "value" FLOAT DEFAULT 0,
    multiplier FLOAT DEFAULT 1,
    range_min INTEGER DEFAULT 1,
    range_max INTEGER DEFAULT 1,
    cost_currency INTEGER REFERENCES lookup_currency(code),
    cost_amount FLOAT DEFAULT 0 NOT NULL,
    PRIMARY KEY(value_id)
);


CREATE INDEX idx_pr_opt_val
    ON product_option_values(option_id,result_id);


CREATE SEQUENCE product_optio_ct_option_id_seq AS DECIMAL(27,0);


CREATE TABLE product_option_map(
    product_option_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL  REFERENCES product_catalog(product_id),
    option_id INTEGER NOT NULL  REFERENCES product_option(option_id),
    PRIMARY KEY(product_option_id)
);


CREATE TABLE product_option_boolean(
    product_option_id INTEGER NOT NULL  REFERENCES product_option(option_id),
    "value" CHAR(1) NOT NULL,
    id INTEGER
);


CREATE TABLE product_option_float(
    product_option_id INTEGER NOT NULL  REFERENCES product_option(option_id),
    "value" FLOAT NOT NULL,
    id INTEGER
);


CREATE TABLE product_option_timestamp(
    product_option_id INTEGER NOT NULL  REFERENCES product_option(option_id),
    "value" TIMESTAMP NOT NULL,
    id INTEGER
);


CREATE TABLE product_option_integer(
    product_option_id INTEGER NOT NULL  REFERENCES product_option(option_id),
    "value" INTEGER NOT NULL,
    id INTEGER
);


CREATE TABLE product_option_text(
    product_option_id INTEGER NOT NULL  REFERENCES product_option(option_id),
    "value" CLOB(2G) NOT LOGGED NOT NULL,
    id INTEGER
);


CREATE SEQUENCE lookup_produc_keyword_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_product_keyword(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE TABLE product_keyword_map(
    product_id INTEGER NOT NULL  REFERENCES product_catalog(product_id),
    keyword_id INTEGER NOT NULL  REFERENCES lookup_product_keyword(code)
);


CREATE INDEX idx_pr_key_map
    ON product_keyword_map(product_id,keyword_id);

