/*
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (8, 'tickets-tickets-reports', true, true, false, true, 'Reports', 30);
*/

update permission_category set folders='t' where category_id in (1,2);
update permission_category set lookups='t' where category_id=4;
update permission_category set lookups='t' where category_id=1;
update permission_category set lookups='t' where category_id=2;
update permission_category set lookups='t' where category_id=8;

alter table opportunity add column notes text;

/*
ignore the revenue, employees fields in accounts
*/

insert into system_prefs (category, data, enabled, enteredby, modifiedby) values ('system.fields.ignore', '<config><ignore>accounts-employees</ignore><ignore>accounts-revenue</ignore></config>', 't', '0', '0');

CREATE TABLE lookup_opportunity_types (
  code SERIAL PRIMARY KEY,
  order_id INT,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE opportunity_type_levels (
  opp_id INT NOT NULL REFERENCES opportunity(opp_id),
  type_id INT NOT NULL REFERENCES lookup_opportunity_types(code),
  level INTEGER not null,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

alter table field_types add column enabled boolean;
alter table field_types alter column enabled set default 't';
update field_types set enabled = 't';
update field_types set enabled = 'f' where id in (3,4,5,6);
update field_types set enabled = 'f' where id in (9);


/*
campaign dashboard and survey updates 
Dated : 11/24/2002 Mathur
*/

CREATE TABLE survey_items (
  item_id serial PRIMARY KEY,
  question_id INT NOT NULL REFERENCES survey_questions(question_id),
  type INT DEFAULT -1,
  description VARCHAR(255)
);


CREATE TABLE active_survey_items (
  item_id serial PRIMARY KEY,
  question_id INT NOT NULL REFERENCES active_survey_questions(question_id),
  type INT DEFAULT -1,
  description VARCHAR(255)
);

CREATE TABLE active_survey_answer_items (
  id SERIAL primary key,
  item_id INT NOT NULL REFERENCES active_survey_items(item_id),
  answer_id INT NOT NULL REFERENCES active_survey_answers(answer_id),
  comments TEXT
);

CREATE TABLE active_survey_answer_avg (
  id SERIAL primary key,
  question_id INT NOT NULL REFERENCES active_survey_questions(question_id),
  item_id INT NOT NULL REFERENCES active_survey_items(item_id),
  total INT NOT NULL DEFAULT 0
);

CREATE TABLE active_campaign_groups (
  id serial PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  groupname VARCHAR(80) NOT NULL,
  groupcriteria TEXT DEFAULT NULL
);

drop sequence survey_question_question_id_seq;
drop table survey_questions;

CREATE TABLE survey_questions (
  question_id serial PRIMARY KEY,
  survey_id INT NOT NULL REFERENCES survey(survey_id),
  type INT NOT NULL REFERENCES lookup_survey_types(code),
  description VARCHAR(255),
  required BOOLEAN NOT NULL DEFAULT false,
  position INT NOT NULL DEFAULT 0
);


drop sequence survey_survey_id_seq;
drop table survey;

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


drop sequence active_survey_q_question_id_seq;
drop table active_survey_questions;

CREATE TABLE active_survey_questions (
  question_id SERIAL PRIMARY KEY,
  active_survey_id INT REFERENCES active_survey(active_survey_id),
  type INT NOT NULL REFERENCES lookup_survey_types(code),
  description VARCHAR(255),
  required BOOLEAN NOT NULL DEFAULT false,
  position INT NOT NULL DEFAULT 0,
  average float default 0.00,
  total1 int default 0,
  total2 int default 0,
  total3 int default 0,
  total4 int default 0,
  total5 int default 0,
  total6 int default 0,
  total7 int default 0
);

drop sequence active_survey_active_survey_seq;
drop table active_survey;

CREATE TABLE active_survey (
  active_survey_id serial PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  intro TEXT,
  outro TEXT,
  itemLength int default -1,
  type INT NOT NULL REFERENCES lookup_survey_types(code),
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);
