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

CREATE TABLE saved_criteriaelement (
  id INTEGER NOT NULL REFERENCES saved_criterialist(id),
  field INTEGER NOT NULL DEFAULT -1,
  operator VARCHAR(50) NOT NULL,
  operatorid INTEGER NOT NULL DEFAULT -1,
  value VARCHAR(80) NOT NULL
);

CREATE TABLE campaign (
  id serial PRIMARY KEY,
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
  approvedby INT DEFAULT -1,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE campaign_run (
  id serial PRIMARY KEY,
  campaign_id INTEGER NOT NULL DEFAULT -1 REFERENCES campaign(campaign_id),
  status INTEGER NOT NULL DEFAULT 0,
  run_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  total_contacts INTEGER DEFAULT 0,
  total_sent INTEGER DEFAULT 0,
  total_replied INTEGER DEFAULT 0,
  total_bounced INTEGER DEFAULT 0
);

CREATE TABLE excluded_recipient (
  id serial PRIMARY KEY,
  campaign_id INT NOT NULL DEFAULT -1 REFERENCES campaign(campaign_id),
  contact_id INT NOT NULL REFERENCES contact(contact_id)
);

CREATE TABLE campaign_list_groups (
  campaign_id int not null REFERENCES campaign(campaign_id),
  group_id int not null REFERENCES saved_criterialist(id)
);

CREATE TABLE scheduled_recipient (
  id serial PRIMARY KEY,
  campaign_id INT NOT NULL DEFAULT -1 REFERENCES campaign(campaign_id),
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
  itemLength int default -1,
  type INT NOT NULL REFERENCES lookup_survey_types(code),
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE campaign_survey_link (
  campaign_id INT REFERENCES campaign(campaign_id),
  survey_id INT REFERENCES survey(survey_id)
);

/* survey_question_question_id_seq */
CREATE TABLE survey_questions (
  question_id serial PRIMARY KEY,
  survey_id INT NOT NULL REFERENCES survey(survey_id),
  type INT NOT NULL REFERENCES lookup_survey_types(code),
  description VARCHAR(255)
);



CREATE TABLE active_survey (
  active_survey_id serial PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  intro TEXT,
  itemLength int default -1,
  type INT NOT NULL REFERENCES lookup_survey_types(code),
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

/* active_survey_q_question_id_seq */
CREATE TABLE active_survey_questions (
  question_id SERIAL PRIMARY KEY,
  active_survey_id INT default -1 REFERENCES active_survey(active_survey_id),
  type INT NOT NULL REFERENCES lookup_survey_types(code),
  description VARCHAR(255),
  average float default 0.00,
  total1 int default 0,
  total2 int default 0,
  total3 int default 0,
  total4 int default 0,
  total5 int default 0,
  total6 int default 0,
  total7 int default 0
);

/* active_survey_r_response_id_seq */
CREATE TABLE active_survey_responses (
  response_id SERIAL PRIMARY KEY,
  active_survey_id INT NOT NULL REFERENCES active_survey(active_survey_id),
  contact_id INT NOT NULL DEFAULT -1,
  unique_code VARCHAR(255),
  ip_address VARCHAR(15) NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

/* active_survey_ans_answer_id_seq */
CREATE TABLE active_survey_answers (
  answer_id SERIAL primary key,
  response_id INT NOT NULL REFERENCES active_survey_responses(response_id),
  question_id INT NOT NULL REFERENCES active_survey_questions(question_id),
  comments TEXT,
  quant_ans int DEFAULT -1,
  text_ans TEXT
);

CREATE TABLE field_types (
  id serial PRIMARY KEY,
  data_typeid int NOT NULL DEFAULT -1,
	data_type VARCHAR(20),
  operator VARCHAR(50),
  display_text varchar(50)
);

CREATE TABLE search_fields (
  id serial PRIMARY KEY,
  field varchar(80),
  description VARCHAR(255),
  searchable BOOLEAN NOT NULL DEFAULT true,
  field_typeid int NOT NULL DEFAULT -1,
  table_name varchar(80),
  object_class varchar(80),
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE recipient_list (
  id serial PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  search_id int,
  dynamic BOOLEAN NOT NULL DEFAULT true,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE message (
  id serial PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  template_id INT,
  subject VARCHAR(255) DEFAULT NULL,
  body TEXT,
  reply_addr VARCHAR(100),
  url VARCHAR(100),
  img VARCHAR(80),  
  enabled BOOLEAN NOT NULL DEFAULT 't',
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
  enabled BOOLEAN NOT NULL DEFAULT 't',
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);



 





