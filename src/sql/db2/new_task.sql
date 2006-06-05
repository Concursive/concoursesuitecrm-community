

CREATE SEQUENCE lookup_task_priority_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_task_priority(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_task_loe_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_task_loe(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_task_category_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_task_category(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_ticket_ask_cat_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_ticket_task_category(
    code INTEGER NOT NULL  PRIMARY KEY,
    description VARGRAPHIC(255) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1'
);


CREATE SEQUENCE task_task_id_seq AS DECIMAL(27,0);



CREATE TABLE task(
    task_id INTEGER NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    priority INTEGER NOT NULL  REFERENCES lookup_task_priority,
    description VARGRAPHIC(80),
    duedate TIMESTAMP,
    reminderid INTEGER,
    notes CLOB(2G) NOT LOGGED,
    sharing INTEGER NOT NULL,
    complete CHAR(1) DEFAULT '0' NOT NULL,
    enabled CHAR(1) DEFAULT '0' NOT NULL,
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedby INTEGER REFERENCES "access"(user_id),
    estimatedloe FLOAT,
    estimatedloetype INTEGER REFERENCES lookup_task_loe,
    "type" INTEGER DEFAULT 1,
    owner INTEGER REFERENCES "access"(user_id),
    completedate TIMESTAMP,
    category_id INTEGER REFERENCES lookup_task_category,
    duedate_timezone VARGRAPHIC(255),
    trashed_date TIMESTAMP,
    ticket_task_category_id INTEGER REFERENCES lookup_ticket_task_category(code),
    PRIMARY KEY(task_id)
);



CREATE TABLE tasklink_contact(
    task_id INTEGER NOT NULL  REFERENCES task,
    contact_id INTEGER NOT NULL  REFERENCES contact(contact_id),
    notes CLOB(2G) NOT LOGGED
);



CREATE TABLE tasklink_ticket(
    task_id INTEGER NOT NULL  REFERENCES task,
    ticket_id INTEGER NOT NULL  REFERENCES ticket(ticketid),
    category_id INTEGER REFERENCES lookup_ticket_task_category(code)
);



CREATE TABLE tasklink_project(
    task_id INTEGER NOT NULL  REFERENCES task,
    project_id INTEGER NOT NULL  REFERENCES projects(project_id)
);



CREATE TABLE taskcategory_project(
    category_id INTEGER NOT NULL  REFERENCES lookup_task_category,
    project_id INTEGER NOT NULL  REFERENCES projects(project_id)
);



CREATE TABLE taskcategorylink_news(
    news_id INTEGER NOT NULL  REFERENCES project_news(news_id),
    category_id INTEGER NOT NULL  REFERENCES lookup_task_category
);

