/* 2003-01-06: Need to perform this manually, updates CFS to 2.5 database 

  Manual entries:
  
  GUI: REMOVE default from project_files.folder_id
  SQL: UPDATE project_files SET folder_id = NULL WHERE folder_id = 0;
  
  ?Update the permissions tables with module code
  
  GUI: ALTER TABLE account_type_levels RENAME COLUMN id TO org_id
  
  GUI: ALTER TABLE organization DROP COLUMN duplicate
  
  GUI: CREATE project_files constraints 
  
ALTER TABLE project_files ADD 
	 FOREIGN KEY 
	(
		[folder_id]
	) REFERENCES project_folders (
		[folder_id]
	),
	 FOREIGN KEY 
	(
		enteredby
	) REFERENCES access (
		[user_id]
	),
	 FOREIGN KEY 
	(
		modifiedby
	) REFERENCES access (
		[user_id]
	)
GO

*/



UPDATE access_log
SET user_id = a.user_id 
FROM access a 
WHERE access_log.user_id = -1 
AND access_log.username = a.username

GO

DELETE FROM access_log
WHERE user_id = -1

GO

/* TODO: CHECK THIS VALUE BEFORE RUNNING 
ALTER TABLE access_log DROP CONSTRAINT DF_access_log_user_id
GO
*/

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

CREATE TABLE usage_log (
  usage_id INT IDENTITY PRIMARY KEY,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NULL,
  action INT NOT NULL,
  record_id INT NULL,
  record_size INT NULL
)
GO

/* TODO: Verify existing entries for insert afterwards */
DROP TABLE lookup_contact_types
GO
CREATE TABLE lookup_contact_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  user_id INT references access(user_id),
  category INT NOT NULL DEFAULT 0
)
GO
INSERT INTO lookup_contact_types (description) VALUES ('Employee');
INSERT INTO lookup_contact_types (description) VALUES ('Personal');
INSERT INTO lookup_contact_types (description) VALUES ('Sales');
INSERT INTO lookup_contact_types (description) VALUES ('Billing');
INSERT INTO lookup_contact_types (description) VALUES ('Technical');
GO

ALTER TABLE organization DROP COLUMN type
ALTER TABLE organization DROP COLUMN industry_desc
ALTER TABLE organization DROP COLUMN ins_type
ALTER TABLE organization DROP COLUMN cust_status
ALTER TABLE organization DROP COLUMN area
ALTER TABLE organization DROP COLUMN industry_code
ALTER TABLE organization ALTER COLUMN owner int NULL
ALTER TABLE organization ALTER COLUMN duplicate_id int NULL
ALTER TABLE organization ADD [namesalutation] [varchar] (80)NULL
ALTER TABLE organization ADD [namelast] [varchar] (80) NULL
ALTER TABLE organization ADD [namefirst] [varchar] (80) NULL
ALTER TABLE organization ADD [namemiddle] [varchar] (80) NULL
ALTER TABLE organization ADD [namesuffix] [varchar] (80) NULL

ALTER TABLE contact ALTER COLUMN owner INT NULL
ALTER TABLE contact ADD primary_contact BIT DEFAULT 0

ALTER TABLE permission_category ADD [folders] [bit] NULL DEFAULT 0
GO
UPDATE permission_category SET folders = 0
GO
ALTER TABLE permission_category ALTER COLUMN [folders] [bit] NOT NULL
GO
ALTER TABLE permission_category ADD [lookups] [bit] NULL DEFAULT 0
GO
UPDATE permission_category SET lookups = 0
GO
ALTER TABLE permission_category ALTER COLUMN [lookups] [bit] NOT NULL
GO

