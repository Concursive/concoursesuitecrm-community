/* October 3, 2002: Need to manually modify relationships also */

DROP TABLE system_prefs

GO

CREATE TABLE system_prefs (
  pref_id INT IDENTITY PRIMARY KEY,
  category VARCHAR(255) NOT NULL,
  data TEXT DEFAULT '' NOT NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id),
  enabled BIT NOT NULL DEFAULT 1
)

GO

UPDATE access_log
SET user_id = a.user_id 
FROM access a 
WHERE access_log.user_id = -1 
AND access_log.username = a.username

GO

DELETE FROM access_log
WHERE user_id = -1

GO

UPDATE contact
SET type_id = NULL
WHERE type_id = 0

GO

UPDATE contact
SET department = NULL
WHERE department = 0
OR department = -1

GO

DROP TABLE news

GO

CREATE TABLE news (
  rec_id INT IDENTITY PRIMARY KEY,
  org_id INT references organization(org_id),
  url TEXT,
  base TEXT,
  headline TEXT,
  body TEXT,
  dateEntered DATETIME,
  type CHAR(1),
  created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
)

GO

DROP TABLE custom_field_category

GO

CREATE TABLE custom_field_category (
  module_id INTEGER NOT NULL REFERENCES system_modules(code),
  category_id INT IDENTITY PRIMARY KEY,
  category_name VARCHAR(255) NOT NULL,
  level INTEGER DEFAULT 0,
  description TEXT,
  start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME,
  default_item BIT DEFAULT 0,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT DEFAULT 1,
  multiple_records BIT DEFAULT 0,
  read_only BIT DEFAULT 0
)

GO

CREATE INDEX "custom_field_cat_idx" ON "custom_field_category" ("module_id");

GO

DROP TABLE custom_field_group

GO

CREATE TABLE custom_field_group (
  category_id INTEGER NOT NULL REFERENCES custom_field_category(category_id),
  group_id INT IDENTITY PRIMARY KEY,
  group_name VARCHAR(255) NOT NULL,
  level INTEGER DEFAULT 0,
  description TEXT,
  start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT DEFAULT 1
)

GO

CREATE INDEX "custom_field_grp_idx" ON "custom_field_group" ("category_id");

GO

DROP TABLE custom_field_info

GO

CREATE TABLE custom_field_info (
  group_id INTEGER NOT NULL REFERENCES custom_field_group(group_id),
  field_id INT IDENTITY PRIMARY KEY,
  field_name VARCHAR(255) NOT NULL,
  level INTEGER DEFAULT 0,
  field_type INTEGER NOT NULL,
  validation_type INTEGER DEFAULT 0,
  required BIT DEFAULT 0,
  parameters TEXT,
  start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME DEFAULT NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT DEFAULT 1,
  additional_text VARCHAR(255)
)

GO

CREATE INDEX "custom_field_inf_idx" ON "custom_field_info" ("group_id");

GO

DROP TABLE custom_field_lookup

GO

CREATE TABLE custom_field_lookup (
  field_id INTEGER NOT NULL REFERENCES custom_field_info(field_id),
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BIT DEFAULT 1
)

GO

DROP TABLE custom_field_record

GO

CREATE TABLE custom_field_record (
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  category_id INTEGER NOT NULL REFERENCES custom_field_category(category_id),
  record_id INT IDENTITY PRIMARY KEY,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  enabled BIT DEFAULT 1
)

GO

CREATE INDEX "custom_field_rec_idx" ON "custom_field_record" ("link_module_id", "link_item_id", "category_id");

GO

DROP TABLE custom_field_data

GO

CREATE TABLE custom_field_data (
  record_id INTEGER NOT NULL REFERENCES custom_field_record(record_id),
  field_id INTEGER NOT NULL REFERENCES custom_field_info(field_id),
  selected_item_id INTEGER DEFAULT 0,
  entered_value TEXT,
  entered_number INTEGER,
  entered_float FLOAT
)

GO

CREATE INDEX "custom_field_dat_idx" ON "custom_field_data" ("record_id", "field_id");

GO

DROP TABLE motd

GO

DROP TABLE opportunity

GO

CREATE TABLE opportunity (
  opp_id INT IDENTITY PRIMARY KEY,
  owner INT not null REFERENCES access(user_id),
  description VARCHAR(80),
  acctlink INT not null default -1,
  contactlink INT not null default -1,
  closedate DATETIME not null,
  closeprob float,
  terms float,
  units char(1),
  lowvalue float,
  guessvalue float,
  highvalue float,
  stage INT references lookup_stage(code),
  stagedate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  commission float,
  type char(1),
  alertdate DATETIME,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  custom1 int default -1,
  custom2 int default -1,
  closed DATETIME,
  custom_data TEXT,
  alert varchar(100) default null,
  enabled BIT NOT NULL DEFAULT 1
)

