
CREATE SEQUENCE saved_criterialist_id_seq;
CREATE TABLE saved_criterialist (
  id INT  PRIMARY KEY,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  enteredby INTEGER  REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INTEGER  REFERENCES access(user_id) NOT NULL,
  owner INTEGER  REFERENCES access(user_id) NOT NULL,
  name VARCHAR(80) NOT NULL,
  contact_source INTEGER DEFAULT -1,
  enabled boolean  DEFAULT true NOT NULL
);


CREATE SEQUENCE campaign_campaign_id_seq;
CREATE TABLE campaign (
  campaign_id INT   PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  list_id int,
  message_id int DEFAULT -1,
  reply_addr VARCHAR(255) ,
  subject VARCHAR(255) ,
  message clob,
  status_id INT DEFAULT 0,
  status VARCHAR(255),
  active boolean DEFAULT false,
  active_date TIMESTAMP ,
  send_method_id INT DEFAULT -1 NOT NULL,
  inactive_date TIMESTAMP ,
  approval_date TIMESTAMP ,
  approvedby INT REFERENCES access(user_id),
  enabled boolean DEFAULT true NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT  REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL,
  type INT DEFAULT 1,
  active_date_timezone VARCHAR(255),
  cc VARCHAR(1024),
  bcc VARCHAR(1024),
  trashed_date TIMESTAMP
);

CREATE SEQUENCE campaign_run_id_seq;
CREATE TABLE campaign_run (
  id INT   PRIMARY KEY,
  campaign_id INTEGER  REFERENCES campaign(campaign_id) NOT NULL,
  status INTEGER DEFAULT 0 NOT NULL,
  run_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  total_contacts INTEGER DEFAULT 0,
  total_sent INTEGER DEFAULT 0,
  total_replied INTEGER DEFAULT 0,
  total_bounced INTEGER DEFAULT 0
);

CREATE SEQUENCE excluded_recipient_id_seq;
CREATE TABLE excluded_recipient (
  id INT  PRIMARY KEY,
  campaign_id INT  REFERENCES campaign(campaign_id) NOT NULL,
  contact_id INT REFERENCES contact(contact_id) NOT NULL
);

CREATE TABLE campaign_list_groups (
  campaign_id INT REFERENCES campaign(campaign_id) NOT NULL,
  group_id INT REFERENCES saved_criterialist(id) NOT NULL
);

CREATE SEQUENCE active_campaign_groups_id_seq;
CREATE TABLE active_campaign_groups (
  id INT PRIMARY KEY,
  campaign_id INT  REFERENCES campaign(campaign_id) NOT NULL,
  groupname VARCHAR(80) NOT NULL,
  groupcriteria CLOB
);


CREATE SEQUENCE scheduled_recipient_id_seq;
CREATE TABLE scheduled_recipient (
  id INT  PRIMARY KEY,
  campaign_id INT REFERENCES campaign(campaign_id) NOT NULL,
  contact_id INT REFERENCES contact(contact_id) NOT NULL,
  run_id INT DEFAULT -1,
  status_id INT DEFAULT 0,
  status VARCHAR(255),
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  scheduled_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  sent_date TIMESTAMP DEFAULT NULL,
  reply_date TIMESTAMP DEFAULT NULL,
  bounce_date TIMESTAMP DEFAULT NULL
);

CREATE SEQUENCE lookup_survey_types_code_seq;
CREATE TABLE lookup_survey_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE survey_survey_id_seq;
CREATE TABLE survey (
  survey_id INT  PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  intro CLOB,
  outro CLOB,
  itemLength INT DEFAULT -1,
  type INT DEFAULT -1,
  enabled boolean DEFAULT true NOT NULL ,
  status INT  DEFAULT -1 NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT  REFERENCES access(user_id) NOT NULL
);

CREATE TABLE campaign_survey_link (
  campaign_id INT REFERENCES campaign(campaign_id),
  survey_id INT REFERENCES survey(survey_id)
);

CREATE SEQUENCE survey_question_question_id_seq;
CREATE TABLE survey_questions (
  question_id INT  PRIMARY KEY,
  survey_id INT  REFERENCES survey(survey_id) NOT NULL,
  type INT  REFERENCES lookup_survey_types(code) NOT NULL,
  description VARCHAR(255),
  required boolean  DEFAULT false,
  position INT DEFAULT 0  NOT NULL 
);

CREATE SEQUENCE survey_items_item_id_seq;
CREATE TABLE survey_items (
  item_id INT  PRIMARY KEY,
  question_id INT  REFERENCES survey_questions(question_id) NOT NULL,
  type INT DEFAULT -1,
  description VARCHAR(255)
);

CREATE SEQUENCE active_survey_active_survey_seq;
CREATE TABLE active_survey (
  active_survey_id INT  PRIMARY KEY,
  campaign_id INT  REFERENCES campaign(campaign_id) NOT NULL,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  intro CLOB,
  outro CLOB,
  itemLength INT DEFAULT -1,
  type INT REFERENCES lookup_survey_types(code) NOT NULL,
  enabled boolean DEFAULT true NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT  REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT REFERENCES access(user_id) NOT NULL
);

