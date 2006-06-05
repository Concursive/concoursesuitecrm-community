

CREATE SEQUENCE help_module_module_id_seq AS DECIMAL(27,0);



CREATE TABLE help_module(
    module_id INTEGER NOT NULL,
    category_id INTEGER REFERENCES permission_category(category_id),
    module_brief_description CLOB(2G) NOT LOGGED,
    module_detail_description CLOB(2G) NOT LOGGED,
    PRIMARY KEY(module_id)
);


CREATE SEQUENCE help_contents_help_id_seq AS DECIMAL(27,0);



CREATE TABLE help_contents(
    help_id INTEGER NOT NULL,
    category_id INTEGER REFERENCES permission_category(category_id),
    link_module_id INTEGER REFERENCES help_module(module_id),
    "module" VARGRAPHIC(255),
    "section" VARGRAPHIC(255),
    subsection VARGRAPHIC(255),
    title VARCHAR(255),
    description CLOB(2G) NOT LOGGED,
    nextcontent INTEGER REFERENCES help_contents(help_id),
    prevcontent INTEGER REFERENCES help_contents(help_id),
    upcontent INTEGER REFERENCES help_contents(help_id),
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(help_id)
);



CREATE SEQUENCE help_tableof__s_content_id_seq AS DECIMAL(27,0);



CREATE TABLE help_tableof_contents(
    content_id INTEGER NOT NULL,
    displaytext VARCHAR(255),
    firstchild INTEGER REFERENCES help_tableof_contents(content_id),
    nextsibling INTEGER REFERENCES help_tableof_contents(content_id),
    parent INTEGER REFERENCES help_tableof_contents(content_id),
    category_id INTEGER REFERENCES permission_category(category_id),
    contentlevel INTEGER NOT NULL,
    contentorder INTEGER NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(content_id)
);



CREATE SEQUENCE help_tableofc_inks_link_id_seq AS DECIMAL(27,0);



CREATE TABLE help_tableofcontentitem_links(
    link_id INTEGER NOT NULL,
    global_link_id INTEGER NOT NULL  REFERENCES help_tableof_contents(content_id),
    linkto_content_id INTEGER NOT NULL  REFERENCES help_contents(help_id),
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(link_id)
);



CREATE SEQUENCE lookup_help_features_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_help_features(
    code INTEGER NOT NULL,
    description VARGRAPHIC(1000) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(code)
);


CREATE SEQUENCE help_features_feature_id_seq AS DECIMAL(27,0);



CREATE TABLE help_features(
    feature_id INTEGER NOT NULL,
    link_help_id INTEGER NOT NULL  REFERENCES help_contents(help_id),
    link_feature_id INTEGER REFERENCES lookup_help_features(code),
    description VARGRAPHIC(1000) NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    completedate TIMESTAMP,
    completedby INTEGER REFERENCES "access"(user_id),
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    "level" INTEGER DEFAULT 0,
    PRIMARY KEY(feature_id)
);



CREATE SEQUENCE help_related__latedlink_id_seq AS DECIMAL(27,0);



CREATE TABLE help_related_links(
    relatedlink_id INTEGER NOT NULL,
    owning_module_id INTEGER REFERENCES help_module(module_id),
    linkto_content_id INTEGER REFERENCES help_contents(help_id),
    displaytext VARGRAPHIC(255) NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    PRIMARY KEY(relatedlink_id)
);



CREATE SEQUENCE help_faqs_faq_id_seq AS DECIMAL(27,0);



CREATE TABLE help_faqs(
    faq_id INTEGER NOT NULL,
    owning_module_id INTEGER NOT NULL  REFERENCES help_module(module_id),
    question VARGRAPHIC(1000) NOT NULL,
    answer VARGRAPHIC(1000) NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    completedate TIMESTAMP,
    completedby INTEGER REFERENCES "access"(user_id),
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    PRIMARY KEY(faq_id)
);



CREATE SEQUENCE help_business_ules_rule_id_seq AS DECIMAL(27,0);



CREATE TABLE help_business_rules(
    rule_id INTEGER NOT NULL,
    link_help_id INTEGER NOT NULL  REFERENCES help_contents(help_id),
    description VARGRAPHIC(1000) NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    completedate TIMESTAMP,
    completedby INTEGER REFERENCES "access"(user_id),
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    PRIMARY KEY(rule_id)
);



CREATE SEQUENCE help_notes_note_id_seq AS DECIMAL(27,0);



CREATE TABLE help_notes(
    note_id INTEGER NOT NULL,
    link_help_id INTEGER NOT NULL  REFERENCES help_contents(help_id),
    description VARGRAPHIC(1000) NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    completedate TIMESTAMP,
    completedby INTEGER REFERENCES "access"(user_id),
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    CONSTRAINT PK_HELP_NOTES PRIMARY KEY(note_id)
);



CREATE SEQUENCE help_tips_tip_id_seq AS DECIMAL(27,0);



CREATE TABLE help_tips(
    tip_id INTEGER NOT NULL,
    link_help_id INTEGER NOT NULL  REFERENCES help_contents(help_id),
    description VARGRAPHIC(1000) NOT NULL,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    CONSTRAINT PK_HELP_TIPS PRIMARY KEY(tip_id)
);


