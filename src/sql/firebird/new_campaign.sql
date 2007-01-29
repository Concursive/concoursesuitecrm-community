
CREATE GENERATOR saved_criterialist_id_seq;
CREATE TABLE saved_criterialist (
  id INTEGER  NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  owner INTEGER NOT NULL REFERENCES "access"(user_id),
  name VARCHAR(80) NOT NULL,
  contact_source INTEGER DEFAULT -1,
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  PRIMARY KEY (ID)
);


CREATE GENERATOR campaign_campaign_id_seq;
CREATE TABLE campaign (
  campaign_id INTEGER NOT NULL,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  list_id INTEGER,
  message_id INTEGER DEFAULT -1,
  reply_addr VARCHAR(255) ,
  subject VARCHAR(255) ,
  "message" BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  status_id INTEGER DEFAULT 0,
  status VARCHAR(255),
  "active" CHAR(1) DEFAULT 'N',
  active_date TIMESTAMP ,
  send_method_id INTEGER DEFAULT -1 NOT NULL,
  inactive_date TIMESTAMP ,
  approval_date TIMESTAMP ,
  approvedby INTEGER REFERENCES "access"(user_id),
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  "type" INTEGER DEFAULT 1,
  active_date_timezone VARCHAR(255),
  cc VARCHAR(1024),
  bcc VARCHAR(1024),
  trashed_date TIMESTAMP,
  PRIMARY KEY (CAMPAIGN_ID)
);

CREATE GENERATOR campaign_run_id_seq;
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

CREATE GENERATOR excluded_recipient_id_seq;
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

CREATE GENERATOR active_campaign_groups_id_seq;
CREATE TABLE active_campaign_groups (
  id INTEGER NOT NULL,
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  groupname VARCHAR(80) NOT NULL,
  groupcriteria BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (ID)
);


CREATE GENERATOR scheduled_recipient_id_seq;
CREATE TABLE scheduled_recipient (
  id INTEGER NOT NULL,
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  contact_id INTEGER NOT NULL REFERENCES contact(contact_id),
  run_id INTEGER DEFAULT -1,
  status_id INTEGER DEFAULT 0,
  status VARCHAR(255),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  scheduled_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  sent_date TIMESTAMP DEFAULT NULL,
  reply_date TIMESTAMP DEFAULT NULL,
  bounce_date TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (ID)
);

CREATE GENERATOR lookup_survey_types_code_seq;
CREATE TABLE lookup_survey_types (
  code INTEGER NOT NULL,
  description VARCHAR(50) NOT NULL,
  default_item CHAR(1) DEFAULT 'N',
  "level" INTEGER DEFAULT 0,
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (CODE)
);

CREATE GENERATOR survey_survey_id_seq;
CREATE TABLE survey (
  survey_id INTEGER NOT NULL,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  intro BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  outro BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  itemLength INTEGER DEFAULT -1,
  "type" INTEGER DEFAULT -1,
  enabled CHAR(1) DEFAULT 'Y' NOT NULL ,
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

CREATE GENERATOR survey_question_question_id_seq;
CREATE TABLE survey_questions (
  question_id INTEGER NOT NULL,
  survey_id INTEGER NOT NULL REFERENCES survey(survey_id),
  "type" INTEGER NOT NULL REFERENCES lookup_survey_types(code),
  description VARCHAR(255),
  required CHAR(1) DEFAULT 'N',
  "position" INTEGER DEFAULT 0  NOT NULL,
  PRIMARY KEY (QUESTION_ID)
);

CREATE GENERATOR survey_items_item_id_seq;
CREATE TABLE survey_items (
  item_id INTEGER NOT NULL,
  question_id INTEGER NOT NULL REFERENCES survey_questions(question_id),
  "type" INTEGER DEFAULT -1,
  description VARCHAR(255),
  PRIMARY KEY (ITEM_ID)
);

CREATE GENERATOR active_survey_active_survey_seq;
CREATE TABLE active_survey (
  active_survey_id INTEGER NOT NULL,
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  intro BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  outro BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  itemLength INTEGER DEFAULT -1,
  "type" INTEGER NOT NULL REFERENCES lookup_survey_types(code),
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ACTIVE_SURVEY_ID)
);