GO

DROP TABLE call_log

GO

CREATE TABLE call_log (
  call_id INT IDENTITY PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  contact_id INT REFERENCES contact(contact_id),
  opp_id INT REFERENCES opportunity(opp_id),
  call_type_id INT REFERENCES lookup_call_types(code),
  length INTEGER,
  subject VARCHAR(255),
  notes TEXT,
  followup_date DATETIME,
  alertdate DATETIME,
  followup_notes TEXT,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  alert varchar(100) default null
)

GO

CREATE INDEX "call_log_cidx" ON "call_log" ("alertdate", "enteredby");

GO

DROP TABLE lookup_project_templates

GO

DROP TABLE groups

GO

DROP TABLE users

GO

DROP TABLE user_groups

GO

DROP TABLE departments

GO

DROP TABLE project_inbox

GO

DROP TABLE project_inbox_items

GO

DROP TABLE project_timesheet

GO

DROP TABLE projects

GO

CREATE TABLE projects (
	project_id INT IDENTITY PRIMARY KEY,
	group_id INTEGER NOT NULL ,
	department_id INTEGER REFERENCES lookup_department(code),
  template_id INTEGER,
	title VARCHAR(100) NOT NULL ,
	shortDescription VARCHAR(200) NOT NULL ,
	requestedBy VARCHAR(50) NULL ,
	requestedDept VARCHAR(50) NULL ,
	requestDate DATETIME DEFAULT CURRENT_TIMESTAMP NULL ,
	approvalDate DATETIME NULL ,
	closeDate DATETIME NULL,
  owner INTEGER NULL,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
)

GO

CREATE INDEX "projects_idx"
  ON "projects"
  ("group_id", "project_id");

GO

DROP TABLE project_requirements

GO

CREATE TABLE project_requirements (
	requirement_id INT IDENTITY PRIMARY KEY,
	project_id INTEGER NOT NULL REFERENCES projects(project_id),
	submittedBy VARCHAR(50) NULL,
	departmentBy VARCHAR(30) NULL,
	shortDescription VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
	dateReceived DATETIME NULL,
	estimated_loevalue INTEGER NULL,
  estimated_loetype INTEGER REFERENCES lookup_project_loe,
  actual_loevalue INTEGER NULL,
  actual_loetype INTEGER REFERENCES lookup_project_loe,
	deadline DATETIME NULL,
  approvedBy INTEGER REFERENCES access(user_id),
  approvalDate DATETIME NULL,
  closedBy INTEGER REFERENCES access(user_id),
	closeDate DATETIME NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
)

GO

DROP TABLE project_assignments

GO

CREATE TABLE project_assignments (
	assignment_id INT IDENTITY PRIMARY KEY,
	project_id INTEGER NOT NULL REFERENCES projects(project_id),
  requirement_id INTEGER NULL REFERENCES project_requirements(requirement_id),
	assignedBy INTEGER REFERENCES access(user_id),
	user_assign_id INTEGER NULL REFERENCES access(user_id),
	activity_id INTEGER REFERENCES lookup_project_activity,
	technology VARCHAR(50) NULL,
	role VARCHAR(255) NULL,
  estimated_loevalue INTEGER NULL,
  estimated_loetype INTEGER REFERENCES lookup_project_loe,
  actual_loevalue INTEGER NULL,
  actual_loetype INTEGER REFERENCES lookup_project_loe,
	priority_id INTEGER REFERENCES lookup_project_priority,
	assign_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  est_start_date DATETIME NULL,
	start_date DATETIME NULL,
	due_date DATETIME NULL,
  status_id INTEGER REFERENCES lookup_project_status,
	status_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	complete_date DATETIME NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
)

GO

CREATE INDEX "project_assignments_idx" ON "project_assignments"
  ("activity_id")

GO
  
CREATE INDEX "project_assignments_cidx" ON "project_assignments" 
  ("complete_date", "user_assign_id")

GO

DROP TABLE project_assignments_status

GO

CREATE TABLE project_assignments_status (
	status_id INT IDENTITY PRIMARY KEY,
	assignment_id INTEGER NOT NULL REFERENCES project_assignments,
	user_id INTEGER NOT NULL REFERENCES access(user_id),
	description TEXT NOT NULL,
	status_date DATETIME DEFAULT CURRENT_TIMESTAMP
)