DROP TABLE news
GO
CREATE TABLE news (
  rec_id INT IDENTITY PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  url TEXT,
  base TEXT,
  headline TEXT,
  body TEXT,
  dateEntered DATETIME,
  type CHAR(1),
  created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE organization_phone ALTER COLUMN number VARCHAR(30) NULL
GO

ALTER TABLE contact_phone ALTER COLUMN number VARCHAR(30) NULL
GO

DROP TABLE cfsinbox_messagelink
GO
DROP TABLE cfsinbox_message
GO
CREATE TABLE cfsinbox_message (
  id INT IDENTITY PRIMARY KEY,
  subject VARCHAR(255) DEFAULT NULL,
  body TEXT NOT NULL,
  reply_id INT NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  sent DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  type int not null default -1,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  delete_flag BIT default 0
)
GO
CREATE TABLE cfsinbox_messagelink (
  id INT NOT NULL REFERENCES cfsinbox_message(id),
  sent_to INT NOT NULL REFERENCES contact(contact_id),
  status INT NOT NULL DEFAULT 0,
  viewed DATETIME DEFAULT NULL,
  enabled BIT NOT NULL DEFAULT 1,
  sent_from INT NOT NULL REFERENCES access(user_id)
)
GO
CREATE TABLE contact_type_levels (
  contact_id INT NOT NULL REFERENCES contact(contact_id),
  type_id INT NOT NULL REFERENCES lookup_contact_types(code),
  level INTEGER not null,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
)
GO
CREATE TABLE lookup_lists_lookup(
  id INT IDENTITY PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  lookup_id INT NOT NULL,
  class_name VARCHAR(20),
  table_name VARCHAR(60),
  level INTEGER DEFAULT 0,
  description TEXT,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP
)
GO

DROP TABLE opportunity
CREATE TABLE opportunity (
  opp_id INT IDENTITY PRIMARY KEY,
  owner INT NOT NULL REFERENCES access(user_id),
  description VARCHAR(80),
  acctlink INT DEFAULT -1,
  contactlink INT DEFAULT -1,
  closedate DATETIME NOT NULL,
  closeprob FLOAT,
  terms FLOAT,
  units CHAR(1),
  lowvalue FLOAT,
  guessvalue FLOAT,
  highvalue FLOAT,
  stage INT REFERENCES lookup_stage(code),
  stagedate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  commission FLOAT,
  type CHAR(1),
  alertdate DATETIME,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  custom1 int default -1,
  custom2 int default -1,
  closed DATETIME,
  custom_data TEXT,
  alert varchar(100) DEFAULT NULL,
  enabled BIT NOT NULL DEFAULT 1,
  notes TEXT
)
GO
CREATE TABLE lookup_opportunity_types (
  code INT IDENTITY PRIMARY KEY,
  order_id INT,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)
GO
CREATE TABLE opportunity_header (
  opp_id INT IDENTITY PRIMARY KEY,
  description VARCHAR(80),
  acctlink INT default -1,
  contactlink INT default -1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
)
GO
CREATE TABLE opportunity_component (
  id INT IDENTITY PRIMARY KEY,
  opp_id INT REFERENCES opportunity_header(opp_id),
  owner INT NOT NULL REFERENCES access(user_id),
  description VARCHAR(80),
  closedate DATETIME NOT NULL,
  closeprob FLOAT,
  terms FLOAT,
  units CHAR(1),
  lowvalue FLOAT,
  guessvalue FLOAT,
  highvalue FLOAT,
  stage INT REFERENCES lookup_stage(code),
  stagedate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  commission FLOAT,
  type CHAR(1),
  alertdate DATETIME,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),  
  closed DATETIME,
  alert VARCHAR(100) DEFAULT NULL,
  enabled BIT NOT NULL DEFAULT 1,
  notes TEXT
)
GO
CREATE TABLE opportunity_type_levels (
  opp_id INT NOT NULL REFERENCES opportunity(opp_id),
  type_id INT NOT NULL REFERENCES lookup_opportunity_types(code),
  level INTEGER not null,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
)
GO
CREATE TABLE opportunity_component_levels (
  opp_id INT NOT NULL REFERENCES opportunity_component(id),
  type_id INT NOT NULL REFERENCES lookup_opportunity_types(code),
  level INTEGER NOT NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
)

DROP TABLE call_log
DROP TABLE lookup_call_types
GO
CREATE TABLE lookup_call_types (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
)
GO
CREATE TABLE call_log (
  call_id INT IDENTITY PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  contact_id INT REFERENCES contact(contact_id),
  opp_id INT REFERENCES opportunity_header(opp_id),
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
  alert VARCHAR(100) DEFAULT NULL
)
GO

