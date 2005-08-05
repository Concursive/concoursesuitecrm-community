 
CREATE SEQUENCE help_module_module_id_seq;
CREATE TABLE help_module (
  module_id INT  PRIMARY KEY,
  category_id INT REFERENCES permission_category(category_id), 
  module_brief_description CLOB,
  module_detail_description CLOB
);

CREATE SEQUENCE help_contents_help_id_seq;
CREATE TABLE help_contents (
  help_id INT  PRIMARY KEY,
  category_id INT REFERENCES permission_category(category_id), 
  link_module_id INT REFERENCES help_module(module_id),
  "module" VARCHAR(255),
  "section" VARCHAR(255),
  subsection VARCHAR(255),
  title VARCHAR (255),
  description CLOB,
  nextcontent INT REFERENCES help_contents(help_id),
  prevcontent INT REFERENCES help_contents(help_id),
  upcontent INT REFERENCES help_contents(help_id),
  enteredby INT REFERENCES access  NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INT REFERENCES access  NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE help_tableof_contents_content_id_seq;
CREATE TABLE help_tableof_contents (
  content_id INT  PRIMARY KEY,
  displaytext VARCHAR (255),
  firstchild INT REFERENCES help_tableof_contents (content_id),
  nextsibling INT REFERENCES help_tableof_contents (content_id),
  parent INT REFERENCES help_tableof_contents (content_id),
  category_id INT REFERENCES permission_category(category_id),
  contentlevel INT NOT NULL,
  contentorder INT NOT NULL,
  enteredby INT REFERENCES access  NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INT REFERENCES access  NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE help_tableofcontentitem_links_link_id_seq;
CREATE TABLE help_tableofcontentitem_links (
  link_id INT PRIMARY KEY,
  global_link_id INT REFERENCES help_tableof_contents(content_id)  NOT NULL ,
  linkto_content_id INT REFERENCES help_contents(help_id)  NOT NULL ,
  enteredby INT REFERENCES access  NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INT REFERENCES access  NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_help_features_code_seq;
CREATE TABLE lookup_help_features (
  code INT PRIMARY KEY,
  description VARCHAR(1000) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE help_features_feature_id_seq;
CREATE TABLE help_features (
  feature_id INT  PRIMARY KEY,
  link_help_id INT REFERENCES help_contents(help_id)  NOT NULL ,
  link_feature_id INT REFERENCES lookup_help_features(code),
  description VARCHAR(1000) NOT NULL,
  enteredby INT REFERENCES access(user_id)  NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INT REFERENCES access(user_id)  NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  completedate TIMESTAMP,
  completedby INT REFERENCES access(user_id),
  enabled boolean DEFAULT true  NOT NULL ,
  "level" INT DEFAULT 0
);

CREATE SEQUENCE help_related_links_relatedlink_id_seq;
CREATE TABLE help_related_links (
  relatedlink_id INT PRIMARY KEY,
  owning_module_id INT REFERENCES  help_module(module_id),
  linkto_content_id INT REFERENCES help_contents(help_id),
  displaytext VARCHAR(255) NOT NULL,
  enteredby INT REFERENCES access(user_id)  NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INT REFERENCES access(user_id)  NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled boolean DEFAULT true  NOT NULL 
);

CREATE SEQUENCE help_faqs_faq_id_seq;
CREATE TABLE help_faqs (
  faq_id INT PRIMARY KEY,
  owning_module_id INT REFERENCES help_module(module_id)  NOT NULL ,
  question VARCHAR(1000) NOT NULL,
  answer VARCHAR(1000) NOT NULL,
  enteredby INT REFERENCES access(user_id)  NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INT REFERENCES access(user_id)  NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  completedate TIMESTAMP,
  completedby INT REFERENCES access(user_id),
  enabled boolean DEFAULT true  NOT NULL 
);

CREATE SEQUENCE help_business_rules_rule_id_seq;
CREATE TABLE help_business_rules (
  rule_id INT  PRIMARY KEY,
  link_help_id INT REFERENCES help_contents(help_id)NOT NULL,
  description VARCHAR(1000) NOT NULL,
  enteredby INT REFERENCES access(user_id)  NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INT REFERENCES access(user_id)  NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  completedate TIMESTAMP,
  completedby INT REFERENCES access(user_id),
  enabled boolean DEFAULT true  NOT NULL 
);

CREATE SEQUENCE help_notes_note_id_seq;
CREATE TABLE help_notes (
  note_id INT  PRIMARY KEY,
  link_help_id INT REFERENCES help_contents(help_id)  NOT NULL,
  description VARCHAR(1000) NOT NULL,
  enteredby INT REFERENCES access(user_id)  NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INT REFERENCES access(user_id)  NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  completedate TIMESTAMP,
  completedby INT REFERENCES access(user_id),
  enabled boolean DEFAULT true  NOT NULL 
);

CREATE SEQUENCE help_tips_tip_id_seq;
CREATE TABLE help_tips (
  tip_id INT  PRIMARY KEY,
  link_help_id INT REFERENCES help_contents(help_id)  NOT NULL , 
  description VARCHAR(1000) NOT NULL,
  enteredby INT REFERENCES access(user_id)  NOT NULL ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INT REFERENCES access(user_id)  NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enabled boolean DEFAULT true  NOT NULL 
);
