
CREATE SEQUENCE saved_criterialist_id_seq;
CREATE TABLE saved_criterialist (
  id INTEGER  NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  owner INTEGER NOT NULL REFERENCES "access"(user_id),
  name NVARCHAR2(80) NOT NULL,
  contact_source INTEGER DEFAULT -1,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  PRIMARY KEY (ID)
);


CREATE SEQUENCE campaign_campaign_id_seq;
CREATE TABLE campaign (
  campaign_id INTEGER NOT NULL,
  name NVARCHAR2(80) NOT NULL,
  description NVARCHAR2(255),
  list_id INTEGER,
  message_id INTEGER DEFAULT -1,
  reply_addr NVARCHAR2(255) ,
  subject NVARCHAR2(255) ,
  "message" CLOB,
  status_id INTEGER DEFAULT 0,
  status NVARCHAR2(255),
  "active" CHAR(1) DEFAULT 0,
  active_date TIMESTAMP ,
  send_method_id INTEGER DEFAULT -1 NOT NULL,
  inactive_date TIMESTAMP ,
  approval_date TIMESTAMP ,
  approvedby INTEGER REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  "type" INTEGER DEFAULT 1,
  active_date_timezone NVARCHAR2(255),
  cc NVARCHAR2(1024),
  bcc NVARCHAR2(1024),
  trashed_date TIMESTAMP,
  PRIMARY KEY (CAMPAIGN_ID)
);


CREATE SEQUENCE campaign_run_id_seq;
CREATE TABLE campaign_run (
  id INTEGER NOT NULL,
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  status INTEGER DEFAULT 0 NOT NULL,
  run_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  total_contacts INTEGER DEFAULT 0,
  total_sent INTEGER DEFAULT 0,
  total_replied INTEGER DEFAULT 0,
  total_bounced INTEGER DEFAULT 0,
  PRIMARY KEY (ID)
);

CREATE SEQUENCE excluded_recipient_id_seq;
CREATE TABLE excluded_recipient (
  id INTEGER NOT NULL,
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  contact_id INTEGER NOT NULL REFERENCES contact(contact_id),
  PRIMARY KEY (ID)
);

CREATE TABLE campaign_list_groups (
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  group_id INTEGER NOT NULL REFERENCES saved_criterialist(id)
);

CREATE SEQUENCE active_campaign_groups_id_seq;
CREATE TABLE active_campaign_groups (
  id INTEGER NOT NULL,
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  groupname NVARCHAR2(80) NOT NULL,
  groupcriteria CLOB,
  PRIMARY KEY (ID)
);


CREATE SEQUENCE scheduled_recipient_id_seq;
CREATE TABLE scheduled_recipient (
  id INTEGER NOT NULL,
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  contact_id INTEGER NOT NULL REFERENCES contact(contact_id),
  run_id INTEGER DEFAULT -1,
  status_id INTEGER DEFAULT 0,
  status NVARCHAR2(255),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  scheduled_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  sent_date TIMESTAMP DEFAULT NULL,
  reply_date TIMESTAMP DEFAULT NULL,
  bounce_date TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (ID)
);

CREATE SEQUENCE lookup_survey_types_code_seq;
CREATE TABLE lookup_survey_types (
  code INTEGER NOT NULL,
  description NVARCHAR2(50) NOT NULL,
  default_item CHAR(1) DEFAULT 0,
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (CODE)
);

CREATE SEQUENCE survey_survey_id_seq;
CREATE TABLE survey (
  survey_id INTEGER NOT NULL,
  name NVARCHAR2(80) NOT NULL,
  description NVARCHAR2(255),
  intro CLOB,
  outro CLOB,
  itemLength INTEGER DEFAULT -1,
  "type" INTEGER DEFAULT -1,
  enabled CHAR(1) DEFAULT 1 NOT NULL ,
  status INTEGER  DEFAULT -1 NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (SURVEY_ID)
);

CREATE TABLE campaign_survey_link (
  campaign_id INTEGER REFERENCES campaign(campaign_id),
  survey_id INTEGER REFERENCES survey(survey_id)
);