GO

DROP TABLE project_issues

GO

CREATE TABLE project_issues (
	issue_id INT IDENTITY PRIMARY KEY,
	project_id INTEGER NOT NULL REFERENCES projects(project_id),
	type_id INTEGER NULL REFERENCES lookup_project_issues,
	subject VARCHAR(255) NOT NULL,
	message TEXT NOT NULL,
  importance INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
)

GO

CREATE INDEX "project_issues_limit_idx"
  ON "project_issues"
  ("type_id", "project_id", "enteredby")

GO
  
CREATE INDEX "project_issues_idx"
  ON "project_issues"
  ("issue_id")

GO
  
DROP TABLE project_issue_replies

GO 

CREATE TABLE project_issue_replies (
	reply_id INT IDENTITY PRIMARY KEY ,
	issue_id INTEGER NOT NULL REFERENCES project_issues,
  reply_to INTEGER DEFAULT 0 ,
	subject VARCHAR(50) NOT NULL ,
	message TEXT NOT NULL ,
  importance INTEGER NULL,
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
)

GO

DROP table project_team

GO

CREATE TABLE project_team (
	project_id INTEGER NOT NULL REFERENCES projects(project_id),
	user_id INTEGER NOT NULL REFERENCES access(user_id),
	userLevel INTEGER NULL,
	entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id)
)

GO

DROP TABLE ticket_level

GO

CREATE TABLE ticket_level (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL UNIQUE,
  default_item BIT DEFAULT 0,
  enabled BIT DEFAULT 1
)

GO

DROP TABLE ticket_source

GO

DROP TABLE ticket

GO

CREATE TABLE ticket (
  ticketid INT IDENTITY PRIMARY KEY,
  org_id INT REFERENCES organization, 
  contact_id INT REFERENCES contact,
  problem TEXT NOT NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  closed DATETIME,
  pri_code INT REFERENCES ticket_priority(code), 
  level_code INT REFERENCES ticket_level(code),
  department_code INT REFERENCES lookup_department,
  source_code INT REFERENCES lookup_ticketsource(code),
  cat_code INT,
  subcat_code1 INT,
  subcat_code2 INT,
  subcat_code3 INT,
  assigned_to INT REFERENCES access,
  comment TEXT,
  solution TEXT,
  scode INT REFERENCES ticket_severity(code),
  critical DATETIME,
  notified DATETIME,
  custom_data TEXT
)

GO 

CREATE INDEX "ticket_cidx" ON "ticket" ("assigned_to", "closed")

GO

DROP TABLE ticketlog

GO

CREATE TABLE ticketlog (
  id INT IDENTITY PRIMARY KEY
  ,ticketid INT REFERENCES ticket(ticketid)
  ,assigned_to INT REFERENCES access(user_id)
  ,comment TEXT
  ,closed BIT
  ,pri_code INT REFERENCES ticket_priority(code)
  ,level_code INT
  ,department_code INT REFERENCES lookup_department(code)
  ,cat_code INT
  ,scode INT REFERENCES ticket_severity(code)
  ,entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,enteredby INT NOT NULL REFERENCES access(user_id)
  ,modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,modifiedby INT NOT NULL REFERENCES access(user_id)
)

GO

DROP TABLE saved_criterialist
GO
DROP TABLE campaign
GO
DROP TABLE campaign_run
GO
DROP TABLE excluded_recipient
GO
DROP TABLE campaign_list_groups
GO
DROP TABLE scheduled_recipient
GO
DROP TABLE lookup_survey_types
GO
DROP TABLE survey
GO
DROP TABLE survey_answer
GO
DROP TABLE field_types
GO
DROP TABLE search_fields
GO
DROP TABLE message
GO
DROP TABLE survey_item
GO
DROP TABLE recipient_list
GO

CREATE TABLE saved_criterialist (
  id INT IDENTITY PRIMARY KEY,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  owner INTEGER NOT NULL REFERENCES access(user_id),
  name VARCHAR(80) NOT NULL,
  contact_source INTEGER DEFAULT -1,
  enabled BIT NOT NULL DEFAULT 1
)

GO

