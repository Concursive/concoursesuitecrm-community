CREATE SEQUENCE module_field__tegorylin_id_seq AS DECIMAL(27,0);

CREATE TABLE module_field_categorylink(
    id INTEGER NOT NULL,
    module_id INTEGER NOT NULL  REFERENCES permission_category(category_id),
    category_id INTEGER NOT NULL  UNIQUE,
    "level" INTEGER DEFAULT 0,
    description CLOB(2G) NOT LOGGED,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id,module_id,category_id)
);



CREATE SEQUENCE custom_field___category_id_seq AS DECIMAL(27,0);


CREATE TABLE custom_field_category(
    module_id INTEGER NOT NULL  REFERENCES module_field_categorylink(category_id),
    category_id INTEGER NOT NULL  UNIQUE,
    category_name VARGRAPHIC(255) NOT NULL,
    "level" INTEGER DEFAULT 0,
    description CLOB(2G) NOT LOGGED,
    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_date TIMESTAMP,
    default_item CHAR(1) DEFAULT '0',
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enabled CHAR(1) DEFAULT '1',
    multiple_records CHAR(1) DEFAULT '0',
    read_only CHAR(1) DEFAULT '0',
    PRIMARY KEY(module_id,category_id)
);


CREATE INDEX custom_field_cat_1
    ON custom_field_category(module_id);


CREATE SEQUENCE custom_field__oup_group_id_seq AS DECIMAL(27,0);


CREATE TABLE custom_field_group(
    category_id INTEGER NOT NULL  REFERENCES custom_field_category(category_id),
    group_id INTEGER NOT NULL  UNIQUE,
    group_name VARGRAPHIC(255) NOT NULL,
    "level" INTEGER DEFAULT 0,
    description CLOB(2G) NOT LOGGED,
    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_date TIMESTAMP,
    entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(category_id,group_id)
);



CREATE INDEX custom_field_grp_1
    ON custom_field_group(category_id);

CREATE SEQUENCE custom_field_info_field_id_seq AS DECIMAL(27,0);
CREATE TABLE custom_field_info(
    group_id INTEGER NOT NULL  REFERENCES custom_field_group(group_id),
    field_id INTEGER NOT NULL  UNIQUE,
    field_name VARGRAPHIC(255) NOT NULL,
    "level" INTEGER DEFAULT 0,
    field_type INTEGER NOT NULL,
    validation_type INTEGER DEFAULT 0,
    required CHAR(1) DEFAULT '0',
    parameters CLOB(2G) NOT LOGGED,
    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_date TIMESTAMP DEFAULT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enabled CHAR(1) DEFAULT '1',
    additional_text VARGRAPHIC(255),
    PRIMARY KEY(group_id,field_id)
);


CREATE INDEX custom_field_inf_1
    ON custom_field_info(group_id);

CREATE SEQUENCE custom_field_lookup_code_seq AS DECIMAL(27,0);
CREATE TABLE custom_field_lookup(
    field_id INTEGER NOT NULL  REFERENCES custom_field_info(field_id),
    code INTEGER NOT NULL,
    description VARGRAPHIC(255) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_date TIMESTAMP,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(field_id,code)
);


CREATE SEQUENCE custom_field__co_record_id_seq AS DECIMAL(27,0);


CREATE TABLE custom_field_record(
    link_module_id INTEGER NOT NULL,
    link_item_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL  REFERENCES custom_field_category(category_id),
    record_id INTEGER NOT NULL  UNIQUE,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(link_module_id,link_item_id,category_id,record_id)
);


CREATE INDEX custom_field_rec_1
    ON custom_field_record(link_module_id,link_item_id,category_id);

CREATE TABLE custom_field_data(
    record_id INTEGER NOT NULL  REFERENCES custom_field_record(record_id),
    field_id INTEGER NOT NULL  REFERENCES custom_field_info(field_id),
    selected_item_id INTEGER DEFAULT 0,
    entered_value CLOB(2G) NOT LOGGED,
    entered_number INTEGER,
    entered_float FLOAT
);


CREATE INDEX custom_field_dat_1
    ON custom_field_data(record_id,field_id);