CREATE GENERATOR active_survey_q_question_id_seq;
CREATE TABLE active_survey_questions (
  question_id INTEGER NOT NULL,
  active_survey_id INTEGER NOT NULL REFERENCES active_survey(active_survey_id),
  "type" INTEGER  REFERENCES lookup_survey_types(code),
  description VARCHAR(255),
  required CHAR(1) DEFAULT 'N' NOT NULL,
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

CREATE GENERATOR active_survey_items_item_id_seq;
CREATE TABLE active_survey_items (
  item_id INTEGER NOT NULL,
  question_id INTEGER NOT NULL REFERENCES active_survey_questions(question_id),
  "type" INTEGER DEFAULT -1,
  description VARCHAR(255),
  PRIMARY KEY (ITEM_ID)
);

CREATE GENERATOR active_survey_r_response_id_seq;
CREATE TABLE active_survey_responses (
  response_id INTEGER  NOT NULL,
  active_survey_id INTEGER NOT NULL REFERENCES active_survey(active_survey_id),
  contact_id INTEGER  DEFAULT -1 NOT NULL,
  unique_code VARCHAR(255),
  ip_address VARCHAR(15) NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  address_updated INTEGER,
  PRIMARY KEY (RESPONSE_ID)
);

CREATE GENERATOR active_survey_ans_answer_id_seq;
CREATE TABLE active_survey_answers (
  answer_id INTEGER  NOT NULL,
  response_id INTEGER NOT NULL REFERENCES active_survey_responses(response_id),
  question_id INTEGER NOT NULL REFERENCES active_survey_questions(question_id),
  comments BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  quant_ans INTEGER DEFAULT -1,
  text_ans BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (ANSWER_ID)
);

CREATE GENERATOR active_survey_answer_ite_id_seq;
CREATE TABLE active_survey_answer_items (
  id INTEGER NOT NULL,
  item_id INTEGER NOT NULL REFERENCES active_survey_items(item_id),
  answer_id INTEGER NOT NULL REFERENCES active_survey_answers(answer_id),
  comments BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  PRIMARY KEY (ID)
);

CREATE GENERATOR active_survey_answer_avg_id_seq;
CREATE TABLE active_survey_answer_avg (
  id INTEGER NOT NULL,
  question_id INTEGER NOT NULL REFERENCES active_survey_questions(question_id),
  item_id INTEGER NOT NULL REFERENCES active_survey_items(item_id),
  total INTEGER  DEFAULT 0 NOT NULL,
  PRIMARY KEY (ID)
);

CREATE GENERATOR field_types_id_seq;
CREATE TABLE field_types (
  id INTEGER NOT NULL,
  data_typeid INTEGER  DEFAULT -1 NOT NULL,
  data_type VARCHAR(20),
  operator VARCHAR(50),
  display_text varchar(50),
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  PRIMARY KEY (ID)
);

CREATE GENERATOR search_fields_id_seq;
CREATE TABLE search_fields (
  id INTEGER NOT NULL,
  field VARCHAR(80),
  description VARCHAR(255),
  searchable CHAR(1) DEFAULT 'Y' NOT NULL,
  field_typeid INTEGER  DEFAULT -1 NOT NULL,
  table_name VARCHAR(80),
  object_class VARCHAR(80),
  enabled CHAR(1) DEFAULT 'Y',
  PRIMARY KEY (ID)
);

CREATE GENERATOR message_id_seq;
CREATE TABLE "message" (
  id INTEGER NOT NULL,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  template_id INTEGER,
  subject VARCHAR(255) DEFAULT NULL,
  body BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  reply_addr VARCHAR(100),
  url VARCHAR(255),
  img VARCHAR(80),
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  access_type INTEGER REFERENCES lookup_access_types,
  PRIMARY KEY (ID)
);

CREATE GENERATOR message_template_id_seq;
CREATE TABLE message_template (
  id INTEGER NOT NULL,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  template_file varchar(80),
  num_imgs INTEGER,
  num_urls INTEGER,
  enabled CHAR(1) DEFAULT 'Y' NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ID)
);

CREATE TABLE saved_criteriaelement (
  id INTEGER NOT NULL REFERENCES saved_criterialist(id),
  field INTEGER NOT NULL references search_fields(id),
  operator VARCHAR(50) NOT NULL,
  operatorid INTEGER NOT NULL references field_types(id),
  "value" VARCHAR(80) NOT NULL,
  source INTEGER DEFAULT -1 NOT NULL,
  value_id INTEGER,
  site_id INT
);

-- Messages received by an user, from a specific contact
CREATE GENERATOR contact_message_id_seq;
CREATE TABLE contact_message (
  id INTEGER NOT NULL PRIMARY KEY,
  message_id INTEGER NOT NULL REFERENCES "message"(id),
  received_date TIMESTAMP NOT NULL,
  received_from INT NOT NULL REFERENCES contact(contact_id),
  received_by INT NOT NULL REFERENCES "access"(user_id)
);

CREATE GENERATOR campaign_group_map_map_id_seq;
CREATE TABLE campaign_group_map (
  map_id INTEGER NOT NULL PRIMARY KEY,
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  user_group_id INTEGER NOT NULL REFERENCES user_group(group_id)
);

CREATE GENERATOR message_file__attachment_id_seq;
CREATE TABLE message_file_attachment (
  attachment_id INTEGER NOT NULL PRIMARY KEY,
  link_module_id INT NOT NULL,
  link_item_id INT NOT NULL,
  file_item_id INT REFERENCES project_files(item_id),
  filename VARCHAR(255) NOT NULL,
  "size" INT DEFAULT 0,
  "version" FLOAT DEFAULT 0
);

CREATE INDEX "message_f_link_module_id" ON "message_file_attachment" (link_module_id);
CREATE INDEX "message_f_link_item_id" ON "message_file_attachment" (link_item_id);