CREATE INDEX "call_log_cidx" ON "call_log" ("alertdate", "enteredby");
GO

DROP TABLE ticketlog
DROP TABLE ticket
DROP TABLE ticket_level
DROP TABLE ticket_source
DROP TABLE ticket_severity
DROP TABLE lookup_ticketsource
DROP TABLE ticket_priority
DROP TABLE ticket_category
GO
CREATE TABLE ticket_level (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL UNIQUE,
  default_item BIT DEFAULT 0,
  level INT DEFAULT 0,
  enabled BIT DEFAULT 1
)
GO
CREATE TABLE ticket_severity (
  code INT IDENTITY PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,style text NOT NULL DEFAULT ''
  ,default_item BIT DEFAULT 0
  ,level INTEGER DEFAULT 0
  ,enabled BIT DEFAULT 1
)
GO
CREATE TABLE lookup_ticketsource (
  code INT IDENTITY PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,default_item BIT DEFAULT 0
  ,level INTEGER DEFAULT 0
  ,enabled BIT DEFAULT 1
)
GO
CREATE TABLE ticket_priority (
  code INT IDENTITY PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,style text NOT NULL DEFAULT '' 
  ,default_item BIT DEFAULT 0
  ,level INTEGER DEFAULT 0
  ,enabled BIT DEFAULT 1
)
GO
CREATE TABLE ticket_category ( 
  id INT IDENTITY PRIMARY KEY
  ,cat_level int  NOT NULL DEFAULT 0 
  ,parent_cat_code int  NOT NULL 
  ,description VARCHAR(300) NOT NULL 
  ,full_description text NOT NULL DEFAULT ''
  ,default_item BIT DEFAULT 0
  ,level INTEGER DEFAULT 0
  ,enabled BIT DEFAULT 1
)
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

INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES 
  ('Normal','background-color:lightgreen;color:black;',0,0,1);
INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES 
  ('Important','background-color:yellow;color:black;',0,1,1);
INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES 
  ('Critical','background-color:red;color:black;font-weight:bold;',0,2,1);
  
INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES 
  ('Scheduled','background-color:lightgreen;color:black;',0,0,1);
INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES 
  ('Next','background-color:yellow;color:black;',0,1,1);
INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES 
  ('Immediate','background-color:red;color:black;font-weight:bold;',0,2,1);

INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Sales','',0,1,1);
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Billing','',0,2,1);
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Technical','',0,3,1);
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Order','',0,4,1);
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Other','',0,5,1);




