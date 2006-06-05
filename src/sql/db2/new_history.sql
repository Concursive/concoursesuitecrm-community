

CREATE SEQUENCE history_history_id_seq AS DECIMAL(27,0);



CREATE TABLE history(
    history_id INTEGER NOT NULL,
    contact_id INTEGER REFERENCES contact(contact_id),
    org_id INTEGER REFERENCES organization(org_id),
    link_object_id INTEGER NOT NULL,
    link_item_id INTEGER,
    status VARGRAPHIC(255),
    "type" VARGRAPHIC(255),
    description CLOB(2G) NOT LOGGED,
    "level" INTEGER DEFAULT 10,
    enabled CHAR(1) DEFAULT '1',
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    PRIMARY KEY(history_id)
);