CREATE SEQUENCE active_survey_q_question_id_seq;
CREATE TABLE active_survey_questions (
  question_id INT  PRIMARY KEY,
  active_survey_id INT REFERENCES active_survey(active_survey_id) NOT NULL,
  type INT  REFERENCES lookup_survey_types(code),
  description VARCHAR(255),
  required boolean DEFAULT false NOT NULL,
  position INT DEFAULT 0 NOT NULL,
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
  item_id INTEGER PRIMARY KEY,
  question_id INT REFERENCES active_survey_questions(question_id) NOT NULL,
  type INT DEFAULT -1,
  description VARCHAR(255)
);

CREATE SEQUENCE active_survey_r_response_id_seq;
CREATE TABLE active_survey_responses (
  response_id INT  PRIMARY KEY,
  active_survey_id INT  REFERENCES active_survey(active_survey_id) NOT NULL,
  contact_id INT  DEFAULT -1 NOT NULL,
  unique_code VARCHAR(255),
  ip_address VARCHAR(15) NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  address_updated INT
);

CREATE SEQUENCE active_survey_ans_answer_id_seq;
CREATE TABLE active_survey_answers (
  answer_id INT  primary key,
  response_id INT  REFERENCES active_survey_responses(response_id) NOT NULL,
  question_id INT  REFERENCES active_survey_questions(question_id) NOT NULL, 
  comments CLOB,
  quant_ans INT DEFAULT -1,
  text_ans CLOB
);

CREATE SEQUENCE active_survey_answer_ite_id_seq;
CREATE TABLE active_survey_answer_items (
  id INTEGER PRIMARY KEY,
  item_id INT  REFERENCES active_survey_items(item_id) NOT NULL,
  answer_id INT  REFERENCES active_survey_answers(answer_id) NOT NULL,
  comments CLOB
);

CREATE SEQUENCE active_survey_answer_avg_id_seq;
CREATE TABLE active_survey_answer_avg (
  id INTEGER PRIMARY KEY,
  question_id INT  REFERENCES active_survey_questions(question_id) NOT NULL,
  item_id INT  REFERENCES active_survey_items(item_id) NOT NULL,
  total INT  DEFAULT 0 NOT NULL
);

CREATE SEQUENCE field_types_id_seq;
CREATE TABLE field_types (
  id INT PRIMARY KEY,
  data_typeid int  DEFAULT -1 NOT NULL,
  data_type VARCHAR(20),
  operator VARCHAR(50),
  display_text varchar(50),
  enabled boolean DEFAULT true NOT NULL
);

CREATE SEQUENCE search_fields_id_seq;
CREATE TABLE search_fields (
  id INT PRIMARY KEY,
  field varchar(80),
  description VARCHAR(255),
  searchable boolean  DEFAULT true NOT NULL,
  field_typeid int  DEFAULT -1 NOT NULL,
  table_name varchar(80),
  object_class varchar(80),
  enabled boolean DEFAULT true
);

CREATE SEQUENCE message_id_seq;
CREATE TABLE message (
  id INT  PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  template_id INT,
  subject VARCHAR(255) DEFAULT NULL,
  body CLOB,
  reply_addr VARCHAR(100),
  url VARCHAR(255),
  img VARCHAR(80),
  enabled boolean  DEFAULT true NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT  REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT  REFERENCES access(user_id) NOT NULL,
  access_type INT REFERENCES lookup_access_types(code)
);

CREATE SEQUENCE message_template_id_seq;
CREATE TABLE message_template (
  id INT PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  template_file varchar(80),
  num_imgs INT,
  num_urls INT,
  enabled boolean  DEFAULT true NOT NULL,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT  REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT  REFERENCES access(user_id) NOT NULL
);

CREATE TABLE saved_criteriaelement (
  id INTEGER  REFERENCES saved_criterialist(id) NOT NULL,
  field INTEGER  references search_fields(id) NOT NULL,
  operator VARCHAR(50) NOT NULL,
  operatorid INTEGER  references field_types(id) NOT NULL,
  value VARCHAR(80) NOT NULL,
  source INT DEFAULT -1 NOT NULL,
  value_id INT,
  site_id INT
);

-- Messages received by an user, from a specific contact
CREATE SEQUENCE contact_message_id_seq;
CREATE TABLE contact_message (
  id INT PRIMARY KEY,
  message_id INTEGER NOT NULL REFERENCES message(id),
  received_date TIMESTAMP NOT NULL,
  received_from INT NOT NULL REFERENCES contact(contact_id),
  received_by INT NOT NULL REFERENCES access(user_id)
);

-- Each campaign can be associated with several user groups.
-- The users belonging to the user groups will have access to the campaign results.
CREATE SEQUENCE campaign_group_map_map_id_seq;
CREATE TABLE campaign_group_map (
  map_id INT PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  user_group_id INT NOT NULL REFERENCES user_group(group_id)
);