CREATE TABLE module_field_categorylink (
  id INTEGER IDENTITY PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  category_id INT UNIQUE NOT NULL,
  level INTEGER DEFAULT 0,
  description TEXT,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP
)
GO
DROP TABLE custom_field_data
DROP TABLE custom_field_record
DROP TABLE custom_field_lookup
DROP TABLE custom_field_info
DROP TABLE custom_field_group
DROP TABLE custom_field_category
GO
CREATE TABLE custom_field_category (
  module_id INTEGER NOT NULL REFERENCES module_field_categorylink(category_id),
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

DROP TABLE lookup_project_templates
DROP TABLE groups
DROP TABLE users
DROP TABLE user_groups
DROP TABLE departments
DROP TABLE project_inbox
DROP TABLE project_inbox_items
DROP TABLE project_timesheet
GO
DROP TABLE project_team
DROP TABLE project_folders
DROP TABLE project_issue_replies
DROP TABLE project_issues
DROP TABLE project_assignments_status
DROP TABLE project_assignments
DROP TABLE project_requirements
DROP TABLE projects
GO
CREATE TABLE projects (
  project_id INT IDENTITY PRIMARY KEY,
  group_id INTEGER NULL,
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
  ("group_id", "project_id")
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
CREATE TABLE project_assignments_status (
  status_id INT IDENTITY PRIMARY KEY,
  assignment_id INTEGER NOT NULL REFERENCES project_assignments,
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  description TEXT NOT NULL,
  status_date DATETIME DEFAULT CURRENT_TIMESTAMP
)
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
CREATE TABLE project_folders (
  folder_id INT IDENTITY PRIMARY KEY,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  subject VARCHAR(255) NOT NULL,
  description TEXT,
  parent INT NULL
)
GO
ALTER TABLE project_files ALTER COLUMN project_id INT NULL
ALTER TABLE project_files_download ALTER COLUMN user_download_id INT NULL
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

DROP TABLE recipient_list
DROP TABLE saved_criteriaelement
DROP TABLE message_template
DROP TABLE message
DROP TABLE search_fields
DROP TABLE field_types
DROP TABLE scheduled_recipient
DROP TABLE campaign_list_groups
DROP TABLE excluded_recipient
DROP TABLE campaign_run
DROP TABLE campaign
DROP TABLE saved_criterialist

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
CREATE TABLE active_campaign_groups (
  id INT IDENTITY PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  groupname VARCHAR(80) NOT NULL,
  groupcriteria TEXT DEFAULT NULL
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
  outro TEXT,
  itemLength INT DEFAULT -1,
  type INT DEFAULT -1,
  enabled BIT NOT NULL DEFAULT 1,
  status INT NOT NULL DEFAULT -1,
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
  description VARCHAR(255),
  required BIT NOT NULL DEFAULT 0,
  position INT NOT NULL DEFAULT 0
)
GO
CREATE TABLE survey_items (
  item_id INT IDENTITY PRIMARY KEY,
  question_id INT NOT NULL REFERENCES survey_questions(question_id),
  type INT DEFAULT -1,
  description VARCHAR(255)
)
GO
CREATE TABLE active_survey (
  active_survey_id INT IDENTITY PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  name VARCHAR(80) NOT NULL,
  description VARCHAR(255),
  intro TEXT,
  outro TEXT,
  itemLength INT DEFAULT -1,
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
  required BIT NOT NULL DEFAULT 0,
  position INT NOT NULL DEFAULT 0,
  average FLOAT DEFAULT 0.00,
  total1 INT DEFAULT 0,
  total2 INT DEFAULT 0,
  total3 INT DEFAULT 0,
  total4 INT DEFAULT 0,
  total5 INT DEFAULT 0,
  total6 INT DEFAULT 0,
  total7 INT DEFAULT 0
)
GO
CREATE TABLE active_survey_items (
  item_id INTEGER IDENTITY PRIMARY KEY,
  question_id INT NOT NULL REFERENCES active_survey_questions(question_id),
  type INT DEFAULT -1,
  description VARCHAR(255)
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
  quant_ans INT DEFAULT -1,
  text_ans TEXT
)
GO
CREATE TABLE active_survey_answer_items (
  id INTEGER IDENTITY PRIMARY KEY,
  item_id INT NOT NULL REFERENCES active_survey_items(item_id),
  answer_id INT NOT NULL REFERENCES active_survey_answers(answer_id),
  comments TEXT
)
GO
CREATE TABLE active_survey_answer_avg (
  id INTEGER IDENTITY PRIMARY KEY,
  question_id INT NOT NULL REFERENCES active_survey_questions(question_id),
  item_id INT NOT NULL REFERENCES active_survey_items(item_id),
  total INT NOT NULL DEFAULT 0
)
GO
CREATE TABLE field_types (
  id INT IDENTITY PRIMARY KEY,
  data_typeid int NOT NULL DEFAULT -1,
	data_type VARCHAR(20),
  operator VARCHAR(50),
  display_text varchar(50),
  enabled BIT DEFAULT 1
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
CREATE TABLE saved_criteriaelement (
  id INTEGER NOT NULL REFERENCES saved_criterialist(id),
  field INTEGER NOT NULL references search_fields(id),
  operator VARCHAR(50) NOT NULL,
  operatorid INTEGER NOT NULL references field_types(id),
  value VARCHAR(80) NOT NULL,
  source INT NOT NULL DEFAULT -1
)
GO


CREATE TABLE sync_log (
  log_id INT IDENTITY PRIMARY KEY,
  system_id INT NOT NULL REFERENCES sync_system(system_id),
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  ip VARCHAR(15),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
)
GO
CREATE TABLE sync_transaction_log (
  transaction_id INT IDENTITY PRIMARY KEY,
  log_id INT NOT NULL REFERENCES sync_log(log_id),
  reference_id VARCHAR(50),
  element_name VARCHAR(255),
  action VARCHAR(20),
  link_item_id INT,
  status_code INT,
  record_count INT,
  message TEXT
)
GO
CREATE TABLE process_log (
  process_id INT IDENTITY PRIMARY KEY,
  system_id INT NOT NULL REFERENCES sync_system(system_id),
  client_id INT NOT NULL REFERENCES sync_client(client_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  process_name VARCHAR(255),
  process_version VARCHAR(20),
  status INT,
  message TEXT
)
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
  revenue_id INT REFERENCES revenue(id),
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
CREATE TABLE lookup_task_category (
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
  owner INTEGER REFERENCES access(user_id),
  completedate DATETIME,
  category_id INTEGER REFERENCES lookup_task_category
)
GO
CREATE TABLE tasklink_contact (
  task_id INT NOT NULL REFERENCES task,
  contact_id INT NOT NULL REFERENCES contact(contact_id),
  notes TEXT
)
GO
CREATE TABLE tasklink_ticket (
  task_id INT NOT NULL REFERENCES task,
  ticket_id INT NOT NULL REFERENCES ticket(ticketid)
)
GO
CREATE TABLE tasklink_project (
  task_id INT NOT NULL REFERENCES task,
  project_id INT NOT NULL REFERENCES projects(project_id)
)
GO
CREATE TABLE taskcategory_project (
  category_id INTEGER NOT NULL REFERENCES lookup_task_category,
  project_id INTEGER NOT NULL REFERENCES projects(project_id)
)
GO

DROP TABLE config
DROP TABLE industry_temp
DROP TABLE mod_log
DROP TABLE motd
DROP TABLE note
DROP TABLE org_type
DROP TABLE system_modules
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


INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Phone Call', 1, 10);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Fax', 0, 20);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('In-Person', 0, 30);

insert into lookup_opportunity_types (description, level) values ('Type 1', 0);
insert into lookup_opportunity_types (description, level) values ('Type 2', 1);
insert into lookup_opportunity_types (description, level) values ('Type 3', 2);
insert into lookup_opportunity_types (description, level) values ('Type 4', 3);
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
INSERT INTO search_fields (field, description, searchable, field_typeid) VALUES ('accountTypeId', 'Account Type', 1, 0);

INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', '=', 'is');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (0, 'string', '!=', 'is not');
INSERT INTO field_types (data_typeID, data_type, operator, display_text, enabled) VALUES (0, 'string', '= | or field_name is null', 'is empty', 0);
INSERT INTO field_types (data_typeID, data_type, operator, display_text, enabled) VALUES (0, 'string', '!= | and field_name is not null', 'is not empty', 0);
INSERT INTO field_types (data_typeID, data_type, operator, display_text, enabled) VALUES (0, 'string', 'like %search_value%', 'contains', 0);
INSERT INTO field_types (data_typeID, data_type, operator, display_text, enabled) VALUES (0, 'string', 'not like %search_value%', 'does not contain', 0);
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '<', 'before');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '>', 'after');
INSERT INTO field_types (data_typeID, data_type, operator, display_text, enabled) VALUES (1, 'date', 'between', 'between', 0);
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '<=', 'on or before');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (1, 'date', '>=', 'on or after');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '>', 'greater than');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '<', 'less than');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '=', 'equals');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '>=', 'greater than or equal to');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', '<=', 'less than or equal to');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', 'is not null', 'exist');
INSERT INTO field_types (data_typeID, data_type, operator, display_text) VALUES (2, 'number', 'is null', 'does not exist');
GO

