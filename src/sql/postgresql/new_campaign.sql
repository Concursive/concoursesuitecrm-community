/**
 *  PostgreSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 27, 2002
 *@version    $Id$
 */
 
CREATE TABLE saved_criterialist (
  id SERIAL PRIMARY KEY,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  owner INTEGER NOT NULL REFERENCES access(user_id),
  name VARCHAR(80) NOT NULL,
  contact_source INTEGER DEFAULT -1,
  enabled BOOLEAN NOT NULL DEFAULT true
);


CREATE TABLE campaign (
  campaign_id serial PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  list_id int,
  message_id int DEFAULT -1,
  reply_addr VARCHAR(255) DEFAULT NULL,
  subject VARCHAR(255) DEFAULT NULL,
  message TEXT DEFAULT NULL,
  status_id INT DEFAULT 0,
  status VARCHAR(255),
  active BOOLEAN DEFAULT false,
  active_date DATE DEFAULT NULL,
  send_method_id INT DEFAULT -1 NOT NULL,
  inactive_date DATE DEFAULT NULL,
  approval_date TIMESTAMP(3) DEFAULT NULL,
  approvedby INT REFERENCES access(user_id),
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  type INT DEFAULT 1
);

CREATE TABLE campaign_run (
  id serial PRIMARY KEY,
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  status INTEGER NOT NULL DEFAULT 0,
  run_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  total_contacts INTEGER DEFAULT 0,
  total_sent INTEGER DEFAULT 0,
  total_replied INTEGER DEFAULT 0,
  total_bounced INTEGER DEFAULT 0
);

CREATE TABLE excluded_recipient (
  id SERIAL PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  contact_id INT NOT NULL REFERENCES contact(contact_id)
);

CREATE TABLE campaign_list_groups (
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  group_id INT NOT NULL REFERENCES saved_criterialist(id)
);

CREATE TABLE active_campaign_groups (
  id SERIAL PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  groupname VARCHAR(80) NOT NULL,
  groupcriteria TEXT DEFAULT NULL
);


CREATE TABLE scheduled_recipient (
  id SERIAL PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  contact_id INT NOT NULL REFERENCES contact(contact_id),
  run_id INT DEFAULT -1,
  status_id INT DEFAULT 0,
  status VARCHAR(255),
  status_date TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  scheduled_date TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  sent_date TIMESTAMP(3) DEFAULT NULL,
  reply_date TIMESTAMP(3) DEFAULT NULL,
  bounce_date TIMESTAMP(3) DEFAULT NULL
);