CREATE TABLE campaign (
  campaign_id INT IDENTITY PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  list_id int,
  message_id int DEFAULT -1,
  reply_addr VARCHAR(255) DEFAULT NULL,
  subject VARCHAR(255) DEFAULT NULL,
  message TEXT DEFAULT NULL,
  status_id INT DEFAULT 0,
  status VARCHAR(255),
  active BIT DEFAULT 0,
  active_date DATETIME DEFAULT NULL,
  send_method_id INT DEFAULT -1 NOT NULL,
  inactive_date DATETIME DEFAULT NULL,
  approval_date DATETIME DEFAULT NULL,
  approvedby INT REFERENCES access(user_id),
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
)

GO

CREATE TABLE campaign_run (
  id INT IDENTITY PRIMARY KEY,
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  status INTEGER NOT NULL DEFAULT 0,
  run_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  total_contacts INTEGER DEFAULT 0,
  total_sent INTEGER DEFAULT 0,
  total_replied INTEGER DEFAULT 0,
  total_bounced INTEGER DEFAULT 0
)

GO

CREATE TABLE excluded_recipient (
  id INT IDENTITY PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  contact_id INT NOT NULL REFERENCES contact(contact_id)
)

GO

CREATE TABLE campaign_list_groups (
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  group_id INT NOT NULL REFERENCES saved_criterialist(id)
)

GO

CREATE TABLE scheduled_recipient (
  id INT IDENTITY PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  contact_id INT NOT NULL REFERENCES contact(contact_id),
  run_id INT DEFAULT -1,
  status_id INT DEFAULT 0,
  status VARCHAR(255),
  status_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  scheduled_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  sent_date DATETIME DEFAULT NULL,
  reply_date DATETIME DEFAULT NULL,
  bounce_date DATETIME DEFAULT NULL
)

GO

CREATE TABLE lookup_survey_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
) 

GO

CREATE TABLE survey (
  survey_id INT IDENTITY PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  intro TEXT,
  itemLength INT DEFAULT -1,
  type INT NOT NULL REFERENCES lookup_survey_types(code),
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
)

GO

CREATE TABLE campaign_survey_link (
  campaign_id INT REFERENCES campaign(campaign_id),
  survey_id INT REFERENCES survey(survey_id)
)

GO

CREATE TABLE survey_questions (
  question_id INT IDENTITY PRIMARY KEY,
  survey_id INT NOT NULL REFERENCES survey(survey_id),
  type INT NOT NULL REFERENCES lookup_survey_types(code),
  description VARCHAR(255)
)

GO

CREATE TABLE active_survey (
  active_survey_id INT IDENTITY PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  intro TEXT,
  itemLength int default -1,
  type INT NOT NULL REFERENCES lookup_survey_types(code),
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
)

GO

CREATE TABLE active_survey_questions (
  question_id INT IDENTITY PRIMARY KEY,
  active_survey_id INT REFERENCES active_survey(active_survey_id),
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
)

GO

CREATE TABLE active_survey_responses (
  response_id INT IDENTITY PRIMARY KEY,
  active_survey_id INT NOT NULL REFERENCES active_survey(active_survey_id),
  contact_id INT NOT NULL DEFAULT -1,
  unique_code VARCHAR(255),
  ip_address VARCHAR(15) NOT NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) 

GO

CREATE TABLE active_survey_answers (
  answer_id INT IDENTITY primary key,
  response_id INT NOT NULL REFERENCES active_survey_responses(response_id),
  question_id INT NOT NULL REFERENCES active_survey_questions(question_id),
  comments TEXT,
  quant_ans int DEFAULT -1,
  text_ans TEXT
)

GO

CREATE TABLE field_types (
  id INT IDENTITY PRIMARY KEY,
  data_typeid int NOT NULL DEFAULT -1,
  data_type VARCHAR(20),
  operator VARCHAR(50),
  display_text varchar(50)
)

GO

CREATE TABLE search_fields (
  id INT IDENTITY PRIMARY KEY,
  field varchar(80),
  description VARCHAR(255),
  searchable BIT NOT NULL DEFAULT 1,
  field_typeid int NOT NULL DEFAULT -1,
  table_name varchar(80),
  object_class varchar(80),
  enabled BIT DEFAULT 1
)

GO

CREATE TABLE message (
  id INT IDENTITY PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  template_id INT,
  subject VARCHAR(255) DEFAULT NULL,
  body TEXT,
  reply_addr VARCHAR(100),
  url VARCHAR(255),
  img VARCHAR(80),
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
)

GO

DROP TABLE message_template
GO

CREATE TABLE message_template (
  id INT IDENTITY PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  template_file varchar(80),
  num_imgs INT,
  num_urls INT,
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
)

GO

DROP TABLE saved_criteriaelement
GO