INSERT INTO lookup_survey_types (description) VALUES ('Open-Ended');
INSERT INTO lookup_survey_types (description) VALUES ('Quantitative (no comments)');
INSERT INTO lookup_survey_types (description) VALUES ('Quantitative (with comments)');
INSERT INTO lookup_survey_types (description) VALUES ('Item List');
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

UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.service.base.SyncClient' WHERE mapped_class_name = 'com.darkhorseventures.cfsbase.SyncClient'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.admin.base.User' WHERE mapped_class_name = 'com.darkhorseventures.cfsbase.User'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.accounts.base.Organization' WHERE mapped_class_name = 'com.darkhorseventures.cfsbase.Organization'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.media.autoguide.base.Inventory' WHERE mapped_class_name = 'com.darkhorseventures.autoguide.base.Inventory'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.media.autoguide.base.InventoryOption' WHERE mapped_class_name = 'com.darkhorseventures.autoguide.base.InventoryOption'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.media.autoguide.base.AdRun' WHERE mapped_class_name = 'com.darkhorseventures.autoguide.base.AdRun'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.service.base.SyncTableList' WHERE mapped_class_name = 'com.darkhorseventures.cfsbase.SyncTableList'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.admin.base.UserList' WHERE mapped_class_name = 'com.darkhorseventures.cfsbase.UserList'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.media.autoguide.base.MakeList' WHERE mapped_class_name = 'com.darkhorseventures.autoguide.base.MakeList'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.media.autoguide.base.ModelList' WHERE mapped_class_name = 'com.darkhorseventures.autoguide.base.ModelList'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.media.autoguide.base.VehicleList' WHERE mapped_class_name = 'com.darkhorseventures.autoguide.base.VehicleList'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.accounts.base.OrganizationList' WHERE mapped_class_name = 'com.darkhorseventures.cfsbase.OrganizationList'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.media.autoguide.base.InventoryList' WHERE mapped_class_name = 'com.darkhorseventures.autoguide.base.InventoryList'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.media.autoguide.base.OptionList' WHERE mapped_class_name = 'com.darkhorseventures.autoguide.base.OptionList'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.media.autoguide.base.InventoryOptionList' WHERE mapped_class_name = 'com.darkhorseventures.autoguide.base.InventoryOptionList'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.utils.web.LookupList' WHERE mapped_class_name = 'com.darkhorseventures.webutils.LookupList'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.media.autoguide.base.AdRunList' WHERE mapped_class_name = 'com.darkhorseventures.autoguide.base.AdRunList'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.utils.web.LookupElement' WHERE mapped_class_name = 'com.darkhorseventures.webutils.LookupElement'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.utils.web.CustomLookupElement' WHERE mapped_class_name = 'com.darkhorseventures.webutils.CustomLookupElement'
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.troubletickets.base.Ticket' WHERE mapped_class_name = 'com.darkhorseventures.cfsbase.Ticket'

