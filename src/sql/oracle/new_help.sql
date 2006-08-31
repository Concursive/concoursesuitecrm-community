
CREATE SEQUENCE help_module_module_id_seq;
CREATE TABLE help_module (
  module_id INTEGER  NOT NULL,
  category_id INTEGER REFERENCES permission_category(category_id),
  module_brief_description CLOB,
  module_detail_description CLOB,
  PRIMARY KEY (MODULE_ID)
);

CREATE SEQUENCE help_contents_help_id_seq;
CREATE TABLE help_contents (
  help_id INTEGER NOT NULL,
  category_id INTEGER REFERENCES permission_category(category_id),
  link_module_id INTEGER REFERENCES help_module(module_id),
  "module" NVARCHAR2(255),
  "section" NVARCHAR2(255),
  subsection NVARCHAR2(255),
  title VARCHAR (255),
  description CLOB,
  nextcontent INTEGER REFERENCES help_contents(help_id),
  prevcontent INTEGER REFERENCES help_contents(help_id),
  upcontent INTEGER REFERENCES help_contents(help_id),
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (HELP_ID)
);

-- Old Name: help_tableof_contents_content_id_seq;
CREATE SEQUENCE help_tableof__s_content_id_seq;
CREATE TABLE help_tableof_contents (
  content_id INTEGER  NOT NULL,
  displaytext VARCHAR (255),
  firstchild INTEGER REFERENCES help_tableof_contents(content_id),
  nextsibling INTEGER REFERENCES help_tableof_contents(content_id),
  parent INTEGER REFERENCES help_tableof_contents(content_id),
  category_id INTEGER REFERENCES permission_category(category_id),
  contentlevel INTEGER NOT NULL,
  contentorder INTEGER NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CONTENT_ID)
);

-- Old Name: help_tableofcontentitem_links_link_id_seq;
CREATE SEQUENCE help_tableofc_inks_link_id_seq;
CREATE TABLE help_tableofcontentitem_links (
  link_id INTEGER NOT NULL,
  global_link_id INTEGER NOT NULL REFERENCES help_tableof_contents(content_id),
  linkto_content_id INTEGER NOT NULL REFERENCES help_contents(help_id),
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (LINK_ID)
);


CREATE SEQUENCE lookup_help_features_code_seq;
CREATE TABLE lookup_help_features (
  code INTEGER NOT NULL,
  description NVARCHAR2(1000) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE help_features_feature_id_seq;
CREATE TABLE help_features (
  feature_id INTEGER  NOT NULL,
  link_help_id INTEGER NOT NULL REFERENCES help_contents(help_id),
  link_feature_id INTEGER REFERENCES lookup_help_features(code),
  description NVARCHAR2(1000) NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  completedate TIMESTAMP,
  completedby INTEGER REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 1  NOT NULL ,
  "level" INTEGER DEFAULT 0,
  PRIMARY KEY (FEATURE_ID)
);

-- Old Name: help_related_links_relatedlink_id_seq;
CREATE SEQUENCE help_related__latedlink_id_seq;
CREATE TABLE help_related_links (
  relatedlink_id INTEGER NOT NULL,
  owning_module_id INTEGER REFERENCES  help_module(module_id),
  linkto_content_id INTEGER REFERENCES help_contents(help_id),
  displaytext NVARCHAR2(255) NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled CHAR(1) DEFAULT 1  NOT NULL,
  PRIMARY KEY (RELATEDLINK_ID)
);

CREATE SEQUENCE help_faqs_faq_id_seq;
CREATE TABLE help_faqs (
  faq_id INTEGER NOT NULL,
  owning_module_id INTEGER NOT NULL REFERENCES help_module(module_id),
  question NVARCHAR2(1000) NOT NULL,
  answer NVARCHAR2(1000) NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  completedate TIMESTAMP,
  completedby INTEGER REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 1  NOT NULL,
  PRIMARY KEY (FAQ_ID)
);

CREATE SEQUENCE help_business_ules_rule_id_seq;
CREATE TABLE help_business_rules (
  rule_id INTEGER  NOT NULL,
  link_help_id INTEGER NOT NULL REFERENCES help_contents(help_id),
  description NVARCHAR2(1000) NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  completedate TIMESTAMP,
  completedby INTEGER REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 1  NOT NULL,
  PRIMARY KEY (RULE_ID)
);

CREATE SEQUENCE help_notes_note_id_seq;
CREATE TABLE help_notes (
  note_id INTEGER  NOT NULL,
  link_help_id INTEGER NOT NULL REFERENCES help_contents(help_id),
  description NVARCHAR2(1000) NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  completedate TIMESTAMP,
  completedby INTEGER REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 1  NOT NULL,
  CONSTRAINT PK_HELP_NOTES PRIMARY KEY (NOTE_ID)
);

CREATE SEQUENCE help_tips_tip_id_seq;
CREATE TABLE help_tips (
  tip_id INTEGER  NOT NULL,
  link_help_id INTEGER NOT NULL REFERENCES help_contents(help_id),
  description NVARCHAR2(1000) NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled CHAR(1) DEFAULT 1  NOT NULL,
  CONSTRAINT PK_HELP_TIPS PRIMARY KEY (TIP_ID)
);
