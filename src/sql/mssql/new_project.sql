/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 20, 2002
 *@version    $Id$
 */
 

CREATE TABLE groups (
	group_id INT IDENTITY PRIMARY KEY ,
	group_name VARCHAR(50) NOT NULL ,
	enabled BIT DEFAULT 0 NOT NULL,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  modified DATETIME DEFAULT CURRENT_TIMESTAMP,
  expiration DATETIME DEFAULT NULL
);

CREATE INDEX "groups_idx" ON "groups" ("group_id");


CREATE TABLE users (
	user_id INT IDENTITY PRIMARY KEY ,
  group_id INTEGER DEFAULT 0 NOT NULL ,
	department_id INTEGER DEFAULT 0 NULL ,
	first_name VARCHAR(50) NULL ,
	last_name VARCHAR(50) NULL ,
	username VARCHAR(50) NOT NULL ,
	password VARCHAR(50) NOT NULL ,
	company VARCHAR(50) NULL ,
	email VARCHAR(50) NULL ,
	entered DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ,
	enteredby INTEGER DEFAULT 0,
	enabled BIT DEFAULT 0 NULL ,
  start_page INTEGER DEFAULT 1 NOT NULL ,
	access_personal BIT DEFAULT 0 NULL ,
	access_enterprise BIT DEFAULT 0 NULL ,
	access_admin BIT DEFAULT 0 NULL ,
	access_inbox BIT DEFAULT 0 NOT NULL ,
	access_resources BIT DEFAULT 0 NOT NULL ,
	last_login DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
  expiration DATETIME DEFAULT NULL
);

CREATE TABLE user_groups (
	user_id INTEGER NOT NULL ,
	group_id INTEGER NOT NULL 
);

CREATE TABLE departments (
	code INT IDENTITY PRIMARY KEY,
	group_id integer NOT NULL DEFAULT 0,
	description VARCHAR(100) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 0 NOT NULL
);