CREATE SEQUENCE survey_questi__question_id_seq;
CREATE TABLE survey_questions (
  question_id INTEGER NOT NULL,
  survey_id INTEGER NOT NULL REFERENCES survey(survey_id),
  "type" INTEGER NOT NULL REFERENCES lookup_survey_types(code),
  description NVARCHAR2(255),
  required CHAR(1) DEFAULT 0,
  "position" INTEGER DEFAULT 0  NOT NULL,
  PRIMARY KEY (QUESTION_ID)
);

CREATE SEQUENCE survey_items_item_id_seq;
CREATE TABLE survey_items (
  item_id INTEGER NOT NULL,
  question_id INTEGER NOT NULL REFERENCES survey_questions(question_id),
  "type" INTEGER DEFAULT -1,
  description NVARCHAR2(255),
  PRIMARY KEY (ITEM_ID)
);

CREATE SEQUENCE active_survey_ctive_survey_seq;
CREATE TABLE active_survey (
  active_survey_id INTEGER NOT NULL,
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  name NVARCHAR2(80) NOT NULL,
  description NVARCHAR2(255),
  intro CLOB,
  outro CLOB,
  itemLength INTEGER DEFAULT -1,
  "type" INTEGER NOT NULL REFERENCES lookup_survey_types(code),
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ACTIVE_SURVEY_ID)
);

CREATE SEQUENCE active_survey__question_id_seq;
CREATE TABLE active_survey_questions (
  question_id INTEGER NOT NULL,
  active_survey_id INTEGER NOT NULL REFERENCES active_survey(active_survey_id),
  "type" INTEGER  REFERENCES lookup_survey_types(code),
  description NVARCHAR2(255),
  required CHAR(1) DEFAULT 0 NOT NULL,
  "position" INTEGER DEFAULT 0 NOT NULL,
  average FLOAT DEFAULT 0.00,
  total1 INTEGER DEFAULT 0,
  total2 INTEGER DEFAULT 0,
  total3 INTEGER DEFAULT 0,
  total4 INTEGER DEFAULT 0,
  total5 INTEGER DEFAULT 0,
  total6 INTEGER DEFAULT 0,
  total7 INTEGER DEFAULT 0,
  PRIMARY KEY (QUESTION_ID)
);

CREATE SEQUENCE active_survey_tems_item_id_seq;
CREATE TABLE active_survey_items (
  item_id INTEGER NOT NULL,
  question_id INTEGER NOT NULL REFERENCES active_survey_questions(question_id),
  "type" INTEGER DEFAULT -1,
  description NVARCHAR2(255),
  PRIMARY KEY (ITEM_ID)
);

CREATE SEQUENCE active_survey__response_id_seq;
CREATE TABLE active_survey_responses (
  response_id INTEGER  NOT NULL,
  active_survey_id INTEGER NOT NULL REFERENCES active_survey(active_survey_id),
  contact_id INTEGER  DEFAULT -1 NOT NULL,
  unique_code NVARCHAR2(255),
  ip_address NVARCHAR2(15) NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  address_updated INTEGER,
  PRIMARY KEY (RESPONSE_ID)
);

CREATE SEQUENCE active_survey_ns_answer_id_seq;
CREATE TABLE active_survey_answers (
  answer_id INTEGER  NOT NULL,
  response_id INTEGER NOT NULL REFERENCES active_survey_responses(response_id),
  question_id INTEGER NOT NULL REFERENCES active_survey_questions(question_id),
  comments CLOB,
  quant_ans INTEGER DEFAULT -1,
  text_ans CLOB,
  PRIMARY KEY (ANSWER_ID)
);

CREATE SEQUENCE active_survey_nswer_ite_id_seq;
CREATE TABLE active_survey_answer_items (
  id INTEGER NOT NULL,
  item_id INTEGER NOT NULL REFERENCES active_survey_items(item_id),
  answer_id INTEGER NOT NULL REFERENCES active_survey_answers(answer_id),
  comments CLOB,
  PRIMARY KEY (ID)
);

