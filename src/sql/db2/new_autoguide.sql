
CREATE SEQUENCE autoguide_make_make_id_seq AS DECIMAL(27,0);


CREATE TABLE autoguide_make(
    make_id INTEGER NOT NULL,
    make_name VARGRAPHIC(30),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL,
    PRIMARY KEY(make_id)
);



CREATE SEQUENCE autoguide_model_model_id_seq AS DECIMAL(27,0);


CREATE TABLE autoguide_model(
    model_id INTEGER NOT NULL,
    make_id INTEGER NOT NULL  REFERENCES autoguide_make(make_id),
    model_name VARGRAPHIC(50),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL,
    PRIMARY KEY(model_id)
);


CREATE SEQUENCE autoguide_veh_l_vehicle_id_seq AS DECIMAL(27,0);


CREATE TABLE autoguide_vehicle(
    vehicle_id INTEGER NOT NULL,
    "year" VARGRAPHIC(4) NOT NULL,
    make_id INTEGER NOT NULL  REFERENCES autoguide_make(make_id),
    model_id INTEGER NOT NULL  REFERENCES autoguide_model(model_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL,
    PRIMARY KEY(vehicle_id)
);


CREATE SEQUENCE autoguide_inv_inventory_id_seq AS DECIMAL(27,0);


CREATE TABLE autoguide_inventory(
    inventory_id INTEGER NOT NULL,
    vehicle_id INTEGER NOT NULL  REFERENCES autoguide_vehicle(vehicle_id),
    account_id INTEGER REFERENCES organization(org_id),
    vin VARGRAPHIC(20),
    mileage VARGRAPHIC(20),
    is_new CHAR(1) DEFAULT '0',
    condition VARGRAPHIC(20),
    comments VARGRAPHIC(255),
    stock_no VARGRAPHIC(20),
    ext_color VARGRAPHIC(20),
    int_color VARGRAPHIC(20),
    style VARGRAPHIC(40),
    invoice_price FLOAT,
    selling_price FLOAT,
    selling_price_text VARGRAPHIC(100),
    sold CHAR(1) DEFAULT '0',
    status VARGRAPHIC(20),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL,
    PRIMARY KEY(inventory_id)
);


CREATE SEQUENCE autoguide_opt_ns_option_id_seq AS DECIMAL(27,0);


CREATE TABLE autoguide_options(
    option_id INTEGER NOT NULL,
    option_name VARGRAPHIC(20) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(option_id)
);



CREATE TABLE autoguide_inventory_options(
    inventory_id INTEGER NOT NULL  REFERENCES autoguide_inventory(inventory_id),
    option_id INTEGER NOT NULL
);


CREATE UNIQUE INDEX idx_autog_inv_opt
    ON autoguide_inventory_options(inventory_id,option_id);


CREATE SEQUENCE autoguide_ad_run_ad_run_id_seq AS DECIMAL(27,0);


CREATE TABLE autoguide_ad_run(
    ad_run_id INTEGER NOT NULL,
    inventory_id INTEGER NOT NULL  REFERENCES autoguide_inventory(inventory_id),
    run_date TIMESTAMP NOT NULL,
    ad_type VARGRAPHIC(20),
    include_photo CHAR(1) DEFAULT '0',
    complete_date TIMESTAMP,
    completedby INTEGER DEFAULT -1,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL,
    PRIMARY KEY(ad_run_id)
);


CREATE SEQUENCE autoguide_ad__n_types_code_seq AS DECIMAL(27,0);


CREATE TABLE autoguide_ad_run_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(20) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '0',
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(code)
);

