/*August 7 2002*/
alter table cfsinbox_message add column delete_flag BIT default 0;

/* August 8, 2002 */
ALTER TABLE campaign ADD COLUMN survey_id INT DEFAULT -1;

CREATE TABLE survey (
  id INT IDENTITY PRIMARY KEY,
  message_id int default -1,
  items_id int default -1,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  intro TEXT,
  itemLength int default -1,
  type int default -1,
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE survey_item (
  id INT IDENTITY PRIMARY KEY,
  survey_id int default -1,
  type int default -1,
  description VARCHAR(255),
  enabled BIT NOT NULL DEFAULT 1,
  average float default 0.00,
  total1 int default 0,
  total2 int default 0,
  total3 int default 0,
  total4 int default 0,
  total5 int default 0,
  total6 int default 0,
  total7 int default 0
);

CREATE TABLE lookup_survey_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

insert into lookup_survey_types (description) values ('Open-Ended');
insert into lookup_survey_types (description) values ('Quantitative (no comments)');
insert into lookup_survey_types (description) values ('Quantitative (with comments)');

CREATE TABLE survey_answer (
  id INT IDENTITY primary key,
  question_id int not null,
  comments VARCHAR(100) default null,
  quant_ans int DEFAULT -1,
  text_ans VARCHAR(100) DEFAULT null,
  enteredby int not null
);

