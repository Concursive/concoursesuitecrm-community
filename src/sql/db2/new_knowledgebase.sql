
CREATE SEQUENCE knowledge_base_kb_id_seq AS DECIMAL(27,0);
CREATE TABLE knowledge_base(
    kb_id INTEGER NOT NULL  PRIMARY KEY,
    category_id INTEGER REFERENCES ticket_category(id),
    title VARGRAPHIC(255) NOT NULL,
    description CLOB(2G) NOT LOGGED,
    item_id INTEGER REFERENCES project_files(item_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id)
);
