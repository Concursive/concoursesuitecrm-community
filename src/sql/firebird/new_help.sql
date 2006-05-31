
CREATE GENERATOR help_module_module_id_seq;
CREATE TABLE help_module (
  module_id INTEGER  NOT NULL,
  category_id INTEGER REFERENCES permission_category(category_id),
  module_brief_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  module_detail_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (MODULE_ID)
);

CREATE GENERATOR help_contents_help_id_seq;
CREATE TABLE help_contents (
  help_id INTEGER  NOT NULL,
  category_id INTEGER REFERENCES permission_category(category_id),
  link_module_id INTEGER REFERENCES help_module(module_id),
  "module" VARCHAR(255),
  "section" VARCHAR(255),
  subsection VARCHAR(255),
  title VARCHAR (255),
  description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  nextcontent INTEGER,
  prevcontent INTEGER,
  upcontent INTEGER,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (HELP_ID)
);

-- THESE ARE REQUIRED - Firebird can not reference itself (primary key) during table creation
ALTER TABLE help_contents ADD CONSTRAINT FK_HELP_CONTENTS_ID_NEXT
  FOREIGN KEY (NEXTCONTENT) REFERENCES HELP_CONTENTS
  (HELP_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE help_contents ADD CONSTRAINT FK_HELP_CONTENTS_ID_PREV
  FOREIGN KEY (PREVCONTENT) REFERENCES HELP_CONTENTS
  (HELP_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE help_contents ADD CONSTRAINT FK_HELP_CONTENTS_ID_UP
  FOREIGN KEY (UPCONTENT) REFERENCES HELP_CONTENTS
  (HELP_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Old Name: help_tableof_contents_content_id_seq;
CREATE GENERATOR help_tableof__ts_content_id_seq;
CREATE TABLE help_tableof_contents (
  content_id INTEGER  NOT NULL,
  displaytext VARCHAR (255),
  firstchild INTEGER ,
  nextsibling INTEGER ,
  parent INTEGER ,
  category_id INTEGER REFERENCES permission_category(category_id),
  contentlevel INTEGER NOT NULL,
  contentorder INTEGER NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CONTENT_ID)
);

-- THESE ARE REQUIRED - Firebird can not reference itself (primary key) during table creation
ALTER TABLE help_tableof_contents ADD CONSTRAINT FK_HELP_TABLEOF_ID_FIRST
  FOREIGN KEY (FIRSTCHILD) REFERENCES HELP_TABLEOF_CONTENTS
  (CONTENT_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE help_tableof_contents ADD CONSTRAINT FK_HELP_TABLEOF_ID_NEXT
  FOREIGN KEY (NEXTSIBLING) REFERENCES HELP_TABLEOF_CONTENTS
  (CONTENT_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE help_tableof_contents ADD CONSTRAINT FK_HELP_TABLEOF_ID_PARENT
  FOREIGN KEY (PARENT) REFERENCES HELP_TABLEOF_CONTENTS
  (CONTENT_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Old Name: help_tableofcontentitem_links_link_id_seq;
CREATE GENERATOR help_tableofc_links_link_id_seq;
CREATE TABLE help_tableofcontentitem_links (
  link_id INTEGER NOT NULL,
  global_link_id INTEGER NOT NULL REFERENCES help_tableof_contents(content_id),
  linkto_content_id INTEGER NOT NULL REFERENCES help_contents(help_id),
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (LINK_ID)
);

CREATE GENERATOR lookup_help_features_code_seq;
CREATE TABLE lookup_help_features (
  code INTEGER NOT NULL,
  description VARCHAR(1000) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

CREATE GENERATOR help_features_feature_id_seq;
CREATE TABLE help_features (
  feature_id INTEGER  NOT NULL,
  link_help_id INTEGER NOT NULL REFERENCES help_contents(help_id),
  link_feature_id INTEGER REFERENCES lookup_help_features(code),
  description VARCHAR(1000) NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  completedate TIMESTAMP,
  completedby INTEGER REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 'Y'  NOT NULL ,
  "level" INTEGER DEFAULT 0,
  PRIMARY KEY (FEATURE_ID)
);

-- Old Name: help_related_links_relatedlink_id_seq;
CREATE GENERATOR help_related__elatedlink_id_seq;
CREATE TABLE help_related_links (
  relatedlink_id INTEGER NOT NULL,
  owning_module_id INTEGER REFERENCES  help_module(module_id),
  linkto_content_id INTEGER REFERENCES help_contents(help_id),
  displaytext VARCHAR(255) NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled CHAR(1) DEFAULT 'Y'  NOT NULL,
  PRIMARY KEY (RELATEDLINK_ID)
);

CREATE GENERATOR help_faqs_faq_id_seq;
CREATE TABLE help_faqs (
  faq_id INTEGER NOT NULL,
  owning_module_id INTEGER NOT NULL REFERENCES help_module(module_id),
  question VARCHAR(1000) NOT NULL,
  answer VARCHAR(1000) NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  completedate TIMESTAMP,
  completedby INTEGER REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 'Y'  NOT NULL,
  PRIMARY KEY (FAQ_ID)
);

CREATE GENERATOR help_business_rules_rule_id_seq;
CREATE TABLE help_business_rules (
  rule_id INTEGER  NOT NULL,
  link_help_id INTEGER NOT NULL REFERENCES help_contents(help_id),
  description VARCHAR(1000) NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  completedate TIMESTAMP,
  completedby INTEGER REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 'Y'  NOT NULL,
  PRIMARY KEY (RULE_ID)
);

CREATE GENERATOR help_notes_note_id_seq;
CREATE TABLE help_notes (
  note_id INTEGER  NOT NULL,
  link_help_id INTEGER NOT NULL REFERENCES help_contents(help_id),
  description VARCHAR(1000) NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  completedate TIMESTAMP,
  completedby INTEGER REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 'Y'  NOT NULL,
  CONSTRAINT PK_HELP_NOTES PRIMARY KEY (NOTE_ID)
);

CREATE GENERATOR help_tips_tip_id_seq;
CREATE TABLE help_tips (
  tip_id INTEGER  NOT NULL,
  link_help_id INTEGER NOT NULL REFERENCES help_contents(help_id),
  description VARCHAR(1000) NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled CHAR(1) DEFAULT 'Y'  NOT NULL,
  CONSTRAINT PK_HELP_TIPS PRIMARY KEY (TIP_ID)
);