INSERT INTO lookup_ticketsource (level,description) VALUES (1,'Phone');
INSERT INTO lookup_ticketsource (level,description) VALUES (2,'Email');
INSERT INTO lookup_ticketsource (level,description) VALUES (3,'Letter');
INSERT INTO lookup_ticketsource (level,description) VALUES (4,'Other');
GO


ALTER TABLE [dbo].[access_log] ADD 
	 FOREIGN KEY 
	(
		[user_id]
	) REFERENCES [dbo].[access] (
		[user_id]
	)
GO

ALTER TABLE [dbo].[account_type_levels] ADD 
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [dbo].[organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[type_id]
	) REFERENCES [dbo].[lookup_account_types] (
		[code]
	)
GO

ALTER TABLE [dbo].[autoguide_ad_run] ADD 
	 FOREIGN KEY 
	(
		[inventory_id]
	) REFERENCES [dbo].[autoguide_inventory] (
		[inventory_id]
	)
GO

ALTER TABLE [dbo].[autoguide_model] ADD 
	 FOREIGN KEY 
	(
		[make_id]
	) REFERENCES [dbo].[autoguide_make] (
		[make_id]
	)
GO

ALTER TABLE [dbo].[autoguide_vehicle] ADD 
	 FOREIGN KEY 
	(
		[make_id]
	) REFERENCES [dbo].[autoguide_make] (
		[make_id]
	),
	 FOREIGN KEY 
	(
		[model_id]
	) REFERENCES [dbo].[autoguide_model] (
		[model_id]
	)