CREATE TABLE saved_criteriaelement (
  id INTEGER NOT NULL REFERENCES saved_criterialist(id),
  field INTEGER NOT NULL references search_fields(id),
  operator VARCHAR(50) NOT NULL,
  operatorid INTEGER NOT NULL references field_types(id),
  value VARCHAR(80) NOT NULL
)

GO

INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('company', 'Company Name', 1, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('namefirst', 'Contact First Name', 1, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('namelast', 'Contact Last Name', 1, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('entered', 'Entered Date', 1, 1);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('zip', 'Zip Code', 1, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('areacode', 'Area Code', 1, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('city', 'City', 1, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('typeId', 'Contact Type', 1, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('contactId', 'Contact ID', 0, 0);
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('title', 'Contact Title', 0, 0);

INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', '=', 'is');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', '!=', 'is not');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', '= | or field_name is null', 'is empty');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', '!= | and field_name is not null', 'is not empty');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', 'like %search_value%', 'contains');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', 'not like %search_value%', 'does not contain');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '<', 'before');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '>', 'after');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', 'between', 'between');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '<=', 'on or before');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '>=', 'on or after');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '>', 'greater than');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '<', 'less than');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '=', 'equals');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '>=', 'greater than or equal to');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '<=', 'less than or equal to');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', 'is not null', 'exist');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', 'is null', 'does not exist');

INSERT INTO lookup_survey_types (description) VALUES ('Open-Ended');
INSERT INTO lookup_survey_types (description) VALUES ('Quantitative (no comments)');
INSERT INTO lookup_survey_types (description) VALUES ('Quantitative (with comments)');

GO



CREATE TABLE lookup_revenue_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)

GO


CREATE TABLE lookup_revenuedetail_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)

GO

CREATE TABLE revenue (
  id INT IDENTITY PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  transaction_id INT DEFAULT -1,
  month INT DEFAULT -1,
  year INT DEFAULT -1,
  amount FLOAT DEFAULT 0,
  type INT REFERENCES lookup_revenue_types(code),
  owner INT REFERENCES access(user_id),
  description VARCHAR(255),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id)
)

GO

CREATE TABLE revenue_detail (
  id INT IDENTITY PRIMARY KEY,
  revenue_id int references revenue(id),
  amount float default 0,
  type int references lookup_revenue_types(code),
  owner int references access(user_id),
  description VARCHAR(255),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id)
)

GO

INSERT INTO lookup_revenue_types (description) VALUES ('Technical');

GO

CREATE TABLE lookup_task_priority (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)

GO

CREATE TABLE lookup_task_loe (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)

GO

CREATE TABLE task (
  task_id INT IDENTITY PRIMARY KEY,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  priority INTEGER NOT NULL REFERENCES lookup_task_priority,
  description VARCHAR(80),
  duedate DATETIME,
  reminderid INT,
  notes TEXT,
  sharing INT NOT NULL,
  complete BIT DEFAULT 0 NOT NULL,
  enabled BIT DEFAULT 0 NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT REFERENCES access(user_id),
  estimatedloe FLOAT,
  estimatedloetype INTEGER REFERENCES lookup_task_loe,
  owner INTEGER NOT NULL,
  completedate DATETIME
  )
  
GO

CREATE TABLE tasklink_contact (
  task_id INT NOT NULL REFERENCES task,
  contact_id INT NOT NULL REFERENCES contact(contact_id),
  notes VARCHAR(255)
  )
  
GO

CREATE TABLE tasklink_ticket (
  task_id INT NOT NULL REFERENCES task,
  ticket_id INT NOT NULL  REFERENCES ticket(ticketid)
  )
  
GO

INSERT INTO lookup_task_loe (level, description, default_item) VALUES (1, 'Minute(s)', 0);
INSERT INTO lookup_task_loe (level, description, default_item) VALUES (1, 'Hour(s)', 1);
INSERT INTO lookup_task_loe (level, description, default_item) VALUES (1, 'Day(s)', 0);
INSERT INTO lookup_task_loe (level, description, default_item) VALUES (1, 'Week(s)', 0);
INSERT INTO lookup_task_loe (level, description, default_item) VALUES (1, 'Month(s)', 0);

INSERT INTO lookup_task_priority (level, description, default_item) VALUES (1, '1', 1);
INSERT INTO lookup_task_priority (level, description) VALUES (2, '2');
INSERT INTO lookup_task_priority (level, description) VALUES (3, '3');
INSERT INTO lookup_task_priority (level, description) VALUES (4, '4');
INSERT INTO lookup_task_priority (level, description) VALUES (5, '5');

