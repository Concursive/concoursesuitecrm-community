

CREATE SEQUENCE lookup_revenue_types_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_revenue_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_revenu_etail_t_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_revenuedetail_types(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE revenue_id_seq AS DECIMAL(27,0);



CREATE TABLE revenue(
    id INTEGER NOT NULL,
    org_id INTEGER REFERENCES organization(org_id),
    transaction_id INTEGER DEFAULT -1,
    "month" INTEGER DEFAULT -1,
    "year" INTEGER DEFAULT -1,
    amount FLOAT DEFAULT 0,
    "type" INTEGER REFERENCES lookup_revenue_types(code),
    owner INTEGER REFERENCES "access"(user_id),
    description VARGRAPHIC(255),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    PRIMARY KEY(id)
);



CREATE SEQUENCE revenue_detail_id_seq AS DECIMAL(27,0);



CREATE TABLE revenue_detail(
    id INTEGER NOT NULL,
    revenue_id INTEGER REFERENCES revenue(id),
    amount FLOAT DEFAULT 0,
    "type" INTEGER REFERENCES lookup_revenue_types(code),
    owner INTEGER REFERENCES "access"(user_id),
    description VARGRAPHIC(255),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    PRIMARY KEY(id)
);