GO

ALTER TABLE [dbo].[contact] ADD 
	 FOREIGN KEY 
	(
		[assistant]
	) REFERENCES [dbo].[contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[department]
	) REFERENCES [dbo].[lookup_department] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [dbo].[organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[super]
	) REFERENCES [dbo].[contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[type_id]
	) REFERENCES [dbo].[lookup_contact_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[user_id]
	) REFERENCES [dbo].[access] (
		[user_id]
	)
GO

ALTER TABLE [dbo].[help_contents] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [dbo].[access] (
		[user_id]
	)
GO

ALTER TABLE [dbo].[organization] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[owner]
	) REFERENCES [dbo].[access] (
		[user_id]
	)
GO

ALTER TABLE [dbo].[project_files_download] ADD 
	 FOREIGN KEY 
	(
		[item_id]
	) REFERENCES [dbo].[project_files] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[user_download_id]
	) REFERENCES [dbo].[access] (
		[user_id]
	)
GO

ALTER TABLE [dbo].[project_files_version] ADD 
	 FOREIGN KEY 
	(
		[item_id]
	) REFERENCES [dbo].[project_files] (
		[item_id]
	)
GO

ALTER TABLE [dbo].[role] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [dbo].[access] (
		[user_id]
	)
GO

ALTER TABLE [dbo].[sync_conflict_log] ADD 
	 FOREIGN KEY 
	(
		[table_id]
	) REFERENCES [dbo].[sync_table] (
		[table_id]
	)
GO

ALTER TABLE [dbo].[sync_map] ADD 
	 FOREIGN KEY 
	(
		[client_id]
	) REFERENCES [dbo].[sync_client] (
		[client_id]
	),
	 FOREIGN KEY 
	(
		[table_id]
	) REFERENCES [dbo].[sync_table] (
		[table_id]
	)
GO

ALTER TABLE [dbo].[sync_table] ADD 
	 FOREIGN KEY 
	(
		[system_id]
	) REFERENCES [dbo].[sync_system] (
		[system_id]
	)
GO