CREATE SEQUENCE active_survey_nswer_avg_id_seq;
CREATE TABLE active_survey_answer_avg (
  id INTEGER NOT NULL,
  question_id INTEGER NOT NULL REFERENCES active_survey_questions(question_id),
  item_id INTEGER NOT NULL REFERENCES active_survey_items(item_id),
  total INTEGER  DEFAULT 0 NOT NULL,
  PRIMARY KEY (ID)
);

CREATE SEQUENCE field_types_id_seq;
CREATE TABLE field_types (
  id INTEGER NOT NULL,
  data_typeid INTEGER  DEFAULT -1 NOT NULL,
  data_type NVARCHAR2(20),
  operator NVARCHAR2(50),
  display_text NVARCHAR2(50),
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  PRIMARY KEY (ID)
);

CREATE SEQUENCE search_fields_id_seq;
CREATE TABLE search_fields (
  id INTEGER NOT NULL,
  field NVARCHAR2(80),
  description NVARCHAR2(255),
  searchable CHAR(1) DEFAULT 1 NOT NULL,
  field_typeid INTEGER  DEFAULT -1 NOT NULL,
  table_name NVARCHAR2(80),
  object_class NVARCHAR2(80),
  enabled CHAR(1) DEFAULT 1,
  PRIMARY KEY (ID)
);

CREATE SEQUENCE message_id_seq;
CREATE TABLE "message" (
  id INTEGER NOT NULL,
  name NVARCHAR2(80) NOT NULL,
  description NVARCHAR2(255),
  template_id INTEGER,
  subject NVARCHAR2(255) DEFAULT NULL,
  body CLOB,
  reply_addr NVARCHAR2(100),
  url NVARCHAR2(255),
  img NVARCHAR2(80),
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  access_type INTEGER REFERENCES lookup_access_types,
  PRIMARY KEY (ID)
);

CREATE SEQUENCE message_template_id_seq;
CREATE TABLE message_template (
  id INTEGER NOT NULL,
  name NVARCHAR2(80) NOT NULL,
  description NVARCHAR2(255),
  template_file NVARCHAR2(80),
  num_imgs INTEGER,
  num_urls INTEGER,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ID)
);

CREATE TABLE saved_criteriaelement (
  id INTEGER NOT NULL REFERENCES saved_criterialist(id),
  field INTEGER NOT NULL references search_fields(id),
  operator NVARCHAR2(50) NOT NULL,
  operatorid INTEGER NOT NULL references field_types(id),
  "value" NVARCHAR2(80) NOT NULL,
  source INTEGER DEFAULT -1 NOT NULL,
  value_id INTEGER,
  site_id INT
);

-- Messages received by an user, from a specific contact
CREATE SEQUENCE contact_message_id_seq;
CREATE TABLE contact_message (
  id INTEGER NOT NULL PRIMARY KEY,
  message_id INTEGER NOT NULL REFERENCES "message"(id),
  received_date TIMESTAMP NOT NULL,
  received_from INT NOT NULL REFERENCES contact(contact_id),
  received_by INT NOT NULL REFERENCES "access"(user_id)
);

CREATE SEQUENCE campaign_group_map_map_id_seq;
CREATE TABLE campaign_group_map (
  map_id INTEGER NOT NULL PRIMARY KEY,
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  user_group_id INTEGER NOT NULL REFERENCES user_group(group_id)
);

CREATE SEQUENCE message_file__ttachment_id_seq;
CREATE TABLE message_file_attachment (
  attachment_id INTEGER NOT NULL PRIMARY KEY,
  link_module_id INT NOT NULL,
  link_item_id INT NOT NULL,
  file_item_id INT REFERENCES project_files(item_id),
  filename NVARCHAR2(255) NOT NULL,
  "size" INT DEFAULT 0,
  "version" FLOAT DEFAULT 0
);

CREATE INDEX message_f_link_module_id ON message_file_attachment (link_module_id);
CREATE INDEX message_f_link_item_id ON message_file_attachment (link_item_id);

