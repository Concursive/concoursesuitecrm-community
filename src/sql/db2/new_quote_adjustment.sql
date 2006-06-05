
CREATE SEQUENCE lookup_quote_delivery_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_quote_delivery(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_quote__ndition_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_quote_condition(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE quote_group_group_id_seq AS DECIMAL(27,0)
    START WITH 1000
    MINVALUE 1000;


CREATE TABLE quote_group(
    group_id INTEGER NOT NULL,
    unused CHAR(1),
    PRIMARY KEY(group_id)
);


ALTER TABLE quote_entry
 ADD COLUMN product_id INTEGER REFERENCES product_catalog(product_id);


ALTER TABLE quote_entry
 ADD COLUMN customer_product_id INTEGER REFERENCES customer_product(customer_product_id);


ALTER TABLE quote_entry
 ADD COLUMN opp_id INTEGER REFERENCES opportunity_header(opp_id);


ALTER TABLE quote_entry
 ADD COLUMN "version" VARGRAPHIC(255) DEFAULT G'0' NOT NULL;


ALTER TABLE quote_entry
 ADD COLUMN group_id INTEGER REFERENCES quote_group(group_id);


ALTER TABLE quote_entry ADD  CHECK (group_id IS NOT NULL);


ALTER TABLE quote_entry
 ADD COLUMN delivery_id INTEGER REFERENCES lookup_quote_delivery(code);


ALTER TABLE quote_entry
 ADD COLUMN email_address CLOB(2G) NOT LOGGED;


ALTER TABLE quote_entry
 ADD COLUMN phone_number CLOB(2G) NOT LOGGED;


ALTER TABLE quote_entry
 ADD COLUMN address CLOB(2G) NOT LOGGED;


ALTER TABLE quote_entry
 ADD COLUMN fax_number CLOB(2G) NOT LOGGED;


ALTER TABLE quote_entry
 ADD COLUMN submit_action INTEGER;


ALTER TABLE quote_entry
 ADD COLUMN closed TIMESTAMP;


ALTER TABLE quote_entry
 ADD COLUMN show_total CHAR(1) DEFAULT '1';


ALTER TABLE quote_entry
 ADD COLUMN show_subtotal CHAR(1) DEFAULT '1';


ALTER TABLE quote_entry
 ADD COLUMN logo_file_id INTEGER REFERENCES project_files(item_id);


ALTER TABLE quote_entry
 ADD COLUMN trashed_date TIMESTAMP;


CREATE SEQUENCE quote_condition_map_id_seq AS DECIMAL(27,0);


CREATE TABLE quote_condition(
    map_id INTEGER NOT NULL,
    quote_id INTEGER NOT NULL  REFERENCES quote_entry(quote_id),
    condition_id INTEGER NOT NULL  REFERENCES lookup_quote_condition(code),
    PRIMARY KEY(map_id)
);


CREATE SEQUENCE quotelog_id_seq AS DECIMAL(27,0);


CREATE TABLE quotelog(
    id INTEGER NOT NULL,
    quote_id INTEGER NOT NULL  REFERENCES quote_entry(quote_id),
    source_id INTEGER REFERENCES lookup_quote_source(code),
    status_id INTEGER REFERENCES lookup_quote_status(code),
    terms_id INTEGER REFERENCES lookup_quote_terms(code),
    type_id INTEGER REFERENCES lookup_quote_type(code),
    delivery_id INTEGER REFERENCES lookup_quote_delivery(code),
    notes CLOB(2G) NOT LOGGED,
    grand_total FLOAT,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
    issued_date TIMESTAMP,
    submit_action INTEGER,
    closed TIMESTAMP,
    PRIMARY KEY(id)
);


CREATE SEQUENCE lookup_quote_remarks_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_quote_remarks(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE quote_remark_map_id_seq AS DECIMAL(27,0);


CREATE TABLE quote_remark(
    map_id INTEGER NOT NULL,
    quote_id INTEGER NOT NULL  REFERENCES quote_entry(quote_id),
    remark_id INTEGER NOT NULL  REFERENCES lookup_quote_remarks(code),
    PRIMARY KEY(map_id)
);


CREATE SEQUENCE quote_notes_notes_id_seq AS DECIMAL(27,0);


CREATE TABLE quote_notes(
    notes_id INTEGER NOT NULL,
    quote_id INTEGER REFERENCES quote_entry(quote_id),
    notes CLOB(2G) NOT LOGGED,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY(notes_id)
);