CREATE TABLE lookup_survey_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE survey (
  survey_id serial PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  intro TEXT,
  outro TEXT,
  itemLength INT DEFAULT -1,
  type INT DEFAULT -1,
  enabled BOOLEAN NOT NULL DEFAULT true,
  status INT NOT NULL DEFAULT -1,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE campaign_survey_link (
  campaign_id INT REFERENCES campaign(campaign_id),
  survey_id INT REFERENCES survey(survey_id)
);

CREATE SEQUENCE survey_question_question_id_seq;
CREATE TABLE survey_questions (
  question_id INTEGER DEFAULT nextval('survey_question_question_id_seq') NOT NULL PRIMARY KEY,
  survey_id INT NOT NULL REFERENCES survey(survey_id),
  type INT NOT NULL REFERENCES lookup_survey_types(code),
  description VARCHAR(255),
  required BOOLEAN NOT NULL DEFAULT false,
  position INT NOT NULL DEFAULT 0
);

CREATE TABLE survey_items (
  item_id SERIAL PRIMARY KEY,
  question_id INT NOT NULL REFERENCES survey_questions(question_id),
  type INT DEFAULT -1,
  description VARCHAR(255)
);

CREATE SEQUENCE active_survey_active_survey_seq;
CREATE TABLE active_survey (
  active_survey_id INTEGER DEFAULT nextval('active_survey_active_survey_seq') NOT NULL PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  intro TEXT,
  outro TEXT,
  itemLength INT DEFAULT -1,
  type INT NOT NULL REFERENCES lookup_survey_types(code),
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE SEQUENCE active_survey_q_question_id_seq;
CREATE TABLE active_survey_questions (
  question_id INTEGER DEFAULT nextval('active_survey_q_question_id_seq') NOT NULL PRIMARY KEY,
  active_survey_id INT REFERENCES active_survey(active_survey_id),
  type INT NOT NULL REFERENCES lookup_survey_types(code),
  description VARCHAR(255),
  required BOOLEAN NOT NULL DEFAULT false,
  position INT NOT NULL DEFAULT 0,
  average FLOAT DEFAULT 0.00,
  total1 INT DEFAULT 0,
  total2 INT DEFAULT 0,
  total3 INT DEFAULT 0,
  total4 INT DEFAULT 0,
  total5 INT DEFAULT 0,
  total6 INT DEFAULT 0,
  total7 INT DEFAULT 0
);

CREATE SEQUENCE active_survey_items_item_id_seq;
CREATE TABLE active_survey_items (
  item_id INTEGER DEFAULT nextval('active_survey_items_item_id_seq') NOT NULL PRIMARY KEY,
  question_id INT NOT NULL REFERENCES active_survey_questions(question_id),
  type INT DEFAULT -1,
  description VARCHAR(255)
);

CREATE SEQUENCE active_survey_r_response_id_seq;
CREATE TABLE active_survey_responses (
  response_id INTEGER DEFAULT nextval('active_survey_r_response_id_seq') NOT NULL PRIMARY KEY,
  active_survey_id INT NOT NULL REFERENCES active_survey(active_survey_id),
  contact_id INT NOT NULL DEFAULT -1,
  unique_code VARCHAR(255),
  ip_address VARCHAR(15) NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE active_survey_ans_answer_id_seq;
CREATE TABLE active_survey_answers (
  answer_id INTEGER DEFAULT nextval('active_survey_ans_answer_id_seq') NOT NULL PRIMARY KEY,
  response_id INT NOT NULL REFERENCES active_survey_responses(response_id),
  question_id INT NOT NULL REFERENCES active_survey_questions(question_id),
  comments TEXT,
  quant_ans INT DEFAULT -1,
  text_ans TEXT
);

CREATE SEQUENCE active_survey_answer_ite_id_seq;
CREATE TABLE active_survey_answer_items (
  id INTEGER DEFAULT nextval('active_survey_answer_ite_id_seq') NOT NULL PRIMARY KEY,
  item_id INT NOT NULL REFERENCES active_survey_items(item_id),
  answer_id INT NOT NULL REFERENCES active_survey_answers(answer_id),
  comments TEXT
);

CREATE SEQUENCE active_survey_answer_avg_id_seq;
CREATE TABLE active_survey_answer_avg (
  id INTEGER DEFAULT nextval('active_survey_answer_avg_id_seq') NOT NULL PRIMARY KEY,
  question_id INT NOT NULL REFERENCES active_survey_questions(question_id),
  item_id INT NOT NULL REFERENCES active_survey_items(item_id),
  total INT NOT NULL DEFAULT 0
);

CREATE TABLE field_types (
  id serial PRIMARY KEY,
  data_typeid int NOT NULL DEFAULT -1,
	data_type VARCHAR(20),
  operator VARCHAR(50),
  display_text varchar(50),
  enabled boolean NOT NULL DEFAULT true
);

CREATE TABLE search_fields (
  id SERIAL PRIMARY KEY,
  field varchar(80),
  description VARCHAR(255),
  searchable BOOLEAN NOT NULL DEFAULT true,
  field_typeid int NOT NULL DEFAULT -1,
  table_name varchar(80),
  object_class varchar(80),
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE message (
  id SERIAL PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  template_id INT,
  subject VARCHAR(255) DEFAULT NULL,
  body TEXT,
  reply_addr VARCHAR(100),
  url VARCHAR(255),
  img VARCHAR(80),
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE message_template (
  id serial PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  template_file varchar(80),
  num_imgs INT,
  num_urls INT,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE saved_criteriaelement (
  id INTEGER NOT NULL REFERENCES saved_criterialist(id),
  field INTEGER NOT NULL references search_fields(id),
  operator VARCHAR(50) NOT NULL,
  operatorid INTEGER NOT NULL references field_types(id),
  value VARCHAR(80) NOT NULL,
  source INT NOT NULL DEFAULT -1
);