CREATE TABLE project_assignments (
	assignment_id INT IDENTITY PRIMARY KEY,
	project_id INTEGER NOT NULL,
  requirement_id INTEGER DEFAULT 0,
	assignedBy INTEGER NOT NULL,
	user_assign_id INTEGER NOT NULL,
	activity_id INTEGER DEFAULT 0 NULL,
	technology VARCHAR(50) NULL,
	role VARCHAR(255) NULL,
  estimated_loevalue INTEGER DEFAULT -1 NOT NULL,
  estimated_loetype INTEGER DEFAULT -1,
  actual_loevalue INTEGER DEFAULT -1 NOT NULL,
  actual_loetype INTEGER DEFAULT -1,
	priority_id INTEGER DEFAULT 0 NOT NULL,
	assign_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  est_start_date DATETIME NULL,
	start_date DATETIME NULL,
	due_date DATETIME NULL,
  status_id INTEGER NOT NULL,
	status_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
	complete_date DATETIME NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE INDEX "project_assignments_idx" ON "project_assignments"
  ("activity_id");
  
CREATE INDEX "project_assignments_cidx" ON "project_assignments" 
  ("complete_date", "user_assign_id");
  
CREATE TABLE project_assignments_status (
	status_id INT IDENTITY PRIMARY KEY,
	assignment_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	description TEXT NOT NULL,
	status_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE project_inbox (
	message_id INTEGER NOT NULL ,
	user_id INTEGER NOT NULL ,
	read_date DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
	addressee_type INTEGER NOT NULL 
);

CREATE TABLE project_inbox_items (
	message_id INT IDENTITY PRIMARY KEY ,
	user_id INTEGER NOT NULL ,
	project_id INTEGER NOT NULL ,
	message_date DATETIME NOT NULL ,
	message_category INTEGER NOT NULL ,
	message_subject VARCHAR(255) NOT NULL ,
	message_body TEXT NOT NULL 
);

CREATE TABLE project_issues (
	issue_id INT IDENTITY PRIMARY KEY,
	project_id INTEGER NOT NULL,
	type_id INTEGER NOT NULL,
	subject VARCHAR(255) NOT NULL,
	message TEXT NOT NULL,
  importance INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE INDEX "project_issues_limit_idx"
  ON "project_issues"
  ("type_id", "project_id", "enteredby");
  
CREATE INDEX "project_issues_idx"
  ON "project_issues"
  ("issue_id");  
  
  
CREATE TABLE project_issue_replies (
	reply_id INT IDENTITY PRIMARY KEY ,
	issue_id INTEGER NOT NULL ,
  reply_to INTEGER DEFAULT 0 ,
	subject VARCHAR(50) NOT NULL ,
	message TEXT NOT NULL ,
  importance INTEGER DEFAULT 0,
	entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE TABLE project_folders (
  folder_id INT IDENTITY PRIMARY KEY,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  subject VARCHAR(255) NOT NULL,
  description TEXT,
  parent INT NOT NULL DEFAULT 0
);
  
/* project_id will be replaced by link_item_id */
CREATE TABLE project_files (
	item_id INT IDENTITY PRIMARY KEY ,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
	project_id INTEGER NOT NULL , 
  folder_id INTEGER DEFAULT 0,
  client_filename VARCHAR(255) NOT NULL,
  filename VARCHAR(255) NOT NULL,
	subject VARCHAR(500) NOT NULL ,
  size INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 , 
  enabled BIT DEFAULT 1 ,
  downloads INTEGER DEFAULT 0,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE INDEX "project_files_cidx" ON "project_files" 
  ("link_module_id", "link_item_id");

CREATE TABLE project_files_version (
	item_id INTEGER,
  client_filename VARCHAR(255) NOT NULL,
  filename VARCHAR(255) NOT NULL,
	subject VARCHAR(500) NOT NULL ,
  size INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 ,
  enabled BIT DEFAULT 1 ,
  downloads INTEGER DEFAULT 0,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE TABLE project_files_download (
	item_id INTEGER,
  version FLOAT DEFAULT 0 ,
  user_download_id INTEGER NOT NULL,
  download_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE lookup_project_templates (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE lookup_project_activity (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  group_id INTEGER NOT NULL DEFAULT 0,
  template_id INTEGER DEFAULT 0
);

CREATE TABLE lookup_project_priority (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  group_id INTEGER NOT NULL DEFAULT 0,
  graphic VARCHAR(75),
  type INTEGER NOT NULL
);


CREATE TABLE lookup_project_issues (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE lookup_project_status (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  group_id INTEGER NOT NULL DEFAULT 0,
  graphic VARCHAR(75),
  type INTEGER NOT NULL
);

CREATE TABLE lookup_project_loe (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  base_value INTEGER DEFAULT 0 NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE project_requirements (
	requirement_id INT IDENTITY PRIMARY KEY,
	project_id INTEGER NOT NULL,
	submittedBy VARCHAR(50) NOT NULL,
	departmentBy VARCHAR(30) NULL,
	shortDescription VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
	dateReceived DATETIME NULL,
	estimated_loevalue INTEGER DEFAULT -1 NOT NULL,
  estimated_loetype INTEGER DEFAULT -1,
  actual_loevalue INTEGER DEFAULT -1 NOT NULL,
  actual_loetype INTEGER DEFAULT -1,
	deadline DATETIME NULL,
  approvedBy INTEGER DEFAULT -1,
  approvalDate DATETIME NULL,
  closedBy INTEGER DEFAULT -1,
	closeDate DATETIME NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE TABLE projects (
	project_id INT IDENTITY PRIMARY KEY ,
	group_id INTEGER NOT NULL ,
	department_id INTEGER NOT NULL ,
  template_id INTEGER NOT NULL DEFAULT 0,
	title VARCHAR(100) NOT NULL ,
	shortDescription VARCHAR(200) NOT NULL ,
	requestedBy VARCHAR(50) NULL ,
	requestedDept VARCHAR(50) NULL ,
	requestDate DATETIME DEFAULT CURRENT_TIMESTAMP NULL ,
	approvalDate DATETIME NULL ,
	closeDate DATETIME NULL,
  owner INTEGER NOT NULL,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified DATETIME DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE INDEX "projects_idx"
  ON "projects"
  ("group_id", "project_id");



CREATE TABLE project_timesheet (
	timesheet_id INT IDENTITY PRIMARY KEY ,
	user_id INTEGER NOT NULL ,
	project_id INTEGER NOT NULL ,
	start_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ,
	stop_time DATETIME NULL 
);

CREATE TABLE project_team (
	project_id INTEGER NOT NULL ,
	user_id INTEGER NOT NULL ,
	userLevel INTEGER DEFAULT 0 NOT NULL ,
	entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER DEFAULT 0 NOT NULL,
  modified DATETIME DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER DEFAULT 0 NOT NULL
);