ALTER TABLE [dbo].[contact_address] ADD 
	 FOREIGN KEY 
	(
		[address_type]
	) REFERENCES [dbo].[lookup_contactaddress_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [dbo].[contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [dbo].[access] (
		[user_id]
	)
GO

ALTER TABLE [dbo].[contact_emailaddress] ADD 
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [dbo].[contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[emailaddress_type]
	) REFERENCES [dbo].[lookup_contactemail_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [dbo].[access] (
		[user_id]
	)
GO

ALTER TABLE [dbo].[contact_phone] ADD 
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [dbo].[contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[phone_type]
	) REFERENCES [dbo].[lookup_contactphone_types] (
		[code]
	)
GO

ALTER TABLE [dbo].[organization_address] ADD 
	 FOREIGN KEY 
	(
		[address_type]
	) REFERENCES [dbo].[lookup_orgaddress_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [dbo].[organization] (
		[org_id]
	)
GO

ALTER TABLE [dbo].[organization_emailaddress] ADD 
	 FOREIGN KEY 
	(
		[emailaddress_type]
	) REFERENCES [dbo].[lookup_orgemail_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [dbo].[organization] (
		[org_id]
	)
GO

ALTER TABLE [dbo].[organization_phone] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [dbo].[access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [dbo].[organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[phone_type]
	) REFERENCES [dbo].[lookup_orgphone_types] (
		[code]
	)
GO

INSERT INTO contact_type_levels
SELECT contact_id, type_id, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM contact
WHERE type_id IS NOT NULL
GO

UPDATE permission_category SET folders = 1, lookups = 1 WHERE category = 'Account Management'
UPDATE permission_category SET folders = 1, lookups = 1 WHERE category = 'Contacts & Resources'
UPDATE permission_category SET folders = 0, lookups = 0 WHERE category = 'Auto Guide'
UPDATE permission_category SET folders = 0, lookups = 1 WHERE category = 'Pipeline Management'
UPDATE permission_category SET folders = 0, lookups = 0 WHERE category = 'Demo'
UPDATE permission_category SET folders = 0, lookups = 0 WHERE category = 'Campaign Manager'
UPDATE permission_category SET folders = 0, lookups = 0 WHERE category = 'Project Management'
UPDATE permission_category SET folders = 0, lookups = 1 WHERE category = 'Tickets'
UPDATE permission_category SET folders = 0, lookups = 0 WHERE category = 'Admin'
UPDATE permission_category SET folders = 0, lookups = 0 WHERE category = 'Help'
UPDATE permission_category SET folders = 0, lookups = 0 WHERE category = 'System'
UPDATE permission_category SET folders = 0, lookups = 0 WHERE category = 'My Home Page'
GO

/* TODO: Verify these values in existing data before inserting */
INSERT INTO module_field_categorylink (module_id, category_id) VALUES (5, 1);
INSERT INTO module_field_categorylink (module_id, category_id) VALUES (3, 2);
GO

INSERT INTO lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) VALUES (5, 1, 'lookupList', 'lookup_account_types', 1, 'Account Types');
INSERT INTO lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) VALUES (5, 2, 'lookupList', 'lookup_revenue_types', 2, 'Revenue Types');
INSERT INTO lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) VALUES (5, 3, 'contactType', '', 3, 'Contact Types');
INSERT INTO lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) VALUES (3, 1, 'contactType', '', 1, 'Contact Types');
INSERT INTO lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) VALUES (3, 2, 'lookupList', 'lookup_contactemail_types', 2, 'Contact Email Type');
INSERT INTO lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) VALUES (3, 3, 'lookupList', 'lookup_contactaddress_types', 3, 'Contact Address Type');
INSERT INTO lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) VALUES (3, 4, 'lookupList', 'lookup_contactphone_types', 4, 'Contact Phone Type');
INSERT INTO lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) VALUES (3, 5, 'lookupList', 'lookup_department', 5, 'Department');
INSERT INTO lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) VALUES (4, 1, 'lookupList', 'lookup_stage', 1, 'Stage');
INSERT INTO lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) VALUES (4, 2, 'lookupList', 'lookup_opportunity_types', 2, 'Opportunity Type');
INSERT INTO lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) VALUES (8, 1, 'lookupList', 'lookup_ticketsource', 1, 'Ticket Source');
INSERT INTO lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) VALUES (8, 2, 'lookupList', 'ticket_severity', 2, 'Ticket Severity');
INSERT INTO lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) VALUES (8, 3, 'lookupList', 'ticket_priority', 3, 'Ticket Priority');
GO

UPDATE permission 
SET permission = 'myhomepage-reassign'
WHERE permission = 'admin-reassign';
GO

UPDATE permission
SET category_id = permission_category.category_id
FROM permission_category
WHERE permission = 'myhomepage-reassign'
AND permission_category.category = 'My Home Page';
GO

UPDATE permission_category
SET category = 'General Contacts'
WHERE category = 'Contacts & Resources';
GO

INSERT INTO permission (category_id, permission, level, permission_view, permission_add, permission_edit, permission_delete, description) VALUES (2, 'myhomepage-tasks', 85, 1, 1, 1, 1, 'My Tasks')
INSERT INTO permission (category_id, permission, level, permission_view, permission_add, permission_edit, permission_delete, description) VALUES (5, 'accounts-accounts-revenue', 95, 1, 1, 1, 1, 'Revenue')
INSERT INTO permission (category_id, permission, level, permission_view, permission_add, permission_edit, permission_delete, description) VALUES (6, 'campaign-campaigns-surveys', 60, 1, 1, 1, 1, 'Campaign Survey Records')
INSERT INTO permission (category_id, permission, level, permission_view, permission_add, permission_edit, permission_delete, description) VALUES (9, 'admin-usage', 45, 1, 0, 0, 0, 'System Usage')
GO

